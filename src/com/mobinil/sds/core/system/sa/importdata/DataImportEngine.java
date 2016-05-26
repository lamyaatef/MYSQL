package com.mobinil.sds.core.system.sa.importdata;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.mobinil.sds.core.system.cam.memo.dao.MemoDAO;
import com.mobinil.sds.core.system.cr.bundle.dao.BundleDao;
import com.mobinil.sds.core.system.nomad.dao.NomadFileDAO;
import com.mobinil.sds.core.system.sa.importdata.model.*;
import com.mobinil.sds.core.utilities.Utility;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import com.mobinil.sds.core.system.sa.importdata.dao.*;
import com.mobinil.sds.web.interfaces.cam.*;
import java.text.*;

import com.mobinil.sds.web.interfaces.sa.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataImportEngine {

    public DataImportEngine() {
    }
    private static final String GetTableCellsDefSQL =
            " select * from vw_ADM_DATA_IMPORT_CELL_DEF, ADM_DATA_IMPORT_CELL_TYPE "
            + " where ADM_DATA_IMPORT_CELL_TYPE.type_id = vw_ADM_DATA_IMPORT_CELL_DEF.CELL_TYPE_ID"
            + " and table_id = ? order by position_num";

    private Vector getTableCellsDef(Connection con, String tableId) throws Exception {
        Vector cellsDefVec = new Vector();
        System.out.println("in getTableCellsDef table id "+tableId);
        PreparedStatement stat = con.prepareStatement(GetTableCellsDefSQL);
        stat.setString(1, tableId);
        ResultSet res = stat.executeQuery();
        while (res.next()) {
            DataImportCellDefModel cellDef = new DataImportCellDefModel(res);
            cellsDefVec.add(cellDef);
        }
        res.close();
        stat.close();
        System.out.println("finished getTableCellsDef");
        return cellsDefVec;
    }

    private /*void*/int insertToTable(DataImportTableDefModel tableModel, Hashtable valuesTable, Vector report, Connection con, int rownum, Statement stat) {
        Utility.logger.debug("inside DataImportEngine function insertToTable****");
        String fieldsNames = "";
        String valueString = "";

        Enumeration keys = valuesTable.keys();
        Enumeration values = valuesTable.elements();



        while (values.hasMoreElements()) {
            DataImportCellDefModel cellModel = (DataImportCellDefModel) keys.nextElement();
            String value = (String) values.nextElement();
            value = value.replaceAll("\\s+", " ").trim();
            if (fieldsNames.length() != 0) {
                fieldsNames += ",";
            }

            fieldsNames += cellModel.getCellSQLName();

            if (valueString.length() != 0) {
                valueString += ",";
            }



            if (cellModel.getCellTypeName().compareTo("date") == 0) {

                java.util.Date parsedDate = HSSFDateUtil.getJavaDate(Double.parseDouble(value));


                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdfOutput = new SimpleDateFormat("MM/dd/yyyy");
                String sqlFormattedDate = sqlDate.toString();


                java.util.Date date = null;


                try {

                    date = sdfInput.parse(sqlFormattedDate);
                } catch (ParseException e) {

                    e.printStackTrace();
                }


                value = sdfOutput.format(date).toString();




                valueString += "to_date('" + value + "','dd/mm/yyyy')"; //dd/mm/yyyy


            } else if (cellModel.getCellTypeName().compareTo("datestring") == 0) {

                SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/mm/yyyy");//dd/mm/yyyy

                value = sdfOutput.format(value);


                valueString += "to_date('" + value + "','dd/mm/yyyy')";//dd.mm.yyyy

            } //added by Lamya to allow for date time insert 
             else   if (cellModel.getCellTypeName().compareTo("date_timestamp") == 0) {

                 System.out.println("date_timestamp if ");

    
                 java.util.Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(Double.parseDouble(value));

                System.out.println("THE DATE : "+date );
                
                /*System.out.println(date.getMinutes());
                System.out.println(date.getHours());
                String dateStr = (date.getMonth()+1)+"/"+(date.getDay()+1)+"/"+ ( date.getYear()+1900) +" "+date.getHours() +":"+date.getMinutes() ;*/
                System.out.println("MINUTES : "+date.getMinutes());
                System.out.println("HOURS : "+date.getHours());
                String dateStr = DateFormat.getDateInstance().format(date);
                System.out.println("THE DATE STRING: "+dateStr );
                
                dateStr += " "+date.getHours()+":"+date.getMinutes();
                System.out.println("THE DATE STRING WITH HOURS AND MINUTES: "+dateStr );
                valueString += "to_date('" + dateStr + "','mm/dd/yyyy HH24:MI')"; //dd/mm/yyyy


            } 
            else {
                valueString += "'" + value + "'";
            }



        }
        String sql = "insert into " + tableModel.getTableName() + "(" + fieldsNames + ") values( " + valueString + ")";
        //Lamya: Nomad Data Records
        String sqlCount = "select count(*) from "+tableModel.getTableName();
        Utility.logger.debug(sql);
        ResultSet rs = null;
        debug(sql);
        debug(sqlCount);
        int count=0;
        try {

            stat.executeUpdate(sql);
            rs = stat.executeQuery(sqlCount);
            if(rs.next())
            {
                count = Integer.parseInt(rs.getString("count(*)"));
            }

        } catch (Exception e) {
            ErrorInfo error = new ErrorInfo();
            error.setErrorMsg(e.getMessage());
            error.setRowNum(rownum);
            report.add(error);
            e.printStackTrace();
        }
        System.out.println("the count ISSS : "+count);
        return count;

    }

    public DataImportReport ImportUploadPaymentListFile(String fileName, String operation, List<String> tableId) {
        Vector report = new Vector();
        DataImportReport importReport = new DataImportReport();

        int numberOfRowsUpdated = 0;
        try {

            Connection con = Utility.getConnection();


            Statement stat = con.createStatement();

            for (String tableID : tableId) {

                Vector cellsDefVec = getTableCellsDef(con, tableID);
                System.out.println("table id" + tableID);
                System.out.println("debugging file name "+fileName);
                DataImportTableDefModel tableModel = DataImportTableDefDAO.getTableDef(tableID);

                FileInputStream tempIn = new FileInputStream(fileName);
                
                Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(tempIn);

                Sheet sheet = wb.getSheetAt(0);

                int lastRowNum = sheet.getLastRowNum() + 1;
                debug("Last Row Number = " + lastRowNum);

                Hashtable valuesTable = new Hashtable();
                Hashtable primaryKeysTable = new Hashtable();

                boolean insertOperationFlag = false;
                if (operation.compareTo(AdministrationInterfaceKey.DATA_IMPORT_INSERT) == 0) {
                    insertOperationFlag = true;
                } else if (operation.compareTo(AdministrationInterfaceKey.DATA_IMPORT_UPDATE) == 0) {
                    insertOperationFlag = false;
                }

                for (int currentRow = 1; currentRow < lastRowNum; currentRow++) {
                    valuesTable.clear();
                    primaryKeysTable.clear();
                    boolean readRowFlag = readRow(cellsDefVec, currentRow, sheet.getRow(currentRow), report, valuesTable, primaryKeysTable, insertOperationFlag);

                    if (readRowFlag) {
                        if (insertOperationFlag) {
                            insertToTable(tableModel, valuesTable, report, con, currentRow, stat);
                        } else {
                            numberOfRowsUpdated += updateTable(tableModel, valuesTable, primaryKeysTable, report, con, currentRow, stat);
                        }
                    }
                }

            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage() != null) {
                ErrorInfo error = new ErrorInfo();
                error.setErrorMsg(e.getMessage());
                report.add(error);

            }
        }


        importReport.setOperation(operation);
        importReport.setNumOfRecordsUpdated(numberOfRowsUpdated);
        importReport.setReport(report);

        return importReport;
    }

    private void insertToTableWithRowNumber(DataImportTableDefModel tableModel, Hashtable valuesTable, Vector report, Connection con, int rownum, Statement stat, int rowNumber, String strUserId) {
//Utility.logger.debug("inside DataImportEngine function insertToTableWithRowNumber****");

        StringBuffer fieldsNames = new StringBuffer("ROW_NUM,USER_ID");
        StringBuffer valueString = new StringBuffer("" + rowNumber + "," + strUserId);

        Enumeration keys = valuesTable.keys();
        Enumeration values = valuesTable.elements();


        while (values.hasMoreElements()) {
            DataImportCellDefModel cellModel = (DataImportCellDefModel) keys.nextElement();
            String value = (String) values.nextElement();
            value = value.replaceAll("\\s+", " ").trim();

            if (fieldsNames.length() != 0) {
                fieldsNames.append(",");
            }

            fieldsNames.append(cellModel.getCellSQLName());

            if (valueString.length() != 0) {
                valueString.append(",");
            }

            if (cellModel.getCellTypeName().compareTo("date") == 0) {
                java.util.Date parsedDate = HSSFDateUtil.getJavaDate(Double.parseDouble(value));


                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdfOutput = new SimpleDateFormat("MM/dd/yyyy");
                String sqlFormattedDate = sqlDate.toString();


                java.util.Date date = null;


                try {

                    date = sdfInput.parse(sqlFormattedDate);
                } catch (ParseException e) {

                    e.printStackTrace();
                }


                value = sdfOutput.format(date).toString();





                valueString.append("to_date('" + value + "','dd/mm/yyyy')");
            } else {
                valueString.append("'" + value + "'");
            }

        }

        StringBuffer sql = new StringBuffer("insert into ");

        sql.append(tableModel.getTableName());
        sql.append("(");
        sql.append(fieldsNames.toString());
        sql.append(") values( ");
        sql.append(valueString.toString());
        sql.append(")");

        try {

            stat.execute(sql.toString());

        } catch (Exception e) {
            ErrorInfo error = new ErrorInfo();
            error.setErrorMsg(e.getMessage());
            error.setRowNum(rownum);
            report.add(error);
        }



    }

    private void insertToTableWithRowNumberWithSequence(String primaryKey, String sequenceName, DataImportTableDefModel tableModel, Hashtable valuesTable, Vector report, Connection con, int rownum, Statement stat, int rowNumber, String strUserId) {
//Utility.logger.debug("inside DataImportEngine function insertToTableWithRowNumber****");

        StringBuffer fieldsNames = new StringBuffer("ROW_NUM,USER_ID");
        StringBuffer valueString = new StringBuffer("" + rowNumber + "," + strUserId);

        Enumeration keys = valuesTable.keys();
        Enumeration values = valuesTable.elements();


        while (values.hasMoreElements()) {
            DataImportCellDefModel cellModel = (DataImportCellDefModel) keys.nextElement();
            String value = (String) values.nextElement();
            value = value.replaceAll("\\s+", " ").trim();

            if (fieldsNames.length() != 0) {
                fieldsNames.append(",");
            }

            fieldsNames.append(cellModel.getCellSQLName());

            if (valueString.length() != 0) {
                valueString.append(",");
            }

            if (cellModel.getCellTypeName().compareTo("date") == 0) {
                if (value.indexOf("/") != -1) {
                } else {
                    java.util.Date parsedDate = HSSFDateUtil.getJavaDate(Double.parseDouble(value));


                    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                    SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdfOutput = new SimpleDateFormat("MM/dd/yyyy");
                    String sqlFormattedDate = sqlDate.toString();


                    java.util.Date date = null;


                    try {

                        date = sdfInput.parse(sqlFormattedDate);
                    } catch (ParseException e) {

                        e.printStackTrace();
                    }


                    value = sdfOutput.format(date).toString();





                }
                valueString.append("to_date('" + value + "','dd/mm/yyyy')");
            } else {
                valueString.append("'" + value + "'");
            }



        }

        StringBuffer sql = new StringBuffer("insert into ");

        sql.append(tableModel.getTableName());
        sql.append("(");
        sql.append(primaryKey);
        sql.append(",");
        sql.append(fieldsNames.toString());
        sql.append(") values( ");
        sql.append(sequenceName);
        sql.append(".nextval,");
        sql.append(valueString.toString());
        sql.append(")");



        try {
            System.out.println(sql);
            stat.execute(sql.toString());

        } catch (Exception e) {
            ErrorInfo error = new ErrorInfo();
            error.setErrorMsg(e.getMessage());
            error.setRowNum(rownum);
            report.add(error);
            e.printStackTrace();
        }



    }

    private void insertToTableWithRowNumber(DataImportTableDefModel tableModel, Hashtable valuesTable, Vector report, Connection con, int rownum, Statement stat, int rowNumber) {
//Utility.logger.debug("inside DataImportEngine function insertToTableWithRowNumber****");
        String fieldsNames = "ROW_NUM";
        String valueString = "" + rowNumber;

        Enumeration keys = valuesTable.keys();
        Enumeration values = valuesTable.elements();

        String dateValue = "";
        while (values.hasMoreElements()) {
            DataImportCellDefModel cellModel = (DataImportCellDefModel) keys.nextElement();
            String value = (String) values.nextElement();
            value = value.replaceAll("\\s+", " ").trim();
            if (fieldsNames.length() != 0) {
                fieldsNames += ",";
            }

            fieldsNames += cellModel.getCellSQLName();

            if (valueString.length() != 0) {
                valueString += ",";
            }

            if (cellModel.getCellTypeName().compareTo("date") == 0) {
                java.util.Date parsedDate = HSSFDateUtil.getJavaDate(Double.parseDouble(value));


                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdfOutput = new SimpleDateFormat("MM/dd/yyyy");
                String sqlFormattedDate = sqlDate.toString();


                java.util.Date date = null;


                try {

                    date = sdfInput.parse(sqlFormattedDate);
                } catch (ParseException e) {

                    e.printStackTrace();
                }
                //  System.out.println (sdfOutput.format (date) );

                dateValue = sdfOutput.format(date).toString();
                valueString += "to_date('" + dateValue + "','mm/dd/yyyy')";

            } else {
                valueString += "'" + value + "'";
            }

        }

        String sql = "insert into " + tableModel.getTableSQLName() + "(" + fieldsNames + ") values( " + valueString + ")";

        //debug(sql);

        try {

            stat.execute(sql);

        } catch (Exception e) {
            ErrorInfo error = new ErrorInfo();
            error.setErrorMsg(e.getMessage());
            error.setRowNum(rownum);
            report.add(error);
        }



    }

    private int updateTable(DataImportTableDefModel tableModel, Hashtable valuesTable, Hashtable primaryKeysTable,
            
        Vector report, Connection con, int rownum, Statement stat) {
        int numOfRecordsUpdated = 0;
        String fieldsNames = "";
        String valueString = "";

        Enumeration keys = valuesTable.keys();
        Enumeration values = valuesTable.elements();
        while (values.hasMoreElements()) {
            DataImportCellDefModel cellModel = (DataImportCellDefModel) keys.nextElement();
            String value = (String) values.nextElement();
            value = value.replaceAll("\\s+", " ").trim();
            if (fieldsNames.length() != 0) {
                fieldsNames += ",";
            }
            fieldsNames += " " + cellModel.getCellSQLName() + "=";
            if (cellModel.getCellTypeName().compareTo("date") == 0) {

                java.util.Date parsedDate = HSSFDateUtil.getJavaDate(Double.parseDouble(value));


                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdfOutput = new SimpleDateFormat("MM/dd/yyyy");
                String sqlFormattedDate = sqlDate.toString();


                java.util.Date date = null;


                try {

                    date = sdfInput.parse(sqlFormattedDate);
                } catch (ParseException e) {

                    e.printStackTrace();
                }


                value = sdfOutput.format(date).toString();


                fieldsNames += "to_date('" + value + "','dd/mm/yyyy')";
            } else {
                fieldsNames += "'" + value + "'";
            }

        }

        String primayKeyWhereStatement = "";
        keys = primaryKeysTable.keys();
        values = primaryKeysTable.elements();
        while (values.hasMoreElements()) {
            DataImportCellDefModel cellModel = (DataImportCellDefModel) keys.nextElement();
            String value = (String) values.nextElement();
            if (primayKeyWhereStatement.length() != 0) {
                primayKeyWhereStatement += " and ";
            }
            primayKeyWhereStatement += " " + cellModel.getCellSQLName() + "=";
            if (cellModel.getCellTypeName().compareTo("date") == 0) {

                if ((value.indexOf("/") != -1)) {
                } else {
                    java.util.Date parsedDate = HSSFDateUtil.getJavaDate(Double.parseDouble(value));


                    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                    SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdfOutput = new SimpleDateFormat("MM/dd/yyyy");
                    String sqlFormattedDate = sqlDate.toString();


                    java.util.Date date = null;


                    try {

                        date = sdfInput.parse(sqlFormattedDate);
                    } catch (ParseException e) {

                        e.printStackTrace();
                    }

                    value = sdfOutput.format(date).toString();
                }

                primayKeyWhereStatement += "to_date('" + value + "','dd/mm/yyyy')";


            } else {
                primayKeyWhereStatement += "'" + value + "'";
            }

        }


        if (fieldsNames.length() == 0) {
            return 0;
        }

        String sql = "update " + tableModel.getTableSQLName() + " set " + fieldsNames;

        if (primayKeyWhereStatement.length() != 0) {
            sql += " where " + primayKeyWhereStatement;
        }
        Utility.logger.debug("wwwwwww" + sql);
        debug(sql);

        try {

            numOfRecordsUpdated = stat.executeUpdate(sql);


        } catch (Exception e) {
            ErrorInfo error = new ErrorInfo();
            error.setErrorMsg(e.getMessage());
            error.setRowNum(rownum);
            report.add(error);
        }

        return numOfRecordsUpdated;

    }
    private static final boolean debugFlag = true;

    private static void debug(String s) {
        if (debugFlag) {
            System.out.println(s);
        }
        //Utility.logger.debug(s);
    }

    public DataImportReport ImportFile(String fileName, String operation, String tableId) {
       
        System.out.println("inside ImportFile in DataImportEngine : "+fileName);
        Vector report = new Vector();
        DataImportReport importReport = new DataImportReport();
        int numberOfRowsInserted = 0;
        int numberOfRowsUpdated = 0;
        try {
            Connection con = Utility.getConnection();
            Vector cellsDefVec = getTableCellsDef(con, tableId);
            System.out.println("now print table id" + tableId);
            DataImportTableDefModel tableModel = DataImportTableDefDAO.getTableDef(tableId);
            System.out.println("finished getTableDef");
            FileInputStream tempIn = new FileInputStream(fileName);
            System.out.println("-> workbook factory create : "+tempIn);
            Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(tempIn);
            System.out.println("-> get sheet");
            Sheet sheet = wb.getSheetAt(0);
            System.out.println("-> get last row no");
            int lastRowNum = sheet.getLastRowNum() + 1;
            debug("Last Row Number = " + lastRowNum);
            System.out.println("again Last Row Number = " + lastRowNum);
            Hashtable valuesTable = new Hashtable();
            Hashtable primaryKeysTable = new Hashtable();

            boolean insertOperationFlag = false;
            if (operation.compareTo(AdministrationInterfaceKey.DATA_IMPORT_INSERT) == 0) {
                insertOperationFlag = true;
            } else if (operation.compareTo(AdministrationInterfaceKey.DATA_IMPORT_UPDATE) == 0) {
                insertOperationFlag = false;
            }

            Statement stat = con.createStatement();
            for (int currentRow = 1; currentRow < lastRowNum; currentRow++) {
                valuesTable.clear();
                primaryKeysTable.clear();
                boolean readRowFlag = readRow(cellsDefVec, currentRow, sheet.getRow(currentRow), report, valuesTable, primaryKeysTable, insertOperationFlag);

                if (readRowFlag) {
                    if (insertOperationFlag) {
                        System.out.println("-> insert to table");
                        numberOfRowsInserted = insertToTable(tableModel, valuesTable, report, con, currentRow, stat);
                        
                        System.out.println("FINISHED insert to table : numberOfRowsInserted : "+numberOfRowsInserted);
                        //TODO : insert a new nomad file with min date max date no of records label id
                        
                        
                    } else {
                        System.out.println("-> update table");
                        numberOfRowsUpdated += updateTable(tableModel, valuesTable, primaryKeysTable, report, con, currentRow, stat);
                        System.out.println("FINISHED update table : numberOfRowsUpdated : "+numberOfRowsUpdated);
                    }
                }
            }
            System.out.println("-> insert in file but the nomad records added are : "+numberOfRowsInserted);
            //String labelName = NomadFileDAO.getLabelName(labelId);
            //TODO: get user id , min date in nomad data , max date in nomad data
            
            
            //NomadFileDAO.insertNewFile("min", "max", numberOfRowsInserted, "New", "274", labelId);
            
            
            
            stat.close();
            Utility.closeConnection(con);

        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage() != null) {
                ErrorInfo error = new ErrorInfo();
                error.setErrorMsg(e.getMessage());
                report.add(error);

            }
        }

        System.out.println("NSERT FILE: numberOfRowsUpdated "+numberOfRowsUpdated+"  numberOfRowsInserted "+numberOfRowsInserted);
        importReport.setOperation(operation);
        importReport.setNumOfRecordsUpdated(numberOfRowsUpdated);
        importReport.setNumOfRecordsInserted(numberOfRowsInserted);
        importReport.setReport(report);

        return importReport;
    }

    public DataImportReport ImportFileWithRowNumber(String fileName, String operation, String tableId) {
        //Utility.logger.debug("inside ImportFileWithRowNumber");
        Vector report = new Vector();
        DataImportReport importReport = new DataImportReport();

        int numberOfRowsUpdated = 0;
        try {
            Connection con = Utility.getConnection();
            Vector cellsDefVec = getTableCellsDef(con, tableId);

            DataImportTableDefModel tableModel = DataImportTableDefDAO.getTableDef(tableId);

            FileInputStream tempIn = new FileInputStream(fileName);


            Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(tempIn);

            Sheet sheet = wb.getSheetAt(0);



            int lastRowNum = sheet.getLastRowNum() + 1;


            Hashtable valuesTable = new Hashtable();

            Hashtable primaryKeysTable = new Hashtable();

            boolean insertOperationFlag = false;

            if (operation.compareTo(AdministrationInterfaceKey.DATA_IMPORT_INSERT) == 0) {


                insertOperationFlag = true;
            } else if (operation.compareTo(AdministrationInterfaceKey.DATA_IMPORT_UPDATE) == 0) {
                insertOperationFlag = false;
            }

            Statement stat = con.createStatement();
            for (int currentRow = 1; currentRow < lastRowNum; currentRow++) {
                valuesTable.clear();
                primaryKeysTable.clear();
                boolean readRowFlag = readRow(cellsDefVec, currentRow, sheet.getRow(currentRow), report, valuesTable, primaryKeysTable, insertOperationFlag);

                if (readRowFlag) {
                    if (insertOperationFlag) {
                        insertToTableWithRowNumber(tableModel, valuesTable, report, con, currentRow, stat, currentRow);
                    } else {
                        numberOfRowsUpdated += updateTable(tableModel, valuesTable, primaryKeysTable, report, con, currentRow, stat);
                    }
                }
            }
            stat.close();
            Utility.closeConnection(con);

        } catch (Exception e) {
            System.out.println("exception e : "+e);
            e.printStackTrace();

            if (e.getMessage() != null) {
                ErrorInfo error = new ErrorInfo();
                error.setErrorMsg(e.getMessage());
                report.add(error);

            }
        }


        importReport.setOperation(operation);
        importReport.setNumOfRecordsUpdated(numberOfRowsUpdated);
        importReport.setReport(report);

        return importReport;
    }

    public DataImportReport ImportMemoFileWithRowNumber(String fileName, String operation, String tableId) {
        //Utility.logger.debug("inside ImportFileWithRowNumber");
        Vector report = new Vector();
        DataImportReport importReport = new DataImportReport();

        int numberOfRowsUpdated = 0;
        try {
            Connection con = Utility.getConnection();
            Vector cellsDefVec = getTableCellsDef(con, tableId);
            System.out.println("table id = " + tableId);
            DataImportTableDefModel tableModel = MemoDAO.getTableDef(tableId);

            FileInputStream tempIn = new FileInputStream(fileName);

            Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(tempIn);
            Sheet sheet = wb.getSheetAt(0);



            int lastRowNum = sheet.getLastRowNum() + 1;
            debug("Last Row Number = " + lastRowNum);

            Hashtable valuesTable = new Hashtable();
            Hashtable primaryKeysTable = new Hashtable();

            boolean insertOperationFlag = false;
            if (operation.compareTo(MemoInterfaceKey.DATA_IMPORT_INSERT) == 0) {


                insertOperationFlag = true;
            } else if (operation.compareTo(MemoInterfaceKey.DATA_IMPORT_UPDATE) == 0) {

                insertOperationFlag = false;
            }

            Statement stat = con.createStatement();
            for (int currentRow = 1; currentRow < lastRowNum; currentRow++) {
                valuesTable.clear();
                primaryKeysTable.clear();
                boolean readRowFlag = readRow(cellsDefVec, currentRow, sheet.getRow(currentRow), report, valuesTable, primaryKeysTable, insertOperationFlag);
                if (readRowFlag) {
                    if (insertOperationFlag) {
                        insertToTableWithRowNumber(tableModel, valuesTable, report, con, currentRow, stat, currentRow);
                    } else {
                        numberOfRowsUpdated += updateTable(tableModel, valuesTable, primaryKeysTable, report, con, currentRow, stat);
                    }
                }
            }
            stat.close();
            Utility.closeConnection(con);

        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage() != null) {
                ErrorInfo error = new ErrorInfo();
                error.setErrorMsg(e.getMessage());
                report.add(error);

            }
        }


        importReport.setOperation(operation);
        importReport.setNumOfRecordsUpdated(numberOfRowsUpdated);
        importReport.setReport(report);

        return importReport;
    }

    private DataImportReport importFileWithRowNumAndOrSheetNum(String fileName, String operation, String tableId, String strUserId, int sheetNum) {
        Vector report = new Vector();
        DataImportReport importReport = new DataImportReport();

        int numberOfRowsUpdated = 0;
        try {
            Connection con = Utility.getConnection();
            Vector cellsDefVec = getTableCellsDef(con, tableId);
            DataImportTableDefModel tableModel = DataImportTableDefDAO.getTableDef(tableId);

            FileInputStream tempIn = new FileInputStream(fileName);
            Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(tempIn);

            Sheet sheet = wb.getSheetAt(sheetNum);

            int lastRowNum = sheet.getLastRowNum() + 1;
            debug("Last Row Number = " + lastRowNum);

            Hashtable valuesTable = new Hashtable();
            Hashtable primaryKeysTable = new Hashtable();

            boolean insertOperationFlag = false;
            if (operation.compareTo(AdministrationInterfaceKey.DATA_IMPORT_INSERT) == 0) {
                insertOperationFlag = true;
            } else if (operation.compareTo(AdministrationInterfaceKey.DATA_IMPORT_UPDATE) == 0) {
                insertOperationFlag = false;
            }

            Statement stat = con.createStatement();
            for (int currentRow = 1; currentRow < lastRowNum; currentRow++) {
                valuesTable.clear();
                primaryKeysTable.clear();
                boolean readRowFlag = readRow(cellsDefVec, currentRow, sheet.getRow(currentRow), report, valuesTable, primaryKeysTable, insertOperationFlag);
                if (readRowFlag) {
                    if (insertOperationFlag) {
                        insertToTableWithRowNumber(tableModel, valuesTable, report, con, currentRow, stat, currentRow, strUserId);
                    } else {
                        numberOfRowsUpdated += updateTable(tableModel, valuesTable, primaryKeysTable, report, con, currentRow, stat);
                    }
                }
            }
            stat.close();
            Utility.closeConnection(con);

        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage() != null) {
                ErrorInfo error = new ErrorInfo();
                error.setErrorMsg(e.getMessage());
                report.add(error);

            }
        }


        importReport.setOperation(operation);
        importReport.setNumOfRecordsUpdated(numberOfRowsUpdated);
        importReport.setReport(report);

        return importReport;
    }

    private DataImportReport importFileWithRowNumAndOrSheetNumSequence(String primaryKey, String sequenceName, String fileName, String operation, String tableId, String strUserId, int sheetNum) {
        Vector report = new Vector();
        DataImportReport importReport = new DataImportReport();

        int numberOfRowsUpdated = 0;
        try {
            Connection con = Utility.getConnection();
            Vector cellsDefVec = getTableCellsDef(con, tableId);
            DataImportTableDefModel tableModel = DataImportTableDefDAO.getTableDef(tableId);

            FileInputStream tempIn = new FileInputStream(fileName);
            Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(tempIn);

            Sheet sheet = wb.getSheetAt(sheetNum);

            int lastRowNum = sheet.getLastRowNum() + 1;
            debug("Last Row Number = " + lastRowNum);

            Hashtable valuesTable = new Hashtable();
            Hashtable primaryKeysTable = new Hashtable();

            boolean insertOperationFlag = false;
            if (operation.compareTo(AdministrationInterfaceKey.DATA_IMPORT_INSERT) == 0) {
                insertOperationFlag = true;
            } else if (operation.compareTo(AdministrationInterfaceKey.DATA_IMPORT_UPDATE) == 0) {
                insertOperationFlag = false;
            }

            Statement stat = con.createStatement();
            for (int currentRow = 1; currentRow < lastRowNum; currentRow++) {
                valuesTable.clear();
                primaryKeysTable.clear();
                boolean readRowFlag = readRow(cellsDefVec, currentRow, sheet.getRow(currentRow), report, valuesTable, primaryKeysTable, insertOperationFlag);
                if (readRowFlag) {
                    if (insertOperationFlag) {
                        insertToTableWithRowNumberWithSequence(primaryKey, sequenceName, tableModel, valuesTable, report, con, currentRow, stat, currentRow, strUserId);
                    } else {
                        numberOfRowsUpdated += updateTable(tableModel, valuesTable, primaryKeysTable, report, con, currentRow, stat);
                    }
                }
            }
            stat.close();
            Utility.closeConnection(con);

        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage() != null) {
                ErrorInfo error = new ErrorInfo();
                error.setErrorMsg(e.getMessage());
                report.add(error);

            }
        }


        importReport.setOperation(operation);
        importReport.setNumOfRecordsUpdated(numberOfRowsUpdated);
        importReport.setReport(report);

        return importReport;
    }

    public DataImportReport ImportFileWithRowNumber(String fileName, String operation, String tableId, String strUserId) {
        return importFileWithRowNumAndOrSheetNum(fileName, operation, tableId, strUserId, 0);
    }

    public DataImportReport ImportFileSheetWithRowNumber(String fileName, String operation, String tableId, String strUserId, int sheetNum) {
        return importFileWithRowNumAndOrSheetNum(fileName, operation, tableId, strUserId, sheetNum);
    }

    public DataImportReport ImportFileSheetWithRowNumberWithSeq(String primaryKey, String sequenceName, String fileName, String operation, String tableId, String strUserId, int sheetNum) {
        return importFileWithRowNumAndOrSheetNumSequence(primaryKey, sequenceName, fileName, operation, tableId, strUserId, sheetNum);
    }

    public boolean readRow(Vector cellsDefVec, int rownum, Row row, Vector report, Hashtable valuesTable, Hashtable primaryKeysTable, boolean insertOperation) {
        if (row == null) {
            ErrorInfo error = new ErrorInfo();
            error.setCellName("");
            error.setRowNum(rownum + 1);
            error.setErrorMsg("The row is empty");
            report.add(error);
            return false;
        }

        for (int cellIndex = 0; cellIndex < cellsDefVec.size(); cellIndex++) {
            DataImportCellDefModel cellModel = (DataImportCellDefModel) cellsDefVec.get(cellIndex);

            Cell cell = row.getCell((short) cellModel.getPositionNum());

            String cellValue = "";

            if (cell != null) {
                cellValue = readCell(cell);
            }

            //Utility.logger.debug( "cell Value = " + cellValue + "  cell Position = " + cellModel.getPositionNum() +" row = " + row.getRowNum() + "  cell is mandatory= " + cellModel.isCellMandatoryFlag());
            //    debug( "cell Value = " + cellValue + "  cell Position = " + cellModel.getPositionNum() +" row = " + row.getRowNum() + "  cell is mandatory= " + cellModel.isCellMandatoryFlag());

            //check if there is no value in the cell and it is mandatory
            if (cellValue.length() == 0 && cellModel.isCellMandatoryFlag()) {
                ErrorInfo error = new ErrorInfo();
                error.setCellName(cellModel.getCellName());
                error.setRowNum(row.getRowNum());
                error.setErrorMsg("No valid value in the field " + cellModel.getCellName() + " which is a mandatory field");
                report.add(error);
                System.out.println("error ");
                return false;
            }

            //check the type of the cell in the definition and the value in it
            if (checkCellType(cellValue, cellModel, row, report)) {
            } else {
                return false;
            }

            if (cellValue.length() != 0) {
                if (cellModel.isCellPrimaryKeyFlag() && (!insertOperation)) {
                    primaryKeysTable.put(cellModel, cellValue);
                } else {
                    valuesTable.put(cellModel, cellValue);
                }
            }

        }


        return true;
    }

    private boolean checkCellType(String cellValue, DataImportCellDefModel cellModel, Row row, Vector report) {
        if (cellValue.length() != 0) {
            if (cellModel.getCellTypeName().compareTo("int") == 0) {
                try {
                    Integer.parseInt(cellValue);
                } catch (Exception e) {
                    ErrorInfo error = new ErrorInfo();
                    error.setCellName(cellModel.getCellName());
                    error.setRowNum(row.getRowNum());
                    error.setErrorMsg("Not valid value in the cell. This cell is of type Integer");
                    report.add(error);
                    return false;
                }
            }
            if (cellModel.getCellTypeName().compareTo("float") == 0) {
                try {
                    Float.parseFloat(cellValue);
                } catch (Exception e) {
                    ErrorInfo error = new ErrorInfo();
                    error.setCellName(cellModel.getCellName());
                    error.setRowNum(row.getRowNum());
                    error.setErrorMsg("Not valid value in the cell. This cell is of type Real");
                    report.add(error);
                    return false;
                }
            }
            if (cellModel.getCellTypeName().compareTo("date") == 0) {

                try {

                    if ((cellValue.indexOf("/") != -1)) {
                        return true;

                    }

                    DateFormat dateFormatter = SimpleDateFormat.getDateInstance(2);

                    dateFormatter.setLenient(false);
                    //cellValue = cellValue.replace('\\','/');


                    java.util.Date parsedDate = HSSFDateUtil.getJavaDate(Double.parseDouble(cellValue));




                    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                    SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdfOutput = new SimpleDateFormat("MM/dd/yyyy");
                    String sqlFormatetd = sqlDate.toString();

                    // try {
                    //java.util.Date   dateValue =  sdfInput.parse (sqlFormatetd) ;
                    // System.out.println (  sdfOutput.format (dateValue )  ) ;

                    //}catch (ParseException e) {

                    // e.printStackTrace();
                    // }  

                } catch (Exception e) {
                    ErrorInfo error = new ErrorInfo();
                    error.setCellName(cellModel.getCellName());
                    error.setRowNum(row.getRowNum());
                    error.setErrorMsg("Not valid value in the cell. This cell is of type Date. Valid date format is dd/mm/yyyy");
                    report.add(error);
                    e.printStackTrace();
                    return false;

                }


            }
            if (cellModel.getCellTypeName().compareTo("intstring") == 0) {

                if (!containsOnlyNumbers(cellValue)) {
                    ErrorInfo error = new ErrorInfo();
                    error.setCellName(cellModel.getCellName());
                    error.setRowNum(row.getRowNum());
                    error.setErrorMsg("Not valid value in the cell. This cell is of type must contain only numeric digits");
                    report.add(error);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean containsOnlyNumbers(String str) {

        //It can't contain only numbers if it's null or empty...
        if (str == null || str.length() == 0) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {

            //If we find a non-digit character we return false.
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /*
     * 8- read a HSSFCell object and return its value as String
     */
    public static String readCell(Cell cell) {
        if (cell == null) {
            return "";
        }


        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                return "";

            case Cell.CELL_TYPE_BOOLEAN:
                if (cell.getBooleanCellValue()) {
                    return "TRUE";
                } else {
                    return "FALSE";
                }

            case Cell.CELL_TYPE_ERROR:
                debug("Error in cell reading");
                return "";

            case Cell.CELL_TYPE_FORMULA:
                debug("Cell is a formula");
                return "";

            case Cell.CELL_TYPE_NUMERIC: {
                int temp = (int) cell.getNumericCellValue();
                if (cell.getNumericCellValue() - temp == 0) {
                    return temp + "";
                } else {
                    return cell.getNumericCellValue() + "";
                }
            }

            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue().trim();
            default:
                return "";
        }

    }

    public static void main(String args[]) {
        try {


            DateFormat dateFormatter = SimpleDateFormat.getDateInstance(2);

            dateFormatter.setLenient(false);
            String s1 = "24/12/1995";
            //String s2= "12/24/1999";
            //String s3="2-2-2000";

            Utility.logger.debug(dateFormatter.parse(s1));
            //Utility.logger.debug(dateFormatter.parse(s3));
            //Utility.logger.debug(dateFormatter.parse(s3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
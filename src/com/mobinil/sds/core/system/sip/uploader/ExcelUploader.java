package com.mobinil.sds.core.system.sip.uploader;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.sip.SIPInterfaceKey;

import  java.text.DecimalFormat;
import java.text.NumberFormat;


public class ExcelUploader
{
    static final int id0 = 0;
    static final int id1 = 1;
    static final int id2 = 2;
    static ArrayList<Boolean> isValidRow = new ArrayList<Boolean>();
    static String validCellId0 = "";
    static String validCellId1 = "";
    static String validCellId2 = "";

    public static void uploadExcel(int type, String filePath, File savedFile,
            String quartar, String year, String month, String userId)
            throws SQLException
    {

        File file = savedFile;

        Workbook wb = null;

        try
        {

            FileInputStream tempIn = new FileInputStream(file);
            wb = WorkbookFactory.create(tempIn);

        } catch (Exception e)
        {
            e.printStackTrace();

        }

        Connection con = Utility.getConnection();
        Statement st = con.createStatement();
        String file_id = String.valueOf(Utility.getSequenceNextVal(con, "SEQ_SIP_FILE_DETAIL_ID"));

        int lastCellIndx = 0;
        int numberOfSheets = wb.getNumberOfSheets();
        
        int sheetIndex = 0; 

            Sheet sheet = wb.getSheetAt(sheetIndex);
            int lastRow = sheet.getLastRowNum();
            String sheetName = wb.getSheetName(sheetIndex);
            Row row = null;

            for (int j = 0; j <= lastRow; j++)
            {

                row = sheet.getRow(j);

                Integer rowNumber = row.getRowNum();

        
                if (rowNumber >= 1)

                {
                    lastCellIndx = row.getLastCellNum();

                    for (int h = 0; h < lastCellIndx; h++)
                    {
                        Cell cell = row.getCell(h);

                        isValidRow.clear();

                        if (cell != null)
                        {
                            cellProcessExPOS(con, st, type, cell, sheetName, rowNumber, h, lastCellIndx, month, quartar, year, file_id, (sheetIndex + 1));
                        } else
                        {

                            // System.out.println("Error in sheet name "+sheetName+
                            // " row number "+(rowNumber+1)+
                            // " Column number "+(j+1)+" is empty." );

                        }

                    }
                }

            }


        insertFile(st, file_id, year, quartar, type + "", userId, lastCellIndx, numberOfSheets);

        validCellId0 = "";
        validCellId1 = "";
        validCellId2 = "";
        st.close();
        Utility.closeConnection(con);

    }

    public static void cellProcess(Connection con, Statement st, int type,
            String tableName, String fields, Cell cell, String sheetName,
            int rowNumber, int currentCellIndex, int lastCellIndex,
            String quartar, String year, String file_id) throws SQLException
    {

        int cellIndex = cell.getColumnIndex();
        int cellType = cell.getCellType();
        
        System.out.println("cell index ="+ cellIndex +"  cellType = " + cell.getCellType());
        
        String cellString = "";

        if (cellType == Cell.CELL_TYPE_STRING)
        {
            System.out.println("cell string = "+cell.getStringCellValue() );
            cellString = cell.getStringCellValue();
        }

        if (cellType == Cell.CELL_TYPE_NUMERIC)
        {
            System.out.println(" cell numeric value  = "+ cell.getNumericCellValue());
            cellString = String.valueOf(cell.getNumericCellValue());
            
            System.out.println("hereee ");
        }
        // if (cellString.contains ( ".0" ))cellString = cellString.replace (
        // ".0" , "" );

        if (cellIndex == id0)
        {
            validCellId0 = cellString == null || cellString.compareTo("") == 0
                    ? "0"
                    : cellString;
            if (validCellId0.compareTo("") != 0)
                isValidRow.add(true);
            else
                isValidRow.add(false);

        }
        if (cellIndex == id1)
        {
            validCellId1 = cellString == null || cellString.compareTo("") == 0
                    ? "0"
                    : cellString;
            if (validCellId1.compareTo("") != 0)
                isValidRow.add(true);
            else
                isValidRow.add(false);
        }

        if (!isValidRow.contains(false)
                && currentCellIndex == (lastCellIndex - 1))
        {
            insertIntoDB(st, type, tableName, fields, validCellId0, validCellId1, quartar, year, file_id);
        }

    }
    
    static NumberFormat formatter = new DecimalFormat("#.#####");
    
    public static void cellProcessExPOS(Connection con, Statement st, int type,
            Cell cell, String sheetName, int rowNumber, int currentCellIndex,
            int lastCellIndex, String month, String quartar, String year,
            String file_id, int sheetNumber) throws SQLException
    {

        int cellIndex = cell.getColumnIndex();
        int cellType = cell.getCellType();

        String cellString = "";
        
        //System.out.println("cell index ="+ cellIndex +"  cellType = " + cell.getCellType());
        
        
        if (cellType == Cell.CELL_TYPE_STRING)
        {                       
            cellString = cell.getStringCellValue();
        }

        if (cellType == Cell.CELL_TYPE_NUMERIC)
        {            
            //this is to avoid the problems of number that have E to the power and so            
            cellString = formatter.format(cell.getNumericCellValue() );
         }


        if (cellIndex == id0)
        {
            validCellId0 = /*
                            * checkCell0( cellString, sheetName, rowNumber ,
                            * cellIndex);
                            */checkCell(cellString);
            if (validCellId0.compareTo("") != 0)
                isValidRow.add(true);
            else
                isValidRow.add(false);

        }
        if (cellIndex == id1)
        {
            validCellId1 = /*
                            * checkCell1( cellString, sheetName, rowNumber ,
                            * cellIndex);
                            */checkCell(cellString);
            if (validCellId1.compareTo("") != 0)
                isValidRow.add(true);
            else
                isValidRow.add(false);
        }

        if (cellIndex == id2)
        {
            validCellId2 = /*
                            * checkCell1( cellString, sheetName, rowNumber ,
                            * cellIndex);
                            */checkCell(cellString);
            if (validCellId2.compareTo("") != 0)
                isValidRow.add(true);
            else
                isValidRow.add(false);
        }

        if (!isValidRow.contains(false)
                && (currentCellIndex == (lastCellIndex - 1)))
        {
            String sql = prepareInsertExcelRowSql(file_id, month, quartar, year, type, sheetNumber);
            // System.out.println ("prepareInsertExcelRowSql isssss  "+sql);
            insertRow(sql, st, type);
        }

    }

    public static String checkCell(String cellString)
    {
        if (cellString == null || cellString.compareTo("") == 0)
            return "0";
        else
            return cellString;
    }

    public static String prepareInsertExcelRowSql(String fileId,
            String monthId, String quartarId, String yearId, int type,
            int sheetNo)
    {

        /*
        if (validCellId1.contains(".0"))
            validCellId1 = validCellId1.replace(".0", "");
        */
        
       /* 
        if (validCellId2.contains(".0"))
            validCellId2 = validCellId2.replace(".0", "");
        */
        
        String checkField1 = "'" + validCellId0 + "'";;
        String checkField2 = "'" + validCellId1 + "'";;
        String checkField3 = "'" + validCellId2 + "'";;

        switch (type)
        {

            // all other lists should be stored as theyy were to manage to
            // retrive them as they are
            case 1 :
            {

            }
                break;
            case 2 :
            {
                checkField1 = "(nvl((select to_char(DISTRICT_CODE) from GEN_DISTRICT where DISTRICT_CODE='"
                        + validCellId0 + "'),";
                checkField1 += "concat('0_','" + validCellId0 + "')))";

                String temp = "";
                if (validCellId1.contains("@"))
                    temp = "0' OR USER_LOGIN = '" + validCellId1;
                else
                    temp = validCellId1 + "' OR USER_LOGIN = '" + validCellId1;

                checkField2 = "(nvl((select to_char(USER_ID) from GEN_USER where USER_ID='"
                        + temp + "'),";
                checkField2 += "concat('0_','" + validCellId1 + "')))";

                checkField3 = "'" + validCellId2 + "'";
            }
                break;
            case 3 :
            {
                checkField1 = "(nvl((select to_char(DCM_ID) from GEN_DCM where DCM_CODE='"
                        + validCellId0 + "'),";
                checkField1 += "concat('0_','" + validCellId0 + "')))";

                checkField2 = "(nvl((select to_char(DCM_ID) from GEN_DCM where DCM_CODE='"
                        + validCellId1 + "'),";
                checkField2 += "concat('0_','" + validCellId1 + "')))";

                checkField3 = "'" + validCellId2 + "'";
            }
                break;
            case 4 :
            {
                String temp = "";

                if (validCellId0.contains("@"))
                    temp = "0' OR USER_LOGIN = '" + validCellId0;
                else
                    temp = validCellId0 + "' OR USER_LOGIN = '" + validCellId0;

                checkField1 = "(nvl((select to_char(USER_ID) from GEN_USER where USER_ID='"
                        + temp + "'),";
                checkField1 += "concat('0_','" + validCellId0 + "')))";

                temp = "";
                if (validCellId1.contains("@"))
                    temp = "0' OR USER_LOGIN = '" + validCellId1;
                else
                    temp = validCellId1 + "' OR USER_LOGIN = '" + validCellId1;

                checkField2 = "(nvl((select to_char(USER_ID) from GEN_USER where USER_ID='"
                        + temp + "'),";
                checkField2 += "concat('0_','" + validCellId1 + "')))";

                checkField3 = "'" + validCellId2 + "'";
            }
                break;
            case 5 :
            {
                checkField1 = "(nvl((select to_char(DCM_ID) from GEN_DCM where DCM_CODE='"
                        + validCellId0 + "'),";
                checkField1 += "concat('0_','" + validCellId0 + "')))";

                String temp = "";
                if (validCellId1.contains("@"))
                    temp = "0' OR USER_LOGIN = '" + validCellId1;
                else
                    temp = validCellId1 + "' OR USER_LOGIN = '" + validCellId1;

                checkField2 = "(nvl((select to_char(USER_ID) from GEN_USER where USER_ID='"
                        + temp + "'),";
                checkField2 += "concat('0_','" + validCellId1 + "')))";

                checkField3 = "'" + validCellId2 + "'";
            }
                break;
            case 6 :
            {
                checkField1 = "(nvl((select to_char(DCM_ID) from GEN_DCM where DCM_CODE='"
                        + validCellId0 + "'),";
                checkField1 += "concat('0_','" + validCellId0 + "')))";
                checkField2 = "'" + validCellId1 + "'";
                checkField3 = "'" + validCellId2 + "'";
            }
                break;
            case 7 :
            {
                checkField1 = "'" + validCellId0 + "'";
                String temp = "";

                if (validCellId1.contains("@"))
                    temp = "0' OR USER_LOGIN = '" + validCellId1;
                else
                    temp = validCellId1 + "' OR USER_LOGIN = '" + validCellId1;

                checkField2 = "(nvl((select to_char(USER_ID) from GEN_USER where USER_ID='"
                        + temp + "'),";
                checkField2 += "concat('0_','" + validCellId1 + "')))";

                checkField3 = "'" + validCellId2 + "'";
            }
                break;

            default :
                break;
        }

        StringBuffer sql = new StringBuffer("INSERT INTO SIP_UPLOADED_LISTS ( FILE_ID ,FIELD1 , FIELD2, FIELD3, MONTH, QUARTAR, YEAR, FILE_TYPE_ID,SHEET_NUMBER");
        sql.append(") VALUES (");
        sql.append("'");
        sql.append(fileId);
        sql.append("',");
        sql.append(checkField1);
        sql.append(",");
        sql.append(checkField2);
        sql.append(",");
        sql.append(checkField3);
        sql.append(",'");
        sql.append(monthId);
        sql.append("','");
        sql.append(quartarId);
        sql.append("','");
        sql.append(yearId);
        sql.append("','");
        sql.append(type);
        sql.append("','");
        sql.append(sheetNo);
        sql.append("')");

        return sql.toString();

    }

    public static void insertFile(Statement st, String file_id, String year,
            String quartar, String type_id, String userId, int fieldsCount,
            int numberOfSheets) throws SQLException
    {

        StringBuffer sqlBuffer = new StringBuffer("INSERT INTO SIP_FILE_DETAIL ( FILE_ID, UPLOADED_DATE, REPORT_YEAR,");
        sqlBuffer.append("REPORT_QUARTAR ,REPORT_TYPE,USER_ID,EXCEL_FIELDS_COUNT,EXCEL_SHEETS_COUNT) VALUES ('");
        sqlBuffer.append(file_id);
        sqlBuffer.append("',sysdate,'");
        sqlBuffer.append((year == null || year.compareTo("") == 0) ? "0" : year);
        sqlBuffer.append("','");
        sqlBuffer.append(quartar);
        sqlBuffer.append("','");
        sqlBuffer.append(type_id);
        sqlBuffer.append("','");
        sqlBuffer.append(userId);
        sqlBuffer.append("','");
        sqlBuffer.append(fieldsCount);
        sqlBuffer.append("','");
        sqlBuffer.append(numberOfSheets);
        sqlBuffer.append("')");

        System.out.println("sql is " + sqlBuffer.toString());
        st.executeUpdate(sqlBuffer.toString());

    }

    public static void insertRow(String sql, Statement st, int type)
            throws SQLException
    {

        DBUtil.executeSQL(sql, st.getConnection());
    }

    public static void insertIntoDB(Statement st, int type, String tableName,
            String fields, String id0, String id1, String quartar, String year,
            String file_id) throws SQLException
    {

        String sql = "";
        // year variable = condition
        if (type == SIPInterfaceKey.HIDDEN_PARAM_SIP_REPORT_EMP)
        {
            if (id1.contains(".0"))
                id1 = id1.replace(".0", "");

            if (id1.contains("@"))
            {
                year = "0' OR USER_LOGIN = '" + id1;
            } else
                year = id1 + "' OR USER_LOGIN = '" + id1;

            sql = "insert into " + tableName + " (" + fields
                    + ",FILE_ID) values ('" + id0
                    + "',(select user_id from gen_user where USER_ID='" + year
                    + "'),'" + file_id + "')";
        } else
            sql = "insert into " + tableName + " (" + fields
                    + ",FILE_ID) values ('" + id1
                    + "',(select DCM_ID from GEN_DCM where DCM_CODE='" + id0
                    + "'),'" + quartar + "','" + year + "','" + file_id + "')";

        System.out.println("sql is " + sql);
        st.executeUpdate(sql);

    }

    public static void upload()
    {
    }

}

package com.mobinil.sds.core.system.cr.excelImport;
/*
 * Thie file handel the excel import operations the following functionality in
 * it 1-init database connection 2- insert sheet informatino in the database
 * 3-this functions is responsible of generating the template it take two input
 * the template path file and the generated file path it uses the template to
 * copy it to the genreated and fill all the look up sheet so the combo box can
 * work fine the intial structure and lay out of the template is all done
 * manually in teh template file it self 4- this function save the html log of
 * the import process in the database 5- this method is responsible of read a
 * row statment strating from cell index readStartCell and using the
 * statmenttype to determine which kind of statment it will read from the row
 * object sent it to it then it stores all the values read from that statment
 * into the valuestable send to it as a parameter 6-get the dcm id of the dcm
 * code of this sheet 7-import excel file taskes the file name as input also an
 * error vector that the method take to fill with any error reports generated
 * also it take an importReport object that it fill with the imoprted items 8-
 * read a HSSFCell object and return its value as String 9-validate a sheet
 * statment checking all values are not null and are valid by validating them
 * from the database and upon that set the necessarly flags weither the sheet is
 * accepted or rejected or an error occure 10-validate a contract statment and
 * form the necessary sql to insert it to the database if it was a valid
 * contract and also calculate the status of the contract and add necessary
 * warning 11-this method set the current sheet status also it keep certin rules
 * such as a rejectede sheet can't be set back to imported 12- init all status
 * values from the database that will be used through the import process 13-
 * init look up tables 14- fillHashTableWithResultSet take a sql and run it and
 * fill the results in a hashtable and retur 15-insert sheet warningfor the
 * current sheet 16-validate dcm id 17- fill a column in the sheet object using
 * the data that result from query the dataabse with the sql sent to the method
 * 18- this is a debuging function taht print debuging information from the
 * class if the debug flag is set to true however make sure to set the debugFlag
 * to false after debugin casue it reduce performance of this module
 */

import java.io.*;
import java.util.*;
import java.sql.*;
import java.util.Date;
import com.mobinil.sds.core.utilities.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.*;
import com.mobinil.sds.core.system.cr.excelImport.Contract;
import com.mobinil.sds.core.system.cr.excelImport.ImportReport;
import com.mobinil.sds.core.system.cr.excelImport.Sheet;
import java.math.*;

public class ExcelImport {

    public static String SHEET_WARNING_MISSING_DATA;
    public static String SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS_TEXT = "REJECTED OR NOT IMPORTED CONTRACT(S)";
    public static String CONTRACT_WARNING_REJECTED_SHEET_TEXT = "REJECTED SHEET";
    public static String CONTRACT_WARNING_INVALID_CONTRACT_DETAILS_TEXT = "INVALID CONTRACT DETAILS";
    public static String SHEET_WARNING_COUNT_MISMATCH;
    public static String CONTRACT_WARNING_INVALID_CONTRACT_DETAILS;
    public static String CONTRACT_WARNING_REJECTED_SHEET_ID;
    public static String SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS;
    public static String SHEET_WARNING_COUNT_MISMATCH_TEXT = "COUNT MISMATCH";
    public static String SHEET_WARNING_MISSING_DATA_TEXT = "MISSING DATA";
    String dcmCode;
    int rowNum;
    ImportReport report = null;
    //Statement validateSheetStatement = null;
    private int currentSheetContractCount = 0;
    private int currentSheetContractsNum = 0;
    private boolean currentSheetHasNotImportedContracts = false;
    private Hashtable statTable;
    private String currentSheetId;
    private Vector insertVector = new Vector(); //carries all the insert statment of sheet 
    private Vector errorVector; //this carry the error message that the system generate while parsing the excel file
    private int currentSheetStatus = -1;
    private Hashtable optParamsTypes;
    private Hashtable sheetWarnings;
    private Hashtable idTypes;
    private Hashtable productTypes;
    private Date timeStamp;
    private String timeStampS;
    private Statement insertStat;
    private Hashtable sheetWarning;
    private String currentSheetSerial;
    private String userId;
    //all these values get initialized from the database 
    private int importPhaseId = 1;
    private int SHEET_REJECTED_IMPORT = -1;
    private int SHEET_IMPORTED = 1;
    private int SHEET_INCOMPLETE = 2;
    private int SHEET_STATUS_NEW = 1;
    private int CONTRACT_IMPORT_STATUS = 1;
    private int CONTRACT_REJECT_STATUS = -1;
    private String SHEET_SERIAL_WARNING_ID = null;
    private Connection con;
    private Hashtable sheetTypes;
    /*
     * database fields and related settings to the vw_cr_sheet
     */
    private static String SHEET_DISTRIBUTER_ID = "SHEET_DISTRIBUTER_CODE";
    private static String SHEET_SERIAL_FIELD = "SHEET_SERIAL_ID";
    private static String SHEET_POS_FIELD = "SHEET_POS_CODE";
    //Ahmed Adel
    //private static String SHEET_SECOND_POS_FIELD="SHEET_SECOND_POS_CODE";
    private static String SHEET_SUPER_DEALER_FIELD = "SHEET_SUPER_DEALER_CODE";
    private static String SHEET_RECIEVE_DATE = "SHEET_RECIEVE_DATE";
    private static String SHEET_SERIAL_BELONG_TO_ANOTHER_DCM_WARNING = "Sheet Serial Belong To Another DCM";
    private static String SHEET_ID = "SHEET_ID";
    private static String SHEET_TYPE_FIELD = "SHEET_TYPE_ID";
    private static String SHEET_CONTRACT_COUNT = "CONTRACTS_COUNT";
    private static final String SHEET_REJECTED_STATUS_SET = "('REJECTED IMPORT','REJECTED DELIVERY','REJECTED VERIFY','REJECTED AUTHINTICATION','REJECTED COMMISSION')";

    /*
     * database related fields and information of Contract
     */
    private static final String CONTRACT_REJECTED_STATUS_SET = "('REJECTED IMPORT','REJECTED DELIVERY','REJECTED VERIFY','REJECTED AUTHINTICATION')";
    private static final String CONTRACT_TYPE_FIELD = "CONTRACT_TYPE_ID";
    private static final String CONTRACT_ID = "CONTRACT_ID";
    private static final String CONTRACT_MAIN_SIM_NO = "CONTRACT_MAIN_SIM_NO";
    private static final String Id_No_Field = "CONTRACT_CUSTOMER_ID_TYPE";
    private static final String CUSTOMER_ID_NO = "CONTRACT_CUSTOMER_ID";
    private static final String CONTRACT_FORM_NUMBER = "CONTRACT_FORM_NUMBER";
    /*
     * Data base connection settings should only be used when testing just excel
     * functinality with no deployment
     */
    /*
     * private static final String dbConnectionString =
     * "jdbc:oracle:thin:@10.1.17.30:1521:sds"; private static final String
     * dbUserName = "sds"; private static final String dbPassword = "sds01";
     */

    /*
     * reading excel columns numbers settings contractFirstCellNum indicate the
     * index of the first cell taht has contrat info as for sheetFirstCellNum
     * indicate the index of first call that has sheet info
     */
    private static final int contractFirstCellNum = 7;
    private static final int sheetFirstCellNum = 0;
    //set this debug flag to true for debuging 
    private static final boolean debugFlag = false;
    private static Statement validateCntrctStat = null;
    long startT = 0;

    /*
     * 2- insert sheet informatino in the database
     */
    public void insertSheetToDB(Statement stat) {
        Statement retrivalQuery = null;
        //debug("insert Sheet To Db");
        if (currentSheetId == null) {
            insertVector.clear();
            return;
        }
        Sheet sheet = this.report.getSheet(currentSheetSerial);
        try {

            //    Statement statQ = con.createStatement();
            //     retrivalQuery = con.createStatement();

            //startT = System.currentTimeMillis();

            ResultSet resQ = stat.executeQuery("select SEQ_CR_SHEET_STATUS.nextval from dual");

            //debug("time to get sheet seq = " + (System.currentTimeMillis() - startT));
            resQ.next();
            String sheetStatusId = resQ.getString(1);


            String sheetStatus;
            if (this.currentSheetHasNotImportedContracts && this.currentSheetStatus != SHEET_REJECTED_IMPORT) {
                if (sheet.contracts.size() == 0) {

                    //  setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);                

                    //  this.report.changeSheetStatus(this.currentSheetSerial,Sheet.REJECTED);
                    errorVector.add(new ErrorInfo("Sheet", sheet.getRowNum(), "Sheet Serial Number " + currentSheetSerial + " has no contracts"));
                    this.insertVector.clear();
                    this.report.sheetReports.remove(this.currentSheetSerial);
                    this.sheetWarning.clear();
                    this.insertVector.clear();
                    return;
                } else {
                    //check if the sheet has not a single contract imported so it can be rejected and not imported
                    if (currentSheetHasNotImportedContracts) {
                        //   setCurrentSheetStatus(SHEET_INCOMPLETE);
                        this.report.changeSheetStatus(this.currentSheetSerial, Sheet.REJECTED);
                        setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
                        this.sheetWarning.put(this.SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS, this.SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS_TEXT);
                    }
                }
            } else {
                if (this.currentSheetStatus != SHEET_REJECTED_IMPORT) {
                    //check if the number of contracts that got imported is differnt from the total number reported 
                    //so taht the status of the sheet be rejected
                    if ((sheet.contracts.size()) != this.currentSheetContractCount || sheet.contracts.size() == 0) {
                        //debug("sheet rejected cause no contracts under it or cause the contract count is not the same as contracts imported sheet id =" + this.currentSheetId);
                        setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
                        this.report.changeSheetStatus(this.currentSheetSerial, Sheet.REJECTED);
                        currentSheetHasNotImportedContracts = false;
                        this.sheetWarning.put(this.SHEET_WARNING_COUNT_MISMATCH, this.SHEET_WARNING_COUNT_MISMATCH_TEXT);

                        //insertVector.clear();

                        //return ;
                    } else {
                        setCurrentSheetStatus(this.SHEET_IMPORTED);
                        if (this.currentSheetStatus == SHEET_STATUS_NEW) {
                            this.report.changeSheetStatus(this.currentSheetSerial, Sheet.IMPORTED);
                        }
                    }
                }
            }
            //check if any of the contracts imported is rejected so 
            //we reject the whole sheet
            if (sheet.isExistContractRejected()) {
                //debug("sheet rejected cause there exist a  contracts under it that is rejected sheet id =" + this.currentSheetId);
                setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
                sheet.setStatus(Sheet.REJECTED);
                this.sheetWarning.put(this.SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS, this.SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS_TEXT);
            }

            //set the status of all non rejected contracts to rejected if the sheet got rejected 
            if (currentSheetStatus == SHEET_REJECTED_IMPORT) {
                //sheet.setAllContractsStatus(Contract.REJECTED);
                sheet.setAllNonRejectedContractsToRejected();
            }

            String statusInsertSQL = "insert into  cr_SHEET_STATUS (user_id,SHEET_STATUS_ID,SHEET_ID,SHEET_STATUS_TYPE_ID,SHEET_STATUS_DATE) values " + "(" + userId + "," + sheetStatusId + "," + this.currentSheetId + "," + currentSheetStatus + "," + "sysdate)";
            this.insertVector.add(statusInsertSQL);



            if (sheet.warnings.size() != 0) {
                String warningInsertSql = "insert into cr_sheet_warning(sheet_warning_id,sheet_status_id,sheet_warning_date,sheet_Warning_type_id)values(SEQ_CR_SHEET_WARNING.nextval," + sheetStatusId + ",TO_DATE(" + "sysdate)," + this.SHEET_SERIAL_WARNING_ID + ")";
                this.insertVector.add(warningInsertSql);
            }



            //insert sheet warning all are system genereated 
            Enumeration sheetWarningsEnum = this.sheetWarning.keys();
            Enumeration sheetWarningsTextEnum = this.sheetWarning.elements();

            while (sheetWarningsEnum.hasMoreElements()) {
                String warningTypeId = (String) sheetWarningsEnum.nextElement();
                String warningText = (String) sheetWarningsTextEnum.nextElement();
                String warningInsertSql = "insert into cr_sheet_warning(sheet_warning_id,sheet_status_id,sheet_warning_date,sheet_warning_type_id)" + " values(SEQ_CR_SHEET_WARNING.nextval," + sheetStatusId + ",sysdate ," + warningTypeId + ")";

                this.insertVector.add(warningInsertSql);

                sheet.warnings.put(warningText, warningText);
            }
            sheetWarning.clear();

            if (insertStat == null) {
                insertStat = con.createStatement();
            }

            insertStat.clearBatch();
            String temp = "";
            try {


                for (int i = 0; i < insertVector.size(); i++) {
                    temp = (String) insertVector.get(i);
                    //debug(temp);
                    //insertStat.execute(temp);
                    insertStat.addBatch(temp);
                }
                //     startT = System.currentTimeMillis();
                insertStat.executeBatch();
                //debug("time to execute batch insert = "+ ((System.currentTimeMillis()-startT) ));
            } catch (Exception e) {
                //debug("here is an exceptoion ");
                //debug("temp = " + temp);
                e.printStackTrace();
            }

            //     startT = System.currentTimeMillis();   

            sheet.insertToDBAllContracts(insertStat, stat);

            //debug("time to execute all contracts insert = "+ ((System.currentTimeMillis()-startT) ));
            //debug("contracts inserted count = "+ sheet.contracts.size());
            //con.commit();
            currentSheetHasNotImportedContracts = false;
            resQ.close();
            //statQ.close();
            //retrivalQuery.close();
            //insertStat.close();
            //insertStat =null;
        } catch (Exception e) {
            //debug("insert sheet");
            e.printStackTrace();
        }
        insertVector.clear();
    }

    /*
     * 3-this functions is responsible of generating the template it take two
     * input the template path file and the generated file path it uses the
     * template to copy it to the genreated and fill all the look up sheet so
     * the combo box can work fine the intial structure and lay out of the
     * template is all done manually in teh template file it self
     */
    public boolean exportTemplate(String template, String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(template);

            Workbook wb = WorkbookFactory.create(tempIn);

            org.apache.poi.ss.usermodel.Sheet lookUpSheet = wb.getSheet("lookup");

            Connection con = Utility.getConnection();


            Statement stat = con.createStatement();

            //fill look up values using this function 
            fillColumn("select SHEET_TYPE_NAME, SHEET_TYPE_ID from VW_CR_SHEET_TYPE order by SHEET_TYPE_NAME ASC", "SHEET_TYPE_NAME", "SHEET_TYPE_ID", stat, lookUpSheet, 0, 0);

            fillColumn("select PRODUCT_NAME, PRODUCT_ID from VW_GEN_PRODUCT ORDER BY PRODUCT_NAME ASC", "PRODUCT_NAME", "PRODUCT_ID", stat, lookUpSheet, 2, 0);

            fillColumn("select *  from VW_CR_CUSTOMER_ID_TYPE ORDER BY CUSTOMER_ID_TYPE_NAME ASC", "CUSTOMER_ID_TYPE_NAME", "CUSTOMER_ID_TYPE_ID", stat, lookUpSheet, 4, 0);
            fillColumn("select CITY_ENGLISH,CITY_CODE from GEN_CITY order by CITY_ENGLISH ASC", "CITY_ENGLISH", "CITY_CODE", stat, lookUpSheet, 6, 0);


            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);

            org.apache.poi.ss.usermodel.Sheet dataSheet = wb.getSheet("Data");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short) 0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();

            //con.close();
            Utility.closeConnection(con);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportPosUpdateAddressTemplate(String template, String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(template);

            Workbook wb = WorkbookFactory.create(tempIn);

            org.apache.poi.ss.usermodel.Sheet lookUpSheet = wb.getSheet("lookup");

            Connection con = Utility.getConnection();


            Statement stat = con.createStatement();

            //fill look up values using this function 

            fillColumn("select REGION_NAME,REGION_ID FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID = 1 order by REGION_ID ASC", "REGION_NAME", "REGION_ID", stat, lookUpSheet, 0, 0);

            fillColumn("select REGION_NAME,REGION_ID FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID = 2 order by REGION_ID ASC", "REGION_NAME", "REGION_ID", stat, lookUpSheet, 2, 0);

            fillColumn("select REGION_NAME,REGION_ID FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID = 3 order by REGION_ID ASC", "REGION_NAME", "REGION_ID", stat, lookUpSheet, 4, 0);

            fillColumn("select REGION_NAME,REGION_ID FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID = 4 order by REGION_ID ASC", "REGION_NAME", "REGION_ID", stat, lookUpSheet, 6, 0);

            fillColumn("select REGION_NAME,REGION_ID FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID = 5 order by REGION_ID ASC", "REGION_NAME", "REGION_ID", stat, lookUpSheet, 8, 0);


            org.apache.poi.ss.usermodel.Sheet dataSheet = wb.getSheet("Data");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short) 0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            System.out.println("fileoutput path" + fileName);
            wb.write(fileOut);
            fileOut.close();

            //con.close();
            Utility.closeConnection(con);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportPaymentLevelTemplate(String template, String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(template);

            Workbook wb = WorkbookFactory.create(tempIn);

            org.apache.poi.ss.usermodel.Sheet lookUpSheet = wb.getSheet("lookup");

            Connection con = Utility.getConnection();


            Statement stat = con.createStatement();

            //fill look up values using this function 
            fillColumn("select DCM_PAYMENT_LEVEL_NAME , DCM_PAYMENT_LEVEL_ID  from GEN_DCM_PAYMENT_LEVEL", "DCM_PAYMENT_LEVEL_NAME", "DCM_PAYMENT_LEVEL_ID", stat, lookUpSheet, 0, 0);


            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);

            org.apache.poi.ss.usermodel.Sheet dataSheet = wb.getSheet("Data");

            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short) 0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();

            //con.close();
            Utility.closeConnection(con);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportCreditAdviceTemplate(String template, String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(template);

            Workbook wb = WorkbookFactory.create(tempIn);

            org.apache.poi.ss.usermodel.Sheet lookUpSheet = wb.getSheet("lookup");

            Connection con = Utility.getConnection();


            Statement stat = con.createStatement();

            //fill look up values using this function 
            fillColumn("select STATUS_ID, STATUS_NAME from CRD_STATUS_CREDIT order by STATUS_NAME ASC", "STATUS_NAME", "STATUS_ID", stat, lookUpSheet, 0, 0);

            File file = new File("Shady");
            System.err.println("Path Excel Import:" + file.getAbsolutePath());


            org.apache.poi.ss.usermodel.Sheet dataSheet = wb.getSheet("Data");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short) 0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            System.out.println("file output = " + fileName);
            //con.close();
            Utility.closeConnection(con);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean exportTemp(String template, String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(template);

            Workbook wb = WorkbookFactory.create(tempIn);

            org.apache.poi.ss.usermodel.Sheet lookUpSheet = wb.getSheet("lookup");

            Connection con = Utility.getConnection();

            Statement stat = con.createStatement();

            fillColumn("select REGION_NAME,REGION_ID FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID = 1 order by REGION_ID ASC", "REGION_NAME", "REGION_ID", stat, lookUpSheet, 0, 0);

            fillColumn("select REGION_NAME,REGION_ID FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID = 2 order by REGION_ID ASC", "REGION_NAME", "REGION_ID", stat, lookUpSheet, 2, 0);

            fillColumn("select REGION_NAME,REGION_ID FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID = 3 order by REGION_ID ASC", "REGION_NAME", "REGION_ID", stat, lookUpSheet, 4, 0);

            fillColumn("select REGION_NAME,REGION_ID FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID = 4 order by REGION_ID ASC", "REGION_NAME", "REGION_ID", stat, lookUpSheet, 6, 0);

            fillColumn("select REGION_NAME,REGION_ID FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID = 5 order by REGION_ID ASC", "REGION_NAME", "REGION_ID", stat, lookUpSheet, 8, 0);
            org.apache.poi.ss.usermodel.Sheet dataSheet = wb.getSheet("Data");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short) 0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            System.out.println("file output = " + fileName);
            //con.close();
            Utility.closeConnection(con);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean exportTemplateDataWarehouse(String template, String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            org.apache.poi.ss.usermodel.Sheet lookUpSheet = wb.getSheet("Sheet1");
            //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con = Utility.getConnection();
            // @machineName:port:SID,   userid,  password            

            Statement stat = con.createStatement();
            //fill look up values using this function 
            //fillColumn("select * from SOP_SCHEMA_PRODUCT,SOP_SCHEMA where SOP_SCHEMA.SCHEMA_STATUS_ID = 1 and SOP_SCHEMA.SCHEMA_ID = SOP_SCHEMA_PRODUCT.SCHEMA_ID", "LCS_PRODUCT_CODE", "PRODUCT_NAME_ENGLISH",stat, lookUpSheet, 0, 1);
            //fillColumn("select PRODUCT_NAME, PRODUCT_ID from VW_GEN_PRODUCT ORDER BY PRODUCT_NAME ASC", "PRODUCT_NAME", "PRODUCT_ID", stat, lookUpSheet, 2, 0);
            //fillColumn("select *  from VW_CR_CUSTOMER_ID_TYPE ORDER BY CUSTOMER_ID_TYPE_NAME ASC", "CUSTOMER_ID_TYPE_NAME", "CUSTOMER_ID_TYPE_ID", stat, lookUpSheet, 4, 0);

            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            org.apache.poi.ss.usermodel.Sheet dataSheet = wb.getSheet("Sheet1");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short) 0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();

            //con.close();
            Utility.closeConnection(con);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportTemplateArchiving(String template, String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            org.apache.poi.ss.usermodel.Sheet lookUpSheet = wb.getSheet("Sheet2");

            Connection con = Utility.getConnection();

            Statement stat = con.createStatement();
            //fill look up values using this function 
            fillColumn("select * from cr_contract_warning_type where phase_id = 3 and warning_status_id = 1 and warning_type_id = 1 and warning_suggested_status_id = 1", "CONTRACT_WARNING_TYPE_ID", "CONTRACT_WARNING_TYPE_NAME", stat, lookUpSheet, 0, 0);
            //fillColumn("select PRODUCT_NAME, PRODUCT_ID from VW_GEN_PRODUCT ORDER BY PRODUCT_NAME ASC", "PRODUCT_NAME", "PRODUCT_ID", stat, lookUpSheet, 2, 0);
            //fillColumn("select *  from VW_CR_CUSTOMER_ID_TYPE ORDER BY CUSTOMER_ID_TYPE_NAME ASC", "CUSTOMER_ID_TYPE_NAME", "CUSTOMER_ID_TYPE_ID", stat, lookUpSheet, 4, 0);

            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            org.apache.poi.ss.usermodel.Sheet dataSheet = wb.getSheet("Sheet1");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short) 0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();

            //con.close();
            Utility.closeConnection(con);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportTemplateAuthDataImport(String template, String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            org.apache.poi.ss.usermodel.Sheet lookUpSheet = wb.getSheet("lookup");
            //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con = Utility.getConnection();
            // @machineName:port:SID,   userid,  password            

            Statement stat = con.createStatement();
            //fill look up values using this function 
            //fillColumn("select SHEET_TYPE_NAME, SHEET_TYPE_ID from VW_CR_SHEET_TYPE order by SHEET_TYPE_NAME ASC", "SHEET_TYPE_NAME", "SHEET_TYPE_ID", stat, lookUpSheet, 0, 0);
            //fillColumn("select PRODUCT_NAME, PRODUCT_ID from VW_GEN_PRODUCT ORDER BY PRODUCT_NAME ASC", "PRODUCT_NAME", "PRODUCT_ID", stat, lookUpSheet, 2, 0);
            //fillColumn("select *  from VW_CR_CUSTOMER_ID_TYPE ORDER BY CUSTOMER_ID_TYPE_NAME ASC", "CUSTOMER_ID_TYPE_NAME", "CUSTOMER_ID_TYPE_ID", stat, lookUpSheet, 4, 0);
            //fillColumn("select CITY_ENGLISH,CITY_CODE from GEN_CITY order by CITY_ENGLISH ASC","CITY_ENGLISH","CITY_CODE",stat,lookUpSheet,6,0);

            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            //HSSFSheet dataSheet = wb.getSheet("Data");
            //dataSheet.setSelected(true);
            //lookUpSheet.setSelected(false);
            //dataSheet.setSelected(true);
            //dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();

            //con.close();
            Utility.closeConnection(con);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * 4- this function save the html log of the import process in the database
     */
    public void saveHtmlLog(String filePath, String fileName) {
        try {
            con = Utility.getConnection();
            String dcmId = this.getDCMId();
            //debug("saving the html log");
            String insertSql = "insert into  cr_excel_import_log(excel_import_log_id,dcm_id,excel_import_log_date,file_name,EXCEL_LOG_HTML)values(SEQ_CR_EXCEL_IMPORT_LOG.nextval," + dcmId + ",sysdate ,'" + fileName + "',?)";
            //debug(insertSql);
            try {
//                System.out.println("filePath ss "+filePath);
                con.setAutoCommit(false);
                File file = new File(filePath);
                FileInputStream fis = new FileInputStream(file);
                PreparedStatement ps = con.prepareStatement(insertSql);
                ps.setBinaryStream(1, fis, (int) file.length());
                ps.executeUpdate();
                con.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Utility.closeConnection(con);
        } catch (Exception e) {
            //debug("problem in saving the html log");
            e.printStackTrace();
        }
    }

    /*
     * 5- this method is responsible of read a row statment strating from cell
     * index readStartCell and using the statmenttype to determine which kind of
     * statment it will read from the row object sent it to it then it stores
     * all the values read from that statment into the valuestable send to it as
     * a parameter
     */
    public boolean readRowStatement(String statementType, Hashtable valuesTable, Row row, int readStartCell) {
        Vector statusVec = (Vector) (statTable.get(statementType));
        if (statusVec == null) {
            //debug("the vector of stat of this type " + statementType + " is null");
            return true;
        }
        boolean statusImport = true;
        String invalidReadItem = null;

        for (int j = 0; j < statusVec.size(); j++) {
            Template_Def_Item defItem = (Template_Def_Item) statusVec.get(j);
            String tempReadValue = defItem.readItem(row, valuesTable, readStartCell);

            if (tempReadValue != null) {
                if (invalidReadItem != null) {
                    invalidReadItem += ", " + tempReadValue;
                } else {
                    invalidReadItem = tempReadValue;
                }
            }
        }
        //debug("--");

        if (invalidReadItem != null) {
            if ((statementType.compareTo("CONTRACT") == 0) && (this.currentSheetId != null)) {
                errorVector.add(new ErrorInfo(statementType + " on Sheet " + this.currentSheetSerial, row.getRowNum() + 1, "The following fields are empty, having corrupted date or exceeding field maximum size: " + invalidReadItem));
            } else {

                // errorVector.add("Error in "+statementType+" Import on row Number " + (row.getRowNum() + 1) +"  has the following fields empty or corrupted date in it " +invalidSheetReadItem);
                errorVector.add(new ErrorInfo(statementType, row.getRowNum() + 1, "The following fields are empty, having corrupted date or exceeding filed maximum size: " + invalidReadItem));
                return false;
            }
        } else {
            return true;
        }
        return false;
    }

    /*
     * public boolean readFile(String fileName) { try { FileInputStream tempIn =
     * new FileInputStream(fileName); HSSFWorkbook wb = new
     * HSSFWorkbook(tempIn); HSSFSheet sheet = wb.getSheetAt(0); int lastRowNum
     * = sheet.getLastRowNum() + 1; //Utility.logger.debug("Last Row Number = "
     * + lastRowNum); for(int i = 0;i < lastRowNum;i++) { HSSFRow row =
     * sheet.getRow(i); if(row != null) { HSSFCell cell = row.getCell((short)0);
     * if(cell != null) { String temp =
     * com.mobinil.sds.core.system.cr.excelImport.ExcelImport.readCell(cell); }
     *
     * // //Utility.logger.debug(cell.getStringCellValue()); } } return true; }
     * catch(Exception e) { e.printStackTrace(); return false; } }
     */
    /*
     * 6-get the dcm id of the dcm code of this sheet
     */
    public String getDCMId() {
        try {
            String dcmId = null;
            String sql = "select dcm_id from vw_gen_dcm where dcm_code = \'" + this.dcmCode + "\'";
            //debug(sql);
            Statement stat = con.createStatement();
            //   startT = System.currentTimeMillis();
            ResultSet res = stat.executeQuery(sql);
            //debug("time to get dcmid  = " + (System.currentTimeMillis() - startT));
            if (res.next()) {
                //errorVector.add("Error in sheet " +dcmField + "  on row " + rowNum +" is not valid");
                dcmId = res.getString("dcm_id");
            }
            res.close();
            stat.close();
            return dcmId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ExcelImport(String userId) {
        this.userId = userId;
    }
//Hashtable contractFormNumbersInExcelFile = new Hashtable();
    Statement globalRetrivalQuery = null;
    Statement globalRetrivalQuery2 = null;
    Statement validateSheetStatement = null;
    private static String lockString = "";

    /*
     * 7-import excel file taskes the file name as input also an error vector
     * that the method take to fill with any error reports generated also it
     * take an importReport object that it fill with the imoprted items
     */
    public synchronized boolean ImportFile(String fileName, Vector errorVector, ImportReport report) {
        System.out.println("import file %%%%%%%%%%%%");

        synchronized (lockString) {


            Statement stat = null;

            try {
                //debug("init connection");
                initConnection();

                stat = con.createStatement();

                //debug("init status values");
                initStatusValues(stat);


                //debug("init look up tables");
                initLookUpTables(stat);
            } catch (Exception e) {
                e.printStackTrace();
                //System.exit(-1);
                return false;
            }
            //debug("file Name = " + fileName);
            this.report = report;
            this.errorVector = errorVector;
            timeStamp = new Date();
            Hashtable sheetTable = new Hashtable();
            Hashtable contractTable = new Hashtable();
            timeStampS = timeStamp.getDate() + "\\" + (timeStamp.getMonth() + 1) + "\\" + (timeStamp.getYear() + 1900) + " " + timeStamp.getHours() + ":" + timeStamp.getMinutes() + ":" + timeStamp.getSeconds();
            try {
                /*
                 * getting the def statments from the database that define both
                 * contract and sheet statement
                 */

                //   globalRetrivalQuery = con.createStatement();
                //   globalRetrivalQuery2 = con.createStatement();
                //   validateSheetStatement = con.createStatement();
                //  validateCntrctStat  = con.createStatement();

                ResultSet res = stat.executeQuery("select * from VW_CR_TEMPLATE_DEF order by template_def_type_id,TEMPLATE_DEF_ORDER");
                String lastDefType = null;
                Vector statDefVec = null;
                statTable = new Hashtable();
                while (res.next()) {
                    String typeName = res.getString("TEMPLATE_DEF_TYPE_NAME");
                    String keyName = res.getString("TEMPLATE_DEF_NAME");
                    boolean mandatory = res.getString("TEMPLATE_DEF_MANDATORY").compareTo("1") == 0 ? true : false;
                    String productName = res.getString("PRODUCT_NAME");
                    int order = res.getInt("TEMPLATE_DEF_ORDER");
                    String type = res.getString("TEMPLATE_DEF_DATA_TYPE");
                    int maxSize = res.getInt("TEMPLATE_DEF_MAX_SIZE");

                    if (lastDefType == null || typeName.compareTo(lastDefType) != 0) {
                        if (lastDefType != null) {
                            statTable.put(lastDefType, statDefVec);
                        }
                        lastDefType = typeName;
                        statDefVec = new Vector();
                    }
                    Template_Def_Item defItem = new Template_Def_Item(keyName, mandatory, order, type, maxSize);
                    statDefVec.add(defItem);
                }
                res.close();

                //stat.close();


                if (lastDefType != null) {
                    statTable.put(lastDefType, statDefVec);
                }

                /*
                 * opening the excel file
                 */
                FileInputStream tempIn = new FileInputStream(fileName);

                Workbook wb = WorkbookFactory.create(tempIn);

                org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);

                int lastRowNum = sheet.getLastRowNum() + 1;
                //debug("Last Row Number = " + lastRowNum);
           /*
                 * verfiing that the dcm code exist then read it and verify it
                 * is a vaild one from database
                 */
                Row dcmRow = sheet.getRow(1);
                Cell dcmCell = dcmRow.getCell((short) 0);
                if (dcmCell == null || this.readCell(dcmCell).compareTo("") == 0) {
                    this.errorVector.add(new ErrorInfo("File", dcmRow.getRowNum(), "DCM Code is missing"));
                    Utility.closeConnection(con);
                    return true;
                } else {
                    dcmCode = this.readCell(dcmCell);
                    String sqlCheck = "select * from vw_gen_dcm where DCM_STATUS_NAME ='ACTIVE' and dcm_code='" + dcmCode + "'";
                    System.out.println("The check query issssssssss" + sqlCheck);
                    //    Statement checkDCMCodeStat = con.createStatement();
                    startT = System.currentTimeMillis();
                    ResultSet checkDCMCodeRes = stat.executeQuery(sqlCheck);
                    //debug("time to get checkDCMCodeRes = " + (System.currentTimeMillis() - startT));

                    if (!checkDCMCodeRes.next()) {
                        this.errorVector.add(new ErrorInfo("File", dcmRow.getRowNum(), "DCM Code Does Not Exist"));
                        checkDCMCodeRes.close();
                        //    checkDCMCodeStat.close();
                        stat.close();
                        Utility.closeConnection(con);
                        return true;
                    }
                    checkDCMCodeRes.close();
                    //checkDCMCodeStat.close();
                }

                /*
                 * raeding and parsing the excel file starting from row number 3
                 * until the last row
                 */
                int emptyRowCount = 0;
                Row row;
                Cell cell;
                boolean isItSheetStatement;
                for (int i = 3; i < lastRowNum; i++) {
//                debug("row num " + i);
                    row = sheet.getRow(i);

                    if (row != null) {
                        rowNum = row.getRowNum() + 1;

                        cell = row.getCell((short) sheetFirstCellNum);
                        isItSheetStatement = false;
                        /*
                         * this check if it is a sheet statment on this row by
                         * examining all the cells from start indiex of the
                         * sheet statment and if found any data on any cell then
                         * it is considered a sheet statment
                         *
                         */
                        for (int cellIndex = 0; cellIndex < this.contractFirstCellNum; cellIndex++) {
                            cell = row.getCell((short) (sheetFirstCellNum + cellIndex));
                            if (cell != null) {
                                if (readCell(cell).compareTo("") != 0) {
                                    isItSheetStatement = true;
                                    break;
                                }
                            }
                        }
                        if (isItSheetStatement) {
                            emptyRowCount = 0;
                            if (currentSheetId != null) {
                                //insering the previous sheet to the database since finding a new sheet
                                //indicate that the previous one have finished 
                                insertSheetToDB(stat);
                            }
                            //init variables 
                            currentSheetId = null;
                            this.currentSheetStatus = -1;
                            currentSheetHasNotImportedContracts = false;

                            sheetTable.clear();

                            //trying to read a sheet statment and returning its information on the row
                            // in the sheetTable object sent to it 
                            if (this.readRowStatement("SHEET", sheetTable, row, this.sheetFirstCellNum)) {
                                //checking the validity of this sheet statment 
                                //it make the sheet rejected if any of the values is not valid or missing 
                                boolean isSheetValid = validateSheet(sheetTable, row.getRowNum() + 1, stat);
                                //debug("isSheetValid = " + isSheetValid);
                                if (!isSheetValid) {
                                    this.currentSheetContractCount = 0;
                                    this.currentSheetId = null;
                                    this.currentSheetSerial = null;
                                    this.insertVector.clear();
                                    this.sheetWarning.clear();
                                }
                            }
                        }
                        /*
                         * checking if this row has a contract statment by
                         * reading all the cells from the start index of the
                         * contract statmenet ending afrer reading offset that
                         * is equal to the size of the vector that contrain the
                         * contract statment
                         */
                        boolean isItContractStatement = false;
                        for (int cellIndex = contractFirstCellNum; cellIndex < this.contractFirstCellNum + 8; cellIndex++) {
                            cell = row.getCell((short) (sheetFirstCellNum + cellIndex));
                            if (cell != null) {
                                if (readCell(cell).compareTo("") != 0) {
                                    isItContractStatement = true;
                                    break;
                                }
                            }
                        }
                        //cehcking if this is a contract that have no sheet assigned to it 
                        if (isItContractStatement) {
                            emptyRowCount = 0;
                            if (currentSheetId == null) {
                                this.errorVector.add(new ErrorInfo("CONTRACT", (row.getRowNum() + 1), " Sheet Was Not Imported"));
                            } else {
                                contractTable.clear();
                                if (this.readRowStatement("CONTRACT", contractTable, row, this.contractFirstCellNum)) {
                                    //debug("done reading");
                                    if (this.report.getSheet(currentSheetSerial).contractExist((String) contractTable.get(this.CONTRACT_MAIN_SIM_NO))) {
                                        this.errorVector.add(new ErrorInfo("CONTRACT on SHEET " + currentSheetSerial, (row.getRowNum() + 1), "Duplicate Contract In Sheet"));
                                        this.currentSheetHasNotImportedContracts = true;
                                    } else {

                                        boolean isContractValid = validateContract(contractTable, row, stat);
                                        //System.out.println("validate contract" + isContractValid);
                                        if (!isContractValid) {
                                            currentSheetHasNotImportedContracts = true;
                                        }
                                    }
                                } else {
                                    currentSheetHasNotImportedContracts = true;
                                }
                            }
                        }

                        if (isItContractStatement == false && isItSheetStatement == false) {
                            emptyRowCount++;
                            //debug("row is empty" );
                            if (emptyRowCount > 10)//if 10 rows are empty then assume the end of the sheet is reached 
                            {
                                //debug("breakkkk");
                                break;
                            }
                        }
                    } else {
                        //row is null
                        emptyRowCount++;
                        //debug("row is empty" );
                        if (emptyRowCount > 10)//if 10 rows are empty then assume the end of the sheet is reached 
                        {
                            //debug("breakkkk");
                            break;
                        }

                    }


                }
                //debug("before final insert");
                insertSheetToDB(stat);

//            validateCntrctStat.close();

                if (insertStat != null) {
                    this.insertStat.close();
                }

                Utility.closeConnection(con);

                //Utility.logger.debug("Total time needed to import = "+ (double)((System.currentTimeMillis() - start_time) / 1000));
                return true;
            } catch (Exception e) {
                //debug("last exception");
                e.printStackTrace();
                try {
                    Utility.closeConnection(con);
                } catch (Exception ee) {
                    e.printStackTrace();
                }
                return false;
            }
        }
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
                //debug("Error in cell reading");
                return "";

            case Cell.CELL_TYPE_FORMULA:
                //debug("Cell is a formula");
                return "";

            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue() + "";

            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue().trim();
            default:
                return "";
        }
    }


    /*
     * this is a test main a stand alone test of the class but it need database
     * connection to be done manually since utility.getconnection will not work
     */
    public static void main(String[] args) {
        //ExcelImport ExcelImport = new ExcelImport();
        //   ExcelImport.exportTemplate("c:/test/template.xls", "c:/test/sampleGenerated.xls");    
        //ExcelImport.readFile("c:/test/mm.xls");    
        //ExcelImport.ImportFile("c:/test/man.xls");
    }


    /*
     * 9-validate a sheet statment checking all values are not null and are
     * valid by validating them from the database and upon that set the
     * necessarly flags weither the sheet is accepted or rejected or an error
     * occur
     */
    private boolean validateSheet(Hashtable sheetInfo, int rowNum, Statement stat) throws Exception {
        sheetWarning = new Hashtable();
        /*
         * if(validateSheetStatement == null) { validateSheetStatement =
         * con.createStatement(); }
         */

        //verify that this sheet serial number exist
        currentSheetSerial = (String) sheetInfo.get(SHEET_SERIAL_FIELD);
        //debug("currentSheetSerial = " + currentSheetSerial);
        if (this.report.getSheet(currentSheetSerial) != null) {
            this.errorVector.add(new ErrorInfo("SHEET", rowNum + 1, "Duplicate Sheet serial number " + currentSheetSerial));
            return false;
        }
        sheetInfo.put(this.SHEET_DISTRIBUTER_ID, this.dcmCode);
        //Statement stat = validateSheetStatement;

        //verify the sheet type exist
        String sheetTypeId = "";
        String sheetContractCount = (String) sheetInfo.get(SHEET_CONTRACT_COUNT);
        if (sheetContractCount == null) {
            setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
            insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT + " Field: " + "Sheet Contract Count");
            sheetContractCount = "0";
        }
        currentSheetContractCount = (int) Float.parseFloat(sheetContractCount);

        //sheet Type verification 
        Object sheetTypeNameObj = sheetInfo.get(SHEET_TYPE_FIELD);
        //debug("sheet type name = " + sheetTypeNameObj);
        if (sheetTypeNameObj != null) {
            Object sheetTypeIdObj = sheetTypes.get((String) sheetTypeNameObj);
            if (sheetTypeIdObj != null) {
                sheetTypeId = (String) sheetTypeIdObj;
            } else {
                setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
                sheetInfo.put(SHEET_TYPE_FIELD, "");
                insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT + " Field: " + "Sheet Type Field");
            }
        } else {
            setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
            insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT + " Field: " + "Sheet Type Field");
        }



        //verify dcm id + pos id + super dealer      
        String sheetDistributerId = validateDCMId(SHEET_DISTRIBUTER_ID, "DISTRIBUTER", sheetInfo, rowNum, stat);
        if (sheetDistributerId == null) {
            setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
            insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT + " Field: " + "Sheet Distributer Code");
            sheetDistributerId = "";
        }
        //verify the super dealer code is a valid code 
        String sheetSuperDealerId = validateDCMId(SHEET_SUPER_DEALER_FIELD, "POS", sheetInfo, rowNum, stat);
        if (sheetSuperDealerId == null) {
            setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
            insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT + " Field: " + "Sheet Super Dealer Code");
            sheetSuperDealerId = "";
        }
        //verify that the pos code is a valid code 
        String sheetPosId = validateDCMId(SHEET_POS_FIELD, "POS", sheetInfo, rowNum, stat);
        if (sheetPosId == null) {
            setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
            insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT + " Field: " + "Sheet POS Code");
            sheetPosId = "";
        }
        //Ahmed Adel: verify that the second pos code is a valid code
        //  String sheetSecondPosId = validateDCMId(SHEET_SECOND_POS_FIELD,"POS", sheetInfo, rowNum,stat);
        // if(sheetSecondPosId == null) {
        //   setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
        // insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT +" Field: "+"Sheet Second POS Code");
        //sheetSecondPosId = "";
        //}
/*
         * String sheetRecieveDate = ""; if(sheetInfo.get(SHEET_RECIEVE_DATE) !=
         * null) { sheetRecieveDate = (String)sheetInfo.get(SHEET_RECIEVE_DATE);
         * } else { setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
         * insertSheetWarning(this.SHEET_WARNING_MISSING_DATA,
         * SHEET_WARNING_MISSING_DATA_TEXT +" Field: "+"SheetRecieveDate);; }
         */
        //puting the system date as the recieve date 
        // this was done this way to reduce changes since
        //origianly it was read from the excel file it self 
        String sheetRecieveDate = "sysdate";
        sheetInfo.put(SHEET_RECIEVE_DATE, sheetRecieveDate);


        //verifty that this sheet serial number beolong to this guy        
        //then perfom insertion + adding warning
        //    startT = System.currentTimeMillis();
        ResultSet res = stat.executeQuery("select * from VW_CR_DCM_SHEET where SHEET_SERIAL_ID= " + currentSheetSerial);
        //debug("time to validate sheet = "+ ((System.currentTimeMillis()-startT) ));
        if (res.next()) {
            //   Statement statq = con.createStatement();
            //          startT = System.currentTimeMillis();

            String sheetDCMInDatabase = res.getString("DCM_ID");

            res.close();


            res = stat.executeQuery("select SEQ_SHEET_ID.nextval from dual");
            //debug("time to get seq_sheet_id = " + (System.currentTimeMillis() - startT));
            res.next();
            this.currentSheetId = res.getString(1);
            //resq.close();
            //    statq.close();
            res.close();

            //debug("***************  sheet id =  " + currentSheetId);
            //String statSheetSerialVerifySQL = " select * from " + " (select sheet_status_type_name,sheet_serial_id from (select * from vw_cr_sheet_status order by sheet_Status_date desc)sheet_status , vw_cr_sheet where rownum<=1" + " and vw_cr_sheet.SHEET_ID = sheet_status.sheet_id)" + " where SHEET_SERIAL_ID = " + currentSheetSerial + " and sheet_Status_type_name not in " + SHEET_REJECTED_STATUS_SET;

            String statSheetSerialVerifySQL = " select count(*) sheet_record_count from vw_Cr_sheet_last_status where SHEET_SERIAL_ID= " + currentSheetSerial + " and sheet_Status_type_name not in " + SHEET_REJECTED_STATUS_SET;

            //debug(statSheetSerialVerifySQL);
            //     Statement statSheetSerialVerify = con.createStatement();
            startT = System.currentTimeMillis();
            ResultSet resVerify = stat.executeQuery(statSheetSerialVerifySQL);
            //debug("time to get resVerify = " + (System.currentTimeMillis() - startT ));
            //debug("&&&&&&&&&&&&&&&& contract count " + sheetContractCount);
            resVerify.next();
            int verifyCount = resVerify.getInt("sheet_record_count");
            resVerify.close();
            //      statSheetSerialVerify.close();

            //shee doesn't exist already in the db with a non rejected status


            if (verifyCount != 0) {
                //debug("verifyCount = " + verifyCount);
                //debug("sheet rejected cause it exist in the database with a status not rejected sheet id " + this.currentSheetId);

                //setCurrentSheetStatus(SHEET_REJECTED_IMPORT);

                // this.report.addSheet(this.currentSheetSerial,currentSheetSerial,Sheet.REJECTED,rowNum, Integer.parseInt(sheetContractCount));            
                this.errorVector.add(new ErrorInfo("SHEET", rowNum, "Sheet exists in the database with non rejected status"));
                this.insertVector.clear();

                this.report.sheetReports.remove(this.currentSheetSerial);
                this.currentSheetId = null;
                this.sheetWarning.clear();
                return false;

                //re init after discovering an error
            } else {
                setCurrentSheetStatus(SHEET_IMPORTED);
                this.report.addSheet(this.currentSheetSerial, currentSheetSerial, Sheet.IMPORTED, rowNum, (int) Float.parseFloat(sheetContractCount));
                if (this.currentSheetStatus == this.SHEET_REJECTED_IMPORT) {
                    this.report.changeSheetStatus(currentSheetSerial, Sheet.REJECTED);
                }
            }
            //debug("this.currentSheetStatus = " + this.currentSheetStatus);
            //debug("current sheet status = " + ((Sheet)this.report.getSheet(currentSheetSerial)).getStatusName());

            //if the sheet serial exist then we can insert it to the system
//            if((String)sheetInfo.get(SHEET_SECOND_POS_FIELD)==null)
            //          {
            //            sheetInfo.remove(SHEET_SECOND_POS_FIELD);
            //          sheetInfo.put(SHEET_SECOND_POS_FIELD,"");
            //    }
            // Ahmed Adel change in insert query add 4th code
            String sql = "insert into cr_SHEET (sheet_id, SHEET_DISTRIBUTER_code,SHEET_SUPER_DEALER_code,SHEET_POS_code,SHEET_TYPE_ID,SHEET_SERIAL_ID,SHEET_RECIEVE_DATE,sheet_time_stamp ,SHEET_CONTRACT_COUNT,phase_id) VALUES (" + this.currentSheetId + ",";
            sql += "\'" + (String) sheetInfo.get(SHEET_DISTRIBUTER_ID) + "\',\'" + (String) sheetInfo.get(SHEET_SUPER_DEALER_FIELD) + "\',\'" + (String) sheetInfo.get(SHEET_POS_FIELD) + "\','" + sheetTypeId + "'," + currentSheetSerial + "," + "sysdate," + "TO_DATE(\'" + timeStampS + "\',\'DD\\MM\\YYYY HH24:MI:ss\'),\'" + sheetContractCount + "'," + importPhaseId +/*
                     * ",'"+(String)sheetInfo.get(SHEET_SECOND_POS_FIELD)+
                     */ ")";

            //debug("insert sql ");            
            //debug(sql);

            /*
             * String sql = "insert into vw_cr_sheet ("; String valuesSql ="";
             *
             * Enumeration keys = sheetInfo.keys(); Enumeration values =
             * sheetInfo.elements();
             *
             * while (keys.hasMoreElements()) { String key = (String)
             * keys.nextElement(); String value =(String) values.nextElement();
             * sql += key + ","; valuesSql += "\'" + value + "\'," ; } sql =
             * sql.substring(0,sql.length()-1) + ") values("; valuesSql =
             * valuesSql.substring(0,valuesSql.length()-1) +")"; sql
             * +=valuesSql;
             */

            //Statement insertStatement = con.createStatement();        
            //debug("insert sql = " + sql);

            //boolean insertStatusFlag = insertStatement.execute(sql);

            // //debug("insert sheet flag = " +insertStatusFlag +"");
            insertVector.add(sql);
            if (this.getDCMId().compareTo(sheetDCMInDatabase) != 0) {
                Sheet sheet = this.report.getSheet(this.currentSheetSerial);
                if (sheet != null) {
                    sheet.warnings.put(SHEET_SERIAL_WARNING_ID, this.SHEET_SERIAL_BELONG_TO_ANOTHER_DCM_WARNING);
                }
            }
            //     res.close();
            return true;
        } else {
            errorVector.add(new ErrorInfo("Sheet", rowNum, "Sheet Serial Number " + currentSheetSerial + " does not exist"));
            this.insertVector.clear();
            this.report.sheetReports.remove(this.currentSheetSerial);
            this.sheetWarning.clear();
            this.currentSheetId = null;
            //re initialize
            res.close();
            return false;
        }
    }
    static final String validateContractSQL = "SELECT cr_contract.contract_id,status_type_name FROM CR_CONTRACT, CR_CONTRACT_STATUS, CR_CONTRACT_sTATUS_TYPE, CR_STATUS_TYPE WHERE CR_CONTRACT.CONTRACT_LAST_STATUS_ID = CONTRACT_STATUS_ID   AND CR_CONTRACT_sTATUS_TYPE.CONTRACT_STATUS_TYPE_ID = CR_CONTRACT_STATUS.CONTRACT_STATUS_TYPE_ID AND CR_STATUS_TYPE.STATUS_TYPE_ID = CR_CONTRACT_STATUS_TYPE.TYPE_ID AND CONTRACT_MAIN_sIM_NO_LCS = '";
    /*
     * 10-validate a contract statment and form the necessary sql to insert it
     * to the database if it was a valid contract and also calculate the status
     * of the contract and add necessary warning
     */

    private boolean validateContract(Hashtable infoTable, Row row, Statement stat) throws Exception {
        //debug("validate contract");
        Object idTypeObj = infoTable.get(Id_No_Field);

        String idNumber = (String) infoTable.get(CUSTOMER_ID_NO);
        String idType = (String) infoTable.get(Id_No_Field);
        // Medhat 26-9-2011
        String contractTypeString = "";
        Object contractTypeObj = infoTable.get(CONTRACT_TYPE_FIELD);
        if (contractTypeObj != null) {
            contractTypeString = (String) this.productTypes.get((String) contractTypeObj);
        }

        System.out.println("contractTypeString isssssssss" + contractTypeString);

        //System.out.println("############################## id Type" + idType);
        //System.out.println("############################## id Number" + idNumber);

        /*
         * if type of id not passport and not national number return false
         */

        //System.out.println("######");
        //System.out.println("idType= "+idType);
        try {
            //Medhat 26-9-2011
            if (contractTypeString.compareTo("43") == 0) {
                System.out.println("SIM SWAP");
                if (idType.compareTo("Passport") != 0 && idType.compareTo("National Number") != 0
                        && idType.compareTo("Weapon License") != 0 && idType.compareTo("Police ID") != 0
                        && idType.compareTo("Judges ID") != 0 && idType.compareTo("Military ID") != 0
                        && idType.compareTo("Driving License") != 0) {
                    this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Invalid ID Type"));
                    return false;
                } else if (idType.compareTo("National Number") == 0) {
                    try {
                        BigDecimal value = new BigDecimal((String) infoTable.get(CUSTOMER_ID_NO));

                        //System.out.println("The id number length issssssssss " + idNumber.length());

                        if (idNumber.length() != 14) {
                            this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not valid Customer ID NO Length"));
                            return false;
                        }
                    } catch (Exception e) {
                        //   e.printStackTrace();
                        this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not valid Customer ID NO"));
                        return false;
                    }

                }
            } //System.out.println ("contractTypeString isssssss "+ contractTypeString);
            //Medhat 26-9-2011
            else if (contractTypeString.compareTo("43") != 0) {
                if (idType.compareTo("Passport") != 0 && idType.compareTo("National Number") != 0) {
                    this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " ID Type Must Be Passport or National Number"));
                    return false;
                } else if (idType.compareTo("National Number") == 0) {
                    try {
                        BigDecimal value = new BigDecimal((String) infoTable.get(CUSTOMER_ID_NO));

                        //System.out.println("The id number length issssssssss " + idNumber.length());

                        if (idNumber.length() != 14) {
                            this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not valid Customer ID NO Length"));
                            return false;
                        }
                    } catch (Exception e) {
                        //   e.printStackTrace();
                        this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not valid Customer ID NO"));
                        return false;
                    }

                }
            }
        } catch (Exception e) {
            //   e.printStackTrace();
            this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not valid Customer ID Type"));
            return false;
        }

        //hagry
        boolean rejectContract = false;

        String contractMainSimNo = (String) infoTable.get(CONTRACT_MAIN_SIM_NO);

        //String contractFormNumber = (String) infoTable.get(CONTRACT_FORM_NUMBER);

        if (!(contractMainSimNo.length() == 20 || contractMainSimNo.length() == 24)) {

            this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not valid Main Sim No"));
            return false;

        }



        /*
         * we dont need it any more since it is done from the database
         * if(contractFormNumber.length() > 12) { this.errorVector.add(new
         * ErrorInfo("CONTRACT", this.rowNum, " Not valid Contract Form
         * Number")); return false; }
         */

        /*
         * the whole code in this lines is to verify that the contract dial
         * number is exactly 9 character and that it is a number the reason of
         * using big decimal is that we faced problems wth number such as
         * 129999999
         */
        try {

            BigDecimal value = new BigDecimal((String) infoTable.get("CONTRACT_DIAL_NO"));
            //BigInteger value = Long.valueOf();

            String valueS = value.toString();
            //   Utility.logger.debug(valueS);
            // infoTable.put("CONTRACT_DIAL_NO",valueS);


            //this is to check that the lenght of the dial number is exaclty 9 digits
            // if (valueS.length() != 9 ||((String)infoTable.get("CONTRACT_DIAL_NO")).length()<9 ) {
            // Update for NDC medhat
            if (((String) infoTable.get("CONTRACT_DIAL_NO")).length() < 9) {
                this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not Valid CONTRACT DIAL NO"));
                //         Utility.logger.debug(valueS);
                return false;
            }

            if (valueS.indexOf(".") != -1) {
                this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not Valid CONTRACT DIAL NO"));
                return false;
            }

        } catch (Exception e) {
            //   e.printStackTrace();
            this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not valid CONTRACT DIAL NO"));
            return false;
        }









        /*
         * check if the id type (personal identification of a customer) is a
         * valid one or not and if it is a valid one it gets its value form the
         * database or else remove it form the infotable so it be inserted as
         * null
         */
        if (idTypeObj != null) {
            String idTypeString = (String) this.idTypes.get((String) idTypeObj);
            if (idTypeString != null) {
                //debug("**************");
                //debug(idTypeString);

                infoTable.put(Id_No_Field, idTypeString);
            } else {
                //  rejectContract = true;                
                ////debug("**********");                
                //   //debug("invalid contract because of invalid id type " +idTypeObj );

                infoTable.remove(Id_No_Field);
            }
        }
        //puting the phase of import 
        infoTable.put("phase_id", importPhaseId + "");

        //infoTable.put("sheet_id",this.currentSheetId);
        //Medhat 26-9-2011
        //Object contractTypeObj = infoTable.get(CONTRACT_TYPE_FIELD);
        //if(contractTypeObj != null) {
        //String contractTypeString = (String)this.productTypes.get((String)contractTypeObj);

        //debug("contract type = " + contractTypeString);
        if (contractTypeString != null) {
            infoTable.put(CONTRACT_TYPE_FIELD, contractTypeString);
        } else {
            rejectContract = true;
            //debug("**********");
            //debug("invalid contract because of invalid contract type");
            infoTable.remove(CONTRACT_TYPE_FIELD);
        }
        //Medhat 26-9-2011
        //}
        infoTable.put(this.SHEET_ID, this.currentSheetId);
        //debug("get next val contract_id");
        //   Statement stat = con.createStatement();
        //   startT = System.currentTimeMillis();
        ResultSet res = stat.executeQuery("select SEQ_CR_CONTRACT_ID.nextval from dual");
        //debug("time to get contract id seq = " + (System.currentTimeMillis()- startT));
        res.next();
        String currentContractId = res.getString(1);
        res.close();
        //   stat.close();
        infoTable.put(CONTRACT_ID, currentContractId);
        //String sql = "insert into vw_CR_contract_inst (";

        String contractMainSimNoLCS = null;
        //System.out.println("LCS validation " + (contractMainSimNo.length()>20));


        //solution 2
        //System.out.println("sim before update "+contractMainSimNo );
        if (contractMainSimNo.length() > 20) {
            contractMainSimNoLCS = contractMainSimNo;


        } else {
            contractMainSimNoLCS = contractMainSimNo.substring(0, 19);
            //System.out.println("contractMainSimNoLCS issssssssssssssss " + contractMainSimNoLCS);
        }

        //solution 2
        //contractMainSimNoLCS = contractMainSimNoLCS.substring(1);

        //System.out.println("sim after update "+contractMainSimNoLCS);
        String sql = "insert into CR_contract (";
        String valuesSql = "";
        Enumeration keys = infoTable.keys();
        Enumeration values = infoTable.elements();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = (String) values.nextElement();
            sql += key + ",";
            if (key.compareTo("CONTRACT_CUSTOMER_BIRTH_DATE") != 0) {
                valuesSql += "\'" + value + "\',";
            } else {
                valuesSql += "TO_DATE(\'" + value + "\',\'dd/mm/yyyy\'),";
            }
        }
        sql += "CONTRACT_MAIN_SIM_NO_LCS,";
        sql = sql.substring(0, sql.length() - 1) + ") values(";

        //solution 1
        //sql+=valuesSql+contractMainSimNoLCS+")";
        sql += valuesSql + "'" + contractMainSimNoLCS + "')";

        System.out.println("insert sql =");
        System.out.println(sql);
//        valuesSql = valuesSql.substring(0, valuesSql.length() - 1) + ")";
//        sql += valuesSql;
        //System.out.println("the insert statement isssssssssssssss \n" + sql+"\n");
        //System.out.println(validateContractSQL +contractMainSimNoLCS +"'" +" ORDER BY CONTRACT_STATUS_DATE DESC \n");
        //System.out.println("Contract Main SIM = "+contractMainSimNo+" Contract SIM In LSC"+contractMainSimNoLCS+"\n");


        // //debug("get next val status_contract_id");

        //Statement statQ  =stat ;

        //ResultSet resQ = statQ.executeQuery("select SEQ_CR_CONTRACT_STATUS.nextval from dual");

        //resQ.next();

        //String contractStatusId = resQ.getString(1);
        infoTable.put(CONTRACT_MAIN_SIM_NO, "'" + contractMainSimNo + "'");
        int contractStatus;

        //Utility.logger.debug("QQQQQ : "+sql);
        Contract contract = this.report.addContract(this.currentSheetSerial, currentContractId, contractMainSimNo, (row.getRowNum() + 1), userId);
        contract.setInsertContractSQL(sql);
        contract.setTimeStamp(this.timeStamp);


        contractStatus = CONTRACT_IMPORT_STATUS;
        contract.setContractStatus(Contract.IMPORTED);

        //this was removed due to performance problems and replaced with new optimized code
        //String validateContractSQL = " select * from vw_Cr_contract_last_status where CONTRACT_Status_type_name not in " + CONTRACT_REJECTED_STATUS_SET + " and contract_main_sim_no ='" + contractMainSimNo + "\'";        
        //debug("validateContractSQL " + validateContractSQL);

        startT = System.currentTimeMillis();
        ///////////// debug /////////////
//        String contractMainSimNoLCS = null;
//        System.out.println("LCS validation " + (contractMainSimNo.length()>20));
//        
//        if (contractMainSimNo.length()>20) {
//            contractMainSimNoLCS=contractMainSimNo;
//        }
//        else {
//          contractMainSimNoLCS =   contractMainSimNo.substring(0,19);
//        }
//        
        ////////////////end drbug ////////////
//        System.out.println(validateContractSQL +contractMainSimNoLCS +"'" +" ORDER BY CONTRACT_STATUS_DATE DESC ");
//        System.out.println("Contract Main SIM = "+contractMainSimNo+" Contract SIM In LSC"+contractMainSimNoLCS);
        //Utility.logger.debug("Contract Main SIM = "+contractMainSimNo+" Contract SIM In LSC"+contractMainSimNoLCS);

        //System.out.println(validateContractSQL +contractMainSimNoLCS +"'" +" ORDER BY CONTRACT_STATUS_DATE DESC ");



        res = stat.executeQuery(validateContractSQL + contractMainSimNoLCS + "'" + " ORDER BY CONTRACT_STATUS_DATE DESC ");


        //debug("time to execute validate contract = "+ ((System.currentTimeMillis()-startT) ));

        boolean validFlag = true;

        if (res.next()) {
            String statusTypeName = res.getString("status_type_name");
            if (statusTypeName.compareTo("PASS") == 0) {
                validFlag = false;
                //the contract exist in the database with a not rejected status 
            }
        } else {
            //the contract doesn't exist in the database so it is still valid
        }

        res.close();

        //if(res.next()) {
        if (!validFlag) {
            //this should not be importd and error should be displayed
            this.errorVector.add(new ErrorInfo("CONTRACT", (row.getRowNum() + 1), " exist in the database with not rejected status"));
            this.report.getSheet(currentSheetSerial).contracts.remove(currentContractId);
            this.currentSheetHasNotImportedContracts = true;
            return false;
        }

        /*
         * this code is to read the sim-swap and add it to the contract
         * information
         */
        Hashtable extraParameters = new Hashtable();
        //debug((String)contractTypeObj);
        if (contractTypeObj != null && ((String) contractTypeObj).compareTo("SIM-SWAP") == 0) {
            boolean flag = this.readRowStatement("SIM-SWAP", extraParameters, row, this.contractFirstCellNum);
            //debug("flag = " + flag);
        }
        Enumeration optParamkeys = extraParameters.keys();
        Enumeration optParamvalues = extraParameters.elements();
        while (optParamkeys.hasMoreElements()) {
            String fieldName = (String) optParamkeys.nextElement();
            String fieldValue = (String) optParamvalues.nextElement();
            //    debug("field name " + fieldName + " fieldValue = " + fieldValue);
            Object paramObj = optParamsTypes.get(fieldName);
            if (paramObj != null) {
                //   debug("param obj = null");
                String fieldID = (String) paramObj;

                sql = "insert into cr_contract_opt_param (CONTRACT_OPT_PARAM_ID, CONTRACT_OPT_PARAM_TYPE_ID, CONTRACT_OPT_PARAM_VALUE,CONTRACT_ID)values(SEQ_CR_CONTRACT_OPT_PARAM_ID.nextval," + fieldID + "," + currentContractId + ",\'" + fieldValue + "')";

                contract.setInsertContractOptSQL(sql);
            }
        }
        //  debug ("rejectContract = true" );
        if (rejectContract) {
            contract.setContractStatus(Contract.REJECTED);
            contract.setWarningId(this.CONTRACT_WARNING_INVALID_CONTRACT_DETAILS);
            contract.setWarningText(this.CONTRACT_WARNING_INVALID_CONTRACT_DETAILS_TEXT);
        }
        return true;
    }

    /*
     * 11-this method set the current sheet status also it keep certin rules
     * such as a rejectede sheet can't be set back to imported
     */
    private void setCurrentSheetStatus(int status) {
        if (currentSheetStatus == this.SHEET_REJECTED_IMPORT) {
            return;
        } else {
            if (currentSheetStatus == SHEET_INCOMPLETE) {
                if (status == this.SHEET_REJECTED_IMPORT) {

                    //setCurrentSheetStatus( status);
                    currentSheetStatus = SHEET_REJECTED_IMPORT;
                }
            } else {
                currentSheetStatus = status;
            }
        }
    }

    /*
     * 12- init all status values from the database that will be used through
     * the import process
     */
    private void initStatusValues(Statement stat) {
        try {
            //Statement stat = con.createStatement();
            //import phase id 
            ResultSet res = stat.executeQuery("select * from VW_CR_PHASE where phase_name = 'IMPORT'");
            res.next();
            this.importPhaseId = res.getInt("PHASE_ID");
            res.close();

            res = stat.executeQuery("select * from vw_cr_sheet_status_type where phase_id = " + this.importPhaseId);
            while (res.next()) {
                String statusName = res.getString("sheet_status_type_name");
                //status types of a sheet                
                int value = res.getInt("sheet_status_type_id");
                if (statusName.compareTo("NEW") == 0) {
                    this.SHEET_IMPORTED = value;
                    //debug("sheet_imported is set");
                } else {
                    if (statusName.compareTo("INCOMPLETE") == 0) {
                        this.SHEET_INCOMPLETE = value;
                        //debug("sheet_incomplete is set");
                    } else {
                        if (statusName.compareTo("REJECTED IMPORT") == 0) {
                            this.SHEET_REJECTED_IMPORT = value;
                            //debug("sheet_rejected_import is set");
                        }
                    }
                }
            }
            res.close();

            //sheet warning ids for this phase 
            res = stat.executeQuery("select * from vw_cr_sheet_warning_type where phase_id = " + importPhaseId);
            while (res.next()) {
                String name = res.getString("sheet_warning_type_name");
                String id = res.getString("sheet_warning_type_id");
                if (SHEET_WARNING_COUNT_MISMATCH_TEXT.compareTo(name) == 0) {
                    SHEET_WARNING_COUNT_MISMATCH = id;
                } else {
                    if (SHEET_WARNING_MISSING_DATA_TEXT.compareTo(name) == 0) {
                        SHEET_WARNING_MISSING_DATA = id;
                    } else {
                        if (SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS_TEXT.compareTo(name) == 0) {
                            SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS = id;
                        }
                    }
                }
            }
            res.close();
            //contract system warnings for the import phase
            res = stat.executeQuery("select * from vw_cr_Contract_warning_type where phase_id = " + importPhaseId);
            while (res.next()) {
                String name = res.getString("contract_warning_type_name");
                String id = res.getString("contract_warning_type_id");
                if (CONTRACT_WARNING_REJECTED_SHEET_TEXT.compareTo(name) == 0) {
                    CONTRACT_WARNING_REJECTED_SHEET_ID = id;
                } else {
                    if (CONTRACT_WARNING_INVALID_CONTRACT_DETAILS_TEXT.compareTo(name) == 0) {
                        CONTRACT_WARNING_INVALID_CONTRACT_DETAILS = id;
                    }
                }

            }

            res.close();

            //contract status 
            res = stat.executeQuery("select * from vw_cr_contract_status_type where contract_status_phase_id = " + importPhaseId);
            while (res.next()) {
                String contractStatusName = res.getString("contract_status_type_name");
                int contractStatusId = res.getInt("contract_status_type_id");
                //debug("init contract status = " + contractStatusName);
                if (contractStatusName.compareTo("IMPORTED") == 0) {
                    this.CONTRACT_IMPORT_STATUS = contractStatusId;
                    Contract.IMPORTED = contractStatusId;
                    //debug("contract import status was set to  " + contractStatusId);
                } else {
                    if (contractStatusName.compareTo("REJECTED IMPORT") == 0) {
                        this.CONTRACT_REJECT_STATUS = contractStatusId;
                        Contract.REJECTED = contractStatusId;
                        //debug("contract rejct status was set to  " + contractStatusId);
                    }
                }
            }
            res.close();

            String getSheetWarning = "select sheet_warning_type_id from vw_cr_sheet_warning_type where sheet_warning_type_name = \'" + SHEET_SERIAL_BELONG_TO_ANOTHER_DCM_WARNING + "\'";
            //debug(getSheetWarning);
            res = stat.executeQuery(getSheetWarning);
            if (res.next()) {
                SHEET_SERIAL_WARNING_ID = res.getString(1);
            }
            res.close();


            //stat.close();
        } catch (Exception e) {
            //e.printStackTrace();                
        }
    }

    /*
     * 13- init look up tables
     */
    private void initLookUpTables(Statement stat) throws Exception {
        // Statement stat = con.createStatement();
        sheetTypes = fillHashTableWithResultSet(stat, "select SHEET_TYPE_NAME, SHEET_TYPE_ID from VW_CR_SHEET_TYPE", "SHEET_TYPE_NAME", "SHEET_TYPE_ID");
        productTypes = fillHashTableWithResultSet(stat, "select PRODUCT_NAME, PRODUCT_ID from VW_GEN_PRODUCT ", "PRODUCT_NAME", "PRODUCT_ID");
        optParamsTypes = fillHashTableWithResultSet(stat, "select * from cr_contract_opt_param_type", "CONTRACT_OPT_PARAM_TYPE_NAME", "CONTRACT_OPT_PARAM_TYPE_ID");
        idTypes = fillHashTableWithResultSet(stat, "select *  from VW_CR_CUSTOMER_ID_TYPE", "customer_id_type_name", "customer_id_type_ID");
        //  stat.close();
        //sheetWarnings = fillHashTableWithResultSet(stat,"select SHEET_WARNING_TYPE_NAME, SHEET_WARNING_TYPE_ID  from VW_CR_SHEET_WARNING_TYPE  where phase_id = " + importPhaseId , "SHEET_WARNING_TYPE_NAME" , "SHEET_WARNING_TYPE_ID ");
    }

    /*
     * 1-init database connection
     */
    private void initConnection() throws Exception {
        //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        //   con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
        con = Utility.getConnection();
    }

    /*
     * 14- fillHashTableWithResultSet take a sql and run it and fill the results
     * in a hashtable and retur
     */
    private Hashtable fillHashTableWithResultSet(Statement stat, String sql, String key, String value) throws Exception {
        //debug("sql " + sql);
        Hashtable table = new Hashtable();
        ResultSet resultSet = stat.executeQuery(sql);
        while (resultSet.next()) {
            table.put(resultSet.getString(key).trim(), resultSet.getString(value).trim());
        }
        resultSet.close();
        return table;
    }

    /*
     * 15-insert sheet warningfor the current sheet
     */
    private void insertSheetWarning(String warningId, String warningText) {
        if (this.sheetWarning.containsKey(warningId)) {
        } else {
            sheetWarning.put(warningId, warningText);
        }
    }

    /*
     * 16-validate dcm id
     */
    private String validateDCMId(String dcmField, String dcmLevel, Hashtable sheetInfo, int rowNum, Statement stat) throws Exception {
        //debug("validate dcm id");
        Object dcmCodeObj = sheetInfo.get(dcmField);
        if (dcmCodeObj == null) {
            errorVector.add(new ErrorInfo("Sheet", rowNum, "Error in sheet " + dcmField));
            return null;
        }
        String dcmCode = (String) dcmCodeObj;

        //to validate that this dcm code match for a dcm in the system and return the status 

        //Statement stat = con.createStatement();
        String dcmId = null;

        String sql = "select dcm_id from  vw_Gen_dcm_level, vw_gen_dcm where "
                + "  dcm_code = \'" + dcmCode + "\'"
                + " and vw_gen_dcm.DCM_LEVEL_ID = vw_Gen_dcm_level.DCM_LEVEL_ID  ";


        if (dcmLevel.compareTo("DISTRIBUTER") == 0) {
            sql += " and dcm_level_name ='DISTRIBUTER' ";
        } else if (dcmLevel.compareTo("SUPER DEALER") == 0) {
            //commented because of the franchise 
            //sql += " and dcm_level_name ='SUPER DEALER' " ;
        } else if (dcmLevel.compareTo("POS") == 0) {
            //commented because of the franchise
            // sql += " and dcm_level_name in ('SUPER DEALER','POS') " ;
        }



        ResultSet res = stat.executeQuery(sql);


        if (res.next()) {
            dcmId = res.getString("dcm_id");
        }
        if (dcmId == null) {
            errorVector.add(new ErrorInfo("Sheet", rowNum, "Error in sheet " + dcmField));
        }
        res.close();
        //stat.close();
        return dcmId;
    }


    /*
     * 17- fill a column in the sheet object using the data that result from
     * query the dataabse with the sql sent to the method
     */
    private boolean fillColumn(String sql, String culName, String culIDName, Statement stat, org.apache.poi.ss.usermodel.Sheet sheet, int colNumber, int startRow) {

        System.out.println("fill Column " + sql);
        System.out.println(" colNumber   " + colNumber);

        try {
            ResultSet resultSet = stat.executeQuery(sql);
            int index = startRow;
            while (resultSet.next()) {

                Row row = null;

                row = sheet.getRow(index);

                if (row == null) {

                    row = sheet.createRow(index);
                }
                Cell cell = null;
                cell = row.createCell((short) colNumber);
                cell.setCellValue((String) resultSet.getString(culName));
                System.out.print((String) resultSet.getString(culName));
                cell = row.createCell((short) (colNumber + 1));
                cell.setCellValue((String) resultSet.getString(culIDName));
                System.out.print((String) resultSet.getString(culIDName));
                index++;
            }

            resultSet.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * 18- this is a debuging function taht print debuging information from the
     * class if the debug flag is set to true however make sure to set the
     * debugFlag to false after debugin casue it reduce performance of this
     * module
     */
    private static void debug(String s) {
        if (debugFlag) {
            //Utility.logger.debug(s);
            System.out.println(s);
        }
    }
}

/*
 * this is a class act as a holder for the template item defined in the database
 * taht is being used by the import engine 1- read the item from the row object
 * 2-get the item name 3-constructor takes the name and the mandatory flag and
 * the order and the type
 */
class Template_Def_Item {

    boolean mandatory; //if an item is mandatory and no data were found and error is reported 
    int order; // the order of the item in the reading 
    String type; // the type of the item wither it is a date or number of a string 
    String name; //the name of this item
    int maxSize;


    /*
     * 1- read the item from the row object
     */
    public String readItem(Row row, Hashtable valueTable, int initialStartReadCol) {

        //Utility.logger.debug("reading cell " + this.name);
        Cell cell = row.getCell((short) (order + initialStartReadCol));
        if (cell == null && mandatory) {
            //  Utility.logger.debug(" the celll is empty "+ row.getRowNum());
            return name;
        } else {
            String value = com.mobinil.sds.core.system.cr.excelImport.ExcelImport.readCell(cell);
            //Utility.logger.debug(name + " , " + value +"||");

            /*
             * this condition to solve the bug that was introduced during to
             * names with ' in it
             */
            if (value != null && value.indexOf('\'') != -1) {
                return name;
            }

            if (value == null || value.compareTo("") == 0) {
                if (this.mandatory) {
                    return name;
                } else {
                    return null;
                }
            } else {

                if (value.length() > this.maxSize) {
                    //Utility.logger.debug("greater than the max size  max =" +maxSize +"    value="+ value.length() +"== " + value);
                    return name;
                }

                if (this.type.compareTo("D") == 0) /*
                 * date type
                 */ {
                    try {
                        if (Date.parse(value) < 0) {
                            return null;
                        }
                    } catch (Exception e) {
                        return name;
                    }
                } else {
                    if (this.type.compareTo("N") == 0) {
                        try {
                            Float.parseFloat(value);
                        } catch (NumberFormatException e) {
                            //    Utility.logger.debug(value);
                            //        e.printStackTrace();
                            return name;
                        }
                    }
                }
                valueTable.put(name, value);

                //   Utility.logger.debug("value = " + value);
                return null;
            }
        }
    }

    /*
     * 2-get the item name
     */
    public String getName() {
        return name;
    }

    /*
     * 3-constructor takes the name and the mandatory flag and the order and the
     * type
     */
    Template_Def_Item(String name, boolean mandatory, int order, String type, int maxSize) {
        this.name = name;
        this.mandatory = mandatory;
        this.order = order;
        this.type = type;
        this.maxSize = maxSize;
    }
}

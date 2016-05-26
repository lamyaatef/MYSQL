package com.mobinil.sds.core.system.cam.excelImport;
/*
* Thie file handel the excel import operations the following functionality in it
 * 1-init database connection
 * 2- insert sheet informatino in the database 
 * 3-this functions is responsible of generating the template
     * it take two input the template path file and the generated file path
     * it uses the template to copy it to the genreated and fill all
     * the look up sheet so the combo box can work fine 
     * the intial structure and lay out of the template is all 
     * done manually in teh template file it self 
* 4- this function save the html log of the import process in the database
* 5- this method is responsible of read a row statment strating from cell index readStartCell
 * and using the statmenttype to determine which kind of statment it will read
 * from the row object sent it to it
 * then it stores all the values read from that statment into the valuestable send to it as a parameter
* 6-get the dcm id of the dcm code of this sheet  
* 7-import excel file taskes the file name as input also an error vector that the method take to fill with 
     * any error reports generated 
     * also it take an importReport object that it fill with the imoprted items
* 8- read a HSSFCell object and return its value as String
* 9-validate a sheet statment 
     * checking all values are not null 
     * and are valid by validating them from the database 
     * and upon that set the necessarly flags weither the sheet is accepted or rejected or an error occure
* 10-validate a contract statment and form the necessary sql to insert it to the database
    * if it was a valid contract 
    * and also calculate the status of the contract and add necessary warning
* 11-this method set the current sheet status 
     * also it keep certin rules
     * such as a rejectede sheet can't be set back to imported
* 12- init all status values from the database that will be used through the import process
* 13- init look up tables
* 14- fillHashTableWithResultSet take a sql and run it and fill the results in a hashtable and retur 
* 15-insert sheet warningfor the current sheet
* 16-validate dcm id
* 17- fill a column in the sheet object using the data that result from query the dataabse with the sql sent to the method
* 18- this is a debuging function taht print debuging information from the class if the debug flag is set to true
 *    however make sure to set the debugFlag to false after debugin casue it reduce performance of this module 
*/
import java.io.*;
import java.lang.*;
import java.util.*;
import java.sql.*;
import java.util.Date;
import com.mobinil.sds.core.utilities.*;
import java.text.*;

import com.mobinil.sds.core.system.cam.memo.dao.MemoDAO;
import com.mobinil.sds.core.system.cam.memo.model.MemoMembersModel;
import com.mobinil.sds.core.system.cam.memo.model.MemoModel;
import com.mobinil.sds.core.system.cam.memo.model.MemoReasonModel;
import com.mobinil.sds.core.system.cam.payment.dao.PaymentScmStatusDao;
import com.mobinil.sds.core.system.cam.payment.model.PaymentScmStatusModel;
import com.mobinil.sds.core.system.cr.excelImport.Contract;
import com.mobinil.sds.core.system.cr.excelImport.ImportReport;
import com.mobinil.sds.web.interfaces.cam.MemoInterfaceKey;

import java.math.*;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

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

    private String userId ;


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
     * Data base connection settings
     * should only be used when testing just excel functinality with no deployment 
     */
   /* private static final String dbConnectionString = "jdbc:oracle:thin:@10.1.17.30:1521:sds";
    private static final String dbUserName = "sds";
    private static final String dbPassword = "sds01";
    */

    /*
     * reading excel columns numbers settings
     * contractFirstCellNum indicate the index of the first cell taht has contrat info 
     * as for 
     * sheetFirstCellNum indicate the index of first call that has sheet info 
     */
    private static final int contractFirstCellNum = 7;
    private static final int sheetFirstCellNum = 0;

    //set this debug flag to true for debuging 

    private static final boolean debugFlag = false  ;

    private static Statement validateCntrctStat = null; 
    
    long startT  =0;
    //for the rownumber of the dcm details search lis
    private int currentRow; 
    
  /*
   * 2- insert sheet informatino in the database 
   */
//    public void insertSheetToDB(Statement stat) {
//      Statement retrivalQuery = null;
//        //debug("insert Sheet To Db");
//        if(currentSheetId == null) {
//            insertVector.clear();
//            return ;
//        }
//        Sheet sheet = this.report.getSheet(currentSheetSerial);
//        try {
//
//        //    Statement statQ = con.createStatement();
//       //     retrivalQuery = con.createStatement();
//            
//            //startT = System.currentTimeMillis();
//
//            ResultSet resQ = stat.executeQuery("select SEQ_CR_SHEET_STATUS.nextval from dual");
//
//            //debug("time to get sheet seq = " + (System.currentTimeMillis() - startT));
//            resQ.next();
//            String sheetStatusId = resQ.getString(1);
//            
//            
//            String sheetStatus;
//            if(this.currentSheetHasNotImportedContracts && this.currentSheetStatus != SHEET_REJECTED_IMPORT) {
//                if(sheet.contracts.size() == 0) {
//                    
//                    //  setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);                
//                    
//                    //  this.report.changeSheetStatus(this.currentSheetSerial,Sheet.REJECTED);
//                    errorVector.add(new ErrorInfo("Sheet", sheet.getRowNum(), "Sheet Serial Number " + currentSheetSerial + " has no contracts"));
//                    this.insertVector.clear();
//                    this.report.sheetReports.remove(this.currentSheetSerial);
//                    this.sheetWarning.clear();
//                    this.insertVector.clear();
//                    return ;
//                }
//                else {
//                     //check if the sheet has not a single contract imported so it can be rejected and not imported
//                    if(currentSheetHasNotImportedContracts) {
//                     //   setCurrentSheetStatus(SHEET_INCOMPLETE);
//                        this.report.changeSheetStatus(this.currentSheetSerial, Sheet.REJECTED);
//                        setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
//                        this.sheetWarning.put(this.SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS, this.SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS_TEXT);
//                    }
//                }
//            }
//            else {
//                if(this.currentSheetStatus != SHEET_REJECTED_IMPORT) {
//                    //check if the number of contracts that got imported is differnt from the total number reported 
//                    //so taht the status of the sheet be rejected
//                    if((sheet.contracts.size()) != this.currentSheetContractCount || sheet.contracts.size() == 0) {
//                        //debug("sheet rejected cause no contracts under it or cause the contract count is not the same as contracts imported sheet id =" + this.currentSheetId);
//                        setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
//                        this.report.changeSheetStatus(this.currentSheetSerial, Sheet.REJECTED);
//                        currentSheetHasNotImportedContracts = false;
//                        this.sheetWarning.put(this.SHEET_WARNING_COUNT_MISMATCH, this.SHEET_WARNING_COUNT_MISMATCH_TEXT);
//                    
//                    //insertVector.clear();
//                    
//                    //return ;
//                    }
//                    else {
//                        setCurrentSheetStatus(this.SHEET_IMPORTED);
//                        if(this.currentSheetStatus == SHEET_STATUS_NEW) {
//                            this.report.changeSheetStatus(this.currentSheetSerial, Sheet.IMPORTED);
//                        }
//                    }
//                }
//            }
//            //check if any of the contracts imported is rejected so 
//            //we reject the whole sheet
//            if(sheet.isExistContractRejected()) {
//                //debug("sheet rejected cause there exist a  contracts under it that is rejected sheet id =" + this.currentSheetId);
//                setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
//                sheet.setStatus(Sheet.REJECTED);
//                this.sheetWarning.put(this.SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS, this.SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS_TEXT);
//            }
//
//            //set the status of all non rejected contracts to rejected if the sheet got rejected 
//            if(currentSheetStatus == SHEET_REJECTED_IMPORT) {                
//                //sheet.setAllContractsStatus(Contract.REJECTED);
//                sheet.setAllNonRejectedContractsToRejected();
//            }
//
//            String statusInsertSQL = "insert into  VW_cr_SHEET_STATUS_INSERT (user_id,SHEET_STATUS_ID,SHEET_ID,SHEET_STATUS_TYPE_ID,SHEET_STATUS_DATE) values " + "("+ userId + "," + sheetStatusId + "," + this.currentSheetId + "," + currentSheetStatus + "," + "sysdate)";
//            this.insertVector.add(statusInsertSQL);
//
//             
//            
//            if(sheet.warnings.size() != 0) {
//                String warningInsertSql = "insert into vw_cr_sheet_warning(sheet_warning_id,sheet_status_id,sheet_warning_date,sheet_Warning_type_id)values(SEQ_CR_SHEET_WARNING.nextval," + sheetStatusId + ",TO_DATE(" +"sysdate)," + this.SHEET_SERIAL_WARNING_ID + ")";
//                this.insertVector.add(warningInsertSql);
//            }
//            
//
//            
//            //insert sheet warning all are system genereated 
//            Enumeration sheetWarningsEnum = this.sheetWarning.keys();
//            Enumeration sheetWarningsTextEnum = this.sheetWarning.elements();
//            
//            while(sheetWarningsEnum.hasMoreElements()) {
//                String warningTypeId = (String)sheetWarningsEnum.nextElement();
//                String warningText = (String)sheetWarningsTextEnum.nextElement();
//                String warningInsertSql = "insert into vw_cr_sheet_warning(sheet_warning_id,sheet_status_id,sheet_warning_date,sheet_warning_type_id)" + " values(SEQ_CR_SHEET_WARNING.nextval," + sheetStatusId + ",sysdate ," + warningTypeId + ")";
//                
//                this.insertVector.add(warningInsertSql);
//                
//                sheet.warnings.put(warningText, warningText);
//            }
//            sheetWarning.clear();
//
//            if(insertStat == null) {
//                insertStat = con.createStatement();
//            }
//
//            insertStat.clearBatch();
//            String temp = "";
//            try
//            {
//              
//          
//            for(int i = 0;i < insertVector.size();i++) {                
//                temp = (String)insertVector.get(i);
//                debug(temp);
//                //insertStat.execute(temp);
//                insertStat.addBatch(temp);                   
//            }
//       //     startT = System.currentTimeMillis();
//            insertStat.executeBatch();
//            //debug("time to execute batch insert = "+ ((System.currentTimeMillis()-startT) ));
//             }
//             catch(Exception e) {
//                    //debug("here is an exceptoion ");
//                    //debug("temp = " + temp);
//                    e.printStackTrace();
//                }
//
//       //     startT = System.currentTimeMillis();   
//            
//            sheet.insertToDBAllContracts(insertStat, stat);
//            
//            //debug("time to execute all contracts insert = "+ ((System.currentTimeMillis()-startT) ));
//            //debug("contracts inserted count = "+ sheet.contracts.size());
//            //con.commit();
//            currentSheetHasNotImportedContracts = false;
//            resQ.close();
//            //statQ.close();
//            //retrivalQuery.close();
//            //insertStat.close();
//            //insertStat =null;
//        }
//        catch(Exception e) {
//            //debug("insert sheet");
//            e.printStackTrace();
//        }
//        insertVector.clear();
//    }

    /*
     * 3-this functions is responsible of generating the template
     * it take two input the template path file and the generated file path
     * it uses the template to copy it to the genreated and fill all
     * the look up sheet so the combo box can work fine 
     * the intial structure and lay out of the template is all 
     * done manually in teh template file it self 
     */
    public boolean exportTemplate(String template, String fileName) {
        try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("lookup");
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password            
                     
            Statement stat = con.createStatement();
            
            Row row=null;
            //fill look up values using this function 
            
            
            fillColumn(row,"select STATUS , IMPORT_PAYMENT_STATUS_ID from cam_import_payment_status", "STATUS", "IMPORT_PAYMENT_STATUS_ID", stat, lookUpSheet, 2, 0);
            fillColumn(row,"select REASON , REASON_ID from CAM_PAYMENT_REASON ", "REASON", "REASON_ID", stat, lookUpSheet, 4, 0);
            fillColumn(row,"select STK_STATUS , STK_STATUS_ID from CAM_STK_NUMBER_STATUS ", "STK_STATUS", "STK_STATUS_ID", stat, lookUpSheet, 6, 0);
           
            
            //  fillColumn("select     PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            Sheet dataSheet = wb.getSheet("Data");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean exportChannelPaymentStatusTemplate(String template, String fileName) {
        try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("lookup");
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password            
                     
            Statement stat = con.createStatement();
            //fill look up values using this function 
            
            Row row=null;
            
            
            fillColumn(row,"select CAM_STATUS_FOR_PAYMENT , ID from CAM_PAYMENT_CAM_STATE where ID!=2 ", "CAM_STATUS_FOR_PAYMENT", "ID", stat, lookUpSheet, 2, 0);
            fillColumn(row,"select REASON , REASON_ID from CAM_PAYMENT_REASON ", "REASON", "REASON_ID", stat, lookUpSheet, 4, 0);
            //fillColumn("select STK_STATUS , STK_STATUS_ID from CAM_STK_NUMBER_STATUS ", "STK_STATUS", "STK_STATUS_ID", stat, lookUpSheet, 6, 0);
           
            
            //  fillColumn("select     PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            Sheet dataSheet = wb.getSheet("Data");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean exportMemoTemplate(String template, String fileName) {
        try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("lookup");
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password            
                     
            Statement stat = con.createStatement();
            
            Row row=null;
            //fill look up values using this function 
            
            
            fillColumn(row,"select CAM_MEMO_REASON.REASON_ID,CAM_MEMO_REASON.REASON_NAME from CAM_MEMO_REASON", "REASON_NAME", "REASON_ID", stat, lookUpSheet, 0, 0);
            
           
            
            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            Sheet dataSheet = wb.getSheet("Data");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean exportPaymentExcel(String template, String fileName,String dcmCode,String dcmName,String stateId,int pageNum,int channelId) {
        try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("lookup");
            Row row=null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password
            String whereClause="";
                     
            Statement stat = con.createStatement();
            //fill look up values using this function
            int rowFrom=(pageNum*50)-50;
            int rowTo=pageNum*50;
            
            debug("dcmCode: "+dcmCode);
            if(!dcmCode.equals(""))
            	whereClause+=" gen_dcm.DCM_CODE= "+ dcmCode;
            
            
            debug("stateId: "+stateId);
            if((!dcmName.equals(""))&&(!dcmCode.equals("")))
            	whereClause+=" AND gen_dcm.DCM_NAME = "+dcmName;
            else if(!dcmName.equals(""))
            	whereClause+=" gen_dcm.DCM_NAME = "+dcmName;
            debug("stateId: "+stateId);
            
            debug("stateId: "+stateId);
            if((!stateId.equals(""))&&((!dcmName.equals(""))||(!dcmCode.equals(""))))
            	whereClause+=" and CAM_PAYMENT_CAM_STATE.ID = "+stateId;
            else if(!stateId.equals(""))
            	whereClause+=" CAM_PAYMENT_CAM_STATE.ID = "+stateId;
            
            debug("channelId: "+channelId);
            if((channelId==1||channelId==2)&&((!stateId.equals(""))||(!dcmName.equals(""))||(!dcmCode.equals(""))))
            {
            	whereClause+="and gen_dcm.CHANNEL_ID= "+channelId;
            }else if(channelId==1||channelId==2)
            {
            	whereClause+="gen_dcm.CHANNEL_ID= "+channelId;
            }
            whereClause+=" and rownum<= "+rowTo;
            whereClause+=" order by  lower (gen_dcm.dcm_name) ";
            whereClause+=" )WHERE RNUM >= "+rowFrom;
            debug("where clause: "+whereClause);
            
            //sql query for dcm name
            
            //fillColumn("select * from ( select GEN_DCM.DCM_NAME , gen_dcm.DCM_ID from CAM_PAYMENT_SCM_STATUS left join CAM_PAYMENT_CAM_STATE on (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID) left join gen_dcm on (CAM_PAYMENT_SCM_STATUS.SCM_ID = gen_dcm.DCM_ID ) left join cam_payment on (CAM_PAYMENT_SCM_STATUS.SCM_ID =cam_payment.SCM_ID )WHERE "+whereClause, "DCM_NAME", "DCM_ID", stat, lookUpSheet, 0, 0);
            fillPaymentColumn(row,"select * from ( select distinct(GEN_DCM.DCM_NAME), rownum rnum from CAM_PAYMENT_SCM_STATUS left join CAM_PAYMENT_CAM_STATE on (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID) left join gen_dcm on (CAM_PAYMENT_SCM_STATUS.SCM_ID = gen_dcm.DCM_ID ) left join cam_payment on (CAM_PAYMENT_SCM_STATUS.SCM_ID =cam_payment.SCM_ID )WHERE "+whereClause, "DCM_NAME", stat, lookUpSheet, 0, 1);
            String dcms="select count(*) from ( select distinct(GEN_DCM.DCM_NAME) from CAM_PAYMENT_SCM_STATUS left join CAM_PAYMENT_CAM_STATE on (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID) left join gen_dcm on (CAM_PAYMENT_SCM_STATUS.SCM_ID = gen_dcm.DCM_ID ) left join cam_payment on (CAM_PAYMENT_SCM_STATUS.SCM_ID =cam_payment.SCM_ID )WHERE "+whereClause;
            //sql query for dcm code
            //fillColumn("select * from ( select GEN_DCM.DCM_CODE ,gen_dcm.DCM_ID from CAM_PAYMENT_SCM_STATUS left join CAM_PAYMENT_CAM_STATE on (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID) left join gen_dcm on (CAM_PAYMENT_SCM_STATUS.SCM_ID = gen_dcm.DCM_ID ) left join cam_payment on (CAM_PAYMENT_SCM_STATUS.SCM_ID =cam_payment.SCM_ID ) WHERE "+whereClause, "DCM_CODE", "DCM_ID", stat, lookUpSheet, 1, 0);
            fillPaymentColumn(row,"select * from ( select distinct(GEN_DCM.DCM_CODE), gen_dcm.dcm_name ,rownum rnum from CAM_PAYMENT_SCM_STATUS left join CAM_PAYMENT_CAM_STATE on (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID) left join gen_dcm on (CAM_PAYMENT_SCM_STATUS.SCM_ID = gen_dcm.DCM_ID ) left join cam_payment on (CAM_PAYMENT_SCM_STATUS.SCM_ID =cam_payment.SCM_ID ) WHERE "+whereClause, "DCM_CODE", stat, lookUpSheet, 1, 1);
            //sqlquery for staus 
            //fillColumn("select * from ( select  CAM_PAYMENT_CAM_STATE.CAM_STATUS_FOR_PAYMENT,CAM_PAYMENT_CAM_STATE.ID from  CAM_PAYMENT_SCM_STATUS left join CAM_PAYMENT_CAM_STATE on (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID) left join gen_dcm on (CAM_PAYMENT_SCM_STATUS.SCM_ID = gen_dcm.DCM_ID ) left join cam_payment on (CAM_PAYMENT_SCM_STATUS.SCM_ID =cam_payment.SCM_ID ) left join CAM_PAYMENT_REASON on (cam_payment_SCM_STATUS.PAYMENT_STATUS_REASON_ID=cam_payment_REASON.REASON_ID) WHERE "+whereClause, "CAM_STATUS_FOR_PAYMENT", "ID", stat, lookUpSheet, 2, 0);
            fillPaymentColumn(row,"select * from ( select  CAM_PAYMENT_CAM_STATE.CAM_STATUS_FOR_PAYMENT , gen_dcm.dcm_name ,rownum rnum from  CAM_PAYMENT_SCM_STATUS left join CAM_PAYMENT_CAM_STATE on (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID) left join gen_dcm on (CAM_PAYMENT_SCM_STATUS.SCM_ID = gen_dcm.DCM_ID ) left join cam_payment on (CAM_PAYMENT_SCM_STATUS.SCM_ID =cam_payment.SCM_ID ) left join CAM_PAYMENT_REASON on (cam_payment_SCM_STATUS.PAYMENT_STATUS_REASON_ID=cam_payment_REASON.REASON_ID) WHERE "+whereClause , "CAM_STATUS_FOR_PAYMENT", stat, lookUpSheet, 2, 1);
            //sqlquery for reason
            //fillColumn("select * from ( select  CAM_PAYMENT_REASON.REASON,CAM_PAYMENT_REASON.REASON_ID from CAM_PAYMENT_SCM_STATUS left join CAM_PAYMENT_CAM_STATE on (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID) left join gen_dcm on (CAM_PAYMENT_SCM_STATUS.SCM_ID = gen_dcm.DCM_ID ) left join cam_payment on (CAM_PAYMENT_SCM_STATUS.SCM_ID =cam_payment.SCM_ID ) left join CAM_PAYMENT_REASON on (cam_payment_SCM_STATUS.PAYMENT_STATUS_REASON_ID=cam_payment_REASON.REASON_ID) WHERE "+whereClause, "REASON", "REASON_ID", stat, lookUpSheet, 3, 0);
            fillPaymentColumn(row,"select * from ( select  CAM_PAYMENT_REASON.REASON, gen_dcm.dcm_name, rownum rnum from CAM_PAYMENT_SCM_STATUS  left join CAM_PAYMENT_CAM_STATE on (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID) left join gen_dcm on (CAM_PAYMENT_SCM_STATUS.SCM_ID = gen_dcm.DCM_ID ) left join cam_payment on (CAM_PAYMENT_SCM_STATUS.SCM_ID =cam_payment.SCM_ID ) left join CAM_PAYMENT_REASON on (cam_payment_SCM_STATUS.PAYMENT_STATUS_REASON_ID=cam_payment_REASON.REASON_ID) WHERE "+whereClause, "REASON", stat, lookUpSheet, 3, 1);
            
            
           
            
            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            Sheet dataSheet = wb.getSheet("Data");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean exportClosedPaymentExcel(String template, String fileName,String payment_id,int fromFrozen) {
        try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("lookup");
            Row row=null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password
            String whereClause="";
                     
            Statement stat = con.createStatement();
            //fill look up values using this function
            debug("payment_id: "+payment_id);
                         
            fillPaymentColumn(row,"SELECT  gen_dcm.dcm_name FROM cam_payment LEFT JOIN payment_detail ON cam_payment.payment_id = payment_detail.payment_detail_id LEFT JOIN gen_dcm ON cam_payment.scm_id = gen_dcm.dcm_id WHERE cam_payment.memo_id = null or cam_payment.flag = 'NOT INCLUDED' and cam_payment.payment_id= " + payment_id, "DCM_Name", stat, lookUpSheet, 0, 1);
            fillPaymentColumn(row,"SELECT  gen_dcm.dcm_code FROM cam_payment LEFT JOIN payment_detail ON cam_payment.payment_id = payment_detail.payment_detail_id LEFT JOIN gen_dcm ON cam_payment.scm_id = gen_dcm.dcm_id WHERE cam_payment.memo_id = null or cam_payment.flag = 'NOT INCLUDED' and cam_payment.payment_id= " + payment_id, "DCM_CODE", stat, lookUpSheet, 1, 1);
            fillPaymentColumn(row,"SELECT  cam_payment.scm_commission_value FROM cam_payment LEFT JOIN payment_detail ON cam_payment.payment_id = payment_detail.payment_detail_id LEFT JOIN gen_dcm ON cam_payment.scm_id = gen_dcm.dcm_id WHERE cam_payment.memo_id = null or cam_payment.flag = 'NOT INCLUDED' and cam_payment.payment_id= " + payment_id, "scm_commission_value", stat, lookUpSheet, 2, 1);
            if(fromFrozen==1)
            	fillPaymentColumn(row,"SELECT  cam_payment.FROZEN_FLAG FROM cam_payment LEFT JOIN payment_detail ON cam_payment.payment_id = payment_detail.payment_detail_id LEFT JOIN gen_dcm ON cam_payment.scm_id = gen_dcm.dcm_id WHERE cam_payment.memo_id = null or cam_payment.flag = 'NOT INCLUDED' and cam_payment.payment_id= " + payment_id, "FROZEN_FLAG", stat, lookUpSheet, 3, 1);
            
            
           
            
            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            Sheet dataSheet =  wb.getSheet("Data");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean exportDCMDetailsExcel(String template, String fileName,String dcmCode) {
    	try {
    		debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password
            String whereClause="";
                     
            Statement stat = con.createStatement();
            //fill look up values using this function
            debug("dcm_code: "+dcmCode);
            Row row=null;
            
          //the status - reason part
            Sheet lookUpSheet = wb.getSheet("DCM Status");
            fillPaymentColumn(row,"select dcm_name from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "dcm_name", stat, lookUpSheet, 0, 1);
            
            fillPaymentColumn(row,"select dcm_code from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "DCM_CODE", stat, lookUpSheet, 1, 1);
           
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" ,GEN_DCM.DCM_EMAIL " +
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "DCM_EMAIL", stat, lookUpSheet, 2, 1);
            
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" ,GEN_CHANNEL.CHANNEL_NAME" +
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "CHANNEL_NAME", stat, lookUpSheet, 3, 1);
            
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" ,CAM_PAYMENT_CAM_STATE.CAM_STATUS_FOR_PAYMENT " +
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "CAM_STATUS_FOR_PAYMENT", stat, lookUpSheet, 4, 1);
            
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" , CAM_PAYMENT_REASON.REASON " +            		
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "REASON", stat, lookUpSheet, 5, 1);
            
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" ,CAM_PAYMENT_SCM_STATUS.STK_NUMBER " +
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "STK_NUMBER", stat, lookUpSheet, 6, 1);
            
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" ,CAM_STK_NUMBER_STATUS.STK_STATUS " +
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "STK_STATUS", stat, lookUpSheet, 7, 1);
            
            
           /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
            
            
            //the payments not in any memo part
             lookUpSheet = wb.getSheet("DCM Payments");
             
             fillPaymentColumn(row,"select dcm_name from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "dcm_name" , stat, lookUpSheet, 0, 1);
             
             fillPaymentColumn(row,"select dcm_code from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "DCM_CODE", stat, lookUpSheet, 1, 1);
             
             
            fillPaymentColumn(row,"SELECT PAYMENT_DETAIL.PAYMENT_NAME" +
            		" FROM CAM_PAYMENT LEFT JOIN PAYMENT_DETAIL ON CAM_PAYMENT.PAYMENT_ID = PAYMENT_DETAIL.PAYMENT_DETAIL_ID " +
            		" LEFT JOIN GEN_DCM ON GEN_DCM.DCM_ID=CAM_PAYMENT.SCM_ID " +
            		" left join cam_payment_scm_Status on cam_payment.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID " +
            		" WHERE CAM_PAYMENT.MEMO_ID = NULL OR CAM_PAYMENT.FLAG = 'NOT INCLUDED'"+
            		" AND GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "PAYMENT_NAME", stat, lookUpSheet, 0, 4);
            
            fillPaymentColumn(row,"SELECT CAM_PAYMENT.SCM_COMMISSION_VALUE" +
            		" FROM CAM_PAYMENT LEFT JOIN PAYMENT_DETAIL ON CAM_PAYMENT.PAYMENT_ID = PAYMENT_DETAIL.PAYMENT_DETAIL_ID " +
            		" LEFT JOIN GEN_DCM ON GEN_DCM.DCM_ID=CAM_PAYMENT.SCM_ID " +
            		" left join cam_payment_scm_Status on cam_payment.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID " +
            		" WHERE CAM_PAYMENT.MEMO_ID = NULL OR CAM_PAYMENT.FLAG = 'NOT INCLUDED'"+
            		" AND GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "SCM_COMMISSION_VALUE", stat, lookUpSheet, 1, 4);
            
            
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            
            
            //all the memos include this dcm
            lookUpSheet = wb.getSheet("DCM Memos");
           
            fillPaymentColumn(row,"select dcm_name from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "dcm_name", stat, lookUpSheet, 3, 1);
            
            fillPaymentColumn(row,"select dcm_code from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "DCM_CODE", stat, lookUpSheet, 4, 1);
            
            
            
            
            
            fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME" +
            		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+
            		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
            		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
            		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
            		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
            		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
            		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
            		"  on(cam_memo.MEMO_ID=MID)" +
            		" order by  lower (CAM_MEMO.MEMO_NAME)", "MEMO_NAME", stat, lookUpSheet, 0, 4);

            
            fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
            		" CAM_MEMO_TYPE.MEMO_TYPE" +
            		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+
            		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
            		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
            		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
            		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
            		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
            		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
            		"  on(cam_memo.MEMO_ID=MID)" +
            		" order by  lower (CAM_MEMO.MEMO_NAME)", "MEMO_TYPE", stat, lookUpSheet, 1, 4);
           
            
            fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
            		" CAM_MEMO_STATE.STATE" +
            		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+
            		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
            		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
            		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
            		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
            		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
            		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
            		"  on(cam_memo.MEMO_ID=MID)" +
            		" order by  lower (CAM_MEMO.MEMO_NAME)", "STATE", stat, lookUpSheet, 2, 4);


            fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
            		" COMMISSION" +
            		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+
            		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
            		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
            		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
            		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
            		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
            		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
            		"  on(cam_memo.MEMO_ID=MID)" +
            		" order by  lower (CAM_MEMO.MEMO_NAME)", "COMMISSION", stat, lookUpSheet, 3, 4);

            fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
            		" CAM_MEMO.CREATION_DATE" +
            		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+
            		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
            		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
            		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
            		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
            		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
            		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
            		"  on(cam_memo.MEMO_ID=MID)" +
            		" order by  lower (CAM_MEMO.MEMO_NAME)", "CREATION_DATE", stat, lookUpSheet, 4, 4);

            fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
            		" cam_memo.PAYMENT_DATE" +
            		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+
            		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
            		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
            		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
            		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
            		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
            		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
            		"  on(cam_memo.MEMO_ID=MID)" +
            		" order by  lower (CAM_MEMO.MEMO_NAME)", "PAYMENT_DATE", stat, lookUpSheet, 5, 4);

            fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
            		" issuedate" +
            		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+
            		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
            		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
            		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
            		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
            		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
            		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
            		"  on(cam_memo.MEMO_ID=MID)" +
            		" order by  lower (CAM_MEMO.MEMO_NAME)", "issuedate", stat, lookUpSheet, 6, 4);

           //payment names
            Vector memos=MemoDAO.getDcmMemos(con, "", dcmCode, "");
            for(int i=0;i<memos.size();i++)
            {
            	
            	MemoModel memo=(MemoModel)memos.get(i);
            	Vector paymentNames=memo.getPaymentNames();
            	StringBuffer pNames=new StringBuffer();
            	for(int j=0;j<paymentNames.size();j++){
            		MemoMembersModel name=(MemoMembersModel)paymentNames.get(j);
            		pNames.append(name.getPaymetName());
            		pNames.append(",");
            	}
            	//set the payment names in cell 7
            	fillDCMDetailsColumn(row,pNames.toString(), lookUpSheet, 7, 4,null);
            	
            }
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            //all the deductions made on this dcm
            lookUpSheet = wb.getSheet("DCM Deductions");
            fillPaymentColumn(row,"select dcm_name from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "dcm_name", stat, lookUpSheet, 2, 1);
            
            fillPaymentColumn(row,"select dcm_code from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "DCM_CODE", stat, lookUpSheet, 3, 1);
            
            
            
            
            
            fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.DEDUCTION_VALUE" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "DEDUCTION_VALUE", stat, lookUpSheet, 0, 4);
            
            fillPaymentColumn(row,"SELECT CAM_DEDUCTION_REASONS.REASON_NAME" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "REASON_NAME", stat, lookUpSheet, 1,4);
            
            fillPaymentColumn(row,"SELECT " +
            		" CAM_DEDUCTION_STATUS.STATUS_NAME" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "STATUS_NAME", stat, lookUpSheet, 2, 4);
            
            fillPaymentColumn(row,"SELECT " +
            		" CAM_DEDUCTION.DEDUCTION_DATE" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "DEDUCTION_DATE", stat, lookUpSheet, 3,4);
            
            fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.GROUP_PAY_TYPE_NAME" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "GROUP_PAY_TYPE_NAME", stat, lookUpSheet, 4, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.REMAINING_VALUE" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "REMAINING_VALUE", stat, lookUpSheet, 5, 4);
            
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            //history of the payment Status
            lookUpSheet = wb.getSheet("DCM Payment Status History");
            
            fillPaymentColumn(row,"select dcm_name from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "dcm_name", stat, lookUpSheet, 1, 1);
            
            fillPaymentColumn(row,"select dcm_code from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "DCM_CODE", stat, lookUpSheet, 2, 1);
            
            fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME" +
            		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
            		" LEFT JOIN PAYMENT_DETAIL " +
            		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
            		" LEFT JOIN CAM_PAYMENT_ACTION" +
            		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
            		" LEFT JOIN CAM_PAYMENT_REASON" +
            		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
            		" and GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "ACTION_NAME", stat, lookUpSheet, 0, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
            		" CAM_PAYMENT_REASON.REASON" +
            		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
            		" LEFT JOIN PAYMENT_DETAIL " +
            		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
            		" LEFT JOIN CAM_PAYMENT_ACTION" +
            		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
            		" LEFT JOIN CAM_PAYMENT_REASON" +
            		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
            		" and GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "REASON", stat, lookUpSheet, 1, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
            		" CAM_PAYMENT_MAKER.TIME_STAMP" +
            		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
            		" LEFT JOIN PAYMENT_DETAIL " +
            		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
            		" LEFT JOIN CAM_PAYMENT_ACTION" +
            		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
            		" LEFT JOIN CAM_PAYMENT_REASON" +
            		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
            		"and GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "TIME_STAMP", stat, lookUpSheet, 2, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
            		" GEN_USER.USER_LOGIN" +
            		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
            		" LEFT JOIN PAYMENT_DETAIL " +
            		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
            		" LEFT JOIN CAM_PAYMENT_ACTION" +
            		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
            		" LEFT JOIN CAM_PAYMENT_REASON" +
            		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
            		" and GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "USER_LOGIN", stat, lookUpSheet, 3, 4);
            
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //history of the insertion and deletion of memos
            lookUpSheet = wb.getSheet("DCM Memo Insertion and Deletion");
            
            fillPaymentColumn(row,"select dcm_name from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "dcm_name", stat, lookUpSheet, 1, 1);
            
            fillPaymentColumn(row,"select dcm_code from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "DCM_CODE", stat, lookUpSheet, 2, 1);
            
            
            
            
            fillPaymentColumn(row,"SELECT  CAM_MEMO.MEMO_NAME " +
               		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
            		" LEFT JOIN CAM_MEMO" +
            		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
            		" LEFT JOIN CAM_MEMO_ACTION" +
            		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" LEFT JOIN CAM_MEMO_REASON" +
            		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
            		" GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "MEMO_NAME", stat, lookUpSheet, 0, 4);

            
            fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
            		" CAM_MEMO_ACTION.ACTION_NAME " +
            		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
            		" LEFT JOIN CAM_MEMO" +
            		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
            		" LEFT JOIN CAM_MEMO_ACTION" +
            		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" LEFT JOIN CAM_MEMO_REASON" +
            		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
            		" GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "ACTION_NAME", stat, lookUpSheet, 1, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_MEMO.MEMO_NAME," +
            		" CAM_MEMO_REASON.REASON_NAME" +
            		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
            		" LEFT JOIN CAM_MEMO" +
            		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
            		" LEFT JOIN CAM_MEMO_ACTION" +
            		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" LEFT JOIN CAM_MEMO_REASON" +
            		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
            		" GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "REASON_NAME", stat, lookUpSheet, 2, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_MEMO.MEMO_NAME," +
            		" CAM_MEMO_MAKER.TIME_STAMP" +
            		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
            		" LEFT JOIN CAM_MEMO" +
            		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
            		" LEFT JOIN CAM_MEMO_ACTION" +
            		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" LEFT JOIN CAM_MEMO_REASON" +
            		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
            		" GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "TIME_STAMP", stat, lookUpSheet, 3, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_MEMO.MEMO_NAME," +
            		" GEN_USER.USER_LOGIN" +
            		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
            		" LEFT JOIN CAM_MEMO" +
            		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
            		" LEFT JOIN CAM_MEMO_ACTION" +
            		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" LEFT JOIN CAM_MEMO_REASON" +
            		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
            		" GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "USER_LOGIN", stat, lookUpSheet, 4, 4);
            
            
            
            
            
            
             
            
           
            
            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
           // HSSFSheet dataSheet = wb.getSheet("Data");
           // dataSheet.setSelected(true);
            lookUpSheet.setSelected(false); 
            //dataSheet.setSelected(true);
            //dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean exportGenDCMDetailsExcel(String template, String fileName,String dcmCode) {
    	try {
    		debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Row row=null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password
            String whereClause="";
                     
            Statement stat = con.createStatement();
            //fill look up values using this function
            debug("dcm_code: "+dcmCode);
            
          //the status - reason part
            Sheet lookUpSheet = wb.getSheet("DCM Status");
            fillPaymentColumn(row,"select dcm_name from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "dcm_name", stat, lookUpSheet, 0, 1);
            
            fillPaymentColumn(row,"select dcm_code from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "DCM_CODE", stat, lookUpSheet, 1, 1);
           
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" ,GEN_DCM.DCM_EMAIL " +
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "DCM_EMAIL", stat, lookUpSheet, 2, 1);
            
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" ,GEN_CHANNEL.CHANNEL_NAME" +
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "CHANNEL_NAME", stat, lookUpSheet, 3, 1);
            
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" ,CAM_PAYMENT_CAM_STATE.CAM_STATUS_FOR_PAYMENT " +
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "CAM_STATUS_FOR_PAYMENT", stat, lookUpSheet, 4, 1);
            
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" , CAM_PAYMENT_REASON.REASON " +            		
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "REASON", stat, lookUpSheet, 5, 1);
            
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" ,CAM_PAYMENT_SCM_STATUS.STK_NUMBER " +
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "STK_NUMBER", stat, lookUpSheet, 6, 1);
            
            fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
            		" ,CAM_STK_NUMBER_STATUS.STK_STATUS " +
            		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
            		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
            		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
            		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
            		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "STK_STATUS", stat, lookUpSheet, 7, 1);
            
            
           
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            //all the deductions made on this dcm
            lookUpSheet = wb.getSheet("DCM Deductions");
            fillPaymentColumn(row,"select dcm_name from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "dcm_name", stat, lookUpSheet, 2, 1);
            
            fillPaymentColumn(row,"select dcm_code from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "DCM_CODE", stat, lookUpSheet, 3, 1);
            
            
            
            
            
            fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.DEDUCTION_VALUE" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "DEDUCTION_VALUE", stat, lookUpSheet, 0, 4);
            
            fillPaymentColumn(row,"SELECT CAM_DEDUCTION_REASONS.REASON_NAME" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "REASON_NAME", stat, lookUpSheet, 1,4);
            
            fillPaymentColumn(row,"SELECT " +
            		" CAM_DEDUCTION_STATUS.STATUS_NAME" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "STATUS_NAME", stat, lookUpSheet, 2, 4);
            
            fillPaymentColumn(row,"SELECT " +
            		" CAM_DEDUCTION.DEDUCTION_DATE" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "DEDUCTION_DATE", stat, lookUpSheet, 3,4);
            
            fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.GROUP_PAY_TYPE_NAME" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "GROUP_PAY_TYPE_NAME", stat, lookUpSheet, 4, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.REMAINING_VALUE" +
            		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
            		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_REASONS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
            		" LEFT JOIN CAM_DEDUCTION_STATUS" +
            		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'", "REMAINING_VALUE", stat, lookUpSheet, 5, 4);
            
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            //history of the payment Status
            lookUpSheet = wb.getSheet("DCM Payment Status History");
            
            fillPaymentColumn(row,"select dcm_name from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "dcm_name", stat, lookUpSheet, 1, 1);
            
            fillPaymentColumn(row,"select dcm_code from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "DCM_CODE", stat, lookUpSheet, 2, 1);
            
            fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME" +
            		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
            		" LEFT JOIN PAYMENT_DETAIL " +
            		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
            		" LEFT JOIN CAM_PAYMENT_ACTION" +
            		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
            		" LEFT JOIN CAM_PAYMENT_REASON" +
            		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
            		" and GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "ACTION_NAME", stat, lookUpSheet, 0, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
            		" CAM_PAYMENT_REASON.REASON" +
            		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
            		" LEFT JOIN PAYMENT_DETAIL " +
            		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
            		" LEFT JOIN CAM_PAYMENT_ACTION" +
            		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
            		" LEFT JOIN CAM_PAYMENT_REASON" +
            		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
            		" and GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "REASON", stat, lookUpSheet, 1, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
            		" CAM_PAYMENT_MAKER.TIME_STAMP" +
            		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
            		" LEFT JOIN PAYMENT_DETAIL " +
            		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
            		" LEFT JOIN CAM_PAYMENT_ACTION" +
            		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
            		" LEFT JOIN CAM_PAYMENT_REASON" +
            		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
            		"and GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "TIME_STAMP", stat, lookUpSheet, 2, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
            		" GEN_USER.USER_LOGIN" +
            		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
            		" LEFT JOIN PAYMENT_DETAIL " +
            		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
            		" LEFT JOIN CAM_PAYMENT_ACTION" +
            		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
            		" LEFT JOIN CAM_PAYMENT_REASON" +
            		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
            		" and GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "USER_LOGIN", stat, lookUpSheet, 3, 4);
            
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //history of the insertion and deletion of memos
            lookUpSheet = wb.getSheet("DCM Memo Insertion and Deletion");
            
            fillPaymentColumn(row,"select dcm_name from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "dcm_name", stat, lookUpSheet, 1, 1);
            
            fillPaymentColumn(row,"select dcm_code from gen_dcm where dcm_code= "+"'"+dcmCode+"'", "DCM_CODE", stat, lookUpSheet, 2, 1);
            
            
            
            
            fillPaymentColumn(row,"SELECT  CAM_MEMO.MEMO_NAME" +
               		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
            		" LEFT JOIN CAM_MEMO" +
            		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
            		" LEFT JOIN CAM_MEMO_ACTION" +
            		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" LEFT JOIN CAM_MEMO_REASON" +
            		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
            		" GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "MEMO_NAME", stat, lookUpSheet, 0, 4);

            
            fillPaymentColumn(row,"SELECT  CAM_MEMO.MEMO_NAME," +
            		" CAM_MEMO_ACTION.ACTION_NAME " +
            		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
            		" LEFT JOIN CAM_MEMO" +
            		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
            		" LEFT JOIN CAM_MEMO_ACTION" +
            		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" LEFT JOIN CAM_MEMO_REASON" +
            		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
            		" GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "ACTION_NAME", stat, lookUpSheet, 1, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_MEMO.MEMO_NAME," +
            		" CAM_MEMO_REASON.REASON_NAME" +
            		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
            		" LEFT JOIN CAM_MEMO" +
            		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
            		" LEFT JOIN CAM_MEMO_ACTION" +
            		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" LEFT JOIN CAM_MEMO_REASON" +
            		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
            		" GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "REASON_NAME", stat, lookUpSheet, 2, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_MEMO.MEMO_NAME," +
            		" CAM_MEMO_MAKER.TIME_STAMP" +
            		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
            		" LEFT JOIN CAM_MEMO" +
            		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
            		" LEFT JOIN CAM_MEMO_ACTION" +
            		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" LEFT JOIN CAM_MEMO_REASON" +
            		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
            		" GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "TIME_STAMP", stat, lookUpSheet, 3, 4);
            
            fillPaymentColumn(row,"SELECT  CAM_MEMO.MEMO_NAME," +
            		" GEN_USER.USER_LOGIN" +
            		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
            		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
            		" LEFT JOIN CAM_MEMO" +
            		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
            		" LEFT JOIN CAM_MEMO_ACTION" +
            		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
            		" LEFT JOIN GEN_USER" +
            		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
            		" LEFT JOIN CAM_MEMO_REASON" +
            		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
            		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
            		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
            		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
            		" GEN_DCM.DCM_CODE= "+"'"+dcmCode+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "USER_LOGIN", stat, lookUpSheet, 4, 4);
            
            
            
             
            
           
            
            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            //HSSFSheet dataSheet = wb.getSheet("Data");
           // dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
           // dataSheet.setSelected(true);
           // dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean exportDCMDetailsListExcel(String template, String fileName) {
    	try {
    		debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            HSSFWorkbook wb = new HSSFWorkbook(tempIn);
            Row row=null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password
            String whereClause="";
                     
            Statement stat = con.createStatement();
            int rowCount=0;
            //fill look up values using this function
            
            
            Vector searchList=MemoDAO.viewSearchDcmDetailsList(con);
            
            for(int i=0;i<searchList.size();i++)
            {
            	PaymentScmStatusModel model =(PaymentScmStatusModel) searchList.get(i);
            	
            	debug("list dcm name: "+model.getDcmName());
            	
            	
            	if(i==0)
            		wb.setSheetName(0, model.getDcmName());
            	else
            		wb.createSheet(model.getDcmName());
            	
            	//the status - reason part
                Sheet lookUpSheet = wb.getSheet(model.getDcmName());
                
                CellStyle style = wb.createCellStyle();
                style.setFillBackgroundColor(HSSFColor.YELLOW.index);
                debug("style is: "+style);
                fillDCMDetailsColumn(row,"DCM Name", lookUpSheet, 0, 0,style);
                fillDCMDetailsColumn(row,"DCM Code", lookUpSheet, 1, 0,style);
                fillDCMDetailsColumn(row,"DCM Email", lookUpSheet, 2, 0,style);
                fillDCMDetailsColumn(row,"Channel", lookUpSheet, 3, 0,style);
                fillDCMDetailsColumn(row,"Status", lookUpSheet, 4, 0,style);
                fillDCMDetailsColumn(row,"Reason", lookUpSheet, 5, 0,style);
                fillDCMDetailsColumn(row,"STK Number", lookUpSheet, 6, 0,style);
                fillDCMDetailsColumn(row,"STK Status", lookUpSheet, 7, 0,style);
                
                
                
                
                
                fillDCMDetailsColumn(row,model.getDcmName(), lookUpSheet, 0, 1,null);
                fillDCMDetailsColumn(row,model.getDcmCode(), lookUpSheet, 1, 1,null);
                      
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" ,GEN_DCM.DCM_EMAIL " +
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "DCM_EMAIL", stat, lookUpSheet, 2, 1);
                
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" ,GEN_CHANNEL.CHANNEL_NAME" +
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "CHANNEL_NAME", stat, lookUpSheet, 3, 1);
                
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" ,CAM_PAYMENT_CAM_STATE.CAM_STATUS_FOR_PAYMENT " +
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "CAM_STATUS_FOR_PAYMENT", stat, lookUpSheet, 4, 1);
                
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" , CAM_PAYMENT_REASON.REASON " +            		
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "REASON", stat, lookUpSheet, 5, 1);
                
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" ,CAM_PAYMENT_SCM_STATUS.STK_NUMBER " +
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "STK_NUMBER", stat, lookUpSheet, 6, 1);
                
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" ,CAM_STK_NUMBER_STATUS.STK_STATUS " +
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "STK_STATUS", stat, lookUpSheet, 7, 1);
                
                
               /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
                
                
                //the payments not in any memo part
                
                fillDCMDetailsColumn(row,"DCM Payments", lookUpSheet, 2, 5,style);
                fillDCMDetailsColumn(row,"Payment Name", lookUpSheet, 0, 6,style);
                fillDCMDetailsColumn(row,"Commission Value", lookUpSheet, 1, 6,style);
                currentRow=7;
                rowCount=currentRow;
                fillPaymentColumn(row,"SELECT PAYMENT_DETAIL.PAYMENT_NAME" +
                		" FROM CAM_PAYMENT LEFT JOIN PAYMENT_DETAIL ON CAM_PAYMENT.PAYMENT_ID = PAYMENT_DETAIL.PAYMENT_DETAIL_ID " +
                		" LEFT JOIN GEN_DCM ON GEN_DCM.DCM_ID=CAM_PAYMENT.SCM_ID " +
                		" left join cam_payment_scm_Status on cam_payment.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID " +
                		" WHERE CAM_PAYMENT.MEMO_ID = NULL OR CAM_PAYMENT.FLAG = 'NOT INCLUDED'"+
                		" AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "PAYMENT_NAME", stat, lookUpSheet, 0, rowCount);
                
                fillPaymentColumn(row,"SELECT CAM_PAYMENT.SCM_COMMISSION_VALUE" +
                		" FROM CAM_PAYMENT LEFT JOIN PAYMENT_DETAIL ON CAM_PAYMENT.PAYMENT_ID = PAYMENT_DETAIL.PAYMENT_DETAIL_ID " +
                		" LEFT JOIN GEN_DCM ON GEN_DCM.DCM_ID=CAM_PAYMENT.SCM_ID " +
                		" left join cam_payment_scm_Status on cam_payment.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID " +
                		" WHERE CAM_PAYMENT.MEMO_ID = NULL OR CAM_PAYMENT.FLAG = 'NOT INCLUDED'"+
                		" AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "SCM_COMMISSION_VALUE", stat, lookUpSheet, 1, rowCount);
                
                debug("the row number after dcm payments is: "+currentRow);
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                
                
                //all the memos include this dcm
                currentRow=currentRow+5;
                rowCount=currentRow;
                fillDCMDetailsColumn(row,"DCM Memos", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Memo Name", lookUpSheet, 0, ++rowCount,style);
                fillDCMDetailsColumn(row,"Memo Type", lookUpSheet, 1, rowCount,style);
                fillDCMDetailsColumn(row,"Memo State", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Commission Value", lookUpSheet, 3, rowCount,style);
                fillDCMDetailsColumn(row,"Creation Date", lookUpSheet, 4, rowCount,style);
                fillDCMDetailsColumn(row,"Payment Date", lookUpSheet, 5, rowCount,style);
                fillDCMDetailsColumn(row,"Issue Date", lookUpSheet, 6, rowCount,style);
                fillDCMDetailsColumn(row,"Payment Names", lookUpSheet, 7, rowCount,style);
                
                
                
                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "MEMO_NAME", stat, lookUpSheet, 0, ++rowCount);

                
                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" CAM_MEMO_TYPE.MEMO_TYPE" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "MEMO_TYPE", stat, lookUpSheet, 1, rowCount);
               
                
                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" CAM_MEMO_STATE.STATE" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "STATE", stat, lookUpSheet, 2, rowCount);


                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" COMMISSION" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "COMMISSION", stat, lookUpSheet, 3, rowCount);

                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" CAM_MEMO.CREATION_DATE" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "CREATION_DATE", stat, lookUpSheet, 4, rowCount);

                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" cam_memo.PAYMENT_DATE" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "PAYMENT_DATE", stat, lookUpSheet, 5, rowCount);

                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" issuedate" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "issuedate", stat, lookUpSheet, 6, rowCount);

               //payment names
                Vector memos=MemoDAO.getDcmMemos(con, "", model.getDcmCode(), "");
                for(int j=0;j<memos.size();j++)
                {
                	
                	MemoModel memo=(MemoModel)memos.get(j);
                	Vector paymentNames=memo.getPaymentNames();
                	StringBuffer pNames=new StringBuffer();
                	for(int k=0;k<paymentNames.size();k++){
                		MemoMembersModel name=(MemoMembersModel)paymentNames.get(k);
                		pNames.append(name.getPaymetName());
                		pNames.append(",");
                	}
                	//set the payment names in cell 7
                	fillDCMDetailsColumn(row,pNames.toString(), lookUpSheet, 7, rowCount,null);
                	
                }
                debug("the row number after dcm memos is: "+currentRow);
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                currentRow=currentRow+5;
                rowCount=currentRow;
                //all the deductions made on this dcm
                fillDCMDetailsColumn(row,"DCM Deductions", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Deduction Value", lookUpSheet, 0, ++rowCount,style);
                fillDCMDetailsColumn(row,"Reason", lookUpSheet, 1, rowCount,style);
                fillDCMDetailsColumn(row,"Status", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Deduction Date", lookUpSheet, 3, rowCount,style);
                fillDCMDetailsColumn(row,"Deduction Type", lookUpSheet, 4, rowCount,style);
                fillDCMDetailsColumn(row,"Remaining Value", lookUpSheet, 5, rowCount,style);
                
                
                
                fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.DEDUCTION_VALUE" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "DEDUCTION_VALUE", stat, lookUpSheet, 0, ++rowCount);
                
                fillPaymentColumn(row,"SELECT CAM_DEDUCTION_REASONS.REASON_NAME" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "REASON_NAME", stat, lookUpSheet, 1,rowCount);
                
                fillPaymentColumn(row,"SELECT " +
                		" CAM_DEDUCTION_STATUS.STATUS_NAME" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "STATUS_NAME", stat, lookUpSheet, 2, rowCount);
                
                fillPaymentColumn(row,"SELECT " +
                		" CAM_DEDUCTION.DEDUCTION_DATE" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "DEDUCTION_DATE", stat, lookUpSheet, 3,rowCount);
                
                fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.GROUP_PAY_TYPE_NAME" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "GROUP_PAY_TYPE_NAME", stat, lookUpSheet, 4, rowCount);
                
                fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.REMAINING_VALUE" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "REMAINING_VALUE", stat, lookUpSheet, 5, rowCount);
                
                debug("the row number after dcm deductions is: "+currentRow);
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                //history of the payment Status
                currentRow=currentRow+5;
                rowCount=currentRow;
                fillDCMDetailsColumn(row,"DCM Payment Status History", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Action", lookUpSheet, 0, ++rowCount,style);
                fillDCMDetailsColumn(row,"Reason", lookUpSheet, 1, rowCount,style);
                fillDCMDetailsColumn(row,"Date", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"User", lookUpSheet, 3, rowCount,style);
                
                
                fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME" +
                		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
                		" LEFT JOIN PAYMENT_DETAIL " +
                		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
                		" LEFT JOIN CAM_PAYMENT_ACTION" +
                		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
                		" LEFT JOIN CAM_PAYMENT_REASON" +
                		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
                		" and GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "ACTION_NAME", stat, lookUpSheet, 0, ++rowCount);
                
                fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
                		" CAM_PAYMENT_REASON.REASON" +
                		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
                		" LEFT JOIN PAYMENT_DETAIL " +
                		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
                		" LEFT JOIN CAM_PAYMENT_ACTION" +
                		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
                		" LEFT JOIN CAM_PAYMENT_REASON" +
                		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
                		" and GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "REASON", stat, lookUpSheet, 1, rowCount);
                
                fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
                		" CAM_PAYMENT_MAKER.TIME_STAMP" +
                		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
                		" LEFT JOIN PAYMENT_DETAIL " +
                		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
                		" LEFT JOIN CAM_PAYMENT_ACTION" +
                		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
                		" LEFT JOIN CAM_PAYMENT_REASON" +
                		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
                		"and GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "TIME_STAMP", stat, lookUpSheet, 2, rowCount);
                
                fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
                		" GEN_USER.USER_LOGIN" +
                		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
                		" LEFT JOIN PAYMENT_DETAIL " +
                		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
                		" LEFT JOIN CAM_PAYMENT_ACTION" +
                		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
                		" LEFT JOIN CAM_PAYMENT_REASON" +
                		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
                		" and GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "USER_LOGIN", stat, lookUpSheet, 3, rowCount);
                
                debug("the row number after history of payment status is: "+currentRow);
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //history of the insertion and deletion of memos
                currentRow=currentRow+5;
                rowCount=currentRow;
                fillDCMDetailsColumn(row,"DCM Memo Insertion And Deletion", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Memo Name", lookUpSheet, 0, ++rowCount,style);
                fillDCMDetailsColumn(row,"Action", lookUpSheet, 1, rowCount,style);
                fillDCMDetailsColumn(row,"Reason", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Date", lookUpSheet, 3, rowCount,style);
                fillDCMDetailsColumn(row,"User", lookUpSheet, 3, rowCount,style);
                
                
                
                fillPaymentColumn(row,"SELECT  distinct(CAM_MEMO.MEMO_NAME)" +
                   		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
                		" LEFT JOIN CAM_MEMO" +
                		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
                		" LEFT JOIN CAM_MEMO_ACTION" +
                		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" LEFT JOIN CAM_MEMO_REASON" +
                		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
                		" GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "MEMO_NAME", stat, lookUpSheet, 0, ++rowCount);

                
                fillPaymentColumn(row,"SELECT  distinct(CAM_MEMO.MEMO_NAME)," +
                		" CAM_MEMO_ACTION.ACTION_NAME " +
                		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
                		" LEFT JOIN CAM_MEMO" +
                		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
                		" LEFT JOIN CAM_MEMO_ACTION" +
                		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" LEFT JOIN CAM_MEMO_REASON" +
                		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
                		" GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "ACTION_NAME", stat, lookUpSheet, 1, rowCount);
                
                fillPaymentColumn(row,"SELECT  distinct(CAM_MEMO.MEMO_NAME)," +
                		" CAM_MEMO_REASON.REASON_NAME" +
                		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
                		" LEFT JOIN CAM_MEMO" +
                		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
                		" LEFT JOIN CAM_MEMO_ACTION" +
                		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" LEFT JOIN CAM_MEMO_REASON" +
                		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
                		" GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "REASON_NAME", stat, lookUpSheet, 2, rowCount);
                
                fillPaymentColumn(row,"SELECT  distinct(CAM_MEMO.MEMO_NAME)," +
                		" CAM_MEMO_MAKER.TIME_STAMP" +
                		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
                		" LEFT JOIN CAM_MEMO" +
                		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
                		" LEFT JOIN CAM_MEMO_ACTION" +
                		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" LEFT JOIN CAM_MEMO_REASON" +
                		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
                		" GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "TIME_STAMP", stat, lookUpSheet, 3, rowCount);
                
                fillPaymentColumn(row,"SELECT  distinct(CAM_MEMO.MEMO_NAME)," +
                		" GEN_USER.USER_LOGIN" +
                		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
                		" LEFT JOIN CAM_MEMO" +
                		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
                		" LEFT JOIN CAM_MEMO_ACTION" +
                		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" LEFT JOIN CAM_MEMO_REASON" +
                		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
                		" GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "USER_LOGIN", stat, lookUpSheet, 4, rowCount);
           
                debug("the row number after insertion and deletion of memos is: "+currentRow);
                lookUpSheet.setSelected(false);
            }
            
            
            
          
            
            
            
            
            
            
             
            
           
            
            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
           // HSSFSheet dataSheet = wb.getSheet("Data");
           // dataSheet.setSelected(true);
             
            //dataSheet.setSelected(true);
            //dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    public boolean exportFocPaymentList(String template, String fileName) {
    	try {
    		debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            HSSFWorkbook wb = new HSSFWorkbook(tempIn);
            Row row=null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password
            String whereClause="";
                     
            Statement stat = con.createStatement();
            int rowCount=0;
            //fill look up values using this function
            
            
            Vector searchList=MemoDAO.getTmpFocPaymentSearchList(con);
            
            for(int i=0;i<searchList.size();i++)
            {
            	PaymentScmStatusModel model =(PaymentScmStatusModel) searchList.get(i);
            	
            	debug("list dcm name: "+model.getDcmName());
            	
            	
            	if(i==0)
            		wb.setSheetName(0, model.getDcmName());
            	else
            		wb.createSheet(model.getDcmName());
            	
            	//the status - reason part
                Sheet lookUpSheet = wb.getSheet(model.getDcmName());
                
                CellStyle style = wb.createCellStyle();
                style.setFillBackgroundColor(HSSFColor.YELLOW.index);
                debug("style is: "+style);
                fillDCMDetailsColumn(row,"DCM Name", lookUpSheet, 0, 0,style);
                fillDCMDetailsColumn(row,"DCM Code", lookUpSheet, 1, 0,style);
                fillDCMDetailsColumn(row,"DCM Email", lookUpSheet, 2, 0,style);
                fillDCMDetailsColumn(row,"Channel", lookUpSheet, 3, 0,style);
                fillDCMDetailsColumn(row,"Status", lookUpSheet, 4, 0,style);
                fillDCMDetailsColumn(row,"Reason", lookUpSheet, 5, 0,style);
                fillDCMDetailsColumn(row,"STK Number", lookUpSheet, 6, 0,style);
                fillDCMDetailsColumn(row,"STK Status", lookUpSheet, 7, 0,style);
                
                
                
                
                
                fillDCMDetailsColumn(row,model.getDcmName(), lookUpSheet, 0, 1,null);
                fillDCMDetailsColumn(row,model.getDcmCode(), lookUpSheet, 1, 1,null);
                      
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" ,GEN_DCM.DCM_EMAIL " +
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "DCM_EMAIL", stat, lookUpSheet, 2, 1);
                
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" ,GEN_CHANNEL.CHANNEL_NAME" +
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "CHANNEL_NAME", stat, lookUpSheet, 3, 1);
                
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" ,CAM_PAYMENT_CAM_STATE.CAM_STATUS_FOR_PAYMENT " +
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "CAM_STATUS_FOR_PAYMENT", stat, lookUpSheet, 4, 1);
                
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" , CAM_PAYMENT_REASON.REASON " +            		
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "REASON", stat, lookUpSheet, 5, 1);
                
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" ,CAM_PAYMENT_SCM_STATUS.STK_NUMBER " +
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "STK_NUMBER", stat, lookUpSheet, 6, 1);
                
                fillPaymentColumn(row,"SELECT GEN_DCM.DCM_NAME" +
                		" ,CAM_STK_NUMBER_STATUS.STK_STATUS " +
                		" FROM GEN_DCM LEFT JOIN   CAM_PAYMENT_SCM_STATUS ON (GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID )" +
                		" LEFT JOIN CAM_PAYMENT_CAM_STATE ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_CAM_STATE_ID = CAM_PAYMENT_CAM_STATE.ID)" +
                		" LEFT JOIN CAM_PAYMENT_REASON ON (CAM_PAYMENT_SCM_STATUS.PAYMENT_STATUS_REASON_ID=CAM_PAYMENT_REASON.REASON_ID)" +
                		" LEFT JOIN GEN_CHANNEL ON (GEN_DCM.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID )" +
                		" LEFT JOIN CAM_STK_NUMBER_STATUS ON(CAM_STK_NUMBER_STATUS.STK_STATUS_ID=CAM_PAYMENT_SCM_STATUS.STK_STATUS)" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (GEN_DCM.DCM_NAME)", "STK_STATUS", stat, lookUpSheet, 7, 1);
                
                
               /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
                
                
                //the payments not in any memo part
                
                fillDCMDetailsColumn(row,"DCM Payments", lookUpSheet, 2, 5,style);
                fillDCMDetailsColumn(row,"Payment Name", lookUpSheet, 0, 6,style);
                fillDCMDetailsColumn(row,"Commission Value", lookUpSheet, 1, 6,style);
                currentRow=7;
                rowCount=currentRow;
                fillPaymentColumn(row,"SELECT PAYMENT_DETAIL.PAYMENT_NAME" +
                		" FROM CAM_PAYMENT LEFT JOIN PAYMENT_DETAIL ON CAM_PAYMENT.PAYMENT_ID = PAYMENT_DETAIL.PAYMENT_DETAIL_ID " +
                		" LEFT JOIN GEN_DCM ON GEN_DCM.DCM_ID=CAM_PAYMENT.SCM_ID " +
                		" left join cam_payment_scm_Status on cam_payment.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID " +
                		" WHERE CAM_PAYMENT.MEMO_ID = NULL OR CAM_PAYMENT.FLAG = 'NOT INCLUDED'"+
                		" AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "PAYMENT_NAME", stat, lookUpSheet, 0, rowCount);
                
                fillPaymentColumn(row,"SELECT CAM_PAYMENT.SCM_COMMISSION_VALUE" +
                		" FROM CAM_PAYMENT LEFT JOIN PAYMENT_DETAIL ON CAM_PAYMENT.PAYMENT_ID = PAYMENT_DETAIL.PAYMENT_DETAIL_ID " +
                		" LEFT JOIN GEN_DCM ON GEN_DCM.DCM_ID=CAM_PAYMENT.SCM_ID " +
                		" left join cam_payment_scm_Status on cam_payment.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID " +
                		" WHERE CAM_PAYMENT.MEMO_ID = NULL OR CAM_PAYMENT.FLAG = 'NOT INCLUDED'"+
                		" AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "SCM_COMMISSION_VALUE", stat, lookUpSheet, 1, rowCount);
                
                debug("the row number after dcm payments is: "+currentRow);
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                
                
                //all the memos include this dcm
                currentRow=currentRow+5;
                rowCount=currentRow;
                fillDCMDetailsColumn(row,"DCM Memos", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Memo Name", lookUpSheet, 0, ++rowCount,style);
                fillDCMDetailsColumn(row,"Memo Type", lookUpSheet, 1, rowCount,style);
                fillDCMDetailsColumn(row,"Memo State", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Commission Value", lookUpSheet, 3, rowCount,style);
                fillDCMDetailsColumn(row,"Creation Date", lookUpSheet, 4, rowCount,style);
                fillDCMDetailsColumn(row,"Payment Date", lookUpSheet, 5, rowCount,style);
                fillDCMDetailsColumn(row,"Issue Date", lookUpSheet, 6, rowCount,style);
                fillDCMDetailsColumn(row,"Payment Names", lookUpSheet, 7, rowCount,style);
                
                
                
                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "MEMO_NAME", stat, lookUpSheet, 0, ++rowCount);

                
                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" CAM_MEMO_TYPE.MEMO_TYPE" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "MEMO_TYPE", stat, lookUpSheet, 1, rowCount);
               
                
                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" CAM_MEMO_STATE.STATE" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "STATE", stat, lookUpSheet, 2, rowCount);


                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" COMMISSION" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "COMMISSION", stat, lookUpSheet, 3, rowCount);

                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" CAM_MEMO.CREATION_DATE" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "CREATION_DATE", stat, lookUpSheet, 4, rowCount);

                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" cam_memo.PAYMENT_DATE" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "PAYMENT_DATE", stat, lookUpSheet, 5, rowCount);

                fillPaymentColumn(row,"SELECT CAM_MEMO.MEMO_NAME," +
                		" issuedate" +
                		" FROM CAM_MEMO JOIN (SELECT DISTINCT(CAM_PAYMENT.MEMO_ID) AS MEMOID,SUM(CAM_PAYMENT.SCM_COMMISSION_VALUE) COMMISSION FROM CAM_PAYMENT LEFT JOIN GEN_DCM ON CAM_PAYMENT.SCM_ID=GEN_DCM.DCM_ID left join cam_payment_scm_status on cam_payment.SCM_ID=cam_payment_scm_status.SCM_ID  WHERE CAM_PAYMENT.FLAG='INCLUDED' AND GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+
                		" GROUP BY MEMO_ID ) ON CAM_MEMO.MEMO_ID=MEMOID" +
                		" LEFT JOIN GEN_CHANNEL ON (CAM_MEMO.CHANNEL_ID=GEN_CHANNEL.CHANNEL_ID)" +
                		" LEFT JOIN CAM_MEMO_STATE ON (CAM_MEMO.STATE_ID = CAM_MEMO_STATE.STATE_ID)" +
                		" LEFT JOIN CAM_MEMO_TYPE ON(CAM_MEMO.TYPE_ID=CAM_MEMO_TYPE.MEMO_TYPE_ID )" +
                		" LEFT JOIN PAYMENT_TYPE ON (CAM_MEMO.PAYMENT_TYPE_ID=PAYMENT_TYPE.PAYMENT_TYPE_ID)" +
                		" left join (select  cam_memo_maker.RECORD_ID as MID , cam_memo_maker.TIME_STAMP as issuedate from cam_memo_maker where cam_memo_maker.ACTION_ID=2 )" +
                		"  on(cam_memo.MEMO_ID=MID)" +
                		" order by  lower (CAM_MEMO.MEMO_NAME)", "issuedate", stat, lookUpSheet, 6, rowCount);

               //payment names
                Vector memos=MemoDAO.getDcmMemos(con, "", model.getDcmCode(), "");
                for(int j=0;j<memos.size();j++)
                {
                	
                	MemoModel memo=(MemoModel)memos.get(j);
                	Vector paymentNames=memo.getPaymentNames();
                	StringBuffer pNames=new StringBuffer();
                	for(int k=0;k<paymentNames.size();k++){
                		MemoMembersModel name=(MemoMembersModel)paymentNames.get(k);
                		pNames.append(name.getPaymetName());
                		pNames.append(",");
                	}
                	//set the payment names in cell 7
                	fillDCMDetailsColumn(row,pNames.toString(), lookUpSheet, 7, rowCount,null);
                	
                }
                debug("the row number after dcm memos is: "+currentRow);
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                currentRow=currentRow+5;
                rowCount=currentRow;
                //all the deductions made on this dcm
                fillDCMDetailsColumn(row,"DCM Deductions", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Deduction Value", lookUpSheet, 0, ++rowCount,style);
                fillDCMDetailsColumn(row,"Reason", lookUpSheet, 1, rowCount,style);
                fillDCMDetailsColumn(row,"Status", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Deduction Date", lookUpSheet, 3, rowCount,style);
                fillDCMDetailsColumn(row,"Deduction Type", lookUpSheet, 4, rowCount,style);
                fillDCMDetailsColumn(row,"Remaining Value", lookUpSheet, 5, rowCount,style);
                
                
                
                fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.DEDUCTION_VALUE" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "DEDUCTION_VALUE", stat, lookUpSheet, 0, ++rowCount);
                
                fillPaymentColumn(row,"SELECT CAM_DEDUCTION_REASONS.REASON_NAME" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "REASON_NAME", stat, lookUpSheet, 1,rowCount);
                
                fillPaymentColumn(row,"SELECT " +
                		" CAM_DEDUCTION_STATUS.STATUS_NAME" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "STATUS_NAME", stat, lookUpSheet, 2, rowCount);
                
                fillPaymentColumn(row,"SELECT " +
                		" CAM_DEDUCTION.DEDUCTION_DATE" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "DEDUCTION_DATE", stat, lookUpSheet, 3,rowCount);
                
                fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.GROUP_PAY_TYPE_NAME" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "GROUP_PAY_TYPE_NAME", stat, lookUpSheet, 4, rowCount);
                
                fillPaymentColumn(row,"SELECT  CAM_DEDUCTION.REMAINING_VALUE" +
                		" FROM CAM_DEDUCTION LEFT JOIN GEN_DCM" +
                		" ON (CAM_DEDUCTION.DCM_ID=GEN_DCM.DCM_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_REASONS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_REASON_ID=CAM_DEDUCTION_REASONS.REASON_ID )" +
                		" LEFT JOIN CAM_DEDUCTION_STATUS" +
                		" ON(CAM_DEDUCTION.DEDUCTION_STATUS_ID= CAM_DEDUCTION_STATUS.STATUS_ID)" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_DEDUCTION.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'", "REMAINING_VALUE", stat, lookUpSheet, 5, rowCount);
                
                debug("the row number after dcm deductions is: "+currentRow);
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                //history of the payment Status
                currentRow=currentRow+5;
                rowCount=currentRow;
                fillDCMDetailsColumn(row,"DCM Payment Status History", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Action", lookUpSheet, 0, ++rowCount,style);
                fillDCMDetailsColumn(row,"Reason", lookUpSheet, 1, rowCount,style);
                fillDCMDetailsColumn(row,"Date", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"User", lookUpSheet, 3, rowCount,style);
                
                
                fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME" +
                		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
                		" LEFT JOIN PAYMENT_DETAIL " +
                		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
                		" LEFT JOIN CAM_PAYMENT_ACTION" +
                		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
                		" LEFT JOIN CAM_PAYMENT_REASON" +
                		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
                		" and GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "ACTION_NAME", stat, lookUpSheet, 0, ++rowCount);
                
                fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
                		" CAM_PAYMENT_REASON.REASON" +
                		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
                		" LEFT JOIN PAYMENT_DETAIL " +
                		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
                		" LEFT JOIN CAM_PAYMENT_ACTION" +
                		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
                		" LEFT JOIN CAM_PAYMENT_REASON" +
                		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
                		" and GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "REASON", stat, lookUpSheet, 1, rowCount);
                
                fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
                		" CAM_PAYMENT_MAKER.TIME_STAMP" +
                		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
                		" LEFT JOIN PAYMENT_DETAIL " +
                		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
                		" LEFT JOIN CAM_PAYMENT_ACTION" +
                		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
                		" LEFT JOIN CAM_PAYMENT_REASON" +
                		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
                		"and GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "TIME_STAMP", stat, lookUpSheet, 2, rowCount);
                
                fillPaymentColumn(row,"SELECT  CAM_PAYMENT_ACTION.ACTION_NAME," +
                		" GEN_USER.USER_LOGIN" +
                		" FROM CAM_PAYMENT_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=GEN_DCM.DCM_ID " +
                		" LEFT JOIN PAYMENT_DETAIL " +
                		" ON CAM_PAYMENT_MAKER.PAYMENT_ID=PAYMENT_DETAIL.PAYMENT_DETAIL_ID" +
                		" LEFT JOIN CAM_PAYMENT_ACTION" +
                		" ON CAM_PAYMENT_MAKER.ACTION_ID=CAM_PAYMENT_ACTION.ACTION_ID" +
                		" LEFT JOIN CAM_PAYMENT_REASON" +
                		" ON CAM_PAYMENT_MAKER.REASON_ID =CAM_PAYMENT_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON CAM_PAYMENT_MAKER.SCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_PAYMENT_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" WHERE  CAM_PAYMENT_MAKER.ACTION_ID!=4 " +
                		" and GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by  lower (CAM_PAYMENT_ACTION.ACTION_NAME)", "USER_LOGIN", stat, lookUpSheet, 3, rowCount);
                
                debug("the row number after history of payment status is: "+currentRow);
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //history of the insertion and deletion of memos
                currentRow=currentRow+5;
                rowCount=currentRow;
                fillDCMDetailsColumn(row,"DCM Memo Insertion And Deletion", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Memo Name", lookUpSheet, 0, ++rowCount,style);
                fillDCMDetailsColumn(row,"Action", lookUpSheet, 1, rowCount,style);
                fillDCMDetailsColumn(row,"Reason", lookUpSheet, 2, rowCount,style);
                fillDCMDetailsColumn(row,"Date", lookUpSheet, 3, rowCount,style);
                fillDCMDetailsColumn(row,"User", lookUpSheet, 3, rowCount,style);
                
                
                
                fillPaymentColumn(row,"SELECT  distinct(CAM_MEMO.MEMO_NAME)" +
                   		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
                		" LEFT JOIN CAM_MEMO" +
                		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
                		" LEFT JOIN CAM_MEMO_ACTION" +
                		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" LEFT JOIN CAM_MEMO_REASON" +
                		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
                		" GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "MEMO_NAME", stat, lookUpSheet, 0, ++rowCount);

                
                fillPaymentColumn(row,"SELECT  distinct(CAM_MEMO.MEMO_NAME)," +
                		" CAM_MEMO_ACTION.ACTION_NAME " +
                		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
                		" LEFT JOIN CAM_MEMO" +
                		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
                		" LEFT JOIN CAM_MEMO_ACTION" +
                		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" LEFT JOIN CAM_MEMO_REASON" +
                		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
                		" GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "ACTION_NAME", stat, lookUpSheet, 1, rowCount);
                
                fillPaymentColumn(row,"SELECT  distinct(CAM_MEMO.MEMO_NAME)," +
                		" CAM_MEMO_REASON.REASON_NAME" +
                		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
                		" LEFT JOIN CAM_MEMO" +
                		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
                		" LEFT JOIN CAM_MEMO_ACTION" +
                		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" LEFT JOIN CAM_MEMO_REASON" +
                		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
                		" GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "REASON_NAME", stat, lookUpSheet, 2, rowCount);
                
                fillPaymentColumn(row,"SELECT  distinct(CAM_MEMO.MEMO_NAME)," +
                		" CAM_MEMO_MAKER.TIME_STAMP" +
                		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
                		" LEFT JOIN CAM_MEMO" +
                		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
                		" LEFT JOIN CAM_MEMO_ACTION" +
                		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" LEFT JOIN CAM_MEMO_REASON" +
                		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
                		" GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "TIME_STAMP", stat, lookUpSheet, 3, rowCount);
                
                fillPaymentColumn(row,"SELECT  distinct(CAM_MEMO.MEMO_NAME)," +
                		" GEN_USER.USER_LOGIN" +
                		" FROM CAM_MEMO_MAKER LEFT JOIN GEN_DCM" +
                		" ON CAM_MEMO_MAKER.DCM_ID=GEN_DCM.DCM_ID" +
                		" LEFT JOIN CAM_MEMO" +
                		" ON CAM_MEMO_MAKER.RECORD_ID=CAM_MEMO.MEMO_ID" +
                		" LEFT JOIN CAM_MEMO_ACTION" +
                		" ON CAM_MEMO_ACTION.ID=CAM_MEMO_MAKER.ACTION_ID" +
                		" LEFT JOIN GEN_USER" +
                		" ON CAM_MEMO_MAKER.MAKER_ID=GEN_USER.USER_ID" +
                		" LEFT JOIN CAM_MEMO_REASON" +
                		" ON CAM_MEMO_MAKER.REASON_ID=CAM_MEMO_REASON.REASON_ID" +
                		" LEFT JOIN CAM_PAYMENT_SCM_STATUS" +
                		" ON GEN_DCM.DCM_ID=CAM_PAYMENT_SCM_STATUS.SCM_ID" +
                		" WHERE CAM_MEMO_ACTION.ID!=6 AND" +
                		" GEN_DCM.DCM_CODE= "+"'"+model.getDcmCode()+"'"+" order by lower (CAM_MEMO.MEMO_NAME)", "USER_LOGIN", stat, lookUpSheet, 4, rowCount);
           
                debug("the row number after insertion and deletion of memos is: "+currentRow);
                lookUpSheet.setSelected(false);
            }
            
            
            
          
            
            
            
            
            
            
             
            
           
            
            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
           // HSSFSheet dataSheet = wb.getSheet("Data");
           // dataSheet.setSelected(true);
             
            //dataSheet.setSelected(true);
            //dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean exportMemoMonitoring(String template, String fileName,String channel_id,String subChannel_id,String from_date,String to_date) {
        try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("Quarterly");
            Row row=null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password
            DateFormatSymbols symbols = new DateFormatSymbols(); 
            String[] monthNames = symbols.getMonths(); 
            int month=0;
            Vector qMemos=MemoDAO.searchMemoMonitor(con, channel_id, subChannel_id, from_date, to_date, "3");
            for(int i=0;i<qMemos.size();i++){
            	MemoModel memo=(MemoModel)qMemos.get(i);
            	fillMemoMonitorColumn(row,"Q"+memo.getQuarterId(), "Quarter No.", lookUpSheet, 0, i+1);
            	if(memo.getMonthInQuarter()-1<0)
            		month=0;
            	else
            		month=memo.getMonthInQuarter()-1;
            	
            	fillMemoMonitorColumn(row,memo.getDayInMonthInQuarter()+"-"+monthNames[month], "Date In Quarter", lookUpSheet, 1, i+1);
            	
            	fillMemoMonitorColumn(row,memo.getName(), "Memo Name", lookUpSheet, 2, i+1);
            	fillMemoMonitorColumn(row,memo.getState(), "Memo Status", lookUpSheet, 3, i+1);
            	fillMemoMonitorColumn(row,memo.getCommissionValue()+"", "Commission Amount", lookUpSheet, 4, i+1);
            	//fillMemoMonitorColumn(memo.getCreator(), "Done By", lookUpSheet, 5, i+1);
            	fillMemoMonitorColumn(row,memo.getSendForValidationDate()+"", "Send For Validation", lookUpSheet, 6, i+1);
            	fillMemoMonitorColumn(row,memo.getApprovalDate()+"", "Approval Date", lookUpSheet, 7, i+1);
            	//fillMemoMonitorColumn(memo.get+"", "Issue date", lookUpSheet, 8, i+1);
            	fillMemoMonitorColumn(row,memo.getSalesBackOfficeApprovalDate()+"", "Sales Back Office Approval", lookUpSheet, 9, i+1);
            	fillMemoMonitorColumn(row,memo.getSalesManagerApprovalDate()+"", " Sales Manager Approval", lookUpSheet, 10, i+1);
            	fillMemoMonitorColumn(row,memo.getSalesDirectiveApprovalDate()+"", " Sales Director Approval", lookUpSheet, 11, i+1);
            	fillMemoMonitorColumn(row,memo.getFinanceReceiveDate()+"", " Finance receiving date", lookUpSheet, 12, i+1);
            	fillMemoMonitorColumn(row,memo.getPaymentDate()+"", "Payment Date", lookUpSheet, 13, i+1);
            	
            	fillMemoMonitorColumn(row,memo.getReadyCommClacDate()+"", "Ready For CommissionCalc  Date", lookUpSheet, 14, i+1);
            	fillMemoMonitorColumn(row,memo.getCalcTargetedDate()+"", "Calc Targeted Date", lookUpSheet, 15, i+1);
            	fillMemoMonitorColumn(row,memo.getPaymentTargetedDate()+"", "Payment Targeted Date", lookUpSheet, 16, i+1);
            	fillMemoMonitorColumn(row,memo.getFinishedOn()+"", "Finished On", lookUpSheet, 17, i+1);
            	fillMemoMonitorColumn(row,memo.getCommCalcDelay()+"", "Commission Calc Delay", lookUpSheet, 18, i+1);
            	fillMemoMonitorColumn(row,memo.getPaymentDelay()+"", "Payment Delay", lookUpSheet, 19, i+1);
            	Vector reasons=memo.getDelayReason();
            	StringBuilder delayReasons=new StringBuilder();
            	for(int j=0;j<reasons.size();j++){
            		MemoReasonModel reason=(MemoReasonModel)reasons.get(j);
            		delayReasons.append(reason.getReasonName());
            		delayReasons.append(",");
            	}
            	fillMemoMonitorColumn(row,delayReasons+"", "Reason of the Delay", lookUpSheet, 20, i+1);
            	
            }
            
            
            lookUpSheet = wb.getSheet("Monthly");
            Vector mMemos=MemoDAO.searchMemoMonitor(con,channel_id, subChannel_id, from_date, to_date, "2");
            for(int i=0;i<mMemos.size();i++){
            	MemoModel memo=(MemoModel)mMemos.get(i);
            	fillMemoMonitorColumn(row,monthNames[memo.getMonthId()-1], "Month", lookUpSheet, 0, i+1);
            	fillMemoMonitorColumn(row,memo.getDayInMonth()+"", "Day In Month", lookUpSheet, 1, i+1);
            	fillMemoMonitorColumn(row,memo.getName(), "Memo Name", lookUpSheet, 2, i+1);
            	fillMemoMonitorColumn(row,memo.getState(), "Memo Status", lookUpSheet, 3, i+1);
            	fillMemoMonitorColumn(row,memo.getCommissionValue()+"", "Commission Amount", lookUpSheet, 4, i+1);
            	//fillMemoMonitorColumn(memo.getCreator(), "Done By", lookUpSheet, 5, i+1);
            	fillMemoMonitorColumn(row,memo.getSendForValidationDate()+"", "Send For Validation", lookUpSheet, 6, i+1);
            	fillMemoMonitorColumn(row,memo.getApprovalDate()+"", "Approval Date", lookUpSheet, 7, i+1);
            	//fillMemoMonitorColumn(memo.get+"", "Issue date", lookUpSheet, 8, i+1);
            	fillMemoMonitorColumn(row,memo.getSalesBackOfficeApprovalDate()+"", "Sales Back Office Approval", lookUpSheet, 9, i+1);
            	fillMemoMonitorColumn(row,memo.getSalesManagerApprovalDate()+"", " Sales Manager Approval", lookUpSheet, 10, i+1);
            	fillMemoMonitorColumn(row,memo.getSalesDirectiveApprovalDate()+"", " Sales Director Approval", lookUpSheet, 11, i+1);
            	fillMemoMonitorColumn(row,memo.getFinanceReceiveDate()+"", " Finance receiving date", lookUpSheet, 12, i+1);
            	fillMemoMonitorColumn(row,memo.getPaymentDate()+"", "Payment Date", lookUpSheet, 13, i+1);
            }
            
            
            lookUpSheet = wb.getSheet("Weekly");
            Vector wMemos=MemoDAO.searchMemoMonitor(con, channel_id, subChannel_id, from_date, to_date, "1");
            for(int i=0;i<wMemos.size();i++){
            	MemoModel memo=(MemoModel)wMemos.get(i);
            	fillMemoMonitorColumn(row,monthNames[memo.getMonthId()], "Week No.", lookUpSheet, 0, i+1);
            	
            	if(memo.getDayInWeek()==1){            	
            	fillMemoMonitorColumn(row,"Sunday", "Day In Week", lookUpSheet, 1, i+1);
            	}else if(memo.getDayInWeek()==2){            	
            	fillMemoMonitorColumn(row,"Monday", "Day In Week", lookUpSheet, 1, i+1);
            	}else if(memo.getDayInWeek()==3){            	
            	fillMemoMonitorColumn(row,"Tuesday", "Day In Week", lookUpSheet, 1, i+1);
            	}else if(memo.getDayInWeek()==4){            	
            	fillMemoMonitorColumn(row,"Wednesday", "Day In Week", lookUpSheet, 1, i+1);
            	}else if(memo.getDayInWeek()==5){            	
            	fillMemoMonitorColumn(row,"Thrusday", "Day In Week", lookUpSheet, 1, i+1);
            	} 
            	
            	fillMemoMonitorColumn(row,memo.getName(), "Memo Name", lookUpSheet, 2, i+1);
            	fillMemoMonitorColumn(row,memo.getState(), "Memo Status", lookUpSheet, 3, i+1);
            	fillMemoMonitorColumn(row,memo.getCommissionValue()+"", "Commission Amount", lookUpSheet, 4, i+1);
            	//fillMemoMonitorColumn(memo.getCreator(), "Done By", lookUpSheet, 5, i+1);
            	fillMemoMonitorColumn(row,memo.getSendForValidationDate()+"", "Send For Validation", lookUpSheet, 6, i+1);
            	fillMemoMonitorColumn(row,memo.getApprovalDate()+"", "Approval Date", lookUpSheet, 7, i+1);
            	//fillMemoMonitorColumn(memo.get+"", "Issue date", lookUpSheet, 8, i+1);
            	fillMemoMonitorColumn(row,memo.getSalesBackOfficeApprovalDate()+"", "Sales Back Office Approval", lookUpSheet, 9, i+1);
            	fillMemoMonitorColumn(row,memo.getSalesManagerApprovalDate()+"", " Sales Manager Approval", lookUpSheet, 10, i+1);
            	fillMemoMonitorColumn(row,memo.getSalesDirectiveApprovalDate()+"", " Sales Director Approval", lookUpSheet, 11, i+1);
            	fillMemoMonitorColumn(row,memo.getFinanceReceiveDate()+"", " Finance receiving date", lookUpSheet, 12, i+1);
            	fillMemoMonitorColumn(row,memo.getPaymentDate()+"", "Payment Date", lookUpSheet, 13, i+1);
            }
            //Statement stat = con.createStatement();
            //fill look up values using this function
            
                         
            //fillPaymentColumn(row,"SELECT  gen_dcm.dcm_name FROM cam_payment LEFT JOIN payment_detail ON cam_payment.payment_id = payment_detail.payment_detail_id LEFT JOIN gen_dcm ON cam_payment.scm_id = gen_dcm.dcm_id WHERE cam_payment.memo_id = null or cam_payment.flag = 'NOT INCLUDED' and cam_payment.payment_id= " + payment_id, "DCM_Name", stat, lookUpSheet, 0, 1);
           // fillPaymentColumn(row,"SELECT  gen_dcm.dcm_code FROM cam_payment LEFT JOIN payment_detail ON cam_payment.payment_id = payment_detail.payment_detail_id LEFT JOIN gen_dcm ON cam_payment.scm_id = gen_dcm.dcm_id WHERE cam_payment.memo_id = null or cam_payment.flag = 'NOT INCLUDED' and cam_payment.payment_id= " + payment_id, "DCM_CODE", stat, lookUpSheet, 1, 1);
           // fillPaymentColumn(row,"SELECT  cam_payment.scm_commission_value FROM cam_payment LEFT JOIN payment_detail ON cam_payment.payment_id = payment_detail.payment_detail_id LEFT JOIN gen_dcm ON cam_payment.scm_id = gen_dcm.dcm_id WHERE cam_payment.memo_id = null or cam_payment.flag = 'NOT INCLUDED' and cam_payment.payment_id= " + payment_id, "scm_commission_value", stat, lookUpSheet, 2, 1);
            
            
           
            
            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
           // HSSFSheet dataSheet = wb.getSheet("Data");
            //dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
          //  dataSheet.setSelected(true);
          //  dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean exportDeductionTemplate(String template_path, String generated_file_path) {
        try {
        	debug("path in excelimport: "+template_path);
            FileInputStream tempIn = new FileInputStream(template_path);
            HSSFWorkbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("lookup");
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password            
                     
            Statement stat = con.createStatement();
            //fill look up values using this function 
            
            Row row=null;
            
            
            fillColumn(row,"SELECT tt1.* , tt2.REASON_STATUS_NAME FROM CAM_DEDUCTION_REASONS tt1 JOIN CAM_REASON_STATUS tt2 ON (tt1.REASON_STATUS_ID = tt2.REASON_STATUS_ID ) WHERE lower(tt2.REASON_STATUS_NAME ) = 'active'", "REASON_NAME", "REASON_ID", stat, lookUpSheet, 0, 0);
            fillColumn(row," SELECT tt1.*, tt2.GROUP_PAY_STATUS_NAME FROM CAM_DEDUCTION_PAY_TYPE_GROUP tt1 JOIN CAM_DED_PAY_TYPE_GROUP_STATUS tt2 ON (tt1.GROUP_PAY_STATUS_ID = tt2.GROUP_PAY_STATUS_ID ) WHERE lower(tt2.GROUP_PAY_STATUS_NAME) = 'active'", "GROUP_PAY_TYPE_NAME", "GROUP_PAY_TYPE_ID", stat, lookUpSheet, 2, 0);
            
           
            
            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            Sheet dataSheet = wb.getSheet("Data");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
           // dataSheet.createRow(10).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(generated_file_path);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportTemplateDataWarehouse(String template, String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("Sheet1");
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
            Sheet dataSheet = wb.getSheet("Sheet1");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportTemplateArchiving(String template, String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("Sheet2");
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con = Utility.getConnection();            
          // @machineName:port:SID,   userid,  password            
                     
            Statement stat = con.createStatement();
            Row row=null;
            //fill look up values using this function 
            fillColumn(row,"select * from cr_contract_warning_type where phase_id = 3 and warning_status_id = 1 and warning_type_id = 1 and warning_suggested_status_id = 1", "CONTRACT_WARNING_TYPE_ID", "CONTRACT_WARNING_TYPE_NAME",stat, lookUpSheet, 0, 0);
            //fillColumn("select PRODUCT_NAME, PRODUCT_ID from VW_GEN_PRODUCT ORDER BY PRODUCT_NAME ASC", "PRODUCT_NAME", "PRODUCT_ID", stat, lookUpSheet, 2, 0);
            //fillColumn("select *  from VW_CR_CUSTOMER_ID_TYPE ORDER BY CUSTOMER_ID_TYPE_NAME ASC", "CUSTOMER_ID_TYPE_NAME", "CUSTOMER_ID_TYPE_ID", stat, lookUpSheet, 4, 0);
            
            //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            Sheet dataSheet = wb.getSheet("Sheet1");
            dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            dataSheet.setSelected(true);
            dataSheet.createRow(1).createCell((short)0).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportTemplateAuthDataImport(String template, String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("lookup");
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
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
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
/*
 * 4- this function save the html log of the import process in the database 
 */
    public void saveHtmlLog(String fileName) {
        try {
            con = Utility.getConnection();            
            //String dcmId = this.getDCMId();
            //debug("saving the html log");
            //String insertSql = "insert into  vw_cr_excel_import_log(excel_import_log_id,dcm_id,excel_import_log_date,file_name)values(SEQ_CR_EXCEL_IMPORT_LOG.nextval," + dcmId + ",sysdate ,'"+ fileName + "')";
            //debug(insertSql);
            
            //con.createStatement().execute(insertSql);
            Utility.closeConnection(con);
        }
        catch(Exception e) {
            //debug("problem in saving the html log");
            e.printStackTrace();
        }
    }

/*
 * 5- this method is responsible of read a row statment strating from cell index readStartCell
 * and using the statmenttype to determine which kind of statment it will read
 * from the row object sent it to it
 * then it stores all the values read from that statment into the valuestable send to it as a parameter 
 */
//    public boolean readRowStatement(String statementType, Hashtable valuesTable, Row row, int readStartCell) {
//        Vector statusVec = (Vector)(statTable.get(statementType));
//        if(statusVec == null) {
//            //debug("the vector of stat of this type " + statementType + " is null");
//            return true;
//        }        
//        boolean statusImport = true;
//        String invalidReadItem = null;
//        
//        for(int j = 0;j < statusVec.size();j++) {
//            Template_Def_Item defItem = (Template_Def_Item)statusVec.get(j);
//            String tempReadValue = defItem.readItem(row, valuesTable, readStartCell);
//
//            if(tempReadValue != null) {
//                if(invalidReadItem != null) {
//                    invalidReadItem += ", " + tempReadValue;
//                }
//                else {
//                    invalidReadItem = tempReadValue;
//                }
//            }
//        }
//        //debug("--");
//        
//        if(invalidReadItem != null) {
//            if((statementType.compareTo("CONTRACT") == 0) && (this.currentSheetId != null)) {
//                errorVector.add(new ErrorInfo(statementType + " on Sheet " + this.currentSheetSerial, row.getRowNum() + 1, "The following fields are empty, having corrupted date or exceeding field maximum size: " + invalidReadItem));
//            }
//            else {
//                
//                // errorVector.add("Error in "+statementType+" Import on row Number " + (row.getRowNum() + 1) +"  has the following fields empty or corrupted date in it " +invalidSheetReadItem);
//                errorVector.add(new ErrorInfo(statementType, row.getRowNum() + 1, "The following fields are empty, having corrupted date or exceeding filed maximum size: " + invalidReadItem));
//                return false;
//            }
//        }
//        else {
//            return true;
//        }
//        return false;
//    }

/*    
    public boolean readFile(String fileName) {
        try {
            FileInputStream tempIn = new FileInputStream(fileName);
            HSSFWorkbook wb = new HSSFWorkbook(tempIn);
            HSSFSheet sheet = wb.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum() + 1;
            //Utility.logger.debug("Last Row Number = " + lastRowNum);
            for(int i = 0;i < lastRowNum;i++) {
                HSSFRow row = sheet.getRow(i);
                if(row != null) {
                    HSSFCell cell = row.getCell((short)0);
                    if(cell != null) {
                        String temp = com.mobinil.sds.core.system.cr.excelImport.ExcelImport.readCell(cell);
                    }
                
                //  //Utility.logger.debug(cell.getStringCellValue());
                }
            }
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
*/


/*
 * 6-get the dcm id of the dcm code of this sheet 
 */
//    public String getDCMId() {
//        try {
//            String dcmId = null;
//            String sql = "select dcm_id from vw_gen_dcm where dcm_code = \'" + this.dcmCode + "\'";
//            //debug(sql);
//            Statement stat = con.createStatement();
//         //   startT = System.currentTimeMillis();
//            ResultSet res = stat.executeQuery(sql);
//            //debug("time to get dcmid  = " + (System.currentTimeMillis() - startT));
//            if(res.next()) {                
//                //errorVector.add("Error in sheet " +dcmField + "  on row " + rowNum +" is not valid");
//                dcmId = res.getString("dcm_id");
//            }
//            res.close();
//            stat.close();
//            return dcmId;
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public ExcelImport(String userId) {
        this.userId= userId;
    }


//Hashtable contractFormNumbersInExcelFile = new Hashtable();

Statement globalRetrivalQuery = null;
Statement globalRetrivalQuery2 = null;
Statement validateSheetStatement = null;
    /*
     * 7-import excel file taskes the file name as input also an error vector that the method take to fill with 
     * any error reports generated 
     * also it take an importReport object that it fill with the imoprted items 
     */
//    public boolean ImportFile(String fileName, Vector errorVector, ImportReport report) {
//    long start_time = System.currentTimeMillis();
//
//    Statement stat =null;
//    
//        try {
//            //debug("init connection");
//            initConnection();
//
//            stat =  con.createStatement();
//            
//            //debug("init status values");
//            initStatusValues(stat);
//
//            
//            //debug("init look up tables");
//            initLookUpTables(stat);
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//            //System.exit(-1);
//            return false; 
//        }
//        //debug("file Name = " + fileName);
//        this.report = report;
//        this.errorVector = errorVector;
//        timeStamp = new Date();
//        Hashtable sheetTable = new Hashtable();
//        Hashtable contractTable = new Hashtable();
//        timeStampS = timeStamp.getDate() + "\\" + (timeStamp.getMonth() + 1) + "\\" + (timeStamp.getYear() + 1900) + " " + timeStamp.getHours() + ":" + timeStamp.getMinutes() + ":" + timeStamp.getSeconds();
//        try {
//        /*
//         * getting the def statments from the database that define both contract and sheet statement 
//         */
//            
//         //   globalRetrivalQuery = con.createStatement();
//         //   globalRetrivalQuery2 = con.createStatement();
//         //   validateSheetStatement = con.createStatement();
//          //  validateCntrctStat  = con.createStatement();
//            
//            ResultSet res = stat.executeQuery("select * from VW_CR_TEMPLATE_DEF order by template_def_type_id,TEMPLATE_DEF_ORDER");
//            String lastDefType = null;
//            Vector statDefVec = null;
//            statTable = new Hashtable();
//            while(res.next()) {
//                String typeName = res.getString("TEMPLATE_DEF_TYPE_NAME");
//                String keyName = res.getString("TEMPLATE_DEF_NAME");
//                boolean mandatory = res.getString("TEMPLATE_DEF_MANDATORY").compareTo("1") == 0 ? true : false;
//                String productName = res.getString("PRODUCT_NAME");
//                int order = res.getInt("TEMPLATE_DEF_ORDER");
//                String type = res.getString("TEMPLATE_DEF_DATA_TYPE");
//                int maxSize = res.getInt("TEMPLATE_DEF_MAX_SIZE");
//                
//                if(lastDefType == null || typeName.compareTo(lastDefType) != 0) {
//                    if(lastDefType != null) {
//                        statTable.put(lastDefType, statDefVec);
//                    }
//                    lastDefType = typeName;
//                    statDefVec = new Vector();
//                }
//                Template_Def_Item defItem = new Template_Def_Item(keyName, mandatory, order, type, maxSize);
//                statDefVec.add(defItem);
//            }
//            res.close();
//
//            //stat.close();
//
//            
//            if(lastDefType != null) {
//                statTable.put(lastDefType, statDefVec);
//            }
//
//            /*
//             * opening the excel file 
//             */
//            FileInputStream tempIn = new FileInputStream(fileName);
//            Workbook wb = new HSSFWorkbook(tempIn);
//            Sheet sheet = wb.getSheetAt(0);
//            
//            int lastRowNum = sheet.getLastRowNum() +1;
//            //debug("Last Row Number = " + lastRowNum);
//           /*
//            * verfiing that the dcm code exist then read it and verify it is a vaild one from database 
//            */
//            HSSFRow dcmRow = sheet.getRow(1);
//             Cell dcmCell = dcmRow.getCell((short)0);
//            if(dcmCell == null || this.readCell(dcmCell).compareTo("") == 0) {
//                this.errorVector.add(new ErrorInfo("File", dcmRow.getRowNum(), "DCM Code is missing"));
//                Utility.closeConnection(con);
//                return true;
//            }
//            else {
//                dcmCode = this.readCell(dcmCell);
//                String sqlCheck = "select * from vw_gen_dcm where DCM_STATUS_NAME ='ACTIVE' and dcm_code='" + dcmCode + "'";
//            //    Statement checkDCMCodeStat = con.createStatement();
//                startT = System.currentTimeMillis();
//                ResultSet checkDCMCodeRes = stat.executeQuery(sqlCheck);
//                //debug("time to get checkDCMCodeRes = " + (System.currentTimeMillis() - startT));
//                
//                if(!checkDCMCodeRes.next()) {
//                    this.errorVector.add(new ErrorInfo("File", dcmRow.getRowNum(), "DCM Code Does Not Exist"));
//                    checkDCMCodeRes.close();
//                //    checkDCMCodeStat.close();
//                    stat.close();
//                    Utility.closeConnection(con);
//                    return true;
//                }
//                checkDCMCodeRes.close();
//                //checkDCMCodeStat.close();
//            }
//
//            /* 
//             * raeding and parsing the excel file starting from row number 3 until the last row 
//             */
//            int emptyRowCount = 0 ; 
//             Row row ; 
//             Cell cell;
//             boolean isItSheetStatement;
//            for(int i = 3;i < lastRowNum ;i++) {
////                debug("row num " + i);
//                row = sheet.getRow(i); 
//              
//                if(row != null) {                                        
//                    rowNum = row.getRowNum() + 1;
//                     
//                    cell = row.getCell((short)sheetFirstCellNum);
//                   isItSheetStatement  = false;
//                    /*
//                     * this check if it is a sheet statment on this row 
//                     * by examining all the cells from start indiex of the sheet statment
//                     * and if found any data on any cell then it is considered a sheet statment 
//                     * 
//                     */
//                    for(int cellIndex = 0;cellIndex < this.contractFirstCellNum;cellIndex++) {
//                        cell = row.getCell((short)(sheetFirstCellNum + cellIndex));
//                        if(cell != null) {
//                            if(readCell(cell).compareTo("") != 0) {
//                                isItSheetStatement = true;
//                                break;
//                            }
//                        }
//                    }
//                    if(isItSheetStatement) {
//                      emptyRowCount =0 ;
//                        if(currentSheetId != null) {
//                            //insering the previous sheet to the database since finding a new sheet
//                            //indicate that the previous one have finished 
//                            insertSheetToDB(stat);                            
//                        }
//                        //init variables 
//                        currentSheetId = null;
//                        this.currentSheetStatus = -1;
//                        currentSheetHasNotImportedContracts = false;
//
//                         sheetTable.clear();
//                         
//                        //trying to read a sheet statment and returning its information on the row
//                        // in the sheetTable object sent to it 
//                        if(this.readRowStatement("SHEET", sheetTable, row, this.sheetFirstCellNum)) {
//                        //checking the validity of this sheet statment 
//                        //it make the sheet rejected if any of the values is not valid or missing 
//                            boolean isSheetValid = validateSheet(sheetTable, row.getRowNum() + 1,stat);
//                            //debug("isSheetValid = " + isSheetValid);
//                            if(!isSheetValid) {
//                                this.currentSheetContractCount = 0;
//                                this.currentSheetId = null;
//                                this.currentSheetSerial = null;
//                                this.insertVector.clear();
//                                this.sheetWarning.clear();
//                            }
//                        }
//                    }                    
//                    /*
//                     * checking if this row has a contract statment 
//                     * by reading all the cells from the start index of the contract statmenet
//                     * ending afrer reading offset that is equal to the size of the vector that contrain the 
//                     * contract statment 
//                     */
//                    boolean isItContractStatement = false;
//                    for(int cellIndex = contractFirstCellNum;cellIndex < this.contractFirstCellNum + 8;cellIndex++) {
//                        cell = row.getCell((short)(sheetFirstCellNum + cellIndex));
//                        if(cell != null) {
//                            if(readCell(cell).compareTo("") != 0) {
//                                isItContractStatement = true;
//                                break;
//                            }
//                        }
//                    }
//                    //cehcking if this is a contract that have no sheet assigned to it 
//                    if(isItContractStatement) {
//                    emptyRowCount = 0;
//                        if(currentSheetId == null) {
//                            this.errorVector.add(new ErrorInfo("CONTRACT", (row.getRowNum() + 1), " Sheet Was Not Imported"));
//                        }                      
//                        else {
//                             contractTable.clear();
//                            if(this.readRowStatement("CONTRACT", contractTable, row, this.contractFirstCellNum)) {
//                                //debug("done reading");
//                                if (this.report.getSheet(currentSheetSerial).contractExist((String)contractTable.get(this.CONTRACT_MAIN_SIM_NO)) )
//                                {
//                                    this.errorVector.add(new ErrorInfo("CONTRACT on SHEET " +currentSheetSerial , (row.getRowNum() + 1), "Duplicate Contract In Sheet"));
//                                    this.currentSheetHasNotImportedContracts = true;
//                                }
//                                else
//                                {                                
//                                  boolean isContractValid = validateContract(contractTable, row,stat);
//                                  if(!isContractValid) 
//                                  {
//                                    currentSheetHasNotImportedContracts = true;
//                                  }
//                                }
//                            }
//                            else {
//                                currentSheetHasNotImportedContracts = true;
//                            }
//                        }
//                    }
//
//                    if (isItContractStatement == false && isItSheetStatement == false)
//                    {
//                      emptyRowCount++;
//                      //debug("row is empty" );
//                      if (emptyRowCount >10)//if 10 rows are empty then assume the end of the sheet is reached 
//                      { 
//                        //debug("breakkkk");
//                        break;
//                      }
//                    }
//                }
//                else
//                {
//                  //row is null
//                  emptyRowCount++;
//                  //debug("row is empty" );
//                  if (emptyRowCount >10)//if 10 rows are empty then assume the end of the sheet is reached 
//                  { 
//                  //debug("breakkkk");
//                  break;
//                  }
//                  
//                }
//
//                
//            }
//            //debug("before final insert");
//            insertSheetToDB(stat);   
//
////            validateCntrctStat.close();
//
//            if(insertStat!=null)
//            this.insertStat.close();
//            
//            Utility.closeConnection(con);
//
//            //Utility.logger.debug("Total time needed to import = "+ (double)((System.currentTimeMillis() - start_time) / 1000));
//            return true;
//        }
//        catch(Exception e) {
//            //debug("last exception");
//            e.printStackTrace();
//           try{ Utility.closeConnection(con);}
//           catch(Exception ee)
//           {e.printStackTrace();}
//            return false;
//        }
//    }

/*
 * 8- read a HSSFCell object and return its value as String
 */    
//    public static String readCell(HSSFCell cell) {
//        if(cell == null) {
//            return "";
//        }
//        switch(cell.getCellType()) {
//          case HSSFCell.CELL_TYPE_BLANK:
//              return "";
//          
//          case HSSFCell.CELL_TYPE_BOOLEAN:
//              if(cell.getBooleanCellValue()) {
//                  return "TRUE";
//              }
//              else {
//                  return "FALSE";
//              }
//          
//          case HSSFCell.CELL_TYPE_ERROR:
//              //debug("Error in cell reading");
//              return "";
//          
//          case HSSFCell.CELL_TYPE_FORMULA:
//              //debug("Cell is a formula");
//              return "";
//          
//          case HSSFCell.CELL_TYPE_NUMERIC:
//              return cell.getNumericCellValue() + "";
//          
//          case HSSFCell.CELL_TYPE_STRING:
//              return cell.getStringCellValue().trim();          
//          default:
//              return "";
//        }
//    }
//

    /*
     * this is a test main a stand alone test of the class
     * but it need database connection to be done manually since 
     * utility.getconnection will not work 
     */
    public static void main(String[] args) {
        //ExcelImport ExcelImport = new ExcelImport();
     //   ExcelImport.exportTemplate("c:/test/template.xls", "c:/test/sampleGenerated.xls");    
    //ExcelImport.readFile("c:/test/mm.xls");    
    //ExcelImport.ImportFile("c:/test/man.xls");
    }


    /*
     * 9-validate a sheet statment 
     * checking all values are not null 
     * and are valid by validating them from the database 
     * and upon that set the necessarly flags weither the sheet is accepted or rejected or an error occur
     */
//    private boolean validateSheet(Hashtable sheetInfo, int rowNum , Statement stat) throws Exception {
//        sheetWarning = new Hashtable();
///*
//        if(validateSheetStatement == null) {
//            validateSheetStatement = con.createStatement();
//        }
//  */
//  
//        //verify that this sheet serial number exist
//        currentSheetSerial = (String)sheetInfo.get(SHEET_SERIAL_FIELD);
//        //debug("currentSheetSerial = " + currentSheetSerial);
//        if(this.report.getSheet(currentSheetSerial) != null) {
//            this.errorVector.add(new ErrorInfo("SHEET", rowNum + 1, "Duplicate Sheet serial number " + currentSheetSerial));
//            return false;
//        }
//        sheetInfo.put(this.SHEET_DISTRIBUTER_ID, this.dcmCode);
//        //Statement stat = validateSheetStatement;
//        
//        //verify the sheet type exist
//        String sheetTypeId = "";
//        String sheetContractCount = (String)sheetInfo.get(SHEET_CONTRACT_COUNT);
//        if(sheetContractCount == null) {
//            setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
//            insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT +" Field: "+"Sheet Contract Count");
//            sheetContractCount = "0";
//        }
//        currentSheetContractCount = (int)Float.parseFloat(sheetContractCount);
//        
//        //sheet Type verification 
//        Object sheetTypeNameObj = sheetInfo.get(SHEET_TYPE_FIELD);
//        //debug("sheet type name = " + sheetTypeNameObj);
//        if(sheetTypeNameObj != null) {
//            Object sheetTypeIdObj = sheetTypes.get((String)sheetTypeNameObj);
//            if(sheetTypeIdObj != null) {
//                sheetTypeId = (String)sheetTypeIdObj;
//            }
//            else {
//                setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
//                sheetInfo.put(SHEET_TYPE_FIELD, "");
//                insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT + " Field: "+"Sheet Type Field");
//            }
//        }
//        else {
//            setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
//            insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT +" Field: "+"Sheet Type Field");
//        }
//
//        
//        
//        //verify dcm id + pos id + super dealer      
//        String sheetDistributerId = validateDCMId(SHEET_DISTRIBUTER_ID,"DISTRIBUTER", sheetInfo, rowNum,stat);
//        if(sheetDistributerId == null) {
//            setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
//            insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT +" Field: "+"Sheet Distributer Code");
//            sheetDistributerId = "";
//        }
//        //verify the super dealer code is a valid code 
//        String sheetSuperDealerId = validateDCMId(SHEET_SUPER_DEALER_FIELD,"SUPER DEALER", sheetInfo, rowNum,stat);
//        if(sheetSuperDealerId == null) {
//            setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
//            insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT +" Field: "+"Sheet Super Dealer Code");
//            sheetSuperDealerId = "";
//        }
//        //verify that the pos code is a valid code 
//        String sheetPosId = validateDCMId(SHEET_POS_FIELD,"POS", sheetInfo, rowNum,stat);
//        if(sheetPosId == null) {
//            setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
//            insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT +" Field: "+"Sheet POS Code"); 
//            sheetPosId = "";
//        }
///*
//        String sheetRecieveDate = "";
//        if(sheetInfo.get(SHEET_RECIEVE_DATE) != null) {
//            sheetRecieveDate = (String)sheetInfo.get(SHEET_RECIEVE_DATE);
//        }
//        else {
//            setCurrentSheetStatus(this.SHEET_REJECTED_IMPORT);
//            insertSheetWarning(this.SHEET_WARNING_MISSING_DATA, SHEET_WARNING_MISSING_DATA_TEXT +" Field: "+"SheetRecieveDate);;
//        }
//        */
//        //puting the system date as the recieve date 
//        // this was done this way to reduce changes since
//        //origianly it was read from the excel file it self 
//        String sheetRecieveDate="sysdate";
//        sheetInfo.put(SHEET_RECIEVE_DATE, sheetRecieveDate);
//        
//        
//        //verifty that this sheet serial number beolong to this guy        
//        //then perfom insertion + adding warning
//    //    startT = System.currentTimeMillis();
//        ResultSet res =stat.executeQuery("select * from VW_CR_DCM_SHEET where SHEET_SERIAL_ID= " + currentSheetSerial);
//        //debug("time to validate sheet = "+ ((System.currentTimeMillis()-startT) ));
//        if(res.next()) 
//        {
//         //   Statement statq = con.createStatement();
//  //          startT = System.currentTimeMillis();
//
//            String sheetDCMInDatabase = res.getString("DCM_ID");
//
//            res.close();
//
//            
//            res = stat.executeQuery("select SEQ_SHEET_ID.nextval from dual");
//            //debug("time to get seq_sheet_id = " + (System.currentTimeMillis() - startT));
//            res.next();
//            this.currentSheetId = res.getString(1);
//           //resq.close();
//        //    statq.close();
//            res.close();
//            
//            //debug("***************  sheet id =  " + currentSheetId);
//            //String statSheetSerialVerifySQL = " select * from " + " (select sheet_status_type_name,sheet_serial_id from (select * from vw_cr_sheet_status order by sheet_Status_date desc)sheet_status , vw_cr_sheet where rownum<=1" + " and vw_cr_sheet.SHEET_ID = sheet_status.sheet_id)" + " where SHEET_SERIAL_ID = " + currentSheetSerial + " and sheet_Status_type_name not in " + SHEET_REJECTED_STATUS_SET;
//
//            String statSheetSerialVerifySQL = " select count(*) sheet_record_count from vw_Cr_sheet_last_status where SHEET_SERIAL_ID= " + currentSheetSerial + " and sheet_Status_type_name not in " + SHEET_REJECTED_STATUS_SET;
//            
//            //debug(statSheetSerialVerifySQL);
//       //     Statement statSheetSerialVerify = con.createStatement();
//            startT = System.currentTimeMillis();
//            ResultSet resVerify = stat.executeQuery(statSheetSerialVerifySQL);
//            //debug("time to get resVerify = " + (System.currentTimeMillis() - startT ));
//            //debug("&&&&&&&&&&&&&&&& contract count " + sheetContractCount);
//            resVerify.next();
//            int verifyCount =  resVerify.getInt("sheet_record_count");
//            resVerify.close();
//      //      statSheetSerialVerify.close();
//
//            //shee doesn't exist already in the db with a non rejected status
//            
//
//            if( verifyCount != 0 ) {
//                //debug("verifyCount = " + verifyCount);
//                //debug("sheet rejected cause it exist in the database with a status not rejected sheet id " + this.currentSheetId);
//                
//                //setCurrentSheetStatus(SHEET_REJECTED_IMPORT);
//                
//                // this.report.addSheet(this.currentSheetSerial,currentSheetSerial,Sheet.REJECTED,rowNum, Integer.parseInt(sheetContractCount));            
//                this.errorVector.add(new ErrorInfo("SHEET", rowNum , "Sheet exists in the database with non rejected status"));
//                this.insertVector.clear();
//                
//                this.report.sheetReports.remove(this.currentSheetSerial);
//                this.currentSheetId =null;
//                this.sheetWarning.clear();
//                return false;
//            
//            //re init after discovering an error
//            }
//            else {
//                setCurrentSheetStatus(SHEET_IMPORTED);
//                this.report.addSheet(this.currentSheetSerial, currentSheetSerial, Sheet.IMPORTED, rowNum, (int)Float.parseFloat(sheetContractCount));
//                if(this.currentSheetStatus == this.SHEET_REJECTED_IMPORT) {
//                    this.report.changeSheetStatus(currentSheetSerial, Sheet.REJECTED);
//                }
//            }
//            //debug("this.currentSheetStatus = " + this.currentSheetStatus);
//            //debug("current sheet status = " + ((Sheet)this.report.getSheet(currentSheetSerial)).getStatusName());
//            
//            //if the sheet serial exist then we can insert it to the system
//            
//            String sql = "insert into VW_cr_SHEET (sheet_id, SHEET_DISTRIBUTER_code,SHEET_SUPER_DEALER_code,SHEET_POS_code,SHEET_TYPE_ID,SHEET_SERIAL_ID,SHEET_RECIEVE_DATE,sheet_time_stamp ,SHEET_CONTRACT_COUNT,phase_id) VALUES (" + this.currentSheetId + ",";
//            sql += "\'" + (String)sheetInfo.get(SHEET_DISTRIBUTER_ID)  + "\',\'" + (String)sheetInfo.get(SHEET_SUPER_DEALER_FIELD) + "\',\'" + (String)sheetInfo.get(SHEET_POS_FIELD) + "\','" + sheetTypeId + "'," + currentSheetSerial + "," + "sysdate," + "TO_DATE(\'" + timeStampS + "\',\'DD\\MM\\YYYY HH24:MI:ss\'),\'" + sheetContractCount + "\'," + importPhaseId + ")";
//
//            //debug("insert sql ");            
//            //debug(sql);
//            
//            /*
//            String sql = "insert into vw_cr_sheet (";
//            String valuesSql ="";
//            
//            Enumeration keys = sheetInfo.keys();
//            Enumeration values = sheetInfo.elements();
//            
//            while (keys.hasMoreElements())
//            {
//            String key = (String) keys.nextElement();
//            String value =(String) values.nextElement();
//            sql += key + ",";
//            valuesSql += "\'" + value + "\'," ;          
//            }
//            sql = sql.substring(0,sql.length()-1) + ") values(";
//            valuesSql = valuesSql.substring(0,valuesSql.length()-1) +")";
//            sql +=valuesSql;
//            */
//            
//            //Statement insertStatement = con.createStatement();        
//            //debug("insert sql = " + sql);
//            
//            //boolean insertStatusFlag = insertStatement.execute(sql);
//            
//            // //debug("insert sheet flag = " +insertStatusFlag +"");
//            insertVector.add(sql);
//            if(this.getDCMId().compareTo(sheetDCMInDatabase) != 0) {
//                Sheet sheet = this.report.getSheet(this.currentSheetSerial);
//                if(sheet != null) {
//                    sheet.warnings.put(SHEET_SERIAL_WARNING_ID, this.SHEET_SERIAL_BELONG_TO_ANOTHER_DCM_WARNING);
//                }
//            }
//       //     res.close();
//            return true;
//        }
//        else {
//            errorVector.add(new ErrorInfo("Sheet", rowNum, "Sheet Serial Number " + currentSheetSerial + " does not exist"));
//            this.insertVector.clear();
//            this.report.sheetReports.remove(this.currentSheetSerial);
//            this.sheetWarning.clear();
//            this.currentSheetId =null;
//            //re initialize
//            res.close();
//            return false;
//        }
//    }


    static final String validateContractSQL ="SELECT cr_contract.contract_id,status_type_name FROM CR_CONTRACT, CR_CONTRACT_STATUS, CR_CONTRACT_sTATUS_TYPE, CR_STATUS_TYPE WHERE CR_CONTRACT.CONTRACT_LAST_STATUS_ID = CONTRACT_STATUS_ID   AND CR_CONTRACT_sTATUS_TYPE.CONTRACT_STATUS_TYPE_ID = CR_CONTRACT_STATUS.CONTRACT_STATUS_TYPE_ID AND CR_STATUS_TYPE.STATUS_TYPE_ID = CR_CONTRACT_STATUS_TYPE.TYPE_ID AND CONTRACT_MAIN_sIM_NO_LCS = '";
   /*
    * 10-validate a contract statment and form the necessary sql to insert it to the database
    * if it was a valid contract 
    * and also calculate the status of the contract and add necessary warning 
    */   
//    private boolean validateContract(Hashtable infoTable, HSSFRow row,Statement stat) throws Exception {
//        //debug("validate contract");
//        Object idTypeObj = infoTable.get(Id_No_Field);
//        boolean rejectContract = false;
//
//        String contractMainSimNo = (String)infoTable.get(CONTRACT_MAIN_SIM_NO);
//
//        //String contractFormNumber = (String) infoTable.get(CONTRACT_FORM_NUMBER);
//
//        if(!(contractMainSimNo.length() == 20 || contractMainSimNo.length() == 24))
//        {
//        
//            this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not valid Main Sim No"));
//            return false;
//        
//        }
//
///*we dont need it any more since it is done from the database
//        if(contractFormNumber.length() > 12) {
//            this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not valid Contract Form Number"));
//            return false;
//        }
//*/
//
///*
// * the whole code in this lines is to verify that the contract dial number is exactly 9 character
// * and that it is a number 
// * the reason of using big decimal is that we faced problems wth number such as 129999999
// */ 
//        try 
//        {
//        
//        BigDecimal value = new BigDecimal((String)infoTable.get("CONTRACT_DIAL_NO"));
//          //BigInteger value = Long.valueOf();
//          
//          String valueS = value.toString();
//       //   Utility.logger.debug(valueS);
//         // infoTable.put("CONTRACT_DIAL_NO",valueS);
//
//          
//      //this is to check that the lenght of the dial number is exaclty 9 digits
//       if (valueS.length() != 9 ||((String)infoTable.get("CONTRACT_DIAL_NO")).length()<9 ) {
//            this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not Valid CONTRACT DIAL NO"));
//   //         Utility.logger.debug(valueS);
//            return false;
//        }
//
//        if (valueS.indexOf(".")!=-1)
//        {
//          this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not Valid CONTRACT DIAL NO"));
//          return false; 
//        }
//        
//        }
//        catch (Exception e)
//        {
//     //   e.printStackTrace();
//          this.errorVector.add(new ErrorInfo("CONTRACT", this.rowNum, " Not valid CONTRACT DIAL NO"));
//          return false;
//        }
//            
//
//        
//        
//
//
//
//
//
//        /*
//         * check if the id type (personal identification of a customer) is a valid one or not
//         * and if it is a valid one it gets its value form the database or else remove it form the infotable 
//         * so it be inserted as null 
//         */
//        if(idTypeObj != null) {
//            String idTypeString = (String)this.idTypes.get((String)idTypeObj);
//            if(idTypeString != null) {
//                //debug("**************");
//                //debug(idTypeString);
//                infoTable.put(Id_No_Field, idTypeString);
//            }
//            else {              
//                //  rejectContract = true;                
//                ////debug("**********");                
//                //   //debug("invalid contract because of invalid id type " +idTypeObj );                
//                infoTable.remove(Id_No_Field);
//            }
//        }
//        //puting the phase of import 
//        infoTable.put("phase_id", importPhaseId + "");
//        
//        //infoTable.put("sheet_id",this.currentSheetId);
//        Object contractTypeObj = infoTable.get(CONTRACT_TYPE_FIELD);
//        if(contractTypeObj != null) {
//            String contractTypeString = (String)this.productTypes.get((String)contractTypeObj);
//            //debug("contract type = " + contractTypeString);
//            if(contractTypeString != null) {
//                infoTable.put(CONTRACT_TYPE_FIELD, contractTypeString);
//            }
//            else {
//                rejectContract = true;
//                //debug("**********");
//                //debug("invalid contract because of invalid contract type");
//                infoTable.remove(CONTRACT_TYPE_FIELD);
//            }
//        }
//        infoTable.put(this.SHEET_ID, this.currentSheetId);
//        //debug("get next val contract_id");
//     //   Statement stat = con.createStatement();
//     //   startT = System.currentTimeMillis();
//        ResultSet res = stat.executeQuery("select SEQ_CR_CONTRACT_ID.nextval from dual");
//        //debug("time to get contract id seq = " + (System.currentTimeMillis()- startT));
//        res.next();
//        String currentContractId = res.getString(1);
//        res.close();
//     //   stat.close();
//        infoTable.put(CONTRACT_ID, currentContractId);
//        //String sql = "insert into vw_CR_contract_inst (";
//
//        String sql = "insert into VW_CR_contract (";
//        String valuesSql = "";
//        Enumeration keys = infoTable.keys();
//        Enumeration values = infoTable.elements();
//        while(keys.hasMoreElements()) {
//            String key = (String)keys.nextElement();
//            String value = (String)values.nextElement();
//            sql += key + ",";
//            if(key.compareTo("CONTRACT_CUSTOMER_BIRTH_DATE") != 0)
//            {
//            valuesSql += "\'" + value + "\',";
//            }
//            else 
//            {
//             valuesSql += "TO_DATE(\'" + value + "\',\'dd/mm/yyyy\'),"; 
//            }
//        }
//        sql = sql.substring(0, sql.length() - 1) + ") values(";
//        valuesSql = valuesSql.substring(0, valuesSql.length() - 1) + ")";
//        sql += valuesSql;
//       // //debug("get next val status_contract_id");
//        
//        //Statement statQ  =stat ;
//        
//        //ResultSet resQ = statQ.executeQuery("select SEQ_CR_CONTRACT_STATUS.nextval from dual");
//        
//        //resQ.next();
//        
//        //String contractStatusId = resQ.getString(1);
//        infoTable.put(CONTRACT_MAIN_SIM_NO, "'" + contractMainSimNo + "'");
//        int contractStatus;
//        
//        //Utility.logger.debug("QQQQQ : "+sql);
//        Contract contract = this.report.addContract(this.currentSheetSerial, currentContractId, contractMainSimNo, (row.getRowNum() + 1),userId);
//        contract.setInsertContractSQL(sql);
//        contract.setTimeStamp(this.timeStamp);
//               
//         
//        contractStatus = CONTRACT_IMPORT_STATUS;
//        contract.setContractStatus(Contract.IMPORTED);            
//
//        //this was removed due to performance problems and replaced with new optimized code
//        //String validateContractSQL = " select * from vw_Cr_contract_last_status where CONTRACT_Status_type_name not in " + CONTRACT_REJECTED_STATUS_SET + " and contract_main_sim_no ='" + contractMainSimNo + "\'";        
//        //debug("validateContractSQL " + validateContractSQL);
//                             
//        startT = System.currentTimeMillis();
//        
//        String contractMainSimNoLCS = null;
//        
//        if (contractMainSimNo.length()>20) {
//            contractMainSimNoLCS=contractMainSimNo;
//        }
//        else {
//          contractMainSimNoLCS =   contractMainSimNo.substring(0,19);
//        }
//        
//        //Utility.logger.debug("Contract Main SIM = "+contractMainSimNo+" Contract SIM In LSC"+contractMainSimNoLCS);
//        res = stat.executeQuery(validateContractSQL +contractMainSimNoLCS +"'" +" ORDER BY CONTRACT_STATUS_DATE DESC " );
//        
//        
//       //debug("time to execute validate contract = "+ ((System.currentTimeMillis()-startT) ));
//
//        boolean validFlag = true; 
//
//        if (res.next())
//        {
//          String statusTypeName = res.getString("status_type_name");
//          if (statusTypeName.compareTo("PASS")==0)
//          {
//            validFlag = false; 
//            //the contract exist in the database with a not rejected status 
//          }
//        }
//        else
//        {
//          //the contract doesn't exist in the database so it is still valid
//        }
//        
//        res.close();
//        
//        //if(res.next()) {
//        if (!validFlag)
//        {
//            //this should not be importd and error should be displayed
//            this.errorVector.add(new ErrorInfo("CONTRACT", (row.getRowNum() + 1), " exist in the database with not rejected status"));
//            this.report.getSheet(currentSheetSerial).contracts.remove(currentContractId);
//            this.currentSheetHasNotImportedContracts = true;
//            return false;                       
//        }
//
///*
// * NDC here we are checking if this contract form number is in the database with a contract that is 
// * not rejected ie in a status that is accepted 
// * if the contract form number exists in the database with a contract that is not rejected
// * then the ocntract is rejected and the whole sheet is rejectede too
// * other wise the operation doens't raise any flags and continue importing 
// */
//   /*
//         res = stat.executeQuery("select GET_FORM_NUMBER_CONTRACTS ('"+contractFormNumber+"')   form_number_flag from dual" );          
//         res.next();
//         int contractFormNumberFlag = res.getInt("form_number_flag");
//
//         if (contractFormNumberFlag == 0 )
//         {
//           //the contract form number is valid since it doesn't exist with any contract that is in an accepted status
//         }
//         else if (contractFormNumberFlag == -1 )
//         {
//           //the function raised an exception due to the contract form number
//           this.errorVector.add(new ErrorInfo("CONTRACT", (row.getRowNum() + 1), " Form Number is corrupted or invalid"));
//           this.report.getSheet(currentSheetSerial).contracts.remove(currentContractId);
//           this.currentSheetHasNotImportedContracts = true;
//           return false;
//         }
//         else
//         {
//           //the contract form number is not valid since it does exist in the database associated with a contract
//           //that is in a PASS status
//           this.errorVector.add(new ErrorInfo("CONTRACT", (row.getRowNum() + 1), " Form Number exist in the database with a contract that is accepted"));
//           this.report.getSheet(currentSheetSerial).contracts.remove(currentContractId);
//           this.currentSheetHasNotImportedContracts = true;
//           return false;
//         }
//         res.close();
//     */             
//        /*
//         * this section verify that this contract form number is not there already in the excel file in above lines
//         * no duplicate check up for the contract form number
//         */
//         
//   //     String cntrctFormNumberInteger = contractFormNumber +"" ;
//        /*
//        if (contractFormNumbersInExcelFile.get(cntrctFormNumberInteger) == null)
//        {
//          //contract didn't come before in the excel file so there is no problem 
//          contractFormNumbersInExcelFile.put(cntrctFormNumberInteger, cntrctFormNumberInteger );
//        }
//        else
//        {
//           this.errorVector.add(new ErrorInfo("CONTRACT", (row.getRowNum() + 1), " Form Number is Duplicate in file"));
//           this.report.getSheet(currentSheetSerial).contracts.remove(currentContractId);
//           this.currentSheetHasNotImportedContracts = true;
//           return false;
//        }
//
//        */
//        
//        //res.close();
//                        
//
//        /*
//         * this code is to read the sim-swap and add it to the contract information 
//         */
//        Hashtable extraParameters = new Hashtable();
//        //debug((String)contractTypeObj);
//        if(contractTypeObj != null && ((String)contractTypeObj).compareTo("SIM-SWAP") == 0) {
//            boolean flag = this.readRowStatement("SIM-SWAP", extraParameters, row, this.contractFirstCellNum);
//            //debug("flag = " + flag);
//        }
//        Enumeration optParamkeys = extraParameters.keys();
//        Enumeration optParamvalues = extraParameters.elements();
//        while(optParamkeys.hasMoreElements()) {
//            String fieldName = (String)optParamkeys.nextElement();
//            String fieldValue = (String)optParamvalues.nextElement();
//        //    debug("field name " + fieldName + " fieldValue = " + fieldValue);
//            Object paramObj = optParamsTypes.get(fieldName);
//            if(paramObj != null) {
//             //   debug("param obj = null");
//                String fieldID = (String)paramObj;
//
//                sql = "insert into vw_cr_contract_opt_param (CONTRACT_OPT_PARAM_ID, CONTRACT_OPT_PARAM_TYPE_ID, CONTRACT_OPT_PARAM_VALUE,CONTRACT_ID)values(SEQ_CR_CONTRACT_OPT_PARAM_ID.nextval," + fieldID + "," + currentContractId + ",\'" + fieldValue + "')";
//                
//                contract.setInsertContractOptSQL(sql);
//            }
//        }
//      //  debug ("rejectContract = true" );
//        if(rejectContract) {
//            contract.setContractStatus(Contract.REJECTED);
//            contract.setWarningId(this.CONTRACT_WARNING_INVALID_CONTRACT_DETAILS);
//            contract.setWarningText(this.CONTRACT_WARNING_INVALID_CONTRACT_DETAILS_TEXT);
//        }
//        return true;
//    }

    /* 
     * 11-this method set the current sheet status 
     * also it keep certin rules
     * such as a rejectede sheet can't be set back to imported 
     */
    private void setCurrentSheetStatus(int status) {
        if(currentSheetStatus == this.SHEET_REJECTED_IMPORT) {
            return ;
        }
        else {
            if(currentSheetStatus == SHEET_INCOMPLETE) {
                if(status == this.SHEET_REJECTED_IMPORT) {
                    
                    //setCurrentSheetStatus( status);
                    currentSheetStatus = SHEET_REJECTED_IMPORT;
                }
            }
            else {
                currentSheetStatus = status;
            }
        }
    }

    /*
     * 12- init all status values from the database that will be used through the import process
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
            while(res.next()) {
                String statusName = res.getString("sheet_status_type_name");
                //status types of a sheet                
                int value = res.getInt("sheet_status_type_id");
                if(statusName.compareTo("NEW") == 0) {
                    this.SHEET_IMPORTED = value;
                    //debug("sheet_imported is set");
                }
                else {
                    if(statusName.compareTo("INCOMPLETE") == 0) {
                        this.SHEET_INCOMPLETE = value;
                        //debug("sheet_incomplete is set");
                    }
                    else {
                        if(statusName.compareTo("REJECTED IMPORT") == 0) {
                            this.SHEET_REJECTED_IMPORT = value;
                            //debug("sheet_rejected_import is set");
                        }
                    }
                }
            }
            res.close();
            
            //sheet warning ids for this phase 
            res = stat.executeQuery("select * from vw_cr_sheet_warning_type where phase_id = " + importPhaseId);
            while(res.next()) {
                String name = res.getString("sheet_warning_type_name");
                String id = res.getString("sheet_warning_type_id");
                if(SHEET_WARNING_COUNT_MISMATCH_TEXT.compareTo(name) == 0) {
                    SHEET_WARNING_COUNT_MISMATCH = id;
                }
                else {
                    if(SHEET_WARNING_MISSING_DATA_TEXT.compareTo(name) == 0) {
                        SHEET_WARNING_MISSING_DATA = id;
                    }
                    else {
                        if(SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS_TEXT.compareTo(name) == 0) {
                            SHEET_WARNING_REJECTED_OR_NOT_IMPORTED_CONTRACTS = id;
                        }
                    }
                }
            }
            res.close();
            //contract system warnings for the import phase
            res = stat.executeQuery("select * from vw_cr_Contract_warning_type where phase_id = " + importPhaseId);
            while(res.next()) {
                String name = res.getString("contract_warning_type_name");
                String id = res.getString("contract_warning_type_id");
                if(CONTRACT_WARNING_REJECTED_SHEET_TEXT.compareTo(name) == 0) {
                    CONTRACT_WARNING_REJECTED_SHEET_ID = id;
                }
                else {
                    if(CONTRACT_WARNING_INVALID_CONTRACT_DETAILS_TEXT.compareTo(name) == 0) {
                        CONTRACT_WARNING_INVALID_CONTRACT_DETAILS = id;
                    }
                }

            }

            res.close();
            
           //contract status 
            res = stat.executeQuery("select * from vw_cr_contract_status_type where contract_status_phase_id = " + importPhaseId);
            while(res.next()) {
                String contractStatusName = res.getString("contract_status_type_name");
                int contractStatusId = res.getInt("contract_status_type_id");
                //debug("init contract status = " + contractStatusName);
                if(contractStatusName.compareTo("IMPORTED") == 0) {
                    this.CONTRACT_IMPORT_STATUS = contractStatusId;
                    Contract.IMPORTED = contractStatusId;
                    //debug("contract import status was set to  " + contractStatusId);
                }
                else {
                    if(contractStatusName.compareTo("REJECTED IMPORT") == 0) {
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
            if(res.next()) {
                SHEET_SERIAL_WARNING_ID = res.getString(1);
            }
            res.close();

            
            //stat.close();
        }
        catch(Exception e) {
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
     * 14- fillHashTableWithResultSet take a sql and run it and fill the results in a hashtable and retur
     */
    private Hashtable fillHashTableWithResultSet(Statement stat, String sql, String key, String value) throws Exception {
        //debug("sql " + sql);
        Hashtable table = new Hashtable();
        ResultSet resultSet = stat.executeQuery(sql);
        while(resultSet.next()) {
            table.put(resultSet.getString(key).trim(), resultSet.getString(value).trim());
        }
        resultSet.close();
        return table;
    }

    /*
     * 15-insert sheet warningfor the current sheet
     */
    private void insertSheetWarning(String warningId, String warningText) 
    {        
      if (this.sheetWarning.containsKey(warningId))
      {    
      }
      else
      {
          sheetWarning.put(warningId, warningText);
      }     
    }

    /*
     * 16-validate dcm id 
     */
    private String validateDCMId(String dcmField, String dcmLevel, Hashtable sheetInfo, int rowNum, Statement stat) throws Exception {
        //debug("validate dcm id");
        Object dcmCodeObj = sheetInfo.get(dcmField);
        if(dcmCodeObj == null) {  
            errorVector.add(new ErrorInfo("Sheet", rowNum,"Error in sheet " +dcmField ));            
            return null;
        }
        String dcmCode = (String)dcmCodeObj;
           
        //to validate that this dcm code match for a dcm in the system and return the status 
     
        //Statement stat = con.createStatement();
        String dcmId =null;
    
        String sql = "select dcm_id from  vw_Gen_dcm_level, vw_gen_dcm where "
                   + "  dcm_code = \'" +dcmCode + "\'"
                   +" and vw_gen_dcm.DCM_LEVEL_ID = vw_Gen_dcm_level.DCM_LEVEL_ID  " ;
                

        if (dcmLevel.compareTo("DISTRIBUTER")==0)
        {
         sql += " and dcm_level_name ='DISTRIBUTER' " ;
        }
        else if (dcmLevel.compareTo("SUPER DEALER")==0)
        {
        //commented because of the franchise 
         //sql += " and dcm_level_name ='SUPER DEALER' " ;
        }
        else if (dcmLevel.compareTo("POS")==0)
        {
        //commented because of the franchise
           // sql += " and dcm_level_name in ('SUPER DEALER','POS') " ;
        }

        
        //debug ("validate dcm sql =  " + sql);
        startT =  System.currentTimeMillis();
        ResultSet res = stat.executeQuery(sql);
        debug("time to validate dcm = "+ ((System.currentTimeMillis()-startT) ));
        
        if (res.next()) {
          dcmId = res.getString("dcm_id");
        }
        if (dcmId == null) {
          errorVector.add(new ErrorInfo("Sheet", rowNum,"Error in sheet " +dcmField ));                
        }
        res.close();
        //stat.close();
        return dcmId;
    }


    /*
     * 17- fill a column in the sheet object using the data that result from query the dataabse with the sql sent to the method
     */
    private boolean fillColumn(Row row,String sql, String culName, String culIDName, Statement stat,Sheet sheet, int colNumber, int startRow) {
        try {
        	debug("sql statement:"+sql);
            ResultSet resultSet = stat.executeQuery(sql);
            int index = startRow;
            Row testRow = null;
            while(resultSet.next()) {
            	debug("culName:"+ culName);
            	debug("culIDName:"+ culIDName);
            	testRow = sheet.getRow(index);
            	
            	//System.out.println("testRow isss "+testRow);
            	
            	if (testRow==null)            	
                row = sheet.createRow(index);
            	else row = testRow;
            	
                //Row row = sheet.createRow((short)index);
                Cell cell = null;
                cell = row.createCell((short)colNumber);
                cell.setCellValue((String)resultSet.getString(culName));
                cell = row.createCell((short)(colNumber + 1));
                cell.setCellValue((String)resultSet.getString(culIDName));
                index++;
            }
            
            resultSet.close();
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean fillPaymentColumn(Row row,String sql, String culName, Statement stat, Sheet sheet, int colNumber, int startRow) {
        try {
        	debug("sql statement:"+sql);
            ResultSet resultSet = stat.executeQuery(sql);
            int index = startRow;
            Row testRow = null;
            
            while(resultSet.next()) {
            	debug("culName:"+ culName);
            	testRow = sheet.getRow(index);
            	if (testRow==null)            	
                row = sheet.createRow(index);
                else row = testRow;
            	//System.out.println("culIDName:"+ culIDName);
                //row = sheet.createRow((short)index);
                Cell cell = null;
                cell = row.createCell((short)colNumber);
                cell.setCellValue((String)resultSet.getString(culName));
               // cell = row.createCell((short)(colNumber + 1));
               // cell.setCellValue((String)resultSet.getString(culIDName));
                index++;
            }
            currentRow=index;
            resultSet.close();
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean fillDCMDetailsColumn(Row row,String cellVal, Sheet sheet, int colNumber, int startRow,CellStyle style) {
        try {
        	
            
        	Row testRow = null;
            	//System.out.println("culIDName:"+ culIDName);
        	testRow = sheet.getRow(startRow);
        	
        	//System.out.println("testRow isss "+testRow);
        	
        	if (testRow==null)            	
            row = sheet.createRow(startRow);
        	else row = testRow;
                //Row row = sheet.createRow(startRow);
                Cell cell = null;
                cell = row.createCell((short)colNumber);
                cell.setCellValue(cellVal);
                if(style!=null){
                	cell.setCellStyle(style);
                	debug("style innnnnnnnnnnnnnnnnnnnnnnnnnnnn column: "+style.getFillBackgroundColor()+" here");
                }
               // cell = row.createCell((short)(colNumber + 1));
               // cell.setCellValue((String)resultSet.getString(culIDName));
                
            
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean fillMemoMonitorColumn(Row row,String cellValue, String culName, Sheet sheet, int colNumber, int startRow) {
        try {
        	
            int index = startRow;
            debug("culName:"+ culName);
           	//System.out.println("culIDName:"+ culIDName);
            //Row row = sheet.createRow((short)index);
            Row testRow = null;
            testRow = sheet.getRow(index);
        	
//        	System.out.println("testRow isss "+testRow);
        	
        	if (testRow==null)            	
            row = sheet.createRow(index);
        	else row = testRow;
            Cell cell = null;
            cell = row.createCell((short)colNumber);
            cell.setCellValue(cellValue);
            // cell = row.createCell((short)(colNumber + 1));
            // cell.setCellValue((String)resultSet.getString(culIDName));
            index++;
           
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    

/*
 * 18- this is a debuging function taht print debuging information from the class if the debug flag is set to true
 *    however make sure to set the debugFlag to false after debugin casue it reduce performance of this module 
 */
    private static void debug(String s) {
        if(debugFlag) {
            System.out.println(s);
        }
    }

    
}

/*
 * this is a class act as a holder for the template item defined in the database taht is being used
 * by the import engine 
 * 1- read the item from the row object
 * 2-get the item name
 * 3-constructor takes the name and the mandatory flag and the order and the type 
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
//    public String readItem(Row row, Hashtable valueTable, int initialStartReadCol) {
//        
//        //Utility.logger.debug("reading cell " + this.name);
//        Cell cell = row.getCell((short)(order + initialStartReadCol));
//        if(cell == null && mandatory) {
//         //  Utility.logger.debug(" the celll is empty "+ row.getRowNum());
//            return name;
//        }
//        else {
//            String value = com.mobinil.sds.core.system.cr.excelImport.ExcelImport.readCell(cell);
//             //Utility.logger.debug(name + " , " + value +"||");
//
//            /*
//             * this condition to solve the bug that was introduced during to names with ' in it
//             */
//            if (value !=null && value.indexOf('\'')!=-1)
//            {
//              return name;
//            }
//            
//            if(value == null || value.compareTo("") == 0 ) {
//                if(this.mandatory) {
//                    return name;
//                }
//                else {
//                    return null;
//                }
//            }
//            else {
//
//                if (value.length()> this.maxSize)
//                {
//                //Utility.logger.debug("greater than the max size  max =" +maxSize +"    value="+ value.length() +"== " + value);
//                  return name;
//                }
//                
//                if(this.type.compareTo("D") == 0) /*date type*/ {
//                    try {
//                        if(Date.parse(value) < 0) {
//                            return null;
//                        }
//                    }
//                    catch(Exception e) {
//                        return name;
//                    }
//                }
//                else {
//                    if(this.type.compareTo("N") == 0) {
//                        try {
//                            Float.parseFloat(value);
//                        }
//                        catch(NumberFormatException e) {
//                        //    Utility.logger.debug(value);
//                       //        e.printStackTrace();
//                            return name;
//                        }
//                    }
//                }
//                valueTable.put(name, value);
//                
//                //   Utility.logger.debug("value = " + value);
//                return null;
//            }
//        }
//    }
    /*
     * 2-get the item name 
     */
    public String getName() {
        return name;
    }

    /*
     * 3-constructor takes the name and the mandatory flag and the order and the type 
     */
    Template_Def_Item(String name, boolean mandatory, int order, String type , int maxSize) {
        this.name = name;
        this.mandatory = mandatory;
        this.order = order;
        this.type = type;
        this.maxSize = maxSize;
    }
}

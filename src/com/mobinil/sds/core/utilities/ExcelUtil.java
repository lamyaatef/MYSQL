package com.mobinil.sds.core.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.sql.*;
import java.util.Date;
import com.mobinil.sds.core.utilities.*;

import java.text.*;
//import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil 
{
  public ExcelUtil()
  {
    try
    {
      initConnection();
      initLookUpTables();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }


  private static final String dbConnectionString = "jdbc:oracle:thin:@10.1.17.30:1521:sds";
  private static final String dbUserName= "sds";
  private static final String dbPassword = "sds01";

  private static final boolean debugFlag = false; 

  private static final int sheetFirstCellNum = 0; 
  private static final int contractFirstCellNum = 9;

  private static String SHEET_DISTRIBUTER_ID = "SHEET_DISTRIBUTER_CODE";
  private static String SHEET_SERIAL_FIELD = "SHEET_SERIAL_ID";
  private static String SHEET_POS_FIELD = "SHEET_POS_CODE";
  private static String SHEET_SUPER_DEALER_FIELD = "SHEET_SUPER_DEALER_CODE";
  private static String SHEET_TYPE_FIELD = "SHEET_TYPE_ID";
  //private static String Contracts_Count_FIELD  = "Contracts Count";
  private static String SHEET_RECIEVE_DATE = "SHEET_RECIEVE_DATE";
  private static String SHEET_CONTRACT_COUNT = "SHEET_CONTRACT_COUNT";
  private static String SHEET_ID = "SHEET_ID";
  private static String CONTRACT_IMPORT_STATUS = "1";

  private static String SHEET_STATUS_NEW= "1";
  private static String INCOMPLETE = "2";

  private static String importPhaseId = "1";
  
  
  private Connection con ; 
  private Hashtable sheetTypes;
  private Hashtable productTypes;
  private Hashtable idTypes;
  private Hashtable sheetWarnings; 
  private Hashtable optParamsTypes; 

  private Vector errorVector  = new Vector();
  private Vector insertVector =  new Vector();
  private String currentSheetId ;
  private Hashtable statTable;

  private boolean currentSheetHasNotImportedContracts = false;

  private int currentSheetContractsNum = 0;
  

  private String validateDCMId(String dcmField , Hashtable sheetInfo, int rowNum )throws Exception
  {

    Object dcmCodeObj = sheetInfo.get(SHEET_DISTRIBUTER_ID) ;
    if (dcmCodeObj == null)
    {
      //errorVector.add("Error in sheet " +dcmField + "  on row " + rowNum);
      return "";
    }

    String dcmCode  = (String) dcmCodeObj;
    return dcmCode; 

/* 
 //to validate that this dcm code match for a dcm in the system and return the status 
 //was commented sicne it is more than required in this stage 
    Statement stat = con.createStatement();
    String dcmId ="";
    
    String sql = "select dcm_id from vw_gen_dcm where dcm_code = \'" +dcmCode + "\'" ;
    debug ("validate dcm sql =  " + sql);
    ResultSet res = stat.executeQuery(sql);
    if (!res.next())
    {
        errorVector.add("Error in sheet " +dcmField + "  on row " + rowNum +" is not valid");
        return null ; 
    }
    else
    {
     dcmId = res.getString("dcm_id");
     return dcmId;
    };
*/
  }
  
  
  private  boolean validateSheet(Hashtable sheetInfo, int rowNum) throws Exception
  {
      Statement stat = con.createStatement();

      
      
      //verify the sheet type exist
      String sheetTypeId="";      


      String sheetContractCount = (String)sheetInfo.get(SHEET_CONTRACT_COUNT);
      if  (sheetContractCount==null)
      {
        sheetContractCount ="";
      }
  //sheet Type verification 
      Object sheetTypeNameObj = sheetInfo.get(SHEET_TYPE_FIELD);
      if (sheetTypeNameObj !=null)
      {
       Object sheetTypeIdObj = sheetTypes.get(sheetTypeNameObj);
       if (sheetTypeIdObj!=null)
       sheetTypeId = (String)sheetTypeIdObj;
       else
       {
         // errorVector.add("Error in sheet Type on row " + rowNum);
         //  return false; 
       }
      }
      //verify dcm id + pos id + super dealer      
          
      String sheetDistributerId =validateDCMId (SHEET_DISTRIBUTER_ID, sheetInfo, rowNum);
   /*
    * if (sheetDistributerId == null)
      return false;
    */   
      String sheetSuperDealerId =validateDCMId (SHEET_SUPER_DEALER_FIELD, sheetInfo, rowNum);
     /*
      * if (sheetSuperDealerId == null)
        return false;
      */
      
      String sheetPosId =validateDCMId (SHEET_POS_FIELD, sheetInfo, rowNum);
      /*
       * if (sheetPosId == null)
          return false;
      */


//verify that this sheet serial number exist
      String currentSheetSerial = (String) sheetInfo.get(SHEET_SERIAL_FIELD);

      String sheetRecieveDate="";
      
      if (sheetInfo.get(SHEET_RECIEVE_DATE)!=null)
      sheetRecieveDate = (String) sheetInfo.get(SHEET_RECIEVE_DATE);
      


//verifty that this sheet serial number beolong to this guy
//insertion + adding warning

      ResultSet res = stat.executeQuery("select * from VW_CR_DCM_SHEET where SHEET_SERIAL_ID= " +currentSheetSerial );

        
      if (res.next())
      {
        Statement statq = con.createStatement();
        ResultSet resq = statq.executeQuery("select SEQ_SHEET_ID.nextval from dual");
        resq.next();
        this.currentSheetId = resq.getString(1);
        
      //if the sheet serial exist then we can insert it to the system 
        String sql = "insert into vw_cr_sheet (sheet_id, SHEET_DISTRIBUTER_code,SHEET_SUPER_DEALER_code,SHEET_POS_code,SHEET_TYPE_ID,SHEET_SERIAL_ID,SHEET_RECIEVE_DATE,sheet_time_stamp ,SHEET_CONTRACT_COUNT) VALUES (" +currentSheetId+",";        
        sql += "\'"+sheetDistributerId +"\',\'"+ sheetSuperDealerId + "\',\'" + sheetPosId+"\',"+sheetTypeId +"," + currentSheetSerial+"," + "TO_DATE(\'" + sheetRecieveDate+"\',\'DD\\MM\\YYYY\'),"+ "TO_DATE(\'" + timeStampS+"\',\'DD\\MM\\YYYY HH24:MI:ss\'),\'"+sheetContractCount+"\')"   ;        
                 
/*
        String sql = "insert into vw_cr_sheet (";
        String valuesSql ="";

        Enumeration keys = sheetInfo.keys();
        Enumeration values = sheetInfo.elements();

        while (keys.hasMoreElements())
        {
          String key = (String) keys.nextElement();
          String value =(String) values.nextElement();
          sql += key + ",";
          valuesSql += "\'" + value + "\'," ;          
        }
        sql = sql.substring(0,sql.length()-1) + ") values(";
        valuesSql = valuesSql.substring(0,valuesSql.length()-1) +")";

        sql +=valuesSql;

  */

        //Statement insertStatement = con.createStatement();        
        debug ("insert sql = " + sql);
        //boolean insertStatusFlag = insertStatement.execute(sql);
        // debug("insert sheet flag = " +insertStatusFlag +"");
        insertVector.add(sql);
                
        if (sheetDistributerId.compareTo(res.getString("DCM_ID"))!=0 )                
        {
          //insert warning
        }
        return true; 
      }
      else
      {
        errorVector.add("Error, Sheet Serial Number " + currentSheetSerial +" does not exist");              
        return false; 
      }            
  }    
  private Date timeStamp;
  private String timeStampS;


  public boolean readRowStatement (String statementType , Hashtable valuesTable  , HSSFRow row , int readStartCell )
  {
    Vector sheetStat = (Vector)(statTable.get(statementType));
    if (sheetStat==null)
    {
      debug("the vector of stat of this type "+ statementType + " is null");
      return true; 
    }
    boolean sheetStatusImport = true ; 
  
    String  invalidSheetReadItem =  null;
    for (int j = 0 ; j < sheetStat.size(); j++)
    {
      Template_Def_Item defItem =  (Template_Def_Item) sheetStat.get(j);
      String tempReadValue = defItem.readItem(row, valuesTable , readStartCell);     
      if (tempReadValue !=null)
       {
         if (invalidSheetReadItem!=null)
          {
            invalidSheetReadItem += ", " + tempReadValue;
           }
         else
          {
            invalidSheetReadItem = tempReadValue;
          }
        }
    }

      if (invalidSheetReadItem!=null)
       {
           errorVector.add("Error in "+statementType+" Import. The "+statementType+" Statment on row Number " + row.getRowNum() +"  has the following fields empty or corrupted date in it");
           errorVector.add(invalidSheetReadItem);
           return false;               
       }
      else
       {
        return true;                                    
      }
            
  }

  private void insertToDB(Vector insertVector)
  {
  debug("mmmmm");
    if (insertVector.size()<1) //sheet with no contracts should not be inserted
    {
      insertVector.clear();
      return ;      
    }
    
     String sheetStatusSql ;    
    try
    {
      Statement insertStat = con.createStatement();
      for (int i = 0 ; i<insertVector.size();i++)
      {
        debug ((String)insertVector.get(i));
        insertStat.execute((String)insertVector.get(i));
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    //  errorVector.add(e.toString());
      insertVector.clear();
    }
  }
  
  private static final String Id_No_Field ="contract_customer_id";
  private static final String CONTRACT_TYPE_FIELD =  "CONTRACT_TYPE";
  private static final String CONTRACT_ID = "CONTRACT_ID";

  private boolean validateContract(Hashtable infoTable, HSSFRow row) throws Exception
  {

      debug("validate contract" );
      
      Object idTypeObj =  infoTable.get(Id_No_Field);
      if (idTypeObj !=null )
      {
        String idTypeString =(String) this.idTypes.get((String)idTypeObj);
        if (idTypeString != null)
        {
          infoTable.put(Id_No_Field ,idTypeString );
        }
        else
        {
          infoTable.remove(Id_No_Field);
        }
      }

      Object contractTypeObj =  infoTable.get(CONTRACT_TYPE_FIELD);
      if (contractTypeObj !=null )
      {
        String contractTypeString = (String)this.productTypes.get((String)contractTypeObj);
        debug ("contract type = " + contractTypeString);
        if (contractTypeString != null)
        {
          infoTable.put(CONTRACT_TYPE_FIELD ,contractTypeString );
        }
        else
        {
          infoTable.remove(CONTRACT_TYPE_FIELD);
        }
      } 

        infoTable.put(this.SHEET_ID, this.currentSheetId);
        
        Statement stat = con.createStatement();
        ResultSet res = stat.executeQuery("select SEQ_CR_CONTRACT_ID.nextval from dual");
        res.next();
        String currentContractId  = res.getString(1);

        infoTable.put(CONTRACT_ID, currentContractId);
                                              
        String sql = "insert into vw_cr_contract (";    
        String valuesSql ="";

        Enumeration keys = infoTable.keys();
        Enumeration values = infoTable.elements();

        while (keys.hasMoreElements())
        {
          String key = (String) keys.nextElement();
          String value =(String) values.nextElement();
          sql += key + ",";
          valuesSql += "\'" + value + "\'," ;          
        }
        sql = sql.substring(0,sql.length()-1) + ") values(";
        valuesSql = valuesSql.substring(0,valuesSql.length()-1) +")";
        sql +=valuesSql;    

        this.insertVector.add(sql);

        Statement statQ  = con.createStatement();
        ResultSet resQ = statQ.executeQuery("select SEQ_CR_CONTRACT_STATUS.nextval from dual");
        resQ.next();
        String contractStatusId = resQ.getString(1);                                                
        String statusInsertSQL = "insert into vw_cr_contract_status (contract_STATUS_ID,contract_ID,contract_STATUS_TYPE_ID,contract_STATUS_DATE) values "+
              "("+contractStatusId+","+ currentContractId +","+CONTRACT_IMPORT_STATUS +","+ "TO_DATE(\'" + timeStampS+"\',\'DD\\MM\\YYYY HH24:MI:ss\')"+")";                            
        this.insertVector.add(statusInsertSQL); 

        Hashtable extraParameters = new Hashtable();

        debug((String)contractTypeObj);
        if ( contractTypeObj!=null && ((String)contractTypeObj).compareTo("SIM-SWAP")==0)
        {
          boolean flag = this.readRowStatement("SIM-SWAP",extraParameters,row,this.contractFirstCellNum);
          debug ("flag = " + flag);
        }

        Enumeration optParamkeys = extraParameters.keys();
        Enumeration  optParamvalues = extraParameters.elements();
        while ( optParamkeys.hasMoreElements())
        {
        
          String fieldName = (String) optParamkeys.nextElement();
          String fieldValue = (String) optParamvalues.nextElement();
          debug("field name " + fieldName +" fieldValue = " + fieldValue);  

          Object paramObj = optParamsTypes.get(fieldName);
          
          if (paramObj!=null)
          {        
            debug("param obj = null");
            String fieldID = (String)paramObj;
            sql = "insert into vw_cr_contract_opt_param (CONTRACT_OPT_PARAM_ID, CONTRACT_OPT_PARAM_TYPE_ID, CONTRACT_OPT_PARAM_VALUE,CONTRACT_ID)values(SEQ_CR_CONTRACT_OPT_PARAM_ID.nextval,"+fieldID+","+currentContractId+",\'"+fieldValue+"')";
            this.insertVector.add(sql);
          }
        }
        
        
        return true; 
  }

  public void insertSheetToDB() 
  {
  debug("insert Sheet To Db");

 try{
     Statement statQ  = con.createStatement();
              ResultSet resQ = statQ.executeQuery("select SEQ_CR_SHEET_STATUS.nextval from dual");
              resQ.next();
              String sheetStatusId = resQ.getString(1); 
              
              String sheetStatus ;
              if (this.currentSheetHasNotImportedContracts)
              {
                sheetStatus =SHEET_STATUS_NEW;
              }
              else
              {
                sheetStatus =INCOMPLETE;
              }                            
              String statusInsertSQL = "insert into vw_cr_sheet_status (SHEET_STATUS_ID,SHEET_ID,SHEET_STATUS_TYPE_ID,SHEET_STATUS_DATE) values "+
              "("+sheetStatusId+","+this.currentSheetId+","+sheetStatus +","+ "TO_DATE(\'" + timeStampS+"\',\'DD\\MM\\YYYY HH24:MI:ss\')"+")";
              this.insertVector.add(statusInsertSQL);
              insertToDB(this.insertVector );             
              currentSheetHasNotImportedContracts = false;                            
 }
 catch 
 (Exception e)
 {
 debug("insert sheet");
 e.printStackTrace(); 
 }
 
  }
  
  public boolean ImportFile(String fileName)
  {
    timeStamp = new Date();    
    timeStampS= timeStamp.getDate() +"\\"+(timeStamp.getMonth() +1) +"\\"+(timeStamp.getYear()+1900)+" " +timeStamp.getHours()+":"+timeStamp.getMinutes()+":"+timeStamp.getSeconds();                
    try
    {
        Statement stat = con.createStatement();                          
        ResultSet res =  stat.executeQuery("select * from VW_CR_TEMPLATE_DEF order by template_def_type_id,TEMPLATE_DEF_ORDER");
        String lastDefType = null; 
        Vector statDefVec = null;
        statTable = new Hashtable();
        while (res.next())
        {         
           String typeName = res.getString("TEMPLATE_DEF_TYPE_NAME");
           String keyName  = res.getString("TEMPLATE_DEF_NAME"); 
           boolean mandatory = res.getString("TEMPLATE_DEF_MANDATORY").compareTo("1")==0 ? true :false;     
           String productName = res.getString("PRODUCT_NAME");           
           int order = res.getInt("TEMPLATE_DEF_ORDER");
           String type = res.getString( "TEMPLATE_DEF_DATA_TYPE");           
           if (lastDefType== null || typeName.compareTo(lastDefType)!=0)
           {
            if (lastDefType!=null)
            {
              statTable.put(lastDefType, statDefVec);
            }
             lastDefType = typeName;             
             statDefVec =new Vector();             
           }

           Template_Def_Item defItem = new Template_Def_Item(keyName,mandatory,order,type);
           statDefVec.add(defItem);                           
        }     
        
        if (lastDefType!=null)
        statTable.put(lastDefType, statDefVec);

      FileInputStream tempIn = new FileInputStream(fileName); 
      HSSFWorkbook wb = new HSSFWorkbook(tempIn);  
      HSSFSheet sheet = wb.getSheetAt(0);
      int lastRowNum = sheet.getLastRowNum();
      debug("Last Row Number = " + lastRowNum);          
      for (int i = 1 ; i < lastRowNum ; i++)
      {
        HSSFRow row = sheet.getRow(i);
        if (row !=null)
        {
          HSSFCell cell;          
          cell = row.getCell((short) sheetFirstCellNum );  

          if (cell!=null && (readCell(cell).compareTo("")!=0))
          {
            if (this.insertVector.size()>0)
           {
             insertSheetToDB();
           }
           
           Hashtable sheetTable = new Hashtable();
           if (this.readRowStatement("SHEET",sheetTable,row,this.sheetFirstCellNum))
           {
             boolean isSheetValid = validateSheet (sheetTable, i);
             debug ("isSheetValid = " + isSheetValid);              
             if (!isSheetValid )
              {                
                this.currentSheetId =null;
                
                if (this.debugFlag)
                {
                  for (int z = 0; z <errorVector.size(); z++)
                  {
                    Utility.logger.debug((String) errorVector.get(z));
                  }
                }
              }
           }
          }
          
          
          cell = row.getCell((short) contractFirstCellNum );  
          if (currentSheetId!=null && cell!=null && (readCell(cell).compareTo("")!=0))
          {
           Hashtable contractTable = new Hashtable();
           if (this.readRowStatement("CONTRACT",contractTable,row,this.contractFirstCellNum))           
           {
              Utility.logger.debug("done reading");
              boolean isContractValid = validateContract (contractTable, row);
              if (!isContractValid)
              {
                currentSheetHasNotImportedContracts=false;
              }
              
           }     
           else
           {
            currentSheetHasNotImportedContracts = false;
           }                       
          }          
        }
      }
      
    debug("before final insert");
    if(this.insertVector.size()>1)
    {
      insertSheetToDB();
    }
    
    for (int z = 0 ; z<this.errorVector.size();z++)
    {
      Utility.logger.debug(this.errorVector.get(z));
    }
      
    return true; 
    }
    catch (Exception e )
    {
      Utility.logger.debug("last exception");
      e.printStackTrace();
      return false ; 
    }      
  }
       

  public  boolean readFile (String fileName)
  {
    try
    {
      FileInputStream tempIn = new FileInputStream(fileName); 
      HSSFWorkbook wb = new HSSFWorkbook(tempIn);  
      HSSFSheet sheet = wb.getSheetAt(0);
      int lastRowNum = sheet.getLastRowNum();
      Utility.logger.debug("Last Row Number = " + lastRowNum);
      for (int i = 0 ; i < lastRowNum ; i++)
      {
        HSSFRow row = sheet.getRow(i);
        if (row !=null)
        {
          HSSFCell cell = row.getCell((short)0 );  
          if (cell!=null)
          {
           String temp = ExcelUtil.readCell(cell);
          }
        //  Utility.logger.debug(cell.getStringCellValue());
        }
      }
      
    return true; 
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false; 
    }
  }

  public  boolean exportTemplate(String template, String fileName)
  {
    try
    {
      FileInputStream tempIn = new FileInputStream(template); 
      HSSFWorkbook wb = new HSSFWorkbook(tempIn);
      HSSFSheet lookUpSheet =  wb.getSheet("lookup");
      DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());

      Connection con = DriverManager.getConnection (dbConnectionString, dbUserName, dbPassword);      
                             // @machineName:port:SID,   userid,  password

       //  Connection con = Utility.getConnection();         
         Statement stat = con.createStatement();
         fillColumn("select SHEET_TYPE_NAME, SHEET_TYPE_ID from VW_CR_SHEET_TYPE order by SHEET_TYPE_NAME ASC", "SHEET_TYPE_NAME" , "SHEET_TYPE_ID", stat, lookUpSheet , 0, 0);
         fillColumn("select PRODUCT_NAME, PRODUCT_ID from VW_GEN_PRODUCT ORDER BY PRODUCT_NAME ASC", "PRODUCT_NAME" ,"PRODUCT_ID", stat, lookUpSheet , 2, 0);
         fillColumn("select NAME, ID  from VW_CR_ID_TYPE ORDER BY NAME ASC", "NAME", "ID" , stat, lookUpSheet , 4, 0);

       //  fillColumn("select PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
   
         HSSFSheet dataSheet =  wb.getSheet("Data");
         dataSheet.setSelected(true);
         lookUpSheet.setSelected(false);
         dataSheet.setSelected(true);
         dataSheet.createRow(1).createCell((short)0).setCellValue("");                  
         FileOutputStream fileOut = new FileOutputStream(fileName);          
         wb.write(fileOut);
          
         fileOut.close();      
         con.close();
          return true;            
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false; 
    }
  }
  

  
  public static void main(String[] args)
  {
//    ExcelUtil excelUtil = new ExcelUtil();
   // ExcelUtil.exportTemplate("c:/template.xls", "c:/man.xls");
   //excelUtil.readFile("c:/test/mm.xls");
//    excelUtil.ImportFile("c:/test/man.xls");
      
//          ExcelUtil e = new ExcelUtil();
    Vector<String> dd  = new Vector<String>();
    dd.add("asdasd");
    dd.add("asdasd");
    dd.add("asdasd");
    dd.add("asdasd");
        try {
            writeExcelFile(dd, "C:\\testExcel");
        //   Vector<String> dd = e.readExcelFirstCol(f);
        //      for (String string : dd) {
        //          System.out.println(string);
        //      }
        //      }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    
  }

  private void initLookUpTables() throws Exception
  {
    Statement stat = con.createStatement();
    sheetTypes = fillHashTableWithResultSet(stat, "select SHEET_TYPE_NAME, SHEET_TYPE_ID from VW_CR_SHEET_TYPE" , "SHEET_TYPE_NAME" , "SHEET_TYPE_ID");
    productTypes = fillHashTableWithResultSet(stat,"select PRODUCT_NAME, PRODUCT_ID from VW_GEN_PRODUCT " , "PRODUCT_NAME" , "PRODUCT_ID");
    optParamsTypes = fillHashTableWithResultSet(stat,"select * from cr_contract_opt_param_type","CONTRACT_OPT_PARAM_TYPE_NAME","CONTRACT_OPT_PARAM_TYPE_ID");
    idTypes = fillHashTableWithResultSet(stat,"select NAME, ID  from VW_CR_ID_TYPE " , "NAME" , "ID");
    sheetWarnings = fillHashTableWithResultSet(stat,"select SHEET_WARNING_TYPE_NAME, SHEET_WARNING_TYPE_ID  from VW_CR_SHEET_WARNING_TYPE  where phase_id = " + importPhaseId , "SHEET_WARNING_TYPE_NAME" , "SHEET_WARNING_TYPE_ID ");
        
  }

  private void initConnection() throws Exception 
  {
    DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
    con = DriverManager.getConnection (dbConnectionString, dbUserName, dbPassword);          
  }

  
    private  Hashtable fillHashTableWithResultSet( Statement stat, String sql, String key, String value) throws Exception
  {
    debug("sql " + sql);
    Hashtable table = new Hashtable();
    ResultSet resultSet = stat.executeQuery(sql);  
    while (resultSet.next())
    {
      table.put(resultSet.getString(key),resultSet.getString(value));
    }
    return table; 
  }

    private static void debug( String s ) 
  {
    if (debugFlag)
    Utility.logger.debug(s);
  }


  public  static String readCell(HSSFCell cell)
  {
    if (cell==null)
    return "";
    
    switch (cell.getCellType())
    {
      case HSSFCell.CELL_TYPE_BLANK :
      return "";
      case HSSFCell.CELL_TYPE_BOOLEAN:
      if (cell.getBooleanCellValue())
      return "TRUE";
      else
      return "FALSE";
      case HSSFCell.CELL_TYPE_ERROR:
      Utility.logger.debug("Error in cell reading" );
      return "";
      case HSSFCell.CELL_TYPE_FORMULA:
      Utility.logger.debug("Cell is a formula" );
      return "";
      case HSSFCell.CELL_TYPE_NUMERIC:
      return cell.getNumericCellValue()+"";
      case HSSFCell.CELL_TYPE_STRING:
      return cell.getStringCellValue();  
      default :
      return "";
    }
  }


  private boolean fillColumn(String sql, String culName ,String culIDName,  Statement stat , HSSFSheet sheet , int colNumber , int startRow)
  {
      try
      {
         ResultSet resultSet = stat.executeQuery(sql);                
         int index = startRow ; 
         while (resultSet.next())
         {          
            HSSFRow row = sheet.createRow((short)index);         
            HSSFCell cell =null;
            cell   = row.createCell((short)colNumber);
            cell.setCellValue((String)resultSet.getString(culName));  
            cell = row.createCell((short)(colNumber+1));
            cell.setCellValue((String)resultSet.getString(culIDName));  
            
            index++;
         }    
         return true; 
      }
      catch (Exception e)
      {
        e.printStackTrace();
        return false; 
      }
  }
  
  
private  static org.apache.poi.ss.usermodel.Workbook getWorkBook(File file){
    String fileNameOnClient = file.getAbsolutePath();
InputStream inp = null;
        try {
            inp = new FileInputStream(fileNameOnClient);
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found In " + fileNameOnClient);
            Utility.logger.debug(ex);
        }
        
        org.apache.poi.ss.usermodel.Workbook wb = null;
        
        try {
            
            wb = fileNameOnClient.toLowerCase().contains(".xlsx") ?   WorkbookFactory.create(inp) : 
                    new HSSFWorkbook(inp);
        } catch (Exception ex) {
            System.out.println("IO Exception : " + ex.getMessage());            
            ex.printStackTrace();
            Utility.logger.debug(ex);
        }
        try {
            inp.close();
        } catch (IOException ex) {
            Utility.logger.debug(ex);
        }
        return wb;
}
public static Vector<String> readExcelFirstCol(File file) {
        
        Vector<String> values = new Vector<String>();        
        Sheet sheet = getWorkBook(file).getSheetAt(0);
        int lastRowNumber = sheet.getLastRowNum()+1;
        for (int cellIndx = 1; cellIndx < lastRowNumber; cellIndx++) {
            Row row = sheet.getRow(cellIndx);
            Cell cell = row.getCell(0);
            if (cell != null) {
                if (cell.getCellType() == Cell.CELL_TYPE_STRING ) {
                    values.add(cell.getStringCellValue());
                }
            }
        }
        
        return values;
    }

    public static String writeExcelFile(Vector<String> errors , String path) throws FileNotFoundException, IOException{
        String fileUniqueName = path+System.getProperty("file.separator") +System.currentTimeMillis() + ".xls";
    HSSFWorkbook wb = new HSSFWorkbook();
    Sheet sheet1 = wb.createSheet("Sheet1");
    int lastRow = errors.size();
        for (int i=0;i<lastRow;i++) {
            Row row = sheet1.createRow((short)i);
            row.createCell(0).setCellValue(errors.get(i));
            row.createCell(1).setCellValue("Invalid STK number.");
        }
    FileOutputStream fileOut = new FileOutputStream(fileUniqueName);
    wb.write(fileOut);
    fileOut.close();
    return fileUniqueName;
    }
    
}



class Template_Def_Item
        {
          String name ;
          boolean mandatory;
          int order;
          String type;

          public String getName()
          {
            return name;
          }
          
          Template_Def_Item (String name, boolean mandatory , int order,String type)
          {
            this.name= name; 
            this.mandatory = mandatory;             
            this.order = order; 
            this.type = type ;
          }          

          public String  readItem(HSSFRow row , Hashtable valueTable , int initialStartReadCol )
          {
            //Utility.logger.debug("reading cell " + this.name);
            HSSFCell cell = row.getCell((short)(order + initialStartReadCol));
            if (cell ==null && mandatory)
            {
              return name; 
            }
            else
            {
              String value = ExcelUtil.readCell(cell);              
              if (value ==null || value.compareTo("")==0) 
              {
                if (this.mandatory)
                return name;
                else
                return null;
              }
              else
              {
                if (this.type.compareTo("D")==0) //date type
                {
                  try{
                  if (Date.parse(value)<0)
                  {
                    return null; 
                  }   
                  }
                  catch (Exception e)
                  {                  
                    return name ;
                  }
                }
                else if (this.type.compareTo("N")==0)
                {
                  try{
                  Float.parseFloat(value);
                  }
                  catch (NumberFormatException e)
                  {
               //   e.printStackTrace();
                    return name; 
                  }
                }
              
               valueTable.put(name,value);
            //   Utility.logger.debug("value = " + value);
               return null; 
              }
            }

    }

    
}
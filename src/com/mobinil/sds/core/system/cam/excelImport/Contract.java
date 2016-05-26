package com.mobinil.sds.core.system.cam.excelImport;
import java.util.*;
import java.sql.*;
import java.util.Date;
import com.mobinil.sds.core.utilities.Utility;
  /*
  * This class is responsible of keeping all the information of the contract 
  * while the excel parsing engine parse the excel data 
  * also it handel the insertion of the contract and the contract status 
  * to the database 
  * 
  * 1-construcing a contract object 
  * 2-setting status 
  * 3-adding opt parameters 
  * 4-insert to database contract info , status and opt parameters
  * 5-get contract status name
  * 6-get contract row number 
  * 7-get status of the contract 
  * 8- set time stamp of the contract
  * 9-get the warning id on the contract 
  *  this is based on the assumption that there is only one warning 
  *  that can put in the import phase on a contract 
  *  if this assumption changes the part of handling the warning 
  *  id and the warning name in this code need to be adjusted 
  *  to be similar to the warning in the excelimport.java 
  *  that hold all the warning of current sheet 
  *  or to be similar to the sheet.java whihc has a hashtable to 
  *  hold its warning

  * 10- set the warning id   
  * 11- get the warning text
  * 12- set the warning text 
  */

public class Contract 
{
 //those values are re initialized from ExcelImport
 //by reading their values from the database 
 //the init of it should be reallocated from the excelimport to this class 
  public static int IMPORTED = 1;  
  public static int REJECTED = 2;
  // debug flag if turend to false no debuging info shall be printed 
  private static final boolean debug = false;

  private int rowNum;
  private int status ;
  private String name; 
  private String insertSQL ;
  private String optSQL =null;
  private String timeStamp;
  private String contractId ;
  String warningId=null;
  String warningText=null;
  String userId;

  

  
  // 4-insert to database contract info , status and opt parameters
  //the insert sql is constructed in the excelimport since 
  //the dynamic property of field adding and optional parameters 
  //make it easier to construct the sql in the excel import 
  public void setInsertContractSQL(String contractInsert) 
  {
    this.insertSQL = contractInsert;
  }

  public String getName()
  {
    return this.name;
  }

  //7-get status of the contract 
  public int getStatus()
  {
    return this.status;
  }

  // 3-adding opt parameters 
  public void setInsertContractOptSQL(String sql)
  {
    this.optSQL = sql; 
  }

// 4-insert to database contract info , status and opt parameters
  //insert to the database 
  //1-the contract it self 
  //2-status
  //3-optional paramerters if exist
   String statusInsertSQL ;
   String warningSQL ;

   static final String contract_warning_sql_constant=  "insert into  cr_contract_warning(contract_warning_id,contract_status_id,contract_warning_date,contract_Warning_type_id)values(SEQ_CR_contract_WARNING.nextval,";

   
   static final String insert_contract_Status_sql_constant = "insert into CR_CONTRACT_STATUS (user_id,contract_STATUS_ID,contract_ID,contract_STATUS_TYPE_ID,contract_STATUS_DATE) values ";
  void insertRecord(Statement stat , Statement queryStat )
  {    
    statusInsertSQL = null;
   String warningSQL = null;
   long stime = 0;
   
    try
    {        
      //debug (insertSQL);    
     // stime = System.currentTimeMillis();
      ResultSet res = queryStat.executeQuery("select SEQ_CR_CONTRACT_STATUS.nextval from dual ");
      //debug("time to get contract status seq = " + (System.currentTimeMillis() - stime));
      
      res.next();
      String statusId = res.getString(1);
                          
               
     // try{
      //debug(insertSQL ) ;
   //   stime = System.currentTimeMillis();
      stat.addBatch(insertSQL);

    
    //  stat.execute(insertSQL);
      //debug("time contract insert = " + (System.currentTimeMillis() -stime));

  
      statusInsertSQL = insert_contract_Status_sql_constant+
              "("+userId+","+statusId+","+ this.contractId +","+this.status +","+ "sysdate"+")";
              
    //  stime= System.currentTimeMillis();
      //stat.execute(statusInsertSQL);

        stat.addBatch(statusInsertSQL);

      
      //debug("time to execute insert contract statsu = " +  (System.currentTimeMillis() -stime));
      
      if (warningId!=null)
      {
        warningSQL = contract_warning_sql_constant +statusId+", sysdate ,"+this.warningId+")";

    //    stat.execute(warningSQL);
    
         stat.addBatch(warningSQL);
      }
      

      if (optSQL !=null )
      {

          stat.addBatch(optSQL);
       //   stat.execute(optSQL);
 
      }

      
    }
    catch (Exception e)
    {      
      e.printStackTrace();         
    }     
  }
  
//commented cause no warning in the import phase for a contract 
//  public Hashtable warnings = new Hashtable() ;
  
  // 8- set time stamp of the contract 
  public void setTimeStamp(Date date)
  {
     this.timeStamp= date.getDate() +"\\"+(date.getMonth() +1) +"\\"+(date.getYear()+1900)+" " +date.getHours()+":"+date.getMinutes()+":"+date.getSeconds(); 
  }

/*
 * used to print any infomraiton for //debuging resons 
 * can be turned of by setting the //debug flag to false
 */
  private static void debug(String s)
  {
    if (debug)
    {
      Utility.logger.debug( s);
    }
  }
     
  /** 1-construcing a contract object
   * Constructor of the contract class take name which is the main sim number 
   * contract id 
   * and row number on the excel sheet 
   */
  public Contract(String name ,String contractId , int rowNum, String userId)
  {
    this.contractId = contractId  ;
    this.rowNum = rowNum;
    this.name = name; 
    this.userId = userId;
  }

   /*
    * 2-setting status
    */
  public void setContractStatus(int status)
  {    
    this.status = status;
    //debug ("contract id " + this.contractId + " was set to " + this.getStatusName() );
    
  }

// 6-get contract row number 
  public int getRowNum()
  {
    return this.rowNum;
  }

/*
  public void addWarning(String id , String value)
  {
    this.warnings.put (id,value);    
  }
*/

/*
 * * 5-get contract status name
 * get the status name of the contract 
 */ 
  public String getStatusName()
  {
    if ( this.status == REJECTED)
    {     
      return "Rejected";
    }    
    if (this.status == IMPORTED)
    {
      return "Imported";
    }                    
    return "unknown contract status";    
  }


  /*
   * 9-get the warning id on the contract 
   *  this is based on the assumption that there is only one warning 
   *  that can put in the import phase on a contract 
   *  if this assumption changes the part of handling the warning 
   *  id and the warning name in this code need to be adjusted 
   *  to be similar to the warning in the excelimport.java 
   *  that hold all the warning of current sheet 
   *  or to be similar to the sheet.java whihc has a hashtable to 
   *  hold its warning 
   */
  public String getWarningId()
  {
    
    return warningId;
  }

  /*
   * 10- set the warning id
   */
  public void setWarningId(String newWarningId)
  {
    warningId = newWarningId;
  }

  /*
   * 11- get the warning text
   */
  public String getWarningText()
  {
    return warningText;
  }

  /*
   * 12- set the warning text 
   */
  public void setWarningText(String newWarningText)
  {
    warningText = newWarningText;
  }
  
}

  
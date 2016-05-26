package com.mobinil.sds.core.system.cr.batch.dao;
import java.sql.*;

import com.mobinil.sds.core.utilities.*;

import oracle.jdbc.driver.OracleTypes;
import java.util.*;


import com.mobinil.sds.core.system.cr.batch.model.*;
import com.mobinil.sds.core.system.cr.batch.dto.*;
import com.mobinil.sds.core.system.cr.sheet.dao.*;
import com.mobinil.sds.core.system.cr.sheet.dto.*;
import com.mobinil.sds.core.system.cr.sheet.model.*;
import com.mobinil.sds.web.interfaces.cr.ContractRegistrationInterfaceKey;
import com.mobinil.sds.core.system.cr.contract.dao.*;
import com.mobinil.sds.core.system.cr.contract.dto.*;
import com.mobinil.sds.core.system.cr.contract.model.*;
import  com.mobinil.sds.core.system.cr.phase.model.*;


/*
 * BATCH DAO responsible of database operations related to the batch 
 * 1-add contract to displayed contracts in authintication 
 * this is to add a certin contract to the contracts infront of the user in the authintication screen 
 * it takes both the main sim no of the contract to be added and the batch id and also 
 * the already chosen cotnracts that are displayed
 * 2-add a sheet to displayed contracts in the authintication 
  * this method add all the contracts in a sheet to the displayed ocntracts in that authentication screen 
 * 3-generate a random sample of the contracts from each sheet in this batch and add them to the already
 * chosen contracts 
 * it generaete the random sample from the contracts that dont have a status in the authintication phase 
 * and that are not already dispalyed also 
 * if it found that it can't generaete the percentage since the number of contracts availbe to be added 
 * tot he sample will not satisfty the sample size
 * it adds all the contracts in this sheet 
 * 4- update a batch phase , make the phase of the batch desgnated by batchId equal to the phase its name
   * is sent in
 * 5-update batch status insertr a new status to the batch designated by batch id
 * 6- get batch status types alloed in contract verification 
 * this return a baststatustypedto object that containts that status valied
 * in the contract verification phase 
 * 7- get Batch Status Types Allowed in the authentication screen 
 * 8-  get batch status types allowed in the delivery verification  
 * 9- get batch status type by phase 
   * get all the status that are in this phase  
 * 10-get batch types in a batchTypeDto
 * 11- get batch information of the batch with the id sent to the method  
 * 12-get batch  it return a batch dto object of the batch with id equal to the id sent to it
 * and all its sheet that are in the phase phaseName 
 * 13- the search method 
   * it takes a dcm id which is a must and (date or/and batch type or/and batchstatus our/and phase)
   * and perform a serach operation and return a vector of all the batches that satisfy the search conditions
 * 14- create batch 
 * it takes a dcmid and a date and gather all the sheets of this dcmid that dont belong to a batch
 * and create a new batch and assign them all to it 
 * and update the batch status to relect wither it will be new (atleast one of the sheets assigned to it is not rejected)
 * or that it is rejected import that mean all its sheets are rejected sheets  
 * 
 * 
 * 
 */

public class BatchDao 
{
 /*
  * the constructor is private so no one can insatntiate an instince of this object 
  * since all its methods ares static and it is inteneded that it is used this way
  */
  private BatchDao()
  {
  
  }

  /*
   * get a batch authintication statistic 
   * this methods get all the statistic for all the sheets in the authintication phase for this batch
   * the statistic consist of number of accepted authintication, number of rejected authinitcation , the sample
   * size and the total number of contracts eligable for authintication 
   */
/*   
  public static HashMap getBatchSheetsAuthinticaionStatistic(String batchId, String [] sheets)
  {
   HashMap sheetsStatistic= new HashMap();
    try
    {
      Connection con = Utility.getConnection();    
      Statement stat = con.createStatement();
      String sql = "select batch_id,batch_auth_percentage from vw_CR_batch,vw_CR_batch_type"
                  +" where vw_CR_batch.BATCH_TYPE_ID = vw_CR_batch_type.BATCH_TYPE_ID "
                  +" and vw_CR_batch.batch_id = "+ batchId;

      ResultSet res = stat.executeQuery(sql);
      res.next();   
      int percentage = res.getInt("batch_auth_percentage");
      for (int i = 0 ; i<sheets.length;i++)
      {
        
      }
      
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return sheetsStatistic;
  }
  */


/*
 * 1-add contract to displayed contracts in authintication 
 * this is to add a certin contract to the contracts infront of the user in the authintication screen 
 * it takes both the main sim no of the contract to be added and the batch id and also 
 * the already chosen cotnracts that are displayed
 */
  public static HashMap addContractToDisplayedContractsInAuthintication(String batchId, String[] alreadyChosenContracts, String contractMainSimNo)
  
  {
   ContractModel contractModel = ContractDao.getContractByMainSimNoAndBatchId(batchId, contractMainSimNo);
   
    String sheetID = contractModel.getSheetId();
    String contractMainSimNum = contractModel.getMainSimNum();
    
   boolean contractSheetIsValid = false; 
   String sql = " select contract_id  from VW_CR_CONTRACT_STATUS_REIMPORT ,vw_gen_product "
       +" where  VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_PHASE_NAME = 'CONTRACT VERIFICATION' "                                      
       +" and VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id = " + sheetID
       +" and vw_gen_product.product_id = VW_CR_CONTRACT_STATUS_REIMPORT.contract_type_id "
       +" and vw_gen_product.product_category_name='PREPAID' "
       +" and contract_main_sim_no ='"+ contractMainSimNum +"'"
       +"and ( VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED VERIFY' "
      +" or VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED AUTHINTICATION'"
      +" or VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='REJECTED AUTHINTICATION')";

   contractSheetIsValid = DBUtil.executeSQLExistCheck(sql);
   
   if (contractModel==null || contractSheetIsValid== false)
   {
    
     return addSheetToDisplayedContractsInAuthintication(batchId,alreadyChosenContracts,null);
   }
   else
   {
    String sheetContractName = contractModel.getSheetId()+"_"+contractModel.getId();
    String [] newChosenContractsArray = new String[alreadyChosenContracts.length+1];
    System.arraycopy(alreadyChosenContracts,0,newChosenContractsArray,0,alreadyChosenContracts.length);
    newChosenContractsArray[alreadyChosenContracts.length]=sheetContractName;            
    
    return addSheetToDisplayedContractsInAuthintication(batchId,newChosenContractsArray,null);
   }   
  }



public static HashMap addContractToDisplayedContractsInAuthintication(String batchId, String[] alreadyChosenContracts, String contractMainSimNo ,String sheetId)
  {
  
	ContractModel contractModel = ContractDao.getContractByMainSimNoAndBatchId(batchId, contractMainSimNo);
	  
	  
	boolean contractSheetIsValid = false;
	
	if(contractModel==null || contractModel.getSheetId().compareTo(sheetId)!=0)
	{
	
	       return addSheetToDisplayedContractsInAuthintication(batchId,alreadyChosenContracts,null);
	}

    String sql = " select contract_id  from VW_CR_CONTRACT_STATUS_REIMPORT ,vw_gen_product "
        +" where  VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_PHASE_NAME = 'CONTRACT VERIFICATION' "                                      
        +" and VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id = " + sheetId
        +" and vw_gen_product.product_id = VW_CR_CONTRACT_STATUS_REIMPORT.contract_type_id "
        +" and vw_gen_product.product_category_name='PREPAID' "
        +" and contract_main_sim_no ='"+ contractMainSimNo +"'"
        +"and ( VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED VERIFY' "
       +" or VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED AUTHINTICATION'"
       +" or VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='REJECTED AUTHINTICATION')";

    contractSheetIsValid = DBUtil.executeSQLExistCheck(sql);
    
   if (contractModel==null || contractSheetIsValid== false)
   {
  
     return addSheetToDisplayedContractsInAuthintication(batchId,alreadyChosenContracts,null);
   }
   else
   {
    String sheetContractName = contractModel.getSheetId()+"_"+contractModel.getId();
    String [] newChosenContractsArray = new String[alreadyChosenContracts.length+1];
    System.arraycopy(alreadyChosenContracts,0,newChosenContractsArray,0,alreadyChosenContracts.length);
    newChosenContractsArray[alreadyChosenContracts.length]=sheetContractName;    
    return addSheetToDisplayedContractsInAuthintication(batchId,newChosenContractsArray,null);
   }   
  }




 /*
  * 2-add a sheet to displayed contracts in the authintication 
  * this method add all the contracts in a sheet to the displayed ocntracts in that authentication screen 
  */
  public static HashMap addSheetToDisplayedContractsInAuthintication(String batchId,  String[] alreadyChosenContracts, String sheetSerial)
  {
    String sheetId="";  

    if (sheetSerial!=null)
    {

      boolean isSheetSerialValidNumber = true; 
      try
      {
      Integer.parseInt(sheetSerial);
      }
      catch(Exception e)
      {
      isSheetSerialValidNumber = false; 
      sheetSerial = null;
      }
      

      if(isSheetSerialValidNumber)
      {
		  String sql = "select * from VW_CR_SHEET_LAST_STATUS_BATCH  where batch_id ="+batchId+" and sheet_serial_id='"+ sheetSerial
		  + "' and sheet_status_type_name in ('ACCEPTED AUTHINTICATION','REJECTED AUTHINTICATION','ACCEPTED VERIFY','ACCEPTED COMMISSION','REJECTED COMMISSION') " 
		  +" and (select count(*) from vw_CR_contract,vw_gen_product " 
		  +" where vw_CR_contract.sheet_id=  VW_CR_SHEET_LAST_STATUS_BATCH.SHEET_ID  "
		  +" and vw_gen_product.PRODUCT_ID =  vw_CR_contract.CONTRACT_TYPE_ID "
		  +" and vw_gen_product.PRODUCT_CATEGORY_NAME = 'PREPAID')>0 " ;

		  
		  sheetId = DBUtil.executeQuerySingleValueString(sql,"sheet_id" );
		  
              
        
      }

    }
        

    
    Hashtable sheetsInDisplay = new Hashtable();
    
    /*
     * collecting contracts in a vector for every sheet 
     * putting each sheet contracts in a vector then put the vector and the sheet serial in the hashtable of sheetInDispaly 
     */
    for (int i = 0 ; i <alreadyChosenContracts.length;i++)
    {
      String temp = alreadyChosenContracts[i];
      String sheet = temp.substring(0,temp.indexOf("_"));
      String contract = temp.substring(temp.indexOf("_")+1,temp.length());
      Vector contractsVector = null; 
      if (sheetsInDisplay.containsKey(sheet))
      {
        contractsVector = (Vector) sheetsInDisplay.get(sheet);
      }
      else
      {
        contractsVector = new Vector();
        sheetsInDisplay.put(sheet,contractsVector);
      }
      contractsVector.add(contract);                 
    }    
     Enumeration sheetsId =sheetsInDisplay.keys();
     Enumeration sheetsContractsVec = sheetsInDisplay.elements();
     HashMap sheetsContractsMap= new HashMap();   
      //getting all the contracts for this sheet and also handlign the case of sending null in the sheet serial 
     if (sheetSerial!=null)
     {     
       ContractDto selectedsheetContractDto = ContractDao.getSheetContractsEligableForAuthintication(sheetId);
       if (selectedsheetContractDto!=null)
      sheetsContractsMap.put(sheetId, selectedsheetContractDto);
     }
     /*
      * getingt contract dto for each contract in eacch vector that belong to a sheet  and putting the sheet id 
      * and the contract dto in the hashtable sheetsContractsMap then return it 
      */
     while (sheetsId.hasMoreElements())
     {
       String curSheetId = (String) sheetsId.nextElement();
       Vector sheetContractVector = (Vector) sheetsContractsVec.nextElement();
       ContractDto contractDto = null;
       if (curSheetId.compareTo(sheetId)!=0)
       {      
          long startTime = System.currentTimeMillis();
          ////Utility.logger.debug(" number of contracts in sheet "+  sheetContractVector.size());
         contractDto = ContractDao.getContractsDto(sheetContractVector,curSheetId);
         ////Utility.logger.debug(" getting contract dto time = "+(System.currentTimeMillis() - startTime)); 
         
       }
      if (contractDto!=null)
      {
      sheetsContractsMap.put(curSheetId, contractDto);
      }      
     }         
    return sheetsContractsMap;
  }

/*
 * 3-generate a random sample of the contracts from each sheet in this batch and add them to the already
 * chosen contracts 
 * it generaete the random sample from the contracts that dont have a status in the authintication phase 
 * and that are not already dispalyed also 
 * if it found that it can't generaete the percentage since the number of contracts availbe to be added 
 * tot he sample will not satisfty the sample size
 * it adds all the contracts in this sheet 
 * 
 */
  public static HashMap generateBatchRandomSample(String batchId , String[] alreadyChosenContracts)
  {
  
    try
    {
      Connection con = Utility.getConnection();    
      Statement stat = con.createStatement();
      HashMap sheetsContractsMap = new HashMap();
      
    String sql = " select vw_CR_sheet.SHEET_ID, COUNT(*)contracts_count from vw_CR_batch ,vw_gen_product, vw_CR_phase batch_phase,vw_CR_sheet,vw_CR_contract_Status_reimport"
    +" where vw_CR_batch.phase_id = batch_phase.phase_id"
    +" and vw_gen_product.product_id= VW_CR_CONTRACT_STATUS_REIMPORT.contract_type_id "
    +" and vw_gen_product.product_category_name='PREPAID' "
    +" and vw_CR_sheet.batch_id = vw_Cr_batch.batch_id"
    +" and vw_CR_contract_Status_reimport.SHEET_ID = vw_CR_sheet.SHEET_ID"
    +" and batch_phase.PHASE_NAME =  'AUTHINTICATION CALL'"
    +" and ( vw_CR_contract_Status_reimport.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED VERIFY' "
	  +" or vw_CR_contract_Status_reimport.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED AUTHINTICATION'"
	  +" or vw_CR_contract_Status_reimport.CONTRACT_STATUS_TYPE_NAME ='REJECTED AUTHINTICATION')"
    +" and vw_CR_batch.batch_id = " + batchId
    +" GROUP BY vw_CR_sheet.SHEET_ID ";
    
    //Utility.logger.debug(sql);

    long startT = System.currentTimeMillis();
    
    ResultSet res = stat.executeQuery(sql);

    //Utility.logger.debug("time to exec = " + (System.currentTimeMillis() - startT));
    
    Hashtable sheetsTotalRecordsCount = new Hashtable();
    while (res.next())
    {
      String sheetId = res.getString("sheet_id");
      Integer sheetCount = new Integer(res.getString("contracts_count"));      
      sheetsTotalRecordsCount.put(sheetId, sheetCount);          
    }

	  sql = "select batch_id,batch_auth_percentage from vw_CR_batch,vw_CR_batch_type"
          +" where vw_CR_batch.BATCH_TYPE_ID = vw_CR_batch_type.BATCH_TYPE_ID "
          +" and vw_CR_batch.batch_id = "+ batchId;

  //Utility.logger.debug(sql);
  
    startT = System.currentTimeMillis();
   res = stat.executeQuery(sql);
   //Utility.logger.debug("time to exdec 4 =  "+ (System.currentTimeMillis() - startT));

   res.next();

  // //Utility.logger.debug(sql ); 
   
   int percentage = res.getInt("batch_auth_percentage");
    //i dont need the connection any more after this
   Utility.closeConnection(con);

   Hashtable sheetsInDisplay = new Hashtable();
   //putting all the contracts of each sheet in a vector for each sheet
   //and add them to the hashtable 
    for (int i = 0 ; i <alreadyChosenContracts.length;i++)
    {
      String temp = alreadyChosenContracts[i];
      String sheet = temp.substring(0,temp.indexOf("_"));
      String contract = temp.substring(temp.indexOf("_")+1,temp.length());
      Vector contractsVector = null; 
      if (sheetsInDisplay.containsKey(sheet))
      {
        contractsVector = (Vector) sheetsInDisplay.get(sheet);
      }
      else
      {
        contractsVector = new Vector();
        sheetsInDisplay.put(sheet,contractsVector);
      }
      contractsVector.add(contract);         
    }
    Enumeration sheetsTotalRecordsCountKeys = sheetsTotalRecordsCount.keys();
    while(sheetsTotalRecordsCountKeys.hasMoreElements())
    {
      String sheetId = (String)sheetsTotalRecordsCountKeys.nextElement();
      if (!sheetsInDisplay.containsKey(sheetId))
      {
        sheetsInDisplay.put(sheetId,new Vector());
      }
    }  
    Enumeration sheetsInDisplaySheetIds = sheetsInDisplay.keys();
    Enumeration sheetsInDisplayContracts = sheetsInDisplay.elements();
    while (sheetsInDisplaySheetIds.hasMoreElements())
    {
      String sheetId = (String)sheetsInDisplaySheetIds.nextElement();
      Vector contractsVec = (Vector) sheetsInDisplayContracts.nextElement();
      String contractsArray[] = new String[contractsVec.size()];
      for (int index = 0 ; index<contractsVec.size();index++)
      {
       contractsArray[index] = (String) contractsVec.elementAt(index);
      }    
      Integer sheetTotal = (Integer)sheetsTotalRecordsCount.get(sheetId);
      
      //get the random sample of contracts in this sheet and add it to the current contracts displayed
      ////Utility.logger.debug(sheetId +"   " + sheetTotal.intValue() +"   " + percentage + "   " +contractsArray);
      startT =System.currentTimeMillis();      
      ContractDto contractDto = ContractDao.getBatchSheetContractRandomSample(sheetId,sheetTotal.intValue(),percentage , contractsArray);
      //Utility.logger.debug("time to get getBatchSheetContractRandomSample = " +(System.currentTimeMillis()-startT));
      
      if (contractDto!=null)
      {
        sheetsContractsMap.put(sheetId, contractDto);
      }     
      
      
    } 
  
    return sheetsContractsMap;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
    return null;
  }



public static HashMap generateBatchRandomSample(String batchId , String[] alreadyChosenContracts,String sheetId)
  {
    try
    {
      Connection con = Utility.getConnection();    
      Statement stat = con.createStatement();
      HashMap sheetsContractsMap = new HashMap();
      
    String sql = " select vw_CR_sheet.SHEET_ID, COUNT(*)contracts_count from vw_CR_batch ,vw_gen_product, vw_CR_phase batch_phase,vw_CR_sheet,vw_CR_contract_Status_reimport"
    +" where vw_CR_batch.phase_id = batch_phase.phase_id"
    +" and vw_CR_sheet.sheet_id="+sheetId 
    +" and vw_gen_product.product_id= VW_CR_CONTRACT_STATUS_REIMPORT.contract_type_id "
    +" and vw_gen_product.product_category_name='PREPAID' "
    +" and vw_CR_sheet.batch_id = vw_Cr_batch.batch_id"
    +" and vw_CR_contract_Status_reimport.SHEET_ID = vw_CR_sheet.SHEET_ID"
    +" and batch_phase.PHASE_NAME =  'AUTHINTICATION CALL'"
    +" and ( vw_CR_contract_Status_reimport.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED VERIFY' "
	  +" or vw_CR_contract_Status_reimport.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED AUTHINTICATION'"
	  +" or vw_CR_contract_Status_reimport.CONTRACT_STATUS_TYPE_NAME ='REJECTED AUTHINTICATION')"
    +" and vw_CR_batch.batch_id = " + batchId
    +" GROUP BY vw_CR_sheet.SHEET_ID ";
    
    //Utility.logger.debug(sql);

  
    
    ResultSet res = stat.executeQuery(sql);

    //Utility.logger.debug("time to exec = " + (System.currentTimeMillis() - startT));
    
    Hashtable sheetsTotalRecordsCount = new Hashtable();
    String tempSheetId = null;
    while (res.next())
    {
    	tempSheetId = res.getString("sheet_id");
      Integer sheetCount = Integer.valueOf(res.getString("contracts_count"));      
      sheetsTotalRecordsCount.put(tempSheetId, sheetCount);          
    }

	  sql = "select batch_id,batch_auth_percentage from vw_CR_batch,vw_CR_batch_type"
          +" where vw_CR_batch.BATCH_TYPE_ID = vw_CR_batch_type.BATCH_TYPE_ID "
          +" and vw_CR_batch.batch_id = "+ batchId;

  //Utility.logger.debug(sql);
  
 
   res = stat.executeQuery(sql);
   //Utility.logger.debug("time to exdec 4 =  "+ (System.currentTimeMillis() - startT));

   res.next();

  // //Utility.logger.debug(sql ); 
   
   int percentage = res.getInt("batch_auth_percentage");
    //i dont need the connection any more after this
   Utility.closeConnection(con);

   Hashtable sheetsInDisplay = new Hashtable();
   //putting all the contracts of each sheet in a vector for each sheet
   //and add them to the hashtable 
    for (int i = 0 ; i <alreadyChosenContracts.length;i++)
    {
      String temp = alreadyChosenContracts[i];
      String sheet = temp.substring(0,temp.indexOf("_"));
      String contract = temp.substring(temp.indexOf("_")+1,temp.length());
      Vector contractsVector = null; 
      if (sheetsInDisplay.containsKey(sheet))
      {
        contractsVector = (Vector) sheetsInDisplay.get(sheet);
      }
      else
      {
        contractsVector = new Vector();
        sheetsInDisplay.put(sheet,contractsVector);
      }
      contractsVector.add(contract);         
    }
    Enumeration sheetsTotalRecordsCountKeys = sheetsTotalRecordsCount.keys();
    while(sheetsTotalRecordsCountKeys.hasMoreElements())
    {
       sheetId = (String)sheetsTotalRecordsCountKeys.nextElement();
      if (!sheetsInDisplay.containsKey(sheetId))
      {
        sheetsInDisplay.put(sheetId,new Vector());
      }
    }  
    Enumeration sheetsInDisplaySheetIds = sheetsInDisplay.keys();
    Enumeration sheetsInDisplayContracts = sheetsInDisplay.elements();
    while (sheetsInDisplaySheetIds.hasMoreElements())
    {
       sheetId = (String)sheetsInDisplaySheetIds.nextElement();
      Vector contractsVec = (Vector) sheetsInDisplayContracts.nextElement();
      String contractsArray[] = new String[contractsVec.size()];
      for (int index = 0 ; index<contractsVec.size();index++)
      {
       contractsArray[index] = (String) contractsVec.elementAt(index);
      }    
      Integer sheetTotal = (Integer)sheetsTotalRecordsCount.get(sheetId);
      
      //get the random sample of contracts in this sheet and add it to the current contracts displayed
      ////Utility.logger.debug(sheetId +"   " + sheetTotal.intValue() +"   " + percentage + "   " +contractsArray);
           
      ContractDto contractDto = ContractDao.getBatchSheetContractRandomSample(sheetId,sheetTotal.intValue(),percentage , contractsArray);
      //Utility.logger.debug("time to get getBatchSheetContractRandomSample = " +(System.currentTimeMillis()-startT));
      
      if (contractDto!=null)
      {
        sheetsContractsMap.put(sheetId, contractDto);
      }      
    }                      
    return sheetsContractsMap;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
    return null;
  
  }


public static void updateBatchLocalAuthPercentage(String id , int localAuth)
{
	  String updatePhase ="update cr_batch set batch_local_auth_percentage="+localAuth +" where batch_Id="+id;
	  DBUtil.executeSQL(updatePhase);
}


  
  /*
   * 4- update a batch phase , make the phase of the batch desgnated by batchId equal to the phase its name
   * is sent in
   * 
   * hagry comment this should be changed to getting the types from hashtable instead of going to the database every time 
   * 
   */


  public static void updateBatchPhase(String batchId, String phaseName)
  {
  ////Utility.logger.debug(" updateBatchPhase" ); 
    try
    {
      Connection con = Utility.getConnection();    
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery("select phase_id from vw_CR_phase where phase_name ='"+phaseName+"'");
      if (res.next())
      {
          String phaseId = res.getString("phase_id");
          String updatePhase ="update vw_CR_batch set phase_id="+phaseId +" where batch_Id="+batchId;
          stat.execute(updatePhase);
      }
      res.close();
      stat.close();
      Utility.closeConnection(con);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  
  public static void updateBatchArchivingFlag(String batchId, String archivingFlagValue)
  {
  ////Utility.logger.debug(" updateBatchPhase" );
	  String updatePhase ="update CR_batch set archiving_flag = "+archivingFlagValue+" where batch_Id="+batchId;
	  DBUtil.executeSQL(updatePhase);
	  
  }


public static void updateBatchStatusAuthenticationBatchPercentage(String batchId) {
    
    Hashtable batchAuthStat = BatchDao.getBatchAuthStatistics(batchId);
    
    Integer accVer= (Integer) batchAuthStat.get("ACCEPTED VERIFY");
       if (accVer ==null) accVer = new Integer(0);
       
       Integer accAuth= (Integer) batchAuthStat.get("ACCEPTED AUTHINTICATION");
       if (accAuth ==null) accAuth = new Integer(0);
       
       Integer rejAuth= (Integer) batchAuthStat.get("REJECTED AUTHINTICATION");
       if (rejAuth == null) rejAuth = new Integer(0);
       
       Integer percentage = (Integer)batchAuthStat.get("percentage");
       
       int total = accVer.intValue() + accAuth.intValue()+rejAuth.intValue();
       int neededAuth = Math.round(total * percentage.intValue()/100);
       
       if ((accAuth.intValue() + rejAuth.intValue()  >= neededAuth) &&(accAuth.intValue()>0)) {
           
           try {
           Connection con = Utility.getConnection();
           Statement stat = con.createStatement();
               String insertNewStatus = "insert into vw_cr_batch_status(batch_status_id,batch_id,batch_status_type_id,batch_status_date) "
                                 +" values(seq_cr_batch_status.nextval,"+batchId+",11,sysdate)";
               stat.executeUpdate(insertNewStatus);
            stat.close();
            Utility.closeConnection(con);
           }
           catch(Exception e) {
               e.printStackTrace();
           }
       }
       
}

public static void  updateBatchStatusInAuth(String batchId)

{	  	 
	Hashtable batchAuthStat=  BatchDao.getBatchAuthStatistics(batchId);	
	Integer accVer= (Integer) batchAuthStat.get("ACCEPTED VERIFY");
	    if (accVer ==null) accVer = new Integer(0);
	    
	    Integer accAuth= (Integer) batchAuthStat.get("ACCEPTED AUTHINTICATION");
	    if (accAuth ==null) accAuth = new Integer(0);
	    
	    Integer rejAuth= (Integer) batchAuthStat.get("REJECTED AUTHINTICATION");
	    if (rejAuth == null) rejAuth = new Integer(0);
	    
	    Integer pending = (Integer) batchAuthStat.get("PENDING");
	    if (pending == null) pending = new Integer(0);
	    
	    
	    Integer percentage = (Integer)batchAuthStat.get("percentage");
	    
	    int total = accVer.intValue() + accAuth.intValue()+rejAuth.intValue()+pending.intValue();
	    int neededAuth = Math.round(total * percentage.intValue()/100);
	     int remaining = neededAuth-accAuth.intValue()-rejAuth.intValue();
	 
String phaseName=null; 
 

  try
  {
    Connection con = Utility.getConnection();

    Statement stat = con.createStatement();
/*
 * the code in the stored procedure was commented cause it return wrong result 
 * this code can be removed to the stored procedure that is named BATCH_SHEET_LAST_STATUS_COUNT
 */

    String sql = "select batch_id,phase_name from vw_CR_batch batch, vw_CR_phase phase where " 
        +" batch.phase_id = phase.phase_id and batch.batch_id = " + batchId;
    java.sql.ResultSet res = stat.executeQuery(sql); 
    
    res.next();      
    phaseName = res.getString("phase_name");

     //Utility.logger.debug("phaseName " + phaseName) ; 

     

        
        
    String statusSQL="";
    String updatedPhaseName ="";

    //deciding which status to be given to the batch 
 
    if (remaining <=0)
  //  else if ((acceptedCommissionCount_elg_auth>0)&&(  rejectedAuthinticationCount+ acceptedCommissionCount ==totalEligableForAuthentication ))
    
    {
      //open commission status , authentication call phase 
      statusSQL = "select * from vw_cr_batch_status_type where batch_status_type_name=\'"+BatchModel.STATUS_OPEN_COMMISSION+"\'";
      updatedPhaseName = ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE;
    }

     
      
        

  //Utility.logger.debug("statusSQL = "+ statusSQL);
  
      if (statusSQL.compareTo("")!=0)
      {
          
      ResultSet resStatus = stat.executeQuery(statusSQL);
      resStatus.next();            
      int newStatusId = resStatus.getInt("batch_status_type_id");

      String sqlOldStatus= "select * from vw_cr_batch_last_status where batch_id ="+batchId;
      //Utility.logger.debug("sqlOldStatus = "+ sqlOldStatus);
      resStatus = stat.executeQuery(sqlOldStatus);

      boolean insertNewStatusFlag = true;             
      if (resStatus.next())
      {
        int status = resStatus.getInt("batch_Status_type_id");
        if (status==newStatusId)
        {
          //should not insert since it is the same old status
          insertNewStatusFlag = false; 
        }
      }
      if (insertNewStatusFlag)
      {
        String insertNewStatus = "insert into vw_cr_batch_status(batch_status_id,batch_id,batch_status_type_id,batch_status_date) "
                          +" values(seq_cr_batch_status.nextval,"+batchId+","+newStatusId+",sysdate)";
        stat.executeUpdate(insertNewStatus);
        res = stat.executeQuery("select * from vw_CR_phase where phase_name ='"+updatedPhaseName+"'");
        if (res.next())
        {
          String phaseId = res.getString("phase_id");
          String updatePhase ="update vw_CR_batch set phase_id="+phaseId +" where batch_Id="+batchId;
          stat.executeUpdate(updatePhase);
        }
      }
    }  
      res.close();
      stat.close();
    
    Utility.closeConnection(con);
  }
  catch (Exception e)
  {
    e.printStackTrace();
  }
}


/*
 * 5-update batch status insertr a new status to the batch designated by batch id
 * the phasename is not important any more since the fjunction is dynamic it manage 
 * to know which status and which phase you sshould be upon the calculation it does 
 */
  public static void  updateBatchStatus(String batchId,Connection con)
  
  {
	  

   String phaseName=null;
   
  
    try
    {
    //  Connection con = Utility.getConnection();
 
      Statement stat = con.createStatement();
  /*
   * the code in the stored procedure was commented cause it return wrong result 
   * this code can be removed to the stored procedure that is named BATCH_SHEET_LAST_STATUS_COUNT
   */

      
      
      String sql = "select sheet_status_type_name,count(cR_sheet.sheet_id)sheet_status_type_count  from cr_sheet_status_type,cr_sheet,cr_sheet_status where batch_id = "+batchId + "and cr_sheet.SHEET_last_status_id = cr_sheet_status.SHEET_STATUS_ID and cr_sheet_status.SHEET_STATUS_TYPE_ID = cr_sheet_status_type.SHEET_STATUS_TYPE_ID"
      
                   +" group by sheet_status_type_name";
                   
      //Utility.logger.debug(sql);
      ResultSet res= stat.executeQuery(sql);

      
      int total = 0 ;
      Hashtable statusTable = new Hashtable();
      while (res.next())
      {
        String statusName = res.getString("sheet_status_type_name");
        Integer statusCount = new Integer (res.getString("sheet_status_type_count"));
        statusTable.put(statusName, statusCount);        
        total+= statusCount.intValue();
      }
      
      
      
    //calculating the number of sheets in each status 
        
      Integer rejectedImportCountInt = (Integer)statusTable.get(SheetModel.STATUS_REJECTED_IMPORT);
      int rejectedImportCount =(rejectedImportCountInt==null ? 0:rejectedImportCountInt.intValue()); 
      Integer newStatusCountInt = (Integer)statusTable.get(SheetModel.STATUS_NEW);          
      int newStatusCount = (newStatusCountInt==null ? 0:newStatusCountInt.intValue());
      Integer acceptedDeliveryCountInt = (Integer)statusTable.get(SheetModel.STATUS_ACCEPTED_DELIVERY);
      int acceptedDeliveryCount = (acceptedDeliveryCountInt==null ? 0:acceptedDeliveryCountInt.intValue());
      Integer rejectedDeliveryCountInt = (Integer)statusTable.get(SheetModel.STATUS_REJECTED_DELIVERY);
      int rejectedDeliveryCount = (rejectedDeliveryCountInt==null ? 0:rejectedDeliveryCountInt.intValue());
      Integer acceptedVerifyCountInt = (Integer)statusTable.get(SheetModel.STATUS_ACCEPTED_VERIFY);
      int acceptedVerifyCount =  (acceptedVerifyCountInt==null ? 0:acceptedVerifyCountInt.intValue());
      Integer rejectedVerifyCountInt = (Integer)statusTable.get(SheetModel.STATUS_REJECTED_VERIFY);
      int rejectedVerifyCount =  (rejectedVerifyCountInt==null ? 0:rejectedVerifyCountInt.intValue()); 
      Integer acceptedAuthinticationCountInt = (Integer) statusTable.get(SheetModel.STATUS_ACCEPTED_AUTHINTICATION);
      int acceptedAuthinticationCount =  (acceptedAuthinticationCountInt==null ? 0:acceptedAuthinticationCountInt.intValue());
      Integer rejectedAuthinticationCountInt=  (Integer) statusTable.get(SheetModel.STATUS_REJECTED_AUTHINTICATION);
      int rejectedAuthinticationCount =  (rejectedAuthinticationCountInt==null ? 0:rejectedAuthinticationCountInt.intValue());
        
      Integer acceptedCommissionCountInt = (Integer) statusTable.get(SheetModel.STATUS_ACCEPTED_COMMISSION);
      int acceptedCommissionCount =  (acceptedCommissionCountInt==null ? 0:acceptedCommissionCountInt.intValue());
      Integer rejectedCommissionnCountInt=  (Integer) statusTable.get(SheetModel.STATUS_REJECTED_COMMISSION);
      int rejectedCommissionnCount =  (rejectedCommissionnCountInt==null ? 0:rejectedCommissionnCountInt.intValue());


        //debugin information about each type and its count 

/*
        Utility.logger.debug("rejectedImport = "+ rejectedImportCount);
        Utility.logger.debug("imported = "+ newStatusCount);
        Utility.logger.debug("accepted delivery " + acceptedDeliveryCount);
        Utility.logger.debug("rejected delivery " + rejectedDeliveryCount);
        Utility.logger.debug("accepted verify " + acceptedVerifyCount);
        Utility.logger.debug("rejected verify " + rejectedVerifyCount);
        Utility.logger.debug("accepted AuthinticationCount " + acceptedAuthinticationCount);
        Utility.logger.debug("rejected AuthinticationCount " + rejectedAuthinticationCount); 
        Utility.logger.debug("acceptedCommissionCountInt " + acceptedCommissionCount) ; 
        Utility.logger.debug("rejectedCommissionnCount " + rejectedCommissionnCount);
        Utility.logger.debug("total " + total);


*/


      sql = "select batch_id,phase_name from vw_CR_batch batch, vw_CR_phase phase where " 
          +" batch.phase_id = phase.phase_id and batch.batch_id = " + batchId;
      res = stat.executeQuery(sql); 
      res.next();      
      phaseName = res.getString("phase_name");

       //Utility.logger.debug("phaseName " + phaseName) ; 

       

      int acceptedVerifyCount_elg_auth = 0;
      int acceptedAuthinticationCount_elg_auth =0;
      int acceptedCommissionCount_elg_auth =0;
      int rejectedAuthinticationCount_elg_auth  = 0;
      int rejectedCommissionnCount_elg_auth = 0;

      int total_elg_auth = 0;
      
      if (phaseName.compareTo(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE)==0)
      {
        sql ="SELECT  sheet_status_type_name, count(cr_sheet.sheet_id)sheet_status_type_count FROM "
            +" CR_SHEET,CR_SHEET_STATUS,CR_SHEET_STATUS_TYPE WHERE "
            +" batch_id = "+batchId
            +" and cr_sheet.SHEET_last_status_id = cr_sheet_status.SHEET_STATUS_ID and cr_sheet_status.SHEET_STATUS_TYPE_ID = cr_sheet_status_type.SHEET_STATUS_TYPE_ID"
            + " and sheet_status_type_name in ('ACCEPTED AUTHINTICATION','REJECTED AUTHINTICATION','ACCEPTED VERIFY','ACCEPTED COMMISSION','REJECTED COMMISSION') " 
            +" and (select count(*) from vw_CR_contract,vw_gen_product " 
  	        +" where vw_CR_contract.sheet_id=  cr_sheet.SHEET_ID  "
  	  		  +" and vw_gen_product.PRODUCT_ID =  vw_CR_contract.CONTRACT_TYPE_ID "
 			      +" and vw_gen_product.PRODUCT_CATEGORY_NAME = 'PREPAID')>0 group by  sheet_status_type_name" ;

      //Utility.logger.debug(sql);
      
//      System.out.println(sql);
        res = stat.executeQuery(sql);
       

      statusTable = new Hashtable();
      while (res.next())
      {
        String statusName = res.getString("sheet_status_type_name");
        Integer statusCount = new Integer (res.getString("sheet_status_type_count"));
        statusTable.put(statusName, statusCount);        
      total_elg_auth+=statusCount.intValue();
      //Utility.logger.debug("statusTable " + statusName + "    " +statusCount);
      }
      

       acceptedVerifyCountInt = (Integer)statusTable.get(SheetModel.STATUS_ACCEPTED_VERIFY);
      acceptedVerifyCount_elg_auth =  (acceptedVerifyCountInt==null ? 0:acceptedVerifyCountInt.intValue());
       acceptedAuthinticationCountInt = (Integer) statusTable.get(SheetModel.STATUS_ACCEPTED_AUTHINTICATION);
      acceptedAuthinticationCount_elg_auth =  (acceptedAuthinticationCountInt==null ? 0:acceptedAuthinticationCountInt.intValue());
       rejectedAuthinticationCountInt=  (Integer) statusTable.get(SheetModel.STATUS_REJECTED_AUTHINTICATION);
      rejectedAuthinticationCount_elg_auth =  (rejectedAuthinticationCountInt==null ? 0:rejectedAuthinticationCountInt.intValue());
        
       acceptedCommissionCountInt = (Integer) statusTable.get(SheetModel.STATUS_ACCEPTED_COMMISSION);
      acceptedCommissionCount_elg_auth =  (acceptedCommissionCountInt==null ? 0:acceptedCommissionCountInt.intValue());
       rejectedCommissionnCountInt=  (Integer) statusTable.get(SheetModel.STATUS_REJECTED_COMMISSION);
      rejectedCommissionnCount_elg_auth =  (rejectedCommissionnCountInt==null ? 0:rejectedCommissionnCountInt.intValue());

 //Utility.logger.debug("this batch is in authentication");
        
        
        /*
        Utility.logger.debug("accepted verify elg " + acceptedVerifyCount_elg_auth);
        
        Utility.logger.debug("accepted AuthinticationCount elg  " + acceptedAuthinticationCount_elg_auth);
        Utility.logger.debug("rejected AuthinticationCount elg  " + rejectedAuthinticationCount_elg_auth); 
        Utility.logger.debug("acceptedCommissionCountInt elg " + acceptedCommissionCount_elg_auth) ; 
        Utility.logger.debug("rejectedCommissionnCount elg " + rejectedCommissionnCount_elg_auth);
        Utility.logger.debug("total _elg_auth" + total_elg_auth);
    */


      }
          
      String statusSQL="";
      String updatedPhaseName ="";

      //deciding which status to be given to the batch 
      if ((newStatusCount == 0 ) &&(rejectedImportCount==total))
      {
         //closed import , import phase 
         statusSQL = "select * from vw_cr_batch_status_type where batch_status_type_name=\'"+BatchModel.STATUS_CLOSED_IMPORT+"\'";
         updatedPhaseName = ContractRegistrationInterfaceKey.IMPORT_PHASE;
      }
      else if ((newStatusCount == 0 ) &&(rejectedDeliveryCount+rejectedImportCount==total))
      {
        //rejected delivery status delivery verification phase
         statusSQL = "select * from vw_cr_batch_status_type where batch_status_type_name=\'"+BatchModel.STATUS_CLOSED_DELIVERY+"\'";
         updatedPhaseName = ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE;                           
      }
     
      else if ((newStatusCount==0) &&(rejectedVerifyCount+rejectedImportCount+rejectedDeliveryCount==total))
      {
        statusSQL = "select * from vw_cr_batch_status_type where batch_status_type_name=\'"+BatchModel.STATUS_CLOSED_VERIFY+"\'";
        updatedPhaseName = ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE;
      }
   
      else if ( (acceptedVerifyCount_elg_auth==0)&& (acceptedAuthinticationCount_elg_auth ==0) &&(acceptedCommissionCount_elg_auth>0))
    //  else if ((acceptedCommissionCount_elg_auth>0)&&(  rejectedAuthinticationCount+ acceptedCommissionCount ==totalEligableForAuthentication ))
      
      {
        //open commission status , authentication call phase 
        statusSQL = "select * from vw_cr_batch_status_type where batch_status_type_name=\'"+BatchModel.STATUS_OPEN_COMMISSION+"\'";
        updatedPhaseName = ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE;
      }

      //else if ((newStatusCount==0) &&(acceptedVerifyCount==0)&& (acceptedDeliveryCount==0) && (acceptedAuthinticationCount >0))
      else if (( (acceptedAuthinticationCount_elg_auth >0)) &&(phaseName.compareTo(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE)==0))
      {
        // open authentication , authentication call phase          
        statusSQL = "select * from vw_cr_batch_status_type where batch_status_type_name=\'"+BatchModel.STATUS_OPEN_AUTHINTICATION+"\'";
        updatedPhaseName = ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE;
      }
         else if ((newStatusCount==0) &&(acceptedVerifyCount>0)&& (acceptedDeliveryCount==0) && (rejectedAuthinticationCount==0) &&
     ((phaseName.compareTo(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE)==0) || 
      (phaseName.compareTo(ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE)==0))
      )
      {
        // open authentication , contract verification phase 
        statusSQL = "select * from vw_cr_batch_status_type where batch_status_type_name=\'"+BatchModel.STATUS_OPEN_AUTHINTICATION+"\'";
        updatedPhaseName = ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE;
      }

      else if ( (acceptedAuthinticationCount_elg_auth + acceptedVerifyCount_elg_auth + acceptedCommissionCount_elg_auth ==0 )&&(rejectedAuthinticationCount_elg_auth>0)&&(phaseName.compareTo(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE)==0))
      {
        //rejected AUTHINTICATION status , authentication call phase
        
         statusSQL = "select * from vw_cr_batch_status_type where batch_status_type_name=\'"+BatchModel.STATUS_CLOSED_AUTHINTICATION+"\'";
         updatedPhaseName = ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE;                           
      }  
      else if (
      (newStatusCount == 0)&&(acceptedDeliveryCount>0)&& 
      ((phaseName.compareTo(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE)==0) || 
      (phaseName.compareTo(ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE)==0)||      
      (phaseName.compareTo(ContractRegistrationInterfaceKey.IMPORT_PHASE)==0)
      )
      )
      {
        //accepted delivery status contract verification phase  
        statusSQL = "select * from vw_cr_batch_status_type where batch_status_type_name=\'"+BatchModel.STATUS_OPEN_VERIFY+"\'";
        updatedPhaseName = ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE;
        ////Utility.logger.debug("back to verify"); 
      }
          

    //Utility.logger.debug("statusSQL = "+ statusSQL);
    
        if (statusSQL.compareTo("")!=0)
        {
            
        ResultSet resStatus = stat.executeQuery(statusSQL);
        resStatus.next();            
        int newStatusId = resStatus.getInt("batch_status_type_id");

        String sqlOldStatus= "select * from vw_cr_batch_last_status where batch_id ="+batchId;
        //Utility.logger.debug("sqlOldStatus = "+ sqlOldStatus);
        resStatus = stat.executeQuery(sqlOldStatus);

        boolean insertNewStatusFlag = true;             
        if (resStatus.next())
        {
          int status = resStatus.getInt("batch_Status_type_id");
          if (status==newStatusId)
          {
            //should not insert since it is the same old status
            insertNewStatusFlag = false; 
          }
        }
        if (insertNewStatusFlag)
        {
          String insertNewStatus = "insert into vw_cr_batch_status(batch_status_id,batch_id,batch_status_type_id,batch_status_date) "
                            +" values(seq_cr_batch_status.nextval,"+batchId+","+newStatusId+",sysdate)";
          stat.executeUpdate(insertNewStatus);
          res = stat.executeQuery("select * from vw_CR_phase where phase_name ='"+updatedPhaseName+"'");
          if (res.next())
          {
            String phaseId = res.getString("phase_id");
            String updatePhase ="update vw_CR_batch set phase_id="+phaseId +" where batch_Id="+batchId;
            stat.executeUpdate(updatePhase);
          }
        }
      }                                                                    
  //    Utility.closeConnection(con);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

/*
 * 6- get batch status types alloed in contract verification 
 * this return a baststatustypedto object that containts that status valied
 * in the contract verification phase  
 */
  public static  BatchStatusTypeDto getBatchStatusTypeAllowedInContractVerification(Connection con)
  {

    
    Object obj =   CachEngine.getObject(ContractRegistrationInterfaceKey.CACH_OBJ_BATCH_STATUS_TYPE_CONTRACT_VERIFICATION);
    //Utility.logger.debug("objected created  : getUserContractWarningByPhase"+phaseName);
    BatchStatusTypeDto batchStatusTypeDto =null;

    if (obj ==null)
    {  
       try
         {
        
          Statement stat = con.createStatement();
          String sql = "SELECT * FROM VW_CR_BATCH_STATUS_TYPE STATUS_TYPE, VW_CR_PHASE PHASE" 
                      +" WHERE STATUS_TYPE.PHASE_ID = PHASE.PHASE_ID"
                      +" AND (PHASE_NAME = 'CONTRACT VERIFICATION' OR BATCH_STATUS_TYPE_NAME='"+BatchModel.STATUS_OPEN_AUTHINTICATION+"')"                  
                      +" order by batch_status_type_name";
   
          ResultSet res = stat.executeQuery(sql );
          batchStatusTypeDto = new BatchStatusTypeDto();
          while (res.next())
          {
            String batchStatusTypeName = res.getString("batch_Status_type_name");
            int batchStatusTypeId = res.getInt("batch_Status_type_id");
            batchStatusTypeDto.addBatchStatusTypeModel(new BatchStatusTypeModel(batchStatusTypeId,batchStatusTypeName));                                
          }
          
          CachEngine.storeObject(ContractRegistrationInterfaceKey.CACH_OBJ_BATCH_STATUS_TYPE_CONTRACT_VERIFICATION , batchStatusTypeDto);      
        }
        catch(Exception e)
        {  
        e.printStackTrace();
        }
    }
    else 
    {
        batchStatusTypeDto = (BatchStatusTypeDto) obj;
    }

    return batchStatusTypeDto;
  }

/*
 * 7- get Batch Status Types Allowed in the authentication screen  
 */
  public static  BatchStatusTypeDto getBatchStatusTypeAllowedInAuthintication()
  {   
    long startTime = System.currentTimeMillis();
    
    Object obj =   CachEngine.getObject(ContractRegistrationInterfaceKey.CACH_OBJ_BATCH_STATUS_TYPE_AUTHENTICATION_CALL);
    //Utility.logger.debug("objected created  : getUserContractWarningByPhase"+phaseName);
    BatchStatusTypeDto batchStatusTypeDto =null;

    if (obj ==null)
    {    
       try
         {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "SELECT * FROM VW_CR_BATCH_STATUS_TYPE STATUS_TYPE, VW_CR_PHASE PHASE" 
                      +" WHERE STATUS_TYPE.PHASE_ID = PHASE.PHASE_ID"
                      +" AND (PHASE_NAME = 'AUTHINTICATION CALL' or BATCH_STATUS_TYPE_NAME='OPEN COMMISSION')"
                      +" order by batch_status_type_name";      
          ResultSet res = stat.executeQuery(sql );
          batchStatusTypeDto = new BatchStatusTypeDto();
          while (res.next())
          {
            String batchStatusTypeName = res.getString("batch_Status_type_name");
            int batchStatusTypeId = res.getInt("batch_Status_type_id");
            batchStatusTypeDto.addBatchStatusTypeModel(new BatchStatusTypeModel(batchStatusTypeId,batchStatusTypeName));                                
          }
          Utility.closeConnection(con);  
          CachEngine.storeObject(ContractRegistrationInterfaceKey.CACH_OBJ_BATCH_STATUS_TYPE_AUTHENTICATION_CALL , batchStatusTypeDto);      
        }
        catch(Exception e)
        {  
        e.printStackTrace();
        }
    }
    else 
    {
        batchStatusTypeDto = (BatchStatusTypeDto) obj;
    }
    Utility.logger.debug("time took = " +( System.currentTimeMillis() - startTime ));
    return batchStatusTypeDto;
  }


/*
 * 8-  get batch status types allowed in the delivery verification  
 */
 public static  BatchStatusTypeDto getBatchStatusTypeAllowedInDelivery(Connection con)
  {    
   try
     {
    //  Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      String sql = "SELECT * FROM VW_CR_BATCH_STATUS_TYPE STATUS_TYPE, VW_CR_PHASE PHASE" 
                  +" WHERE STATUS_TYPE.PHASE_ID = PHASE.PHASE_ID"
                  +" AND ( PHASE_NAME = 'DELIVERY VERIFICATION' or PHASE_NAME = 'CONTRACT VERIFICATION' or PHASE_NAME='IMPORT')"
                  +" order by batch_status_type_name";      
      ResultSet res = stat.executeQuery(sql );
      BatchStatusTypeDto batchStatusTypeDto = new BatchStatusTypeDto();
      while (res.next())
      {
        String batchStatusTypeName = res.getString("batch_Status_type_name");
        int batchStatusTypeId = res.getInt("batch_Status_type_id");
        batchStatusTypeDto.addBatchStatusTypeModel(new BatchStatusTypeModel(batchStatusTypeId,batchStatusTypeName));                                
      }
      res.close();
      stat.close();
    
      //Utility.closeConnection(con);  
      return batchStatusTypeDto;
    }
    catch(Exception e)
    {  
    e.printStackTrace();
    }
    return null;
  }

  /*
   * 9- get batch status type by phase 
   * get all the status that are in this phase 
   */
  public static  BatchStatusTypeDto  getBatchStatusTypeByPhase(String phaseName)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery("select * from vw_cr_batch_status_type,vw_cr_phase where vw_cr_batch_status_type.PHASE_ID = vw_cr_phase.PHASE_ID and phase_name=\'"+phaseName+"\' order by batch_status_type_name");
      BatchStatusTypeDto batchStatusTypeDto = new BatchStatusTypeDto();
      while (res.next())
      {
        String batchStatusTypeName = res.getString("batch_Status_type_name");
        int batchStatusTypeId = res.getInt("batch_Status_type_id");
        batchStatusTypeDto.addBatchStatusTypeModel(new BatchStatusTypeModel(batchStatusTypeId,batchStatusTypeName));                                
      }
      res.close();
      stat.close();
    
      Utility.closeConnection(con);  
      return batchStatusTypeDto;
    }
    catch(Exception e)
    {  
    e.printStackTrace();
    }
    return null;
  }

  

/*
 * 10-get batch types in a batchTypeDto
 */
  public static  BatchTypeDto getBatchTypes(Connection con)
  {
   // long startTime = System.currentTimeMillis();
    
    Object obj =   CachEngine.getObject(ContractRegistrationInterfaceKey.CACH_OBJ_BATCH_TYPE);
    //Utility.logger.debug("objected created  : getUserContractWarningByPhase"+phaseName);
    BatchTypeDto batchTypeDto =null;

    if (obj ==null)
    {
        try
        {
   //       Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          ResultSet res = stat.executeQuery("select * from vw_cr_batch_type order by batch_type_name");
          batchTypeDto = new BatchTypeDto();
          while (res.next())
          {
            String batchTypeName = res.getString("batch_type_name");
            int batchTypeId = res.getInt("batch_type_id");
            batchTypeDto.addBatchTypeModel(new BatchTypeModel(batchTypeId,batchTypeName));                                
          }
          res.close();
	      stat.close();
	    
   //       Utility.closeConnection(con);
          CachEngine.storeObject(ContractRegistrationInterfaceKey.CACH_OBJ_BATCH_TYPE , batchTypeDto);      
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        
    }
    else 
    {
        batchTypeDto = (BatchTypeDto) obj;
    }
 //   Utility.logger.debug("time took = " +( System.currentTimeMillis() - startTime ));
    return batchTypeDto;
  }

 public static Hashtable getBatchAuthStatistics(String batchID) {
 Hashtable myData= new Hashtable();
     try {
         Connection con= Utility.getConnection();
         Statement stat = con.createStatement();

         
         ResultSet res = stat.executeQuery("select VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME, COUNT(*)contracts_count from  vw_gen_product , CR_SHEET,VW_CR_CONTRACT_STATUS_REIMPORT where batch_id="+batchID+ "AND CR_SHEET.SHEET_ID = VW_CR_CONTRACT_STATUS_REIMPORT.SHEET_ID AND vw_gen_product.product_id= VW_CR_CONTRACT_STATUS_REIMPORT.contract_type_id and vw_gen_product.product_category_name='PREPAID' and ( VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED VERIFY'  or VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED AUTHINTICATION' or VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='REJECTED AUTHINTICATION') GROUP BY VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ");
         
         while (res.next()) {
             myData.put(res.getString(1), new Integer(res.getInt(2)));
         }
         
         res.close();
         res=stat.executeQuery("select nvl(batch_local_auth_percentage ,batch_auth_percentage) batch_auth_percentage from cr_batch_type,cr_batch where cr_batch.batch_id="+batchID+" and cr_batch.BATCH_TYPE_ID = cr_batch_type.batch_type_id");
         if (res.next())
         {
         myData.put("percentage",new Integer(res.getInt(1)));
         }
         res.close();
	      stat.close();
	         
         Utility.closeConnection(con);
     }
     catch (Exception e) {
         e.printStackTrace();
     }
     return myData;
 }
 /*
  * 11- get batch information of the batch with the id sent to the method  
  */
  public static  BatchDto getBatchInfo(String id)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery( " SELECT batch.batch_id,to_char(batch_date,'dd/mm/yyyy') batch_date ,batch_type_name,vw_gen_dcm.dcm_id,dcm_name,batch_status_type_name,batch.archiving_flag " 
                     +" FROM VW_CR_BATCH BATCH,VW_GEN_DCM, VW_CR_BATCH_sTATUS BATCH_STATUS , vw_cr_Batch_status_type , vw_cr_batch_type"
                     +" WHERE "
                     +" BATCH.BATCH_ID = BATCH_STATUS.BATCH_ID  "
                     +" AND BATCH.DCM_ID = VW_GEN_DCM.DCM_ID "
                     +" AND batch.batch_id = "+ id 
                     +" AND vw_cr_Batch_status_type.BATCH_STATUS_TYPE_ID = batch_status.BATCH_STATUS_TYPE_ID "
                     +"and vw_cr_batch_type.BATCH_TYPE_ID = batch.BATCH_TYPE_ID "
                     +" and BATCH_STATUS.BATCH_STATUS_ID = "
                     +" (SELECT BATCH_STATUS_ID FROM (SELECT  batch_id, BATCH_STATUS_ID FROM VW_CR_BATCH_STATUS where batch_id = "+ id +"ORDER BY BATCH_STATUS_DATE DESC)BATCH_sTATUS_FILTER WHERE ROWNUM=1 AND BATCH_sTATUS_FILTER.BATCH_ID=BATCH.BATCH_ID) ");

      BatchDto batchDto = null ;
      while (res.next())
      {
        batchDto = new BatchDto();
        String batchId = res.getString("Batch_id");
        String batchDate = res.getString("Batch_Date");
        String batchTypeId = res.getString("Batch_type_name");
        String batchDCMId = res.getString("dcm_id");
        String batchDCMName = res.getString("dcm_name");
        String statusName = res.getString("batch_Status_type_name"); 
        String archivingFlag = res.getString("ARCHIVING_FLAG");
        BatchModel batchModel = new BatchModel(batchId,batchDate,batchDCMId,batchTypeId,batchDCMName,archivingFlag);
        batchModel.setBatchStatus(statusName);
        batchDto.setBatchModel(batchModel);                        
      }
      res.close();
      stat.close();
    
      Utility.closeConnection(con);
      return batchDto;
    }
    catch (Exception e)
    {      
    }
    return null;
  }

  
  public static  BatchModel getBatch(String id)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery( " SELECT batch.batch_id,to_char(batch_date,'dd/mm/yyyy') batch_date ,batch_type_name,vw_gen_dcm.dcm_id,dcm_name,batch_status_type_name,batch.archiving_flag " 
                     +" FROM VW_CR_BATCH BATCH,VW_GEN_DCM, VW_CR_BATCH_sTATUS BATCH_STATUS , vw_cr_Batch_status_type , vw_cr_batch_type"
                     +" WHERE "
                     +" BATCH.BATCH_ID = BATCH_STATUS.BATCH_ID  "
                     +" AND BATCH.DCM_ID = VW_GEN_DCM.DCM_ID "
                     +" AND batch.batch_id = "+ id 
                     +" AND vw_cr_Batch_status_type.BATCH_STATUS_TYPE_ID = batch_status.BATCH_STATUS_TYPE_ID "
                     +"and vw_cr_batch_type.BATCH_TYPE_ID = batch.BATCH_TYPE_ID "
                     +" and BATCH_STATUS.BATCH_STATUS_ID = "
                     +" (SELECT BATCH_STATUS_ID FROM (SELECT  batch_id, BATCH_STATUS_ID FROM VW_CR_BATCH_STATUS where batch_id = "+ id +"ORDER BY BATCH_STATUS_DATE DESC)BATCH_sTATUS_FILTER WHERE ROWNUM=1 AND BATCH_sTATUS_FILTER.BATCH_ID=BATCH.BATCH_ID) ");

      BatchModel batchModel =null;
      while (res.next())
      {
   
        String batchId = res.getString("Batch_id");
        String batchDate = res.getString("Batch_Date");
        String batchTypeId = res.getString("Batch_type_name");
        String batchDCMId = res.getString("dcm_id");
        String batchDCMName = res.getString("dcm_name");
        String statusName = res.getString("batch_Status_type_name"); 
        String archivingFlag = res.getString("ARCHIVING_FLAG");
        batchModel = new BatchModel(batchId,batchDate,batchDCMId,batchTypeId,batchDCMName,archivingFlag);
        batchModel.setBatchStatus(statusName);                             
      }
      res.close();
      stat.close();
    
      Utility.closeConnection(con);
      return batchModel;
    }
    catch (Exception e)
    {      
    }
    return null;
  }
/*
 * 12-get batch  it return a batch dto object of the batch with id equal to the id sent to it
 * and all its sheet that are in the phase phaseName 
 */
  public static  BatchDto getBatch(String id, String phaseName,Connection con)
  {    
  try
    {
    
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery( " SELECT batch.batch_id,to_char(batch_date,'dd/mm/yyyy') batch_date ,batch_type_name,vw_gen_dcm.dcm_id,dcm_name,batch_status_type_name,batch.archiving_flag " 
                     +" FROM VW_CR_BATCH BATCH,VW_GEN_DCM, VW_CR_BATCH_sTATUS BATCH_STATUS , vw_cr_Batch_status_type , vw_cr_batch_type"
                     +" WHERE "
                     +" BATCH.BATCH_ID = BATCH_STATUS.BATCH_ID  "
                     +" AND BATCH.DCM_ID = VW_GEN_DCM.DCM_ID "
                     +" AND batch.batch_id = "+ id 
                     +" AND vw_cr_Batch_status_type.BATCH_STATUS_TYPE_ID = batch_status.BATCH_STATUS_TYPE_ID "
                     +"and vw_cr_batch_type.BATCH_TYPE_ID = batch.BATCH_TYPE_ID "
                     +" and BATCH_STATUS.BATCH_STATUS_ID = "
                     +" (SELECT BATCH_STATUS_ID FROM (SELECT batch_id, batch_status_id FROM VW_CR_BATCH_STATUS where batch_id = "+ id +" ORDER BY BATCH_STATUS_DATE DESC)BATCH_sTATUS_FILTER WHERE ROWNUM=1 AND BATCH_sTATUS_FILTER.BATCH_ID=BATCH.BATCH_ID) ");

      BatchDto batchDto = null ;
      while (res.next())
      {
        batchDto = new BatchDto();
        String batchId = res.getString("Batch_id");
        String batchDate = res.getString("Batch_Date");
        String batchTypeId = res.getString("Batch_type_name");
        String batchDCMId = res.getString("dcm_id");
        String batchDCMName = res.getString("dcm_name");
        String statusName = res.getString("batch_Status_type_name");
        String archivingFlag = res.getString("ARCHIVING_FLAG");
        BatchModel batchModel = new BatchModel(batchId,batchDate,batchDCMId,batchTypeId,batchDCMName,archivingFlag);
        batchModel.setBatchStatus(statusName);
        batchDto.setBatchModel(batchModel);
      } 
      res.close();
      stat.close();
    
    
      return batchDto;
    }
    catch (Exception e)
    {      
    }
    return null;
  }

  public static  BatchDto getBatchAndSheets(String id, String phaseName,Connection con)
  {    
  try
    {
     
      Statement stat = con.createStatement();
      
      
      String sql = " SELECT batch.batch_id,to_char(batch_date,'dd/mm/yyyy') batch_date ,batch_type_name,vw_gen_dcm.dcm_id,dcm_name,batch_status_type_name,batch.archiving_flag " 
                     +" FROM  vw_cr_Batch_status_type , vw_cr_batch_type , VW_GEN_DCM,  VW_CR_BATCH BATCH, VW_CR_BATCH_sTATUS BATCH_STATUS  "
                     +" WHERE "
                     +"  batch.batch_id = "+ id
                     +" and BATCH_STATUS.BATCH_STATUS_ID = "
                     +" (SELECT BATCH_STATUS_ID FROM (SELECT batch_id, batch_status_id FROM VW_CR_BATCH_STATUS where batch_id = " + id +" ORDER BY BATCH_STATUS_DATE DESC)BATCH_sTATUS_FILTER WHERE ROWNUM=1 AND BATCH_sTATUS_FILTER.BATCH_ID=BATCH.BATCH_ID) "
                     +" and BATCH.BATCH_ID = BATCH_STATUS.BATCH_ID  "
                     +" AND BATCH.DCM_ID = VW_GEN_DCM.DCM_ID " 
                     +" AND vw_cr_Batch_status_type.BATCH_STATUS_TYPE_ID = batch_status.BATCH_STATUS_TYPE_ID "
                     +" and vw_cr_batch_type.BATCH_TYPE_ID = batch.BATCH_TYPE_ID "; 


      ResultSet res = stat.executeQuery( sql);
      System.out.println("The batch sheets query isssssss " + sql);
      //Utility.logger.debug("time to batch details = " + (System.currentTimeMillis() - startT));
      //Utility.logger.debug(sql);


       
       

                     

      BatchDto batchDto = null ;
      while (res.next())
      {
        batchDto = new BatchDto();
        String batchId = res.getString("Batch_id");
        String batchDate = res.getString("Batch_Date");
        String batchTypeId = res.getString("Batch_type_name");
        String batchDCMId = res.getString("dcm_id");
        String batchDCMName = res.getString("dcm_name");
        String statusName = res.getString("batch_Status_type_name");
        String archivingFlag = res.getString("ARCHIVING_FLAG");
        BatchModel batchModel = new BatchModel(batchId,batchDate,batchDCMId,batchTypeId,batchDCMName,archivingFlag);
        batchModel.setBatchStatus(statusName);
        batchDto.setBatchModel(batchModel);
        //getting batch sheets in this phase
        SheetDto sheetDto = null;
        ////Utility.logger.debug("before geting the batch");
        if (phaseName.compareTo(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE)==0)
        {
        
         sheetDto = SheetDao.getBatchSheetsInContractVerification(batchId,con);
         //Utility.logger.debug("time 3 = " + (System.currentTimeMillis() - startT));
        }
        else
        {
        
          sheetDto = SheetDao.getBatchSheets(batchId,phaseName,con);
        //Utility.logger.debug("time 2 = " + (System.currentTimeMillis() - startT));                               
        }
                         
        batchDto.setSheetDto(sheetDto);        
        
      }      
     
      return batchDto;
    }
    catch (Exception e)
    {    
    e.printStackTrace();
    }
    return null;
  }

  /*
   * 13- the search method 
   * it takes a dcm id which is a must and (date or/and batch type or/and batchstatus our/and phase)
   * and perform a serach operation and return a vector of all the batches that satisfy the search conditions
   */
  public static  Vector SearchBatch(String dcmId, String date, String batchType, String batchStatusTypeId, String phaseName,Connection con)
  {  

  ////Utility.logger.debug("search batch is called " + dcmId +"   " + date+"   " +  batchType+"   "+ batchStatusTypeId+"  " + phaseName);
    try
    {
   
      Statement stat = con.createStatement();
      String sql = "SELECT batch.batch_id,to_char(batch_date,'dd/mm/yyyy') batch_date ,batch_type_name,vw_gen_dcm.dcm_id,dcm_name,batch.batch_status_type_name,batch.archiving_flag "
                   +" FROM vw_cr_phase,VW_CR_BATCH_LAST_STATUS BATCH, vw_gen_dcm , vw_cr_Batch_status_type , vw_cr_batch_type"
                   +" WHERE "                 
                   +"  batch.dcm_id = vw_gen_dcm.dcm_id "
                   +" and vw_cr_phase.phase_id = batch.phase_id "
                   +" AND vw_cr_Batch_status_type.BATCH_STATUS_TYPE_ID = batch.BATCH_STATUS_TYPE_ID "
                   +"and vw_cr_batch_type.BATCH_TYPE_ID = batch.BATCH_TYPE_ID ";                   
      
      if (dcmId!=null && dcmId.length()>0)
      {
        sql+=" and batch.dcm_id = "+ dcmId;
      }
      if (batchType!=null && batchType.length()>0)
      {
        sql+=" and batch.batch_Type_id = "+ batchType;
      }

      if (batchStatusTypeId!=null && batchStatusTypeId.length()>0)
      {
        sql+=" and  vw_cr_batch_status_type.batch_status_type_id = "+ batchStatusTypeId;
      }

      if (date!=null && date.length()>0)
      {
        sql+=" and to_char(batch.batch_date,\'dd/mm/yyyy\') = '"+ date+"'";
      }

      if (phaseName == null)
      {
        
      }
      else if (phaseName.compareTo(ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE)==0)
      {
        sql+=" and ( vw_cr_phase.phase_name = '"+ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE+"'";
        sql+=" or  vw_cr_phase.phase_name = '"+ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE
            + "' or  vw_cr_phase.phase_name = '"+ContractRegistrationInterfaceKey.IMPORT_PHASE + "')";
      }
      else if (phaseName.compareTo(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE)==0)
      {
        sql+= " and  batch.batch_status_type_name in ('"+BatchModel.STATUS_CLOSED_VERIFY+"','"
              +BatchModel.STATUS_OPEN_AUTHINTICATION+"','"+BatchModel.STATUS_OPEN_VERIFY+"')";
        sql+=" and vw_cr_phase.phase_name <> '"+ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE+"'";
      }
      else if (phaseName.compareTo(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE)==0)
      {
      
        sql+= " and  batch.batch_status_type_name in ('"+BatchModel.STATUS_CLOSED_AUTHINTICATION+"','"
              +BatchModel.STATUS_OPEN_AUTHINTICATION+"','" +BatchModel.STATUS_OPEN_COMMISSION+ "')";
        sql+=" and vw_cr_phase.phase_name = '"+ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE+"'";
      }      
      System.out.println ("The batch query issssss " + sql);
      ////Utility.logger.debug( sql );
      ResultSet res = stat.executeQuery( sql);                                        
      Vector batchsCol = new Vector();      
      while (res.next())
      {
        
        BatchDto batchDto = new BatchDto();        
        String batchId = res.getString("Batch_id");
        String batchDate = res.getString("Batch_Date");
        String batchTypeId = res.getString("Batch_type_name");
        String batchDCMId = res.getString("dcm_id");
        String batchDCMName=  res.getString("dcm_name");
        String statusName = res.getString("batch_Status_type_name");
        String archivingFlag = res.getString("ARCHIVING_FLAG");
        BatchModel batchModel = new BatchModel(batchId,batchDate,batchDCMId,batchTypeId,batchDCMName,archivingFlag);
        batchModel.setBatchStatus(statusName);
        batchDto.setBatchModel(batchModel);
        batchsCol.add(batchDto);
      }      
      res.close();
      stat.close();
    
   
      return batchsCol;
    }
    catch (Exception e)
    {
    e.printStackTrace();
    }
    return null;
  }
  

/*
 * 14- create batch 
 * it takes a dcmid and a date and gather all the sheets of this dcmid that dont belong to a batch
 * and create a new batch and assign them all to it 
 * and update the batch status to relect wither it will be new (atleast one of the sheets assigned to it is not rejected)
 * or that it is rejected import that mean all its sheets are rejected sheets 
 */
  public static  Vector createBatch(String dcmId, String batchDate) 
  {
    Vector report = new Vector();
    if (dcmId ==null)
    return null;
    try
    {
      Connection con = Utility.getConnection();
      Statement batchTypesStat = con.createStatement(); 
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery("select * from vw_cr_phase where phase_name = \'"+ContractRegistrationInterfaceKey.IMPORT_PHASE+"\'");
      res.next();
      int deliveryPhaseId = res.getInt("phase_id");
      res = stat.executeQuery("select * from cr_batch_status_type where batch_status_type_name = \'"+BatchModel.STATUS_NEW+"\'");
      res.next();
      int newBatchStatusId = res.getInt("BATCH_STATUS_TYPE_ID");      
      res = stat.executeQuery("select dcm_code from vw_gen_dcm where dcm_id ="+dcmId );
      if (!res.next())
      {
        return null;
      }      

      String dcmCode = res.getString("dcm_code");
      
      String sqlTypesCanBeImported =
      "SELECT SHEET_TYPE.SHEET_TYPE_NAME, BATCH_TYPE.BATCH_TYPE_ID" 
       +" FROM" 
       +" VW_CR_SHEET SHEET, VW_CR_SHEET_TYPE SHEET_TYPE , VW_CR_PHASE,VW_CR_BATCH_TYPE BATCH_TYPE"
       +" WHERE"
       +" SHEET.PHASE_ID = VW_CR_PHASE.PHASE_ID"
       +" AND SHEET.SHEET_DISTRIBUTER_CODE =\'" + dcmCode+"\'"
       +" AND PHASE_NAME = \'IMPORT\'"
       +" AND BATCH_ID is NULL"
       +" AND BATCH_TYPE.BATCH_TYPE_NAME = SHEET_TYPE.SHEET_TYPE_NAME"
       +" AND SHEET.SHEET_TYPE_ID = SHEET_TYPE.SHEET_TYPE_ID"   
       +" GROUP BY SHEET_TYPE.SHEET_TYPE_NAME,BATCH_TYPE_ID";      

      //Utility.logger.debug(sqlTypesCanBeImported);

      ResultSet batchTypesRes = batchTypesStat.executeQuery(sqlTypesCanBeImported); 
      while (   batchTypesRes.next())
      {
        int batchTypeId = batchTypesRes.getInt("BATCH_TYPE_ID");
        String sheetTypeName = batchTypesRes.getString("SHEET_TYPE_NAME");
        ResultSet resId =stat.executeQuery("select seq_cr_batch.nextval from dual" );
        resId.next();
        int batchId = resId.getInt(1);  
        report.add("Batch ID=" +batchId + " of Type = " + sheetTypeName + " was created"  );
        String insertNewBatchSQL =  "insert into Cr_batch (batch_id,batch_type_id,batch_date,phase_id,dcm_id,archiving_flag)"
                                   +"values("+batchId+","+batchTypeId+","+ batchDate+","+deliveryPhaseId +","+dcmId+",0)";        
        stat.execute(insertNewBatchSQL);
        String insertNewBatchStatusSQL = "insert into vw_cr_batch_status (batch_status_id, batch_id, batch_status_type_id, batch_status_date)"
                                        +"VALUES( SEQ_CR_BATCH_STATUS.NEXTVAL,"+batchId+","+newBatchStatusId+",SYSDATE)";
        stat.execute(insertNewBatchStatusSQL);
        
        String updateSheets = 
                "update cr_sheet set phase_id = "+deliveryPhaseId+",batch_id="+batchId 
               +" where cr_sheet.sheet_id in"
               +" (select sheet_id "
               +" FROM"  
               +" VW_CR_SHEET SHEET, VW_CR_SHEET_TYPE SHEET_TYPE , VW_CR_PHASE"
               +" WHERE"
               +" SHEET.PHASE_ID = VW_CR_PHASE.PHASE_ID"
               +" AND PHASE_NAME = \'IMPORT\'"
               +" AND SHEET.SHEET_DISTRIBUTER_CODE =\'" + dcmCode+"\'"               
               +" AND BATCH_ID is NULL"    
               +" and cr_sheet.sheet_type_id ="+batchTypeId                
               +" AND SHEET.SHEET_TYPE_ID = SHEET_TYPE.SHEET_TYPE_ID)";
        ////Utility.logger.debug(updateSheets);               
        stat.execute(updateSheets);   

        updateBatchStatus(batchId+"",con);        
      }
      Utility.closeConnection(con);
      if (report.size()==0)
      {
        report.add("No available sheets for this distributer to create a batch");
      }
      return report;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null; 
    }    
  }
  
  
  public static Vector<ContractDialNumberModel> getContractDate(String contractDialNo)
  {
	  Vector<ContractDialNumberModel> contractDataVec = new Vector<ContractDialNumberModel>();
	  try
	  {
		  Connection con = Utility.getConnection();
		  Statement stat = con.createStatement();
		  String strSql = "select cr_contract.CONTRACT_DIAL_NO,cr_batch.BATCH_ID,cr_batch.batch_date,"+
		  				  "gen_dcm.DCM_NAME,cr_contract_status.contract_status_date,"+
		  				  "cr_batch_status_type.BATCH_STATUS_TYPE_NAME,cr_batch_type.BATCH_TYPE_NAME," +
		  				  "cr_contract_status.contract_status_type_id,cr_contract_status_type.contract_status_type_name "+
		  				  "from cr_contract,cr_sheet,cr_batch,cr_batch_status_type,cr_batch_type,gen_dcm,"+
		  				  "cr_contract_status,cr_contract_status_type "+
		  				  "where cr_contract.CONTRACT_DIAL_NO = '"+contractDialNo+"'" +
		  				  "and cr_contract.CONTRACT_LAST_STATUS_ID = cr_contract_status.CONTRACT_STATUS_ID "+
		  				  "and cr_contract_status.CONTRACT_STATUS_TYPE_ID = cr_contract_status_type.CONTRACT_STATUS_TYPE_ID "+
		  				  "and (cr_contract_status_type.CONTRACT_STATUS_TYPE_ID = '5' or cr_contract_status_type.CONTRACT_STATUS_TYPE_ID = '6' "+
		  				  "or cr_contract_status_type.CONTRACT_STATUS_TYPE_ID = '7' or cr_contract_status_type.CONTRACT_STATUS_TYPE_ID = '8') "+
		  				  "and cr_contract.SHEET_ID = cr_sheet.SHEET_ID "+
		  				  "and cr_sheet.batch_id = cr_batch.batch_id "+
		  				  "and cr_sheet.SHEET_DISTRIBUTER_CODE = gen_dcm.DCM_CODE "+
		  				  "and cr_batch.batch_last_status_type_id = cr_batch_status_type.BATCH_STATUS_TYPE_ID "+
		  				  "and cr_batch.BATCH_TYPE_ID = cr_batch_type.BATCH_TYPE_ID "+
		  				  "order by contract_status_date desc";
		  System.out.println("the query issssssssssssssss " + strSql);
		  ResultSet res = stat.executeQuery(strSql);
		  while(res.next()) 
		  {
			  contractDataVec.add(new ContractDialNumberModel(res));
			  break;
		  }
		  res.close();
	      stat.close();
	      
	      Utility.closeConnection(con);
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  return contractDataVec;
  }
  
  
  public static ContractModel getContractData (String contractDialNo)
  {
	  try
	  {
		  Connection con = Utility.getConnection();
	      Statement stat = con.createStatement();
	      String sql = "SELECT cr_contract.contract_last_status_id,cr_contract.CONTRACT_MAIN_SIM_NO,"+
	      			   "cr_contract.CONTRACT_TYPE_ID,cr_contract.CONTRACT_ID,cr_contract_status_type.contract_status_type_name,"+
	      			   "gen_product.PRODUCT_NAME,cr_contract.contract_dial_no,"+
	      			   "CONCAT( CONCAT (CONCAT (CR_CONTRACT.CONTRACT_CUSTOMER_1ST_NAME,' ') , CONCAT(CR_CONTRACT.CONTRACT_CUSTOMER_2ND_NAME ,' ')) ,CR_CONTRACT.CONTRACT_CUSTOMER_LST_NAME) as CONTRACT_CUSTOMER_NAME,"+
	      			   "cr_contract.CONTRACT_ADDRESS,cr_contract.CONTRACT_AREA,cr_contract.CONTRACT_CUSTOMER_ID,"+
	      			   "cr_contract.CONTRACT_CUSTOMER_ID_type,CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_NAME,"+
	      			   "cr_contract.SHEET_ID,cr_contract.CONTRACT_HOME_PHONE,cr_sheet.SHEET_SERIAL_ID,"+
	      			   "SDS_GET_CONTRACT_REIMPORT_FLAG  (cr_contract.CONTRACT_MAIN_SIM_NO,  cr_contract.CONTRACT_ID ,cr_contract.SHEET_ID)   RE_IMPORTED_FLAG,"+
	      			   "cr_contract_status.USER_ID,gen_person.PERSON_FULL_NAME,cr_contract.AUTOMATIC_FLAG,cr_contract.CONTRACT_CUSTOMER_BIRTH_DATE "+
	      			   "FROM cr_contract,cr_sheet,cr_batch,cr_batch_status_type,CR_CUSTOMER_ID_TYPE,"+
	      			   "cr_batch_type,gen_dcm,gen_product,gen_person,cr_contract_status,cr_contract_status_type "+
	      			   "WHERE cr_contract.contract_dial_no = '"+contractDialNo+"' "+
	      			   "AND cr_contract.contract_last_status_id =cr_contract_status.contract_status_id "+
	      			   "ANd cr_contract.CONTRACT_TYPE_ID = gen_product.PRODUCT_ID "+
	      			   "and CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID = cr_contract.CONTRACT_CUSTOMER_ID_TYPE "+
	      			   "and gen_person.PERSON_ID =  cr_contract_status.USER_ID "+
	      			   "AND cr_contract_status.contract_status_type_id =cr_contract_status_type.contract_status_type_id "+
	      			  "AND cr_contract.sheet_id = cr_sheet.sheet_id "+
	      			   "AND cr_sheet.batch_id = cr_batch.batch_id "+
	      			   "AND cr_sheet.sheet_distributer_code = gen_dcm.dcm_code "+
	      			   "AND cr_batch.batch_last_status_type_id =cr_batch_status_type.batch_status_type_id "+
	      			   "AND cr_batch.batch_type_id = cr_batch_type.batch_type_id "+
	      			   "ORDER BY contract_status_date DESC";
	
	      ResultSet res = stat.executeQuery(sql);
	      
	      ContractModel contractModel = null;
	      if (res.next())
	      {
	        contractModel = new ContractModel(res);
	        contractModel.setAutomaticFlag(res.getString(ContractModel.AUTOMATIC_FLAG));
	        contractModel.setCustomerBirthDate(res.getString("CONTRACT_CUSTOMER_BIRTH_DATE"));
	      }
	      
	      res.close();
	      stat.close();
	      
	      Utility.closeConnection(con);
	      return contractModel;
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  
  }
  
  public static String getBatchLastStatus (String contractDialNumber)
  {	  
	  String sql = "SELECT cr_batch.BATCH_LAST_STATUS_TYPE_ID "+	      			 
		   "FROM cr_contract,cr_sheet,cr_batch,cr_batch_status_type,CR_CUSTOMER_ID_TYPE,"+
		   "cr_batch_type,gen_dcm,gen_product,gen_person,cr_contract_status,cr_contract_status_type "+
		   "WHERE cr_contract.contract_dial_no = '"+contractDialNumber+"' "+
		   "AND cr_contract.contract_last_status_id =cr_contract_status.contract_status_id "+
		   "ANd cr_contract.CONTRACT_TYPE_ID = gen_product.PRODUCT_ID "+
		   "and CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID = cr_contract.CONTRACT_CUSTOMER_ID_TYPE "+
		   "and gen_person.PERSON_ID =  cr_contract_status.USER_ID "+
		   "AND cr_contract_status.contract_status_type_id =cr_contract_status_type.contract_status_type_id "+
		   "AND cr_contract.sheet_id = cr_sheet.sheet_id "+
		   "AND cr_sheet.batch_id = cr_batch.batch_id "+
		   "AND cr_sheet.sheet_distributer_code = gen_dcm.dcm_code "+
		   "AND cr_batch.batch_last_status_type_id =cr_batch_status_type.batch_status_type_id "+
		   "AND cr_batch.batch_type_id = cr_batch_type.batch_type_id "+
		   "ORDER BY contract_status_date DESC";
	  String batchLastStatus =  DBUtil.executeQuerySingleValueString(sql, "BATCH_LAST_STATUS_TYPE_ID");
	  
	  return batchLastStatus;
  }
  
  public static String getContractLastStatus (String contractDialNumber)
  {
	  
	  String sql = "SELECT cr_contract_status.CONTRACT_STATUS_TYPE_ID "+	      			 
		   "FROM cr_contract,cr_sheet,cr_batch,cr_batch_status_type,CR_CUSTOMER_ID_TYPE,"+
		   "cr_batch_type,gen_dcm,gen_product,gen_person,cr_contract_status,cr_contract_status_type "+
		   "WHERE cr_contract.contract_dial_no = '"+contractDialNumber+"' "+
		   "AND cr_contract.contract_last_status_id =cr_contract_status.contract_status_id "+
		   "ANd cr_contract.CONTRACT_TYPE_ID = gen_product.PRODUCT_ID "+
		   "and CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID = cr_contract.CONTRACT_CUSTOMER_ID_TYPE "+
		   "and gen_person.PERSON_ID =  cr_contract_status.USER_ID "+
		   "AND cr_contract_status.contract_status_type_id =cr_contract_status_type.contract_status_type_id "+
		   "AND cr_contract.sheet_id = cr_sheet.sheet_id "+
		   "AND cr_sheet.batch_id = cr_batch.batch_id "+
		   "AND cr_sheet.sheet_distributer_code = gen_dcm.dcm_code "+
		   "AND cr_batch.batch_last_status_type_id =cr_batch_status_type.batch_status_type_id "+
		   "AND cr_batch.batch_type_id = cr_batch_type.batch_type_id "+
		   "ORDER BY contract_status_date DESC";
	  String contractLastStatus =  DBUtil.executeQuerySingleValueString(sql, "CONTRACT_STATUS_TYPE_ID");	  	  
	  return contractLastStatus;	  	  
  }
  
  public static String getContractSiMnumber (String contractDialNumber)
  {
	  
	String sql = "SELECT cr_contract.CONTRACT_MAIN_SIM_NO "+	      			 
	   "FROM cr_contract,cr_sheet,cr_batch,cr_batch_status_type,CR_CUSTOMER_ID_TYPE,"+
	   "cr_batch_type,gen_dcm,gen_product,gen_person,cr_contract_status,cr_contract_status_type "+
	   "WHERE cr_contract.contract_dial_no = '"+contractDialNumber+"' "+
	   "AND cr_contract.contract_last_status_id =cr_contract_status.contract_status_id "+
	   "ANd cr_contract.CONTRACT_TYPE_ID = gen_product.PRODUCT_ID "+
	   "and CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID = cr_contract.CONTRACT_CUSTOMER_ID_TYPE "+
	   "and gen_person.PERSON_ID =  cr_contract_status.USER_ID "+
	   "AND cr_contract_status.contract_status_type_id =cr_contract_status_type.contract_status_type_id "+
	   "AND cr_contract.sheet_id = cr_sheet.sheet_id "+
	   "AND cr_sheet.batch_id = cr_batch.batch_id "+
	   "AND cr_sheet.sheet_distributer_code = gen_dcm.dcm_code "+
	   "AND cr_batch.batch_last_status_type_id =cr_batch_status_type.batch_status_type_id "+
	   "AND cr_batch.batch_type_id = cr_batch_type.batch_type_id "+
	   "ORDER BY contract_status_date DESC";
	
	  String contractSimNumber =  DBUtil.executeQuerySingleValueString(sql, "CONTRACT_MAIN_SIM_NO");
	return contractSimNumber;
  }
  
  public static String getContractId (String contractDialNumber)
  {	 
	  String sql = "SELECT cr_contract.CONTRACT_ID "+	      			 
		   "FROM cr_contract,cr_sheet,cr_batch,cr_batch_status_type,CR_CUSTOMER_ID_TYPE,"+
		   "cr_batch_type,gen_dcm,gen_product,gen_person,cr_contract_status,cr_contract_status_type "+
		   "WHERE cr_contract.contract_dial_no = '"+contractDialNumber+"' "+
		   "AND cr_contract.contract_last_status_id =cr_contract_status.contract_status_id "+
		   "ANd cr_contract.CONTRACT_TYPE_ID = gen_product.PRODUCT_ID "+
		   "and CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID = cr_contract.CONTRACT_CUSTOMER_ID_TYPE "+
		   "and gen_person.PERSON_ID =  cr_contract_status.USER_ID "+
		   "AND cr_contract_status.contract_status_type_id =cr_contract_status_type.contract_status_type_id "+
		   "AND cr_contract.sheet_id = cr_sheet.sheet_id AND cr_sheet.batch_id = cr_batch.batch_id "+
		   "AND cr_sheet.sheet_distributer_code = gen_dcm.dcm_code "+
		   "AND cr_batch.batch_last_status_type_id =cr_batch_status_type.batch_status_type_id "+
		   "AND cr_batch.batch_type_id = cr_batch_type.batch_type_id ORDER BY contract_status_date DESC";
	  
	  String contractId =  DBUtil.executeQuerySingleValueString(sql, "CONTRACT_ID");	 
	  return contractId;
  }
  
  public static String getSheetId (String contractDialNumber)
  {
	  String sql = "SELECT cr_contract.SHEET_ID "+	      			 
		   "FROM cr_contract,cr_sheet,cr_batch,cr_batch_status_type,CR_CUSTOMER_ID_TYPE,"+
		   "cr_batch_type,gen_dcm,gen_product,gen_person,cr_contract_status,cr_contract_status_type "+
		   "WHERE cr_contract.contract_dial_no = '"+contractDialNumber+"' "+
		   "AND cr_contract.contract_last_status_id =cr_contract_status.contract_status_id "+
		   "ANd cr_contract.CONTRACT_TYPE_ID = gen_product.PRODUCT_ID "+
		   "and CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID = cr_contract.CONTRACT_CUSTOMER_ID_TYPE "+
		   "and gen_person.PERSON_ID =  cr_contract_status.USER_ID "+
		   "AND cr_contract_status.contract_status_type_id =cr_contract_status_type.contract_status_type_id "+
		   "AND cr_contract.sheet_id = cr_sheet.sheet_id "+
		   "AND cr_sheet.batch_id = cr_batch.batch_id "+
		   "AND cr_sheet.sheet_distributer_code = gen_dcm.dcm_code "+
		   "AND cr_batch.batch_last_status_type_id =cr_batch_status_type.batch_status_type_id "+
		   "AND cr_batch.batch_type_id = cr_batch_type.batch_type_id "+
		   "ORDER BY contract_status_date DESC";
	  
	  
	  String sheetId =  DBUtil.executeQuerySingleValueString(sql, "SHEET_ID");
	  
	  return sheetId;
  }
  
  /*public static String getArchivingFlag (String contractDialNumber)
  {
	  String archivingFlag = "";
	  try
	  {
		  Connection con = Utility.getConnection();
	      Statement stat = con.createStatement();
	      String sql = "SELECT cr_batch.ARCHIVING_FLAG "+	      			 
	      			   "FROM cr_contract,cr_sheet,cr_batch,cr_batch_status_type,CR_CUSTOMER_ID_TYPE,"+
	      			   "cr_batch_type,gen_dcm,gen_product,gen_person,cr_contract_status,cr_contract_status_type "+
	      			   "WHERE cr_contract.contract_dial_no = '"+contractDialNumber+"' "+
	      			   "AND cr_contract.contract_last_status_id =cr_contract_status.contract_status_id "+
	      			   "ANd cr_contract.CONTRACT_TYPE_ID = gen_product.PRODUCT_ID "+
	      			   "and CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID = cr_contract.CONTRACT_CUSTOMER_ID_TYPE "+
	      			   "and gen_person.PERSON_ID =  cr_contract_status.USER_ID "+
	      			   "AND cr_contract_status.contract_status_type_id =cr_contract_status_type.contract_status_type_id "+
	      			   "AND (   cr_contract_status_type.contract_status_type_id = '5' OR cr_contract_status_type.contract_status_type_id = '6' "+
	      			   "OR cr_contract_status_type.contract_status_type_id = '7' OR cr_contract_status_type.contract_status_type_id = '8') "+
	      			   "AND cr_contract.sheet_id = cr_sheet.sheet_id "+
	      			   "AND cr_sheet.batch_id = cr_batch.batch_id "+
	      			   "AND cr_sheet.sheet_distributer_code = gen_dcm.dcm_code "+
	      			   "AND cr_batch.batch_last_status_type_id =cr_batch_status_type.batch_status_type_id "+
	      			   "AND cr_batch.batch_type_id = cr_batch_type.batch_type_id "+
	      			   "ORDER BY contract_status_date DESC";
	      System.out.println("The select query issssssssssss " + sql);
	      ResultSet res = stat.executeQuery(sql);
	      	      while (res.next())
	      {
	      	    	archivingFlag = res.getString("ARCHIVING_FLAG");
	        break;
	        //contractModel.setContractFormNumber(res.getString("contract_Form_Number"));
	      }
	      res.close();
	      stat.close();
	      
	      Utility.closeConnection(con); 
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  return archivingFlag;
  }*/
  
  /*public static String getSheetSerial (String contractDialNumber)
  {
	  String sheetSerial = "";
	  try
	  {
		  Connection con = Utility.getConnection();
	      Statement stat = con.createStatement();
	      String sql = "SELECT cr_sheet.SHEET_SERIAL_ID "+	      			 
	      			   "FROM cr_contract,cr_sheet,cr_batch,cr_batch_status_type,CR_CUSTOMER_ID_TYPE,"+
	      			   "cr_batch_type,gen_dcm,gen_product,gen_person,cr_contract_status,cr_contract_status_type "+
	      			   "WHERE cr_contract.contract_dial_no = '"+contractDialNumber+"' "+
	      			   "AND cr_contract.contract_last_status_id =cr_contract_status.contract_status_id "+
	      			   "ANd cr_contract.CONTRACT_TYPE_ID = gen_product.PRODUCT_ID "+
	      			   "and CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID = cr_contract.CONTRACT_CUSTOMER_ID_TYPE "+
	      			   "and gen_person.PERSON_ID =  cr_contract_status.USER_ID "+
	      			   "AND cr_contract_status.contract_status_type_id =cr_contract_status_type.contract_status_type_id "+
	      			   "AND (   cr_contract_status_type.contract_status_type_id = '5' OR cr_contract_status_type.contract_status_type_id = '6' "+
	      			   "OR cr_contract_status_type.contract_status_type_id = '7' OR cr_contract_status_type.contract_status_type_id = '8') "+
	      			   "AND cr_contract.sheet_id = cr_sheet.sheet_id "+
	      			   "AND cr_sheet.batch_id = cr_batch.batch_id "+
	      			   "AND cr_sheet.sheet_distributer_code = gen_dcm.dcm_code "+
	      			   "AND cr_batch.batch_last_status_type_id =cr_batch_status_type.batch_status_type_id "+
	      			   "AND cr_batch.batch_type_id = cr_batch_type.batch_type_id "+
	      			   "ORDER BY contract_status_date DESC";
	      System.out.println("The select query issssssssssss " + sql);
	      ResultSet res = stat.executeQuery(sql);
	      	      while (res.next())
	      {
	      	    	sheetSerial = res.getString("SHEET_SERIAL_ID");
	        break;
	        //contractModel.setContractFormNumber(res.getString("contract_Form_Number"));
	      }
	      res.close();
	      stat.close();
	      
	      Utility.closeConnection(con); 
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  return sheetSerial;
  }*/
  
  /*public static String getBatchId (String contractDialNumber)
  {
	  String batchId = "";
	  try
	  {
		  Connection con = Utility.getConnection();
	      Statement stat = con.createStatement();
	      String sql = "SELECT cr_batch.BATCH_ID "+	      			 
	      			   "FROM cr_contract,cr_sheet,cr_batch,cr_batch_status_type,CR_CUSTOMER_ID_TYPE,"+
	      			   "cr_batch_type,gen_dcm,gen_product,gen_person,cr_contract_status,cr_contract_status_type "+
	      			   "WHERE cr_contract.contract_dial_no = '"+contractDialNumber+"' "+
	      			   "AND cr_contract.contract_last_status_id =cr_contract_status.contract_status_id "+
	      			   "ANd cr_contract.CONTRACT_TYPE_ID = gen_product.PRODUCT_ID "+
	      			   "and CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID = cr_contract.CONTRACT_CUSTOMER_ID_TYPE "+
	      			   "and gen_person.PERSON_ID =  cr_contract_status.USER_ID "+
	      			   "AND cr_contract_status.contract_status_type_id =cr_contract_status_type.contract_status_type_id "+
	      			   "AND (   cr_contract_status_type.contract_status_type_id = '5' OR cr_contract_status_type.contract_status_type_id = '6' "+
	      			   "OR cr_contract_status_type.contract_status_type_id = '7' OR cr_contract_status_type.contract_status_type_id = '8') "+
	      			   "AND cr_contract.sheet_id = cr_sheet.sheet_id "+
	      			   "AND cr_sheet.batch_id = cr_batch.batch_id "+
	      			   "AND cr_sheet.sheet_distributer_code = gen_dcm.dcm_code "+
	      			   "AND cr_batch.batch_last_status_type_id =cr_batch_status_type.batch_status_type_id "+
	      			   "AND cr_batch.batch_type_id = cr_batch_type.batch_type_id "+
	      			   "ORDER BY contract_status_date DESC";
	      System.out.println("The select query issssssssssss " + sql);
	      ResultSet res = stat.executeQuery(sql);
	      	      while (res.next())
	      {
	      	    	batchId = res.getString("BATCH_ID");
	        break;
	        //contractModel.setContractFormNumber(res.getString("contract_Form_Number"));
	      }
	      res.close();
	      stat.close();
	      
	      Utility.closeConnection(con); 
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  return batchId;
  }
  
  
  
  public static boolean isContractHasWarningInVerification(String contractDialNumber)
  {
   boolean flag = false; 
     try 
    {
      Connection con= Utility.getConnection();
      Statement stmt = con.createStatement();
      String sql = "select * from VW_cr_CONTRACT_HIST_VERFIY where contract_Status_type_name='"+ContractModel.STATUS_REJECTED_VERIFY +"'and  CONTRACT_DIAL_NO='"+contractDialNumber+"'";
      ResultSet res= stmt.executeQuery(sql);
     
      if (res.next())
      flag = true;       
      stmt.close();
      Utility.closeConnection(con);
     
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
     return flag ;
  }*/
  
  public static BatchSheetModel getBatchSheetData (String contractDialNo)
  {
	  try
	  {
		  Connection con = Utility.getConnection();
	      Statement stat = con.createStatement();
	      String sql = "select gen_city.city_english,gen_dcm.DCM_NAME,gen_dcm.DCM_ADDRESS,cr_contract.CONTRACT_MAIN_SIM_NO,cr_batch.ARCHIVING_FLAG,cr_sheet.SHEET_SERIAL_ID,cr_batch.BATCH_ID,cr_sheet.SHEET_POS_CODE, "+
	      			   "cr_batch.BATCH_DATE,cr_batch.BATCH_TYPE_ID,cr_batch.BATCH_LAST_STATUS_TYPE_ID,cr_sheet.SHEET_ID,gen_dcm.DCM_ID,cr_sheet.SHEET_SUPER_DEALER_CODE "+	      			   
	      			   "FROM cr_contract,gen_city,cr_sheet,cr_batch,cr_batch_status_type,CR_CUSTOMER_ID_TYPE,"+
	      			   "cr_batch_type,gen_dcm,gen_product,gen_person,cr_contract_status,cr_contract_status_type "+
	      			   "WHERE cr_contract.contract_dial_no = '"+contractDialNo+"' "+
	      			   "AND cr_contract.contract_last_status_id =cr_contract_status.contract_status_id "+
	      			   "ANd cr_contract.CONTRACT_TYPE_ID = gen_product.PRODUCT_ID "+
	      			   "and CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID = cr_contract.CONTRACT_CUSTOMER_ID_TYPE "+
	      			   "and gen_person.PERSON_ID =  cr_contract_status.USER_ID "+
	      			   "AND cr_contract_status.contract_status_type_id =cr_contract_status_type.contract_status_type_id "+
	      			   "AND cr_contract.sheet_id = cr_sheet.sheet_id "+
	      			   "AND cr_sheet.batch_id = cr_batch.batch_id "+
	      			   "AND cr_sheet.SHEET_POS_CODE = gen_dcm.dcm_code "+
	      			   "AND gen_dcm.dcm_city_id = gen_city.city_code "+
	      			   "AND cr_batch.batch_last_status_type_id =cr_batch_status_type.batch_status_type_id "+
	      			   "AND cr_batch.batch_type_id = cr_batch_type.batch_type_id "+
	      			   "ORDER BY contract_status_date DESC";
	     // System.out.println("The select query issssssssssss " + sql);
	     
	      ResultSet res = stat.executeQuery(sql);
	      
	      BatchSheetModel batchSheetModel = null;
	      while (res.next())
	      {
	    	  batchSheetModel = new BatchSheetModel(res);
	        
	        break;
	        //contractModel.setContractFormNumber(res.getString("contract_Form_Number"));
	      }
	      res.close();
	      stat.close();
	      
	      Utility.closeConnection(con);
	      return batchSheetModel;
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  
  }
  
  
}




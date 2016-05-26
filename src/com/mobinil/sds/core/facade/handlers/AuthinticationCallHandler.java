package com.mobinil.sds.core.facade.handlers;

import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.system.ac.authcall.dao.*;
import com.mobinil.sds.core.system.cr.batch.dao.BatchDao;
import com.mobinil.sds.core.system.cr.batch.dto.*;
import com.mobinil.sds.core.system.cr.batch.model.BatchSheetModel;
import com.mobinil.sds.core.system.cr.contract.dao.*;
import com.mobinil.sds.core.system.cr.contract.dto.*;
import com.mobinil.sds.core.system.cr.contract.model.*;
import com.mobinil.sds.core.system.cr.sheet.dao.*;
import com.mobinil.sds.core.system.cr.sheet.dto.*;
import com.mobinil.sds.core.system.cr.sheet.model.*;
import com.mobinil.sds.core.system.gn.dcm.dao.DCMDao;
import com.mobinil.sds.core.system.gn.dcm.dto.DCMDto;
import com.mobinil.sds.core.system.sa.users.dao.*;
//import com.mobinil.sds.core.system.sa.users.dao.cmp.*;
import com.mobinil.sds.core.system.sa.users.model.*;
import com.mobinil.sds.web.interfaces.*;
import com.mobinil.sds.web.interfaces.ac.*;
import com.mobinil.sds.web.interfaces.cr.*;
import com.mobinil.sds.web.interfaces.gn.ua.*;
import com.mobinil.sds.web.interfaces.sa.*;

import java.sql.*;
import java.util.*;



import com.mobinil.sds.core.system.sa.importdata.dao.*;
  /**
  * AuthinticationCallHandlerEJBBean Statless session bean is responsible for 
  * handling all actions of the authentication call module.
  * 
  * 1- public HashMap handle(String action, HashMap paramHashMap)
  *    Handles all actions of the authentication call module required by client.
  *    
  * @version	1.01 April 2004
  * @author  Victor Saad Fahim
  * @see
  *
  * SDS
  * MobiNil
  */

public class AuthinticationCallHandler 
{
  


  /**
  * handle method:
  * Handles all actions of the authentication call module required by client.
  *  a) action is the required action sent from the client.
  *  b) paramHashMap is holding all attributes from the client side.
  *  c) The required action will be performed.
  *  d) Data required by client is returned in a HashMap.
  *
  * @param	String action, HashMap paramHashMap
  * @return  HashMap
  * @throws  
  * @see    
  * 
  */

  public static HashMap handle(String action, HashMap paramHashMap,java.sql.Connection con)
  {

    HashMap dataHashMap = new HashMap(100);
    String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    if(strUserID != null && strUserID.compareTo("") != 0)
    {
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);           
    }
    /*
     * a. initializing  the batch search screen of the authentication 
     * by sending 
     * the  list of dcm that the user can see upon the user id 
     * and the batch types that the user can select
     * and the batch status types
     * and all of those are sent in the additional collection
     */
    if (action.compareTo(AuthCallInterfaceKey.ACTION_SHOW_SEARCH_BATCH_SCREEN_AUTHENTICATION_CALL)==0 ||
        action.compareTo(AuthCallInterfaceKey.ACTION_AUTHENTICATION_CALL_BATCH_PERCENTAGE)==0||
        action.compareTo(AuthCallInterfaceKey.ACTION_ADMIN_SHOW_SEARCH_BATCH_SCREEN_AUTHENTICATION_CALL)==0 ||
        action.compareTo(AuthCallInterfaceKey.ACTION_SHOW_SERACH_BATCH_BY_SHEET_AUTH_CALL)==0)
    {
      try
      {
        Hashtable additionalCollection = new Hashtable();

        // Return all DCMs of DISTRIBUTOR level as DCMDto.
        DCMDto dcmDto = DCMDao.getAuthenticationCallDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID,con);
        // Return all batch types as BatchTypeDto.
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);
        // Return all batch status types allowed in authintication call module as BatchStatusTypeDto.
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInAuthintication();

        // Fill additionalCollection HashMap with the above objects.
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);
        
        // Insert additionalCollection HashMap in dataHashMap that will be returned to the user.
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    
    /*
     * b. performing the serach on batches 
     */
    else if (action.compareTo(AuthCallInterfaceKey.ACTION_ADMIN_SEARCH_BATCH_AUTHENTICATION_CALL_BATCH_PERCENTAGE)==0 ||
             action.compareTo(AuthCallInterfaceKey.ACTION_SEARCH_BATCH_AUTHENTICATION_CALL)==0 ||
             action.compareTo(AuthCallInterfaceKey.ACTION_ADMIN_SEARCH_BATCH_AUTHENTICATION_CALL)==0 ||
             action.compareTo(AuthCallInterfaceKey.ACTION_SEARCH_BATCH_AUTHENTICATION_CALL_BY_SHEET)==0
             )
    {
      try
      {
        Hashtable additionalCollection = new Hashtable();
        //Keep track of the search criteria by resending the parameters sent by the client.
        //Helpfull to allow back and cancel buttons use.
         
        String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);        
        String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);        
        String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);        
        String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        
        // Return all DCMs of DISTRIBUTOR level as DCMDto.
        DCMDto dcmDto = DCMDao.getAuthenticationCallDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID,con);
        // Return all batch types as BatchTypeDto.
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);
        // Return all batch status types allowed in authintication call module as BatchStatusTypeDto.
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInAuthintication();         

        // Fill additionalCollection HashMap with the above objects.
        //sending the data that the screen need to initialize it self same as the data sent 
        //in initialize batch search by puting them in the optional collection
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);        
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);

        // Insert additionalCollection HashMap in dataHashMap that will be returned to the user.
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
        // Search for batches using search criteria sent by the user and putting
        //it in the dataHashMap that will be sent to the user.
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , BatchDao.SearchBatch(dcmId, date, batchType, batchStatusTypeId, ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));
        
        
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else if (action.compareTo(AuthCallInterfaceKey.ACTION_UPDATE_BATCH_TO_COMMISSION)==0 ||
             action.compareTo(AuthCallInterfaceKey.ACTION_ADMIN_UPDATE_BATCH_TO_COMMISSION)==0  ||
             action.compareTo(AuthCallInterfaceKey.ACTION_UPDATE_BATCH_TO_COMMISSION_BY_SHEET)==0 

             )
             
    {
     try
      {
        Hashtable additionalCollection = new Hashtable();
        //Keep track of the search criteria by resending the parameters sent by the client.
        //Helpfull to allow back and cancel buttons use.

        String batchId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        BatchDao.updateBatchPhase(batchId,ContractRegistrationInterfaceKey.COMMISSION_PHASE);
         
        String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);        
        String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);        
        String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);        
        String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        
        // Return all DCMs of DISTRIBUTOR level as DCMDto.
        DCMDto dcmDto = DCMDao.getAuthenticationCallDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID,con);
        // Return all batch types as BatchTypeDto.
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);
        // Return all batch status types allowed in authintication call module as BatchStatusTypeDto.
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInAuthintication();         

        // Fill additionalCollection HashMap with the above objects.
        //sending the data that the screen need to initialize it self same as the data sent 
        //in initialize batch search by puting them in the optional collection
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);        
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);

        // Insert additionalCollection HashMap in dataHashMap that will be returned to the user.
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
        // Search for batches using search criteria sent by the user and putting
        //it in the dataHashMap that will be sent to the user.
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , BatchDao.SearchBatch(dcmId, date, batchType, batchStatusTypeId, ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
   
    
    }

    else if (action.compareTo(AuthCallInterfaceKey.ACTION_SHOW_SHEET_DETAILS_FOR_AUTHENTICATION_CALL)==0 )
    {
          Hashtable additionalCollection = new Hashtable();
      
      //Keep track of the search criteria by resending the parameters sent by the client.
      //Helpfull to allow back and cancel buttons use.
      String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);      
      String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);      
      String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);      
      String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);      
      String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);
      String batchId =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);

      String sheetId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);

      //Utility.logger.debug("sheetid = "+ sheetId);
      
      Vector batchDtos = new Vector();
      batchDtos.add(BatchDao.getBatch(batchId, phaseName,con));
      
      Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);
      
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
      additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO, batchDtos);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID ,sheetId);

      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);

      HashMap sheetContracts = new HashMap();
      HashMap sheetsStatistic= new HashMap();

      Hashtable sheetsReImportedTable = new Hashtable();
      
      //Return all authenticated contracts as the default mode for the user page grouped by sheets.
      //VIEW_ALL_AUTH
      //this is the code of the option VIEW_ALL_AUTH
      if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.VIEW_ALL_AUTH) == 0)
      { 
        //getting all the sheets in this batch 
        long stime = System.currentTimeMillis();
        //Utility.logger.debug(" vel 1");
        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId,sheetId);
        //Utility.logger.debug("333 = " + (System.currentTimeMillis() -stime/1000));
        
        for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
        {
        
          SheetModel newSheetModel = newSheetDto.getSheet(i);
          String sheetID = newSheetModel.getSheetId();
          String sheetName = newSheetModel.getSheetName();
          //getting the contracts in this sheet taht was previously rejected in the authentication phase
          long starttime = System.currentTimeMillis();

          if (newSheetModel.isSheetReImportFlag())
          {
            sheetsReImportedTable.put(newSheetModel.getSheetId(), newSheetModel.getSheetId());
            
          }
          //Utility.logger.debug("111");
          Hashtable contractsPrevRejected = ContractDao.getContractsListPrevioslyRejected(sheetID,"REJECTED AUTHINTICATION");
          //Utility.logger.debug(System.currentTimeMillis()- starttime /1000);
          starttime = System.currentTimeMillis();
          //sending the contractes previsoly rejected to getSheetContractsInAuthinticationPhase since it was ammended 
          //by having it to return the contracts in authentication phase plus those who where rejected prevoulsy in this phase 
          ContractDto newContractDto = ContractDao.getSheetContractsInAuthinticationPhase(sheetID,contractsPrevRejected);
          //Utility.logger.debug(System.currentTimeMillis()- starttime /1000);
          starttime = System.currentTimeMillis();

          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.
          SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);
          //Utility.logger.debug(System.currentTimeMillis()- starttime /1000);
          starttime = System.currentTimeMillis();

          //getting the sheet authentication statistic for each sheet and put it in the hasthable sheetsStatistic
          //the sheetauthinticaoitnstatistic is only return as null if no any contract is eligable for authetnication not authenticated
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
            sheetContracts.put(sheetID, newContractDto);
            
          }
        }
      }
      //Return all contracts as the default mode for the administration page grouped by sheets.
      //VIEW_ALL
      else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.VIEW_ALL) == 0)
      {
        //SheetDto newSheetDto = SheetDao.getBatchSheets(batchId,null);
        //Utility.logger.debug(" vel 2");
        long startT = System.currentTimeMillis();
        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId,sheetId);

        //Utility.logger.debug("time to get sheets eligable for authentication = " + (System.currentTimeMillis() - startT));
        
        for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
        {
          SheetModel newSheetModel = newSheetDto.getSheet(i);
          String sheetID = newSheetModel.getSheetId();
          String sheetName = newSheetModel.getSheetName();
          if (newSheetModel.isSheetReImportFlag())
          {
            sheetsReImportedTable.put(newSheetModel.getSheetId(), newSheetModel.getSheetId());
            
          }
          startT = System.currentTimeMillis();
          ContractDto newContractDto = ContractDao.getSheetContractsEligableForAuthintication(sheetID);
          //Utility.logger.debug("time to get contracts eligable for authentication = " + (System.currentTimeMillis() - startT));
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.
          startT= System.currentTimeMillis();
          
          SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);
          //Utility.logger.debug("time to get sheetAuthinticationStatistic  = " + (System.currentTimeMillis() - startT));
          
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
            sheetContracts.put(sheetID, newContractDto);
            
          }
        }
      }
    else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.ADD_RANDOM_CONTRACTS) == 0)
      {
        //Check if the parameter is a string or an array of strings and extract data from it.
        long startT = System.currentTimeMillis();
        
        String[] alreadyChosenContracts = new String[0];
        Object o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        if (o!=null && (!(o instanceof String )))
        {
          alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }
        else if (o!=null && (o instanceof String))
        {
          alreadyChosenContracts = new String[1];
          alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }
        //Return a hashmap of all contracts to be displayed.
        // contracts are choosen randomly butting in consideration the 
        //already displayed copntracts.

         //Utility.logger.debug("time before gen random =  "+ (System.currentTimeMillis() - startT));
        startT = System.currentTimeMillis();
        
        sheetContracts = BatchDao.generateBatchRandomSample(batchId , alreadyChosenContracts,sheetId);

        //Utility.logger.debug("time to gen random =  "+ (System.currentTimeMillis() - startT));
        
        Object[] sheetsId = sheetContracts.keySet().toArray();
        for (int i = 0 ;  i < sheetsId.length;i++)
        {        
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.
          startT =System.currentTimeMillis();
          
          SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel((String)sheetsId[i]);

          if (SheetDao.isSheetReImported(  sheetAuthinticationStatistic.getSheetSerial() ,  sheetAuthinticationStatistic.getSheetId()     ) )
          {
            sheetsReImportedTable.put(sheetAuthinticationStatistic.getSheetId() ,  sheetAuthinticationStatistic.getSheetId());
          }
          
          //Utility.logger.debug("time to get sheet statistic =  "+ (System.currentTimeMillis() - startT));
          
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put((String)sheetsId[i],sheetAuthinticationStatistic );
            
          }
        }              
      }
      // Choose a contract using its main sim number. Append the choosen contract to 
      // the already displayed contracts butting in consideration the already displayed contracts.
      else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.ADD_CONTRACT) == 0)
      {
        String contractMainSimNoToAdd = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);

        //Check if the parameter is a string or an array of strings and extract data from it.
        String[] alreadyChosenContracts = new String[0];
        Object o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        if (o!=null && (!(o instanceof String )))
        {
          alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }
        else if (o!=null && (o instanceof String))
        {
          alreadyChosenContracts = new String[1];
          alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }        

        
        
        //Utility.logger.debug( "contractMainSimNoToAdd  = "+ contractMainSimNoToAdd);
        long stime = System.currentTimeMillis();        

        sheetContracts = BatchDao.addContractToDisplayedContractsInAuthintication(batchId,alreadyChosenContracts,contractMainSimNoToAdd,sheetId);
        

        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId,sheetId);


        stime = System.currentTimeMillis();

        for (int i = 0 ;  i < newSheetDto.getSheetModelsSize();i++)
        {
          SheetModel newSheetModel = newSheetDto.getSheet(i);
          String sheetID = newSheetModel.getSheetId();
          String sheetName = newSheetModel.getSheetName();


            if (newSheetModel.isSheetReImportFlag())
          {
            sheetsReImportedTable.put(newSheetModel.getSheetId(), newSheetModel.getSheetId());
            
          }

          
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.
           SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);
           
           //Utility.logger.debug("C" + (System.currentTimeMillis() - stime) /1000);
        stime = System.currentTimeMillis();
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
            if (!sheetContracts.containsKey(sheetID))
            {
              sheetContracts.put(sheetID,new ContractDto());
              
            }
          }
        }        
      }

      
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetContracts);

      dataHashMap.put(ContractRegistrationInterfaceKey.SHEET_REIMPORTED_TABLE  ,sheetsReImportedTable);
      
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE,
                               ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));
      // Send statistics about the number of contracts, authenticated contracts 
      // and remaining contracts in each sheet.
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL, sheetsStatistic);
    }

    else if (action.compareTo(AuthCallInterfaceKey.ACTION_SHOW_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_SCREEN_BY_SHEET)==0 )
    {
    long sTime =System.currentTimeMillis();
    //hagry
      Hashtable additionalCollection = new Hashtable();
      
      //Keep track of the search criteria by resending the parameters sent by the client.
      //Helpfull to allow back and cancel buttons use.
      String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);      
      String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);      
      String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);      
      String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);      
      String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);
      String batchId =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);

      Vector batchDtos = new Vector();
      batchDtos.add(BatchDao.getBatch(batchId, phaseName,con));
System.out.println("^^^^ 1");
      //Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);
      
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
      additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO, batchDtos);
    additionalCollection.put("batch_auth_statistics",BatchDao.getBatchAuthStatistics(batchId));

    
    System.out.println("^^^^ 2");
//      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
      
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);

      HashMap sheetContracts = new HashMap();
      HashMap sheetsStatistic= new HashMap();

  Hashtable sheetsReImportedTable = new Hashtable();

        if (paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS) ==null)
        {
          paramHashMap.put(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS , AuthCallInterfaceKey.VIEW_ALL_AUTH );
        }
      
      //Return all authenticated contracts as the default mode for the user page grouped by sheets.
      //VIEW_ALL_AUTH
      //this is the code of the option VIEW_ALL_AUTH
        
        System.out.println("^^^^ 3");
      if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.VIEW_ALL_AUTH) == 0)
      { 
      
      
          try{
              
          
           //Utility.logger.debug(" vel 3");        
           SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthinticationOPT(batchId,con);

           //Utility.logger.debug("get sheet dto time  = " + (System.currentTimeMillis()- startT ));
           
           for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
           {          
             SheetModel newSheetModel = newSheetDto.getSheet(i);
             String sheetID = newSheetModel.getSheetId();
             String sheetName = newSheetModel.getSheetName();
             
             ContractDto newContractDto = ContractDao.getSheetContractsInAuthinticationPhaseOPT(sheetID,sheetName,con);

             
             SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID,con);
                       
             if (sheetAuthinticationStatistic!=null)
             {
               sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
               sheetContracts.put(sheetID, newContractDto); 
               
             }          
           }
                 
             }
             catch(Exception e) {
                 e.printStackTrace();
             }
             
                }
      
     dataHashMap.put(ContractRegistrationInterfaceKey.SHEET_REIMPORTED_TABLE, sheetsReImportedTable);

     System.out.println("^^^^ 4");
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE,
                               ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));
      // Send statistics about the number of contracts, authenticated contracts 
      // and remaining contracts in each sheet.
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL, sheetsStatistic);
    
    System.out.println("show_choose_contracts_for_authentication_call_screen_by_sheet took ="+(System.currentTimeMillis()-sTime));  
    }
    
    
    /*
     * c. initializngthe screen of chosing contracts for authentiaction
     * in the admin page the following get executed 
     * * VIEW_ALL
     * as for the normal page the default option that get executed is 
     * * VIEW_ALL_AUTH     
     */
     else if(action.compareTo(AuthCallInterfaceKey.ACTION_ADMIN_SHOW_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_SCREEN_BATCH_PERCENTAGE)==0) {
         Hashtable additionalCollection = new Hashtable();
         
         System.out.println("this is the choose contract auth percentage");
         //Keep track of the search criteria by resending the parameters sent by the client.
         //Helpfull to allow back and cancel buttons use.
         String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);      
         String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);      
         String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);      
         String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);      
         String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);
         String batchId =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);

         Vector batchDtos = new Vector();
         batchDtos.add(BatchDao.getBatch(batchId, phaseName,con));
         
         
         
         Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);
         
         additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
         additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
         additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
         additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
         additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO, batchDtos);
         
         additionalCollection.put("batch_auth_statistics",BatchDao.getBatchAuthStatistics(batchId));

         dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);



         HashMap sheetContracts = new HashMap();
         HashMap sheetsStatistic= new HashMap();
         Hashtable sheetsReImportedTable = new Hashtable();
         
         //Return all authenticated contracts as the default mode for the user page grouped by sheets.
         //VIEW_ALL_AUTH
         //this is the code of the option VIEW_ALL_AUTH
         System.out.println("the hashmap value of param = "+ ((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)));
        
        
         if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.VIEW_ALL_AUTH) == 0)
         { 
           long stime = System.currentTimeMillis();
           SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId);
           
           for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
           {
           long s2Time=System.currentTimeMillis();
             SheetModel newSheetModel = newSheetDto.getSheet(i);

             if (newSheetModel.isSheetReImportFlag())
             {
               sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
               
             }
             String sheetID = newSheetModel.getSheetId();
             String sheetName = newSheetModel.getSheetName();

System.out.println("part 1 took "+ ((System.currentTimeMillis()-s2Time)/1000));
s2Time=System.currentTimeMillis();
             //getting the contracts in this sheet taht was previously rejected in the authentication phase
             long starttime = System.currentTimeMillis();
             Hashtable contractsPrevRejected = ContractDao.getContractsListPrevioslyRejected(sheetID,"REJECTED AUTHINTICATION");
             
             

               System.out.println("part 2 took "+ ((System.currentTimeMillis()-s2Time)/1000));
               s2Time=System.currentTimeMillis();
               
             //sending the contractes previsoly rejected to getSheetContractsInAuthinticationPhase since it was ammended 
             //by having it to return the contracts in authentication phase plus those who where rejected prevoulsy in this phase 
            // ContractDto newContractDto = ContractDao.getSheetContractsInAuthinticationPhase(sheetID,contractsPrevRejected);
             ContractDto newContractDto = ContractDao.getSheetContractsInAuthinticationPhase(sheetID,contractsPrevRejected);
             

               System.out.println("part 3 took "+ ((System.currentTimeMillis()-s2Time)/1000));
               s2Time=System.currentTimeMillis();
             // Get sheet statistics as SheetAuthinticationStatisticModel.
             // Statistics about the number of contracts, authenticated contracts 
             // and remaining contracts a sheet.
             SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);
             if (sheetAuthinticationStatistic!=null)
             {
               sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
               sheetContracts.put(sheetID, newContractDto);
             }

               System.out.println("part 4 took "+ ((System.currentTimeMillis()-s2Time)/1000));
               s2Time=System.currentTimeMillis();
           }
         
         System.out.println("took for loading all "+ ((System.currentTimeMillis()-stime)/1000));
         
         }
         
         //Return all contracts as the default mode for the administration page grouped by sheets.
         //VIEW_ALL
         else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.VIEW_ALL) == 0)
         {
             System.out.println("view all part ");
             try{  
             
         
           //  Connection con = Utility.getConnection();
           long startT = System.currentTimeMillis();
           SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthinticationOPT(batchId,con);
            
            long s2Time = System.currentTimeMillis();

       
           for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
           {
             SheetModel newSheetModel = newSheetDto.getSheet(i);
             String sheetID = newSheetModel.getSheetId();
             String sheetName = newSheetModel.getSheetName();

             if (newSheetModel.isSheetReImportFlag())
             {
               sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
               
             }
             startT = System.currentTimeMillis();

             //ContractDto newContractDto = ContractDao.getSheetContractsEligableForAuthintication(sheetID);
              ContractDto newContractDto = ContractDao.getSheetContractsInAuthinticationPhaseOPT(sheetID,sheetName,con);
              //System.out.println("part 1.a ="+(System.currentTimeMillis()-startT));         
             // startT = System.currentTimeMillis(); 
               
             //Utility.logger.debug("time to get contracts eligable for authentication = " + (System.currentTimeMillis() - startT));
             // Get sheet statistics as SheetAuthinticationStatisticModel.
             // Statistics about the number of contracts, authenticated contracts 
             // and remaining contracts a sheet.
             
             SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID,con);
             
            // System.out.println("part 1.b ="+(System.currentTimeMillis()-startT));         
            //   startT = System.currentTimeMillis(); 
               
             if (sheetAuthinticationStatistic!=null)
             {
               sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
               sheetContracts.put(sheetID, newContractDto);
             }
           }            
             
             System.out.println("total time needed ="+(System.currentTimeMillis()-s2Time)); 
         }
             catch(Exception e) {
                 e.printStackTrace();
             }
             
         }
            
      
   
         dataHashMap.put(ContractRegistrationInterfaceKey.SHEET_REIMPORTED_TABLE , sheetsReImportedTable);
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetContracts);
         dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE,
                                  ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));
         // Send statistics about the number of contracts, authenticated contracts 
         // and remaining contracts in each sheet.
         dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL, sheetsStatistic);
     }
    else if (action.compareTo(AuthCallInterfaceKey.ACTION_SHOW_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_SCREEN)==0 ||
             action.compareTo(AuthCallInterfaceKey.ACTION_ADMIN_SHOW_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_SCREEN)==0)
    {
      Hashtable additionalCollection = new Hashtable();
      
      //Keep track of the search criteria by resending the parameters sent by the client.
      //Helpfull to allow back and cancel buttons use.
      String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);      
      String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);      
      String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);      
      String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
     
      
      String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);
      String batchId =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);

      Vector batchDtos = new Vector();
      batchDtos.add(BatchDao.getBatch(batchId, phaseName,con));
      
      Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);
      
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
      additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO, batchDtos);

      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);

      HashMap sheetContracts = new HashMap();
      HashMap sheetsStatistic= new HashMap();
      Hashtable sheetsReImportedTable = new Hashtable();
      
      //Return all authenticated contracts as the default mode for the user page grouped by sheets.
      //VIEW_ALL_AUTH
      //this is the code of the option VIEW_ALL_AUTH
      if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.VIEW_ALL_AUTH) == 0)
      { 
        long stime = System.currentTimeMillis();
        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId);
        
        for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
        {
        
          SheetModel newSheetModel = newSheetDto.getSheet(i);

          if (newSheetModel.isSheetReImportFlag())
          {
            sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
            
          }
          String sheetID = newSheetModel.getSheetId();
          String sheetName = newSheetModel.getSheetName();

          //getting the contracts in this sheet taht was previously rejected in the authentication phase
          long starttime = System.currentTimeMillis();
          Hashtable contractsPrevRejected = ContractDao.getContractsListPrevioslyRejected(sheetID,"REJECTED AUTHINTICATION");
          
          //sending the contractes previsoly rejected to getSheetContractsInAuthinticationPhase since it was ammended 
          //by having it to return the contracts in authentication phase plus those who where rejected prevoulsy in this phase 
          ContractDto newContractDto = ContractDao.getSheetContractsInAuthinticationPhase(sheetID,contractsPrevRejected);
          
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.
          SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
            sheetContracts.put(sheetID, newContractDto);
          }
        }
      }
      //Return all contracts as the default mode for the administration page grouped by sheets.
      //VIEW_ALL
      else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.VIEW_ALL) == 0)
      {
        long startT = System.currentTimeMillis();
        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId);

        for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
        {
          SheetModel newSheetModel = newSheetDto.getSheet(i);
          String sheetID = newSheetModel.getSheetId();
          String sheetName = newSheetModel.getSheetName();

          if (newSheetModel.isSheetReImportFlag())
          {
            sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
            
          }
          startT = System.currentTimeMillis();
          ContractDto newContractDto = ContractDao.getSheetContractsEligableForAuthintication(sheetID);
          //Utility.logger.debug("time to get contracts eligable for authentication = " + (System.currentTimeMillis() - startT));
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.
          
          SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);
          
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
            sheetContracts.put(sheetID, newContractDto);
          }
        }
      }

      dataHashMap.put(ContractRegistrationInterfaceKey.SHEET_REIMPORTED_TABLE , sheetsReImportedTable);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetContracts);
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE,
                               ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));
      // Send statistics about the number of contracts, authenticated contracts 
      // and remaining contracts in each sheet.
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL, sheetsStatistic);                               
    }    
    //hagry
     else if (action.compareTo(AuthCallInterfaceKey.ACTION_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_BATCH_PERCENTAGE)==0)
     {
        System.out.println("choose_contracts_for_authentication_call_batch_percentage action was called");
       Hashtable additionalCollection = new Hashtable();

       //Keep track of the search criteria by resending the parameters sent by the client.
       //Helpfull to allow back and cancel buttons use.
       String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
       additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
       String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
       additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
       String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
       additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
       String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
       additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
       String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);
       String batchId =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);

       Vector batchDtos = new Vector();
       batchDtos.add(BatchDao.getBatch(batchId,phaseName,con));
       Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);
             
       additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO, batchDtos);
       dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
       dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);
       HashMap sheetContracts = new HashMap();
       HashMap sheetsStatistic= new HashMap();
       additionalCollection.put("batch_auth_statistics",BatchDao.getBatchAuthStatistics(batchId));
       //Utility.logger.debug("******************");
       
       //Utility.logger.debug(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)));

       Hashtable sheetsReImportedTable = new Hashtable();
       

       //Return all authenticated contracts grouped by sheets.
       if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.VIEW_ALL_AUTH) == 0)
       {      
         long startT = System.currentTimeMillis();
        try{
            
        
         //Utility.logger.debug(" vel 3");        
         SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthinticationOPT(batchId,con);

         //Utility.logger.debug("get sheet dto time  = " + (System.currentTimeMillis()- startT ));
         
         for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
         {          
           SheetModel newSheetModel = newSheetDto.getSheet(i);
           String sheetID = newSheetModel.getSheetId();
           String sheetName = newSheetModel.getSheetName();
     
           
           startT =System.currentTimeMillis();
           
    
        
           ContractDto newContractDto = ContractDao.getSheetContractsInAuthinticationPhaseOPT(sheetID,sheetName,con);

           //Utility.logger.debug("get sheet contracts in auth time  = " + (System.currentTimeMillis()- startT ));
           
           // Get sheet statistics as SheetAuthinticationStatisticModel.
           // Statistics about the number of contracts, authenticated contracts 
           // and remaining contracts a sheet.

           startT = System.currentTimeMillis();
           SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID,con);
       
           
           if (sheetAuthinticationStatistic!=null)
           {
             sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
             sheetContracts.put(sheetID, newContractDto); 
             
           }          
         }
               
           }
           catch(Exception e) {
               e.printStackTrace();
           }
           
           
           System.out.println("get sheet SheetAuthinticationStatisticModel in auth time  = " + (System.currentTimeMillis()- startT ));
         
       }
       //Return all contracts grouped by sheets.
       else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.VIEW_ALL) == 0)
       {
       /*
       //hagry
       //Utility.logger.debug(" vel 4");
       
         SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId);
         for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
         {
           SheetModel newSheetModel = newSheetDto.getSheet(i);

               if (newSheetModel.isSheetReImportFlag())
           {
             sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
             
           }

           
           String sheetID = newSheetModel.getSheetId();
           String sheetName = newSheetModel.getSheetName();

           long startT = System.currentTimeMillis();
           
           ContractDto newContractDto = ContractDao.getSheetContractsEligableForAuthintication(sheetID);

             //Utility.logger.debug("get sheet getSheetContractsEligableForAuthintication in auth time  = " + (System.currentTimeMillis()- startT ));
         
           // Get sheet statistics as SheetAuthinticationStatisticModel.
           // Statistics about the number of contracts, authenticated contracts 
           // and remaining contracts a sheet.

           startT = System.currentTimeMillis();
           
           SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);

           //Utility.logger.debug("get sheet sheetAuthinticationStatistic in auth time  = " + (System.currentTimeMillis()- startT ));
             
           if (sheetAuthinticationStatistic!=null)
           {
             sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
             sheetContracts.put(sheetID, newContractDto);
             
           }
         }
         */
         
        long startT = System.currentTimeMillis();
        try{
           
        
        //Utility.logger.debug(" vel 3");        
        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthinticationOPT(batchId,con);

        //Utility.logger.debug("get sheet dto time  = " + (System.currentTimeMillis()- startT ));
        
        for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
        {          
          SheetModel newSheetModel = newSheetDto.getSheet(i);
          String sheetID = newSheetModel.getSheetId();
          String sheetName = newSheetModel.getSheetName();
        
        /*
        if (newSheetModel.isSheetReImportFlag())
          {
            sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
            
          }
          
          */
          
          startT =System.currentTimeMillis();
          
          //Hashtable contractsPrevRejected = ContractDao.getContractsListPrevioslyRejected(sheetID,"REJECTED AUTHINTICATION");

          //Utility.logger.debug("get contracts prev rejec dto time  = " + (System.currentTimeMillis()- startT ));

          startT =System.currentTimeMillis();
          ContractDto newContractDto = ContractDao.getSheetContractsVerifiabileInAuthinticationPhaseOPT(sheetID,sheetName,con);

          //Utility.logger.debug("get sheet contracts in auth time  = " + (System.currentTimeMillis()- startT ));
          
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.

          startT = System.currentTimeMillis();
          SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID,con);
        
          
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
            sheetContracts.put(sheetID, newContractDto); 
            
          }          
        }
              
          }
          catch(Exception e) {
              e.printStackTrace();
          }
          
          
          System.out.println("get sheet SheetAuthinticationStatisticModel in auth time  = " + (System.currentTimeMillis()- startT ));
        
       }
       // Choose random contracts from each sheet. Append the choosen contracts to 
       // the already displayed contracts butting in consideration the already displayed contracts.
       else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.ADD_RANDOM_CONTRACTS) == 0)
       {
           long startT = System.currentTimeMillis();
       try{
         //Check if the parameter is a string or an array of strings and extract data from it.
         
       ///  Connection con =Utility.getConnection();
         String[] alreadyChosenContracts = new String[0];
         Object o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
         if (o!=null && (!(o instanceof String )))
         {
           alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
         }
         else if (o!=null && (o instanceof String))
         {
           alreadyChosenContracts = new String[1];
           alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
         }
         //Return a hashmap of all contracts to be displayed.
         // contracts are choosen randomly butting in consideration the 
         //already displayed copntracts.

          //Utility.logger.debug("time before gen random =  "+ (System.currentTimeMillis() - startT));
        // startT = System.currentTimeMillis();
         System.out.println("first part took "+ (System.currentTimeMillis()-startT));
         
         sheetContracts = BatchDao.generateBatchRandomSample(batchId , alreadyChosenContracts);

            
         //Utility.logger.debug("time to gen random =  "+ (System.currentTimeMillis() - startT));
         
         //Object[] sheetsId = sheetContracts.keySet().toArray();
         
        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthinticationOPT(batchId,con);
           
         for (int i = 0 ;  i < newSheetDto.getSheetModelsSize();i++)
         {        
           // Get sheet statistics as SheetAuthinticationStatisticModel.
           // Statistics about the number of contracts, authenticated contracts 
           // and remaining contracts a sheet.
           //startT =System.currentTimeMillis();
           SheetModel sheetModel = newSheetDto.getSheet(i);
           
           SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetModel.getSheetId(),con);


           if (SheetDao.isSheetReImported(  sheetAuthinticationStatistic.getSheetSerial() ,  sheetAuthinticationStatistic.getSheetId()     ) )
           {
             sheetsReImportedTable.put(sheetAuthinticationStatistic.getSheetId() ,  sheetAuthinticationStatistic.getSheetId());
           }
           
           //Utility.logger.debug("time to get sheet statistic =  "+ (System.currentTimeMillis() - startT));
           
           if (sheetAuthinticationStatistic!=null)
           {
             sheetsStatistic.put(sheetModel.getSheetId(),sheetAuthinticationStatistic );
             
           }
           
         }
           
       }
       catch(Exception e) {
           e.printStackTrace();
       }
          System.out.println("adding random contract took "+(System.currentTimeMillis()- startT));
         
       }
       // Choose a contract using its main sim number. Append the choosen contract to 
       // the already displayed contracts butting in consideration the already displayed contracts.
       else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.ADD_CONTRACT) == 0)
       {
         String contractMainSimNoToAdd = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);

         //Check if the parameter is a string or an array of strings and extract data from it.
         String[] alreadyChosenContracts = new String[0];
         Object o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
         if (o!=null && (!(o instanceof String )))
         {
           alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
         }
         else if (o!=null && (o instanceof String))
         {
           alreadyChosenContracts = new String[1];
           alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
         }        

         
         
         //Utility.logger.debug( "contractMainSimNoToAdd  = "+ contractMainSimNoToAdd);
         long stime = System.currentTimeMillis();        
         sheetContracts = BatchDao.addContractToDisplayedContractsInAuthintication(batchId,alreadyChosenContracts,contractMainSimNoToAdd);
         
         //Utility.logger.debug("A" + (System.currentTimeMillis() - stime));
         stime = System.currentTimeMillis();
         //Utility.logger.debug("vel 5");
         SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId);

         //Utility.logger.debug("B" + (System.currentTimeMillis() - stime) /1000);
         stime = System.currentTimeMillis();

         for (int i = 0 ;  i < newSheetDto.getSheetModelsSize();i++)
         {
           SheetModel newSheetModel = newSheetDto.getSheet(i);
           String sheetID = newSheetModel.getSheetId();
           String sheetName = newSheetModel.getSheetName();
               if (newSheetModel.isSheetReImportFlag())
           {
             sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
             
           }
           // Get sheet statistics as SheetAuthinticationStatisticModel.
           // Statistics about the number of contracts, authenticated contracts 
           // and remaining contracts a sheet.
            SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);
            
            //Utility.logger.debug("C" + (System.currentTimeMillis() - stime) /1000);
         stime = System.currentTimeMillis();
           if (sheetAuthinticationStatistic!=null)
           {
             sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
             if (!sheetContracts.containsKey(sheetID))
             {
               sheetContracts.put(sheetID,new ContractDto());
               
             }
           }
         }        
       }
       // Choose contracts in a sheet using its serial number. Append the choosen contracts to 
       // the already displayed contracts butting in consideration the already displayed contracts.
       else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.ADD_SHEET) == 0)
       {
         String sheetToAdd = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);        
         String[] alreadyChosenContracts = new String[0];
         // Check if the paramter is an array or a string and extracting data from it.
         Object o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
         if (o!=null && (!(o instanceof String )))
         {
           alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
         }
         else if (o!=null && (o instanceof String))
         {
           alreadyChosenContracts = new String[1];
           alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
         }
         
         sheetContracts = BatchDao.addSheetToDisplayedContractsInAuthintication(batchId,alreadyChosenContracts,sheetToAdd);
         //Utility.logger.debug("vel 6");
         SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId);
         
         for (int i = 0 ;  i < newSheetDto.getSheetModelsSize();i++)
         {
           SheetModel newSheetModel = newSheetDto.getSheet(i);
           String sheetID = newSheetModel.getSheetId();
           String sheetName = newSheetModel.getSheetName();
                  if (newSheetModel.isSheetReImportFlag())
           {
             sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
             
           }
           // Get sheet statistics as SheetAuthinticationStatisticModel.
           // Statistics about the number of contracts, authenticated contracts 
           // and remaining contracts a sheet.
            SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);
           if (sheetAuthinticationStatistic!=null)
           {
             sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
             if (!sheetContracts.containsKey(sheetID))
             {
               sheetContracts.put(sheetID,new ContractDto());
               
             }
           }
         }        
       }
       ////Utility.logger.debug(" putting sheet id orderd");
       
       dataHashMap.put( ContractRegistrationInterfaceKey.SHEET_REIMPORTED_TABLE ,sheetsReImportedTable);
       
       dataHashMap.put(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS, paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS));
       dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetContracts);
       dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE,
                                ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));

       // Send statistics about the number of contracts, authenticated contracts 
       // and remaining contracts in each sheet.
       dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL, sheetsStatistic);                               
       
     }    
    else if (action.compareTo(AuthCallInterfaceKey.ACTION_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL)==0 ||
             action.compareTo(AuthCallInterfaceKey.ACTION_ADMIN_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL)==0)
    {
      Hashtable additionalCollection = new Hashtable();

      //Keep track of the search criteria by resending the parameters sent by the client.
      //Helpfull to allow back and cancel buttons use.
      String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
      String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
      String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
      String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
      String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);
      String batchId =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);

      Vector batchDtos = new Vector();
      batchDtos.add(BatchDao.getBatch(batchId,phaseName,con));
      Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);
            
      additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO, batchDtos);
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);
      HashMap sheetContracts = new HashMap();
      HashMap sheetsStatistic= new HashMap();

      //Utility.logger.debug("******************");
      
      //Utility.logger.debug(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)));

      Hashtable sheetsReImportedTable = new Hashtable();
      

      //Return all authenticated contracts grouped by sheets.
      if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.VIEW_ALL_AUTH) == 0)
      {      
        long startT = System.currentTimeMillis();

        //Utility.logger.debug(" vel 3");        
        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId);

        //Utility.logger.debug("get sheet dto time  = " + (System.currentTimeMillis()- startT ));
        
        for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
        {          
          SheetModel newSheetModel = newSheetDto.getSheet(i);
          String sheetID = newSheetModel.getSheetId();
          String sheetName = newSheetModel.getSheetName();
    if (newSheetModel.isSheetReImportFlag())
          {
            sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
            
          }
          startT =System.currentTimeMillis();
          
          Hashtable contractsPrevRejected = ContractDao.getContractsListPrevioslyRejected(sheetID,"REJECTED AUTHINTICATION");

          //Utility.logger.debug("get contracts prev rejec dto time  = " + (System.currentTimeMillis()- startT ));

          startT =System.currentTimeMillis();
          ContractDto newContractDto = ContractDao.getSheetContractsInAuthinticationPhase(sheetID,contractsPrevRejected);

          //Utility.logger.debug("get sheet contracts in auth time  = " + (System.currentTimeMillis()- startT ));
          
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.

          startT = System.currentTimeMillis();
          SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);
      //Utility.logger.debug("get sheet SheetAuthinticationStatisticModel in auth time  = " + (System.currentTimeMillis()- startT ));
          
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
            sheetContracts.put(sheetID, newContractDto); 
            
          }          
        }
      }
      //Return all contracts grouped by sheets.
      else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.VIEW_ALL) == 0)
      {
      //hagry
      //Utility.logger.debug(" vel 4");
        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId);
        for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
        {
          SheetModel newSheetModel = newSheetDto.getSheet(i);

              if (newSheetModel.isSheetReImportFlag())
          {
            sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
            
          }

          
          String sheetID = newSheetModel.getSheetId();
          String sheetName = newSheetModel.getSheetName();

          long startT = System.currentTimeMillis();
          
          ContractDto newContractDto = ContractDao.getSheetContractsEligableForAuthintication(sheetID);

            //Utility.logger.debug("get sheet getSheetContractsEligableForAuthintication in auth time  = " + (System.currentTimeMillis()- startT ));
        
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.

          startT = System.currentTimeMillis();
          
          SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);

          //Utility.logger.debug("get sheet sheetAuthinticationStatistic in auth time  = " + (System.currentTimeMillis()- startT ));
            
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
            sheetContracts.put(sheetID, newContractDto);
            
          }
        }
      }
      // Choose random contracts from each sheet. Append the choosen contracts to 
      // the already displayed contracts butting in consideration the already displayed contracts.
      else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.ADD_RANDOM_CONTRACTS) == 0)
      {
        //Check if the parameter is a string or an array of strings and extract data from it.
        long startT = System.currentTimeMillis();
        
        String[] alreadyChosenContracts = new String[0];
        Object o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        if (o!=null && (!(o instanceof String )))
        {
          alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }
        else if (o!=null && (o instanceof String))
        {
          alreadyChosenContracts = new String[1];
          alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }
        //Return a hashmap of all contracts to be displayed.
        // contracts are choosen randomly butting in consideration the 
        //already displayed copntracts.

         //Utility.logger.debug("time before gen random =  "+ (System.currentTimeMillis() - startT));
        startT = System.currentTimeMillis();
        
        sheetContracts = BatchDao.generateBatchRandomSample(batchId , alreadyChosenContracts);

        //Utility.logger.debug("time to gen random =  "+ (System.currentTimeMillis() - startT));
        
        Object[] sheetsId = sheetContracts.keySet().toArray();
        for (int i = 0 ;  i < sheetsId.length;i++)
        {        
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.
          startT =System.currentTimeMillis();
          
          SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel((String)sheetsId[i]);

          if (SheetDao.isSheetReImported(  sheetAuthinticationStatistic.getSheetSerial() ,  sheetAuthinticationStatistic.getSheetId()     ) )
          {
            sheetsReImportedTable.put(sheetAuthinticationStatistic.getSheetId() ,  sheetAuthinticationStatistic.getSheetId());
          }
          
          //Utility.logger.debug("time to get sheet statistic =  "+ (System.currentTimeMillis() - startT));
          
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put((String)sheetsId[i],sheetAuthinticationStatistic );
            
          }
        }              
      }
      // Choose a contract using its main sim number. Append the choosen contract to 
      // the already displayed contracts butting in consideration the already displayed contracts.
      else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.ADD_CONTRACT) == 0)
      {
        String contractMainSimNoToAdd = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);

        //Check if the parameter is a string or an array of strings and extract data from it.
        String[] alreadyChosenContracts = new String[0];
        Object o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        if (o!=null && (!(o instanceof String )))
        {
          alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }
        else if (o!=null && (o instanceof String))
        {
          alreadyChosenContracts = new String[1];
          alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }        

        
        
        //Utility.logger.debug( "contractMainSimNoToAdd  = "+ contractMainSimNoToAdd);
        long stime = System.currentTimeMillis();        
        sheetContracts = BatchDao.addContractToDisplayedContractsInAuthintication(batchId,alreadyChosenContracts,contractMainSimNoToAdd);
        
        //Utility.logger.debug("A" + (System.currentTimeMillis() - stime));
        stime = System.currentTimeMillis();
        //Utility.logger.debug("vel 5");
        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId);

        //Utility.logger.debug("B" + (System.currentTimeMillis() - stime) /1000);
        stime = System.currentTimeMillis();

        for (int i = 0 ;  i < newSheetDto.getSheetModelsSize();i++)
        {
          SheetModel newSheetModel = newSheetDto.getSheet(i);
          String sheetID = newSheetModel.getSheetId();
          String sheetName = newSheetModel.getSheetName();
              if (newSheetModel.isSheetReImportFlag())
          {
            sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
            
          }
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.
           SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);
           
           //Utility.logger.debug("C" + (System.currentTimeMillis() - stime) /1000);
        stime = System.currentTimeMillis();
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
            if (!sheetContracts.containsKey(sheetID))
            {
              sheetContracts.put(sheetID,new ContractDto());
              
            }
          }
        }        
      }
      // Choose contracts in a sheet using its serial number. Append the choosen contracts to 
      // the already displayed contracts butting in consideration the already displayed contracts.
      else if(((String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)).compareTo(AuthCallInterfaceKey.ADD_SHEET) == 0)
      {
        String sheetToAdd = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);        
        String[] alreadyChosenContracts = new String[0];
        // Check if the paramter is an array or a string and extracting data from it.
        Object o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        if (o!=null && (!(o instanceof String )))
        {
          alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }
        else if (o!=null && (o instanceof String))
        {
          alreadyChosenContracts = new String[1];
          alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }
        
        sheetContracts = BatchDao.addSheetToDisplayedContractsInAuthintication(batchId,alreadyChosenContracts,sheetToAdd);
        //Utility.logger.debug("vel 6");
        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchId);
        
        for (int i = 0 ;  i < newSheetDto.getSheetModelsSize();i++)
        {
          SheetModel newSheetModel = newSheetDto.getSheet(i);
          String sheetID = newSheetModel.getSheetId();
          String sheetName = newSheetModel.getSheetName();
                 if (newSheetModel.isSheetReImportFlag())
          {
            sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
            
          }
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.
           SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheetID);
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put(sheetID,sheetAuthinticationStatistic );
            if (!sheetContracts.containsKey(sheetID))
            {
              sheetContracts.put(sheetID,new ContractDto());
              
            }
          }
        }        
      }
      ////Utility.logger.debug(" putting sheet id orderd");
      
      dataHashMap.put( ContractRegistrationInterfaceKey.SHEET_REIMPORTED_TABLE ,sheetsReImportedTable);
      
      dataHashMap.put(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS, paramHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS));
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetContracts);
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE,
                               ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));

      // Send statistics about the number of contracts, authenticated contracts 
      // and remaining contracts in each sheet.
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL, sheetsStatistic);                               
      
    }    
    else if(action.compareTo(AuthCallInterfaceKey.ACTION_SHOW_CONTRACT_DETAILS_FOR_AUTHENTICATION_CALL)==0 ||
            action.compareTo(AuthCallInterfaceKey.ACTION_ADMIN_SHOW_CONTRACT_DETAILS_FOR_AUTHENTICATION_CALL)==0 ||
            action.compareTo(AuthCallInterfaceKey.ACTION_SHOW_CONTRACT_DETAILS_FOR_AUTHENTICATION_CALL_BY_SHEET)==0
        ||
                    action.compareTo(AuthCallInterfaceKey.ACTION_SHOW_CONTRACT_DETAILS_FOR_AUTHENTICATION_CALL_BATCH_PERCENTAGE)==0
            )
            
    {
    long stime = System.currentTimeMillis();
      try
      {
        //Keep track of the search criteria by resending the parameters sent by the client.
        //Helpfull to allow back and cancel buttons use.
        String dcmID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID , dcmID);
        String batchID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID , batchID);
        String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
        String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
        String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
        String contractID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID);
        System.out.println("The contract id isssssssssssssssssss " + contractID);
        ContractModel contractModel = ContractDao.getContract(contractID);
        String sheetID = contractModel.getSheetId();
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID , sheetID);
        String sheetName = contractModel.getSheetSerialNo();
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_NAME , sheetName);
        String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);

        

        //Check if the parameter is a string or an array of strings and extract data from it.
        String[] alreadyChosenContracts = new String[0];
        Object o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        if (o!=null && (!(o instanceof String )))
        {
          alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }
        else if (o!=null && (o instanceof String))
        {
          alreadyChosenContracts = new String[1];
          alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }        

        long startT = System.currentTimeMillis();
        
        boolean doesContractHasPreviousWarning = ContractDao.isContractHasWarningInAuthentication(contractModel.getMainSimNum());
        //Utility.logger.debug("doesContractHasPreviousWarning = "+ (System.currentTimeMillis() - startT ));
        
        dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRACT_HAS_PREVIOUS_WARNING , new Boolean(doesContractHasPreviousWarning));

        startT = System.currentTimeMillis(); 
        boolean pOSQuestionFlag = ContractDao.getPOSQuestionFlag(sheetID);
        //Utility.logger.debug("pOSQuestionFlag = "+ (System.currentTimeMillis() - startT ));
        
        dataHashMap.put(AuthCallInterfaceKey.OBJ_POS_QUESTION_FLAG , new Boolean(pOSQuestionFlag));
        
        ContractStatusTypeDto newContractStatusTypeDto = ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con);
        
        Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);

        startT = System.currentTimeMillis();
        
        Vector contractWarningsModelVector = ContractDao.getContractWarnings(contractID);
        //Utility.logger.debug("contractWarningsModelVector = "+ (System.currentTimeMillis() - startT ));

        startT = System.currentTimeMillis() ;
        HashMap additionalCollection = new HashMap();
        additionalCollection.put(AuthCallInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
        additionalCollection.put(AuthCallInterfaceKey.OBJ_CONTRACT_WARNING, contractWarningsModelVector);
        //additionalCollection.put(AuthCallInterfaceKey.OBJ_AUTH_CALL_STATUS, AuthCallDAO.getAuthCallStatuses());
        //additionalCollection.put(AuthCallInterfaceKey.OBJ_AUTH_CALL_OWNER_STATUS, AuthCallDAO.getAuthCallOwnerStatuses());
        //additionalCollection.put(AuthCallInterfaceKey.OBJ_AUTH_CALL_POS_STATUS, AuthCallDAO.getAuthCallPOSStatuses());
        //Utility.logger.debug("putting data  = "+ (System.currentTimeMillis() - startT ));
  
        startT  = System.currentTimeMillis();
        //additionalCollection.put(AuthCallInterfaceKey.OBJ_AUTH_CALL_STATUS_BY_CONTRACT, AuthCallDAO.getAuthCallStatusByContract(contractID));
        //Utility.logger.debug("getAuthCallStatusByContract data  = "+ (System.currentTimeMillis() - startT ));

        additionalCollection.put(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT, alreadyChosenContracts);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);
        dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE, newContractStatusTypeDto);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractModel);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      
      System.out.println("showing contract details took = "+ (System.currentTimeMillis()-stime));
    }
    else if(action.compareTo(AuthCallInterfaceKey.ACTION_ADMIN_UPDATE_CONTRACT_STATUS)==0)
    { 
      try
      {
    //  Connection con = Utility.getConnection();
      Hashtable additionalCollection = new Hashtable();
      //Keep track of the search criteria by resending the parameters sent by the client.
      //Helpfull to allow back and cancel buttons use.
      String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
      String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
      String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
      String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
      String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);
      String batchID =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);

      String sheetID = "";
      String statusContractID = "";
      String warningContractID = "";
      String statusID = "";
      String[] warningID = new String[1];

      Hashtable sheetsGotModifiedTable = new Hashtable();
      for(int j=0; j<paramHashMap.size(); j++)
      {
        String statusSelectControl = (String)paramHashMap.keySet().toArray()[j];
        String tempStatusString = statusSelectControl;
        if(tempStatusString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
        {
          Utility.logger.debug("kkkkkkkkkk"+tempStatusString);
          tempStatusString = tempStatusString.substring(tempStatusString.lastIndexOf(
                                        ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7);
          if(tempStatusString.lastIndexOf("_") > -1)
          {
            sheetID = tempStatusString.substring(0, tempStatusString.lastIndexOf("_"));
            statusContractID = tempStatusString.substring(tempStatusString.lastIndexOf("_")+1);
           
            try 
            {
              Integer.parseInt(statusContractID);
              statusID = (String)paramHashMap.get(statusSelectControl);
           
              
              if(statusID.compareTo("") != 0)
              {
             
                for(int k=0; k<paramHashMap.size(); k++)
                {
                  
                  String warningSelectControl = (String)paramHashMap.keySet().toArray()[k];
                  String tempWarningString = warningSelectControl;
                  if(tempWarningString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS))
                  {
                    tempWarningString = tempWarningString.substring(tempWarningString.lastIndexOf(
                                                  ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS)+14);
                    if(tempWarningString.lastIndexOf("_") > -1)
                    {
                      try 
                      {
                        Utility.logger.debug("wwwwwwwwww"+tempWarningString);
                        sheetID = tempWarningString.substring(0, tempWarningString.lastIndexOf("_"));
                        warningContractID = tempWarningString.substring(tempWarningString.lastIndexOf("_")+1);
                        Integer.parseInt(warningContractID);
                        warningID[0] = (String)paramHashMap.get(warningSelectControl);
                        if(warningContractID.compareTo(statusContractID) == 0)
                        {                                                    
                            Vector contractSystemWarningModelVector = ContractDao.validateContractInContractVerification(con,warningContractID,"0","");

                            String contractLastStatusTypeId  =
                            (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT_OLD_STATUS +sheetID+"_"+warningContractID);
                           
                            
                            
                            if (statusID.compareTo(contractLastStatusTypeId)==0)
                            {
                              ////Utility.logger.debug("same status");
                            }
                            else
                            {
                              ////Utility.logger.debug("updating the status");
           
                              ContractDao.updateContractRecord(warningContractID, strUserID, sheetID, batchID, statusID, warningID, contractSystemWarningModelVector,con);
                              sheetsGotModifiedTable.put(sheetID, sheetID);
                            }
                            
                            
                        }
                        
                      } 
                      catch (Exception ex) 
                      {
                        continue;
                      }
                    }
                  }
                }
              }
            } 
            catch (Exception ex) 
            {
              continue;
            }
        }        
      }    
    }

      ////Utility.logger.debug("number of sheets to be updated = " + sheetsGotModifiedTable.size());
      
      Enumeration sheetsGotModifiedIDs = sheetsGotModifiedTable.keys();
      while (sheetsGotModifiedIDs.hasMoreElements())
      {
        SheetDao.updateSheetStatusRecord((String)sheetsGotModifiedIDs.nextElement() ,batchID,strUserID);  
      }
      
      BatchDao.updateBatchStatus(batchID,con);
      
      Vector batchDtos = new Vector();
      batchDtos.add(BatchDao.getBatch(batchID, phaseName,con));
      Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);
            
      additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO, batchDtos);

      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);

      

      HashMap sheetContracts = new HashMap();
      HashMap sheetsStatistic= new HashMap();
      String contractMainSimNoToAdd = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);

      //Check if the parameter is a string or an array of strings and extract data from it.
      String[] alreadyChosenContracts = new String[0];
      Object o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
      if (o!=null && (!(o instanceof String )))
      {
        alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
      }
      else if (o!=null && (o instanceof String))
      {
        alreadyChosenContracts = new String[1];
        alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
      }
      
      sheetContracts = BatchDao.addContractToDisplayedContractsInAuthintication(batchID,alreadyChosenContracts,contractMainSimNoToAdd,sheetID);        

      Hashtable sheetsReImportedTable = new Hashtable();
      SheetModel newSheetModel = SheetDao.getSheetById(sheetID);
      if (newSheetModel.isSheetReImportFlag())
      {
        sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
      }
      dataHashMap.put(ContractRegistrationInterfaceKey.SHEET_REIMPORTED_TABLE , sheetsReImportedTable);
      
      Object[] sheetsId = sheetContracts.keySet().toArray();
      for (int i = 0 ;  i < sheetsId.length;i++)
      {
        // Get sheet statistics as SheetAuthinticationStatisticModel.
        // Statistics about the number of contracts, authenticated contracts 
        // and remaining contracts a sheet.
        SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel((String)sheetsId[i]);
        if (sheetAuthinticationStatistic!=null)
        {
          sheetsStatistic.put((String)sheetsId[i],sheetAuthinticationStatistic );
        }
      }        
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetContracts);
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE,
                               ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));

      // Send statistics about the number of contracts, authenticated contracts 
      // and remaining contracts in each sheet.
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL, sheetsStatistic);                               
      dataHashMap.put(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS, AuthCallInterfaceKey.ADD_CONTRACT);
      
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }

    else if (action.compareTo(AuthCallInterfaceKey.ACTION_UPDATE_CONTRACT_AUTH_BY_SHEET)==0)
    {
    
        Hashtable additionalCollection = new Hashtable();
      long startTime = System.currentTimeMillis() ;

      //Keep track of the search criteria by resending the parameters sent by the client.
      //Helpfull to allow back and cancel buttons use.
      String contractID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID);
      String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
      String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
      String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
      String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
      String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);
      String batchID =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
      String sheetID =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
      String authCallStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_STATUS);
      String contractStatusTypeID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);

      

      //Check if the parameter is a string or an array of strings and extract data from it.
      String[] applied_user_warnings = new String[0];
      Object o = paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
      if (o!=null && (!(o instanceof String )))
      {
        applied_user_warnings = (String[])paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
      }
      else if (o!=null && (o instanceof String))
      {
        applied_user_warnings = new String[1];
        applied_user_warnings[0] = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
      }
      Vector contractSystemWarningModelVector = new Vector();


       
      startTime = System.currentTimeMillis();
      String contractStatusID = ContractDao.updateContractRecord(contractID, strUserID, sheetID, batchID, contractStatusTypeID, applied_user_warnings, contractSystemWarningModelVector,con);



      
      
      
      String ownerStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_OWNER_STATUS);
      String posStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_POS_STATUS);

      //hagry
//      String authCallStatusID = AuthCallDAO.getAuthCallStatusID(authCallStatus);
//      String authCallPOSStatusID = AuthCallDAO.getAuthCallPOSStatusID(posStatus);
//      String authCallOwnerStatusID = AuthCallDAO.getAuthCallOwnerStatusID(ownerStatus);
      
      
/*
      if(authCallStatus.compareTo("REACHABLE")==0)
      {
        String authCallID = AuthCallDAO.insertAuthCall(authCallStatusID, strUserID, contractStatusID);
        AuthCallDAO.insertAuthCallParam(authCallOwnerStatusID, authCallPOSStatusID, authCallID);
      }
      else if(authCallStatus.compareTo("UNREACHABLE")==0)
      {
        AuthCallDAO.insertAuthCall(authCallStatusID, strUserID, contractStatusID);
      }
*/

      SheetDao.updateSheetStatusRecord(sheetID ,batchID,strUserID);


      BatchDao.updateBatchStatusInAuth(batchID);



      
      Vector batchDtos = new Vector();
      batchDtos.add(BatchDao.getBatch(batchID, phaseName,con));


      
      Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);

  
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
      additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO, batchDtos);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID,sheetID);

      

      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);



      HashMap sheetContracts = new HashMap();
      HashMap sheetsStatistic= new HashMap();
      String contractMainSimNoToAdd = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);
      String[] alreadyChosenContracts = new String[0];
      o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
      if (o!=null && (!(o instanceof String )))
      {
        alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
      }
      else if (o!=null && (o instanceof String))
      {
        alreadyChosenContracts = new String[1];
        alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
      }        




      sheetContracts = BatchDao.addContractToDisplayedContractsInAuthintication(batchID,alreadyChosenContracts,contractMainSimNoToAdd,sheetID);
      
      

    
      Object[] sheetsId = sheetContracts.keySet().toArray();
      for (int i = 0 ;  i < sheetsId.length;i++)
      {
        // Get sheet statistics as SheetAuthinticationStatisticModel.
        // Statistics about the number of contracts, authenticated contracts 
        // and remaining contracts a sheet.
        SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel((String)sheetsId[i]);
        if (sheetAuthinticationStatistic!=null)
        {
          sheetsStatistic.put((String)sheetsId[i],sheetAuthinticationStatistic );
        }
      }        
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetContracts);
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE,
                               ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));

      // Send statistics about the number of contracts, authenticated contracts 
      // and remaining contracts in each sheet.
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL, sheetsStatistic);                               
      dataHashMap.put(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS, AuthCallInterfaceKey.ADD_CONTRACT);

      //Utility.logger.debug("put sheetid ="+ sheetID);
      dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID ,sheetID);

      
    }
    else if((action.compareTo(AuthCallInterfaceKey.ACTION_UPDATE_CONTRACT_AUTH)==0) ||
            (action.compareTo(AuthCallInterfaceKey.ACTION_ADMIN_UPDATE_CONTRACT_AUTH)==0))
            
    {
      Hashtable additionalCollection = new Hashtable();
      long startTime = System.currentTimeMillis() ;
//hagry1
      //Keep track of the search criteria by resending the parameters sent by the client.
      //Helpfull to allow back and cancel buttons use.
      String contractID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID);
      String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
      String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
      String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
      String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
      String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);

      String batchID =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
      String sheetID =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
      //String authCallStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_STATUS);
      String authCallStatus= "Unreachable";
      String contractStatusTypeID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);

      //Check if the parameter is a string or an array of strings and extract data from it.
      String[] applied_user_warnings = new String[0];
      Object o = paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
      if (o!=null && (!(o instanceof String )))
      {
        applied_user_warnings = (String[])paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
      }
      else if (o!=null && (o instanceof String))
      {
        applied_user_warnings = new String[1];
        applied_user_warnings[0] = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
      }
      Vector contractSystemWarningModelVector = new Vector();

      Utility.logger.debug(" phase 1 time = " + (System.currentTimeMillis() - startTime ));
       
      startTime = System.currentTimeMillis();
      String contractStatusID = ContractDao.updateContractRecord(contractID, strUserID, sheetID, batchID, contractStatusTypeID, applied_user_warnings, contractSystemWarningModelVector,con);

      Utility.logger.debug(" phase 2 time = " + (System.currentTimeMillis() - startTime ));

      
      
      
      String ownerStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_OWNER_STATUS);
      String posStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_POS_STATUS);

      Utility.logger.debug(" phase 2.1 time = " + (System.currentTimeMillis() - startTime ));
      startTime = System.currentTimeMillis();
      
      //String authCallStatusID = AuthCallDAO.getAuthCallStatusID(authCallStatus);
      //String authCallPOSStatusID = AuthCallDAO.getAuthCallPOSStatusID(posStatus);
      //String authCallOwnerStatusID = AuthCallDAO.getAuthCallOwnerStatusID(ownerStatus);
      
      Utility.logger.debug(" phase 2 time = " + (System.currentTimeMillis() - startTime ));
      startTime = System.currentTimeMillis();

      Utility.logger.debug(" phase 3 time = " + (System.currentTimeMillis() - startTime ));
      startTime = System.currentTimeMillis();

      SheetDao.updateSheetStatusRecord(sheetID ,batchID,strUserID);        
      
      BatchDao.updateBatchStatus(batchID,con);
      
      Vector batchDtos = new Vector();
      batchDtos.add(BatchDao.getBatch(batchID, phaseName,con));
      
      Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);

  
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
      additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
      additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO, batchDtos);

      additionalCollection.put("batch_auth_statistics",BatchDao.getBatchAuthStatistics(batchID));

      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);

      HashMap sheetContracts = new HashMap();
      HashMap sheetsStatistic= new HashMap();
      String contractMainSimNoToAdd = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);
      String[] alreadyChosenContracts = new String[0];
      o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
      if (o!=null && (!(o instanceof String )))
      {
        alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
      }
      else if (o!=null && (o instanceof String))
      {
        alreadyChosenContracts = new String[1];
        alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
      }        

      startTime = System.currentTimeMillis();

      //  sheetContracts = BatchDao.addSheetToDisplayedContractsInAuthintication(batchID,alreadyChosenContracts,null);
      sheetContracts = BatchDao.addContractToDisplayedContractsInAuthintication(batchID,alreadyChosenContracts,contractMainSimNoToAdd);
      
        Utility.logger.debug(" geting addContractToDisplayedContractsInAuthintication time = "+ (System.currentTimeMillis()- startTime));
      startTime = System.currentTimeMillis();
        SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchID);
        
      //Object[] sheetsId = sheetContracts.keySet().toArray();
      for (int i = 0 ;  i < newSheetDto.getSheetModelsSize();i++)
      {
        // Get sheet statistics as SheetAuthinticationStatisticModel.
        // Statistics about the number of contracts, authenticated contracts 
        // and remaining contracts a sheet.
        SheetModel sheet = newSheetDto.getSheet(i);
        
        SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheet.getSheetId());
        if (sheetAuthinticationStatistic!=null)
        {
          sheetsStatistic.put((String)sheet.getSheetId(),sheetAuthinticationStatistic);
        }
      }        

      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetContracts);

      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE,
                               ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));

      // Send statistics about the number of contracts, authenticated contracts 
      // and remaining contracts in each sheet.
      dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL, sheetsStatistic);                               
      dataHashMap.put(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS, AuthCallInterfaceKey.ADD_CONTRACT);
      
      Hashtable sheetsReImportedTable = new Hashtable();
    //  SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchID);
              
              for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
              {
        
                SheetModel newSheetModel = newSheetDto.getSheet(i);

                if (newSheetModel.isSheetReImportFlag())
                {
                  sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
            
                }
              }  
      dataHashMap.put(ContractRegistrationInterfaceKey.SHEET_REIMPORTED_TABLE , sheetsReImportedTable);
      Utility.logger.debug(" phase 4 time = " + (System.currentTimeMillis() - startTime ));
    }
      else if (action.compareTo(AuthCallInterfaceKey.ACTION_UPDATE_CONTRACT_AUTH_BATCH_PERCENTAGE)==0)
      {
        Hashtable additionalCollection = new Hashtable();
        long startTime = System.currentTimeMillis() ;
      //hagry1
        //Keep track of the search criteria by resending the parameters sent by the client.
        //Helpfull to allow back and cancel buttons use.
        String contractID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID);
        String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
        String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);

        String batchID =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        String sheetID =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
        //String authCallStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_STATUS);
        String authCallStatus= "Unreachable";
        String contractStatusTypeID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);

        //Check if the parameter is a string or an array of strings and extract data from it.
        String[] applied_user_warnings = new String[0];
        Object o = paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
        if (o!=null && (!(o instanceof String )))
        {
          applied_user_warnings = (String[])paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
        }
        else if (o!=null && (o instanceof String))
        {
          applied_user_warnings = new String[1];
          applied_user_warnings[0] = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
        }
        Vector contractSystemWarningModelVector = new Vector();

        Utility.logger.debug(" phase 1 time = " + (System.currentTimeMillis() - startTime ));
         
        startTime = System.currentTimeMillis();
        String contractStatusID = ContractDao.updateContractRecord(contractID, strUserID, sheetID, batchID, contractStatusTypeID, applied_user_warnings, contractSystemWarningModelVector,con);

        Utility.logger.debug(" phase 2 time = " + (System.currentTimeMillis() - startTime ));

        
        
        
        String ownerStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_OWNER_STATUS);
        String posStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_POS_STATUS);

        Utility.logger.debug(" phase 2.1 time = " + (System.currentTimeMillis() - startTime ));
        startTime = System.currentTimeMillis();
        
        //String authCallStatusID = AuthCallDAO.getAuthCallStatusID(authCallStatus);
        //String authCallPOSStatusID = AuthCallDAO.getAuthCallPOSStatusID(posStatus);
        //String authCallOwnerStatusID = AuthCallDAO.getAuthCallOwnerStatusID(ownerStatus);
        
        Utility.logger.debug(" phase 2 time = " + (System.currentTimeMillis() - startTime ));
        startTime = System.currentTimeMillis();

        Utility.logger.debug(" phase 3 time = " + (System.currentTimeMillis() - startTime ));
        startTime = System.currentTimeMillis();

        SheetDao.updateSheetStatusRecord(sheetID ,batchID,strUserID);        
        
       // BatchDao.updateBatchStatus(batchID);
        BatchDao.updateBatchStatusAuthenticationBatchPercentage(batchID);
        Vector batchDtos = new Vector();
        batchDtos.add(BatchDao.getBatch(batchID, phaseName,con));
        
        Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);

      
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO, batchDtos);

        additionalCollection.put("batch_auth_statistics",BatchDao.getBatchAuthStatistics(batchID));

        dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);

        HashMap sheetContracts = new HashMap();
        HashMap sheetsStatistic= new HashMap();
        String contractMainSimNoToAdd = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);
        String[] alreadyChosenContracts = new String[0];
        o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        if (o!=null && (!(o instanceof String )))
        {
          alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }
        else if (o!=null && (o instanceof String))
        {
          alreadyChosenContracts = new String[1];
          alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }        

        startTime = System.currentTimeMillis();

        //  sheetContracts = BatchDao.addSheetToDisplayedContractsInAuthintication(batchID,alreadyChosenContracts,null);
        sheetContracts = BatchDao.addContractToDisplayedContractsInAuthintication(batchID,alreadyChosenContracts,contractMainSimNoToAdd);
        
          Utility.logger.debug(" geting addContractToDisplayedContractsInAuthintication time = "+ (System.currentTimeMillis()- startTime));
        startTime = System.currentTimeMillis();
          SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchID);
          
        //Object[] sheetsId = sheetContracts.keySet().toArray();
        for (int i = 0 ;  i < newSheetDto.getSheetModelsSize();i++)
        {
          // Get sheet statistics as SheetAuthinticationStatisticModel.
          // Statistics about the number of contracts, authenticated contracts 
          // and remaining contracts a sheet.
          SheetModel sheet = newSheetDto.getSheet(i);
          
          SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheet.getSheetId());
          if (sheetAuthinticationStatistic!=null)
          {
            sheetsStatistic.put((String)sheet.getSheetId(),sheetAuthinticationStatistic);
          }
        }        

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetContracts);

        dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE,
                                 ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));

        // Send statistics about the number of contracts, authenticated contracts 
        // and remaining contracts in each sheet.
        dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL, sheetsStatistic);                               
        dataHashMap.put(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS, AuthCallInterfaceKey.ADD_CONTRACT);
        
        Hashtable sheetsReImportedTable = new Hashtable();
      //  SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchID);
                
                
                for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
                {
          
                  SheetModel newSheetModel = newSheetDto.getSheet(i);
/*
                  if (newSheetModel.isSheetReImportFlag())
                  {
                    sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
              
                  }
                  */
                }  
        dataHashMap.put(ContractRegistrationInterfaceKey.SHEET_REIMPORTED_TABLE , sheetsReImportedTable);
        Utility.logger.debug(" phase 4 time = " + (System.currentTimeMillis() - startTime ));
      }
    else if(action.compareTo(AuthCallInterfaceKey.ACTION_SHOW_USER_DCM_SCREEN_AUTHENTICATION_CALL)==0)
    {
      try 
      {
        Connection m_conSDSConnection = Utility.getConnection();
        Vector  usersVector = UserDAO.getAuthenticationCallUsersList(m_conSDSConnection);
        HashMap additionalDataHashMap = UserDAO.getAdditionalData(m_conSDSConnection);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalDataHashMap);
        Utility.closeConnection(m_conSDSConnection);
      } 
      catch (Exception ex) 
      {
        ex.printStackTrace();
      } 
    }
    else if(action.compareTo(AuthCallInterfaceKey.ACTION_UPDATE_USER_DCM_AUTHENTICATION_CALL)==0)
    {
      try 
      {
        Connection m_conSDSConnection = Utility.getConnection();
        int userID = new Integer((String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID)).intValue();

        CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_DCM_LIST_AUTHENTICATION_CALL+userID);
        
        if(UserDAO.deleteAuthenticationCallUserDCMs(m_conSDSConnection, userID))
        {
          for(int j=0; j<paramHashMap.size(); j++)
          {
            String tempString = (String)paramHashMap.keySet().toArray()[j];
            if(tempString.startsWith(UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX))
            {
              int nDCMID = new Integer(tempString.substring(
                                    tempString.lastIndexOf(
                                      UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX)+9)).intValue();
              //AthUserDCMAccessCMPHome newAthUserDCMAccessHome = 
              //  (AthUserDCMAccessCMPHome)Utility.getEJBHome("AthUserDCMAccessCMP", "com.mobinil.sds.core.system.sa.users.dao.cmp.AthUserDCMAccessCMPHome");
              //AthUserDCMAccessCMP newAthUserDCMAccessRemote = newAthUserDCMAccessHome.create(new UserDCMModel(userID, nDCMID));
              UserDAO.insertAthUserDcmAccess(m_conSDSConnection,userID,nDCMID);
            }
          }
          Vector  usersVector = UserDAO.getAuthenticationCallUsersList(m_conSDSConnection);
          HashMap additionalDataHashMap = UserDAO.getAdditionalData(m_conSDSConnection);
        
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalDataHashMap);
        }
        Utility.closeConnection(m_conSDSConnection);
      } 
      catch (Exception ex) 
      {
        ex.printStackTrace();
      } 
    }
    else if(action.compareTo(AuthCallInterfaceKey.ACTION_AUTHENTICATION_DATA_IMPORT)==0)
    {
        Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("2");
        dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector); 
    }
    else if(action.compareTo(AuthCallInterfaceKey.ACTION_AUTHENTICATION_DATA_IMPORT_PROCESS)==0)
    {
    }
    else if(action.compareTo(AuthCallInterfaceKey.ACTION_GENERATE_AUTHENTICATION_DATA_IMPORT_TEMPLATE)==0)
    {
    }
    else if(action.compareTo(AuthCallInterfaceKey.ACTION_SHEET_PERCENTAGE_IMPORT_TEMPLATE)==0)
    {
    }
    else if(action.compareTo(AuthCallInterfaceKey.ACTION_VIEW_CONTRACT_DATA)==0)
    {
    	String contractDialNo = "";
    	dataHashMap.put(AuthCallInterfaceKey.INPUT_HIDDEN_DIAL_NUMBER, contractDialNo);
    	Vector contractDataVec = new Vector();
    	dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, contractDataVec);
    }
    else if(action.compareTo(AuthCallInterfaceKey.ACTION_SEARCH_CONTRACT_DATA)==0)
    {
    	/*try{
    		//Connection con = Utility.getConnection();
    	
    	String contractDialNo = (String)paramHashMap.get(AuthCallInterfaceKey.INPUT_TEXT_CONTRACT_DIAL_NUMBER);
    	dataHashMap.put(AuthCallInterfaceKey.INPUT_HIDDEN_DIAL_NUMBER, contractDialNo);
    	System.out.println("The dial number isssssssssss " + contractDialNo);
    	ContractModel contractModel = BatchDao.getContractData(contractDialNo);
    	BatchSheetModel batchSheetModel = BatchDao.getBatchSheetData(contractDialNo);
    	String contractMainSimNumber = BatchDao.getContractSiMnumber(contractDialNo);
    	dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO , contractMainSimNumber);
    	String contractId = BatchDao.getContractId(contractDialNo);
    	//String archivingFlag = BatchDao.getArchivingFlag(contractDialNo);
    	//dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG,archivingFlag);
    	//String sheetSerialNumber = BatchDao.getSheetSerial(contractDialNo);
    	//dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO , sheetSerialNumber);
    	//String batchId  = BatchDao.getBatchId(contractDialNo);
    	//dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID , batchId);
    	System.out.println("issssssssssssssss " + 1);
    	ContractStatusTypeDto newContractStatusTypeDto = ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);
    	long startTime = System.currentTimeMillis();
    	startTime = System.currentTimeMillis();
    	if(contractModel != null)
        {         
    		System.out.println("issssssssssssssss " + 1); 
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractModel);
            
            //boolean doesContractHasPreviousWarning = ContractDao.isContractHasWarningInVerification(contractMainSimNumber);
            boolean doesContractHasPreviousWarning = ContractDao.isContractHasWarningInAuthentication(contractModel.getMainSimNum());
            //Utility.logger.debug("doesContractHasPreviousWarning = "+ (System.currentTimeMillis() - startT ));
            
            dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRACT_HAS_PREVIOUS_WARNING , new Boolean(doesContractHasPreviousWarning));
            ////Utility.logger.debug("the doesContractHasPreviousWarning statement  = " + (System.currentTimeMillis() - startTime));
            startTime  = System.currentTimeMillis();
     
            Vector contractUserWarningModelVector = ContractDao.getActiveUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);
            ////Utility.logger.debug("the contractUserWarningModelVector statement  = " + (System.currentTimeMillis() - startTime));
            startTime  = System.currentTimeMillis();
            String automaticFlag = contractModel.getAutomaticFlag();
            
            String contractLastStatusId = contractModel.getContractLastStatusId();

            //Vector contractSystemWarningModelVector = new Vector();
              //            contractSystemWarningModelVector = ContractDao.validateContractInContractVerification(con,contractModel.getId(),automaticFlag,contractLastStatusId);
            
            //Vector contractSystemWarningModelVector = ContractDao.getContractWarnings(contractId);
            
           ////Utility.logger.debug("the contractSystemWarningModelVector statement  = " + (System.currentTimeMillis() - startTime));
            startTime  = System.currentTimeMillis();
            
            
//            if (contractSystemWarningModelVector ==null)
//            {
//                          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, 
//                            "Could not connect to the Inventory Database. Please contact your System Administrator");
//            }
            
            
            Vector contractWarningsModelVector = ContractDao.getContractWarnings(contractId);
              ////Utility.logger.debug("the contractWarningsModelVector statement  = " + (System.currentTimeMillis() - startTime));
            startTime  = System.currentTimeMillis();
            
            HashMap warnings = new HashMap();
            warnings.put(AuthCallInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
            warnings.put(AuthCallInterfaceKey.OBJ_CONTRACT_WARNING, contractWarningsModelVector);
//            if (contractSystemWarningModelVector!=null)
//            {
//              warnings.put(AuthCallInterfaceKey.OBJ_SYSTEM_WARNING, contractSystemWarningModelVector);
//            }
            // add the vector to the datahasmap to display all the user warnings available 
            dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRACT_HAS_PREVIOUS_WARNING , new Boolean(doesContractHasPreviousWarning));
            dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRACT_WARNING, warnings);
            dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE, newContractStatusTypeDto);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, contractModel);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, batchSheetModel);
            System.out.println("Medhaaaaaaaaaaaaaaaaaaaaaaat");
          
          
        }
    	 else
         {
           dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "This Contract Main Sim Number does not exist.");
         }
         ////Utility.logger.debug("the if statement  = " + (System.currentTimeMillis() - startTime));
         startTime  = System.currentTimeMillis();
         SheetModel newSheetModel = null;
        ////Utility.logger.debug("geting sheetModel = " + (System.currentTimeMillis() - startTime));
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , newSheetModel);
         
    	}*/
    	try{
    		String contractDialNo = (String)paramHashMap.get(AuthCallInterfaceKey.INPUT_TEXT_CONTRACT_DIAL_NUMBER);
        	dataHashMap.put(AuthCallInterfaceKey.INPUT_HIDDEN_DIAL_NUMBER, contractDialNo);
        	System.out.println("The dial number isssssssssss " + contractDialNo);
        	ContractModel contractModel = BatchDao.getContractData(contractDialNo);
        	String contractId = BatchDao.getContractId(contractDialNo);
        	String batchLastStatus = BatchDao.getBatchLastStatus(contractDialNo);
        	dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_BATCH_LAST_STATUS_ID , batchLastStatus);
        	String contractLastStatus = BatchDao.getContractLastStatus(contractDialNo);
        	dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_LAST_STATUS_ID , contractLastStatus);
        	//String sheetId = BatchDao.getSheetId(contractDialNo);
        	String sheetId =  contractModel.getSheetId();
        	BatchSheetModel batchSheetModel = BatchDao.getBatchSheetData(contractDialNo);
        	String contractMainSimNumber = BatchDao.getContractSiMnumber(contractDialNo);
        	dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO , contractMainSimNumber);
        	long startT = System.currentTimeMillis();
        	boolean doesContractHasPreviousWarning = ContractDao.isContractHasWarningInVerification(contractMainSimNumber);
        	dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRACT_HAS_PREVIOUS_WARNING , new Boolean(doesContractHasPreviousWarning));

            startT = System.currentTimeMillis(); 
            
            boolean pOSQuestionFlag = ContractDao.getPOSQuestionFlag(sheetId);
            //Utility.logger.debug("pOSQuestionFlag = "+ (System.currentTimeMillis() - startT ));
            
            dataHashMap.put(AuthCallInterfaceKey.OBJ_POS_QUESTION_FLAG , new Boolean(pOSQuestionFlag));
             ContractStatusTypeDto newContractStatusTypeDto = ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con);
            
            Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);

            startT = System.currentTimeMillis();
            
            Vector contractWarningsModelVector = ContractDao.getContractWarnings(contractId);
            //Utility.logger.debug("contractWarningsModelVector = "+ (System.currentTimeMillis() - startT ));

            startT = System.currentTimeMillis() ;
            HashMap additionalCollection = new HashMap();
            additionalCollection.put(AuthCallInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
            additionalCollection.put(AuthCallInterfaceKey.OBJ_CONTRACT_WARNING, contractWarningsModelVector);
            startT  = System.currentTimeMillis();
            //additionalCollection.put(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT, alreadyChosenContracts);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, batchSheetModel);
            dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE, newContractStatusTypeDto);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractModel);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
//    	Vector contractDataVec = BatchDao.getContractDate(contractDialNo);
//    	dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, contractDataVec);
    }
    else if(action.compareTo(AuthCallInterfaceKey.ACTION_UPDATE_CONTRACT)==0)
    {
//    	 String dcmID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
//         dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID , dcmID);
//         String batchID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
//         dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID , batchID);
//         String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
//         dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
//         String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
//         dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
//         String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
//         dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
//         String sheetID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
//         dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID , sheetID);
//         String sheetSerialNumber = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);
//         dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO , sheetSerialNumber);
//         String sheetPOSCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE);
//         dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE , sheetPOSCode);
//         String sheetSuperDealerCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE);
//         dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE , sheetSuperDealerCode);
//         String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);
//         String contractId =(String)paramHashMap.get( ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID);  
//         String archivingFlag = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG);
//         dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG , archivingFlag);
//         String[] applied_user_warnings = new String[0];
//         Object o = paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
//         if (o!=null && (!(o instanceof String )))
//         {
//           //////Utility.logger.debug("class name = "+ o.getClass().getName());
//           
//           applied_user_warnings = (String[])paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
// /*
//           for (int i = 0;i<applied_user_warnings.length;i++)
//           {
//             ////Utility.logger.debug("warning " + applied_user_warnings[i]);
//           }
//           */
//           
//         }
//         else if (o!=null && (o instanceof String))
//         {
//           applied_user_warnings = new String[1];
//           applied_user_warnings[0] = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
//         }
//
//         String[] system_warnings = new String[0];
//         o = paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_SYSTEM_WARNINGS);
//         if (o!=null && (!(o instanceof String )))
//         {
//           //////Utility.logger.debug("class name = "+ o.getClass().getName());
//           
//           system_warnings = (String[])paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_SYSTEM_WARNINGS);
// /*
//           for (int i = 0;i<system_warnings.length;i++)
//           {
//             ////Utility.logger.debug("warning " + system_warnings[i]);
//           }
//   */        
//         }
//         else if (o!=null && (o instanceof String))
//         {
//           system_warnings = new String[1];
//           system_warnings[0] = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_SYSTEM_WARNINGS);
//         }
//         String newStatus = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);
//
//         //if (contractSystemWarningModelVector!=null)
//         if (system_warnings !=null)
//         {
//           //ContractDao.updateContract(contractId, strUserID, sheetID, batchID, newStatus, applied_user_warnings, contractSystemWarningModelVector);
//           ContractDao.updateContract(contractId, strUserID, sheetID, batchID, newStatus, applied_user_warnings, system_warnings);
//         }
//         SheetModel newSheetModel = SheetDao.verifySheet(sheetSerialNumber, sheetPOSCode, sheetSuperDealerCode,batchID);
//         dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , newSheetModel);
//         if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_UPDATE_CONTRACT)==0) {
//           ContractStatusTypeDto newContractStatusTypeDto = ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE);
//           ContractDto newContractDto = ContractDao.getSheetContracts(sheetID);
//           Vector contractUserWarningModelVector = ContractDao.getActiveUserContractWarningByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE);
//             
//           dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
//           dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE, newContractStatusTypeDto);
//           dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , newContractDto);
//         }
    	
    	
    	System.out.println("user id isssssssssss " + strUserID);
    	String contractDialNo = (String)paramHashMap.get(AuthCallInterfaceKey.INPUT_HIDDEN_DIAL_NUMBER);
    	dataHashMap.put(AuthCallInterfaceKey.INPUT_HIDDEN_DIAL_NUMBER, contractDialNo);
        Hashtable additionalCollection = new Hashtable();
        long startTime = System.currentTimeMillis() ;
  //hagry1
        //Keep track of the search criteria by resending the parameters sent by the client.
        //Helpfull to allow back and cancel buttons use.
        String contractID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID);
        System.out.println("The contract id isssssssssss " + contractID);
        String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
        String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);
      
        String batchID =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        String sheetID =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
        //String authCallStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_STATUS);
        String authCallStatus= "Unreachable";
        String contractStatusTypeID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);

        //Check if the parameter is a string or an array of strings and extract data from it.
        String[] applied_user_warnings = new String[0];
        Object o = paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
        if (o!=null && (!(o instanceof String )))
        {
          applied_user_warnings = (String[])paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
        }
        else if (o!=null && (o instanceof String))
        {
          applied_user_warnings = new String[1];
          applied_user_warnings[0] = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
        }
        Vector contractSystemWarningModelVector = new Vector();

        Utility.logger.debug(" phase 1 time = " + (System.currentTimeMillis() - startTime ));
         
        startTime = System.currentTimeMillis();
        String contractStatusID = ContractDao.updateContractStatusRecord(contractID, strUserID, sheetID, batchID, contractStatusTypeID, applied_user_warnings, contractSystemWarningModelVector);

        Utility.logger.debug(" phase 2 time = " + (System.currentTimeMillis() - startTime ));

        
        
        
        //String ownerStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_OWNER_STATUS);
        //String posStatus = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_POS_STATUS);

        Utility.logger.debug(" phase 2.1 time = " + (System.currentTimeMillis() - startTime ));
        startTime = System.currentTimeMillis();
        
        //String authCallStatusID = AuthCallDAO.getAuthCallStatusID(authCallStatus);
        //String authCallPOSStatusID = AuthCallDAO.getAuthCallPOSStatusID(posStatus);
        //String authCallOwnerStatusID = AuthCallDAO.getAuthCallOwnerStatusID(ownerStatus);
        
        Utility.logger.debug(" phase 2 time = " + (System.currentTimeMillis() - startTime ));
        startTime = System.currentTimeMillis();

        Utility.logger.debug(" phase 3 time = " + (System.currentTimeMillis() - startTime ));
        startTime = System.currentTimeMillis();

        SheetDao.updateSheetStatusRecord(sheetID ,batchID,strUserID);        
        
        BatchDao.updateBatchStatus(batchID,con);
        
        //Vector batchDtos = new Vector();
        //batchDtos.add(BatchDao.getBatch(batchID, phaseName));
        
        //Vector contractUserWarningModelVector = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);

    
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
        //additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO, batchDtos);

        //additionalCollection.put("batch_auth_statistics",BatchDao.getBatchAuthStatistics(batchID));

        //dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);

        //HashMap sheetContracts = new HashMap();
        //HashMap sheetsStatistic= new HashMap();
        String contractMainSimNoToAdd = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);
        String[] alreadyChosenContracts = new String[0];
        o = paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        if (o!=null && (!(o instanceof String )))
        {
          alreadyChosenContracts = (String[])paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }
        else if (o!=null && (o instanceof String))
        {
          alreadyChosenContracts = new String[1];
          alreadyChosenContracts[0] = (String)paramHashMap.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        }        

        startTime = System.currentTimeMillis();

        //  sheetContracts = BatchDao.addSheetToDisplayedContractsInAuthintication(batchID,alreadyChosenContracts,null);
        //sheetContracts = BatchDao.addContractToDisplayedContractsInAuthintication(batchID,alreadyChosenContracts,contractMainSimNoToAdd);
        
          Utility.logger.debug(" geting addContractToDisplayedContractsInAuthintication time = "+ (System.currentTimeMillis()- startTime));
        startTime = System.currentTimeMillis();
          //SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchID);
          
        //Object[] sheetsId = sheetContracts.keySet().toArray();
//        for (int i = 0 ;  i < newSheetDto.getSheetModelsSize();i++)
//        {
//          // Get sheet statistics as SheetAuthinticationStatisticModel.
//          // Statistics about the number of contracts, authenticated contracts 
//          // and remaining contracts a sheet.
//          SheetModel sheet = newSheetDto.getSheet(i);
//          
//          SheetAuthinticationStatisticModel sheetAuthinticationStatistic =SheetDao.getSheetAuthinticationStatisticModel(sheet.getSheetId());
//          if (sheetAuthinticationStatistic!=null)
//          {
//            sheetsStatistic.put((String)sheet.getSheetId(),sheetAuthinticationStatistic);
//          }
//        }        

        //dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetContracts);

        dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE,
                                 ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE,con));

        // Send statistics about the number of contracts, authenticated contracts 
        // and remaining contracts in each sheet.
        //dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL, sheetsStatistic);                               
        //dataHashMap.put(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS, AuthCallInterfaceKey.ADD_CONTRACT);
        
        Hashtable sheetsReImportedTable = new Hashtable();
      //  SheetDto newSheetDto = SheetDao.getBatchSheetsEligableForAuthintication(batchID);
                
//                for(int i=0; i<newSheetDto.getSheetModelsSize(); i++)
//                {
//          
//                  SheetModel newSheetModel = newSheetDto.getSheet(i);
//
//                  if (newSheetModel.isSheetReImportFlag())
//                  {
//                    sheetsReImportedTable.put(newSheetModel.getSheetId() , newSheetModel.getSheetId());
//              
//                  }
//                }  
        dataHashMap.put(ContractRegistrationInterfaceKey.SHEET_REIMPORTED_TABLE , sheetsReImportedTable);
        Utility.logger.debug(" phase 4 time = " + (System.currentTimeMillis() - startTime ));
      
    }
    return dataHashMap;
  }
}
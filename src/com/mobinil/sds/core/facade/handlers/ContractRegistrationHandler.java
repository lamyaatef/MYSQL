package com.mobinil.sds.core.facade.handlers;

/**
 * ContractRegistrationHandlerBean Statless Sesion Bean.
 * It handles all Contract Registration module actions. 
 * 
 * 1- public HashMap handle(String action, HashMap paramHashMap)
 *    Handles all actions of the authentication call module required by client.
 *    According to the action it calls the concerned Data Access Object 
 *    or a CMP and return the data Hash Map with the returned data.
 * 
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * 
 */ 

import com.mobinil.sds.core.system.cr.batch.dao.BatchDao;
import com.mobinil.sds.core.system.cr.batch.dto.*;
import com.mobinil.sds.core.system.cr.contract.dao.*;
import com.mobinil.sds.core.system.cr.contract.dto.*;
import com.mobinil.sds.core.system.cr.contract.model.*;
import com.mobinil.sds.core.system.cr.importlog.dao.*;
import com.mobinil.sds.core.system.cr.phase.dao.*;
import com.mobinil.sds.core.system.cr.phase.dto.*;
import com.mobinil.sds.core.system.cr.phase.model.*;
import com.mobinil.sds.core.system.cr.sheet.dao.*;
import com.mobinil.sds.core.system.cr.sheet.dto.*;
import com.mobinil.sds.core.system.cr.sheet.model.*;
import com.mobinil.sds.core.system.cr.worker.*;
import com.mobinil.sds.core.system.gn.dcm.dao.DCMDao;
import com.mobinil.sds.core.system.gn.dcm.dto.DCMDto;
import com.mobinil.sds.core.system.sa.users.dao.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.web.interfaces.*;
import com.mobinil.sds.web.interfaces.ac.*;
import com.mobinil.sds.web.interfaces.cr.*;
import com.mobinil.sds.web.interfaces.sa.*;


import java.sql.Connection;
import java.util.*;

import com.mobinil.sds.core.system.sa.importdata.dao.*;


public class ContractRegistrationHandler  
{


  /**
  * handle method:
  * Handles all actions of the Contract Registration module required by client.
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

  public static HashMap handle(String action, HashMap paramHashMap, java.sql.Connection con)
  {
    HashMap dataHashMap = new HashMap(100);
    
    try
    {
      String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      if(strUserID != null && strUserID.compareTo("") != 0  )
      {
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);           
      }

      String strChannelId = "1";
      if(paramHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
      {
        strChannelId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
      }

      Utility.logger.debug("dddd : "+strChannelId);
      //generate excel file this is one method implemented in a littile bit different 
      //from the architecture of the rest of the code 
      //the code needed is jsut one method call  however it is not done here 
      //it is done in the jsp 
      //this is due to its nature of genreating a file that the user download it
      if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_GENERATE_FILE)==0)
      {
      }
      /*
       * import file code 
       * the responsible of importing the uploaded excel file 
       * also the mehtod here is not handelde in the handle howevfeer it is handeld inthe 
       * jsp page due to its nature 
       */
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_IMPORT_FILE)==0)
      {
      }

        else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_LCS_PRODUCT_MAPPING)==0)
      {
        dataHashMap = new HashMap();
      //  Connection con = Utility.getConnection();
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
          Vector lcsVec = ContractDao.getLCSProductMapping(con);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, lcsVec);
          
      }

      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_EDIT_LCS_PRODUCT_MAPPING)==0)
      {
        dataHashMap = new HashMap();
      //  Connection con = Utility.getConnection();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        String productId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_PRODUCT_ID);
        
        if(productId.equals("null")){
            productId=null;
        }
        dataHashMap.put(ContractRegistrationInterfaceKey.INPUT_HIDDEN_PRODUCT_ID,productId);
        String inventoryItemType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_INVENTORY_ITEM_TYPE);
        dataHashMap.put(ContractRegistrationInterfaceKey.INPUT_HIDDEN_INVENTORY_ITEM_TYPE,inventoryItemType);
        lcsProductMappingModel lcsProductMappingModel = ContractDao.selectLcsProductMapping(con,productId,inventoryItemType);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,lcsProductMappingModel);
        Vector products = ContractDao.getAllProducts(con);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,products);
      }

      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADD_LCS_PRODUCT_MAPPING)==0)
      {
        dataHashMap = new HashMap();
       // Connection con = Utility.getConnection();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        Vector products = ContractDao.getAllProducts(con);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,products);
      }


      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_LCS_PRODUCT_MAPPING)==0)
      {
        dataHashMap = new HashMap();
     //   Connection con = Utility.getConnection();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        String newProductId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_SEARCH_SELECT_PRODUCT_NAME);
        String newInventoryItemType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_TEXT_INVENTORY_ITEM_TYPE);  
        String productId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_PRODUCT_ID);
        String inventoryItemType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_INVENTORY_ITEM_TYPE);
        boolean check = ContractDao.updateLcsProductMapping(con,productId,inventoryItemType,newProductId,newInventoryItemType);
        System.out.println("The check isssssssssssssssssssssss " + check);
        if(check)
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Inventory Item Type already exists and assigned to another Product");
        }
       Vector lcsVec = ContractDao.getLCSProductMapping(con);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, lcsVec);
      }

      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SAVE_LCS_PRODUCT_MAPPING)==0)
      {
        dataHashMap = new HashMap();
     //   Connection con = Utility.getConnection();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        String productId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_SEARCH_SELECT_PRODUCT_NAME);
        String inventoryItemType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_TEXT_INVENTORY_ITEM_TYPE);  
        boolean check = ContractDao.saveLcsProductMapping(con,productId,inventoryItemType);
        System.out.println("The check isssssssssssssssssssssss " + check);
        if(check)
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Inventory Item Type already exists");
        }
       Vector lcsVec = ContractDao.getLCSProductMapping(con);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, lcsVec);
      }

       else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_LCS_DCM_MAPPING)==0)
      {
        dataHashMap = new HashMap();
      //  Connection con = Utility.getConnection();
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
          Vector lcsVec = ContractDao.getLCSDcmMapping(con);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, lcsVec);
          
      }

      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_EDIT_LCS_DCM_MAPPING)==0)
      {
        dataHashMap = new HashMap();
     //   Connection con = Utility.getConnection();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        String dcmlcsNameId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_DCM_LCS_NAME_ID);
        dataHashMap.put(ContractRegistrationInterfaceKey.INPUT_HIDDEN_LCS_NAME,dcmlcsNameId);
        LcsDcmMappingModel lcsDcmMappingModel = ContractDao.selectLcsDcmMapping(con,dcmlcsNameId);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,lcsDcmMappingModel);
      }

       else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_LCS_DCM_MAPPING)==0)
      {
        dataHashMap = new HashMap();
      //  Connection con = Utility.getConnection();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        String newDcmCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_TEXT_DCM_CODE);
        String newDcmName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_TEXT_DCM_NAME);
        String newLcsNam = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_TEXT_LCS_NAME);
        String dcmLcsId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_DCM_LCS_NAME_ID);
        String dcmCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_DCM_CODE);
        String dcmName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_DCM_NAME);
        String lcsName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_LCS_NAME);
        boolean check = ContractDao.updateLcsDcmMapping(con,dcmLcsId,dcmCode,dcmName,lcsName,newDcmCode,newDcmName,newLcsNam);
        System.out.println("The check isssssssssssssssssssssss " + check);
        if(check)
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"This DCM does not exist");
        }
          Vector lcsVec = ContractDao.getLCSDcmMapping(con);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, lcsVec);
      }

      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SAVE_LCS_DCM_MAPPING)==0)
      {
        dataHashMap = new HashMap();
     //   Connection con = Utility.getConnection();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        Long dcmLcsNameId = Utility.getSequenceNextVal(con,"SEQ_DCM_LCS");
        String dcmCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_TEXT_DCM_CODE);
        String dcmName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_TEXT_DCM_NAME);
        String lcsName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_TEXT_LCS_NAME);  
        boolean check = ContractDao.saveLcsDcmMapping(con,dcmLcsNameId,dcmCode,dcmName,lcsName);
        System.out.println("The check isssssssssssssssssssssss " + check);
        if(check)
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"This DCM does not exist");
        }
          Vector lcsVec = ContractDao.getLCSDcmMapping(con);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, lcsVec);
      }
      
      /*
       * action show generate batch screen of the normal screen or the admin screen handling 
       */
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_GEN_BATCH_SCREEN)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_ADMIN_GEN_BATCH_SCREEN)==0 || 
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_GEN_BATCH_FRANCHISE)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_ADMIN_GEN_BATCH_FRANCHISE)==0 ||
    	  // COMPLEMANTRY ADDED
    	      action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_GEN_BATCH_COMPLEMANTRY)==0 ||
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_ADMIN_GEN_BATCH_COMPLEMANTRY)==0) 
          
      {

        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_GEN_BATCH_FRANCHISE)==0 ||
           action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_ADMIN_GEN_BATCH_FRANCHISE)==0)
        {
          strChannelId = "2";
        }
        
        // COMPLEMANTRY ADDED
        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_GEN_BATCH_COMPLEMANTRY)==0 ||
                action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_ADMIN_GEN_BATCH_COMPLEMANTRY)==0)
             {
               strChannelId = "3";
             }
      
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID, strChannelId);
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID , strChannelId,con);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
      }
      /*
       * generating batch 
       */
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_GENERATE_BATCH)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_GENERATE_BATCH)==0)
      {
        String generationDate = (String)paramHashMap.get(ContractRegistrationInterfaceKey.GENERATE_BATCH_DATE);
        String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.GENERATE_BATCH_DCM_ID);
        String channelId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID,channelId,con);

        generationDate = "TO_DATE(\'" + generationDate+"\',\'DD\\MM\\YYYY\')";

        //creating the batch and returning a vector of strings that contian a report about the 
        //batches got genreated or message that no batches were generated 
        Vector report = BatchDao.createBatch(dcmId,generationDate);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , report);
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_IMPORT_LOG_SEARCH_SCREEN)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_IMPORT_LOG_SEARCH_SCREEN_FRANCHISE)==0
    	  // complemantry added  
    	  ||  action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_IMPORT_LOG_SEARCH_SCREEN_COMPLEMANTRY)==0)
    	  
      {

        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_IMPORT_LOG_SEARCH_SCREEN_FRANCHISE)==0)
        {
          strChannelId = "2";
        }
        
        
       // complemantry added
        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_IMPORT_LOG_SEARCH_SCREEN_COMPLEMANTRY)==0)
        {
          strChannelId = "3";
        }
        
        
        Hashtable additionalCollection = new Hashtable();
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID , strChannelId,con);

        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
      }
      

      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_DOWNLOAD_EXCEL_LOG)==0)
      {
        dataHashMap.put(ContractRegistrationInterfaceKey.DOWNLOAD_EXCEL_LOG_ID,paramHashMap.get(ContractRegistrationInterfaceKey.DOWNLOAD_EXCEL_LOG_ID));
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SEARCH_IMPORT_LOG)==0)
      {
        Hashtable additionalCollection = new Hashtable();
        String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
        String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_IMPORT_LOG_DATE);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_IMPORT_LOG_DATE,date);
        String channelId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID,channelId);
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID,channelId,con);

        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , ImportLogDAO.SearchImportLog(dcmId, date));
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_SEARCH_BATCH_SCREEN_DELIVERY)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_SEARCH_SCREEN_DELIVERY_FRANCHISE)==0 ||
               // complemantry added
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_SEARCH_SCREEN_DELIVERY_COMPLEMANTRY)==0)
      {

        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_SEARCH_SCREEN_DELIVERY_FRANCHISE)==0)
        {
          strChannelId = "2";
        }
        
        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_SEARCH_SCREEN_DELIVERY_COMPLEMANTRY)==0)
        {
          strChannelId = "3";
        }
        Hashtable additionalCollection = new Hashtable();
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID , strChannelId,con);
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInDelivery(con);         

        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SEARCH_BATCH_DELIVERY)==0)
      {
        Hashtable additionalCollection = new Hashtable();
        String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
        String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);         
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
        String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
        String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
        String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);         
        String channelId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID,channelId);
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID,channelId,con);
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInDelivery(con);

        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , BatchDao.SearchBatch(dcmId, date, batchType, batchStatusTypeId,phaseName,con));
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_DETAILS)==0)
      {
        Hashtable additionalCollection = new Hashtable();
      
        String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmId);
        String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
        String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
        String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
        String batchId =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);

        Vector warnings = SheetDao.getSheetActiveUserWarningByPhase(ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con);
        SheetStatusTypeDto newSheetStatusTypeDto = SheetDao.getSheetStatusTypeByPhase(ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con);
        Vector batchDtos = new Vector();
        
        batchDtos.add(BatchDao.getBatchAndSheets(batchId,ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con));

        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING,warnings);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SHEET_STATUS_TYPE, newSheetStatusTypeDto);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batchDtos);
      }    
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_PRINT_BATCHS)==0)
      {
        String batchIds =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        String temp = "";
        Vector batchDtos = new Vector();
        for (int i = 0 ; i < batchIds.length();i++)
        {
          if (batchIds.charAt(i)==',') 
          {
            if (temp.length()!=0)
            {
              batchDtos.add(BatchDao.getBatchAndSheets(temp,ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con));
            }
            temp="";
          }
          else
          {
            temp+= batchIds.charAt(i) ;
          }       
        }
        if (temp.length()!=0)
        {
          batchDtos.add(BatchDao.getBatchAndSheets(temp,ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con));
        }
        temp="";
         
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batchDtos);
      }        
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_APPLY_SHEET_STATUS)==0)
      {
        Hashtable additionalCollection = new Hashtable();

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

        String statusSheetID = "";
        String warningSheetID = "";
        String statusID = "";
        String[] warningID = new String[1];
        for(int j=0; j<paramHashMap.size(); j++)
        {
          String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
          if(tempStatusString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
          {
            statusSheetID = tempStatusString.substring(tempStatusString.lastIndexOf(
                                          ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7);
            statusID = (String)paramHashMap.get(tempStatusString);
            String sheetStatusID = SheetDao.insertSheetStatus(statusSheetID, statusID, strUserID,con);          
            if(sheetStatusID != null)
            {
              for(int k=0; k<paramHashMap.size(); k++)
              {
                String tempWarningString = (String)paramHashMap.keySet().toArray()[k];
                if(tempWarningString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS))
                {
                  warningSheetID = tempWarningString.substring(tempWarningString.lastIndexOf(
                                                ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS)+14);
                  warningID[0] = (String)paramHashMap.get(tempWarningString);
                  if(warningSheetID.compareTo(statusSheetID) == 0)
                  {
                    SheetDao.insertSheetWarnings(sheetStatusID,warningID,con);
                  }
                }
              }
            }
          }       
        }

        BatchDao.updateBatchStatus(batchId,con);
   
        Vector warnings = SheetDao.getSheetActiveUserWarningByPhase(ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con);
       
        SheetStatusTypeDto newSheetStatusTypeDto = SheetDao.getSheetStatusTypeByPhase(ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con);
        
        Vector batchDtos = new Vector();
        
        batchDtos.add(BatchDao.getBatchAndSheets(batchId,ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con));
        
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING,warnings);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SHEET_STATUS_TYPE, newSheetStatusTypeDto);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batchDtos);
        
      }    
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_STATUS)==0)
      {
        Hashtable additionalCollection = new Hashtable();
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

        String statusSheetID = "";
        String warningSheetID = "";
        String statusID = "";
        String[] warningID = new String[1];
        for(int j=0; j<paramHashMap.size(); j++)
        {
          String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
          if(tempStatusString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
          {
            statusSheetID = tempStatusString.substring(tempStatusString.lastIndexOf(
                                          ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7);
            statusID = (String)paramHashMap.get(tempStatusString);
            String sheetStatusID = SheetDao.insertSheetStatus(statusSheetID, statusID, strUserID,con);     
            if(sheetStatusID != null)
            {
              for(int k=0; k<paramHashMap.size(); k++)
              {
                String tempWarningString = (String)paramHashMap.keySet().toArray()[k];
                if(tempWarningString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS))
                {
                  warningSheetID = tempWarningString.substring(tempWarningString.lastIndexOf(
                                                ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS)+14);
                  warningID[0] = (String)paramHashMap.get(tempWarningString);
                  if(warningSheetID.compareTo(statusSheetID) == 0)
                  {
                    SheetDao.insertSheetWarnings(sheetStatusID,warningID,con);
                  }
                }
              }
            }
          }       
        }
        BatchDao.updateBatchStatus(batchId,con);
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID,con);
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInDelivery(con);         

        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , BatchDao.SearchBatch(dcmId, date, batchType, batchStatusTypeId,phaseName,con));
      }    
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_SHEET_DETAILS)==0 )
      {
        Hashtable additionalCollection = new Hashtable();
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
        String sheetID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID , sheetID);
        String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);

        //String sheetStatusID = "";      
        //////Utility.logger.debug("sheet id " + sheetID);

        //      BatchDao.updateBatchStatus(batchId,phaseName);

        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID,con);
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInDelivery(con);         

        Vector warnings = SheetDao.getSheetActiveUserWarningByPhase(ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con);

        Vector sheetWarnings = SheetDao.getSheetWarnings(sheetID,con);

        SheetStatusTypeDto newSheetStatusTypeDto = SheetDao.getSheetStatusTypeByPhase(ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con);
      
        SheetDto sheetDto =  SheetDao.getSheetWithOutStatistic(sheetID,con);

        ////Utility.logger.debug("get sheet with out statistic time = " + (System.currentTimeMillis() - startT));

       additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING,warnings);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SHEET_WARNING,sheetWarnings);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SHEET_STATUS_TYPE, newSheetStatusTypeDto);
    
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);                           
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION ,sheetDto );
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_DETAILS_STATUS)==0)
      {
        Hashtable additionalCollection = new Hashtable();

        String dcmID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID,dcmID);
        String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
        String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
        String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
        String batchID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        String sheetID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
        String statusID =(String)paramHashMap.get( ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);
        String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);

        String sheetStatusID = SheetDao.insertSheetStatus(sheetID, statusID, strUserID,con);                                               
        BatchDao.updateBatchStatus(batchID,con);
        String[] applied_user_warnings = new String[0];
        Object o = paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
        if (o!=null && (!(o instanceof String )))
        {
          //////Utility.logger.debug("class name = "+ o.getClass().getName());
          
          applied_user_warnings = (String[])paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
/*
          for (int i = 0;i<applied_user_warnings.length;i++)
          {
            //////Utility.logger.debug("warning " + applied_user_warnings[i]);
          }
  */        
        }
        else if (o!=null && (o instanceof String))
        {
          applied_user_warnings = new String[1];
          applied_user_warnings[0] = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
        }
        SheetDao.insertSheetWarnings(sheetStatusID, applied_user_warnings,con);
        Vector warnings = SheetDao.getSheetActiveUserWarningByPhase(ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con);
        SheetStatusTypeDto newSheetStatusTypeDto = SheetDao.getSheetStatusTypeByPhase(ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con);
        Vector batchDtos = new Vector();
        batchDtos.add(BatchDao.getBatchAndSheets(batchID,ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE,con));

        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING,warnings);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SHEET_STATUS_TYPE, newSheetStatusTypeDto);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batchDtos);
      }
      
   // Complementary added
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_SEARCH_BATCH_SCREEN_CONTRACT_VERIFICATION)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_SEARCH_BATCH_SCREEN_CONTRACT_VERIFICATION)==0 || 
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_SEARCH_BATCH_SCREEN_CONTRACT_VERIFICATION_BY_SHEET)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_MANAGE_BATCHES_OPENED_FOR_ARCHIVING)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_FRANCHISE)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_BY_SHEET_FRANCHISE)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_MANAGE_BATCHES_OPENED_FOR_ARCHIVING_FRANCHISE)==0 ||
               // Complementary added  
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_MANAGE_BATCHES_OPENED_FOR_ARCHIVING_COMPLEMANTRY)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_FRANCHISE)==0||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_COMPLEMANTRY)==0||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_COMPLEMANTRY)==0 ||
             //Complementary added  
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_BY_SHEET_COMPLEMANTRY)==0 
      
      )
               
      
      
      
     
      {
        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_FRANCHISE)==0 ||
           action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_BY_SHEET_FRANCHISE)==0 ||
           action.compareTo(ContractRegistrationInterfaceKey.ACTION_MANAGE_BATCHES_OPENED_FOR_ARCHIVING_FRANCHISE)==0 ||
           action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_FRANCHISE)==0)
        {
          strChannelId = "2";
        }
        
        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_COMPLEMANTRY)==0 ||
                action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_BY_SHEET_COMPLEMANTRY)==0 ||
                action.compareTo(ContractRegistrationInterfaceKey.ACTION_MANAGE_BATCHES_OPENED_FOR_ARCHIVING_COMPLEMANTRY)==0 ||
                action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_COMPLEMANTRY)==0)
             {
               strChannelId = "3";
             }
      
        Hashtable additionalCollection = new Hashtable();
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID , strChannelId,con);
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInContractVerification(con);
        
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SEARCH_BATCH_CONTRACT_VERIFICATION)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SEARCH_BATCH_CONTRACT_VERIFICATION)==0||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SEARCH_BATCH_CONTRACT_VERIFICATION_BY_SHEET)==0||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_SEARCH_MANAGE_BATCH_OPENED_FOR_ARCHIVING)==0)
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
        String channelId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID,channelId);
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID,channelId,con);
        
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);         
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInContractVerification(con);
        
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , BatchDao.SearchBatch(dcmId, date, batchType, batchStatusTypeId, ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con));
        
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_GENERATE_EXCEL_FOR_ACCEPTED_VERIFY)==0 ||
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_GENERATE_EXCEL_FOR_REJECTED_VERIFY)==0)
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
        String batchId =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
    
        BatchDto batchDto = BatchDao.getBatchAndSheets(batchId,ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
        SheetDto sheetDto = batchDto.getSheetDto();
        Vector listOfSheets = sheetDto.getSheetModels();
        for(int i=0;i<listOfSheets.size();i++)
        {
          SheetModel sheetModel = (SheetModel)listOfSheets.get(i);   
          String sheetId = sheetModel.getSheetId();
          ContractDto contractDto = new ContractDto();
          if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_GENERATE_EXCEL_FOR_ACCEPTED_VERIFY)==0)
          {
            contractDto = ContractDao.getSheetContractsByContractStatus(sheetId,ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_ACCEPTED_VERIFY);
          }
          else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_GENERATE_EXCEL_FOR_REJECTED_VERIFY)==0)
          {
            contractDto = ContractDao.getSheetContractsByContractStatus(sheetId,ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_REJECTED_VERIFY); 
          }
          Vector contractModels = contractDto.getContractModels();
          sheetDto.addSheetContracts(sheetId,contractModels);
        }      
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batchDto);
        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_GENERATE_EXCEL_FOR_ACCEPTED_VERIFY)==0)
        {
          dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS,ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_ACCEPTED_VERIFY);
        }
        else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_GENERATE_EXCEL_FOR_REJECTED_VERIFY)==0)
        {
          dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS,ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_REJECTED_VERIFY);
        }
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_AUTHINTICATE_BATCH_CONTRACT_VERIFICATION)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_AUTHINTICATE_BATCH_CONTRACT_VERIFICATION)==0)
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
        String batchId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);

       BatchDao.updateBatchPhase(batchId,ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE); 
        
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID,con);
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);         
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInContractVerification(con);
          
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , BatchDao.SearchBatch(dcmId, date, batchType, batchStatusTypeId, ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con));
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_IMPORT_EXCEL_FROM_ARCHIVING)==0)
      {
        dataHashMap = new HashMap ();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("9");
        dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector);
      }
      
      
      
      
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_IMPORT_EXCEL_FROM_ARCHIVING_REJECT)==0)
      {
        dataHashMap = new HashMap ();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("9");
        dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector);
      }
      
      
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_IMPORT_EXCEL_FROM_ARCHIVING_PROCESS)==0)
      {
        dataHashMap = new HashMap ();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        if(paramHashMap.containsKey(InterfaceKey.HASHMAP_KEY_JOB_ID))
        {
          String jobId = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_JOB_ID);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_JOB_ID,jobId);
        }
        else
        {
          String batchId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
          dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID,batchId);
        }
      }
      
      
      
      
      
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_IMPORT_EXCEL_FROM_ARCHIVING_PROCESS_REJECT)==0)
      {
        dataHashMap = new HashMap ();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
       /* if(paramHashMap.containsKey(InterfaceKey.HASHMAP_KEY_JOB_ID))
        {
          String jobId = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_JOB_ID);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_JOB_ID,jobId);
        }
        else
        {
          String batchId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
          dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID,batchId);
        }*/
      }
      
      
      
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_CR_CHECK_SIM_IN_LCS)==0)
      {
        dataHashMap = new HashMap ();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("11");
        dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_CR_CHECK_SIM_IN_LCS_PROCESS)==0)
      {
        dataHashMap = new HashMap ();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_GENERATE_EXCEL_FROM_ARCHIVING_TEMPLATE)==0)
      {
        dataHashMap = new HashMap ();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_OPEN_ARCHIVING_PROCESS)==0||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_OPEN_ARCHIVING_PROCESS_BY_SHEET)==0||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_OPEN_ARCHIVING_PROCESS)==0)
      {
        Hashtable additionalCollection = new Hashtable();         
        //Keep track of the search criteria by resending the parameters sent by the client.
        //Helpfull to allow back and cancel buttons use.
        if(paramHashMap.containsKey(InterfaceKey.HASHMAP_KEY_JOB_ID))
        {
          String jobId = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_JOB_ID);
          HashMap workerHashMap = WorkerDataManagement.getWorkerData(jobId);
          if(workerHashMap == null)
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_JOB_ID,jobId);
          }
          else
          {
            dataHashMap = workerHashMap;
            WorkerDataManagement.removeWorkerData(jobId);
          }
        }
        else
        {
            String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
            String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
            String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
            String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
            String batchId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);

            OpenArchivingWorker oaWorker = new OpenArchivingWorker(strUserID,batchId,dcmId,date,batchType,batchStatusTypeId);
            String jobId = WorkerDataManagement.addWorker(oaWorker);
            HashMap workerHashMap = WorkerDataManagement.getWorkerData(jobId);
            if(workerHashMap == null)
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_JOB_ID,jobId);
            }
            else
            {
              dataHashMap = workerHashMap;
              WorkerDataManagement.removeWorkerData(jobId);
            }
        }

        /*
         * BatchDto batchDto = BatchDao.getBatchAndSheets(batchId,ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE);
        SheetDto sheetDto = batchDto.getSheetDto();
        Vector listOfSheets = sheetDto.getSheetModels();
        for(int i=0;i<listOfSheets.size();i++)
        {
          SheetModel sheetModel = (SheetModel)listOfSheets.get(i);   
          String sheetId = sheetModel.getSheetId();
          ContractDto contractDto = ContractDao.getSheetContracts(sheetId);
          Vector contractModels = contractDto.getContractModels();
          for(int j=0;j<contractModels.size();j++)
          {
            ContractModel contractModel = (ContractModel)contractModels.get(j);
            String contractId = contractModel.getId();
            String automaticFlag = contractModel.getAutomaticFlag();
            String contractLastStatusId = contractModel.getContractLastStatusId();
            String contractStatusName = contractModel.getStatusTypeName();
            
            Vector contractUserWarnings = contractDto.getContractWarning(contractId);
            Vector contractSystemWarningModelVector = ContractDao.validateContractInContractVerification(con,contractId,automaticFlag,contractLastStatusId);
            String contractSystemGeneratedStatusId = "5";
            //5 is accpeted verify status
            for(int k=0;k<contractSystemWarningModelVector.size();k++)
            {
              ContractWarningModel contractWarningModel = (ContractWarningModel)contractSystemWarningModelVector.elementAt(k);
              String warningSuggestedStatus = contractWarningModel.getContractwarningSuggestedStatusID();
              if(warningSuggestedStatus.compareTo("2")==0)
              {
                //suggested 2 means rejected

                //6 is rejected verify status
                contractSystemGeneratedStatusId = "6";
              }
            }
            //update here contract status with system generated status
            String[] user_warnings = new String[0];
            if(contractStatusName.compareTo(ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_ACCEPTED_VERIFY)!=0 && contractStatusName.compareTo(ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_REJECTED_VERIFY)!=0)
            {
              ContractDao.updateContract(contractId, strUserID, sheetId, batchId, contractSystemGeneratedStatusId, user_warnings , contractSystemWarningModelVector);
            }  
          }
        }
      
        BatchDao.updateBatchArchivingFlag(batchId,ContractRegistrationInterfaceKey.CONST_ARCHIVING_FLAG_ENTERED_ARCHIVING_PROCESS); 
        
        DCMDto dcmDto = DCMDao.getContractRegisterationUserDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR, strUserID);
        BatchTypeDto batchTypeDto = BatchDao.getBatchTypes(con);         
        BatchStatusTypeDto batchStatusTypeDto = BatchDao.getBatchStatusTypeAllowedInContractVerification();
          
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO,batchTypeDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO,batchStatusTypeDto);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , BatchDao.SearchBatch(dcmId, date, batchType, batchStatusTypeId, ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE));
        */
      }
      
      
      
      
      
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_DETAILS_CONTRACT_VERIFICATION)==0 ||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_DETAILS_CONTRACT_VERIFICATION)==0 || 
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_DETAILS_CONTRACT_VERIFICATION_BY_SHEET)==0||
               action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_UPDATE_CONTRACT_STATUS_CONTRACT_VERIFY_SCREEN_BY_SHEET)==0)
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
        String batchId =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        
        
        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_UPDATE_CONTRACT_STATUS_CONTRACT_VERIFY_SCREEN_BY_SHEET)==0) 
        {
            String[] warningID = new String[1];
            String statusSheetID ="";
            for(int j=0; j<paramHashMap.size(); j++)
            {
              
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              if(tempStatusString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
              {
                statusSheetID = tempStatusString.substring(tempStatusString.lastIndexOf(
                                              ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7);
                
                if (isStringValidInt (statusSheetID))
                {
                  String statusID = (String)paramHashMap.get(tempStatusString);
                                    
                  if(statusID.compareTo("") != 0)
                  {
                    String tempWarningString = ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+"_"+statusSheetID;
                    warningID[0] = (String) paramHashMap.get(tempWarningString);
                    //////////////get contracts by sheet id///////
                    ContractDto newContractDto = ContractDao.getSheetContracts(statusSheetID,con);
                    Vector vecContractsModel = newContractDto.getContractModels();
                    ContractModel newContractModel =null;
               
                    for(int i = 0;i<vecContractsModel.size();i++) 
                    {
                      newContractModel = (ContractModel)vecContractsModel.get(i);                                        
                      
                      Vector contractSystemWarningModelVector = ContractDao.validateContractInContractVerification(con,newContractModel.getId(),newContractModel.getAutomaticFlag() ,newContractModel.getContractLastStatusId());
                      
                      ContractDao.updateContractRecord(newContractModel.getId() , strUserID , statusSheetID , batchId , statusID , warningID, contractSystemWarningModelVector,con);
                      
                    }
                         
                    SheetDao.updateSheetStatus(statusSheetID, batchId, strUserID,con);             
                  }
                }
              }
                         }
        }

        
        String sheetSerialNumber = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);
        if(sheetSerialNumber != null)
        {
          additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO , sheetSerialNumber);
        }
        String sheetPOSCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE);
        if(sheetPOSCode != null)
        {
          additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE , sheetPOSCode);
        }
        String sheetSuperDealerCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE);
        if(sheetSuperDealerCode != null)
        {
          additionalCollection.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE , sheetSuperDealerCode);
        }
        
        
        ContractStatusTypeDto newContractStatusTypeDto = ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
        dataHashMap.put(ContractRegistrationInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , newContractStatusTypeDto);
        Vector contractUserWarningModelVector = ContractDao.getActiveUserContractWarningByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
        dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
        
        Vector batchDtos = new Vector();
        BatchDto batchDto = null;
        if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_DETAILS_CONTRACT_VERIFICATION)==0||
            action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_DETAILS_CONTRACT_VERIFICATION_BY_SHEET)==0||
            action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_UPDATE_CONTRACT_STATUS_CONTRACT_VERIFY_SCREEN_BY_SHEET)==0) 
        {
          batchDto = BatchDao.getBatchAndSheets(batchId,ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
        }
        else {
          batchDto = BatchDao.getBatch(batchId,ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
        }
        

		 String batchDate = ContractDao.getBatchDate(con ,batchDto.getBatchModel().getBatchId()).split(" ")[0];
 		 System.out.println(batchDate);
 		 String month = batchDate.split("-")[1]  ;
 		 if(month.startsWith("0"))
 		 {
 			month = month.charAt(1)+"";
 		 }
 		 
 			 
 		 String msg = SheetDao.selectCRBatchStatus(batchDate.split("-")[0], month , con);
 			
 		 if(msg.equals("") || msg == null)  /// batch still open and can be deleted
 		 {	
 			batchDto.sheetClosed = 0; 
 		 }
 		 else
 		 {
 			batchDto.sheetClosed = 1;  
 		 }
        batchDtos.add(batchDto);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batchDtos);
        
      }    

      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_VERIFY_SHEET)==0 ||
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_VERIFY_SHEET)==0)
      {
        //Keep track of the search criteria by resending the parameters sent by the client.
        //Helpfull to allow back and cancel buttons use.
        String dcmID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID , dcmID);
        String batchID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID , batchID);
        String archivingFlag =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG,archivingFlag);
        String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
        String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
        String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
        String sheetSerialNumber = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO , sheetSerialNumber);
        String sheetPOSCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE , sheetPOSCode);
        String sheetSuperDealerCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE , sheetSuperDealerCode);

        String contractSimNo = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);
        if(contractSimNo != null)
        {
          dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO , contractSimNo);
        }


       // Utility.logger.debug("verify  sheet ");
        
        SheetModel newSheetModel = SheetDao.verifySheet(sheetSerialNumber, sheetPOSCode, sheetSuperDealerCode,batchID,con);


        if(newSheetModel != null)
        {  

          if (newSheetModel.isSheetReImportFlag() )
          {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP , "Sheet was reimported in a newer batch.");
          }
          
          String sheetID = newSheetModel.getSheetId();
          String sheetBatchID = newSheetModel.getSheetBatchID();
          if(sheetBatchID.compareTo(batchID) == 0)
          {
            ContractStatusTypeDto newContractStatusTypeDto = ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
            if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_VERIFY_SHEET)==0) {
              ContractDto newContractDto = ContractDao.getSheetContracts(sheetID,con);


              dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , newContractDto);
            }
            Vector contractUserWarningModelVector = ContractDao.getActiveUserContractWarningByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
            
            dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
            dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE, newContractStatusTypeDto);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , newSheetModel);
          }
          else
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP , "Sheet does not exists in this batch, it exists in batch "+sheetBatchID+".");
          }
        }
        else
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP , "Such sheet does not exist.");
        }
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_UPDATE_CONTRACT_STATUS_CONTRACT_VERIFY_SCREEN)==0)
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
        String batchID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        String sheetID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);

        String statusContractID = "";
        String warningContractID = "";
        String statusID = "";
        String[] warningID = new String[1];
        //////Utility.logger.debug(" paramHashMap.size() = " + paramHashMap.size());
        Object[] paramArray = paramHashMap.keySet().toArray();
        
        for(int j=0; j<paramArray.length; j++)
        {
          String tempStatusString = (String)paramArray[j];
          if(tempStatusString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
          {
            statusContractID = tempStatusString.substring(tempStatusString.lastIndexOf(
                                          ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7);
                
            if (isStringValidInt (statusContractID))
            {
              statusID = (String)paramHashMap.get(tempStatusString);
             // Utility.logger.debug("ddddddd"+tempStatusString+"----------------"+statusContractID);
              if(statusID.compareTo("") != 0)
              {
                String tempWarningString = ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+"_"+statusContractID;
                warningContractID = statusContractID;
                //////Utility.logger.debug("warningContractID " + warningContractID);
                
              //   if (isStringValidInt (warningContractID))
                  {
                                                                                   
                      warningID[0] = (String)paramHashMap.get(tempWarningString);
                    
                      if(warningContractID.compareTo(statusContractID) == 0)
                      {  
                          String strContractLastStatusAndAuotmaticFlag = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_LAST_STATUS+"_"+statusContractID);
                          String strAutomaticFlag = strContractLastStatusAndAuotmaticFlag.substring(0,1);
                          String strContractLastStatus = strContractLastStatusAndAuotmaticFlag.substring(2,strContractLastStatusAndAuotmaticFlag.length());
        
                          Vector contractSystemWarningModelVector = ContractDao.validateContractInContractVerification(con,warningContractID,strAutomaticFlag,strContractLastStatus);
        
                          ContractDao.updateContractRecord(warningContractID, strUserID , sheetID, batchID, statusID, warningID, contractSystemWarningModelVector,con);                          
                       
                      }
                  }                     
                                
                  }
                                                                  
                    
 
            } 
           
          }       
        }
        SheetDao.updateSheetStatus(sheetID, batchID, strUserID,con);      
        SheetStatusTypeDto newSheetStatusTypeDto = SheetDao.getSheetStatusTypeByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
        Vector batchDtos = new Vector();
        batchDtos.add(BatchDao.getBatchAndSheets(batchID,ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con));
        Vector contractUserWarningModelVector = ContractDao.getActiveUserContractWarningByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
        
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SHEET_STATUS_TYPE, newSheetStatusTypeDto);

        dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batchDtos);
        
        ////Utility.logger.debug(" admin mass update contract = "+ (System.currentTimeMillis() - methodStartTime));
        //Utility.closeConnection(con);
      }    
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_VERIFY_CONTRACT)==0 || 
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_VERIFY_CONTRACT)==0 ||
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_RECHECK_CONTRACT_SYSTEM_WARNING)==0||
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_RECHECK_CONTRACT_SYSTEM_WARNING)==0)
      {
        
        //Keep track of the search criteria by resending the parameters sent by the client.
        //Helpfull to allow back and cancel buttons use.

        
        String dcmID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID , dcmID);
        String batchID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID , batchID);
        String archivingFlag =  (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG,archivingFlag);
        String date = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE,date);
        String batchType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE,batchType);
        String batchStatusTypeId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE,batchStatusTypeId);
        String sheetID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID , sheetID);
        String sheetSerialNumber = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO , sheetSerialNumber);
        String sheetPOSCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE , sheetPOSCode);
        String sheetSuperDealerCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE , sheetSuperDealerCode);
        String contractSimNo = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO , contractSimNo);

        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_RECHECK_CONTRACT_SYSTEM_WARNING)==0||
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_RECHECK_CONTRACT_SYSTEM_WARNING)==0)
        {
          String strContractId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID);
          boolean changeContractChecked = ContractDao.updateContractWarningChecked(strContractId,con);
          if(changeContractChecked) 
          {
              boolean deleteContractSystenWarning = ContractDao.deleteContractSystemWarning(strContractId,con);  
          }
        }

        ContractStatusTypeDto newContractStatusTypeDto = ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
        
        ContractModel contractModel = ContractDao.getContractBySimNo(contractSimNo, batchID , sheetID,con);

                
        if(contractModel != null)
        {         
          if(contractModel.getSheetId().compareTo(sheetID)==0)
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractModel);
            
            boolean doesContractHasPreviousWarning = ContractDao.isContractHasWarningInVerification(contractSimNo);
               Vector contractUserWarningModelVector = ContractDao.getActiveUserContractWarningByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
            ////Utility.logger.debug("the contractUserWarningModelVector statement  = " + (System.currentTimeMillis() - startTime));
            
            String automaticFlag = contractModel.getAutomaticFlag();
            
            String contractLastStatusId = contractModel.getContractLastStatusId();

            Vector contractSystemWarningModelVector = new Vector();
            if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_RECHECK_CONTRACT_SYSTEM_WARNING)==0||
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_RECHECK_CONTRACT_SYSTEM_WARNING)==0)
            {
              contractSystemWarningModelVector = ContractDao.validateContractInContractVerification(con,contractModel.getId(),"0",contractLastStatusId);
            }
            else
            {
              contractSystemWarningModelVector = ContractDao.validateContractInContractVerification(con,contractModel.getId(),automaticFlag,contractLastStatusId);
            }
           
            
            if (contractSystemWarningModelVector ==null)
            {
                          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, 
                            "Could not connect to the Inventory Database. Please contact your System Administrator");
            }
            
            
            Vector contractWarningsModelVector = ContractDao.getContractWarnings(contractModel.getId());
              ////Utility.logger.debug("the contractWarningsModelVector statement  = " + (System.currentTimeMillis() - startTime));
            
            HashMap warnings = new HashMap();
            warnings.put(AuthCallInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
            warnings.put(AuthCallInterfaceKey.OBJ_CONTRACT_WARNING, contractWarningsModelVector);
            if (contractSystemWarningModelVector!=null)
            {
              warnings.put(AuthCallInterfaceKey.OBJ_SYSTEM_WARNING, contractSystemWarningModelVector);
            }
            // add the vector to the datahasmap to display all the user warnings available 
            dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRACT_HAS_PREVIOUS_WARNING , new Boolean(doesContractHasPreviousWarning));
            dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRACT_WARNING, warnings);
            dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE, newContractStatusTypeDto);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, contractModel);
          }
          else
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, 
                            "This Contract Main Sim Number exists in sheet \""+
                            contractModel.getSheetSerialNo()+"\"");
          }
        }
        else
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "This Contract Main Sim Number does not exist.");
        }
        ////Utility.logger.debug("the if statement  = " + (System.currentTimeMillis() - startTime));
       
        SheetModel newSheetModel = SheetDao.verifySheet(sheetSerialNumber, sheetPOSCode, sheetSuperDealerCode,batchID,con);
       ////Utility.logger.debug("geting sheetModel = " + (System.currentTimeMillis() - startTime));
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , newSheetModel);
        
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_CONTRACT)==0 ||
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_UPDATE_CONTRACT)==0)
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
        String sheetID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID , sheetID);
        String sheetSerialNumber = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO , sheetSerialNumber);
        String sheetPOSCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE , sheetPOSCode);
        String sheetSuperDealerCode = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE , sheetSuperDealerCode);
        String phaseName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME);
        String contractId =(String)paramHashMap.get( ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID);  
        String archivingFlag = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG);
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG , archivingFlag);
        
        // Check if the applyed user warnings is a string or an array of strings.
        String[] applied_user_warnings = new String[0];
        Object o = paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
        if (o!=null && (!(o instanceof String )))
        {
          //////Utility.logger.debug("class name = "+ o.getClass().getName());
          
          applied_user_warnings = (String[])paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
/*
          for (int i = 0;i<applied_user_warnings.length;i++)
          {
            ////Utility.logger.debug("warning " + applied_user_warnings[i]);
          }
          */
          
        }
        else if (o!=null && (o instanceof String))
        {
          applied_user_warnings = new String[1];
          applied_user_warnings[0] = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);
        }

        String[] system_warnings = new String[0];
        o = paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_SYSTEM_WARNINGS);
        if (o!=null && (!(o instanceof String )))
        {
          //////Utility.logger.debug("class name = "+ o.getClass().getName());
          
          system_warnings = (String[])paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_SYSTEM_WARNINGS);
/*
          for (int i = 0;i<system_warnings.length;i++)
          {
            ////Utility.logger.debug("warning " + system_warnings[i]);
          }
  */        
        }
        else if (o!=null && (o instanceof String))
        {
          system_warnings = new String[1];
          system_warnings[0] = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_SYSTEM_WARNINGS);
        }

        
        //Vector contractSystemWarningModelVector = ContractDao.validateContractInContractVerification(contractId);
        /*Vector contractSystemWarningModelVector = new Vector();
        for (int sw = 0; sw < system_warnings.length; sw++) {
          contractSystemWarningModelVector.add(system_warnings[sw]);
        }

        if (contractSystemWarningModelVector ==null)
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP,"Could not connect to the Inventory Database");
        }*/
            
            
        String newStatus = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);

        //if (contractSystemWarningModelVector!=null)
        if (system_warnings !=null)
        {
          //ContractDao.updateContract(contractId, strUserID, sheetID, batchID, newStatus, applied_user_warnings, contractSystemWarningModelVector);
          ContractDao.updateContract(contractId, strUserID, sheetID, batchID, newStatus, applied_user_warnings, system_warnings);
        }
        SheetModel newSheetModel = SheetDao.verifySheet(sheetSerialNumber, sheetPOSCode, sheetSuperDealerCode,batchID,con);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , newSheetModel);
        if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_UPDATE_CONTRACT)==0) {
          ContractStatusTypeDto newContractStatusTypeDto = ContractDao.getContractStatusTypeByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
          ContractDto newContractDto = ContractDao.getSheetContracts(sheetID,con);
          Vector contractUserWarningModelVector = ContractDao.getActiveUserContractWarningByPhase(ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE,con);
            
          dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_USER_WARNING, contractUserWarningModelVector);
          dataHashMap.put(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE, newContractStatusTypeDto);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , newContractDto);
        }
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_SHEET_HISTORY)==0 )
      {
        String sheetSerialNum = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);
        Vector<SheetDto> sheetHistoryVector = SheetDao.getSheetHistory(sheetSerialNum,con);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetHistoryVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_CONTRACT_HISTORY_TELL_AUTH)==0 )
      {
        String contractMainSimNo = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);
        Vector contractHistoryUntilVerifyVector = ContractDao.getContractHistory(contractMainSimNo);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractHistoryUntilVerifyVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_CONTRACT_HISTORY_TELL_VERIFY)==0 )
      {
        String contractMainSimNo = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);
        Vector contractHistoryUntilVerifyVector = ContractDao.getContractHistory(contractMainSimNo);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractHistoryUntilVerifyVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_CONTRACT_HISTORY)==0 )
      {
        String contractMainSimNo = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);
        Vector contractHistoryVector = ContractDao.getContractHistory(contractMainSimNo);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractHistoryVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_SHEETS_WARNINGS_SCREEN)==0)
      {
        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        Vector phasesVector = newPhaseDTO.getPhases();
        HashMap sheetPhaseWarnings = new HashMap();
        for(int i=0; i<phasesVector.size(); i++)
        {
          PhaseModel newPhaseModel = (PhaseModel)phasesVector.elementAt(i);
          String strPhaseName = newPhaseModel.getPhaseName();
          Vector sheetUserWarningByPhaseVector = SheetDao.getSheetUserWarningByPhase(strPhaseName);
          sheetPhaseWarnings.put(strPhaseName, sheetUserWarningByPhaseVector);
        }
        Vector warningStatusVector = SheetDao.getWarningStatuses(con);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetPhaseWarnings);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_WARNINGS_STATUSES)==0)
      {
        String warningID = "";
        String warningStatusID = "";
        for(int j=0; j<paramHashMap.size(); j++)
        {
          String tempString = (String)paramHashMap.keySet().toArray()[j];
          if(tempString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
          {
            warningID = tempString.substring(
                                        tempString.lastIndexOf(
                                          ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7);
            warningStatusID = (String)paramHashMap.get(tempString);
            SheetDao.updateSheetWarningStatus(warningID, warningStatusID);
          }
        }
        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        Vector phasesVector = newPhaseDTO.getPhases();
        HashMap sheetPhaseWarnings = new HashMap();
        for(int i=0; i<phasesVector.size(); i++)
        {
          PhaseModel newPhaseModel = (PhaseModel)phasesVector.elementAt(i);
          String strPhaseName = newPhaseModel.getPhaseName();
          Vector sheetUserWarningByPhaseVector = SheetDao.getSheetUserWarningByPhase(strPhaseName);
          sheetPhaseWarnings.put(strPhaseName, sheetUserWarningByPhaseVector);
        }
        Vector warningStatusVector = SheetDao.getWarningStatuses(con);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetPhaseWarnings);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
      }

      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_EDIT_SHEET_WARNING)==0)
      {
        //changed for jboss
        //SheetWarningTypeCMPHome sheetWarningTypeCMPHome = (SheetWarningTypeCMPHome)Utility.getEJBHome("SheetWarningTypeCMP", 
        //          "com.mobinil.sds.core.system.cr.sheet.dao.cmp.SheetWarningTypeCMPHome");
        //SheetWarningTypeCMP sheetWarningTypeCMPRemote = sheetWarningTypeCMPHome.findByPrimaryKey(
        //                            new Long((String)paramHashMap.get(
        //                                ContractRegistrationInterfaceKey.CONTROL_HIDDEN_WARNING_ID)));

        String strSheetWarningId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_WARNING_ID);

        SheetWarningModel newSheetWarningModel = SheetDao.getSheetWarningType(strSheetWarningId);
        //SheetWarningModel newSheetWarningModel = new SheetWarningModel();
        //newSheetWarningModel.setSheetWarningID(sheetWarningTypeCMPRemote.getSheet_warning_type_id().toString());
        //newSheetWarningModel.setSheetWarningName(sheetWarningTypeCMPRemote.getSheet_warning_type_name());
        //newSheetWarningModel.setSheetwarningDesc(sheetWarningTypeCMPRemote.getSheet_warning_type_desc());
        //newSheetWarningModel.setSheetPhaseID(sheetWarningTypeCMPRemote.getPhase_id().toString());
        //newSheetWarningModel.setSheetWarningTypeID(sheetWarningTypeCMPRemote.getWarning_type_id().toString());
        //newSheetWarningModel.setSheetwarningStatusID(sheetWarningTypeCMPRemote.getWarning_status_id().toString());

        Vector warningStatusVector = SheetDao.getWarningStatuses(con);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newSheetWarningModel);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_WARNING)==0)
      {
        String warningID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_WARNING_ID);
        String warningName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_WARNING_NAME);
        String warningDesc = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_WARNING_DESC);
        String warningStatusID = "";
        for(int j=0; j<paramHashMap.size(); j++)
        {
          String tempString = (String)paramHashMap.keySet().toArray()[j];
          if(tempString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
          {
            warningStatusID = (String)paramHashMap.get(tempString);
          }
        }

        SheetWarningModel newSheetWarningModel = new SheetWarningModel();
        newSheetWarningModel.setSheetWarningName(warningName);
        newSheetWarningModel.setSheetwarningDesc(warningDesc);
        newSheetWarningModel.setSheetwarningStatusID(warningStatusID);

        //SheetWarningTypeCMPHome sheetWarningTypeCMPHome = (SheetWarningTypeCMPHome)Utility.getEJBHome("SheetWarningTypeCMP", 
        //          "com.mobinil.sds.core.system.cr.sheet.dao.cmp.SheetWarningTypeCMPHome");
        //SheetWarningTypeCMP sheetWarningTypeCMPRemote = sheetWarningTypeCMPHome.findByPrimaryKey(new Long(warningID));
        //sheetWarningTypeCMPRemote.updateSheetWarning(newSheetWarningModel);
        SheetDao.updateSheetWarningType(warningID,warningName,warningDesc,warningStatusID);
        

        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        Vector phasesVector = newPhaseDTO.getPhases();
        HashMap sheetPhaseWarnings = new HashMap();
        for(int i=0; i<phasesVector.size(); i++)
        {
          PhaseModel newPhaseModel = (PhaseModel)phasesVector.elementAt(i);
          String strPhaseName = newPhaseModel.getPhaseName();
          sheetPhaseWarnings.put(strPhaseName, SheetDao.getSheetUserWarningByPhase(strPhaseName));
        }
        Vector warningStatusVector = SheetDao.getWarningStatuses(con);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetPhaseWarnings);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_NEW_SHEET_WARNING)==0)
      {
        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, newPhaseDTO);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADD_SHEET_WARNING)==0)
      {
        
        Long lWarningID = Utility.getSequenceNextVal(con, "SEQ_CR_SHEET_WARNING_TYPE");
        String WarningID = lWarningID.toString();
        String warningName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_WARNING_NAME);
        String warningDesc = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_WARNING_DESC);
        String warningPhaseID = "";
        for(int j=0; j<paramHashMap.size(); j++)
        {
          String tempString = (String)paramHashMap.keySet().toArray()[j];
          if(tempString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
          {
            warningPhaseID = (String)paramHashMap.get(tempString);
          }
        }
        SheetWarningModel newSheetWarningModel = new SheetWarningModel();
        newSheetWarningModel.setSheetWarningID(WarningID);
        newSheetWarningModel.setSheetWarningName(warningName);
        newSheetWarningModel.setSheetwarningDesc(warningDesc);
        newSheetWarningModel.setSheetPhaseID(warningPhaseID);

        //SheetWarningTypeCMPHome sheetWarningTypeCMPHome = (SheetWarningTypeCMPHome)Utility.getEJBHome("SheetWarningTypeCMP", 
        //          "com.mobinil.sds.core.system.cr.sheet.dao.cmp.SheetWarningTypeCMPHome");
        //SheetWarningTypeCMP sheetWarningTypeCMPRemote = sheetWarningTypeCMPHome.create(newSheetWarningModel);

        SheetDao.insertSheetWarningType(con,WarningID,warningName,warningDesc,warningPhaseID);
        

        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        Vector phasesVector = newPhaseDTO.getPhases();
        HashMap sheetPhaseWarnings = new HashMap();
        for(int i=0; i<phasesVector.size(); i++)
        {
          PhaseModel newPhaseModel = (PhaseModel)phasesVector.elementAt(i);
          String strPhaseName = newPhaseModel.getPhaseName();
          sheetPhaseWarnings.put(strPhaseName, SheetDao.getSheetUserWarningByPhase(strPhaseName));
        }
        Vector warningStatusVector = SheetDao.getWarningStatuses(con);

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetPhaseWarnings);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
      }

      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_CONTRACTS_WARNINGS_SCREEN)==0)
      {
        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        Vector phasesVector = newPhaseDTO.getPhases();
        HashMap contractPhaseWarnings = new HashMap();
        for(int i=0; i<phasesVector.size(); i++)
        {
          PhaseModel newPhaseModel = (PhaseModel)phasesVector.elementAt(i);
          String strPhaseName = newPhaseModel.getPhaseName();
          contractPhaseWarnings.put(strPhaseName, ContractDao.getUserContractWarningByPhase(strPhaseName));
        }
        Vector warningStatusVector = SheetDao.getWarningStatuses(con);
        Vector warningSuggestedStatusVector = SheetDao.getWarningSuggestedStatuses(con);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractPhaseWarnings);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
        dataHashMap.put(ContractRegistrationInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningSuggestedStatusVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_CONTRACTS_SYSTEM_WARNINGS_SCREEN)==0)
      {
        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        Vector phasesVector = newPhaseDTO.getPhases();
       
        HashMap contractPhaseWarnings = new HashMap();
        for(int i=0; i<phasesVector.size(); i++)
        {
          PhaseModel newPhaseModel = (PhaseModel)phasesVector.elementAt(i);
          String strPhaseName = newPhaseModel.getPhaseName();
          contractPhaseWarnings.put(strPhaseName, ContractDao.getSystemContractWarningByPhase(strPhaseName));
        }
        Vector warningStatusVector = SheetDao.getWarningStatuses(con);
        Vector warningSuggestedStatusVector = SheetDao.getWarningSuggestedStatuses(con);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractPhaseWarnings);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
        dataHashMap.put(ContractRegistrationInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningSuggestedStatusVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_CONTRACT_WARNINGS_STATUSES)==0)
      {
        String warningID = "";
        String warningStatusID = "";
        String warningSuggestedStatusID = "";
        for(int j=0; j<paramHashMap.size(); j++)
        {
          String tempString = (String)paramHashMap.keySet().toArray()[j];
          if(tempString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
          {
            warningID = tempString.substring(
                                        tempString.lastIndexOf(
                                          ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7);
            warningStatusID = (String)paramHashMap.get(tempString);
            warningSuggestedStatusID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_WARNING_SUGGESTED_STATUS+warningID);
            ContractDao.updateContractWarningStatus(warningID, warningStatusID,warningSuggestedStatusID,con);
          }
        }
        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        Vector phasesVector = newPhaseDTO.getPhases();
        HashMap contractPhaseWarnings = new HashMap();
        for(int i=0; i<phasesVector.size(); i++)
        {
          PhaseModel newPhaseModel = (PhaseModel)phasesVector.elementAt(i);
          String strPhaseName = newPhaseModel.getPhaseName();
          CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_CONTRACT_WARNINGS+strPhaseName);
          CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_ACTIVE_CONTRACT_WARNINGS+strPhaseName);
          contractPhaseWarnings.put(strPhaseName, ContractDao.getUserContractWarningByPhase(strPhaseName));
        }
        Vector warningStatusVector = SheetDao.getWarningStatuses(con);
        Vector warningSuggestedStatusVector = SheetDao.getWarningSuggestedStatuses(con);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractPhaseWarnings);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
        dataHashMap.put(ContractRegistrationInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningSuggestedStatusVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_CONTRACT_SYSTEM_WARNINGS_STATUSES)==0)
      {
        String warningID = "";
        String warningStatusID = "";
        String warningSuggestedStatusID = "";
        for(int j=0; j<paramHashMap.size(); j++)
        {
          String tempString = (String)paramHashMap.keySet().toArray()[j];
          if(tempString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
          {
            warningID = tempString.substring(
                                        tempString.lastIndexOf(
                                          ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7);
            warningStatusID = (String)paramHashMap.get(tempString);
            warningSuggestedStatusID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_WARNING_SUGGESTED_STATUS+warningID);
            ContractDao.updateContractWarningStatus(warningID, warningStatusID,warningSuggestedStatusID,con);
          }
        }
        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        Vector phasesVector = newPhaseDTO.getPhases();
        HashMap contractPhaseWarnings = new HashMap();
        for(int i=0; i<phasesVector.size(); i++)
        {
          PhaseModel newPhaseModel = (PhaseModel)phasesVector.elementAt(i);
          String strPhaseName = newPhaseModel.getPhaseName();
          CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_CONTRACT_SYSTEM_WARNINGS+strPhaseName);
          CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_ACTIVE_CONTRACT_WARNINGS+strPhaseName);
          contractPhaseWarnings.put(strPhaseName, ContractDao.getSystemContractWarningByPhase(strPhaseName));
        }
        Vector warningStatusVector = SheetDao.getWarningStatuses(con);
        Vector warningSuggestedStatusVector = SheetDao.getWarningSuggestedStatuses(con);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractPhaseWarnings);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
        dataHashMap.put(ContractRegistrationInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningSuggestedStatusVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_EDIT_CONTRACT_WARNING)==0)
      {
        /*ContractWarningTypeCMPHome contractWarningTypeCMPHome = (ContractWarningTypeCMPHome)Utility.getEJBHome("ContractWarningTypeCMP", 
                  "com.mobinil.sds.core.system.cr.contract.dao.cmp.ContractWarningTypeCMPHome");
        ContractWarningTypeCMP contractWarningTypeCMPRemote = contractWarningTypeCMPHome.findByPrimaryKey(
                                    new Long((String)paramHashMap.get(
                                        ContractRegistrationInterfaceKey.CONTROL_HIDDEN_WARNING_ID)));

        ContractWarningModel newContractWarningModel = new ContractWarningModel();
        newContractWarningModel.setContractWarningID(contractWarningTypeCMPRemote.getContract_warning_type_id().toString());
        newContractWarningModel.setContractWarningName(contractWarningTypeCMPRemote.getContract_warning_type_name());
        newContractWarningModel.setContractwarningDesc(contractWarningTypeCMPRemote.getContract_warning_type_desc());
        newContractWarningModel.setContractPhaseID(contractWarningTypeCMPRemote.getPhase_id().toString());
        newContractWarningModel.setContractWarningTypeID(contractWarningTypeCMPRemote.getWarning_type_id().toString());
        newContractWarningModel.setContractwarningStatusID(contractWarningTypeCMPRemote.getWarning_status_id().toString());

        newContractWarningModel.setContractwarningSuggestedStatusID(contractWarningTypeCMPRemote.getWarning_suggested_status_id().toString());
        */
        
        ContractWarningModel newContractWarningModel = ContractDao.getVwCrContractWarningType(con,(String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_WARNING_ID));
        
        Vector warningStatusVector = SheetDao.getWarningStatuses(con);
        Vector warningSuggestedStatusVector = SheetDao.getWarningSuggestedStatuses(con);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newContractWarningModel);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
        dataHashMap.put(ContractRegistrationInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningSuggestedStatusVector);
        
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_CONTRACT_WARNING)==0)
      {
        
        String warningID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_WARNING_ID);
        String warningName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_WARNING_NAME);
        String warningDesc = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_WARNING_DESC);
        String warningSuggestedStatusId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_WARNING_SUGGESTED_STATUS);
        Utility.logger.debug("Suggested Status Id : "+warningSuggestedStatusId);
        String warningStatusID = "";
        for(int j=0; j<paramHashMap.size(); j++)
        {
          String tempString = (String)paramHashMap.keySet().toArray()[j];
          Utility.logger.debug("HashMap keys : "+tempString);
          if(tempString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
          {
            warningStatusID = (String)paramHashMap.get(tempString);
          }
        }

        ContractWarningModel newContractWarningModel = new ContractWarningModel();
        newContractWarningModel.setContractWarningName(warningName);
        newContractWarningModel.setContractwarningDesc(warningDesc);
        newContractWarningModel.setContractwarningStatusID(warningStatusID);
        newContractWarningModel.setContractwarningSuggestedStatusID(warningSuggestedStatusId);
        
        //ContractWarningTypeCMPHome contractWarningTypeCMPHome = (ContractWarningTypeCMPHome)Utility.getEJBHome("ContractWarningTypeCMP", 
        //          "com.mobinil.sds.core.system.cr.contract.dao.cmp.ContractWarningTypeCMPHome");
        //ContractWarningTypeCMP contractWarningTypeCMPRemote = contractWarningTypeCMPHome.findByPrimaryKey(new Long(warningID));
        //contractWarningTypeCMPRemote.updateContractWarning(newContractWarningModel);
        ContractDao.updateVwCrContractWarningType(con,warningID,warningName,warningDesc,warningStatusID,warningSuggestedStatusId);
        
        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        Vector phasesVector = newPhaseDTO.getPhases();
        HashMap contractPhaseWarnings = new HashMap();
        for(int i=0; i<phasesVector.size(); i++)
        {
          PhaseModel newPhaseModel = (PhaseModel)phasesVector.elementAt(i);
          String strPhaseName = newPhaseModel.getPhaseName();
          CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_CONTRACT_WARNINGS+strPhaseName);
          CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_ACTIVE_CONTRACT_WARNINGS+strPhaseName);
          contractPhaseWarnings.put(strPhaseName, ContractDao.getUserContractWarningByPhase(strPhaseName));
        }
        Vector warningStatusVector = SheetDao.getWarningStatuses(con);
        Vector warningSuggestedStatusVector = SheetDao.getWarningSuggestedStatuses(con);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractPhaseWarnings);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
        dataHashMap.put(ContractRegistrationInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningSuggestedStatusVector);
        
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_NEW_CONTRACT_WARNING)==0)
      {
        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, newPhaseDTO);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADD_CONTRACT_WARNING)==0)
      {
        
        Long lWarningID = Utility.getSequenceNextVal(con, "SEQ_CR_CONTRACT_WARNING_TYPE");
        String WarningID = lWarningID.toString();
        String warningName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_WARNING_NAME);
        String warningDesc = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_WARNING_DESC);
        String warningPhaseID = "";
        for(int j=0; j<paramHashMap.size(); j++)
        {
          String tempString = (String)paramHashMap.keySet().toArray()[j];
          if(tempString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
          {
            warningPhaseID = (String)paramHashMap.get(tempString);
          }
        }
        ContractWarningModel newContractWarningModel = new ContractWarningModel();
        newContractWarningModel.setContractWarningID(WarningID);
        newContractWarningModel.setContractWarningName(warningName);
        newContractWarningModel.setContractwarningDesc(warningDesc);
        newContractWarningModel.setContractPhaseID(warningPhaseID);

        //ContractWarningTypeCMPHome contractWarningTypeCMPHome = (ContractWarningTypeCMPHome)Utility.getEJBHome("ContractWarningTypeCMP", 
        //          "com.mobinil.sds.core.system.cr.contract.dao.cmp.ContractWarningTypeCMPHome");
        //ContractWarningTypeCMP contractWarningTypeCMPRemote = contractWarningTypeCMPHome.create(newContractWarningModel);
        ContractDao.insertVwCrContractWarningType(con,WarningID,warningName,warningDesc,"1","1",warningPhaseID,"1");
        

        PhaseDTO newPhaseDTO = PhaseDAO.getPhases();
        Vector phasesVector = newPhaseDTO.getPhases();
        HashMap contractPhaseWarnings = new HashMap();
        for(int i=0; i<phasesVector.size(); i++)
        {
          PhaseModel newPhaseModel = (PhaseModel)phasesVector.elementAt(i);
          String strPhaseName = newPhaseModel.getPhaseName();
          CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_CONTRACT_WARNINGS+strPhaseName);
          CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_ACTIVE_CONTRACT_WARNINGS+strPhaseName);
          contractPhaseWarnings.put(strPhaseName, ContractDao.getUserContractWarningByPhase(strPhaseName));
        }
        Vector warningStatusVector = SheetDao.getWarningStatuses(con);
        Vector warningSuggestedStatusVector = SheetDao.getWarningSuggestedStatuses(con);
        
        dataHashMap.put(ContractRegistrationInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningSuggestedStatusVector);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, contractPhaseWarnings);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, warningStatusVector);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_USER_DCM_SCREEN)==0||
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_USER_DCM_SCREEN_FRANCHISE)==0 ||
              
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_USER_DCM_SCREEN_FRANCHISE)==0  ||
              // complemantry added
              action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_USER_DCM_SCREEN_COMPLEMANTRY)==0)
             
      
      {
        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_USER_DCM_SCREEN_FRANCHISE)==0)
        {
          strChannelId = "2";
        }
        
        if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_USER_DCM_SCREEN_COMPLEMANTRY)==0)
        {
          strChannelId = "3";
        }


        ///Connection m_conSDSConnection = Utility.getConnection();
        //Vector  usersVector = UserDAO.getContractRegisterationUsersList(m_conSDSConnection);
        Vector  usersVector = UserDAO.getSystemUsersList(con,"PERSON_FULL_NAME");
        HashMap additionalDataHashMap = UserDAO.getAdditionalData(con,strChannelId,DCMDao.DCM_LEVEL_DISTRIBUTOR);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalDataHashMap);
        //Utility.closeConnection(m_conSDSConnection);
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_USER_DCM)==0)
      {
        //Connection m_conSDSConnection = Utility.getConnection();
        int userID = new Integer((String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID)).intValue();

        CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_DCM_LIST_CONTRACT_REGISTRATION+userID+"_"+strChannelId);
        
        if(UserDAO.deleteContractRegisterationUserDCMs(con, userID , strChannelId))
        {
          for(int j=0; j<paramHashMap.size(); j++)
          {
            String tempString = (String)paramHashMap.keySet().toArray()[j];
            if(tempString.startsWith(UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX))
            {
              int nDCMID = new Integer(tempString.substring(
                                    tempString.lastIndexOf(
                                      UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX)+9)).intValue();
              //CRUserDCMAccessCMPHome newCRUserDCMAccessCMPHome = 
              //  (CRUserDCMAccessCMPHome)Utility.getEJBHome("CRUserDCMAccessCMP", "com.mobinil.sds.core.system.sa.users.dao.cmp.CRUserDCMAccessCMPHome");
              //CRUserDCMAccessCMP newCRUserDCMAccessCMPRemote = newCRUserDCMAccessCMPHome.create(new UserDCMModel(userID, nDCMID));
              UserDAO.insertCrUserDcmAccess(con,userID,nDCMID);
            }
          }
          //Vector  usersVector = UserDAO.getContractRegisterationUsersList(m_conSDSConnection);
          Vector  usersVector = UserDAO.getSystemUsersList(con,"PERSON_FULL_NAME");
          HashMap additionalDataHashMap = UserDAO.getAdditionalData(con,strChannelId,DCMDao.DCM_LEVEL_DISTRIBUTOR);
        
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalDataHashMap);
        }
        
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_LIST_SHEET_TYPES)==0)
      {
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, SheetDao.getSheetTypes());
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_TYPES_STATUSES)==0)
      {
        CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_SHEET_TYPES);
        CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_BATCH_TYPE);
        String sheetTypeID = "";
        String sheetTypeStatusID = "";
        for(int j=0; j<paramHashMap.size(); j++)
        {
          String tempString = (String)paramHashMap.keySet().toArray()[j];
          if(tempString.startsWith(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
          {
            sheetTypeID = tempString.substring(
                                        tempString.lastIndexOf(
                                          ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7);
            sheetTypeStatusID = (String)paramHashMap.get(tempString);
            //SheetTypeCMPHome newSheetTypeCMPHome = 
            //  (SheetTypeCMPHome)Utility.getEJBHome("SheetTypeCMP", "com.mobinil.sds.core.system.cr.sheet.dao.cmp.SheetTypeCMPHome");
            //SheetTypeCMP newSheetTypeCMPRemote = newSheetTypeCMPHome.findByPrimaryKey(new Long(sheetTypeID));
            //newSheetTypeCMPRemote.setSheet_type_status_id(new Long(sheetTypeStatusID));
            SheetDao.updateSheetType(sheetTypeID,sheetTypeStatusID);
          }
        }
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, SheetDao.getSheetTypes());
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_NEW_SHEET_TYPE)==0)
      {
      
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_EDIT_SHEET_TYPE)==0)
      {
      CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_SHEET_TYPES);
      CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_BATCH_TYPE);
        String sheetTypeID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_TYPE_ID);
        /*SheetTypeCMPHome newSheetTypeCMPHome = 
          (SheetTypeCMPHome)Utility.getEJBHome("SheetTypeCMP", "com.mobinil.sds.core.system.cr.sheet.dao.cmp.SheetTypeCMPHome");
        SheetTypeCMP newSheetTypeCMPRemote = newSheetTypeCMPHome.findByPrimaryKey(new Long(sheetTypeID));
        SheetTypeModel newSheetTypeModel = new SheetTypeModel();
        newSheetTypeModel.setSheetTypeId(newSheetTypeCMPRemote.getSheet_type_id().toString());
        newSheetTypeModel.setSheetTypeName(newSheetTypeCMPRemote.getSheet_type_name());
        newSheetTypeModel.setSheetTypeDesc(newSheetTypeCMPRemote.getSheet_type_desc());
        newSheetTypeModel.setSheetTypeStatusId(newSheetTypeCMPRemote.getSheet_type_status_id().toString());
        newSheetTypeModel.setSheetAuthPercentage(newSheetTypeCMPRemote.getSheet_auth_percentage().doubleValue());
        newSheetTypeModel.setSheetPOSQuestionFlag(newSheetTypeCMPRemote.getPos_question().toString());
        newSheetTypeModel.setSheetDiscardUnrealUnreachableFlag(newSheetTypeCMPRemote.getDiscard_unreal_unreachable().toString());
        */
        SheetTypeModel newSheetTypeModel = SheetDao.getCrSheetType(sheetTypeID);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newSheetTypeModel);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, SheetDao.getSheetTypeStatuses());
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADD_SHEET_TYPE)==0)
      {
      CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_SHEET_TYPES);
      CachEngine.removeObject(ContractRegistrationInterfaceKey.CACH_OBJ_BATCH_TYPE);
        
        Long lSheetTypeID = Utility.getSequenceNextVal(con, "SEQ_CR_SHEET_TYPE_ID");
        String sheetTypeName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_TYPE_NAME);
        String sheetTypeDesc = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_TYPE_DESC);
        String sheetTypeAuthPercent = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_TYPE_AUTH_PERCENT);
        String sheetPOSQuestionFlag = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_CHECK_BOX_POS_QUESTION);
        String sheetDiscardUnrealUnreachableFlag = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_CHECK_BOX_DISCARD_UNREAL_UNREACHABLE);

        SheetTypeModel newSheetTypeModel = new SheetTypeModel();
        newSheetTypeModel.setSheetTypeId(lSheetTypeID.toString());
        newSheetTypeModel.setSheetTypeName(sheetTypeName);
        newSheetTypeModel.setSheetTypeDesc(sheetTypeDesc);
        newSheetTypeModel.setSheetTypeStatusId("1");
        newSheetTypeModel.setSheetAuthPercentage(new Double(sheetTypeAuthPercent).doubleValue());
        if(sheetPOSQuestionFlag != null)
        {
          newSheetTypeModel.setSheetPOSQuestionFlag(sheetPOSQuestionFlag);
        }
        else
        {
          newSheetTypeModel.setSheetPOSQuestionFlag("0");
        }
        if(sheetDiscardUnrealUnreachableFlag != null)
        {
          newSheetTypeModel.setSheetDiscardUnrealUnreachableFlag(sheetDiscardUnrealUnreachableFlag);
        }
        else
        {
          newSheetTypeModel.setSheetDiscardUnrealUnreachableFlag("0");
        }
        //SheetTypeCMPHome newSheetTypeCMPHome = 
        //  (SheetTypeCMPHome)Utility.getEJBHome("SheetTypeCMP", "com.mobinil.sds.core.system.cr.sheet.dao.cmp.SheetTypeCMPHome");
        //SheetTypeCMP newSheetTypeCMPRemote = null;
        try 
        {
          //newSheetTypeCMPRemote = newSheetTypeCMPHome.create(newSheetTypeModel);
          SheetDao.insertSheetType(con,newSheetTypeModel);
        } 
        catch (Exception ex) 
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Another Sheet Type with the same name already exists");
        } 
        finally 
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, SheetDao.getSheetTypes());
        }
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_UPDATE_SHEET_SERIAL)==0)
      {
      
        DCMDto dcmDto = DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
        HashMap additionalCollection = new HashMap();
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
        
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_SERIAL_ASSIGNMETN)==0)
      {       
        String[] sheetDCMRowID = new String[0];
        Object o = paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
        if (o!=null && (!(o instanceof String )))
        {          
          sheetDCMRowID = (String[])paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);         
        }
        else if (o!=null && (o instanceof String))
        {
          sheetDCMRowID = new String[1];
          sheetDCMRowID[0] = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
        }

        String[] sheetDCMId = new String[0];
         o = paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        if (o!=null && (!(o instanceof String )))
        {          
          sheetDCMId = (String[])paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);         
        }
        else if (o!=null && (o instanceof String))
        {
          sheetDCMId = new String[1];
          sheetDCMId[0] = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        }

        //////Utility.logger.debug("sheet dcm id = " + sheetDCMId.length);
        //////Utility.logger.debug("sheet serial id = " + sheetDCMRowID.length);

        SheetDao.updateSheetSerialToDCM(sheetDCMRowID , sheetDCMId);
        
        DCMDto dcmDto = DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
        HashMap additionalCollection = new HashMap();
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);        
      }            
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_SHEET_SERIAL)==0)
      {
        String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);        
        String inputType = (String)paramHashMap.get( ContractRegistrationInterfaceKey.CONTROL_SELECT_SHEET_ASSIGN_TYPE);
        
        String inputSerial ="";
        String inputFrom ="";
        String inputTo="";        

        inputSerial = (String)paramHashMap.get( ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_INPUT);
        inputFrom = (String)paramHashMap.get( ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_FROM);
        inputTo = (String)paramHashMap.get( ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_TO);
  
        if (dcmId==null) 
        dcmId ="";

        if (inputSerial==null) 
        inputSerial ="";
        if (inputFrom==null) 
        inputFrom ="";
        if (inputTo==null) 
        inputTo ="";

        
                
        DCMDto dcmDto = DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
        HashMap additionalCollection = new HashMap();
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJL_SHEET_SERIAL_INPUT,inputSerial);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SHEET_SERIAL_FROM,inputFrom);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SHEET_SERIAL_TO,inputTo);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SELECTED_ASSIGN_TYPE,inputType);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SELECTED_DCM_ID,dcmId);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);

        if (inputType.compareTo(ContractRegistrationInterfaceKey.SHEET_INPUT_TYPE_ASSIGN_SHEET)==0)
        {
        
          SheetDCMModel sheetDCMModel = SheetDao.getSheetSerialDCM(inputSerial);
          
          Vector sheetDCMModelVector = new Vector();
          if (sheetDCMModel !=null)
          {
            sheetDCMModelVector.add(sheetDCMModel);
          }
          else
          {
            
            dataHashMap.put( ContractRegistrationInterfaceKey.SERVER_MESSAGE ,ContractRegistrationInterfaceKey.SHEET_ASSIGNMENT_SHEET_DOES_NOT_EXIST_ERROR_MESSAGE);
          }
          
          dataHashMap.put( ContractRegistrationInterfaceKey.SHEET_DCM_ASSIGNMENT_VECTOR , sheetDCMModelVector);
        }
        else if (inputType.compareTo(ContractRegistrationInterfaceKey.RANGE_INPUT_TYPE_ASSIGN_SHEET)==0)
        {
        
           Vector sheetDCMModelVector = SheetDao.getSheetRangeSerialToDCM(inputFrom, inputTo);
          dataHashMap.put( ContractRegistrationInterfaceKey.SHEET_DCM_ASSIGNMENT_VECTOR , sheetDCMModelVector);
        }
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHOW_CREATE_SHEET_SERIAL)==0)
      {
      
        DCMDto dcmDto = DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
        HashMap additionalCollection = new HashMap();
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);
        
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SELECT_SHEET_SERIAL_TO_SET_PERCENTAGE)==0)
      {
        
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHEET_SERIAL_LOCAL_AUTH_PERCENTAGE_LIST)==0)
      {
          Vector vecSheetModel = new Vector();
          String sheetSelectType = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_SHEET_ASSIGN_TYPE);
          String batchId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_BATCH_ID_INPUT);
          if(sheetSelectType.compareTo(ContractRegistrationInterfaceKey.SHEET_INPUT_TYPE_ASSIGN_SHEET) == 0) 
          {
              String sheetSerial = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_INPUT);
              SheetModel sheetModel = SheetDao.getSheetBySerialNumberAndBatchId(batchId,sheetSerial);
                if(sheetModel != null)
                {
                    vecSheetModel.add(sheetModel);
                }
          }
          else if(sheetSelectType.compareTo(ContractRegistrationInterfaceKey.RANGE_INPUT_TYPE_ASSIGN_SHEET) == 0) 
          {
              String fromSheetSerial = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_FROM);
              int intFromSheetSerial = Integer.parseInt(fromSheetSerial);
              String toSheetSerial = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_TO);
              int intToSheetSerial = Integer.parseInt(toSheetSerial);
              for(int i=intFromSheetSerial; i<intToSheetSerial;i++ ) 
              {
              String curSheetSerial = ""+i;
              Utility.logger.debug(curSheetSerial);
              SheetModel sheetModel = SheetDao.getSheetBySerialNumberAndBatchId(batchId,curSheetSerial);
                if(sheetModel != null)
                {
                    vecSheetModel.add(sheetModel);
                }                
              }
          }
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION , vecSheetModel);    
        dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_TEXT_BATCH_ID_INPUT , batchId);    
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_SAVE_SHEET_LOCAL_PERCENTAGE)==0)
      {
      String batchId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_BATCH_ID_INPUT);
      for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              if(tempStatusString.startsWith(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_LOCAL_PERCENTAGE))
              {
              String sheetSerialNumber = tempStatusString.substring((ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_LOCAL_PERCENTAGE).length()+1);
              String keyValue = (String)paramHashMap.get(tempStatusString);
              int inttmSheetLocalAuthPercentage = Integer.parseInt(keyValue); 
              SheetDao.updateSheetLocalAuthPercentage(batchId, sheetSerialNumber , inttmSheetLocalAuthPercentage);
              }
            }
      }
      else if (action.compareTo(ContractRegistrationInterfaceKey.ACTION_CREATE_SHEET_SERIAL)==0)
      {
        String dcmId = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);        
        String inputType = (String)paramHashMap.get( ContractRegistrationInterfaceKey.CONTROL_SELECT_SHEET_ASSIGN_TYPE);
        
        String inputSerial ="";
        String inputFrom ="";
        String inputTo="";

        //////Utility.logger.debug("dcm id= " + dcmId);

        inputSerial = (String)paramHashMap.get( ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_INPUT);
        inputFrom = (String)paramHashMap.get( ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_FROM);
        inputTo = (String)paramHashMap.get( ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_TO);
  
        if (dcmId==null) 
        dcmId ="";

        if (inputSerial==null) 
        inputSerial ="";
        if (inputFrom==null) 
        inputFrom ="";
        if (inputTo==null) 
        inputTo ="";

        
                
        DCMDto dcmDto = DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
        HashMap additionalCollection = new HashMap();
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_DCM_DTO,dcmDto);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJL_SHEET_SERIAL_INPUT,inputSerial);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SHEET_SERIAL_FROM,inputFrom);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SHEET_SERIAL_TO,inputTo);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SELECTED_ASSIGN_TYPE,inputType);
        additionalCollection.put(ContractRegistrationInterfaceKey.OBJ_SELECTED_DCM_ID,dcmId);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , additionalCollection);

        if (inputType.compareTo(ContractRegistrationInterfaceKey.SHEET_INPUT_TYPE_ASSIGN_SHEET)==0)
        {
          String message = SheetDao.assignSheetSerialToDCM(inputSerial,dcmId);
          dataHashMap.put( ContractRegistrationInterfaceKey.SERVER_MESSAGE , message);
        }
        else if (inputType.compareTo(ContractRegistrationInterfaceKey.RANGE_INPUT_TYPE_ASSIGN_SHEET)==0)
        {
          String message = SheetDao.assignSheetRangeSerialToDCM(inputFrom, inputTo,dcmId);
          dataHashMap.put( ContractRegistrationInterfaceKey.SERVER_MESSAGE , message);
        }
      }
      
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_TYPE)==0)
      {
        
        String sheetTypeID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_TYPE_ID);
        String sheetTypeName = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_TYPE_NAME);
        String sheetTypeDesc = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_TYPE_DESC);
        String sheetTypeStatusID = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+sheetTypeID);
        String sheetTypeAuthPercent = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_TYPE_AUTH_PERCENT);
        String sheetPOSQuestionFlag = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_CHECK_BOX_POS_QUESTION);
        String sheetDiscardUnrealUnreachableFlag = (String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_CHECK_BOX_DISCARD_UNREAL_UNREACHABLE);

        SheetTypeModel newSheetTypeModel = new SheetTypeModel();
        newSheetTypeModel.setSheetTypeId(sheetTypeID);
        newSheetTypeModel.setSheetTypeName(sheetTypeName);
        newSheetTypeModel.setSheetTypeDesc(sheetTypeDesc);
        newSheetTypeModel.setSheetTypeStatusId(sheetTypeStatusID);
        newSheetTypeModel.setSheetAuthPercentage(new Double(sheetTypeAuthPercent).doubleValue());
        if(sheetPOSQuestionFlag != null)
        {
          newSheetTypeModel.setSheetPOSQuestionFlag(sheetPOSQuestionFlag);
        }
        else
        {
          newSheetTypeModel.setSheetPOSQuestionFlag("0");
        }
        if(sheetDiscardUnrealUnreachableFlag != null)
        {
          newSheetTypeModel.setSheetDiscardUnrealUnreachableFlag(sheetDiscardUnrealUnreachableFlag);
        }
        else
        {
          newSheetTypeModel.setSheetDiscardUnrealUnreachableFlag("0");
        }
        //SheetTypeCMPHome newSheetTypeCMPHome = 
        //  (SheetTypeCMPHome)Utility.getEJBHome("SheetTypeCMP", "com.mobinil.sds.core.system.cr.sheet.dao.cmp.SheetTypeCMPHome");
        //SheetTypeCMP newSheetTypeCMPRemote = newSheetTypeCMPHome.findByPrimaryKey(new Long(sheetTypeID));
        try
        {
          //newSheetTypeCMPRemote.updateSheetType(newSheetTypeModel);
          SheetDao.updateSheetType(con,newSheetTypeModel);
        } 
        catch (Exception ex) 
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Another Sheet Type with the same name already exists");
        } 
        finally 
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, SheetDao.getSheetTypes());
        }
        
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHEET_PERCENTAGE_IMPORT)==0) 
      {
          Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("3");
          dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector); 
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_SHEET_PERCENTAGE_IMPORT_PROCESS)==0) 
      {
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_DCM_CHANGE_STATUS_IMPORT)==0) 
      {
          Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("4");
          dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector); 
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_DCM_CHANGE_STATUS_IMPORT_PROCESS)==0) 
      {
      }else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_CHANGE_BATCH_DATE)==0){
    	 // strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			//dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
		     dataHashMap.put(ContractRegistrationInterfaceKey.INPUT_HIDDEN_MESSAGE, "");
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_CHANGE_BATCH_DATE)==0) 
      {
    	    strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
			
			String msg = "";
			String closedBatchIDList = "";
    	    String fromBatchId=(String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_FROM_BATCH_ID);
    	    String toBatchId=(String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TO_BATCH_ID);
    	    String batchDate=(String)paramHashMap.get(ContractRegistrationInterfaceKey.GENERATE_BATCH_DATE);
    	    for(int i=Integer.parseInt(fromBatchId);i<=Integer.parseInt(toBatchId);i++){
    			if(ContractDao.checkBatchExist(con,String.valueOf(i)))
    			{
    				 String date = ContractDao.getBatchDate(con,String.valueOf(i)).split(" ")[0];
    		  		 System.out.println(date);
    		  		 String month = date.split("-")[1]  ;
    		  		 if(month.startsWith("0"))
    		  		 {
    		  			month = month.charAt(1)+"";
    		  		 }
    		  		 
    		  			 
    		  		 msg = SheetDao.selectCRBatchStatus(date.split("-")[0], month , con);
    		  			
    		  		 if(msg.equals("") || msg == null)  /// batch still open and can be deleted
    		  		 {	 
    		  			ContractDao.chanegBatchDateByID(con, String.valueOf(i), batchDate);
    				    ContractDao.insertBatchHistory(String.valueOf(i), strUserID,"UPDATE");
    		  		 }
    		  		 else
    		  		 {
    		  			closedBatchIDList = closedBatchIDList + String.valueOf(i) + "-";
    		  			//System.out.println("####################### " + closedBatchIDList); 
    		  		 }
    			
    			}
    		  }
    	  //if(ContractDao.checkBatchExist(con,fromBatchId)){
    		 // ContractDao.chanegBatchDate(con, fromBatchId,toBatchId, batchDate);
    	      dataHashMap.put(ContractRegistrationInterfaceKey.INPUT_HIDDEN_MESSAGE, closedBatchIDList);
    		  dataHashMap.put(ContractRegistrationInterfaceKey.ACTION_CHANGE_BATCH_DATE_FAILED, "ok");
    	//  }else{
    		//  dataHashMap.put(ContractRegistrationInterfaceKey.ACTION_CHANGE_BATCH_DATE_FAILED, "failed");
    	//  }
    	  
      }else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_DELETE_BATCH)==0)
      {
    	    strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
			
			 dataHashMap.put(ContractRegistrationInterfaceKey.INPUT_HIDDEN_MESSAGE, ""); 
  	  
      }
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_DELETE_BATCH)==0)
      {
    	    strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,action);
			
			String msg = "";
  	  
  	  String batchId=(String)paramHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
  	  
  	  if(ContractDao.checkBatchExist(con,batchId))
  	  {
  		 String date = ContractDao.getBatchDate(con,batchId).split(" ")[0];
  		 System.out.println(date);
  		 String month = date.split("-")[1]  ;
  		 if(month.startsWith("0"))
  		 {
  			month = month.charAt(1)+"";
  		 }
  		 
  			 
  		 msg = SheetDao.selectCRBatchStatus(date.split("-")[0], month , con);
  			
  		 if(msg.equals("") || msg == null)  /// batch still open and can be deleted
  		 {	 
  		   ContractDao.insertBatchHistory(batchId, strUserID,"DELETE");
  		   ContractDao.daleteBatch(batchId);
  		   msg = "Batch Deleted ....";
  		   dataHashMap.put(ContractRegistrationInterfaceKey.ACTION_CHANGE_BATCH_DATE_FAILED, "ok");
  		 }
  	  }
  	  else
  	  {
  		  msg = "Invalid Batch ID ....";
  		  dataHashMap.put(ContractRegistrationInterfaceKey.ACTION_CHANGE_BATCH_DATE_FAILED, "failed");
  	  }
  	      dataHashMap.put(ContractRegistrationInterfaceKey.INPUT_HIDDEN_MESSAGE, msg); 
      }
      
      
      
      
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_MONTHS_MANAGEMENT_CR)==0)
      {
    	  dataHashMap = new HashMap();
    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
     	  Vector sheetWarningTypes = SheetDao.getCRAllMonths(con);
          dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_COLLECTION, sheetWarningTypes);
          dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_ADDITIONAL_COLLECTION, "  ");  
  	  
      }
      
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_VIEW_MONTHS_MANAGEMENT)==0)
      {
    	  dataHashMap = new HashMap();
    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
     	  Vector sheetWarningTypes = SheetDao.getCRAllMonths(con);
          dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_COLLECTION, sheetWarningTypes);
          dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_ADDITIONAL_COLLECTION, "  ");  
  	  
      }
      
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADD_YEAR)==0)
      {
    	  dataHashMap = new HashMap();
    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
          String year = (String) paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_YEAR);
          dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_ADDITIONAL_COLLECTION, SheetDao.insertCRNewYear(year,con));
          dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_COLLECTION, SheetDao.getCRAllMonths(con)); 
  	  
      }
      
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_ADD_YEAR_CR)==0)
      {
    	  dataHashMap = new HashMap();
    	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
          String year = (String) paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_YEAR);
          dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_ADDITIONAL_COLLECTION, SheetDao.insertCRNewYear(year,con));
          dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_COLLECTION, SheetDao.getCRAllMonths(con)); 
  	  
      }
      
      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_CLOSE_MONTH_COMMISSION)==0)
      {
    	 dataHashMap = new HashMap();
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
       	 String commissionYear = (String) paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_YEAR);
       	 String commissionMonth = (String) paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_MONTH);
       	 String commissionStatusId = (String) paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_STATUS_ID);
      	 String commissionStatus = (String) paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_STATUS);
      	 SheetDao.changeCRMonthStatus(strUserID , commissionStatusId ,commissionStatus , commissionMonth ,  commissionYear, con);
       	 dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_ADDITIONAL_COLLECTION, "  ");
         dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_COLLECTION, SheetDao.getCRAllMonths(con)); 
  	  
      }

      else if(action.compareTo(ContractRegistrationInterfaceKey.ACTION_CLOSE_MONTH_COMMISSION_CR)==0)
      {
    	 dataHashMap = new HashMap();
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
       	 String commissionYear = (String) paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_YEAR);
       	 String commissionMonth = (String) paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_MONTH);
       	 String commissionStatusId = (String) paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_STATUS_ID);
      	 String commissionStatus = (String) paramHashMap.get(ContractRegistrationInterfaceKey.INPUT_HIDDEN_STATUS);
      	 SheetDao.changeCRMonthStatus(strUserID , commissionStatusId ,commissionStatus , commissionMonth ,  commissionYear, con);
       	 dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_ADDITIONAL_COLLECTION, "  ");
         dataHashMap.put(ContractRegistrationInterfaceKey.CR_HASHMAP_KEY_COLLECTION, SheetDao.getCRAllMonths(con)); 
  	  
      } 
      
      
      
      dataHashMap.put(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID,strChannelId);
      
    }
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return dataHashMap;
  }


  private static boolean isStringValidInt(String s)
  {
    try
    {
      Integer.parseInt(s);
      return true; 
    }
    catch(NumberFormatException e)
    {
    return false;
    }
  
  }
}
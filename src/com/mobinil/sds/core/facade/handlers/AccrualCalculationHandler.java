package com.mobinil.sds.core.facade.handlers;


import java.util.Vector;
import java.util.HashMap;
import java.lang.Object;
import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.system.acc.dao.*;
import com.mobinil.sds.core.system.acc.model.*;
import com.mobinil.sds.core.system.sop.schemas.dao.SchemaDAO;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.acc.*;
import com.mobinil.sds.core.system.cam.accrual.model.*;
import com.mobinil.sds.core.system.cam.accrual.dao.*;

public class AccrualCalculationHandler {
	
	static final int SELECT_PRODUCT_CHANNEL             = 1;
	static final int VIEW_PRODUCT_VALUES                = 2;
	static final int SAVE_PRODUCT_VALUES                = 3;
	static final int SELECT_DCM_CHANNEL                 = 4;
	static final int VIEW_DCM_VALUES                    = 5;
	static final int SAVE_DCM_VALUES                    = 6;
	static final int VIEW_DCM_PRODUCT                   = 7;
	static final int SEARCH_DCM_PRODUCT                 = 8;
	static final int EXPORT_DCM_PRODUCT                 = 9;
	static final int VIEW_TRANSACTION_TYPE              = 10;
	static final int SAVE_TRANSACTION_TYPE              = 11;
	static final int SAVE_ACCRUAL_VALUE                 = 12;
	static final int VIEW_ACCRUAL_DATA                  = 13;
	static final int VIEW_ACCRUAL_DETAIL_DATA           = 14;
	static final int VIEW_GROUP_MANAGEMENT              = 15;
	static final int CREATE_NEW_TRANSACTIONTYPE_GROUP   = 16;
	static final int EDIT_TRANSACTIONTYPE_GROUP         = 17;
	static final int DELETE_TRANSACTIONTYPE_GROUP       = 18;
	static final int SAVE_TRANSACTIONTYPE_GROUP         = 19;
	static final int UPDATE_TRANSACTIONTYPE_GROUP       = 20;
	static final int VIEW_WITH_HOLD_TAX                 = 21;
	static final int EDIT_WITH_HOLD_TAX                 = 22;
	static final int DELETE_WITH_HOLD_TAX               = 23;
	static final int ADD_WITH_HOLD_TAX                  = 24;    
	static final int SAVE_WITH_HOLD_TAX                 = 25;
	static final int UPDATE_WITH_HOLD_TAX               = 26;
	static final int VIEW_FRANCHISE_GROUP               = 27;
	static final int EDIT_FRANCHISE_GROUP               = 28;
	static final int ADD_FRANCHISE_GROUP                = 29;       
	static final int SAVE_FRANCHISE_GROUP               = 30;
	static final int UPDATE_FRANCHISE_GROUP             = 31;
	static final int VIEW_FRANCHISE_LIST                = 32;
	static final int ADD_FRANCHISE_LIST                 = 33;
	static final int SAVE_FRANCHISE_LIST                = 34;
	static final int DELETE_FRANCHISE_GROUP             = 35;
	static final int EDIT_FRANCHISE_LIST                = 36;
	static final int DELETE_FRANCHISE_LIST              = 37;
	static final int UPDATE_FRANCHISE_LIST              = 38;
	static final int VIEW_COMMISSION_DCM                = 39;
	static final int EDIT_COMMISSION_DCM                = 40;
	static final int CREATE_NEW_COMMISSION_DCM          = 41;
	static final int DELETE_COMMISSION_DCM              = 42;
	static final int SAVE_COMMISSION_DCM                = 43;
	static final int UPDATE_COMMISSION_DCM              = 44;
	
	
	public static HashMap handle(String action, HashMap paramHashMap,java.sql.Connection con)
	  {
		String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	    int actionType = 0;
	    HashMap dataHashMap = null;
	   // Connection  con = null;
	    try 
	    {
	     //  con = Utility.getConnection();
	       	   if (action.equals(AccInterfaceKey.ACTION_SELECT_PRODUCT_CHANNEL))                                   
	       		   actionType = SELECT_PRODUCT_CHANNEL;
	       	   else if(action.equals(AccInterfaceKey.ACTION_VIEW_PRODUCT_VALUES))
	       		   actionType = VIEW_PRODUCT_VALUES;
	       	   else if(action.equals(AccInterfaceKey.ACTION_SAVE_PRODUCT_VALUES))
	       		   actionType = SAVE_PRODUCT_VALUES;
	       	   else if(action.equals(AccInterfaceKey.ACTION_SELECT_DCM_CHANNEL))
	       			actionType = SELECT_DCM_CHANNEL; 
	       	   else if(action.equals(AccInterfaceKey.ACTION_VIEW_DCM_VALUES))
	       		   actionType = VIEW_DCM_VALUES;
	       	   else if(action.equals(AccInterfaceKey.ACTION_SAVE_DCM_VALUES))
	       		   actionType = SAVE_DCM_VALUES ;
	       	   else if(action.equals(AccInterfaceKey.ACTION_VIEW_DCM_PRODUCT))
	       		   actionType = VIEW_DCM_PRODUCT;
	       	   else if(action.equals(AccInterfaceKey.ACTION_SEARCH_DCM_PRODUCT))
	       		   actionType = SEARCH_DCM_PRODUCT;
	       	   else if(action.equals(AccInterfaceKey.ACTION_EXPORT_DCM_PRODUCT))
	       		   actionType = EXPORT_DCM_PRODUCT;
	       	   else if(action.equals(AccInterfaceKey.ACTION_VIEW_TRANSACTION_TYPE))
	       		   actionType = VIEW_TRANSACTION_TYPE;
	       	   else if(action.equals(AccInterfaceKey.ACTION_SAVE_TRANSACTION_TYPE))
	       		   actionType = SAVE_TRANSACTION_TYPE;
	       	   else if(action.equals(AccInterfaceKey.ACTION_SAVE_ACCRUAL_VALUE))
	       		   actionType = SAVE_ACCRUAL_VALUE;
	       	   else if(action.equals(AccInterfaceKey.ACTION_VIEW_ACCRUAL_DATA))
	       		   actionType = VIEW_ACCRUAL_DATA;
	       	   else if(action.equals(AccInterfaceKey.ACTION_VIEW_ACCRUAL_DETAIL_DATA))
	       		   actionType = VIEW_ACCRUAL_DETAIL_DATA;
	       	   else if(action.equals(AccInterfaceKey.ACTION_VIEW_GROUP_MANAGEMENT))
	       		   actionType = VIEW_GROUP_MANAGEMENT;
	       	   else if(action.equals(AccInterfaceKey.ACTION_CREATE_NEW_TRANSACTIONTYPE_GROUP))
	       		   actionType = CREATE_NEW_TRANSACTIONTYPE_GROUP;
	       	   else if(action.equals(AccInterfaceKey.ACTION_EDIT_TRANSACTIONTYPE_GROUP))
	       		   actionType = EDIT_TRANSACTIONTYPE_GROUP;
	       	   else if(action.equals(AccInterfaceKey.ACTION_DELETE_TRANSACTIONTYPE_GROUP))
	       		   actionType = DELETE_TRANSACTIONTYPE_GROUP;
	       	   else if(action.equals(AccInterfaceKey.ACTION_SAVE_TRANSACTIONTYPE_GROUP))
	       		   actionType = SAVE_TRANSACTIONTYPE_GROUP;
	       	   else if(action.equals(AccInterfaceKey.ACTION_UPDATE_TRANSACTIONTYPE_GROUP))
	       		   actionType = UPDATE_TRANSACTIONTYPE_GROUP;
	       	   else if(action.equals(AccInterfaceKey.ACTION_VIEW_WITH_HOLD_TAX))
	       		   actionType = VIEW_WITH_HOLD_TAX;
	       	   else if(action.equals(AccInterfaceKey.ACTION_EDIT_WITH_HOLD_TAX))
	       		   actionType = EDIT_WITH_HOLD_TAX;
	       	   else if(action.equals(AccInterfaceKey.ACTION_DELETE_WITH_HOLD_TAX))
	       		   actionType = DELETE_WITH_HOLD_TAX;
	       	   else if(action.equals(AccInterfaceKey.ACTION_ADD_WITH_HOLD_TAX))
	       		   actionType = ADD_WITH_HOLD_TAX;
	       	   else if(action.equals(AccInterfaceKey.ACTION_SAVE_WITH_HOLD_TAX))
	       		   actionType = SAVE_WITH_HOLD_TAX;
	       	   else if(action.equals(AccInterfaceKey.ACTION_UPDATE_WITH_HOLD_TAX))
	       		   actionType = UPDATE_WITH_HOLD_TAX;
	       	   else if(action.equals(AccInterfaceKey.ACTION_VIEW_FRANCHISE_GROUP))
	       		   actionType = VIEW_FRANCHISE_GROUP;
	       	   else if(action.equals(AccInterfaceKey.ACTION_EDIT_FRANCHISE_GROUP))
	       		   actionType = EDIT_FRANCHISE_GROUP;
	       	   else if(action.equals(AccInterfaceKey.ACTION_ADD_FRANCHISE_GROUP))
	       		   actionType = ADD_FRANCHISE_GROUP;
	       	   else if(action.equals(AccInterfaceKey.ACTION_SAVE_FRANCHISE_GROUP))
	       		   actionType = SAVE_FRANCHISE_GROUP;
	       	   else if(action.equals(AccInterfaceKey.ACTION_UPDATE_FRANCHISE_GROUP))
	       		   actionType = UPDATE_FRANCHISE_GROUP;
	       	   else if(action.equals(AccInterfaceKey.ACTION_VIEW_FRANCHISE_LIST))
	       		   actionType = VIEW_FRANCHISE_LIST;
	       	   else if(action.equals(AccInterfaceKey.ACTION_ADD_FRANCHISE_LIST))
	       		   actionType = ADD_FRANCHISE_LIST;
	       	   else if(action.equals(AccInterfaceKey.ACTION_SAVE_FRANCHISE_LIST))
	       		   actionType = SAVE_FRANCHISE_LIST;
	       	   else if(action.equals(AccInterfaceKey.ACTION_DELETE_FRANCHISE_GROUP))
	       		   actionType = DELETE_FRANCHISE_GROUP;
	       	   else if(action.equals(AccInterfaceKey.ACTION_EDIT_FRANCHISE_LIST))
	       		   actionType = EDIT_FRANCHISE_LIST;
	       	   else if(action.equals(AccInterfaceKey.ACTION_DELETE_FRANCHISE_LIST))
	       		   actionType = DELETE_FRANCHISE_LIST;
	       	   else if(action.equals(AccInterfaceKey.ACTION_UPDATE_FRANCHISE_LIST))
	       		   actionType = UPDATE_FRANCHISE_LIST;
	       	   else if(action.equals(AccInterfaceKey.ACTION_VIEW_COMMISSION_DCM))
	       		   actionType = VIEW_COMMISSION_DCM;
	       	   else if(action.equals(AccInterfaceKey.ACTION_EDIT_COMMISSION_DCM))
	       		   actionType = EDIT_COMMISSION_DCM;
	       	   else if(action.equals(AccInterfaceKey.ACTION_CREATE_NEW_COMMISSION_DCM))
	       		   actionType = CREATE_NEW_COMMISSION_DCM;
	       	   else if(action.equals(AccInterfaceKey.ACTION_DELETE_COMMISSION_DCM))
	       		   actionType = DELETE_COMMISSION_DCM;
	       	   else if(action.equals(AccInterfaceKey.ACTION_SAVE_COMMISSION_DCM))
	       		   actionType = SAVE_COMMISSION_DCM;
	       	   else if(action.equals(AccInterfaceKey.ACTION_UPDATE_COMMISSION_DCM))
	       		   actionType = UPDATE_COMMISSION_DCM;
	    
	       	 switch (actionType)                                                                                     
	         {
	       	 
	         case SELECT_PRODUCT_CHANNEL:
	             try
	             {
	               dataHashMap = new HashMap();
	               strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	               Vector vecChannels = AccDAO.getAllChannels(con);
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecChannels);
	             }
	             catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case VIEW_PRODUCT_VALUES:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String channelId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
		             Vector productValues = AccDAO.getProductValues(con, channelId);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, productValues);
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case SAVE_PRODUCT_VALUES:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String channelId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
		             AccDAO.deleteProductValue(con, channelId);
		             for(int j=0; j<paramHashMap.size(); j++)
		             {
		            String tempString = (String)paramHashMap.keySet().toArray()[j];
		            if(tempString.startsWith(AccInterfaceKey.INPUT_TEXT_PRODUCT_VALUE))
		            {
		             String strOptional = (String)paramHashMap.get(tempString);
		             System.out.println("The values issssssssss " + strOptional);
		             int productId = new Integer(tempString.substring(
		                                     tempString.lastIndexOf(
		                                    		 AccInterfaceKey.INPUT_TEXT_PRODUCT_VALUE)+13)).intValue();
		             
		             System.out.println("The product id issssss " + productId);
		             AccDAO.insertProductValue(con, productId, strOptional, channelId);
		                             
		             
		             }
		            }
		             Vector productValues = AccDAO.getProductValues(con, channelId);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, productValues);
		             
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case SELECT_DCM_CHANNEL:
	        	 try
	             {
	               dataHashMap = new HashMap();
	               strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	               Vector vecChannels = AccDAO.getAllChannels(con);
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecChannels);
	             }
	             catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case VIEW_DCM_VALUES:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String channelId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
		             Vector dcmValues = AccDAO.selectDcmValue(con, channelId);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, dcmValues);
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	        	 break;
	        	 
	         case SAVE_DCM_VALUES:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String channelId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
		             AccDAO.deleteDcmValue(con, channelId);
		             for(int j=0; j<paramHashMap.size(); j++)
		             {
		            	 
		            String tempString = (String)paramHashMap.keySet().toArray()[j];
		            if(tempString.startsWith(AccInterfaceKey.INPUT_TEXT_DCM_VALUE))
		            {
		             String strOptional = (String)paramHashMap.get(tempString);
		             System.out.println("The values issssssssss " + strOptional);
		             String dcmCodeValue = tempString.substring(
		                                     tempString.lastIndexOf(
		                                    		 AccInterfaceKey.INPUT_TEXT_DCM_VALUE)+9);
		             
		             System.out.println("The dcmCodeValue issssss " + dcmCodeValue);
		             int index = dcmCodeValue.lastIndexOf("_");
		             System.out.println("The index issss " + index);
		             String dcmCode = dcmCodeValue.substring(0,index);
		             System.out.println("The dcm code issss " + dcmCode);
		             String dcmName = dcmCodeValue.substring(index+1,dcmCodeValue.length());
		             System.out.println("The dcm name issss " + dcmName);
		             
		             AccDAO.insertDcmValue(con, dcmCode, dcmName, strOptional, channelId);
		             //AccDAO.insertProductValue(con, productId, strOptional, channelId);
		                             
		             
		             }
		            }
		             Vector dcmValues = AccDAO.selectDcmValue(con, channelId);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, dcmValues);
		             
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case VIEW_DCM_PRODUCT:
	           try
	           {
	        	   dataHashMap = new HashMap();
	               strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	               Vector vecChannels = AccDAO.getAllChannels(con);
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecChannels);
	               Vector dcmProduct = new Vector();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmProduct);
	           }
	           catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case SEARCH_DCM_PRODUCT:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		               strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		               Vector vecChannels = AccDAO.getAllChannels(con);
		               dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecChannels);
		               String channelId = (String)paramHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID);
		               System.out.println("The channel id issssss " + channelId);
			           dataHashMap.put(AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID, channelId);
			           String activationDateFrom =(String)paramHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_FROM);
			           dataHashMap.put(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_FROM, activationDateFrom);
			           String activationDateTo = (String)paramHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_TO);
			           dataHashMap.put(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_TO, activationDateTo);
		               Vector dcmProduct = AccDAO.getDcmProductCalculation(con,channelId,activationDateFrom,activationDateTo);
		               dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmProduct); 
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case SAVE_ACCRUAL_VALUE:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             int userId = Integer.parseInt(strUserID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             Vector vecChannels = AccDAO.getAllChannels(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecChannels);
		             String channelId = (String)paramHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID);
		             System.out.println("The channel id issssss " + channelId);
			         //dataHashMap.put(AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID, channelId);
			         String activationDateFrom =(String)paramHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_FROM);
			         //dataHashMap.put(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_FROM, activationDateFrom);
			         String activationDateTo = (String)paramHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_TO);
			         //dataHashMap.put(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_TO, activationDateTo);
			         Long accrualValueId = AccDAO.insertAccrualValue(con, channelId, activationDateFrom, activationDateTo);
			         Vector dcmProduct = AccDAO.getDcmProductCalculation(con,channelId,activationDateFrom,activationDateTo);
			         
			         for(int i=0;i<dcmProduct.size();i++)
			     	{
			     		DcmProductEquationModel dcmProductEquationModel = (DcmProductEquationModel)dcmProduct.get(i);
			     		String productId = dcmProductEquationModel.getProductId();
			     		String productName = dcmProductEquationModel.getProductName();
			     		String productValue = dcmProductEquationModel.getProductValue();
			     		String dcmId = dcmProductEquationModel.getDcmId();
			     		String dcmName = dcmProductEquationModel.getDcmName();
			     		String dcmValue = dcmProductEquationModel.getDcmValue();
			     		String lineCount = dcmProductEquationModel.getLineCount();
			     		String transactionTypeId = dcmProductEquationModel.getTransactionTypeId();
			     		String eValue = dcmProductEquationModel.getEValue();
			     		AccDAO.insertAccrualValueDetail(con, accrualValueId, dcmId, productId, lineCount, dcmValue, productValue, eValue, dcmName, productName);
			     		
			     	}
			         String accrualValueStr = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_ACCRUAL_VALUE);
			         double accrualValue = Double.parseDouble(accrualValueStr);
			         int intChannelId = Integer.parseInt(channelId);
			         int accrualId = AccrualDAO.getChannelAccrual(con, intChannelId);
			         System.out.println("The Accrual Id issss " + accrualId);
			         int reasonId = 1;
			         MakerAccrualValueModel maker_value_m = new MakerAccrualValueModel();
			         maker_value_m.setAccrual_id(accrualId);
		             maker_value_m.setReason_id(reasonId);
		             maker_value_m.setAccrual_value(accrualValue);
		             int inserted_accrual_value_id = AccrualDAO.addDeleteAccrualValue(con, maker_value_m, userId, "add");
		             dcmProduct = new Vector();
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmProduct);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Accrual Saved Successful");
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case EXPORT_DCM_PRODUCT:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		               strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		               Vector vecChannels = AccDAO.getAllChannels(con);
		               dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecChannels);
		               String channelId = (String)paramHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID);
		               System.out.println("The channel id issssss " + channelId);
			           dataHashMap.put(AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID, channelId);
			           String activationDateFrom =(String)paramHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_FROM);
			           dataHashMap.put(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_FROM, activationDateFrom);
			           String activationDateTo = (String)paramHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_TO);
			           dataHashMap.put(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_TO, activationDateTo);
		               Vector dcmProduct = AccDAO.getDcmProductCalculation(con,channelId,activationDateFrom,activationDateTo);
		               dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmProduct); 
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break; 
	           
	         case VIEW_TRANSACTION_TYPE:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             Vector vecChannels = AccDAO.getAllChannels(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecChannels);
		             Vector vecGroups = AccDAO.getAllGroups(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, vecGroups);
		             Vector transactionType = AccDAO.getTransactionType(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,transactionType);
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case SAVE_TRANSACTION_TYPE:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             Vector vecChannels = AccDAO.getAllChannels(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecChannels);
		             Vector vecGroups = AccDAO.getAllGroups(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, vecGroups);
		             for(int j=0; j<paramHashMap.size(); j++)
		             {
		            String tempString = (String)paramHashMap.keySet().toArray()[j];
		            String strOptional = "";
		            String transactionTypeId = "";
		            String group = "";
		            if(tempString.startsWith(AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID))
		            {
		             strOptional = (String)paramHashMap.get(tempString);
		             System.out.println("The values issssssssss " + strOptional);
		             transactionTypeId = tempString.substring(
		                                     tempString.lastIndexOf(
		                                    		 AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID)+10);
		             
		             System.out.println("The Transaction type id issssss " + transactionTypeId);
		             group = (String)paramHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_GROUP_ID+transactionTypeId);
		             System.out.println("The group issssssssss " + group);
		            }
//		             String group = "";
//		             if(tempString.startsWith(AccInterfaceKey.INPUT_SEARCH_TEXT_GROUP_ID))
//			            {
//			             group = (String)paramHashMap.get(tempString);
//			             System.out.println("The group issssssssss " + group);
//			             
//			             
//			             }
		              AccDAO.updateTransactionType(con, transactionTypeId, strOptional,group);      
		             
		             
		            }
		             
		             Vector transactionType = AccDAO.getTransactionType(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,transactionType);
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	        
	           
	         case VIEW_ACCRUAL_DATA:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             Vector accrualDataVec = AccDAO.getAllAccrualData(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, accrualDataVec);
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case VIEW_ACCRUAL_DETAIL_DATA:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String accrualId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_ACCRUAL_VALUE);
		             Vector accrualDetail = AccDAO.getAccrualDataById(con, accrualId);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, accrualDetail);
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case VIEW_GROUP_MANAGEMENT:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             Vector vecGroups = AccDAO.getAllGroups(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecGroups);
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case DELETE_TRANSACTIONTYPE_GROUP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String groupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_GROUP_ID);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_GROUP_ID,groupId);
		             AccDAO.deleteTransactionGroup(con, groupId);
		             Vector vecGroups = AccDAO.getAllGroups(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecGroups); 
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case UPDATE_TRANSACTIONTYPE_GROUP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String oldGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_GROUP_ID);
		             String newGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_GROUP_ID);
		             String groupName = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_GROUP_NAME);
		             AccDAO.updateTransactionGroup(con, oldGroupId, newGroupId, groupName);
		             Vector vecGroups = AccDAO.getAllGroups(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecGroups); 
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case SAVE_TRANSACTIONTYPE_GROUP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String newGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_GROUP_ID);
		             String groupName = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_GROUP_NAME);
		             AccDAO.insertTransactionGroup(con, newGroupId, groupName);
		             Vector vecGroups = AccDAO.getAllGroups(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecGroups); 
		             
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case CREATE_NEW_TRANSACTIONTYPE_GROUP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case EDIT_TRANSACTIONTYPE_GROUP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String groupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_GROUP_ID);
		             GroupModel groupModel = AccDAO.selectTranasactionGroup(con, groupId);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, groupModel);
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case VIEW_WITH_HOLD_TAX:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             Vector vecWithHoldTax = AccDAO.getAllWithHoldTax(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecWithHoldTax);
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case EDIT_WITH_HOLD_TAX:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String dcmCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_DCM_CODE);
		             String withHoldTax = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_WITH_HOLD_TAX);
		             float withHoldTaxFloat = Float.parseFloat(withHoldTax);
		             WithHoldTaxModel withHoldTaxModel = AccDAO.selectWithHoldTax(con, dcmCode,withHoldTaxFloat);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, withHoldTaxModel); 
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case DELETE_WITH_HOLD_TAX:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String dcmCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_DCM_CODE);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_DCM_CODE,dcmCode);
		             String withHoldTax = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_WITH_HOLD_TAX);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_WITH_HOLD_TAX,withHoldTax);
		             AccDAO.deleteWithHoldTax(con, dcmCode, withHoldTax);
		             Vector vecWithHoldTax = AccDAO.getAllWithHoldTax(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecWithHoldTax);
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case ADD_WITH_HOLD_TAX:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case SAVE_WITH_HOLD_TAX:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String newDcmCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_DCM_CODE);
		             String withHoldTax = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_WITH_HOLD_TAX);
		             float withHoldTaxFloat = Float.parseFloat(withHoldTax);
		             System.out.println("The with hold tax in handler isssssssssssss " + withHoldTaxFloat);
		             boolean check = AccDAO.insertWithHoldTax(con, newDcmCode, withHoldTaxFloat);
		             System.out.println("The check isssssssssssssss " + check);
		             if(!check)
				        {
				          dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"This DCM is already exist");
				        }
		             Vector vecWithHoldTax = AccDAO.getAllWithHoldTax(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecWithHoldTax);
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case UPDATE_WITH_HOLD_TAX:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String oldDcmCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_DCM_CODE);
		             String newDcmCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_DCM_CODE);
		             String oldWithHoldTax = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_WITH_HOLD_TAX);
		             float withOldHoldTaxFloat = Float.parseFloat(oldWithHoldTax);
		             String newWithHoldTax = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_WITH_HOLD_TAX);
		             float withNewHoldTaxFloat = Float.parseFloat(newWithHoldTax);
		             boolean check = AccDAO.updateWithHoldTax(con, oldDcmCode, newDcmCode, withOldHoldTaxFloat, withNewHoldTaxFloat);
		             if(!check)
				        {
				          dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"This DCM is already exist");
				        }
		             Vector vecWithHoldTax = AccDAO.getAllWithHoldTax(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecWithHoldTax);
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case VIEW_FRANCHISE_GROUP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             Vector vecFranchiseGroup = AccDAO.getAllFranchiseGroup(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecFranchiseGroup);
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case EDIT_FRANCHISE_GROUP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String franchiseGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID);
		             System.out.println("The franchise group id issssssssssssssss " + franchiseGroupId);
		             FranchiseGroupModel franchiseGroupModel = AccDAO.selectFranchiseGroup(con, franchiseGroupId);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, franchiseGroupModel);
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case ADD_FRANCHISE_GROUP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case SAVE_FRANCHISE_GROUP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String franchiseGroupName = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_FRANCHISE_GROUP_NAME);
		             String franchiseGroupDescription = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_FRANCHISE_GROUP_DESCRIPTION);
		             AccDAO.insertFranchiseGroup(con, franchiseGroupName, franchiseGroupDescription);
		             Vector vecFranchiseGroup = AccDAO.getAllFranchiseGroup(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecFranchiseGroup);
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        	 break;
	        	 
	         case UPDATE_FRANCHISE_GROUP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String franchiseGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID);
		             String franchiseGroupName = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_FRANCHISE_GROUP_NAME);
		             String franchiseGroupDescription = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_FRANCHISE_GROUP_DESCRIPTION);
		             AccDAO.updateFranchiseGroup(con, franchiseGroupId, franchiseGroupName, franchiseGroupDescription);
		             Vector vecFranchiseGroup = AccDAO.getAllFranchiseGroup(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecFranchiseGroup);
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        	 break;
	        	 
	         case VIEW_FRANCHISE_LIST:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String franchiseGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID, franchiseGroupId);
		             Vector vecFranchiseList = AccDAO.getFranchiseListByGroupId(con, franchiseGroupId);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecFranchiseList); 
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case ADD_FRANCHISE_LIST:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
		             String franchiseGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID, franchiseGroupId);
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case SAVE_FRANCHISE_LIST:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String franchiseGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID, franchiseGroupId);
		             String franchiseCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
		             AccDAO.insertFranchiseList(con, franchiseGroupId, franchiseCode);
		             Vector vecFranchiseList = AccDAO.getFranchiseListByGroupId(con, franchiseGroupId);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecFranchiseList); 
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case DELETE_FRANCHISE_GROUP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String franchiseGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID);
		             AccDAO.deleteFranchiseGroup(con,franchiseGroupId );
		             Vector vecFranchiseGroup = AccDAO.getAllFranchiseGroup(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecFranchiseGroup);
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case EDIT_FRANCHISE_LIST:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String franchiseGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID, franchiseGroupId);
		             String franchiseCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_CODE);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_CODE,franchiseCode);
		             FranchiseListModel franchiseListModel = AccDAO.selectFranchiseList(con, franchiseGroupId, franchiseCode);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, franchiseListModel);
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case DELETE_FRANCHISE_LIST:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String franchiseGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID, franchiseGroupId);
		             String franchiseCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_CODE);
		             AccDAO.deleteFranchiseList(con, franchiseGroupId, franchiseCode);
		             Vector vecFranchiseList = AccDAO.getFranchiseListByGroupId(con, franchiseGroupId);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecFranchiseList); 
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	         case UPDATE_FRANCHISE_LIST:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             String franchiseGroupId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID);
		             dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID, franchiseGroupId);
		             String oldFranchiseCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_CODE);
		             String newFranchiseCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
		             AccDAO.updateFranchiseList(con, franchiseGroupId, oldFranchiseCode, newFranchiseCode);
		             Vector vecFranchiseList = AccDAO.getFranchiseListByGroupId(con, franchiseGroupId);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecFranchiseList); 
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	        case VIEW_COMMISSION_DCM:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             Vector vecCommissionDcm = AccDAO.getAllCommissionDcm(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecCommissionDcm);  
	        	 }
	        	 catch(Exception e)
	        	 {
	        		 e.printStackTrace();
	        	 }
	        break;
	        
	        case EDIT_COMMISSION_DCM:
	        	try
	        	{
	        		dataHashMap = new HashMap();
		            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		            String oldDcmId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_OLD_DCM_ID);
		            dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_OLD_DCM_ID, oldDcmId);
		            String newDcmId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_NEW_DCM_ID);
		            dataHashMap.put(AccInterfaceKey.INPUT_HIDDEN_NEW_DCM_ID, newDcmId);
		            CommissionDcmFixModel commissionDcmFixeModel = AccDAO.selectCommissionDcm(con, oldDcmId, newDcmId);
		            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, commissionDcmFixeModel);
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        break;
	        
	        case CREATE_NEW_COMMISSION_DCM:
	        	try
	        	{
	        		dataHashMap = new HashMap();
		            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        break;
	        
	        case DELETE_COMMISSION_DCM:
	        	try
	        	{
	        		dataHashMap = new HashMap();
		            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		            String oldDcmId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_OLD_DCM_ID);
		            String newDcmId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_NEW_DCM_ID);
		            AccDAO.deleteCommissionDcm(con, oldDcmId, newDcmId);
		            Vector vecCommissionDcm = AccDAO.getAllCommissionDcm(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecCommissionDcm);  
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        break;
	        
	        case SAVE_COMMISSION_DCM:
	        	try
	        	{
	        		dataHashMap = new HashMap();
		            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		            String oldDcmCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_OLD_DCM_CODE);
		            String newDcmCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_NEW_DCM_CODE);
		             int check = AccDAO.insertCommissionDcm(con, oldDcmCode, newDcmCode);
		             System.out.println("The check isssssssssssssss " + check);
		             if(check==1)
				        {
				          dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"New DCM Code Does not exist");
				        }
		             else if(check == 2)
		             {
		            	 dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Old DCM Code Does not exist");
		             }
		             else if(check == 3)
		             {
		            	 dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"New DCM Code and Old DCM Code are not exist");
		             }
		             Vector vecCommissionDcm = AccDAO.getAllCommissionDcm(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecCommissionDcm);
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        break;
	        
	        case UPDATE_COMMISSION_DCM:
	        	try
	        	{
	        		dataHashMap = new HashMap();
		            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		            String oldDcmCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_OLD_DCM_CODE);
		            String newDcmCode = (String)paramHashMap.get(AccInterfaceKey.INPUT_TEXT_NEW_DCM_CODE);
		            String oldDcmId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_OLD_DCM_ID);
		            System.out.println("The old dcm id isssssssssssss " + oldDcmId);
		            String newDcmId = (String)paramHashMap.get(AccInterfaceKey.INPUT_HIDDEN_NEW_DCM_ID);
		            System.out.println("The new dcm id isssssssssssss " + newDcmId);
		             int check = AccDAO.updateCommissionDcm(con, oldDcmCode, newDcmCode, oldDcmId, newDcmId);
		             System.out.println("The check isssssssssssssss " + check);
		             if(check==1)
				        {
				          dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"New DCM Code Does not exist");
				        }
		             else if(check == 2)
		             {
		            	 dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Old DCM Code Does not exist");
		             }
		             else if(check == 3)
		             {
		            	 dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"New DCM Code and Old DCM Code are not exist");
		             }
		             Vector vecCommissionDcm = AccDAO.getAllCommissionDcm(con);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecCommissionDcm);
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        break;
	        	 
	         default:
	             Utility.logger.debug ("Unknown action received: " + action ); 
	         }

	        
	       }
	       catch(Exception objExp)
	       {
	         objExp.printStackTrace();
	       }
	    
	    return dataHashMap;
	    }

}

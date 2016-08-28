package com.mobinil.sds.core.facade.handlers;

/**
 * AdministratorHandlerEJBBean Statless Sesion Bean.
 * It handles all administration module common actions. 
 * 
 * handle(String action, HashMap paramHashMap)
 * According to the action it calls the concerned Data Access Object 
 * or a CMP and return the data Hash Map with the returned data.
 * 
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

 /*
  * 
  * the following code has the bussines logic needed to handel the following request related to the admin moduel 
  *  1- LIST_ALL_MODULS 
  *  2- UPDATE_MODULS_STATUSES 
  *  3- LIST_ALL_PRIVILEGES
  *  4- UPDATE_PRIVILEGES_STATUSES
  *  5- SEARCH_REVENUE 
     6- UPDATE_REVENUE 
     7- SEARCH_SCRATCH_CARD 
     8- UPDATE_SCRATCH_CARD
     9- SEARCH_ACTIVATION 
    10- UPDATE_ACTIVATION
    11- SHOW_ACTIVATION
    12- SHOW_DATA_IMPORT_SCREEN
    13- SHOW_POS_BULK_UPDATE_IMPORT_SCREEN
  *   

  */

import com.mobinil.sds.core.system.authenticationResult.dao.LabelDao;
import com.mobinil.sds.core.system.sa.scratch_card.dao.ScratchCardDao;
import com.mobinil.sds.core.system.sa.activation.dao.*;
import com.mobinil.sds.core.system.sa.scratch_card.model.ScratchCardModel;
import com.mobinil.sds.web.interfaces.sop.SOPInterfaceKey;
import com.mobinil.sds.core.system.sa.modules.dao.*;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.core.system.sa.revenue.dao.*; 
import com.mobinil.sds.core.system.gn.dcm.dao.*;
import com.mobinil.sds.core.system.gn.dcm.dto.*;
import com.mobinil.sds.core.system.cr.bundle.dao.*;
import com.mobinil.sds.core.system.cr.bundle.model.*;
import com.mobinil.sds.core.system.sop.schemas.dao.*;
import com.mobinil.sds.core.system.sop.schemas.model.*;
import com.mobinil.sds.core.system.sop.requests.dao.*;
import com.mobinil.sds.core.system.sop.requests.model.*;
import java.sql.*;
import com.mobinil.sds.core.system.sa.product.model.ProductModel;
import java.util.*;

import com.mobinil.sds.core.system.cr.sheet.dao.*;
import com.mobinil.sds.core.system.dataMigration.model.PaymentLevelModel;
import com.mobinil.sds.core.system.tango.dao.TangoFileDAO;
import com.mobinil.sds.core.system.nomad.dao.NomadFileDAO;
import com.mobinil.sds.core.system.nomad.dao.NomadLabelDao;
import com.mobinil.sds.core.system.paymentHistory.dao.PaymentHistoryFileDAO;
import com.mobinil.sds.core.system.request.dao.RequestDao;
import com.mobinil.sds.core.system.request.model.ChannelModel;
import com.mobinil.sds.core.system.sa.history.dao.PaymentLevelHistoryDao;
import com.mobinil.sds.core.system.sa.product.dao.*;
import com.mobinil.sds.core.system.sa.importdata.dao.*;
import com.mobinil.sds.core.system.sa.monthList.dao.MonthOfTheListDao;
import com.mobinil.sds.core.system.scm.dao.PoiWriteExcelFile;
import com.mobinil.sds.core.system.scm.model.POSSearchExcelModel;

import com.mobinil.sds.core.utilities.CachEngine;
import com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey;
import com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey;
import java.util.logging.Level;
public class AdministratorHandler  
{
  //////////////////Static ints used to switch on the actions.//////////////////
  static final int LIST_ALL_MODULS = 1;

  static final int UPDATE_MODULS_STATUSES = 2;

  static final int LIST_ALL_PRIVILEGES = 3;

  static final int UPDATE_PRIVILEGES_STATUSES = 4;

  static final int SEARCH_REVENUE =5 ;

  static final int UPDATE_REVENUE=6;

  static final int SEARCH_SCRATCH_CARD =7;

  static final int UPDATE_SCRATCH_CARD=8;


  static final int SEARCH_ACTIVATION =9;

  static final int UPDATE_ACTIVATION=10;
  static final int SHOW_ACTIVATION=11;

  static final int SHOW_DATA_IMPORT_SCREEN=12;
  
  
  static final int RUN_LINUX_COMMAND_INPUT = 13;
  static final int RUN_LINUX_COMMAND_OUTPUT = 14;
  static final int SHOW_BUNDLE_PRODUCT_TYPE = 15;
  static final int EDIT_BUNDLE_PRODUCT_TYPE = 16;
  static final int UPDATE_BUNDLE_PRODUCT_TYPE = 17;
  static final int CREATE_NEW_PRODUCT_TYPE = 18;
  static final int SAVE_NEW_PRODUCT_TYPE = 19;
  static final int SHOW_BUNDLE_PROMOTION_TYPE = 20;
  static final int EDIT_BUNDLE_PROMOTION_TYPE = 21;
  static final int CREATE_NEW_PROMOTION_TYPE = 22;
  static final int UPDATE_BUNDLE_PROMOTION_TYPE = 23;
  static final int SAVE_NEW_PROMOTION_TYPE = 24;
  static final int SHOW_BUNDLE_COMPONENT = 25;
  static final int EDIT_BUNDLE_COMPONENT = 26;
  static final int CREATE_NEW_BUNDLE_COMPONENT = 27;
  static final int UPDATE_BUNDLE_COMPONENT = 28;
  static final int SAVE_NEW_BUNDLE_COMPONENT = 29;
  static final int VIEW_WAREHOUSE_LIST   = 30;
  
  static final int LIST_ALL_PRODUCTS = 31;
  static final int LIST_ALL_CHANNELS = 32;
  static final int LIST_ALL_PAYMENT_LEVELS = 33;
  
  static final int SHOW_POS_BULK_UPDATE_IMPORT_SCREEN=34;
  
  static final int EDIT_PRODUCT = 35;
  static final int EDIT_CHANNEL = 36;
  static final int EDIT_PAYMENT_LEVEL = 37;

  static final int NEW_PRODUCT = 38;
  static final int NEW_CHANNEL = 39;
  static final int NEW_PAYMENT_LEVEL = 40;
  
  static final int ADD_PRODUCT = 41;
  static final int ADD_CHANNEL = 42;
  static final int ADD_PAYMENT_LEVEL = 43;
  
  
  
  static final int UPDATE_PRODUCT = 44;
  static final int UPDATE_CHANNEL = 45;
  static final int UPDATE_PAYMENT_LEVEL = 46;
  
  
  
  static final int SHOW_NOMAD_FILE_IMPORT_SCREEN=47;
  static final int SHOW_NOMAD_FILE_LIST=48;
  
  
  static final int SAVE_PAYMENT_LEVEL_HISTORY = 49;
  
  static final int DELETE_NOMAD_FILE = 50;
  static final int LIST_PAYMENT_LEVEL_HISTORY = 51;
  static final int LIST_PAYMENT_LEVEL_HISTORY_FILES = 52;
  static final int DELETE_HISTORY_FILE = 53;
  static final int EXPORT_PAYMENT_LEVEL_HISTORY = 54;
  static final int SAVE_LIST_MONTH = 55; 
  static final int SAVE_LIST = 56; 
  
  /**
   * handle method:
   * 
   * According to the action it calls the concerned Data Access Object
   * or a CMP and return the data Hash Map with the returned data.
   * 
   * @param	String action, HashMap paramHashMap
   * @return HashMap
   * @throws  
   * @see    
  */	

  public static HashMap handle(String action, HashMap paramHashMap,java.sql.Connection con)
  {
    int actionType = 0;
    HashMap dataHashMap = new HashMap(100);
    String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    if(strUserID != null && strUserID.compareTo("") != 0)
    {
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);           
    }
    try
    {
      
      if (action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_POS_BULK_UPDATE_IMPORT_SCREEN)==0)
      {
        actionType = SHOW_POS_BULK_UPDATE_IMPORT_SCREEN;        
      }  
      if (action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_DATA_IMPORT_SCREEN)==0)
      {
        actionType = SHOW_DATA_IMPORT_SCREEN;        
      }      
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_LIST_MODULES)==0)
      {
        actionType = LIST_ALL_MODULS;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_UPDATE_MODULES_STATUS)==0)
      {
        actionType = UPDATE_MODULS_STATUSES;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_LIST_PRIVILEGES)==0)
      {
        actionType = LIST_ALL_PRIVILEGES;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_UPDATE_PRIVILEGE_STATUS)==0)
      {
        actionType = UPDATE_PRIVILEGES_STATUSES;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SEARCH_REVENUE)==0)
      {
        actionType = SEARCH_REVENUE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_UPDATE_REVENUE)==0)
      {
        actionType = UPDATE_REVENUE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SEARCH_SCRATCH_CARD)==0)
      {
        actionType = SEARCH_SCRATCH_CARD;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_UPDATE_SCRATCH_CARD)==0)
      {
        actionType = UPDATE_SCRATCH_CARD;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SEARCH_ACTIVATION)==0)
      {
        actionType = SEARCH_ACTIVATION;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_UPDATE_ACTIVATION)==0)
      {
        actionType = UPDATE_ACTIVATION;
      }      
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_ACTIVATION)==0)
      {
        actionType = SHOW_ACTIVATION;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_RUN_LINUX_COMMAND_INPUT)==0)
      {
        actionType = RUN_LINUX_COMMAND_INPUT;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_RUN_LINUX_COMMAND_OUTPUT)==0)
      {
        actionType = RUN_LINUX_COMMAND_OUTPUT;
      }

      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_BUNDLE_PRODUCT_TYPE)==0)
      {
        actionType = SHOW_BUNDLE_PRODUCT_TYPE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_EDIT_BUNDLE_PRODUCT_TYPE)==0)
      {
        actionType = EDIT_BUNDLE_PRODUCT_TYPE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_UPDATE_BUNDLE_PRODUCT_TYPE)==0)
      {
        actionType = UPDATE_BUNDLE_PRODUCT_TYPE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_CREATE_NEW_PRODUCT_TYPE)==0)
      {
        actionType = CREATE_NEW_PRODUCT_TYPE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SAVE_NEW_PRODUCT_TYPE)==0)
      {
        actionType = SAVE_NEW_PRODUCT_TYPE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_BUNDLE_PROMOTION_TYPE)==0)
      {
        actionType = SHOW_BUNDLE_PROMOTION_TYPE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_EDIT_BUNDLE_PROMOTION_TYPE)==0)
      {
        actionType = EDIT_BUNDLE_PROMOTION_TYPE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_CREATE_NEW_PROMOTION_TYPE)==0)
      {
        actionType = CREATE_NEW_PROMOTION_TYPE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_UPDATE_BUNDLE_PROMOTION_TYPE)==0)
      {
        actionType = UPDATE_BUNDLE_PROMOTION_TYPE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SAVE_NEW_PROMOTION_TYPE)==0)
      {
        actionType = SAVE_NEW_PROMOTION_TYPE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_BUNDLE_COMPONENT)==0)
      {
        actionType = SHOW_BUNDLE_COMPONENT;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_EDIT_BUNDLE_COMPONENT)==0)
      {
        actionType = EDIT_BUNDLE_COMPONENT;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_CREATE_NEW_BUNDLE_COMPONENT)==0)
      {
        actionType = CREATE_NEW_BUNDLE_COMPONENT;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_UPDATE_BUNDLE_COMPONENT)==0)
      {
        actionType = UPDATE_BUNDLE_COMPONENT;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SAVE_NEW_BUNDLE_COMPONENT)==0)
      {
        actionType = SAVE_NEW_BUNDLE_COMPONENT;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_PRODUCTS)==0)
      {
        actionType = LIST_ALL_PRODUCTS;
      }
      
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_CHANNELS)==0)
      {
        actionType = LIST_ALL_CHANNELS;
      }
      
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_PAYMENT_LEVELS)==0)
      {
        actionType = LIST_ALL_PAYMENT_LEVELS;
      }
      
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_CHANNELS)==0)
      {
        actionType = LIST_ALL_CHANNELS;
      }
      
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_EDIT_PRODUCT)==0)
      {
        actionType = EDIT_PRODUCT;
      }
      
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_ADD_PRODUCT)==0)
      {
        actionType = ADD_PRODUCT;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_NEW_PRODUCT)==0)
      {
        actionType = NEW_PRODUCT;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_UPDATE_PRODUCT)==0)
      {
        actionType = UPDATE_PRODUCT;
      }
      
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_EDIT_CHANNEL)==0)
      {
        actionType = EDIT_CHANNEL;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_UPDATE_CHANNEL)==0)
      {
        actionType = UPDATE_CHANNEL;
      }
     
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_ADD_CHANNEL)==0)
      {
        actionType = ADD_CHANNEL;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_EDIT_PAYMENT_LEVEL)==0)
      {
        actionType = EDIT_PAYMENT_LEVEL;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_UPDATE_PAYMENT_LEVEL)==0)
      {
        actionType = UPDATE_PAYMENT_LEVEL;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_NEW_PAYMENT_LEVEL)==0)
      {
        actionType = NEW_PAYMENT_LEVEL;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_ADD_PAYMENT_LEVEL)==0)
      {
        actionType = ADD_PAYMENT_LEVEL;
      }
      
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_NOMAD_IMPORT_SCREEN)==0)
      {
        actionType = SHOW_NOMAD_FILE_IMPORT_SCREEN;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_VIEW_NOMAD_FILE)==0)
      {
        actionType = SHOW_NOMAD_FILE_LIST;
      }
      
      
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SAVE_PAYMENT_LEVEL_HISTORY)==0)
      {
        actionType = SAVE_PAYMENT_LEVEL_HISTORY;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SAVE_LIST)==0)
      {
        actionType = SAVE_LIST;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_DELETE_NOMAD_FILE)==0)
      {
        actionType = DELETE_NOMAD_FILE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_DELETE_HISTORY_FILE)==0)
      {
        actionType = DELETE_HISTORY_FILE;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_EXPORT_PAYMENT_LEVEL_HISTORY)==0)
      {
        actionType = EXPORT_PAYMENT_LEVEL_HISTORY;
      }
      
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_PAYMENT_LEVEL_HISTORY)==0)
      {
        actionType = LIST_PAYMENT_LEVEL_HISTORY;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SHOW_HISTORY_FILE)==0)
      {
        actionType = LIST_PAYMENT_LEVEL_HISTORY_FILES;
      }
      else if(action.compareTo(AdministrationInterfaceKey.ACTION_SAVE_LIST_MONTH)==0)
      {
        actionType = SAVE_LIST_MONTH;
      }
      
      //////////////////////////////////////////////////////////////////////////
      switch (actionType) 
      {
          /////////////////paymeny level history month in year//////////
          
        case LIST_PAYMENT_LEVEL_HISTORY:
              
            // split by __ =>> YEAR_MONTH
                
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_LIST_COLLECTION, PaymentLevelHistoryDao.getHistoryFileMonthYeatByUserId(strUserID));
        break;
            
            
            case SAVE_LIST_MONTH:
              
            // split by __ =>> YEAR_MONTH
                System.out.println("in action SAVE_LIST_MONTH");
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_LIST_COLLECTION, MonthOfTheListDao.getHistoryFileMonthYeatByUserId(strUserID));
        break;
            

            
        case LIST_PAYMENT_LEVEL_HISTORY_FILES:
            {
                    System.out.println("in LIST_PAYMENT_LEVEL_HISTORY_FILES");
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
                    Vector files =PaymentHistoryFileDAO.getallFiles(con,strUserID);
	            dataHashMap.put(AdministrationInterfaceKey.VECTOR_FILES,files);
              
                   }
            break;
            
case SHOW_NOMAD_FILE_LIST:
		 {
                    System.out.println("in SHOW_NOMAD_FILE_LIST");
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
                    Vector files =NomadFileDAO.getallNomadfiles(con,strUserID);
	            dataHashMap.put(AdministrationInterfaceKey.VECTOR_FILES,files);
              
                   }
			break;
            
        ////////////////////////list actions + privileges&roles(web interface): Product, Channel, PaymentLevel  
        case LIST_ALL_PAYMENT_LEVELS:
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, BundleDao.getPaymentLevels(con));
        break;  
            
        case LIST_ALL_PRODUCTS:
            System.out.println("LIST PRODUCTS");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, BundleDao.getProducts(con));
        break;      
        
        case LIST_ALL_CHANNELS:    
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, BundleDao.getChannels(con));
        break;
        
        
        ////////////////////////edit and update actions: Product, Channel, PaymentLevel
        case EDIT_PRODUCT:
        {
            
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          String strHiddenNameProductId = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_HIDDEN_NAME_PRODUCT_ID);
          System.out.println("edit product "+strHiddenNameProductId);
          ProductModel newProductModel = BundleDao.getProductById(con, strHiddenNameProductId);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newProductModel);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strHiddenNameProductId);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, BundleDao.getProductCategories(con));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_FIELD, BundleDao.getProductCategoryByProductId(con,strHiddenNameProductId));
        }
        break;
        
            
        case EDIT_CHANNEL:
        {
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          String strHiddenNameChannelId = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_HIDDEN_NAME_CHANNEL_ID);
          System.out.println("edit channel "+strHiddenNameChannelId);
          ChannelModel newChannelModel = BundleDao.getChannelById(con, strHiddenNameChannelId);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newChannelModel);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strHiddenNameChannelId);
        }
        break;
            
            
       case EDIT_PAYMENT_LEVEL:
        {
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          String strHiddenNamePaymentLevelId = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_HIDDEN_NAME_PAYMENT_LEVEL_ID);
          System.out.println("edit payment level "+strHiddenNamePaymentLevelId);
          PaymentLevelModel newPaymentLevelModel = BundleDao.getPaymentLevelById(con, strHiddenNamePaymentLevelId);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newPaymentLevelModel);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strHiddenNamePaymentLevelId);
        }
        break;     
            
            
        case UPDATE_PRODUCT:
        {
            
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          
          String strHiddenNameProductId = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          String productName = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_NAME);
          String productCatName = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_CAT_NAME);
          String productDesc = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_DESC);
          
          System.out.println("params : "+productName+"  "+productCatName);
          System.out.println("update product "+strHiddenNameProductId);
          
          
          ProductModel edittedProductModel = new ProductModel();
          
          edittedProductModel.setProductId(strHiddenNameProductId);
          edittedProductModel.setProductCategoryName(productCatName);
          edittedProductModel.setProductName(productName);
          edittedProductModel.setProductCategoryId(BundleDao.getProductCategoryIdByName(con, productCatName));
          edittedProductModel.setProductDesc(productDesc);
          
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, BundleDao.updateProduct(con, edittedProductModel));
          
          /*dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newProductModel);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strHiddenNameProductId);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, BundleDao.getProductCategories(con));*/
        }
        break;    
        
        case UPDATE_PAYMENT_LEVEL:
        {
            
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          
          String strHiddenNamePaymentLevelId = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          String edittedPaymentLevelName = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_TEXT_NAME_PAYMENT_LEVEL_NAME);
          
          System.out.println("params : "+edittedPaymentLevelName);
          System.out.println("update payment level "+strHiddenNamePaymentLevelId);
          
          
          PaymentLevelModel edittedPaymentLevelModel = new PaymentLevelModel();
          
          edittedPaymentLevelModel.setPaymentLevelId(strHiddenNamePaymentLevelId);
          edittedPaymentLevelModel.setPaymentLevelName(edittedPaymentLevelName);
          
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, BundleDao.updatePaymentLevel(con, edittedPaymentLevelModel));
          
        }
        break;        
            
            
        case UPDATE_CHANNEL:
        {
            
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          
          String strHiddenNameChannelId = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          String channelName = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_TEXT_NAME_CHANNEL_NAME);
          
          System.out.println("params : "+channelName);
          System.out.println("update channel "+strHiddenNameChannelId);
          
          
          ChannelModel edittedChannelModel = new ChannelModel();
          
          edittedChannelModel.setChannelId(/*BundleDao.getChannelIdByName(con, channelName)*/Integer.parseInt(strHiddenNameChannelId));
          edittedChannelModel.setChannelName(channelName);
          
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, BundleDao.updateChannel(con, edittedChannelModel));
          
          /*dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newProductModel);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strHiddenNameProductId);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, BundleDao.getProductCategories(con));*/
        }
        break;       
        /////////////////////////////////////////////////////////
            
        ///////////////////add and new actions: Product, Channel, PaymentLevel
        case ADD_PRODUCT:
        {
          System.out.println("add product action");
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          String productName = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_NAME);
          String productCatName = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_CAT_NAME);
          String productDesc = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_TEXT_NAME_PRODUCT_DESC);
          System.out.println("params : "+productName+"  "+productCatName);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, BundleDao.addNewProduct(con, productName, productCatName,productDesc));
          //dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(sdsCon));
        }
        break; 
            
            
        case ADD_CHANNEL:
        {
          System.out.println("add channel action");
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          String channelName = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_TEXT_NAME_CHANNEL_NAME);
          System.out.println("params : "+channelName);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, BundleDao.addNewChannel(con, channelName));
         // dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(sdsCon));
        }
        break;     
            
            
            
        case ADD_PAYMENT_LEVEL:
        {
          System.out.println("add payment level action");
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          String paymentLevelName = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_TEXT_NAME_PAYMENT_LEVEL_NAME);
          System.out.println("params : "+paymentLevelName);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, BundleDao.addNewPaymentLevel(con, paymentLevelName));
         // dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(sdsCon));
        }
        break;    
        
        case NEW_PRODUCT:
            System.out.println("new product");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, BundleDao.getProductCategories(con));
        
        break;
        ///////////////////////////////////////////////////
            
        case SHOW_BUNDLE_PRODUCT_TYPE:
          try
          {
            dataHashMap = new HashMap();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector productTypeVec = BundleDao.getAllBundleProductType(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,productTypeVec);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        
        break;

        case EDIT_BUNDLE_PRODUCT_TYPE:
        {
          try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strProductTypeID = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_HIDDEN_PRODUCT_TYPE_ID);
            System.out.println("The product type id isssssssssss "+strProductTypeID );
            BundleModel bundleModel = BundleDao.selectProductType(con,strProductTypeID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,bundleModel);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        }
        break;

        case UPDATE_BUNDLE_PRODUCT_TYPE:
        {
           try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strProductTypeId = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_HIDDEN_PRODUCT_TYPE_ID);
            String strProductTypeName = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_TEXT_PRODUCT_TYPE_NAME);
            BundleDao.updateProductType(con,strProductTypeId,strProductTypeName);
            Vector productTypeVec = BundleDao.getAllBundleProductType(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,productTypeVec);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        }
        break;

        case CREATE_NEW_PRODUCT_TYPE:
        try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;

        case SAVE_NEW_PRODUCT_TYPE:
         try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strProductTypeId = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_TEXT_PRODUCT_TYPE_ID);
            String strProductTypeName = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_TEXT_PRODUCT_TYPE_NAME);
            BundleDao.insertProductType(con,strProductTypeId,strProductTypeName);
            Vector productTypeVec = BundleDao.getAllBundleProductType(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,productTypeVec);
          }
           catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;

        case SHOW_BUNDLE_PROMOTION_TYPE:
        {
          try
          {
            dataHashMap = new HashMap();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector promotionTypeVec = BundleDao.getAllBundlePromotionType(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,promotionTypeVec);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        }
        break;

        case EDIT_BUNDLE_PROMOTION_TYPE:
        {
          try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strPromotionTypeID = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_HIDDEN_PROMOTION_TYPE_ID);
            BundleModel bundleModel = BundleDao.selectPromotionType(con,strPromotionTypeID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,bundleModel);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        }
        break;

        case CREATE_NEW_PROMOTION_TYPE:
        try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;

        case UPDATE_BUNDLE_PROMOTION_TYPE:
        {
           try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strPromotionTypeId = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_HIDDEN_PROMOTION_TYPE_ID);
            String strPromotionTypeName = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_TEXT_PROMOTION_TYPE_NAME);
            BundleDao.updatePromotionType(con,strPromotionTypeId,strPromotionTypeName);
            Vector promotionTypeVec = BundleDao.getAllBundlePromotionType(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,promotionTypeVec);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        }
        break;

        case SAVE_NEW_PROMOTION_TYPE:
         try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strPromotionTypeId = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_TEXT_PROMOTION_TYPE_ID);
            String strPromotionTypeName = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_TEXT_PROMOTION_TYPE_NAME);
            BundleDao.insertPromotionType(con,strPromotionTypeId,strPromotionTypeName);
            Vector promotionTypeVec = BundleDao.getAllBundlePromotionType(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,promotionTypeVec);
          }
           catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;

        case SHOW_BUNDLE_COMPONENT:
        try
          {
            dataHashMap = new HashMap();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            Vector bundleComponentVec = BundleDao.getAllBundleComponent(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,bundleComponentVec);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;

        case EDIT_BUNDLE_COMPONENT:
        try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strBundleComponentID = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_HIDDEN_BUNDLE_COMPONENT_ID);
            BundleModel bundleModel = BundleDao.selectBundleComponent(con,strBundleComponentID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,bundleModel);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;

        case CREATE_NEW_BUNDLE_COMPONENT:
        try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;

        case UPDATE_BUNDLE_COMPONENT:
        {
           try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strBundleComponentId = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_HIDDEN_BUNDLE_COMPONENT_ID);
            String strBundleComponentName = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_TEXT_BUNDLE_COMPONENT_NAME);
            BundleDao.updateBundleComponent(con,strBundleComponentId,strBundleComponentName);
            Vector bundleComponentVec = BundleDao.getAllBundleComponent(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,bundleComponentVec);
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        }
        break;

        case SAVE_NEW_BUNDLE_COMPONENT:
         try
          {
            dataHashMap = new HashMap ();
            strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String strBundleComponentId = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_TEXT_BUNDLE_COMPONENT_ID);
            String strBundleComponentName = (String)paramHashMap.get(AdministrationInterfaceKey.INPUT_TEXT_BUNDLE_COMPONENT_NAME);
            BundleDao.insertBundleComponent(con,strBundleComponentId,strBundleComponentName);
            Vector bundleComponentVec = BundleDao.getAllBundleComponent(con);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,bundleComponentVec);
          }
           catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        
        case LIST_ALL_MODULS:
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, ModuleDAO.getModulesList(con));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, ModuleDAO.getModuleStatuses(con));
        }
        break;
        case LIST_ALL_PRIVILEGES:
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, ModuleDAO.getModulesList(con));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, ModuleDAO.getPrivilegeStatuses(con));
        }
        break;
        case UPDATE_MODULS_STATUSES:
        {
          int nModuleID = 0;
          int nModuleStatusID = 0;
          CachEngine.removeObject(AdministrationInterfaceKey.CACH_OBJ_MODULE_LIST);
          for(int j=0; j<paramHashMap.size(); j++)
          {
            String tempString = (String)paramHashMap.keySet().toArray()[j];
            if(tempString.startsWith(AdministrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
            {
              nModuleID = new Integer(tempString.substring(
                                          tempString.lastIndexOf(
                                            AdministrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7)).intValue();
              nModuleStatusID = new Integer((String)paramHashMap.get(tempString)).intValue();
              ModuleDAO.updateModuleStatus(con, nModuleID, nModuleStatusID);
            }
          }
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, ModuleDAO.getModulesList(con));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, ModuleDAO.getModuleStatuses(con));
        }
        break;
        case UPDATE_PRIVILEGES_STATUSES:
        {
          int nPrivilegeID = 0;
          int nPrivilegeStatusID = 0;
          for(int j=0; j<paramHashMap.size(); j++)
          {
            String tempString = (String)paramHashMap.keySet().toArray()[j];
            if(tempString.startsWith(AdministrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
            {
              nPrivilegeID = new Integer(tempString.substring(
                                          tempString.lastIndexOf(
                                            AdministrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7)).intValue();
              nPrivilegeStatusID = new Integer((String)paramHashMap.get(tempString)).intValue();
              ModuleDAO.updatePrirvlegeStatus(con, nPrivilegeID, nPrivilegeStatusID);
            }
          }
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, ModuleDAO.getModulesList(con));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, ModuleDAO.getPrivilegeStatuses(con));
        }
        break;
        case SEARCH_REVENUE:
        {
        String month = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_MONTH);
        String year = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_YEAR);

        dataHashMap.put(AdministrationInterfaceKey.CONTROL_SELECT_MONTH ,month);
        dataHashMap.put(AdministrationInterfaceKey.CONTROL_INPUT_YEAR ,year);
        
        Utility.logger.debug("month " + month);
        Utility.logger.debug("year " + year);

        DCMDto dcmDto =  DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
        Hashtable revenueTable  = RevenueDao.getDCMRevenue(month, year);
        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,revenueTable);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmDto);
        }
        break;
        case UPDATE_REVENUE:
        {
        String month = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_MONTH);
        String year = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_YEAR);

        dataHashMap.put(AdministrationInterfaceKey.CONTROL_SELECT_MONTH ,month);
        dataHashMap.put(AdministrationInterfaceKey.CONTROL_INPUT_YEAR ,year);

        String[] revenueRowId = new String[0];
        Object revenueRowIdObj = paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_REVENUE_ID);
        if (revenueRowIdObj!=null && (!(revenueRowIdObj instanceof String )))
        {
          revenueRowId = (String[])revenueRowIdObj;
        }
        else if (revenueRowIdObj!=null && (revenueRowIdObj instanceof String))
        {
          revenueRowId = new String[1];
          revenueRowId[0] = (String)revenueRowIdObj;
        }

        String[] dcmIds = new String[0];
        Object dcmIdsObj = paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_DCM_ID);
        if (dcmIdsObj!=null && (!(dcmIdsObj instanceof String )))
        {
          dcmIds = (String[])dcmIdsObj;
        }
        else if (dcmIdsObj!=null && (dcmIdsObj instanceof String))
        {
          dcmIds = new String[1];
          dcmIds[0] = (String)dcmIdsObj;
        }
        String[] amounts = new String[0];
        Object amountsObj = paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_AMOUNT);
        if (amountsObj!=null && (!(amountsObj instanceof String )))
        {
          amounts = (String[])amountsObj;
        }
        else if (amountsObj!=null && (amountsObj instanceof String))
        {
          amounts = new String[1];
          amounts[0] = (String)amountsObj;
        }
        RevenueDao.updateRevenueAmount( revenueRowId, dcmIds,  amounts,year,month);        
        DCMDto dcmDto =  DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
        Hashtable revenueTable  = RevenueDao.getDCMRevenue(month, year);        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,revenueTable);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmDto);
        }
        break;
        
        ///////////////////////////////////////////////// payment level history /////////////
        case SAVE_PAYMENT_LEVEL_HISTORY:
        {
            System.out.println("SAVE Payment History");
            String month = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_MONTH);
            String year = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_YEAR);
            String userID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String historyId = "";
            
            dataHashMap.put(AdministrationInterfaceKey.CONTROL_SELECT_MONTH ,month);
            dataHashMap.put(AdministrationInterfaceKey.CONTROL_INPUT_YEAR ,year);

            Utility.logger.debug("month " + month);
            Utility.logger.debug("year " + year);
            System.out.println("month , year , userId : "+month+"  "+year+"  "+userID);
            boolean exists = PaymentLevelHistoryDao.checkHistoryFile(month, year, userID);
            System.out.println("exists : "+exists);
            if (!exists)
            {
                PaymentLevelHistoryDao.insertHistoryFile(month, year, userID);
                historyId = PaymentLevelHistoryDao.getHistoryFileId(month, year, userID);    
                System.out.println("history file id : "+historyId);
                PaymentLevelHistoryDao.insertHistoryFileDetail(historyId);
            }
            historyId = PaymentLevelHistoryDao.getHistoryFileId(month, year, userID);
            dataHashMap.put(AdministrationInterfaceKey.TEXT_PAY_LEVEL_HISTORY_ID ,historyId);
            dataHashMap.put(AdministrationInterfaceKey.TEXT_PAY_LEVEL_HISTORY_ID_EXISTS ,exists);
         //   DCMDto dcmDto =  DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
          //  Hashtable revenueTable  = RevenueDao.getDCMRevenue(month, year);

          //  dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,revenueTable);
          //  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmDto);
        }
        break;
            //////////////////////// Month of the List /////////////////////////
       case SAVE_LIST:
        {
            System.out.println("SAVE Month of the List");
            String month = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_MONTH);
            String year = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_YEAR);
            String list = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_LIST);
            String userID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String listId = "";
            
            dataHashMap.put(AdministrationInterfaceKey.CONTROL_SELECT_MONTH ,month);
            dataHashMap.put(AdministrationInterfaceKey.CONTROL_INPUT_YEAR ,year);
            dataHashMap.put(AdministrationInterfaceKey.CONTROL_SELECT_LIST ,list);

            Utility.logger.debug("month " + month);
            Utility.logger.debug("year " + year);
            Utility.logger.debug("list " + list);
            System.out.println("month , year , userId, list : "+month+"  "+year+"  "+userID+"  "+list);
            boolean exists = MonthOfTheListDao.checkHistoryFile(month, year, userID);
            System.out.println("exists ? : "+exists);
            if (!exists)
            {
                MonthOfTheListDao.insertHistoryFile(month, year, userID,list);
                listId = MonthOfTheListDao.getHistoryFileId(month, year, userID);    
                System.out.println("list file id : "+listId);
                MonthOfTheListDao.insertHistoryFileDetail(listId,list);
            }
            listId = MonthOfTheListDao.getHistoryFileId(month, year, userID);
            dataHashMap.put(AdministrationInterfaceKey.TEXT_LIST_OF_THE_MONTH_ID ,listId);
            dataHashMap.put(AdministrationInterfaceKey.TEXT_LIST_OF_THE_MONTH_ID_EXISTS ,exists);
         
        }
        break;
            /////////////////////////////////////////////
        case SEARCH_SCRATCH_CARD:
        {
        String month = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_MONTH);
        String year = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_YEAR);
        dataHashMap.put(AdministrationInterfaceKey.CONTROL_SELECT_MONTH ,month);
        dataHashMap.put(AdministrationInterfaceKey.CONTROL_INPUT_YEAR ,year);                        
        DCMDto dcmDto =  DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
        Hashtable scratchCardTable  = ScratchCardDao.getDCMScratchCard(month, year);        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,scratchCardTable);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmDto);
        }
        break;
        case UPDATE_SCRATCH_CARD:
        {
        String month = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_MONTH);
        String year = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_YEAR);

        dataHashMap.put(AdministrationInterfaceKey.CONTROL_SELECT_MONTH ,month);
        dataHashMap.put(AdministrationInterfaceKey.CONTROL_INPUT_YEAR ,year);

        String[] scratchCardRowId = new String[0];
        Object scratchCardIdObj = paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_SCRATCH_CARD_ID);
        if (scratchCardIdObj!=null && (!(scratchCardIdObj instanceof String )))
        {
          scratchCardRowId = (String[])scratchCardIdObj;
        }
        else if (scratchCardIdObj!=null && (scratchCardIdObj instanceof String))
        {
          scratchCardRowId = new String[1];
          scratchCardRowId[0] = (String)scratchCardIdObj;
        }

        String[] dcmIds = new String[0];
        Object dcmIdsObj = paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_DCM_ID);
        if (dcmIdsObj!=null && (!(dcmIdsObj instanceof String )))
        {
          dcmIds = (String[])dcmIdsObj;
        }
        else if (dcmIdsObj!=null && (dcmIdsObj instanceof String))
        {
          dcmIds = new String[1];
          dcmIds[0] = (String)dcmIdsObj;
        }
        String[] amounts = new String[0];
        Object amountsObj = paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_AMOUNT);
        if (amountsObj!=null && (!(amountsObj instanceof String )))
        {
          amounts = (String[])amountsObj;
        }
        else if (amountsObj!=null && (amountsObj instanceof String))
        {
          amounts = new String[1];
          amounts[0] = (String)amountsObj;
        }

        ScratchCardDao.updateScratchCardAmount( scratchCardRowId , dcmIds,  amounts,year,month);        
        DCMDto dcmDto =  DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
        Hashtable scratchCardTable  =  ScratchCardDao.getDCMScratchCard(month, year);        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,scratchCardTable);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmDto);
        }
        break;
            

        case SHOW_ACTIVATION:
         {
          Vector productsVector =  ProductDAO.getAllProducts();
          dataHashMap.put(AdministrationInterfaceKey.PRODUCT_VECTOR ,productsVector);
         }
        break;


        case SEARCH_ACTIVATION:
        {
        String month = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_MONTH);
        String year = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_YEAR);
        String productId = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_PRODUCT_ID);
        
        dataHashMap.put(AdministrationInterfaceKey.CONTROL_SELECT_MONTH ,month);
        dataHashMap.put(AdministrationInterfaceKey.CONTROL_INPUT_YEAR ,year);  
        dataHashMap.put(AdministrationInterfaceKey.CONTROL_SELECT_PRODUCT_ID ,productId);
                
        DCMDto dcmDto =  DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
        Vector productsVector =  ProductDAO.getAllProducts();
        
        Hashtable activationTable  = new Hashtable();    

        if (month!=null && year!=null)
        activationTable = ActivationDAO.getDCMActivation(month, year,productId);        

        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,activationTable);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmDto);
        dataHashMap.put(AdministrationInterfaceKey.PRODUCT_VECTOR ,productsVector);
        }
        
        break;
        case UPDATE_ACTIVATION:
        {
        String month = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_MONTH);
        String year = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_YEAR);
        String productId = (String) paramHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_PRODUCT_ID);
        
        dataHashMap.put(AdministrationInterfaceKey.CONTROL_SELECT_MONTH ,month);
        dataHashMap.put(AdministrationInterfaceKey.CONTROL_INPUT_YEAR ,year);
        dataHashMap.put(AdministrationInterfaceKey.CONTROL_SELECT_PRODUCT_ID ,productId);
        
        String[] rowId = new String[0];
        Object rowIdObj = paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_ACTIVATION_ID);
        if (rowIdObj!=null && (!(rowIdObj instanceof String )))
        {
          rowId = (String[])rowIdObj;
        }
        else if (rowIdObj!=null && (rowIdObj instanceof String))
        {
          rowId = new String[1];
          rowId[0] = (String)rowIdObj;
        }

        String[] dcmIds = new String[0];
        Object dcmIdsObj = paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_DCM_ID);
        if (dcmIdsObj!=null && (!(dcmIdsObj instanceof String )))
        {
          dcmIds = (String[])dcmIdsObj;
        }
        else if (dcmIdsObj!=null && (dcmIdsObj instanceof String))
        {
          dcmIds = new String[1];
          dcmIds[0] = (String)dcmIdsObj;
        }
        String[] amounts = new String[0];
        Object amountsObj = paramHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_AMOUNT);
        if (amountsObj!=null && (!(amountsObj instanceof String )))
        {
          amounts = (String[])amountsObj;
        }
        else if (amountsObj!=null && (amountsObj instanceof String))
        {
          amounts = new String[1];
          amounts[0] = (String)amountsObj;
        }

        ActivationDAO.updateActivationAmount( rowId , dcmIds,  amounts,year,month,productId);
        
        DCMDto dcmDto =  DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,con);
        Vector productsVector =  ProductDAO.getAllProducts();
        
        Hashtable activationTable  =  ActivationDAO.getDCMActivation(month, year,productId);        
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,activationTable);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,dcmDto);
        dataHashMap.put(AdministrationInterfaceKey.PRODUCT_VECTOR ,productsVector);
        }
        break;

        case SHOW_DATA_IMPORT_SCREEN:
        {
            Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("1");
            dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector); 
        }
        break;
          
            
            
        
        case SHOW_POS_BULK_UPDATE_IMPORT_SCREEN:
        {
            Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("43");
            dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector); 
        }
        break;
            
           
        case DELETE_NOMAD_FILE:
        {
            System.out.println("delete nomad file action");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String  fieldId =(String) paramHashMap.get("fieldId");
	    System.out.println("FILE ID ISSSSSSSSSSSS"+fieldId);
            String  strStatus =(String) paramHashMap.get("statusStr");
            NomadFileDAO.delNomadFileById(con, fieldId);
        }
        break;
          case EXPORT_PAYMENT_LEVEL_HISTORY:
          {
             // Vector<POSSearchExcelModel> dataVec = RequestDao.searchPosDataExcel(con, posDataOwnerIdType, posDataDocNum, posDataManagerName, posDataStkNum, posDataManagerIdType, posDataProposedDoc, posDataManagerIdNum, posDataName, posDataCode, posDataRegion, posDataGover, posDataDistrict, posDataArea, posDataCity, posDataOwnerName, posDataOwnerIdNum, Level, Payment, Channel, posStatusId, stkStatusId, psymentStatusId, posPhone, englishAddress, entryDate, docLocation, supervisorDetailId,supervisorDetailName, teamleaderDetailId, teamleaderDetailName, salesrepDetailId, salesrepDetailName);
            System.out.println("%%% export history file action");
              String Slach = System.getProperty("file.separator");
              System.out.println("BASE_DIRECTION test values "+paramHashMap.get("baseDirectory"));
              String baseDirectory = (String) paramHashMap.get("baseDirectory");//SCMInterfaceKey.BASE_DIRECTION
              String  fieldId =(String) paramHashMap.get("fieldId");
              System.out.println("file id value "+paramHashMap.get("fieldId"));
              Vector files =PaymentHistoryFileDAO.getallHistoryFiles(con,fieldId);
              String excelLink = PoiWriteExcelFile.exportExcelSheetForHistory(/*dataVec*/files, baseDirectory);
              dataHashMap.put(SCMInterfaceKey.SEARCH_EXCEL_SHEET_LINK, excelLink);
          }
          break;  
            
            case DELETE_HISTORY_FILE:
        {
            System.out.println("%%% delete history file action");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String  fieldId =(String) paramHashMap.get("fieldId");
	    System.out.println("FILE ID ISSSSSSSSSSSS"+fieldId);
            String  strStatus =(String) paramHashMap.get("statusStr");
            PaymentHistoryFileDAO.delHistoryFileById(con, fieldId);
        }
        break;
            
        case SHOW_NOMAD_FILE_IMPORT_SCREEN:
        {
            Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("43");
            dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector);
           // Vector labelVec= NomadLabelDao.getLabelByUser(con, strUserID);
           // dataHashMap.put(AuthResInterfaceKey.VECTOR_LABEL,labelVec); 
        }
        break;    
            
            
        /*case SHOW_NOMAD_FILE_IMPORT_SCREEN:
        {
            Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("43");
            dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector); 
        }
        break;   */
            
            
            
        case RUN_LINUX_COMMAND_INPUT:
        {
        }
        break;
        case RUN_LINUX_COMMAND_OUTPUT:
        {
          String absoluteFilePathe = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_LINUX_FILE_PATH);
          String sizeNeeded = (String)paramHashMap.get(AdministrationInterfaceKey.CONTROL_LINUX_FILE_SIZE_NEEDED);

          String linuxCommand = "tail -n "+sizeNeeded+" "+absoluteFilePathe;
          Utility.logger.debug("Linux command in handler : "+linuxCommand);
          String strResult = Utility.runLinuxCommand(linuxCommand);

          dataHashMap.put(AdministrationInterfaceKey.LINUX_COMMAND_RESULT,strResult);
        }
        break;
      }
      
    }
    catch(Exception objExp)
    {
      objExp.printStackTrace();
    }
    return dataHashMap;
  }
}
package com.mobinil.sds.core.facade.handlers;


import java.util.Vector;
import java.util.HashMap;


import java.sql.*;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.web.interfaces.aacm.AACMInterfaceKey;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.core.system.aacm.dao.AuthAgentDAO;
import com.mobinil.sds.core.system.aacm.dao.RevenueFileDao;
import com.mobinil.sds.core.system.sa.importdata.dao.*;
import com.mobinil.sds.core.system.aacm.model.*;
import com.mobinil.sds.core.system.aacm.util.ExcelGeneratorReport;
import com.mobinil.sds.core.system.commission.postarget.utils.CalendarUtility;
import com.mobinil.sds.core.system.sa.importdata.DataImportEngine;
import com.mobinil.sds.core.system.sa.importdata.ErrorInfo;
import com.mobinil.sds.core.system.sa.importdata.model.DataImportReport;
import java.io.File;
import javax.servlet.http.HttpServletRequest;


public class AACMHandler
{
	
    
	static final int VIEW_AUTHORIZED_AGENT             = 1;
	static final int CREATE_NEW_AUTHAGENT              = 2;
	static final int SAVE_NEW_AUTHAGENT                = 3;  
	static final int EDIT_AUTHAGENT                    = 4;
	static final int DELETE_AUTHAGENT                  = 5;
	static final int UPDATE_AUTHAGENT                  = 6;
	static final int VIEW_AMS_DATA                     = 7;
	static final int IMPORT_AMS_DATA                   = 8;
	static final int AUTHAGENT_DATA_IMPORT             = 9;
	static final int AUTHAGENT_DATA_IMPORT_PROCESS     = 10;
	static final int UPLOAD_AMS_DATA                   = 11;
	static final int UPLOAD_AMS_DATA_PROCESS		   = 12;
	static final int VIEW_AMS_DATA_FILE                = 13;
	static final int DELETE_AMS_DATA_FILE              = 14;
	static final int VIEW_AUTH_AGENT_FILE              = 17;
	static final int DELETE_AUTH_AGENT_FILE            = 18;
	static final int EXPORT_AMS_DATA_TO_EXCEL          = 19;
	static final int EXPORT_AUTH_AGENT_DATA_TO_EXCEL   = 20;
	static final int ACTION_UPLOAD_REVENUE_FILE=21;	
        static final int ACTION_MANAGEMENT_REVENUE_FILE=22;
        static final int ACTION_DELETE_REVENUE_FILE=23;
        static final int ACTION_EXPORT_REVENUE_FILE=24;
        static final int ACTION_UPLOAD_REVENUE_FILE_PROCESS=25;


	  public HashMap handle(String action, HashMap paramHashMap)
	  {
              int actionType = 0;
	    HashMap dataHashMap = new HashMap(100);
	    Connection  con = null;
//		String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        if (strUserID != null && strUserID.compareTo("") != 0) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        }
                        else
                        {
                        strUserID = getUserId(strUserID, paramHashMap);
                        }
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
	    
	  
	  
	  try 
	    {
	       con = Utility.getConnection();
	       if (action.equals(AACMInterfaceKey.ACTION_VIEW_AUTHORIZED_AGENT))                                   
	           actionType = VIEW_AUTHORIZED_AGENT;
	       else if (action.equals(AACMInterfaceKey.ACTION_CREATE_NEW_AUTHAGENT))
	    	   actionType = CREATE_NEW_AUTHAGENT;
	       else if (action.equals(AACMInterfaceKey.ACTION_SAVE_NEW_AUTHAGENT))
	    	   actionType = SAVE_NEW_AUTHAGENT;
	       else if (action.equals(AACMInterfaceKey.ACTION_EDIT_AUTHAGENT))
	    	   actionType = EDIT_AUTHAGENT;
	       else if (action.equals(AACMInterfaceKey.ACTION_DELETE_AUTHAGENT))
	    	   actionType = DELETE_AUTHAGENT ;
	       else if (action.equals(AACMInterfaceKey.ACTION_UPDATE_AUTHAGENT))
	    	   actionType = UPDATE_AUTHAGENT;
	       else if (action.equals(AACMInterfaceKey.ACTION_VIEW_AMS_DATA))
	    	   actionType = VIEW_AMS_DATA;
	       else if (action.equals(AACMInterfaceKey.ACTION_IMPORT_AMS_DATA))
	    	   actionType = IMPORT_AMS_DATA;
	       else if (action.equals(AACMInterfaceKey.ACTION_AUTHAGENT_DATA_IMPORT))
	    	   actionType = AUTHAGENT_DATA_IMPORT;
	       else if (action.equals(AACMInterfaceKey.ACTION_AUTHAGENT_DATA_IMPORT_PROCESS))
	    	   actionType = AUTHAGENT_DATA_IMPORT_PROCESS;
	       else if(action.equals(AACMInterfaceKey.ACTION_UPLOAD_AMS_DATA))
	    	   actionType = UPLOAD_AMS_DATA;
	       else if (action.equals(AACMInterfaceKey.ACTION_UPLOAD_AMS_DATA_PROCESS))
	    	   actionType = UPLOAD_AMS_DATA_PROCESS;
	       else if (action.equals(AACMInterfaceKey.ACTION_VIEW_AMS_DATA_FILE))
	    	   actionType = VIEW_AMS_DATA_FILE;
	       else if (action.equals(AACMInterfaceKey.ACTION_DELETE_AMS_DATA_FILE))
	    	   actionType = DELETE_AMS_DATA_FILE ;
	       else if (action.equals(AACMInterfaceKey.ACTION_VIEW_AUTH_AGENT_FILE))
	    	   actionType = VIEW_AUTH_AGENT_FILE;
	       else if(action.equals(AACMInterfaceKey.ACTION_DELETE_AUTH_AGENT_FILE))
	    	   actionType = DELETE_AUTH_AGENT_FILE;
	       else if (action.equals(AACMInterfaceKey.ACTION_EXPORT_AMS_DATA_TO_EXCEL))
	    	   actionType = EXPORT_AMS_DATA_TO_EXCEL;
	       else if (action.equals(AACMInterfaceKey.ACTION_EXPORT_AUTH_AGENT_DATA_TO_EXCEL))
	    	   actionType = EXPORT_AUTH_AGENT_DATA_TO_EXCEL;
               else if(action.equals(AACMInterfaceKey.ACTION_UPLOAD_REV_FILE))                        
                            actionType=ACTION_UPLOAD_REVENUE_FILE;
			else if(action.equals(AACMInterfaceKey.ACTION_MANAGEMENT_REV_FILE))                        
                            actionType=ACTION_MANAGEMENT_REVENUE_FILE;
			else if(action.equals(AACMInterfaceKey.ACTION_EXPORT_REV_FILE))                        
                            actionType=ACTION_EXPORT_REVENUE_FILE;
			else if(action.equals(AACMInterfaceKey.ACTION_DELETE_REV_FILE))                        
                            actionType=ACTION_DELETE_REVENUE_FILE;
			else if(action.equals(AACMInterfaceKey.ACTION_UPLOAD_REVENUE_FILE_PROCESS))                        
                            actionType=ACTION_UPLOAD_REVENUE_FILE_PROCESS;
	    	   
	       
	       switch (actionType)                                                                                     
	       {
                   case ACTION_MANAGEMENT_REVENUE_FILE:
                       {                                
                           RevenueFileDao dao = new RevenueFileDao( con);                            
                           dataHashMap.put(AACMInterfaceKey.VECTOR_OF_FILES, dao.getAllRevnFiles());
                       }
                       break;   
                   case ACTION_EXPORT_REVENUE_FILE:
                       {        
                           String fileId = (String)paramHashMap.get(AACMInterfaceKey.CONTROL_HIDDEN_FILE_ID_UPLOAD);
                           RevenueFileDao dao = new RevenueFileDao( con);                                 
                           ExcelGeneratorReport excelGen = new ExcelGeneratorReport(getTempFolderPath(paramHashMap),  Long.valueOf(fileId), dao);
                                String filepath = excelGen.getGeneratedReport();
                                System.out.println("filepath iss "+filepath);
                                dataHashMap.put(AACMInterfaceKey.EXPORT_FILE_REVENUE_PATH, filepath);                                
                                dataHashMap.put(AACMInterfaceKey.VECTOR_OF_FILES, dao.getAllRevnFiles());
                       }
                       break;   
                            case ACTION_DELETE_REVENUE_FILE:
                            {   
                                String fileId = (String)paramHashMap.get(AACMInterfaceKey.CONTROL_HIDDEN_FILE_ID_UPLOAD);
                                RevenueFileDao dao = new RevenueFileDao( con);                            
                                dao.deleteRevenueFile(fileId);
                                dataHashMap.put(AACMInterfaceKey.VECTOR_OF_FILES, dao.getAllRevnFiles());
                            }
                       break;   
                         case ACTION_UPLOAD_REVENUE_FILE:
                            {
                                dataHashMap.put(AACMInterfaceKey.ERROR_MSG, "");  
                            }
                        break;   
                         case ACTION_UPLOAD_REVENUE_FILE_PROCESS:
                            {
                                String monthId = (String)paramHashMap.get(AACMInterfaceKey.CONTROL_SELECT_MONTH_NAME),
                                        yearId=(String)paramHashMap.get(AACMInterfaceKey.CONTROL_SELECT_YEAR_NAME),                                        
                                        msg = "";
                                yearId = String.valueOf(new CalendarUtility().getYears().get(Integer.valueOf(yearId)));
                                 Vector<ErrorInfo> errors = new Vector<ErrorInfo>();       
                                RevenueFileDao dao = new RevenueFileDao(yearId, monthId, strUserID, con);
                                if (!dao.isIsExists()){
                                File excelFile = GetUploadedFile.getFile(paramHashMap, AACMInterfaceKey.CONTROL_FILE_REVENUE_FILE_UPLOAD);                                
                                dao.truncateTempTable();
                                DataImportEngine die = new DataImportEngine();
                                DataImportReport report = die.ImportFileWithRowNumber(excelFile.getAbsolutePath(), AdministrationInterfaceKey.DATA_IMPORT_INSERT, "60", strUserID);                                
                                errors = report.getReport();                                
                                long file_id = dao.beginValidateAndInsertDCMCodes();
                                if (errors.isEmpty())
                                msg = "File Uploaded Successfully.";    
                                else
                                msg = "File Uploaded Successfully but there are some errors.";        
                                }
                                else
                                {
                                msg = "Year and month already exists before.";
                                }                                
                                dataHashMap.put(AACMInterfaceKey.ERROR_MSG, msg);    
                                dataHashMap.put(AACMInterfaceKey.ERROR_VECTOR, errors);    
                            
                                

                       }
	       break;   
	       case VIEW_AUTHORIZED_AGENT:
	           try
	           {
	             dataHashMap = new HashMap ();
	             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	             String channelID = AACMInterfaceKey.CONST_GEN_CHANNEL_AUTHORIZED_AGENT;
	             dataHashMap.put(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelID);
	             Vector vecAuth = AuthAgentDAO.getAllAuthAgent(con,channelID);
	             dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecAuth);
	             
	           }
	           catch(Exception objExp)                                                                             
	           {                                     
	             objExp.printStackTrace();
	           }
	         break; 
	         
	       case CREATE_NEW_AUTHAGENT:
	           try
	           {
	             dataHashMap = new HashMap ();
	             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	             String channelID = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
	             dataHashMap.put(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelID);
	           }
	           catch(Exception objExp)
	           {
	             objExp.printStackTrace();
	           }
	         break;
	       
	       case SAVE_NEW_AUTHAGENT:
	    	   try
	    	   {
	    		  dataHashMap = new HashMap ();
		          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		          String authAgentCode = (String)paramHashMap.get(AACMInterfaceKey.INPUT_TEXT_AUTHAGENT_CODE);
		          String authAgentName = (String)paramHashMap.get(AACMInterfaceKey.INPUT_TEXT_AUTHAGENT_NAME);
		          String channelID = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
		          dataHashMap.put(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelID);
		          //Long genDcmId = AuthAgentDAO.insertGenDcm(con, authAgentCode, authAgentName,channelID);
		          boolean check = AuthAgentDAO.insertAuthAgent(con, authAgentCode,authAgentName,channelID);
		          if(check)
			        {
			          dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"This Authorized Agent does not exist in GEN_DCM");
			        }
		          Vector vecAuth = AuthAgentDAO.getAllAuthAgent(con,channelID);
		          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecAuth);
	    	   }
	    	   catch(Exception objExp)
	           {
	             objExp.printStackTrace();
	           }
	         break;
	         
	       case UPDATE_AUTHAGENT:
	    	   try
	    	   {
	    		   dataHashMap = new HashMap ();
			       dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			       String authAgentCode = (String)paramHashMap.get(AACMInterfaceKey.INPUT_TEXT_AUTHAGENT_CODE);
			       String authAgentName = (String)paramHashMap.get(AACMInterfaceKey.INPUT_TEXT_AUTHAGENT_NAME);
			       System.out.println("The authorized agent name issssssssss " + authAgentName);
			       String channelID = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
			       dataHashMap.put(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelID);
			       String dcmId = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_DCM_ID);
			       String oldAuthAgentName = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_NAME);
			       String oldAuthAgentCode = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_CODE);
			       boolean check = AuthAgentDAO.updateAuthAgent(con, oldAuthAgentCode,oldAuthAgentName,dcmId, authAgentName,authAgentCode,channelID);
			       //AuthAgentDAO.updateAuthAgentInGenDcm(con, dcmId, authAgentName, authAgentCode);
			       if(check)
			        {
			          dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"This Authorized Agent does not exist in GEN_DCM");
			        }
			       Vector vecAuth = AuthAgentDAO.getAllAuthAgent(con,channelID);
			       dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecAuth);
	    	   }
	    	   catch(Exception objExp)
	           {
	             objExp.printStackTrace();
	           }
	         break;
	         
	       case DELETE_AUTHAGENT:
	    	   try
	    	   {
	    		   dataHashMap = new HashMap ();
			       dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			       String channelID = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
			       dataHashMap.put(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelID);
			       String dcmId = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_DCM_ID);
			       String authAgentName = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_NAME);
			       String authAgentCode = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_CODE);
			       AuthAgentDAO.deleteAuthAgentFromAuthAgent(con, authAgentCode,authAgentName);
			       //AuthAgentDAO.deleteAuthAgentFromGenDcm(con, dcmId,channelID);
			       Vector vecAuth = AuthAgentDAO.getAllAuthAgent(con,channelID);
		           dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecAuth);
	    	   }
	    	   catch(Exception objExp)
	           {
	             objExp.printStackTrace();
	           }
	         break;
	         
	       case EDIT_AUTHAGENT:
	    	   try
	    	   {
	    		   dataHashMap = new HashMap ();
			       dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			       String channelID = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
			       dataHashMap.put(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelID);
			       String dcmId = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_DCM_ID);
			       AuthAgentModel authAgentModel = AuthAgentDAO.selectAuthAgent(con,dcmId,channelID );
			       dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,authAgentModel);
			       String authAgentName = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_NAME);
			       dataHashMap.put(AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_NAME, authAgentName);
			       String authAgentCode = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_CODE);
			       dataHashMap.put(AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_CODE, authAgentCode);
	    	   }
	    	   catch(Exception objExp)
	           {
	             objExp.printStackTrace();
	           }
	         break;
	         
	       case VIEW_AMS_DATA:
	    	   try
	    	   {
	    		   dataHashMap = new HashMap ();
			       dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);  
			       Vector DCMNotExist = new Vector();
	    		   dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,DCMNotExist);
	    	   }
	    	   catch(Exception objExp)
	           {
	             objExp.printStackTrace();
	           }
	         break;
	         
	       case IMPORT_AMS_DATA:
	    	   try
	    	   {
	    		   dataHashMap = new HashMap ();
			       dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			       String year = (String)paramHashMap.get(AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_YEAR);
			       System.out.println("The import year isssssssssss " + year);
			       String month = (String)paramHashMap.get(AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_MONTH);
			       System.out.println("The import month isssssssssss " + month);
	    		   AuthAgentDAO.insertAMSPortfolio(con, strUserID,year,month);
	    		   //AuthAgentDAO.updateAmsDataWithDcmExists();
	    		   AuthAgentDAO.updatePortfolio();
	    		   //Vector DCMNotExist = AuthAgentDAO.selectAuthorizedAgentNotExist();
	    		   //dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,DCMNotExist);
	    	   }
	    	   catch(Exception objExp)
	           {
	             objExp.printStackTrace();
	           }
	         break;
	         
	       case AUTHAGENT_DATA_IMPORT:
	    	   try
	    	   {
	    		   dataHashMap = new HashMap();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	               Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("23");
	               dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector);  
	    	   }
	    	   catch(Exception objExp)
	           {
	             objExp.printStackTrace();
	           }
	         break;
	         
	       case AUTHAGENT_DATA_IMPORT_PROCESS:
	    	   break;
	    	   
	       case UPLOAD_AMS_DATA:
	    	   try
	    	   {
	    	       dataHashMap = new HashMap();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	               Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("24");
	               dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector);   
	    	   }
	    	   catch(Exception objExp)
	           {
	             objExp.printStackTrace();
	           }
	         break;
	         
	       case UPLOAD_AMS_DATA_PROCESS:
	    	   System.out.println("sssssssssssssssssssssssss");
	    	   break;
	    	   
	       case VIEW_AMS_DATA_FILE:
	    	   try
	    	   {
	    		   dataHashMap = new HashMap();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	               Vector amsDataFileVec = AuthAgentDAO.getAMSImportFile();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,amsDataFileVec);
	    	   }
	    	   catch(Exception e)
	    	   {
	    		   e.printStackTrace();
	    	   }
	    	   break;
	    	   
	       case DELETE_AMS_DATA_FILE :
	    	   try
	    	   {
	    		   dataHashMap = new HashMap();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	               String fileId = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AMS_FILE_ID);
	               AuthAgentDAO.deleteAmsDataFile(fileId);
	               Vector amsDataFileVec = AuthAgentDAO.getAMSImportFile();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,amsDataFileVec);
	    	   }
	    	   catch(Exception e)
	    	   {
	    		   e.printStackTrace();
	    	   }
	    	   break;
	    	   
	       case VIEW_AUTH_AGENT_FILE:
	    	   try
	    	   {
	    		   dataHashMap = new HashMap();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	               //AuthAgentDAO.insertMailRobot();
	               Vector authAgentFileVec = AuthAgentDAO.getAuthAgentFile();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,authAgentFileVec);
	    	   }
	    	   catch(Exception e)
	    	   {
	    		   e.printStackTrace();
	    	   }
	    	   break;
	    	   
	       case DELETE_AUTH_AGENT_FILE:
	    	   try
	    	   {
	    		   dataHashMap = new HashMap();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	               String fileId = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AUTH_AGENT_FILE_ID);
	               AuthAgentDAO.deleteAuthAgentFile(fileId);
	               Vector authAgentFileVec = AuthAgentDAO.getAuthAgentFile();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,authAgentFileVec);
	    	   }
	    	   catch(Exception e)
	    	   {
	    		   e.printStackTrace();
	    	   }
	    	   break;
	    	   
	       case EXPORT_AMS_DATA_TO_EXCEL:
	    	   try
	    	   {
	    		   dataHashMap = new HashMap();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	               String fileId = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AMS_FILE_ID);
	               Vector amsDataVec = AuthAgentDAO.getAmsDataWithFile(fileId);
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, amsDataVec);
	    	   }
	    	   catch(Exception e)
	    	   {
	    		   e.printStackTrace();
	    	   }
	    	  break;
	    	  
	       case EXPORT_AUTH_AGENT_DATA_TO_EXCEL:
	    	   try
	    	   {
	    		   dataHashMap = new HashMap();
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	               String fileId = (String)paramHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AUTH_AGENT_FILE_ID);
	               Vector authAgentDataVec = AuthAgentDAO.getAuthAgentDataWithFile(fileId);
	               dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, authAgentDataVec);
	    	   }
	    	   catch(Exception e)
	    	   {
	    		   e.printStackTrace();
	    	   }
	    	  break;
	       
	       default:
	           Utility.logger.debug ("Unknown action received: " + action ); 
	       }
	       if (con!=null)
	       {
	       Utility.closeConnection(con);
	       } 
	       }
	  catch(Exception objExp)
	    {
	      objExp.printStackTrace();
	    }
	  return dataHashMap;
	    }
      private static String getUserId(String userId, HashMap paramHashMap) {
        userId = userId == null ? (String) ((HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET)).getSession(false).getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID) : userId;
        return userId;
    }
      private static String getTempFolderPath(HashMap paramHashMap){
        HttpServletRequest request = (HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET);
        return  request.getRealPath("/")+"downloadItems";        
        }
}

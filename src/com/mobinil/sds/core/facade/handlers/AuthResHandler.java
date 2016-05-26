package com.mobinil.sds.core.facade.handlers;
import java.sql.*;
import java.util.*;

import com.mobinil.sds.core.system.authenticationResult.dao.AuthResDAO;
import com.mobinil.sds.core.system.authenticationResult.dao.LabelDao;
import com.mobinil.sds.core.system.authenticationResult.dao.SearchCategroyDAO;
import com.mobinil.sds.core.system.sa.importdata.DataImportEngine;
import com.mobinil.sds.core.system.sa.importdata.ErrorInfo;
import com.mobinil.sds.core.system.sa.importdata.model.DataImportReport;
import com.mobinil.sds.core.system.sa.users.dao.UserDAO;
import com.mobinil.sds.core.system.scm.dao.PoiWriteExcelFile;
import com.mobinil.sds.core.utilities.DestoryRunningThread;
import com.mobinil.sds.core.utilities.GetUploadedFile;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey;
import com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey;
import com.mobinil.sds.web.interfaces.sa.UserInterfaceKey;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;






 public class AuthResHandler {

         static final int  VIEW_LABEL_MANAGEMENT =1;
	 static final int  CREATE_NEW_LABEL=2;
	 static final int  SAVE_NEW_LABEL=3;
	 static final int  DELETE_LABEL=4;
	 static final int  EDIT_LABEL=5;
	 static final int  UPDATE_LABEL=6;
	 static final int  AUTH_RES_IMPORT=7;
	 static final int  AUTH_RES_IMPORT_PROCESS=8;
	 static final int  VIEW_AUTH_RES_FILE=9;
         static final int  DELETE_AUTH_RES_FILE=10;
         static final int  VIEW_STATISTICS=11;
         static final int  AUTH_RES_SEARCH_IMPORT=12;
         static final int  AUTH_RES_SEARCH_IMPORT_PROCESS=13;
         static final int      DELETE_AUTH_SEARCH_RES_FILE=14;
         static final int        VIEW_AUTH_RES_SEARCH_FILE=15;
         static final int  VIEW_SEARCH_FILE_DATA=16;
         static final int  VIEW_SEARCH_FILE_INVALID_SIMS=17;
         static final int VIEW_USER_LABEL              = 18;
         static final int VIEW_LABEL_LIST              = 19;
         static final int ADD_NEW_USER                 = 20;
         static final int SAVE_NEW_USER                = 21;
         static final int ASSIGN_LABEL                 = 22;
         static final int SAVE_ASSIGNED_LABEL          = 23;
         static final int DELETE_USER                  = 24;
         static final int DELETE_USER_LABEL            = 25;
         static final int ASSIGN_LABEL_TO_USER         = 26;
         static final int UPDATE_USER_LABELS           = 27;
         static final int  VIEW_SEARCH_GATEGORY_MANAGEMENT =28;
         static final int  CREATE_NEW_SEARCH_CATEGORY=29;
         static final int  SAVE_NEW_SEARCH_CATEGORY=30;
         static final int  DELETE_SEARCH_CATEGORY=31;
         static final int  EDIT_SEARCH_CATEGORY=32;
         static final int  UPDATE_SEARCH_CATEGORY=33;
         static final int  SIM_INFO_IMPORT=34;
         static final int  VIEW_AUTH_SIM_INFO_FILE=35;
         static final int  DELETE_AUTH_SIM_INFO_FILE=36;
         static final int  DOWNLOAD_SIM_INFO_RESULT_FILE=37;
         
         static final int  ACTION_UPLOAD_VOC_MNP_MIGRA_PROCESS=38;
         static final int  ACTION_UPLOAD_DATA_LINE_PROCESS=39;
         static final int  ACTION_UPLOAD_ACHIV_PREPAID_PROCESS=40;
         static final int  ACTION_GROSS_ADDS_MANAGEMENT=41;
         static final int  ACTION_GROSS_ADDS_FILE_DELETE=42;
         
         
	  public static  HashMap handle(String action, HashMap paramHashMap,java.sql.Connection con)
	  {

	    int actionType = 0;
	    HashMap dataHashMap =   new HashMap();;
            HttpServletRequest request = (HttpServletRequest)paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET);
//            HttpSession session = request.getSession(false);
//            String  strUserIdSess = (String)session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
            String strUserIdSess = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        if (strUserIdSess != null && strUserIdSess.compareTo("") != 0) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserIdSess);
                        }
                        else
                        {
                        strUserIdSess = getUserId(strUserIdSess, paramHashMap);
                        }
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserIdSess); 
            
	   
    try 
	    {       
	      if  (action
					.equals(AuthResInterfaceKey.ACTION_VIEW_LABEL_MANAGEMENT))
				actionType = VIEW_LABEL_MANAGEMENT;
	      
	      else if  (action
                .equals(AuthResInterfaceKey.ACTION_VIEW_SEARCH_CATEGORY_MANAGEMENT))
            actionType =VIEW_SEARCH_GATEGORY_MANAGEMENT;
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_CREATE_NEW_LABEL))
				actionType =CREATE_NEW_LABEL;
			else if  (action
                  .equals(AuthResInterfaceKey.ACTION_CREATE_NEW_SEARCH_CATEGORY))
              actionType =CREATE_NEW_SEARCH_CATEGORY;
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_SAVE_NEW_LABEL))
				actionType =SAVE_NEW_LABEL;
	      
			else if  (action
                  .equals(AuthResInterfaceKey.ACTION_SAVE_NEW_SEARCH_CATEGORY))
              actionType =SAVE_NEW_SEARCH_CATEGORY;
			
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_DELETE_LABEL))
				actionType =DELETE_LABEL;
	      
			else if  (action
                  .equals(AuthResInterfaceKey.ACTION_DELETE_SEARCH_CATEGORY))
              actionType =DELETE_SEARCH_CATEGORY;
			
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_EDIT_LABEL))
				actionType =EDIT_LABEL;
			
			else if  (action
                  .equals(AuthResInterfaceKey.ACTION_EDIT_SEARCH_CATEGORY))
              actionType =EDIT_SEARCH_CATEGORY;
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_UPDATE_LABEL))
				actionType =UPDATE_LABEL;
	      
			else if  (action
                  .equals(AuthResInterfaceKey.ACTION_UPDATE_SEARCH_CATEGORY))
              actionType =UPDATE_SEARCH_CATEGORY;
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_AUTH_RES_IMPORT))
				actionType =AUTH_RES_IMPORT;
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_AUTH_RES_IMPORT_PROCESS))
				actionType =AUTH_RES_IMPORT_PROCESS;
			
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_VIEW_AUTH_RES_FILE))
				actionType =VIEW_AUTH_RES_FILE;
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_VIEW_AUTH_SIM_INFO_FILE))
				actionType =VIEW_AUTH_SIM_INFO_FILE;
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_DELETE_AUTH_SIM_INFO_FILE))
				actionType =DELETE_AUTH_SIM_INFO_FILE;
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_DELETE_AUTH_RES_FILE))
				actionType =DELETE_AUTH_RES_FILE;
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_VIEW_STATISTICS))
				actionType =VIEW_STATISTICS;
	      
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_AUTH_RES_SEARCH_IMPORT))
				actionType =AUTH_RES_SEARCH_IMPORT;
	      
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_AUTH_RES_SEARCH_IMPORT_PROCESS))
				actionType =AUTH_RES_SEARCH_IMPORT_PROCESS;
	      
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_DELETE_AUTH_SEARCH_RES_FILE))
				actionType =DELETE_AUTH_SEARCH_RES_FILE;
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_VIEW_AUTH_RES_SEARCH_FILE))
				actionType =VIEW_AUTH_RES_SEARCH_FILE;
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_VIEW_SEARCH_FILE_DATA))
				actionType =VIEW_SEARCH_FILE_DATA;
	      
			else if  (action
					.equals(AuthResInterfaceKey.ACTION_VIEW_SEARCH_FILE_INVALID_SIMS))
				actionType =VIEW_SEARCH_FILE_INVALID_SIMS;
			else if(action.equals(AuthResInterfaceKey.ACTION_VIEW_USER_LABEL))
				actionType = VIEW_USER_LABEL;
			else if (action.equals(AuthResInterfaceKey.ACTION_VIEW_LABEL_LIST))
				actionType = VIEW_LABEL_LIST;
			else if (action.equals(AuthResInterfaceKey.ACTION_ADD_NEW_USER))
				actionType = ADD_NEW_USER;
			else if (action.equals(AuthResInterfaceKey.ACTION_SAVE_NEW_USER))
				actionType = SAVE_NEW_USER;
			else if (action.equals(AuthResInterfaceKey.ACTION_ASSIGN_LABEL))
				actionType = ASSIGN_LABEL;
			else if (action.equals(AuthResInterfaceKey.ACTION_SAVE_ASSIGNED_LABEL))
				actionType = SAVE_ASSIGNED_LABEL;
			else if (action.equals(AuthResInterfaceKey.ACTION_DELETE_USER))
				actionType = DELETE_USER;
			else if (action.equals(AuthResInterfaceKey.ACTION_DELETE_USER_LABEL))
				actionType = DELETE_USER_LABEL;
			else if (action.equals(AuthResInterfaceKey.ACTION_ASSIGN_LABEL_TO_USER))
				actionType = ASSIGN_LABEL_TO_USER;
			else if (action.equals(AuthResInterfaceKey.ACTION_UPDATE_USER_LABELS))
				actionType = UPDATE_USER_LABELS;
			else if (action.equals(AuthResInterfaceKey.ACTION_SIM_INFO_IMPORT))
				actionType = SIM_INFO_IMPORT;
			else if (action.equals(AuthResInterfaceKey.ACTION_DOWNLOAD_SIM_INFO_RESULT_FILE))
				actionType = DOWNLOAD_SIM_INFO_RESULT_FILE;
			else if (action.equals(AuthResInterfaceKey.ACTION_UPLOAD_VOC_MNP_MIGRA_PROCESS))
				actionType = ACTION_UPLOAD_VOC_MNP_MIGRA_PROCESS;
			else if (action.equals(AuthResInterfaceKey.ACTION_UPLOAD_DATA_LINE_PROCESS))
				actionType = ACTION_UPLOAD_DATA_LINE_PROCESS;
			else if (action.equals(AuthResInterfaceKey.ACTION_UPLOAD_ACHIV_PREPAID_PROCESS))
				actionType = ACTION_UPLOAD_ACHIV_PREPAID_PROCESS;
			else if (action.equals(AuthResInterfaceKey.ACTION_GROSS_ADDS_MANAGEMENT))
				actionType = ACTION_GROSS_ADDS_MANAGEMENT;
			else if (action.equals(AuthResInterfaceKey.ACTION_GROSS_ADDS_FILE_DELETE))
				actionType = ACTION_GROSS_ADDS_FILE_DELETE;

   switch (actionType) {
    
       case ACTION_UPLOAD_VOC_MNP_MIGRA_PROCESS: {
           String tableName = "AUTH_VOC_MNP_MIGR";
           uploadGrossAddsExcel(con, paramHashMap, dataHashMap, tableName, strUserIdSess, true);          
       }
       break;
       case ACTION_UPLOAD_DATA_LINE_PROCESS: {
           String tableName = "AUTH_DATA_LINE";
           uploadGrossAddsExcel(con, paramHashMap, dataHashMap, tableName, strUserIdSess, false);           
       }
       break;
       case ACTION_UPLOAD_ACHIV_PREPAID_PROCESS: {
           String tableName = "AUTH_ACHIV_PREPAID";
           uploadGrossAddsExcel(con, paramHashMap, dataHashMap, tableName, strUserIdSess, false);                      
       }
       break;
       case ACTION_GROSS_ADDS_MANAGEMENT: {
           dataHashMap.put(AuthResInterfaceKey.VECTOR_OF_ALL_GROSS_ADDS_FILES, AuthResDAO.getGrossAddsFiles(con));
       }
       break;
       case ACTION_GROSS_ADDS_FILE_DELETE: {
           String fileId=(String)paramHashMap.get(AuthResInterfaceKey.CONTROL_HIDDEN_DELETE_FILE_ID);
           AuthResDAO.deleteGrossAddsFiles(con, fileId);           
           dataHashMap.put(AuthResInterfaceKey.VECTOR_OF_ALL_GROSS_ADDS_FILES, AuthResDAO.getGrossAddsFiles(con));
       }
       break;
    case VIEW_LABEL_MANAGEMENT:
		try {

			dataHashMap = new HashMap();
		
			String strUserID = (String) paramHashMap
					.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			Vector vecLabel = new Vector();

			vecLabel = LabelDao.getLabel(con);
					
           dataHashMap.put(AuthResInterfaceKey.VECTOR_LABEL,vecLabel);
			
			

		} catch (Exception objExp) {
			objExp.printStackTrace();
		}
		break;
		
    case VIEW_SEARCH_GATEGORY_MANAGEMENT:
       try {
          
           dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,(String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID));
           fillSearchCategoryVector(con,dataHashMap,"");
          
       } catch (Exception objExp) {
           objExp.printStackTrace();
       }
       break;
       	
		

	case CREATE_NEW_LABEL:
		try {
			dataHashMap = new HashMap();
            String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          	dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
           Vector service = new Vector();
			dataHashMap.put(AuthResInterfaceKey.VECTOR_LABEL,service);
			 dataHashMap.put("Action",action);
       } catch (Exception objExp) {
			objExp.printStackTrace();
		}
		break;
		
		
	  case CREATE_NEW_SEARCH_CATEGORY:
	        try {	            
	            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,(String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID));	           
	            dataHashMap.put(AuthResInterfaceKey.VECTOR_SEARCH_CATEGORY,new Vector());
	             dataHashMap.put("Action",action);
	       } catch (Exception objExp) {
	            objExp.printStackTrace();
	        }
	        break;
		
	case SAVE_NEW_LABEL:
		try {
			dataHashMap = new HashMap();
      
           
			String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			String Name="";
			String Description="";
			Name=(String)paramHashMap.get(AuthResInterfaceKey.INPUT_TEXT_LABEL_NAME);
			System.out.println("the label name isssss"+Name);
			Description=(String)paramHashMap.get(AuthResInterfaceKey.INPUT_TEXT_LABEL_DESCRIPTION);

				if (Description.length()==0){
					
					Description=" ";	
					
				}
				 LabelDao.insertNewLabel(con,Name,Description);
				dataHashMap.put(AuthResInterfaceKey.VECTOR_LABEL,LabelDao.getLabel(con));

		} catch (Exception objExp) {
			objExp.printStackTrace();
		}
		break;
		
		
	case SAVE_NEW_SEARCH_CATEGORY:
       try {
          
           dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,(String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID));           
           String catName=(String)paramHashMap.get(AuthResInterfaceKey.INPUT_TEXT_CATEGROY_NAME);           
           String catDescription=(String)paramHashMap.get(AuthResInterfaceKey.INPUT_TEXT_CATEGORY_DESCRIPTION);           
           if (catDescription.length()==0)catDescription=" ";    
                   
           SearchCategroyDAO.addNewSearchCategory(con,catName,catDescription);
               dataHashMap=fillSearchCategoryVector(con,dataHashMap,"");

       } catch (Exception objExp) {
           objExp.printStackTrace();
       }
       break;
		
	case DELETE_LABEL:
		try {
			dataHashMap = new HashMap();
      
           
			String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			String labelId=(String) paramHashMap.get("fieldId");
			
		if(LabelDao.checkifDataExists(con,labelId))
		{
			
			
		 String message="data exists";
			
	    dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,message);
	    dataHashMap.put(AuthResInterfaceKey.VECTOR_LABEL,LabelDao.getLabel(con));
			
			
		}
		
		else{	
		
		     LabelDao.deleeteLabel(con, labelId);
             dataHashMap.put(AuthResInterfaceKey.VECTOR_LABEL,LabelDao.getLabel(con));
		}

		} catch (Exception objExp) {
			objExp.printStackTrace();
		}
		break;
		
		
		
	case DELETE_SEARCH_CATEGORY:
       try {      
           dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,(String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID));
           String catId=(String) paramHashMap.get(AuthResInterfaceKey.INPUT_HIDDEN_SEARCH_CATEGORY_ID);
           
       if(SearchCategroyDAO.checkExsitsCategory ( con , catId ))
       {   
        String message="There are some files using this category.";           
       dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,message);    
       }       
       else{   
       
          SearchCategroyDAO.deleteSearchCategory ( con , catId );  
       }
       dataHashMap = fillSearchCategoryVector ( con , dataHashMap , "" );

       } catch (Exception objExp) {
           objExp.printStackTrace();
       }
       break;

	case EDIT_LABEL:
		try {
			dataHashMap = new HashMap();
			
			 	
				String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
				dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
				String labelId=(String) paramHashMap.get("fieldId");
                Vector service =LabelDao.getLabelforSpecificId(con,labelId);
                dataHashMap.put(AuthResInterfaceKey.VECTOR_LABEL,service);
                dataHashMap.put("Action",action);
                

		} catch (Exception objExp) {
			objExp.printStackTrace();
		}
		break;
		
		
	case EDIT_SEARCH_CATEGORY:
       try {    
               
               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,(String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID));                              
               dataHashMap = fillSearchCategoryVector ( con , dataHashMap , (String) paramHashMap.get(AuthResInterfaceKey.INPUT_HIDDEN_SEARCH_CATEGORY_ID) );
               dataHashMap.put("Action",action);
               

       } catch (Exception objExp) {
           objExp.printStackTrace();
       }
       break;
		
	case UPDATE_LABEL:
		try {
			dataHashMap = new HashMap();
      
           
			String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			String Name="";
			String Description="";
			Name=(String)paramHashMap.get(AuthResInterfaceKey.INPUT_TEXT_LABEL_NAME);
			Description=(String)paramHashMap.get(AuthResInterfaceKey.INPUT_TEXT_LABEL_DESCRIPTION);
			String labelId=(String) paramHashMap.get(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID);
			Name=(String)paramHashMap.get(AuthResInterfaceKey.INPUT_TEXT_LABEL_NAME);
			Description=(String)paramHashMap.get(AuthResInterfaceKey.INPUT_TEXT_LABEL_DESCRIPTION);
			 LabelDao.updateLabel(con, labelId, Name, Description);
			dataHashMap.put(AuthResInterfaceKey.VECTOR_LABEL,LabelDao.getLabel(con));
	

		} catch (Exception objExp) {
			objExp.printStackTrace();
		}
		break;
    
		
	case UPDATE_SEARCH_CATEGORY:
       try {
           dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,(String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID));
           String catName=(String)paramHashMap.get(AuthResInterfaceKey.INPUT_TEXT_CATEGROY_NAME);
           String catDescription=(String)paramHashMap.get(AuthResInterfaceKey.INPUT_TEXT_CATEGORY_DESCRIPTION);           
           String catId=(String) paramHashMap.get(AuthResInterfaceKey.INPUT_HIDDEN_SEARCH_CATEGORY_ID);
           SearchCategroyDAO.editSearchCategory ( con , catId , catName , catDescription );            
           dataHashMap = fillSearchCategoryVector ( con , dataHashMap , "" );
   

       } catch (Exception objExp) {
           objExp.printStackTrace();
       }
       break;

      case AUTH_RES_IMPORT:
            try                                                                                                 
            {
                dataHashMap = new HashMap();
                String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
                Vector labelVec= LabelDao.getLabelByUser(con, strUserID);
                dataHashMap.put(AuthResInterfaceKey.VECTOR_LABEL,labelVec); 
            }                                                                                                   
            catch(Exception objExp)                                                                             
            {                                     
              objExp.printStackTrace();
            }
          break;
      case SIM_INFO_IMPORT:{
            try                                                                                                 
            {
                dataHashMap = new HashMap();                
                Vector labelVec= LabelDao.getLabelByUser(con, "-11");
                dataHashMap.put(AuthResInterfaceKey.VECTOR_LABEL,labelVec.get(0)); 
            }                                                                                                   
            catch(Exception objExp)                                                                             
            {                                     
              objExp.printStackTrace();
            }
          break;
      }
     case AUTH_RES_IMPORT_PROCESS:
     	 
    	  try{
  	       dataHashMap = new HashMap();
           String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
           dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
  	  }
  	   catch(Exception objException)
  	   {
  		   
  		  objException.printStackTrace();   
  		   
  	   }

       break;
       
		case VIEW_AUTH_RES_FILE:
		 {
                    String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
                    Vector files =AuthResDAO.getallAuthResfiles(con,strUserID);
	            dataHashMap.put(AuthResInterfaceKey.VECTOR_FILES,files);
              
        }
			break;
	case VIEW_AUTH_SIM_INFO_FILE: {
           updateVectorOfSimFiles(con, dataHashMap, strUserIdSess);
           break;
       }
       
			
			
		case DELETE_AUTH_RES_FILE:
		 {
				String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
				 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
				 String  fieldId =(String) paramHashMap.get("fieldId");
				 System.out.println("FILE ID ISSSSSSSSSSSS"+fieldId);
                                String  strStatus =(String) paramHashMap.get("statusStr");
                                if (strStatus!=null && strStatus.equalsIgnoreCase(AuthResInterfaceKey.STATUS_PROCESSING))
                                {
                                    String threadId = AuthResDAO.getThreadId(con,fieldId);
                                    DestoryRunningThread drt = new DestoryRunningThread();
                                    Boolean isIdExists = drt.stopThreadWithId(threadId);
                                    if (isIdExists)
                                    {
                                    AuthResDAO.delAuthResDataForProcessingThreads(con, fieldId);
                                    }
                                }
                                else
                                {
                                 AuthResDAO.updateAuthResStatus(con,fieldId,"Deleted");
                                }

				
              Vector files =AuthResDAO.getallAuthResfiles(con,strUserID);
              dataHashMap.put(AuthResInterfaceKey.VECTOR_FILES,files);
            
        }
			break;
	      case DELETE_AUTH_SIM_INFO_FILE: {
           String fieldId = (String) paramHashMap.get("fieldId");
           AuthResDAO.updateAuthSIMStatus(con, fieldId, "Deleted");
           updateVectorOfSimFiles(con, dataHashMap, strUserIdSess);
           break;
       }			
			
		case VIEW_STATISTICS:
		 {
				 String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
				 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
				 String  fieldId =(String) paramHashMap.get("fieldId");
                 dataHashMap.put(AuthResInterfaceKey.VECTOR_STATISTICS,AuthResDAO.getStatistics(con, fieldId));
         }
			break;
			
			
			
			
	      case AUTH_RES_SEARCH_IMPORT:
	            try                                                                                                 
	            {
	                dataHashMap = new HashMap();
	                String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	                dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	                Vector labelVec= LabelDao.getLabelByUser(con, strUserID);
	                dataHashMap.put(AuthResInterfaceKey.VECTOR_LABEL,labelVec);
	                dataHashMap = fillSearchCategoryVector ( con , dataHashMap , "" );
	                
	            }                                                                                                   
	            catch(Exception objExp)                                                                             
	            {                                     
	              objExp.printStackTrace();
	            }
	          break;
	     case AUTH_RES_SEARCH_IMPORT_PROCESS:
	     	 
	    	  try{
	  	       dataHashMap = new HashMap();
	           String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	           dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	  	      }
	  	   catch(Exception objException)
	  	      {
	  		   
	  		  objException.printStackTrace();   
	  		   
	  	      }

	       break;
	       
	       
			case VIEW_AUTH_RES_SEARCH_FILE:
			 {
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					Vector files =AuthResDAO.getallAuthResSearchfiles(con,strUserID);
		            dataHashMap.put(AuthResInterfaceKey.VECTOR_SEARCH_FILES,files);
	              
	        }
				break;
	       
	       
	       
			case DELETE_AUTH_SEARCH_RES_FILE:
			 {
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					 String  fieldId =(String) paramHashMap.get("fieldId");
					 System.out.println("FILE ID ISSSSSSSSSSSS"+fieldId);
					 AuthResDAO.updateAuthResASearchStatusFile(con,fieldId,"Deleted");
	              Vector files =AuthResDAO.getallAuthResSearchfiles(con,strUserID);
	              dataHashMap.put(AuthResInterfaceKey.VECTOR_SEARCH_FILES,files);
	            
	        }
				break;
				
				
				
			case VIEW_SEARCH_FILE_DATA:
			 {
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					 String  fieldId =(String) paramHashMap.get("fieldId");
					 System.out.println("FILE ID ISSSSSSSSSSSS"+fieldId);
					 dataHashMap.put(AuthResInterfaceKey.CONTROL_HIDDEN_FILE_ID,fieldId);
					
					 
	            //  Vector files =AuthResDAO.getallAuthResSearchfiles(con);
	           dataHashMap.put(AuthResInterfaceKey.VECTOR_SEARCH_FILES_DATA, AuthResDAO.getallAuthResSearchfileData(con, fieldId));
	            
	        }
				break;
				
				
				
				
			case VIEW_SEARCH_FILE_INVALID_SIMS:
			 {
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					 String  fieldId =(String) paramHashMap.get("fieldId");
					 System.out.println("FILE ID ISSSSSSSSSSSS"+fieldId);
					 dataHashMap.put(AuthResInterfaceKey.CONTROL_HIDDEN_FILE_ID,fieldId);

	                 dataHashMap.put(AuthResInterfaceKey.VECTOR_SEARCH_FILES_INVALID_SIM, AuthResDAO.getInvalidsimSerials(con, fieldId));
	            
	        }
				break;
				
			case VIEW_USER_LABEL:
				try
				{
					dataHashMap = new HashMap();
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					 Vector userLabelVec = AuthResDAO.getUserLabel(con);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, userLabelVec);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			break;
			
			case VIEW_LABEL_LIST:
				try
				{
					dataHashMap = new HashMap();
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					 String userId = (String)paramHashMap.get(AuthResInterfaceKey.INPUT_HIDDEN_USER_ID);
					 dataHashMap.put(AuthResInterfaceKey.INPUT_HIDDEN_USER_ID, userId);
					 Vector labelVec = AuthResDAO.getLabelList(con, userId);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, labelVec);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			break;
			
			case ADD_NEW_USER:
				try
				{
					dataHashMap = new HashMap();
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			break;
			
			case SAVE_NEW_USER:
				try
				{
					dataHashMap = new HashMap();
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					String userId = (String)paramHashMap.get(AuthResInterfaceKey.INPUT_TEXT_USER_ID);
					int check = AuthResDAO.insertNewUser(con, userId);
					if(check == 1)
					{
						dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"This User is already exist in the USER Label Table");
					}
					else if(check == 2)
					{
						dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"This User does not exist in the system");
					}
					
					Vector userLabelVec = AuthResDAO.getUserLabel(con);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, userLabelVec);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			break;
			
			case ASSIGN_LABEL:
				try
				{
					dataHashMap = new HashMap();
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					String userId = (String)paramHashMap.get(AuthResInterfaceKey.INPUT_HIDDEN_USER_ID);
					 dataHashMap.put(AuthResInterfaceKey.INPUT_HIDDEN_USER_ID, userId);
					 Vector labelVec = AuthResDAO.getLabelByUser(con, userId);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, labelVec);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			break;
			
			case SAVE_ASSIGNED_LABEL:
				try
				{
					dataHashMap = new HashMap();
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					String userId = (String)paramHashMap.get(AuthResInterfaceKey.INPUT_HIDDEN_USER_ID);
					 dataHashMap.put(AuthResInterfaceKey.INPUT_HIDDEN_USER_ID, userId);
					 String labelId = (String)paramHashMap.get(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID);
					 AuthResDAO.assignLabelToUser(con, userId, labelId);
					 Vector labelVec = AuthResDAO.getLabelList(con, userId);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, labelVec);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			break;
			
			case DELETE_USER:
				try
				{
					dataHashMap = new HashMap();
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					String userId = (String)paramHashMap.get(AuthResInterfaceKey.INPUT_HIDDEN_USER_ID);
					AuthResDAO.deleteUser(con, userId);
					Vector userLabelVec = AuthResDAO.getUserLabel(con);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, userLabelVec);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			break;
			
			case DELETE_USER_LABEL:
				try
				{
					dataHashMap = new HashMap();
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					String userId = (String)paramHashMap.get(AuthResInterfaceKey.INPUT_HIDDEN_USER_ID);
					dataHashMap.put(AuthResInterfaceKey.INPUT_HIDDEN_USER_ID, userId);
					String labelId = (String)paramHashMap.get(AuthResInterfaceKey.INPUT_HIDDEN_label_ID);
					AuthResDAO.deleteUserLabel(con, userId, labelId);
					Vector labelVec = AuthResDAO.getLabelList(con, userId);
					 dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, labelVec);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			break;
                            
                        case DOWNLOAD_SIM_INFO_RESULT_FILE:
                        {
                        String baseDirectory =request.getRealPath("/authRes/uploadtext/")+System.getProperty("file.separator");      
                        String fileId = (String)paramHashMap.get(AuthResInterfaceKey.FILE_ID);
                        PoiWriteExcelFile writer = new PoiWriteExcelFile();                        
                        String fileExported = writer.buildSimInfoExcelFile(con,fileId,baseDirectory);                       
                            dataHashMap.put(AuthResInterfaceKey.SIM_INFO_FILE_DOWNLOAD_PATH, fileExported);
                        break;
                        }
			
			case ASSIGN_LABEL_TO_USER:
				try
				{
					dataHashMap = new HashMap();
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(con));
					Vector labelVec = AuthResDAO.getAllLabels(con);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelVec);
					Vector userLabelVec = AuthResDAO.getAllUserLabel(con);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, userLabelVec);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			break;
			
			case UPDATE_USER_LABELS:
				try
				{
					dataHashMap = new HashMap();
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					int paramHashMapSize = paramHashMap.size();
//	              String groupIdKey = (String) paramHashMap.get(ReportInterfaceKey.PARAM_GROUP_ID) ;
//	              int nGroupID = new Integer (groupIdKey).intValue() ;
	              String strPersonID = (String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID);
	              AuthResDAO.deleteUser(con, strPersonID)   ;             
	              for(int i=0;i<paramHashMapSize;i++) 
	              {
	                  String tempKey = (String) paramHashMap.keySet().toArray()[i];
	                  //System.out.println(paramHashMap.get(tempKey));
//	                  String tempValue = (String)paramHashMap.get(tempKey);
	                  //Utility.logger.debug("wwwwwww"+tempKey+"-----------------"+tempValue);

	                  if(tempKey.startsWith(UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+strPersonID)) 
	                  {
	                      String labelIdKey = tempKey.substring((UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+strPersonID).length());
	                      //Utility.logger.debug(reportId);
	                      System.out.println("The label id issssssssssss " + labelIdKey);
	                      AuthResDAO.updateUserLabel(con, strPersonID, labelIdKey);
	                  }
	              }
	              dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(con));
					Vector labelVec = AuthResDAO.getAllLabels(con);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelVec);
					Vector userLabelVec = AuthResDAO.getAllUserLabel(con);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, userLabelVec);
	              
				}
				catch(Exception e)
				{
					e.printStackTrace();
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
	  
	  /**
	   * @param con DB connection
	   * @param dataHashMap hashmap that will increase with all categries
	   * @param catId category id if it null or empty vector will get all categories else vector contain one model
	   * 
	   * @return dataHashMap hashmap after adding all categries
	   * @author mabdelaal
	   * */
	  public static HashMap fillSearchCategoryVector(Connection con,HashMap dataHashMap,String catId)
	  {  
        dataHashMap.put(AuthResInterfaceKey.VECTOR_SEARCH_CATEGORY,SearchCategroyDAO.getAllSearchCategories ( con,catId ));
        return dataHashMap; 
	  }
          private static void updateVectorOfSimFiles(Connection con, HashMap dataHashMap,String strUserIdSess){
              Vector files =AuthResDAO.getallAuthSimINfofiles(con,strUserIdSess);
              dataHashMap.put(AuthResInterfaceKey.VECTOR_FILES,files);
          }
          private static String getUserId(String userId, HashMap paramHashMap) {
        userId = userId == null ? (String) ((HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET)).getSession(false).getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID) : userId;
        return userId;
    }
        private static String getTempFolderPath(HashMap paramHashMap){
        HttpServletRequest request = (HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET);
        return  request.getRealPath("/")+"downloadItems";        
        }
        
        private static void uploadGrossAddsExcel(Connection con , HashMap paramHashMap , HashMap dataHashMap,String tableName ,String strUserIdSess,boolean isLabeled) throws Exception{
        String typeId = (String) paramHashMap.get(AuthResInterfaceKey.CONTROL_HIDDEN_UPLOAD_FILE_TYPE_ID);
           String month = (String) paramHashMap.get(AuthResInterfaceKey.CONTROL_SELECT_MONTH_NUMBER);
           String year = (String) paramHashMap.get(AuthResInterfaceKey.CONTROL_SELECT_YEAR_NUMBER);           
           String label = isLabeled ? (String) paramHashMap.get(AuthResInterfaceKey.CONTROL_SELECT_LABEL_ID) : null;
           String primaryKeyName = "", sequenceName = "";
           if (typeId.compareTo("1")==0)
           {
               primaryKeyName = "AUTH_VOC_MNP_MIGR_ID";
               sequenceName = "AUTH_VOC_MNP_MIGR_ID_SEQ";
           }
           else if (typeId.compareTo("2")==0)
           {
               primaryKeyName = "AUTH_ACHIV_PREPAID_ID";
               sequenceName = "AUTH_ACHIV_PREPAID_ID_SEQ";
           }
           else if (typeId.compareTo("3")==0)
           {
               primaryKeyName = "AUTH_DATA_LINE_ID";
               sequenceName = "AUTH_DATA_LINE_ID_SEQ";
           }
           if (!AuthResDAO.checkMonthAndYearPerTypeForUploadedFile(con, month, year, typeId)){ 
           String fileId = AuthResDAO.insertFileToGrossAdds(con, year, month, typeId, strUserIdSess)    ;
           String tableId = AuthResDAO.getTableIdByName(con, tableName)    ;
           File excelFile = GetUploadedFile.getFile(paramHashMap, AuthResInterfaceKey.AUTH_UPLOAD_DIR);
           DataImportEngine die = new DataImportEngine();
           DataImportReport report = die.ImportFileSheetWithRowNumberWithSeq(primaryKeyName,sequenceName,excelFile.getAbsolutePath(), AdministrationInterfaceKey.DATA_IMPORT_INSERT, tableId, strUserIdSess,0);
           for (Object obj :report.getReport())
           {
           ErrorInfo error = (ErrorInfo) obj;
               System.out.println(error.toString());
           }
           AuthResDAO.updateFileId(con, fileId, tableName, isLabeled? " , Label_id="+label : "",strUserIdSess);
           dataHashMap.put(AuthResInterfaceKey.ERROR_MESSAGE, "File Uploaded Successfully.");
        
        }
           else{
           dataHashMap.put(AuthResInterfaceKey.ERROR_MESSAGE, "Year and month are exist for another file.");
           }
        }
}
    

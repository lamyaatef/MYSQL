package com.mobinil.sds.core.facade.handlers;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Vector;

import com.mobinil.sds.core.system.ma.dao.*;
import com.mobinil.sds.core.system.ma.model.*;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.ma.MAInterfaceKey;


public class MaintenanceHandler {
	  
	static final int SHOW_ALL_MODULES = 1 ;                           
	static final int CREATE_NEW_MODULE=2;
	static final int SAVE_MODULE=3;
	static final int DELETE_MODULE=4;
	static final int UPDATE_MODULE=5;
	static final int EDIT_MODULE=6;
	static final int SHOW_ALL_PRIVILAGES=7;
	static final int DELETE_PRIVILAGE=8;
	static final int CREATE_PRIVILAGE=9;
	static final int SAVE_PRIVILAGE=10;
	static final int UPDATE_PRIVILAGE=11;
	static final int EDIT_PRIVILAGE=12;
	  public static  HashMap handle(String action, HashMap paramHashMap,java.sql.Connection con)
	  {
		  Vector vectorListOfModules;
		  int actionType = 0;
		    HashMap dataHashMap = new HashMap();
		  try
		  {
			  if (action.equals(MAInterfaceKey.ACTION_SHOW_ALL_MODULES))                                   
			        actionType = SHOW_ALL_MODULES;
			  else if (action.equals(MAInterfaceKey.ACTION_CREATE_NEW_MODULE))
			  		actionType= CREATE_NEW_MODULE;
			  else if (action.equals(MAInterfaceKey.ACTION_SAVE_MODULE))
			  		actionType=SAVE_MODULE;
			  else if (action.equals(MAInterfaceKey.ACTION_DELETE_MODULE))
			  		actionType=DELETE_MODULE;
			  else if (action.equals(MAInterfaceKey.ACTION_UPDATE_MODULE))
			  		actionType=UPDATE_MODULE;
			  else if (action.equals(MAInterfaceKey.ACTION_EDIT_MODULE))
			  		actionType=EDIT_MODULE;
			  else if(action.equals(MAInterfaceKey.ACTION_SHOW_ALL_PRIVILAGES))
				  	actionType=SHOW_ALL_PRIVILAGES;
			  else if(action.equals(MAInterfaceKey.ACTION_DELETE_PRIVILAGE))
				  	actionType=DELETE_PRIVILAGE;
			  else if(action.equals(MAInterfaceKey.ACTION_CREATE_PRIVILAGE))
				  	actionType=CREATE_PRIVILAGE;
			  else if(action.equals(MAInterfaceKey.ACTION_SAVE_PRIVILAGE))
				  	actionType=SAVE_PRIVILAGE;
			  else if(action.equals(MAInterfaceKey.ACTION_UPDATE_PRIVILAGE))
				  	actionType=UPDATE_PRIVILAGE;
			  else if(action.equals(MAInterfaceKey.ACTION_EDIT_PRIVILAGE))
				  	actionType=EDIT_PRIVILAGE;
		switch(actionType)
			  {
			 // System.out.println("Action Type is: "+action);
			  		case SHOW_ALL_MODULES:
			  			showAllModules(dataHashMap,paramHashMap,con);
		      	
			  		break;
			  		
			  		case CREATE_NEW_MODULE:
			  		//	System.out.println("READDDDDDDDDDDDDDDDDDDdd");
			  			createModule(dataHashMap,paramHashMap,con);
			  		break;
			  		case SAVE_MODULE:
			  			try
			            {
			          	System.out.println("sdfsdfsdfsdfsddf");
			          	dataHashMap = new HashMap ();
			                 String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			                 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			                 //String channelId = (String)paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
			                 //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelId); 
			                 Long strModuleId = Utility.getSequenceNextVal(con, "seq_adm_module_id");
			                 String strModuleName = (String)paramHashMap.get(MAInterfaceKey.INPUT_TEXT_MODULE_NAME);
			                 String strModuleDesc = (String)paramHashMap.get(MAInterfaceKey.INPUT_TEXT_MODULE_DESC);
			                 System.out.println("Description is : "+strModuleDesc);
			                 String strModuleStatusId = (String)paramHashMap.get(MAInterfaceKey.INPUT_SELECT_MODULE_ID);

			                 MaintenanceDAO.insertModule(con,strModuleId,strModuleName,strModuleDesc,strModuleStatusId);
			                 Vector vecSchemaModule = MaintenanceDAO.getAllModules(con);
			                 dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,vecSchemaModule);
			            }
			              catch(Exception objExp)
			            {
			              objExp.printStackTrace();
			            }
			  		break;
			  		case DELETE_MODULE:
			  		
			  		     try
			  	         {
			  	       	//System.out.println("######################");
			  	       	dataHashMap = new HashMap();
			  	           String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			  	           dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			  	           String strModuleID = (String)paramHashMap.get(MAInterfaceKey.INPUT_HIDDEN_MODULE_ID);
			  	           MaintenanceDAO.deleteModule(con,strModuleID);
			  	           Vector vecSchemaModule = MaintenanceDAO.getAllModules(con);
			  	           dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,vecSchemaModule); }
			  	        catch(Exception objExp)
			  	         {
			  	           objExp.printStackTrace();
			  	         }
			  		  
			  		break;
			  		case UPDATE_MODULE:
				  		
			  		  try
				      {
				        dataHashMap = new HashMap ();
				        String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
				        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
				        String strModuleId = (String)paramHashMap.get(MAInterfaceKey.INPUT_HIDDEN_MODULE_ID);
				        ModuleModel moduleModel = (ModuleModel) MaintenanceDAO.selectModule(con,strModuleId);
				        System.out.println("ACCCCCCCCCCCCCCCCC");
				        dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,moduleModel);
				      }
				        catch(Exception objExp)
				      {
				        objExp.printStackTrace();
				      }
			  		  
			  		break;
			 		case EDIT_MODULE:
				  		
			 		      try
			 		      {
			 		    	System.out.println("###########update action###########");
			 		    	dataHashMap = new HashMap();
			 		        String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			 		        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			 		        String strModuleID = (String)paramHashMap.get(MAInterfaceKey.INPUT_HIDDEN_MODULE_ID);
			 		        String strModuleName = (String)paramHashMap.get(MAInterfaceKey.INPUT_TEXT_MODULE_NAME);
			 		        String strModuleDescription = (String)paramHashMap.get(MAInterfaceKey.INPUT_TEXT_MODULE_DESC);

			 		        String strModuleStatusId = (String)paramHashMap.get(MAInterfaceKey.INPUT_SELECT_MODULE_ID);
			 		
			 		        
			 		        MaintenanceDAO.editModule(con,strModuleID,strModuleName,strModuleDescription,strModuleStatusId);
			 		        Vector vecSchemaModule = MaintenanceDAO.getAllModules(con);
			 		        Vector vecListOfModules= MaintenanceDAO.getModulesList(con);
			 		        dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,vecSchemaModule); 
			 		        dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE_2,vecListOfModules); 

			 		      }
			 		    catch(Exception objExp)
			 		      {
			 		        objExp.printStackTrace();
			 		      }
				  		  
				  		break;
			 		case SHOW_ALL_PRIVILAGES:
				        try
			            {
			             
			             String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			             String sss=(String )paramHashMap.get(MAInterfaceKey.INPUT_SEARCH_MODULE_NAME);
//			             System.out.println("yhe  moduleeeeeeeeeeeeeeeeeeeeeeee "+sss);
			             dataHashMap.put(MAInterfaceKey.INPUT_SEARCH_MODULE_NAME,sss);
			             Vector vecSchemaPrivilage;
			             	if(sss==null)
			             	{
			             		sss="-";
			             		 vecSchemaPrivilage = MaintenanceDAO.searchPrivilages(con,sss);
			             	}
			             	else
			             	{
			             		
			             		 vecSchemaPrivilage = MaintenanceDAO.searchPrivilages(con,sss);

			             		
			             	}
			             dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,vecSchemaPrivilage);
			             Vector vecListOfModules=MaintenanceDAO.getModulesList(con);
			             dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE_2,vecListOfModules);
			             //String moduleId=(String )paramHashMap.get(MAInterfaceKey.INPUT_SEARCH_MODULE_NAME);
			            }
			         catch(Exception objExp)
			           {
			             objExp.printStackTrace();
			           }
			 		
			 		break;
			 		
			 		case DELETE_PRIVILAGE:
					     try
			  	         {
			  	       	//System.out.println("######################");
			  	       	dataHashMap = new HashMap();
			  	           String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			  	           dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			  	           String strPrivilageID = (String)paramHashMap.get(MAInterfaceKey.INPUT_HIDDEN_PRIVILAGE_ID);
			  	           MaintenanceDAO.deletePrivilage(con,strPrivilageID);
			  	           Vector vecSchemaPrivilage = MaintenanceDAO.getAllPrivilages(con);
			  	           dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,vecSchemaPrivilage); 
			  	           }
			  	        catch(Exception objExp)
			  	         {
			  	           objExp.printStackTrace();
			  	         }
			 		
			 		break;
			 		case CREATE_PRIVILAGE:
			 			  try
			 	          {
			 				dataHashMap = new HashMap ();
			 	            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			 	                //  ModuleModel moduleModel = (ModuleModel) SchemaDAO.selectItem(con,strItemId);
			 	            //  Vector vecSchemaProdcut = SchemaDAO.getAllItems(con);

			 	            //  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecSchemaProdcut);
			 	             
			 	            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			 	            String privilageId = (String)paramHashMap.get(MAInterfaceKey.INPUT_HIDDEN_PRIVILAGE_ID);
			 	            dataHashMap.put(MAInterfaceKey.INPUT_HIDDEN_PRIVILAGE_ID,privilageId);
			 	           vectorListOfModules=MaintenanceDAO.getModulesList(con);
			 	           dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE_2, vectorListOfModules);
			 	             Vector vecPrivilagesStatusList=MaintenanceDAO.getPrivilagesStatusList(con);
				 	           dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE_3, vecPrivilagesStatusList);
				                 String sss=(String )paramHashMap.get(MAInterfaceKey.INPUT_SEARCH_MODULE_NAME);
				                dataHashMap.put(MAInterfaceKey.INPUT_SEARCH_MODULE_NAME,sss); 	

			 	          }
			 	            catch(Exception objExp)
			 	          {
			 	            objExp.printStackTrace();
			 			  
			 			  
			 	          }
			 		
			 		break;
			 		case SAVE_PRIVILAGE:
			  			try
			            {
			          	dataHashMap = new HashMap ();
			                 String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			                 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			                 //String channelId = (String)paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
			                 //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelId); 
			                 Long strPrivilageId = Utility.getSequenceNextVal(con, "seq_adm_privilage_id");
			                 String strPrivilageName = (String)paramHashMap.get(MAInterfaceKey.INPUT_TEXT_PRIVILAGE_NAME);
			                 String strPrivilageDesc = (String)paramHashMap.get(MAInterfaceKey.INPUT_TEXT_PRIVILAGE_DESC);
			                 String strModuleId = (String)paramHashMap.get(MAInterfaceKey.INPUT_SELECT_MODULE_NAME);
			                 String strPrivilageStatusId=(String) paramHashMap.get(MAInterfaceKey.INPUT_SELECT_PRIVILAGE_STATUS_NAME);
			                 String strPrivilageActionName=(String) paramHashMap.get(MAInterfaceKey.INPUT_TEXT_PRIVILAGE_ACTION_NAME);
			                 String strOrderValue=(String) paramHashMap.get(MAInterfaceKey.INPUT_TEXT_ORDER_VALUE);
			                 String strPrivilageTarget=(String) paramHashMap.get(MAInterfaceKey.INPUT_TEXT_PRIVILAGE_TARGET);
			                 
			                 
			                 MaintenanceDAO.insertPrivilage(con,strPrivilageId,strPrivilageName,strPrivilageDesc,strModuleId,strPrivilageStatusId,strPrivilageActionName,strOrderValue,strPrivilageTarget);
			                 Vector vecSchemaPrivilage = new Vector() ;//= MaintenanceDAO.getAllPrivilages(con);
			                 String sss=(String )paramHashMap.get(MAInterfaceKey.INPUT_SEARCH_MODULE_NAME);
				            // System.out.println("yhe  moduleeeeeeeeeeeeeeeeeeeeeeee "+sss);
				             dataHashMap.put(MAInterfaceKey.INPUT_SEARCH_MODULE_NAME,sss);
				             /*	if(sss==null)
				             	{
				             		sss="-";
				             		 vecSchemaPrivilage = MaintenanceDAO.searchPrivilages(con,sss);
				             	}
				             	else
				             	{*/
				             		
				             	//	 vecSchemaPrivilage = MaintenanceDAO.searchPrivilages(con,);

				             		
				             	
				             	  dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,vecSchemaPrivilage);
					                 Vector vecListOfModules=MaintenanceDAO.getModulesList(con);
					                 dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE_2,vecListOfModules);
					               

			            }
			              catch(Exception objExp)
			            {
			              objExp.printStackTrace();
			            }
			 			
			 		break;
			 		case EDIT_PRIVILAGE:
				  		
			 		      try
			 		      {
			 		    	System.out.println("###########update action###########");
			 		    	dataHashMap = new HashMap();
			 		        String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
			 		        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
			 		        String strPrivilageID = (String)paramHashMap.get(MAInterfaceKey.INPUT_HIDDEN_PRIVILAGE_ID);
			 		        String strPrivilageName = (String)paramHashMap.get(MAInterfaceKey.INPUT_TEXT_PRIVILAGE_NAME);
			 		        String strPrivilageDescription = (String)paramHashMap.get(MAInterfaceKey.INPUT_TEXT_PRIVILAGE_DESC);
			 		        String strModuleId=(String) paramHashMap.get(MAInterfaceKey.INPUT_SELECT_MODULE_NAME);
			 		        String strPrivilageStatusId = (String)paramHashMap.get(MAInterfaceKey.INPUT_SELECT_PRIVILAGE_STATUS_NAME);
			 		        String strPrivilageActionName=(String) paramHashMap.get(MAInterfaceKey.INPUT_TEXT_PRIVILAGE_ACTION_NAME);
			 		        String strOrderValue=(String ) paramHashMap.get(MAInterfaceKey.INPUT_TEXT_ORDER_VALUE);
			 		        String strPrivilageTarget="rhs";//(String) paramHashMap.get(MAInterfaceKey.INPUT_TEXT_PRIVILAGE_TARGET);
			 		        
			 		        
			 		        MaintenanceDAO.editPrivilage(con,strPrivilageID,strPrivilageName,strPrivilageDescription,strModuleId,strPrivilageStatusId,strPrivilageActionName,strOrderValue,strPrivilageTarget);
			 		        String sss=(String )paramHashMap.get(MAInterfaceKey.INPUT_SEARCH_MODULE_NAME);
					        dataHashMap.put(MAInterfaceKey.INPUT_SEARCH_MODULE_NAME,sss);
					        Vector vecSchemaPrivilage=new Vector();
					        dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,vecSchemaPrivilage);
				            Vector vecListOfModules=MaintenanceDAO.getModulesList(con);
						    dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE_2,vecListOfModules);
						               

			 		      }
			 		    catch(Exception objExp)
			 		      {
			 		        objExp.printStackTrace();
			 		      }
				  		  
				  		break;
			 		case UPDATE_PRIVILAGE:
			 	  		  try
					      {
					        dataHashMap = new HashMap ();
					        String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					        String strPrivilageId = (String)paramHashMap.get(MAInterfaceKey.INPUT_HIDDEN_PRIVILAGE_ID);
					        PrivilageModel privilageModel = (PrivilageModel) MaintenanceDAO.selectPrivilage(con,strPrivilageId);
					        System.out.println("ACCCCCCCCCCCCCCCCC");
					        dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,privilageModel);
				 	           vectorListOfModules=MaintenanceDAO.getModulesList(con);
				 	           dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE_2, vectorListOfModules);
				 	             Vector vecPrivilagesStatusList=MaintenanceDAO.getPrivilagesStatusList(con);
					 	           dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE_3, vecPrivilagesStatusList);

					      }
					        catch(Exception objExp)
					      {
					        objExp.printStackTrace();
					      }
			 		break;
			  		default:
			  		//	Utility.logger.debug ("Unknown action received: " + action ); 
			  			//com.mobinil.sds.core.utilities.Utility.logger.d 
			   
			  
			  }
			  
		  }
		  catch(Exception e)
		  {
			  
			  e.printStackTrace();
			  
		  }
		  return dataHashMap ; 
		  
		  
		  
		
	  }
	  
	  private static void showAllModules(HashMap dataHashMap,HashMap paramHashMap,Connection con)
	  {
		  
		  
	        try
            {
             
             String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
             
             Vector vecSchemaModule = MaintenanceDAO.getAllModules(con);
             System.out.println("Vector size is ddddddddddddddddd: "+vecSchemaModule.size());
             dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,vecSchemaModule);
           }
         catch(Exception objExp)
           {
             objExp.printStackTrace();
           }
		  
	  }
	  
	  private static void createModule(HashMap dataHashMap, HashMap paramHashMap,Connection con)
	  {
		  try
          {
			dataHashMap = new HashMap ();
            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                //  ModuleModel moduleModel = (ModuleModel) SchemaDAO.selectItem(con,strItemId);
            //  Vector vecSchemaProdcut = SchemaDAO.getAllItems(con);

            //  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecSchemaProdcut);
             
            
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
            String moduleId = (String)paramHashMap.get(MAInterfaceKey.INPUT_HIDDEN_MODULE_ID);
            dataHashMap.put(MAInterfaceKey.INPUT_HIDDEN_MODULE_ID,moduleId);
        
          
          }
            catch(Exception objExp)
          {
            objExp.printStackTrace();
		  
		  
          }
	  }
	  private static void saveModule(HashMap dataHashMap, HashMap paramHashMap,Connection con)
	  {
		  try
          {
        	System.out.println("sdfsdfsdfsdfsddf");
        	dataHashMap = new HashMap ();
               String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
               //String channelId = (String)paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
               //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelId); 
               Long strModuleId = Utility.getSequenceNextVal(con, "seq_adm_module_id");
               String strModuleName = (String)paramHashMap.get(MAInterfaceKey.INPUT_TEXT_MODULE_NAME);
               String strModuleDesc = (String)paramHashMap.get(MAInterfaceKey.INPUT_TEXT_MODULE_DESC);
               String strModuleStatusId = (String)paramHashMap.get(MAInterfaceKey.INPUT_SELECT_MODULE_ID);

               MaintenanceDAO.insertModule(con,strModuleId,strModuleName,strModuleName,strModuleStatusId);
               Vector vecSchemaModule = MaintenanceDAO.getAllModules(con);
               dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,vecSchemaModule);
          }
            catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
       //     return dataHashMap;
	  }
	  
	  private static void deleteModule(HashMap dataHashMap,HashMap paramHashMap,Connection con)
	  {
	     try
         {
       	//System.out.println("######################");
       	dataHashMap = new HashMap();
           String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
           dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
           String strModuleID = (String)paramHashMap.get(MAInterfaceKey.INPUT_HIDDEN_MODULE_ID);
           MaintenanceDAO.deleteModule(con,strModuleID);
           Vector vecSchemaModule = MaintenanceDAO.getAllModules(con);
           dataHashMap.put(MAInterfaceKey.VECTOR_SCHEMA_MODULE,vecSchemaModule); }
        catch(Exception objExp)
         {
           objExp.printStackTrace();
         }
	  }
	  
	  
	 
	  
}

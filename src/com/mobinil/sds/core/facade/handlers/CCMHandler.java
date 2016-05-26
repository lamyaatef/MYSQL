package com.mobinil.sds.core.facade.handlers;

import java.sql.*;
import java.util.*;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.ccm.CCMInterfaceKey;
import com.mobinil.sds.web.interfaces.sop.SOPInterfaceKey;
import com.mobinil.sds.core.system.ccm.addAndWin.DAO.addAndWinDao;
import com.mobinil.sds.core.system.ccm.addAndWin.model.AddAndWinShopAssignmentModel;
import com.mobinil.sds.core.system.ccm.entity.dao.ENTITYDao;
import com.mobinil.sds.core.system.ccm.entity.dto.EntityDto;
import com.mobinil.sds.core.system.ccm.entity.model.*;
import com.mobinil.sds.core.system.ccm.entityproject.model.entityProjectModel;
import com.mobinil.sds.core.system.ccm.project.dao.ProjectDao;
import com.mobinil.sds.core.system.ccm.service.dao.ServiceDao;
import com.mobinil.sds.core.system.sop.schemas.dao.SchemaDAO;
import com.mobinil.sds.core.utilities.*;
public class CCMHandler

{
	static final int VIEW_ENTITY_DEFINITION_MANAGEMENT = 1;
	static final int SAVE_ENTITY_FIELD_DEFINITION = 2;
	static final int VIEW_ENTITY_TYPE_FIELDS = 3;
	static final int CREATE_NEW_ENTITY_FIELD = 4;
	static final int EDIT_ENTITY_FIELDS_DEFINITION=5;
	static final int UPDATE_ENTITY_FIELD_DEFINITION=6;
	static final int VIEW_ENTITY_MANAGEMENT=7;
	static final int SEARCH_ENTITY=8;
	static final int CREATE_NEW_ENTITY=9;
	static final int SAVE_NEW_ENTITY=10;
	static final int  VIEW_ENTITY_TYPE_LIST=11;
	static final int  VIEW_ENTITY_OPTIONAL_DETAILS=12;
	static final int  EDIT_ENTITY=13;
	static final int  VIEW_CREATE_NEW_ENTITY=14;
	static final int  UPDATE_ENTITY =15;
	static final int IMPORT_PRODUCTS_FOR_COMPLEMANTRYS=17;
	static final int IMPORT_PRODUCTS_FROM_PGW=16;
	static final int VIEW_SERVICE_MANAGEMENT =18;
	static final int  CREATE_NEW_SERVICE=19;
	static final int  SAVE_NEW_SERVICE=20;
	static final int  DELETE_SERVICE=21;
	static final int  EDIT_SERVICE=22;
	static final int  UPDATE_SERVICE=23;
	static final int VIEW_PROJECT_MANAGEMENT=24;
	static final int ADD_PROJECT_SERVICE_SCHEMA=25;
	static final int SAVE_PROJECT=26;
	static final int VIEW_SERVICE_ASSIGNED_PROJECT=27;
	static final int SUB_ENTITY_TREE=28;
	static final int  ADD_SUB_ENTITIES=29;
	static final int  ADD_SUB_ENTITIES_NEW=30;
	static final int   EDIT_SUB_ENTITY_TREE=31;
	static final int  EDIT_PROJECT=32;
	static final int  SEARCH_PROJECT=33;
	static final int  UPDATE_PROJECT=34;
	
	static final int  ADD_SUB_ENTITIES_LEVEL1=35;
	static final int  DELETE_ENTITY_FIELDS_DEFINITION=36;
	
	static final int  CHOOSE_SUB_ENTITY=37;
	static final int  DELETE_SUB_ENTITIES=38;
	
	static final int  ADD_ENTITIES_FIRST_LEVEL=39;
	
	static final int  CHOOSE_ENTITY_FIRST_LEVEL=40;
	static final int  DELETE_PROJECT=41;
	
	static final int VIEW_ADD_AND_WIN_SHOP_ASSIGNMENT=42;
	
	static final int ASSIGN_ADD_AND_WIN_TO_SHOP=43;
	static final int SEARCH_ADD_AND_WIN=44;
	
	static final int NEXT_AND_PREVIOUS=45;
	
	
	public static int rowNum =0;


	
	
	
	
	
	
	

	public HashMap handle(String action, HashMap paramHashMap) {
		int actionType = 0;
		HashMap dataHashMap = null;
		Connection con = null;
		System.out.println("printtttttttttttttttt " + action);

		try {

			con = Utility.getConnection();

			if (action.equals(CCMInterfaceKey.ACTION_VIEW_ENTITY_DEFINITION_MANAGEMENT))
				actionType = VIEW_ENTITY_DEFINITION_MANAGEMENT;
			else if (action
					.equals(CCMInterfaceKey.ACTION_SAVE_ENTITY_FIELD_DEFINITION))
				actionType = SAVE_ENTITY_FIELD_DEFINITION;
			else if (action
					.equals(CCMInterfaceKey.ACTION_VIEW_ENTITY_TYPE_FIELDS))
				actionType = VIEW_ENTITY_TYPE_FIELDS;

		
			else if (action
					.equals(CCMInterfaceKey.ACTION_VIEW_ENTITY_TYPE_FIELDS))
				actionType = VIEW_ENTITY_TYPE_FIELDS;
			else if (action
					.equals(CCMInterfaceKey.ACTION_CREATE_NEW_ENTITY_FIELD))
				actionType = CREATE_NEW_ENTITY_FIELD;
			
			
			else if (action
					.equals(CCMInterfaceKey.ACTION_EDIT_ENTITY_FIELDS_DEFINITION))
				actionType = EDIT_ENTITY_FIELDS_DEFINITION;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_UPDATE_ENTITY_FIELD_DEFINITION))
				actionType = UPDATE_ENTITY_FIELD_DEFINITION;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_VIEW_ENTITY_MANAGEMENT))
				actionType = VIEW_ENTITY_MANAGEMENT;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_SEARCH_ENTITY))
				actionType = SEARCH_ENTITY;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_CREATE_NEW_ENTITY))
				actionType = CREATE_NEW_ENTITY;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_SAVE_NEW_ENTITY))
				actionType = SAVE_NEW_ENTITY;
			
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_VIEW_ENTITY_TYPE_LIST))
				actionType = VIEW_ENTITY_TYPE_LIST;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_VIEW_ENTITY_OPTIONAL_DETAILS))
				actionType = VIEW_ENTITY_OPTIONAL_DETAILS;
			else if  (action
					.equals(CCMInterfaceKey.ACTION_EDIT_ENTITY))
				actionType = EDIT_ENTITY;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_VIEW_CREATE_NEW_ENTITY))
				actionType = VIEW_CREATE_NEW_ENTITY;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_IMPORT_PRODUCTS_FROM_PGW))
				actionType = IMPORT_PRODUCTS_FROM_PGW;
			
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_IMPORT_PRODUCTS_FOR_COMPLEMANTRYS))
				actionType = IMPORT_PRODUCTS_FOR_COMPLEMANTRYS;
			
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_VIEW_SERVICE_MANAGEMENT))
				actionType = VIEW_SERVICE_MANAGEMENT;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_CREATE_NEW_SERVICE))
				actionType =CREATE_NEW_SERVICE;
			else if  (action
					.equals(CCMInterfaceKey.ACTION_SAVE_NEW_SERVICE))
				actionType =SAVE_NEW_SERVICE;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_DELETE_SERVICE))
				actionType =DELETE_SERVICE;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_EDIT_SERVICE))
				actionType =EDIT_SERVICE;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_UPDATE_SERVICE))
				actionType =UPDATE_SERVICE;
			

			else if  (action
					.equals(CCMInterfaceKey.ACTION_UPDATE_ENTITY))
				actionType =UPDATE_ENTITY;
			else if  (action
					.equals(CCMInterfaceKey.ACTION_VIEW_PROJECT_MANAGEMENT))
				actionType =VIEW_PROJECT_MANAGEMENT;
			else if  (action
					.equals(CCMInterfaceKey.ACTION_ADD_PROJECT_SERVICE_SCHEMA))
				actionType =ADD_PROJECT_SERVICE_SCHEMA;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_SAVE_PROJECT))
				actionType =SAVE_PROJECT;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_VIEW_SERVICE_ASSIGNED_PROJECT))
				actionType =VIEW_SERVICE_ASSIGNED_PROJECT;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_SUB_ENTITY_TREE))
				actionType =SUB_ENTITY_TREE;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_ADD_SUB_ENTITIES))
				actionType =ADD_SUB_ENTITIES;
		
	
			else if  (action
					.equals(CCMInterfaceKey.ACTION_ADD_SUB_ENTITIES_NEW))
				actionType =ADD_SUB_ENTITIES_NEW;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_EDIT_SUB_ENTITY_TREE))
				actionType =EDIT_SUB_ENTITY_TREE;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_EDIT_PROJECT))
				actionType =EDIT_PROJECT;
		
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_SEARCH_PROJECT))
				actionType =SEARCH_PROJECT;
			
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_UPDATE_PROJECT))
				actionType =UPDATE_PROJECT;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_DELETE_ENTITY_FIELDS_DEFINITION))
				actionType =DELETE_ENTITY_FIELDS_DEFINITION;
			
		
			
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_ADD_SUB_ENTITIES_LEVEL1))
				actionType =ADD_SUB_ENTITIES_LEVEL1;
			
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_CHOOSE_SUB_ENTITY))
				actionType =CHOOSE_SUB_ENTITY;
			
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_DELETE_SUB_ENTITIES))
				actionType =DELETE_SUB_ENTITIES;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_ADD_ENTITIES_FIRST_LEVEL))
				actionType =ADD_ENTITIES_FIRST_LEVEL;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_CHOOSE_ENTITY_FIRST_LEVEL))
				actionType =CHOOSE_ENTITY_FIRST_LEVEL;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_DELETE_PROJECT))
				actionType =DELETE_PROJECT;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_VIEW_ADD_AND_WIN_SHOP_ASSIGNMENT))
				actionType =VIEW_ADD_AND_WIN_SHOP_ASSIGNMENT;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_ASSIGN_ADD_AND_WIN_TO_SHOP))
				actionType =ASSIGN_ADD_AND_WIN_TO_SHOP;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_SEARCH_ADD_AND_WIN))
				actionType =SEARCH_ADD_AND_WIN;
			
			else if  (action
					.equals(CCMInterfaceKey.ACTION_NEXT_AND_PREVIOUS))
				actionType =NEXT_AND_PREVIOUS;
			
			
			
			
			
   
			switch (actionType) {

			case VIEW_ENTITY_DEFINITION_MANAGEMENT:
				try {

					dataHashMap = new HashMap();

					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					Vector vecEntityType = new Vector();

					vecEntityType = ENTITYDao.getEntityTypes(con);
					
					dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,
							vecEntityType);
					dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ACTION,"2");

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;

			    case SAVE_ENTITY_FIELD_DEFINITION:
				try {

					dataHashMap = new HashMap();

					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					
				    String entity_type_id =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
	               // System.out.println("this entity type id in the create save handleeeeeeeeeeeeeeeeer" +entity_type_id);

					Vector vecEntityField = new Vector();
					Vector vecEntityTypeField = new Vector();

					dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_FIELD_TYPES,
							vecEntityField);
					
					String order_id = (String) paramHashMap
							.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_ORDER);
					 
					
					String entity_field_label = (String) paramHashMap
							.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_LABEL_NAME);
					String entity_field_type_id = (String) paramHashMap
							.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_FIELD_TYPE_ID);
					String is_mandatory = (String) paramHashMap
							.get(CCMInterfaceKey.INPUT_IS_MANADATORY);
				//	System.out.println("Is Mandatory issssssssss " + is_mandatory);

					ENTITYDao.insertNewEntityField(con, order_id,
							entity_type_id, entity_field_label,
							entity_field_type_id, is_mandatory);

					vecEntityTypeField = ENTITYDao.getEntityTypeField(con,entity_type_id);
					
					dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPE_FIELDS,
							vecEntityTypeField);
					
					dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,
							entity_type_id);


				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				
				break;

			case VIEW_ENTITY_TYPE_FIELDS:
				try {

					dataHashMap = new HashMap();
					String entityTypeId = (String) paramHashMap
							.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
					System.out
							.println("entityTypeId isssssssssssssssssssssss"
									+ entityTypeId);
					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					Vector vecEntityTypeFields = new Vector();

					vecEntityTypeFields = ENTITYDao.getEntityTypeField(con,
							entityTypeId);
					
					dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);

					dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPE_FIELDS,
							vecEntityTypeFields);
					
					

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;

			case CREATE_NEW_ENTITY_FIELD:
				try {
					dataHashMap = new HashMap();
                    String entityTypeId =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
                  //  System.out.println("this entity type id in the create handleeeeeeeeeeeeeeeeer"+entityTypeId);
                   
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					
					Vector entityField = new Vector();
					
					
					
					
					dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_FIELD_TYPES,ENTITYDao.getEntityFieldTypes(con));
					
					dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
					
					dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPE_FIELDS,entityField);
					

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;

			case EDIT_ENTITY_FIELDS_DEFINITION:
				try {
					dataHashMap = new HashMap();
					
					// String entityTypeId =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);	
						String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						
						String entityFieldTyperecordId=(String) paramHashMap.get("fieldId");
						
						
						
						 String entityTypeId =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
		                    System.out.println("this entity type id in the edit handleeeeeeeeeeeeeeeeer"+entityTypeId);
						
						
						//System.out.println("passed field id issssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss "+entityFieldTyperecordId);
						Vector entityTypesFieldsrecord =ENTITYDao.getEntityTypeFieldByRecordId(con, entityFieldTyperecordId);
						
						dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPE_FIELDS,entityTypesFieldsrecord);
						
						dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
						

						dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_FIELD_TYPES,ENTITYDao.getEntityFieldTypes(con));
					
					
					

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
			case DELETE_ENTITY_FIELDS_DEFINITION:
				try {
					dataHashMap = new HashMap();
					
					// String entityTypeId =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);	
						String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						
						String entityFieldTyperecordId=(String) paramHashMap.get("fieldId");
						
						
						
						 String entityTypeId =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
		              //      System.out.println("this entity type id in the DELETE handleeeeeeeeeeeeeeeeer"+entityTypeId);
						
						
				//		System.out.println("passed field id issssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss "+entityFieldTyperecordId);
						 String message="";
	
						 
						 
						 
					     boolean checkData=  ENTITYDao.checkDataExists(con, entityFieldTyperecordId);
						 
						 if(checkData==false)
						 {ENTITYDao.deleteEntityField(con, entityFieldTyperecordId);
					       System.out.println("Delete function not done : ") ;
						 
						 }
						 
						 
						 else{
							 
							 
					        	message="Data exists  in this field";
					            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,message);
					            
					        	  dataHashMap.put("flag","dataExist");
					              System.out.println("Delete function not done : ") ;
					       
					              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,message); 
							 
							 
						     }
						
					
						
						dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
						

						dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_FIELD_TYPES,ENTITYDao.getEntityFieldTypes(con));
						
						
						dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
						
			   	    Vector vecEntityTypeField = ENTITYDao.getEntityTypeField(con,entityTypeId);
						
						
						
						dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPE_FIELDS,
								vecEntityTypeField);
					
					
					

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
				
				
				
			 case UPDATE_ENTITY_FIELD_DEFINITION:
					try {

						dataHashMap = new HashMap();

						String strUserID = (String) paramHashMap
								.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						
					    String entity_type_id =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);

						String entityTypeId = (String) paramHashMap
						.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
	
						String entityFieldTyperecordId=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_FIELD_ID);
						
						
						
						String order_id = (String) paramHashMap
								.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_ORDER);
						 
						
						String entity_field_label = (String) paramHashMap
								.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_FIELD_LABEL_NAME);
						String entity_field_type_id = (String) paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_FIELD_TYPE_ID);
						
					//	System.out.println("entity_field_type_id isssssssssssssssssssss"+entity_field_type_id ); 
						
						String is_mandatory = (String) paramHashMap
								.get(CCMInterfaceKey.INPUT_IS_MANADATORY);
					
						
					

						ENTITYDao.updateEntityField(con, entityFieldTyperecordId, order_id, "a", entity_field_label, entity_field_type_id,entity_type_id, is_mandatory);

				   	    Vector vecEntityTypeField = ENTITYDao.getEntityTypeField(con,entity_type_id);
						
						
						
						dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPE_FIELDS,
								vecEntityTypeField);
						
						dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_FIELD_TYPES,ENTITYDao.getEntityFieldTypes(con));
						dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);

					} catch (Exception objExp) {
						objExp.printStackTrace();
					}
					
					break;
					
				case VIEW_ENTITY_MANAGEMENT:
					try {

						dataHashMap = new HashMap();

						String strUserID = (String) paramHashMap
								.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						
						 Vector vecEntityadditionalField = new Vector();
						 Vector  vecEntityMandatoryData= new Vector();   
						
						String entity_type_id =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
                     //   System.out.println("entity type id isssssssssssssssss"+entity_type_id);

						//vecEntityadditionalField = ENTITYDao.getAdditionalEntityFieldLabel(con, entity_type_id);

						dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,
								ENTITYDao.getEntityTypes(con));

						  dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_DATA,
								vecEntityadditionalField);
						dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,vecEntityMandatoryData);
								
						dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ACTION,"2");
				

					} catch (Exception objExp) {
						objExp.printStackTrace();
					}
					break;
					
					
				case SEARCH_ENTITY:
					try {

						dataHashMap = new HashMap();

						String strUserID = (String) paramHashMap
								.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						
						 Vector vecEntityadditionalField = new Vector();
						
						String entity_type_id =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
						
						String entityName =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME);
						String entityCode =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE);

						//String entityName = (String)paramHashMap.get();
						
						 //String entityID =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID);
                        //System.out.println("entity type id isssssssssssssssss"+entity_type_id);


						
                        
						   vecEntityadditionalField = ENTITYDao.getAdditionalEntityFieldData(con, entity_type_id);
						   
					        Vector	     vecEntityadditionalFieldLabel = ENTITYDao.getAdditionalEntityFieldLabel(con, entity_type_id);

						   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,ENTITYDao.getEntityTypes(con));
						   
						  Vector entMandatoryData =ENTITYDao.getentityByFilter(con,entity_type_id,entityName,entityCode);
						  dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,entMandatoryData);
						  
						//   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,ENTITYDao.getEntityMandatoryData(con,entity_type_id));
						   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);
						   
						   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_DATA,vecEntityadditionalField);
						   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entity_type_id);
						   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE,entityCode);
						   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME,entityName);
						  
						   dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,CCMInterfaceKey.ACTION_SEARCH_ENTITY);

					} catch (Exception objExp) {
						objExp.printStackTrace();
					}
					break;
					
					
				case CREATE_NEW_ENTITY:
					try {
						


						dataHashMap = new HashMap();

						String strUserID = (String) paramHashMap
								.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						   dataHashMap.put("action",action);
						String entityTypeId =(String) paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
						
					//	System.out.println("entity type id IN create entity isssssssssssssssss"+entityTypeId);

						 Vector	vecEntityadditionalFieldLabel = ENTITYDao.getAdditionalEntityFieldLabel(con, entityTypeId);
						 dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
						 
						 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);
					  
					 
						 dataHashMap.put("flag","2");
						 
						//Drawing optional Field 
						 Vector	vecEntityOptionalFieldLabel = ENTITYDao.getEntityOptionalFields(con, entityTypeId);
						 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_OPTIONAL_FIELD_LABEL,vecEntityOptionalFieldLabel);
						
						  
				

					} catch (Exception objExp) {
						objExp.printStackTrace();
					}
					break;
					
					
			    case SAVE_NEW_ENTITY:
					try {

						
						
						dataHashMap = new HashMap();
						ArrayList messageOrderd = new ArrayList() ;
						
						

						String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						
					    String entityTypeId =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
		               //System.out.println("Handler Entity Type when save " +entityTypeId);
					
		                Vector vecEntityadditionalField = new Vector();

						 dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
						 
						
		 
						//Drawing optional Field 
						
		                
		                //vecEntityadditionalField = ENTITYDao.getAdditionalEntityFieldData(con, entity_type_id);
		                Vector	 vecEntityadditionalFieldLabel = ENTITYDao.getAdditionalEntityFieldLabel(con, entityTypeId);
		                dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);
		                
						

		                
		                String entityName = (String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME);
		                dataHashMap.put(CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME,entityName); 
						 
						
						String entityCode = (String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_CODE);
						dataHashMap.put(CCMInterfaceKey.INPUT_TEXT_ENTITY_CODE,entityCode); 
						
						String entityAddress= (String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_ADDRESS);
						dataHashMap.put(CCMInterfaceKey.INPUT_TEXT_ENTITY_ADDRESS,entityAddress); 
						

		    

					//	vecEntityTypeField = ENTITYDao.getEntityTypeField(con,entity_type_id);
						
					//	dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPE_FIELDS,
					//			vecEntityTypeField);
						
					  
					      Vector vecEntityOptionalFieldLabel = ENTITYDao.getEntityOptionalFields(con, entityTypeId);
					      dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_OPTIONAL_FIELD_LABEL,vecEntityOptionalFieldLabel);	
						    dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,ENTITYDao.getEntityTypes(con));
						 
						   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,ENTITYDao.getEntityMandatoryData(con,entityTypeId));
						   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);
						   
						   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_DATA,vecEntityadditionalField);
						Vector   vecmandatoryData = ENTITYDao.getEntityMandatoryData(con,entityTypeId);
						   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,vecmandatoryData);
							  String message ="";
							   
						       String entity_id="";

						   
				   
						   
		   
       // optional field   
						       
						       
						       
						          if (message.compareTo("")==0)
						          { 
						       //	   System.out.println("here in mahmoud if statment");
						         
						       	 //  System.out.println("here in mahmoud try statment");
						       	   	entity_id=ENTITYDao.insertNewEntity(con,entityName,entityTypeId, entityAddress, entityCode, strUserID, "1");
						       	
						       		  
						       		  
						       		   System.out.println("message in catch sql exception isss"+entity_id);
						       		   if (entity_id.contains("unique constraint")){
						       			   message = "Code Must be unique+";
                                             messageOrderd.add(message);
						       		   }
						       		   
						       		   
						       	       if (message.compareTo("")!=0){
						       	      	  //System.out.println("b4 break");  
						       	      	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,message);
						       	      	  dataHashMap.put("flag","2");

						       	 				 dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
						       	 				 
						       	 				 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);

						       	 				//Drawing optional Field 
						       	 				
						       	 				 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_OPTIONAL_FIELD_LABEL,vecEntityOptionalFieldLabel);
						       	 				dataHashMap.put("action",action);
						       	      	  }   
						       		   
						      
						       	 
						          }
			  
						 			  
       for (int i=0;i<vecEntityOptionalFieldLabel .size();i++)
 	    {
 		
 		 EntityOptionalFieldModel model = (EntityOptionalFieldModel) vecEntityOptionalFieldLabel .get(i);
    	
        
       //   String EntityFieldTypeName=model.getEntityFieldTypeName();
          String EntityFieldType=model.getEntityFieldTypeId();
          
          //Entity_Field record id
          
          String EntityFieldId = model.getEntityFieldId();
   
          String j =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_TEXT_AND_FIELD_ID+EntityFieldId);
          dataHashMap.put(CCMInterfaceKey.CONTROL_INPUT_TEXT_AND_FIELD_ID+EntityFieldId,j); 
          String k =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_DATE_AND_FIELD_ID+EntityFieldId);
          dataHashMap.put(CCMInterfaceKey.CONTROL_INPUT_DATE_AND_FIELD_ID+EntityFieldId,k);
          String l =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_NUMBER_AND_FIELD_ID+EntityFieldId);
          dataHashMap.put(CCMInterfaceKey.CONTROL_INPUT_NUMBER_AND_FIELD_ID+EntityFieldId,l);
          String m=(String)  paramHashMap.get(CCMInterfaceKey.CONTROL_INPUT_CHECKBOX_AND_FIELD_ID+EntityFieldId);
          dataHashMap.put(CCMInterfaceKey.CONTROL_INPUT_CHECKBOX_AND_FIELD_ID+EntityFieldId,m);
          dataHashMap.put("action",action);
          //System.out.println("actiididdbndkjdb is "+action);
 
          // Checking Mandatories 
          
          

          
           
           if (EntityFieldType.compareTo("1")==0)
			   
           {  
         	
         	  if (model.getIsMandatory().compareTo("1")==0&&(j.compareTo("")==0||j==null))
         	  { 
         		 String fieldName =model.getEntityfieldLabel();
         		  message = fieldName+"   must not be null"; 
         		 messageOrderd.add(message);
         		
                 
         		 
         	  }  
         	  
  }
              if (EntityFieldType.compareTo("3")==0)			   
              {  	  
            	  
            	  //System.out.println("nuberrrrrrrrrr before valida"+l);
            	  if (model.getIsMandatory().compareTo("1")==0&&(l.compareTo("")==0||l==null))
             	  { 
            		   String fieldName =model.getEntityfieldLabel();
             		   message =  fieldName+"  must not be null ";
             		  messageOrderd.add(message);
             		   
             	
                    	  }
             	
     	      
             	
            	  else if (model.getIsMandatory().compareTo("1")==0&&(l.compareTo("")!=0||l!=null))
            	  {
            	   try {

					Integer.parseInt(l);
			   } 
            	  catch (Exception e) {
					   String fieldName =model.getEntityfieldLabel();
             		   message =  fieldName+"  must enter a number ";
             		  messageOrderd.add(message);
             		   
             		
                      
					
				}
             }
				   
			      	  else if (model.getIsMandatory().compareTo("1")!=0 && (l.compareTo("")!=0)){
		            	   try {
		            		
		            		System.out.println("BEFEORE PARSE"+l);
		            		
							Integer.parseInt(l);
						
						         } catch (Exception e) {
							   String fieldName =model.getEntityfieldLabel();
		             		   message =  fieldName+"  must enter a number ";
		             		  messageOrderd.add(message);
		             	      
							
				         }
			      	  }
              }  
             if (EntityFieldType.compareTo("5")==0)
				   
              {  
              	  if (model.getIsMandatory().compareTo("1")==0&&(k.compareTo("")==0||k==null))
              	  { 
              		 String fieldName =model.getEntityfieldLabel();
             		  message = fieldName+"  must enter a date";
             		 messageOrderd.add(message);
                  
             		  
             	  }
   
              }
              if (EntityFieldType.compareTo("4")==0)
				   
              {  
            	  
            	  if (model.getIsMandatory().compareTo("1")==0&&(m==null))
              	  { 
            		  String fieldName =model.getEntityfieldLabel();
             		  message = fieldName+"Check Box message must be checked";
             		 messageOrderd.add(message);
                   
             	  }
              }
            	  //System.out.println("checkBox issssssss"+m);
                  
              } //end of the forLoop
       if (message.compareTo("")!=0){
     	  //System.out.println("b4 break");  
     	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,message);
     	 dataHashMap.put(CCMInterfaceKey.MESSAGE_QUEUE_MESSAGE,messageOrderd);
     	  
     	  dataHashMap.put("flag","2");

				 dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
				 
				 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);

				//Drawing optional Field 
				
				 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_OPTIONAL_FIELD_LABEL,vecEntityOptionalFieldLabel);
				 dataHashMap.put("action",action);

     	  }

     // insertion  Loop   
       if (message.compareTo("")==0)
       {
    	  
    	   //dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"");
       for (int i=0;i<vecEntityOptionalFieldLabel.size();i++)
 	    {
 		
 		 EntityOptionalFieldModel model = (EntityOptionalFieldModel) vecEntityOptionalFieldLabel .get(i);
    	
        
        //  String EntityFieldTypeName=model.getEntityFieldTypeName();
          String EntityFieldType=model.getEntityFieldTypeId();
          
          //Entity_Field record id
          
          String EntityFieldId = model.getEntityFieldId();
         
        
          
           String j =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_TEXT_AND_FIELD_ID+EntityFieldId);
            
           String k =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_DATE_AND_FIELD_ID+EntityFieldId);
           
           String l =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_NUMBER_AND_FIELD_ID+EntityFieldId);
           
           String m=(String)  paramHashMap.get(CCMInterfaceKey.CONTROL_INPUT_CHECKBOX_AND_FIELD_ID+EntityFieldId);
 
 
     
           
           if (EntityFieldType.compareTo("1")==0)
			   
           {  
         	  //System.out.println("textttttttttt");
       
         
         	  ENTITYDao.insertNewEntityOptionaldetail(con, entity_id, EntityFieldId, j, "1");
         	  
           }
              if (EntityFieldType.compareTo("3")==0)
						   
              {  
          
	        // System.out.println("Numberrrrrrrrrrrrrr"+l);
      ENTITYDao.insertNewEntityOptionaldetail(con, entity_id, EntityFieldId, l, "1");
              }

              if (EntityFieldType.compareTo("5")==0)
				   
              {  
            	  ENTITYDao.insertNewEntityOptionaldetail(con, entity_id, EntityFieldId, k, "1");

              }
              if (EntityFieldType.compareTo("4")==0)
				   
              {  
            	  //System.out.println("checkBox issssssss"+m);
             ENTITYDao.insertNewEntityOptionaldetail(con, entity_id, EntityFieldId, m, "1");
              }
              //dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"");
              dataHashMap.put("flag","1");
 	    }
       
       dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,CCMInterfaceKey.ACTION_SAVE_NEW_ENTITY);
       }  
         
           dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
            //System.out.println("this entity type id in the save new entity" +entityTypeId);	
	}		
       catch (Exception objExp) {
						objExp.printStackTrace();
	}
					
    break;

			    case VIEW_ENTITY_TYPE_LIST:
					try {

						dataHashMap = new HashMap();
						String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						Vector vecEntityType = new Vector();
						String entity_type_id =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
						vecEntityType = ENTITYDao.getEntityTypes(con);
						
						dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,vecEntityType);
						dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ACTION,"1");
						dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entity_type_id);
						 System.out.println("this entity type id in the VIEW entity TYPE" +entity_type_id);
						

					} catch (Exception objExp) {
						objExp.printStackTrace();
					}
					
					break;
					
					
					
				case  VIEW_ENTITY_OPTIONAL_DETAILS:
					try {

						dataHashMap = new HashMap();

						String strUserID = (String) paramHashMap
								.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						
						 Vector vecEntityadditionalField = new Vector();
						
						 String entity_id=(String) paramHashMap.get("entityId");
						  // System.out.println("entity id issssssssssssssssss"+entity_id);
						 
						String entity_type_id =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
						
	
                       // System.out.println("entity type id isssssssssssssssss"+entity_type_id);

  
						    vecEntityadditionalField = ENTITYDao.getAdditionalEntityFieldDataForspecificEntity(con,entity_type_id,entity_id);
						   
					        Vector	vecEntityadditionalFieldLabel = ENTITYDao.getAdditionalEntityFieldLabelForSpecific(con, entity_type_id,entity_id);

						   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,ENTITYDao.getEntityTypes(con));
						  
						   
						   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,ENTITYDao.getEntityMandatoryDataForspecific(con,entity_type_id ,entity_id));
						  
						//   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,ENTITYDao.getEntityMandatoryData(con,entity_type_id));
						   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);
						   
						   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_DATA,vecEntityadditionalField);
						   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entity_type_id);

					} catch (Exception objExp) {
						objExp.printStackTrace();
					}
					break;
					
				case EDIT_ENTITY:
					try {

						dataHashMap = new HashMap();
						
						    dataHashMap.put("action",action);
							String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

							// dont forget t  change it to record
							 String entity_id=(String) paramHashMap.get("entityId");
							 dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID,entity_id);
						   //     System.out.println("this entityid in the edit handleeeeeeeeeeeeeeeeer"+entity_id);	

							 String entityTypeId =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
			               //   System.out.println("this entity type id in the edit handleeeeeeeeeeeeeeeeer"+entityTypeId);			                    			                    

							 Vector	vecEntityadditionalFieldLabel = ENTITYDao.getAdditionalEntityFieldLabel(con, entityTypeId);
						     dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
								 
							dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);
                               //Drawing optional Field 
								 Vector	vecEntityOptionalFieldLabel = ENTITYDao.getEntityOptionalFields(con, entityTypeId);
								 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_OPTIONAL_FIELD_LABEL,vecEntityOptionalFieldLabel);
		 
									dataHashMap.put("flag","2");
									 
									//Drawing optional Field 
									
									 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_OPTIONAL_FIELD_LABEL,vecEntityOptionalFieldLabel);
									 
									 //Data vectors 
								Vector	 vecmandatoryData=(Vector) ENTITYDao.getEntityMandatoryDataForspecific(con,entityTypeId ,entity_id);
								dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,vecmandatoryData);
                           Vector vecEntityadditionalField = ENTITYDao.getAdditionalEntityFieldDataForspecificEntity(con,entityTypeId,entity_id);
								   
						     

							   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,ENTITYDao.getEntityTypes(con));
	                           dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);
							   
							   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_DATA,vecEntityadditionalField);
							   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);

					} catch (Exception objExp) {
						objExp.printStackTrace();
					}
					break;
					
           case VIEW_CREATE_NEW_ENTITY:
					try {
						dataHashMap = new HashMap();

						String strUserID = (String) paramHashMap
								.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						
						String entityTypeId =(String) paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
						
					//	System.out.println("entity type id IN create entity isssssssssssssssss"+entityTypeId);

						 Vector	vecEntityadditionalFieldLabel = ENTITYDao.getAdditionalEntityFieldLabel(con, entityTypeId);
						 dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
						 
						 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);
		 
						//Drawing optional Field 
						 Vector	vecEntityOptionalFieldLabel = ENTITYDao.getEntityOptionalFields(con, entityTypeId);
						 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_OPTIONAL_FIELD_LABEL,vecEntityOptionalFieldLabel);

					} catch (Exception objExp) {
						objExp.printStackTrace();
					}
					break;

		    case UPDATE_ENTITY:
				try {

					dataHashMap = new HashMap();
					ArrayList messageOrderd = new ArrayList() ;
					

					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					 String entity_id =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID);
					dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID,entity_id);
				        System.out.println("this entityid in the UPDATE  handleeeeeeeeeeeeeeeeer"+entity_id);
					  //String record_id=(String) paramHashMap.get("recordId");
					
					 
					
				    String entityTypeId =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
	            //   System.out.println("Handler Entity Type when UPDATE " +entityTypeId);
				//	Vector vecEntityField = new Vector();
			    //Vector vecEntityTypeField = new Vector();
	                Vector vecEntityadditionalField = new Vector();
	                
	              //  vecEntityadditionalField = (Vector)paramHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_DATA);

					 
					 dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
					 
					
	 
					//Drawing optional Field 
					
	                
	                vecEntityadditionalField = ENTITYDao.getAdditionalEntityFieldData(con, entityTypeId);
					 dataHashMap.put("action",action);
	                
					

	                
	                String entityName = (String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME);
	                dataHashMap.put(CCMInterfaceKey.INPUT_TEXT_ENTITY_NAME,entityName); 
					 
					
					String entityCode = (String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_CODE);
					dataHashMap.put(CCMInterfaceKey.INPUT_TEXT_ENTITY_CODE,entityCode); 
					
					String entityAddress= (String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_ENTITY_ADDRESS);
					dataHashMap.put(CCMInterfaceKey.INPUT_TEXT_ENTITY_ADDRESS,entityAddress); 
					

	                    

				  
				   Vector vecEntityOptionalFieldLabel = ENTITYDao.getEntityOptionalFields(con, entityTypeId);
				   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_OPTIONAL_FIELD_LABEL,vecEntityOptionalFieldLabel);	
					    dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,ENTITYDao.getEntityTypes(con));
				
		   
						   String message ="";		
						   
						   
					       
					    
					      
					       	 
					        
        for (int i=0;i<vecEntityOptionalFieldLabel.size();i++)
	    {
		
		 EntityOptionalFieldModel model = (EntityOptionalFieldModel) vecEntityOptionalFieldLabel .get(i);
 	
		   String EntityFieldType=model.getEntityFieldTypeId();
      // String EntityFieldTypeName=model.getEntityFieldTypeName();
       
       //Entity_Field record id
       
       String EntityFieldId = model.getEntityFieldId();

       String j =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_TEXT_AND_FIELD_ID+EntityFieldId);
       dataHashMap.put(CCMInterfaceKey.CONTROL_INPUT_TEXT_AND_FIELD_ID+EntityFieldId,j); 
       String k =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_DATE_AND_FIELD_ID+EntityFieldId);
       dataHashMap.put(CCMInterfaceKey.CONTROL_INPUT_DATE_AND_FIELD_ID+EntityFieldId,k);
       String l =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_NUMBER_AND_FIELD_ID+EntityFieldId);
       dataHashMap.put(CCMInterfaceKey.CONTROL_INPUT_NUMBER_AND_FIELD_ID+EntityFieldId,l);
       String m=(String)  paramHashMap.get(CCMInterfaceKey.CONTROL_INPUT_CHECKBOX_AND_FIELD_ID+EntityFieldId);
       dataHashMap.put(CCMInterfaceKey.CONTROL_INPUT_CHECKBOX_AND_FIELD_ID+EntityFieldId,m);
       dataHashMap.put("action",action);
    //   System.out.println("actiididdbndkjdb is "+action);

				          // Checking Mandatories 
				           
       if (EntityFieldType.compareTo("1")==0)
		   
       {  
     	
     	  if (model.getIsMandatory().compareTo("1")==0&&(j.compareTo("")==0||j==null))
     	  { 
     		 String fieldName =model.getEntityfieldLabel();
     		  message = fieldName+"   must not be null"; 
     		 messageOrderd.add(message);
     		
             
     		 
     	  }  
     	  
}
          if (EntityFieldType.compareTo("3")==0)			   
          {  	  
        	  
        	  //System.out.println("nuberrrrrrrrrr before valida"+l);
        	  if (model.getIsMandatory().compareTo("1")==0&&(l.compareTo("")==0||l==null))
         	  { 
        		   String fieldName =model.getEntityfieldLabel();
         		   message =  fieldName+"  must not be null ";
         		  messageOrderd.add(message);
         		   
         	
                	  }
         	
 	      
         	
        	  else if (model.getIsMandatory().compareTo("1")==0&&(l.compareTo("")!=0||l!=null))
        	  {
        	   try {

				Integer.parseInt(l);
		   } 
        	  catch (Exception e) {
				   String fieldName =model.getEntityfieldLabel();
         		   message =  fieldName+"  must enter a number ";
         		  messageOrderd.add(message);
         		   
         		
                  
				
			}
         }
			   
		      	  else if (model.getIsMandatory().compareTo("1")!=0 && (l.compareTo("")!=0)){
	            	   try {
	            		
	            		System.out.println("BEFEORE PARSE"+l);
	            		
						Integer.parseInt(l);
					
					         } catch (Exception e) {
						   String fieldName =model.getEntityfieldLabel();
	             		   message =  fieldName+"  must enter a number ";
	             		  messageOrderd.add(message);
	             	      
						
			         }
		      	  }
          }  
         if (EntityFieldType.compareTo("5")==0)
			   
          {  
          	  if (model.getIsMandatory().compareTo("1")==0&&(k.compareTo("")==0||k==null))
          	  { 
          		 String fieldName =model.getEntityfieldLabel();
         		  message = fieldName+"  must enter a date";
         		 messageOrderd.add(message);
              
         		  
         	  }

          }
          if (EntityFieldType.compareTo("4")==0)
			   
          {  
        	  
        	  if (model.getIsMandatory().compareTo("1")==0&&(m==null))
          	  { 
        		  String fieldName =model.getEntityfieldLabel();
         		  message = fieldName+"Check Box message must be checked";
         		 messageOrderd.add(message);
               
         	  }
          }
        	  //System.out.println("checkBox issssssss"+m);
              
          } //end of the forLoop
        
        if (message.compareTo("")==0)
        { 
     //	   System.out.println("here in mahmoud if statment");
       
     	 //  System.out.println("here in mahmoud try statment");
     	   	entity_id=ENTITYDao.insertNewEntity(con,entityName,entityTypeId, entityAddress, entityCode, strUserID, "1");
     	
     		  
     		  
     		   System.out.println("message in catch sql exception isss"+entity_id);
     		   if (entity_id.contains("unique constraint")){
     			   message = "Code Must be unique+";
                messageOrderd.add(message);
     		   }
     		   
     		   
     	       if (message.compareTo("")!=0){
     	      	  //System.out.println("b4 break");  
     	      	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,message);
     	      	  dataHashMap.put("flag","2");

     	 				 dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
     	 			 Vector	vecEntityadditionalFieldLabel = ENTITYDao.getAdditionalEntityFieldLabel(con, entityTypeId);
     	 				 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);

     	 				//Drawing optional Field 
     	 				
     	 				 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_OPTIONAL_FIELD_LABEL,vecEntityOptionalFieldLabel);
     	 				dataHashMap.put("action",action);
     	      	  }   
     		         
        
        } 
        
   if (message.compareTo("")!=0){
 	  //System.out.println("b4 break");  
 	  dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,message);
 	 dataHashMap.put(CCMInterfaceKey.MESSAGE_QUEUE_MESSAGE,messageOrderd);
 	  
 	  dataHashMap.put("flag","2");

			 dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entityTypeId);
			 Vector	vecEntityadditionalFieldLabel = ENTITYDao.getAdditionalEntityFieldLabel(con, entityTypeId);
			 
			 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);

			//Drawing optional Field 
			
			 dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_OPTIONAL_FIELD_LABEL,vecEntityOptionalFieldLabel);
			 dataHashMap.put("action",action);

   }	          
 
		    
	
				              
				              
				              
  // execute updati data   
       if (message.compareTo("")==0)
    {
      ENTITYDao.updateEntity(con,entity_id,entityName,entityTypeId, entityAddress, entityCode, strUserID, "1");
 	  // dataHashMap.put("flag","2");
 	  // dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"");				   


  for (int i=0;i<vecEntityOptionalFieldLabel.size();i++)
    {
	 EntityOptionalFieldModel model = (EntityOptionalFieldModel) vecEntityOptionalFieldLabel .get(i);   
     String EntityFieldTypeName=model.getEntityFieldTypeName();
   //Entity_Field record id 
     String EntityFieldId = model.getEntityFieldId();
     String j =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_TEXT_AND_FIELD_ID+EntityFieldId);
       
      String k =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_DATE_AND_FIELD_ID+EntityFieldId);
      
      String l =(String) paramHashMap.get( CCMInterfaceKey.CONTROL_INPUT_NUMBER_AND_FIELD_ID+EntityFieldId);
      
      String m=(String)  paramHashMap.get(CCMInterfaceKey.CONTROL_INPUT_CHECKBOX_AND_FIELD_ID+EntityFieldId);



      
      if (EntityFieldTypeName.equalsIgnoreCase("Text"))
		   
      {  
    	 // System.out.println("textttttttttt");
  
    
    	  ENTITYDao.updateEntityOptionaldetail(con, entity_id, EntityFieldId, j, "1");
    	  
      }
         if (EntityFieldTypeName.equalsIgnoreCase("Number"))
					   
         {  
     
      //  System.out.println("Numberrrrrrrrrrrrrr"+l);
    ENTITYDao.updateEntityOptionaldetail(con, entity_id, EntityFieldId, l, "1");
         }

         if (EntityFieldTypeName.equalsIgnoreCase("Date"))
			   
         {  
       	  ENTITYDao.updateEntityOptionaldetail(con, entity_id, EntityFieldId, k, "1");

         }
         if (EntityFieldTypeName.equalsIgnoreCase("CheckBox"))
			   
         {  
       	  //System.out.println("checkBox issssssss"+m);
        ENTITYDao.updateEntityOptionaldetail(con, entity_id, EntityFieldId, m, "1");
         }
         dataHashMap.put("flag","1");
    }
  
  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,CCMInterfaceKey.ACTION_UPDATE_ENTITY);
    }	
    }
  
				
  catch (Exception objExp) {
					objExp.printStackTrace();
}
				
break;



	        case IMPORT_PRODUCTS_FOR_COMPLEMANTRYS:
	            try                                                                                                 
	            {
	              dataHashMap = new HashMap();
	              String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	              dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	              String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
	              dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelID);

	              String maxSchemaCode = SchemaDAO.getMaxSchemaCode(con);
	              String maxSchemaCodeNumber = maxSchemaCode.substring(2,5);
	              int intMaxSchemaCodeNumber = Integer.parseInt(maxSchemaCodeNumber);
	              intMaxSchemaCodeNumber += 1;
	              dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE,"sc"+intMaxSchemaCodeNumber);
	            }                                                                                                   
	            catch(Exception objExp)                                                                             
	            {                                     
	              objExp.printStackTrace();
	            }
	          break;

	    /** case IMPORT_PRODUCTS_FROM_PGW:
	        try                                                                                                 
	         {
	            dataHashMap = new HashMap();
	            String schemaCode = (String)paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE);
	            String schemaName = (String)paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_NAME);
	            String schemaDescription = (String)paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_DESCRIPTION);
	            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	            String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
	              dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelID);
	        
	            boolean importProduct = SchemaDao.importProduct(con,channelID);
	            if(importProduct==true)
	            {
	              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Products Imported Successfully."); 
	            }
	            else
	            {
	              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"Failed To Import Products."); 
	            }
	          }                                                                                                   
	          catch(Exception objExp)                                                                             
	          {                                     
	            objExp.printStackTrace();
	          }
	        break;
	        
               */
			
	        case VIEW_SERVICE_MANAGEMENT:
				try {

					dataHashMap = new HashMap();
				
					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					Vector vecService = new Vector();

					vecService = ServiceDao.getService(con);
							
					
				

					dataHashMap.put(CCMInterfaceKey.VECTOR_SERVICE,
							vecService);
					
					

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
				

			case CREATE_NEW_SERVICE:
				try {
					dataHashMap = new HashMap();

                 
                   
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					
					Vector service = new Vector();
					dataHashMap.put(CCMInterfaceKey.VECTOR_SERVICE,
							service);
					
	
					

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
				
			case SAVE_NEW_SERVICE:
				try {
					dataHashMap = new HashMap();
              
                   
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					String Name="";
					String Description="";
						Name=(String)paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME);
						Description=(String)paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_SERVICE_DESCRIPTION);
					//	System.out.println("dESCRIPTION ISSSSSSSSSSS"+Description);
						if (Description.length()==0){
							
							Description=" ";	
							
						}
						 ServiceDao.insertNewService(con,Name, Description);
						dataHashMap.put(CCMInterfaceKey.VECTOR_SERVICE,
								ServiceDao.getService(con));
	
				//	dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_FIELD_TYPES,ENTITYDao.getEntityFieldTypes(con));
					
				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
				
			case DELETE_SERVICE:
				try {
					dataHashMap = new HashMap();
              
                   
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					String serviceId=(String) paramHashMap.get("fieldId");
						 ServiceDao.deleeteService(con, serviceId);
						 
						dataHashMap.put(CCMInterfaceKey.VECTOR_SERVICE,
								ServiceDao.getService(con));


				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;

			case EDIT_SERVICE:
				try {
					dataHashMap = new HashMap();
					
					 	
						String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
						String serviceId=(String) paramHashMap.get("fieldId");
	
						Vector service =ServiceDao.getServiceforSpecificId(con,serviceId);
						
						dataHashMap.put(CCMInterfaceKey.VECTOR_SERVICE,
								service);

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
				
			case UPDATE_SERVICE:
				try {
					dataHashMap = new HashMap();
              
                   
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					String Name="";
					String Description="";
					Name=(String)paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME);
					Description=(String)paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_SERVICE_DESCRIPTION);
					String serviceId=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID);
						Name=(String)paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME);
						Description=(String)paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_SERVICE_DESCRIPTION);
						 ServiceDao.updateService(con, serviceId, Name, Description);
						dataHashMap.put(CCMInterfaceKey.VECTOR_SERVICE,
								ServiceDao.getService(con));
			
					
					
					
					
				//	dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_FIELD_TYPES,ENTITYDao.getEntityFieldTypes(con));
					

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
				
	        case VIEW_PROJECT_MANAGEMENT:
				try {
                   String status="1";
                   String channelID="3";
					dataHashMap = new HashMap();
				
					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					
          

				
					Vector	vecProjects =new  Vector();

					dataHashMap.put(CCMInterfaceKey.VECTOR_PROJECT_LIST,
							vecProjects);

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
	        case ADD_PROJECT_SERVICE_SCHEMA:
				try {
                   String status="1";
                   String channelID="3";
					dataHashMap = new HashMap();
				
					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					

					Vector	vecSchema = ProjectDao.getAllSchemas(con, status, channelID);

					dataHashMap.put(CCMInterfaceKey.VECTOR_SCHEMA_LIST,
							vecSchema);
					Vector	vecService = ServiceDao.getService(con);
					Vector  vecEntity=ENTITYDao.getAllEntities(con);
				//	System.out.println("entity vector in add project handler is:"+vecEntity);

					dataHashMap.put(CCMInterfaceKey.VECTOR_SERVICE_LIST,
							vecService);
					dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,vecEntity);
					
					
					  Vector vecProject = new Vector();
			             
		              
			      		dataHashMap.put(CCMInterfaceKey.VECTOR_PROJECT,
			      				vecProject);

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
	        case SAVE_PROJECT:
				try {
                  String status="1";
                   String channelID="3";
					dataHashMap = new HashMap();
				 
					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					
					String projectName="";
					String projectAddress="";
					String projectDescription="";
					String projectStartDate="";
					String serviceids="";
					
					String entityids ="";
					String schemaName="";
					String schemaId="";
					

					projectName=(String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_PROJECT_NAME);
					 projectAddress=(String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_PROJECT_ADDRESS);
					projectDescription=(String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_PROJECT_DESCRIPTION);
					 projectStartDate=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_TEXT_PROJECT_START_DATE);
				//	 schemaName=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_SCHEMA_NAME);
					 schemaId=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_SCHEMA_ID);
					 System.out.println("schemaId issssssssss save project handler  "+schemaId);

					 
			     serviceids = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID);
			//	System.out.println("Services idss  assignded tom project issssssss"+serviceids);
				  entityids = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID);
			//	System.out.println("Entity idss  assignded tom project issssssss"+entityids);
			     Long  projectID=ProjectDao.insertNewProject(con, projectName, projectDescription,status, projectAddress, projectStartDate,schemaId);
					
				 dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectID);

			      StringTokenizer st = new StringTokenizer(serviceids,",");
			      while (st.hasMoreTokens()) 
			      {
			         String serviceid = st.nextToken();
			         ProjectDao.insertAssignedService(con,projectID.toString(),serviceid);
			      }
			      
			      
			      StringTokenizer et = new StringTokenizer(entityids,",");
			      while (et.hasMoreTokens()) 
			      {
			         String entityid = et.nextToken();
			         ProjectDao.insertAssignedEntities(con,projectID.toString(),entityid);
			      }
					Vector	vecProjects =ProjectDao.getProject(con);

					dataHashMap.put(CCMInterfaceKey.VECTOR_PROJECT_LIST,
							vecProjects);
			
					

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
			

	        case VIEW_SERVICE_ASSIGNED_PROJECT:
				try {

					dataHashMap = new HashMap();
				
					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					
					Vector vecService = new Vector();
				String	projectId=(String) paramHashMap.get("fieldId");
				String	projectName=(String) paramHashMap.get("project");
				System.out.println("proiject name inm the handler issssss"+projectName);
					vecService = ProjectDao.getServiceAssignedProject(con, projectId);
							
					
				

					dataHashMap.put(CCMInterfaceKey.VECTOR_SERVICE_ASSIGNED_PROJECT,
							vecService);
					
					

					dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME,
							projectName);
					

					
					//String projectName =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME);
				
					//String projectStartDate =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_TEXT_PROJECT_START_DATE);

					//String schema =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA);
					   
					  Vector project =ProjectDao.getSpecificProject(con, projectId);
					  dataHashMap.put(CCMInterfaceKey.VECTOR_PROJECT_LIST,project);
				
					   
					   
					   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME,projectName);
					   //dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA,schema);
					 
					  
					  //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,CCMInterfaceKey.ACTION_SEARCH_PROJECT);
					
					

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
	        case SUB_ENTITY_TREE:
	            try                                                                                                 
	            {
	            	
	            	String status="1";
	                   String channelID="3";
						dataHashMap = new HashMap();
					 
						String strUserID = (String) paramHashMap
								.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
						
						String projectName="";
						String projectAddress="";
						String projectDescription="";
						String projectStartDate="";
						String serviceids="";
						String entityids ="";
						String schemaName="";
						String schemaId="";
						

						projectName=(String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_PROJECT_NAME);
						 projectAddress=(String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_PROJECT_ADDRESS);
						projectDescription=(String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_PROJECT_DESCRIPTION);
						 projectStartDate=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_TEXT_PROJECT_START_DATE);
						 //schemaName=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_SCHEMA_NAME);
						 schemaId=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_SELECT_SCHEMA_ID);
						 System.out.println("schemaId issssssssss "+schemaId);

						 
				     serviceids = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID);
				//	System.out.println("Services idss  assignded tom project issssssss"+serviceids);
					  entityids = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID);
				//	System.out.println("Entity idss  assignded tom project issssssss"+entityids);
				     Long  projectID=ProjectDao.insertNewProject(con, projectName, projectDescription,status, projectAddress, projectStartDate,schemaId);
				     dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME,projectName);
				      
		          //    projectName= prjectModel.getProjectName();
	                  System.out.println("The Project name in the handler  isssssssssssss " +projectName);

				      StringTokenizer st = new StringTokenizer(serviceids,",");
				      while (st.hasMoreTokens()) 
				      {
				         String serviceid = st.nextToken();
				         ProjectDao.insertAssignedService(con,projectID.toString(),serviceid);
				      }
				      
				      
				  //    StringTokenizer et = new StringTokenizer(entityids,",");
				  //    while (et.hasMoreTokens()) 
				  //    {
				  ///       String entityid = et.nextToken();
				  //       ProjectDao.insertAssignedEntities(con,projectID.toString(),entityid);
				   //   }
				     	Vector	vecProjects =ProjectDao.getProject(con);

						dataHashMap.put(CCMInterfaceKey.VECTOR_PROJECT_LIST,
								vecProjects);
						
                    con.commit();

	           
	              Vector entities = ENTITYDao.getAllEntities(con);
	              
	              System.out.println("Entity ssssssssssssssssssss"+entities.size());
	              
	             
	          //     String   projectId=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);
	           //   System.out.println ("The project Id issssssssss " + projectID);
	            //  Thread.sleep(3000);
	              Vector projectentities = ENTITYDao.getChildEntites(con,projectID.toString());
	              System.out.println("projectentities sizew is "+projectentities.size());
	           //   Thread.sleep(3000);
	           //   System.out.println("The Vector size issssssssssssssssssss" +projectentities.size());
	              
	              
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectID.toString());
	             
	             dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT,projectentities);
	           //  Thread.sleep(3000);
	             Vector projectentitiesAll = ENTITYDao.getChildEntitesforproject(con,projectID.toString());
	             System.out.println("projectentitiesAll size isss "+projectentitiesAll.size());
	             //Thread.sleep(3000);
	             dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ALL_PROJECT,projectentitiesAll);
	             
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,entities);
	        
	            }
	            catch(Exception objExp)                                                                             
	            {                                     
	              objExp.printStackTrace();
	            }
	            break;
	            
         case ADD_SUB_ENTITIES:
	            try
	            {
	             dataHashMap = new HashMap ();
	             String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	            // System.out.println("User From Page Secondf Time "+strUserID);
	             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	             
	              String  ParentId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_PARENT);
	            //  System.out.println("Parent id in ADD SUB ENTITY IS:"+ParentId);
	              String  childId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME);
	            String  projectId = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);
   
	              ENTITYDao.addSubEntity(con, projectId.toString(), ParentId, childId);
	                            
	              Vector entities = ENTITYDao.getAllEntities(con);
	           
	              Vector projectentities = ENTITYDao.getChildEntitesforproject(con,projectId);
	              
	               
	              
	          //   Vector projectentities = ENTITYDao.getChildEntitesforproject(con,projectId);
	              
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectId);
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT,projectentities);
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,entities);
	              
	              Vector projectentitiesAll = ENTITYDao.getChildEntitesforproject(con,projectId);
		             dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ALL_PROJECT,projectentitiesAll);
	            }
	            catch(Exception objExp)                                                                             
	            {                                     
	              objExp.printStackTrace();
	            }
	            break;   
	            
	            
            case ADD_SUB_ENTITIES_NEW:
	            try
	            
	            {
	             dataHashMap = new HashMap ();
	             String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	            // System.out.println("User From Page Secondf Time "+strUserID);
	             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	             
	             // String  ParentId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_PARENT22);
	             
	             String  ParentId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID);
	             
	             String  ProjectName = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME);
	             
	         //   System.out.println("ParentId ib th e handler issssssssssss:"+ParentId);
	           //   String  childId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME22);
	             
	        	 String childId=(String) paramHashMap.get("entityId");
	        	 
	        	// String entityTypeID=(String) paramHashMap.get("entityTypeId");	              
	        	 String  projectId = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);
	              
	            //  System.out.println("ProjectId ib th e handler issssssssssss:"+projectId);
	              
	              if(ProjectDao.checkEntityExist(con, childId, projectId))
	              {
	            	  ProjectDao.updateSubEnities(con, projectId, childId, ParentId) ;
	              }else{
	            	  ProjectDao.insertSubEntities(con, projectId, childId, ParentId) ;  
	              }
	
	              
	              con.commit();
	             // 	System.out.println("insert parent is"+ParentId+" child is"+childId);
	              	
	              
	           //   Vector entities = ENTITYDao.getentityByFilter(con, entityTypeID,"" , "");
	              
	              Vector projectentitiesAll = ENTITYDao.getChildEntitesforproject(con,projectId.toString());
	             
	            
	              Vector projectentities = ENTITYDao.getChildEntites(con,projectId.toString());
	           
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectId);
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT,projectentities);
	              
	         //     dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ALL_PROJECT,projectentitiesAll);
	         //     dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,entities);
	             // String projectName="";
	              for (int j=0; j<projectentitiesAll.size(); j++)
	              {
	            	//System.out.println("sss dddd "+projectentity.get(j));
	           // 	System.out.println("j="+j);
	                EntityDto dto = (EntityDto) projectentitiesAll.get(j);
	                entityProjectModel prjectModel = (entityProjectModel)dto.getModel();
	                 // String regionTypeLevelId = regionModel.getRegionLevelTypeId();
	                  String enityName = prjectModel.getEntityName();
	               //   System.out.println("The entity name in the handler  isssssssssssss " +enityName);
	                  String enityId = prjectModel.getEntityId();
	                  //projectName= prjectModel.getProjectName();
	                 // System.out.println("The Project name in for loop handler  isssssssssssss " +projectName);

	              }
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME,ProjectName);
	        //      System.out.println("project name in the ccm hamdler issssssss"+ProjectName);
	              
	        
	            }
	            
	            catch(Exception objExp)                                                                             
	            {                                     
	              objExp.printStackTrace();
	            }
	            break;
	            
	            
	            
	            
	            
	            
	            
	            
	            
            case ADD_ENTITIES_FIRST_LEVEL:
	            try
	            
	            {
	             dataHashMap = new HashMap ();
	             String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	            // System.out.println("User From Page Secondf Time "+strUserID);
	             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	             
	             // String  ParentId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_PARENT22);
	             
	             String  ParentId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID);
	             
	             String  ProjectName = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME);
	             
	            //System.out.println("ParentId ib th FIRST LEVEL  issssssssssss:"+ParentId);
	           //   String  childId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME22);
	             
	        	 String childId=(String) paramHashMap.get("entityId");
	        	 
	        	// String entityTypeID=(String) paramHashMap.get("entityTypeId");	              
	        	 String  projectId = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);
	              
	             // System.out.println("ProjectId ib th e handler issssssssssss:"+projectId);
	              
	        
	            	  ProjectDao.insertSubEntities(con, projectId, childId, projectId) ;  
	             
	
	              
	              con.commit();
	              //	System.out.println("insert parent is"+ParentId+" child is"+childId);
	              	
	              
	           //   Vector entities = ENTITYDao.getentityByFilter(con, entityTypeID,"" , "");
	              
	              Vector projectentitiesAll = ENTITYDao.getChildEntitesforproject(con,projectId.toString());
	             
	            
	              Vector projectentities = ENTITYDao.getChildEntites(con,projectId.toString());
	           
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectId);
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT,projectentities);
	              
	         //     dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ALL_PROJECT,projectentitiesAll);
	         //     dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,entities);
	             // String projectName="";
	              for (int j=0; j<projectentitiesAll.size(); j++)
	              {
	            	//System.out.println("sss dddd "+projectentity.get(j));
	            //	System.out.println("j="+j);
	                EntityDto dto = (EntityDto) projectentitiesAll.get(j);
	                entityProjectModel prjectModel = (entityProjectModel)dto.getModel();
	                 // String regionTypeLevelId = regionModel.getRegionLevelTypeId();
	                  String enityName = prjectModel.getEntityName();
	               //   System.out.println("The entity name in the handler  isssssssssssss " +enityName);
	                  String enityId = prjectModel.getEntityId();
	                  //projectName= prjectModel.getProjectName();
	                 // System.out.println("The Project name in for loop handler  isssssssssssss " +projectName);

	              }
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME,ProjectName);
	              
	            
	              
	        //      System.out.println("project name in the ccm hamdler issssssss"+ProjectName);
	              
	        
	            }
	            
	            catch(Exception objExp)                                                                             
	            {                                     
	              objExp.printStackTrace();
	            }
	            break;
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
            case DELETE_SUB_ENTITIES:
	            try
	            
	            {
	  
	            String message ="";
	             dataHashMap = new HashMap ();
	             String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	            // System.out.println("User From Page Secondf Time "+strUserID);
	             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	             
	             // String  ParentId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_PARENT22);
	             
	             String  ParentId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID);
	           // System.out.println("ParentId ib th e handler issssssssssss:"+ParentId);
	           //   String  childId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME22);
	             
	        	 String childId=(String) paramHashMap.get("entityId");
	        	 
	              String  projectId = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);
	              String  projectName = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME);
	              
	             // System.out.println("ProjectId ib th delete handler issssssssssss:"+projectId);
	              
	              String flagFromPage="";
	              flagFromPage=(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_FLAGN);
	              System.out.println("flagFromPage in the handler isssssssss"+flagFromPage);
	        boolean check=  ProjectDao.checkChildExist(con, ParentId, projectId);
	        
	        System.out.println("flagFromPage in the handler isssssssss   "+flagFromPage);
	        if((check==false) || flagFromPage.compareTo("secondTime")==0 ){	           
	            	  ProjectDao.deleteSubEntities(con,projectId,ParentId);	    
	            	  System.out.println("Delete function  done : ") ;
	            	  
	            	  
	            	  
                  con.commit();
                  
	           }
	        
	        else 
	        {
	        	message="Are you Sure u want t delete entity with its sub entities";
	            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,message);
	            
	        	  dataHashMap.put("flag","childExist");
	            System.out.println("Delete function not done : ") ;
	            
	              dataHashMap.put(CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID,ParentId);
	            
	        	
	        	
	        }
	              	
	        
	     //   System.out.println("delete parent is"+ParentId+" child is"+childId);
	              	
	              
	              Vector entities = ENTITYDao.getAllEntities(con);
	              
	              Vector projectentitiesAll = ENTITYDao.getChildEntitesforproject(con,projectId.toString());
	             
	            
	              Vector projectentities = ENTITYDao.getChildEntites(con,projectId.toString());
	           
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectId);
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME,projectName);
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT,projectentities);
	              
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ALL_PROJECT,projectentitiesAll);
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,entities);
	               for (int j=0; j<projectentitiesAll.size(); j++)
	             {
	            	//System.out.println("sss dddd "+projectentity.get(j));
	            	//System.out.println("j="+j);
	                EntityDto dto = (EntityDto) projectentitiesAll.get(j);
	                entityProjectModel prjectModel = (entityProjectModel)dto.getModel();
	                 // String regionTypeLevelId = regionModel.getRegionLevelTypeId();
	                  String enityName = prjectModel.getEntityName();
	                //  System.out.println("The entity name in the handler  isssssssssssss " +enityName);
	                  String enityId = prjectModel.getEntityId();
	               //   System.out.println("The entity name in the handler  isssssssssssss " +enityId);

	              }
	            }
	            
	            catch(Exception objExp)                                                                             
	            {                                     
	              objExp.printStackTrace();
	            }
	            break; 
	            
	 /**           
            case ADD_SUB_ENTITIES_LEVEL1:
	            try
	            {
	             dataHashMap = new HashMap ();
	             String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	             System.out.println("User From Page Secondf Time "+strUserID);
	             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	             
	              String  ParentId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_PARENT22);
	              String  childId = (String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME22);
	              String projectId = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);

	              ProjectDao.insertSubEntities(con, projectId, childId, ParentId) ;
	              	              
	              Vector entities = ENTITYDao.getAllEntities(con);
	             
	              Vector projectentities = ENTITYDao.getChildEntitesLevel1(con,projectId.toString());
	           
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectId);
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT,projectentities);
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,entities);
	            }
	            catch(Exception objExp)                                                                             
	            {                                     
	              objExp.printStackTrace();
	            }
	            break; 
	            */
				
	        case EDIT_SUB_ENTITY_TREE:
	            try                                                                                                 
	            {
	            	
	            	String status="1";
	                   String channelID="3";
						dataHashMap = new HashMap();
					 
						String strUserID = (String) paramHashMap
								.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
						
						String	projectId=(String) paramHashMap.get("fieldId");
						String	projectName=(String) paramHashMap.get("project");
					//	System.out.println("projectName innnnnnnn edit handler isssssssss:"+projectName);
						
						
					
	             // Vector entities = ENTITYDao.getAllEntities(con);
	            //  System.out.println("Entity ssssssssssssssssssss"+entities);
	              
	          //     String   projectId=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);
	            //  System.out.println ("The project Id issssssssss " + projectId);
	              Vector projectentities = ENTITYDao.getChildEntites(con,projectId);
	            //  System.out.println("The Vector size issssssssssssssssssss" +projectentities.size());
	              
	           //   Vector entities = ENTITYDao.getentityByFilter(con, entityTypeID,"" , "");
	              
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectId);
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT,projectentities);
	            //  dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,entities);
	              
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME,projectName);
	              
	              Vector projectentitiesAll = ENTITYDao.getChildEntitesforproject(con,projectId.toString());
	              dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ALL_PROJECT,projectentitiesAll);
	            }
	            catch(Exception objExp)                                                                             
	            {                                     
	              objExp.printStackTrace();
	            }
	            break;
	            
	            
	        case EDIT_PROJECT:
	            try                                                                                                 
	            {
	            	System.out.println("HERE IN EDIT PROJEEECTTTTTTTTTTT");
	             	String status="1";
	                   String channelID="3";
						dataHashMap = new HashMap();
						
						
						dataHashMap.put("flag","edit");
						String strUserID = (String) paramHashMap
								.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
						String	projectId=(String) paramHashMap.get("fieldId");
						
						
	              Vector vecProject = ProjectDao.getSpecificProject(con,projectId);
	             
	              
	      		dataHashMap.put(CCMInterfaceKey.VECTOR_PROJECT,
	      				vecProject);
	      		Vector	vecSchema = ProjectDao.getAllSchemas(con, status, channelID);

				dataHashMap.put(CCMInterfaceKey.VECTOR_SCHEMA_LIST,vecSchema);
	              
	              
	              dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectId);
	              
	              
	              
	          	
				Vector	vecService = ServiceDao.getService(con);
				Vector  vecEntity=ENTITYDao.getAllEntities(con);
				Vector  vecServiceProject=ProjectDao.getServiceforSpecificproject(con, projectId);
				
				Vector  vecentityProject=ProjectDao.getEntityforSpecificproject(con, projectId);
				
				dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ASSIGNED_PROJECT,vecentityProject);
				
				//System.out.println("HERE IN EDIT PROJEEECTTTTTTTTTTT");
				//System.out.println("Vector service assigned project"+vecServiceProject.size());
				
				
				dataHashMap.put(CCMInterfaceKey.VECTOR_SERVICE_ASSIGNED_PROJECT,vecServiceProject);

				dataHashMap.put(CCMInterfaceKey.VECTOR_SERVICE_LIST,
						vecService);
				dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,
						vecEntity);
	            
	            }
	            catch(Exception objExp)                                                                             
	            {                                     
	              objExp.printStackTrace();
	            }
	            break;
	            
	            
			case SEARCH_PROJECT:
				try {

					dataHashMap = new HashMap();

					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					
		
					
					String projectName =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME);
					System.out.println("projectName issss"+projectName);
					String projectAddress =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_ADDRESS);
					//String projectStartDate =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_TEXT_PROJECT_START_DATE);

					String schema =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA);
					   
					  Vector project =ProjectDao.getprojectByFilter(con, projectName,projectAddress,schema);
					  dataHashMap.put(CCMInterfaceKey.VECTOR_PROJECT_LIST,project);
				
					   
					   
					   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME,projectName);
					   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA,schema);
					 
					  
					  dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,CCMInterfaceKey.ACTION_SEARCH_PROJECT);

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
			
	        case UPDATE_PROJECT:
				try {
                  String status="1";
                   String channelID="3";
					dataHashMap = new HashMap();
					
				
					System.out.println("herer in update action");
					// dataHashMap.put("hiddenUpdateFlag","update");
				//	System.out.println(paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID+"1") instanceof String);
					String	projectId=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID+"1");
				//	System.out.println("project id  in  update handler:"+projectId);
					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					
					String projectName="";
					String projectAddress="";
					String projectDescription="";
					String projectStartDate="";
					String serviceids="";
					
					String entityids ="";
					String schemaName="";
					String schemaId="";
					

					projectName=(String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_PROJECT_NAME);
					 projectAddress=(String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_PROJECT_ADDRESS);
					projectDescription=(String) paramHashMap.get(CCMInterfaceKey.INPUT_TEXT_PROJECT_DESCRIPTION);
					 projectStartDate=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_TEXT_PROJECT_START_DATE);
					 //schemaName=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_SCHEMA_NAME);
					 schemaId=(String) paramHashMap.get(CCMInterfaceKey.CONTROL_SCHEMA_ID);

					 
			     serviceids = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID);
				//System.out.println("Services idss  assignded tom project issssssss"+serviceids);
				  entityids = (String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID);
				
			    ProjectDao.updateProject(con, projectId, projectName, projectAddress, projectDescription, status, projectStartDate, schemaId);
					
				 dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectId);

			      StringTokenizer st = new StringTokenizer(serviceids,",");
			     
			      ProjectDao.deleteAssignedService(con,projectId);
			      while (st.hasMoreTokens()) 
			      {
			         String serviceid = st.nextToken();
			        ProjectDao.insertAssignedService(con,projectId,serviceid);
			      }
			     // System.out.println("Entity idss  assignded tom project issssssss"+entityids);
			 /** 
			      ProjectDao.deleteAssignedEntities(con,projectId);
			      StringTokenizer et = new StringTokenizer(entityids,",");
			      while (et.hasMoreTokens()) 
			      {
			         String entityid = et.nextToken();
			       ProjectDao.insertAssignedEntities(con,projectId,entityid);
			      }
              */
					Vector	vecProjects =ProjectDao.getProject(con);

					dataHashMap.put(CCMInterfaceKey.VECTOR_PROJECT_LIST,
							vecProjects);
			} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
				
				
			case CHOOSE_SUB_ENTITY:
				try {

					dataHashMap = new HashMap();

					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					
					 Vector vecEntityadditionalField = new Vector();
					
				//	String entity_type_id =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
					String projectName =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME);
					// parent id passed from tree

					String parentId =(String)paramHashMap.get(CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID);
					 String entitytypeId="";	
					 
					 Vector vec1=ENTITYDao.getSpecificEntity(con,parentId);
				        for(int i=0;i<vec1.size();i++)
				        {
				          EntityMandatoryDataModel entityMandatoryDataModel = (EntityMandatoryDataModel)  vec1.get(i);
				          entitytypeId=entityMandatoryDataModel.getEntityTypeID();
				        }
				        
			
			
			Vector vec2=  ENTITYDao.getSpecificEntityTypes(con,entitytypeId);
			
			String entitytypeName="";
			
	        for(int i=0;i<vec2.size();i++)
	        {
	        	EntityTypesModel typeModel = (EntityTypesModel)  vec2.get(i);
	        	entitytypeName=typeModel.getENTITY_TYPE_NAME();
	        }
	
					
					String projectId =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);

					//System.out.println("xxxxxxxxxxxxxx"+parentId);
					String entityName =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME);
					String entityCode =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE);

					//String entityName = (String)paramHashMap.get();
					
					String entityID =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID);
                    //System.out.println("entity type id isssssssssssssssss"+entity_type_id);


					
                    
					   vecEntityadditionalField = ENTITYDao.getAdditionalEntityFieldData(con,entitytypeId);
					   
				        Vector	     vecEntityadditionalFieldLabel = ENTITYDao.getAdditionalEntityFieldLabel(con, entitytypeId);

					   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,ENTITYDao.getEntityTypes(con));
					   
					 // Vector entMandatoryData =ENTITYDao.getentityByFilter(con,entity_type_id,entityName,entityCode);
					   
					   Vector entMandatoryData =ENTITYDao.getentityFilteredForParent(con, projectId,entitytypeId);
					  dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,entMandatoryData);
					  
					//   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,ENTITYDao.getEntityMandatoryData(con,entity_type_id));
					   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);
					   
					   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_DATA,vecEntityadditionalField);
					   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entitytypeId);
					   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE,entityCode);
					   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME,entityName);
					   
					   dataHashMap.put(CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID,parentId);
					   
					   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectId);
					   
					   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME,projectName);
					   
					   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_NAME,entitytypeName);
					   
					   
                       dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,CCMInterfaceKey.ACTION_SEARCH_ENTITY);
                       
                       dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ACTION,"1");;

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
				
				
			case CHOOSE_ENTITY_FIRST_LEVEL:
				try {

					dataHashMap = new HashMap();

					String strUserID = (String) paramHashMap
							.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					
					 Vector vecEntityadditionalField = new Vector();
					
				//	String entity_type_id =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
					String projectName =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME);
					// parent id passed from tree

				//	String parentId =(String)paramHashMap.get(CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID);
					// String entitytypeId="";	
					 
					// Vector vec1=ENTITYDao.getAllEntities(con);
				
          	    String projectId =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID);

					//System.out.println("xxxxxxxxxxxxxx"+parentId);
					String entityName =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME);
					String entityCode =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE);

					//String entityName = (String)paramHashMap.get();
					
					//String entityID =(String)paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_ID);
                    //System.out.println("entity type id isssssssssssssssss"+entity_type_id);


					
                    
					//   vecEntityadditionalField = ENTITYDao.getAdditionalEntityFieldData(con,entitytypeId);
					   
				     //   Vector	     vecEntityadditionalFieldLabel = ENTITYDao.getAdditionalEntityFieldLabel(con, entitytypeId);

					   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,ENTITYDao.getEntityTypes(con));
					   
					 // Vector entMandatoryData =ENTITYDao.getentityByFilter(con,entity_type_id,entityName,entityCode);
					   
					   Vector entMandatoryData =ENTITYDao.getAllEntitiesFirstLevel(con,projectId);
					  dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,entMandatoryData);
					  
					//   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_MANDATORY_DATA,ENTITYDao.getEntityMandatoryData(con,entity_type_id));
					  // dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL,vecEntityadditionalFieldLabel);
					   
					   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_ADDITIONAL_DATA,vecEntityadditionalField);
					//   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID,entitytypeId);
					   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_CODE,entityCode);
					   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_ENTITY_NAME,entityName);
					   
					//   dataHashMap.put(CCMInterfaceKey.INPUT_HIDDEN_PARENT_ID,parentId);
					   
					   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_ID,projectId);
					   
					   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_PROJECT_NAME,projectName);
					   
					//   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_NAME,entitytypeName);
					   
					   dataHashMap.put(CCMInterfaceKey.CONTROL_HIDDEN_ACTION,"2");
					   
					   
                       dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION,CCMInterfaceKey.ACTION_SEARCH_ENTITY);

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
				
				
			case DELETE_PROJECT:
				try {
					dataHashMap = new HashMap();
              
                   
					String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
					    String	projectId=(String) paramHashMap.get("fieldId");
						ProjectDao.deleteProject(con, projectId);
						
						  Vector project =ProjectDao.getProject(con);
						  dataHashMap.put(CCMInterfaceKey.VECTOR_PROJECT_LIST,project);
						 
			

				} catch (Exception objExp) {
					objExp.printStackTrace();
				}
				break;
	         case VIEW_ADD_AND_WIN_SHOP_ASSIGNMENT:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		             String    strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             
		             CCMHandler.rowNum = 0;
		          
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		             Vector vecShops = addAndWinDao.getShops(con);
		             Vector vecAddandWin= addAndWinDao.getAddAndWin(con, CCMHandler.rowNum);
		           
		          
		             dataHashMap.put(CCMInterfaceKey.VECTOR_SHOPS,vecShops);
		           
		             //dataHashMap.put(CCMInterfaceKey.VECTOR_ADD_AND_WIN,null);
		             dataHashMap.put(CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, 0+"");
		             
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
	         case ASSIGN_ADD_AND_WIN_TO_SHOP:
	        	 try
	        	 {
	        		 dataHashMap = new HashMap();
		           String   strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
		             dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
		      
		            String row_num = (String) paramHashMap.get(CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM);
		            System.out.println("rrrrrrrrrrrrrrr: "+CCMHandler.rowNum);
		         	int  row_number = Integer.parseInt( row_num);
		             String selected_shop_value = null;
		            // Vector addAndWin= addAndWinDao.getAddAndWinByFilter(con,"","",row_number);
		             Vector addAndWin = addAndWinDao.getAddAndWin(con,row_number);
		             dataHashMap.put(CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, row_num);
		             String addAndWind_shopID = null;
		        
		         	for (int i =0;i<addAndWin.size();i++)
		          	{
		          		AddAndWinShopAssignmentModel addAndWinModel=(AddAndWinShopAssignmentModel)  addAndWin.get(i);
		          		String  addAndWinId = addAndWinModel.getDCM_ID();
		          		
		          		 String  choosenaddAndWinId = addAndWinModel.getADDANDWIN_ID();
		          		System.out.println("parameter : "+(CCMInterfaceKey.CONTROL_HIDDEN_SHOP_ID+addAndWinId));
		          		selected_shop_value = (String) paramHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_SHOP_ID+addAndWinId);
	                	addAndWind_shopID = addAndWinModel.getSHOP_ID();
		          		
		         	if(choosenaddAndWinId != null && selected_shop_value!= null)
		         		{
		         			
		         			System.out.println("update function ");
		         			System.out.println("add and win idddddd isssssss"+addAndWinId);
		          			System.out.println("selected shop id isssssssss"+selected_shop_value);
		          			addAndWinDao.updateAddandWin_shop(con,addAndWinId,selected_shop_value);
		         			
		         			
		         		}
		          		
		          		
		          		
		         	else  if(selected_shop_value!= null && selected_shop_value.compareTo("")!=0)
		          		{
		          			System.out.println("add and win idddddd isssssss"+addAndWinId);
		          			System.out.println("selected shop id isssssssss"+selected_shop_value);
	          				System.out.println("insert function");
	          				addAndWinDao.insertAddandWin_shop(con,addAndWinId,selected_shop_value);
                         }
		         	
		     
		          	}
		             
		             Vector vecShops = addAndWinDao.getShops(con);
		             Vector vecAddandWin= addAndWinDao.getAddAndWin(con,row_number);
		           
		          
		             dataHashMap.put(CCMInterfaceKey.VECTOR_SHOPS,vecShops);
		         
		             dataHashMap.put(CCMInterfaceKey.VECTOR_ADD_AND_WIN,vecAddandWin);
		           /*  
		             for(int j=0; j<paramHashMap.size();j++)
		             {
		            String tempString = (String)paramHashMap.keySet().toArray()[j];
		            System.out.println("The temp string isssss "+tempString);
		            String strOptional = "";
		            String addAndWInId = "";
		            String shop = "";
		            tempString=tempString.replace(CCMInterfaceKey.CONTROL_HIDDEN_SHOP_ID, "");
		           
		            
		            
		            
		            System.out.print("temp string value issssssssssssssssssss"+tempString);
		            
                    if(tempString.startsWith(CCMInterfaceKey.CONTROL_HIDDEN_SHOP_ID))
		            {
		            	 tempString=tempString.replace(CCMInterfaceKey.CONTROL_HIDDEN_SHOP_ID, "");
		            // strOptional = (String)paramHashMap.get(tempString);
		             System.out.println("The values issssssssss " + tempString);
		             /*addAndWInId = tempString.substring(
		                                 tempString.lastIndexOf(
		                                    		 (CCMInterfaceKey.CONTROL_HIDDEN_SHOP_ID)+22));*/
		             
		         //   System.out.println("The ADD AND WIN id issssss " + addAndWInId);
		 
		           // }
//		             String group = "";
//		             if(tempString.startsWith(AccInterfaceKey.INPUT_SEARCH_TEXT_GROUP_ID))
//			            {
//			             group = (String)paramHashMap.get(tempString);
//			             System.out.println("The group issssssssss " + group);
//			             
//			             
//			             }
		            //  AccDAO.updateTransactionType(con, transactionTypeId, strOptional,group);      
		             
		             
		           // }
		             
		          //  Vector transactionType = AccDAO.getTransactionType(con);
		           //  dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,transactionType);
	        	 }
	        	 catch(Exception objExp)
	             {
	               objExp.printStackTrace();
	             }
	           break;
	           
				case SEARCH_ADD_AND_WIN:
					try {

						dataHashMap = new HashMap();

						String strUserID = (String) paramHashMap
								.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					String row_num = (String) paramHashMap.get(CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM);
					dataHashMap.put(CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, row_num);
					
					int  row_number = Integer.parseInt( row_num);
					
					 System.out.println("Row number issssssssssssssssss: "+row_num);
			         	 
				
						
				
						
						String Name =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_NAME);
						String Code =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_CODE);
                       
					   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,ENTITYDao.getEntityTypes(con));
						   
						   Vector vecShops = addAndWinDao.getShops(con);
				          
				             Vector vecAddandWin= addAndWinDao.getAddAndWinByFilterSearch(con,Name,Code,row_number);
				             
				     
				             
				             
						   
				             
				             
				             dataHashMap.put(CCMInterfaceKey.VECTOR_SHOPS,vecShops);
					         
				             dataHashMap.put(CCMInterfaceKey.VECTOR_ADD_AND_WIN,vecAddandWin);
				             
				             dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_NAME,Name);
							   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_CODE,Code);
				             
				       

					} catch (Exception objExp) {
						objExp.printStackTrace();
					}
					break;
					
					
				case NEXT_AND_PREVIOUS:
					try {

						dataHashMap = new HashMap();

						String strUserID = (String) paramHashMap
								.get(InterfaceKey.HASHMAP_KEY_USER_ID);
					String row_num = (String) paramHashMap.get(CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM);
					dataHashMap.put(CCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, row_num);
					
					int  row_number = Integer.parseInt( row_num);
					 System.out.println("rrrrrrrrrrrrrrr: "+row_num);
			         	 
						
						
				
						
						String Name =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_NAME);
						String Code =(String)paramHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_CODE);
                       
					   dataHashMap.put(CCMInterfaceKey.VECTOR_ENTITY_TYPES,ENTITYDao.getEntityTypes(con));
						   
						   Vector vecShops = addAndWinDao.getShops(con);
				          
				             Vector vecAddandWin= addAndWinDao.getAddAndWinByFilter(con,Name,Code,row_number);
						   
				             
				             
				             dataHashMap.put(CCMInterfaceKey.VECTOR_SHOPS,vecShops);
					         
				             dataHashMap.put(CCMInterfaceKey.VECTOR_ADD_AND_WIN,vecAddandWin);
				             
				      	   
							   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_NAME,Name);
							   dataHashMap.put(CCMInterfaceKey.INPUT_SEARCH_TEXT_ADDANDWIN_CODE,Code);
				             
				       

					} catch (Exception objExp) {
						objExp.printStackTrace();
					}
					break;
	           
                 default:
				Utility.logger.debug("Unknown action received: " + action);
			}

			if (con != null) {
				Utility.closeConnection(con);
			}
		} catch (Exception objExp) {
			objExp.printStackTrace();
		}

		return dataHashMap;
	}
}

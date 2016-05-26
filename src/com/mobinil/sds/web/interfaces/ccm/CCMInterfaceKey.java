package com.mobinil.sds.web.interfaces.ccm;

import com.mobinil.sds.core.system.ccm.entity.dao.ENTITYDao;

public class CCMInterfaceKey {
	
	  ////////////////////////////////////////////////////////////////////////
	  /*              CCM Actions Keys                 /
	  *//////////////////////////////////////////////////////////////////////
	
	  public static final String ACTION_VIEW_ENTITY_DEFINITION_MANAGEMENT = "view_entity_definition_management";
	  public static final String ACTION_SAVE_ENTITY_FIELD_DEFINITION= "save_entity_field_definition";
	  public static final String ACTION_UPDATE_ENTITY_FIELD_DEFINITION = "update_entity_field_definition";
      public static final String ACTION_VIEW_ENTITY_TYPE_FIELDS ="view_entity_type_field";
      public static final String ACTION_CREATE_NEW_ENTITY_FIELD="create_new_entity_field";
	  public static final String ACTION_EDIT_ENTITY_FIELDS_DEFINITION="edit_entity_fields_definition";
	
	
      public static final String ACTION_VIEW_ENTITY_MANAGEMENT="view_entity_management";
      public static final String ACTION_SEARCH_ENTITY="search_entity";
      
      public static final String  ACTION_CREATE_NEW_ENTITY="create_new_entity";
      public static final String  ACTION_SAVE_NEW_ENTITY="save_new_entity";
      public static final String ACTION_VIEW_ENTITY_TYPE_LIST ="view_entity_type_list" ;
      public static final String ACTION_VIEW_ENTITY_OPTIONAL_DETAILS="view_entity_optional_details";
      public static final String ACTION_EDIT_ENTITY="edit_entity";
      public static final String ACTION_VIEW_CREATE_NEW_ENTITY="view_create_new_entity";
      public static final String ACTION_UPDATE_ENTITY="update_entity";
      public static final String ACTION_IMPORT_PRODUCTS_FROM_PGW="import_products_from_pgw";
      public static final String  ACTION_IMPORT_PRODUCTS_FOR_COMPLEMANTRYS="import_products_for_complemantrys";
      public static final String  ACTION_VIEW_SERVICE_MANAGEMENT="view_service_management";
      public static final String  ACTION_CREATE_NEW_SERVICE= "create_new_service";
      public static final String   ACTION_SAVE_NEW_SERVICE="save_new_service";
      public static final String   ACTION_DELETE_SERVICE="delete_service";
      public static final String   ACTION_EDIT_SERVICE="edit_service";
      public static final String   ACTION_UPDATE_SERVICE="update_service";
      public static final String    ACTION_VIEW_PROJECT_MANAGEMENT="view_project_management";
      public static final String    ACTION_ADD_PROJECT_SERVICE_SCHEMA="add_project_service_schema";
      public static final String    ACTION_SAVE_PROJECT="save_project";
      public static final String     ACTION_VIEW_SERVICE_ASSIGNED_PROJECT="view_service_assigned_project";
      public static final String     ACTION_VIEW_ENTITY_ASSIGNED_PROJECT="view_entity_assigned_project";
      public static final String      ACTION_SUB_ENTITY_TREE="sub_entity_tree";
      public static final String     ACTION_ADD_SUB_ENTITIES="add_sub_entities";
      public static final String       ACTION_ADD_SUB_ENTITIES_NEW="add_sub_entities_new";
      public static final String       ACTION_EDIT_SUB_ENTITY_TREE="edit_sub_entity_tree";
      public static final String       ACTION_EDIT_PROJECT="edit_project";
      public static final String    ACTION_SEARCH_PROJECT="search_project";
      public static final String    ACTION_UPDATE_PROJECT="update_project";
      
      public static final String    ACTION_ADD_SUB_ENTITIES_LEVEL1="add_sub_entities_level1";
      
      public static final String    ACTION_DELETE_ENTITY_FIELDS_DEFINITION="delete_entity_fields_definintion";
      public static final String    ACTION_CHOOSE_SUB_ENTITY="choose_sub_entity";
      
      public static final String    ACTION_DELETE_SUB_ENTITIES="delete_sub_entities";
      public static final String    ACTION_ADD_ENTITIES_FIRST_LEVEL="add_entities_first_level";
      
      public static final String    ACTION_CHOOSE_ENTITY_FIRST_LEVEL="choose_entity_first_level";
      public static final String   ACTION_DELETE_PROJECT="delete_project";
      
      public static final String   ACTION_VIEW_ADD_AND_WIN_SHOP_ASSIGNMENT="view_add_and_win_shop_assignment";
      
      public static final String   ACTION_ASSIGN_ADD_AND_WIN_TO_SHOP="assign_add_and_win_to_shop";
      
      public static final String   ACTION_SEARCH_ADD_AND_WIN="search_add_and_win";
      public static final String   ACTION_PREVIOUS_ADD_AND_WIN_SEARCH="previous_add_and_win_search";
      public static final String   ACTION_NEXT_ADD_AND_WIN_SEARCH="next_add_and_win_search";
      
      public static final String   ACTION_NEXT_AND_PREVIOUS="next_add_previous";
      

       
      
     

////////////////////////////////////////////////////////////////////////
/*             CONTROL             /
*//////////////////////////////////////////////////////////////////////
      
      
      public static final String CONTROL_HIDDEN_FLAGN="control_hidden_flag";
      public static final String CONTROL_HIDDEN_ACTION="control_hidden_action";
      
      // entity type
	public static final String CONTROL_HIDDEN_ENTITY_TYPE_ID = "entity_type_id";
	public static final String CONTROL_HIDDEN_ENTITY_TYPE_NAME = "entity_type_name";
	public static final String CONTROL_Select_ENTITY_TYPE_ID = "entity_select_type";
	
	public static final String CONTROL_SERVICE_ID = "control_service_id";
	public static final String CONTROL_SERVICE_NAME = "control_service_name";
	public static final String CONTROL_SELECT_SERVICE_ID = "control_select_service";
	
	public static final String CONTROL_SCHEMA_ID = "control_schema_id";
	public static final String CONTROL_SCHEMA_NAME = "control_schema_name";
	public static final String CONTROL_SELECT_SCHEMA_ID = "control_select_schema";
	public static final String CONTROL_HIDDEN_PROJECT_ID = "control_hidden_project_id";
	public static final String CONTROL_HIDDEN_PROJECT_NAME = "control_hidden_project_name";
	
	
	
	
	public static final String CONTROL_SELECT_ENTITY_FIELD_TYPE_ID = "entity_select_entity_field_type_id";
	public static final String CONTROL_HIDDEN_ENTITY_FIELD_TYPE_ID = "entity_hidden_entity_field_type_id";
	public static final String CONTROL_HIDDEN_ENTITY_FIELD_TYPE_NAME = "entity_hiddent_entity_field_type_name";
	public static final String CONTROL_HIDDEN_ENTITY_ID = "hidden_entity_id";
	
	
	public static final String  CONTROL_HIDDEN_RECORD_ENTITY_TYPE_ID="control_hidden_record_entity_type_id";
	
	
	public static final String    CONTROL_HIDDEN_ENTITY_FIELD_ID ="control_hidden_entity_field_id";
	
	//public static final String    CONTROL_HIDDEN_ENTITY_ID="control_hidden_entity_id";
	
	public static final String    CONTROL_INPUT_TEXT_AND_FIELD_ID ="control_input_texy_and_field_id";
	public static final String    CONTROL_INPUT_DATE_AND_FIELD_ID ="control_input_date_and_field_id";
	public static final String    CONTROL_INPUT_NUMBER_AND_FIELD_ID ="control_input_number_and_field_id";
	public static final String    CONTROL_INPUT_LIST_AND_FIELD_ID ="control_input_list_and_field_id";
	public static final String    CONTROL_INPUT_CHECKBOX_AND_FIELD_ID ="control_input_checkbox_and_field_id";
	public static final String    CONTROL_HIDDEN_SERVICE_ID ="control_hidden_service_id";
	public static final String    CONTROL_ENTITY_NAME="control_entity_name";
	public static final String     CONTROL_TEXT_PROJECT_START_DATE="control_text_project_start_date";
	public static final String     CONTROL_HIDDEN_SHOP_ID="control_hidden_shop_id";
	
	public static final String    INPUT_HIDDEN_ROW_NUM="input_hidden_row_num";
	public static final String    INPUT_HIDDEN_COUNT="input_hidden_count";
	
	
	



	
	public static final String  INPUT_TEXT_ENTITY_FIELD_LABEL_NAME = "input_text_entity_field_label_name";
	public static final String  INPUT_TEXT_ENTITY_FIELD_ORDER = "input_text_entity_field_order";
	public static final String  INPUT_IS_MANADATORY ="IS_Mandatory";

	 public static final String INPUT_SEARCH_TEXT_ENTITY_NAME = "search_text_entity_name";
	 public static final String INPUT_SEARCH_TEXT_ENTITY_CODE = "search_text_entity_code";
	                         
	 
	 
	 
	 public static final String  INPUT_TEXT_ENTITY_NAME="input_text_entity_name";
	 
	 public static final String  INPUT_TEXT_ENTITY_CODE="input_text_entity_code";
	 
	 public static final String  INPUT_TEXT_ENTITY_ADDRESS="input_text_entity_address";
	 
	 public static final String INPUT_TEXT_SERVICE_NAME="input_text_service_name";
	 public static final String INPUT_TEXT_SERVICE_DESCRIPTION="input_text_service_description";
	 public static final String  INPUT_TEXT_PROJECT_NAME="input_text_project_name";
	 public static final String  INPUT_TEXT_PROJECT_DESCRIPTION="input_text_project_description";
	 public static final String   INPUT_TEXT_PROJECT_ADDRESS="input_text_project_address";
	
	 public static final String  INPUT_SEARCH_TEXT_ENTITY_PARENT="input_search_text_entity_parent";
	 public static final String   INPUT_SEARCH_TEXT_ENTITY_PARENT22="input_search_text_entity_parent22";
	 public static final String   INPUT_SEARCH_TEXT_ENTITY_NAME22="input_search_text_entity_name22";
	 public static final String   INPUT_SEARCH_TEXT_PROJECT_NAME="input_search_text_project_name";
	 public static final String   INPUT_SEARCH_TEXT_PROJECT_ADDRESS="input_search_text_project_address";
	 public static final String   INPUT_SEARCH_TEXT_SCHEMA="input_search_text_schema";
	 public static final String   INPUT_HIDDEN_PARENT_ID="hiddent_input_parent_id";
	 public static final String   INPUT_ADD_AND_WIN_ID="input_add_and_win_id";
	 
	 public static final String   INPUT_SEARCH_TEXT_ADDANDWIN_CODE="input_search_text_addandwin_code";
	 public static final String   INPUT_SEARCH_TEXT_ADDANDWIN_NAME="input_search_text_addandwin_name";
	 
	 

	 

	
  ////////////////////////////////////////////////////////////////////////
	  /*             VECTOR              /
	  *//////////////////////////////////////////////////////////////////////
	  public static final String VECTOR_ENTITY_TYPES  = "vector_entity_types";
	  
	  public static final String VECTOR_ENTITY_FIELD_TYPES  = "vector_entity_field_types";
	  
	  
	  public static final String VECTOR_ENTITY_TYPE_FIELDS  = "vector_entity_type_fields";
	  
	  public static final String  VECTOR_ENTITY_ADDITIONAL_FIELD_LABEL="vector_entity_additional_field_label";
	  
	  public static final String  VECTOR_ENTITY_MANDATORY_DATA="vector_entity_mandatory_data";
	  public static final String  VECTOR_ENTITY_ADDITIONAL_DATA="vector_entity_additional_data";
	  
	  public static final String    VECTOR_ENTITY_OPTIONAL_FIELD_LABEL ="vector_entity_optional_field_label";
	  public static final String    VECTOR_SERVICE="vector_service";
	  public static final String    VECTOR_SCHEMA_LIST="vector_schema_list";
	  public static final String    VECTOR_SERVICE_LIST="vector_service_list";
	  public static final String     VECTOR_PROJECT_LIST="vector_project_list";
	  public static final String     VECTOR_SERVICE_ASSIGNED_PROJECT="vector_service_assigned_project";
	  public static final String     VECTOR_ENTITY_ASSIGNED_PROJECT="vector_entity_assigned_project";
	  public static final String     VECTOR_PROJECT="vector_project";
	  
	  public static final String     VECTOR_ENTITY_ALL_PROJECT="vector_entity_all_project";
	  
	  public static final String     VECTOR_ADD_AND_WIN="vector_add_and_win";
	  
	  public static final String     VECTOR_SHOPS="vector_shops";
	  
	  public static final String     MESSAGE_QUEUE_MESSAGE="message_queue_message";

	  
	
	  
	 
	  
	  
	
	  
	  
}

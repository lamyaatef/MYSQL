package com.mobinil.sds.web.interfaces.dcm;

public interface DCMInterfaceKey {

    public static final String ACTION_DCM_SA_POS_LIST = "dcm_sa_pos_list";
    public static final String ACTION_DCM_SA_CREATE_NEW_POS = "dcm_sa_create_new_pos";
    public static final String ACTION_DCM_USER_CREATE_NEW_POS = "dcm_user_create_new_pos";
    public static final String ACTION_DCM_SA_EDIT_POS = "dcm_sa_edit_pos";
    public static final String ACTION_DCM_USER_POS_LIST = "dcm_user_pos_list";
    public static final String ACTION_DCM_USER_VIEW_POS_DETAILS = "dcm_user_view_pos_details";
    public static final String ACTION_DCM_USER_VIEW_POS_RELATIONSHIPS = "dcm_user_view_pos_relationships";
    public static final String ACTION_DCM_POS_GROUPS = "dcm_pos_groups";
    public static final String ACTION_DCM_UPLOAD_POS_FOR_GROUP = "dcm_upload_pos_for_group";
    public static final String ACTION_DCM_UPLOAD_POS_FOR_GROUP_PROCESS = "dcm_upload_pos_for_group_process";
    public static final String ACTION_DCM_EDIT_POS_GROUP = "dcm_edit_pos_group";
    public static final String ACTION_DCM_CREATE_NEW_POS_GROUP = "dcm_create_new_pos_group";
    public static final String ACTION_DCM_ASSIGN_POS_TO_POS_GROUP = "dcm_assign_pos_to_pos_group";
    public static final String ACTION_DCM_ASSIGN_FUNCTION_TO_POS_GROUP = "dcm_assign_function_to_pos_group";
    public static final String ACTION_DCM_POS_FUNCTIONS = "dcm_pos_functions";
    public static final String ACTION_DCM_EDIT_POS_FUNCTION = "dcm_edit_pos_function";
    public static final String ACTION_DCM_CREATE_NEW_POS_FUNCTION = "dcm_create_new_pos_function";
    public static final String ACTION_DCM_POS_BRANCHES = "dcm_pos_branches";
    public static final String ACTION_DCM_EDIT_USER_ACCOUNT = "dcm_edit_user_account";
    public static final String ACTION_DCM_ADMIN_EDIT_SALES_AGENT = "dcm_admin_edit_sales_agent";
    public static final String ACTION_DCM_ADMIN_CREATE_NEW_SALES_AGENT = "dcm_admin_create_new_sales_agent";
    public static final String ACTION_DCM_ASSIGN_TARGET_TO_GROUP = "dcm_assign_target_to_group";
    public static final String ACTION_DCM_MANAGERS = "dcm_managers";
    public static final String ACTION_DCM_ASSIGN_SALES_AGENT_TO_MANAGER = "dcm_assign_sales_agent_to_manager";
    public static final String ACTION_DCM_SAVE_ASSIGN_SALES_AGENT_TO_MANAGER = "dcm_save_assign_sales_agent_to_manager";
    public static final String ACTION_DCM_CREATE_NEW_MANAGER = "dcm_create_new_manager";
    public static final String ACTION_DCM_MANAGER_SALES_AGENTS = "dcm_manager_sales_agents";
    public static final String ACTION_DCM_MANAGER_VIEW_SALES_AGENT_TARGET = "dcm_manager_view_sales_agent_target";
    public static final String ACTION_DCM_MANAGER_EDIT_SALES_AGENT_TARGET = "dcm_manager_edit_sales_agent_target";
    public static final String ACTION_DCM_SALES_AGENT_VISITS = "dcm_sales_agent_visits";
    public static final String ACTION_DCM_EDIT_SALES_AGENT_VISIT = "dcm_edit_sales_agent_visit";
    public static final String ACTION_DCM_CREATE_NEW_SALES_AGENT_VISIT = "dcm_create_new_sales_agent_visit";
    public static final String ACTION_DCM_ADMIN_SALES_AGENTS_ASSIGN_FUNCTION = "dcm_admin_sales_agents_assign_function";
    public static final String ACTION_DCM_ADMIN_SALES_AGENTS_ASSIGN_GROUP = "dcm_admin_sales_agents_assign_group";
    public static final String ACTION_DCM_ASSIGN_FUNCTION_TO_SALES_AGENT = "dcm_assign_function_to_sales_agent";
    public static final String ACTION_DCM_SALES_AGENT_VISITS_FOR_AGENTS = "dcm_sales_agent_visits_for_agents";
    public static final String ACTION_DCM_VIEW_SALES_AGENT_VISIT_DETAILS = "dcm_view_sales_agent_visit_details";
    public static final String ACTION_DCM_SEARCH_SALES_AGENT_ACTUAL_VISITS_FOR_AGENTS = "dcm_search_sales_agent_actual_visits_for_agents";
    public static final String ACTION_DCM_SALES_AGENT_ACTUAL_VISITS_FOR_AGENTS = "dcm_sales_agent_actual_visits_for_agents";
    public static final String ACTION_DCM_VIEW_SALES_AGENT_ACTUAL_VISIT_DETAILS = "dcm_view_sales_agent_actual_visit_details";
    public static final String ACTION_DCM_CREATE_NEW_SALES_AGENT_ACTUAL_VISIT = "dcm_create_new_sales_agent_actual_visit";
    public static final String ACTION_DCM_SAVE_SALES_AGENT_ACTUAL_VISIT = "dcm_save_sales_agent_actual_visit";
    public static final String ACTION_DCM_SALES_AGENT_BLUE_COPY_ENTRY_FORM = "dcm_sales_agent_blue_copy_entry_form";
    public static final String ACTION_DCM_SAVE_SALES_AGENT_BLUE_COPY_ENTRY_FORM = "dcm_save_sales_agent_blue_copy_entry_form";
    public static final String ACTION_DCM_MANAGER_BLUE_COPIES_COLLECTED = "dcm_manager_blue_copies_collected";
    public static final String ACTION_DCM_MANAGER_EDIT_BLUE_COPIES_COLLECTED = "dcm_manager_edit_blue_copies_collected";
    public static final String ACTION_DCM_SALES_AGENT_CREATE_SERVICE_REQUEST = "dcm_sales_agent_create_service_request";
    public static final String ACTION_DCM_MANAGER_EDIT_SERVICE_REQUEST = "dcm_manager_edit_service_request";
    public static final String ACTION_DCM_MANAGER_SERVICE_REQUEST_LIST = "dcm_manager_service_request_list";
    public static final String ACTION_DCM_SEARCH_MANAGER_SERVICE_REQUEST_LIST = "dcm_search_manager_service_request_list";
    public static final String ACTION_DCM_MANAGER_SERVICES_LIST = "dcm_manager_services_list";
    public static final String ACTION_DCM_SEARCH_MANAGER_SERVICES_LIST = "dcm_search_manager_services_list";
    public static final String ACTION_DCM_UPDATE_SERVICES_STATUS = "dcm_update_services_status";
    public static final String ACTION_DCM_REGIONAL_MANAGEMENT_TREE = "dcm_regional_management_tree";
    public static final String ACTION_CHAIN_MANAGEMENT = "chain_management";
    public static final String ACTION_SEARCH_GEN_DCM = "search_gen_dcm";
    public static final String ACTION_NEXT_GEN_DCM = "next_gen_dcm";
    public static final String ACTION_PREVIOUS_GEN_DCM = "previous_gen_dcm";
    public static final String ACTION_EDIT_GEN_DCM = "edit_gen_dcm";
    public static final String ACTION_EXPORT_GEN_DCM_TO_EXCEL = "export_gen_dcm_to_excel";
    public static final String ACTION_CREATE_NEW_GEN_DCM = "create_new_gen_dcm";
    public static final String ACTION_SAVE_GEN_DCM = "save_gen_dcm";
    public static final String ACTION_UPDATE_GEN_DCM = "update_gen_dcm";
    public static final String ACTION_DCM_EDIT_REGION = "dcm_edit_region";
    public static final String ACTION_INSERT_NEW_REGION = "insert_new_region";
    public static final String ACTION_DELETE_REGION = "delete_region";
    public static final String ACTION_MOVE_REGION_FROM_TO = "move_region_from_to";
    public static final String ACTION_EDIT_REGION = "edit_region";
    public static final String ACTION_UPDATE_REGION_NAME = "update_region_name";
    public static final String ACTION_DCM_CHANGED_SALES_AGENTS_DATA_MANAGEMENT = "dcm_changed_sales_agents_data_management";
    public static final String ACTION_DCM_ASSIGN_TARGET_TO_POS = "dcm_assign_target_to_pos";
    public static final String ACTION_DCM_CREATE_NEW_SERVICE = "dcm_create_new_service";
    public static final String ACTION_DCM_EDIT_SERVICE = "dcm_edit_service";
    public static final String ACTION_DCM_SAVE_SERVICE = "dcm_save_service";
    public static final String ACTION_DCM_ASSIGN_POS_TO_SERVICE = "dcm_assign_pos_to_service";
    public static final String ACTION_DCM_SAVE_ASSIGN_POS_TO_SERVICE = "dcm_save_assign_pos_to_service";
    public static final String ACTION_DCM_UPLOAD_SERVICE_ELIGIBILITY_LIST = "dcm_upload_service_eligibility_list";
    public static final String ACTION_DCM_UPLOAD_SERVICE_ELIGIBILITY_LIST_PROCESS = "dcm_upload_service_eligibility_list_process";
    public static final String ACTION_DCM_ADMIN_VIEW_POS_CHANGES_DETAILS = "dcm_admin_view_pos_changes_details";
    public static final String ACTION_DCM_UPLOAD_ASSIGN_SALES_AGENT_TO_MANAGER = "dcm_upload_assign_sales_agent_to_manager";
    public static final String ACTION_DCM_SALES_AGENT_BLUE_COPIES_COLLECTED = "dcm_sales_agent_blue_copies_collected";
    public static final String ACTION_DCM_MOVE_REGIONS_SELECT_SOURCES = "dcm_move_regions_select_sources";
    public static final String ACTION_DCM_MOVE_REGIONS_SELECT_DESTINATION = "dcm_move_regions_select_destination";
    public static final String ACTION_DCM_UPDATE_DCM_USER = "dcm_update_dcm_user";
    public static final String ACTION_DCM_ADD_NEW_REGIONAL_MANAGER = "dcm_add_new_regional_manager";
    public static final String ACTION_DCM_ADD_NEW_MANAGER = "dcm_add_new_manager";
    public static final String ACTION_DCM_ADD_NEW_SALES_AGENT = "dcm_add_new_sales_agent";
    public static final String ACTION_DCM_ADD_NEW_DCM_USER = "dcm_add_new_dcm_user";
    public static final String ACTION_DCM_UPDATE_DCM_USER_STATUS_TYPE = "dcm_update_dcm_user_status_type";
    public static final String ACTION_SEARCH_DCM_MANAGERS = "search_dcm_managers";
    public static final String ACTION_DCM_CHANGE_USER_DETAIL_STATUS = "dcm_change_user_detail_status";
    public static final String ACTION_DCM_SEARCH_ADMIN_SALES_AGENTS_ASSIGN_FUNCTION = "dcm_search_admin_sales_agents_assign_function";
    public static final String ACTION_DCM_SAVE_ASSIGN_FUNCTION_TO_SALES_AGENT = "dcm_save_assign_function_to_sales_agent";
    public static final String ACTION_DCM_SAVE_ASSIGN_TARGET_TO_SALES_AGENT = "dcm_save_assign_target_to_sales_agent";
    public static final String ACTION_DCM_SEARCH_ADMIN_SALES_AGENTS_ASSIGN_GROUP = "dcm_search_admin_sales_agents_assign_group";
    public static final String ACTION_DCM_SEARCH_MANAGER_SALES_AGENTS = "dcm_search_manager_sales_agents";
    public static final String ACTION_DCM_SAVE_VISIT_PLAN_DETAILS = "dcm_save_visit_plan_details";
    public static final String ACTION_DCM_UPDATE_SALES_AGENT_VISITS_STATUS = "dcm_update_sales_agent_visits_status";
    public static final String ACTION_DCM_SEARCH_SALES_AGENT_VISITS = "dcm_search_sales_agent_visits";
    public static final String ACTION_DCM_EDIT_SALES_AGENT_VISIT_FOR_AGENT = "dcm_edit_sales_agent_visit_for_agent";
    public static final String ACTION_DCM_CREATE_NEW_SALES_AGENT_VISIT_FOR_AGENT = "dcm_create_new_sales_agent_visit_for_agent";
    public static final String ACTION_DCM_SAVE_VISIT_PLAN_DETAILS_FOR_AGENT = "dcm_save_visit_plan_details_for_agent";
    public static final String ACTION_DCM_SEARCH_SALES_AGENT_VISITS_FOR_AGENT = "dcm_search_sales_agent_visits_for_agent";
    public static final String ACTION_DCM_CREATE_NEW_CASE_FROM_ACTUAL_VISIT = "dcm_create_new_case_from_actual_visit";
    public static final String ACTION_DCM_CREATE_NEW_CASE_FROM_ACTUAL_VISIT_FOR_SERVICE_REQUEST = "dcm_create_new_case_from_actual_visit_for_service_request";
    public static final String ACTION_DCM_SUPER_ADMIN_POS_LIST = "dcm_super_admin_pos_list";
    public static final String ACTION_DCM_POS_SAVE_DETAIL = "dcm_pos_save_detail";
    public static final String ACTION_VIEW_REGION_MANAGEMENT = "view_region_management";

///list of constants
    public static final String CONST_REGIONAL_MANAGER_ROLE_ID = "68";
    public static final String CONST_MANAGER_ROLE_ID = "69";
    public static final String CONST_SALES_AGENT_ROLE_ID = "70";
    public static final String CONST_DCM_USER_LEVEL_TYPE_REGIONAL_MANAGER_ID = "1";
    public static final String CONST_DCM_USER_LEVEL_TYPE_MANAGER_ID = "2";
    public static final String CONST_DCM_USER_LEVEL_TYPE_SALES_AGENT_ID = "3";
    public static final String CONST_DCM_USER_DETIAL_STATUS_ACTIVE_ID = "1";
    public static final String CONST_DCM_USER_DETIAL_STATUS_INACTIVE_ID = "2";
    public static final String CONST_DCM_USER_DETIAL_STATUS_REJECTED_ID = "3";
    public static final String CONST_DCM_USER_DETIAL_STATUS_PENDING_ID = "4";
    public static final String CONST_DCM_PREDEFINED_FUNCTION_CREATE_NEW_CASE = "5";
    public static final String CONST_DCM_PREDEFINED_FUNCTION_SALES_FORM = "6";
    public static final String CONST_DCM_PREDEFINED_FUNCTION_SERVICE_REQUEST = "7";
    //POS Keys
    public static final String CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG = "hidden_pos_super_admin_flag";
    public static final String CONTROL_TEXT_POS_NAME = "pos_name";
    public static final String CONTROL_TEXT_POS_EMAIL = "pos_email";
    public static final String CONTROL_TEXT_POS_ADDRESS = "pos_address";
    public static final String CONTROL_TEXT_POS_REGION = "pos_region";
    public static final String CONTROL_TEXT_POS_COMMERCIAL_NUMBER = "pos_commercial_number";
    public static final String CONTROL_TEXT_POS_TAX_ID = "pos_tax_id";
    public static final String CONTROL_TEXT_POS_LEGAL_FORM = "pos_legal_form";
    public static final String CONTROL_TEXT_POS_PLACE_TYPE = "pos_place_type";
    public static final String CONTROL_TEXT_POS_OWNER_NAME = "pos_owner_name";
    public static final String CONTROL_TEXT_POS_OWNER_BIRTH_DATE = "name1";
    public static final String CONTROL_TEXT_POS_OWNER_ID_TYPE = "pos_owner_id_type";
    public static final String CONTROL_TEXT_POS_OWNER_ID_NUMBER = "pos_onwer_id_number";
    public static final String CONTROL_TEXT_POS_MANAGER_NAME = "pos_manager_name";
    public static final String CONTROL_TEXT_POS_MANAGER_BIRTH_DATE = "name2";
    public static final String CONTROL_TEXT_POS_MANAGER_ID_TYPE = "pos_manager_id_type";
    public static final String CONTROL_TEXT_POS_MANAGER_ID_NUMBER = "pos_manager_id_number";
    public static final String ACTION_SAVE_NEW_POS = "dcm_sa_save_new_pos";
    public static final String ACTION_USER_SAVE_NEW_POS = "dcm_user_save_new_pos";
    public static final String CONTROL_REGION_MANAG_TEXT = "selectedNode";
    public static final String CONTROL_REGION_MANAG_TEXT_ID = "selectedNodeId";
    public static final String CONTROL_REGION_MANAG_TEXT_REGION_TO = "selectedToRegion";
    public static final String CONTROL_REGION_MANAG_TEXT_REGION_TO_ID = "selectedToRegionId";
    public static final String CONTROL_REGION_MANAG_TEXT_NAME = "selectedNodeName";
    public static final String CONTROL_TEXT_POS_CITY = "pos_city";
    public static final String CONTROL_TEXT_POS_CODE = "pos_code";
    public static final String ACTION_SEARCH_POS = "search_pos";
    public static final String ACTION_USER_SEARCH_POS = "search_user_pos";
    public static final String CONTROL_TEXT_POS_STATUS = "pos_status";
    public static final String CONTROL_TEXT_POS_PHONE = "pos_phone";
    public static final String VECTOR_POS_RESULT = "vector_pos_result";
    public static final String VECTOR_PLACE_TYPE = "vector_place_type";
    public static final String VECTOR_REGIONS = "vector_regions";
    public static final String VECTOR_ID_TYPE = "vector_id_type";
    public static final String VECTOR_LEGAL_FORM = "vector_legal_form";
    public static final String VECTOR_POS_CITY = "vector_pos_city";
    public static final String VECTOR_POS_STATUS = "vector_pos_status";
    public static final String VECTOR_COLUMN_LIST = "vector_column_list";
    public static final String COLUMN_COUNT = "column_count";
    public static final String ROW_COUNT = "row_count";
    public static final String ACTION_LIST_TABLE_FIELDS = "dcm_generic_model";
    public static final String ACTION_GENERIC_MODEL = "generic_model";
    public static final String VECTOR_COLUMN_NAMES = "vectot_column_name";
    public static final String VECTOR_TABLE_MODELS = "vector_table_models";
    public static final String GENERIC_TABLE_NAME = "generic_table_name";
    public static final String ADD_GENERIC_RECORD = "add_generic_record";
    public static final String EDIT_GENERIC_RECORD = "edit_generic_record";
    public static final String POS_SEARCH_STATUS = "pos_search_status";
    public static final String POS_SEARCH_CITY = "pos_search_city";
    public static final String INPUT_POS_NAME = "input_pos_name";
    public static final String INPUT_POS_COMMERCIAL_NUMBER = "input_pos_commercial_number";
    public static final String INPUT_POS_STATUS = "input_pos_status";
    public static final String INPUT_POS_CITY = "input_pos_city";
    public static final String INPUT_POS_CODE = "inpu_pos_code";
    public static final String INPUT_POS_PHONE = "input_pos_phone";
    public static final String INPUT_HIDDEN_POS_ID = "hidden_pos_id";
    public static final String INPUT_HIDDEN_ROW_NUM = "hidden_row_num";
    public static final String INPUT_HIDDEN_COUNT = "hidden_count";
    public static final String INPUT_HIDDEN_CHAIN_CODE = "hidden_chain_code";
    public static final String INPUT_HIDDEN_CHAIN_LEVEL = "hidden_chain_level";
    public static final String INPUT_HIDDEN_CHAIN_PAYMENT_LEVEL = "hidden_chain_payment_level";
    public static final String INPUT_HIDDEN_CHAIN_DISTRICT = "hidden_chain_distict";
    public static final String INPUT_HIDDEN_STK_NUMBER = "hidden_stk_number";
    public static final String INPUT_HIDDEN_CHAIN_CHANNEL = "hidden_chain_channel";
    public static final String INPUT_HIDDEN_CHAIN_CITY = "hidden_chain_city";
    public static final String INPUT_HIDDEN_CHAIN_STATUS = "hidden_chain_status";
    public static final String POS_DETAIL_MODEL = "pos_detail_model";
    public static final String POS_PHONES_VECTOR = "pos_phones_vector";
    public static final String POS_PHONES_COUNT = "pos_phones_count";
    public static final String ACTION_DCM_UPDATE_POS_STATUS = "dcm_update_pos_status";
    public static final String ACTION_DCM_USER_UPDATE_POS_STATUS = "dcm_update_user_pos_status";
    public static final String INPUT_DCM_POS_LIST_STATUS = "dcm_pos_list_status";
    public static final String INPUT_HIDDEN_DCM_POS_CURRENT_STATUS = "dcm_pos_current_status";
    public static final String DCM_SAVE_POS_TYPE = "dcm_save_pos_type";
    public static final String DCM_SAVE_POS_TYPE_NEW = "dcm_save_pos_type_new";
    public static final String DCM_SAVE_POS_TYPE_EDIT = "dcm_save_pos_type_edit";
    public static final String DCM_USER_SAVE_POS_TYPE_NEW = "dcm_user_save_pos_type_new";
    public static final String ACTION_DCM_SAVE_EDITED_POS = "dcm_save_edited_pos";
    public static final String DCM_ACCEPTED_POS = "accepted_pos";
    public static final String DCM_PENDING_POS = "pending_pos";
    public static final String DCM_CHANGES_POS = "changes_pos";
    public static final String DCM_POS_WARNING_VECTOR = "pos_warning_vector";
    public static final String CONTROL_TEXT_POS_WARNING = "pos_warning";
    //Chains Keys
    public static final String INPUT_HIDDEN_DCM_ID = "hidden_dcm_id";
    public static final String INPUT_SEARCH_SELECT_CHAIN_STATUS = "search_select_chain_status";
    public static final String INPUT_SEARCH_SELECT_CHAIN_LEVEL = "search_select_chain_level";
    public static final String INPUT_SEARCH_SELECT_CHAIN_CHANNEL = "search_select_chain_channel";
    public static final String INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL = "search_select_chain_payment_level";
    public static final String INPUT_SEARCH_SELECT_CHAIN_CITY = "search_select_chain_city";
    public static final String INPUT_SEARCH_SELECT_CHAIN_DISTRICT = "search_select_chain_district";
    public static final String INPUT_SEARCH_TEXT_DCM_CODE = "search_text_dcm_code";
    public static final String INPUT_SEARCH_TEXT_DCM_NAME = "search_text_dcm_name";
    public static final String INPUT_SEARCH_TEXT_DCM_PHONE = "search_text_dcm_phone";
    public static final String INPUT_SEARCH_TEXT_DCM_EMAIL = "search_text_dcm_email";
    public static final String INPUT_SEARCH_TEXT_DCM_ADDRESS = "search_text_dcm_address";
    public static final String INPUT_SEARCH_TEXT_DCM_RANK = "search_text_dcm_rank";
    public static final String INPUT_SEARCH_TEXT_STK_NUMBER = "search_text_stk_number";
    public static final String CONTROL_HIDDEN_TOTAL_PAGES = "page_total";
    public static final String CONTROL_HIDDEN_PAGE_NUMBER = "page_number";
    public static final String INPUT_HIDDEN_PAGE_START_INDEX = "hidden_page_start_index";
    public static final String INPUT_HIDDEN_PAGE_END_INDEX = "hidden_page_end_index";
    //Sales Agent Keys
    public static final String INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID = "hidden_dcm_user_level_type_id";
    public static final String INPUT_SELECT_REGION_ID = "select_region_id";
    public static final String INPUT_SELECT_REGION_NAME = "select_region_name";
    public static final String INPUT_HIDDEN_REGION_ID = "hidden_region_id";
    public static final String INPUT_HIDDEN_REGION_NAME = "hidden_region_name";
    public static final String INPUT_HIDDEN_REGION_LEVEL_TYPE_ID = "hidden_region_level_type_id";
    public static final String INPUT_SEARCH_SELECT_REGION_NAME_FROM = "search_select_region_name_from";
    public static final String INPUT_SEARCH_SELECT_REGION_NAME_TO = "search_select_region_name_to";
    public static final String INPUT_SEARCH_SELECT_REGION_NAME = "search_select_region_name";
    public static final String INPUT_SEARCH_SELECT_LEVEL_NAME = "search_select_level_name";
    public static final String INPUT_SEARCH_SELECT_DELETE_REGION = "search_select_delete_region";
    public static final String INPUT_SEARCH_SELECT_EDIT_REGION = "search_select_edit_region";
    public static final String INPUT_TEXT_REGION_NAME = "region_name";
    public static final String INPUT_SEARCH_SELECT_REGION_LEVEL_NAME = "search_select_region_level_name";
    public static final String INPUT_TEXT_REGION_ID = "region_id";
    public static final String INPUT_TEXT_DCM_USER_ADDRESS = "text_dcm_user_address";
    public static final String INPUT_TEXT_DCM_USER_MOBILE = "text_dcm_user_mobile";
    public static final String INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS = "serach_select_dcm_user_list_status";
    public static final String INPUT_SEARCH_TEXT_DCM_USER_NAME = "serach_text_dcm_user_name";
    public static final String INPUT_SEARCH_SELECT_DCM_USER_TYPE = "serach_select_dcm_user_TYPE";
    public static final String INPUT_SELECT_DCM_USER_LIST_STATUS = "select_dcm_user_list";
    public static final String INPUT_HIDDEN_DCM_USER_LIST_STATUS = "hidden_dcm_user_list";
    public static final String INPUT_HIDDEN_DCM_USER_ID = "hidden_dcm_user_id";
    public static final String INPUT_HIDDEN_DCM_USER_DETAIL_STATUS_ID = "hidden_dcm_user_detail_status_id";
    public static final String INPUT_HIDDEN_DCM_USER_DETAIL_ID = "hidden_dcm_user_detail_id";
    public static final String INPUT_HIDDEN_DCM_USER_NAME = "hidden_dcm_user_name";
    public static final String INPUT_HIDDEN_DCM_USER_EMAIL = "hidden_dcm_user_email";
    public static final String INPUT_HIDDEN_DCM_USER_ADDRESS = "hidden_dcm_user_address";
    public static final String DCM_HASHMAP_KEY_COLLECTION = "dcm_hashmap_key_collection";
    public static final String DCM_HASHMAP_KEY_ADDITIONAL_COLLECTION = "dcm_hashmap_key_additional_collection";
    public static final String INPUT_SEARCH_TEXT_POS_NAME = "serach_text_pos_name";
    public static final String INPUT_SEARCH_TEXT_POS_CODE = "serach_text_pos_code";
    public static final String INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM = "serach_text_visit_creation_date_from";
    public static final String INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO = "serach_text_visit_creation_date_to";
    //visit plan keys
    public static final String INPUT_TEXT_VISIT_PLAN_NAME = "text_visit_plan_name";
    public static final String INPUT_SELECT_FUNCTION_ID = "select_function_id";
    public static final String INPUT_TEXTAREA_VISIT_PLAN_COMMENTS = "textarea_visit_plan_comments";
    public static final String INPUT_TEXT_VISIT_DATE = "text_visit_date";
    public static final String INPUT_SELECT_VISIT_STATUS_ID = "select_visit_status_id";
    public static final String INPUT_HIDDEN_VISIT_STATUS_ID = "hidden_visit_status_id";
    public static final String INPUT_HIDDEN_VISIT_PLAN_ID = "hidden_visit_plan_id";
    public static final String INPUT_SEARCH_SELECT_FUNCTION_ID = "search_select_function_id";
    public static final String INPUT_SEARCH_SELECT_VISIT_STATUS_ID = "search_select_visit_status_id";
    //actual visit keys
    public static final String INPUT_HIDDEN_ACTUAL_VISIT_ID = "hidden_actual_visit_id";
    //Generic Model Keys
    public static final String ACTION_FIND_GENERIC_TABLE = "find_generic_table";
    public static final String CONTROL_TEXT_GENERIC_TABLE_NAME = "generic_table_name";
    public static final String ACTION_SELECT_GENERIC_TABLE = "select_generic_table";
    public static final String DCM_GENERIC_TABLE_COLUMN_COUNT = "generic_table_column_count";
    public static final String DCM_GENERIC_TABLE_ROW_COUNT = "generic_table_row_count";
    public static final String DCM_GENERIC_MODEL_DATA_VECTOR = "generic_model_data_vector";
    public static final String INPUT_HIDDEN_SELECTED_GENERIC_ITEM_ID = "selected_generic_item_id";
    public static final String ACTION_EDIT_GENERIC_ITEM = "edit_generic_item";
    public static final String ACTION_ADD_NEW_GENERIC_ITEM = "add_new_generic_item";
    public static final String ACTION_SAVE_NEW_GENERIC_ITEM = "save_new_generic_item";
    public static final String DCM_GENERIC_MODEL_EDIT_VALUES = "generic_model_values";
    public static final String CONTROL_TEXT_DCM_GENERIC_MODEL_FIELD = "generic_field";
    public static final String INPUT_HIDDEN_DCM_GENERIC_MODEL_PK_NAME = "generic_pk_value";
    public static final String INPUT_HIDDEN_DCM_GENERIC_MODEL_PK_VALUE = "generic_pk_name";
    public static final String INPUT_HIDDEN_DCM_GENERIC_MODEL_TABLE_NAME = "generic_table_name";
    //POS Branches Keys
    public static final String DCM_POS_BRANCH_STATUS_VECTOR = "branch_status_vector";
    public static final String CONTROL_TEXT_DCM_BRANCH_STATUS = "branch_status";
    public static final String CONTROL_TEXT_DCM_BRANCH_NAME = "branch_name";
    public static final String CONTROL_TEXT_DCM_BRANCH_CODE = "branch_code";
    public static final String CONTROL_TEXT_DCM_BRANCH_START_DATE_FROM = "branch_start_date_from";
    public static final String CONTROL_TEXT_DCM_BRANCH_START_DATE_TO = "branch_start_date_to";
    public static final String CONTROL_TEXT_DCM_BRANCH_END_DATE_FROM = "branch_end_date_from";
    public static final String CONTROL_TEXT_DCM_BRANCH_END_DATE_TO = "branch_end_date_to";
    public static final String CONTROL_TEXT_DCM_MAIN_BRANCH_NAME = "main_branch_name";
    public static final String ACTION_DCM_SEARCH_BRACHES = "search_branches";
    public static final String DCM_POS_BRANCHES_SEARCH_RESULT = "branches_search_result";
    public static final String DCM_POS_SEARCH_BRANCH_STATUS = "search_branch_status";
    public static final String DCM_SEARCH_FLAG = "search_flag";
    public static final String ACTION_DCM_SAVE_NEW_BRANCH = "save_new_branch";
    public static final String DCM_BRANCHES_POS_VECTOR = "branches_pos_vector";
    public static final String DCM_BRANCHES_HIDDEN_ID = "branches_hidden_id";
    public static final String ACTION_DCM_BRANCH_UPDATE_STATUS = "update_branch_status";
    public static final String INPUT_HIDDEN_BRANCH_STATUS = "hidden_branch_status";
    public static final String INPUT_HIDDEN_BRANCH_ID = "hidden_branch_id";
//FUNCTION KEYS
    public static final String DCM_FUNCTION_SEARCH_VECTOR = "function_search_result_vector";
    public static final String INPUT_SEARCH_TEXT_DCM_FUNCTION_ID = "input_search_text_dcm_function_id";
    public static final String INPUT_SEARCH_TEXT_DCM_GROUP_ID = "input_search_text_dcm_group_id";
    public static final String INPUT_HIDDEN_DCM_FUNCTION_STATUS = "hidden_status_id";
    public static final String DCM_FUNCTION_HIDDEN_ID = "function_hidden_id";
    public static final String DCM_FUNCTION_STATUS_VECTOR = "function_status_vector";
    public static final String DCM_EDITED_FUNCTION_MODEL = "edited_functiom_model";
    public static final String CONTROL_TEXT_DCM_FUNCTION_NAME = "function_name";
    public static final String CONTROL_TEXT_DCM_FUNCTION_STATUS = "function_status";
    public static final String CONTROL_TEXT_DCM_FUNCTION_DESCRIPTION = "function_description";
    public static final String CONTROL_TEXT_DCM_FUNCTION_UNIT = "function_unit";
    public static final String ACTION_DCM_SEARCH_FUNCTION = "search_function";
    public static final String ACTION_DCM_SAVE_NEW_FUNCTION = "save_new_function";
    public static final String ACTION_DCM_ADD_NEW_FUNCTION = "add_new_function";
    public static final String ACTION_DCM_UPDATE_FUNCTION_STATUS = "update_function_status";
    // Groups Keys
    public static final String CONTROL_TEXT_DCM_GROUP_NAME = "group_name";
    public static final String CONTROL_TEXT_DCM_GROUP_STATUS = "group_status";
    public static final String CONTROL_TEXT_DCM_GROUP_DESC = "group_description";
    public static final String CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME = "group_function_name";
    public static final String CONTROL_TEXT_DCM_GROUP_POS_NAME = "group_pos_name";
    public static final String CONTROL_TEXT_DCM_GROUP_POS_CODE = "group_pos_code";
    public static final String DCM_EDITED_GROUP_MODEL = "edited_group";
    public static final String INPUT_HIDDEN_GROUP_ID = "hidden_group_id";
    public static final String VECTOR_GROUP_STATUS = "group_status_vector";
    public static final String VECTOR_GROUP_FUNCTION = "group_function_vector";
    public static final String VECTOR_GROUP_FUNCTION_TARGET = "group_function_target_vector";
    public static final String ACTION_DCM_GROUP_SEARCH = "dcm_search_groups";
    public static final String ACTION_DCM_SAVE_NEW_GROUP = "save_new_group";
    public static final String ACTION_DCM_SAVE_EDITED_GROUP = "save_edited_group";
    public static final String ACTION_DCM_SAVE_GROUP_POS = "save_group_pos";
    public static final String ACTION_DCM_SAVE_GROUP_FUNCTION = "save_group_function";
    public static final String ACTION_DCM_UPDATE_GROUP_STATUS = "update_group_status";
    public static final String INPUT_HIDDEN_GROUP_STATUS = "hidden_group_status";
    public static final String DCM_GROUP_SEARCH_RESULT = "group_search_result";
    public static final String DCM_GROUP_FUNCTION_VECTOR = "group_function_vector";
//ACTUAL VISIT KEYS
    public static final String DCM_ACTUAL_VISIT_OBJECTIVES_VECTOR = "actual_visit_objective_vector";
    public static final String DCM_ACTUAL_VISIT_POS_VECTOR = "actual_visit_pos_vector";
    public static final String DCM_ACTUAL_VISIT_COMMENTS_VECTOR = "actual_visit_comment_vector";
    public static final String CONTROL_TEXT_DCM_ACTUAL_VISIT_POS_CODE = "actual_visit_pos_code";
    public static final String CONTROL_TEXT_DCM_ACTUAL_VISIT_COMMENTS = "actual_visit_comment";
    public static final String CONTROL_TEXT_DCM_ACTUAL_VISIT_DATE = "actual_visit_date";
    public static final String CONTROL_SELECT_DCM_ACTUAL_VISIT_FUNCTION_ID = "select_dcm_actual_visit_function_id";
//ACTUAL VISIT KEYS
    public static final String INPUT_HIDDEN_POS_SERVICE_ID = "hidden_pos_service_id";
    public static final String INPUT_TEXT_SALES_FORM_CONTRACTS_COLLECTED = "text_sales_form_contracts_collected";
    public static final String INPUT_HIDDEN_FUNCTION_ID = "hidden_function_id";
    public static final String INPUT_TEXT_POS_SERVICE_NAME = "text_pos_service_name";
    public static final String INPUT_TEXTAREA_POS_SERVICE_DESC = "textarea_pos_service_desc";
    public static final String INPUT_SELECT_POS_SERVICE_STATUS_TYPE_ID = "select_pos_service_status_type_id";
    public static final String INPUT_HIDDEN_POS_SERVICE_STATUS_TYPE_ID = "hidden_pos_service_status_type_id";
    public static final String INPUT_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID = "select_pos_service_eligibility_type_id";
    public static final String INPUT_SEARCH_TEXT_POS_SERVICE_NAME = "search_text_pos_service_name";
    public static final String INPUT_SEARCH_SELECT_POS_SERVICE_STATUS_TYPE_ID = "search_select_pos_service_status_type_id";
    public static final String INPUT_SEARCH_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID = "search_select_pos_service_eligibility_type_id";
    public static final String INPUT_HIDDEN_SERVICE_REQUEST_ID = "hidden_service_request_id";
    public static final String INPUT_SEARCH_TEXT_CASE_TITLE = "search_text_case_title";
    public static final String INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_FROM = "search_text_service_request_initiate_date_from";
    public static final String INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_TO = "search_text_service_request_initiate_date_to";
    public static final String INPUT_SEARCH_SELECT_SERVICE_REQUEST_STATUS_TYPE_ID = "search_select_service_request_status_type_id";
    public static final String INPUT_SELECT_SERVICE_REQUEST_STATUS_TYPE_ID = "select_service_request_status_type_id";
    public static final String INPUT_SELECT_SERVICE_REQUEST_WARNING_TYPE_ID = "select_service_request_warning_type_id";
    public static final String ACTION_ACTUAL_VISIT_EDIT_POS = "actual_visit_edit_pos";
    public static final String VECTOR_ALL_REGIONS_LEVELS = "vector_all_regions_levels";
    public static final String ACTION_SEARCH_REGION = "action_search_region";
    public static final String SEARCH_REGION_RESULT = "search_region_result";
    public static final String ADD_CHILDS_TO_REGION = "add_childs_to_region";
    
    public static final String EDIT_USERS_TO_REGION = "edit_users_to_region";
    public static final String UPDATE_USERS_TO_REGION = "update_users_to_region";
    
    public static final String DELETE_REGION = "delete_region";
    public static final String ACTION_VIEW_REGION_PARENT = "action_view_region_parent";
    public static final String ACTION_VIEW_REGION_CHILDS = "action_view_region_childs";
    public static final String ACTION_EXPORT_REGION_POS_REPORT = "action_export_region_pos_report";
    public static final String ACTION_EXPORT_SPECIFIC_REGION_REPORT = "action_export_specific_region_report";
    
    //public static final String ACTION_SHOW_HISTORY_FILE ="show_history";
    public static final String VECTOR_REGION_CHILDS = "vector_region_childs";
    public static final String VECTOR_REGION_PARENTS = "vector_region_parents";
    public static final String Message = "message";
    public static final String ACTION_ADD_CHILDS_TO_REGION = "save_childs_to_region";
    public static final String IMPORT_TABLE = "table";
    public static final String REGION_CHILDS_TABLE = "51";
    public static final String ACTION_ADD_CHILDS_TO_REGION_PROCESS = "excel_add_childs_to_region";
    public static final String SAVE_CHILDS_TO_REGION = "save_childs_to_region";
    public static final String INPUT_CONTROL_PAGE_NUMBER = "control_text_page_number";
    public static final String STRING_OF_TOTAL_PAGE_NUMBER = "string_of_total_page_number";
    public static final String DESTINATION_PAGE = "destinationPage";
    public static final String FIRST_PAGE_NUMBER = "first_page_number";
    public static final String ACTION_WHEN_CLICK_ENTER_PAGING = "action_name_when_click_enter";
    public static final String INPUT_TEXT_CAPMAS_CODE = "input_text_capmas_code";
    public static final String INPUT_TEXT_POP_CODE = "input_text_pop_code";
    public static final String INPUT_TEXT_MARKAZ_ENAME = "input_text_markaz_ename";
    public static final String INPUT_TEXT_MARKAZ_ANAME = "input_text_markaz_aname";
    public static final String INPUT_TEXT_REGION_ANAME = "input_text_region_aname";
    public static final String INPUT_TYPE_AREA_COVRAGE = "input_type_area_covrage";
    public static final String INPUT_TYPE_AREA_TYPE_ORIGINAL = "input_type_area_type_original";
    public static final String VECTOR_ALL_COVARAGE_LEVELS = "vector_all_covarage_levels";
    public static final String VECTOR_ALL_ORGIGINAL_LEVELS = "vector_all_original_levels";
    public static final String INPUT_SEARCH_SELECT_COVARAGE_LEVEL_NAME = "input_search_select_covarage_level_name";
    public static final String INPUT_SEARCH_SELECT_ORIGINAL_LEVEL_NAME = "input_search_select_original_level_name";
    public static final String INPUT_TEXT_FAMILY = "input_text_family";
    public static final String INPUT_TEXT_REGION_CODE = "input_text_region_code";
    public static final String ACTION_VIEW_REGION_DETAILS = "action_view_region_details";
    public static final String VECTOR_REGION_DETAILS = "vector_Rregion_details";
    public static final String AREA_CHILDS_TABLE = "54";
    public static final String CONTROL_SHOW_REGIONS_CHECKBOX = "show_regions_checkbox_";
    public static final String ACTION_VIEW_EDIT_PARENT = "action_view_edit_parent";
    public static final String VECTOR_PARENTS = "parents";
    public static final String VECTOR_SELECTED = "select";
    public static final String VECTOR_UPDATED = "updated";
    public static final String CONTROL_SHOW_PARENT_COMBOBOX = "show_parent_combobox";
    public static final String ACTION_UPDATE_GEOGRAPHICAL = "action_update_geographical";
    public static final String CONTROL_HIDDEN_UPDATE_CHILDS = "control_hidden_update_childs";
    public static final String ACTION_UPLOAD_EXCEL_ADDRESS = "action_upload_excel_address";
    public static final String ACTION_GENERATE_EXCEL_TEMPLATE = "action_generate_excel_template";
    public static final String ACTION_UPLOAD_GENERATED_FILE="action_upload_generated_file";
}
package com.mobinil.sds.web.interfaces.sa;

/**
 * Administration module interface holding all the keys used in the Administration
 * module (actions and controls).
 * For example:
 * <pre>
 *      UserDTO dtoUser;
 *      request.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT,dtoUser);
 * </pre>
 *
 * @version	1.01 Feb 2004
 * @author  Ahmed Mohamed Abd Elmonem
 * @see     
 *
 * SDS
 * MobiNil
 */ 

public interface AdministrationInterfaceKey 
{

public static final String CACH_OBJ_MODULE_STATUS = "cach_obj_module_status";
public static final String CACH_OBJ_PRIVILAGE_STATUS = "cach_obj_privilage_status";
public static final String CACH_OBJ_MODULE_LIST = "cach_obj_module_list";
////////////////////////////////////////////////////////////////////////
/*                      Returned Data HashMap Keys 
*////////////////////////////////////////////////////////////////////////

// Key for the list all modules action
  public static final String ACTION_LIST_MODULES = "list_modules";

// Key for the update modules action
  public static final String ACTION_UPDATE_MODULES_STATUS = "update_modules_status";

// Key for the list of privileges orderd by modules
  public static final String ACTION_LIST_PRIVILEGES = "list_privileges";

  public static final String ACTION_SHOW_REVENU_DATA_ENTRY ="show_revenu_screen";
  public static final String ACTION_SEARCH_REVENUE = "search_revenue_screen";
  public static final String ACTION_UPDATE_REVENUE = "update_revenue_screen";
  public static final String ACTION_SHOW_PAYMENT_LEVEL_HISTORY ="show_payment_level_history";
  public static final String ACTION_SAVE_PAYMENT_LEVEL_HISTORY ="save_payment_level_history";
  

  public static final String ACTION_SHOW_SCRATCH_CATRD_DATA_ENTRY ="show_scratch_card_screen";
  public static final String ACTION_SEARCH_SCRATCH_CARD = "search_scratch_card_screen";
  public static final String ACTION_UPDATE_SCRATCH_CARD = "update_scratch_card_screen";

  public static final String ACTION_SHOW_ACTIVATION ="show_activation_screen";
  public static final String ACTION_SEARCH_ACTIVATION = "search_activation_screen";
  public static final String ACTION_UPDATE_ACTIVATION= "update_activation_screen";

  public static final String ACTION_SHOW_DATA_IMPORT_SCREEN="show_data_import_screen";
  public static final String ACTION_SHOW_POS_BULK_UPDATE_IMPORT_SCREEN="show_pos_bulk_update_import_screen";
  public static final String ACTION_DATA_IMPORT_PROCESS = "data_import_process";
  public static final String ACTION_BULK_UPDATE_PROCESS = "bulk_update_process";

  
  
  public static final String ACTION_SHOW_NOMAD_IMPORT_SCREEN="show_nomad_file_import_screen";
  public static final String ACTION_NOMAD_IMPORT_PROCESS = "nomad_file_import_process";
  public static final String ACTION_TANGO_IMPORT_PROCESS = "tango_file_import_process";
  public static final String ACTION_VIEW_NOMAD_FILE = "view_nomad_file";
  public static final String ACTION_DELETE_NOMAD_FILE = "delete_nomad_file";
  public static final String VECTOR_FILES = "vector_files";
  public static final String TEXT_NOMAD_FILE_NAME = "nomad_file_name";
  
  public static final String ACTION_RUN_LINUX_COMMAND_INPUT = "run_linux_command_input";
  public static final String ACTION_RUN_LINUX_COMMAND_OUTPUT = "run_linux_command_output";
  
  
// Key for the update privilege action
  public static final String ACTION_UPDATE_PRIVILEGE_STATUS = "update_privilege_status";


// Key for the Bundle Action
  public static final String ACTION_SHOW_BUNDLE_PRODUCT_TYPE = "show_bundle_product_type";
  public static final String ACTION_SHOW_PRODUCTS = "show_products";
  public static final String ACTION_SHOW_CHANNELS = "show_channels";
  public static final String ACTION_SHOW_PAYMENT_LEVELS = "show_payment_levels";
  public static final String ACTION_EDIT_BUNDLE_PRODUCT_TYPE = "edit_bundle_product_type";
  public static final String ACTION_UPDATE_BUNDLE_PRODUCT_TYPE = "update_bundle_product_type";
  public static final String ACTION_CREATE_NEW_PRODUCT_TYPE = "create_new_product_type";
  public static final String ACTION_SAVE_NEW_PRODUCT_TYPE = "save_new_product_type";
  public static final String ACTION_SHOW_BUNDLE_PROMOTION_TYPE = "show_bundle_promotion_type";
  public static final String ACTION_EDIT_BUNDLE_PROMOTION_TYPE = "edit_bundle_promotion_type";
  public static final String ACTION_CREATE_NEW_PROMOTION_TYPE = "create_new_promotion_type";
  public static final String ACTION_UPDATE_BUNDLE_PROMOTION_TYPE = "update_bundle_promotion_type";
  public static final String ACTION_SAVE_NEW_PROMOTION_TYPE = "save_new_promotion_type";
  public static final String ACTION_SHOW_BUNDLE_COMPONENT = "show_bundle_component";
  public static final String ACTION_EDIT_BUNDLE_COMPONENT ="edit_bundle_component";
  public static final String ACTION_CREATE_NEW_BUNDLE_COMPONENT ="create_new_bundle_component";
  public static final String ACTION_UPDATE_BUNDLE_COMPONENT ="update_bundle_component";
  public static final String ACTION_SAVE_NEW_BUNDLE_COMPONENT ="save_new_bundle_component";
  public static final String ACTION_EDIT_PRODUCT ="edit_product";
  public static final String ACTION_UPDATE_PRODUCT ="update_product";
  public static final String ACTION_NEW_PRODUCT ="new_product";
  public static final String ACTION_ADD_PRODUCT ="add_product";
  public static final String ACTION_EDIT_PAYMENT_LEVEL ="edit_payment_level";
  public static final String ACTION_NEW_PAYMENT_LEVEL ="new_payment_level";
  public static final String ACTION_ADD_PAYMENT_LEVEL ="add_payment_level";
  public static final String ACTION_UPDATE_PAYMENT_LEVEL ="update_payment_level";
  public static final String ACTION_EDIT_CHANNEL ="edit_channel";
  public static final String ACTION_NEW_CHANNEL ="new_channel";
  public static final String ACTION_ADD_CHANNEL ="add_channel";
  public static final String ACTION_UPDATE_CHANNEL ="update_channel";
/////////////////////////////////////////CONTROLS///////////////////////////////
// Key for the select control name prefix

  public static final String CONTROL_LINUX_FILE_PATH = "control_linux_file_path";
  public static final String CONTROL_LINUX_FILE_SIZE_NEEDED = "control_linux_file_size_needed";
  public static final String CONTROL_TEXT_NAME_PRODUCT_NAME ="product_name";
  public static final String CONTROL_TEXT_NAME_PRODUCT_CAT_NAME = "product_cat_name";
  public static final String CONTROL_TEXT_NAME_PRODUCT_DESC = "product_desc";

  public static final String CONTROL_TEXT_NAME_CHANNEL_NAME ="channel_name";
  public static final String CONTROL_TEXT_NAME_PAYMENT_LEVEL_NAME ="payment_level_name";
  
  public static final String LINUX_COMMAND_RESULT = "linux_command_result";
  
  
  public static final String CONTROL_SELSCT_NAME_PREFIX = "select_";

  public static final String CONTROL_SELECT_MONTH ="control_select_month";
  public static final String CONTROL_INPUT_YEAR = "control_input_year";  
  public static final String CONTROL_INPUT_AMOUNT="AMOUNT_INPUT";
  public static final String CONTROL_INPUT_AMOUNT_DB_VALUE="AMOUNT_INPUT_DB";
  public static final String CONTROL_INPUT_DCM_ID ="dcm_id_input";
  public static final String CONTROL_INPUT_REVENUE_ID ="REVENUE_ID_input";
  public static final String CONTROL_INPUT_SCRATCH_CARD_ID ="SCRATCH_ID_input";
  public static final String CONTROL_HIDDEN_NAME_PRODUCT_ID = "product_id";
  public static final String CONTROL_HIDDEN_NAME_CHANNEL_ID = "channel_id";
  public static final String CONTROL_HIDDEN_NAME_PAYMENT_LEVEL_ID = "payment_level_id";
  public static final String CONTROL_INPUT_ACTIVATION_ID ="ACTIVATION_ID_input";
  public static final String CONTROL_BUTTON_NAME_EDIT_PRODUCT_PREFIX = "edit_product_";
  public static final String CONTROL_BUTTON_NAME_EDIT_CHANNEL_PREFIX = "edit_channel_";
  public static final String CONTROL_BUTTON_NAME_EDIT_PAYMENT_LEVEL_PREFIX = "edit_payment_level_";
  

  public static final String PRODUCT_VECTOR = "product_vector";
  public static final String CONTROL_SELECT_PRODUCT_ID = "product_id";

  public static final String TABLE_DEF_VECTOR= "table_vector";

  public static final String DATA_IMPORT_INSERT ="INSERT";
  public static final String DATA_IMPORT_UPDATE= "UPDATE";
  

  public static final String CONTROL_DATA_IMPORT_TABLES = "control_table_id";
  public static final String CONTROL_DATA_IMPORT_OPERATION="control_data_import_operation";
  public static final String TEXT_NOMAD_DATA_TABLE_NAME = "GEN_DCM_NOMAD";
  public static final String TEXT_PAY_LEVEL_HISTORY_ID = "history_id";
  public static final String TEXT_PAY_LEVEL_HISTORY_ID_EXISTS = "history_id_exists";

  public static final String QUERY_STRING_TABLES = "TABLE_ID";
  public static final String QUERY_STRING_OPERATION = "operation";
  public static final String QUERY_LABEL_ID = "label_id";
  
  
  public static final String INPUT_HIDDEN_PRODUCT_TYPE_ID = "hidden_product_type_id";
  public static final String INPUT_TEXT_PRODUCT_TYPE_NAME = "text_product_type_name";
  public static final String INPUT_TEXT_PRODUCT_TYPE_ID = "text_product_type_id";
  public static final String INPUT_HIDDEN_PROMOTION_TYPE_ID = "hidden_promotion_type_id";
  public static final String INPUT_TEXT_PROMOTION_TYPE_ID = "text_promotion_type_id";
  public static final String INPUT_TEXT_PROMOTION_TYPE_NAME = "text_promotion_type_name";
  public static final String INPUT_HIDDEN_BUNDLE_COMPONENT_ID = "hidden_bundle_component_id";
  public static final String INPUT_TEXT_BUNDLE_COMPONENT_ID = "text_bundle_component_id";
  public static final String INPUT_TEXT_BUNDLE_COMPONENT_NAME = "text_bundle_component_name";
}
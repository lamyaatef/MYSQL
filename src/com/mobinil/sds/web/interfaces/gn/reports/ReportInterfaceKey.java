package com.mobinil.sds.web.interfaces.gn.reports;

public interface ReportInterfaceKey 
{
  public static final boolean   DEBUG_FLAG    = true ;

  //---------------------------------------------------------------------------
  public static final String ACTION_INITIALIZE_WIZARD = "action_initialize_wizard";

  public static final String REPORT_FINISHED = "report_finished";
  public static final String REPORT_LOADING = "report_loading";
  public static final String EXPORT_FILE_PATH = "EXPORT_PATH";
  //---------------------------------------------------------------------------
  public static final String ACTION_SAVE_REPORT = "action_save_report";
  public static final String PARAM_SAVE_REPORT_NAME = "param_save_report_name";
  public static final String PARAM_SAVE_REPORT_DESC = "param_save_report_desc";
  public static final String PARAM_SAVE_REPORT_DATAVIEW_ID = "param_save_report_dataview_id";
  //public static final String PARAM_SAVE_REPORT_SELECT_LIST = "param_save_report_select_list";
  //public static final String PARAM_SAVE_REPORT_ORDER_BY_FIELD_ID_LIST = "param_save_report_order_by_field_id_list";
  //public static final String PARAM_SAVE_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST = "param_save_report_order_by_field_order_type_list";
  public static final String ACTION_ASSIGN_REPORT_TO_USER = "assign_report_to_user";
  public static final String ACTION_UPDATE_USER_REPORTS = "update_user_reports";
  public static final String INPUT_HIDDEN_REPORT_ID = "report_id";
  public static final String HASHMAP_KEY_COLLECTION_PERSON_REPORT = "person_report_vector";
  //---------------------------------------------------------------------------
  public static final String ACTION_UPDATE_REPORT = "action_update_report";
  public static final String PARAM_UPDATE_REPORT_ID = "param_update_report_id";
  public static final String PARAM_UPDATE_REPORT_NAME = "param_update_report_name";
  public static final String PARAM_UPDATE_REPORT_DESC = "param_update_report_desc";
  public static final String PARAM_UPDATE_REPORT_DATAVIEW_ID = "param_update_report_dataview_id";

  //---------------------------------------------------------------------------
  public static final String ACTION_RETRIEVE_VIEW_ONLY_REPORT_LIST = "action_retrieve_view_only_report_list";
  public static final String ACTION_RETRIEVE_REPORT_LIST = "action_retrieve_report_list";
  public static final String ACTION_RETRIEVE_REPORT_LIST_FOR_USER = "action_retrieve_report_list_for_user";
  public static final String PARAM_RETRIEVE_REPORT_LIST_UNIVERSE = "param_retrieve_report_list_universe";

  //---------------------------------------------------------------------------
  public static final String ACTION_LOAD_REPORT = "action_load_report";
  public static final String PARAM_LOAD_REPORT_ID = "param_load_report_id";


  //---------------------------------------------------------------------------
  public static final String CACH_OBJ_REPORT_STATUS = "cach_obj_report_status";


  //---------------------------------------------------------------------------
  // action requested from Hagry because of displaying the data in two pages
  public static final String ACTION_LOAD_REPORT_DETAILS = "action_load_report_details";
  public static final String PARAM_LOAD_REPORT_DETAILS_ID = "param_load_report_details_id";

  //---------------------------------------------------------------------------
/*
  public static final String ACTION_SAVE_CUSTOMIZED_REPORT = "action_save_customized_report";
  public static final String PARAM_SAVE_CUSTOMIZED_REPORT_ID = "param_save_customized_report_id";

  public static final String PARAM_SAVE_CUSTOMIZED_REPORT_SELECT_LIST = "param_save_customized_report_select_list";
  //public static final String PARAM_SAVE_CUSTOMIZED_REPORT_SELECT_NUM = "param_save_customized_report_select_num";
  //public static final String PARAM_SAVE_CUSTOMIZED_REPORT_SELECT_FIELD_ID = "param_save_customized_report_select_field_id";

  public static final String PARAM_SAVE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID_LIST = "param_save_customized_report_order_by_field_id_list";
  public static final String PARAM_SAVE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST = "param_save_customized_report_order_by_field_order_type_list";
  //public static final String PARAM_SAVE_CUSTOMIZED_REPORT_ORDER_BY_NUM = "param_save_customized_report_order_by_num";
  //public static final String PARAM_SAVE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID = "param_save_customized_report_order_by_field_id";
  //public static final String PARAM_SAVE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE = "param_save_customized_report_order_by_field_order_type";
*/


  //---------------------------------------------------------------------------
  public static final String ACTION_UPDATE_CUSTOMIZED_REPORT = "action_update_customized_report";
  public static final String PARAM_UPDATE_CUSTOMIZED_REPORT_ID = "param_update_customized_report_id";
  public static final String PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST = "param_update_customized_report_select_list";
  public static final String PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID_LIST = "param_update_customized_report_order_by_field_id_list";
  public static final String PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST = "param_update_customized_report_order_by_field_order_type_list";


  //---------------------------------------------------------------------------
  public static final String ACTION_PREVIEW_REPORT = "action_preview_report";
  public static final String ACTION_SHOW_REPORT_PARAM = "action_show_report_param";
  public static final String ACTION_SHOW_REPORT_PARAM_EXCEL = "action_show_report_param_excel";
  public static final String ACTION_SHOW_REPORT_PARAM_TEXT = "action_show_report_param_text";
  public static final String ACTION_SAVE_REPORT_STATUS = "save_report_status";

  public static final String FILE_TYPE = "txt";
  public static final String ACTION_EXPORT_REPORT_EXCEL = "action_export_report_excel";
  public static final String ACTION_EXPORT_REPORT_EXCEL_FINISHED = "action_export_report_excel_finished";
  public static final String ACTION_EXPORT_REPORT_TEXT = "action_export_report_text";
  public static final String ACTION_EXPORT_REPORT_TEXT_FINISHED = "action_export_report_text_finished";
  public static final String PARAM_PREVIEW_REPORT_ID = "param_preview_report_id";
  public static final String PARAM_PREVIEW_REPORT_ROWS_PER_PAGE = "param_preview_report_rows_per_page";
  public static final String PARAM_PREVIEW_REPORT_PAGE_NUM = "param_preview_report_page_num";
  

  public static final String PARAM_PREVIEW_REPORT_PARAMS_NUM       = "param_preview_report_params_num";
  public static final String PARAM_PREVIEW_REPORT_PARAM_ID         = "param_preview_report_param_id";
  public static final String PARAM_PREVIEW_REPORT_PARAM_VALUE      = "param_preview_report_param_value";
  public static final String PARAM_PREVIEW_REPORT_PARAM_TYPE       = "param_preview_report_param_type";
  public static final int    PARAM_PREVIEW_REPORT_PARAM_TYPE_NUMERIC = 1;
  public static final int    PARAM_PREVIEW_REPORT_PARAM_TYPE_TEXT    = 2;
  public static final int    PARAM_PREVIEW_REPORT_PARAM_TYPE_DATE    = 3;
  public static final String PARAM_PREVIEW_REPORT_PARAM_TYPE_DATE_FORMATTING = "dd/mm/yyyy";

  //---------------------------------------------------------------------------
  public static final String ERR_STR_SAVE_REPORT        = "couldn't save the report";
  public static final String ERR_STR_UNIQUE_REPORT      = "report name exists already";
  public static final int    ERR_NUM_SAVE_REPORT        = -1 ;
  public static final int    ERR_NUM_UNIQUE_REPORT      = -2 ;


  //------------------- Keys imported from QueryBuilderInterface begin --------------
  public static final int    PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE       = 1;
  public static final int    PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY       = 2;
  public static final int    PARAM_PREVIEW_QUERY_QUERY_TYPE_KPI         = 3;
  public static final int    PARAM_PREVIEW_QUERY_QUERY_TYPE_ELIGIBLE    = 4;
  //public static final int    PARAM_PREVIEW_QUERY_QUERY_TYPE_REPORT      = 5;

  public static final int    PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE    = 1;
  public static final int    PARAM_PREVIEW_QUERY_QUERY_STATUS_INACTIVE  = 2;

  public static final int    DISPLAY_TYPE_DISPLAYED       = 1;
  public static final int    DISPLAY_TYPE_NOT_DISPLAYED   = 2;
  public static final int    DISPLAY_TYPE_SHOWN           = 3;

  public static final String ORDER_TYPE_ASCENDING          = "ASC";
  public static final String ORDER_TYPE_DESCENDING         = "DESC";


  public static final String UNIVERSE_TYPE_AD_HOC_REPORTS    = "Ad-hoc Reports";
  public static final String UNIVERSE_TYPE_KPI               = "KPI";
  public static final String UNIVERSE_TYPE_LINES_ELIGIBLE_FOR_COMMISSIONING    = "Lines Eligible for commissioning";
  //------------------- Keys imported from QueryBuilderInterface end --------------


  public static final String ACTION_SHOW_REPORT_USER_ASSIGN_SCREEN = "ACTION_SHOW_REPORT_USER_ASSIGN_SCREEN";
  
  public static final String ACTION_UPDATE_USER_REPORT = "ACTION_UPDATE_USER_Report";
  //------------------------------------------------------------------
  public static final String ACTION_NEW_GROUP = "new_group";
  public static final String ACTION_LOAD_GROUP= "load_group";
  public static final String ACTION_SAVE_GROUP= "save_group";
  public static final String ACTION_SHOW_REPORT= "show_reports";
  public static final String ACTION_VIEW_REPORT = "view_reports";
  public static final String ACTION_SAVE_GROUP_STATUS = "save_group_status";
  public static final String ACTION_ASSIGN_REPORT_TO_GROUP = "assign_report_to_group";

  public static final String CONTROL_GROUP_NAME="group_name";
  public static final String CONTROL_GROUP_DESC="group_desc";
  public static final String CONTROL_GROUP_STATUS="group_status";
  public static final String CONTROL_CHECKBOX_ASSIGN_REPORT_TO_GROUP="assign_report_to_group";

   
   
  public static final String HASHMAP_KEY_COLLECTION_GROUP_STATUS = "group_status_vector"; 
  public static final String HASHMAP_KEY_COLLECTION_GROUP_REPORTS = "group_reports_vector"; 
  public static final String HASHMAP_KEY_COLLECTION_GROUP_DETAILS = "group_details_dto"; 
  public static final String HASHMAP_KEY_COLLECTION_ALL_REPORTS = "all_reports_vector"; 
  
  public static final String PARAM_GROUP_ID = "param_group_id";
  public static final String INPUT_SEARCH_TEST_REPORT_NAME = "search_text_report_name";

  

  

}

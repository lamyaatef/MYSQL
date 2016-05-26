package com.mobinil.sds.web.interfaces.dm;

public class DMInterfaceKey {
	
	 ////////////////////////////////////////////////////////////////////////
	  /*           DM Actions Keys                 /
	  *//////////////////////////////////////////////////////////////////////
	public static final String GENERAL_REVENUE_REPORT_START_DATE = "general_revenue_report_start_date";
	public static final String GENERAL_REVENUE_REPORT_END_DATE = "general_revenue_report_end_date";
	 public static final String VIEW_GENERAL_REVENUE_REPORT = "view_general_revenue_report";
	 public static final String GENERATE_REVENUE_REPORT = "generate_revenue_report";
	  public static final String ACTION_CHOOSE_ZIP_FILE = "choose_zip_file";
	  public static final String ACTION_UPLOAD_ZIP_FILE  = "upload_zip_file";
	  public static final String ACTION_VIEW_FILES  = "view_files";
	  public static final String  ACTION_DELETE_FILE="delete_file";
	  
	  public static final String ACTION_PAYMENT_CASH_REPORT_IMPORT="payment_cash_report_import";
      public static final String ACTION_PAYMENT_CASH_REPORT_IMPORT_PROCESS="payment_cash_report_import_process";
      public static final String ACTION_PAYMENT_MASTER_IMPORT="payment_master_import";
      public static final String ACTION_PAYMENT_MASTER_IMPORT_PROCESS="payment_master_import_process";
      public static final String ACTION_PAYMENT_VISA_IMPORT="payment_visa_import";
      public static final String ACTION_PAYMENT_VISA_IMPORT_PROCESS="payment_visa_import_process";
      public static final String ACTION_COMPLEMANTRY_DISTRIBUTION_IMPORT="complemantry_distribution_import";
      public static final String ACTION_COMPLEMANTRY_DISTRIBUTION_IMPORT_PROCESS="complemantry_distribution_import_process";
      
      
      
      
      public static final String ACTION_AUTH_CALL_IMPORT = "auth_call_import";
      public static final String ACTION_AUTH_CALL_IMPORT_PROCESS = "auth_call_import_process";
      public static final String ACTION_DELETE_AUTH_CALL = "delete_auth_call";
      public static final String ACTION_VIEW_AUTH_CALL = "view_auth_call";
      
      public static final String ACTION_VIEW_CASH="view_cash";  
      public static final String ACTION_DELETE_CASH="delete_cash";  
      
      public static final String ACTION_VIEW_VISA="view_visa";  
      public static final String ACTION_DELETE_VISA="delete_visa";  
      
      
      public static final String ACTION_VIEW_MASTER="view_master";  
      public static final String ACTION_DELETE_MASTER="delete_master";  
      
      
      public static final String ACTION_VIEW_DIST="view_dist";  
      public static final String ACTION_DELETE_DIST="delete_dist";  
      
      
      public static final String   ACTION_VIEW_ZIP_UPLOAD_STATSTICS="view_zip_upload_statstics";
      
      public static final String   ACTION_VIEW_AUTH_CALL_INVALID_DCM_CODE="view_auth_call_invalid_dcm_code";
      
      public static final String   ACTION_POS_PAY_HISTORY_IMPORT="pos_pay_history_import";
      public static final String   ACTION_POS_PAY_HISTORY_IMPORT_PROCESS="pos_pay_history_import_process";
      
      
      
      public static final String   ACTION_POS_DEL_CHK_IMPORT="pos_del_chk_import";
      public static final String   ACTION_POS_DEL_CHK_IMPORT_PROCESS="pos_del_chk_import_process";
      
      
      
      public static final String   ACTION_POS_ELG_CHK_IMPORT="pos_elg_chk_import";
      public static final String   ACTION_POS_ELG_CHK_IMPORT_PROCESS="pos_elg_chk_import_process";
      
      
      public static final String   ACTION_VIEW_POS_HIS="view_pos_his";
      public static final String   ACTION_DELETE_POS_HIS="delete_pos_his";
      
      public static final String   ACTION_VIEW_POS_DEL="view_pos_del";
      public static final String   ACTION_DELETE_POS_DEL="delete_pos_del";
      
      
      public static final String   ACTION_VIEW_POS_ELG="view_pos_elg";
      public static final String   ACTION_DELETE_POS_ELG="delete_pos_elg";
      
      public static final String   ACTION_ELG_CHK_SEARCH_IMPORT="elg_chk_search_import";
      
      
      public static final String   ACTION_ELG_CHK_SEARCH_IMPORT_PROCESS="elg_chk_search_import_process";
      
      public static final String   ACTION_VIEW_ELG_CHK_SEARCH_FILE="view_elg_chk_search_file";
      
      
      public static final String   ACTION_DELETE_ELG_CHK_SEARCH_FILE="delete_elg_chk_search_file";
      
      public static final String   ACTION_VIEW_ELG_CHK_SEARCH_FILE_INVALID_POS="view_elg_search_file_invalid_pos";
      
      
      public static final String   ACTION_VIEW_ELG_CHK_SEARCH_FILE_DATA="view_elg_chk_search_file_data";
      
      public static final String   ACTION_DEL_SEARCH_IMPORT="del_search_import";
      
      public static final String   ACTION_DEL_SEARCH_IMPORT_PROCESS="del_search_import_process";
      
      public static final String   ACTION_VIEW_DEL_CHK_SEARCH_FILE_DATA="view_del_chk_search_file_data";   
      
      
      public static final String    ACTION_VIEW_DEL_CHK_SEARCH_FILE="view_del_chk_search_file";
      
      public static final String    ACTION_VIEW_DEL_CHK_SEARCH_FILE_INVALID_POS="view_del_chk_search_file_invalid_pos"; 
      public static final String    ACTION_DELETE_DEL_CHK_SEARCH_FILE="delete_del_chk_search_file";
      
      
      public static final String    ACTION_HIS_SEARCH_IMPORT="his_search_import";
      
      public static final String    ACTION_HIS_SEARCH_IMPORT_PROCESS="his_search_import_process";
      
      public static final String    ACTION_VIEW_PAY_HIS_SEARCH_FILE="view_pay_his_search_file";
      
      
      public static final String    ACTION_DELETE_PAY_HIS_SEARCH_FILE="delete_pay_his_search_file";
      
      public static final String   ACTION_VIEW_PAY_HIS_SEARCH_FILE_DATA="view_pay_his_search_file_data";   
      
      public static final String   ACTION_VIEW_PAY_HIS_SEARCH_FILE_INVALID_POS="view_pay_his_search_file_invalid_pos"; 
      

      public static final String ACTION_VIEW_REVENUE_REPORT = "view_revenue_report";
      public static final String ACTION_EXPORT_REVENUE_REPORT = "export_revenue_report";
      public static final String ACTION_VIEW_CATEGORY_LIST = "view_category_list";
      public static final String ACTION_CREATE_NEW_CATEGORY = "create_new_category";
      public static final String ACTION_NOT_INTIALIZED_SERIALS_IMPORT = "not_intialized_serials_import";
      public static final String ACTION_NOT_INTIALIZED_SERIALS_IMPORT_PROCESS = "not_intialized_serials_import_process";
      public static final String ACTION_VIEW_INTIALIZED_SERIALS_FILE = "view_intialized_serials_file";
      public static final String ACTION_DELETE_INTIALIZED_SERIALS_FILE = "delete_intialized_serials_file";
      public static final String ACTION_DELETE_CATEGORY = "delete_category";
      public static final String ACTION_SAVE_NEW_CATEGORY = "save_new_category";
      public static final String ACTION_VIEW_CATEGORY_PRODUCT = "view_category_product";
      public static final String ACTION_DELETE_CATEGORY_PRODUCT = "delete_category_product";
      public static final String ACTION_ASSIGN_CATEGORY_PRODUCT = "assign_category_product";
      public static final String ACTION_SAVE_CATEGORY_PRODUCT= "save_category_product";

       ////////////////////////////////////////////////////////////////////////
	  /*           DM INPUR Keys                 /
	  *//////////////////////////////////////////////////////////////////////
	  
      public static final String CONTROL_SELECT_UPLOAD_ZIP_YEAR = "upload_zip_year";
	  public static final String CONTROL_SELECT_UPLOAD_ZIP_MONTH = "upload_zip_month";
	  public static final String CONTROL_SELECTED_MONTH = "control_selected_month";
      public static final String CONTROL_SELECTED_YEAR= "control_selected_year";
      public static final String CONTROL_SELECT_PAYMENT_CASH_MONTH= "control_select_payment_cash_month";
      public static final String CONTROL_SELECT_PAYMENT_CASH_YEAR= "control_select_payment_cash_year";
      
      
      public static final String CONTROL_SELECT_PAYMENT_MASTER_MONTH= "control_select_payment_master_month";
      public static final String CONTROL_SELECT_PAYMENT_MASTER_YEAR= "control_select_payment_master_year";
      
      public static final String CONTROL_SELECT_PAYMENT_VISA_MONTH= "control_select_payment_visa_month";
      public static final String CONTROL_SELECT_PAYMENT_VISA_YEAR= "control_select_payment_visa_year";
      
      public static final String CONTROL_SELECT_COMP_DIST_MONTH= "control_select_comp_dist_month";
      public static final String CONTROL_SELECT_COMP_DIST_YEAR= "control_select_comp_dist_year";
      
      
      public static final String CONTROL_SELECT_AUTH_CALL_MONTH= "control_select_auth_call_month";
      public static final String CONTROL_SELECT_AUTH_CALL_YEAR= "control_select_auth_call_year";
      
      public static final String CONTROL_SELECT_POS_HIS_YEAR= "control_select_pos_his_year";
      public static final String CONTROL_SELECT_POS_HIS_MONTH= "control_select_pos_his_month";
      
      
      
      public static final String CONTROL_SELECT_POS_DEL_YEAR= "control_select_pos_del_year";
      public static final String CONTROL_SELECT_POS_DEL_MONTH= "control_select_pos_del_month";
      
      
      
      
      public static final String CONTROL_SELECT_POS_ELG_YEAR= "control_select_pos_elg_year";
      public static final String CONTROL_SELECT_POS_ELG_MONTH= "control_select_pos_elg_month";
      
      public static final String CONTROL_HIDDEN_FILE_ID= "control_hidden_file_id";
      
      public static final String CONTROL_INPUT_DESCRIPTION= "control_input_description";

      public static final String HIDDEN_ACTION= "hidden_action";
      public static final String HIDDEN_ACTION_2= "hidden_action_2";
      public static final String HIDDEN_FILE_ID= "hidden_file_id";  
      
      public static final String INPUT_SEARCH_TEXT_DATE_FROM = "search_text_date_from";
      public static final String INPUT_SEARCH_TEXT_DATE_TO = "search_text_date_to";
      public static final String INPUT_TEXT_CATEGORY_NAME = "text_category_name";
      public static final String CONTROL_HIDDEN_PROBLEMATIC_LABEL_ID = "hidden_problematic_label_id";
      public static final String CONTROL_HIDDEN_PAYMENT_LEVEL_ID = "hidden_payment_level_id";
      public static final String INPUT_HIDDEN_NOT_INTIALIZED_FILE_ID = "hidden_not_intialized_file_id";
      public static final String INPUT_HIDDEN_CATEGORY_ID = "hidden_category_id";
      public static final String INPUT_HIDDEN_PRODUCT_ID = "hidden_product_id";
      public static final String CONTROL_HIDDEN_PRODUCT_ID = "product_id";
      
 ////////////////////////////////////////////////////////////////////////
	  /*           DM VECTOR                /
	  *//////////////////////////////////////////////////////////////////////
	  
	public static final String   VECTOR_FILES="vector_files";
	public static final String   VECTOR_STATISTICS="vector_statistics";
	public static final String   VECTOR_POS_CODE="vector_pos_code";
	public static final String   VECTOR_DCM_CODE="vector_dcm_code";
	
	public static final String   VECTOR_SEARCH_FILES="vector_search_file";
	
	public static final String   VECTOR_SEARCH_FILES_DATA="vector_search_file";
	public static final String   VECTOR_SEARCH_FILES_INVALID_POS="vector_search_file";
	
	
	
	
	


	
	
	
	
	

}

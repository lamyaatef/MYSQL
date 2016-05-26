package com.mobinil.sds.web.interfaces.payment;

public class PaymentInterfaceKey 
{
  public static final String ACTION_SEARCH_PAYMENT = "search_payment";
  public static final String ACTION_PAYMENT_SEARCH_PAYMENT = "payment_search_payment";
  public static final String ACTION_PAYMENT_SAVE_NEW_PAYMENT = "payment_save_new_payment";
  public static final String ACTION_VIEW_READY_PAYMENT = "view_ready_payment";
  public static final String ACTION_VIEW_CLOSED_PAYMENT = "view_closed_payment";  
  public static final String ACTION_CREATE_NEW_PAYMENT = "create_new_payment"; 
  public static final String ACTION_SAVE_NEW_PAYMENT = "save_new_payment"; 
  public static final String ACTION_PAY_PAYMENT = "pay_payment";
  public static final String ACTION_DELETE_PAYMENT = "delete_payment";
  public static final String ACTION_CLOSE_PAYMENT = "close_payment";
  public static final String ACTION_EXPORT_PAYMENT = "export_payment";
  public static final String ACTION_VIEW_PAYMENT = "view_payment";
  public static final String ACTION_UPDATE_PAYMENT_STATUS = "update_payment_status";
  public static final String ACTION_PAYMENT_COMMISSION = "payment_commission";
  public static final String ACTION_SAVE_PAYMENT_COMMISSION = "save_payment_commission";
  public static final String ACTION_EXPORT_PAYMENT_TO_EXCEL = "export_payment_to_excel";
  public static final String ACTION_EXPORT_PAYMENT_SHEETS_TO_EXCEL = "export_payment_sheets_to_excel";
  public static final String ACTION_ADD_TO_ACCOUNT_MODULE = "add_to_account_module";  
  
  public static final String CONTROL_HIDDEN_COMMISSION_CHECKED = "control_hidden_commission_checked";
  public static final String CONTROL_TEXT_PAYMENT_STATUS      = "payment_status";
  public static final String CONTROL_TEXT_PAYMENT_NAME        = "payment_name";  
  public static final String CONTROL_TEXT_PAYMENT_TYPE        = "payment_type";
  public static final String CONTROL_TEXT_PAYMENT_CATEGORY    = "payment_category";
  public static final String CONTROL_TEXT_PAYMENT_START_DATE  = "payment_start_date";
  public static final String CONTROL_TEXT_PAYMENT_END_DATE    = "payment_end_date";
  public static final String CONTROL_TEXT_PAYMENT_DESCRIPTION = "commission_description";
  public static final String CONTROL_TEXT_PAYMENT_START_DATE_FROM  = "payment_start_date_from";
  public static final String CONTROL_TEXT_PAYMENT_END_DATE_FROM    = "payment_end_date_from";
  public static final String CONTROL_TEXT_PAYMENT_START_DATE_TO  = "payment_start_date_to";
  public static final String CONTROL_TEXT_PAYMENT_END_DATE_TO    = "payment_end_date_to";

  public static final String VECTOR_PAYMENT_STATUS = "payment_status_vector";
  public static final String VECTOR_PAYMENT_TYPE = "payment_type_vector";  
  public static final String VECTOR_PAYMENT_COMMISSION_LIST = "payment_commission_list";  
  public static final String VECTOR_PAYMENT_COMMISSION = "payment_commission";  
    public static final String VECTOR_PAYMENT_COMMISSION_FACTOR = "payment_commission_factors";
  
  public static final String INPUT_HIDDEN_PAYMENT_ACTION = "hidden_payment_action";
  public static final String INPUT_HIDDEN_DELETE_PAYMENT = "Delete";
  public static final String INPUT_HIDDEN_CLOSE_PAYMENT = "Close";
  public static final String INPUT_HIDDEN_PAY_PAYMENT = "Pay";  
  public static final String INPUT_HIDDEN_ADD_TO_ACCOUNT = "Add";  
  public static final String INPUT_HIDDEN_ALL_PAYMENT = "All";    
  public static final String INPUT_HIDDEN_PAYMENT_ID = "hidden_payment_id";     
  public static final String INPUT_HIDDEN_PAYMENT_STATUS = "hidden_payment_status";  

//-----------------------------------------------------------
public static final String ACTION_VIEW_PAYMENT_TYPE = "view_payment_type";       
public static final String ACTION_NEW_PAYMENT_TYPE = "new_payment_type";       
public static final String ACTION_SAVE_PAYMENT_TYPE_STATUS = "save_payment_type_status";       
public static final String ACTION_ASSIGN_COMMISSION_CATEGORY_TO_PAYMENT_TYPE = "assign_commission_category_to_payment_type";       
public static final String ACTION_SAVE_PAYMENT_TYPE = "save_payment_type";       
public static final String ACTION_SAVE_CATEGORY_TO_PAYMENT_TYPE = "save_category_to_paymnet_type";       

public static final String PARAM_LOAD_PAYMENT_TYPE_ID = "param_load_payment_type_id";
public static final String PARAM_PAYMENT_TYPE_ID = "param_load_payment_type_id";


public static final String VEC_PAYMENT_TYPE = "vec_payment_type";
public static final String VEC_COMMISSIION_CATEGORY = "vec_commission_category";
public static final String VEC_PAYMENT_METHODS = "vec_payment_methods";

public static final String DTO_PAYMENT_TYPE_DETAIL = "payment_type_detail";
public static final String VEC_PAYMENT_TYPE_STATUS = "vec_payment_type_status";
public static final String VEC_COMMISSION_CONSTRAIN = "vec_commission_constrain";

public static final String CONTROL_INPUT_TEXT_PAYMENT_TYPE_NAME = "payment_type_name";
public static final String CONTROL_SELECT_PAYMENT_TYPE_STATUS = "payment_type_status";
public static final String CONTROL_SELECT_PAYMENT_TYPE_ACRRUAL = "payment_type_accrual";
public static final String CONTROL_SELECT_PAYMENT_TYPE_METHOD = "payment_type_method";
public static final String CONTROL_CHECKBOX_NAME_PREFIX = "checkbox_name_prefix";

public static final String INPUT_TEXT_PAGE_HEADER = "text_page_header";



  public PaymentInterfaceKey()
  {
  }
}
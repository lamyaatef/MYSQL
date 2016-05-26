package com.mobinil.sds.web.interfaces.sa;

public interface SecurityInterfaceKey 
{

/////////////////////////////////////////ACTIONS///////////////////////////////

// Key for the list all users action
  public static final String ACTION_CHECK_RULES = "check_rules";

 // Key for the list all users action
 public static final String ACTION_UPDATE_RULES = "update_rules";

  // Key for the list all users action
 public static final String ACTION_SHOW_CURRENT_RULES = "show_security_management";

 // Key to get DCM request
  public static final String ACTION_SHOW_LOG = "show_user_log";  

// Key to get DCM request
  public static final String ACTION_GET_LOG = "get_logs";

 /////////////////////////////////////////CONTROLS///////////////////////////////

// Key for the select control name prefix
  public static final String CONTROL_SELECT_NAME_STATUS_RULE = "select_";
  
// Key for the select control name prefix
  public static final String CONTROL_SELECT_NAME_TYPE_RULE = "select_type_";  

// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_PASSWORD_LENGTH_RULE = "password_length";
  
// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_PASSWORD_CHAR_NUM = "password_char_and_num";
  
  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_PASSWORD_LAST_NPASSWORDS = "password_last";
  
  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_PASSWORD_SEQUENCE = "password_seq";
  
  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_PASSWORD_SIMILAR = "password_similar";
  
  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_PASSWORD_CASE_SENSITIVE = "password_case";
  
  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_PASSWORD_ID = "password_and_id";
  
  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_LOCK = "user_lock";
  
  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_EXPIRE = "user_expire";
  
 // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_LOCK_TIMES = "lock_times_count"; 

  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_DAYS_EXPIRED_LOGIN = "Days_expired_login";

  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_LAST_PASSWORD_COUNT = "last_password_count";

  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_PASSWORD_LENGTH_PROP = "password_length_count";

   // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_MINIMUM_OPTIONAL = "minimum_optional"; 

 // Key for the select control name prefix
  public static final String CONTROL_BUTTON_NAME_UPDATE_SECURITY = "update_security";  
}

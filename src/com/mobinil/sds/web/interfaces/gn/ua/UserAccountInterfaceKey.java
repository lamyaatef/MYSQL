package com.mobinil.sds.web.interfaces.gn.ua;

/**
 * UserAccountInterfaceKey interface holding all the keys used in the User
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


public interface UserAccountInterfaceKey 
{

////////////////////////////////////////////////////////////////////////
/*                      Returned Data HashMap Keys 
*////////////////////////////////////////////////////////////////////////

  public static final String ACTION_REDIRECT_TO_LOGIN_PAGE = "redirect_to_login_page";

// Key for the list all users action
  public static final String ACTION_SHOW_LOGIN_PAGE = "show_login_page";

// Key to get DCM request
  public static final String ACTION_GET_DCM_REQUEST = "get_dcm_request";
  
// Key for the list all users action
  public static final String ACTION_LOGIN = "login";

  // Key for the list all users action
  public static final String ACTION_CONFIRMATION = "confirmation";

// Key for the list all users action
  public static final String ACTION_RELOGIN = "relogin";

// Key for the list all users action
  public static final String ACTION_LOGOUT = "logout";

// Key for the update users action
  public static final String ACTION_SHOW_USER_MAIN = "show_user_main";

// Key for the update users action
  public static final String ACTION_SHOW_USER_ROLES = "change_current_role";

// Key for the update users action
  public static final String ACTION_CHANGE_CURRENT_ROLE = "change_current_role";
  
// Key for the update users action
  public static final String ACTION_SHOW_ROLE_PRIVILEGES_MAIN = "show_role_privileges_main";

// Key for the update users action
  public static final String ACTION_SHOW_ROLE_PRIVILEGES = "show_role_privileges";

// Key for the update privilege action
  public static final String ACTION_CHANGE_PASSWORD = "change_password";

// Key for the update privilege action
  public static final String ACTION_SAVE_NEW_PASSWORD = "save_new_password";

// Key for the update privilege action
  public static final String ACTION_FORGOT_PASSWORD = "forgot_password";

// Key for the update privilege action
  public static final String ACTION_GO_TO_RENEW_PASSWORD = "go_to_new_password";

// Key for the update privilege action
  public static final String ACTION_GO_TO_ACTIVATION = "go_to_activation";

  // Key for the update privilege action
  public static final String ACTION_CHECK_ACTIVATION = "check_activation";

// Key for the update privilege action
  public static final String ACTION_RESEND_PASSWORD = "resend_password";

// Key for the update privilege action
  public static final String ACTION_NEW_PASSWORD = "create_new_password";

/////////////////////////////////////////CONTROLS///////////////////////////////
// Key for the select control name prefix
  public static final String CONTROL_SELSCT_NAME_PREFIX = "select_";

// Key for the select control name prefix
  public static final String CONTROL_CHECKBOX_NAME_PREFIX = "checkbox_";

// Key for the select control name prefix
  public static final String CONTROL_HIDDEN_NAME_USER_ID = "user_account_id";

// Key of the server name
  public static final String CONTROL_HIDDEN_SERVER_NAME = "server_name";
  
// Key of the server port
  public static final String CONTROL_HIDDEN_SERVER_PORT = "server_port";
  
// Key of the context path
  public static final String CONTROL_HIDDEN_CONTEXT_PATH = "context_path";
  
// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_EMAIL = "user_account_email";

  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_CONFIRMATION_CODE= "user_confirmation_code";

  // Key for the select control name prefix
  public static final String CONTROL_HIDDEN_NAME_ACTIVATION_PAGE= "activation_page";
  
  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_NEW_PASSWORD= "user_account_new_password";

  // Key for the select control name prefix
  public static final String CONTROL_BUTTON_NAME_USER_CONFIRM_PASSWORD= "user_account_confirm_password";

   // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_CONFIRM_PASSWORD= "code";

// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_LOGIN = "user_account_login";

// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_PASSWORD = "user_account_password";

// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_OLD_USER_PASSWORD = "old_user_password";

// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_NEW_USER_PASSWORD = "new_user_password";

  // Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_CHECK_CODE = "check_activation_code";

// Key for the select control name prefix
  public static final String CONTROL_HIDDEN_PRIVILEGE_ACTION_NAME = "privilege_action_name";

}
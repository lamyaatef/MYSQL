package com.mobinil.sds.web.interfaces.sa;

/**
 * UserInterfaceKey interface holding all the keys used in the User
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

public interface UserInterfaceKey 
{
public static final String CACH_OBJ_USER_LOCK = "cach_obj_user_lock";
public static final String CACH_OBJ_USER_STATUS = "cach_obj_user_status";
public static final String CACH_OBJ_USER_LIST = "cach_obj_user_list";
////////////////////////////////////////////////////////////////////////
/*                      Returned Data HashMap Keys 
*////////////////////////////////////////////////////////////////////////

// Key for the list all roles action
  public static final String HASHMAP_KEY_USER_STATUSES_LIST = "user_statuses_list";

// Key for the list all roles action
  public static final String  HASHMAP_KEY_USER_MAX_LOCK = "max_lock_times";
  
  // Key for the list all roles action
  public static final String HASHMAP_KEY_USER_LOCK_LIST = "user_lock_list";

// Key for the list all roles action
  public static final String HASHMAP_KEY_ROLES_LIST = "roles_list";

// Key for the list all DCMs action
  public static final String HASHMAP_KEY_DCMS_LIST = "dcms_list";

//key for the list all reports actions 
  public static final String HASHMAP_KEY_REPORT_LIST = "report_list";

  public static final String HASHMAP_KEY_REPORT_USERLIST = "report_user_list";

/////////////////////////////////////////ACTIONS///////////////////////////////

// Key for the list all users action
  public static final String ACTION_LIST_USERS = "list_users";

// Key for the update users action
  public static final String ACTION_UPDATE_USERS_STATUS = "update_users_status";

// Key for the update privilege action
  public static final String ACTION_UPDATE_USER_ROLES = "update_user_roles";

// Key for the update privilege action
  public static final String ACTION_SHOW_USER_ROLE_PRIVILEGES = "show_user_role_privileges";

// Key for the update privilege action
  public static final String ACTION_NEW_USER = "new_user";

// Key for the update privilege action
  public static final String ACTION_EDIT_USER = "edit_user";

// Key for the update privilege action
  public static final String ACTION_ADD_USER = "add_user";

// Key for the update privilege action
  public static final String ACTION_UPDATE_USER = "update_user";


  

/////////////////////////////////////////CONTROLS///////////////////////////////

// Key for the select control name prefix
  public static final String CONTROL_SELSCT_NAME_PREFIX = "select_";

  // Key for the select control name prefix
  public static final String CONTROL_SELSCT_LOCK_PREFIX = "select_lock_";

// Key for the select control name prefix
  public static final String CONTROL_CHECKBOX_NAME_PREFIX = "checkbox_";

// Key for the select control name prefix
  public static final String CONTROL_BUTTON_NAME_EDIT_USER_PREFIX = "edit_user_";

// Key for the select control name prefix
  public static final String CONTROL_HIDDEN_NAME_USER_ID = "user_id";

// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_NAME = "user_name";

// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_EMAIL = "user_email";

// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_ADDRESS = "user_address";

// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_USER_LOGIN = "user_login";

  public static final String CONTROL_HIDDEN_Report_ID ="report_id";
  public static final String CONTROL_TEXT_Report_NAME ="report_name";
  


  

}
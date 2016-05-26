package com.mobinil.sds.web.interfaces.sa;

/**
 * RoleInterfaceKey interface holding all the keys used in the Role
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

public interface RoleInterfaceKey 
{

public static final String CACH_OBJ_ROLE_STATUS = "cach_obj_role_status";
public static final String CACH_OBJ_ROLE_LIST = "cach_obj_role_list";
////////////////////////////////////////////////////////////////////////
/*                      Returned Data HashMap Keys 
*////////////////////////////////////////////////////////////////////////
// Key for the list all roles action
  public static final String HASHMAP_KEY_ROLE_STATUSES_LIST = "role_statuses_list";

// Key for the list all roles action
  public static final String HASHMAP_KEY_MODULES_LIST = "modules_list";

/////////////////////////////////////////ACTIONS///////////////////////////////

// Key for the list all roles action
  public static final String ACTION_LIST_ROLES = "list_roles";

// Key for the update roles action
  public static final String ACTION_UPDATE_ROLES_STATUS = "update_roles_status";

// Key for the update privilege action
  public static final String ACTION_UPDATE_ROLE_PRIVILEGES = "update_role_privileges";

// Key for the update privilege action
  public static final String ACTION_NEW_ROLE = "new_role";

// Key for the update privilege action
  public static final String ACTION_EDIT_ROLE = "edit_role";

// Key for the update privilege action
  public static final String ACTION_ADD_ROLE = "add_role";

// Key for the update privilege action
  public static final String ACTION_UPDATE_ROLE = "update_role";

/////////////////////////////////////////CONTROLS///////////////////////////////

// Key for the select control name prefix
  public static final String CONTROL_SELSCT_NAME_PREFIX = "select_";

// Key for the select control name prefix
  public static final String CONTROL_CHECKBOX_NAME_PREFIX = "checkbox_";

// Key for the select control name prefix
  public static final String CONTROL_BUTTON_NAME_EDIT_ROLE_PREFIX = "edit_role_";

// Key for the select control name prefix
  public static final String CONTROL_HIDDEN_NAME_ROLE_ID = "role_id";

// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_ROLE_NAME = "role_name";

// Key for the select control name prefix
  public static final String CONTROL_TEXT_NAME_ROLE_DESCRIPTION = "role_description";

}
package com.mobinil.sds.core.facade.handlers;

/**
 * RoleHandlerEJBBean Statless Sesion Bean.
 * It handles all role related actions. 
 * 
 * handle(String action, HashMap paramHashMap)
 * According to the action it calls the concerned Data Access Object 
 * or a CMP and return the data Hash Map with the returned data.
 * 
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import com.mobinil.sds.core.system.sa.roles.dao.*;
//import com.mobinil.sds.core.system.sa.roles.dao.cmp.*;
import com.mobinil.sds.core.system.sa.roles.model.*;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.sa.*;

import com.mobinil.sds.core.utilities.CachEngine;
import java.sql.*;

import java.util.HashMap;



public class RoleHandler  
{
  //////////////////Static ints used to switch on the actions.//////////////////
  static final int LIST_ALL_ROLES = 1;

  static final int UPDATE_ROLES_STATUS = 2;

  static final int UPDATE_ROLE_PRIVILEGES = 3;

  static final int NEW_ROLE = 4;
  
  static final int EDIT_ROLE = 5;
  
  static final int ADD_ROLE = 6;
  
  static final int UPDATE_ROLE = 7;
  




  /**
   * handle method:
   * 
   * According to the action it calls the concerned Data Access Object
   * or a CMP and return the data Hash Map with the returned data.
   * 
   * @param	String action, HashMap paramHashMap
   * @return HashMap
   * @throws  
   * @see    
  */	

  public static HashMap<String,Object> handle(String action, HashMap paramHashMap,java.sql.Connection sdsCon)
  {
    int actionType = 0;
    HashMap<String,Object> dataHashMap = new HashMap<String,Object>(100);
    String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    if(strUserID != null && strUserID.compareTo("") != 0)
    {
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);           
    }
    try
    {
      
      if(action.equals(RoleInterfaceKey.ACTION_LIST_ROLES))
      {
        actionType = LIST_ALL_ROLES;
      }
      else if(action.equals(RoleInterfaceKey.ACTION_UPDATE_ROLES_STATUS))
      {
        actionType = UPDATE_ROLES_STATUS;
      }
      else if(action.equals(RoleInterfaceKey.ACTION_UPDATE_ROLE_PRIVILEGES))
      {
        actionType = UPDATE_ROLE_PRIVILEGES;
      }
      else if(action.equals(RoleInterfaceKey.ACTION_NEW_ROLE))
      {
        actionType = NEW_ROLE;
      }
      else if(action.equals(RoleInterfaceKey.ACTION_EDIT_ROLE))
      {
        actionType = EDIT_ROLE;
      }
      else if(action.equals(RoleInterfaceKey.ACTION_ADD_ROLE))
      {
        actionType = ADD_ROLE;
      }
      else if(action.equals(RoleInterfaceKey.ACTION_UPDATE_ROLE))
      {
        actionType = UPDATE_ROLE;
      }
      switch (actionType) 
      {
        case LIST_ALL_ROLES:
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, RoleDAO.getRolesList(sdsCon));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, RoleDAO.getAdditionalData(sdsCon));
        }
        break;
        case UPDATE_ROLES_STATUS:
        {
          int nRoleID = 0;
          int nRoleStatusID = 0;
          CachEngine.removeObject(RoleInterfaceKey.CACH_OBJ_ROLE_LIST);
          for(int j=0; j<paramHashMap.size(); j++)
          {
            String tempString = (String)paramHashMap.keySet().toArray()[j];
            if(tempString.startsWith(RoleInterfaceKey.CONTROL_SELSCT_NAME_PREFIX))
            {
              nRoleID = new Integer(tempString.substring(
                                          tempString.lastIndexOf(
                                            RoleInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7)).intValue();
                                            
              nRoleStatusID = new Integer((String)paramHashMap.get(tempString)).intValue();
              String strStatusName = RoleDAO.getRoleStatusName(sdsCon, nRoleStatusID);
              //RoleCMPHome roleCMPHome = (RoleCMPHome)Utility.getEJBHome("RoleCMP",
              //"com.mobinil.sds.core.system.sa.roles.dao.cmp.RoleCMPHome");
              //RoleCMP roleCMPRemote = roleCMPHome.findByPrimaryKey(new Long(nRoleID));
              RoleModel roleModel = RoleDAO.getAdmRole(sdsCon,nRoleID+"");
              
              //if(roleCMPRemote.getRole_status_name().compareTo(strStatusName) != 0)
              if(roleModel.getRoleStatusName().compareTo(strStatusName) != 0)
              {
                if(strStatusName.equals("Delete"))
                {
                  if(! RoleDAO.hasUsers(sdsCon, nRoleID))
                  {
                    RoleDAO.deleteRolePrivileges(sdsCon, nRoleID);
                    //roleCMPRemote.remove();
                    RoleDAO.deleteAdmRole(sdsCon, nRoleID);
                  }
                  else
                  {
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "You can not delete a role which is assigned to a user.");
                  }
                }
                else
                {
                  //roleCMPRemote.setRole_status_id(new Long(nRoleStatusID));
                }
              }
            }
          }
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, RoleDAO.getRolesList(sdsCon));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, RoleDAO.getAdditionalData(sdsCon));
        }
        break;
        case UPDATE_ROLE_PRIVILEGES:
        {
          int nRoleID = new Integer((String)paramHashMap.get(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID)).intValue();
          RoleDAO.deleteRolePrivileges(sdsCon, nRoleID);
          
          for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempString = (String)paramHashMap.keySet().toArray()[j];
              if(tempString.startsWith(RoleInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX))
              {
                int nPrivilegeID = new Integer(tempString.substring(
                                      tempString.lastIndexOf(
                                        RoleInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX)+9)).intValue();
                //RolePrivilegeCMPHome rolePrivilegeCMPHome = 
                //  (RolePrivilegeCMPHome)Utility.getEJBHome("RolePrivilegeCMP",
                //  "com.mobinil.sds.core.system.sa.roles.dao.cmp.RolePrivilegeCMPHome");
                //RolePrivilegeCMP rolePrivilegeCMPRemote = rolePrivilegeCMPHome.create(new RolePrivilegeModel(nRoleID, nPrivilegeID));
                RoleDAO.insertAdmRolePrivilage(sdsCon,nRoleID, nPrivilegeID);
              }
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, RoleDAO.getRolesList(sdsCon));
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, RoleDAO.getAdditionalData(sdsCon));
          
        }
        break;
        case EDIT_ROLE:
        {
          RoleModel newRoleModel = RoleDAO.getAdmRole(sdsCon,(String)paramHashMap.get(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newRoleModel);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, RoleDAO.getRoleStatuses(sdsCon));
        }
        break;
        case UPDATE_ROLE:
        {
          CachEngine.removeObject(RoleInterfaceKey.CACH_OBJ_ROLE_LIST);
          int nRoleID = new Integer((String)paramHashMap.get(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID)).intValue();
          Long lRoleID = new Long((long)nRoleID);
          String strRoleName = (String)paramHashMap.get(RoleInterfaceKey.CONTROL_TEXT_NAME_ROLE_NAME);
          String strRoleDesc = (String)paramHashMap.get(RoleInterfaceKey.CONTROL_TEXT_NAME_ROLE_DESCRIPTION);
          int nRoleStatusID = new Integer((String)paramHashMap.get(RoleInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+nRoleID)).intValue();
          RoleModel newRoleModel = new RoleModel(nRoleID, strRoleName, strRoleDesc, nRoleStatusID);
          //RoleCMPHome roleCMPHome = (RoleCMPHome)Utility.getEJBHome("RoleCMP",
          //"com.mobinil.sds.core.system.sa.roles.dao.cmp.RoleCMPHome");
          //RoleCMP roleCMPRemote = roleCMPHome.findByPrimaryKey(lRoleID);  
          try 
          {
            //roleCMPRemote.updateRole(newRoleModel);
            RoleDAO.updateAdmRole(sdsCon,nRoleID+"",strRoleName,strRoleDesc,nRoleStatusID+"");
          }
          catch(Exception e)
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Another Role with the same name already exists");
          }
          finally
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, RoleDAO.getRolesList(sdsCon));
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, RoleDAO.getAdditionalData(sdsCon));
          }
        }
        break;
        case NEW_ROLE:
        {
        }
        break;
        case ADD_ROLE:
        {
          CachEngine.removeObject(RoleInterfaceKey.CACH_OBJ_ROLE_LIST);
          Long lRoleID = Utility.getSequenceNextVal(sdsCon, "SEQ_ADM_ROLE_ID");
          int nRoleID = lRoleID.intValue();
          String strRoleName = (String)paramHashMap.get(RoleInterfaceKey.CONTROL_TEXT_NAME_ROLE_NAME);
          String strRoleDesc = (String)paramHashMap.get(RoleInterfaceKey.CONTROL_TEXT_NAME_ROLE_DESCRIPTION);
          RoleModel newRoleModel = new RoleModel(nRoleID, strRoleName, strRoleDesc);
          //RoleCMPHome roleCMPHome = (RoleCMPHome)Utility.getEJBHome("RoleCMP",
          //"com.mobinil.sds.core.system.sa.roles.dao.cmp.RoleCMPHome");
          //RoleCMP roleCMPRemote =  null;
          try 
          {
            //roleCMPRemote = roleCMPHome.create(newRoleModel);
            RoleDAO.insertAdmRole(sdsCon,nRoleID+"",strRoleName,strRoleDesc,"1");
          }
          catch(Exception e)
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Another Role with the same name already exists");
          }
          finally
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, RoleDAO.getRolesList(sdsCon));
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, RoleDAO.getAdditionalData(sdsCon));
          }
        }
        break;
      }
      
    }
    catch(Exception objExp)
    {
      objExp.printStackTrace();
    }
    return dataHashMap;
  }
}
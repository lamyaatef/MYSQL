package com.mobinil.sds.core.facade.handlers;

/**
 * UserHandlerEJBBean Statless Sesion Bean.
 * It handles all user related actions. 
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
import com.mobinil.sds.core.system.sa.persons.model.*;
import com.mobinil.sds.core.system.sa.users.dao.*;
import com.mobinil.sds.core.system.sa.users.dto.*;
import com.mobinil.sds.core.system.security.dao.securityDAO;
import com.mobinil.sds.core.system.sa.users.model.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.web.interfaces.gn.ua.*;


import java.util.HashMap;
import java.util.Vector;




public class UserHandler 
{
  //////////////////Static ints used to switch on the actions.//////////////////
  static final int LIST_ALL_USERS = 1;

  static final int UPDATE_USERS_STATUS = 2;

  static final int SHOW_USER_ROLE_PRIVILEGES = 3;

  static final int UPDATE_USER_ROLES = 4;

  static final int NEW_USER = 5;
  
  static final int EDIT_USER = 6;
  
  static final int ADD_USER = 7;
  
  static final int UPDATE_USER = 8;
  
  
 
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

  public static HashMap handle(String action, HashMap paramHashMap,java.sql.Connection sdsCon)
  {
    int actionType = 0;
    HashMap dataHashMap = new HashMap(100);
    String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    if(strUserID != null && strUserID.compareTo("") != 0)
    {
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);           
    }
    
    try
    {
       

      if(action.equals(UserInterfaceKey.ACTION_LIST_USERS))
      {
        actionType = LIST_ALL_USERS;
      }
      if(action.equals(UserInterfaceKey.ACTION_UPDATE_USERS_STATUS))
      {
        actionType = UPDATE_USERS_STATUS;
      }
      if(action.equals(UserInterfaceKey.ACTION_SHOW_USER_ROLE_PRIVILEGES))
      {
        actionType = SHOW_USER_ROLE_PRIVILEGES;
      }
      if(action.equals(UserInterfaceKey.ACTION_UPDATE_USER_ROLES))
      {
        actionType = UPDATE_USER_ROLES;
      }
      if(action.equals(UserInterfaceKey.ACTION_NEW_USER))
      {
        actionType = NEW_USER;
      }
      if(action.equals(UserInterfaceKey.ACTION_EDIT_USER))
      {
        actionType = EDIT_USER;
      }
      if(action.equals(UserInterfaceKey.ACTION_ADD_USER))
      {
        actionType = ADD_USER;
      }
      if(action.equals(UserInterfaceKey.ACTION_UPDATE_USER))
      {
        actionType = UPDATE_USER;
      }
      switch (actionType) 
      {
        case LIST_ALL_USERS:
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(sdsCon));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, UserDAO.getAdditionalData(sdsCon));
          dataHashMap.put(UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK,  UserDAO.getLockTime(sdsCon));
        }
        break;
        case UPDATE_USERS_STATUS:
        {
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          int userID = 0;
          int userStatusID = 0;
          int lockId=0;
          int userLockId=0;
          String strStatusName="";
          String strLockName="";
          Integer maxLockTime = UserDAO.getLockTime(sdsCon);
          Vector vecLockNames = UserDAO.getUserLock(sdsCon);
          for(int j=0; j<paramHashMap.size(); j++)
          {
            String tempString = (String)paramHashMap.keySet().toArray()[j];
            if(tempString.startsWith(UserInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)||tempString.startsWith(UserInterfaceKey.CONTROL_SELSCT_LOCK_PREFIX))
            {
            boolean lock=false;
            if (tempString.startsWith(UserInterfaceKey.CONTROL_SELSCT_LOCK_PREFIX)){
            lock=true;            
            }
            if (lock){
              userID = new Integer(tempString.substring(
                                          tempString.lastIndexOf(
                                            UserInterfaceKey.CONTROL_SELSCT_LOCK_PREFIX)+12)).intValue();

            System.out.println("tempString in update lock is "+tempString);                                              
            lockId = new Integer((String)paramHashMap.get(tempString)).intValue();                                            
              if (lockId==2)
              {
              UserLockModel tempULM = (UserLockModel) vecLockNames.get(1);
              strLockName = tempULM.getUserLockName();
              }
              else
              {
              UserLockModel tempULM = (UserLockModel) vecLockNames.get(0);
              strLockName = tempULM.getUserLockName();
              }
            }
            else
            {
              userID = new Integer(tempString.substring(
                                          tempString.lastIndexOf(
                                            UserInterfaceKey.CONTROL_SELSCT_NAME_PREFIX)+7)).intValue();
              System.out.println("tempString in update status is "+tempString);                                                     
              userStatusID = new Integer((String)paramHashMap.get(tempString)).intValue();
              strStatusName = UserDAO.getUserStatusName(sdsCon, userStatusID);
            }
//              System.out.println("update users "+paramHashMap.get(tempString));


           
              //UserCMPHome userCMPHome = (UserCMPHome)Utility.getEJBHome("UserCMP", 
              //          "com.mobinil.sds.core.system.sa.users.dao.cmp.UserCMPHome");
              //UserCMP userCMPRemote = userCMPHome.findByPrimaryKey(new Long(userID));

              Vector userVector = UserDAO.getUsersListByUserId(userID+"");
              UserDTO newUserDTO = (UserDTO)userVector.get(0);
              UserModel userModel = newUserDTO.getUserModel();

              if (lock){
              String strUserLock = userModel.getM_isLocked(); 
              int userLockCount = new Integer(strUserLock).intValue();
                if (userLockCount<=maxLockTime.intValue())
                {
                UserLockModel tempULM = (UserLockModel) vecLockNames.get(1);
                strUserLock = tempULM.getUserLockName();
                }
                else
                {
                UserLockModel tempULM = (UserLockModel) vecLockNames.get(0);
                strUserLock = tempULM.getUserLockName();
                }
              if (strUserLock.compareTo(strLockName)!=0){
                if (strLockName.compareTo("LOCKED")==0)
                {
                  userLockId = maxLockTime.intValue()+1;
                }
                else{
                  userLockId=0;
                }
              UserDAO.updateUserStatus(sdsCon,userID,userLockId,lock);
              lock=false;
              }
    
             
              }
              
              else
              {
              String strUserStatusName = userModel.getUserStatusName();
              //if(userCMPRemote.getUser_status_name().compareTo(strStatusName) != 0)
              if(strUserStatusName.compareTo(strStatusName) != 0)
              {
           
              if(strStatusName.compareTo("Delete")==0)
                {
                  UserDAO.deleteUserRoles(sdsCon, userID);
                  UserDAO.deleteContractRegisterationUserDCMs(sdsCon, userID);
                  UserDAO.deleteAuthenticationCallUserDCMs(sdsCon, userID);
                  
                }
               
                UserDAO.updateUserStatus(sdsCon,userID,userStatusID,lock);
              }
                  //userCMPRemote.setUser_status_id(new Long(userStatusID));
                
              }
              lock= false;
            }
            
          }
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(sdsCon));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, UserDAO.getAdditionalData(sdsCon));
          dataHashMap.put(UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK,  UserDAO.getLockTime(sdsCon));


        }
        break;
        case SHOW_USER_ROLE_PRIVILEGES:
        {
          int userID = new Integer((String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID)).intValue();
          int nRoleID = new Integer((String)paramHashMap.get(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID)).intValue();
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUserRolePrivileges(sdsCon, nRoleID));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, paramHashMap.get(RoleInterfaceKey.CONTROL_TEXT_NAME_ROLE_NAME));
        }
        break;
        case UPDATE_USER_ROLES:
        {
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          int userID = new Integer((String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID)).intValue();
          if(UserDAO.deleteUserRoles(sdsCon, userID))
          {
            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempString = (String)paramHashMap.keySet().toArray()[j];
              if(tempString.startsWith(UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX))
              {
                int nRoleID = new Integer(tempString.substring(
                                      tempString.lastIndexOf(
                                        UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX)+9)).intValue();
                //UserRoleCMPHome userRoleCMPHome = 
                //  (UserRoleCMPHome)Utility.getEJBHome("UserRoleCMP",
                //  "com.mobinil.sds.core.system.sa.users.dao.cmp.UserRoleCMPHome");
                //UserRoleCMP userRoleCMPRemote = userRoleCMPHome.create(new UserRoleModel(userID, nRoleID));
                UserDAO.insertUserRole(sdsCon,userID,nRoleID);
              }
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(sdsCon));
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, UserDAO.getAdditionalData(sdsCon));
            dataHashMap.put(UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK,  UserDAO.getLockTime(sdsCon));
          }
        }
        break;
        case EDIT_USER:
        {
          System.out.println("EDITTTTTTTTTT");
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          //UserCMPHome userCMPHome = (UserCMPHome)Utility.getEJBHome("UserCMP", 
          //          "com.mobinil.sds.core.system.sa.users.dao.cmp.UserCMPHome");
          //UserCMP userCMPRemote = userCMPHome.findByPrimaryKey(
          //                            new Long((String)paramHashMap.get(
          //                                UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID)));

          String strHiddenNameUserId = (String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID);
          Vector userVector = UserDAO.getUsersListByUserId(strHiddenNameUserId+"");
          UserDTO newUserDTO = (UserDTO)userVector.get(0);
          UserModel newUserModel = newUserDTO.getUserModel();
          PersonModel newPersonModel = newUserModel.getPersonModel();    
          
          /*int userID = userCMPRemote.getUser_id().intValue();
          int userStatusID = userCMPRemote.getUser_status_id().intValue();
          String strUserStatusName = userCMPRemote.getUser_status_name();
          PersonModel newPersonModel = new PersonModel(userID,
                                                      userCMPRemote.getPerson_full_name(),
                                                      userCMPRemote.getPerson_address(),
                                                      userCMPRemote.getPerson_status_id().intValue(),
                                                      userCMPRemote.getPerson_status_name(),
                                                      userCMPRemote.getPerson_type_id().intValue(),
                                                      userCMPRemote.getPerson_type_name(),
                                                      userCMPRemote.getPerson_type_status_id().intValue(),
                                                      userCMPRemote.getPerson_type_status_name(),
                                                      userCMPRemote.getPerson_email());
          UserModel newUserModel = new UserModel(newPersonModel, userStatusID, strUserStatusName);
          */
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newUserModel);
          dataHashMap.put(UserInterfaceKey.HASHMAP_KEY_USER_LOCK_LIST, UserDAO.getUserLock(sdsCon));
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, UserDAO.getUserStatuses(sdsCon));
          dataHashMap.put(UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK,  UserDAO.getLockTime(sdsCon));
        }
        break;
        case UPDATE_USER:
        {
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          int nPersonID = new Integer((String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID)).intValue();
          Long lPersonID = new Long((long)nPersonID);
          String serverName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME);
          String serverPort = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT);
          String appName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH);

          String strPersonName = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_NAME);
          String strPersonAddress = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_ADDRESS);
          String strPersonEmail = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL);
          
         // int userStatusID = new Integer((String)paramHashMap.get(UserInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+nPersonID)).intValue();
      //    System.out.println("User Status issssssssssssss " + userStatusID );

          //PersonModel newPersonModel = new PersonModel(nPersonID, strPersonName, strPersonAddress, strPersonEmail);
          PersonModel newPersonModel = UserDAO.getPerson(sdsCon,nPersonID);
          
          UserModel newUserModel = null;
          //UserCMPHome userCMPHome = (UserCMPHome)Utility.getEJBHome("UserCMP", 
          //          "com.mobinil.sds.core.system.sa.users.dao.cmp.UserCMPHome");
          //UserCMP userCMPRemote = userCMPHome.findByPrimaryKey(lPersonID);
          //Utility.logger.debug("Old pass -->"+userCMPRemote.getPassword());
          Vector userVector = UserDAO.getUsersListByUserId(nPersonID+"");
          UserDTO newUserDTO = (UserDTO)userVector.get(0);
          UserModel newUserModelX = newUserDTO.getUserModel();
          PersonModel newPersonModelX = newUserModelX.getPersonModel();    
          try 
          {
            //if(userCMPRemote.getPerson_email().compareToIgnoreCase(strPersonEmail) !=0)
            if(newPersonModelX.getPersonEMail().compareToIgnoreCase(strPersonEmail) !=0)
            {
              String strUserPassword = Math.random()+"";
              strUserPassword = strUserPassword.substring(strUserPassword.lastIndexOf(".")+1,strUserPassword.lastIndexOf(".")+7);
              Utility.logger.debug("Password"+strUserPassword);
              String strEncryptedUserPassword = EncryptUtil.encryptIt(strUserPassword + strPersonEmail);
              newUserModel = UserDAO.getUser(sdsCon,nPersonID,newPersonModel);
              Utility.sendPasswordByMail(serverName, serverPort, appName, strPersonEmail, strPersonName, strUserPassword);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, 
                              "User Data was saved successfully .The new password will be sent to the new email provided.");
            }
            else
            {
              newUserModel = UserDAO.getUser(sdsCon,nPersonID,newPersonModel);
            }
            //userCMPRemote.updateUser(newUserModel);
            int user_id = newPersonModel.getPersonID();
            String user_login = newPersonModel.getPersonEMail();
            String password = newUserModel.getUserPassword();
            int user_status_id = newUserModel.getUserStatusID();
            String user_status_name = newUserModel.getUserStatusName();
            String person_full_name = newPersonModel.getPersonFullName();
            String person_address = newPersonModel.getPersonAddress();
            int person_status_id = newPersonModel.getPersonStatusID();
            String person_status_name = newPersonModel.getPersonStatusName();
            int person_type_id = newPersonModel.getPersonTypeID();
            String person_type_name = newPersonModel.getPersonTypeName();
            int person_type_status_id = newPersonModel.getPersonTypeStatusID();
            String person_type_status_name = newPersonModel.getPersonTypeStatusName();            
            int maxLockTime = (Integer.parseInt((String)paramHashMap.get(UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK)));
            int lockId = new Integer((String)paramHashMap.get(UserInterfaceKey.CONTROL_SELSCT_LOCK_PREFIX+user_id)).intValue();                                            
            int userLockId=0;
            if (lockId==1)
                {
                  userLockId = maxLockTime+1;
                }
                else{
                  userLockId=0;
                }
            int userStatusID = new Integer((String)paramHashMap.get(UserInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+user_id)).intValue();
            UserDAO.updateVwGenUserDetails(user_id,strPersonEmail,password,userStatusID,user_status_name,strPersonName,strPersonAddress,person_status_id,person_status_name,person_type_id,person_type_name,person_type_status_id,person_type_status_name,userLockId);
            //Utility.logger.debug("New pass -->"+userCMPRemote.getPassword());
          } 
          catch (Exception ex) 
          {
          ex.printStackTrace();
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Another User with the same E-Mail already exists");
          } 
          finally 
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(sdsCon));
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, UserDAO.getAdditionalData(sdsCon));
            dataHashMap.put(UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK,  UserDAO.getLockTime(sdsCon));
          }
        }
        break;
        case NEW_USER:
        {
            System.out.println("new user action");
        }
        break;
        case ADD_USER:
        {
          System.out.println("add user action");
          CachEngine.removeObject(UserInterfaceKey.CACH_OBJ_USER_LIST);
          Long lPersonID = Utility.getSequenceNextVal(sdsCon, "SEQ_GEN_PERSON_ID");
          int nPersonID = lPersonID.intValue();
          String serverName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME);
          String serverPort = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT);
          String appName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH);

          String strPersonName = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_NAME);
          String strPersonAddress = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_ADDRESS);
          String strPersonEmail = (String)paramHashMap.get(UserInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL);
          String strUserPassword = Math.random()+"";
          strUserPassword = strUserPassword.substring(strUserPassword.lastIndexOf(".")+1,strUserPassword.lastIndexOf(".")+7);
          Utility.logger.debug("The password isssssssssssssssss : " + strUserPassword);
          PersonModel newPersonModel = new PersonModel(nPersonID, strPersonName,
                                                       strPersonAddress, strPersonEmail);
          String strEncryptedUserPassword = EncryptUtil.encryptIt(strUserPassword + strPersonEmail);
          String isLocked="0";
          
          String expireDate="sysDate+"+securityDAO.getProps(sdsCon,"DAYS_FOR_EXPIRED_LOGIN");
          UserModel newUserModel = new UserModel(newPersonModel, strEncryptedUserPassword,isLocked,expireDate);
          //UserCMPHome userCMPHome = (UserCMPHome)Utility.getEJBHome("UserCMP", "com.mobinil.sds.core.system.sa.users.dao.cmp.UserCMPHome");
          //UserCMP userCMPRemote = null;
          try 
          {
            //userCMPRemote = userCMPHome.create(newUserModel);
            int user_id = newPersonModel.getPersonID();
            String user_login = newPersonModel.getPersonEMail();
            String password = newUserModel.getUserPassword();
            isLocked = newUserModel.getM_isLocked();
            expireDate = newUserModel.getM_expire();
            //int user_status_id = newUserModel.getUserStatusID();
            int user_status_id = 1;
            String user_status_name = newUserModel.getUserStatusName();
            String person_full_name = newPersonModel.getPersonFullName();
            String person_address = newPersonModel.getPersonAddress();
            //int person_status_id = newPersonModel.getPersonStatusID();
            int person_status_id = 1;
            String person_status_name = newPersonModel.getPersonStatusName();
            //int person_type_id = newPersonModel.getPersonTypeID();
            int person_type_id = 1;
            String person_type_name = newPersonModel.getPersonTypeName();
            //int person_type_status_id = newPersonModel.getPersonTypeStatusID();
            int person_type_status_id = 1;
            String person_type_status_name = newPersonModel.getPersonTypeStatusName();
            UserDAO.insertVwGenUserDetails(sdsCon,user_id,user_login,password,user_status_id,
            user_status_name,person_full_name,
            person_address,person_status_id,
            person_status_name,person_type_id,
            person_type_name,person_type_status_id,
            person_type_status_name,isLocked,expireDate);

            Utility.logger.debug("Password "+strUserPassword);
//          Utility.sendPasswordByMail(serverName, serverPort, appName, strPersonEmail, strPersonName, strUserPassword);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "User password was sent to the email provided.");
          } 
          catch (Exception ex) 
          {
            ex.printStackTrace();
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Another User with the same E-Mail already exists");
          } 
          finally 
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(sdsCon));
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, UserDAO.getAdditionalData(sdsCon));
            dataHashMap.put(UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK,  UserDAO.getLockTime(sdsCon));
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
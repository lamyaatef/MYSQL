package com.mobinil.sds.core.facade.handlers;

/**
 * UserAccountHandlerEJBBean Statless Sesion Bean.
 * It handles all user account related actions. 
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
 
import com.mobinil.sds.core.system.gn.user.dao.*;
import com.mobinil.sds.core.system.sa.modules.dao.*;
import com.mobinil.sds.core.system.sa.roles.dto.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.gn.ua.*;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.core.utilities.SecurityUtils;

import java.sql.*;

import java.util.*;


public class UserAccountHandler  
{
  static final String LOGIN_PAGE = "0";

  static final String ACTIVATION_PAGE = "1";

  //////////////////Static ints used to switch on the actions.//////////////////
  static final int SHOW_LOGIN_PAGE = 1;

  static final int LOGIN = 2;

  static final int SHOW_USER_MAIN = 3;
  
  static final int CHANGE_CURRENT_ROLE = 4;

  static final int SHOW_USER_ROLES = 5;
  
  static final int SHOW_ROLE_PRIVILEGES = 6;
  
  static final int CHANGE_PASSWORD = 7;

  static final int SAVE_NEW_PASSWORD = 8;
  
  static final int FORGOT_PASSWORD = 9;
  
  static final int RESEND_PASSWORD = 10;
  
  static final int SHOW_ROLE_PRIVILEGES_MAIN = 11;

  static final int RELOGIN = 12;
  
  static final int NEW_PASSWORD = 13;

  static final int GO_TO_RENEW_PASSWORD = 14;

  static final int GO_TO_ACTIVATION = 15;
  
  static final int CHECK_ACTIVATION =16 ;

    static final int ACTIVATION =17 ;
  


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

  public static HashMap handle(String action, HashMap paramHashMap, Connection sdsCon)
  {
    int actionType = 0;
    HashMap dataHashMap = new HashMap(100);
    try
    {
    	     
      if(action.equals(UserAccountInterfaceKey.ACTION_SHOW_LOGIN_PAGE))
      {
        actionType = SHOW_LOGIN_PAGE;
      }
      if(action.equals(UserAccountInterfaceKey.ACTION_LOGIN))
      {
        actionType = LOGIN;
      }
      if(action.equals(UserAccountInterfaceKey.ACTION_RELOGIN))
      {
        actionType = RELOGIN;
      }
      if(action.equals(UserAccountInterfaceKey.ACTION_CHANGE_CURRENT_ROLE))
      {
        actionType = CHANGE_CURRENT_ROLE;
      }
      if(action.equals(UserAccountInterfaceKey.ACTION_SHOW_USER_ROLES))
      {
        actionType = SHOW_USER_ROLES;
      }
      if(action.equals(UserAccountInterfaceKey.ACTION_SHOW_ROLE_PRIVILEGES_MAIN))
      {
        actionType = SHOW_ROLE_PRIVILEGES_MAIN;
      }
      if(action.equals(UserAccountInterfaceKey.ACTION_SHOW_ROLE_PRIVILEGES))
      {
        actionType = SHOW_ROLE_PRIVILEGES;
      }
      if(action.equals(UserAccountInterfaceKey.ACTION_CHANGE_PASSWORD))
      {
        actionType = CHANGE_PASSWORD;
      }
      if(action.equals(UserAccountInterfaceKey.ACTION_SAVE_NEW_PASSWORD))
      {
        actionType = SAVE_NEW_PASSWORD;
      }
      if(action.equals(UserAccountInterfaceKey.ACTION_FORGOT_PASSWORD))
      {
        actionType = FORGOT_PASSWORD;
      }
      if(action.equals(UserAccountInterfaceKey.ACTION_RESEND_PASSWORD))
      {
        actionType = RESEND_PASSWORD;
      }
      if(action.equals(UserAccountInterfaceKey.ACTION_NEW_PASSWORD))
      {
        actionType = NEW_PASSWORD;
      }

     if(action.equals(UserAccountInterfaceKey.ACTION_GO_TO_RENEW_PASSWORD))
      {
        actionType = GO_TO_RENEW_PASSWORD;
      }

       if(action.equals(UserAccountInterfaceKey.ACTION_GO_TO_ACTIVATION))
      {
        actionType = GO_TO_ACTIVATION;
      }

       if(action.equals(UserAccountInterfaceKey.ACTION_CHECK_ACTIVATION))
      {
        actionType = CHECK_ACTIVATION;
      }
      
       if(action.equals(UserAccountInterfaceKey.ACTION_CONFIRMATION))
      {
        actionType = CHECK_ACTIVATION;
      }
      
      switch (actionType) 
      {
        case GO_TO_RENEW_PASSWORD:
        {
          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);         
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
        }
        break;
        case ACTIVATION:{
          System.out.println("in ACTIVATION");
        }
        break;
        
          case SHOW_LOGIN_PAGE:
          {
          }
          break;
        case LOGIN:
        {
          String strUserEmail = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL);
          String strUserPassword = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_PASSWORD);
          String strEncryptUserPassword = EncryptUtil.encryptIt(strUserPassword + strUserEmail);
          
          String strPassword = UserAccountDAO.getPassword(sdsCon, strUserEmail);
          if(strPassword != null)
          {
        
            if(EncryptUtil.comparePasswords(strEncryptUserPassword, strPassword))
            {

              String userID = UserAccountDAO.getUserID(sdsCon, strUserEmail, strPassword);
              String strSecurityErr = SecurityUtils.checkPassInLogin(sdsCon,strUserPassword,strEncryptUserPassword,strUserEmail,userID);

              
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, userID);
          
              if (strSecurityErr.compareTo("")!=0)
              {             
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP,"Current Password Does not Meet Security Rules");
              }
              UserAccountDAO.setDefaultLock(sdsCon,userID);

            }
            else
            {
            
              dataHashMap.put(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL, strUserEmail);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "You entered an invalid login information.");
              String userID = UserAccountDAO.getUserID(sdsCon, strUserEmail, strPassword);
              UserAccountDAO.setIncrementLock(sdsCon,userID);                        
            }
          }
          else
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Such account is not active or does not exist.");
          }
        }
        break;
        case RELOGIN:
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "You currently have no access rights to this privilege.");
        }
        break;
        case SHOW_USER_MAIN:
        {
        }
        break;
        case CHANGE_CURRENT_ROLE:
        {
          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          int nUserID = new Integer(strUserID).intValue();
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserAccountDAO.getUserDTO(sdsCon, nUserID));
        }
        break;
        case SHOW_USER_ROLES:
        {
          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          int nUserID = new Integer(strUserID).intValue();
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserAccountDAO.getUserDTO(sdsCon, nUserID));
        }
        break;
        case SHOW_ROLE_PRIVILEGES_MAIN:
        {
          String strRoleID = (String)paramHashMap.get(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID);
          dataHashMap.put(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID, strRoleID);
        }
        break;
        case SHOW_ROLE_PRIVILEGES:
        {
          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          int nUserID = new Integer(strUserID).intValue();
          String strRoleID = (String)paramHashMap.get(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID);
          int nRoleID = new Integer(strRoleID).intValue();
          RoleDTO newRoleDTO = UserAccountDAO.getUserRolePrivileges(sdsCon, nUserID, nRoleID);
          Vector rolePrivilegeVector = (Vector)newRoleDTO.getRolePrivileges();
          
          
          dataHashMap.put(RoleInterfaceKey.CONTROL_HIDDEN_NAME_ROLE_ID, strRoleID);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newRoleDTO);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, ModuleDAO.getModulesList(sdsCon));
          
        }
        break;
        case CHECK_ACTIVATION:
        {
          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);         
          System.out.println("user befor code checking "+strUserID);
          String strCode = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_CONFIRMATION_CODE);
          String strDbCode = UserAccountDAO.getActivationCode(sdsCon,strUserID);
          java.util.Date dtCodeExpire = UserAccountDAO.getExpireActivationCode(sdsCon,strUserID);
        if(strCode.compareTo(strDbCode)!=0)
          { 
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP,"Invalid Activation Code.");
          }
        else
            {
          if (dtCodeExpire.before(new java.util.Date())){
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Activation Code Expired.");
              }          
              else
              {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Correct Code.");
              }


            }
         
        }
        break;
        case CHANGE_PASSWORD:
        {
        }
        break;
        case SAVE_NEW_PASSWORD:
        {
          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          int nUserID = new Integer(strUserID).intValue();
          String strUserEmail = UserAccountDAO.getEmail(sdsCon, nUserID);
          String strOldPassword = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_TEXT_NAME_OLD_USER_PASSWORD);
          String strNewPassword = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_TEXT_NAME_NEW_USER_PASSWORD);
          
          strOldPassword = EncryptUtil.encryptIt(strOldPassword + strUserEmail);
          String strPassword = UserAccountDAO.getPassword(sdsCon, nUserID);
          if(strPassword != null)
          {
            if(EncryptUtil.comparePasswords(strOldPassword, strPassword))
            {
              String strNewPasswordEncripted = EncryptUtil.encryptIt(strNewPassword + strUserEmail);
              String strSecurityErr = SecurityUtils.checkPass(sdsCon,strNewPassword,strNewPasswordEncripted,strUserEmail,nUserID+"");
            if (strSecurityErr.compareTo("")!=0)
            {      
            UserAccountDAO.updateUserLastPass(sdsCon,nUserID+"",strNewPasswordEncripted);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, strSecurityErr);                        
            }
            else
            {
            UserAccountDAO.setNewPassword(sdsCon, nUserID, strNewPasswordEncripted);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Your new Password has been changed successfully.");
            }
              
            }
            else
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "You entered an invalid old password.");
            }
          }
          else
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Such account is not active or does not exist.");
          }
        }
        break;
        case FORGOT_PASSWORD:
        {
        

        String strPersonEmail = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL);
        String strUserID = UserAccountDAO.getUserID(sdsCon,strPersonEmail);
        String strUserPersonFullName = UserAccountDAO.getUserPersonFullName(sdsCon, strPersonEmail);
        if (strUserPersonFullName!=null){        
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,"1");
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);  
          String strActivationCode = UserAccountDAO.getActivationCode(sdsCon);
            System.out.println("Activation code is "+strActivationCode);
            
            UserAccountDAO.setActivationCode(sdsCon, strPersonEmail, strActivationCode);
        }
        
        }
        break;
        case NEW_PASSWORD:
        {
          String strPassword = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_NEW_PASSWORD); 
          String strConfirmPassword = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_CONFIRM_PASSWORD);
          String userID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          System.out.println("user id is "+userID);
          String strPersonEmail = UserAccountDAO.getEmail(sdsCon,Integer.valueOf(userID).intValue());          
          System.out.println("email is "+strPersonEmail+" password is "+strPassword);
          String strEncryptedUserPassword = EncryptUtil.encryptIt(strPassword + strPersonEmail);
          System.out.println("password after encryption is "+strEncryptedUserPassword);
          if (strPassword.compareTo(strConfirmPassword)!=0){
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Password and confirm password does not match.");
          System.out.println("pass not confirmed");
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,userID);
          }
          else{
            String strSecurityErr = SecurityUtils.checkPass(sdsCon,strPassword,strEncryptedUserPassword,strPersonEmail,userID);
            if (strSecurityErr.compareTo("")!=0){            
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, strSecurityErr);            
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,userID);
            }
            else{
            UserAccountDAO.setNewPassword(sdsCon, userID, strEncryptedUserPassword);
            UserAccountDAO.updateUserLastPass(sdsCon,userID,strEncryptedUserPassword);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Your new password was updated to your account.");
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,userID);
            }
          }
          
        }
        break;
        case GO_TO_ACTIVATION:
        {
         String userID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
         dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,userID);

        }
        break;
        case RESEND_PASSWORD:
        {
          String serverName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME);
          String serverPort = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT);
          String appName = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH);
          String strPersonEmail = (String)paramHashMap.get(UserAccountInterfaceKey.CONTROL_TEXT_NAME_USER_EMAIL);
          String strUserPersonFullName = UserAccountDAO.getUserPersonFullName(sdsCon, strPersonEmail);
          String strPassword = UserAccountDAO.getPassword(sdsCon, strPersonEmail);
          if(strPassword != null)
          {
//            String strUserPassword = Math.random()+"";
//            strUserPassword = strUserPassword.substring(strUserPassword.lastIndexOf(".")+1,strUserPassword.lastIndexOf(".")+7);
//            Utility.logger.debug(strUserPassword);
//            String strEncryptedUserPassword = EncryptUtil.encryptIt(strUserPassword + strPersonEmail);
            String strActivationCode = UserAccountDAO.getActivationCode(sdsCon);
            System.out.println("Activation code is "+strActivationCode);
            
            UserAccountDAO.setActivationCode(sdsCon, strPersonEmail, strActivationCode);
//            Utility.logger.debug("Password : "+strUserPassword);
            Utility.sendPasswordByMail(serverName, serverPort, appName, strPersonEmail, strUserPersonFullName, strActivationCode);
            String userID = UserAccountDAO.getUserID(sdsCon, strPersonEmail);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,userID);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Activation code was sent to the email you provided.");
          }
          else
          {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Such account is not active or does not exist.");
          }
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,"1");
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
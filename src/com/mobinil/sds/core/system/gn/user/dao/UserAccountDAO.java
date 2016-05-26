package com.mobinil.sds.core.system.gn.user.dao;

/**
 * UserAccountDAO Class handles all the user account database operations.
 * 
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import com.mobinil.sds.core.system.sa.persons.model.*;
import com.mobinil.sds.core.system.sa.privileges.model.*;
import com.mobinil.sds.core.system.sa.roles.dto.*;
import com.mobinil.sds.core.system.sa.roles.model.*;
import com.mobinil.sds.core.system.sa.users.dto.*;
import com.mobinil.sds.core.system.security.dao.securityDAO;
import com.mobinil.sds.core.system.security.dto.logDTO;
import com.mobinil.sds.core.system.sa.users.model.*;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.*;

import java.util.Vector;


public class UserAccountDAO 
{
  /**
   * getUserID method:
   * 
   * Returns a String of the user ID.
   * 
   * @param	Connection argCon, String argEmail, String argPassword
   * @return String
   * @throws  
   * @see    
  */	

  public static String getUserID(Connection argCon, String argEmail, String argPassword)
  {
    String strUserID = null;
    try 
    {
      Statement stmtUser = argCon.createStatement();
      String strUserQuery="select Person_ID from GEN_PERSON,GEN_USER where GEN_PERSON.PERSON_ID=GEN_USER.USER_ID and PERSON_EMAIL = '"+argEmail+"' and GEN_USER.PASSWORD='"+argPassword+"'";
//      String strUserQuery = "select USER_ID "+
//                            "from VW_GEN_USER_DETAILS "+
//                            "where PERSON_EMAIL = '"+argEmail+
//                            "' and PASSWORD = '"+argPassword+"'";
      ResultSet rstUser = stmtUser.executeQuery(strUserQuery);
      while(rstUser.next())
      {
        strUserID = rstUser.getString(1);
      }
      rstUser.close();
      stmtUser.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return strUserID;
  }

/**
   * getUserID method:
   * 
   * Returns a String of the user ID.
   * 
   * @param	Connection argCon, String argEmail, String argPassword
   * @return String
   * @throws  
   * @see    
  */	

  public static String getUserID(Connection argCon, String argEmail)
  {
    String strUserID = null;
    try 
    {
      Statement stmtUser = argCon.createStatement();
      String strUserQuery="select Person_ID from GEN_PERSON,GEN_USER where GEN_PERSON.PERSON_ID=GEN_USER.USER_ID and PERSON_EMAIL = '"+argEmail+"'";
//      String strUserQuery = "select USER_ID "+
//                            "from VW_GEN_USER_DETAILS "+
//                            "where PERSON_EMAIL = '"+argEmail+
//                            "' and PASSWORD = '"+argPassword+"'";
      ResultSet rstUser = stmtUser.executeQuery(strUserQuery);
      while(rstUser.next())
      {
        strUserID = rstUser.getString(1);
      }
      rstUser.close();
      stmtUser.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return strUserID;
  }
  
//public static String getUserID(Connection argCon, String argActivationCode)
//  {
//    String strUserID = null;
//    try 
//    {
//      Statement stmtUser = argCon.createStatement();
////      String strUserQuery="select Person_ID from GEN_PERSON,GEN_USER where GEN_PERSON.PERSON_ID=GEN_USER.USER_ID and PERSON_EMAIL = '"+argEmail+"' and GEN_USER.PASSWORD='"+argPassword+"'";
//      String strUserQuery = "select USER_ID "+
//                            "from VW_GEN_USER "+
//                            "where USER_ACTIV_CODE = '"+argActivationCode;
//      ResultSet rstUser = stmtUser.executeQuery(strUserQuery);
//      while(rstUser.next())
//      {
//        strUserID = rstUser.getString(1);
//      }
//      rstUser.close();
//      stmtUser.close();
//    } 
//    catch (Exception ex) 
//    {
//      ex.printStackTrace();
//    }
//    return strUserID;
//  }


  public static java.util.Date getExpireActivationCode(Connection argCon, String argUserId)
  {
    Date strCodeEndDate = null;
    try 
    {
      Statement stmtUser = argCon.createStatement();
//      String strUserQuery="select Person_ID from GEN_PERSON,GEN_USER where GEN_PERSON.PERSON_ID=GEN_USER.USER_ID and PERSON_EMAIL = '"+argEmail+"' and GEN_USER.PASSWORD='"+argPassword+"'";
      String strUserQuery = "select USER_EXPIRE_CODE_DATE "+
                            "from VW_GEN_USER "+
                            "where USER_ID = '"+argUserId+"'";
      ResultSet rstUser = stmtUser.executeQuery(strUserQuery);
      while(rstUser.next())
      {
        strCodeEndDate = rstUser.getDate(1);
      }
      rstUser.close();
      stmtUser.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return strCodeEndDate;
  }
  /**
   * getEmail method:
   * 
   * Returns a String of the user Email.
   * 
   * @param	Connection argCon, int argUserID
   * @return String
   * @throws  
   * @see    
  */	

  public static String getEmail(Connection argCon, int argUserID)
  {
    String strEmail = null;
    try 
    {
      Statement stmtUser = argCon.createStatement();
      String strUserQuery ="select PERSON_EMAIL from GEN_PERSON,GEN_USER,GEN_USER_STATUS where USER_ID=PERSON_ID and GEN_USER.USER_STATUS_ID=GEN_USER_STATUS.USER_STATUS_ID and USER_ID='"+argUserID+"' and USER_STATUS_NAME = 'Active'";
//      String strUserQuery = "select PERSON_EMAIL "+
//                            " from VW_GEN_USER_DETAILS"+
//                            " where USER_ID = "+argUserID+
//                            " and USER_STATUS_NAME = 'Active'";
      ResultSet rstUser = stmtUser.executeQuery(strUserQuery);
      while(rstUser.next())
      {
        strEmail = rstUser.getString(1);
      }
      rstUser.close();
      stmtUser.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return strEmail;
  }

  /**
   * getPassword method:
   * 
   * Returns a String of the user encrypted password.
   * 
   * @param	Connection argCon, int argUserID
   * @return String
   * @throws  
   * @see    
  */	

  public static String getPassword(Connection argCon, int argUserID)
  {
    String strPassword = null;
    try 
    {
      Statement stmtUser = argCon.createStatement();
            String strUserQuery ="select PASSWORD from GEN_PERSON,GEN_USER,GEN_USER_STATUS where USER_ID=PERSON_ID and GEN_USER.USER_STATUS_ID=GEN_USER_STATUS.USER_STATUS_ID and USER_ID='"+argUserID+"' and USER_STATUS_NAME = 'Active'";
//      String strUserQuery = "select PASSWORD "+
//                            " from VW_GEN_USER_DETAILS"+
//                            " where USER_ID = "+argUserID+
//                            " and USER_STATUS_NAME = 'Active'";
      ResultSet rstUser = stmtUser.executeQuery(strUserQuery);
      while(rstUser.next())
      {
        strPassword = rstUser.getString(1);
      }
      rstUser.close();
      stmtUser.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return strPassword;
  }

  /**
   * getName method:
   * 
   * Returns a String of the User Person Full Name.
   * 
   * @param	Connection argCon, String argUserEmail
   * @return String
   * @throws  
   * @see    
  */	

  public static String getUserPersonFullName(Connection argCon, String argUserEmail)
  {
    String strUserPersonFullName = null;
    try 
    {
      Statement stmtUser = argCon.createStatement();
       String strUserQuery ="select PERSON_FULL_NAME from GEN_PERSON,GEN_USER,GEN_USER_STATUS where USER_ID=PERSON_ID and GEN_USER.USER_STATUS_ID=GEN_USER_STATUS.USER_STATUS_ID and PERSON_EMAIL='"+argUserEmail+"' and USER_STATUS_NAME = 'Active'";
//      String strUserQuery = "select PERSON_FULL_NAME "+
//                            " from VW_GEN_USER_DETAILS"+
//                            " where PERSON_EMAIL = '"+argUserEmail+"'"+
//                            " and USER_STATUS_NAME = 'Active'";
      ResultSet rstUser = stmtUser.executeQuery(strUserQuery);
      while(rstUser.next())
      {
        strUserPersonFullName = rstUser.getString(1);
      }
      rstUser.close();
      stmtUser.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return strUserPersonFullName;
  }

  /**
   * getPassword method:
   * 
   * Returns a String of the user encrypted password.
   * 
   * @param	Connection argCon, String argUserEmail
   * @return String
   * @throws  
   * @see    
  */	

  public static String getPassword(Connection argCon, String argUserEmail)
  {
    String strPassword = null;
    try 
    {
      Statement stmtUser = argCon.createStatement();
      String strUserQuery ="select password from gen_user,gen_person,gen_user_status where gen_user.USER_ID =  gen_person.PERSON_ID and gen_user.USER_STATUS_ID = gen_user_status.USER_STATUS_ID and PERSON_EMAIL = '"+argUserEmail+"' and USER_STATUS_NAME = 'Active'";
//     String strUserQuery = "select PASSWORD "+
//                            " from VW_GEN_USER_DETAILS"+
//                            " where PERSON_EMAIL = '"+argUserEmail+"'"+
//                            " and USER_STATUS_NAME = 'Active'";
//System.out.println("query login is "+strUserQuery);
      ResultSet rstUser = stmtUser.executeQuery(strUserQuery);
      while(rstUser.next())
      {
        strPassword = rstUser.getString(1);
      }
      rstUser.close();
      stmtUser.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return strPassword;
  }

  /**
   * getUserDTO method:
 
 Returns a UserDTO.
   * 
   * @param	Connection argCon, int argUserID
   * @return UserDTO
   * @throws  
   * @see    
  */	

  public static UserDTO getUserDTO(Connection argCon, int argUserID)
  {
    UserDTO newUserDTO = null;
    try 
    {
      Statement stmtUser = argCon.createStatement();
   //   String strUserQuery ="select * from GEN_PERSON,GEN_USER,GEN_USER_STATUS where USER_ID=PERSON_ID and GEN_USER.USER_STATUS_ID=GEN_USER_STATUS.USER_STATUS_ID  and USER_ID ='"+argUserID+"'";
      String strUserQuery = "select * from VW_GEN_USER_DETAILS "+
                            "where USER_ID = '"+argUserID+"'";
      ResultSet rstUser = stmtUser.executeQuery(strUserQuery);
      while(rstUser.next())
      {
        PersonModel newPersonModel = new PersonModel(argUserID);
        UserModel newUserModel = new UserModel(newPersonModel, 
                                               rstUser.getString("password"), 
                                               rstUser.getInt("user_status_id"),
                                               rstUser.getString("user_status_name"),
                                               rstUser.getInt("IS_LOCKED")+""
                                               );
        newPersonModel.setPersonFullName(rstUser.getString("person_full_name"));
        newPersonModel.setPersonAddress(rstUser.getString("person_address"));
        newPersonModel.setPersonStatusID(rstUser.getInt("person_status_id"));
        newPersonModel.setPersonStatusName(rstUser.getString("person_status_name"));
        newPersonModel.setPersonTypeID(rstUser.getInt("person_type_id"));
        newPersonModel.setPersonTypeName(rstUser.getString("person_type_name"));
        newPersonModel.setPersonTypeStatusID(rstUser.getInt("person_type_status_id"));
        newPersonModel.setPersonTypeStatusName(rstUser.getString("person_type_status_name"));
        newPersonModel.setPersonEMail(rstUser.getString("person_email"));
        newUserDTO = new UserDTO();
        newUserDTO.setUserModel(newUserModel);
        newUserDTO.setUserRoles(getUserActiveRoles(argCon, newPersonModel.getPersonID()));
      }
      rstUser.close();
      stmtUser.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return newUserDTO;
  }

  /**
   * getUserActiveRoles method:
   * 
   * Returns a Vector holding this user active roles as RoleModels.
   * 
   * @param	Connection argCon, int argUserID
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector getUserActiveRoles(Connection argCon, int argUserID)
  {
    Vector vecUserRolesList = new Vector();
    try 
    {
      Statement stmtUserRolesList = argCon.createStatement();
      String strUserRolesListQuery = "select distinct ROLE_ID, ROLE_NAME"+
                                     " from VW_ADM_USER_ROLE_MODULE_PRIV"+
                                     " where USER_ID = "+argUserID+
                                     " and PRIVILAGE_STATUS_NAME = 'Active'"+
                                     " and MODULE_STATUS_NAME = 'Active'"+
                                     " and ROLE_STATUS_NAME = 'Active'"+
                                     " order by ROLE_NAME";
      ResultSet rstUserRolesList = stmtUserRolesList.executeQuery(strUserRolesListQuery);
      while(rstUserRolesList.next())
      {
        RoleModel newRoleModel = new RoleModel(rstUserRolesList.getInt(1), 
                                               rstUserRolesList.getString(2));
        vecUserRolesList.addElement(newRoleModel);
      }
      rstUserRolesList.close();
      stmtUserRolesList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUserRolesList;
  }

  /**
   * getUserRolePrivileges method:
   * 
   * Returns a RoleDTO holding this user active role and its active privileges.
   * 
   * @param	Connection argCon, int argUserID, int argRoleID
   * @return RoleDTO
   * @throws  
   * @see    
  */	

  public static RoleDTO getUserRolePrivileges(Connection argCon, int argUserID, int argRoleID)
  {
    RoleModel newRoleModel = new RoleModel(argRoleID);
    RoleDTO newRoleDTO = new RoleDTO();
    newRoleDTO.setRoleModel(newRoleModel);
    Vector vecUserRolePrivilegesList = new Vector();
    try 
    {
      Statement stmtPrivilegesList = argCon.createStatement();
      String strPrivilegesListQuery = "select PRIVILAGE_ID"+
                                   " from VW_ADM_USER_ROLE_MODULE_PRIV"+
                                   " where USER_ID = "+argUserID+
                                   " and ROLE_ID = "+argRoleID+
                                   " and MODULE_STATUS_NAME = 'Active'"+
                                   " and PRIVILAGE_STATUS_NAME = 'Active'";
      ResultSet rstPrivilegesList = stmtPrivilegesList.executeQuery(strPrivilegesListQuery);
      while(rstPrivilegesList.next())
      {
        vecUserRolePrivilegesList.addElement(new PrivilegeModel(rstPrivilegesList.getInt(1)));
      }
      newRoleDTO.setRolePrivileges(vecUserRolePrivilegesList);
      newRoleDTO.setRoleModules(getUserRoleModules(argCon, argUserID, argRoleID));
      rstPrivilegesList.close();
      stmtPrivilegesList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return newRoleDTO;
  }

  /**
   * getUserRoleModules method:
   * 
   * Returns a Vector holding this user's role active modules ids
   * as Integer objects.
   * 
   * @param	Connection argCon, int argUserID, int argRoleID
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector getUserRoleModules(Connection argCon, int argUserID, int argRoleID)
  {
    Vector vecUserRoleModulesList = new Vector();
    try 
    {
      Statement stmtModulesList = argCon.createStatement();
      String strModulesListQuery = "select distinct MODULE_ID, MODULE_NAME"+
                                   " from VW_ADM_USER_ROLE_MODULE_PRIV"+
                                   " where USER_ID = "+argUserID+
                                   " and ROLE_ID = "+argRoleID+
                                   " and MODULE_STATUS_NAME = 'Active'"+
                                   " and PRIVILAGE_STATUS_NAME = 'Active'"+
                                   " order by MODULE_NAME";
      ResultSet rstModulesList = stmtModulesList.executeQuery(strModulesListQuery);
      while(rstModulesList.next())
      {
        vecUserRoleModulesList.addElement(new Integer(rstModulesList.getInt(1)));
      }
      rstModulesList.close();
      stmtModulesList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUserRoleModulesList;
  }

  /**
   * setNewPassword method:
   * 
   * Sets a new password for this user and returns a boolean indecating 
   * if this change was successful or not.
   * 
   * @param	Connection argCon, int argUserID, String argNewPassword
   * @return boolean
   * @throws  
   * @see    
  */	

  public static boolean setNewPassword(Connection argCon, int argUserID, String argNewPassword)
  {
    try 
    {
      Statement stmtPasswordChange = argCon.createStatement();
  //    String strPasswordChangeQuery = "update GEN_USER"+
    //                                  " set PASSWORD = '"+argNewPassword+"'"+
      //                                " where USER_ID = "+argUserID;
      String strPasswordChangeQuery = "update VW_GEN_USER"+
                                      " set USER_PASSWORD = '"+argNewPassword+"'"+
                                      " where USER_ID = "+argUserID;
      if(stmtPasswordChange.executeUpdate(strPasswordChangeQuery) >0)
      {
        return true;
      }
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return false;
  }

  public static void setIncrementLock(Connection argCon, String argUserID)
  {
    try 
    {
      Statement stmt = argCon.createStatement();
  //    String strPasswordChangeQuery = "update GEN_USER"+
    //                                  " set PASSWORD = '"+argNewPassword+"'"+
      //                                " where USER_ID = "+argUserID;
      String strIncLock = "update GEN_USER set IS_LOCKED = IS_LOCKED+1 where USER_ID = "+argUserID;
//      System.out.println("increment lock query is "+strIncLock);
      stmt.executeUpdate(strIncLock) ;
      stmt.close();

    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }

  }

  public static void setDefaultLock(Connection argCon, String argUserID)
  {
    try 
    {
      Statement stmt = argCon.createStatement();
 
      String strDefLock = "update GEN_USER set IS_LOCKED = '0' where USER_ID = "+argUserID;

      stmt.executeUpdate(strDefLock) ;
      stmt.close();

    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }

  }
/**
   * setNewPassword method:
   * 
   * Sets a new password for this user and returns a boolean indecating 
   * if this change was successful or not.
   * 
   * @param	Connection argCon, String argUserEmail, String argNewPassword
   * @return boolean
   * @throws  
   * @see    
  */	

  public static void updateUserLastPass(Connection argCon, String userId,String argEncreptedPass)
  {
    try 
    {
      Statement stmtPasswordChange = argCon.createStatement();
    //  String strPasswordChangeQuery = "update (select * from GEN_PERSON,GEN_USER where USER_ID=PERSON_ID) t1 set PASSWORD = '"+argNewPassword+"'  where PERSON_EMAIL='"+argUserEmail+"'";
      int lastPass = securityDAO.getProps(argCon,"LAST_PASSWORD_COUNT").intValue();
            int updateUser = 0;
            ResultSet chckLst6Pswrd = 
              stmtPasswordChange.executeQuery("select Max(PASSWORD_INDEX) passCount from SDS_PASS_TRACE where SYSTEM_USER_ID ="+userId);
//            System.out.println("select Max(PASSWORD_INDEX) passCount from SDS_PASS_TRACE where SYSTEM_USER_ID ="+userId);

            if (chckLst6Pswrd.next())
              {
              if (chckLst6Pswrd.getInt("passCount")!=0){
                if (chckLst6Pswrd.getInt("passCount") > 0 && 
                    chckLst6Pswrd.getInt("passCount") < (lastPass + 1))
                  {
                    if (chckLst6Pswrd.getInt("passCount") == lastPass)
                      {
                        
                        int updatePassTrace = 
                          stmtPasswordChange.executeUpdate("insert into SDS_PASS_TRACE(SYSTEM_USER_ID,PASSWORD,PASSWORD_INDEX) Values ("+userId+
                                                         ",'" + argEncreptedPass + "'," + 
                                                         (chckLst6Pswrd.getInt("passCount") + 1) + 
                                                         ")");

//                            System.out.println(updatePassTrace);



                        updatePassTrace = 
                            stmtPasswordChange.executeUpdate("update SDS_PASS_TRACE set PASSWORD_INDEX= PASSWORD_INDEX-1 where SYSTEM_USER_ID="+userId);
//                            System.out.println(updatePassTrace);


                        int deletePasstrace = 
                          stmtPasswordChange.executeUpdate("delete from SDS_PASS_TRACE where SYSTEM_USER_ID ="+userId+
                                                         " and PASSWORD_INDEX=0");
//System.out.println(deletePasstrace);
                      }
                    else
                      {                        
                          int updatePassTrace = 
                            stmtPasswordChange.executeUpdate("insert into SDS_PASS_TRACE(SYSTEM_USER_ID,PASSWORD,PASSWORD_INDEX) Values (" + 
                                                           userId+",'" + argEncreptedPass + "'," + 
                                                           (chckLst6Pswrd.getInt("passCount") + 
                                                            1) + ")");
//                                                            System.out.println(updatePassTrace);
                          

                        }
                      }
              }
              else
                  {
                   int updatePassTrace = 
                          stmtPasswordChange.executeUpdate("insert into SDS_PASS_TRACE(SYSTEM_USER_ID,PASSWORD,PASSWORD_INDEX) Values ("+userId+
                                                         ",'" + argEncreptedPass + "'," + 
                                                         1 + 
                                                         ")");
//                                                         System.out.println(updatePassTrace);
                  }
                  }
                  
              
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }

  }

 public static String getActivationCode(Connection argCon)
  {
      String ActivationCode="";
    try 
    {

    Statement stmtActivation = argCon.createStatement();
    String SQL = "select dbms_random.String('x',15) str from dual";

      
      ResultSet rstActive = stmtActivation.executeQuery(SQL);
      while(rstActive.next())
      {
        ActivationCode = rstActive.getString("str");
      }
      rstActive.close();
      stmtActivation.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return ActivationCode;
  }

 /**
   * setNewPassword method:
   * 
   * Sets a new password for this user and returns a boolean indecating 
   * if this change was successful or not.
   * 
   * @param	Connection argCon, String argUserEmail, String argNewPassword
   * @return boolean
   * @throws  
   * @see    
  */	

  public static boolean setNewPassword(Connection argCon, String argUserId, String argUserCode)
  {
    try 
    {
      Statement stmtPasswordChange = argCon.createStatement();
    //  String strPasswordChangeQuery = "update (select * from GEN_PERSON,GEN_USER where USER_ID=PERSON_ID) t1 set PASSWORD = '"+argNewPassword+"'  where PERSON_EMAIL='"+argUserEmail+"'";
      String strPasswordChangeQuery = "update GEN_USER"+
                                      " set IS_LOCKED='0',PASSWORD = '"+argUserCode+"'"+
                                      " where USER_ID = '"+argUserId+"'";

//                                      System.out.println("Query update new password is "+strPasswordChangeQuery);
//                                      System.out.println("code in new password is "+argUserCode);
                                      
      if(stmtPasswordChange.executeUpdate(strPasswordChangeQuery) >0)
      {
        return true;
      }
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return false;
  }
  
  /**
   * setNewPassword method:
   * 
   * Sets a new password for this user and returns a boolean indecating 
   * if this change was successful or not.
   * 
   * @param	Connection argCon, String argUserEmail, String argNewPassword
   * @return boolean
   * @throws  
   * @see    
  */	

  public static void setActivationCode(Connection argCon, String argUserId, String argNewPassword)
  {
    try 
    {
      Statement stmtPasswordChange = argCon.createStatement();
    //  String strPasswordChangeQuery = "update (select * from GEN_PERSON,GEN_USER where USER_ID=PERSON_ID) t1 set PASSWORD = '"+argNewPassword+"'  where PERSON_EMAIL='"+argUserEmail+"'";
      String strPasswordChangeQuery = "update VW_GEN_USER"+
                                      " set USER_ACTIV_CODE = '"+argNewPassword+"', USER_EXPIRE_CODE_DATE=sysdate+1"+
                                      " where USER_LOGIN = '"+argUserId+"'";

//                                      System.out.println("Query to update activation code "+strPasswordChangeQuery);
      stmtPasswordChange.executeUpdate(strPasswordChangeQuery);

    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }

  }

/**
   * getPrivilegeID method:
   * 
   * Gets a privilege id as an int depending on the privilege 
   * action name given as a parameter. 
   * If this privilege action name does not exist it returns 0.
   * 
   * @param	Connection argCon, String argPrivilegeActionName
   * @return int
   * @throws  
   * @see    
  */	

  public static String getActivationCode(Connection argCon, String argUserId)
  {
    String privilegeID = null;
    try 
    {
      Statement stmtAction = argCon.createStatement();
      String strActionQuery = "select USER_ACTIV_CODE "+
                            " from VW_GEN_USER"+
                            " where USER_ID = '"+argUserId+"'";
//                            System.out.println("query for activation code is "+strActionQuery);

      ResultSet rstAction = stmtAction.executeQuery(strActionQuery);
      while(rstAction.next())
      {
        privilegeID = rstAction.getString(1);
      }
      rstAction.close();
      stmtAction.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return privilegeID;
  }


  /**
   * getPrivilegeID method:
   * 
   * Gets a privilege id as an int depending on the privilege 
   * action name given as a parameter. 
   * If this privilege action name does not exist it returns 0.
   * 
   * @param	Connection argCon, String argPrivilegeActionName
   * @return int
   * @throws  
   * @see    
  */	

  public static String getPrivilegeID(Connection argCon, String argPrivilegeActionName)
  {
    String privilegeID = null;
    try 
    {
      Statement stmtAction = argCon.createStatement();
      String strActionQuery = "select PRIVILAGE_ID "+
                            " from VW_ADM_MODULE_PRIVILAGE"+
                            " where PRIVILAGE_ACTION_NAME = '"+argPrivilegeActionName+"'";

      ResultSet rstAction = stmtAction.executeQuery(strActionQuery);
      while(rstAction.next())
      {
        privilegeID = rstAction.getString("PRIVILAGE_ID");
      }
      rstAction.close();
      stmtAction.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return privilegeID;
  }

  /**
   * hasPrivilegeAccess method:
   * 
   * Checks if this user using this role has access to this privilege. 
   * It returns boolean as an indecation.
   * 
   * @param	Connection argCon, int argUserID, int argRoleID, int argPrivilegeID
   * @return boolean
   * @throws  
   * @see    
  */	

  public static boolean hasPrivilegeAccess(Connection argCon, String argUserID, String argRoleID, String argPrivilegeID)
  {
    boolean hasAccess = false;
    try 
    {
      Statement stmtUser = argCon.createStatement();
      String strUserQuery = "select PRIVILAGE_ID "+
                            " from VW_ADM_USER_ROLE_MODULE_PRIV"+
                            " where USER_ID = "+argUserID+
                            " and ROLE_ID = "+argRoleID+
                            " and PRIVILAGE_ID = "+argPrivilegeID+
                            " and ROLE_STATUS_NAME = 'Active'"+
                            " and MODULE_STATUS_NAME = 'Active'"+
                            " and PRIVILAGE_STATUS_NAME = 'Active'";

      ResultSet rstUser = stmtUser.executeQuery(strUserQuery);
      while(rstUser.next())
      {
        hasAccess = true;
      }
      rstUser.close();
      stmtUser.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return hasAccess;
  }

   public static void userLog(Connection con,String argAction,String argUserID,String argUserIp,String argUrl, String duration)
  {
	 if (argUserID == null)
		  return;
	 if (argUserID.compareTo("null")==0)
		 return;
	 
    String strInsrtLog = "insert into SDS_ACTION_LOG values(SDS_LOG_SEQ.nextval,'"+argAction+"','"+argUserID+"','"+argUserIp+"',sysdate,'"+argUrl+"','"+duration+"')";
    
    DBUtil.executeSQL(strInsrtLog, con);

  }
  
}
package com.mobinil.sds.core.system.sa.users.dao; 

/**
 * RoleDAO Class accesses database to retreive or update more than one 
 * user. All methods are static.
 * 
 * 1- Gets a list of all users as a Vector of UserDTO. Each UserDTO holds
 *    the data of one user as a UserModel, its roles as a Vector of 
 *    UserRoleModel and the assigned DCMs as a Vector of UserDCMModel.
 *    
 * 2- Gets a HashMap holding two Vectors. The first Vector holds all available
 *    user Statuses as UserStatusModel. The second Vector holds a list of all 
 *    roles as a Vector of RoleDTO.
 *    
 * 3- Gets a list of all available user statuses as a Vector of
 *    UserStatusModel.
 *    
 * 4- Retrives the status of the provided status id.
 * 
 * 5- Checks if this user is assigned DCMs. And return a boolean indecating 
 *    whether this user has DCMs assigned to it or not.
 *    
 * 6- Retrieves all user role privileges as a Vector of privileges names.
 * 
 * 7- Retrieves all user roles as a Vector of UserRoleModel.
 * 
 * 8- Retrieves all Contract Registeration user DCMs as a Vector of UserDCMModel.
 *  
 * 9- Deletes all roles assigned to the user with the provided id. And return 
 *    a boolean indecating whether the deletion was successful or not.
 * 
 * 10- Deletes all DCMs assigned to the user with the provided id for 
 *     Contract Registeration module. And return a boolean indecating whether 
 *     the deletion was successful or not.
 * 
 * 11- Retrieves all Authentication Call user DCMs as a Vector of UserDCMModel.
 * 
 * 12- Gets a list of all users as a Vector of UserDTO. Each UserDTO holds
 *     the data of one user as a UserModel, the assigned DCMs for the 
 *     Contract Registeration module as a Vector of UserDCMModel.
 *     
 * 13- Gets a list of all users as a Vector of UserDTO. Each UserDTO holds
 *     the data of one user as a UserModel, the assigned DCMs for the 
 *     Authentication Call module as a Vector of UserDCMModel.
 *     
 * 14- Deletes all DCMs assigned to the user with the provided id for 
 *     Authentication Call module. And return a boolean indecating whether 
 *     the deletion was successful or not.
 *     
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import com.mobinil.sds.core.system.sa.modules.dao.*;
import com.mobinil.sds.core.system.sa.persons.model.*;
import com.mobinil.sds.core.system.sa.roles.dao.*;
import com.mobinil.sds.core.system.gn.dcm.dao.*;
import com.mobinil.sds.core.system.sa.users.dto.*;
import com.mobinil.sds.core.system.sa.users.model.*;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;

import java.util.*;
import com.mobinil.sds.core.system.gn.reports.domain.*;

public class UserDAO 
{
  /**
   * 1- Gets a list of all users as a Vector of UserDTO. Each UserDTO holds
    the data of one user as a UserModel, its roles as a Vector of 
    UserRoleModel and the assigned DCMs as a Vector of UserDCMModel.
   * 
   * @param	Connection argCon
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector getUsersList(Connection argCon)
  {

 
  Vector vecUsersList =null;


    vecUsersList= new Vector();   

    try 
    {
      Statement stmtUsersList = argCon.createStatement();
//      String strUsersListQuery = "select * from VW_GEN_USER_DETAILS"+
//                                 " where USER_STATUS_NAME in('Active','InActive')"+
//                                 " and PERSON_TYPE_ID = 1"+
//                                 " and PERSON_FULL_NAME not in('Administrator')"+
//                                 " order by PERSON_FULL_NAME";

String strUsersListQuery ="select * from GEN_USER usr_t "+
"left join GEN_PERSON prsn_t on usr_t.USER_ID =  prsn_t.PERSON_ID"+
" left join GEN_USER_STATUS stat_t on stat_t.USER_STATUS_ID=usr_t.USER_STATUS_ID "+
" left join GEN_PERSON_STATUS prsn_st on prsn_t.PERSON_STATUS_ID = prsn_st.PERSON_STATUS_ID"+
" left join GEN_PERSON_TYPE prsn_tt on prsn_t.PERSON_TYPE_ID = prsn_tt.PERSON_TYPE_ID "+
" left join GEN_PERSON_TYPE_STATUS prsn_tst on prsn_tt.STATUS_ID = prsn_tst.PERSON_TYPE_STATUS_ID "+
"where USER_STATUS_NAME in('Active','InActive') and PERSON_TYPE_STATUS_NAME in ('Active','InActive') and prsn_tt.PERSON_TYPE_ID = 1 and PERSON_FULL_NAME not in('Administrator') order by PERSON_FULL_NAME";

//System.out.println("Query in getUsersList is "+strUsersListQuery);
  
ResultSet rstUsersList = stmtUsersList.executeQuery(strUsersListQuery);
      while(rstUsersList.next())
      {
        /**
         * A person is a more general object than a user. The system may have many
         * persons but not all of them are users. But a user must be a person.
         * That is why a UserModel must take a PersonModel in its constructor.
         * A user is a person who has an account that he can use to login to the 
         * system. Privileges and DCMs are assigned to a user account.
         */
        PersonModel newPersonModel = new PersonModel(rstUsersList.getInt("USER_ID"));
        UserModel newUserModel = new UserModel(newPersonModel, 
                                               rstUsersList.getString("Password"), 
                                               rstUsersList.getInt("User_status_id"),
                                               rstUsersList.getString("User_status_Name"),
                                               rstUsersList.getInt("is_locked")+"");
        newPersonModel.setPersonFullName(rstUsersList.getString("person_full_name"));
        newPersonModel.setPersonAddress(rstUsersList.getString("person_address"));
        newPersonModel.setPersonStatusID(rstUsersList.getInt("person_status_id"));
        newPersonModel.setPersonStatusName(rstUsersList.getString("person_status_name"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeName(rstUsersList.getString("person_type_name"));
        newPersonModel.setPersonTypeStatusID(rstUsersList.getInt("person_type_status_id"));
        newPersonModel.setPersonTypeStatusName(rstUsersList.getString("person_type_status_name"));
        newPersonModel.setPersonEMail(rstUsersList.getString("person_email"));
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setUserModel(newUserModel);
        newUserDTO.setUserRoles(getUserRoles(argCon, newPersonModel.getPersonID()));
        vecUsersList.addElement(newUserDTO);
      }
      rstUsersList.close();
      stmtUsersList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
 
  
  
    return vecUsersList;
  }



  public static Vector getUsersListByUserId(String userId)
  {
    Vector vecUsersList = new Vector();
    try 
    {
      Connection argCon = Utility.getConnection();
      Statement stmtUsersList = argCon.createStatement();
      String strUsersListQuery = 
//      "select * from VW_GEN_USER_DETAILS"+
//                                 " where USER_ID = "+userId+" and USER_STATUS_NAME in('Active','InActive')"+
//                                 " and PERSON_TYPE_ID = 1"+
//                                 " and PERSON_FULL_NAME not in('Administrator')"+
//                                 " order by PERSON_FULL_NAME";
"select * from GEN_USER usr_t "+
"left join GEN_PERSON prsn_t on usr_t.USER_ID =  prsn_t.PERSON_ID "+
"left join GEN_USER_STATUS stat_t on stat_t.USER_STATUS_ID=usr_t.USER_STATUS_ID "+
"left join GEN_PERSON_STATUS prsn_st on prsn_t.PERSON_STATUS_ID = prsn_st.PERSON_STATUS_ID "+
"left join GEN_PERSON_TYPE prsn_tt on prsn_t.PERSON_TYPE_ID = prsn_tt.PERSON_TYPE_ID "+
"left join GEN_PERSON_TYPE_STATUS prsn_tst on prsn_tt.STATUS_ID = prsn_tst.PERSON_TYPE_STATUS_ID "+
"where USER_ID="+userId+" and USER_STATUS_NAME in('Active','InActive') and PERSON_TYPE_STATUS_NAME in ('Active','InActive') and prsn_t.PERSON_TYPE_ID = 1 and PERSON_FULL_NAME not in('Administrator') order by PERSON_FULL_NAME";
      System.out.println("strUsersListQuery LAMYA : "+strUsersListQuery);
      ResultSet rstUsersList = stmtUsersList.executeQuery(strUsersListQuery);
      while(rstUsersList.next())
      {
        /**
         * A person is a more general object than a user. The system may have many
         * persons but not all of them are users. But a user must be a person.
         * That is why a UserModel must take a PersonModel in its constructor.
         * A user is a person who has an account that he can use to login to the 
         * system. Privileges and DCMs are assigned to a user account.
         */
        PersonModel newPersonModel = new PersonModel(rstUsersList.getInt(1));
        UserModel newUserModel = new UserModel(newPersonModel, 
                                               rstUsersList.getString("Password"), 
                                               rstUsersList.getInt("User_status_id"),
                                               rstUsersList.getString("User_status_Name"),
                                               rstUsersList.getInt("is_locked")+"");
        newPersonModel.setPersonFullName(rstUsersList.getString("person_full_name"));
        newPersonModel.setPersonAddress(rstUsersList.getString("person_address"));
        newPersonModel.setPersonStatusID(rstUsersList.getInt("person_status_id"));
        newPersonModel.setPersonStatusName(rstUsersList.getString("person_status_name"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeName(rstUsersList.getString("person_type_name"));
        newPersonModel.setPersonTypeStatusID(rstUsersList.getInt("person_type_status_id"));
        newPersonModel.setPersonTypeStatusName(rstUsersList.getString("person_type_status_name"));
        newPersonModel.setPersonEMail(rstUsersList.getString("person_email"));
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setUserModel(newUserModel);
        newUserDTO.setUserRoles(getUserRoles(argCon, newPersonModel.getPersonID()));
        vecUsersList.addElement(newUserDTO);
      }
      rstUsersList.close();
      stmtUsersList.close();
      Utility.closeConnection(argCon);
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUsersList;
  }
  /**
   * 2- Gets a HashMap holding two Vectors. The first Vector holds all available
   *    user Statuses as UserStatusModel. The second Vector holds a list of all 
   *    roles as a Vector of RoleDTO.
   * 
   * @param	Connection argCon
   * @return HashMap
   * @throws  
   * @see    
  */	

  public static HashMap getAdditionalData(Connection argCon)
  {
    HashMap colUsersData = new HashMap();
    colUsersData.put(UserInterfaceKey.HASHMAP_KEY_USER_STATUSES_LIST, getUserStatuses(argCon));
    colUsersData.put(UserInterfaceKey.HASHMAP_KEY_ROLES_LIST, RoleDAO.getRolesList(argCon));
    colUsersData.put(UserInterfaceKey.HASHMAP_KEY_USER_LOCK_LIST, getUserLock(argCon));
//    colUsersData.put(UserInterfaceKey.HASHMAP_KEY_USER_MAX_LOCK,  getLockTime(argCon)+"");
    try
    {
      colUsersData.put(UserInterfaceKey.HASHMAP_KEY_DCMS_LIST, DCMDao.getDCMListByLevel(DCMDao.DCM_LEVEL_DISTRIBUTOR,argCon));
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return colUsersData;
  }

  public static HashMap getAdditionalData(Connection argCon,String channelId,String level)
  {
    HashMap colUsersData = new HashMap();
    colUsersData.put(UserInterfaceKey.HASHMAP_KEY_USER_STATUSES_LIST, getUserStatuses(argCon));
    colUsersData.put(UserInterfaceKey.HASHMAP_KEY_ROLES_LIST, RoleDAO.getRolesList(argCon));
    try
    {
      colUsersData.put(UserInterfaceKey.HASHMAP_KEY_DCMS_LIST, DCMDao.getDCMListByLevel(level,channelId,argCon));
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return colUsersData;
  }

 public static Vector<UserLockModel> getUserLock(Connection argCon)
  {
  
    Vector <UserLockModel>vecUserLockList= new Vector<UserLockModel>();    
    try 
    {
      Statement stmtUserLockList = argCon.createStatement();
      String strUserLockListQuery = "select * from SDS_GEN_USER_LOCK_STATUS"+
                                        " order by ID";
      ResultSet rstUserLockList = stmtUserLockList.executeQuery(strUserLockListQuery);
      while(rstUserLockList.next())
      {
        UserLockModel newUserLockModel = new UserLockModel(rstUserLockList.getInt(1), 
                                                     rstUserLockList.getString(2),
                                                     rstUserLockList.getString(3));
        vecUserLockList.addElement(newUserLockModel);
      }
      rstUserLockList.close();
      stmtUserLockList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUserLockList;
  }
  

  /**
   * 3- Gets a list of all available user statuses as a Vector of
   *    UserStatusModel.
   * 
   * @param	Connection argCon
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector<UserStatusModel> getUserStatuses(Connection argCon)
  {
    
  Vector<UserStatusModel> vecUserStatusesList =null;

    vecUserStatusesList= new Vector<UserStatusModel>();    
    try 
    {
      Statement stmtUserStatusesList = argCon.createStatement();
      String strUserStatusesListQuery = "select * from VW_GEN_USER_STATUS"+
                                        " order by USER_STATUS_NAME";
      ResultSet rstUserStatusesList = stmtUserStatusesList.executeQuery(strUserStatusesListQuery);
      while(rstUserStatusesList.next())
      {
        UserStatusModel newUserStatusModel = new UserStatusModel(rstUserStatusesList.getInt(1), 
                                                     rstUserStatusesList.getString(2),
                                                     rstUserStatusesList.getString(3));
        vecUserStatusesList.addElement(newUserStatusModel);
      }
      rstUserStatusesList.close();
      stmtUserStatusesList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
   
  
  
    return vecUserStatusesList;
  }

  /**
   * 4- Retrives the status of the provided status id.
   * 
   * @param	Connection argCon, int argStatusID
   * @return String
   * @throws  
   * @see    
  */	

  public static String getUserStatusName(Connection argCon, int argStatusID)
  {
    String strStatusName = null;
    try 
    {
      Statement stmtStatusName = argCon.createStatement();
      String strStatusNameQuery = "select USER_STATUS_NAME"+
                                        " from VW_GEN_USER_STATUS"+
                                        " where USER_STATUS_ID = "+ argStatusID ;
      ResultSet rstStatusName = stmtStatusName.executeQuery(strStatusNameQuery);
      while(rstStatusName.next())
      {
        strStatusName = rstStatusName.getString(1);
      }
      rstStatusName.close();
      stmtStatusName.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return strStatusName;
  }

  /**
   * 5- Checks if this user is assigned DCMs. And return a boolean indecating 
   *    whether this user has DCMs assigned to it or not.
   * 
   * @param	Connection argCon, int argRoleID
   * @return boolean
   * @throws  
   * @see    
  */	

  public static boolean hasDCMs(Connection argCon, int argUserID)
  {
    boolean hasDCMs = false;
    try 
    {
      Statement stmtUserDCMsList = argCon.createStatement();
      String strUserDCMsListQuery = "select DCM_ID from VW_CR_USER_DCM_ACCESS";
      if(argUserID != 0)
      {
        strUserDCMsListQuery += " where USER_ID = "+argUserID;
      }
      ResultSet rstUserDCMsList = stmtUserDCMsList.executeQuery(strUserDCMsListQuery);
      while(rstUserDCMsList.next())
      {
        hasDCMs = true;
        break;
      }
      rstUserDCMsList.close();
      stmtUserDCMsList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return hasDCMs;
  }

  /**
   * 6- Retrieves all user role privileges as a Vector of privileges names.
   * 
   * @param	Connection argCon, int argUserID, int argRoleID
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector getUserRolePrivileges(Connection argCon, int argRoleID)
  {
    Vector vecUserRolePrivilegesList = new Vector();
    try 
    {
      Statement stmtUserRolePrivilegesList = argCon.createStatement();
      String strUserRolePrivilegesListQuery = "select distinct PRIVILAGE_NAME"+
                                              " from VW_ADM_USER_ROLE_MODULE_PRIV"+
                                              " where ROLE_ID="+argRoleID+
                                              " order by PRIVILAGE_NAME";
      ResultSet rstUserRolePrivilegesList = stmtUserRolePrivilegesList.executeQuery(strUserRolePrivilegesListQuery);
      while(rstUserRolePrivilegesList.next())
      {
        vecUserRolePrivilegesList.addElement(rstUserRolePrivilegesList.getString(1));
      }
      rstUserRolePrivilegesList.close();
      stmtUserRolePrivilegesList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUserRolePrivilegesList;
  }

  /**
   * 7- Retrieves all user roles as a Vector of UserRoleModel.
   * 
   * @param	Connection argCon, int argUserID
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector getUserRoles(Connection argCon, int argUserID)
  {
    Vector vecUserRolesList = new Vector();
    try 
    {
      Statement stmtUserRolesList = argCon.createStatement();
      String strUserRolesListQuery = "select * from VW_ADM_USER_ROLE";
      if(argUserID != 0)
      {
        strUserRolesListQuery += " where USER_ID = "+argUserID;
      }
      ResultSet rstUserRolesList = stmtUserRolesList.executeQuery(strUserRolesListQuery);
      while(rstUserRolesList.next())
      {
        UserRoleModel newUserRoleModel = new UserRoleModel(rstUserRolesList.getInt(1), 
                                                                          rstUserRolesList.getInt(2));
        vecUserRolesList.addElement(newUserRoleModel);
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
   * 8- Retrieves all Contract Registeration user DCMs as a Vector of UserDCMModel.
   * 
   * @param	Connection argCon, int argUserID
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector getContractRegisterationUserDCMs(Connection argCon, int argUserID)
  {
    Vector vecUserDCMsList = new Vector();
    try 
    {
      Statement stmtUserDCMsList = argCon.createStatement();
      String strUserDCMsListQuery = "select * from VW_CR_USER_DCM_ACCESS";
      if(argUserID != 0)
      {
        strUserDCMsListQuery += " where USER_ID = "+argUserID;
      }
      ResultSet rstUserDCMsList = stmtUserDCMsList.executeQuery(strUserDCMsListQuery);
      while(rstUserDCMsList.next())
      {
        UserDCMModel newUserDCMModel = new UserDCMModel(rstUserDCMsList.getInt(1), 
                                                        rstUserDCMsList.getInt(2));
        vecUserDCMsList.addElement(newUserDCMModel);
      }
      rstUserDCMsList.close();
      stmtUserDCMsList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUserDCMsList;
  }

  public static Vector getSOPUserDCMs(Connection argCon, int argUserID)
  {
    Vector vecUserDCMsList = new Vector();
    try 
    {
      Statement stmtUserDCMsList = argCon.createStatement();
      String strUserDCMsListQuery = "select * from SOP_USER_DCM_ACCESS";
      if(argUserID != 0)
      {
        strUserDCMsListQuery += " where USER_ID = "+argUserID;
      }
      ResultSet rstUserDCMsList = stmtUserDCMsList.executeQuery(strUserDCMsListQuery);
      while(rstUserDCMsList.next())
      {
        UserDCMModel newUserDCMModel = new UserDCMModel(rstUserDCMsList.getInt("USER_ID"), 
                                                        rstUserDCMsList.getInt("DCM_ID"));
        vecUserDCMsList.addElement(newUserDCMModel);
      }
      rstUserDCMsList.close();
      stmtUserDCMsList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUserDCMsList;
  }

  /**
   * 9- Deletes all roles assigned to the user with the provided id. And return 
   * a boolean indecating whether the deletion was successful or not.
   * 
   * @param	Connection argCon, int argUserID
   * @return boolean
   * @throws  
   * @see    
  */	

  public static boolean deleteUserRoles(Connection argCon, int argUserID)
  {
    boolean delete = false;
    try 
    {
      Statement stmtDeleteUserRoles = argCon.createStatement();
      String strDeleteUserRolesQuery = "delete from VW_ADM_USER_ROLE";
      if(argUserID != 0)
      {
        strDeleteUserRolesQuery += " where USER_ID = "+argUserID;
      }
      if (stmtDeleteUserRoles.executeUpdate(strDeleteUserRolesQuery) >= 0)
      {
        delete = true;
      }
      else
      {
        delete = false;
      }
      stmtDeleteUserRoles.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return delete;
  }

  /**
   * 10- Deletes all DCMs assigned to the user with the provided id for 
   *     Contract Registeration module. And return a boolean indecating whether 
   *     the deletion was successful or not.
   * 
   * @param	Connection argCon, int argUserID
   * @return boolean
   * @throws  
   * @see    
  */	
  
  public static boolean deleteContractRegisterationUserDCMs(Connection argCon, int argUserID)
  {
    boolean delete = false;
    try 
    {
      Statement stmtDeleteUserDCMs = argCon.createStatement();
      String strDeleteUserDCMsQuery = "delete from VW_CR_USER_DCM_ACCESS where USER_ID = "+argUserID;
      
      if (stmtDeleteUserDCMs.executeUpdate(strDeleteUserDCMsQuery) >= 0)
      {
        delete = true;
      }
      else
      {
        delete = false;
      }
      stmtDeleteUserDCMs.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return delete;
  }


  public static boolean deleteContractRegisterationUserDCMs(Connection argCon, int argUserID ,String channelId)
  {
    boolean delete = false;
    try 
    {
      Statement stmtDeleteUserDCMs = argCon.createStatement();
      String strDeleteUserDCMsQuery = "delete from CR_USER_DCM_ACCESS where USER_ID = "+argUserID+" and DCM_ID IN( SELECT dcm_id FROM vw_gen_dcm WHERE channel_id = '"+channelId+"')";
      if (stmtDeleteUserDCMs.executeUpdate(strDeleteUserDCMsQuery) >= 0)
      {
        delete = true;
      }
      else
      {
        delete = false;
      }
      stmtDeleteUserDCMs.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return delete;
  }

  

  public static boolean deleteSOPUserDCMs(Connection argCon, int argUserID,String channelId)
  {
    boolean delete = false;
    try 
    {
      Statement stmtDeleteUserDCMs = argCon.createStatement();
      String strDeleteUserDCMsQuery = "delete from SOP_USER_DCM_ACCESS WHERE USER_ID = '"+argUserID+"'and dcm_id IN( SELECT dcm_id FROM vw_gen_dcm WHERE channel_id = '"+channelId+"')";
      //if(argUserID != 0)
      //{
        //strDeleteUserDCMsQuery += " and USER_ID = "+argUserID;
      //}
      Utility.logger.debug("The delete query isssssssssss " + strDeleteUserDCMsQuery);
      if (stmtDeleteUserDCMs.executeUpdate(strDeleteUserDCMsQuery) >= 0)
      {
        delete = true;
      }
      else
      {
        delete = false;
      }
      stmtDeleteUserDCMs.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return delete;
  }

  public static void insertSOPUserDCMs(Connection argCon, int argUserID,int dcmID,String channelID)
  {
    try 
    {
      Statement stmtDeleteUserDCMs = argCon.createStatement();
      String strDeleteUserDCMsQuery = "insert into SOP_USER_DCM_ACCESS(USER_ID,DCM_ID) values("+argUserID+","+dcmID+")";
      Utility.logger.debug("The insert query isssssssssssss " + strDeleteUserDCMsQuery);
      stmtDeleteUserDCMs.executeUpdate(strDeleteUserDCMsQuery);
      stmtDeleteUserDCMs.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
  }
  /**
   * 11- Retrieves all Authentication Call user DCMs as a Vector of UserDCMModel.
   * 
   * @param	Connection argCon, int argUserID
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector getAuthenticationCallUserDCMs(Connection argCon, int argUserID)
  {
    Vector vecUserDCMsList = new Vector();
    try 
    {
      Statement stmtUserDCMsList = argCon.createStatement();
      String strUserDCMsListQuery = "select * from VW_ATH_USER_DCM_ACCESS";
      if(argUserID != 0)
      {
        strUserDCMsListQuery += " where USER_ID = "+argUserID;
      }
      ResultSet rstUserDCMsList = stmtUserDCMsList.executeQuery(strUserDCMsListQuery);
      while(rstUserDCMsList.next())
      {
        UserDCMModel newUserDCMModel = new UserDCMModel(rstUserDCMsList.getInt(1), 
                                                        rstUserDCMsList.getInt(2));
        vecUserDCMsList.addElement(newUserDCMModel);
      }
      rstUserDCMsList.close();
      stmtUserDCMsList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUserDCMsList;
  }
  /**
   * 12- Gets a list of all users as a Vector of UserDTO. Each UserDTO holds
   *     the data of one user as a UserModel, the assigned DCMs for the 
   *     Contract Registeration module as a Vector of UserDCMModel.
   * 
   * @param	Connection argCon
   * @return Vector
   * @throws  
   * @see    
  */	

//  public static Vector getContractRegisterationUsersList(Connection argCon)

    /**
     * 12- Gets a list of all users as a Vector of UserDTO.Each UserDTO holds
     the data of one user as a UserModel, the assigned DCMs for the 
     Contract Registeration module as a Vector of UserDCMModel.
     * @param Connection argCon
     * @return Vector
     * @throws (ERROR)
     * @see
     */
      public static Vector getSystemUsersList(Connection argCon,String orderBy)
  {
    Vector vecUsersList = new Vector();
    try 
    {
      Statement stmtUsersList = argCon.createStatement();
      String strUsersListQuery = "select * from VW_GEN_USER_DETAILS"+
                                 " where USER_STATUS_NAME in('Active','InActive')"+
                                 " and PERSON_TYPE_ID = 1"+
                                 " and PERSON_FULL_NAME not in('Administrator')"+
                                 " order by "+orderBy+" ";
      ResultSet rstUsersList = stmtUsersList.executeQuery(strUsersListQuery);
      while(rstUsersList.next())
      {
        /**
         * A person is a more general object than a user. The system may have many
         * persons but not all of them are users. But a user must be a person.
         * That is why a UserModel must take a PersonModel in its constructor.
         * A user is a person who has an account that he can use to login to the 
         * system. Privileges and DCMs are assigned to a user account.
         */
        PersonModel newPersonModel = new PersonModel(rstUsersList.getInt(1));
        UserModel newUserModel = new UserModel(newPersonModel, 
                                              rstUsersList.getString("Password"), 
                                               rstUsersList.getInt("User_status_id"),
                                               rstUsersList.getString("User_status_Name"),
                                               rstUsersList.getInt("is_locked")+"");
        newPersonModel.setPersonFullName(rstUsersList.getString("person_full_name"));
        newPersonModel.setPersonAddress(rstUsersList.getString("person_address"));
        newPersonModel.setPersonStatusID(rstUsersList.getInt("person_status_id"));
        newPersonModel.setPersonStatusName(rstUsersList.getString("person_status_name"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeName(rstUsersList.getString("person_type_name"));
        newPersonModel.setPersonTypeStatusID(rstUsersList.getInt("person_type_status_id"));
        newPersonModel.setPersonTypeStatusName(rstUsersList.getString("person_type_status_name"));
        newPersonModel.setPersonEMail(rstUsersList.getString("person_email"));
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setUserModel(newUserModel);
        newUserDTO.setUserDCMs(getContractRegisterationUserDCMs(argCon, newPersonModel.getPersonID()));
        vecUsersList.addElement(newUserDTO);
      }
      rstUsersList.close();
      stmtUsersList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUsersList;
  }

  public static Vector getSOPSystemUsersList(Connection argCon)
  {
    Vector vecUsersList = new Vector();
    try 
    {
      Statement stmtUsersList = argCon.createStatement();
      String strUsersListQuery = "select * from VW_GEN_USER_DETAILS"+
                                 " where USER_STATUS_NAME in('Active','InActive')"+
                                 " and PERSON_TYPE_ID = 1"+
                                 " and PERSON_FULL_NAME not in('Administrator')"+
                                 " order by PERSON_FULL_NAME";
      ResultSet rstUsersList = stmtUsersList.executeQuery(strUsersListQuery);
      while(rstUsersList.next())
      {
        /**
         * A person is a more general object than a user. The system may have many
         * persons but not all of them are users. But a user must be a person.
         * That is why a UserModel must take a PersonModel in its constructor.
         * A user is a person who has an account that he can use to login to the 
         * system. Privileges and DCMs are assigned to a user account.
         */
        PersonModel newPersonModel = new PersonModel(rstUsersList.getInt(1));
        UserModel newUserModel = new UserModel(newPersonModel, 
                                               rstUsersList.getString("Password"), 
                                               rstUsersList.getInt("User_status_id"),
                                               rstUsersList.getString("User_status_Name"),
                                               rstUsersList.getInt("is_locked")+"");
        newPersonModel.setPersonFullName(rstUsersList.getString("person_full_name"));
        newPersonModel.setPersonAddress(rstUsersList.getString("person_address"));
        newPersonModel.setPersonStatusID(rstUsersList.getInt("person_status_id"));
        newPersonModel.setPersonStatusName(rstUsersList.getString("person_status_name"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeName(rstUsersList.getString("person_type_name"));
        newPersonModel.setPersonTypeStatusID(rstUsersList.getInt("person_type_status_id"));
        newPersonModel.setPersonTypeStatusName(rstUsersList.getString("person_type_status_name"));
        newPersonModel.setPersonEMail(rstUsersList.getString("person_email"));
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setUserModel(newUserModel);
        newUserDTO.setUserDCMs(getSOPUserDCMs(argCon, newPersonModel.getPersonID()));
        vecUsersList.addElement(newUserDTO);
      }
      rstUsersList.close();
      stmtUsersList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUsersList;
  }

  /**
   * 13- Gets a list of all users as a Vector of UserDTO. Each UserDTO holds
     the data of one user as a UserModel, the assigned DCMs for the 
     Authentication Call module as a Vector of UserDCMModel.
   * 
   * @param	Connection argCon
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector getAuthenticationCallUsersList(Connection argCon)
  {
    Vector vecUsersList = new Vector();
    try 
    {
      Statement stmtUsersList = argCon.createStatement();
      String strUsersListQuery = "select * from VW_GEN_USER_DETAILS"+
                                 " where USER_STATUS_NAME in('Active','InActive')"+
                                 " and PERSON_TYPE_ID = 1"+
                                 " and PERSON_FULL_NAME not in('Administrator')"+
                                 " order by PERSON_FULL_NAME";
      ResultSet rstUsersList = stmtUsersList.executeQuery(strUsersListQuery);
      while(rstUsersList.next())
      {
        /**
         * A person is a more general object than a user. The system may have many
         * persons but not all of them are users. But a user must be a person.
         * That is why a UserModel must take a PersonModel in its constructor.
         * A user is a person who has an account that he can use to login to the 
         * system. Privileges and DCMs are assigned to a user account.
         */
        PersonModel newPersonModel = new PersonModel(rstUsersList.getInt(1));
        UserModel newUserModel = new UserModel(newPersonModel, 
                                               rstUsersList.getString("Password"), 
                                               rstUsersList.getInt("User_status_id"),
                                               rstUsersList.getString("User_status_Name"),
                                               rstUsersList.getInt("is_locked")+"");
        newPersonModel.setPersonFullName(rstUsersList.getString("person_full_name"));
        newPersonModel.setPersonAddress(rstUsersList.getString("person_address"));
        newPersonModel.setPersonStatusID(rstUsersList.getInt("person_status_id"));
        newPersonModel.setPersonStatusName(rstUsersList.getString("person_status_name"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeName(rstUsersList.getString("person_type_name"));
        newPersonModel.setPersonTypeStatusID(rstUsersList.getInt("person_type_status_id"));
        newPersonModel.setPersonTypeStatusName(rstUsersList.getString("person_type_status_name"));
        newPersonModel.setPersonEMail(rstUsersList.getString("person_email"));
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setUserModel(newUserModel);
        newUserDTO.setUserDCMs(getAuthenticationCallUserDCMs(argCon, newPersonModel.getPersonID()));
        vecUsersList.addElement(newUserDTO);
      }
      rstUsersList.close();
      stmtUsersList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUsersList;
  }

  /**
   * 14- Deletes all DCMs assigned to the user with the provided id for 
   *     Authentication Call module. And return a boolean indecating whether 
   *     the deletion was successful or not.
   * 
   * @param	Connection argCon, int argUserID
   * @return boolean
   * @throws  
   * @see    
  */	
  
  public static boolean deleteAuthenticationCallUserDCMs(Connection argCon, int argUserID)
  {
    boolean delete = false;
    try 
    {
      Statement stmtDeleteUserDCMs = argCon.createStatement();
      String strDeleteUserDCMsQuery = "delete from VW_ATH_USER_DCM_ACCESS";
      if(argUserID != 0)
      {
        strDeleteUserDCMsQuery += " where USER_ID = "+argUserID;
      }
      if (stmtDeleteUserDCMs.executeUpdate(strDeleteUserDCMsQuery) >= 0)
      {
        delete = true;
      }
      else
      {
        delete = false;
      }
      stmtDeleteUserDCMs.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return delete;
  }



  /**
   * 15- Checks if this user is assigned reports. And return a boolean indecating 
   *    whether this user has repots assigned to it or not.
   * 
   * @param	Connection argCon, int argRoleID
   * @return boolean
   * @throws  
   * @see    
  */	

/*  public static boolean hasReport(Connection argCon, int argUserID)
  {
    boolean hasreportFlag = false;
    try 
    {
      Statement stmtUserReportList = argCon.createStatement();
      String strUserReportListQuery = "select REPORT_ID from VW_ADH_REPORT_USER";
      if(argUserID != 0)
      {
        strUserReportListQuery += " where USER_ID = "+argUserID;
      }
      ResultSet rstUserReportList = stmtUserReportList.executeQuery(strUserReportListQuery);
      while(rstUserReportList.next())
      {
        hasreportFlag = true;
        break;
      }
      rstUserReportList.close();
      stmtUserReportList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return hasreportFlag;
  }*/



/**
   * 16- Retrieves all Contract Registeration user reports as a Vector of UserReportModel.
   * 
   * @param	Connection argCon, int argUserID
   * @return Vector
   * @throws  
   * @see    
  */	

/*  public static Vector getUserReports(Connection argCon, int argUserID)
  {
    Vector vecUserReportsList = new Vector();
    try 
    {
      Statement stmtUserReportsList = argCon.createStatement();
      String strUserReportsListQuery = "select * from vw_adh_report_user";
      if(argUserID != 0)
      {
        strUserReportsListQuery += " where USER_ID = "+argUserID;
      }
      ResultSet rstUserReportsList = stmtUserReportsList.executeQuery(strUserReportsListQuery);
      while(rstUserReportsList.next())
      {
        UserReportModel newUserReportModel = new UserReportModel(rstUserReportsList.getInt("user_id"), 
                                                        rstUserReportsList.getInt("REPORT_ID"));
        vecUserReportsList.addElement(newUserReportModel);
      }
      rstUserReportsList.close();
      stmtUserReportsList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUserReportsList;
  }
*/
 

  /**
   * 17- Deletes all Reports assigned to the user with the provided id for 
   *     Contract Registeration module. And return a boolean indecating whether 
   *     the deletion was successful or not.
   * 
   * @param	Connection argCon, int argUserID
   * @return boolean
   * @throws  
   * @see    
  */	
  
/*  public static boolean deleteUserReports(Connection argCon, int argUserID)
  {
    boolean delete = false;
    try 
    {
      Statement stmtDeleteUserReports = argCon.createStatement();
      String strDeleteUserReportsQuery = "delete from vw_adh_report_user";
      if(argUserID != 0)
      {
        strDeleteUserReportsQuery += " where USER_ID = "+argUserID;
      }
      if (stmtDeleteUserReports.executeUpdate(strDeleteUserReportsQuery) >= 0)
      {
        delete = true;
      }
      else
      {
        delete = false;
      }
      stmtDeleteUserReports.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return delete;
  }
*/


  /**
   * 18- Gets a HashMap holding two Vectors. The first Vector holds all available
   *    user Statuses as UserStatusModel. The second Vector holds a list of all 
   *    reports as a Vector of DTO.
   * 
   * @param	Connection argCon
   * @return HashMap
   * @throws  
   * @see    
  */	

/*  public static HashMap getUserReportsAdditionalData(Connection argCon)
  {
    HashMap colUsersData = new HashMap();
    colUsersData.put(UserInterfaceKey.HASHMAP_KEY_USER_STATUSES_LIST, getUserStatuses(argCon));
    colUsersData.put(UserInterfaceKey.HASHMAP_KEY_ROLES_LIST, RoleDAO.getRolesList(argCon));
    

  
    try
    {
      ReportEngine reportEngine = new ReportEngine();

      colUsersData.put(UserInterfaceKey.HASHMAP_KEY_REPORT_USERLIST,reportEngine.retrieveUserReportList());
      
      colUsersData.put(UserInterfaceKey.HASHMAP_KEY_REPORT_LIST,reportEngine.retrieveReportList());
      reportEngine.finalize();
      
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }

    return colUsersData;
  }
 */
  public static void insertUserRole(Connection con,int intUserId,int intRoleId)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = "insert into ADM_USER_ROLE (USER_ID,ROLE_ID) "+
                        " values("+intUserId+","+intRoleId+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }

  public static void insertCrUserDcmAccess(Connection con,int intUserId,int intDcmId)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = "insert into CR_USER_DCM_ACCESS (USER_ID,DCM_ID) "+
                        " values("+intUserId+","+intDcmId+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }

  public static void insertVwGenUserDetails(Connection con,int user_id,String user_login,String password,int user_status_id,String user_status_name,String person_full_name,String person_address,int person_status_id,String person_status_name,int person_type_id,String person_type_name,int person_type_status_id,String person_type_status_name,String isLocked,String expireDate)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSqlPerson = "insert into GEN_PERSON (PERSON_ID,PERSON_FULL_NAME, "+
                        " PERSON_ADDRESS, PERSON_STATUS_ID, PERSON_TYPE_ID,  "+
                        "  PERSON_EMAIL) "+
                        " values("+user_id+",'"+person_full_name+"' "+
                        " ,'"+person_address+"',"+person_status_id+","+person_type_id+" "+
                        " ,'"+user_login+"') ";

                        
       					user_login = user_login.toLowerCase();
                        String strSqlUser="insert into GEN_USER (USER_ID,USER_LOGIN ,PASSWORD, USER_STATUS_ID,IS_LOCKED,SYSTEM_LOGIN_END_DATE) values("+user_id+",'"+user_login+"','"+password+"','"+user_status_id+"','"+isLocked+"',"+expireDate+")";
                        System.out.println("Query in insert GEN_USER "+strSqlUser);                        
                        System.out.println("Query in insert GEN_person "+strSqlPerson);                        
       Utility.logger.debug(strSqlUser); 
       Utility.logger.debug(strSqlPerson); 
       stat.executeUpdate(strSqlPerson);
       stat.executeUpdate(strSqlUser);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }

  public static void updateVwGenUserDetails(int user_id,String user_login,String password,int user_status_id,String user_status_name,String person_full_name,String person_address,int person_status_id,String person_status_name,int person_type_id,String person_type_name,int person_type_status_id,String person_type_status_name,int lockId)
  {
      try
      {
       Connection con = Utility.getConnection(); 
       Statement stat = con.createStatement();
//       String strSql = "update VW_GEN_USER_DETAILS set PASSWORD = '"+password+"', USER_STATUS_ID = "+user_status_id+",PERSON_FULL_NAME = '"+person_full_name+"', "+
//                        " PERSON_ADDRESS = '"+person_address+"', PERSON_STATUS_ID = "+person_status_id+", PERSON_TYPE_ID = "+person_type_id+",  "+
//                        " PERSON_TYPE_STATUS_ID = "+person_type_status_id+" , PERSON_EMAIL = '"+user_login+"' "+
//                        " where USER_ID = "+user_id+" ";

String updateUserSQL="update GEN_USER set IS_LOCKED='"+lockId+"', USER_LOGIN='"+user_login+"', PASSWORD = '"+password+"', USER_STATUS_ID = "+user_status_id+" where USER_ID = "+user_id;
String updatePersonSQL="update GEN_PERSON set  PERSON_FULL_NAME = '"+person_full_name+"', "+
                        " PERSON_ADDRESS = '"+person_address+"', PERSON_STATUS_ID = "+person_status_id+", PERSON_TYPE_ID = "+person_type_id+",  "+
                        " PERSON_EMAIL = '"+user_login+"' "+
                        " where PERSON_ID = "+user_id+" ";
      System.out.println("Query in update updateGenUser is "+updateUserSQL);
      System.out.println("Query in update updateGenPERSON is "+updatePersonSQL);
       Utility.logger.debug(updateUserSQL); 
       Utility.logger.debug(updatePersonSQL); 
       stat.executeUpdate(updatePersonSQL);
       stat.executeUpdate(updateUserSQL);
       stat.close();
       Utility.closeConnection(con);
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }

  public static void updateVwGenUserDetails(int user_id,String user_login,String password,int user_status_id,String user_status_name,String person_full_name,String person_address,int person_status_id,String person_status_name,int person_type_id,String person_type_name,int person_type_status_id,String person_type_status_name)
  {
      try
      {
       Connection con = Utility.getConnection(); 
       Statement stat = con.createStatement();
//       String strSql = "update VW_GEN_USER_DETAILS set PASSWORD = '"+password+"', USER_STATUS_ID = "+user_status_id+",PERSON_FULL_NAME = '"+person_full_name+"', "+
//                        " PERSON_ADDRESS = '"+person_address+"', PERSON_STATUS_ID = "+person_status_id+", PERSON_TYPE_ID = "+person_type_id+",  "+
//                        " PERSON_TYPE_STATUS_ID = "+person_type_status_id+" , PERSON_EMAIL = '"+user_login+"' "+
//                        " where USER_ID = "+user_id+" ";

String updateUserSQL="update GEN_USER set USER_LOGIN='"+user_login+"', PASSWORD = '"+password+"', USER_STATUS_ID = "+user_status_id+" where USER_ID = "+user_id;
String updatePersonSQL="update GEN_PERSON set  PERSON_FULL_NAME = '"+person_full_name+"', "+
                        " PERSON_ADDRESS = '"+person_address+"', PERSON_STATUS_ID = "+person_status_id+", PERSON_TYPE_ID = "+person_type_id+",  "+
                        " PERSON_EMAIL = '"+user_login+"' "+
                        " where PERSON_ID = "+user_id+" ";
      System.out.println("Query in update updateGenUser is "+updateUserSQL);
      System.out.println("Query in update updateGenPERSON is "+updatePersonSQL);
       Utility.logger.debug(updateUserSQL); 
       Utility.logger.debug(updatePersonSQL); 
       stat.executeUpdate(updatePersonSQL);
       stat.executeUpdate(updateUserSQL);
       stat.close();
       Utility.closeConnection(con);
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }

  public static void insertAthUserDcmAccess(Connection con,int userId,int dcmId)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = "insert into ATH_USER_DCM_ACCESS (USER_ID,DCM_ID) "+
                        " values("+userId+","+dcmId+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }

  public static void updateUserEmail(Connection con,int userId,String userEmail)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = "update GEN_PERSON set PERSON_EMAIL = '"+userEmail+"' where PERSON_ID = "+userId+" ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }
  
    public static void updateUserStatus(Connection con,int userId,int statusID,boolean lockFlag)
    {
        try
        {
         Statement stat = con.createStatement();
         String strSql ="";
         if (lockFlag)
         {
             strSql = "update gen_user set IS_LOCKED = '"+statusID+"' where USER_ID = "+userId+" ";
         }
         else
         {
           strSql = "update gen_user set user_status_id = '"+statusID+"' where USER_ID = "+userId+" ";
         }
         
         //Utility.logger.debug(strSql); 
         System.out.println(strSql);
         stat.executeUpdate(strSql);
         
         
         stat.close();
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
    }

public static Integer getLockTime(Connection con){

Integer maxLock = new Integer(0);
      try
      {
       Statement stat = con.createStatement();
       String strMaxLock = "select PROPERTIES from SDS_PROPERTIES where REASONE = 'LOCK_TIMES'";
       ResultSet res = stat.executeQuery(strMaxLock);
       if(res.next())
       {
        maxLock = new Integer( res.getInt("PROPERTIES"));
       }
       Utility.logger.debug(strMaxLock); 
       res.close();
       stat.close();       
      }
      catch (SQLException sqle)
      {
      sqle.printStackTrace();
      }
      return maxLock;

}

  public static PersonModel getPerson(Connection con,int personId)
  {
      PersonModel personModel = null;
      try
      {
       Statement stat = con.createStatement();
       String strSql = "select * from VW_GEN_USER_DETAILS where USER_ID = "+personId+" ";
       ResultSet res = stat.executeQuery(strSql);
       if(res.next())
       {
         personModel = new PersonModel(personId); 
         personModel.setPersonAddress(res.getString("PERSON_ADDRESS"));
         personModel.setPersonEMail(res.getString("PERSON_EMAIL"));
         personModel.setPersonFullName(res.getString("PERSON_FULL_NAME"));
         personModel.setPersonID(res.getInt("USER_ID"));
         personModel.setPersonStatusID(res.getInt("USER_STATUS_ID"));
         personModel.setPersonStatusName(res.getString("USER_STATUS_NAME"));
         personModel.setPersonTypeID(res.getInt("PERSON_TYPE_ID"));
         personModel.setPersonTypeName(res.getString("PERSON_TYPE_NAME"));
         personModel.setPersonTypeStatusID(res.getInt("PERSON_TYPE_STATUS_ID"));
         personModel.setPersonTypeStatusName(res.getString("PERSON_TYPE_STATUS_NAME"));
       }
       //Utility.logger.debug(strSql); 
       res.close();
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
      return personModel;
  }  

  public static UserModel getUser(Connection con,int userId,PersonModel personModel)
  {
      UserModel userModel = null;
      try
      {
       Statement stat = con.createStatement();
       String strSql = "select * from GEN_USER usr_t left join GEN_USER_STATUS stat_t on stat_t.USER_STATUS_ID=usr_t.USER_STATUS_ID  where USER_ID = "+userId+" ";
       ResultSet res = stat.executeQuery(strSql);
       if(res.next())
       {
         userModel = new UserModel(personModel);
         userModel.setUserPassword(res.getString("PASSWORD"));
         userModel.setUserStatusID(res.getInt("USER_STATUS_ID"));
         userModel.setUserStatusName(res.getString("USER_STATUS_NAME"));
         userModel.setM_isLocked(res.getString("IS_LOCKED"));
       }
       //Utility.logger.debug(strSql); 
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
      return userModel;
  }  
  
  
  public static Vector getSOPUserPaymentDelete(Connection argCon)
  {
    Vector vecUsersList = new Vector();
    try 
    {
      Statement stmtUsersList = argCon.createStatement();
      
      String strUsersListQuery = " select * from VW_GEN_USER_DETAILS "+
                                 " where USER_STATUS_NAME in('Active','InActive')"+
                                 " and PERSON_TYPE_ID = 1"+
                                 " and PERSON_FULL_NAME not in('Administrator')"+
                                 " order by PERSON_FULL_NAME";
      ResultSet rstUsersList = stmtUsersList.executeQuery(strUsersListQuery);
      while(rstUsersList.next())
      {
        /**
         * A person is a more general object than a user. The system may have many
         * persons but not all of them are users. But a user must be a person.
         * That is why a UserModel must take a PersonModel in its constructor.
         * A user is a person who has an account that he can use to login to the 
         * system. Privileges and DCMs are assigned to a user account.
         */
        PersonModel newPersonModel = new PersonModel(rstUsersList.getInt(1));
        UserModel newUserModel = new UserModel(newPersonModel, 
                                               rstUsersList.getString("Password"), 
                                               rstUsersList.getInt("User_status_id"),
                                               rstUsersList.getString("User_status_Name"),
                                               rstUsersList.getInt("is_locked")+"");
        newPersonModel.setPersonFullName(rstUsersList.getString("person_full_name"));
        newPersonModel.setPersonAddress(rstUsersList.getString("person_address"));
        newPersonModel.setPersonStatusID(rstUsersList.getInt("person_status_id"));
        newPersonModel.setPersonStatusName(rstUsersList.getString("person_status_name"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeID(rstUsersList.getInt("person_type_id"));
        newPersonModel.setPersonTypeName(rstUsersList.getString("person_type_name"));
        newPersonModel.setPersonTypeStatusID(rstUsersList.getInt("person_type_status_id"));
        newPersonModel.setPersonTypeStatusName(rstUsersList.getString("person_type_status_name"));
        newPersonModel.setPersonEMail(rstUsersList.getString("person_email"));
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setUserModel(newUserModel);
        newUserDTO.setUserDCMs(getSOPUserDCMs(argCon, newPersonModel.getPersonID()));
        vecUsersList.addElement(newUserDTO);
      }
      rstUsersList.close();
      stmtUsersList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecUsersList;
  }
  
  
  public static void deleteUserPaymentPermission(Connection con)
  {
	  try
      {
	    Statement stmtDeletePaymentPermission = con.createStatement();
        String strDeletePaymentPermissionQuery = "delete from SOP_PAYMENT_COMMISSION_DELETE";
        stmtDeletePaymentPermission.executeUpdate(strDeletePaymentPermissionQuery);
        stmtDeletePaymentPermission.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }	      
  }
  
  public static void updateUserPaymentPermission(Connection con,int userId , int statusID)
  {
	  try
      {
        Statement stat = con.createStatement();
        String strSql = "insert into SOP_PAYMENT_COMMISSION_DELETE (PERSON_ID,DELETE_STATUS_ID) values("+userId+","+statusID+") ";
        stat.executeUpdate(strSql);
        
        System.out.println("#################  " + strSql);

        stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }	      
  }
  
  
  
  public static Vector getUserPaymentPermission(Connection con)
  {
	  Vector vecUsersList = new Vector();
	   
	  try
      {		 
			Statement stmtUsersList = con.createStatement();
			String strUsersListQuery = " select * from SOP_PAYMENT_COMMISSION_DELETE";
			ResultSet rstUsersList = stmtUsersList
					.executeQuery(strUsersListQuery);
			while (rstUsersList.next()) {
				vecUsersList.addElement(rstUsersList.getInt("PERSON_ID"));
			}
			rstUsersList.close();
			stmtUsersList.close();
       
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }	  
      
      return vecUsersList;
  }
  
}
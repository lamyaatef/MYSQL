package com.mobinil.sds.core.system.dcm.user.dao;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.dcm.*;
import com.mobinil.sds.core.system.dcm.user.model.*;
import com.mobinil.sds.core.system.dcm.function.model.*;

public class DCMUserDAO 
{
  public DCMUserDAO()
  {
  }

  public static int getDCMUserLevelTypeId(Connection con,String strUserId)
  {
    int userLevelId = -1;
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from DCM_USER where USER_ID = "+strUserId+" ";
     Utility.logger.debug(strSql); 
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       userLevelId = res.getInt("USER_LEVEL_TYPE_ID");
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return userLevelId; 
  }

  public static int getDCMUserId(Connection con,String strUserId)
  {
    int dcmUserId = -1;
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from DCM_USER where USER_ID = "+strUserId+" ";
     Utility.logger.debug(strSql); 
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       dcmUserId = res.getInt("DCM_USER_ID");
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return dcmUserId; 
  }

  public static Vector getDCMUserByLevel(Connection con,int levelTypeId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from VW_DCM_USER where USER_LEVEL_TYPE_ID = "+levelTypeId+" order by USER_EMAIL";
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getDCMUserByLevel(Connection con,String userId,int levelTypeId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from VW_DCM_USER where MANAGER_DCM_USER_ID = (select dcm_user_id from DCM_USER where user_id = "+userId+") and USER_LEVEL_TYPE_ID = "+levelTypeId+" order by USER_EMAIL";
     
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getDCMUserForRegionalManager(Connection con,String userId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from VW_DCM_USER where MANAGER_DCM_USER_ID = (select dcm_user_id from DCM_USER where user_id = "+userId+") and USER_LEVEL_TYPE_ID = 2 order by USER_EMAIL";
     
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     }

     String strSql2 = "select vw_dcm_user.* "+
                       " from vw_dcm_user,dcm_user_assigned_user "+ 
                       " where vw_dcm_user.USER_ID = dcm_user_assigned_user.ASSIGNED_USER_ID "+ 
                       " and dcm_user_assigned_user.USER_ID = "+userId+" "+
                       " and vw_dcm_user.USER_LEVEL_TYPE_ID = 2 "+
                       " order by USER_LEVEL_TYPE_ID,USER_FULL_NAME ";

     res= stat.executeQuery(strSql2);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getDCMUserForManager(Connection con,String userId,String userAssignedToId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from VW_DCM_USER where MANAGER_DCM_USER_ID = (select dcm_user_id from DCM_USER where user_id = "+userId+") and USER_LEVEL_TYPE_ID >= 2 and USER_ID <> "+userAssignedToId+" order by USER_EMAIL";
     
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     }

     String strSql2 = "select vw_dcm_user.* "+
                       " from vw_dcm_user,dcm_user_assigned_user "+ 
                       " where vw_dcm_user.USER_ID = dcm_user_assigned_user.ASSIGNED_USER_ID "+ 
                       " and dcm_user_assigned_user.USER_ID = "+userId+" "+
                       " and vw_dcm_user.USER_LEVEL_TYPE_ID >= 2 "+
                       " and vw_dcm_user.USER_ID <> "+userAssignedToId+" "+
                       " order by USER_LEVEL_TYPE_ID,USER_FULL_NAME ";

     res= stat.executeQuery(strSql2);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }
  public static DCMUserDetailModel getDCMUserDetailById(Connection con,String userId)
  {
    DCMUserDetailModel dcmUserDetailModel = null;
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select VW_DCM_USER_DETAIL.* from VW_DCM_USER_DETAIL,VW_DCM_USER "+
                      " where VW_DCM_USER.USER_ID = "+userId+" "+
                      " and VW_DCM_USER.USER_DETAIL_ID = VW_DCM_USER_DETAIL.USER_DETAIL_ID";
                      System.out.println("The query issssssssssssssssssss " + strSql);
      Utility.logger.debug("The query isssssssssssssssss  " + strSql);
     
     ResultSet res= stat.executeQuery(strSql);     
     if(res.next())
     {
       dcmUserDetailModel = new DCMUserDetailModel(res);
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return dcmUserDetailModel; 
  }

  public static Vector getDCMUserGroupFunctionTargetByUser(Connection con,String userId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from VW_DCM_USER_GRP_FUN_TARGET "+
                      " where USER_ID = "+userId+" ";
     
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       vec.add(new DCMUserGroupFunctionTargetModel(res));
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static void deleteDCMUserGroupFunctionTargetByUser(Connection con,String userGroupFunctionTargetId)
  {
    try
    {
     Statement stat = con.createStatement();
     String strSql = "delete from VW_DCM_USER_GRP_FUN_TARGET "+
                      " where USER_GROUP_FUNCTION_TARGET_ID = "+userGroupFunctionTargetId+" ";
     
     stat.executeUpdate(strSql);     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }
  
  public static Vector getDCMUserDetailByStatusId(Connection con,String userId,String userDetailStatusId)
  {
    Vector dcmUserDetailList = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select VW_DCM_USER_DETAIL.* from VW_DCM_USER_DETAIL "+
                      " where VW_DCM_USER_DETAIL.USER_ID = "+userId+" "+
                      " and VW_DCM_USER_DETAIL.USER_DETAIL_STATUS_ID = "+userDetailStatusId+" ";
     
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       DCMUserDetailModel dcmUserDetailModel = new DCMUserDetailModel(res);
       dcmUserDetailList.add(dcmUserDetailModel);
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return dcmUserDetailList; 
  }
  
  public static Vector getDCMUserByManager(Connection con,int dcmUserId,String strUserId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from VW_DCM_USER where MANAGER_DCM_USER_ID = "+dcmUserId+" order by USER_LEVEL_TYPE_ID,USER_FULL_NAME";
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     }

     String strSql2 = "select vw_dcm_user.* "+
                       " from vw_dcm_user,dcm_user_assigned_user "+ 
                       " where vw_dcm_user.USER_ID = dcm_user_assigned_user.ASSIGNED_USER_ID "+ 
                       " and dcm_user_assigned_user.USER_ID = "+strUserId+" "+
                       " order by USER_LEVEL_TYPE_ID,USER_FULL_NAME ";

     Utility.logger.debug(strSql2); 
     res= stat.executeQuery(strSql2);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     } 

     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  /*public static Vector getDCMUserByManagerByFilter(Connection con,int dcmUserId,String strUserId,String searchDcmUserName,String searchDcmUserStatusTypeId,String searchDcmUserLevelTypeId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from VW_DCM_USER where MANAGER_DCM_USER_ID = "+dcmUserId+" ";
     if(searchDcmUserName.compareTo("")!=0) strSql += " and USER_FULL_NAME like '%"+searchDcmUserName+"%' ";        
     if(searchDcmUserStatusTypeId.compareTo("")!=0) strSql += " and USER_STATUS_TYPE_ID = "+searchDcmUserStatusTypeId+" "; 
     if(searchDcmUserLevelTypeId.compareTo("")!=0) strSql += " and USER_LEVEL_TYPE_ID = "+searchDcmUserLevelTypeId+" "; 
     strSql += " order by USER_LEVEL_TYPE_ID,USER_FULL_NAME";
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     }

     String strSql2 = "select vw_dcm_user.* "+
                       " from vw_dcm_user,dcm_user_assigned_user "+ 
                       " where vw_dcm_user.USER_ID = dcm_user_assigned_user.ASSIGNED_USER_ID "+ 
                       " and dcm_user_assigned_user.USER_ID = "+strUserId+" ";
     if(searchDcmUserName.compareTo("")!=0) strSql2 += " and USER_FULL_NAME like '%"+searchDcmUserName+"%' ";        
     if(searchDcmUserStatusTypeId.compareTo("")!=0) strSql2 += " and USER_STATUS_TYPE_ID = "+searchDcmUserStatusTypeId+" "; 
     if(searchDcmUserLevelTypeId.compareTo("")!=0) strSql2 += " and USER_LEVEL_TYPE_ID = "+searchDcmUserLevelTypeId+" "; 
     strSql2 += " order by USER_LEVEL_TYPE_ID,USER_FULL_NAME";
      

     Utility.logger.debug(strSql2); 
     res= stat.executeQuery(strSql2);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     } 

     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }*/

  public static Vector getDCMUserByManagerByFilter(Connection con,int dcmUserId,String strUserId,String searchDcmUserName,String searchDcmUserStatusTypeId,String searchDcmUserLevelTypeId,String functionId,String groupId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select VW_DCM_USER.* from VW_DCM_USER ";

     strSql += " where VW_DCM_USER.MANAGER_DCM_USER_ID = "+dcmUserId+" ";
     if(searchDcmUserName.compareTo("")!=0) strSql += " and VW_DCM_USER.USER_FULL_NAME like '%"+searchDcmUserName+"%' ";        
     if(searchDcmUserStatusTypeId.compareTo("")!=0) strSql += " and VW_DCM_USER.USER_STATUS_TYPE_ID = "+searchDcmUserStatusTypeId+" "; 
     if(searchDcmUserLevelTypeId.compareTo("")!=0) strSql += " and VW_DCM_USER.USER_LEVEL_TYPE_ID = "+searchDcmUserLevelTypeId+" "; 
     if(functionId.compareTo("")!=0)strSql += " and VW_DCM_USER.USER_ID in (select USER_ID from DCM_USER_FUNCTION where FUNCTION_ID = "+functionId+") ";
     if(groupId.compareTo("")!=0)strSql += " and VW_DCM_USER.USER_ID in (select USER_ID from DCM_USER_GROUP_FUNCTION_TARGET where GROUP_ID = "+groupId+") ";   
     strSql += " order by VW_DCM_USER.USER_LEVEL_TYPE_ID,VW_DCM_USER.USER_FULL_NAME";
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     }

     String strSql2 = "select vw_dcm_user.* "+
                       " from vw_dcm_user,dcm_user_assigned_user "; 
     if(functionId.compareTo("")!=0)
     {
       strSql2 += ",DCM_USER_FUNCTION where VW_DCM_USER.USER_ID = DCM_USER_FUNCTION.USER_ID and "+
                  " DCM_USER_FUNCTION.FUNCTION_ID = "+functionId+" and ";
     }
     else
     {
       strSql2 += " where ";
     }
      
     strSql2 +=        " vw_dcm_user.USER_ID = dcm_user_assigned_user.ASSIGNED_USER_ID "+ 
                       " and dcm_user_assigned_user.USER_ID = "+strUserId+" ";
     if(searchDcmUserName.compareTo("")!=0) strSql2 += " and USER_FULL_NAME like '%"+searchDcmUserName+"%' ";        
     if(searchDcmUserStatusTypeId.compareTo("")!=0) strSql2 += " and USER_STATUS_TYPE_ID = "+searchDcmUserStatusTypeId+" "; 
     if(searchDcmUserLevelTypeId.compareTo("")!=0) strSql2 += " and USER_LEVEL_TYPE_ID = "+searchDcmUserLevelTypeId+" "; 
     strSql2 += " order by USER_LEVEL_TYPE_ID,USER_FULL_NAME";
      

     Utility.logger.debug(strSql2); 
     res= stat.executeQuery(strSql2);     
     while(res.next())
     {
       vec.add(new DCMUserModel(res));
     } 

     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }
  public static void insertAdmUserRole(Connection con,int userId,String roleId)
  {
    try
    {
     Statement stat = con.createStatement();
     String strSql = "insert into ADM_USER_ROLE(USER_ID,ROLE_ID) values("+userId+","+roleId+")";

     stat.executeUpdate(strSql);     
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void insertDcmUserGroupFunctionTarget(Connection con,String userId,String groupId,String functionId,String targetValue,String targetDurationTypeId)
  {
    try
    {
     Statement stat = con.createStatement();

     Long lUserGroupFunctionTargetID = Utility.getSequenceNextVal(con, "USER_GROUP_FUNCTION_TARGET_ID");
     String strSql = "insert into DCM_USER_GROUP_FUNCTION_TARGET(USER_GROUP_FUNCTION_TARGET_ID,USER_ID,GROUP_ID,FUNCTION_ID,TARGET_VALUE,TARGET_DURATION_TYPE_ID) "+
                     " values("+lUserGroupFunctionTargetID+","+userId+","+groupId+","+functionId+","+targetValue+","+targetDurationTypeId+")";

     stat.executeUpdate(strSql);     
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void updateDcmUserGroupFunctionTarget(Connection con,String userId,String groupId,String functionId,String targetValue,String targetDurationTypeId,String strUserGroupFunctionTargetID)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "update DCM_USER_GROUP_FUNCTION_TARGET set "+
                     " GROUP_ID = "+groupId+",TARGET_VALUE = "+targetValue+",TARGET_DURATION_TYPE_ID = "+targetDurationTypeId+"  "+
                     " where USER_GROUP_FUNCTION_TARGET_ID = "+strUserGroupFunctionTargetID+" ";

     stat.executeUpdate(strSql);     
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }
  
  public static void insertDcmUserAsignedUser(Connection con,String userId,String assignedUserId)
  {
    try
    {
     Statement stat = con.createStatement();
     
     String strSql2 = "select * from DCM_USER_ASSIGNED_USER where USER_ID = "+userId+" and ASSIGNED_USER_ID = "+assignedUserId+" " ;
     ResultSet res = stat.executeQuery(strSql2);
     if(res.next())
     {}
     else
     {
      Long lUserAssignedUserID = Utility.getSequenceNextVal(con, "SEQ_DCM_USER_ASSIGNED_USER_ID");
      String strSql = "insert into DCM_USER_ASSIGNED_USER(USER_ASSIGNED_USER_ID,USER_ID,ASSIGNED_USER_ID) "+
                      "values("+lUserAssignedUserID+","+userId+","+assignedUserId+")";
      stat.executeUpdate(strSql);     
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void insertDcmUserFunction(Connection con,String userId,String functionId)
  {
    try
    {
     Statement stat = con.createStatement();
     
     String strSql2 = "select * from DCM_USER_FUNCTION where USER_ID = "+userId+" and FUNCTION_ID = "+functionId+" " ;
     ResultSet res = stat.executeQuery(strSql2);
     if(res.next())
     {}
     else
     {
      Long lDcmUserFunctionID = Utility.getSequenceNextVal(con, "SEQ_DCM_USER_FUNCTION_ID");
      String strSql = "insert into DCM_USER_FUNCTION(USER_FUNCTION_ID,USER_ID,FUNCTION_ID) "+
                      "values("+lDcmUserFunctionID+","+userId+","+functionId+")";
      stat.executeUpdate(strSql);     
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void deleteDcmUserAsignedUser(Connection con,String userId,String assignedUserId)
  {
    try
    {
     Statement stat = con.createStatement();
     
     String strSql2 = "delete from DCM_USER_ASSIGNED_USER where USER_ID = "+userId+" and ASSIGNED_USER_ID = "+assignedUserId+" " ;
     stat.executeUpdate(strSql2);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void deleteDcmUserFunction(Connection con,String userId,String functionId)
  {
    try
    {
     Statement stat = con.createStatement();
     
     String strSql2 = "delete from DCM_USER_FUNCTION where USER_ID = "+userId+" and FUNCTION_ID = "+functionId+" " ;
     stat.executeUpdate(strSql2);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }
  public static Vector getDcmUserAssignedUser(Connection con,String userId)
  {
    Vector usersAssignedTo = new Vector();
    try
    {
     Statement stat = con.createStatement();
     
     String strSql2 = "select * from DCM_USER_ASSIGNED_USER where USER_ID = "+userId+" " ;
     ResultSet res = stat.executeQuery(strSql2);
     while(res.next())
     {  
      String strUserAssignedToId = res.getString("ASSIGNED_USER_ID");
      usersAssignedTo.add(strUserAssignedToId);
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return usersAssignedTo;
  }

  public static Vector getDcmUserAssignedFunctions(Connection con,String userId)
  {
    Vector functionsAssignedTo = new Vector();
    try
    {
     Statement stat = con.createStatement();
     
     String strSql2 = "select * from DCM_USER_FUNCTION where USER_ID = "+userId+" " ;
     ResultSet res = stat.executeQuery(strSql2);
     while(res.next())
     {  
      String strFunctionToId = res.getString("FUNCTION_ID");
      functionsAssignedTo.add(strFunctionToId);
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return functionsAssignedTo;
  }

  public static HashMap getDcmUsersAssignedFunctions(Connection con,Vector usersVec)
  {
    HashMap usersAssignedFunctions = new HashMap();
    try
    {
     for(int i=0;i<usersVec.size();i++)
     {
       DCMUserModel dcmUserModel = (DCMUserModel)usersVec.get(i); 
       String userId = dcmUserModel.getUserId(); 
       Vector functionsAssignedTo = new Vector(); 
       Statement stat = con.createStatement();
     
       String strSql2 = "select * from DCM_USER_FUNCTION where USER_ID = "+userId+" " ;
       ResultSet res = stat.executeQuery(strSql2);
       while(res.next())
       {  
        String strFunctionToId = res.getString("FUNCTION_ID");
        functionsAssignedTo.add(strFunctionToId);
       }
       res.close();
       stat.close();
       usersAssignedFunctions.put(userId,functionsAssignedTo);
     }
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return usersAssignedFunctions;
  }

  public static Vector getDcmUserAssignedFunctionModels(Connection con,String userId)
  {
    Vector functionsAssignedTo = new Vector();
    try
    {
     Statement stat = con.createStatement();
     
     String strSql2 = "select VW_DCM_FUNCTION.* from DCM_USER_FUNCTION,VW_DCM_FUNCTION "+
                      " where VW_DCM_FUNCTION.FUNCTION_ID = DCM_USER_FUNCTION.FUNCTION_ID "+
                      " and DCM_USER_FUNCTION.USER_ID = "+userId+" " ;
     ResultSet res = stat.executeQuery(strSql2);
     while(res.next())
     {  
      functionsAssignedTo.add(new FunctionModel(res));
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return functionsAssignedTo;
  }
  
  public static Long insertDcmUser(Connection con,int userId,String userLevelTypeId,String regionId,int managerDcmUserId,String userStatusTypeId,int userLevelId)
  {
    Long lDcmUserID = null;
    try
    {
     Statement stat = con.createStatement();
      String strSql = "";
     lDcmUserID = Utility.getSequenceNextVal(con, "SEQ_DCM_USER_ID");
     if(regionId.startsWith(","))
     //String strSql = "insert into DCM_USER(DCM_USER_ID,USER_ID,MANAGER_DCM_USER_ID,USER_LEVEL_TYPE_ID,USER_STATUS_TYPE_ID,REGION_ID,USER_LEVEL_ID) "+
                    // " values("+lDcmUserID+","+userId+","+managerDcmUserId+","+userLevelTypeId+","+userStatusTypeId+","+regionId+","+userLevelId+")";

    {
      strSql = "insert into DCM_USER(DCM_USER_ID,USER_ID,MANAGER_DCM_USER_ID,USER_LEVEL_TYPE_ID,USER_STATUS_TYPE_ID,USER_LEVEL_ID) "+
                     " values("+lDcmUserID+","+userId+","+managerDcmUserId+","+userLevelTypeId+","+userStatusTypeId+","+userLevelId+")";
      //System.out.println("The first query issssssssssssss "+ strSql);
    }
    else
    {      
      strSql = "insert into DCM_USER(DCM_USER_ID,USER_ID,MANAGER_DCM_USER_ID,USER_LEVEL_TYPE_ID,USER_STATUS_TYPE_ID,REGION_ID,USER_LEVEL_ID) "+
                    " values("+lDcmUserID+","+userId+","+managerDcmUserId+","+userLevelTypeId+","+userStatusTypeId+","+regionId+","+userLevelId+")";
      //System.out.println("The first query issssssssssssss "+ strSql);
    }
     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);    

    if(regionId.startsWith(","))
    {
     StringTokenizer st = new StringTokenizer(regionId,",");
      while (st.hasMoreTokens()) 
      {
         regionId = st.nextToken();
         DCMUserDAO.insertDcmUserRegion(con,lDcmUserID,regionId);
      }
    }
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return lDcmUserID;
  }

  public static Long insertDcmUserDetail(Connection con,int userId,String userFullName,String userAddress,String userEmail,String userMobile,String regionId,String userDetailStatusId,String sysUserId)
  {
    Long lDcmUserDetailID = null;
    try
    {
     Statement stat = con.createStatement();
     String strSql = "";
     lDcmUserDetailID = Utility.getSequenceNextVal(con, "SEQ_DCM_USER_DETAIL_ID");         
     strSql = "insert into DCM_USER_DETAIL(USER_DETAIL_ID,USER_ID,USER_FULL_NAME,USER_ADDRESS,USER_EMAIL,USER_MOBILE,REGION_ID,USER_DETAIL_STATUS_ID,CREATION_TIMESTAMP,CREATION_USER_ID) "+
                     " values("+lDcmUserDetailID+","+userId+",'"+userFullName+"','"+userAddress+"','"+userEmail+"','"+userMobile+"',"+regionId+","+userDetailStatusId+",SYSDATE,"+sysUserId+")";

     System.out.println("The insert query isssssssssss "+ strSql);
     
     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);     
     
     stat.close();     
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return lDcmUserDetailID;
  }

   public static Long insertMultipleDcmUserDetail(Connection con,Long dcmUserId,int userId,String userFullName,String userAddress,String userEmail,String userMobile,String regionId,String userDetailStatusId,String sysUserId)
  {
    Long lDcmUserDetailID = null;
    try
    {
     Statement stat = con.createStatement();
     String strSql = "";
     lDcmUserDetailID = Utility.getSequenceNextVal(con, "SEQ_DCM_USER_DETAIL_ID");
     if(regionId.startsWith(","))
     {
     //String strSql = "insert into DCM_USER_DETAIL(USER_DETAIL_ID,USER_ID,USER_FULL_NAME,USER_ADDRESS,USER_EMAIL,USER_MOBILE,REGION_ID,USER_DETAIL_STATUS_ID,CREATION_TIMESTAMP,CREATION_USER_ID) "+
                     //" values("+lDcmUserDetailID+","+userId+",'"+userFullName+"','"+userAddress+"','"+userEmail+"','"+userMobile+"',"+regionId+","+userDetailStatusId+",SYSDATE,"+sysUserId+")";

     strSql = "insert into DCM_USER_DETAIL(USER_DETAIL_ID,USER_ID,USER_FULL_NAME,USER_ADDRESS,USER_EMAIL,USER_MOBILE,USER_DETAIL_STATUS_ID,CREATION_TIMESTAMP,CREATION_USER_ID) "+
                    " values("+lDcmUserDetailID+","+userId+",'"+userFullName+"','"+userAddress+"','"+userEmail+"','"+userMobile+"',"+userDetailStatusId+",SYSDATE,"+sysUserId+")";

     //System.out.println("The first details query isssssssssss " +strSql );
     }
     else
     {
       strSql = "insert into DCM_USER_DETAIL(USER_DETAIL_ID,USER_ID,USER_FULL_NAME,USER_ADDRESS,USER_EMAIL,USER_MOBILE,REGION_ID,USER_DETAIL_STATUS_ID,CREATION_TIMESTAMP,CREATION_USER_ID) "+
                     " values("+lDcmUserDetailID+","+userId+",'"+userFullName+"','"+userAddress+"','"+userEmail+"','"+userMobile+"',"+regionId+","+userDetailStatusId+",SYSDATE,"+sysUserId+")";

      //System.out.println("The first details query isssssssssss " +strSql );
     }

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);     
      StringTokenizer st = new StringTokenizer(regionId,",");
      while (st.hasMoreTokens()) 
      {
         regionId = st.nextToken();
         DCMUserDAO.insertDcmDetailsUserRegion(con,lDcmUserDetailID,dcmUserId,regionId);
      }
     stat.close();
     
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return lDcmUserDetailID;
  }


  public static void insertDcmUserRegion(Connection con,Long lDcmUserID,String strRegionId)
  {
    try
    {
      Statement stat = con.createStatement();

      //Long lCaseCCReceiverID = Utility.getSequenceNextVal(con, "SEQ_HLP_CASE_CC_RECEIVER_ID");
      
      String strSql = "insert into DCM_USER_REGION(DCM_USER_ID,REGION_ID) "+
                      " values("+lDcmUserID+","+strRegionId+")";

      //System.out.println("The second Query isssssssssssssssssssss " + strSql);

      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

public static void insertDcmDetailsUserRegion(Connection con,Long lDcmUserDetailID,Long lDcmUserID,String strRegionId)
  {
    try
    {
      Statement stat = con.createStatement();

      //Long lCaseCCReceiverID = Utility.getSequenceNextVal(con, "SEQ_HLP_CASE_CC_RECEIVER_ID");
      

      String strSql = "update DCM_USER_REGION SET USER_DETAIL_ID = '"+lDcmUserDetailID+"'"+
                      " where DCM_USER_ID = '"+lDcmUserID+"'";

      //System.out.println("The second Query isssssssssssssssssssss " + strSql);

      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }


  public static void insertDcmVisitPlan(Connection con,String planDate,String posId,String userId,String functionId,String visitComments,String systemUserId,String visitPlanStatusTypeId)
  {
    Long lVisitPlanID = null;
    try
    {
     Statement stat = con.createStatement();

     lVisitPlanID = Utility.getSequenceNextVal(con, "SEQ_VISIT_PLAN_ID");
     String strSql = "insert into DCM_VISIT_PLAN(VISIT_PLAN_ID,VISIT_PLAN_DATE,POS_ID,USER_ID,FUNCTION_ID,VISIT_COMMENT,CREATOR_USER_ID,CREATION_DATE,LAST_MODIFIED_USER_ID,LAST_MODIFIED_DATE,VISIT_PLAN_STATUS_TYPE_ID) "+
                     " values("+lVisitPlanID+",TO_DATE('"+planDate+"','mm/dd/yyyy'),"+posId+","+userId+","+functionId+",'"+visitComments+"',"+systemUserId+",SYSDATE,"+systemUserId+",SYSDATE,"+visitPlanStatusTypeId+")";

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);     
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void insertDcmActualVisit(Connection con,String posCode,String userId,String actualVisitDate,String visitComments,String functionId)
  {
    Long lActualVisitID = null;
    try
    {
     Statement stat = con.createStatement();

     lActualVisitID = Utility.getSequenceNextVal(con, "SEQ_ACTUAL_VISIT_ID");
     String strSql = "insert into DCM_ACTUAL_VISIT(ACTUAL_VISIT_ID,ACTUAL_VISIT_DATE,USER_ID,POS_CODE,ACTUAL_VISIT_COMMENT,FUNCTION_ID) "+
                     " values("+lActualVisitID+",TO_DATE('"+actualVisitDate+"','mm/dd/yyyy'),"+userId+",'"+posCode+"','"+visitComments+"',"+functionId+")";

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);     
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void updateDcmActualVisit(Connection con,String posCode,String userId,String actualVisitDate,String visitComments,String functionId,String actualVisitId)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "update DCM_ACTUAL_VISIT set ACTUAL_VISIT_DATE = TO_DATE('"+actualVisitDate+"','mm/dd/yyyy'),ACTUAL_VISIT_COMMENT = '"+visitComments+"',FUNCTION_ID = "+functionId+" "+
                     " where ACTUAL_VISIT_ID = "+actualVisitId+" "; 

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);     
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }
  

  public static void insertDcmActualVisitDetail(Connection con,String actualVisitId,String functionId,String functionForiegnKeyId)
  {
    Long lActualVisitDetailID = null;
    try
    {
     Statement stat = con.createStatement();

     lActualVisitDetailID = Utility.getSequenceNextVal(con, "SEQ_ACTUAL_VISIT_DETAIL_ID");
     String strSql = "insert into DCM_ACTUAL_VISIT_DETAIL(ACTUAL_VISIT_DETAIL_ID,ACTUAL_VISIT_ID,FUNCTION_ID,FUNCTION_FORIEGN_KEY_ID) "+
                     " values("+lActualVisitDetailID+","+actualVisitId+","+functionId+","+functionForiegnKeyId+")";

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);     
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void updateDcmVisitPlan(Connection con,String planDate,String posId,String userId,String functionId,String visitComments,String systemUserId,String visitPlanStatusTypeId,String visitPlanId)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "update DCM_VISIT_PLAN set VISIT_PLAN_DATE = TO_DATE('"+planDate+"','mm/dd/yyyy'),"+
                     " POS_ID = "+posId+",USER_ID = "+userId+",FUNCTION_ID = "+functionId+",VISIT_COMMENT = '"+visitComments+"',"+
                     " LAST_MODIFIED_USER_ID = "+systemUserId+",LAST_MODIFIED_DATE = SYSDATE,VISIT_PLAN_STATUS_TYPE_ID = "+visitPlanStatusTypeId+" "+
                     " where VISIT_PLAN_ID = "+visitPlanId+"  ";

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);     
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void updateDcmVisitPlan(Connection con,String visitPlanStatusTypeId,String visitPlanId)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "update DCM_VISIT_PLAN set VISIT_PLAN_STATUS_TYPE_ID = "+visitPlanStatusTypeId+" "+
                     " where VISIT_PLAN_ID = "+visitPlanId+"  ";

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);     
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void updateDcmUserStatusType(Connection con,String dcmUserId,String userStatusTypeId)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "update DCM_USER set USER_STATUS_TYPE_ID = "+userStatusTypeId+" where DCM_USER_ID = "+dcmUserId+" ";
     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);     
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }


  public static void updateDcmUser(Connection con,String userId,String userDetailId)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql3 = "update DCM_USER_DETAIL set USER_DETAIL_STATUS_ID = 2 where "+
                      " USER_DETAIL_ID = (select USER_DETAIL_ID from DCM_USER where USER_ID = "+userId+" ) "; 
     stat.executeUpdate(strSql3);  

     String strSql = "update DCM_USER set USER_DETAIL_ID = "+userDetailId+" where USER_ID = "+userId+" "; 
     stat.executeUpdate(strSql);     

     String strSql2 = "update DCM_USER_DETAIL set USER_DETAIL_STATUS_ID = 1 where USER_DETAIL_ID = "+userDetailId+" "; 
     stat.executeUpdate(strSql2);

     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void updateDcmUserDetailStatus(Connection con,String dcmUserDetailId,String dcmUserDetailStatusId)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql2 = "update DCM_USER_DETAIL set USER_DETAIL_STATUS_ID = "+dcmUserDetailStatusId+" where USER_DETAIL_ID = "+dcmUserDetailId+" "; 
     stat.executeUpdate(strSql2);     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void updateDcmUser(Connection con,String dcmUserId,String userLevelTypeId,String regionId,String userStatusTypeId)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "update DCM_USER set USER_LEVEL_TYPE_ID = "+userLevelTypeId+" "+
                     " ,USER_STATUS_TYPE_ID = "+userStatusTypeId+" ,REGION_ID,USER_LEVEL_ID = "+regionId+" "+
                     " where DCM_USER_ID = "+dcmUserId+" ";

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);     
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

 /* public static Long insertDcmUserDetail(Connection con,int userId,String userFullName,String userAddress,String userEmail,String userMobile,String regionId,String userDetailStatusId,String sysUserId)
  {
    Long lDcmUserDetailID = null;
    try
    {
     Statement stat = con.createStatement();
     String strSql = "";
     lDcmUserDetailID = Utility.getSequenceNextVal(con, "SEQ_DCM_USER_DETAIL_ID");
     if(regionId.startsWith(","))
     {
     //String strSql = "insert into DCM_USER_DETAIL(USER_DETAIL_ID,USER_ID,USER_FULL_NAME,USER_ADDRESS,USER_EMAIL,USER_MOBILE,REGION_ID,USER_DETAIL_STATUS_ID,CREATION_TIMESTAMP,CREATION_USER_ID) "+
                     //" values("+lDcmUserDetailID+","+userId+",'"+userFullName+"','"+userAddress+"','"+userEmail+"','"+userMobile+"',"+regionId+","+userDetailStatusId+",SYSDATE,"+sysUserId+")";

     strSql = "insert into DCM_USER_DETAIL(USER_DETAIL_ID,USER_ID,USER_FULL_NAME,USER_ADDRESS,USER_EMAIL,USER_MOBILE,USER_DETAIL_STATUS_ID,CREATION_TIMESTAMP,CREATION_USER_ID) "+
                     " values("+lDcmUserDetailID+","+userId+",'"+userFullName+"','"+userAddress+"','"+userEmail+"','"+userMobile+"',"+userDetailStatusId+",SYSDATE,"+sysUserId+")";

     System.out.println("The first details query isssssssssss " +strSql );
     }
     else
     {
       strSql = "insert into DCM_USER_DETAIL(USER_DETAIL_ID,USER_ID,USER_FULL_NAME,USER_ADDRESS,USER_EMAIL,USER_MOBILE,REGION_ID,USER_DETAIL_STATUS_ID,CREATION_TIMESTAMP,CREATION_USER_ID) "+
                     " values("+lDcmUserDetailID+","+userId+",'"+userFullName+"','"+userAddress+"','"+userEmail+"','"+userMobile+"',"+regionId+","+userDetailStatusId+",SYSDATE,"+sysUserId+")";

      System.out.println("The first details query isssssssssss " +strSql );
     }

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);     
      StringTokenizer st = new StringTokenizer(regionId,",");
      while (st.hasMoreTokens()) 
      {
         regionId = st.nextToken();
         //CaseDAO.insertCaseCCReceiver(con,lDcmUserDetailID,regionId);
      }
     stat.close();
     
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return lDcmUserDetailID;
  }*/

  /*public static void insertDcmDetailsMultipleRegion(Connection con,Long lDcmUserDetailID,String strRegionId)
  {
    try
    {
      Statement stat = con.createStatement();

      //Long lCaseCCReceiverID = Utility.getSequenceNextVal(con, "SEQ_HLP_CASE_CC_RECEIVER_ID");
      
      String strSql = "insert into DCM(CASE_CC_RECEIVER_ID,CASE_DETAIL_ID,CC_RECEIVER_USER_ID) "+
                      " values("+lCaseCCReceiverID+","+lCaseDetailId+","+strCCReceiverId+")";

      stat.execute(strSql);  
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }*/


  public static boolean checkDcmUserDetail(Connection con,int userId,String userDetailStatusId)
  {
    boolean checkIfStatusExists = false;
    try
    {
     Statement stat = con.createStatement();

     String strSql = "select * from DCM_USER_DETAIL where USER_ID = "+userId+" and USER_DETAIL_STATUS_ID = "+userDetailStatusId+" ";

     ResultSet res = stat.executeQuery(strSql);     
     if(res.next())
     {
        checkIfStatusExists = true;
     }

     stat.close();
     
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return checkIfStatusExists;
  }

  public static boolean checkDcmUserGroupFunctionTarget(Connection con,String userGroupFunctionTargetId,String groupId,String functionId)
  {
    boolean checkIfExists = false;
    try
    {
     Statement stat = con.createStatement();

     String strSql = "select * from DCM_USER_GROUP_FUNCTION_TARGET where GROUP_ID = "+groupId+" and FUNCTION_ID = "+functionId+" ";
     if(userGroupFunctionTargetId.compareTo("")!=0)
     {
       strSql += "and USER_GROUP_FUNCTION_TARGET_ID <> "+userGroupFunctionTargetId+" ";
     }

     ResultSet res = stat.executeQuery(strSql);     
     if(res.next())
     {
        checkIfExists = true;
     }

     stat.close();
     
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return checkIfExists;
  }

  public static int getDCMUserLevelId(Connection con,String strUserId)
  {
    int dcmUserLevelId = -1;
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from DCM_USER where USER_ID = "+strUserId+" ";
     Utility.logger.debug(strSql); 
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
       dcmUserLevelId = res.getInt("USER_LEVEL_ID");
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return dcmUserLevelId; 
  } 

  public static int getDcmUserRegionId(Connection con,String strUserId)
  {
    int dcmUserRegionId = 0;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select VW_DCM_USER_DETAIL.* from VW_DCM_USER_DETAIL,VW_DCM_USER "+
                      " where VW_DCM_USER.USER_ID = "+strUserId+" "+
                      " and VW_DCM_USER.USER_DETAIL_ID = VW_DCM_USER_DETAIL.USER_DETAIL_ID";
      Utility.logger.debug("The query issssssssssss " + strSql);
      ResultSet res = stat.executeQuery(strSql);
      while (res.next())
      {
        dcmUserRegionId = res.getInt("REGION_ID");
      }
      res.close();
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return dcmUserRegionId;
  }


  public static Vector getDCMVisitPlanByManagerUserId(Connection con,Vector vecSalesAgents)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res = null;
     for(int i=0;i<vecSalesAgents.size();i++)
     {
        DCMUserModel dcmUserModel = (DCMUserModel)vecSalesAgents.get(i);
        String dcmUserId = dcmUserModel.getDcmUserId();
        String userId = dcmUserModel.getUserId();

        String strSql = "select VW_DCM_VISIT_PLAN.* from VW_DCM_VISIT_PLAN where VW_DCM_VISIT_PLAN.USER_ID = "+userId+" ";
        Utility.logger.debug(strSql); 
        res= stat.executeQuery(strSql);     
        while(res.next())
        {
          vec.add(new VisitPlanModel(res));
        }   
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getDCMVisitPlanByManagerUserId(Connection con,String userId)
  {
    Vector vec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select VW_DCM_VISIT_PLAN.* from VW_DCM_VISIT_PLAN where VW_DCM_VISIT_PLAN.USER_ID = "+userId+" ";
      ResultSet res= stat.executeQuery(strSql);     
      while(res.next())
      {
        vec.add(new VisitPlanModel(res));
      }   
      res.close();
      stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static Vector getDCMVisitPlanByFilter(Connection con,Vector vecSalesAgents,String salesAgentName,String posCode,String posName,String functionId,String visitStatusId,String creationDateFrom,String creationDateTo)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res = null;
     for(int i=0;i<vecSalesAgents.size();i++)
     {
        DCMUserModel dcmUserModel = (DCMUserModel)vecSalesAgents.get(i);
        String dcmUserId = dcmUserModel.getDcmUserId();
        String userId = dcmUserModel.getUserId();

        String strSql = "select VW_DCM_VISIT_PLAN.* from VW_DCM_VISIT_PLAN where VW_DCM_VISIT_PLAN.USER_ID = "+userId+" ";
        if(salesAgentName.compareTo("")!=0)strSql += " and VW_DCM_VISIT_PLAN.USER_FULL_NAME like '%"+salesAgentName+"%'"; 
        if(posName.compareTo("")!=0)strSql += " and VW_DCM_VISIT_PLAN.POS_NAME like '%"+posName+"%'";
        if(posCode.compareTo("")!=0)strSql += " and VW_DCM_VISIT_PLAN.POS_CODE = '"+posCode+"'";
        if(functionId.compareTo("")!=0)strSql += " and VW_DCM_VISIT_PLAN.FUNCTION_ID = '"+functionId+"'";
        if(visitStatusId.compareTo("")!=0)strSql += " and VW_DCM_VISIT_PLAN.VISIT_PLAN_STATUS_TYPE_ID = '"+visitStatusId+"'";
        if(creationDateFrom.compareTo("*")!=0)strSql += " and VW_DCM_VISIT_PLAN.CREATION_DATE >= TO_DATE('"+creationDateFrom+"','mm/dd/yyyy')";
        if(creationDateTo.compareTo("*")!=0)strSql += " and VW_DCM_VISIT_PLAN.CREATION_DATE <= TO_DATE('"+creationDateTo+"','mm/dd/yyyy')+1";
        Utility.logger.debug(strSql); 
        res= stat.executeQuery(strSql);     
        while(res.next())
        {
          vec.add(new VisitPlanModel(res));
        }   
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }


  public static Vector getDCMActualVisitByFilter(Connection con,String userId,String posCode,String posName,String actualVisitDateFrom,String actualVisitDateTo)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res = null;
     
      String strSql = "select VW_DCM_ACTUAL_VISIT.* from VW_DCM_ACTUAL_VISIT where VW_DCM_ACTUAL_VISIT.USER_ID = "+userId+" ";
      if(posName.compareTo("")!=0)strSql += " and VW_DCM_ACTUAL_VISIT.POS_NAME like '%"+posName+"%'";
      if(posCode.compareTo("")!=0)strSql += " and VW_DCM_ACTUAL_VISIT.POS_CODE = '"+posCode+"'";
      if(actualVisitDateFrom.compareTo("*")!=0)strSql += " and VW_DCM_ACTUAL_VISIT.ACTUAL_VISIT_DATE >= TO_DATE('"+actualVisitDateFrom+"','mm/dd/yyyy')";
      if(actualVisitDateTo.compareTo("*")!=0)strSql += " and VW_DCM_ACTUAL_VISIT.ACTUAL_VISIT_DATE <= TO_DATE('"+actualVisitDateTo+"','mm/dd/yyyy')";
      Utility.logger.debug(strSql); 
      res= stat.executeQuery(strSql);     
      while(res.next())
      {
        vec.add(new ActualVisitModel(res));
      }   
     
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static ActualVisitModel getDCMActualVisitById(Connection con,String actualVisitId)
  {
    ActualVisitModel actualVisitModel = null;
    try
    {
     Statement stat = con.createStatement();
     ResultSet res = null;
     
      String strSql = "select * from VW_DCM_ACTUAL_VISIT where VW_DCM_ACTUAL_VISIT.ACTUAL_VISIT_ID = "+actualVisitId+" ";
      res= stat.executeQuery(strSql);     
      if(res.next())
      {
        actualVisitModel = new ActualVisitModel(res);
      }   
     
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return actualVisitModel; 
  }

  public static Vector getDCMActualVisitDetailById(Connection con,String actualVisitId)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res = null;
     
      String strSql = "select * from VW_DCM_ACTUAL_VISIT_DETAIL where ACTUAL_VISIT_ID = "+actualVisitId+" order by FUNCTION_ID";
      res= stat.executeQuery(strSql);     
      while(res.next())
      {
        vec.add(new ActualVisitDetailModel(res));
      }   
     
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }
  
  public static Vector getDCMVisitPlanByFilter(Connection con,String userId,String salesAgentName,String posCode,String posName,String functionId,String visitStatusId,String creationDateFrom,String creationDateTo)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res = null;

      String strSql = "select VW_DCM_VISIT_PLAN.* from VW_DCM_VISIT_PLAN where VW_DCM_VISIT_PLAN.USER_ID = "+userId+" ";
      if(salesAgentName.compareTo("")!=0)strSql += " and VW_DCM_VISIT_PLAN.USER_FULL_NAME like '%"+salesAgentName+"%'"; 
      if(posName.compareTo("")!=0)strSql += " and VW_DCM_VISIT_PLAN.POS_NAME like '%"+posName+"%'";
      if(posCode.compareTo("")!=0)strSql += " and VW_DCM_VISIT_PLAN.POS_CODE = '"+posCode+"'";
      if(functionId.compareTo("")!=0)strSql += " and VW_DCM_VISIT_PLAN.FUNCTION_ID = '"+functionId+"'";
      if(visitStatusId.compareTo("")!=0)strSql += " and VW_DCM_VISIT_PLAN.VISIT_PLAN_STATUS_TYPE_ID = '"+visitStatusId+"'";
      if(creationDateFrom.compareTo("*")!=0)strSql += " and VW_DCM_VISIT_PLAN.CREATION_DATE >= TO_DATE('"+creationDateFrom+"','mm/dd/yyyy')";
      if(creationDateTo.compareTo("*")!=0)strSql += " and VW_DCM_VISIT_PLAN.CREATION_DATE <= TO_DATE('"+creationDateTo+"','mm/dd/yyyy')+1";
      Utility.logger.debug(strSql); 
      res= stat.executeQuery(strSql);     
      while(res.next())
      {
        vec.add(new VisitPlanModel(res));
      }   
     
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static VisitPlanModel getDCMVisitPlanById(Connection con,String visitPlanId)
  {
    VisitPlanModel visitPlanModel = null;
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "select VW_DCM_VISIT_PLAN.* from VW_DCM_VISIT_PLAN where VW_DCM_VISIT_PLAN.VISIT_PLAN_ID = "+visitPlanId+" ";
      Utility.logger.debug(strSql); 
      ResultSet res = stat.executeQuery(strSql);     
      if(res.next())
      {
        visitPlanModel = new VisitPlanModel(res);
      }   
     
      res.close();
      stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return visitPlanModel; 
  }

  public static String getFunctionName(Connection con,String functionId)
  {
    String functionName = "";
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "select * from DCM_FUNCTION where FUNCTION_ID = "+functionId+" ";
      //Utility.logger.debug(strSql); 
      ResultSet res = stat.executeQuery(strSql);     
      if(res.next())
      {
        functionName = res.getString("FUNCTION_NAME");
      }   
     
      res.close();
      stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return functionName; 
  }

  public static String getGroupName(Connection con,String groupId)
  {
    String groupName = "";
    try
    {
      Statement stat = con.createStatement();
      
      String strSql = "select * from DCM_GROUP where GROUP_ID = "+groupId+" ";
      //Utility.logger.debug(strSql); 
      ResultSet res = stat.executeQuery(strSql);     
      if(res.next())
      {
        groupName = res.getString("GROUP_NAME");
      }   
     
      res.close();
      stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return groupName; 
  }
}
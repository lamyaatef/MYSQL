package com.mobinil.sds.core.system.dcm.user.model;
import java.sql.*;
import java.io.*;

public class DCMUserGroupFunctionTargetModel implements Serializable
{
  String userGroupFunctionTargetId;  
  String userId;
  String userFullName;
  String userEmail;
  String groupId;                       
  String groupName; 
  String functionId;
  String functionName;
  String targetValue;                   
  String targetDurationTypeId;
  String targetDurationTypeName;
  String numberOfDays;
  
  public static final String USER_GROUP_FUNCTION_TARGET_ID = "USER_GROUP_FUNCTION_TARGET_ID"; 
  public static final String USER_ID = "USER_ID";    
  public static final String USER_FULL_NAME = "USER_FULL_NAME";    
  public static final String USER_EMAIL = "USER_EMAIL";
  public static final String GROUP_ID = "GROUP_ID";                
  public static final String GROUP_NAME = "GROUP_NAME";
  public static final String FUNCTION_ID = "FUNCTION_ID";                    
  public static final String FUNCTION_NAME = "FUNCTION_NAME";
  public static final String TARGET_VALUE = "TARGET_VALUE";                   
  public static final String TARGET_DURATION_TYPE_ID = "TARGET_DURATION_TYPE_ID";
  public static final String TARGET_DURATION_TYPE_NAME = "TARGET_DURATION_TYPE_NAME";
  public static final String NUMBER_OF_DAYS = "NUMBER_OF_DAYS";
   
  public DCMUserGroupFunctionTargetModel()
  {
  }

  public DCMUserGroupFunctionTargetModel(ResultSet res)
  {
    try
    {
      userGroupFunctionTargetId = res.getString(USER_GROUP_FUNCTION_TARGET_ID);  
      userId = res.getString(USER_ID);                        
      groupId = res.getString(GROUP_ID);                       
      functionId = res.getString(FUNCTION_ID);                    
      targetValue = res.getString(TARGET_VALUE);                   
      targetDurationTypeId = res.getString(TARGET_DURATION_TYPE_ID);
      userFullName = res.getString(USER_FULL_NAME);
      userEmail = res.getString(USER_EMAIL);
      groupName = res.getString(GROUP_NAME); 
      functionName = res.getString(FUNCTION_NAME);
      targetDurationTypeName = res.getString(TARGET_DURATION_TYPE_NAME);
      numberOfDays = res.getString(NUMBER_OF_DAYS);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

  public String getUserGroupFunctionTargetId()
  {
  return userGroupFunctionTargetId;
  }
  public void setUserGroupFunctionTargetId(String newUserGroupFunctionTargetId)
  {
  userGroupFunctionTargetId= newUserGroupFunctionTargetId;
  }
  
  public String getUserId()
  {
  return userId;
  }
  public void setUserId(String newUserId)
  {
  userId= newUserId;
  }
  
  public String getUserFullName()
  {
  return userFullName;
  }
  public void setUserFullName(String newUserFullName)
  {
  userFullName= newUserFullName;
  }
  
  public String getUserEmail()
  {
  return userEmail;
  }
  public void setUserEmail(String newUserEmail)
  {
  userEmail= newUserEmail;
  }
  
  public String getGroupId()
  {
  return groupId;
  }
  public void setGroupId(String newGroupId)
  {
  groupId= newGroupId;
  }
  
  public String getGroupName()
  {
  return groupName;
  }
  public void setGroupName(String newGroupName)
  {
  groupName= newGroupName;
  }
  
  public String getFunctionId()
  {
  return functionId;
  }
  public void setFunctionId(String newFunctionId)
  {
  functionId= newFunctionId;
  }
  
  public String getFunctionName()
  {
  return functionName;
  }
  public void setFunctionName(String newFunctionName)
  {
  functionName= newFunctionName;
  }
  
  public String getTargetValue()
  {
  return targetValue;
  }
  public void setTargetValue(String newTargetValue)
  {
  targetValue= newTargetValue;
  }
  
  public String getTargetDurationTypeId()
  {
  return targetDurationTypeId;
  }
  public void setTargetDurationTypeId(String newTargetDurationTypeId)
  {
  targetDurationTypeId= newTargetDurationTypeId;
  }
  
  public String getTargetDurationTypeName()
  {
  return targetDurationTypeName;
  }
  public void setTargetDurationTypeName(String newTargetDurationTypeName)
  {
  targetDurationTypeName= newTargetDurationTypeName;
  }
  
  public String getNumberOfDayes()
  {
  return numberOfDays;
  }
  public void setNumberOfDays(String newNumberOfDays)
  {
  numberOfDays= newNumberOfDays;
  }
}
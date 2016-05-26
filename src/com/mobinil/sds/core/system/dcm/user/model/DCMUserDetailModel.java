package com.mobinil.sds.core.system.dcm.user.model;
import java.sql.*;
import java.io.*;

public class DCMUserDetailModel implements Serializable
{
  String userDetailId;
  String userId;
  String userFullName;
  String userAddress;
  String userEmail;
  String userMobile;
  String regionId;
  String regionName;

  String userDetailStatusId;
  String userDetailStatusName;
  Timestamp creationTimestamp;
  String creationUserId;
  String creationUserFullName;
  String creationUserEmail;
  
  public static final String USER_DETAIL_ID = "USER_DETAIL_ID";
  public static final String USER_ID = "USER_ID";
  public static final String USER_FULL_NAME = "USER_FULL_NAME";
  public static final String USER_ADDRESS = "USER_ADDRESS";
  public static final String USER_EMAIL = "USER_EMAIL";
  public static final String USER_MOBILE = "USER_MOBILE";
  public static final String REGION_ID = "REGION_ID";
  public static final String REGION_NAME = "REGION_NAME";

  public static final String USER_DETAIL_STATUS_ID = "USER_DETAIL_STATUS_ID";
  public static final String USER_DETAIL_STATUS_NAME = "USER_DETAIL_STATUS_NAME";
  public static final String CREATION_TIMESTAMP = "CREATION_TIMESTAMP";
  public static final String CREATION_USER_ID = "CREATION_USER_ID";
  public static final String CREATION_USER_FULL_NAME = "CREATION_USER_FULL_NAME";
  public static final String CREATION_USER_EMAIL = "CREATION_USER_EMAIL";
  
  public DCMUserDetailModel()
  {
  }

  public DCMUserDetailModel(ResultSet res)
  {
    try
    {
      userDetailId = res.getString(USER_DETAIL_ID);
      userId = res.getString(USER_ID);
      userFullName = res.getString(USER_FULL_NAME);
      userAddress = res.getString(USER_ADDRESS);
      userEmail = res.getString(USER_EMAIL);
      userMobile = res.getString(USER_MOBILE);
      regionId = res.getString(REGION_ID);
      regionName = res.getString(REGION_NAME);

      userDetailStatusId = res.getString(USER_DETAIL_STATUS_ID);
      userDetailStatusName = res.getString(USER_DETAIL_STATUS_NAME);
      creationTimestamp = res.getTimestamp(CREATION_TIMESTAMP);
      creationUserId = res.getString(CREATION_USER_ID);
      creationUserFullName = res.getString(CREATION_USER_FULL_NAME);
      creationUserEmail = res.getString(CREATION_USER_EMAIL);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

    
  public String getUserDetailId()
  {
  return userDetailId;
  }
  public void setUserDetailId(String newUserDetailId)
  {
  userDetailId= newUserDetailId;
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
  
  public String getUserAddress()
  {
  return userAddress;
  }
  public void setUserAddress(String newUserAddress)
  {
  userAddress= newUserAddress;
  }
  
  public String getUserEmail()
  {
  return userEmail;
  }
  public void setUserEmail(String newUserEmail)
  {
  userEmail= newUserEmail;
  }
  
  public String getUserMobile()
  {
  return userMobile;
  }
  public void setUserMobile(String newUserMobile)
  {
  userMobile= newUserMobile;
  }
  
  public String getRegionId()
  {
  return regionId;
  }
  public void setRegionId(String newRegionId)
  {
  regionId= newRegionId;
  }
  
  public String getRegionName()
  {
  return regionName;
  }
  public void setRegionName(String newRegionName)
  {
  regionName= newRegionName;
  }

  public String getUserDetailStatusId()
  {
  return userDetailStatusId;
  }
  public void setUserDetailStatusId(String newUserDetailStatusId)
  {
  userDetailStatusId= newUserDetailStatusId;
  }
  
  public String getUserDetailStatusName()
  {
  return userDetailStatusName;
  }
  public void setUserDetailStatusName(String newUserDetailStatusName)
  {
  userDetailStatusName= newUserDetailStatusName;
  }
  
  public Timestamp getCreationTimestamp()
  {
  return creationTimestamp;
  }
  public void setCreationTimestamp(Timestamp newCreationTimestamp)
  {
  creationTimestamp= newCreationTimestamp;
  }
  
  public String getCreationUserId()
  {
  return creationUserId;
  }
  public void setCreationUserId(String newCreationUserId)
  {
  creationUserId= newCreationUserId;
  }

  public String getCreationUserFullName()
  {
  return creationUserFullName;
  }
  public void setCreationUserFullName(String newCreationUserFullName)
  {
  creationUserFullName= newCreationUserFullName;
  }
  
  public String getCreationUserEmail()
  {
  return creationUserEmail;
  }
  public void setCreationUserEmail(String newCreationUserEmail)
  {
  creationUserEmail= newCreationUserEmail;
  }
}
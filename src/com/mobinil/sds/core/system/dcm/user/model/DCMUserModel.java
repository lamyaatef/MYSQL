package com.mobinil.sds.core.system.dcm.user.model;
import com.mobinil.sds.core.system.Model;
import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DCMUserModel extends Model implements Serializable
{


  String dcmUserId;
  String userId;
  String userFullName;
  String userEmail;
  String managerDcmUserId;
  String userLevelTypeId;
  String userLevelTypeName;
  String userDetailId;
  String userStatusTypeId;
  String userStatusTypeName;
  String regionId;
  String regionName;
  String userLevelId;
  Date creationTimeStamp;

  public static final String DCM_USER_ID = "DCM_USER_ID";
  public static final String USER_ID = "USER_ID";
  public static final String USER_FULL_NAME = "USER_FULL_NAME";
  public static final String USER_EMAIL = "USER_EMAIL";
  public static final String MANAGER_DCM_USER_ID = "MANAGER_DCM_USER_ID";
  public static final String USER_LEVEL_TYPE_ID = "USER_LEVEL_TYPE_ID";
  public static final String USER_LEVEL_TYPE_NAME = "USER_LEVEL_TYPE_NAME";
  public static final String USER_DETAIL_ID = "USER_DETAIL_ID";
  public static final String USER_STATUS_TYPE_ID = "USER_STATUS_TYPE_ID";
  public static final String USER_STATUS_TYPE_NAME = "USER_STATUS_TYPE_NAME";
  public static final String REGION_ID = "REGION_ID";
  public static final String REGION_NAME = "REGION_NAME";
  public static final String USER_LEVEL_ID = "USER_LEVEL_ID";

  
  public DCMUserModel()
  {
  }

  public DCMUserModel(ResultSet res)
  {
    try
    {
      dcmUserId = res.getString(DCM_USER_ID);
      userId = res.getString(USER_ID);
      userFullName = res.getString(USER_FULL_NAME);
      userEmail = res.getString(USER_EMAIL);
      managerDcmUserId = res.getString(MANAGER_DCM_USER_ID);
      userLevelTypeId = res.getString(USER_LEVEL_TYPE_ID);
      userLevelTypeName = res.getString(USER_LEVEL_TYPE_NAME);
      userDetailId = res.getString(USER_DETAIL_ID);
      userStatusTypeId = res.getString(USER_STATUS_TYPE_ID);
      userStatusTypeName = res.getString(USER_STATUS_TYPE_NAME);
      regionId = res.getString(REGION_ID);
      regionName = res.getString(REGION_NAME);
      userLevelId = res.getString(USER_LEVEL_ID);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

  public String getDcmUserId()
  {
  return dcmUserId;
  }
  public void setDcmUserId(String newDcmUserId)
  {
  dcmUserId= newDcmUserId;
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
  
  public String getManagerDcmUserId()
  {
  return managerDcmUserId;
  }
  public void setManagerDcmUserId(String newManagerDcmUserId)
  {
  managerDcmUserId= newManagerDcmUserId;
  }
  
  public String getUserLevelTypeId()
  {
  return userLevelTypeId;
  }
  public void setUserLevelTypeId(String newUserLevelTypeId)
  {
  userLevelTypeId= newUserLevelTypeId;
  }
  
  public String getUserLevelTypeName()
  {
  return userLevelTypeName;
  }
  public void setUserLevelTypeName(String newUserLevelTypeName)
  {
  userLevelTypeName= newUserLevelTypeName;
  }
  
  public String getUserDetailId()
  {
  return userDetailId;
  }
  public void setUserDetailId(String newUserDetailId)
  {
  userDetailId= newUserDetailId;
  }
  
  public String getUserStatusTypeId()
  {
  return userStatusTypeId;
  }
  public void setUserStatusTypeId(String newUserStatusTypeId)
  {
  userStatusTypeId= newUserStatusTypeId;
  }
  
  public String getUserStatusTypeName()
  {
  return userStatusTypeName;
  }
  public void setUserStatusTypeName(String newUserStatusTypeName)
  {
  userStatusTypeName= newUserStatusTypeName;
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

  public String getUserLevelId()
  {
  return userLevelId;
  }
  public void setUserLevelId(String newUserLevelId)
  {
  userLevelId= newUserLevelId;
  }

public Date getCreationTimeStamp() {
    return creationTimeStamp;
}


public void setCreationTimeStamp(Date passCreationTimeStamp) {
    creationTimeStamp = passCreationTimeStamp;
}



    public void fillForRepManagementSearch(ResultSet res){
        try {
            //this.setDcmUserId(res.getString("DCM_USER_ID"));
            this.setUserFullName(res.getString("USER_FULL_NAME"));
            this.setUserId(res.getString("USER_ID"));
            this.setRegionName(res.getString("REGION_NAME"));
            this.setUserLevelTypeName(res.getString("USER_LEVEL_TYPE_NAME"));
            this.setCreationTimeStamp(res.getDate("CREATION_TIMESTAMP"));
            this.setUserLevelTypeId(res.getString("USER_LEVEL_TYPE_ID"));
        } catch (SQLException ex) {
            Logger.getLogger(DCMUserModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void fillRepDcmUserModel(ResultSet res){
        try {

            this.setDcmUserId(res.getString("DCM_USER_ID"));
            this.setUserDetailId(res.getString("USER_DETAIL_ID"));
            this.setUserId(res.getString("USER_ID"));
            this.setRegionId(res.getString("REGION_ID"));
            this.setUserLevelTypeId(res.getString("USER_LEVEL_TYPE_ID"));
            
        } catch (SQLException ex) {
            Logger.getLogger(DCMUserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillForRepDetail(ResultSet res){
        try {

            this.setDcmUserId(res.getString("DCM_USER_ID"));
            this.setUserDetailId(res.getString("USER_DETAIL_ID"));
            this.setRegionId(res.getString("REGION_ID"));
            this.setUserLevelTypeId(res.getString("USER_LEVEL_TYPE_ID"));

        } catch (SQLException ex) {
            Logger.getLogger(DCMUserModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

        public void fillForRepSupAssign(ResultSet res){
        try {

            this.setDcmUserId(res.getString("DCM_USER_ID"));
            
            this.setUserFullName(res.getString("USER_FULL_NAME"));

        } catch (SQLException ex) {
            Logger.getLogger(DCMUserModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
           public void fillForRepTeamleadAssign(ResultSet res){
        try {

            this.setDcmUserId(res.getString("DCM_USER_ID"));
            this.setUserFullName(res.getString("USER_FULL_NAME"));

        } catch (SQLException ex) {
            Logger.getLogger(DCMUserModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void fillInstance(ResultSet res) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
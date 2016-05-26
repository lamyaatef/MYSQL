package com.mobinil.sds.core.system.hlp.mission.model;
import java.sql.*;
import java.io.*;

public class MissionStatusModel implements Serializable
{
  String missionStatusId;
  String missionId;
  String missionStatusTypeId;
  String missionStatusTypeName;
  Timestamp missionStatusTimestamp; 
  String userId;
  String missionName;
  String missionDescription;
  Date missionStartDate;
  Date missionEndDate ;
  String filSurveyId;
  Date creationDate;

  public static final String MISSION_STATUS_ID = "MISSION_STATUS_ID";
  public static final String MISSION_ID = "MISSION_ID";
  public static final String MISSION_STATUS_TYPE_ID = "MISSION_STATUS_TYPE_ID";
  public static final String MISSION_STATUS_TYPE_NAME = "MISSION_STATUS_TYPE_NAME";
  public static final String MISSION_STATUS_TIMESTAMP  = "MISSION_STATUS_TIMESTAMP";
  public static final String USER_ID = "USER_ID";
  public static final String MISSION_NAME = "MISSION_NAME";
  public static final String MISSION_DESCRIPTION = "MISSION_DESCRIPTION";
  public static final String MISSION_START_DATE = "MISSION_START_DATE";
  public static final String MISSION_END_DATE  = "MISSION_END_DATE";
  public static final String FIL_SURVEY_ID = "FIL_SURVEY_ID";
  public static final String CREATION_DATE = "CREATION_DATE";
  
  public MissionStatusModel()
  {
  }

  public MissionStatusModel(ResultSet res)
  {
    try
    {
      missionStatusId = res.getString(MISSION_STATUS_ID);
      missionId = res.getString(MISSION_ID);
      missionStatusTypeId = res.getString(MISSION_STATUS_TYPE_ID);
      missionStatusTypeName = res.getString(MISSION_STATUS_TYPE_NAME);
      missionStatusTimestamp = res.getTimestamp(MISSION_STATUS_TIMESTAMP); 
      userId = res.getString(USER_ID);
      missionName = res.getString(MISSION_NAME);
      missionDescription = res.getString(MISSION_DESCRIPTION);
      missionStartDate = res.getDate(MISSION_START_DATE);
      missionEndDate  = res.getDate(MISSION_END_DATE);
      filSurveyId = res.getString(FIL_SURVEY_ID);
      creationDate = res.getDate(CREATION_DATE); 
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

    
  public String getMissionStatusId()
  {
  return missionStatusId;
  }
  public void setMissionStatusId(String newMissionStatusId)
  {
  missionStatusId= newMissionStatusId;
  }
  
  public String getMissionId()
  {
  return missionId;
  }
  public void setMissionId(String newMissionId)
  {
  missionId= newMissionId;
  }
  
  public String getMissionStatusTypeId()
  {
  return missionStatusTypeId;
  }
  public void setMissionStatusTypeId(String newMissionStatusTypeId)
  {
  missionStatusTypeId= newMissionStatusTypeId;
  }
  
  public String getMissionStatusTypeName()
  {
  return missionStatusTypeName;
  }
  public void setMissionStatusTypeName(String newMissionStatusTypeName)
  {
  missionStatusTypeName= newMissionStatusTypeName;
  }
  
  public Timestamp getMissionStatusTimestamp()
  {
  return missionStatusTimestamp;
  }
  public void setMissionStatusTimestamp(Timestamp newMissionStatusTimestamp)
  {
  missionStatusTimestamp= newMissionStatusTimestamp;
  }
  
  public String getUserId()
  {
  return userId;
  }
  public void setUserId(String newUserId)
  {
  userId= newUserId;
  }
  
  public String getMissionName()
  {
  return missionName;
  }
  public void setMissionName(String newMissionName)
  {
  missionName= newMissionName;
  }
  
  public String getMissionDescription()
  {
  return missionDescription;
  }
  public void setMissionDescription(String newMissionDescription)
  {
  missionDescription= newMissionDescription;
  }
  
  public Date getMissionStartDate()
  {
  return missionStartDate;
  }
  public void setMissionStartDate(Date newMissionStartDate)
  {
  missionStartDate= newMissionStartDate;
  }
  
  public Date getMissionEndDate ()
  {
  return missionEndDate ;
  }
  public void setMissionEndDate (Date newMissionEndDate )
  {
  missionEndDate = newMissionEndDate ;
  }
  
  public String getFilSurveyId()
  {
  return filSurveyId;
  }
  public void setFilSurveyId(String newFilSurveyId)
  {
  filSurveyId= newFilSurveyId;
  }
  
  public Date getCreationDate()
  {
  return creationDate;
  }
  public void setCreationDate(Date newCreationDate)
  {
  creationDate= newCreationDate;
  }
}
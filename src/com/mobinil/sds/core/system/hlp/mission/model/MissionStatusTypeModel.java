package com.mobinil.sds.core.system.hlp.mission.model;
import java.sql.*;
import java.io.*;

public class MissionStatusTypeModel implements Serializable 
{
  String missionStatusTypeId;
  String missionStatusTypeName; 

  public static final String MISSION_STATUS_TYPE_ID = "MISSION_STATUS_TYPE_ID";
  public static final String MISSION_STATUS_TYPE_NAME  = "MISSION_STATUS_TYPE_NAME";
  
  public MissionStatusTypeModel()
  {
  }

  public MissionStatusTypeModel(ResultSet res)
  {
    try
    {
      missionStatusTypeId = res.getString(MISSION_STATUS_TYPE_ID);
      missionStatusTypeName = res.getString(MISSION_STATUS_TYPE_NAME); 
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
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
}
package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class MissionGroupModel implements Serializable
{
  String missionGroupId;
  String missionGroupName;
  String missionGroupStatusId;
  String missionGroupStatusName;

  public static final String MISSION_GROUP_ID = "MISSION_GROUP_ID";        
  public static final String MISSION_GROUP_NAME = "MISSION_GROUP_NAME";
  public static final String MISSION_GROUP_STATUS_ID = "MISSION_GROUP_STATUS_ID";
  public static final String MISSION_GROUP_STATUS_NAME = "MISSION_GROUP_STATUS_NAME";
  
  public MissionGroupModel()
  {
  }

  public MissionGroupModel(ResultSet res)
  {
      try
      {
          missionGroupId = res.getString(MISSION_GROUP_ID);
          missionGroupName = res.getString(MISSION_GROUP_NAME);        
          missionGroupStatusId = res.getString(MISSION_GROUP_STATUS_ID);
          missionGroupStatusName = res.getString(MISSION_GROUP_STATUS_NAME);        
      }
      catch(Exception e)
      {
        
      }
  }
  

  public String getMissionGroupId()
  {
    return missionGroupId;
  }

  public void setMissionGroupId(String newMissionGroupId)
  {
    missionGroupId = newMissionGroupId;
  }
////////////////////////////////////////////
  public String getMissionGroupName()
  {
    return missionGroupName;
  }

  public void setMissionGroupName(String newMissionGroupName)
  {
    missionGroupName = newMissionGroupName;
  }
////////////////////////////////////////////  


  public String getMissionGroupStatusId()
  {
    return missionGroupStatusId;
  }

  public void setMissionGroupStatusId(String newMissionGroupStatusId)
  {
    missionGroupStatusId = newMissionGroupStatusId;
  }
////////////////////////////////////////////
  public String getMissionGroupStatusName()
  {
    return missionGroupStatusName;
  }

  public void setMissionGroupStatusName(String newMissionGroupStatusName)
  {
    missionGroupStatusName = newMissionGroupStatusName;
  }
////////////////////////////////////////////  
}
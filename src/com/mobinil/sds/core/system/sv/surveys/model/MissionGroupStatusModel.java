package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class MissionGroupStatusModel implements Serializable
{
  String missionGroupStatusId;
  String missionGroupStatusName;

  public static final String MISSION_GROUP_STATUS_ID = "MISSION_GROUP_STATUS_ID";
  public static final String MISSION_GROUP_STATUS_NAME = "MISSION_GROUP_STATUS_NAME";
  
  public MissionGroupStatusModel()
  {
  }
  
  public MissionGroupStatusModel(ResultSet res)
  {
      try
      {
          missionGroupStatusId = res.getString(MISSION_GROUP_STATUS_ID);
          missionGroupStatusName = res.getString(MISSION_GROUP_STATUS_NAME);        
      }
      catch(Exception e)
      {
        
      }
  }


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
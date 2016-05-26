package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class GroupStatusModel implements Serializable
{
  String groupStatusId;
  String groupStatusName;

  public static final String GROUP_STATUS_ID = "GROUP_STATUS_ID";
  public static final String GROUP_STATUS_NAME = "GROUP_STATUS_NAME";
  
  public GroupStatusModel()
  {
  }

  public GroupStatusModel(ResultSet res)
  {
      try
      {
          groupStatusId = res.getString(GROUP_STATUS_ID);
          groupStatusName = res.getString(GROUP_STATUS_NAME);        
      }
      catch(Exception e)
      {
        
      }
  }

  public String getGroupStatusId()
  {
    return groupStatusId;
  }

  public void setGroupStatusId(String newGroupStatusId)
  {
    groupStatusId = newGroupStatusId;
  }
////////////////////////////////////////////
  public String getGroupStatusName()
  {
    return groupStatusName;
  }

  public void setGroupStatusName(String newGroupStatusName)
  {
    groupStatusName = newGroupStatusName;
  }
////////////////////////////////////////////  
}
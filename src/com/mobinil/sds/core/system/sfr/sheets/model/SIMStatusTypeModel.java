package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class SIMStatusTypeModel implements Serializable
{
  String simStatusTypeId;
  String simStatusTypeName;

  public static final String SIM_STATUS_TYPE_ID = "SIM_STATUS_TYPE_ID";
  public static final String SIM_STATUS_TYPE_NAME = "SIM_STATUS_TYPE_NAME";
  
  public SIMStatusTypeModel()
  {
  }

  public SIMStatusTypeModel(ResultSet res)
  {
    try
    {
      simStatusTypeId = res.getString(SIM_STATUS_TYPE_ID);
      simStatusTypeName = res.getString(SIM_STATUS_TYPE_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
  }

  public String getSimStatusTypeId()
  {
  return simStatusTypeId;
  }
  public void setSimStatusTypeId(String newSimStatusTypeId)
  {
  simStatusTypeId= newSimStatusTypeId;
  }
  
  
  public String getSimStatusTypeName()
  {
  return simStatusTypeName;
  }
  public void setSimStatusTypeName(String newSimStatusTypeName)
  {
  simStatusTypeName= newSimStatusTypeName;
  }
}
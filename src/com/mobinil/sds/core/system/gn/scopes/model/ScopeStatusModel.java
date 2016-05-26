package com.mobinil.sds.core.system.gn.scopes.model;
import java.sql.*;
import java.io.*;

public class ScopeStatusModel implements Serializable 
{
  String dataviewStatusId;
  String dataviewStatusName;
  String dataviewStatusDesc;
  
  public static final String DATA_VIEW_STATUS_ID = "DATA_VIEW_STATUS_ID";
  public static final String DATA_VIEW_STATUS_NAME = "DATA_VIEW_STATUS_NAME";
  public static final String DATA_VIEW_STATUS_DESC = "DATA_VIEW_STATUS_DESC";
  
  public ScopeStatusModel()
  {
  }

  public ScopeStatusModel(ResultSet res)
  {
      try
      {
        dataviewStatusId = res.getString(DATA_VIEW_STATUS_ID);
        dataviewStatusName = res.getString(DATA_VIEW_STATUS_NAME);
        dataviewStatusDesc = res.getString(DATA_VIEW_STATUS_DESC);
      }
      catch(Exception e)
      {
    
      }     
  }
  
  public String getDataviewStatusId()
  {
    return dataviewStatusId;
  }

  public void setDataviewStatusId(String newDataviewStatusId)
  {
    dataviewStatusId = newDataviewStatusId;
  } 
/////////////////////////////////////  
  public String getDataviewStatusName()
  {
    return dataviewStatusName;
  }

  public void setDataviewStatusName(String newDataviewStatusName)
  {
    dataviewStatusName = newDataviewStatusName;
  } 
/////////////////////////////////////  
  public String getDataviewStatusDesc()
  {
    return dataviewStatusDesc;
  }

  public void setDataviewStatusDesc(String newDataviewStatusDesc)
  {
    dataviewStatusDesc = newDataviewStatusDesc;
  }   
}
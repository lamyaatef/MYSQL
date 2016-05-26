package com.mobinil.sds.core.system.fn.addfunction.model;
import java.sql.*;
import java.io.*;

public class functionstatusModel implements Serializable
{
  String functionStatusId;
  String functionStatusName;
  String functionStatusDesc;

  public static final String FUNCTION_STATUS_ID ="FUNCTION_STATUS_ID";
  public static final String FUNCTION_STATUS_NAME= "FUNCTION_STATUS_NAME";
  public static final String FUNCTION_STATUS_DESC = "FUNCTION_STATUS_DESC";
  
  public functionstatusModel(ResultSet res)
  {
      try
      {
        functionStatusId = res.getString(FUNCTION_STATUS_ID);
        functionStatusName = res.getString(FUNCTION_STATUS_NAME);
        functionStatusDesc = res.getString(FUNCTION_STATUS_DESC);
      }
      catch(Exception e)
      {
    
      }    
  }

  public String getFunctionStatusId()
  {
    return functionStatusId;
  }

  public void setFunctionStatusId(String newFunctionStatusId)
  {
    functionStatusId = newFunctionStatusId;
  }

  public String getFunctionStatusName()
  {
    return functionStatusName;
  }

  public void setFunctionStatusName(String newFunctionStatusName)
  {
    functionStatusName = newFunctionStatusName;
  }  

  public String getFunctionStatusDesc()
  {
    return functionStatusDesc;
  }

  public void setFunctionStatusDesc(String newFunctionStatusDesc)
  {
    functionStatusDesc = newFunctionStatusDesc;
  }
}
package com.mobinil.sds.core.system.hlp.casepkg.model;
import java.sql.*;
import java.io.*;

public class CasePriorityModel implements Serializable
{
  String casePriorityId;
  String casePriorityName;

  public static final String CASE_PRIORITY_ID = "CASE_PRIORITY_ID";
  public static final String CASE_PRIORITY_NAME = "CASE_PRIORITY_NAME";
  
  public CasePriorityModel()
  {
  }

  public CasePriorityModel(ResultSet res)
  {
    try
    {
      casePriorityId = res.getString(CASE_PRIORITY_ID);
      casePriorityName = res.getString(CASE_PRIORITY_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public String getCasePriorityId()
  {
  return casePriorityId;
  }
  public void setCasePriorityId(String newCasePriorityId)
  {
  casePriorityId= newCasePriorityId;
  }
  
  public String getCasePriorityName()
  {
  return casePriorityName;
  }
  public void setCasePriorityName(String newCasePriorityName)
  {
  casePriorityName= newCasePriorityName;
  }
}
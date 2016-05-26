package com.mobinil.sds.core.system.hlp.casepkg.model;
import java.sql.*;
import java.io.*;

public class CaseDirectionModel implements Serializable
{
  String caseDirectionId;
  String caseDirectionName;

  public static final String CASE_DIRECTION_ID = "CASE_DIRECTION_ID";
  public static final String CASE_DIRECTION_NAME = "CASE_DIRECTION_NAME";
  
  public CaseDirectionModel()
  {
  }

  public CaseDirectionModel(ResultSet res)
  {
    try
    {
      caseDirectionId = res.getString(CASE_DIRECTION_ID);
      caseDirectionName = res.getString(CASE_DIRECTION_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

    
  public String getCaseDirectionId()
  {
  return caseDirectionId;
  }
  public void setCaseDirectionId(String newCaseDirectionId)
  {
  caseDirectionId= newCaseDirectionId;
  }
  
  public String getCaseDirectionName()
  {
  return caseDirectionName;
  }
  public void setCaseDirectionName(String newCaseDirectionName)
  {
  caseDirectionName= newCaseDirectionName;
  }
}
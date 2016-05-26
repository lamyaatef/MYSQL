package com.mobinil.sds.core.system.hlp.casepkg.model;
import java.sql.*;
import java.io.*;

public class CaseStatusModel implements Serializable
{
  String caseStatusId;
  String caseStatusName;

  String caseStatusWarningId;
  String caseStatusWarningName;
  
  public static final String CASE_STATUS_ID = "CASE_STATUS_ID";
  public static final String CASE_STATUS_NAME = "CASE_STATUS_NAME";

  public static final String CASE_STATUS_WARNING_ID = "CASE_STATUS_WARNING_ID";
  public static final String CASE_STATUS_WARNING_NAME = "CASE_STATUS_WARNING_NAME";

  public CaseStatusModel()
  {
  }

  public CaseStatusModel(ResultSet res)
  {
    try
    {
      caseStatusId = res.getString(CASE_STATUS_ID);
      caseStatusName = res.getString(CASE_STATUS_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

  public String getCaseStatusId()
  {
  return caseStatusId;
  }
  public void setCaseStatusId(String newCaseStatusId)
  {
  caseStatusId= newCaseStatusId;
  }
  
  public String getCaseStatusName()
  {
  return caseStatusName;
  }
  public void setCaseStatusName(String newCaseStatusName)
  {
  caseStatusName= newCaseStatusName;
  }

  public String getCaseStatusWarningId()
  {
  return caseStatusWarningId;
  }
  public void setCaseStatusWarningId(String newCaseStatusWarningId)
  {
  caseStatusWarningId= newCaseStatusWarningId;
  }
  
  public String getCaseStatusWarningName()
  {
  return caseStatusWarningName;
  }
  public void setCaseStatusWarningName(String newCaseStatusWarningName)
  {
  caseStatusWarningName= newCaseStatusWarningName;
  }
}
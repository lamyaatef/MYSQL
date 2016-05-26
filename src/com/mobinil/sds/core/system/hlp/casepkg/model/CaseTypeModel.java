package com.mobinil.sds.core.system.hlp.casepkg.model;
import java.sql.*;
import java.io.*;

public class CaseTypeModel implements Serializable
{
  String caseTypeId;
  String caseTypeName;
  String caseSuperTypeId;

  String caseSuperTypeName;
  
  public static final String CASE_TYPE_ID = "CASE_TYPE_ID";
  public static final String CASE_TYPE_NAME = "CASE_TYPE_NAME";
  public static final String CASE_SUPER_TYPE_ID = "CASE_SUPER_TYPE_ID";

  public static final String CASE_SUPER_TYPE_NAME = "CASE_SUPER_TYPE_NAME";
  
  public CaseTypeModel()
  {
  }

  public CaseTypeModel(ResultSet res)
  {
    try
    {
      caseTypeId = res.getString(CASE_TYPE_ID);
      caseTypeName = res.getString(CASE_TYPE_NAME);
      caseSuperTypeId = res.getString(CASE_SUPER_TYPE_ID);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

  public String getCaseTypeId()
  {
  return caseTypeId;
  }
  public void setCaseTypeId(String newCaseTypeId)
  {
  caseTypeId= newCaseTypeId;
  }
  
  public String getCaseTypeName()
  {
  return caseTypeName;
  }
  public void setCaseTypeName(String newCaseTypeName)
  {
  caseTypeName= newCaseTypeName;
  }
  
  public String getCaseSuperTypeId()
  {
  return caseSuperTypeId;
  }
  public void setCaseSuperTypeId(String newCaseSuperTypeId)
  {
  caseSuperTypeId= newCaseSuperTypeId;
  }
  
  public String getCaseSuperTypeName()
  {
  return caseSuperTypeName;
  }
  public void setCaseSuperTypeName(String newCaseSuperTypeName)
  {
  caseSuperTypeName= newCaseSuperTypeName;
  }
}
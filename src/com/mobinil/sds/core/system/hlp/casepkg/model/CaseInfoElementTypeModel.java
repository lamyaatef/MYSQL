package com.mobinil.sds.core.system.hlp.casepkg.model;
import java.sql.*;
import java.io.*;

public class CaseInfoElementTypeModel implements Serializable
{
  String caseInfoElementTypeId;      
  String caseInfoElementTypeName;      								 

  public static final String CASE_INFO_ELEMENT_TYPE_ID = "CASE_INFO_ELEMENT_TYPE_ID";      
  public static final String CASE_INFO_ELEMENT_TYPE_NAME = "CASE_INFO_ELEMENT_TYPE_NAME";      								 
  
  public CaseInfoElementTypeModel()
  {
  }

  public CaseInfoElementTypeModel(ResultSet res)
  {
    try
    {
      caseInfoElementTypeId = res.getString(CASE_INFO_ELEMENT_TYPE_ID);      
      caseInfoElementTypeName = res.getString(CASE_INFO_ELEMENT_TYPE_NAME);      								 
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }   
  }

  public String getCaseInfoElementTypeId()
  {
  return caseInfoElementTypeId;
  }
  public void setCaseInfoElementTypeId(String newCaseInfoElementTypeId)
  {
  caseInfoElementTypeId= newCaseInfoElementTypeId;
  }
  
  public String getCaseInfoElementTypeName()
  {
  return caseInfoElementTypeName;
  }
  public void setCaseInfoElementTypeName(String newCaseInfoElementTypeName)
  {
  caseInfoElementTypeName= newCaseInfoElementTypeName;
  }
}
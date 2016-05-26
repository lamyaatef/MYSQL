package com.mobinil.sds.core.system.hlp.casepkg.dto;
import java.sql.*;
import java.io.*;
import java.util.Vector;

public class CaseDTO implements Serializable
{
  Vector userList;
  Vector caseStatusList;
  Vector caseTypeList;
  Vector caseCategoryList;
  Vector casePriorityList;
  Vector caseSuperTypeList;
  Vector caseStatusWarningList;
  Vector dialNumberType;
  Vector caseDirection;
  Vector caseInfoElementList;
  Vector caseChannelId;
  
  public CaseDTO()
  {
  }

  public Vector getCaseChannelId()
  {
    return caseChannelId;
  }
  public void setCaseChannelId(Vector newCaseChannelId)
  {
    caseChannelId = newCaseChannelId;
  }
  
  public Vector getUserList()
  {
  return userList;
  }
  public void setUserList(Vector newUserList)
  {
  userList= newUserList;
  }
  
  public Vector getCaseStatusList()
  {
  return caseStatusList;
  }
  public void setCaseStatusList(Vector newCaseStatusList)
  {
  caseStatusList= newCaseStatusList;
  }
  
  public Vector getCaseTypeList()
  {
  return caseTypeList;
  }
  public void setCaseTypeList(Vector newCaseTypeList)
  {
  caseTypeList= newCaseTypeList;
  }
  
  public Vector getCaseCategoryList()
  {
  return caseCategoryList;
  }
  public void setCaseCategoryList(Vector newCaseCategoryList)
  {
  caseCategoryList= newCaseCategoryList;
  }
  
  public Vector getCasePriorityList()
  {
  return casePriorityList;
  }
  public void setCasePriorityList(Vector newCasePriorityList)
  {
  casePriorityList= newCasePriorityList;
  }
  
  public Vector getCaseSuperTypeList()
  {
  return caseSuperTypeList;
  }
  public void setCaseSuperTypeList(Vector newCaseSuperTypeList)
  {
  caseSuperTypeList= newCaseSuperTypeList;
  }
  
  public Vector getCaseStatusWarningList()
  {
  return caseStatusWarningList;
  }
  public void setCaseStatusWarningList(Vector newCaseStatusWarningList)
  {
  caseStatusWarningList= newCaseStatusWarningList;
  }


  public Vector getDialNumberType()
  {
  return dialNumberType;
  }
  public void setDialNumberType(Vector newDialNumberType)
  {
  dialNumberType= newDialNumberType;
  }
  
  public Vector getCaseDirection()
  {
  return caseDirection;
  }
  public void setCaseDirection(Vector newCaseDirection)
  {
  caseDirection= newCaseDirection;
  }

  public Vector getCaseInfoElementList()
  {
  return caseInfoElementList;
  }
  public void setCaseInfoElementList(Vector newCaseInfoElementList)
  {
  caseInfoElementList= newCaseInfoElementList;
  }
}
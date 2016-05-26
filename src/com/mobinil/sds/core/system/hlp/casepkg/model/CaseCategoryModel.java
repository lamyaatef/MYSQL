package com.mobinil.sds.core.system.hlp.casepkg.model;
import java.sql.*;
import java.io.*;

public class CaseCategoryModel implements Serializable
{
  String caseCategoryId;
  String caseCategoryName;

  public static final String CASE_CATEGORY_ID = "CASE_CATEGORY_ID";
  public static final String CASE_CATEGORY_NAME = "CASE_CATEGORY_NAME";
  
  public CaseCategoryModel()
  {
  }

  public CaseCategoryModel(ResultSet res)
  {
    try
    {
      caseCategoryId = res.getString(CASE_CATEGORY_ID);
      caseCategoryName = res.getString(CASE_CATEGORY_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

  public String getCaseCategoryId()
  {
  return caseCategoryId;
  }
  public void setCaseCategoryId(String newCaseCategoryId)
  {
  caseCategoryId= newCaseCategoryId;
  }
  
  public String getCaseCategoryName()
  {
  return caseCategoryName;
  }
  public void setCaseCategoryName(String newCaseCategoryName)
  {
  caseCategoryName= newCaseCategoryName;
  }
  
}
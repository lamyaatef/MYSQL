package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class SheetWarningTypeModel implements Serializable
{
  String sheetWarningTypeId;
  String sheetWarningTypeName;
  String suggestedSheetStatusTypeId;
  String suggestedSheetStatusTypeName;

  public static final String SHEET_WARNING_TYPE_ID = "SHEET_WARNING_TYPE_ID"; 
  public static final String SHEET_WARNING_TYPE_NAME = "SHEET_WARNING_TYPE_NAME";
  public static final String SUGGESTED_SHEET_STATUS_TYPE_ID = "SUGGESTED_SHEET_STATUS_TYPE_ID";
  public static final String SHEET_STATUS_TYPE_NAME = "SHEET_STATUS_TYPE_NAME";
  
  public SheetWarningTypeModel()
  {
  }

  public SheetWarningTypeModel(ResultSet res)
  {
    try
    {
      sheetWarningTypeId = res.getString(SHEET_WARNING_TYPE_ID);
      sheetWarningTypeName = res.getString(SHEET_WARNING_TYPE_NAME);
      suggestedSheetStatusTypeId = res.getString(SUGGESTED_SHEET_STATUS_TYPE_ID);
      suggestedSheetStatusTypeName = res.getString(SHEET_STATUS_TYPE_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
  }

  public String getSheetWarningTypeId()
  {
  return sheetWarningTypeId;
  }
  public void setSheetWarningTypeId(String newSheetWarningTypeId)
  {
  sheetWarningTypeId= newSheetWarningTypeId;
  }
  
  public String getSheetWarningTypeName()
  {
  return sheetWarningTypeName;
  }
  public void setSheetWarningTypeName(String newSheetWarningTypeName)
  {
  sheetWarningTypeName= newSheetWarningTypeName;
  }
  
  public String getSuggestedSheetStatusTypeId()
  {
  return suggestedSheetStatusTypeId;
  }
  public void setSuggestedSheetStatusTypeId(String newSuggestedSheetStatusTypeId)
  {
  suggestedSheetStatusTypeId= newSuggestedSheetStatusTypeId;
  }

  public String getSuggestedSheetStatusTypeName()
  {
  return suggestedSheetStatusTypeName;
  }
  public void setSuggestedSheetStatusTypeName(String newSuggestedSheetStatusTypeName)
  {
  suggestedSheetStatusTypeName= newSuggestedSheetStatusTypeName;
  }
}
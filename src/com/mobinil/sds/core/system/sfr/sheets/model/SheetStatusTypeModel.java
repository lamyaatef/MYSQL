package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class SheetStatusTypeModel implements Serializable
{
  String sheetStatusTypeId;
  String sheetStatusTypeName;

  public static final String SHEET_STATUS_TYPE_ID = "SHEET_STATUS_TYPE_ID"; 
  public static final String SHEET_STATUS_TYPE_NAME = "SHEET_STATUS_TYPE_NAME";
  
  public SheetStatusTypeModel()
  {
  }

  public SheetStatusTypeModel(ResultSet res)
  {
    try
    {
      sheetStatusTypeId = res.getString(SHEET_STATUS_TYPE_ID);
      sheetStatusTypeName = res.getString(SHEET_STATUS_TYPE_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }   
  }

  public String getSheetStatusTypeId()
  {
  return sheetStatusTypeId;
  }
  public void setSheetStatusTypeId(String newSheetStatusTypeId)
  {
  sheetStatusTypeId= newSheetStatusTypeId;
  }
  
  
  public String getSheetStatusTypeName()
  {
  return sheetStatusTypeName;
  }
  public void setSheetStatusTypeName(String newSheetStatusTypeName)
  {
  sheetStatusTypeName= newSheetStatusTypeName;
  }
}
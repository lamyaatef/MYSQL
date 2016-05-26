package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class SheetWarningModel implements Serializable 
{
  String sheetWarningId; 
  String sheetWarningTypeId;
  String sheetWarningTypeName;
  String sheetId;
  Timestamp sheetWarningTimestamp;
  String userId;
  String personFullName;
  

  public static final String SHEET_WARNING_ID = "SHEET_WARNING_ID";
  public static final String SHEET_WARNING_TYPE_ID = "SHEET_WARNING_TYPE_ID";
  public static final String SHEET_WARNING_TYPE_NAME = "SHEET_WARNING_TYPE_NAME";
  public static final String SHEET_ID = "SHEET_ID";
  public static final String SHEET_WARNING_TIMESTAMP = "SHEET_WARNING_TIMESTAMP";
  public static final String USER_ID = "USER_ID";
  public static final String PERSON_FULL_NAME = "PERSON_FULL_NAME";
  
  public SheetWarningModel()
  {
  }

  public SheetWarningModel(ResultSet res)
  {
    try
    {
      sheetWarningId = res.getString(SHEET_WARNING_ID);  
      sheetWarningTypeId = res.getString(SHEET_WARNING_TYPE_ID);
      sheetWarningTypeName = res.getString(SHEET_WARNING_TYPE_NAME);
      sheetId = res.getString(SHEET_ID);
      sheetWarningTimestamp = res.getTimestamp(SHEET_WARNING_TIMESTAMP);
      userId = res.getString(USER_ID);
      personFullName = res.getString(PERSON_FULL_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public String getSheetWarningId()
  {
  return sheetWarningId;
  }
  public void setSheetWarningId(String newSheetWarningId)
  {
  sheetWarningId= newSheetWarningId;
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
  
  public String getSheetId()
  {
  return sheetId;
  }
  public void setSheetId(String newSheetId)
  {
  sheetId= newSheetId;
  }	
  
  public Timestamp getSheetWarningTimestamp()
  {
  return sheetWarningTimestamp;
  }
  public void setSheetWarningTimestamp(Timestamp newSheetWarningTimestamp)
  {
  sheetWarningTimestamp= newSheetWarningTimestamp;
  }	
  
  public String getUserId()
  {
  return userId;
  }
  public void setUserId(String newUserId)
  {
  userId= newUserId;
  }	

  public String getPersonFullName()
  {
  return personFullName;
  }
  public void setPersonFullName(String newPersonFullName)
  {
  personFullName= newPersonFullName;
  }	
}
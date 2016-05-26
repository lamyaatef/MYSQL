package com.mobinil.sds.core.system.cr.sheet.model;
import java.sql.*;
import java.io.*;

public class SheetTempPercentageModel  implements Serializable
{
  String rowNum;
  String batchId;
  String sheetSerialNumber;
  String sheetLocalAuthPercentage;

  public static final String ROW_NUM = "ROW_NUM";
  public static final String BATCH_ID = "BATCH_ID";
  public static final String SHEET_SERIAL_NUMBER = "SHEET_SERIAL_NUMBER";
  public static final String SHEET_LOCAL_AUTH_PERCENTAGE = "SHEET_LOCAL_AUTH_PERCENTAGE";
  
  public SheetTempPercentageModel() {
  }

  public SheetTempPercentageModel(ResultSet res) {
    try
    {
      rowNum = res.getString(ROW_NUM);
      batchId = res.getString(BATCH_ID);
      sheetSerialNumber = res.getString(SHEET_SERIAL_NUMBER);
      sheetLocalAuthPercentage = res.getString(SHEET_LOCAL_AUTH_PERCENTAGE);
    }
    catch(Exception e)
    {
      
    }
  }

  public String getRowNum()
  {
    return rowNum;
  }

  public void setRowNum(String newRowNum)
  {
    rowNum = newRowNum;
  } 
/////////////////////////////////////   
  public String getBatchId()
  {
    return batchId;
  }

  public void setBatchId(String newBatchId)
  {
    batchId = newBatchId;
  } 
/////////////////////////////////////    
  public String getSheetSerialNumber()
  {
    return sheetSerialNumber;
  }

  public void setSheetSerialNumber(String newSheetSerialNumber)
  {
    sheetSerialNumber = newSheetSerialNumber;
  } 
//////////////////////////////////////////////////////
  public String getSheetLocalAuthPercentage()
  {
    return sheetLocalAuthPercentage;
  }

  public void setSheetLocalAuthPercentage(String newSheetLocalAuthPercentage)
  {
    sheetLocalAuthPercentage = newSheetLocalAuthPercentage;
  } 
}
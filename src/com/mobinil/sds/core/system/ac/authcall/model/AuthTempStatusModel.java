package com.mobinil.sds.core.system.ac.authcall.model;
import java.sql.*;
import java.io.*;

public class AuthTempStatusModel implements Serializable
{
  String rowNum;
  String batchId;
  String sheetSerialNumber;
  String userId;
  String mainSIMNumber;
  String contractStatusId;
  String warning1;
  String warning2;
  String warning3;

  public static final String ROW_NUM = "ROW_NUM";
  public static final String BATCH_ID = "BATCH_ID";
  public static final String SHEET_SERIAL_NUMBER = "SHEET_SERIAL_NUMBER";
  public static final String USER_ID = "USER_ID";
  public static final String CONTRACT_STATUS_ID = "CONTRACT_STATUS_ID";
  public static final String MAIN_SIM_NUMBER = "MAIN_SIM_NUMBER";
  public static final String WARNING_1 = "WARNING_1";
  public static final String WARNING_2 = "WARNING_2";
  public static final String WARNING_3 = "WARNING_3";
  
  public AuthTempStatusModel() 
  {
  }

  public AuthTempStatusModel(ResultSet res) 
  {
    try
    {
      rowNum = res.getString(ROW_NUM);
      batchId = res.getString(BATCH_ID);
      sheetSerialNumber = res.getString(SHEET_SERIAL_NUMBER);
      userId = res.getString(USER_ID);
      mainSIMNumber = res.getString(MAIN_SIM_NUMBER);
      contractStatusId = res.getString(CONTRACT_STATUS_ID);
      warning1 = res.getString(WARNING_1);
      warning2 = res.getString(WARNING_2);
      warning3 = res.getString(WARNING_3);  
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
/////////////////////////////////////  
  public String getUserId()
  {
    return userId;
  }

  public void setUserId(String newUserId)
  {
    userId = newUserId;
  } 
/////////////////////////////////////  
  public String getMainSIMNumber()
  {
    return mainSIMNumber;
  }

  public void setMainSIMNumber(String newMainSIMNumber)
  {
    mainSIMNumber = newMainSIMNumber;
  } 
/////////////////////////////////////  
  public String getContractStatusId()
  {
    return contractStatusId;
  }

  public void setContractStatusId(String newContractStatusId)
  {
    contractStatusId = newContractStatusId;
  } 
/////////////////////////////////////  
  public String getWarning1()
  {
    return warning1;
  }

  public void setWarning1(String newWarning1)
  {
    warning1 = newWarning1;
  } 
/////////////////////////////////////  
  public String getWarning2()
  {
    return warning2;
  }

  public void setWarning2(String newWarning2)
  {
    warning2 = newWarning2;
  } 
/////////////////////////////////////  
  public String getWarning3()
  {
    return warning3;
  }

  public void setWarning3(String newWarning3)
  {
    warning3 = newWarning3;
  } 
/////////////////////////////////////  
}
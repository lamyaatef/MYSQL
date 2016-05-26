package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class SheetStatusModel implements Serializable
{
  String sheetStatusId;
  String sheetStatusTypeId;
  String sheetStatusTypeName;
  String sheetSerial;
  String sheetId;
  Timestamp sheetStatusTimestamp;
  String userId;
  String personFullName;
  String batchId;
  String secondPosId;
  String secondPosCode;
  String secondPosName;
  String posId;
  String posCode;
  String posName;
  int sheetSimCount;
  String agentId;
  String agentFullName;


  public static final String SHEET_STATUS_ID = "SHEET_STATUS_ID";
  public static final String SHEET_STATUS_TYPE_ID = "SHEET_STATUS_TYPE_ID";
  public static final String SHEET_STATUS_TYPE_NAME = "SHEET_STATUS_TYPE_NAME";
  public static final String SHEET_SERIAL = "SHEET_SERIAL";
  public static final String SHEET_ID = "SHEET_ID";
  public static final String SHEET_STATUS_TIMESTAMP = "SHEET_STATUS_TIMESTAMP";
  public static final String USER_ID = "USER_ID";
  public static final String PERSON_FULL_NAME = "PERSON_FULL_NAME";
  public static final String BATCH_ID = "BATCH_ID";
  public static final String SECOND_POS_ID = "SECOND_POS_ID"; 
  public static final String SECOND_POS_CODE = "SECOND_POS_CODE"; 
  public static final String SECOND_POS_NAME = "SECOND_POS_NAME"; 
  public static final String POS_ID = "POS_ID";
  public static final String POS_CODE = "POS_CODE";
  public static final String POS_NAME = "POS_NAME";
  public static final String SHEET_SIM_COUNT = "SHEET_SIM_COUNT";

  public SheetStatusModel()
  {
  }

  public SheetStatusModel(ResultSet res)
  {
    try
    {
      sheetStatusId = res.getString(SHEET_STATUS_ID);
      sheetStatusTypeId = res.getString(SHEET_STATUS_TYPE_ID);
      sheetStatusTypeName = res.getString(SHEET_STATUS_TYPE_NAME);
      sheetSerial = res.getString(SHEET_SERIAL);
      sheetId = res.getString(SHEET_ID);
      sheetStatusTimestamp = res.getTimestamp(SHEET_STATUS_TIMESTAMP);
      userId = res.getString(USER_ID);
      personFullName = res.getString(PERSON_FULL_NAME);
      batchId = res.getString(BATCH_ID);
      secondPosId = res.getString(SECOND_POS_ID);
      secondPosCode = res.getString(SECOND_POS_CODE);
      secondPosName = res.getString(SECOND_POS_NAME);
      posId = res.getString(POS_ID);
      posCode = res.getString(POS_CODE);
      posName = res.getString(POS_NAME);
      sheetSimCount = res.getInt(SHEET_SIM_COUNT);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
  }

  public String getSheetStatusId()
  {
  return sheetStatusId;
  }
  public void setSheetStatusId(String newSheetStatusId)
  {
  sheetStatusId= newSheetStatusId;
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
  
  
  public String getSheetSerial()
  {
  return sheetSerial;
  }
  public void setSheetSerial(String newSheetSerial)
  {
  sheetSerial= newSheetSerial;
  }	
  
  public String getSheetId()
  {
  return sheetId;
  }
  public void setSheetId(String newSheetId)
  {
  sheetId= newSheetId;
  }
  
  
  public Timestamp getSheetStatusTimestamp()
  {
  return sheetStatusTimestamp;
  }
  public void setSheetStatusTimestamp(Timestamp newSheetStatusTimestamp)
  {
  sheetStatusTimestamp= newSheetStatusTimestamp;
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
  
  
  public String getBatchId()
  {
  return batchId;
  }
  public void setBatchId(String newBatchId)
  {
  batchId= newBatchId;
  }	
  
  public String getSecondPosId()
  {
  return secondPosId;
  }
  public void setSecondPosId(String newSecondPosId)
  {
  secondPosId= newSecondPosId;
  }
  
  public String getSecondPosCode()
  {
  return secondPosCode;
  }
  public void setSecondPosCode(String newSecondPosCode)
  {
  secondPosCode= newSecondPosCode;
  }
  
  
  public String getPosId()
  {
  return posId;
  }
  public void setPosId(String newPosId)
  {
  posId= newPosId;
  }	
  
  
  public String getPosCode()
  {
  return posCode;
  }
  public void setPosCode(String newPosCode)
  {
  posCode= newPosCode;
  }	
  
  
  public String getPosName()
  {
  return posName;
  }
  public void setPosName(String newPosName)
  {
  posName= newPosName;
  }	
  
  
  public int getSheetSimCount()
  {
  return sheetSimCount;
  }
  public void setSheetSimCount(int newSheetSimCount)
  {
  sheetSimCount= newSheetSimCount;
  }	

  public String getAgentFullName()
  {
    return agentFullName;
  }

  public void setAgentFullName(String newAgentFullName)
  {
    agentFullName = newAgentFullName;
  }

  public String getAgentId()
  {
    return agentId;
  }

  public void setAgentId(String newAgentId)
  {
    agentId = newAgentId;
  }

  public String getSecondPosName()
  {
    return secondPosName;
  }

  public void setSecondPosName(String newSecondPosName)
  {
    secondPosName = newSecondPosName;
  }
}
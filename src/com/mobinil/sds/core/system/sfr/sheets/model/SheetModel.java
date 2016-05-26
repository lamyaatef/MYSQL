package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class SheetModel implements Serializable
{
  String sheetSerial;
  String sheetId;
  String batchId;
  String posId;
  String posName;
  String posCode;
  String secondPosId;
  String secondPosCode;
  int sheetSIMCount;
  String sheetStatusTypeId;
  String sheetStatusTypeName;
  int sheetSIMScanedTotal;
  int sheetSIMScanedDublicate;
  int sheetSIMScanedUnique;
  int sheetSIMScanedAccepted;
  int sheetSIMScanedRejected;
  String agentId;
  String agentFullName;
  
  public static final String SHEET_SERIAL = "SHEET_SERIAL"; 
  public static final String SHEET_ID = "SHEET_ID"; 
  public static final String BATCH_ID = "BATCH_ID";
  public static final String POS_ID = "POS_ID";
  public static final String POS_NAME = "POS_NAME";
  public static final String POS_CODE = "POS_CODE";
  public static final String SECOND_POS_ID = "SECOND_POS_ID"; 
  public static final String SECOND_POS_CODE = "SECOND_POS_CODE"; 
  public static final String SHEET_SIM_COUNT = "SHEET_SIM_COUNT";
  public static final String SHEET_STATUS_TYPE_ID = "SHEET_STATUS_TYPE_ID"; 
  public static final String SHEET_STATUS_TYPE_NAME = "SHEET_STATUS_TYPE_NAME";

  public SheetModel()
  {
  }

  public SheetModel(ResultSet res)
  {
    try
    {
      sheetSerial = res.getString(SHEET_SERIAL);
      sheetId = res.getString(SHEET_ID);
      batchId = res.getString(BATCH_ID);
      posId = res.getString(POS_ID);
      posName = res.getString(POS_NAME);
      posCode = res.getString(POS_CODE);
      secondPosId = res.getString(SECOND_POS_ID);
      secondPosCode = res.getString(SECOND_POS_CODE);
      sheetSIMCount = res.getInt(SHEET_SIM_COUNT);
      sheetStatusTypeId = res.getString(SHEET_STATUS_TYPE_ID);
      sheetStatusTypeName = res.getString(SHEET_STATUS_TYPE_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }   
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
  
  
  public String getBatchId()
  {
  return batchId;
  }
  public void setBatchId(String newBatchId)
  {
  batchId= newBatchId;
  }
  
  
  public String getPosId()
  {
  return posId;
  }
  public void setPosId(String newPosId)
  {
  posId= newPosId;
  }
  
  public String getPosName()
  {
  return posName;
  }
  public void setPosName(String newPosName)
  {
  posName= newPosName;
  }

  public String getPosCode()
  {
  return posCode;
  }
  public void setPosCode(String newPosCode)
  {
  posCode= newPosCode;
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
  
  public int getSheetSIMCount()
  {
  return sheetSIMCount;
  }
  public void setSheetSIMCount(int newSheetSIMCount)
  {
  sheetSIMCount= newSheetSIMCount;
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

  public int getSheetSIMScanedTotal()
  {
  return sheetSIMScanedTotal;
  }
  public void setSheetSIMScanedTotal(int newSheetSIMScanedTotal)
  {
  sheetSIMScanedTotal= newSheetSIMScanedTotal;
  }

  public int getSheetSIMScanedDublicate()
  {
  return sheetSIMScanedDublicate;
  }
  public void setSheetSIMScanedDublicate(int newSheetSIMScanedDublicate)
  {
  sheetSIMScanedDublicate= newSheetSIMScanedDublicate;
  }

  public int getSheetSIMScanedUnique()
  {
  return sheetSIMScanedUnique;
  }
  public void setSheetSIMScanedUnique(int newSheetSIMScanedUnique)
  {
  sheetSIMScanedUnique= newSheetSIMScanedUnique;
  }

  public int getSheetSIMScanedAccepted()
  {
  return sheetSIMScanedAccepted;
  }
  public void setSheetSIMScanedAccepetd(int newSheetSIMScanedAccepted)
  {
  sheetSIMScanedAccepted= newSheetSIMScanedAccepted;
  }

  public int getSheetSIMScanedRejected()
  {
  return sheetSIMScanedRejected;
  }
  public void setSheetSIMScanedRejected(int newSheetSIMScanedRejected)
  {
  sheetSIMScanedRejected= newSheetSIMScanedRejected;
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
}
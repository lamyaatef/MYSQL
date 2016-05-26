package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class SheetImportModel implements Serializable
{
  String rowNum;
  String agentId;
  String posCode;
  String secondPosCode;
  int sheetSerial;
  int contractsCount;

  public static final String ROW_NUM = "ROW_NUM";
  public static final String AGENT_ID = "AGENT_ID";
  public static final String POS_CODE = "POS_CODE";
  public static final String SECOND_POS_CODE = "SECOND_POS_CODE";
  public static final String SHEET_SERIAL = "SHEET_SERIAL";
  public static final String CONTRACTS_COUNT = "CONTRACTS_COUNT";

  public SheetImportModel()
  {
  }

  public SheetImportModel(ResultSet res)
  {
    try
    {
      rowNum = res.getString(ROW_NUM);
      agentId = res.getString(AGENT_ID);
      posCode = res.getString(POS_CODE);
      secondPosCode = res.getString(SECOND_POS_CODE);
      String strSheetSerial = res.getString(SHEET_SERIAL);
      if(strSheetSerial == null)
      {
        sheetSerial = -1;  
      }
      else
      {
        sheetSerial = res.getInt(SHEET_SERIAL);
      }
      String strContractsCount = res.getString(CONTRACTS_COUNT);
      if(strContractsCount == null)
      {
        contractsCount = -1;  
      }
      else
      {
        contractsCount = res.getInt(CONTRACTS_COUNT);
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
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
  
  public String getAgentId()
  {
  return agentId;
  }
  public void setAgentId(String newAgentId)
  {
  agentId= newAgentId;
  }

  public String getPosCode()
  {
  return posCode;
  }
  public void setPosCode(String newPosCode)
  {
  posCode= newPosCode;
  }
  
  public String getSecondPosCode()
  {
  return secondPosCode;
  }
  public void setSecondPosCode(String newSecondPosCode)
  {
  secondPosCode= newSecondPosCode;
  }

  public int getSheetSerial()
  {
  return sheetSerial;
  }
  public void setSheetSerial(int newSheetSerial)
  {
  sheetSerial= newSheetSerial;
  }

  public int getContractsCount()
  {
  return contractsCount;
  }
  public void setContractsCount(int newContractsCount)
  {
  contractsCount= newContractsCount;
  }
}
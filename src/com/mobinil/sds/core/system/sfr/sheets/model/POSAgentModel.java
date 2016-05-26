package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class POSAgentModel implements Serializable
{
  String secondPosId;
  String secondPosCode;

  public static final String SECOND_POS_ID = "SECOND_POS_ID"; 
  public static final String SECOND_POS_CODE = "SECOND_POS_CODE";
  
  public POSAgentModel()
  {
  }

  public POSAgentModel(ResultSet res)
  {
    try
    {
      secondPosId = res.getString(SECOND_POS_ID);
      secondPosCode = res.getString(SECOND_POS_CODE);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
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
}
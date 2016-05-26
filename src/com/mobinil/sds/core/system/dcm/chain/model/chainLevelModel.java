package com.mobinil.sds.core.system.dcm.chain.model;
import java.sql.*;
import java.io.*;

public class chainLevelModel implements Serializable
{
  String dcmLevelId;
  String dcmLevelName;

  public static final String DCM_LEVEL_ID = "DCM_LEVEL_ID";
  public static final String DCM_LEVEL_NAME = "DCM_LEVEL_NAME";
  
  public chainLevelModel()
  {
  }
  
  public chainLevelModel(ResultSet res)
  {
    try
    {
      dcmLevelId = res.getString(DCM_LEVEL_ID);
      dcmLevelName = res.getString(DCM_LEVEL_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }
  ////////////////////////////////////////////////
  public String getDcmLevelId()
  {
    return dcmLevelId;
  }
  public void setDcmLevelId(String newDcmLevelId)
  {
    dcmLevelId = newDcmLevelId;
  }
  ////////////////////////////////////////////////
  public String getDcmLevelName()
  {
    return dcmLevelName;
  }
  public void setDcmLevelName(String newDcmLevelName)
  {
    dcmLevelName = newDcmLevelName;
  }
}
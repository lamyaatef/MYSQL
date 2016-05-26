package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class BatchStatusTypeModel implements Serializable
{
  String batchStatusTypeId;
  String batchStatusTypeName;

  public static final String BATCH_STATUS_TYPE_ID = "BATCH_STATUS_TYPE_ID"; 
  public static final String BATCH_STATUS_TYPE_NAME = "BATCH_STATUS_TYPE_NAME";
  
  public BatchStatusTypeModel()
  {
  }

  public BatchStatusTypeModel(ResultSet res)
  {
    try
    {
      batchStatusTypeId = res.getString(BATCH_STATUS_TYPE_ID);
      batchStatusTypeName = res.getString(BATCH_STATUS_TYPE_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }     
  }


  public String getBatchStatusTypeId()
  {
  return batchStatusTypeId;
  }
  public void setBatchStatusTypeId(String newBatchStatusTypeId)
  {
  batchStatusTypeId= newBatchStatusTypeId;
  }
  
  
  public String getBatchStatusTypeName()
  {
  return batchStatusTypeName;
  }
  public void setBatchStatusTypeName(String newBatchStatusTypeName)
  {
  batchStatusTypeName= newBatchStatusTypeName;
  }
}
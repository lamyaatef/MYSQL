package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class BatchModel implements Serializable
{
  String batchId;
  String agentId;
  String agentFullName;
  String batchStatusTypeId;
  String batchStatusTypeName;
  Date batchDate;
  String batchSuggestedStatus;
  
  public static final String BATCH_ID = "BATCH_ID"; 
  public static final String AGENT_ID = "AGENT_ID"; 
  public static final String PERSON_FULL_NAME = "PERSON_FULL_NAME";
  public static final String BATCH_STATUS_TYPE_ID = "BATCH_STATUS_TYPE_ID"; 
  public static final String BATCH_STATUS_TYPE_NAME = "BATCH_STATUS_TYPE_NAME";
  public static final String BATCH_DATE = "BATCH_DATE";

  public BatchModel()
  {
  }

  public BatchModel(ResultSet res)
  {
    try
    {
      batchId = res.getString(BATCH_ID);
      agentId = res.getString(AGENT_ID);
      agentFullName = res.getString(PERSON_FULL_NAME);
      batchStatusTypeId = res.getString(BATCH_STATUS_TYPE_ID);
      batchStatusTypeName = res.getString(BATCH_STATUS_TYPE_NAME);
      batchDate = res.getDate(BATCH_DATE);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }     
  }

  public String getBatchId()
  {
  return batchId;
  }
  public void setBatchId(String newBatchId)
  {
  batchId= newBatchId;
  }
  
 
  public String getAgentId()
  {
  return agentId;
  }
  public void setAgentId(String newAgentId)
  {
  agentId= newAgentId;
  }
  
  public String getAgentFullName()
  {
  return agentFullName;
  }
  public void setAgentFullName(String newAgentFullName)
  {
  agentFullName = newAgentFullName;
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
  
  
  public Date getBatchDate()
  {
  return batchDate;
  }
  public void setBatchDate(Date newBatchDate)
  {
  batchDate= newBatchDate;
  }

  public String getBatchSuggestedStatus()
  {
  return batchSuggestedStatus;
  }
  public void setBatchSuggestedStatus(String newBatchSuggestedStatus)
  {
  batchSuggestedStatus= newBatchSuggestedStatus;
  }
}
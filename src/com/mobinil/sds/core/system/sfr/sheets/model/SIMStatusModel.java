package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class SIMStatusModel implements Serializable
{
  String simStatusId;
  String simStatusTypeId;
  String simStatusTypeName;
  String simSerial;
  Timestamp simStatusTimestamp;
  String userId;
  String personFullName;
  String agentId;
  String personFulName1;
  String sheetSerial;
  String batchId;
  String secondPosId;
  String secondPosCode;
  String secondPosName;
  String posId;
  String posName;
  String posCode;
  private String infoSource;
  
  
  public static final String SIM_STATUS_ID = "SIM_STATUS_ID";
  public static final String SIM_STATUS_TYPE_ID = "SIM_STATUS_TYPE_ID";
  public static final String SIM_STATUS_TYPE_NAME = "SIM_STATUS_TYPE_NAME";
  public static final String SIM_SERIAL = "SIM_SERIAL";
  public static final String SIM_STATUS_TIMESTAMP = "SIM_STATUS_TIMESTAMP";
  public static final String USER_ID = "USER_ID";
  public static final String PERSON_FULL_NAME = "PERSON_FULL_NAME";
  public static final String AGENT_ID = "AGENT_ID";
  public static final String PERSON_FULL_NAME_1 = "PERSON_FULL_NAME_1";
  public static final String SHEET_SERIAL = "SHEET_SERIAL";
  public static final String BATCH_ID = "BATCH_ID";
  public static final String SECOND_POS_ID = "SECOND_POS_ID"; 
  public static final String SECOND_POS_CODE = "SECOND_POS_CODE"; 
  public static final String SECOND_POS_NAME = "SECOND_POS_NAME"; 
  public static final String POS_ID = "POS_ID";
  public static final String POS_NAME = "POS_NAME";
  public static final String POS_CODE = "POS_CODE";
  public static final String INFO_SOURCE="INFO_SOURCE";

  public SIMStatusModel()
  {
  }

  public SIMStatusModel(ResultSet res)
  {
    try
    {
      simStatusId = res.getString(SIM_STATUS_ID);
      simStatusTypeId = res.getString(SIM_STATUS_TYPE_ID);
      simStatusTypeName = res.getString(SIM_STATUS_TYPE_NAME);
      simSerial = res.getString(SIM_SERIAL);
      simStatusTimestamp = res.getTimestamp(SIM_STATUS_TIMESTAMP);
      userId = res.getString(USER_ID);
      personFullName = res.getString(PERSON_FULL_NAME);
      agentId = res.getString(AGENT_ID);
      personFulName1 = res.getString(PERSON_FULL_NAME_1);
      sheetSerial = res.getString(SHEET_SERIAL);
      batchId = res.getString(BATCH_ID);
      secondPosId = res.getString(SECOND_POS_ID);
      secondPosCode = res.getString(SECOND_POS_CODE);
      secondPosName = res.getString(SECOND_POS_NAME);
      posId = res.getString(POS_ID);
      posCode = res.getString(POS_CODE);
      posName = res.getString(POS_NAME);
      infoSource=res.getString(INFO_SOURCE);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }      
  }

  public String getSimStatusId()
  {
  return simStatusId;
  }
  public void setSimStatusId(String newSimStatusId)
  {
  simStatusId= newSimStatusId;
  }
  
  
  public String getSimStatusTypeId()
  {
  return simStatusTypeId;
  }
  public void setSimStatusTypeId(String newSimStatusTypeId)
  {
  simStatusTypeId= newSimStatusTypeId;
  }
  
  
  public String getSimStatusTypeName()
  {
  return simStatusTypeName;
  }
  public void setSimStatusTypeName(String newSimStatusTypeName)
  {
  simStatusTypeName= newSimStatusTypeName;
  }
  
  
  public String getSimSerial()
  {
  return simSerial;
  }
  public void setSimSerial(String newSimSerial)
  {
  simSerial= newSimSerial;
  }
  
  
  public Timestamp getSimStatusTimestamp()
  {
  return simStatusTimestamp;
  }
  public void setSimStatusTimestamp(Timestamp newSimStatusTimestamp)
  {
  simStatusTimestamp= newSimStatusTimestamp;
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

  public String getAgenId()
  {
  return agentId;
  }
  public void setAgentId(String newAgentId)
  {
  agentId= newAgentId;
  }
  
  
  public String getPersonFullName1()
  {
  return personFulName1;
  }
  public void setPersonFullName1(String newPersonFullName1)
  {
  personFulName1= newPersonFullName1;
  }
  
  public String getSheetSerial()
  {
  return sheetSerial;
  }
  public void setSheetSerial(String newSheetSerial)
  {
  sheetSerial= newSheetSerial;
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
 
  public String getSecondPosName()
  {
  return secondPosName;
  }
  public void setSecondPosName(String newSecondPosName)
  {
  secondPosName= newSecondPosName;
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

    /**
     * @return the infoSource
     */
    public String getInfoSource() {
        return infoSource;
    }

    /**
     * @param infoSource the infoSource to set
     */
    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }
}
package com.mobinil.sds.core.system.sop.requests.model;
import java.sql.*;
import java.io.*;

public class RequestModel implements Serializable
{

  String requestCode;
  String requestId;
  String dcmId;
  String dcmName;
  String dcmCode;
  String schemaId;
  String creationDate;
  String lastRequestStatusLogId;
  String requestStatusId;
  String requestStatusName;

  public static final String REQUEST_CODE = "REQUEST_CODE";
  public static final String REQUEST_ID = "REQUEST_ID";
  public static final String DCM_ID = "DCM_ID";
  public static final String DCM_CODE = "DCM_CODE";
  public static final String DCM_NAME = "DCM_NAME";
  public static final String SCHEMA_ID = "SCHEMA_ID";
  public static final String CREATION_DATE = "CREATION_DATE";
  public static final String LAST_REQUEST_STATUS_LOG_ID = "LAST_REQUEST_STATUS_LOG_ID";
  public static final String REQUEST_STATUS_ID = "REQUEST_STATUS_ID";
  public static final String REQUEST_STATUS_NAME = "REQUEST_STATUS_NAME";

  public RequestModel()
  {
  }

  public RequestModel(ResultSet res)
  {
    try
    {
      requestCode = res.getString(REQUEST_CODE);
      requestId = res.getString(REQUEST_ID);
      dcmId = res.getString(DCM_ID);
      dcmCode = res.getString(DCM_CODE);
      dcmName = res.getString(DCM_NAME);
      schemaId = res.getString(SCHEMA_ID);
      creationDate = res.getString(CREATION_DATE);
      lastRequestStatusLogId = res.getString(LAST_REQUEST_STATUS_LOG_ID);
      requestStatusId = res.getString(REQUEST_STATUS_ID);
      requestStatusName = res.getString(REQUEST_STATUS_NAME);     
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }

  public String getRequestCode()
  {
    return requestCode;
  }
  public void setRequestCode(String newRequestCode)
  {
    requestCode = newRequestCode;
  }
  public String getRequestId()
  {
    return requestId;
  }
  public void setRequestId(String newRequestId)
  {
    requestId = newRequestId;
  }
  public String getDcmId()
  {
    return dcmId;
  }
  public void setDcmId(String newDcmId)
  {
    dcmId = newDcmId;
  }
  public String getDcmCode()
  {
    return dcmCode;
  }
  public void setDcmCode(String newDcmCode)
  {
    dcmCode = newDcmCode;
  }
  public String getDcmName()
  {
    return dcmName;
  }
  public void setDcmName(String newDcmName)
  {
    dcmName = newDcmName;
  }
  public String getSchemaId()
  {
    return schemaId;
  }
  public void setSchemaId(String newSchemaId)
  {
    schemaId = newSchemaId;
  }
  public String getCreationDate()
  {
    return creationDate;
  }
  public void setCreationDate(String newCreationDate)
  {
    creationDate = newCreationDate;
  }
  public String getLastRequestStatusLogId()
  {
    return lastRequestStatusLogId;
  }
  public void setLastRequestStatusLogId(String newLastRequestStatusLogId)
  {
    lastRequestStatusLogId = newLastRequestStatusLogId;
  }
  public String getRequestStatusId()
  {
    return requestStatusId;
  }
  public void setRequestStatusId(String newRequestStatusId)
  {
    requestStatusId = newRequestStatusId;
  }
  public String getRequestStatusName()
  {
    return requestStatusName;
  }
  public void setRequestStatusName(String newRequestStatusName)
  {
    requestStatusName = newRequestStatusName;
  }  
}
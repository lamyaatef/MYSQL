package com.mobinil.sds.core.system.sop.schemas.model;
import java.sql.*;
import java.io.*;

public class SchemaModel implements Serializable
{
  String schemaId;
  String schemaCode;
  String schemaName;
  String schemaDescription;
  String creationDate;
  String startDate;
  String endDate;
  String lastSchemaStatusLogId;
  String schemaStatusId;
  String schemaStatusName;

  public static final String SCHEMA_ID = "SCHEMA_ID";
  public static final String SCHEMA_CODE = "SCHEMA_CODE";
  public static final String SCHEMA_NAME = "SCHEMA_NAME";
  public static final String SCHEMA_DESCRIPTION = "SCHEMA_DESCRIPTION";
  public static final String CREATION_DATE = "CREATION_DATE";
  public static final String START_DATE = "START_DATE";
  public static final String END_DATE = "END_DATE";
  public static final String LAST_SCHEMA_STATUS_LOG_ID = "LAST_SCHEMA_STATUS_LOG_ID";
  public static final String SCHEMA_STATUS_ID = "SCHEMA_STATUS_ID";
  public static final String SCHEMA_STATUS_NAME = "SCHEMA_STATUS_NAME";

  public SchemaModel()
  {
  }

  public SchemaModel(ResultSet res)
  {
    try
    {
      schemaId = res.getString(SCHEMA_ID);
      schemaCode = res.getString(SCHEMA_CODE);
      schemaName = res.getString(SCHEMA_NAME);
      schemaDescription = res.getString(SCHEMA_DESCRIPTION);
      creationDate = res.getString(CREATION_DATE);
      startDate = res.getString(START_DATE);
      endDate = res.getString(END_DATE);
      lastSchemaStatusLogId = res.getString(LAST_SCHEMA_STATUS_LOG_ID);
      schemaStatusId = res.getString(SCHEMA_STATUS_ID);
      schemaStatusName = res.getString(SCHEMA_STATUS_NAME);    
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }

  public String getSchemaId()
  {
  return schemaId;
  }
  public void setSchemaId(String newSchemaId)
  {
  schemaId = newSchemaId;
  }

  public String getSchemaCode()
  {
  return schemaCode;
  }
  public void setSchemaCode(String newSchemaCode)
  {
  schemaCode = newSchemaCode;
  }

  public String getSchemaName()
  {
  return schemaName;
  }
  public void setSchemaName(String newSchemaName)
  {
  schemaName = newSchemaName;
  }

  public String getSchemaDescription()
  {
  return schemaDescription;
  }
  public void setSchemaDescription(String newSchemaDescription)
  {
  schemaDescription = newSchemaDescription;
  }  

  public String getCreationDate()
  {
  return creationDate;
  }
  public void setCreationDate(String newCreationDate)
  {
  creationDate = newCreationDate;
  }

  public String getStartDate()
  {
  return startDate;
  }
  public void setStartDate(String newStartDate)
  {
  startDate = newStartDate;
  }

  public String getEndDate()
  {
  return endDate;
  }
  public void setEndDate(String newEndDate)
  {
  endDate = newEndDate;
  }

  public String getLastSchemaStatusLogId()
  {
  return lastSchemaStatusLogId;
  }
  public void setLastSchemaStatusLogId(String newLastSchemaStatusLogId)
  {
  lastSchemaStatusLogId = newLastSchemaStatusLogId;
  }

  public String getSchemaStatusId()
  {
  return schemaStatusId;
  }
  public void setSchemaStatusId(String newSchemaStatusId)
  {
  schemaStatusId = newSchemaStatusId;
  }

  public String getSchemaStatusName()
  {
  return schemaStatusName;
  }
  public void setSchemaStatusName(String newSchemaStatusName)
  {
  schemaStatusName = newSchemaStatusName;
  }  
}
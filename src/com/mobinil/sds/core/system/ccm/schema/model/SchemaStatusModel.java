package com.mobinil.sds.core.system.ccm.schema.model;
import java.sql.*;
import java.io.*;

public class SchemaStatusModel implements Serializable
{
  String schemaStatusId;
  String schemaStatusName;

  public static final String SCHEMA_STATUS_ID = "SCHEMA_STATUS_ID";
  public static final String SCHEMA_STATUS_NAME = "SCHEMA_STATUS_NAME";
  
  public SchemaStatusModel()
  {
  }

  public SchemaStatusModel(ResultSet res)
  {
    try
    {
      schemaStatusId = res.getString(SCHEMA_STATUS_ID);
      schemaStatusName = res.getString(SCHEMA_STATUS_NAME);   
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }

  public String getSchemaStatusId()
  {
    return schemaStatusId;
  }

  public void setSchemaStatusId(String newSchemaStatusId)
  {
    schemaStatusId = newSchemaStatusId;
  }
////////////////////////////////////////////   
  public String getSchemaStatusName()
  {
    return schemaStatusName;
  }

  public void setSchemaStatusName(String newSchemaStatusName)
  {
    schemaStatusName = newSchemaStatusName;
  }
////////////////////////////////////////////  
}
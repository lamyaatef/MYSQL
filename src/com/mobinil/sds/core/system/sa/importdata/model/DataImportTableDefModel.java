package com.mobinil.sds.core.system.sa.importdata.model;
import java.sql.*;
import java.io.*;

public class DataImportTableDefModel implements Serializable
{
  String tableId;
  String tableName;
  String tableSQLName;

  public static final String TABLE_ID ="TABLE_ID";
  public static final String TABLE_NAME= "TABLE_NAME";
  public static final String TABLE_SQL_NAME = "TABLE_SQL_NAME";
  
  public DataImportTableDefModel(ResultSet res)  
  {
  try
  {
    tableId = res.getString(TABLE_ID);
    tableName = res.getString(TABLE_NAME);
    tableSQLName = res.getString(TABLE_SQL_NAME);
    
  }
  catch(Exception e)
  {
    
  }
  }

  public String getTableId()
  {
    return tableId;
  }

  public void setTableId(String newTableId)
  {
    tableId = newTableId;
  }

  public String getTableName()
  {
    return tableName;
  }

  public void setTableName(String newTableName)
  {
    tableName = newTableName;
  }

  public String getTableSQLName()
  {
    return tableSQLName;
  }

  public void setTableSQLName(String newTableSQLName)
  {
    tableSQLName = newTableSQLName;
  }
}
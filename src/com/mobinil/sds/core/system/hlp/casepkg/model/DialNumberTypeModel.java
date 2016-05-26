package com.mobinil.sds.core.system.hlp.casepkg.model;
import java.sql.*;
import java.io.*;

public class DialNumberTypeModel implements Serializable
{
  String dialNumberTypeId;
  String dialNumberTypeName;

  public static final String DIAL_NUMBER_TYPE_ID = "DIAL_NUMBER_TYPE_ID";
  public static final String DIAL_NUMBER_TYPE_NAME = "DIAL_NUMBER_TYPE_NAME";
  
  public DialNumberTypeModel()
  {
  }

  public DialNumberTypeModel(ResultSet res)
  {
    try
    {
      dialNumberTypeId = res.getString(DIAL_NUMBER_TYPE_ID);
      dialNumberTypeName = res.getString(DIAL_NUMBER_TYPE_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

  public String getDialNumberTypeId()
  {
  return dialNumberTypeId;
  }
  public void setDialNumberTypeId(String newDialNumberTypeId)
  {
  dialNumberTypeId= newDialNumberTypeId;
  }
  
  public String getDialNumberTypeName()
  {
  return dialNumberTypeName;
  }
  public void setDialNumberTypeName(String newDialNumberTypeName)
  {
  dialNumberTypeName= newDialNumberTypeName;
  }	
}
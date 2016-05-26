package com.mobinil.sds.core.system.fn.addfunction.model;
import java.sql.*;
import java.io.*;

public class functiontypeModel implements Serializable
{
  String functionTypeId;
  String functionTypeName;
  String functionTypeDesc;

  public static final String FUNCTION_TYPE_ID ="FUNCTION_TYPE_ID";
  public static final String FUNCTION_TYPE_NAME= "FUNCTION_TYPE_NAME";
  public static final String FUNCTION_TYPE_DESC = "FUNCTION_TYPE_DESC";
  
  public functiontypeModel(ResultSet res)
  {
      try
      {
        functionTypeId = res.getString(FUNCTION_TYPE_ID);
        functionTypeName = res.getString(FUNCTION_TYPE_NAME);
        functionTypeDesc = res.getString(FUNCTION_TYPE_DESC);
      }
      catch(Exception e)
      {
    
      }    
  }

  public String getFunctionTypeId()
  {
    return functionTypeId;
  }

  public void setFunctionTypeId(String newFunctionTypeId)
  {
    functionTypeId = newFunctionTypeId;
  }

  public String getFunctionTypeName()
  {
    return functionTypeName;
  }

  public void setFunctionTypeName(String newFunctionTypeName)
  {
    functionTypeName = newFunctionTypeName;
  }  

  public String getFunctionTypeDesc()
  {
    return functionTypeDesc;
  }

  public void setFunctionTypeDesc(String newFunctionTypeDesc)
  {
    functionTypeDesc = newFunctionTypeDesc;
  }
}
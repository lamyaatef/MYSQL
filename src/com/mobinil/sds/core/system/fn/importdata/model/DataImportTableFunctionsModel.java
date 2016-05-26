package com.mobinil.sds.core.system.fn.importdata.model;
import java.sql.*;
import java.io.*;

public class DataImportTableFunctionsModel implements Serializable
{
  String functionId;
  String functionName;
  String functionStatusId;
  String functionStatusName;
  String functionSQLCall;
  String functionTypeId;
  String functionTypeName;
  String functionHelpText;
  String functionDescription;

  public static final String FUNCTION_ID ="FUNCTION_ID";
  public static final String FUNCTION_NAME= "FUNCTION_NAME";
  public static final String FUNCTION_STATUS_ID = "FUNCTION_STATUS_ID";
  public static final String FUNCTION_STATUS_NAME = "FUNCTION_STATUS_NAME";
  public static final String FUNCTION_SQL_CALL = "FUNCTION_SQL_CALL";
  public static final String FUNCTION_TYPE_ID= "FUNCTION_TYPE_ID";
  public static final String FUNCTION_TYPE_NAME= "FUNCTION_TYPE_NAME";
  public static final String FUNCTION_HELP_TEXT = "FUNCTION_HELP_TEXT";
  public static final String FUNCTION_DESCRIPTION ="FUNCTION_DESCRIPTION";

  public DataImportTableFunctionsModel()
  {
    
  }

  public DataImportTableFunctionsModel(ResultSet res)  
  {
      try
      {
        functionId = res.getString(FUNCTION_ID);
        functionName = res.getString(FUNCTION_NAME);
        functionStatusId = res.getString(FUNCTION_STATUS_ID);
        functionStatusName = res.getString(FUNCTION_STATUS_NAME);
        functionSQLCall = res.getString(FUNCTION_SQL_CALL);
        functionTypeId = res.getString(FUNCTION_TYPE_ID);
        functionTypeName = res.getString(FUNCTION_TYPE_NAME);
        functionHelpText = res.getString(FUNCTION_HELP_TEXT);
        functionDescription = res.getString(FUNCTION_DESCRIPTION);    
      }
      catch(Exception e)
      {
    
      }
  }

  public String getFunctionId()
  {
    return functionId;
  }

  public void setFunctionId(String newFunctionId)
  {
    functionId = newFunctionId;
  }

  public String getFunctionName()
  {
    return functionName;
  }

  public void setFunctionName(String newFunctionName)
  {
    functionName = newFunctionName;
  }  

  public String getFunctionStatusId()
  {
    return functionStatusId;
  }

  public void setFunctionStatusId(String newFunctionStatusId)
  {
    functionStatusId = newFunctionStatusId;
  }

  public String getFunctionStatusName()
  {
    return functionStatusName;
  }

  public void setFunctionStatusName(String newFunctionStatusName)
  {
    functionStatusName = newFunctionStatusName;
  }

  public String getFunctionSQLCall()
  {
    return functionSQLCall;
  }

  public void setFunctionSQLCall(String newFunctionSQLCall)
  {
    functionSQLCall = newFunctionSQLCall;
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

  public String getFunctionHelpText()
  {
    return functionHelpText;
  }

  public void setFunctionHelpText(String newFunctionHelpText)
  {
    functionHelpText = newFunctionHelpText;
  }

  public String getFunctionDescription()
  {
    return functionDescription;
  }

  public void setFunctionDescription(String newFunctionDescription)
  {
    functionDescription = newFunctionDescription;
  }
}
package com.mobinil.sds.core.system.dcm.function.model;

import java.sql.*;

public class FunctionModel 
{
  int functionID       = 0  ;
  String functionName  = "" ;
  String functionDesc  = "" ;
  int functionStatusID = 0  ;
  String functionStatusName = "";
  String functionUnits = "";
  String functionTypeName = "";
  int functionTypeID = 0;
  

  public void set_function_id(int newFunctionID)
  {
    functionID = newFunctionID;
  }
  public int get_function_id()
  {
    return functionID;
  }

  public void set_function_units(String newFunctionUnits)
  {
    functionUnits = newFunctionUnits;
  }
  public String get_function_units()
  {
    return functionUnits;
  }

  public void set_function_name(String newFunctionName)
  {
    functionName = newFunctionName;
  }
  public String get_function_name()
  {
    return functionName;
  }

  public void set_function_status_name(String newFunctionStatusName)
  {
    functionStatusName = newFunctionStatusName;
  }
  public String get_function_status_name()
  {
    return functionStatusName;
  }

  public void set_function_desc(String newFunctionDesc)
  {
    functionDesc = newFunctionDesc;
  }
  public String get_function_desc()
  {
    return functionDesc;
  }

  public void set_function_status_id(int newFunctionStatusID)
  {
    functionStatusID = newFunctionStatusID;
  }
  public int get_function_status_id()
  {
    return functionStatusID;
  }
  public String getFunctionTypeName()
  {
    return functionTypeName;
  }
  public void setFunctionTypeName(String newFunctionTypeName)
  {
    functionTypeName = newFunctionTypeName;
  }
  public int getFunctionTypeID()
  {
  	return functionTypeID;
  }
  public void setFunctionTypeID(int newFunctionTypeID)  
  {
  	functionTypeID = newFunctionTypeID;
  }


  public FunctionModel()
  {
  }

  public FunctionModel(ResultSet res)
  {
    try
    {
      functionID       = res.getInt("FUNCTION_ID");
      functionName  = res.getString("FUNCTION_NAME") ;
      functionDesc  = res.getString("FUNCTION_DESCRIPTION") ;
      functionStatusID = res.getInt("FUNCTION_STATUS_ID")  ;
      functionStatusName = res.getString("FUNCTION_STATUS_NAME");
      functionUnits = res.getString("FUNCTION_UNIT");   
      functionTypeID = res.getInt("FUNCTION_TYPE_ID");
      functionTypeName  = res.getString("FUNCTION_TYPE_NAME");
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}
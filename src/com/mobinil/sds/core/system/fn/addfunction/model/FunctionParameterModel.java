package com.mobinil.sds.core.system.fn.addfunction.model;
import java.sql.*;
import java.io.*;

public class FunctionParameterModel  implements Serializable
{
  String parameterDefinitionId;
  String functionId;
  String parameterDefinitionName;
  String parameterDefinitionDesc;
  String parameterDefinitionOptional;
  String parameterDefinitionIsList;
    
    
  public static final String PARAMETER_DEFINITION_ID = "PARAMETER_DEFINITION_ID";
  public static final String FUNCTION_ID = "FUNCTION_ID";
  public static final String PARAMETER_DEFINITION_NAME = "PARAMETER_DEFINITION_NAME";
  public static final String PARAMETER_DEFINITION_DESC = "PARAMETER_DEFINITION_DESC";
  public static final String PARAMETER_DEFINITION_OPTIONAL = "PARAMETER_DEFINITION_OPTIONAL";
  public static final String PARAMETER_DEFINITION_IS_LIST = "PARAMETER_DEFINITION_IS_LIST";
  public FunctionParameterModel() 
  {
  }

  public FunctionParameterModel(ResultSet res) 
  {
      try
      {
          parameterDefinitionId = res.getString(PARAMETER_DEFINITION_ID);
          functionId = res.getString(FUNCTION_ID);
          parameterDefinitionName = res.getString(PARAMETER_DEFINITION_NAME);
          parameterDefinitionDesc = res.getString(PARAMETER_DEFINITION_DESC);
          parameterDefinitionOptional = res.getString(PARAMETER_DEFINITION_OPTIONAL);
          parameterDefinitionIsList = res.getString(PARAMETER_DEFINITION_IS_LIST);      
      }
      catch(Exception e) 
      {
        
      }
  }

  public String getParameterDefinitionId()
  {
    return parameterDefinitionId;
  }

  public void setParameterDefinitionId(String newParameterDefinitionId)
  {
    parameterDefinitionId = newParameterDefinitionId;
  }
//////////////////////////////////////////////////
  public String getFunctionId()
  {
    return functionId;
  }

  public void setFunctionId(String newFunctionId)
  {
    functionId = newFunctionId;
  }
//////////////////////////////////////////////////
  public String getParameterDefinitionName()
  {
    return parameterDefinitionName;
  }

  public void setParameterDefinitionName(String newParameterDefinitionName)
  {
    parameterDefinitionName = newParameterDefinitionName;
  }
//////////////////////////////////////////////////
  public String getParameterDefinitionDesc()
  {
    return parameterDefinitionDesc;
  }

  public void setParameterDefinitionDesc(String newParameterDefinitionDesc)
  {
    parameterDefinitionDesc = newParameterDefinitionDesc;
  }
//////////////////////////////////////////////////
  public String getParameterDefinitionOptional()
  {
    return parameterDefinitionOptional;
  }

  public void setParameterDefinitionOptional(String newParameterDefinitionOptional)
  {
    parameterDefinitionOptional = newParameterDefinitionOptional;
  }
//////////////////////////////////////////////////
  public String getParameterDefinitionIsList()
  {
    return parameterDefinitionIsList;
  }

  public void setParameterDefinitionIsList(String newParameterDefinitionIsList)
  {
    parameterDefinitionIsList = newParameterDefinitionIsList;
  }
//////////////////////////////////////////////////
}
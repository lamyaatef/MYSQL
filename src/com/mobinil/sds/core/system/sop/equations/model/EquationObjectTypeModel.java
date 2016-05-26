package com.mobinil.sds.core.system.sop.equations.model;
import java.sql.*;
import java.io.*;

public class EquationObjectTypeModel implements Serializable
{
  String equationObjectTypeId;
  String equationObjectTypeName;

  public static final String EQUATION_OBJECT_TYPE_ID = "EQUATION_OBJECT_TYPE_ID";
  public static final String EQUATION_OBJECT_TYPE_NAME = "EQUATION_OBJECT_TYPE_NAME";
  
  public EquationObjectTypeModel()
  {
  }

  public EquationObjectTypeModel(ResultSet res)
  {
    try
    {
      equationObjectTypeId = res.getString(EQUATION_OBJECT_TYPE_ID);
      equationObjectTypeName = res.getString(EQUATION_OBJECT_TYPE_NAME);   
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }

  public String getEquationObjectTypeId()
  {
    return equationObjectTypeId;
  }

  public void setEquationObjectTypeId(String newEquationObjectTypeId)
  {
    equationObjectTypeId = newEquationObjectTypeId;
  }
////////////////////////////////////////////   
  public String getEquationObjectTypeName()
  {
    return equationObjectTypeName;
  }

  public void setEquationObjectTypeName(String newEquationObjectTypeName)
  {
    equationObjectTypeName = newEquationObjectTypeName;
  }
////////////////////////////////////////////   
}
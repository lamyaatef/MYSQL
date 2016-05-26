package com.mobinil.sds.core.system.sop.equations.model;
import java.sql.*;
import java.io.*;

public class EquationOperatorModel implements Serializable
{
  String equationOperatorId;
  String equationOperator;

  public static final String EQUATION_OPERATOR_ID = "EQUATION_OPERATOR_ID";
  public static final String EQUATION_OPERATOR = "EQUATION_OPERATOR";
  
  public EquationOperatorModel()
  {
  }

  public EquationOperatorModel(ResultSet res)
  {
    try
    {
      equationOperatorId = res.getString(EQUATION_OPERATOR_ID);
      equationOperator = res.getString(EQUATION_OPERATOR);   
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }

  public String getEquationOperatorId()
  {
    return equationOperatorId;
  }

  public void setEquationOperatorId(String newEquationOperatorId)
  {
    equationOperatorId = newEquationOperatorId;
  }
////////////////////////////////////////////   
  public String getEquationOperator()
  {
    return equationOperator;
  }

  public void setEquationOperator(String newEquationOperator)
  {
    equationOperator = newEquationOperator;
  }
////////////////////////////////////////////  
}
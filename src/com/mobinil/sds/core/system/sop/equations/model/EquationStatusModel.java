package com.mobinil.sds.core.system.sop.equations.model;
import java.sql.*;
import java.io.*;

public class EquationStatusModel implements Serializable
{
  int equationStatusId;
  String equationStatusName;

  public static final String EQUATION_STATUS_ID = "EQUATION_STATUS_ID";
  public static final String EQUATION_STATUS_NAME = "EQUATION_STATUS_NAME";
  
  public EquationStatusModel()
  {
  }

  public EquationStatusModel(ResultSet res)
  {
    try
    {
      equationStatusId = res.getInt(EQUATION_STATUS_ID);
      equationStatusName = res.getString(EQUATION_STATUS_NAME);   
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }

  public int getEquationStatusId()
  {
    return equationStatusId;
  }

  public void setEquationStatusId(int newEquationStatusId)
  {
    equationStatusId = newEquationStatusId;
  }
////////////////////////////////////////////   
  public String getEquationStatusName()
  {
    return equationStatusName;
  }

  public void setEquationStatusName(String newEquationStatusName)
  {
    equationStatusName = newEquationStatusName;
  }
////////////////////////////////////////////
}
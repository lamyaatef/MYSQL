package com.mobinil.sds.core.system.sop.equations.model;
import java.sql.*;
import java.io.*;

public class EquationModel implements Serializable
{
  int equationId;
  String equationTitle;
  String equationDescription;
  int lastStatusId;
  int equationStatusId;
  String equationStatusName;

  public static final String EQUATION_ID = "EQUATION_ID";
  public static final String EQUATION_TITLE = "EQUATION_TITLE";
  public static final String EQUATION_DESCRIPTION = "EQUATION_DESCRIPTION";
  public static final String LAST_STATUS_ID = "LAST_STATUS_ID";
  public static final String EQUATION_STATUS_ID = "EQUATION_STATUS_ID";
  public static final String EQUATION_STATUS_NAME = "EQUATION_STATUS_NAME";

  
  public EquationModel()
  {
  }

  public EquationModel(ResultSet res)
  {
    try
    {
      equationId = res.getInt(EQUATION_ID);
      equationTitle = res.getString(EQUATION_TITLE);   
      equationDescription = res.getString(EQUATION_DESCRIPTION);
      lastStatusId = res.getInt(LAST_STATUS_ID);
      equationStatusId = res.getInt(EQUATION_STATUS_ID);
      equationStatusName = res.getString(EQUATION_STATUS_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
  }  

	public int getEquationId()
	{
	return equationId;
	}
	public void setEquationId(int newEquationId)
	{
	equationId = newEquationId;
	}
	
	public String getEquationTitle()
	{
	return equationTitle;
	}
	public void setEquationTitle(String newEquationTitle)
	{
	equationTitle = newEquationTitle;
	}
	
	public String getEquationDescription()
	{
	return equationDescription;
	}
	public void setEquationDescription(String newEquationDescription)
	{
	equationDescription = newEquationDescription;
	}
	
	public int getLastStatusId()
	{
	return lastStatusId;
	}
	public void setLastStatusId(int newLastStatusId)
	{
	lastStatusId = newLastStatusId;
	}
	
	public int getEquationStatusId()
	{
	return equationStatusId;
	}
	public void setEquationStatusId(int newEquationStatusId)
	{
	equationStatusId = newEquationStatusId;
	}
	
	public String getEquationStatusName()
	{
	return equationStatusName;
	}
	public void setEquationStatusName(String newEquationStatusName)
	{
	equationStatusName = newEquationStatusName;
	}  
}
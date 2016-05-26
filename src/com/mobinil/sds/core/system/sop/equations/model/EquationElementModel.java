package com.mobinil.sds.core.system.sop.equations.model;
import java.sql.*;
import java.io.*;

public class EquationElementModel implements Serializable
{
  int equationElementType;
  String equationElementValue;
  String equationElementValueId;
  
  public EquationElementModel()
  {
  }

	public int getEquationElementType()
	{
	return equationElementType;
	}
	public void setEquationElementType(int newEquationElementType)
	{
	equationElementType = newEquationElementType;
	}
	
	public String getEquationElementValue()
	{
	return equationElementValue;
	}
	public void setEquationElementValue(String newEquationElementValue)
	{
	equationElementValue = newEquationElementValue;
	}

  public String getEquationElementValueId()
	{
	return equationElementValueId;
	}
	public void setEquationElementValueId(String newEquationElementValueId)
	{
	equationElementValueId = newEquationElementValueId;
	}  
}
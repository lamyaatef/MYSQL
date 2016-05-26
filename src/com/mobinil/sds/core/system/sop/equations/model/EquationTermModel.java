package com.mobinil.sds.core.system.sop.equations.model;
import java.sql.*;
import java.io.*;

public class EquationTermModel implements Serializable
{
  String equationTermId;
  String equationTermName;
  int isFromDataView;
  String dataViewId;

  public static final String EQUATION_TERM_ID = "EQUATION_TERM_ID";
  public static final String EQUATION_TERM_NAME = "EQUATION_TERM_NAME";
  public static final String IS_FROM_DATA_VIEW = "IS_FROM_DATA_VIEW";
  public static final String DATA_VIEW_ID = "DATA_VIEW_ID";
  
  public EquationTermModel()
  {
  }

  public EquationTermModel(ResultSet res)
  {
    try
    {
      equationTermId = res.getString(EQUATION_TERM_ID);
      equationTermName = res.getString(EQUATION_TERM_NAME); 
      isFromDataView = res.getInt(IS_FROM_DATA_VIEW);
      dataViewId = res.getString(DATA_VIEW_ID);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
  }


  public String getEquationTermId()
	{
	return equationTermId;
	}
	public void setEquationTermId(String newEquationTermId)
	{
	equationTermId = newEquationTermId;
	}
	
	public String getEquationTermName()
	{
	return equationTermName;
	}
	public void setEquationTermName(String newEquationTermName)
	{
	equationTermName = newEquationTermName;
	}
	
	public int getIsFromDataView()
	{
	return isFromDataView;
	}
	public void setIsFromDataView(int newIsFromDataView)
	{
	isFromDataView = newIsFromDataView;
	}
	
	public String getDataViewId()
	{
	return dataViewId;
	}
	public void setDataViewId(String newDataViewId)
	{
	dataViewId = newDataViewId;
	} 
}
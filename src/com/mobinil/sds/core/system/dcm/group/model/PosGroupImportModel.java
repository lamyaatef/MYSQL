package com.mobinil.sds.core.system.dcm.group.model;
import java.sql.*;
import java.io.*;

public class PosGroupImportModel implements Serializable
{
  String posCode = "";
  String rowNum = "";

  public static final String POS_CODE = "POS_CODE";
  public static final String ROW_NUM = "ROW_NUM";
  
  public PosGroupImportModel()
  {
  }

  public PosGroupImportModel(ResultSet res)
  {
    try
    {
      posCode = res.getString(POS_CODE);
      rowNum = res.getString(ROW_NUM);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public String getPosCode()
	{
	return posCode;
	}
	public void setPosCode(String newPosCode)
	{
	posCode= newPosCode;
	}
	
	public String getRowNum()
	{
	return rowNum;
	}
	public void setRowNum(String newRowNum)
	{
	rowNum= newRowNum;
	}
}
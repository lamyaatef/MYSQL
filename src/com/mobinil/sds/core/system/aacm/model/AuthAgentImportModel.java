package com.mobinil.sds.core.system.aacm.model;
import java.sql.*;
import java.io.*;


public class AuthAgentImportModel implements Serializable{
	
	  String rowNum;
	  public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getBscsCode() {
		return bscsCode;
	}

	public void setBscsCode(String bscsCode) {
		this.bscsCode = bscsCode;
	}

	public String getDialNumber() {
		return dialNumber;
	}

	public void setDialNumber(String dialNumber) {
		this.dialNumber = dialNumber;
	}

	String bscsCode;
	  String dialNumber;
	  
	  public static final String ROW_NUM = "ROW_NUM";
	  public static final String BSCS_CODE = "BSCS_CODE";
	  public static final String DIAL_NUMBER = "DIAL_NUMBER";
	  
	  public AuthAgentImportModel(){
		  
	  }
	   
	  public AuthAgentImportModel(ResultSet res){
		  try
		  {
			  rowNum = res.getString(ROW_NUM);
			  bscsCode = res.getString(BSCS_CODE);
			  dialNumber = res.getString(DIAL_NUMBER);
		  }
		  catch(Exception e)
		    {
		      e.printStackTrace();
		    }  
	  }

}

package com.mobinil.sds.core.system.aacm.model;
import java.sql.*;
import java.io.*;

public class AMSImportModel implements Serializable{

	String rowNum;
	  public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getAuthAgentName() {
		return authAgentName;
	}

	public void setAuthAgentName(String authAgentName) {
		this.authAgentName = authAgentName;
	}

	public String getBscsCode() {
		return bscsCode;
	}

	public void setBscsCode(String bscsCode) {
		this.bscsCode = bscsCode;
	}

	String authAgentName;
	  String bscsCode;
	  
	  public static final String ROW_NUM = "ROW_NUM";
	  public static final String AUTHORIZED_AGENT_NAME = "AUTHORIZED_AGENT_NAME";
	  public static final String BSCS_CODE = "BSCS_CODE";
	  
	  public AMSImportModel(){
		  
	  }
	   
	  public AMSImportModel(ResultSet res){
		  try
		  {
			  rowNum = res.getString(ROW_NUM);
			  authAgentName = res.getString(AUTHORIZED_AGENT_NAME);
			  bscsCode = res.getString(BSCS_CODE);
		  }
		  catch(Exception e)
		    {
		      e.printStackTrace();
		    }  
	  }

}


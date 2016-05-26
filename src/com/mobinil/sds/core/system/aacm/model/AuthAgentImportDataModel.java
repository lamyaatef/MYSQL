package com.mobinil.sds.core.system.aacm.model;
import java.sql.*;
import java.io.*;

public class AuthAgentImportDataModel implements Serializable{
	
	String bscsCode;
	String dialNumber;
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
	
	
	public static final String BSCS_CODE = "BSCS_CODE";
	public static final String DIAL_NUMBER = "DIAL_NUMBER";
	
	public AuthAgentImportDataModel(){
		
	}
	public AuthAgentImportDataModel(ResultSet res){
		try
		{
			bscsCode = res.getString(BSCS_CODE);
			dialNumber = res.getString(DIAL_NUMBER);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

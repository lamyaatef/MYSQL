package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;

public class DcmValuesModel implements Serializable{
	
	String dcmId;
	String dcmName;
	public String getDcmId() {
		return dcmId;
	}
	public void setDcmId(String dcmId) {
		this.dcmId = dcmId;
	}
	public String getDcmName() {
		return dcmName;
	}
	public void setDcmName(String dcmName) {
		this.dcmName = dcmName;
	}
	public String getDcmValue() {
		return dcmValue;
	}
	public void setDcmValue(String dcmValue) {
		this.dcmValue = dcmValue;
	}
	String dcmValue;
	
	public static final String DCM_ID = "DCM_ID";
	public static final String DCM_NAME = "DCM_NAME";
	public static final String DCM_VALUE = "DCM_VALUE";
	
	
	public DcmValuesModel()
	  {
	  }
	
	public DcmValuesModel(ResultSet res)
	  {
	    try
	    {
	    	dcmId = res.getString(DCM_ID);
	    	dcmName = res.getString(DCM_NAME);
	    	dcmValue = res.getString(DCM_VALUE);
	     
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	  }


}

package com.mobinil.sds.core.system.aacm.model;
import java.sql.*;
import java.io.*;


public class AuthAgentModel implements Serializable
{
	String authAgentCode;
	public String getAuthAgentCode() {
		return authAgentCode;
	}


	public void setAuthAgentCode(String authAgentCode) {
		this.authAgentCode = authAgentCode;
	}


	public String getAuthAgentName() {
		return authAgentName;
	}


	public void setAuthAgentName(String authAgentName) {
		this.authAgentName = authAgentName;
	}


	String authAgentName;
	String dcmId;
	
	public String getDcmId() {
		return dcmId;
	}


	public void setDcmId(String dcmId) {
		this.dcmId = dcmId;
	}


	public static final String AUTH_AGENT_CODE = "AUTH_AGENT_CODE";
	public static final String AUTH_AGENT_NAME = "AUTH_AGENT_NAME";
	public static final String DCM_ID = "DCM_ID";
	
	public AuthAgentModel()
	  {
	  }
	
	
	public AuthAgentModel(ResultSet res)
	  {
	    try
	    {
	    	authAgentCode = res.getString(AUTH_AGENT_CODE);
	    	authAgentName = res.getString(AUTH_AGENT_NAME);
	    	dcmId = res.getString(DCM_ID);
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
}

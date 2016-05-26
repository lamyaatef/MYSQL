package com.mobinil.sds.core.system.sip.model;




import java.sql.ResultSet;
import java.util.*;

public class SIPReportTypeModel {
	public SIPReportTypeModel()
	{
		
		
	}
	String sipReportId;
	 String sipReportName;
	
	 
	 public static final String SIP_REPORT_ID="REPORT_TYPE_ID";
	 public static final String SIP_REPORT_NAME="REPORT_TYPE_NAME";
	

	  public SIPReportTypeModel(ResultSet res)
	  {
	    try
	    {
	    	sipReportId = res.getString(SIP_REPORT_ID);
	    	sipReportName = res.getString(SIP_REPORT_NAME);
	
	    	//moduleStatusName=res.getString(MODULE_STATUS_NAME);
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }


	public String getSipReportId() {
		return sipReportId;
	}


	public void setSipReportId(String sipReportId) {
		this.sipReportId = sipReportId;
	}


	public String getSipReportName() {
		return sipReportName;
	}


	public void setSipReportName(String sipReportName) {
		this.sipReportName = sipReportName;
	}




}

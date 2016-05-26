package com.mobinil.sds.core.system.sip.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SIPSavedReportModel {
	  private Integer savedReportId;
	  private String savedReportName;
	  private String savedReportType;
	  private Integer savedReportTypeID;
	  public Integer getSavedReportTypeID() {
		return savedReportTypeID;
	}

	public void setSavedReportTypeID(Integer savedReportTypeID) {
		this.savedReportTypeID = savedReportTypeID;
	}

	public static final String SIP_SAVED_REPORT_ID = "REPORT_ID";
	  public static final String SIP_SAVED_REPORT_NAME = "REPORT_NAME";
	  public static final String SIP_SAVED_REPORT_TYPE ="REPORT_TYPE";

	  
	  public SIPSavedReportModel(ResultSet res) throws SQLException
	  {
		  savedReportType= "";
		  int tempInt = res.getInt(SIP_SAVED_REPORT_TYPE);
		  if (tempInt==1)savedReportType = "Line Commission";
		  if (tempInt==2)savedReportType = "NI Commission";
		  if (tempInt==3)savedReportType = "SOP";
		  
		  savedReportId = new Integer (res.getInt(SIP_SAVED_REPORT_ID));
		  savedReportTypeID = res.getInt(SIP_SAVED_REPORT_TYPE);
		  savedReportName = res.getString(SIP_SAVED_REPORT_NAME);
		  
	  }

	  public String getSavedReportType() {
		return savedReportType;
	}

	public void setSavedReportType(String savedReportType) {
		this.savedReportType = savedReportType;
	}

	public Integer getSavedReportId() {
			return savedReportId;
		}

		public void setSavedReportId(Integer savedReportId) {
			this.savedReportId = savedReportId;
		}

		public String getSavedReportName() {
			return savedReportName;
		}

		public void setSavedReportName(String savedReportName) {
			this.savedReportName = savedReportName;
		}

		public SIPSavedReportModel()
		  {
		  }
	
}
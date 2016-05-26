package com.mobinil.sds.core.system.sip.model;

import java.sql.*;
import java.io.*;
public class sipReportChannelModel implements Serializable
{
	String sipReportChannelId;
	String sipReportChannelName;
	private String medhat;
	
	public static final String sipReport_CHANNEL_ID = "sip_Report_CHANNEL_ID";
	public static final String sipReport_CHANNEL_NAME = "sip_Report_CHANNEL_NAME";
	
	public sipReportChannelModel()
	  {
	  }
	
	public sipReportChannelModel(ResultSet res)
	  {
	   
	      try {
			sipReportChannelId = res.getString(sipReport_CHANNEL_ID);
			  sipReportChannelName = res.getString(sipReport_CHANNEL_NAME);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}   
	    
	  }
	
	 public String getsipReportChannelId()
	  {
	    return sipReportChannelId;
	  }

	  public void setsipReportChannelId(String newsipReportChannelId)
	  {
		  sipReportChannelId = newsipReportChannelId;
	  }
	////////////////////////////////////////////   
	  public String getsipReportChannelName()
	  {
	    return sipReportChannelName;
	  }

	  public void setsipReportChannelName(String newsipReportChannelName)
	  {
		  sipReportChannelName = newsipReportChannelName;
	  }
	////////////////////////////////////////////  

}
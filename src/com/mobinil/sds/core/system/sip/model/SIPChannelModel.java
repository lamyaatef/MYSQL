package com.mobinil.sds.core.system.sip.model;

import java.sql.*;
import java.io.*;
public class SIPChannelModel implements Serializable
{
	  private Integer channelId;
	  private String channelName;

	  public static final String SIP_CHANNEL_ID = "SIP_REPORT_CHANNEL_ID";
	  public static final String SIP_CHANNEL_NAME = "SIP_REPORT_CHANNEL_NAME";

	  public SIPChannelModel()
	  {
	  }
	  
	  public SIPChannelModel(ResultSet res) throws SQLException
	  {
		  channelId = new Integer (res.getInt(SIP_CHANNEL_ID));
		  channelName = res.getString(SIP_CHANNEL_NAME);
	  }

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getChannelId() {
		return channelId;
	}

}

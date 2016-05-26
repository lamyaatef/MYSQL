package com.mobinil.sds.core.system.commission.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChannelModel implements Serializable  

{
	  private Integer channelId;
	  private String channelName;

	  public static final String COMMISSION_CHANNEL_ID = "COMMISSION_CHANNEL_ID";
	  public static final String COMMISSION_CHANNEL_NAME = "COMMISSION_CHANNEL_NAME";

	  public ChannelModel()
	  {
	  }
	  
	  public ChannelModel(ResultSet res) throws SQLException
	  {
		  channelId = new Integer (res.getInt(COMMISSION_CHANNEL_ID));
		  channelName = res.getString(COMMISSION_CHANNEL_NAME);
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

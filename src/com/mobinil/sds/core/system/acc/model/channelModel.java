package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;


public class channelModel implements Serializable{
	
	String channelId;
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	String channelName;
	
	public static final String CHANNEL_ID = "CHANNEL_ID";
	public static final String CHANNEL_NAME = "CHANNEL_NAME";
	
	public channelModel()
	  {
	  }
	
	public channelModel(ResultSet res)
	  {
	    try
	    {
	    	channelId = res.getString(CHANNEL_ID);
	    	channelName = res.getString(CHANNEL_NAME);
	     
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	  }
	
	

}

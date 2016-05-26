package com.mobinil.sds.core.system.sop.requests.model;
import java.sql.*;
import java.io.*;


public class ChannelDCMModel implements Serializable{
	
	String channelId;
	String channelName;
	String dcmId;
	String dcmName;
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
	
	public static final String CHANNEL_ID = "CHANNEL_ID";
	public static final String CHANNEL_NAME = "CHANNEL_NAME";
	public static final String DCM_ID = "DCM_ID";
	public static final String DCM_NAME = "DCM_NAME";
	
	public ChannelDCMModel(){
		
	}
	
	public ChannelDCMModel(ResultSet res){
		try
		{
			channelId = res.getString(CHANNEL_ID);
			channelName = res.getString(CHANNEL_NAME);
			dcmId = res.getString(DCM_ID);
			dcmName = res.getString(DCM_NAME);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

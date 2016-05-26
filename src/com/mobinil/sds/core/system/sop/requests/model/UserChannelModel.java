package com.mobinil.sds.core.system.sop.requests.model;
import java.io.Serializable;
import java.sql.ResultSet;

public class UserChannelModel implements Serializable{
	
	String userId;
	String channelId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public static final String USER_ID = "USER_ID";
	public static final String CHANNEL_ID = "CHANNEL_ID";
	
	public UserChannelModel(){
		
	}
	
	public UserChannelModel(ResultSet res)
	{
		try
		{
			userId = res.getString(USER_ID);
			channelId = res.getString(CHANNEL_ID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

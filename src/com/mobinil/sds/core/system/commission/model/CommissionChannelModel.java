package com.mobinil.sds.core.system.commission.model;
import java.sql.*;
import java.io.*;
public class CommissionChannelModel implements Serializable
{
	String commissionChannelId;
	String commissionChannelName;
	private String medhat;
	
	public static final String COMMISSION_CHANNEL_ID = "COMMISSION_CHANNEL_ID";
	public static final String COMMISSION_CHANNEL_NAME = "COMMISSION_CHANNEL_NAME";
	
	public CommissionChannelModel()
	  {
	  }
	
	public CommissionChannelModel(ResultSet res)
	  {
	   
	      try {
			commissionChannelId = res.getString(COMMISSION_CHANNEL_ID);
			  commissionChannelName = res.getString(COMMISSION_CHANNEL_NAME);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}   
	    
	  }
	
	 public String getCommissionChannelId()
	  {
	    return commissionChannelId;
	  }

	  public void setCommissionChannelId(String newCommissionChannelId)
	  {
		  commissionChannelId = newCommissionChannelId;
	  }
	////////////////////////////////////////////   
	  public String getCommissionChannelName()
	  {
	    return commissionChannelName;
	  }

	  public void setCommissionChannelName(String newCommissionChannelName)
	  {
		  commissionChannelName = newCommissionChannelName;
	  }
	////////////////////////////////////////////  

}

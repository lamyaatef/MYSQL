package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;

public class AccrualDataModel implements Serializable{

	String accAccrualValueId;
	String activationDateFrom;
	String activationDateTo;
	String channelId;
	public String getAccAccrualValueId() {
		return accAccrualValueId;
	}
	public void setAccAccrualValueId(String accAccrualValueId) {
		this.accAccrualValueId = accAccrualValueId;
	}
	public String getActivationDateFrom() {
		return activationDateFrom;
	}
	public void setActivationDateFrom(String activationDateFrom) {
		this.activationDateFrom = activationDateFrom;
	}
	public String getActivationDateTo() {
		return activationDateTo;
	}
	public void setActivationDateTo(String activationDateTo) {
		this.activationDateTo = activationDateTo;
	}
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
	
	
	public static final String ACC_ACCRUAL_VALUE_ID = "ACC_ACCRUAL_VALUE_ID";
	public static final String ACTIVATION_DATE_FROM = "ACTIVATION_DATE_FROM";
	public static final String ACTIVATION_DATE_TO = "ACTIVATION_DATE_TO";
	public static final String CHANNEL_ID = "CHANNEL_ID";
	public static final String CHANNEL_NAME = "CHANNEL_NAME";
	
	public AccrualDataModel(){
	}
	
	public AccrualDataModel(ResultSet res)
	{
		try
		{
			accAccrualValueId = res.getString(ACC_ACCRUAL_VALUE_ID);
			activationDateFrom = res.getString(ACTIVATION_DATE_FROM);
			activationDateTo = res.getString(ACTIVATION_DATE_TO);
			channelId = res.getString(CHANNEL_ID);
			channelName = res.getString(CHANNEL_NAME);
		}
		
		catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	}
}

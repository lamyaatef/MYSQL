package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;

public class TransactionTypeModel implements Serializable{
	
	String transactionTypeId;
	String transactionTypeName;
	String channelId;
	String groupId;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(String transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	public String getTransactionTypeName() {
		return transactionTypeName;
	}
	public void setTransactionTypeName(String transactionTypeName) {
		this.transactionTypeName = transactionTypeName;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public static final String TRANSACTION_TYPE_ID = "TRANSACTION_TYPE_ID";
	public static final String TRANSACTION_TYPE_NAME = "TRANSACTION_TYPE_NAME";
	public static final String CHANNEL_ID = "CHANNEL_ID";
	public static final String GROUP_ID = "TRANSACTION_GROUP_ID";
	
	
	public TransactionTypeModel(){
		
	}
	
	public TransactionTypeModel(ResultSet res)
	{
		try
		{
			transactionTypeId = res.getString(TRANSACTION_TYPE_ID);
			transactionTypeName = res.getString(TRANSACTION_TYPE_NAME);
			channelId = res.getString(CHANNEL_ID);
			groupId = res.getString(GROUP_ID);
		}
		catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	}
	
}

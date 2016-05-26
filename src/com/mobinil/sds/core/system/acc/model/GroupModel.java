package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;

public class GroupModel implements Serializable{
	
	String groupId;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	String groupName;

	public static final String CR_TRANSACTION_GROUP_ID = "CR_TRANSACTION_GROUP_ID";
	public static final String CR_TRANSACTION_GROUP_NAME = "CR_TRANSACTION_GROUP_NAME";
	
	public GroupModel(){
		
	}
	public GroupModel(ResultSet res){
		try
		{
			groupId = res.getString(CR_TRANSACTION_GROUP_ID);
			groupName = res.getString(CR_TRANSACTION_GROUP_NAME);
		}
		catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	}
}

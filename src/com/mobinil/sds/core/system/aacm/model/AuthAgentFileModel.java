package com.mobinil.sds.core.system.aacm.model;
import java.sql.*;
import java.io.*;

public class AuthAgentFileModel implements Serializable{
	
	String fileId;
	String month;
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	String year;
	String status;
	String userId;
	String timestamp;
	String userName;
	String count;
	
	public static final String FILE_ID = "FILE_ID";
	public static final String MONTH = "MONTH";
	public static final String YEAR = "YEAR";
	public static final String STATUS = "STATUS";
	public static final String USER_ID = "USER_ID";
	public static final String TIMESTAMP = "TIMESTAMP";
	public static final String PERSON_FULL_NAME = "PERSON_FULL_NAME";
	public static final String COUNT = "COUNT";
	
	public AuthAgentFileModel(){
		
	}
	
	public AuthAgentFileModel(ResultSet res)
	{
		try
		{
			fileId = res.getString(FILE_ID);
			month = res.getString(MONTH);
			year = res.getString(YEAR);
			status = res.getString(STATUS);
			userId = res.getString(USER_ID);
			timestamp = res.getString(TIMESTAMP);
			userName = res.getString(PERSON_FULL_NAME);
			count = res.getString(COUNT);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

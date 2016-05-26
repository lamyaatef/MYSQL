package com.mobinil.sds.core.system.aacm.model;
import java.sql.*;
import java.io.*;

public class AMSImportFileModel implements Serializable{
	
	String fileId;
	String monthOfMapping;
	String yearOfMapping;
	String userId;
	String timestamp;
	String status;
	String personFullName;
	String count;
	public String getPersonFullName() {
		return personFullName;
	}
	public void setPersonFullName(String personFullName) {
		this.personFullName = personFullName;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getMonthOfMapping() {
		return monthOfMapping;
	}
	public void setMonthOfMapping(String monthOfMapping) {
		this.monthOfMapping = monthOfMapping;
	}
	public String getYearOfMapping() {
		return yearOfMapping;
	}
	public void setYearOfMapping(String yearOfMapping) {
		this.yearOfMapping = yearOfMapping;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public static final String FILE_ID = "FILE_ID";
	public static final String MONTH_OF_MAPPING = "MONTH_OF_MAPPING";
	public static final String YEAR_OF_MAPPING = "YEAR_OF_MAPPING";
	public static final String TIMESTAMP = "TIMESTAMP";
	public static final String USER_ID = "USER_ID";
	public static final String STATUS = "STATUS";
	public static final String PERSON_FULL_NAME = "PERSON_FULL_NAME";
	public static final String COUNT = "COUNT";
	
	public AMSImportFileModel(){
	}
	public AMSImportFileModel(ResultSet res){
		try
		{
			fileId = res.getString(FILE_ID);
			monthOfMapping = res.getString(MONTH_OF_MAPPING);
			yearOfMapping = res.getString(YEAR_OF_MAPPING);
			timestamp = res.getString(TIMESTAMP);
			userId = res.getString(USER_ID);
			status = res.getString(STATUS);
			personFullName = res.getString(PERSON_FULL_NAME);
			count = res.getString(COUNT);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

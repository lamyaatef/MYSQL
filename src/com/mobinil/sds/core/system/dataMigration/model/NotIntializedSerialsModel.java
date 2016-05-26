package com.mobinil.sds.core.system.dataMigration.model;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class NotIntializedSerialsModel implements Serializable{
	
	String fileId;
	String year;
	String month;
	String status;
	String userId;
	Timestamp time_stamp;
	String problematicName;
	String paymentLevelName;
	
	public String getProblematicName() {
		return problematicName;
	}
	public void setProblematicName(String problematicName) {
		this.problematicName = problematicName;
	}
	public String getPaymentLevelName() {
		return paymentLevelName;
	}
	public void setPaymentLevelName(String paymentLevelName) {
		this.paymentLevelName = paymentLevelName;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
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
	public Timestamp getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}

	public static final String FILE_ID = "FILE_ID";
	public static final String YEAR = "YEAR";
	public static final String MONTH = "MONTH";
	public static final String STATUS = "STATUS";
	public static final String USER_ID = "USER_ID";
	public static final String TIME_STAMP= "TIME_STAMP";
	public static final String PROBLEMATIC_LABEL_NAME = "PROBLEMATIC_LABEL_NAME";
	public static final String PAYMENT_LAVEL_NAME = "PAYMENT_LAVEL_NAME";
	
	public NotIntializedSerialsModel(){
		
	}
	public NotIntializedSerialsModel(ResultSet res)
	{
		try
		{
			fileId = res.getString(FILE_ID);
			year = res.getString(YEAR);
			month = res.getString(MONTH);
			status = res.getString(STATUS);
			userId = res.getString(USER_ID);
			time_stamp = res.getTimestamp(TIME_STAMP);
			problematicName = res.getString(PROBLEMATIC_LABEL_NAME);
			paymentLevelName = res.getString(PAYMENT_LAVEL_NAME);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}

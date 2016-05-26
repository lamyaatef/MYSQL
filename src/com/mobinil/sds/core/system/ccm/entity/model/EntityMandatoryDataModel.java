package com.mobinil.sds.core.system.ccm.entity.model;

import java.sql.ResultSet;

public class EntityMandatoryDataModel {
	
	String entityId;
	String entityyName;
	String entityTypeID;
	String entityAdress;
	String entityCode;
	String userId;
	String systemDate;
	String recordId;
	String status;

	public static final String  ENTITY_ID   = "ENTITY_ID";
	public static final String  ENTITY_NAME  = "ENTITY_NAME";
	public static final String ENTITY_TYPE_ID  = "ENTITY_TYPE_ID";
	public static final String ENTITY_ADDRESS = "ENTITY_ADDRESS";
	public static final String ENTITY_CODE = "ENTITY_CODE";
	public static final String  USER_ID  = " USER_ID";
	public static final String SYSTEM_DATE = "SYSTEM_DATE ";
	public static final String  RECORD_ID  = "RECORD_ID ";
	public static final String   STATUS   = "STATUS";
	

	EntityMandatoryDataModel () {}

	public EntityMandatoryDataModel(ResultSet res) {
		try {
			entityId = res.getString("ENTITY_ID");
			entityyName = res.getString("ENTITY_NAME");
			entityTypeID = res.getString("ENTITY_TYPE_ID");
			entityAdress = res.getString("ENTITY_ADDRESS");
			entityCode= res.getString("ENTITY_CODE");
			userId = res.getString("USER_ID");
			systemDate= res.getString("SYSTEM_DATE");
			recordId = res.getString("RECORD_ID");
			status= res.getString("STATUS");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}


	public String getEntityyName() {
		return entityyName;
	}


	public void setEntityyName(String entityyName) {
		this.entityyName = entityyName;
	}


	public String getEntityTypeID() {
		return entityTypeID;
	}


	public void setEntityTypeID(String entityTypeID) {
		this.entityTypeID = entityTypeID;
	}


	public String getEntityAdress() {
		return entityAdress;
	}


	public void setEntityAdress(String entityAdress) {
		this.entityAdress = entityAdress;
	}


	public String getEntityCode() {
		return entityCode;
	}


	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getSystemDate() {
		return systemDate;
	}


	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}


	public String getRecordId() {
		return recordId;
	}


	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	

}

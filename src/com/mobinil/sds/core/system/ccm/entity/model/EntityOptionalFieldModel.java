package com.mobinil.sds.core.system.ccm.entity.model;

import java.sql.ResultSet;

public class EntityOptionalFieldModel {

	String entityFieldId;
	String orderId;
	String entityTypeID;
	String sqlQuery;
	String entityfieldLabel;
	String entityFieldTypeId;
	String isMandatory;
	String entityFieldTypeName;


	public static final String  ENTITY_FIELD_ID  = "ENTITY_FIELD_ID";
	public static final String  ORDER_ID = "ORDER_ID";
	public static final String ENTITY_TYPE_ID = "ENTITY_TYPE_ID";
	public static final String SQL_QUERY = "SQL_QUERY";
	public static final String ENTITY_FIELD_LABEL = "ENTITY_FIELD_LABEL";
	public static final String  ENTITY_FIELD_TYPE_ID  = "ENTITY_FIELD_TYPE_ID";
	public static final String IS_MANDATORY_ID = "IS_MANDATORY_ID";
	public static final String  ENTITY_FIELD_TYPE_NAME = "ENTITY_FIELD_TYPE_NAME";


	EntityOptionalFieldModel() {}

	public EntityOptionalFieldModel(ResultSet res) {
		try {
			entityFieldId = res.getString("ENTITY_FIELD_ID");
			orderId = res.getString("ORDER_ID");
			entityTypeID = res.getString("ENTITY_TYPE_ID");
			sqlQuery = res.getString("SQL_QUERY");
			entityfieldLabel= res.getString("ENTITY_FIELD_LABEL");	
			entityFieldTypeId= res.getString("ENTITY_FIELD_TYPE_ID");
			isMandatory = res.getString("IS_MANDATORY_ID");
			entityFieldTypeName= res.getString("ENTITY_FIELD_TYPE_NAME");
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getEntityFieldId() {
		return entityFieldId;
	}

	public void setEntityFieldId(String entityFieldId) {
		this.entityFieldId = entityFieldId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEntityTypeID() {
		return entityTypeID;
	}

	public void setEntityTypeID(String entityTypeID) {
		this.entityTypeID = entityTypeID;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public String getEntityfieldLabel() {
		return entityfieldLabel;
	}

	public void setEntityfieldLabel(String entityfieldLabel) {
		this.entityfieldLabel = entityfieldLabel;
	}

	public String getEntityFieldTypeId() {
		return entityFieldTypeId;
	}

	public void setEntityFieldTypeId(String entityFieldTypeId) {
		this.entityFieldTypeId = entityFieldTypeId;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getEntityFieldTypeName() {
		return entityFieldTypeName;
	}

	public void setEntityFieldTypeName(String entityFieldTypeName) {
		this.entityFieldTypeName = entityFieldTypeName;
	}
}

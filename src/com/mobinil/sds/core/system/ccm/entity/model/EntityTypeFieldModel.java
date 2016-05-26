package com.mobinil.sds.core.system.ccm.entity.model;

import java.sql.ResultSet;

public class EntityTypeFieldModel {

	String entityFieldId;
	String orderId;
	String entityTypeId;
	String sqlQuery;
	String entityFieldLabel;
	String entityFieldTypeId;
	String isMandatory;
	String entityFieldTypeName;
	
	public static final String ENTITY_FIELD_ID = "ENTITY_FIELD_ID";
	public static final String ORDER_ID = "ORDER_ID";
	public static final String ENTITY_TYPE_ID = "ENTITY_TYPE_ID";
	public static final String SQL_QUERY = "SQL_QUERY";
	public static final String ENTITY_FIELD_LABEL = "ENTITY_FIELD_LABEL";
	public static final String ENTITY_FIELD_TYPE_ID= " ENTITY_FIELD_TYPE_ID";
	public static final String IS_MANDATORY_ID = "IS_MANDATORY_ID";
	public static final String ENTITY_FIELD_TYPE_NAME = "ENTITY_FIELD_TYPE_NAME";

	EntityTypeFieldModel() {
	}

	public EntityTypeFieldModel(ResultSet res) {
		try {
			entityFieldId = res.getString(ENTITY_FIELD_ID);
			orderId= res.getString("ORDER_ID");
			entityTypeId = res.getString("ENTITY_TYPE_ID");
			sqlQuery = res.getString("SQL_QUERY");
			entityFieldLabel = res.getString("ENTITY_FIELD_LABEL");
			entityFieldTypeId=res.getString("ENTITY_FIELD_TYPE_ID");
			isMandatory= res.getString("IS_MANDATORY_ID");
			entityFieldTypeName=res.getString("ENTITY_FIELD_TYPE_NAME");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getEntityFieldTypeName() {
		return entityFieldTypeName;
	}

	public void setEntityFieldTypeName(String entityFieldTypeName) {
		this.entityFieldTypeName = entityFieldTypeName;
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

	public String getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(String entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public String getEntityFieldLabel() {
		return entityFieldLabel;
	}

	public void setEntityFieldLabel(String entityFieldLabel) {
		this.entityFieldLabel = entityFieldLabel;
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

}

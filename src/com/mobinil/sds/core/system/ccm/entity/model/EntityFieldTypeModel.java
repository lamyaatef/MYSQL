package com.mobinil.sds.core.system.ccm.entity.model;

import java.sql.ResultSet;

public class EntityFieldTypeModel {

	String entityFieldTypeId;
	String entityyFieldType;

	public static final String ENTITY_FIELD_TYPE_ID = "ENTITY_FIELD_TYPE_ID";
	public static final String ENTITY_FIELD_TYPE = "ENTITY_FIELD_TYPE";

	EntityFieldTypeModel() {

	}

	public EntityFieldTypeModel(ResultSet res) {
		try {
			entityFieldTypeId = res.getString("ENTITY_FIELD_TYPE_ID");
			entityyFieldType = res.getString("ENTITY_FIELD_TYPE_NAME");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getEntityFieldTypeId() {
		return entityFieldTypeId;
	}

	public void setEntityFieldTypeId(String entityFieldTypeId) {
		this.entityFieldTypeId = entityFieldTypeId;
	}

	public String getEntityyFieldType() {
		return entityyFieldType;
	}

	public void setEntityyFieldType(String entityyFieldType) {
		this.entityyFieldType = entityyFieldType;
	}

}

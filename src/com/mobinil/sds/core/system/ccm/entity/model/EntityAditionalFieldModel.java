package com.mobinil.sds.core.system.ccm.entity.model;

import java.sql.ResultSet;

public class EntityAditionalFieldModel {
	
	String entityId;
	String entityFieldValue;
	String entityFieldID;
	String entityFieldLabel;
	public static final String ENTITY_ID =  "ENTITY_ID";
	public static final String ENTITY_FIELD_VALUE = "ENTITY_FIELD_VALUE";
	public static final String ENTITY_FIELD_ID = "ENTITY_FIELD_ID";	
	public static final String ENTITY_FIELD_LABEL = "ENTITY_FIELD_LABEL";

      EntityAditionalFieldModel() {}
      
  	public EntityAditionalFieldModel(ResultSet res) {
		try {
			
			entityId= res.getString(ENTITY_ID);
			entityFieldValue= res.getString(ENTITY_FIELD_VALUE);
			entityFieldID= res.getString(ENTITY_FIELD_ID);
			entityFieldLabel= res.getString(ENTITY_FIELD_LABEL);
		
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

	public String getEntityFieldValue() {
		return entityFieldValue;
	}

	public void setEntityFieldValue(String entityFieldValue) {
		this.entityFieldValue = entityFieldValue;
	}

	public String getEntityFieldID() {
		return entityFieldID;
	}

	public void setEntityFieldID(String entityFieldID) {
		this.entityFieldID = entityFieldID;
	}    
	
	public String getEntityFieldLabel() {
		return entityFieldLabel;
	}

	public void setEntityFieldLabel(String entityFieldLabel) {
		this.entityFieldLabel = entityFieldLabel;
	}
     
}

package com.mobinil.sds.core.system.ccm.entity.model;

import java.sql.ResultSet;

public class EntityAditionalFieldLabelModel {

	//String entityId;
	String entityFieldLabel;
	//public static final String ENTITY_ID = "ENTITY_ID";
	public static final String ENTITY_FIELD_LABEL = "ENTITY_FIELD_LABEL";

	EntityAditionalFieldLabelModel() {}
      
  	public EntityAditionalFieldLabelModel(ResultSet res) {
		try {
			
		 //  entityId= res.getString(ENTITY_ID);
		
			entityFieldLabel= res.getString(ENTITY_FIELD_LABEL);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String getEntityFieldLabel() {
		return entityFieldLabel;
	}

	public void setEntityFieldLabel(String entityFieldLabel) {
		this.entityFieldLabel = entityFieldLabel;
	}
     
}

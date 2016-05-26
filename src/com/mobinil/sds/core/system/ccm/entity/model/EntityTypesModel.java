package com.mobinil.sds.core.system.ccm.entity.model;

import java.sql.ResultSet;

public class EntityTypesModel {
	
    private String ENTITY_TYPE_ID;
    private String ENTITY_TYPE_NAME;
    EntityTypesModel()
    {
      
    }

    public EntityTypesModel(ResultSet res)
    {
      try
      {
    	  ENTITY_TYPE_ID = res.getString("ENTITY_TYPE_ID");
    	  ENTITY_TYPE_NAME = res.getString("ENTITY_TYPE_NAME");

      }
      catch(Exception e)
      {
        e.printStackTrace();
      } 
    }
    
    
	public String getENTITY_TYPE_ID() {
		return ENTITY_TYPE_ID;
	}



	public void setENTITY_TYPE_ID(String entity_type_id) {
		ENTITY_TYPE_ID = entity_type_id;
	}



	public String getENTITY_TYPE_NAME() {
		return ENTITY_TYPE_NAME;
	}



	public void setENTITY_TYPE_NAME(String entity_type_name) {
		ENTITY_TYPE_NAME = entity_type_name;
	}

}

package com.mobinil.sds.core.system.ccm.entity.model;

import java.sql.ResultSet;

public class EntityListOptionModel {
	
    private String ENTITY_FIELD_ID;
    private String LIST_OPTIONS;
    

    public EntityListOptionModel()
    {
      
    }

    public EntityListOptionModel(ResultSet res)
    {
      try
      {
    	  ENTITY_FIELD_ID = res.getString("ENTITY_FIELD_ID");
    	  LIST_OPTIONS = res.getString("LIST_OPTIONS");

      }
      catch(Exception e)
      {
        e.printStackTrace();
      } 
    }

	public String getENTITY_FIELD_ID() {
		return ENTITY_FIELD_ID;
	}

	public void setENTITY_FIELD_ID(String entity_field_id) {
		ENTITY_FIELD_ID = entity_field_id;
	}

	public String getLIST_OPTIONS() {
		return LIST_OPTIONS;
	}

	public void setLIST_OPTIONS(String list_options) {
		LIST_OPTIONS = list_options;
	}
    


}

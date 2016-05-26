package com.mobinil.sds.core.system.ccm.entity.dto;
import java.io.Serializable;
import java.util.Vector;
import com.mobinil.sds.core.system.ccm.entity.model.*;
import com.mobinil.sds.core.system.ccm.entityproject.model.entityProjectModel;
public class EntityDto {

public EntityDto()
  {
  }
  private entityProjectModel model;
  private Vector childEntities;
  
  
  
  public entityProjectModel getModel() {
		return model;
	}
	public void setModel(entityProjectModel model) {
		this.model = model;
	}
	public Vector getChildEntities() {
		return childEntities;
	}
	public void setChildEntities(Vector childEntities) {
		this.childEntities = childEntities;
	}

}
package com.mobinil.sds.core.system.ccm.entityproject.model;

import java.sql.*;
import java.io.*;

public class entityProjectModel implements Serializable
 
{
	

  String projectId;
  String projectName;
  String schemaId;
  String entityId;
  String entityName;
  String parentId;
  String levelId;
  String serviceId;
  String serviceName;
  String  entityTypeId;
  
  private String id;
  private String name;
  private String parentid;
  
  public static final String PROJECT_ID = "PROJECT_ID";
  public static final String PROJECT_NAME = "PROJECT_NAME";
  public static final String SCHEMA_ID = "SCHEMA_ID";
  public static final String ENTITY_ID= "ENTITY_ID";
  public static final String ENTITY_NAME = "ENTITY_NAME";
  public static final String ENTITY_TYPE_ID = "ENTITY_TYPE_ID";
  public static final String PARENT_ID = "PARENT_ID";
  public static final String LEVEL_ID = "LEVEL_ID";
  public static final String SERVICE_ID = "SERVICE_ID";
  public static final String SERVICE_NAME = "SERVICE_NAME";


  
  public entityProjectModel()
  {
  }


  public entityProjectModel(ResultSet res)
  {
    try
    {
    	projectId = res.getString(PROJECT_ID);
    	projectName = res.getString(PROJECT_NAME);
    	schemaId = res.getString(SCHEMA_ID);
        entityId= res.getString(ENTITY_ID);
        entityName = res.getString(ENTITY_NAME);
        entityTypeId= res.getString(ENTITY_TYPE_ID); 
        parentId= res.getString(PARENT_ID);
        levelId = res.getString(LEVEL_ID);
        serviceId = res.getString(SERVICE_ID);    
        serviceName = res.getString(SERVICE_NAME); 
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 

  } 

public String getEntityTypeId() {
	return entityTypeId;
}


public void setEntityTypeId(String entityTypeId) {
	this.entityTypeId = entityTypeId;
}


public String getProjectId() {
	return projectId;
}


public void setProjectId(String projectId) {
	this.projectId = projectId;
}


public String getProjectName() {
	return projectName;
}


public void setProjectName(String projectName) {
	this.projectName = projectName;
}


public String getSchemaId() {
	return schemaId;
}


public void setSchemaId(String schemaId) {
	this.schemaId = schemaId;
}


public String getEntityId() {
	return entityId;
}


public void setEntityId(String entityId) {
	this.entityId = entityId;
}


public String getEntityName() {
	return entityName;
}


public void setEntityName(String entityName) {
	this.entityName = entityName;
}


public String getParentId() {
	return parentId;
}


public void setParentId(String parentId) {
	this.parentId = parentId;
}


public String getLevelId() {
	return levelId;
}


public void setLevelId(String levelId) {
	this.levelId = levelId;
}


public String getServiceId() {
	return serviceId;
}


public void setServiceId(String serviceId) {
	this.serviceId = serviceId;
}


public String getServiceName() {
	return serviceName;
}


public void setServiceName(String serviceName) {
	this.serviceName = serviceName;
}


public String getId() {
	return id;
}


public void setId(String id) {
	this.id = id;
}


public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}


public String getParentid() {
	return parentid;
}


public void setParentid(String parentid) {
	this.parentid = parentid;
}
  }

package com.mobinil.sds.core.system.ccm.project.model;
import java.sql.ResultSet;
public class ProjectModel {
	

	String projectName;
	String projectDetail;
	String projectStautusId;
	String projectAddress;
	String projectCreationDate;
	String schemaId;
	String schemaName;

	public static final String  PROJECT_ID  = "PROJECT_ID";
	public static final String PROJECT_NAME  = "PROJECT_NAME";
	public static final String PROJECT_DETAIL  = "PROJECT_DETAIL";
	public static final String PROJECT_STATUS_ID="PROJECT_STATUS_ID";
	public static final String PROJECT_CREATION_DATE="PROJECT_CREATION_DATE";
	public static final String PROJECT_ADDRESS= "PROJECT_ADDRESS";
	public static final String SCHEMA_ID= "SCHEMA_ID";
	public static final String SCHEMA_NAME= "SCHEMA_NAME";
	
		 ProjectModel() {}
	
	
	
	public ProjectModel(ResultSet res) {
		try {
			projectId = res.getString("PROJECT_ID");
			projectName = res.getString("PROJECT_NAME");
			projectDetail = res.getString("PROJECT_DETAIL");
			projectStautusId=res.getString("PROJECT_STATUS_ID");
			projectAddress=res.getString("PROJECT_ADDRESS");
			projectCreationDate=res.getString("PROJECT_CREATION_DATE");
			schemaId=res.getString("SCHEMA_ID");
			schemaId=res.getString("SCHEMA_ID");
			schemaName=res.getString("SCHEMA_NAME");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


			public String getSchemaName() {
		return schemaName;
	}



	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}



			public String getSchemaId() {
		return schemaId;
	}



	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}



			public String getProjectAddress() {
		return projectAddress;
	}



	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}



	public String getProjectCreationDate() {
		return projectCreationDate;
	}



	public void setProjectCreationDate(String projectCreationDate) {
		this.projectCreationDate = projectCreationDate;
	}


			String projectId;
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



			public String getProjectDetail() {
				return projectDetail;
			}



			public void setProjectDetail(String projectDetail) {
				this.projectDetail = projectDetail;
			}



			public String getProjectStautusId() {
				return projectStautusId;
			}



			public void setProjectStautusId(String projectStautusId) {
				this.projectStautusId = projectStautusId;
			}


	}

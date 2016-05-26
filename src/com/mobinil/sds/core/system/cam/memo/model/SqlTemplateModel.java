package com.mobinil.sds.core.system.cam.memo.model;

public class SqlTemplateModel {
	private int templateId;
	private String templateName;
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	private String templateSql;
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public String getTemplateSql() {
		return templateSql;
	}
	public void setTemplateSql(String templateSql) {
		this.templateSql = templateSql;
	}
	public SqlTemplateModel() {
		super();
	}
	

}

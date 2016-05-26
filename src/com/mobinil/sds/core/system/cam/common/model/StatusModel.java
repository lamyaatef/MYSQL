package com.mobinil.sds.core.system.cam.common.model;

public class StatusModel 
{
private int status_id;
private String status_name, status_desc;
  public StatusModel()
  {
  }
  public StatusModel(int status_id, String status_name, String status_desc) {
		super();
		this.status_id = status_id;
		this.status_name = status_name;
		this.status_desc = status_desc;
	}
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public String getStatus_desc() {
		return (status_desc==null)?"":status_desc;
	}
	public void setStatus_desc(String status_desc) {
		this.status_desc = status_desc;
	}
}
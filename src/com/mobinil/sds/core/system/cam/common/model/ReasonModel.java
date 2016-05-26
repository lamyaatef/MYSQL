package com.mobinil.sds.core.system.cam.common.model;

public class ReasonModel 
{
private int reason_id, reason_status_id;
private String reason_name, reason_desc, reason_status_name;
  public ReasonModel()
  {
  }
  
	public ReasonModel(int reason_id,
			 String reason_name,String reason_desc, int reason_status_id, String reason_status_name) {
		super();
		this.reason_id = reason_id;
		this.reason_status_id = reason_status_id;
		this.reason_name = reason_name;
		this.reason_desc = reason_desc;
		this.reason_status_name = reason_status_name;
	}
	public int getReason_id() {
		return reason_id;
	}
	public void setReason_id(int reason_id) {
		this.reason_id = reason_id;
	}
	public int getReason_status_id() {
		return reason_status_id;
	}
	public void setReason_status_id(int reason_status_id) {
		this.reason_status_id = reason_status_id;
	}
	public String getReason_name() {
		return reason_name;
	}
	public void setReason_name(String reason_name) {
		this.reason_name = reason_name;
	}
	public String getReason_desc() {
		return (reason_desc==null)?"":reason_desc;
	}
	public void setReason_desc(String reason_desc) {
		this.reason_desc = reason_desc;
	}
	public String getReason_status_name() {
		return reason_status_name;
	}
	public void setReason_status_name(String reason_status_name) {
		this.reason_status_name = reason_status_name;
	}
}
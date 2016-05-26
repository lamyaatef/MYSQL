package com.mobinil.sds.core.system.cam.memo.model;

public class SubChannelModel {
	private int subChannelId;
	private String criteriaName;
	public SubChannelModel(int subChannelId, String criteriaName) {
		super();
		this.subChannelId = subChannelId;
		this.criteriaName = criteriaName;
	}
	public int getSubChannelId() {
		return subChannelId;
	}
	public void setSubChannelId(int subChannelId) {
		this.subChannelId = subChannelId;
	}
	public String getCriteriaName() {
		return criteriaName;
	}
	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}
	

}

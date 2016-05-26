package com.mobinil.sds.core.system.cam.memo.model;

public class DcmMemoHistoryModel {
	private String memoName;
	private String actionName;
	private String actionDate;
	private String userName;
	private String reason;
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public DcmMemoHistoryModel(String memoName, String actionName,
			String actionDate, String userName, String reason) {
		super();
		this.memoName = memoName;
		this.actionName = actionName;
		this.actionDate = actionDate;
		this.userName = userName;
		this.reason = reason;
	}
	public DcmMemoHistoryModel(String memoName, String actionName,
			String actionDate, String userName) {
		super();
		this.memoName = memoName;
		this.actionName = actionName;
		this.actionDate = actionDate;
		this.userName = userName;
	}
	public String getMemoName() {
		return memoName;
	}
	public void setMemoName(String memoName) {
		this.memoName = memoName;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}

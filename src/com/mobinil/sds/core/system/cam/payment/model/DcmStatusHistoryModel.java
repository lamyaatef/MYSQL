package com.mobinil.sds.core.system.cam.payment.model;

public class DcmStatusHistoryModel {
	private String paymentName;
	private String actionName;
	private String reason;
	private String deductionDate;
	private String userName;
	public DcmStatusHistoryModel(String paymentName, String actionName,
			String reason, String deductionDate, String userName) {
		super();
		this.paymentName = paymentName;
		this.actionName = actionName;
		this.reason = reason;
		this.deductionDate = deductionDate;
		this.userName = userName;
	}
	public DcmStatusHistoryModel(String actionName, String reason,
			String deductionDate, String userName) {
		super();
		this.actionName = actionName;
		this.reason = reason;
		this.deductionDate = deductionDate;
		this.userName = userName;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDeductionDate() {
		return deductionDate;
	}
	public void setDeductionDate(String deductionDate) {
		this.deductionDate = deductionDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	

}

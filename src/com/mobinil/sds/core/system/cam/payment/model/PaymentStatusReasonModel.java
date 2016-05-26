package com.mobinil.sds.core.system.cam.payment.model;

public class PaymentStatusReasonModel {
	private int reasonId;
	private String reason;
	private String reasonDesc;
	
	public PaymentStatusReasonModel(int reasonId, String reason) {
		super();
		this.reasonId = reasonId;
		this.reason = reason;
	}
	
	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public PaymentStatusReasonModel(int reasonId, String reason,
			String reasonDesc) {
		super();
		this.reasonId = reasonId;
		this.reason = reason;
		this.reasonDesc = reasonDesc;
	}

	public int getReasonId() {
		return reasonId;
	}
	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	

}

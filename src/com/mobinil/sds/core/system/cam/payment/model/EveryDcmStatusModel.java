package com.mobinil.sds.core.system.cam.payment.model;

public class EveryDcmStatusModel {
	private int scmPaymentStatusId;
	private int paymentCamStateId;
	private String camStatusForPayment;
	private int scmId;
	private String reason;
	public EveryDcmStatusModel(int scmPaymentStatusId, int paymentCamStateId,
			String camStatusForPayment, int scmId, String reason) {
		super();
		this.scmPaymentStatusId = scmPaymentStatusId;
		this.paymentCamStateId = paymentCamStateId;
		this.camStatusForPayment = camStatusForPayment;
		this.scmId = scmId;
		this.reason = reason;
	}
	public int getPaymentCamStateId() {
		return paymentCamStateId;
	}
	public void setPaymentCamStateId(int paymentCamStateId) {
		this.paymentCamStateId = paymentCamStateId;
	}
	public String getCamStatusForPayment() {
		return camStatusForPayment;
	}
	public void setCamStatusForPayment(String camStatusForPayment) {
		this.camStatusForPayment = camStatusForPayment;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getScmPaymentStatusId() {
		return scmPaymentStatusId;
	}
	public void setScmPaymentStatusId(int scmPaymentStatusId) {
		this.scmPaymentStatusId = scmPaymentStatusId;
	}
	public int getScmId() {
		return scmId;
	}
	public void setScmId(int scmId) {
		this.scmId = scmId;
	}
	 
	

}

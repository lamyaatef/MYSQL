package com.mobinil.sds.core.system.cam.payment.model;

public class PaymentTypeMethodModel {
	private int paymentTypeMethodId;
	private String paymentTypeMethodName;
	public PaymentTypeMethodModel(int paymentTypeMethodId,
			String paymentTypeMethodName) {
		super();
		this.paymentTypeMethodId = paymentTypeMethodId;
		this.paymentTypeMethodName = paymentTypeMethodName;
	}
	public int getPaymentTypeMethodId() {
		return paymentTypeMethodId;
	}
	public void setPaymentTypeMethodId(int paymentTypeMethodId) {
		this.paymentTypeMethodId = paymentTypeMethodId;
	}
	public String getPaymentTypeMethodName() {
		return paymentTypeMethodName;
	}
	public void setPaymentTypeMethodName(String paymentTypeMethodName) {
		this.paymentTypeMethodName = paymentTypeMethodName;
	}
	

}

package com.mobinil.sds.core.system.cam.payment.model;

public class PaymentStatusCasesModel 
{
  private int camStatusForPaymentId ;
    private String camStatusForPayment;
	public PaymentStatusCasesModel(int camStatusForPaymentId,
			String camStatusForPayment) {
		super();
		this.camStatusForPaymentId = camStatusForPaymentId;
		this.camStatusForPayment = camStatusForPayment;
	}
	public int getCamStatusForPaymentId() {
		return camStatusForPaymentId;
	}
	public void setCamStatusForPaymentId(int camStatusForPaymentId) {
		this.camStatusForPaymentId = camStatusForPaymentId;
	}
	public String getCamStatusForPayment() {
		return camStatusForPayment;
	}
	public void setCamStatusForPayment(String camStatusForPayment) {
		this.camStatusForPayment = camStatusForPayment;
	}

    
}
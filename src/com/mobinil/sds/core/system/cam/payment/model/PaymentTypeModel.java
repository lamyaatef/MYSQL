package com.mobinil.sds.core.system.cam.payment.model;

public class PaymentTypeModel 
{
    private int paymentTypeId;
    private String paymentTypeName;
    public PaymentTypeModel()
    {
      
    }
	public PaymentTypeModel(int paymentTypeId, String paymentTypeName) {
		super();
		this.paymentTypeId = paymentTypeId;
		this.paymentTypeName = paymentTypeName;
	}
	public int getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

    
}
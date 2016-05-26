package com.mobinil.sds.core.system.dataMigration.model;
import java.io.Serializable;
import java.sql.ResultSet;

public class PaymentLevelModel implements Serializable{
	
	String paymentLevelId;
	String paymentLevelName;
	public String getPaymentLevelId() {
		return paymentLevelId;
	}
	public void setPaymentLevelId(String paymentLevelId) {
		this.paymentLevelId = paymentLevelId;
	}
	public String getPaymentLevelName() {
		return paymentLevelName;
	}
	public void setPaymentLevelName(String paymentLevelName) {
		this.paymentLevelName = paymentLevelName;
	}
	
	public static final String DCM_PAYMENT_LEVEL_ID = "DCM_PAYMENT_LEVEL_ID";
	public static final String DCM_PAYMENT_LEVEL_NAME = "DCM_PAYMENT_LEVEL_NAME";
	
	public PaymentLevelModel()
	{
		
	}
	public PaymentLevelModel(ResultSet res){
		try
		{
			paymentLevelId = res.getString(DCM_PAYMENT_LEVEL_ID);
			paymentLevelName = res.getString(DCM_PAYMENT_LEVEL_NAME);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

package com.mobinil.sds.core.system.payment.model;

public class PaymentTypeTempModel {
	private Integer PAYMENT_TYPE_METHOD_ID ;
	private String PAYMENT_TYPE_METHOD_NAME ;
	private String PAYMENT_TYPE_METHOD_DESC ;
	public Integer getPAYMENT_TYPE_METHOD_ID() {
		return PAYMENT_TYPE_METHOD_ID;
	}
	public void setPAYMENT_TYPE_METHOD_ID(Integer payment_type_method_id) {
		PAYMENT_TYPE_METHOD_ID = payment_type_method_id;
	}
	public String getPAYMENT_TYPE_METHOD_NAME() {
		return PAYMENT_TYPE_METHOD_NAME;
	}
	public void setPAYMENT_TYPE_METHOD_NAME(String payment_type_method_name) {
		PAYMENT_TYPE_METHOD_NAME = payment_type_method_name;
	}
	public String getPAYMENT_TYPE_METHOD_DESC() {
		return PAYMENT_TYPE_METHOD_DESC;
	}
	public void setPAYMENT_TYPE_METHOD_DESC(String payment_type_method_desc) {
		PAYMENT_TYPE_METHOD_DESC = payment_type_method_desc;
	}
}

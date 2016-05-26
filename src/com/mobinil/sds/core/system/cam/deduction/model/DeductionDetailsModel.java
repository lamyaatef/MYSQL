package com.mobinil.sds.core.system.cam.deduction.model;

import java.sql.Date;

public class DeductionDetailsModel {
private int rec_id, maker_id, action_id, deduction_id, 	payment_id, memo_id;
private Date action_date;
private double deleted_value;
private String memo_name, payment_name, maker_name, action_name;




public DeductionDetailsModel() {
	 
}
 
public DeductionDetailsModel(int rec_id, int maker_id, int action_id,
		int deduction_id,Date action_date, int payment_id, int memo_id, 
		double deleted_value, String memo_name, String payment_name,
		String maker_name, String action_name) {
	super();
	this.rec_id = rec_id;
	this.maker_id = maker_id;
	this.action_id = action_id;
	this.deduction_id = deduction_id;
	this.payment_id = payment_id;
	this.memo_id = memo_id;
	this.action_date = action_date;
	this.deleted_value = deleted_value;
	this.memo_name = memo_name;
	this.payment_name = payment_name;
	this.maker_name = maker_name;
	this.action_name = action_name;
}

public int getRec_id() {
	return rec_id;
}
public void setRec_id(int rec_id) {
	this.rec_id = rec_id;
}
public int getMaker_id() {
	return maker_id;
}
public void setMaker_id(int maker_id) {
	this.maker_id = maker_id;
}
public int getAction_id() {
	return action_id;
}
public void setAction_id(int action_id) {
	this.action_id = action_id;
}
public int getDeduction_id() {
	return deduction_id;
}
public void setDeduction_id(int deduction_id) {
	this.deduction_id = deduction_id;
}
public int getPayment_id() {
	return payment_id;
}
public void setPayment_id(int payment_id) {
	this.payment_id = payment_id;
}
public int getMemo_id() {
	return memo_id;
}
public void setMemo_id(int memo_id) {
	this.memo_id = memo_id;
}
public Date getAction_date() {
	return action_date;
}
public void setAction_date(Date action_date) {
	this.action_date = action_date;
}
public double getDeleted_value() {
	return deleted_value;
}
public void setDeleted_value(double deleted_value) {
	this.deleted_value = deleted_value;
}
public String getMemo_name() {
	return memo_name;
}
public void setMemo_name(String memo_name) {
	this.memo_name = memo_name;
}
public String getPayment_name() {
	return payment_name;
}
public void setPayment_name(String payment_name) {
	this.payment_name = payment_name;
}
public String getMaker_name() {
	return maker_name;
}
public void setMaker_name(String maker_name) {
	this.maker_name = maker_name;
}
public String getAction_name() {
	return action_name;
}
public void setAction_name(String action_name) {
	this.action_name = action_name;
}

}

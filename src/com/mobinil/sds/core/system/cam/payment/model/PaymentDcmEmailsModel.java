package com.mobinil.sds.core.system.cam.payment.model;

public class PaymentDcmEmailsModel 
{
  private String scmName;
  private String scmEmail;
  private int recordId;
public PaymentDcmEmailsModel(int recordId,String scmName, String scmEmail) {
	super();
	this.scmName = scmName;
	this.scmEmail = scmEmail;
	this.recordId = recordId;
}
public String getScmName() {
	return scmName;
}
public void setScmName(String scmName) {
	this.scmName = scmName;
}
public String getScmEmail() {
	return scmEmail;
}
public void setScmEmail(String scmEmail) {
	this.scmEmail = scmEmail;
}
public int getRecordId() {
	return recordId;
}
public void setRecordId(int recordId) {
	this.recordId = recordId;
}
    
    
}
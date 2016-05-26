package com.mobinil.sds.core.system.ifReportDelivery.model;

public class BatchModel {
private int batchId;
private String batchType;
private String batchDate;
private String dcmName;
private String dcmCode;
public int getBatchId() {
	return batchId;
}
public void setBatchId(int batchId) {
	this.batchId = batchId;
}
public String getBatchType() {
	return batchType;
}
public void setBatchType(String batchType) {
	this.batchType = batchType;
}
public String getBatchDate() {
	return batchDate;
}
public void setBatchDate(String batchDate) {
	this.batchDate = batchDate;
}
public String getDcmName() {
	return dcmName;
}
public void setDcmName(String dcmName) {
	this.dcmName = dcmName;
}
public String getDcmCode() {
	return dcmCode;
}
public void setDcmCode(String dcmCode) {
	this.dcmCode = dcmCode;
}
}

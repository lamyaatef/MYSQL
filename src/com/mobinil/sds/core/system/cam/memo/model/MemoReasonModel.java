package com.mobinil.sds.core.system.cam.memo.model;

public class MemoReasonModel {
private int reasonId;
private String reasonName;
private String reasonDesc;
public MemoReasonModel(int reasonId, String reasonName, String reasonDesc) {
	super();
	this.reasonId = reasonId;
	this.reasonName = reasonName;
	this.reasonDesc = reasonDesc;
}
public MemoReasonModel(int reasonId, String reasonName) {
	super();
	this.reasonId = reasonId;
	this.reasonName = reasonName;
}
public int getReasonId() {
	return reasonId;
}
public void setReasonId(int reasonId) {
	this.reasonId = reasonId;
}
public String getReasonName() {
	return reasonName;
}
public void setReasonName(String reasonName) {
	this.reasonName = reasonName;
}
public String getReasonDesc() {
	return reasonDesc;
}
public void setReasonDesc(String reasonDesc) {
	this.reasonDesc = reasonDesc;
}


}

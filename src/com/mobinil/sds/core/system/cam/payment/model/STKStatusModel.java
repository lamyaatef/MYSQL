package com.mobinil.sds.core.system.cam.payment.model;

public class STKStatusModel {
private int stkId;
private String stkStatus;
private String stkDesc;
public STKStatusModel(int stkId, String stkStatus, String stkDesc) {
	super();
	this.stkId = stkId;
	this.stkStatus = stkStatus;
	this.stkDesc = stkDesc;
}
public int getStkId() {
	return stkId;
}
public void setStkId(int stkId) {
	this.stkId = stkId;
}
public String getStkStatus() {
	return stkStatus;
}
public void setStkStatus(String stkStatus) {
	this.stkStatus = stkStatus;
}
public String getStkDesc() {
	return stkDesc;
}
public void setStkDesc(String stkDesc) {
	this.stkDesc = stkDesc;
}

}

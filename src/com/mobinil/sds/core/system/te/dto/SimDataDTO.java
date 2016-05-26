package com.mobinil.sds.core.system.te.dto;
import java.util.Date;
public class SimDataDTO 
{

 
private Integer simId;
private String simNo;
private Integer taskID;
private String activation;
private String productName;
private String dcmName;
private String dcmCode;
private String transactionTypeName;
private Integer lcsId;
private Integer lcsTransactionTypeId;
private Integer lcsProductId;
private String lcsIssueDate;
private Integer lcsStatusId;


public Integer getSimId() {
	return simId;
}

public void setSimId(Integer simId) {
	this.simId = simId;
}

public String getSimNo() {
	return simNo;
}

public void setSimNo(String simNo) {
	this.simNo = simNo;
}

public Integer getTaskID() {
	return taskID;
}

public void setTaskID(Integer taskID) {
	this.taskID = taskID;
}



public Integer getLcsId() {
	return lcsId;
}

public void setLcsId(Integer lcsId) {
	this.lcsId = lcsId;
}

public Integer getLcsTransactionTypeId() {
	return lcsTransactionTypeId;
}

public void setLcsTransactionTypeId(Integer lcsTransactionTypeId) {
	this.lcsTransactionTypeId = lcsTransactionTypeId;
}

public Integer getLcsProductId() {
	return lcsProductId;
}

public void setLcsProductId(Integer lcsProductId) {
	this.lcsProductId = lcsProductId;
}


public String getActivation() {
	return activation;
}

public void setActivation(String activation) {
	this.activation = activation;
}

public String getProductName() {
	return productName;
}

public void setProductName(String productName) {
	this.productName = productName;
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

public String getTransactionTypeName() {
	return transactionTypeName;
}

public void setTransactionTypeName(String transactionTypeName) {
	this.transactionTypeName = transactionTypeName;
}

public String getLcsIssueDate() {
	return lcsIssueDate;
}

public void setLcsIssueDate(String lcsIssueDate) {
	this.lcsIssueDate = lcsIssueDate;
}

public Integer getLcsStatusId() {
	return lcsStatusId;
}

public void setLcsStatusId(Integer lcsStatusId) {
	this.lcsStatusId = lcsStatusId;
}



  public SimDataDTO()
  {
  }
}
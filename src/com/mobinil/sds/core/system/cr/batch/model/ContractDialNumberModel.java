package com.mobinil.sds.core.system.cr.batch.model;
import java.io.Serializable;
import java.sql.ResultSet;

public class ContractDialNumberModel implements Serializable{
	
	String contractDialNo;
	String batchId;
	String batchDate;
	String dcmName;
	String contractStatusDate;
	String batchStatusTypeName;
	String batchTypeName;
	String contractStatusTypeId;
	String contractStatusTypeName;
	public String getContractDialNo() {
		return contractDialNo;
	}
	public void setContractDialNo(String contractDialNo) {
		this.contractDialNo = contractDialNo;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
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
	public String getContractStatusDate() {
		return contractStatusDate;
	}
	public void setContractStatusDate(String contractStatusDate) {
		this.contractStatusDate = contractStatusDate;
	}
	public String getBatchStatusTypeName() {
		return batchStatusTypeName;
	}
	public void setBatchStatusTypeName(String batchStatusTypeName) {
		this.batchStatusTypeName = batchStatusTypeName;
	}
	public String getBatchTypeName() {
		return batchTypeName;
	}
	public void setBatchTypeName(String batchTypeName) {
		this.batchTypeName = batchTypeName;
	}
	public String getContractStatusTypeId() {
		return contractStatusTypeId;
	}
	public void setContractStatusTypeId(String contractStatusTypeId) {
		this.contractStatusTypeId = contractStatusTypeId;
	}
	public String getContractStatusTypeName() {
		return contractStatusTypeName;
	}
	public void setContractStatusTypeName(String contractStatusTypeName) {
		this.contractStatusTypeName = contractStatusTypeName;
	}
	
	public static final String CONTRACT_DIAL_NO = "CONTRACT_DIAL_NO";
	public static final String BATCH_ID = "BATCH_ID";
	public static final String BATCH_DATE = "BATCH_DATE";
	public static final String DCM_NAME = "DCM_NAME";
	public static final String CONTRACT_STATUS_DATE = "CONTRACT_STATUS_DATE";
	public static final String BATCH_STATUS_TYPE_NAME = "BATCH_STATUS_TYPE_NAME";
	public static final String BATCH_TYPE_NAME = "BATCH_TYPE_NAME";
	public static final String CONTRACT_STATUS_TYPE_ID = "CONTRACT_STATUS_TYPE_ID";
	public static final String CONTRACT_STATUS_TYPE_NAME = "CONTRACT_STATUS_TYPE_NAME";

	
	public ContractDialNumberModel(){
		
	}
	
	public ContractDialNumberModel(ResultSet res)
	{
		try
		{
			contractDialNo = res.getString(CONTRACT_DIAL_NO);
			batchId = res.getString(BATCH_ID);
			batchDate = res.getString(BATCH_DATE);
			dcmName = res.getString(DCM_NAME);
			contractStatusDate = res.getString(CONTRACT_STATUS_DATE);
			batchStatusTypeName = res.getString(BATCH_STATUS_TYPE_NAME);
			batchTypeName = res.getString(BATCH_TYPE_NAME);
			contractStatusTypeId = res.getString(CONTRACT_STATUS_TYPE_ID);
			contractStatusTypeName = res.getString(CONTRACT_STATUS_TYPE_NAME);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
}

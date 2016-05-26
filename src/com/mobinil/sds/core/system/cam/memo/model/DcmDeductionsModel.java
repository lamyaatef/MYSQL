package com.mobinil.sds.core.system.cam.memo.model;

public class DcmDeductionsModel {
	private int deductionValue;
	private String reasonName;
	private String statusName;
	private String deductionDate;
	private String deductionType;
	private int remainingValue;
	
	public DcmDeductionsModel(int deductionValue,
			String reasonName, String statusName, String deductionDate,
			String deductionType, int remainingValue) {
		super();
		this.deductionValue = deductionValue;
		this.reasonName = reasonName;
		this.statusName = statusName;
		this.deductionDate = deductionDate;
		this.deductionType = deductionType;
		this.remainingValue = remainingValue;
	}

	public int getDeductionValue() {
		return deductionValue;
	}

	public void setDeductionValue(int deductionValue) {
		this.deductionValue = deductionValue;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getDeductionDate() {
		return deductionDate;
	}

	public void setDeductionDate(String deductionDate) {
		this.deductionDate = deductionDate;
	}

	public String getDeductionType() {
		return deductionType;
	}

	public void setDeductionType(String deductionType) {
		this.deductionType = deductionType;
	}

	public int getRemainingValue() {
		return remainingValue;
	}

	public void setRemainingValue(int remainingValue) {
		this.remainingValue = remainingValue;
	}
	
	

}

package com.mobinil.sds.core.system.cam.memo.model;

public class MemoMembersModel {
	int paymentId;
	int scmId;
	String paymetName;
	String dcmName;
	int scmCommissionValue;
	String startDate;
	String endDate;
	String frozenStatus;
	String addToAccountingModuleDate;
	int paymentTypeId;
	String paymentTypename;
	String paymentStatusName ; 

	
	
	private String dcmCode;

	public int getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getPaymentTypename() {
		return paymentTypename;
	}

	public void setPaymentTypename(String paymentTypename) {
		this.paymentTypename = paymentTypename;
	}

	public String getAddToAccountingModuleDate() {
		return addToAccountingModuleDate;
	}

	public void setAddToAccountingModuleDate(String addToAccountingModuleDate) {
		this.addToAccountingModuleDate = addToAccountingModuleDate;
	}

	public MemoMembersModel() {
	}

	public MemoMembersModel(int paymentId, String paymetName,int scmCommissionValue,
			String startDate, String endDate) {
		this.paymentId=paymentId;
		this.paymetName=paymetName;
		this.scmCommissionValue=scmCommissionValue;
		this.startDate=startDate;
		this.endDate=endDate;

	}

	public String getFrozenStatus() {
		return frozenStatus;
	}

	public void setFrozenStatus(String frozenStatus) {
		this.frozenStatus = frozenStatus;
	}

	public MemoMembersModel(int paymentId, String paymetName,
			int scmCommissionValue, String startDate, String endDate,
			String frozenStatus) {
		super();
		this.paymentId = paymentId;
		this.paymetName = paymetName;
		this.scmCommissionValue = scmCommissionValue;
		this.startDate = startDate;
		this.endDate = endDate;
		this.frozenStatus = frozenStatus;
	}

	public MemoMembersModel(int paymentId, int scmId, String paymetName,
			String dcmName, int scmCommissionValue) {
		this.paymentId = paymentId;
		this.scmId = scmId;
		this.paymetName = paymetName;
		this.dcmName = dcmName;
		this.scmCommissionValue = scmCommissionValue;
	}
	
	public MemoMembersModel(int paymentId, int scmId, String paymetName,
			String dcmName, int scmCommissionValue, String frozenStatus) {
		super();
		this.paymentId = paymentId;
		this.scmId = scmId;
		this.paymetName = paymetName;
		this.dcmName = dcmName;
		this.scmCommissionValue = scmCommissionValue;
		this.frozenStatus = frozenStatus;
	}

	public MemoMembersModel( int scmId,
			String dcmName, int scmCommissionValue) {
				this.scmId = scmId;
				this.dcmName = dcmName;
				this.scmCommissionValue = scmCommissionValue;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public void setDcmCode (String dcmCode)
	{
	    this.dcmCode = dcmCode;
	    
	}
	
	public String getDcmCode()
	{
	    return dcmCode;
	}
	public int getScmId() {
		return scmId;
	}

	public void setScmId(int scmId) {
		this.scmId = scmId;
	}

	public String getPaymetName() {
		return paymetName;
	}

	public void setPaymetName(String paymetName) {
		this.paymetName = paymetName;
	}

	public String getDcmName() {
		return dcmName;
	}

	public void setDcmName(String dcmName) {
		this.dcmName = dcmName;
	}

	public int getScmCommissionValue() {
		return scmCommissionValue;
	}

	public void setScmCommissionValue(int scmCommissionValue) {
		this.scmCommissionValue = scmCommissionValue;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	   
    public String getPaymentStatusName()
    {
        return paymentStatusName;       
    }
    
    public void setPaymentStatusName( String stateName)
    {
        this.paymentStatusName = stateName;
        if (paymentStatusName == null)
            paymentStatusName = "Not Exist in SCM status";
    }

}
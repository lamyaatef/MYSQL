package com.mobinil.sds.core.system.cam.payment.model;


public class PaymentScmStatusModel 
{
  
    private int scmId;
    
    private String dcmName;
    private String dcmCode;
    private String email;
    private String channel;
    private String phone;
    private String status;
    private String reason;
    private String stkNumber;
    private String stkNumberStatus;
    
    
    public String getStkNumber() {
		return stkNumber;
	}

	public void setStkNumber(String stkNumber) {
		this.stkNumber = stkNumber;
	}

	public String getStkNumberStatus() {
		return stkNumberStatus;
	}

	public void setStkNumberStatus(String stkNumberStatus) {
		this.stkNumberStatus = stkNumberStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public PaymentScmStatusModel(String dcmName, String dcmCode, String phone,
			String email, String channel, String status,
			String reason,String stkNumber,String stkStatus) {
		super();
		this.dcmName = dcmName;
		this.dcmCode = dcmCode;
		this.email = email;
		this.channel = channel;
		this.phone = phone;
		this.status = status;
		this.reason = reason;
		this.stkNumber=stkNumber;
		this.stkNumberStatus=stkStatus;
	}

	public PaymentScmStatusModel( int scmId, String dcmName , String dcmCode ) {
                this.scmId = scmId;
        
        this.dcmName = dcmName;
        this.dcmCode = dcmCode;
        
    }
	public PaymentScmStatusModel() {
		
	}

    public String getDcmCode() {
        return dcmCode;
    }

    public String getDcmName() {
        return dcmName;
    }


    public int getScmId() {
        return scmId;
    }



    public void setDcmCode(String dcmCode) {
        this.dcmCode = dcmCode;
    }

    public void setDcmName(String dcmName) {
        this.dcmName = dcmName;
    }


    public void setScmId(int scmId) {
        this.scmId = scmId;
    }

}
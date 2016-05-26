package com.mobinil.sds.core.system.cam.payment.model;
import java.util.Date;

public class PaymentImportModel 
{
  private int id;
  private Date timeStamp;
  private String FileID;
  private String scmCode;
  private String scmName;
  private String scmStatus;
  private String reason;
  private String stkNumber;
  private String stkNumberStatus;
  private String stkStatusComment;
  
  public String getStkStatusComment() {
	return stkStatusComment;
}

public void setStkStatusComment(String stkStatusComment) {
	this.stkStatusComment = stkStatusComment;
}

public PaymentImportModel(String scmCode, String scmStatus, String reason,
		String stkNumber, String stkNumberStatus) {
	super();
	this.scmCode = scmCode;
	this.scmStatus = scmStatus;
	this.reason = reason;
	this.stkNumber = stkNumber;
	this.stkNumberStatus = stkNumberStatus;
}

public String getStkNumberStatus() {
	return stkNumberStatus;
}

public void setStkNumberStatus(String stkNumberStatus) {
	this.stkNumberStatus = stkNumberStatus;
}

public String getStkNumber() {
	return stkNumber;
}

public void setStkNumber(String stkNumber) {
	this.stkNumber = stkNumber;
}

public PaymentImportModel()
  {
    
  }

    public PaymentImportModel(int id, String scmCode,String scmName, String scmStatus,
		String reason, String stkNumber, String stkNumberStatus) {
	super();
	this.id = id;
	this.scmCode = scmCode;
	this.scmName=scmName;
	this.scmStatus = scmStatus;
	this.reason = reason;
	this.stkNumber = stkNumber;
	this.stkNumberStatus = stkNumberStatus;
}

	public String getScmName() {
		return scmName;
	}

	public void setScmName(String scmName) {
		this.scmName = scmName;
	}

	public PaymentImportModel(String FileID, String scmCode, String scmStatus,String reason) {
        this.FileID = FileID;
        this.scmCode = scmCode;
        this.scmStatus = scmStatus;
    }

    public PaymentImportModel(int id, Date timeStamp, String FileID, String scmCode, String scmStatus) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.FileID = FileID;
        this.scmCode = scmCode;
        this.scmStatus = scmStatus;
    }
    

    public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setFileID(String FileID) {
        this.FileID = FileID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScmCode(String scmCode) {
        this.scmCode = scmCode;
    }

    public void setScmStatus(String scmStatus) {
        this.scmStatus = scmStatus;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getFileID() {
        return FileID;
    }

    public int getId() {
        return id;
    }

    public String getScmCode() {
        return scmCode;
    }

    public String getScmStatus() {
        return scmStatus;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
package com.mobinil.sds.core.system.cam.payment.model;
import java.sql.Date;

public class PaymentScreenManagementModel 
{
      private int startDate ;
    private int endDate;
    private String flag;
	public PaymentScreenManagementModel(int startDate, int endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public PaymentScreenManagementModel(String flag) {
		super();
		this.flag = flag;
	}
	public PaymentScreenManagementModel(int startDate, int endDate, String flag) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.flag = flag;
	}
	public int getStartDate() {
		return startDate;
	}
	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}
	public int getEndDate() {
		return endDate;
	}
	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

    
}
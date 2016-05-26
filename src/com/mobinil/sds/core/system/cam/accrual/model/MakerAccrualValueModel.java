package com.mobinil.sds.core.system.cam.accrual.model;

import java.sql.Date;

public class MakerAccrualValueModel 
{
private int value_id, accrual_id, reason_id, status_id, maker_id;
private String status_name, reason_name, accrual_name, maker_name;
private double accrual_value;
private Date value_date ; 
  public MakerAccrualValueModel()
  {
  }

  public MakerAccrualValueModel(int value_id, int accrual_id,
			double accrual_value, int reason_id, int status_id, int maker_id, Date value_date ,
			String status_name, String reason_name, String accrual_name, String maker_name) {
		super();
		this.value_id = value_id;
		this.accrual_id = accrual_id;
		this.reason_id = reason_id;
		this.status_id = status_id;
		this.status_name = status_name;
		this.reason_name = reason_name;
		this.accrual_name = accrual_name;
		this.accrual_value = accrual_value;
		this.value_date=value_date;
    this.maker_id = maker_id;
    this.maker_name = maker_name;
	}



	public Date getValue_date() {
	return value_date;
}

public void setValue_date(Date value_date) {
	this.value_date = value_date;
}

	public String getMaker_name() {
		return maker_name;
	}

	public void setMaker_name(String maker_name) {
		this.maker_name = maker_name;
	}
  public int getMaker_id()
  {return maker_id;}
  
  public void setMaker_id(int maker_id)
  {this.maker_id = maker_id;}
	public int getValue_id() {
		return value_id;
	}
	public void setValue_id(int value_id) {
		this.value_id = value_id;
	}
	public int getAccrual_id() {
		return accrual_id;
	}
	public void setAccrual_id(int accrual_id) {
		this.accrual_id = accrual_id;
	}
	public int getReason_id() {
		return reason_id;
	}
	public void setReason_id(int reason_id) {
		this.reason_id = reason_id;
	}
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public String getReason_name() {
		return reason_name;
	}
	public void setReason_name(String reason_name) {
		this.reason_name = reason_name;
	}
	public String getAccrual_name() {
		return accrual_name;
	}
	public void setAccrual_name(String accrual_name) {
		this.accrual_name = accrual_name;
	}
	public double getAccrual_value() {
		return accrual_value;
	}
	public void setAccrual_value(double accrual_value) {
		this.accrual_value = accrual_value;
	}
	

}
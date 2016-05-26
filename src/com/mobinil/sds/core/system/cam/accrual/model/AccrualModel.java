package com.mobinil.sds.core.system.cam.accrual.model;

public class AccrualModel 
{
 private int accrual_id, status_id, channel_id;
 private String accrual_name, accural_desc,status_name, channel_name;
 private double accrual_value;
 private String accrual_24_in_out_values_str;
  public AccrualModel()
  {
  }


  public AccrualModel(int accrual_id, String accrual_name,String accural_desc, int status_id,  double accrual_value
  ,  int channel_id, String status_name, String channel_name) {
		super();
		this.accrual_id = accrual_id;
		this.status_id = status_id;
		this.status_name = status_name;
		this.channel_id = channel_id;
		this.accrual_name = accrual_name;
		this.accural_desc = accural_desc;
		this.accrual_value = accrual_value;
    this.channel_name=channel_name;
	}
  public void setChannel_name(String channel_name){    this.channel_name=channel_name;}
  public String getChannel_name(){return channel_name;}
  public int getAccrual_id() {
		return accrual_id;
	}
	public void setAccrual_id(int accrual_id) {
		this.accrual_id = accrual_id;
	}
	public int getStatus_id() {
		return status_id;
	}
	
	public String getAccrual_24_in_out_values_str() {
		return accrual_24_in_out_values_str;
	}


	public void setAccrual_24_in_out_values_str(String accrual_24_in_out_values_str) {
		this.accrual_24_in_out_values_str = accrual_24_in_out_values_str;
	}


	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public int getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
	public String getAccrual_name() {
		return accrual_name;
	}
	public void setAccrual_name(String accrual_name) {
		this.accrual_name = accrual_name;
	}
	public String getAccural_desc() {
		return (accural_desc==null)?"":accural_desc;
	}
	public void setAccural_desc(String accural_desc) {
		this.accural_desc = accural_desc;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public double getAccrual_value() {
		return accrual_value;
	}
	public void setAccrual_value(double accrual_value) {
		this.accrual_value = accrual_value;
	}
	 
	
}
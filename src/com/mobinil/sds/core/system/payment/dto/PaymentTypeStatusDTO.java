package com.mobinil.sds.core.system.payment.dto;

public class PaymentTypeStatusDTO 
{
private Integer statusId;
private String statusName;

  public void setStatusId(Integer statusId)
  {
    this.statusId = statusId;
  }

  public Integer getStatusId()
  {
    return statusId;
  }

  public void setStatusName(String statusName)
  {
    this.statusName = statusName;
  }

  public String getStatusName()
  {
    return statusName;
  }
  public PaymentTypeStatusDTO()
  {
  }
}
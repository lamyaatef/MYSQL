package com.mobinil.sds.core.system.payment.dto;

public class CommissionConstrainDTO 
{
private Integer constrianId;
private String constrianName;
  public void setConstrianId(Integer constrianId)
  {
    this.constrianId = constrianId;
  }

  public Integer getConstrianId()
  {
    return constrianId;
  }

  public void setConstrianName(String constrianName)
  {
    this.constrianName = constrianName;
  }

  public String getConstrianName()
  {
    return constrianName;
  }
  public CommissionConstrainDTO()
  {
  }
}
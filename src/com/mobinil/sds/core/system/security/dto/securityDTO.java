package com.mobinil.sds.core.system.security.dto;

public class securityDTO 
{
private String ID;
private String SECUIRTY_NAME;
private Boolean SECURITY_STATUS;
private Integer intSECURITY_STATUS;
private Integer sECUIRTY_TYPE;
  public securityDTO()
  {
  }

  public void setID(String iD)
  {
    this.ID = iD;
  }

  public String getID()
  {
    return ID;
  }

  public void setSECUIRTY_NAME(String sECUIRTY_NAME)
  {
    this.SECUIRTY_NAME = sECUIRTY_NAME;
  }

  public String getSECUIRTY_NAME()
  {
    return SECUIRTY_NAME;
  }

  public void setSECURITY_STATUS(Boolean sECURITY_STATUS)
  {
    this.SECURITY_STATUS = sECURITY_STATUS;
  }

  public Boolean getSECURITY_STATUS()
  {
    return SECURITY_STATUS;
  }

  public void setIntSECURITY_STATUS(Integer sECURITY_STATUS)
  {
    this.intSECURITY_STATUS = sECURITY_STATUS;
  }

  public Integer getIntSECURITY_STATUS()
  {
    return intSECURITY_STATUS;
  }

  public void setSECUIRTY_TYPE(Integer sECUIRTY_TYPE)
  {
    this.sECUIRTY_TYPE = sECUIRTY_TYPE;
  }

  public Integer getSECUIRTY_TYPE()
  {
    return sECUIRTY_TYPE;
  }
}
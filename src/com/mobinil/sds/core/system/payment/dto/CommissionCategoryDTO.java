package com.mobinil.sds.core.system.payment.dto;

public class CommissionCategoryDTO 
{
private Integer commissionTypeCategoryId;
private String commissionTypeCategoryName;
private String commissionTypeCategoryMandatory;
private Integer paymentTypeId;
    public Integer getCommissionTypeCategoryId()
    {
        return commissionTypeCategoryId;
    }

    
    public void setCommissionTypeCategoryId(Integer commissionTypeCategoryId)
    {
        this.commissionTypeCategoryId = commissionTypeCategoryId;
    }


    public String getCommissionTypeCategoryName() 
    {
        return commissionTypeCategoryName;
    }

  
    public void setCommissionTypeCategoryName(String commissionTypeCategoryName) 
    {
        this.commissionTypeCategoryName = commissionTypeCategoryName;
    }
      public void setCommissionTypeCategoryMandatory(String commissionTypeCategoryMandatory)
  {
    this.commissionTypeCategoryMandatory = commissionTypeCategoryMandatory;
  }

  public String getCommissionTypeCategoryMandatory()
  {
    return commissionTypeCategoryMandatory;
  }

    public void setPaymentTypeId(Integer paymentTypeId)
  {
    this.paymentTypeId = paymentTypeId;
  }

  public Integer getPaymentTypeId()
  {
    return paymentTypeId;
  }
  public CommissionCategoryDTO()
  {
  }
}
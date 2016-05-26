package com.mobinil.sds.core.system.payment.dto;
import java.util.Vector;
public class PaymentTypeDTO 
{
private Integer typeId;
private String typeName;
private Integer typeStatusId;
private String typeStatusName;
private Integer methodTypeId;
private String methodTypeName;
private String paymentAccrualId;

public void setAccrualId(String accID)
{
	this.paymentAccrualId = accID;	
}
public String getPaymentAccrualId()
{
	return this.paymentAccrualId;
}

public String getMethodTypeName() {
	return methodTypeName;
}

public void setMethodTypeName(String methodTypeName) {
	this.methodTypeName = methodTypeName;
}
private Vector typeCategories;
public Integer getMethodTypeId() {
	return methodTypeId;
}

public void setMethodTypeId(Integer methodTypeId) {
	this.methodTypeId = methodTypeId;
}


  public void setTypeId(Integer typeId)
  {
    this.typeId = typeId;
  }

  public Integer getTypeId()
  {
    return typeId;
  }

  public void setTypeName(String typeName)
  {
    this.typeName = typeName;
  }

  public String getTypeName()
  {
    return typeName;
  }

  public void setTypeStatusId(Integer typeStatusId)
  {
    this.typeStatusId = typeStatusId;
  }

  public Integer getTypeStatusId()
  {
    return typeStatusId;
  }

  public void setTypeStatusName(String typeStatusName)
  {
    this.typeStatusName = typeStatusName;
  }

  public String getTypeStatusName()
  {
    return typeStatusName;
  }
  public Vector getTypeCategories() {
        return typeCategories;
    }
    
    public void setTypeCategories(Vector typeCategories) {
        this.typeCategories = typeCategories;
    }
  public PaymentTypeDTO()
  {
  }
}
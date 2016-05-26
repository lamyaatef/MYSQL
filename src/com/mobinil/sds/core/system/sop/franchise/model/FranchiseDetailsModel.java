package com.mobinil.sds.core.system.sop.franchise.model;
import java.util.ArrayList;
import java.io.Serializable;

public class FranchiseDetailsModel implements Serializable
{
  public FranchiseDetailsModel()
  {
  }

  private String franchiseCode;
  private String itemCode;
  private String lcsItemCode;
  private String lcsItemDesc;
  private String pgwQuantity;
  private String lcsQuantity;


  public String getFranchiseCode()
  {
    return franchiseCode;
  }
  public void setFranchiseCode(String newFranchiseCode)
  {
    franchiseCode = newFranchiseCode;
  }

  public String getItemCode()
  {
    return itemCode;
  }
  public void setItemCode(String newItemCode)
  {
    itemCode = newItemCode;
  }

  public String getLcsItemCode()
  {
    return lcsItemCode;
  }
  public void setLcsItemCode(String newLcsItemCode)
  {
    lcsItemCode = newLcsItemCode;
  }

  public String getLcsItemDesc()
  {
    return lcsItemDesc;
  }
  public void setLcsItemDesc(String newLcsItemDesc)
  {
    lcsItemDesc = newLcsItemDesc;
  }

  public String getPgwQuantity()
  {
    return pgwQuantity;
  }
  public void setPgwQuantity(String newPgwQuantity)
  {
    pgwQuantity = newPgwQuantity;
  }

  public String getLcsQuantity()
  {
    return lcsQuantity;
  }
  public void setLcsQuantity(String newLcsQuantity)
  {
    lcsQuantity = newLcsQuantity;
  }
}
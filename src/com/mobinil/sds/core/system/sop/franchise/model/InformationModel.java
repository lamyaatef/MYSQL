package com.mobinil.sds.core.system.sop.franchise.model;

public class InformationModel 
{
  public InformationModel()
  {
  }

  private int id;
  private String franchiseCode;
  private String itemCode;
  private String date;
  private int quantity;
  private String sourceItemCode;
  private String sourceItemName;
  private String sourceItemDesc;
  
  public int getId()
  {
    return id;
  }
  public void setId(int id)
  {
    this.id = id;
  }


  public String getFranchiseCode()
  {
    return franchiseCode;
  }
  public void setFranchiseCode(String code)
  {
    franchiseCode = code;
  }

  
  public String getItemCode()
  {
    return itemCode;
  }
  public void setItemCode(String code)
  {
    itemCode = code;
  }


  public String getDate()
  {
    return date;
  }
  public void setDate(String date)
  {
    this.date = date;
  }


  public int getQuantity()
  {
    return quantity;
  }
  public void setQuantity(int x)
  {
    quantity = x;
  }


  public String getSourceItemCode()
  {
    return sourceItemCode;
  }
  public void setSourceItemCode(String sourceCode)
  {
    sourceItemCode = sourceCode;
  }

  public String getSourceItemName()
  {
    return sourceItemName;
  }
  public void setSourceItemName(String sourceName)
  {
    sourceItemName = sourceName;
  }

  public String getSourceItemDesc()
  {
    return sourceItemDesc;
  }
  public void setSourceItemDesc(String sourceDesc)
  {
    sourceItemDesc = sourceDesc;
  }
}
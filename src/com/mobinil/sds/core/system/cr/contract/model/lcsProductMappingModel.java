package com.mobinil.sds.core.system.cr.contract.model;
import java.sql.*;
import java.io.*;

public class lcsProductMappingModel 
{
  String productName;
  String inventoryItemType;
  String productId;

  public static final String PRODUCT_NAME = "PRODUCT_NAME";
  public static final String INVENTORY_ITEM_TYPE = "INVENTORY_ITEM_TYPE";
  public static final String PRODUCT_ID = "PRODUCT_ID";
  
  public lcsProductMappingModel()
  {
  }

  public lcsProductMappingModel(ResultSet res)
  {
    try
    {
     productName = res.getString(PRODUCT_NAME); 
     inventoryItemType = res.getString(INVENTORY_ITEM_TYPE);
     productId = res.getString(PRODUCT_ID);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }
/////////////////////////////////////////////////////////////
  public String getProductName()
  {
    return productName;
  }
  public void setProductName(String newProductName)
  {
    productName = newProductName;
  }
//////////////////////////////////////////////////////////
public String getProductId()
  {
    return productId;
  }
  public void setProductId(String newProductId)
  {
    productId = newProductId;
  }
//////////////////////////////////////////////////////////
    public String getInventoryItemType()
  {
    return inventoryItemType;
  }
  public void setInventoryItemType(String newInventoryItemType)
  {
    inventoryItemType = newInventoryItemType;
  }
  
}
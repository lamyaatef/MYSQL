package com.mobinil.sds.core.system.cr.contract.model;
import java.sql.*;
import java.io.*;

public class ProductNameModel 
{
  String productId;
  String productName;

  public static final String PRODUCT_NAME = "PRODUCT_NAME";
  public static final String PRODUCT_ID = "PRODUCT_ID";
  
  public ProductNameModel()
  {
  }

   public ProductNameModel(ResultSet res)
  {
    try
    {
     productName = res.getString(PRODUCT_NAME); 
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
}
package com.mobinil.sds.core.system.sa.product.model;

import java.sql.*;
import java.io.*;


public class ProductModel  implements Serializable 
{
  public static final String PRODUCT_ID  = "PRODUCT_ID";
  public static final String PRODUCT_NAME = "PRODUCT_NAME";
  public static final String PRODUCT_DESC = "PRODUCT_DESC";
  public static final String PRODUCT_CATEGORY_ID = "PRODUCT_CATEGORY_ID";
  public static final String PRODUCT_CATEGORY_NAME = "PRODUCT_CATEGORY_NAME";
  String productId;
  String productName;
  String productDesc;
  String productCategoryId;
  String productCategoryName;

 

  public ProductModel()
  {
  }

  public ProductModel(ResultSet res )
  {
  try
  {    
     productId = res.getString(PRODUCT_ID);
     productName = res.getString (PRODUCT_NAME);
     productDesc = res.getString(PRODUCT_DESC);
     productCategoryId = res.getString(PRODUCT_CATEGORY_ID);
     productCategoryName = res.getString(PRODUCT_CATEGORY_NAME);
 
  }
  catch (Exception e)
  {
    e.printStackTrace();
  }
  }


  public String getProductId()
  {
    return productId;
  }

  public void setProductId(String newProductId)
  {
    productId = newProductId;
  }

  public String getProductName()
  {
    return productName;
  }

  public void setProductName(String newProductName)
  {
    productName = newProductName;
  }

  public String getProductDesc()
  {
    return productDesc;
  }

  public void setProductDesc(String newProductDesc)
  {
    productDesc = newProductDesc;
  }

  public String getProductCategoryId()
  {
    return productCategoryId;
  }

  public void setProductCategoryId(String newProductCategoryId)
  {
    productCategoryId = newProductCategoryId;
  }

  public String getProductCategoryName()
  {
    return productCategoryName;
  }

  public void setProductCategoryName(String newProductCategoryName)
  {
    productCategoryName = newProductCategoryName;
  }
}
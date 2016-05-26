package com.mobinil.sds.core.system.sop.schemas.model;
import java.sql.*;
import java.io.*;

public class ProductImportModel implements Serializable
{
  String rowNum;
  String productCode;
  String activeAmount;

  public static final String ROW_NUM = "ROW_NUM";
  public static final String PRODUCT_CODE = "PRODUCT_CODE";
  public static final String ACTIVE_AMOUNT = "ACTIVE_AMOUNT";
  
  public ProductImportModel()
  {
  }

  public ProductImportModel(ResultSet res)
  {
    try
    {
      rowNum = res.getString(ROW_NUM);
      productCode = res.getString(PRODUCT_CODE);
      activeAmount = res.getString(ACTIVE_AMOUNT);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }

  public String getRowNum()
  {
  return rowNum;
  }
  public void setRowNum(String newRowNum)
  {
  rowNum = newRowNum;
  }
  public String getProductCode()
  {
  return productCode;
  }
  public void setProductCode(String newProductCode)
  {
  productCode = newProductCode;
  }
  public String getActiveAmount()
  {
  return activeAmount;
  }
  public void setActiveAmount(String newActiveAmount)
  {
  activeAmount = newActiveAmount;
  }  
}
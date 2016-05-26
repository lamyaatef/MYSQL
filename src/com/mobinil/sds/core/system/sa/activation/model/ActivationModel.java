package com.mobinil.sds.core.system.sa.activation.model;
import java.sql.*;
import java.io.*;



public class ActivationModel implements Serializable 
{
  String dcmId;
  double amount;
  String rowID;
  String productID;
  

  public static final String DCM_ID_FIELD = "DCM_ID";
  public static final String ROW_ID_FIELD ="DCM_ACTIVATION_ID";
  public static final String AMOUNT_FIELD = "TOTAL_ACTIVATION";
  public static final String PRODUCT_ID_FIELD = "PRODUCT_ID";
  
    
  public ActivationModel()
  {
  }

  public ActivationModel(ResultSet res)
  {
    try
    {
    this.dcmId = res.getString(this.DCM_ID_FIELD);
    this.rowID = res.getString(this.ROW_ID_FIELD);
    this.amount = res.getDouble(this.AMOUNT_FIELD);
    this.productID = res.getString(this.PRODUCT_ID_FIELD);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public String getDcmId()
  {
    return dcmId;
  }

  public void setDcmId(String newDcmId)
  {
    dcmId = newDcmId;
  }

  public double getAmount()
  {
    return amount;
  }

  public void setAmount(double newAmount)
  {
    amount = newAmount;
  }

  public String getRowID()
  {
    return rowID;
  }

  public void setRowID(String newRowID)
  {
    rowID = newRowID;
  }

  public String getProductID()
  {
    return productID;
  }

  public void setProductID(String newProductID)
  {
    productID = newProductID;
  }
}
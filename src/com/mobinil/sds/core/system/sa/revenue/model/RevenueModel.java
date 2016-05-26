package com.mobinil.sds.core.system.sa.revenue.model;
import java.sql.*;
import java.io.*;

public class RevenueModel implements Serializable 
{
  String dcmId;
  double amount;
  String rowID;

  public static final String DCM_ID_FIELD = "DCM_ID";
  public static final String ROW_ID_FIELD ="REVENUE_ID";
  public static final String AMOUNT_FIELD = "AMOUNT";
    
  public RevenueModel()
  {
  }

  public RevenueModel(ResultSet res)
  {
    try
    {
    this.dcmId = res.getString(this.DCM_ID_FIELD);
    this.rowID = res.getString(this.ROW_ID_FIELD);
    this.amount = res.getDouble(this.AMOUNT_FIELD);
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
}
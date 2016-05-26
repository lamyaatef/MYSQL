package com.mobinil.sds.core.system.sa.scratch_card.model;

import java.sql.*;
import java.io.*;

public class ScratchCardModel implements Serializable 
{
  String dcmId;
  double amount;
  String rowID;

  public static final String DCM_ID_FIELD = "DCM_ID";
  public static final String ROW_ID_FIELD ="SCRATCH_CARDS_ID";
  public static final String AMOUNT_FIELD = "AMOUNT";
    
  public ScratchCardModel()
  {
  }

  public ScratchCardModel(ResultSet res)
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
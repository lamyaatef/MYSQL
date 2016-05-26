package com.mobinil.sds.core.system.dcm.chain.model;
import java.sql.*;
import java.io.*;

public class chainPaymentLevelModel implements Serializable
{
  String dcmPaymentLevelId;
  String dcmPaymentLevelName;

  public static final String DCM_PAYMENT_LEVEL_ID = "DCM_PAYMENT_LEVEL_ID";
  public static final String DCM_PAYMENT_LEVEL_NAME = "DCM_PAYMENT_LEVEL_NAME";
  
  public chainPaymentLevelModel()
  {
  }

  public chainPaymentLevelModel(ResultSet res)
  {
    try
    {
      dcmPaymentLevelId = res.getString(DCM_PAYMENT_LEVEL_ID);
      dcmPaymentLevelName = res.getString(DCM_PAYMENT_LEVEL_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  ///////////////////////////////////////////////////////////////////////
  public String getDcmPaymentLevelId()
  {
    return dcmPaymentLevelId;
  }
  public void setDcmPaymentLevelId(String newDcmPaymentLevelId)
  {
    dcmPaymentLevelId = newDcmPaymentLevelId;
  }
  ///////////////////////////////////////////////////////////////////
  public String getDcmPaymentLevelName()
  {
    return dcmPaymentLevelName;
  }
  public void setDcmPaymentLevelName(String newDcmPaymentLevelName)
  {
    dcmPaymentLevelName = newDcmPaymentLevelName;
  }
}
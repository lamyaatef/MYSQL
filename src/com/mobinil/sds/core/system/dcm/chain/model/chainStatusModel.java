package com.mobinil.sds.core.system.dcm.chain.model;
import java.sql.*;
import java.io.*;

public class chainStatusModel implements Serializable
{
   String dcmStatusId;
   String dcmStatusName;

   public static final String DCM_STATUS_ID = "DCM_STATUS_ID";
   public static final String DCM_STATUS_NAME = "DCM_STATUS_NAME";
   
  public chainStatusModel()
  {
  }
  
  public chainStatusModel(ResultSet res)
  {
    try
    {
      dcmStatusId = res.getString(DCM_STATUS_ID);
      dcmStatusName = res.getString(DCM_STATUS_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }
  ///////////////////////////////////////////////////
  public String getDcmStatusId()
  {
    return dcmStatusId;
  }
  public void setDcmStatusId(String newDcmStatusId)
  {
    dcmStatusId = newDcmStatusId;
  }
  ////////////////////////////////////////////////////
  public String getDcmStatusName()
  {
    return dcmStatusName;
  }
  public void setDcmStatusName(String newDcmStatusName)
  {
    dcmStatusName = newDcmStatusName;
  }
}
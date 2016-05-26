package com.mobinil.sds.core.system.sfr.sheets.model;
import java.sql.*;
import java.io.*;

public class SIMModel implements Serializable
{
  String simId;
  String simSerial;
  String sheetId;
  Date activationDate;
  String contractType;
  String simStatusTypeId;
  String simStatusTypeName;
  String simInfoSource;
  
  public static final String SIM_ID = "SIM_ID";
  public static final String SIM_SERIAL = "SIM_SERIAL";
  public static final String SHEET_ID = "SHEET_ID";
  public static final String SIM_STATUS_TYPE_ID = "SIM_STATUS_TYPE_ID";
  public static final String SIM_STATUS_TYPE_NAME = "SIM_STATUS_TYPE_NAME";
  public static final String SIM_INFO_SOURCE = "INFO_SOURCE";
  public SIMModel()
  {
  }

  public SIMModel(ResultSet res)
  {
    try
    {
      simId = res.getString(SIM_ID);
      simSerial = res.getString(SIM_SERIAL);
      sheetId = res.getString(SHEET_ID);
      simStatusTypeId = res.getString(SIM_STATUS_TYPE_ID);
      simStatusTypeName = res.getString(SIM_STATUS_TYPE_NAME); 
      simInfoSource=res.getString(SIM_INFO_SOURCE);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
  }

  public String getSimStatusTypeId()
  {
  return simStatusTypeId;
  }
  public void setSimStatusTypeId(String newSimStatusTypeId)
  {
  simStatusTypeId= newSimStatusTypeId;
  }
  
  
  public String getSimStatusTypeName()
  {
  return simStatusTypeName;
  }
  public void setSimStatusTypeName(String newSimStatusTypeName)
  {
  simStatusTypeName= newSimStatusTypeName;
  }
  
  
  public String getSimSerial()
  {
  return simSerial;
  }
  public void setSimSerial(String newSimSerial)
  {
  simSerial= newSimSerial;
  }

  public String getSimId()
  {
  return simId;
  }
  public void setSimId(String newSimId)
  {
  simId= newSimId;
  }
  
  public String getSheetId()
  {
  return sheetId;
  }
  public void setSheetId(String newSheetId)
  {
  sheetId= newSheetId;
  }

    /**
     * @return the simInfoSource
     */
    public String getSimInfoSource() {
        return simInfoSource;
    }

    /**
     * @param simInfoSource the simInfoSource to set
     */
    public void setSimInfoSource(String simInfoSource) {
        this.simInfoSource = simInfoSource;
    }
  
  
  

 

}
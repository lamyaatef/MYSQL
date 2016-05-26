package com.mobinil.sds.core.system.cr.sheet.model;
import java.io.Serializable;
import java.util.*;
import java.sql.*;

/*
 * Sheet DCM Model it encapsulate the following information of the sheet type in it
 * 
 * Sheet DCM Sheet ID;
 * Sheet Sheet Serial ID;
 * Sheet DCM ID;
 *
 * 1- Sheet DCM model constructoer that take a result set as input 
 * 2- get DCM Sheet ID 
 * 3- set DCM Sheet ID 
 * 4- get Sheet Serial ID  
 * 5- set Sheet Serial ID
 * 6- get DCM ID
 * 7- set DCM ID
 * 
 */
public class SheetDCMModel implements Serializable 
{

  private String sheetDCMId;
  private String sheetSerialID;
  private String DCMID;

  public static final String DCM_SHEET_ID = "DCM_SHEET_ID";
  public static final String SHEET_SERIAL_ID  = "SHEET_SERIAL_ID";
  public static final String DCM_ID = "DCM_ID";

  public SheetDCMModel()
  {
  }
  /*
   * 1- Sheet type model constructoer that take a result set as input
   */  
  public SheetDCMModel(ResultSet res)
  {
    try 
    {
      this.sheetDCMId = res.getString(DCM_SHEET_ID);
      this.sheetSerialID = res.getString(SHEET_SERIAL_ID);
      this.DCMID = res.getString(DCM_ID); 
    }
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
  }

  /*
   * 2- get DCM Sheet ID 
   */  
  public String getSheetDCMId()
  {
    return sheetDCMId;
  }

  /*
   * 3- set DCM Sheet ID 
   */  
  public void setSheetDCMId(String newSheetDCMId)
  {
    sheetDCMId = newSheetDCMId;
  }

  /*
   * 4- get Sheet Serial ID  
   */  
  public String getSheetSerialID()
  {
    return sheetSerialID;
  }

  /*
   * 5- set Sheet Serial ID
   */  
  public void setSheetSerialID(String newSheetSerialID)
  {
    sheetSerialID = newSheetSerialID;
  }

  /*
   * 6- get DCM ID
   */  
  public String getDCMID()
  {
    return DCMID;
  }

  /*
   * 7- set DCM ID
   */  
  public void setDCMID(String newDCMID)
  {
    DCMID = newDCMID;
  }
}
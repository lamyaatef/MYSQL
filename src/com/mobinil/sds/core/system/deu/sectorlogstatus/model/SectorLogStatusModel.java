package com.mobinil.sds.core.system.deu.sectorlogstatus.model;

import java.sql.ResultSet;
import java.io.Serializable;

public class SectorLogStatusModel implements Serializable
{
  private static final String STATUS_ID="STATUS_ID";
  private static final String STATUS_TEXT="STATUS_TEXT";
  private String statusID;
  private String statusText;
  
  public SectorLogStatusModel(ResultSet res)
  {
  try
  {
    this.statusID = res.getString(STATUS_ID);
    this.statusText = res.getString(STATUS_TEXT);                               
  }
  catch(Exception  e)
  {
    e.printStackTrace();
  }
  }

  public String getStatusID() {
    return statusID;
  }

  public void setStatusID(String newStatusID) {
    statusID = newStatusID;
  }

  public String getStatusText() {
    return statusText;
  }

  public void setStatusText(String newStatusText) {
    statusText = newStatusText;
  }
}


package com.mobinil.sds.core.system.deu.sector.model;

import java.sql.ResultSet;
import java.io.Serializable;

public class SectorModel implements Serializable
{
  private static final String SECTOR_ID="SECTOR_ID";
  private static final String OUTPUT_FILE_ID="OUTPUT_FILE_ID";
  private static final String SOURCE_ID="SOURCE_ID";
  private static final String SEPARATOR="SEPARATOR";
  private static final String SECTOR_ORDER="SECTOR_ORDER";
  private String outputFileID;
  private String sectorOrder;
  private String separator;
  private String sourceID;
  private String sectorID;

  public SectorModel(ResultSet res)
  {
    try
    {
      this.sectorID=res.getString(SECTOR_ID);
      this.outputFileID=res.getString(OUTPUT_FILE_ID);
      this.sectorOrder=res.getString(SECTOR_ORDER);
      this.separator=res.getString(SEPARATOR);
      this.sourceID=res.getString(SOURCE_ID);
    }
    catch(Exception  e)
    {
      e.printStackTrace();
    }
  }

  public String getOutputFileID() {
    return outputFileID;
  }

  public void setOutputFileID(String newOutputFileID) {
    outputFileID = newOutputFileID;
  }

  public String getSectorOrder() {
    return sectorOrder;
  }

  public void setSectorOrder(String newSectorOrder) {
    sectorOrder = newSectorOrder;
  }

  public String getSeparator() {
    return separator;
  }

  public void setSeparator(String newSeparator) {
    separator = newSeparator;
  }

  public String getSourceID() {
    return sourceID;
  }

  public void setSourceID(String newSourceID) {
    sourceID = newSourceID;
  }

  public String getSectorID() {
    return sectorID;
  }

  public void setSectorID(String newSectorID) {
    sectorID = newSectorID;
  }


}


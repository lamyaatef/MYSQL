package com.mobinil.sds.core.system.deu.source.model;

import java.sql.ResultSet;
import java.io.Serializable;

public class SourceModel implements Serializable
{
  private static final String SOURCE_ID="SOURCE_ID";
  private static final String SOURCE_NAME="NAME";
  private static final String SOURCE_SQL="SOURCE_SQL";
  private static final String SOURCE_CONNECTION_ID="CONNECTION";
  private static final String SOURCE_DATAVIEW_ID="DATA_VIEW_ID";
  private static final String SOURCE_DESCRIPTION="DESCRIPTION";

  private String sourceID;
  private String sourceName;
  private String sourceSQL;
  private String sourceConnection;
  private String sourceDataview;
  private String sourceDescription;

  public SourceModel(ResultSet res)
  {
  try
  {
    this.sourceID = res.getString(SOURCE_ID);
    this.sourceName = res.getString(SOURCE_NAME);
    this.sourceSQL = res.getString(SOURCE_SQL);
    this.sourceConnection = res.getString(SOURCE_CONNECTION_ID);
    this.sourceDataview = res.getString(SOURCE_DATAVIEW_ID);
    this.sourceDescription = res.getString(SOURCE_DESCRIPTION);                              
  }
  catch(Exception  e)
  {
    e.printStackTrace();
  }
  }

  public String getSourceConnection() {
    return sourceConnection;
  }

  public void setSourceConnection(String newSourceConnection) {
    sourceConnection = newSourceConnection;
  }

  public String getSourceDataview() {
    return sourceDataview;
  }

  public void setSourceDataview(String newSourceDataview) {
    sourceDataview = newSourceDataview;
  }

  public String getSourceDescription() {
    return sourceDescription;
  }

  public void setSourceDescription(String newSourceDescription) {
    sourceDescription = newSourceDescription;
  }

  public String getSourceID() {
    return sourceID;
  }

  public void setSourceID(String newSourceID) {
    sourceID = newSourceID;
  }

  public String getSourceName() {
    return sourceName;
  }

  public void setSourceName(String newSourceName) {
    sourceName = newSourceName;
  }

  public String getSourceSQL() {
    return sourceSQL;
  }

  public void setSourceSQL(String newSourceSQL) {
    sourceSQL = newSourceSQL;
  }
}


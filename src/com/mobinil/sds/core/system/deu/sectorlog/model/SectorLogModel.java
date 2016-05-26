package com.mobinil.sds.core.system.deu.sectorlog.model;

import java.sql.ResultSet;
import java.io.Serializable;

public class SectorLogModel implements Serializable{

  private static final String SECTOR_WORK_LOG_ID="SECTOR_WORK_LOG_ID";
  private static final String TIME_INITIAL="TIME_INITIAL";
  private static final String CONNECTION_NAME="CONNECTION_NAME";
  private static final String SOURCE_NAME="SOURCE_NAME";
  private static final String OUTPUT_FILE_NAME="OUTPUT_FILE_NAME";
  private static final String TASK_NAME="TASK_NAME";
  private static final String SECTOR_STATUS="SECTOR_STATUS";
  private static final String TIME_FINAL="TIME_FINAL";
  private static final String ROW_COUNT="ROW_COUNT";
  private static final String ERROR="ERROR";
    private static final String SECTOR_ORDER="SECTOR_ORDER";
    private static final String TASK_WORK_LOG_ID="TASK_WORK_LOG_ID";
  
  private String connectionName;
  private String error;
  private String fileName;
  private String rowCount;
  private String status;
  private String logID;
  private String sourceName;
  private String taskName;
  private String timeFinal;
  private String timeInitial;
  private String sectorOrder;
  private String taskWorkLogId;
  

  
    public SectorLogModel(ResultSet res)
  {
    try
    {
      this.logID = res.getString(SECTOR_WORK_LOG_ID);
      this.timeInitial = res.getString(TIME_INITIAL);
      this.timeFinal = res.getString(TIME_FINAL);
      this.taskName = res.getString(TASK_NAME);
      this.fileName = res.getString(OUTPUT_FILE_NAME);
      this.sourceName = res.getString(SOURCE_NAME);
      this.connectionName = res.getString(CONNECTION_NAME);
      this.sectorOrder=res.getString(SECTOR_ORDER);
      this.rowCount = res.getString(ROW_COUNT);
      this.status = res.getString(SECTOR_STATUS);
      this.error = res.getString(ERROR);   
      this.taskWorkLogId = res.getString(TASK_WORK_LOG_ID);
    }
    catch(Exception  e)
    {
      e.printStackTrace();
    }
  }

  public String getConnectionName() {
    return connectionName;
  }

  public void setConnectionName(String newConnectionName) {
    connectionName = newConnectionName;
  }

  public String getError() {
    return error;
  }

  public void setError(String newError) {
    error = newError;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String newFileName) {
    fileName = newFileName;
  }

  public String getRowCount() {
    return rowCount;
  }

  public void setRowCount(String newRowCount) {
    rowCount = newRowCount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String newStatus) {
    status = newStatus;
  }

  public String getLogID() {
    return logID;
  }

  public void setLogID(String newLogID) {
    logID = newLogID;
  }

  public String getSourceName() {
    return sourceName;
  }

  public void setSourceName(String newSourceName) {
    sourceName = newSourceName;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String newTaskName) {
    taskName = newTaskName;
  }

  public String getTimeFinal() {
    return timeFinal;
  }

  public void setTimeFinal(String newTimeFinal) {
    timeFinal = newTimeFinal;
  }

  public String getTimeInitial() {
    return timeInitial;
  }

  public void setTimeInitial(String newTimeInitial) {
    timeInitial = newTimeInitial;
  }

  public String getSectorOrder() {
    return sectorOrder;
  }

  public void setSectorOrder(String newSectorOrder) {
    sectorOrder = newSectorOrder;
  }

  public String getTaskWorkLogId()
  {
    return taskWorkLogId;
  }

  public void setTaskWorkLogId(String newTaskWorkLogId)
  {
    taskWorkLogId = newTaskWorkLogId;
  }



}
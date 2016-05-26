package com.mobinil.sds.core.system.deu.tasklog.model;

import java.sql.ResultSet;
import java.io.Serializable;

public class TaskLogModel implements Serializable
{
  private static final String TASK_WORK_LOG_ID="TASK_WORK_LOG_ID";
  private static final String TIME_INITIAL="TIME_INITIAL";
  private static final String OUTPUT_FILE_NAME="OUTPUT_FILE_NAME";
  private static final String TASK_NAME="TASK_NAME";
  private static final String TIME_FINAL="TIME_FINAL";
  private static final String SECTOR_COUNT="SECTOR_COUNT";
  private static final String ROW_COUNT="ROW_COUNT";
  private static final String STATUS_ID="STATUS_ID";
  private static final String STATUS="STATUS";
  private static final String ERROR="ERROR";
  
  private String error;
  private String fileName;
  private String rowCount;
  private String sectorCount;
  private String status;
  private String statusID;
  private String taskName;
  private String logID;
  private String timeInitial;
  private String timeFinal;

  public TaskLogModel(ResultSet res)
  {
    try
    {
      this.logID = res.getString(TASK_WORK_LOG_ID);
      this.timeInitial = res.getString(TIME_INITIAL);
      this.fileName = res.getString(OUTPUT_FILE_NAME);
      this.taskName = res.getString(TASK_NAME);
      this.timeFinal = res.getString(TIME_FINAL);
      this.sectorCount = res.getString(SECTOR_COUNT);
      this.rowCount = res.getString(ROW_COUNT);
      this.statusID = res.getString(STATUS_ID);
      this.status = res.getString(STATUS);
      this.error = res.getString(ERROR);                               
    }
    catch(Exception  e)
    {
      e.printStackTrace();
    }
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

  public String getSectorCount() {
    return sectorCount;
  }

  public void setSectorCount(String newSectorCount) {
    sectorCount = newSectorCount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String newStatus) {
    status = newStatus;
  }

  public String getStatusID() {
    return statusID;
  }

  public void setStatusID(String newStatusID) {
    statusID = newStatusID;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String newTaskName) {
    taskName = newTaskName;
  }

  public String getLogID() {
    return logID;
  }

  public void setLogID(String newLogID) {
    logID = newLogID;
  }

  public String getTimeInitial() {
    return timeInitial;
  }

  public void setTimeInitial(String newTimeInitial) {
    timeInitial = newTimeInitial;
  }

  public String getTimeFinal() {
    return timeFinal;
  }

  public void setTimeFinal(String newTimeFinal) {
    timeFinal = newTimeFinal;
  }
}


package com.mobinil.sds.core.system.deu.taskstatus.model;

import java.sql.ResultSet;
import java.io.Serializable;

/*
 * Task status model
 * this class represent task status model
 */

public class TaskStatusModel implements Serializable
{
//column names are hard coded 
  private static final String STATUS_ID="STATUS_ID";
  private static final String STATUS_TYPE="STATUS_TYPE";

  /*
   * the two values of the task status that are stored in the database are stored in the following two variables
   */
  private String taskStatusID;
  private String taskStatusType;


  /*
   * constructor that take result set as input
   * and extract information needed to set the values of the objects fields
   */
  public TaskStatusModel(ResultSet res)
  {
  try
  {
    this.taskStatusID = res.getString(STATUS_ID);
    this.taskStatusType = res.getString(STATUS_TYPE);                               
  }
  catch(Exception  e)
  {
    e.printStackTrace();
  }
  }

  public String getTaskStatusID() {
    return taskStatusID;
  }

  public void setTaskStatusID(String newTaskStatusID) {
    taskStatusID = newTaskStatusID;
  }

  public String getTaskStatusType() {
    return taskStatusType;
  }

  public void setTaskStatusType(String newTaskStatusType) {
    taskStatusType = newTaskStatusType;
  }
}


package com.mobinil.sds.core.system.deu.tasklogstatus.model;
import java.sql.ResultSet;
import java.io.Serializable;

/*
 * this is task log status model that hold status id and status name 
 */
public class TaskLogStatusModel implements Serializable
{

//column names are hard coded in the database
  private static final String STATUS_ID="STATUS_ID";
  private static final String STATUS_TEXT="STATUS_TEXT";

  private String statusID;
  private String statusText;

  /*
   * constructor that take result set as input and extract the information from it to the fields values
   */
  public TaskLogStatusModel(ResultSet res)
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


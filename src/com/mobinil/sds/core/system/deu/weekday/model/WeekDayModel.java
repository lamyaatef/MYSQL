package com.mobinil.sds.core.system.deu.weekday.model;

import java.sql.ResultSet;
import java.io.Serializable;

/*
 * This is the week day model which get for each day in the week the  mapping number for that that is used in the system
 */

public class WeekDayModel implements Serializable
{
//column names are hard coded static here 
  private static final String DAY_ID="DAY_ID";
  private static final String DAY_NAME="DAY_NAME";
  private static final String WORK_DAY="WORK_DAY";

  
  private String dayID;
  private String dayName;
  private String workDay;


/*
 * constructor that take a result set and extract the values of the object fields form it
 */
  public WeekDayModel(ResultSet res)
  {
    try
    {
      this.dayID = res.getString(DAY_ID);
      this.dayName = res.getString(DAY_NAME);                   
    }
    catch(Exception  e)
    {
      e.printStackTrace();
    }
  }

  /**
   *  this function used to get the day ID
     *
     *  @return String containing the day ID
     */

  public String getDayID() {
    return dayID;
  }

  /**
    * this function used to set the day id
     *  @param string new day id
     *
     *  @return void
     */

  public void setDayID(String newDayID) {
    dayID = newDayID;
  }

  /**
     * this function is used to get the day name 
     *
     *
     *  @return String Day name
     */

  public String getDayName() {
    return dayName;
  }

  /**
     *  This function is used to set the day name
     *
     *  @param dayname String
     *
     *  @return void
     */

  public void setDayName(String newDayName) {
    dayName = newDayName;
  }

 /*
  * this function is to return the work day as string
  */

  public String getWorkDay() {
    return workDay;
  }

/*
 * this function is to set the work day with a string 
 */
  public void setWorkDay(String newWorkDay) {
    workDay = newWorkDay;
  }
}


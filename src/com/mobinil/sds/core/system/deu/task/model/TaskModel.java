package com.mobinil.sds.core.system.deu.task.model;

import java.sql.ResultSet;
import java.io.Serializable;

public class TaskModel implements Serializable
{
  private static final String TASK_ID="TASK_ID";
  private static final String TASK_NAME="NAME";
  private static final String TASK_OUTPUT_FILE="OUTPUT_FILE";
  private static final String TASK_FREQUENCY="FREQUENCY";
  private static final String TASK_RATE="RATE";
  private static final String TASK_DAY_OF_WEEK="DAY_OF_WEEK";
  private static final String TASK_DAY_OF_MONTH="DAY_OF_MONTH";
  private static final String TASK_MONTH="MONTH";
  private static final String TASK_START_DATE="START_DATE";
  private static final String TASK_END_DATE="END_DATE";
  private static final String TASK_MAX_OCCURRENCES="MAX_OCCURRENCES";
  private static final String TASK_OCCURRENCES="OCCURRENCES";
  private static final String TASK_STATUS="TASK_STATUS";
  private static final String TASK_RUN_HOUR="RUN_HOUR";
  private static final String TASK_RUN_MIN="RUN_MIN";
  private static final String TASK_DESCRIPTION="DESCRIPTION";
  private static final String TASK_LAST_RUN="LAST_RUN";
  private static final String TASK_NEXT_RUN_DATE="NEXT_RUN_DATE";
  
  private String taskID;
  private String name;
  private String outputFile;
  private String frequency;
  private String startDate;
  private String endDate;
  private String maxOccurrences;
  private String occurrences;
  private String status;
  private String runHour;
  private String description;
  private String nextRunDate;
  private String rate;
  private String weekDay;
  private String monthDay;
  private String month;
  private String lastRun;
  private String runMin;

  public TaskModel(ResultSet res)
  {
  try
  {
    this.taskID = res.getString(TASK_ID);
    this.name = res.getString(TASK_NAME);
    this.outputFile = res.getString(TASK_OUTPUT_FILE);
    this.frequency = res.getString(TASK_FREQUENCY);
    this.rate= res.getString(TASK_RATE);
    this.monthDay= res.getString(TASK_DAY_OF_MONTH);
    this.weekDay= res.getString(TASK_DAY_OF_WEEK);
    this.month= res.getString(TASK_MONTH);
    this.lastRun= res.getString(TASK_LAST_RUN);
    this.startDate = res.getString(TASK_START_DATE);
    this.endDate = res.getString(TASK_END_DATE);
    this.maxOccurrences = res.getString(TASK_MAX_OCCURRENCES);
    this.occurrences = res.getString(TASK_OCCURRENCES);
    this.status = res.getString(TASK_STATUS);
    this.runHour = res.getString(TASK_RUN_HOUR);
    this.runMin = res.getString(TASK_RUN_MIN);
    this.description = res.getString(TASK_DESCRIPTION);
    this.nextRunDate = res.getString(TASK_NEXT_RUN_DATE);
  }
  catch(Exception  e)
  {
    e.printStackTrace();
  }
  }

  public String getTaskID() {
    return taskID;
  }

  public void setTaskID(String newTaskID) {
    taskID = newTaskID;
  }

  public String getName() {
    return name;
  }

  public void setName(String newName) {
    name = newName;
  }

  public String getOutputFile() {
    return outputFile;
  }

  public void setOutputFile(String newOutputFile) {
    outputFile = newOutputFile;
  }

  public String getFrequency() {
    return frequency;
  }

  public void setFrequency(String newFrequency) {
    frequency = newFrequency;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String newStartDate) {
    startDate = newStartDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String newEndDate) {
    endDate = newEndDate;
  }

  public String getMaxOccurrences() {
    return maxOccurrences;
  }

  public void setMaxOccurrences(String newMaxOccurrences) {
    maxOccurrences = newMaxOccurrences;
  }

  public String getOccurrences() {
    return occurrences;
  }

  public void setOccurrences(String newOccurrences) {
    occurrences = newOccurrences;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String newStatus) {
    status = newStatus;
  }

  public String getRunHour() {
    return runHour;
  }

  public void setRunHour(String newRunHour) {
    runHour = newRunHour;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String newDescription) {
    description = newDescription;
  }

  public String getNextRunDate() {
    return nextRunDate;
  }

  public void setNextRunDate(String newNextRunDate) {
    nextRunDate = newNextRunDate;
  }

  public String getRate() {
    return rate;
  }

  public void setRate(String newRate) {
    rate = newRate;
  }

  public String getWeekDay() {
    return weekDay;
  }

  public void setWeekDay(String newWeekDay) {
    weekDay = newWeekDay;
  }

  public String getMonthDay() {
    return monthDay;
  }

  public void setMonthDay(String newMonthDay) {
    monthDay = newMonthDay;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String newMonth) {
    month = newMonth;
  }

  public String getLastRun() {
    return lastRun;
  }

  public void setLastRun(String newLastRun) {
    lastRun = newLastRun;
  }

  public String getEndOption() {
    if(this.getEndDate()!=null)
      return "3";//end by date
    else if(this.getMaxOccurrences()!=null)
      return "2";//end by occurrences
    else 
      return "1";
  }

  public String getRunMin() {
    return runMin;
  }

  public void setRunMin(String newRunMin) {
    runMin = newRunMin;
  }

}



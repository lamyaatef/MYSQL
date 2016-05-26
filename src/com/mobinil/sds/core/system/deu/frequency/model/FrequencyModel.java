package com.mobinil.sds.core.system.deu.frequency.model;

import java.sql.ResultSet;
import java.io.Serializable;
/*
 * frequency model class 
 */
public class FrequencyModel implements Serializable
{
  private static final String FREQUENCY_ID="FREQUENCY_ID";
  private static final String FREQUENCY_TYPE="FREQUENCY_TYPE";
  
  private String frequencyID;
  private String frequencyType;

  /*
   * constructor that take a result set and set the instance variables with their values
   */
  public FrequencyModel(ResultSet res)
  {
  try
  {
    this.frequencyID = res.getString(FREQUENCY_ID);
    this.frequencyType = res.getString(FREQUENCY_TYPE);                               
  }
  catch(Exception  e)
  {
    e.printStackTrace();
  }
  }

/*
 * get frequency type as a string
 */
  public String getFrequencyType() {
    return frequencyType;
  }

/*
 * set frequency type take new frequency type as a string 
 */
  public void setFrequencyType(String newFrequencyType) {
    frequencyType = newFrequencyType;
  }

/*
 * get frequency id as a string
 */
  public String getFrequencyID() {
    return frequencyID;
  }

/*
 * set frequency id take a new frequency id as string
 */
  public void setFrequencyID(String newFrequencyID) {
    frequencyID = newFrequencyID;
  }


}


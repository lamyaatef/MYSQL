package com.mobinil.sds.core.system.deu.encoding.model;

import java.sql.ResultSet;
import java.io.Serializable;
/*
 * this is class represent the encoding as encoding model 
 */
public class EncodingModel implements Serializable
{
  /*
   * the two fields in this entity their names are hard coded
   */
  private static final String ENCODING_ID="ENCODING_ID";
  private static final String ENCODING_TYPE="ENCODING_TYPE";

  
  private String EncodingID; //encoding id 
  private String EncodingType; //encoding type which is a string that has the name of the encoding 

  /*
   * constructor that take result set as input and extracts the values of the model fields from the result set 
   */
  public EncodingModel(ResultSet res)
  {
  try
  {
    this.EncodingID = res.getString(ENCODING_ID);
    this.EncodingType = res.getString(ENCODING_TYPE);                               
  }
  catch(Exception  e)
  {
    e.printStackTrace();
  }
  }

/*
 * get encoding id
 */
  public String getEncodingID() {
    return EncodingID;
  }

/*
 * set encoding id 
 * take new encoding id as input 
 */
  public void setEncodingID(String newEncodingID) {
    EncodingID = newEncodingID;
  }

/*
 * get encoding type as string
 */
  public String getEncodingType() {
    return EncodingType;
  }

/*
 * set encoding type 
 * take the new encoding type as string in its parameters
 */
  public void setEncodingType(String newEncodingType) {
    EncodingType = newEncodingType;
  }

}


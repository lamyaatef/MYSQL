package com.mobinil.sds.core.system.gn.dcm.model;

/**
 * DCMModel Class represents one DCM and its data.
 * 
 * 1- dcmId
 * 2- dcmName
 * 3- dcmCode
 * 4- dcmLevel
 * 
 * It has one constructors.
 * 
 * @version	1.01 April 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.Serializable;

public class DCMModel  implements Serializable
{
  int dcmId;
  String dcmName;
  String dcmCode;
  String dcmLevel;
  String channelName;
  String stkNumber;

  public DCMModel(String dcmName, String dcmCode, int dcmId, String dcmLevel,String channelName,String stkNumber)
  {
    this.dcmName = dcmName;
    this.dcmCode = dcmCode;
    this.dcmId = dcmId;
    this.dcmLevel = dcmLevel;
    this.channelName = channelName;
    this.stkNumber = stkNumber;
    
  }

  public String getDcmName()
  {
    return dcmName;
  }

  public void setDcmName(String newDcmName)
  {
    dcmName = newDcmName;
  }

  public String getDcmCode()
  {
    return dcmCode;
  }

  public void setDcmCode(String newDcmCode)
  {
    dcmCode = newDcmCode;
  }

  public int getDcmId()
  {
    return dcmId;
  }

  public void setDcmId(int newDcmId)
  {
    dcmId = newDcmId;
  }

  public String getDcmLevel()
  {
    return dcmLevel;
  }

  public void setDcmLevel(String newDcmLevel)
  {
    dcmLevel = newDcmLevel;
  }

  public void setChannelName(String newChannelName)
  {
    channelName = newChannelName;
  }
  public String getChannelName()
  {
    return channelName;
  }

  public void setStkNumber(String newStkNumber)
  {
    stkNumber = newStkNumber;
  }
  public String getStkNumber()
  {
    return stkNumber;
  }
  
}
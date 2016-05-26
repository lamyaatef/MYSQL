package com.mobinil.sds.core.system.dcm.chain.model;
import java.sql.*;
import java.io.*;

public class ChainModel implements Serializable
{
  String dcmId;
  String dcmCode;
  String dcmName;
  String dcmMail;
  String dcmPhone;
  String dcmStatusId;
  String dcmLevelId;
  String dcmLevelName;
  String stkNumber;
  String dcmStatusName;
  String dcmRank;
  String dcmCityId;
  String dcmDistrictId;
  String dcmPaymentLevelId;
  String dcmAddress;
  String channelId;
  String channelName;
  String cityName;
  String districtName;
  String dcmPaymentLevelName;

  public static final String DCM_ID = "DCM_ID";
  public static final String DCM_CODE = "DCM_CODE";
  public static final String DCM_NAME = "DCM_NAME";
  public static final String DCM_EMAIL = "DCM_EMAIL";
  public static final String DCM_PHONE = "DCM_PHONE";
  public static final String DCM_STATUS_ID = "DCM_STATUS_ID";
  public static final String DCM_LEVEL_ID = "DCM_LEVEL_ID";
  public static final String STK_NUMBER = "STK_NUMBER";
  public static final String DCM_STATUS_NAME = "DCM_STATUS_NAME";
  public static final String DCM_RANK = "DCM_RANK";
  public static final String DCM_CITY_ID = "DCM_CITY_ID";
  public static final String DCM_DISTRICT_ID = "DCM_DISTRICT_ID";
  public static final String DCM_PAYMENT_LEVEL_ID = "DCM_PAYMENT_LEVEL_ID";
  public static final String DCM_ADDRESS = "DCM_ADDRESS";
  public static final String CHANNEL_ID = "CHANNEL_ID";
  public static final String CHANNEL_NAME = "CHANNEL_NAME";
  public static final String DCM_LEVEL_NAME = "DCM_LEVEL_NAME";
  public static final String CITY_ENGLISH = "CITY_ENGLISH";
  public static final String DCM_PAYMENT_LEVEL_NAME = "DCM_PAYMENT_LEVEL_NAME";
  public static final String DISTRICT_ENGLISH = "DISTRICT_ENGLISH";
   
  public ChainModel()
  {
  }
  public ChainModel (ResultSet res)
  {
    try
    {
      dcmId = res.getString(DCM_ID);
      dcmCode = res.getString(DCM_CODE);
      dcmName = res.getString(DCM_NAME);
      dcmMail = res.getString(DCM_EMAIL);
      dcmPhone = res.getString(DCM_PHONE);
      dcmStatusId = res.getString(DCM_STATUS_ID);
      dcmLevelId = res.getString(DCM_LEVEL_ID);
      stkNumber = res.getString(STK_NUMBER);
      dcmStatusName = res.getString(DCM_STATUS_NAME);
      dcmRank = res.getString(DCM_RANK);
      dcmCityId = res.getString(DCM_CITY_ID);
      dcmDistrictId = res.getString(DCM_DISTRICT_ID);
      districtName = res.getString(DISTRICT_ENGLISH);
      dcmPaymentLevelId = res.getString(DCM_PAYMENT_LEVEL_ID);
      dcmAddress = res.getString(DCM_ADDRESS);
      channelId = res.getString(CHANNEL_ID);
      channelName = res.getString(CHANNEL_NAME);
      dcmLevelName = res.getString(DCM_LEVEL_NAME);
      dcmPaymentLevelName = res.getString(DCM_PAYMENT_LEVEL_NAME);
      cityName = res.getString(CITY_ENGLISH);

    }
     catch(Exception e)
    {
      e.printStackTrace();
    }  
  }
  /////////////////////////////////////////////////
  public String getDcmId()
  {
    return dcmId;
  }
  public void setDcmId(String newDcmId)
  {
    dcmId = newDcmId;
  }
  /////////////////////////////////////////////////
  public String getDcmCode()
  {
    return dcmCode;
  }
  public void setDcmCode(String newDcmCode)
  {
    dcmCode = newDcmCode;
  }
  ///////////////////////////////////////////////
  public String getDcmName()
  {
    return dcmName;
  }
  public void setDcmName(String newDcmName)
  {
    dcmName = newDcmName;
  }
  //////////////////////////////////////////////
  public String getDcmMail()
  {
    return dcmMail;
  }
  public void setDcmMail(String newDcmMail)
  {
    dcmMail = newDcmMail;
  }
  /////////////////////////////////////////////
  public String getDcmPhone()
  {
    return dcmPhone;
  }
  public void setDcmPhone(String newDcmPhone)
  {
    dcmPhone = newDcmPhone;
  }
  //////////////////////////////////////////////
  public String getDcmStatusId()
  {
    return dcmStatusId;
  }
  public void setDcmStatusId(String newDcmStatusId)
  {
    dcmStatusId = newDcmStatusId;
  }
  /////////////////////////////////////////////
  public String getDcmLevelId()
  {
    return dcmLevelId;
  }
  public void setDcmLevelId(String newDcmLevelId)
  {
    dcmLevelId = newDcmLevelId;
  }
  /////////////////////////////////////////////
  public String getDcmLevelName()
  {
    return dcmLevelName;
  }
  public void setDcmLevelName (String newDcmLevelName)
  {
    dcmLevelName = newDcmLevelName;
  }
  //////////////////////////////////////////////
  public String getStkNumber()
  {
    return stkNumber;
  }
  public void setStkNumber(String newStkNumber)
  {
    stkNumber = newStkNumber;
  }
  ///////////////////////////////////////////
  public String getDcmStatusName()
  {
    return dcmStatusName;
  }
  public void setDcmStatusName(String newDcmStatusName)
  {
    dcmStatusName = newDcmStatusName;
  }
  /////////////////////////////////////////////////////////
  public String getDcmRank()
  {
    return dcmRank;
  }
  public void setDcmRank(String newDcmRank)
  {
    dcmRank = newDcmRank;
  }
  ////////////////////////////////////////////////////////////
  public String getDcmCityId()
  {
    return dcmCityId;
  }
  public void setDcmCityId(String newDcmCityId)
  {
    dcmCityId = newDcmCityId;
  }
  ////////////////////////////////////////////////////
  public String getDcmCityName()
  {
    return cityName;
  }
  public void setDcmCityName(String newCityName)
  {
    cityName = newCityName;
  }
  ////////////////////////////////////////////////////
  public String getDcmDistrictId()
  {
    return dcmDistrictId;
  }
  public void setDcmDistrictId(String newDcmDistrictId)
  {
    dcmDistrictId = newDcmDistrictId;
  }
  /////////////////////////////////////////////////////
  public String getDcmDistrictName()
  {
    return districtName;
  }
  public void setDcmDistrictName(String newDistrictName)
  {
    districtName = newDistrictName;
  }
  ///////////////////////////////////////////////////////
  public String getDcmPaymentLevelId()
  {
    return dcmPaymentLevelId;
  }
  public void setDcmPaymentLevelId(String newDcmPaymentLevelId)
  {
    dcmPaymentLevelId = newDcmPaymentLevelId;
  }
  ////////////////////////////////////////////////////////////////
  public String getDcmPaymentLevelName()
  {
    return dcmPaymentLevelName;
  }
  public void setDcmPaymentLevelName(String newDcmPaymentLevelName)
  {
    dcmPaymentLevelName = newDcmPaymentLevelName;
  }
  //////////////////////////////////////////////////////////////////
  public String getDcmAddress()
  {
    return dcmAddress;
  }
  public void setDcmAddress(String newDcmAddress)
  {
    dcmAddress = newDcmAddress;
  }
  ////////////////////////////////////////////////////
  public String getChannelId()
  {
    return channelId;
  }
  public void setChannelId(String newChannelId)
  {
    channelId = newChannelId;
  }
  ///////////////////////////////////////////////////////
  public String getChannelName()
  {
    return channelName;
  }
  public void setChannelName(String newChannelName)
  {
    channelName = newChannelName;
  }
}
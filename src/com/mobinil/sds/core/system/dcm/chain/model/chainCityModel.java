package com.mobinil.sds.core.system.dcm.chain.model;
import java.sql.*;
import java.io.*;

public class chainCityModel implements Serializable 
{
  String cityId;
  String cityName;

  public static final String CITY_CODE = "CITY_CODE";
  public static final String CITY_ENGLISH = "CITY_ENGLISH";
  
  public chainCityModel()
  {
  }

  public chainCityModel(ResultSet res)
  {
    try
    {
      cityId = res.getString(CITY_CODE);
      cityName = res.getString(CITY_ENGLISH);
    }
     catch(Exception e)
    {
      e.printStackTrace();
    }  
  }
  //////////////////////////////////////////////
  public String getCityId()
  {
    return cityId;
  }
  public void setCityId(String newCityId)
  {
    cityId = newCityId;
  }
  ////////////////////////////////////////////////
  public String getCityName()
  {
    return cityName;
  }
  public void setCityName(String newCityName)
  {
    cityName = newCityName;
  }
}
package com.mobinil.sds.core.system.dcm.city.model;

public class CityModel 
{

  String cityCode     = "";
  String cityArabic   = "";
  String cityEnglish  ="";
  String citySRCode   ="";
  String cityGovCode  ="";

  public void set_city_code(String code)
  {
    cityCode = code;
  }
  public String get_city_code()
  {
    return cityCode;
  }

  public void set_city_arabic(String city_arabic)
  {
    cityArabic = city_arabic;
  }
  public String get_city_arabic()
  {
    return cityArabic;
  }

  public void set_city_english(String city_english)
  {
    cityEnglish = city_english;
  }
  public String get_city_english()
  {
    return cityEnglish;
  }

  public void set_city_sr_code(String sr_code)
  {
    citySRCode = sr_code;
  }
  public String get_city_sr_code()
  {
    return citySRCode;
  }

  public void set_city_gov_code(String gov_code)
  {
    cityGovCode = gov_code;
  }
  public String get_city_gov_code()
  {
    return cityGovCode;
  }
  
  public CityModel()
  {
  }
}
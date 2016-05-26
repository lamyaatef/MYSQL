package com.mobinil.sds.core.system.dcm.chain.model;
import java.sql.*;
import java.io.*;

public class chainDistrictModel  implements Serializable
{
  String districtId;
  String districtName;

  public static final String DISTRICT_CODE = "DISTRICT_CODE";
  public static final String DISTRICT_ENGLISH = "DISTRICT_ENGLISH";
  
  public chainDistrictModel()
  {
  }

  public chainDistrictModel(ResultSet res)
  {
    try
    {
      districtId = res.getString(DISTRICT_CODE);
      districtName = res.getString(DISTRICT_ENGLISH);
    }
     catch(Exception e)
    {
      e.printStackTrace();
    }  
  }
  ///////////////////////////////////////////////////////
  public String getDistrictId()
  {
    return districtId;
  }
  public void setDistrictId(String newDistrictId)
  {
    districtId = newDistrictId;
  }
  //////////////////////////////////////////////////////////
  public String getDistrictName()
  {
    return districtName;
  }
  public void setDistrictName(String newDisrictName)
  {
    districtName = newDisrictName;
  }
}
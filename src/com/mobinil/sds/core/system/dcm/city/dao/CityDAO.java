package com.mobinil.sds.core.system.dcm.city.dao;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.system.dcm.city.model.*;
import com.mobinil.sds.core.utilities.*;

public class CityDAO 
{
  public static Vector get_all_cities(Connection con)
  {
    Vector cities       = new Vector();
    try
    {
        Statement stmt      = con.createStatement();
        CityModel cityModel ;


        
        String sqlString = "select * from GEN_CITY";
        ResultSet rs = stmt.executeQuery(sqlString);
        while(rs.next())
        {
          cityModel = new CityModel();
          cityModel.set_city_code(rs.getString("CITY_CODE"));
          cityModel.set_city_english(rs.getString("CITY_ENGLISH"));
          cities.add(cityModel);  
        }
        rs.close();
        stmt.close();
      
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return cities;
  }
  public CityDAO()
  {
  }
}
package com.mobinil.sds.core.system.ccm.schema.dao;
import java.sql.*;
import java.util.Vector;


import com.mobinil.sds.core.system.ccm.schema.model.*;




public class SchemaDao 
{
  public SchemaDao()
  {
  }
  

  public static Vector getAllSchemas(Connection con,String status,String channelID)
  {
      Vector Vec = new Vector();
      try
      {
       Statement stat = con.createStatement();
       String strSql = "select * from VW_SOP_SCHEMA where CHANNEL_ID = '"+channelID+"' SCHEMA_STATUS_ID= "+status+"";
       ResultSet res= stat.executeQuery(strSql);
       while(res.next())
       {
    	   Vec.add(new SchemaModel(res));
       }
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return Vec; 
  }
}

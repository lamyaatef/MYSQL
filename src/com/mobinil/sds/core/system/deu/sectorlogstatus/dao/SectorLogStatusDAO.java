package com.mobinil.sds.core.system.deu.sectorlogstatus.dao;

import com.mobinil.sds.core.system.deu.sectorlogstatus.model.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

public class SectorLogStatusDAO {
 
  private static boolean debugFlag = false; 

  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }
  public static Vector getAllSectorLogStatus()
  {
   Vector statusVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from VW_DEU_SECTOR_LOG_STATUS");
    while (res.next())
    {
      statusVector.add(new SectorLogStatusModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return statusVector;
  }

}
package com.mobinil.sds.core.system.deu.sector.dao;
import com.mobinil.sds.core.system.deu.sector.model.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

public class SectorDAO 
{
  private static boolean debugFlag = false; 

  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }
  public static Vector getSectorsByFileID(String fileID)
  {
     Vector sectorVector = new Vector(); 
     try
     {
      Connection con = Utility.getConnection() ;
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery("select * from VW_DEU_SECTORS WHERE "+
      "VW_DEU_SECTORS.OUTPUT_FILE_ID= "+fileID+"ORDER BY SECTOR_ORDER");
      while (res.next())
      {
        sectorVector.add(new SectorModel(res));
      }
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }   
    return sectorVector;
  }

  public static int addSector(String fileID,String sourceID,String separator,
  String sectorOrder)
  {
    int ret=1;
    try
     {
        Connection con = Utility.getConnection();
        Statement stat = con.createStatement();
        String insert="INSERT INTO DEU_OUTPUT_FILE_SECTOR_SOURCE"+
        "(SECTOR_ID, OUTPUT_FILE_ID, SOURCE_ID, SEPARATOR, SECTOR_ORDER)"+
        "VALUES"+
        "(SEQ_DEU_SECTOR.NEXTVAL, '"+fileID+"', '"+sourceID+"', '"+separator+
        "', '"+sectorOrder+"')";
        debug(insert);    
        stat.execute(insert);
        debug("Adding Sector [return:"+ret+"]");
        stat.close();
        Utility.closeConnection(con);
     }catch(Exception e){
       e.printStackTrace();
       ret=-1;
    }
    return ret;
  }

  public static int deleteSectors(String fileID)
  {
    int ret=1;
      try
     {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      String update="DELETE FROM DEU_OUTPUT_FILE_SECTOR_SOURCE where "+
      "DEU_OUTPUT_FILE_SECTOR_SOURCE.OUTPUT_FILE_ID= "+fileID;
      debug(update);    
      stat.executeUpdate(update);
      debug("Deleting Sectors [return:"+ret+"]");
      stat.close();
      Utility.closeConnection(con);
     }catch(Exception e){
     e.printStackTrace();
     ret=-1;
  }
      return ret;
  }
}
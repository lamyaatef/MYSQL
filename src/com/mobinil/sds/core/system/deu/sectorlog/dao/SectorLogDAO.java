package com.mobinil.sds.core.system.deu.sectorlog.dao;
import com.mobinil.sds.core.system.deu.sectorlog.model.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

public class SectorLogDAO 
{
  private static boolean debugFlag = false; 

  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }
  
  public static Vector getAllSectorLogs()
  {
   Vector logVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("SELECT * FROM VW_DEU_SECTOR_LOG order by task_work_log_id, sector_work_log_id");
    while (res.next())
    {
      logVector.add(new SectorLogModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return logVector;
  }

  public static Vector searchSectorLogs(String logID, String initialRelation, 
  String initTime, String finalTime, String finalRelation, String task, 
  String file, String source, String connection, String rowCountRelation, 
  String rowCount, String status, String taskWorkLogId)
  {
   Vector logVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    boolean first=true;
    String search="SELECT * FROM VW_DEU_SECTOR_LOG  ";
    if((logID!=null)&&(!logID.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
        search+=" AND ";
      search+="VW_DEU_SECTOR_LOG.SECTOR_WORK_LOG_ID='"+logID+"'";
    }
    if((initTime!=null)&&(!initTime.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
        search+=" AND ";
      search+="trunc(to_date(VW_DEU_SECTOR_LOG.TIME_INITIAL,'dd/mm/yyyy HH24:MI:ss'))"+initialRelation+"to_date('"+initTime+"','dd/mm/yyyy')";
    }
    if((finalTime!=null)&&(!finalTime.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
        search+=" AND ";
      search+="trunc(to_Date(VW_DEU_SECTOR_LOG.TIME_FINAL,'dd/mm/yyyy  HH24:MI:ss'))"+finalRelation+"to_date('"+finalTime+"','dd/mm/yyyy')";
    }
    if((task!=null)&&(!task.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
        search+=" AND ";
      search+="VW_DEU_SECTOR_LOG.TASK_NAME='"+task+"'";
    }
    if((source!=null)&&(!source.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
        search+=" AND ";
      search+="VW_DEU_SECTOR_LOG.SOURCE_NAME='"+source+"'";
    }
    if((connection!=null)&&(!connection.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
        search+=" AND ";
      search+="VW_DEU_SECTOR_LOG.CONNECTION_NAME='"+connection+"'";
    }
    if((file!=null)&&(!file.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
       search+=" AND ";
      search+="VW_DEU_SECTOR_LOG.OUTPUT_FILE_NAME='"+file+"'";
    }
    if((rowCount!=null)&&(!rowCount.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
        search+=" AND ";
      search+="VW_DEU_SECTOR_LOG.ROW_COUNT"+rowCountRelation+rowCount;
    }

    if((taskWorkLogId!=null)&&(!taskWorkLogId.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
        search+=" AND ";
        
      search+="VW_DEU_SECTOR_LOG.task_work_log_id = "+taskWorkLogId;
    }

    
    if((status!=null)&&(!status.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
        search+=" AND ";
      search+="VW_DEU_SECTOR_LOG.SECTOR_STATUS='"+status+"'";
    }
    debug(search);

    search +=" order by task_work_log_id, sector_work_log_id";
    ResultSet res = stat.executeQuery(search);
    while (res.next())
    {
      logVector.add(new SectorLogModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return logVector;
  }

  public static Vector getSectorLogs(String taskLogID)
  {
   Vector logVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("SELECT * FROM VW_DEU_SECTOR_LOG where VW_DEU_SECTOR_LOG.TASK_WORK_LOG_ID='"+taskLogID+"'");

//    Utility.logger.debug(    "SELECT * FROM VW_DEU_SECTOR_LOG where VW_DEU_SECTOR_LOG.TASK_WORK_LOG_ID='"+taskLogID+"'" );
    
    while (res.next())
    {
      logVector.add(new SectorLogModel(res));
    }
    
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
  // e.printStackTrace();
   }   
   return logVector;
  }

  
}
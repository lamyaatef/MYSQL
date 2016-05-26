package com.mobinil.sds.core.system.deu.tasklog.dao;
import com.mobinil.sds.core.system.deu.tasklog.model.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

public class TaskLogDAO 
{
  private static boolean debugFlag = false; 

  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }
  public static Vector getAllTaskLogs()
  {
   Vector logVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from VW_DEU_TASK_LOG order by task_work_log_id");
    while (res.next())
    {
      logVector.add(new TaskLogModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return logVector;
  }

 public static Vector searchTaskLogs(String logID, String initialRelation, 
  String initTime, String finalTime, String finalRelation, String task, 
  String file, String secCountRelation, String secCount, 
  String rowCountRelation, String rowCount, String status)
  {
   Vector logVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    boolean first=true;
    String search="SELECT * FROM VW_DEU_TASK_LOG ";
    if((logID!=null)&&(!logID.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
        search+=" AND ";
      search+="VW_DEU_TASK_LOG.TASK_WORK_LOG_ID='"+logID+"'";
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
      search+="TRUNC(VW_DEU_TASK_LOG.TIME_INITIAL)"+initialRelation+"to_date('"+initTime+"','mm/dd/yyyy')";
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
      search+="TRUNC(VW_DEU_TASK_LOG.TIME_FINAL)"+finalRelation+"to_date('"+finalTime+"','mm/dd/yyyy')";
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
      search+="VW_DEU_TASK_LOG.TASK_NAME='"+task+"'";
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
      search+="VW_DEU_TASK_LOG.OUTPUT_FILE_NAME='"+file+"'";
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
      search+="VW_DEU_TASK_LOG.ROW_COUNT"+rowCountRelation+rowCount;
    } 
    if((secCount!=null)&&(!secCount.equals("")))
    {
      if(first)
      {
        search+="WHERE ";
        first=false;
      }
      else
        search+=" AND ";
      search+="VW_DEU_TASK_LOG.SECTOR_COUNT"+secCountRelation+secCount;
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
      search+="VW_DEU_TASK_LOG.STATUS_ID='"+status+"'";
    }
    debug(search);
    
    ResultSet res = stat.executeQuery(search);
    while (res.next())
    {
      logVector.add(new TaskLogModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return logVector;
  }


}
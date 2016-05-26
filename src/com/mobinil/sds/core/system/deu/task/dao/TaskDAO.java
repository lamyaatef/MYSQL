package com.mobinil.sds.core.system.deu.task.dao;

import com.mobinil.sds.core.system.deu.task.model.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

public class TaskDAO  {

  private static boolean debugFlag = false; 

  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }
  
  public static Vector getAllTasks()
  {
   Vector taskVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("SELECT * FROM VW_DEU_TASK order by TASK_STATUS,NEXT_RUN_DATE");
    while (res.next())
    {
      taskVector.add(new TaskModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return taskVector;
  }


  public static int addTask
    (String name, String outputFile, String frequency, String dailyRate , String monthlyRate, 
    String weeklyRate, String weekDay, String monthlyMonthDay, String yearlyMonthDay, String month, String startDate, 
    String endOption, String endDate, String maxOccurrences, String status, 
    String runHour,String runMin, String description)
  {
  String rate="";String monthDay="";
  if (frequency.equals("1"))
  {
    weekDay="";month="";rate=dailyRate;
  }  
  else if (frequency.equals("2"))
  {
    rate="";weekDay="";month="";
  }
  else if (frequency.equals("3"))
  {
    month="";rate=weeklyRate;
  }
  else if (frequency.equals("4"))
  {
    weekDay="";month="";rate=monthlyRate;monthDay=monthlyMonthDay;
  }
  else if (frequency.equals("5"))
  {
    rate="";weekDay="";monthDay=yearlyMonthDay;
  }
  
  if(endOption.equals("1"))
  {
    endDate="";maxOccurrences="";
  }
  else if(endOption.equals("2"))
  {
    endDate="";
  }
  else if(endOption.equals("3"))
  {
    maxOccurrences="";
  }  
    
  int ret=1;
    try
   {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();      
      String insert="INSERT INTO DEU_TASK_SETTINGS"+
     "(TASK_SETTINGS_ID, NAME, OUTPUT_FILE_ID, FREQUENCY_ID, START_DATE, ";
     if(endOption.equalsIgnoreCase("3"))
      insert+="END_DATE,";
     insert+=" STATUS_ID, FREQ_RUN_HOUR,FREQ_RUN_MIN, DESCRITPION, MAX_OCCURRENCES, DAY_OF_WEEK, RATE, DAY_OF_MONTH, MONTH) "+
      "VALUES (SEQ_DEU_TASK_SETTING.NEXTVAL, '"+name+"','"+outputFile+"','"+frequency+
      "',to_date('"+startDate+"','dd/mm/yyyy')";
      if(endOption.equalsIgnoreCase("3"))
        insert+=",to_date('"+endDate+"','dd/mm/yyyy')";
      insert+=",'"+status+"','"+runHour+"','"+runMin+"','"+description+"','"+maxOccurrences+"','"
      +weekDay+"','"+rate+"','"+monthDay+"','"+month+"')"; 
      debug(insert);
      stat.execute(insert);
      debug("Adding Task [return:"+ret+"]");
      stat.close();
      Utility.closeConnection(con);
     }catch(Exception e){
     e.printStackTrace();
     ret=-1;
    }
    return ret;
  }

  public static int updateTask
    (String taskID, String name, String outputFile, String frequency, String dailyRate , String monthlyRate, 
    String weeklyRate, String weekDay, String monthlyMonthDay, String yearlyMonthDay, String month, String startDate, 
    String endOption, String endDate, String maxOccurrences, String status, 
    String runHour, String runMin, String description)
  {
  String rate="";String monthDay="";
  if (frequency.equals("1"))
  {
    weekDay="";month="";rate=dailyRate;
  }  
  else if (frequency.equals("2"))
  {
    rate="";weekDay="";month="";
  }
  else if (frequency.equals("3"))
  {
    month="";rate=weeklyRate;
  }
  else if (frequency.equals("4"))
  {
    weekDay="";month="";rate=monthlyRate;monthDay=monthlyMonthDay;
  }
  else if (frequency.equals("5"))
  {
    rate="";weekDay="";monthDay=yearlyMonthDay;
  }
  if(endOption.equals("1"))
  {
    endDate="";maxOccurrences="";
  }
  else if(endOption.equals("2"))
  {
    endDate="";
  }
  else if(endOption.equals("3"))
  {
    maxOccurrences="";
  }  
    
  int ret=1;
    try
   {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();      
      String update="UPDATE DEU_TASK_SETTINGS SET ";
      update+="NAME= '"+name;
      update+="',OUTPUT_FILE_ID=  '"+outputFile;
      update+="',FREQUENCY_ID=  '"+frequency+"'";
      update+=",START_DATE= to_date('"+startDate+"','dd/mm/yyyy'),END_DATE= ";
      if(endOption.equalsIgnoreCase("3"))
        update+="to_date('"+endDate+"','dd/mm/yyyy')";
        else update+="''";
      update+=",STATUS_ID=  '"+status;
      update+="',FREQ_RUN_HOUR=  '"+runHour;
      update+="',FREQ_RUN_MIN=  '"+runMin;
      update+="',DESCRITPION= '"+description;
      update+="',MAX_OCCURRENCES=  '"+maxOccurrences;
      update+="',DAY_OF_WEEK=  '"+weekDay;
      update+="',RATE=  '"+rate;
      update+="',DAY_OF_MONTH=  '"+monthDay;
      update+="',MONTH = '"+month;
      update+="' WHERE TASK_SETTINGS_ID= "+taskID;
      debug(update);
      stat.execute(update);
      debug("Updating Task [return:"+ret+"]");
      stat.close();
      Utility.closeConnection(con);
     }catch(Exception e){
     e.printStackTrace();
     ret=-1;
    }
    return ret;
  }  
  public static TaskModel getTaskByID (String id)
  {
  TaskModel tm=null;
    try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from vw_deu_task where TASK_ID="+id);
    if(res.next())
    {
      tm= new TaskModel(res);
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   } 
   return tm;
  }

  public static void deleteTask (String id)
  {
    try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    stat.executeUpdate("delete from deu_task_settings where task_settings_id="+id);
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   } 
  }
}
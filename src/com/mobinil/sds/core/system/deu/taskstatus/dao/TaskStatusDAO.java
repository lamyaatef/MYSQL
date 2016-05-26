package com.mobinil.sds.core.system.deu.taskstatus.dao;

import com.mobinil.sds.core.system.deu.taskstatus.model.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

/*
 * this class is task status database access object
 * that is responsible of accessing the task status entity in the database 
 * 
 */
public class TaskStatusDAO  {

 //debug flag should only be set to true in case of debugging the file
  private static boolean debugFlag = false; 

/*
 *  this method is used to send to it a string and it only print it out if the debug flag is set to true 
 */
  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }

  /*
   * this method return a vector of task status model 
   */
  public static Vector getAllTaskStatus()
  {
   Vector statusVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from vw_deu_task_status");
    while (res.next())
    {
      statusVector.add(new TaskStatusModel(res));
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
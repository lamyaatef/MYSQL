package com.mobinil.sds.core.system.deu.tasklogstatus.dao;

import com.mobinil.sds.core.system.deu.tasklogstatus.model.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;


/*
 * task log status Dao 
 * database access object to manipulate the task log status entity
 */
public class TaskLogStatusDAO {

 //debug flag should only be true if the object is being debugged
  private static boolean debugFlag = false; 

/*
 * this method prints the string msg sent to it only if debugFlag is set to true 
 */
  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }

  /*
   * get all task log status as a vector composed of TaskLogStatusModel
   */
  public static Vector getAllTaskLogStatus()
  {
   Vector statusVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from VW_DEU_TASK_LOG_STATUS");
    while (res.next())
    {
      statusVector.add(new TaskLogStatusModel(res));
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
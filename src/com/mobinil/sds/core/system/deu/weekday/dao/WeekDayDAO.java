package com.mobinil.sds.core.system.deu.weekday.dao;
import com.mobinil.sds.core.system.deu.weekday.model.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

/*
 * this class represent week day database access object that is responsible about database accessing for the week day entity
 * 
 */
public class WeekDayDAO 
{

//this is a debug flag should be set to true in case of debugging this class
  private static boolean debugFlag = false; 

/*
 * this is debug method that take string as input 
 * and print this stream only if the debug flag is true 
 */
  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }

/*
 * this method return all week day in the database as a vector of WeekDayModel
 */
  public static Vector getAllDays()
  {
   Vector daysVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from vw_deu_weekday");
    while (res.next())
    {
      daysVector.add(new WeekDayModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return daysVector;
  }
}
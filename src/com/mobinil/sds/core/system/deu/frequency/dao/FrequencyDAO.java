package com.mobinil.sds.core.system.deu.frequency.dao;

import com.mobinil.sds.core.system.deu.frequency.model.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

/*
 * this class if the frequence data access object 
 * that is responsbile of getting the frequencey from the database
 */
public class FrequencyDAO  {

 //this is the debug flag should be set to false if not debugging the class 
  private static boolean debugFlag = false; 

/*
 * debug method that take a string as input and only print it
 * to the system.out. stream in case the debug flag is set to true 
 */
  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }

/*
 * get all frequencies as a vector of frequency model 
 */
  
  public static Vector getAllFrequencies()
  {
   Vector freqVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from vw_deu_frequency");
    while (res.next())
    {
      freqVector.add(new FrequencyModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return freqVector;
  }

}
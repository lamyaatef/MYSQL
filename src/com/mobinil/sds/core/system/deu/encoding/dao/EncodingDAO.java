package com.mobinil.sds.core.system.deu.encoding.dao;

import com.mobinil.sds.core.system.deu.encoding.model.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

/*
 * this class is responsible about the encoding entity database access object
 */
public class EncodingDAO  {

 //debug flag should only be true incase of debugging the class
  private static boolean debugFlag = false; 

/*
 * debug method take a string sa input and only print it if the debug flag is true
 */
  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }

/*
 * get all encoding in the databse as a vector composed of EncodingModels
 */
  public static Vector getAllEncodings()
  {
   Vector encodingVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from vw_deu_encoding order by upper(encoding_type)");
    while (res.next())
    {
      encodingVector.add(new EncodingModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return encodingVector;
  }

}
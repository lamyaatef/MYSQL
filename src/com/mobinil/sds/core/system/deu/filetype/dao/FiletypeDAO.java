package com.mobinil.sds.core.system.deu.filetype.dao;
/*
 * this is the file type dao responsible of retriving the file types from 
 * the database 
 */
import com.mobinil.sds.core.system.deu.filetype.model.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

public class FiletypeDAO  {

 //debug flag should be true when debugging this class 
  private static boolean debugFlag = false; 

//debug method take a string as argument and only print it out if the debug flag is true
  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }


/*
 * get all file types in teh system in a vector of filetype model
 */
  public static Vector getAllFiletypes()
  {
   Vector typesVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from vw_deu_file_type order by upper(file_type_ext)");
    while (res.next())
    {
      typesVector.add(new FileTypeModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return typesVector;
  }

}
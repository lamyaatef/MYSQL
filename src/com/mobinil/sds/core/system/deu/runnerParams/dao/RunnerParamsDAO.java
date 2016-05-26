package com.mobinil.sds.core.system.deu.runnerParams.dao;

import com.mobinil.sds.core.system.deu.runnerParams.model.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

public class RunnerParamsDAO  {

 //debug flag should be set to true only when debugging the class 
  private static boolean debugFlag = false; 

/*
 * debug method print the msg fi the debug flag is set to true 
 */
  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }

/*
 * get all params as a vector of RunnerParamModel
 */
  public static Vector getAllParams()
  {
   Vector paramVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from DEU_RUNNER_PARAMS");
    while (res.next())
    {
      paramVector.add(new RunnerParamsModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return paramVector;
  }

}
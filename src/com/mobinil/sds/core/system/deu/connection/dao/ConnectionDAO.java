package com.mobinil.sds.core.system.deu.connection.dao;
import com.mobinil.sds.core.system.deu.connection.model.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

/*
 * this class is the connection data access object that is responsible of the connection entity in the database
 */
public class ConnectionDAO 
{

// this is a flag that should be true only in case of debuggin since it will result in printing traces through out the execution of the program
  private static boolean debugFlag = false; 

/*
 * the debug method take a string as input and only print it if the debug flag is true
 */
  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }

/*
 * get all connections in the database and return them in a vector
 */
  public static Vector getAllConnections()
  {
   Vector connectionVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from vw_deu_connection order by name");
    while (res.next())
    {
      connectionVector.add(new ConnectionModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
     e.printStackTrace();
   }   
   return connectionVector;
  }

/*
 * this function add new connection it takes the following parameters
 * String name this is the connection name
 * String dbIP this is the database ip
 * String dbPort database port
 * String dbSchema  schema name
 * String dbUser user name
 * String dbPass user password
 * String desc description of the connection
 * 
 * the function return null in case of its success other wise it return the error message so it can be displayed to the user
 */
  public static String addConnection(String name,String dbIP, String dbPort, String dbSchema, String dbUser, String dbPass,String desc)
  {
    //int ret=1;
    if(desc==null)
      desc="";
      try
     {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      String update="INSERT INTO DEU_CONNECTION"+
      "(CONNECTION_ID, NAME, IP, PORT, SCHEMA, USERNAME, PASSWORD, DESCRIPTION)"+
      "VALUES(SEQ_DEU_CONNECTION.NEXTVAL,'"+name+"','"+dbIP+"','"+dbPort+"','"+dbSchema+"','"+dbUser+"','"+PasswordUtility.encrypt(dbPass,5)+"','"+desc+"')";

      debug(update);    
      stat.execute(update);

      stat.close();
      Utility.closeConnection(con);
     }catch(Exception e){
     return e.getMessage();
    }
    return null;
  }

/*
 * update connection 
 * String name this is the connection name
 * String dbIP this is the database ip
 * String dbPort database port
 * String dbSchema  schema name
 * String dbUser user name
 * String dbPass user password
 * String desc description of the connection
 * 
 * the function return null in case of its success other wise it return the error message so it can be displayed to the user
 
 */
  public static String UpdateConnection(String id,String name,String dbIP, String dbPort, String dbSchema, String dbUser, String dbPass,String desc)
  {
    //int ret=1;
      try
     {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      if((desc==null)||(desc.equals("null")))
        desc="";
      String update="UPDATE DEU_CONNECTION "+
      "SET NAME='"+name+"', IP='"+dbIP+"', PORT='"+dbPort+"', SCHEMA='"+dbSchema+"', USERNAME='"+dbUser;
  if(!dbPass.equals(""))
      update+="', PASSWORD='"+PasswordUtility.encrypt(dbPass,5);
      update+="', DESCRIPTION='"+desc+"' "+
      "WHERE CONNECTION_ID='"+id+"'";
      debug(update);    
      stat.executeUpdate(update);
    
      stat.close();
      Utility.closeConnection(con);
     }catch(Exception e){
     return e.getMessage();
  }
      return null;
  }

/*
 * get connection model of a certin connection 
 * take connection id as input
 */
  public static ConnectionModel getConnectionByID (String id)
  {
    ConnectionModel cm=null;
    try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from vw_deu_connection where connection_id="+id);
    if(res.next())
    {
       cm= new ConnectionModel(res);
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   } 
   return cm;
  }

/*
 * this function test if a connection using its input parameters can be opend or not 
 * it retun true if the connection opening was successful 
 * other wise it return false 
 * 
 * the input parameters are 
 * dpIP : database IP address
 * dpPort : database port 
 * dbSchema : schema name
 * dbUser: database user name
 * dbPass: database password
 * 
 * this function assume that all connections are oracle connections 
 * 
 */
  public static boolean testConnection(String dbIP, String dbPort, String dbSchema, String dbUser, String dbPass)
  {
    try{
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection c=DriverManager.getConnection("jdbc:oracle:thin:@"+dbIP+":"+dbPort+":"+dbSchema, dbUser,dbPass);
      c.close();
    }
    catch(Exception e)
    {return false;}
    return true;
  }

/*
 * delete a certin connection by its ID 
 * take input Connection Id 
 */
  public static boolean deleteConnection (String id)
  {
  boolean successFlag = true; 
  
    try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    stat.executeUpdate("delete from DEU_CONNECTION where CONNECTION_ID="+id);
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
  // e.printStackTrace();
  successFlag = false; 
   } 

   return successFlag;
  }
  
}
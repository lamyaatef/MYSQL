package com.mobinil.sds.core.system.deu.source.dao;

import com.mobinil.sds.core.system.deu.source.model.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

public class SourceDAO 
{
  private static boolean debugFlag = false; 

  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }
  public static Vector getAllSources()
  {
   Vector sourceVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from VW_DEU_SOURCES order by upper(name)");
    while (res.next())
    {
      sourceVector.add(new SourceModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return sourceVector;
  }

  public static int addSource(String name,String sql, String connection, String dataview, String desc)
  {
  int ret=1;
    try
   {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    String update="INSERT INTO DEU_SOURCE (SOURCE_ID, NAME, SOURCE_SQL, CONNECTION_ID, DESCRIPTION) VALUES (SEQ_DEU_SOURCE.NEXTVAL, '"+name+"','"+sql+"','"+connection+"','"+desc+"')";
    debug(update);    
    stat.execute(update);
    debug("Adding Source [return:"+ret+"]");
    stat.close();
    Utility.closeConnection(con);
   }catch(Exception e){
   e.printStackTrace();
   ret=-1;
  }
      return ret;
  }

  public static int UpdateSource(String id,String name,String sql, String connection, String dataview, String desc)
  {
  int ret=1;
    try
   {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    if((desc==null)||(desc.equals("null")))
      desc="";
    String update="UPDATE DEU_SOURCE "+
    "SET NAME='"+name+"', SOURCE_SQL='"+sql+"'";
    if(connection!=null)
      update+=", CONNECTION_ID='"+connection+"'";
    update+=", DESCRIPTION='"+desc+"' "+
    "WHERE SOURCE_ID='"+id+"'";
    debug(update);    
    stat.executeUpdate(update);
    debug("Updating Source [return:"+ret+"]");
    stat.close();
    Utility.closeConnection(con);
   }catch(Exception e){
   e.printStackTrace();
   ret=-1;
  }
  return ret;
  }

  public static SourceModel getSourceByID (String id)
  {
  SourceModel sm=null;
    try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from VW_DEU_SOURCES where source_id="+id);
    if(res.next())
    {
       sm= new SourceModel(res);
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   } 
   return sm;
  }

   public static boolean deleteSource (String id)
  {
  boolean successFlag = true;
  Connection con =null;
    try
   {
     con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    stat.executeUpdate("delete from DEU_SOURCE where SOURCE_ID="+id);
    
    }
   catch(Exception e)
   {
   //e.printStackTrace();
   successFlag = false; 
   }
   
   if (con!=null)
   {
   try{
    Utility.closeConnection(con);
   }
   catch(Exception e)
   {
   
   }
   }
   return successFlag; 
  }
}
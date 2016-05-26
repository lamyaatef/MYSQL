package com.mobinil.sds.core.system.deu.file.dao;

import com.mobinil.sds.core.system.deu.file.model.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.*;

/*
 * file database access object is a class resoponsible about retriving and updating and inserting in the entity of file in the database
 */
public class FileDAO  {

//debug flag should be set to true only when debugging the class
  private static boolean debugFlag = false; 

/*
 * debug method take a msg as string and print it only if the debug flag is set to true 
 */
  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }

  /*
   * get the next file id from the sequence seq_deu_output_file and return it as int 
   */
   public static int getNextID()
   {
   int nextID=0;
    try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("SELECT SEQ_DEU_OUTPUT_FILE.NEXTVAL FROM DUAL");
    while (res.next())
    {
      nextID=res.getInt(1);
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return nextID;
   }

   /*
    * get all files as a vector composed of file models
    */
  public static Vector getAllFiles()
  {
   Vector fileVector = new Vector(); 
   try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("SELECT * FROM vw_deu_files order by upper(name)");
    while (res.next())
    {
      fileVector.add(new FileModel(res));
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   }   
   return fileVector;
  }

/*
 * add a new file to the database
 * take the following as input 
 * 
 * file name 
 * destination 
 * file name on the hard disk 
 * file extension 
 * sector spearator 
 * time stamped
 * encoding 
 * descriptoin
 * 
 * the function return a positive id incase of its success otherwise it return -1
 * 
 */
  public static int addFile(String name, String destination,String fileName, String fileExtension, String separator, String timeStamped, String encoding, String description)
  {
  int fileID=-1;
    try
   {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    if(timeStamped.equalsIgnoreCase("ON"))
      timeStamped="1";
    else 
       timeStamped="0";
    fileID=getNextID();
    String updateFile="INSERT INTO DEU_OUTPUT_FILE"+
   "(OUTPUT_FILE_ID, NAME, DESTINATION, FILE_NAME, FILE_TYPE, SECTOR_SEPARATOR, TIME_STAMPED, ENCODING_ID,DESCRIPTION) "+
    "VALUES ('"+fileID+"', '"+name+"','"+destination+"','"+fileName+"','"+fileExtension+"','"+separator+"','"+timeStamped+"','"+encoding+"','"+description+"')";
    debug(updateFile);    
    stat.execute(updateFile);
    debug("Adding File [return:"+fileID+"]");
    stat.close();
    Utility.closeConnection(con);
   }catch(Exception e){
   e.printStackTrace();
   fileID=-1;
  }
      return fileID;
  }

/*
 * update file in the database
 * file name 
 * destination 
 * file name on the hard disk 
 * file extension 
 * sector spearator 
 * time stamped
 * encoding 
 * descriptoin
 * 
 * the function return a positive id incase of its success otherwise it return -1
 * 
 */
  public static int UpdateFile(String id, String name, String destination,String fileName, String fileExtension, String separator, String timeStamped, String encoding, String description)
  {
  int ret=1;
    try
   {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    if((description==null)||(description.equals("null")))
      description="";
    if (timeStamped==null)
      timeStamped="0";
    else if(timeStamped.equalsIgnoreCase("ON"))
      timeStamped="1";
    String update="UPDATE DEU_OUTPUT_FILE SET NAME= '"+name+"',DESTINATION= '"+destination+"'," +
    "FILE_NAME= '"+fileName+"', FILE_TYPE= '"+fileExtension+"', SECTOR_SEPARATOR= '"+separator+"', TIME_STAMPED= '"+timeStamped+"',"+ 
    "ENCODING_ID= '"+encoding+"',DESCRIPTION= '"+description+"' WHERE OUTPUT_FILE_ID='"+id+"'";
    debug(update);    
    stat.executeUpdate(update);
    debug("Updating File [return:"+ret+"]");
    stat.close();
    Utility.closeConnection(con);
   }catch(Exception e){
   e.printStackTrace();
   ret=-1;
  }
      return ret;
  }

/*
 * get filemodel of the file id sent to the function 
 * other wise if the file was not found it return null
 */
  public static FileModel getFileByID (String id)
  {
  FileModel fm=null;
    try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from vw_deu_files where OUTPUT_FILE_ID="+id);
    if(res.next())
    {
      fm= new FileModel(res);
    }
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   e.printStackTrace();
   } 
   return fm;
  }

/*
 * delete file from the system of that id 
 */
    public static boolean deleteFile (String id)
  {
  boolean successFlag = true; 
    try
   {
    Connection con = Utility.getConnection() ;
    Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from VW_DEU_TASK_FILE where output_file_id =" +id);
    if (res.next())
    {
    //there is a task that refer to this file
    successFlag = false;
    }
    else
    {    
    stat.execute("delete from DEU_OUTPUT_FILE_SECTOR_SOURCE where OUTPUT_FILE_ID="+id);    
    stat.execute("delete from DEU_OUTPUT_FILE where OUTPUT_FILE_ID="+id);
    }
    
    stat.close();
    Utility.closeConnection(con);
    }
   catch(Exception e)
   {
   //e.printStackTrace();
   successFlag = false;
   }

   return successFlag ; 
  }
}
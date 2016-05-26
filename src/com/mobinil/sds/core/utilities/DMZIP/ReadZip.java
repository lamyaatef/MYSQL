package com.mobinil.sds.core.utilities.DMZIP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.mobinil.sds.core.system.dataMigration.DAO.DataMigrationDao;
import com.mobinil.sds.core.utilities.Utility;

public class ReadZip {

	
  public  int getDMZIPData(Connection con, String file_path, Long file_id) {
	//  HashMap DM_HM = new HashMap();
	   int numReadLine=0;
    try 
    {
	 // java.sql.Statement state = con.createStatement();
	 String sql = "insert into DM_SIMSWAP_DATA (FILE_ID, ACTIVATION_DATE, DIAL_NO, SIM, SIM_EXTENDED, POS_CODE) "
			+ " values(?,TO_DATE(?,'dd/mm/yyyy'),?,?,?,?)";
				
      java.sql.PreparedStatement  prest = con.prepareStatement(sql);
	  prest.clearBatch();
	  System.out.println("current time b4 insert is "+new java.util.Date());
	  ZipFile zf = new ZipFile(file_path);
	  Enumeration entries = zf.entries();
	  Integer hm_id=new Integer(0);
	  ArrayList zip_line_data_AL = null;
	  hm_id=new Integer(0);
	
  
	 // numReadLine = new Integer(-1);
	  while (entries.hasMoreElements()) 
	  {
		  
		 
		
	        ZipEntry ze = (ZipEntry) entries.nextElement();
          long size = ze.getSize();
          if (size > 0) {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(zf.getInputStream(ze)));
            String line; 
            int last_visisted_comma=-1;     
            	int count= 0;
            	
            while ((line = br.readLine()) != null) {
            	 numReadLine++;
            	
            	
            	 count++;
            	 zip_line_data_AL = new ArrayList(5);
            	 
            	String[] line_items = splitString(line, 5, ",");
            	String[] date_items = splitString(line_items[0], 2, " ");
            	
            	String[] dialNo_items = splitString(line_items[1], 2, "\"");
            	String[] SIM_items = splitString(line_items[2], 2, "\"");
            	String[] SIM_EXTENDED_items = splitString(line_items[3], 2, "\"");
            	String[] POS_CODE_items = splitString(line_items[4], 2, "\"");
            	zip_line_data_AL.add(date_items[0]);
            	zip_line_data_AL.add(Utility.replaceAll(line_items[1], "\"", ""));
            	zip_line_data_AL.add(Utility.replaceAll(line_items[2], "\"", ""));
            	zip_line_data_AL.add(Utility.replaceAll(line_items[3], "\"", ""));
            	zip_line_data_AL.add(Utility.replaceAll(line_items[4], "\"", ""));
            	  	
            	DataMigrationDao.insertNewFileData(prest, file_id,zip_line_data_AL);
            	
            	if (count==1000)
            	{
            		prest.executeBatch();
            		count=0;   	
            	}
                }
            
            prest.executeBatch();
            
            br.close();
            }
          prest.close();  
          System.out.println("Number of reasd lines issssssssss"+numReadLine);
          }
          }
            
    catch(Exception e)
		{
			e.printStackTrace();
		}
     
     
    
    System.out.println("current time a insert is "+new java.util.Date());
    return numReadLine;
  }

  public static String[] splitString(String line, int item_count, String split_str)
  {
	  String[] all_items = new String[item_count];
	  int last_visisted_comma= 0;
	  String line_item=null;
	  int line_length = line.length();
	  int last_index = line.lastIndexOf(split_str);
	  boolean done = false;
	  int counter = 0;
	  try{
  	while(last_visisted_comma<line_length && last_visisted_comma  != -1 )
  	{
  		if(last_visisted_comma != last_index && last_visisted_comma==0)
  			line_item = line.substring(last_visisted_comma, line.indexOf(split_str, last_visisted_comma+1));
  		else if(last_visisted_comma != last_index)	
	  		line_item = line.substring(last_visisted_comma+1, line.indexOf(split_str, last_visisted_comma+1));
	  	else
	  	{
	  		line_item = line.substring(last_visisted_comma+1, line_length);
	  		done = true;
	  	}
	  	last_visisted_comma = line.indexOf(split_str, last_visisted_comma+1);
	  	all_items[counter++] = line_item;
	  	if(done)
	  	{
	  		break;
	  	}
	 }
	  	//System.out.println("///////////////////////////////////////");
	    
  } catch(Exception e){System.out.println("dddddd: "+last_visisted_comma);e.printStackTrace();}
  return all_items;
}
  
  private static void displayArr(String[] arr)
  {
	  for(int i=0;i<arr.length;i++)
	  {
		  System.out.println("  "+arr[i]);
		  
	  }
  }
  
  public static void displayArrList(ArrayList al)
  {
	  for(int i=0;i<al.size();i++)
	  {
		  System.out.println("  "+al.get(i));
		  
	  }
  }
//  public static void main(String[] arr)
//  {
//	  getDMZIPData("Main_table.zip");
//  }



}

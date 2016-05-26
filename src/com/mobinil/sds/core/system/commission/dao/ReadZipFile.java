/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.commission.dao;

/**
 *
 * @author Ahmed Adel
 */
import com.mobinil.sds.core.utilities.Utility;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.String;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ReadZipFile {

    public  void readDataFromZipFile(String file_Path,String file_Id,Connection con)
    {
        int numReadLine=0;

         try 
    {	
    String sql= "INSERT INTO SDS.COMMISSION_RATED_ACTIVITY_DATA ("+
                    "RATED_FILE_ID, SUBSCRIPTION_ID, ACCESS_METHOD_DIAL,"+
                    "SIM_CARD_SERIAL_NUMBER, ACTIVATION_DATE, FIRST_RATED_ACTIVITY_DATE, "+
                    "REVENUE, DAYS, FIRST_CALL_CELL_CILL_ID, "+
                    "GOVERNRATE,District)"+
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?)" ;


      java.sql.PreparedStatement  prest = con.prepareStatement(sql) ;

      String exceptionSql=" INSERT INTO SDS.COMMISSION_RATED_REFUESD_DATA ("+
                    "RATED_FILE_ID, SUBSCRIPTION_ID, ACCESS_METHOD_DIAL,"+
                    "SIM_CARD_SERIAL_NUMBER, ACTIVATION_DATE, FIRST_RATED_ACTIVITY_DATE, "+
                    "REVENUE, DAYS, FIRST_CALL_CELL_CILL_ID, "+
                    "GOVERNRATE,District,LINE_NUMBER)"+
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)" ;
             java.sql.PreparedStatement exceptionprest = con.prepareStatement(exceptionSql);

             
	  System.out.println("current time b4 insert is "+new java.util.Date());
          System.out.print("FIE PATHHHHH"+file_Path);
          ZipFile zf = new ZipFile(file_Path);
	  Enumeration entries = zf.entries();

	 // numReadLine = new Integer(-1);
	  while (entries.hasMoreElements()) 
	  {
		  
		 
		
	        ZipEntry ze = (ZipEntry) entries.nextElement();
          long size = ze.getSize();
          if (size > 0) {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(zf.getInputStream(ze)));
            String line; 
            	int count= 0;

            while ((line = br.readLine()) != null) {
            	 numReadLine++;
                
             String[] columns = new String[10];
             String Subscription_Id="";
             String Access_Method_Dial="";
             String SIM_Card_Serial_Number="";
             String Activation_Dt="";
             String First_Rated_Activity_Dt="";
             String Revenue="";
             String days="";
             String First_Call_Cell_Id="";
             String Governorate="";
             String District="";
             int column=0;

              columns=line.split(",");
              if(column<=columns.length-1)
              Subscription_Id=columns[column];
              column++;
              if(column<=columns.length-1)
              Access_Method_Dial=columns[column];
              column++;
              if(column<=columns.length-1){
              if(columns[column].charAt(0)=='0')
              columns[column]=columns[column].replaceFirst("0"," ");
              if(columns[column].toLowerCase().endsWith("f")) {
              StringBuffer buf=new StringBuffer(columns[column]);
              buf.setCharAt(buf.length()-1,' ');
              columns[column]=buf.toString().trim();
              }
              SIM_Card_Serial_Number=columns[column];
              }
              column++;
              if(column<=columns.length-1)
              Activation_Dt=columns[column];
              column++;
              if(column<=columns.length-1)
              First_Rated_Activity_Dt=columns[column];
              column++;
              if(column<=columns.length-1)
              Revenue=columns[column];
              column++;
              if(column<=columns.length-1)
              days=columns[column];
              if(column<=columns.length-1)
              column++;
              if(column<=columns.length-1)
              First_Call_Cell_Id=columns[column];
              column++;
              if(column<=columns.length-1)
              Governorate=columns[column];
              column++;
              if(column<=columns.length-1)
              District=columns[column];

             if(count!=0){
             CommissionDAO.insertRatedFileData(file_Id,prest, Subscription_Id, Access_Method_Dial, SIM_Card_Serial_Number, Activation_Dt, First_Rated_Activity_Dt, Revenue, days, First_Call_Cell_Id, Governorate,District);

             try{

             prest.execute();
             
             }catch(Exception e){
             e.printStackTrace();
       
             CommissionDAO.insertErrorRatedFileData(file_Id,exceptionprest, Subscription_Id, Access_Method_Dial, SIM_Card_Serial_Number, Activation_Dt, First_Rated_Activity_Dt, Revenue, days, First_Call_Cell_Id, Governorate,District,numReadLine);
             exceptionprest.execute();

             
             }
             }
             count++;
             }

            br.close();

            }
          prest.close();
          exceptionprest.close();
          System.out.println("Number of reasd lines issssssssss"+numReadLine);
          }
          }
            
    catch(Exception e)
		{

                        
           
			e.printStackTrace();
		}
     
    
    }



}

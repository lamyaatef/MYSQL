/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlexportapp;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFontFormatting;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author sand
 */
public class MySQLExportApp {

   private static void doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
    {
        final JSch jsch = new JSch();
        Session session = jsch.getSession( strSshUser, strSshHost, 22 );
        session.setPassword( strSshPassword );

        final Properties config = new Properties();
        config.put( "StrictHostKeyChecking", "no" );
        session.setConfig( config );

        session.connect();
        session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
    }




    public static void dumpFile (String query , String fileName,Connection con)
    {
        
        try{
            Statement stat = con.createStatement();
            System.out.println("query : "+query);
            ResultSet res = stat.executeQuery(query);
            
            ResultSetMetaData rsMetaData = res.getMetaData();
            int count = rsMetaData.getColumnCount();
            //Workbook workbook = new SXSSFWorkbook(-1);
            //Workbook workbook = new XSSFWorkbook();
            Workbook workbook = new HSSFWorkbook();
            Sheet worksheet = workbook.createSheet("My Worksheet");
            Row rowhead=   worksheet.createRow((short)0);
            //ArrayList<Row> rows = new ArrayList<Row>();
            //ArrayList<ArrayList<Cell>> cells=new ArrayList<ArrayList<Cell>>();
            
            
            
            FileOutputStream fileOut = new FileOutputStream(fileName);
            
            /*for(int i=1; i<=count;i++){
                
                ArrayList<Cell> cell = new ArrayList<Cell>();
                cells.add(cell);
            }*/
            for(int header=0;header<count;header++){
            
                rowhead.createCell((short) header).setCellValue(rsMetaData.getColumnName(header+1));
                      
            }
            
            
            //int i=0;
            int i=0;
            while(res.next())
            {
             Row row=   worksheet.createRow((short)i);
             for(int k=0;k<count;k++)
                row.createCell((short) k).setCellValue(res.getString(k+1));
             /* rows.add(worksheet.createRow(i));
              
              for(int cellno=0;cellno<count;cellno++){
                    cells.get(cellno).add(rows.get(i).createCell((short) cellno));
                }
              
             
              
                            
             for (int j = 1 ; j <= count; j ++){
                    
                cells.get(j-1).get(i).setCellValue(res.getString(j));
                    
             }*/
              
            
              
              i++;
            }
       
    
           

            
          /* for(int header=0;header<count;header++){
                    cells.get(header).get(0).setCellValue(rsMetaData.getColumnName(header+1));
                      
                }*/
          
          
          workbook.write(fileOut);


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
/*private static String exportExcelSheetForSMSSendingData(Vector<ArrayList<String>> results,int cellCount,ResultSetMetaData resultMeta,String directionFile)
  {
      System.out.println("inside exportExcelSheetForSMSSendingData "+cellCount);
      FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(directionFile);
            System.out.println("before creating");
            //Workbook workbook = new SXSSFWorkbook(-1);
            //Workbook workbook = new XSSFWorkbook();
            Workbook workbook = new HSSFWorkbook();
            Sheet worksheet = workbook.createSheet("My Worksheet");
            ArrayList<Row> rows = new ArrayList<Row>();
            ArrayList<ArrayList<Cell>> cells=new ArrayList<ArrayList<Cell>>();
          System.out.println("results size "+results.size()+" and cellcount "+cellCount);
            
            for(int i=1; i<=cellCount;i++){
                
                ArrayList<Cell> cell = new ArrayList<Cell>();
                cells.add(cell);
            }
            
            for (int k=0;k<=results.size();k++){

                boolean isAdded = rows.add(worksheet.createRow(k));
                
                for(int cellno=0;cellno<cellCount;cellno++){
                    cells.get(cellno).add(rows.get(k).createCell((short) cellno));
                }
                System.out.println("row "+k+" isAdded? "+isAdded);

            }

            
            for(int header=0;header<cellCount;header++)
            {
                cells.get(header).get(0).setCellValue(resultMeta.getColumnName(header+1));
            }
            
            
            
            try {
                int lineIndex=0;
                    
                    for(int i=1;i<=results.size();i++)
                    {
                        for(int j=0;j<cellCount;j++)
                        {
                            String cellVal = results.get(i-1).toString();
                            System.out.println("cell value "+cellVal);
                          //  System.out.println(" Line Index : "+lineIndex);
                          //  cellVal = cellVal.substring(lineIndex,cellVal.indexOf(","));
                            
                           // lineIndex = cellVal.length();
                           // System.out.print(" lineIndex: "+lineIndex);
                            cells.get(j).get(i).setCellValue(cellVal);
                        }
                    }
             
                } 
            catch (Exception ex) {
                    System.out.println("ex: "+ex);
                }
            
        workbook.write(fileOut);
			//fileOut.flush();
			//fileOut.close();
        System.out.println("file name "+directionFile);
	return directionFile;

        } catch (FileNotFoundException e)
				{
					e.printStackTrace();
                                        System.out.println("file name empty "+e);
					return "";
				} catch (IOException e)
				{
					e.printStackTrace();
                                        System.out.println("file name empty "+e);
					return "";
				}
                                catch (SQLException e)
				{
					e.printStackTrace();
                                        System.out.println("file name empty "+e);
					return "";
				}
                                catch(Exception e)
                                {
                                    	e.printStackTrace();
                                        System.out.println("GENERAL file name empty "+e);
					return "";
                                }
  }*/

    
    
    public static void main(String[] args)
    {
        try
        {
            String strSshUser = "root";                  // SSH loging username
            String strSshPassword = "sandyroot";                   // SSH login password
            String strSshHost = "196.219.66.14";          // hostname or ip or SSH server
            int nSshPort = 60022;                                    // remote SSH host port number
            String strRemoteHost = "127.0.0.1/smssending";  // hostname or ip of your database server
            int nLocalPort = 3306;                                // local port number use to bind SSH tunnel
            int nRemotePort = 3306;                               // remote port number of your database
            String strDbUser = "lamya";                    // database loging username
            String strDbPassword = "lamya";                    // database login password

         //  BatchExtract.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.0.163:"+nLocalPort, strDbUser, strDbPassword);

            String query="select * from smssending.round2_arpu_0, smssending.smstext where smssending.round2_arpu_0.batchname='batch13-1' limit 300";
//select dial from smssending.round2_arpu_60   where batchname = 'batch29-1'            
//select * from smssending.round2_arpu_0, smssending.smstext where smssending.round2_arpu_0.batchname='batch13-1'

            
            dumpFile(query,"/home/sand/Downloads/batch.xlsx",con);

            con.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            System.exit(0);
        }
    }
    
}

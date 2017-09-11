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
public class SQLExportSDS {

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




    private void loopOnTables(Connection con, Connection con2){
        try{
        //gvms
        Statement stat = con.createStatement();
        //gvms2
        Statement stat2 = con2.createStatement();
        
        String query="select table_name from user_tables";
        ResultSet rs = stat.executeQuery(query);
        while(rs.next())
            {
                String tableName = rs.getString("table_name");
                //String query3="select * from gvms."+tableName;
                //ResultSet res = stat.executeQuery(query3);
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int count = rsMetaData.getColumnCount();
                //create table for database gvms2
                for(int k=0;k<count;k++)
                {
                    String columnName = rsMetaData.getColumnName(count);
                    String columnType = rsMetaData.getColumnTypeName(count);
                    String query1="create table gvms2."+tableName+" ( "+columnName+" "+columnType+" , "+")";
                    if(k==count-1)
                        query1="create table gvms2."+tableName+" ( "+columnName+" "+columnType+" )";
                    stat2.execute(query1);
                }
                    String query2="insert into gvms2."+tableName+"select * from gvms."+tableName;
                    stat2.execute(query2);
                
            }
            
            
        }catch(Exception e){System.out.println(e);}
        
    }
    
    
    
    
    public static void main(String[] args)
    {
        try
        {
            String strSshUser = "root";                  // SSH loging username
            String strSshPassword = "sandyroot";                   // SSH login password
            String strSshHost = "196.219.66.14";          // hostname or ip or SSH server
            int nSshPort = 60022;                                    // remote SSH host port number
            String strRemoteHost = "10.0.0.16/gvms";  // hostname or ip of your database server
            int nLocalPort = 3306;                                // local port number use to bind SSH tunnel
            int nRemotePort = 3306;                               // remote port number of your database
            String strDbUser = "gvms";                    // database loging username
            String strDbPassword = "gvms";                    // database login password

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.0.0.16:1521:gvms",strDbUser,strDbPassword);
            Connection con2 = DriverManager.getConnection("jdbc:oracle:thin:@10.0.0.16:1521:gvms2",strDbUser,strDbPassword);
            
            
            
    


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

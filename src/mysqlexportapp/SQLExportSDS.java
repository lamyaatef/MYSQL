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
import java.util.*;


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




    private static void loadSDSTables(Connection con, Connection con2){
        try{
            System.out.println("con "+con.isValid(0));
            System.out.println("con2 "+con2.isValid(0));
        //database: gvms at 10.0.0.16
        Statement stat = con.createStatement();
        Statement innerStat = con.createStatement();
        //database: sds2 at 10.0.0.77
        Statement stat2 = con2.createStatement();
        
        String query="select table_name from user_tables";
        ResultSet rs = stat.executeQuery(query);
        System.out.println("get tables: "+query);
        
        while(rs.next())
            {
            
                String tableName = rs.getString("table_name");
                String columnName = "";
                String columnType = "";       
                
                String columns="select * from "+tableName;
                System.out.println("get columns: "+columns);
                Statement columnsStat = con.createStatement();
                ResultSet rsColumns = columnsStat.executeQuery(columns);
        
                
                ResultSetMetaData rsMetaData = rsColumns.getMetaData();
                int count = rsMetaData.getColumnCount();
                System.out.println("count of table columns: "+tableName+" : "+count);
                
                String checkTable = "select * from sds."+tableName;
                try
                {
                boolean exists = con2.createStatement().execute(checkTable);
                if(exists)
                {
                    String dropQuery="drop table sds."+tableName;
                    System.out.println("query0-drop "+dropQuery);
                    con2.createStatement().execute(dropQuery);
                
                }
                }catch(SQLException ex){System.out.println(ex);}
                
                String query1="create table sds."+tableName+" ( ";
                
                for(int k=1;k<=count;k++)
                {
                    columnName = rsMetaData.getColumnName(k);
                    columnType = rsMetaData.getColumnTypeName(k);
                    System.out.println("table name "+tableName+" column name "+columnName+" column type "+columnType);
                    if (columnType.contains("VARCHAR"))
                            columnType += "(1000)";
                    query1+=columnName+" "+columnType;
                    if(k==count)
                        query1+=" ) ";
                    else
                        query1+=" , ";
                    
                    
                    
                }
                System.out.println("query1 "+query1);
                stat2.execute(query1);
                
                String query2 = "select * from gvms."+tableName+" where rownum<=1000";
                System.out.println("query2 "+query2);
                ResultSet data = innerStat.executeQuery(query2);
                ResultSetMetaData metaData = data.getMetaData();
                String query3 = "";
                int totalColumns = metaData.getColumnCount();
                System.out.println("total columns "+totalColumns);
                int dataCount = 1;
                int recordNo = 1;
                String st="";
                boolean hasData=false;
                while(data.next())
                {
                    System.out.println("record no "+recordNo);
                    dataCount = 1;
                    query3 = "insert into sds."+tableName+" values( ";
                    while(dataCount<=totalColumns)
                    {
                            System.out.println("index: "+dataCount);
                            hasData=true;
                            String dateType= metaData.getColumnTypeName(dataCount);
                            System.out.println("date type "+dateType);
                            if (dateType.compareToIgnoreCase("DATE")==0 || dateType.compareToIgnoreCase("TIMESTAMP")==0)
                            {
                                if (st==null || st.compareToIgnoreCase("null")==0)
                                    query3 +=st;
                                else
                                {
                                    String dateSt = data.getString(metaData.getColumnName(dataCount));
                                    System.out.println("date st "+dateSt);
                                    
                                    if (dateSt==null || dateSt.compareToIgnoreCase("null")==0)
                                    {
                                        query3 +=null;
                                    }
                                    else 
                                    {
                                        
                                        if (dateSt!=null && dateSt.contains(" "))
                                            dateSt = dateSt.substring(0, dateSt.indexOf(" "));
                                    System.out.println("date st After "+dateSt);
                                    st = "to_date('"+dateSt+"','yyyy-mm-dd')";
                                    query3 +=st;
                                    }
                                }

                            }
                            else if (dateType.compareToIgnoreCase("BLOB")==0)
                            {
                                st = "null";
                                query3+=st;
                            }
                            
                            else if (dateType.compareToIgnoreCase("NUMBER")==0)
                            {
                               st = data.getString(metaData.getColumnName(dataCount));
                               System.out.println("ST in Number "+st);
                               if (st==null || st.compareToIgnoreCase("null")==0)
                                    query3 +="0";
                               else query3+=st;
                               
                            }
                            
                            else
                            {
                                st = data.getString(metaData.getColumnName(dataCount));
                                System.out.println("st "+st);
                                if (st!=null && st.contains(" "))
                                    st = st.trim();
                                query3 +="'"+st+"'";
                            }


                            if(dataCount==count)
                            {
                                query3+=" ) ";
        
                            }

                            else
                                query3+=" , ";
                            System.out.println("query3: "+query3);
                            dataCount++;
                    }
                    if(hasData)
                        stat2.execute(query3);
                    recordNo++;
                }
       
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
            int nRemotePort = 3306; 
            // remote port number of your database
            String strDbUser = "gvms";                    // database loging username
            String strDbPassword = "gvms";                    // database login password

            String strDbUser2 = "sds";                    // database loging username
            String strDbPassword2 = "sds";                    // database login password

            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //SID : GVMS
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.0.0.16:1521:gvms",strDbUser,strDbPassword);
           // Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.11.113.80:1521:sds",strDbUser2,strDbPassword2);
            System.out.println("get con "+con);
            //SID : SDS
            Connection con2 = DriverManager.getConnection("jdbc:oracle:thin:@10.0.0.77:1521:sds",strDbUser2,strDbPassword2);
            System.out.println("get con2 "+con2);
            
            loadSDSTables(con,con2);
            


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

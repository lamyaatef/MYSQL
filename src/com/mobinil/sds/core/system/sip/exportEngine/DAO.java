package com.mobinil.sds.core.system.sip.exportEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import com.mobinil.sds.core.system.dcm.genericModel.GenericModel;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.GenericModelDAO;
import com.mobinil.sds.core.system.sip.dao.SipDAO;
import com.mobinil.sds.core.system.sip.dto.sipCSVArpuResult;
import com.mobinil.sds.core.system.sip.dto.sipDistHistoryCSVDTO;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.sip.SIPInterfaceKey;

public class DAO
{
   
   static HashMap years = new HashMap();
   
   static HashMap<String,String> quriesHashMap = new HashMap<String,String>();

   public static String getTotalGrossAddsACT(Connection con, String year_id,
         String quartar, String dcm_id, HashMap years,HashMap quiries) throws SQLException
   {
      Integer preAmount = 0;
      Integer postAmount = 0;
      String dateCondition = "";
      String sqlPre = (String)quiries.get ( "2" );
      String sqlPost =(String) quiries.get ( "3" );
      
      if ( quartar.compareTo ( "1" ) == 0 )
         dateCondition = " activation_date between to_Date('1/1/"
               + years.get ( year_id ) + "' ,'dd/mm/yyyy') and (to_date ('1/4/"
               + years.get ( year_id ) + "','dd/mm/yyyy')-1)";
      if ( quartar.compareTo ( "2" ) == 0 )
         dateCondition = " activation_date between to_Date('1/4/"
               + years.get ( year_id ) + "' ,'dd/mm/yyyy') and (to_date ('1/7/"
               + years.get ( year_id ) + "','dd/mm/yyyy')-1)";
      if ( quartar.compareTo ( "3" ) == 0 )
         dateCondition = " activation_date between to_Date('1/7/"
               + years.get ( year_id )
               + "' ,'dd/mm/yyyy') and (to_date ('1/10/" + years.get ( year_id )
               + "','dd/mm/yyyy')-1)";
      if ( quartar.compareTo ( "4" ) == 0 )
         dateCondition = " activation_date between to_Date('1/10/"
               + years.get ( year_id )
               + "' ,'dd/mm/yyyy') and (to_date ('31/12/"
               + years.get ( year_id ) + "','dd/mm/yyyy'))";
      
      if (sqlPre!=null)
      {
      if (sqlPre.contains ( "#1" ))sqlPre = sqlPre.replace ( "#1" , dateCondition );
      if (sqlPre.contains ( "#2" ))sqlPre = sqlPre.replace ( "#2" , dcm_id );
      }
      
      ///*Test*/sqlPre = new StringBuffer("select count(*) from dem_logistics_prepaid where activation_date between to_Date('1/7/2009' ,'dd/mm/yyyy') and (to_date ('1/8/2009','dd/mm/yyyy')-1) group by dcm_id");
      
      System.out.println ( "sqlPre isssss " + sqlPre );
      System.out.println ( "sqlPost isssss " + sqlPost );
      
      
      
      Statement st = con.createStatement ( );
      ResultSet rs = st.executeQuery ( sqlPre );
      if ( rs.next ( ) ) preAmount = rs.getInt ( 1 );
      
      
      rs.close ( );
      st.close ( );
      rs = null;
      st=null;
      
      if (sqlPost!=null)
      {
      if (sqlPost.contains ( "#1" ))sqlPost = sqlPost.replace ( "#1" , dateCondition );
      if (sqlPost.contains ( "#2" ))sqlPost = sqlPost.replace ( "#2" , dcm_id );
      }
      ///*Test*/sqlPost = new StringBuffer("select count(*) from dem_logistics_postpaid where activation_date between to_Date('1/7/2009' ,'dd/mm/yyyy') and (to_date ('1/8/2009','dd/mm/yyyy')-1) group by dcm_id");
      
      
      st = con.createStatement ( );
      rs = st.executeQuery ( sqlPost );
      
      if ( rs.next ( ) ) postAmount = rs.getInt ( 1 );
      
      if (postAmount==null)postAmount=0;
      if (preAmount==null)preAmount=0;
      
      System.out.println ( "sqlPre isssss " + sqlPre );
      System.out.println ( "sqlPost isssss " + sqlPost );
      System.out.println ( "postAmount isssss " + postAmount );
      System.out.println ( "preAmount isssss " + preAmount );
      System.out.println ( "tatal amount isssss " + (preAmount+postAmount) );
      
      rs.close ( );
      st.close ( );
      return (preAmount+postAmount)+"";
      
   }
   
   
   public static ArrayList<String> getDetailedGrossAddsACT(Connection con, String year_id,
         String quartar, String dcm_id, HashMap years,HashMap quiries) throws SQLException
   {
      ArrayList<String> sumOfAct = new ArrayList<String>(3);
      sumOfAct.add ( 0 , "0" );
      sumOfAct.add ( 1 , "0" );
      sumOfAct.add ( 2 , "0" );
      
      ArrayList<Integer> sumOfActPre = new ArrayList<Integer>(3);
      sumOfActPre.add ( 0 , 0 );
      sumOfActPre.add ( 1 , 0 );
      sumOfActPre.add ( 2 , 0 );
      
      ArrayList<Integer> sumOfActPost = new ArrayList<Integer>(3);
      sumOfActPost.add ( 0 , 0 );
      sumOfActPost.add ( 1 , 0 );
      sumOfActPost.add ( 2 , 0 );
      
      
      String dateCondition = "";
      String sqlPre = (String)quiries.get ( "2" );
      String sqlPost =(String) quiries.get ( "3" );    
      
      
      if ( quartar.compareTo ( "1" ) == 0 )
         dateCondition = " activation_date between to_Date('1/"
               + years.get ( year_id ) + "' ,'mm/yyyy') and (to_date ('4/"
               + years.get ( year_id ) + "','mm/yyyy')-1)";
      
      if ( quartar.compareTo ( "2" ) == 0 )
         dateCondition = " activation_date between to_Date('4/"
               + years.get ( year_id ) + "' ,'mm/yyyy') and (to_date ('7/"
               + years.get ( year_id ) + "','mm/yyyy')-1)";
      
      if ( quartar.compareTo ( "3" ) == 0 )
         dateCondition = " activation_date between to_Date('7/"
               + years.get ( year_id ) + "' ,'mm/yyyy') and (to_date ('10/"
               + years.get ( year_id ) + "','mm/yyyy')-1)";
      
      if ( quartar.compareTo ( "4" ) == 0 )
         dateCondition = " activation_date between to_Date('10/"
               + years.get ( year_id ) + "' ,'mm/yyyy') and (to_date ('31/12/"
               + years.get ( year_id ) + "','dd/mm/yyyy'))";
      
      
      if (sqlPre!=null)
      {
      if (sqlPre.contains ( "#1" ))sqlPre = sqlPre.replace ( "#1" , dateCondition );
      if (sqlPre.contains ( "#2" ))sqlPre = sqlPre.replace ( "#2" , dcm_id );
      }
      
      sqlPre = sqlPre+",to_char(activation_date ,'mm/yyyy')";

      if (sqlPost!=null)
      {
      if (sqlPost.contains ( "#1" ))sqlPost = sqlPost.replace ( "#1" , dateCondition );
      if (sqlPost.contains ( "#2" ))sqlPost = sqlPost.replace ( "#2" , dcm_id );
      }
      sqlPost = sqlPost+",to_char(activation_date ,'mm/yyyy')";
      
      System.out.println ( "sqlPre detail pre Act isssss " + sqlPre );
      System.out.println ( "sqlPost detail post Act isssss " + sqlPost );
      
      
      
      Statement stPre = con.createStatement ( );
      ResultSet rsPre = stPre.executeQuery ( sqlPre.toString ( ) );
      
      Statement stPost = con.createStatement ( );
      ResultSet rsPost = stPost.executeQuery ( sqlPost.toString ( ) );
      
      int counter =0;
      while (rsPre.next ( ))
      {
         sumOfActPre.set ( counter , rsPre.getInt ( 1 ) );
         counter++;
      }
      
      counter =0;
      while (rsPost.next ( ))
      {
         sumOfActPost.set ( counter , rsPost.getInt ( 1 ) );
         counter++;
      }
      
      Integer intActPre=0;
      Integer intActPost=0;
      Integer Total=0;
      for ( int i = 0 ; i < 3 ; i ++ )
      {
          intActPre = sumOfActPre.get ( i );
         intActPost = sumOfActPost.get ( i );
         Total = intActPre+intActPost;
         sumOfAct.set ( i , String.valueOf (Total));
      }
      
      rsPre.close ( );
      stPre.close ( );
      rsPost.close ( );
      stPost.close ( );
      return sumOfAct;
      
   }
   
   public static ArrayList<String> getGenericDataFromSQL (Connection con,String sql,int numberOfCells,String [] data,int columnNumberToBeCatch) throws SQLException{
      ArrayList<String> array = new ArrayList<String>(numberOfCells);      
      int numberOfQuestionMarks = sql.split( "\\?" ).length-1;      
      int counter = 0;
      
      System.out.println ("numberOfQuestionMarks is "+numberOfQuestionMarks);
      for ( int i = 0 ; i < numberOfCells ; i ++ )
      {
         array.add ( i,"0" );
      }
      System.out.println ("array size is "+array.size ( ));
      
      System.out.println ("data size is "+data.length);
      
      PreparedStatement ps = null;
      
      ps = con.prepareStatement ( sql );
      
      for ( int i = 1 ; i <= numberOfQuestionMarks ; i ++ )
      {
         System.out.println ("here in set param i "+i+" data of i is "+data[i-1]);
         ps.setString ( i , data[i-1] );
      }
      System.out.println ("sql is "+sql);
      
      
      ResultSet rs = ps.executeQuery ( );
      
      
      while (rs.next ( )){
         System.out.println ("rs.getObject ( columnNumberToBeCatch ) ) is "+rs.getObject ( columnNumberToBeCatch ) );
         array.set ( counter , String.valueOf ( rs.getObject ( columnNumberToBeCatch ) ) );
         counter++;
      }
      
      
      ps.close ( );
      rs.close ( );
      
      
      
      
      return array; 
      
   }
   
   public static ArrayList<String> getGenericDataFromSQLHash (Connection con,String sql,int numberOfCells,String [] data,int columnNumberToBeCatch) throws SQLException{
      ArrayList<String> array = new ArrayList<String>(numberOfCells);      
      int numberOfQuestionMarks = sql.split( "#" ).length-1;      
      int counter = 0;
      
      System.out.println ("numberOfQuestionMarks is "+numberOfQuestionMarks);
      for ( int i = 0 ; i < numberOfCells ; i ++ )
      {
         array.add ( i,"0" );
      }
      System.out.println ("array size is "+array.size ( ));
      
      System.out.println ("data size is "+data.length);
      
      
      
      Statement st = con.createStatement(  );
      
      for ( int i = 1 ; i <= numberOfQuestionMarks ; i ++ )
      {
         System.out.println ("here in set param i "+i+" data of i is "+data[i-1]);
         sql = sql.replace ( "#"+i , data[i-1] );
         
      }
      System.out.println ("sql is "+sql);
      
      
      ResultSet rs = st.executeQuery ( sql );
      
      
      while (rs.next ( )){
         System.out.println ("rs.getObject ( columnNumberToBeCatch ) ) is "+rs.getObject ( columnNumberToBeCatch ) );
         array.set ( counter , String.valueOf ( rs.getObject ( columnNumberToBeCatch ) ) );
         counter++;
      }
      
      
      st.close ( );
      rs.close ( );
      
      
      
      
      return array; 
      
   }
   public static void saveRowForDistReport(Connection con,com.mobinil.sds.core.system.sip.dto.sipDistHistoryDTO sipdh){
      
      String sql = prepareSQLString(sipdh);
      DBUtil.executeSQL ( sql , con );
      
   }
   
   public static String prepareSQLString(com.mobinil.sds.core.system.sip.dto.sipDistHistoryDTO sipdh){
      StringBuffer sql = new StringBuffer("insert into SIP_DIST_HISTORY (");
      sql.append ( "DCM_ID, SIP_REPORT_ID,DCM_NAME, DCM_COUNT, DORMANT_COUNT, EXECUTIVE_NAME, SCRATCH, E_TOPUP,");
      sql.append ( " GROSSADDTOTAL, EXPOSTOTAL, NONEXPOSTOTAL, TOTALGROSSADDSDETAIL , ");
      sql.append ( " REVENUESCRATCHDETIAL, REVENUEETOPUPDETAIL, GROSSADDSDETAIL, " );
      sql.append ( "DORMANTDETAIL, DETAILEDGROSSADDSACT, DETAILEDEXPOSANDNONEXPOS) " );
      sql.append ( " Values (" );
      sql.append ( "'" );
      sql.append (sipdh.getDcmId ( ));
      sql.append ( "','" );
      sql.append (sipdh.getSipReportId ( ));
      sql.append ( "','" );
      sql.append (sipdh.getDcmName ( ));
      sql.append ( "','" );
      sql.append (sipdh.getDcmCount ( ));
      sql.append ( "','" );
      sql.append (sipdh.getDormantCount ( ));
      sql.append ( "','" );
      sql.append (sipdh.getExectivesName ( ));
      sql.append ( "','" );
      sql.append (sipdh.getScratch ( ));
      sql.append ( "','" );
      sql.append (sipdh.getE_Topup ( ));
      sql.append ( "','" );
      sql.append (sipdh.getGrossAddTotal ( ));
      sql.append ( "','" );
      sql.append (sipdh.getExPosTotal ( ));      
      sql.append ( "','" );
      sql.append (sipdh.getNonExPosTotal ( ));
      sql.append ( "','" );
      sql.append (sipdh.getTotalGrossAddsDetail ( ));
      sql.append ( "','" );
      
      sql.append (sipdh.getRevenueTargetDetail ( ).get ( 0 ));
      sql.append ( "," );
      sql.append (sipdh.getRevenueTargetDetail ( ).get ( 1 ));
      sql.append ( "," );
      sql.append (sipdh.getRevenueTargetDetail ( ).get ( 2 ));
      sql.append ( "','" );
      
      sql.append (sipdh.getRevenueTargetDetail ( ).get ( 3 ));
      sql.append ( "," );
      sql.append (sipdh.getRevenueTargetDetail ( ).get ( 4 ));
      sql.append ( "," );
      sql.append (sipdh.getRevenueTargetDetail ( ).get ( 5 ));
      sql.append ( "','" );
      
      sql.append (sipdh.getGrossAddsDetail ( ).get ( 0 ));
      sql.append ( "," );
      sql.append (sipdh.getGrossAddsDetail ( ).get ( 1 ));
      sql.append ( "," );
      sql.append (sipdh.getGrossAddsDetail ( ).get ( 2 ));
      sql.append ( "','" );
      
      sql.append (sipdh.getDormantDetail ( ).get ( 0 ));
      sql.append ( "," );
      sql.append (sipdh.getDormantDetail ( ).get ( 1 ));
      sql.append ( "," );
      sql.append (sipdh.getDormantDetail ( ).get ( 2 ));
      sql.append ( "','" );
      
      sql.append (sipdh.getDetailedGrossAddsACT ( ).get ( 0 ));
      sql.append ( "," );
      sql.append (sipdh.getDetailedGrossAddsACT ( ).get ( 1 ));
      sql.append ( "," );
      sql.append (sipdh.getDetailedGrossAddsACT ( ).get ( 2 ));
      sql.append ( "','" );
      
      
      sql.append (sipdh.getDetailedExPOSAndNonExPOS ( ).get ( 0 ));
      sql.append ( "," );
      sql.append (sipdh.getDetailedExPOSAndNonExPOS ( ).get ( 1 ));
      sql.append ( "," );
      sql.append (sipdh.getDetailedExPOSAndNonExPOS ( ).get ( 2 ));      
      sql.append ( "," );
      sql.append (sipdh.getDetailedExPOSAndNonExPOS ( ).get ( 3 ));
      sql.append ( "," );
      sql.append (sipdh.getDetailedExPOSAndNonExPOS ( ).get ( 4 ));
      sql.append ( "," );
      sql.append (sipdh.getDetailedExPOSAndNonExPOS ( ).get ( 5 ));
      
      sql.append ( "')" );
      
      

      
      
      
      return sql.toString ( );
   }
   
   
   
   
   public static void distributorsCSVReport(
         Connection con,String report_id,String emp_id,String dcmId,Vector<String> vec,String labelId) throws SQLException     
   {     
       
     //hagry //this query is based on nonesence assumptions 
       //(select Field1 from SIP_UPLOADED_LISTS where rownum=1 and Field2=(select dcm_code from gen_dcm where dcm_id='"+
       //dcmId+"'))
       //how does this specify which list you are using
       
     
       
       
     
       
       String sql = "INSERT INTO SIP_DIST_HISTORY_CSV ( EMP_ID, CHANNEL_ID, MODE_ID, LINE_ID, KPI_ID, KPI_VALUE,"+
       "TRANSACTION_DATE, SIP_REPORT_ID ) VALUES ( ?, 10, " +
            " '1', '1', ?, ?,  last_day(to_date(?,'mm/yyyy')), '"+report_id+"')";
      
      System.out.println ("sql csv history is "+sql);
      PreparedStatement ps = con.prepareStatement ( sql );
      String [] ids = null;
      String reportTypestr = "";
      String reportYear = "";
      String reportMonth = "";
      
      if (vec==null)
          vec = new Vector<String>();
      
      for(int j=0; j<vec.size(); j++)
      {
        String tempString = vec.get ( j );  
        
      
           ids = tempString.split ( "_" );
           String empID = ids[0];
           reportTypestr = ids[1];           
           reportYear = ids[3];
           reportMonth = ids[4];
           
           int reportM  = Integer.parseInt(reportMonth);
           reportM+=3;
           
           
           String dataDCMId = ids[2];
           
           if (dataDCMId.compareTo(dcmId)!=0)
               continue;
           
           if (reportTypestr.compareTo ( "3" )==0)reportTypestr = "6";
           
           ps.setString(1, empID);
           ps.setString ( 2 , reportTypestr );
           ps.setString ( 3 , ids[5] );
           ps.setString ( 4 , reportM+"/"+years.get ( reportYear ) );
           ps.execute ( ); 
           
           
      }
      
      ps.close ( );
      insertQuerySettingsKPI(con,report_id,sql,dcmId,labelId);
      
   }
   public static void insertQuerySettingsKPI (Connection con,String report_id,String sql,String dcmid,String labelId) throws SQLException{
           
      
      
      String kpiSql = "select KPI_ID,SIP_REPORT_QUARTER_ID,SIP_REPORT_YEAR from SIP_KPI_METRIC,SIP_REPORT_DETAIL where KPI_ID not in (1,2,3,6) and SIP_REPORT_DETAIL_ID='"+report_id+"'";
      PreparedStatement ps = con.prepareStatement ( sql );
      Statement st = con.createStatement ( );
      ResultSet rs = st.executeQuery ( kpiSql );
      String kpi_id = "";
      String quartar_id="";
      String year_id="";
      HashMap <String,sipCSVArpuResult> allDCMSArpuHashMap = null;
      HashMap <String,String> allEXposHashMap = null ;
      HashMap <String,String> allDormantHashMap = null ;
      String monthsArray [] = null;
      boolean isAllDCMArpuPrepared = false;
      while (rs.next ( ))
      {
         ps.clearParameters ( );
         kpi_id =rs.getString ( "KPI_ID" );
         
         ps.setString ( 1 , kpi_id );
          if (!isAllDCMArpuPrepared)
             {
             quartar_id = rs.getString ( "SIP_REPORT_QUARTER_ID" );
             year_id = rs.getString ( "SIP_REPORT_YEAR" );
             
             allDormantHashMap = getDormantListHashMap(con,labelId,year_id,quartar_id);
             allEXposHashMap =getExPOSListHashMap(con);
             allDCMSArpuHashMap = getAllDCMSArpu(con,quartar_id,year_id);             
             
             
             
             if ( quartar_id.compareTo ( "1" ) == 0 )
                monthsArray = SIPInterfaceKey.SIP_REPORT_QUARTAR_1.split ( "," );
             if ( quartar_id.compareTo ( "2" ) == 0 )
                monthsArray = SIPInterfaceKey.SIP_REPORT_QUARTAR_2.split ( "," );;
             if ( quartar_id.compareTo ( "3" ) == 0 )
                monthsArray = SIPInterfaceKey.SIP_REPORT_QUARTAR_3.split ( "," );;
             if ( quartar_id.compareTo ( "4" ) == 0 )
                monthsArray = SIPInterfaceKey.SIP_REPORT_QUARTAR_4.split ( "," );;
             isAllDCMArpuPrepared=true;            
             }
         ////////////////////////
          switchKPICase ( ps , quartar_id , year_id , dcmid , kpi_id , monthsArray , allDCMSArpuHashMap , allEXposHashMap , allDormantHashMap );
         
      }
      
       ps.close ( );
      st.close ( ); 
      rs.close ( );
      
   }
   
   
   public static void switchKPICase(PreparedStatement ps,String quartar_id, String year_id,String dcmid,String kpi_id,String [] monthsArray,HashMap <String,sipCSVArpuResult> allDCMSArpuHashMap,HashMap <String,String> allEXposHashMap,HashMap <String,String> allDormantHashMap) throws SQLException{
      
      System.out.println ("case number is "+kpi_id);
      
      switch ( Integer.valueOf ( kpi_id ) )
      {
         case 4 :
         {
            String exPosStr = "";
            for ( int i = 0 ; i < monthsArray.length ; i ++ )
            {
               
                if (allEXposHashMap == null)
                    continue; 
                
               for ( int j = 0 ; j < allEXposHashMap.size ( ) ; j ++ )
               {
                  exPosStr = (String)allEXposHashMap.keySet ( ).toArray ( )[j];
                  if (exPosStr.startsWith ( dcmid ))
                  {
                     
                     executeStatment(ps,((String)allEXposHashMap.get ( exPosStr )).split ( "_" )[0],monthsArray[i]+"/"+years.get ( year_id ));
                  }
                  
               }
               
            }
            
         }  
         break;
         case 5 :
         {  
            String exPosStr = "";
            for ( int i = 0 ; i < monthsArray.length ; i ++ )
            {
               
               
               if (allEXposHashMap==null)
                   return; 
               
               for ( int j = 0 ; j < allEXposHashMap.size ( ) ; j ++ )
               {
                  exPosStr = (String)allEXposHashMap.keySet ( ).toArray ( )[j];
                  if (exPosStr.startsWith ( dcmid ))
                  {
                     
                     executeStatment(ps,((String)allEXposHashMap.get ( exPosStr )).split ( "_" )[1],monthsArray[i]+"/"+years.get ( year_id ));
                  }
                  
               }
               
//               exPosStr = dcmid+"_"+year_id+"_"+monthsArray[i]+"_2";
//               
//               if (allEXposHashMap.get ( exPosStr )!=null)
//               {
//                  executeStatment(ps,(String)allEXposHashMap.get ( exPosStr ),monthsArray[i]+"/"+years.get ( year_id ));
//               }
//               else executeStatment(ps,"0",monthsArray[i]+"/"+years.get ( year_id ));
            }
         }  
         break;
         
         case 7 :
         {
            String dates [] =formatQuartarMonthly(quartar_id,year_id);
            String arpuCount = "";
            if (allDCMSArpuHashMap!=null && allDCMSArpuHashMap.get(dcmid)!=null){
            
             arpuCount = allDCMSArpuHashMap.get ( dcmid ).getCount ( )==null?"0":allDCMSArpuHashMap.get ( dcmid ).getCount ( )+"";            
            }
            else arpuCount="0";
               
               
              try
              {
               for ( int i = 0 ; i < dates.length ; i ++ )
               {
                  executeStatment(ps,arpuCount,dates[i]);   
               }
              }
              catch(Exception e)
              {
                  e.printStackTrace();
              }
         }
         break;
            
         case 8 :
         {
            String dates [] =formatQuartarMonthly(quartar_id,year_id);
            String arpuCount = "";
            if (allDCMSArpuHashMap!=null && allDCMSArpuHashMap.get(dcmid)!=null){
            arpuCount = allDCMSArpuHashMap.get ( dcmid ).getArpu ( )==null?"0":allDCMSArpuHashMap.get ( dcmid ).getArpu ( )+"";            
            }
            else arpuCount="0";
               
               try{
               for ( int i = 0 ; i < dates.length ; i ++ )
               {
                  executeStatment(ps,arpuCount,dates[i]);   
               } 
               }catch(Exception e)
               {
                   e.printStackTrace();
               }
            
         }  
         break;
         
         case 9 :
         {
            String dormantStr = "";
            for ( int i = 0 ; i < monthsArray.length ; i ++ )
            {
               dormantStr = dcmid+"_"+monthsArray[i];
               
               if (allDormantHashMap == null) continue;
               
               if (allDormantHashMap.get ( dormantStr )!=null)
               {                 
                  executeStatment(ps,(String)allDormantHashMap.get ( dormantStr ),monthsArray[i]+"/"+years.get ( year_id ));
               }
               else executeStatment(ps,"0",monthsArray[i]+"/"+years.get ( year_id ));
            }
         }  
         break;
         
         
         
         default :
         break;
      }
      
   }
   
   
   public static HashMap<String,String> getDormantListHashMap (Connection con,String labelIs,String year_id,String quartarId) throws SQLException{
      HashMap<String,String> hm = null;
      String sql = (String)quriesHashMap.get ( "30" );
      PreparedStatement ps = con.prepareStatement ( sql );
      ps.setString ( 1 , labelIs );
      ps.setString ( 2 , year_id );
      ps.setString ( 3 , quartarId );
      
      
      ResultSet rs = ps.executeQuery (  );
      while (rs.next ( ))
      {
         if (hm==null)hm =new HashMap<String,String>();
         
         
         
         
         hm.put ( rs.getString ( "HASHMAPKEY" ) , rs.getString ( "DORMANTCOUNT" ) );
         
      }
      
      ps.close ( );
      rs.close ( );
      return hm;
      
   } 
   
   public static HashMap<String,String> getExPOSListHashMap (Connection con) throws SQLException{
      HashMap<String,String> hm = null;
      String sql = "SELECT field1 HASHMAPKEY,Field2 POS_COUNT_EX, Field3 POS_COUNT_NON FROM SIP_UPLOADED_LISTS where FILE_TYPE_ID=6";
      Statement st = con.createStatement ( );
      ResultSet rs = st.executeQuery ( sql );
      while (rs.next ( ))
      {
         if (hm==null)hm =new HashMap<String,String>();
         
         hm.put ( rs.getString ( "HASHMAPKEY" ) , rs.getString ( "POS_COUNT_EX" )+"_"+rs.getString ( "POS_COUNT_NON" ) );
         
      }
      
      st.close ( );
      rs.close ( );
      return hm;
      
   } 
   
   public static void executeStatment(PreparedStatement ps , String count,String tranactionDate) throws SQLException{
      
      
      ps.setString ( 2 , count );
      ps.setString ( 3 , tranactionDate );
      ps.execute ( ); 
   }
   
   public static HashMap<String,sipCSVArpuResult> getAllDCMSArpu (Connection con,String quartar,String year_id) throws SQLException{
   
   HashMap<String,sipCSVArpuResult> hmpre = new HashMap<String,sipCSVArpuResult> ();
   HashMap<String,sipCSVArpuResult> hmpost = new HashMap<String,sipCSVArpuResult> ();
   HashMap<String,sipCSVArpuResult> hmtotal = new HashMap<String,sipCSVArpuResult> ();
   
   String fromDate="";
   String toDate = "";
   String startEndDate [] = formatQuartar ( quartar , year_id );
   fromDate= startEndDate[0];
   toDate= startEndDate[1];
   String sqlpre = (String)quriesHashMap.get ( "29" );
   String sqlpost= (String)quriesHashMap.get ( "28" );
   
   
   sipCSVArpuResult csvResult = null;
   
   
      PreparedStatement st = con.prepareStatement ( sqlpre );
//      PreparedStatement st1 = con.prepareStatement ( sqlpost );
      
      
      
      st.setString ( 1 , fromDate );
      st.setString ( 2 , toDate );
      
  //    st1.setString ( 1 , fromDate );
  //    st1.setString ( 2 , toDate );
      
      ResultSet rs = st.executeQuery (  );
    //  ResultSet rs1 = st1.executeQuery (  );
      
      while (rs.next ( ))
      {
         csvResult = new sipCSVArpuResult();
         csvResult.setArpu ( rs.getInt("ARPU") );
         csvResult.setCount ( rs.getInt("COUNT") );
         hmpre.put ( rs.getString ( "DCM_ID" ) , csvResult );
      }
      /*
      while (rs1.next ( ))
      {
         csvResult = new sipCSVArpuResult();
         csvResult.setArpu ( rs1.getInt("ARPU") );
         csvResult.setCount ( rs1.getInt("COUNT") );
         hmpost.put ( rs.getString ( "DCM_ID" ) , csvResult );         
         
      }
      */
      
      for ( int i = 0 ; i < hmpre.size ( ) ; i ++ )
      {
       String dcmIdPre =(String) hmpre.keySet().toArray()[i];
       for ( int j = 0 ; j < hmpost.size() ; j ++ )
       {
          if (hmpost.get ( dcmIdPre )!=null)
          {
             csvResult = new sipCSVArpuResult();
             Integer totalArpuRevenu = hmpre.get ( dcmIdPre ).getArpu ( )+hmpost.get ( dcmIdPre ).getArpu ( );
             Integer totalCount = hmpre.get ( dcmIdPre ).getCount ( )+hmpost.get ( dcmIdPre ).getCount ( );
             csvResult.setArpu ( totalArpuRevenu );
             csvResult.setCount ( totalCount );
             hmtotal.put ( dcmIdPre , csvResult );
          }
       }
      }
      for ( int i = 0 ; i < hmpost.size ( ) ; i ++ )
      {
       String dcmIdpost =(String) hmpost.keySet().toArray()[i];
       for ( int j = 0 ; j < hmpre.size() ; j ++ )
       {
          if (hmpre.get ( dcmIdpost )!=null)
          {
             csvResult = new sipCSVArpuResult();
             Integer totalArpuRevenu = hmpre.get ( dcmIdpost ).getArpu ( )+hmpost.get ( dcmIdpost ).getArpu ( );
             Integer totalCount = hmpre.get ( dcmIdpost ).getCount ( )+hmpost.get ( dcmIdpost ).getCount ( );
             csvResult.setArpu ( totalArpuRevenu );
             csvResult.setCount ( totalCount );
             if (hmtotal.get ( dcmIdpost )==null)
             {
             hmtotal.put ( dcmIdpost , csvResult );
             }
          }
       }
       
       
       
       
         
         
      }
      
     st.close ( );
   //  st1.close ( );
     rs.close ( );
   //  rs1.close ( );
      
      
      
      return hmtotal;
      
   }
   public static String[] formatQuartar (String quartar,String year_id){
      String fromDate="";
      String toDate = "";
      String yearName = (String)years.get ( year_id);
      if ( quartar.compareTo ( "1" ) == 0 )
      {
         fromDate = " 1/"+ yearName ;
         toDate = "3/"+ yearName ;
      }
      
      if ( quartar.compareTo ( "2" ) == 0 )
      {
         fromDate = " 4/"+ yearName ;
         toDate = "6/"+ yearName ;      
      }
      if ( quartar.compareTo ( "3" ) == 0 )
      {
         fromDate = " 7/"+ yearName ;
         toDate = "9/"+ yearName ;
      }   
      
      if ( quartar.compareTo ( "4" ) == 0 )
      {
         fromDate = " 10/"+ yearName ;
         toDate = "12/"+ yearName ;      
      } 
      
      return new String[]{fromDate,toDate};
      
   }
   
   
   public static String[] formatQuartarMonthly (String quartar,String year_id){
      String fromDate="";
      String toDate = "";
      String yearName = (String)years.get ( year_id);
      String dates [] = new String[4];
      if ( quartar.compareTo ( "1" ) == 0 )
      {
         fromDate = " 1/"+ yearName ;
         toDate = "2/"+ yearName ;
         dates [0] = fromDate;
         dates [1] = toDate;
         fromDate = " 2/"+ yearName ;
         toDate = "3/"+ yearName ;
         dates [2] = fromDate;
         dates [3] = toDate;
      }
      
      if ( quartar.compareTo ( "2" ) == 0 )
      {
         
         fromDate = " 4/"+ yearName ;
         toDate = "5/"+ yearName ;
         dates [0] = fromDate;
         dates [1] = toDate;
         fromDate = " 5/"+ yearName ;
         toDate = "6/"+ yearName ;
         dates [2] = fromDate;
         dates [3] = toDate;
         
            
      }
      if ( quartar.compareTo ( "3" ) == 0 )
      {
         fromDate = " 7/"+ yearName ;
         toDate = "8/"+ yearName ;
         dates [0] = fromDate;
         dates [1] = toDate;
         fromDate = " 8/"+ yearName ;
         toDate = "9/"+ yearName ;
         dates [2] = fromDate;
         dates [3] = toDate;
      }   
      
      if ( quartar.compareTo ( "4" ) == 0 )
      {
         fromDate = " 10/"+ yearName ;
         toDate = "11/"+ yearName ;
         dates [0] = fromDate;
         dates [1] = toDate;
         fromDate = " 11/"+ yearName ;
         toDate = "12/"+ yearName ;
         dates [2] = fromDate;
         dates [3] = toDate;  
      } 
      
      return dates;
      
   }

   
   public static Vector<String> getSavedReportValues(Connection con ,String reportId,String dcmId) throws SQLException{
      
      String sql = prepareSavedReportValuesSQL(reportId,dcmId);
      Vector<String> vec = null;
      StringBuffer key = null;
      
      Statement st = con.createStatement ( );
      ResultSet rs = st.executeQuery ( sql );
      
      System.out.println("********************* begin sql ");
      System.out.println(sql);
      System.out.println("********************* end sql ");
      while (rs.next ( ))
      {
        if (vec==null) vec = new Vector<String>();
        
        key = new StringBuffer("");
        key.append ( rs.getString ( "EMP_ID" ) );
        key.append ( "_" );
        key.append ( rs.getString ( "REPORT_TYPE_ID" ) );
        key.append ( "_" );
        key.append ( rs.getString ( "DCM_ID" ) );
        key.append ( "_" );
        key.append ( rs.getString ( "REPORT_YEAR_ID" ) );
        key.append ( "_" );
        key.append ( rs.getString ( "MONTH_ID" ) );
        key.append ( "_" );
        key.append ( String.valueOf ( rs.getInt ( "AMOUNT" )) );
         
        vec.add ( key.toString ( ) );                 
      }
      
      st.close ( );
      rs.close ( );
      
      return vec;
   }
   public static String prepareSavedReportValuesSQL (String reportId,String dcmId){
      
      StringBuffer sql = new StringBuffer("");      
      
      
      
      
      
      
      sql.append ( " SELECT   SIP_SAVED_REPORT.REPORT_ID,SIP_REPORT_ITEM.DCM_ID, GD.DCM_NAME,SIP_SAVED_REPORT.REPORT_TYPE_ID, SIP_REPORT_TYPE.REPORT_TYPE_NAME, SIP_SAVED_REPORT.REPORT_YEAR_ID");
      sql.append ( " ,SIP_SAVED_REPORT.Month_ID, SIP_UPLOADED_LISTS.FIELD2 EMP_ID, ");
      sql.append ( " SUM (SIP_REPORT_ITEM_AMOUNT) AMOUNT");
      sql.append ( " FROM ");
      sql.append ( " SIP_SAVED_REPORT,");
      sql.append ( " SIP_REPORT_ITEM,");
      sql.append ( " SIP_REPORT_FACTOR,");
      sql.append ( " GEN_DCM GD   ");
      sql.append ( " , SIP_REPORT_TYPE,");
      sql.append ( " SIP_UPLOADED_LISTS");               
      sql.append ( " WHERE SIP_SAVED_REPORT.REPORT_ID IN (SELECT MANY_SAVED_REPORT_ID");
      sql.append ( " FROM SIP_SAVED_REPORT_SIP_REPORT");
      sql.append ( " WHERE MANY_SIP_REPORT_ID = '");
      sql.append ( reportId );
      sql.append ( "')");      
      sql.append ( " AND SIP_SAVED_REPORT.REPORT_ID = SIP_REPORT_ITEM.SIP_REPORT_DETAIL_ID");
      sql.append ( " AND SIP_SAVED_REPORT.REPORT_TYPE_ID = SIP_REPORT_TYPE.REPORT_TYPE_ID");
      sql.append ( " AND SIP_REPORT_ITEM.SIP_REPORT_FACTOR_ID =");
      sql.append ( " SIP_REPORT_FACTOR.SIP_REPORT_FACTOR_ID");
      sql.append ( " AND GD.DCM_CODE =SIP_UPLOADED_LISTS.FIELD1");
      sql.append ( " AND SIP_REPORT_FACTOR_VALUE = 1");
      sql.append ( " AND SIP_REPORT_FACTOR_NAME NOT IN (SELECT CONFIG_NAME");
      sql.append ( " FROM SIP_CONFIG_SETTING)");
      sql.append ( " AND GD.DCM_ID = SIP_REPORT_ITEM.DCM_ID");      
      sql.append ( " AND GD.DCM_PAYMENT_LEVEL_ID = 1");
      sql.append ( " AND GD.DCM_ID in (select dcm_id from gen_dcm, SIP_UPLOADED_LISTS    where gen_dcm.DCM_CODE =SIP_UPLOADED_LISTS.FIELD1)");       
      sql.append ( " GROUP BY SIP_SAVED_REPORT.REPORT_ID,SIP_REPORT_ITEM.DCM_ID, GD.DCM_NAME,SIP_SAVED_REPORT.REPORT_TYPE_ID,SIP_REPORT_TYPE.REPORT_TYPE_NAME,");
      sql.append ( " SIP_SAVED_REPORT.Month_ID ,SIP_SAVED_REPORT.REPORT_YEAR_ID,SIP_UPLOADED_LISTS.FIELD2");
      sql.append ( " order by SIP_REPORT_ITEM.DCM_ID , SIP_SAVED_REPORT.REPORT_TYPE_ID");
            
      
      
      
      System.out.println("Begin prepareSavedReportValuesSQL************");
      
      System.out.println(sql.toString ( ));
      System.out.println("End prepareSavedReportValuesSQL************");
      
      
      
      return sql.toString ( );
   }
   
   private static int getYearValue(String yearID, Connection con)
   {
       String sql = "select year_name from SIP_REPORT_YEAR where year_id = "+ yearID;       
       int yearValue = DBUtil.executeQuerySingleValueInt(sql,"year_name",con);
       return yearValue;
   }
   
   public static void saveDormantForAllDCMs(String dormantSQL , Connection con,String sipReportID, String labelId, String quartar, String year)
   {
   try
   {
       System.out.println("begining of getDormantForAllDCMs ");
         
     
     
   /*   
      System.out.println("dormant sql=");
      System.out.println(dormantSQL);
      
      System.out.println("year = "+ year);
      System.out.println("Quarter = "+ quartar);
     */ 
      int yearValue = getYearValue(year,con);
    //  System.out.println("Year Value ="+ yearValue);
      
      
      int dormantYearToCalculateOn = yearValue;
      
      if (quartar.compareTo("1")==0)
      {
          dormantYearToCalculateOn = dormantYearToCalculateOn - 1;
      }
      
      int dormantQuarterToCalculateOn = Integer.parseInt(quartar);
      dormantQuarterToCalculateOn = dormantQuarterToCalculateOn -1;//previous quarter;
      
      if (dormantQuarterToCalculateOn == 0)
          dormantQuarterToCalculateOn = 4;
      
      //System.out.println("dormantQuarterToCalculateOn ="+dormantQuarterToCalculateOn);
      
      
      PreparedStatement ps = con.prepareStatement ( dormantSQL ) ;
      ps.setString(1,sipReportID);
      ps.setString ( 2 , labelId );
      ps.setString ( 3 , dormantYearToCalculateOn+"" );
      ps.setString ( 4, dormantQuarterToCalculateOn+"" );
      ps.execute();
      
      ps.close ( );
      
      System.out.println("End of getDormantForAllDCMs ");
   }
   catch(Exception e)
   {
       e.printStackTrace();
   }
   }
   
   private static String getSipReportMonths(String quarter)
   {
       String months ="";
       if ( quarter.compareTo ( "1" ) == 0 )
           months = SIPInterfaceKey.SIP_REPORT_QUARTAR_1;
        if ( quarter.compareTo ( "2" ) == 0 )
           months = SIPInterfaceKey.SIP_REPORT_QUARTAR_2;
        if ( quarter.compareTo ( "3" ) == 0 )
           months = SIPInterfaceKey.SIP_REPORT_QUARTAR_3;
        if ( quarter.compareTo ( "4" ) == 0 )
           months = SIPInterfaceKey.SIP_REPORT_QUARTAR_4;
        return months;
   }
   
   public static String getDormantSQLIdByReportCategory(String sipReprotCategory)
   {
       System.out.println("sipReprotCategory =" + sipReprotCategory);
       
       String sql = "SELECT QUERY FROM SIP_REPORT_QUERY WHERE QUERY_TYPE_ID = 4 and REPORT_TYPE_CATEGORY_ID  ="+ sipReprotCategory;
       String query = DBUtil.executeQuerySingleValueString(sql, "QUERY");
       return query;
   }
   
   public static String getLinesSQLIdByReportCategory(String sipReprotCategory)
   {
       System.out.println("sipReprotCategory =" + sipReprotCategory);
       
       String sql = "SELECT QUERY FROM SIP_REPORT_QUERY WHERE QUERY_TYPE_ID = 1 and REPORT_TYPE_CATEGORY_ID  ="+ sipReprotCategory;
       String query = DBUtil.executeQuerySingleValueString(sql, "QUERY");
       return query;
   }
   
   public static String getNILinesSQLIdByReportCategory(String sipReprotCategory)
   {
       System.out.println("sipReprotCategory =" + sipReprotCategory);

       String sql = "SELECT QUERY FROM SIP_REPORT_QUERY WHERE QUERY_TYPE_ID = 2 and REPORT_TYPE_CATEGORY_ID  ="+ sipReprotCategory;
       String query = DBUtil.executeQuerySingleValueString(sql, "QUERY");
       return query;
   }
   
   public static String getSOPSQLIdByReportCategory(String sipReprotCategory)
   {
       String sql = "SELECT QUERY FROM SIP_REPORT_QUERY WHERE QUERY_TYPE_ID = 3 and REPORT_TYPE_CATEGORY_ID  ="+ sipReprotCategory;
       String query = DBUtil.executeQuerySingleValueString(sql, "QUERY");
       return query;
   }
 
   public static String getExPosSQLIdByReportCategory(String sipReprotCategory)
   {
       System.out.println("sipReprotCategory =" + sipReprotCategory);
       
       String sql = "SELECT QUERY FROM SIP_REPORT_QUERY WHERE QUERY_TYPE_ID = 5 and REPORT_TYPE_CATEGORY_ID  ="+ sipReprotCategory;
       String query = DBUtil.executeQuerySingleValueString(sql, "QUERY");
       return query;
   }
   
 
   public static String getNonExPosSQLIdByReportCategory(String sipReprotCategory)
   {
       System.out.println("sipReprotCategory =" + sipReprotCategory);
       
       String sql = "SELECT QUERY FROM SIP_REPORT_QUERY WHERE QUERY_TYPE_ID = 6 and REPORT_TYPE_CATEGORY_ID  ="+ sipReprotCategory;
       String query = DBUtil.executeQuerySingleValueString(sql, "QUERY");
       return query;
   }
   
   
   private static int getLastMonthInQ(String quarter)
   {
       int lastMonthinQ = 1;
       
       if (quarter.compareTo("1")==0)
           lastMonthinQ= 3;
       else if (quarter.compareTo("2")==0)
           lastMonthinQ= 6;
       else if (quarter.compareTo("3")==0)
           lastMonthinQ= 9;
       else if (quarter.compareTo("4")==0)
           lastMonthinQ= 12;
       
       return lastMonthinQ;
   }
   
   public static void calculateSIP(
         String report_id,String sipReprotCategory, String labelId, String quarter, String year,String userId)
         throws Exception
   {
     // ArrayList < String > totalStrings = new ArrayList < String > ( );
      
      com.mobinil.sds.core.system.sip.dto.sipDistHistoryDTO sipdh = new com.mobinil.sds.core.system.sip.dto.sipDistHistoryDTO();
      sipdh.setSipReportId ( report_id );
           
      String months = getSipReportMonths(quarter);
                       
      Connection con = Utility.getConnection ( );
      SipDAO.deleteSipReportDetailHistory ( con , report_id );
      
      loadReportSpecificQuries(con);
      loadYearVector(con);
      
      String dormantSQLID = getDormantSQLIdByReportCategory(sipReprotCategory);      
  
      saveDormantForAllDCMs (dormantSQLID, con , report_id,labelId , quarter , year );
            
      String savedLinesSQLID = getLinesSQLIdByReportCategory(sipReprotCategory);
                
      DBUtil.executePreparedStatment(savedLinesSQLID, con, report_id ,report_id );
      
      System.out.println("****** After saving Lines Data ***** ");
      String savedNILineSQLID = getNILinesSQLIdByReportCategory(sipReprotCategory);
           
      DBUtil.executePreparedStatment(savedNILineSQLID, con, report_id ,report_id );
      
      System.out.println("****** After saving NI Lines Data ***** ");
      
      String savedSOPSQLID = getSOPSQLIdByReportCategory(sipReprotCategory);
           
      DBUtil.executePreparedStatment(savedSOPSQLID, con, report_id ,report_id);
      
      System.out.println("****** After saving SOP Data ***** ");
      
     int lastMonthinQ = getLastMonthInQ (quarter);     
     int yearValue = getYearValue(year,con);
     
     String monthYearString = lastMonthinQ+"/"+yearValue;
     
     //SAVING EXPOS
     String saveEXPOSSQLID = getExPosSQLIdByReportCategory(sipReprotCategory);
 
     DBUtil.executePreparedStatment(saveEXPOSSQLID, con, monthYearString ,report_id);
       
     System.out.println("****** After saving EX POS Data ***** ");
     
     //SAVING NONEXPOS
     String saveNonEXPOSSQLID = getNonExPosSQLIdByReportCategory(sipReprotCategory);
     DBUtil.executePreparedStatment(saveNonEXPOSSQLID, con, monthYearString ,report_id);
       
     System.out.println("****** After saving NonEX POS Data ***** ");
     
     
     //saving extra data should be added here as well 
     

      
      SipDAO.updatesipReportDetailStatus ( con , report_id , "2"  );           //report is draft 
      Utility.closeConnection ( con );                     
      
   }
   
   
   public static void loadYearVector(java.sql.Connection con) throws Exception{
      GenericModel sipYearModel = GenericModelDAO.getColumns(con,"SIP_REPORT_YEAR");                                 
      Vector sipYears = GenericModelDAO.getModels(con ,sipYearModel);
      for ( int i = 0 ; i < sipYears.size ( ) ; i ++ )
      {
         GenericModel gn = (GenericModel)sipYears.get ( i );
         years.put ( gn.get_primary_key_value() , gn.get_field_2_value() );
         
      }
      
      
      }
   
   public static void loadReportSpecificQuries(java.sql.Connection con) throws Exception
   {
   System.out.println("Begin Loading SIP specific Queries");
   String sql = "select * from SIP_REPORT_QUERY";
   Statement st = con.createStatement ( );
   ResultSet rs = st.executeQuery ( sql );
   while (rs.next ( ))
   {
      String id = rs.getString ( "ID" );
      String query =  rs.getString ( "QUERY" );
       quriesHashMap.put ( id ,query );
      
     //  System.out.println("id = "+ id);
     //  System.out.println("query = " + query);
   }
      
   rs.close();
   st.close();
   System.out.println("End of Loading SIP specific Queries");   
      }

      
}

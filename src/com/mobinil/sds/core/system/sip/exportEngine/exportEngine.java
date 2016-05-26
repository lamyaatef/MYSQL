package com.mobinil.sds.core.system.sip.exportEngine;

import java.sql.*;
import java.util.*;

import com.mobinil.sds.core.system.dcm.genericModel.GenericModel;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.GenericModelDAO;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.sip.SIPInterfaceKey;

public class exportEngine
{
  static HashMap years = new HashMap();
  
  static HashMap quriesHashMap = new HashMap();
   /**
    * @param args
    * @throws Exception 
    */
   
  public static ArrayList<String> refactorString(String string,int numberOfFailedEmpty){
     
     ArrayList < String > totalStrings = new ArrayList < String > ( );
     String tempArray [] = null;
     if (string!=null && string.compareTo ( "" )!=0 && string.compareTo ( "," )!=0)
     {
        tempArray = string.split ( "," );
        for ( int i = 1 ; i < tempArray.length ; i ++ )
        {
           totalStrings.add ( tempArray[i] );            
        }
     }
     else
     {
        for ( int i = 1 ; i < numberOfFailedEmpty ; i ++ )
        {
           totalStrings.add ( "" );            
        }
     }
     return totalStrings;
  }
  
   public static HashMap < String , LinkedHashMap < String , ArrayList < String > >> getDistributorsExcelReport(
         String report_id)
         throws Exception
   {
      
      HashMap < String , LinkedHashMap < String , ArrayList < String > >> allReport = new HashMap < String , LinkedHashMap < String , ArrayList < String > >> ( );
      LinkedHashMap < String , ArrayList < String > > exectives = new LinkedHashMap < String , ArrayList < String >> ( );
      LinkedHashMap < String , ArrayList < String > > revenueREport = new LinkedHashMap < String , ArrayList < String >> ( );
      LinkedHashMap < String , ArrayList < String > > GrossAdds = new LinkedHashMap < String , ArrayList < String >> ( );
      LinkedHashMap < String , ArrayList < String > > Dormant = new LinkedHashMap < String , ArrayList < String >> ( );
      LinkedHashMap < String , ArrayList < String > > POS = new LinkedHashMap < String , ArrayList < String >> ( );
      LinkedHashMap < String , ArrayList < String > > GrossAdds2 = new LinkedHashMap < String , ArrayList < String >> ( );
      LinkedHashMap < String , ArrayList < String > > GrossAddActHashMap = new LinkedHashMap < String , ArrayList < String >> ( );
      
      LinkedHashMap < String , ArrayList < String > > revenueREportNonDetail = new LinkedHashMap < String , ArrayList < String >> ( );
      LinkedHashMap < String , ArrayList < String > > GrossAddsNonDetail = new LinkedHashMap < String , ArrayList < String >> ( );
      LinkedHashMap < String , ArrayList < String > > DormantNonDetail = new LinkedHashMap < String , ArrayList < String >> ( );
      LinkedHashMap < String , ArrayList < String > > GrossAdds2NonDetail = new LinkedHashMap < String , ArrayList < String >> ( );
      LinkedHashMap < String , ArrayList < String > > ExPOSNonDetail = new LinkedHashMap < String , ArrayList < String >> ( );
      
      ArrayList < String > totalStrings = new ArrayList < String > ( );
      
       
      String dcmName = "";
      String dcmId = "";
      String dcmCount = "";
      String dormantCount = "";
      
      
      
      
      
      Connection con = Utility.getConnection ( );
      
      
      
     
      
    
      
        String sql = "select DCM_ID, DCM_NAME, DCM_COUNT, DORMANT_COUNT, EXECUTIVE_NAME, SCRATCH, E_TOPUP" +
        		     ", GROSSADDTOTAL, EXPOSTOTAL, NONEXPOSTOTAL, TOTALGROSSADDSDETAIL, REVENUESCRATCHDETIAL" +
        		     ", REVENUEETOPUPDETAIL, GROSSADDSDETAIL, DORMANTDETAIL, DETAILEDGROSSADDSACT" +
        		     ", DETAILEDEXPOSANDNONEXPOS, SIP_REPORT_ID" +
        		     " from SIP_DIST_HISTORY where sip_report_id='"+report_id+"'";
        
        
        
      System.out.println ( "sql issssssssssssssssss " + sql );
      
      Statement st = con.createStatement ( );
      ResultSet rs = st.executeQuery ( sql );
      
      
      while ( rs.next ( ) )
      {
         dcmName = rs.getString ( "DCM_NAME" );
         
         dcmCount = rs.getInt ( "DCM_COUNT" ) + "";
         
         dormantCount = rs.getInt ( "DORMANT_COUNT" ) + "";
         
         dcmId = rs.getString ( "DCM_ID" );
         
         
         totalStrings.add ( rs.getString ( "EXECUTIVE_NAME" ) );         
         exectives.put ( dcmName , totalStrings );         
         totalStrings = new ArrayList < String > ( );
         
         
         totalStrings.add ( rs.getString ( "SCRATCH" ) );
         totalStrings.add ( rs.getString ( "E_TOPUP" ) );
         revenueREportNonDetail.put ( dcmName , totalStrings );         
         totalStrings = new ArrayList < String > ( );
         
         totalStrings.add ( dormantCount );
         DormantNonDetail.put ( dcmName , totalStrings );
         totalStrings = new ArrayList < String > ( );
         
         totalStrings.add ( rs.getString ( "GROSSADDTOTAL" ) );
         GrossAdds2NonDetail.put ( dcmName , totalStrings );         
         totalStrings = new ArrayList < String > ( );
         
         totalStrings.add ( dcmCount );
         GrossAddsNonDetail.put ( dcmName , totalStrings );
         totalStrings = new ArrayList < String > ( );
         
         totalStrings.add ( rs.getString ( "EXPOSTOTAL" ) );
         totalStrings.add ( rs.getString ( "NONEXPOSTOTAL" ) );         
         ExPOSNonDetail.put ( dcmName , totalStrings );         
         totalStrings = new ArrayList < String > ( );
         
         
         String revenueScratchDetial = rs.getString ( "REVENUESCRATCHDETIAL" )==null?"":rs.getString ( "REVENUESCRATCHDETIAL" );
         String revenueEtopupDetail = rs.getString ( "REVENUEETOPUPDETAIL" )==null?"":rs.getString ( "REVENUEETOPUPDETAIL" );         
         String string = revenueScratchDetial+","+revenueEtopupDetail;         
         totalStrings = refactorString(string,7);
         revenueREport.put ( dcmName , totalStrings );         
         totalStrings = new ArrayList < String > ( );
         
         
              
         
         totalStrings = refactorString(rs.getString ( "GROSSADDSDETAIL" )==null?"":rs.getString ( "GROSSADDSDETAIL" ),4);         
         totalStrings.add ( rs.getString ( "TOTALGROSSADDSDETAIL" ) );
         GrossAdds.put ( dcmName , totalStrings );
         totalStrings = new ArrayList < String > ( );
         
         
         totalStrings = refactorString(rs.getString ( "DORMANTDETAIL" )==null?"":rs.getString ( "DORMANTDETAIL" ),4);                  
         Dormant.put ( dcmName , totalStrings );         
         totalStrings = new ArrayList < String > ( );        
         
         
         totalStrings = refactorString(rs.getString ( "DETAILEDEXPOSANDNONEXPOS" )==null?"":rs.getString ( "DETAILEDEXPOSANDNONEXPOS" ),7);
         GrossAdds2.put ( dcmName , totalStrings );         
         totalStrings = new ArrayList < String > ( );
         
         
         
         totalStrings = refactorString(rs.getString ( "DETAILEDGROSSADDSACT" )==null?"":rs.getString ( "DETAILEDGROSSADDSACT" ),4);         
         GrossAddActHashMap.put ( dcmName , totalStrings );         
         totalStrings = new ArrayList < String > ( );
         
         
         
         
      }
      rs.close();
      st.close ( );
      Utility.closeConnection ( con );      
      
      allReport.put ( "revenueREportNonDetail" , revenueREportNonDetail );
      allReport.put ( "GrossAddsNonDetail" , GrossAddsNonDetail );
      allReport.put ( "DormantNonDetail" , DormantNonDetail );
      allReport.put ( "GrossAdds2NonDetail" , GrossAdds2NonDetail );
      allReport.put ( "ExPOSNonDetail" , ExPOSNonDetail );
      
      allReport.put ( "exectives" , exectives );
      allReport.put ( "revenueREport" , revenueREport );
      allReport.put ( "GrossAdds" , GrossAdds );
      allReport.put ( "Dormant" , Dormant );
      allReport.put ( "POS" , POS );
      allReport.put ( "GrossAdds2" , GrossAdds2 );
      allReport.put ( "GrossAddActHashMap" , GrossAddActHashMap );
      
      return allReport;
      
   }
   public static HashMap < String , LinkedHashMap < String , ArrayList < String > >> getQualityExcelReport(
         String report_id, String labelId, String quartar, String year)
         throws Exception
   {
      HashMap < String , LinkedHashMap < String , ArrayList < String > >> allReport = new HashMap < String , LinkedHashMap < String , ArrayList < String > >> ( );
      
      LinkedHashMap<String,ArrayList<String> >  fieldRepName= new  LinkedHashMap<String,ArrayList<String>>();
      LinkedHashMap<String,ArrayList<String> >  revenueREport= new  LinkedHashMap<String,ArrayList<String>>();
      LinkedHashMap<String,ArrayList<String> >  GrossAdds= new  LinkedHashMap<String,ArrayList<String>>();
      LinkedHashMap<String,ArrayList<String> >  Dormant= new  LinkedHashMap<String,ArrayList<String>>();
      LinkedHashMap<String,ArrayList<String> >  notIntial= new  LinkedHashMap<String,ArrayList<String>>();
      
      
      
      
ArrayList < String > totalStrings = new ArrayList < String > ( );
      
      String dcmName = "";
      String dcmId = "";
      String dcmCount = "";
      String dormantCount = "";
      
      
      
      String months = "";
      
      if ( quartar.compareTo ( "1" ) == 0 )
         months = SIPInterfaceKey.SIP_REPORT_QUARTAR_1;
      if ( quartar.compareTo ( "2" ) == 0 )
         months = SIPInterfaceKey.SIP_REPORT_QUARTAR_2;
      if ( quartar.compareTo ( "3" ) == 0 )
         months = SIPInterfaceKey.SIP_REPORT_QUARTAR_3;
      if ( quartar.compareTo ( "4" ) == 0 )
         months = SIPInterfaceKey.SIP_REPORT_QUARTAR_4;
      
      
      
        
      
      Connection con = Utility.getConnection ( );
      
      
      getReportQuries(con);
      yearVector(con);
      String sql = (String)quriesHashMap.get ( "21" );
      
      
      if (sql!=null)
      {  
       if (sql.contains ( "#1" ))sql =sql.replace ( "#1" , labelId );
       if (sql.contains ( "#2" ))sql =sql.replace ( "#2" , year );
       if (sql.contains ( "#3" ))sql =sql.replace ( "#3" , months );
       if (sql.contains ( "#4" ))sql =sql.replace ( "#4" , report_id );
       if (sql.contains ( "#5" ))sql =sql.replace ( "#5" , SIPInterfaceKey.SIP_REPORT_FACTOR_VALUE_INCLUDE );
      }
      
      
      System.out.println ( "sqlBuffer issssssssssssssssss " + sql );
      
      Statement st = con.createStatement ( );
      ResultSet rs = st.executeQuery ( sql );
      
      while(rs.next ( ))
      {
         
         


         dcmName = rs.getString ( "DCM_NAME" );
         dcmCount = rs.getInt ( "amount" ) + "";
         dormantCount = rs.getInt ( "dormantCount" ) + "";
         dcmId = rs.getString ( "DCM_ID" );
         
     
         totalStrings.add(dcmId);
         totalStrings.add ( DAO.getGenericDataFromSQL(con,(String)quriesHashMap.get ( "22" ),1/*numberOfCells*/,new String[]{dcmId}/*data replace*/,1/*columnNumberToBeCatch*/).get ( 0 ) );         
         fieldRepName.put ( dcmName , totalStrings );
         totalStrings = new ArrayList < String > ( );
    
    
         for ( int i = 1 ; i < 7 ; i ++ )
         {
            totalStrings.add ( DAO.getGenericDataFromSQL(con,(String)quriesHashMap.get ( "23" ),1/*numberOfCells*/,new String[]{dcmId}/*data replace*/,i/*columnNumberToBeCatch*/).get ( 0 ) );            
         }
    revenueREport.put ( dcmName , totalStrings );
    totalStrings = new ArrayList < String > ( );
                
    totalStrings = DAO.getGenericDataFromSQLHash ( con,(String)quriesHashMap.get ( "24" ),3/*numberOfCells*/,new String[]{labelId,year,months,report_id,dcmId}/*data replace*/,1/*columnNumberToBeCatch*/);
       GrossAdds.put(dcmName,totalStrings);
       totalStrings=new ArrayList<String>();
                 
       
       
       totalStrings = DAO.getGenericDataFromSQL(con,(String)quriesHashMap.get ( "25" ),3,new String[]{labelId,year,quartar,dcmId},3);
       totalStrings.add ( dormantCount );       
       Dormant.put(dcmName,totalStrings);                 
       totalStrings=new ArrayList<String>();
               
       totalStrings = DAO.getGenericDataFromSQLHash ( con,(String)quriesHashMap.get ( "26" ),3/*numberOfCells*/,new String[]{labelId,year,months,report_id,dcmId}/*data replace*/,1/*columnNumberToBeCatch*/);
       totalStrings.add(DAO.getGenericDataFromSQLHash ( con,(String)quriesHashMap.get ( "27" ),1/*numberOfCells*/,new String[]{labelId,year,months,report_id,dcmId}/*data replace*/,1/*columnNumberToBeCatch*/).get ( 0 ));
       notIntial.put(dcmName,totalStrings);                 
       totalStrings=new ArrayList<String>();
      }
      
      
      rs.close();
      st.close ( );
      Utility.closeConnection ( con );  
      
      
     
       
          
      
      allReport.put ( "fieldRepName" , fieldRepName );
      allReport.put ( "revenueREport" , revenueREport );
      allReport.put ( "GrossAdds" , GrossAdds );
      allReport.put ( "Dormant" , Dormant );
      allReport.put ( "notIntial" , notIntial );
          
          return allReport;
   }
   
   public static HashMap < String , LinkedHashMap < String , ArrayList < String > >> getSDExcelReport(
         String report_id, String labelId, String quartar, String year)
         throws Exception
   {
      
 HashMap < String , LinkedHashMap < String , ArrayList < String > >> allReport = new HashMap < String , LinkedHashMap < String , ArrayList < String > >> ( );
      
      LinkedHashMap<String,ArrayList<String> >  firstSDdetails= new  LinkedHashMap<String,ArrayList<String>>();
      LinkedHashMap<String,ArrayList<String> >  firstGrossAdds= new  LinkedHashMap<String,ArrayList<String>>();
      LinkedHashMap<String,ArrayList<String> >  firstDormant= new  LinkedHashMap<String,ArrayList<String>>();
      LinkedHashMap<String,ArrayList<String> >  firstPOS= new  LinkedHashMap<String,ArrayList<String>>();
      
      LinkedHashMap<String,ArrayList<String> >  secondGrossAdds= new  LinkedHashMap<String,ArrayList<String>>();
      LinkedHashMap<String,ArrayList<String> >  secondSDdetails= new  LinkedHashMap<String,ArrayList<String>>();
      LinkedHashMap<String,ArrayList<String> >  secondDormant= new  LinkedHashMap<String,ArrayList<String>>();     
      LinkedHashMap<String,ArrayList<String> >  secondPOS= new  LinkedHashMap<String,ArrayList<String>>();
      
      LinkedHashMap<String,ArrayList<String> >  thirdrevenueREport= new  LinkedHashMap<String,ArrayList<String>>();
      LinkedHashMap<String,ArrayList<String> >  thirdGrossAdds= new  LinkedHashMap<String,ArrayList<String>>();
      
      
      
ArrayList < String > totalStrings = new ArrayList < String > ( );
      
      String dcmId = "";
      String dcmName = "";
      String dcmCount = "";
      String dormantCount = "";
      
      
      String months = "";
      
      if ( quartar.compareTo ( "1" ) == 0 )
         months = SIPInterfaceKey.SIP_REPORT_QUARTAR_1;
      if ( quartar.compareTo ( "2" ) == 0 )
         months = SIPInterfaceKey.SIP_REPORT_QUARTAR_2;
      if ( quartar.compareTo ( "3" ) == 0 )
         months = SIPInterfaceKey.SIP_REPORT_QUARTAR_3;
      if ( quartar.compareTo ( "4" ) == 0 )
         months = SIPInterfaceKey.SIP_REPORT_QUARTAR_4;
      
     
      
        
      
      Connection con = Utility.getConnection ( );
      
      
      
      getReportQuries(con);
      yearVector(con);
      String sql = (String)quriesHashMap.get ( "12" );
      
      
      if (sql!=null)
      {  
       if (sql.contains ( "#1" ))sql =sql.replace ( "#1" , labelId );
       if (sql.contains ( "#2" ))sql =sql.replace ( "#2" , year );
       if (sql.contains ( "#3" ))sql =sql.replace ( "#3" , months );
       if (sql.contains ( "#4" ))sql =sql.replace ( "#4" , report_id );
       if (sql.contains ( "#5" ))sql =sql.replace ( "#5" , SIPInterfaceKey.SIP_REPORT_FACTOR_VALUE_INCLUDE );
      }
      
      
      System.out.println ( "sqlBuffer issssssssssssssssss " + sql );
      
      Statement st = con.createStatement ( );
      ResultSet rs = st.executeQuery ( sql );
      
      while(rs.next ( ))
      {
         
         


         dcmName = rs.getString ( "DCM_NAME" );
         dcmCount = rs.getInt ( "amount" ) + "";
         dormantCount = rs.getInt ( "dormantCount" ) + "";
         dcmId = rs.getString ( "DCM_ID" );
         
         
         
         totalStrings.add(dcmId);
         totalStrings.add ( DAO.getGenericDataFromSQL(con,(String)quriesHashMap.get ( "13" ),1/*numberOfCells*/,new String[]{dcmId}/*data replace*/,1/*columnNumberToBeCatch*/).get ( 0 ) );         
         firstSDdetails.put ( dcmName , totalStrings );
         totalStrings = new ArrayList < String > ( );
         
         
         HashMap hs = new HashMap();
         hs.put ( "2" , quriesHashMap.get ( "14" ) );
         hs.put ( "3" , quriesHashMap.get ( "15" ) );
         totalStrings.add ( DAO.getTotalGrossAddsACT ( con , year , quartar , dcmId , years,hs ) );
         firstGrossAdds.put(dcmName,totalStrings);         
         totalStrings=new ArrayList<String>();
         
         totalStrings.add(dormantCount);
         firstDormant.put(dcmName,totalStrings);
         totalStrings=new ArrayList<String>();
         
         totalStrings.add ( DAO.getGenericDataFromSQL(con,(String)quriesHashMap.get ( "16" ),1/*numberOfCells*/,new String[]{dcmId}/*data replace*/,1/*columnNumberToBeCatch*/).get ( 0 ) );
         totalStrings.add ( DAO.getGenericDataFromSQL(con,(String)quriesHashMap.get ( "16" ),1/*numberOfCells*/,new String[]{dcmId}/*data replace*/,2/*columnNumberToBeCatch*/).get ( 0 ) );    
         firstPOS.put(dcmName,totalStrings);
         totalStrings=new ArrayList<String>();
         /////////////////////////////////second/////////////////////////////////////////////////
         
         
         totalStrings.add(dcmId);
         totalStrings.add ( DAO.getGenericDataFromSQL(con,(String)quriesHashMap.get ( "13" ),1/*numberOfCells*/,new String[]{dcmId}/*data replace*/,1/*columnNumberToBeCatch*/).get ( 0 ) );         
         secondSDdetails.put ( dcmName , totalStrings );
         totalStrings = new ArrayList < String > ( );
         
         
         totalStrings = DAO.getGenericDataFromSQLHash ( con,(String)quriesHashMap.get ( "17" ),3/*numberOfCells*/,new String[]{labelId,year,months,report_id,dcmId}/*data replace*/,1/*columnNumberToBeCatch*/);         
         totalStrings.add ( DAO.getGenericDataFromSQLHash ( con,(String)quriesHashMap.get ( "18" ),1/*numberOfCells*/,new String[]{labelId,year,months,report_id,dcmId}/*data replace*/,1/*columnNumberToBeCatch*/).get ( 0 ) );         
         secondGrossAdds.put(dcmName,totalStrings);         
         totalStrings=new ArrayList<String>();
         
         
         totalStrings = DAO.getGenericDataFromSQL(con,(String)quriesHashMap.get ( "19" ),3,new String[]{labelId,year,quartar,dcmId},3);            
         secondDormant.put(dcmName,totalStrings);
         totalStrings=new ArrayList<String>();
         
         
         for ( int i = 1 ; i < 7 ; i ++ )
         {
            totalStrings.add ( DAO.getGenericDataFromSQL(con,(String)quriesHashMap.get ( "20" ),1/*numberOfCells*/,new String[]{dcmId}/*data replace*/,i/*columnNumberToBeCatch*/).get ( 0 ) );            
         }
         secondPOS.put(dcmName,totalStrings);
         totalStrings=new ArrayList<String>();
         
         //////////////////////third/////////////////////////////////
         
         totalStrings.add("1");
         totalStrings.add("2");
         thirdrevenueREport.put(dcmName,totalStrings);
         totalStrings=new ArrayList<String>();
         
         
         
         totalStrings.add("1");     
         thirdGrossAdds.put(dcmName,totalStrings);         
         totalStrings=new ArrayList<String>();
      }
      

      
      
      
      
      
      rs.close();
      st.close ( );
      Utility.closeConnection ( con );  
      
      
     
          
      
      allReport.put ( "firstSDdetails" , firstSDdetails );
      allReport.put ( "firstGrossAdds" , firstGrossAdds );
      allReport.put ( "firstDormant" , firstDormant );
      allReport.put ( "firstPOS" , firstPOS );

      allReport.put ( "secondGrossAdds" , secondGrossAdds );
      allReport.put ( "secondSDdetails" , secondSDdetails );
      allReport.put ( "secondDormant" , secondDormant );
      allReport.put ( "secondPOS" , secondPOS );
      
      
      allReport.put ( "thirdrevenueREport" , thirdrevenueREport );
      allReport.put ( "thirdGrossAdds" , thirdGrossAdds );
      
      
          
          return allReport;
   }
   
   
   public static void yearVector(java.sql.Connection con) throws Exception{
      GenericModel sipYearModel = GenericModelDAO.getColumns(con,"SIP_REPORT_YEAR");                                 
      Vector sipYears = GenericModelDAO.getModels(con ,sipYearModel);
      for ( int i = 0 ; i < sipYears.size ( ) ; i ++ )
      {
         GenericModel gn = (GenericModel)sipYears.get ( i );
         years.put ( gn.get_primary_key_value() , gn.get_field_2_value() );
         
      }
      
      
      }
   
   public static void getReportQuries(java.sql.Connection con) throws Exception
   {
   
   String sql = "select * from SIP_REPORT_QUERY";
   Statement st = con.createStatement ( );
   ResultSet rs = st.executeQuery ( sql );
   while (rs.next ( ))
   {
      quriesHashMap.put ( rs.getString ( "ID" ) , rs.getString ( "QUERY" ) );
   }
      
      
      }
   public static void main(String[] args)
   {
      
      
   }
   
}

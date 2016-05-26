package com.mobinil.sds.core.system.commission.factor.dao;

import com.mobinil.sds.core.system.commission.factor.model.*;
import com.mobinil.sds.core.system.gn.querybuilder.dao.*;
import com.mobinil.sds.core.system.gn.querybuilder.domain.*;
import com.mobinil.sds.core.system.gn.querybuilder.dto.*;
import com.mobinil.sds.core.system.gn.querybuilder.model.*;

import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.utilities.*;

public class FactorDAO 
{
 public static Vector getCommissionFactorValues(Connection con , String commissionID)throws Exception
 {
   Statement stmt = con.createStatement();
   Vector commissionFactors = new Vector();
   FactorModel factorModel = null;
   String sqlString = "SELECT  COMMISSION_FACTOR_ID , COMMISSION_FACTOR_NAME ,COMMISSION_FACTOR_VALUE"+
                      " FROM COMMISSION_FACTOR WHERE COMMISSION_DETAIL_ID ="+commissionID+" ORDER BY COMMISSION_FACTOR_NAME" ;
  ResultSet rs = stmt.executeQuery(sqlString);
  Utility.logger.debug(sqlString);
  while(rs.next())
  {
   factorModel = new FactorModel();
   factorModel.setCommissionFactorID(rs.getInt("COMMISSION_FACTOR_ID"));
   factorModel.setCommissionFactorName(rs.getString("COMMISSION_FACTOR_NAME")); 
   factorModel.setCommissionFactorValue(rs.getDouble("COMMISSION_FACTOR_VALUE"));
   factorModel.setCommissionID(Integer.parseInt(commissionID));
   commissionFactors.add(factorModel);
   factorModel=null;
  }
  return commissionFactors;
 }
 public static Vector<FactorModel> getCommissionFactorNotInDrivingPlan(Connection con , String commissionID)throws Exception
 {

   StringBuilder sqlBuilder = new StringBuilder();
   sqlBuilder.append(" select * from COMMISSION_FACTOR where COMMISSION_FACTOR_NAME not in ");
   sqlBuilder.append(" (select FACTORNAME from DP_FINAL_FACTOR_VALUE where DP_FINAL_FACTOR_VALUE.dp_id in ");
   sqlBuilder.append(" (select dp_id from COMMISSION_Detail where COMMISSION_DETAIL_ID = ");   
   sqlBuilder.append(commissionID);
   sqlBuilder.append("))");
   sqlBuilder.append(" AND COMMISSION_DETAIL_ID = ");
   sqlBuilder.append(commissionID);   
   
  return DBUtil.executeSqlQueryMultiValue(sqlBuilder.toString(), FactorModel.class, con);
 }

 
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 
 
 
 
 
 
 
 
 public static Vector getsipReportFactorValues(Connection con , String sipReportID)throws Exception
 {
   Statement stmt = con.createStatement();
   Vector sipReportFactors = new Vector();
   FactorModel factorModel = null;
   String sqlString = "SELECT  SIP_REPORT_FACTOR_ID , SIP_REPORT_FACTOR_NAME ,SIP_REPORT_FACTOR_VALUE"+
                      " FROM SIP_REPORT_FACTOR WHERE SIP_REPORT_DETAIL_ID ="+sipReportID+" ORDER BY SIP_REPORT_FACTOR_NAME" ;
  ResultSet rs = stmt.executeQuery(sqlString);
  System.out.println("The sql  isssssssss"+sqlString);
  Utility.logger.debug(sqlString);
  while(rs.next())
  {
   factorModel = new FactorModel();
   factorModel.setCommissionFactorID(rs.getInt("SIP_REPORT_FACTOR_ID"));
   factorModel.setCommissionFactorName(rs.getString("SIP_REPORT_FACTOR_NAME")); 
   factorModel.setCommissionFactorValue(rs.getDouble("SIP_REPORT_FACTOR_VALUE"));
   factorModel.setCommissionID(Integer.parseInt(sipReportID));
   sipReportFactors.add(factorModel);
   factorModel=null;
  }
  return sipReportFactors;
 }
 
 
 
 
 
 
 
 
 
 
 
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public static Vector getsipReportFactors(Connection con , String sipReportID)throws Exception
 {
   Statement stmt = con.createStatement();
   Vector commissionFactors = new Vector();
   FactorModel factorModel = null;
   String sqlString = "SELECT  SIP_REPORT_FACTOR_ID , SIP_REPORT_FACTOR_NAME ,SIP_REPORT_FACTOR_VALUE "+
                      
                      "FROM SIP_REPORT_factor WHERE SIP_REPORT_DETAIL_ID ="+sipReportID  +" ORDER By SIP_REPORT_FACTOR_NAME" ;
  System.out.println(sqlString);
  ResultSet rs = stmt.executeQuery(sqlString);

  while(rs.next())
  {
   factorModel = new FactorModel();
   factorModel.setCommissionFactorID(rs.getInt("SIP_REPORT_FACTOR_ID"));
   factorModel.setCommissionFactorName(rs.getString("SIP_REPORT_FACTOR_NAME")); 
   factorModel.setCommissionFactorValue(rs.getDouble("SIP_REPORT_FACTOR_VALUE"));
//   factorModel.setCommissionItemName(rs.getString("SIP_REPORT_ITEM_NAME"));
//   factorModel.setCommissionItemAmount(rs.getInt("SIP_REPORT_ITEM_AMOUNT"));
//   factorModel.setCommissionDCMID(rs.getInt("DCM_ID"));
//   factorModel.setCommissionDCMName(rs.getString("DCM_NAME"));
//   factorModel.setCommissionDCMCode(rs.getString("DCM_CODE"));
//   factorModel.setCommissionName(rs.getString("SIP_REPORT_NAME"));
//   factorModel.setCommissionType(rs.getString("SIP_REPORT_TYPE_NAME"));
//   factorModel.setCommissionCategory(rs.getString("SIP_REPORT_TYPE_CATEGORY_NAME"));
//   factorModel.setCommissionID(Integer.parseInt(commissionID));
   commissionFactors.add(factorModel);
   factorModel=null;
  }
  return commissionFactors;
 }
 
 
 
 
 /////////////////////////////////////////////////////////
 
 
 
 
 
 
 
 
 
 
 
 public static Vector getCommissionFactors(Connection con , String commissionID)throws Exception
 {
   Statement stmt = con.createStatement();
   Vector commissionFactors = new Vector();
   FactorModel factorModel = null;
   String sqlString = "SELECT  COMMISSION_FACTOR_ID , COMMISSION_FACTOR_NAME ,COMMISSION_FACTOR_VALUE"+
                      ",COMMISSION_ITEM_NAME,COMMISSION_ITEM_AMOUNT,DCM_ID ,DCM_NAME,DCM_CODE,COMMISSION_NAME,COMMISSION_TYPE_NAME,COMMISSION_TYPE_CATEGORY_NAME  "+
                      "FROM VW_COMMISSION_ITEM_FACTOR WHERE COMMISSION_DETAIL_ID ="+commissionID  +" ORDER BY DCM_ID , COMMISSION_FACTOR_NAME" ;
  Utility.logger.debug(sqlString);
  ResultSet rs = stmt.executeQuery(sqlString);

  while(rs.next())
  {
   factorModel = new FactorModel();
   factorModel.setCommissionFactorID(rs.getInt("COMMISSION_FACTOR_ID"));
   factorModel.setCommissionFactorName(rs.getString("COMMISSION_FACTOR_NAME")); 
   factorModel.setCommissionFactorValue(rs.getDouble("COMMISSION_FACTOR_VALUE"));
   factorModel.setCommissionItemName(rs.getString("COMMISSION_ITEM_NAME"));
   factorModel.setCommissionItemAmount(rs.getInt("COMMISSION_ITEM_AMOUNT"));
   factorModel.setCommissionDCMID(rs.getInt("DCM_ID"));
   factorModel.setCommissionDCMName(rs.getString("DCM_NAME"));
   factorModel.setCommissionDCMCode(rs.getString("DCM_CODE"));
   factorModel.setCommissionName(rs.getString("COMMISSION_NAME"));
   factorModel.setCommissionType(rs.getString("COMMISSION_TYPE_NAME"));
   factorModel.setCommissionCategory(rs.getString("COMMISSION_TYPE_CATEGORY_NAME"));
//   factorModel.setCommissionID(Integer.parseInt(commissionID));
   commissionFactors.add(factorModel);
   factorModel=null;
  }
  return commissionFactors;
 }
 
 
 
 
 
 
 
 
 
 
 //////////////////////////////////////////////////////////////////
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public static void setFactorValue(Connection con , String factorID ,String commissionID,String factorValue )throws Exception
 {
   Statement stmt = con.createStatement();
   String sqlString = "UPDATE COMMISSION_FACTOR SET COMMISSION_FACTOR_VALUE = "+factorValue+" WHERE "+
                      " COMMISSION_FACTOR_ID = "+factorID;
  int rows = stmt.executeUpdate(sqlString);
  Utility.logger.debug(sqlString+","+rows);
  stmt.close();
  }
 

 
 
 
 ///////////////////////////////////////////////////////////////
 
 
 
 public static void setsipReportFactorValue(Connection con , String factorID  ,String factorValue )throws Exception
 {
   Statement stmt = con.createStatement();
   String sqlString = "UPDATE SIP_REPORT_FACTOR SET SIP_REPORT_FACTOR_VALUE = "+factorValue+" WHERE "+
                      " SIP_REPORT_FACTOR_ID = "+factorID;
  int rows = stmt.executeUpdate(sqlString);
  Utility.logger.debug(sqlString+","+rows);
  stmt.close();
  }
 
 
 
 
 
 
 //////////////////////////////////////////////
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 //Type B DataViews 'Normal DATAVIEWS'
  public static void getFactorsFromDataView(Connection con ,String commissionID)throws Exception
  {
    Statement stmt = con.createStatement();
    String sql = "SELECT COMMISSION_DATA_VIEW_SQL FROM COMMISSION_DETAIL WHERE COMMISSION_DETAIL_ID = "+commissionID;
    
      ResultSet res = stmt.executeQuery(sql);
      if(res.next())
      {
        String strDataViewQuery = res.getString("COMMISSION_DATA_VIEW_SQL");
        Utility.logger.debug(strDataViewQuery);
        ResultSet rs = stmt.executeQuery(strDataViewQuery);
        int dcmID = 0;
        String itemName = "";
        int itemAmount = 0;
        String sqlString = "";
        while(rs.next())
        {
          dcmID = rs.getInt(1);
          itemName = rs.getString(2);
          itemAmount = rs.getInt(3);
          Utility.logger.debug("DCMID "+dcmID);
          Utility.logger.debug("item name "+itemName);
          Utility.logger.debug("item amount "+itemAmount);
          Long itemID = Utility.getSequenceNextVal(con,"SEQ_COMMISSION_ITEM");
          int item_id = itemID.intValue();
          Statement stmt2 =  con.createStatement();
          sqlString = "INSERT INTO COMMISSION_ITEM (COMMISSION_ITEM_ID,COMMISSION_ITEM_NAME,COMMISSION_DETAIL_ID"+
                      ",COMMISSION_ITEM_AMOUNT,DCM_ID) VALUES("+item_id+",'"+itemName+"',"+commissionID+","+itemAmount+
                      ","+dcmID+" )";
          int rows = stmt2.executeUpdate(sqlString);
          stmt2.close();
        }
        rs.close();
        String sqlString2 = "SELECT DISTINCT (COMMISSION_ITEM_NAME) FROM COMMISSION_ITEM WHERE COMMISSION_DETAIL_ID = "+commissionID;
        rs = stmt.executeQuery(sqlString2);
        while(rs.next())
        {
          Long factorID = Utility.getSequenceNextVal(con,"SEQ_COMMISSION_FACTOR");
          int factor_id = factorID.intValue();
          Statement stmt3 = con.createStatement(); 
          itemName = rs.getString("COMMISSION_ITEM_NAME");
          sqlString2 = "INSERT INTO COMMISSION_FACTOR (COMMISSION_FACTOR_ID , COMMISSION_FACTOR_NAME  ,COMMISSION_FACTOR_VALUE,COMMISSION_DETAIL_ID)"+
                      " VALUES ("+factor_id+",'"+itemName+"','',"+commissionID+")";
                      
          int rows = stmt3.executeUpdate(sqlString2);
          sqlString2 = "UPDATE COMMISSION_ITEM SET COMMISSION_FACTOR_ID = "+factor_id+
                        " WHERE COMMISSION_DETAIL_ID = "+commissionID+" AND COMMISSION_ITEM_NAME = '"+
                        itemName+"'";
          rows = stmt3.executeUpdate(sqlString2);                        
          stmt3.close();
        }
        
      }
    res.close();
    stmt.close();
  }
  
  
  
  
  
  
  ///////////////////////////////////////////////////////////////////////////////////////
  
  
  
  
  
  
  
  
  
  
  
//Type B DataViews 'Normal DATAVIEWS'
  public static void getsipReportFactorsFromNormalDataView(Connection con ,String commissionID)throws Exception
  {
   
	  System.out.println("Here in the  NormaL DATA  VIEW");
	  Statement stmt = con.createStatement();
    String sql = "SELECT   REPORT_DATA_VIEW_SQL FROM  SIP_saved_REPORT WHERE REPORT_ID=  "+commissionID;
    
      ResultSet res = stmt.executeQuery(sql);
      if(res.next())
      {
        String strDataViewQuery = res.getString("REPORT_DATA_VIEW_SQL");
        Utility.logger.debug(strDataViewQuery);
        ResultSet rs = stmt.executeQuery(strDataViewQuery);
        int dcmID = 0;
        String itemName = "";
        int itemAmount = 0;
        String sqlString = "";
        while(rs.next())
        {
          dcmID = rs.getInt(1);
          itemName = rs.getString(2);
          itemAmount = rs.getInt(3);
          Utility.logger.debug("DCMID "+dcmID);
          Utility.logger.debug("item name "+itemName);
          Utility.logger.debug("item amount "+itemAmount);
          Long itemID = Utility.getSequenceNextVal(con,"SEQ_SIP_REPORT_ITEM");
          int item_id = itemID.intValue();
          Statement stmt2 =  con.createStatement();
          sqlString = "INSERT INTO SIP_REPORT_ITEM (SIP_REPORT_ITEM_ID,SIP_REPORT_ITEM_NAME,SIP_REPORT_DETAIL_ID"+
                      ",SIP_REPORT_ITEM_AMOUNT,DCM_ID) VALUES("+item_id+",'"+itemName+"',"+commissionID+","+itemAmount+
                      ","+dcmID+" )";
          int rows = stmt2.executeUpdate(sqlString);
          stmt2.close();
        }
        rs.close();
        String sqlString2 = "SELECT DISTINCT (SIP_REPORT_ITEM_NAME) FROM SIP_REPORT_ITEM WHERE SIP_REPORT_DETAIL_ID = "+commissionID;
        System.out.println("The  query  for getting  the item  name  to  be   inserted in report facor table   issss  "+sqlString2);
       
        rs = stmt.executeQuery(sqlString2);
        while(rs.next())
        {
        	System.out.println("Here  in  the insert  of sip  report factor");
        	Long factorID = Utility.getSequenceNextVal(con,"SEQ_SIP_REPORT_FACTOR");
          int factor_id = factorID.intValue();
          Statement stmt3 = con.createStatement(); 
          itemName = rs.getString("SIP_REPORT_ITEM_NAME");
          sqlString2 = "INSERT INTO SIP_REPORT_FACTOR (SIP_REPORT_FACTOR_ID , SIP_REPORT_FACTOR_NAME  ,SIP_REPORT_FACTOR_VALUE,SIP_REPORT_DETAIL_ID)"+
                      " VALUES ("+factor_id+",'"+itemName+"','1',"+commissionID+")";
            
          
          System.out.println("Insert sip  report factor  query  issssssss"   +sqlString2);
          int rows = stmt3.executeUpdate(sqlString2);
          sqlString2 = "UPDATE SIP_REPORT_ITEM SET SIP_REPORT_FACTOR_ID = "+factor_id+
                        " WHERE SIP_REPORT_DETAIL_ID = "+commissionID+" AND SIP_REPORT_ITEM_NAME = '"+
                        itemName+"'";
          rows = stmt3.executeUpdate(sqlString2);                        
          stmt3.close();
        }
        
      }
    res.close();
    stmt.close();
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  ////////////////////////////////////////////////////////////////////////////////////////////
  
  

  
  
  
  //type A DataViews 'CROSS TAB DATA VIEWS'
  public static void getCommissionFactorsFromDataView(Connection con , String commissionID) throws Exception
  {
    Statement stmt = con.createStatement();

     Vector factorIDs = new Vector();    
    String sql = "SELECT COMMISSION_DATA_VIEW_SQL FROM COMMISSION_DETAIL WHERE COMMISSION_DETAIL_ID = "+commissionID;
    Utility.logger.debug(sql);
    ResultSet res = stmt.executeQuery(sql);
      if(res.next())
      {
        String dataViewSQL = res.getString("COMMISSION_DATA_VIEW_SQL");
        Utility.logger.debug(dataViewSQL);
        ResultSet rs = stmt.executeQuery(dataViewSQL);
        if(rs.next())
        {
          ResultSetMetaData metaData = rs.getMetaData();
          int columnCount = metaData.getColumnCount();
         
          String item_name = "";
          factorIDs.add(0,"");              
          factorIDs.add(1,"");              
          for(int i = 2 ; i <= columnCount ; i++)
          {
              Statement stmt2 = con.createStatement();
              item_name = metaData.getColumnName(i);
              Long factorID = Utility.getSequenceNextVal(con,"SEQ_COMMISSION_FACTOR");
              int factor_id = factorID.intValue();
              factorIDs.add(i,factor_id+"");              
              String sqlString = "INSERT INTO COMMISSION_FACTOR (COMMISSION_FACTOR_ID , COMMISSION_FACTOR_NAME  ,COMMISSION_FACTOR_VALUE ,COMMISSION_DETAIL_ID)"+
                          " VALUES ("+factor_id+",'"+item_name+"','',"+commissionID+")";
              int rows = stmt2.executeUpdate(sqlString);               

        }
        }
        rs = stmt.executeQuery(dataViewSQL);
        while(rs.next())
        {
          ResultSetMetaData metaData = rs.getMetaData();
          int columnCount = metaData.getColumnCount();
          int DCM_ID = 0;
          int item_amount = 0;
          String item_name = "";
          DCM_ID = rs.getInt(1);        
          for(int i = 2 ; i <= columnCount ; i++)
          {
               item_amount = rs.getInt(i);
               item_name = metaData.getColumnName(i);
               Long itemID = Utility.getSequenceNextVal(con,"SEQ_COMMISSION_ITEM");
               int item_id = itemID.intValue();
               Statement stmt2 =  con.createStatement();
               String sqlString = "INSERT INTO COMMISSION_ITEM (COMMISSION_ITEM_ID,COMMISSION_ITEM_NAME,COMMISSION_DETAIL_ID"+
                           ",COMMISSION_ITEM_AMOUNT,DCM_ID , COMMISSION_FACTOR_ID ) VALUES("+item_id+",'"+item_name+"',"+commissionID+","+item_amount+
                           ","+DCM_ID+","+(String)factorIDs.get(i)+" )";
              int rows = stmt2.executeUpdate(sqlString);
               
          }
        }
      }
  }
  
  
  
  ////////////////////////////////////////////////////////////////////////
  
  
  
  
  
  
  
  //type A DataViews 'CROSS TAB DATA VIEWS'
  public static void getsipReprotFactorsFromDataView(Connection con , String commissionID) throws Exception
  {
 try
	 
	 {
	 Statement stmt = con.createStatement();
	    System.out.println(" Here in  the cross data views ");
     Vector factorIDs = new Vector();    
    String sql = "SELECT   REPORT_DATA_VIEW_SQL FROM  SIP_saved_REPORT WHERE REPORT_ID = "+commissionID;
    Utility.logger.debug(sql);
    ResultSet res = stmt.executeQuery(sql);
    
      if(res.next())
      {
        String dataViewSQL = res.getString("REPORT_DATA_VIEW_SQL");
        Utility.logger.debug(dataViewSQL);
        ResultSet rs = stmt.executeQuery(dataViewSQL);
        Statement stmt2 = con.createStatement();
        if(rs.next())
        {
          ResultSetMetaData metaData = rs.getMetaData();
          int columnCount = metaData.getColumnCount();

          String item_name = "";
          factorIDs.add(0,"");              
          factorIDs.add(1,"");              
          for(int i = 2 ; i <= columnCount ; i++)
          {
              
              item_name = metaData.getColumnName(i);
              Long factorID = Utility.getSequenceNextVal(con,"SEQ_SIP_REPORT_FACTOR");
              int factor_id = factorID.intValue();
              factorIDs.add(i,factor_id+"");              
              String sqlString = "INSERT INTO SIP_REPORT_FACTOR (SIP_REPORT_FACTOR_ID , SIP_REPORT_FACTOR_NAME  ,SIP_REPORT_FACTOR_VALUE ,SIP_REPORT_DETAIL_ID)"+
                          " VALUES ("+factor_id+",'"+item_name+"','1',"+commissionID+")";
            System.out.println("The getsipReprotFactorsFromDataView  query  is that insertr in SIP_REPORT_FACTOR  is   " +sqlString);
              
              int rows = stmt2.executeUpdate(sqlString);               

        }
        }
        rs = stmt.executeQuery(dataViewSQL);
        while(rs.next())
        {
          ResultSetMetaData metaData = rs.getMetaData();
          int columnCount = metaData.getColumnCount();
          int DCM_ID = 0;
         int item_amount = 0;
          String item_name = "";
          DCM_ID = rs.getInt(1);        
          for(int i = 2 ; i <= columnCount ; i++)
          {
               item_amount = rs.getInt(i);
               item_name = metaData.getColumnName(i);
               item_name = metaData.getColumnName(i);
               Long itemID = Utility.getSequenceNextVal(con,"SEQ_SIP_REPORT_ITEM");
               int item_id = itemID.intValue();
               
               String sqlString = "INSERT INTO SIP_REPORT_ITEM (SIP_REPORT_ITEM_ID,SIP_REPORT_ITEM_NAME,SIP_REPORT_DETAIL_ID"+
                           ",SIP_REPORT_ITEM_AMOUNT,DCM_ID , SIP_REPORT_FACTOR_ID ) VALUES("+item_id+",'"+item_name+"',"+commissionID+","+item_amount+
                           ","+DCM_ID+","+(String)factorIDs.get(i)+" )";
            
               System.out.println("The quey inserting in  the  SIP_REPORT_ITEM  is    " +sqlString);
               
               int rows = stmt2.executeUpdate(sqlString);
               
          }
        }
        rs.close ( );
    stmt2.close ( );
      }
      res.close ( );
      stmt.close ( );
	 }
	 catch(Exception ex)
	 {
		 
	ex.printStackTrace();	 
		 
	 }
	 }
  
  
  
  
  
  
  //////////////////////////////////////////////////////////////////////////
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public static void getOldCommissionFactorValues(Connection con , String oldCommissoinID,String newCommissionID) throws Exception
  {
    Statement stmt = con.createStatement();
    String sql = "SELECT COMMISSION_FACTOR_VALUE,COMMISSION_FACTOR_NAME FROM COMMISSION_FACTOR WHERE"+
                       " COMMISSION_DETAIL_ID = "+oldCommissoinID+" ORDER BY COMMISSION_FACTOR_NAME";
    ResultSet rs = stmt.executeQuery(sql);
    Statement stmt2 = con.createStatement();
    while(rs.next())
    {
      String sqlString = " UPDATE COMMISSION_FACTOR SET COMMISSION_FACTOR_VALUE  = "+rs.getDouble("COMMISSION_FACTOR_VALUE")+
                         " WHERE COMMISSION_DETAIL_ID = "+newCommissionID +" AND COMMISSION_FACTOR_NAME = '"+
                          rs.getString("COMMISSION_FACTOR_NAME")+"' ";
      Utility.logger.debug(sqlString);
      int rows = stmt2.executeUpdate(sqlString);
    }
    rs.close();
    stmt2.close();
    stmt.close();
  }
  
  
  /////////////////////////////////////////////////////
  
  
  
  
  public static void getOldsipReportFactorValues(Connection con , String oldCommissoinID,String newCommissionID) throws Exception
  {
    Statement stmt = con.createStatement();
    String sql = "SELECT SIP_REPORT_FACTOR_VALUE,SIP_REPORT_FACTOR_NAME FROM SIP_REPORT_FACTOR WHERE"+
                       " SIP_REPORT_DETAIL_ID = "+oldCommissoinID+" ORDER BY SIP_REPORT_FACTOR_NAME";
    ResultSet rs = stmt.executeQuery(sql);
    Statement stmt2 = con.createStatement();
    while(rs.next())
    {
      String sqlString = " UPDATE SIP_REPORT_FACTOR SET SIP_REPORT_FACTOR_VALUE  = "+rs.getDouble("SIP_REPORT_FACTOR_VALUE")+
                         " WHERE SIP_REPORT_DETAIL_ID = "+newCommissionID +" AND SIP_REPORT_FACTOR_NAME = '"+
                          rs.getString("SIP_REPORT_FACTOR_NAME")+"' ";
      Utility.logger.debug(sqlString);
      int rows = stmt2.executeUpdate(sqlString);
    }
    rs.close();
    stmt2.close();
    stmt.close();
  }
  
  
  
  
  
  //////////////////////////////////////////////////
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public FactorDAO()
  {
  }

}
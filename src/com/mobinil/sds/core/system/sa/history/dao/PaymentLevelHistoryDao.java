/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.sa.history.dao;

import com.mobinil.sds.core.system.sa.histDetail.model.PayLevelHistDetailModel;
import com.mobinil.sds.core.system.sa.history.model.PayLevelHistroyModel;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author sand
 */
public class PaymentLevelHistoryDao {
    
  private  PaymentLevelHistoryDao()
  {
  }

  public static boolean checkHistoryFile(String month , String year, String userId )
  {
        boolean exists =false;

        try
        {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "select * from GEN_DCM_PAYMENT_LEVEL_HISTORY where user_id!='null' and user_id = '"+userId+"' and year ='"+year+"' and month='"+month+"'";
          System.out.println("check history file sql : "+sql);
          ResultSet res = stat.executeQuery(sql);
          if(res.next())
          {
           exists = true;
          }
          Utility.closeConnection(con);      
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        return exists;
  }
  public static void invalidateHistoryFile(String month , String year, String userId )
  {
      try
        {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "update GEN_DCM_PAYMENT_LEVEL_HISTORY set status_id='2' where user_id = '"+userId+"' and year='"+year+"' and month ='"+month+"'";
          stat.executeQuery(sql);
          sql="commit";
          stat.executeUpdate(sql);
          Utility.closeConnection(con);      
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
  }
  public static String getHistoryFileId(String month , String year, String userId )
  {
      System.out.println("inside get history file id");
      String historyFileId = "";

        try
        {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "select history_file_id from GEN_DCM_PAYMENT_LEVEL_HISTORY where user_id = '"+userId+"' and year ='"+year+"' and month='"+month+"' order by history_file_id desc ";
          System.out.println("get history sql : "+sql);
          ResultSet res = stat.executeQuery(sql);
          if(res.next())
          {
            historyFileId = res.getString("history_file_id");
            System.out.println(" history id "+historyFileId);
          }
          Utility.closeConnection(con);      
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        return historyFileId;
  }
  
  
  
  public static String getHistoryFileMonthYeatByUserId(String userId )
  {
      System.out.println("STRING ARRAYYYYYYYY");
      String historyFileId = "";
      PayLevelHistroyModel payHist = new PayLevelHistroyModel();
      String MonthYear = "";
      int index=0;
      try
        {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "select * from GEN_DCM_PAYMENT_LEVEL_HISTORY where user_id = '"+userId+"'  order by history_file_id desc ";
          System.out.println(" SQL << "+sql);
          ResultSet res = stat.executeQuery(sql);
          
          while(res.next())
          {
            payHist = new PayLevelHistroyModel(res);
            String month = String.valueOf(Integer.toString(payHist.getMonth()));
            String year = String.valueOf(Integer.toString(payHist.getYear()));
            MonthYear += month+"_"+year;
            
            MonthYear +="__";
            
          }
          MonthYear = MonthYear.substring(0, MonthYear.length()-2);
          System.out.println("THEEEEEE MonthYear  :  "+MonthYear);
        
          Utility.closeConnection(con);      
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return MonthYear;
  }
  
  
  
  public static void insertHistoryFile(String month , String year, String userId )
  {
   
    try
        {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "insert into gen_dcm_payment_level_history (user_id,status_id, month,year) values ('"+userId+"','1','"+month+"','"+year+"')";
          System.out.println("insert history sql : "+sql);
          stat.executeUpdate(sql);
          sql="commit";
          stat.executeUpdate(sql);
          
          Utility.closeConnection(con);      
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }

    
  }
  public static void insertHistoryFileDetail(String historyId)
  {
    Vector histDetailVec = new Vector();
    PayLevelHistDetailModel payDetail;
    Vector histVec = new Vector();
    PayLevelHistroyModel payHist;
  
    try
        {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "insert into dcm_payment_level_hist_detail select '"+historyId+"', dcm_id, dcm_code,dcm_payment_level_id, channel_id from gen_dcm";
          System.out.println("insert detail history sql : "+sql);
          stat.executeUpdate(sql);
          sql="commit";
          stat.executeUpdate(sql);
          
          sql="select * from dcm_payment_level_hist_detail where history_file_id = '"+historyId+"' ";
          System.out.println("select id detail history sql : "+sql);
          ResultSet rs = stat.executeQuery(sql);
          while(rs.next())
          {
              payDetail = new PayLevelHistDetailModel(rs);
              histDetailVec.add((PayLevelHistDetailModel)payDetail);
          }
         /* sql="select * from gen_dcm_payment_level_history where history_file_id = '"+historyId+"' ";
          ResultSet rs2 = stat.executeQuery(sql);
          while(rs2.next())
          {
              payHist = new PayLevelHistroyModel(rs2);
              histVec.add((PayLevelHistroyModel)payHist);
          }*/
          
          Utility.closeConnection(con);      
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }

    
  }
}
    


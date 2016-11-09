/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.sa.monthList.dao;

import com.mobinil.sds.core.system.sa.monthList.model.MonthOfTheListModel;
import com.mobinil.sds.core.system.sa.monthListDetail.model.MonthOfTheListDetailModel;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author sand
 */
public class MonthOfTheListDao {
    
    
    public static void insertHistoryFile(String month , String year, String userId, String list )
  {
   
    try
        {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "insert into gen_dcm_month_list (history_file_id,user_id,status_id, month,year,list_name,timestamp) values ((select max(history_file_id)+1 from gen_dcm_month_list),'"+userId+"','1','"+month+"','"+year+"','"+list+"',sysdate)";
          System.out.println("insert moth list sql : "+sql);
          stat.executeUpdate(sql);
          stat.close();
          Utility.closeConnection(con);      
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        
    
  }
    
    public static String getHistoryFileId(String month , String year, String userId, String list )
  {
      System.out.println("get history file id");
      String historyFileId = "";

        try
        {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "select history_file_id from gen_dcm_month_list where user_id = '"+userId+"' and year ='"+year+"' and month='"+month+"' and list_name='"+list+"' order by history_file_id desc ";
          System.out.println("get history sql : "+sql);
          ResultSet res = stat.executeQuery(sql);
          if(res.next())
          {
            historyFileId = res.getString("history_file_id");
            System.out.println(" history id "+historyFileId);
          }
          stat.close();
          Utility.closeConnection(con);      
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        return historyFileId;
  }
  public static boolean checkHistoryFile(String month , String year, String userId, String list)
  {
        boolean exists =false;

        try
        {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "select * from gen_dcm_month_list where user_id!='null' and user_id = '"+userId+"' and year ='"+year+"' and month='"+month+"' and list_name = '"+list+"' ";
          System.out.println("check month list sql : "+sql);
          ResultSet res = stat.executeQuery(sql);
          if(res.next())
          {
           exists = true;
          }
          stat.close();
          Utility.closeConnection(con);      
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        //System.out.println("exists? "+exists);
        return exists;
  }
  public static String getHistoryFileMonthYeatByUserId(String userId )
  {
      System.out.println("getHistoryFileMonthYeatByUserId ... ");
      String historyFileId = "";
      MonthOfTheListModel listHist = new MonthOfTheListModel();
      String MonthYear = "";
      int index=0;
      try
        {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "select gen_dcm_month_list.*,dcm_month_list_file_status.status_name,gen_person.person_full_name from dcm_month_list_file_status,gen_person , gen_dcm_month_list where gen_dcm_month_list.user_id = gen_person.person_id and gen_dcm_month_list.status_id = dcm_month_list_file_status.status_id and user_id = '"+userId+"'  order by history_file_id desc ";
          ResultSet res = stat.executeQuery(sql);
          
          while(res.next())
          {
            listHist = new MonthOfTheListModel(res);
            String month = String.valueOf(Integer.toString(listHist.getMONTH()));
            String year = String.valueOf(Integer.toString(listHist.getYEAR()));
            MonthYear += month+"_"+year;
            
            MonthYear +="__";
            
          }
          MonthYear = MonthYear.substring(0, MonthYear.length()-2);
          System.out.println("THEEEEEE MonthYear  :  "+MonthYear);
          stat.close();
          Utility.closeConnection(con);      
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return MonthYear;
  }
  
  
  public static void insertHistoryFileDetail(String historyId, String list)
  {
    Vector histDetailVec = new Vector();
    MonthOfTheListDetailModel listDetail;
    Vector histVec = new Vector();
    MonthOfTheListDetailModel listHist;
  
    try
        {
          Connection con = Utility.getConnection();
          Statement stat = con.createStatement();
          String sql = "insert into gen_dcm_month_list_detail select '"+historyId+"',dcm_id, dcm_code,dcm_payment_level_id, channel_id,'"+list+"' from gen_dcm";
          System.out.println("INSERT detail month of the list sql : "+sql);
          stat.executeUpdate(sql);
          
          sql="select * from gen_dcm_month_list_detail where history_file_id = '"+historyId+"' ";
          System.out.println("select id detail month list sql : "+sql);
          ResultSet rs = stat.executeQuery(sql);
          while(rs.next())
          {
              listDetail = new MonthOfTheListDetailModel(rs);
              histDetailVec.add((MonthOfTheListDetailModel)listDetail);
          }
          stat.close();
          Utility.closeConnection(con);      
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }

    
  }
    
}

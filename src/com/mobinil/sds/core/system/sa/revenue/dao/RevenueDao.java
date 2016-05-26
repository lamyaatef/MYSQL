package com.mobinil.sds.core.system.sa.revenue.dao;

import java.sql.*;
import com.mobinil.sds.core.utilities.*;
import java.util.*;
import com.mobinil.sds.core.system.sa.revenue.model.*;

public class RevenueDao 
{
  private  RevenueDao()
  {
  }

  public static Hashtable getDCMRevenue(String month , String year )
  {
    Hashtable revenueTable= new Hashtable();
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      String sql = "select * from VW_DCC_DCM_REVENUE where year ='"+year+"' and month='"+month+"'";
    //  Utility.logger.debug(sql);
      ResultSet res = stat.executeQuery(sql);
      while(res.next())
      {
       RevenueModel model = new RevenueModel(res); 
       revenueTable.put(model.getDcmId() ,model);
      }
      Utility.closeConnection(con);      
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return revenueTable;
  }


  public static void updateRevenueAmount(String[] revenueRowId,String[] dcmIds,String[]  amounts ,String year, String month)
  {
  try
  {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    
  
    for (int i = 0 ; i < revenueRowId.length ;i++)
    {
      String dcmId =dcmIds[i];   
      String amount=amounts[i];  
      String rowId = revenueRowId[i];
      String sql ="";
      
      if ((rowId.compareTo("")==0) && (amount.compareTo("")!=0))
      {
        sql ="insert into  VW_DCC_DCM_REVENUE (revenue_id,dcm_id,year,month,amount) values("
            +"SEQ_DC_REVENUE_ID.nextval,"+dcmId+","+year+","+month+","+amount+")";
        stat.execute(sql);
      }
      else
      if((rowId.compareTo("")!=0) && (amount.compareTo("")!=0))
      {
        sql = "update VW_DCC_DCM_REVENUE set amount = "+amount+" where revenue_id="+rowId;
        stat.execute(sql);
      }
    //  Utility.logger.debug(sql);      
    }
    Utility.closeConnection(con);  
  }
  catch(Exception e)
  {
    e.printStackTrace();
  }
  }
}
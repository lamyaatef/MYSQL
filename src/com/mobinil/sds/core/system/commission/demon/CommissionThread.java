package com.mobinil.sds.core.system.commission.demon;
import com.mobinil.sds.web.interfaces.commission.CommissionInterfaceKey;
import com.mobinil.sds.core.system.commission.factor.dao.FactorDAO;
import com.mobinil.sds.core.system.commission.dao.CommissionDAO;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.system.commission.model.*;

public class CommissionThread extends Thread
{
  String dataViewType ="";
  int commissionID=0;
  Connection con = null;
  String strUserID = "";
  public void run()
  {
  Utility.logger.debug("in Thread ");
    try{
    	CommissionModel commissionModel =null;
    	
         if(dataViewType.equals(CommissionInterfaceKey.DATA_VIEW_TYPE_A)){
            FactorDAO.getCommissionFactorsFromDataView(con,commissionID+"");    
             commissionModel = CommissionDAO.getCommissionByID(con,commissionID+"");
            if(commissionModel.getCommissionBaseID() != 0)
              CommissionDAO.getCommissionBaseFactorValue(con,commissionModel.getCommissionBaseID()+"" , commissionID+"");
            updateFactors(con, commissionModel);
        //    CommissionDAO.updateCommissionStatus(con,String.valueOf(commissionID),"2",strUserID);
         }
         else if (dataViewType.equals(CommissionInterfaceKey.DATA_VIEW_TYPE_B)){
            FactorDAO.getFactorsFromDataView(con,commissionID+"");    
             commissionModel = CommissionDAO.getCommissionByID(con,commissionID+"");            
            if(commissionModel.getCommissionBaseID() != 0)
              CommissionDAO.getCommissionBaseFactorValue(con,commissionModel.getCommissionBaseID()+"" , commissionID+"");            
            updateFactors(con, commissionModel);
       //     CommissionDAO.updateCommissionStatus(con,String.valueOf(commissionID),"2",strUserID);
         }         
         System.out.println("subtract ="+  commissionModel.getCommissionSubtractId());
         if (commissionModel.getCommissionSubtractId()!=null)
         {
        	 Statement stat = con.createStatement();
        	 String sql = "UPDATE COMMISSION_ITEM c SET COMMISSION_ITEM_AMOUNT ="+
        				"nval  ( ( select  ( a.commission_item_amount  -  b.commission_item_amount) COMMISSION_ITEM_AMOUNT "+        				
        				" from commission_item a, commission_item b "+
        				" where a.commission_detail_id="+ commissionModel.getCommissionID()+
        				" and b.commission_detail_id = "+commissionModel.getCommissionSubtractId() +
        				" and a.COMMISSION_ITEM_NAME = b.COMMISSION_ITEM_NAME "+        				
        				" and a.DCM_ID = b.DCM_ID "+
        				" AND A.DCM_ID = c.DCM_ID "+
        				 
        				" AND A.COMMISSION_ITEM_NAME = c.COMMISSION_ITEM_NAME "+
        				" ) , COMMISSION_ITEM_AMOUNT ) "+        				
        				" WHERE c.COMMISSION_DETAIL_ID ="+ commissionModel.getCommissionID();

        	 System.out.println(sql);
        	 stat.execute(sql);
                 
        	 stat.close();
         }
         CommissionDAO.updateCommissionStatus(con,String.valueOf(commissionID),"2",strUserID);
             
         con.close();
         Utility.logger.debug("done threading.");
    }
    catch(Exception ex)
    {
      if(con!=null)
        try{
          con.close();
        }catch(Exception e){}
      Utility.logger.debug("Error in Commission Thread: "+ex.getMessage()+".");
      ex.printStackTrace();
    }
  }


  public CommissionThread(String dataViewTyp, int commID ,String strUserID,String []conStr)
  {
    dataViewType =  dataViewTyp;
    commissionID = commID;
    try
    {
      con = DriverManager.getConnection(conStr[0],conStr[1],conStr[2]);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    this.strUserID = strUserID;
    
  }
  private void updateFactors(Connection con,CommissionModel comModel){
/*
      String updateQuery = "update COMMISSION_FACTOR cfac set COMMISSION_FACTOR_VALUE = " +
 " (select fval.FACTORVALUE from DP_FINAL_FACTOR_VALUE fval where LOWER(fval.FACTORNAME) = LOWER(cfac.COMMISSION_FACTOR_NAME) and fval.DP_ID='"+comModel.getDpId()+"')"+
 " where cfac.COMMISSION_DETAIL_ID = '"+comModel.getCommissionID()+"'";      
  DBUtil.executeSQL(updateQuery, con);
  */
      //DP Commented line
  }
}
package com.mobinil.sds.core.utilities;

import com.mobinil.sds.web.interfaces.commission.CommissionInterfaceKey;
import com.mobinil.sds.web.interfaces.sip.SIPInterfaceKey;
import com.mobinil.sds.core.system.commission.factor.dao.FactorDAO;
import com.mobinil.sds.core.system.commission.factor.model.FactorModel;
import com.mobinil.sds.core.system.commission.dao.CommissionDAO;
import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.system.commission.model.*;
import com.mobinil.sds.core.system.sip.dao.SipDAO;
import com.mobinil.sds.core.system.sip.model.savedSipReportModel;
import com.mobinil.sds.core.system.sip.model.sipReportModel;

public class sipReportThread extends Thread
{
  String dataViewType ="";
  int sipReportId=0;
  Connection con = null;
  String strUserID = "";
  public void run()
  {
  Utility.logger.debug("in Thread ");
    try{
    	
    	System.out.println("Thread Started   in  util ");
    	savedSipReportModel sipReportModel =null;
    	
         if(dataViewType.equals(SIPInterfaceKey.DATA_VIEW_TYPE_A)){
        	 System.out.println("Thread Started   in  factor dao    type   expected vnormal 1111");
        	 
        	// FactorDAO.getsipReprotFactorsFromDataView(con,sipReportId+"");    
        	 FactorDAO.getsipReprotFactorsFromDataView(con,sipReportId+"");  
        	 sipReportModel = SipDAO.getSavedsipReportByID(con,sipReportId+"");  
 
            SipDAO.updatesipReportStatus(con,String.valueOf(sipReportId),"2",strUserID);
         }
         else if (dataViewType.equals(SIPInterfaceKey.DATA_VIEW_TYPE_B)){
        	 System.out.println("Thread Started  normal in  factor dao  22222");
              FactorDAO.getsipReportFactorsFromNormalDataView(con,sipReportId+"");    
              sipReportModel = SipDAO.getSavedsipReportByID(con,sipReportId+"");            
                  
            SipDAO.updatesipReportStatus(con,String.valueOf(sipReportId),"2",strUserID);
         }
   con.close();
         Utility.logger.debug("done threading.");
         
         System.out.println("The  Thread is   Done");
    }
    catch(Exception ex)
    {
      if(con!=null)
        try{
          con.close();
        }catch(Exception e){}
      Utility.logger.debug("Error in SIP  REPORT Thread: "+ex.getMessage()+".");
      
      
      System.out.println("Error in  Threading ");
      ex.printStackTrace();
    }
  }


  public sipReportThread(String dataViewTyp,int reportId,String strUserID)
  {
    dataViewType =  dataViewTyp;
    sipReportId = reportId;
    try
    {
      con = Utility.getConnection();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    this.strUserID = strUserID;
    
  }
}
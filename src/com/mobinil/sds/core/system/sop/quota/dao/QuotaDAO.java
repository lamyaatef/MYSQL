package com.mobinil.sds.core.system.sop.quota.dao;
import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.sop.*;
import com.mobinil.sds.core.system.sop.quota.model.*;

import com.mobinil.sds.core.system.gn.querybuilder.dao.*;
import com.mobinil.sds.core.system.gn.querybuilder.domain.*;
import com.mobinil.sds.core.system.gn.querybuilder.dto.*;
import com.mobinil.sds.core.system.gn.querybuilder.model.*;

public class QuotaDAO 
{
  private QuotaDAO()
  {
  }

  public static Vector getDcmQuota(Connection con,String dcmId)
  {
    Vector vecSop = new Vector();

    java.util.Date currentDate = new java.util.Date();

    int currentYear = currentDate.getYear()+1900;
    int currentMonth = currentDate.getMonth()+1;
    int currentDay = currentDate.getDate();

    String strCurrentDate = currentYear+"/"+currentMonth+"/"+currentDay;
    
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SOP_DCM_QUOTA where DCM_ID = "+dcmId+" and VALID_TO > TO_DATE('"+strCurrentDate+"','yyyy/mm/dd') order by VALID_FROM");     
     while(res.next())
     {
        vecSop.add(new DCMQuotaModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return vecSop; 
  }

  public static String getDcmCurrentQuota(Connection con,String dcmId)
  {
    String dcmQuota = "";
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from VW_SOP_DCM_CURRENT_QUOTA where DCM_ID = "+dcmId+" ";
     
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
        dcmQuota = res.getString("DCM_QUOTA");
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return dcmQuota; 
  }

  public static void updateDcmQuotaAfterRequest(Connection con,String dcmId,String requestProductQuota,String operation)
  {
    java.util.Date currentDate = new java.util.Date();

    int currentYear = currentDate.getYear()+1900;
    int currentMonth = currentDate.getMonth()+1;
    int currentDay = currentDate.getDate();

    String strCurrentDate = currentYear+"/"+currentMonth+"/"+currentDay;

    
    try
    {
      Statement stat = con.createStatement();      
      String updateSql = "update SOP_DCM_QUOTA "+
                         " set DCM_QUOTA = DCM_QUOTA "+operation+" "+requestProductQuota+" "+
                         " where DCM_ID = "+dcmId+" "+
                         " and VALID_FROM <= TO_DATE('"+strCurrentDate+"','yyyy/mm/dd') "+
                         " and VALID_TO > TO_DATE('"+strCurrentDate+"','yyyy/mm/dd') ";
      //Utility.logger.debug(insertSql);
      stat.execute(updateSql); 
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }


  public static void updateDcmQuota(Connection con,String dcmQuotaId,String dcmQuota)
  {
    try
    {
     Statement stat = con.createStatement();
     
     String strSql = "update SOP_DCM_QUOTA set DCM_QUOTA = '"+dcmQuota+"' where DCM_QUOTA_ID = "+dcmQuotaId+" ";

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);
        
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void updateFixedDcmQuota(Connection con,String dcmId,String dcmQuota)
  {
    try
    {
     Statement stat = con.createStatement();
     
     String strSql = "update SOP_DCM_QUOTA set DCM_QUOTA = '"+dcmQuota+"' "+
                     " where DCM_ID = "+dcmId+" "+
                     " and SYSDATE >= valid_from AND SYSDATE < valid_to ";

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);
        
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static String selectCurrentFixedDcmQuota(Connection con,String dcmId)
  {
    String dcmQuota = "";
    try
    {
     Statement stat = con.createStatement();
     
     String strSql = "select * from SOP_DCM_QUOTA "+
                     " where DCM_ID = "+dcmId+" "+
                     " and SYSDATE >= valid_from AND SYSDATE < valid_to ";

     ResultSet res = stat.executeQuery(strSql);
     if(res.next())
     {
       dcmQuota = res.getString("DCM_QUOTA");
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return dcmQuota;
  }
  
  public static void insertDcmQuota(Connection con,Long dcmQuotaId,String dcmId,String dcmQuota,String validFrom,String validTo)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "insert into SOP_DCM_QUOTA (DCM_QUOTA_ID,DCM_ID,DCM_QUOTA,VALID_FROM,VALID_TO) values("+dcmQuotaId+","+dcmId+",'"+dcmQuota+"',TO_DATE('"+validFrom+"','yyyy/mm/dd'),TO_DATE('"+validTo+"','yyyy/mm/dd'))";

     Utility.logger.debug(strSql);  
     stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void insertFixedDcmQuota(Connection con,Long dcmQuotaId,String dcmId,String dcmQuota,int validityDays)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "insert into SOP_DCM_QUOTA (DCM_QUOTA_ID,DCM_ID,DCM_QUOTA,VALID_FROM,VALID_TO) values("+dcmQuotaId+","+dcmId+",'"+dcmQuota+"',SYSDATE,SYSDATE+"+validityDays+")";

     Utility.logger.debug(strSql);  
     stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }
  
  public static void insertDcmQuotaLog(Connection con,Long dcmQuotaLogId,String dcmQuotaId,String dcmId,String userId,String dcmQuotaOld,String dcmQuotaNew,String validFrom,String validTo,String changeReason)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "insert into SOP_DCM_QUOTA_LOG (DCM_QUOTA_LOG_ID,DCM_QUOTA_ID,DCM_ID,USER_ID,DCM_QUOTA_OLD,DCM_QUOTA_NEW,VALID_FROM,VALID_TO,CHANGE_REASON ) "+
                     " values("+dcmQuotaLogId+","+dcmQuotaId+","+dcmId+","+userId+",'"+dcmQuotaOld+"','"+dcmQuotaNew+"',TO_DATE('"+validFrom+"','yyyy-mm-dd'),TO_DATE('"+validTo+"','yyyy-mm-dd'),'"+changeReason+"')";

     Utility.logger.debug(strSql);  
     stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static DCMQuotaModel getDcmQuotaSetting(Connection con,String dcmId)
  {
    DCMQuotaModel dcmQuotaModel = new DCMQuotaModel();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from SOP_DCM_QUOTA_SETTING where DCM_ID = "+dcmId+" ";
  
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
        dcmQuotaModel.setDcmId(res.getString(DCMQuotaModel.DCM_ID));
        dcmQuotaModel.setDcmQuotaSettingId(res.getString(DCMQuotaModel.DCM_QUOTA_SETTING_ID));
        dcmQuotaModel.setValidDays(res.getInt(DCMQuotaModel.VALID_DAYS));
        dcmQuotaModel.setRecalculateDays(res.getInt(DCMQuotaModel.RECALCULATE_DAYS));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return dcmQuotaModel; 
  }


  public static void updateDcmQuotaSetting(Connection con,String dcmId,String validDays,String recalculateDays)
  {
    try
    {
     Statement stat = con.createStatement();

     
     String strSql = "update SOP_DCM_QUOTA_SETTING set VALID_DAYS = "+validDays+",RECALCULATE_DAYS = "+recalculateDays+" where DCM_ID = "+dcmId+" ";

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);
        
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void updateDcmQuotaSetting(Connection con,String dcmId,String recalculateDayDate)
  {
    try
    {
     Statement stat = con.createStatement();

     
     String strSql = "update SOP_DCM_QUOTA_SETTING set RECALCULATE_DAY_DATE = TO_DATE('"+recalculateDayDate+"','yyyy/mm/dd') where DCM_ID = "+dcmId+" ";

     Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);
        
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void insertDcmQuotaSetting(Connection con,String dcmId,String validDays,String recalculateDays)
  {
    try
    {
     Statement stat = con.createStatement();

     Long lDcmQuotaSettingID = Utility.getSequenceNextVal(con, "SEQ_SOP_DCM_QUOTA_SETTING_ID"); 
     String strSql = "insert into SOP_DCM_QUOTA_SETTING (DCM_QUOTA_SETTING_ID,DCM_ID,VALID_DAYS,RECALCULATE_DAYS) values("+lDcmQuotaSettingID+","+dcmId+","+validDays+","+recalculateDays+")";

     Utility.logger.debug(strSql);  
     stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static String dcmQuotaCalculationsChecking(Connection con)
  {
    String strMsg = "Failed to calculate quota.";
    try
    {
      Statement stat = con.createStatement();
      Statement stat2 = con.createStatement();

      Vector validDCMForCalculations = QuotaDAO.getValidDcmQuotaSettingForCalculation(con);
      
      HashMap dcmQuotaHashMap = new HashMap();
      String strQuery = "select * from ADH_DATA_VIEW where DATA_VIEW_NAME = 'quota_calculations_test2' "+
                        " and data_view_version = (select max(data_view_version) from ADH_DATA_VIEW where DATA_VIEW_NAME = 'quota_calculations_test2' )";
      ResultSet res = stat.executeQuery(strQuery);
      if(res.next())
      {
        int intDataViewId = res.getInt("DATA_VIEW_ID");

        QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine();
        Vector vec =new Vector();
        QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO(con,intDataViewId,vec);
        String strDataViewQuery = queryBuilderEngine.constructQuerySQL(queryDTO);
        Utility.logger.debug(strDataViewQuery);
        ResultSet res2 = stat2.executeQuery(strDataViewQuery);
        while(res2.next())
        {
          String strDCMCode = res2.getString("DCM_CODE");
          int intProductAmount = res2.getInt("QUOTA_AMOUNT");
          Utility.logger.debug(strDCMCode+"-------"+intProductAmount);
          dcmQuotaHashMap.put(strDCMCode,intProductAmount+"");
        }
        res2.close();
      }
      for(int i=0;i<validDCMForCalculations.size();i++)
      {
        DCMQuotaModel dcmQuotaModel = (DCMQuotaModel)validDCMForCalculations.get(i);
        String strDCMCode = dcmQuotaModel.getDcmCode();
        String strDCMID = dcmQuotaModel.getDcmId();
        int intRecalculateDays = dcmQuotaModel.getRecalculateDays();
        int intValidDays = dcmQuotaModel.getValidDays();
        int numberOfInsertion = intRecalculateDays/intValidDays;
        if(dcmQuotaHashMap.containsKey(strDCMCode))
        {
          String strDCMQuotaValue = (String)dcmQuotaHashMap.get(strDCMCode);
          for(int j=0;j<numberOfInsertion;j++)
          {
            Long lDCMQuotaID = Utility.getSequenceNextVal(con, "SEQ_SOP_DCM_QUOTA_ID");
            QuotaDAO.insertDcmQuota(con,lDCMQuotaID,strDCMID,strDCMQuotaValue,j*intValidDays,(j+1)*intValidDays);
          }
          QuotaDAO.updateDcmQuotaSetting(con,strDCMID,intRecalculateDays+1);
        }
      }

      res.close();
      stat2.close();
      stat.close();
      strMsg = "Quota calculated succesfully.";
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return strMsg;
  }

  public static Vector getValidDcmQuotaSettingForCalculation(Connection con)
  {
    Vector vec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     String strSql = "select * from VW_SOP_DCM_QUOTA_SETTING where RECALCULATE_DAY_DATE <= SYSDATE ";
  
     ResultSet res= stat.executeQuery(strSql);     
     while(res.next())
     {
        DCMQuotaModel dcmQuotaModel = new DCMQuotaModel();
        dcmQuotaModel.setDcmQuotaSettingId(res.getString(dcmQuotaModel.DCM_QUOTA_SETTING_ID));
        dcmQuotaModel.setDcmCode(res.getString(dcmQuotaModel.DCM_CODE));
        dcmQuotaModel.setDcmId(res.getString(dcmQuotaModel.DCM_ID));
        dcmQuotaModel.setValidDays(res.getInt(dcmQuotaModel.VALID_DAYS));
        dcmQuotaModel.setRecalculateDays(res.getInt(dcmQuotaModel.RECALCULATE_DAYS));
        vec.add(dcmQuotaModel);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return vec; 
  }

  public static void insertDcmQuota(Connection con,Long dcmQuotaId,String dcmId,String dcmQuota,int validFrom,int validTo)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "insert into SOP_DCM_QUOTA (DCM_QUOTA_ID,DCM_ID,DCM_QUOTA,VALID_FROM,VALID_TO) values("+dcmQuotaId+","+dcmId+",'"+dcmQuota+"',SYSDATE+"+validFrom+",SYSDATE+"+validTo+")";

     Utility.logger.debug(strSql);  
     stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void updateDcmQuotaSetting(Connection con,String dcmId,int recalculateDayDate)
  {
    try
    {
     Statement stat = con.createStatement();

     
     String strSql = "update SOP_DCM_QUOTA_SETTING set RECALCULATE_DAY_DATE = SYSDATE+"+recalculateDayDate+" where DCM_ID = "+dcmId+" ";

     //Utility.logger.debug(strSql); 
     stat.executeUpdate(strSql);
        
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }
}
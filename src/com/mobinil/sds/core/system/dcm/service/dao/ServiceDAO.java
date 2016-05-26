package com.mobinil.sds.core.system.dcm.service.dao;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.dcm.*;
import com.mobinil.sds.core.system.dcm.service.model.*;

public class ServiceDAO 
{
  public static Vector getPosServicesByFilter(Connection con,String serviceName,String serviceStatusTypeId,String serviceEligibiltyTypeId,String posName,String posCode)
  {
    Vector vec = new Vector();
    boolean andFlag = false;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from VW_DCM_POS_SERVICE ";
      if(serviceName.compareTo("")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " POS_SERVICE_NAME like '%"+serviceName+"%' ";
        andFlag = true;
      }
      if(serviceStatusTypeId.compareTo("")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " POS_SERVICE_STATUS_TYPE_ID = '"+serviceStatusTypeId+"' ";
        andFlag = true;
      }
      if(serviceEligibiltyTypeId.compareTo("")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " SERVICE_ELIGIBILITY_TYPE_ID = '"+serviceEligibiltyTypeId+"' ";
        andFlag = true;
      }
      if(posName.compareTo("")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " POS_SERVICE_ID in (select POS_SERVICE_ID from VW_DCM_POS_SERVICE_ELIGIBILITY where POS_NAME = '"+posName+"') ";
        andFlag = true;
      }
      if(posCode.compareTo("")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " POS_SERVICE_ID in (select POS_SERVICE_ID from VW_DCM_POS_SERVICE_ELIGIBILITY where POS_CODE = '"+posCode+"') ";
        andFlag = true;
      }
      strSql += " order by POS_SERVICE_STATUS_TYPE_ID,POS_SERVICE_NAME ";
      
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        vec.add(new ServiceModel(res));
      }
      res.close();
      stat.close();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return vec;
  }

  public static Vector getPosServiceRequestsByFilter(Connection con,String caseTitle,String serviceName,String posName,String posCode,String initiateTimeStampFrom,String initiateTimeStampTo,String serviceRequestStatusTypeId)
  {
    Vector vec = new Vector();
    boolean andFlag = false;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from VW_DCM_SERVICE_REQUEST ";
      if(serviceName.compareTo("")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " POS_SERVICE_NAME like '%"+serviceName+"%' ";
        andFlag = true;
      }
      if(serviceRequestStatusTypeId.compareTo("")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " SERVICE_REQ_STATUS_TYPE_ID = "+serviceRequestStatusTypeId+" ";
        andFlag = true;
      }
      if(caseTitle.compareTo("")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " CASE_TITLE like '%"+caseTitle+"%' ";
        andFlag = true;
      }
      if(posCode.compareTo("")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " POS_CODE = '"+posCode+"' ";
        andFlag = true;
      }
      if(posName.compareTo("")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " POS_NAME like '%"+posName+"%' ";
        andFlag = true;
      }
      if(initiateTimeStampFrom.compareTo("*")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " INITIATE_TIMESTAMP >= TO_DATE('"+initiateTimeStampFrom+"','mm/dd/yyyy') ";
        andFlag = true;
      }
      if(initiateTimeStampTo.compareTo("*")!=0)
      {
        if(!andFlag)strSql += " where ";
        else strSql += " and ";
        strSql += " INITIATE_TIMESTAMP <= TO_DATE('"+initiateTimeStampTo+"','mm/dd/yyyy')+1 ";
        andFlag = true;
      }
      strSql += " order by SERVICE_REQUEST_ID ";

      Utility.logger.debug(strSql);
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        ServiceRequestModel serviceRequestModel = new ServiceRequestModel(res);
        String serviceRequestLastStatusId = serviceRequestModel.getServiceRequestLastStatusId();
        serviceRequestModel.setServiceRequestWarnings(ServiceDAO.getServiceRequestWarnings(con,serviceRequestLastStatusId));
        vec.add(serviceRequestModel);
      }
      res.close();
      stat.close();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return vec;
  }

  public static ServiceModel getPosServicesById(Connection con,String posServiceId)
  {
    ServiceModel serviceModel = null;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from VW_DCM_POS_SERVICE where POS_SERVICE_ID = "+posServiceId+" ";
      ResultSet res = stat.executeQuery(strSql);

      if(res.next())
      {
        serviceModel = new ServiceModel(res);
      }
      res.close();
      stat.close();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return serviceModel;
  }

  public static void insertPosService(Connection con,String serviceName,String serviceDesc,String serviceStatusTypeId,String serviceEligibiltyTypeId)
  {
    try
    {
      Statement stat = con.createStatement();
      Long lPosServiceID = Utility.getSequenceNextVal(con, "SEQ_DCM_POS_SERVICE_ID");
      String strSql = "insert into DCM_POS_SERVICE(POS_SERVICE_ID,POS_SERVICE_NAME,POS_SERVICE_DESC,SERVICE_ELIGIBILITY_TYPE_ID,POS_SERVICE_STATUS_TYPE_ID) "+
                      " values("+lPosServiceID+",'"+serviceName+"','"+serviceDesc+"',"+serviceEligibiltyTypeId+","+serviceStatusTypeId+")";
      //System.out.print(strSql);
      stat.executeUpdate(strSql);
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }


  public static void updatePosService(Connection con,String serviceId,String serviceName,String serviceDesc,String serviceStatusTypeId,String serviceEligibiltyTypeId)
  {
    try
    {
      Statement stat = con.createStatement();

      String strSql = "update DCM_POS_SERVICE set POS_SERVICE_NAME = '"+serviceName+"',POS_SERVICE_DESC = '"+serviceDesc+"',SERVICE_ELIGIBILITY_TYPE_ID = "+serviceEligibiltyTypeId+",POS_SERVICE_STATUS_TYPE_ID = "+serviceStatusTypeId+" "+
                      " where POS_SERVICE_ID = "+serviceId+" ";

      System.out.print(strSql);
      stat.executeUpdate(strSql);
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void updatePosService(Connection con,String serviceId,String serviceStatusTypeId)
  {
    try
    {
      Statement stat = con.createStatement();

      String strSql = "update DCM_POS_SERVICE set POS_SERVICE_STATUS_TYPE_ID = "+serviceStatusTypeId+" "+
                      " where POS_SERVICE_ID = "+serviceId+" ";

      System.out.print(strSql);
      stat.executeUpdate(strSql);
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public static HashMap getPosEligiableForService(Connection con,String posServiceId)
  {
    HashMap posAssignedToServiceHashMap = new HashMap();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from VW_DCM_POS_SERVICE_ELIGIBILITY where POS_SERVICE_ID = "+posServiceId+" ";
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        String posCode = res.getString("POS_CODE");
        String posName = res.getString("POS_NAME");
        posAssignedToServiceHashMap.put(posCode,posName);
      }
      res.close();
      stat.close();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return posAssignedToServiceHashMap;
  }

  public static Vector getServiceRequestWarnings(Connection con,String serviceRequestStatusId)
  {
    Vector vec = new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select DCM_SERVICE_REQ_WARNING_TYPE.* from DCM_SERVICE_REQ_WARNING_TYPE,DCM_SERVICE_REQUEST_WARNINIG "+
                      " where DCM_SERVICE_REQUEST_WARNINIG.SERVICE_REQUEST_STATUS_ID = "+serviceRequestStatusId+" "+
                      " and DCM_SERVICE_REQUEST_WARNINIG.SERVICE_REQ_WARNING_TYPE_ID = DCM_SERVICE_REQ_WARNING_TYPE.SERVICE_REQ_WARNING_TYPE_ID ";
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        String warninTypeName = res.getString("SERVICE_REQ_WARNING_TYPE_NAME");
        vec.add(warninTypeName);
      }
      res.close();
      stat.close();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return vec;
  }

  public static void insertPosEligiableForService(Connection con,String posCode,String serviceId)
  {
    try
    {
      Statement stat = con.createStatement();
      Long lPosServiceEligibilityID = Utility.getSequenceNextVal(con, "SEQ_DCM_SERVICE_ELIGIBILIT_ID");
      String strSql = "insert into DCM_POS_SERVICE_ELIGIBILITY(POS_SERVICE_ELIGIBILITY_ID,POS_CODE,POS_SERVICE_ID) "+
                      " values("+lPosServiceEligibilityID+",'"+posCode+"',"+serviceId+")";
      //System.out.print(strSql);
      stat.executeUpdate(strSql);
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void deletePosEligiableForService(Connection con,String posCode,String serviceId)
  {
    try
    {
      Statement stat = con.createStatement();
      String strSql = "delete from DCM_POS_SERVICE_ELIGIBILITY where POS_CODE = '"+posCode+"' and POS_SERVICE_ID = "+serviceId+" ";
      //System.out.print(strSql);
      stat.executeUpdate(strSql);
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }


  public static boolean checkPosCodeExists(Connection con,String posCode)
  {
    boolean posCodeExists = false;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from gen_dcm where DCM_CODE = '"+posCode+"' ";
      ResultSet res = stat.executeQuery(strSql);
      if(res.next())
      {
        posCodeExists = true;
      }
      res.close();
      stat.close();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return posCodeExists;
  }

  public static boolean checkPosServiceEligibilityExists(Connection con,String posCode,String posServiceId)
  {
    boolean posServicEligibilityExists = false;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from DCM_POS_SERVICE_ELIGIBILITY where POS_CODE = '"+posCode+"' and POS_SERVICE_ID = "+posServiceId+" ";
      ResultSet res = stat.executeQuery(strSql);
      if(res.next())
      {
        posServicEligibilityExists = true;
      }
      res.close();
      stat.close();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return posServicEligibilityExists;
  }

  public static Vector getTmpServiceEligibility(Connection con)
  {
    Vector vec =  new Vector();
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from TMP_SERVICE_ELIGIBILITY order by ROW_NUM";
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        vec.add(new PosServiceImportModel(res));
      }
      res.close();
      stat.close();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return vec;
  }

  public static void deleteTmpServiceEligibility(Connection con)
  {
    try
    {
      Statement stat = con.createStatement();
      String strSql = "delete from TMP_SERVICE_ELIGIBILITY ";
      //System.out.print(strSql);
      stat.executeUpdate(strSql);
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public static Long insertDcmServiceRequest(Connection con,String caseId,String posCode,String posServiceId)
  {
    Long lDcmServiceRequestID = null;
    try
    {
      Statement stat = con.createStatement();
      lDcmServiceRequestID = Utility.getSequenceNextVal(con, "SEQ_DCM_SERVICE_REQUEST_ID");
      
      Long lDcmServiceRequestStatusId = ServiceDAO.insertDcmServiceRequestStatus(con,lDcmServiceRequestID+"","1");                

      String strSql = "insert into DCM_SERVICE_REQUEST(SERVICE_REQUEST_ID,CASE_ID,POS_CODE,POS_SERVICE_ID,LAST_SERVICE_REQUEST_STATUS_ID) "+
                      " values("+lDcmServiceRequestID+","+caseId+",'"+posCode+"',"+posServiceId+","+lDcmServiceRequestStatusId+")";

      //System.out.print(strSql);
      stat.executeUpdate(strSql);
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return lDcmServiceRequestID;
  }

  public static Long insertDcmServiceRequestStatus(Connection con,String serviceRequestId,String serviceRequestStatusTypeId)
  {
    Long lDcmServiceRequestStatusID = null;
    try
    {
      Statement stat = con.createStatement();
      lDcmServiceRequestStatusID = Utility.getSequenceNextVal(con, "SEQ_DCM_SERVICE_REQ_STATUS_ID");
      String strSql = "insert into DCM_SERVICE_REQUEST_STATUS(SERVICE_REQUEST_STATUS_ID,SERVICE_REQUEST_ID,SERVICE_REQ_STATUS_TYPE_ID) "+
                      " values("+lDcmServiceRequestStatusID+","+serviceRequestId+","+serviceRequestStatusTypeId+")";
      //System.out.print(strSql);
      stat.executeUpdate(strSql);
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return lDcmServiceRequestStatusID;
  }
}
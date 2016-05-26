package com.mobinil.sds.core.system.dcm.service.model;
import java.sql.*;
import java.io.*;

public class ServiceRequestStatusModel implements Serializable
{
  String serviceRequestStatusId = "";
  String serviceRequestId = "";
  String caseId = "";
  String posCode = "";
  String posServiceId = "";
  String serviceReqStatusTypeId = "";
  String serviceReqStatusTypeName = "";
  
  public static final String SERVICE_REQUEST_STATUS_ID = "";
  public static final String SERVICE_REQUEST_ID = "";
  public static final String CASE_ID = "";
  public static final String POS_CODE = "";
  public static final String POS_SERVICE_ID = "";
  public static final String SERVICE_REQ_STATUS_TYPE_ID = "";
  public static final String SERVICE_REQ_STATUS_TYPE_NAME = "";

  public ServiceRequestStatusModel()
  {
  }

  public ServiceRequestStatusModel(ResultSet res)
  {
    try
    {
      serviceRequestStatusId = res.getString(SERVICE_REQUEST_STATUS_ID);
      serviceRequestId = res.getString(SERVICE_REQUEST_ID);
      caseId = res.getString(CASE_ID);
      posCode = res.getString(POS_CODE);
      posServiceId = res.getString(POS_SERVICE_ID);
      serviceReqStatusTypeId = res.getString(SERVICE_REQ_STATUS_TYPE_ID);
      serviceReqStatusTypeName = res.getString(SERVICE_REQ_STATUS_TYPE_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public String getServiceRequestStatusId()
  {
  return serviceRequestStatusId;
  }
  public void setServiceRequestStatusId(String newServiceRequestStatusId)
  {
  serviceRequestStatusId= newServiceRequestStatusId;
  }

  public String getServiceRequestId()
  {
  return serviceRequestId;
  }
  public void setServiceRequestId(String newServiceRequestId)
  {
  serviceRequestId= newServiceRequestId;
  }

  public String getCaseId()
  {
  return caseId;
  }
  public void setCaseId(String newCaseId)
  {
  caseId= newCaseId;
  }

  public String getPosCode()
  {
  return posCode;
  }
  public void setPosCode(String newPosCode)
  {
  posCode= newPosCode;
  }

  public String getPosServiceId()
  {
  return posServiceId;
  }
  public void setPosServiceId(String newPosServiceId)
  {
  posServiceId= newPosServiceId;
  }

  public String getServiceReqStatusTypeId()
  {
  return serviceReqStatusTypeId;
  }
  public void setServiceReqStatusTypeId(String newServiceReqStatusTypeId)
  {
  serviceReqStatusTypeId= newServiceReqStatusTypeId;
  }

  public String getServiceReqStatusTypeName()
  {
  return serviceReqStatusTypeName;
  }
  public void setServiceReqStatusTypeName(String newServiceReqStatusTypeName)
  {
  serviceReqStatusTypeName= newServiceReqStatusTypeName;
  }
}
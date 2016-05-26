package com.mobinil.sds.core.system.dcm.service.model;
import java.sql.*;
import java.io.*;
import java.util.Vector;

public class ServiceRequestModel implements Serializable
{

  String serviceRequestId = "";
  String caseId = "";
  String caseTitle = "";
  String caseDescription = "";
  Timestamp initiateTimestamp;
  String posCode  = "";
  String posId = "";
  String posName = "";
  String posServiceId = "";
  String posServiceName  = "";
  String posServiceDesc = "";
  String serviceRequestStatusTypeId = "";
  String serviceRequestStatusTypeName = "";
  String serviceRequestLastStatusId = "";
  Vector serviceRequestWarnings;

  public static final String SERVICE_REQUEST_ID = "SERVICE_REQUEST_ID";
  public static final String CASE_ID = "CASE_ID";
  public static final String CASE_TITLE = "CASE_TITLE";
  public static final String CASE_DESCRIPTION = "CASE_DESCRIPTION";
  public static final String INITIATE_TIMESTAMP = "INITIATE_TIMESTAMP";
  public static final String POS_CODE  = "POS_CODE";
  public static final String POS_ID = "POS_ID";
  public static final String POS_NAME = "POS_NAME";
  public static final String POS_SERVICE_ID = "POS_SERVICE_ID";
  public static final String POS_SERVICE_NAME  = "POS_SERVICE_NAME";
  public static final String POS_SERVICE_DESC = "POS_SERVICE_DESC";
  public static final String SERVICE_REQ_STATUS_TYPE_ID = "SERVICE_REQ_STATUS_TYPE_ID";
  public static final String SERVICE_REQ_STATUS_TYPE_NAME = "SERVICE_REQ_STATUS_TYPE_NAME";
  public static final String SERVICE_REQUEST_STATUS_ID = "SERVICE_REQUEST_STATUS_ID";
  
  public ServiceRequestModel()
  {
  }

  public ServiceRequestModel(ResultSet res)
  {
    try
    {
      serviceRequestId  = res.getString(SERVICE_REQUEST_ID);
      caseId = res.getString(CASE_ID);
      caseTitle = res.getString(CASE_TITLE);
      caseDescription = res.getString(CASE_DESCRIPTION);
      initiateTimestamp = res.getTimestamp(INITIATE_TIMESTAMP);
      posCode  = res.getString(POS_CODE);
      posId = res.getString(POS_ID);
      posName = res.getString(POS_NAME);
      posServiceId = res.getString(POS_SERVICE_ID);
      posServiceName  = res.getString(POS_SERVICE_NAME);
      posServiceDesc = res.getString(POS_SERVICE_DESC);
      serviceRequestStatusTypeId = res.getString(SERVICE_REQ_STATUS_TYPE_ID);
      serviceRequestStatusTypeName = res.getString(SERVICE_REQ_STATUS_TYPE_NAME);
      serviceRequestLastStatusId = res.getString(SERVICE_REQUEST_STATUS_ID);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
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
	
	public String getCaseTitle()
	{
	return caseTitle;
	}
	public void setCaseTitle(String newCaseTitle)
	{
	caseTitle= newCaseTitle;
	}
	
	public String getCaseDescription()
	{
	return caseDescription;
	}
	public void setCaseDescription(String newCaseDescription)
	{
	caseDescription= newCaseDescription;
	}

  public Timestamp getInitiateTimestamp()
	{
	return initiateTimestamp;
	}
	public void setInitiateTimestamp(Timestamp newInitiateTimestamp)
	{
	initiateTimestamp= newInitiateTimestamp;
	}
  
	public String getPosCode ()
	{
	return posCode ;
	}
	public void setPosCode (String newPosCode )
	{
	posCode = newPosCode ;
	}
	
	public String getPosId()
	{
	return posId;
	}
	public void setPosId(String newPosId)
	{
	posId= newPosId;
	}
	
	public String getPosName()
	{
	return posName;
	}
	public void setPosName(String newPosName)
	{
	posName= newPosName;
	}
	
	public String getPosServiceId()
	{
	return posServiceId;
	}
	public void setPosServiceId(String newPosServiceId)
	{
	posServiceId= newPosServiceId;
	}
	
	public String getPosServiceName ()
	{
	return posServiceName ;
	}
	public void setPosServiceName (String newPosServiceName )
	{
	posServiceName = newPosServiceName ;
	}
	
	public String getPosServiceDesc()
	{
	return posServiceDesc;
	}
	public void setPosServiceDesc(String newPosServiceDesc)
	{
	posServiceDesc= newPosServiceDesc;
	}

  public String getServiceRequestStatusTypeId()
	{
	return serviceRequestStatusTypeId;
	}
	public void setServiceRequestStatusTypeId(String newServiceRequestStatusTypeId)
	{
	serviceRequestStatusTypeId= newServiceRequestStatusTypeId;
	}

  public String getServiceRequestStatusTypeName()
	{
	return serviceRequestStatusTypeName;
	}
	public void setServiceRequestStatusTypeName(String newServiceRequestStatusTypeName)
	{
	serviceRequestStatusTypeName= newServiceRequestStatusTypeName;
	}


  public Vector getServiceRequestWarnings()
	{
	return serviceRequestWarnings;
	}
	public void setServiceRequestWarnings(Vector newServiceRequestWarnings)
	{
	serviceRequestWarnings= newServiceRequestWarnings;
	}

  public String getServiceRequestLastStatusId()
	{
	return serviceRequestLastStatusId;
	}
	public void setServiceRequestLastStatusId(String newServiceRequestLastStatusId)
	{
	serviceRequestLastStatusId= newServiceRequestLastStatusId;
	}

}
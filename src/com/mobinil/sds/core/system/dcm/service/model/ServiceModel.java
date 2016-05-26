package com.mobinil.sds.core.system.dcm.service.model;
import java.sql.*;
import java.io.*;

public class ServiceModel implements Serializable
{
  String posServiceId = "";
  String posServiceName = "";
  String posServiceDesc = "";
  String serviceEligibilityTypeId = "";
  String serviceEligibilityTypeName = "";
  String posServiceStatusTypeId = "";
  String posServiceStatusTypeName = "";

  public static final String POS_SERVICE_ID = "POS_SERVICE_ID";
  public static final String POS_SERVICE_NAME = "POS_SERVICE_NAME";
  public static final String POS_SERVICE_DESC = "POS_SERVICE_DESC";
  public static final String SERVICE_ELIGIBILITY_TYPE_ID = "SERVICE_ELIGIBILITY_TYPE_ID";
  public static final String SERVICE_ELIGIBILITY_TYPE_NAME = "SERVICE_ELIGIBILITY_TYPE_NAME";
  public static final String POS_SERVICE_STATUS_TYPE_ID = "POS_SERVICE_STATUS_TYPE_ID";
  public static final String POS_SERVICE_STATUS_TYPE_NAME = "POS_SERVICE_STATUS_TYPE_NAME";
  
  public ServiceModel()
  {
  }

  public ServiceModel(ResultSet res)
  {
    try
    {
      posServiceId = res.getString(POS_SERVICE_ID);
      posServiceName = res.getString(POS_SERVICE_NAME);
      posServiceDesc = res.getString(POS_SERVICE_DESC);
      serviceEligibilityTypeId = res.getString(SERVICE_ELIGIBILITY_TYPE_ID);
      serviceEligibilityTypeName = res.getString(SERVICE_ELIGIBILITY_TYPE_NAME);
      posServiceStatusTypeId = res.getString(POS_SERVICE_STATUS_TYPE_ID);
      posServiceStatusTypeName = res.getString(POS_SERVICE_STATUS_TYPE_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  	
	public String getPosServiceId()
	{
	return posServiceId;
	}
	public void setPosServiceId(String newPosServiceId)
	{
	posServiceId= newPosServiceId;
	}
	
	public String getPosServiceName()
	{
	return posServiceName;
	}
	public void setPosServiceName(String newPosServiceName)
	{
	posServiceName= newPosServiceName;
	}
	
	public String getPosServiceDesc()
	{
	return posServiceDesc;
	}
	public void setPosServiceDesc(String newPosServiceDesc)
	{
	posServiceDesc= newPosServiceDesc;
	}
	
	public String getServiceEligibilityTypeId()
	{
	return serviceEligibilityTypeId;
	}
	public void setServiceEligibilityTypeId(String newServiceEligibilityTypeId)
	{
	serviceEligibilityTypeId= newServiceEligibilityTypeId;
	}
	
	public String getServiceEligibilityTypeName()
	{
	return serviceEligibilityTypeName;
	}
	public void setServiceEligibilityTypeName(String newServiceEligibilityTypeName)
	{
	serviceEligibilityTypeName= newServiceEligibilityTypeName;
	}
	
	public String getPosServiceStatusTypeId()
	{
	return posServiceStatusTypeId;
	}
	public void setPosServiceStatusTypeId(String newPosServiceStatusTypeId)
	{
	posServiceStatusTypeId= newPosServiceStatusTypeId;
	}
	
	public String getPosServiceStatusTypeName()
	{
	return posServiceStatusTypeName;
	}
	public void setPosServiceStatusTypeName(String newPosServiceStatusTypeName)
	{
	posServiceStatusTypeName= newPosServiceStatusTypeName;
	}
}
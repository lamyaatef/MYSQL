package com.mobinil.sds.core.system.sop.requests.model;
import java.sql.*;
import java.io.*;

public class RequestStatusModel implements Serializable
{
  String requestStatusId;
  String requestStatusName;

  public static final String REQUEST_STATUS_ID = "REQUEST_STATUS_ID";
  public static final String REQUEST_STATUS_NAME = "REQUEST_STATUS_NAME";
  
  public RequestStatusModel()
  {
  }

  public RequestStatusModel(ResultSet res)
  {
    try
    {
      requestStatusId = res.getString(REQUEST_STATUS_ID);
      requestStatusName = res.getString(REQUEST_STATUS_NAME);   
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }

  public String getRequestStatusId()
  {
    return requestStatusId;
  }

  public void setRequestStatusId(String newRequestStatusId)
  {
    requestStatusId = newRequestStatusId;
  }
////////////////////////////////////////////   
  public String getRequestStatusName()
  {
    return requestStatusName;
  }

  public void setRequestStatusName(String newRequestStatusName)
  {
    requestStatusName = newRequestStatusName;
  }
////////////////////////////////////////////    
}
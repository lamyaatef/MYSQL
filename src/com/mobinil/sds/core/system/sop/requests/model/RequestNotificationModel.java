package com.mobinil.sds.core.system.sop.requests.model;
import java.sql.*;
import java.io.*;

public class RequestNotificationModel implements Serializable
{
  String requestNotificationId;
  String personFullName;
  String personEmail;
  
  public static final String REQUEST_NOTIFICATION_ID = "REQUEST_NOTIFICATION_ID";
  public static final String PERSON_FULL_NAME = "PERSON_FULL_NAME";
  public static final String PERSON_EMAIL = "PERSON_EMAIL";
  
  public RequestNotificationModel()
  {
  }

  public RequestNotificationModel(ResultSet res)
  {
    try
    {
      requestNotificationId = res.getString(REQUEST_NOTIFICATION_ID);
      personFullName = res.getString(PERSON_FULL_NAME);   
      personEmail = res.getString(PERSON_EMAIL);   
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }

  public String getRequestNotificationId()
  {
    return requestNotificationId;
  }
  public void setRequestNotificationId(String newRequestNotificationId)
  {
    requestNotificationId= newRequestNotificationId;
  }
  
  public String getPersonFullName()
  {
    return personFullName;
  }
  public void setPersonFullName(String newPersonFullName)
  {
    personFullName= newPersonFullName;
  }
  
  public String getPersonEmail()
  {
    return personEmail;
  }
  public void setPersonEmail(String newPersonEmail)
  {
    personEmail= newPersonEmail;
  }
}
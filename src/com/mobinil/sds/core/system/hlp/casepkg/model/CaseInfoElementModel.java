package com.mobinil.sds.core.system.hlp.casepkg.model;
import java.sql.*;
import java.io.*;

public class CaseInfoElementModel implements Serializable
{
  String caseInfoElementId ;       
  String userId;
  String userFullName;
  String userEmail;
  Timestamp caseInfoElementTimestamp;
  Date caseInfoElementDate;         
  String caseInfoElementTypeId;      
  String caseInfoElementTypeName;      								 
  String caseInfoElementTitle;        
  String caseInfoElementDescription; 
  String contactName;
  String contactInfo ;
  String caseId ;
  
  public static final String CASE_INFO_ELEMENT_ID = "CASE_INFO_ELEMENT_ID";       
  public static final String USER_ID = "USER_ID";                        
  public static final String USER_FULL_NAME = "USER_FULL_NAME";  
  public static final String USER_EMAIL = "USER_EMAIL";  
  public static final String CASE_INFO_ELEMENT_TIMESTAMP = "CASE_INFO_ELEMENT_TIMESTAMP";    
  public static final String CASE_INFO_ELEMENT_DATE = "CASE_INFO_ELEMENT_DATE";         
  public static final String CASE_INFO_ELEMENT_TYPE_ID = "CASE_INFO_ELEMENT_TYPE_ID";      
  public static final String CASE_INFO_ELEMENT_TYPE_NAME = "CASE_INFO_ELEMENT_TYPE_NAME";      								 
  public static final String CASE_INFO_ELEMENT_TITLE = "CASE_INFO_ELEMENT_TITLE";        
  public static final String CASE_INFO_ELEMENT_DESCRIPTION = "CASE_INFO_ELEMENT_DESCRIPTION"; 
  public static final String CONTACT_NAME = "CONTACT_NAME";
  public static final String CONTACT_INFO = "CONTACT_INFO";
  public static final String CASE_ID = "CASE_ID";
  
  public CaseInfoElementModel()
  {
  }

  public CaseInfoElementModel(ResultSet res)
  {
    try
    {
      caseInfoElementId = res.getString(CASE_INFO_ELEMENT_ID) ;       
      userId = res.getString(USER_ID);                       
      userFullName = res.getString(USER_FULL_NAME); 
      userEmail = res.getString(USER_EMAIL); 
      caseInfoElementTimestamp = res.getTimestamp(CASE_INFO_ELEMENT_TIMESTAMP);
      caseInfoElementDate = res.getDate(CASE_INFO_ELEMENT_DATE);         
      caseInfoElementTypeId = res.getString(CASE_INFO_ELEMENT_TYPE_ID);      
      caseInfoElementTypeName = res.getString(CASE_INFO_ELEMENT_TYPE_NAME);      								 
      caseInfoElementTitle = res.getString(CASE_INFO_ELEMENT_TITLE);        
      caseInfoElementDescription = res.getString(CASE_INFO_ELEMENT_DESCRIPTION); 
      contactName = res.getString(CONTACT_NAME);
      contactInfo = res.getString(CONTACT_INFO) ;
      caseId = res.getString(CASE_ID) ;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }


  public String getCaseInfoElementId ()
  {
  return caseInfoElementId ;
  }
  public void setCaseInfoElementId (String newCaseInfoElementId )
  {
  caseInfoElementId = newCaseInfoElementId ;
  }       
  
  public String getUserId()
  {
  return userId;
  }
  public void setUserId(String newUserId)
  {
  userId= newUserId;
  }	

  public String getUserFullName()
  {
  return userFullName;
  }
  public void setUserFullName(String newUserFullName)
  {
  userFullName= newUserFullName;
  }	

  public String getUserEmail()
  {
  return userEmail;
  }
  public void setUserEmail(String newUserEmail)
  {
  userEmail= newUserEmail;
  }	
  
  public Timestamp getCaseInfoElementTimestamp()
  {
  return caseInfoElementTimestamp;
  }
  public void setCaseInfoElementTimestamp(Timestamp newCaseInfoElementTimestamp)
  {
  caseInfoElementTimestamp= newCaseInfoElementTimestamp;
  }
  
  public Date getCaseInfoElementDate()
  {
  return caseInfoElementDate;
  }
  public void setCaseInfoElementDate(Date newCaseInfoElementDate)
  {
  caseInfoElementDate= newCaseInfoElementDate;
  }
  
  public String getCaseInfoElementTypeId()
  {
  return caseInfoElementTypeId;
  }
  public void setCaseInfoElementTypeId(String newCaseInfoElementTypeId)
  {
  caseInfoElementTypeId= newCaseInfoElementTypeId;
  }
  
  public String getCaseInfoElementTypeName()
  {
  return caseInfoElementTypeName;
  }
  public void setCaseInfoElementTypeName(String newCaseInfoElementTypeName)
  {
  caseInfoElementTypeName= newCaseInfoElementTypeName;
  }
  
  public String getCaseInfoElementTitle()
  {
  return caseInfoElementTitle;
  }
  public void setCaseInfoElementTitle(String newCaseInfoElementTitle)
  {
  caseInfoElementTitle= newCaseInfoElementTitle;
  }
  
  public String getCaseInfoElementDescription()
  {
  return caseInfoElementDescription;
  }
  public void setCaseInfoElementDescription(String newCaseInfoElementDescription)
  {
  caseInfoElementDescription= newCaseInfoElementDescription;
  }
  
  public String getContactName()
  {
  return contactName;
  }
  public void setContactName(String newContactName)
  {
  contactName= newContactName;
  }
  
  public String getContactInfo()
  {
  return contactInfo ;
  }
  public void setContactInfo (String newContactInfo)
  {
  contactInfo = newContactInfo ;
  }

  public String getCaseId()
  {
  return caseId ;
  }
  public void setCaseId(String newCaseId)
  {
  caseId = newCaseId ;
  }
}
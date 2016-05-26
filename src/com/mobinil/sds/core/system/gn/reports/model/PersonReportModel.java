package com.mobinil.sds.core.system.gn.reports.model;
import java.io.*;
import java.sql.*;

public class PersonReportModel  implements Serializable
{
String personReportId;
String personId;
String reportId;
String groupId;

public static final String PERSON_REPORT_ID = "PERSON_REPORT_ID";
public static final String PERSON_ID = "PERSON_ID";
public static final String REPORT_ID = "REPORT_ID";
public static final String GROUP_ID = "GROUP_ID";
  public PersonReportModel() 
  {
  }

  public PersonReportModel(ResultSet res) 
  {
      try
      {
          personReportId = res.getString(PERSON_REPORT_ID);
          personId = res.getString(PERSON_ID);
          reportId = res.getString(REPORT_ID);    
          groupId = res.getString(GROUP_ID);
      }
      catch(Exception e) 
      {
        
      }
  }

 public void setPersonReportId(String newPersonReportId) 
 {
        personReportId = newPersonReportId;
 }
 public String getPersonReportId() 
 {
        return personReportId;  
 }
///////////////////////////////////// 
 public void setReportId(String newReportId) 
 {
        reportId = newReportId;
 }
 public String getReportId() 
 {
        return reportId;  
 }
///////////////////////////////////// 
 public void setPersonId(String newPersonId) 
 {
        personId = newPersonId;
 }
 public String getPersonId() 
 {
        return personId;  
 }
///////////////////////////////////// 
public void setGroupId(String newGroupId) 
 {
        groupId = newGroupId;
 }
 public String getGroupId() 
 {
        return groupId;  
 }
}
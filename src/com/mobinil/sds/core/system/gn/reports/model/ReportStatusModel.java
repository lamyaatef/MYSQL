package com.mobinil.sds.core.system.gn.reports.model;
import java.sql.*;
import java.io.*;

public class ReportStatusModel implements Serializable
{
  String reportStatusId;
  String reportStatusName;
  String reportStatusDescription;

  public static final String REPORT_STATUS_ID = "REPORT_STATUS_ID";
  public static final String REPORT_STATUS_NAME = "REPORT_STATUS_NAME";
  public static final String REPORT_STATUS_DESCRIPTION = "REPORT_STATUS_DESCRIPTION";
  
  public ReportStatusModel()
  {
  }
  public ReportStatusModel(ResultSet res)
  {
      try
      {
        reportStatusId = res.getString(REPORT_STATUS_ID);
        reportStatusName = res.getString(REPORT_STATUS_NAME);
        reportStatusDescription = res.getString(REPORT_STATUS_DESCRIPTION);
      }
      catch(Exception e)
      {
    
      }      
  }

//////////////////////////////////  
  public String getReportStatusId()
  {
    return reportStatusId;
  }

  public void setReportStatusId(String newReportStatusId)
  {
    reportStatusId = newReportStatusId;
  } 

//////////////////////////////////  
  public String getReportStatusName()
  {
    return reportStatusName;
  }

  public void setReportStatusName(String newReportStatusName)
  {
    reportStatusName = newReportStatusName;
  }

//////////////////////////////////  
  public String getReportStatusDescription()
  {
    return reportStatusDescription;
  }

  public void setReportStatusDescription(String newReportStatusDescription)
  {
    reportStatusDescription = newReportStatusDescription;
  }    
}
package com.mobinil.sds.core.system.gn.reports.model;
import java.sql.*;
import java.io.*;

public class ReportModel implements Serializable 
{
  String reportId;
  String reportName;
  String reportDescription;
  String dataviewId;
  String reportStatusId;

  public static final String REPORT_ID = "REPORT_ID";
  public static final String REPORT_NAME = "REPORT_NAME";
  public static final String REPORT_DESCRIPTION = "REPORT_DESCRIPTION";
  public static final String DATA_VIEW_ID = "DATA_VIEW_ID";
  public static final String REPORT_STATUS_ID = "REPORT_STATUS_ID";
  
  public ReportModel()
  {
  }
  public ReportModel(ResultSet res)
  {
      try
      {
        reportId = res.getString(REPORT_ID);
        reportStatusId = res.getString(REPORT_STATUS_ID);
        reportName = res.getString(REPORT_NAME);
        reportDescription = res.getString(REPORT_DESCRIPTION);
        dataviewId = res.getString(DATA_VIEW_ID);
      }
      catch(Exception e)
      {
    
      }    
  }

//////////////////////////////////  
  public String getReportId()
  {
    return reportId;
  }

  public void setReportId(String newReportId)
  {
    reportId = newReportId;
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
  public String getReportName()
  {
    return reportName;
  }

  public void setReportName(String newReportName)
  {
    reportName = newReportName;
  }

//////////////////////////////////  
  public String getReportDescription()
  {
    return reportDescription;
  }

  public void setReportDescription(String newReportDescription)
  {
    reportDescription = newReportDescription;
  }  
//////////////////////////////////  
  public String getDataviewId()
  {
    return dataviewId;
  }

  public void setDataviewId(String newDataviewId)
  {
    dataviewId = newDataviewId;
  }   
}
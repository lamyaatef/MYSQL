package com.mobinil.sds.core.system.gn.reports.dto;
import java.io.*;
import java.util.Vector;

public class ReportDTO implements Serializable
{
  private int m_nReportID;
  private int m_nReportStatusID;
  private String m_strReportName;
  private String m_strReportDescription;
  private int m_nReportDataViewID;
  private Vector m_vecReportSelectedFields;
  private Vector m_vecReportSelectedOrderBy;
  private boolean m_bIsDataViewActive;
  private int m_nGroupId;
  private String m_strGroupName;

  public ReportDTO()
  {
  }

  public int getReportID()
  {
    return m_nReportID;
  }

  public void setReportID(int newReportID)
  {
    m_nReportID = newReportID;
  }

public int getGroupID()
  {
    return m_nGroupId;
  }

  public void setGroupID(int newGroupID)
  {
    m_nGroupId = newGroupID;
  }

  public int getReportStatusID()
  {
    return m_nReportStatusID;
  }

  public void setReportStatusID(int newReportStatusID)
  {
    m_nReportStatusID = newReportStatusID;
  }

  public String getReportName()
  {
    return m_strReportName;
  }

  public void setReportName(String newReportName)
  {
    m_strReportName = newReportName;
  }

  public String getGroupName()
  {
    return m_strGroupName;
  }

  public void setGroupName(String newGroupName)
  {
    m_strGroupName = newGroupName;
  }

  public String getReportDescription()
  {
    return m_strReportDescription;
  }

  public void setReportDescription(String newReportDescription)
  {
    m_strReportDescription = newReportDescription;
  }

  public int getReportDataViewID()
  {
    return m_nReportDataViewID;
  }

  public void setReportDataViewID(int newReportDataViewID)
  {
    m_nReportDataViewID = newReportDataViewID;
  }

  public Vector getReportSelectedFields()
  {
    return m_vecReportSelectedFields;
  }

  public void setReportSelectedFields(Vector newReportSelectedFields)
  {
    m_vecReportSelectedFields = newReportSelectedFields;
  }

  public Vector getReportSelectedOrderBy()
  {
    return m_vecReportSelectedOrderBy;
  }

  public void setReportSelectedOrderBy(Vector newReportSelectedOrderBy)
  {
    m_vecReportSelectedOrderBy = newReportSelectedOrderBy;
  }

  public boolean isDataViewActive()
  {
    return m_bIsDataViewActive;
  }

  public void setIsDataViewActive(boolean newM_bIsDataViewActive)
  {
    m_bIsDataViewActive = newM_bIsDataViewActive;
  }

}
package com.mobinil.sds.core.system.gn.reports.dto;

import java.util.Vector;
import com.mobinil.sds.core.system.gn.dataview.dto.DetailedDataViewDTO;
import java.io.*;


public class ReportBuilderWizardDTO implements Serializable
{
  private ReportDTO m_dtoReport;
  private Vector m_vecReportFields;
  private Vector m_vecReportOrderBy;
  private DetailedDataViewDTO m_dtoDetailedDataView;
  private Vector m_vecParameterList;
  private Vector m_vecAvailableDataViews;

  public ReportBuilderWizardDTO()
  {
  }

  public ReportDTO getReport()
  {
    return m_dtoReport;
  }

  public void setReport(ReportDTO newReport)
  {
    m_dtoReport = newReport;
  }

  public Vector getReportFields()
  {
    return m_vecReportFields;
  }

  public void setReportFields(Vector newReportFields)
  {
    m_vecReportFields = newReportFields;
  }

  public Vector getReportOrderBy()
  {
    return m_vecReportOrderBy;
  }

  public void setReportOrderBy(Vector newReportOrderBy)
  {
    m_vecReportOrderBy = newReportOrderBy;
  }

  public DetailedDataViewDTO getDetailedDataView()
  {
    return m_dtoDetailedDataView;
  }

  public void setDetailedDataView(DetailedDataViewDTO newDetailedDataView)
  {
    m_dtoDetailedDataView = newDetailedDataView;
  }

  public Vector getReportParameters()
  {
    return m_vecParameterList;
  }

  public void setReportParameters(Vector newParameterList)
  {
    m_vecParameterList = newParameterList;
  }

  public Vector getAvailableDataViews()
  {
    return m_vecAvailableDataViews;
  }

  public void setAvailableDataViews(Vector newAvailableDataViews)
  {
    m_vecAvailableDataViews = newAvailableDataViews;
  }

}
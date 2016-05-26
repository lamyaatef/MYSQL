package com.mobinil.sds.core.system.gn.reports.dto;
import java.io.*;

public class ReportOrderByDTO implements Serializable
{
  private int m_nOrderByFieldID;
  private String m_nOrderByFieldName;
  private String m_strOrderByType;

  public ReportOrderByDTO()
  {
  }

  public int getOrderByFieldID()
  {
    return m_nOrderByFieldID;
  }

  public void setOrderByFieldID(int newOrderByFieldID)
  {
    m_nOrderByFieldID = newOrderByFieldID;
  }

  public String getOrderByFieldName()
  {
    return m_nOrderByFieldName;
  }

  public void setOrderByFieldName(String newOrderByFieldName)
  {
    m_nOrderByFieldName = newOrderByFieldName;
  }

  public String getOrderByType()
  {
    return m_strOrderByType;
  }

  public void setOrderByType(String newOrderByType)
  {
    m_strOrderByType = newOrderByType;
  }
}
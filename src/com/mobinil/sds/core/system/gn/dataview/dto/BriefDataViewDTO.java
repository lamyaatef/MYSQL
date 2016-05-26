package com.mobinil.sds.core.system.gn.dataview.dto;
import java.io.*;

public class BriefDataViewDTO implements Serializable 
{
  private int m_nDataViewID;
  private int m_nDataViewIssue;
  private int m_nDataViewVersion;
  private String m_strDataViewName;
  private int m_nDataViewTypeID;
  private String m_strDataViewTypeName;
  private String m_strDataViewDescription;
  private String m_nDataViewUniverse;
  private String m_strOverRideSQL;

  public BriefDataViewDTO()
  {
  }

  public int getDataViewID()
  {
      return m_nDataViewID;
  }

  public void setDataViewID(int newDataViewID)
  {
      m_nDataViewID = newDataViewID;
  }

  public int getDataViewVersion()
  {
      return m_nDataViewVersion;
  }

  public void setDataViewVersion(int newDataViewVersion)
  {
      m_nDataViewVersion = newDataViewVersion;
  }

  public String getDataViewName()
  {
      return m_strDataViewName;
  }

  public void setDataViewName(String newDataViewName)
  {
      m_strDataViewName = newDataViewName;
  }

  public int getDataViewTypeID()
  {
      return m_nDataViewTypeID;
  }

  public void setDataViewTypeID(int newDataViewTypeID)
  {
      m_nDataViewTypeID = newDataViewTypeID;
  }

  public String getDataViewTypeName()
  {
      return m_strDataViewTypeName;
  }

  public void setDataViewTypeName(String newDataViewTypeName)
  {
      m_strDataViewTypeName = newDataViewTypeName;
  }

  public int getDataViewIssue()
  {
    return m_nDataViewIssue;
  }

  public void setDataViewIssue(int newDataViewIssue)
  {
    m_nDataViewIssue = newDataViewIssue;
  }

  public String getDataViewDescription()
  {
      return m_strDataViewDescription;
  }

  public void setDataViewDescription(String newDataViewDescription)
  {
      m_strDataViewDescription = newDataViewDescription;
  }

  public String getDataViewUniverse()
  {
    return m_nDataViewUniverse;
  }

  public void setDataViewUniverse(String newDataViewUniverse)
  {
    m_nDataViewUniverse = newDataViewUniverse;
  }

  public String getOverRideSQL()
  {
    return m_strOverRideSQL;
  }

  public void setOverRideSQL(String newM_strOverRideSQL)
  {
    m_strOverRideSQL = newM_strOverRideSQL;
  }

}
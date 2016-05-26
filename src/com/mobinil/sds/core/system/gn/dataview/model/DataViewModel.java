package com.mobinil.sds.core.system.gn.dataview.model;
import java.io.*;

public class DataViewModel implements Serializable
{
  private int m_nDataViewID;
  private int m_nDataViewIssue;
  private int m_nDataViewVersion;
  private String m_strDataViewName;
  private String m_strDataViewOverrideSQL;
  private String m_strDataViewTypeName;
  private int m_nDataViewStatusID;
  private String m_nDataViewStatusName;
  private int m_nDataViewTypeID;
  private String m_strDataViewDescription;
  private String m_strDataViewStatusName;
  private int m_nDataViewUnique;
  private int m_nDataViewUniverse;


  public DataViewModel()
  {
  }

  public DataViewModel(
        int newID, int newDataViewIssue, int newDataViewVersion, 
        String newDataViewName, String newDataViewOverrideSQL, 
        int newDataViewTypeID, String newDataViewTypeName,
        String newDataViewDescription,
        int newDataViewStatusID, String newDataViewStatusName, 
        int newDataViewUnique, int newDataViewUniverse )
  {
    setDataViewID(newID);
    setDataViewIssue(newDataViewIssue);
    setDataViewVersion(newDataViewVersion);
    setDataViewName(newDataViewName);
    setDataViewOverrideSQL (newDataViewOverrideSQL);
    setDataViewTypeID(newDataViewTypeID);
    setDataViewTypeName(newDataViewTypeName);
    setDataViewDescription(newDataViewDescription);
    setDataViewStatusID(newDataViewStatusID);
    setDataViewStatusName(newDataViewStatusName);
    setDataViewUnique(newDataViewUnique);
    setDataViewUniverse(newDataViewUniverse);
  }


  public int getDataViewID()
  {
    return m_nDataViewID;
  }

  public void setDataViewID(int newDataViewID)
  {
    m_nDataViewID = newDataViewID;
  }

  public int getDataViewIssue()
  {
    return m_nDataViewIssue;
  }

  public void setDataViewIssue(int newDataViewIssue)
  {
    m_nDataViewIssue = newDataViewIssue;
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

  public String getDataViewOverrideSQL()
  {
    return m_strDataViewOverrideSQL;
  }

  public void setDataViewOverrideSQL(String newDataViewOverrideSQL)
  {
    m_strDataViewOverrideSQL = newDataViewOverrideSQL;
  }
  
  public String getDataViewTypeName()
  {
    return m_strDataViewTypeName;
  }

  public void setDataViewTypeName(String newDataViewTypeName)
  {
    m_strDataViewTypeName = newDataViewTypeName;
  }

  public int getDataViewStatusID()
  {
    return m_nDataViewStatusID;
  }

  public void setDataViewStatusID(int newDataViewStatusID)
  {
    m_nDataViewStatusID = newDataViewStatusID;
  }

  public String getDataViewStatusName()
  {
    return m_nDataViewStatusName;
  }

  public void setDataViewStatusName(String newDataViewStatusName)
  {
    m_nDataViewStatusName = newDataViewStatusName;
  }

  public int getDataViewTypeID()
  {
    return m_nDataViewTypeID;
  }

  public void setDataViewTypeID(int newDataViewTypeID)
  {
    m_nDataViewTypeID = newDataViewTypeID;
  }

  public String getDataViewDescription()
  {
    return m_strDataViewDescription;
  }

  public void setDataViewDescription(String newDataViewDescription)
  {
    m_strDataViewDescription = newDataViewDescription;
  }

  public int getDataViewUnique()
  {
    return m_nDataViewUnique;
  }

  public void setDataViewUnique(int newDataViewUnique)
  {
    m_nDataViewUnique = newDataViewUnique;
  }

  public int getDataViewUniverse()
  {
    return m_nDataViewUniverse;
  }

  public void setDataViewUniverse(int newDataViewUniverse)
  {
    m_nDataViewUniverse = newDataViewUniverse;
  }
}
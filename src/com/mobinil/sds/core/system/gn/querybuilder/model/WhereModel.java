package com.mobinil.sds.core.system.gn.querybuilder.model;
import java.io.*;

public class WhereModel implements Serializable
{
  int m_nWhereID;
  String m_nDataViewID;
  String m_nDataViewIssue;
  int m_nDataViewVersion;
  int m_nElementTypeID;
  String m_strElementTypeName;
  int m_nElementID;
  int m_nWherePlace;

  public WhereModel()
  {
  }

  public int getWhereID()
  {
    return m_nWhereID;
  }

  public void setWhereID(int newWhereID)
  {
    m_nWhereID = newWhereID;
  }

  public String getDataViewID()
  {
    return m_nDataViewID;
  }

  public void setDataViewID(String newDataViewID)
  {
    m_nDataViewID = newDataViewID;
  }

  public String getDataViewIssue()
  {
    return m_nDataViewIssue;
  }

  public void setDataViewIssue(String newDataViewIssue)
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

  public int getElementTypeID()
  {
    return m_nElementTypeID;
  }

  public void setlementTypeID(int newElementTypeID)
  {
    m_nElementTypeID = newElementTypeID;
  }

  public String getM_strElementTypeName()
  {
    return m_strElementTypeName;
  }

  public void setM_strElementTypeName(String newM_strElementTypeName)
  {
    m_strElementTypeName = newM_strElementTypeName;
  }

  public int getElementID()
  {
    return m_nElementID;
  }

  public void setElementID(int newElementID)
  {
    m_nElementID = newElementID;
  }

  public int getWherePlace()
  {
    return m_nWherePlace;
  }

  public void setWherePlace(int newWherePlace)
  {
    m_nWherePlace = newWherePlace;
  }
}
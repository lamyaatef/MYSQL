package com.mobinil.sds.core.system.gn.querybuilder.model;
import java.io.*;

public class FromModel implements Serializable
{
  private int m_nFromID;
  private int m_nDataViewID;
  private int m_nDataViewIssue;
  private int m_nDataViewVersion;
  private int m_nFRDataViewID;
  private int m_nFRDataViewIssue;
  private int m_nFRDataViewVersion;
  private String m_strFRDataViewName;
  private int m_nFRDataViewTypeID;
  private String m_strFRDataViewDescription;
  

  public FromModel()
  {
  }
  
  public FromModel(int newFromID, int newDatraViewID, int newDataViewIssue, int newDataViewVersion, 
        int newFRDataViewID, int newFRDataViewIssue, int newFRDataViewVersion)
  {
    setFromID (newFromID);
    setDataViewID (newDatraViewID);
    setDataViewIssue (newDataViewIssue);
    setDataViewVersion (newDataViewVersion);
    setFRDataViewID (newFRDataViewID);
    setFRDataViewIssue (newFRDataViewIssue);
    setFRDataViewVersion (newFRDataViewVersion);
  }

  public int getFromID()
  {
    return m_nFromID;
  }
  public void setFromID(int newFromID)
  {
    m_nFromID = newFromID;
  }
///////////////////////////////
  public int getDataViewID()
  {
    return m_nDataViewID;
  }
  public void setDataViewID(int newDatraViewID)
  {
    m_nDataViewID = newDatraViewID;
  }
///////////////////////////
  public int getDataViewIssue()
  {
    return m_nDataViewIssue;
  }
  public void setDataViewIssue(int newDataViewIssue)
  {
    m_nDataViewIssue = newDataViewIssue;
  }
////////////////////////
  public int getDataViewVersion()
  {
    return m_nDataViewVersion;
  }
  public void setDataViewVersion(int newDataViewVersion)
  {
    m_nDataViewVersion = newDataViewVersion;
  }
/////////////////////////
  public int getFRDataViewID()
  {
    return m_nFRDataViewID;
  }
  public void setFRDataViewID(int newFRDataViewID)
  {
    m_nFRDataViewID = newFRDataViewID;
  }
///////////////////////////
  public int getFRDataViewIssue()
  {
    return m_nFRDataViewIssue;
  }
  public void setFRDataViewIssue(int newFRDataViewIssue)
  {
    m_nFRDataViewIssue = newFRDataViewIssue;
  }
/////////////////////////////
  public int getFRDataViewVersion()
  {
    return m_nFRDataViewVersion;
  }
  public void setFRDataViewVersion(int newFRDataViewVersion)
  {
    m_nFRDataViewVersion = newFRDataViewVersion;
  }
/////////////////////////////
  public String getFRDataViewName()
  {
    return m_strFRDataViewName;
  }
  public void setFRDataViewName(String newFRDataViewName)
  {
    m_strFRDataViewName = newFRDataViewName;
  }
//////////////////////////////
  public int getFRDataViewTypeID()
  {
    return m_nFRDataViewTypeID;
  }
  public void setFRDataViewTypeID(int newFRDataViewTypeID)
  {
    m_nFRDataViewTypeID = newFRDataViewTypeID;
  }
///////////////////////////////
  public String getFRDataViewDescription()
  {
    return m_strFRDataViewDescription;
  }
  public void setFRDataViewDescription(String newFRDataViewDescription)
  {
    m_strFRDataViewDescription = newFRDataViewDescription;
  }
  
}
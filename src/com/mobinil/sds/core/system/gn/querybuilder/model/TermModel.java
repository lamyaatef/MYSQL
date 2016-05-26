package com.mobinil.sds.core.system.gn.querybuilder.model;
import java.io.*;

public class TermModel implements Serializable
{
  int m_nTermID;
  int m_nDataViewID;
  int m_nDataViewIssue;
  int m_nDataViewVersion;
  int m_nOperand1TableID;
  String m_strOperand1TableName;
  int m_nOperand1ObjectID;
  String m_nOperand2TableID;
  String m_strOperand2TableName;
  int m_nOperand2ObjectID;
  int m_nOperatorID;
  String m_strTermOperatorName;
  String m_strTermOperatorSQL;
  int m_nTermTypeID;
  String m_strTermTypeName;
  String m_strTermSQL;

  public TermModel()
  {
  }

  public int getTermID()
  {
    return m_nTermID;
  }

  public void setTermID(int newTermID)
  {
    m_nTermID = newTermID;
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

  public int getOperand1TableID()
  {
    return m_nOperand1TableID;
  }

  public void setOperand1TableID(int newOperand1TableID)
  {
    m_nOperand1TableID = newOperand1TableID;
  }

  public String getOperand1TableName()
  {
    return m_strOperand1TableName;
  }

  public void setOperand1TableName(String newOperand1TableName)
  {
    m_strOperand1TableName = newOperand1TableName;
  }

  public int getOperand1ObjectID()
  {
    return m_nOperand1ObjectID;
  }

  public void setOperand1ObjectID(int newOperand1ObjectID)
  {
    m_nOperand1ObjectID = newOperand1ObjectID;
  }

  public String getOperand2TableID()
  {
    return m_nOperand2TableID;
  }

  public void setOperand2TableID(String newOperand2TableID)
  {
    m_nOperand2TableID = newOperand2TableID;
  }

  public String getOperand2TableName()
  {
    return m_strOperand2TableName;
  }

  public void setOperand2TableName(String newOperand2TableName)
  {
    m_strOperand2TableName = newOperand2TableName;
  }

  public int getOperand2ObjectID()
  {
    return m_nOperand2ObjectID;
  }

  public void setOperand2ObjectID(int newOperand2ObjectID)
  {
    m_nOperand2ObjectID = newOperand2ObjectID;
  }

  public int getOperatorID()
  {
    return m_nOperatorID;
  }

  public void setOperatorID(int newOperatorID)
  {
    m_nOperatorID = newOperatorID;
  }

  public String getTermOperatorName()
  {
    return m_strTermOperatorName;
  }

  public void setTermOperatorName(String newTermOperatorName)
  {
    m_strTermOperatorName = newTermOperatorName;
  }

  public String getTermOperatorSQL()
  {
    return m_strTermOperatorSQL;
  }

  public void setTermOperatorSQL(String newTermOperatorSQL)
  {
    m_strTermOperatorSQL = newTermOperatorSQL;
  }

  public int getTermTypeID()
  {
    return m_nTermTypeID;
  }

  public void setTermTypeID(int newTermTypeID)
  {
    m_nTermTypeID = newTermTypeID;
  }

  public String getTermTypeName()
  {
    return m_strTermTypeName;
  }

  public void setTermTypeName(String newTermTypeName)
  {
    m_strTermTypeName = newTermTypeName;
  }

  public String getTermSQL()
  {
    return m_strTermSQL;
  }

  public void setTermSQL(String newTermSQL)
  {
    m_strTermSQL = newTermSQL;
  }
}
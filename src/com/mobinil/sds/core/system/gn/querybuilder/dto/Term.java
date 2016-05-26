package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;

public class Term extends ConditionElement implements Serializable
{
  private TermTypeDTO TermType;
  private int m_nTermID;
  private int m_nDataViewID;
  private String m_strDataViewName;
  private int m_nOperatorID;
  private String m_strOperatorName;
  private String m_strOperatorSQL;
  private String m_strTermSQLCache;

  public Term()
  {
  }

  public TermTypeDTO getTermType()
  {
      return TermType;
  }

  public void setTermType(TermTypeDTO newTermType)
  {
      TermType = newTermType;
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

  public String getDataViewName()
  {
      return m_strDataViewName;
  }

  public void setDataViewName(String newDataViewName)
  {
      m_strDataViewName = newDataViewName;
  }

  public int getOperatorID()
  {
      return m_nOperatorID;
  }

  public void setOperatorID(int newOperatorID)
  {
      m_nOperatorID = newOperatorID;
  }

  public String getOperatorName()
  {
      return m_strOperatorName;
  }

  public void setOperatorName(String newOperatorName)
  {
      m_strOperatorName = newOperatorName;
  }

  public String getOperatorSQL()
  {
    return m_strOperatorSQL;
  }

  public void setOperatorSQL(String newOperatorSQL)
  {
    m_strOperatorSQL = newOperatorSQL;
  }

  public String getTermSQLCache()
  {
    return m_strTermSQLCache;
  }

  public void setTermSQLCache(String newTermSQLCache)
  {
    m_strTermSQLCache = newTermSQLCache;
  }

}
package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;

public class FieldtoDataViewTermDTO extends Term implements Serializable
{
  private int m_n1stOperandFieldID;
  private String m_str1stOperandFieldName;
  private int m_n1stOperandFieldParentDataViewID;
  private int m_n1stOperandFieldType;
  private String m_str1stOperandFieldSQLCache;
  //private int m_n1stOperandFieldParentDataViewID;
  //private String m_str1stOperandFieldParentDataViewName;
  //private String m_str1stOperandFieldParentDataViewName;
  //private String m_str1stOperandFieldSQLCache;

  private int m_nRelatedDataViewID;
  private String m_strRelatedDataViewName;
  private int m_nRelatedDataViewTypeID;
  private String m_strRelatedDataViewSQL;

  public FieldtoDataViewTermDTO()
  {
  }

  public int get1stOperandFieldID()
  {
      return m_n1stOperandFieldID;
  }

  public void set1stOperandFieldID(int new1stOperandFieldID)
  {
      m_n1stOperandFieldID = new1stOperandFieldID;
  }

  public String get1stOperandFieldName()
  {
      return m_str1stOperandFieldName;
  }

  public void set1stOperandFieldName(String new1stOperandFieldName)
  {
      m_str1stOperandFieldName = new1stOperandFieldName;
  }

  public int getRelatedDataViewID()
  {
    return m_nRelatedDataViewID;
  }

  public void setRelatedDataViewID(int newRelatedDataViewID)
  {
    m_nRelatedDataViewID = newRelatedDataViewID;
  }

  public String getRelatedDataViewName()
  {
    return m_strRelatedDataViewName;
  }

  public void setRelatedDataViewName(String newRelatedDataViewName)
  {
    m_strRelatedDataViewName = newRelatedDataViewName;
  }

  public int getRelatedDataViewTypeID()
  {
    return m_nRelatedDataViewTypeID;
  }

  public void setRelatedDataViewTypeID(int newRelatedDataViewTypeID)
  {
    m_nRelatedDataViewTypeID = newRelatedDataViewTypeID;
  }

  public String getRelatedDataViewSQL()
  {
    return m_strRelatedDataViewSQL;
  }

  public void setRelatedDataViewSQL(String newRelatedDataViewSQL)
  {
    m_strRelatedDataViewSQL = newRelatedDataViewSQL;
  }

  public int get1stOperandFieldParentDataViewID()
  {
    return m_n1stOperandFieldParentDataViewID;
  }

  public void set1stOperandFieldParentDataViewID(int new1stOperandFieldParentDataViewID)
  {
    m_n1stOperandFieldParentDataViewID = new1stOperandFieldParentDataViewID;
  }

  /*public String get1stOperandFieldParentDataViewName()
  {
    return m_str1stOperandFieldParentDataViewName;
  }

  public void set1stOperandFieldParentDataViewName(String new1stOperandFieldParentDataViewName)
  {
    m_str1stOperandFieldParentDataViewName = new1stOperandFieldParentDataViewName;
  }*/

  public String get1stOperandFieldSQLCache()
  {
    return m_str1stOperandFieldSQLCache;
  }

  public void set1stOperandFieldSQLCache(String new1stOperandFieldSQLCache)
  {
    m_str1stOperandFieldSQLCache = new1stOperandFieldSQLCache;
  }

  public int get1stOperandFieldType()
  {
    return m_n1stOperandFieldType;
  }

  public void set1stOperandFieldType(int new1stOperandFieldType)
  {
    m_n1stOperandFieldType = new1stOperandFieldType;
  }

}
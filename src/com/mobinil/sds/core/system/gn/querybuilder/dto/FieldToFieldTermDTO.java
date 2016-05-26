package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;

public class FieldToFieldTermDTO extends Term implements Serializable
{
    private int m_n1stOperandFieldID;
    private String m_str1stOperandFieldName;
    private int m_n1stOperandFieldType;
    //private String m_str1stOperandFieldValue;
    private int m_n1stOperandFieldParentDataViewID;
    //private String m_str1stOperandFieldParentDataViewName;
    private String m_str1stOperandFieldSQLCache;
    
    private int m_n2ndOperandFieldID;
    private String m_str2ndOperandFieldName;
    private int m_n2ndOperandFieldType;
    //private String m_str2ndOperandFieldValue;
    private int m_n2ndOperandFieldParentDataViewID;
    //private String m_str2ndOperandFieldParentDataViewName;
    private String m_str2ndOperandFieldSQLCache;
    
    /*private int m_n1stOperandFieldType;
    private int m_n2ndOperandFieldType;*/

    public FieldToFieldTermDTO()
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

    public int get2ndOperandFieldID()
    {
        return m_n2ndOperandFieldID;
    }

    public void set2ndOperandFieldID(int new2ndOperandFieldID)
    {
        m_n2ndOperandFieldID = new2ndOperandFieldID;
    }

    public String get2ndOperandFieldName()
    {
        return m_str2ndOperandFieldName;
    }

    public void set2ndOperandFieldName(String new2ndOperandFieldName)
    {
        m_str2ndOperandFieldName = new2ndOperandFieldName;
    }

    public int get1stOperandFieldType()
    {
      return m_n1stOperandFieldType;
    }

    public void set1stOperandFieldType(int new1stOperandFieldType)
    {
      m_n1stOperandFieldType = new1stOperandFieldType;
    }

    public int get2ndOperandFieldType()
    {
      return m_n2ndOperandFieldType;
    }

    public void set2ndOperandFieldType(int new2ndOperandFieldType)
    {
      m_n2ndOperandFieldType = new2ndOperandFieldType;
    }

/*    public String get1stOperandFieldValue()
    {
      return m_str1stOperandFieldValue;
    }

    public void set1stOperandFieldValue(String new1stOperandFieldValue)
    {
      m_str1stOperandFieldValue = new1stOperandFieldValue;
    }

    public String get2ndOperandFieldValue()
    {
      return m_str2ndOperandFieldValue;
    }

    public void set2ndOperandFieldValue(String new2ndOperandFieldValue)
    {
      m_str2ndOperandFieldValue = new2ndOperandFieldValue;
    }
*/
    /*
    public int get1stOperandFieldType()
    {
      return m_n1stOperandFieldType;
    }

    public void set1stOperandFieldType(int new1stOperandFieldType)
    {
      m_n1stOperandFieldType = new1stOperandFieldType;
    }

    public int get2ndOperandFieldType()
    {
      return m_n2ndOperandFieldType;
    }

    public void set2ndOperandFieldType(int new2ndOperandFieldType)
    {
      m_n2ndOperandFieldType = new2ndOperandFieldType;
    }
    */
    public int get1stOperandFieldParentDataViewID()
    {
      return m_n1stOperandFieldParentDataViewID;
    }

    public void set1stOperandFieldParentDataViewID(int new1stOperandFieldParentDataViewID)
    {
      m_n1stOperandFieldParentDataViewID = new1stOperandFieldParentDataViewID;
    }
/*
    public String get1stOperandFieldParentDataViewName()
    {
      return m_str1stOperandFieldParentDataViewName;
    }

    public void set1stOperandFieldParentDataViewName(String new1stOperandFieldParentDataViewName)
    {
      m_str1stOperandFieldParentDataViewName = new1stOperandFieldParentDataViewName;
    }
*/

    public int get2ndOperandFieldParentDataViewID()
    {
      return m_n2ndOperandFieldParentDataViewID;
    }

    public void set2ndOperandFieldParentDataViewID(int new2ndOperandFieldParentDataViewID)
    {
      m_n2ndOperandFieldParentDataViewID = new2ndOperandFieldParentDataViewID;
    }

/*    public String get2ndOperandFieldParentDataViewName()
    {
      return m_str2ndOperandFieldParentDataViewName;
    }

    public void set2ndOperandFieldParentDataViewName(String new2ndOperandFieldParentDataViewName)
    {
      m_str2ndOperandFieldParentDataViewName = new2ndOperandFieldParentDataViewName;
    }
*/
  public String get1stOperandFieldSQLCache()
  {
    return m_str1stOperandFieldSQLCache;
  }

  public void set1stOperandFieldSQLCache(String new1stOperandFieldSQLCache)
  {
    m_str1stOperandFieldSQLCache = new1stOperandFieldSQLCache;
  }

  public String get2ndOperandFieldSQLCache()
  {
    return m_str2ndOperandFieldSQLCache;
  }

  public void set2ndOperandFieldSQLCache(String new2ndOperandFieldSQLCache)
  {
    m_str2ndOperandFieldSQLCache = new2ndOperandFieldSQLCache;
  }

}
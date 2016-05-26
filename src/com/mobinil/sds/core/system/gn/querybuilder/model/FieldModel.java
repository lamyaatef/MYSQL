package com.mobinil.sds.core.system.gn.querybuilder.model;
import java.io.*;

public class FieldModel implements Serializable
{
  int m_nDataViewID;
  int m_nDataViewIssue;
  int m_nDataViewVersion;
  int m_nFieldID;
  String m_strFieldName;
  String m_strFieldDescription;
  String m_strFieldSQLCash;
  int m_nFieldDisplayTypeID;
  String m_strFieldDisplayTypeName;
  int m_nFieldTypeID;
  String m_strFieldTypeName;
  int m_nFieldTypeObjectID;
  int m_nFieldRFObject;
  //int m_nFieldUnique;


  public FieldModel ()
  { }
  
  public FieldModel (int newDataViewID, int newDataViewIssue, 
        int newDataViewVersion, int newFieldID, String newFieldName, 
        String newFieldDescription, String newFieldSQLCash, 
        int newFieldDisplayTypeID, String newFieldDisplayTypeName, 
        int newFieldTypeID, String newFieldTypeName, 
        int newFieldTypeObjectID, int newFieldRFObject/*, int newFieldUnique*/)
  {
    setDataViewID (newDataViewID) ;
    setDataViewIssue (newDataViewIssue) ;
    setDataViewVersion (newDataViewVersion) ;
    setFieldID (newFieldID) ;
    setFieldName (newFieldName) ;
    setFieldDescription (newFieldDescription) ;
    setFieldSQLCash (newFieldSQLCash) ;
    setFieldDisplayTypeID (newFieldDisplayTypeID) ;
    setFieldDisplayTypeName (newFieldDisplayTypeName) ;
    setFieldTypeID (newFieldTypeID) ;
    setFieldTypeName (newFieldTypeName) ;
    setFieldTypeObjectID (newFieldTypeObjectID) ;
    setFieldRFObject(newFieldRFObject) ;
    //setFieldUnique (newFieldUnique) ;
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

  public int getFieldID()
  {
    return m_nFieldID;
  }

  public void setFieldID(int newFieldID)
  {
    m_nFieldID = newFieldID;
  }

  public String getFieldName()
  {
    return m_strFieldName;
  }

  public void setFieldName(String newFieldName)
  {
    m_strFieldName = newFieldName;
  }

  public String getFieldDescription()
  {
    return m_strFieldDescription;
  }

  public void setFieldDescription(String newFieldDescription)
  {
    m_strFieldDescription = newFieldDescription;
  }

  public String getFieldSQLCash()
  {
    return m_strFieldSQLCash;
  }

  public void setFieldSQLCash(String newFieldSQLCash)
  {
    m_strFieldSQLCash = newFieldSQLCash;
  }

  public int getFieldDisplayTypeID()
  {
    return m_nFieldDisplayTypeID;
  }

  public void setFieldDisplayTypeID(int newFieldDisplayTypeID)
  {
    m_nFieldDisplayTypeID = newFieldDisplayTypeID;
  }

  public String getFieldDisplayTypeName()
  {
    return m_strFieldDisplayTypeName;
  }

  public void setFieldDisplayTypeName(String newFieldDisplayTypeName)
  {
    m_strFieldDisplayTypeName = newFieldDisplayTypeName;
  }

  public int getFieldTypeID()
  {
    return m_nFieldTypeID;
  }

  public void setFieldTypeID(int newFieldTypeID)
  {
    m_nFieldTypeID = newFieldTypeID;
  }

  public String getFieldTypeName()
  {
    return m_strFieldTypeName;
  }

  public void setFieldTypeName(String newFieldTypeName)
  {
    m_strFieldTypeName = newFieldTypeName;
  }

  public int getFieldTypeObjectID()
  {
    return m_nFieldTypeObjectID;
  }

  public void setFieldTypeObjectID(int newFieldTypeObjectID)
  {
    m_nFieldTypeObjectID = newFieldTypeObjectID;
  }

  public int getFieldRFObject()
  {
    return m_nFieldRFObject;
  }

  public void setFieldRFObject(int newFieldRFObject)
  {
    m_nFieldRFObject = newFieldRFObject;
  }
/*
  public int getFieldUnique()
  {
    return m_nFieldUnique;
  }

  public void setFieldUnique(int newFieldUnique)
  {
    m_nFieldUnique = newFieldUnique;
  }
*/
}
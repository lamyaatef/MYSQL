package com.mobinil.sds.core.system.gn.querybuilder.model;
/**
 * Function Parameter Value Model -  Model class holds a set of attributes that matches the 
 * attributes of the function parameter values table in the database.
 * For example:
 * <pre>
 *      FunctionParameterValueModel modFunctionParameterValue = new FunctionParameterValueModel();
 *      FunctionParameterValueModel.getParameterValueID();
 * </pre>
 *
 * @version	1.01 March 2004
 * @author  Ahmed Mohamed Abd Elmonem
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.*;


public class FunctionParameterValueModel implements Serializable, Cloneable
{
    // ID of (original/old) dataviewfieldID, or constantID - no new parameter ID is generated
  private int m_nParameterValueID;
    // name of dataviewfieldname, or constantname
  private String m_strParameterValueName;
    // dataviewfield, const, function 
  private int m_nParameterValueFieldType;
  private String m_strParameterValueSQLCache;
  private int m_nParameterValuePlace;

  private int m_nParameterValueDataViewID;
  private int m_nParameterValueDataViewType;
  private String m_strParameterValueDataViewName;


    // has the ID of the param definition (VW_ADH_PARAMETER_DEFINITION.PARAMETER_DEFINITION_ID)
  private int m_nParameterDefinitionID; 
    // has the name of the param definition (VW_ADH_PARAMETER_DEFINITION.PARAMETER_DEFINITION_NAME)
  private String m_strParameterDefinitionName;

  private int m_nParentFunctionFieldID; // has the ID of the field representing the function 
  //private int m_nLinkFieldID; // has the ID of the field representing the function 
  //private int m_nValueFieldID; // has the ID of the field representing the parameter


  public FunctionParameterValueModel()
  {
  }

  public Object clone( ) throws CloneNotSupportedException 
  {
    return super.clone( );
  }

  public int getParameterValueID()
  {
     return m_nParameterValueID;
  }

  public void setParameterValueID(int argParameterValueID)
  {
      m_nParameterValueID = argParameterValueID;
  }

  public int getParentFunctionFieldID()
  {
      return m_nParentFunctionFieldID;
  }

  public void setParentFunctionFieldID(int argParentFunctionFieldID)
  {
      m_nParentFunctionFieldID = argParentFunctionFieldID;
  }
/*
  public int getValueFieldID()
  {
      return m_nValueFieldID;
  }

  public void setValueFieldID(int argValueFieldID)
  {
      m_nValueFieldID = argValueFieldID;
  }
*/  
  public int getParameterValuePlace()
  {
      return m_nParameterValuePlace;
  }

  public void setParameterValuePlace(int argParameterValuePlace)
  {
      m_nParameterValuePlace = argParameterValuePlace;
  }
  public int getParameterDefinitionID()
  {
      return m_nParameterDefinitionID;
  }

  public void setParameterDefinitionID(int argParameterDefinitionID)
  {
      m_nParameterDefinitionID = argParameterDefinitionID;
  }
    
  public String getParameterDefinitionName()
  {
      return m_strParameterDefinitionName;
  }

  public void setParameterDefinitionName(String argParameterDefinitionName)
  {
      m_strParameterDefinitionName = argParameterDefinitionName;
  }

  public int getParameterValueFieldType()
  {
    return m_nParameterValueFieldType;
  }

  public void setParameterValueFieldType(int newParameterValueFieldType)
  {
    m_nParameterValueFieldType = newParameterValueFieldType;
  }

  public String getParameterValueName()
  {
    return m_strParameterValueName;
  }

  public void setParameterValueName(String newParameterValueName)
  {
    m_strParameterValueName = newParameterValueName;
  }

  public String getParameterValueSQLCache()
  {
    return m_strParameterValueSQLCache;
  }

  public void setParameterValueSQLCache(String newParameterValueSQLCache)
  {
    m_strParameterValueSQLCache = newParameterValueSQLCache;
  }

  public int getParameterValueDataViewID()
  {
    return m_nParameterValueDataViewID;
  }

  public void setParameterValueDataViewID(int newParameterValueDataViewID)
  {
    m_nParameterValueDataViewID = newParameterValueDataViewID;
  }

  public int getParameterValueDataViewType()
  {
    return m_nParameterValueDataViewType;
  }

  public void setParameterValueDataViewType(int newParameterValueDataViewType)
  {
    m_nParameterValueDataViewType = newParameterValueDataViewType;
  }

  public String getParameterValueDataViewName()
  {
    return m_strParameterValueDataViewName;
  }

  public void setParameterValueDataViewName(String newParameterValueDataViewName)
  {
    m_strParameterValueDataViewName = newParameterValueDataViewName;
  }

}
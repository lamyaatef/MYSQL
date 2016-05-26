package com.mobinil.sds.core.system.gn.querybuilder.dto;

import java.util.HashMap;
import java.util.Vector;
import java.io.*;



public class QueryDTO implements Serializable
{
  private String m_strQueryName;
  private int m_nVersion;
  private int m_nIssue;
  private String m_strQueryDescription;
  private Vector m_colDisplayedFields;
  private Vector m_colTerms;
  private HashMap m_hMapDetailedDataViews;
  private QueryWhereDTO m_dtoQueryWhere;
  private Vector m_colConstantsFields;
  private String m_strErrorMessage;
    
  private Vector m_colFunctionsFields;
  private Vector m_colParametersFields;
  private Vector m_colGroupBy;
  private QueryHavingDTO m_dtoQueryHaving;
  private Vector m_colOrderBy;
  private int m_nQueryUnique;
  private int m_nQueryUniverse;
  private String m_strQueryUniverse;
  private int m_nSaveAs;
  private Vector m_colSelectedDataViews;
  String m_strOverRideSQL;
  
  private String runUserID;

  public QueryDTO()
  {
  }

  public String getRunUserID() {
      return this.runUserID;
  }
  public void setRunUserID(String userID) {
      this.runUserID = userID;
  }
  public String getQueryName()
  {
      return m_strQueryName;
  }

  public void setQueryName(String newQueryName)
  {
      m_strQueryName = newQueryName;
  }

  public int getQueryVersion()
  {
      return m_nVersion;
  }

  public void setQueryVersion(int newVersion)
  {
      m_nVersion = newVersion;
  }

  public int getQueryIssue()
  {
      return m_nIssue;
  }

  public void setQueryIssue(int newIssue)
  {
      m_nIssue = newIssue;
  }

  public String getQueryDescription()
  {
      return m_strQueryDescription;
  }

  public void setQueryDescription(String newQueryDescription)
  {
      m_strQueryDescription = newQueryDescription;
  }

  public HashMap getDetailedDataViews()
  {
      return m_hMapDetailedDataViews;
  }

  public void setDetailedDataViews(HashMap newDetailedDataViews)
  {
      m_hMapDetailedDataViews = newDetailedDataViews;
  }

  public Vector getConstantsFields()
  {
      return m_colConstantsFields;
  }

  public void setConstantsFields(Vector newConstantsFields)
  {
      m_colConstantsFields = newConstantsFields;
  }

  public Vector getFunctionsFields()
  {
      return m_colFunctionsFields;
  }

  public void setFunctionsFields(Vector newFunctionsFields)
  {
      m_colFunctionsFields = newFunctionsFields;
  }

  public Vector getParametersFields()
  {
      return m_colParametersFields;
  }

  public void setParametersFields(Vector newParametersFields)
  {
      m_colParametersFields = newParametersFields;
  }

  public QueryWhereDTO getQueryWhere()
  {
      return m_dtoQueryWhere;
  }

  public void setQueryWhere(QueryWhereDTO newQueryWhere)
  {
      m_dtoQueryWhere = newQueryWhere;
  }

  public Vector getGroupBy()
  {
      return m_colGroupBy;
  }

  public void setGroupBy(Vector newGroupBy)
  {
      m_colGroupBy = newGroupBy;
  }

  public QueryHavingDTO getQueryHaving()
  {
      return m_dtoQueryHaving;
  }

  public void setQueryHaving(QueryHavingDTO newQueryHaving)
  {
      m_dtoQueryHaving = newQueryHaving;
  }

  public Vector getOrderBy()
  {
      return m_colOrderBy;
  }

  public void setOrderBy(Vector newOrderBy)
  {
      m_colOrderBy = newOrderBy;
  }

  public Vector getDisplayedFields()
  {
    return m_colDisplayedFields;
  }

  public void setDisplayedFields(Vector newDisplayedFields)
  {
    m_colDisplayedFields = newDisplayedFields;
  }

  public Vector getTerms()
  {
    return m_colTerms;
  }

  public void setTerms(Vector newTerms)
  {
    m_colTerms = newTerms;
  }

  public String getErrorMessage()
  {
    return m_strErrorMessage;
  }

  public void setErrorMessage(String newErrorMessage)
  {
    m_strErrorMessage = newErrorMessage;
  }

  public int getQueryUnique()
  {
    return m_nQueryUnique;
  }

  public void setQueryUnique(int newQueryUnique)
  {
    m_nQueryUnique = newQueryUnique;
  }

  public int getQueryUniverse ()
  {
    return m_nQueryUniverse ;
  }

  public void setQueryUniverse ( int newQueryUniverse )
  {
    m_nQueryUniverse = newQueryUniverse ;
  }


  public String getQueryUniverseName ()
  {
    return m_strQueryUniverse ;
  }

  public void setQueryUniverseName ( String newQueryUniverse )
  {
    m_strQueryUniverse = newQueryUniverse ;
  }
  
  public int getQuerySaveAs()
  {
    return m_nSaveAs;
  }

  public void setQuerySaveAs(int newSaveAs)
  {
    m_nSaveAs = newSaveAs;
  }

  public Vector getSelectedDataViews()
  {
    return m_colSelectedDataViews;
  }

  public void setSelectedDataViews(Vector newSelectedDataViews)
  {
    m_colSelectedDataViews = newSelectedDataViews;
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
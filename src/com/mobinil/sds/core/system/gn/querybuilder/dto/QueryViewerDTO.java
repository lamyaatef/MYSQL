package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;
import java.util.Vector;

public class QueryViewerDTO implements Serializable
{
  private Vector m_colRows;
  private Vector m_colHeaderInterfaceFields;
  private String m_strErrorMessage;
  private String m_strSQLCode;
  String m_dataViewName;
  //private QueryDTO queryDTO;
  
  public  boolean isFile;
  public String fileName;
  
    
  public QueryViewerDTO()
  {
  }

  public Vector getRows()
  {
      return m_colRows;
  }

  public void setRows (Vector newRows)
  {
      m_colRows = newRows;
  }

  public Vector getHeaderInterfaceFields()
  {
      return m_colHeaderInterfaceFields;
  }

  public void setHeaderInterfaceFields (Vector newHeaderInterfaceFields)
  {
      m_colHeaderInterfaceFields = newHeaderInterfaceFields;
  }

  public String getErrorMessage()
  {
    return m_strErrorMessage;
  }

  public void setErrorMessage(String newErrorMessage)
  {
    m_strErrorMessage = newErrorMessage;
  }

  public String getSQLCode()
  {
    return m_strSQLCode;
  }

  public void setSQLCode(String newSQLCode)
  {
    m_strSQLCode = newSQLCode;
  }

  public String getM_dataViewName()
  {
    return m_dataViewName;
  }

  public void setM_dataViewName(String newM_dataViewName)
  {
    m_dataViewName = newM_dataViewName;
  }
}
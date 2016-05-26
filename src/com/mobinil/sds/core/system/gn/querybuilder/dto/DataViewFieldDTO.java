package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;

public class DataViewFieldDTO extends FieldDTO implements Serializable
{
    private int m_nParentDataViewID;
    private int m_nParentDataViewVersion;
  private String m_strParentDataViewName;


    public DataViewFieldDTO ()
    {    }

    public DataViewFieldDTO(int newParentDataViewID, int newParentDataViewVersion)
    {
        m_nParentDataViewID         = newParentDataViewID;
        m_nParentDataViewVersion    = newParentDataViewVersion;
    }

    public int getParentDataViewID()
    {
        return m_nParentDataViewID;
    }

    public void setParentDataViewID(int newParentDataViewID)
    {
        m_nParentDataViewID = newParentDataViewID;
    }

    public int getParentDataViewVersion()
    {
        return m_nParentDataViewVersion;
    }

    public void setParentDataViewVersion(int newParentDataViewVersion)
    {
        m_nParentDataViewVersion = newParentDataViewVersion;
    }

  public String getParentDataViewName()
  {
    return m_strParentDataViewName;
  }

  public void setParentDataViewName(String newParentDataViewName)
  {
    m_strParentDataViewName = newParentDataViewName;
  }
}
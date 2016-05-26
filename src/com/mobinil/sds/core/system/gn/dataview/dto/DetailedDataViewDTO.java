package com.mobinil.sds.core.system.gn.dataview.dto;
import java.io.*;
import java.util.Vector;

public class DetailedDataViewDTO extends BriefDataViewDTO implements Serializable
{
    private Vector m_colDataViewFields;
    private boolean m_bDataViewActive;
    private String m_strDataViewOverrideSQL;
    private int iIndex;


    public DetailedDataViewDTO()
    {
    }

    public Vector getDataViewFields()
    {
        return m_colDataViewFields;
    }

    public void setDataViewFields(Vector newDataViewFields)
    {
        m_colDataViewFields = newDataViewFields;
    }

    public boolean isDataViewActive()
    {
        return m_bDataViewActive;
    }

    public void setDataViewActive(boolean newDataViewActive)
    {
        m_bDataViewActive = newDataViewActive;
    }

    public String getDataViewOverrideSQL()
    {
        return m_strDataViewOverrideSQL;
    }

    public void setDataViewOverrideSQL(String newDataViewOverrideSQL)
    {
        m_strDataViewOverrideSQL = newDataViewOverrideSQL;
    }

  public int getIndex()
  {
    return iIndex;
  }

  public void setIndex(int newIIndex)
  {
    iIndex = newIIndex;
  }
}
package com.mobinil.sds.core.system.gn.querybuilder.model;
import java.io.*;

public class TermOperatorModel implements Serializable
{
    private int m_nTermOperatorID;
    private String m_strTermOperatorName;
    private String m_strTermOperatorSQL;
    private String m_strTermOperatorDescription;
    private int m_nTermOperatorTypeID;

    public TermOperatorModel()
    {
    }

    public int getTermOperatorID()
    {
        return m_nTermOperatorID;
    }

    public void setTermOperatorID(int newTermOperatorID)
    {
        m_nTermOperatorID = newTermOperatorID;
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

    public String getTermOperatorDescription()
    {
        return m_strTermOperatorDescription;
    }

    public void setTermOperatorDescription(String newTermOperatorDescription)
    {
        m_strTermOperatorDescription = newTermOperatorDescription;
    }

    public int getTermOperatorTypeID()
    {
      return m_nTermOperatorTypeID;
    }

    public void setTermOperatorTypeID(int newTermOperatorTypeID)
    {
      m_nTermOperatorTypeID = newTermOperatorTypeID;
    }
}
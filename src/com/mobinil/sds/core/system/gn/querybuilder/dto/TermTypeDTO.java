package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;

public class TermTypeDTO implements Serializable
{
    private int m_nTermTypeID;
    private String m_strTermTypeName;

    public TermTypeDTO()
    {
    }

    public int getTermTypeID()
    {
        return m_nTermTypeID;
    }

    public void setTermTypeID(int newTermTypeID)
    {
        m_nTermTypeID = newTermTypeID;
    }

    public String getTermTypeName()
    {
        return m_strTermTypeName;
    }

    public void setTermTypeName(String newTermTypeName)
    {
        m_strTermTypeName = newTermTypeName;
    }
}
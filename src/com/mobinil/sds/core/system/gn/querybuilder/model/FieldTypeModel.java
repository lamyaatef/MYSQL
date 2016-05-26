package com.mobinil.sds.core.system.gn.querybuilder.model;
import java.io.*;

public class FieldTypeModel implements Serializable
{
    private int m_nFieldTypeID;
    private String m_strFieldTypeName;
    private String m_strFieldTypeDescription;

    public FieldTypeModel()
    {
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

    public String getFieldTypeDescription()
    {
        return m_strFieldTypeDescription;
    }

    public void setFieldTypeDescription(String newFieldTypeDescription)
    {
        m_strFieldTypeDescription = newFieldTypeDescription;
    }
}
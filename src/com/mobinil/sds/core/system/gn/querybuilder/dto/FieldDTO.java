package com.mobinil.sds.core.system.gn.querybuilder.dto;
import com.mobinil.sds.core.system.gn.querybuilder.model.FieldTypeModel;
import com.mobinil.sds.core.system.gn.querybuilder.model.FieldDisplayTypeModel;
import java.io.*;


public abstract class FieldDTO implements Serializable
{
    private int m_nDataViewID;
    private int m_nFieldID;
    private String m_strFieldName;
    private String m_strFieldDescription;
    private String m_strFieldSQLCash;
    private FieldTypeModel m_FieldType;
    //private int m_nFieldUnique;
    //private FieldDisplayTypeModel m_FieldDisplayType;

    public FieldDTO()
    {
    }

    public int getDataViewID()
    {
        return m_nDataViewID;
    }

    public void setDataViewID(int newDataViewID)
    {
        m_nDataViewID = newDataViewID;
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

    public FieldTypeModel getFieldType()
    {
        return m_FieldType;
    }

    public void setFieldType(FieldTypeModel newFieldType)
    {
        m_FieldType = newFieldType;
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
/*
    public FieldDisplayTypeModel getFieldDisplayType()
    {
        return m_FieldDisplayType;
    }

    public void setFieldDisplayType(FieldDisplayTypeModel newFieldDisplayType)
    {
        m_FieldDisplayType = newFieldDisplayType;
    }*/
}
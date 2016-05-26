package com.mobinil.sds.core.system.gn.querybuilder.model;
import java.io.*;



/*
 Display field status is always "not displayed" unless in select clause.
 Only when handling select clause the "displayed" ID is saved in the DB, otherwise 
 the "not displayed" is saved. 
 That is, the display status is directly dependent on the code being executed.

 This class should be deleted.

 */
public class FieldDisplayTypeModel implements Serializable
{

    private int m_nFieldDisplayTypeID;
    private String m_strFieldDisplayTypeName;
    private String m_strFieldDisplayTypeDescription;

    public FieldDisplayTypeModel()
    {
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

    public String getFieldDisplayTypeDescription()
    {
        return m_strFieldDisplayTypeDescription;
    }

    public void setFieldDisplayTypeDescription(String newFieldDisplayTypeDescription)
    {
        m_strFieldDisplayTypeDescription = newFieldDisplayTypeDescription;
    }
}
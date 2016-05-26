package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;

public class ConditionElementTypeDTO implements Serializable
{
    private int m_nConditionElementTypeID;
    private String m_strConditionElementTypeName;

    public ConditionElementTypeDTO()
    {
    }

    public int getConditionElementTypeID()
    {
        return m_nConditionElementTypeID;
    }

    public void setConditionElementTypeID(int newConditionElementTypeID)
    {
        m_nConditionElementTypeID = newConditionElementTypeID;
    }

    public String getConditionElementTypeName()
    {
        return m_strConditionElementTypeName;
    }

    public void setConditionElementTypeName(String newConditionElementTypeName)
    {
        m_strConditionElementTypeName = newConditionElementTypeName;
    }
}
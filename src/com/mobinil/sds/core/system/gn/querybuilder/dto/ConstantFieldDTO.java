package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;

public class ConstantFieldDTO extends FieldDTO implements Serializable, Cloneable
{
    //private String m_strValue;
    private int m_nDataType;

    public ConstantFieldDTO()
    {
    }

    public Object clone( ) throws CloneNotSupportedException 
    {
      return super.clone( );
    }
/*
    public String getValue()
    {
        return m_strValue;
    }

    public void setValue(String newValue)
    {
        m_strValue = newValue;
    }

    public ConstantFieldDTO(String newValue)
    {
        m_strValue = newValue;
    }
*/

    public int getDataType()
    {
      return m_nDataType;
    }

    public void setDataType(int newDataType)
    {
      m_nDataType = newDataType;
    }





}
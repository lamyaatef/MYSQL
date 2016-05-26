package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;
import java.util.Vector;

public class DataRowDTO implements Serializable
{
    private Vector m_colValues;

    public DataRowDTO ()
    {
    }

    public Vector getValues ()
    {
        return m_colValues;
    }

    public void setValues (Vector newValues)
    {
        m_colValues = newValues;
    }
}
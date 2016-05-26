package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;
import java.util.Vector;

public class QueryHavingDTO implements Serializable
{
    private Vector m_colConditionElements;

    public QueryHavingDTO()
    {
    }

    public Vector getConditionElements()
    {
        return m_colConditionElements;
    }

    public void setConditionElements(Vector newConditionElements)
    {
        m_colConditionElements = newConditionElements;
    }
}
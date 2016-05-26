package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;
import java.util.Vector;

public class QueryWhereDTO implements Serializable
{
    //private ConditionElement m_colConditionElements;
    private Vector m_colConditionElements;
    //private Vector m_colTerms;

    public QueryWhereDTO()
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
    
/*
    public Vector getTerms()
    {
      return m_colTerms;
    }

    public void setTerms(Vector newTerms)
    {
      m_colTerms = newTerms;
    }
*/
}
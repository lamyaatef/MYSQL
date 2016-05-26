package com.mobinil.sds.core.system.gn.querybuilder.dto;

import java.util.*;
import java.io.*;

public class QueryBuilderWizardDTO implements Serializable
{
    private Vector m_colTermOperators;
    private Vector m_colRelationSymbols;
    private Vector m_colFunctionTypes;
    private Vector m_colFieldTypes;
    private Vector m_colTermTypes;
    private Vector m_colConditionElementTypes;
    private Vector m_colInputControlTypes;
    private Vector m_colFieldDisplayTypes;
    private HashMap m_hMapFunctions;
    private QueryDTO m_dtoQuery;



  
    public QueryBuilderWizardDTO()
    {
    }

    public Vector getTermOperators()
    {
        return m_colTermOperators;
    }

    public void setTermOperators(Vector newTermOperators)
    {
        m_colTermOperators = newTermOperators;
    }

    public Vector getRelationSymbols()
    {
        return m_colRelationSymbols;
    }

    public void setRelationSymbols(Vector newRelationSymbols)
    {
        m_colRelationSymbols = newRelationSymbols;
    }

    public Vector getFunctionTypes()
    {
        return m_colFunctionTypes;
    }

    public void setFunctionTypes(Vector newFunctionTypes)
    {
        m_colFunctionTypes = newFunctionTypes;
    }

    public Vector getFieldTypes()
    {
        return m_colFieldTypes;
    }

    public void setFieldTypes(Vector newFieldTypes)
    {
        m_colFieldTypes = newFieldTypes;
    }

    public Vector getTermTypes()
    {
        return m_colTermTypes;
    }

    public void setTermTypes(Vector newTermTypes)
    {
        m_colTermTypes = newTermTypes;
    }

    public Vector getConditionElementTypes()
    {
        return m_colConditionElementTypes;
    }

    public void setConditionElementTypes (Vector newConditionElementTypes)
    {
        m_colConditionElementTypes = newConditionElementTypes;
    }

    public Vector getInputControlTypes()
    {
        return m_colInputControlTypes;
    }

    public void setInputControlTypes(Vector newInputControlTypes)
    {
        m_colInputControlTypes = newInputControlTypes;
    }

    public Vector getFieldDisplayTypes()
    {
        return m_colFieldDisplayTypes;
    }

    public void setFieldDisplayTypes(Vector newFieldDisplayTypes)
    {
        m_colFieldDisplayTypes = newFieldDisplayTypes;
    }

    public HashMap getFunctions()
    {
        return m_hMapFunctions;
    }

    public void setFunctions(HashMap newFunctions)
    {
        m_hMapFunctions = newFunctions;
    }

    public QueryDTO getQuery()
    {
        return m_dtoQuery;
    }

    public void setQuery(QueryDTO newQuery)
    {
        m_dtoQuery = newQuery;
    }
}
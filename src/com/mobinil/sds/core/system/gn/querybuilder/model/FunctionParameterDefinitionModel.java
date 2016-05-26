package com.mobinil.sds.core.system.gn.querybuilder.model;
/**
 * Function Parameter Definition Model -  Model class holds a set of attributes that matches the 
 * attributes of the function parameter definitions table in the database.
 * For example:
 * <pre>
 *      FunctionParameterDefinitionModel modFunctionParameterDefinition = new FunctionParameterDefinitionModel();
 *      FunctionParameterDefinitionModel.getParameterDefinitionID();
 * </pre>
 *
 * @version	1.01 March 2004
 * @author  Ahmed Mohamed Abd Elmonem
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.*;

public class FunctionParameterDefinitionModel implements Serializable
{
    private int m_nParameterDefinitionID;
    private int m_nFunctionID;
    private String m_strParameterDefinitionName;
    private String m_strParameterDefinitionDescription;
    private int m_nParameterDefinitionOptional;
    private int m_nParameterDefinitionIsList;
  
    public FunctionParameterDefinitionModel()
    {
    }

    public int getParameterDefinitionID()
    {
        return m_nParameterDefinitionID;
    }

    public void setParameterDefinitionID(int argParameterDefinitionID)
    {
        m_nParameterDefinitionID = argParameterDefinitionID;
    }

    public int getFunctionID()
    {
        return m_nFunctionID;
    }

    public void setFunctionID(int argFunctionID)
    {
        m_nFunctionID = argFunctionID;
    }

    public String getParameterDefinitionName()
    {
        return m_strParameterDefinitionName;
    }

    public void setParameterDefinitionName(String argParameterDefinitionName)
    {
        m_strParameterDefinitionName = argParameterDefinitionName;
    }

    public String getParameterDefinitionDescription()
    {
        return m_strParameterDefinitionDescription;
    }

    public void setParameterDefinitionDescription(String argParameterDefinitionDescription)
    {
        m_strParameterDefinitionDescription = argParameterDefinitionDescription;
    }

    public void setParameterDefinitionOptional(int argParameterDefinitionOptional)
    {
        m_nParameterDefinitionOptional = argParameterDefinitionOptional;
    }

    public int getParameterDefinitionOptional()
    {
        return m_nParameterDefinitionOptional;
    }

    public void setParameterDefinitionIsList(int argParameterDefinitionIsList)
    {
        m_nParameterDefinitionIsList = argParameterDefinitionIsList;
    }

    public int getParameterDefinitionIsList()
    {
        return m_nParameterDefinitionIsList;
    }
}


    

  
  
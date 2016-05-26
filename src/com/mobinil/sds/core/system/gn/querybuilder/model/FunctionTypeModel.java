package com.mobinil.sds.core.system.gn.querybuilder.model;
/**
 * Function Type Model -  Model class holds a set of attributes that matches the attributes of the function types table 
 * in the database.
 * For example:
 * <pre>
 *      FunctionTypeModel modFunctionType = new FunctionTypeModel();
 *      FunctionTypeModel.getFunctionTypeID();
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

public class FunctionTypeModel  implements Serializable
{
    private int m_nFunctionTypeID;
    private String m_strFunctionTypeName;
    private String m_strFunctionTypeDescription;

    public FunctionTypeModel()
    {
    }

    public int getFunctionTypeID()
    {
        return m_nFunctionTypeID;
    }

    public void setFunctionTypeID(int argFunctionTypeID)
    {
        m_nFunctionTypeID = argFunctionTypeID;
    }

    public String getFunctionTypeName()
    {
        return m_strFunctionTypeName;
    }

    public void setFunctionTypeName(String argFunctionTypeName)
    {
        m_strFunctionTypeName = argFunctionTypeName;
    }

    public String getFunctionTypeDescription()
    {
        return m_strFunctionTypeDescription;
    }

    public void setFunctionTypeDescription(String argFunctionTypeDescription)
    {
        m_strFunctionTypeDescription = argFunctionTypeDescription;
    }
}
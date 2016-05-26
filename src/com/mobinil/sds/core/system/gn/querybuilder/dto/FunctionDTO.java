package com.mobinil.sds.core.system.gn.querybuilder.dto;

/**
 * Function DTO - Data Transfer Object holds a set of the function attributes used to represent 
 * a function.
 * For example:
 * <pre>
 *      FunctionDTO dtoFunction = new FunctionDTO();
 *      dtoFunction.getFunctionID();
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
import java.util.Vector;

public class FunctionDTO implements Serializable, Cloneable
{
    private int m_nFunctionID;
    private String m_strFunctionName;
    private int m_nFunctionTypeID;
    private String m_strFunctionTypeName;
    private String m_strFunctionSQL;
    private String m_strFunctionDescription;
    private String m_strFunctionHelpText;
    private int m_nFunctionStatusID;
    private String m_strFunctionStatusName;
    private Vector m_colParameterDefinitions;

    public FunctionDTO ()
    {
    }

    public Object clone( ) throws CloneNotSupportedException 
    {
      return super.clone( );
    }

    public int getFunctionID ()
    {
        return m_nFunctionID;
    }

    public void setFunctionID (int argFunctionID)
    {
        m_nFunctionID = argFunctionID;
    }

    public String getFunctionName ()
    {
        return m_strFunctionName;
    }

    public void setFunctionName (String argFunctionName)
    {
        m_strFunctionName = argFunctionName;
    }

    public int getFunctionTypeID ()
    {
        return m_nFunctionTypeID;
    }

    public void setFunctionTypeID (int argFunctionTypeID)
    {
        m_nFunctionTypeID = argFunctionTypeID;
    }
    public String getFunctionTypeName ()
    {
        return m_strFunctionTypeName;
    }

    public void setFunctionTypeName (String argFunctionTypeName)
    {
        m_strFunctionTypeName = argFunctionTypeName;
    }

    public int getFunctionStatusID ()
    {
        return m_nFunctionStatusID;
    }

    public void setFunctionStatusID (int argFunctionStatusID)
    {
        m_nFunctionStatusID = argFunctionStatusID;
    }
    public String getFunctionStatusName ()
    {
        return m_strFunctionStatusName;
    }

    public void setFunctionStatusName (String argFunctionStatusName)
    {
        m_strFunctionStatusName = argFunctionStatusName;
    }
    
    public String getFunctionSQL ()
    {
        return m_strFunctionSQL;
    }

    public void setFunctionSQL (String argFunctionSQL)
    {
        m_strFunctionSQL = argFunctionSQL;
    }
    
    public String getFunctionDescription ()
    {
        return m_strFunctionDescription;
    }

    public void setFunctionDescription (String argFunctionDescription)
    {
        m_strFunctionDescription = argFunctionDescription;
    }

    public String getFunctionHelpText ()
    {
        return m_strFunctionHelpText;
    }

    public void setFunctionHelpText (String argFunctionHelpText)
    {
        m_strFunctionHelpText = argFunctionHelpText;
    }
    
    public Vector getParameterDefinitions ()
    {
        return m_colParameterDefinitions;
    }

    public void setParameterDefinitions (Vector argParameterDefinitions)
    {
        m_colParameterDefinitions = argParameterDefinitions;
    }
}

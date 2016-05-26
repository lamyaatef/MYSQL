package com.mobinil.sds.core.system.gn.querybuilder.dto;
/**
 * Function Field DTO - Data Transfer Object holds a set of the function attributes used to represent 
 * a function as a field.
 * For example:
 * <pre>
 *      FunctionFieldDTO dtoFunctionField = new FunctionFieldDTO();
 *      dtoFunctionField.getFunctionParameterValues();
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
import java.util.*;
import com.mobinil.sds.core.system.gn.querybuilder.model.FunctionParameterValueModel;

public class FunctionFieldDTO extends FieldDTO implements Serializable, Cloneable
{
    private FunctionDTO m_dtoFunction;
    private Vector m_colFunctionParameterValues;

    public FunctionFieldDTO()
    {
    }
    
    public Object clone( ) throws CloneNotSupportedException 
    {
      FunctionFieldDTO functionFieldDTO = (FunctionFieldDTO) super.clone( );
      functionFieldDTO.setFunctionDTO((FunctionDTO)m_dtoFunction.clone());
      Vector functionParameterValues = new Vector () ;
      for (int i=0 ; i<m_colFunctionParameterValues.size() ; i++)
      {
        functionParameterValues.addElement(
          ( (FunctionParameterValueModel) m_colFunctionParameterValues.elementAt(i) ).clone()
        ); //(Vector) m_colFunctionParameterValues.clone()
      }
      functionFieldDTO.setFunctionParameterValues( functionParameterValues );
      return functionFieldDTO ;
    }

    /*public FunctionFieldDTO myClone( )  
    {
      FunctionFieldDTO newfunctionFieldDTO = (FunctionFieldDTO) super.clone( );
      functionFieldDTO.setFunctionDTO((FunctionDTO)m_dtoFunction.clone());
      functionFieldDTO.setFunctionParameterValues( (Vector) m_colFunctionParameterValues.clone() );
      return functionFieldDTO ;
    }*/
    
    public FunctionDTO getFunctionDTO()
    {
        return m_dtoFunction;
    }

    public void setFunctionDTO(FunctionDTO argFunction)
    {
        m_dtoFunction = argFunction;
    }

    public Vector getFunctionParameterValues()
    {
        return m_colFunctionParameterValues;
    }

    public void setFunctionParameterValues(Vector argFunctionParameterValues)
    {
        m_colFunctionParameterValues = argFunctionParameterValues;
    }
}
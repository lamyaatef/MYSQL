package com.mobinil.sds.core.system.sop.equations.dto;
import java.util.Vector;
import java.io.*;

public class EquationDTO implements Serializable
{
  private Vector vecEquationOperator;
  private Vector vecEquationObjectType;
  private Vector vecEquationTerms;
  private Vector vecEquationElements;
  private Vector vecEquation;
  
  public EquationDTO()
  {
  }

  public Vector getVecEquationOperator()
  {
    return vecEquationOperator;
  }

  public void setVecEquationOperator(Vector newVecEquationOperator)
  {
    vecEquationOperator = newVecEquationOperator;
  }
  /////////////////////////////////////////////////
  public Vector getVecEquationObjectType()
  {
    return vecEquationObjectType;
  }

  public void setVecEquationObjectType(Vector newVecEquationObjectType)
  {
    vecEquationObjectType = newVecEquationObjectType;
  }
  /////////////////////////////////////////////////
  public Vector getVecEquationTerms()
  {
    return vecEquationTerms;
  }

  public void setVecEquationTerms(Vector newVecEquationTerms)
  {
    vecEquationTerms = newVecEquationTerms;
  }
  /////////////////////////////////////////////////
  public Vector getVecEquationElements()
  {
    return vecEquationElements;
  }

  public void setVecEquationElements(Vector newVecEquationElements)
  {
    vecEquationElements = newVecEquationElements;
  }
  /////////////////////////////////////////////////
  public Vector getVecEquation()
  {
    return vecEquation;
  }

  public void setVecEquation(Vector newVecEquation)
  {
    vecEquation = newVecEquation;
  }
}
package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;

public class ParameterFieldDTO extends FieldDTO implements Serializable, Cloneable
{
  private int m_nValueType;
  private String m_strLabelText;
  private int m_nControlTypeID;
  private int m_nOrder;

  public ParameterFieldDTO()
  {
  }

  public Object clone( ) throws CloneNotSupportedException 
  {
    return super.clone( );
  }

  public int geValueType()
  {
    return m_nValueType;
  }

  public void setValueType(int newValueType)
  {
    m_nValueType = newValueType;
  }

  public String getLabelText()
  {
    return m_strLabelText;
  }

  public void setLabelText(String newLabelText)
  {
    m_strLabelText = newLabelText;
  }

  public int getControlTypeID()
  {
    return m_nControlTypeID;
  }

  public void setControlTypeID(int newControlTypeID)
  {
    m_nControlTypeID = newControlTypeID;
  }

  public int getOrder()
  {
    return m_nOrder;
  }

  public void setOrder(int newOrder)
  {
    m_nOrder = newOrder;
  }

}
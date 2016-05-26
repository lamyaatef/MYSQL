package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;

public class OrderByDTO implements Serializable
{
  private FieldDTO m_objFieldDTO;
  private String m_strOrderType;

  public OrderByDTO()
  {
  }

  public FieldDTO getFieldDTO()
  {
    return m_objFieldDTO;
  }

  public void setFieldDTO(FieldDTO newFieldDTO)
  {
    m_objFieldDTO = newFieldDTO;
  }

  public String getOrderType()
  {
    return m_strOrderType;
  }

  public void setOrderType(String newOrderType)
  {
    m_strOrderType = newOrderType;
  }
}
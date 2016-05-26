package com.mobinil.sds.core.system.gn.querybuilder.dto;

public class RelationSymbolDTO extends ConditionElement
{
  private int m_nRelationSymbolID;
  private String m_strRelationSymboName;
  private String m_strRelationSymbolSQL;
  private String m_strRelationSymbolDescription;

  public RelationSymbolDTO()
  {
  }

  public int getRelationSymbolID()
  {
    return m_nRelationSymbolID;
  }

  public void setRelationSymbolID(int newRelationSymbolID)
  {
    m_nRelationSymbolID = newRelationSymbolID;
  }

  public String getRelationSymboName()
  {
    return m_strRelationSymboName;
  }

  public void setRelationSymboName(String newRelationSymboName)
  {
    m_strRelationSymboName = newRelationSymboName;
  }

  public String getRelationSymbolSQL()
  {
    return m_strRelationSymbolSQL;
  }

  public void setRelationSymbolSQL(String newRelationSymbolSQL)
  {
    m_strRelationSymbolSQL = newRelationSymbolSQL;
  }

  public String getRelationSymbolDescription()
  {
    return m_strRelationSymbolDescription;
  }

  public void setRelationSymbolDescription(String newRelationSymbolDescription)
  {
    m_strRelationSymbolDescription = newRelationSymbolDescription;
  }

  
}
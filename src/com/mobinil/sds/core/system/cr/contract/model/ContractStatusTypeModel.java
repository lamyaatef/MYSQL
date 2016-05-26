package com.mobinil.sds.core.system.cr.contract.model;
import java.io.Serializable;

public class ContractStatusTypeModel implements Serializable
{
  int id;
  String name;

  public ContractStatusTypeModel(int id , String name)
  {
  this.id = id ; 
  this.name = name ; 
  }

  public int getId()
  {
    return id;
  }

  public void setId(int newId)
  {
    id = newId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String newName)
  {
    name = newName;
  }
}
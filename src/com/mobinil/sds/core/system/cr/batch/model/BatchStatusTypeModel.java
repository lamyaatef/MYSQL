package com.mobinil.sds.core.system.cr.batch.model;
import java.io.Serializable;
/*
 * this class is a model of batch status it contains just an id and name for a status
 * and just getters and setters 
 */
public class BatchStatusTypeModel implements Serializable
{
  int id;
  String name;

  public BatchStatusTypeModel(int id , String name)
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
package com.mobinil.sds.core.system.cr.batch.model;
import java.io.Serializable;
/*
 * this class is a model that contain batch type information 
 * it only contains an id and a name of the type 
 * with all the getters and setters 
 */
public class BatchTypeModel implements Serializable 
{
  int id;
  String name;

  public BatchTypeModel(int id, String name)
  {
  this.id = id;
  this.name = name; 
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
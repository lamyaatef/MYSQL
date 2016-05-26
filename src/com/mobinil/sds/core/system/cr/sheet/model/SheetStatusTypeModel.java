package com.mobinil.sds.core.system.cr.sheet.model;
import java.io.Serializable;
/*
 * sheet status type model 
 * it encapsulate both the id and the name of the sheet type 
 * 1- constructor taht take both id and name of the sheet status type 
 * 2- get the sheet status type id 
 * 3-set the sheet status id  
 * 4- get the name of the sheet status 
 * 5- set the name of the sheet status 
 */
public class SheetStatusTypeModel implements Serializable
{
  private int id;
  private String name;

/*
 * 1- constructor taht take both id and name of the sheet status type 
 */
  public SheetStatusTypeModel(int id , String name)
  {
  this.id = id ; 
  this.name = name ; 
  }

/*
 * 2- get the sheet status type id 
 */
  public int getId()
  {
    return id;
  }
/*
 * 3-set the sheet status id 
 */
  public void setId(int newId)
  {
    id = newId;
  }

/*
 * 4- get the name of the sheet status 
 */
  public String getName()
  {
    return name;
  }

/*
 * 5- set the name of the sheet status  
 */
  public void setName(String newName)
  {
    name = newName;
  }
}
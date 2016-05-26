package com.mobinil.sds.core.system.cr.sheet.model;
import java.io.Serializable;
import java.sql.*;
/*
 * sheet type status model 
 * it encapsulate both the id and the name of the sheet type status
 * 1- constructor taht take both id and name of the sheet type status
 * 2- get the sheet type status id 
 * 3- set the sheet type status id  
 * 4- get the name of the sheet type status 
 * 5- set the name of the sheet type status
 * 6- constructor that take result set and extract the two information needed
 * to construct this class 
 */
public class SheetTypeStatusModel implements Serializable
{

  public static final String SHEET_TYPE_STATUS_NAME = "SHEET_TYPE_STATUS_NAME";
  public static final String SHEET_TYPE_STATUS_ID = "SHEET_TYPE_STATUS_ID";
  
  private String id;
  private String name;

/*
 * 6- constructor that take result set and extract the two information needed
 * to construct this class 
 */
  public  SheetTypeStatusModel(ResultSet res) throws Exception
  {
     this.id = res.getString(SHEET_TYPE_STATUS_ID) ; 
     this.name = res.getString(SHEET_TYPE_STATUS_NAME) ; 
  }

/*
 * 1- constructor taht take both id and name of the sheet type status 
 */
  public SheetTypeStatusModel(String id , String name)
  {
  this.id = id ; 
  this.name = name ; 
  }

/*
 * 2- get the sheet type status id 
 */
  public String getId()
  {
    return id;
  }
/*
 * 3-set the sheet type status id 
 */
  public void setId(String newId)
  {
    id = newId;
  }

/*
 * 4- get the name of the sheet type status 
 */
  public String getName()
  {
    return name;
  }

/*
 * 5- set the name of the sheet type status  
 */
  public void setName(String newName)
  {
    name = newName;
  }
}
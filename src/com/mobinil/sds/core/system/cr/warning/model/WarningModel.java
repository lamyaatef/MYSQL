package com.mobinil.sds.core.system.cr.warning.model;
import java.io.Serializable;

/*
 * THIS is the warning model it encapsulate warning id , warning name , warning description , warning status id 
 * 1-constructor that take both the id and name of the warning
 * 2- constructor that take id, name , description and status of the warning
 * 3- get warning description
 * 4-set warning description 
 * 5- get warning id  
 * 6- set warning id 
 * 7- get warning name
 * 8- set warning name 
 * 9- get warning status id
 * 10- set warning status id 
 */

public class WarningModel implements Serializable 
{
  private String warningId;
  private String warningName;
  private String warningDesc;
  private String warningStatusID;

 /*
  * 1-constructor that take both the id and name of the warning 
  */
  public WarningModel(String id , String name)
  {
    this.warningId = id;
    this.warningName = name;
  }

/*
 * 2- constructor that take id, name , description and status of the warning  
 */
  public WarningModel(String id , String name, String Desc, String status)
  {
    this.warningId = id;
    this.warningName = name;
    this.warningStatusID = status;
    this.warningDesc = Desc;
  }

/*
 * 3- get warning description 
 */
  public String getWarningDesc()
  {
    return warningDesc;
  }

/*
 * 4-set warning description 
 */
  public void setWarningDesc(String newWarningDesc)
  {
    warningDesc = newWarningDesc;
  }

/*
 * 5- get warning id 
 */
  public String getWarningId()
  {
    return warningId;
  }

/*
 * 6- set warning id 
 */
  public void setWarningId(String newWarningId)
  {
    warningId = newWarningId;
  }

/*
 * 7- get warning name 
 */
  public String getWarningName()
  {
    return warningName;
  }

/*
 * 8- set warning name 
 */
  public void setWarningName(String newWarningName)
  {
    warningName = newWarningName;
  }

/*
 * 9- get warning status id
 */
  public String getWarningStatusID()
  {
    return warningStatusID;
  }

/*
 * 10- set warning status id 
 */
  public void setWarningStatusID(String newWarningStatusID)
  {
    warningStatusID = newWarningStatusID;
  }
}
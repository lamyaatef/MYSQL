package com.mobinil.sds.core.system.cr.sheet.model;
import java.io.Serializable;
import java.util.*;
import java.sql.*;

/*
 * Sheet Type Model it encapsulate the following information of the sheet type in it
 * 
 * Sheet Type Id;
 * Sheet Type Name;
 * Sheet Type Description;
 * Sheet Authentication Percentage;
 * Sheet Type Status Id;
 * Sheet POS Question Flag which indicats whether contracts in sheets of this type should be asked for the POS reachnes while verifing.
 * Sheet Discard Unreal Unreachable Flag 
 * 
 *
 * 1- Sheet type model constructoer that take a result set as input 
 * 2- get sheet type id 
 * 3- set sheet type id  
 * 4- get sheet type name  
 * 5- set sheet type name 
 * 6- get sheet type description
 * 7- set sheet type description
 * 8- get sheet type status id 
 * 9- set sheet type status id 
 * 10- get sheet type status name 
 * 11- set sheet type status name 
 * 12- get sheet Authentication Percentage
 * 13- set sheet Authentication Percentage 
 * 14- get sheet pos question flag
 * 15- set sheet pos question flag  
 * 16- get sheet Discard Unreal Unreachable Flag 
 * 17- set sheet Discard Unreal Unreachable Flag
 * 
 */
public class SheetTypeModel implements Serializable 
{

  private String sheetTypeId;
  private String sheetTypeName;
  private String sheetTypeDesc;
  private String sheetTypeStatusId;
  private String sheetTypeStatusName;
  private double sheetAuthPercentage;
  private String sheetPOSQuestionFlag;
  private String sheetDiscardUnrealUnreachableFlag;

  public static final String SHEET_TYPE_ID = "SHEET_TYPE_ID";
  public static final String SHEET_TYPE_NAME = "SHEET_TYPE_NAME";
  public static final String SHEET_TYPE_DESC = "SHEET_TYPE_DESC";
  public static final String SHEET_AUTH_PERCENTAGE = "SHEET_AUTH_PERCENTAGE";
  public static final String SHEET_TYPE_STATUS_ID = "SHEET_TYPE_STATUS_ID";
  public static final String SHEET_TYPE_STATUS_NAME = "SHEET_TYPE_STATUS_NAME";
  public static final String POS_QUESTION = "POS_QUESTION";
  public static final String DISCARD_UNREAL_UNREACHABLE= "DISCARD_UNREAL_UNREACHABLE";

  public SheetTypeModel()
  {
  }
  /*
   * 1- Sheet type model constructoer that take a result set as input
   */  
  public SheetTypeModel(ResultSet res)
  {
    try 
    {
      this.sheetTypeId = res.getString(SHEET_TYPE_ID);
      this.sheetTypeName = res.getString(SHEET_TYPE_NAME);
      this.sheetTypeDesc = res.getString(SHEET_TYPE_DESC); 
      this.sheetAuthPercentage = res.getDouble(SHEET_AUTH_PERCENTAGE);
      this.sheetTypeStatusId = res.getString(SHEET_TYPE_STATUS_ID);
      this.sheetPOSQuestionFlag = res.getString(POS_QUESTION);
      this.sheetDiscardUnrealUnreachableFlag = res.getString(DISCARD_UNREAL_UNREACHABLE);
      this.sheetTypeStatusName = res.getString(SHEET_TYPE_STATUS_NAME);
    }
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
  }
/*
 * 2- get sheet type id
 */
  public String getSheetTypeId()
  {
    return sheetTypeId;
  }

/*
 * 3- set sheet type id
 */
  public void setSheetTypeId(String argSheetTypeId)
  {
    sheetTypeId = argSheetTypeId;
  }

/*
 * 4- get sheet type name
 */
  public String getSheetTypeName()
  {
    return sheetTypeName;
  }

/*
 * 5- set sheet type name
 */
  public void setSheetTypeName(String argSheetTypeName)
  {
    sheetTypeName = argSheetTypeName;
  }

/*
 * 6- get sheet type description
 */
  public String getSheetTypeDesc()
  {
    return sheetTypeDesc;
  }

/*
 * 7- set sheet type description
 */
  public void setSheetTypeDesc(String argSheetTypeDesc)
  {
    sheetTypeDesc = argSheetTypeDesc;
  }

/*
 * 8- get sheet type status id
 */
  public String getSheetTypeStatusId()
  {
    return sheetTypeStatusId;
  }

/*
 * 9- set sheet type status id
 */
  public void setSheetTypeStatusId(String argSheetTypeStatusId)
  {
    sheetTypeStatusId = argSheetTypeStatusId;
  }

/*
 * 10- get sheet type status name
 */
  public String getSheetTypeStatusName()
  {
    return sheetTypeStatusName;
  }

/*
 * 11- set sheet type status name
 */
  public void setSheetTypeStatusName(String argSheetTypeStatusName)
  {
    sheetTypeStatusName = argSheetTypeStatusName;
  }

/*
 * 12- get sheet Authentication Percentage
 */
  public double getSheetAuthPercentage()
  {
    return sheetAuthPercentage;
  }

/*
 * 13- set sheet Authentication Percentage
 */
  public void setSheetAuthPercentage(double argSheetAuthPercentage)
  {
    sheetAuthPercentage = argSheetAuthPercentage;
  }

/*
 * 14- get sheet pos question flag
 */
  public String getSheetPOSQuestionFlag()
  {
    return sheetPOSQuestionFlag;
  }

/*
 * 15- set sheet pos question flag
 */
  public void setSheetPOSQuestionFlag(String argSheetPOSQuestionFlag)
  {
    sheetPOSQuestionFlag = argSheetPOSQuestionFlag;
  }

/*
 * 16- get sheet Discard Unreal Unreachable Flag
 */
  public String getSheetDiscardUnrealUnreachableFlag()
  {
    return sheetDiscardUnrealUnreachableFlag;
  }

/*
 * 17- set sheet Discard Unreal Unreachable Flag
 */
  public void setSheetDiscardUnrealUnreachableFlag(String argSheetDiscardUnrealUnreachableFlag)
  {
    sheetDiscardUnrealUnreachableFlag = argSheetDiscardUnrealUnreachableFlag;
  }

}
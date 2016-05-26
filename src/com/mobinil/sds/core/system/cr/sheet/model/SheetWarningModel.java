package com.mobinil.sds.core.system.cr.sheet.model;
import java.sql.*;
import java.io.*;

/*
 * this class encapsulate a Sheet Warning 
 * it has the following fields of warning encapsulated in it 
 * sheet warning id 
 * sheet warning name 
 * sheet warning description 
 * sheet warning status id 
 * sheet phase id 
 * sheet warning type id 
 * 
 * also it has the fileds names of the database of the warning table as constants 
 * 
 * 1-default consturcotr
 * 2- constructor that take a result set and fill the internal fields of the class with 
 * the values from the fields whihc their names are the values of the constants in this class 
 * 3- get sheet phase id 
 * 4- set sheet phase id
 * 5- get the sheet warning id 
 * 6- set sheet warning id  
 * 7- get sheet warning name 
 * 8- set sheet warning name  
 * 9- get sheet warning type id 
 * 10- set sheet warning type id
 * 11- get sheet warning description 
 * 12- set sheet warning description  
 * 13- get sheet warning status id 
 * 14- set sheet warning status id   
 */

public class SheetWarningModel implements Serializable 
{
  private String m_strSheetWarningID;
  private String m_strSheetWarningName;
  private String m_strSheetwarningDesc;
  private String m_strSheetwarningStatusID;
  private String m_strSheetPhaseID;
  private String m_strSheetWarningTypeID;

  public static final String WARNING_ID= "SHEET_WARNING_TYPE_ID";
  public static final String WARNING_NAME= "SHEET_WARNING_TYPE_NAME";
  public static final String WARNING_DESC= "SHEET_WARNING_TYPE_DESC";
  public static final String PHASE_ID = "PHASE_ID";
  public static final String WARNING_TYPE_ID = "WARNING_TYPE_ID";
  public static final String WARNING_STATUS_ID = "WARNING_STATUS_ID";

  /*
   * 1-default consturcotr
   */
  public SheetWarningModel()
  {
  }

/*
 * 2- constructor that take a result set and fill the internal fields of the class with 
 * the values from the fields whihc their names are the values of the constants in this class 
 */
  public SheetWarningModel(ResultSet res)
  {
    try{
    m_strSheetWarningID = res.getString(WARNING_ID);
    m_strSheetWarningName = res.getString(WARNING_NAME);
    m_strSheetwarningDesc = res.getString(WARNING_DESC);
    m_strSheetPhaseID = res.getString(PHASE_ID);
    m_strSheetWarningTypeID = res.getString(WARNING_TYPE_ID);
    m_strSheetwarningStatusID = res.getString(WARNING_STATUS_ID);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

/*
 * 3- get sheet phase id 
 */
  public String getSheetPhaseID()
  {
    return m_strSheetPhaseID;
  }

/*
 * 4- set sheet phase id 
 */
  public void setSheetPhaseID(String argSheetPhaseID)
  {
    m_strSheetPhaseID = argSheetPhaseID;
  }

/*
 * 5- get the sheet warning id 
 */
  public String getSheetWarningID()
  {
    return m_strSheetWarningID;
  }

/*
 * 6- set sheet warning id 
 */
  public void setSheetWarningID(String argSheetWarningID)
  {
    m_strSheetWarningID = argSheetWarningID;
  }

/*
 * 7- get sheet warning name 
 */
  public String getSheetWarningName()
  {
    return m_strSheetWarningName;
  }

/*
 * 8- set sheet warning name 
 */
  public void setSheetWarningName(String argSheetWarningName)
  {
    m_strSheetWarningName = argSheetWarningName;
  }

/*
 * 9- get sheet warning type id 
 */
  public String getSheetWarningTypeID()
  {
    return m_strSheetWarningTypeID;
  }

/*
 * 10- set sheet warning type id
 */
  public void setSheetWarningTypeID(String argSheetWarningTypeID)
  {
    m_strSheetWarningTypeID = argSheetWarningTypeID;
  }

/*
 * 11- get sheet warning description 
 */
  public String getSheetwarningDesc()
  {
    return m_strSheetwarningDesc;
  }

/*
 * 12- set sheet warning description 
 */
  public void setSheetwarningDesc(String argSheetwarningDesc)
  {
    m_strSheetwarningDesc = argSheetwarningDesc;
  }

/*
 * 13- get sheet warning status id 
 */
  public String getSheetwarningStatusID()
  {
    return m_strSheetwarningStatusID;
  }

/*
 * 14- set sheet warning status id 
 */
  public void setSheetwarningStatusID(String argSheetwarningStatusID)
  {
    m_strSheetwarningStatusID = argSheetwarningStatusID;
  }
}
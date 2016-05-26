package com.mobinil.sds.core.system.cr.contract.model;
import java.sql.*;
import java.io.*;
/*
 * Contract Warning Model encapsulate the follwoing about a contract warning 
 * warning id 
 * warning name
 * warning description 
 * phase id 
 * warning type id 
 * warning status id 
 * 
 * also it has the fileds names that map to this information in the the database 
 * as constants 
 * 
 * 1- constructor that take a result set and empty the needed information from it 
 * to fill the new instance of fields values
 * 2- get contract warning id
 * 3- set contract warning id 
 * 4- get contract warning name  
 * 5- set contract warning name 
 * 6- get contract phase id 
 * 7- set contract phase id
 * 8- get contract warning type id 
 * 9- set contract warning type id  
 * 10- get contract warning description 
 * 11- set contract warning description
 * 12- get contract warning status id 
 * 13- set contract warning status id  
 * 
 */

public class ContractWarningModel implements Serializable 
{
  private String m_strContractWarningID;
  private String m_strContractWarningName;
  private String m_strContractwarningDesc;
  private String m_strContractPhaseID;
  private String m_strContractWarningTypeID;
  private String m_strContractWarningTypeName;
  private String m_strContractwarningStatusID;
  private String m_strContractwarningStatusName;
  private String m_strContractwarningSuggestedStatusID;
  private String m_strContractwarningSuggestedStatusName;
  
  public static final String WARNING_ID= "CONTRACT_WARNING_TYPE_ID";
  public static final String WARNING_NAME= "CONTRACT_WARNING_TYPE_NAME";
  public static final String WARNING_DESC= "CONTRACT_WARNING_TYPE_DESC";
  public static final String PHASE_ID = "PHASE_ID";
  public static final String WARNING_TYPE_ID = "WARNING_TYPE_ID";
  public static final String WARNING_TYPE_NAME = "WARNING_TYPE_NAME";
  public static final String WARNING_STATUS_ID = "WARNING_STATUS_ID";
  public static final String WARNING_STATUS_NAME = "WARNING_STATUS_NAME";
  public static final String WARNING_SUGGESTED_STATUS_ID = "WARNING_SUGGESTED_STATUS_ID";
  public static final String WARNING_SUGGESTED_STATUS_NAME = "WARNING_SUGGESTED_STATUS_NAME";
  
  public ContractWarningModel()
  {
  }

  /*
   * 1- constructor that take a result set and empty the needed information from it 
   * to fill the new instance of fields values 
   */
  public ContractWarningModel(ResultSet res)
  {
    try{
      m_strContractWarningID = res.getString(WARNING_ID);
      m_strContractWarningName = res.getString(WARNING_NAME);
      m_strContractwarningDesc = res.getString(WARNING_DESC);
      m_strContractPhaseID = res.getString(PHASE_ID);
      m_strContractWarningTypeID = res.getString(WARNING_TYPE_ID);
      m_strContractWarningTypeName = res.getString(WARNING_TYPE_NAME);
      m_strContractwarningStatusID = res.getString(WARNING_STATUS_ID);
      m_strContractwarningStatusName = res.getString(WARNING_STATUS_NAME);
      m_strContractwarningSuggestedStatusID = res.getString(WARNING_SUGGESTED_STATUS_ID);
      m_strContractwarningSuggestedStatusName = res.getString(WARNING_SUGGESTED_STATUS_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

 /*
  * 2- get contract warning id
  */
  public String getContractWarningID()
  {
    return m_strContractWarningID;
  }

/*
 * 3- set contract warning id 
 */
  public void setContractWarningID(String argContractWarningID)
  {
    m_strContractWarningID = argContractWarningID;
  }

/*
 * 4- get contract warning name 
 */
  public String getContractWarningName()
  {
    return m_strContractWarningName;
  }

/*
 * 5- set contract warning name 
 */
  public void setContractWarningName(String argContractWarningName)
  {
    m_strContractWarningName = argContractWarningName;
  }

/*
 * 6- get contract phase id 
 */
  public String getContractPhaseID()
  {
    return m_strContractPhaseID;
  }

/*
 * 7- set contract phase id
 */
  public void setContractPhaseID(String argContractPhaseID)
  {
    m_strContractPhaseID = argContractPhaseID;
  }

/*
 * 8- get contract warning type id 
 */
  public String getContractWarningTypeID()
  {
    return m_strContractWarningTypeID;
  }

/*
 * 9- set contract warning type id 
 */
  public void setContractWarningTypeID(String argContractWarningTypeID)
  {
    m_strContractWarningTypeID = argContractWarningTypeID;
  }

/*
 * 10- get contract warning description
 */
  public String getContractwarningDesc()
  {
    return m_strContractwarningDesc;
  }

/*
 * 11- set contract warning description
 */
  public void setContractwarningDesc(String argContractwarningDesc)
  {
    m_strContractwarningDesc = argContractwarningDesc;
  }

/*
 * 12- get contract warning status id
 */
  public String getContractwarningStatusID()
  {
    return m_strContractwarningStatusID;
  }

/*
 * 13- set contract warning status id 
 */
  public void setContractwarningStatusID(String argContractwarningStatusID)
  {
    m_strContractwarningStatusID = argContractwarningStatusID;
  }

  public String getContractwarningStatusName() {
    return m_strContractwarningStatusName;
  }

  public String getContractWarningTypeName() {
    return m_strContractWarningTypeName;
  }

  public void setContractWarningTypeName(String newContractWarningTypeName) {
    m_strContractWarningTypeName = newContractWarningTypeName;
  }

  public void setContractwarningStatusName(String newContractwarningStatusName) {
    m_strContractwarningStatusName = newContractwarningStatusName;
  }

///////////////////////new warning suggested status////////////////
  public String getContractwarningSuggestedStatusID()
  {
    return m_strContractwarningSuggestedStatusID;
  }

  public void setContractwarningSuggestedStatusID(String argContractwarningSuggestedStatusID)
  {
    m_strContractwarningSuggestedStatusID = argContractwarningSuggestedStatusID;
  }

  public String getContractwarningSuggestedStatusName()
  {
    return m_strContractwarningSuggestedStatusName;
  }

  public void setContractwarningSuggestedStatusName(String argContractwarningSuggestedStatusName)
  {
    m_strContractwarningSuggestedStatusName = argContractwarningSuggestedStatusName;
  }

}
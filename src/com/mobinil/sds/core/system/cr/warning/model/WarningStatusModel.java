package com.mobinil.sds.core.system.cr.warning.model;
import java.io.Serializable;

/*
 * This is Warning Status Model that whold the information that represent a warning status which is 
 * the id of that status and its name 
 * 
 * 1-constructor take warning status id and warning status name 
 * 2- get warning status id 
 * 3- set warning status id 
 * 4- get warning status name 
 * 5- set warning status name 
 */
public class WarningStatusModel implements Serializable 
{
  private String m_strWarningStatusID;
  private String m_strWarningStatusName;

/*
 * 1-constructor take warning status id and warning status name
 */
  public WarningStatusModel(String newWarningStatusID, String newWarningStatusName)
  {
    m_strWarningStatusID = newWarningStatusID;
    m_strWarningStatusName = newWarningStatusName;
  }

/*
 * 2- get warning status id
 */
  public String getWarningStatusID()
  {
    return m_strWarningStatusID;
  }

/*
 * 3- set warning status id
 */
  public void setWarningStatusID(String newWarningStatusID)
  {
    m_strWarningStatusID = newWarningStatusID;
  }

/*
 * 4- get warning status name
 */
  public String getWarningStatusName()
  {
    return m_strWarningStatusName;
  }

/*
 * 5- set warning status name
 */
  public void setWarningStatusName(String newWarningStatusName)
  {
    m_strWarningStatusName = newWarningStatusName;
  }
}
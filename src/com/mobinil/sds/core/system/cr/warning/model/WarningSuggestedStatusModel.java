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
public class WarningSuggestedStatusModel implements Serializable 
{
  private String m_strWarningSuggestedStatusID;
  private String m_strWarningSuggestedStatusName;

/*
 * 1-constructor take warning status id and warning status name
 */
  public WarningSuggestedStatusModel(String newWarningSuggestedStatusID, String newWarningSuggestedStatusName)
  {
    m_strWarningSuggestedStatusID = newWarningSuggestedStatusID;
    m_strWarningSuggestedStatusName = newWarningSuggestedStatusName;
  }

/*
 * 2- get warning status id
 */
  public String getWarningSuggestedStatusID()
  {
    return m_strWarningSuggestedStatusID;
  }

/*
 * 3- set warning status id
 */
  public void setWarningSuggestedStatusID(String newWarningSuggestedStatusID)
  {
    m_strWarningSuggestedStatusID = newWarningSuggestedStatusID;
  }

/*
 * 4- get warning status name
 */
  public String getWarningSuggestedStatusName()
  {
    return m_strWarningSuggestedStatusName;
  }

/*
 * 5- set warning status name
 */
  public void setWarningSuggestedStatusName(String newWarningSuggestedStatusName)
  {
    m_strWarningSuggestedStatusName = newWarningSuggestedStatusName;
  }
}
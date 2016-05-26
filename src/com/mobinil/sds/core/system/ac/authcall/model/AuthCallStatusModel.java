package com.mobinil.sds.core.system.ac.authcall.model;
import java.io.Serializable;

/*
 * Authentication Call status model 
 * It encapsulate both the id and the name of the Authentication Call Status.
 * 
 * 1- constructor taht take both id and name of the Authentication Call Status.
 * 2- get the Authentication Call Status id.
 * 3-set the Authentication Call Status id.
 * 4- get the name of the Authentication Call Status.
 * 5- set the name of the Authentication Call Status.
 * 
 */

public class AuthCallStatusModel implements Serializable 
{
  private String m_strAuthCallStatusID;
  private String m_strAuthCallStatusName;

/* 
 * 1- constructor taht take both id and name of the Authentication Call Status.
 */
  public AuthCallStatusModel(String argAuthCallStatusID, String argAuthCallStatusName)
  {
    m_strAuthCallStatusID = argAuthCallStatusID;
    m_strAuthCallStatusName = argAuthCallStatusName;
  }

/* 
 * 2- get the Authentication Call Status id.
 */
  public String getAuthCallStatusID()
  {
    return m_strAuthCallStatusID;
  }

/* 
 * 3-set the Authentication Call Status id.
 */
  public void setAuthCallStatusID(String argAuthCallStatusID)
  {
    m_strAuthCallStatusID = argAuthCallStatusID;
  }

/* 
 * 4- get the name of the Authentication Call Status.
 */
  public String getAuthCallStatusName()
  {
    return m_strAuthCallStatusName;
  }

/* 
 * 5- set the name of the Authentication Call Status.
 */
  public void setAuthCallStatusName(String argAuthCallStatusName)
  {
    m_strAuthCallStatusName = argAuthCallStatusName;
  }
}
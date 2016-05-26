package com.mobinil.sds.core.system.ac.authcall.model;
import java.io.Serializable;

/*
 * Authentication Call Owner Status model 
 * It encapsulate both the id and the name of the Authentication Call Owner Status.
 * 
 * 1- constructor taht take both id and name of the Authentication Call Owner Status.
 * 2- get the Authentication Call Owner Status id.
 * 3-set the Authentication Call Owner Status id.
 * 4- get the name of the Authentication Call Owner Status.
 * 5- set the name of the Authentication Call Owner Status.
 * 
 */

public class AuthCallOwnerStatusModel implements Serializable 
{
  private String m_strAuthCallOwnerStatusID;
  private String m_strAuthCallOwnerStatusName;

/* 
 * 1- constructor taht take both id and name of the Authentication Call Owner Status.
 */
  public AuthCallOwnerStatusModel(String argAuthCallOwnerStatusID,
                             String argAuthCallOwnerStatusName)
  {
    m_strAuthCallOwnerStatusID = argAuthCallOwnerStatusID;
    m_strAuthCallOwnerStatusName = argAuthCallOwnerStatusName;
  }

/* 
 * 2- get the Authentication Call Owner Status id.
 */
  public String getAuthCallOwnerStatusID()
  {
    return m_strAuthCallOwnerStatusID;
  }

/* 
 * 3-set the Authentication Call Owner Status id.
 */
  public void setAuthCallOwnerStatusID(String argAuthCallOwnerStatusID)
  {
    m_strAuthCallOwnerStatusID = argAuthCallOwnerStatusID;
  }

/* 
 * 4- get the name of the Authentication Call Owner Status.
 */
  public String getAuthCallOwnerStatusName()
  {
    return m_strAuthCallOwnerStatusName;
  }

/* 
 * 5- set the name of the Authentication Call Owner Status.
 */
  public void setAuthCallOwnerStatusName(String argAuthCallOwnerStatusName)
  {
    m_strAuthCallOwnerStatusName = argAuthCallOwnerStatusName;
  }
}
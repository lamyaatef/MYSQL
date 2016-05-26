package com.mobinil.sds.core.system.ac.authcall.model;
import java.io.Serializable;

/*
 * Authentication Call POS Status model 
 * It encapsulate both the id and the name of the Authentication Call POS Status.
 * 
 * 1- constructor taht take both id and name of the Authentication Call POS Status.
 * 2- get the Authentication Call POS Status id.
 * 3-set the Authentication Call POS Status id.
 * 4- get the name of the Authentication Call POS Status.
 * 5- set the name of the Authentication Call POS Status.
 * 
 */

public class AuthCallPOSStatusModel implements Serializable 
{
  private String m_strAuthCallPOSStatusID;
  private String m_strAuthCallPOSStatusName;

/* 
 * 1- constructor taht take both id and name of the Authentication Call POS Status.
 */
  public AuthCallPOSStatusModel(String argAuthCallPOSStatusID, String argAuthCallPOSStatusName)
  {
    m_strAuthCallPOSStatusID = argAuthCallPOSStatusID;
    m_strAuthCallPOSStatusName = argAuthCallPOSStatusName;
  }

/* 
 * 2- get the Authentication Call POS Status id.
 */
  public String getAuthCallPOSStatusID()
  {
    return m_strAuthCallPOSStatusID;
  }

/* 
 * 3-set the Authentication Call POS Status id.
 */
  public void setAuthCallPOSStatusID(String argAuthCallPOSStatusID)
  {
    m_strAuthCallPOSStatusID = argAuthCallPOSStatusID;
  }

/* 
 * 4- get the name of the Authentication Call POS Status.
 */
  public String getAuthCallPOSStatusName()
  {
    return m_strAuthCallPOSStatusName;
  }

/* 
 * 5- set the name of the Authentication Call POS Status.
 */
  public void setAuthCallPOSStatusName(String argAuthCallPOSStatusName)
  {
    m_strAuthCallPOSStatusName = argAuthCallPOSStatusName;
  }
}
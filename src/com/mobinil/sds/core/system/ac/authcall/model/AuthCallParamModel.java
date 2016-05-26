package com.mobinil.sds.core.system.ac.authcall.model;
import java.io.Serializable;

/*
 * Authentication Call Parameter model 
 * It encapsulate all the data of the authentication call parameters
 * (Param ID - Param Owner Status ID - POS Validation Status ID- Auth Call POS Status Name).
 * 
 * 1- constructor taht take all the data of the Authentication Call Parameter
 *     (Param ID - Param Owner Status ID - POS Validation Status ID- Auth Call POS Status Name).
 * 2- Get the ID of the Authentication Call Parameter.
 * 3- Set the ID of the Authentication Call Parameter.
 * 4- Get the Param Owner Status ID of the Authentication Call Parameter.
 * 5- Set the Param Owner Status ID of the Authentication Call Parameter.
 * 6- Get the POS Validation Status ID of the Authentication Call Parameter.
 * 7- Set the POS Validation Status ID of the Authentication Call Parameter.
 * 8- Get the Auth Call POS Status Name of the Authentication Call Parameter.
 * 9- Set the Auth Call POS Status Name of the Authentication Call Parameter.
 * 
 */

public class AuthCallParamModel implements Serializable 
{
  private String m_strParamID;
  private String m_strParamOwnerStatusID;
  private String m_strPOSValidationStatusID;
  private String m_strAuthCallPOSStatusName;

/* 
 * 1- constructor taht take all the data of the Authentication Call Parameter
 *     (Param ID - Param Owner Status ID - POS Validation Status ID- Auth Call POS Status Name).
 */
  public AuthCallParamModel(String argParamID, String argParamOwnerStatusID, 
                            String argPOSValidationStatusID, 
                            String argAuthCallPOSStatusName)
  {
    m_strParamID = argParamID;
    m_strParamOwnerStatusID = argParamOwnerStatusID;
    m_strPOSValidationStatusID = argPOSValidationStatusID;
    m_strAuthCallPOSStatusName = argAuthCallPOSStatusName;
  }

/* 
 * 2- Get the ID of the Authentication Call Parameter.
 */
  public String getParamID()
  {
    return m_strParamID;
  }

/* 
 * 3- Set the ID of the Authentication Call Parameter.
 */
  public void setParamID(String argParamID)
  {
    m_strParamID = argParamID;
  }

/* 
 * 4- Get the Param Owner Status ID of the Authentication Call Parameter.
 */
  public String getParamOwnerStatusID()
  {
    return m_strParamOwnerStatusID;
  }

/* 
 * 5- Set the Param Owner Status ID of the Authentication Call Parameter.
 */
  public void setParamOwnerStatusID(String argParamOwnerStatusID)
  {
    m_strParamOwnerStatusID = argParamOwnerStatusID;
  }

/* 
 * 6- Get the POS Validation Status ID of the Authentication Call Parameter.
 */
  public String getPOSValidationStatusID()
  {
    return m_strPOSValidationStatusID;
  }

/* 
 * 7- Set the POS Validation Status ID of the Authentication Call Parameter.
 */
  public void setPOSValidationStatusID(String argPOSValidationStatusID)
  {
    m_strPOSValidationStatusID = argPOSValidationStatusID;
  }

/* 
 * 8- Get the Auth Call POS Status Name of the Authentication Call Parameter.
 */
  public String getAuthCallPOSStatusName()
  {
    return m_strAuthCallPOSStatusName;
  }

/* 
 * 9- Set the Auth Call POS Status Name of the Authentication Call Parameter.
 */
  public void setAuthCallPOSStatusName(String argAuthCallPOSStatusName)
  {
    m_strAuthCallPOSStatusName = argAuthCallPOSStatusName;
  }
}
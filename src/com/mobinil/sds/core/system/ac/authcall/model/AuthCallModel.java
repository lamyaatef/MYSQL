package com.mobinil.sds.core.system.ac.authcall.model;
import java.io.Serializable;

/*
 * Authentication Call model 
 * It encapsulate all the data of the authentication call
 * (ID - Date - Status ID- Status Name - User ID - Contract Status ID).
 * 
 * 1- constructor taht take all the data of the Authentication Call
 *     (ID - Date - Status ID- Status Name - User ID - Contract Status ID).
 * 2- Get the ID of the Authentication Call.
 * 3- Set the ID of the Authentication Call.
 * 4- Get the Date of the Authentication Call.
 * 5- Set the Date of the Authentication Call.
 * 6- Get the Status ID of the Authentication Call.
 * 7- Set the Status ID of the Authentication Call.
 * 8- Get the Status Name of the Authentication Call.
 * 9- Set the Status Name of the Authentication Call.
 * 10- Get the User ID of the Authentication Call.
 * 11- Set the User ID of the Authentication Call.
 * 12- Get the Contract Status ID of the Authentication Call.
 * 13- Set the Contract Status ID of the Authentication Call.
 * 
 */

public class AuthCallModel implements Serializable 
{
  
  private String m_strAuthCallID;
  private String m_strAuthCallDate;
  private String m_strAuthCallStatusID;
  private String m_strAuthCallStatusName;
  private String m_strAuthCallUserID;
  private String m_strContractStatusID;

/* 
 * 1- constructor taht take all the data of the Authentication Call
 *     (ID - Date - Status ID- Status Name - User ID - Contract Status ID).
 */
  public AuthCallModel(String argAuthCallID, String argAuthCallDate,
                                 String argAuthCallStatusID, String argAuthCallUserID,
                                 String argContractStatusID, String argAuthCallStatusName)
  {
    m_strAuthCallID = argAuthCallID;
    m_strAuthCallDate = argAuthCallDate;
    m_strAuthCallStatusID = argAuthCallStatusID;
    m_strAuthCallUserID = argAuthCallUserID;
    m_strContractStatusID = argContractStatusID;
    m_strAuthCallStatusName = argAuthCallStatusName;
  }

/* 
 * 2- Get the ID of the Authentication Call.
*/
  public String getAuthCallID()
  {
    return m_strAuthCallID;
  }

/* 
 * 3- Set the ID of the Authentication Call.
 */
  public void setAuthCallID(String argAuthCallID)
  {
    m_strAuthCallID = argAuthCallID;
  }

/* 
 * 4- Get the Date of the Authentication Call.
 */
  public String getAuthCallDate()
  {
    return m_strAuthCallDate;
  }

/* 
 * 5- Set the Date of the Authentication Call.
 */
  public void setAuthCallDate(String argAuthCallDate)
  {
    m_strAuthCallDate = argAuthCallDate;
  }

/* 
 * 6- Get the Status ID of the Authentication Call.
 */
  public String getAuthCallStatusID()
  {
    return m_strAuthCallStatusID;
  }

/* 
 * 7- Set the Status ID of the Authentication Call.
 */
  public void setAuthCallStatusID(String argAuthCallStatusID)
  {
    m_strAuthCallStatusID = argAuthCallStatusID;
  }

/* 
 * 8- Get the Status Name of the Authentication Call.
 */
  public String getAuthCallStatusName()
  {
    return m_strAuthCallStatusName;
  }

/*
 * 9- Set the Status Name of the Authentication Call.
 */
  public void setAuthCallStatusName(String argAuthCallStatusName)
  {
    m_strAuthCallStatusName = argAuthCallStatusName;
  }

/*
 * 10- Get the User ID of the Authentication Call.
 */
  public String getAuthCallUserID()
  {
    return m_strAuthCallUserID;
  }

/*
 * 11- Set the User ID of the Authentication Call.
 */
  public void setAuthCallUserID(String argAuthCallUserID)
  {
    m_strAuthCallUserID = argAuthCallUserID;
  }

/*
 * 12- Get the Contract Status ID of the Authentication Call.
 */
  public String getContractStatusID()
  {
    return m_strContractStatusID;
  }

/*
 * 13- Set the Contract Status ID of the Authentication Call.
 */
  public void setContractStatusID(String argContractStatusID)
  {
    m_strContractStatusID = argContractStatusID;
  }
}
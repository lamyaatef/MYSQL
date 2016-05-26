package com.mobinil.sds.core.system.sa.users.model;

/**
 * UserStatusModel Class represents one user status.
 * 
 * 1- m_nUserStatusID
 * 2- m_strUserStatusName
 * 3- m_strUserStatusDescription
 * 
 * It has three constructors
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.Serializable;

public class UserStatusModel implements Serializable 
{
  private int m_nUserStatusID;
  private String m_strUserStatusName;
  private String m_strUserStatusDescription;

  public UserStatusModel(int argUserStatusID)
  {
    m_nUserStatusID = argUserStatusID;
  }

  public UserStatusModel(int argUserStatusID, String argUserStatusName)
  {
    m_nUserStatusID = argUserStatusID;
    m_strUserStatusName = argUserStatusName;
  }

  public UserStatusModel(int argUserStatusID, String argUserStatusName, String argUserStatusDescription)
  {
    m_nUserStatusID = argUserStatusID;
    m_strUserStatusName = argUserStatusName;
    m_strUserStatusDescription = argUserStatusDescription;
  }

  public int getUserStatusID()
  {
    return m_nUserStatusID;
  }

  public void setUserStatusID(int argUserStatusID)
  {
    m_nUserStatusID = argUserStatusID;
  }

  public String getUserStatusName()
  {
    return m_strUserStatusName;
  }

  public void setUserStatusName(String argUserStatusName)
  {
    m_strUserStatusName = argUserStatusName;
  }

  public String getUserStatusDescription()
  {
    return m_strUserStatusDescription;
  }

  public void setUserStatusDescription(String argUserStatusDescription)
  {
    m_strUserStatusDescription = argUserStatusDescription;
  }
}
package com.mobinil.sds.core.system.sa.users.model;

/**
 * UserRoleModel Class represents one role user pair.
 * 
 * 1- m_nUserID
 * 2- m_nRoleID
 * 
 * It has one constructors.
 * 
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.Serializable;

public class UserRoleModel implements Serializable 
{
  private int m_nUserID;
  private int m_nRoleID;

  public UserRoleModel(int argUserID, int argRoleID)
  {
    m_nUserID = argUserID;
    m_nRoleID = argRoleID;
  }

  public int getUserID()
  {
    return m_nUserID;
  }

  public void setUserID(int argUserID)
  {
    m_nUserID = argUserID;
  }

  public int getRoleID()
  {
    return m_nRoleID;
  }

  public void setRoleID(int argRoleID)
  {
    m_nRoleID = argRoleID;
  }
}
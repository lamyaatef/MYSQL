package com.mobinil.sds.core.system.sa.users.model;

/**
 * UserDCMModel Class represents a DCM user pair.
 * 
 * 1- m_nUserID
 * 2- m_nDCMID
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

public class UserDCMModel implements Serializable 
{
  private int m_nUserID;
  private int m_nDCMID;

  public UserDCMModel(int argUserID, int argDCMID)
  {
    m_nUserID = argUserID;
    m_nDCMID = argDCMID;
  }

  public int getUserID()
  {
    return m_nUserID;
  }

  public void setUserID(int argUserID)
  {
    m_nUserID = argUserID;
  }

  public int getDCMID()
  {
    return m_nDCMID;
  }

  public void setDCMID(int argDCMID)
  {
    m_nDCMID = argDCMID;
  }
}
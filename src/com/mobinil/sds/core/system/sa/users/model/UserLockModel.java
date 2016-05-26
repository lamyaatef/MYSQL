package com.mobinil.sds.core.system.sa.users.model;

import java.io.Serializable;
public class UserLockModel implements Serializable 
{
 private int m_nUserLockID;
  private String m_strUserLockName;
  private String m_strUserLockDescription;

  public UserLockModel(int argUserLockID)
  {
    m_nUserLockID = argUserLockID;
  }

  public UserLockModel(int argUserLockID, String argUserLockName)
  {
    m_nUserLockID = argUserLockID;
    m_strUserLockName = argUserLockName;
  }

  public UserLockModel(int argUserLockID, String argUserLockName, String argUserLockDescription)
  {
    m_nUserLockID = argUserLockID;
    m_strUserLockName = argUserLockName;
    m_strUserLockDescription = argUserLockDescription;
  }

  public int getUserLockID()
  {
    return m_nUserLockID;
  }

  public void setUserLockID(int argUserLockID)
  {
    m_nUserLockID = argUserLockID;
  }

  public String getUserLockName()
  {
    return m_strUserLockName;
  }

  public void setUserLockName(String argUserLockName)
  {
    m_strUserLockName = argUserLockName;
  }

  public String getUserLockDescription()
  {
    return m_strUserLockDescription;
  }

  public void setUserLockDescription(String argUserLockDescription)
  {
    m_strUserLockDescription = argUserLockDescription;
  }
}
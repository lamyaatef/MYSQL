package com.mobinil.sds.core.system.sa.users.model;

/**
 * UserModel Class represents one user and its data.
 * 
 * 1- m_modPersonModel
 * 2- m_strUserPassword
 * 3- m_nUserStatusID
 * 4- m_strUserStatusName
 * 
 * It has six constructors.
 *
 * A person is a more general object than a user. The system may have many
 * persons but not all of them are users. But a user must be a person.
 * That is why a UserModel must take a PersonModel in its constructor.
 * A user is a person who has an account that he can use to login to the 
 * system. Privileges and DCMs are assigned to a user account.
 *
 * 
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import com.mobinil.sds.core.system.sa.persons.model.*;
import java.io.Serializable;

public class UserModel implements Serializable 
{
  private PersonModel m_modPersonModel;
  private String m_strUserPassword;
  private int m_nUserStatusID;
  private String m_strUserStatusName;
  private String m_isLocked;
  private String m_expire;

  
  

  public UserModel(PersonModel argPersonModel)
  {
    m_modPersonModel = argPersonModel;
  }

  public UserModel(PersonModel argPersonModel, String argUserPassword,String isLocked,String expire)
  {
    m_modPersonModel = argPersonModel;
    m_strUserPassword = argUserPassword;
   m_isLocked = isLocked;
   m_expire = expire;

  }

  public UserModel(PersonModel argPersonModel, int argUserStatusID)
  {
    m_modPersonModel = argPersonModel;
    m_nUserStatusID = argUserStatusID;
  }

  public UserModel(PersonModel argPersonModel, String argUserPassword, 
                   int argUserStatusID)
  { 
    m_modPersonModel = argPersonModel;
    m_strUserPassword = argUserPassword;
    m_nUserStatusID = argUserStatusID;
  }

  public UserModel(PersonModel argPersonModel, int argUserStatusID, 
                   String argUserStatusName)
  {
    m_modPersonModel = argPersonModel;
    m_nUserStatusID = argUserStatusID;
    m_strUserStatusName = argUserStatusName;
  }

  public UserModel(PersonModel argPersonModel, String argUserPassword, 
                   int argUserStatusID, String argUserStatusName,String isLocked)
  {
    m_modPersonModel = argPersonModel;
    m_strUserPassword = argUserPassword;
    m_nUserStatusID = argUserStatusID;
    m_strUserStatusName = argUserStatusName;
    m_isLocked = isLocked;
  }

  public PersonModel getPersonModel()
  {
    return m_modPersonModel;
  }

  public void setPersonModel(PersonModel argPersonModel)
  {
    m_modPersonModel = argPersonModel;
  }

  public String getUserPassword()
  {
    return m_strUserPassword;
  }

  public void setUserPassword(String argUserPassword)
  {
    m_strUserPassword = argUserPassword;
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
    public void setM_isLocked(String m_isLocked)
  {
    this.m_isLocked = m_isLocked;
  }

  public String getM_isLocked()
  {
    return m_isLocked;
  }

    public void setM_expire(String m_expire)
  {
    this.m_expire = m_expire;
  }

  public String getM_expire()
  {
    return m_expire;
  } 

}
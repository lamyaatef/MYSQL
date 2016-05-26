package com.mobinil.sds.core.system.sa.users.dto;

/**
 * UserDTO Class holds the data of one user as a UserModel, its roles as a 
 * Vector of UserRoleModel and the assigned DCMs as a Vector of UserDCMModel.
 * 
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import com.mobinil.sds.core.system.sa.users.model.UserModel;
import java.util.Vector;
import java.io.Serializable;

public class UserDTO implements Serializable 
{
  private UserModel m_modUser;
  private Vector m_colUserRoles;
  private Vector m_colUserDCMs;

  public UserModel getUserModel()
  {
    return m_modUser;
  }

  public void setUserModel(UserModel argUserModel)
  {
    m_modUser = argUserModel;
  }

  public Vector getUserRoles()
  {
    return m_colUserRoles;
  }

  public void setUserRoles(Vector argUserRoles)
  {
    m_colUserRoles = argUserRoles;
  }

  public Vector getUserDCMs()
  {
    return m_colUserDCMs;
  }

  public void setUserDCMs(Vector argUserDCMs)
  {
    m_colUserDCMs = argUserDCMs;
  }
}
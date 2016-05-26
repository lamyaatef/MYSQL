package com.mobinil.sds.core.system.sa.roles.dto;

/**
 * RoleDTO class holds the data of one role as a RoleModel, its privileges 
 * as a Vector of RolePrivilegeModel and a Vector of ModuleModels
 * 
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import com.mobinil.sds.core.system.sa.roles.model.RoleModel;
import com.mobinil.sds.core.system.sa.roles.model.RolePrivilegeModel;

import java.io.Serializable;

import java.util.Vector;

public class RoleDTO implements Serializable 
{
  private RoleModel m_modRole;
  private Vector<RolePrivilegeModel> m_colRolePrivileges;
  private Vector m_colRoleModules;

  public RoleModel getRoleModel()
  {
    return m_modRole;
  }

  public void setRoleModel(RoleModel argRoleModel)
  {
    m_modRole = argRoleModel;
  }

  public Vector getRolePrivileges()
  {
    return m_colRolePrivileges;
  }

  public void setRolePrivileges(Vector<RolePrivilegeModel> argRolePrivileges)
  {
    m_colRolePrivileges = argRolePrivileges;
  }

  public Vector<RolePrivilegeModel> getRoleModules()
  {
    return m_colRoleModules;
  }

  public void setRoleModules(Vector argRoleModules)
  {
    m_colRoleModules = argRoleModules;
  }
}
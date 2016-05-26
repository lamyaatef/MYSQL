package com.mobinil.sds.core.system.sa.modules.dto;

/**
 * ModuleDTO Class holds the data of one module as a MduleModel and its 
 * privileges as a Vector of PrivilegeModel.
 * 
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import com.mobinil.sds.core.system.sa.modules.model.ModuleModel;

import java.io.Serializable;

import java.util.Vector;

public class ModuleDTO implements Serializable 
{
  private ModuleModel m_modModule;
  private Vector m_colModulePrivileges;

  public ModuleModel getModuleModel()
  {
    return m_modModule;
  }

  public void setModuleModel(ModuleModel argModuleModel)
  {
    m_modModule = argModuleModel;
  }

  public Vector getModulePrivileges()
  {
    return m_colModulePrivileges;
  }

  public void setModulePrivileges(Vector argModulePrivileges)
  {
    m_colModulePrivileges = argModulePrivileges;
  }
}
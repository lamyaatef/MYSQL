package com.mobinil.sds.core.system.sa.modules.model;

/**
 * ModuleStatusModel Class represents one module status.
 * 
 * 1- m_nModuleStatusID
 * 2- m_strModuleStatusName
 * 3- m_strModuleStatusDescription
 * 
 * It has three constructors
 * 
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.Serializable;

public class ModuleStatusModel implements Serializable 
{
  private int m_nModuleStatusID;
  private String m_strModuleStatusName;
  private String m_strModuleStatusDescription;

  public ModuleStatusModel(int argModuleStatusID)
  {
    m_nModuleStatusID = argModuleStatusID;
  }

  public ModuleStatusModel(int argModuleStatusID, String argModuleStatusName)
  {
    m_nModuleStatusID = argModuleStatusID;
    m_strModuleStatusName = argModuleStatusName;
  }

  public ModuleStatusModel(int argModuleStatusID, String argModuleStatusName, 
                           String argModuleStatusDescription)
  {
    m_nModuleStatusID = argModuleStatusID;
    m_strModuleStatusName = argModuleStatusName;
    m_strModuleStatusDescription = argModuleStatusDescription;
  }

  public int getModuleStatusID()
  {
    return m_nModuleStatusID;
  }

  public void setModuleStatusID(int argModuleStatusID)
  {
    m_nModuleStatusID = argModuleStatusID;
  }

  public String getModuleStatusName()
  {
    return m_strModuleStatusName;
  }

  public void setModuleStatusName(String argModuleStatusName)
  {
    m_strModuleStatusName = argModuleStatusName;
  }

  public String getModuleStatusDescription()
  {
    return m_strModuleStatusDescription;
  }

  public void setModuleStatusDescription(String argModuleStatusDescription)
  {
    m_strModuleStatusDescription = argModuleStatusDescription;
  }
}
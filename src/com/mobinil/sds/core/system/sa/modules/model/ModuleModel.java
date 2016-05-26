package com.mobinil.sds.core.system.sa.modules.model;

/**
 * ModuleModel Class represents one moduleand its data.
 * 
 * 1- m_nModuleID
 * 2- m_strModuleName
 * 3- m_strModuleDescription
 * 4- m_nModuleStatusID
 * 5- m_strModuleStatusName
 * 
 * It has five constructors
 * 
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.Serializable;

public class ModuleModel implements Serializable
{
  private int m_nModuleID;
  private String m_strModuleName;
  private String m_strModuleDescription;
  private int m_nModuleStatusID;
  private String m_strModuleStatusName;
  
  public ModuleModel(int argModuleID)
  {
    m_nModuleID = argModuleID;
  }

  public ModuleModel(int argModuleID, String argModuleName)
  {
    m_nModuleID = argModuleID;
    m_strModuleName =  argModuleName;
  }

  public ModuleModel(int argModuleID, String argModuleName, 
                    String argModuleDescription)
  {
    m_nModuleID = argModuleID;
    m_strModuleName = argModuleName;
    m_strModuleDescription = argModuleDescription;
  }
  
  public ModuleModel(int argModuleID, String argModuleName, 
                    String argModuleDescription, int argModuleStatusID)
  {
    m_nModuleID = argModuleID;
    m_strModuleName = argModuleName;
    m_strModuleDescription = argModuleDescription;
    m_nModuleStatusID = argModuleStatusID;
  }

  public ModuleModel(int argModuleID, String argModuleName, 
                    String argModuleDescription, int argModuleStatusID, 
                    String argModuleStatusName)
  {
    m_nModuleID = argModuleID;
    m_strModuleName = argModuleName;
    m_strModuleDescription = argModuleDescription;
    m_nModuleStatusID = argModuleStatusID;
    m_strModuleStatusName = argModuleStatusName;
  }

  public int getModuleID()
  {
    return m_nModuleID;
  }

  public void setModuleID(int argModuleID)
  {
    m_nModuleID = argModuleID;
  }

  public int getModuleStatusID()
  {
    return m_nModuleStatusID;
  }

  public void setModuleStatusID(int argModuleStatusID)
  {
    m_nModuleStatusID = argModuleStatusID;
  }

  public String getModuleDescription()
  {
    return m_strModuleDescription;
  }

  public void setModuleDescription(String argModuleDescription)
  {
    m_strModuleDescription = argModuleDescription;
  }

  public String getModuleName()
  {
    return m_strModuleName;
  }

  public void setModuleName(String argModuleName)
  {
    m_strModuleName = argModuleName;
  }

  public String getModuleStatusName()
  {
    return m_strModuleStatusName;
  }

  public void setModuleStatusName(String argModuleStatusName)
  {
    m_strModuleStatusName = argModuleStatusName;
  }
}
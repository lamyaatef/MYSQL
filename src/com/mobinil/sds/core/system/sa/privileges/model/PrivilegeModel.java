package com.mobinil.sds.core.system.sa.privileges.model;

/**
 * PrivilegeModel Class represents one privilege and all its data.
 * 
 * 1- m_nPrivilegeID
 * 2- m_strPrivilegeName
 * 3- m_strPrivilegeDescription
 * 4- m_strPrivilegeActionName
 * 5- m_nPrivilegeStatusID
 * 6- m_strPrirvilegeStatusName
 * 7- m_nModuleID
 * 8- m_strModuleName
 * 9- m_strPrivilegeTarget
 * 
 * It has six constructors.
 * 
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.Serializable;
import java.sql.ResultSet;

import com.mobinil.sds.core.system.Model;

public class PrivilegeModel extends Model implements Serializable
{
  private int m_nPrivilegeID;
  private String m_strPrivilegeName;
  private String m_strPrivilegeDescription;
  private String m_strPrivilegeActionName;
  private int m_nPrivilegeStatusID;
  private String m_strPrirvilegeStatusName;
  private int m_nModuleID;
  private String m_strModuleName;
  private String m_strPrivilegeTarget;

	 public static final String PRIVILAGE_ID="PRIVILAGE_ID";
	 public static final String PRIVILAGE_NAME="PRIVILAGE_NAME";
	 public static final String MODULE_ID="MODULE_ID";
	 public static final String PRIVILAGE_STATUS_ID="PRIVILAGE_STATUS_ID";
	 public static final String PRIVILAGE_DESC="PRIVILAGE_DESC";
	 public static final String MODULE_NAME="MODULE_NAME";
	 public static final String PRIVILAGE_ACTION_NAME="PRIVILAGE_ACTION_NAME";
	 public static final String PRIVILAGE_TARGET="PRIVILAGE_TARGET";

  public PrivilegeModel(int argPrivilegeID)
  {
    m_nPrivilegeID = argPrivilegeID;
  }

  public PrivilegeModel(int argModuleID, int argPrivilegeID)
  {
    m_nModuleID = argModuleID;
    m_nPrivilegeID = argPrivilegeID;
  }

  public PrivilegeModel(int argModuleID, int argPrivilegeID, 
                        String argPrivilegeName)
  {
    m_nModuleID = argModuleID;
    m_nPrivilegeID = argPrivilegeID;
    m_strPrivilegeName = argPrivilegeName;
  }

  public PrivilegeModel(int argModuleID, int argPrivilegeID, 
                        String argPrivilegeName, String argPrivilegeDescription)
  {
    m_nModuleID = argModuleID;
    m_nPrivilegeID = argPrivilegeID;
    m_strPrivilegeName = argPrivilegeName;
    m_strPrivilegeDescription = argPrivilegeDescription;
  }

  public PrivilegeModel(int argModuleID, int argPrivilegeID, 
                        String argPrivilegeName, String argPrivilegeDescription, 
                        int argPrivilegeStatusID)
  {
    m_nModuleID = argModuleID;
    m_nPrivilegeID = argPrivilegeID;
    m_strPrivilegeName = argPrivilegeName;
    m_strPrivilegeDescription = argPrivilegeDescription;
    m_nPrivilegeStatusID = argPrivilegeStatusID;
  }

  public PrivilegeModel(int argModuleID, int argPrivilegeID, 
                        String argPrivilegeName, String argPrivilegeDescription, 
                        int argPrivilegeStatusID, String argPrivilegeStatusName)
  {
    m_nModuleID = argModuleID;
    m_nPrivilegeID = argPrivilegeID;
    m_strPrivilegeName = argPrivilegeName;
    m_strPrivilegeDescription = argPrivilegeDescription;
    m_nPrivilegeStatusID = argPrivilegeStatusID;
    m_strPrirvilegeStatusName = argPrivilegeStatusName;
  }
  public  void fillInstance(ResultSet res)
  {
	  try
	  {
		  this.setPrivilegeID(res.getInt(PRIVILAGE_ID));
		  this.setPrivilegeName(res.getString(PRIVILAGE_NAME));
		  this.setModuleID(res.getInt(MODULE_ID));
		  this.setPrivilegeStatusID(res.getInt(PRIVILAGE_STATUS_ID));
		  this.setPrivilegeDescription(res.getString(PRIVILAGE_DESC));
		  this.setModuleName(res.getString(MODULE_NAME));
		  this.setPrivilegeActionName(res.getString(PRIVILAGE_ACTION_NAME));
		  this.setPrivilegeTarget(res.getString(PRIVILAGE_TARGET));
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
  }
  public int getPrivilegeID()
  {
    return m_nPrivilegeID;
  }

  public void setPrivilegeID(int argPrivilegeID)
  {
    m_nPrivilegeID = argPrivilegeID;
  }

  public String getPrivilegeName()
  {
    return m_strPrivilegeName;
  }

  public void setPrivilegeName(String argPrivilegeName)
  {
    m_strPrivilegeName = argPrivilegeName;
  }

  public int getModuleID()
  {
    return m_nModuleID;
  }

  public void setModuleID(int argModuleID)
  {
    m_nModuleID = argModuleID;
  }

  public int getPreviligeStatusID()
  {
    return m_nPrivilegeStatusID;
  }

  public void setPrivilegeStatusID(int argPrivilegeStatusID)
  {
    m_nPrivilegeStatusID = argPrivilegeStatusID;
  }

  public String getPrirvilegeStatusName()
  {
    return m_strPrirvilegeStatusName;
  }

  public void setPrirvilegeStatusName(String argPrirvilegeStatusName)
  {
    m_strPrirvilegeStatusName = argPrirvilegeStatusName;
  }

  public String getPrivilegeDescription()
  {
    return m_strPrivilegeDescription;
  }

  public void setPrivilegeDescription(String argPrivilegeDescription)
  {
    m_strPrivilegeDescription = argPrivilegeDescription;
  }

  public String getModuleName()
  {
    return m_strModuleName;
  }

  public void setModuleName(String argModuleName)
  {
    m_strModuleName = argModuleName;
  }

  public String getPrivilegeActionName()
  {
    return m_strPrivilegeActionName;
  }

  public void setPrivilegeActionName(String argPrivilegeActionName)
  {
    m_strPrivilegeActionName = argPrivilegeActionName;
  }

  public String getPrivilegeTarget()
  {
    return m_strPrivilegeTarget;
  }

  public void setPrivilegeTarget(String argPrivilegeTarget)
  {
    m_strPrivilegeTarget = argPrivilegeTarget;
  }


}
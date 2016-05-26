package com.mobinil.sds.core.system.sa.roles.model;

/**
 * RoleModel Class represents one role and all its data.
 *  
 * 1- m_nRoleID
 * 2- m_strRoleName
 * 3- m_strRoleDescription
 * 4- m_nRoleStatusID
 * 5- m_strRoleStatusName
 * 
 * It has five constructors
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.Serializable;
import com.mobinil.sds.core.system.Model;
import java.sql.*;

public class RoleModel extends Model implements Serializable
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private int m_nRoleID;
  private String m_strRoleName;
  private String m_strRoleDescription;
  private int m_nRoleStatusID;
  private String m_strRoleStatusName;

  public RoleModel()
  {
	 
  }
  public  void fillInstance(ResultSet res)
  {
	  try
	  {
		  this.setRoleDescription(res.getString("ROLE_DESC"));
		  this.setRoleID(res.getInt("ROLE_ID"));
		  this.setRoleName(res.getString("ROLE_NAME"));
		  this.setRoleStatusID(res.getInt("ROLE_STATUS_ID"));
		  this.setRoleStatusName(res.getString("ROLE_STATUS_NAME"));
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
  }
  
  public void fillNameOnly(ResultSet res)
  {
	  try
	  {
		  this.setRoleName(res.getString("ROLE_NAME"));		
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }  
  }
  
  public RoleModel(ResultSet res)
  {
	  fillInstance(res);	  
  }
  
  public RoleModel(int argRoleID)
  {
    m_nRoleID = argRoleID;
  }

  public RoleModel(int argRoleID, String argRoleName)
  {
    m_nRoleID = argRoleID;
    m_strRoleName = argRoleName;
  }

  public RoleModel(int argRoleID, String argRoleName, String argRoleDescription)
  {
    m_nRoleID = argRoleID;
    m_strRoleName = argRoleName;
    m_strRoleDescription = argRoleDescription;
  }

  public RoleModel(int argRoleID, String argRoleName, String argRoleDescription, 
                   int argRoleStatusID)
  {
    m_nRoleID = argRoleID;
    m_strRoleName = argRoleName;
    m_strRoleDescription = argRoleDescription;
    m_nRoleStatusID = argRoleStatusID;
  }

  public RoleModel(int argRoleID, String argRoleName, String argRoleDescription, 
                   int argRoleStatusID, String argRoleStatusName)
  {
    m_nRoleID = argRoleID;
    m_strRoleName = argRoleName;
    m_strRoleDescription = argRoleDescription;
    m_nRoleStatusID = argRoleStatusID;
    m_strRoleStatusName = argRoleStatusName;
  }

  public int getRoleID()
  {
    return m_nRoleID;
  }

  public void setRoleID(int argRoleID)
  {
    m_nRoleID = argRoleID;
  }

  public String getRoleName()
  {
    return m_strRoleName;
  }

  public void setRoleName(String argRoleName)
  {
    m_strRoleName = argRoleName;
  }

  public String getRoleDescription()
  {
    return m_strRoleDescription;
  }

  public void setRoleDescription(String argRoleDescription)
  {
    m_strRoleDescription = argRoleDescription;
  }

  public int getRoleStatusID()
  {
    return m_nRoleStatusID;
  }

  public void setRoleStatusID(int argRoleStatusID)
  {
    m_nRoleStatusID = argRoleStatusID;
  }

  public String getRoleStatusName()
  {
    return m_strRoleStatusName;
  }

  public void setRoleStatusName(String argRoleStatusName)
  {
    m_strRoleStatusName = argRoleStatusName;
  }
}
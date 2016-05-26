package com.mobinil.sds.core.system.sa.roles.model;

/**
 * RoleStatusModel Class represents one role status.
 * 
 * 1- m_nRoleStatusID
 * 2- m_strRoleStatusName
 * 3- m_strRoleStatusDescription
 * 
 * It has three constructors
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

public class RoleStatusModel extends Model implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int m_nRoleStatusID;
	private String m_strRoleStatusName;
	private String m_strRoleStatusDescription;

	public RoleStatusModel() {
		
	}

	public RoleStatusModel(int argRoleStatusID)
	{
		m_nRoleStatusID = argRoleStatusID;
	}

	public RoleStatusModel(int argRoleStatusID, String argRoleStatusName)
	{
		m_nRoleStatusID = argRoleStatusID;
		m_strRoleStatusName = argRoleStatusName;
	}

	public RoleStatusModel(int argRoleStatusID, String argRoleStatusName, String argRoleStatusDescription)
	{
		m_nRoleStatusID = argRoleStatusID;
		m_strRoleStatusName = argRoleStatusName;
		m_strRoleStatusDescription = argRoleStatusDescription;
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

	public String getRoleStatusDescription()
	{
		return m_strRoleStatusDescription;
	}

	public void setRoleStatusDescription(String argRoleStatusDescription)
	{
		m_strRoleStatusDescription = argRoleStatusDescription;
	}

	@Override
	public void fillInstance(ResultSet res) 
	{
		try{
			m_nRoleStatusID = res.getInt(1);
			m_strRoleStatusName = res.getString(2);
			m_strRoleStatusDescription = res.getString(3);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
package com.mobinil.sds.core.system.sa.roles.model;

/**
 * RolePrivilegeModel Class holds the data of one role privilege pair.
 * 
 * 1- m_nRoleID
 * 2- m_nPrivilegeID
 * 
 * It has one constructor.
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.Serializable;
import java.sql.*;

import com.mobinil.sds.core.system.Model;

public class RolePrivilegeModel extends Model implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private int m_nRoleID;
	private int m_nPrivilegeID;

	public RolePrivilegeModel() {
		
	}

	public RolePrivilegeModel(int argRoleID, int argPrivilegeID)
	{
		m_nRoleID = argRoleID;
		m_nPrivilegeID = argPrivilegeID;
	}

	public RolePrivilegeModel(ResultSet res)
	{
		fillInstance(res);
	}
	
	public int getRoleID()
	{
		return m_nRoleID;
	}

	public void setRoleID(int argRoleID)
	{
		m_nRoleID = argRoleID;
	}

	public int getPrivilegeID()
	{
		return m_nPrivilegeID;
	}

	public void setPrivilegeID(int argPrivilegeID)
	{
		m_nPrivilegeID = argPrivilegeID;
	}

	
	public void fillInstance(ResultSet res) {
		try
		{
		m_nRoleID = res.getInt(1); 
		m_nPrivilegeID = res.getInt(2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
}
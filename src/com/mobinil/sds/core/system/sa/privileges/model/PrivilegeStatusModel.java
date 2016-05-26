package com.mobinil.sds.core.system.sa.privileges.model;

/**
 * PrivilegeStatusModel Class represents one privilege status.
 * 
 * 1- m_nPrivilegeStatusID
 * 2- m_strPrivilegeStatusName
 * 3- m_strPrivilegeStatusDescription
 * 
 * It has three constructors
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.io.Serializable;
import java.sql.ResultSet;

import com.mobinil.sds.core.system.Model;

public class PrivilegeStatusModel extends Model implements Serializable 
{
  private int m_nPrivilegeStatusID;
  private String m_strPrivilegeStatusName;
  private String m_strPrivilegeStatusDescription;

  public static final String PRIVILAGE_STATUS_ID="PRIVILAGE_STATUS_ID";
  public static final String PRIVILAGE_STATUS_NAME="PRIVILAGE_STATUS_NAME";
  public static final String PRIVILAGE_STATUS_DESC="PRIVILAGE_STATUS_DESC";
  public PrivilegeStatusModel(int argPrivilegeStatusID)
  {
    m_nPrivilegeStatusID = argPrivilegeStatusID;
  }

  public PrivilegeStatusModel(int argPrivilegeStatusID, 
                              String argPrivilegeStatusName)
  {
    m_nPrivilegeStatusID = argPrivilegeStatusID;
    m_strPrivilegeStatusName = argPrivilegeStatusName;
  }

  public PrivilegeStatusModel(int argPrivilegeStatusID, 
                              String argPrivilegeStatusName, 
                              String argPrivilegeStatusDescription)
  {
    m_nPrivilegeStatusID = argPrivilegeStatusID;
    m_strPrivilegeStatusName = argPrivilegeStatusName;
    m_strPrivilegeStatusDescription = argPrivilegeStatusDescription;
  }
  
  public  void fillInstance(ResultSet res)
  {
	  try
	  {
		  this.setPrivilegeStatusID(res.getInt(PRIVILAGE_STATUS_ID));
		  this.setPrivilegeStatusName(res.getString(PRIVILAGE_STATUS_NAME));
		  this.setPrivilegeStatusDescription(res.getString(PRIVILAGE_STATUS_DESC));
		
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
  }

  public int getPrivilegeStatusID()
  {
    return m_nPrivilegeStatusID;
  }

  public void setPrivilegeStatusID(int argPrivilegeStatusID)
  {
    m_nPrivilegeStatusID = argPrivilegeStatusID;
  }

  public String getPrivilegeStatusName()
  {
    return m_strPrivilegeStatusName;
  }

  public void setPrivilegeStatusName(String argPrivilegeStatusName)
  {
    m_strPrivilegeStatusName = argPrivilegeStatusName;
  }

  public String getPrivilegeStatusDescription()
  {
    return m_strPrivilegeStatusDescription;
  }

  public void setPrivilegeStatusDescription(String argPrivilegeStatusDescription)
  {
    m_strPrivilegeStatusDescription = argPrivilegeStatusDescription;
  }
}
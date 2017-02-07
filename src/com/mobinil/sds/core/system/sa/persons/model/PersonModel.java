package com.mobinil.sds.core.system.sa.persons.model;

/**
 * PersonModel Class represents one Person and all its data.
 * 
 * 1- m_nPersonID
 * 2- m_strPersonFullName
 * 3- m_strPersonAddress
 * 4- m_nPersonStatusID
 * 5- m_strPersonStatusName
 * 6- m_nPersonTypeID
 * 7- m_strPersonTypeName
 * 8- m_nPersonTypeStatusID
 * 9- m_strPersonTypeStatusName
 * 10- m_strPersonEMail
 * 
 * It has five constructors
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import com.mobinil.sds.core.system.Model;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonModel extends Model implements Serializable
{
  private int m_nPersonID;
  private String m_strPersonFullName;
  private String m_strPersonAddress;
  private int m_nPersonStatusID;
  private String m_strPersonStatusName;
  private int m_nPersonTypeID;
  private String m_strPersonTypeName;
  private int m_nPersonTypeStatusID;
  private String m_strPersonTypeStatusName;
  private String m_strPersonEMail;
  private String m_strUserLevelTypeName;

  public PersonModel(){
      super();
  }
  public PersonModel(int argPersonID)
  {
    m_nPersonID = argPersonID;
  }

  public PersonModel(int argPersonID, String argPersonFullName, 
                     String argPersonAddress, String argPersonEMail)
  {
    m_nPersonID = argPersonID;
    m_strPersonFullName = argPersonFullName;
    m_strPersonAddress = argPersonAddress;
    m_strPersonEMail = argPersonEMail;
  }

  public PersonModel(int argPersonID, String argPersonFullName, 
                     String argPersonAddress, int argPersonStatusID, 
                     int argPersonTypeID, int argPersonTypeStatusID, 
                     String argPersonEMail)
  {
    m_nPersonID = argPersonID;
    m_strPersonFullName = argPersonFullName;
    m_strPersonAddress = argPersonAddress;
    m_nPersonStatusID = argPersonStatusID;
    m_nPersonTypeID = argPersonTypeID;
    m_nPersonTypeStatusID = argPersonTypeStatusID;
    m_strPersonEMail = argPersonEMail;
  }

  public PersonModel(String argPersonFullName, String argPersonAddress,
                    int argPersonStatusID, int argPersonTypeID,
                    int argPersonTypeStatusID, String argPersonEMail)
  {
    m_strPersonFullName = argPersonFullName;
    m_strPersonAddress = argPersonAddress;
    m_nPersonStatusID = argPersonStatusID;
    m_nPersonTypeID = argPersonTypeID;
    m_nPersonTypeStatusID = argPersonTypeStatusID;
    m_strPersonEMail = argPersonEMail;
  }

  public PersonModel(int argPersonID, String argPersonFullName, 
                    String argPersonAddress, int argPersonStatusID, 
                    String argPersonStatusName, int argPersonTypeID,
                    String argPersonTypeName, int argPersonTypeStatusID, 
                    String argPersonTypeStatusName, String argPersonEMail)
  {
    m_nPersonID = argPersonID;
    m_strPersonFullName = argPersonFullName;
    m_strPersonAddress = argPersonAddress;
    m_nPersonStatusID = argPersonStatusID;
    m_strPersonStatusName = argPersonStatusName;
    m_nPersonTypeID = argPersonTypeID;
    m_strPersonTypeName = argPersonTypeName;
    m_nPersonTypeStatusID = argPersonTypeStatusID;
    m_strPersonTypeStatusName = argPersonTypeStatusName;
    m_strPersonEMail = argPersonEMail;
  }

  public int getPersonID()
  {
    return m_nPersonID;
  }

  public void setPersonID(int argPersonID)
  {
    m_nPersonID = argPersonID;
  }

  public String getPersonFullName()
  {
    return m_strPersonFullName;
  }

  public void setPersonFullName(String argPersonFullName)
  {
    m_strPersonFullName = argPersonFullName;
  }

  public String getPersonAddress()
  {
    return m_strPersonAddress;
  }

  public void setPersonAddress(String argPersonAddress)
  {
    m_strPersonAddress = argPersonAddress;
  }

  public int getPersonStatusID()
  {
    return m_nPersonStatusID;
  }

  public void setPersonStatusID(int argPersonStatusID)
  {
    m_nPersonStatusID = argPersonStatusID;
  }

  public String getPersonStatusName()
  {
    return m_strPersonStatusName;
  }

  public void setPersonStatusName(String argPersonStatusName)
  {
    m_strPersonStatusName = argPersonStatusName;
  }

  public int getPersonTypeID()
  {
    return m_nPersonTypeID;
  }

  public void setPersonTypeID(int argPersonTypeID)
  {
    m_nPersonTypeID = argPersonTypeID;
  }

  public String getPersonTypeName()
  {
    return m_strPersonTypeName;
  }

  public void setPersonTypeName(String argPersonTypeName)
  {
    m_strPersonTypeName = argPersonTypeName;
  }

  public int getPersonTypeStatusID()
  {
    return m_nPersonTypeStatusID;
  }

  public void setPersonTypeStatusID(int argPersonTypeStatusID)
  {
    m_nPersonTypeStatusID = argPersonTypeStatusID;
  }

  public String getPersonTypeStatusName()
  {
    return m_strPersonTypeStatusName;
  }

  public void setPersonTypeStatusName(String argPersonTypeStatusName)
  {
    m_strPersonTypeStatusName = argPersonTypeStatusName;
  }

  public String getPersonEMail()
  {
    return m_strPersonEMail;
  }

  public void setPersonEMail(String argPersonEMail)
  {
    m_strPersonEMail = argPersonEMail;
  }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setPersonID(res.getInt("PERSON_ID"));
            this.setPersonFullName(res.getString("PERSON_FULL_NAME"));
            this.setPersonAddress(res.getString("PERSON_ADDRESS"));
            this.setPersonEMail(res.getString("PERSON_EMAIL"));
            this.setM_strUserLevelTypeName(res.getString("user_level_type_name"));
        } catch (SQLException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public String getM_strUserLevelTypeName() {
        return m_strUserLevelTypeName;
    }

    /**
     * @param m_strUserLevelTypeName the m_strUserLevelTypeName to set
     */
    public void setM_strUserLevelTypeName(String m_strUserLevelTypeName) {
        this.m_strUserLevelTypeName = m_strUserLevelTypeName;
    }

}
package com.mobinil.sds.core.system.dcm.owner.model;

import java.sql.Date;
import java.util.*;

public class OwnerModel 
{
  String ownerName;
  Date   ownerBirthDate;
  String ownerIDTypeName;
  int ownerIDTypeID;
  String ownerIDNumber;
  Vector ownerPhones = new Vector();
  public void setOwnerName(String name)
  {
    ownerName = name;
  }
  public String getOwnerName()
  {
    return ownerName;
  }
  public void setOwnerBirthDate(Date birthDate)
  {
    ownerBirthDate = birthDate;
  }
  public Date getOwnerBirthDate()
  {
    return ownerBirthDate;
  }
  public void setOwnerIDTypeName(String IDTypeName)
  {
    ownerIDTypeName = IDTypeName;
  }
  public String getOwnerIDTypeName()
  {
    return ownerIDTypeName;
  }
  public void setOwnerIDTypeID(int IDTypeID)
  {
    ownerIDTypeID = IDTypeID;
  }
  public int getOwnerIDTypeID()
  {
    return ownerIDTypeID;
  }
  public void setOwnerIDNumber(String IDNumber)
  {
    ownerIDNumber = IDNumber;
  }
  public String getOwnerIDNumber()
  {
    return ownerIDNumber;
  }
  public void setOwnerPhones(Vector phones)
  {
    ownerPhones = phones;
  }
  public Vector getOwnerPhoones()
  {
    return ownerPhones;
  }
  public OwnerModel()
  {
  }
}
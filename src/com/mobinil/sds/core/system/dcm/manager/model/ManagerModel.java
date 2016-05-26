package com.mobinil.sds.core.system.dcm.manager.model;

import java.sql.Date;
import java.util.*;

public class ManagerModel 
{
  String managerName;
  Date   managerBirthDate;
  String managerIDTypeName;
  int managerIDTypeID;
  String managerIDNumber;
  Vector managerPhones = new Vector();
  public void setManagerName(String name)
  {
    managerName = name;
  }
  public String getManagerName()
  {
    return managerName;
  }
  public void setManagerBirthDate(Date birthDate)
  {
    managerBirthDate = birthDate;
  }
  public Date getManagerBirthDate()
  {
    return managerBirthDate;
  }
  public void setManagerIDTypeName(String IDTypeName)
  {
    managerIDTypeName = IDTypeName;
  }
  public String getManagerIDTypeName()
  {
    return managerIDTypeName;
  }
  public void setManagerIDTypeID(int IDTypeID)
  {
    managerIDTypeID = IDTypeID;
  }
  public int getManagerIDTypeID()
  {
    return managerIDTypeID;
  }
  public void setManagerIDNumber(String IDNumber)
  {
    managerIDNumber = IDNumber;
  }
  public String getManagerIDNumber()
  {
    return managerIDNumber;
  }
  public void setManagerPhones(Vector phones)
  {
    managerPhones = phones;
  }
  public Vector getManagerPhoones()
  {
    return managerPhones;
  }
  public ManagerModel()
  {
  }
}
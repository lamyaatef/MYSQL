package com.mobinil.sds.core.system.dcm.manager.dao;

import com.mobinil.sds.core.system.dcm.manager.model.*;
import java.sql.*;
import java.util.Vector;
import com.mobinil.sds.core.utilities.Utility;
public class ManagerDAO 
{
  public static ManagerModel getManagerDetailsByPOSID(Connection con , int posID)
  {
    ManagerModel managerModel = new ManagerModel();
    try
      {    
        Statement stmt = con.createStatement();
        String sqlString = "SELECT * FROM VW_DCM_POS_MANAGER WHERE POS_DETAIL_ID = "+ posID ;
        ResultSet rs =  stmt.executeQuery(sqlString);
        if (rs.next())
        {
          String managerName   = rs.getString("POS_MANAGER_NAME");
          int managerIDTypeID  = rs.getInt("POS_MANAGER_ID_TYPE_ID");
          String managerIDTypeName   = rs.getString("ID_TYPE_NAME");
          Date managerBirthDate   = rs.getDate("POS_MANAGER_BIRTHDATE");
          String managerIDNumber   = rs.getString("POS_MANAGER_ID_NUMBER");
          int managerID   = rs.getInt("POS_MANAGER_ID");

          managerModel.setManagerBirthDate(managerBirthDate);
          managerModel.setManagerIDNumber(managerIDNumber);
          managerModel.setManagerIDTypeID(managerIDTypeID);
          managerModel.setManagerIDTypeName(managerIDTypeName);
          managerModel.setManagerName(managerName);
          managerModel.setManagerPhones(getManagerPhones(con , posID));

        }
        rs.close();
        stmt.close();
      }catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return managerModel;
  }
  public static Vector getManagerPhones(Connection con ,int posID)
  {
  Utility.logger.debug("Manager DAO" + posID);
    Vector managerPhones = new Vector();
            int managerID = 0;
    try
      {    
        Statement stmt = con.createStatement();
        String sqlString = "SELECT POS_MANAGER_PHONE_NUMBER FROM DCM_POS_MANAGER_PHONE WHERE POS_MANAGER_ID IN ("+
                      "SELECT POS_MANAGER_ID FROM VW_DCM_POS_DETAIL WHERE POS_DETAIL_ID = " +  posID + ")";

        Utility.logger.debug(sqlString);        
        ResultSet rs =  stmt.executeQuery(sqlString);
        while(rs.next())
        {
            Utility.logger.debug("MANAGER PHONES"+rs.getString("POS_MANAGER_PHONE_NUMBER"));
            String managerPhoneNumber = rs.getString("POS_MANAGER_PHONE_NUMBER");
            managerPhones.add(managerPhoneNumber);
        }
        rs.close();
        stmt.close();
      }catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return managerPhones;  
    
  }
  
  public ManagerDAO()
  {
  }
}
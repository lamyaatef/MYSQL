package com.mobinil.sds.core.system.dcm.owner.dao;

import com.mobinil.sds.core.system.dcm.owner.model.*;
import java.sql.*;
import java.util.Vector;
import com.mobinil.sds.core.utilities.Utility;
public class OwnerDAO 
{
  public static OwnerModel getOwnerDetailsByPOSID(Connection con , int posID)
  {
    OwnerModel ownerModel = new OwnerModel();
    try
      {    
        Statement stmt = con.createStatement();
        String sqlString = "SELECT * FROM VW_DCM_POS_OWNER WHERE POS_DETAIL_ID = "+ posID ;
        ResultSet rs =  stmt.executeQuery(sqlString);
        if (rs.next())
        {
          String ownerName   = rs.getString("POS_OWNER_NAME");
          int ownerIDTypeID  = rs.getInt("POS_OWNER_ID_TYPE_ID");
          String ownerIDTypeName   = rs.getString("ID_TYPE_NAME");
          Date ownerBirthDate   = rs.getDate("POS_OWNER_BIRTHDATE");
          String ownerIDNumber   = rs.getString("POS_OWNER_ID_NUMBER");
          int ownerID   = rs.getInt("POS_OWNER_ID");

          ownerModel.setOwnerBirthDate(ownerBirthDate);
          ownerModel.setOwnerIDNumber(ownerIDNumber);
          ownerModel.setOwnerIDTypeID(ownerIDTypeID);
          ownerModel.setOwnerIDTypeName(ownerIDTypeName);
          ownerModel.setOwnerName(ownerName);
          ownerModel.setOwnerPhones(getOwnerPhones(con , posID));

        }
        rs.close();
        stmt.close();
      }catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return ownerModel;
  }
  public static Vector getOwnerPhones(Connection con , int posID)
  {
    Utility.logger.debug("Owner DAO");
    Vector ownerPhones = new Vector();
    try
      {    
        Statement stmt = con.createStatement();
        int ownerID = 0;
        String sqlString = "SELECT POS_OWNER_PHONE_NUMBER FROM DCM_POS_OWNER_PHONE WHERE POS_OWNER_ID IN (SELECT POS_OWNER_ID"+
                    " FROM VW_DCM_POS_DETAIL WHERE POS_DETAIL_ID = " + posID +")";
        Utility.logger.debug(sqlString);        
        ResultSet rs =  stmt.executeQuery(sqlString);
        while(rs.next())
        {
            Utility.logger.debug("OWNER PHONE : "+rs.getString("POS_OWNER_PHONE_NUMBER"));
            String ownerPhoneNumber = rs.getString("POS_OWNER_PHONE_NUMBER");
            ownerPhones.add(ownerPhoneNumber);
        }
        rs.close();
        stmt.close();
      }catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return ownerPhones;  
    
  }
  public OwnerDAO()
  {
  }
}
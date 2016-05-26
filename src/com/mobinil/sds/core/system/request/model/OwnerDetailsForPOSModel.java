/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ahmed Adel
 */
public class OwnerDetailsForPOSModel extends Model{
    private int ownerId;
    private String ownerName;
    private String ownerBirthDate;
    private int ownerIDTypeID;
    private String ownerIDNumber;

    /**
     * @return the ownerName
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * @param ownerName the ownerName to set
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @return the ownerBirthDate
     */
    public String getOwnerBirthDate() {
        return ownerBirthDate;
    }

    /**
     * @param ownerBirthDate the ownerBirthDate to set
     */
    public void setOwnerBirthDate(String ownerBirthDate) {
        this.ownerBirthDate = ownerBirthDate;
    }

    /**
     * @return the ownerIDTypeID
     */
    public int getOwnerIDTypeID() {
        return ownerIDTypeID;
    }

    /**
     * @param ownerIDTypeID the ownerIDTypeID to set
     */
    public void setOwnerIDTypeID(int ownerIDTypeID) {
        this.ownerIDTypeID = ownerIDTypeID;
    }

    /**
     * @return the ownerIDNumber
     */
    public String getOwnerIDNumber() {
        return ownerIDNumber;
    }

    /**
     * @param ownerIDNumber the ownerIDNumber to set
     */
    public void setOwnerIDNumber(String ownerIDNumber) {
        this.ownerIDNumber = ownerIDNumber;
    }

    /**
     * @return the ownerId
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(int ownerId) {
        this.setOwnerId(ownerId);
    }

    @Override
    public void fillInstance(ResultSet res) {
       try
		{
			if( GenericDAO.checkColumnName("POS_OWNER_ID", res))
                        this.setOwnerId(res.getInt("POS_OWNER_ID"));
			if( GenericDAO.checkColumnName("POS_OWNER_NAME", res))
                        this.setOwnerName(res.getString("POS_OWNER_NAME"));
			if( GenericDAO.checkColumnName("POS_OWNER_BIRTHDATE", res))
                        this.setOwnerBirthDate(res.getDate("POS_OWNER_BIRTHDATE").toString());
                        if( GenericDAO.checkColumnName("POS_OWNER_ID_TYPE_ID", res))
                        this.setOwnerIDTypeID(res.getInt("POS_OWNER_ID_TYPE_ID"));
                         if( GenericDAO.checkColumnName("POS_OWNER_ID_NUMBER", res))
                        this.setOwnerIDNumber(res.getString("POS_OWNER_ID_NUMBER"));
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

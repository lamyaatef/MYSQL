/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AHMED SAFWAT
 */
public class DCMUserLevelTypeModel extends Model{
private int userLevelTypeId;
private String userLevelTypeName;

    /**
     * @return the userLevelTypeId
     */
    public int getUserLevelTypeId() {
        return userLevelTypeId;
    }

    /**
     * @param userLevelTypeId the userLevelTypeId to set
     */
    public void setUserLevelTypeId(int userLevelTypeId) {
        this.userLevelTypeId = userLevelTypeId;
    }

    /**
     * @return the userLevelTypeName
     */
    public String getUserLevelTypeName() {
        return userLevelTypeName;
    }

    /**
     * @param userLevelTypeName the userLevelTypeName to set
     */
    public void setUserLevelTypeName(String userLevelTypeName) {
        this.userLevelTypeName = userLevelTypeName;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setUserLevelTypeId(res.getInt("USER_LEVEL_TYPE_ID"));
            this.setUserLevelTypeName(res.getString("USER_LEVEL_TYPE_NAME"));
        } catch (SQLException ex) {
            Logger.getLogger(DCMUserLevelTypeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

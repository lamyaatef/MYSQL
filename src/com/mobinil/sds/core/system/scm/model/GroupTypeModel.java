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
public class GroupTypeModel extends Model{
    private String groupTypeId;
    private String groupTypeName;
    private String groupTypeDesc;

    /**
     * @return the groupTypeId
     */
    public String getGroupTypeId() {
        return groupTypeId;
    }

    /**
     * @param groupTypeId the groupTypeId to set
     */
    public void setGroupTypeId(String groupTypeId) {
        this.groupTypeId = groupTypeId;
    }

    /**
     * @return the groupTypeName
     */
    public String getGroupTypeName() {
        return groupTypeName;
    }

    /**
     * @param groupTypeName the groupTypeName to set
     */
    public void setGroupTypeName(String groupTypeName) {
        this.groupTypeName = groupTypeName;
    }

    /**
     * @return the groupTypeDesc
     */
    public String getGroupTypeDesc() {
        return groupTypeDesc;
    }

    /**
     * @param groupTypeDesc the groupTypeDesc to set
     */
    public void setGroupTypeDesc(String groupTypeDesc) {
        this.groupTypeDesc = groupTypeDesc;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setGroupTypeId(res.getString("GROUP_TYPE_ID"));
            this.setGroupTypeName(res.getString("GROUP_TYPE_NAME"));
            this.setGroupTypeDesc(res.getString("GROUP_TYPE_DESC"));
        } catch (SQLException ex) {
            Logger.getLogger(GroupTypeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}

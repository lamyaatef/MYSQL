/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AHMED SAFWAT
 */
public class POSGroupModel extends Model{

private int groupId;
private String groupName;
private String description;
private Date creationDate;
private String createdBy;
private String groupTypeId;
private String groupTypeName;
private int noOfPOSs; // not presist in the database

    /**
     * @return the groupId
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the noOfPOSs
     */
    public int getNoOfPOSs() {
        return noOfPOSs;
    }

    /**
     * @param noOfPOSs the noOfPOSs to set
     */
    public void setNoOfPOSs(int noOfPOSs) {
        this.noOfPOSs = noOfPOSs;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setGroupId(res.getInt("GROUP_ID"));
            this.setGroupName(res.getString("GROUP_NAME"));
            this.setDescription(res.getString("DESCRIPTION"));
            this.setCreationDate(res.getDate("CREATED_IN"));
            this.setCreatedBy(res.getString("CREATED_BY"));
            this.setGroupTypeId(res.getString("GROUP_TYPE_ID"));
            this.setGroupTypeName(res.getString("GROUP_TYPE_NAME"));
        } catch (SQLException ex) {
            Logger.getLogger(POSGroupModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillPOSGroup(ResultSet res) {
        try {
            this.setGroupId(res.getInt("GROUP_ID"));
            this.setGroupName(res.getString("GROUP_NAME"));
            this.setDescription(res.getString("DESCRIPTION"));
            this.setCreationDate(res.getDate("CREATED_IN"));
            this.setCreatedBy(res.getString("CREATED_BY"));
            this.setGroupTypeId(res.getString("GROUP_TYPE_ID"));
        } catch (SQLException ex) {
            Logger.getLogger(POSGroupModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public void fillForRepGroupAssign(ResultSet res) {
        try {
            this.setGroupId(res.getInt("GROUP_ID"));
            this.setGroupName(res.getString("GROUP_NAME"));

        } catch (SQLException ex) {
            Logger.getLogger(POSGroupModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

}

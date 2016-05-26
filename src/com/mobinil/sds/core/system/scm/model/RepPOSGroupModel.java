/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AHMED SAFWAT
 */
public class RepPOSGroupModel extends Model{

    private String groupId;
    private String groupName;
    private String dcmUserId; // Rep Id
    private Date createdIn;
    private String createdBy;

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the dcmUserId
     */
    public String getDcmUserId() {
        return dcmUserId;
    }

    /**
     * @param dcmUserId the dcmUserId to set
     */
    public void setDcmUserId(String dcmUserId) {
        this.dcmUserId = dcmUserId;
    }

    /**
     * @return the createdIn
     */
    public Date getCreatedIn() {
        return createdIn;
    }

    /**
     * @param createdIn the createdIn to set
     */
    public void setCreatedIn(Date createdIn) {
        this.createdIn = createdIn;
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

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.groupId = res.getString("GROUP_ID");
            this.groupName=res.getString("GROUP_NAME");
//            this.dcmUserId=res.getString("DCM_USER_ID");
//            this.createdIn=res.getDate("CREATED_IN");
//            this.createdBy=res.getString("CREATED_BY");
        } catch (SQLException ex) {
            Logger.getLogger(RepPOSGroupModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



}

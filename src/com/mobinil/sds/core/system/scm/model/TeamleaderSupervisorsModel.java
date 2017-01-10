/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.scm.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mobinil.sds.core.system.Model;

/**
 *
 * @author sand
 */
public class TeamleaderSupervisorsModel extends Model{
        private String supId;
    private String supName;
    private String teamleaderId;
    private String teamleaderName;
    private Date createdIn;
    private String createdBy;

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

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setTeamleaderId(res.getString("TEAMLEAD_ID"));
            this.setSupName(res.getString("TEAMLEADER_NAME"));
            this.setSupId(res.getString("SUPERVISOR_ID"));
            this.setSupName(res.getString("SUPERVISOR_NAME"));
             this.setCreatedIn(res.getDate("creation_timestamp"));
            this.setCreatedBy(res.getString("user_id"));
        } catch (SQLException ex) {
            Logger.getLogger(RepSupervisorModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /**
     * @param teamleaderId the teamleaderId to set
     */
    public void setTeamleaderId(String teamleaderId) {
        this.teamleaderId = teamleaderId;
    }

    /**
     * @return the supId
     */
    public String getSupId() {
        return supId;
    }

    /**
     * @param supId the supId to set
     */
    public void setSupId(String supId) {
        this.supId = supId;
    }

    /**
     * @return the supName
     */
    public String getSupName() {
        return supName;
    }

    /**
     * @param supName the supName to set
     */
    public void setSupName(String supName) {
        this.supName = supName;
    }

    /**
     * @return the teamleaderId
     */
    public String getTeamleaderId() {
        return teamleaderId;
    }

    /**
     * @return the teamleaderName
     */
    public String getTeamleaderName() {
        return teamleaderName;
    }

    /**
     * @param teamleaderName the teamleaderName to set
     */
    public void setTeamleaderName(String teamleaderName) {
        this.teamleaderName = teamleaderName;
    }


    
}

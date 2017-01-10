/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author sand
 */
public class SupervisorTeamleadersModel extends Model{
    
    
    
    
    private String teamleadId;
    private String teamleadName;
    private String supId;
    private String supName;
    private Date createdIn;
    private String createdBy;

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
            this.setSupId(res.getString("SUP_ID"));
            this.setSupName(res.getString("SUPERVISOR_NAME"));
            this.setTeamleadId(res.getString("TEAMLEADER_ID"));
            this.setTeamleadName(res.getString("TEAMLEADER_NAME"));
             this.setCreatedIn(res.getDate("creation_timestamp"));
            this.setCreatedBy(res.getString("user_id"));
        } catch (SQLException ex) {
            Logger.getLogger(RepSupervisorModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the teamleadId
     */
    public String getTeamleadId() {
        return teamleadId;
    }

    /**
     * @param teamleadId the teamleadId to set
     */
    public void setTeamleadId(String teamleadId) {
        this.teamleadId = teamleadId;
    }

    /**
     * @return the teamleadName
     */
    public String getTeamleadName() {
        return teamleadName;
    }

    /**
     * @param teamleadName the teamleadName to set
     */
    public void setTeamleadName(String teamleadName) {
        this.teamleadName = teamleadName;
    }
    
}

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
public class TeamleaderRepsModel extends Model{
    private String repId;
    private String repName;
    private String teamleaderId;
    
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
            //this.setSupName(res.getString("SUP_NAME"));
            this.setRepId(res.getString("REP_ID"));
            this.setRepName(res.getString("REP_NAME"));
             this.setCreatedIn(res.getDate("CREATED_IN"));
            this.setCreatedBy(res.getString("CREATED_BY"));
        } catch (SQLException ex) {
            Logger.getLogger(RepSupervisorModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the repId
     */
    public String getRepId() {
        return repId;
    }

    /**
     * @param repId the repId to set
     */
    public void setRepId(String repId) {
        this.repId = repId;
    }

    /**
     * @return the repName
     */
    public String getRepName() {
        return repName;
    }

    /**
     * @param repName the repName to set
     */
    public void setRepName(String repName) {
        this.repName = repName;
    }

    /**
     * @return the teamleaderId
     */
    public String getTeamleaderId() {
        return teamleaderId;
    }

    /**
     * @param teamleaderId the teamleaderId to set
     */
    public void setTeamleaderId(String teamleaderId) {
        this.teamleaderId = teamleaderId;
    }


    
}

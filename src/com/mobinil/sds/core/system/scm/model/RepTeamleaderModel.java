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
public class RepTeamleaderModel extends Model{
    private String repId;
    private String repName;
    private String teamleadId;
    private String teamleadName;
    private Date createdIn;
    private String createdBy;
    
      @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setTeamleadId(res.getString("TEAMLEAD_ID"));
            this.setTeamleadName(res.getString("TEAMLEAD_NAME"));//still
            this.setRepId(res.getString("REP_ID"));
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
    
}

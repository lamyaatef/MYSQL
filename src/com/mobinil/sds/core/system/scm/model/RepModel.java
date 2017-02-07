/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.scm.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author sand
 */
public class RepModel {
    private String repId ="";
    private String repName="";
    private String repEmail="";
    private String repMobile="";
    private String supervisorId ="";
    
    private String teamleaderId="";
    
    
    public RepModel(){}
    public RepModel(ResultSet res, boolean hasRegion) throws SQLException{
    
        repId = res.getString("salesrep_id");
        repName = res.getString("salesrep_name");
        repMobile = res.getString("mobile");
        //repAddress = res.getString("rep_address");
        repEmail = res.getString("email");
        supervisorId = res.getString("sup_id");
        teamleaderId = res.getString("teamlead_id");
        
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
     * @return the repEmail
     */
    public String getRepEmail() {
        return repEmail;
    }

    /**
     * @param repEmail the repEmail to set
     */
    public void setRepEmail(String repEmail) {
        this.repEmail = repEmail;
    }

    /**
     * @return the repMobile
     */
    public String getRepMobile() {
        return repMobile;
    }

    /**
     * @param repMobile the repMobile to set
     */
    public void setRepMobile(String repMobile) {
        this.repMobile = repMobile;
    }

    

    /**
     * @return the supervisorId
     */
    public String getSupervisorId() {
        return supervisorId;
    }

    /**
     * @param supervisorId the supervisorId to set
     */
    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
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

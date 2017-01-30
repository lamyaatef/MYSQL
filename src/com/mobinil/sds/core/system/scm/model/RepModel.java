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
    private String repAddress="";
    private String supervisorId ="";
    private String supervisorAddress="";
    private String supervisorEmail="";
    private String supervisorName="";
    private String supervisorMobile="";
    private String teamleaderId="";
    private String teamleaderName="";
    private String teamleaderAddress="";
    private String teamleaderEmail="";
    private String teamleaderMobile="";
    private String regionName="";
    
    public RepModel(){}
    public RepModel(ResultSet res, boolean hasRegion) throws SQLException{
    
        repId = res.getString("salesrep_id");
        repName = res.getString("salesrep_name");
        repMobile = res.getString("mobile");
        //repAddress = res.getString("rep_address");
        repEmail = res.getString("email");
        teamleaderName = res.getString("teamleader_name");
        teamleaderEmail = res.getString("teamleader_email");
        teamleaderMobile = res.getString("teamleader_mobile");
        if (teamleaderMobile==null)
                teamleaderMobile="null";
        supervisorEmail = res.getString("supervisor_email");
        supervisorMobile = res.getString("supervisor_mobile");
        if (supervisorMobile==null)
                supervisorMobile="null";
        supervisorName = res.getString("supervisor_name");
        
        if(hasRegion)
        {
            regionName = res.getString("region_name");
            /*supervisorId = res.getString("sup_id");
            supervisorAddress = res.getString("supervisor_address");
            supervisorEmail = res.getString("supervisor_email");
            supervisorMobile = res.getString("supervisor_mobile");
            supervisorName = res.getString("supervisor_name");
            teamleaderId = res.getString("teamlead_id");
            teamleaderAddress = res.getString("teamleader_address");
            teamleaderEmail = res.getString("teamleader_email");
            teamleaderMobile = res.getString("teamleader_mobile");
            teamleaderName = res.getString("teamleader_name");*/
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
     * @return the repAddress
     */
    public String getRepAddress() {
        return repAddress;
    }

    /**
     * @param repAddress the repAddress to set
     */
    public void setRepAddress(String repAddress) {
        this.repAddress = repAddress;
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
     * @return the supervisorAddress
     */
    public String getSupervisorAddress() {
        return supervisorAddress;
    }

    /**
     * @param supervisorAddress the supervisorAddress to set
     */
    public void setSupervisorAddress(String supervisorAddress) {
        this.supervisorAddress = supervisorAddress;
    }

    /**
     * @return the supervisorEmail
     */
    public String getSupervisorEmail() {
        return supervisorEmail;
    }

    /**
     * @param supervisorEmail the supervisorEmail to set
     */
    public void setSupervisorEmail(String supervisorEmail) {
        this.supervisorEmail = supervisorEmail;
    }

    /**
     * @return the supervisorName
     */
    public String getSupervisorName() {
        return supervisorName;
    }

    /**
     * @param supervisorName the supervisorName to set
     */
    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    /**
     * @return the supervisorMobile
     */
    public String getSupervisorMobile() {
        return supervisorMobile;
    }

    /**
     * @param supervisorMobile the supervisorMobile to set
     */
    public void setSupervisorMobile(String supervisorMobile) {
        this.supervisorMobile = supervisorMobile;
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

    /**
     * @return the teamleaderAddress
     */
    public String getTeamleaderAddress() {
        return teamleaderAddress;
    }

    /**
     * @param teamleaderAddress the teamleaderAddress to set
     */
    public void setTeamleaderAddress(String teamleaderAddress) {
        this.teamleaderAddress = teamleaderAddress;
    }

    /**
     * @return the teamleaderEmail
     */
    public String getTeamleaderEmail() {
        return teamleaderEmail;
    }

    /**
     * @param teamleaderEmail the teamleaderEmail to set
     */
    public void setTeamleaderEmail(String teamleaderEmail) {
        this.teamleaderEmail = teamleaderEmail;
    }

    /**
     * @return the teamleaderMobile
     */
    public String getTeamleaderMobile() {
        return teamleaderMobile;
    }

    /**
     * @param teamleaderMobile the teamleaderMobile to set
     */
    public void setTeamleaderMobile(String teamleaderMobile) {
        this.teamleaderMobile = teamleaderMobile;
    }

    /**
     * @return the regionName
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * @param regionName the regionName to set
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}

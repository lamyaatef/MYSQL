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
public class TeamleaderModel {
    private String teamleaderId="";
    private String teamleaderName="";
    private String teamleaderAddress="";
    private String teamleaderEmail="";
    private String teamleaderMobile="";
    private String supId="";
    private String regionName="";
    
    public TeamleaderModel(){}
    public TeamleaderModel(ResultSet res, boolean hasRegion) throws SQLException{
    
            teamleaderId = res.getString("teamleader_id");
            //teamleaderAddress = res.getString("teamleader_address");
            teamleaderEmail = res.getString("email");
            teamleaderMobile = res.getString("mobile");
            teamleaderName = res.getString("teamleader_name");
            supId = res.getString("sup_id");
            if(hasRegion)
                regionName = res.getString("region_name");
    }

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

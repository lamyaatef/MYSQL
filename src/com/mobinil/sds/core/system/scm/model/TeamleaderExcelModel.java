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
public class TeamleaderExcelModel {
    private String teamleaderId="";
    private String teamleaderName="";
    private String teamleaderAddress="";
    private String teamleaderEmail="";
    private String teamleaderMobile="";
    
    
    public TeamleaderExcelModel(){}
    public TeamleaderExcelModel(ResultSet res, boolean hasManager) throws SQLException{
    
            teamleaderId = res.getString("team_id");
            teamleaderAddress = res.getString("teamleader_address");
            teamleaderEmail = res.getString("teamleader_email");
            teamleaderMobile = res.getString("teamleader_mobile");
            teamleaderName = res.getString("teamleader_name");
       
      
        
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
}

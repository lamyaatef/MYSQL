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
public class SupervisorModel {
    private String supervisorId ="";
    private String supervisorAddress="";
    private String supervisorEmail="";
    private String supervisorName="";
    private String supervisorMobile="";
    private String regionName = "";
    
    
    public SupervisorModel(){}
    public SupervisorModel(ResultSet res, boolean hasRegion) throws SQLException{
    
        supervisorId = String.valueOf(res.getInt("supervisor_id"));
        supervisorId = res.getString("supervisor_id");
            //supervisorAddress = res.getString("supervisor_address");
            supervisorEmail = res.getString("email");
            supervisorMobile = res.getString("mobile");
            if (supervisorMobile==null)
                supervisorMobile="null";
            supervisorName = res.getString("supervisor_name");
            if(hasRegion)
                regionName = res.getString("region_name");
        
    }

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

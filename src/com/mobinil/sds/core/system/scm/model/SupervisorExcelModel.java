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
public class SupervisorExcelModel {
    private String supervisorId ="";
    private String supervisorAddress="";
    private String supervisorEmail="";
    private String supervisorName="";
    private String supervisorMobile="";
    
    
    public SupervisorExcelModel(){}
    public SupervisorExcelModel(ResultSet res, boolean hasManager) throws SQLException{
    
        supervisorId = res.getString("sup_id");
            supervisorAddress = res.getString("supervisor_address");
            supervisorEmail = res.getString("supervisor_email");
            supervisorMobile = res.getString("supervisor_mobile");
            supervisorName = res.getString("supervisor_name");
        
        
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

    
}

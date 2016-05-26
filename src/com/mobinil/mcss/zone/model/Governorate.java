/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.zone.model;

/**
 *
 * @author Gado
 */
public class Governorate {
    
    private long governorateId ;
    private String governorateName;
    private String governorateCode;

    /**
     * @return the governorateId
     */
    public long getGovernorateId() {
        return governorateId;
    }

    /**
     * @param governorateId the governorateId to set
     */
    public void setGovernorateId(long governorateId) {
        this.governorateId = governorateId;
    }

    /**
     * @return the governorateName
     */
    public String getGovernorateName() {
        return governorateName;
    }

    /**
     * @param governorateName the governorateName to set
     */
    public void setGovernorateName(String governorateName) {
        this.governorateName = governorateName;
    }

    /**
     * @return the governorateCode
     */
    public String getGovernorateCode() {
        return governorateCode;
    }

    /**
     * @param governorateCode the governorateCode to set
     */
    public void setGovernorateCode(String governorateCode) {
        this.governorateCode = governorateCode;
    }
    
}

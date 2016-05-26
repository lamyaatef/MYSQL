/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.request.model;

/**
 *
 * @author sand
 */
public class UserLevelType {
    private String userLevelTypeId;
    private String userLevelTypeName;

    /**
     * @return the userLevelTypeId
     */
    public String getUserLevelTypeId() {
        return userLevelTypeId;
    }

    /**
     * @param userLevelTypeId the userLevelTypeId to set
     */
    public void setUserLevelTypeId(String userLevelTypeId) {
        this.userLevelTypeId = userLevelTypeId;
    }

    /**
     * @return the userLevelTypeName
     */
    public String getUserLevelTypeName() {
        return userLevelTypeName;
    }

    /**
     * @param userLevelTypeName the userLevelTypeName to set
     */
    public void setUserLevelTypeName(String userLevelTypeName) {
        this.userLevelTypeName = userLevelTypeName;
    }
}

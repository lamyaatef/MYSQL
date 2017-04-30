/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class DCMUserDetailModel extends Model {

    private int userId;
    private String userName;
    private String userAddress;
    private String userMobile;
    private String userFullName;
    private String userEmail;
    private String userLevelTypeId;
    private String userLevelTypeName;
    private String regionId;
    private String regionName;

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setUserId(res.getInt("USER_ID"));
            this.setUserName(res.getString("USER_FULL_NAME"));

        } catch (SQLException ex) {
            Logger.getLogger(DCMUserDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the userAddress
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * @param userAddress the userAddress to set
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * @return the userMobile
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * @param userMobile the userMobile to set
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    /**
     * @return the userFullName
     */
    public String getUserFullName() {
        return userFullName;
    }

    /**
     * @param userFullName the userFullName to set
     */
    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void fillRepDCMUserDetail(ResultSet res){
        try {
            this.setUserFullName(res.getString("USER_FULL_NAME"));
            this.setUserAddress(res.getString("USER_ADDRESS"));
            this.setUserEmail(res.getString("USER_EMAIL"));
            this.setUserMobile(res.getString("USER_MOBILE"));

        } catch (SQLException ex) {
            Logger.getLogger(DCMUserDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    /**
     * @return the regionId
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * @param regionId the regionId to set
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
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

    
    public void fillDetail(ResultSet res){
        try {
            this.setUserId(res.getInt("DCM_USER_ID"));
            //this.setUserLevelTypeId(res.getString("USER_LEVEL_TYPE_ID"));
            this.setRegionName(res.getString("REGION_NAME"));
            this.setUserFullName(res.getString("USER_FULL_NAME"));
            //this.setUserAddress(res.getString("USER_ADDRESS"));
            this.setUserEmail(res.getString("USER_EMAIL"));
            this.setUserMobile(res.getString("USER_MOBILE"));
            //this.setRegionId(res.getString("REGION_ID"));

        } catch (SQLException ex) {
            Logger.getLogger(DCMUserDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void fillDetailWithRegion(ResultSet res){
        try {
            this.setUserId(res.getInt("DCM_USER_ID"));
            //this.setUserLevelTypeId(res.getString("USER_LEVEL_TYPE_ID"));
            this.setRegionName(res.getString("REGION")+" - "+res.getString("GOVERN")+" - "+res.getString("CITY")+" - "+res.getString("DISTRICT")+" - "+res.getString("IMG_DISTRICT")+" - "+res.getString("AREA"));
            this.setUserFullName(res.getString("USER_FULL_NAME"));
            //this.setUserAddress(res.getString("USER_ADDRESS"));
            this.setUserEmail(res.getString("USER_EMAIL"));
            this.setUserMobile(res.getString("USER_MOBILE"));
            //this.setRegionId(res.getString("REGION_ID"));

        } catch (SQLException ex) {
            Logger.getLogger(DCMUserDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /*
    public void fillForRepSupDetail(ResultSet res){
        try {
            this.setUserId(res.getInt("DCM_USER_ID"));
            //this.setUserLevelTypeId(res.getString("USER_LEVEL_TYPE_ID"));
            this.setRegionName(res.getString("REGION_NAME"));
            this.setUserFullName(res.getString("USER_FULL_NAME"));
            //this.setUserAddress(res.getString("USER_ADDRESS"));
            this.setUserEmail(res.getString("USER_EMAIL"));
            this.setUserMobile(res.getString("USER_MOBILE"));
            //this.setRegionId(res.getString("REGION_ID"));

        } catch (SQLException ex) {
            Logger.getLogger(DCMUserDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    public void fillForRepTeamleadDetail(ResultSet res){
        try {
            this.setUserId(res.getInt("DCM_USER_ID"));
            //this.setUserLevelTypeId(res.getString("USER_LEVEL_TYPE_ID"));
            this.setRegionName(res.getString("REGION_NAME"));
            this.setUserFullName(res.getString("USER_FULL_NAME"));
            //this.setUserAddress(res.getString("USER_ADDRESS"));
            this.setUserEmail(res.getString("USER_EMAIL"));
            this.setUserMobile(res.getString("USER_MOBILE"));
            //this.setRegionId(res.getString("REGION_ID"));

        } catch (SQLException ex) {
            Logger.getLogger(DCMUserDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/
}

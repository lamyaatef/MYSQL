/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.monthListFile.model;

import java.sql.ResultSet;

/**
 *
 * @author sand
 */
public class MonthListFileModel {
     private String  HISTORY_FILE_ID;
	    private String  USER_ID ;
	    private String  FILE_TIMESTAMP ;
	    private String  DCM_CHANNEL_NAME ;
	    private String  DCM_PAYMENT_LEVEL ;
	    private String  DCM_CODE ;
            private String  DCM_ID ;
            private String  FILE_YEAR ;
            private String  FILE_MONTH;
            private String  FILE_STATUS ;
            private String USERNAME;
            private String regionName;
            private String governName;
            private String cityName;
            private String districtName;
            private String areaName;
            private String Supervisor;
            private String Teamleader;
            private String Salesrep;
            
            private String LIST_NAME;

    /**
     * @return the HISTORY_FILE_ID
     */
            
               
public MonthListFileModel()
{
	
}
public MonthListFileModel(ResultSet res)
{
    try
	      {
	    	  
	  
		    HISTORY_FILE_ID = res.getString("HISTORY_FILE_ID");
                    USERNAME= res.getString("PERSON_FULL_NAME");
                    DCM_CODE = res.getString("DCM_CODE");
                    FILE_TIMESTAMP = res.getString("TIMESTAMP");
                    FILE_MONTH = res.getString("MONTH");
                    FILE_YEAR = res.getString("YEAR");
                    DCM_CHANNEL_NAME =  res.getString("CHANNEL_NAME");
                    DCM_PAYMENT_LEVEL =  res.getString("DCM_PAYMENT_LEVEL_NAME");
                    FILE_STATUS= res.getString("STATUS_NAME");
                    DCM_ID = res.getString("DCM_ID");
                    USER_ID = res.getString("USER_ID");
                    
                    regionName = res.getString("region_name");
                    governName = res.getString("govern_name");
                    cityName = res.getString("city_name");
                    districtName = res.getString("district_name");
                    areaName = res.getString("area_name");
                    Supervisor = res.getString("Supervisor_Name");
                    Teamleader = res.getString("Teamleader_Name");
                    Salesrep = res.getString("Salesrep_Name");
                    LIST_NAME = res.getString("LIST_NAME");
                }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	
}        
    public String getHISTORY_FILE_ID() {
        return HISTORY_FILE_ID;
    }

    /**
     * @param HISTORY_FILE_ID the HISTORY_FILE_ID to set
     */
    public void setHISTORY_FILE_ID(String HISTORY_FILE_ID) {
        this.HISTORY_FILE_ID = HISTORY_FILE_ID;
    }

    /**
     * @return the USER_ID
     */
    public String getUSER_ID() {
        return USER_ID;
    }

    /**
     * @param USER_ID the USER_ID to set
     */
    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    /**
     * @return the FILE_TIMESTAMP
     */
    public String getFILE_TIMESTAMP() {
        return FILE_TIMESTAMP;
    }

    /**
     * @param FILE_TIMESTAMP the FILE_TIMESTAMP to set
     */
    public void setFILE_TIMESTAMP(String FILE_TIMESTAMP) {
        this.FILE_TIMESTAMP = FILE_TIMESTAMP;
    }

    /**
     * @return the FILE_YEAR
     */
    public String getFILE_YEAR() {
        return FILE_YEAR;
    }

    /**
     * @param FILE_YEAR the FILE_YEAR to set
     */
    public void setFILE_YEAR(String FILE_YEAR) {
        this.FILE_YEAR = FILE_YEAR;
    }

    /**
     * @return the FILE_MONTH
     */
    public String getFILE_MONTH() {
        return FILE_MONTH;
    }

    /**
     * @param FILE_MONTH the FILE_MONTH to set
     */
    public void setFILE_MONTH(String FILE_MONTH) {
        this.FILE_MONTH = FILE_MONTH;
    }

    /**
     * @return the FILE_STATUS_ID
     */

    /**
     * @return the STATUS_NAME
     */


    /**
     * @return the USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }

    /**
     * @param USERNAME the USERNAME to set
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    /**
     * @return the LIST_NAME
     */
    public String getLIST_NAME() {
        return LIST_NAME;
    }

    /**
     * @param LIST_NAME the LIST_NAME to set
     */
    public void setLIST_NAME(String LIST_NAME) {
        this.LIST_NAME = LIST_NAME;
    }

    /**
     * @return the DCM_CHANNEL_NAME
     */
    public String getDCM_CHANNEL_NAME() {
        return DCM_CHANNEL_NAME;
    }

    /**
     * @param DCM_CHANNEL_NAME the DCM_CHANNEL_NAME to set
     */
    public void setDCM_CHANNEL_NAME(String DCM_CHANNEL_NAME) {
        this.DCM_CHANNEL_NAME = DCM_CHANNEL_NAME;
    }

    /**
     * @return the DCM_PAYMENT_LEVEL
     */
    public String getDCM_PAYMENT_LEVEL() {
        return DCM_PAYMENT_LEVEL;
    }

    /**
     * @param DCM_PAYMENT_LEVEL the DCM_PAYMENT_LEVEL to set
     */
    public void setDCM_PAYMENT_LEVEL(String DCM_PAYMENT_LEVEL) {
        this.DCM_PAYMENT_LEVEL = DCM_PAYMENT_LEVEL;
    }

    /**
     * @return the DCM_CODE
     */
    public String getDCM_CODE() {
        return DCM_CODE;
    }

    /**
     * @param DCM_CODE the DCM_CODE to set
     */
    public void setDCM_CODE(String DCM_CODE) {
        this.DCM_CODE = DCM_CODE;
    }

    /**
     * @return the DCM_ID
     */
    public String getDCM_ID() {
        return DCM_ID;
    }

    /**
     * @param DCM_ID the DCM_ID to set
     */
    public void setDCM_ID(String DCM_ID) {
        this.DCM_ID = DCM_ID;
    }

    /**
     * @return the FILE_STATUS
     */
    public String getFILE_STATUS() {
        return FILE_STATUS;
    }

    /**
     * @param FILE_STATUS the FILE_STATUS to set
     */
    public void setFILE_STATUS(String FILE_STATUS) {
        this.FILE_STATUS = FILE_STATUS;
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

    /**
     * @return the governName
     */
    public String getGovernName() {
        return governName;
    }

    /**
     * @param governName the governName to set
     */
    public void setGovernName(String governName) {
        this.governName = governName;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the districtName
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * @param districtName the districtName to set
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * @return the areaName
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName the areaName to set
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * @return the Supervisor
     */
    public String getSupervisor() {
        return Supervisor;
    }

    /**
     * @param Supervisor the Supervisor to set
     */
    public void setSupervisor(String Supervisor) {
        this.Supervisor = Supervisor;
    }

    /**
     * @return the Teamleader
     */
    public String getTeamleader() {
        return Teamleader;
    }

    /**
     * @param Teamleader the Teamleader to set
     */
    public void setTeamleader(String Teamleader) {
        this.Teamleader = Teamleader;
    }

    /**
     * @return the Salesrep
     */
    public String getSalesrep() {
        return Salesrep;
    }

    /**
     * @param Salesrep the Salesrep to set
     */
    public void setSalesrep(String Salesrep) {
        this.Salesrep = Salesrep;
    }
    
}

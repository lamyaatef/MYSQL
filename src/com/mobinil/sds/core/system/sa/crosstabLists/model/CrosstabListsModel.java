/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.sa.crosstabLists.model;

import java.sql.ResultSet;

/**
 *
 * @author sand
 */
public class CrosstabListsModel {
    private String dcmCode="";
    private String dcmName="";
    private String listName="";
    private String month="";
    private String year="";
    private String posAreaId="";
    private String areaName="";
    private String regionName="";
    private String governName="";
    private String cityName="";
    private String districtName="";
    private String salesrepName="";
    private String supervisorName="";
    private String teamleaderName="";

    /**
     * @return the dcmCode
     */
    public CrosstabListsModel()
{
	
}
public CrosstabListsModel(ResultSet res)
{
    try
	      {
	    	  
	  
		    dcmCode = res.getString("dcm_code");
                    dcmName= res.getString("dcm_name");
                    listName = res.getString("list_name");
                    month = res.getString("month");
                    year = res.getString("year");
                    posAreaId = res.getString("pos_area_id");
                    areaName =  res.getString("area_name");
                    regionName =  res.getString("supervisor_region_name");
                    governName= res.getString("supervisor_govern_name");
                    cityName = res.getString("supervisor_city_name");
                    districtName = res.getString("supervisor_district_name");
                    
                    //salesrepName = res.getString("salesrep_name");
                    supervisorName = res.getString("supervisor_name");
                    //teamleaderName = res.getString("teamleader_name");
                    
                }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	
}        


    public String getDcmCode() {
        return dcmCode;
    }

    /**
     * @param dcmCode the dcmCode to set
     */
    public void setDcmCode(String dcmCode) {
        this.dcmCode = dcmCode;
    }

    /**
     * @return the dcmName
     */
    public String getDcmName() {
        return dcmName;
    }

    /**
     * @param dcmName the dcmName to set
     */
    public void setDcmName(String dcmName) {
        this.dcmName = dcmName;
    }

    /**
     * @return the listName
     */
    public String getListName() {
        return listName;
    }

    /**
     * @param listName the listName to set
     */
    public void setListName(String listName) {
        this.listName = listName;
    }

    /**
     * @return the Month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param Month the Month to set
     */
    public void setMonth(String Month) {
        this.month = Month;
    }

    /**
     * @return the Year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param Year the Year to set
     */
    public void setYear(String Year) {
        this.year = Year;
    }

    /**
     * @return the posAreaId
     */
    public String getPosAreaId() {
        return posAreaId;
    }

    /**
     * @param posAreaId the posAreaId to set
     */
    public void setPosAreaId(String posAreaId) {
        this.posAreaId = posAreaId;
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
     * @return the salesrepName
     */
    public String getSalesrepName() {
        return salesrepName;
    }

    /**
     * @param salesrepName the salesrepName to set
     */
    public void setSalesrepName(String salesrepName) {
        this.salesrepName = salesrepName;
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
}

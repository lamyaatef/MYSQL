/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.scm.model;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 *
 * @author sand
 */
public class PosDetailAddressModel implements Serializable {

    String PosCode;
    String districtId;
    String cityId;
    String regionId;
    String governrateId;
    String areaId;
    String address;
    String ArabicAddress;
    public static final String POS_CODE = "POS_CODE";
    public static final String POS_DISTRICT_ID = "POS_DISTRICT_ID";
    public static final String POS_CITY_ID = "POS_CITY_ID";
    public static final String REGION_ID = "REGION_ID";
    public static final String POS_GOVERNRATE = "POS_GOVERNRATE";
    public static final String POS_AREA_ID = "POS_AREA_ID";
    public static final String POS_ADDRESS = "POS_ADDRESS";
    public static final String POS_ARABIC_ADDRESS = "POS_ARABIC_ADDRESS";
    
//public PosDetailAddressModel(ResultSet res2)
//  {
//    try
//    {
//      PosCode = res2.getString(POS_CODE);
//      districtId = res2.getString(POS_DISTRICT_ID);
//      cityId = res2.getString(POS_CITY_ID);
//      regionId = res2.getString(REGION_ID);
//      governrateId = res2.getString(POS_GOVERNRATE);
//      areaId = res2.getString(POS_AREA_ID);
//      address=res2.getString(POS_ADDRESS);
//      ArabicAddress=res2.getString(POS_ARABIC_ADDRESS);
//    }
//    catch(Exception e)
//    {
//      e.printStackTrace();
//    }  
//  }

    public String getPosCode() {
        return PosCode;
    }

    public void setPosCode(String PosCode) {
        this.PosCode = PosCode;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getGovernrateId() {
        return governrateId;
    }

    public void setGovernrateId(String governrateId) {
        this.governrateId = governrateId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArabicAddress() {
        return ArabicAddress;
    }

    public void setArabicAddress(String ArabicAddress) {
        this.ArabicAddress = ArabicAddress;
    }

}

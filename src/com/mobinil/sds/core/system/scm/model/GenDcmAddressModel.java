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
public class GenDcmAddressModel implements Serializable {

    String DcmCode;
    String cityId;
    String districtId;
    String address;
    public static final String DCM_CODE="DCM_CODE";
    public static final String DCM_CITY_ID = "DCM_CITY_ID";
    public static final String DCM_DISTRICT_ID = "DCM_DISTRICT_ID";
    public static final String DCM_ADDRESS = "DCM_ADDRESS";

//    public GenDcmAddressModel(ResultSet res) {
//        try {
//            cityId = res.getString(DCM_CITY_ID);
//            districtId = res.getString(DCM_DISTRICT_ID);
//            address = res.getString(DCM_ADDRESS);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

  
    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDcmCode() {
        return DcmCode;
    }

    public void setDcmCode(String DcmCode) {
        this.DcmCode = DcmCode;
    }
    
}

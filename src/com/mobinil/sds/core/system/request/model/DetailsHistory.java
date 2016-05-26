/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class DetailsHistory extends Model{
    private String POS_Name;
    private String POS_Email;
    private String POS_Address;
    private String region_Id;
    private Timestamp update_Id;
    private String POS_Governrate;
    private String POS_Area_Id;
    private String POS_Demo_Line;
    private String POS_Place_Type_Id;
    private String POS_District_Id;
    private String POS_City_Id;

    /**
     * @return the POS_Name
     */
    public String getPOS_Name() {
        return POS_Name;
    }

    /**
     * @param POS_Name the POS_Name to set
     */
    public void setPOS_Name(String POS_Name) {
        this.POS_Name = POS_Name;
    }

    /**
     * @return the POS_Email
     */
    public String getPOS_Email() {
        return POS_Email;
    }

    /**
     * @param POS_Email the POS_Email to set
     */
    public void setPOS_Email(String POS_Email) {
        this.POS_Email = POS_Email;
    }

    /**
     * @return the POS_Address
     */
    public String getPOS_Address() {
        return POS_Address;
    }

    /**
     * @param POS_Address the POS_Address to set
     */
    public void setPOS_Address(String POS_Address) {
        this.POS_Address = POS_Address;
    }

    /**
     * @return the region_Id
     */
    public String getRegion_Id() {
        return region_Id;
    }

    /**
     * @param region_Id the region_Id to set
     */
    public void setRegion_Id(String region_Id) {
        this.region_Id = region_Id;
    }

    /**
     * @return the update_Id
     */
    public Timestamp getUpdate_Id() {
        return update_Id;
    }

    /**
     * @param update_Id the update_Id to set
     */
    public void setUpdate_Id(Timestamp update_Id) {
        this.update_Id = update_Id;
    }

    /**
     * @return the POS_Governrate
     */
    public String getPOS_Governrate() {
        return POS_Governrate;
    }

    /**
     * @param POS_Governrate the POS_Governrate to set
     */
    public void setPOS_Governrate(String POS_Governrate) {
        this.POS_Governrate = POS_Governrate;
    }

    /**
     * @return the POS_Area_Id
     */
    public String getPOS_Area_Id() {
        return POS_Area_Id;
    }

    /**
     * @param POS_Area_Id the POS_Area_Id to set
     */
    public void setPOS_Area_Id(String POS_Area_Id) {
        this.POS_Area_Id = POS_Area_Id;
    }

    /**
     * @return the POS_Demo_Line
     */
    public String getPOS_Demo_Line() {
        return POS_Demo_Line;
    }

    /**
     * @param POS_Demo_Line the POS_Demo_Line to set
     */
    public void setPOS_Demo_Line(String POS_Demo_Line) {
        this.POS_Demo_Line = POS_Demo_Line;
    }

    /**
     * @return the POS_Place_Type_Id
     */
    public String getPOS_Place_Type_Id() {
        return POS_Place_Type_Id;
    }

    /**
     * @param POS_Place_Type_Id the POS_Place_Type_Id to set
     */
    public void setPOS_Place_Type_Id(String POS_Place_Type_Id) {
        this.POS_Place_Type_Id = POS_Place_Type_Id;
    }

    /**
     * @return the POS_District_Id
     */
    public String getPOS_District_Id() {
        return POS_District_Id;
    }

    /**
     * @param POS_District_Id the POS_District_Id to set
     */
    public void setPOS_District_Id(String POS_District_Id) {
        this.POS_District_Id = POS_District_Id;
    }

    /**
     * @return the POS_City_Id
     */
    public String getPOS_City_Id() {
        return POS_City_Id;
    }

    /**
     * @param POS_City_Id the POS_City_Id to set
     */
    public void setPOS_City_Id(String POS_City_Id) {
        this.POS_City_Id = POS_City_Id;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setPOS_Name(res.getString("POS_NAME"));
             this.setPOS_Address(res.getString("POS_ADDRESS"));
            this.setPOS_Email(res.getString("POS_EMAIL"));
            this.setPOS_Demo_Line(res.getString("POS_DEMO_LINE"));
            this.setRegion_Id(res.getString("REGION_ID"));
            this.setPOS_Governrate(res.getString("POS_GOVERNRATE"));
            this.setPOS_City_Id(res.getString("POS_CITY_ID"));
            this.setPOS_Place_Type_Id(res.getString("POS_PLACE_TYPE_ID"));
            this.setUpdate_Id(res.getTimestamp("UPDATED_IN"));
            this.setPOS_Area_Id(res.getString("POS_AREA_ID"));
            this.setPOS_District_Id(res.getString("POS_DISTRICT_ID"));
        } catch (SQLException ex) {
            Logger.getLogger(DetailsHistory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

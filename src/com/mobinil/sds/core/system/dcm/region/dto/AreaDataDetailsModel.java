/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.dcm.region.dto;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import com.mobinil.sds.core.system.scm.model.BarCodeCaseModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class AreaDataDetailsModel extends Model{
    private int area_ID;
    private String campas_Code;
    private String covarage;
    private String pop;
    private String sceArabicName;
    private String sceEnglishName;
    private String typeOriginal;
    private String family;
    private String areaCode;

    /**
     * @return the area_ID
     */
    public int getArea_ID() {
        return area_ID;
    }

    /**
     * @param area_ID the area_ID to set
     */
    public void setArea_ID(int area_ID) {
        this.area_ID = area_ID;
    }

    /**
     * @return the campas_Code
     */
    public String getCampas_Code() {
        return campas_Code;
    }

    /**
     * @param campas_Code the campas_Code to set
     */
    public void setCampas_Code(String campas_Code) {
        this.campas_Code = campas_Code;
    }

    /**
     * @return the covarage
     */
    public String getCovarage() {
        return covarage;
    }

    /**
     * @param covarage the covarage to set
     */
    public void setCovarage(String covarage) {
        this.covarage = covarage;
    }

    /**
     * @return the pop
     */
    public String getPop() {
        return pop;
    }

    /**
     * @param pop the pop to set
     */
    public void setPop(String pop) {
        this.pop = pop;
    }

    /**
     * @return the sceArabicName
     */
    public String getSceArabicName() {
        return sceArabicName;
    }

    /**
     * @param sceArabicName the sceArabicName to set
     */
    public void setSceArabicName(String sceArabicName) {
        this.sceArabicName = sceArabicName;
    }

    /**
     * @return the sceEnglishName
     */
    public String getSceEnglishName() {
        return sceEnglishName;
    }

    /**
     * @param sceEnglishName the sceEnglishName to set
     */
    public void setSceEnglishName(String sceEnglishName) {
        this.sceEnglishName = sceEnglishName;
    }

    /**
     * @return the typeOriginal
     */
    public String getTypeOriginal() {
        return typeOriginal;
    }

    /**
     * @param typeOriginal the typeOriginal to set
     */
    public void setTypeOriginal(String typeOriginal) {
        this.typeOriginal = typeOriginal;
    }

    /**
     * @return the family
     */
    public String getFamily() {
        return family;
    }

    /**
     * @param family the family to set
     */
    public void setFamily(String family) {
        this.family = family;
    }

    /**
     * @return the areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * @param areaCode the areaCode to set
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public void fillInstance(ResultSet res) {

        try {
                if( GenericDAO.checkColumnName("AREA_ID", res))
                this.setArea_ID(res.getInt("AREA_ID"));
                if( GenericDAO.checkColumnName("CAPMAS_CODE", res))
                this.setCampas_Code(res.getString("CAPMAS_CODE"));
                if( GenericDAO.checkColumnName("COVARAGE", res))
                this.setCovarage(res.getString("COVARAGE"));
                if( GenericDAO.checkColumnName("POP", res))
                this.setPop(res.getString("POP"));
                if( GenericDAO.checkColumnName("SCEARABICNAME", res))
                this.setSceArabicName(res.getString("SCEARABICNAME"));
                if( GenericDAO.checkColumnName("SCEENGLISHNAME", res))
                this.setSceEnglishName(res.getString("SCEENGLISHNAME"));
                if( GenericDAO.checkColumnName("FAMILY", res))
                this.setFamily(res.getString("FAMILY"));
                if( GenericDAO.checkColumnName("AREA_CODE", res))
                this.setAreaCode(res.getString("AREA_CODE"));
                if( GenericDAO.checkColumnName("TYPE_ORIGINAL", res))
                this.setTypeOriginal(res.getString("TYPE_ORIGINAL"));

            } catch (SQLException ex) {
                Logger.getLogger(BarCodeCaseModel.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
}

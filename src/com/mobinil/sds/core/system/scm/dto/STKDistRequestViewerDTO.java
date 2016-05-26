/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.dto;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import com.mobinil.sds.core.system.scm.model.STKDistRequestModel;
import java.sql.ResultSet;

/**
 *
 * @author mabdelaal
 */
public class STKDistRequestViewerDTO extends Model {

    private STKDistRequestModel stkDistRequestModel;
    private String DCM_NAME,PERSON_FULL_NAME;


    @Override
    public void fillInstance(ResultSet res) {
        try {
            STKDistRequestModel temp = new STKDistRequestModel();
            temp.fillInstance(res);
                setStkDistRequestModel(temp);
        if (GenericDAO.checkColumnName("PERSON_FULL_NAME", res)) {
                this.setPERSON_FULL_NAME(res.getString("PERSON_FULL_NAME"));
            }
        if (GenericDAO.checkColumnName("DCM_NAME", res)) {
                this.setDCM_NAME(res.getString("DCM_NAME"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the stkDistRequestModel
     */
    public STKDistRequestModel getStkDistRequestModel() {
        return stkDistRequestModel;
    }

    /**
     * @param stkDistRequestModel the stkDistRequestModel to set
     */
    public void setStkDistRequestModel(STKDistRequestModel stkDistRequestModel) {
        this.stkDistRequestModel = stkDistRequestModel;
    }

    /**
     * @return the DCM_NAME
     */
    public String getDCM_NAME() {
        return DCM_NAME;
    }

    /**
     * @param DCM_NAME the DCM_NAME to set
     */
    public void setDCM_NAME(String DCM_NAME) {
        this.DCM_NAME = DCM_NAME;
    }

    /**
     * @return the PERSON_FULL_NAME
     */
    public String getPERSON_FULL_NAME() {
        return PERSON_FULL_NAME;
    }

    /**
     * @param PERSON_FULL_NAME the PERSON_FULL_NAME to set
     */
    public void setPERSON_FULL_NAME(String PERSON_FULL_NAME) {
        this.PERSON_FULL_NAME = PERSON_FULL_NAME;
    }

}

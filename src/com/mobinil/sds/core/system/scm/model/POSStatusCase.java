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
public class POSStatusCase extends Model{

    private String POSCode;
    private int caseExist;

    /**
     * @return the POSId
     */
    public String getPOSCode() {
        return POSCode;
    }

    /**
     * @param POSId the POSId to set
     */
    public void setPOSCode(String POSCode) {
        this.POSCode = POSCode;
    }

    /**
     * @return the caseExist
     */
    public int getCaseExist() {
        return caseExist;
    }

    /**
     * @param caseExist the caseExist to set
     */
    public void setCaseExist(int caseExist) {
        this.caseExist = caseExist;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setPOSCode(res.getString("POS_CODE"));
            this.setCaseExist(res.getInt("EXIST"));
        } catch (SQLException ex) {
            Logger.getLogger(POSStatusCase.class.getName()).log(Level.SEVERE, null, ex);
        }


    }



}

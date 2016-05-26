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
public class STKStatusCase extends Model{
private String STKDial;
private int existCase;

    /**
     * @return the STKDial
     */
    public String getSTKDial() {
        return STKDial;
    }

    /**
     * @param STKDial the STKDial to set
     */
    public void setSTKDial(String STKDial) {
        this.STKDial = STKDial;
    }

    /**
     * @return the existCase
     */
    public int getExistCase() {
        return existCase;
    }

    /**
     * @param existCase the existCase to set
     */
    public void setExistCase(int existCase) {
        this.existCase = existCase;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setSTKDial(res.getString("STK_DIAL"));
            this.setExistCase(res.getInt("EXIST"));
        } catch (SQLException ex) {
            Logger.getLogger(STKStatusCase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

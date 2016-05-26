/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.commission.model;

import com.mobinil.sds.core.system.Model;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class RatedFileError extends Model{
    private int lineNumber;
    private String SIMNumber;
    private Date firstRatedsCall;
    /**
     * @return the lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * @param lineNumber the lineNumber to set
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * @return the SIMNumber
     */
    public String getSIMNumber() {
        return SIMNumber;
    }

    /**
     * @param SIMNumber the SIMNumber to set
     */
    public void setSIMNumber(String SIMNumber) {
        this.SIMNumber = SIMNumber;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setLineNumber(res.getInt("LINE_NUMBER"));
            this.setSIMNumber(res.getString("SIM_CARD_SERIAL_NUMBER"));
            this.setFirstRatedsCall(res.getDate("FIRST_RATED_ACTIVITY_DATE"));
        } catch (SQLException ex) {
            Logger.getLogger(RatedFileError.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the firstRatedsCall
     */
    public Date getFirstRatedsCall() {
        return firstRatedsCall;
    }

    /**
     * @param firstRatedsCall the firstRatedsCall to set
     */
    public void setFirstRatedsCall(Date firstRatedsCall) {
        this.firstRatedsCall = firstRatedsCall;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

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
public class POSStatusHistory extends Model{

private String userName;
private String statusName;
private Date changeDate;
private String reason;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the changeDate
     */
    public Date getChangeDate() {
        return changeDate;
    }

    /**
     * @param changeDate the changeDate to set
     */
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the status
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param status the status to set
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public void fillInstance(ResultSet res) {
      try {
            
            this.setChangeDate(res.getDate("UPDATED_IN"));
            this.setReason(res.getString("REASON"));
            this.setStatusName(res.getString("DCM_STATUS_NAME"));
            this.setUserName(res.getString("PERSON_FULL_NAME"));
        } catch (SQLException ex) {
            Logger.getLogger(POSStatusHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

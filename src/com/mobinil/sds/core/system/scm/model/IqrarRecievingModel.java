/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AHMED SAFWAT
 */
public class IqrarRecievingModel extends Model {

    private String posCode;
    private Date iqrarRecievedDate; // not presist in the database it's only for already recieved iqrars

    public String getPosCode() {
        return this.posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public Date getIqrarRecievedDate() {
        return this.iqrarRecievedDate;
    }

    public void setIqrarRecievedDate(Date iqrarRecievedDate) {
        this.iqrarRecievedDate = iqrarRecievedDate;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {

            if (GenericDAO.checkColumnName("POS_CODE", res)) {
                this.setPosCode(res.getString("POS_CODE"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(IqrarRecievingModel.class.getName()).log(Level.SEVERE, null, ex);
        }




    }
}

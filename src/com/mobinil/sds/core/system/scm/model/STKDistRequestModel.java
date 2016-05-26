/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author mabdelaal
 */
public class STKDistRequestModel extends Model {

    private String REQUEST_ID, DIST_ID, QUANTITY, USER_ID, NO_OF_DOWN;
    private Timestamp UPDATE_IN;
    private Blob REQUEST_FILE;

    /**
     * @return the REQUEST_ID
     */
    public String getREQUEST_ID() {
        return REQUEST_ID;
    }

    /**
     * @param REQUEST_ID the REQUEST_ID to set
     */
    public void setREQUEST_ID(String REQUEST_ID) {
        this.REQUEST_ID = REQUEST_ID;
    }

    /**
     * @return the DIST_ID
     */
    public String getDIST_ID() {
        return DIST_ID;
    }

    /**
     * @param DIST_ID the DIST_ID to set
     */
    public void setDIST_ID(String DIST_ID) {
        this.DIST_ID = DIST_ID;
    }

    /**
     * @return the QUANTITY
     */
    public String getQUANTITY() {
        return QUANTITY;
    }

    /**
     * @param QUANTITY the QUANTITY to set
     */
    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    /**
     * @return the USER_ID
     */
    public String getUSER_ID() {
        return USER_ID;
    }

    /**
     * @param USER_ID the USER_ID to set
     */
    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    /**
     * @return the NO_OF_DOWN
     */
    public String getNO_OF_DOWN() {
        return NO_OF_DOWN;
    }

    /**
     * @param NO_OF_DOWN the NO_OF_DOWN to set
     */
    public void setNO_OF_DOWN(String NO_OF_DOWN) {
        this.NO_OF_DOWN = NO_OF_DOWN;
    }

    /**
     * @return the UPDATE_IN
     */
    public Timestamp getUPDATE_IN() {
        return UPDATE_IN;
    }

    /**
     * @param UPDATE_IN the UPDATE_IN to set
     */
    public void setUPDATE_IN(Timestamp UPDATE_IN) {
        this.UPDATE_IN = UPDATE_IN;
    }

    /**
     * @return the REQUEST_FILE
     */
    public Blob getREQUEST_FILE() {
        return REQUEST_FILE;
    }

    /**
     * @param REQUEST_FILE the REQUEST_FILE to set
     */
    public void setREQUEST_FILE(Blob REQUEST_FILE) {
        this.REQUEST_FILE = REQUEST_FILE;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {            
            if (GenericDAO.checkColumnName("REQUEST_ID", res)) {
                this.setREQUEST_ID(res.getString("REQUEST_ID"));
            }
            if (GenericDAO.checkColumnName("DIST_ID", res)) {
                this.setDIST_ID(res.getString("DIST_ID"));
            }
            if (GenericDAO.checkColumnName("QUANTITY", res)) {
                this.setQUANTITY(res.getString("QUANTITY"));
            }
            if (GenericDAO.checkColumnName("USER_ID", res)) {
                this.setUSER_ID(res.getString("USER_ID"));
            }
            if (GenericDAO.checkColumnName("NO_OF_DOWN", res)) {
                this.setNO_OF_DOWN(res.getString("NO_OF_DOWN"));
            }
            if (GenericDAO.checkColumnName("UPDATE_IN", res)) {
                this.setUPDATE_IN(res.getTimestamp("UPDATE_IN"));
            }
            if (GenericDAO.checkColumnName("REQUEST_FILE", res)) {
                this.setREQUEST_FILE(res.getBlob("REQUEST_FILE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

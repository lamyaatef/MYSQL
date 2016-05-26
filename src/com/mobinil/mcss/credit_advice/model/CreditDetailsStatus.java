/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author JOE
 */
public class CreditDetailsStatus {

    private long cdstatusID;
    private String cdstatusName;

    public String getCdstatusName() {
        return cdstatusName;
    }

    public void setCdstatusName(String cdstatusName) {
        this.cdstatusName = cdstatusName;
    }

    public long getCdstatusID() {
        return cdstatusID;
    }

    public void setCdstatusID(long cdstatusID) {
        this.cdstatusID = cdstatusID;
    }
}

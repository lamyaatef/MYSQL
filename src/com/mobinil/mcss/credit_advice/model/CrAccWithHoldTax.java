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
 * @author SAND
 */
public class CrAccWithHoldTax {

    private String dcmCode;
    private Float withHoldTax;

    public String getDcmCode() {
        return dcmCode;
    }

    public void setDcmCode(String dcmCode) {
        this.dcmCode = dcmCode;
    }

    public Float getWithHoldTax() {
        return withHoldTax;
    }

    public void setWithHoldTax(Float withHoldTax) {
        this.withHoldTax = withHoldTax;
    }
}

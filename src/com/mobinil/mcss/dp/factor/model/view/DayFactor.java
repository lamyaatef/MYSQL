/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.factor.model.view;

import javax.validation.constraints.*;

/**
 *
 * @author mabdelaal
 */
public class DayFactor {
    @Min(1)
    private String nameId;
    private String dpId;
    private String factorId;

    public DayFactor() {
    }

    public DayFactor(String nameId, String dpId,String factorId) {
        this.dpId = dpId;
        this.nameId = nameId;
        this.factorId = factorId;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getFactorId() {
        return factorId;
    }

    public void setFactorId(String factorId) {
        this.factorId = factorId;
    }
}

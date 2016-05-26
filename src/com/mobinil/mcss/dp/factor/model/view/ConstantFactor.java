/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.factor.model.view;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.NotEmpty;


/**
 *
 * @author mabdelaal
 */
public class ConstantFactor {
    @Min(1)    
    private String nameId;    
    private String dpId;
    @NotEmpty
    @Digits(integer= Integer.MAX_VALUE,fraction= Integer.MAX_VALUE)
    private String factorValue;
    private String factorId;
    private String factorValueId;
    public ConstantFactor(){}
    public ConstantFactor(String factorId,String nameId,String dpId,String factorValue,String factorValueId){
    this.factorId = factorId;
        this.dpId = dpId;
    this.factorValue = factorValue;
    this.nameId = nameId;
    this.factorValueId = factorValueId;
    }
    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getFactorValue() {
        return factorValue;
    }

    public void setFactorValue(String factorValue) {
        this.factorValue = factorValue;
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
    
    public String toString(){
    StringBuilder objStr = new StringBuilder("nameId is ");
    objStr.append(nameId);
    objStr.append(" factorId is");
    objStr.append(factorId);
    objStr.append(" dpId is");
    objStr.append(dpId);
    objStr.append(" factorValue is");
    objStr.append(factorValue);
    return objStr.toString();
    }

    public String getFactorValueId() {
        return factorValueId;
    }

    public void setFactorValueId(String factorValueId) {
        this.factorValueId = factorValueId;
    }
}

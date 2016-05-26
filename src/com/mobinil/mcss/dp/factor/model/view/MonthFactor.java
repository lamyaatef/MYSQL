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
public class MonthFactor {
    @Min(1)
     private String nameId;
    private String dpId;
    @NotEmpty
    @Digits(integer= Integer.MAX_VALUE,fraction= Integer.MAX_VALUE)
    private String factorValue;
    private String factorId;
    private String factorValueId;
    @NotEmpty
    @Min(0)    
    @Max(36)    
    @Size(min=1,max=2)
    private String monthBck;
    @NotEmpty
    @Min(0)    
    @Max(36)    
    @Size(min=1,max=2)
    private String monthFrw;

    public MonthFactor(){}
    public MonthFactor(String factorId,String nameId,String dpId,String factorValue,String factorValueId,String monthBck,String monthFrw){
    this.factorId = factorId;
        this.dpId = dpId;
    this.factorValue = factorValue;
    this.nameId = nameId;
    this.factorValueId = factorValueId;
    this.monthBck = monthBck;
    this.monthFrw = monthFrw;
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

    public String getMonthBck() {
        return monthBck;
    }

    public void setMonthBck(String monthBck) {
        this.monthBck = monthBck;
    }

    public String getMonthFrw() {
        return monthFrw;
    }

    public void setMonthFrw(String monthFrw) {
        this.monthFrw = monthFrw;
    }
    public String toString(){
    StringBuilder sd = new StringBuilder();
    sd.append("nameId is ");
    sd.append(this.nameId);
    sd.append("-");
    sd.append("factorValue is");
    sd.append(this.factorValue);
    sd.append("-");
    sd.append("dpid is ");
    sd.append(this.dpId);
    sd.append("-");
    sd.append("monthBck is ");
    sd.append(this.monthBck);
    sd.append("-");
    sd.append("monthFrw is");
    sd.append(this.monthFrw);
    sd.append("-");
    sd.append("factorId is");
    sd.append(this.factorId);
    sd.append("-");
    sd.append("factorValueId is");
    sd.append(this.factorValueId);
    
    return sd.toString();   
    }

    public String getFactorId() {
        return factorId;
    }

    public void setFactorId(String factorId) {
        this.factorId = factorId;
    }

    public String getFactorValueId() {
        return factorValueId;
    }

    public void setFactorValueId(String factorValueId) {
        this.factorValueId = factorValueId;
    }
}

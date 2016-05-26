/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.dpManagement.model;

import com.mobinil.mcss.dp.factor.model.Factor;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 *
 * @author Gado
 */
@Entity
@Table(name="dp_final_factor_value")
public class DistFactorsValues implements Serializable{
    @Id
    @Column(name="FINAL_FACTOR_VALUE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_FACTOR_VALUE_SEQ")
    @SequenceGenerator(name = "S_FACTOR_VALUE_SEQ", sequenceName = "FACTOR_VALUE_SEQ", allocationSize = 1)
    private long factorValueId;
    @ManyToOne
    @JoinColumn(name="factor_id")
    private Factor  factorId;
    @Column(name="factorname")
    private String factorname;   
    @Column(name="factorvalue")
    private float factorvalue;
    @ManyToOne
    @JoinColumn(name="dp_id")
    private DrivingPlan  planId;
    /**
     * @return the factorValueId
     */
    public long getFactorValueId() {
        return factorValueId;
    }

    /**
     * @param factorValueId the factorValueId to set
     */
    public void setFactorValueId(long factorValueId) {
        this.factorValueId = factorValueId;
    }

    /**
     * @return the factorId
     */
    public Factor getFactorId() {
        return factorId;
    }

    /**
     * @param factorId the factorId to set
     */
    public void setFactorId(Factor factorId) {
        this.factorId = factorId;
    }

    /**
     * @return the factorname
     */
    public String getFactorname() {
        return factorname;
    }

    /**
     * @param factorname the factorname to set
     */
    public void setFactorname(String factorname) {
        this.factorname = factorname;
    }

    /**
     * @return the factorvalue
     */
    public float getFactorvalue() {
        return factorvalue;
    }

    /**
     * @param factorvalue the factorvalue to set
     */
    public void setFactorvalue(float factorvalue) {
        this.factorvalue = factorvalue;
    }

    /**
     * @return the planId
     */
    public DrivingPlan getPlanId() {
        return planId;
    }

    /**
     * @param planId the planId to set
     */
    public void setPlanId(DrivingPlan planId) {
        this.planId = planId;
    }

  
}

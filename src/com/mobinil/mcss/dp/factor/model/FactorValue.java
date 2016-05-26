package com.mobinil.mcss.dp.factor.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DP_FACTOR_VALUE")
public class FactorValue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SEQ_DP_FACTOR_VALUE_ID")
    @SequenceGenerator(name = "GEN_SEQ_DP_FACTOR_VALUE_ID", sequenceName = "SEQ_DP_FACTOR_VALUE_ID", allocationSize = 1)
    @Column(name = "DP_FACTOR_VALUE_ID")
    private Long factorValueId;        
    
    @Column(name = "DP_FACTOR_VALUE")
    private String factorValue;
    @Column(name = "DP_RANGE_FROM") 
    private Timestamp factorRangeFrom;
    @Column(name = "DP_RANGE_TO") 
    private Timestamp factorRangeTo;
    
    @OneToOne
    @JoinColumn(name="DP_FACTOR_ID")
    private Factor factor;
    
    @Column(name = "DP_MONTH_BCK")
    private String monthFactorBackward;
    
    @Column(name = "DP_MONTH_FRW")
    private String monthFactorForward;
    
   
    
    public FactorValue() {
    }
    public FactorValue(String value) {
       this.factorValue = value; 
    }
    public FactorValue(String value,String monthFactorBackward,String monthFactorForward) {
       this.factorValue = value; 
       this.monthFactorBackward = monthFactorBackward;
       this.monthFactorForward = monthFactorForward;
    }
    public FactorValue(String value,Timestamp factorRangeFrom,Timestamp factorRangeTo) {
       this.factorValue = value; 
       this.factorRangeFrom = factorRangeFrom;
       this.factorRangeTo = factorRangeTo;
    }
    

    public Long getFactorValueId() {
        return factorValueId;
    }

    public void setFactorValueId(Long factorValueId) {
        this.factorValueId = factorValueId;
    }

    public String getFactorValue() {
        return factorValue;
    }

    public void setFactorValue(String factorValue) {
        this.factorValue = factorValue;
    }

    public Timestamp getFactorRangeFrom() {
        return factorRangeFrom;
    }

    public void setFactorRangeFrom(Timestamp factorRangeFrom) {
        this.factorRangeFrom = factorRangeFrom;
    }

    public Timestamp getFactorRangeTo() {
        return factorRangeTo;
    }

    public void setFactorRangeTo(Timestamp factorRangeTo) {
        this.factorRangeTo = factorRangeTo;
    }

    public Factor getFactor() {
        return factor;
    }

    public void setFactor(Factor factor) {
        this.factor = factor;
    }

    public String getMonthFactorBackward() {
        return monthFactorBackward;
    }

    public void setMonthFactorBackward(String monthFactorBackward) {
        this.monthFactorBackward = monthFactorBackward;
    }

    public String getMonthFactorForward() {
        return monthFactorForward;
    }

    public void setMonthFactorForward(String monthFactorForward) {
        this.monthFactorForward = monthFactorForward;
    }
    

}

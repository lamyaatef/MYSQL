package com.mobinil.mcss.dp.factorname.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "DP_FACTOR_NAME")
public class Name {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SEQ_DP_FACTOR_NAME_ID")
    @SequenceGenerator(name = "GEN_SEQ_DP_FACTOR_NAME_ID", sequenceName = "SEQ_DP_FACTOR_NAME_ID", allocationSize = 1)
    @Column(name = "DP_FACTOR_NAME_ID")
    private Long factorNameId;    
    
    @Min(1)
    @Max(4)
    @Column(name = "DP_FACTOR_TYPE_ID")
    private String factorTypeId; 
    
    @NotEmpty 
    @Size(min=1,max=100)
    @Column(name = "DP_FACTOR_NAME", length = 1000) 
    private String factorName;
    @Column(name = "DP_FACTOR_DESC", nullable = true, length = 4000)
    @Size(min=0,max=4000)
    private String factorDescribtion;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="DP_FACTOR_TYPE_ID",insertable=false,updatable=false)
    private FactorType factorType;
    
    public Name() {
    }

    public Long getFactorNameId() {
        return factorNameId;
    }

    public void setFactorNameId(Long factorNameId) {
        this.factorNameId = factorNameId;
    }

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public String getFactorDescribtion() {
        return factorDescribtion;
    }

    public void setFactorDescribtion(String factorDescribtion) {
        this.factorDescribtion = factorDescribtion;
    }
    
    public FactorType getFactorType() {
        return factorType;
    }

    public void setFactorType(FactorType factorType) {
        this.factorType = factorType;
    }

    public String getFactorTypeId() {
        return factorTypeId;
    }

    public void setFactorTypeId(String factorTypeId) {
        this.factorTypeId = factorTypeId;
    }
}

package com.mobinil.mcss.dp.factor.model;

import com.mobinil.mcss.dp.dpManagement.model.DrivingPlan;
import com.mobinil.mcss.dp.factorname.model.FactorType;
import com.mobinil.mcss.dp.factorname.model.Name;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DP_FACTOR")
public class Factor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SEQ_DP_FACTOR_ID")
    @SequenceGenerator(name = "GEN_SEQ_DP_FACTOR_ID", sequenceName = "SEQ_DP_FACTOR_ID", allocationSize = 1)
    @Column(name = "DP_FACTOR_ID")
    private Long factorId;        
    
    @OneToOne
    @JoinColumn(name="DP_ID")
    private DrivingPlan drivingPlan;
    
    @OneToOne
    @JoinColumn(name="DP_FACTOR_NAME_ID"/*,updatable=true,insertable=false*/)
    private Name factorName;
    
     @OneToMany(mappedBy="factor",targetEntity=FactorValue.class,cascade= CascadeType.ALL,fetch= FetchType.EAGER)     
    private List<FactorValue> factorValues;
    
    public Factor() {
    }
    public Factor(Long dpId,Long nameId,String factorType,ArrayList<FactorValue> factorValues) {
       drivingPlan = new DrivingPlan();
       drivingPlan.setDpId(dpId);
       factorName = new Name();
       factorName.setFactorType(new FactorType(factorType));
       factorName.setFactorNameId(nameId);
        for (FactorValue factorValue : factorValues) {
            factorValue.setFactor(this);     
        }
         
       
       this.factorValues = factorValues;
       
//       toString();
    }

    public Long getFactorId() {
        return factorId;
    };

    public void setFactorId(Long factorId) {
        this.factorId = factorId;
    }

    public DrivingPlan getDrivingPlan() {
        
        return drivingPlan;
    }

    public void setDrivingPlan(DrivingPlan drivingPlan) {
        this.drivingPlan = drivingPlan;
    }

    public Name getFactorName() {
        return factorName;
    }

    public void setFactorName(Name factorName) {
        this.factorName = factorName;
    }

    public List<FactorValue> getFactorValues() {
        return factorValues;
    }

    public void setFactorValues(List<FactorValue> factorValues) {
        this.factorValues = factorValues;
    }

    
    public String toString() {
        return ("Factor{" + "factorId=" + factorId + ", drivingPlan=" + drivingPlan.getDpId() + ", factorName=" + factorName.getFactorNameId() + ", factorValues=" + factorValues.size() + ", factorType=" + factorName.getFactorTypeId()+ ", factorType2=" + factorName.getFactorType(). getFactorTypeId()+ '}');        
        
//        return "";
    }

    

   
}

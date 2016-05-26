package com.mobinil.mcss.dp.factorname.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DP_FACTOR_TYPE")
public class FactorType {

	
	private Long factorTypeId;

	
	private String factorTypeName;

	
	
	public FactorType() {		
	}	

	public FactorType(String id) {		            
            this.factorTypeId = Long.parseLong(id);
	}
        
        @Id	
	@Column(name = "DP_FACTOR_TYPE_ID",updatable=false)
    public Long getFactorTypeId() {
        return factorTypeId;
    }

    public void setFactorTypeId(Long factorTypeId) {
        this.factorTypeId = factorTypeId;
    }
@Column(name = "DP_FACTOR_TYPE_NAME", updatable=false)
    public String getFactorTypeName() {
        return factorTypeName;
    }

    public void setFactorTypeName(String factorTypeName) {
        this.factorTypeName = factorTypeName;
    }

    
	
}

package com.mobinil.mcss.dp.factor.service;
import com.mobinil.mcss.dp.dpManagement.model.DrivingPlan;
import com.mobinil.mcss.dp.factor.model.Factor;
import com.mobinil.mcss.dp.factor.model.FactorValue;
import com.mobinil.mcss.dp.factorname.model.FactorType;
import com.mobinil.mcss.dp.factorname.model.Name;
import java.util.List;



public interface FactorService {

	public void saveFactor ( Factor factor );
	public void updateFactor ( Factor factor );
	public boolean checkDublicateFactor ( Factor factor );
	public void deleteFactorValues (Long factorId);
	public List<Factor> getFactorslist(Long drivPlanId);	
	public DrivingPlan getDrivPlanById(Long drivPlanId);	
        public List<Name> getFactorNamelist(String factorTypeId);	
        public List<FactorValue> getFactorValuelist(Long factorId);	
	public List<FactorType> getFactorsTypelist();	        
        public void deleteFactors(String ids,String factorType);
        
}
package com.mobinil.mcss.dp.factor.service;
import com.mobinil.mcss.dp.dpManagement.model.DrivingPlan;
import com.mobinil.mcss.dp.factor.dao.FactorDao;
import com.mobinil.mcss.dp.factor.model.Factor;
import com.mobinil.mcss.dp.factor.model.FactorValue;
import com.mobinil.mcss.dp.factorname.model.FactorType;
import com.mobinil.mcss.dp.factorname.model.Name;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("FactorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FactorServiceImpl implements FactorService {

	@Autowired
	private FactorDao factorDao;

	public FactorServiceImpl() {
	}

    @Override
    public void saveFactor(Factor factor) {
        factorDao.saveFactor(factor);
    }

    @Override
    public void updateFactor(Factor factor) {
        factorDao.updateFactor(factor);
    }

    @Override
    public List<Factor> getFactorslist(Long drivPlanId) {
        return factorDao.getFactorslist(drivPlanId);
    }

    @Override
    public void deleteFactors(String ids,String factorType) {
        factorDao.deleteFactors(ids,factorType);
    }

    @Override
    public List<FactorType> getFactorsTypelist() {
        return  factorDao.getFactorsTypelist();
    }

    @Override
    public List<Name> getFactorNamelist(String factorTypeId) {
        return factorDao.getFactorNamelist(factorTypeId);
    }

    @Override
    public DrivingPlan getDrivPlanById(Long drivPlanId) {
        return  factorDao.getDrivPlanById(drivPlanId);
    }

    @Override
    public List<FactorValue> getFactorValuelist(Long factorId) {
        return  factorDao.getFactorValuelist(factorId);
    }

    @Override
    public void deleteFactorValues(Long factorId) {
        factorDao.deleteFactorValues(factorId);
    }

    @Override
    public boolean checkDublicateFactor(Factor factor) {
        return  factorDao.checkDublicateFactor(factor);
    }



   

}
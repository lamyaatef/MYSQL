package com.mobinil.mcss.dp.factor.dao;

import com.mobinil.mcss.dp.dpManagement.model.DrivingPlan;
import com.mobinil.mcss.dp.factor.model.Factor;
import com.mobinil.mcss.dp.factor.model.FactorValue;
import com.mobinil.mcss.dp.factorname.model.FactorType;
import com.mobinil.mcss.dp.factorname.model.Name;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;




import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("FactorDao")
public class FactorDaoImpl implements FactorDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveFactor(Factor factor) {
       Session dbSession = sessionFactory.openSession();        
        Transaction transaction = null;
        try {
            transaction = dbSession.beginTransaction();
            dbSession.save(factor);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw new HibernateException(e);
        } finally {
            dbSession.close();
        }
    }

    @Override
    public void updateFactor(Factor factor) {
        Session dbSession = sessionFactory.openSession();        
        Transaction transaction = null;
        try {
            transaction = dbSession.beginTransaction();
            dbSession.update(factor);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw new HibernateException(e);
        } finally {
            dbSession.close();
        }
    }

    @Override
    public List<Factor> getFactorslist(Long drivPlanId) {
        Session dbSession = sessionFactory.openSession();
        Criteria cr =  dbSession.createCriteria(Factor.class);        
        cr.add(Restrictions.eq("drivingPlan.dpId", drivPlanId));
        List<Factor> factorList = (List<Factor>) cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();        
        if (!factorList.isEmpty())
        {
            for (Factor factor : factorList) {
                if (!factor.getFactorValues().isEmpty())
                {
                    factor.getFactorValues().size();
                }
                   
            }
        }
        dbSession.close();
        
        return factorList;
    }

    @Override
    public void deleteFactors(String ids,String factorType) {
        ids = ids.substring(0, ids.length() - 1);
        Session dbSession = sessionFactory.openSession();
        Query query = dbSession.createQuery("delete from FactorValue where DP_FACTOR_ID in (" + ids + ")");
        query.executeUpdate();
        query = dbSession.createQuery("delete from Factor where DP_FACTOR_ID in (" + ids + ")");
        query.executeUpdate();
        dbSession.close();
    }

    @Override
    public List<FactorType> getFactorsTypelist() {
        Session dbSession = sessionFactory.openSession();
        List<FactorType> factorTypeList = (List<FactorType>) dbSession.createCriteria(FactorType.class).list();
        dbSession.close();
        return factorTypeList;
    }

    @Override
    public List<Name> getFactorNamelist(String factorTypeId) {
        Session dbSession = sessionFactory.openSession();
        Criteria crName =  dbSession.createCriteria(Name.class);        
        crName.add(Restrictions.eq("factorTypeId", factorTypeId));
        List<Name> factorNameList = (List<Name>) crName.list();                
        dbSession.close();        
        return factorNameList;
    }

    @Override
    public DrivingPlan getDrivPlanById(Long drivPlanId) {
        Session dbSession =  sessionFactory.openSession();
        Criteria criteria =dbSession.createCriteria(DrivingPlan.class);
        criteria.add(Restrictions.eq("dpId", drivPlanId));
        List<DrivingPlan> drivingPlanList = (List<DrivingPlan>) criteria.list();         
        dbSession.close();        
        return drivingPlanList.get(0);
    }

    @Override
    public List<FactorValue> getFactorValuelist(Long factorId) {
       Session dbSession = sessionFactory.openSession();
        Criteria crFactor =  dbSession.createCriteria(FactorValue.class);         
        crFactor.add(Restrictions.eq("factor.factorId", factorId));
        List<FactorValue> factorValueList = (List<FactorValue>) crFactor.list();                
        dbSession.close();        
        return factorValueList;
    }

    @Override
    public void deleteFactorValues(Long factorId) {      
        Session dbSession = sessionFactory.openSession();
        Query query = dbSession.createQuery("delete from FactorValue where DP_FACTOR_ID in (" + factorId + ")");
        query.executeUpdate();        
        dbSession.close();
    }

    @Override
    public boolean checkDublicateFactor(Factor factor) {
//        System.out.println("factor is "+factor.toString());
      Session dbSession = sessionFactory.openSession();
        Criteria cr =  dbSession.createCriteria(Factor.class);        
        cr.add(Restrictions.eq("drivingPlan.dpId", factor.getDrivingPlan().getDpId()));
        cr.add(Restrictions.eq("factorName.factorNameId", factor.getFactorName().getFactorNameId()));        
        if (factor.getFactorId()!=null)
        {
        cr.add(Restrictions.ne("factorId", factor.getFactorId()));        
        }
        List<Factor> factorList = (List<Factor>) cr.list();                
        dbSession.close(); 
        return  factorList.isEmpty();
    }

    
}
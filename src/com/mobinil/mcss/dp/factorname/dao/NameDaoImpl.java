package com.mobinil.mcss.dp.factorname.dao;

import com.mobinil.mcss.dp.factorname.model.FactorType;
import com.mobinil.mcss.dp.factorname.model.Name;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;




import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("NameDao")
public class NameDaoImpl implements NameDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveName(Name name) {
        Session dbSession = sessionFactory.openSession();        
        Transaction transaction = null;
        try {
            transaction = dbSession.beginTransaction();
            dbSession.save(name);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw new HibernateException(e);
        } finally {
            dbSession.close();
        }
    }

    @Override
    public List<Name> getNameslist() {
        Session dbSession = sessionFactory.openSession();
        List<Name> factorNameList = (List<Name>) dbSession.createCriteria(Name.class).list();
        dbSession.close();
        return factorNameList;
    }

    @Override
    public void deleteNames(String ids) throws HibernateException {
        ids = ids.substring(0, ids.length() - 1);
        Session dbSession = sessionFactory.openSession();
        Query query = dbSession.createQuery("delete from Name where DP_FACTOR_NAME_ID in (" + ids + ")");
        query.executeUpdate();
        dbSession.close();
    }    

    @Override
    public List<FactorType> getFactorTypes() {
        Session dbSession = sessionFactory.openSession();
        List<FactorType> factorTypeList = (List<FactorType>) dbSession.createCriteria(FactorType.class).list();
        dbSession.close();
        return factorTypeList;
    }

    @Override
    public List<Name> getNameslist(Name name) {
        Session dbSession = sessionFactory.openSession(); 
        String query = "from Name where "+(name.getFactorTypeId().compareTo("0")!=0 ? ("DP_FACTOR_TYPE_ID="+name.getFactorTypeId()+" and") : "")+"  Lower(DP_FACTOR_NAME) like '%' || :factornamevar || '%') ";        
        Query qu =  dbSession.createQuery(query);         
        qu.setString("factornamevar", name.getFactorName().toLowerCase());
         List<Name> factorNameList = qu.list();
        dbSession.close();
        return factorNameList;
    }

    @Override
    public boolean checkDublicateName(Name name) {
        Session dbSession = sessionFactory.openSession();        
        List<Name> factorNameList = dbSession.createQuery("from Name where DP_FACTOR_TYPE_ID="+name.getFactorTypeId()+" and DP_FACTOR_NAME ='"+name.getFactorName()+"' and DP_FACTOR_NAME_ID <> "+name.getFactorNameId()).list();         
        dbSession.close();
        return factorNameList.isEmpty();
    }

    @Override
    public void updateName(Name name) {
        Session dbSession = sessionFactory.openSession();        
        Transaction transaction = null;
        try {
            transaction = dbSession.beginTransaction();
            dbSession.update(name);                
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw new HibernateException(e);
        } finally {
            dbSession.close();
        }
    }
}
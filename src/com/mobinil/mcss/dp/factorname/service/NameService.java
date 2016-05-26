package com.mobinil.mcss.dp.factorname.service;

import com.mobinil.mcss.dp.factorname.model.FactorType;
import com.mobinil.mcss.dp.factorname.model.Name;
import java.util.List;
import org.hibernate.HibernateException;



public interface NameService {

	public void addName(Name name);
        public void updateName ( Name name );

	public List<Name> listNames();
        
	public List<Name> listNames(Name name);
	public boolean checkDublicateName(Name name);
        
        public void deleteNames(String ids)throws HibernateException;
        
        public List<FactorType> getFactorTypes();
}
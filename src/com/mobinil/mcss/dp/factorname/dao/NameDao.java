package com.mobinil.mcss.dp.factorname.dao;

import com.mobinil.mcss.dp.factorname.model.FactorType;
import com.mobinil.mcss.dp.factorname.model.Name;
import java.util.List;
import org.hibernate.HibernateException;




public interface NameDao {
	// To Save the article detail
	public void saveName ( Name name );
	public void updateName ( Name name );
	
	// To get list of all articles
	public List<Name> getNameslist();
	public List<Name> getNameslist(Name name);
        
        public void deleteNames(String ids) throws HibernateException;
	
        public List<FactorType> getFactorTypes();
        public boolean checkDublicateName(Name name);
}
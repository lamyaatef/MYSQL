package com.mobinil.mcss.dp.factorname.service;

import com.mobinil.mcss.dp.factorname.model.Name;
import java.util.List;


import com.mobinil.mcss.dp.factorname.dao.NameDao;
import com.mobinil.mcss.dp.factorname.model.FactorType;
import org.hibernate.HibernateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("NameService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NameServiceImpl implements NameService {

	@Autowired
	private NameDao nameDao;

	public NameServiceImpl() {
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addName(Name name) {
		nameDao.saveName(name);
	}

	public List<Name> listNames() {
		return nameDao.getNameslist();
	}

    @Override
    public void deleteNames(String ids) throws HibernateException {
        nameDao.deleteNames(ids);
    }

    @Override
    public List<FactorType> getFactorTypes() {
        return nameDao.getFactorTypes();
    }

    @Override
    public List<Name> listNames(Name name) {
        return  nameDao.getNameslist(name);
    }

    @Override
    public boolean checkDublicateName(Name name) {
        return nameDao.checkDublicateName(name);
    }

    @Override
    public void updateName(Name name) {
        nameDao.updateName(name);
    }

   

}
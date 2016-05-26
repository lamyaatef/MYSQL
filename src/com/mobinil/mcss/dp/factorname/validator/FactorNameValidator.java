/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.factorname.validator;

import com.mobinil.mcss.dp.factorname.dao.NameDao;
import com.mobinil.mcss.dp.factorname.dao.NameDaoImpl;
import com.mobinil.mcss.dp.factorname.model.Name;
import com.mobinil.mcss.dp.factorname.service.NameService;
import com.mobinil.mcss.dp.factorname.service.NameServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author mabdelaal
 */
@Component("factorNameValidator")
public class FactorNameValidator implements Validator {
    
    @Autowired
    private NameService nameService;
    
    @Override
    public boolean supports(Class<?> type) {
        return Name.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {        
        Name name = (Name)o;
        System.out.println("name id is "+name.getFactorNameId());
        System.out.println("name naem is "+name.getFactorName());
//        name.setFactorName(name.getFactorName().toLowerCase());
        boolean isDubFactorName =  nameService.checkDublicateName(name);
        if (!isDubFactorName)
        {
          errors.rejectValue("factorName", "DublicateFactorName.name.factorName","Dublicate factor name and type.");
        }
    }
    
}

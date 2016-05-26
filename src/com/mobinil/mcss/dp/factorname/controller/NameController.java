package com.mobinil.mcss.dp.factorname.controller;

import com.mobinil.mcss.dp.factorname.model.FactorType;
import com.mobinil.mcss.dp.factorname.model.Name;
import com.mobinil.mcss.dp.factorname.service.NameService;
import com.mobinil.mcss.dp.factorname.validator.FactorNameValidator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.hibernate.HibernateException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dp/factorname")
public class NameController {

    @Autowired
    private NameService nameService;
    @Autowired
    private FactorNameValidator factorValidator;

    public void setFactorNameValidator(
            FactorNameValidator factorValidator) {
        this.factorValidator = factorValidator;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public ModelAndView saveName(@Valid Name name,
            BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("types", getFactorType());
        model.put("name", name);
        factorValidator.validate(name, result);
        if (result.hasErrors()) {                        
            return new ModelAndView("/dp/factorname/addName", model);
        } else {
            if (name != null && name.getFactorNameId() != null) {
                nameService.updateName(name);
            } else {
                nameService.addName(name);
            }
            return new ModelAndView("redirect:/dp/factorname/names.htm");

        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listNames(@ModelAttribute("name") Name name, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
//        System.out.println("name iss "+name);

        List<Name> names = name != null && name.getFactorName() != null
                ? nameService.listNames(name) : nameService.listNames();
        model.put("names", names);
        model.put("types", getFactorType());
        Name name2 = new Name();
        model.put("name", name2);
        return new ModelAndView("/dp/factorname/namesList", model);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addName(@ModelAttribute("name") Name name,
            BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("types", getFactorType());
        return new ModelAndView("/dp/factorname/addName", model);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editName(HttpServletRequest request, HttpServletResponse response) {        
        String id = request.getParameter("factorNameId"),
                name = request.getParameter("factorName"),
                typeId = request.getParameter("factorTypeId"),
                desc = request.getParameter("factorNamedesc");
        Name editName = new Name();
        editName.setFactorNameId(Long.parseLong(id));
        editName.setFactorName(name);
        editName.setFactorDescribtion(desc);
        editName.setFactorType(new FactorType(typeId));
        editName.setFactorTypeId(typeId);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", editName);
        model.put("types", getFactorType());
        return new ModelAndView("/dp/factorname/addName", model);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteNames(HttpServletRequest request) {
        Name name = new Name();
        ModelAndView mav = listNames(name, null);
        Map<String, Object> model  = mav.getModel();
        String ids = request.getParameter("deleteIds");
        try {
        nameService.deleteNames(ids);    
        } catch (HibernateException e) {            
            String msg = "";
            if (e.getCause().getMessage().contains("integrity constraint")) {
                msg = "Deletion faild because factors using this name.";
            } else {
                e.printStackTrace();
                msg = "Deletion failed due to some reasone."+e.getCause().getMessage();
            }

            model.put("erreoDel", msg);
        }
        return new ModelAndView("/dp/factorname/namesList",model);
    }

    private List<FactorType> getFactorType() {
        List<FactorType> typeList = nameService.getFactorTypes();
        Map<String, String> typeMap = new HashMap<String, String>();
        for (FactorType factorType : typeList) {
            typeMap.put(String.valueOf(factorType.getFactorTypeId()), factorType.getFactorTypeName());
        }
        return typeList;
    }
    
    
};
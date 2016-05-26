package com.mobinil.mcss.dp.factor.controller;

import com.mobinil.mcss.dp.dpManagement.model.DrivingPlan;
import com.mobinil.mcss.dp.factor.constant.FactorConstant;
import com.mobinil.mcss.dp.factor.model.Factor;
import com.mobinil.mcss.dp.factor.model.FactorValue;
import com.mobinil.mcss.dp.factor.model.view.*;
import com.mobinil.mcss.dp.factor.service.FactorService;
import com.mobinil.mcss.dp.factor.validator.FactorValidator;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(FactorConstant.FACTOR_REQUEST_MAPPING)
public class FactorController {

    @Autowired
    private FactorService factorService;
    @Autowired
    private FactorValidator factorValidator;

    public void setFactorValidator(FactorValidator factorValidator) {
        this.factorValidator = factorValidator;
    }

    @RequestMapping(value = FactorConstant.NAV_CON_SAV, method = RequestMethod.GET)
    public ModelAndView saveConstantFactor(@Valid ConstantFactor conFactor,
            BindingResult result, HttpServletRequest request) {
        Factor conFactorObj = initFactorPerType(FactorConstant.FACTOR_TYPE_CONSTANT, Long.parseLong(conFactor.getDpId()), Long.parseLong(conFactor.getNameId()), conFactor.getFactorValue(), "", "");
        if (conFactor.getFactorId() != null) {
            Long factorId = Long.parseLong(conFactor.getFactorId());
            conFactorObj.setFactorId(factorId);
            conFactorObj.getFactorName().setFactorNameId(Long.parseLong(conFactor.getNameId()));
        }
        factorValidator.validate(conFactorObj, result);
        printErrors(result);
        if (!result.hasErrors()) {
            if (conFactor.getFactorId() == null) {
                factorService.saveFactor(conFactorObj);
            } else {
               List<FactorValue> valueList = conFactorObj.getFactorValues();
                for (FactorValue value : valueList) {
                    value.setFactorValueId(Long.parseLong(conFactor.getFactorValueId()));
                    value.setFactor(conFactorObj);
                }
                conFactorObj.setFactorValues(valueList);
                factorService.updateFactor(conFactorObj);
            }
            return listFactors(request);
        } else {
            //printErrors(result);
            Map<String, Object> model = putBasicKeysToAddFactorPages(request);
            model.put("constantFactor", conFactor);
            if (conFactor.getFactorId() == null) {
                return new ModelAndView(FactorConstant.REDIRECT_ADD_CON_FACTORS, model);
            } else {
                return editConFactor(request);
            }


        }

    }

    @RequestMapping(value = FactorConstant.NAV_MON_SAV, method = RequestMethod.GET)
    public ModelAndView saveMonthFactor(@Valid MonthFactor monFactor,
            BindingResult result, HttpServletRequest request) {
        Factor monFactorObj = initFactorPerType(FactorConstant.FACTOR_TYPE_MONTH, Long.parseLong(monFactor.getDpId()), Long.parseLong(monFactor.getNameId()), monFactor.getFactorValue(), monFactor.getMonthBck(), monFactor.getMonthFrw());
        if (monFactor.getFactorId() != null) {
            Long factorId = Long.parseLong(monFactor.getFactorId());
            monFactorObj.setFactorId(factorId);
            monFactorObj.getFactorName().setFactorNameId(Long.parseLong(monFactor.getNameId()));
        }
        factorValidator.validate(monFactorObj, result);
        if (!result.hasErrors()) {
        if (monFactor.getFactorId() == null) {
            factorService.saveFactor(monFactorObj);
        } else {
            Long factorId = Long.parseLong(monFactor.getFactorId());
            monFactorObj.setFactorId(factorId);
            monFactorObj.getFactorName().setFactorNameId(Long.parseLong(monFactor.getNameId()));
            List<FactorValue> valueList = monFactorObj.getFactorValues();
            for (FactorValue value : valueList) {
                value.setFactorValueId(Long.parseLong(monFactor.getFactorValueId()));
                value.setFactor(monFactorObj);
            }
            monFactorObj.setFactorValues(valueList);
            factorService.updateFactor(monFactorObj);
        }
        }
        else{
        Map<String, Object> model = putBasicKeysToAddFactorPages(request);
            model.put("monthFactor", monFactor);
            if (monFactor.getFactorId() == null) {
                return new ModelAndView(FactorConstant.REDIRECT_ADD_MON_FACTORS, model);
            } else {
                return editMonFactor(request);
            }
        }
        return listFactors(request);
    }

    @RequestMapping(value = FactorConstant.NAV_DAY_SAV, method = RequestMethod.GET)
    public ModelAndView saveDayFactor(@Valid DayFactor dayFactor,
            BindingResult result, HttpServletRequest request) {

        Factor dayFactorObj = new Factor(Long.parseLong(dayFactor.getDpId()), Long.parseLong(dayFactor.getNameId()), FactorConstant.FACTOR_TYPE_DAY, getAllValues(request));
        if (dayFactor.getFactorId() != null) {
            Long factorId = Long.parseLong(dayFactor.getFactorId());
            dayFactorObj.setFactorId(factorId);
            dayFactorObj.getFactorName().setFactorNameId(Long.parseLong(dayFactor.getNameId()));
        }
        factorValidator.validate(dayFactorObj, result);
        if (!result.hasErrors()) {
        if (dayFactor.getFactorId() == null) {
            factorService.saveFactor(dayFactorObj);
        } else {
            Long factorId = Long.parseLong(dayFactor.getFactorId());
            dayFactorObj.setFactorId(factorId);
            dayFactorObj.getFactorName().setFactorNameId(Long.parseLong(dayFactor.getNameId()));
            factorService.deleteFactorValues(factorId);
            List<FactorValue> valueList = dayFactorObj.getFactorValues();
            for (FactorValue value : valueList) {
                value.setFactor(dayFactorObj);
            }
            dayFactorObj.setFactorValues(valueList);
            factorService.updateFactor(dayFactorObj);
            
        }
        }
        else{
        Map<String, Object> model = putBasicKeysToAddFactorPages(request);
//        List<FactorValue> factorValList = dayFactorObj.getFactorValues();                
        List<FactorValue> factorValList = getAllValues(request);                
        model.put("dayFactor", dayFactor);
        model.put("valueListSize", factorValList.size());
        model.put("valueList", factorValList);
            
            if (dayFactor.getFactorId() == null) {
                return new ModelAndView(FactorConstant.REDIRECT_ADD_DAY_FACTORS, model);
            } else {
                return editDayFactor(request);
            }
        }
        return listFactors(request);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listFactors(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        //Ahmed Adel
        String dpId = getDpID(request);        
        //Finish Ahmed Adel
        DrivingPlan drivPlan = factorService.getDrivPlanById(Long.parseLong(dpId));
        HashMap<String, List<Factor>> HMFactors = spliteMainFactorsList(factorService.getFactorslist(drivPlan.getDpId()));
        model.put(FactorConstant.LIST_FACTORS_CONSTANT, HMFactors.get(FactorConstant.FACTOR_TYPE_CONSTANT));
        model.put(FactorConstant.LIST_FACTORS_DAY, HMFactors.get(FactorConstant.FACTOR_TYPE_DAY));
//        model.put(FactorConstant.LIST_FACTORS_DAY_MANY_VALS, HMFactors.get(FactorConstant.FACTOR_TYPE_DAY_MANY_VALS));
        model.put(FactorConstant.LIST_FACTORS_MONTH, HMFactors.get(FactorConstant.FACTOR_TYPE_MONTH));
        model.put(FactorConstant.FACTOR_DRIVING_PLAN_, drivPlan.getPlanName());
        model.put(FactorConstant.FACTOR_DRIVING_PLAN_ID, drivPlan.getDpId());        
        return new ModelAndView(FactorConstant.REDIRECT_MAIN_FACTORS, model);
    }
    @RequestMapping(value=FactorConstant.NAV_VIEW_FACTORS , method = RequestMethod.GET)
    public ModelAndView viewFactors(HttpServletRequest request) {
        ModelAndView viewFactorsModel = listFactors(request);
        viewFactorsModel.setViewName(FactorConstant.REDIRECT_VIEW_FACTORS);
        return viewFactorsModel;
    }

    @RequestMapping(value = FactorConstant.NAV_CON_ADD, method = RequestMethod.GET)
    public ModelAndView addConFactor(@ModelAttribute("constantFactor") ConstantFactor conFactor, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(FactorConstant.REDIRECT_ADD_CON_FACTORS, putBasicKeysToAddFactorPages(request));
    }

    @RequestMapping(value = FactorConstant.NAV_MON_ADD, method = RequestMethod.GET)
    public ModelAndView addMonFactor(@ModelAttribute("monthFactor") MonthFactor monFactor, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = putBasicKeysToAddFactorPages(request);
        return new ModelAndView(FactorConstant.REDIRECT_ADD_MON_FACTORS, model);
    }

    @RequestMapping(value = FactorConstant.NAV_DAY_ADD, method = RequestMethod.GET)
    public ModelAndView addDayFactor(@ModelAttribute("dayFactor") DayFactor dayFactor, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = putBasicKeysToAddFactorPages(request);        
        List<FactorValue> factorValList = new ArrayList<FactorValue>(1);        
        factorValList.add(new FactorValue("","",""));
        model.put("dayFactor", dayFactor);
        model.put("valueListSize", factorValList.size());
        model.put("valueList", factorValList);
        return new ModelAndView(FactorConstant.REDIRECT_ADD_DAY_FACTORS, model);
    }

    @RequestMapping(value = FactorConstant.NAV_CON_EDIT, method = RequestMethod.GET)
    public ModelAndView editConFactor(HttpServletRequest request) {
        String factorid = request.getParameter("factorid");
        String factornameid = request.getParameter("factornameid");
        String factorval = request.getParameter("factorval");
        String factorvalid = request.getParameter("factorvalid");
        String dpid = request.getParameter("dpid");
        ConstantFactor conFactor = new ConstantFactor(factorid, factornameid, dpid, factorval, factorvalid);
        Map<String, Object> model = putBasicKeysToAddFactorPages(request);
        model.put("constantFactor", conFactor);
        return new ModelAndView(FactorConstant.REDIRECT_EDIT_CON_FACTORS, model);
    }

    @RequestMapping(value = FactorConstant.NAV_MON_EDIT, method = RequestMethod.GET)
    public ModelAndView editMonFactor(HttpServletRequest request) {
        String factorid = request.getParameter("factorid");
        String factornameid = request.getParameter("factornameid");
        String factorval = request.getParameter("factorval");
        String factorvalid = request.getParameter("factorvalid");
        String dpid = request.getParameter("dpid");
        String factormonbck = request.getParameter("factormonbck");
        String factormonfwd = request.getParameter("factormonfwd");
        MonthFactor monFactor = new MonthFactor(factorid, factornameid, dpid, factorval, factorvalid, factormonbck, factormonfwd);
        Map<String, Object> model = putBasicKeysToAddFactorPages(request);
        model.put("monthFactor", monFactor);
        return new ModelAndView(FactorConstant.REDIRECT_EDIT_MON_FACTORS, model);
    }

    @RequestMapping(value = FactorConstant.NAV_DAY_EDIT, method = RequestMethod.GET)
    public ModelAndView editDayFactor(HttpServletRequest request) {
        String factorid = request.getParameter("factorid");
        factorid = factorid==null || factorid.compareTo("")==0 ? request.getParameter("factorId") : factorid;
        String factornameid = request.getParameter("factornameid");
        String dpid = request.getParameter("dpid");
        DayFactor dayFactor = new DayFactor(factornameid, dpid, factorid);
        List<FactorValue> factorValList = factorService.getFactorValuelist(Long.parseLong(factorid));
        Map<String, Object> model = putBasicKeysToAddFactorPages(request);
        model.put("dayFactor", dayFactor);
        model.put("valueListSize", factorValList.size());
        model.put("valueList", factorValList);
        return new ModelAndView(FactorConstant.REDIRECT_EDIT_DAY_FACTORS, model);
    }

    @RequestMapping(value = FactorConstant.NAV_DEL, method = RequestMethod.GET)
    public ModelAndView deleteFactors(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        String factorTypeId = request.getParameter("factorType");
        String dpId = getDpID(request);
        System.out.println("dpid iss "+dpId);
        String deleteIds = request.getParameter("deleteIds");
        model.put("dpid", dpId);
        factorService.deleteFactors(deleteIds, factorTypeId);
        return listFactors(request);
    }

    private HashMap<String, List<Factor>> spliteMainFactorsList(List<Factor> factors) {
        HashMap<String, List<Factor>> HMFactors = new HashMap<String, List<Factor>>();
        List<Factor> constantFactors = new ArrayList<Factor>();
        List<Factor> monthFactors = new ArrayList<Factor>();
        List<Factor> dayFactors = new ArrayList<Factor>();
//        List<Factor> dayFactorsHasOneValue = new ArrayList<Factor>();
//        List<Factor> dayFactorsHasManyValue = new ArrayList<Factor>();
        
        String currentId = "";
        for (Factor factor : factors) {
            currentId = factor.getFactorName().getFactorTypeId();
            if (currentId.compareTo(FactorConstant.FACTOR_TYPE_CONSTANT) == 0) {
                constantFactors.add(factor);
            }
            else if (currentId.compareTo(FactorConstant.FACTOR_TYPE_MONTH) == 0) {
                monthFactors.add(factor);
            }
            else if (currentId.compareTo(FactorConstant.FACTOR_TYPE_DAY) == 0) {
                dayFactors.add(factor);
//                if (factor.getFactorValues().isEmpty() || factor.getFactorValues().size() == 1) {
//                    dayFactorsHasOneValue.add(factor);
//                } else {
//                    dayFactorsHasManyValue.add(factor);
//                }

            }
        }
        HMFactors.put(FactorConstant.FACTOR_TYPE_CONSTANT, constantFactors);
        HMFactors.put(FactorConstant.FACTOR_TYPE_DAY, dayFactors);
//        HMFactors.put(FactorConstant.FACTOR_TYPE_DAY_MANY_VALS, dayFactorsHasManyValue);
        HMFactors.put(FactorConstant.FACTOR_TYPE_MONTH, monthFactors);
        return HMFactors;
    }

    private Factor initFactorPerType(String factorTypeId, Long dpId, Long nameId, String value,
            String monthFactorBackward, String monthFactorForward) {
        return new Factor(dpId, nameId, factorTypeId, initFactorValue(value, factorTypeId, monthFactorBackward, monthFactorForward));
    }

    private ArrayList<FactorValue> initFactorValue(String value, String factorTypeId, String monthFactorBackward, String monthFactorForward) {
        ArrayList<FactorValue> factorValues = new ArrayList<FactorValue>(1);
        if (factorTypeId.compareTo(FactorConstant.FACTOR_TYPE_CONSTANT) == 0) {
            factorValues.add(new FactorValue(value));
        }
        if (factorTypeId.compareTo(FactorConstant.FACTOR_TYPE_MONTH) == 0) {
            factorValues.add(new FactorValue(value, monthFactorBackward, monthFactorForward));
        }
        return factorValues;
    }

    private Map<String, Object> putBasicKeysToAddFactorPages(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        String factorTypeId = request.getParameter("factorType");
        String dpid = request.getParameter("dpid");
        model.put("name_list", factorService.getFactorNamelist(factorTypeId));
        model.put("factorType", factorTypeId);
        model.put("dpid", dpid);
        return model;
    }

    private ArrayList<FactorValue> getAllValues(HttpServletRequest request) {
        ArrayList<FactorValue> factorValues = new ArrayList<FactorValue>();
        Map<String, String[]> parametersMap = request.getParameterMap();
        String hhmmss = " 00:00:00";
        for (String key : parametersMap.keySet()) {
            if (key.startsWith("from")) {
                String fieldId = key.substring(4, key.length());
                String factorValue = request.getParameter("val" + fieldId);
                factorValue = factorValue ==null || factorValue.compareTo("")==0 ? null : factorValue ;
                String factorFromValue = request.getParameter(key);
                factorFromValue = factorFromValue ==null || factorFromValue.compareTo("")==0 ? null : factorFromValue.contains(hhmmss) ? factorFromValue : factorFromValue +hhmmss ;
                String factorToValue = request.getParameter("to" + fieldId);
                factorToValue = factorToValue ==null || factorToValue.compareTo("")==0 ? null : factorToValue.contains(hhmmss) ? factorToValue : factorToValue+hhmmss ;                
                FactorValue value = new FactorValue();
                value.setFactorValue(factorValue);
                value.setFactorRangeFrom(factorFromValue!=null ? Timestamp.valueOf(factorFromValue ) : null);
                value.setFactorRangeTo(factorToValue!=null ? Timestamp.valueOf(factorToValue ) : null);
                factorValues.add(value);
            }
        }
        return factorValues;
    }

    private void printErrors(BindingResult result) {
        for (ObjectError error : result.getAllErrors()) {
            System.out.println("---------------");
            System.out.println(error.getCode());
            System.out.println("----------------");
            for (String string : error.getCodes()) {
                System.out.println(string);
            }
            System.out.println("********************");
            System.out.println(error.toString());
        }
    }
    private String getDpID (HttpServletRequest request){
    String dpId = (String)request.getParameter("dpid");
        dpId = dpId ==null  || dpId.compareTo("undefined")==0 ? request.getParameter("dpId"):dpId;
        dpId = dpId ==null  || dpId.compareTo("undefined")==0 ? request.getParameter(FactorConstant.FACTOR_DRIVING_PLAN_ID):dpId;
        return dpId;
    }

}
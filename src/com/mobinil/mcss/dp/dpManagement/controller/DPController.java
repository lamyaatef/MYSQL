/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.dpManagement.controller;

import com.mobinil.mcss.dp.dpManagement.model.CommissionCategory;
import com.mobinil.mcss.dp.dpManagement.model.DPStatus;
import com.mobinil.mcss.dp.dpManagement.model.DrivingPlan;
import com.mobinil.mcss.dp.dpManagement.model.DrivingPlanHistory;
import com.mobinil.mcss.dp.dpManagement.model.GenChannel;
import com.mobinil.mcss.dp.dpManagement.model.GenDcmLevel;
import com.mobinil.mcss.dp.dpManagement.model.GenDcmPaymentLevel;
import com.mobinil.mcss.dp.dpManagement.model.PaymentType;
import com.mobinil.mcss.dp.dpManagement.service.DPService;
import com.mobinil.mcss.dp.factor.model.Factor;
import com.mobinil.mcss.dp.factor.model.FactorValue;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Gado
 */
@Controller
@RequestMapping("/dp/managment")
public class DPController {

    @Autowired
    private DPService dpService;

    @RequestMapping(value = "/DrivingPlanManagment.htm", method = RequestMethod.GET)
    public ModelAndView prepareListsOfTypes(@ModelAttribute("drivingPlan") DrivingPlan drivingPlan,
            BindingResult result, HttpServletRequest request) {


        String userId = request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
        HttpSession s = request.getSession();
        s.setAttribute(InterfaceKey.HASHMAP_KEY_USER_ID, userId);
        Map<String, Object> data = new HashMap<String, Object>();
        List<GenChannel> channels = dpService.getChannels();
        data.put(DPInterfaceKey.channelList, channels);
        List<GenDcmLevel> dcmLevels = dpService.getDcmLevels();
        data.put(DPInterfaceKey.dcmLevel, dcmLevels);
        List<GenDcmPaymentLevel> dcmPaymentLevels = dpService.getDcmPayments();
        data.put(DPInterfaceKey.dcmPaymentLevel, dcmPaymentLevels);
        DPStatus status = new DPStatus();
        List<DPStatus> dpStatus = dpService.getDPStatus();
        int index = 0;
        for (int i = 0; i < dpStatus.size(); i++) {
            if (dpStatus.get(i).getStatusId() == 5) {
                index = i;
            }

        }
        dpStatus.remove(index);
        data.put(DPInterfaceKey.dpStatusList, dpStatus);
        List<PaymentType> paymentType = dpService.getPaymentTypes();
        data.put(DPInterfaceKey.dpPaymentTypeList, paymentType);
        List<CommissionCategory> CommissionCategory = dpService.getCommissionCategorys();
        data.put(DPInterfaceKey.dpCommissionCategoryList, CommissionCategory);
        data.put(DPInterfaceKey.monthsList, dpService.getMonthList());


        List<String> years = getYears();



        data.put(DPInterfaceKey.yearsList, years);

        return new ModelAndView("/dp/managment/DrivingPlanManagment", data);
    }

    public ModelAndView prepareListsOfTypes(@ModelAttribute("drivingPlan") DrivingPlan drivingPlan,
            BindingResult resultt) {




        Map<String, Object> data = new HashMap<String, Object>();
        List<GenChannel> channels = dpService.getChannels();
        data.put(DPInterfaceKey.channelList, channels);
        List<GenDcmLevel> dcmLevels = dpService.getDcmLevels();
        data.put(DPInterfaceKey.dcmLevel, dcmLevels);
        List<GenDcmPaymentLevel> dcmPaymentLevels = dpService.getDcmPayments();
        data.put(DPInterfaceKey.dcmPaymentLevel, dcmPaymentLevels);
        List<DPStatus> dpStatus = dpService.getDPStatus();
        int index = 0;
        for (int i = 0; i < dpStatus.size(); i++) {
            if (dpStatus.get(i).getStatusId() == 5) {
                index = i;
            }

        }
        dpStatus.remove(index);
        data.put(DPInterfaceKey.dpStatusList, dpStatus);
        List<PaymentType> paymentType = dpService.getPaymentTypes();
        data.put(DPInterfaceKey.dpPaymentTypeList, paymentType);
        List<CommissionCategory> CommissionCategory = dpService.getCommissionCategorys();
        data.put(DPInterfaceKey.dpCommissionCategoryList, CommissionCategory);


        data.put(DPInterfaceKey.monthsList, dpService.getMonthList());


        List<String> years = getYears();

        data.put(DPInterfaceKey.yearsList, years);
        return new ModelAndView("/dp/managment/DrivingPlanManagment", data);
    }

    @RequestMapping(value = "/DrivingPlanNew", method = RequestMethod.GET)
    public ModelAndView prepareListsOfTypesForNew(@ModelAttribute("drivingPlan") DrivingPlan drivingPlan,
            BindingResult result, Map<String, Object> data) {
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        List<GenChannel> channels = dpService.getChannels();
        data.put(DPInterfaceKey.channelList, channels);
        List<GenDcmLevel> dcmLevels = dpService.getDcmLevels();
        data.put(DPInterfaceKey.dcmLevel, dcmLevels);
        List<GenDcmPaymentLevel> dcmPaymentLevels = dpService.getDcmPayments();
        data.put(DPInterfaceKey.dcmPaymentLevel, dcmPaymentLevels);
        List<PaymentType> paymentType = dpService.getPaymentTypes();
        data.put(DPInterfaceKey.dpPaymentTypeList, paymentType);
        List<CommissionCategory> CommissionCategory = dpService.getCommissionCategorys();
        data.put(DPInterfaceKey.dpCommissionCategoryList, CommissionCategory);
        data.put(DPInterfaceKey.monthsList, dpService.getMonthList());
        List<String> years = getYears();

        data.put(DPInterfaceKey.yearsList, years);
        return new ModelAndView("/dp/managment/DrivingPlanNew", data);
    }

    @RequestMapping(value = "/copy", method = RequestMethod.GET)
    public ModelAndView copy(HttpServletRequest request) {

        Map<String, Object> data = new HashMap<String, Object>();
        List<GenChannel> channels = dpService.getChannels();
        data.put(DPInterfaceKey.channelList, channels);
        List<GenDcmLevel> dcmLevels = dpService.getDcmLevels();
        data.put(DPInterfaceKey.dcmLevel, dcmLevels);
        List<GenDcmPaymentLevel> dcmPaymentLevels = dpService.getDcmPayments();
        data.put(DPInterfaceKey.dcmPaymentLevel, dcmPaymentLevels);
        List<PaymentType> paymentType = dpService.getPaymentTypes();
        data.put(DPInterfaceKey.dpPaymentTypeList, paymentType);
        List<CommissionCategory> CommissionCategory = dpService.getCommissionCategorys();
        data.put(DPInterfaceKey.dpCommissionCategoryList, CommissionCategory);
        data.put(DPInterfaceKey.monthsList, dpService.getMonthList());
        List<String> years = getYears();

        data.put(DPInterfaceKey.yearsList, years);
        String dpId = request.getParameter("hiddenDpId");
        String editFlag = request.getParameter("editflag");

        DrivingPlan dp = dpService.getDPById(Long.parseLong(dpId));
        if (editFlag.equals("0")) {
            dp.setDpId(0);
            data.put("drivingPlan", dp);
            data.put("CopyDpId", dpId);
        } else {
            data.put("drivingPlan", dp);
        }
        return new ModelAndView("/dp/managment/DrivingPlanNew", data);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(HttpServletRequest request) {

        String dpId = request.getParameter("hiddenDpId");


        dpService.deleteDP(Long.parseLong(dpId));
        DrivingPlan dp = new DrivingPlan();

        ModelAndView mav = new ModelAndView();
        mav.addObject("cause", "Driving plan was Deleted");
        String direct = "/dp/managment/Result";
        mav.setViewName(direct);
        return mav;

    }

    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    public ModelAndView sendingApprove(HttpServletRequest request) {

        String dpId = request.getParameter("hiddenDpId");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);

        boolean isApproved = dpService.sendingApprove(Long.parseLong(dpId), userId);

        ModelAndView mav = new ModelAndView();

        if (isApproved) {
            mav.addObject("cause", "Driving plan status is pending approved");
        } else {
            mav.addObject("cause", "Driving plan must be approved by same user created it.");

        }
        String direct = "/dp/managment/Result";
        mav.setViewName(direct);
        return mav;

    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@ModelAttribute(value = "drivingPlan") DrivingPlan search, BindingResult result) throws Exception {

        Set<DrivingPlan> results = dpService.search(search);
        ModelAndView mv = prepareListsOfTypes(search, result);

        mv.addObject("results", results);
        if (!results.isEmpty()) {
            mv.addObject("MSG", "");
        } else {
            mv.addObject("MSG", "No Result");
        }


        return mv;

    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(@Valid DrivingPlan drivingPlan, BindingResult result, HttpServletRequest request) {

        if (drivingPlan.getPlanName().equals("")) {
            result.rejectValue("planName", "NotEmpty.drivingPlan.planName", "");
        }
        if (drivingPlan.getId().getChannel().getChannelId() == Long.valueOf(0)) {
            result.rejectValue("id.channel.channelId", "NotEmpty.drivingPlan.channel.channelId", "");
        }
        if (drivingPlan.getId().getDcmPaymentLevel().getIdPaymentLevel() == Long.valueOf(0)) {
            result.rejectValue("id.dcmPaymentLevel.idPaymentLevel", "NotEmpty.drivingPlan.dcmPaymentLevel.idPaymentLevel", "");
        }
        if (drivingPlan.getId().getDcmPaymentLevel().getIdPaymentLevel() == Long.valueOf(0)) {
            result.rejectValue("id.dcmLevel.idLevel", "NotEmpty.drivingPlan.dcmLevel.idLevel", "");
        }
        if (drivingPlan.getId().getDPMonth().equals("0")) {
            result.rejectValue("id.DPMonth", "NotEmpty.drivingPlan.DPMonth", "");
        }
        if (drivingPlan.getId().getDPYear().equals("0")) {
            result.rejectValue("id.DPYear", "NotEmpty.drivingPlan.DPYear", "");
        }
        if (drivingPlan.getCommissionCategory().getCategoryId() == Long.valueOf(0)) {
            result.rejectValue("commissionCategory.categoryId", "NotEmpty.commissionCategory.categoryId", "");
        }
        String copyId = request.getParameter("CopyDpId");


        if (result.hasErrors()) {
            Map<String, Object> data = new HashMap<String, Object>();
            return prepareListsOfTypesForNew(drivingPlan, result, data);
//        
        } else {
            DPStatus p = new DPStatus();
            p.setStatusId(1);
            HttpSession session = request.getSession();
            String userId = (String) session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
            drivingPlan.setPlanStatus(p);
            drivingPlan.setUser(userId);
            ModelAndView mav = new ModelAndView();
            String direct;
            try {
                if (drivingPlan.getDpId() == 0) {
                    if (!copyId.equals("") && copyId != null) {
                        DrivingPlan dp = dpService.getDPById(Long.parseLong(copyId));
                        Set<Factor> factorList = dp.getFactors();
                        for (Factor singleFactor : factorList) {
                            singleFactor.setFactorId(null);
                            singleFactor.setDrivingPlan(drivingPlan);
                            List<FactorValue> values = singleFactor.getFactorValues();
                            for (FactorValue v : values) {
                                v.setFactorValueId(null);
                                v.setFactor(singleFactor);
                            }
                            singleFactor.setFactorValues(values);
                        }
                        drivingPlan.setFactors(factorList);
                        dpService.saveDP(drivingPlan);

                    } else {
                        dpService.saveDP(drivingPlan);
                    }
                    mav.addObject("cause", "Your data has been inserted sucessefully");
                } else {
                    dpService.updateDP(drivingPlan);
                    mav.addObject("cause", "Your data has been updated sucessefully");
                }

            } catch (Exception ex) {
                String cause = ex.getMessage();
                System.out.println(cause);
                if (cause.contains("unique")) {

                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("MSG", "Error : insert dublicated data");
                    data.put("CopyDpId", copyId);
                    drivingPlan.setDpId(0);
                    return prepareListsOfTypesForNew(drivingPlan, result, data);
                }
            }
            direct = "/dp/managment/Result";
            mav.setViewName(direct);

            return mav;
        }

    }

    @RequestMapping(value = "/ApproveDrivingPlan", method = RequestMethod.GET)
    public ModelAndView ApproveManagement(@ModelAttribute("drivingPlan") DrivingPlan drivingPlan,
            BindingResult result, HttpServletRequest request) {
        String userId = (String) request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
        HttpSession s = request.getSession();
        s.setAttribute(InterfaceKey.HASHMAP_KEY_USER_ID, userId);
        System.out.println("Gado1:" + userId);
        List<DrivingPlan> results = dpService.getApprovedDrivingPlan();
        ModelAndView mv = new ModelAndView("/dp/managment/ApproveDrivingPlan");
        mv.addObject("results", results);
        if (!results.isEmpty()) {
            mv.addObject("MSG", "");
        } else {
            mv.addObject("MSG", "No Result");
        }


        return mv;
    }

    @RequestMapping(value = "/fapprove", method = RequestMethod.GET)
    public ModelAndView FinalApprove(HttpServletRequest request) {
        String dpId = request.getParameter("hiddenDpId");
        String comment = request.getParameter("comment");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
        String operation = dpService.approveDrivingPlan(Long.valueOf(dpId), userId, comment);
        ModelAndView mav = new ModelAndView();
        if (operation.equals("done")) {
            dpService.distributeFactorsValues(Long.valueOf(dpId));
            mav.addObject("cause", "Driving plan  is  approved");
        } else if (operation.equals("user")) {
            mav.addObject("cause", "This is same user");
        }
        String direct = "/dp/managment/Resultforapprove";
        mav.setViewName(direct);
        return mav;
    }

    @RequestMapping(value = "/refuse", method = RequestMethod.GET)
    public ModelAndView refuse(HttpServletRequest request) {
        String dpId = request.getParameter("hiddenDpId");
        String comment = request.getParameter("comment");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
        String operation = dpService.refuseDrivingPlan(Long.valueOf(dpId), userId, comment);
        ModelAndView mav = new ModelAndView();
        if (operation.equals("done")) {
            mav.addObject("cause", "Driving plan  is  Refused");
        } else if (operation.equals("user")) {
            mav.addObject("cause", "This is same user");
        }
        String direct = "/dp/managment/Resultforapprove";
        mav.setViewName(direct);
        return mav;
    }

    @RequestMapping(value = "/dpDelete", method = RequestMethod.GET)
    public ModelAndView dpDelete(HttpServletRequest request) {
        String dpId = request.getParameter("hiddenDpId");
        //   HttpSession session = request.getSession();
        // String userId = (String) session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
        dpService.deleteDP(Long.parseLong(dpId));
        ModelAndView mav = new ModelAndView();
        mav.addObject("cause", "Driving plan was Deleted");
        String direct = "/dp/managment/Resultforapprove";
        mav.setViewName(direct);
        return mav;
    }

    @RequestMapping(value = "/factor", method = RequestMethod.GET)
    public ModelAndView manageFactor(HttpServletRequest request) {
        String dpId = (String) request.getParameter("hiddenDpId");
        RedirectView rv = new RedirectView("../../dp/factor/factors.htm?dpid=" + dpId);
        ModelAndView mv = new ModelAndView(rv);
        mv.addObject("request", request);
        return mv;
    }

    @RequestMapping(value = "/viewfactors", method = RequestMethod.GET)
    public ModelAndView viewFactor(HttpServletRequest request) {
        String dpId = (String) request.getParameter("hiddenDpId");
        System.out.println("Gadooooooooo :  " + dpId);
        RedirectView rv = new RedirectView("../../dp/factor/viewfactor.htm?dpid=" + dpId);
        ModelAndView mv = new ModelAndView(rv);
        mv.addObject("request", request);
        return mv;
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ModelAndView history(HttpServletRequest request) {

        String dpId = request.getParameter("hiddenDpId");
        List<DrivingPlanHistory> histories = dpService.viewHistory(Long.valueOf(dpId));
        ModelAndView mav = new ModelAndView();
        mav.addObject("histories", histories);
        String direct = "/dp/managment/DrivingPlanCommentsHistory";
        mav.setViewName(direct);
        return mav;

    }

    private List<String> getYears() {
        List<String> years = new ArrayList<String>();
        for (Integer i = 2000; i <= 2019; i++) {
            years.add(i.toString());
        }
        return years;
    }
}

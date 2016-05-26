/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.zone.controller;

import com.mobinil.mcss.legacy_models.DcmRegion;
import com.mobinil.mcss.legacy_models.GenDcm;
import com.mobinil.mcss.zone.model.DcmRegionModel;
import com.mobinil.mcss.zone.model.Governorate;
import com.mobinil.mcss.zone.model.Zone;

import com.mobinil.mcss.zone.service.ZoneService;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gado
 */
@Controller
@RequestMapping("/zone")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;
    final String NEW_MODE = "0";
    final String EDIT_MODE = "1";

    @RequestMapping(value = "/ZoneManagement")
    public ModelAndView viewZones() {
        List zones = zoneService.getAllZones();
        ModelAndView mv = new ModelAndView("/zone/ZoneManagement");
        mv.addObject("zones", zones);
        return mv;
    }

    @RequestMapping(value = "/new")
    public ModelAndView newZone(@ModelAttribute("zone") Zone zone,
            BindingResult result, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("/zone/NewZone");

        return mv;
    }

    @RequestMapping(value = "/add")
    public ModelAndView addZone(@ModelAttribute("zone") Zone zone,
            BindingResult result, HttpServletRequest request) {
        if (zone.getZoneName().equals("")) {
            result.rejectValue("zoneName", "NotEmpty.Zone.zoneName", "");
        }
        printErrors(result);
        if (result.hasErrors()) {
            zone = new Zone();
            return newZone(zone, result, request);
        }
        zone.setZoneCode(null);
        zoneService.addZone(zone);

        return viewZones();
    }

    @RequestMapping(value = "/assigngover")
    public ModelAndView assigngover(HttpServletRequest request) {
        String zoneCode = (String) request.getParameter("hiddendl");
        Zone zone = new Zone();
        zone.setZoneCode(Long.parseLong(zoneCode));
        String zoneName = zoneService.getZoneName(Long.parseLong(zoneCode));
        ModelAndView mv = new ModelAndView("/zone/uploadGovernorate");
        mv.addObject("zonecode", zoneCode);
        mv.addObject("zone", zone);
        mv.addObject("zoneName", zoneName);
        return mv;

    }

    @RequestMapping(value = "/addgover", method = RequestMethod.POST)
    public ModelAndView addgover(HttpServletRequest request) {
        String govercode = request.getParameter("govercode");
        String zoneCode = request.getParameter("zonecode");
        Zone zone = new Zone();
        zone.setZoneCode(Long.parseLong(zoneCode));
        boolean assgined = zoneService.assginGovernrateToZone(govercode, zone);
        ModelAndView mv = new ModelAndView("/zone/uploadGovernorate");
        if (assgined) {
            mv.addObject("zonecode",zoneCode);
            mv.addObject("msg", "Governorate assgined");
        } else {
            mv.addObject("zonecode",zoneCode);
            mv.addObject("msg", "Governorate Code error");
        }
        return mv;

    }

    @RequestMapping(value = "/getgover", method = RequestMethod.GET)
    public ModelAndView getgover(HttpServletRequest request) {
        String zoneCode = request.getParameter("hiddendl");

        List<Governorate> govers = zoneService.getZoneGovernorate(Long.parseLong(zoneCode));
        ModelAndView mv = new ModelAndView("/zone/ZoneGovernrate");
        String zoneName = zoneService.getZoneName(Long.parseLong(zoneCode));
        mv.addObject("zoneName", zoneName);
        mv.addObject("govers", govers);
        mv.addObject("zonecode", zoneCode);
        if (govers.isEmpty()) {
            mv.addObject("MSG", "There's No Governorates In this Zone");
        }

        return mv;

    }

    @RequestMapping(value = "/delgover", method = RequestMethod.GET)
    public ModelAndView unassigngover(HttpServletRequest request) {
        String zoneCode = request.getParameter("zonecode");
        long goverId = Long.valueOf(request.getParameter("hiddendl"));
        zoneService.unassignGovernorate(Long.parseLong(zoneCode), goverId);
        List<Governorate> govers = zoneService.getZoneGovernorate(Long.parseLong(zoneCode));
        ModelAndView mv = new ModelAndView("/zone/ZoneGovernrate");
        String zoneName = zoneService.getZoneName(Long.parseLong(zoneCode));
        mv.addObject("zoneName", zoneName);
        mv.addObject("govers", govers);
        if (govers.isEmpty()) {
            mv.addObject("MSG", "There's No Governorates In this Zone");
        }
        return mv;

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteZone(HttpServletRequest request) {
        String zoneCode = request.getParameter("hiddendl");
        boolean deleted = zoneService.deleteZone(Long.parseLong(zoneCode));
        ModelAndView mv = new ModelAndView("/zone/ZoneManagement");
        List zones = zoneService.getAllZones();
        if (deleted) {
            mv.addObject("MSG", "Zone Deleted succesfull");
        } else {
            mv.addObject("MSG", "Cannot Delete This Zone");
        }


        mv.addObject("zones", zones);

        return mv;



    }

    @RequestMapping(value = "/saveDCM", method = RequestMethod.GET)
    public ModelAndView saveDCM(@ModelAttribute("DcmRegionModel") DcmRegionModel dcmRegionModel,
            BindingResult result, HttpServletRequest request) {        
        
        String strIsNewMode = request.getParameter("isNewMode");
        if (!dcmRegionModel.checkModelValue())
        {
        return validateDCM(dcmRegionModel,strIsNewMode);
        }        
        String[] regionsFromRequest = dcmRegionModel.getSelectedRegionsId() == null
                ? new String[0] : dcmRegionModel.getSelectedRegionsId().split("\\,");        
        zoneService.openSession();
        if (strIsNewMode.compareTo("true") == 0) {
            zoneService.deleteOldDCMRegion(new DcmRegionModel(String.valueOf(dcmRegionModel.getDcmId()), String.valueOf(dcmRegionModel.getDcmId())));
        }
        for (String regionId : regionsFromRequest) {
            zoneService.addDCMToRegion(new DcmRegionModel(String.valueOf(dcmRegionModel.getDcmId()), regionId));
        }
        zoneService.closeSession();
        return getDCMRegions();
    }

    @RequestMapping(value = "/addEditDCMRegion", method = RequestMethod.GET)
    public ModelAndView addEditDCM(@ModelAttribute("DcmRegionModel") DcmRegionModel dcmRegionModel,
            BindingResult result, HttpServletRequest request) {        
        Map<String, Object> model = new HashMap<String, Object>();
        String dcmId = request.getParameter("dcmIdHidden");
        String title = dcmId == null || dcmId.compareTo("") == 0 ? "New DCM" : "Edit DCM";
        String strIsNewMode = dcmId == null || dcmId.compareTo("") == 0 ? NEW_MODE : EDIT_MODE;
        model.put("title", title);
        model.put("isNewMode", strIsNewMode);
        zoneService.openSession();                
        model.put("isNewMode", "false");        
        List<DcmRegion> regions = zoneService.getRegions();        
        List<GenDcm> dcms = zoneService.getAvailableDCMS();
        if (strIsNewMode.equals(EDIT_MODE)) {
            List<DcmRegionModel> oneDCM = zoneService.getDCMRegions(dcmId);
            for (DcmRegionModel dcmRegionModel1 : oneDCM) {
                dcmRegionModel.setDcmId(dcmRegionModel1.getDcmId());
                for (DcmRegion dcmRegion : regions) {
                    if (dcmRegionModel1.getRegionId() == dcmRegion.getRegionId().longValue()) {
                        regions.remove(dcmRegion);
                        dcmRegion.setStrIsSelected("true");
                        regions.add(dcmRegion);
                        break;

                    }
                }
            }
            model.put("isNewMode", "true");
            model.put("DcmRegionModel", dcmRegionModel);
            dcms = zoneService.getAllDCMS();
        }   
        model.put("regionsValues", regions);
        model.put("dcmValues", dcms);
        zoneService.closeSession();
        return new ModelAndView("/zone/addEditDCMRegion", model);
    }
    
    private ModelAndView validateDCM(DcmRegionModel dcmRegionModel,String strIsNewMode){
    Map<String, Object> model = new HashMap<String, Object>();
    if (dcmRegionModel.getDcmId()==0)
    {
//        System.out.println("dcmRegionModel.getDcmId() is "+dcmRegionModel.getDcmId());
    model.put("msgDCM", "Please Select DCM.");
    }
    if (dcmRegionModel.getSelectedRegionsId()==null )
    {
//        System.out.println("dcmRegionModel.getSelectedRegionsId() is "+dcmRegionModel.getSelectedRegionsId());           
    model.put("msgRegion", "Please select at least one region.");
    }    
    zoneService.openSession();
    List<DcmRegion> regions = zoneService.getRegions();
    if (strIsNewMode.equals(EDIT_MODE))
        model.put("dcmValues", zoneService.getAllDCMS());
    else
        model.put("dcmValues", zoneService.getAvailableDCMS());
    
    zoneService.closeSession();
    model.put("regionsValues", regions);
    return new ModelAndView("/zone/addEditDCMRegion", model);  
    }

    @RequestMapping(value = "/deletedcm", method = RequestMethod.GET)
    public ModelAndView deleteDCM(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        String dcmId = request.getParameter("dcmIdHidden");
        zoneService.openSession();
        zoneService.deleteOldDCMRegion(new DcmRegionModel(dcmId, dcmId));
        List<DcmRegionModel> allDCMRegions = zoneService.getDCMRegions();
        zoneService.closeSession();
        getDCMGroup(model, allDCMRegions);
        return new ModelAndView("/zone/assignDcmToRegion", model);
    }

    @RequestMapping(value = "/assignDcmToRegion", method = RequestMethod.GET)
    public ModelAndView getDCMRegions() {
        Map<String, Object> model = new HashMap<String, Object>();
        zoneService.openSession();
        List<DcmRegionModel> allDCMRegions = zoneService.getDCMRegions();
        zoneService.closeSession();
        getDCMGroup(model, allDCMRegions);
        return new ModelAndView("/zone/assignDcmToRegion", model);
    }

    private void getDCMGroup(Map<String, Object> model, List<DcmRegionModel> allDCMRegions) {
        Map<Long, List<DcmRegionModel>> groupOfDCMWithRegions = new HashMap<Long, List<DcmRegionModel>>();
        for (DcmRegionModel dcmRegionModel : allDCMRegions) {
            if (groupOfDCMWithRegions.get(dcmRegionModel.getDcmId()) == null) {
                groupOfDCMWithRegions.put(dcmRegionModel.getDcmId(), getDCMRegionByID(allDCMRegions, dcmRegionModel));
            }
        }
        model.put("groupOfDCMWithRegions", groupOfDCMWithRegions);
    }

    private List<DcmRegionModel> getDCMRegionByID(List<DcmRegionModel> allDCMRegions, DcmRegionModel filterModel) {
        List<DcmRegionModel> listOfFilteredDCM = new ArrayList<DcmRegionModel>();
        for (DcmRegionModel dcmRegionModel : allDCMRegions) {
            if (dcmRegionModel.equalByDCM(filterModel)) {
                listOfFilteredDCM.add(dcmRegionModel);
            }
        }
        return listOfFilteredDCM;

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
}

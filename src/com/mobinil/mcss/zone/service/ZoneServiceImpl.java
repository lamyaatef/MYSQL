/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.zone.service;

import com.mobinil.mcss.legacy_models.DcmRegion;
import com.mobinil.mcss.legacy_models.GenDcm;
import com.mobinil.mcss.zone.dao.ZoneDAO;
import com.mobinil.mcss.zone.model.DcmRegionModel;
import com.mobinil.mcss.zone.model.Governorate;
import com.mobinil.mcss.zone.model.Zone;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gado
 */
@Service("ZoneService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ZoneServiceImpl implements ZoneService{
    
    @Autowired
    private ZoneDAO zonedao;
    
    
    public ZoneServiceImpl(ZoneDAO zoneDao) {
        this.zonedao = zoneDao;
    }
    public ZoneServiceImpl() {
        
    }
    public List<Zone> getAllZones() {
       return zonedao.getAllZones();
    }

    public boolean addZone(Zone zone) {
        return zonedao.addZone(zone);
    }

 

    public void editZone(String name, Zone zone) {
        zonedao.editZone(name, zone);
    }


    public String getZoneName(Long zoneCode) {
      return zonedao.getZoneName(zoneCode);
    }

    public boolean assginGovernrateToZone(String governratecode, Zone zone) {
        return zonedao.assginGovernrateToZone(governratecode, zone);
    }

    public List<Governorate> getZoneGovernorate(Long zoneCode) {
      return zonedao.getZoneGovernorate(zoneCode);
    }

    public void unassignGovernorate(Long zoneCode, long goverId) {
         zonedao.unassignGovernorate(zoneCode, goverId);
    }

    public boolean deleteZone(Long zoneCode) {
       return zonedao.deleteZone(zoneCode);
    }

    @Override
    public List<DcmRegionModel> getDCMRegions() {
//        List<DcmRegionModel> allRegions = zonedao.getDCMRegions();
        return zonedao.getDCMRegions();
    }

     @Override
    public boolean checkDcmExistsInRegion(DcmRegionModel dcmRegionModel) {
        boolean returnValue = false;       
//        for (DcmRegionModel dcmRegionModelOrig : listDCMSRegions) {
//            if (dcmRegionModel.equals(dcmRegionModelOrig))
//            {
//            returnValue = true;
//            break;
//            }   
//        }
        return returnValue;        
    }

    @Override
    public int addDCMToRegion(DcmRegionModel dcmRegionModel) {
        int insertResult = 1;
//        boolean isObjectDublicate = checkDcmExistsInRegion(dcmRegionModel);        
//        if (!isObjectDublicate)
//        {            
             boolean isObjectDublicate = zonedao.addNewDCMRegion(dcmRegionModel);
            
        
//        }
        return  isObjectDublicate ? insertResult : 0;
        
    }
   

    @Override
    public boolean editDCMRegion( DcmRegionModel newDcmRegionModel) {
        
//        boolean isObjectDublicate = checkDcmExistsInRegion(oldDcmRegionModel);
//        if (isObjectDublicate)
//        {
        return (zonedao.updateDCMRegion(newDcmRegionModel));
//        if (zonedao.updateDCMRegion(newDcmRegionModel)){    
//        listDCMSRegions.remove(oldDcmRegionModel);
//        listDCMSRegions.add(newDcmRegionModel); 
//        }
//        else isObjectDublicate = false;
//        }
//        
//        return  isObjectDublicate;
        
    }

    @Override
    public boolean deleteOldDCMRegion(DcmRegionModel dcmRegionModel) {        
//        boolean isObjectDublicate = checkDcmExistsInRegion(dcmRegionModel,listDCMSRegions);
//       if (isObjectDublicate)
//        {
            return zonedao.deleteDCMRegion(dcmRegionModel);
            
        
//        }
//       return  isObjectDublicate;
       
    }

    @Override
    public void openSession() {
       zonedao.openSession();
    }

    @Override
    public void closeSession() {
       zonedao.closeSession();
    }

    @Override
    public List<DcmRegionModel> getDCMRegions(String dcm_id) {
        return zonedao.getDCMRegions(dcm_id);
    }

    @Override
    public List<GenDcm> getAvailableDCMS() {
        return zonedao.getAvailableDCMS();
    }

    @Override
    public List<DcmRegion> getRegions() {
        return zonedao.getRegions();
    }
    @Override
    public List<DcmRegionModel> getDCMRegionsTest() {
        // get all dcms from db
        ArrayList<DcmRegionModel> allDCMSRegions = new ArrayList<DcmRegionModel>();
        allDCMSRegions.add(new DcmRegionModel("1", "20"));
        allDCMSRegions.add(new DcmRegionModel("1", "21"));
        allDCMSRegions.add(new DcmRegionModel("1", "22"));
        allDCMSRegions.add(new DcmRegionModel("1", "23"));
        allDCMSRegions.add(new DcmRegionModel("1", "24"));

        allDCMSRegions.add(new DcmRegionModel("2", "20"));
        allDCMSRegions.add(new DcmRegionModel("2", "21"));
        allDCMSRegions.add(new DcmRegionModel("2", "22"));
        allDCMSRegions.add(new DcmRegionModel("2", "23"));
        allDCMSRegions.add(new DcmRegionModel("2", "24"));


        return allDCMSRegions;

    }

    @Override
    public List<GenDcm> getAllDCMS() {
        return zonedao.getAllDCMS();
    }

   
    
       
}

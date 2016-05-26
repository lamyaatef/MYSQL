/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.zone.service;

import com.mobinil.mcss.legacy_models.DcmRegion;
import com.mobinil.mcss.legacy_models.GenDcm;
import com.mobinil.mcss.zone.model.DcmRegionModel;
import com.mobinil.mcss.zone.model.Governorate;
import com.mobinil.mcss.zone.model.Zone;
import java.util.List;

/**
 *
 * @author Gado
 */
public interface ZoneService {
    
   public List <Zone> getAllZones();
   public boolean addZone (Zone zone);
   public void editZone (String name , Zone zone);
   public String getZoneName (Long zoneCode);
   public boolean assginGovernrateToZone (String governratecode,Zone zone);
   public List<Governorate> getZoneGovernorate (Long zoneCode);
   public void unassignGovernorate (Long zoneCode,long goverId);
   public boolean deleteZone (Long zoneCode);
   public List<DcmRegionModel> getDCMRegions();
   public boolean checkDcmExistsInRegion(DcmRegionModel dcmRegionModel);
   public int addDCMToRegion(DcmRegionModel dcmRegionModel);    
   public boolean deleteOldDCMRegion(DcmRegionModel dcmRegionModel);    
   public boolean editDCMRegion(DcmRegionModel dcmRegionModel);    
   public List<GenDcm> getAvailableDCMS();
   public List<GenDcm> getAllDCMS();
   public List<DcmRegion> getRegions();
   public List<DcmRegionModel> getDCMRegions(String dcm_id); 
   public List<DcmRegionModel> getDCMRegionsTest() ;
   public void openSession() ;
   public void closeSession() ;
}

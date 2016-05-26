/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.zone.dao;

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
public interface ZoneDAO {
   public List <Zone> getAllZones();
   public boolean addZone (Zone zone);
  
   public void editZone (String name , Zone zone);
   public String getZoneName (Long zoneCode);
   public boolean assginGovernrateToZone (String governratecode,Zone zone);
   public List<Governorate> getZoneGovernorate (Long zoneCode);
   public void unassignGovernorate (Long zoneCode,long goverId);
   public boolean deleteZone (Long zoneCode);
   public Boolean addNewDCMRegion(DcmRegionModel dcmRegionModel);
    public Boolean deleteDCMRegion(DcmRegionModel dcmRegionModel);
    public Boolean updateDCMRegion(DcmRegionModel dcmRegionModel);
    public List<DcmRegionModel> getDCMRegions() ;
   public List<GenDcm> getAvailableDCMS();
   public List<GenDcm> getAllDCMS();
   public List<DcmRegion> getRegions();
   public List<DcmRegionModel> getDCMRegions(String dcm_id);    
    public void openSession() ;
    public void closeSession() ;
}

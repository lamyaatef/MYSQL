/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.zone.model;
import com.mobinil.mcss.legacy_models.DcmRegion;
import com.mobinil.mcss.legacy_models.GenDcm;
import java.util.List;
import java.util.Set;
import javax.persistence.*;


/**
 *
 * @author mabdelaal
 */
@Entity
@Table(name="DISTRIBUTES_REGIONS")
public class DcmRegionModel {
    @Id    
    @Column(name="DISTRIBUTES_REGIONS_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISTRIBUTES_REGIONS_ID")
    @SequenceGenerator(name = "DISTRIBUTES_REGIONS_ID", sequenceName = "COMMISSION_DIST_REGIONS_ID_SEQ", allocationSize = 1)
    private Long dist_reg_id;
    
    @OneToOne
    @JoinColumn(name="DCM_ID",insertable=false,updatable=false)
    private GenDcm genDcm;
    
    @OneToOne
    @JoinColumn(name="REGION_ID",insertable=false,updatable=false)
    private DcmRegion dcmRegion;
    
    @Transient    
    private String selectedRegionsId;
    
    @Column(name="DCM_ID")
    private Long dcmId;
    @Column(name="REGION_ID")
    private Long regionId;

    public DcmRegionModel(String dcmId, String regionId) {
        this.dcmId = Long.parseLong(dcmId);
        this.regionId = Long.parseLong(regionId);
    }

    public DcmRegionModel(Long dist_reg_id) {
        this.dist_reg_id = dist_reg_id;
    }

    public DcmRegionModel() {
    }
    
    public boolean equalByDCM(Object obj){
    if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DcmRegionModel other = (DcmRegionModel) obj;
        if ((this.dcmId == null) ? (other.dcmId != null) : !this.dcmId.equals(other.dcmId)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DcmRegionModel other = (DcmRegionModel) obj;
        if ((this.dcmId == null) ? (other.dcmId != null) : !this.dcmId.equals(other.dcmId)) {
            return false;
        }
        if ((this.regionId == null) ? (other.regionId != null) : !this.regionId.equals(other.regionId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (this.dcmId != null ? this.dcmId.hashCode() : 0);
        hash = 73 * hash + (this.regionId != null ? this.regionId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "DcmRegionModel{" + "dist_reg_id=" + dist_reg_id + ", genDcm=" + genDcm + ", dcmRegion=" + dcmRegion + ", dcmId=" + dcmId + ", regionId=" + regionId + '}';
    }

    public boolean checkModelValue(){
        boolean result = true;
//        System.out.println("getDcmId is "+getDcmId());
//        System.out.println("getSelectedRegionsId is "+getSelectedRegionsId());
    if (getDcmId()==0)
    {
    result = false;
    }
    if (getSelectedRegionsId()==null)
    {
    result = false;
    }    
    return result;
    }
    

    /**
     * @return the dcmId
     */
    public Long getDcmId() {
        return dcmId;
    }

    /**
     * @param dcmId the dcmId to set
     */
    public void setDcmId(Long dcmId) {
        this.dcmId = dcmId;
    }

    /**
     * @return the regionId
     */
    public Long getRegionId() {
        return regionId;
    }

    /**
     * @param regionId the regionId to set
     */
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    /**
     * @return the dist_reg_id
     */
    public Long getDist_reg_id() {
        return dist_reg_id;
    }

    /**
     * @param dist_reg_id the dist_reg_id to set
     */
    public void setDist_reg_id(Long dist_reg_id) {
        this.dist_reg_id = dist_reg_id;
    }

    /**
     * @return the genDcm
     */
    public GenDcm getGenDcm() {
        return genDcm;
    }

    /**
     * @param genDcm the genDcm to set
     */
    public void setGenDcm(GenDcm genDcm) {
        this.genDcm = genDcm;
    }

    /**
     * @return the dcmRegion
     */
    public DcmRegion getDcmRegion() {
        return dcmRegion;
    }

    /**
     * @param dcmRegion the dcmRegion to set
     */
    public void setDcmRegion(DcmRegion dcmRegion) {
        this.dcmRegion = dcmRegion;
    }
    /**
     * @return the selectedRegionsId
     */
    public String getSelectedRegionsId() {
        return selectedRegionsId;
    }

    /**
     * @param selectedRegionsId the selectedRegionsId to set
     */
    public void setSelectedRegionsId(String selectedRegionsId) {
        this.selectedRegionsId = selectedRegionsId;
    }
    
}

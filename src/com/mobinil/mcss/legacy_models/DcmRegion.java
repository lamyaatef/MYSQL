/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.legacy_models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mabdelaal
 */
@Entity
@Table(name = "DCM_REGION")
@NamedQueries({
    @NamedQuery(name = "DcmRegion.findAll", query = "SELECT d FROM DcmRegion d")})
public class DcmRegion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REGION_ID")
    private BigDecimal regionId;
    @Size(max = 128)
    @Column(name = "REGION_NAME")
    private String regionName;
    @Column(name = "REGION_STATUS_TYPE_ID")
    private BigInteger regionStatusTypeId;
    @Column(name = "PARENT_REGION_ID")
    private BigInteger parentRegionId;
    @Column(name = "REGION_LEVEL_TYPE_ID")
    private BigInteger regionLevelTypeId;
    @Size(max = 10)
    @Column(name = "REGION_CODE")
    private String regionCode;
    @Size(max = 50)
    @Column(name = "CAMPAS_CODE")
    private String campasCode;
    @Size(max = 512)
    @Column(name = "ARABIC_NAME")
    private String arabicName;
    @Transient
    private String strIsSelected;

    public DcmRegion() {
    }

    public DcmRegion(BigDecimal regionId) {
        this.regionId = regionId;
    }

    public BigDecimal getRegionId() {
        return regionId;
    }

    public void setRegionId(BigDecimal regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public BigInteger getRegionStatusTypeId() {
        return regionStatusTypeId;
    }

    public void setRegionStatusTypeId(BigInteger regionStatusTypeId) {
        this.regionStatusTypeId = regionStatusTypeId;
    }

    public BigInteger getParentRegionId() {
        return parentRegionId;
    }

    public void setParentRegionId(BigInteger parentRegionId) {
        this.parentRegionId = parentRegionId;
    }

    public BigInteger getRegionLevelTypeId() {
        return regionLevelTypeId;
    }

    public void setRegionLevelTypeId(BigInteger regionLevelTypeId) {
        this.regionLevelTypeId = regionLevelTypeId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCampasCode() {
        return campasCode;
    }

    public void setCampasCode(String campasCode) {
        this.campasCode = campasCode;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regionId != null ? regionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DcmRegion)) {
            return false;
        }
        DcmRegion other = (DcmRegion) object;
        if ((this.regionId == null && other.regionId != null) || (this.regionId != null && !this.regionId.equals(other.regionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mobinil.mcss.legacy_models.DcmRegion[ regionId=" + regionId + " ]";
    }

    /**
     * @return the strIsSelected
     */
    public String getStrIsSelected() {
        return strIsSelected;
    }

    /**
     * @param strIsSelected the strIsSelected to set
     */
    public void setStrIsSelected(String strIsSelected) {
        this.strIsSelected = strIsSelected;
    }
    
}

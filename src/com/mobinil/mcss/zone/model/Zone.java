/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.zone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author Gado
 */
@Entity
@Table(name="commission_zone")
public class Zone {
   
    @Id
    @Column(name="zone_code")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zone_seq")
    @SequenceGenerator(name = "zone_seq", sequenceName = "zone_seq", allocationSize = 1)
    private Long zoneCode ;
    @Column(name="zone_name")
    private String zoneName;
   

    /**
     * @return the zoneCode
     */
    public Long getZoneCode() {
        return zoneCode;
    }

    /**
     * @param zoneCode the zoneCode to set
     */
    public void setZoneCode(Long zoneCode) {
        this.zoneCode = zoneCode;
    }

    /**
     * @return the zoneName
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * @param zoneName the zoneName to set
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }


    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.zone.model;

import com.mobinil.mcss.spring.util.GExcel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Gado
 */
@Entity
@Table(name="commssion_zone_governorate")
public class ZoneGovernorate {
    @Id
    @Column(name="zone_gover_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zone_governorate")
    @SequenceGenerator(name = "zone_governorate", sequenceName = "zone_governorate", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name="ZONE_CODE")
     private Zone zone;      
    @GExcel(excelColumn=0)
    @Column (name="GOVERNRATE_ID")
       private long governrate;

    /**
     * @return the zone
     */
    public Zone getZone() {
        return zone;
    }

    /**
     * @param zone the zone to set
     */
    public void setZone(Zone zone) {
        this.zone = zone;
    }

    /**
     * @return the governrate
     */
    public long getGovernrate() {
        return governrate;
    }

    /**
     * @param governrate the governrate to set
     */
    public void setGovernrate(long governrate) {
        this.governrate = governrate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

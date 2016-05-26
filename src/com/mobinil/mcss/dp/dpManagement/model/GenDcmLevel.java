/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.dpManagement.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Ahmed Adel
 */
@Entity
@Table(name="GEN_DCM_LEVEL")
public class GenDcmLevel implements Serializable{
    @Id
    @Column(name="DCM_LEVEL_ID")
    private long idLevel;
    
    @Column(name="DCM_LEVEL_NAME")
    private String levelName;

    public long getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(long id) {
        this.idLevel = id;
    }

    /**
     * @return the name
     */
    public String getlevelName() {
        return levelName;
    }

    /**
     * @param name the name to set
     */
    public void setlevelName(String name) {
        this.levelName = levelName;
    }
    
}

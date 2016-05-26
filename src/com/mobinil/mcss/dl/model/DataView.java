/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dl.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Gado
 */
@Entity
@Table(name="ADH_DATA_VIEW")
public class DataView implements Serializable{
    @Id
    @Column(name="data_view_id")
    private long dataViewId;
    @Column(name="data_view_name")
    private String dataviewName;

    /**
     * @return the dataViewId
     */
    public long getDataViewId() {
        return dataViewId;
    }

    /**
     * @param dataViewId the dataViewId to set
     */
    public void setDataViewId(long dataViewId) {
        this.dataViewId = dataViewId;
    }

    /**
     * @return the dataviewName
     */
    public String getDataviewName() {
        return dataviewName;
    }

    /**
     * @param dataviewName the dataviewName to set
     */
    public void setDataviewName(String dataviewName) {
        this.dataviewName = dataviewName;
    }
    
}

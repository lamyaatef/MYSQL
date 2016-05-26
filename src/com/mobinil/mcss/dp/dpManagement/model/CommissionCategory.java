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
 * @author Gado
 */
@Entity
@Table(name = "COMMISSION_TYPE_CATEGORY")
public class CommissionCategory implements Serializable {
    @Id
    @Column(name="COMMISSION_TYPE_CATEGORY_ID")
    private long categoryId;
    @Column(name="COMMISSION_TYPE_CATEGORY_NAME")
    private String categoryName;  
    @Column(name="COMMISSION_TYPE_ID")
    private int commissionType;        
    @Column(name="COMMISSION_TYPE_CATEGORY_DESC")
    private String desc;

    /**
     * @return the id
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * @param id the id to set
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param name the name to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the commissionType
     */
    public int getCommissionType() {
        return commissionType;
    }

    /**
     * @param commissionType the commissionType to set
     */
    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
}

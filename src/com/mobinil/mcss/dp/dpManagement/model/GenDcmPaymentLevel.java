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
@Table(name="GEN_DCM_PAYMENT_LEVEL")
public class GenDcmPaymentLevel implements Serializable{
    @Id
    @Column (name="DCM_PAYMENT_LEVEL_ID")
    private long idPaymentLevel;
    
    @Column (name="DCM_PAYMENT_LEVEL_NAME")
    private String namePaymentLevel;

    /**
     * @return the id
     */
    public long getIdPaymentLevel() {
        return idPaymentLevel;
    }

    /**
     * @param id the id to set
     */
    public void setIdPaymentLevel(long id) {
        this.idPaymentLevel = id;
    }

    /**
     * @return the name
     */
    public String getNamePaymentLevel() {
        return namePaymentLevel;
    }

    /**
     * @param name the name to set
     */
    public void setNamePaymentLevel(String namePaymentLevel) {
        this.namePaymentLevel = namePaymentLevel;
    }
    
    
    
}

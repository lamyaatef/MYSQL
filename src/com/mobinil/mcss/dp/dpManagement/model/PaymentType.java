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
@Table(name="PAYMENT_TYPE")
public class PaymentType implements Serializable{
    @Id
    @Column(name="PAYMENT_TYPE_ID")
    private long typeId;
    @Column(name="PAYMENT_TYPE_NAME")
    private String typeName;
    @Column(name="PAYMENT_TYPE_STATUS_ID")
    private int status;
    @Column(name="PAYMENT_METHOD_TYPE_ID")        
    private int methodType;
    @Column(name="PAYMENT_ACCRUAL_ID")
    private int accrualId;

    
  

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the methodType
     */
    public int getMethodType() {
        return methodType;
    }

    /**
     * @param methodType the methodType to set
     */
    public void setMethodType(int methodType) {
        this.methodType = methodType;
    }

    /**
     * @return the accrualId
     */
    public int getAccrualId() {
        return accrualId;
    }

    /**
     * @param accrualId the accrualId to set
     */
    public void setAccrualId(int accrualId) {
        this.accrualId = accrualId;
    }

    /**
     * @return the typeId
     */
    public long getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

   
}

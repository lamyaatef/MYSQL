/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.request.model;

/**
 *
 * @author sand
 */
public class PaymentMethodModel {
    private String paymentMethodName;
    private int paymentMethodId;

    /**
     * @return the paymentMethodName
     */
    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    /**
     * @param paymentMethodName the paymentMethodName to set
     */
    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    /**
     * @return the paymentMethodId
     */
    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    /**
     * @param paymentMethodId the paymentMethodId to set
     */
    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    
}

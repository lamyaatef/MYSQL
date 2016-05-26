/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.model;

/**
 *
 * @author Salma
 */
public class PaymentModel {

    private String paymentName;
    private int paymentId;

    /**
     * @return the paymentName
     */
    public String getPaymentName() {
        return paymentName;
    }

    /**
     * @param paymentName the paymentName to set
     */
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    /**
     * @return the paymentId
     */
    public int getPaymentId() {
        return paymentId;
    }

    /**
     * @param paymentId the paymentId to set
     */
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

}

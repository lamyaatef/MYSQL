/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.dao;

/**
 *
 * @author Brain
 */
public interface UpdateCreditAdviceDao {

    public void clearTempTableCRDetail();

    public void clearTempTableCRDetailWithSerial();

    public void updateRDetailWithId();

    public void updateRDetailWithSerial();
}

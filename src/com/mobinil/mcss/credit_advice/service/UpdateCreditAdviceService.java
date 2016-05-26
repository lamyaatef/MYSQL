/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.service;

/**
 *
 * @author Brain
 */
public interface UpdateCreditAdviceService {

    public void generateTemplate(String template, String file, String userId);

    public void clearTempTableCRDetail();

    public void clearTempTableCRDetailWithSerial();

    public void updateRDetailWithId();

    public void updateRDetailWithSerial();
}

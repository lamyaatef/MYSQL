/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.service;

import com.mobinil.mcss.credit_advice.dao.UpdateCreditAdviceDao;
import com.mobinil.mcss.credit_advice.dao.UpdateCreditAdviceDaoImpl;
import com.mobinil.sds.core.system.cr.excelImport.ExcelImport;

/**
 *
 * @author Brain
 */
public class UpdateCreditAdviceServiceImpl implements UpdateCreditAdviceService {

    UpdateCreditAdviceDao updateCreditAdviceDao = new UpdateCreditAdviceDaoImpl();

    @Override
    public void generateTemplate(String template, String file, String userId) {


        ExcelImport excelImport = new ExcelImport(userId);

        excelImport.exportCreditAdviceTemplate(template, file);

    }

    @Override
    public void clearTempTableCRDetail() {
        updateCreditAdviceDao.clearTempTableCRDetail();
    }

    @Override
    public void clearTempTableCRDetailWithSerial() {


        updateCreditAdviceDao.clearTempTableCRDetailWithSerial();
    }

    @Override
    public void updateRDetailWithId() {

        updateCreditAdviceDao.updateRDetailWithId();
    }

    @Override
    public void updateRDetailWithSerial() {
        updateCreditAdviceDao.updateRDetailWithSerial();
    }
}

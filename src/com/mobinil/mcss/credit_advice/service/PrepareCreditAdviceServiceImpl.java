/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.service;

import com.mobinil.mcss.credit_advice.dao.PrepareCreditAdviceDao;
import com.mobinil.mcss.credit_advice.dao.PrepareCreditAdviceDaoImpl;
import com.mobinil.mcss.credit_advice.model.CAFile;
import com.mobinil.mcss.credit_advice.model.CAMemo;
import com.mobinil.mcss.credit_advice.model.CamPayment;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Brain
 */
public class PrepareCreditAdviceServiceImpl implements PrepareCreditAdviceService {

    PrepareCreditAdviceDao prepareCreditAdviceDao = new PrepareCreditAdviceDaoImpl();

    
    @Override
    public List getAvailableMemos(Long memoID, String memoName, String dateFrom, String dateTo) {
        return prepareCreditAdviceDao.getAvailableMemos(memoID, memoName, dateFrom, dateTo);
    }
    public void prepareCA(List<CAMemo> memosList , String userID ){
        
    prepareCreditAdviceDao.prepareCA(memosList, userID) ;
    }
    public List reviewCrPayment(Long memoID) {

        List<CAMemo> camMemos = prepareCreditAdviceDao.reviewCrPayment(memoID);
        
        for (CAMemo cAMemo : camMemos) {
            Long deductionvalue = (Long) cAMemo.getDeductionsValue();
            Long commissionvalue = (Long) cAMemo.getScmCommissionValue();
            Long total = commissionvalue - deductionvalue;
            Float tax = (Float) cAMemo.getWithHoldTax();
            Float taxAmount = total * tax;
            Float totalAmount = total - taxAmount;
            cAMemo.setTotalPayment(totalAmount);

            
        }

        return camMemos;


    }
    /*public List reviewCrPayment(Long memoID) {
    
    List<CamMemo> camMemos = prepareCreditAdviceDao.reviewCrPayment(memoID);
    
    
    List<CamMemo> finalResult = new ArrayList<CamMemo>();
    
    for (CAMemo camMemo : camMemos) {
    
    Set<CamPayment> camPaymentSet = camMemo.getCamPayments();
    Float TotalAmount = 0F;
    for (CamPayment camPayment : camPaymentSet) {
    
    
    Long deductionvalue = (Long) camPayment.getDeductionsValue();
    Long commissionvalue = (Long) camPayment.getScmCommissionValue();
    Long total = commissionvalue - deductionvalue;
    Float tax = prepareCreditAdviceDao.getTax(camPayment.getGenDcm().getDcmCode());
    Float taxAmount = commissionvalue * tax;
    TotalAmount = total - taxAmount;
    
    
    }
    
    camMemo.setTotalPayment(TotalAmount);
    
    finalResult.add(camMemo);
    }
    
    return finalResult;
    
    
    }
    
    public void saveOrUpdate(CAFile cafile) {
    
    CAFile cafiles = new CAFile();
    
    
    } */
}

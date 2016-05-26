/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.service;

import com.mobinil.mcss.credit_advice.dao.GenerateCreditAdviceDao;
import com.mobinil.mcss.credit_advice.dao.GenerateCreditAdviceDaoImpl;
import com.mobinil.mcss.credit_advice.model.CAMemo;
import java.util.List;

/**
 *
 * @author sand
 */
public class GenerateCreditAdviceServiceImpl implements GenerateCreditAdviceService {

    GenerateCreditAdviceDao generateCreditAdviceDao = new GenerateCreditAdviceDaoImpl();

    @Override
    public List getPreparedCredits(Long memoID, String memoName, String dateFrom, String dateTo, Long fileID) {
        return generateCreditAdviceDao.getPreparedCredits(memoID, memoName, dateFrom, dateTo, fileID);
    }

    @Override
    public List viewCredits(Long fileID) {


        List<CAMemo> camMemos = generateCreditAdviceDao.viewCredits(fileID);

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

    @Override
    public void deleteCredits(Long fileId, String userID) {
        System.out.println("DELETE FILE SERVICE ");
        generateCreditAdviceDao.deleteCredits(fileId, userID);
    }

    @Override
    public void generateCAFile(String userID, List<Long> fileIds) {
        generateCreditAdviceDao.generateCAFile(userID, fileIds);
    }
}

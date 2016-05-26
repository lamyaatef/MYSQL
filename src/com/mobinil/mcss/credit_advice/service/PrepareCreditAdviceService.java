/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.service;

import com.mobinil.mcss.credit_advice.model.CAMemo;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Brain
 */
public interface PrepareCreditAdviceService {
    public List getAvailableMemos(Long memoID, String memoName, String dateFrom, String dateTo);
    public List reviewCrPayment(Long memoID);
    public void prepareCA(List<CAMemo> memosList , String userID );
   // public List reviewCrPayment(Long memoID);
}

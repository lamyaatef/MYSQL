/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.dao;

import com.mobinil.mcss.credit_advice.model.CAMemo;
import com.mobinil.mcss.credit_advice.model.StatusFile;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Brain
 */
public interface SearchCreditAdviceDao {

    public List getMemos(Long memoID, String memoName, Date dateFrom, Date dateTo, String scmCode, String scmName, String creditSerial, String amount, int creditStatus , Long creditBatchID);

    public List<StatusFile> fillStatus();
    
    
    public List getCreditAdvices(Long memoId,Long fileId);
    
     public void updateCAFilePrinting(Long fileId);

    public void updateCAPrinting(List<Long> caIdList);
}

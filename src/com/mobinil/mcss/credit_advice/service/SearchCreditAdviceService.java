/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.service;

import com.mobinil.mcss.credit_advice.model.CAMemo;
import com.mobinil.mcss.credit_advice.model.StatusFile;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Brain
 */
public interface SearchCreditAdviceService {

    public List getMemos(Long memoID, String memoName, Date dateFrom, Date dateTo, String scmCode, String scmName, String creditSerial, String amount, int creditStatus, Long creditBatchID);

    public List<StatusFile> fillStatus();

    public List getCreditAdvices(Long memoId, Long fileId);

    public String updateCAFilePrinting(Long fileId, String filePath);

    public String updateCAPrinting(List<Long> caIdList, String filePath);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.service;

import com.mobinil.mcss.credit_advice.dao.SearchCreditAdviceDao;
import com.mobinil.mcss.credit_advice.dao.SearchCreditAdviceDaoImpl;
import com.mobinil.mcss.credit_advice.model.CAMemo;
import com.mobinil.mcss.credit_advice.model.StatusFile;
import com.mobinil.mcss.credit_advice.util.DbReportFill;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brain
 */
public class SearchCreditAdviceServiceImpl implements SearchCreditAdviceService {

    SearchCreditAdviceDao searchCreditAdviceDao = new SearchCreditAdviceDaoImpl();

    @Override
    public List getMemos(Long memoID, String memoName, Date dateFrom, Date dateTo, String scmCode, String scmName, String creditSerial, String amount, int creditStatus, Long creditBatchID) {
        return searchCreditAdviceDao.getMemos(memoID, memoName, dateFrom, dateTo, scmCode, scmName, creditSerial, amount, creditStatus, creditBatchID);
    }

    @Override
    public List<StatusFile> fillStatus() {
        return searchCreditAdviceDao.fillStatus();
    }

    @Override
    public List getCreditAdvices(Long memoId, Long fileId) {
        return searchCreditAdviceDao.getCreditAdvices(memoId, fileId);
    }

    @Override
    public String updateCAFilePrinting(Long fileId, String filePath) {
        DbReportFill dbReportFill = new DbReportFill();

        String createdFile = "";
        try {
            try {
                createdFile = dbReportFill.generateCreditAdviceFile(fileId, filePath);
            } catch (IOException ex) {
                Logger.getLogger(SearchCreditAdviceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            searchCreditAdviceDao.updateCAFilePrinting(fileId);
        } catch (SQLException ex) {
            Logger.getLogger(SearchCreditAdviceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return createdFile;
    }

    @Override
    public String updateCAPrinting(List<Long> caIdList, String filePath) {
        DbReportFill dbReportFill = new DbReportFill();
        String createdFile = "";

        try {

            createdFile = dbReportFill.generateCreditAdvices(caIdList, filePath);
            searchCreditAdviceDao.updateCAPrinting(caIdList);
        } catch (SQLException ex) {
            Logger.getLogger(SearchCreditAdviceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return createdFile;

    }
}

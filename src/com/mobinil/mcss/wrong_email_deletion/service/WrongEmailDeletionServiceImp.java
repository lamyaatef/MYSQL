/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.wrong_email_deletion.service;

import com.mobinil.mcss.wrong_email_deletion.dao.WrongEmailDeletionDao;
import com.mobinil.mcss.wrong_email_deletion.dao.WrongEmailDeletionDaoImp;
import com.mobinil.mcss.wrong_email_deletion.model.WrongEmailDeletionModel;
import java.util.List;

/**
 *
 * @author SAND
 */
public class WrongEmailDeletionServiceImp implements WrongEmailDeletionService {
    
    WrongEmailDeletionDao wrongEmailDeletionDao = new WrongEmailDeletionDaoImp();
    
    @Override
    public List getEmails(String posCode, String date ,String dateTo){
    
        return wrongEmailDeletionDao.getEmails(posCode, date,dateTo);
    }
    
    @Override
    public void archiveAndDelete(List<WrongEmailDeletionModel> adEmailList, String reason){
      wrongEmailDeletionDao.archiveAndDelete(adEmailList, reason);
    }
}

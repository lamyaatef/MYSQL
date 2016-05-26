/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.wrong_email_deletion.service;

import com.mobinil.mcss.wrong_email_deletion.model.WrongEmailDeletionModel;
import java.util.List;

/**
 *
 * @author SAND
 */
public interface WrongEmailDeletionService {
    
     public List getEmails(String posCode, String date ,String dateTo);
     public void archiveAndDelete(List<WrongEmailDeletionModel> adEmailList, String reason);
    
}

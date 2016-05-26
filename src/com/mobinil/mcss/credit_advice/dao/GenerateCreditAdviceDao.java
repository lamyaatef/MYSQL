/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.dao;

import com.mobinil.mcss.credit_advice.model.CAMemo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author JOE
 */
public interface GenerateCreditAdviceDao {
    
   public List getPreparedCredits(Long memoID, String memoName, String dateFrom, String dateTo, Long fileID);
   public void generateCAFile(String userID,List<Long> fileIds);
   public List viewCredits(Long fileID);
   public void deleteCredits(Long fileId,String userID);
    
    
}
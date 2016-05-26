/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.service;

import java.util.List;

/**
 *
 * @author sand
 */
public interface GenerateCreditAdviceService {

    public List getPreparedCredits(Long memoID, String memoName, String dateFrom, String dateTo, Long fileID);

    public List viewCredits(Long fileID);

    public void deleteCredits(Long fileId, String userID);

    public void generateCAFile(String userID, List<Long> fileIds);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dl.service;

import com.mobinil.mcss.dl.dao.DCMListDao;
import com.mobinil.mcss.dl.excel.ExcelGenerator;
import com.mobinil.mcss.dl.model.DCMList;
import com.mobinil.mcss.dl.model.DCMListDetail;
import com.mobinil.mcss.dl.model.DCMListHistory;
import com.mobinil.mcss.dl.model.DataView;
import com.mobinil.mcss.dl.model.DcmListType;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mabdelaal
 */
@Service("DCMListService")
public class DCMListServiceImpl implements DCMListService {
        @Autowired
	private DCMListDao dcmListDao;
    @Override
    public List<DCMList> getLists() {
        return  getDcmListDao().getLists();
    }

    public DCMListDao getDcmListDao() {
        return dcmListDao;
    }

    public void setDcmListDao(DCMListDao dcmListDao) {
        this.dcmListDao = dcmListDao;
    }

    @Override
    public List<DCMList> searchLists(DCMList dcmList) {
        return dcmListDao.searchLists(dcmList);
    }

    @Override
    public String addList(DCMList dcmList) {
       return dcmListDao.addList(dcmList);
    }

    @Override
    public void editList(DCMList dcmList) {
        dcmListDao.editList(dcmList);
    }

    @Override
    public void changeStatusList(List<DCMList> dcmListArr) {
        dcmListDao.changeStatusList(dcmListArr);
    }
    
    @Override
    public void importDcmListDetail(List<DCMListDetail> dCMListDetail,long historyId) {
        dcmListDao.importDcmListDetail(dCMListDetail,historyId);
    }

    public List<DcmListType> getTypeList() {
       return dcmListDao.getTypeList();
    }

    public List<DataView> getAllDataView() {
        return dcmListDao.getAllDataView();
    }

    public DCMList getDcmListById(long dcmId) {
       return dcmListDao.getDcmListById(dcmId);
    }


    

    public List<DCMListHistory> getDcmListHistory(long dcmListId) {
        return dcmListDao.getDcmListHistory(dcmListId);
    }
     public List<DCMListHistory> getDcmListHistoryDeleted(long dcmListId) {
        return dcmListDao.getDcmListHistoryDeleted(dcmListId);
    }
    

    public DCMListHistory getHistoryById(long historyId) {
        return dcmListDao.getHistoryById(historyId);
    }

    public void updateListFromExcelSheet(long historyId, List<DCMListDetail> dCMListDetail) {
          dcmListDao.updateListFromExcelSheet(historyId, dCMListDetail);
    }

    public long insertHistoryForDcmList(long dcm,Integer month,String year) {
       return dcmListDao.insertHistoryForDcmList(dcm, month, year);
    }


    @Override
    public void exportDCMList(long listHistoryId,String filePath) {
         dcmListDao.exportDCMList(listHistoryId,filePath);        
    }

    @Override
    public void updateHistoryStatus(DCMListHistory dcmList) {
        dcmListDao.updateHistoryStatus(dcmList);
    }
    
    //Ahmed Adel
    public void activeList(DCMList dcmList) {
       dcmListDao.activeList(dcmList);

     }

    @Override
    public List<DCMList> getAllHistroicalUpdate() {
         return  getDcmListDao().getAllHistroicalUpdate();
    }

    
}

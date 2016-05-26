/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dl.service;

import com.mobinil.mcss.dl.model.DCMList;
import com.mobinil.mcss.dl.model.DCMListDetail;
import com.mobinil.mcss.dl.model.DCMListHistory;
import com.mobinil.mcss.dl.model.DataView;
import com.mobinil.mcss.dl.model.DcmListType;
import java.util.List;

/**
 *
 * @author mabdelaal
 */
public interface DCMListService {

    public List<DCMList> getLists();

    public List<DcmListType> getTypeList();

    public List<DCMList> searchLists(DCMList dcmList);

    public void activeList(DCMList dcmList);

    public String addList(DCMList dcmList);

    public void editList(DCMList dcmList);

    public void changeStatusList(List<DCMList> dcmListArr);

    public void importDcmListDetail(List<DCMListDetail> dCMListDetail, long historyId);

    public List<DataView> getAllDataView();

    public DCMList getDcmListById(long dcmId);

    public void exportDCMList(long listHistoryId, String filePath);

    public List<DCMListHistory> getDcmListHistory(long dcmListId);

    public List<DCMListHistory> getDcmListHistoryDeleted(long dcmListId);

    public DCMListHistory getHistoryById(long historyId);

    public void updateListFromExcelSheet(long historyId, List<DCMListDetail> dCMListDetail);

    public long insertHistoryForDcmList(long dcm, Integer month, String year);

    public void updateHistoryStatus(DCMListHistory dcmList);

    public List<DCMList> getAllHistroicalUpdate();
}

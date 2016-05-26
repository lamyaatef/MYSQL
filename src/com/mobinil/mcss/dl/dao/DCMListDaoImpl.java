/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dl.dao;

import com.lowagie.text.pdf.ArabicLigaturizer;
import com.mobinil.mcss.dl.constant.DCMListConstant;
import com.mobinil.mcss.dl.excel.ExcelGenerator;
import com.mobinil.mcss.dl.model.DCMList;
import com.mobinil.mcss.dl.model.DCMListDetail;
import com.mobinil.mcss.dl.model.DCMListHistory;
import com.mobinil.mcss.dl.model.DCMListStatus;
import com.mobinil.mcss.dl.model.DataView;
import com.mobinil.mcss.dl.model.DcmListType;
import com.mobinil.mcss.dp.dpManagement.model.Month;
import com.mobinil.sds.core.system.gn.querybuilder.domain.QueryBuilderEngine;
import com.mobinil.sds.core.system.gn.querybuilder.dto.QueryDTO;
import com.mobinil.sds.core.utilities.DBUtil;
import java.io.IOException;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.sql.CLOB;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mabdelaal
 */
@Repository("DCMListDao")
public class DCMListDaoImpl implements DCMListDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<DCMList> getLists() {
        Session dbSession = getSessionFactory().openSession();
        Criteria cr = dbSession.createCriteria(DCMList.class);
        cr.add(Restrictions.ne("DL_LIST_STATUS.statusId", DCMListConstant.DCM_LIST_STATUS_DELETE));
        List<DCMList> dcmList = cr.list();
        dbSession.close();
        return dcmList;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<DCMList> searchLists(DCMList dcmListObj) {
        Session dbSession = getSessionFactory().openSession();
//      Query searchQuery = dbSession.createQuery("");
        Criteria cr = dbSession.createCriteria(DCMList.class);
        cr.add(Restrictions.eq("DL_LIST_ID", dcmListObj.getDL_LIST_ID()));
        List<DCMList> dcmList = cr.list();

        dbSession.close();
        return dcmList;
    }

    @Override
    public String addList(DCMList dcmList) {
        return saveOrUpdate(dcmList);
    }

    @Override
    public void editList(DCMList dcmList) {
        saveOrUpdate(dcmList);
    }

    private String saveOrUpdate(DCMList dcmListObj) {
        Session dbSession = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = dbSession.beginTransaction();
            if (dcmListObj != null && dcmListObj.getDL_LIST_ID() != null) {
                System.out.println("update");

                dbSession.update(dcmListObj);
            } else {
                System.out.println("save");
                dbSession.save(dcmListObj);
            }
            if (dcmListObj.getDATA_VIEW_ID() == null) {

                transaction.commit();
                return "done";

            }

            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine();
            Vector vec = new Vector();
            QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO(dbSession.connection(), safeLongToInt(dcmListObj.getDATA_VIEW_ID().getDataViewId()), vec);
            String strDataViewQuery = queryBuilderEngine.constructQuerySQL(queryDTO);
            dcmListObj.setDATA_VIEW_SQL(strDataViewQuery.getBytes());
            DCMListStatus status = new DCMListStatus(1);
            dcmListObj.setDL_LIST_STATUS(status);
            dbSession.update(dcmListObj);
            transaction.commit();
            dbSession.close();
            return "done";
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            transaction.rollback();
            dbSession.close();
            return "unique";
        }

    }

    @Override
    public void changeStatusList(List<DCMList> dcmListArr) {
        Session dbSession = sessionFactory.openSession();
        SQLQuery query = null;
        try {

            for (DCMList dCMList : dcmListArr) {
                query = dbSession.createSQLQuery("update DL_LIST set DL_LIST_STATUS_ID =" + dCMList.getDL_LIST_STATUS().getStatusId() + " where DL_LIST_ID=" + dCMList.getDL_LIST_ID());
                query.executeUpdate();
            }
        } catch (HibernateException e) {
            throw new HibernateException(e);
        } finally {
            dbSession.close();
        }
    }

    public void importDcmListDetail(List<DCMListDetail> dCMListDetail, long historyId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        HashSet<String> hash = new HashSet();
        for (DCMListDetail detail : dCMListDetail) {
            hash.add(detail.getDcmCode());
        }
        List<String> finalList = new ArrayList<String>(hash);
        for (int i = 0; i < finalList.size(); i++) {
            DCMListDetail dcmdetail = new DCMListDetail();

            dcmdetail.setDcmCode(finalList.get(i));
            dcmdetail.setHistoryID(historyId);
            session.save(dcmdetail);
            if (i % 2 == 0) { //20, same as the JDBC batch size
                //flush a batch of inserts and release memory:
                session.flush();
                session.clear();
            }
        }
        tx.commit();
        updateDcmId(historyId);
        session.close();
    }

    public List<DcmListType> getTypeList() {
        Session session = sessionFactory.openSession();
        Criteria query = session.createCriteria(DcmListType.class);
        List<DcmListType> types = query.list();
        session.close();
        return types;
    }

    public List<DataView> getAllDataView() {
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(DataView.class);
        List<DataView> dataViews = cr.list();
        session.close();
        return dataViews;
    }

    public DCMList getDcmListById(long dcmId) {
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(DCMList.class);
        cr.add(Restrictions.eq("DL_LIST_ID", dcmId));
        DCMList dcmlist = (DCMList) cr.list().get(0);
        session.close();
        return dcmlist;
    }

    private void updateDcmId(long historyId) {
        Session session = sessionFactory.openSession();

        Connection connection = session.connection();
        try {
            Statement stmt = connection.createStatement();
            stmt.executeQuery("update DL_LIST_DETAIL set DL_LIST_DETAIL.DL_DCM_ID = (SELECT GEN_DCM.DCM_ID FROM GEN_DCM WHERE GEN_DCM.DCM_CODE = DL_LIST_DETAIL.DL_DCM_CODE)"
                    + " where DL_LIST_DETAIL.DL_LIST_HISTORY_ID = " + historyId);
            stmt.executeQuery("update DL_LIST_HISTORY set AFFECTED_ROWS = (SELECT count(*) FROM DL_LIST_DETAIL WHERE DL_LIST_HISTORY_ID = " + historyId
                    + " ) where DL_LIST_HISTORY_ID = " + historyId);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DCMListDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DCMListDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }



    }

    public void updateHistoryStatus(DCMListHistory historyObj) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            DCMListHistory dcmListHistoryObj = getDCMListHistory(session, historyObj.getHistoryId());
            dcmListHistoryObj.setHistoryStatusId(historyObj.getHistoryStatusId());
            session.update(dcmListHistoryObj);
            tx.commit();
            if (historyObj.getHistoryStatusId() == DCMListConstant.DCM_LIST_STATUS_DELETE) {
                Connection con = session.connection();
                CallableStatement call = con.prepareCall("begin DELETE_DCM_LIST('" + dcmListHistoryObj.getHistoryId() + "'); end;");
                call.execute();
                call.close();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<DCMListHistory> getDcmListHistory(long dcmListId) {
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(DCMListHistory.class);
        cr.add(Restrictions.eq("dcmList.DL_LIST_ID", dcmListId));
        cr.add(Restrictions.eq("historyStatusId", DCMListConstant.DCM_LIST_STATUS_ACTIVE));
        List<DCMListHistory> historyList = cr.list();
        session.close();
        return historyList;
    }

    public List<DCMListHistory> getDcmListHistoryDeleted(long dcmListId) {
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(DCMListHistory.class);
        cr.add(Restrictions.eq("dcmList.DL_LIST_ID", dcmListId));
        List<DCMListHistory> historyList = cr.list();
        session.close();
        return historyList;
    }

    public DCMListHistory getHistoryById(long historyId) {
        Session session = sessionFactory.openSession();
        DCMListHistory history = getDCMListHistory(session, historyId);
        session.close();
        return history;
    }

    private DCMListHistory getDCMListHistory(Session session, long historyId) {
        Criteria cr = session.createCriteria(DCMListHistory.class);
        cr.add(Restrictions.eq("historyId", historyId));
        List<DCMListHistory> historyList = cr.list();
        DCMListHistory history = historyList.get(0);
        return history;

    }

    public void updateListFromExcelSheet(long historyId, List<DCMListDetail> dCMListDetail) {
        importDcmListDetail(dCMListDetail, historyId);

    }

    public long insertHistoryForDcmList(long dcm, Integer monthId, String year) {
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(Month.class);
        cr.add(Restrictions.eq("index", monthId));
        Month month = (Month) cr.list().get(0);
        Criteria cr2 = session.createCriteria(DCMList.class);
        cr.add(Restrictions.eq("DL_LIST_ID", dcm));
        DCMList dcmList = (DCMList) cr2.list().get(0);
        DCMListHistory dcmListHistory = new DCMListHistory();
        dcmListHistory.setMonth(month);
        dcmListHistory.setDcmList(dcmList);
        dcmListHistory.setYear(year);
        dcmListHistory.setAffectedRows(0);
        dcmListHistory.setCreationDate(new java.util.Date(System.currentTimeMillis()));
        dcmListHistory.setHistoryStatusId(DCMListConstant.DCM_LIST_STATUS_ACTIVE);
        Transaction tx = session.beginTransaction();
        Object obj = session.save(dcmListHistory);
        tx.commit();
        session.close();
//        System.out.println("is obj instance of history "+(obj instanceof DCMListHistory));
//        System.out.println("is obj instance of serlizable "+(obj instanceof Serializable));
//        try {
//            System.out.println("---------");
//            System.out.println(dcmListHistory.getHistoryId());
//            System.out.println(((DCMListHistory)obj).getHistoryId());             
//        } catch (Exception e) {
//        }

//        Connection con = session.connection();
//        int history = DBUtil.executeQuerySingleValueInt("select max(DL_LIST_HISTORY_ID)  from DL_LIST_HISTORY", "max(DL_LIST_HISTORY_ID)", con);
//        try {
//            con.close();
//            session.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(DCMListDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return dcmListHistory.getHistoryId();

    }

    @Override
    public void exportDCMList(long listHistoryId, String filePath) {
        Session dbSession = sessionFactory.openSession();
        ExcelGenerator eg = new ExcelGenerator(filePath);
        eg.createSheetAndHeaders();
        Criteria dcmListDetialsCr = dbSession.createCriteria(DCMListDetail.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dcmListDetialsCr.add(Restrictions.eq("historyID", listHistoryId));
        ScrollableResults res = dcmListDetialsCr.scroll();
        while (res.next()) {
            eg.exportDCMList((DCMListDetail) res.get(0));
        }
        try {
            eg.writeExcelFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dbSession.close();

    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
    //Ahmed Adel

    public void activeList(DCMList dcmList) {
        Session dbSession = sessionFactory.openSession();
        Transaction trx = dbSession.beginTransaction();
        DCMListStatus status = new DCMListStatus(1);
        dcmList.setDL_LIST_STATUS(status);
        dbSession.update(dcmList);
        trx.commit();
    }

    @Override
    public List<DCMList> getAllHistroicalUpdate() {
        Session dbSession = getSessionFactory().openSession();
        Criteria cr = dbSession.createCriteria(DCMList.class);
        List<DCMList> dcmList = cr.list();
        dbSession.close();
        return dcmList;
    }
}

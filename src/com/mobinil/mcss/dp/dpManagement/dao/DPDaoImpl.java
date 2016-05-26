package com.mobinil.mcss.dp.dpManagement.dao;

import com.mobinil.mcss.dp.dpManagement.model.CommissionCategory;
import com.mobinil.mcss.dp.dpManagement.model.DPStatus;
import com.mobinil.mcss.dp.dpManagement.model.DistFactorsValues;
import com.mobinil.mcss.dp.dpManagement.model.DrivingPlan;
import com.mobinil.mcss.dp.dpManagement.model.DrivingPlanHistory;
import com.mobinil.mcss.dp.dpManagement.model.GenChannel;
import com.mobinil.mcss.dp.dpManagement.model.GenDcmLevel;
import com.mobinil.mcss.dp.dpManagement.model.GenDcmPaymentLevel;
import com.mobinil.mcss.dp.dpManagement.model.Month;
import com.mobinil.mcss.dp.dpManagement.model.PaymentType;
import com.mobinil.mcss.dp.factor.dao.FactorDao;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;




import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("DPDao")
public class DPDaoImpl implements DPDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private FactorDao factorDao;

    public List<GenChannel> getChannels() {
        Session session = sessionFactory.openSession();
        List<GenChannel> channels = session.createQuery("FROM GenChannel").list();
        session.close();
        return channels;
    }

    public List<GenDcmLevel> getDcmLevels() {
        Session session = sessionFactory.openSession();
        List<GenDcmLevel> dcmLevels = session.createQuery("FROM GenDcmLevel").list();
        session.close();
        return dcmLevels;

    }

    public List<DPStatus> getDPStatus() {
        Session session = sessionFactory.openSession();
        List<DPStatus> dpStatus = session.createCriteria(DPStatus.class).list();
        session.close();
        return dpStatus;

    }

    public List<GenDcmPaymentLevel> getDcmPayments() {
        Session session = sessionFactory.openSession();
        List<GenDcmPaymentLevel> dcmPaymentLevels = session.createQuery("FROM GenDcmPaymentLevel").list();
        session.close();
        return dcmPaymentLevels;
    }

    public void saveDP(DrivingPlan dp) throws Exception {
        Session session = sessionFactory.openSession();
        String TempName=dp.getPlanName();
        DPStatus planStatus =  new DPStatus();
        planStatus.setStatusId(0);
        dp.setPlanName("");
        
        Set <DrivingPlan> dpSet = search(dp);
        if(!dpSet.isEmpty()){
            
        List <DrivingPlan> dpList = new ArrayList<DrivingPlan>(dpSet);
        DrivingPlan d= dpList.get(0);
        long status=d.getPlanStatus().getStatusId();
        if(status==5)
        {
        planStatus.setStatusId(1);                
        dp.setPlanName(TempName); 
        dp.setPlanStatus(planStatus);
        Transaction tx = session.beginTransaction();
        session.save(dp);
        tx.commit();
        session.close();
        }
        else
        {
            dp.setPlanName(TempName);
            Exception ex = new SQLException("unique"); 
            
            throw ex;
        }
        }else
        {
        dp.setPlanName(TempName);  
         planStatus.setStatusId(1); 
          dp.setPlanStatus(planStatus);
        Transaction tx = session.beginTransaction();
        session.save(dp);
        tx.commit();
        session.close();
        }
        
    }

    public Set<DrivingPlan> search(DrivingPlan dp) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(DrivingPlan.class);

        if (dp.getPlanName() != null && !dp.getPlanName().equals("")) {
            Criterion preNameFilter = Restrictions.like("planName", "%" + dp.getPlanName());
            Criterion postNameFilter = Restrictions.like("planName", dp.getPlanName() + "%");
            Criterion NameFilter = Restrictions.like("planName", dp.getPlanName());
            LogicalExpression orNameEx = Restrictions.or(preNameFilter, postNameFilter);
            LogicalExpression orNameFilter = Restrictions.or(orNameEx, NameFilter);
            criteria.add(orNameFilter);

        }
        if (dp.getId().getDPMonth().getIndex() != 0) {
            criteria.add(Restrictions.eq("id.DPMonth", dp.getId().getDPMonth()));

        }
        if (!dp.getId().getDPYear().equals("0")) {
            criteria.add(Restrictions.eq("id.DPYear", dp.getId().getDPYear()));

        }
        if (dp.getId().getDcmLevel().getIdLevel() != 0) {
            criteria.add(Restrictions.eq("id.dcmLevel", dp.getId().getDcmLevel()));

        }
        if (dp.getId().getDcmPaymentLevel().getIdPaymentLevel() != 0) {
            criteria.add(Restrictions.eq("id.dcmPaymentLevel", dp.getId().getDcmPaymentLevel()));

        }
        if (dp.getId().getChannel().getChannelId() != 0) {
            criteria.add(Restrictions.eq("id.channel", dp.getId().getChannel()));

        }
        if (dp.getPlanStatus().getStatusId() != 0) {
            criteria.add(Restrictions.eq("planStatus.id", dp.getPlanStatus().getStatusId()));

        }
        if (dp.getPaymentType().getTypeId() != 0) {
            criteria.add(Restrictions.eq("paymentType", dp.getPaymentType()));

        }
        if (dp.getCommissionCategory().getCategoryId() != 0) {

            criteria.add(Restrictions.eq("commissionCategory", dp.getCommissionCategory()));
        }


        Set<DrivingPlan> results = new HashSet<DrivingPlan>(criteria.list());
        
        session.close();
        return results;

    }

    public DrivingPlan getDPById(long dpId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(DrivingPlan.class);
        criteria.add(Restrictions.eq("dpId", dpId));
        DrivingPlan dp = (DrivingPlan) criteria.list().get(0);
        session.close();
        return dp;
    }

    public void updateDP(DrivingPlan dp) throws Exception{
        Session session = sessionFactory.openSession();
        DPStatus planStatus =  new DPStatus();
        String TempName=dp.getPlanName();
        planStatus.setStatusId(1);
        dp.setPlanName("");
        Set <DrivingPlan> dpSet = search(dp);
        if(!dpSet.isEmpty()){
        List <DrivingPlan> dpList = new ArrayList<DrivingPlan>(dpSet);
        DrivingPlan d= dpList.get(0);
        long status=d.getPlanStatus().getStatusId();
      
        planStatus.setStatusId(1);                
        dp.setPlanName(TempName); 
        dp.setPlanStatus(planStatus);
        Transaction tx = session.beginTransaction();
        session.update(dp);
        tx.commit();
        session.close();
        
        }else
        {
        dp.setPlanName(TempName);    
        dp.setPlanStatus(planStatus);
        Transaction tx = session.beginTransaction();
        session.update(dp);
        tx.commit();
        session.close();
        }
        
    }

    public void deleteDP(long dpId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        DPStatus DPStatus;
        Criteria statusCr = session.createCriteria(DPStatus.class);
        statusCr.add(Restrictions.eq("id", Long.valueOf(5)));
        DPStatus = (DPStatus) statusCr.list().get(0);
        DrivingPlan dp = getDPById(dpId);
        dp.setPlanStatus(DPStatus);
        session.update(dp);
        tx.commit();
        session.close();

    }

    public List<Month> getMonthList() {

        Session session = sessionFactory.openSession();
        List<Month> months = session.createQuery("FROM Month").list();
        session.close();
        return months;

    }

    public boolean sendingApprove(long dpId, String userId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        DPStatus DPStatus;
        Criteria statusCr = session.createCriteria(DPStatus.class);
        statusCr.add(Restrictions.eq("id", Long.valueOf(3)));
        DPStatus = (DPStatus) statusCr.list().get(0);
        DrivingPlan dp = getDPById(dpId);
        
        boolean approved=false;
        
        if(dp.getUser().equals(userId))
        {
            approved=true;
        dp.setPlanStatus(DPStatus);
        dp.setUser(userId);
        session.update(dp);
        tx.commit();
        }
        session.close();
        return approved;
    }

    public List<PaymentType> getPaymentTypes() {
        Session session = sessionFactory.openSession();
        List<PaymentType> PaymentType = session.createQuery("FROM PaymentType").list();
        session.close();
        return PaymentType;
    }

    public List<DrivingPlan> getApprovedDrivingPlan() {
        Session session = sessionFactory.openSession();
        List<DrivingPlan> approvedDrivingPlans = session.createQuery("FROM DrivingPlan where planStatus.statusId = 3").list();
        session.close();
        return approvedDrivingPlans;
    }

    public String approveDrivingPlan(long dpId, String userId, String comment) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        DPStatus DPStatus;
        Criteria statusCr = session.createCriteria(DPStatus.class);
        statusCr.add(Restrictions.eq("id", Long.valueOf(2)));
        DPStatus = (DPStatus) statusCr.list().get(0);
        DrivingPlan dp = getDPById(dpId);
        if (!dp.getUser().equals(userId)) {
            dp.setPlanStatus(DPStatus);
            dp.setUser(userId);
            session.update(dp);
            tx.commit();
            session.close();
            try {
                String status = "Approved";
                logApproveDrivingPlan(dpId, userId, comment, status);
            } catch (SQLException ex) {
                Logger.getLogger(DPDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "done";
        } else {
            session.update(dp);
            tx.commit();
            session.close();
            return "user";
        }
    }

    public String refuseDrivingPlan(long dpId, String userId, String comment) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        DPStatus DPStatus;
        Criteria statusCr = session.createCriteria(DPStatus.class);
        statusCr.add(Restrictions.eq("id", Long.valueOf(4)));
        DPStatus = (DPStatus) statusCr.list().get(0);
        DrivingPlan dp = getDPById(dpId);
        if (!dp.getUser().equals(userId)) {
            dp.setPlanStatus(DPStatus);
            dp.setUser(userId);
            session.update(dp);
            tx.commit();
            session.close();
            try {
                String status = "Refused";
                logApproveDrivingPlan(dpId, userId, comment, status);
            } catch (SQLException ex) {
                Logger.getLogger(DPDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "done";
        } else {
            session.update(dp);
            tx.commit();
            session.close();
            return "user";
        }

    }

    private void logApproveDrivingPlan(long dpId, String userId, String Comment, String status) throws SQLException {
        Session session = sessionFactory.openSession();
        Statement stmt = session.connection().createStatement();

        stmt.execute("INSERT INTO SDS.DP_LOG_APPROVE_DRIVINGPLAN ("
                + "APPROVE_ID, DRIVING_PLAN_ID, USER_ID, "
                + "APPROVE_COMMENT,MODIFY_DATE,DPSTATUS) "
                + "VALUES (seq_approve_drivingplan.NEXTVAL," + dpId + ",'" + userId + "','" + Comment + "',sysdate,'" + status + "')");

    }

    public List<CommissionCategory> getCommissionCategorys() {
        Session session = sessionFactory.openSession();
        List<CommissionCategory> commissionCategorys = session.createQuery("FROM CommissionCategory").list();
        session.close();
        return commissionCategorys;
    }

    public List<DrivingPlanHistory> viewHistory(long dpId) {
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(DrivingPlanHistory.class);
        cr.add(Restrictions.eq("dp.dpId", dpId));
        cr.addOrder(Order.asc("modifyDate"));
        List<DrivingPlanHistory> histories = cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return histories;
    }

    public void addDisFactorValue(DistFactorsValues value) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(value);
        tx.commit();
        session.close();

    }
}
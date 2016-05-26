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
import java.util.List;
import java.util.Set;




public interface DPDao {
	// To Save the article detail
	public List<GenChannel> getChannels();
	public List<GenDcmLevel> getDcmLevels();
        public List<PaymentType> getPaymentTypes();
	public List<GenDcmPaymentLevel> getDcmPayments();
        public List<CommissionCategory> getCommissionCategorys();
        public void saveDP (DrivingPlan dp) throws Exception;
        public Set<DrivingPlan> search (DrivingPlan dp);
        public List<DPStatus> getDPStatus();
        public DrivingPlan getDPById(long dpId);
        public void updateDP (DrivingPlan dp)throws Exception;
        public void deleteDP (long dpId);
        public List<Month> getMonthList();
        public boolean sendingApprove(long dpId,String userId);
        public List<DrivingPlan> getApprovedDrivingPlan ();
        public String approveDrivingPlan (long dpId,String userId,String Comment);
        public String refuseDrivingPlan(long dpId,String userId,String Comment);
        public List<DrivingPlanHistory> viewHistory (long dpId);
        public void addDisFactorValue(DistFactorsValues value);
}
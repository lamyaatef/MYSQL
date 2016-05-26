package com.mobinil.mcss.dp.dpManagement.service;

import com.mobinil.mcss.dp.dpManagement.model.*;
import java.util.List;
import java.util.Set;



public interface DPService {

	public List<GenChannel> getChannels();
	public List<GenDcmLevel> getDcmLevels();
        public List<PaymentType> getPaymentTypes();
	public List<GenDcmPaymentLevel> getDcmPayments();
        public List<CommissionCategory> getCommissionCategorys();
        public List<DPStatus> getDPStatus();
        public void saveDP (DrivingPlan dp) throws Exception;
        public Set<DrivingPlan> search (DrivingPlan dp);
        public DrivingPlan getDPById(long dpId);
        public void updateDP (DrivingPlan dp)throws Exception;
        public void deleteDP (long dpId);
        public List<Month> getMonthList();
        public boolean sendingApprove (long dpId,String userId);
        public List<DrivingPlan> getApprovedDrivingPlan ();
        public String approveDrivingPlan (long dpId,String userId,String Comment);
        public String refuseDrivingPlan(long dpId,String userId,String Comment);
        public List<DrivingPlanHistory> viewHistory (long dpId);
        public void distributeFactorsValues(long dpId);
}
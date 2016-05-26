package com.mobinil.mcss.dp.dpManagement.service;

import com.mobinil.mcss.dp.dpManagement.dao.DPDao;
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
import com.mobinil.mcss.dp.factor.model.Factor;
import com.mobinil.mcss.dp.factor.model.FactorValue;
import com.mobinil.mcss.dp.factor.service.FactorService;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("DPService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DPServiceImpl implements DPService {

    @Autowired
    private DPDao dpDao;
    @Autowired
    private FactorService factorService;

    public List<GenChannel> getChannels() {
        return dpDao.getChannels();
    }

    public List<GenDcmLevel> getDcmLevels() {
        return dpDao.getDcmLevels();
    }

    public List<GenDcmPaymentLevel> getDcmPayments() {
        return dpDao.getDcmPayments();
    }

    public void saveDP(DrivingPlan dp) throws Exception {
        dpDao.saveDP(dp);
    }

    public Set<DrivingPlan> search(DrivingPlan dp) {
        return dpDao.search(dp);
    }

    public List<DPStatus> getDPStatus() {
        return dpDao.getDPStatus();
    }

    public DrivingPlan getDPById(long dpId) {
        return dpDao.getDPById(dpId);
    }

    public void updateDP(DrivingPlan dp) throws Exception {
        dpDao.updateDP(dp);
    }

    public void deleteDP(long dpId) {
        dpDao.deleteDP(dpId);
    }

    public List<Month> getMonthList() {
        return dpDao.getMonthList();
    }

    public boolean sendingApprove(long dpId, String userId) {
     return   dpDao.sendingApprove(dpId, userId);
    }

    public List<PaymentType> getPaymentTypes() {
        return dpDao.getPaymentTypes();
    }

    public List<DrivingPlan> getApprovedDrivingPlan() {
        return dpDao.getApprovedDrivingPlan();
    }

    public String approveDrivingPlan(long dpId, String userId, String Comment) {
        return dpDao.approveDrivingPlan(dpId, userId, Comment);
    }

    public String refuseDrivingPlan(long dpId, String userId, String Comment) {
        return dpDao.refuseDrivingPlan(dpId, userId, Comment);
    }

    public List<CommissionCategory> getCommissionCategorys() {
        return dpDao.getCommissionCategorys();
    }

    public List<DrivingPlanHistory> viewHistory(long dpId) {
        return dpDao.viewHistory(dpId);
    }

    public void distributeFactorsValues(long dpId) {
        List<Factor> factorsforDrivingPlan = factorService.getFactorslist(dpId);

        DrivingPlan drivingPlan = getDPById(dpId);

        for (Factor f : factorsforDrivingPlan) {

            String factorName = f.getFactorName().getFactorName();
            //Constant
            if (f.getFactorName().getFactorTypeId().equals("1")) {
               distributeConstantValue(f, factorName, drivingPlan);
            }
            //Month
            else if (f.getFactorName().getFactorTypeId().equals("3")) {
              distributeMonthValue(f, factorName, drivingPlan);
            }
            //Day
            else if (f.getFactorName().getFactorTypeId().equals("2")) {
               distributeDayValue(f, factorName, drivingPlan);
            }

        }
    }

    private void insertDistributedValue(DistFactorsValues value) {

        dpDao.addDisFactorValue(value);
    }

    private void distributeConstantValue(Factor f,String factorName,DrivingPlan drivingPlan) {
        List<FactorValue> values = new ArrayList<FactorValue>(f.getFactorValues());
        float FactorValue = Float.parseFloat(values.get(0).getFactorValue());
        DistFactorsValues value = new DistFactorsValues();
        value.setFactorId(f);
        value.setFactorname(factorName);
        value.setFactorvalue(FactorValue);
        value.setPlanId(drivingPlan);
        insertDistributedValue(value);
    }
    private void distributeMonthValue(Factor f,String factorName,DrivingPlan drivingPlan) {
       List<Month> months = getMonthList();
                List<FactorValue> values = new ArrayList<FactorValue>(f.getFactorValues());
                FactorValue v = values.get(0);
                Calendar cr = Calendar.getInstance();
                Calendar finish = Calendar.getInstance();

                SimpleDateFormat sp = new SimpleDateFormat("dd-MM-yyyy");
                Date startDay;
                try {
                    startDay = sp.parse("01-" + Integer.toString(drivingPlan.getId().getDPMonth().getIndex()) + "-" + drivingPlan.getId().getDPYear());

                    cr.setTime(startDay);
                    finish.setTime(startDay);

                    float FactorValue = Float.parseFloat(values.get(0).getFactorValue());
                    DistFactorsValues value = new DistFactorsValues();
                    String MonthBack = v.getMonthFactorBackward();
                    String MonthForward = v.getMonthFactorForward();

                    int back = Integer.parseInt(MonthBack);
                    int forward = Integer.parseInt(MonthForward);
                    cr.add(Calendar.MONTH, back * -1);
                    finish.add(Calendar.MONTH, forward);
                    Date finishDay = finish.getTime();
                    System.out.println(cr.getTime().toString());
                    SimpleDateFormat sdf;
                    sdf = new SimpleDateFormat("MM");
                    while (cr.before(finish) || cr.equals(finish)) {

                        value.setFactorId(f);
                        value.setFactorname(f.getFactorName().getFactorName() + "_" + sdf.format(cr.getTime()) + "-" + Integer.toString(cr.getTime().getYear() + 1900));
                        value.setFactorvalue(FactorValue);
                        value.setPlanId(drivingPlan);
                        insertDistributedValue(value);
                        cr.add(Calendar.MONTH, 1);
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(DPServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
     private void distributeDayValue(Factor f,String factorName,DrivingPlan drivingPlan) {
        List<FactorValue> values = new ArrayList<FactorValue>(f.getFactorValues());
                for (FactorValue singleValue : values) {
                    Timestamp rangeFrom = singleValue.getFactorRangeFrom();
                    Timestamp rangeTo = singleValue.getFactorRangeTo();
                    Date BeginDate = new Date(rangeFrom.getTime());
                    Date EndDate = new Date(rangeTo.getTime());
                    Calendar cr = Calendar.getInstance();
                    cr.setTime(BeginDate);
                    while (cr.getTime().before(EndDate) || cr.getTime().equals(EndDate)) {
                        DistFactorsValues value = new DistFactorsValues();
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        Date day = cr.getTime();

                        String stringDay = format.format(day);
                        String arrayDate[] = stringDay.split("-");
                        value.setFactorname(f.getFactorName().getFactorName() + "_" + arrayDate[0] + "-" + arrayDate[1] + "-" + arrayDate[2]);
                        value.setFactorvalue(Float.parseFloat(singleValue.getFactorValue()));
                        value.setFactorId(f);
                        cr.add(Calendar.DATE, 1);
                        value.setPlanId(drivingPlan);
                        insertDistributedValue(value);
                    }
                }
    }
}
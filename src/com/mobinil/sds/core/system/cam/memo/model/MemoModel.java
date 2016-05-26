package com.mobinil.sds.core.system.cam.memo.model;

import java.util.Date;
import java.util.Vector;

public class MemoModel {

    private int id;
    private String name;
    private String desc;
    private String type;
    private Date startDate;
    private String state;
    private String paymentType;
    private String paymentMethod;
    private String channel;
    private String subChannel;
    private String paymentName;
    private int commissionValue;
    private int deductionValue;
    private String memoComment;
    private int periodId;
    private int quarterId;
    private int monthInQuarter;
    private int dayInMonthInQuarter;
    private int monthId;
    private int dayInMonth;
    private int weekId;
    private int dayInWeek;
    private Date sendForValidationDate;
    private Date approvalDate;
    private Date salesManagerApprovalDate;
    private Date salesBackOfficeApprovalDate;
    private Date salesDirectiveApprovalDate;
    private Date financeReceiveDate;
    private Date paymentDate;
    private Date issueDate;
    private Date financeIssueDate;
    private Vector paymentNames;

    public Date getFinanceIssueDate() {
        return financeIssueDate;
    }

    public void setFinanceIssueDate(Date financeIssueDate) {
        this.financeIssueDate = financeIssueDate;
    }

    public Vector getPaymentNames() {
        return paymentNames;
    }

    public void setPaymentNames(Vector paymentNames) {
        this.paymentNames = paymentNames;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    private String creator;
    private Vector delayReason;
    private Date readyCommClacDate;
    private Date calcTargetedDate;
    private Date paymentTargetedDate;
    private Date finishedOn;
    private int commCalcDelay;
    private int paymentDelay;

    public Date getReadyCommClacDate() {
        return readyCommClacDate;
    }

    public void setReadyCommClacDate(Date readyCommClacDate) {
        this.readyCommClacDate = readyCommClacDate;
    }

    public Date getCalcTargetedDate() {
        return calcTargetedDate;
    }

    public void setCalcTargetedDate(Date calcTargetedDate) {
        this.calcTargetedDate = calcTargetedDate;
    }

    public Date getPaymentTargetedDate() {
        return paymentTargetedDate;
    }

    public void setPaymentTargetedDate(Date paymentTargetedDate) {
        this.paymentTargetedDate = paymentTargetedDate;
    }

    public Date getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(Date finishedOn) {
        this.finishedOn = finishedOn;
    }

    public int getCommCalcDelay() {
        return commCalcDelay;
    }

    public void setCommCalcDelay(int commCalcDelay) {
        this.commCalcDelay = commCalcDelay;
    }

    public int getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(int paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public Vector getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(Vector delayReason) {
        this.delayReason = delayReason;
    }

    public MemoModel(int id, String name, Date startDate, String state,
            String channel, String subChannel,
            int commissionValue, int periodId, Date sendForValidationDate,
            Date approvalDate, Date salesManagerApprovalDate,
            Date salesBackOfficeApprovalDate,
            Date salesDirectiveApprovalDate, Date financeReceiveDate,
            Date paymentDate) {
        super();
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.state = state;
        this.channel = channel;
        this.subChannel = subChannel;
        this.commissionValue = commissionValue;
        this.periodId = periodId;
        this.sendForValidationDate = sendForValidationDate;
        this.approvalDate = approvalDate;
        this.salesManagerApprovalDate = salesManagerApprovalDate;
        this.salesBackOfficeApprovalDate = salesBackOfficeApprovalDate;
        this.salesDirectiveApprovalDate = salesDirectiveApprovalDate;
        this.financeReceiveDate = financeReceiveDate;
        this.paymentDate = paymentDate;


    }

    public MemoModel() {
    }

    public MemoModel(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public MemoModel(int id, String name, Date sendForValidationDate,
            Date approvalDate, Date salesManagerApprovalDate,
            Date salesBackOfficeApprovalDate,
            Date salesDirectiveApprovalDate, Date financeReceiveDate,
            Date payment_date) {
        super();
        this.id = id;
        this.name = name;
        this.sendForValidationDate = sendForValidationDate;
        this.approvalDate = approvalDate;
        this.salesManagerApprovalDate = salesManagerApprovalDate;
        this.salesBackOfficeApprovalDate = salesBackOfficeApprovalDate;
        this.salesDirectiveApprovalDate = salesDirectiveApprovalDate;
        this.financeReceiveDate = financeReceiveDate;
        this.paymentDate = paymentDate;

    }

    public MemoModel(int id, String name, Date sendForValidationDate,
            Date approvalDate, Date salesManagerApprovalDate,
            Date salesBackOfficeApprovalDate,
            Date salesDirectivrApprovalDate, Date financeReceiveDate,
            Date paymentDate, int periodId) {
        super();
        this.id = id;
        this.name = name;
        this.sendForValidationDate = sendForValidationDate;
        this.approvalDate = approvalDate;
        this.salesManagerApprovalDate = salesManagerApprovalDate;
        this.salesBackOfficeApprovalDate = salesBackOfficeApprovalDate;
        this.salesDirectiveApprovalDate = salesDirectiveApprovalDate;
        this.financeReceiveDate = financeReceiveDate;
        this.paymentDate = paymentDate;
        this.periodId = periodId;
    }

    public int getPeriodId() {
        return periodId;
    }

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public MemoModel(int id, String name, String desc, Date startDate,
            String channel, String paymentType, String state, String type) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.startDate = startDate;
        this.channel = channel;
        this.paymentType = paymentType;
        this.state = state;
        this.type = type;
    }

    public MemoModel(int id, String name, String desc, Date startDate,
            String channel, String paymentType, String paymentMethod,
            String state, String type) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.startDate = startDate;
        this.channel = channel;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;
        this.state = state;
        this.type = type;
    }

    public String getMemoComment() {
        return memoComment;
    }

    public void setMemoComment(String memoComment) {
        this.memoComment = memoComment;
    }

    public MemoModel(int id, String name, String desc, Date startDate,
            String channel, String state, String type) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.startDate = startDate;
        this.state = state;
        this.channel = channel;

    }

    public MemoModel(String name, String desc, String type, Date startDate,
            String state, String paymentType, String channel,
            String paymentName, int commissionValue) {
        super();
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.startDate = startDate;
        this.state = state;
        this.paymentType = paymentType;
        this.channel = channel;
        this.paymentName = paymentName;
        this.commissionValue = commissionValue;
    }

    public MemoModel(String name, String desc, String type, Date startDate,
            String state, String channel, int commission_value) {
        super();
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.startDate = startDate;
        this.state = state;
        this.channel = channel;
        this.commissionValue = commission_value;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public int getCommissionValue() {
        return commissionValue;
    }

    public void setCommissionValue(int commissionValue) {
        this.commissionValue = commissionValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public int getId() {
        return id;
    }

    public String getPaymentType() {
        if (paymentType == null) {
            return "-1";
        } else {
            return paymentType;
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getState() {
        return state;
    }

    public String getType() {
        return type;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getSendForValidationDate() {
        return sendForValidationDate;
    }

    public void setSendForValidationDate(Date sendForValidationDate) {
        this.sendForValidationDate = sendForValidationDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Date getSalesManagerApprovalDate() {
        return salesManagerApprovalDate;
    }

    public void setSalesManagerApprovalDate(Date salesManagerApprovalDate) {
        this.salesManagerApprovalDate = salesManagerApprovalDate;
    }

    public Date getSalesBackOfficeApprovalDate() {
        return salesBackOfficeApprovalDate;
    }

    public void setSalesBackOfficeApprovalDate(
            Date salesBackOfficeApprovalDate) {
        this.salesBackOfficeApprovalDate = salesBackOfficeApprovalDate;
    }

    public Date getSalesDirectiveApprovalDate() {
        return salesDirectiveApprovalDate;
    }

    public void setSalesDirectiveApprovalDate(Date salesDirectiveApprovalDate) {
        this.salesDirectiveApprovalDate = salesDirectiveApprovalDate;
    }

    public Date getFinanceReceiveDate() {
        return financeReceiveDate;
    }

    public void setFinanceReceiveDate(Date financeReceiveDate) {
        this.financeReceiveDate = financeReceiveDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getSubChannel() {
        return subChannel;
    }

    public void setSubChannel(String subChannel) {
        this.subChannel = subChannel;
    }

    public int getQuarterId() {
        return quarterId;
    }

    public void setQuarterId(int quarterId) {
        this.quarterId = quarterId;
    }

    public int getMonthInQuarter() {
        return monthInQuarter;
    }

    public void setMonthInQuarter(int monthInQuarter) {
        this.monthInQuarter = monthInQuarter;
    }

    public int getDayInMonthInQuarter() {
        return dayInMonthInQuarter;
    }

    public void setDayInMonthInQuarter(int dayInMonthInQuarter) {
        this.dayInMonthInQuarter = dayInMonthInQuarter;
    }

    public int getMonthId() {
        return monthId;
    }

    public void setMonthId(int monthId) {
        this.monthId = monthId;
    }

    public int getDayInMonth() {
        return dayInMonth;
    }

    public void setDayInMonth(int dayInMonth) {
        this.dayInMonth = dayInMonth;
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    public int getDayInWeek() {
        return dayInWeek;
    }

    public void setDayInWeek(int dayInWeek) {
        this.dayInWeek = dayInWeek;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getDeductionValue() {
        return deductionValue;
    }

    public void setDeductionValue(int deductionValue) {
        this.deductionValue = deductionValue;
    }
}
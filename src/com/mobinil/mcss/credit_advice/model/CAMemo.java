/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Brain
 */
public class CAMemo {

    private Long memoId;
    private Long stateId;
    private Long typeId;
    private Long paymentTypeId;
    private Long paymenMethodId;
    private String memoName;
    private String desc;
    private Long channelId;
    private Date startDate;
    private String memoComment;
    private Date sendForValidationDate;
    private Date approvalDate;
    private Date salesManagerApprovalDate;
    private Date salesBackOfficeApprovalDate;
    private Date salesDirectiveApprovalDate;
    private Date financeReceiveDate;
    private Date paymentDate;
    private String memoTitle;
    private Date readyCommCalcDate;
    private Date calcTargetedDate;
    private Date paymentTargetedDate;
    private Date finishedOn;
    private Long cafileId;
    private Long commCalcDelay;
    private Long paymentDelay;
    private Float totalPayment;
    private long scmId;
    private long scmCommissionValue;
    private String dcmName;
    private String channelName;
    private String dcmCode;
    private Long deductionsValue;
    private Float withHoldTax;
    private long filetraceId;
    private long cafileTraceId;
    private long cafileTraceUserId;
    private Date cafileTraceDate;
    private long cafileTraceStatusId;
    private StatusFile statusId;
    private List<CAMemo> memos;

    public long getScmCommissionValue() {
        return scmCommissionValue;
    }

    public void setScmCommissionValue(long scmCommissionValue) {
        this.scmCommissionValue = scmCommissionValue;
    }

    public long getScmId() {
        return scmId;
    }

    public void setScmId(long scmId) {
        this.scmId = scmId;
    }

    public Long getCafileId() {
        return cafileId;
    }

    public void setCafileId(Long cafileId) {
        this.cafileId = cafileId;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getFinanceReceiveDate() {
        return financeReceiveDate;
    }

    public void setFinanceReceiveDate(Date financeReceiveDate) {
        this.financeReceiveDate = financeReceiveDate;
    }

    public String getMemoComment() {
        return memoComment;
    }

    public void setMemoComment(String memoComment) {
        this.memoComment = memoComment;
    }

    public Long getMemoId() {
        return memoId;
    }

    public void setMemoId(Long memoId) {
        this.memoId = memoId;
    }

    public String getMemoName() {
        return memoName;
    }

    public void setMemoName(String memoName) {
        this.memoName = memoName;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getSalesBackOfficeApprovalDate() {
        return salesBackOfficeApprovalDate;
    }

    public void setSalesBackOfficeApprovalDate(Date salesBackOfficeApprovalDate) {
        this.salesBackOfficeApprovalDate = salesBackOfficeApprovalDate;
    }

    public Date getSalesDirectiveApprovalDate() {
        return salesDirectiveApprovalDate;
    }

    public void setSalesDirectiveApprovalDate(Date salesDirectiveApprovalDate) {
        this.salesDirectiveApprovalDate = salesDirectiveApprovalDate;
    }

    public Date getSalesManagerApprovalDate() {
        return salesManagerApprovalDate;
    }

    public void setSalesManagerApprovalDate(Date salesManagerApprovalDate) {
        this.salesManagerApprovalDate = salesManagerApprovalDate;
    }

    public Date getSendForValidationDate() {
        return sendForValidationDate;
    }

    public void setSendForValidationDate(Date sendForValidationDate) {
        this.sendForValidationDate = sendForValidationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Float getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Float totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getDcmName() {
        return dcmName;
    }

    public void setDcmName(String dcmName) {
        this.dcmName = dcmName;
    }

    public String getDcmCode() {
        return dcmCode;
    }

    public void setDcmCode(String dcmCode) {
        this.dcmCode = dcmCode;
    }

    public Float getWithHoldTax() {
        return withHoldTax;
    }

    public void setWithHoldTax(Float withHoldTax) {
        this.withHoldTax = withHoldTax;
    }

    public Date getCalcTargetedDate() {
        return calcTargetedDate;
    }

    public void setCalcTargetedDate(Date calcTargetedDate) {
        this.calcTargetedDate = calcTargetedDate;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getCommCalcDelay() {
        return commCalcDelay;
    }

    public void setCommCalcDelay(Long commCalcDelay) {
        this.commCalcDelay = commCalcDelay;
    }

    public Date getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(Date finishedOn) {
        this.finishedOn = finishedOn;
    }

    public String getMemoTitle() {
        return memoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }

    public Long getPaymenMethodId() {
        return paymenMethodId;
    }

    public void setPaymenMethodId(Long paymenMethodId) {
        this.paymenMethodId = paymenMethodId;
    }

    public Long getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(Long paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public Date getPaymentTargetedDate() {
        return paymentTargetedDate;
    }

    public void setPaymentTargetedDate(Date paymentTargetedDate) {
        this.paymentTargetedDate = paymentTargetedDate;
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public Date getReadyCommCalcDate() {
        return readyCommCalcDate;
    }

    public void setReadyCommCalcDate(Date readyCommCalcDate) {
        this.readyCommCalcDate = readyCommCalcDate;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getDeductionsValue() {
        return deductionsValue;
    }

    public void setDeductionsValue(Long deductionsValue) {
        this.deductionsValue = deductionsValue;
    }

    public long getFiletraceId() {
        return filetraceId;
    }

    public void setFiletraceId(long filetraceId) {
        this.filetraceId = filetraceId;
    }

    public long getCafileTraceId() {
        return cafileTraceId;
    }

    public void setCafileTraceId(long cafileTraceId) {
        this.cafileTraceId = cafileTraceId;
    }

    public long getCafileTraceUserId() {
        return cafileTraceUserId;
    }

    public void setCafileTraceUserId(long cafileTraceUserId) {
        this.cafileTraceUserId = cafileTraceUserId;
    }

    public Date getCafileTraceDate() {
        return cafileTraceDate;
    }

    public void setCafileTraceDate(Date cafileTraceDate) {
        this.cafileTraceDate = cafileTraceDate;
    }

    public long getCafileTraceStatusId() {
        return cafileTraceStatusId;
    }

    public void setCafileTraceStatusId(long cafileTraceStatusId) {
        this.cafileTraceStatusId = cafileTraceStatusId;
    }

    public StatusFile getStatusId() {
        return statusId;
    }

    public void setStatusId(StatusFile statusId) {
        this.statusId = statusId;
    }

    public List<CAMemo> getMemos() {
        return memos;
    }

    public void setMemos(List<CAMemo> memos) {
        this.memos = memos;
    }
}

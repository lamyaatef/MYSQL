package com.mobinil.sds.core.system.cam.payment.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

public class PaymentModel 
{
 

 private int paymentDetailId ;
 private String paymentName ;
 private int  paymentStatusTypeId;
 private int paymentTypeId;
 private Date paymentStartDate;
 private Date paymentEndDate;
 private String paymentTypeName;
 private String paymentStatusTypeName;
 private String DCMName;
 private String DCMCode;
  
 private int paymentSCMId;
 private int paymentSCMCommissionValue;

    public String getDCMCode() {
        return DCMCode;
    }

    public void setDCMCode(String DCMCode) {
        this.DCMCode = DCMCode;
    }
// private int paymentAccrualId;
// private String paymentFlag;
// private int paymentDeductionValue;
 private int paymentRecordId;
// private int paymentMemoId;
// private String paymentFrozenFlag;

    public int getPaymentSCMId() {
        return paymentSCMId;
    }
//
    public void setPaymentSCMId(int paymentSCMId) {
        this.paymentSCMId = paymentSCMId;
    }
//
    public int getPaymentSCMCommissionValue() {
        return paymentSCMCommissionValue;
    }

    public void setPaymentSCMCommissionValue(int paymentSCMCommissionValue) {
        this.paymentSCMCommissionValue = paymentSCMCommissionValue;
    }
//
//    public int getPaymentAccrualId() {
//        return paymentAccrualId;
//    }
//
//    public void setPaymentAccrualId(int paymentAccuralId) {
//        this.paymentAccrualId = paymentAccuralId;
//    }
//
//    public String getPaymentFlag() {
//        return paymentFlag;
//    }
//
//    public void setPaymentFlag(String paymentFlag) {
//        this.paymentFlag = paymentFlag;
//    }
//
//    public int getPaymentDeductionValue() {
//        return paymentDeductionValue;
//    }
//
//    public void setPaymentDeductionValue(int paymentDeductionValue) {
//        this.paymentDeductionValue = paymentDeductionValue;
//    }
//
    public int getPaymentRecordId() {
        return paymentRecordId;
    }

    public void setPaymentRecordId(int paymentRecordId) {
        this.paymentRecordId = paymentRecordId;
    }

    public String getPaymentStatusTypeName() {
        return paymentStatusTypeName;
    }

    public void setPaymentStatusTypeName(String paymentStatusTypeName) {
        this.paymentStatusTypeName = paymentStatusTypeName;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }
 
 

    public int getPaymentDetailId() {
        return paymentDetailId;
    }

    public void setPaymentDetailId(int paymentDetailId) {
        this.paymentDetailId = paymentDetailId;
    }

    public Date getPaymentEndDate() {
        return paymentEndDate;
    }

    public void setPaymentEndDate(Date paymentEndDate) {
        this.paymentEndDate = paymentEndDate;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public Date getPaymentStartDate() {
        return paymentStartDate;
    }

    public void setPaymentStartDate(Date paymentStartDate) {
        this.paymentStartDate = paymentStartDate;
    }

    public int getPaymentStatusTypeId() {
        return paymentStatusTypeId;
    }

    public void setPaymentStatusTypeId(int paymentStatusTypeId) {
        this.paymentStatusTypeId = paymentStatusTypeId;
    }

    public int getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(int paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
 
     public PaymentModel()
  {
  }

 public PaymentModel(Connection con , ResultSet rs)
  {
      try {
          
          paymentDetailId = rs.getInt("PAYMENT_DETAIL_ID");
          paymentName = rs.getString("PAYMENT_NAME");

          paymentStatusTypeId = rs.getInt("PAYMENT_STATUS_TYPE_ID");       
          paymentTypeId =rs.getInt("PAYMENT_TYPE_ID");
          paymentStartDate =rs.getDate("PAYMENT_START_DATE");
          paymentEndDate   =rs.getDate("PAYMENT_END_DATE");    
          paymentTypeName =rs.getString("PAYMENT_TYPE_NAME");
          
      } catch (Exception e) {
      e.printStackTrace();
      }
  }

//    public int getPaymentMemoId() {
//        return paymentMemoId;
//    }
//
//    public void setPaymentMemoId(int paymentMemoId) {
//        this.paymentMemoId = paymentMemoId;
//    }
//
//    public String getPaymentFrozenFlag() {
//        return paymentFrozenFlag;
//    }
//
//    public void setPaymentFrozenFlag(String paymentFrozenFlag) {
//        this.paymentFrozenFlag = paymentFrozenFlag;
//    }

    public String getDCMName() {
        return DCMName;
    }

    public void setDCMName(String DCMName) {
        this.DCMName = DCMName;
    }

   

   

   
  
  
  



}
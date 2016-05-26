package com.mobinil.sds.core.system.payment.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PaymentModel 
{
  int paymentID = 0;
  String paymentName = "";
  int paymentStatusID = 0;
  String paymentStatusName = "";
  int paymentTypeID =0;
  int paymentTypeStatusID =0;
  String paymentTypeStatusName ="";
  String paymentTypeName ="";
  Date paymentStartDate ;
  Date paymentEndDate ;

  
  public int getPaymentID()
  {
  	return paymentID;
  }
  public void setPaymentID(int newPaymentID)
  {
  	paymentID = newPaymentID;
  }

  public String getPaymentName()
  {
    return paymentName;
  }
  public void setPaymentName(String newPaymentName)
  { 
  	paymentName = newPaymentName;
  }

  public int getPaymentStatusID()
  {
  	return paymentStatusID;
  }
  public void setPaymentStatusID(int newPaymentStatusID)
  {
  	paymentStatusID = newPaymentStatusID;
  }

  public String getPaymentStatusName()
  {
  	return paymentStatusName;
  }
  public void setPaymentStatusName(String newPaymentStatusName)
  {
  	paymentStatusName = newPaymentStatusName;
  }

  public int getPaymentTypeID()
  {
  	return paymentTypeID;
  }
  public void setPaymentTypeID(int newPaymentTypeID)
  {
  	paymentTypeID = newPaymentTypeID;
  }

  public String getPaymentTypeName()
  {
  	return paymentTypeName;
  }
  public void setPaymentTypeName(String newPaymentTypeName)
  {
  	paymentTypeName = newPaymentTypeName;
  }

  public Date getPaymentStartDate()
  {
  	return paymentStartDate;
  }
  public void setPaymentStartDate(Date newPaymentStartDate)
  {
  	paymentStartDate = newPaymentStartDate;
  }

  public Date getPaymentEndDate()
  {
    return paymentEndDate;
  }
  public void setPaymentEndDate(Date newPaymentEndDate)
  {
  	paymentEndDate = newPaymentEndDate;
  }
  public PaymentModel(Connection con , ResultSet rs) throws Exception
  {
    setPaymentID(rs.getInt("PAYMENT_DETAIL_ID"));
    setPaymentName(rs.getString("PAYMENT_NAME"));
    setPaymentStatusID(rs.getInt("PAYMENT_STATUS_TYPE_ID"));
    setPaymentStatusName(rs.getString("PAYMENT_STATUS_TYPE_NAME"));
    setPaymentTypeID(rs.getInt("PAYMENT_TYPE_ID"));
    setPaymentTypeName(rs.getString("PAYMENT_TYPE_NAME"));
    setPaymentStartDate(rs.getDate("PAYMENT_START_DATE"));
    setPaymentEndDate(rs.getDate("PAYMENT_END_DATE"));

  }

  public PaymentModel()
  {
  }
}
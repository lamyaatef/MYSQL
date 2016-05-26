package com.mobinil.sds.core.system.sop.requests.model;
import java.sql.*;
import java.io.*;

public class InvoiceModel implements Serializable
{
  String dcmId;
  String totalAmount;
  String invoiceNumber;
  String paymentSerialNumber;
  Date paymentDate;
  String schemaProductId;
  String productQuantity;
  String productPrice;
  String invoiceDetailId;

  public static final String DCM_ID = "DCM_ID";
  public static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
  public static final String INVOICE_NUMBER = "INVOICE_NUMBER";
  public static final String PAYMENT_SERIAL_NUMBER = "PAYMENT_SERIAL_NUMBER";
  public static final String PAYMENT_DATE = "PAYMENT_DATE";
  public static final String SCHEMA_PRODUCT_ID = "SCHEMA_PRODUCT_ID";
  public static final String PRODUCT_QUANTITY = "PRODUCT_QUANTITY";
  public static final String PRODUCT_PRICE = "PRODUCT_PRICE";
  public static final String INVOICE_DETAIL_ID = "INVOICE_DETAIL_ID";
  

  public InvoiceModel()
  {
    
  }

  public InvoiceModel(ResultSet res)
  {
    try
    {
     dcmId = res.getString(DCM_ID); 
     totalAmount = res.getString(TOTAL_AMOUNT);
     invoiceNumber = res.getString(INVOICE_NUMBER);
     paymentSerialNumber = res.getString(PAYMENT_SERIAL_NUMBER);
     paymentDate = res.getDate(PAYMENT_DATE);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

  public String getDcmId()
  {
    return dcmId;
  }
  public void setDcmId(String newDcmId)
  {
    dcmId = newDcmId;
  }
////////////////////////////////////////
 public String getTotalAmount()
 {
   return totalAmount;
 }
 public void setTotalAmount(String newTotalAmount)
 {
   totalAmount = newTotalAmount;
 }
/////////////////////////////////////////////////
 public String getInvoiceNumber()
 {
   return invoiceNumber;  
 }
 public void setInvoiceNumber(String newInvoiceNumber)
 {
   invoiceNumber = newInvoiceNumber;
 }
////////////////////////////////////////////////////
 public String getPaymentSerialNumber()
 {
   return paymentSerialNumber;
 }
 public void setPaymentSerialNumber(String newPaymentSerialNumber)
 {
   paymentSerialNumber = newPaymentSerialNumber;
 }
////////////////////////////////////////////////////////////
 public Date getPaymentDate()
 {
   return paymentDate;
 }
 public void setPaymentDate(Date newPaymentDate)
 {
   paymentDate = newPaymentDate;
 }
//////////////////////////////////////////////////////////
 public String getSchemaProductId()
 {
   return schemaProductId;
 }
 public void setSchemaProductId(String newSchemaProductId)
 {
    schemaProductId = newSchemaProductId;
 }
////////////////////////////////////////////////////////////
 public String getProductQuantity()
 {
   return productQuantity;
 }
 public void setProductQuantity(String newproductQuantity)
 {
   productQuantity = newproductQuantity;
 }
//////////////////////////////////////////////////////////////
 public String getProductPrice()
 {
   return productPrice;
 }
 public void setProductPrice(String newProductPrice)
 {
   productPrice = newProductPrice;
 }
//////////////////////////////////////////////////////////////////
 public String getInvoiceDetailId()
 {
   return invoiceDetailId;
 }
 public void setInvoiceDetailId(String newInvoiceDetailId)
 {
   invoiceDetailId = newInvoiceDetailId;
 }
}
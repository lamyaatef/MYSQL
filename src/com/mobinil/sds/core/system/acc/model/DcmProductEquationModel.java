package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;

public class DcmProductEquationModel implements Serializable{
	
	String dcmId;
	String dcmName;
	String dcmValue;
	String productId;
	String productName;
	String productValue;
	String transactionTypeId;
	String lineCount;
	String eValue;
	public String getDcmId() {
		return dcmId;
	}
	public void setDcmId(String dcmId) {
		this.dcmId = dcmId;
	}
	public String getDcmName() {
		return dcmName;
	}
	public void setDcmName(String dcmName) {
		this.dcmName = dcmName;
	}
	public String getDcmValue() {
		return dcmValue;
	}
	public void setDcmValue(String dcmValue) {
		this.dcmValue = dcmValue;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductValue() {
		return productValue;
	}
	public void setProductValue(String productValue) {
		this.productValue = productValue;
	}
	public String getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(String transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	public String getLineCount() {
		return lineCount;
	}
	public void setLineCount(String lineCount) {
		this.lineCount = lineCount;
	}
	public String getEValue() {
		return eValue;
	}
	public void setEValue(String value) {
		eValue = value;
	}
	
	public static final String DCM_ID = "DCM_ID";
	public static final String DCM_NAME = "DCM_NAME";
	public static final String DCM_VALUE = "DCM_VALUE";
	public static final String PRODUCT_ID = "PRODUCT_ID";
	public static final String PRODUCT_NAME = "PRODUCT_NAME";
	public static final String PRODUCT_VALUE = "PRODUCT_VALUE";
	public static final String TRANSACTION_TYPE_ID = "TRANSACTION_TYPE_ID";
	public static final String LINE_COUNT = "LINE_COUNT";
	public static final String EVALUE = "EVALUE";
	
	public DcmProductEquationModel()
	  {
	  }
	public DcmProductEquationModel(ResultSet res)
	  {
	    try
	    {
	    	dcmId = res.getString(DCM_ID);
	    	dcmName = res.getString(DCM_NAME);
	    	dcmValue = res.getString(DCM_VALUE);
	    	productId = res.getString(PRODUCT_ID);
	    	productName = res.getString(PRODUCT_NAME);
	    	productValue = res.getString(PRODUCT_VALUE);
	    	transactionTypeId = res.getString(TRANSACTION_TYPE_ID);
	    	lineCount = res.getString(LINE_COUNT);
	    	eValue = res.getString(EVALUE);
	     
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	  }
	

}

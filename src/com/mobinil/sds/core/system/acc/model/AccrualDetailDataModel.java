package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;


public class AccrualDetailDataModel {
	
	String accAccrualValueDetialId;
	String dcmId;
	String productId;
	String lineCount;
	String dcmValue;
	String productValue;
	String accrualValue;
	String accAccrualValueId;
	String dcmName;
	String productName;
	public String getAccAccrualValueDetialId() {
		return accAccrualValueDetialId;
	}
	public void setAccAccrualValueDetialId(String accAccrualValueDetialId) {
		this.accAccrualValueDetialId = accAccrualValueDetialId;
	}
	public String getDcmId() {
		return dcmId;
	}
	public void setDcmId(String dcmId) {
		this.dcmId = dcmId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getLineCount() {
		return lineCount;
	}
	public void setLineCount(String lineCount) {
		this.lineCount = lineCount;
	}
	public String getDcmValue() {
		return dcmValue;
	}
	public void setDcmValue(String dcmValue) {
		this.dcmValue = dcmValue;
	}
	public String getProductValue() {
		return productValue;
	}
	public void setProductValue(String productValue) {
		this.productValue = productValue;
	}
	public String getAccrualValue() {
		return accrualValue;
	}
	public void setAccrualValue(String accrualValue) {
		this.accrualValue = accrualValue;
	}
	public String getAccAccrualValueId() {
		return accAccrualValueId;
	}
	public void setAccAccrualValueId(String accAccrualValueId) {
		this.accAccrualValueId = accAccrualValueId;
	}
	public String getDcmName() {
		return dcmName;
	}
	public void setDcmName(String dcmName) {
		this.dcmName = dcmName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public static final String ACC_ACCRUAL_VALUE_DETAIL_ID = "ACC_ACCRUAL_VALUE_DETAIL_ID";
	public static final String DCM_ID = "DCM_ID";
	public static final String PRODUCT_ID = "PRODUCT_ID";
	public static final String LINE_COUNT = "LINE_COUNT";
	public static final String DCM_VALUE = "DCM_VALUE";
	public static final String PRODUCT_VALUE = "PRODUCT_VALUE";
	public static final String ACCRUAL_VALUE = "ACCRUAL_VALUE";
	public static final String ACC_ACCRUAL_VALUE_ID = "ACC_ACCRUAL_VALUE_ID";
	public static final String DCM_NAME = "DCM_NAME";
	public static final String PRODUCT_NAME = "PRODUCT_NAME";
	
	public AccrualDetailDataModel(){
		
	}
	
	public AccrualDetailDataModel(ResultSet res){
		try
		{
			accAccrualValueDetialId = res.getString(ACC_ACCRUAL_VALUE_DETAIL_ID);
			dcmId = res.getString(DCM_ID);
			productId = res.getString(PRODUCT_ID);
			lineCount = res.getString(LINE_COUNT);
			dcmValue = res.getString(DCM_VALUE);
			productValue = res.getString(PRODUCT_VALUE);
			accrualValue = res.getString(ACCRUAL_VALUE);
			accAccrualValueId = res.getString(ACC_ACCRUAL_VALUE_ID);
			dcmName = res.getString(DCM_NAME);
			productName = res.getString(PRODUCT_NAME);
		}
		
		catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	}
}

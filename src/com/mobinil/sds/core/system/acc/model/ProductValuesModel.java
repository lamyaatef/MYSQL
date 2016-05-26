package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;


public class ProductValuesModel implements Serializable{
	
	String productId;
	String productName;
	String productValue;
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
	
	public static final String PRODUCT_ID = "PRODUCT_ID";
	public static final String PRODUCT_NAME = "PRODUCT_NAME";
	public static final String PRODUCT_VALUE = "PRODUCT_VALUE";
	
	public ProductValuesModel()
	  {
	  }
	
	public ProductValuesModel(ResultSet res)
	  {
	    try
	    {
	    	productId = res.getString(PRODUCT_ID);
	    	productName = res.getString(PRODUCT_NAME);
	    	productValue = res.getString(PRODUCT_VALUE);
	     
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	  }

}

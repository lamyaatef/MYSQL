package com.mobinil.sds.core.system.dataMigration.model;
import java.io.Serializable;
import java.sql.ResultSet;

public class ProductModel implements Serializable{
	
	String productId;
	String productName;
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
	
	public static final String PRODUCT_ID = "PRODUCT_ID";
	public static final String PRODUCT_NAME = "PRODUCT_NAME";
	
	public ProductModel(){
		
	}
	public ProductModel(ResultSet res )
	{
		try
		{
			productId = res.getString(PRODUCT_ID);
			productName = res.getString(PRODUCT_NAME);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

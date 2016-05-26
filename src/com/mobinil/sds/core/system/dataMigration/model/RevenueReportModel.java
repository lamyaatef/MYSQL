package com.mobinil.sds.core.system.dataMigration.model;
import java.io.Serializable;
import java.sql.ResultSet;

public class RevenueReportModel implements Serializable{
	
	String dcmName;
	String productId;
	String productName;
	String categoryName;
	String countOfLines;
	String countOfPositiveU1;
	String countOfNegativeU1;
	String countOfZeroU1;
	String rpuU1;
	String countOfPositiveU2;
	String countOfNegativeU2;
	String countOfZeroU2;
	String rpuU2;
	String countOfPositiveU3;
	String countOfNegativeU3;
	String countOfZeroU3;
	String rpuU3;
	String countOfPositiveU4;
	String countOfNegativeU4;
	String countOfZeroU4;
	String rpuU4;
	String countUniquePositive ;
	
	public String getUniquePositive()
	{
		return countUniquePositive;
	}
	public String getDcmName() {
		return dcmName;
	}
	public void setDcmName(String dcmName) {
		this.dcmName = dcmName;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCountOfLines() {
		return countOfLines;
	}
	public void setCountOfLines(String countOfLines) {
		this.countOfLines = countOfLines;
	}
	public String getCountOfPositiveU1() {
		return countOfPositiveU1;
	}
	public void setCountOfPositiveU1(String countOfPositiveU1) {
		this.countOfPositiveU1 = countOfPositiveU1;
	}
	public String getCountOfNegativeU1() {
		return countOfNegativeU1;
	}
	public void setCountOfNegativeU1(String countOfNegativeU1) {
		this.countOfNegativeU1 = countOfNegativeU1;
	}
	public String getCountOfZeroU1() {
		return countOfZeroU1;
	}
	public void setCountOfZeroU1(String countOfZeroU1) {
		this.countOfZeroU1 = countOfZeroU1;
	}
	public String getRpuU1() {
		return rpuU1;
	}
	public void setRpuU1(String rpuU1) {
		this.rpuU1 = rpuU1;
	}
	public String getCountOfPositiveU2() {
		return countOfPositiveU2;
	}
	public void setCountOfPositiveU2(String countOfPositiveU2) {
		this.countOfPositiveU2 = countOfPositiveU2;
	}
	public String getCountOfNegativeU2() {
		return countOfNegativeU2;
	}
	public void setCountOfNegativeU2(String countOfNegativeU2) {
		this.countOfNegativeU2 = countOfNegativeU2;
	}
	public String getCountOfZeroU2() {
		return countOfZeroU2;
	}
	public void setCountOfZeroU2(String countOfZeroU2) {
		this.countOfZeroU2 = countOfZeroU2;
	}
	public String getRpuU2() {
		return rpuU2;
	}
	public void setRpuU2(String rpuU2) {
		this.rpuU2 = rpuU2;
	}
	public String getCountOfPositiveU3() {
		return countOfPositiveU3;
	}
	public void setCountOfPositiveU3(String countOfPositiveU3) {
		this.countOfPositiveU3 = countOfPositiveU3;
	}
	public String getCountOfNegativeU3() {
		return countOfNegativeU3;
	}
	public void setCountOfNegativeU3(String countOfNegativeU3) {
		this.countOfNegativeU3 = countOfNegativeU3;
	}
	public String getCountOfZeroU3() {
		return countOfZeroU3;
	}
	public void setCountOfZeroU3(String countOfZeroU3) {
		this.countOfZeroU3 = countOfZeroU3;
	}
	public String getRpuU3() {
		return rpuU3;
	}
	public void setRpuU3(String rpuU3) {
		this.rpuU3 = rpuU3;
	}
	public String getCountOfPositiveU4() {
		return countOfPositiveU4;
	}
	public void setCountOfPositiveU4(String countOfPositiveU4) {
		this.countOfPositiveU4 = countOfPositiveU4;
	}
	public String getCountOfNegativeU4() {
		return countOfNegativeU4;
	}
	public void setCountOfNegativeU4(String countOfNegativeU4) {
		this.countOfNegativeU4 = countOfNegativeU4;
	}
	public String getCountOfZeroU4() {
		return countOfZeroU4;
	}
	public void setCountOfZeroU4(String countOfZeroU4) {
		this.countOfZeroU4 = countOfZeroU4;
	}
	public String getRpuU4() {
		return rpuU4;
	}
	public void setRpuU4(String rpuU4) {
		this.rpuU4 = rpuU4;
	}
	
	public static final String DCM_NAME = "DCM_NAME";
	public static final String PRODUCT_ID = "PRODUCT_ID";
	public static final String PRODUCT_NAME = "PRODUCT_NAME";
	public static final String CATEGORY_NAME = "CATEGORY_NAME";
	public static final String COUNT_OF_LINES = "COUNT_OF_LINES";
	public static final String COUNT_OF_POSITIVE_U1 = "COUNT_OF_POSITIVE_U1";
	public static final String COUNT_OF_NEGATIVE_U1 = "COUNT_OF_NEGATIVE_U1";
	public static final String COUNT_OF_ZERO_U1 = "COUNT_OF_ZERO_U1";
	public static final String RPU_U1 = "RPU_U1";
	public static final String COUNT_OF_POSITIVE_U2 = "COUNT_OF_POSITIVE_U2";
	public static final String COUNT_OF_NEGATIVE_U2 = "COUNT_OF_NEGATIVE_U2";
	public static final String COUNT_OF_ZERO_U2 = "COUNT_OF_ZERO_U2";
	public static final String RPU_U2 = "RPU_U2";
	public static final String COUNT_OF_POSITIVE_U3 = "COUNT_OF_POSITIVE_U3";
	public static final String COUNT_OF_NEAGTIVE_U3 = "COUNT_OF_NEGATIVE_U3";
	public static final String COUNT_OF_ZERO_U3 = "COUNT_OF_ZERO_U3";
	public static final String RPU_U3 = "RPU_U3";
	public static final String COUNT_OF_POSITIVE_U4 = "COUNT_OF_POSITIVE_U4";
	public static final String COUNT_OF_NEGATIVE_U4 = "COUNT_OF_NEGATIVE_U4";
	public static final String COUNT_OF_ZERO_U4 = "COUNT_OF_ZERO_U4";
	public static final String RPU_U4 = "RPU_U4";
	public static final String UNIQUE_POSITIVE = "count_unique_positive";
	
	public RevenueReportModel(){
		
	}
	
	public RevenueReportModel(ResultSet res)
	{
		try
		{
			dcmName = res.getString(DCM_NAME);
			productId = res.getString(PRODUCT_ID);
			productName = res.getString(PRODUCT_NAME);
		    categoryName = res.getString(CATEGORY_NAME);
		    countOfLines = res.getString(COUNT_OF_LINES);
		    countOfPositiveU1 = res.getString(COUNT_OF_POSITIVE_U1);
		    countOfNegativeU1 = res.getString(COUNT_OF_NEGATIVE_U1);
		    countOfZeroU1 = res.getString(COUNT_OF_ZERO_U1);
		    rpuU1 = res.getString(RPU_U1);
		    countOfPositiveU2 = res.getString(COUNT_OF_POSITIVE_U2);
		    countOfNegativeU2 = res.getString(COUNT_OF_NEGATIVE_U2);
		    countOfZeroU2 = res.getString(COUNT_OF_ZERO_U3);
		    rpuU2 = res.getString(RPU_U2);
		    countOfPositiveU3 = res.getString(COUNT_OF_POSITIVE_U3);
		    countOfNegativeU3 = res.getString(COUNT_OF_NEAGTIVE_U3);
		    countOfZeroU3 =  res.getString(COUNT_OF_ZERO_U3);
		    rpuU3 = res.getString(RPU_U3);
		    countOfPositiveU4 = res.getString(COUNT_OF_POSITIVE_U4);
		    countOfNegativeU4 =  res.getString(COUNT_OF_NEGATIVE_U4);
		    countOfZeroU4 = res.getString(COUNT_OF_ZERO_U4);
		    rpuU4 = res.getString(RPU_U4);
		    countUniquePositive = res.getString(UNIQUE_POSITIVE);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;

public class WithHoldTaxModel implements Serializable{
	
	String dcmCode;
	float withHoldTax;
	public String getDcmCode() {
		return dcmCode;
	}
	public void setDcmCode(String dcmCode) {
		this.dcmCode = dcmCode;
	}
	public float getWithHoldTax() {
		return withHoldTax;
	}
	public void setWithHoldTax(float withHoldTax) {
		this.withHoldTax = withHoldTax;
	}
	
	public static final String DCM_CODE = "DCM_CODE";
	public static final String WITH_HOLD_TAX = "WITH_HOLD_TAX";
	
	public WithHoldTaxModel(){
		
	}
	public WithHoldTaxModel(ResultSet res)
	{
		try
		{
			dcmCode = res.getString(DCM_CODE);
			withHoldTax = res.getFloat(WITH_HOLD_TAX);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

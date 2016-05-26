package com.mobinil.sds.core.system.aacm.model;
import java.sql.*;
import java.io.*;

public class AMSPortfolioModel implements Serializable{
	
	String accountName;
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	String accountBSCSCode;
	public String getAccountBSCSCode() {
		return accountBSCSCode;
	}
	public void setAccountBSCSCode(String accountBSCSCode) {
		this.accountBSCSCode = accountBSCSCode;
	}
	
	public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
	public static final String ACCOUNT_BSCS_CODE = "ACCOUNT_BSCS_CODE";
	
	public AMSPortfolioModel(){
		
	}
	public AMSPortfolioModel(ResultSet res)
	{
		try
		{
			accountName = res.getString(ACCOUNT_NAME);
			accountBSCSCode = res.getString(ACCOUNT_BSCS_CODE);
		}
		catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	}

}

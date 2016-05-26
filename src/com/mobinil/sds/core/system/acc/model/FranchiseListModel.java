package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;

public class FranchiseListModel implements Serializable{
	String franchiseGroupId;
	String franchiseCode;
	public String getFranchiseGroupId() {
		return franchiseGroupId;
	}
	public void setFranchiseGroupId(String franchiseGroupId) {
		this.franchiseGroupId = franchiseGroupId;
	}
	public String getFranchiseCode() {
		return franchiseCode;
	}
	public void setFranchiseCode(String franchiseCode) {
		this.franchiseCode = franchiseCode;
	}
	
	public static final String FRANCHISE_GROUP_ID = "FRANCHISE_GROUP_ID";
	public static final String FRANCHISE_CODE = "FRANCHISE_CODE";
	
	public FranchiseListModel(){
		
	}
	public FranchiseListModel(ResultSet res)
	{
		try
		{
			franchiseGroupId = res.getString(FRANCHISE_GROUP_ID);
			franchiseCode = res.getString(FRANCHISE_CODE);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

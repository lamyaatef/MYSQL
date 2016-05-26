package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;

public class FranchiseGroupModel implements Serializable{
	String franchiseGroupId;
	String franchiseGroupName;
	String franchiseGroupDescription;
	public String getFranchiseGroupId() {
		return franchiseGroupId;
	}
	public void setFranchiseGroupId(String franchiseGroupId) {
		this.franchiseGroupId = franchiseGroupId;
	}
	public String getFranchiseGroupName() {
		return franchiseGroupName;
	}
	public void setFranchiseGroupName(String franchiseGroupName) {
		this.franchiseGroupName = franchiseGroupName;
	}
	public String getFranchiseGroupDescription() {
		return franchiseGroupDescription;
	}
	public void setFranchiseGroupDescription(String franchiseGroupDescription) {
		this.franchiseGroupDescription = franchiseGroupDescription;
	}
	
	public static final String ACC_FRANCHISE_GROUP_ID = "ACC_FRANCHISE_GROUP_ID";
	public static final String FRANCHISE_GROUP_NAME = "FRANCHISE_GROUP_NAME";
	public static final String FRANCHISE_GROUP_DESCRIPTION = "FRANCHISE_GROUP_DESCRIPTION";
	
	public FranchiseGroupModel(){
		
	}
	public FranchiseGroupModel(ResultSet res){
		try
		{
			franchiseGroupId = res.getString(ACC_FRANCHISE_GROUP_ID);
			franchiseGroupName = res.getString(FRANCHISE_GROUP_NAME);
			franchiseGroupDescription = res.getString(FRANCHISE_GROUP_DESCRIPTION);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

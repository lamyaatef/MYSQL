package com.mobinil.sds.core.system.nomadFile.model;
import com.mobinil.sds.core.system.authenticationResult.model.*;
import java.sql.*;
import java.io.*;

public class NomadFileUserLabelModel implements Serializable{
	
	String userId;
	String labelId;
	
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public static final String USER_ID = "USER_ID";
	public static final String LABEL_ID = "LABEL_ID";
	
	public NomadFileUserLabelModel(){
		
	}
	
	public NomadFileUserLabelModel(ResultSet res)
	{
		try
		{
			userId = res.getString(USER_ID);
			labelId = res.getString(LABEL_ID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

package com.mobinil.sds.core.system.acc.model;
import java.sql.*;
import java.io.*;

public class CommissionDcmFixModel implements Serializable{
	
	String newDcmId;
	String newDcmCode;
	String newDcmName;
	String oldDcmId;
	String oldDcmCode;
	String oldDcmName;
	
	public String getNewDcmId() {
		return newDcmId;
	}

	public void setNewDcmId(String newDcmId) {
		this.newDcmId = newDcmId;
	}

	public String getNewDcmCode() {
		return newDcmCode;
	}

	public void setNewDcmCode(String newDcmCode) {
		this.newDcmCode = newDcmCode;
	}

	public String getNewDcmName() {
		return newDcmName;
	}

	public void setNewDcmName(String newDcmName) {
		this.newDcmName = newDcmName;
	}

	public String getOldDcmId() {
		return oldDcmId;
	}

	public void setOldDcmId(String oldDcmId) {
		this.oldDcmId = oldDcmId;
	}

	public String getOldDcmCode() {
		return oldDcmCode;
	}

	public void setOldDcmCode(String oldDcmCode) {
		this.oldDcmCode = oldDcmCode;
	}

	public String getOldDcmName() {
		return oldDcmName;
	}

	public void setOldDcmName(String oldDcmName) {
		this.oldDcmName = oldDcmName;
	}
	
	public static final String OLD_DCM_ID = "OLD_DCM_ID";
	public static final String OLD_DCM_CODE = "OLD_DCM_CODE";
	public static final String OLD_DCM_NAME = "OLD_DCM_NAME";
	public static final String NEW_DCM_ID = "NEW_DCM_ID";
	public static final String NEW_DCM_CODE = "NEW_DCM_CODE";
	public static final String NEW_DCM_NAME = "NEW_DCM_NAME";
	
	public CommissionDcmFixModel()
	{
		
	}
	
	public CommissionDcmFixModel(ResultSet res)
	{
		try
		{
			oldDcmId = res.getString(OLD_DCM_ID);
			oldDcmCode = res.getString(OLD_DCM_CODE);
			oldDcmName = res.getString(OLD_DCM_NAME);
			newDcmId = res.getString(NEW_DCM_ID);
			newDcmCode = res.getString(NEW_DCM_CODE);
			newDcmName = res.getString(NEW_DCM_NAME);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

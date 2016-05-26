package com.mobinil.sds.core.system.ccm.addAndWin.model;
import java.sql.ResultSet;
public class AddAndWinShopAssignmentModel {
	
	   private String DCM_ID;
	    private String DCM_CODE;
	    private String DCM_NAME;
	    private String ADDANDWIN_ID;
	    private String SHOP_ID;
	    private String SHOP_NAME;
	

public AddAndWinShopAssignmentModel()
{
	
}



	    public String getDCM_ID() {
	return DCM_ID;
}



public void setDCM_ID(String dcm_id) {
	DCM_ID = dcm_id;
}



public String getDCM_CODE() {
	return DCM_CODE;
}



public void setDCM_CODE(String dcm_code) {
	DCM_CODE = dcm_code;
}



public String getDCM_NAME() {
	return DCM_NAME;
}



public void setDCM_NAME(String dcm_name) {
	DCM_NAME = dcm_name;
}



public String getADDANDWIN_ID() {
	return ADDANDWIN_ID;
}



public void setADDANDWIN_ID(String addandwin_id) {
	ADDANDWIN_ID = addandwin_id;
}



public String getSHOP_ID() {
	return SHOP_ID;
}



public void setSHOP_ID(String shop_id) {
	SHOP_ID = shop_id;
}



public String getSHOP_NAME() {
	return SHOP_NAME;
}



public void setSHOP_NAME(String shop_name) {
	SHOP_NAME = shop_name;
}



		public AddAndWinShopAssignmentModel(ResultSet res)
	    {
	      try
	      {
	    	  DCM_ID = res.getString("DCM_ID");
	    	  DCM_CODE = res.getString("DCM_CODE");
	    	  DCM_NAME = res.getString("DCM_NAME");
	    	  ADDANDWIN_ID = res.getString("ADDANDWIN_ID");
	    	  SHOP_ID = res.getString("SHOP_ID");
	    	  SHOP_NAME = res.getString("SHOP_NAME");
 }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}

package com.mobinil.sds.core.system.authenticationResult.model;

import java.sql.ResultSet;



public class AuthResSearchDataModel {
	
	   private String   SEARCH_ID;
	    private String  SIM_SERIAL ;
	    private String   DIAL ;
	    private String   ACTIVATION_DATE ;
	    private String   POS_CODE ;
	    private String   SECOND_POS_CODE ;
	    private String   STF_BATCH_DATE;
	    private String  CIF_BATCH_DATE ;
	    private String   SHEET_DISTRIBUTOR_CODE ;
	    private String   SHEET_POS_CODE ;
	    private String   LCS_DCM_ID ;
	    private String   LCS_INIT_DATE ;
	    private String   LCS_CONTRACT_TYPE_ID ;
	    private String   SECOND_POS_NAME ;
	    private String   SHEET_SUPER_DEALER ;

public String getSEARCH_ID() {
			return SEARCH_ID;
		}


		public void setSEARCH_ID(String search_id) {
			SEARCH_ID = search_id;
		}


		public String getSIM_SERIAL() {
			return SIM_SERIAL;
		}


		public void setSIM_SERIAL(String sim_serial) {
			SIM_SERIAL = sim_serial;
		}


		public String getDIAL() {
			return DIAL;
		}


		public void setDIAL(String dial) {
			DIAL = dial;
		}


		public String getACTIVATION_DATE() {
			return ACTIVATION_DATE;
		}


		public void setACTIVATION_DATE(String activation_date) {
			ACTIVATION_DATE = activation_date;
		}


		public String getPOS_CODE() {
			return POS_CODE;
		}


		public void setPOS_CODE(String pos_code) {
			POS_CODE = pos_code;
		}


		public String getSECOND_POS_CODE() {
			return SECOND_POS_CODE;
		}


		public void setSECOND_POS_CODE(String second_pos_code) {
			SECOND_POS_CODE = second_pos_code;
		}


		public String getSTF_BATCH_DATE() {
			return STF_BATCH_DATE;
		}


		public void setSTF_BATCH_DATE(String stf_batch_date) {
			STF_BATCH_DATE = stf_batch_date;
		}


		public String getCIF_BATCH_DATE() {
			return CIF_BATCH_DATE;
		}


		public void setCIF_BATCH_DATE(String cif_batch_date) {
			CIF_BATCH_DATE = cif_batch_date;
		}


		public String getSHEET_DISTRIBUTOR_CODE() {
			return SHEET_DISTRIBUTOR_CODE;
		}


		public void setSHEET_DISTRIBUTOR_CODE(String sheet_distributor_code) {
			SHEET_DISTRIBUTOR_CODE = sheet_distributor_code;
		}


		public String getSHEET_POS_CODE() {
			return SHEET_POS_CODE;
		}


		public void setSHEET_POS_CODE(String sheet_pos_code) {
			SHEET_POS_CODE = sheet_pos_code;
		}


		public String getLCS_DCM_ID() {
			return LCS_DCM_ID;
		}


		public void setLCS_DCM_ID(String lcs_dcm_id) {
			LCS_DCM_ID = lcs_dcm_id;
		}


		public String getLCS_INIT_DATE() {
			return LCS_INIT_DATE;
		}


		public void setLCS_INIT_DATE(String lcs_init_date) {
			LCS_INIT_DATE = lcs_init_date;
		}


		public String getLCS_CONTRACT_TYPE_ID() {
			return LCS_CONTRACT_TYPE_ID;
		}


		public void setLCS_CONTRACT_TYPE_ID(String lcs_contract_type_id) {
			LCS_CONTRACT_TYPE_ID = lcs_contract_type_id;
		}


		public String getSECOND_POS_NAME() {
			return SECOND_POS_NAME;
		}


		public void setSECOND_POS_NAME(String second_pos_name) {
			SECOND_POS_NAME = second_pos_name;
		}


		public String getSHEET_SUPER_DEALER() {
			return SHEET_SUPER_DEALER;
		}


		public void setSHEET_SUPER_DEALER(String sheet_super_dealer) {
			SHEET_SUPER_DEALER = sheet_super_dealer;
		}


public AuthResSearchDataModel()
{
	
}


public AuthResSearchDataModel(ResultSet res)
	    {
	      try
	      {
	    	  
	    	  
	    	  
	   	   SEARCH_ID= res.getString("SEARCH_ID");
		   SIM_SERIAL = res.getString("SIM_SERIAL");
		   DIAL = res.getString("DIAL");
		   ACTIVATION_DATE= res.getString("ACTIVATION_DATE");
		   POS_CODE= res.getString("POS_CODE");
		   SECOND_POS_CODE = res.getString("SECOND_POS_CODE");
		   STF_BATCH_DATE= res.getString("STF_BATCH_DATE");
		   CIF_BATCH_DATE = res.getString("CIF_BATCH_DATE");
		   SHEET_DISTRIBUTOR_CODE = res.getString("SHEET_DISTRIBUTOR_CODE");
		   SHEET_POS_CODE = res.getString("SHEET_POS_CODE");
		   LCS_DCM_ID = res.getString("LCS_DCM_ID");
		   LCS_INIT_DATE= res.getString("LCS_INIT_DATE");
		   LCS_CONTRACT_TYPE_ID = res.getString("LCS_CONTRACT_TYPE_ID");
		   SECOND_POS_NAME = res.getString("SECOND_POS_NAME");
		   SHEET_SUPER_DEALER= res.getString("SHEET_SUPER_DEALER");
	    	  

		    
	    	
}
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}
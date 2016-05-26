package com.mobinil.sds.core.system.dataMigration.model;
import java.sql.ResultSet;
public class POS_PAY_HISModel {
	  
	

		private String   ROW_NUM;
		private String   PHASE_DATE;
	    private String   MEMO_NAME;
	    private String   CODE;
	    private String   TOTAL_AMOUNT;
	    private String   AMOUNT;
	    private String   DONE;
	    private String   MSG; 
	    private String   SALES_TAX;
	    private String   REG_DATE;
	    private String   COMMENT1;
	    private String   COMMENT2;
	    private String   MEMEO_DESC;
	    private String   DEDUCTION_FEES;
	    private String   REMAINED_DEDUCTION;
	    
	    private String   FINANCE_DATE;
	    private String   BUDGET_ID;
	    private String   BUDGET_NAME;
	    
	    
	       
         
	    


public String getFINANCE_DATE() {
			return FINANCE_DATE;
		}


		public void setFINANCE_DATE(String finance_date) {
			FINANCE_DATE = finance_date;
		}


		public String getBUDGET_ID() {
			return BUDGET_ID;
		}


		public void setBUDGET_ID(String budget_id) {
			BUDGET_ID = budget_id;
		}


		public String getBUDGET_NAME() {
			return BUDGET_NAME;
		}


		public void setBUDGET_NAME(String budget_name) {
			BUDGET_NAME = budget_name;
		}


public POS_PAY_HISModel()
{
	
}


		public POS_PAY_HISModel(ResultSet res)
	    {
	      try
	      {
	    	  
	    	  ROW_NUM=res.getString("ROW_NUM");
	    	  PHASE_DATE = res.getString("PHASE_DATE");
	    	  MEMO_NAME = res.getString("MEMO_NAME");
	    	  CODE = res.getString("CODE");
	    	  TOTAL_AMOUNT = res.getString("TOTAL_AMOUNT");
	    	  AMOUNT  = res.getString("AMOUNT");
	    	  DONE = res.getString("DONE");
	    	  MSG = res.getString("MSG");
	    	  SALES_TAX = res.getString("SALES_TAX");
	    	  
	    	  REG_DATE = res.getString("REG_DATE");
	    	  
	    	  COMMENT1 = res.getString("COMMENT1");
	    	  
	    	  
	    	  COMMENT2 = res.getString("COMMENT2");
	    	  MEMEO_DESC = res.getString("MEMEO_DESC");
	    	  DEDUCTION_FEES = res.getString("DEDUCTION_FEES");
              REMAINED_DEDUCTION = res.getString("REMAINED_DEDUCTION");
              
              FINANCE_DATE = res.getString("FINANCE_DATE");
              
              BUDGET_ID = res.getString("BUDGET_ID");
              BUDGET_NAME = res.getString("BUDGET_NAME");
              
              
              
              
          

	    	
     }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }


		public String getROW_NUM() {
			return ROW_NUM;
		}


		public void setROW_NUM(String row_num) {
			ROW_NUM = row_num;
		}


		public String getPHASE_DATE() {
			return PHASE_DATE;
		}


		public void setPHASE_DATE(String phase_date) {
			PHASE_DATE = phase_date;
		}


		public String getMEMO_NAME() {
			return MEMO_NAME;
		}


		public void setMEMO_NAME(String memo_name) {
			MEMO_NAME = memo_name;
		}


		public String getCODE() {
			return CODE;
		}


		public void setCODE(String code) {
			CODE = code;
		}


		public String getTOTAL_AMOUNT() {
			return TOTAL_AMOUNT;
		}


		public void setTOTAL_AMOUNT(String total_amount) {
			TOTAL_AMOUNT = total_amount;
		}


		public String getAMOUNT() {
			return AMOUNT;
		}


		public void setAMOUNT(String amount) {
			AMOUNT = amount;
		}


		public String getDONE() {
			return DONE;
		}


		public void setDONE(String done) {
			DONE = done;
		}


		public String getMSG() {
			return MSG;
		}


		public void setMSG(String msg) {
			MSG = msg;
		}


		public String getSALES_TAX() {
			return SALES_TAX;
		}


		public void setSALES_TAX(String sales_tax) {
			SALES_TAX = sales_tax;
		}


		public String getREG_DATE() {
			return REG_DATE;
		}


		public void setREG_DATE(String reg_date) {
			REG_DATE = reg_date;
		}


		public String getCOMMENT1() {
			return COMMENT1;
		}


		public void setCOMMENT1(String comment1) {
			COMMENT1 = comment1;
		}


		public String getCOMMENT2() {
			return COMMENT2;
		}


		public void setCOMMENT2(String comment2) {
			COMMENT2 = comment2;
		}


		public String getMEMEO_DESC() {
			return MEMEO_DESC;
		}


		public void setMEMEO_DESC(String memeo_desc) {
			MEMEO_DESC = memeo_desc;
		}


		public String getDEDUCTION_FEES() {
			return DEDUCTION_FEES;
		}


		public void setDEDUCTION_FEES(String deduction_fees) {
			DEDUCTION_FEES = deduction_fees;
		}


		public String getREMAINED_DEDUCTION() {
			return REMAINED_DEDUCTION;
		}


		public void setREMAINED_DEDUCTION(String remained_deduction) {
			REMAINED_DEDUCTION = remained_deduction;
		}
}
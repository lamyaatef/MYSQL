package com.mobinil.sds.core.system.dataMigration.model;

import java.sql.ResultSet;



public class POS_ELG_CHKModel {
	  
	

	private String   ROW_NUM;
	private String   POS_CODE;
    private String   POS_ENM;
    private String   ENTRY_DT;
    private String   STK_DLVR_DT;
    private String   IQRAR_DLVR_DT;
    private String   IQRAR_RCV_DT;
    private String   POS_STATUS; 
    private String   STK_STATUS;
    private String   REGIONAL_NAME;
    private String   REP_NAME;
    private String   CHANNEL_CODE;
    private String   LEVEL_CODE;
    private String   STK_DIAL_NO;
   
    
    


public String getSTK_DIAL_NO() {
		return STK_DIAL_NO;
	}


	public void setSTK_DIAL_NO(String stk_dial_no) {
		STK_DIAL_NO = stk_dial_no;
	}


public POS_ELG_CHKModel()
{

}


	public POS_ELG_CHKModel(ResultSet res)
    {
      try
      {
    	  
    	  ROW_NUM=res.getString("ROW_NUM");
    	  POS_CODE = res.getString("POS_CODE");
    	  POS_ENM = res.getString("POS_ENM");
    	  ENTRY_DT = res.getString("ENTRY_DT");
    	  STK_DLVR_DT = res.getString("STK_DLVR_DT");
    	  IQRAR_DLVR_DT  = res.getString("IQRAR_DLVR_DT");
    	  IQRAR_RCV_DT = res.getString("IQRAR_RCV_DT");
    	  POS_STATUS = res.getString("POS_STATUS");
    	  STK_STATUS = res.getString("STK_STATUS");
    	  REGIONAL_NAME = res.getString("REGIONAL_NAME");
          REP_NAME = res.getString("REP_NAME");
          CHANNEL_CODE = res.getString("CHANNEL_CODE");
    	  LEVEL_CODE = res.getString("LEVEL_CODE");
    	  STK_DIAL_NO = res.getString("STK_DIAL_NO");
    	  
    	 
 

    	
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


	public String getPOS_CODE() {
		return POS_CODE;
	}


	public void setPOS_CODE(String pos_code) {
		POS_CODE = pos_code;
	}


	public String getPOS_ENM() {
		return POS_ENM;
	}


	public void setPOS_ENM(String pos_enm) {
		POS_ENM = pos_enm;
	}


	public String getENTRY_DT() {
		return ENTRY_DT;
	}


	public void setENTRY_DT(String entry_dt) {
		ENTRY_DT = entry_dt;
	}


	public String getSTK_DLVR_DT() {
		return STK_DLVR_DT;
	}


	public void setSTK_DLVR_DT(String stk_dlvr_dt) {
		STK_DLVR_DT = stk_dlvr_dt;
	}


	public String getIQRAR_DLVR_DT() {
		return IQRAR_DLVR_DT;
	}


	public void setIQRAR_DLVR_DT(String iqrar_dlvr_dt) {
		IQRAR_DLVR_DT = iqrar_dlvr_dt;
	}


	public String getIQRAR_RCV_DT() {
		return IQRAR_RCV_DT;
	}


	public void setIQRAR_RCV_DT(String iqrar_rcv_dt) {
		IQRAR_RCV_DT = iqrar_rcv_dt;
	}


	public String getPOS_STATUS() {
		return POS_STATUS;
	}


	public void setPOS_STATUS(String pos_status) {
		POS_STATUS = pos_status;
	}


	public String getSTK_STATUS() {
		return STK_STATUS;
	}


	public void setSTK_STATUS(String stk_status) {
		STK_STATUS = stk_status;
	}


	public String getREGIONAL_NAME() {
		return REGIONAL_NAME;
	}


	public void setREGIONAL_NAME(String regional_name) {
		REGIONAL_NAME = regional_name;
	}


	public String getREP_NAME() {
		return REP_NAME;
	}


	public void setREP_NAME(String rep_name) {
		REP_NAME = rep_name;
	}


	public String getCHANNEL_CODE() {
		return CHANNEL_CODE;
	}


	public void setCHANNEL_CODE(String channel_code) {
		CHANNEL_CODE = channel_code;
	}


	public String getLEVEL_CODE() {
		return LEVEL_CODE;
	}


	public void setLEVEL_CODE(String level_code) {
		LEVEL_CODE = level_code;
	}
	
	
}
	
	
package com.mobinil.sds.core.system.dataMigration.model;

import java.sql.ResultSet;




public class POS_DEL_CHKModel {
	  
	

	private String   ROW_NUM;
	private String   DCM_CODE;
    private String   DELIVERED;
    public String getROW_NUM() {
		return ROW_NUM;
	}


	public void setROW_NUM(String row_num) {
		ROW_NUM = row_num;
	}


	public String getDCM_CODE() {
		return DCM_CODE;
	}


	public void setDCM_CODE(String dcm_code) {
		DCM_CODE = dcm_code;
	}


	public String getDELIVERED() {
		return DELIVERED;
	}


	public void setDELIVERED(String delivered) {
		DELIVERED = delivered;
	}


	public String getNI() {
		return NI;
	}


	public void setNI(String ni) {
		NI = ni;
	}


	public String getPROBL() {
		return PROBL;
	}


	public void setPROBL(String probl) {
		PROBL = probl;
	}


	public String getNOTD() {
		return NOTD;
	}


	public void setNOTD(String notd) {
		NOTD = notd;
	}


	public String getACC() {
		return ACC;
	}


	public void setACC(String acc) {
		ACC = acc;
	}


	private String   NI;
    private String   PROBL;
    private String   NOTD;
    private String   ACC;
 
    

   
    
    


public POS_DEL_CHKModel()
{

}


	public POS_DEL_CHKModel(ResultSet res)
    {
      try
      {
    	  
    	  ROW_NUM=res.getString("ROW_NUM");
    	  DCM_CODE = res.getString("DCM_CODE");
    	  DELIVERED = res.getString("DELIVERED");
    	  NI = res.getString("NI");
    	  PROBL = res.getString("PROBL");
    	  NOTD  = res.getString("NOTD");
    	  ACC = res.getString("ACC");

      }
      catch(Exception e)
      {
        e.printStackTrace();
      } 
    }
}

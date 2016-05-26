

package com.mobinil.sds.core.system.dataMigration.model;

import java.sql.ResultSet;




public class PosDelSearchfileDataModel {
	  
	

	private String   SEARCH_ID ;
	private String   DCM_CODE;
    private String   DELIVERED;
    public String getSEARCH_ID () {
		return SEARCH_ID ;
	}


	public void setSEARCH_ID (String SEARCH_ID ) {
		SEARCH_ID  = SEARCH_ID ;
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
 
    

   
    
    


public PosDelSearchfileDataModel()
{

}


	public PosDelSearchfileDataModel(ResultSet res)
    {
      try
      {
    	  
    	  SEARCH_ID =res.getString("SEARCH_ID");
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
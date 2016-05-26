
package com.mobinil.sds.core.system.dataMigration.model;
import java.sql.ResultSet;
public class inValidPosCodeModel {
	
	   private String POS_CODE;
	   private String NO_REPEATED;

public String getPOS_CODE() {
		return POS_CODE;
	}


	public void setPOS_CODE(String pos_code) {
		POS_CODE = pos_code;
	}


public inValidPosCodeModel()
{
	
}


public inValidPosCodeModel(ResultSet res)
	    {
	      try
	      {
              POS_CODE = res.getString("POS_CODE");
	    	  NO_REPEATED=res.getString("NO_REPEATED");
	    

	    	}
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }


public String getNO_REPEATED() {
	return NO_REPEATED;
}


public void setNO_REPEATED(String no_repeated) {
	NO_REPEATED = no_repeated;
}
}

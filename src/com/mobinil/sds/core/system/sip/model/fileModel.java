package com.mobinil.sds.core.system.sip.model;
import java.sql.ResultSet;

import com.mobinil.sds.web.interfaces.sip.SIPInterfaceKey;
public class fileModel {
	
   private String FILE_ID ="";
   private String UPLOADED_DATE;
   private String REPORT_YEAR_ID; 
   private String REPORT_QUARTAR_ID;
   private Integer REPORT_TYPE_ID;
   private String YEAR;
   private String MONTH;
   private String USER_ID;
   
  
	    
	 

	

public String getUPLOADED_DATE()
   {
      return UPLOADED_DATE;
   }


   public void setUPLOADED_DATE(String uPLOADEDDATE)
   {
      UPLOADED_DATE = uPLOADEDDATE;
   }


   public String getREPORT_YEAR_ID()
   {
      return REPORT_YEAR_ID;
   }


   public void setREPORT_YEAR_ID(String rEPORTYEARID)
   {
      REPORT_YEAR_ID = rEPORTYEARID;
   }


   public String getREPORT_QUARTAR_ID()
   {
      return REPORT_QUARTAR_ID;
   }


   public void setREPORT_QUARTAR_ID(String rEPORTQUARTARID)
   {
      REPORT_QUARTAR_ID = rEPORTQUARTARID;
   }


   public Integer getREPORT_TYPE_ID()
   {
      return REPORT_TYPE_ID;
   }


   public void setREPORT_TYPE_ID(Integer rEPORTTYPEID)
   {
      REPORT_TYPE_ID = rEPORTTYPEID;
   }


   public String getYEAR()
   {
      return YEAR;
   }


   public void setYEAR(String yEAR)
   {
      YEAR = yEAR;
   }


   public String getMONTH()
   {
      return MONTH;
   }


   public void setMONTH(String mONTH)
   {
      MONTH = mONTH;
   }


public fileModel()
{
	
}


		public String getFILE_ID() {
	return FILE_ID;
}







public void setFILE_ID(String file_id) {
	FILE_ID = file_id;
}




















		public fileModel(ResultSet res)
	    {
	      try
	      {


//	         private String REPORT_YEAR_ID; 
//	         private String REPORT_QUARTAR_ID;
//	         private String REPORT_TYPE_ID;

	  
		    FILE_ID = res.getString("FILE_ID");
		    YEAR = res.getString("YEAR_NAME");
		    REPORT_TYPE_ID = res.getInt ("REPORT_TYPE");	
		    
		    if (REPORT_TYPE_ID ==SIPInterfaceKey.HIDDEN_PARAM_SIP_REPORT_SD)
		    {
		       
		       int quarterInt = res.getInt("REPORT_TYPE"); 
		       if (quarterInt==1)MONTH = "Jan";
		       if (quarterInt==2)MONTH = "Feb";
		       if (quarterInt==3)MONTH = "Mar";
		       if (quarterInt==4)MONTH = "Apr";
		       if (quarterInt==5)MONTH = "May";
		       if (quarterInt==6)MONTH = "Jun";
		       if (quarterInt==7)MONTH = "Jul";
		       if (quarterInt==8)MONTH = "Aug";
		       if (quarterInt==9)MONTH = "Sep";
		       if (quarterInt==10)MONTH = "Oct";
		       if (quarterInt==11)MONTH = "Nov";
		       if (quarterInt==12)MONTH = "Dec";
		    }
		    else
		    {
		    MONTH = res.getString("REPORT_QUARTAR");
		    if (MONTH!=null&&MONTH.compareTo ( "" )!=0&&MONTH.compareTo ( "0" )!=0)MONTH = "Q"+MONTH;
		    else MONTH=""; 
		       
		    }
		    REPORT_YEAR_ID = res.getString("REPORT_YEAR");
		    REPORT_QUARTAR_ID = res.getString("REPORT_QUARTAR");
		    USER_ID=res.getString("USER_ID");
		    UPLOADED_DATE = res.getString ( "UPLOADED_DATE" );
	    	
 }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }


		public String getUSER_ID() {
			return USER_ID;
		}


		public void setUSER_ID(String uSERID) {
			USER_ID = uSERID;
		}
}

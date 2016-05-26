package com.mobinil.sds.core.system.sop.requests.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;

  public class genProductModel implements Serializable
	  {

		 	  

	//  private  String PRODUCT_ID ;
	  private  String PRODUCT_NAME;
	 // private   String PRODUCT_DESC ;
	//  private   String PRODUCT_CATEGORY_ID ;

	    

	    public genProductModel()
	    {
	      
	    }

	    public genProductModel(ResultSet res)
	    {
	      try
	      {
	    	//  PRODUCT_ID = res.getString("PRODUCT_ID"); 
	    	 PRODUCT_NAME = res.getString("PRODUCT_NAME");
	    	//  PRODUCT_DESC = res.getString("PRODUCT_DESC");
	    	//  PRODUCT_CATEGORY_ID = res.getString("PRODUCT_CATEGORY_ID");
	       
	      }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }

	
		public String getPRODUCT_NAME() {
			return PRODUCT_NAME;
		}

		public void setPRODUCT_NAME(String product_name) {
			PRODUCT_NAME = product_name;
		}

		
	
	 
	  }
	  

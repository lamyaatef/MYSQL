/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.nomadFile.model;

import java.sql.ResultSet;

/**
 *
 * @author sand
 */
public class NomadFileModel {
    
            private String  GEN_DCM_NOMAD_FILE_ID;
	    private String  USER_ID ;
	    private String  FILE_CREATION_DATE ;
	    private String  FILE_UPLOAD_DATE ;
	    private String  TOTAL_NUMBER_OF_RECORDS ;
	    private String  MIN_DATE ;
            private String  MAX_DATE ;
            private String  LABEL_ID;
            private String  STATUS ;
            private String USERNAME;
            
public NomadFileModel()
{
	
}
public NomadFileModel(ResultSet res, String username)
{
    try
	      {
	    	  
	  
		    GEN_DCM_NOMAD_FILE_ID = res.getString("GEN_DCM_NOMAD_FILE_ID");
		    USER_ID = res.getString("USER_ID");
		    FILE_CREATION_DATE = res.getString("FILE_CREATION_DATE");
		    FILE_UPLOAD_DATE = res.getString("FILE_UPLOAD_DATE");
		    TOTAL_NUMBER_OF_RECORDS = res.getString("TOTAL_NUMBER_OF_RECORDS");
		    MIN_DATE = res.getString("MIN_DATE");
		    MAX_DATE = res.getString("MAX_DATE");
                    LABEL_ID = res.getString("LABEL_ID");
                    STATUS = res.getString("STATUS");
                    USERNAME = username;
                }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	
}

    /**
     * @return the GEN_DCM_NOMAD_FILE_ID
     */
    public String getGEN_DCM_NOMAD_FILE_ID() {
        return GEN_DCM_NOMAD_FILE_ID;
    }

    /**
     * @param GEN_DCM_NOMAD_FILE_ID the GEN_DCM_NOMAD_FILE_ID to set
     */
    public void setGEN_DCM_NOMAD_FILE_ID(String GEN_DCM_NOMAD_FILE_ID) {
        this.GEN_DCM_NOMAD_FILE_ID = GEN_DCM_NOMAD_FILE_ID;
    }

    /**
     * @return the USER_ID
     */
    public String getUSER_ID() {
        return USER_ID;
    }

    /**
     * @param USER_ID the USER_ID to set
     */
    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    /**
     * @return the FILE_CREATION_DATE
     */
    public String getFILE_CREATION_DATE() {
        return FILE_CREATION_DATE;
    }

    /**
     * @param FILE_CREATION_DATE the FILE_CREATION_DATE to set
     */
    public void setFILE_CREATION_DATE(String FILE_CREATION_DATE) {
        this.FILE_CREATION_DATE = FILE_CREATION_DATE;
    }

    /**
     * @return the FILE_UPLOAD_DATE
     */
    public String getFILE_UPLOAD_DATE() {
        return FILE_UPLOAD_DATE;
    }

    /**
     * @param FILE_UPLOAD_DATE the FILE_UPLOAD_DATE to set
     */
    public void setFILE_UPLOAD_DATE(String FILE_UPLOAD_DATE) {
        this.FILE_UPLOAD_DATE = FILE_UPLOAD_DATE;
    }

    /**
     * @return the TOTAL_NUMBER_OF_RECORDS
     */
    public String getTOTAL_NUMBER_OF_RECORDS() {
        return TOTAL_NUMBER_OF_RECORDS;
    }

    /**
     * @param TOTAL_NUMBER_OF_RECORDS the TOTAL_NUMBER_OF_RECORDS to set
     */
    public void setTOTAL_NUMBER_OF_RECORDS(String TOTAL_NUMBER_OF_RECORDS) {
        this.TOTAL_NUMBER_OF_RECORDS = TOTAL_NUMBER_OF_RECORDS;
    }

    /**
     * @return the MIN_DATE
     */
    public String getMIN_DATE() {
        return MIN_DATE;
    }

    /**
     * @param MIN_DATE the MIN_DATE to set
     */
    public void setMIN_DATE(String MIN_DATE) {
        this.MIN_DATE = MIN_DATE;
    }

    /**
     * @return the MAX_DATE
     */
    public String getMAX_DATE() {
        return MAX_DATE;
    }

    /**
     * @param MAX_DATE the MAX_DATE to set
     */
    public void setMAX_DATE(String MAX_DATE) {
        this.MAX_DATE = MAX_DATE;
    }

    /**
     * @return the LABEL
     */
  
    /**
     * @return the STATUS
     */
    public String getSTATUS() {
        return STATUS;
    }

    /**
     * @param STATUS the STATUS to set
     */
    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    /**
     * @return the LABEL_ID
     */
    public String getLABEL_ID() {
        return LABEL_ID;
    }

    /**
     * @param LABEL_ID the LABEL_ID to set
     */
    public void setLABEL_ID(String LABEL_ID) {
        this.LABEL_ID = LABEL_ID;
    }

    /**
     * @return the USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }

    /**
     * @param USERNAME the USERNAME to set
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    /**
     * @return the USER_NAME
     */
   

    
}

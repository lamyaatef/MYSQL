/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.scm.dto;

import java.sql.ResultSet;

/**
 *
 * @author SAND
 */
public class STKPaymentLevel {
    
    
   
  String dcmCode;
  String gendcmpaymentLevelid;
  String poscode;
  String posdetaildcmpaymentlevelid;
  
  public static final String DCM_CODE = "DCM_CODE";
  public static final String genDcmPaymentLevelId = "DCM_PAYMENT_LEVEL_ID";
  public static final String posCode = "POS_CODE";
  public static final String posDetaildcmPaymentLevelId = "DCM_PAYMENT_LEVEL_ID";

  public STKPaymentLevel(ResultSet res)
  {
    try
    {
      dcmCode = res.getString(DCM_CODE);
      gendcmpaymentLevelid = res.getString(genDcmPaymentLevelId);
      poscode = res.getString(posCode);
      posdetaildcmpaymentlevelid=res.getString(posDetaildcmPaymentLevelId);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }  
  }

  
  public STKPaymentLevel()
  {
  }

    public String getDcmCode() {
        return dcmCode;
    }

    public void setDcmCode(String dcmCode) {
        this.dcmCode = dcmCode;
    }

    public String getGendcmpaymentLevelid() {
        return gendcmpaymentLevelid;
    }

    public void setGendcmpaymentLevelid(String gendcmpaymentLevelid) {
        this.gendcmpaymentLevelid = gendcmpaymentLevelid;
    }

    public String getPoscode() {
        return poscode;
    }

    public void setPoscode(String poscode) {
        this.poscode = poscode;
    }

    public String getPosdetaildcmpaymentlevelid() {
        return posdetaildcmpaymentlevelid;
    }

    public void setPosdetaildcmpaymentlevelid(String posdetaildcmpaymentlevelid) {
        this.posdetaildcmpaymentlevelid = posdetaildcmpaymentlevelid;
    }
    
  
  
}

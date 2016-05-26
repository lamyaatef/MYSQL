package com.mobinil.sds.core.system.sop.quota.model;
import java.sql.*;
import java.io.*;

public class DCMQuotaModel implements Serializable
{
///////DCM Quota 
  String dcmQuotaId;
  String dcmId;
  String dcmCode;
  String dcmQuota;
  Date validFrom;
  Date validTo;

///////DCM Quota Setting
  String dcmQuotaSettingId;
  int validDays;
  int recalculateDays;
  
  public static final String DCM_QUOTA_ID = "DCM_QUOTA_ID";
  public static final String DCM_ID = "DCM_ID";
  public static final String DCM_CODE = "DCM_CODE";
  public static final String DCM_QUOTA = "DCM_QUOTA";
  public static final String VALID_FROM = "VALID_FROM";
  public static final String VALID_TO = "VALID_TO";

  public static final String DCM_QUOTA_SETTING_ID = "DCM_QUOTA_SETTING_ID";
  public static final String VALID_DAYS = "VALID_DAYS";
  public static final String RECALCULATE_DAYS = "RECALCULATE_DAYS";
  
  public DCMQuotaModel()
  {
  }

  public DCMQuotaModel(ResultSet res)
  {
    try
    {
      dcmQuotaId = res.getString(DCM_QUOTA_ID);
      dcmId = res.getString(DCM_ID);
      dcmQuota = res.getString(DCM_QUOTA);
      validFrom = res.getDate(VALID_FROM);
      validTo = res.getDate(VALID_TO);
    }
    catch(Exception e)
    {
      
    }
  }

  public String getDcmQuotaId()
  {
  return dcmQuotaId;
  }
  public void setDcmQuotaId(String newDcmQuotaId)
  {
  dcmQuotaId = newDcmQuotaId;
  }
  
  public String getDcmId()
  {
  return dcmId;
  }
  public void setDcmId(String newDcmId)
  {
  dcmId = newDcmId;
  }

  public String getDcmCode()
  {
  return dcmCode;
  }
  public void setDcmCode(String newDcmCode)
  {
  dcmCode = newDcmCode;
  }
  
  public String getDcmQuota()
  {
  return dcmQuota;
  }
  public void setDcmQuota(String newDcmQuota)
  {
  dcmQuota = newDcmQuota;
  }
  
  public Date getValidFrom()
  {
  return validFrom;
  }
  public void setValidFrom(Date newValidFrom)
  {
  validFrom = newValidFrom;
  }
  
  public Date getValidTo()
  {
  return validTo;
  }
  public void setValidTo(Date newValidTo)
  {
  validTo = newValidTo;
  }
//////////////////
  public int getValidDays()
  {
  return validDays;
  }
  public void setValidDays(int newValidDays)
  {
  validDays = newValidDays;
  }

  public int getRecalculateDays()
  {
  return recalculateDays;
  }
  public void setRecalculateDays(int newRecalculateDays)
  {
  recalculateDays = newRecalculateDays;
  }

  public String getDcmQuotaSettingId()
  {
  return dcmQuotaSettingId;
  }
  public void setDcmQuotaSettingId(String newDcmQuotaSettingId)
  {
  dcmQuotaSettingId = newDcmQuotaSettingId;
  }
}
package com.mobinil.sds.core.system.sip.dto;

import java.util.ArrayList;

public class sipDistHistoryDTO
{
   private String dcmName;
   private String dcmCount;
   private String dormantCount;
   private String dcmId;
   private String exectivesName;
   private String Scratch;
   private String E_Topup;
   private String GrossAddTotal;
   private String ExPosTotal;
   private String NonExPosTotal;
   private String totalGrossAddsDetail;
   private String sipReportId;
   
   private ArrayList < String > revenueTargetDetail;
   private ArrayList < String > grossAddsDetail;      
   private ArrayList < String > dormantDetail;
   private ArrayList < String >detailedGrossAddsACT;
   private ArrayList < String >detailedExPOSAndNonExPOS;
   
   
   
   
   public ArrayList < String > getDetailedGrossAddsACT()
   {
      return detailedGrossAddsACT;
   }
   public void setDetailedGrossAddsACT(ArrayList < String > detailedGrossAddsACT)
   {
      this.detailedGrossAddsACT = detailedGrossAddsACT;
   }
   public ArrayList < String > getDormantDetail()
   {
      return dormantDetail;
   }
   public void setDormantDetail(ArrayList < String > dormantDetail)
   {
      this.dormantDetail = dormantDetail;
   }
   public ArrayList < String > getGrossAddsDetail()
   {
      return grossAddsDetail;
   }
   public void setGrossAddsDetail(ArrayList < String > grossAddsDetail)
   {
      this.grossAddsDetail = grossAddsDetail;
   }
   public String getTotalGrossAddsDetail()
   {
      return totalGrossAddsDetail;
   }
   public void setTotalGrossAddsDetail(String totalGrossAddsDetail)
   {
      this.totalGrossAddsDetail = totalGrossAddsDetail;
   }
   public ArrayList < String > getRevenueTargetDetail()
   {
      return revenueTargetDetail;
   }
   public void setRevenueTargetDetail(ArrayList < String > revenueTargetDetail)
   {
      this.revenueTargetDetail = revenueTargetDetail;
   }
   public String getExPosTotal()
   {
      return ExPosTotal;
   }
   public void setExPosTotal(String exPosTotal)
   {
      ExPosTotal = exPosTotal;
   }
   public String getNonExPosTotal()
   {
      return NonExPosTotal;
   }
   public void setNonExPosTotal(String nonExPosTotal)
   {
      NonExPosTotal = nonExPosTotal;
   }
   public String getGrossAddTotal()
   {
      return GrossAddTotal;
   }
   public void setGrossAddTotal(String grossAddTotal)
   {
      GrossAddTotal = grossAddTotal;
   }
   public String getScratch()
   {
      return Scratch;
   }
   public void setScratch(String scratch)
   {
      Scratch = scratch;
   }
   public String getE_Topup()
   {
      return E_Topup;
   }
   public void setE_Topup(String eTopup)
   {
      E_Topup = eTopup;
   }
   public String getExectivesName()
   {
      return exectivesName;
   }
   public void setExectivesName(String exectivesName)
   {
      this.exectivesName = exectivesName;
   }
   public String getDcmName()
   {
      return dcmName;
   }
   public void setDcmName(String dcmName)
   {
      this.dcmName = dcmName;
   }
   public String getDcmCount()
   {
      return dcmCount;
   }
   public void setDcmCount(String dcmCount)
   {
      this.dcmCount = dcmCount;
   }
   public String getDormantCount()
   {
      return dormantCount;
   }
   public void setDormantCount(String dormantCount)
   {
      this.dormantCount = dormantCount;
   }
   public String getDcmId()
   {
      return dcmId;
   }
   public void setDcmId(String dcmId)
   {
      this.dcmId = dcmId;
   }
   public void setDetailedExPOSAndNonExPOS(
         ArrayList < String > detailedExPOSAndNonExPOS)
   {
      this.detailedExPOSAndNonExPOS = detailedExPOSAndNonExPOS;
   }
   public ArrayList < String > getDetailedExPOSAndNonExPOS()
   {
      return detailedExPOSAndNonExPOS;
   }
   public void setSipReportId(String sipReportId)
   {
      this.sipReportId = sipReportId;
   }
   public String getSipReportId()
   {
      return sipReportId;
   }
   
   
   
}

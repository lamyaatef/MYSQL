 package com.mobinil.sds.core.system.sip.exportEngine;

public class gatheringDistDataThread extends Thread
{
   String report_id;
   String labelId;
   String quartar;
   String year;
   String userId;
   String sipReprotCategory ;
   
   public gatheringDistDataThread(String report_id,String sipReprotCategory, String labelId, String quartar, String year,String userId){
      this.labelId = labelId;
      this.quartar = quartar;
      this.report_id = report_id;      
      this.year = year;
      this.userId = userId;
      this.sipReprotCategory = sipReprotCategory ;
      
      
      
   }
   
   public void run (){
     try
   {
      Thread.sleep ( 500 );  
      System.out.println("Begining of Gathering Data Thread");
      DAO.calculateSIP(report_id,sipReprotCategory,labelId,quartar,year,userId);
      System.out.println("End of Gathering Data Thread");
   }
   catch ( Exception e )
   {
      // TODO Auto-generated catch block
      e.printStackTrace();
   }
   }
   
   
}

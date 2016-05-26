package com.mobinil.sds.core.system.sa.scratch_card.dao;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;
import java.util.*;
import com.mobinil.sds.core.system.sa.scratch_card.model.*;

public class ScratchCardDao 
{
  private  ScratchCardDao()
  {
  }

  public static Hashtable getDCMScratchCard(String month , String year )
  {
    Hashtable scratchCardTable= new Hashtable();
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      String sql = "select * from VW_DCC_DCM_SCRATCH_CARDS where year ='"+year+"' and month='"+month+"'";
    //  Utility.logger.debug(sql);
      ResultSet res = stat.executeQuery(sql);
      while(res.next())
      {
       ScratchCardModel model = new ScratchCardModel(res); 
       scratchCardTable.put(model.getDcmId() ,model);
      }
      Utility.closeConnection(con);      
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return scratchCardTable;
  }


  public static void updateScratchCardAmount(String[] scratchCardRowId,String[] dcmIds,String[]  amounts ,String year, String month)
  {
  try
  {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    Utility.logger.debug(scratchCardRowId.length+"");
    for (int i = 0 ; i < scratchCardRowId.length ;i++)
    {
      Utility.logger.debug("beg " + i);
      String dcmId =dcmIds[i];   
      String amount=amounts[i];  
      String rowId = scratchCardRowId[i];
      String sql ="";
      Utility.logger.debug("beg " + i);
      if ((rowId.compareTo("")==0) && (amount.compareTo("")!=0))
      {      
        sql ="insert into  VW_DCC_DCM_SCRATCH_CARDS (SCRATCH_CARDS_ID,dcm_id,year,month,amount) values("
            +"SEQ_DDC_SCRATCH_CARDS.nextval,"+dcmId+","+year+","+month+","+amount+")";            
      }
      else
      if((rowId.compareTo("")!=0) && (amount.compareTo("")!=0))
      {      
        sql = "update VW_DCC_DCM_SCRATCH_CARDS set amount = "+amount+" where SCRATCH_CARDS_ID="+rowId;           
      }
     // Utility.logger.debug(sql);
     if (sql!="")
      stat.execute(sql);
    }
    Utility.closeConnection(con);  
  }
  catch(Exception e)
  {
    e.printStackTrace();
  }
  }
}
package com.mobinil.sds.core.system.sa.activation.dao;

import java.sql.*;
import com.mobinil.sds.core.utilities.*;
import java.util.*;
import com.mobinil.sds.core.system.sa.activation.model.*;


public class ActivationDAO 
{
  private  ActivationDAO()
  {
  }

  public static Hashtable getDCMActivation(String month , String year , String productId)
  {
    Hashtable activationTable= new Hashtable();
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      String sql = "select * from VW_DCC_DCM_ACTIVATION where year ='"+year+"' and month='"+month+"'"
                  +" and product_id = "+ productId;
                  
      //Utility.logger.debug(sql);
      ResultSet res = stat.executeQuery(sql);
      while(res.next())
      {
       ActivationModel model = new ActivationModel(res); 
       activationTable.put(model.getDcmId() ,model);
      }
      Utility.closeConnection(con);      
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return activationTable;
  }


  public static void updateActivationAmount(String[] activationRowId,String[] dcmIds,String[]  amounts ,String year, String month, String productId)
  {
  try
  {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
 //   Utility.logger.debug(activationRowId.length);
    for (int i = 0 ; i < activationRowId.length ;i++)
    {
  
      String dcmId =dcmIds[i];   
      String amount=amounts[i];  
      String rowId = activationRowId[i];
      String sql ="";
   
      if ((rowId.compareTo("")==0) && (amount.compareTo("")!=0))
      {      
        sql ="insert into  VW_DCC_DCM_ACTIVATION (DCM_ACTIVATION_ID,dcm_id,year,month,TOTAL_ACTIVATION,product_id) values("
            +"SEQ_DDC_SCRATCH_CARDS.nextval,"+dcmId+","+year+","+month+","+amount+","+productId+")";            
      }
      else
      if((rowId.compareTo("")!=0) && (amount.compareTo("")!=0))
      {      
        sql = "update VW_DCC_DCM_ACTIVATION set TOTAL_ACTIVATION = "+amount+" where DCM_ACTIVATION_ID="+rowId;           
      }

   //   Utility.logger.debug(sql);

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
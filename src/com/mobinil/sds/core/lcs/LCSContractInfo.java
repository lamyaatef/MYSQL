package com.mobinil.sds.core.lcs;

import java.util.*;
import java.sql.*;
/**
 *
 * @author Hagry
 */
public class LCSContractInfo {
    
//      public String transactionId ;
//      public String inventoryItemId; 
//      public String sourceTypeId ;
//      public String sourceTypeName; 
//      public String currentStatus; 
//      public String transactionTypeName;
//      
//      public java.sql.Date issueDate;            
//      public String dcmId ;
//      public String productId ;
//      public String transactionTypeId; 
//      
//      
//      public void mapTransactionType(HashMap transactionTypes)
//      {
//        transactionTypeId = (String)transactionTypes.get(transactionTypeName);   
//        if (transactionTypeId == null)
//        {
//            transactionTypeId ="'-1'";
//        }      
//      }
//
//     
//      
//      public void mapLCSDCM   (Statement sdsStat, HashMap dcmMapping)         
//      {               
//        String dcmNameInLCSMapping ="";
//       
//         dcmNameInLCSMapping = (String)dcmMapping.get(sourceTypeName);
//   
//        if (dcmNameInLCSMapping == null)
//        {
//            dcmNameInLCSMapping ="'-1'";
//            dcmId="'-1'";
//        }
//        else
//        {
//          String sqlGetDCMID = "select dcm_id from vw_gen_dcm where dcm_name='"+ dcmNameInLCSMapping+"' and dcm_level_id = 1 ";       
//          dcmId= "'-1'";
//          try
//          {
//              ResultSet res = sdsStat.executeQuery(sqlGetDCMID);
//             if (res.next())            
//             {
//                dcmId = res.getString("dcm_id");      
//                res.close();   
//             }
//          }
//          catch (Exception e)
//          {
//              e.printStackTrace();
//          }
//        }
//      }
//                  
//      private final String invontrySQL ="select * from SDS_INVENTORY_SYSTEM_ITEMS  where INVENTORY_ITEM_ID = ";
//     
//      public String getStringValue()
//      {
//          if (this.issueDate == null)
//          {
//              return dcmId+","+productId+","+transactionTypeId+",null,'"+this.currentStatus+"'";
//          }
//          else
//          {
//              return dcmId+","+productId+","+transactionTypeId+",to_date('"+issueDate.toString()+"','yyyy-mm-dd'),'"+this.currentStatus+"'";
//          }
//      }
//      public void mapProductID(Statement lcsStat, HashMap inventoryTypes)
//      {       
//        String sql =  invontrySQL+ inventoryItemId ;       
//        String lcsType ="";        
//        try
//        {
//            ResultSet res = lcsStat.executeQuery(sql);
//            if (res.next())
//            {
//             lcsType = res.getString("LCS_PRODUCT_TYPE");
//            }
//            res.close();  
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//                       
//        productId = (String)inventoryTypes.get(lcsType);   
//        if (productId == null)
//        {
//            productId ="'-1'";
//        }                    
//      }      
}

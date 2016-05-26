/**
 * 
 */
package com.mobinil.sds.core.lcs;
import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;
/**
 *
 * @author Hagry
 */
public class LCSContractData {

//private  HashMap transactionTypes = null;
//private  HashMap inventoryTypes = null;
//private  HashMap dcmMapping = null;
//private Connection lcsCon = null;
//private Connection con=null;
//
//private boolean lcsIsPrepared = false;
//
//private CallableStatement cs;   
//private Statement statLCS ;
//private Statement statSDS= null;
//
//public LCSContractData ()
//{
//	
//}
//
//private static final int WaitTime = 30000;
//
//public void closeLCSConnection()
//{	try{
//	if (lcsCon != null)
//	Utility.closeConnection(lcsCon);
//	lcsCon = null;
//}
//catch(Exception e)
//{
//	
//}
//	
//}
//public void openLCSConnection()
//{
//	boolean flag = false; 
//	while (!flag)
//	{
//		
//	   try
//	    {
//
//		this.lcsCon= Utility.getLCSConnection();
//		flag = true; 
//		}
//	    catch (Exception e)
//	    {	
//	       e.printStackTrace();
//	       try{Thread.sleep(WaitTime);}catch(Exception ee){}
//         }
//	}
//}
//
//public Connection openSDSConnection()
//{
//	boolean flag = false; 
//	while (!flag)
//	{
//		
//	   try
//	    {
//		this.con= Utility.getConnection();
//		flag = true; 
//		}
//	    catch (Exception e)
//	    {	
//	       e.printStackTrace();
//	       try{Thread.sleep(WaitTime);}catch(Exception ee){}
//         }
//	}
//	return this.con;
//}
//public LCSContractData(Connection con,Connection lcsCon)
//{
//    this.con=con;
//    this.lcsCon = lcsCon;
//}
//   public void prepareLCS()throws Exception
//   {
//      if (lcsIsPrepared ==false)
//      {
//        String sql ="{call XXMBN.XX_SERIAL_NUM_ISSUED_DATE (?,?,?,?,?,?,?,?)}";
//        cs =  lcsCon.prepareCall(sql);
//        lcsIsPrepared = true;
//        statSDS = con.createStatement();
//        statLCS = lcsCon.createStatement();        
//      }
//   }
//
//   public void cleanLCSStat()
//   {
//	   lcsIsPrepared = false;
//	   
//       try{
//    	   if(cs != null) 
//    		   cs.close();               
//       }
//       catch(Exception e){e.printStackTrace();}
//       
//
//       try{
//	       if(statLCS != null) 
//	    	   statLCS.close();
//	   }
//	   catch(Exception e){e.printStackTrace();}
//	              
//       try{
//       if(statSDS != null) 
//    	   statSDS.close();               
//       }
//       catch(Exception e) {e.printStackTrace();}
//   }
//     
//      
//      public LCSContractInfo getLCSData(String sim)throws Exception
//   {
//      LCSContractInfo lcsContract = new LCSContractInfo();
//      
//       prepareLCS();
//      
//       cs.setString(1,sim);            
//
//      cs.registerOutParameter(2, Types.INTEGER);
//      cs.registerOutParameter(3, Types.INTEGER);
//      cs.registerOutParameter(4, Types.INTEGER);
//      cs.registerOutParameter(5, Types.VARCHAR);
//      cs.registerOutParameter(6, Types.VARCHAR);
//      cs.registerOutParameter(7, Types.VARCHAR);
//      cs.registerOutParameter(8, Types.DATE);
//      
//      try{
//      cs.execute();
//      }catch (Exception e){
//          System.out.println("Serial Number Procedure Cannot execute");
//      }
//      lcsContract.transactionId = cs.getString(2);
//      lcsContract.inventoryItemId = cs.getString(3);
//      lcsContract.sourceTypeId = cs.getString(4);
//      lcsContract.sourceTypeName = cs.getString(5);
//      lcsContract.currentStatus = cs.getString(6);
//      lcsContract.transactionTypeName = cs.getString(7);     
//      lcsContract.issueDate = cs.getDate(8);
//      
//      
//     lcsContract.mapTransactionType(transactionTypes);                  
//     lcsContract.mapLCSDCM(statSDS,dcmMapping);              
//     lcsContract.mapProductID(statLCS,inventoryTypes);
//      
//    
//    
//    // String returnValue ="'"+dcmId+"','"+productId+"','"+transactionTypeId+"',to_date('"+issueDate.toString()+"','yyyy-mm-dd')";
//      return lcsContract;
//   }
//  
//       
//    public  void loadData()
//    {
//    transactionTypes =  Utility.getMap(con, "select transaction_type_name,transaction_type_id from cr_transaction_type");
//    inventoryTypes =Utility.getMap(con, "select inventory_item_type,product_id from CR_PRODUCT_INVENTORY_CODES");
//    dcmMapping = Utility.getMap(con,"SELECT LCS_NAME,DCM_NAME FROM CR_DCM_LCS_NAME");
//    
//     Utility.logger.info("Lookup Data Loaded Successfully");
//    }
   
   
}

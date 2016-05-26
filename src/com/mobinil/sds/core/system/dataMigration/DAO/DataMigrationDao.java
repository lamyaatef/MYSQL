package com.mobinil.sds.core.system.dataMigration.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.mobinil.sds.core.system.dataMigration.model.*;
import com.mobinil.sds.core.utilities.Utility;
 



public class DataMigrationDao {
	private final static boolean debugFlag = false;
	private static void debug(String msg)
	{
		if (debugFlag )
			System.out.println(msg);
	}
	
	public static Vector GetYearMonthSpecificFile(Connection con, String FileId) {
		Vector vec = new Vector();
		Statement stat = null;
		ResultSet res = null;
		try {
			stat = con.createStatement();
			String strSql = "select * from DM_AUTH_CALL_FILE where status <> 'Deleted' and file_id='"+FileId+"'";
			debug("GET all files QUERY ISSSSSSSS"+strSql);
			res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new fileModel (res));
			}
			//res.close();
			//stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		finally
		{
			try
			{
				if (res != null)
					res.close();
			}
			catch (Exception e)
			{				
			}
			try
			{
				if (stat != null)
					stat.close();
			}
			catch (Exception e)
			{	
				
			}
			
		}
		
		
		
		return vec;
	}
	public static Vector getallCashfiles(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = "select * from   DM_PAYMENT_CASH_REPORT_FILE where status <> 'Deleted'   ORDER BY  year ,MONTH    ";
			System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new fileModel (res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	public static void updateCashStatus(Connection con,String file_id,String status)
	{
	
		try {
			
			 Statement stat = con.createStatement();
			 
			  String strSql = "update DM_PAYMENT_CASH_REPORT_FILE"+ 
		         "  SET  STATUS = '"+status+"' where FILE_ID = '"+file_id+"'";  
			  
		      System.out.println("the change status query  is " +strSql);
			    
				stat.execute(strSql);		
				stat.close();
	  
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	public static Vector getallMasterfiles(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = "select * from   DM_PAYMENT_MASTER_FILE where status <> 'Deleted'   ORDER BY  year ,MONTH  ";
			System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new fileModel (res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	public static void updateMasterStatus(Connection con,String file_id,String status)
	{
	
		try {
			
			 Statement stat = con.createStatement();
			 
			  String strSql = "update DM_PAYMENT_MASTER_FILE"+ 
		         "  SET  STATUS = '"+status+"' where FILE_ID = '"+file_id+"'";  
			  
		      System.out.println("the change status query  is " +strSql);
			    
				stat.execute(strSql);		
				stat.close();
	  
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
public static Vector getallAuthCallfiles(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = "  select * from   DM_AUTH_CALL_FILE where status <> 'Deleted'  ORDER BY  year ,MONTH  ";
			System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new fileModel (res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	public static void updateAuthCallStatus(Connection con,String file_id,String status)
	{
	
		try {
			
			 Statement stat = con.createStatement();
			 
			  String strSql = "update DM_AUTH_CALL_FILE"+ 
		         "  SET  STATUS = '"+status+"' where FILE_ID = '"+file_id+"'";  
			  
		      System.out.println("the change status query  is " +strSql);
			    
				stat.execute(strSql);		
				stat.close();
	  
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
	
	
	
	
	public static Vector getallVisafiles(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = "select * from   DM_PAYMENT_VISA_FILE where status <> 'Deleted'  ORDER BY  year ,MONTH    ";
			System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new fileModel (res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	public static void updateVisaStatus(Connection con,String file_id,String status)
	{
	
		try {
			
			 Statement stat = con.createStatement();
			 
			  String strSql = "update DM_PAYMENT_VISA_FILE"+ 
		         "  SET  STATUS = '"+status+"' where FILE_ID = '"+file_id+"'";  
			  
		      System.out.println("the change status query  is " +strSql);
			    
				stat.execute(strSql);		
				stat.close();
	  
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
	public static Vector getallDistfiles(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = "select * from   DM_DIS_COM_FILE where status <> 'Deleted'  ORDER BY  year ,MONTH   ";
			System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new fileModel (res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	public static void updateDisStatus(Connection con,String file_id,String status)
	{
	
		try {
			
			 Statement stat = con.createStatement();
			 
			  String strSql = "update DM_DIS_COM_FILE"+ 
		         "  SET  STATUS = '"+status+"' where FILE_ID = '"+file_id+"'";  
			  
		      System.out.println("the change status query  is " +strSql);
			    
				stat.execute(strSql);		
				stat.close();
	  
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
   public static Vector getfilesData(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = "select * from   DM_SIMSWAP_DATA where status <> 'Deleted' ";
			System.out.println("GET all files data QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new fileDataModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	
	public static void insertNewFileData(java.sql.PreparedStatement pstat, Long file_id, ArrayList file_line_data) {

	//	Long File= null;
		int count=0; 
		
		String activation_date = (String)file_line_data.get(count++);
		String dial_no= (String)file_line_data.get(count++);
		String sim= (String)file_line_data.get(count++);
		String sim_extended= (String)file_line_data.get(count++);
		String pos_code= (String)file_line_data.get(count++);
		
		
	     
		
		
		try {
			
           		//String sql = "insert into DATA_MIGRATION_FILE_DATA (FILE_ID, ACTIVATION_DATE, DIAL_NO, SIM, SIM_EXTENDED, POS_CODE) "
	//	+ " values(?,TO_DATE(?,'dd/mm/yyyy'),?,?,?,?)";
			
	  //  PreparedStatement  prest = con.prepareStatement(sql);
	  
			pstat.setInt(1, file_id.intValue());
			pstat.setString(2,activation_date);
			pstat.setString(3,dial_no);
			pstat.setString(4,sim);
			pstat.setString(5,sim_extended);
			pstat.setString(6,pos_code);
			
			//pstat.execute();
			pstat.addBatch();
			
			
			
			
	
          /*
			String strSql = "insert into DATA_MIGRATION_FILE_DATA (FILE_ID, ACTIVATION_DATE, DIAL_NO, SIM, SIM_EXTENDED, POS_CODE) "
				+ " values("+file_id+",TO_DATE('"+activation_date+"','dd/mm/yyyy'),"+dial_no+",'"+sim+"','"+sim_extended+"','"+pos_code+"')";
 	        stat.execute(strSql);
 	        */		
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static Long insertNewFile(Connection con,String year,  String month,String status,String user_id)
	
	{

		Long FILE_ID= null;

		try {
			
    Statement stat = con.createStatement();
	  	FILE_ID = Utility.getSequenceNextVal(con, "SEQ_DM_FILE_ID");
          
			String strSql = "insert into DM_SIMSWAP_FILE (FILE_ID, YEAR, MONTH, STATUS,TIME_STAMP,USER_ID) "
					+ " values("+FILE_ID+",'"+year+"','"+month+"','"+status+"',SYSDATE,'"+user_id+"')";
	       System.out.println("the insert query is " +strSql);
	       
	       
	    
			stat.execute(strSql);		
			stat.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  FILE_ID;
	}
	
	public static Long getZipSeq(Connection con)
	{
		Long FILE_ID= null;
		try {
	  	FILE_ID = Utility.getSequenceNextVal(con, "SEQ_dm_ZIP_UPLOADING");
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  FILE_ID;
	}
	
	
	
	public static void updateStatus(Connection con,String file_id,String status)
	{
	
		try {
			
			 Statement stat = con.createStatement();
			 
			  String strSql = "update DM_SIMSWAP_FILE"+ 
		         "  SET  STATUS = '"+status+"' where FILE_ID = '"+file_id+"'";  
			  
		      System.out.println("the change status query  is " +strSql);
			    
				stat.execute(strSql);		
				stat.close();
	  
   } catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
	
	public static void updateNoFReadlines(Connection con,String file_id,int  nooflines)
	{
	
		try {
			
			 Statement stat = con.createStatement();
			 
			  String strSql = "update DM_SIMSWAP_FILE"+ 
		         "  SET NO_READ_LINES = "+nooflines+" where FILE_ID = '"+file_id+"'";  
			  
		      System.out.println("the change status query  is " +strSql);
			    
				stat.execute(strSql);		
				stat.close();
	  
   } catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
	
	
	public static boolean checkFile(String year,String month)
	{
		boolean Status=false;
		try {
			
  Connection con = Utility.getConnection();	
		
	 Statement stat = con.createStatement();
			 
			  String strSql = "select * from  DM_SIMSWAP_FILE"+ 
		         "  where YEAR = '"+year+"' and  MONTH='"+month+"' and lower(status) <> 'deleted'  ";  
			  
		      System.out.println("the change status query  is " +strSql);
		      
		      ResultSet res = stat.executeQuery(strSql);
		      if (res.next())
		      {
		    	  
		    	Status=true;  
		    	
		    	  
		      }
			    
				stat.execute(strSql);		
				stat.close();
       } catch (Exception e) {
			e.printStackTrace();
		}
		
		return Status;
	}

	
	
	
	
	
	
	public static boolean checkCashFile(String year,String month)
	{
		boolean Status=false;
		try {
			
  Connection con = Utility.getConnection();	
		
	 Statement stat = con.createStatement();
	 
	 

			 
			  String strSql = "select * from  DM_PAYMENT_CASH_REPORT_FILE"+ 
		         "  where YEAR = '"+year+"' and  MONTH='"+month+"' and lower(status) <> 'deleted'  ";  
			  
		      System.out.println("the change status query  is " +strSql);
		      
		      ResultSet res = stat.executeQuery(strSql);
		      if (res.next())
		      {		    	  
		    	Status=true;  
		    	
		    	  
		      }
			    
				stat.execute(strSql);		
				stat.close();
       } catch (Exception e) {
			e.printStackTrace();
		}
		
		return Status;
	}
	
	
	
	public static boolean checkMasterFile(String year,String month)
	{
		boolean Status=false;
		try {
			
  Connection con = Utility.getConnection();	
		
	 Statement stat = con.createStatement();
			 
			  String strSql = "select * from  DM_PAYMENT_Master_FILE"+ 
		         "  where YEAR = '"+year+"' and  MONTH='"+month+"' and lower(status) <> 'deleted'  ";  
			  
		      System.out.println("the change status query  is " +strSql);
		      
		      ResultSet res = stat.executeQuery(strSql);
		      if (res.next())
		      {
		    	  
		    	Status=true;  
		    	
		    	  
		      }
			    
				stat.execute(strSql);		
				stat.close();
       } catch (Exception e) {
			e.printStackTrace();
		}
		
		return Status;
	}
	
	
	
	public static boolean checkVisaFile(String year,String month)
	{
		boolean Status=false;
		try {
			
  Connection con = Utility.getConnection();	
		
	 Statement stat = con.createStatement();
			 
			  String strSql = "select * from  DM_PAYMENT_visa_FILE"+ 
		         "  where YEAR = '"+year+"' and  MONTH='"+month+"' and lower(status) <> 'deleted'  ";  
			  
		      System.out.println("the change status query  is " +strSql);
		      
		      ResultSet res = stat.executeQuery(strSql);
		      if (res.next())
		      {
		    	  
		    	Status=true;  
		    	
		    	  
		      }
			    
				stat.execute(strSql);		
				stat.close();
       } catch (Exception e) {
			e.printStackTrace();
		}
		
		return Status;
	}
	
	
	
	
	public static boolean checkDistFile(String year,String month)
	{
		boolean Status=false;
		try {
			
  Connection con = Utility.getConnection();	
		
	 Statement stat = con.createStatement();
			 
			  String strSql = "select * from DM_DIS_COM_FILE"+ 
		         "  where YEAR = '"+year+"' and  MONTH='"+month+"' and lower(status) <> 'deleted'  ";  
			  
		      System.out.println("the change status query  is " +strSql);
		      
		      ResultSet res = stat.executeQuery(strSql);
		      if (res.next())
		      {
		    	  
		    	Status=true;  
		    	
		    	  
		      }
			    
				stat.execute(strSql);		
				stat.close();
       } catch (Exception e) {
			e.printStackTrace();
		}
		
		return Status;
	}
	
	
	
	
	
	public static boolean checkAuthCall(String year,String month)
	{
		boolean Status=false;
		try {
			
  Connection con = Utility.getConnection();	
		
	 Statement stat = con.createStatement();
			 
			  String strSql = "select * from DM_auth_call_file"+ 
		         "  where YEAR = '"+year+"' and  MONTH='"+month+"' and lower(status) <> 'deleted'  ";  
			  
		      System.out.println("the change status query  is " +strSql);
		      
		      ResultSet res = stat.executeQuery(strSql);
		      if (res.next())
		      {
		    	  
		    	Status=true;  
		    	
		    	  
		      }
			    
				stat.execute(strSql);		
				stat.close();
       } catch (Exception e) {
			e.printStackTrace();
		}
		
		return Status;
	}
	
	
	
	
	
	
	
	
	
	
	 public static Vector getTmpPaymentDetailReport()
	  {
	      Vector vec = new Vector();
	      try
	      {
	       Connection con = Utility.getConnection();
	       Statement stat = con.createStatement();
	       ResultSet res= stat.executeQuery("select * from DM_TEMP_PAYMENT_REPORT ");
	       while(res.next())
	       {
	        vec.add(new DetailedPaymentReportModel(res));
	       }
	       stat.close();
	       Utility.closeConnection(con);
	      }
	      catch(Exception e)
	      {
	      e.printStackTrace();
	      }
	        return vec; 
	  }
	  
		
		public static Long insertNewPaymentCashFile(String year,  String month,String status,String user_ID)
		
		{

			Long FILE_ID=null;

			try {
			      Connection con = Utility.getConnection();
				
	        Statement stat = con.createStatement();
		  	FILE_ID = Utility.getSequenceNextVal(con, "SEQ_DM_PAYMENT_CASH_FILE_ID");
	          
				String strSql = "insert into DM_PAYMENT_CASH_REPORT_FILE(FILE_ID, YEAR, MONTH, STATUS,USER_ID,TIME_STAMP) "
						+ " values("+FILE_ID+",'"+year+"','"+month+"','"+status+"','"+user_ID+"',sysdate)";
		       System.out.println("the insert query is " +strSql);
		       
               stat.execute(strSql);		
				stat.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return  FILE_ID;
		}
		
		
		public static PreparedStatement getInsertPaymentCashDataPreparedStatemnt(Connection con,Long fileId)
		{
			PreparedStatement p = null;
			
			try{
				String sql = "insert into DM_PAYMENT_CASH_REPORT_DATA(FILE_ID, NAME ,PAYMENT_SERIAL,PAYMENT_DATE ,CUSTOMER_CODE,MOBILE_NUMBER,TOTAL_AMOUNT,CREDIT_CARD_NUMBER )"+
				            " values('"+fileId+"',?,?,?,?,?,?,?)";
				System.out.println("The insert isssssss " + sql);
				p = con.prepareStatement(sql);
//				Thread.sleep(9000);
			}
			catch(Exception e)
			{
			e.printStackTrace();	
			}
			return p;
		}
		
		public static void insertNewPaymentCashData(PreparedStatement ps,Long fileid,  String name,String serial,String date,String code,String mobile,String totalAmount,String creditCardNumber)
		
		{


			try {
				 
	//Connection con = Utility.getConnection();
	    //Statement stat = con.createStatement();
	     name=name.replaceAll("\"","");
	     
	     serial=serial.replaceAll("\"","");
	     
	     code=code.replaceAll("\"",""); 
	     
	     mobile=mobile.replaceAll("\"","");
	     
	     totalAmount=totalAmount.replaceAll("\"","");
	     
	     creditCardNumber=creditCardNumber.replaceAll("\"","");
	     
	     SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	     // (3) create a new String in the format we want
	     
	     java.util.Date  formattedDate=  formatter.parse(date);

	    // java.sql.Date  sqlDate=  new java.sql.Date(to_Date());
	     
	     ps.setString(1, name);
	     ps.setString(2, serial);
//	     ps.setString(3, formattedDate.getDate()+"/"+formattedDate.getMonth()+"/"+formattedDate.getYear());
	     ps.setTimestamp(3, new Timestamp(formattedDate.getTime()));
	     //ps.setString(3,formattedDate.toString());
	     ps.setString(4,code);
	     ps.setString(5,mobile);
	     ps.setString(6,totalAmount);
	     ps.setString(7, creditCardNumber);
	     ps.execute();
	
		//		String strSql = "insert into DM_PAYMENT_CASH_REPORT_DATA(FILE_ID, NAME ,PAYMENT_SERIAL,PAYMENT_DATE ,CUSTOMER_CODE,MOBILE_NUMBER,TOTAL_AMOUNT ) "
			//			+ " values("+fileid+",'"+serial+"','"+code+"',to_Date('"+date+"','dd/mm/yyyy hh24:mi:ss'),'"+name+"','"+mobile+"','"+totalAmount+"')";
		      // System.out.println("the data insert query is " +strSql);
              //  stat.execute(strSql);		
				//stat.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

		}
		/*public static void main(String [] args){
			Connection con =null;
			try {
				con = Utility.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			insertNewPaymentCashData(getInsertPaymentCashDataPreparedStatemnt(con,new Long("96")),new Long("96"),"0","0","23/04/2009","0","0","0");
			
		}*/
		
		public static void deleteTmpPaymentCashData()
		
		{


			try {
				 Connection con = Utility.getConnection();
	    Statement stat = con.createStatement();
	
	
				String strSql = ("delete from  DM_TEMP_PAYMENT_REPORT ");
					
		       System.out.println("the delete tmo data query is " +strSql);
		       
           stat.execute(strSql);		
				stat.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

		}
	  
	 // insertPaymentReportCash
	



public static Vector getTmpvisaMaster()
{
   Vector vec = new Vector();
   try
   {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    ResultSet res= stat.executeQuery("select * from DM_PAYMENT_master_temp");
    while(res.next())
    {
     vec.add(new PaymentVisaModel(res));
    }
    stat.close();
    Utility.closeConnection(con);
   }
   catch(Exception e)
   {
   e.printStackTrace();
   }
     return vec; 
}



public static Vector getTmpvisaData()
{
   Vector vec = new Vector();
   try
   {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    ResultSet res= stat.executeQuery("select * from DM_PAYMENT_visa_temp");
    while(res.next())
    {
     vec.add(new PaymentVisaModel(res));
    }
    stat.close();
    Utility.closeConnection(con);
   }
   catch(Exception e)
   {
   e.printStackTrace();
   }
     return vec; 
}


	
	public static Long insertNewPaymentMasterFile(String year,  String month,String status,String user_ID)
	
	{

		Long FILE_ID=null;

		try {
		      Connection con = Utility.getConnection();
			
     Statement stat = con.createStatement();
	  	FILE_ID = Utility.getSequenceNextVal(con, "SEQ_DM_PAYMENT_master_FILE_ID");
       
			String strSql = "insert into DM_PAYMENT_Master_FILE(FILE_ID, YEAR, MONTH, STATUS,USER_ID,TIME_STAMP) "
					+ " values("+FILE_ID+",'"+year+"','"+month+"','"+status+"','"+user_ID+"',sysdate)";
	       System.out.println("the insert query is " +strSql);
	       
         stat.execute(strSql);		
			stat.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  FILE_ID;
	}
	
	
	public static void insertNewPaymentMasterData(Long fileid,  String name,String serial,String extraPay,String date,String code,String mobile,String credit,String totalAmount)
	
	{


		try {
			 Connection con = Utility.getConnection();
 Statement stat = con.createStatement();


			String strSql = "insert into DM_PAYMENT_master_DATA(FILE_ID, NAME ,PAYMENT_SERIAL,EXTRA_PAYMENT ,PAYMENT_DATE ,CUSTOMER_CODE,MOBILE_NUMBER, CREDIT_CARD_ACCOUNT_NO ,TOTAL_AMOUNT ) "
					+ " values("+fileid+",'"+name+"','"+serial+"','"+extraPay+"','"+date+"','"+code+"','"+mobile+"','"+credit+"','"+totalAmount+"')";
	       System.out.println("the data insert query is " +strSql);
	       
         stat.execute(strSql);		
			stat.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	public static void deleteTmpPaymentMasterData()
	
	{


		try {
			 Connection con = Utility.getConnection();
 Statement stat = con.createStatement();


			String strSql = ("delete from  DM_PAYMENT_master_temp ");
				
	       System.out.println("the delete tmo data query is " +strSql);
	       
     stat.execute(strSql);		
			stat.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public static Vector getTmpPaymentMaster()
	{
	    Vector vec = new Vector();
	    try
	    {
	     Connection con = Utility.getConnection();
	     Statement stat = con.createStatement();
	     ResultSet res= stat.executeQuery("select * from DM_PAYMENT_master_temp");
	     while(res.next())
	     {
	      vec.add(new  PaymentMasterModel(res));
	     }
	     stat.close();
	     Utility.closeConnection(con);
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	      return vec; 
	}

		
		public static Long insertNewPaymentVisaFile(String year,String month,String status,String user_ID)
		
		{

			Long FILE_ID=null;

			try {
			      Connection con = Utility.getConnection();
				
	      Statement stat = con.createStatement();
		  	FILE_ID = Utility.getSequenceNextVal(con, "SEQ_DM_PAYMENT_visa_FILE_ID");
	        
				String strSql = "insert into DM_PAYMENT_visa_FILE(FILE_ID, YEAR, MONTH, STATUS,USER_ID,TIME_STAMP) "
						+ " values("+FILE_ID+",'"+year+"','"+month+"','"+status+"','"+user_ID+"',sysdate)";
		       System.out.println("the insert query is " +strSql);
		       
	          stat.execute(strSql);		
				stat.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return  FILE_ID;
		}
		
		
		public static void insertNewPaymentVisaData(Long fileid,  String scm_code,String serial,String date,String code,String mobile,String totalAmount)
		
		{


			try {
				 Connection con = Utility.getConnection();
	  Statement stat = con.createStatement();


				String strSql = "insert into DM_PAYMENT_visa_DATA(FILE_ID, SCM_CODE ,PAYMENT_SERIAL,PAYMENT_DATE ,CUSTOMER_CODE,MOBILE_NUMBER,TOTAL_AMOUNT ) "
						+ " values("+fileid+",'"+scm_code+"','"+serial+"','"+date+"','"+code+"','"+mobile+"','"+totalAmount+"')";
		       System.out.println("the data insert query is " +strSql);
             stat.execute(strSql);		
				stat.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

		}
		
		public static void deleteTmpPaymentVisaData()
		
		{


			try {
				 Connection con = Utility.getConnection();
	  Statement stat = con.createStatement();


				String strSql = ("delete from  DM_PAYMENT_visa_temp");
					
		       System.out.println("the delete tmo data query is " +strSql);
		       
	      stat.execute(strSql);		
				stat.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

		}
		
		
		public static Vector getTmpDistComp()
		{
		    Vector vec = new Vector();
		    try
		    {
		     Connection con = Utility.getConnection();
		     Statement stat = con.createStatement();
		     ResultSet res= stat.executeQuery("select * from DM_DIS_COM_TEMP ");
		     
		  
		     
		    
		
		     while(res.next())
		     {
		      vec.add(new CompDistModel(res));
		     }
		     stat.close();
		     Utility.closeConnection(con);
		    }
		    catch(Exception e)
		    {
		    e.printStackTrace();
		    }
		      return vec; 
		}

			
			public static Long insertNewCompDistFile(String year,  String month,String status,String user_ID)
			
			{

				Long FILE_ID=null;

				try {
				      Connection con = Utility.getConnection();
					
		      Statement stat = con.createStatement();
			  	FILE_ID = Utility.getSequenceNextVal(con, "SEQ_DM_dist_comp_FILE_ID");
			
        String strSql = "insert into DM_DIS_COM_FILE(FILE_ID, YEAR, MONTH, STATUS,USER_ID,TIME_STAMP) "
							+ " values("+FILE_ID+",'"+year+"',"+month+",'"+status+"','"+user_ID+"',sysdate)";
			       System.out.println("the insert query is " +strSql);
			       
		          stat.execute(strSql);		
					stat.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return  FILE_ID;
			}
			
			
			public static void insertNewCompDistData(Long fileid,  String scm_code,String scs,String etopupup)
			
			{


				try {
					 Connection con = Utility.getConnection();
		  Statement stat = con.createStatement();
		  



					String strSql = "insert into DM_DIS_COM_DATA(FILE_ID, SCM_CODE ,SCS ,ETOPUP) "
							+ " values("+fileid+",'"+scm_code+"','"+scs+"','"+etopupup+"')";
			       System.out.println("the data insert query is " +strSql);
	             stat.execute(strSql);		
					stat.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				

			}
			
			public static void deleteTmpCompDistData()
			
			{


				try {
					 Connection con = Utility.getConnection();
		  Statement stat = con.createStatement();


					String strSql = ("delete from  DM_DIS_COM_TEMP");
						
			       System.out.println("the delete tmo data query is " +strSql);
			       
		      stat.execute(strSql);		
					stat.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				

			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			public static Vector getTmpAuthCall()
			{
			    Vector vec = new Vector();
			    try
			    {
			     Connection con = Utility.getConnection();
			     Statement stat = con.createStatement();
			     ResultSet res= stat.executeQuery("select * from DM_auth_call_temp ");
			     
                    while(res.next())
			     {
			      vec.add(new authCallModel(res));
			     }
			     stat.close();
			     Utility.closeConnection(con);
			    }
			    catch(Exception e)
			    {
			    e.printStackTrace();
			    }
			      return vec; 
			}

				
				public static Long insertNewAuthCallFile(String year,  String month,String status,String user_ID)
				
				{

					Long FILE_ID=null;

					try {
					      Connection con = Utility.getConnection();
						
			      Statement stat = con.createStatement();
				  	FILE_ID = Utility.getSequenceNextVal(con, "SEQ_DM_auth_call_FILE_ID");
				
	        String strSql = "insert into DM_auth_call_FILE(FILE_ID, YEAR, MONTH, STATUS,USER_ID,TIME_STAMP) "
								+ " values("+FILE_ID+",'"+year+"',"+month+",'"+status+"','"+user_ID+"',sysdate)";
				       System.out.println("the insert query is " +strSql);
				       
			          stat.execute(strSql);		
						stat.close();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return  FILE_ID;
				}
				
				
				public static void insertNewAuthCallData(Long fileid,  String dcm_code,String unreal_count)
				
				{


					try {
						 Connection con = Utility.getConnection();
			  Statement stat = con.createStatement();
			  



						String strSql = "insert into DM_auth_call_DATA(FILE_ID, dcm_code ,UNREAL_COUNT) "
								+ " values("+fileid+",'"+dcm_code+"','"+unreal_count+"')";
				       System.out.println("the data insert query is " +strSql);
		             stat.execute(strSql);		
						stat.close();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					

				}
				
				public static void deleteTmpAuthCallData()
				
				{


					try {
						 Connection con = Utility.getConnection();
			  Statement stat = con.createStatement();


						String strSql = ("delete from  DM_auth_call_TEMP");
							
				       System.out.println("the delete tmo data query is " +strSql);
				       
			      stat.execute(strSql);		
						stat.close();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					

				}
    public static void insertStastics(int fileid, int read_lines)
			
			{


				try {
			 Connection con = Utility.getConnection();
		     Statement  stat = con.createStatement();
		  
	         int inserted_lines=DataMigrationDao.getNoOfinsertedlines(fileid);
	         
	         int total_non_valid=DataMigrationDao.getNoOfPosCode(fileid);
	         
	         int non_valid=DataMigrationDao.getNoOfdistinctPosCode(fileid);
	         
	         


					String strSql = "insert into DM_SIMSWAP_STATISTCS(FILE_ID, NO_READ_LINES ,NO_INSERTED_LINES,IN_VALID_POS_CODE ,DISTINCT_IN_VALID_POS_CODE) "
							+ " values("+fileid+",'"+read_lines+"','"+inserted_lines+"','"+total_non_valid+"','"+non_valid+"')";
			       
					System.out.println("the insert statistics  query is " +strSql);
	                stat.execute(strSql);
	             
	          
					stat.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				}	
		
		
		
		public static int  getNoOfinsertedlines(int file_id)
		{
		   int count=0;
		    try
		    {
		     Connection con = Utility.getConnection();
		     Statement stat = con.createStatement();
		     System.out.println("dddddddddddddddd "+file_id);
		     ResultSet res=   	stat.executeQuery("select count(*)  as counter   from dm_simswap_data where file_Id="+file_id+"");
		     res.next();
		     count=res.getInt("counter");
		     
		     stat.close();
		     Utility.closeConnection(con);
		    }
		    catch(Exception e)
		    {
		    e.printStackTrace();
		    }
		      return count; 
		}
		
		
		
		
		public static int  getNoOfdistinctPosCode(int file_id)
		{
		   int count=0;
		    try
		    {
		     Connection con = Utility.getConnection();
		     Statement stat = con.createStatement();
		     ResultSet res=stat.executeQuery("select count(distinct(pos_code)) as counter from dm_simswap_data where file_Id ="+file_id+" and  pos_code not in (select dcm_code from gen_dcm where pos_code = dcm_code)");
		     res.next();
		     count=res.getInt("COUNTer");
		     stat.close();
		     Utility.closeConnection(con);
		    }
		    catch(Exception e)
		    {
		    e.printStackTrace();
		    }
		      return count; 
		}
		
		public static int  getNoOfPosCode(int file_id)
		{
		   int count=0;
		    try
		    {
		     Connection con = Utility.getConnection();
		     Statement stat = con.createStatement();
		     ResultSet res=   	stat.executeQuery("select count(pos_code)  as counter  from dm_simswap_data where file_Id ="+file_id+" and  pos_code not in (select dcm_code from gen_dcm where pos_code = dcm_code)");
		     res.next();
		     count=res.getInt("COUNTer");
		     stat.close();
		     Utility.closeConnection(con);
		    }
		    catch(Exception e)
		    {
		    e.printStackTrace();
		    }
		      return count; 
		}
		
		
		
		
		public static Vector  getStaistics(String  file_id)
		{
	    Vector vec = new Vector();
	    try
	    {
	     Connection con = Utility.getConnection();
	     Statement stat = con.createStatement();
	     ResultSet res= stat.executeQuery("select * from DM_SIMSWAP_STATISTCS where file_id ='"+file_id+"'");
	     while(res.next())
	     {
	      vec.add(new  zipUploadStatisticsModel(res));
	     }
	     stat.close();
	     Utility.closeConnection(con);
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	      return vec; 
	}
		
		
		
		public static Vector  getinvalidPosCode(String  file_id)
		{
	    Vector vec = new Vector();
	    try
	    {
	     Connection con = Utility.getConnection();
	     Statement stat = con.createStatement();
	     ResultSet res= stat.executeQuery("select  pos_code as pos_code ,count (pos_code) as NO_REPEATED from dm_simswap_data where  file_Id ='"+file_id+"' and  pos_code not in (select dcm_code from gen_dcm where pos_code = dcm_code)  group by pos_code ");
	     
	     
	     while(res.next())
	     {
	      vec.add(new  inValidPosCodeModel(res));
	     }
	     stat.close();
	     Utility.closeConnection(con);
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	      return vec; 
	}
		
     public static Vector  getinvalidDcmCode(String  file_id)
		{
	    Vector vec = new Vector();
	    try
	    {
	     Connection con = Utility.getConnection();
	     Statement stat = con.createStatement();
	     ResultSet res= stat.executeQuery("select distinct dcm_code as dcm_code from dm_auth_call_data where  file_Id ='"+file_id+"' and  dcm_code not in (select dcm_code from gen_dcm where dm_auth_call_data.dcm_code = gen_dcm.dcm_code)");
	     while(res.next())
	     {
	      vec.add(new  InValid_Dcm_CodeModel(res));
	     }
	     stat.close();
	     Utility.closeConnection(con);
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	      return vec; 
	}
		
public static int  getinsrerteValue(Long file_id)
		{
		   int count=0;
		    try
		    {
		     Connection con = Utility.getConnection();
		     Statement stat = con.createStatement();
		     System.out.println("dddddddddddddddd "+file_id);
		     ResultSet res=   	stat.executeQuery("select count(*)  as counter   from   DM_PAYMENT_CASH_REPORT_DATA where file_Id="+file_id+"");
		     res.next();
		     count=res.getInt("counter");
		     
		     stat.close();
		     Utility.closeConnection(con);
		    }
		    catch(Exception e)
		    {
		    e.printStackTrace();
		    }
		      return count; 
		}
			






public static Long insertNewPOS_PAY_HISFile(String year,String month,String status,String user_ID)

{

	Long FILE_ID=null;

	try {
	      Connection con = Utility.getConnection();
		
  Statement stat = con.createStatement();
  	FILE_ID = Utility.getSequenceNextVal(con, "SEQ_DM_POS_PAY_HIS_FILE_ID");
    
		String strSql = "insert into DM_POS_PAY_HIS_FILE(FILE_ID, YEAR, MONTH, STATUS,USER_ID,TIME_STAMP) "
				+ " values("+FILE_ID+",'"+year+"','"+month+"','"+status+"','"+user_ID+"',sysdate)";
       System.out.println("the insert query is " +strSql);
       
      stat.execute(strSql);		
		stat.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return  FILE_ID;
}


public static Long insertNewPOS_ELG_CHKFile(String year,String month,String status,String user_ID)

{

	Long FILE_ID=null;

	try {
	      Connection con = Utility.getConnection();
		
  Statement stat = con.createStatement();
  	FILE_ID = Utility.getSequenceNextVal(con, "SEQ_DM_POS_ELG_CHK_FILE_ID");
    
		String strSql = "insert into DM_POS_ELG_CHK_FILE(FILE_ID, YEAR, MONTH, STATUS,USER_ID,TIME_STAMP) "
				+ " values("+FILE_ID+",'"+year+"','"+month+"','"+status+"','"+user_ID+"',sysdate)";
       System.out.println("the insert query is " +strSql);
       
      stat.execute(strSql);		
		stat.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return  FILE_ID;
}



public static Long insertNewPOS_DEL_CHKFile(String year,String month,String status,String user_ID)

{

	Long FILE_ID=null;

	try {
	      Connection con = Utility.getConnection();
		
  Statement stat = con.createStatement();
  	FILE_ID = Utility.getSequenceNextVal(con, "SEQ_DM_POS_DEL_CHK_FILE_ID");
    
		String strSql = "insert into DM_POS_DEL_CHK_FILE(FILE_ID, YEAR, MONTH, STATUS,USER_ID,TIME_STAMP) "
				+ " values("+FILE_ID+",'"+year+"','"+month+"','"+status+"','"+user_ID+"',sysdate)";
       System.out.println("the insert query is " +strSql);
       
      stat.execute(strSql);		
		stat.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return  FILE_ID;
}


public static void insertNewPOS_PAY_HISData(Long fileid,String phaseDate,String memName,String code,String toatalAmount,String amount,String done,String msg,String saleTax ,String regDate,String comment1,String comment2,String memoDesc,String deductionFees,String remainedDeduction,String financeDate,String budget_id,String budget_name)

{
	
	


	try {
		
		
		if(financeDate==null)
			
		{
			
		financeDate="";	
			
		}
		 Connection con = Utility.getConnection();
Statement stat = con.createStatement();


		String strSql = "insert into DM_POS_PAY_HIS_DATA(FILE_ID, PHASE_DATE ,MEMO_NAME,CODE ,TOTAL_AMOUNT,AMOUNT,DONE,MSG,SALES_TAX,REG_DATE,COMMENT1,COMMENT2,MEMEO_DESC,DEDUCTION_FEES,REMAINED_DEDUCTION,FINANCE_DATE,BUDGET_ID,BUDGET_NAME) "
				+ " values("+fileid+",'"+phaseDate+"','"+memName+"','"+code+"','"+toatalAmount+"','"+amount+"','"+done+"','"+msg+"','"+saleTax+"','"+regDate+"','"+comment1+"','"+comment2+"','"+memoDesc+"','"+deductionFees+"','"+remainedDeduction+"','"+financeDate+"','"+budget_id+"','"+budget_name+"')";
       System.out.println("the data insert query is " +strSql);
       
   
       
     try{  
     stat.execute(strSql);
     }catch(Exception e){}
		stat.close();
		Utility.closeConnection(con);
	} catch (Exception e) {
		e.printStackTrace();
	}
	

}


public static void insertNewPOS_DEL_CHKData(Long fileid,  String dcmCode,String delivered,String ni,String probl,String notd,String acc)

{

try {
		 Connection con = Utility.getConnection();
Statement stat = con.createStatement();


		String strSql = "insert into DM_POS_DEL_CHK_DATA(FILE_ID, DCM_CODE ,DELIVERED,NI ,PROBL,NOTD,ACC) "
				+ " values("+fileid+",'"+dcmCode+"','"+delivered+"','"+ni+"','"+probl+"','"+notd+"','"+acc+"')";
       System.out.println("the data insert query is " +strSql);
     stat.execute(strSql);		
		stat.close();
		Utility.closeConnection(con);
	} catch (Exception e) {
		e.printStackTrace();
	}
	

}







public static void insertNewPOS_ELG_CHKData(Long fileid,  String posCode,String posEnm,String entryDt,String stkDlvrDate,String iqrarDlvrDate,String iqrarRcvDate,String posStatus,String stkStatus ,String regName,String repName,String channelCode,String levelCode,String stkdial)

{
	



	try {
		 Connection con = Utility.getConnection();
Statement stat = con.createStatement();

if( stkDlvrDate==null){
	stkDlvrDate="";	
}

if( iqrarDlvrDate==null){
	iqrarDlvrDate="";	
}


if( iqrarRcvDate==null)
{
	iqrarRcvDate="";	
}



		String strSql = "insert into DM_POS_ELG_CHK_DATA(FILE_ID, POS_CODE ,POS_ENM,ENTRY_DT ,STK_DLVR_DT,IQRAR_DLVR_DT,IQRAR_RCV_DT,POS_STATUS,STK_STATUS,REGIONAL_NAME,REP_NAME,CHANNEL_CODE,LEVEL_CODE,STK_DIAL_NO ) "
				+ " values("+fileid+",'"+posCode+"','"+posEnm+"','"+entryDt+"','"+stkDlvrDate+"','"+iqrarDlvrDate+"','"+iqrarRcvDate+"','"+posStatus+"','"+stkStatus+"','"+regName+"','"+repName+"','"+channelCode+"','"+levelCode+"','"+stkdial+"')";
       System.out.println("the data insert query is " +strSql);
     stat.execute(strSql);		
		stat.close();
		Utility.closeConnection(con);
	} catch (Exception e) {
		e.printStackTrace();
	}
	

}







public static void deleteTmpPOS_PAY_HISData()

{


	try {
		 Connection con = Utility.getConnection();
Statement stat = con.createStatement();


		String strSql = ("delete from DM_POS_PAY_HIS_TEMP");
			
       System.out.println("the delete tmo data query is " +strSql);
       
  stat.execute(strSql);		
		stat.close();
		Utility.closeConnection(con);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	

}

public static void deleteTmpPOS_DEL_CHKData()

{


	try {
		 Connection con = Utility.getConnection();
Statement stat = con.createStatement();


		String strSql = ("delete from DM_POS_DEL_CHK_TEMP");
			
       System.out.println("the delete tmo data query is " +strSql);
       
  stat.execute(strSql);		
		stat.close();
		Utility.closeConnection(con);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	

}





public static void deleteTmpPOS_ELG_CHKData()

{


	try {
		 Connection con = Utility.getConnection();
Statement stat = con.createStatement();


		String strSql = ("delete from DM_POS_ELG_CHK_TEMP");
			
       System.out.println("the delete tmo data query is " +strSql);
       
  stat.execute(strSql);		
		stat.close();
		Utility.closeConnection(con);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	

}



public static Vector getTmpPOS_PAY_HISData()
{
   Vector vec = new Vector();
   try
   {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    ResultSet res= stat.executeQuery("select * FROM  DM_POS_PAY_HIS_TEMP");
    while(res.next())
    {
     vec.add(new POS_PAY_HISModel(res));
    }
    stat.close();
    Utility.closeConnection(con);
   }
   catch(Exception e)
   {
   e.printStackTrace();
   }
     return vec; 
}




public static Vector getTmpPOS_DEL_CHKData()
{
   Vector vec = new Vector();
   try
   {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    ResultSet res= stat.executeQuery("select * FROM DM_POS_DEL_CHK_TEMP");
    while(res.next())
    {
     vec.add(new POS_DEL_CHKModel(res));
    }
    stat.close();
    Utility.closeConnection(con);
   }
   catch(Exception e)
   {
   e.printStackTrace();
   }
     return vec; 
}




public static Vector getTmpPOS_ELG_CHKData()
{
   Vector vec = new Vector();
   try
   {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    ResultSet res= stat.executeQuery("select *  FROM DM_POS_ELG_CHK_TEMP");
    while(res.next())
    {
     vec.add(new POS_ELG_CHKModel(res));
    }
    stat.close();
    Utility.closeConnection(con);
   }
   catch(Exception e)
   {
   e.printStackTrace();
   }
     return vec; 
}


public static boolean checkPOS_HISFile(String year,String month)
{
	boolean Status=false;
	try {
		
Connection con = Utility.getConnection();	
	
 Statement stat = con.createStatement();
		 
		  String strSql = "select * from  DM_POS_PAY_HIS_FILE"+ 
	         "  where YEAR = '"+year+"' and  MONTH='"+month+"' and lower(status) <> 'deleted'  ";  
		  
	      System.out.println("the change status query  is " +strSql);
	      
	      ResultSet res = stat.executeQuery(strSql);
	      if (res.next())
	      {
	    	  
	    	Status=true;  
	    	
	    	  
	      }
		    
			stat.execute(strSql);		
			stat.close();
			Utility.closeConnection(con);
   } catch (Exception e) {
		e.printStackTrace();
	}
	
	return Status;
}

public static boolean checkPOS_DELFile(String year,String month)
{
	boolean Status=false;
	try {
		
Connection con = Utility.getConnection();	
	
 Statement stat = con.createStatement();
		 
		  String strSql = "select * from  DM_POS_DEL_CHK_FILE"+ 
	         "  where YEAR = '"+year+"' and  MONTH='"+month+"' and lower(status) <> 'deleted'  ";  
		  
	      System.out.println("the change status query  is " +strSql);
	      
	      ResultSet res = stat.executeQuery(strSql);
	      if (res.next())
	      {
	    	  
	    	Status=true;  
	    	
	    	  
	      }
		    
			stat.execute(strSql);		
			stat.close();
			Utility.closeConnection(con);
   } catch (Exception e) {
		e.printStackTrace();
	}
	
	return Status;
}




public static boolean checkPOS_ELGFile(String year,String month)
{
	boolean Status=false;
	try {
		
Connection con = Utility.getConnection();	
	
 Statement stat = con.createStatement();
		 
		  String strSql = "select * from  DM_POS_ELG_CHK_FILE"+ 
	         "  where YEAR = '"+year+"' and  MONTH='"+month+"' and lower(status) <> 'deleted'  ";  
		  
		  
		  
		  
	      System.out.println("the change status query  is " +strSql);
	      
	      ResultSet res = stat.executeQuery(strSql);
	      if (res.next())
	      {
	    	  
	    	Status=true;  
	    	
	    	  
	      }
		    
			stat.execute(strSql);		
			stat.close();
			Utility.closeConnection(con);
   } catch (Exception e) {
		e.printStackTrace();
	}
	
	return Status;
}





public static Vector getallpayHisfiles(Connection con) {
	Vector vec = new Vector();
	try {
		Statement stat = con.createStatement();
		String strSql = "select * from   DM_POS_PAY_HIS_FILE  where status <> 'Deleted'  ORDER BY  year ,MONTH  ";
		System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
		ResultSet res = stat.executeQuery(strSql);
		while (res.next()) {
			vec.add(new fileModel (res));
		}
		res.close();
		stat.close();
		Utility.closeConnection(con);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return vec;
}




public static Vector getallpos_Delfiles(Connection con) {
	Vector vec = new Vector();
	try {
		Statement stat = con.createStatement();
		String strSql = "select * from   DM_POS_DEL_CHK_FILE  where status <> 'Deleted'   ORDER BY  year ,MONTH   ";
		System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
		ResultSet res = stat.executeQuery(strSql);
		while (res.next()) {
			vec.add(new fileModel (res));
		}
		res.close();
		stat.close();
		Utility.closeConnection(con);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return vec;
}



public static Vector getallpos_Elgfiles(Connection con) {
	Vector vec = new Vector();
	try {
		Statement stat = con.createStatement();
		String strSql = "select * from   DM_POS_ELG_CHK_FILE where status <> 'Deleted'  ORDER BY  year ,MONTH   ";
		System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
		ResultSet res = stat.executeQuery(strSql);
		while (res.next()) {
			vec.add(new fileModel (res));
		}
		res.close();
		stat.close();
		Utility.closeConnection(con);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return vec;
}




public static void updatepos_his_file(Connection con,String file_id,String status)
{

	try {
		
		 Statement stat = con.createStatement();
		 
		  String strSql = "update DM_POS_PAY_HIS_FILE"+ 
	         "  SET  STATUS = '"+status+"' where FILE_ID = '"+file_id+"'";  
		  
	      System.out.println("the change status query  is " +strSql);
		    
			stat.execute(strSql);		
			stat.close();
  
			Utility.closeConnection(con);	 
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	

}



public static void updatepos_del_file(Connection con,String file_id,String status)
{

	try {
		
		 Statement stat = con.createStatement();
		 
		  String strSql = "update DM_POS_DEL_CHK_FILE"+ 
	         "  SET  STATUS = '"+status+"' where FILE_ID = '"+file_id+"'";  
		  
	      System.out.println("the change status query  is " +strSql);
		    
			stat.execute(strSql);		
			stat.close();
			Utility.closeConnection(con);
		 
		
	} catch (Exception e) {
		e.printStackTrace();
	}

}
	
	

	public static void updatepos_elg_file(Connection con,String file_id,String status)
	{

		try {
			
			 Statement stat = con.createStatement();
			 
			  String strSql = "update DM_POS_ELG_CHK_FILE"+ 
		         "  SET  STATUS = '"+status+"' where FILE_ID = '"+file_id+"'";  
			  
		      System.out.println("the change status query  is " +strSql);
			    
				stat.execute(strSql);		
				stat.close();
				Utility.closeConnection(con);
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

	public static void updateSearchpos_elg_file(Connection con,String file_id,String status)
	{

		try {
			
			 Statement stat = con.createStatement();
			 
			  String strSql = "update DM_ELG_CHK_SEARCH_FILE"+ 
		         "  SET  STATUS = '"+status+"' where  SEARCH_FILE_ID= '"+file_id+"'";  
			  
		      System.out.println("the change status query  is " +strSql);
			    
				stat.execute(strSql);		
				stat.close();
				Utility.closeConnection(con);
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	

	public static void updateSearchpos_DEL_file(Connection con,String file_id,String status)
	{

		try {
			
			 Statement stat = con.createStatement();
			 
			  String strSql = "update DM_DEL_CHK_SEARCH_FILE"+ 
		         "  SET  STATUS = '"+status+"' where  SEARCH_FILE_ID= '"+file_id+"'";  
			  
		      System.out.println("the change status query  is " +strSql);
			    
				stat.execute(strSql);		
				stat.close();
				Utility.closeConnection(con);
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public static void updateSearchpos_HIS_file(Connection con,String file_id,String status)
	{

		try {
			
			 Statement stat = con.createStatement();
			 
			  String strSql = "update DM_HIS_CHK_SEARCH_FILE "+ 
		         "  SET  STATUS = '"+status+"' where  SEARCH_FILE_ID= '"+file_id+"'";  
			  
		      System.out.println("the change status query  is " +strSql);
			    
				stat.execute(strSql);		
				stat.close();
	  
				Utility.closeConnection(con);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	public static Vector getallfiles(Connection con) {
		System.out.println();
		Vector vec = new Vector();
		Statement stat = null;
		ResultSet res = null;
		try {
			stat = con.createStatement();
			String strSql = "select * from   DM_SIMSWAP_FILE where status <> 'Deleted'   ORDER BY  year ,MONTH    ";
			debug("GET all files QUERY ISSSSSSSS"+strSql);
			res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new fileModel (res));
			}
			//res.close();
			//stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		finally
		{
			try
			{
				if (res != null)
					res.close();
			}
			catch (Exception e)
			{				
			}
			try
			{
				if (stat != null)
					stat.close();
			}
			catch (Exception e)
			{	
				
			}
			
		}
		
		
		return vec;
	

}


	
	
	public static Vector getallPosElgSearchfiles(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = " select SEARCH_FILE_ID , YEAR , MONTH ,STATUS ,TIME_STAMP ,USER_ID,DESCRIPTION from  DM_ELG_CHK_SEARCH_FILE where  status <> 'Deleted' order by search_file_id";
			
			
			
			System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new ElgChkSearchFileModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	
	public static Vector getallDelElgSearchfiles(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = " select SEARCH_FILE_ID , YEAR , MONTH ,STATUS ,TIME_STAMP ,USER_ID,DESCRIPTION from  DM_DEL_CHK_SEARCH_FILE where  status <> 'Deleted' order by search_file_id";
			
			
			
			System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new ElgChkSearchFileModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	
	
	
	
	
	public static Vector getallPOSHISSearchfiles(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = " select SEARCH_FILE_ID , YEAR , MONTH ,STATUS ,TIME_STAMP ,USER_ID,DESCRIPTION from DM_HIS_CHK_SEARCH_FILE where  status <> 'Deleted' order by search_file_id";
			
			
			
			System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new ElgChkSearchFileModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	
	public static Vector getallPosElgSearchfileData(Connection con,String search_file_id ,String month,String year) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
		//	String strSql = " SELECT * FROM DM_POS_ELG_CHK_SEARCH_DATA   where search_id ='"+search_file_id+"'";
		//	String strSql= "SELECT * FROM DM_POS_ELG_CHK_SEARCH_DATA   where search_id ='"+search_file_id+"'and  POS_CODE_EXIST_FLAG='1'";
			
		
			String strSql="SELECT DISTINCT(DM_POS_ELG_CHK_SEARCH_DATA.POS_CODE), DM_POS_ELG_CHK_DATA.CHANNEL_CODE, DM_POS_ELG_CHK_DATA.ENTRY_DT, DM_POS_ELG_CHK_DATA.IQRAR_DLVR_DT, DM_POS_ELG_CHK_DATA.LEVEL_CODE, DM_POS_ELG_CHK_DATA.POS_ENM, DM_POS_ELG_CHK_DATA.POS_STATUS, DM_POS_ELG_CHK_DATA.REGIONAL_NAME, DM_POS_ELG_CHK_DATA.REP_NAME, DM_POS_ELG_CHK_DATA.STK_DIAL_NO, DM_POS_ELG_CHK_DATA.STK_DLVR_DT, DM_POS_ELG_CHK_DATA.STK_STATUS, DM_POS_ELG_CHK_DATA.IQRAR_RCV_DT,DM_POS_ELG_CHK_SEARCH_DATA.SEARCH_ID  FROM  DM_POS_ELG_CHK_DATA,DM_POS_ELG_CHK_SEARCH_DATA,DM_POS_ELG_CHK_FILE  WHERE    DM_POS_ELG_CHK_DATA.POS_CODE   = DM_POS_ELG_CHK_SEARCH_DATA.pos_CODE AND DM_POS_ELG_CHK_SEARCH_DATA.POS_CODE_EXIST_FLAG     = '1'  and DM_POS_ELG_CHK_SEARCH_DATA.search_id='"+search_file_id+"'  AND  DM_POS_ELG_CHK_DATA.FILE_ID =  ( (SELECT DISTINCT(file_id) from  DM_POS_ELG_CHK_FILE where DM_POS_ELG_CHK_FILE.MONTH ='"+month+"' and DM_POS_ELG_CHK_FILE.YEAR ='"+year+"'and status <> 'Deleted') )";

				 

			System.out.println("GET search data QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new PosElgSearchfileDataModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	

	public static Vector getallPosElgSearchfileData(Connection con,String search_file_id) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
		//	String strSql = " SELECT * FROM DM_POS_ELG_CHK_SEARCH_DATA   where search_id ='"+search_file_id+"'";
			String strSql= "SELECT * FROM DM_POS_ELG_CHK_SEARCH_DATA   where search_id ='"+search_file_id+"'and  POS_CODE_EXIST_FLAG='1'";
			
		
			
			
			
			
			
			
			System.out.println("GET search data QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new PosElgSearchfileDataModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	public static Vector getallPosDelSearchfileData(Connection con,String search_file_id) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = " SELECT  *  FROM   DM_POS_DEL_CHK_SEARCH_DATA  where search_id ='"+search_file_id+"'";
			
			
			
			System.out.println("GET search data QUERY ISSSSSSSS "+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new PosDelSearchfileDataModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	
	
	
	public static Vector getallPosDelSearchfileData(Connection con,String search_file_id,String month,String year) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
		//	String strSql = " SELECT  *  FROM   DM_POS_DEL_CHK_SEARCH_DATA  where search_id ='"+search_file_id+"'";
			
			String strSql = 	 "SELECT  DISTINCT(DM_POS_DEL_CHK_SEARCH_DATA.DCM_CODE ), DM_POS_DEL_CHK_DATA.ACC,DM_POS_DEL_CHK_DATA.DELIVERED,DM_POS_DEL_CHK_DATA.FILE_ID,DM_POS_DEL_CHK_DATA.NI,DM_POS_DEL_CHK_DATA.NOTD,DM_POS_DEL_CHK_DATA.PROBL,DM_POS_DEL_CHK_SEARCH_DATA.SEARCH_ID "+ 
			    " FROM DM_POS_DEL_CHK_DATA,DM_POS_DEL_CHK_SEARCH_DATA,DM_POS_DEL_CHK_FILE "+
			    " WHERE   DM_POS_DEL_CHK_DATA.dcm_code= DM_POS_DEL_CHK_SEARCH_DATA.dcm_CODE AND DM_POS_DEL_CHK_SEARCH_DATA.CODE_EXIST_FLAG = '1' "+
			    " and DM_POS_DEL_CHK_SEARCH_DATA.search_id='"+search_file_id+"' "+
			    " AND  DM_POS_DEL_CHK_DATA.FILE_ID =  ( (SELECT DISTINCT(file_id) from  DM_POS_DEL_CHK_FILE where DM_POS_DEL_CHK_FILE.MONTH ='"+month+"' and DM_POS_DEL_CHK_FILE.YEAR ='"+year+"'and status <> 'Deleted') )"; 

			
			System.out.println("GET search data QUERY ISSSSSSSS "+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new PosDelSearchfileDataModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	
	



	
	
	public static Vector getallPosHisSearchfileData(Connection con,String search_file_id) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			//String strSql = " SELECT * FROM  DM_POS_PAY_HIS_SEARCH_DATA  where search_id ='"+search_file_id+"'";
			
			
			
			
			
	String strSql="SELECT DISTINCT(DM_POS_PAY_HIS_SEARCH_DATA.CODE),DM_POS_PAY_HIS_DATA.AMOUNT,DM_POS_PAY_HIS_DATA.BUDGET_ID,DM_POS_PAY_HIS_DATA.BUDGET_NAME,DM_POS_PAY_HIS_SEARCH_DATA.SEARCH_ID,"+
			" DM_POS_PAY_HIS_DATA.COMMENT1,DM_POS_PAY_HIS_DATA.COMMENT2,DM_POS_PAY_HIS_DATA.DEDUCTION_FEES,DM_POS_PAY_HIS_DATA.DONE,DM_POS_PAY_HIS_DATA.FINANCE_DATE,"+
			" DM_POS_PAY_HIS_DATA.MEMEO_DESC,DM_POS_PAY_HIS_DATA.MEMO_NAME,DM_POS_PAY_HIS_DATA.MSG,DM_POS_PAY_HIS_DATA.PHASE_DATE,DM_POS_PAY_HIS_DATA.REG_DATE,"+
			" DM_POS_PAY_HIS_DATA.REMAINED_DEDUCTION,DM_POS_PAY_HIS_DATA.SALES_TAX,DM_POS_PAY_HIS_DATA.TOTAL_AMOUNT"+
			" FROM  DM_POS_PAY_HIS_DATA,DM_POS_PAY_HIS_SEARCH_DATA"+ 
			" WHERE	DM_POS_PAY_HIS_DATA.CODE = DM_POS_PAY_HIS_SEARCH_DATA.CODE AND DM_POS_PAY_HIS_SEARCH_DATA.CODE_EXIST_FLAG = '1'"+
			 "AND DM_POS_PAY_HIS_SEARCH_DATA.SEARCH_ID ='"+search_file_id+"'";
			
			
			
			
			System.out.println("GET search data QUERY ISSSSSSSS "+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new PosHisSearchfileDataModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	
	
	public static Vector getallPosHisSearchfileData(Connection con,String search_file_id,String month,String year) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			//String strSql = " SELECT * FROM  DM_POS_PAY_HIS_SEARCH_DATA  where search_id ='"+search_file_id+"'";
			
			
			
			
	   /*		
	String strSql="SELECT DISTINCT(DM_POS_PAY_HIS_SEARCH_DATA.CODE),DM_POS_PAY_HIS_DATA.AMOUNT,DM_POS_PAY_HIS_DATA.BUDGET_ID,DM_POS_PAY_HIS_DATA.BUDGET_NAME,DM_POS_PAY_HIS_SEARCH_DATA.SEARCH_ID,"+
			" DM_POS_PAY_HIS_DATA.COMMENT1,DM_POS_PAY_HIS_DATA.COMMENT2,DM_POS_PAY_HIS_DATA.DEDUCTION_FEES,DM_POS_PAY_HIS_DATA.DONE,DM_POS_PAY_HIS_DATA.FINANCE_DATE,"+
			" DM_POS_PAY_HIS_DATA.MEMEO_DESC,DM_POS_PAY_HIS_DATA.MEMO_NAME,DM_POS_PAY_HIS_DATA.MSG,DM_POS_PAY_HIS_DATA.PHASE_DATE,DM_POS_PAY_HIS_DATA.REG_DATE,"+
			" DM_POS_PAY_HIS_DATA.REMAINED_DEDUCTION,DM_POS_PAY_HIS_DATA.SALES_TAX,DM_POS_PAY_HIS_DATA.TOTAL_AMOUNT"+
			" FROM  DM_POS_PAY_HIS_DATA,DM_POS_PAY_HIS_SEARCH_DATA"+ 
			" WHERE	DM_POS_PAY_HIS_DATA.CODE = DM_POS_PAY_HIS_SEARCH_DATA.CODE AND DM_POS_PAY_HIS_SEARCH_DATA.CODE_EXIST_FLAG = '1'"+
			 "AND DM_POS_PAY_HIS_SEARCH_DATA.SEARCH_ID ='"+search_file_id+"'";
			
		*/	
	


	String strSql=	 "SELECT  DISTINCT(DM_POS_PAY_HIS_SEARCH_DATA.CODE),DM_POS_PAY_HIS_DATA.AMOUNT,DM_POS_PAY_HIS_DATA.BUDGET_ID,DM_POS_PAY_HIS_DATA.BUDGET_NAME,DM_POS_PAY_HIS_SEARCH_DATA.SEARCH_ID, DM_POS_PAY_HIS_DATA.COMMENT1,DM_POS_PAY_HIS_DATA.COMMENT2,DM_POS_PAY_HIS_DATA.DEDUCTION_FEES,DM_POS_PAY_HIS_DATA.DONE,DM_POS_PAY_HIS_DATA.FINANCE_DATE, DM_POS_PAY_HIS_DATA.MEMEO_DESC,DM_POS_PAY_HIS_DATA.MEMO_NAME,DM_POS_PAY_HIS_DATA.MSG,DM_POS_PAY_HIS_DATA.PHASE_DATE,DM_POS_PAY_HIS_DATA.REG_DATE, DM_POS_PAY_HIS_DATA.REMAINED_DEDUCTION,DM_POS_PAY_HIS_DATA.SALES_TAX,DM_POS_PAY_HIS_DATA.TOTAL_AMOUNT "+ 
	" FROM  DM_POS_PAY_HIS_DATA,DM_POS_PAY_HIS_SEARCH_DATA,DM_POS_PAY_HIS_FILE "+
	" WHERE	DM_POS_PAY_HIS_DATA.CODE = DM_POS_PAY_HIS_SEARCH_DATA.CODE AND DM_POS_PAY_HIS_SEARCH_DATA.CODE_EXIST_FLAG = '1' "+
	" and DM_POS_PAY_HIS_SEARCH_DATA.search_id='"+search_file_id+"' "+
	" AND  DM_POS_PAY_HIS_DATA.FILE_ID =  ( (SELECT DISTINCT(file_id) from  DM_POS_PAY_HIS_FILE where DM_POS_PAY_HIS_FILE.MONTH ='"+month+"' and DM_POS_PAY_HIS_FILE.YEAR ='"+year+"'and status <> 'Deleted') )"; 

	 
	
	
	
	
	
			
			
			System.out.println("GET search data QUERY ISSSSSSSS "+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new PosHisSearchfileDataModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	
	public static Vector getInvalidposElgPOScode(Connection con,String search_file_Id) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
		//	String strSql = " select * from DM_POS_ELG_SEARCH_TEMP_SERIAL   WHERE  DM_POS_ELG_SEARCH_TEMP_SERIAL.SEARCH_ID ='"+search_file_Id+"' ";
			
			String strSql = " select distinct pos_code, SEARCH_ID from  DM_POS_elg_CHK_SEARCH_DATA where POS_CODE_EXIST_FLAG ='0' and SEARCH_ID ='"+search_file_Id+"' "	;
			
			System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new PosElgInvalidPosCode(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	public static Vector getInvalidposDELPOScode(Connection con,String search_file_Id) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = " select DISTINCT dcm_code , search_id  FROM DM_POS_DEL_CHK_SEARCH_DATA WHERE   DM_POS_DEL_CHK_SEARCH_DATA.CODE_EXIST_FLAG = '0' and DM_POS_DEL_CHK_SEARCH_DATA.SEARCH_ID ='"+search_file_Id+"'  ";
			
			
			
			System.out.println("GET all files QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new PosDelInvalidPosCode(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	
	
	public static Vector getInvalidposHISScode(Connection con,String search_file_Id) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
//			String strSql = " select * from DM_POS_HIS_SEARCH_TEMP_SERIAL   WHERE  SEARCH_ID ='"+search_file_Id+"' ";
			
			String strSql =	"select DISTINCT code , search_id  FROM DM_POS_PAY_HIS_SEARCH_DATA WHERE   DM_POS_PAY_HIS_SEARCH_DATA.CODE_EXIST_FLAG = '0' and DM_POS_PAY_HIS_SEARCH_DATA.SEARCH_ID ='"+search_file_Id+"' ";
			
			System.out.println("GET invalid code issssssssss"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new PosPayHisInvalidCode(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	
	
	
	public static Long insertPosElgSearchFile(String year, String month,String status,String description,String user_id)
	
	{
 
		Connection con=null;
		Long SEARCH_FILE_ID= null;

		try {
			
			 con = Utility.getConnection();
		
    Statement stat = con.createStatement();
    SEARCH_FILE_ID = Utility.getSequenceNextVal(con, "SEQ_pos_elg_SEARCH_FILE_ID");
          
			String strSql = "insert into  DM_ELG_CHK_SEARCH_FILE (SEARCH_FILE_ID, YEAR, MONTH, STATUS,DESCRIPTION,TIME_STAMP,USER_ID) "
					+ " values("+SEARCH_FILE_ID+",'"+year+"','"+month+"','"+status+"','"+description+"',SYSDATE,'"+user_id+"')";
	       System.out.println("the insert query is " +strSql);
	       
	       
	    
			stat.execute(strSql);		
			stat.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  SEARCH_FILE_ID;
	}
	

	
	
	public static Long insertPosDelSearchFile(String year, String month,String status,String description,String user_id)
	
	{
 
		Connection con=null;
		Long SEARCH_FILE_ID= null;

		try {
			
			 con = Utility.getConnection();
		
    Statement stat = con.createStatement();
    SEARCH_FILE_ID = Utility.getSequenceNextVal(con, "SEQ_pos_DEL_SEARCH_FILE_ID");
          
			String strSql = "insert into  DM_DEL_CHK_SEARCH_FILE (SEARCH_FILE_ID, YEAR, MONTH, STATUS,DESCRIPTION,TIME_STAMP,USER_ID) "
					+ " values("+SEARCH_FILE_ID+",'"+year+"','"+month+"','"+status+"','"+description+"',SYSDATE,'"+user_id+"')";
	       System.out.println("the insert query is " +strSql);
	       
	       
	    
			stat.execute(strSql);		
			stat.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  SEARCH_FILE_ID;
	}
	
	
	
	
	
	
	
	
	
	public static Long insertPayHisSearchFile(String year, String month,String status,String description,String user_id)
	
	{
 
		Connection con=null;
		Long SEARCH_FILE_ID= null;

		try {
			
			 con = Utility.getConnection();
		
    Statement stat = con.createStatement();
    SEARCH_FILE_ID = Utility.getSequenceNextVal(con,"SEQ_pos_HIS_SEARCH_FILE_ID");
          
			String strSql = "insert into  DM_HIS_CHK_SEARCH_FILE(SEARCH_FILE_ID, YEAR, MONTH, STATUS,DESCRIPTION,TIME_STAMP,USER_ID) "
					+ " values("+SEARCH_FILE_ID+",'"+year+"','"+month+"','"+status+"','"+description+"',SYSDATE,'"+user_id+"')";
	      
			System.out.println("the insert query is " +strSql);
	       
	       
	    
			stat.execute(strSql);		
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  SEARCH_FILE_ID;
	}
	
	

	public static boolean checkIfposCodeExist(String posCode ,String year,String month)
	{
		boolean Status=false;
		try {
			
  Connection con = Utility.getConnection();	
		
	 Statement stat = con.createStatement();
	 
	 


			  
			  
	//	String strsql= "  select * from auth_res_data  where auth_res_data.SIM_SERIAL='"+simSerial+"' and FILE_ID =  (SELECT DISTINCT(file_id) from "+
			//    	   " AUTH_RES_file where AUTH_RES_file.MONTH ='"+month+"' and AUTH_RES_file.year ='"+year+"'and  AUTH_RES_file.LABEL_ID  ='"+label+"'and status <> 'Deleted' ) ";
			  
	 String strsql= "select * from DM_POS_ELG_CHK_DATA where DM_POS_elg_CHK_DATa.POS_CODE    ='"+posCode+"' and FILE_ID =  (SELECT DISTINCT(file_id) from  DM_POS_ELG_CHK_file where DM_POS_ELG_CHK_file.MONTH ='"+month+"' and DM_POS_ELG_CHK_file.year ='"+year+"' and status <> 'Deleted' )";
		
	//	String strsql=	"select * from DM_POS_ELG_CHK_DATA    where DM_POS_DEL_CHK_DATA.POS_CODE  ='"+posCode+"' and FILE_ID =  (SELECT DISTINCT(file_id) from "+
		//	"	DM_POS_ELG_CHK_file where AUTH_RES_file.MONTH ='"+month+"' and AUTH_RES_file.year ='"+year+"'  'and status <> 'Deleted' ) ";
		
		      System.out.println("check if pos code exist query is query is " +strsql);
		      
		      ResultSet res = stat.executeQuery(strsql);
		      if (res.next())
		      {
		    	  
		    	Status=true;  
		    	
		    	  
		      }
			    
				stat.execute(strsql);		
				stat.close();
       } catch (Exception e) {
			e.printStackTrace();
		}
		
		return Status;
	}
	

	
	
	
	
	public static boolean checkIfposCodeExistDel(String posCode ,String year,String month)
	{
		boolean Status=false;
		try {
			
  Connection con = Utility.getConnection();	
		
	 Statement stat = con.createStatement();
	 
	 


			  
			  
	//	String strsql= "  select * from auth_res_data  where auth_res_data.SIM_SERIAL='"+simSerial+"' and FILE_ID =  (SELECT DISTINCT(file_id) from "+
			//    	   " AUTH_RES_file where AUTH_RES_file.MONTH ='"+month+"' and AUTH_RES_file.year ='"+year+"'and  AUTH_RES_file.LABEL_ID  ='"+label+"'and status <> 'Deleted' ) ";
			  
	 String strsql= "select * from DM_POS_del_CHK_DATA where DM_POS_del_CHK_DATa.DCM_CODE    ='"+posCode+"' and FILE_ID =  (SELECT DISTINCT(file_id) from  DM_POS_del_CHK_file where DM_POS_del_CHK_file.MONTH ='"+month+"' and DM_POS_del_CHK_file.year ='"+year+"' and status <> 'Deleted' )";
		
	//	String strsql=	"select * from DM_POS_ELG_CHK_DATA    where DM_POS_DEL_CHK_DATA.POS_CODE  ='"+posCode+"' and FILE_ID =  (SELECT DISTINCT(file_id) from "+
		//	"	DM_POS_ELG_CHK_file where AUTH_RES_file.MONTH ='"+month+"' and AUTH_RES_file.year ='"+year+"'  'and status <> 'Deleted' ) ";
		
		      System.out.println("check if pos code for dm exist query is query is " +strsql);
		      
		      ResultSet res = stat.executeQuery(strsql);
		      if (res.next())
		      {
		    	  
		    	Status=true;  
		    	
		    	  
		      }
			    
				stat.execute(strsql);		
				stat.close();
       } catch (Exception e) {
			e.printStackTrace();
		}
		
		return Status;
	}
	
	
	
	
	
	
	
	public static void insertPOSElgSearchData(Long searchFileId,String pos,String year, String month,Statement stat)
	
	{


		try {
		//	 Connection con = Utility.getConnection();
			 String EXIST_FLAG="0";
//Statement stat = con.createStatement();

String strSql = "insert into DM_POS_ELG_CHK_SEARCH_DATA(SEARCH_ID,POS_CODE,POS_CODE_EXIST_FLAG)"
					+ " values("+searchFileId+",'"+pos+"','"+EXIST_FLAG+"')";
	       System.out.println("the data insert query is " +strSql);
	       
	       stat.addBatch(strSql);
	     
	   //    updatePOSelgData(con,year,month,searchFileId.toString());
	       
	     
	
      		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	public static void updatePOSElgExistFlag(Long searchFileId,String month,String year)
	
	{


		try {
			 Connection con = Utility.getConnection();
			 
Statement stat = con.createStatement();

String strSql = " UPDATE DM_POS_elg_CHK_SEARCH_DATA SET POS_CODE_EXIST_FLAG = '1' WHERE  DM_POS_elg_CHK_SEARCH_DATA.POS_CODE IN (SELECT pos_CODE FROM DM_POS_elg_CHK_DATA  WHERE SEARCH_ID ='"+searchFileId+"' "+
" AND  DM_POS_ELG_CHK_DATA.FILE_ID = "+ 
 "(SELECT DISTINCT(file_id) from DM_POS_ELG_CHK_FILE where DM_POS_ELG_CHK_FILE.MONTH ='"+month+"' and DM_POS_ELG_CHK_FILE.YEAR ='"+year+"'and status <> 'Deleted')  ) ";
	      
System.out.println("the update flag query  issssssssis " +strSql);
	       

	       
	       
	       stat.execute(strSql);
           stat.close();
           Utility.closeConnection(con);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	
	
	
	public static void updatePOSPayHisExistFlag(Long searchFileId)
	
	{


		try {
			 Connection con = Utility.getConnection();
			 
Statement stat = con.createStatement();

//String strSql = " UPDATE DM_POS_elg_CHK_SEARCH_DATA SET POS_CODE_EXIST_FLAG = '1' WHERE  DM_POS_elg_CHK_SEARCH_DATA.POS_CODE IN (SELECT pos_CODE FROM DM_POS_elg_CHK_DATA  WHERE SEARCH_ID ='"+searchFileId+"')";
String strSql="UPDATE DM_POS_PAY_HIS_SEARCH_DATA  SET  CODE_EXIST_FLAG = '1' WHERE  DM_POS_PAY_HIS_SEARCH_DATA.CODE  IN (SELECT CODE FROM DM_POS_PAY_HIS_DATA  WHERE SEARCH_ID ='"+searchFileId+"')";	      

System.out.println("the update flag query  issssssssis " +strSql);
	       
	       stat.execute(strSql);
           stat.close();
           Utility.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	
	
	public static void updatePOSDelChkExistFlag(Long searchFileId,String month,String year)
	
	{


		try {
			 Connection con = Utility.getConnection();
			 
Statement stat = con.createStatement();

//String strSql = " UPDATE DM_POS_elg_CHK_SEARCH_DATA SET POS_CODE_EXIST_FLAG = '1' WHERE  DM_POS_elg_CHK_SEARCH_DATA.POS_CODE IN (SELECT pos_CODE FROM DM_POS_elg_CHK_DATA  WHERE SEARCH_ID ='"+searchFileId+"')";
//String strSql="UPDATE DM_POS_PAY_HIS_SEARCH_DATA  SET  CODE_EXIST_FLAG = '1' WHERE  DM_POS_PAY_HIS_SEARCH_DATA.CODE  IN (SELECT CODE FROM DM_POS_PAY_HIS_DATA  WHERE SEARCH_ID ='"+searchFileId+"')";	      

/*
String strSql=" UPDATE DM_POS_PAY_HIS_SEARCH_DATA  SET  CODE_EXIST_FLAG = '1' WHERE  DM_POS_PAY_HIS_SEARCH_DATA.CODE  IN (SELECT CODE FROM DM_POS_PAY_HIS_DATA  WHERE SEARCH_ID ='"+searchFileId+"' "+
 " AND  DM_POS_PAY_HIS_DATA.FILE_ID = "+ 
 "  (SELECT DISTINCT(file_id) from  DM_POS_PAY_HIS_FILE where DM_POS_PAY_HIS_FILE.MONTH ='"+month+"' and DM_POS_PAY_HIS_FILE.YEAR ='"+year+"'and status <> 'Deleted')  ) ";
*/


String strSql="UPDATE DM_POS_DEL_CHK_SEARCH_DATA  SET  CODE_EXIST_FLAG = '1' WHERE  DM_POS_DEL_CHK_SEARCH_DATA. DCM_CODE   IN (SELECT  DCM_CODE FROM DM_POS_DEL_CHK_DATA  WHERE SEARCH_ID ='"+searchFileId+"' "+
 " AND  DM_POS_DEL_CHK_DATA.FILE_ID = "+ 
  " (SELECT DISTINCT(file_id) from  DM_POS_DEL_CHK_FILE where DM_POS_DEL_CHK_FILE.MONTH ='"+month+"' and DM_POS_DEL_CHK_FILE.YEAR ='"+year+"'and status <> 'Deleted')  ) ";


System.out.println("the update flag query  issssssssis " +strSql);
	       
	       stat.execute(strSql);
           stat.close();
           Utility.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	
	public static void updatePOSPayHisExistFlag(Long searchFileId,String month,String year)
	
	{


		try {
			 Connection con = Utility.getConnection();
			 
Statement stat = con.createStatement();

//String strSql = " UPDATE DM_POS_elg_CHK_SEARCH_DATA SET POS_CODE_EXIST_FLAG = '1' WHERE  DM_POS_elg_CHK_SEARCH_DATA.POS_CODE IN (SELECT pos_CODE FROM DM_POS_elg_CHK_DATA  WHERE SEARCH_ID ='"+searchFileId+"')";
//String strSql="UPDATE DM_POS_PAY_HIS_SEARCH_DATA  SET  CODE_EXIST_FLAG = '1' WHERE  DM_POS_PAY_HIS_SEARCH_DATA.CODE  IN (SELECT CODE FROM DM_POS_PAY_HIS_DATA  WHERE SEARCH_ID ='"+searchFileId+"')";	      


String strSql=" UPDATE DM_POS_PAY_HIS_SEARCH_DATA  SET  CODE_EXIST_FLAG = '1' WHERE  DM_POS_PAY_HIS_SEARCH_DATA.CODE  IN (SELECT CODE FROM DM_POS_PAY_HIS_DATA  WHERE SEARCH_ID ='"+searchFileId+"' "+
 " AND  DM_POS_PAY_HIS_DATA.FILE_ID = "+ 
 "  (SELECT DISTINCT(file_id) from  DM_POS_PAY_HIS_FILE where DM_POS_PAY_HIS_FILE.MONTH ='"+month+"' and DM_POS_PAY_HIS_FILE.YEAR ='"+year+"'and status <> 'Deleted')  ) ";




System.out.println("the update flag query  issssssssis " +strSql);
	       
	       stat.execute(strSql);
           stat.close();
           Utility.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public static boolean checkIfposCodeExistHis(String posCode ,String year,String month)
	{
		boolean Status=false;
		try {
			
  Connection con = Utility.getConnection();	
		
	 Statement stat = con.createStatement();
	 
	 


			  
			  
	//	String strsql= "  select * from auth_res_data  where auth_res_data.SIM_SERIAL='"+simSerial+"' and FILE_ID =  (SELECT DISTINCT(file_id) from "+
			//    	   " AUTH_RES_file where AUTH_RES_file.MONTH ='"+month+"' and AUTH_RES_file.year ='"+year+"'and  AUTH_RES_file.LABEL_ID  ='"+label+"'and status <> 'Deleted' ) ";
			  
	 String strsql= "select * from DM_POS_PAY_HIS_DATA where DM_POS_PAY_HIS_DATA.CODE    ='"+posCode+"' and FILE_ID =  (SELECT DISTINCT(file_id) from  DM_POS_PAY_HIS_FILE where DM_POS_PAY_HIS_FILE.MONTH ='"+month+"' and DM_POS_PAY_HIS_FILE.year ='"+year+"' and status <> 'Deleted' )";
		
	//	String strsql=	"select * from DM_POS_ELG_CHK_DATA    where DM_POS_DEL_CHK_DATA.POS_CODE  ='"+posCode+"' and FILE_ID =  (SELECT DISTINCT(file_id) from "+
		//	"	DM_POS_ELG_CHK_file where AUTH_RES_file.MONTH ='"+month+"' and AUTH_RES_file.year ='"+year+"'  'and status <> 'Deleted' ) ";
		
		      System.out.println("check if pos code for dm exist query is query is " +strsql);
		      
		      ResultSet res = stat.executeQuery(strsql);
		      if (res.next())
		      {
		    	  
		    	Status=true;  
		    	
		    	  
		      }
			    
				stat.execute(strsql);		
				stat.close();
				Utility.closeConnection(con);
       } catch (Exception e) {
			e.printStackTrace();
		}
		
		return Status;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void updatePOSelgData(Connection con,String year, String month,String search_file_id )
	{

		try {
			
			con = Utility.getConnection();
			Statement stat = con.createStatement();





//String  sql="  update DM_POS_ELG_CHK_SEARCH_DATA"+ 
// " SET(POS_ENM  ,ENTRY_DT ,STK_DLVR_DT ,IQRAR_DLVR_DTE,IQRAR_RCV_DT,POS_STATUS ,STK_STATUS ,REGIONAL_NAME, REP_NAME ,CHANNEL_CODE ,LEVEL_CODE,STK_DIAL_NO )="+
//; " (select DIAL ,ENTRY_DT ,PSTK_DLVR_DT ,IQRAR_DLVR_DT,IQRAR_RCV_DT,POS_STATUS ,STK_STATUS ,REGIONAL_NAME,REP_NAME ,CHANNEL_CODE,LEVEL_CODE,STK_DIAL_NO "+  
// " FROM  DM_POS_ELG_CHK_DATA  WHERE    DM_POS_ELG_CHK_DATA.POS_CODE=DM_POS_ELG_CHK_SEARCH_DATA.POS_CODE AND  DM_POS_ELG_CHK_DATA.FILE_ID =  (SELECT DISTINCT(file_id) from "+
// " DM_POS_ELG_CHK_FILE where DM_POS_ELG_CHK_FILE.MONTH ='"+month+"' and DM_POS_ELG_CHK_FILE.year ='"+year+"'and status <> 'Deleted') )   where DM_POS_ELG_CHK_SEARCH_DATA='"+search_file_id+"'" ;


String  sql= "update DM_POS_ELG_CHK_SEARCH_DATA SET(POS_ENM ,ENTRY_DT,STK_DLVR_DT,IQRAR_DLVR_DT,IQRAR_RCV_DT,POS_STATUS ,STK_STATUS ,REGIONAL_NAME, REP_NAME ,CHANNEL_CODE ,LEVEL_CODE,STK_DIAL_NO )= (select POS_ENM ,ENTRY_DT ,STK_DLVR_DT ,IQRAR_DLVR_DT,IQRAR_RCV_DT,POS_STATUS ,STK_STATUS ,REGIONAL_NAME,REP_NAME ,CHANNEL_CODE,LEVEL_CODE,STK_DIAL_NO  FROM  DM_POS_ELG_CHK_DATA  WHERE    DM_POS_ELG_CHK_DATA.POS_CODE=DM_POS_ELG_CHK_SEARCH_DATA.POS_CODE AND  DM_POS_ELG_CHK_DATA.FILE_ID =  (SELECT DISTINCT(file_id) from  DM_POS_ELG_CHK_FILE where DM_POS_ELG_CHK_FILE.MONTH ='"+month+"' and DM_POS_ELG_CHK_FILE.year ='"+year+"'and status <> 'Deleted') )   where DM_POS_ELG_CHK_SEARCH_DATA.SEARCH_ID='"+search_file_id+"' ";

	



System.out.print("the update query is :"+sql);
			 
int x=stat.executeUpdate(sql);	


System.out.println("Records Updated:"+x);

			
			
				stat.close();
				Utility.closeConnection(con);
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	
	
/*	
public static void insertPOSDelSearchData(Long searchFileId,String pos,String year, String month)
	
	{


		try {
			 Connection con = Utility.getConnection();
Statement stat = con.createStatement();

String strSql = "insert into DM_POS_del_CHK_SEARCH_DATA(SEARCH_ID,DCM_CODE)"
					+ " values("+searchFileId+",'"+pos+"')";
	       System.out.println("the data insert query is " +strSql);
	       
	       stat.execute(strSql);
	     
	       updatePOSdelData(con,year,month,searchFileId.toString());
	       
	     
	
      		
			stat.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	*/
	
	
	
	
	
	
	
	public static void insertPOSDelSearchData(Long searchFileId,String pos,String year, String month,Statement stat)
		
		{


			try {
			//	 Connection con = Utility.getConnection();
	//Statement stat = con.createStatement();
	String exist_flag="0";

	String strSql = "insert into DM_POS_del_CHK_SEARCH_DATA(SEARCH_ID,DCM_CODE,CODE_EXIST_FLAG)"
						+ " values("+searchFileId+",'"+pos+"','"+exist_flag+"')";
		 
	System.out.println("the data insert query is " +strSql);
		     
	      
		      

		  stat.addBatch(strSql);

	     
		     
		     //  updatePOShisData(con,year,month,searchFileId.toString());
		       

				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

		}
	
	
	
	
	
	
	
	

	public static void updatePOSdelData(Connection con,String year, String month,String search_file_id )
	{

		try {
			
			con = Utility.getConnection();
			Statement stat = con.createStatement();


			String  sql="update DM_POS_del_CHK_SEARCH_DATA SET(DELIVERED ,NI,PROBL,NOTD,ACC )= (select DELIVERED ,NI ,PROBL ,NOTD,ACC FROM  DM_POS_del_CHK_DATA  WHERE    DM_POS_del_CHK_DATA.DCM_CODE=DM_POS_DEL_CHK_SEARCH_DATA.DCM_CODE AND  DM_POS_del_CHK_DATA.FILE_ID =  (SELECT DISTINCT(file_id) from  DM_POS_del_CHK_FILE where DM_POS_del_CHK_FILE.MONTH ='"+month+"' and DM_POS_del_CHK_FILE.YEAR ='"+year+"'and status <> 'Deleted') )   where DM_POS_DEL_CHK_SEARCH_DATA.SEARCH_ID='"+search_file_id+"' ";



  
			System.out.print("the update query is :"+sql);

			 
            int x=stat.executeUpdate(sql);	


System.out.println("Records Updated:"+x);
			
			
				stat.close();
	  
			 
				Utility.closeConnection(con);
		}
		  
		catch (Exception e) {
			
			e.printStackTrace();
		}
		

	}
	
	
	
	
	
	
	
	
	
	
	
	public static void updatePOShisData(Connection con,String year, String month,String search_file_id )
	{

		try {
			
			con = Utility.getConnection();
			Statement stat = con.createStatement();


		//	String  sql="update DM_POS_del_CHK_SEARCH_DATA SET(DELIVERED ,NI,PROBL,NOTD,ACC )= (select DELIVERED ,NI ,PROBL ,NOTD,ACC FROM  DM_POS_del_CHK_DATA  WHERE    DM_POS_del_CHK_DATA.DCM_CODE=DM_POS_DEL_CHK_SEARCH_DATA.DCM_CODE AND  DM_POS_del_CHK_DATA.FILE_ID =  (SELECT DISTINCT(file_id) from  DM_POS_del_CHK_FILE where DM_POS_del_CHK_FILE.MONTH ='"+month+"' and DM_POS_del_CHK_FILE.YEAR ='"+year+"'and status <> 'Deleted') )   where DM_POS_DEL_CHK_SEARCH_DATA.SEARCH_ID='"+search_file_id+"' ";


			String  sql=	" update DM_POS_PAY_HIS_SEARCH_DATA SET(PHASE_DATE,MEMO_NAME,TOTAL_AMOUNT,AMOUNT,DONE,MSG,SALES_TAX,REG_DATE,COMMENT1,COMMENT2,MEMEO_DESC,DEDUCTION_FEES ,REMAINED_DEDUCTION,FINANCE_DATE,BUDGET_ID,BUDGET_NAME  )="+
				"(select PHASE_DATE,MEMO_NAME,TOTAL_AMOUNT,AMOUNT,DONE,MSG,SALES_TAX,REG_DATE,COMMENT1,COMMENT2,MEMEO_DESC,DEDUCTION_FEES ,REMAINED_DEDUCTION,FINANCE_DATE,BUDGET_ID,BUDGET_NAME FROM  DM_POS_PAY_HIS_DATA WHERE    DM_POS_PAY_HIS_DATA.CODE=DM_POS_PAY_HIS_SEARCH_DATA.CODE AND  DM_POS_PAY_HIS_DATA.FILE_ID =  (SELECT DISTINCT(file_id) from  DM_POS_PAY_HIS_FILE where DM_POS_PAY_HIS_FILE.MONTH ='"+month+"' and DM_POS_PAY_HIS_FILE.YEAR ='"+year+"'and status <> 'Deleted') )   where DM_POS_PAY_HIS_SEARCH_DATA.SEARCH_ID='"+search_file_id+"' ";
			
			
			

    System.out.print("the update query is :"+sql);
			 
    int x=stat.executeUpdate(sql);	


    System.out.println("Records Updated:"+x);
			
			
				stat.close();
	  
				Utility.closeConnection(con);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	

	
	
public static void insertPOSHisSearchData(Long searchFileId,String pos,String year, String month,Statement stat)
	
	{


		try {
		//	 Connection con = Utility.getConnection();
//Statement stat = con.createStatement();
String exist_flag="0";

String strSql = "insert into DM_POS_PAY_HIS_SEARCH_DATA(SEARCH_ID,CODE,CODE_EXIST_FLAG)"
					+ " values("+searchFileId+",'"+pos+"','"+exist_flag+"')";
	 
// System.out.println("the data insert query is " +strSql);
	     
      
	      

	  stat.addBatch(strSql);

     
	     
	     //  updatePOShisData(con,year,month,searchFileId.toString());
	       

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	
	
	

	public static void insertInvalidPOScODE(String  search_fileid,String POS)
	
	{

   try {
			 Connection con = Utility.getConnection();
Statement stat = con.createStatement();

String strSql = "insert into DM_POS_ELG_SEARCH_TEMP_SERIAL(SEARCH_ID,SIM_SERIAL)"
					+ " values('"+search_fileid+"','"+POS+"') " ;
	       System.out.println("the data insert invalid POS CODE query is " +strSql);
	       
  stat.execute(strSql);		
			stat.close();
			Utility.closeConnection(con);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	public static void insertInvalidPOScODEDel(String  search_fileid,String POS)
	
	{

   try {
			 Connection con = Utility.getConnection();
			Statement stat = con.createStatement();

			String strSql = "insert into DM_POS_del_SEARCH_TEMP_SERIAL(SEARCH_ID,SIM_SERIAL)"
					+ " values('"+search_fileid+"','"+POS+"') " ;
	       //System.out.println("the data insert invalid POS CODE query is " +strSql);
	       
  stat.execute(strSql);		
			stat.close();
			Utility.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	public static void insertInvalidPOScODEHis(String  search_fileid,String POS)
	
	{

   try {
			 Connection con = Utility.getConnection();
Statement stat = con.createStatement();

String strSql = "insert into DM_POS_his_SEARCH_TEMP_SERIAL(SEARCH_ID,SIM_SERIAL)"
					+ " values('"+search_fileid+"','"+POS+"') " ;
	       System.out.println("the data insert invalid  CODE query is " +strSql);
	       
  stat.execute(strSql);		
			stat.close();
			Utility.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	public static HashMap getRevenueReport()
	{
		HashMap data = new HashMap();
		
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "SELECT   VW_GEN_DCM.DCM_NAME AS Dcm_Name,arpu_category.PRODUCT_ID,"+
							"gen_product.PRODUCT_NAME,arpu_category_type.CATEGORY_NAME,"+
							" COUNT (ARPU_DATA.IMSI) AS count_of_lines,"+							
							"SUM (DECODE (SIGN (ARPU_DATA.U1), +1, 1, 0)) AS Count_of_Positive_U1,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U1), -1, 1, 0)) AS Count_of_Negative_U1,"+
							"SUM (DECODE (ARPU_DATA.U1, 0, 1, 0)) AS Count_of_Zero_U1,"+
							"SUM (ARPU_DATA.U1) AS RPU_U1,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U2), +1, 1, 0)) AS Count_of_Positive_U2,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U2), -1, 1, 0)) AS Count_of_Negative_U2,"+
							"SUM (DECODE (ARPU_DATA.U2, 0, 1, 0)) AS Count_of_Zero_U2,"+
							" SUM (ARPU_DATA.U2) AS RPU_U2,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U3), +1, 1, 0)) AS Count_of_Positive_U3,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U3), -1, 1, 0) ) AS Count_of_Negative_U3,"+
							"SUM (DECODE (ARPU_DATA.U3, 0, 1, 0)) AS Count_of_Zero_U3,"+
							" SUM (ARPU_DATA.U3) AS RPU_U3,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U4), +1, 1, 0)) AS Count_of_Positive_U4,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U4), -1, 1, 0)) AS Count_of_Negative_U4,"+
							"SUM (DECODE (ARPU_DATA.U4, 0, 1, 0)) AS Count_of_Zero_U4,"+
							"SUM (ARPU_DATA.U4) AS RPU_U4 "+
							"FROM ARPU_DATA, VW_GEN_DCM, arpu_category,gen_product,arpu_category_type "+
							"WHERE VW_GEN_DCM.DCM_ID = ARPU_DATA.LCS_DCM_ID "+
							"and ARPU_DATA.LCS_CONTRACT_TYPE_ID = arpu_category.PRODUCT_ID "+
							"and arpu_category.PRODUCT_ID = gen_product.PRODUCT_ID "+
							"and arpu_category.CATEGORY_ID = arpu_category_type.CATEGORY_ID "+
							"GROUP BY VW_GEN_DCM.DCM_NAME,arpu_category.PRODUCT_ID,gen_product.PRODUCT_NAME,"+
							"arpu_category_type.CATEGORY_NAME";
			System.out.println("The query isssssssssss " + strSql);
			
			ResultSet res = stat.executeQuery(strSql);
			while(res.next())
			{
				String categoryName = res.getString("CATEGORY_NAME");
				Vector revenueReportCategory = getRevenueReportByCategoryName(categoryName);
				data.put(categoryName, revenueReportCategory);
			}
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	
	public static Vector getRevenueReportByCategoryName (String categoryName)
	{
		Vector revenueReportCategory =  new Vector();
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "SELECT   VW_GEN_DCM.DCM_NAME AS Dcm_Name,arpu_category.PRODUCT_ID,"+
							"gen_product.PRODUCT_NAME,arpu_category_type.CATEGORY_NAME,"+
							" COUNT (ARPU_DATA.IMSI) AS count_of_lines,"+							
							"SUM (DECODE (SIGN (ARPU_DATA.U1), +1, 1, DECODE (SIGN (ARPU_DATA.U2), +1, 1, DECODE (SIGN (ARPU_DATA.U3), +1, 1, DECODE (SIGN (ARPU_DATA.U4), +1, 1, 0))))) AS count_unique_positive,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U1), +1, 1, 0)) AS Count_of_Positive_U1,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U1), -1, 1, 0)) AS Count_of_Negative_U1,"+
							"SUM (DECODE (ARPU_DATA.U1, 0, 1, 0)) AS Count_of_Zero_U1,"+
							"SUM (ARPU_DATA.U1) AS RPU_U1,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U2), +1, 1, 0)) AS Count_of_Positive_U2,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U2), -1, 1, 0)) AS Count_of_Negative_U2,"+
							"SUM (DECODE (ARPU_DATA.U2, 0, 1, 0)) AS Count_of_Zero_U2,"+
							" SUM (ARPU_DATA.U2) AS RPU_U2,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U3), +1, 1, 0)) AS Count_of_Positive_U3,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U3), -1, 1, 0) ) AS Count_of_Negative_U3,"+
							"SUM (DECODE (ARPU_DATA.U3, 0, 1, 0)) AS Count_of_Zero_U3,"+
							" SUM (ARPU_DATA.U3) AS RPU_U3,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U4), +1, 1, 0)) AS Count_of_Positive_U4,"+
							"SUM (DECODE (SIGN (ARPU_DATA.U4), -1, 1, 0)) AS Count_of_Negative_U4,"+
							"SUM (DECODE (ARPU_DATA.U4, 0, 1, 0)) AS Count_of_Zero_U4,"+
							"SUM (ARPU_DATA.U4) AS RPU_U4 "+
							"FROM ARPU_DATA, VW_GEN_DCM, arpu_category,gen_product,arpu_category_type "+
							"WHERE VW_GEN_DCM.DCM_ID = ARPU_DATA.LCS_DCM_ID "+
							"and arpu_category_type.CATEGORY_NAME = '"+categoryName+"' "+
							"and ARPU_DATA.LCS_CONTRACT_TYPE_ID = arpu_category.PRODUCT_ID "+
							"and arpu_category.PRODUCT_ID = gen_product.PRODUCT_ID "+
							"and arpu_category.CATEGORY_ID = arpu_category_type.CATEGORY_ID "+
							"GROUP BY VW_GEN_DCM.DCM_NAME,arpu_category.PRODUCT_ID,gen_product.PRODUCT_NAME,"+
							"arpu_category_type.CATEGORY_NAME";
			ResultSet res = stat.executeQuery(strSql);
			while(res.next())
			{
				revenueReportCategory.add(new RevenueReportModel(res));
			}
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return revenueReportCategory;
		
	}
	
	public static Vector getAllCategories()
	{
		Vector categoryVec = new Vector();
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "select * from ARPU_CATEGORY_TYPE order by category_id";
			ResultSet res = stat.executeQuery(strSql);
			while(res.next())
			{
				categoryVec.add(new CategoryModel(res));
			}
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return categoryVec;
	}
	
	public static void saveNewCategory(String categoryName)
	{
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			Long categoryId = Utility.getSequenceNextVal(con,"ARPU_CATEGORY_SEQ");
			String strSql = "insert into ARPU_CATEGORY_TYPE(category_id,category_name)values('"+categoryId+"','"+categoryName+"')";
			stat.executeUpdate(strSql);
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void deleteCategory(String categoryId)
	{
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "delete from ARPU_CATEGORY_TYPE where category_id = '"+categoryId+"'";
			stat.executeUpdate(strSql);
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static Vector getCategoryProduct(String categoryId)
	{
		Vector categoryProduct = new Vector();
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "SELECT ARPU_CATEGORY.PRODUCT_ID,GEN_PRODUCT.PRODUCT_NAME "+
							"FROM ARPU_CATEGORY,GEN_PRODUCT "+
							"WHERE ARPU_CATEGORY.CATEGORY_ID = '"+categoryId+"' "+
							"AND ARPU_CATEGORY.PRODUCT_ID = GEN_PRODUCT.PRODUCT_ID";
			ResultSet res = stat.executeQuery(strSql);
			while(res.next())
			{
				categoryProduct.add(new ProductModel(res));
			}
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return categoryProduct;
	}
	
	public static void deleteCategoryProduct(String categoryId,String productId)
	{
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "delete from ARPU_CATEGORY where category_id = '"+categoryId+"'and product_id = '"+productId+"'";
			stat.executeUpdate(strSql);
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static Vector getAllProduct()
	{
		Vector productVec = new Vector();
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "select product_id,product_name from gen_product order by product_id";
			ResultSet res = stat.executeQuery(strSql);
			while (res.next())
			{
				productVec.add(new ProductModel(res));
			}
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return productVec;
	}
	
	public static void saveCategoryProduct(String categoryId,String productId)
	{
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "insert into ARPU_CATEGORY(category_id,product_id)values('"+categoryId+"','"+productId+"')";
			stat.executeUpdate(strSql);
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static Vector getAllProblematicLabel()
	{
		Vector problematicVec = new Vector();
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "select * from DATA_PROBLEMATIC_LABEL order by PROBLEMATIC_LABEL_ID";
			ResultSet res = stat.executeQuery(strSql);
			while (res.next())
			{
				problematicVec.add(new ProblematicModel(res));
			}
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return problematicVec;
	}
	
	public static Vector getAllPaymentLevel()
	{
		Vector paymentLevelVec = new Vector();
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "select * from GEN_DCM_PAYMENT_LEVEL order by DCM_PAYMENT_LEVEL_ID";
			
			ResultSet res = stat.executeQuery(strSql);
			while (res.next())
			{
				paymentLevelVec.add(new PaymentLevelModel(res));
			}
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return paymentLevelVec;
	}
	
	
	
	
	public static PreparedStatement getInsertSimProblimiticDataPreparedStatemnt(Connection con,Long fileId,String problematicId,String paymentLevelId)
	{
		PreparedStatement p = null;
		
		try{
			String sql = "insert into DATA_PROBLEMATIC(sim,file_id,PROBLEMATIC_LABEL_ID,PAYMENT_LEVEL_ID)values(?,'"+fileId+"','"+problematicId+"','"+paymentLevelId+"')";
			
			p = con.prepareStatement(sql);
			
		}
		catch(Exception e)
		{
		e.printStackTrace();	
		}
		return p;
	}
	

	public static void insertSimNumber(PreparedStatement ps, String simNumber)
	{
		try
		{
			//Connection con = Utility.getConnection();
			//Statement stat = con.createStatement();
		//	String strSql = "insert into DATA_PROBLEMATIC(sim,file_id,PROBLEMATIC_LABEL_ID,PAYMENT_LEVEL_ID)values('"+simNumber+"','"+fileId+"','"+problematicId+"','"+paymentLevelId+"')";
			//System.out.println("The insert statement isssss " +strSql);
			
			ps.setString(1, simNumber);
			ps.execute();
			
			//stat.close();
			//Utility.closeConnection(con);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static Long insertNewProblematicFile(String year,  String month,String status,String user_ID,String paymentLevelName,String problematicName)
	
	{

		Long FILE_ID=null;

		try {
		      Connection con = Utility.getConnection();
			
        Statement stat = con.createStatement();
	  	FILE_ID = Utility.getSequenceNextVal(con, "SEQ_DATA_PROBLEMATIC_FILE_ID");
          
			String strSql = "insert into DATA_PROBLEMATIC_FILE(FILE_ID, YEAR, MONTH, STATUS,USER_ID,TIME_STAMP,PROBLEMATIC_LABEL_NAME,PAYMENT_LAVEL_NAME) "
					+ " values("+FILE_ID+",'"+year+"','"+month+"','"+status+"','"+user_ID+"',sysdate,'"+problematicName+"','"+paymentLevelName+"')";
	       System.out.println("the insert query is " +strSql);
	       
           stat.execute(strSql);		
			stat.close();
			Utility.closeConnection(con);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  FILE_ID;
	}
	
	public static Vector getNotIntializedSerialsFile()
	{
		Vector serialsFile = new Vector();
		try
		{
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			//String strSql = "select DATA_PROBLEMATIC_FILE.FILE_ID,year,month,status,time_stamp,user_id,DATA_PROBLEMATIC_LABEL.PROBLEMATIC_LABEL_NAME,gen_dcm_payment_level.DCM_PAYMENT_LEVEL_NAME "+
				//			"from DATA_PROBLEMATIC_FILE,DATA_PROBLEMATIC ,gen_dcm_payment_level,DATA_PROBLEMATIC_LABEL "+
					//		"WHERE DATA_PROBLEMATIC.file_id =  DATA_PROBLEMATIC_FILE.FILE_ID "+
						//	"and DATA_PROBLEMATIC_LABEL.PROBLEMATIC_LABEL_ID = DATA_PROBLEMATIC.PROBLEMATIC_LABEL_ID "+
							//"and gen_dcm_payment_level.DCM_PAYMENT_LEVEL_ID = DATA_PROBLEMATIC.PAYMENT_LEVEL_ID "+
							//"and STATUS = 'active' order by DATA_PROBLEMATIC_FILE.FILE_ID";
			String strSql = "select * from DATA_PROBLEMATIC_FILE where STATUS = 'active' order by FILE_ID";
			ResultSet res = stat.executeQuery(strSql);
			while(res.next())
			{
				serialsFile.add(new NotIntializedSerialsModel(res));
			}
			Utility.closeConnection(con);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return serialsFile;
	}
	
	public static boolean checkNotIntializedSerialsFile(String year,String month,String paymentLevelId,String problematicId)
	{
		boolean Status=false;
		try {
			
				Connection con = Utility.getConnection();	
				Statement stat = con.createStatement();
				String strSql = "select year,month,PAYMENT_LEVEL_ID,PROBLEMATIC_LABEL_ID from  DATA_PROBLEMATIC_FILE,DATA_PROBLEMATIC"+ 
								"  where PAYMENT_LEVEL_ID = '"+paymentLevelId+"' and PROBLEMATIC_LABEL_ID = '"+problematicId+"' "+
								"and DATA_PROBLEMATIC_FILE.file_id = DATA_PROBLEMATIC.file_id "+
								"and YEAR = '"+year+"' and  MONTH='"+month+"' and lower(status) <> 'deleted'  ";  
				System.out.println("the change status query  is " +strSql);
				ResultSet res = stat.executeQuery(strSql);
				if (res.next())
				{		    	  
					Status=true;  
				}
				stat.execute(strSql);		
				stat.close();
				Utility.closeConnection(con);
			} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return Status;
	}
	
	public static String getPyamentLevelName (String paymentLevelId)
	{
		String paymentLevelName = "";
		try
		{
			Connection con = Utility.getConnection();	
			Statement stat = con.createStatement();
			String strSql = "select DCM_PAYMENT_LEVEL_NAME from GEN_DCM_PAYMENT_LEVEL where DCM_PAYMENT_LEVEL_ID = '"+paymentLevelId+"'";
			ResultSet res = stat.executeQuery(strSql);
			while (res.next())
			{
				paymentLevelName = res.getString("DCM_PAYMENT_LEVEL_NAME");
			}
			stat.close();
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return paymentLevelName;
	}
	
	public static String getProblematicName (String problematicId)
	{
		String problematicName = "";
		try
		{
			Connection con = Utility.getConnection();	
			Statement stat = con.createStatement();
			String strSql = "select PROBLEMATIC_LABEL_NAME from DATA_PROBLEMATIC_LABEL where PROBLEMATIC_LABEL_ID = '"+problematicId+"'";
			ResultSet res = stat.executeQuery(strSql);
			while (res.next())
			{
				problematicName = res.getString("PROBLEMATIC_LABEL_NAME");
			}
			stat.close();
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return problematicName;
	}
	public static void deleteNotInializedSerialsFile(String fileId)
	{
		try
		{
			Connection con = Utility.getConnection();	
			Statement stat = con.createStatement();
			String strSql = "update DATA_PROBLEMATIC_FILE set status = 'deleted' where file_id = '"+fileId+"'";
			stat.executeUpdate(strSql);
			stat.close();
			Utility.closeConnection(con);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static int  getNotIntialzedinsrerteValue(Long file_id)
	{
	   int count=0;
	    try
	    {
	     Connection con = Utility.getConnection();
	     Statement stat = con.createStatement();
	     System.out.println("dddddddddddddddd "+file_id);
	     ResultSet res=   	stat.executeQuery("select count(*)  as counter   from   DATA_PROBLEMATIC where file_Id="+file_id+"");
	     res.next();
	     count=res.getInt("counter");
	     
	     stat.close();
	     Utility.closeConnection(con);
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	      return count; 
	}
	


}
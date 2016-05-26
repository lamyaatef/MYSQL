package com.mobinil.sds.core.system.aacm.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.sop.*;
import com.mobinil.sds.core.system.aacm.model.*;
import com.mobinil.sds.core.system.dcm.pos.model.POSDetailModel;
import com.mobinil.sds.core.system.sop.schemas.model.ProductImportModel;
import com.mobinil.sds.core.system.sop.schemas.model.ProductModel;


public class AuthAgentDAO {

	 private    AuthAgentDAO()
	  {
	     System.out.println("hi");
	  }
	 
	 
	 public static Vector getAllAuthAgent(Connection con,String channelId)
	  {
	    Vector authVec = new Vector();
	    try
	    {
	      Statement stat = con.createStatement();
	      ResultSet res = stat.executeQuery("select * from AACM_AUTH_AGENT,GEN_DCM"+
	    		  							" where AACM_AUTH_AGENT.AUTH_AGENT_CODE = GEN_DCM.DCM_code and CHANNEL_ID = '"+channelId+"'");
	      
	      while (res.next())
	      {
	    	  	
	        AuthAgentModel authAgentModel = new AuthAgentModel(res);
	        authVec.add(authAgentModel);
	      }
	      java.util.Date date = new java.util.Date();
	      
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return authVec;
	  }
	 
	 public static Long insertGenDcm(Connection con , String authAgentCode ,String authAgentName,String channelId)
	  {
	     Long genDcmId = null;
	     try
	      {
	        Statement stmt = con.createStatement();      
	        
	        genDcmId = Utility.getSequenceNextVal(con , "SEQ_GEN_DCM_ID" );
	        
	        String sqlString = "INSERT INTO GEN_DCM (DCM_ID ,DCM_NAME,  DCM_CODE,CHANNEL_ID )"+
	                    "VALUES("+genDcmId+",'"+authAgentName+"','"+authAgentCode+"','"+channelId+"')";
	        System.out.println("The Gen DCM insert query isssssss " + sqlString);
	        stmt.executeUpdate(sqlString);
	        stmt.close();
	      }
	      catch(Exception ex)
	      {
	        ex.printStackTrace();
	      }
	      return genDcmId;
	  }
	 
	 public static boolean insertAuthAgent(Connection con ,String authAgentCode ,String authAgentName,String channelId)
	  {
		 boolean check = false;
	     try
	      {
	    	 Statement stat = con.createStatement();
		     String sql = "select * from gen_dcm where dcm_name = '"+authAgentName+"' and dcm_code = '"+authAgentCode+"' and channel_Id = '"+channelId+"'";
		     System.out.println ("The check query isssssssssssss " + sql);
		     ResultSet res = stat.executeQuery(sql);
		     if(res.next())
		     {
		    	 Statement stmt = con.createStatement();      
	        
		    	 Long agentAuthId = Utility.getSequenceNextVal(con , "AACM_AUTH_AGENT_SEQ" );
	        
		    	 //String sqlString = "INSERT INTO AACM_AUTH_AGENT (AACM_AUTH_AGENT_ID ,AUTH_AGENT_CODE,AUTH_AGENT_NAME)"+
		    	 	//				"VALUES("+agentAuthId+",(select dcm_code from gen_dcm where dcm_name = '"+authAgentName+"' and CHANNEL_ID = '"+channelId+"'),'"+authAgentName+"')";
		    	 String sqlString = "INSERT INTO AACM_AUTH_AGENT (AACM_AUTH_AGENT_ID ,AUTH_AGENT_CODE,AUTH_AGENT_NAME)"+
		    	 "VALUES("+agentAuthId+",'"+authAgentCode+"','"+authAgentName+"')";
		    	 
		    	 System.out.println("The insert query issssssssssssss " + sqlString);
		    	 stmt.executeUpdate(sqlString);
		    	 stmt.close();
		     }
		     else
		     {
		    	check = true;
		     }
		     
	      }
	      catch(Exception ex)
	      {
	        ex.printStackTrace();
	      }
	      return check;
	      
	  }
	 
	 public static void deleteAuthAgentFromGenDcm(Connection con , String genDcmId, String channelId )
	  {
	     
	     try
	      {
	        Statement stmt = con.createStatement();      
	        
	        
	        String sqlString = "DELETE FROM GEN_DCM WHERE DCM_ID = '"+genDcmId+"' and CHANNEL_ID = '"+channelId+"'";
	        stmt.executeUpdate(sqlString);
	        stmt.close();
	      }
	      catch(Exception ex)
	      {
	        ex.printStackTrace();
	      }
	      
	  }
	 
	 public static void deleteAuthAgentFromAuthAgent(Connection con ,String authAgentCode, String authAgentName )
	  {
	     
	     try
	      {
	        Statement stmt = con.createStatement();      
	        
	        
	        String sqlString = "DELETE FROM AACM_AUTH_AGENT WHERE AUTH_AGENT_CODE = '"+authAgentCode+"' and AUTH_AGENT_NAME = '"+authAgentName+"'";
	        stmt.executeUpdate(sqlString);
	        stmt.close();
	      }
	      catch(Exception ex)
	      {
	        ex.printStackTrace();
	      }
	      
	  }
	 
	 public static boolean updateAuthAgent(Connection con ,String oldAuthAgentCode,String oldAuthAgentName,String genDcmId,String authAgentName,String authAgentcode,String channelId )
	  {
		 boolean check = false;
	     try
	      {
	    	Statement stat = con.createStatement();
	    	String sql = "select * from gen_dcm where dcm_name = '"+authAgentName+"' and dcm_code = '"+oldAuthAgentCode+"'";
	    	System.out.println ("The check query isssssssssssss " + sql);
	    	ResultSet res = stat.executeQuery(sql);
	    	if(res.next())
	    	{
	    		Statement stmt = con.createStatement();      
//	    		String sqlString = "UPDATE AACM_AUTH_AGENT SET AUTH_AGENT_NAME = '"+authAgentName+"'"+
//	    							" , AUTH_AGENT_CODE = (SELECT DCM_CODE FROM GEN_DCM WHERE DCM_NAME = '"+authAgentName+"'and channel_id = '"+channelId+"') "+
//	    							"WHERE AUTH_AGENT_NAME = '"+oldAuthAgentName+"'";
	    		
	    		String sqlString = "UPDATE AACM_AUTH_AGENT SET AUTH_AGENT_NAME = '"+authAgentName+"', AUTH_AGENT_CODE = '"+authAgentcode+"' "+
	    		                    "WHERE AUTH_AGENT_NAME = '"+oldAuthAgentName+"'";
	    			
	    		System.out.println("The update query issssssss " + sqlString);
	    		stmt.executeUpdate(sqlString);
	    		stmt.close();
	    	}
	    	else
	    	{
	    		check = true;
	    	}
	      }
	      catch(Exception ex)
	      {
	        ex.printStackTrace();
	      }
	      return check;
	  }
	 
	 public static void updateAuthAgentInGenDcm(Connection con , String genDcmId,String authAgentName,String authAgentCode )
	  {
	     
	     try
	      {
	        Statement stmt = con.createStatement();      
	        
	        
	        String sqlString = "UPDATE GEN_DCM SET DCM_NAME = '"+authAgentName+"' ,DCM_CODE = '"+authAgentCode+"' WHERE DCM_ID  = '"+genDcmId+"'";
	        stmt.executeUpdate(sqlString);
	        stmt.close();
	      }
	      catch(Exception ex)
	      {
	        ex.printStackTrace();
	      }
	      
	  }
	 
	 public static AuthAgentModel selectAuthAgent (Connection con,String dcmId,String channelId)
	  {
		 AuthAgentModel authAgentModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from AACM_AUTH_AGENT,GEN_DCM"+
	      				" where  AACM_AUTH_AGENT.AUTH_AGENT_CODE = GEN_DCM.DCM_CODE" +
	      				" and DCM_ID = '"+dcmId+"' and CHANNEL_ID = '"+channelId+"'";
	      System.out.println("The select query issssssss " + strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while (res.next())
	      {
	    	  authAgentModel = new AuthAgentModel(res);
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return authAgentModel;
	  }
	 
	 
	 public static Vector getAMSPortfolio(Connection con)
	  {
	    Vector portoflioData = new Vector();
	    try
	    {
	      Statement stat = con.createStatement();
	      ResultSet res = stat.executeQuery("select Account_name,account_bscs_code from AMS_ACCOUNT@AMSLINK");
	      while (res.next())
	      {
	    	  	
	        AMSPortfolioModel amsPortfolioModel = new AMSPortfolioModel(res);
	        portoflioData.add(amsPortfolioModel);
	      }
	      
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return portoflioData;
	  }
	 
	 
	 public static void insertAMSPortfolio(Connection con,String strUserId,String mappingYear,String mappingMonth)
	 {
		 
		 try
		 {
			 //Vector portoflioData = getAMSPortfolio( con);
			 Statement stmt = con.createStatement();
			 java.util.Date date = new java.util.Date();
	         Calendar cal = Calendar.getInstance();
	         int day = cal.get(Calendar.DATE);
	         int month = cal.get(Calendar.MONTH) + 1;
	         int year = cal.get(Calendar.YEAR);
	         System.out.println("The day issssss " + day);
	         System.out.println("The month isssss " + month);
	         System.out.println("The year isssssss " + year);
	         Long amsImportId = null;
			// for (int i=0 ; i <portoflioData.size() ; i++)
			 //{
				// AMSPortfolioModel amsPortfolioModel = (AMSPortfolioModel)portoflioData.get(i);
				// String accountName = amsPortfolioModel.getAccountName();
				 //String accountBSCSCode = amsPortfolioModel.getAccountBSCSCode();
				 amsImportId = Utility.getSequenceNextVal(con , "AACM_AMS_IMPORT_SEQ" );
				//String strSql = "insert into AACM_AMS_IMPORT (AACM_AMS_IMPORT_ID,AUTHORIZED_AGENT_NAME,BSCS_CODE,TIMESTAMP,USER_ID,MONTH_OF_MAPPING)"+
				 				 //" values ('"+amsImportId+"','"+accountName+"','"+accountBSCSCode+"',SYSDATE ,'"+strUserId+"','"+mappingMonth+"-"+mappingYear+"')";
				 
				 String strSql2 ="insert into AACM_AMS_IMPORT (select AACM_AMS_IMPORT_SEQ.nextval,Account_name,account_bscs_code,'"+mappingMonth+"','0',SYSDATE ,'"+strUserId+"','"+mappingYear+"' from AMS_ACCOUNT@AMSLINK)";
				 System.out.println("The insert query issssssssssss " + strSql2);
				 stmt.executeUpdate(strSql2);
			 //}	
			 //updateAmsDataWithDcmExists(con);
		 }
		 
		 catch(Exception e)
		    {
		      e.printStackTrace();
		    }
	 }
	 
	 public static Long updateAmsDataWithStatusDcmExists()
	 {
		 Long fileId = null;
		 try
		 {
			 Connection con = Utility.getConnection();
			 fileId = Utility.getSequenceNextVal(con, "AACM_AMS_IMPORT_FILE_SEQ");
			 Statement stmt = con.createStatement();
			 String strSql = "UPDATE AACM_AMS_IMPORT SET IMPORT_STATUS = '0'  , FILE_ID = '"+fileId+"' where import_status is null";
			 stmt.executeUpdate(strSql);
			 Statement stmt2 = con.createStatement();
			 String strSql2 = "update AACM_AMS_IMPORT SET IMPORT_STATUS = '1' "+
			 				 "WHERE AUTHORIZED_AGENT_NAME in (SELECT DCM_NAME FROM GEN_DCM " +
			 				 "WHERE AUTHORIZED_AGENT_NAME = DCM_NAME)";
			 stmt2.executeUpdate(strSql2);
		 }
		 catch(Exception e)
		 {
		      e.printStackTrace();
		 }
		 return fileId;
	 }
	 
	 public static Vector selectAuthorizedAgentNotExist(Long fileId)
	 {
		 Vector DCMNotExist = new Vector();
		 try
		 {
			 Connection con = Utility.getConnection();
			 Statement stat = con.createStatement();
			 String strSql = "select distinct(AUTHORIZED_AGENT_NAME) from AACM_AMS_IMPORT where import_status = '0' and FILE_ID = '"+fileId+"'";
			 ResultSet res = stat.executeQuery(strSql);
			 while (res.next())
			 {
				 String authAgentName = res.getString("AUTHORIZED_AGENT_NAME");
				 DCMNotExist.add(authAgentName);
			 }
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		 return DCMNotExist;
	 }
	 
	  public static Vector getTmpAuthAgent()
	  {
	      Vector authAgentVec = new Vector();
	      try
	      {
	       Connection con = Utility.getConnection();
	       Statement stat = con.createStatement();
	       ResultSet res= stat.executeQuery("select * from TMP_AUTHAGENT_DATA ");
	       while(res.next())
	       {
	    	   authAgentVec.add(new AuthAgentImportModel(res));
	       }
	       stat.close();
	       Utility.closeConnection(con);
	      }
	      catch(Exception e)
	      {
	      e.printStackTrace();
	      }
	        return authAgentVec; 
	  }
	  
	  public static boolean insertAuthAgentMigration(Connection con,Statement stat,String bscsCode , String dialNumber)
	  {
		  boolean flag = false;
		  Long authAgentMigrationId =  null;
		  try
		  {
			  //Connection con = Utility.getConnection();
		      //Statement stat = con.createStatement();
		      authAgentMigrationId = Utility.getSequenceNextVal(con , "aacm_authagent_migration_seq" );
			  
		      String strSql = "insert into AACM_AUTHAGENT_MIGRATION (AACM_AUTHAGENT_MIGRATION_ID,BSCS_CODE,DIAL_NUMBER )"+
		      				  "VALUES ('"+authAgentMigrationId+"','"+bscsCode+"','"+dialNumber+"')";
		      //System.out.println("The insert query issssssss " + strSql);
		      stat.addBatch(strSql);
		      //stat.executeUpdate(strSql);
		      flag = true;
		  }
		  catch(Exception e) 
	      {
			  e.printStackTrace();
	      //System.out.println("This dial number is already exist");
	      }
		  return flag;
	  }
	  
	  public static Long updateAuthAgentFileID()
		 {
			 Long fileId = null;
			 try
			 {
				 Connection con = Utility.getConnection();
				 fileId = Utility.getSequenceNextVal(con , "AACM_AUTHAGENT_FILE_SEQ" );
				 Statement stmt = con.createStatement();
				 String strSql = "UPDATE AACM_AUTHAGENT_MIGRATION SET  FILE_ID = '"+fileId+"' where FILE_ID is null";
				 stmt.executeUpdate(strSql);
				 Statement stmt2 = con.createStatement();
				 String strSql2 = "select * from AACM_AUTHAGENT_MIGRATION where file_id = '"+fileId+"'";
				 ResultSet res = stmt2.executeQuery(strSql2);
				 if(res.next())
				 {
					 fileId = fileId;
				 }
				 else
				 {
					 fileId = null;
				 }
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
			 }
			 return fileId;
		 }
	  
	  public static void deleteTmpAuthAgent()
	  {
		  try
		  {
			  Connection con = Utility.getConnection();
		      Statement stat = con.createStatement();
		      String strSql = "delete  from TMP_AUTHAGENT_DATA";
		      stat.executeUpdate(strSql);
		  }
		  catch(Exception e)
	      {
	      e.printStackTrace();
	      }
	  }
	  
	  public static void updateAuthAgentMigrationSimMigrationDate(Long fileId)
	  {
		  try
		  {
			  Connection con = Utility.getConnection();
		      Statement stat = con.createStatement();		      
//		      String strSql = "update AACM_AUTHAGENT_MIGRATION set (SIM_SERIAL,migration_date) = (SELECT SIM_SERIAL ,migration_date "+
//		      				  "FROM AACM_MIGRATION_DATA,AACM_AUTHAGENT_MIGRATION_FILE  "+
//		      				  "WHERE AACM_AUTHAGENT_MIGRATION.DIAL_NUMBER = AACM_MIGRATION_DATA .DIAL_NUMBER "+
//		      				  "and AACM_AUTHAGENT_MIGRATION_FILE.status = 'active' "+
//		      				  "and AACM_AUTHAGENT_MIGRATION_FILE.file_id = AACM_AUTHAGENT_MIGRATION.file_id "+
//		      				  "and AACM_AUTHAGENT_MIGRATION_FILE.month = floor(to_char(aacm_migration_data.migration_date,'MM')) "+
//		      				  "and AACM_AUTHAGENT_MIGRATION_FILE.year = to_char(aacm_migration_data.migration_date,'yyyy')) "+
//		      				  "where AACM_AUTHAGENT_MIGRATION.file_id = '"+fileId+"' ";
//		      
		      String strSql = "UPDATE aacm_authagent_migration SET migration_date = (SELECT distinct(migration_date) "+
		      				  " FROM aacm_migration_data, aacm_authagent_migration_file "+
		      				  " WHERE aacm_authagent_migration.dial_number = aacm_migration_data.dial_number "+
		      				  "AND aacm_authagent_migration_file.status = 'active' "+
		      				  "AND aacm_authagent_migration_file.file_id =aacm_authagent_migration.file_id "+
		      				  "AND aacm_authagent_migration_file.MONTH =  FLOOR (TO_CHAR (aacm_migration_data.migration_date, 'MM')) "+
		      				  "AND aacm_authagent_migration_file.YEAR = TO_CHAR (aacm_migration_data.migration_date, 'yyyy')),"+
		      				  "SIM_SERIAL = (SELECT distinct(SIM_SERIAL)FROM aacm_migration_data, aacm_authagent_migration_file "+
		      				  " WHERE aacm_authagent_migration.dial_number = aacm_migration_data.dial_number "+
		      				  "AND aacm_authagent_migration_file.status = 'active' "+
		      				  "AND aacm_authagent_migration_file.file_id =aacm_authagent_migration.file_id "+
		      				  "AND aacm_authagent_migration_file.MONTH =FLOOR (TO_CHAR (aacm_migration_data.migration_date, 'MM')) "+
		      				  "AND aacm_authagent_migration_file.YEAR = TO_CHAR (aacm_migration_data.migration_date, 'yyyy'))"+
		      				  "WHERE aacm_authagent_migration.file_id = '"+fileId+"'";
		      
		      System.out.println("The first update query isssssssssss" + strSql);
		      stat.executeUpdate(strSql);
		      stat.close();
		      con.close();
		  }
		  catch(Exception e)
	      {
	      e.printStackTrace();
	      }
	  }
	  
	  public static void updateMigrationDataChecked()
	  {
		  try
		  {
			  Connection con = Utility.getConnection();
		      Statement stat = con.createStatement();
		      String strSql = "UPDATE AACM_MIGRATION_DATA SET CHECKED = '1' WHERE DIAL_NUMBER IN (SELECT DIAL_NUMBER FROM AACM_AUTHAGENT_MIGRATION WHERE SIM_SERIAL IS NOT NULL"+
		      				  " AND MIGRATION_DATE IS NOT NULL)";
		      stat.executeUpdate(strSql);
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
	  }
	  
	  public static void updateAuthAgentMigrationBSCSCode(Long fileId)
	  {
		  try
		  {
			  Connection con = Utility.getConnection();
		      Statement stat = con.createStatement();
		      //String strSql = "UPDATE AACM_AUTHAGENT_MIGRATION SET DCM_CODE = (SELECT distinct(AUTHORIZED_AGENT_NAME) from AACM_AMS_IMPORT ,AACM_AMS_IMPORT_FILE,AACM_AUTHAGENT_MIGRATION_FILE,AACM_AUTHAGENT_MIGRATION  "+
		      	//			  " WHERE AACM_AUTHAGENT_MIGRATION.BSCS_CODE like concat(AACM_AMS_IMPORT.BSCS_CODE,'%') and AACM_AUTHAGENT_MIGRATION.file_id = '"+fileId+"' )";
		      				 // " and AACM_AMS_IMPORT.file_id = AACM_AMS_IMPORT_FILE.file_id"+
		      				 // " and AACM_AUTHAGENT_MIGRATION_FILE.status = 'active'"+
		      				//" and AACM_AMS_IMPORT_FILE.status = 'active'"+
		      				 // " and AACM_AUTHAGENT_MIGRATION_file.month = AACM_AMS_IMPORT_FILE.month_of_mapping"+
		      				  //" and AACM_AUTHAGENT_MIGRATION_file.year = AACM_AMS_IMPORT_FILE.year_of_mapping)";
		      
		      /*String strSql = "UPDATE AACM_AUTHAGENT_MIGRATION SET DCM_CODE = "+
		      				  "(select AUTHORIZED_AGENT_NAME from AACM_AMS_IMPORT ,AACM_AMS_IMPORT_FILE,AACM_AUTHAGENT_MIGRATION_FILE "+
		      				  "WHERE AACM_AUTHAGENT_MIGRATION.BSCS_CODE like concat (AACM_AMS_IMPORT.BSCS_CODE,'%') "+
		      				  "and AACM_AMS_IMPORT.file_id = AACM_AMS_IMPORT_FILE.file_id "+
		      				  "and AACM_AUTHAGENT_MIGRATION_FILE.status = 'active' "+
		      				  "and AACM_AMS_IMPORT_FILE.status = 'active' "+
		      				  "and AACM_AUTHAGENT_MIGRATION_file.month = AACM_AMS_IMPORT_FILE.month_of_mapping "+
		      				  "and AACM_AUTHAGENT_MIGRATION_file.year =  AACM_AMS_IMPORT_FILE.year_of_mapping)"+
		      				  "where "+
		      				  "aacm_authagent_migration.file_id = '"+fileId+"'";*/
		      
		      String strSql = "update aacm_authagent_migration set  dcm_code =( "+
		      				  "SELECT authorized_agent_name "+
		      				  "FROM aacm_ams_import,aacm_ams_import_file,aacm_authagent_migration_file "+
		      				  "where  aacm_authagent_migration_file.status = 'active' "+
		      				  "and aacm_ams_import_file.status = 'active' "+
		      				  "and aacm_ams_import.file_id = aacm_ams_import_file.file_id "+
		      				  "AND aacm_authagent_migration_file.MONTH = aacm_ams_import_file.month_of_mapping "+
		      				  "AND aacm_authagent_migration_file.YEAR = aacm_ams_import_file.year_of_mapping "+
		      				  "and AMS_CHECK_BSCS_CODE(aacm_authagent_migration.bscs_code) = AACM_AMS_IMPORT.BSCS_CODE)"+
		      				  "WHERE aacm_authagent_migration.file_id = '"+fileId+"'";
		      System.out.println("The second update query isssssssssss" + strSql);
		      stat.executeUpdate(strSql);
		      stat.close();
		      con.close();
		  }
		  catch(Exception e)
	      {
	      e.printStackTrace();
	      }
	  }
	  
	  public static void updatePortfolio()
	  {
		  try
		  {
			  Connection con = Utility.getConnection();
		      Statement stat = con.createStatement();
		      String strSql = "INSERT INTO AACM_PORTFOLIO (SELECT AACM_PORTFOLIO_SEQ.nextval,dcm_code,bscs_code,"+
		      				  "month_of_mapping,year_of_mapping from aacm_ams_import,gen_dcm "+
		      				  "where gen_dcm.dcm_name = aacm_ams_import.authorized_agent_name and dcm_code not in "+
		      				  "(select dcm_code from AACM_PORTFOLIO))";
		      System.out.println("The update query issssss " + strSql);
		      stat.executeUpdate(strSql);
		  }
		  catch(Exception e)
	      {
	      e.printStackTrace();
	      }
		  
	  }
	  
	  public static void insertAuthAgentMigrationFile(Long fileId,String month,String year,String userId)
	  {
		  try
		  {
			  Connection con = Utility.getConnection();
		      Statement stat = con.createStatement();
		      //fileId = Utility.getSequenceNextVal(con , "AACM_AUTHAGENT_FILE_SEQ" );
		      String strSql = "INSERT INTO AACM_AUTHAGENT_MIGRATION_FILE (FILE_ID,MONTH,YEAR,STATUS,USER_ID,TIMESTAMP)values ('"+fileId+"','"+month+"','"+year+"','active','"+userId+"',SYSDATE) ";
		      System.out.println("The file insert Query isssssssssssss " + strSql);
		      stat.executeUpdate(strSql);
		  }
		  catch(Exception e)
	      {
	      e.printStackTrace();
	      }
		  //return fileId;
	  }
	  
	  public static Vector getTmpAmsData()
	  {
	      Vector amsData = new Vector();
	      try
	      {
	       Connection con = Utility.getConnection();
	       Statement stat = con.createStatement();
	       ResultSet res= stat.executeQuery("select * from TMP_AMS_DATA ");
	       while(res.next())
	       {
	    	   amsData.add(new AMSImportModel(res));
	       }
	       stat.close();
	       Utility.closeConnection(con);
	      }
	      catch(Exception e)
	      {
	      e.printStackTrace();
	      }
	        return amsData; 
	  }
	  
	  public static void insertAMSData(String authAgentName,String bscsCode,String strUserId,String mappingYear,String mappingMonth)
		 {
			 
			 try
			 {
				 Connection con = Utility.getConnection();
				 Statement stmt = con.createStatement();
		         Long amsImportId = null;
				
					 amsImportId = Utility.getSequenceNextVal(con , "AACM_AMS_IMPORT_SEQ" );
					
					 
					 String strSql2 ="insert into AACM_AMS_IMPORT (AACM_AMS_IMPORT_ID,AUTHORIZED_AGENT_NAME,BSCS_CODE,MONTH_OF_MAPPING,IMPORT_STATUS,USER_ID,YEAR_OF_MAPPING)VALUES "+
					 				 "('"+amsImportId+"','"+authAgentName+"','"+bscsCode+"','"+mappingMonth+"',0,'"+strUserId+"','"+mappingYear+"')";
					 System.out.println("The insert query issssssssssss " + strSql2);
					 stmt.executeUpdate(strSql2);
				 
			 }
			 
			 catch(Exception e)
			    {
			      e.printStackTrace();
			    }
		 }
	  
	  public static void insertAmsImportFile(Long fileId,String userId,String month,String year)
	  {
		  try
		  {
			  Connection con = Utility.getConnection();
			  Statement stat = con.createStatement();
			  String strSql = "insert into AACM_AMS_IMPORT_FILE (FILE_ID,MONTH_OF_MAPPING,YEAR_OF_MAPPING,TIMESTAMP,USER_ID,STATUS) VALUES "+
			  				  "('"+fileId+"','"+month+"','"+year+"',SYSDATE,'"+userId+"','active')";
			  System.out.println("The insert query isssssss " + strSql);
			  stat.executeUpdate(strSql);
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
	  }
	  
	  public static boolean amsUploadCheck(String year,String month)
	  {
		  boolean check = false;
		  try
		  {
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "SELECT * FROM AACM_AMS_IMPORT_FILE WHERE month_of_mapping = '"+month+"' "+
							"and year_of_mapping = '"+year+"' and status = 'active'" ;
			ResultSet res = stat.executeQuery(strSql);
			if(res.next())
			{
				check = true;
			}
		  }
		  catch(Exception e)
		  {
			e.printStackTrace();  
		  }
		  return check;
	  }
	  
	  public static boolean amsAuthAgentCheck(String year,String month)
	  {
		  boolean check = false;
		  try
		  {
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "SELECT * FROM AACM_AUTHAGENT_MIGRATION_FILE WHERE month = '"+month+"' "+
							"and year = '"+year+"' and status = 'active'" ;
			ResultSet res = stat.executeQuery(strSql);
			if(res.next())
			{
				check = true;
			}
		  }
		  catch(Exception e)
		  {
			e.printStackTrace();  
		  }
		  return check;
	  }
	  
	  public static Vector getAMSImportFile ()
	  {
		  Vector amsFile = new Vector();
		  try
		  {
			  Connection con = Utility.getConnection();
			  Statement stat = con.createStatement();
			  //String strSql = "select file_id,month_of_mapping,year_of_mapping, to_char(timestamp,'dd/mm/yyyy')as timestamp,user_id,status from aacm_ams_import_file where status = 'active'";
			  String strSql = "SELECT file_id, month_of_mapping, year_of_mapping,TO_CHAR (TIMESTAMP, 'dd/mm/yyyy') AS TIMESTAMP,"+
			  				  "user_id,person_full_name, status,(select count(*) from AACM_AMS_IMPORT "+
			  				  "where AACM_AMS_IMPORT.file_id =aacm_ams_import_file.file_id )as count "+
			  				  "FROM aacm_ams_import_file,gen_person where status = 'active' and aacm_ams_import_file.user_id = gen_person.person_id";
			  ResultSet res = stat.executeQuery(strSql);
			  while (res.next())
			  {
				  AMSImportFileModel amsImportFileModel = new AMSImportFileModel(res);
				  amsFile.add(amsImportFileModel);
			  }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  return amsFile;
	  }
	  
	  public static void deleteAmsDataFile(String fileId)
	  {
		  try
		  {
			  Connection con = Utility.getConnection();
			  Statement stat = con.createStatement();
			  String strSql = "update AACM_AMS_IMPORT_FILE set status = 'deactive' where FILE_ID = '"+fileId+"' ";
			  stat.executeUpdate(strSql);
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
	  }
	  
	  public static Vector getAuthAgentFile ()
	  {
		  Vector authAgentFile = new Vector();
		  try
		  {
			  Connection con = Utility.getConnection();
			  Statement stat = con.createStatement();
			  //String strSql = "select * from AACM_AUTHAGENT_MIGRATION_FILE where status = 'active'";
			  String strSql = "SELECT file_id, month, year, TO_CHAR (TIMESTAMP, 'dd/mm/yyyy') AS TIMESTAMP,user_id,person_full_name,status,"+
			  				  "(select count(*) from AACM_AUTHAGENT_MIGRATION where AACM_AUTHAGENT_MIGRATION.file_id =AACM_AUTHAGENT_MIGRATION_FILE.file_id )as count "+
			  				  "from AACM_AUTHAGENT_MIGRATION_FILE ,gen_person where status = 'active' "+
			  				  "and AACM_AUTHAGENT_MIGRATION_FILE.user_id = gen_person.PERSON_ID";
			  ResultSet res = stat.executeQuery(strSql);
			  while (res.next())
			  {
				  AuthAgentFileModel authAgentFileModel = new AuthAgentFileModel(res);
				  authAgentFile.add(authAgentFileModel);
			  }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  return authAgentFile;
	  }
	  
	  public static void deleteAuthAgentFile(String fileId)
	  {
		  try
		  {
			  Connection con = Utility.getConnection();
			  Statement stat = con.createStatement();
			  String strSql = "update AACM_AUTHAGENT_MIGRATION_FILE set status = 'deactive' where FILE_ID = '"+fileId+"' ";
			  stat.executeUpdate(strSql);
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
	  }
	  
	  public static Vector getAmsDataWithFile(String fileId)
	  {
		  Vector amsDataVec = new Vector();
		  try
		  {
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "select row_num,authorized_agent_name,bscs_code from aacm_ams_import where file_id = '"+fileId+"'";
			ResultSet res = stat.executeQuery(strSql);
			while(res.next())
			{
				amsDataVec.add(new AMSImportModel(res));
			}
			stat.close();
			con.close();
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  return amsDataVec;
	  }
	  
	  public static Vector getAuthAgentDataWithFile(String fileId)
	  {
		  Vector authAgentDataVec = new Vector();
		  try
		  {
			Connection con = Utility.getConnection();
			Statement stat = con.createStatement();
			String strSql = "select bscs_code,dial_number from AACM_AUTHAGENT_MIGRATION where file_id = '"+fileId+"'";
			ResultSet res = stat.executeQuery(strSql);
			while(res.next())
			{
				authAgentDataVec.add(new AuthAgentImportDataModel(res));
			}
			stat.close();
			con.close();
		  }
		  catch(Exception e)
		  { 
			  e.printStackTrace();
		  }
		  return authAgentDataVec;
	  }
	  
	  public static void insertMailRobot()
	  {
		  String mailFrom = "salma2310@gmail.com";
		  String mailTo = "medhat.amin@hotmail.com";
		  String mailSub = "Test Mail Robot";
		  String mailBody = "";
		  String mailFile = "";
		  try
		  {
			  Connection con = Utility.getConnection();
			  Statement stat = con.createStatement();
			  String strSql = "INSERT INTO MAIL_ROBOT_MAIL_DATA (ID  ,  SEND_DATE , MAILFROM , MAILTO , MAILSUB , MAILBODY , MAILATTACH  ) "+
			  					"VALUES ( ROBOT_DATA_SEQ.NEXTVAL  , SYSDATE ,  '" +mailFrom+ "','" + mailTo + "' , '" + mailSub +"' , '" + mailBody +"' , '" + mailFile +"' )";
			  System.out.println("The mail robot insert query issssssssssssssssss " + strSql);
			  stat.executeUpdate(strSql);
			  stat.close();
			  con.close();
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
	  }
	  

}

package com.mobinil.sds.core.system.ifReportDelivery.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;


import com.mobinil.sds.core.system.ifReportDelivery.model.*;

import oracle.jdbc.OracleTypes;
 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.*;
import java.sql.*;
import java.util.Date;
import com.mobinil.sds.core.utilities.*;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
public class IFReportDeliveyDao {
	private final static boolean  debugFlag = false;
	private static int currIndex;
	private static void debug(String msg)
	{
		if(debugFlag) {
			System.out.println(msg);
        }
	}
        public static void truncateTempTable(Connection con){
        DBUtil.executeSQL("truncate table IF_INFO_FORT_DATA_TEMP", con);
        }
	public static ResultSet getMobinilToInfoFortData(Connection con,int batchId){
		/**/
		
		//Vector batchs = new Vector();
		ResultSet res=null;;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select cr_batch.batch_id,gen_dcm.dcm_name,cr_batch.BATCH_TYPE_ID,cr_batch.BATCH_LAST_STATUS_TYPE_ID,CR_BATCH_STATUS_TYPE.BATCH_STATUS_TYPE_NAME," +
	      		" cr_sheet.SHEET_SERIAL_ID,cr_sheet.sheet_id,cr_sheet.SHEET_POS_CODE,cr_sheet.SHEET_SUPER_DEALER_CODE,cr_sheet.SHEET_DISTRIBUTER_CODE," +
	      		" CR_SHEET_STATUS_TYPE.SHEET_STATUS_TYPE_NAME," +
	      		" cr_sheet.SHEET_LAST_STATUS_ID,cr_sheet.SHEET_ID,count(cr_contract.CONTRACT_MAIN_SIM_NO),cr_contract.CONTRACT_LAST_STATUS_ID" +
	      		" from" +
	      		" cr_batch,cr_sheet,CR_BATCH_STATUS_TYPE,CR_SHEET_STATUS_TYPE,cr_contract,gen_dcm,CR_SHEET_STATUS,CR_CONTRACT_STATUS" +
	      		" where" +
	      		" cr_batch.batch_id ="+"'"+batchId+"'" +
	      		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = '7'" +
	      		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = CR_BATCH_STATUS_TYPE.BATCH_STATUS_TYPE_ID" +
	      		" and cr_batch.BATCH_ID = cr_sheet.BATCH_ID and cr_sheet.SHEET_LAST_STATUS_ID = CR_SHEET_STATUS.SHEET_STATUS_ID and CR_SHEET_STATUS.SHEET_STATUS_TYPE_ID = '5'" +
	      		" and CR_SHEET_STATUS.SHEET_STATUS_TYPE_ID = CR_SHEET_STATUS_TYPE.SHEET_STATUS_TYPE_ID" +
	      		" and cr_sheet.SHEET_ID = cr_contract.SHEET_ID and CR_CONTRACT_STATUS.CONTRACT_STATUS_TYPE_ID = '5'" +
	      		" and cr_contract.CONTRACT_LAST_STATUS_ID = CR_CONTRACT_STATUS.CONTRACT_STATUS_ID" +
	      		" group by" +
	      		" cr_batch.batch_id,gen_dcm.dcm_name,cr_batch.BATCH_TYPE_ID,cr_batch.BATCH_LAST_STATUS_TYPE_ID,CR_BATCH_STATUS_TYPE.BATCH_STATUS_TYPE_NAME," +
	      		" cr_sheet.SHEET_SERIAL_ID,cr_sheet.SHEET_POS_CODE,cr_sheet.SHEET_SUPER_DEALER_CODE,cr_sheet.SHEET_DISTRIBUTER_CODE," +
	      		" CR_SHEET_STATUS_TYPE.SHEET_STATUS_TYPE_NAME," +
	      		" cr_sheet.SHEET_LAST_STATUS_ID,cr_sheet.SHEET_ID,cr_contract.CONTRACT_LAST_STATUS_ID";

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      res = stat.executeQuery(strSql);
	      
	      //stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return res;

	}
	
	public static ResultSet getMobinilToInfoFortSheetData(Connection con,int batchId){
		/**/
		
		//Vector batchs = new Vector();
		ResultSet res=null;;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select cr_sheet.SHEET_ID,cr_sheet.SHEET_SERIAL_ID,cr_sheet.SHEET_POS_CODE,cr_sheet.SHEET_SUPER_DEALER_CODE" +
	      		"	,CR_SHEET_STATUS_TYPE.SHEET_STATUS_TYPE_NAME" +
	      		"	from cr_sheet,CR_SHEET_STATUS_TYPE,CR_SHEET_STATUS" +
	      		"	where" +
	      		"	cr_sheet.SHEET_LAST_STATUS_ID = CR_SHEET_STATUS.SHEET_STATUS_ID and CR_SHEET_STATUS.SHEET_STATUS_TYPE_ID = '5'" +
	      		"	and CR_SHEET_STATUS.SHEET_STATUS_TYPE_ID = CR_SHEET_STATUS_TYPE.SHEET_STATUS_TYPE_ID" +
	      		"	and cr_sheet.BATCH_ID = "+batchId +" order by SHEET_SERIAL_ID ";

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      res = stat.executeQuery(strSql);
	      
	      //stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return res;

	}
	
	public static Vector searchForExport(Connection con,String batchId,String batchDate,String dcmName,String batchTypeId){
		Vector batchs = new Vector();
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "SELECT BATCH_ID,to_char(BATCH_DATE,'dd/mm/yyyy') BATCH_DATE,BATCH_TYPE_NAME,dcm_code,dcm_name from cr_batch left join gen_dcm on cr_batch.dcm_id= gen_dcm.dcm_id left join CR_BATCH_TYPE on (CR_BATCH_TYPE.BATCH_TYPE_ID=CR_BATCH.BATCH_TYPE_ID) where BATCH_LAST_STATUS_TYPE_ID=7 and ";
	      if(batchId!=null && !batchId.equals(""))
	    	  strSql+=" cr_batch.batch_id = "+batchId;
	      
	      if((batchDate !=null && !batchDate.equals("Batch_Date")) && (batchId!=null&& !batchId.equals("")))
	    	  strSql+=" and cr_batch.batch_date = "+"to_date('"+batchDate+"','dd-mm-yy')";
	      else if((batchDate !=null && !batchDate.equals("Batch_Date")))
	    	  strSql+=" cr_batch.batch_date = "+"to_date('"+batchDate+"','dd-mm-yy')";
	      
	      if((dcmName!=null && !dcmName.equals(""))  && ((batchDate!=null && !batchDate.equals("Batch_Date")) || (batchId!=null && !batchId.equals(""))))
	    	  strSql+=" and gen_dcm.dcm_name = "+dcmName;
	      else if((dcmName!=null && !dcmName.equals("")))
	    	  strSql+=" gen_dcm.dcm_name = "+dcmName;
	      
	      
	      if((batchTypeId!=null && !batchTypeId.equals("")) &&  ((dcmName!=null && !dcmName.equals("")) || (batchDate!=null && !batchDate.equals("Batch_Date")) || (batchId!=null && !batchId.equals(""))))
	    	  strSql+=" and cr_batch.BATCH_TYPE_ID = "+batchTypeId;
	      else if((batchTypeId!=null && !batchTypeId.equals("")))
	    	  strSql+=" cr_batch.BATCH_TYPE_ID = "+batchTypeId;
	      
	      strSql+=" order by BATCH_ID";

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
                System.out.println(strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while (res.next())
	      {
	        BatchModel bm=new BatchModel();
	        bm.setBatchId(res.getInt("BATCH_ID"));
	        bm.setBatchDate(res.getString("BATCH_DATE"));
	        bm.setBatchType(res.getString("BATCH_TYPE_NAME"));
	        bm.setDcmCode(res.getString("dcm_code"));
	        bm.setDcmName(res.getString("dcm_name"));
	        batchs.add(bm);
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return batchs;
	}
	
	public static Vector getBatchTypes(Connection con)
	{
		Vector batchTypes = new Vector();
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "SELECT BATCH_TYPE_ID,BATCH_TYPE_NAME from cr_batch_type";
	      	      

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while (res.next())
	      {
	        BatchTypeModel bType=new BatchTypeModel();
	        bType.setBatchTypeId(res.getInt("BATCH_TYPE_ID"));
	        bType.setBatchTypeName(res.getString("BATCH_TYPE_NAME"));
	        batchTypes.add(bType);
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return batchTypes;
	}
	public static Vector getJobs(Connection con,String jobDate,String reportDate,String jobStatus,boolean flag)
	{
		Vector jobs = new Vector();
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "SELECT to_char(IF_JOB_DETAILS.JOB_DATE,'dd/mm/yyyy') job_date,IF_JOB_DETAILS.JOB_ID,IF_JOB_DETAILS.JOB_STATUS_ID,IF_JOB_DETAILS.PROCESSING_TIME,to_char(IF_JOB_DETAILS.REPORT_CREATION_DATE,'dd/mm/yyyy') report_date,IF_JOB_DETAILS.USER_ID ,  IF_JOB_STATUS.JOB_STATUS_NAME from IF_JOB_DETAILS join IF_JOB_STATUS on IF_JOB_STATUS.JOB_STATUS_ID=if_job_details.JOB_STATUS_ID " 
	      		 ;
	      
	      if(jobStatus!=null && !jobStatus.equals(""))
	    	  strSql+="  where IF_JOB_STATUS.JOB_STATUS_ID = "+jobStatus;
	      
	      if((jobDate!=null && !jobDate.equals("Job_Creation_Date"  )&& !jobDate.equals(""))&& (jobStatus!=null && !jobStatus.equals("")))
	    	  strSql+="and if_job_details.JOB_DATE  >= to_date( ' "+jobDate+"','mm/dd/yyyy') ";
	      else if(jobDate!=null && !jobDate.equals("Job_Creation_Date") && !jobDate.equals(""))
	    	  strSql+="  where if_job_details.JOB_DATE  >= to_date( ' "+jobDate+"','mm/dd/yyyy') ";
	      
	      if((jobDate!=null && !jobDate.equals("Job_Creation_Date") && !jobDate.equals(""))&&((reportDate!=null && !reportDate.equals("Report_Creation_Date") && !reportDate.equals("")) || (jobStatus!=null && !jobStatus.equals(""))))
	    	  strSql+=" and if_job_details.REPORT_CREATION_DATE  >= to_date( ' "+reportDate+"','mm/dd/yyyy') ";
	      else if (reportDate!=null && !reportDate.equals("Report_Creation_Date") && !reportDate.equals(""))
	    	  strSql+="  where if_job_details.REPORT_CREATION_DATE  >= to_date( ' "+reportDate+"','mm/dd/yyyy') ";
	      
	      
	      strSql+="order by job_id";

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);

                System.out.println(strSql);
	      ResultSet rs = stat.executeQuery(strSql);
	      
	      int count=0;
	      while (rs.next())
	      {
	    	  IFReportJobModel jm=new IFReportJobModel();
	    	  jm.setJobId(rs.getInt("JOB_ID"));
	    	  debug("jobId: "+jm.getJobId());
	    	  jm.setJobDate(rs.getString("JOB_DATE"));
	    	  jm.setJobStatusId(rs.getInt("JOB_STATUS_ID"));
	    	  jm.setUserId(rs.getInt("USER_ID"));
	    	  jm.setReportCreationDate(rs.getString("REPORT_DATE"));
	    	  jm.setProcessingTime(rs.getInt("PROCESSING_TIME"));
	    	  jm.setJobStatus(rs.getString("JOB_STATUS_NAME"));
	    	  
	    	  
	    	  if(flag){
	    		  
	    	   	  count=getNumberOfRecords(con,jm.getJobId(), 3);
	    	 	 
		    	  jm.setNumberOfNotFound(count);
		    	  
		    	  
		    	  count=getNumberOfRecords(con,jm.getJobId(), 5);
		 
		       	 jm.setNumberOfDistErrors(count);

	  	  
		    	  count=getNumberOfRecords(con,jm.getJobId(), 6);
		 
	         	  jm.setNumberOfInfoFortErros(count);
	         	  
	         	  
	         	  
	         	  count=getNumberOfDublicateRecords(con, jm.getJobId());
	         	  jm.setNumberOfDuplicates(count);
	         	  
	         	  count=getNumberOfDistinctRecords(con, jm.getJobId());
	         	  jm.setNumberOfDistincts(count);
	         	  
	         	  count=getNumberOfAutoMatched(con, jm.getJobId());
	         	  jm.setNumberofAutoMatched(count);

	    		  
	    	  }
	    	  
	    	  
	    	  
	    	  
	          	  

	    	  jobs.add(jm);
	        
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return jobs;
	}
	
	public static int getJobStatus(Connection con,String jobId){
		int statusId=0;
		try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select JOB_STATUS_ID from IF_JOB_DETAILS where job_id = " +
	      		jobId ;
	      	      

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      ResultSet rs = stat.executeQuery(strSql);
	      while (rs.next())
	      {
	    	  statusId=rs.getInt("JOB_STATUS_ID");
	        
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return statusId;
	}
	public static int getNumberOfRecords(Connection con,int jobId,int recordTypeId){
		int count=0;
		try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select count(*) not_found  from IF_INFO_FORT_DATA where data_record_type_id =" +
	      		recordTypeId +
	      		" and job_id = " +
	      		jobId ;
	      	      

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      ResultSet rs = stat.executeQuery(strSql);
	      while (rs.next())
	      {
	    	  count=rs.getInt(1);
	        
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return count;
	}
	
	public static int getNumberOfAutoMatched(Connection con,int jobId){
		int count=0;
		try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select count(*) from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID=2 and job_id = " +
	      		jobId ;
	      	      

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      ResultSet rs = stat.executeQuery(strSql);
	      while (rs.next())
	      {
	    	  count=rs.getInt(1);
	        
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return count;
	}
	public static int getNumberOfDublicateRecords(Connection con,int jobId){
		int count=0;
		try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select count(*) numberofoccurrence from(select SIM_SERIAL,count(SIM_SERIAL)  numberofoccurrence from IF_INFO_FORT_DATA where job_id = " +
	      		jobId +
	      		" group by sim_serial having (count(SIM_SERIAL)>1))" 
	      		 ;
	      	      

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      ResultSet rs = stat.executeQuery(strSql);
	      while (rs.next())
	      {
	    	  count=rs.getInt("numberofoccurrence");
	        
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return count;
	}
	public static ResultSet getNumberOfDublicateSerials(Connection con,String jobId){
		 ResultSet rs =null;
		try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select SIM_SERIAL,count(SIM_SERIAL)  numberofoccurrence from IF_INFO_FORT_DATA where job_id = " +
	      		jobId +
	      		"  group by sim_serial having (count(SIM_SERIAL)>1)" 
	      		 ;
	      	      

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      rs = stat.executeQuery(strSql);
	    
	      
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return rs;
	}
	public static int getNumberOfDistinctRecords(Connection con,int jobId){
		int count=0;
		try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select count(distinct(sim_serial)) numberofoccurrence from IF_INFO_FORT_DATA where job_id = " +
	      		jobId 
	      		 ;
	      	      

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      ResultSet rs = stat.executeQuery(strSql);
	      while (rs.next())
	      {
	    	  count=rs.getInt("numberofoccurrence");
	        
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return count;
	}
	public static Vector getJobstatuses(Connection con)
	{
		Vector jobStatuses = new Vector();
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "SELECT * from IF_JOB_STATUS  ";
	      	      

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      ResultSet rs = stat.executeQuery(strSql);
	      while (rs.next())
	      {
	    	  JobStatusModel js=new JobStatusModel();
	    	  js.setJobStatusId(rs.getInt("JOB_STATUS_ID"));
	    	  js.setJobStatusName(rs.getString("JOB_STATUS_NAME"));
	    	  
	    	  
	    	  jobStatuses.add(js);
	        
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return jobStatuses;
	}
	
	public static void completeInfofortDataTable(int jobId){
		CallableStatement stmt=null;
		Connection con=null;
		try {
			con=Utility.getConnection();
			String procedureName = "completeInfofortDataTable";
			String query = null;
			query = "{call " + procedureName + "(?)}";
			stmt = con.prepareCall(query);
			
			stmt.setInt(1, jobId);
			
			stmt.execute();
			//Utility.closeConnection(con);

		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}catch(Exception ex){
		ex.printStackTrace();	
		}finally
		{
			debug("data completion done");
		
			Utility.closeCallbaleStatement(stmt);
			
			
		}
	}
	public static String exportMobinilToInfoReport(String template, String fileName,int batchId){
		String dcmName="";
		String date="";
    	try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("sheet1");
            Cell cell = null;
            Row row =  null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password            
                     
            Statement stat = con.createStatement();
           
            
            
            
            /* for the first line in the excel sheet contains batchid,type,creation date and status
             * select cr_batch.batch_id,gen_dcm.dcm_name,cr_batch.BATCH_DATE,cr_batch_type.BATCH_TYPE_NAME , CR_BATCH_STATUS_TYPE.BATCH_STATUS_TYPE_NAME" +
            		"from cr_batch,CR_BATCH_STATUS_TYPE,gen_dcm,cr_batch_type" +
            		"where cr_batch.batch_id = "+ batchId +
            		"and cr_batch.BATCH_LAST_STATUS_TYPE_ID = '7'" +
            		"and cr_batch.BATCH_LAST_STATUS_TYPE_ID = CR_BATCH_STATUS_TYPE.BATCH_STATUS_TYPE_ID" +
            		"and cr_batch.DCM_ID=gen_dcm.DCM_ID" +
            		"and cr_batch.BATCH_TYPE_ID=cr_batch_type.BATCH_TYPE_ID
             */
                       
            fillColumn(cell,row,"select cr_batch.batch_id " +
            		" from cr_batch,CR_BATCH_STATUS_TYPE , gen_dcm,cr_batch_type " +
            		" where cr_batch.batch_id = "+ batchId +
            		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = '7' " +
            		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = CR_BATCH_STATUS_TYPE.BATCH_STATUS_TYPE_ID " +
            		" and cr_batch.DCM_ID=gen_dcm.DCM_ID " +
            		" and cr_batch.BATCH_TYPE_ID=cr_batch_type.BATCH_TYPE_ID ", "batch_id", stat, lookUpSheet, 0, 3);

            dcmName=fillColumn(cell,row,"select gen_dcm.dcm_name " +
            		" from cr_batch,CR_BATCH_STATUS_TYPE,gen_dcm,cr_batch_type" +
            		" where cr_batch.batch_id = "+ batchId +
            		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = '7'" +
            		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = CR_BATCH_STATUS_TYPE.BATCH_STATUS_TYPE_ID" +
            		" and cr_batch.DCM_ID=gen_dcm.DCM_ID" +
            		" and cr_batch.BATCH_TYPE_ID=cr_batch_type.BATCH_TYPE_ID", "dcm_name", stat, lookUpSheet, 1, 3);
            
           date= fillColumn(cell,row,"select to_char(cr_batch.BATCH_DATE,'dd-mm-yyyy') BDate " +
            		" from cr_batch,CR_BATCH_STATUS_TYPE,gen_dcm,cr_batch_type" +
            		" where cr_batch.batch_id = "+ batchId +
            		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = '7'" +
            		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = CR_BATCH_STATUS_TYPE.BATCH_STATUS_TYPE_ID" +
            		" and cr_batch.DCM_ID=gen_dcm.DCM_ID" +
            		" and cr_batch.BATCH_TYPE_ID=cr_batch_type.BATCH_TYPE_ID", "BDate", stat, lookUpSheet, 2,3);
            
            fillColumn(cell,row,"select cr_batch_type.BATCH_TYPE_NAME " +
            		" from cr_batch,CR_BATCH_STATUS_TYPE,gen_dcm,cr_batch_type" +
            		" where cr_batch.batch_id = "+ batchId +
            		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = '7'" +
            		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = CR_BATCH_STATUS_TYPE.BATCH_STATUS_TYPE_ID" +
            		" and cr_batch.DCM_ID=gen_dcm.DCM_ID" +
            		" and cr_batch.BATCH_TYPE_ID=cr_batch_type.BATCH_TYPE_ID", "BATCH_TYPE_NAME", stat, lookUpSheet, 3, 3);
            
            fillColumn(cell,row,"select CR_BATCH_STATUS_TYPE.BATCH_STATUS_TYPE_NAME " +
            		" from cr_batch,CR_BATCH_STATUS_TYPE,gen_dcm,cr_batch_type" +
            		" where cr_batch.batch_id = "+ batchId +
            		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = '7'" +
            		" and cr_batch.BATCH_LAST_STATUS_TYPE_ID = CR_BATCH_STATUS_TYPE.BATCH_STATUS_TYPE_ID" +
            		" and cr_batch.DCM_ID=gen_dcm.DCM_ID" +
            		" and cr_batch.BATCH_TYPE_ID=cr_batch_type.BATCH_TYPE_ID", "BATCH_STATUS_TYPE_NAME", stat, lookUpSheet, 4,3);
            
            
            /*for the sheet part in the excel sheet
             * select cr_sheet.SHEET_ID,cr_sheet.SHEET_SERIAL_ID,cr_sheet.SHEET_POS_CODE,cr_sheet.SHEET_SUPER_DEALER_CODE
			,CR_SHEET_STATUS_TYPE.SHEET_STATUS_TYPE_NAME
			from cr_sheet,CR_SHEET_STATUS_TYPE,CR_SHEET_STATUS
			where
			cr_sheet.SHEET_LAST_STATUS_ID = CR_SHEET_STATUS.SHEET_STATUS_ID and CR_SHEET_STATUS.SHEET_STATUS_TYPE_ID = '5'
			and CR_SHEET_STATUS.SHEET_STATUS_TYPE_ID = CR_SHEET_STATUS_TYPE.SHEET_STATUS_TYPE_ID
			and cr_sheet.BATCH_ID ='47294'
             */
            int i=5;
            ResultSet rs=getMobinilToInfoFortSheetData(con,batchId);
            while(rs.next()){
            	debug("exported "+i);
            	fillCell(row,String.valueOf(rs.getInt("SHEET_SERIAL_ID")),lookUpSheet, 0,i);//sheet serial
            	fillCell(row,rs.getString("SHEET_POS_CODE"),lookUpSheet, 1,i);//pos
            	fillCell(row,rs.getString("SHEET_SUPER_DEALER_CODE"),lookUpSheet, 2,i);//super dealer code
            	//fillCell(rs.getString("count(cr_contract.CONTRACT_MAIN_SIM_NO)"),lookUpSheet, 3,i);//total
            	fillColumn(cell,row," select count(cr_contract.CONTRACT_MAIN_SIM_NO) total from cr_contract,CR_CONTRACT_STATUS where" +
            			" cr_contract.SHEET_ID= "+rs.getString("SHEET_ID") +" and" +
            			" CR_CONTRACT_STATUS.CONTRACT_STATUS_TYPE_ID = '5' " +
            			" and cr_contract.CONTRACT_LAST_STATUS_ID = CR_CONTRACT_STATUS.CONTRACT_STATUS_ID", "total", stat, lookUpSheet, 3, i);
            	
            	
            	fillCell(row,rs.getString("SHEET_STATUS_TYPE_NAME"),lookUpSheet, 4,i);//sheet status
            	i++;
            }
            
            
            
            
            /*for the total number of contracts ber sheet
             * select count(cr_contract.CONTRACT_MAIN_SIM_NO) total from cr_contract,CR_CONTRACT_STATUS where
			cr_contract.SHEET_ID=6968338 and
			CR_CONTRACT_STATUS.CONTRACT_STATUS_TYPE_ID = '5'
			and cr_contract.CONTRACT_LAST_STATUS_ID = CR_CONTRACT_STATUS.CONTRACT_STATUS_ID

             */
            
            //rs=IFReportDeliveyDao.getMobinilToInfoFortData(con, batchId);
           
            
            //wb.setSheetName(0,"");
            //String batchid="";
            //String dcmName="";
            //String creationDate="";
            //String batchtype="";
           // String batchStatus="";
           /* while(rs.next()){
            	debug("exported "+i);
            	if(i%50==0){
            		debug("exported "+i);
            	}
            	fillCell(String.valueOf(rs.getInt("SHEET_SERIAL_ID")),lookUpSheet, 0,i);//sheet serial
            	fillCell(rs.getString("SHEET_POS_CODE"),lookUpSheet, 1,i);//pos
            	fillCell(rs.getString("SHEET_SUPER_DEALER_CODE"),lookUpSheet, 2,i);//super dealer code
            	//fillCell(rs.getString("count(cr_contract.CONTRACT_MAIN_SIM_NO)"),lookUpSheet, 3,i);//total
            	
            	
            	
            	fillCell(rs.getString("SHEET_STATUS_TYPE_NAME"),lookUpSheet, 4,i);//sheet status
            	
            	           		
            		
            	i++;
            	//batchid=rs.getString("BATCH_ID");
            	//dcmName=rs.getString("DCM_NAME");
            	//creationDate=String.valueOf(rs.getDate(""));
            	//batchtype=rs.getString("");
            	//batchStatus=rs.getString("");
            	
            	
            }*/

            
            
           // fillColumn(cell,row,"select STATUS , IMPORT_PAYMENT_STATUS_ID from cam_import_payment_status", "STATUS", "IMPORT_PAYMENT_STATUS_ID", stat, lookUpSheet, 2, 0);
           // fillColumn(cell,row,"select REASON , REASON_ID from CAM_PAYMENT_REASON ", "REASON", "REASON_ID", stat, lookUpSheet, 4, 0);
           // fillColumn(cell,row,"select STK_STATUS , STK_STATUS_ID from CAM_STK_NUMBER_STATUS ", "STK_STATUS", "STK_STATUS_ID", stat, lookUpSheet, 6, 0);
           
            
            //  fillColumn(cell,row,"select     PRODUCT_NAME from VW_GEN_PRODUCT, "PRODUCT_NAME" , stat, lookUpSheet , 2, 0);
            //HSSFSheet dataSheet = wb.getSheet("Data");
            //dataSheet.setSelected(true);
            lookUpSheet.setSelected(false);
            //dataSheet.setSelected(true);
            //dataSheet.createRow(1).createCell((short)0).setCellValue("");
            debug("dcmName: "+fileName+"/"+dcmName+"-"+date+".xls");
            FileOutputStream fileOut = new FileOutputStream(fileName+"/"+dcmName+"-"+date+".xls");
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            return dcmName+"-"+date;
        }
        catch(Exception e) {
            e.printStackTrace();
            return dcmName+"-"+date;
        }
    }
	
	
	
	public static void exportData(String template, String fileName,String jobId,String recordTypeId,String from,String to){
		
    	try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("data");
            Cell cell = null;
            Row row =  null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password            
                     
            Statement stat = con.createStatement();
                       
           fillColumn(cell,row,"select IF_INFO_FORT_DATA.BATCH_ID from " +
            		"(SELECT IF_INFO_FORT_DATA.BATCH_ID ,rownum rnum " +
	      		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
	      		" and JOB_ID= " +
	      		jobId +
	      		"and rownum<" +
	      		to +
	      		") where " +
	      		"rnum>" +
	      		from +
	      		" order by sim_serial", "batch_id", stat, lookUpSheet, 0, 4);
            
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_1ST_NAME from " +
            		"(SELECT IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_1ST_NAME ,rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "CONTRACT_CUSTOMER_1ST_NAME", stat, lookUpSheet, 0, 4);
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME from " +
            		"(SELECT IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME , rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "CONTRACT_CUSTOMER_2ND_NAME", stat, lookUpSheet, 0, 4);
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME from " +
            		"(SELECT IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME ,rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "CONTRACT_CUSTOMER_LST_NAME", stat, lookUpSheet, 0, 4);
            
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.FIRST_NAME from " +
            		"(SELECT IF_INFO_FORT_DATA.FIRST_NAME , rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "FIRST_NAME", stat, lookUpSheet, 0, 4);
            
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.SECOND_NAME from " +
            		"(SELECT IF_INFO_FORT_DATA.SECOND_NAME , rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "SECOND_NAME", stat, lookUpSheet, 0, 4);
            
            
            
            
            
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.THIRD_NAME from " +
            		"(SELECT IF_INFO_FORT_DATA.THIRD_NAME  ,rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "THIRD_NAME", stat, lookUpSheet, 0, 4);
            
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.FOURTH_NAME from " +
            		"(SELECT IF_INFO_FORT_DATA.FOURTH_NAME , rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "FOURTH_NAME", stat, lookUpSheet, 0, 4);
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.CONTRACT_ID from " +
            		"(SELECT IF_INFO_FORT_DATA.CONTRACT_ID , rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "CONTRACT_ID", stat, lookUpSheet, 0, 4);
            
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.DCM_NAME from " +
            		"(SELECT IF_INFO_FORT_DATA.DCM_NAME  , rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "DCM_NAME", stat, lookUpSheet, 0, 4);
            
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.POS_CODE from " +
            		"(SELECT IF_INFO_FORT_DATA.POS_CODE  , rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "POS_CODE", stat, lookUpSheet, 0, 4);
            
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.SHEET_SERIAL from " +
            		"(SELECT IF_INFO_FORT_DATA.SHEET_SERIAL , rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "SHEET_SERIAL", stat, lookUpSheet, 0, 4);
            
            
            fillColumn(cell,row,"select IF_INFO_FORT_DATA.SIM_SERIAL from " +
            		"(SELECT IF_INFO_FORT_DATA.SIM_SERIAL , rownum rnum " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		"and rownum<" +
    	      		to +
    	      		") where " +
    	      		"rnum>" +
    	      		from +
    	      		" order by sim_serial", "SIM_SERIAL", stat, lookUpSheet, 0, 4);
            
            
           /* fillColumn(cell,row,"select * from " +
            		"(SELECT IF_INFO_FORT_DATA.BATCH_ID,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_1ST_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME,IF_INFO_FORT_DATA.CONTRACT_ID,IF_INFO_FORT_DATA.DATA_RECORD_TYPE_ID,IF_INFO_FORT_DATA.DCM_NAME" +
	      		",IF_INFO_FORT_DATA.DIAL,IF_INFO_FORT_DATA.FIRST_NAME,IF_INFO_FORT_DATA.FOURTH_NAME,IF_INFO_FORT_DATA.JOB_ID,IF_INFO_FORT_DATA.POS_CODE,IF_INFO_FORT_DATA.SECOND_NAME,IF_INFO_FORT_DATA.SHEET_SERIAL," +
	      		"IF_INFO_FORT_DATA.SIM_SERIAL,IF_INFO_FORT_DATA.THIRD_NAME  ,rownum rnum " +
	      		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
	      		" and JOB_ID= " +
	      		jobId +
	      		"and rownum<" +
	      		to +
	      		") where " +
	      		"rnum>" +
	      		from +
	      		" order by sim_serial", "batch_id", stat, lookUpSheet, 0, 4);*/
            

            
            
             lookUpSheet.setSelected(false);
            //dataSheet.setSelected(true);
            //dataSheet.createRow(1).createCell((short)0).setCellValue("");
            
            FileOutputStream fileOut = new FileOutputStream(fileName+".xls");
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            
        }
        catch(Exception e) {
            e.printStackTrace();
            
        }
    }
	public static void main(String[] args) {
		
		exportDataByType("c:\\test\\data by type.xls","c:\\test\\","73","3");
	}
	public static void exportDataByType(String template, String fileName,String jobId,String recordTypeId){
		
    	try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("Sheet1");
            Cell cell = null;
            Row row =  null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password            
                     
            Statement stat = con.createStatement();
                       
           fillColumn(cell,row,"SELECT BATCH_ID , sim_serial " +
	      		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
	      		" and JOB_ID= " +
	      		jobId +
	      		" order by sim_serial", "batch_id", stat, lookUpSheet, 0, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.DCM_NAME  , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "DCM_NAME", stat, lookUpSheet, 1, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.DIAL , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "DIAL", stat, lookUpSheet, 2, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.POS_CODE  , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "POS_CODE", stat, lookUpSheet, 3, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.SHEET_SERIAL , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "SHEET_SERIAL", stat, lookUpSheet, 4, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.SIM_SERIAL " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "SIM_SERIAL", stat, lookUpSheet, 5, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.FIRST_NAME , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "FIRST_NAME", stat, lookUpSheet, 6, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.SECOND_NAME , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "SECOND_NAME", stat, lookUpSheet, 7, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.THIRD_NAME  ,sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "THIRD_NAME", stat, lookUpSheet, 8, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.FOURTH_NAME , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "FOURTH_NAME", stat, lookUpSheet, 9, 1);
           
           fillColumn(cell,row,"SELECT CONTRACT_CUSTOMER_1ST_NAME , sim_serial  " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "CONTRACT_CUSTOMER_1ST_NAME", stat, lookUpSheet, 10, 1); 
            
            fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME , sim_serial " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		" order by sim_serial", "CONTRACT_CUSTOMER_2ND_NAME", stat, lookUpSheet, 11, 1);
            
            fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME ,sim_serial " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		" order by sim_serial", "CONTRACT_CUSTOMER_LST_NAME", stat, lookUpSheet, 12, 1); 
            
            /*fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.CONTRACT_ID , sim_serial " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		" order by sim_serial", "CONTRACT_ID", stat, lookUpSheet, 0, 4);*/
            
            
            
            
            
            
            
            
            
            
            
            
           /* fillColumn(cell,row,"select * from " +
            		"(SELECT IF_INFO_FORT_DATA.BATCH_ID,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_1ST_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME,IF_INFO_FORT_DATA.CONTRACT_ID,IF_INFO_FORT_DATA.DATA_RECORD_TYPE_ID,IF_INFO_FORT_DATA.DCM_NAME" +
	      		",IF_INFO_FORT_DATA.DIAL,IF_INFO_FORT_DATA.FIRST_NAME,IF_INFO_FORT_DATA.FOURTH_NAME,IF_INFO_FORT_DATA.JOB_ID,IF_INFO_FORT_DATA.POS_CODE,IF_INFO_FORT_DATA.SECOND_NAME,IF_INFO_FORT_DATA.SHEET_SERIAL," +
	      		"IF_INFO_FORT_DATA.SIM_SERIAL,IF_INFO_FORT_DATA.THIRD_NAME  ,rownum rnum " +
	      		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
	      		" and JOB_ID= " +
	      		jobId +
	      		"and rownum<" +
	      		to +
	      		") where " +
	      		"rnum>" +
	      		from +
	      		" order by sim_serial", "batch_id", stat, lookUpSheet, 0, 4);*/
            

            
            
             lookUpSheet.setSelected(false);
            //dataSheet.setSelected(true);
            //dataSheet.createRow(1).createCell((short)0).setCellValue("");
            
            FileOutputStream fileOut = new FileOutputStream(fileName+".xls");
//            FileOutputStream fileOut_csv = new FileOutputStream(fileName+".csv");
//            OutputStreamWriter out = new OutputStreamWriter(fileOut_csv,"UTF-8");
           
            
//        out.write("aaaaaaaa");
        
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            
        }
        catch(Exception e) {
            e.printStackTrace();
            
        }
    }
public static void exportDataByTypeCSV(String template, String fileName,String jobId,String recordTypeId){
		
    	try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            HSSFWorkbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("Sheet1");
            Cell cell = null;
            Row row =  null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password            
                     
            Statement stat = con.createStatement();
                       
           fillColumn(cell,row,"SELECT BATCH_ID , sim_serial " +
	      		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
	      		" and JOB_ID= " +
	      		jobId +
	      		" order by sim_serial", "batch_id", stat, lookUpSheet, 0, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.DCM_NAME  , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "DCM_NAME", stat, lookUpSheet, 1, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.DIAL , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "DIAL", stat, lookUpSheet, 2, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.POS_CODE  , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "POS_CODE", stat, lookUpSheet, 3, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.SHEET_SERIAL , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "SHEET_SERIAL", stat, lookUpSheet, 4, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.SIM_SERIAL " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "SIM_SERIAL", stat, lookUpSheet, 5, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.FIRST_NAME , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "FIRST_NAME", stat, lookUpSheet, 6, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.SECOND_NAME , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "SECOND_NAME", stat, lookUpSheet, 7, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.THIRD_NAME  ,sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "THIRD_NAME", stat, lookUpSheet, 8, 1);
           
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.FOURTH_NAME , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "FOURTH_NAME", stat, lookUpSheet, 9, 1);
           
           fillColumn(cell,row,"SELECT CONTRACT_CUSTOMER_1ST_NAME , sim_serial  " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "CONTRACT_CUSTOMER_1ST_NAME", stat, lookUpSheet, 10, 1); 
            
            fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME , sim_serial " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		" order by sim_serial", "CONTRACT_CUSTOMER_2ND_NAME", stat, lookUpSheet, 11, 1);
            
            fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME ,sim_serial " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		" order by sim_serial", "CONTRACT_CUSTOMER_LST_NAME", stat, lookUpSheet, 12, 1); 
            
            fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.ID , sim_serial " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		" order by sim_serial", "ID", stat, lookUpSheet, 13, 1);
            
           fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.PASSPORT , sim_serial " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		" order by sim_serial", "PASSPORT", stat, lookUpSheet, 14, 1);

            /*fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.CONTRACT_ID , sim_serial " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		" order by sim_serial", "CONTRACT_ID", stat, lookUpSheet, 0, 4);*/
            
            
            
            
            
            
            
            
            
            
            
            
           /* fillColumn(cell,row,"select * from " +
            		"(SELECT IF_INFO_FORT_DATA.BATCH_ID,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_1ST_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME,IF_INFO_FORT_DATA.CONTRACT_ID,IF_INFO_FORT_DATA.DATA_RECORD_TYPE_ID,IF_INFO_FORT_DATA.DCM_NAME" +
	      		",IF_INFO_FORT_DATA.DIAL,IF_INFO_FORT_DATA.FIRST_NAME,IF_INFO_FORT_DATA.FOURTH_NAME,IF_INFO_FORT_DATA.JOB_ID,IF_INFO_FORT_DATA.POS_CODE,IF_INFO_FORT_DATA.SECOND_NAME,IF_INFO_FORT_DATA.SHEET_SERIAL," +
	      		"IF_INFO_FORT_DATA.SIM_SERIAL,IF_INFO_FORT_DATA.THIRD_NAME  ,rownum rnum " +
	      		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
	      		" and JOB_ID= " +
	      		jobId +
	      		"and rownum<" +
	      		to +
	      		") where " +
	      		"rnum>" +
	      		from +
	      		" order by sim_serial", "batch_id", stat, lookUpSheet, 0, 4);*/
            

            
            
             lookUpSheet.setSelected(false);
            //dataSheet.setSelected(true);
            //dataSheet.createRow(1).createCell((short)0).setCellValue("");
            
            FileOutputStream fileOut = new FileOutputStream(fileName+".xls");
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            
        }
        catch(Exception e) {
            e.printStackTrace();
            
        }
    }
	public static void exportDuplicates(String template, String fileName,String jobId){
		
    	try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            Workbook wb = new HSSFWorkbook();
            Sheet lookUpSheet = wb.createSheet("Sheet1");
            Cell cell = null;
            Row row =  null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password            
                     
            Statement stat = con.createStatement();
            
           ResultSet rs=getNumberOfDublicateSerials(con, jobId);
           int i=1;
           
           while(rs.next()){
        	   debug("i rowNum: "+i);
        	   	  fillColumn(cell,row,"select batch_id from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
        	   		rs.getString("sim_serial") +
        	   		"' and job_id = " +
        	   		jobId, "batch_id", stat, lookUpSheet, 0, i);
                  
                  fillColumn(cell,row,"select DCM_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "DCM_NAME", stat, lookUpSheet, 1, i);
                  
                  fillColumn(cell,row,"select DIAL from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "DIAL", stat, lookUpSheet, 2, i);
                  
                  fillColumn(cell,row,"select POS_CODE from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "POS_CODE", stat, lookUpSheet, 3, i);
                  
                  fillColumn(cell,row,"select SHEET_SERIAL from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "SHEET_SERIAL", stat, lookUpSheet, 4, i);
                  
                  fillColumn(cell,row,"select SIM_SERIAL from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "SIM_SERIAL", stat, lookUpSheet, 5, i);
                  
                  fillColumn(cell,row,"select FIRST_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "FIRST_NAME", stat, lookUpSheet, 6, i);
                  
                  fillColumn(cell,row,"select SECOND_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "SECOND_NAME", stat, lookUpSheet, 7, i);
                  
                  fillColumn(cell,row,"select THIRD_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "THIRD_NAME", stat, lookUpSheet, 8, i);
                  
                  fillColumn(cell,row,"select FOURTH_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "FOURTH_NAME", stat, lookUpSheet, 9, i);
                  
                  fillColumn(cell,row,"select CONTRACT_CUSTOMER_1ST_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "CONTRACT_CUSTOMER_1ST_NAME", stat, lookUpSheet, 10, i); 
                   
                   fillColumn(cell,row,"select CONTRACT_CUSTOMER_2ND_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
               	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "CONTRACT_CUSTOMER_2ND_NAME", stat, lookUpSheet, 11,i);
                   
                   fillColumn(cell,row,"select CONTRACT_CUSTOMER_LST_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
               	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "CONTRACT_CUSTOMER_LST_NAME", stat, lookUpSheet, 12, i);
                   i=currIndex+1;
                   
                   debug("currIndex: "+currIndex);
        	   
           }
                       
            
            
            /*fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.CONTRACT_ID , sim_serial " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		" order by sim_serial", "CONTRACT_ID", stat, lookUpSheet, 0, 4);*/
            
            
            
            
            
            
            
            
            
            
            
            
           /* fillColumn(cell,row,"select * from " +
            		"(SELECT IF_INFO_FORT_DATA.BATCH_ID,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_1ST_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME,IF_INFO_FORT_DATA.CONTRACT_ID,IF_INFO_FORT_DATA.DATA_RECORD_TYPE_ID,IF_INFO_FORT_DATA.DCM_NAME" +
	      		",IF_INFO_FORT_DATA.DIAL,IF_INFO_FORT_DATA.FIRST_NAME,IF_INFO_FORT_DATA.FOURTH_NAME,IF_INFO_FORT_DATA.JOB_ID,IF_INFO_FORT_DATA.POS_CODE,IF_INFO_FORT_DATA.SECOND_NAME,IF_INFO_FORT_DATA.SHEET_SERIAL," +
	      		"IF_INFO_FORT_DATA.SIM_SERIAL,IF_INFO_FORT_DATA.THIRD_NAME  ,rownum rnum " +
	      		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
	      		" and JOB_ID= " +
	      		jobId +
	      		"and rownum<" +
	      		to +
	      		") where " +
	      		"rnum>" +
	      		from +
	      		" order by sim_serial", "batch_id", stat, lookUpSheet, 0, 4);*/
            

            
            
             lookUpSheet.setSelected(false);
            //dataSheet.setSelected(true);
            //dataSheet.createRow(1).createCell((short)0).setCellValue("");
            
            FileOutputStream fileOut = new FileOutputStream(fileName+".xls");
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            
        }
        catch(Exception e) {
            e.printStackTrace();
            
        }
    }
public static void exportDuplicatesCSV(String template, String fileName,String jobId){
		
    	try {
        	debug("path in excelimport: "+template);
            FileInputStream tempIn = new FileInputStream(template);
            HSSFWorkbook wb = new HSSFWorkbook(tempIn);
            Sheet lookUpSheet = wb.getSheet("Sheet1");
            Cell cell = null;
            Row row =  null;
          //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
            Connection con =Utility.getConnection();            
          // @machineName:port:SID,   userid,  password            
                     
            Statement stat = con.createStatement();
            
           ResultSet rs=getNumberOfDublicateSerials(con, jobId);
           int i=1;
           
           while(rs.next()){
        	   debug("i rowNum: "+i);
        	   	  fillColumn(cell,row,"select batch_id from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
        	   		rs.getString("sim_serial") +
        	   		"' and job_id = " +
        	   		jobId, "batch_id", stat, lookUpSheet, 0, i);
                  
                  fillColumn(cell,row,"select DCM_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "DCM_NAME", stat, lookUpSheet, 1, i);
                  
                  fillColumn(cell,row,"select DIAL from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "DIAL", stat, lookUpSheet, 2, i);
                  
                  fillColumn(cell,row,"select POS_CODE from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "POS_CODE", stat, lookUpSheet, 3, i);
                  
                  fillColumn(cell,row,"select SHEET_SERIAL from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "SHEET_SERIAL", stat, lookUpSheet, 4, i);
                  
                  fillColumn(cell,row,"select SIM_SERIAL from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "SIM_SERIAL", stat, lookUpSheet, 5, i);
                  
                  fillColumn(cell,row,"select FIRST_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "FIRST_NAME", stat, lookUpSheet, 6, i);
                  
                  fillColumn(cell,row,"select SECOND_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "SECOND_NAME", stat, lookUpSheet, 7, i);
                  
                  fillColumn(cell,row,"select THIRD_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "THIRD_NAME", stat, lookUpSheet, 8, i);
                  
                  fillColumn(cell,row,"select FOURTH_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "FOURTH_NAME", stat, lookUpSheet, 9, i);
                  
                  fillColumn(cell,row,"select CONTRACT_CUSTOMER_1ST_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
              	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "CONTRACT_CUSTOMER_1ST_NAME", stat, lookUpSheet, 10, i); 
                   
                   fillColumn(cell,row,"select CONTRACT_CUSTOMER_2ND_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
               	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "CONTRACT_CUSTOMER_2ND_NAME", stat, lookUpSheet, 11,i);
                   
                   fillColumn(cell,row,"select CONTRACT_CUSTOMER_LST_NAME from IF_INFO_FORT_DATA where SIM_SERIAL= '" +
               	   		rs.getString("sim_serial") +
            	   		"' and job_id = " +
            	   		jobId, "CONTRACT_CUSTOMER_LST_NAME", stat, lookUpSheet, 12, i);
                   i=currIndex+1;
                   
                   debug("currIndex: "+currIndex);
        	   
           }
                       
            
            
            /*fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.CONTRACT_ID , sim_serial " +
            		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
    	      		" and JOB_ID= " +
    	      		jobId +
    	      		" order by sim_serial", "CONTRACT_ID", stat, lookUpSheet, 0, 4);*/
            
            
            
            
            
            
            
            
            
            
            
            
           /* fillColumn(cell,row,"select * from " +
            		"(SELECT IF_INFO_FORT_DATA.BATCH_ID,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_1ST_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME,IF_INFO_FORT_DATA.CONTRACT_ID,IF_INFO_FORT_DATA.DATA_RECORD_TYPE_ID,IF_INFO_FORT_DATA.DCM_NAME" +
	      		",IF_INFO_FORT_DATA.DIAL,IF_INFO_FORT_DATA.FIRST_NAME,IF_INFO_FORT_DATA.FOURTH_NAME,IF_INFO_FORT_DATA.JOB_ID,IF_INFO_FORT_DATA.POS_CODE,IF_INFO_FORT_DATA.SECOND_NAME,IF_INFO_FORT_DATA.SHEET_SERIAL," +
	      		"IF_INFO_FORT_DATA.SIM_SERIAL,IF_INFO_FORT_DATA.THIRD_NAME  ,rownum rnum " +
	      		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
	      		" and JOB_ID= " +
	      		jobId +
	      		"and rownum<" +
	      		to +
	      		") where " +
	      		"rnum>" +
	      		from +
	      		" order by sim_serial", "batch_id", stat, lookUpSheet, 0, 4);*/
            

            
            
             lookUpSheet.setSelected(false);
            //dataSheet.setSelected(true);
            //dataSheet.createRow(1).createCell((short)0).setCellValue("");
            
            FileOutputStream fileOut = new FileOutputStream(fileName+".xls");
            wb.write(fileOut);
            fileOut.close();
            
            //con.close();
            Utility.closeConnection(con);
            
        }
        catch(Exception e) {
            e.printStackTrace();
            
        }
    }

public static void exportJobDetailsPage(String template, String fileName,String jobId,int from, int to){
	
	try {
    	debug("path in excelimport: "+template);
        FileInputStream tempIn = new FileInputStream(template);
        HSSFWorkbook wb = new HSSFWorkbook(tempIn);
        Sheet lookUpSheet = wb.getSheet("Sheet1");
        Cell cell = null;
        Row row =  null;
      //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
        Connection con =Utility.getConnection();            
      // @machineName:port:SID,   userid,  password            
                 
        Statement stat = con.createStatement();
                   
       fillColumn(cell,row,"select * from (SELECT distinct(sim_serial),rownum rnum,  BATCH_ID   " +
    		   "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
	      		jobId +
	      		"and rownum<=" +
	      		to +
	      		") where " +
	      		"rnum>" +
	      		from +
	      		" order by sim_serial", "batch_id", stat, lookUpSheet, 0, 1);
          
          fillColumn(cell,row,"select * from (SELECT distinct(sim_serial),rownum rnum,  IF_INFO_FORT_DATA.DCM_NAME   " +
        		  "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
  	      		jobId +
  	      		"and rownum<" +
  	      		to +
  	      		") where " +
  	      		"rnum>" +
  	      		from +
  	      		" order by sim_serial", "DCM_NAME", stat, lookUpSheet, 1, 1);
          
          fillColumn(cell,row,"select * from (SELECT distinct(sim_serial), rownum rnum,  IF_INFO_FORT_DATA.DIAL   " +
        		  "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
  	      		jobId +
  	      		"and rownum<" +
  	      		to +
  	      		") where " +
  	      		"rnum>" +
  	      		from +
  	      		" order by sim_serial", "DIAL", stat, lookUpSheet, 2, 1);
          
          fillColumn(cell,row,"select * from (SELECT distinct(sim_serial),rownum rnum,  IF_INFO_FORT_DATA.POS_CODE  " +
        		  "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
  	      		jobId +
  	      		"and rownum<" +
  	      		to +
  	      		") where " +
  	      		"rnum>" +
  	      		from +
  	      		" order by sim_serial", "POS_CODE", stat, lookUpSheet, 3, 1);
          
          fillColumn(cell,row,"select * from (SELECT distinct(sim_serial), rownum rnum,  IF_INFO_FORT_DATA.SHEET_SERIAL   " +
        		  "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
  	      		jobId +
  	      		"and rownum<" +
  	      		to +
  	      		") where " +
  	      		"rnum>" +
  	      		from +
  	      		" order by sim_serial", "SHEET_SERIAL", stat, lookUpSheet, 4, 1);
          
          fillColumn(cell,row,"select * from (SELECT distinct(sim_serial)  sim_serial, rownum rnum" +
        		  " from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
  	      		jobId +
  	      		"and rownum<" +
  	      		to +     
  	      		") where " +
  	      		"rnum>" +
  	      		from +
  	      		" order by sim_serial", "SIM_SERIAL", stat, lookUpSheet, 5, 1);
          
          fillColumn(cell,row,"select * from (SELECT distinct(sim_serial), rownum rnum,  IF_INFO_FORT_DATA.FIRST_NAME  " +
        		  "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
  	      		jobId +
  	      		"and rownum<" +
  	      		to +
  	      		") where " +
  	      		"rnum>" +
  	      		from +
  	      		" order by sim_serial", "FIRST_NAME", stat, lookUpSheet, 6, 1);
          
          fillColumn(cell,row,"select * from (SELECT distinct(sim_serial), rownum rnum,  IF_INFO_FORT_DATA.SECOND_NAME  " +
        		  "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
  	      		jobId +
  	      		"and rownum<" +
  	      		to +
  	      		") where " +
  	      		"rnum>" +
  	      		from +
  	      		" order by sim_serial", "SECOND_NAME", stat, lookUpSheet, 7, 1);
          
          fillColumn(cell,row,"select * from (SELECT distinct(sim_serial), rownum rnum,  IF_INFO_FORT_DATA.THIRD_NAME  " +
        		  "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
  	      		jobId +
  	      		"and rownum<" +
  	      		to +
  	      		") where " +
  	      		"rnum>" +
  	      		from +
  	      		" order by sim_serial", "THIRD_NAME", stat, lookUpSheet, 8, 1);
          
          fillColumn(cell,row,"select * from (SELECT distinct(sim_serial), rownum rnum,  IF_INFO_FORT_DATA.FOURTH_NAME  " +
        		  "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
  	      		jobId +
  	      		"and rownum<" +
  	      		to +
  	      		") where " +
  	      		"rnum>" +
  	      		from +
  	      		" order by sim_serial", "FOURTH_NAME", stat, lookUpSheet, 9, 1);
          
          fillColumn(cell,row,"select * from (SELECT distinct(sim_serial), rownum rnum,  CONTRACT_CUSTOMER_1ST_NAME   " +
        		  "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
  	      		jobId +
  	      		"and rownum<" +
  	      		to +
  	      		") where " +
  	      		"rnum>" +
  	      		from +
  	      		" order by sim_serial", "CONTRACT_CUSTOMER_1ST_NAME", stat, lookUpSheet, 10, 1); 
           
           fillColumn(cell,row,"select * from (SELECT distinct(sim_serial), rownum rnum,  IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME  " +
        		   "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
   	      		jobId +
   	      		"and rownum<" +
   	      		to +
   	      		") where " +
   	      		"rnum>" +
   	      		from +
   	      		" order by sim_serial", "CONTRACT_CUSTOMER_2ND_NAME", stat, lookUpSheet, 11, 1);
           
           fillColumn(cell,row,"select * from (SELECT distinct(sim_serial),rownum rnum,  IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME  " +
        		   "from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
   	      		jobId +
   	      		"and rownum<" +
   	      		to +
   	      		") where " +
   	      		"rnum>" +
   	      		from +
   	      		" order by sim_serial", "CONTRACT_CUSTOMER_LST_NAME", stat, lookUpSheet, 12, 1); 
           
           /*fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.CONTRACT_ID , sim_serial " +
           		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
   	      		" and JOB_ID= " +
   	      		jobId +
   	      		" order by sim_serial", "CONTRACT_ID", stat, lookUpSheet, 0, 4);*/
           
           
           
      
      
       
      
        
        /*fillColumn(cell,row,"SELECT IF_INFO_FORT_DATA.CONTRACT_ID , sim_serial " +
        		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
	      		" and JOB_ID= " +
	      		jobId +
	      		" order by sim_serial", "CONTRACT_ID", stat, lookUpSheet, 0, 4);*/
        
        
        
        
        
        
        
        
        
        
        
        
       /* fillColumn(cell,row,"select * from " +
        		"(SELECT IF_INFO_FORT_DATA.BATCH_ID,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_1ST_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME,IF_INFO_FORT_DATA.CONTRACT_ID,IF_INFO_FORT_DATA.DATA_RECORD_TYPE_ID,IF_INFO_FORT_DATA.DCM_NAME" +
      		",IF_INFO_FORT_DATA.DIAL,IF_INFO_FORT_DATA.FIRST_NAME,IF_INFO_FORT_DATA.FOURTH_NAME,IF_INFO_FORT_DATA.JOB_ID,IF_INFO_FORT_DATA.POS_CODE,IF_INFO_FORT_DATA.SECOND_NAME,IF_INFO_FORT_DATA.SHEET_SERIAL," +
      		"IF_INFO_FORT_DATA.SIM_SERIAL,IF_INFO_FORT_DATA.THIRD_NAME  ,rownum rnum " +
      		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = " +recordTypeId+
      		" and JOB_ID= " +
      		jobId +
      		"and rownum<" +
      		to +
      		") where " +
      		"rnum>" +
      		from +
      		" order by sim_serial", "batch_id", stat, lookUpSheet, 0, 4);*/
        

        
        
         lookUpSheet.setSelected(false);
        //dataSheet.setSelected(true);
        //dataSheet.createRow(1).createCell((short)0).setCellValue("");
        
        FileOutputStream fileOut = new FileOutputStream(fileName+".xls");
        wb.write(fileOut);
        fileOut.close();
        
        //con.close();
        Utility.closeConnection(con);
        
    }
    catch(Exception e) {
        e.printStackTrace();
        
    }
}


public static void generateChangeTypeTemplate(String template, String fileName){
	
	try {
    	debug("path in excelimport: "+template);
        FileInputStream tempIn = new FileInputStream(template);
        HSSFWorkbook wb = new HSSFWorkbook(tempIn);
        Sheet lookUpSheet = wb.getSheet("lookup");
        Cell cell = null;
        Row row =  null;
      //   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      //  Connection con = DriverManager.getConnection(dbConnectionString, dbUserName, dbPassword);
        Connection con =Utility.getConnection();            
      // @machineName:port:SID,   userid,  password            
                 
        Statement stat = con.createStatement();
                   
       fillColumn(cell,row,"SELECT DATA_RECORD_TYPE_NAME from IF_DATA_RECORD_TYPE where IF_DATA_RECORD_TYPE.DATA_RECORD_TYPE_ID!=3 and IF_DATA_RECORD_TYPE.DATA_RECORD_TYPE_ID!=1 ", "DATA_RECORD_TYPE_NAME", stat, lookUpSheet, 0, 1);
          
                 
         lookUpSheet.setSelected(false);
        //dataSheet.setSelected(true);
        //dataSheet.createRow(1).createCell((short)0).setCellValue("");
        
        FileOutputStream fileOut = new FileOutputStream(fileName+".xls");
        wb.write(fileOut);
        fileOut.close();
        
        //con.close();
        Utility.closeConnection(con);
        
    }
    catch(Exception e) {
        e.printStackTrace();
        
    }
}
	
	
	private static boolean fillCell(Row row,String cellValue, Sheet sheet, int colNumber, int startRow) {
        try {
        	
            int index = startRow;
            Row testRow = null;
            testRow = sheet.getRow(index);
        	
        	//System.out.println("testRow isss "+testRow);
        	
        	if (testRow==null)            	
            row = sheet.createRow(index);
        	else row = testRow;
        	
           	//debug("culIDName:"+ culIDName);
            //Row row = sheet.createRow((short)index);
            Cell cell = null;
            cell = row.createCell((short)colNumber);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//            cell.setCellValue("cell row data");
            cell.setCellValue(cellValue);
            // cell = row.createCell((short)(colNumber + 1));
            // cell.setCellValue((String)resultSet.getString(culIDName));
            index++;
           
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	private static String fillColumn(Cell cell,Row row,String sql, String culName, 
			Statement stat, Sheet sheet, int colNumber, int startRow) {
		PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
		String rsName="";
        try {
        	
        	//debug("culName:"+ culName);
        	debug("sql statement:"+sql);
            ResultSet resultSet = stat.executeQuery(sql);
            int index = startRow;
            Row testRow = null;
            
            while(resultSet.next()) {            	
            	//debug("culIDName:"+ culIDName);
            	testRow = sheet.getRow(index);
            	
            	//System.out.println("testRow isss "+testRow);
            	
            	if (testRow==null)            	
                row = sheet.createRow(index);
            	else row = testRow;
            	
            	
                cell = row.createCell(colNumber);
//                cell.setCellValue("cell col data");
                
                
                if(culName.compareTo("BDate")==0)
                {
                	rsName=String.valueOf(resultSet.getString(culName));
                	debug("BDate: "+String.valueOf(resultSet.getString(culName)));
                	cell.setCellValue(rsName);
                }
                else{
                rsName=(String)resultSet.getString(culName);
                
                debug("result name: "+index+" "+rsName);
               // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellValue((String)resultSet.getString(culName));
                }
               // cell = row.createCell((short)(colNumber + 1));
               // cell.setCellValue((String)resultSet.getString(culIDName));
                index++;
            }
            currIndex=index;
            
            resultSet.close();
            return rsName;
        }
        catch(Exception e) {
            e.printStackTrace();
            return rsName;
        }
    }
	
	
	
	public static int createNewJob(String userId,String reportCreationDate){
		ResultSet res=null;
		int jobId=0;
		String stemp="'"; 
		stemp+=reportCreationDate.substring(3, 5);
		stemp+="/";
		stemp+=reportCreationDate.substring(0,2);
		stemp+="/";
		stemp+=reportCreationDate.substring(6, 10);
		stemp+="'";
		debug("stemp: "+stemp);
		
	    try
	    {
	    	Connection con=Utility.getConnection();
	      Statement stat = con.createStatement();
	      String strSql = "insert into IF_JOB_DETAILS(JOB_ID,JOB_DATE,JOB_STATUS_ID,USER_ID,REPORT_CREATION_DATE) values(IF_INFO_FORT_JOB_ID.nextval,sysdate,1,"+userId+",to_date("+stemp+",'dd/mm/yyyy'))";

	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      stat.executeQuery(strSql);
	      
	      strSql = "select IF_INFO_FORT_JOB_ID.currval from dual ";
	      debug("The query is " + strSql);
	      res=stat.executeQuery(strSql);
	      while(res.next()){
	      jobId=res.getInt("currval");
	      }
	      
	      strSql = "update IF_INFO_FORT_DATA set JOB_ID= "+jobId+" where JOB_ID is null";
	      debug("The query is " + strSql);
	      stat.executeUpdate(strSql);
	      debug("elmafroood done");
	      
	      //stat.close();
	      Utility.closeConnection(con);
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return jobId;
	}
	public static Vector getInfoFortData(Connection con,String jobID,String from,String to){
		Vector dataRecords=new Vector();
		try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from " +
	      		"(SELECT IF_INFO_FORT_DATA.BATCH_ID,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_1ST_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME,IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME,IF_INFO_FORT_DATA.CONTRACT_ID,IF_INFO_FORT_DATA.DATA_RECORD_TYPE_ID,IF_INFO_FORT_DATA.DCM_NAME" +
	      		",IF_INFO_FORT_DATA.DIAL,IF_INFO_FORT_DATA.FIRST_NAME,IF_INFO_FORT_DATA.FOURTH_NAME,IF_INFO_FORT_DATA.JOB_ID,IF_INFO_FORT_DATA.POS_CODE,IF_INFO_FORT_DATA.SECOND_NAME,IF_INFO_FORT_DATA.SHEET_SERIAL," +
	      		" sim_serial , IF_INFO_FORT_DATA.THIRD_NAME  ,rownum rnum " +
	      		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
	      		jobID +
	      		" and rownum<=" +
	      		to +
	      		") where " +
	      		"rnum>" +
	      		from +
	      		" order by sim_serial";
	      
	      
	    	  //strSql+=" JOB_ID= "+jobID;
	      
	      
	    	  //strSql+=" and DATA_RECORD_TYPE_ID !=2 ";
	    	  //strSql+=" and rownum<100 ";
	      
	      
	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      ResultSet rs = stat.executeQuery(strSql);
	      while (rs.next())
	      {
	    	  InfoFortDataModel dm=new InfoFortDataModel();
	    	  dm.setJobId(rs.getInt("JOB_ID"));
	    	  dm.setBatchId(rs.getInt("BATCH_ID"));
	    	  dm.setDcmName(rs.getString("DCM_NAME"));
	    	  dm.setSheetSerial(rs.getInt("SHEET_SERIAL"));
	    	  dm.setPosCode(rs.getString("POS_CODE"));
	    	  dm.setSimSerial(rs.getString("SIM_SERIAL"));
	    	  dm.setDial(rs.getInt("DIAL"));
	    	  dm.setInfoFort1stName(rs.getString("FIRST_NAME"));
	    	  dm.setInfoFort2ndName(rs.getString("SECOND_NAME"));
	    	  dm.setInfoFort3rdName(rs.getString("THIRD_NAME"));
	    	  dm.setInfoFort4thName(rs.getString("FOURTH_NAME"));
	    	  dm.setContractId(rs.getInt("CONTRACT_ID"));
	    	  dm.setContract1stName(rs.getString("CONTRACT_CUSTOMER_1ST_NAME"));
	    	  dm.setContract2ndName(rs.getString("CONTRACT_CUSTOMER_2ND_NAME"));
	    	  dm.setContractlstName(rs.getString("CONTRACT_CUSTOMER_LST_NAME"));
	    	  dm.setRecordTypeId(rs.getInt("DATA_RECORD_TYPE_ID"));	  
	    	  
	    	  
	    	  
	    	  dataRecords.add(dm);
	        
	        
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return dataRecords;
	}
	public static ResultSet getInfoFortDataForCompare(Connection con,String jobID){
		ResultSet rs=null;
		try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from " +
	      		"(SELECT IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_1ST_NAME," +
	      		"IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME," +
	      		"IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME" +
	      		",IF_INFO_FORT_DATA.FIRST_NAME" +
	      		",IF_INFO_FORT_DATA.FOURTH_NAME" +
	      		",IF_INFO_FORT_DATA.SECOND_NAME" +
	      		",sim_serial" +
	      		",IF_INFO_FORT_DATA.THIRD_NAME " +
	      		"from IF_INFO_FORT_DATA" +
	      		" where " +
	      		//"DATA_RECORD_TYPE_ID!=3 and " +
	      		"DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
	      		jobID +	      		
	      		") " +
	      		" order by sim_serial";
	      
	       
	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      rs = stat.executeQuery(strSql);
	      
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return rs;
	}
	public static int getInfoFortDataCount(Connection con,String jobID){
		//Vector dataRecords=new Vector();
		int count=0;
		try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "SELECT count(IF_INFO_FORT_DATA.BATCH_ID) countid " +
	      		"from IF_INFO_FORT_DATA where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " + jobID;
	      
	      
	    	  //strSql+=" JOB_ID= "+jobID;
	      
	      
	    	  //strSql+=" and DATA_RECORD_TYPE_ID !=2 ";
	    	  //strSql+=" and rownum<100 ";
	      
	      
	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      ResultSet rs = stat.executeQuery(strSql);
	      
	      while (rs.next())
	      {    	  
	        count=rs.getInt("countid");
	        
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return count;
	}
	public static void closeJob(Connection con,String jobId){
		 try
		    {
		      Statement stat = con.createStatement();
		      String strSql = "update IF_JOB_DETAILS set JOB_STATUS_ID=2 where job_id = "+jobId;
		      	      

		      //Utility.logger.debug(strSql);
		      debug("The query is " + strSql);
		     stat.executeQuery(strSql);
		     
		      stat.close();
		    }
		    catch(Exception e)
		    {
		    e.printStackTrace();
		    }
	}
	public static void deleteJob(Connection con,String jobId){
		 try
		    {
		      Statement stat = con.createStatement();
		      String strSql = "update IF_JOB_DETAILS set JOB_STATUS_ID=3 where job_id = "+jobId;		      	      

		      //Utility.logger.debug(strSql);
		      debug("The query is " + strSql);
		     stat.executeQuery(strSql);
		     
		      stat.close();
		    }
		    catch(Exception e)
		    {
		    e.printStackTrace();
		    }
	}
	
	
	public static Vector getDataRecordTypes(Connection con){
		Vector dataRecords=new Vector();
		try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "SELECT * from IF_DATA_RECORD_TYPE where IF_DATA_RECORD_TYPE.DATA_RECORD_TYPE_ID!=3 and IF_DATA_RECORD_TYPE.DATA_RECORD_TYPE_ID!=1 ";
	      
	      
	      //Utility.logger.debug(strSql);
	      debug("The query is " + strSql);
	      ResultSet rs = stat.executeQuery(strSql);
	      while (rs.next())
	      {
	    	  DataRecordTypeModel tm=new DataRecordTypeModel();
	    	 tm.setTypeId(rs.getInt("DATA_RECORD_TYPE_ID"));
	    	 tm.setTypeName(rs.getString("DATA_RECORD_TYPE_NAME"));
	        
	    	 dataRecords.add(tm);
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return dataRecords;
	}
	
	
	public static void updateJobDetailsData(String userId,String simSerial,String typeId,String jobId){
		try
	    {
	    	Connection con=Utility.getConnection();
	      Statement stat = con.createStatement();
	      
	      String strSql = "select DATA_RECORD_TYPE_ID from IF_INFO_FORT_DATA  where SIM_SERIAL= "+simSerial +" and job_id= " +jobId ;
	      debug("The query is " + strSql);
	      ResultSet res=(ResultSet)  stat.executeQuery(strSql);
	      int oldTypeId=0;
	      while(res.next()){
	    	  oldTypeId=res.getInt(1);
	      }
	      //update only if old type is not the parameter type
	      if(oldTypeId!=Integer.parseInt(typeId)){
	    	  
	    	  
	    	  strSql = "update IF_INFO_FORT_DATA set DATA_RECORD_TYPE_ID= "+typeId+" where SIM_SERIAL= "+simSerial +" and job_id= " +jobId ;
		      debug("The query is " + strSql);
		      stat.executeUpdate(strSql);
		      
		      
		      
		      strSql ="select DATA_RECORD_TYPE_NAME from IF_DATA_RECORD_TYPE where DATA_RECORD_TYPE_ID = "+typeId;
		      debug("The query is " + strSql);
		      ResultSet rs=stat.executeQuery(strSql);
		      String typeName="";
		      while(rs.next()){
		    	  typeName=rs.getString(1);
		    	  
		      }
		      strSql ="insert into IF_DATA_ACTIONS_HISTORY(DATA_ACTIONS_HISTORY_ID,SIM_SERIAL,USER_ID,TIME_STAMP,ACTION_UPDATE_TO) values(IF_ACTION_HISTORY_ID.nextval," +
		  		"'"+ simSerial + "'"+
		  		" ," +
		  		userId +
		  		",sysdate," +
		  		"'"+typeName +"'"+
		  		")";
		      debug("The query is " + strSql);
		      stat.executeUpdate(strSql);
	    	  
	      }
	      
	      
	      
	      
	      Utility.closeConnection(con);
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	}
	public static boolean compare(ResultSet rs){
		String infoFort1stName="",infoFort2ndName="",infoFort3rdName="";
		String contract1stName="",contract2ndName="",contract3rdName="";
		String simSerial="";
		try{
			while(rs.next()){
				infoFort1stName=rs.getString("FIRST_NAME");
				infoFort2ndName=rs.getString("SECOND_NAME");
				infoFort3rdName=rs.getString("THIRD_NAME");
				
				contract1stName=rs.getString("CONTRACT_CUSTOMER_1ST_NAME");
				contract2ndName=rs.getString("CONTRACT_CUSTOMER_2ND_NAME");
				contract3rdName=rs.getString("CONTRACT_CUSTOMER_LST_NAME");
				
				//IF any name is null what to do??????????????
								
				debug("sim serial:  "+rs.getString("SIM_SERIAL"));
				
				debug("comparing first name: "+compareTwoNames(removeSpaces(infoFort1stName), removeSpaces(contract1stName)));
				
				debug("comparing second name: "+compareTwoNames(removeSpaces(infoFort2ndName), removeSpaces(contract2ndName)));
				
				debug("comparing third name: "+compareTwoNames(removeSpaces(infoFort3rdName), removeSpaces(contract3rdName)));
				
				//if the three are ok update as matched
				//if(compareTwoNames(removeSpaces(infoFort1stName), removeSpaces(contract1stName)) && compareTwoNames(removeSpaces(infoFort2ndName), removeSpaces(contract2ndName)) && compareTwoNames(removeSpaces(infoFort3rdName), removeSpaces(contract3rdName)) )
				//update this sim as matched
				
			}	
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
			
		
		
		 
		return true;
	}
	public static boolean compareTwoNames(String name,String cName){
		if(name.length()!=cName.length())
			return false;
		
		char c1='a',c2='a';
		for(int i=0;i<name.length();i++){
			c1=name.charAt(i);
			c2=cName.charAt(i);
			
			//if the two characters is any kind of alf 
			if(( c1=='\u0625' ||c1=='\u0627'||c1=='\u0623'||c1=='\u0622') && ( c2=='\u0625' ||c2=='\u0627'||c2=='\u0623'||c2=='\u0622'))
				continue;
			//if the two characters is any kind of waw 
			else if((c1=='\u0624'||c1=='\u0648')&& (c2=='\u0624'||c2=='\u0648'))
			continue;
			//if the two characters is any kind of waw 
			else if((c1=='\u0626'||c1=='\u0649'||c1=='\u064A')&& (c2=='\u0626'||c2=='\u0649'||c2=='\u064A'))
			continue;
			else if(Character.valueOf(c1).compareTo(Character.valueOf(c2))==0)
				continue;
			else
				return false;
			
			
			
		}
		
		
		return true;
	}
	private static String removeSpaces(String name)
	{
		StringTokenizer st=new StringTokenizer(name," ",false);
		String tempName="";
		while(st.hasMoreElements())
			tempName+=st.nextElement();
		
		return tempName;
	}
	
	public static void compare(Connection con,int jobId,int userId)
    {
	
        
        
    //--first step normalize
    //--second step if the one contain the other then true
    //--if they not contain each other compare character by character
        
    
    ResultSet rs=null;
    String infoFortName=null,infoFort1stName=null,infoFort2ndName=null,infoFort3rdName=null;
    String contractName=null,contract1stName=null,contract2ndName=null,contract3rdName=null;
    String simSerial=null;
    String temp=null;
    int firstNameRate=0,secondNameRate=0,thirdNameRate=0,totalRate=0;
    int counter=0;
    boolean check=false;
    
    
    try{
        
        //get the connection
      /*  if(System.getProperty("oracle.jserver.version")!=null){
            con=DriverManager.getConnection("jdbc:default:connection:");
            System.out.println("connection is ok");
        }else{
            System.out.println("connection is not ok");
        }*/
        
        //excute the query and get the result set
        
        rs=getInfoFortDataForCompare(con, String.valueOf(jobId));

        
        while(rs.next()){
        	infoFort1stName=rs.getString("FIRST_NAME");
        	for(int i=0;i<infoFort1stName.length();i++)
                temp+=normalize(infoFort1stName.charAt(i));
                
                
        	infoFort1stName=temp;
                temp="";
        	
            infoFort2ndName=rs.getString("SECOND_NAME");
            for(int i=0;i<infoFort2ndName.length();i++)
                temp+=normalize(infoFort2ndName.charAt(i));
                
                
            infoFort2ndName=temp;
                temp="";
                
            infoFort3rdName=rs.getString("THIRD_NAME");
            for(int i=0;i<infoFort3rdName.length();i++)
                temp+=normalize(infoFort3rdName.charAt(i));
                
                
            infoFort3rdName=temp;
                temp="";
            
            infoFortName=infoFort1stName+infoFort2ndName+contract3rdName;
            System.out.println("infoFortName:" +infoFortName);
            
            
            
            contract1stName=rs.getString("CONTRACT_CUSTOMER_1ST_NAME");
            for(int i=0;i<contract1stName.length();i++)
                temp+=normalize(contract1stName.charAt(i));
                
                
            contract1stName=temp;
                temp="";
                
            contract2ndName=rs.getString("CONTRACT_CUSTOMER_2ND_NAME");
            for(int i=0;i<contract2ndName.length();i++)
                temp+=normalize(contract2ndName.charAt(i));
                
                
            contract2ndName=temp;
                temp="";
                
            contract3rdName=rs.getString("CONTRACT_CUSTOMER_LST_NAME");
            for(int i=0;i<contract3rdName.length();i++)
                temp+=normalize(contract3rdName.charAt(i));
                
                
            contract3rdName=temp;
                temp="";
            
            contractName=contract1stName+contract2ndName+contract3rdName;
            System.out.println("contractName:" +contractName);
            
            simSerial=rs.getString("SIM_SERIAL");
            
            //normalizing the two names
            
           /* for(int i=0;i<infoFortName.length();i++)
            temp+=normalize(infoFortName.charAt(i));
            
            
            infoFortName=temp;
            temp=null;
            
            for(int i=0;i<contractName.length();i++)
                temp+=normalize(contractName.charAt(i));
            contractName=temp;
            
            temp=null;*/
            
            //--check if one contain the other
            if(checkContains(removeSpaces(infoFortName), removeSpaces(contractName))){
                //update this sim as matched
            	System.out.println("update as matched because they contains each others ");
                updateJobDetailsData(con,String.valueOf(userId), simSerial, "2");
            }
                
                else {        //comparing character by character
                    
                    
                    //compare the first names
                    for(int i=0;i<infoFort1stName.length()&& i<contract1stName.length();i++)
                    {
                        if(infoFort1stName.charAt(i)!=contract1stName.charAt(i))
                            counter++;
                        
                    }
                    if(infoFort1stName.length()>contract1stName.length())
                       	counter+=infoFort1stName.length()-contract1stName.length();
                    else
                    	counter+=contract1stName.length()-infoFort1stName.length();
                    
                    	
                    firstNameRate=counter*6;
                    counter=0;
                    
                    //compare the second names
                    for(int i=0;i<infoFort2ndName.length()&& i<contract2ndName.length();i++)
                    {
                        if(infoFort2ndName.charAt(i)!=contract2ndName.charAt(i))
                            counter++;
                    }
                    
                    if(infoFort2ndName.length()>contract2ndName.length())
                       	counter+=infoFort2ndName.length()-contract2ndName.length();
                    else
                    	counter+=contract2ndName.length()-infoFort2ndName.length();
                    
                    secondNameRate=counter*4;
					counter=0;
					
					//compare the third names
					for(int i=0;i<infoFort3rdName.length() && i<contract3rdName.length();i++)
					{
						if(infoFort3rdName.charAt(i)!=contract3rdName.charAt(i))
							counter++;
					}
					
					if(infoFort3rdName.length()>contract3rdName.length())
                       	counter+=infoFort3rdName.length()-contract3rdName.length();
                    else
                    	counter+=contract3rdName.length()-infoFort3rdName.length();
                    
					thirdNameRate=counter*2;
					counter=0;
					
					
					totalRate=firstNameRate+secondNameRate+thirdNameRate;
					
					if(totalRate<30){
						//update this sim as matched
						System.out.println("update as matched because the rate is less than 30 ");
						updateJobDetailsData(con,String.valueOf(userId), simSerial, "2");
						
					}
					else{
							//update as un matched
						System.out.println("update as un matched because the rate is more than 30 ");
						updateJobDetailsData(con,String.valueOf(userId), simSerial, "7");
						
					}
					
				}
            System.out.println("sim serial: "+simSerial);
            System.out.println("total rate: "+totalRate);
            System.out.println("1st rate: "+firstNameRate);
            System.out.println("2nd rate: "+secondNameRate);
            System.out.println("3rd rate: "+thirdNameRate);
			totalRate=0;
			firstNameRate=0;
			secondNameRate=0;
			thirdNameRate=0;
			
			
			
		}	
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	
	
	
	}
	public static char normalize(char c1)
	{
	char cc=c1;
	if( c1=='\u0625' ||c1=='\u0627'||c1=='\u0623'||c1=='\u0622')
	cc='\u0627';
	else if(c1=='\u0624'||c1=='\u0648')
	cc='\u0648';
	else if(c1=='\u0626'||c1=='\u0649'||c1=='\u064A')
	cc='\u0649';
	return cc;
	}
	public static boolean checkContains(String infoFortName,String ContractName){
		//the two names must be without spaces
		
		if(infoFortName.contains(ContractName))
		return true;
	else if(ContractName.contains(infoFortName))
		return true;
	else		
		return false;
	}
	public static void updateJobDetailsData(Connection con,String userId,String simSerial,String typeId){
		try
	    {
	    	
	      Statement stat = con.createStatement();
	      
	      	  
	    	  
	    	 String strSql = "update IF_INFO_FORT_DATA set DATA_RECORD_TYPE_ID= "+typeId+" where SIM_SERIAL= "+simSerial;
		      System.out.println("The query is " + strSql);
		      stat.executeUpdate(strSql);
		      
		      
		      
		      /*strSql ="select DATA_RECORD_TYPE_NAME from IF_DATA_RECORD_TYPE where DATA_RECORD_TYPE_ID = "+typeId;
		      System.out.println("The query is " + strSql);
		      ResultSet rs=stat.executeQuery(strSql);
		      String typeName=null;
		      while(rs.next()){
		    	  typeName=rs.getString(1);
		    	  
		      }
		      strSql ="insert into IF_DATA_ACTIONS_HISTORY(DATA_ACTIONS_HISTORY_ID,SIM_SERIAL,USER_ID,TIME_STAMP,ACTION_UPDATE_TO) values(IF_ACTION_HISTORY_ID.nextval," +
		  		"'"+ simSerial + "'"+
		  		" ," +
		  		userId +
		  		",sysdate," +
		  		"'"+typeName +"'"+
		  		")";
		      System.out.println("The query is " + strSql);
		      stat.executeUpdate(strSql);*/
		      
		      
		      
		      
		      stat.close(); 
	      
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }finally{
	    	
	    }


	    
}
public static Vector <String> checkOnIFTemp(Connection con)
{
    String query="UPDATE IF_INFO_FORT_DATA_TEMP SET SIM_EXIST=1 WHERE SIM_SERIAL IN "
            + "(SELECT SIM_SERIAL FROM IF_INFO_FORT_DATA) ";
            
    DBUtil.executeSQL(query,con);

    String simErrorQuery="SELECT SIM_SERIAL FROM IF_INFO_FORT_DATA_TEMP WHERE SIM_EXIST=1";
    Vector <String> sims=DBUtil.executeQueryMultiValueString(simErrorQuery,"SIM_SERIAL");

    query="insert into IF_INFO_FORT_DATA (select * from IF_INFO_FORT_DATA_TEMP where SIM_EXIST is null)";
    DBUtil.executeSQL(query,con);    

    return sims;

}
}

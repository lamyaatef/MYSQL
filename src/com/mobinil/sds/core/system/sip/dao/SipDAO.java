package com.mobinil.sds.core.system.sip.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import oracle.jdbc.OraclePreparedStatement;



import com.mobinil.sds.core.system.commission.factor.model.FactorModel;
import com.mobinil.sds.core.system.commission.model.ChannelModel;
import com.mobinil.sds.core.system.commission.model.CommissionChannelModel;
import com.mobinil.sds.core.system.commission.model.CommissionModel;
import com.mobinil.sds.core.system.gn.querybuilder.domain.QueryBuilderEngine;
import com.mobinil.sds.core.system.gn.querybuilder.dto.QueryDTO;
import com.mobinil.sds.core.system.ma.model.ModuleModel;
import com.mobinil.sds.core.system.ma.model.PrivilageModel;
import com.mobinil.sds.core.system.sip.dto.sipDistHistoryCSVDTO;
import com.mobinil.sds.core.system.sip.dto.sipExportFileDTO;
import  com.mobinil.sds.core.system.sip.model.*;

import com.mobinil.sds.core.system.sip.model.SIPReportTypeModel;
//import com.mobinil.sds.core.system.ma.model.PrivilageModel;
//import om.mobinil.sds.core.system.ma.model.PrivilageStatusModel;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.web.interfaces.commission.CommissionInterfaceKey;
import com.mobinil.sds.web.interfaces.sip.SIPInterfaceKey;

public class SipDAO {
	 public SipDAO()
	  {
	  }
		 public static  String getsipReportCategoryName(Connection con,String categoryId)throws SQLException
	 {
	   Statement stmt = con.createStatement();
	   
	   
	 	  
	   String sqlString = "select SIP_REPORT_TYPE_CATEGORY_NAME from SIP_REPORT_TYPE_CATEGORY where SIP_REPORT_TYPE_CATEGORY_ID="+categoryId;
	   ResultSet rs = stmt.executeQuery(sqlString);
	   String ss = "";
	   
	   while(rs.next())
	   {
	 	  ss = rs.getString("SIP_REPORT_TYPE_CATEGORY_NAME");
	   }
	   
	   
	   rs.close();
	   stmt.close();
	   return ss;
	 }
	 
	 
	 
	 
	 
	 
	 
	 public static  String getChannelName(Connection con,String channelId)throws SQLException
	 {
	   Statement stmt = con.createStatement();
	   
	   
	 	  
	   String sqlString = "select SIP_REPORT_CHANNEL_NAME from SIP_REPORT_CHANNEL where SIP_REPORT_CHANNEL_ID="+channelId;
	   ResultSet rs = stmt.executeQuery(sqlString);
	   String ss = "";
	   
	   while(rs.next())
	   {
	 	  ss = rs.getString("SIP_REPORT_CHANNEL_NAME");
	   }
	   
	   
	   rs.close();
	   stmt.close();
	   return ss;
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public static Vector getAllSipReports(Connection con)
	   	{
		 Vector sopVec = new Vector();
		    SIPReportTypeModel reportTypeModel = null;
		   // StatusModel statusModel=null;
		    try
		    {
		      Statement stat = con.createStatement();
		    //  String strSql = "select item_id,item_name,status_name from ITEM, status where status.status_id=item.status_id";
		    //  String strSql="select module_id,module_name,module_desc,module_status_name  from ADM_MODULE,ADM_MODULE_STATUS where ADM_MODULE.module_status_id=ADM_MODULE_STATUS.module_status_id";
		      String strSql="select * from SIP_REPORT_TYPE ORDER BY report_type_name";

		    
		      //Utility.logger.debug(strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      while (res.next())
		      {
		    	  reportTypeModel=new SIPReportTypeModel();
		    	  reportTypeModel.setSipReportId(res.getString("REPORT_TYPE_ID"));
		          System.out.println(res.getString("REPORT_TYPE_ID"));

		          reportTypeModel.setSipReportName(res.getString("REPORT_TYPE_NAME"));
		          System.out.println(res.getString("REPORT_TYPE_NAME"));

		          //		          moduleModel.setModuleStatusId(res.getString("MODULE_STATUS_ID"));
		          sopVec.add(reportTypeModel);
		      }
		      stat.close();
		     
		    }
		    catch(Exception e)
		    {
		      e.printStackTrace();
		    } 
		    return sopVec;
		 
		 
		 
	   	}
	  public static SIPReportTypeModel selectReportType(Connection con,String reportTypeId)
	  {
	    Vector sopVec = new Vector();
	    SIPReportTypeModel reportTypeModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from SIP_REPORT_TYPE where REPORT_TYPE_ID = '"+reportTypeId+"'";
	      System.out.println(strSql);
	      //Utility.logger.debug("SQL--->"+strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while (res.next())
	      {
	    	  reportTypeModel = new SIPReportTypeModel(res);

	       // itemModel = new ItemModel();
	        //itemModel.setItemId(res.getString(itemModel.ITEM_ID));
	        //itemModel.setItemName(res.getString(itemModel.ITEM_NAME));
	        //itemModel.setStatusId(res.getString(itemModel.STATUS_ID));
	        //itemModel.setInvoiceDetailId(res.getString(invoiceModel.INVOICE_DETAIL_ID));
	        //sopVec.add(itemModel);
	      }
	      stat.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return reportTypeModel;
	  }
	  public static void editModule(Connection con,String moduleId,String moduleName,String moduleDescription,String statusModuleId)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "update ADM_MODULE set module_name='"+moduleName+"', module_desc='"+moduleDescription+"' , module_status_id='"+statusModuleId+"' where module_id='"+moduleId+"'";
	      System.out.println(strSql);

	   //  System.out.print(strSql);
	      stat.executeUpdate(strSql);
	      stat.close();
	      
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	  public static void deleteSipReportType (Connection con,String reportTypeId)
	  {
	    Vector sopVec = new Vector();
	    SIPReportTypeModel reportTypeModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql="delete  from SIP_REPORT_TYPE where report_type_id='"+reportTypeId+"'";
	      System.out.println(strSql);
	      ResultSet res = stat.executeQuery(strSql);
	   
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	  }
	 public static void updatesipReportStatus  (Connection con , String sipReportId ,String StatusID ,String userID)throws Exception
	 {
	    Statement stmt =  con.createStatement();
	    String sqlString = " UPDATE SIP_SAVED_REPORT  SET REPORT_STATUS_ID = "+ StatusID + " WHERE  REPORT_ID="+sipReportId+"";
	    int rows = stmt.executeUpdate(sqlString);
	    Long sipReportStatusID = Utility.getSequenceNextVal(con, "SEQ_SIP_REPORT_STATUS");
	    int sip_report_status_id = sipReportStatusID.intValue();
	    sqlString = "INSERT INTO SIP_REPORT_STATUS (SIP_REPORT_STATUS_ID, SIP_REPORT_ID,SIP_REPORT_STATUS_TYPE_ID,SIP_REPORT_STATUS_DATE,USER_ID)"+
	                " VALUES("+sip_report_status_id+","+sipReportId+","+StatusID+",sysdate,"+userID+")";
	      System.out.println("The updatesipReportStatus Query iss    " +sqlString);
	    rows = stmt.executeUpdate(sqlString);
	    
	    stmt.close();
	 }
	 
	 
	 public static void updatesipReportDetailStatus  (Connection con , String sipReportId ,String StatusID )throws Exception
     {
        
        String sqlString = " UPDATE SIP_REPORT_DETAIL  SET SIP_REPORT_STATUS_TYPE_ID = "+ StatusID + " WHERE  SIP_REPORT_DETAIL_ID="+sipReportId+"";
        
        DBUtil.executeSQL ( sqlString , con );
     }
	 
	 
	 
	 
	 
	  public static void updatesipReportSQLString(Connection con,String DataViewSQL , int sipReportID)throws Exception
	  {
	    
	    String sqlString = "UPDATE SIP_SAVED_REPORT SET  REPORT_DATA_VIEW_SQL =? WHERE  REPORT_ID = "+sipReportID;
	    OraclePreparedStatement ostat=(OraclePreparedStatement) con.prepareStatement(sqlString)   ; 
	    ostat.setStringForClob(1,DataViewSQL);
	          
	   ostat.executeUpdate();
	   ostat.close();
	  }
	 
	  
	  
	  public static boolean checksavedSipExistSip(Connection con,String sipReportID)
	  
	  {
		  
		  Statement stmt;
		  boolean flag =false;
		  try {
			stmt = con.createStatement();
	
		  

		  
		  
		  String sqlString = " select REPORT_ID from SIP_SAVED_REPORT where  report_id= ' "+sipReportID+" '  "+
    "  group by report_id " +
       " having count((select MANY_SAVED_REPORT_ID from SIP_SAVED_REPORT_SIP_REPORT where " +
       " MANY_SAVED_REPORT_ID=  ' "+sipReportID+" ' and MANY_SIP_REPORT_ID= '100'))>0 " ;
		  
		  
		 
		  System.out.println("the delete check query is " +sqlString );
		  ResultSet rs=stmt.executeQuery(sqlString);
		   if(rs.next())
		   {
			   flag=true;
		   }
		   
	
		  
		   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return  flag;
		  
		
	  }
	 
	 
	 
	 
	 
	 
	 
	 
	 public static  sipReportModel getsipReportByID(Connection con ,String sipREprotId)throws Exception
	 {
	   Statement stmt = con.createStatement();
	   String sqlString = "SELECT * FROM VW_sip_report_DETAIL  WHERE SIP_REPORT_DETAIL_ID = "+ sipREprotId;
	  System.out.println("The get  sip  report by  id   Query  issss "+sqlString);
	   
	   ResultSet rs = stmt.executeQuery(sqlString);
	   sipReportModel sipReportModel = null;   
	   if(rs.next())
	   {
	      sipReportModel = new sipReportModel(con,rs);
	   }
	   
	   rs.close();
	   stmt.close();
	   return sipReportModel;
	   }
	 
	 
	 
	 public static void deleteSavedSipReport(Connection con ,String sipREprotId)throws Exception
	 {
		 
		try
		{
		   Statement stmt = con.createStatement();
		   String sqlString = "DELETE  FROM  SIP_SAVED_REPORT  WHERE   REPORT_ID= "+sipREprotId;
		   
		   String sqlString1= " delete from sip_report_factor where SIP_REPORT_DETAIL_ID=" +sipREprotId;

 String sqlString2	="   delete from sip_report_item where SIP_REPORT_DETAIL_ID=" +sipREprotId ;
		  System.out.println("The delete saevd reportt  que5ry  is "+sqlString);
		  System.out.println("The delete saevd reportt  que5ry  is "+sqlString1);
		  System.out.println("The delete saevd reportt  que5ry  is "+sqlString2);
		  stmt.execute(sqlString);
		  stmt.execute(sqlString1);
		  stmt.execute(sqlString2);
		  stmt.close();
		
		} catch(Exception ex)
		
		{
			ex.printStackTrace();
			
			
		}
		 
	 
	 }
	 

	 public static  savedSipReportModel getSavedsipReportByID(Connection con ,String sipREprotId)throws Exception
	 {
	   Statement stmt = con.createStatement();
	 //  String sqlString = "SELECT * FROM VW_sip_report_DETAIL  WHERE SIP_REPORT_DETAIL_ID        = "+ sipREprotId;

	   
	 // String   sqlString="     SELECT  *   FROM  SIP_SAVED_REPORT,SIP_REPORT_TYPE,SIP_REPORT_QUARTER,SIP_REPORT_YEAR,SIP_REPORT_DATA_VIEW_TYPE,SIP_REPORT_STATUS,sip_report_status_type "+
      //     "where      SIP_SAVED_REPORT.REPORT_TYPE_ID  =  "+sipREprotId+ "     and      SIP_SAVED_REPORT.REPORT_TYPE_ID  =   SIP_REPORT_TYPE.REPORT_TYPE_ID     and  SIP_SAVED_REPORT.REPORT_QUARTER_ID=SIP_REPORT_QUARTER.QUARTER_ID and "+ 
       //    " SIP_SAVED_REPORT.DATA_VIEW_TYPE_ID=SIP_REPORT_DATA_VIEW_TYPE.DATA_VIEW_TYPE_ID  and  SIP_SAVED_REPORT.REPORT_YEAR_ID=SIP_REPORT_YEAR.YEAR_ID  and "+ 
       //    " SIP_SAVED_REPORT.REPORT_STATUS_ID=SIP_REPORT_STATUS.SIP_REPORT_STATUS_ID  and  SIP_REPORT_STATUS.SIP_REPORT_STATUS_TYPE_ID=SIP_REPORT_STATUS_TYPE.REPORT_STATUS_TYPE_ID " ;
           		
       
	
	  

	  String   sqlString=	"  SELECT  *   FROM "+
	 "  SIP_SAVED_REPORT,SIP_REPORT_TYPE,SIP_REPORT_YEAR,SIP_REPORT_DATA_VIEW_TYPE,SIP_REPORT_STATUS,sip_report_status_type"+ 
	  "  where "+
	   "      SIP_SAVED_REPORT.REPORT_ID  =   "+sipREprotId+ "  "+    
	    "      and   "+
	     "     SIP_SAVED_REPORT.REPORT_ID=SIP_REPORT_STATUS.SIP_REPORT_ID and " +
	      "       SIP_SAVED_REPORT.REPORT_TYPE_ID  =   SIP_REPORT_TYPE.REPORT_TYPE_ID " +
	        "   and  SIP_SAVED_REPORT.DATA_VIEW_TYPE_ID=SIP_REPORT_DATA_VIEW_TYPE.DATA_VIEW_TYPE_ID " +
	         "    and  SIP_SAVED_REPORT.REPORT_YEAR_ID=SIP_REPORT_YEAR.YEAR_ID  and  SIP_SAVED_REPORT.REPORT_STATUS_ID=SIP_REPORT_STATUS.SIP_REPORT_STATUS_TYPE_ID "+
	          "    and  SIP_REPORT_STATUS.SIP_REPORT_STATUS_TYPE_ID=SIP_REPORT_STATUS_TYPE.REPORT_STATUS_TYPE_ID    ORDER BY    SIP_SAVED_REPORT.REPORT_ID";

	  
	  
	  
	  
	  
	  
	  
	  
	  System.out.println("The get  saevd " +
		  		"" +
		  		"sip  report by  id   Query  issss "+sqlString);
	  
	  
	  
	   ResultSet rs = stmt.executeQuery(sqlString);
	   savedSipReportModel sipReportModel = null;   
	   if(rs.next())
	   {
	        sipReportModel = new savedSipReportModel(con,rs);
	   }
	   
	   rs.close();
	   stmt.close();
	   return sipReportModel;
	   }
	 
	 


	 
	 
	 
	 
	 public static  Vector getAllSavedsipReportByID(Connection con )throws Exception
	 {
	   Statement stmt = con.createStatement();
	 //  String sqlString = "SELECT * FROM VW_sip_report_DETAIL  WHERE SIP_REPORT_DETAIL_ID        = "+ sipREprotId;

	   
	 // String   sqlString="     SELECT  *   FROM  SIP_SAVED_REPORT,SIP_REPORT_TYPE,SIP_REPORT_QUARTER,SIP_REPORT_YEAR,SIP_REPORT_DATA_VIEW_TYPE,SIP_REPORT_STATUS,sip_report_status_type "+
      //     "where      SIP_SAVED_REPORT.REPORT_TYPE_ID  =  "+sipREprotId+ "     and      SIP_SAVED_REPORT.REPORT_TYPE_ID  =   SIP_REPORT_TYPE.REPORT_TYPE_ID     and  SIP_SAVED_REPORT.REPORT_QUARTER_ID=SIP_REPORT_QUARTER.QUARTER_ID and "+ 
       //    " SIP_SAVED_REPORT.DATA_VIEW_TYPE_ID=SIP_REPORT_DATA_VIEW_TYPE.DATA_VIEW_TYPE_ID  and  SIP_SAVED_REPORT.REPORT_YEAR_ID=SIP_REPORT_YEAR.YEAR_ID  and "+ 
       //    " SIP_SAVED_REPORT.REPORT_STATUS_ID=SIP_REPORT_STATUS.SIP_REPORT_STATUS_ID  and  SIP_REPORT_STATUS.SIP_REPORT_STATUS_TYPE_ID=SIP_REPORT_STATUS_TYPE.REPORT_STATUS_TYPE_ID " ;
           		
       
	Vector sipReportVector=new Vector();
	  

	  String   sqlString=	"  SELECT  *   FROM "+
	 "  SIP_SAVED_REPORT,SIP_REPORT_TYPE,SIP_REPORT_QUARTER,SIP_REPORT_YEAR,SIP_REPORT_DATA_VIEW_TYPE,SIP_REPORT_STATUS,sip_report_status_type"+ 
	  "  where "+
	 
	     "     SIP_SAVED_REPORT.REPORT_ID=SIP_REPORT_STATUS.SIP_REPORT_ID and " +
	      "       SIP_SAVED_REPORT.REPORT_TYPE_ID  =   SIP_REPORT_TYPE.REPORT_TYPE_ID " +     
	       "   and  SIP_SAVED_REPORT.REPORT_QUARTER_ID=SIP_REPORT_QUARTER.QUARTER_ID " +
	        "   and  SIP_SAVED_REPORT.DATA_VIEW_TYPE_ID=SIP_REPORT_DATA_VIEW_TYPE.DATA_VIEW_TYPE_ID " +
	         "    and  SIP_SAVED_REPORT.REPORT_YEAR_ID=SIP_REPORT_YEAR.YEAR_ID  and  SIP_SAVED_REPORT.REPORT_STATUS_ID=SIP_REPORT_STATUS.SIP_REPORT_STATUS_TYPE_ID "+
	          "    and  SIP_REPORT_STATUS.SIP_REPORT_STATUS_TYPE_ID=SIP_REPORT_STATUS_TYPE.REPORT_STATUS_TYPE_ID     ORDER BY   SIP_SAVED_REPORT.REPORT_ID";

	  
	  
	  
	  
	  
	  
	  
	  
	  System.out.println("The get  saevd " +
		  		"" +
		  		"sip  report by  id   Query  issss "+sqlString);
	  
	  
	  
	   ResultSet rs = stmt.executeQuery(sqlString);
	   savedSipReportModel sipReportModel =new savedSipReportModel() ;   
	
	   
	   while(rs.next())
	   {
		   sipReportModel=    new savedSipReportModel(con,rs);
		 
		
	        sipReportVector.add(sipReportModel);
	   }
	   
	   rs.close();
	   stmt.close();
	   return sipReportVector;
	   }
	 
	 
	 
	 
	 
	/*
 public static Vector searchPrivilages(Connection con,String moduleId)
	 {
		 
		 Vector sopVec = new Vector();
		 SIPReportModel privilageModel = null;
		   // StatusModel statusModel=null;
		    try
		    {
		      Statement stat = con.createStatement();
		    //  String strSql = "select item_id,item_name,status_name from ITEM, status where status.status_id=item.status_id";
		    //  String strSql="select module_id,module_name,module_desc,module_status_name  from ADM_MODULE,ADM_MODULE_STATUS where ADM_MODULE.module_status_id=ADM_MODULE_STATUS.module_status_id";
		      String strSql="select privilage_id,privilage_name,privilage_desc,module_name,privilage_status_name,privilage_action_name,order_value,privilage_target from ADM_PRIVILAGE, ADM_PRIVILAGE_STATUS ,ADM_MODULE where  ADM_PRIVILAGE.privilage_status_id=ADM_PRIVILAGE_STATUS.privilage_status_id and ADM_PRIVILAGE.MODULE_ID =ADM_MODULE.MODULE_ID and ADM_PRIVILAGE.MODULE_ID ="+moduleId;

		      System.out.println(strSql);
		      System.out.println("dsfdsfsdbdsf");
		      //Utility.logger.debug(strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      while (res.next())
		      {s
		    	  privilageModel=new PrivilageModel();
		    	  privilageModel.setPrivilageId(res.getString("PRIVILAGE_ID"));
		          privilageModel.setPrivilageName(res.getString("PRIVILAGE_NAME"));
		          privilageModel.setPrivilageDesc(res.getString("PRIVILAGE_DESC"));
		          privilageModel.setModuleName(res.getString("MODULE_NAME"));
		          privilageModel.setPrivilageStatusName(res.getString("PRIVILAGE_STATUS_NAME"));
		          privilageModel.setPrivilageActionName(res.getString("PRIVILAGE_ACTION_NAME"));
		          privilageModel.setOrderValue(res.getString("ORDER_VALUE"));
		          privilageModel.setPrivilageTarget(res.getString("PRIVILAGE_TARGET"));
		          //		          moduleModel.setModuleStatusId(res.getString("MODULE_STATUS_ID"));
		          sopVec.add(privilageModel);
		      }
		      stat.close();
		    }
		    catch(Exception e)
		    {
		      e.printStackTrace();
		    } 
		    return sopVec;
		 
	 }*/


	  public static void insertSipReport(Connection con,Long sipReportId,String sipReportName)
	  {
		  Statement stat = null ; 
	    try
	    {
	      stat = con.createStatement();
	      String strSql = "insert into SIP_REPORT_TYPE values("+sipReportId+",'"+sipReportName+"')";
	      System.out.print(strSql);
	      stat.executeUpdate(strSql);           
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    finally
	    {	 
	    	if (stat != null ) try{ stat.close();} catch (Exception e ) {}
	    }
	  }
	  

	  public static void editSipReport(Connection con,String sipReportId,String sipReportName)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "update SIP_REPORT_TYPE set report_type_name='"+sipReportName+"'  where report_type_id='"+sipReportId+"'";

	      stat.executeUpdate(strSql);
	      stat.close();
	      
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	  ////////////////////////////////////////////////

	/* public static Vector getSipReportsList(Connection con)
	  {
			 Vector sopVec = new Vector();
			 SIPReportModel moduleModel = null;
			   // StatusModel statusModel=null;
			    try
			    {
			      Statement stat = con.createStatement();
			      String strSql="select * from SIP_REPORT_TYPE ORDER BY sip_report_name";

			      ResultSet res = stat.executeQuery(strSql);
			      while (res.next())
			      {
			    	  sopVec.add(new SIPReportModel(res));

			      }
			      stat.close();
			    }
			    catch(Exception e)
			    {
			      e.printStackTrace();
			    } 
			    return sopVec;
		  
		  
		  
		  
		  
	  }*/

	  
	  
	  
	  
	  public static SIPModel SIPReportModelByID
      (Connection con,
             String sipReportId 
              )
      throws Exception
{
         Statement stmt = con.createStatement();
         
         StringBuffer sqlString = new StringBuffer("select * from VW_SIP_REPORT_DETAIL where 1=1");
         
         
         if(!sipReportId.equals(""))
            {
            sqlString.append (  " AND  SIP_REPORT_detail_ID = '" );
            sqlString.append (  sipReportId );
            sqlString.append (  "'" );
            
            }
         System.out.println(sqlString);
         ResultSet rs = stmt.executeQuery(sqlString.toString ( ));
         SIPModel sipReportModel = null;
         
         if(rs.next())
         {
            sipReportModel = new SIPModel(con,rs);
         
         }
         rs.close();
         stmt.close();
         return sipReportModel;
      }
	  
	  
	  
	  
	  
	  
	  
	  public static Vector searchSIPReportByFilter
	  (Connection con,
	         String sipReportId ,                      
	         String sipReportName,
	         String sipReportStatus,                             
	         String sipReportChannel,                               
	         String sipReportTypeCategory,          
	         String sipReportQuarter,                                   
	         String sipReportLabel,
	         String sipReportYear,
	         String sipReportLCID,                             
	         String sipReportNICom,                                 
	         String sipReportSOPID,
	         String userId )
	  throws Exception
{
	    
       
         
         
         
         
         
         
	     Statement stmt = con.createStatement();
         
         StringBuffer sqlString = new StringBuffer("select * from VW_SIP_REPORT_DETAIL where 1=1");
         
         
         if(!sipReportId.equals(""))
            {
            sqlString.append (  " AND  SIP_REPORT_detail_ID = '" );
            sqlString.append (  sipReportId );
            sqlString.append (  "'" );
            
            }
         
         if(!sipReportName.equals(""))
         {        
            sqlString.append(" And SIP_REPORT_NAME like '%"); 
            sqlString.append(sipReportName);
            sqlString.append("%'");
         }
         
         if(!sipReportStatus.equals(""))
         {
            sqlString.append (  " AND  SIP_REPORT_STATUS_TYPE_ID = '" );
            sqlString.append (  sipReportStatus );
            sqlString.append (  "'" );        
         }        
         
         
         if(!sipReportTypeCategory.equals(""))
         {
            sqlString.append (  " AND  SIP_REPORT_TYPE_CATEGORY_ID = '" );
            sqlString.append (  sipReportTypeCategory );
            sqlString.append (  "'" );  
         }
         
         if(!sipReportChannel.equals(""))
         {
            sqlString.append (  " AND  SIP_REPORT_CHANNEL_ID = '" );
            sqlString.append (  sipReportChannel );
            sqlString.append (  "'" );
            
         }
         else{         
         
            sqlString.append (  " AND  SIP_REPORT_CHANNEL_ID in (select  SIP_channel_id from  SIP_User_channel where  SIP_USER_ID='");
            sqlString.append (userId);
            sqlString.append ("')" );
         }
         
         if(!sipReportQuarter.equals(""))
         {
            sqlString.append (  " AND  SIP_REPORT_QUARTER_ID = '" );
            sqlString.append (  sipReportQuarter );
            sqlString.append (  "'" );        
         }  
         if(!sipReportYear.equals(""))
         {
            sqlString.append (  " AND  SIP_REPORT_YEAR = '" );
            sqlString.append (  sipReportYear );
            sqlString.append (  "'" );        
         }        
         if(!sipReportLabel.equals(""))
         {
            sqlString.append (  " AND  SIP_REPORT_LABEL = '" );
            sqlString.append (  sipReportLabel );
            sqlString.append (  "'" );        
         }        
         
         if(!sipReportLCID.equals(""))
         {
         sqlString.append (  " AND  SIP_REPORT_LC_ID Like ('%" );
         sqlString.append (  sipReportLCID );
         sqlString.append (  "%')" );
         
         }
         
         if(!sipReportNICom.equals(""))
         {
         sqlString.append (  " AND  SIP_REPORT_NI_ID Like ('%" );
         sqlString.append (  sipReportNICom );
         sqlString.append (  "%')" );
         
         }
         
         if(!sipReportSOPID.equals(""))
         {
         sqlString.append (  " AND  SIP_REPORT_SOP_ID Like ('%" );
         sqlString.append (  sipReportSOPID );
         sqlString.append (  "%')" );
         
         }
         
         

         
         
         
         
         sqlString.append( " ORDER BY  SIP_REPORT_CHANNEL_ID, SIP_REPORT_STATUS_TYPE_ID, SIP_REPORT_NAME");
         Utility.logger.debug(" SIP_REPORT Search SQL:  "+sqlString);
         
         System.out.println(sqlString);
         //System.out.println("Commission Search SQL: "+sqlString);
         //System.out.println("status is "+commissionStatus);
         
         ResultSet rs = stmt.executeQuery(sqlString.toString ( ));
         SIPModel sipReportModel = null;
         Vector sipSearchResults = new Vector();
         while(rs.next())
         {
            sipReportModel = new SIPModel(con,rs);
            sipSearchResults.add(sipReportModel);
         }
         rs.close();
         stmt.close();
         return sipSearchResults;
      }
	  public static Vector getSIPChannel(Connection con,String userId)
	  {
	      Vector sipChannelVec = new Vector();
	      try {
	            Statement stmt = con.createStatement();
	            StringBuffer sqlStringBuffer = new StringBuffer("select * from sip_report_channel");
	            sqlStringBuffer.append ( " where sip_report_channel_id in (select sip_channel_id from sip_user_channel");
	            sqlStringBuffer.append ( " where sip_user_id = '");
	            sqlStringBuffer.append ( userId);
	            sqlStringBuffer.append ( "')");
	            //System.out.println("The query isssssss " + sqlString);
	            ResultSet rs = stmt.executeQuery(sqlStringBuffer.toString ( ));
	            while(rs.next())
	             {
	               sipChannelVec.add(new SIPChannelModel(rs));
	             }
	            rs.close();
	            stmt.close();
	           }
	      catch (SQLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return sipChannelVec;
	  }
	  
	  public static  void insertSipChannels(Connection con,String channelName )throws SQLException
	  {
	    Statement stmt = con.createStatement();
	    String sqlString = "insert into SIP_REPORT_CHANNEL VALUES(SEQ_ID_SIP_CHANNEL.nextVal,'"+channelName+"')";
	     stmt.executeUpdate(sqlString);  
	    
	    stmt.close();  
	  }
	  public static void deleteSipChannel(Connection con,String channelId)
	  {
		  try
		  {
		  Statement stmt = con.createStatement();
		    String sqlString = "delete from SIP_REPORT_CHANNEL Where sip_REPORT_channel_id='"+channelId+"'";
		     stmt.executeUpdate(sqlString);  
		    
		    stmt.close(); 
		  }
		  catch(Exception e)
		  {}
		  
		  
	  }
	  public static void deleteSipConfig(Connection con,String configId)
	  {
		  try
		  {
		  Statement stmt = con.createStatement();
		    String sqlString = "delete from SIP_CONFIG_SETTING Where config_id='"+configId+"'";
		     stmt.executeUpdate(sqlString);  
		    
		    stmt.close(); 
		  }
		  catch(Exception e)
		  {}
		  
		  
	  }
	  public static  void updateSipChannels(Connection con,String channelId,String channelName )throws SQLException
	  {
	    Statement stmt = con.createStatement();
	    String sqlString = "update SIP_REPORT_CHANNEL set SIP_REPORT_CHANNEL_NAME='"+channelName+"' where SIP_REPORT_CHANNEL_ID="+channelId;
	     stmt.executeUpdate(sqlString);  
	    
	    stmt.close();                                                                                                                                                                                                                                                                                                                                                                                                                
	  }
	  public static  Vector getUserChannels(Connection con)throws SQLException
	  {
	    Statement stmt = con.createStatement();  
	    String sqlString = "select  sip_user_id,sip_channel_id from sip_User_channel";
	    ResultSet rs = stmt.executeQuery(sqlString);
	    Vector comUserChannelMap = new Vector();
	    SIPChannelModel cm = null;
	    while(rs.next())
	    {
	  	  cm = new SIPChannelModel();
	  	  Integer Key =new Integer( rs.getInt("sip_user_id"));	  
	  	  String value = rs.getString("sip_channel_id");	  
	  	  cm.setChannelId(Key);
	  	  cm.setChannelName(value);
	  	  comUserChannelMap.addElement(cm);
	    }
	    
	    
	    rs.close();
	    stmt.close();
	    return comUserChannelMap;
	  }
	  public static  void deleteUserChannels(Connection con,String userId)throws SQLException
	  {
	    Statement stmt = con.createStatement();
	    String sqlString = "delete from SIP_USER_CHANNEL where SIP_USER_ID="+userId;  
	     stmt.executeUpdate(sqlString);  
	    
	    stmt.close();  
	  }

	  public static  void insertUserChannels(Connection con,String userId,String channelId)throws SQLException
	  {
	    Statement stmt = con.createStatement();
	    String sqlString = "insert Into SIP_USER_CHANNEL values('"+userId+"','"+channelId+"')";  
	     stmt.executeUpdate(sqlString);  
	    
	    stmt.close();  
	  }

	  public static  Vector getSIPChannels(Connection con,boolean channelflag,String channelId)throws SQLException
	  {
	    Statement stmt = con.createStatement();
	    String chnlId = "";
	    if(channelflag)chnlId="and SIP_REPORT_CHANNEL_ID="+channelId;
	  	  
	    String sqlString = "select * from SIP_REPORT_CHANNEL where 1=1 " +chnlId+ " Order By SIP_REPORT_CHANNEL_NAME";
	    ResultSet rs = stmt.executeQuery(sqlString);
	    Vector comChannelVec = new Vector();
	    Boolean comHasPay ;
	    while(rs.next())
	    {
	  	  comChannelVec.addElement(new SIPChannelModel(rs));
	    }
	    
	    
	    rs.close();
	    stmt.close();
	    return comChannelVec;
	  }
	  
	  public static  void insertSipConfigs(Connection con,String configName)throws SQLException
	  {
	    Statement stmt = con.createStatement();
	    String sqlString = "insert into SIP_CONFIG_SETTING VALUES(SEQ_ID_SIP_CONFIG.nextVal,'"+configName+"')";
	     stmt.executeUpdate(sqlString);  
	    
	    stmt.close();  
	  }
	  public static  void updateSipConfigs(Connection con,String configId,String configName,String configType )throws SQLException
	  {
	    Statement stmt = con.createStatement();
	    String sqlString = "update SIP_CONFIG_SETTING set CONFIG_NAME='"+configName+"' where CONFIG_ID="+configId;
	     stmt.executeUpdate(sqlString);  
	    
	    stmt.close();                                                                                                                                                                                                                                                                                                                                                                                                                
	  }
	  
	  public static  Vector getSIPConfigs(Connection con,boolean channelflag,String channelId)throws SQLException
	  {
	    Statement stmt = con.createStatement();
	    String chnlId = "";
	    if(channelflag)chnlId="and CONFIG_ID="+channelId;
	  	  
	    String sqlString = "select * from SIP_CONFIG_SETTING where 1=1 " +chnlId+ " Order By CONFIG_NAME";
	    ResultSet rs = stmt.executeQuery(sqlString);
	    Vector comChannelVec = new Vector();
	    Boolean comHasPay ;
	    while(rs.next())
	    {
	  	  comChannelVec.addElement(new SIPConfigModel(rs));
	    }
	    
	    
	    rs.close();
	    stmt.close();
	    return comChannelVec;
	  }
	///////////////////////////////////////////////////////
	  public static  void insertSipReport(Connection con,String reportName,String reportType)throws SQLException
	  {
	    Statement stmt = con.createStatement();
	    String sqlString = "insert into SIP_SAVED_REPORT VALUES(SEQ_ID_SIP_SAVED_REPORT.nextVal,'"+reportName+"','"+reportType+"')";
	     stmt.executeUpdate(sqlString);  
	    
	    stmt.close();  
	  }
	  public static  void updateSipReport(Connection con,String reportId,String reportName,String reportType )throws SQLException
	  {
	    Statement stmt = con.createStatement();
	    String sqlString = "update SIP_SAVED_REPORT set REPORT_NAME='"+reportName+"', REPORT_TYPE='"+ reportType +"' where REPORT_ID="+reportId;
	     stmt.executeUpdate(sqlString);  
	    
	    stmt.close();                                                                                                                                                                                                                                                                                                                                                                                                                
	  }
	  
	  public static  Vector getSIPReports(Connection con,boolean channelflag,String reportId)throws SQLException
	  {
	    Statement stmt = con.createStatement();
	    String chnlId = "";
	    if(channelflag)chnlId="and REPORT_ID="+reportId;
	  	  
	    String sqlString = "select * from SIP_SAVED_REPORT where 1=1 " +chnlId+ " Order By REPORT_NAME";
	    ResultSet rs = stmt.executeQuery(sqlString);
	    Vector comChannelVec = new Vector();
	    Boolean comHasPay ;
	    while(rs.next())
	    {
	  	  comChannelVec.addElement(new SIPSavedReportModel(rs));
	    }
	    
	    
	    rs.close();
	    stmt.close();
	    return comChannelVec;
	  }
	 
	  
	  
	  
	  

	  
	  
	  
	  
	  
	  
	  
	  
	  
	  public static void deleteSipReport(Connection con,String reportId)
	  {
		  try
		  {
		  Statement stmt = con.createStatement();
		    String sqlString = "delete from SIP_SAVED_REPORT Where report_id='"+reportId+"'";
		     stmt.executeUpdate(sqlString);  
		    
		    stmt.close(); 
		  }
		  catch(Exception e)
		  {}
		  
		  
	  }
	  
	  
	  
	  public static void deleteSipReportDetailHistory(Connection con,String reportId)
      {
	     String sqlString = "delete from SIP_DIST_HISTORY Where SIP_REPORT_ID='"+reportId+"'";
	     DBUtil.executeSQL ( sqlString , con );
	     sqlString = "delete from  SIP_VALUES Where SIP_REPORT_ID='"+reportId+"'";
	     DBUtil.executeSQL ( sqlString , con );
	     
          
      } 
	  
//	  SipDAO.addNewsipReport(con,sipReprotName,
//            sipReprotDesc,sipReprotCategory,strUserID,sipReprotChannel,report_quarter,year,label,NI_commission_ids,line_coimmission_ids,sop_report_ids);
	  
	  public static int addNewsipReport(Connection con , String sipReportName, String sipReportDesc , 
            String commissionCategoryType , String userID ,String sipReportChannel ,         
           String report_quarter, String  yearr,String  label,String  NI_commission_ids,String  line_coimmission_ids,String  sop_report_ids)
    throws Exception
      {
      Statement stmt = con.createStatement();
      Long  sipReportID = Utility.getSequenceNextVal(con , "SEQ_SIP_REPORT_DETAIL");
      int sipReport_id = sipReportID.intValue();
     
      
      System.out.println("sip REPORT Name issssss "+sipReportName);
      
      
      
      String sqlString = "INSERT INTO SIP_REPORT_DETAIL " +
      		"(SIP_REPORT_DETAIL_ID , SIP_REPORT_NAME , SIP_REPORT_STATUS_TYPE_ID,"+
      " SIP_REPORT_TYPE_CATEGORY_ID,SIP_REPORT_DESCRIPTION,"+
      " SIP_REPORT_CHANNEL_ID,SIP_REPORT_CREATION_DATE,SIP_REPORT_QUARTER_ID," +
      "SIP_REPORT_YEAR ,SIP_REPORT_LABEL,SIP_REPORT_NI_ID,SIP_REPORT_LC_ID , SIP_REPORT_SOP_ID  )"+
      " VALUES("+sipReport_id+",'"+sipReportName+"','1','"+commissionCategoryType+"',"+
      "'"+sipReportDesc+"','"+sipReportChannel+"',sysdate,'"+report_quarter+"','"+yearr+"','"+label+"','"+NI_commission_ids+"','"+line_coimmission_ids+"','"+sop_report_ids+"')";
      System.out.println("Query for insert new sip Report is     "+sqlString);
      
      
      int temp = stmt.executeUpdate(sqlString);
      if (temp==0)
      { return 0;}
      else
      {
         if (temp>0)
         {
            
          String updatedNICommissionIDs =  linkSipReportForSavedReport(con,NI_commission_ids,sipReport_id+"","2");
          String updatedLineCommissionIDs =  linkSipReportForSavedReport(con,line_coimmission_ids,sipReport_id+"","1");
          String updatedSOPIDs =linkSipReportForSavedReport(con,sop_report_ids,sipReport_id+"","3");
          
          updateSIPReportAfterInsertion(con,updatedNICommissionIDs,updatedLineCommissionIDs,updatedSOPIDs,sipReport_id+"");
         }
      }
      
      
      
      
      stmt.close();
      return sipReport_id;
      }
	  
	  public static int updateSipReport(Connection con ,String sipReport_id, String sipReportName, String sipReportDesc , 
            String commissionCategoryType , String userID ,String sipReportChannel ,         
           String report_quarter, String  yearr,String  label,String  NI_commission_ids,String  line_coimmission_ids,String  sop_report_ids)
    throws Exception
      {
      
      
      StringBuffer sqlStringBuffer = new StringBuffer("update SIP_REPORT_DETAIL set" );
      
      sqlStringBuffer.append ( " SIP_REPORT_NAME = '" );
      sqlStringBuffer.append ( sipReportName );
      sqlStringBuffer.append ( "'," );
      
      sqlStringBuffer.append ( " SIP_REPORT_TYPE_CATEGORY_ID = '" );
      sqlStringBuffer.append ( commissionCategoryType );
      sqlStringBuffer.append ( "'," );
      
      sqlStringBuffer.append ( " SIP_REPORT_STATUS_TYPE_ID = '1'," );      
      
      
      
      sqlStringBuffer.append ( " SIP_REPORT_DESCRIPTION = '" );
      sqlStringBuffer.append ( sipReportDesc );
      sqlStringBuffer.append ( "'," );
      
      
      sqlStringBuffer.append ( " SIP_REPORT_CHANNEL_ID = '" );
      sqlStringBuffer.append ( sipReportChannel );
      sqlStringBuffer.append ( "'," );
      
      
      sqlStringBuffer.append ( " SIP_REPORT_QUARTER_ID = '" );
      sqlStringBuffer.append ( report_quarter );
      sqlStringBuffer.append ( "'," );
      
      
      sqlStringBuffer.append ( " SIP_REPORT_YEAR = '" );
      sqlStringBuffer.append ( yearr );
      sqlStringBuffer.append ( "'," );
      
      sqlStringBuffer.append ( " SIP_REPORT_LABEL = '" );
      sqlStringBuffer.append ( label );
      sqlStringBuffer.append ( "'," );
      
      
      sqlStringBuffer.append ( " SIP_REPORT_NI_ID = '" );
      sqlStringBuffer.append ( NI_commission_ids );
      sqlStringBuffer.append ( "'," );
      
      sqlStringBuffer.append ( " SIP_REPORT_LC_ID = '" );
      sqlStringBuffer.append ( line_coimmission_ids );
      sqlStringBuffer.append ( "'," );
      
      sqlStringBuffer.append ( " SIP_REPORT_SOP_ID = '" );
      sqlStringBuffer.append ( sop_report_ids );
      sqlStringBuffer.append ( "'" );
      
      sqlStringBuffer.append(" where SIP_REPORT_DETAIL_ID='");
      sqlStringBuffer.append(sipReport_id);
      sqlStringBuffer.append("'");
      
      System.out.println("Query for update new sip Report is     "+sqlStringBuffer);
      
      Statement st = con.createStatement ( );
      
      int result = st.executeUpdate ( sqlStringBuffer.toString ( ) );
      
      if (result>0)
      {
         deleteSipReportForSavedReport(con,sipReport_id);
         String updatedNICommissionIDs = linkSipReportForSavedReport(con,NI_commission_ids,sipReport_id,"2");
         String updatedLineCommissionIDs = linkSipReportForSavedReport(con,line_coimmission_ids,sipReport_id,"1");
         String updatedSOPIDs = linkSipReportForSavedReport(con,sop_report_ids,sipReport_id,"3");
         
         updateSIPReportAfterInsertion(con,updatedNICommissionIDs,updatedLineCommissionIDs,updatedSOPIDs,sipReport_id);
      }
      
      return result;
      }
	  
	  public static void updateSIPReportAfterInsertion(Connection con,String updatedNICommissionIDs,String updatedLineCommissionIDs,String updatedSOPIDs,String sipReportId) throws SQLException
	  {
	    String sql = "update sip_report_detail set SIP_REPORT_NI_ID ='"+updatedNICommissionIDs+"', SIP_REPORT_LC_ID ='"+updatedLineCommissionIDs+"', SIP_REPORT_SOP_ID='"+updatedSOPIDs+"' where SIP_REPORT_DETAIL_ID='"+sipReportId+"' " ;
	    DBUtil.executeSQL ( sql , con ) ;
	  }
	  
	  public static String linkSipReportForSavedReport(Connection con,String ids,String sipReport,String savedReportTypeId) throws SQLException
	  {
	     String sql = "";
	     String returnedString="";
	     String idsArray []= ids.split ( "," );
	     int idsArraySize = 0;
	     
	     if (idsArray.length  > 3) idsArraySize = 3;
	     else idsArraySize = idsArray.length; 
	     
	     for ( int i = 0 ; i < idsArraySize ; i ++ )
         {
            String string = idsArray[i];
            if(string!=null && string.compareTo ( "" )!=0)
            {
               sql="select REPORT_ID from SIP_SAVED_REPORT where  report_id='"+string+"' and report_type_id='"+savedReportTypeId+"'";
               sql+=" group by report_id";
               sql+=" having count((select MANY_SAVED_REPORT_ID from SIP_SAVED_REPORT_SIP_REPORT where";
               sql+=" MANY_SAVED_REPORT_ID= '"+string+"' and MANY_SIP_REPORT_ID= '"+sipReport+"'))=0";
               
               System.out.println ("sql in testQuery is is "+sql);
               
               if (DBUtil.executeSQLExistCheck ( sql , con )){
               
               sql = "insert into SIP_SAVED_REPORT_SIP_REPORT values ('"+sipReport+"','"+string+"',(nvl ((select max(many_order) from SIP_SAVED_REPORT_SIP_REPORT where MANY_SIP_REPORT_ID='"+sipReport+"' and MANY_SAVED_REPORT_ID='"+string+"'),'0')+1))";               
               
               System.out.println ("sql in linkSipReportForSavedReport is "+sql);
               System.out.println ("sipReport in linkSipReportForSavedReport is "+sipReport);
               System.out.println ("string in linkSipReportForSavedReport is "+string);
               System.out.println ("order in linkSipReportForSavedReport is "+(i+1));
               
               returnedString+=string+",";
               DBUtil.executeSQL ( sql , con ) ;
               }
            }
            
         }
	     if (returnedString.compareTo ( "" )!=0&&returnedString.contains ( "," ) )
	     {
	        returnedString = returnedString.substring ( 0,(returnedString.length ( ) -1));
	     }
	     return returnedString;
	  }
	  
	  
	  public static void deleteSipReportForSavedReport(Connection con,String sipReport) throws SQLException
      {
         String sql = "";
         
               sql = "delete from SIP_SAVED_REPORT_SIP_REPORT where MANY_SIP_REPORT_ID='"+sipReport+"'";
               DBUtil.executeSQL ( sql , con ) ;
               sql = "delete from SIP_DIST_HISTORY where SIP_REPORT_ID='"+sipReport+"'";
               DBUtil.executeSQL ( sql , con ) ;
               sql = "delete from SIP_DIST_HISTORY_CSV where SIP_REPORT_ID='"+sipReport+"'";
               DBUtil.executeSQL ( sql , con ) ;
         
         
      }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  public static int addNewSavedReport(Connection con , String sipReportName,
	        
            String userID , String dataViewID,String dataViewType , String report_quarter_id, String  yearr,String reportTypeId,String monthId)
     throws Exception
{
Statement stmt = con.createStatement();
Long  sipReportID = Utility.getSequenceNextVal(con , "SEQ_SIP_SAVED_REPORT");
int sipReport_id = sipReportID.intValue();
String strQuery = "select * from ADH_DATA_VIEW where DATA_VIEW_ID =  "+dataViewID+
" and data_view_version = (select max(data_view_version) from ADH_DATA_VIEW where DATA_VIEW_ID = "+dataViewID+" )";
ResultSet res = stmt.executeQuery(strQuery);
String strDataViewQuery="";
if(res.next())
{
int intDataViewId = res.getInt("DATA_VIEW_ID");

QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine();
Vector vec =new Vector();
QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO(con,intDataViewId,vec);
strDataViewQuery = queryBuilderEngine.constructQuerySQL(queryDTO);
Utility.logger.debug(strDataViewQuery);
}
res.close();
strDataViewQuery = strDataViewQuery.replaceAll("'","''");
int dataViewTypeID = 0;
Utility.logger.debug(dataViewType);
if(dataViewType.equals(SIPInterfaceKey.DATA_VIEW_TYPE_A))
dataViewTypeID = 1;
if(dataViewType.equals(SIPInterfaceKey.DATA_VIEW_TYPE_B))
dataViewTypeID = 2;
sipReportName = sipReportName;

System.out.println("sip REPORT Name issssss "+sipReportName);


/*
String sqlString = "INSERT INTO SIP_REPORT_DETAIL (SIP_REPORT_DETAIL_ID , SIP_REPORT_NAME , SIP_REPORT_STATUS_TYPE_ID,"+
" SIP_REPORT_TYPE_CATEGORY_ID, SIP_REPORT_START_DATE , SIP_REPORT_END_DATE , SIP_REPORT_DESCRIPTION,"+
" SIP_REPORT_DATA_VIEW_ID ,SIP_REPORT_DATA_VIEW_SQL , DATA_VIEW_TYPE_ID , SIP_REPORT_CHANNEL_ID,SIP_REPORT_CREATION_DATE,SIP_REPORT_QUARTER_ID,SIP_REPORT_YEAR ,SIP_REPORT_LABEL,SIP_REPORT_NI_ID,SIP_REPORT_LC_ID , SIP_REPORT_SOP_ID  )"+


" VALUES("+sipReport_id+",'"+sipReportName+"','1','"+commissionCategoryType+"',TO_DATE('"+sipReportStartDate+"','dd/mm/yyyy'),"+
"TO_DATE('"+sipReportEndDate+"','dd/mm/yyyy'),'"+sipReportDesc+"',"+dataViewID+",?, "+dataViewTypeID+",'"+sipReportChannel+"',sysdate,'"+report_quarter+"','"+yearr+"','"+label+"','"+NI_commission_ids+"','"+line_coimmission_ids+"','"+sop_report_ids+"')";

*/


String sqlString =  "INSERT  INTO  SIP_SAVED_REPORT   (REPORT_ID,REPORT_NAME,REPORT_TYPE_ID,"+ /*REPORT_QUARTER_ID,*/"REPORT_DATA_VIEW_ID,REPORT_YEAR_ID,REPORT_DATA_VIEW_SQL,DATA_VIEW_TYPE_ID,REPORT_STATUS_ID,MONTH_ID) " +
" VALUES("+sipReport_id+",'"+sipReportName+"','"+reportTypeId+"',"/*'"+report_quarter_id+"',"*/+
 " '"+dataViewID+"' ,'"+yearr+"', ?, '"+dataViewTypeID+"','"+1+"','"+monthId+"')";




System.out.println("Query for insert new SAEVD sip Report is     "+sqlString);

//;report_quarter,   yearr, label,  NI_commission_ids, line_coimmission_ids  sop_report_ids)

//SIP_REPORT_QUARTER_ID        
//SIP_REPORT_YEAR              
//SIP_REPORT_LABEL             
//SIP_REPORT_NI_ID             
//SIP_REPORT_LC_ID             
//SIP_REPORT_SOP_ID  





oracle.jdbc.OraclePreparedStatement   pstmt = (oracle.jdbc.OraclePreparedStatement)con.prepareStatement(sqlString);

System.out.println("The Data View Query  issssssssssss "+strDataViewQuery);
pstmt.setStringForClob(1,strDataViewQuery);
//System.out.println("BEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEESY   "  +pstmt.toString() );



pstmt.execute();
pstmt.close();



//Utility.logger.debug(sqlString);
//int rows = stmt.executeUpdate(sqlString);
Long sipReportStatusID = Utility.getSequenceNextVal(con, "SEQ_SIP_REPORT_STATUS");
int sipReport_status_id = sipReportStatusID.intValue();
sqlString = "INSERT INTO SIP_REPORT_STATUS (SIP_REPORT_STATUS_ID, SIP_REPORT_ID,SIP_REPORT_STATUS_TYPE_ID,SIP_REPORT_STATUS_DATE,USER_ID)"+
" VALUES("+sipReport_status_id+","+sipReport_id+",1,TO_DATE(SYSDATE,'dd/mm/yyyy'),"+userID+")";

System.out.println("The sql String  Report status issssssssssssssssss"+ sqlString);

stmt.executeUpdate(sqlString);



stmt.close();
return sipReport_id;
}
     
	  
	/*  public static int addNewsipReport(Connection con , String sipReportName, String sipReportStartDate,
              String sipReportEndDate , String sipReportDesc , 
              String commissionCategoryType , String userID , String dataViewID,String dataViewType ,String sipReportChannel ,         
             String report_quarter, String  yearr,String  label,String  NI_commission_ids,String  line_coimmission_ids,String  sop_report_ids)
	  throws Exception
{
Statement stmt = con.createStatement();
Long  sipReportID = Utility.getSequenceNextVal(con , "SEQ_SIP_REPORT_DETAIL");
int sipReport_id = sipReportID.intValue();
String strQuery = "select * from ADH_DATA_VIEW where DATA_VIEW_ID =  "+dataViewID+
" and data_view_version = (select max(data_view_version) from ADH_DATA_VIEW where DATA_VIEW_ID = "+dataViewID+" )";
ResultSet res = stmt.executeQuery(strQuery);
String strDataViewQuery="";
if(res.next())
{
int intDataViewId = res.getInt("DATA_VIEW_ID");

QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine();
Vector vec =new Vector();
QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO(con,intDataViewId,vec);
strDataViewQuery = queryBuilderEngine.constructQuerySQL(queryDTO);
Utility.logger.debug(strDataViewQuery);
}
res.close();
strDataViewQuery = strDataViewQuery.replaceAll("'","''");
int dataViewTypeID = 0;
Utility.logger.debug(dataViewType);
if(dataViewType.equals(SIPInterfaceKey.DATA_VIEW_TYPE_A))
dataViewTypeID = 1;
if(dataViewType.equals(SIPInterfaceKey.DATA_VIEW_TYPE_B))
dataViewTypeID = 2;
sipReportName = sipReportName+sipReport_id;

System.out.println("sip REPORT Name issssss "+sipReportName);



String sqlString = "INSERT INTO SIP_REPORT_DETAIL (SIP_REPORT_DETAIL_ID , SIP_REPORT_NAME , SIP_REPORT_STATUS_TYPE_ID,"+
" SIP_REPORT_TYPE_CATEGORY_ID, SIP_REPORT_START_DATE , SIP_REPORT_END_DATE , SIP_REPORT_DESCRIPTION,"+
" SIP_REPORT_DATA_VIEW_ID ,SIP_REPORT_DATA_VIEW_SQL , DATA_VIEW_TYPE_ID , SIP_REPORT_CHANNEL_ID,SIP_REPORT_CREATION_DATE,SIP_REPORT_QUARTER_ID,SIP_REPORT_YEAR ,SIP_REPORT_LABEL,SIP_REPORT_NI_ID,SIP_REPORT_LC_ID , SIP_REPORT_SOP_ID  )"+


" VALUES("+sipReport_id+",'"+sipReportName+"','1','"+commissionCategoryType+"',TO_DATE('"+sipReportStartDate+"','dd/mm/yyyy'),"+
"TO_DATE('"+sipReportEndDate+"','dd/mm/yyyy'),'"+sipReportDesc+"',"+dataViewID+",?, "+dataViewTypeID+",'"+sipReportChannel+"',sysdate,'"+report_quarter+"','"+yearr+"','"+label+"','"+NI_commission_ids+"','"+line_coimmission_ids+"','"+sop_report_ids+"')";
System.out.println("Query for insert new sip Report is     "+sqlString);
 
//;report_quarter,   yearr, label,  NI_commission_ids, line_coimmission_ids  sop_report_ids)

//SIP_REPORT_QUARTER_ID        
//SIP_REPORT_YEAR              
//SIP_REPORT_LABEL             
//SIP_REPORT_NI_ID             
//SIP_REPORT_LC_ID             
//SIP_REPORT_SOP_ID  





oracle.jdbc.OraclePreparedStatement   pstmt = (oracle.jdbc.OraclePreparedStatement)con.prepareStatement(sqlString);

System.out.println("The Data View Query  issssssssssss "+strDataViewQuery);
pstmt.setStringForClob(1,strDataViewQuery);
//System.out.println("BEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEESY   "  +pstmt.toString() );



pstmt.execute();
pstmt.close();



//Utility.logger.debug(sqlString);
//int rows = stmt.executeUpdate(sqlString);
Long sipReportStatusID = Utility.getSequenceNextVal(con, "SEQ_SIP_REPORT_STATUS");
int sipReport_status_id = sipReportStatusID.intValue();
sqlString = "INSERT INTO SIP_REPORT_STATUS (SIP_REPORT_STATUS_ID, SIP_REPORT_DETAIL_ID,SIP_REPORT_STATUS_TYPE_ID,SIP_REPORT_STATUS_DATE,USER_ID)"+
" VALUES("+sipReport_status_id+","+sipReport_id+",1,TO_DATE(SYSDATE,'dd/mm/yyyy'),"+userID+")";

System.out.println("The sql String  Report status issssssssssssssssss"+ sqlString);

stmt.executeUpdate(sqlString);



stmt.close();
return sipReport_id;
}
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  /////////////////////////////////
	  
	  
	  
	  
	  
	  
	  
	  
	  public static int addNewSavedReport(Connection con , String sipReportName,
 
             String userID , String dataViewID,String dataViewType , String report_quarter_id, String  yearr,String reportQuarter)
	  throws Exception
{
Statement stmt = con.createStatement();
Long  sipReportID = Utility.getSequenceNextVal(con , "SEQ_SIP_SAVED_REPORT");
int sipReport_id = sipReportID.intValue();
String strQuery = "select * from ADH_DATA_VIEW where DATA_VIEW_ID =  "+dataViewID+
" and data_view_version = (select max(data_view_version) from ADH_DATA_VIEW where DATA_VIEW_ID = "+dataViewID+" )";
ResultSet res = stmt.executeQuery(strQuery);
String strDataViewQuery="";
if(res.next())
{
int intDataViewId = res.getInt("DATA_VIEW_ID");

QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine();
Vector vec =new Vector();
QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO(con,intDataViewId,vec);
strDataViewQuery = queryBuilderEngine.constructQuerySQL(queryDTO);
Utility.logger.debug(strDataViewQuery);
}
res.close();
strDataViewQuery = strDataViewQuery.replaceAll("'","''");
int dataViewTypeID = 0;
Utility.logger.debug(dataViewType);
if(dataViewType.equals(SIPInterfaceKey.DATA_VIEW_TYPE_A))
dataViewTypeID = 1;
if(dataViewType.equals(SIPInterfaceKey.DATA_VIEW_TYPE_B))
dataViewTypeID = 2;
sipReportName = sipReportName+sipReport_id;

System.out.println("sip REPORT Name issssss "+sipReportName);


/*
String sqlString = "INSERT INTO SIP_REPORT_DETAIL (SIP_REPORT_DETAIL_ID , SIP_REPORT_NAME , SIP_REPORT_STATUS_TYPE_ID,"+
" SIP_REPORT_TYPE_CATEGORY_ID, SIP_REPORT_START_DATE , SIP_REPORT_END_DATE , SIP_REPORT_DESCRIPTION,"+
" SIP_REPORT_DATA_VIEW_ID ,SIP_REPORT_DATA_VIEW_SQL , DATA_VIEW_TYPE_ID , SIP_REPORT_CHANNEL_ID,SIP_REPORT_CREATION_DATE,SIP_REPORT_QUARTER_ID,SIP_REPORT_YEAR ,SIP_REPORT_LABEL,SIP_REPORT_NI_ID,SIP_REPORT_LC_ID , SIP_REPORT_SOP_ID  )"+


" VALUES("+sipReport_id+",'"+sipReportName+"','1','"+commissionCategoryType+"',TO_DATE('"+sipReportStartDate+"','dd/mm/yyyy'),"+
"TO_DATE('"+sipReportEndDate+"','dd/mm/yyyy'),'"+sipReportDesc+"',"+dataViewID+",?, "+dataViewTypeID+",'"+sipReportChannel+"',sysdate,'"+report_quarter+"','"+yearr+"','"+label+"','"+NI_commission_ids+"','"+line_coimmission_ids+"','"+sop_report_ids+"')";

*/
/*

String sqlString =  "INSERT  INTO  SIP_SAVED_REPORT   (REPORT_ID,REPORT_NAME,REPORT_TYPE,REPORT_QUARTER_ID,REPORT_DATA_VIEW_ID,REPORT_YEAR_ID,REPORT_DATA_VIEW_SQL,DATA_VIEW_TYPE_ID,REPORT_STATUS_ID) " +
 " VALUES("+sipReport_id+",'"+sipReportName+"','"+report_quarter_id+"', "+
  " '"+dataViewID+"' ,'"+yearr+"',   ,?, '"+dataViewTypeID+"','"+1+"')";




System.out.println("Query for insert new SAEVD sip Report is     "+sqlString);
 
//;report_quarter,   yearr, label,  NI_commission_ids, line_coimmission_ids  sop_report_ids)

//SIP_REPORT_QUARTER_ID        
//SIP_REPORT_YEAR              
//SIP_REPORT_LABEL             
//SIP_REPORT_NI_ID             
//SIP_REPORT_LC_ID             
//SIP_REPORT_SOP_ID  





oracle.jdbc.OraclePreparedStatement   pstmt = (oracle.jdbc.OraclePreparedStatement)con.prepareStatement(sqlString);

System.out.println("The Data View Query  issssssssssss "+strDataViewQuery);
pstmt.setStringForClob(1,strDataViewQuery);
//System.out.println("BEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEESY   "  +pstmt.toString() );



pstmt.execute();
pstmt.close();



//Utility.logger.debug(sqlString);
//int rows = stmt.executeUpdate(sqlString);
Long sipReportStatusID = Utility.getSequenceNextVal(con, "SEQ_SIP_REPORT_STATUS");
int sipReport_status_id = sipReportStatusID.intValue();
sqlString = "INSERT INTO SIP_REPORT_STATUS (SIP_REPORT_STATUS_ID, SIP_REPORT_ID,SIP_REPORT_STATUS_TYPE_ID,SIP_REPORT_STATUS_DATE,USER_ID)"+
" VALUES("+sipReport_status_id+","+sipReport_id+",1,TO_DATE(SYSDATE,'dd/mm/yyyy'),"+userID+")";

System.out.println("The sql String  Report status issssssssssssssssss"+ sqlString);

stmt.executeUpdate(sqlString);



stmt.close();
return sipReport_id;
}*/
	  
	  
	  
	  
	  //////////////////////////////////
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  public static Vector getSIPFactors(Connection con , String commissionID)throws Exception
      {
        Statement stmt = con.createStatement();
        Vector commissionFactors = new Vector();
        FactorModel factorModel = null;        
        String sqlString = "SELECT  SIP_REPORT_FACTOR_ID , SIP_REPORT_FACTOR_NAME ,SIP_REPORT_FACTOR_VALUE"+
                           ",SIP_REPORT_ITEM_NAME,SIP_REPORT_ITEM_AMOUNT,DCM_ID ,DCM_NAME,DCM_CODE,SIP_REPORT_NAME,SIP_REPORT_TYPE_NAME,SIP_REPORT_TYPE_CATEGORY_NAME "+
                           "FROM VW_SIP_REPORT_ITEM_FACTOR WHERE SIP_REPORT_DETAIL_ID ='"+commissionID+"' ORDER BY DCM_ID , SIP_REPORT_FACTOR_NAME" ;
       Utility.logger.debug(sqlString);
       ResultSet rs = stmt.executeQuery(sqlString);

       while(rs.next())
       {
        factorModel = new FactorModel();
        factorModel.setCommissionFactorID(rs.getInt("SIP_REPORT_FACTOR_ID"));
        factorModel.setCommissionFactorName(rs.getString("SIP_REPORT_FACTOR_NAME")); 
        factorModel.setCommissionFactorValue(rs.getDouble("SIP_REPORT_FACTOR_VALUE"));
        factorModel.setCommissionItemName(rs.getString("SIP_REPORT_ITEM_NAME"));
        factorModel.setCommissionItemAmount(rs.getInt("SIP_REPORT_ITEM_AMOUNT"));
        factorModel.setCommissionDCMID(rs.getInt("DCM_ID"));
        factorModel.setCommissionDCMName(rs.getString("DCM_NAME"));
        factorModel.setCommissionDCMCode(rs.getString("DCM_CODE"));
        factorModel.setCommissionName(rs.getString("SIP_REPORT_NAME"));
        factorModel.setCommissionType(rs.getString("SIP_REPORT_TYPE_NAME"));
        factorModel.setCommissionCategory(rs.getString("SIP_REPORT_TYPE_CATEGORY_NAME"));
//      factorModel.setCommissionID(Integer.parseInt(commissionID));
        commissionFactors.add(factorModel);
        factorModel=null;
       }
       return commissionFactors;
      }
       
	  
	  
	  
	  
	  
	  
	  
	  public static void setFactorValue(Connection con , String factorID ,String sipReportId,String factorValue )throws Exception
	  {
	    Statement stmt = con.createStatement();
	    String sqlString = "UPDATE sip_report_FACTOR SET sip_report_FACTOR_VALUE = "+factorValue+" WHERE "+
	                       " sip_report_FACTOR_ID = "+sipReportId;
	   int rows = stmt.executeUpdate(sqlString);
	   Utility.logger.debug(sqlString+","+rows);
	   System.out.println("The sql   for   inserting    value  isssssss "+sqlString);
	   stmt.close();
	   }
	  public static Vector getallfiles(Connection con) {
         System.out.println();
         Vector vec = new Vector();
         Statement stat = null;
         ResultSet res = null;
         try {
             stat = con.createStatement();
             String strSql = "select FILE_ID, TO_CHAR(UPLOADED_DATE,'MM/DD/YYYY HH24:MI:SS') UPLOADED_DATE, REPORT_YEAR, REPORT_QUARTAR, REPORT_TYPE, USER_ID,YEAR_ID,YEAR_NAME from SIP_FILE_DETAIL SFD , SIP_REPORT_YEAR SRY where SFD.REPORT_YEAR = SRY.YEAR_ID ORDER BY  YEAR_NAME,REPORT_QUARTAR";
             
             
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
   
   
   public static void deleteFile(Connection con,String fileId,String typeId) {
      
      String sql = "delete from SIP_FILE_DETAIL where FILE_ID='"+fileId+"'";
      DBUtil.executeSQL ( sql , con ) ;
      
      sql = "delete from SIP_UPLOADED_LISTS where FILE_ID='"+fileId+"'";
      
      
      DBUtil.executeSQL ( sql , con ) ;
      
  

}
	  
   public static void changeSipReportStatus(Connection con,String sipId,String statusType) {
      String sql = "update SIP_REPORT_DETAIL set SIP_REPORT_STATUS_TYPE_ID='"+statusType+"' where SIP_REPORT_DETAIL_ID='"+sipId+"'";
//      System.out.println ("sql to change status isss " );
//      System.out.println (sql);
      
      DBUtil.executeSQL  (  sql ,con );
   }
   public static  SIPModel getSipReportByID(Connection con ,String sipReportID)throws Exception
   {
     Statement stmt = con.createStatement();
     String sqlString = "SELECT * FROM VW_SIP_REPORT_DETAIL WHERE SIP_REPORT_DETAIL_ID = "+ sipReportID;
     ResultSet rs = stmt.executeQuery(sqlString);
     SIPModel sipModel = null;   
     if(rs.next())
     {
        sipModel = new SIPModel(con,rs);
     }
     
     rs.close();
     stmt.close();
     return sipModel;
     } 
   public static Vector getSipFactorValues(Connection con , String sipID)throws Exception
   {
     Statement stmt = con.createStatement();
     Vector commissionFactors = new Vector();
     FactorModel factorModel = null;
     
     
     
     String sqlString = "SELECT  SIP_REPORT_FACTOR_ID , SIP_REPORT_FACTOR_NAME ,SIP_REPORT_FACTOR_VALUE"+
                        " FROM SIP_REPORT_FACTOR WHERE SIP_REPORT_DETAIL_ID ="+sipID+"  ORDER BY SIP_REPORT_FACTOR_NAME" ;
     System.out.println (sqlString);
    ResultSet rs = stmt.executeQuery(sqlString);
    Utility.logger.debug(sqlString);
    while(rs.next())
    {
     factorModel = new FactorModel();
     factorModel.setCommissionFactorID(rs.getInt("SIP_REPORT_FACTOR_ID"));
     factorModel.setCommissionFactorName(rs.getString("SIP_REPORT_FACTOR_NAME")); 
     factorModel.setCommissionFactorValue(rs.getDouble("SIP_REPORT_FACTOR_VALUE"));
     factorModel.setCommissionID(Integer.parseInt(sipID));
     commissionFactors.add(factorModel);
     factorModel=null;
    }
    return commissionFactors;
   }
   
   public static Vector searchSIPFileByFilter(Connection con,String year,String quarter,String type,String filterUserId )
	  throws Exception
{
	    
    
      
      
      
      

	     Statement stmt = con.createStatement();
      
      StringBuffer sqlString = new StringBuffer("select * from SIP_FILE_DETAIL SFD,SIP_REPORT_YEAR SRY where SFD.REPORT_YEAR = SRY.YEAR_ID  and 1=1");
      
      
      if(!year.equals(""))
         {
         sqlString.append (  " And  REPORT_YEAR = '" );
         sqlString.append (  year );
         sqlString.append (  "'" );
         
         }
      
      if(!quarter.equals(""))
      {        
         sqlString.append(" And REPORT_QUARTAR ='" ); 
         sqlString.append(quarter);
         sqlString.append("'");
      }
      if(!type.equals(""))
      {        
         sqlString.append(" And REPORT_TYPE ='" ); 
         sqlString.append(type);
         sqlString.append("'");
      }
       
      if(!filterUserId.equals(""))
      {        
         sqlString.append(" And USER_ID ='" ); 
         sqlString.append(filterUserId);
         sqlString.append("'");
      }
      
      
      
      

      
      
      
      
     // sqlString.append( " ORDER BY  SIP_REPORT_CHANNEL_ID, SIP_REPORT_STATUS_TYPE_ID, SIP_REPORT_NAME");
     // Utility.logger.debug(" SIP_REPORT Search SQL:  "+sqlString);
      
      System.out.println(sqlString);
      //System.out.println("Commission Search SQL: "+sqlString);
      //System.out.println("status is "+commissionStatus);
      
      ResultSet rs = stmt.executeQuery(sqlString.toString ( ));
      fileModel sipReportModel = null;
      Vector sipSearchResults = new Vector();
      while(rs.next())
      {
         sipReportModel = new fileModel(rs);
         sipSearchResults.add(sipReportModel);
      }
      rs.close();
      stmt.close();
      return sipSearchResults;
   }
   public static int addNewsipReport(Connection con , String sipReportName, String sipReportStartDate,
           String sipReportEndDate , String sipReportDesc , 
           String commissionCategoryType , String userID , String dataViewID,String dataViewType ,String sipReportChannel ,         
          String report_quarter, String  yearr,String  label,String  NI_commission_ids,String  line_coimmission_ids,String  sop_report_ids)
	  throws Exception
{
Statement stmt = con.createStatement();
Long  sipReportID = Utility.getSequenceNextVal(con , "SEQ_SIP_REPORT_DETAIL");
int sipReport_id = sipReportID.intValue();
String strQuery = "select * from ADH_DATA_VIEW where DATA_VIEW_ID =  "+dataViewID+
" and data_view_version = (select max(data_view_version) from ADH_DATA_VIEW where DATA_VIEW_ID = "+dataViewID+" )";
ResultSet res = stmt.executeQuery(strQuery);
String strDataViewQuery="";
if(res.next())
{
int intDataViewId = res.getInt("DATA_VIEW_ID");

QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine();
Vector vec =new Vector();
QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO(con,intDataViewId,vec);
strDataViewQuery = queryBuilderEngine.constructQuerySQL(queryDTO);
Utility.logger.debug(strDataViewQuery);
}
res.close();
strDataViewQuery = strDataViewQuery.replaceAll("'","''");
int dataViewTypeID = 0;
Utility.logger.debug(dataViewType);
if(dataViewType.equals(SIPInterfaceKey.DATA_VIEW_TYPE_A))
dataViewTypeID = 1;
if(dataViewType.equals(SIPInterfaceKey.DATA_VIEW_TYPE_B))
dataViewTypeID = 2;
sipReportName = sipReportName+sipReport_id;

System.out.println("sip REPORT Name issssss "+sipReportName);



String sqlString = "INSERT INTO SIP_REPORT_DETAIL (SIP_REPORT_DETAIL_ID , SIP_REPORT_NAME , SIP_REPORT_STATUS_TYPE_ID,"+
" SIP_REPORT_TYPE_CATEGORY_ID, SIP_REPORT_START_DATE , SIP_REPORT_END_DATE , SIP_REPORT_DESCRIPTION,"+
" SIP_REPORT_DATA_VIEW_ID ,SIP_REPORT_DATA_VIEW_SQL , DATA_VIEW_TYPE_ID , SIP_REPORT_CHANNEL_ID,SIP_REPORT_CREATION_DATE,SIP_REPORT_QUARTER_ID,SIP_REPORT_YEAR ,SIP_REPORT_LABEL,SIP_REPORT_NI_ID,SIP_REPORT_LC_ID , SIP_REPORT_SOP_ID  )"+


" VALUES("+sipReport_id+",'"+sipReportName+"','1','"+commissionCategoryType+"',TO_DATE('"+sipReportStartDate+"','dd/mm/yyyy'),"+
"TO_DATE('"+sipReportEndDate+"','dd/mm/yyyy'),'"+sipReportDesc+"',"+dataViewID+",?, "+dataViewTypeID+",'"+sipReportChannel+"',sysdate,'"+report_quarter+"','"+yearr+"','"+label+"','"+NI_commission_ids+"','"+line_coimmission_ids+"','"+sop_report_ids+"')";
System.out.println("Query for insert new sip Report is     "+sqlString);

//;report_quarter,   yearr, label,  NI_commission_ids, line_coimmission_ids  sop_report_ids)

//SIP_REPORT_QUARTER_ID        
//SIP_REPORT_YEAR              
//SIP_REPORT_LABEL             
//SIP_REPORT_NI_ID             
//SIP_REPORT_LC_ID             
//SIP_REPORT_SOP_ID  





oracle.jdbc.OraclePreparedStatement   pstmt = (oracle.jdbc.OraclePreparedStatement)con.prepareStatement(sqlString);

System.out.println("The Data View Query  issssssssssss "+strDataViewQuery);
pstmt.setStringForClob(1,strDataViewQuery);
//System.out.println("BEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEESY   "  +pstmt.toString() );



pstmt.execute();
pstmt.close();



//Utility.logger.debug(sqlString);
//int rows = stmt.executeUpdate(sqlString);
Long sipReportStatusID = Utility.getSequenceNextVal(con, "SEQ_SIP_REPORT_STATUS");
int sipReport_status_id = sipReportStatusID.intValue();
sqlString = "INSERT INTO SIP_REPORT_STATUS (SIP_REPORT_STATUS_ID, SIP_REPORT_DETAIL_ID,SIP_REPORT_STATUS_TYPE_ID,SIP_REPORT_STATUS_DATE,USER_ID)"+
" VALUES("+sipReport_status_id+","+sipReport_id+",1,TO_DATE(SYSDATE,'dd/mm/yyyy'),"+userID+")";

System.out.println("The sql String  Report status issssssssssssssssss"+ sqlString);

stmt.executeUpdate(sqlString);



stmt.close();
return sipReport_id;
}
	  public static Vector getYearList(Connection con)
	  {
			 Vector sopVec = new Vector();
			    ModuleModel moduleModel = null;
			   // StatusModel statusModel=null;
			    try
			    {
			      Statement stat = con.createStatement();
			      String strSql="select * from ADM_Module ORDER BY module_name";

			      ResultSet res = stat.executeQuery(strSql);
			      while (res.next())
			      {
			    	  sopVec.add(new ModuleModel(res));

			      }
			      stat.close();
			    }
			    catch(Exception e)
			    {
			      e.printStackTrace();
			    } 
			    return sopVec;
		  
		  
		  
		  
		  
	  }
	  
	  
  
	  public static Vector getItemNameAmount(Connection con,String savedReportId)
	  {
			 Vector reportItemVec = new Vector();
			    sipReportItemModel itemModel = null;
			   // StatusModel statusModel=null;
			    try
			    {
			      Statement stat = con.createStatement();
			   

			    String strSql=   " select  DCM_ID,SIP_REPORT_ITEM_NAME ,SIP_REPORT_ITEM_AMOUNT,SIP_REPORT_FACTOR_VALUE   as include  from  sip_report_item , SIP_REPORT_FACTOR" +
			       " where  sip_report_item.SIP_REPORT_DETAIL_ID= '"+savedReportId+"'  and   SIP_REPORT_ITEM.SIP_REPORT_FACTOR_ID =  SIP_REPORT_FACTOR.SIP_REPORT_FACTOR_ID " ;
			       
			    System.out.println("Query  getting report item   and amount" +strSql);  
			     
			    ResultSet res = stat.executeQuery(strSql);
			      while (res.next())
			      {
			    	  reportItemVec.add(new sipReportItemModel(res));

			      }
			      stat.close();
			    }
			    catch(Exception e)
			    {
			      e.printStackTrace();
			    } 
			    return reportItemVec;
		  
	  
	  }
	  
 
	  public static boolean checkBeforeChangeStatue(Connection con,String channelID,String quartarID,String year_id)
      {
         
         String sql = "select SIP_REPORT_CHANNEL_ID,SIP_REPORT_QUARTER_ID from VW_SIP_REPORT_DETAIL where  SIP_REPORT_CHANNEL_ID = '"+channelID+"' AND  SIP_REPORT_QUARTER_ID = '"+quartarID+"' AND SIP_REPORT_YEAR = '"+year_id+"' and SIP_REPORT_STATUS_TYPE_ID = 3 ";
         System.out.println ("sql checkBeforeChangeStatue isss "+sql);
         
         return DBUtil.executeSQLExistCheck ( sql , con );
      }
	  
	  public static Vector exportFile(Connection con,String typeId)
	  {
			 Vector sopVec = new Vector();
			 int typeId1=Integer.parseInt(typeId);
			 //String typeId=dataHashMap.
			 try
			 {
				 if(typeId1==1)
				 {
					 System.out.println("ISSSSSSSSSSSSSSSS 111111");
				 }
				 else if(typeId1==2)
				 {
					 System.out.println("ISSSSSSSSSSSSSSSS 222222");

				 }
				 else if(typeId1==3)
				 {
					 System.out.println("ISSSSSSSSSSSSSSSS 3333333");

				 }
				 else if(typeId1==4)
				 {
					 System.out.println("ISSSSSSSSSSSSSSSS 444444");

				 }
				 else if(typeId1==5)
				 {
					 System.out.println("ISSSSSSSSSSSSSSSS 555555");

				 }
				 
			 }
			 catch(Exception e)
			 {
				 
				 
			 }
		  return sopVec;
		  
	  }



	  public static Vector searchSavedReportByFilter(Connection con,String ReportId,String ReportName,String ReportQuarter,String ReportYear,String ReportStatus ,String ReportType)
	  throws Exception
{
   Statement stmt = con.createStatement();  
     // StringBuffer sqlString = new StringBuffer("select * from SIP_FILE_DETAIL SFD,SIP_REPORT_YEAR SRY where SFD.REPORT_YEAR = SRY.YEAR_ID  and 1=1");
      StringBuffer sqlString =new  StringBuffer("SELECT  *   FROM   SIP_SAVED_REPORT,SIP_REPORT_TYPE,SIP_REPORT_YEAR,SIP_REPORT_DATA_VIEW_TYPE,SIP_REPORT_STATUS,sip_report_status_type   where ");
    if(!ReportId.equals(""))
         {
         sqlString.append (  " SIP_SAVED_REPORT.REPORT_ID = '" );
         sqlString.append (  ReportId );
         sqlString.append (  "'" );
         sqlString.append (  " and " );
         
         }
      
      if(!ReportName.equals(""))
      {        
         sqlString.append(" SIP_SAVED_REPORT.report_name ='" ); 
         sqlString.append(ReportName);
         sqlString.append("'");
         sqlString.append (  " and " );
      }
      /*if(!ReportQuarter.equals(""))
      {        
         sqlString.append("   SIP_SAVED_REPORT.REPORT_QUARTER_ID = '" ); 
         sqlString.append(ReportQuarter);
         sqlString.append("'");
         sqlString.append (  " and " );
      }*/
       
     
      
      if(!ReportYear.equals(""))
      {        
         sqlString.append("  SIP_SAVED_REPORT.REPORT_YEAR_ID = '" ); 
         sqlString.append(ReportYear);
         sqlString.append("'");
         sqlString.append (  " and " );
      }
      
      
      
      
      if(!ReportType.equals(""))
      {        
         sqlString.append("  SIP_SAVED_REPORT.REPORT_TYPE_ID ='" ); 
         sqlString.append(ReportType);
         sqlString.append("'");
         sqlString.append (  " and " );
      }
      
      
      
      if(!ReportStatus.equals(""))
      {        
         sqlString.append(" SIP_SAVED_REPORT.REPORT_STATUS_ID = '" ); 
         sqlString.append(ReportStatus);
         sqlString.append("'");
         sqlString.append (  " and " );
      }
   sqlString.append("       SIP_SAVED_REPORT.REPORT_ID=SIP_REPORT_STATUS.SIP_REPORT_ID   and        SIP_SAVED_REPORT.REPORT_TYPE_ID  =   SIP_REPORT_TYPE.REPORT_TYPE_ID  and  SIP_SAVED_REPORT.DATA_VIEW_TYPE_ID=SIP_REPORT_DATA_VIEW_TYPE.DATA_VIEW_TYPE_ID    " +
" and  SIP_SAVED_REPORT.REPORT_YEAR_ID=SIP_REPORT_YEAR.YEAR_ID    and  SIP_SAVED_REPORT.REPORT_STATUS_ID=SIP_REPORT_STATUS.SIP_REPORT_STATUS_TYPE_ID       and  SIP_REPORT_STATUS.SIP_REPORT_STATUS_TYPE_ID=SIP_REPORT_STATUS_TYPE.REPORT_STATUS_TYPE_ID       ORDER BY   SIP_SAVED_REPORT.REPORT_ID " ); 

  
     // sqlString.append( " ORDER BY  SIP_REPORT_CHANNEL_ID, SIP_REPORT_STATUS_TYPE_ID, SIP_REPORT_NAME");
     // Utility.logger.debug(" SIP_REPORT Search SQL:  "+sqlString);
      
      System.out.println(sqlString);
      //System.out.println("Commission Search SQL: "+sqlString);
      //System.out.println("status is "+commissionStatus);
      
      ResultSet rs = stmt.executeQuery(sqlString.toString ( ));
      savedSipReportModel sipReportModel = new savedSipReportModel();
      Vector sipSearchResults = new Vector();
      while(rs.next())
      {
    	  sipReportModel = new savedSipReportModel(con,rs);
         sipSearchResults.add(sipReportModel);
      }
      rs.close();
      stmt.close();
      return sipSearchResults;
   }





	  public static Vector<sipDistHistoryCSVDTO> fillDistVector(
	         String report_id,String sql,Vector<sipDistHistoryCSVDTO> csvVector ) throws SQLException     
	   {     
	     Connection con = Utility.getConnection ( );
	     // Vector<sipDistHistoryCSVDTO> csvVector = new Vector<sipDistHistoryCSVDTO>();      
	      
	     // String sql = prepareSQLExportCSV(report_id);

	      Statement st = con.createStatement (  );
	      ResultSet rs = st.executeQuery ( sql );  
	      
	      while(rs.next ( )){
	         sipDistHistoryCSVDTO csvdto = new sipDistHistoryCSVDTO();
	         csvdto.setEmp_id (rs.getString ( "EMP_ID" ) );
	         csvdto.setLine_name (rs.getString ( "LINE_NAME" ) );         
	         csvdto.setKpi_name(rs.getString ( "KPI_NAME"  ));
	         csvdto.setSip_report_channel_name (rs.getString ( "SIP_REPORT_CHANNEL_NAME" ) );
	         csvdto.setMode_name(rs.getString ( "MODE_NAME" ) );
	         csvdto.setKpi_value (rs.getString ( "KPI_VALUE" ) );
	         csvdto.setSip_report_id(rs.getString ( "SIP_REPORT_ID" ) );
	         csvdto.setTransaction_date(rs.getString ( "TRANSACTION_DATE" ));
	         csvVector.add ( csvdto );
	      }
	      rs.close ( );
	      st.close ( );
	      
	      Utility.closeConnection ( con );
	      return csvVector;
	   }
	  
	  
	  

	   
	   public static String prepareSQLExportCSV (String reportId)
	   {
	      StringBuffer sql = new StringBuffer("");
	 
	      
	      sql.append (" SELECT  SDHC.EMP_ID,SIP_REPORT_CHANNEL_NAME,MODE_NAME,LINE_NAME,KPI_NAME, KPI_VALUE, to_char(LAST_DAY(TRANSACTION_DATE),'dd-Mon-yy') TRANSACTION_DATE, SIP_REPORT_ID");
	      sql.append (" FROM SIP_DIST_HISTORY_CSV SDHC,SIP_BUSS_LINE SBL,");
	      sql.append (" SIP_KPI_METRIC SKM,");
	      sql.append (" SIP_REPORT_CHANNEL SRC,");
	      sql.append (" SIP_SALE_MODE SSM,");
	      sql.append (" SIP_UPLOADED_LISTS SUE");
	    //  sql.append (" GEN_USER GU");
	      sql.append (" WHERE SDHC.CHANNEL_ID = SRC.SIP_REPORT_CHANNEL_ID");
	      sql.append (" AND SDHC.EMP_ID = SUE.FIELD2");
	      sql.append (" AND SDHC.KPI_ID = SKM.KPI_ID");
	      sql.append (" AND SDHC.LINE_ID = SBL.LINE_ID");
	      sql.append (" AND SDHC.MODE_ID = SSM.MODE_ID");
	    //  sql.append (" AND FIELD2 = GU.USER_ID");
	    //  sql.append (" AND FIELD2 = '");
	     // sql.append ( empId );
	      //sql.append ( "'" );
	      sql.append (" AND SIP_REPORT_ID='");
	      sql.append (reportId);
	      sql.append ("'");
	      sql.append (" order by SDHC.EMP_ID,SDHC.TRANSACTION_DATE");
	      
	      return sql.toString ( );
	   }
	   
	   








	   public static  String exportUploadedFile(String path,String fileId,Boolean fileDataOrInvalidData) throws Exception 
	    {
	        
	      Vector<sipExportFileDTO> fileDataVec = getFileData(fileId);
	      sipExportFileDTO sfdto = null;
	      Workbook wb;
          
          wb =  new HSSFWorkbook();
	      
	      String excel_fields_count="",excel_sheets_count="",file_type_name="",field = "";
	      String [] file_sheet_name = null;
	      String [] file_sheet_header=null;
	      Sheet sheet[] = null;
	      Sheet excelSheet = null;
	      Row row = null;
	      Cell cell = null;
	      Boolean isFirstTime = false;
	      for ( int i = 0 ; i < fileDataVec.size ( ) ; i ++ )
         {
	         sfdto = fileDataVec.get ( i );
	         if (!isFirstTime)
	         {
	            excel_fields_count = sfdto.getExcel_fields_count ( );
	            excel_sheets_count = sfdto.getExcel_sheets_count ( );
	            file_type_name = sfdto.getFile_type_name ( );
	            file_sheet_header = sfdto.getFile_sheet_header ( ).split ( "," );
	            file_sheet_name = sfdto.getFile_sheet_name ( ).split ( "," );
	            sheet = new Sheet[file_sheet_name.length];
	            for ( int j = 0 ; j < file_sheet_name.length ; j ++ )
               {
	               sheet[j] =  wb.createSheet(file_sheet_name[j]);
	               row = sheet[j].createRow ( 0 );
	               for ( int f = 0 ; f < file_sheet_header.length ; f ++ )
                  {
                     cell = row.createCell ( f );
                     cell.setCellValue ( file_sheet_header[f] );
                  }
	               
               }
	            
	            isFirstTime=true;
	         }
	         excelSheet  =sheet[Integer.valueOf (sfdto.getSheet_number ( ))-1];
	         row = excelSheet.createRow ( i+1 );
	         
	         for ( int j = 0 ; j < file_sheet_header.length ; j ++ )
            {
	            cell = row.createCell ( j );
	            
	            
	            if (j==0) field = sfdto.getField1 ( );
	            if (j==1)field = sfdto.getField2 ( );;
	            if (j==2)field = sfdto.getField3 ( );
	            
	            
	            if (fileDataOrInvalidData)
                {
                 if (!field.startsWith ( "0_" ))
                    cell.setCellValue ( field );
                }
                else 
                {
                   if (field.startsWith ( "0_" ))
                        cell.setCellValue ( field );
                }
	             
            }
	         
	         
	         
         }
	      
	       
	       
	      file_type_name = file_type_name.replace ( " " , "_" );
	
	Date date = new Date();
	        
	        StringBuffer filePath = new StringBuffer("");    
	        filePath.append(path);                  
	          filePath.append(file_type_name);
	          filePath.append((date.getMonth ( )+1));
	          filePath.append("-");
	          filePath.append(date.getHours ( ));
	          filePath.append("-");
	          filePath.append(date.getMinutes());
	          filePath.append("-");
	          filePath.append(date.getSeconds());
	          filePath.append(".xls");
	          
	File excelDistFile = new File(filePath.toString()); 

	//if(wb instanceof XSSFWorkbook) file += "x";
	FileOutputStream out = new FileOutputStream(excelDistFile);
	wb.write(out);
	out.close();

	return filePath.toString();
	    }



public static Vector<sipExportFileDTO> getFileData(String fileId) throws SQLException
{
      StringBuffer sql = new StringBuffer("");
      
      Vector<sipExportFileDTO> fileDataVec = null;
      sipExportFileDTO sfdto = null;
      sql.append ( "select EXCEL_FIELDS_COUNT,EXCEL_SHEETS_COUNT,FILE_SHEET_NAME,REPORT_TYPE,FILE_TYPE_NAME,FIELD1,FIELD2,FIELD3,SHEET_NUMBER,FILE_SHEET_HEADER");
      sql.append ( " from SIP_FILE_DETAIL SFD,SIP_FILE_TYPE SFT,SIP_UPLOADED_LISTS SUL");
      sql.append ( " where SFD.FILE_ID = SUL.FILE_ID");
      sql.append ( " AND SFD.REPORT_TYPE = SFT.FILE_TYPE_ID");
      sql.append ( " AND  SFD.FILE_ID = '");
      sql.append ( fileId );
      sql.append ( "'" );
      
      Connection con = Utility.getConnection ( );
      Statement st = con.createStatement ( );
      ResultSet rs = st.executeQuery ( sql.toString ( ) );
      while (rs.next ( ))
      {
         if (fileDataVec==null)fileDataVec=new Vector<sipExportFileDTO>();
         
         sfdto=new sipExportFileDTO();
         sfdto.setExcel_fields_count ( rs.getString ( "EXCEL_FIELDS_COUNT" ) );
         sfdto.setExcel_sheets_count ( rs.getString ( "EXCEL_SHEETS_COUNT" ) );         
         sfdto.setField1 ( (rs.getString ( "FIELD1" )==null||rs.getString ( "FIELD1" ).compareTo ( "" )==0)?"":rs.getString ( "FIELD1" ));
         sfdto.setField2 ( (rs.getString ( "FIELD2" )==null||rs.getString ( "FIELD2" ).compareTo ( "" )==0)?"":rs.getString ( "FIELD2" ));
         sfdto.setField3 ( (rs.getString ( "FIELD3" )==null||rs.getString ( "FIELD3" ).compareTo ( "" )==0)?"":rs.getString ( "FIELD3" )) ;        
         sfdto.setFile_sheet_name  ( rs.getString ( "FILE_SHEET_NAME" ));
         sfdto.setFile_type_name ( rs.getString ( "FILE_TYPE_NAME" ));         
         sfdto.setReport_type ( rs.getString ( "REPORT_TYPE" ));         
         sfdto.setSheet_number  ( rs.getString ( "SHEET_NUMBER" ));
         sfdto.setFile_sheet_header  ( rs.getString ( "FILE_SHEET_HEADER" ));
         
         
         
         fileDataVec.add ( sfdto );
      }
      st.close ( );
      rs.close ( );
      Utility.closeConnection ( con );
      return fileDataVec;
}



public static void main(String[] args)
{
   String ss = "sheet1";
   String [] ssd = ss.split ( "," );
   for ( int i = 0 ; i < ssd.length ; i ++ )
   {
      System.out.println (ssd[i]);
   }
}








}

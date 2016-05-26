package com.mobinil.sds.core.system.request;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import com.mobinil.sds.core.system.request.model.SupervisorModel;
import com.mobinil.sds.core.utilities.Utility;

public class RequestDao 
{
	public static Vector getSupervisorList(Connection con)throws Exception
	  {
	    Statement stmt = con.createStatement();    
	    String sqlString = "select USER_DETAIL_ID , USER_FULL_NAME from DCM_USER_DETAIL where USER_DETAIL_STATUS_ID = 1";
	    Utility.logger.debug(sqlString);
	    ResultSet rs = stmt.executeQuery(sqlString);
	    rs =stmt.executeQuery(sqlString);
	    SupervisorModel supervisorModel = null;
	    Vector supervisorList = new Vector();
	    
	    while(rs.next())
	    {
	      supervisorModel = new SupervisorModel();
	      supervisorModel.setSupervisorId(rs.getInt("USER_DETAIL_ID"));
	      supervisorModel.setSupervisorName(rs.getString("USER_FULL_NAME"));
	     // supervisorModel.setSupervisorStatus(rs.getInt("USER_DETAIL_STATUS_ID"));
	      supervisorList.add(supervisorModel);  
	    }
	    
	    stmt.close();
	    rs.close();
	    
	    return supervisorList;
	  }  

	
	
	
	public static String getSupervisorName(Connection con , String userId)throws Exception
	  {
	    Statement stmt = con.createStatement();    
	    String sqlString = "select USER_FULL_NAME from DCM_USER_DETAIL where USER_DETAIL_ID = " + userId;
	    Utility.logger.debug(sqlString);
	    ResultSet rs = stmt.executeQuery(sqlString);
	    rs =stmt.executeQuery(sqlString);
	    String supervisorName = "";

	    if(rs.next())
	    {
	    	supervisorName = rs.getString("USER_FULL_NAME");
	   
	    }
	    
	    stmt.close();
	    rs.close();
	    
	    return supervisorName;
	  }  
	
	
	
	public static int getAvailableStkCount(Connection con)throws Exception
	  {
	    Statement stmt = con.createStatement();    
	    String sqlString = "select count(*) AS rowcount from SCM_STK_STOCK where STK_STATUS_ID = 1";
	    Utility.logger.debug(sqlString);
	    ResultSet rs = stmt.executeQuery(sqlString);
	    rs =stmt.executeQuery(sqlString);
	    int rowCount = 0;
	    
	    if(rs.next())
	    {
	      rowCount = rs.getInt("rowcount"); 
	    }
	    
	    stmt.close();
	    rs.close();
	    
	    return rowCount;
	  }  

	
	
	public static void updateStkStatus(Connection con , String stkNumber)throws Exception
	  {
	    Statement stat = null ; 
		try
		{
		 stat = con.createStatement();
		 Statement stmt = con.createStatement();    
	     String sqlString = "update SCM_STK_STOCK set STK_STATUS_ID = 2 where STK_NUMBER = '"+stkNumber+"'";
	     Utility.logger.debug(sqlString);
	     System.out.print(sqlString);
	     stat.executeUpdate(sqlString);  
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

	
	public static int getPosCount(Connection con)throws Exception
	  {
	    Statement stmt = con.createStatement();    
	    String sqlString = "select count(*) AS rowcount from GEN_DCM";
	    Utility.logger.debug(sqlString);
	    ResultSet rs = stmt.executeQuery(sqlString);
	    rs =stmt.executeQuery(sqlString);
	    int rowCount = 0;
	    
	    if(rs.next())
	    {
	      rowCount = rs.getInt("rowcount"); 
	    }
	    
	    stmt.close();
	    rs.close();
	    
	    return rowCount;
	  }  
	
	
	public static void insertStk(Connection con, String supervisorId , String stkNumber , String requestDate)
	  {
		  Statement stat = null ; 
	    try
	    {
	      stat = con.createStatement();
	      String strSql = "insert into REP_STK_SUPERVISOR (STK_ID , SUPERVISOR_ID , CREATION_DATE) values ((select STK_ID from SCM_STK_STOCK where STK_NUMBER = '"+stkNumber+"') ,"+supervisorId+",'"+requestDate+"')";
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
	
	public static boolean checkStkNumberAvailable(Connection con , String stkNumber) throws SQLException
	{
	  Statement stmt = con.createStatement();    
	  String sqlString = "select * from SCM_STK_STOCK where STK_NUMBER = '"+stkNumber+"' and STK_STATUS_ID = 1";
	  Utility.logger.debug(sqlString);
	  ResultSet rs = stmt.executeQuery(sqlString);
	  rs =stmt.executeQuery(sqlString);
	  if(rs.next())
	  {
	    stmt.close();
	    rs.close();
	    return true; 
	  }
	  else
	  {
	    stmt.close();
		rs.close();
		return false;	
	  }
	}
	
	
	public static void insertPosSupervisor(Connection con, String supervisorId , int posId , String requestDate)
	  {
		  Statement stat = null ; 
	    try
	    {
	      stat = con.createStatement();
	      String strSql = "insert into REP_POS_SUPERVISOR (POS_ID , SUPERVISOR_ID , CREATION_DATE) values("+posId+","+supervisorId+",'"+requestDate+"')";
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
	
	public static void insertPosDcm(Connection con, int posId )
	  {
		  Statement stat = null ; 
	    try
	    {
	      stat = con.createStatement();
	      String strSql = "insert into GEN_DCM (DCM_ID , DCM_STATUS_ID) values("+posId+", 18)";
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
	
	
	
	
	public static void insertRequestTrack(Connection con, String supervisorId , String posQuantity , String posFrom , String posTo , String userId , String requestDate , String stkQuantity )
	  {
		  Statement stat = null ; 
	    try
	    {
	      stat = con.createStatement();
	      String strSql = "insert into REP_KIT_TRACK (SUPERVISOR_ID , STK_QUANTITY , POS_QUANTITY , POS_FROM , POS_TO , CREATION_DATE , USER_ID) " +
	      		          " values("+supervisorId+", "+stkQuantity+" , "+posQuantity+" , "+posFrom+" , "+posTo+" , '"+requestDate+"' , "+userId+")";
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
	
}

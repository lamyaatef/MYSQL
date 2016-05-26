package com.mobinil.sds.core.system.authenticationResult.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.mobinil.sds.core.system.authenticationResult.model.LabelModel;
import com.mobinil.sds.core.utilities.Utility;

public class LabelDao {

		public static Vector getLabel(Connection con) {
			Vector vec = new Vector();
			try {
				Statement stat = con.createStatement();
				String strSql = "SELECT * FROM AUTH_RES_LABEL ";
				System.out.println("select query  is"+strSql);
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new LabelModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;

		}
		
		public static Vector getLabelByUser(Connection con,String userId) {
			Vector vec = new Vector();
			try {
				Statement stat = con.createStatement();
				String strSql = "SELECT * FROM AUTH_RES_LABEL where label_id in (select label_id from AUTH_RES_USER_LABEL where user_id = '"+userId+"') ";
				System.out.println("select query  is"+strSql);
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new LabelModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;

		}
		
		

		
		
		public static void insertNewLabel(Connection con,
				String labelName,  String lebelDescription)
{

			Long Label_ID = null;

			try {
				Statement stat = con.createStatement();
				Label_ID= Utility.getSequenceNextVal(con,"SEQ_AUTH_RES_LABEL_ID");

				String strSql = "insert into AUTH_RES_LABEL ( LABEL_ID ,LABEL_NAME,LABEL_DESCRIPTION) "
						+ " values("+Label_ID+",'"+labelName+"' ,'"+lebelDescription+"')";
		       System.out.println("the query is " +strSql);
		  
				stat.execute(strSql);		
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
		  public static void updateLabel(Connection con,String labelId,String labelName,String labelDescription)
		  {
		    try
		    {
		      Statement stat = con.createStatement();
		        String strSql = "update AUTH_RES_LABEL set "+ 
		         "LABEL_NAME = '"+labelName+"',LABEL_DESCRIPTION='"+labelDescription+"' where LABEL_ID = "+labelId+"";  
		      
		     System.out.println("TheQuery isssssss " + strSql);
		     stat.execute(strSql);          
		    }
		    catch(Exception e)
		    {  
		      e.printStackTrace();
		    }    
		  }	
		
			public static void deleeteLabel(Connection con,
					String labelId)
	{

				try {
					Statement stat = con.createStatement();
				

					 String deleteSql = "delete from AUTH_RES_LABEL where LABEL_ID="+labelId+"";
			       System.out.println("the query is " +deleteSql);
			  
					stat.execute(deleteSql);		
					stat.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	

			
			
			
			public static Vector getLabelforSpecificId(Connection con,String labelId) {
				Vector vec = new Vector();
				try {
					Statement stat = con.createStatement();
					String strSql = "SELECT * FROM AUTH_RES_LABEL where LABEL_ID="+ labelId+" ";
					ResultSet res = stat.executeQuery(strSql);
					while (res.next()) {
						vec.add(new LabelModel(res));
					}
					res.close();
					stat.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return vec;

			}	
	
			
	public static    boolean  checkifDataExists(Connection conn,String Label_id)
			
			{
		
		boolean status =false;
		
		try{
			
		Statement	stat=conn.createStatement();
		
		String strSql  = "SELECT * FROM AUTH_RES_FILE where LABEL_ID = "+Label_id+ " and status  <> 'Deleted'";
		
		ResultSet res = stat.executeQuery(strSql);
		
		if(res.next())
		{
			
			status= true;
			
			
		}
			
		
			
			
		}
		catch(Exception ex){
		
		ex.printStackTrace();
		}
		
		
		
		
		
		return status;
		
		
		
		
	}
			
}

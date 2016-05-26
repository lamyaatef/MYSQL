package com.mobinil.sds.core.system.ccm.service.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;


import com.mobinil.sds.core.system.ccm.service.model.ServiceModel;
import com.mobinil.sds.core.utilities.Utility;

public class ServiceDao {

		public static Vector getService(Connection con) {
			Vector vec = new Vector();
			try {
				Statement stat = con.createStatement();
				String strSql = "SELECT * FROM CCM_SERVICE ";
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new ServiceModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;

		}	
		
		
		public static void insertNewService(Connection con,
				String serviceName,  String serviceDescription)
{

			Long Service_ID = null;

			try {
				Statement stat = con.createStatement();
				Service_ID= Utility.getSequenceNextVal(con, "SEQ_CCM_SERVICE_ID");

				String strSql = "insert into ccm_SERVICE ( SERVICE_ID ,SERVICE_NAME,SERVICE_DESCRIPTION) "
						+ " values("+Service_ID+",'"+serviceName+"' ,'"+serviceDescription+"')";
		       System.out.println("the query is " +strSql);
		  
				stat.execute(strSql);		
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
		  public static void updateService(Connection con,String serviceId,String serviceName,String serviceDescription)
		  {
		    try
		    {
		      Statement stat = con.createStatement();
		        String strSql = "update CCM_SERVICE set "+ 
		         "SERVICE_NAME = '"+serviceName+"',SERVICE_DESCRIPTION='"+serviceDescription+"' where SERVICE_ID = "+serviceId+"";  
		      
		     System.out.println("TheQuery isssssss " + strSql);
		     stat.execute(strSql);          
		    }
		    catch(Exception e)
		    {  
		      e.printStackTrace();
		    }    
		  }	
		
			public static void deleeteService(Connection con,
					String serviceId)
	{

				try {
					Statement stat = con.createStatement();
				

					 String deleteSql = "delete from ccm_service where SERVICE_ID="+serviceId+"";
			       System.out.println("the query is " +deleteSql);
			  
					stat.execute(deleteSql);		
					stat.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	

			
			
			
			public static Vector getServiceforSpecificId(Connection con,String serviceId) {
				Vector vec = new Vector();
				try {
					Statement stat = con.createStatement();
					String strSql = "SELECT * FROM CCM_SERVICE where SERVICE_ID="+ serviceId+" ";
					ResultSet res = stat.executeQuery(strSql);
					while (res.next()) {
						vec.add(new ServiceModel(res));
					}
					res.close();
					stat.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return vec;

			}	
			
			
			
			
			
			
			
			
			
			
			
			
			
		}

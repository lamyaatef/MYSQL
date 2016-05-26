package com.mobinil.sds.core.system.cr.notificationList.dao;

import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.system.cr.notificationList.dto.notificatioListDTO;
import com.mobinil.sds.core.utilities.DBUtil;

public class notificatioListDAO {
	
	public static Vector getAllEmails(Connection con,boolean emailFlag,String emailId) throws SQLException
	{
		String emailConstrain = "";
		
		if (emailFlag)emailConstrain=" Where ID="+emailId;
		Statement st = con.createStatement();
		Vector allEmails = new Vector();
		
		String SQL = "select * from CR_LCS_MAPPING_NOTIFCATION"+emailConstrain;
		ResultSet rs = st.executeQuery(SQL);
		while (rs.next()){
			notificatioListDTO nldto = new notificatioListDTO();
			nldto.setId(new Integer (rs.getInt("ID")));
			nldto.setEmail(rs.getString("EMAIL"));
			allEmails.addElement(nldto);
			
		}
		rs.close();
		st.close();
		
		return allEmails;
		
	}
	 public static void deleteEmail(Connection con,int emailId) 
	{
        String sql = "delete from CR_LCS_MAPPING_NOTIFCATION where ID="+ emailId;	    
        DBUtil.executeSQL(sql, con);                
	}
	 
	 public static void insertEmail(Connection con,String email) throws SQLException
	{	            
        String sql = "insert into CR_LCS_MAPPING_NOTIFCATION values(SEQ_LCS_NOTIFICATION_ID.nextval,'"+email+"')";
        DBUtil.executeSQL(sql, con);	            	            
	}
	 
	 public static void updateEmail(Connection con,String email,String emailId) throws SQLException
    {
        String sql = "update CR_LCS_MAPPING_NOTIFCATION set  EMAIL='"+email+"' where ID = "+emailId;
        DBUtil.executeSQL(sql, con);
    }	 
	 
	 
	 public static int getSeqValue(Connection con,String seqName) throws SQLException
	  {

	    String sql = "select "+seqName+".nextVal val from dual";
	    Statement st = con.createStatement();
	    ResultSet seqValueRS =  st.executeQuery(sql);
	    int seqValue=0;
	        try
	        {

	            if (seqValueRS.next()) {
	                seqValue = seqValueRS.getInt("val");
	            }

	        }
	        catch (SQLException ex)
	        {
	            ex.printStackTrace();
	        }
	        seqValueRS.close();
	        st.close();
	        
	    return seqValue;
	    }		
}

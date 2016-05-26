package com.mobinil.sds.core.system.security.dao;

import java.sql.*;
import com.mobinil.sds.core.utilities.*;
import java.util.Date;
public class passwordDAO 
{
   public static Boolean checkLastPassword(String passEncripted, String sysUserId)
  {
  Boolean aa = Boolean.TRUE;
  Connection con = null;
  Statement stat = null;
  ResultSet checkPassExists = null; 
  
  try
     {
	  con = Utility.getConnection();
      stat = con.createStatement();
      String SQL="select SYSTEM_USER_ID from SDS_PASS_TRACE  where PASSWORD='" + 
                                 passEncripted + "' and SYSTEM_USER_ID = " + sysUserId;
      checkPassExists = stat.executeQuery(SQL);


    
   if (!checkPassExists.equals(null))
   {
    if (checkPassExists.next())
      {
    	aa = Boolean.FALSE;
      
      }
   }
   		     
        aa= Boolean.TRUE;
      }
    catch (SQLException e)
      {
    	e.printStackTrace();
        aa= Boolean.TRUE;
      }
    finally
    {
    	if (checkPassExists!=null)try{checkPassExists.close();}catch(Exception e){}
    	if (stat!=null)try{stat.close();}catch(Exception e){}
    	if (con!=null)try{Utility.closeConnection(con);}catch(Exception e){}
   	
    }
    return aa;
  }

  public static Boolean checkLock(String userId,Connection con)
  {
    Boolean aa = Boolean.TRUE;
    Integer lockTimes = securityDAO.getProps(con,"LOCK_TIMES");
    Integer loginFailTimes =new Integer(0);
try
      {
   // Connection con = Utility.getConnection();
   Statement stat = con.createStatement();
    String SQL = "select IS_LOCKED from GEN_USER where USER_ID='" + userId + "'";
    ResultSet getUserLock = stat.executeQuery(SQL);
    
        if (getUserLock.next())
          {
            loginFailTimes = Integer.valueOf(getUserLock.getString("IS_LOCKED"))  ;
          }
        getUserLock.close();
        stat.close();
    //    Utility.closeConnection(con);
        
        
        if (loginFailTimes.intValue() >= lockTimes.intValue()){
        return aa.FALSE;
        }
        return aa.TRUE;
      }
    catch (SQLException e)
      {
    	e.printStackTrace();
        return aa.FALSE;
      }

  }
  public static Boolean checkExpire(String userId)
  {    
    
    Date endLoginDate =null;
try
      {
    Connection con = Utility.getConnection();
    Statement stat = con.createStatement();
    String SQL = "select SYSTEM_LOGIN_END_DATE from GEN_USER where USER_ID='" + userId + "'";
    ResultSet getUserExpire = stat.executeQuery(SQL);
    
        if (getUserExpire.next())
          {
            endLoginDate = getUserExpire.getDate("SYSTEM_LOGIN_END_DATE") ;
          }
        getUserExpire.close();
        stat.close();
        Utility.closeConnection(con);
        
        
        if (endLoginDate.before(new Date())){
        return Boolean.FALSE;
        }
        return Boolean.TRUE;
      }
    catch (SQLException e)
      {
        return Boolean.FALSE;
      }

  }
}
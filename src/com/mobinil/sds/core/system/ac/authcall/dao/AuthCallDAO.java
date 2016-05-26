package com.mobinil.sds.core.system.ac.authcall.dao;

import java.sql.*;
import com.mobinil.sds.core.utilities.*;
import java.util.*;
import com.mobinil.sds.core.system.ac.authcall.model.*;
import com.mobinil.sds.core.system.cr.sheet.model.*;

/*
 * AuthCallDAO responsible of database operations related to the authentication call. 
 * 1- Get Authentication Call Statuses. Returns a Vector of AuthCallStatusModel objects.
 * 2- Get Authentication Call Owner Statuses. Returns a Vector of AuthCallOwnerStatusModel objects. 
 * 3- Get Authentication Call POS Statuses. Returns a Vector of AuthCallPOSStatusModel objects.
 * 4- Get Authentication Call Status ID. Return the authentication call status ID
 *     of the provided Authentication Call Status as a String object.
 * 5- Get Authentication Call Owner Status ID. Return the authentication call owner status ID
 *     of the provided Authentication Call Owner Status as a String object. 
 * 6- Get Authentication Call POS Status ID. Return the authentication call pos status ID
 *     of the provided Authentication Call POS Status as a String object. 
 * 7- Get Authentication Call Status By Contract. Return a HashMap Object holding 
 *     Authentication Call Status, Authentication Call Owner Status, Authentication Call POS Status
 *     of a contract with the given contract id.
 * 8- Insert New Authentication Call record in the database with the following data
 *     (authCallStatusID, userID, contractStatusID). 
 *     It return the new authentication call id as a String object.
 * 9- Insert Authentication Call Parameter record in the database with the following data
 *     (paramOwnerStatusID, paramPOSStatusID, authCallID).
 * 
 */

public class AuthCallDAO 
{

/*
 * 1-Get Authentication Call statuses. Returns a Vector of AuthCallStatusModel objects.
 */
  public static Vector getAuthCallStatuses()
  {
    Vector authCallStatusVector = new Vector();
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery("select AUTH_CALL_STATUS_ID ,AUTH_CALL_STATUS_NAME  from VW_ATH_AUTH_CALL_STATUS");
      while (res.next())
      {
        String authCallStatusID = res.getString("AUTH_CALL_STATUS_ID");
        String authCallStatusName = res.getString("AUTH_CALL_STATUS_NAME");
        AuthCallStatusModel newAuthCallStatusModel = new AuthCallStatusModel(authCallStatusID, authCallStatusName);
        authCallStatusVector.add(newAuthCallStatusModel);
      }
      Utility.closeConnection(con);
      return authCallStatusVector;
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
    return null;
  }

/*
 * 2-Get Authentication Call Owner Statuses. Returns a Vector of AuthCallOwnerStatusModel objects. 
 */
  public static Vector getAuthCallOwnerStatuses()
  {
    Vector authCallOwnerStatusVector = new Vector();
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery("select * from VW_ATH_AUTH_OWNER_STATUS");
      while (res.next())
      {
        String authCallOwnerStatusID = res.getString("AUTH_CALL_OWNER_STATUS_ID");
        String authCallOwnerStatusName = res.getString("AUTH_CALL_OWNER_STATUS_NAME");
        AuthCallOwnerStatusModel newAuthCallOwnerStatusModel = new AuthCallOwnerStatusModel(authCallOwnerStatusID, authCallOwnerStatusName);
        authCallOwnerStatusVector.add(newAuthCallOwnerStatusModel);
      }
      Utility.closeConnection(con);
      return authCallOwnerStatusVector;
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
    return null;
  }

/*
 * 3-Get Authentication Call POS Statuses. Returns a Vector of AuthCallPOSStatusModel objects.
 */
  public static Vector getAuthCallPOSStatuses()
  {
    Vector authCallPOSStatusVector = new Vector();
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery("select AUTH_CALL_POS_STATUS_ID , AUTH_CALL_POS_STATUS_NAME from VW_ATH_AUTH_POS_STATUS");
      while (res.next())
      {
        String authCallPOSStatusID = res.getString("AUTH_CALL_POS_STATUS_ID");
        String authCallPOSStatusName = res.getString("AUTH_CALL_POS_STATUS_NAME");
        AuthCallPOSStatusModel newAuthCallPOSStatusModel = new AuthCallPOSStatusModel(authCallPOSStatusID, authCallPOSStatusName);
        authCallPOSStatusVector.add(newAuthCallPOSStatusModel);
      }
      Utility.closeConnection(con);
      return authCallPOSStatusVector;
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
    return null;
  }

/*
 * 4- Get Authentication Call Status ID. Return the authentication call status ID
 *     of the provided Authentication Call Status as a String object.
 */
  public static String getAuthCallStatusID(String authCallStatusName)
  {
    String authCallStatusID = "";
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery("select * from VW_ATH_AUTH_CALL_STATUS where AUTH_CALL_STATUS_NAME = '"+authCallStatusName+"'");
      while (res.next())
      {
        authCallStatusID = res.getString("AUTH_CALL_STATUS_ID");
      }
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
    return authCallStatusID;
  }

/*
 * 5- Get Authentication Call Owner Status ID. Return the authentication call owner status ID
 *     of the provided Authentication Call Owner Status as a String object. 
 */
  public static String getAuthCallOwnerStatusID(String authCallStatusName)
  {
    String authCallOwnerStatusID = null;
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery("select * from VW_ATH_AUTH_OWNER_STATUS where AUTH_CALL_OWNER_STATUS_NAME = '"+authCallStatusName+"'");
      while (res.next())
      {
        authCallOwnerStatusID = res.getString("AUTH_CALL_OWNER_STATUS_ID");
      }
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
    return authCallOwnerStatusID;
  }

/*
 * 6- Get Authentication Call POS Status ID. Return the authentication call pos status ID
 *     of the provided Authentication Call POS Status as a String object. 
 */
  public static String getAuthCallPOSStatusID(String authCallStatusName)
  {
    String authCallPOSStatusID = null;
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      String query = "select * from VW_ATH_AUTH_POS_STATUS where AUTH_CALL_POS_STATUS_NAME = '"+authCallStatusName+"'";
      ResultSet res = stat.executeQuery(query);
      while (res.next())
      {
        authCallPOSStatusID = res.getString("AUTH_CALL_POS_STATUS_ID");
      }
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
    return authCallPOSStatusID;
  }

/*
 * 7- Get Authentication Call Status By Contract. Return a HashMap Object holding 
 *     Authentication Call Status, Authentication Call Owner Status, Authentication Call POS Status
 *     of a contract with the given contract id.
 */
  public static HashMap getAuthCallStatusByContract(String contractID)
  {
    HashMap authCallStatusByContract = new HashMap();
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      /*
       * i chagned the statement to another view that provide the same info but more optimized
       */
     // String sql = "select * from VW_ATH_AUTH_CALL_BY_CONTRACT where CONTRACT_ID = "+ contractID ;
     
      String sql = "select * from vw_ath_call_by_Contract_id_opt where CONTRACT_ID = "+ contractID ;

      
      long startT = System.currentTimeMillis();
      ResultSet res = stat.executeQuery(sql);

      //Utility.logger.debug(sql);
      
      //Utility.logger.debug("time of exec = "+ (System.currentTimeMillis() - startT ));
      
      
      while (res.next())
      {
        String authCallStatusName = res.getString("AUTH_CALL_STATUS_NAME");
        String authCallOwnerStatusName = res.getString("AUTH_CALL_OWNER_STATUS_NAME");
        String authCallPOSStatusName = res.getString("AUTH_CALL_POS_STATUS_NAME");
        if(authCallStatusName ==null)
        {
          authCallStatusName = "";
        }
        authCallStatusByContract.put("AUTH_CALL_STATUS_NAME", authCallStatusName);
        if(authCallOwnerStatusName == null)
        {
          authCallOwnerStatusName = "";
        }
        authCallStatusByContract.put("AUTH_CALL_OWNER_STATUS_NAME", authCallOwnerStatusName);
        if(authCallPOSStatusName == null)
        {
          authCallPOSStatusName = "";
        }
        authCallStatusByContract.put("AUTH_CALL_POS_STATUS_NAME", authCallPOSStatusName);
      }
      Utility.closeConnection(con);
      return authCallStatusByContract;
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
    return null;
  }

/*
 * 8-Insert New Authentication Call record in the database with the following data
 *    (authCallStatusID, userID, contractStatusID). 
 *    It return the new authentication call id as a String object.
 */
  public static String insertAuthCall(String authCallStatusID,
                                    String userID, String contractStatusID)
  {
    String authCallId = "";
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      String getNewSeqValue ="select SEQ_AUTH_CALL_ID.nextval from dual";
      ResultSet res = stat.executeQuery(getNewSeqValue);
      res.next();

      authCallId = res.getString(1);
      
      String insertSql = "insert into VW_ATH_AUTH_CALL_INSERT(AUTH_CALL_ID, "+
                         "AUTH_CALL_DATE, AUTH_CALL_STATUS_ID, AUTH_CALL_USER_ID, CONTRACT_STATUS_ID) "+
                         "values("+ authCallId+",sysdate,"+authCallStatusID+","+userID+", "+contractStatusID+")";                            
      stat.execute(insertSql);          
      //SheetDao.updateSheetStatusInContractVerification(sheetId,batchId);      
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
    return authCallId;
  }

/*
 * 9- Insert Authentication Call Parameter record in the database with the following data
 *     (paramOwnerStatusID, paramPOSStatusID, authCallID).
 */
  public static void insertAuthCallParam(String paramOwnerStatusID, 
                                           String paramPOSStatusID,
                                           String authCallID)
  {
    String authCallParamId = null;
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      String getNewSeqValue ="select SEQ_AUTH_CALL_PARAM_ID.nextval from dual";
      ResultSet res = stat.executeQuery(getNewSeqValue);
      res.next();

      authCallParamId = res.getString(1);
      
      String insertSql = "insert into VW_ATH_AUTH_CALL_PARAM_INSERT(AUTH_CALL_PARAM_ID, "+
                         "PARAM_OWNER_STATUS_ID, PARAM_POS_STATUS_ID, AUTH_CALL_ID) "+
                         "values("+ authCallParamId+","+paramOwnerStatusID+","+paramPOSStatusID+","+authCallID+")";                            
      stat.execute(insertSql);         

      
      //SheetDao.updateSheetStatusInContractVerification(sheetId,batchId);      
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static Vector getAllAuthTempStatuses()
  {
    Vector authTempStatusVector = new Vector();
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery("select * from TMP_AUTHENTICATION_STATUS");
      while (res.next())
      {
        authTempStatusVector.add(new AuthTempStatusModel(res));
      }
      Utility.closeConnection(con);
      return authTempStatusVector;
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
    return null;
  }

  public static Vector getAllSheetPercentageTemp()
  {
    Vector sheetTempPercentageVector = new Vector();
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      ResultSet res = stat.executeQuery("select * from TMP_SHEET_PERCENTAGE");
      while (res.next())
      {
        sheetTempPercentageVector.add(new SheetTempPercentageModel(res));
      }
      Utility.closeConnection(con);
      return sheetTempPercentageVector;
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
    return null;
  }

}
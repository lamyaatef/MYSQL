package com.mobinil.sds.core.system.gn.scopes.dao;

import com.mobinil.sds.core.system.gn.scopes.model.*;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.web.interfaces.gn.scopes.*;

public class ScopesDAO 
{
  public ScopesDAO()
  {
  }

  public static Vector getAllScopes()
  {
      Vector funVec = new Vector();
      try
      {
       Connection con = Utility.getConnection();
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_ADH_DATA_VIEW c where DATA_VIEW_TYPE_ID = 1 and DATA_VIEW_VERSION  = (select max(b.DATA_VIEW_VERSION) from VW_ADH_DATA_VIEW b where b.DATA_VIEW_ISSUE = c.DATA_VIEW_ISSUE) order by c.DATA_VIEW_STATUS_ID , UPPER(c.DATA_VIEW_NAME)");
       while(res.next())
       {
         funVec.add(new ScopesModel(res));
       }
       stat.close();
       Utility.closeConnection(con);        
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return funVec; 
  }
  public static Vector getAllDataViews()
  {
      Vector funVec = new Vector();
      try
      {
       Connection con = Utility.getConnection();
       Statement stat = con.createStatement();
       String str = "select * from VW_ADH_DATA_VIEW c where DATA_VIEW_TYPE_ID = 2 and DATA_VIEW_VERSION  = (select max(b.DATA_VIEW_VERSION) from VW_ADH_DATA_VIEW b where b.DATA_VIEW_ISSUE = c.DATA_VIEW_ISSUE) order by c.DATA_VIEW_STATUS_ID , UPPER(c.DATA_VIEW_NAME)";
       Utility.logger.debug(str);
       ResultSet res= stat.executeQuery(str);     
       while(res.next())
       {
         funVec.add(new ScopesModel(res));
       }
       stat.close();
       Utility.closeConnection(con);        
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return funVec; 
  }
  public static Vector getAllScopeStatus()
  {
    long startTime = System.currentTimeMillis();

    Object obj =   CachEngine.getObject(ScopesInterfaceKey.CACH_OBJ_SCOPES_STATUS);
    Vector scopeStatusVec =null;

    if (obj ==null)
    {
  
      scopeStatusVec = new Vector(); 
      try
      {
       Connection con = Utility.getConnection();
       Statement stat = con.createStatement();
       ResultSet res= stat.executeQuery("select * from VW_ADH_DATA_VIEW_STATUS order by DATA_VIEW_STATUS_ID");     
       while(res.next())
       {
         scopeStatusVec.add(new ScopeStatusModel(res));
       }
       stat.close();
       Utility.closeConnection(con);   
       CachEngine.storeObject(ScopesInterfaceKey.CACH_OBJ_SCOPES_STATUS, scopeStatusVec); 
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
    }
    else 
    {
      scopeStatusVec = (Vector) obj;
    }
    Utility.logger.debug("took "+( System.currentTimeMillis() - startTime));
    return scopeStatusVec; 
  } 

public static void insertScope(Long ScopeID,String ScopeName,int intScopeStatusId,int intScopeIssue,int intScopeVersion,String ScopeDescription)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      Statement stat2 = con.createStatement();
      
      String insertSql = "insert into ADH_DATA_VIEW(DATA_VIEW_ID, "+
                         "DATA_VIEW_ISSUE, DATA_VIEW_VERSION,DATA_VIEW_NAME, DATA_VIEW_OVERRIDE_SQL, DATA_VIEW_TYPE_ID , DATA_VIEW_DESCRIPTION , DATA_VIEW_STATUS_ID , DATA_VIEW_UNIQUE) "+
                         "values("+ScopeID+","+intScopeIssue+","+intScopeVersion+",'"+ScopeName+"','',1,'"+ScopeDescription+"',"+intScopeStatusId+",null)";                            
      Utility.logger.debug("SQL : "+ insertSql)                         ;
      stat.execute(insertSql);  
      
      String insertScopeInUniverse = "insert into ADH_UNIVERSE_DEFINITION(UNIVERSE_ID,DATA_VIEW_ID) "+
                                     " values(1,"+ScopeID+")"; 
      stat2.execute(insertScopeInUniverse);  
              
      //SheetDao.updateSheetStatusInContractVerification(sheetId,batchId); 
      stat.close();
      stat2.close();
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void insertScopeFields(Long dataViewId,Long FieldId,String FieldName,String FieldDescription,String FieldSQLCash,int ItemPosition)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      
      String insertSql = "insert into ADH_FIELD(DATA_VIEW_ID,FIELD_ID,FIELD_NAME,FIELD_DESCRIPTION,FIELD_SQL_CASH,FIELD_DISPLAY_TYPE_ID,FIELD_TYPE_ID,ITEM_POSITION) "+
                         "values("+dataViewId+","+FieldId+",'"+FieldName+"','"+FieldDescription+"','"+FieldSQLCash+"',1,1,"+ItemPosition+")";                            
      Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
      //SheetDao.updateSheetStatusInContractVerification(sheetId,batchId);      
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static Vector getScopeById(int scopeid)
  {
    Vector scopeVec = new Vector();
    try
    {
     Connection con = Utility.getConnection();
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_ADH_DATA_VIEW where DATA_VIEW_ID="+scopeid+" ");     
     while(res.next())
     {
       scopeVec.add(new ScopesModel(res));
     }
     stat.close();
     Utility.closeConnection(con);        
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return scopeVec; 
  }  

  public static Vector getScopeFields(int scopeid)
  {
    Vector scopeVec = new Vector();
    try
    {
     Connection con = Utility.getConnection();
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_ADH_FIELD where DATA_VIEW_ID="+scopeid+" order by ITEM_POSITION");
     while(res.next())
     {
       scopeVec.add(new FieldsModel(res));
     }
     stat.close();
     Utility.closeConnection(con);        
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return scopeVec; 
  }
  
  public static void updateScope(int ScopeID,String ScopeName,int intScopeStatusId,int intScopeIssue,int intScopeVersion,String ScopeDescription)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      
      String insertSql = "update ADH_DATA_VIEW set "+
                         "DATA_VIEW_ISSUE = "+intScopeIssue+", "+
                         "DATA_VIEW_VERSION = "+intScopeVersion+", "+
                         "DATA_VIEW_NAME = '"+ScopeName+"', "+
                         "DATA_VIEW_DESCRIPTION = '"+ScopeDescription+"', "+
                         "DATA_VIEW_STATUS_ID = "+intScopeStatusId+" "+
                         "where DATA_VIEW_ID = "+ScopeID+" ";                            
      Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
      //SheetDao.updateSheetStatusInContractVerification(sheetId,batchId);      
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }  

  public static void deleteScopeField(int FieldID)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      
      String insertSql = "delete from ADH_FIELD where FIELD_ID = "+FieldID+"";                            
      Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
      //SheetDao.updateSheetStatusInContractVerification(sheetId,batchId);      
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }
  }

  public static void updateScopeFields(int FieldId,String FieldName,String FieldDescription,String FieldSQLCash,int ItemPosition)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      
      String insertSql = "update ADH_FIELD set "+
                         "FIELD_NAME = '"+FieldName+"' ,"+
                         "FIELD_DESCRIPTION = '"+FieldDescription+"' ,"+
                         "FIELD_SQL_CASH = '"+FieldSQLCash+"' ,"+
                         "ITEM_POSITION = "+ItemPosition+" "+
                         "where FIELD_ID = "+FieldId+" ";                            
      Utility.logger.debug(insertSql);
      stat.execute(insertSql);          
      //SheetDao.updateSheetStatusInContractVerification(sheetId,batchId);      
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void updateDataViewStatus(Connection con,String DataViewId,String DataViewStatusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update ADH_DATA_VIEW set "+
                         "DATA_VIEW_STATUS_ID = "+DataViewStatusId+" "+
                         "where DATA_VIEW_ID = "+DataViewId+" ";                            
      stat.execute(insertSql);
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }
  public static String checkDataViewReportsChangeFromActive(Connection con,String dataViewId) 
  {
    String bolReportStatus = "Success";
    try
    {
      Statement stat = con.createStatement();
      String sql = " select * from ADH_REPORT where DATA_VIEW_ID = "+dataViewId+" ";
      ResultSet res = stat.executeQuery(sql);
      while(res.next()) 
      {
        int reportStatus = res.getInt("REPORT_STATUS_ID");
        String reportName = res.getString("REPORT_NAME");
        if(reportStatus == 1) 
        {
          if(bolReportStatus == "Success")
          {
            bolReportStatus = "&&";
          }
          bolReportStatus += reportName+"&&";
        }
      }
      res.close();
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    } 
    return bolReportStatus;
  }

  public static String checkDataViewFrmChangeFromActive(Connection con,String dataViewId) 
  {
    String bolReturnValue = "Success";
    try
    {
      ////////////////////check one level down///////////////////
      Statement stat = con.createStatement();
      String sql = " select * from VW_ADH_FROM where FR_DATA_VIEW_ID = "+dataViewId+" ";
      ResultSet res = stat.executeQuery(sql);
      while(res.next()) 
      {
        int dataviewStatus = res.getInt("DATA_VIEW_STATUS_ID");
        String dataviewName = res.getString("DATA_VIEW_NAME");
        if(dataviewStatus == 1) 
        {
          if(bolReturnValue == "Success") 
          {
              bolReturnValue = "&&";
          }
          bolReturnValue += dataviewName+"&&";
        }
      }
      res.close();
      stat.close();  
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    } 
    return bolReturnValue;
  } 

   public static String checkDataViewFrmChangeFromInActive(Connection con,String dataViewId) 
  {
    String bolReturnValue = "Success";
    try
    {
      ////////////////////check one level up///////////////////
      Statement stat2 = con.createStatement();
      //select * from VW_ADH_DATA_VIEW c where DATA_VIEW_VERSION  = (select max(b.DATA_VIEW_VERSION) from VW_ADH_DATA_VIEW b where b.DATA_VIEW_ISSUE = c.DATA_VIEW_ISSUE) order by c.DATA_VIEW_STATUS_ID , UPPER(c.DATA_VIEW_NAME)
      String sql2 = " select * from VW_ADH_FROM where DATA_VIEW_ID = "+dataViewId+" ";
      ResultSet res2 = stat2.executeQuery(sql2);
      while(res2.next()) 
      {
        int frDataviewStatus = res2.getInt("FR_DATA_VIEW_STATUS_ID");
        String frDataviewName = res2.getString("FR_DATA_VIEW_NAME");
        if(frDataviewStatus == 2) 
        {
          if(bolReturnValue == "Success") 
          {
            bolReturnValue = "&&";
          }
          bolReturnValue += frDataviewName+"&&";
        }
      }
      res2.close();
      stat2.close();     
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    } 
    return bolReturnValue;
  } 
  
}
package com.mobinil.sds.core.system.fn.importdata.dao;
import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.core.system.fn.importdata.model.*;
import com.mobinil.sds.core.system.fn.addfunction.model.*;
import com.mobinil.sds.web.interfaces.fn.*;

public class DataImportTableFunctionsDAO 
{
  public DataImportTableFunctionsDAO()
  {
  }

 public static Vector getAllTableFunctions()
  {
  long startTime = System.currentTimeMillis();

  Object obj =   CachEngine.getObject(FunctionsInterfaceKey.CACH_OBJ_FUNCTION_LIST);
  Vector funVec =null;

  if (obj ==null)
  {
  
    funVec= new Vector();   
  
    try
    {
     Connection con = Utility.getConnection();
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_ADH_FUNCTION order by FUNCTION_TYPE_NAME,FUNCTION_NAME");     
     while(res.next())
     {
       funVec.add(new DataImportTableFunctionsModel(res));
     }
     stat.close();
     Utility.closeConnection(con);        
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    CachEngine.storeObject(FunctionsInterfaceKey.CACH_OBJ_FUNCTION_LIST , funVec); 
  } 
  else 
  {
    funVec = (Vector) obj;
  }
    Utility.logger.debug("took "+( System.currentTimeMillis() - startTime));
    return funVec; 
  }

  public static Vector getAllFunctionTypes()
  {
     long startTime = System.currentTimeMillis();

    Object obj =   CachEngine.getObject(FunctionsInterfaceKey.CACH_OBJ_FUNCTION_TYPES );
    Vector funTypeVec =null;

    if (obj ==null)
    {
  
        funTypeVec= new Vector(); 
        try
        {
         Connection con = Utility.getConnection();
         Statement stat = con.createStatement();
         ResultSet res= stat.executeQuery("select * from ADH_FUNCTION_TYPE");     
         while(res.next())
         {
           funTypeVec.add(new functiontypeModel(res));
         }
         stat.close();
         Utility.closeConnection(con);        
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        CachEngine.storeObject(FunctionsInterfaceKey.CACH_OBJ_FUNCTION_TYPES , funTypeVec);         
    }
    else 
    {
      funTypeVec = (Vector) obj;
    }
    Utility.logger.debug("took "+( System.currentTimeMillis() - startTime));
    return funTypeVec; 
  }

  public static Vector getAllFunctionStatus()
  {
    long startTime = System.currentTimeMillis();

    Object obj =   CachEngine.getObject(FunctionsInterfaceKey.CACH_OBJ_FUNCTION_STATUS );
    Vector funStatusVec =null;

    if (obj ==null)
    {
  
        funStatusVec= new Vector(); 
        try
        {
         Connection con = Utility.getConnection();
         Statement stat = con.createStatement();
         ResultSet res= stat.executeQuery("select * from ADH_FUNCTION_STATUS");     
         while(res.next())
         {
           funStatusVec.add(new functionstatusModel(res));
         }
         stat.close();
         Utility.closeConnection(con);        
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        CachEngine.storeObject(FunctionsInterfaceKey.CACH_OBJ_FUNCTION_STATUS , funStatusVec); 
    }
    else 
    {
      funStatusVec = (Vector) obj;
    }
    Utility.logger.debug("took "+( System.currentTimeMillis() - startTime));
    return funStatusVec; 
  }
  public static void insertFunction(Long FunctionID,String FunctionName,int FunctionTypeId,int FunctionStatusId,String FunctionSQLCall,String FunctionHelpText,String FunctionDescription)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      
      String insertSql = "insert into ADH_FUNCTION(FUNCTION_ID, "+
                         "FUNCTION_NAME, FUNCTION_TYPE_ID,FUNCTION_STATUS_ID, FUNCTION_SQL_CALL, FUNCTION_HELP_TEXT,FUNCTION_DESCRIPTION) "+
                         "values("+FunctionID+",'"+FunctionName+"',"+FunctionTypeId+","+FunctionStatusId+",'"+FunctionSQLCall+"','"+FunctionHelpText+"','"+FunctionDescription+"')";                            
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

    public static void insertFunctionParameters(Long FunctionID,Long ParameterID,String ParameterName,String ParameterDesc)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      
      String insertSql = "insert into ADH_PARAMETER_DEFINITION(PARAMETER_DEFINITION_ID,FUNCTION_ID, "+
                         "PARAMETER_DEFINITION_NAME, PARAMETER_DEFINITION_DESC,PARAMETER_DEFINITION_OPTIONAL, PARAMETER_DEFINITION_IS_LIST) "+
                         "values("+ParameterID+","+FunctionID+",'"+ParameterName+"','"+ParameterDesc+"',0,0)";                            
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

  public static Vector getFunctionById(int functionid)
  {
    Vector funVec = new Vector();
    try
    {
     Connection con = Utility.getConnection();
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_ADH_FUNCTION where FUNCTION_ID="+functionid+" order by FUNCTION_TYPE_NAME,FUNCTION_NAME");     
     while(res.next())
     {
       funVec.add(new DataImportTableFunctionsModel(res));
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

  public static Vector getFunctionParameters(int functionid)
  {
    Vector funVec = new Vector();
    try
    {
     Connection con = Utility.getConnection();
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from ADH_PARAMETER_DEFINITION where FUNCTION_ID="+functionid+"");
     while(res.next())
     {
       funVec.add(new FunctionParameterModel(res));
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

  public static void updateFunction(int FunctionID,String FunctionName,int FunctionTypeId,int FunctionStatusId,String FunctionSQLCall,String FunctionHelpText,String FunctionDescription)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      
      String insertSql = "update ADH_FUNCTION set FUNCTION_NAME = '"+FunctionName+"', FUNCTION_TYPE_ID = "+FunctionTypeId+", "+
                         " FUNCTION_STATUS_ID = "+FunctionStatusId+", FUNCTION_SQL_CALL = '"+FunctionSQLCall+"', FUNCTION_HELP_TEXT = '"+FunctionHelpText+"', "+
                         " FUNCTION_DESCRIPTION = '"+FunctionDescription+"' "+
                         " where FUNCTION_ID = "+FunctionID+" ";                            
      
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

  public static void deleteFunctionParameters(int ParameterID)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      
      String insertSql = "delete from ADH_PARAMETER_DEFINITION where PARAMETER_DEFINITION_ID = "+ParameterID+"";                            
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
  
  public static void updateFunctionParameters(int ParameterID,String ParameterName,String ParameterDesc)
  {
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
 
      String insertSql = "update ADH_PARAMETER_DEFINITION "+
                         " set PARAMETER_DEFINITION_NAME = '"+ParameterName+"' ,"+
                         " PARAMETER_DEFINITION_DESC = '"+ParameterDesc+"' "+  
                         " where PARAMETER_DEFINITION_ID = "+ParameterID+"";                            
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
  
}
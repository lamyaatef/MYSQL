package com.mobinil.sds.core.system.sa.importdata.dao;
import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.system.sa.importdata.model.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.system.sa.importdata.model.DataImportTableDefModel;


public class DataImportTableDefDAO 
{
//turn this flag to false before deployment
  private static final boolean debugFlag = false;

  private void debug(String s)
  {
    if (debugFlag)
    Utility.logger.debug(s);
  }


  private DataImportTableDefDAO()
  {
  }

/*
 * this method return a vector of DataImportTableDefModel of all the tables defined in the view 
 * vw_adm_data_import_def
 * 
 */
  public static Vector getAllTableDef()
  {
    Vector tableVec = new Vector();
    try
    {
     Connection con = Utility.getConnection();
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from vw_ADM_DATA_IMPORT_DEF"); 
     //after upload the excel shhet get the data from data view and put it in the resultset (res)
     while(res.next())
     {
       tableVec.add(new DataImportTableDefModel(res));
     }
     stat.close();
     Utility.closeConnection(con);        
    }
    catch(Exception e)
    {
    if (debugFlag)
    e.printStackTrace();
    }

    return tableVec; 
  }
  
  public static Vector getTableDefByCategory(String categoryId)
  {
    Vector tableVec = new Vector();
    try
    {
     Connection con = Utility.getConnection();
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from vw_ADM_DATA_IMPORT_DEF where TABLE_CATEGORY_ID = "+categoryId+" ");     
     while(res.next())
     {
       tableVec.add(new DataImportTableDefModel(res));
     }
     stat.close();
     Utility.closeConnection(con);        
    }
    catch(Exception e)
    {
    if (debugFlag)
    e.printStackTrace();
    }

    return tableVec; 
  }

  public static DataImportTableDefModel getTableDef(String tableId,int tableCategory)
  {
    System.out.println("get table DEF  ");
    DataImportTableDefModel tableDefModel = null;
    try
    {
     Connection con = Utility.getConnection();
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from vw_ADM_DATA_IMPORT_DEF where table_id="+tableId+" and table_category_id = "+tableCategory);     
     if (res.next())
     {
      tableDefModel =  new DataImportTableDefModel(res);
     }
     stat.close();
     Utility.closeConnection(con);        
    }
    catch(Exception e)
    {
    if (debugFlag)
    e.printStackTrace();
    }

    return tableDefModel; 
  }
  
  
  public static DataImportTableDefModel getTableDef(String tableId)
  {
    System.out.println("get table DEF  ");
    DataImportTableDefModel tableDefModel = null;
    try
    {
     Connection con = Utility.getConnection();
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from vw_ADM_DATA_IMPORT_DEF where table_id="+tableId);     
     if (res.next())
     {
      tableDefModel =  new DataImportTableDefModel(res);
     }
     stat.close();
     Utility.closeConnection(con);        
    }
    catch(Exception e)
    {
    if (debugFlag)
    e.printStackTrace();
    }

    return tableDefModel; 
  }
  
  
}
package com.mobinil.sds.core.system.cr.importlog.dao;

/**
 * ImportLogDAO Class is used to return more than one record of import logs.
 * 
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import com.mobinil.sds.core.system.cr.importlog.model.*;
import com.mobinil.sds.core.utilities.*;

import java.sql.*;

import java.util.*;
public class ImportLogDAO 
{
  private ImportLogDAO()
  {
  }

  /**
   * SearchImportLog method:
   * 
   * Returns a vector of ImportLogModel for this DCM and date.
   * 
   * @param	String dcmId, String date
   * @return Vector
   * @throws  
   * @see    
  */	

  public static final Vector SearchImportLog(String dcmId, String date)
  {  
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      String sql = "sELECT EXCEL_IMPORT_LOG_ID, DCM_ID,to_char(EXCEL_IMPORT_LOG_DATE,'dd/mm/yyyy') IMPORT_LOG_DATE , to_char(EXCEL_IMPORT_LOG_DATE,'hh:mi:ssam')import_log_time ,FILE_NAME,EXCEL_LOG_HTML " +
                   " FROM VW_CR_EXCEL_IMPORT_LOG"+
                   " WHERE DCM_ID = " +dcmId;
      
      if (date!=null && date.length()>0)
      {
        sql+=" and to_char(EXCEL_IMPORT_LOG_DATE,\'dd/mm/yyyy\') = '"+ date+"'";
      }

      sql += " order by excel_import_log_date desc";
      Utility.logger.debug( sql );

      ResultSet res = stat.executeQuery( sql);

                                        
      Vector importLogCol = new Vector();      
      while (res.next())
      {
        String excelImportLogID = res.getString("EXCEL_IMPORT_LOG_ID");
        String DCMId = res.getString("dcm_id");
        String excelImportDate = res.getString("IMPORT_LOG_DATE");
        String excelImportTime = res.getString("IMPORT_LOG_TIME");
        String fileName=  res.getString("FILE_NAME");

        ImportLogModel importLogModel = new ImportLogModel(excelImportLogID, 
                                                           DCMId, 
                                                           excelImportDate,excelImportTime,
                                                           fileName,res.getBlob("EXCEL_LOG_HTML"));
        importLogCol.add(importLogModel);
      }      
      Utility.closeConnection(con);
      return importLogCol;
    }
    catch (Exception e)
    {
    e.printStackTrace();
    }
    return null;
  }


  public static final ImportLogModel SearchImportLogByID(Connection con,String logId)
  {
    try
    {
      
      Statement stat = con.createStatement();
      String sql = "sELECT EXCEL_IMPORT_LOG_ID, DCM_ID,to_char(EXCEL_IMPORT_LOG_DATE,'dd/mm/yyyy') IMPORT_LOG_DATE , to_char(EXCEL_IMPORT_LOG_DATE,'hh:mi:ssam')import_log_time ,FILE_NAME,EXCEL_LOG_HTML " +
                   " FROM VW_CR_EXCEL_IMPORT_LOG"+
                   " WHERE EXCEL_IMPORT_LOG_ID = " +logId;

      Utility.logger.debug( sql );

      ResultSet res = stat.executeQuery( sql);


      ImportLogModel importLogModel = null;
      if(res.next())
      {
        String excelImportLogID = res.getString("EXCEL_IMPORT_LOG_ID");
        String DCMId = res.getString("dcm_id");
        String excelImportDate = res.getString("IMPORT_LOG_DATE");
        String excelImportTime = res.getString("IMPORT_LOG_TIME");
        String fileName=  res.getString("FILE_NAME");

         importLogModel = new ImportLogModel(excelImportLogID,
                                                           DCMId,
                                                           excelImportDate,excelImportTime,
                                                           fileName,res.getBlob("EXCEL_LOG_HTML"));

      }
      
      return importLogModel;
    }
    catch (Exception e)
    {
    e.printStackTrace();
    }
    return null;
  }
  
}
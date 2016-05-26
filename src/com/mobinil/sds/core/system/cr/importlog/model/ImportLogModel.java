package com.mobinil.sds.core.system.cr.importlog.model;
import java.io.Serializable;
import java.sql.Blob;

/**
 * ImportLogModel Class holds all the data of one excel import log:
 * 1- m_strDCMID
 * 2- m_strExcelImportLogID
 * 3- m_strExcelImportLogDate
 * 4- m_strFileName
 *
 * It has four constructors
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

public class ImportLogModel implements Serializable 
{
  private String m_strDCMID;
  private String m_strExcelImportLogID;
  private String m_strExcelImportLogDate;
  private String m_strFileName;
  private String m_strExcelImportLogTime;
  private Blob m_blbExcelImportLogfile;
/*
  public ImportLogModel(String argExcelImportLogID)
  {
    m_strExcelImportLogID = argExcelImportLogID;
  }
*/
/*
  public ImportLogModel(String argExcelImportLogID, String argDCMID)
  {
    m_strExcelImportLogID = argExcelImportLogID;
    m_strDCMID = argDCMID;
  }
  */
/*
  public ImportLogModel(String argExcelImportLogID,String argDCMID, 
                        String argExcelImportLogDate)
  {
    m_strExcelImportLogID = argExcelImportLogID;
    m_strDCMID = argDCMID;
    m_strExcelImportLogDate = argExcelImportLogDate;
  }
*/
  public ImportLogModel(String argExcelImportLogID,String argDCMID, 
                        String argExcelImportLogDate, String argImportLogTime,String argFileName,Blob m_blbExcelImportLogfile)
  {
    m_strExcelImportLogID = argExcelImportLogID;
    m_strDCMID = argDCMID;
    m_strExcelImportLogDate = argExcelImportLogDate;
    m_strFileName = argFileName;
    this.m_strExcelImportLogTime = argImportLogTime;
    this.m_blbExcelImportLogfile = m_blbExcelImportLogfile;
  }

  public String getDCMID()
  {
    return m_strDCMID;
  }

  public void setDCMID(String argDCMID)
  {
    m_strDCMID = argDCMID;
  }

  public String getExcelImportLogID()
  {
    return m_strExcelImportLogID;
  }

  public void setExcelImportLogID(String argExcelImportLogID)
  {
    m_strExcelImportLogID = argExcelImportLogID;
  }

  public String getExcelImportLogDate()
  {
    return m_strExcelImportLogDate;
  }

  public void setExcelImportLogDate(String argExcelImportLogDate)
  {
    m_strExcelImportLogDate = argExcelImportLogDate;
  }

  public String getFileName()
  {
    return m_strFileName;
  }

  public void setFileName(String argFileName)
  {
    m_strFileName = argFileName;
  }

  public String getExcelImportLogTime()
  {
    return m_strExcelImportLogTime;
  }

  public void setExcelImportLogTime(String newM_strExcelImportLogTime)
  {
    m_strExcelImportLogTime = newM_strExcelImportLogTime;
  }

    /**
     * @return the m_blbExcelImportLogfile
     */
    public Blob getM_blbExcelImportLogfile() {
        return m_blbExcelImportLogfile;
    }

    /**
     * @param m_blbExcelImportLogfile the m_blbExcelImportLogfile to set
     */
    public void setM_blbExcelImportLogfile(Blob m_blbExcelImportLogfile) {
        this.m_blbExcelImportLogfile = m_blbExcelImportLogfile;
    }
}
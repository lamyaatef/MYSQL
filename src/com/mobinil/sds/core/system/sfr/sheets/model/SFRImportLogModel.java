package com.mobinil.sds.core.system.sfr.sheets.model;
import java.io.Serializable;

public class SFRImportLogModel implements Serializable
{
  private String m_strExcelImportLogID;
  private String m_strExcelImportLogDate;
  private String m_strFileName;
  private String m_strExcelImportLogTime;
  
  public SFRImportLogModel()
  {
  }

  public SFRImportLogModel(String argExcelImportLogID,
                        String argExcelImportLogDate, String argImportLogTime,String argFileName)
  {
    m_strExcelImportLogID = argExcelImportLogID;
    m_strExcelImportLogDate = argExcelImportLogDate;
    m_strFileName = argFileName;
    this.m_strExcelImportLogTime = argImportLogTime;
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
}
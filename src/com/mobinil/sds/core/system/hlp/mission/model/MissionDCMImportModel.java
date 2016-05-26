package com.mobinil.sds.core.system.hlp.mission.model;
import java.sql.*;
import java.io.*;

public class MissionDCMImportModel implements Serializable
{
  String rowNum;
  String missionId;
  String dcmCode;

  public static final String ROW_NUM = "ROW_NUM";
  public static final String MISSION_ID = "MISSION_ID";
  public static final String DCM_CODE = "DCM_CODE";
  
  public MissionDCMImportModel()
  {
  }

  public MissionDCMImportModel(ResultSet res)
  {
    try
    {
      rowNum = res.getString(ROW_NUM);
      missionId = res.getString(MISSION_ID);
      dcmCode = res.getString(DCM_CODE);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }   
  }

  public String getRowNum()
  {
  return rowNum;
  }
  public void setRowNum(String newRowNum)
  {
  rowNum = newRowNum;
  }
  
  public String getMissionId()
  {
  return missionId;
  }
  public void setMissionId(String newMissionId)
  {
  missionId= newMissionId;
  }

  public String getDcmCode()
  {
  return dcmCode;
  }
  public void setDcmCode(String newDcmCode)
  {
  dcmCode= newDcmCode;
  }
}
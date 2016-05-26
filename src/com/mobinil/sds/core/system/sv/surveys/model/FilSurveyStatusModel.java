package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class FilSurveyStatusModel implements Serializable 
{
  String filSurveyStatusId;
  String filSurveyStatusName;

  public static final String FIL_SURVEY_STATUS_ID = "FIL_SURVEY_STATUS_ID";
  public static final String FIL_SURVEY_STATUS_NAME = "FIL_SURVEY_STATUS_NAME";
  
  public FilSurveyStatusModel()
  {
  }

  public FilSurveyStatusModel(ResultSet res)
  {
      try
      {
          filSurveyStatusId = res.getString(FIL_SURVEY_STATUS_ID);
          filSurveyStatusName = res.getString(FIL_SURVEY_STATUS_NAME);        
      }
      catch(Exception e)
      {
        
      }  
  } 

  public String getFilSurveyStatusId()
  {
    return filSurveyStatusId;
  }

  public void setFilSurveyStatusId(String newFilSurveyStatusId)
  {
    filSurveyStatusId = newFilSurveyStatusId;
  }
////////////////////////////////////////////
  public String getFilSurveyStatusName()
  {
    return filSurveyStatusName;
  }

  public void setFilSurveyStatusName(String newFilSurveyStatusName)
  {
    filSurveyStatusName = newFilSurveyStatusName;
  }
////////////////////////////////////////////   
}
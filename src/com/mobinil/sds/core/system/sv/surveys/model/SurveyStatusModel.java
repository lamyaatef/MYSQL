package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class SurveyStatusModel implements Serializable 
{
  String surveyStatusId;
  String surveyStatusName;

  public static final String SURVEY_STATUS_ID = "SURVEY_STATUS_ID";
  public static final String SURVEY_STATUS_NAME = "SURVEY_STATUS_NAME";
  
  public SurveyStatusModel()
  {
  }

  public SurveyStatusModel(ResultSet res)
  {
      try
      {
          surveyStatusId = res.getString(SURVEY_STATUS_ID);
          surveyStatusName = res.getString(SURVEY_STATUS_NAME);        
      }
      catch(Exception e)
      {
        
      }
  }

  public String getSurveyStatusId()
  {
    return surveyStatusId;
  }

  public void setSurveyStatusId(String newSurveyStatusId)
  {
    surveyStatusId = newSurveyStatusId;
  }
////////////////////////////////////////////
  public String getSurveyStatusName()
  {
    return surveyStatusName;
  }

  public void setSurveyStatusName(String newSurveyStatusName)
  {
    surveyStatusName = newSurveyStatusName;
  }
////////////////////////////////////////////   
}
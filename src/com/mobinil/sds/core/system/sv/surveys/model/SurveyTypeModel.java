package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class SurveyTypeModel implements Serializable
{
  String surveyTypeId;
  String surveyTypeName;
  String surveyCategoryId;
  String surveyCategoryName;
  String surveyTypeStatusId;
  String surveyTypeStatusName;
  
  public static final String SURVEY_TYPE_ID = "SURVEY_TYPE_ID";
  public static final String SURVEY_TYPE_NAME = "SURVEY_TYPE_NAME";
  public static final String SURVEY_CATEGORY_ID = "SURVEY_CATEGORY_ID";
  public static final String SURVEY_CATEGORY_NAME = "SURVEY_CATEGORY_NAME";
  public static final String SURVEY_TYPE_STATUS_ID = "SURVEY_TYPE_STATUS_ID";
  public static final String SURVEY_TYPE_STATUS_NAME = "SURVEY_TYPE_STATUS_NAME";
  
  public SurveyTypeModel()
  {
  }

  public SurveyTypeModel(ResultSet res)
  {
      try
      {
          surveyTypeId = res.getString(SURVEY_TYPE_ID);
          surveyTypeName = res.getString(SURVEY_TYPE_NAME);
          surveyCategoryId = res.getString(SURVEY_CATEGORY_ID);
          surveyCategoryName = res.getString(SURVEY_CATEGORY_NAME);
          surveyTypeStatusId = res.getString(SURVEY_TYPE_STATUS_ID);
          surveyTypeStatusName = res.getString(SURVEY_TYPE_STATUS_NAME);
      }
      catch(Exception e)
      {
        
      }
  }

  public String getSurveyTypeId()
  {
    return surveyTypeId;
  }

  public void setSurveyTypeId(String newSurveyTypeId)
  {
    surveyTypeId = newSurveyTypeId;
  }
//////////////////////////////////////////// 
  public String getSurveyTypeName()
  {
    return surveyTypeName;
  }

  public void setSurveyTypeName(String newSurveyTypeName)
  {
    surveyTypeName = newSurveyTypeName;
  }
////////////////////////////////////////////   
  public String getSurveyCategoryId()
  {
    return surveyCategoryId;
  }

  public void setSurveyCategoryId(String newSurveyCategoryId)
  {
    surveyCategoryId = newSurveyCategoryId;
  }
//////////////////////////////////////////// 
  public String getSurveyCategoryName()
  {
    return surveyCategoryName;
  }

  public void setSurveyCategoryName(String newSurveyCategoryName)
  {
    surveyCategoryName = newSurveyCategoryName;
  }
//////////////////////////////////////////// 
  public String getSurveyTypeStatusId()
  {
    return surveyTypeStatusId;
  }

  public void setSurveyTypeStatusId(String newSurveyTypeStatusId)
  {
    surveyTypeStatusId = newSurveyTypeStatusId;
  }
//////////////////////////////////////////// 
  public String getSurveyTypeStatusName()
  {
    return surveyTypeStatusName;
  }

  public void setSurveyTypeStatusName(String newSurveyTypeStatusName)
  {
    surveyTypeStatusName = newSurveyTypeStatusName;
  }
//////////////////////////////////////////// 
}
package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class SurveyModel implements Serializable
{
  String surveyId;
  String surveyName;
  String surveyStatusId;
  String surveyStatusName;
  String surveyTypeId;
  String surveyTypeName;
  String surveyDescription;  
  String surveyCategoryId;
  String surveyCategoryName;
  String surveyTypeStatusId;
  String surveyTypeStatusName;
  String surveyCategoryStatusId;
  String surveyCategoryStatusName;

  public static final String SURVEY_ID = "SURVEY_ID";
  public static final String SURVEY_NAME = "SURVEY_NAME";
  public static final String SURVEY_STATUS_ID = "SURVEY_STATUS_ID";
  public static final String SURVEY_STATUS_NAME = "SURVEY_STATUS_NAME";
  public static final String SURVEY_TYPE_ID = "SURVEY_TYPE_ID";
  public static final String SURVEY_TYPE_NAME = "SURVEY_TYPE_NAME";
  public static final String SURVEY_DESCRIPTION = "SURVEY_DESCRIPTION";  
  public static final String SURVEY_CATEGORY_ID = "SURVEY_CATEGORY_ID";
  public static final String SURVEY_CATEGORY_NAME = "SURVEY_CATEGORY_NAME";
  public static final String SURVEY_TYPE_STATUS_ID = "SURVEY_TYPE_STATUS_ID";
  public static final String SURVEY_TYPE_STATUS_NAME = "SURVEY_TYPE_STATUS_NAME";
  public static final String SURVEY_CATEGORY_STATUS_ID = "SURVEY_CATEGORY_STATUS_ID";
  public static final String SURVEY_CATEGORY_STATUS_NAME = "SURVEY_CATEGORY_STATUS_NAME";
  
  public SurveyModel()
  {
  }

  public SurveyModel(ResultSet res)
  {
      try
      {
          surveyId = res.getString(SURVEY_ID);
          surveyName = res.getString(SURVEY_NAME);
          surveyStatusId = res.getString(SURVEY_STATUS_ID);
          surveyStatusName = res.getString(SURVEY_STATUS_NAME);
          surveyTypeId = res.getString(SURVEY_TYPE_ID);
          surveyTypeName = res.getString(SURVEY_TYPE_NAME);
          surveyDescription = res.getString(SURVEY_DESCRIPTION);  
          surveyCategoryId = res.getString(SURVEY_CATEGORY_ID);
          surveyCategoryName = res.getString(SURVEY_CATEGORY_NAME);
          surveyTypeStatusId = res.getString(SURVEY_TYPE_STATUS_ID);
          surveyTypeStatusName = res.getString(SURVEY_TYPE_STATUS_NAME);
          surveyCategoryStatusId = res.getString(SURVEY_CATEGORY_STATUS_ID);
          surveyCategoryStatusName = res.getString(SURVEY_CATEGORY_STATUS_NAME);
      }
      catch(Exception e)
      {
      
      }
  }

  public String getSurveyId()
  {
    return surveyId;
  }

  public void setSurveyId(String newSurveyId)
  {
    surveyId = newSurveyId;
  }
////////////////////////////////////////////
  public String getSurveyName()
  {
    return surveyName;
  }

  public void setSurveyName(String newSurveyName)
  {
    surveyName = newSurveyName;
  }
//////////////////////////////////////////// 
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
  public String getSurveyDescription()
  {
    return surveyDescription;
  }

  public void setSurveyDescription(String newSurveyDescription)
  {
    surveyDescription = newSurveyDescription;
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
  public String getSurveyCategoryStatusId()
  {
    return surveyCategoryStatusId;
  }

  public void setSurveyCategoryStatusId(String newSurveyCategoryStatusId)
  {
    surveyCategoryStatusId = newSurveyCategoryStatusId;
  }
//////////////////////////////////////////// 
  public String getSurveyCategoryStatusName()
  {
    return surveyCategoryStatusName;
  }

  public void setSurveyCategoryStatusName(String newSurveyCategoryStatusName)
  {
    surveyCategoryStatusName = newSurveyCategoryStatusName;
  }
//////////////////////////////////////////// 

}
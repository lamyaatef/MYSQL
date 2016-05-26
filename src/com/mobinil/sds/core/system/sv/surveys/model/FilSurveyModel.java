package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class FilSurveyModel implements Serializable
{
  String filSurveyId;
  String filSurveyName;
  String filSurveyStatusId;
  String filSurveyStatusName;
  String filSurveyTypeId;
  String surveyTypeName;
  String filSurveyDescription;
  String filSurveyReferenceType;
  String filSurveyReferenceId;
  String filSurveyDate ;
  String filSurveyCompleted;
  String filSurveyValue;
  String surveyId;
  String filSurveyUserId; 
  String surveyCategoryId;
  String surveyCategoryName;
  String surveyTypeStatusId;
  String surveyTypeStatusName;
  String surveyCategoryStatusId;
  String surveyCategoryStatusName;
  String personFullName;
  String filSurveyCompletedTimestamp;
  
  public static final String FIL_SURVEY_ID = "FIL_SURVEY_ID";
  public static final String FIL_SURVEY_NAME = "FIL_SURVEY_NAME";
  public static final String FIL_SURVEY_STATUS_ID = "FIL_SURVEY_STATUS_ID"; 
  public static final String FIL_SURVEY_STATUS_NAME = "FIL_SURVEY_STATUS_NAME"; 
  public static final String FIL_SURVEY_TYPE_ID = "FIL_SURVEY_TYPE_ID";
  public static final String SURVEY_TYPE_NAME = "SURVEY_TYPE_NAME";
  public static final String FIL_SURVEY_DESCRIPTION  = "FIL_SURVEY_DESCRIPTION";
  public static final String FIL_SURVEY_REFERENCE_TYPE = "FIL_SURVEY_REFERENCE_TYPE";
  public static final String FIL_SURVEY_REFERENCE_ID = "FIL_SURVEY_REFERENCE_ID";
  public static final String FIL_SURVEY_DATE  = "FIL_SURVEY_DATE";
  public static final String FIL_SURVEY_COMPLETED = "FIL_SURVEY_COMPLETED";
  public static final String FIL_SURVEY_VALUE = "FIL_SURVEY_VALUE";
  public static final String SURVEY_ID = "SURVEY_ID";
  public static final String FIL_SURVEY_USER_ID  = "FIL_SURVEY_USER_ID";  
  public static final String SURVEY_CATEGORY_ID = "SURVEY_CATEGORY_ID";
  public static final String SURVEY_CATEGORY_NAME = "SURVEY_CATEGORY_NAME";
  public static final String SURVEY_TYPE_STATUS_ID = "SURVEY_TYPE_STATUS_ID";
  public static final String SURVEY_TYPE_STATUS_NAME = "SURVEY_TYPE_STATUS_NAME";
  public static final String SURVEY_CATEGORY_STATUS_ID = "SURVEY_CATEGORY_STATUS_ID";
  public static final String SURVEY_CATEGORY_STATUS_NAME = "SURVEY_CATEGORY_STATUS_NAME";
  public static final String PERSON_FULL_NAME = "PERSON_FULL_NAME";
  public static final String FIL_SURVEY_COMPLETED_TIMESTAMP = "FIL_SURVEY_COMPLETED_TIMESTAMP";
  
  public FilSurveyModel()
  {
  }

  public FilSurveyModel(ResultSet res)
  {
      try
      {
          filSurveyId = res.getString(FIL_SURVEY_ID);
          filSurveyName = res.getString(FIL_SURVEY_NAME);
          filSurveyStatusId = res.getString(FIL_SURVEY_STATUS_ID);
          filSurveyStatusName = res.getString(FIL_SURVEY_STATUS_NAME);
          filSurveyTypeId = res.getString(FIL_SURVEY_TYPE_ID);
          surveyTypeName = res.getString(SURVEY_TYPE_NAME);
          filSurveyDescription = res.getString(FIL_SURVEY_DESCRIPTION);
          filSurveyReferenceType = res.getString(FIL_SURVEY_REFERENCE_TYPE);
          filSurveyReferenceId = res.getString(FIL_SURVEY_REFERENCE_ID);
          filSurveyDate = res.getString(FIL_SURVEY_DATE);
          filSurveyCompleted = res.getString(FIL_SURVEY_COMPLETED);
          filSurveyValue = res.getString(FIL_SURVEY_VALUE);
          surveyId = res.getString(SURVEY_ID);
          filSurveyUserId = res.getString(FIL_SURVEY_USER_ID); 
          surveyCategoryId = res.getString(SURVEY_CATEGORY_ID);
          surveyCategoryName = res.getString(SURVEY_CATEGORY_NAME);
          surveyTypeStatusId = res.getString(SURVEY_TYPE_STATUS_ID);
          surveyTypeStatusName = res.getString(SURVEY_TYPE_STATUS_NAME);
          surveyCategoryStatusId = res.getString(SURVEY_CATEGORY_STATUS_ID);
          surveyCategoryStatusName = res.getString(SURVEY_CATEGORY_STATUS_NAME);
          personFullName = res.getString(PERSON_FULL_NAME);
          filSurveyCompletedTimestamp = res.getString(FIL_SURVEY_COMPLETED_TIMESTAMP);
      }
      catch(Exception e)
      {

      }
  }

  public String getFilSurveyCompletedTimestamp()
  {
    return filSurveyCompletedTimestamp;
  }

  public void setFilSurveyCompletedTimestamp(String newFilSurveyCompletedTimestamp)
  {
    filSurveyCompletedTimestamp = newFilSurveyCompletedTimestamp;
  }
////////////////////////////////////////////
  public String getSurveyId()
  {
    return surveyId;
  }

  public void setSurveyId(String newSurveyId)
  {
    surveyId = newSurveyId;
  }
////////////////////////////////////////////
  public String getFilSurveyId()
  {
    return filSurveyId;
  }

  public void setFilSurveyId(String newFilSurveyId)
  {
    filSurveyId = newFilSurveyId;
  }
////////////////////////////////////////////
  public String getFilSurveyName()
  {
    return filSurveyName;
  }

  public void setFilSurveyName(String newFilSurveyName)
  {
    filSurveyName = newFilSurveyName;
  }
////////////////////////////////////////////
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
  public String getFilSurveyTypeId()
  {
    return filSurveyTypeId;
  }

  public void setFilSurveyTypeId(String newFilSurveyTypeId)
  {
    filSurveyTypeId = newFilSurveyTypeId;
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
  public String getFilSurveyDescription()
  {
    return filSurveyDescription;
  }

  public void setFilSurveyDescription(String newFilSurveyDescription)
  {
    filSurveyDescription = newFilSurveyDescription;
  }
////////////////////////////////////////////
  public String getFilSurveyReferenceType()
  {
    return filSurveyReferenceType;
  }

  public void setFilSurveyReferenceType(String newFilSurveyReferenceType)
  {
    filSurveyReferenceType = newFilSurveyReferenceType;
  }
////////////////////////////////////////////
  public String getFilSurveyReferenceId ()
  {
    return filSurveyReferenceId ;
  }

  public void setFilSurveyReferenceId (String newFilSurveyReferenceId)
  {
    filSurveyReferenceId  = newFilSurveyReferenceId ;
  }
////////////////////////////////////////////
  public String getFilSurveyDate()
  {
    return filSurveyDate;
  }

  public void setFilSurveyDate(String newFilSurveyDate)
  {
    filSurveyDate = newFilSurveyDate;
  }
////////////////////////////////////////////
  public String getFilSurveyCompleted()
  {
    return filSurveyCompleted;
  }

  public void setFilSurveyCompleted(String newFilSurveyCompleted)
  {
    filSurveyCompleted = newFilSurveyCompleted;
  }
////////////////////////////////////////////
  public String getFilSurveyValue()
  {
    return filSurveyValue;
  }

  public void setFilSurveyValue(String newFilSurveyValue)
  {
    filSurveyValue = newFilSurveyValue;
  }
////////////////////////////////////////////
  public String getFilSurveyUserId()
  {
    return filSurveyUserId;
  }

  public void setFilSurveyUserId(String newFilSurveyUserId)
  {
    filSurveyUserId = newFilSurveyUserId;
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
  public String getPersonFullName()
  {
    return personFullName;
  }

  public void setPersonFullName(String newPersonFullName)
  {
    personFullName = newPersonFullName;
  }
//////////////////////////////////////////// 
}
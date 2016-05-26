package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class QuestionTypeModel implements Serializable
{
  String questionTypeId;
  String questionTypeName;

  public static final String QUESTION_TYPE_ID = "QUESTION_TYPE_ID";
  public static final String QUESTION_TYPE_NAME = "QUESTION_TYPE_NAME";
  
  public QuestionTypeModel()
  {
  }

  public QuestionTypeModel(ResultSet res)
  {
      try
      {
          questionTypeId = res.getString(QUESTION_TYPE_ID);
          questionTypeName = res.getString(QUESTION_TYPE_NAME);      
      }
      catch(Exception e)
      {
        
      }
  }

  public String getQuestionTypeId()
  {
    return questionTypeId;
  }

  public void setQuestionTypeId(String newQuestionTypeId)
  {
    questionTypeId = newQuestionTypeId;
  }
//////////////////////////////////////////// 
  public String getQuestionTypeName()
  {
    return questionTypeName;
  }

  public void setQuestionTypeName(String newQuestionTypeName)
  {
    questionTypeName = newQuestionTypeName;
  }
////////////////////////////////////////////   
}
package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class QuestionCategoryModel implements Serializable 
{
  String questionCategoryId;
  String questionCategoryName;

  public static final String QUESTION_CATEGORY_ID = "QUESTION_CATEGORY_ID";
  public static final String QUESTION_CATEGORY_NAME = "QUESTION_CATEGORY_NAME";
  
  public QuestionCategoryModel()
  {
  }

  public QuestionCategoryModel(ResultSet res)
  {
      try
      {
          questionCategoryId = res.getString(QUESTION_CATEGORY_ID);
          questionCategoryName = res.getString(QUESTION_CATEGORY_NAME);
      }
      catch(Exception e)
      {
        
      }
  }

  public String getQuestionCategoryId()
  {
    return questionCategoryId;
  }

  public void setQuestionCategoryId(String newQuestionCategoryId)
  {
    questionCategoryId = newQuestionCategoryId;
  }
//////////////////////////////////////////// 
  public String getQuestionCategoryName()
  {
    return questionCategoryName;
  }

  public void setQuestionCategoryName(String newQuestionCategoryName)
  {
    questionCategoryName = newQuestionCategoryName;
  }
////////////////////////////////////////////   
}
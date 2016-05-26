package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class QuestionStatusModel implements Serializable
{
  String questionStatusId;
  String questionStatusName;

  public static final String QUESTION_STATUS_ID = "QUESTION_STATUS_ID";
  public static final String QUESTION_STATUS_NAME = "QUESTION_STATUS_NAME";
  
  public QuestionStatusModel()
  {
  }

  public QuestionStatusModel(ResultSet res)
  {
      try
      {
          questionStatusId = res.getString(QUESTION_STATUS_ID);
          questionStatusName = res.getString(QUESTION_STATUS_NAME);        
      }
      catch(Exception e)
      {
        
      }
  }

  public String getQuestionStatusId()
  {
    return questionStatusId;
  }

  public void setQuestionStatusId(String newQuestionStatusId)
  {
    questionStatusId = newQuestionStatusId;
  }
//////////////////////////////////////////// 
  public String getQuestionStatusName()
  {
    return questionStatusName;
  }

  public void setQuestionStatusName(String newQuestionStatusName)
  {
    questionStatusName = newQuestionStatusName;
  }
////////////////////////////////////////////   
}
package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class FilQuestionModel implements Serializable 
{
  String filQuestionId;
  String filQuestion;
  String filQuestionTypeId;
  String questionTypeName;
  String filQuestionCategoryId;
  String questionCategoryName;
  String filGroupId;
  String filQuestionWeight;
  String filQuestionAnswer;
  String filQuestionOrder;
  String filQuestionMandatory;
  String questionId;

  public static final String FIL_QUESTION_ID = "FIL_QUESTION_ID";
  public static final String FIL_QUESTION = "FIL_QUESTION";
  public static final String FIL_QUESTION_TYPE_ID = "FIL_QUESTION_TYPE_ID";
  public static final String QUESTION_TYPE_NAME = "QUESTION_TYPE_NAME";
  public static final String FIL_QUESTION_CATEGORY_ID = "FIL_QUESTION_CATEGORY_ID";
  public static final String QUESTION_CATEGORY_NAME = "QUESTION_CATEGORY_NAME";
  public static final String FIL_GROUP_ID = "FIL_GROUP_ID";
  public static final String FIL_QUESTION_WEIGHT = "FIL_QUESTION_WEIGHT";
  public static final String FIL_QUESTION_ANSWER = "FIL_QUESTION_ANSWER";
  public static final String FIL_QUESTION_ORDER = "FIL_QUESTION_ORDER";
  public static final String FIL_QUESTION_MANDATORY = "FIL_QUESTION_MANDATORY";
  public static final String QUESTION_ID = "QUESTION_ID";

  public FilQuestionModel()
  {
  }

  public FilQuestionModel(ResultSet res)
  {
      try
      {
        filQuestionId = res.getString(FIL_QUESTION_ID);
        filQuestion = res.getString(FIL_QUESTION);
        filQuestionTypeId = res.getString(FIL_QUESTION_TYPE_ID);
        questionTypeName = res.getString(QUESTION_TYPE_NAME);
        filQuestionCategoryId = res.getString(FIL_QUESTION_CATEGORY_ID);
        questionCategoryName = res.getString(QUESTION_CATEGORY_NAME);
        filGroupId = res.getString(FIL_GROUP_ID);
        filQuestionWeight = res.getString(FIL_QUESTION_WEIGHT);
        filQuestionAnswer = res.getString(FIL_QUESTION_ANSWER);
        filQuestionOrder = res.getString(FIL_QUESTION_ORDER);
        filQuestionMandatory = res.getString(FIL_QUESTION_MANDATORY);
        questionId = res.getString(QUESTION_ID);
      }
      catch(Exception e)
      {

      }
  }  

  public String getFilQuestionId()
  {
    return filQuestionId;
  }

  public void setFilQuestionId(String newFilQuestionId)
  {
    filQuestionId = newFilQuestionId;
  }
//////////////////////////////////////////// 
  public String getFilQuestion()
  {
    return filQuestion;
  }

  public void setFilQuestion(String newFilQuestion)
  {
    filQuestion = newFilQuestion;
  }
//////////////////////////////////////////// 
  public String getFilQuestionTypeId()
  {
    return filQuestionTypeId;
  }

  public void setFilQuestionTypeId(String newFilQuestionTypeId)
  {
    filQuestionTypeId = newFilQuestionTypeId;
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
  public String getFilQuestionCategoryId()
  {
    return filQuestionCategoryId;
  }

  public void setFilQuestionCategoryId(String newFilQuestionCategoryId)
  {
    filQuestionCategoryId = newFilQuestionCategoryId;
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
  public String getFilGroupId()
  {
    return filGroupId;
  }

  public void setFilGroupId(String newFilGroupId)
  {
    filGroupId = newFilGroupId;
  }
//////////////////////////////////////////// 
  public String getFilQuestionWeight()
  {
    return filQuestionWeight;
  }

  public void setFilQuestionWeight(String newFilQuestionWeight)
  {
    filQuestionWeight = newFilQuestionWeight;
  }
//////////////////////////////////////////// 
  public String getFilQuestionAnswer()
  {
    return filQuestionAnswer;
  }

  public void setFilQuestionAnswer(String newFilQuestionAnswer)
  {
    filQuestionAnswer = newFilQuestionAnswer;
  }
//////////////////////////////////////////// 
  public String getFilQuestionOrder()
  {
    return filQuestionOrder;
  }

  public void setFilQuestionOrder(String newFilQuestionOrder)
  {
    filQuestionOrder = newFilQuestionOrder;
  }
//////////////////////////////////////////// 
  public String getFilQuestionMandatory()
  {
    return filQuestionMandatory;
  }

  public void setFilQuestionMandatory(String newFilQuestionMandatory)
  {
    filQuestionMandatory = newFilQuestionMandatory;
  }
//////////////////////////////////////////// 
  public String getQuestionId()
  {
    return questionId;
  }

  public void setQuestionId(String newQuestionId)
  {
    questionId = newQuestionId;
  }
//////////////////////////////////////////// 
}
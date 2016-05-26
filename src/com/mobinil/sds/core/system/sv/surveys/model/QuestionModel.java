package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class QuestionModel implements Serializable 
{
  String questionId;
  String question;
  String questionStatusId;
  String questionStatusName;
  String questionTypeId;
  String questionTypeName;
  String questionCategoryId;
  String questionCategoryName;
  String groupId;
  String questionWeight;
  String questionOrder;
  String questionMandatory;

  public static final String QUESTION_ID = "QUESTION_ID";
  public static final String QUESTION = "QUESTION";
  public static final String QUESTION_STATUS_ID = "QUESTION_STATUS_ID";
  public static final String QUESTION_STATUS_NAME = "QUESTION_STATUS_NAME";
  public static final String QUESTION_TYPE_ID = "QUESTION_TYPE_ID";
  public static final String QUESTION_TYPE_NAME = "QUESTION_TYPE_NAME";
  public static final String QUESTION_CATEGORY_ID = "QUESTION_CATEGORY_ID";
  public static final String QUESTION_CATEGORY_NAME = "QUESTION_CATEGORY_NAME";
  public static final String GROUP_ID = "GROUP_ID";
  public static final String QUESTION_WEIGHT = "QUESTION_WEIGHT";
  public static final String QUESTION_ORDER = "QUESTION_ORDER";
  public static final String QUESTION_MANDATORY = "QUESTION_MANDATORY";  

  public QuestionModel()
  {
  }

  public QuestionModel(ResultSet res)
  {
      try
      {
          questionId = res.getString(QUESTION_ID);
          question = res.getString(QUESTION);
          questionStatusId = res.getString(QUESTION_STATUS_ID);
          questionStatusName = res.getString(QUESTION_STATUS_NAME);
          questionTypeId = res.getString(QUESTION_TYPE_ID);
          questionTypeName = res.getString(QUESTION_TYPE_NAME);
          questionCategoryId = res.getString(QUESTION_CATEGORY_ID);
          questionCategoryName = res.getString(QUESTION_CATEGORY_NAME);
          groupId = res.getString(GROUP_ID);
          questionWeight = res.getString(QUESTION_WEIGHT);
          questionOrder = res.getString(QUESTION_ORDER);
          questionMandatory = res.getString(QUESTION_MANDATORY);        
      }
      catch(Exception e)
      {
        
      }
  }

  public String getQuestionId()
  {
    return questionId;
  }

  public void setQuestionId(String newQuestionId)
  {
    questionId = newQuestionId;
  }
////////////////////////////////////////////
  public String getQuestion()
  {
    return question;
  }

  public void setQuestion(String newQuestion)
  {
    question = newQuestion;
  }
//////////////////////////////////////////// 
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
  public String getGroupId()
  {
    return groupId;
  }

  public void setGroupId(String newGroupId)
  {
    groupId = newGroupId;
  }
//////////////////////////////////////////// 
  public String getQuestionWeight()
  {
    return questionWeight;
  }

  public void setQuestionWeight(String newQuestionWeight)
  {
    questionWeight = newQuestionWeight;
  }
//////////////////////////////////////////// 
  public String getQuestionOrder()
  {
    return questionOrder;
  }

  public void setQuestionOrder(String newQuestionOrder)
  {
    questionOrder = newQuestionOrder;
  }
//////////////////////////////////////////// 
  public String getQuestionMandatory()
  {
    return questionMandatory;
  }

  public void setQuestionMandatory(String newQuestionMandatory)
  {
    questionMandatory = newQuestionMandatory;
  }
//////////////////////////////////////////// 
}
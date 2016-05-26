package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class ChoiceModel implements Serializable
{
  String choiceId;
  String choice;
  String questionId;
  String choiceValue;
  String choiceOrder;

  public static final String CHOICE_ID = "CHOICE_ID";
  public static final String CHOICE = "CHOICE";
  public static final String QUESTION_ID = "QUESTION_ID";
  public static final String CHOICE_VALUE = "CHOICE_VALUE";
  public static final String CHOICE_ORDER = "CHOICE_ORDER";
  
  public ChoiceModel()
  {
  }

  public ChoiceModel(ResultSet res)
  {
      try
      {
          choiceId = res.getString(CHOICE_ID);
          choice = res.getString(CHOICE);
          questionId = res.getString(QUESTION_ID);
          choiceValue = res.getString(CHOICE_VALUE);
          choiceOrder = res.getString(CHOICE_ORDER);        
      }
      catch(Exception e)
      {
        
      }
  }

  public String getChoiceId()
  {
    return choiceId;
  }

  public void setChoiceId(String newChoiceId)
  {
    choiceId = newChoiceId;
  }
//////////////////////////////////////////// 
  public String getChoice()
  {
    return choice;
  }

  public void setChoice(String newChoice)
  {
    choice = newChoice;
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
  public String getChoiceValue()
  {
    return choiceValue;
  }

  public void setChoiceValue(String newChoiceValue)
  {
    choiceValue = newChoiceValue;
  }
////////////////////////////////////////////  
  public String getChoiceOrder()
  {
    return choiceOrder;
  }

  public void setChoiceOrder(String newChoiceOrder)
  {
    choiceOrder = newChoiceOrder;
  }
////////////////////////////////////////////  
}
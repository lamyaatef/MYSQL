package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class FilGroupModel implements Serializable
{
    String filGroupId;
    String filGroupName;
    String filSurveyId;
    String filGroupWeight;
    String filGroupOrder ;
    String filGroupReference;
    String filGroupStatusId;
    String filGroupDescription;
    String groupId;
    String filGroupValue;
    String filGroupStatusName;
    
    public static final String FIL_GROUP_ID = "FIL_GROUP_ID";
    public static final String FIL_GROUP_NAME = "FIL_GROUP_NAME";
    public static final String FIL_SURVEY_ID = "FIL_SURVEY_ID";
    public static final String FIL_GROUP_WEIGHT = "FIL_GROUP_WEIGHT";
    public static final String FIL_GROUP_ORDER = "FIL_GROUP_ORDER"; 
    public static final String FIL_GROUP_REFERENCE = "FIL_GROUP_REFERENCE";
    public static final String FIL_GROUP_STATUS_ID = "FIL_GROUP_STATUS_ID";
    public static final String FIL_GROUP_DESCRIPTION = "FIL_GROUP_DESCRIPTION";
    public static final String GROUP_ID = "GROUP_ID";
    public static final String FIL_GROUP_VALUE = "FIL_GROUP_VALUE";
    public static final String FIL_GROUP_STATUS_NAME = "FIL_GROUP_STATUS_NAME";

  public FilGroupModel()
  {
  }

  public FilGroupModel(ResultSet res)
  {
      try
      {
        filGroupId = res.getString(FIL_GROUP_ID);
        filGroupName = res.getString(FIL_GROUP_NAME);
        filSurveyId = res.getString(FIL_SURVEY_ID);
        filGroupWeight = res.getString(FIL_GROUP_WEIGHT);
        filGroupOrder = res.getString(FIL_GROUP_ORDER); 
        filGroupReference = res.getString(FIL_GROUP_REFERENCE);
        filGroupStatusId = res.getString(FIL_GROUP_STATUS_ID);
        filGroupDescription = res.getString(FIL_GROUP_DESCRIPTION);
        groupId = res.getString(GROUP_ID);
        filGroupValue = res.getString(FIL_GROUP_VALUE);
        filGroupStatusName = res.getString(FIL_GROUP_STATUS_NAME);
      }
      catch(Exception e)
      {

      }  
  }  

  public String getFilGroupId()
  {
    return filGroupId;
  }

  public void setFilGroupId(String newFilGroupId)
  {
    filGroupId = newFilGroupId;
  }
////////////////////////////////////////////  
  public String getFilGroupName()
  {
    return filGroupName;
  }

  public void setFilGroupName(String newFilGroupName)
  {
    filGroupName = newFilGroupName;
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
  public String getFilGroupWeight()
  {
    return filGroupWeight;
  }

  public void setFilGroupWeight(String newFilGroupWeight)
  {
    filGroupWeight = newFilGroupWeight;
  }
////////////////////////////////////////////  
  public String getFilGroupOrder()
  {
    return filGroupOrder;
  }

  public void setFilGroupOrder(String newFilGroupOrder)
  {
    filGroupOrder = newFilGroupOrder;
  }
////////////////////////////////////////////  
  public String getFilGroupReference()
  {
    return filGroupReference;
  }

  public void setFilGroupReference(String newFilGroupReference)
  {
    filGroupReference = newFilGroupReference;
  }
////////////////////////////////////////////  
  public String getFilGroupStatusId()
  {
    return filGroupStatusId;
  }

  public void setFilGroupStatusId(String newFilGroupStatusId)
  {
    filGroupStatusId = newFilGroupStatusId;
  }
////////////////////////////////////////////  
  public String getFilGroupDescription()
  {
    return filGroupDescription;
  }

  public void setFilGroupDescription(String newFilGroupDescription)
  {
    filGroupDescription = newFilGroupDescription;
  }
////////////////////////////////////////////  
  public String getFilGroupStatusName()
  {
    return filGroupStatusName;
  }

  public void setFilGroupStatusName(String newFilGroupStatusName)
  {
    filGroupStatusName = newFilGroupStatusName;
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
  public String getFilGroupValue()
  {
    return filGroupValue;
  }

  public void setFilGroupValue(String newFilGroupValue)
  {
    filGroupValue = newFilGroupValue;
  }
////////////////////////////////////////////  
}
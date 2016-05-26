package com.mobinil.sds.core.system.sv.surveys.model;
import java.sql.*;
import java.io.*;

public class GroupModel implements Serializable 
{
  String groupId;
  String groupName;
  String surveyId;
  String groupWeight;
  String groupOrder;
  String groupReference;
  String groupStatusId;
  String groupStatusName;
  String groupDescription;
  String groupCategoryID;
  String groupCategoryName;
  
  public static final String GROUP_ID = "GROUP_ID";
  public static final String GROUP_NAME = "GROUP_NAME";
  public static final String SURVEY_ID = "SURVEY_ID";
  public static final String GROUP_WEIGHT = "GROUP_WEIGHT";
  public static final String GROUP_ORDER = "GROUP_ORDER";
  public static final String GROUP_REFERENCE = "GROUP_REFERENCE";
  public static final String GROUP_STATUS_ID = "GROUP_STATUS_ID";
  public static final String GROUP_STATUS_NAME = "GROUP_STATUS_NAME";
  public static final String GROUP_DESCRIPTION = "GROUP_DESCRIPTION";
  public static final String GROUP_CATEGORY_ID = "GROUP_CATEGORY_ID";
  public static final String GROUP_CATEGORY_NAME = "GROUP_CATEGORY_NAME";
  
  public GroupModel()
  {
  }

  public GroupModel(ResultSet res)
  {
      try
      {
          groupId = res.getString(GROUP_ID);
          groupName = res.getString(GROUP_NAME);
          surveyId = res.getString(SURVEY_ID);
          groupWeight = res.getString(GROUP_WEIGHT);
          groupOrder = res.getString(GROUP_ORDER);
          groupReference = res.getString(GROUP_REFERENCE);
          groupStatusId = res.getString(GROUP_STATUS_ID);
          groupStatusName = res.getString(GROUP_STATUS_NAME);
          groupDescription = res.getString(GROUP_DESCRIPTION); 
          groupCategoryID = res.getString(GROUP_CATEGORY_ID);
          groupCategoryName = res.getString(GROUP_CATEGORY_NAME);
      }
      catch(Exception e)
      {
        
      }
  }

  public String getGroupId()
  {
    return groupId;
  }

  public void setGroupId(String newGroupId)
  {
    groupId = newGroupId;
  }
////////////////////////////////////////////
  public String getGroupName()
  {
    return groupName;
  }

  public void setGroupName(String newGroupName)
  {
    groupName = newGroupName;
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
  public String getGroupWeight()
  {
    return groupWeight;
  }

  public void setGroupWeight(String newGroupWeight)
  {
    groupWeight = newGroupWeight;
  }
////////////////////////////////////////////
  public String getGroupOrder()
  {
    return groupOrder;
  }

  public void setGroupOrder(String newGroupOrder)
  {
    groupOrder = newGroupOrder;
  }
////////////////////////////////////////////
  public String getGroupReference()
  {
    return groupReference;
  }

  public void setGroupReference(String newGroupReference)
  {
    groupReference = newGroupReference;
  }
////////////////////////////////////////////
  public String getGroupStatusId()
  {
    return groupStatusId;
  }

  public void setGroupStatusId(String newGroupStatusId)
  {
    groupStatusId = newGroupStatusId;
  }
////////////////////////////////////////////
  public String getGroupStatusName()
  {
    return groupStatusName;
  }

  public void setGroupStatusName(String newGroupStatusName)
  {
    groupStatusName = newGroupStatusName;
  }
////////////////////////////////////////////
  public String getGroupDescription()
  {
    return groupDescription;
  }

  public void setGroupDescription(String newGroupDescription)
  {
    groupDescription = newGroupDescription;
  }
////////////////////////////////////////////
  public String getGroupCategoryID()
  {
   	return groupCategoryID;
  }
  public void setGroupCategoryID(String newGroupCategoryID)
  {
    groupCategoryID = newGroupCategoryID;
  }
///////////////////////////////////////////  
  public String getGroupCategoryName()
  {
  	return groupCategoryName;
  }
  public void setGroupCategoryName(String newGroupCategoryName)
  {
  	groupCategoryName = newGroupCategoryName;
  }
//////////////////////////////////////////
}
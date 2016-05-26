package com.mobinil.sds.core.system.dcm.user.model;
import java.sql.*;
import java.io.*;

public class VisitPlanModel implements Serializable 
{
  String visitPlanId;
  Date visitPlanDate;
  String posId;
  String posCode;
  String posName;
  String userId;
  String userFullName;
  String userEmail;
  String functionId;
  String functionName;
  String visitComment;
  String creatorUserId;
  String creatorUserFullName;
  String creatorUserEmail;
  Timestamp creationDate;
  String lastModifiedUserId;
  String lastModifiedUserFullName;
  String lastModifiedUserEmail;
  Timestamp lastModifiedDate;
  String visitPlanStatusTypeId;
  String visitPlanStatusTypeName;

  public static final String VISIT_PLAN_ID = "VISIT_PLAN_ID";
  public static final String VISIT_PLAN_DATE = "VISIT_PLAN_DATE";
  public static final String POS_ID = "POS_ID";
  public static final String POS_CODE = "POS_CODE";
  public static final String POS_NAME = "POS_NAME";
  public static final String USER_ID = "USER_ID";
  public static final String USER_FULL_NAME = "USER_FULL_NAME";
  public static final String USER_EMAIL = "USER_EMAIL";
  public static final String FUNCTION_ID = "FUNCTION_ID";
  public static final String FUNCTION_NAME = "FUNCTION_NAME";
  public static final String VISIT_COMMENT = "VISIT_COMMENT";
  public static final String CREATOR_USER_ID = "CREATOR_USER_ID";
  public static final String CREATOR_USER_FULL_NAME = "CREATOR_USER_FULL_NAME";
  public static final String CREATOR_USER_EMAIL = "CREATOR_USER_EMAIL";
  public static final String CREATION_DATE = "CREATION_DATE";
  public static final String LAST_MODIFIED_USER_ID = "LAST_MODIFIED_USER_ID";
  public static final String LAST_MODIFIED_USER_FULL_NAME = "LAST_MODIFIED_USER_FULL_NAME";
  public static final String LAST_MODIFIED_USER_EMAIL = "LAST_MODIFIED_USER_EMAIL";
  public static final String LAST_MODIFIED_DATE = "LAST_MODIFIED_DATE";
  public static final String VISIT_PLAN_STATUS_TYPE_ID = "VISIT_PLAN_STATUS_TYPE_ID";
  public static final String VISIT_PLAN_STATUS_TYPE_NAME = "VISIT_PLAN_STATUS_TYPE_NAME";
  
  public VisitPlanModel()
  {
  }

  public VisitPlanModel(ResultSet res)
  {
    try
    {
      visitPlanId = res.getString(VISIT_PLAN_ID);
      visitPlanDate = res.getDate(VISIT_PLAN_DATE);
      posId = res.getString(POS_ID);
      posCode = res.getString(POS_CODE);
      posName = res.getString(POS_NAME);
      userId = res.getString(USER_ID);
      userFullName = res.getString(USER_FULL_NAME);
      userEmail = res.getString(USER_EMAIL);
      functionId = res.getString(FUNCTION_ID);
      functionName = res.getString(FUNCTION_NAME);
      visitComment = res.getString(VISIT_COMMENT);
      creatorUserId = res.getString(CREATOR_USER_ID);
      creatorUserFullName = res.getString(CREATOR_USER_FULL_NAME);
      creatorUserEmail = res.getString(CREATOR_USER_EMAIL);
      creationDate = res.getTimestamp(CREATION_DATE);
      lastModifiedUserId = res.getString(LAST_MODIFIED_USER_ID);
      lastModifiedUserFullName = res.getString(LAST_MODIFIED_USER_FULL_NAME);
      lastModifiedUserEmail = res.getString(LAST_MODIFIED_USER_EMAIL);
      lastModifiedDate = res.getTimestamp(LAST_MODIFIED_DATE);
      visitPlanStatusTypeId = res.getString(VISIT_PLAN_STATUS_TYPE_ID);
      visitPlanStatusTypeName = res.getString(VISIT_PLAN_STATUS_TYPE_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

    
  public String getVisitPlanId()
  {
  return visitPlanId;
  }
  public void setVisitPlanId(String newVisitPlanId)
  {
  visitPlanId= newVisitPlanId;
  }
  
  public Date getVisitPlanDate()
  {
  return visitPlanDate;
  }
  public void setVisitPlanDate(Date newVisitPlanDate)
  {
  visitPlanDate= newVisitPlanDate;
  }
  
  public String getPosId()
  {
  return posId;
  }
  public void setPosId(String newPosId)
  {
  posId= newPosId;
  }
  
  public String getPosCode()
  {
  return posCode;
  }
  public void setPosCode(String newPosCode)
  {
  posCode= newPosCode;
  }
  
  public String getPosName()
  {
  return posName;
  }
  public void setPosName(String newPosName)
  {
  posName= newPosName;
  }
  
  public String getUserId()
  {
  return userId;
  }
  public void setUserId(String newUserId)
  {
  userId= newUserId;
  }
  
  public String getUserFullName()
  {
  return userFullName;
  }
  public void setUserFullName(String newUserFullName)
  {
  userFullName= newUserFullName;
  }
  
  public String getUserEmail()
  {
  return userEmail;
  }
  public void setUserEmail(String newUserEmail)
  {
  userEmail= newUserEmail;
  }
  
  public String getFunctionId()
  {
  return functionId;
  }
  public void setFunctionId(String newFunctionId)
  {
  functionId= newFunctionId;
  }
  
  public String getFunctionName()
  {
  return functionName;
  }
  public void setFunctionName(String newFunctionName)
  {
  functionName= newFunctionName;
  }
  
  public String getVisitComment()
  {
  return visitComment;
  }
  public void setVisitComment(String newVisitComment)
  {
  visitComment= newVisitComment;
  }
  
  public String getCreatorUserId()
  {
  return creatorUserId;
  }
  public void setCreatorUserId(String newCreatorUserId)
  {
  creatorUserId= newCreatorUserId;
  }
  
  public String getCreatorUserFullName()
  {
  return creatorUserFullName;
  }
  public void setCreatorUserFullName(String newCreatorUserFullName)
  {
  creatorUserFullName= newCreatorUserFullName;
  }
  
  public String getCreatorUserEmail()
  {
  return creatorUserEmail;
  }
  public void setCreatorUserEmail(String newCreatorUserEmail)
  {
  creatorUserEmail= newCreatorUserEmail;
  }
  
  public Timestamp getCreationDate()
  {
  return creationDate;
  }
  public void setCreationDate(Timestamp newCreationDate)
  {
  creationDate= newCreationDate;
  }
  
  public String getLastModifiedUserId()
  {
  return lastModifiedUserId;
  }
  public void setLastModifiedUserId(String newLastModifiedUserId)
  {
  lastModifiedUserId= newLastModifiedUserId;
  }
  
  public String getLastModifiedUserFullName()
  {
  return lastModifiedUserFullName;
  }
  public void setLastModifiedUserFullName(String newLastModifiedUserFullName)
  {
  lastModifiedUserFullName= newLastModifiedUserFullName;
  }
  
  public String getLastModifiedUserEmail()
  {
  return lastModifiedUserEmail;
  }
  public void setLastModifiedUserEmail(String newLastModifiedUserEmail)
  {
  lastModifiedUserEmail= newLastModifiedUserEmail;
  }
  
  public Timestamp getLastModifiedDate()
  {
  return lastModifiedDate;
  }
  public void setLastModifiedDate(Timestamp newLastModifiedDate)
  {
  lastModifiedDate= newLastModifiedDate;
  }
  
  public String getVisitPlanStatusTypeId()
  {
  return visitPlanStatusTypeId;
  }
  public void setVisitPlanStatusTypeId(String newVisitPlanStatusTypeId)
  {
  visitPlanStatusTypeId= newVisitPlanStatusTypeId;
  }
  
  public String getVisitPlanStatusTypeName()
  {
  return visitPlanStatusTypeName;
  }
  public void setVisitPlanStatusTypeName(String newVisitPlanStatusTypeName)
  {
  visitPlanStatusTypeName= newVisitPlanStatusTypeName;
  }
}
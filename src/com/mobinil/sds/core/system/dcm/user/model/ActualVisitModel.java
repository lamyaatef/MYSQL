package com.mobinil.sds.core.system.dcm.user.model;
import java.sql.*;
import java.io.*;

public class ActualVisitModel implements Serializable
{
  String actualVisitId;
  Date actualVisitDate;
  String userId;
  String userFullName;
  String userEmail;
  String posCode;
  String posId;
  String posName;
  String actualVisitComment; 
  String functionId;
  String functionName;
  
  public static final String ACTUAL_VISIT_ID = "ACTUAL_VISIT_ID";
  public static final String ACTUAL_VISIT_DATE = "ACTUAL_VISIT_DATE";
  public static final String USER_ID = "USER_ID";
  public static final String USER_FULL_NAME = "USER_FULL_NAME";
  public static final String USER_EMAIL = "USER_EMAIL";
  public static final String POS_CODE = "POS_CODE";
  public static final String POS_ID = "POS_ID";
  public static final String POS_NAME = "POS_NAME";
  public static final String ACTUAL_VISIT_COMMENT = "ACTUAL_VISIT_COMMENT";
  public static final String FUNCTION_ID = "FUNCTION_ID";
  public static final String FUNCTION_NAME = "FUNCTION_NAME";
  
  public ActualVisitModel()
  {
  }

  public ActualVisitModel(ResultSet res)
  {
    try
    {
      actualVisitId = res.getString(ACTUAL_VISIT_ID);
      actualVisitDate = res.getDate(ACTUAL_VISIT_DATE);
      userId = res.getString(USER_ID);
      userFullName = res.getString(USER_FULL_NAME);
      userEmail = res.getString(USER_EMAIL);
      posCode = res.getString(POS_CODE);
      posId = res.getString(POS_ID);
      posName = res.getString(POS_NAME);
      actualVisitComment = res.getString(ACTUAL_VISIT_COMMENT);
      functionId = res.getString(FUNCTION_ID);
      functionName = res.getString(FUNCTION_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public String getActualVisitId()
  {
  return actualVisitId;
  }
  public void setActualVisitId(String newActualVisitId)
  {
  actualVisitId= newActualVisitId;
  }

  public Date getActualVisitDate()
  {
  return actualVisitDate;
  }
  public void setActualVisitDate(Date newActualVisitDate)
  {
  actualVisitDate= newActualVisitDate;
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

  public String getPosCode()
  {
  return posCode;
  }
  public void setPosCode(String newPosCode)
  {
  posCode= newPosCode;
  }

  public String getPosId()
  {
  return posId;
  }
  public void setPosId(String newPosId)
  {
  posId= newPosId;
  }

  public String getPosName()
  {
  return posName;
  }
  public void setPosName(String newPosName)
  {
  posName= newPosName;
  }

  public String getActualVisitComment()
  {
  return actualVisitComment;
  }
  public void setActualVisitComment(String newActualVisitComment)
  {
  actualVisitComment= newActualVisitComment;
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
}
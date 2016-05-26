package com.mobinil.sds.core.system.hlp.casepkg.model;
import java.sql.*;
import java.io.*;

public class CaseDetailModel implements Serializable
{
  String caseDetailId;
  String caseId;
  String caseTitle;
  String caseDescription;
  String senderUserId;
  String senderFullName;
  String senderEmail;
  String receiverUserId;
  String receiverFullName;
  String receiverEmail;
  Timestamp caseTimestamp;
  String caseStatusId;
  String caseStatusName;
  String receiverComment;
  String casePriorityId;
  String casePriorityName;
  String caseTypeId;
  String caseTypeName;
  String caseSuperTypeId;
  String caseSuperTypeName;
  String caseCategoryId;
  String caseCategoryName;
  String caseStatusWarningId;
  String caseStatusWarningName;
  String posCode;
  String dialNumber;
  String dialNumberTypeId;
  String dialNumberTypeName; 
  String caseDirectionId;
  String caseDirectionName;
  
  public static final String CASE_DETAIL_ID = "CASE_DETAIL_ID";
  public static final String CASE_ID = "CASE_ID";
  public static final String CASE_TITLE = "CASE_TITLE";
  public static final String CASE_DESCRIPTION = "CASE_DESCRIPTION";
  public static final String SENDER_USER_ID = "SENDER_USER_ID";
  public static final String SENDER_FULL_NAME = "SENDER_FULL_NAME";
  public static final String SENDER_EMAIL = "SENDER_EMAIL";
  public static final String RECEIVER_USER_ID = "RECEIVER_USER_ID";
  public static final String RECEIVER_FULL_NAME = "RECEIVER_FULL_NAME";
  public static final String RECEIVER_EMAIL = "RECEIVER_EMAIL";
  public static final String CASE_TIMESTAMP = "CASE_TIMESTAMP";
  public static final String CASE_STATUS_ID = "CASE_STATUS_ID";
  public static final String CASE_STATUS_NAME = "CASE_STATUS_NAME";
  public static final String RECEIVER_COMMENT = "RECEIVER_COMMENT";
  public static final String CASE_PRIORITY_ID = "CASE_PRIORITY_ID";
  public static final String CASE_PRIORITY_NAME = "CASE_PRIORITY_NAME";
  public static final String CASE_TYPE_ID = "CASE_TYPE_ID";
  public static final String CASE_TYPE_NAME = "CASE_TYPE_NAME";
  public static final String CASE_SUPER_TYPE_ID = "CASE_SUPER_TYPE_ID";
  public static final String CASE_SUPER_TYPE_NAME = "CASE_SUPER_TYPE_NAME";
  public static final String CASE_CATEGORY_ID = "CASE_CATEGORY_ID";
  public static final String CASE_CATEGORY_NAME = "CASE_CATEGORY_NAME";
  public static final String CASE_STATUS_WARNING_ID = "CASE_STATUS_WARNING_ID";
  public static final String CASE_STATUS_WARNING_NAME = "CASE_STATUS_WARNING_NAME"; 
  public static final String POS_CODE = "POS_CODE";
  public static final String DIAL_NUMBER = "DIAL_NUMBER";
  public static final String DIAL_NUMBER_TYPE_ID = "DIAL_NUMBER_TYPE_ID";
  public static final String DIAL_NUMBER_TYPE_NAME  = "DIAL_NUMBER_TYPE_NAME";
  public static final String CASE_DIRECTION_ID = "CASE_DIRECTION_ID";
  public static final String CASE_DIRECTION_NAME = "CASE_DIRECTION_NAME";
  
  public CaseDetailModel()
  {
  }

  public CaseDetailModel(ResultSet res)
  {
    try
    {
      caseDetailId = res.getString(CASE_DETAIL_ID);
      caseId = res.getString(CASE_ID);
      caseTitle = res.getString(CASE_TITLE);
      caseDescription = res.getString(CASE_DESCRIPTION);
      caseTypeId = res.getString(CASE_TYPE_ID);
      caseTypeName = res.getString(CASE_TYPE_NAME);
      caseSuperTypeId = res.getString(CASE_SUPER_TYPE_ID);
      caseSuperTypeName = res.getString(CASE_SUPER_TYPE_NAME);
      caseStatusId = res.getString(CASE_STATUS_ID);
      caseStatusName = res.getString(CASE_STATUS_NAME);
      casePriorityId = res.getString(CASE_PRIORITY_ID);
      casePriorityName = res.getString(CASE_PRIORITY_NAME);
      caseCategoryId = res.getString(CASE_CATEGORY_ID);
      caseCategoryName = res.getString(CASE_CATEGORY_NAME);
      caseStatusWarningId = res.getString(CASE_STATUS_WARNING_ID);
      caseStatusWarningName = res.getString(CASE_STATUS_WARNING_NAME); 
      senderUserId = res.getString(SENDER_USER_ID);
      senderFullName = res.getString(SENDER_FULL_NAME);
      senderEmail = res.getString(SENDER_EMAIL);
      receiverUserId = res.getString(RECEIVER_USER_ID);
      receiverFullName = res.getString(RECEIVER_FULL_NAME);
      receiverEmail = res.getString(RECEIVER_EMAIL);
      caseTimestamp = res.getTimestamp(CASE_TIMESTAMP);
      receiverComment = res.getString(RECEIVER_COMMENT);
      posCode = res.getString(POS_CODE);
      dialNumber = res.getString(DIAL_NUMBER);
      dialNumberTypeId = res.getString(DIAL_NUMBER_TYPE_ID);
      dialNumberTypeName = res.getString(DIAL_NUMBER_TYPE_NAME); 
      caseDirectionId = res.getString(CASE_DIRECTION_ID);
      caseDirectionName = res.getString(CASE_DIRECTION_NAME);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  
  public String getCaseDetailId()
  {
  return caseDetailId;
  }
  public void setCaseDetailId(String newCaseDetailId)
  {
  caseDetailId= newCaseDetailId;
  }
  
  public String getCaseId()
  {
  return caseId;
  }
  public void setCaseId(String newCaseId)
  {
  caseId= newCaseId;
  }
  
  public String getCaseTitle()
  {
  return caseTitle;
  }
  public void setCaseTitle(String newCaseTitle)
  {
  caseTitle= newCaseTitle;
  }
  
  public String getCaseDescription()
  {
  return caseDescription;
  }
  public void setCaseDescription(String newCaseDescription)
  {
  caseDescription= newCaseDescription;
  }
  
  public String getSenderUserId()
  {
  return senderUserId;
  }
  public void setSenderUserId(String newSenderUserId)
  {
  senderUserId= newSenderUserId;
  }
  
  public String getSenderFullName()
  {
  return senderFullName;
  }
  public void setSenderFullName(String newSenderFullName)
  {
  senderFullName= newSenderFullName;
  }
  
  public String getSenderEmail()
  {
  return senderEmail;
  }
  public void setSenderEmail(String newSenderEmail)
  {
  senderEmail= newSenderEmail;
  }
  
  public String getReceiverUserId()
  {
  return receiverUserId;
  }
  public void setReceiverUserId(String newReceiverUserId)
  {
  receiverUserId= newReceiverUserId;
  }
  
  public String getReceiverFullName()
  {
  return receiverFullName;
  }
  public void setReceiverFullName(String newReceiverFullName)
  {
  receiverFullName= newReceiverFullName;
  }
  
  public String getReceiverEmail()
  {
  return receiverEmail;
  }
  public void setReceiverEmail(String newReceiverEmail)
  {
  receiverEmail= newReceiverEmail;
  }
  
  public Timestamp getCaseTimestamp()
  {
  return caseTimestamp;
  }
  public void setCaseTimestamp(Timestamp newCaseTimestamp)
  {
  caseTimestamp= newCaseTimestamp;
  }

  public String getCaseStatusId()
  {
  return caseStatusId;
  }
  public void setCaseStatusId(String newCaseStatusId)
  {
  caseStatusId= newCaseStatusId;
  }
  
  public String getCaseStatusName()
  {
  return caseStatusName;
  }
  public void setCaseStatusName(String newCaseStatusName)
  {
  caseStatusName= newCaseStatusName;
  }
  
  public String getReceiverComment()
  {
  return receiverComment;
  }
  public void setReceiverComment(String newReceiverComment)
  {
  receiverComment= newReceiverComment;
  }
  
  public String getCasePriorityId()
  {
  return casePriorityId;
  }
  public void setCasePriorityId(String newCasePriorityId)
  {
  casePriorityId= newCasePriorityId;
  }
  
  public String getCasePriorityName()
  {
  return casePriorityName;
  }
  public void setCasePriorityName(String newCasePriorityName)
  {
  casePriorityName= newCasePriorityName;
  }
  
  public String getCaseTypeId()
  {
  return caseTypeId;
  }
  public void setCaseTypeId(String newCaseTypeId)
  {
  caseTypeId= newCaseTypeId;
  }
  
  public String getCaseTypeName()
  {
  return caseTypeName;
  }
  public void setCaseTypeName(String newCaseTypeName)
  {
  caseTypeName= newCaseTypeName;
  }
  
  public String getCaseSuperTypeId()
  {
  return caseSuperTypeId;
  }
  public void setCaseSuperTypeId(String newCaseSuperTypeId)
  {
  caseSuperTypeId= newCaseSuperTypeId;
  }
  
  public String getCaseSuperTypeName()
  {
  return caseSuperTypeName;
  }
  public void setCaseSuperTypeName(String newCaseSuperTypeName)
  {
  caseSuperTypeName= newCaseSuperTypeName;
  }
  
  public String getCaseCategoryId()
  {
  return caseCategoryId;
  }
  public void setCaseCategoryId(String newCaseCategoryId)
  {
  caseCategoryId= newCaseCategoryId;
  }
  
  public String getCaseCategoryName()
  {
  return caseCategoryName;
  }
  public void setCaseCategoryName(String newCaseCategoryName)
  {
  caseCategoryName= newCaseCategoryName;
  }
  
  public String getCaseStatusWarningId()
  {
  return caseStatusWarningId;
  }
  public void setCaseStatusWarningId(String newCaseStatusWarningId)
  {
  caseStatusWarningId= newCaseStatusWarningId;
  }
  
  public String getCaseStatusWarningName()
  {
  return caseStatusWarningName;
  }
  public void setCaseStatusWarningName(String newCaseStatusWarningName)
  {
  caseStatusWarningName= newCaseStatusWarningName;
  }


  public String getPosCode ()
  {
  return posCode ;
  }
  public void setPosCode (String newPosCode )
  {
  posCode = newPosCode ;
  }
  
  public String getDialNumber()
  {
  return dialNumber;
  }
  public void setDialNumber(String newDialNumber)
  {
  dialNumber= newDialNumber;
  }
  
  public String getDialNumberTypeId()
  {
  return dialNumberTypeId;
  }
  public void setDialNumberTypeId(String newDialNumberTypeId)
  {
  dialNumberTypeId= newDialNumberTypeId;
  }
  
  public String getDialNumberTypeName ()
  {
  return dialNumberTypeName ;
  }
  public void setDialNumberTypeName (String newDialNumberTypeName )
  {
  dialNumberTypeName = newDialNumberTypeName ;
  }
  
  public String getCaseDirectionId()
  {
  return caseDirectionId;
  }
  public void setCaseDirectionId(String newCaseDirectionId)
  {
  caseDirectionId= newCaseDirectionId;
  }
  
  public String getCaseDirectionName()
  {
  return caseDirectionName;
  }
  public void setCaseDirectionName(String newCaseDirectionName)
  {
  caseDirectionName= newCaseDirectionName;
  }
}
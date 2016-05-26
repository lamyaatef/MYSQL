package com.mobinil.sds.core.system.hlp.casepkg.model;
import java.sql.*;
import java.io.*;

public class CaseCCReceiverModel implements Serializable
{
  String caseCcReceiverId;
  String caesDetailId;
  String ccReceiverUserId;
  String ccReceiverFullName;
  String ccReceiverEmail;
  
  public static final String CASE_CC_RECEIVER_ID = "CASE_CC_RECEIVER_ID";
  public static final String CAES_DETAIL_ID = "CAES_DETAIL_ID";
  public static final String CC_RECEIVER_USER_ID = "CC_RECEIVER_USER_ID";
  public static final String CC_RECEIVER_FULL_NAME = "CC_RECEIVER_FULL_NAME";
  public static final String CC_RECEIVER_EMAIL = "CC_RECEIVER_EMAIL";
  
  public CaseCCReceiverModel()
  {
  }

  public CaseCCReceiverModel(ResultSet res)
  {
    try
    {
      caseCcReceiverId = res.getString(CASE_CC_RECEIVER_ID);
      caesDetailId = res.getString(CAES_DETAIL_ID);
      ccReceiverUserId = res.getString(CC_RECEIVER_USER_ID);
      ccReceiverFullName = res.getString(CC_RECEIVER_FULL_NAME);
      ccReceiverEmail = res.getString(CC_RECEIVER_EMAIL);   
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
  }

  public String getCaseCcReceiverId()
  {
  return caseCcReceiverId;
  }
  public void setCaseCcReceiverId(String newCaseCcReceiverId)
  {
  caseCcReceiverId= newCaseCcReceiverId;
  }
  
  public String getCaesDetailId()
  {
  return caesDetailId;
  }
  public void setCaesDetailId(String newCaesDetailId)
  {
  caesDetailId= newCaesDetailId;
  }
  
  public String getCcReceiverUserId()
  {
  return ccReceiverUserId;
  }
  public void setCcReceiverUserId(String newCcReceiverUserId)
  {
  ccReceiverUserId= newCcReceiverUserId;
  }
  
  public String getCcReceiverFullName()
  {
  return ccReceiverFullName;
  }
  public void setCcReceiverFullName(String newCcReceiverFullName)
  {
  ccReceiverFullName= newCcReceiverFullName;
  }
  
  public String getCcReceiverEmail()
  {
  return ccReceiverEmail;
  }
  public void setCcReceiverEmail(String newCcReceiverEmail)
  {
  ccReceiverEmail= newCcReceiverEmail;
  }
}
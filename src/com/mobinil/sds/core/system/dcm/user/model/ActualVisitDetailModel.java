package com.mobinil.sds.core.system.dcm.user.model;
import java.sql.*;
import java.io.*;

public class ActualVisitDetailModel implements Serializable
{
  String actualVisitDetailId;
  String actualVisitId;
  Date actualVisitDate;
  String userId;
  String posCode;
  String actualVisitComment;
  String functionId;
  String functionName;
  String functionDescription;
  String functionUnit;
  String functionForiegnKeyId;

  public static final String ACTUAL_VISIT_DETAIL_ID = "ACTUAL_VISIT_DETAIL_ID";
  public static final String ACTUAL_VISIT_ID = "ACTUAL_VISIT_ID";
  public static final String ACTUAL_VISIT_DATE = "ACTUAL_VISIT_DATE";
  public static final String USER_ID = "USER_ID";
  public static final String POS_CODE = "POS_CODE";
  public static final String ACTUAL_VISIT_COMMENT = "ACTUAL_VISIT_COMMENT";
  public static final String FUNCTION_ID = "FUNCTION_ID";
  public static final String FUNCTION_NAME = "FUNCTION_NAME";
  public static final String FUNCTION_DESCRIPTION = "FUNCTION_DESCRIPTION";
  public static final String FUNCTION_UNIT = "FUNCTION_UNIT";
  public static final String FUNCTION_FORIEGN_KEY_ID = "FUNCTION_FORIEGN_KEY_ID";
  
  public ActualVisitDetailModel()
  {
  }

  public ActualVisitDetailModel(ResultSet res)
  {
    try
    {
      actualVisitDetailId = res.getString(ACTUAL_VISIT_DETAIL_ID);
      actualVisitId = res.getString(ACTUAL_VISIT_ID);
      actualVisitDate = res.getDate(ACTUAL_VISIT_DATE);
      userId = res.getString(USER_ID);
      posCode = res.getString(POS_CODE);
      actualVisitComment = res.getString(ACTUAL_VISIT_COMMENT);
      functionId = res.getString(FUNCTION_ID);
      functionName = res.getString(FUNCTION_NAME);
      functionDescription = res.getString(FUNCTION_DESCRIPTION);
      functionUnit = res.getString(FUNCTION_UNIT);
      functionForiegnKeyId = res.getString(FUNCTION_FORIEGN_KEY_ID);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  
	public String getActualVisitDetailId()
	{
	return actualVisitDetailId;
	}
	public void setActualVisitDetailId(String newActualVisitDetailId)
	{
	actualVisitDetailId= newActualVisitDetailId;
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
	
	public String getPosCode()
	{
	return posCode;
	}
	public void setPosCode(String newPosCode)
	{
	posCode= newPosCode;
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
	
	public String getFunctionDescription()
	{
	return functionDescription;
	}
	public void setFunctionDescription(String newFunctionDescription)
	{
	functionDescription= newFunctionDescription;
	}
	
	public String getFunctionUnit()
	{
	return functionUnit;
	}
	public void setFunctionUnit(String newFunctionUnit)
	{
	functionUnit= newFunctionUnit;
	}
	
	public String getFunctionForiegnKeyId()
	{
	return functionForiegnKeyId;
	}
	public void setFunctionForiegnKeyId(String newFunctionForiegnKeyId)
	{
	functionForiegnKeyId= newFunctionForiegnKeyId;
	}
}
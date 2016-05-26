package com.mobinil.sds.core.system.sip.model;

import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.*;
import java.sql.Date;
import java.io.Serializable;
public class SIPSearchReportModel implements Serializable
{
  int commissionID = 0;
  String commissionName = "";
  String commissionCreationDate;
 
public String getCommissionCreationDate() {
	return commissionCreationDate;
}
public void setCommissionCreationDate(String commissionCreationDate) {
	this.commissionCreationDate = commissionCreationDate;
}
  String commissionStartDate;
  String commissionEndDate;
  String commissionDescription = "";
  String commissionDataViewSQL = "";
  int commissionDataViewID = 0;
  int commissionStatusTypeID = 0;
  String commissionStatusTypeName ="";
  int commissionTypeID = 0;
  String commissionTypeName ="";
  String commissionTypeCategoryName ="";
  int commissionTypeCtageoryID =0;
  int commissionDataViewTypeID = 0;
  String commissionDataViewTypeName = "";
  Boolean commissionHasPayment;
  int commissionChannelId = 0;
  String commissionChannelName = "";
  

  Vector commissionItemIDs = new Vector();
  Vector commissionItemNames = new Vector();
  Vector commissionItemFactorIDs = new Vector();
  Vector commissionItemDCMIDs = new Vector();

  Vector commissionFactorIDs = new Vector();
  Vector commissionFactorValues = new Vector();    
  Vector commissionFactorNames = new Vector(); 

  int commissionBaseID = 0;

public Boolean getCommissionHasPayment()
  {
  	return commissionHasPayment;
  }
  public void setCommissionHasPayment(Boolean newCommissionHasPayment)
  {
  	commissionHasPayment = newCommissionHasPayment;
  }

  
  public int getCommissionID()
  {
  	return commissionID;
  }
  public void setCommissionID(int newCommissionID)
  {
  	commissionID = newCommissionID;
  }



 public String getCommissionName()
 {
 	return commissionName;
 }
 public void setCommissionName(String newCommissionName)
 {
 	commissionName = newCommissionName;
 }
  public String getCommissionStartDate()
  {
  	return commissionStartDate;
  }
  public void setCommissionStartDate(String newCommissionStartDate)
  {
  	commissionStartDate = newCommissionStartDate;
  }
  public String getCommissionEndDate()
  {
  	return commissionEndDate;
  }
  public void setCommissionEndDate(String newCommissionEndDate)
  {
  	commissionEndDate = newCommissionEndDate;
  }
  public String getCommissionDescription()
  {
  	return commissionDescription;
  }
  public void setCommissionDescription(String newCommissionDescription)
  {
  	commissionDescription = newCommissionDescription;
  }
  public String getCommissionDataViewSQL()
  {
  	return commissionDataViewSQL;
  }
  public void setCommissionDataViewSQL(String newCommissionDataViewSQL)
  {
  	commissionDataViewSQL = newCommissionDataViewSQL;
  }
  public int getCommissionDataViewID()
  {
  	return commissionDataViewID;
  }
  public void setCommissionDataViewID(int newCommissionDataViewID)
  {
  	commissionDataViewID = newCommissionDataViewID;
  }
  public int getCommissionStatusTypeID()
  {
  	return commissionStatusTypeID;
  }
  public void setCommissionStatusTypeID(int newCommissionStatusTypeID)
  {
  	commissionStatusTypeID = newCommissionStatusTypeID;
  }
  public String getCommissionStatusTypeName()
  {
  	return commissionStatusTypeName;
  }
  public void setCommissionStatusTypeName(String newCommissionStatusTypeName)
  {
  	commissionStatusTypeName = newCommissionStatusTypeName;
  }
  public int getCommissionTypeID()
  {
  	return commissionTypeID;
  }
  public void setCommissionTypeID(int newCommissionTypeID)
  {
  	commissionTypeID = newCommissionTypeID;
  }
  public String getCommissionTypeName()
  {
  	return commissionTypeName;
  }
  public void setCommissionTypeName(String newCommissionTypeName)
  {
  	commissionTypeName = newCommissionTypeName;
  }
  public String getCommissionTypeCategoryName()
  {
  	return commissionTypeCategoryName;
  }
  public void setCommissionTypeCategoryName(String newCommissionTypeCategoryName)
  {
  	commissionTypeCategoryName = newCommissionTypeCategoryName;
  }
  public int getCommissionTypeCtageoryID()
  {
  	return commissionTypeCtageoryID;
  }
  public void setCommissionTypeCtageoryID(int newCommissionTypeCtageoryID)
  {
  	commissionTypeCtageoryID = newCommissionTypeCtageoryID;
  }
  
  public Vector getCommissionItemIDs()
  {
	  	return commissionItemIDs;
  }
  public void setCommissionItemIDs(Vector newCommissionItemIDs)
  {
	  	commissionItemIDs = newCommissionItemIDs;
  }
  
  public Vector getCommissionItemNames()
  {
	  	return commissionItemNames;
  }
  public void setCommissionItemNames(Vector newCommissionItemNames)
  {
	  	commissionItemNames = newCommissionItemNames;
  }
  
  public Vector getCommissionItemFactorIDs()
  {
	  	return commissionItemFactorIDs;
  }
  public void setCommissionItemFactorIDs(Vector newCommissionItemFactorIDs)
  {
	  	commissionItemFactorIDs = newCommissionItemFactorIDs;
  }
 
  
public Vector getCommissionItemDCMIDs()
{
		return commissionItemDCMIDs;
}
public void setCommissionItemDCMIDs(Vector newCommissionItemDCMIDs)
{
		commissionItemDCMIDs = newCommissionItemDCMIDs;
}
  
  public Vector getCommissionFactorIDs()
  {
	  	return commissionFactorIDs;
  }
  public void setCommissionFactorIDs(Vector newCommissionFactorIDs)
  {
	  	commissionFactorIDs = newCommissionFactorIDs;
  }
  
  public Vector getCommissionFactorValues()
  {
	  	return commissionFactorValues;
  }
  public void setCommissionFactorValues(Vector newCommissionFactorValues)
  {
	  	commissionFactorValues = newCommissionFactorValues;
  }
  
  public Vector getCommissionFactorNames()
  {
	  	return commissionFactorNames;
  }
  public void setCommissionFactorNames(Vector newCommissionFactorNames)
  {
	  	commissionFactorNames = newCommissionFactorNames;
  }  
  public int getCommissionDataViewTypeID()
  {
    return commissionDataViewTypeID;
  }
  public void setCommissionDataViewTypeID(int newCommissionDataViewTypeID)
  {
    commissionDataViewTypeID = newCommissionDataViewTypeID;
  }
  public String getCommissionDataViewTypeName()
  {
    return commissionDataViewTypeName;
  }
  public void setCommissionDataViewTypeName(String newCommissionDataViewTypeName)
  {
    commissionDataViewTypeName = newCommissionDataViewTypeName;
  }  
  public int getCommissionBaseID()
  {
  	return commissionBaseID;
  }
  public void setCommissionBaseID(int newCommissionBaseID)
  {
  	commissionBaseID = newCommissionBaseID;
  }  
  
  public int getCommissionChannelId()
  {
	  return commissionChannelId;
  }
  public void setCommissionChannelId(int newComissionChannelId)
  {
	  commissionChannelId = newComissionChannelId;
  }
  
  public String getCommissionChannelName ()
  {
	  return commissionChannelName;
  }
  public void setCommissionChannelName (String newCommissionChannelName)
  {
	  commissionChannelName = newCommissionChannelName;
  }
  
  public   SIPSearchReportModel(Connection con,ResultSet rs)throws Exception
  {
      setCommissionID(rs.getInt("COMMISSION_DETAIL_ID"));
      setCommissionName(rs.getString("COMMISSION_NAME"));
      if (rs.getString("COMMISSION_CREATION_DATE")!=null)
      setCommissionCreationDate(rs.getString("COMMISSION_CREATION_DATE"));
      else
      setCommissionCreationDate("");
      setCommissionStartDate(rs.getString("COMMISSION_START_DATE"));
      setCommissionEndDate(rs.getString("COMMISSION_END_DATE"));
      setCommissionDescription(rs.getString("COMMISSION_DESCRIPTION"));
     
      //removed for optimization
      // setCommissionDataViewSQL(rs.getString("COMMISSION_DATA_VIEW_SQL"));    
      
      setCommissionDataViewID(rs.getInt("COMMISSION_DATA_VIEW_ID"));
      setCommissionStatusTypeID(rs.getInt("COMMISSION_STATUS_TYPE_ID")); 
      setCommissionStatusTypeName(rs.getString("COMMISSION_STATUS_TYPE_NAME"));
      setCommissionTypeID(rs.getInt("COMMISSION_TYPE_ID"));
      setCommissionTypeName(rs.getString("COMMISSION_TYPE_NAME"));
      setCommissionTypeCategoryName(rs.getString("COMMISSION_TYPE_CATEGORY_NAME"));
      setCommissionTypeCtageoryID(rs.getInt("COMMISSION_TYPE_CATEGORY_ID"));
      setCommissionDataViewTypeID(rs.getInt("DATA_VIEW_TYPE_ID"));
      setCommissionDataViewTypeName(rs.getString("DATA_VIEW_TYPE_NAME"));
      setCommissionBaseID(rs.getInt("COMMISSION_BASE_ID"));
      setCommissionChannelId(rs.getInt("COMMISSION_CHANNEL_ID"));
      setCommissionChannelName(rs.getString("COMMISSION_CHANNEL_NAME"));
      

      
  }


  

}
package com.mobinil.sds.core.system.sip.model;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.*;
import java.io.Serializable;
public class SIPModel implements Serializable
{
  int sipID = 0;
  String sipName = "";
  String sipCreationDate;
 
public String getsipCreationDate() {
	return sipCreationDate;
}
public void setsipCreationDate(String sipCreationDate) {
	this.sipCreationDate = sipCreationDate;
}
  String sipStartDate;
  String sipEndDate;
  String sipDescription = "";
  String sipDataViewSQL = "";
  int sipDataViewID = 0;
  int sipStatusTypeID = 0;
  String sipStatusTypeName ="";
  int sipTypeID = 0;
  int sipYearID = 0;
  public int getSipYearID()
{
   return sipYearID;
}
public void setSipYearID(int sipYearID)
{
   this.sipYearID = sipYearID;
}
String sipTypeName ="";
  String sipTypeCategoryName ="";
  int sipTypeCtageoryID =0;
  int sipDataViewTypeID = 0;
  String sipDataViewTypeName = "";
  Boolean sipHasPayment;
  int sipChannelId = 0;
  String sipChannelName = "";
  String sipReportQuarter="";                                   
  
  String sipReportLabel="";
    String sipReportYear="";
    String sipReportLCID="";                             
    String sipReportNICom="";                                 
    String sipReportSOPID="";
  

  Vector sipItemIDs = new Vector();
  Vector sipItemNames = new Vector();
  Vector sipItemFactorIDs = new Vector();
  Vector sipItemDCMIDs = new Vector();

  Vector sipFactorIDs = new Vector();
  Vector sipFactorValues = new Vector();    
  Vector sipFactorNames = new Vector(); 

  

public Boolean getsipHasPayment()
  {
  	return sipHasPayment;
  }
  public void setsipHasPayment(Boolean newsipHasPayment)
  {
  	sipHasPayment = newsipHasPayment;
  }

  
  public int getsipID()
  {
  	return sipID;
  }
  public void setsipID(int newsipID)
  {
  	sipID = newsipID;
  }



 public String getsipName()
 {
 	return sipName;
 }
 public void setsipName(String newsipName)
 {
 	sipName = newsipName;
 }
  public String getsipStartDate()
  {
  	return sipStartDate;
  }
  public void setsipStartDate(String newsipStartDate)
  {
  	sipStartDate = newsipStartDate;
  }
  public String getsipEndDate()
  {
  	return sipEndDate;
  }
  public void setsipEndDate(String newsipEndDate)
  {
  	sipEndDate = newsipEndDate;
  }
  public String getsipDescription()
  {
  	return sipDescription;
  }
  public void setsipDescription(String newsipDescription)
  {
  	sipDescription = newsipDescription;
  }
  public String getsipDataViewSQL()
  {
  	return sipDataViewSQL;
  }
  public void setsipDataViewSQL(String newsipDataViewSQL)
  {
  	sipDataViewSQL = newsipDataViewSQL;
  }
  public int getsipDataViewID()
  {
  	return sipDataViewID;
  }
  public void setsipDataViewID(int newsipDataViewID)
  {
  	sipDataViewID = newsipDataViewID;
  }
  public int getsipStatusTypeID()
  {
  	return sipStatusTypeID;
  }
  public void setsipStatusTypeID(int newsipStatusTypeID)
  {
  	sipStatusTypeID = newsipStatusTypeID;
  }
  public String getsipStatusTypeName()
  {
  	return sipStatusTypeName;
  }
  public void setsipStatusTypeName(String newsipStatusTypeName)
  {
  	sipStatusTypeName = newsipStatusTypeName;
  }
  public int getsipTypeID()
  {
  	return sipTypeID;
  }
  public void setsipTypeID(int newsipTypeID)
  {
  	sipTypeID = newsipTypeID;
  }
  public String getsipTypeName()
  {
  	return sipTypeName;
  }
  public void setsipTypeName(String newsipTypeName)
  {
  	sipTypeName = newsipTypeName;
  }
  public String getsipTypeCategoryName()
  {
  	return sipTypeCategoryName;
  }
  public void setsipTypeCategoryName(String newsipTypeCategoryName)
  {
  	sipTypeCategoryName = newsipTypeCategoryName;
  }
  public int getsipTypeCtageoryID()
  {
  	return sipTypeCtageoryID;
  }
  public void setsipTypeCtageoryID(int newsipTypeCtageoryID)
  {
  	sipTypeCtageoryID = newsipTypeCtageoryID;
  }
  
  public Vector getsipItemIDs()
  {
	  	return sipItemIDs;
  }
  public void setsipItemIDs(Vector newsipItemIDs)
  {
	  	sipItemIDs = newsipItemIDs;
  }
  
  public Vector getsipItemNames()
  {
	  	return sipItemNames;
  }
  public void setsipItemNames(Vector newsipItemNames)
  {
	  	sipItemNames = newsipItemNames;
  }
  
  public Vector getsipItemFactorIDs()
  {
	  	return sipItemFactorIDs;
  }
  public void setsipItemFactorIDs(Vector newsipItemFactorIDs)
  {
	  	sipItemFactorIDs = newsipItemFactorIDs;
  }
 
  
public Vector getsipItemDCMIDs()
{
		return sipItemDCMIDs;
}
public void setsipItemDCMIDs(Vector newsipItemDCMIDs)
{
		sipItemDCMIDs = newsipItemDCMIDs;
}
  
  public Vector getsipFactorIDs()
  {
	  	return sipFactorIDs;
  }
  public void setsipFactorIDs(Vector newsipFactorIDs)
  {
	  	sipFactorIDs = newsipFactorIDs;
  }
  
  public Vector getsipFactorValues()
  {
	  	return sipFactorValues;
  }
  public void setsipFactorValues(Vector newsipFactorValues)
  {
	  	sipFactorValues = newsipFactorValues;
  }
  
  public Vector getsipFactorNames()
  {
	  	return sipFactorNames;
  }
  public void setsipFactorNames(Vector newsipFactorNames)
  {
	  	sipFactorNames = newsipFactorNames;
  }  
  public int getsipDataViewTypeID()
  {
    return sipDataViewTypeID;
  }
  public void setsipDataViewTypeID(int newsipDataViewTypeID)
  {
    sipDataViewTypeID = newsipDataViewTypeID;
  }
  public String getsipDataViewTypeName()
  {
    return sipDataViewTypeName;
  }
  public void setsipDataViewTypeName(String newsipDataViewTypeName)
  {
    sipDataViewTypeName = newsipDataViewTypeName;
  }  
  
  
  public int getsipChannelId()
  {
	  return sipChannelId;
  }
  public void setsipChannelId(int newComissionChannelId)
  {
	  sipChannelId = newComissionChannelId;
  }
  
  public String getsipChannelName ()
  {
	  return sipChannelName;
  }
  public void setsipChannelName (String newsipChannelName)
  {
	  sipChannelName = newsipChannelName;
  }
  public String getSipReportQuarter()
  {
     return sipReportQuarter;
  }
  public void setSipReportQuarter(String sipReportQuarter)
  {
     this.sipReportQuarter = sipReportQuarter;
  }
  public String getSipReportLabel()
  {
     return sipReportLabel;
  }
  public void setSipReportLabel(String sipReportLabel)
  {
     this.sipReportLabel = sipReportLabel;
  }
  public String getSipReportYear()
  {
     return sipReportYear;
  }
  public void setSipReportYear(String sipReportYear)
  {
     this.sipReportYear = sipReportYear;
  }
  public String getSipReportLCID()
  {
     return sipReportLCID;
  }
  public void setSipReportLCID(String sipReportLCID)
  {
     this.sipReportLCID = sipReportLCID;
  }
  public String getSipReportNICom()
  {
     return sipReportNICom;
  }
  public void setSipReportNICom(String sipReportNICom)
  {
     this.sipReportNICom = sipReportNICom;
  }
  public String getSipReportSOPID()
  {
     return sipReportSOPID;
  }
  public void setSipReportSOPID(String sipReportSOPID)
  {
     this.sipReportSOPID = sipReportSOPID;
  }
  
  public SIPModel(Connection con,ResultSet rs)throws Exception
  {
    
     
     
     setsipID(rs.getInt("SIP_REPORT_DETAIL_ID"));
      setsipName(rs.getString("SIP_REPORT_NAME"));
      if (rs.getString("SIP_REPORT_CREATION_DATE")!=null)
      setsipCreationDate(rs.getString("SIP_REPORT_CREATION_DATE"));
      else
    	  setsipCreationDate("");
      setsipStartDate(rs.getString("SIP_REPORT_START_DATE"));
      setsipEndDate(rs.getString("SIP_REPORT_END_DATE"));
      setsipDescription(rs.getString("SIP_REPORT_DESCRIPTION"));
     

      setsipStatusTypeID(rs.getInt("SIP_REPORT_STATUS_TYPE_ID")); 
      setsipStatusTypeName(rs.getString("SIP_REPORT_STATUS_TYPE_NAME"));
      setsipTypeID(rs.getInt("SIP_REPORT_TYPE_ID"));
      setSipYearID(rs.getInt("YEAR_ID"));
      setsipTypeCategoryName(rs.getString("SIP_REPORT_TYPE_CATEGORY_NAME"));
      setsipTypeCtageoryID(rs.getInt("SIP_REPORT_TYPE_CATEGORY_ID"));

      
      setsipChannelId(rs.getInt("SIP_REPORT_CHANNEL_ID"));
      setsipChannelName(rs.getString("SIP_REPORT_CHANNEL_NAME"));
      
      setSipReportQuarter(rs.getString("SIP_REPORT_QUARTER_ID"));
      setSipReportLabel(rs.getString("SIP_REPORT_LABEL"));
      setSipReportYear(rs.getString("SIP_REPORT_YEAR"));
      setSipReportLCID(rs.getString("SIP_REPORT_LC_ID")==null?"":rs.getString("SIP_REPORT_LC_ID"));
      setSipReportNICom(rs.getString("SIP_REPORT_NI_ID")==null?"":rs.getString("SIP_REPORT_NI_ID"));
      setSipReportSOPID(rs.getString("SIP_REPORT_SOP_ID")==null?"":rs.getString("SIP_REPORT_SOP_ID"));
      
      
      
      
      
      
                                                   
      
            
      
      
      
         
  }
  
  
  
}
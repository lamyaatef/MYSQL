package com.mobinil.sds.core.system.sip.model;






import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.*;
import java.io.Serializable;
public class sipReportModel implements Serializable
{
  int sipReportID = 0;
  String sipReportName = "";
  String sipReportCreationDate;
  
  String sipReportQuarter="";                                   
  
String sipReportLabel="";
  String sipReportYear="";
  String sipReportLCID="";                             
  String sipReportNICom="";                                 
  String sipReportSOPID="";
  
public String getsipReportCreationDate() {
	return sipReportCreationDate;
}
public void setsipReportCreationDate(String sipReportCreationDate) {
	this.sipReportCreationDate = sipReportCreationDate;
}
  String sipReportStartDate;
  String sipReportEndDate;
  String sipReportDescription = "";
  String sipReportDataViewSQL = "";
  int sipReportDataViewID = 0;
  int sipReportStatusTypeID = 0;
  String sipReportStatusTypeName ="";
  int sipReportTypeID = 0;
  String sipReportTypeName ="";
  String sipReportTypeCategoryName ="";
  int sipReportTypeCtageoryID =0;
  int sipReportDataViewTypeID = 0;
  String sipReportDataViewTypeName = "";
  
  int sipReportChannelId = 0;
  String sipReportChannelName = "";
  


  Vector sipReportItemIDs = new Vector();
  Vector sipReportItemNames = new Vector();
  Vector sipReportItemFactorIDs = new Vector();
  Vector sipReportItemDCMIDs = new Vector();

  Vector sipReportFactorIDs = new Vector();
  Vector sipReportFactorValues = new Vector();    
  Vector sipReportFactorNames = new Vector(); 
 
  public int getsipReportID()
  {
  	return sipReportID;
  }
  public void setsipReportID(int newsipReportID)
  {
  	sipReportID = newsipReportID;
  }



 public String getsipReportName()
 {
 	return sipReportName;
 }
 public void setsipReportName(String newsipReportName)
 {
 	sipReportName = newsipReportName;
 }
  public String getsipReportStartDate()
  {
  	return sipReportStartDate;
  }
  public void setsipReportStartDate(String newsipReportStartDate)
  {
  	sipReportStartDate = newsipReportStartDate;
  }
  public String getsipReportEndDate()
  {
  	return sipReportEndDate;
  	
  }
  public void setsipReportEndDate(String newsipReportEndDate)
  {
  	sipReportEndDate = newsipReportEndDate;
  }
  public String getsipReportDescription()
  {
  	return sipReportDescription;
  }
  public void setsipReportDescription(String newsipReportDescription)
  {
  	sipReportDescription = newsipReportDescription;
  }
  public String getsipReportDataViewSQL()
  {
  	return sipReportDataViewSQL;
  }
  public void setsipReportDataViewSQL(String newsipReportDataViewSQL)
  {
  	sipReportDataViewSQL = newsipReportDataViewSQL;
  	
  	
  }
  public int getsipReportDataViewID()
  {
  	return sipReportDataViewID;
  }
  public void setsipReportDataViewID(int newsipReportDataViewID)
  {
  	sipReportDataViewID = newsipReportDataViewID;
  }
  public int getsipReportStatusTypeID()
  {
  	return sipReportStatusTypeID;
  }
  public void setsipReportStatusTypeID(int newsipReportStatusTypeID)
  {
  	sipReportStatusTypeID = newsipReportStatusTypeID;
  }
  public String getsipReportStatusTypeName()
  {
  	return sipReportStatusTypeName;
  }
  public void setsipReportStatusTypeName(String newsipReportStatusTypeName)
  {
  	sipReportStatusTypeName = newsipReportStatusTypeName;
  }
  public int getsipReportTypeID()
  {
  	return sipReportTypeID;
  }
  public void setsipReportTypeID(int newsipReportTypeID)
  {
  	sipReportTypeID = newsipReportTypeID;
  }
  public String getsipReportTypeName()
  {
  	return sipReportTypeName;
  }
  public void setsipReportTypeName(String newsipReportTypeName)
  {
  	sipReportTypeName = newsipReportTypeName;
  }
  public String getsipReportTypeCategoryName()
  {
  	return sipReportTypeCategoryName;
  }
  public void setsipReportTypeCategoryName(String newsipReportTypeCategoryName)
  {
  	sipReportTypeCategoryName = newsipReportTypeCategoryName;
  }
  public int getsipReportTypeCtageoryID()
  {
  	return sipReportTypeCtageoryID;
  }
  public void setsipReportTypeCtageoryID(int newsipReportTypeCtageoryID)
  {
  	sipReportTypeCtageoryID = newsipReportTypeCtageoryID;
  }
  
  public Vector getsipReportItemIDs()
  {
	  	return sipReportItemIDs;
  }
  public void setsipReportItemIDs(Vector newsipReportItemIDs)
  {
	  	sipReportItemIDs = newsipReportItemIDs;
  }
  
  public Vector getsipReportItemNames()
  {
	  	return sipReportItemNames;
  }
  public void setsipReportItemNames(Vector newsipReportItemNames)
  {
	  	sipReportItemNames = newsipReportItemNames;
  }
  
  public Vector getsipReportItemFactorIDs()
  {
	  	return sipReportItemFactorIDs;
  }
  public void setsipReportItemFactorIDs(Vector newsipReportItemFactorIDs)
  {
	  	sipReportItemFactorIDs = newsipReportItemFactorIDs;
  }
 
  
public Vector getsipReportItemDCMIDs()
{
		return sipReportItemDCMIDs;
}
public void setsipReportItemDCMIDs(Vector newsipReportItemDCMIDs)
{
		sipReportItemDCMIDs = newsipReportItemDCMIDs;
}
  
  public Vector getsipReportFactorIDs()
  {
	  	return sipReportFactorIDs;
  }
  public void setsipReportFactorIDs(Vector newsipReportFactorIDs)
  {
	  	sipReportFactorIDs = newsipReportFactorIDs;
  }
  
  public Vector getsipReportFactorValues()
  {
	  	return sipReportFactorValues;
  }
  public void setsipReportFactorValues(Vector newsipReportFactorValues)
  {
	  	sipReportFactorValues = newsipReportFactorValues;
  }
  
  public Vector getsipReportFactorNames()
  {
	  	return sipReportFactorNames;
  }
  public void setsipReportFactorNames(Vector newsipReportFactorNames)
  {
	  	sipReportFactorNames = newsipReportFactorNames;
  }  
  public int getsipReportDataViewTypeID()
  {
    return sipReportDataViewTypeID;
  }
  public void setsipReportDataViewTypeID(int newsipReportDataViewTypeID)
  {
    sipReportDataViewTypeID = newsipReportDataViewTypeID;
  }
  public String getsipReportDataViewTypeName()
  {
    return sipReportDataViewTypeName;
  }
  public void setsipReportDataViewTypeName(String newsipReportDataViewTypeName)
  {
    sipReportDataViewTypeName = newsipReportDataViewTypeName;
  }  

  
  public int getsipReportChannelId()
  {
	  return sipReportChannelId;
  }
  public void setsipReportChannelId(int newComissionChannelId)
  {
	  sipReportChannelId = newComissionChannelId;
  }
  
  public String getsipReportChannelName ()
  {
	  return sipReportChannelName;
  }
  public void setsipReportChannelName (String newsipReportChannelName)
  {
	  sipReportChannelName = newsipReportChannelName;
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
  public sipReportModel(Connection con,ResultSet rs)throws Exception
  {
      
      
      
      setsipReportID(rs.getInt("SIP_REPORT_DETAIL_ID"));
      setsipReportName(rs.getString("SIP_REPORT_NAME"));
      if (rs.getString("SIP_REPORT_CREATION_DATE")!=null)
      setsipReportCreationDate(rs.getString("SIP_REPORT_CREATION_DATE"));
      else
          setsipReportCreationDate("");
      setsipReportStartDate(rs.getString("SIP_REPORT_START_DATE"));
      setsipReportEndDate(rs.getString("SIP_REPORT_END_DATE"));
      setsipReportDescription(rs.getString("SIP_REPORT_DESCRIPTION"));
     
      //removed for optimization
      // setsipReportDataViewSQL(rs.getString("SIP_REPORT_DATA_VIEW_SQL"));    
      
//      setsipReportDataViewID(rs.getInt("SIP_REPORT_DATA_VIEW_ID"));
      setsipReportStatusTypeID(rs.getInt("SIP_REPORT_STATUS_TYPE_ID")); 
      setsipReportStatusTypeName(rs.getString("SIP_REPORT_STATUS_TYPE_NAME"));
      setsipReportTypeID(rs.getInt("SIP_REPORT_TYPE_ID"));
//      setsipReportTypeName(rs.getString("SIP_REPORT_TYPE_NAME"));
      setsipReportTypeCategoryName(rs.getString("SIP_REPORT_TYPE_CATEGORY_NAME"));
      setsipReportTypeCtageoryID(rs.getInt("SIP_REPORT_TYPE_CATEGORY_ID"));
//      setsipReportDataViewTypeID(rs.getInt("DATA_VIEW_TYPE_ID"));
//      setsipReportDataViewTypeName(rs.getString("DATA_VIEW_TYPE_NAME"));
      
      setsipReportChannelId(rs.getInt("SIP_REPORT_CHANNEL_ID"));
      setsipReportChannelName(rs.getString("SIP_REPORT_CHANNEL_NAME"));
      
      setSipReportQuarter(rs.getString("SIP_REPORT_QUARTER_ID"));
      setSipReportLabel(rs.getString("SIP_REPORT_LABEL"));
      setSipReportYear(rs.getString("SIP_REPORT_YEAR"));
      setSipReportLCID(rs.getString("SIP_REPORT_LC_ID")==null?"":rs.getString("SIP_REPORT_LC_ID"));
      setSipReportNICom(rs.getString("SIP_REPORT_NI_ID")==null?"":rs.getString("SIP_REPORT_NI_ID"));
      setSipReportSOPID(rs.getString("SIP_REPORT_SOP_ID")==null?"":rs.getString("SIP_REPORT_SOP_ID"));
   

  }
  
  


  public sipReportModel()
  {
  }
}
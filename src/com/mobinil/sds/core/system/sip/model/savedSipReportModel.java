package com.mobinil.sds.core.system.sip.model;


import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.*;
import java.io.Serializable;
public class savedSipReportModel implements Serializable
{
  int sipReportID = 0;
  String  sipYearId="";
  String  sipYearName="";
  
  String sipReportName = "";
  
  
  String SipReportQuarterId="";       
  String SipReportQuarterName=""; 
  

  public String getSipReportQuarterName() {
	return SipReportQuarterName;
}
public void setSipReportQuarterName(String sipReportQuarterName) {
	SipReportQuarterName = sipReportQuarterName;
}
public String getSipYearId() {
	return sipYearId;
}
public void setSipYearId(String sipYearId) {
	this.sipYearId = sipYearId;
}
public String getSipYearName() {
	return sipYearName;
}
public void setSipYearName(String sipYearName) {
	this.sipYearName = sipYearName;
}




String sipReportYear="";





String sipReportDataViewSQL = "";
   String sipReportDataViewID = "";
   String sipReportStatusTypeID = "";
  String sipReportStatusTypeName ="";
  String sipReportTypeID = "";
  String sipReportTypeName ="";

  String  sipReportDataViewTypeID = "";
  String sipReportDataViewTypeName = "";
  

  



 
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



  public String getsipReportDataViewSQL()
  {
  	return sipReportDataViewSQL;
  }
  public void setsipReportDataViewSQL(String newsipReportDataViewSQL)
  {
  	sipReportDataViewSQL = newsipReportDataViewSQL;
  	
  	
  }
  public String getsipReportDataViewID()
  {
  	return sipReportDataViewID;
  }
  public void setsipReportDataViewID(String newsipReportDataViewID)
  {
  	sipReportDataViewID = newsipReportDataViewID;
  }
  public String getsipReportStatusTypeID()
  {
  	return sipReportStatusTypeID;
  }
  public void setsipReportStatusTypeID(String newsipReportStatusTypeID)
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
  public String getsipReportTypeID()
  {
  	return sipReportTypeID;
  }
  public void setsipReportTypeID(String newsipReportTypeID)
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


  public String getsipReportDataViewTypeID()
  {
    return sipReportDataViewTypeID;
  }
  public void setsipReportDataViewTypeID(String newsipReportDataViewTypeID)
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

  

  public String getSipReportQuarterId()
  {
     return SipReportQuarterId;
  }
  public void setSipReportQuarterId(String SipReportQuarterId)
  {
     this.SipReportQuarterId = SipReportQuarterId;
  }

  public savedSipReportModel(Connection con,ResultSet rs)throws Exception
  {
      setsipReportID(rs.getInt("REPORT_ID"));
      setsipReportName(rs.getString("REPORT_NAME"));
      
     
      //removed for optimization
      // setsipReportDataViewSQL(rs.getString("SIP_REPORT_DATA_VIEW_SQL"));    
      
      setsipReportDataViewID(rs.getString("REPORT_DATA_VIEW_ID"));
      setsipReportStatusTypeID(rs.getString("REPORT_STATUS_ID")); 
      setsipReportStatusTypeName(rs.getString("REPORT_STATUS_TYPE_NAME"));
      
      setsipReportTypeID(rs.getString("REPORT_TYPE_ID"));
      
      setsipReportTypeName(rs.getString("REPORT_TYPE_NAME"));
     
      
      setsipReportDataViewTypeID(rs.getString("DATA_VIEW_TYPE_ID"));
      setsipReportDataViewTypeName(rs.getString("DATA_VIEW_TYPE_NAME"));
      

     /*
      setSipReportQuarterId(rs.getString("QUARTER_ID"));
      setSipReportQuarterName(rs.getString("QUARTER_NAME"));*/
      setSipYearId(rs.getString("YEAR_ID"));
      setSipYearName(rs.getString("YEAR_NAME"));

  }
  
  


  public savedSipReportModel()
  {
  }
}
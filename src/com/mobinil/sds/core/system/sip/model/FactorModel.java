package com.mobinil.sds.core.system.sip.model;
import java.sql.*;
public class FactorModel 
{
  int sipReportFactorID = 0;
  String sipReportFactorName = "";
  int sipReportFactorValue = 0;
  int sipReportItemID = 0;

  String sipReportItemName = "";
  int sipReportID = 0;
  int sipReportItemAmount =0;
  int sipReportDCMID = 0;

  String sipReportDCMName = "";
  String sipReportDCMCode ="";
  String sipReportName = "";
  String sipReportType = "";
  String sipReportCategory = "";
  
  public int getsipReportFactorID()
  {
  	return sipReportFactorID;
  }
  public void setsipReportFactorID(int newsipReportFactorID)
  {
  	sipReportFactorID = newsipReportFactorID;
  }
  
  public String getsipReportFactorName()
  {
  	return sipReportFactorName;
  }
  public void setsipReportFactorName(String newsipReportFactorName)
  {
  	sipReportFactorName = newsipReportFactorName;
  }
  
  public double getsipReportFactorValue()
  {
  	return sipReportFactorValue;
  }
  public void setsipReportFactorValue(int newsipReportFactorValue)
  {
  	sipReportFactorValue = newsipReportFactorValue;
  }
  public int getsipReportItemID()
  {
  	return sipReportItemID;
  }
  public void setsipReportItemID(int newsipReportItemID)
  {
  	sipReportItemID = newsipReportItemID;
  }  


  public String getsipReportItemName()
  {
  	return sipReportItemName;
  }
  public void setsipReportItemName(String newsipReportItemName)
  {
  	sipReportItemName = newsipReportItemName;
  }

  public int getsipReportID()
  {
  	return sipReportID;
  }
  public void setsipReportID(int newsipReportID)
  {
  	sipReportID = newsipReportID;
  }

  public int getsipReportItemAmount()
  {
  	return sipReportItemAmount;
  }
  public void setsipReportItemAmount(int newsipReportItemAmount)
  {
  	sipReportItemAmount = newsipReportItemAmount;
  }

  public int getsipReportDCMID()
  {
    return sipReportDCMID;
  }
  public void setsipReportDCMID(int newsipReportDCMID)
  {
    sipReportDCMID = newsipReportDCMID;
  } 
  
  public String getsipReportDCMName()
  {
  	return sipReportDCMName;
  }
  public void setsipReportDCMName(String newsipReportDCMName)
  {
  	sipReportDCMName = newsipReportDCMName;
  }

  public String getsipReportDCMCode()
  {
  	return sipReportDCMCode;
  }
  public void setsipReportDCMCode(String newsipReportDCMCode)
  {
  	sipReportDCMCode = newsipReportDCMCode;
  }

  public String getsipReportName()
  {
  	return sipReportName;
  }
  public void setsipReportName(String newsipReportName)
  {
  	sipReportName = newsipReportName;
  }

  public String getsipReportType()
  {
  	return sipReportType;
  }
  public void setsipReportType(String newsipReportType)
  {
  	sipReportType = newsipReportType;
  }

  public String getsipReportCategory()
  {
  	return sipReportCategory;
  }
  public void setsipReportCategory(String newsipReportCategory)
  {
  	sipReportCategory = newsipReportCategory;
  }

  public FactorModel(ResultSet rs)throws Exception
  {
    setsipReportFactorID(rs.getInt("sip_Report_FACTOR_ID"));
    setsipReportFactorName(rs.getString("sip_Report_FACTOR_NAME"));
    setsipReportFactorValue(rs.getInt("sip_Report_FACTOR_VALUE"));
    System.out.println ( "sip_Report_FACTOR_VALUE issssssss "+ rs.getInt("sip_Report_FACTOR_VALUE"));
    setsipReportItemID(rs.getInt("sip_Report_ITEM_ID"));
    setsipReportItemName(rs.getString("sip_Report_ITEM_NAME"));
    setsipReportID(rs.getInt("sip_Report_DETAIL_ID"));
    setsipReportItemAmount(rs.getInt("sip_Report_ITEM_AMOUNT"));
    setsipReportDCMID(rs.getInt("DCM_ID"));
  }
  public FactorModel()
  {
  }
}
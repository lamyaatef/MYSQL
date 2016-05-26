package com.mobinil.sds.core.system.sip.model;




import java.sql.*;
public class sipReportItemModel 
{
 
	
	String DCM_ID="";
	String SIP_REPORT_ITEM_NAME ="";
	String SIP_REPORT_ITEM_AMOUNT ="";
	String INCLUDE="";
	
	
	
	

  
  


  public String getDCM_ID() {
	return DCM_ID;
}
public void setDCM_ID(String dCMID) {
	DCM_ID = dCMID;
}
public String getSIP_REPORT_ITEM_NAME() {
	return SIP_REPORT_ITEM_NAME;
}
public void setSIP_REPORT_ITEM_NAME(String sIPREPORTITEMNAME) {
	SIP_REPORT_ITEM_NAME = sIPREPORTITEMNAME;
}
public String getSIP_REPORT_ITEM_AMOUNT() {
	return SIP_REPORT_ITEM_AMOUNT;
}
public void setSIP_REPORT_ITEM_AMOUNT(String sIPREPORTITEMAMOUNT) {
	SIP_REPORT_ITEM_AMOUNT = sIPREPORTITEMAMOUNT;
}
public String getINCLUDE() {
	return INCLUDE;
}
public void setINCLUDE(String iNCLUDE) {
	INCLUDE = iNCLUDE;
}

public sipReportItemModel(ResultSet rs)throws Exception
{
	setDCM_ID(rs.getString("DCM_ID"));
  setSIP_REPORT_ITEM_NAME(rs.getString("SIP_REPORT_ITEM_NAME"));
  setSIP_REPORT_ITEM_AMOUNT(rs.getString("SIP_REPORT_ITEM_AMOUNT"));
  setINCLUDE(rs.getString("INCLUDE"));

}


public sipReportItemModel()
  {
  }
}
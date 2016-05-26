package com.mobinil.sds.core.system.cam.memo.model;

import java.sql.ResultSet;

public class MemoRemovalImportModel {
private String dcmCode;
private String reason;
private   String rowNum;
public MemoRemovalImportModel(String dcmCode, String reason) {
	super();
	this.dcmCode = dcmCode;
	this.reason = reason;
}
public MemoRemovalImportModel(ResultSet res)
{
  try
  {

	  rowNum=res.getString("ROW_NUM");
	  dcmCode=res.getString("DCM_CODE");
	  reason = res.getString("REASON");
	
}
  catch(Exception e)
  {
    e.printStackTrace();
  } 
}
public String getRowNum() {
	return rowNum;
}
public void setRowNum(String rowNum) {
	this.rowNum= rowNum;
}
public String getDcmCode() {
	return dcmCode;
}
public void setDcmCode(String dcmCode) {
	this.dcmCode = dcmCode;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}

}

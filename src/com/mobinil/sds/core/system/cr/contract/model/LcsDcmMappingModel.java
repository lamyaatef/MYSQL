package com.mobinil.sds.core.system.cr.contract.model;
import java.sql.*;
import java.io.*;

import com.mobinil.sds.core.system.Model;

public class LcsDcmMappingModel extends Model
{

  String dcmCode;
  String lcsName;
  String dcmName;
  String dcmLcsNameId;
  

  public static final String LCS_NAME = "LCS_NAME";
  public static final String DCM_NAME = "DCM_NAME";
  public static final String DCM_CODE = "DCM_CODE";
  public static final String DCM_LCS_NAME_ID = "DCM_LCS_NAME_ID";

  
  public LcsDcmMappingModel()
  {
  }
  
  public  void fillInstance(java.sql.ResultSet res)
  {
	  try
	    {
	     lcsName = res.getString(LCS_NAME); 
	     dcmName = res.getString(DCM_NAME);
	     dcmCode = res.getString(DCM_CODE);
	     dcmLcsNameId = res.getString(DCM_LCS_NAME_ID);
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }  
  }
  
  public LcsDcmMappingModel(ResultSet res)
  {
    fillInstance(res); 
  }

public String getDcmLcsNameId() {
	return dcmLcsNameId;
}

public void setDcmLcsNameId(String dcmLcsNameId) {
	this.dcmLcsNameId = dcmLcsNameId;
}

/////////////////////////////////////////////////////////////
  public String getLcsName()
  {
    return lcsName;
  }
  public void setLcsName(String newLcsName)
  {
    lcsName = newLcsName;
  }
//////////////////////////////////////////////////////////

 public String getDcmName()
  {
    return dcmName;
  }
  public void setDcmName(String newDcmName)
  {
    dcmName = newDcmName;
  }
  ////////////////////////////////////////////////////////////
  public String getDcmCode()
  {
    return dcmCode;
  }
  public void setDcmCode(String newDcmCode)
  {
    dcmName = newDcmCode;
  }
}
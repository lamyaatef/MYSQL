package com.mobinil.sds.core.system.gn.scopes.model;
import java.sql.*;
import java.io.*;

public class ScopesModel implements Serializable
{
  String dataviewId;
  String dataviewIssue;
  String dataviewVersion;
  String dataviewName;
  String dataviewOverrideSQL;
  String dataviewTypeId;
  String dataviewTypeName;
  String dataviewDescription;
  String dataviewStatusId;
  String dataviewStatusName;
  String dataviewUnique;

  public static final String DATA_VIEW_ID = "DATA_VIEW_ID";
  public static final String DATA_VIEW_ISSUE = "DATA_VIEW_ISSUE";
  public static final String DATA_VIEW_VERSION = "DATA_VIEW_VERSION";
  public static final String DATA_VIEW_NAME = "DATA_VIEW_NAME";
  public static final String DATA_VIEW_OVERRIDE_SQL = "DATA_VIEW_OVERRIDE_SQL";
  public static final String DATA_VIEW_TYPE_ID = "DATA_VIEW_TYPE_ID";
  public static final String DATA_VIEW_TYPE_NAME = "DATA_VIEW_TYPE_NAME";
  public static final String DATA_VIEW_DESCRIPTION = "DATA_VIEW_DESCRIPTION";
  public static final String DATA_VIEW_STATUS_ID = "DATA_VIEW_STATUS_ID";
  public static final String DATA_VIEW_STATUS_NAME = "DATA_VIEW_STATUS_NAME";
  public static final String DATA_VIEW_UNIQUE = "DATA_VIEW_UNIQUE";
  

  public ScopesModel()
  {
  }

  public ScopesModel(ResultSet res)
  {
      try
      {
        dataviewId = res.getString(DATA_VIEW_ID);
        dataviewIssue = res.getString(DATA_VIEW_ISSUE);
        dataviewVersion = res.getString(DATA_VIEW_VERSION);
        dataviewName = res.getString(DATA_VIEW_NAME);
        dataviewOverrideSQL = res.getString(DATA_VIEW_OVERRIDE_SQL);
        dataviewTypeId = res.getString(DATA_VIEW_TYPE_ID);
        dataviewTypeName = res.getString(DATA_VIEW_TYPE_NAME);
        dataviewDescription = res.getString(DATA_VIEW_DESCRIPTION);
        dataviewStatusId = res.getString(DATA_VIEW_STATUS_ID); 
        dataviewStatusName = res.getString(DATA_VIEW_STATUS_NAME); 
        dataviewUnique = res.getString(DATA_VIEW_UNIQUE); 
      }
      catch(Exception e)
      {
    
      }  
  } 

  public String getDataviewId()
  {
    return dataviewId;
  }

  public void setDataviewId(String newDataviewId)
  {
    dataviewId = newDataviewId;
  } 
/////////////////////////////////////  
  public String getDataviewIssue()
  {
    return dataviewIssue;
  }

  public void setDataviewIssue(String newDataviewIssue)
  {
    dataviewIssue = newDataviewIssue;
  } 
/////////////////////////////////////  
  public String getDataviewVersion()
  {
    return dataviewVersion;
  }

  public void setDataviewVersion(String newDataviewVersion)
  {
    dataviewVersion = newDataviewVersion;
  } 
/////////////////////////////////////  
  public String getDataviewName()
  {
    return dataviewName;
  }

  public void setDataviewName(String newDataviewName)
  {
    dataviewName = newDataviewName;
  } 
/////////////////////////////////////  
  public String getDataviewOverrideSQL()
  {
    return dataviewOverrideSQL;
  }

  public void setDataviewOverrideSQL(String newDataviewOverrideSQL)
  {
    dataviewOverrideSQL = newDataviewOverrideSQL;
  } 
/////////////////////////////////////  
  public String getDataviewTypeId()
  {
    return dataviewTypeId;
  }

  public void setDataviewTypeId(String newDataviewTypeId)
  {
    dataviewTypeId = newDataviewTypeId;
  } 
/////////////////////////////////////  
  public String getDataviewTypeName()
  {
    return dataviewTypeName;
  }

  public void setDataviewTypeName(String newDataviewTypeName)
  {
    dataviewTypeName = newDataviewTypeName;
  } 
/////////////////////////////////////  
  public String getDataviewDescription()
  {
    return dataviewDescription;
  }

  public void setDataviewDescription(String newDataviewDescription)
  {
    dataviewDescription = newDataviewDescription;
  } 
/////////////////////////////////////  
  public String getDataviewStatusId()
  {
    return dataviewStatusId;
  }

  public void setDataviewStatusId(String newDataviewStatusId)
  {
    dataviewStatusId = newDataviewStatusId;
  } 
/////////////////////////////////////  
  public String getDataviewStatusName()
  {
    return dataviewStatusName;
  }

  public void setDataviewStatusName(String newDataviewStatusName)
  {
    dataviewStatusName = newDataviewStatusName;
  } 
/////////////////////////////////////  
  public String getDataviewUnique()
  {
    return dataviewUnique;
  }

  public void setDataviewUnique(String newDataviewUnique)
  {
    dataviewUnique = newDataviewUnique;
  } 
/////////////////////////////////////  

}
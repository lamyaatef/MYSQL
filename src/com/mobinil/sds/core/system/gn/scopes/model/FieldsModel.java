package com.mobinil.sds.core.system.gn.scopes.model;
import java.sql.*;
import java.io.*;

public class FieldsModel implements Serializable
{
  String dataviewId;
  String dataviewIssue;
  String dataviewVersion;
  String fieldId;
  String fieldName;
  String fieldDescription;
  String fieldSQLCash;
  String fieldDisplayTypeId;
  String fieldDisplayTypeName;
  String fieldTypeName;
  String fieldTypeObjectId;
  String fieldRFObjrct;
  String itemPosition;

  public static final String DATA_VIEW_ID = "DATA_VIEW_ID";
  public static final String DATA_VIEW_ISSUE = "DATA_VIEW_ISSUE";
  public static final String DATA_VIEW_VERSION = "DATA_VIEW_VERSION";
  public static final String FIELD_ID = "FIELD_ID";
  public static final String FIELD_NAME = "FIELD_NAME";
  public static final String FIELD_DESCRIPTION = "FIELD_DESCRIPTION";
  public static final String FIELD_SQL_CASH = "FIELD_SQL_CASH";
  public static final String FIELD_DISPLAY_TYPE_ID = "FIELD_DISPLAY_TYPE_ID";
  public static final String FIELD_DISPLAY_TYPE_NAME = "FIELD_DISPLAY_TYPE_NAME";
  public static final String FIELD_TYPE_NAME = "FIELD_TYPE_NAME";
  public static final String FIELD_TYPE_OBJECT_ID = "FIELD_TYPE_OBJECT_ID";  
  public static final String FIELD_RF_OBJECT = "FIELD_RF_OBJECT";
  public static final String ITEM_POSITION = "ITEM_POSITION";
  
  public FieldsModel()
  {
  }
  
  public FieldsModel(ResultSet res)
  {
      try
      {
        dataviewId = res.getString(DATA_VIEW_ID);
        dataviewIssue = res.getString(DATA_VIEW_ISSUE);
        dataviewVersion = res.getString(DATA_VIEW_VERSION);
        fieldId = res.getString(FIELD_ID);
        fieldName = res.getString(FIELD_NAME);
        fieldDescription = res.getString(FIELD_DESCRIPTION);
        fieldSQLCash = res.getString(FIELD_SQL_CASH);
        fieldDisplayTypeId = res.getString(FIELD_DISPLAY_TYPE_ID);
        fieldDisplayTypeName = res.getString(FIELD_DISPLAY_TYPE_NAME);
        fieldTypeName = res.getString(FIELD_TYPE_NAME);
        fieldTypeObjectId = res.getString(FIELD_TYPE_OBJECT_ID);
        fieldRFObjrct = res.getString(FIELD_RF_OBJECT);
        itemPosition = res.getString(ITEM_POSITION);
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
  public String getFieldId()
  {
    return fieldId;
  }

  public void setFieldId(String newFieldId)
  {
    fieldId = newFieldId;
  }
/////////////////////////////////////  
  public String getFieldName()
  {
    return fieldName;
  }

  public void setFieldName(String newFieldName)
  {
    fieldName = newFieldName;
  } 
  /////////////////////////////////////  
  public String getFieldDescription()
  {
    return fieldDescription;
  }

  public void setFieldDescription(String newFieldDescription)
  {
    fieldDescription = newFieldDescription;
  } 
  /////////////////////////////////////  
  public String getFieldSQLCash()
  {
    return fieldSQLCash;
  }

  public void setFieldSQLCash(String newFieldSQLCash)
  {
    fieldSQLCash = newFieldSQLCash;
  } 
  /////////////////////////////////////  
  public String getFieldDisplayTypeId()
  {
    return fieldDisplayTypeId;
  }

  public void setFieldDisplayTypeId(String newFieldDisplayTypeId)
  {
    fieldDisplayTypeId = newFieldDisplayTypeId;
  } 
  /////////////////////////////////////  
  public String getFieldDisplayTypeName()
  {
    return fieldDisplayTypeName;
  }

  public void setFieldDisplayTypeName(String newFieldDisplayTypeName)
  {
    fieldDisplayTypeName = newFieldDisplayTypeName;
  } 
  /////////////////////////////////////  
  public String getFieldTypeName()
  {
    return fieldTypeName;
  }

  public void setFieldTypeName(String newFieldTypeName)
  {
    fieldTypeName = newFieldTypeName;
  } 
  /////////////////////////////////////  
  public String getFieldTypeObjectId()
  {
    return fieldTypeObjectId;
  }

  public void setFieldTypeObjectId(String newFieldTypeObjectId)
  {
    fieldTypeObjectId = newFieldTypeObjectId;
  } 
  /////////////////////////////////////  
  public String getFieldRFObjrct()
  {
    return fieldRFObjrct;
  }

  public void setFieldRFObjrct(String newFieldRFObjrct)
  {
    fieldRFObjrct = newFieldRFObjrct;
  } 
  /////////////////////////////////////  
  public String getItemPosition()
  {
    return itemPosition;
  }

  public void setItemPosition(String newItemPosition)
  {
    itemPosition = newItemPosition;
  } 
}
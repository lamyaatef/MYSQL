package com.mobinil.sds.core.system.dcm.genericModel;
import java.io.Serializable;
import java.util.Vector;

public class GenericModel implements Serializable
{
  int columnCount = 0;
  
  String tableName="";
  String primary_key_name   = "";
  String field_1_name       = "";
  String field_2_name       = "";
  String field_3_name       = "";
  String field_4_name       = "";
  String field_5_name       = "";

  String primary_key_value  = "" ;
  String field_1_value      = "";
  String field_2_value      = "";
  String field_3_value      = "";
  String field_4_value      = "";
  String field_5_value      = "";

  Vector columnVector        = new Vector();

  public static final String FIELD_1_NAME = "field_1_name";
  public static final String FIELD_2_NAME = "field_2_name";
  public static final String FIELD_3_NAME = "field_3_name";
  public static final String FIELD_4_NAME = "field_4_name";
  public static final String FIELD_5_NAME = "field_5_name";

  public static final String FIELD_1_VALUE = "field_1_value";
  public static final String FIELD_2_VALUE = "field_2_value";
  public static final String FIELD_3_VALUE = "field_3_value";
  public static final String FIELD_4_VALUE = "field_4_value";
  public static final String FIELD_5_VALUE = "field_5_value";
  
  public void set_columnCount(int count)
  {
    columnCount = count;
  }
  public int get_columnCount()
  {
    return columnCount;
  }

  public void set_columnVector(Vector columns)
  {
    columnVector = columns;
  }
  public Vector get_columnVector()
  {
    return columnVector;
  }

  public void set_tableName(String tblName)
  {
    tableName = tblName;
  }
  public String get_tableName()
  {
    return tableName;
  }
  
  public void set_field_1_name(String fld_1_name)
  {
    field_1_name = fld_1_name;
  }
  public String get_field_1_name()
  {
    return field_1_name;
  }

  public void set_field_2_name(String fld_2_name)
  {
    field_2_name = fld_2_name;
  }
  public String get_field_2_name()
  {
    return field_2_name;
  }

  public void set_field_3_name(String fld_3_name)
  {
    field_3_name = fld_3_name;
  }
  public String get_field_3_name()
  {
    return field_3_name;
  }

  public void set_field_4_name(String fld_4_name)
  {
    field_4_name = fld_4_name;
  }
  public String get_field_4_name()
  {
    return field_4_name;
  }

  public void set_field_5_name(String fld_5_name)
  {
    field_5_name = fld_5_name;
  }
  public String get_field_5_name()
  {
    return field_5_name;
  }

  public void set_field_1_value(String fld_1_value)
  {
    field_1_value = fld_1_value;
  }
  public String get_field_1_value()
  {
    return field_1_value;
  }

  public void set_field_2_value(String fld_2_value)
  {
    field_2_value = fld_2_value;
  }
  public String get_field_2_value()
  {
    return field_2_value;
  }

  public void set_field_3_value(String fld_3_value)
  {
    field_3_value = fld_3_value;
  }
  public String get_field_3_value()
  {
    return field_3_value;
  }

  public void set_field_4_value(String fld_4_value)
  {
    field_4_value = fld_4_value;
  }
  public String get_field_4_value()
  {
    return field_4_value;
  }

  public void set_field_5_value(String fld_5_value)
  {
    field_5_value = fld_5_value;
  }
  public String get_field_5_value()
  {
    return field_5_value;
  }

  public void set_primary_key_value(String PK)
  {
          primary_key_value = PK;
  }
  public String get_primary_key_value()
  {
          return primary_key_value;
  }


  public void set_primary_key_name(String PK)
  {
          primary_key_name = PK;
  }
  public String get_primary_key_name()
  {
          return primary_key_name;
  }  
  public GenericModel()
  {
  }

  
}
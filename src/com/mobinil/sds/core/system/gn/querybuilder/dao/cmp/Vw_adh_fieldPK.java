package com.mobinil.sds.core.system.gn.querybuilder.dao.cmp;
import java.io.Serializable;

public class Vw_adh_fieldPK implements Serializable 
{
  public Long field_id;

  public Vw_adh_fieldPK()
  {
  }

  public Vw_adh_fieldPK(Long field_id)
  {
    this.field_id = field_id;
  }

/*
  public Vw_adh_fieldPK(Long data_view_id, Long data_view_issue, Long data_view_version, Long field_id, String field_name, Long field_display_type_id, String field_display_type_name, Long field_type_id)
  {
    this.data_view_id = data_view_id;
    this.data_view_issue = data_view_issue;
    this.data_view_version = data_view_version;
    this.field_id = field_id;
    this.field_name = field_name;
    this.field_display_type_id = field_display_type_id;
    this.field_display_type_name = field_display_type_name;
    this.field_type_id = field_type_id;
  }

  public Vw_adh_fieldPK(Long data_view_id, Long data_view_issue, Long data_view_version, Long field_id, String field_name, Long field_display_type_id, String field_display_type_name)
  {
    this.data_view_id = data_view_id;
    this.data_view_issue = data_view_issue;
    this.data_view_version = data_view_version;
    this.field_id = field_id;
    this.field_name = field_name;
    this.field_display_type_id = field_display_type_id;
    this.field_display_type_name = field_display_type_name;
  }

  public Vw_adh_fieldPK(Long data_view_id, Long data_view_issue, Long data_view_version, Long field_id, String field_name, Long field_display_type_id)
  {
    this.data_view_id = data_view_id;
    this.data_view_issue = data_view_issue;
    this.data_view_version = data_view_version;
    this.field_id = field_id;
    this.field_name = field_name;
    this.field_display_type_id = field_display_type_id;
  }

  public Vw_adh_fieldPK(Long data_view_id, Long data_view_issue, Long data_view_version, Long field_id, String field_name)
  {
    this.data_view_id = data_view_id;
    this.data_view_issue = data_view_issue;
    this.data_view_version = data_view_version;
    this.field_id = field_id;
    this.field_name = field_name;
  }
*/

  public boolean equals(Object other)
  {
    if (other instanceof Vw_adh_fieldPK)
    {
      final Vw_adh_fieldPK otherVw_adh_fieldPK = (Vw_adh_fieldPK)other;

      // The following assignment statement is auto-maintained and may be overwritten!
      boolean areEqual = (otherVw_adh_fieldPK.field_id.equals(field_id));

      return areEqual;
    }

    return false;
  }

  public int hashCode()
  {
    // Add custom hashCode() impl here
    return super.hashCode();
  }
}
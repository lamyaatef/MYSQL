package com.mobinil.sds.core.system.gn.querybuilder.dao.cmp;
import java.io.Serializable;

public class Vw_adh_group_byPK implements Serializable 
{
  public Long group_place;
  public String group_field_name;
  public Long group_field_id;
  public Long data_view_id;
  public Long group_by_id;

  public Vw_adh_group_byPK()
  {
  }
/*
  public Vw_adh_group_byPK(Long group_by_id, Long data_view_id, Long group_field_id, String group_field_name, Long group_place)
  {
    this.group_by_id = group_by_id;
    this.data_view_id = data_view_id;
    this.group_field_id = group_field_id;
    this.group_field_name = group_field_name;
    this.group_place = group_place;
  }*/

  public Vw_adh_group_byPK(Long group_by_id)
  {
    this.group_by_id = group_by_id;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Vw_adh_group_byPK)
    {
      final Vw_adh_group_byPK otherVw_adh_group_byPK = (Vw_adh_group_byPK)other;

      // The following assignment statement is auto-maintained and may be overwritten!
      boolean areEqual = (otherVw_adh_group_byPK.group_by_id.equals(group_by_id) && otherVw_adh_group_byPK.data_view_id.equals(data_view_id) && otherVw_adh_group_byPK.group_field_id.equals(group_field_id) && otherVw_adh_group_byPK.group_field_name.equals(group_field_name) && otherVw_adh_group_byPK.group_place.equals(group_place));

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
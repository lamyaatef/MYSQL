package com.mobinil.sds.core.system.gn.querybuilder.dao.cmp;
import java.io.Serializable;

public class Vw_adh_havingPK implements Serializable 
{
  public Long having_id;

  public Vw_adh_havingPK()
  {
  }

  public Vw_adh_havingPK(Long having_id)
  {
    this.having_id = having_id;
  }

  /*public Vw_adh_havingPK(Long having_id, Long data_view_id, Long element_type_id, String element_type_name, Long element_id)
  {
    this.having_id = having_id;
    this.data_view_id = data_view_id;
    this.element_type_id = element_type_id;
    this.element_type_name = element_type_name;
    this.element_id = element_id;
  }

  public Vw_adh_havingPK(Long having_id, Long data_view_id, Long element_type_id, String element_type_name)
  {
    this.having_id = having_id;
    this.data_view_id = data_view_id;
    this.element_type_id = element_type_id;
    this.element_type_name = element_type_name;
  }*/

  public boolean equals(Object other)
  {
    if (other instanceof Vw_adh_havingPK)
    {
      final Vw_adh_havingPK otherVw_adh_havingPK = (Vw_adh_havingPK)other;

      // The following assignment statement is auto-maintained and may be overwritten!
      boolean areEqual = (otherVw_adh_havingPK.having_id.equals(having_id));

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
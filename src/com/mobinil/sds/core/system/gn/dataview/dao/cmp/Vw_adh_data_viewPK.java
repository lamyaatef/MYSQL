package com.mobinil.sds.core.system.gn.dataview.dao.cmp;
import java.io.Serializable;

public class Vw_adh_data_viewPK implements Serializable 
{
  public Long data_view_id;

  public Vw_adh_data_viewPK()
  {
  }

  public Vw_adh_data_viewPK(Long data_view_id)
  {
    this.data_view_id = data_view_id;
  }


  public boolean equals(Object other)
  {
    if (other instanceof Vw_adh_data_viewPK)
    {
      final Vw_adh_data_viewPK otherVw_adh_data_viewPK = (Vw_adh_data_viewPK)other;

      // The following assignment statement is auto-maintained and may be overwritten!
      boolean areEqual = (otherVw_adh_data_viewPK.data_view_id.equals(data_view_id));

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
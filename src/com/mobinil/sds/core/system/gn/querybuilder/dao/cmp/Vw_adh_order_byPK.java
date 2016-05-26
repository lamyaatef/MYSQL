package com.mobinil.sds.core.system.gn.querybuilder.dao.cmp;
import java.io.Serializable;

public class Vw_adh_order_byPK implements Serializable 
{
  public Long order_by_id;

  public Vw_adh_order_byPK()
  {
  }

  public Vw_adh_order_byPK(Long order_by_id)
  {
    this.order_by_id = order_by_id;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Vw_adh_order_byPK)
    {
      final Vw_adh_order_byPK otherVw_adh_order_byPK = (Vw_adh_order_byPK)other;

      // The following assignment statement is auto-maintained and may be overwritten!
      boolean areEqual = (otherVw_adh_order_byPK.order_by_id.equals(order_by_id));

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
package com.mobinil.sds.core.system.gn.querybuilder.dao.cmp;
import java.io.Serializable;

public class Vw_adh_universe_definitionPK implements Serializable 
{
  public Long data_view_id;
  public Long universe_id;

  public Vw_adh_universe_definitionPK()
  {
  }

  public Vw_adh_universe_definitionPK(Long universe_id, Long data_view_id)
  {
    this.universe_id = universe_id;
    this.data_view_id = data_view_id;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Vw_adh_universe_definitionPK)
    {
      final Vw_adh_universe_definitionPK otherVw_adh_universe_definitionPK = (Vw_adh_universe_definitionPK)other;

      // The following assignment statement is auto-maintained and may be overwritten!
      boolean areEqual = (otherVw_adh_universe_definitionPK.universe_id.equals(universe_id) && otherVw_adh_universe_definitionPK.data_view_id.equals(data_view_id));

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
package com.mobinil.sds.core.system.gn.querybuilder.dao.cmp;
import java.io.Serializable;

public class Vw_adh_parameter_valuePK implements Serializable 
{
  public Long parameter_value_id;

  public Vw_adh_parameter_valuePK()
  {
  }

  public Vw_adh_parameter_valuePK(Long parameter_value_id)
  {
    this.parameter_value_id = parameter_value_id;
  }

  public boolean equals(Object other)
  {
    if (other instanceof Vw_adh_parameter_valuePK)
    {
      final Vw_adh_parameter_valuePK otherVw_adh_parameter_valuePK = (Vw_adh_parameter_valuePK)other;

      // The following assignment statement is auto-maintained and may be overwritten!
      boolean areEqual = (otherVw_adh_parameter_valuePK.parameter_value_id.equals(parameter_value_id));

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
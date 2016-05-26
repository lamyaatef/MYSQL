package com.mobinil.sds.core.system.gn.querybuilder.dao.cmp;
import java.io.Serializable;

public class Vw_adh_input_paramPK implements Serializable 
{
  public Long input_param_id;

  public Vw_adh_input_paramPK()
  {
  }

  public Vw_adh_input_paramPK(Long input_param_id)
  {
    this.input_param_id = input_param_id;
  }
/*
  public Vw_adh_input_paramPK(Long input_param_id, Long field_id, String input_param_label_text, Long input_control_types_id, String input_control_types_value)
  {
    this.input_param_id = input_param_id;
    this.field_id = field_id;
    this.input_param_label_text = input_param_label_text;
    this.input_control_types_id = input_control_types_id;
    this.input_control_types_value = input_control_types_value;
  }

  public Vw_adh_input_paramPK(Long input_param_id, Long field_id, String input_param_label_text, Long input_control_types_id)
  {
    this.input_param_id = input_param_id;
    this.field_id = field_id;
    this.input_param_label_text = input_param_label_text;
    this.input_control_types_id = input_control_types_id;
  }

  public Vw_adh_input_paramPK(Long input_param_id, Long field_id, String input_param_label_text)
  {
    this.input_param_id = input_param_id;
    this.field_id = field_id;
    this.input_param_label_text = input_param_label_text;
  }
*/
  public boolean equals(Object other)
  {
    if (other instanceof Vw_adh_input_paramPK)
    {
      final Vw_adh_input_paramPK otherVw_adh_input_paramPK = (Vw_adh_input_paramPK)other;

      // The following assignment statement is auto-maintained and may be overwritten!
      boolean areEqual = (otherVw_adh_input_paramPK.input_param_id.equals(input_param_id));

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
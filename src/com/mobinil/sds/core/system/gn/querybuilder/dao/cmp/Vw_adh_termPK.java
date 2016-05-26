package com.mobinil.sds.core.system.gn.querybuilder.dao.cmp;
import java.io.Serializable;

public class Vw_adh_termPK implements Serializable 
{
    public Long term_id;

    public Vw_adh_termPK()
    {
    }

    public Vw_adh_termPK(Long term_id)
    {
        this.term_id = term_id;
    }

    public boolean equals(Object other)
    {
        if (other instanceof Vw_adh_termPK)
        {
            final Vw_adh_termPK otherVw_adh_termPK = (Vw_adh_termPK)other;

            // The following assignment statement is auto-maintained and may be overwritten!
            boolean areEqual = (otherVw_adh_termPK.term_id.equals(term_id));

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
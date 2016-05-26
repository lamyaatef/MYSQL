package com.mobinil.sds.core.system.gn.querybuilder.dao.cmp;
import java.io.Serializable;

public class Vw_adh_fromPK implements Serializable 
{
    public Long from_id;

    public Vw_adh_fromPK()
    {
    }

    public Vw_adh_fromPK(Long from_id)
    {
        this.from_id = from_id;
    }

    public boolean equals(Object other)
    {
        if (other instanceof Vw_adh_fromPK)
        {
            final Vw_adh_fromPK otherVw_adh_fromPK = (Vw_adh_fromPK)other;

            // The following assignment statement is auto-maintained and may be overwritten!
            boolean areEqual = (otherVw_adh_fromPK.from_id.equals(from_id));

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
package com.mobinil.sds.core.system.gn.querybuilder.dao.cmp;
import java.io.Serializable;

public class Vw_adh_wherePK implements Serializable 
{
    public Long where_id;

    public Vw_adh_wherePK()
    {
    }

    public Vw_adh_wherePK(Long where_id)
    {
        this.where_id = where_id;
    }

    public boolean equals(Object other)
    {
        if (other instanceof Vw_adh_wherePK)
        {
            final Vw_adh_wherePK otherVw_adh_wherePK = (Vw_adh_wherePK)other;

            // The following assignment statement is auto-maintained and may be overwritten!
            boolean areEqual = (otherVw_adh_wherePK.where_id.equals(where_id));

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
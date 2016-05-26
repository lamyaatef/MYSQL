package com.mobinil.sds.core.system.gn.querybuilder.dto;
import java.io.*;

public class ConditionElement implements Serializable
{
    private ConditionElementTypeDTO ConditionElementType;

    public ConditionElement()
    {
    }

    public ConditionElementTypeDTO getConditionElementType()
    {
        return ConditionElementType;
    }

    public void setConditionElementType(ConditionElementTypeDTO newConditionElementType)
    {
        ConditionElementType = newConditionElementType;
    }
}
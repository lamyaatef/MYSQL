/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.factor.validator;

import com.mobinil.mcss.dp.factor.constant.FactorConstant;
import com.mobinil.mcss.dp.factor.model.Factor;
import com.mobinil.mcss.dp.factor.model.FactorValue;
import com.mobinil.mcss.dp.factor.service.FactorService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author mabdelaal
 */
@Component("factorValidator")
public class FactorValidator implements Validator {

    @Autowired
    private FactorService factorService;

    @Override
    public boolean supports(Class<?> type) {
        return Factor.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Factor factor = (Factor) o;

//        name.setFactorName(name.getFactorName().toLowerCase());
        boolean isDubFactor = factorService.checkDublicateFactor(factor);
        if (!isDubFactor) {
            errors.rejectValue("nameId", "DublicateFactorName.constantFactor.nameId", "Dublicate factor.");
        }
        String factorTypeId = factor.getFactorName().getFactorTypeId();
        factorTypeId = factorTypeId == null ? String.valueOf(factor.getFactorName().getFactorType().getFactorTypeId()) : factorTypeId;
        if (factorTypeId.compareTo(FactorConstant.FACTOR_TYPE_DAY) == 0) {
            List<FactorValue> values = factor.getFactorValues();
            if (!checkEmptyValuesSize(values)) {
                errors.rejectValue("factorId", "EmptyFactorValue.dayFactor.factorId", "Day factor must have at least one value.");
            } else if (!checkBlankValues(values)) {
                errors.rejectValue("factorId", "EmptyFactorValueFields.dayFactor.factorId", "Please fill all field values.");
            } else if (!checkValuesIsNumber(values)) {
                errors.rejectValue("factorId", "FactorValueIsNumber.dayFactor.factorId", "Factor values must be a number.");
            } else if (!checkInvalidFromToDates(values)) {
                errors.rejectValue("factorId", "FactorDateFromAfterTo.dayFactor.factorId", "Factor From date must be before To date.");
            } 
            else if (!checkTimeOverlap(values)) {
                errors.rejectValue("factorId", "FactorDatesOverlapped.dayFactor.factorId", "Factor dates are overlapped , please review factor dates.");
            }
        }




    }

    private boolean checkEmptyValuesSize(List<FactorValue> values) {
        return values.size() > 0 ? true : false;
    }

    private boolean checkBlankValues(List<FactorValue> values) {
        for (FactorValue factorValue : values) {
            if (factorValue.getFactorRangeFrom() == null || factorValue.getFactorRangeTo() == null || factorValue.getFactorValue() == null) {
                return false;
            }
        }
        return true;
    }

    private boolean checkValuesIsNumber(List<FactorValue> values) {
        for (FactorValue factorValue : values) {
            try {
                Integer.parseInt(factorValue.getFactorValue());
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private boolean checkInvalidFromToDates(List<FactorValue> values) {
        for (FactorValue factorValue : values) {
            Timestamp from = factorValue.getFactorRangeFrom();
            Timestamp to = factorValue.getFactorRangeTo();
            if (from.after(to)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkTimeOverlap(List<FactorValue> values) {
        for (FactorValue factorValue : values) {
            Timestamp from = factorValue.getFactorRangeFrom();
            Timestamp to = factorValue.getFactorRangeTo();
            if (!checkOneRangeInAllRanges(values, factorValue)) {
                return false;
            }

        }

        return true;
    }

    private boolean checkOneRangeInAllRanges(List<FactorValue> values, FactorValue currentRange) {
        for (FactorValue factorValue : values) {
            Timestamp from = factorValue.getFactorRangeFrom();
            Timestamp to = factorValue.getFactorRangeTo();
            Timestamp fromLng = currentRange.getFactorRangeFrom();
            Timestamp toLng = currentRange.getFactorRangeTo();            
            if (!factorValue.equals(currentRange)) {
                if (fromLng.getTime() >= from.getTime() && fromLng.getTime() <= to.getTime()) {
                    return false;
                } else if (toLng.getTime() >= from.getTime() && toLng.getTime() <= to.getTime()) {
                    return false;
                }
            }
        }
        return true;

    }    
}

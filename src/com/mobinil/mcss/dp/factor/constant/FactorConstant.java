/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.factor.constant;

/**
 *
 * @author mabdelaal
 */
public interface FactorConstant {
    
    //---------------------Navigation Rules--------------------------
    public static final String NAV_VIEW_FACTORS="/viewfactor";    
    public static final String NAV_CON_ADD="/saveConFactor";    
    public static final String NAV_MON_ADD="/saveMonFactor";    
    public static final String NAV_DAY_ADD="/saveDayFactor";    
    public static final String NAV_CON_SAV="/insertCon";
    public static final String NAV_MON_SAV="/insertMon";
    public static final String NAV_DAY_SAV="/insertDay";
    public static final String NAV_CON_EDIT="/editConFactor";    
    public static final String NAV_MON_EDIT="/editMonFactor";    
    public static final String NAV_DAY_EDIT="/editDayFactor";    
    public static final String NAV_DEL="/del";
    
    //----------------------Return Views-----------------------------
    public static final String REDIRECT_MAIN_FACTORS=new StringBuilder(FactorConstant.FACTOR_REQUEST_MAPPING).append("/factorList").toString() ;;
    public static final String REDIRECT_VIEW_FACTORS=new StringBuilder(FactorConstant.FACTOR_REQUEST_MAPPING).append("/viewfactor").toString() ;;
    public static final String REDIRECT_ADD_CON_FACTORS=new StringBuilder(FactorConstant.FACTOR_REQUEST_MAPPING).append(FactorConstant.NAV_CON_ADD).toString() ;    
    public static final String REDIRECT_ADD_MON_FACTORS=new StringBuilder(FactorConstant.FACTOR_REQUEST_MAPPING).append(FactorConstant.NAV_MON_ADD).toString() ;    
    public static final String REDIRECT_ADD_DAY_FACTORS=new StringBuilder(FactorConstant.FACTOR_REQUEST_MAPPING).append(FactorConstant.NAV_DAY_ADD).toString() ;    
    public static final String REDIRECT_EDIT_CON_FACTORS=new StringBuilder(FactorConstant.FACTOR_REQUEST_MAPPING).append(FactorConstant.NAV_CON_EDIT).toString() ;    
    public static final String REDIRECT_EDIT_MON_FACTORS=new StringBuilder(FactorConstant.FACTOR_REQUEST_MAPPING).append(FactorConstant.NAV_MON_EDIT).toString() ;    
    public static final String REDIRECT_EDIT_DAY_FACTORS=new StringBuilder(FactorConstant.FACTOR_REQUEST_MAPPING).append(FactorConstant.NAV_DAY_EDIT).toString() ;    
    
    
    //----------------------Arrays----------------------------------
    public static final String LIST_FACTORS_CONSTANT="factor_list_con";
    public static final String LIST_FACTORS_DAY="factor_list_day";
    public static final String LIST_FACTORS_DAY_MANY_VALS="factor_list_day_many_vals";
    public static final String LIST_FACTORS_MONTH="factor_list_month";
    
    //----------------------Types----------------------------------
    public static final String FACTOR_TYPE_CONSTANT="1";
    public static final String FACTOR_TYPE_DAY="2";
    public static final String FACTOR_TYPE_DAY_MANY_VALS="2m";
    public static final String FACTOR_TYPE_MONTH="3";
    
    
    public static final String FACTOR_REQUEST_MAPPING="/dp/factor";
    public static final String FACTOR_MODEL_ATTRIBUTE="factor";
    public static final String FACTOR_HIDDEN_ID="factorId";
    public static final String FACTOR_DRIVING_PLAN_="dp_name";
    public static final String FACTOR_DRIVING_PLAN_ID="dp_name_id";
    
    
}

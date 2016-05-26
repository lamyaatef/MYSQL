package com.mobinil.sds.web.interfaces.cc;
/**
 * CommissionInterfaceKey interface holding all the keys used in the commission
 * module (actions and controls).
 * For example:
 * <pre>
 *       request.getAttribute(CommissionInterfaceKey.ACTION_UPDATE_KPI_TEMPLATE);
 * </pre>
 *
 * @version	1.01 March 2004
 * @author  Ahmed Mohamed Abd Elmonem
 * @see     
 *
 * SDS
 * MobiNil
 */ 

public class CommissionInterfaceKey 
{
////////////////////////////////////////////////////////////////////////
/*                      Returned Data HashMap Keys 
*////////////////////////////////////////////////////////////////////////

// Key for the list all KPI templates action
  public static final String ACTION_LIST_KPI_TEMPLATES = "action_list_kpi_templates";

// Key for the update KPI template action
  public static final String ACTION_UPDATE_KPI_TEMPLATE = "action_update_kpi_template";

// Key for creating new KPI template action
  public static final String ACTION_NEW_KPI_TEMPLATE = "action_new_kpi_template";
  
// Key for adding KPI template action
  public static final String ACTION_ADD_KPI_TEMPLATE = "action_add_kpi_template";

// Key for the edit KPI template action
  public static final String ACTION_EDIT_KPI_TEMPLATE = "action_edit_kpi_template";

// Key for the check KPI name action
  public static final String ACTION_CHECK_KPI_NAME = "action_check_kpi_name";

/////////////////////////////////////////CONTROLS///////////////////////////////
// Key for the select control name prefix
  public static final String CONTROL_BUTTON_NAME_EDIT_KPI_TEMPLATE_PREFIX = "edit_kpi_template";

// Key for the select control name prefix
  public static final String CONTROL_HIDDEN_NAME_KPI_TEMPLATE_ID = "kpi_template_id";

// Key for the KPI template name
  public static final String CONTROL_TEXT_NAME_KPI_TEMPLATE_NAME = "kpi_template_name";

// Key for the KPI template target
  public static final String CONTROL_TEXT_NAME_KPI_TEMPLATE_TARGET = "kpi_template_target";
  
// Key for the KPI template threshold
  public static final String CONTROL_TEXT_NAME_KPI_TEMPLATE_THRESHOLD = "kpi_template_threshold";

// Key for the KPI template description
  public static final String CONTROL_TEXT_NAME_KPI_TEMPLATE_DESCRIPTION = "kpi_template_description";

// Key for the KPI template use target radio 
  public static final String CONTROL_RADIOBOX_NAME_KPI_TEMPLATE_USE_TARGET = "kpi_template_use_target";

// Key for the KPI template use target radio option
  public static final String CONTROL_RADIOBOX_NAME_KPI_TEMPLATE_USE_TARGET_OPTION = "kpi_template_use_target_option";  

// Key for the KPI template relate to higher radio option
  public static final String CONTROL_RADIOBOX_NAME_KPI_TEMPLATE_RELATE_TO_HIGHER_OPTION = "kpi_template_relate_to_higher_option";  
  
// Key for the KPI template description
  public static final String CONTROL_SELECT_NAME_KPI_TEMPLATE_DATA_VIEW_ID = "kpi_template_dataview_id";

  public static final String CONTROL_CHECKBOX_NAME_OVERIDE_EXISTING_RECORD = "overide_existing_record";

  public static final String CONTROL_ARRAY_NAME_OVERIDED_RECORDS = "overided_records";

  public static final String CONTROL_ARRAY_NAME_ALL_EXISTING_RECORDS = "all_existing_records";

  public static final String CONTROL_ARRAY_NAME_ALL_ACTIVATED_RECORDS = "all_activated_records";

// -------------------------- Commission --------------------------------------
// Key for the list all commissions action
  public static final String ACTION_LIST_COMMISSIONS = "action_list_commissions";

// Key for creating new commission action
  public static final String ACTION_NEW_COMMISSION = "action_new_commission";
  
// Key for the edit commission action
  public static final String ACTION_EDIT_COMMISSION = "action_edit_commission";

// Key for adding commission action
  public static final String ACTION_ADD_COMMISSION = "action_add_commission";

// Key for the update commission action
  public static final String ACTION_UPDATE_COMMISSION = "action_update_commission";

// Key for the update commission action
  public static final String ACTION_LIST_COMMISSION_KPIS_FOR_CALCULATIONS = "action_list_commission_kpis_for_calculations";
  
// Key for showing commission history page
  public static final String ACTION_SHOW_COMMISSION_HISTORY_SEARCH_SCREEN = "show_commission_history_search_screen";

// Key for showing commission history search reasults
  public static final String ACTION_SEARCH_COMMISSION_HISTORY = "search_commission_history";

// -------------------------- Commission --------------------------------------
// Key for the commission ID
  public static final String CONTROL_HIDDEN_NAME_COMMISSION_ID = "commission_id";
  
// Key for the reset budget option
  public static final String CONTROL_HIDDEN_NAME_RESET_BUDGET = "reset_budget";

// Key for the select control name prefix
  public static final String CONTROL_BUTTON_NAME_EDIT_COMMISSION_PREFIX = "edit_commission";

// Key for the select control name prefix
  public static final String CONTROL_BUTTON_NAME_CLOSE_COMMISSION_PREFIX = "close_commission";

// Key for the select control name prefix
  public static final String CONTROL_BUTTON_NAME_CALCULATE_COMMISSION_PREFIX = "calculate_commission";

// Key for the commission name
  public static final String CONTROL_TEXT_NAME_COMMISSION_NAME = "commission_name";

// Key for the commission description
  public static final String CONTROL_TEXT_NAME_COMMISSION_DESCRIPTION = "commission_description";

// Key for the commission date
  public static final String CONTROL_TEXT_NAME_COMMISSION_DATE = "commission_date";

// Key for the commission applied from date
  public static final String CONTROL_TEXT_NAME_COMMISSION_APPLIED_FROM_DATE = "commission_applied_from_date";

// Key for the commission applied to date
  public static final String CONTROL_TEXT_NAME_COMMISSION_APPLIED_TO_DATE = "commission_applied_to_date";

// Key for the commission Dataview IDs
  public static final String CONTROL_SELECT_NAME_COMMISSION_ELIGIBLE_DATAVIEW  = "control_select_name_commission_eligible_dataview";

// Key for the commission DCM level
  public static final String CONTROL_SELECT_NAME_COMMISSION_DCMLEVEL  = "control_select_name_commission_dcmlevel";

  // Key for the KPI template id 
  public static final String CONTROL_SELECT_NAME_COMMISSION_TEMPLATE_ID = "commission_template_id";

///////////////////////////Commission KPIS /////////////////////////////////////////

// Key for the list commission kpis action
  public static final String ACTION_LIST_COMMISSION_KPIS = "action_list_commission_kpis";  

// Key for the update KPI template action
//  public static final String ACTION_UPDATE_COMMISSION_KPI = "action_update_commission_kpi";

// Key for creating new KPI template action
//  public static final String ACTION_NEW_COMMISSION_KPI = "action_new_commission_kpi";
  
// Key for adding KPI template action
 public static final String ACTION_SAVE_COMMISSION_KPIS = "action_save_commission_kpis";

// Key for the edit KPI template action
//  public static final String ACTION_EDIT_COMMISSION_KPI = "action_edit_commission_kpi";


/////////////////////////////////// COMMISSION KPIS CONTROLS ////////////////////////////////////////
// Key for the edit kpi prefix
//  public static final String CONTROL_BUTTON_NAME_EDIT_KPI_PREFIX = "edit_commission_kpi";

// Key for the kpi ID
  public static final String CONTROL_HIDDEN_NAME_KPI_COUNT = "commission_kpi_count";

// Key for the kpi ID
  public static final String CONTROL_HIDDEN_NAME_COMMISSION_KPI_ID_PREFIX = "commission_kpi_id";

// Key for the KPI weight
  public static final String CONTROL_TEXT_NAME_COMMISSION_KPI_WEIGHT_PREFIX = "commission_kpi_weight";

// Key for the KPI target
//  public static final String CONTROL_TEXT_NAME_COMMISSION_KPI_TARGET = "commission_kpi_target";
  
// Key for the KPI threshold
//  public static final String CONTROL_TEXT_NAME_COMMISSION_KPI_THRESHOLD = "commission_kpi_threshold";

// Key for the KPI description
//  public static final String CONTROL_TEXT_NAME_COMMISSION_KPI_DESCRIPTION = "commission_kpi_description";

// Key for the KPI use target
//  public static final String CONTROL_CHECKBOX_NAME_COMMISSION_KPI_USE_TARGET = "commission_kpi_use_target";
  
// Key for the KPI dataview ID
//  public static final String CONTROL_SELECT_NAME_COMMISSION_KPI_TEMPLATE_ID_PREFIX = "commission_kpi_template_id";


///////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////// PENALTIES EQUATIONS /////////////////////////////////////////////////////////
// Key for the list all budgets action
  public static final String ACTION_LIST_PENALTIES_EQUATIONS = "action_list_penalties_equations";
  
// Key for the list all budgets action
  public static final String ACTION_SAVE_PENALTIES_EQUATIONS = "action_save_penalties_equations";

////////////////////////////////////////// PENALTY EQ. CONTROLS ////////////////////////////////////
// Key for the kpi ID
  public static final String CONTROL_HIDDEN_NAME_PENALTY_EQ_TYPE = "penalty_eq_type";

// Key for the kpi ID
  public static final String CONTROL_HIDDEN_NAME_PENALTY_EQ_FOR = "penalty_eq_for";
  
// Key for the kpi ID
  public static final String CONTROL_HIDDEN_NAME_SELECTED_COMMISSION_KPI_ID = "selected_commission_kpi_id";  

// Key for the kpi ID
  public static final String CONTROL_HIDDEN_NAME_EQUATION_ELEMENTS_COUNT = "equation_elements_count";

  // Key for the kpi ID
  public static final String CONTROL_TEXT_NAME_PENALTY_EQUATION_DESCRIPTION = "penalty_equation_description";
  

// Key for the kpi ID
  public static final String CONTROL_HIDDEN_NAME_PENALTY_EQ_ELEMENT_TYPE_ID_PREFIX = "penalty_equation_element_type_id";

// Key for the kpi ID
  public static final String CONTROL_HIDDEN_NAME_PENALTY_EQ_ELEMENT_VALUE_PREFIX = "penalty_equation_element_value";
  
///////////////////////////////////////// BUDGETS /////////////////////////////////////////////////////////
// Key for the list all budgets action
  public static final String ACTION_LIST_BUDGETS = "action_list_budgets";

// Key for the update budget action
  public static final String ACTION_UPDATE_BUDGET = "action_update_budget";

// Key for creating new budget action
  public static final String ACTION_NEW_BUDGET = "action_new_budget";
  
// Key for adding budget action
  public static final String ACTION_ADD_BUDGET = "action_add_budget";

// Key for the edit budget action
  public static final String ACTION_EDIT_BUDGET = "action_edit_budget";

/////////////////////////////////// BUDGET CONTROLS ////////////////////////////////////////
// Key for the edit budget prefix
  public static final String CONTROL_BUTTON_NAME_EDIT_BUDGET_PREFIX = "edit_budget";

// Key for the budget ID
  public static final String CONTROL_HIDDEN_NAME_BUDGET_ID = "budget_id";

// Key for the budget ID
  public static final String CONTROL_HIDDEN_NAME_BUDGET_PRODUCTS_COUNT = "budget_products_count";

// Key for the budget name
  public static final String CONTROL_TEXT_NAME_BUDGET_NAME = "budget_name";

// Key for the budget description
  public static final String CONTROL_TEXT_NAME_BUDGET_DESCRIPTION = "budget_description";

// Key for the KPI template use target radio option
  public static final String CONTROL_RADIOBOX_NAME_USE_AS_BUDGET_OPTION = "use_as_budget_option";  

// Key for the KPI template relate to higher radio option
  public static final String CONTROL_RADIOBOX_NAME_USE_AS_AMOUNT_OPTION = "use_as_amount_option";  

// Key for the product amount prefix
  public static final String CONTROL_RADIOBOX_NAME_USE_AS_BUDGET_PREFIX = "use_as_budget";

// Key for the product budget prefix
  public static final String CONTROL_TEXT_NAME_BUDGET_PRODUCT_AMOUNT_PREFIX = "budget_product_amount";
  
// Key for the product budget prefix
  public static final String CONTROL_TEXT_NAME_BUDGET_PRODUCT_REASONS_PREFIX = "budget_product_reasons";  
  
// Key for the product budget prefix
  public static final String CONTROL_HIDDEN_NAME_BUDGET_PRODUCT_NAME_PREFIX = "budget_product_name";
  
///////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////// PENALTIES PARAMETERS /////////////////////////////////////////////////////////
// Key for the list all budgets action
  public static final String ACTION_PENALTIES_PARAMETERS = "action_penalties_parameters";

// Key for the update budget action
  public static final String ACTION_SAVE_PENALTIES = "action_save_penalties";

/////////////////////////////////// PENALTIES PARAMETERS CONTROLS ////////////////////////////////////////
// Key for the kpi ID
  public static final String CONTROL_HIDDEN_NAME_DCM_ID_PREFIX = "dcm_id";
  
// Key for the kpi ID
  public static final String CONTROL_HIDDEN_NAME_DCM_COUNT = "dcm_count";
// Key for the KPI name
  public static final String CONTROL_TEXT_NAME_PENALTY_DAYS_NO_PREFIX = "penalty_days_no";

// Key for the KPI target
  public static final String CONTROL_TEXT_NAME_BURNED_LINES_NO_PREFIX = "burned_lines_no";

// Key for the KPI target
  public static final String CONTROL_TEXT_NAME_PENALTY_REASONS_PREFIX = "penalty_reasons";

/////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static final String COMMISSION_DATE_FORMATTING = "dd/MM/yyyy";

  public static final int COMMISSION_STATUS_NEW = 1;
  public static final int COMMISSION_STATUS_CLOSED = 2;
  
  public static final String COMMISSION_STATUS_IS_NEW = "New Commission";
  public static final String COMMISSION_STATUS_IS_CLOSED = "Commission Closed";
  
  //public static final String PARAM_COMMISSION_DATE_FORMATTING = "yyyy-mm-dd";
  //public static final String PARAM_COMMISSION_DATE_FORMATTING = "yyyy-mm-dd hh:mm:ss.fffffffff";

  public static final int    GEN_DCM_LEVEL_DISTRIBUTER = 1 ;

  public static final String    PENALTY_EQUATION_TYPE_TOTAL = "TOTAL" ;
  public static final String    PENALTY_EQUATION_TYPE_PERCENTAGE = "PERCENTAGE" ;
  
  public static final String  PENALTY_EQUATION_FOR_KPI = "KPI" ;
  public static final String  PENALTY_EQUATION_FOR_COMMISSION = "COMMISSION";
  
  public static final String    PENALTY_EQUATION_OBJECT_TYPE_TERM = "TERM" ;
  public static final String    PENALTY_EQUATION_OBJECT_TYPE_OPERATOR  = "OPERATOR" ;
  public static final String    PENALTY_EQUATION_OBJECT_TYPE_FACTOR  = "FACTOR" ;



  // ---------------------------------------------------------------------
  // ---------------------------------------------------------------------
  // ---------------------------------------------------------------------
  // actions added by fady
  /*
  1) KPI ID --> ACTION_KPI_GET_KPI_DATA --> Parameters, [parameters values, and previous calculations if exist]

  2) parameters values --> ACTION_KPI_CALCULATE --> Vector of DCMKPICalculate

  3) parameters values, adjustments --> ACTION_KPI_RECALCULATE --> Vector of DCMKPICalculate

  4) parameters values, adjustments --> ACTION_KPI_SAVE --> saving vector of DCMKPICalculate in a way that 
                                                                can be retrieved in ACTION_KPI_GET_KPI_DATA
  */
  // -----------------------
  public static final String ACTION_KPI_GET_KPI_DATA              = "action_kpi_get_kpi_data";
  public static final String PARAM_KPI_GET_KPI_DATA_KPI_ID        = "param_kpi_get_kpi_data_kpi_id";
  public static final String PARAM_KPI_GET_KPI_DATA_COMMISSION_ID = "param_kpi_get_kpi_data_commission_id";

  // -----------------------
  public static final String ACTION_KPI_CALCULATE                 = "action_kpi_calculate";
  public static final String PARAM_KPI_CALCULATE_KPI_ID           = "param_kpi_calculate_kpi_id";
  public static final String PARAM_KPI_CALCULATE_COMMISSION_ID    = "param_kpi_calculate_commission_id";
  public static final String PARAM_KPI_CALCULATE_COMMISSION_KPI_ID= "param_kpi_calculate_commission_kpi_id";

  public static final String PARAM_KPI_CALCULATE_PARAMS_NUM       = "param_kpi_calculate_params_num";
  public static final String PARAM_KPI_CALCULATE_PARAM_ID         = "param_kpi_calculate_param_id";
  public static final String PARAM_KPI_CALCULATE_PARAM_NAME       = "param_kpi_calculate_param_name";
  public static final String PARAM_KPI_CALCULATE_PARAM_VALUE      = "param_kpi_calculate_param_value";
  public static final String PARAM_KPI_CALCULATE_PARAM_TYPE       = "param_kpi_calculate_param_type";
  public static final int    PARAM_KPI_CALCULATE_PARAM_TYPE_NUMERIC = 1;
  public static final int    PARAM_KPI_CALCULATE_PARAM_TYPE_TEXT    = 2;
  public static final int    PARAM_KPI_CALCULATE_PARAM_TYPE_DATE    = 3;
  public static final String PARAM_KPI_CALCULATE_PARAM_TYPE_DATE_FORMATTING = "dd/mm/yyyy";

  public static final String PARAM_KPI_CALCULATE_FIELD_EXCLUDED   = "DCM_ID";
  //public static final String PARAM_KPI_CALCULATE_TARGET           = "param_kpi_calculate_target";

  public static final String PARAM_KPI_CALCULATE_ADJUSTS_NUM      = "param_kpi_calculate_adjusts_num";
  public static final String PARAM_KPI_CALCULATE_ADJUST_FIGURE    = "param_kpi_calculate_adjust_figure";
  public static final String PARAM_KPI_CALCULATE_ADJUST_REASON    = "param_kpi_calculate_adjust_reason";
  public static final String PARAM_KPI_CALCULATE_ADJUST_PERCENT   = "param_kpi_calculate_adjust_percent";

  // -----------------------
  public static final String ACTION_KPI_SAVE                      = "action_kpi_save";
  // ... all keys of ACTION_KPI_CALCULATE should be used here ...





  // -----------------------
  public static final String ACTION_LIST_COMMISSIONS_FOR_CALCULATIONS     = "action_list_commissions_for_calculations";

  // -----------------------
  public static final String ACTION_COMMISSION_CHECK_KPIS_ARE_CALCULATED     = "action_commission_check_kpis_are_calculated";
  public static final String PARAM_COMMISSION_CHECK_KPIS_ARE_CALCULATED_COMMISSION_ID    = "param_commission_check_kpis_are_calculated_commission_id";
  
  public static final String COMMISSION_DRAFT_TYPE     = "Draft";
  public static final String COMMISSION_PAYMENT_TYPE   = "Payment";
  
  // -----------------------
  public static final String ACTION_COMMISSION_GET_COMMISSION_DATA           = "action_commission_get_commission_data";
  public static final String PARAM_COMMISSION_GET_COMMISSION_DATA_COMMISSION_ID = "param_commission_get_commission_data_commission_id";


  // -----------------------
  public static final String ACTION_COMMISSION_CALCULATE_HISTORY = "action_commission_calculate_history";
  public static final String ACTION_COMMISSION_CALCULATE                 = "action_commission_calculate";
  public static final String PARAM_COMMISSION_CALCULATE_COMMISSION_ID    = "param_commission_calculate_commission_id";

  public static final String PARAM_COMMISSION_CALCULATE_ADJUSTS_NUM      = "param_commission_calculate_adjusts_num";
  public static final String PARAM_COMMISSION_CALCULATE_ADJUST_FIGURE    = "param_commission_calculate_adjust_figure";
  public static final String PARAM_COMMISSION_CALCULATE_ADJUST_REASON    = "param_commission_calculate_adjust_reason";

  // -----------------------
  public static final String ACTION_COMMISSION_SAVE                      = "action_commission_save";
  // ... all keys of ACTION_COMMISSION_CALCULATE should be used here ...

  // -----------------------
  public static final String ACTION_BUDGET_CALCULATE                  = "action_budget_calculate";
  public static final String PARAM_BUDGET_CALCULATE_COMMISSION_ID         = "param_budget_calculate_commission_id";
  public static final String PARAM_BUDGET_CALCULATE_PARAMS_NUM            = "param_budget_calculate_params_num";
  public static final String PARAM_BUDGET_CALCULATE_PARAM_ID              = "param_budget_calculate_param_id";
  public static final String PARAM_BUDGET_CALCULATE_PARAM_NAME           = "param_budget_calculate_param_name";
  public static final String PARAM_BUDGET_CALCULATE_PARAM_VALUE           = "param_budget_calculate_param_value";
  public static final String PARAM_BUDGET_CALCULATE_PARAM_TYPE            = "param_budget_calculate_param_type";
  public static final int    PARAM_BUDGET_CALCULATE_PARAM_TYPE_NUMERIC = 1;
  public static final int    PARAM_BUDGET_CALCULATE_PARAM_TYPE_TEXT    = 2;
  public static final int    PARAM_BUDGET_CALCULATE_PARAM_TYPE_DATE    = 3;
  public static final String PARAM_BUDGET_CALCULATE_PARAM_TYPE_DATE_FORMATTING = "dd/mm/yyyy";

  // -----------------------
  public static final String ACTION_BUDGET_SAVE                  = "action_budget_save";
  //  ... all keys of ACTION_BUDGET_CALCULATE parameters should be used

  public static final String PARAM_BUDGET_TYPE_CORE_BUDGET        = "Core Budget";
  public static final String PARAM_BUDGET_TYPE_BUDGET             = "Budget";
  public static final String PARAM_BUDGET_TYPE_DISTRIBUTION_BUDGET= "Distribution Budget";


  // -----------------------
  public static final String ACTION_BUDGET_GET_BUDGET_DATA      = "action_budget_get_budget_data";
  public static final String PARAM_BUDGET_GET_BUDGET_DATA_COMMISSION_ID = "param_budget_get_budget_data_commission_id";
  public static final String PARAM_BUDGET_GET_BUDGET_DATA_DATE_FORMATTING = "dd/MM/yyyy";

  // -----------------------
  public static final String ACTION_BUDGET_SHOW_PAYMENT_COMMISSION_HISTORY = "action_budget_show_payment_commission_history";
  public static final String ACTION_BUDGET_EXPORT_PAYMENT_COMMISSION_HISTORY = "action_budget_export_payment_commission_history";
  public static final String PARAM_BUDGET_SHOW_PAYMENT_COMMISSION_HISTORY_COMMISSION_ID = "param_budget_show_payment_commission_history_commission_id";

  // -----------------------
  public static final String ACTION_BUDGET_CHECK_TOTALS_ARE_CALCULATED     = "action_budget_check_totals_are_calculated";
  public static final String PARAM_BUDGET_CHECK_TOTALS_ARE_CALCULATED_COMMISSION_ID    = "param_budget_check_totals_are_calculated_commission_id";

  // -----------------------
  public static final String ACTION_COMMISSION_CLOSE_COMMISSION                 = "action_commission_close_commission";
  public static final String PARAM_COMMISSION_CLOSE_COMMISSION_COMMISSION_ID    = "param_commission_close_commission_commission_id";
  
  public static final String MSG_COMMISSION_CLOSE_COMMISSION_VALID              = "valid commission closure";
  public static final String MSG_COMMISSION_CLOSE_COMMISSION_INVALID_PAYMENT    = "invalid payment commission closure";
  public static final String MSG_COMMISSION_CLOSE_COMMISSION_INVALID_DRAFT      = "invalid draft commission closure";


  // -----------------------
  public static final boolean   DEBUG_FLAG    = true ;


  // Actions add by victor 19/9/2004

  public static final String ACTION_IMPORT_DISTRIBUTORS_FILE= "import_distributors_file";

  
 
}



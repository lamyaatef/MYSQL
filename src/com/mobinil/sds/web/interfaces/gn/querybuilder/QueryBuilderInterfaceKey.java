package com.mobinil.sds.web.interfaces.gn.querybuilder;

public interface QueryBuilderInterfaceKey 
{

  //---------------------------------------------------------------------------
  public static final String ACTION_INITIALIZE_GENERAL_WIZARD   = "action_initialize_general_wizard";
  public static final String ACTION_INITIALIZE_KPI_WIZARD       = "action_initialize_kpi_wizard";
  public static final String ACTION_INITIALIZE_ELIGIBLE_WIZARD  = "action_initialize_eligible_wizard";
  public static final String ACTION_INITIALIZE_DCM_QUOTA_CALCULATION_WIZARD  = "action_initialize_dcm_quota_calculation_wizard";
  //public static final String PARAM_INITIALIZE_WIZARD_UNIVERSE = "param_initialize_wizard_universe";

  //---------------------------------------------------------------------------
  public static final String ACTION_INITIALIZE_QUERY_BUILDER = "action_initialize_query_builder";
  public static final String PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS = "initializeQB_selected_dataviews";
  public static final String PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME = "initializeQB_query_name";
  public static final String PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC = "initializeQB_query_desc";
  public static final String PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS_NUM = "initializeQB_selected_dataviews_num";

  // types must be hardcoded according to the business name 
  //public static final String FIELD_TYPE_DATAVIEW_FIELD      = "DB FIELD";
  //public static final String FIELD_TYPE_CONST_FIELD         = "CONSTANT";
  //public static final String FIELD_TYPE_FUNCTION_FIELD      = "FUNCTION";

  // temporary 
  public static final int    FIELD_TYPE_DATAVIEW_FIELD      = 1;
  public static final int    FIELD_TYPE_CONST_FIELD         = 2;
  public static final int    FIELD_TYPE_FUNCTION_FIELD      = 3;
  public static final int    FIELD_TYPE_FUNCTION_RF_FIELD   = 4; //function shadow field
  public static final int    FIELD_TYPE_PARAMETER_FIELD     = 5;
  public static final int    FIELD_TYPE_INPUT_PARAMETER_FIELD  = 6;
  public static final int    FIELD_TYPE_ALL_FIELDS          = 7;

  //---------------------------------------------------------------------------
  public static final String ACTION_CONSTRUCT_QUERY_SQL = "action_construct_query_sql";
  public static final String PARAM_CONSTRUCT_QUERY_SQL_QUERYDTO = "constructQSQL_querydto";
  public static final int    DISPLAY_TYPE_DISPLAYED       = 1;
  public static final int    DISPLAY_TYPE_NOT_DISPLAYED   = 2;
  public static final int    DISPLAY_TYPE_SHOWN           = 3;

  //---------------------------------------------------------------------------
  public static final int    FUNCTION_STATUS_ACTIVE       = 1;
  public static final int    FUNCTION_STATUS_INACTIVE     = 2;

  //---------------------------------------------------------------------------
  public static final int    FUNCTION_TYPE_AGGREGATE          = 1;
  public static final int    FUNCTION_TYPE_ARITHMATIC         = 2;
  public static final int    FUNCTION_TYPE_STORED_PROCEDURE   = 3;

  //---------------------------------------------------------------------------
  public static final String ACTION_PREVIEW_SAVE_QUERY = "action_preview_save_query";
  public static final String ACTION_PREVIEW_CHECK_QUERY = "action_preview_check_query";
  public static final String ACTION_SAVE_QUERY = "action_save_query";
  public static final String PARAM_SAVE_QUERY_QUERYDTO = "saveQ_querydto";

  //---------------------------------------------------------------------------
  public static final String ACTION_PREVIEW_QUERY = "action_preview_query";

  public static final String PARAM_PREVIEW_QUERY_QUERY_NAME             = "previewQ_query_name";
  public static final String PARAM_PREVIEW_QUERY_QUERY_DESC             = "previewQ_query_desc";

  public static final String PARAM_PREVIEW_QUERY_QUERY_SAVE             = "previewQ_query_save";
    public static final int    PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE     = 0;
    public static final int    PARAM_PREVIEW_QUERY_QUERY_SAVE_TRUE      = 1;
  public static final String PARAM_PREVIEW_QUERY_QUERY_ISSUE             = "previewQ_query_issue";

  public static final String PARAM_PREVIEW_QUERY_QUERY_UNIQUE           = "previewQ_query_unique";
    public static final int    PARAM_PREVIEW_QUERY_QUERY_UNIQUE_FALSE     = 0;
    public static final int    PARAM_PREVIEW_QUERY_QUERY_UNIQUE_TRUE      = 1;

  public static final String PARAM_PREVIEW_QUERY_QUERY_TYPE             = "param_preview_query_query_type";
    //public static final String PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE       = "Scope";
    //public static final String PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY       = "Data View";
    public static final int    PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE       = 1;
    public static final int    PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY       = 2;
    public static final int    PARAM_PREVIEW_QUERY_QUERY_TYPE_KPI         = 3;
    public static final int    PARAM_PREVIEW_QUERY_QUERY_TYPE_ELIGIBLE    = 4;
    //public static final int    PARAM_PREVIEW_QUERY_QUERY_TYPE_REPORT      = 5;

  public static final String PARAM_PREVIEW_QUERY_QUERY_UNIVERSE         = "param_preview_query_query_universe";
    public static final String UNIVERSE_TYPE_AD_HOC_REPORTS    = "Ad-hoc Reports";
    public static final String UNIVERSE_TYPE_KPI               = "KPI";
    public static final String UNIVERSE_TYPE_DCM_QUOTA_CALCULATION ="DCM Quota Calculation";
    public static final String UNIVERSE_TYPE_LINES_ELIGIBLE_FOR_COMMISSIONING    = "Lines Eligible for commissioning";
    
    public static final int UNIVERSE_ID_TYPE_AD_HOC_REPORTS    = 1;
    public static final int UNIVERSE_ID_TYPE_KPI               = 2;
    public static final int UNIVERSE_ID_TYPE_LINES_ELIGIBLE_FOR_COMMISSIONING    = 3;


  public static final String PARAM_PREVIEW_QUERY_QUERY_STATUS           = "param_preview_query_query_status";
    public static final int    PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE    = 1;
    public static final int    PARAM_PREVIEW_QUERY_QUERY_STATUS_INACTIVE  = 2;


  // --------------------- list of displayed fields ---------------------

  public static final String PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NUM   = "previewQ_selected_dataviews_num";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_IDS   = "previewQ_selected_dataviews_ids";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NAMES = "previewQ_selected_dataviews_names";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_TYPE_IDS = "previewQ_selected_dataviews_type_ids";
  
  public static final String PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NUM      = "previewQ_selected_fields_num";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS      = "previewQ_selected_fields_ids";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NAMES    = "previewQ_selected_fields_names";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_FIELDS_VALUES   = "previewQ_selected_fields_values";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES    = "previewQ_selected_fields_types";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DESCRIPS = "previewQ_selected_fields_descrips";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_FIELDS_ALIASES  = "previewQ_selected_fields_aliases";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_IDS = "previewQ_selected_fields_dataviews_ids";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_NAMES = "previewQ_selected_fields_dataviews_names";
  public static final String PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_TYPES = "previewQ_selected_fields_dataviews_types";
  
  // to be removed later
  //public static final String PARAM_PREVIEW_QUERY_SELECTED_FIELDS_UNIQUES  = "previewQ_selected_fields_uniques";
  //public static final int    PARAM_PREVIEW_QUERY_SELECTED_FIELDS_UNIQUE_FALSE = 0;
  //public static final int    PARAM_PREVIEW_QUERY_SELECTED_FIELDS_UNIQUE_TRUE = 1;

  // --------------------- list of constants ---------------------

  public static final String PARAM_PREVIEW_QUERY_CONST_NUM = "param_preview_query_const_num";
  public static final String PARAM_PREVIEW_QUERY_CONST_NAME = "param_preview_query_const_name";
  public static final String PARAM_PREVIEW_QUERY_CONST_TYPE = "param_preview_query_const_type";
  public static final String PARAM_PREVIEW_QUERY_CONST_VALUE = "param_preview_query_const_value";
  public static final String PARAM_PREVIEW_QUERY_CONST_DESC = "param_preview_query_const_desc";

  public static final int    PARAM_PREVIEW_QUERY_CONST_TYPE_NUMERIC = 1;
  public static final int    PARAM_PREVIEW_QUERY_CONST_TYPE_TEXT    = 2;
  public static final int    PARAM_PREVIEW_QUERY_CONST_TYPE_DATE    = 3;
  public static final String PARAM_PREVIEW_QUERY_CONST_TYPE_DATE_FORMATTING = "dd/mm/yyyy";
  
  // --------------------- list of parameters  ---------------------

  public static final String PARAM_PREVIEW_QUERY_PARAMS_NUM = "param_preview_query_params_num";
  public static final String PARAM_PREVIEW_QUERY_PARAM_NAME = "param_preview_query_param_name";
  public static final String PARAM_PREVIEW_QUERY_PARAM_VALUE = "param_preview_query_param_value";
  public static final String PARAM_PREVIEW_QUERY_PARAM_LABEL = "param_preview_query_param_label";
  public static final String PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID = "param_preview_query_param_control_type_id";
  public static final String PARAM_PREVIEW_QUERY_PARAM_DESC = "param_preview_query_param_desc";
  //public static final String PARAM_PREVIEW_QUERY_PARAM_FIELD_ID = "param_preview_query_param_field_id";
  //public static final String PARAM_PREVIEW_QUERY_PARAM_DATAVIEW_ID = "param_preview_query_param_dataview_id";


  public static final String PARAM_PREVIEW_QUERY_PARAM_TYPE = "param_preview_query_param_type";
  public static final int    PARAM_PREVIEW_QUERY_PARAM_TYPE_NUMERIC = 1;
  public static final int    PARAM_PREVIEW_QUERY_PARAM_TYPE_TEXT    = 2;
  public static final int    PARAM_PREVIEW_QUERY_PARAM_TYPE_DATE    = 3;
  public static final String PARAM_PREVIEW_QUERY_PARAM_TYPE_DATE_FORMATTING = "dd/mm/yyyy";

  public static final int    PARAM_PREVIEW_QUERY_CONTROL_TYPE_TEXT = 1 ; // text box
  public static final int    PARAM_PREVIEW_QUERY_CONTROL_TYPE_SELECT = 2 ; //combo box
  public static final int    PARAM_PREVIEW_QUERY_CONTROL_TYPE_CALENDAR = 3 ; //calendar component



  // --------------------- list of functions -----------------------


    // mandatory key indicating num of functions
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELDS_NUM      = "previewQ_function_fields_num";
    // optional key indicating the function ID, e.g. ID of Sum ()
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_ID      = "param_preview_query_function_field_id";
    // optional key indicating the function Name, e.g. "Summation function"
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME      = "param_preview_query_function_field_name";
    // optional key indicating the function SQL code, e.g. Sum()
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_SQLCALL      = "param_preview_query_function_field_sqlcall";
    // optional key indicating the function type, e.g. aggregate, arithmetic
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_TYPE      = "param_preview_query_function_field_type";
  
    /* Mandatory key indicating num of parameters for each function.
       Each one of these parameters will be saved in the ADH_FIELD table
       A corresponding row is created in ADH_PARAMETER_VALUE where 
       ADH_PARAMETER_VALUE.LINK_FIELD_ID has the ID of the field representing 
       the function and ADH_PARAMETER_VALUE.VALUE_FIELD_ID has the ID of the 
       field representing the parameter of the function.
       The ADH_PARAMETER_VALUE is the relation of the many-to-many relationship 
       between ADH_FIELD and ADH_PARAMETER_VALUE */
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETERS_NUM      = "previewQ_function_fields_parameters_num";
  
    /*optional key indicating the parameter ID in the table ADH_PARAMETER_DEFINITION which 
          describes how many parameters exist for each function
      The key is saved in ADH_PARAMETER_VALUE.FUNCTION_PARAMETER_ID
      The key could be null because some parameters are optional*/
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID      = "previewQ_function_fields_parameter_id";
  
    // optional key indicating the parameter Type, e.g. dataview field, 
    // const field, or function field. This is saved in ADH_FIELD.FIELD_TYPE_ID
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE      = "previewQ_function_fields_parameter_type";
  /* 
  -->In case the parameter is a const field:
       optional key indicating the const index in the list of constants
       this is saved in ADH_PARAMETER_VALUE.LINK_FIELD_ID   
  -->In case the parameter is a function field:
       optional key indicating the const index in the list of functions
       this is saved in ADH_PARAMETER_VALUE.LINK_FIELD_ID   
  */
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX      = "previewQ_function_fields_parameter_index";
  /* In case the parameter is a const field:
       optional key indicating the const value
       This is saved in ADH_PARAMETER_VALUE.VALUE_FIELD_ID
  */
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE      = "previewQ_function_fields_parameter_value";
  // In case the parameter is a dataview field:
    // optional key indicating the field ID. This is saved in ADH_PARAMETER_VALUE.LINK_FIELD_ID   
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_ID      = "previewQ_function_fields_parameter_field_id";
    // optional key indicating the field Name. This is not saved in the database
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_NAME      = "previewQ_function_fields_parameter_field_name";
    // optional key indicating the field parent dataview ID. This is not saved in the database
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_ID     = "previewQ_function_fields_parameter_dataview_id";
    // optional key indicating the field parent dataview name. This is not saved in the database
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_NAME      = "previewQ_function_fields_parameter_dataview_name";
    // optional key indicating the field parent dataview type. This is not saved in the database
    // could be used to generate the quotations ??????????
  public static final String PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_TYPE      = "previewQ_function_fields_parameter_dataview_type";

  // --------------------- list of terms -----------------------

  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_NUM = "param_preview_query_terms_field_to_field_num";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TERM_NAME = "param_preview_query_terms_field_to_field_term_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_ID = "param_preview_query_terms_field_to_field_1st_field_id";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_NAME = "param_preview_query_terms_field_to_field_1st_field_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_VALUE = "param_preview_query_terms_field_to_field_1st_field_value";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_TYPE = "param_preview_query_terms_field_to_field_1st_field_type";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_ID = "param_preview_query_terms_field_to_field_1st_field_dataview_id";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_NAME = "param_preview_query_terms_field_to_field_1st_field_dataview_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_TYPE = "param_preview_query_terms_field_to_field_1st_field_dataview_type";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_ID = "param_preview_query_terms_field_to_field_2nd_field_id";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_NAME = "param_preview_query_terms_field_to_field_2nd_field_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_VALUE = "param_preview_query_terms_field_to_field_2nd_field_value";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_TYPE = "param_preview_query_terms_field_to_field_2nd_field_type";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_ID = "param_preview_query_terms_field_to_field_2nd_field_dataview_id";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_NAME = "param_preview_query_terms_field_to_field_2nd_field_dataview_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_TYPE = "param_preview_query_terms_field_to_field_2nd_field_dataview_type";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_ID = "param_preview_query_terms_field_to_field_operator_id";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_NAME = "param_preview_query_terms_field_to_field_operator_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_SQL = "param_preview_query_terms_field_to_field_operator_sql";
  //public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_TYPE = "param_preview_query_terms_field_to_field_operator_type";

  public static final int    PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE    = 1;
  public static final int    PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE = 2;
  //public static final int    PARAM_PREVIEW_QUERY_TERMS_SYMBOL_TYPE            = 3;

  public static final int    PARAM_PREVIEW_QUERY_FTF_OPERATORS_TYPE    = 1;
  public static final int    PARAM_PREVIEW_QUERY_FTDV_OPERATORS_TYPE   = 2;

  public static final String PARAM_PREVIEW_QUERY_OPERATOR_LEFT_JOIN              = "left join" ;
  public static final String PARAM_PREVIEW_QUERY_OPERATOR_RIGHT_JOIN             = "right join" ;
  public static final String PARAM_PREVIEW_QUERY_OPERATOR_BEGINS_WITH            = "begins with" ;
  public static final String PARAM_PREVIEW_QUERY_OPERATOR_ENDS_WITH              = "ends with" ;
  public static final String PARAM_PREVIEW_QUERY_OPERATOR_CONTAINS               = "contains" ;

  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_NUM = "param_preview_query_terms_field_to_dataview_num";
  //public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_DATAVIEW_ID = "param_preview_query_terms_field_to_dataview_dataview_id";
  //public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_DATAVIEW_NAME = "param_preview_query_terms_field_to_dataview_dataview_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TERM_NAME = "param_preview_query_terms_field_to_dataview_term_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_ID = "param_preview_query_terms_field_to_dataview_1st_field_id";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_NAME = "param_preview_query_terms_field_to_dataview_1st_field_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_VALUE = "param_preview_query_terms_field_to_dataview_1st_field_value";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_TYPE = "param_preview_query_terms_field_to_dataview_1st_field_type";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_ID = "param_preview_query_terms_field_to_dataview_1st_field_dataview_id";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_NAME = "param_preview_query_terms_field_to_dataview_1st_field_dataview_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_TYPE = "param_preview_query_terms_field_to_dataview_1st_field_dataview_type";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_ID = "param_preview_query_terms_field_to_dataview_operator_id";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_NAME = "param_preview_query_terms_field_to_dataview_operator_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_SQL = "param_preview_query_terms_field_to_dataview_operator_sql";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_ID = "param_preview_query_terms_field_to_dataview_related_dataview_id";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_NAME = "param_preview_query_terms_field_to_dataview_related_dataview_name";
  public static final String PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_TYPE_ID = "param_preview_query_terms_field_to_dataview_related_dataview_type_id";

  // --------------------- list of wheres -----------------------

  public static final String PARAM_PREVIEW_QUERY_WHERES_NUM = "param_preview_query_wheres_num";
  public static final String PARAM_PREVIEW_QUERY_WHERE_TYPE = "param_preview_query_where_type";
  public static final String PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID = "param_preview_query_where_element_id";
  public static final String PARAM_PREVIEW_QUERY_WHERE_SYMBOL_SQL = "param_preview_query_where_symbol_sql";

  public static final int    PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM = 1;
  public static final int    PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL = 2;

  public static final int    PARAM_PREVIEW_QUERY_WHERE_SYMBOL_AND       = 1;
  public static final int    PARAM_PREVIEW_QUERY_WHERE_SYMBOL_OR        = 2;
  public static final int    PARAM_PREVIEW_QUERY_WHERE_SYMBOL_NOT       = 3;
  public static final int    PARAM_PREVIEW_QUERY_WHERE_SYMBOL_LBRACKET  = 4;
  public static final int    PARAM_PREVIEW_QUERY_WHERE_SYMBOL_RBRACKET  = 5;


  // --------------------- list of group by ---------------------

  public static final String PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM      = "previewQ_groupby_fields_num";
  public static final String PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID      = "previewQ_groupby_field_id";
  public static final String PARAM_PREVIEW_QUERY_GROUPBY_FIELD_NAME    = "previewQ_groupby_field_name";
  public static final String PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE    = "previewQ_groupby_field_type";
  public static final String PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DESCRIPTION = "previewQ_groupby_field_description";
  // alias should be removed - no alias is allowed in group by clause 
  //public static final String PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ALIAS  = "previewQ_groupby_field_alias";
  public static final String PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_ID = "previewQ_groupby_field_dataview_id";
  public static final String PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_NAME = "previewQ_groupby_field_dataview_name";
  public static final String PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_TYPE = "previewQ_groupby_field_dataview_type";


  // --------------------- list of order by ---------------------

  public static final String PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM      = "previewQ_orderby_fields_num";
  public static final String PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID      = "previewQ_orderby_field_id";
  public static final String PARAM_PREVIEW_QUERY_ORDERBY_FIELD_NAME    = "previewQ_orderby_field_name";
  public static final String PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE    = "previewQ_orderby_field_type";
  public static final String PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DESCRIPTION = "previewQ_orderby_field_description";
  public static final String PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE    = "previewQ_orderby_field_order_type";
  public static final String PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_ID = "previewQ_orderby_field_dataview_id";
  public static final String PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_NAME = "previewQ_orderby_field_dataview_name";
  public static final String PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_TYPE = "previewQ_orderby_field_dataview_type";

  public static final String ORDER_TYPE_ASCENDING          = "ASC";
  public static final String ORDER_TYPE_DESCENDING         = "DESC";

  //public static final int ORDER_TYPE_ASCENDING          = 1;
  //public static final int ORDER_TYPE_DESCENDING         = 2;


  // --------------------- list of having -----------------------

  public static final String PARAM_PREVIEW_QUERY_HAVINGS_NUM = "param_preview_query_havings_num";
  public static final String PARAM_PREVIEW_QUERY_HAVING_TYPE = "param_preview_query_having_type";
  public static final String PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID = "param_preview_query_having_element_id";
  public static final String PARAM_PREVIEW_QUERY_HAVING_SYMBOL_SQL = "param_preview_query_having_symbol_sql";



  //---------------------------------------------------------------------------
  public static final String ACTION_LOAD_QUERY = "action_load_query";
  public static final String PARAM_LOAD_QUERY_DATAVIEW_ID = "param_load_query_dataview_id";

  //---------------------------------------------------------------------------
  //public static final String ACTION_LIST_DATAVIEWS = "action_list_dataviews";
  public static final String ACTION_LIST_GENERAL_DATAVIEWS  = "action_list_general_dataviews";
  public static final String ACTION_LIST_KPI_DATAVIEWS      = "action_list_kpi_dataviews";
  public static final String ACTION_LIST_DCM_QUOTA_CALCULATION_DATAVIEWS      = "action_list_dcm_quota_calculation_dataviews";
  public static final String ACTION_LIST_ELIGIBLE_DATAVIEWS = "action_list_eligible_dataviews";
  //public static final String PARAM_CONSTRUCT_QUERY_SQL_QUERYDTO = "constructQSQL_querydto";

  //---------------------------------------------------------------------------
  //public static final String ACTION_UPDATE_DATAVIEW = "action_update_dataview";
  public static final String ACTION_UPDATE_GENERAL_DATAVIEW 	= "action_update_general_dataview";
  public static final String ACTION_UPDATE_KPI_DATAVIEW 	= "action_update_kpi_dataview";
  public static final String ACTION_UPDATE_DCM_QUOTA_CALCULATION_DATAVIEW = "action_update_dcm_quota_calculation_dataview";
  public static final String ACTION_UPDATE_ELIGIBLE_DATAVIEW 	= "action_update_eligible_dataview";
  public static final String PARAM_UPDATE_DATAVIEW_DATAVIEW_ID = "param_update_dataview_dataview_id";


  //---------------------------------------------------------------------------
  //public static final String ERR_SAVE_QUERY = "couldn't save the Data View";
  public static final String ERR_UNIQUE_QUERY = "Data View name exists already";


  public static final boolean   DEBUG_FLAG    = true ;

 public static final int PREVIEW_REPORT_ROW_MAX = 100;

}
package com.mobinil.sds.core.facade.handlers;

import java.util.Vector;
import java.util.HashMap;

import com.mobinil.sds.web.interfaces.gn.querybuilder.QueryBuilderInterfaceKey;
import com.mobinil.sds.core.system.gn.querybuilder.domain.QueryBuilderEngine;
import com.mobinil.sds.core.system.gn.querybuilder.dto.*;
import com.mobinil.sds.web.interfaces.InterfaceKey;

import com.mobinil.sds.core.system.gn.dataview.dao.* ;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.*;

import com.mobinil.sds.core.system.gn.scopes.dao.*;
import com.mobinil.sds.core.system.gn.scopes.model.*;
import com.mobinil.sds.web.interfaces.gn.scopes.*;

import com.mobinil.sds.core.system.fn.importdata.dao.*;
import com.mobinil.sds.core.system.fn.addfunction.model.*;
import com.mobinil.sds.web.interfaces.fn.*;

import com.mobinil.sds.core.utilities.CachEngine;

public class QueryBuilderHandler 
{
  static final int INITIALIZE_GENERAL_WIZARD              =  1;
  static final int INITIALIZE_KPI_WIZARD                  	   =  2;
  static final int INITIALIZE_ELIGIBLE_WIZARD               =  3;
  static final int INITIALIZE_QUERY_BUILDER                 =  4;
  static final int PREVIEW_QUERY                                =  5;
  static final int SAVE_QUERY                                    =  6;
  static final int PREVIEW_SAVE_QUERY                       =  7;
  static final int PREVIEW_CHECK_QUERY                     =  8;
  static final int LIST_GENERAL_DATAVIEWS                 =  9;
  static final int LIST_KPI_DATAVIEWS                        = 10;
  static final int LIST_ELIGIBLE_DATAVIEWS                 = 11;
  static final int UPDATE_GENERAL_DATAVIEW              = 12;
  static final int UPDATE_KPI_DATAVIEW                     = 13;
  static final int UPDATE_ELIGIBLE_DATAVIEW              = 14;

  static final int SHOW_ALL_FUNCTIONS                      = 15;
  static final int ADD_FUNCTION                                 = 16;
  static final int EDIT_FUNCTION                                = 17;
  static final int SAVE_FUNCTION                               = 18;

  static final int SHOW_ALL_SCOPES                           = 19;
  static final int ADD_SCOPE                                      = 20;
  static final int EDIT_SCOPE                                     = 21;
  static final int SAVE_SCOPE                                    = 22;
  static final int SAVE_SCOPE_STATUS                       = 23;

  static final int SHOW_ALL_DATA_VIEWS                     = 24;
  static final int SAVE_DATA_VIEW_STATUS                  = 25;

  static final int INITIALIZE_DCM_QUOTA_CALCULATION_WIZARD = 26;
  static final int LIST_DCM_QUOTA_CALCULATION_DATAVIEWS = 27;
  static final int UPDATE_DCM_QUOTA_CALCULATION_DATAVIEW = 28;
  
  // should be deleted later
  //static final int CONSTRUCT_QUERY_SQL                   = 100;
  static final int LOAD_QUERY                                    = 101;


// ----------- temporary code begin ------------------------
/*
  // ------- hardcoding group by keys ---------------------------
  private void insertHardCodedGroupByKeys_DataViewField (HashMap paramHashMap)
  {
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM, 
                        new String ("1") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE + "0", 
                        new String (""+QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD) ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID + "0", 
                        new String ("15") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_NAME + "0",
                        new String ("PRODUCT_CATEGORY_ID") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DESCRIPTION + "0", 
                        new String ("PRODUCT_CATEGORY_ID_description") ) ;
    //paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ALIAS + "0", 
    //                    new String ("PRODUCT_CATEGORY_ID_alias") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_ID + "0", 
                        new String ("2") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_NAME + "0", 
                        new String ("VW_GEN_PRODUCT") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_TYPE + "0", 
                        new String (""+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE) ) ;
  }

  private void insertHardCodedGroupByKeys_FunctionField (HashMap paramHashMap)
  {

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM, 
                        new String ("1") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE + "0", 
                        new String (""+QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD) );
        // first function in the function list
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID + "0", 
                        new String ("0") ) ;
  }

  // ------- hardcoding having keys ---------------------------
  private void insertHardCodedHavingKeys_DataViewField (HashMap paramHashMap)
  {
    
    //select sum (vw_gen_product.PRODUCT_ID), vw_gen_product.PRODUCT_CATEGORY_ID  
    //from vw_gen_product
    //group by vw_gen_product.PRODUCT_CATEGORY_ID
    //having vw_gen_product.PRODUCT_CATEGORY_ID = 17

    insertHardCodedGroupByKeys_DataViewField (paramHashMap) ;

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVINGS_NUM, 
                        new String ("1") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_TYPE + "0",
                        new String ( "" + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM ) ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID + "0",
                        new String ( "0" ) ) ; // first term
  }

  // ------- hardcoding order by keys ---------------------------
  private void insertHardCodedOrderByKeys_DataViewField (HashMap paramHashMap)
  {
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM, 
                        new String ("1") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE + "0", 
                        new String (""+QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD) ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID + "0", 
                        new String ("15") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_NAME + "0",
                        new String ("PRODUCT_CATEGORY_ID") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DESCRIPTION + "0", 
                        new String ("PRODUCT_CATEGORY_ID_description") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_ID + "0", 
                        new String ("2") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_NAME + "0", 
                        new String ("VW_GEN_PRODUCT") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_TYPE + "0", 
                        new String (""+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE) ) ;

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE + "0", 
                        new String (""+QueryBuilderInterfaceKey.ORDER_TYPE_ASCENDING) ) ;
  }

  private void insertHardCodedOrderByKeys_FunctionField (HashMap paramHashMap)
  {
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM, 
                        new String ("1") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE + "0", 
                        new String (""+QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD) );
        // first function in the function list
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID + "0", 
                        new String ("0") ) ;

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE + "0", 
                        new String (""+QueryBuilderInterfaceKey.ORDER_TYPE_DESCENDING) ) ;

  }

  // ------- hardcoding parameter keys ---------------------------
  private void insertHardCodedQueryParametersList (HashMap paramHashMap)
  {

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAMS_NUM, 
                        new String ("3") ) ;

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME + "0", 
                        new String ("sample param1 name") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE + "0", 
                        new String ("fady") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL + "0", 
                        new String ("Please enter your name") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC + "0", 
                        new String ("sample param1 description") ) ;

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE + "0", 
                        new String (""+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_TEXT) ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID + "0", 
                        new String (""+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONTROL_TYPE_TEXT) ) ;


    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME + "1", 
                        new String ("sample param2 name") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE + "1", 
                        new String ("11/11/2004") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL + "1", 
                        new String ("Please enter your birthdate") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC + "1", 
                        new String ("sample param2 description") ) ;

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID + "1", 
                        new String (""+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONTROL_TYPE_CALENDAR) ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE + "1", 
                        new String (""+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_DATE) ) ;


    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME + "2", 
                        new String ("sample param3 name") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE + "2", 
                        new String ("41") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL + "2", 
                        new String ("Please enter your number") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC + "2", 
                        new String ("sample param3 description") ) ;

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE + "2", 
                        new String (""+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_NUMERIC) ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID + "2", 
                        new String (""+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONTROL_TYPE_TEXT) ) ;



    //paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE + "0", 
    //                    new String (""+QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD) );
  }

  private void insertHardCodedQueryParameters_SelectedFields (HashMap paramHashMap)
  {

    insertHardCodedQueryParametersList (paramHashMap) ;

    //keys suitable to the following query:
    //  select dataview.field, parameter1, parameter2
    //  from dataview

    //  SELECT  "VW_GEN_PRODUCT"."PRODUCT_ID" AS "PRODUCT_ID" , 
    //          to_date ( '11/11/2004', 'dd/mm/yyyy') AS "sample param2 name" , 
    //          'fady' AS "sample param1 name" 
    //  FROM "VW_GEN_PRODUCT" 
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NUM, 
                        new String ("3") ) ;

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS + "1", 
                        new String ("1") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES + "1", 
                        new String ("" + QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD) ) ;

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS + "2", 
                        new String ("0") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES + "2", 
                        new String ("" + QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD) ) ;

  }

  private void insertHardCodedQueryParameters_Terms (HashMap paramHashMap)
  {

    insertHardCodedQueryParametersList (paramHashMap) ;

    
    //keys suitable to the following query:
    //  select dataview.field
    //  from dataview
    //  where dataview.field = parameter1

    //  SELECT  "VW_GEN_PRODUCT"."PRODUCT_NAME" AS "PRODUCT_NAME" 
    //  FROM    "VW_GEN_PRODUCT" 
    //  WHERE   "VW_GEN_PRODUCT"."PRODUCT_ID" = 41 

    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_ID + "0", 
                        new String ("2") ) ;
    paramHashMap.put ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_TYPE + "0", 
                        new String ("" + QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD) ) ;

  }

  private void insertHardCodedQueryParameters_FunctionParameter (HashMap paramHashMap)
  {

    insertHardCodedQueryParametersList (paramHashMap) ;

    //keys suitable to the following query:

    //  SELECT CHR ( 41 ) AS "Function (1):CHR (x)" 
    //  FROM "VW_GEN_PRODUCT" 

    paramHashMap.put ( 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME  + "0" + 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX + "0", 
            new String ("2") ) ;
    paramHashMap.put ( 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME  + "0" + 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE + "0", 
            new String ("" + QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD) ) ;
  }


  private void insertHardCodedUniverseKeys (HashMap paramHashMap)
  {
    //key for initialize action
    //key for preview and later actions
    paramHashMap.put ( 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIVERSE, 
            //new String ("1") ) ;
            new String ( QueryBuilderInterfaceKey.UNIVERSE_TYPE_AD_HOC_REPORTS) ) ;

  }


  private void insertHardCodedSaveKeys (HashMap paramHashMap)
  {
    //key for initialize action
    paramHashMap.put ( 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE, 
            new String ( "" + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE) ) ;

    paramHashMap.put ( 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_ISSUE, 
            new String ( "0" ) );
  }



  private void insertHardCodedSaveKeysTesting (HashMap paramHashMap)
  {
    paramHashMap.put ( 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE, 
            new String ( "" + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_TRUE) ) ;
    paramHashMap.put ( 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_ISSUE, 
            new String ( "316" ) );
  }

  private void insertHardCodedQueryKeys (HashMap paramHashMap)
  {
    //insertHardCodedUniverseKeys (paramHashMap) ;
    //insertHardCodedSaveKeys     (paramHashMap) ;

    //insertHardCodedGroupByKeys_DataViewField (paramHashMap) ;
    //insertHardCodedGroupByKeys_FunctionField (paramHashMap) ;
            
    //insertHardCodedOrderByKeys_DataViewField (paramHashMap) ;
    //insertHardCodedOrderByKeys_FunctionField (paramHashMap) ;
            
    //insertHardCodedHavingKeys_DataViewField (paramHashMap) ;

    //insertHardCodedQueryParameters_SelectedFields (paramHashMap) ;
    //insertHardCodedQueryParameters_Terms(paramHashMap) ;
    //insertHardCodedQueryParameters_FunctionParameter(paramHashMap) ;

    //insertHardCodedSaveKeysTesting     (paramHashMap) ;
  }
*/

// ----------- temporary code end ------------------------




  public static HashMap handle(String action, HashMap paramHashMap, java.sql.Connection conSDSConnection)
  {
    int actionType = 0;
    HashMap dataHashMap = null;

    try 
    {
      // temporary code begin
      /*
      Utility.logger.debug ( "  -- Begin HashMap in QueryBuilderHandlerEJBBean ------------------------------------------------------ ") ;
      Object arrKey[] = paramHashMap.keySet().toArray() ;
      for (int i = 0 ; i < arrKey.length ; i++ )
      {
        //Utility.logger.debug ( "found Key: " + (String) arrKey[i] ) ;
        Utility.logger.debug ( "  " + (String) arrKey[i] + "\n    ----> " + (String) paramHashMap.get ((String) arrKey[i]) ) ;
      }
      Utility.logger.debug ( "  -- End HashMap in QueryBuilderHandlerEJBBean ------------------------------------------------------ ") ;
      */
      // temporary code end

      if (action.equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_GENERAL_WIZARD))                                   
        actionType = INITIALIZE_GENERAL_WIZARD;                                                                       
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_KPI_WIZARD))                                   
        actionType = INITIALIZE_KPI_WIZARD;                                                                       
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_ELIGIBLE_WIZARD))                                   
        actionType = INITIALIZE_ELIGIBLE_WIZARD;                                                                       
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_QUERY_BUILDER))                       
        actionType = INITIALIZE_QUERY_BUILDER; 
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_DCM_QUOTA_CALCULATION_WIZARD))                       
        actionType = INITIALIZE_DCM_QUOTA_CALCULATION_WIZARD;   
      //else if (action.equals(QueryBuilderInterfaceKey.ACTION_CONSTRUCT_QUERY_SQL))                       
      // actionType = CONSTRUCT_QUERY_SQL ;                                                                
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_PREVIEW_QUERY))                       
        actionType = PREVIEW_QUERY ;
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_SAVE_QUERY))                       
        actionType = SAVE_QUERY ;
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_LOAD_QUERY))                       
        actionType = LOAD_QUERY ;
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_PREVIEW_SAVE_QUERY))                       
        actionType = PREVIEW_SAVE_QUERY ;
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_PREVIEW_CHECK_QUERY))                       
        actionType = PREVIEW_CHECK_QUERY ;
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_LIST_GENERAL_DATAVIEWS))                       
        actionType = LIST_GENERAL_DATAVIEWS ;
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_LIST_KPI_DATAVIEWS))                       
        actionType = LIST_KPI_DATAVIEWS ;
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_LIST_DCM_QUOTA_CALCULATION_DATAVIEWS))                       
        actionType = LIST_DCM_QUOTA_CALCULATION_DATAVIEWS ;  
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_LIST_ELIGIBLE_DATAVIEWS))                       
        actionType = LIST_ELIGIBLE_DATAVIEWS ;
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_UPDATE_GENERAL_DATAVIEW))                       
        actionType = UPDATE_GENERAL_DATAVIEW ;
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_UPDATE_KPI_DATAVIEW))                       
        actionType = UPDATE_KPI_DATAVIEW ;
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_UPDATE_DCM_QUOTA_CALCULATION_DATAVIEW))                       
        actionType = UPDATE_DCM_QUOTA_CALCULATION_DATAVIEW ;  
      else if (action.equals(QueryBuilderInterfaceKey.ACTION_UPDATE_ELIGIBLE_DATAVIEW))                       
        actionType = UPDATE_ELIGIBLE_DATAVIEW ;

      else if (action.equals(ScopesInterfaceKey.ACTION_SHOW_ALL_SCOPES))                       
        actionType = SHOW_ALL_SCOPES ;
      else if (action.equals(ScopesInterfaceKey.ACTION_ADD_SCOPE))                       
        actionType = ADD_SCOPE ;
      else if (action.equals(ScopesInterfaceKey.ACTION_EDIT_SCOPE))                       
        actionType = EDIT_SCOPE ;
      else if (action.equals(ScopesInterfaceKey.ACTION_SAVE_SCOPE))                       
        actionType = SAVE_SCOPE ;
      else if (action.equals(ScopesInterfaceKey.ACTION_SAVE_SCOPE_STATUS))                       
        actionType = SAVE_SCOPE_STATUS;  

      else if (action.equals(FunctionsInterfaceKey.ACTION_SHOW_ALL_FUNCTIONS))                       
        actionType = SHOW_ALL_FUNCTIONS ;
      else if (action.equals(FunctionsInterfaceKey.ACTION_ADD_FUNCTION))                       
        actionType = ADD_FUNCTION ;
      else if (action.equals(FunctionsInterfaceKey.ACTION_EDIT_FUNCTION))                       
        actionType = EDIT_FUNCTION ;
      else if (action.equals(FunctionsInterfaceKey.ACTION_SAVE_FUNCTION))                       
        actionType = SAVE_FUNCTION ;    

      else if (action.equals(ScopesInterfaceKey.ACTION_SHOW_ALL_DATA_VIEWS))                       
        actionType = SHOW_ALL_DATA_VIEWS;
      else if (action.equals(ScopesInterfaceKey.ACTION_SAVE_DATA_VIEW_STATUS))                       
        actionType = SAVE_DATA_VIEW_STATUS;
        
      switch (actionType)                                                                                     
      {                                                                                                       
        case INITIALIZE_GENERAL_WIZARD:
          try                                                                                                 
          {
            
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;

            // temporary code begin
            //insertHardCodedQueryKeys (paramHashMap);
            // temporary code end

            dataHashMap = queryBuilderEngine.initializeWizard ( conSDSConnection,QueryBuilderInterfaceKey.UNIVERSE_TYPE_AD_HOC_REPORTS );
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case INITIALIZE_KPI_WIZARD:
          try                                                                                                 
          {
            
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;

            // temporary code begin
            //insertHardCodedQueryKeys (paramHashMap);
            // temporary code end

            dataHashMap = queryBuilderEngine.initializeWizard ( conSDSConnection,QueryBuilderInterfaceKey.UNIVERSE_TYPE_KPI );
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case INITIALIZE_DCM_QUOTA_CALCULATION_WIZARD:
          try                                                                                                 
          {
            
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;

            // temporary code begin
            //insertHardCodedQueryKeys (paramHashMap);
            // temporary code end

            dataHashMap = queryBuilderEngine.initializeWizard ( conSDSConnection,QueryBuilderInterfaceKey.UNIVERSE_TYPE_DCM_QUOTA_CALCULATION );
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case INITIALIZE_ELIGIBLE_WIZARD:
          try                                                                                                 
          {
            
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;

            // temporary code begin
            //insertHardCodedQueryKeys (paramHashMap);
            // temporary code end

            dataHashMap = queryBuilderEngine.initializeWizard ( conSDSConnection,QueryBuilderInterfaceKey.UNIVERSE_TYPE_LINES_ELIGIBLE_FOR_COMMISSIONING );
            
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case INITIALIZE_QUERY_BUILDER:
          try
          {
            
            String strQueryName;
            String strQueryDesc;
            Vector argSelectedDataViews = new Vector () ;
            QueryDTO queryDTO ;
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
            dataHashMap = new HashMap ();
            Integer nNumParam; 
            StringBuffer paramKey ;

            //argSelectedDataViews = (Vector) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS) ;
            
            nNumParam = new Integer ( (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS_NUM) ) ;
            for (int i=0 ; i < nNumParam.intValue() ; i++)
            {
              paramKey = new StringBuffer ( QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS ) ;
              paramKey.append ( i ) ;
              argSelectedDataViews.addElement( new Integer ((String) paramHashMap.get (paramKey.toString())) );
            }
            strQueryName = (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME) ;
            strQueryDesc = (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC) ;
            queryDTO = queryBuilderEngine.initializeQueryBuilder(conSDSConnection,
             strQueryName, strQueryDesc, argSelectedDataViews);
            
            
            
            
            

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryDTO);
            
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        /*case CONSTRUCT_QUERY_SQL:
          try
          {
            QueryDTO queryDTO;
            String strSQL;
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
            dataHashMap = new HashMap ();
            
            queryDTO = (QueryDTO) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_CONSTRUCT_QUERY_SQL_QUERYDTO) ;
            
            strSQL = queryBuilderEngine.constructQuerySQL(queryDTO);


            dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, strSQL);
            
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;*/
        case SAVE_QUERY:
          try
          {
            
            QueryDTO queryDTO;
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
            dataHashMap = new HashMap ();
            queryBuilderEngine.saveQuery (conSDSConnection,paramHashMap);
            
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        case PREVIEW_QUERY:
          try
          {
            
            QueryViewerDTO queryViewerDTO;
            String strErrorMessage = null;
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
            dataHashMap = new HashMap ();

            //queryDTO = (QueryDTO) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERYDTO) ;

            // temporary code begin
            //insertHardCodedQueryKeys (paramHashMap);
            // temporary code end
           
            System.out.println("previewQuery false 1");
            queryViewerDTO = queryBuilderEngine.previewQuery(false, conSDSConnection,paramHashMap/*, strErrorMessage*/);

            Utility.logger.debug ("QueryBuilderHandler:generated SQL: \n" + queryViewerDTO.getSQLCode() ) ;
            if (queryViewerDTO.getErrorMessage() == null)
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryViewerDTO);
            else 
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, queryViewerDTO.getErrorMessage());
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryViewerDTO);
            }
            
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        case PREVIEW_SAVE_QUERY:
          try
          {
            
            QueryViewerDTO queryViewerDTO;
            String strErrorMessage = null;
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
            dataHashMap = new HashMap ();

            System.out.println("previewQuery true");
            queryViewerDTO = queryBuilderEngine.previewQuery(true,conSDSConnection,paramHashMap);

            //Utility.logger.debug ("QueryBuilderHandler:generated SQL: \n" + queryViewerDTO.getSQLCode() ) ;

            if (queryViewerDTO.getErrorMessage() == null )
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryViewerDTO);
            else 
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, queryViewerDTO.getErrorMessage());
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryViewerDTO);
            }
            
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;        
        case PREVIEW_CHECK_QUERY:
          try
         {            
            QueryViewerDTO queryViewerDTO;
            String strErrorMessage = null;
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
            dataHashMap = new HashMap ();
            
            //queryDTO = (QueryDTO) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERYDTO) ;            
            // temporary code begin
            //insertHardCodedQueryKeys (paramHashMap);
            // temporary code end
            String strDataViewName = (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_NAME) ;

            if ( DataViewDAO.dataViewNameExists (conSDSConnection, strDataViewName) == false )
            {
              System.out.println("previewQuery false 2");
              queryViewerDTO = queryBuilderEngine.previewQuery(true,conSDSConnection,paramHashMap);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "VALID" );
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryViewerDTO);
            }
            else
            {                
              System.out.println("previewQuery false 3");
              queryViewerDTO = queryBuilderEngine.previewQuery(false,conSDSConnection,paramHashMap);
              
              Utility.logger.debug ("QueryBuilderHandler:generated SQL: \n" + queryViewerDTO.getSQLCode() ) ;
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, QueryBuilderInterfaceKey.ERR_UNIQUE_QUERY) ;

              if (queryViewerDTO.getErrorMessage() == null )
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryViewerDTO);
              /*
              //an assumption here is that saved queries have been successfully previewed before saving
              else 
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, queryViewerDTO.getErrorMessage());
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryViewerDTO);
              }*/
            }
            
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;        
        case LOAD_QUERY:
          try
          {
            
            String strErrorMessage = null;
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
            dataHashMap = new HashMap ();
            
            int argDataViewID = new Integer ((String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_LOAD_QUERY_DATAVIEW_ID) ).intValue() ;
            Vector vec =new Vector();
            QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO (conSDSConnection,argDataViewID,vec);
            
            if (queryDTO.getErrorMessage() == null )
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryDTO);
            else 
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, queryDTO.getErrorMessage());

            
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        case LIST_GENERAL_DATAVIEWS:
          try
          {
            
            QueryDTO queryDTO;
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
            dataHashMap = new HashMap ();
            Vector vecDataViewsList = queryBuilderEngine.listDataViews ( conSDSConnection,QueryBuilderInterfaceKey.UNIVERSE_TYPE_AD_HOC_REPORTS );
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecDataViewsList);
            
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        case LIST_KPI_DATAVIEWS:
          try
          {
            
            QueryDTO queryDTO;
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
            dataHashMap = new HashMap ();
            Vector vecDataViewsList = queryBuilderEngine.listDataViews ( conSDSConnection,QueryBuilderInterfaceKey.UNIVERSE_TYPE_KPI );
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecDataViewsList);
            
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        case LIST_DCM_QUOTA_CALCULATION_DATAVIEWS:
          try
          {
            
            QueryDTO queryDTO;
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
            dataHashMap = new HashMap ();
            Vector vecDataViewsList = queryBuilderEngine.listDataViews ( conSDSConnection,QueryBuilderInterfaceKey.UNIVERSE_TYPE_DCM_QUOTA_CALCULATION );
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecDataViewsList);
            
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        case LIST_ELIGIBLE_DATAVIEWS:
          try
          {
            
            QueryDTO queryDTO;
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
            dataHashMap = new HashMap ();
            Vector vecDataViewsList = queryBuilderEngine.listDataViews ( conSDSConnection,QueryBuilderInterfaceKey.UNIVERSE_TYPE_LINES_ELIGIBLE_FOR_COMMISSIONING );
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecDataViewsList);
            
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        case UPDATE_GENERAL_DATAVIEW:
          try
          {
            
            QueryBuilderWizardDTO queryBuilderWizardDTO = null ;
            String strErrorMessage = null;
            Vector vecScopesList = null , vecNonScopesList = null ; 
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;

            // temporary code begin
            //insertHardCodedQueryKeys (paramHashMap);
            // temporary code end

            dataHashMap = queryBuilderEngine.initializeWizard(conSDSConnection, QueryBuilderInterfaceKey.UNIVERSE_TYPE_AD_HOC_REPORTS );

            queryBuilderWizardDTO = (QueryBuilderWizardDTO) dataHashMap.get (InterfaceKey.HASHMAP_KEY_DTO_OBJECT) ;
            vecScopesList = (Vector) dataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
            vecNonScopesList = (Vector) dataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION) ;
     
            dataHashMap = new HashMap ();

            int argDataViewID = new Integer ((String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_UPDATE_DATAVIEW_DATAVIEW_ID) ).intValue() ;
            Vector vec =new Vector();
            QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO (conSDSConnection,argDataViewID,vec);

            if (queryDTO.getErrorMessage() == null )
            {
              queryBuilderWizardDTO.setQuery(queryDTO);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryBuilderWizardDTO);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecScopesList);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecNonScopesList);
            }
            else 
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, queryDTO.getErrorMessage());
            }
              
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        case UPDATE_KPI_DATAVIEW:
          try
          {
            
            QueryBuilderWizardDTO queryBuilderWizardDTO = null ;
            String strErrorMessage = null;
            Vector vecScopesList = null , vecNonScopesList = null ; 
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;

            // temporary code begin
            //insertHardCodedQueryKeys (paramHashMap);
            // temporary code end

            dataHashMap = queryBuilderEngine.initializeWizard( conSDSConnection,QueryBuilderInterfaceKey.UNIVERSE_TYPE_KPI );

            queryBuilderWizardDTO = (QueryBuilderWizardDTO) dataHashMap.get (InterfaceKey.HASHMAP_KEY_DTO_OBJECT) ;
            vecScopesList = (Vector) dataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
            vecNonScopesList = (Vector) dataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION) ;
     
            dataHashMap = new HashMap ();

            int argDataViewID = new Integer ((String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_UPDATE_DATAVIEW_DATAVIEW_ID) ).intValue() ;
            Vector vec =new Vector();
            QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO (conSDSConnection,argDataViewID,vec);

            if (queryDTO.getErrorMessage() == null )
            {
              queryBuilderWizardDTO.setQuery(queryDTO);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryBuilderWizardDTO);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecScopesList);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecNonScopesList);
            }
            else 
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, queryDTO.getErrorMessage());
            }
              
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        case UPDATE_DCM_QUOTA_CALCULATION_DATAVIEW:
          try
          {
            
            QueryBuilderWizardDTO queryBuilderWizardDTO = null ;
            String strErrorMessage = null;
            Vector vecScopesList = null , vecNonScopesList = null ; 
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;

            // temporary code begin
            //insertHardCodedQueryKeys (paramHashMap);
            // temporary code end

            dataHashMap = queryBuilderEngine.initializeWizard( conSDSConnection,QueryBuilderInterfaceKey.UNIVERSE_TYPE_DCM_QUOTA_CALCULATION );

            queryBuilderWizardDTO = (QueryBuilderWizardDTO) dataHashMap.get (InterfaceKey.HASHMAP_KEY_DTO_OBJECT) ;
            vecScopesList = (Vector) dataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
            vecNonScopesList = (Vector) dataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION) ;
     
            dataHashMap = new HashMap ();

            int argDataViewID = new Integer ((String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_UPDATE_DATAVIEW_DATAVIEW_ID) ).intValue() ;
            Vector vec =new Vector();
            QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO (conSDSConnection,argDataViewID,vec);

            if (queryDTO.getErrorMessage() == null )
            {
              queryBuilderWizardDTO.setQuery(queryDTO);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryBuilderWizardDTO);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecScopesList);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecNonScopesList);
            }
            else 
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, queryDTO.getErrorMessage());
            }
              
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;
        case UPDATE_ELIGIBLE_DATAVIEW:
          try
          {
            
            QueryBuilderWizardDTO queryBuilderWizardDTO = null ;
            String strErrorMessage = null;
            Vector vecScopesList = null , vecNonScopesList = null ; 
            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;

            // temporary code begin
            //insertHardCodedQueryKeys (paramHashMap);
            // temporary code end

            dataHashMap = queryBuilderEngine.initializeWizard( conSDSConnection,QueryBuilderInterfaceKey.UNIVERSE_TYPE_LINES_ELIGIBLE_FOR_COMMISSIONING );

            queryBuilderWizardDTO = (QueryBuilderWizardDTO) dataHashMap.get (InterfaceKey.HASHMAP_KEY_DTO_OBJECT) ;
            vecScopesList = (Vector) dataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
            vecNonScopesList = (Vector) dataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION) ;
     
            dataHashMap = new HashMap ();

            int argDataViewID = new Integer ((String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_UPDATE_DATAVIEW_DATAVIEW_ID) ).intValue() ;
            Vector vec =new Vector();
            QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO (conSDSConnection,argDataViewID,vec);

            if (queryDTO.getErrorMessage() == null )
            {
              queryBuilderWizardDTO.setQuery(queryDTO);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryBuilderWizardDTO);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecScopesList);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecNonScopesList);
            }
            else 
            {
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, queryDTO.getErrorMessage());
            }
              
          }
          catch(Exception objExp)
          {
            objExp.printStackTrace();
          }
        break;

        case SHOW_ALL_SCOPES:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            Vector scopesVector = ScopesDAO.getAllScopes();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , scopesVector);
            Vector scopesStatusVector = ScopesDAO.getAllScopeStatus();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , scopesStatusVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case ADD_SCOPE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            Vector scopeStatusVector = ScopesDAO.getAllScopeStatus();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , scopeStatusVector);
            String scopeaction = "Add";
            dataHashMap.put( ScopesInterfaceKey.SCOPE_ACTION , scopeaction);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        
        case EDIT_SCOPE:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            Vector scopeStatusVector = ScopesDAO.getAllScopeStatus();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , scopeStatusVector);

            String ScopeID = (String)paramHashMap.get(ScopesInterfaceKey.CONTROL_HIDDEN_SCOPE_ID);
            int intScopeID = Integer.parseInt(ScopeID.trim());
            
            Vector ScopeVector = ScopesDAO.getScopeById(intScopeID);
            dataHashMap.put( ScopesInterfaceKey.HASHMAP_KEY_SCOPE_COLLECTION , ScopeVector);

            Vector ScopeFieldsVector = ScopesDAO.getScopeFields(intScopeID);
            dataHashMap.put( ScopesInterfaceKey.HASHMAP_KEY_SCOPE_FIELD_COLLECTION , ScopeFieldsVector);

            String scopeaction = "Edit";
            dataHashMap.put( ScopesInterfaceKey.SCOPE_ACTION , scopeaction);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        
        case SAVE_SCOPE:
          try                                                                                                 
          {
            Connection conX = Utility.getConnection(); 
            //for(int j=0; j<paramHashMap.size(); j++)
            //{
            //  String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
            //  String keyValue = (String)paramHashMap.get(tempStatusString);
            //  Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
            //}
            int hashmapsize = paramHashMap.size();
            Utility.logger.debug("size of hash map : "+hashmapsize);

            String ScopeID = (String)paramHashMap.get(ScopesInterfaceKey.CONTROL_HIDDEN_SCOPE_ID);
            
            String ScopeName = (String)paramHashMap.get(ScopesInterfaceKey.CONTROL_TEXTAREA_SCOPE_NAME);
            String ScopeStatusId = (String)paramHashMap.get(ScopesInterfaceKey.CONTROL_SELECT_SCOPE_STATUS);
            int intScopeStatusId = Integer.parseInt(ScopeStatusId.trim());
            String ScopeIssue = (String)paramHashMap.get(ScopesInterfaceKey.CONTROL_TEXT_SCOPE_ISSUE);
            int intScopeIssue = 0;
            String ScopeVersion = (String)paramHashMap.get(ScopesInterfaceKey.CONTROL_TEXT_SCOPE_VERSION);
            int intScopeVersion = Integer.parseInt(ScopeVersion.trim());
            String ScopeDescription = (String)paramHashMap.get(ScopesInterfaceKey.CONTROL_TEXTAREA_SCOPE_DESCRIPTION);
              
            String ParametersCount = (String)paramHashMap.get("user_defined_data_view_count");
            int intParametersCount = Integer.parseInt(ParametersCount.trim());
                
            String scopeaction = (String)paramHashMap.get("scopeaction");

            if(scopeaction.equals("Add"))
            {
            Long lScopeID = Utility.getSequenceNextVal(conX, "SEQ_ADH_DATA_VIEW_ID");
            Long lScopeIssue = Utility.getSequenceNextVal(conX, "SEQ_ADH_DATA_VIEW_ISSUE");
            intScopeIssue = Integer.parseInt(""+lScopeIssue+"");            
                ScopesDAO.insertScope(lScopeID,ScopeName,intScopeStatusId,intScopeIssue,intScopeVersion,ScopeDescription);
                for(int i=1;i<=intParametersCount;i++)
                {
                if(paramHashMap.get("user_defined_data_view__R"+i+"__C4") != null)
                    {
                   Long lFieldID = Utility.getSequenceNextVal(conX, "SEQ_ADH_FIELD_ID"); 
                   String FieldName = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C1"); 
                   String FieldDesc = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C2");
                   String FieldSQLCash = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C3");
                   String FieldRemove = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C4");
                   String ItemPosition = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C5");
                   int intItemPosition = Integer.parseInt(ItemPosition.trim());
                                
                   ScopesDAO.insertScopeFields(lScopeID,lFieldID,FieldName,FieldDesc,FieldSQLCash,intItemPosition);
                    }
                }
            }
            else if(scopeaction.equals("Edit"))
            {
            intScopeIssue = Integer.parseInt(ScopeIssue.trim());
            int intScopeID = Integer.parseInt(ScopeID.trim());
            ScopesDAO.updateScope(intScopeID,ScopeName,intScopeStatusId,intScopeIssue,intScopeVersion,ScopeDescription);

            Vector CurrentScopeFieldsVec = ScopesDAO.getScopeFields(intScopeID);
            FieldsModel fieldsModel;  
            String yyfieldId = "";
            boolean updated = false;
            String zzfieldID = "";
            int intFieldID;
            Long lFieldID ;
            Long longScopeID;
            String FieldName;
            String FieldDesc;
            String FieldSQLCash;
            String ItemPosition;
             for(int k=1;k<=intParametersCount;k++)
             {
                if(paramHashMap.get("user_defined_data_view__R"+k+"__C4") != null)
                {
                    zzfieldID = (String)paramHashMap.get("user_defined_data_view__R"+k+"__C6");
                    if(zzfieldID.equals(null) || zzfieldID.equals(""))
                    {
                       Utility.logger.debug("Field will be inserted");
                       lFieldID = Utility.getSequenceNextVal(conX, "SEQ_ADH_FIELD_ID"); 
                       longScopeID = new Long(ScopeID);  
                       FieldName = (String)paramHashMap.get("user_defined_data_view__R"+k+"__C1"); 
                       FieldDesc = (String)paramHashMap.get("user_defined_data_view__R"+k+"__C2");
                       FieldSQLCash = (String)paramHashMap.get("user_defined_data_view__R"+k+"__C3");
                       //ItemPosition = (String)paramHashMap.get("user_defined_data_view__R"+k+"__C5");
                       //int intItemPosition = Integer.parseInt(ItemPosition.trim());
                       
                       ScopesDAO.insertScopeFields(longScopeID,lFieldID,FieldName,FieldDesc,FieldSQLCash,k);
                    }
                    else
                    {
                    }
                }
                else
                {
                  Utility.logger.debug("Field hash map is null :: user_defined_data_view__R"+k+"__C4");
                }
             }            
                for(int h= 0; h<CurrentScopeFieldsVec.size(); h++)
                {
                    //int intyyParameterId = Integer.parseInt(yyParameterId.trim()); 
                    fieldsModel = (FieldsModel) CurrentScopeFieldsVec.get(h);
                    yyfieldId = fieldsModel.getFieldId();
                    //Long lParameterID = Integer.parseInt(xxParameterID.trim()); 
                    for(int i=1;i<=intParametersCount;i++)
                    {
                        if(paramHashMap.get("user_defined_data_view__R"+i+"__C4") != null)
                        {
                            String xxfieldID = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C6");
                            //int lParameterID = Integer.parseInt(xxParameterID.trim()); 
                            if(xxfieldID.equals(yyfieldId))
                            {
                                Utility.logger.debug("Field will be updated");
                                intFieldID = Integer.parseInt(xxfieldID.trim());
                                FieldName = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C1"); 
                                FieldDesc = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C2");
                                FieldSQLCash = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C3");
                                ItemPosition = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C5");
                                int intItemPosition = Integer.parseInt(ItemPosition.trim());
                                
                                ScopesDAO.updateScopeFields(intFieldID,FieldName,FieldDesc,FieldSQLCash,intItemPosition);
                                updated = true;
                            }
                           
                         }
                     }
                    if(updated == false)
                    {
                        Utility.logger.debug("Field will be deleted");
                        intFieldID = Integer.parseInt(yyfieldId.trim());
                        ScopesDAO.deleteScopeField(intFieldID);
                    }
                    updated = false;
                }
                
            }
            
            dataHashMap = new HashMap ();
            Vector scopesVector = ScopesDAO.getAllScopes();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , scopesVector);
            Vector scopesStatusVector = ScopesDAO.getAllScopeStatus();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , scopesStatusVector);
            Utility.closeConnection(conX);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case SAVE_SCOPE_STATUS:
          try                                                                                                 
          {
            Connection connectionX = Utility.getConnection();
            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              if(tempStatusString.startsWith("dataviewstatus"))
              {                               
                String dataViewId = tempStatusString.substring(14, tempStatusString.length());
                ScopesDAO.updateDataViewStatus(connectionX,dataViewId,keyValue);
              }              
            }
            dataHashMap = new HashMap ();
            Vector scopesVector = ScopesDAO.getAllScopes();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , scopesVector);
            Vector scopesStatusVector = ScopesDAO.getAllScopeStatus();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , scopesStatusVector);
            Utility.closeConnection(connectionX);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        
        case SHOW_ALL_FUNCTIONS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            Vector tablefunctionVector = DataImportTableFunctionsDAO.getAllTableFunctions();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , tablefunctionVector); 
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case ADD_FUNCTION:
          try                                                                                                 
          {
            CachEngine.removeObject(FunctionsInterfaceKey.CACH_OBJ_FUNCTION_LIST);
            dataHashMap = new HashMap ();
            Vector functionTypesVector = DataImportTableFunctionsDAO.getAllFunctionTypes();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , functionTypesVector);
            Vector functionStatusVector = DataImportTableFunctionsDAO.getAllFunctionStatus();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , functionStatusVector);
            String functionaction = "Add";
            dataHashMap.put( FunctionsInterfaceKey.FUNCTION_ACTION , functionaction);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case EDIT_FUNCTION:
          try                                                                                                 
          {
            CachEngine.removeObject(FunctionsInterfaceKey.CACH_OBJ_FUNCTION_LIST);
            dataHashMap = new HashMap ();
            Vector functionTypesVector = DataImportTableFunctionsDAO.getAllFunctionTypes();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , functionTypesVector);
            Vector functionStatusVector = DataImportTableFunctionsDAO.getAllFunctionStatus();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , functionStatusVector);

            String FunctionID = (String)paramHashMap.get(FunctionsInterfaceKey.CONTROL_HIDDEN_FUNCTION_ID);
            int intFunctionID = Integer.parseInt(FunctionID.trim());
            
            Vector functionVector = DataImportTableFunctionsDAO.getFunctionById(intFunctionID);
            dataHashMap.put( FunctionsInterfaceKey.HASHMAP_KEY_FUNCTION_COLLECTION , functionVector);
            Vector functionParameterVector = DataImportTableFunctionsDAO.getFunctionParameters(intFunctionID);
            dataHashMap.put( FunctionsInterfaceKey.HASHMAP_KEY_FUNCTION_PARAMETER_COLLECTION , functionParameterVector);
            String functionaction = "Edit";
            dataHashMap.put( FunctionsInterfaceKey.FUNCTION_ACTION , functionaction);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case SAVE_FUNCTION:
          try                                                                                                 
          {
            CachEngine.removeObject(FunctionsInterfaceKey.CACH_OBJ_FUNCTION_LIST );
            Connection conX = Utility.getConnection();
            Long lFunctionID = Utility.getSequenceNextVal(conX, "SEQ_ADH_FUNCTION_ID");
            //for(int j=0; j<paramHashMap.size(); j++)
            //{
            //  String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
            //  String keyValue = (String)paramHashMap.get(tempStatusString);
            //  Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
            //}
            int hashmapsize = paramHashMap.size();
            Utility.logger.debug("size of hash map : "+hashmapsize);

            String FunctionID = (String)paramHashMap.get(FunctionsInterfaceKey.CONTROL_HIDDEN_FUNCTION_ID);
            
            String FunctionName = (String)paramHashMap.get(FunctionsInterfaceKey.CONTROL_TEXTAREA_FUNCTION_NAME);
            String FunctionTypeId = (String)paramHashMap.get(FunctionsInterfaceKey.CONTROL_SELECT_FUNCTION_TYPE);
            int intFunctionTypeId = Integer.parseInt(FunctionTypeId.trim());
            String FunctionStatusId = (String)paramHashMap.get(FunctionsInterfaceKey.CONTROL_SELECT_FUNCTION_STATUS);
            int intFunctionStatusId = Integer.parseInt(FunctionStatusId.trim());
            String FunctionSQLCall = (String)paramHashMap.get(FunctionsInterfaceKey.CONTROL_TEXT_FUNCTION_SQL_CALL);
            String FunctionHelpText = (String)paramHashMap.get(FunctionsInterfaceKey.CONTROL_TEXTAREA_FUNCTION_HELP_TEXT);
            String FunctionDescription = (String)paramHashMap.get(FunctionsInterfaceKey.CONTROL_TEXTAREA_FUNCTION_DESCRIPTION);

            String ParametersCount = (String)paramHashMap.get("user_defined_data_view_count");
            int intParametersCount = Integer.parseInt(ParametersCount.trim());
                
            String functionaction = (String)paramHashMap.get("functionaction");

            if(functionaction.equals("Add"))
            {
                DataImportTableFunctionsDAO.insertFunction(lFunctionID,FunctionName,intFunctionTypeId,intFunctionStatusId,FunctionSQLCall,FunctionHelpText,FunctionDescription);
                for(int i=1;i<=intParametersCount;i++)
                {
                if(paramHashMap.get("user_defined_data_view__R"+i+"__C3") != null)
                    {
                   Long lParameterID = Utility.getSequenceNextVal(conX, "SEQ_ADH_PARAMETER_DEF_ID"); 
                   String ParameterName = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C1"); 
                   String ParameterDesc = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C2");
                   String addornot = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C3");
                   String ParameterID = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C4");
                   DataImportTableFunctionsDAO.insertFunctionParameters(lFunctionID,lParameterID,ParameterName,ParameterDesc);
                    }
                }
            }
            else if(functionaction.equals("Edit"))
            {
            int intFunctionID = Integer.parseInt(FunctionID.trim());
            DataImportTableFunctionsDAO.updateFunction(intFunctionID,FunctionName,intFunctionTypeId,intFunctionStatusId,FunctionSQLCall,FunctionHelpText,FunctionDescription);
            Vector CurrentfunParametersVec = DataImportTableFunctionsDAO.getFunctionParameters(intFunctionID);
            FunctionParameterModel functionParameterModel;  
            String yyParameterId = "";
            boolean updated = false;
            String zzParameterID = "";
            int intParameterID;
            Long lParameterID ;
            Long longFunctionID;
            String ParameterName;
            String ParameterDesc;
              
             for(int k=1;k<=intParametersCount;k++)
             {
                if(paramHashMap.get("user_defined_data_view__R"+k+"__C3") != null)
                {
                    zzParameterID = (String)paramHashMap.get("user_defined_data_view__R"+k+"__C4");
                    if(zzParameterID.equals(null) || zzParameterID.equals(""))
                    {
                       Utility.logger.debug("Parameter will be inserted");
                       lParameterID = Utility.getSequenceNextVal(conX, "SEQ_ADH_PARAMETER_DEF_ID"); 
                       longFunctionID = new Long(FunctionID);  
                       ParameterName = (String)paramHashMap.get("user_defined_data_view__R"+k+"__C1"); 
                       ParameterDesc = (String)paramHashMap.get("user_defined_data_view__R"+k+"__C2");
                       DataImportTableFunctionsDAO.insertFunctionParameters(longFunctionID,lParameterID,ParameterName,ParameterDesc);
                    }
                    else
                    {
                        Utility.logger.debug("Parameter id ::::::"+ zzParameterID);
                    }
                }
                else
                {
                  Utility.logger.debug("Parameter hash map is null :: user_defined_data_view__R"+k+"__C3");
                }
             }            
                for(int h= 0; h<CurrentfunParametersVec.size(); h++)
                {
                    //int intyyParameterId = Integer.parseInt(yyParameterId.trim()); 
                    functionParameterModel = (FunctionParameterModel) CurrentfunParametersVec.get(h);
                    yyParameterId = functionParameterModel.getParameterDefinitionId();
                    //Long lParameterID = Integer.parseInt(xxParameterID.trim()); 
                    for(int i=1;i<=intParametersCount;i++)
                    {
                        if(paramHashMap.get("user_defined_data_view__R"+i+"__C3") != null)
                        {
                            String xxParameterID = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C4");
                            //int lParameterID = Integer.parseInt(xxParameterID.trim()); 
                            if(xxParameterID.equals(yyParameterId))
                            {
                                Utility.logger.debug("Parameter will be updated");
                                intParameterID = Integer.parseInt(xxParameterID.trim());
                                ParameterName = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C1"); 
                                ParameterDesc = (String)paramHashMap.get("user_defined_data_view__R"+i+"__C2");
                                DataImportTableFunctionsDAO.updateFunctionParameters(intParameterID,ParameterName,ParameterDesc);
                                updated = true;
                            }
                           
                         }
                     }
                    if(updated == false)
                    {
                        Utility.logger.debug("Parameter will be deleted");
                        intParameterID = Integer.parseInt(yyParameterId.trim());
                        DataImportTableFunctionsDAO.deleteFunctionParameters(intParameterID);
                    }
                    updated = false;
                }
                
            }
            
            dataHashMap = new HashMap ();
            Vector tablefunctionVector = DataImportTableFunctionsDAO.getAllTableFunctions();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , tablefunctionVector); 
            
            Utility.closeConnection(conX);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;

        case SHOW_ALL_DATA_VIEWS:
          try                                                                                                 
          {
            dataHashMap = new HashMap ();
            Vector scopesVector = ScopesDAO.getAllDataViews();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , scopesVector);
            Vector scopeStatusVector = ScopesDAO.getAllScopeStatus();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , scopeStatusVector);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        case SAVE_DATA_VIEW_STATUS:
          try                                                                                                 
          {
            Connection connectionX = Utility.getConnection();
            dataHashMap = new HashMap ();
            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              if(tempStatusString.startsWith(ScopesInterfaceKey.CONTROL_SELECT_DATA_VIEW_STATUS))
              {                               
                String dataViewId = tempStatusString.substring(14, tempStatusString.length());
                String dataViewPreviousStatus = (String)paramHashMap.get(ScopesInterfaceKey.CONTROL_HIDDEN_DATA_VIEW_PREVIOUS_STATUS+dataViewId);
                String dataViewName = (String)paramHashMap.get(ScopesInterfaceKey.CONTROL_HIDDEN_DATA_VIEW_NAME+dataViewId);
                //Utility.logger.debug("Phase 1 DataView Id : "+dataViewId+" --DataView Previous Status Id : "+dataViewPreviousStatus+"  DataView New Status Id : "+keyValue);
                if(dataViewPreviousStatus.equalsIgnoreCase("1") && keyValue.equalsIgnoreCase("2")) 
                {
                  String strCheckReports = ScopesDAO.checkDataViewReportsChangeFromActive(connectionX,dataViewId);
                  //Utility.logger.debug("Phase 2 checking result = "+strCheckReports+" ---- DataView Id : "+dataViewId+" --DataView Previous Status Id : "+dataViewPreviousStatus+"  DataView New Status Id : "+keyValue);
                  if(strCheckReports.equalsIgnoreCase("Success"))
                  {
                    String strCheckDataviews = ScopesDAO.checkDataViewFrmChangeFromActive(connectionX,dataViewId);
                    //Utility.logger.debug("Phase 3 checking result = "+strCheckDataviews+" ---- DataView Id : "+dataViewId+" --DataView Previous Status Id : "+dataViewPreviousStatus+"  DataView New Status Id : "+keyValue);
                    if(strCheckDataviews.equalsIgnoreCase("Success"))
                    {
                       //Utility.logger.debug("Phase 4 DataView Id : "+dataViewId+" --DataView Previous Status Id : "+dataViewPreviousStatus+"  DataView New Status Id : "+keyValue);
                       ScopesDAO.updateDataViewStatus(connectionX,dataViewId,keyValue);
                    }
                    else 
                    {
                      strCheckDataviews = "Dataview&&"+dataViewName+strCheckDataviews;
                      dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,strCheckDataviews);  
                    }
                  }
                  else 
                  {
                    //Utility.logger.debug(strCheckReports);
                    strCheckReports = "report&&"+dataViewName+strCheckReports;
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,strCheckReports);
                  }
                }
                else if(dataViewPreviousStatus.equalsIgnoreCase("2") && keyValue.equalsIgnoreCase("1")) 
                {
                  String strCheckDataviewInactive = ScopesDAO.checkDataViewFrmChangeFromInActive(connectionX,dataViewId);
                  if(strCheckDataviewInactive.equalsIgnoreCase("Success"))
                  {
                     Utility.logger.debug("Phase 3 DataView Id : "+dataViewId+" --DataView Previous Status Id : "+dataViewPreviousStatus+"  DataView New Status Id : "+keyValue);
                     ScopesDAO.updateDataViewStatus(connectionX,dataViewId,keyValue);
                  }
                  else 
                  {
                    strCheckDataviewInactive = "Dataview&&"+dataViewName+strCheckDataviewInactive;
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,strCheckDataviewInactive);
                  }
                }
              }              
            }
            Vector scopesVector = ScopesDAO.getAllDataViews();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_COLLECTION , scopesVector);
            Vector scopeStatusVector = ScopesDAO.getAllScopeStatus();
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , scopeStatusVector);
            Utility.closeConnection(connectionX);
          }                                                                                                   
          catch(Exception objExp)                                                                             
          {                                     
            objExp.printStackTrace();
          }
        break;
        
        default:
          Utility.logger.debug ("Unknown action received: " + action );
      }
    }
    catch(Exception objExp)
    {
      objExp.printStackTrace();
    }
    return dataHashMap;
  }
}
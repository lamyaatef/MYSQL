package com.mobinil.sds.core.system.gn.querybuilder.domain;
import java.util.Vector;
import java.sql.*;
import java.util.HashMap;


import com.mobinil.sds.core.utilities.Utility;

import com.mobinil.sds.core.system.gn.querybuilder.dao.* ;
import com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.*;
import com.mobinil.sds.core.system.gn.querybuilder.dto.*;
import com.mobinil.sds.core.system.gn.querybuilder.model.*;

import com.mobinil.sds.core.system.gn.dataview.dao.* ;
import com.mobinil.sds.core.system.gn.dataview.dto.*;
import com.mobinil.sds.core.system.gn.dataview.model.*;
import com.mobinil.sds.core.system.gn.dataview.dao.cmp.*;

import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.gn.querybuilder.QueryBuilderInterfaceKey;


public class QueryBuilderEngine 
{

    //Connection m_conSDSConnection ; 
    HashMap dataHashMap ;

    //---------------------------------------------------------------
    public QueryBuilderEngine()
    {
      try
      {
        //m_conSDSConnection = Utility.getConnection();
        dataHashMap = new HashMap() ;
      }
      catch(Exception objExp)                                                                             
      {
        objExp.printStackTrace();
      }
    }


    private void debug(String s)
    {
      if (QueryBuilderInterfaceKey.DEBUG_FLAG)
      {
        Utility.logger.debug(s);
      }
    }
    //---------------------------------------------------------------
    public void debug ( String place, String msg )
    {
      debug ( place, msg, true ) ;
    }

    public void debug ( String place, String msg, boolean EOF )
    {
      if (QueryBuilderInterfaceKey.DEBUG_FLAG == true)
      {
        System.out.print ( "  " ) ;
        if ( (place != null) && (! (place.equalsIgnoreCase(""))) ) System.out.print( place + ": " ) ;
        System.out.print ( msg ) ;
        if (EOF==true) Utility.logger.debug ("") ;
      }
    }

    //---------------------------------------------------------------
    //---------------- first scenario -------------------------------
    //---------------------------------------------------------------
    public HashMap initializeWizard (Connection m_conSDSConnection, String strUniverseType )
    {
      QueryBuilderWizardDTO queryBuilderWizardDTO = null ;
      Vector vecScopesList = null ;
      Vector vecNonScopesList = null ;
      try
      {

        //String strDataViewUniverseID = (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_INITIALIZE_WIZARD_UNIVERSE)  ;


        debug("initializeWizard", "strDataViewUniverseID: " + strUniverseType);

        
        
        int nDataViewUniverseID =
           (DataViewDAO.switchUniverseTypeToID ( m_conSDSConnection, strUniverseType)).intValue() ; 

        //Utility.logger.debug("initializeWizard strDataViewUniverseID: " + strUniverseType + nDataViewUniverseID);
        
        vecScopesList = 
        DataViewDAO.getDataViewsList ( m_conSDSConnection, 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE , 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE,
            nDataViewUniverseID ) ;

        vecNonScopesList = 
        DataViewDAO.getDataViewsList ( m_conSDSConnection, 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY , 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE,
            nDataViewUniverseID ) ;

        dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecScopesList);
        dataHashMap.put (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecNonScopesList);

        queryBuilderWizardDTO = new QueryBuilderWizardDTO() ;

        queryBuilderWizardDTO.setFieldTypes ( QueryBuilderDAO.getFieldTypesList(m_conSDSConnection) );
        queryBuilderWizardDTO.setFieldDisplayTypes ( QueryBuilderDAO.getFieldDisplayTypesList(m_conSDSConnection) );
        queryBuilderWizardDTO.setConditionElementTypes ( QueryBuilderDAO.getConditionElementTypesList(m_conSDSConnection) );
        queryBuilderWizardDTO.setTermOperators ( QueryBuilderDAO.getTermOperatorsList(m_conSDSConnection) );
        queryBuilderWizardDTO.setTermTypes ( QueryBuilderDAO.getTermTypesList(m_conSDSConnection) );
        queryBuilderWizardDTO.setRelationSymbols( QueryBuilderDAO.getRelationSymbolsList(m_conSDSConnection) );
        queryBuilderWizardDTO.setFunctions(QueryBuilderDAO.getFunctionsList(m_conSDSConnection));

        //queryBuilderWizardDTO.setQuery(queryDTO);

        dataHashMap.put (InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryBuilderWizardDTO) ;
      }
      catch(Exception objExp)                                                                             
      {                                     
        objExp.printStackTrace();
      }
      return dataHashMap ;
    }


    //---------------------------------------------------------------
    //--------------------- second scenario -------------------------
    //---------------------------------------------------------------
    public QueryDTO initializeQueryBuilder (Connection m_conSDSConnection,
              String argQueryName, String argQueryDescription, 
              Vector argSelectedDataViews)
    {
      QueryDTO queryDTO = null;
      try
      {
        queryDTO = new QueryDTO();
        queryDTO.setQueryDescription(argQueryDescription);
        queryDTO.setQueryName(argQueryName);


        HashMap hMapDetailedDataViews = constructInitialQueryDTO (m_conSDSConnection,argQueryName, argQueryDescription, argSelectedDataViews) ;


        queryDTO.setDetailedDataViews(hMapDetailedDataViews);
        queryDTO.setSelectedDataViews(argSelectedDataViews);


        //queryBuilderWizardDTO.setQuery(queryDTO);

        //dataviewsdetailsdto = getDataViewsDetails(argSelectedDataViews);
        /*dataHashMap.put (InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryBuilderWizardDTO);*/

      }
      catch(Exception objExp)                                                                             
      {                                     
        objExp.printStackTrace();
      }
      return queryDTO;
    }

    //---------------------------------------------------------------
    private DetailedDataViewDTO getDataViewDetails (Connection m_conSDSConnection,int argDataViewID)
    {
      DetailedDataViewDTO detailedDataViewDTO ;

      detailedDataViewDTO = DataViewDAO.getDataViewDetails (m_conSDSConnection, argDataViewID ) ;
      
      return detailedDataViewDTO ;
    }
    
    //---------------------------------------------------------------
    private HashMap constructInitialQueryDTO (
            Connection m_conSDSConnection,
            String argQueryName, String argQueryDescription, 
            Vector argSelectedDataViews)
    {
      int nDataViewID ;
      HashMap hMapDetailedDataViews = new HashMap(); 

      //Utility.logger.debug ( " size 1 ----> " + argSelectedDataViews.size() );
      for (int i=0 ; i < argSelectedDataViews.size(); i++ )
      {
        DetailedDataViewDTO detailedDataViewDTO ;
        nDataViewID = ((Integer)argSelectedDataViews.elementAt(i)).intValue() ;
        detailedDataViewDTO = getDataViewDetails(m_conSDSConnection,nDataViewID) ;

        
        Vector fields = DataViewDAO.getDataViewFieldsListByDisplayType(m_conSDSConnection, nDataViewID, QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED) ;
        detailedDataViewDTO.setDataViewFields( fields );
        
        hMapDetailedDataViews.put (new Integer ( nDataViewID ) , detailedDataViewDTO ) ;
      }
      
      return hMapDetailedDataViews;
    }


    //---------------------------------------------------------------
    private HashMap constructQueryDTOFromList ( Connection m_conSDSConnection,HashMap paramHashMap )
    {
        Integer nNumParam; 
        String paramID ;
        String paramName ;
        String paramType ;

        HashMap hMapDetailedDataViews = null ; 

      try
      {
        hMapDetailedDataViews = new HashMap(); 
        // --------------------- constructing from list -----------------------
        nNumParam = new Integer ( (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NUM) ) ;
        for (int i=0 ; i < nNumParam.intValue() ; i++)
        {
          DetailedDataViewDTO detailedDataViewDTO = new DetailedDataViewDTO ();
          paramID   = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_IDS + i ) ;
          paramName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_NAMES + i ) ;
          paramType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_DATAVIEWS_TYPE_IDS + i ) ;

          detailedDataViewDTO.setDataViewID ( new Integer ( (String) paramHashMap.get (paramID) ).intValue() );
          detailedDataViewDTO.setDataViewName ( (String) paramHashMap.get (paramName)  );
          detailedDataViewDTO.setDataViewTypeID( new Integer ( (String) paramHashMap.get (paramType) ).intValue() );
          detailedDataViewDTO.setIndex( i );
          if ( detailedDataViewDTO.getDataViewTypeID() != QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE ) 
          {
            Vector vec =new Vector();
            QueryDTO subQueryDTO = loadQueryDTO ( m_conSDSConnection,detailedDataViewDTO.getDataViewID() ,vec) ;
            String subQuerySQL = constructQuerySQL(subQueryDTO) ;
            detailedDataViewDTO.setDataViewOverrideSQL (subQuerySQL) ;
          }
          hMapDetailedDataViews.put (new Integer ( i ) , detailedDataViewDTO ) ;
        }
      }
      catch (Exception objExp)
      {
        objExp.printStackTrace();
      }
      return hMapDetailedDataViews ;
    }

    //---------------------------------------------------------------
    private Vector constructQueryDTOConstantsList ( Connection m_conSDSConnection,HashMap paramHashMap )
    {
        Integer nNumParam; 
        String paramID ;
        String paramName ;
        String paramType ;
        String paramValue ;
        String constValueType ;
        String paramDesc ;
        Vector vecConstants = null;
      try
      {
        // --------------------- constructing constants ---------------------
        String strConstNum = (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NUM) ;
        if (strConstNum != null)
        {
          nNumParam = new Integer ( strConstNum ) ;
          vecConstants = new Vector () ;
          ConstantFieldDTO constantFieldDTO = null ;
          for (int i=0 ; i < nNumParam.intValue() ; i++)
          {
            //Utility.logger.debug ("constructing const number " + i) ;
            constantFieldDTO = new ConstantFieldDTO () ;

            paramName  = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_NAME + i )  ;
            paramType  = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE + i )  ;
            paramValue = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_VALUE + i )  ;
            constValueType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE + i )  ;
            paramDesc  = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_DESC + i )  ;


            constantFieldDTO.setFieldName ( (String) paramHashMap.get (paramName) ) ;
            constantFieldDTO.setDataType( new Integer ( (String) paramHashMap.get (paramType) ).intValue() );
            constantFieldDTO.setFieldDescription( (String) paramHashMap.get (paramDesc) );
            debug ("constructQueryDTOConstantsList", "const desc: " + constantFieldDTO.getFieldDescription());
            
            int nConstType = new Integer ( (String) paramHashMap.get (constValueType) ).intValue() ;
            
            //System.out.println("constant type ="+ nConstType);
            //System.out.println("string value="+ (String) paramHashMap.get (paramValue) );
            
            if (nConstType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_NUMERIC)
            {
                //System.out.println("numberic value="+ (String) paramHashMap.get (paramValue) );
                
              constantFieldDTO.setFieldSQLCash ( (String) paramHashMap.get (paramValue)) ;
            }
            else if (nConstType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_TEXT)
            {
               // System.out.println("string value="+ (String) paramHashMap.get (paramValue) );
                //System.out.println("\'" + (String) paramHashMap.get (paramValue) + "\'" );
                
              constantFieldDTO.setFieldSQLCash ( "\'" + (String) paramHashMap.get (paramValue) + "\'" ) ;
            }
            else if (nConstType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_DATE)
            {
           // System.out.println("date value" + (String) paramHashMap.get (paramValue)  );
              constantFieldDTO.setFieldSQLCash ( "to_date ( \'" + 
                    (String) paramHashMap.get (paramValue) + "\', \'" + 
                    QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_DATE_FORMATTING 
                    + "\')  " ) ;
            }

            vecConstants.addElement(constantFieldDTO);
            //Utility.logger.debug ("added const number " + i) ;
          }
        }
      }
      catch (Exception objExp)
      {
        objExp.printStackTrace();
      }
      return vecConstants ;
    }

    //---------------------------------------------------------------
    private Vector constructQueryDTOParametersList ( HashMap paramHashMap )
    {
      Integer nParamNum;
      Vector  vecParameters = null;
      String  paramName ;
      String  paramValue ;
      String  paramLabel ;
      String  paramValueType ;
      String  paramDesc ;
      String  paramCtrlType ;

      try
      {
        // --------------------- constructing parameters ---------------------

        String strParamNum = (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAMS_NUM) ;
        if (strParamNum != null)
        {
          nParamNum = new Integer ( strParamNum ) ;
          vecParameters = new Vector () ;
          ParameterFieldDTO parameterFieldDTO = null ;
          for (int i=0 ; i < nParamNum.intValue() ; i++)
          {
            parameterFieldDTO = new ParameterFieldDTO () ;

            paramName  = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_NAME + i )  ;
            paramValue = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_VALUE + i )  ;
            paramLabel  = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_LABEL + i )  ;
            paramDesc  = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_DESC + i )  ;
            paramCtrlType  = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_CONTROL_TYPE_ID + i )  ;
            paramValueType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE + i )  ;

            parameterFieldDTO.setFieldID ( i ) ;
            parameterFieldDTO.setFieldName ( (String) paramHashMap.get (paramName) ) ;
            parameterFieldDTO.setFieldDescription( (String) paramHashMap.get (paramDesc) );
            parameterFieldDTO.setLabelText( (String) paramHashMap.get (paramLabel) );
            parameterFieldDTO.setControlTypeID(new Integer ( (String) paramHashMap.get (paramCtrlType) ).intValue() );
            parameterFieldDTO.setValueType( new Integer ( (String) paramHashMap.get (paramValueType) ).intValue() );
            parameterFieldDTO.setOrder( i );
            //FieldDisplayTypeModel fieldDisplayTypeModel = new FieldDisplayTypeModel () ;
            //fieldDisplayTypeModel.setFieldDisplayTypeID();
            //parameterFieldDTO.setFieldDisplayType( fieldDisplayTypeModel ); 

            FieldTypeModel fieldTypeModel = new FieldTypeModel () ;
            fieldTypeModel.setFieldTypeID( QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD ) ; 
            parameterFieldDTO.setFieldType( fieldTypeModel );

            int nParamType = new Integer ( (String) paramHashMap.get (paramValueType) ).intValue() ;
            if (nParamType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_NUMERIC)
            {
              parameterFieldDTO.setFieldSQLCash ( (String) paramHashMap.get (paramValue)) ;
            }
            else if (nParamType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_TEXT)
            {
              parameterFieldDTO.setFieldSQLCash ( "\'" + (String) paramHashMap.get (paramValue) + "\'" ) ;
            }
            else if (nParamType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_DATE)
            {
              parameterFieldDTO.setFieldSQLCash ( "to_date ( \'" + 
                    (String) paramHashMap.get (paramValue) + "\', \'" + 
                    QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_DATE_FORMATTING 
                    + "\')  " ) ;
            }

            vecParameters.addElement(parameterFieldDTO);
            //Utility.logger.debug ("added query parameter number " + i) ;
          }
        }
      }
      catch (Exception objExp)
      {
        objExp.printStackTrace();
      }
      return vecParameters ;
    }

    //---------------------------------------------------------------
    private Vector constructQueryDTOFunctionsList ( 
                  HashMap   paramHashMap ,
                  Vector    vecConstants ,
                  Vector    vecParameters )
    {
      Vector vecFunctions = null ;
      Vector vecFuncParamValues = null ;
      try
      {
        // --------------------- constructing functions -----------------------
        String strFuncNum = (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELDS_NUM) ;
        Utility.logger.debug ( "  strFuncNum: " + strFuncNum ) ;
        if (strFuncNum != null)
        {
          FunctionFieldDTO functionFieldDTO = null ;
          FunctionDTO functionDTO = null ;
          FunctionParameterValueModel funcParamValue = null ;
          Integer nFuncNum = null ;
          Integer nParamNum = null ;
          int funcID ;

          nFuncNum = new Integer ( strFuncNum ) ;
          vecFunctions = new Vector () ;
          for (int i=0 ; i < nFuncNum.intValue() ; i++)
          {
            functionFieldDTO = new FunctionFieldDTO () ;
            functionDTO = new FunctionDTO () ;
            
           
            functionFieldDTO.setFieldName( (String) paramHashMap.get( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i) );
            Utility.logger.debug(functionFieldDTO.getFieldName());
            //functionFieldDTO.setFieldDescription();
            functionDTO.setFunctionID( new Integer ((String) paramHashMap.get( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_ID + i)).intValue() );
            functionDTO.setFunctionSQL( (String) paramHashMap.get( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_SQLCALL + i) );

            FieldTypeModel fieldTypeModel = new FieldTypeModel () ;
            fieldTypeModel.setFieldTypeID( QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD ) ; 

            functionFieldDTO.setFieldType( fieldTypeModel );
            functionFieldDTO.setFunctionDTO(functionDTO);
            functionFieldDTO.setFieldID (i) ;
            
            // --------------- getting parameters for the function 
            StringBuffer strFuncSQLCache = new StringBuffer (functionDTO.getFunctionSQL() ) ;
            String strParamSQLCache = null ;
            String strParamName = null ;
            String strParamNum = (String) paramHashMap.get(
                  QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETERS_NUM + i) ;
            Utility.logger.debug ( "  ---> parameters number : " + strParamNum ) ; 
            

            if (strParamNum != null)
            {
              vecFuncParamValues = new Vector () ;
              nParamNum = new Integer ( strParamNum ) ;
              if (nParamNum.intValue()>0) strFuncSQLCache.append(" ( ") ; 
              for (int j=0 ; j < nParamNum.intValue() ; j++)
              {
                funcParamValue = new FunctionParameterValueModel () ;

                // is this code valid in case of const param and function param
              //  Utility.logger.debug("here is the problem ");
            //    Utility.logger.debug(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
  //                + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID + j);
                  
                funcParamValue.setParameterDefinitionID( new Integer (
                (String) paramHashMap.get(
                  QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                  + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID + j) )
                  .intValue() );
                
                Utility.logger.debug ( "  ---> parameter key: " + 
                      QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                      + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE + j 
                    ) ;
                Utility.logger.debug ( "  ---> parameter value: " + 
                    (String) paramHashMap.get(
                      QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                      + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE + j) 
                    ) ;

                int nParamType = new Integer (
                (String) paramHashMap.get(
                  QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                  + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_TYPE + j) ).intValue();

                Utility.logger.debug("************************");
                Utility.logger.debug("nparam type = " + nParamType);
                Utility.logger.debug("************************");
                
                funcParamValue.setParameterValueFieldType(nParamType);
                // according to the type certain keys will be read
                if (nParamType == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
                {
                  strParamName = 
                      (String) paramHashMap.get(
                      QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                      + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_NAME + j) ; 

                  strParamSQLCache = 
                      "\"" +
                      (String) paramHashMap.get(
                      QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                      + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_NAME + j)
                      + "\".\"" +
                      (String) paramHashMap.get(
                      QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                      + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_NAME + j) 
                      + "\""
                      ; 

                  //strParamSQLCache = strParamName ;
                  
                  funcParamValue.setParameterValueDataViewID(
                      new Integer ( (String) paramHashMap.get(
                      QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                      + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_ID + j) ).intValue()
                      );
                  funcParamValue.setParameterValueDataViewName(
                      (String) paramHashMap.get(
                      QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                      + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_NAME + j)
                      );
                  funcParamValue.setParameterValueDataViewType(
                      new Integer ( (String) paramHashMap.get(
                      QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                      + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_DATAVIEW_TYPE + j) ).intValue()
                      );


                  int nParamID = new Integer ( (String) paramHashMap.get(
                    QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                    + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_FIELD_ID + j) ).intValue() ;

                  funcParamValue.setParameterValueID(nParamID);



                  /*funcParamValue.setParameterDefinitionID( new Integer (
                  (String) paramHashMap.get(
                    QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                    + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_ID + j) )
                    .intValue() );*/
                }
                else if (nParamType == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
                {
                  int nParamID = new Integer ( (String) paramHashMap.get(
                    QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                    + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX + j) ).intValue() ;

                  funcParamValue.setParameterValueID(nParamID);

                  // get the const name from the list of constants
                  /*strParamName =
                      ( (ConstantFieldDTO) queryDTO.getConstantsFields().elementAt(nParamID) ).getFieldName() ; 
                  String strParamValue = 
                      ( (ConstantFieldDTO) queryDTO.getConstantsFields().elementAt(nParamID) ).getFieldSQLCash() ;*/ 
                  strParamName =
                      ( (ConstantFieldDTO) vecConstants.elementAt(nParamID) ).getFieldName() ; 
                  String strParamValue = 
                      ( (ConstantFieldDTO) vecConstants.elementAt(nParamID) ).getFieldSQLCash() ; 

                  //funcParamValue.setParameterValuePlace(nParamID); 
                  
                  strParamSQLCache = strParamValue ;

/*
                  String strParamValue = (String) paramHashMap.get(
                    QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                    + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_VALUE + j) ;
                    */
                  //if (j!=0) strFuncSQLCache.append (", ");
                  //strFuncSQLCache.append( ( strParamSQLCache ) );
                }
                else if (nParamType == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
                {
                  // this represents the function index -- typically a previous function in the list
                  int nParamID = new Integer ( (String) paramHashMap.get(
                    QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                    + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX + j) ).intValue() ;

                  //funcParamValue.setParameterValuePlace(nParamID); 

                  funcParamValue.setParameterValueID(nParamID);

                  strParamName =
                      ( (FunctionFieldDTO) vecFunctions.elementAt(nParamID)).getFieldName() ;

                  strParamSQLCache = ((FunctionFieldDTO) vecFunctions.elementAt(nParamID)).getFieldSQLCash() ;

                  Utility.logger.debug ( "  nested function SQL: " + strFuncSQLCache ) ;
                }
                else if (nParamType == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
                {
                  int nParamID = new Integer ( (String) paramHashMap.get(
                    QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_NAME + i
                    + QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FUNCTION_FIELD_PARAMETER_INDEX + j) ).intValue() ;

                  //funcParamValue.setParameterValuePlace(nParamID); 

                  funcParamValue.setParameterValueID(nParamID);

                  strParamName =
                      ( (ParameterFieldDTO) vecParameters.elementAt(nParamID)).getFieldName() ;

                  strParamSQLCache = ((ParameterFieldDTO) vecParameters.elementAt(nParamID)).getFieldSQLCash() ;

                  Utility.logger.debug ( "  Parameter SQL: " + strParamSQLCache ) ;
                }

                debug ( "constructQueryDTOFunctionsList:040701", "funcParamValue.getParameterValueID(): " + funcParamValue.getParameterValueID() );

                //funcParamValue.setParameterValueID();
                //funcParamValue.setParameterDefinitionID();
                //funcParamValue.setLinkFieldID();
                //funcParamValue.setValueFieldID();
                //funcParamValue.setParameterValuePlace(j);

                funcParamValue.setParameterValueSQLCache(strParamSQLCache); 
                if (j!=0) 
                  strFuncSQLCache.append (", ");
                strFuncSQLCache.append ( strParamSQLCache );
                funcParamValue.setParameterValueName ( strParamName ) ;
                Utility.logger.debug ( "  got parameter name: " + funcParamValue.getParameterValueName() ) ;
                vecFuncParamValues.addElement(funcParamValue);
              }
            }
            //Utility.logger.debug ( "---->" + strFuncSQLCache ) ;
            if (nParamNum.intValue()>0) strFuncSQLCache.append(" ) ") ; 
            functionFieldDTO.setFieldSQLCash( strFuncSQLCache.toString() );
            functionFieldDTO.setFunctionParameterValues(vecFuncParamValues);
            vecFunctions.addElement(functionFieldDTO);
            //Utility.logger.debug ("added function field number " + i) ;
          }
          //queryDTO.setFunctionsFields(vecFunctions);
        }
      
      }
      catch (Exception objExp)
      {
        objExp.printStackTrace();
      }
      return vecFunctions ;
    }

    //---------------------------------------------------------------
    private Vector constructQueryDTODisplayedFields ( 
                  HashMap paramHashMap, 
                  //Vector  vecConstants,
                  Vector  vecFunctions,
                  Vector  vecParameters )
    {
      Vector fields = null ;
      Integer nNumParam; 
      String paramID ;
      String paramName ;
      String paramType ;
      String paramValue ;
      String paramDesc ;
      String paramAlias ;
      String paramDataViewName ;
      String paramDataViewID ;
      String paramDataViewType ;
      String constValueType ;

      try
      {

        // --------------------- constructing displayed fields -----------------------
        fields = new Vector () ;
        nNumParam = new Integer ( (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NUM) ) ;
        for (int i=0 ; i < nNumParam.intValue() ; i++)
        {
          FieldTypeModel fieldTypeModel = new FieldTypeModel ();
          int fieldTypeID ;


          paramID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_IDS + i) ;
          paramName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_NAMES + i) ;
          paramValue = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_VALUES + i) ;
          paramType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_TYPES + i) ;
          paramDesc = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DESCRIPS + i) ;
          //paramUnique = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_UNIQUES + i) ;
          paramAlias = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_ALIASES + i) ;
          paramDataViewName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_NAMES + i) ;
          paramDataViewID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_IDS + i) ;
          paramDataViewType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_SELECTED_FIELDS_DATAVIEWS_TYPES + i) ;

          fieldTypeID = (new Integer ((String)paramHashMap.get (paramType) ) ).intValue() ;
          fieldTypeModel.setFieldTypeID (fieldTypeID) ;
          if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
          {
            DataViewFieldDTO dataViewFieldDTO = new DataViewFieldDTO () ;
            dataViewFieldDTO.setFieldID ( new Integer ((String) paramHashMap.get (paramID) ).intValue() );
            dataViewFieldDTO.setFieldType( fieldTypeModel );
            String strAlias = (String) paramHashMap.get (paramAlias) ;
            if (strAlias.equalsIgnoreCase("") )
            {
              // there is no alias
              dataViewFieldDTO.setFieldName ( (String) paramHashMap.get (paramName)  );
            }
            else
            {
              dataViewFieldDTO.setFieldName ( strAlias );
            }

            String strSQLCache = null ;

            String begin    = new String ("\"") ;
            String middle   = new String ("\".\"") ;
            String end      = new String ("\"") ;

            strSQLCache = new String ( begin +
                  (String) paramHashMap.get (paramDataViewName) + middle + 
                  (String) paramHashMap.get (paramName) + end ) ;

            dataViewFieldDTO.setFieldSQLCash ( strSQLCache ) ; 
            dataViewFieldDTO.setFieldDescription((String) paramHashMap.get (paramDesc));
            dataViewFieldDTO.setParentDataViewID((new Integer ((String)paramHashMap.get (paramDataViewID))).intValue() );
            dataViewFieldDTO.setParentDataViewName( (String)paramHashMap.get ( paramDataViewName ) );
            fields.addElement ( dataViewFieldDTO ) ;
          }
          else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
          {
            ConstantFieldDTO constantFieldDTO = new ConstantFieldDTO () ;
            
            constantFieldDTO.setFieldID ( (new Integer ( (String) paramHashMap.get (paramID) )).intValue() );
            constantFieldDTO.setFieldType( fieldTypeModel );
            String strAlias = (String) paramHashMap.get (paramAlias) ;
            if (strAlias.equalsIgnoreCase("") )
            {
              // there is no alias
              constantFieldDTO.setFieldName ( (String) paramHashMap.get (paramName)  );
            }
            else
            {
              constantFieldDTO.setFieldName ( strAlias );
            }

            constValueType  = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE 
                    + constantFieldDTO.getFieldID()) ;
            int nConstType = new Integer ( (String) paramHashMap.get (constValueType) ).intValue() ;
            if (nConstType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_NUMERIC)
            {
              constantFieldDTO.setFieldSQLCash ( (String) paramHashMap.get (paramValue)) ;
            }
            else if (nConstType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_TEXT)
            {
              constantFieldDTO.setFieldSQLCash ( "\'" + (String) paramHashMap.get (paramValue) + "\'" ) ;
            }
            else if (nConstType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_DATE)
            {
              constantFieldDTO.setFieldSQLCash ( "to_date ( \'" + 
                    (String) paramHashMap.get (paramValue) + "\', \'" + 
                    QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_DATE_FORMATTING 
                    + "\')  " ) ;
            }

            constantFieldDTO.setFieldDescription((String) paramHashMap.get (paramDesc));
            fields.addElement ( constantFieldDTO ) ;
          }
          else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
          {
            FunctionFieldDTO functionFieldDTO = null ;//new FunctionFieldDTO () ;
            int nParamID = new Integer ( (String) paramHashMap.get (paramID) ).intValue() ; // index of function in functions list
            String strAlias = (String) paramHashMap.get (paramAlias) ;
            functionFieldDTO = (FunctionFieldDTO) ((FunctionFieldDTO) vecFunctions.elementAt(nParamID)).clone() ;
            //String strSQLCache = functionFieldDTO.getFieldSQLCash () ;
            //Utility.logger.debug ( strSQLCache ) ;
            //functionFieldDTO.setFieldSQLCash ( strSQLCache );
              if (strAlias.equalsIgnoreCase("") )
              {
              // there is no alias            
              }
              else
              {
                functionFieldDTO.setFieldName ( strAlias );
              }

            fields.addElement ( functionFieldDTO ) ;
          }
          else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
          {
            ParameterFieldDTO parameterFieldDTO = null ;//new FunctionFieldDTO () ;
            int nParamID = new Integer ( (String) paramHashMap.get (paramID) ).intValue() ; // index of function in functions list

            parameterFieldDTO = (ParameterFieldDTO) ((ParameterFieldDTO) vecParameters.elementAt(nParamID)).clone() ;
            //String strSQLCache = functionFieldDTO.getFieldSQLCash () ;
            //Utility.logger.debug ( strSQLCache ) ;
            //functionFieldDTO.setFieldSQLCash ( strSQLCache );
            fields.addElement ( parameterFieldDTO ) ;
          }
        }
      }
      catch (Exception objExp)
      {
        objExp.printStackTrace();
      }
      return fields ;
    }

    //---------------------------------------------------------------
    private Vector constructQueryDTOTermsList ( 
              Connection m_conSDSConnection,
              HashMap paramHashMap,
              Vector  vecConstants,
              Vector  vecParameters,
              Vector  vecFunctions)
    {
        // --------------------- constructing terms -----------------------
        Integer nNumParam; 
        String termName ;
        String field1ID ;
        String field1Name ;
        String field1Type ;
        String field1Value ;
        String field1ParentDataViewName;
        String field1ParentDataViewID;
        String field1ParentDataViewType;
        String field2ID ;
        String field2Name ;
        String field2Type ;
        String field2Value ;
        String field2ParentDataViewName;
        String field2ParentDataViewID;
        String field2ParentDataViewType; 
        String dataViewID ;
        String dataViewName ;
        String dataViewType ;
        String operatorID ;
        String operatorName ;
        String operatorSQL ;

        String strSQLCache = null ;
        int fieldParentDataViewType ; 
        Vector vecTerms = null ;
        TermTypeDTO termTypeDTO = null ;

      try
      {

        vecTerms = new Vector ();
        termTypeDTO = new TermTypeDTO ();
        String strFieldToFieldNum = (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_NUM) ;
        //Utility.logger.debug ( "------->strFieldToFieldNum: " + strFieldToFieldNum ) ;
        if (strFieldToFieldNum != null)
        {
          nNumParam = new Integer ( strFieldToFieldNum ) ;
          for (int i=0 ; i < nNumParam.intValue() ; i++)
          {
            FieldToFieldTermDTO fieldToFieldTermDTO = new FieldToFieldTermDTO() ;
            termName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TERM_NAME + i ) ;
            field1ID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_ID + i ) ;
            field1Name = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_NAME + i ) ;
            field1Type = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_TYPE + i ) ;
            field1Value = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_VALUE + i ) ;
            field1ParentDataViewName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_NAME + i ) ;
            field1ParentDataViewID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_ID + i ) ;
            field1ParentDataViewType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_1ST_FIELD_DATAVIEW_TYPE + i ) ;
            field2ID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_ID + i ) ;
            field2Name = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_NAME + i ) ;
            field2Type = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_TYPE + i ) ;
            field2Value = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_VALUE + i ) ;
            //constValueType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_VALUE + i ) ;
            field2ParentDataViewName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_NAME + i ) ;
            field2ParentDataViewID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_ID + i ) ;
            field2ParentDataViewType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_2ND_FIELD_DATAVIEW_TYPE + i ) ;
            operatorID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_ID + i ) ;
            operatorName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_NAME + i ) ;
            operatorSQL = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_OPERATOR_SQL + i ) ;
          
            fieldToFieldTermDTO.setTermSQLCache( (String) paramHashMap.get (termName) ); 
            debug ( "constructQueryDTOTermsList" , fieldToFieldTermDTO.getTermSQLCache() ) ;

            termTypeDTO.setTermTypeID (QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE);
            fieldToFieldTermDTO.setTermType (termTypeDTO);
          
            fieldToFieldTermDTO.set1stOperandFieldID(new Integer ( (String) paramHashMap.get (field1ID) ).intValue() );
            fieldToFieldTermDTO.set1stOperandFieldName( (String) paramHashMap.get (field1Name) ) ;
            int nField1Type = new Integer ( (String) paramHashMap.get (field1Type) ).intValue() ;
            fieldToFieldTermDTO.set1stOperandFieldType (nField1Type);

            if ( nField1Type == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD)
            {
              //Utility.logger.debug ( field1ParentDataViewID + "-------->"+ (String) paramHashMap.get (field1ParentDataViewID) );
              //Utility.logger.debug ( field1ParentDataViewName + "-------->"+ (String) paramHashMap.get (field1ParentDataViewName) );
              fieldToFieldTermDTO.set1stOperandFieldParentDataViewID (new Integer ( (String) paramHashMap.get (field1ParentDataViewID) ).intValue() );
            
              String begin    = new String ("\"") ;
              String middle   = new String ("\".\"") ;
              String end      = new String ("\"") ;

              strSQLCache = new String ( begin +
                    (String) paramHashMap.get (field1ParentDataViewName) + middle + 
                    (String) paramHashMap.get (field1Name) + end ) ;
              fieldToFieldTermDTO.set1stOperandFieldSQLCache( strSQLCache ) ; 

              //Utility.logger.debug ( "  get1stOperandFieldSQLCache() " + fieldToFieldTermDTO.get1stOperandFieldSQLCache() ) ;
              //Utility.logger.debug ( "  field1ParentDataViewName: " + (String) paramHashMap.get (field1ParentDataViewName ) ) ; 
              //Utility.logger.debug ( "  field1Name: " + (String) paramHashMap.get (field1Name) ) ;
            }
            //Utility.logger.debug ("1--->" + (String) paramHashMap.get (field1Value) ) ;
            else if ( nField1Type == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD)
            {
              fieldToFieldTermDTO.set1stOperandFieldID ( new Integer ( (String) paramHashMap.get (field1ID) ).intValue() );
              ConstantFieldDTO constantFieldDTO = (ConstantFieldDTO) vecConstants.elementAt(fieldToFieldTermDTO.get1stOperandFieldID() ) ; 
              fieldToFieldTermDTO.set1stOperandFieldSQLCache( constantFieldDTO.getFieldSQLCash() );
              //Utility.logger.debug (" fieldToFieldTermDTO.get2ndOperandFieldSQLCache( ) -->" + fieldToFieldTermDTO.get1stOperandFieldSQLCache( ) ) ;
            }
            else if ( nField1Type == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
            {
              FunctionFieldDTO functionFieldDTO = null ;//new FunctionFieldDTO () ;
              int nField1ID = new Integer ( (String) paramHashMap.get (field1ID) ).intValue() ; // index of function in functions list
              //fieldToFieldTermDTO.set1stOperandFieldID ( nField1ID ) ; 

              functionFieldDTO = (FunctionFieldDTO) vecFunctions.elementAt(nField1ID) ;
              //strSQLCache = functionFieldDTO.getFieldSQLCash () ;
              //Utility.logger.debug ( strSQLCache ) ;
              //fieldToFieldTermDTO.set1stOperandFieldSQLCache( strSQLCache ) ; 
              fieldToFieldTermDTO.set1stOperandFieldSQLCache( functionFieldDTO.getFieldSQLCash () ) ;
            }
            else if ( nField1Type == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
            {
              ParameterFieldDTO parameterFieldDTO = null ;
              int nField1ID = new Integer ( (String) paramHashMap.get (field1ID) ).intValue() ;

              parameterFieldDTO = (ParameterFieldDTO) vecParameters.elementAt(nField1ID) ;
              //fieldToFieldTermDTO.set1stOperandFieldID(); SQLCache( parameterFieldDTO.getFieldSQLCash () ) ;
              fieldToFieldTermDTO.set1stOperandFieldSQLCache( parameterFieldDTO.getFieldSQLCash () ) ;
            }



            fieldToFieldTermDTO.setOperatorID (new Integer ( (String) paramHashMap.get (operatorID) ).intValue() ) ;
            fieldToFieldTermDTO.setOperatorName ((String) paramHashMap.get (operatorName) ) ;
            fieldToFieldTermDTO.setOperatorSQL  ((String) paramHashMap.get (operatorSQL) ) ;

            if (fieldToFieldTermDTO.getOperatorName().equalsIgnoreCase(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_LEFT_JOIN))
            {
              fieldToFieldTermDTO.set1stOperandFieldSQLCache( new String ( fieldToFieldTermDTO.get1stOperandFieldSQLCache() + " (+) " ) );
            }

            fieldToFieldTermDTO.set2ndOperandFieldID(new Integer ( (String) paramHashMap.get (field2ID) ).intValue() );
            fieldToFieldTermDTO.set2ndOperandFieldName( (String) paramHashMap.get (field2Name) ) ;
            int nField2Type = new Integer ( (String) paramHashMap.get (field2Type) ).intValue() ;
            fieldToFieldTermDTO.set2ndOperandFieldType ( nField2Type ) ;

            if ( nField2Type == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD)
            {
              fieldToFieldTermDTO.set2ndOperandFieldID ( new Integer ( (String) paramHashMap.get (field2ID) ).intValue() );
              //Utility.logger.debug (" getting const at index: " + fieldToFieldTermDTO.get2ndOperandFieldID() );
              ConstantFieldDTO constantFieldDTO = (ConstantFieldDTO) vecConstants.elementAt(fieldToFieldTermDTO.get2ndOperandFieldID() ) ;
              
              if (fieldToFieldTermDTO.getOperatorName().equalsIgnoreCase(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_CONTAINS))
              {
                String temp = constantFieldDTO.getFieldSQLCash();
                
                if ( constantFieldDTO.getFieldSQLCash().charAt(0) == '\'' )
                {
                  StringBuffer tmpSQLCache = new StringBuffer ( constantFieldDTO.getFieldSQLCash() ) ;
                  int tmpSQLCachelength = tmpSQLCache.length();
                  tmpSQLCache.deleteCharAt(tmpSQLCachelength-1);
                  tmpSQLCache.deleteCharAt(0); 
                  //constantFieldDTO.setFieldSQLCash(tmpSQLCache.toString()) ;
                  temp = tmpSQLCache.toString();
                }
                //fieldToFieldTermDTO.set2ndOperandFieldSQLCache( "\'%" + constantFieldDTO.getFieldSQLCash() + "%\'" );
                fieldToFieldTermDTO.set2ndOperandFieldSQLCache( "\'%" + temp + "%\'" );
              }
              else if (fieldToFieldTermDTO.getOperatorName().equalsIgnoreCase(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_BEGINS_WITH))
              {
                  String temp = constantFieldDTO.getFieldSQLCash();
                  
                if ( constantFieldDTO.getFieldSQLCash().charAt(0) == '\'' )
                {
                  StringBuffer tmpSQLCache = new StringBuffer ( constantFieldDTO.getFieldSQLCash() ) ;
                  int tmpSQLCachelength = tmpSQLCache.length();
                  tmpSQLCache.deleteCharAt(tmpSQLCachelength-1);
                  tmpSQLCache.deleteCharAt(0); 
                  //constantFieldDTO.setFieldSQLCash(tmpSQLCache.toString()) ;
                   temp = tmpSQLCache.toString();
                }
               // fieldToFieldTermDTO.set2ndOperandFieldSQLCache( "\'" + constantFieldDTO.getFieldSQLCash() + "%\'" );
                fieldToFieldTermDTO.set2ndOperandFieldSQLCache( "\'" + temp + "%\'" );
              }
              else if (fieldToFieldTermDTO.getOperatorName().equalsIgnoreCase(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_ENDS_WITH))
              {
                  String temp = constantFieldDTO.getFieldSQLCash();
                if ( constantFieldDTO.getFieldSQLCash().charAt(0) == '\'' )
                {
                  StringBuffer tmpSQLCache = new StringBuffer ( constantFieldDTO.getFieldSQLCash() ) ;
                  int tmpSQLCachelength = tmpSQLCache.length();
                  tmpSQLCache.deleteCharAt(tmpSQLCachelength-1);
                  tmpSQLCache.deleteCharAt(0); 
                  //constantFieldDTO.setFieldSQLCash(tmpSQLCache.toString()) ;
                   temp = tmpSQLCache.toString();
                }
               // fieldToFieldTermDTO.set2ndOperandFieldSQLCache( "\'%" + constantFieldDTO.getFieldSQLCash() + "\'" );
                fieldToFieldTermDTO.set2ndOperandFieldSQLCache( "\'%" + temp + "\'" );
              }
              else
                fieldToFieldTermDTO.set2ndOperandFieldSQLCache( constantFieldDTO.getFieldSQLCash() );

              debug ("constructQueryDTOTermsList", "2ndOperandFieldSQLCache: "+fieldToFieldTermDTO.get2ndOperandFieldSQLCache());


              //Utility.logger.debug (" fieldToFieldTermDTO.get2ndOperandFieldSQLCache( ) -->" + fieldToFieldTermDTO.get2ndOperandFieldSQLCache( ) ) ;
            }
            else if ( nField2Type == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD)
            {
              fieldToFieldTermDTO.set2ndOperandFieldID ( new Integer ( (String) paramHashMap.get (field2ID) ).intValue() );
              fieldToFieldTermDTO.set2ndOperandFieldName( (String) paramHashMap.get (field2Name) );
              //Utility.logger.debug ( field2ParentDataViewID + "-------->"+ (String) paramHashMap.get (field2ParentDataViewID) );
              //Utility.logger.debug ( "2.get2ndOperandFieldType -------->"+ fieldToFieldTermDTO.get2ndOperandFieldType() );
              fieldToFieldTermDTO.set2ndOperandFieldParentDataViewID (new Integer ( (String) paramHashMap.get (field2ParentDataViewID) ).intValue() );
              //fieldToFieldTermDTO.set2ndOperandFieldParentDataViewName( (String) paramHashMap.get (field2ParentDataViewName) );


              String begin    = new String ("\"") ;
              String middle   = new String ("\".\"") ;
              String end      = new String ("\"") ;

              strSQLCache = new String ( begin +
                    (String) paramHashMap.get (field2ParentDataViewName) + middle + 
                    (String) paramHashMap.get (field2Name) + end ) ;
              fieldToFieldTermDTO.set2ndOperandFieldSQLCache( strSQLCache ) ; 
            }
            else if ( nField2Type == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
            {
              FunctionFieldDTO functionFieldDTO = null ;//new FunctionFieldDTO () ;
              int nField2ID = new Integer ( (String) paramHashMap.get (field2ID) ).intValue() ; // index of function in functions list

              functionFieldDTO = (FunctionFieldDTO) vecFunctions.elementAt(nField2ID) ;
              fieldToFieldTermDTO.set2ndOperandFieldSQLCache( functionFieldDTO.getFieldSQLCash () ) ; 
            }
            else if ( nField2Type == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
            {
              ParameterFieldDTO parameterFieldDTO = null ;
              int nField2ID = new Integer ( (String) paramHashMap.get (field2ID) ).intValue() ; // index of function in functions list
              //fieldToFieldTermDTO.set2ndOperandFieldID ( nField2ID ) ; 

              parameterFieldDTO = (ParameterFieldDTO) vecParameters.elementAt(nField2ID) ;


              if (fieldToFieldTermDTO.getOperatorName().equalsIgnoreCase(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_CONTAINS))
              {
              String temp =  parameterFieldDTO.getFieldSQLCash() ;
                if ( parameterFieldDTO.getFieldSQLCash().charAt(0) == '\'' )
                {
                  StringBuffer tmpSQLCache = new StringBuffer ( parameterFieldDTO.getFieldSQLCash() ) ;
                  int tmpSQLCachelength = tmpSQLCache.length();
                  tmpSQLCache.deleteCharAt(tmpSQLCachelength-1);
                  tmpSQLCache.deleteCharAt(0); 
                 //parameterFieldDTO.setFieldSQLCash(tmpSQLCache.toString()) ;
                  temp=tmpSQLCache.toString() ;
                }
                fieldToFieldTermDTO.set2ndOperandFieldSQLCache( "\'%" + temp + "%\'" );
              }
              else if (fieldToFieldTermDTO.getOperatorName().equalsIgnoreCase(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_BEGINS_WITH))
              {
              String temp= parameterFieldDTO.getFieldSQLCash();
                if ( parameterFieldDTO.getFieldSQLCash().charAt(0) == '\'' )
                {
                  StringBuffer tmpSQLCache = new StringBuffer ( parameterFieldDTO.getFieldSQLCash() ) ;
                  int tmpSQLCachelength = tmpSQLCache.length();
                  tmpSQLCache.deleteCharAt(tmpSQLCachelength-1);
                  tmpSQLCache.deleteCharAt(0); 
                 // parameterFieldDTO.setFieldSQLCash(tmpSQLCache.toString()) ;
                 temp = tmpSQLCache.toString();
                
                }
                fieldToFieldTermDTO.set2ndOperandFieldSQLCache( "\'" + temp + "%\'" );
              }
              else if (fieldToFieldTermDTO.getOperatorName().equalsIgnoreCase(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_ENDS_WITH))
              {
              String temp = parameterFieldDTO.getFieldSQLCash();
                if ( parameterFieldDTO.getFieldSQLCash().charAt(0) == '\'' )
                {
                  StringBuffer tmpSQLCache = new StringBuffer ( parameterFieldDTO.getFieldSQLCash() ) ;
                  int tmpSQLCachelength = tmpSQLCache.length();
                  tmpSQLCache.deleteCharAt(tmpSQLCachelength-1);
                  tmpSQLCache.deleteCharAt(0); 
                  //parameterFieldDTO.setFieldSQLCash(tmpSQLCache.toString()) ;
                  temp= tmpSQLCache.toString();
                }
                fieldToFieldTermDTO.set2ndOperandFieldSQLCache( "\'%" + temp + "\'" );
              }
              else
                fieldToFieldTermDTO.set2ndOperandFieldSQLCache( parameterFieldDTO.getFieldSQLCash() );

              debug ("constructQueryDTOTermsList", "2ndOperandFieldSQLCache: "+fieldToFieldTermDTO.get2ndOperandFieldSQLCache());
            }



            if (fieldToFieldTermDTO.getOperatorName().equalsIgnoreCase(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_RIGHT_JOIN))
            {
              fieldToFieldTermDTO.set2ndOperandFieldSQLCache( new String (fieldToFieldTermDTO.get2ndOperandFieldSQLCache() + " (+) ") );
            }


            vecTerms.addElement(fieldToFieldTermDTO);
          }
        }


        termTypeDTO = new TermTypeDTO ();
        String strFieldToDataViewNum = (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_NUM) ;
        //Utility.logger.debug ( "------->strFieldToDataViewNum: " + strFieldToDataViewNum ) ;
        if (strFieldToDataViewNum != null)
        {
          nNumParam = new Integer ( strFieldToDataViewNum ) ;
          //Utility.logger.debug ( "strFieldToDataViewNum: " + nNumParam ) ;
        
          for (int i=0 ; i < nNumParam.intValue() ; i++)
          {
            FieldtoDataViewTermDTO fieldtoDataViewTermDTO = new FieldtoDataViewTermDTO() ;

            termName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TERM_NAME + i ) ;
            field1ID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_ID + i ) ;
            field1Name = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_NAME + i ) ;
            field1Type = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_TYPE + i ) ;
            field1ParentDataViewID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_ID + i ) ;
            field1ParentDataViewName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_NAME + i ) ;
            field1ParentDataViewType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_1ST_FIELD_DATAVIEW_TYPE + i ) ;
            operatorID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_ID + i ) ;
            operatorName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_NAME + i ) ;
            operatorSQL = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_OPERATOR_SQL + i ) ;
            dataViewID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_ID + i ) ;
            dataViewName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_NAME + i ) ;
            dataViewType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_RELATED_DATAVIEW_TYPE_ID + i ) ;

            fieldtoDataViewTermDTO.setTermSQLCache( (String) paramHashMap.get (termName) ); 
            //(String) paramHashMap.get (termName) ;
            termTypeDTO.setTermTypeID (QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE);
            fieldtoDataViewTermDTO.setTermType(termTypeDTO);

            fieldtoDataViewTermDTO.set1stOperandFieldID(new Integer ( (String) paramHashMap.get (field1ID) ).intValue() );
            fieldtoDataViewTermDTO.set1stOperandFieldName( (String) paramHashMap.get (field1Name) ) ;

            int nField1Type = new Integer ( (String) paramHashMap.get (field1Type) ).intValue() ;
            fieldtoDataViewTermDTO.set1stOperandFieldType (nField1Type);
            if ( nField1Type == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD)
            {
              fieldtoDataViewTermDTO.set1stOperandFieldParentDataViewID( new Integer ( (String) paramHashMap.get (field1ParentDataViewID) ).intValue() )  ;

              String begin    = new String ("\"") ;
              String middle   = new String ("\".\"") ;
              String end      = new String ("\"") ;

              strSQLCache = new String ( begin +
                    (String) paramHashMap.get (field1ParentDataViewName) + middle + 
                    (String) paramHashMap.get (field1Name) + end ) ;
              fieldtoDataViewTermDTO.set1stOperandFieldSQLCache( strSQLCache ) ; 

            }
            else if ( nField1Type == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD)
            {
              fieldtoDataViewTermDTO.set1stOperandFieldID ( new Integer ( (String) paramHashMap.get (field1ID) ).intValue() );
              ConstantFieldDTO constantFieldDTO = (ConstantFieldDTO) vecConstants.elementAt(fieldtoDataViewTermDTO.get1stOperandFieldID() ) ; 
              fieldtoDataViewTermDTO.set1stOperandFieldSQLCache( constantFieldDTO.getFieldSQLCash() );
              //Utility.logger.debug (" fieldToFieldTermDTO.get2ndOperandFieldSQLCache( ) -->" + fieldToFieldTermDTO.get1stOperandFieldSQLCache( ) ) ;
            }
            else if ( nField1Type == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
            {
              FunctionFieldDTO functionFieldDTO = null ;//new FunctionFieldDTO () ;
              int nField1ID = new Integer ( (String) paramHashMap.get (field1ID) ).intValue() ; // index of function in functions list
              //fieldToFieldTermDTO.set1stOperandFieldID ( nField1ID ) ; 

              functionFieldDTO = (FunctionFieldDTO) vecFunctions.elementAt(nField1ID) ;
              strSQLCache = functionFieldDTO.getFieldSQLCash () ;
              Utility.logger.debug ( strSQLCache ) ;
              fieldtoDataViewTermDTO.set1stOperandFieldSQLCache( strSQLCache ) ; 
            }
            else if ( nField1Type == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
            {
              ParameterFieldDTO parameterFieldDTO = null ;
              int nField1ID = new Integer ( (String) paramHashMap.get (field1ID) ).intValue() ;

              parameterFieldDTO = (ParameterFieldDTO) vecParameters.elementAt(nField1ID) ;
              fieldtoDataViewTermDTO.set1stOperandFieldSQLCache( parameterFieldDTO.getFieldSQLCash () ) ;
            }


            fieldtoDataViewTermDTO.setOperatorID(new Integer ( (String) paramHashMap.get (operatorID) ).intValue() ) ;
            fieldtoDataViewTermDTO.setOperatorName((String) paramHashMap.get (operatorName) ) ;
            fieldtoDataViewTermDTO.setOperatorSQL( (String) paramHashMap.get (operatorSQL) ) ;
            fieldtoDataViewTermDTO.setRelatedDataViewID( new Integer ( (String) paramHashMap.get (dataViewID) ).intValue() );
            fieldtoDataViewTermDTO.setRelatedDataViewName( (String) paramHashMap.get (dataViewName) );
            //Utility.logger.debug (dataViewID + "--->" + fieldtoDataViewTermDTO.getRelatedDataViewID() );
            //Utility.logger.debug (dataViewName + "--->" + fieldtoDataViewTermDTO.getRelatedDataViewName() );
            //Utility.logger.debug ( dataViewType + "--->" + (String) paramHashMap.get (dataViewType) ) ;
            fieldtoDataViewTermDTO.setRelatedDataViewTypeID( new Integer ( (String) paramHashMap.get (dataViewType) ).intValue() );

            if ( fieldtoDataViewTermDTO.getRelatedDataViewTypeID() != QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE ) 
            {
              Utility.logger.debug ("loading subquery: " + fieldtoDataViewTermDTO.getRelatedDataViewName()) ;
              Vector vec =new Vector();
              QueryDTO subQueryDTO = loadQueryDTO ( m_conSDSConnection,fieldtoDataViewTermDTO.getRelatedDataViewID() ,vec) ;
              String subQuerySQL = constructQuerySQL(subQueryDTO) ;
              fieldtoDataViewTermDTO.setRelatedDataViewSQL (subQuerySQL) ;
            }
            Utility.logger.debug ( "adding term: " + fieldtoDataViewTermDTO.get1stOperandFieldName() + "__" + fieldtoDataViewTermDTO.getRelatedDataViewName()  ) ;
            vecTerms.addElement(fieldtoDataViewTermDTO);
          }
        }
        //Utility.logger.debug ( "size of terms: " + vecTerms.size() ) ;

      }
      catch (Exception objExp)
      {
        objExp.printStackTrace();
      }
      return vecTerms ;
    }

    //---------------------------------------------------------------
    private  QueryWhereDTO constructQueryDTOWhere ( 
                  HashMap paramHashMap,
                  Vector vecTerms )
    {
      Integer nNumParam; 
      String strQueryName;
      QueryWhereDTO queryWhereDTO = null ;
      Vector  vecConditionElements = null ;
      TermTypeDTO termTypeDTO = null ;
      try
      {

        // --------------------- constructing where expression---------------------
        queryWhereDTO = new QueryWhereDTO () ;
        vecConditionElements = new Vector () ; // symbols are considered as terms here
        String  whereType ;
        String  elementID ;
        String  symbolSQL ;
        int     nWhereType ;
        String  strWheresNum ;
        strWheresNum = (String) paramHashMap.get ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERES_NUM ) ;
        //Utility.logger.debug ( "------->strWheresNum: " + strWheresNum ) ;
        if (strWheresNum != null)
        {
          nNumParam = new Integer ( strWheresNum ) ;
          for (int i=0 ; i < nNumParam.intValue() ; i++)
          {
            whereType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE + i ) ;
            elementID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_ELEMENT_ID + i ) ;
            symbolSQL = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_SYMBOL_SQL + i ) ;
            nWhereType = new Integer ( (String) paramHashMap.get ( whereType ) ).intValue() ;
          
            if (nWhereType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL)
            {
              RelationSymbolDTO symbol = new RelationSymbolDTO () ; 
              symbol.setRelationSymbolSQL((String) paramHashMap.get ( symbolSQL ));
              ConditionElementTypeDTO conditionElementTypeDTO = new ConditionElementTypeDTO () ;
              conditionElementTypeDTO.setConditionElementTypeID(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL);
              symbol.setConditionElementType(conditionElementTypeDTO);
              symbol.setRelationSymbolID(new Integer ( (String) paramHashMap.get ( elementID ) ).intValue() );
              symbol.setRelationSymbolSQL((String) paramHashMap.get ( symbolSQL ) );
              vecConditionElements.addElement(symbol);
            }
            else if (nWhereType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM)
            {
              int termIndex = new Integer ( (String) paramHashMap.get ( elementID ) ).intValue() ;
              //Utility.logger.debug ( "getting term index " + termIndex ) ;
              termTypeDTO = ( (Term) vecTerms.elementAt(termIndex) ).getTermType() ;

              if ( termTypeDTO.getTermTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE )
              {
                FieldToFieldTermDTO fieldToFieldTerm = null ;
                fieldToFieldTerm = ( (FieldToFieldTermDTO) vecTerms.elementAt(termIndex) ) ;
                ConditionElementTypeDTO conditionElementTypeDTO = new ConditionElementTypeDTO () ;
                conditionElementTypeDTO.setConditionElementTypeID(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM);
                fieldToFieldTerm.setConditionElementType(conditionElementTypeDTO);
                fieldToFieldTerm.setTermID(termIndex);
                //Utility.logger.debug ("1. fieldToFieldTerm.setTermID()" + fieldToFieldTerm.getTermID()) ;
                vecConditionElements.addElement(fieldToFieldTerm);
              }
              else if ( termTypeDTO.getTermTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE )
              {
                FieldtoDataViewTermDTO fieldtoDataViewTerm = null ;
                fieldtoDataViewTerm = ( (FieldtoDataViewTermDTO) vecTerms.elementAt(termIndex) ) ;
                ConditionElementTypeDTO conditionElementTypeDTO = new ConditionElementTypeDTO () ;
                conditionElementTypeDTO.setConditionElementTypeID(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM);
                fieldtoDataViewTerm.setConditionElementType(conditionElementTypeDTO);
                //Utility.logger.debug ("1. fieldtoDataViewTerm.setTermID()" + fieldtoDataViewTerm.getTermID()) ;
                fieldtoDataViewTerm.setTermID(termIndex);
                vecConditionElements.addElement(fieldtoDataViewTerm);
              }
            }
          }
        }
        queryWhereDTO.setConditionElements(vecConditionElements);
      }
      catch (Exception objExp)
      {
        objExp.printStackTrace();
      }
      return queryWhereDTO ;
    }


    //---------------------------------------------------------------
    private Vector constructQueryDTOGroupByList ( HashMap paramHashMap ,
                      //Vector vecConstants,
                      Vector vecParameters,
                      Vector vecFunctions)
    {
      Vector vecGroupByList = null ;
      Integer nGroupByNum; 
      String  termName ;
      String  fieldID ;
      String  fieldName ;
      String  fieldType ;
      String  fieldDescription ;
      //String  fieldAlias ;
      String  fieldParentDataViewName;
      String  fieldParentDataViewID;
      String  fieldParentDataViewType;
      FieldTypeModel fieldTypeModel = null ;
      int fieldTypeID ;
      int nFieldParentDataViewType;
      try
      {
        vecGroupByList = new Vector () ;

        String strGroupByNum = (String) paramHashMap.get (QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELDS_NUM) ;
        //Utility.logger.debug ( "------->strGroupByNum: " + strGroupByNum ) ;
        if (strGroupByNum != null)
        {
          nGroupByNum = new Integer ( strGroupByNum ) ;
          for (int i=0 ; i < nGroupByNum.intValue() ; i++)
          {
            fieldTypeModel = new FieldTypeModel ();
            
            fieldID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ID + i ) ;
            fieldType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_TYPE + i ) ;
            fieldName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_NAME + i ) ;
            fieldDescription = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DESCRIPTION + i ) ;
            //fieldAlias = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_ALIAS + i ) ;
            fieldParentDataViewID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_ID + i ) ;
            fieldParentDataViewName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_NAME + i ) ;
            fieldParentDataViewType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_GROUPBY_FIELD_DATAVIEW_TYPE + i ) ;


            fieldTypeID = (new Integer ((String)paramHashMap.get (fieldType) ) ).intValue() ;
            fieldTypeModel.setFieldTypeID (fieldTypeID) ;
            if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
            {
              DataViewFieldDTO dataViewFieldDTO = new DataViewFieldDTO () ;
              dataViewFieldDTO.setFieldID ( new Integer ((String) paramHashMap.get (fieldID) ).intValue() );
              dataViewFieldDTO.setFieldType( fieldTypeModel );
              //String strAlias = (String) paramHashMap.get (fieldAlias) ;
              //if (strAlias.equalsIgnoreCase("") )
                dataViewFieldDTO.setFieldName ( (String) paramHashMap.get (fieldName) );
              //else
              //  dataViewFieldDTO.setFieldName ( strAlias );

              String strSQLCache = null ;

              String begin    = new String ("\"") ;
              String middle   = new String ("\".\"") ;
              String end      = new String ("\"") ;

              strSQLCache = new String ( begin +
                    (String) paramHashMap.get (fieldParentDataViewName) + middle + 
                    (String) paramHashMap.get (fieldName) + end ) ;

              dataViewFieldDTO.setFieldSQLCash ( strSQLCache ) ; 
              dataViewFieldDTO.setFieldDescription((String) paramHashMap.get (fieldDescription));
              dataViewFieldDTO.setParentDataViewID((new Integer ((String)paramHashMap.get (fieldParentDataViewID))).intValue() );
              dataViewFieldDTO.setParentDataViewName( (String)paramHashMap.get ( fieldParentDataViewName ) );
              vecGroupByList.addElement ( dataViewFieldDTO ) ;
            }
            else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
            {
              //functionFieldDTO.setFieldName( (String) paramHashMap.get (paramName) );
              int nParamID = ( new Integer ((String) paramHashMap.get (fieldID) ).intValue() ) ;
              FunctionFieldDTO functionFieldDTO = (FunctionFieldDTO) ((FunctionFieldDTO) vecFunctions.elementAt(nParamID)).clone() ;
              vecGroupByList.addElement( functionFieldDTO );
              
            }
            else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
            {
              int nParamID = ( new Integer ((String) paramHashMap.get (fieldID) ).intValue() ) ;
              ParameterFieldDTO parameterFieldDTO = (ParameterFieldDTO) ((ParameterFieldDTO) vecParameters.elementAt(nParamID)).clone() ;
              vecGroupByList.addElement( parameterFieldDTO );
            }
          }
        }
          

      }
      catch (Exception objExp)
      {
        objExp.printStackTrace();
      }
      return vecGroupByList ;
    }


    //---------------------------------------------------------------
    private Vector constructQueryDTOOrderByList ( HashMap paramHashMap ,
                      //Vector vecConstants,
                      Vector vecFunctions,
                      Vector vecParameters )
    {
      Vector vecOrderByList = null ;
      Integer nOrderByNum; 
      String  termName ;
      String  fieldID ;
      String  fieldName ;
      String  fieldType ;
      String  fieldDescription ;
      String  fieldParentDataViewName;
      String  fieldParentDataViewID;
      String  fieldParentDataViewType;
      String  fieldOrderType;
      FieldTypeModel fieldTypeModel = null ;
      int fieldTypeID ;
      int nFieldParentDataViewType;
      //int nFieldOrderType ;
      String  strFieldOrderType ;
      try
      {
        vecOrderByList = new Vector () ;

        String strOrderByNum = (String) paramHashMap.get (QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELDS_NUM) ;
        //Utility.logger.debug ( "------->strOrderByNum: " + strOrderByNum ) ;
        if (strOrderByNum != null)
        {
          nOrderByNum = new Integer ( strOrderByNum ) ;
          for (int i=0 ; i < nOrderByNum.intValue() ; i++)
          {
            fieldTypeModel = new FieldTypeModel ();
            
            fieldID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ID + i ) ;
            fieldType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_TYPE + i ) ;
            fieldName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_NAME + i ) ;
            fieldDescription = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DESCRIPTION + i ) ;
            fieldParentDataViewID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_ID + i ) ;
            fieldParentDataViewName = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_NAME + i ) ;
            fieldParentDataViewType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_DATAVIEW_TYPE + i ) ;
            fieldOrderType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_ORDERBY_FIELD_ORDER_TYPE + i ) ;



            fieldTypeID = (new Integer ((String)paramHashMap.get (fieldType) ) ).intValue() ;
            fieldTypeModel.setFieldTypeID (fieldTypeID) ;
            if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
            {
              DataViewFieldDTO dataViewFieldDTO = new DataViewFieldDTO () ;
              dataViewFieldDTO.setFieldID ( new Integer ((String) paramHashMap.get (fieldID) ).intValue() );
              dataViewFieldDTO.setFieldType( fieldTypeModel );
              dataViewFieldDTO.setFieldName ( (String) paramHashMap.get (fieldName) );

              String strSQLCache = null ;

              String begin    = new String ("\"") ;
              String middle   = new String ("\".\"") ;
              String end      = new String ("\"") ;

              strSQLCache = new String ( begin +
                    (String) paramHashMap.get (fieldParentDataViewName) + middle + 
                    (String) paramHashMap.get (fieldName) + end ) ;

              dataViewFieldDTO.setFieldSQLCash ( strSQLCache ) ; 
              dataViewFieldDTO.setFieldDescription((String) paramHashMap.get (fieldDescription));
              dataViewFieldDTO.setParentDataViewID((new Integer ((String)paramHashMap.get (fieldParentDataViewID))).intValue() );
              dataViewFieldDTO.setParentDataViewName( (String)paramHashMap.get ( fieldParentDataViewName ) );
              OrderByDTO orderByDTO = new OrderByDTO ();
              //nFieldOrderType =  new Integer ((String) paramHashMap.get (fieldOrderType) ).intValue() ;
              //orderByDTO.setOrderType (nFieldOrderType);
              strFieldOrderType = (String) paramHashMap.get (fieldOrderType) ;
              orderByDTO.setOrderType (strFieldOrderType);
              orderByDTO.setFieldDTO (dataViewFieldDTO);

              vecOrderByList.addElement ( orderByDTO ) ;
            }
            else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
            {
              int nParamID = ( new Integer ((String) paramHashMap.get (fieldID) ).intValue() ) ;
              FunctionFieldDTO functionFieldDTO = (FunctionFieldDTO) ((FunctionFieldDTO) vecFunctions.elementAt(nParamID)).clone() ;

              OrderByDTO orderByDTO = new OrderByDTO ();
              //nFieldOrderType =  new Integer ((String) paramHashMap.get (fieldOrderType) ).intValue() ;
              //orderByDTO.setOrderType (nFieldOrderType);

              strFieldOrderType = (String) paramHashMap.get (fieldOrderType) ;
              orderByDTO.setOrderType (strFieldOrderType);
              orderByDTO.setFieldDTO (functionFieldDTO);

              vecOrderByList.addElement ( orderByDTO ) ;
            }
            else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
            {
              int nParamID = ( new Integer ((String) paramHashMap.get (fieldID) ).intValue() ) ;
              ParameterFieldDTO parameterFieldDTO = (ParameterFieldDTO) ((ParameterFieldDTO) vecParameters.elementAt(nParamID)).clone() ;

              OrderByDTO orderByDTO = new OrderByDTO ();
              //nFieldOrderType =  new Integer ((String) paramHashMap.get (fieldOrderType) ).intValue() ;
              //orderByDTO.setOrderType (nFieldOrderType);

              strFieldOrderType = (String) paramHashMap.get (fieldOrderType) ;
              orderByDTO.setOrderType (strFieldOrderType);
              orderByDTO.setFieldDTO (parameterFieldDTO);

              vecOrderByList.addElement ( orderByDTO ) ;
            }
          }
        }
      }
      catch (Exception objExp)
      {
        objExp.printStackTrace();
      }
      return vecOrderByList ;
    }


    //---------------------------------------------------------------
    private  QueryHavingDTO constructQueryDTOHaving ( 
                  HashMap paramHashMap,
                  Vector vecTerms )
    {
      Integer nNumParam; 
      String strQueryName;
      QueryHavingDTO queryHavingDTO = null ;
      Vector  vecConditionElements = null ;
      TermTypeDTO termTypeDTO = null ;
      try
      {

        // --------------------- constructing having expression---------------------
        queryHavingDTO = new QueryHavingDTO () ;
        vecConditionElements = new Vector () ; // symbols are considered as terms here
        String  strHavingType ;
        String  elementID ;
        String  symbolSQL ;
        int     nHavingType ;
        String  strHavingsNum ;

        strHavingsNum = (String) paramHashMap.get ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVINGS_NUM ) ;
        //Utility.logger.debug ( "------->strHavingsNum: " + strHavingsNum ) ;
        if (strHavingsNum != null)
        {
          nNumParam = new Integer ( strHavingsNum ) ;
          for (int i=0 ; i < nNumParam.intValue() ; i++)
          {
            strHavingType = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_TYPE + i ) ;
            elementID = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_ELEMENT_ID + i ) ;
            symbolSQL = new String ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_HAVING_SYMBOL_SQL + i ) ;
            
            nHavingType = new Integer ( (String) paramHashMap.get ( strHavingType ) ).intValue() ;
          
            if (nHavingType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL)
            {
              RelationSymbolDTO symbol = new RelationSymbolDTO () ; 
              symbol.setRelationSymbolSQL((String) paramHashMap.get ( symbolSQL ));
              ConditionElementTypeDTO conditionElementTypeDTO = new ConditionElementTypeDTO () ;
              conditionElementTypeDTO.setConditionElementTypeID(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL);
              symbol.setConditionElementType(conditionElementTypeDTO);
              symbol.setRelationSymbolID(new Integer ( (String) paramHashMap.get ( elementID ) ).intValue() );
              symbol.setRelationSymbolSQL((String) paramHashMap.get ( symbolSQL ) );
              vecConditionElements.addElement(symbol);
            }
            else if (nHavingType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM)
            {
              int termIndex = new Integer ( (String) paramHashMap.get ( elementID ) ).intValue() ;
              //Utility.logger.debug ( "getting term index " + termIndex ) ;
              termTypeDTO = ( (Term) vecTerms.elementAt(termIndex) ).getTermType() ;

              if ( termTypeDTO.getTermTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE )
              {
                FieldToFieldTermDTO fieldToFieldTerm = null ;
                fieldToFieldTerm = ( (FieldToFieldTermDTO) vecTerms.elementAt(termIndex) ) ;
                ConditionElementTypeDTO conditionElementTypeDTO = new ConditionElementTypeDTO () ;
                conditionElementTypeDTO.setConditionElementTypeID(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM);
                fieldToFieldTerm.setConditionElementType(conditionElementTypeDTO);
                fieldToFieldTerm.setTermID(termIndex);
                //Utility.logger.debug ("1. fieldToFieldTerm.setTermID()" + fieldToFieldTerm.getTermID()) ;
                vecConditionElements.addElement(fieldToFieldTerm);
              }
              else if ( termTypeDTO.getTermTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE )
              {
                FieldtoDataViewTermDTO fieldtoDataViewTerm = null ;
                fieldtoDataViewTerm = ( (FieldtoDataViewTermDTO) vecTerms.elementAt(termIndex) ) ;
                ConditionElementTypeDTO conditionElementTypeDTO = new ConditionElementTypeDTO () ;
                conditionElementTypeDTO.setConditionElementTypeID(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM);
                fieldtoDataViewTerm.setConditionElementType(conditionElementTypeDTO);
                //Utility.logger.debug ("1. fieldtoDataViewTerm.setTermID()" + fieldtoDataViewTerm.getTermID()) ;
                fieldtoDataViewTerm.setTermID(termIndex);
                vecConditionElements.addElement(fieldtoDataViewTerm);
              }
            }
          }
        }
        queryHavingDTO.setConditionElements(vecConditionElements);
      }
      catch (Exception objExp)
      {
        objExp.printStackTrace();
      }
      return queryHavingDTO ;
    }


    //---------------------------------------------------------------
    public QueryDTO constructQueryDTO ( Connection m_conSDSConnection,HashMap paramHashMap )
    {
      QueryDTO queryDTO = null ;
      try
      {

        // temporary code to display all hashmap keys received from the client
   //     Utility.logger.debug ( "\n  -->constructQueryDTO ") ;
        /*
        Utility.logger.debug ( "  -- Begin HashMap ------------------------------------------------------ ") ;
        Object arrKey[] = paramHashMap.keySet().toArray() ;
        for (int i = 0 ; i < arrKey.length ; i++ )
        {
          //Utility.logger.debug ( "found Key: " + (String) arrKey[i] ) ;
          Utility.logger.debug ( "  " + (String) arrKey[i] + "\n    ----> " + (String) paramHashMap.get ((String) arrKey[i]) ) ;
        }
        Utility.logger.debug ( "  -- End HashMap ------------------------------------------------------ ") ;
        */


        //String strDataViewName =  ;

        queryDTO = new QueryDTO();
        
        queryDTO.setRunUserID( (String)paramHashMap.get("USER_ID"));

        queryDTO.setQueryName         ( (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_NAME) );

        queryDTO.setQueryDescription  ( (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_DESC) );

        queryDTO.setQueryUnique       ( new Integer ( (String) paramHashMap.get (QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE) ).intValue()) ;

        queryDTO.setQuerySaveAs       ( new Integer ( (String) paramHashMap.get (QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE) ).intValue()) ;

        queryDTO.setQueryIssue        ( new Integer ( (String) paramHashMap.get (QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_ISSUE) ).intValue()) ;

        //queryDTO.setQueryUniverse     ( new Integer ( (String) paramHashMap.get (QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIVERSE) ).intValue()) ;
        String strDataViewUniverseID = (String) paramHashMap.get (QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIVERSE) ;
        int nDataViewUniverseID = (DataViewDAO.switchUniverseTypeToID ( m_conSDSConnection, strDataViewUniverseID)).intValue() ; 
        queryDTO.setQueryUniverse     ( nDataViewUniverseID ) ;

        queryDTO.setDetailedDataViews ( constructQueryDTOFromList (m_conSDSConnection,paramHashMap) );

        queryDTO.setConstantsFields   ( constructQueryDTOConstantsList (m_conSDSConnection,paramHashMap) );

        queryDTO.setParametersFields  ( constructQueryDTOParametersList (paramHashMap/*, 
                                                queryDTO.getConstantsFields()*/ ) );

        queryDTO.setFunctionsFields   ( constructQueryDTOFunctionsList (paramHashMap, 
                                                queryDTO.getConstantsFields(),
                                                queryDTO.getParametersFields() ) );

        
        queryDTO.setDisplayedFields   ( constructQueryDTODisplayedFields (paramHashMap, 
                                                //queryDTO.getConstantsFields(),
                                                queryDTO.getFunctionsFields(),
                                                queryDTO.getParametersFields() ) );


        queryDTO.setTerms             ( constructQueryDTOTermsList (m_conSDSConnection,paramHashMap, 
                                                queryDTO.getConstantsFields(),
                                                queryDTO.getParametersFields(),
                                                queryDTO.getFunctionsFields()) ) ;


        queryDTO.setQueryWhere        ( constructQueryDTOWhere (paramHashMap, 
                                                queryDTO.getTerms()) )  ;

        queryDTO.setGroupBy           ( constructQueryDTOGroupByList (paramHashMap, 
                                                //queryDTO.getConstantsFields(),
                                                queryDTO.getParametersFields(),
                                                queryDTO.getFunctionsFields()) ) ;

        queryDTO.setOrderBy           ( constructQueryDTOOrderByList (paramHashMap, 
                                                //queryDTO.getConstantsFields(),
                                                queryDTO.getParametersFields(),
                                                queryDTO.getFunctionsFields()) ) ;

        queryDTO.setQueryHaving       ( constructQueryDTOHaving (paramHashMap, 
                                                queryDTO.getTerms()) )  ;

     //   Utility.logger.debug ( "  <--constructQueryDTO\n") ;
      }
      catch (Exception objExp)
      {
        objExp.printStackTrace();
      }
      return queryDTO ;
    }


    //---------------------------------------------------------------
    //---------------------- xxxxx scenario -------------------------
    //---------------------------------------------------------------

    private void constructQuerySQLSelectClause ( QueryDTO queryDTO, StringBuffer strSQL  )
    {
      DataViewFieldDTO  dataViewFieldDTO ;
      ConstantFieldDTO  constantFieldDTO ;
      FunctionFieldDTO  functionFieldDTO ;
      ParameterFieldDTO parameterFieldDTO ;
      int fieldTypeID ;
      String strFieldName;

      //  -------- SELECT CLAUSE ---------------------------- 
      strSQL.append ("  SELECT  " );

      if ( queryDTO.getQueryUnique() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE_TRUE )
        strSQL.append ( " DISTINCT  " ) ;

//      Utility.logger.debug ("generating SQL for DataView: " + queryDTO.getQueryName()) ;
      //Utility.logger.debug ("  displayedFields.size(): " + displayedFields.size()) ;
      Vector displayedFields = queryDTO.getDisplayedFields () ;
      for (int i=0; i<displayedFields.size() ; i++)
      {
        fieldTypeID = ( (FieldDTO) displayedFields.elementAt(i) ).getFieldType().getFieldTypeID() ;
        //System.out.println ( "  fieldTypeID " + fieldTypeID ) ; 

        if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
        {
            dataViewFieldDTO = (DataViewFieldDTO)displayedFields.elementAt(i) ;
            strFieldName = dataViewFieldDTO.getFieldName() ;
            //strFieldSQLCash = new String (dataViewFieldDTO.getFieldSQLCash() ) ;
            if ( i != 0 ) strSQL.append (", ");
            //Utility.logger.debug (" 1." + (dataViewFieldDTO.getFieldSQLCash() != null )) ;
            //Utility.logger.debug (" 2." + ( (dataViewFieldDTO.getFieldSQLCash() ) ) );
            //Utility.logger.debug (" 3." + ((dataViewFieldDTO.getFieldSQLCash().equalsIgnoreCase("")) )) ;
            strSQL.append (dataViewFieldDTO.getFieldSQLCash() + " AS ");
            strSQL.append ("\""+dataViewFieldDTO.getFieldName() + "\" ");
        }
        else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
        {
            constantFieldDTO = (ConstantFieldDTO)displayedFields.elementAt(i) ;
            //Utility.logger.debug ( "  generating const ID " + constantFieldDTO.getFieldID() ) ;  
            if ( i != 0 ) strSQL.append (", ");

            //strSQL.append ("\'" + constantFieldDTO.getFieldSQLCash() + "\' AS ");
            strSQL.append ( constantFieldDTO.getFieldSQLCash() + " AS ");
            strSQL.append ("\""+constantFieldDTO.getFieldName() + "\" ");
        }
        else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
        {
            //Utility.logger.debug ( "  got function field" ) ; 
        	//System.out.println ( "  got function field" ) ; 
        	
            functionFieldDTO = (FunctionFieldDTO)displayedFields.elementAt(i) ;
            if ( i != 0 ) strSQL.append (", ");

            Vector vecFunctionParameters = functionFieldDTO.getFunctionParameterValues();
            FunctionDTO functionDto = functionFieldDTO.getFunctionDTO();
            String functionSql = functionDto.getFunctionSQL();
            Vector functionParameterDefinition = functionDto.getParameterDefinitions();
            //Utility.logger.debug("Function SQL : "+functionSql);
            
           //System.out.println("functionSql="+functionSql);
            String functionSqlCash=null;
            
            if (functionSql.compareTo("FUNC_LOG_UID")==0) {
                functionSqlCash = queryDTO.getRunUserID();
            }
            else
            {
                 functionSqlCash = functionSql+"(";
                 String tempSt =null;
                for(int p=0;p<vecFunctionParameters.size();p++)
                {
                  FunctionParameterValueModel functionParameterValueModel = (FunctionParameterValueModel)vecFunctionParameters.get(p);
                  
                //  System.out.println("value ="+functionParameterValueModel.getParameterValueSQLCache());
                  
                  if (functionParameterValueModel.getParameterValueSQLCache() != null)
                  {
	                  if(p!=0)functionSqlCash += ",";
	                  tempSt += functionParameterValueModel.getParameterValueSQLCache();
	                  functionSqlCash += functionParameterValueModel.getParameterValueSQLCache();;
                  }
                }
                functionSqlCash += ")";
                
                if (tempSt==null)
                {
                	functionSqlCash = functionFieldDTO.getFieldSQLCash();
                }
                
            }

         //  System.out.println("Function SQL Cash Old : "+functionFieldDTO.getFieldSQLCash());
         //  System.out.println("Function SQL Cash New : "+functionSqlCash);
            
           
            strSQL.append ( functionSqlCash + " AS ");
            // temporary code - should enlarge the name in the Field table
           // strSQL.append ("\""+functionFieldDTO.getFieldName().substring(0,20) + "\" ");          

            strSQL.append ("\""+functionFieldDTO.getFieldName().substring(0,Math.min(functionFieldDTO.getFieldName().length(),20)) + "\" ");

//            Utility.logger.debug("field name = " + functionFieldDTO.getFieldName());
            //Utility.logger.debug("field name = " + functionFieldDTO.getFieldSQLCash());
            //strSQL.append ( functionFieldDTO.getFieldSQLCash() );
        }
        else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
        {
            //Utility.logger.debug ( "  got parameter field" ) ; 
            parameterFieldDTO = (ParameterFieldDTO)displayedFields.elementAt(i) ;
            if ( i != 0 ) strSQL.append (", ");

            strSQL.append ( parameterFieldDTO.getFieldSQLCash() + " AS ");
            strSQL.append ("\""+parameterFieldDTO.getFieldName() + "\" ");
            //strSQL.append ( parameterFieldDTO.getFieldSQLCash() );
        }
      }
    }

    //---------------------------------------------------------------
    private void constructQuerySQLFromClause ( QueryDTO queryDTO, StringBuffer strSQL  )
    {
      //  -------- FROM CLAUSE ---------------------------- 
      HashMap hMapDetailedDataViews = queryDTO.getDetailedDataViews ();
      //System.out.println("hMapDetailedDataViews = "+hMapDetailedDataViews);
      
      DetailedDataViewDTO detailedDataViewDTO ;
      Object arrKeys[] = hMapDetailedDataViews.keySet().toArray();


      

      strSQL.append ("\n  FROM  ");
      for (int i=0; i<arrKeys.length ; i++)
      {
        //Utility.logger.debug ( "array key--->" + (Integer)arrKeys[i] ) ;
        detailedDataViewDTO = (DetailedDataViewDTO)hMapDetailedDataViews.get((Integer)arrKeys[i]);
        if ( i != 0 ) strSQL.append (", ");

        
        if (detailedDataViewDTO.getDataViewTypeID() != QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE )
          strSQL.append ( "\n(\n" + detailedDataViewDTO.getDataViewOverrideSQL() + "\n) \"" + detailedDataViewDTO.getDataViewName() + "\"\n");
        else if (detailedDataViewDTO.getDataViewTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE )
          strSQL.append ( "\"" + detailedDataViewDTO.getDataViewName() + "\" ");
      }
    }


    //---------------------------------------------------------------
    private void constructQuerySQLWhereClause ( QueryDTO queryDTO, StringBuffer strSQL  )
    {
      //  -------- WHERE CLAUSE ---------------------------- 
      QueryWhereDTO queryWhereDTO = queryDTO.getQueryWhere() ;
      int condNum = queryWhereDTO.getConditionElements().size() ;
      //Utility.logger.debug ("  --->queryWhereDTO.getConditionElements().size(): " + condNum) ;
      if ( condNum != 0 ) 
      {
        strSQL.append ("\n  WHERE  ");
        
        Vector conditionElements = queryDTO.getQueryWhere().getConditionElements() ;
        int termType ;
        FieldToFieldTermDTO fieldToFieldTermDTO = null ; 
        FieldtoDataViewTermDTO fieldtoDataViewTermDTO = null ;
        RelationSymbolDTO relationSymbolDTO = null ;
        //Utility.logger.debug ("--->conditionElements.size()" + conditionElements.size()) ;
        for (int i=0; i<conditionElements.size() ; i++)
        {
          ConditionElement conditionElement = 
              (ConditionElement) conditionElements.elementAt(i) ;
          termType = conditionElement.getConditionElementType().getConditionElementTypeID() ;
          //= ( (Term) terms.elementAt(i) ).getTermType().getTermTypeID ();
          if ( termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL )
          {
            //Utility.logger.debug ("--->WHERE_TYPE_SYMBOL") ;

            relationSymbolDTO = ( RelationSymbolDTO ) conditionElements.elementAt(i) ; 
            strSQL.append ( relationSymbolDTO.getRelationSymbolSQL() + " ");
          }
          else if ( termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM  )
          {
            //Utility.logger.debug ("--->WHERE_TYPE_TERM") ;
            Term term = ( Term ) conditionElements.elementAt(i) ;
          
            termType = term.getTermType().getTermTypeID();
        
            if ( termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE )
            {
              //Utility.logger.debug ("--->FIELD_TO_FIELD_TYPE") ;
              fieldToFieldTermDTO = (FieldToFieldTermDTO) conditionElements.elementAt(i) ;
              strSQL.append ( fieldToFieldTermDTO.get1stOperandFieldSQLCache() + " ");
              strSQL.append ( fieldToFieldTermDTO.getOperatorSQL() + " ");
              strSQL.append ( fieldToFieldTermDTO.get2ndOperandFieldSQLCache() + " ");
            }
            else if ( termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE )
            {
              //Utility.logger.debug ("--->FIELD_TO_DATAVIEW_TYPE") ;
              fieldtoDataViewTermDTO = (FieldtoDataViewTermDTO) conditionElements.elementAt(i) ;
              strSQL.append ( fieldtoDataViewTermDTO.get1stOperandFieldSQLCache() + " ");
              strSQL.append ( fieldtoDataViewTermDTO.getOperatorSQL() + " ");

              if (fieldtoDataViewTermDTO.getRelatedDataViewTypeID() != QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE )
                strSQL.append ( "\n(\n" + fieldtoDataViewTermDTO.getRelatedDataViewSQL() + "\n)\n");
              else if (fieldtoDataViewTermDTO.getRelatedDataViewTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE )
                strSQL.append ( fieldtoDataViewTermDTO.getRelatedDataViewName() + " ");
            }
          }
        }
      }
    }


    //---------------------------------------------------------------
    private void constructQuerySQLGroupByClause ( QueryDTO queryDTO, StringBuffer strSQL )
    {
      DataViewFieldDTO dataViewFieldDTO ;
      FunctionFieldDTO functionFieldDTO ;
      int fieldTypeID ;
      String strFieldName;


      int nGroupByNum = queryDTO.getGroupBy().size() ;
      if ( nGroupByNum != 0 ) 
      {
        strSQL.append ("\n  GROUP BY  ");
        
        Vector vecGroupByList = queryDTO.getGroupBy() ;

        for (int i=0; i<vecGroupByList.size() ; i++)
        {
          fieldTypeID = ( (FieldDTO) vecGroupByList.elementAt(i) ).getFieldType().getFieldTypeID() ;
          //Utility.logger.debug ( "  fieldTypeID " + fieldTypeID ) ; 

          if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
          {
              dataViewFieldDTO = (DataViewFieldDTO)vecGroupByList.elementAt(i) ;
              strFieldName = dataViewFieldDTO.getFieldName() ;
              if ( i != 0 ) strSQL.append (", ");

              // error in group by with "AS" expression - no alias is allowed
              //strSQL.append (dataViewFieldDTO.getFieldSQLCash() + " AS ");
              //strSQL.append ("\""+dataViewFieldDTO.getFieldName() + "\" ");

              // should test if quotations are needed in field name (if there is a space in field name) 
              strSQL.append (dataViewFieldDTO.getFieldSQLCash() );

          }
          else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
          {
              //Utility.logger.debug ( "  got function field" ) ; 
              functionFieldDTO = (FunctionFieldDTO)vecGroupByList.elementAt(i) ;
              if ( i != 0 ) strSQL.append (", ");

              // error in group by with "AS" expression - no alias is allowed
              //strSQL.append ( functionFieldDTO.getFieldSQLCash() + " AS ");
              //strSQL.append ("\""+functionFieldDTO.getFieldName() + "\" ");
              strSQL.append ( functionFieldDTO.getFieldSQLCash() );
          }
        }
      }
    }


    //---------------------------------------------------------------
    private void constructQuerySQLOrderByClause ( QueryDTO queryDTO, StringBuffer strSQL )
    {
      DataViewFieldDTO dataViewFieldDTO ;
      FunctionFieldDTO functionFieldDTO ;
      int fieldTypeID ;
      String strFieldName;


      int nOrderByNum = queryDTO.getOrderBy().size() ;
      if ( nOrderByNum != 0 ) 
      {
        strSQL.append ("\n  ORDER BY  ");
        
        Vector vecOrderByList = queryDTO.getOrderBy() ;

        for (int i=0; i<vecOrderByList.size() ; i++)
        {
          fieldTypeID = ( (OrderByDTO) vecOrderByList.elementAt(i) ).getFieldDTO().getFieldType().getFieldTypeID() ;
          //Utility.logger.debug ( "  fieldTypeID " + fieldTypeID ) ; 

          if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
          {
            dataViewFieldDTO = (DataViewFieldDTO) ( (OrderByDTO)vecOrderByList.elementAt(i)).getFieldDTO() ;
            strFieldName = dataViewFieldDTO.getFieldName() ;
            if ( i != 0 ) strSQL.append (", ");

            // should test if quotations are needed in field name (if there is a space in field name) 
            strSQL.append (dataViewFieldDTO.getFieldSQLCash() );
          }
          else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
          {
            //Utility.logger.debug ( "  got function field" ) ; 
            functionFieldDTO = (FunctionFieldDTO) ((OrderByDTO)vecOrderByList.elementAt(i)).getFieldDTO() ;
            if ( i != 0 ) strSQL.append (", ");
            strSQL.append ( functionFieldDTO.getFieldSQLCash() );
          }
          strSQL.append ( " " + ( (OrderByDTO) vecOrderByList.elementAt(i) ).getOrderType() + " " ) ;
        }
      }
    }


    //---------------------------------------------------------------
    private void constructQuerySQLHavingClause ( QueryDTO queryDTO, StringBuffer strSQL  )
    {
      //  -------- HAVING CLAUSE ---------------------------- 
      QueryHavingDTO queryHavingDTO = queryDTO.getQueryHaving() ;
      int condNum = queryHavingDTO.getConditionElements().size() ;
      if ( condNum != 0 ) 
      {
        strSQL.append ("\n  HAVING  ");
        
        Vector conditionElements = queryDTO.getQueryHaving().getConditionElements() ;
        int termType ;
        FieldToFieldTermDTO fieldToFieldTermDTO = null ; 
        FieldtoDataViewTermDTO fieldtoDataViewTermDTO = null ;
        RelationSymbolDTO relationSymbolDTO = null ;
        //Utility.logger.debug ("--->conditionElements.size()" + conditionElements.size()) ;
        for (int i=0; i<conditionElements.size() ; i++)
        {
          ConditionElement conditionElement = 
              (ConditionElement) conditionElements.elementAt(i) ;
          termType = conditionElement.getConditionElementType().getConditionElementTypeID() ;
          //= ( (Term) terms.elementAt(i) ).getTermType().getTermTypeID ();
          if ( termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL )
          {
            //Utility.logger.debug ("--->HAVING_TYPE_SYMBOL") ;

            relationSymbolDTO = ( RelationSymbolDTO ) conditionElements.elementAt(i) ; 
            strSQL.append ( relationSymbolDTO.getRelationSymbolSQL() + " ");
          }
          else if ( termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM  )
          {
            //Utility.logger.debug ("--->HAVING_TYPE_TERM") ;
            Term term = ( Term ) conditionElements.elementAt(i) ;
          
            termType = term.getTermType().getTermTypeID();
        
            if ( termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE )
            {
              //Utility.logger.debug ("--->FIELD_TO_FIELD_TYPE") ;
              fieldToFieldTermDTO = (FieldToFieldTermDTO) conditionElements.elementAt(i) ;
              strSQL.append ( fieldToFieldTermDTO.get1stOperandFieldSQLCache() + " ");
              strSQL.append ( fieldToFieldTermDTO.getOperatorSQL() + " ");
              strSQL.append ( fieldToFieldTermDTO.get2ndOperandFieldSQLCache() + " ");
            }
            else if ( termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE )
            {
              //Utility.logger.debug ("--->FIELD_TO_DATAVIEW_TYPE") ;
              fieldtoDataViewTermDTO = (FieldtoDataViewTermDTO) conditionElements.elementAt(i) ;
              strSQL.append ( fieldtoDataViewTermDTO.get1stOperandFieldSQLCache() + " ");
              strSQL.append ( fieldtoDataViewTermDTO.getOperatorSQL() + " ");

              if (fieldtoDataViewTermDTO.getRelatedDataViewTypeID() != QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE )
                strSQL.append ( "\n(\n" + fieldtoDataViewTermDTO.getRelatedDataViewSQL() + "\n)\n");
              else if (fieldtoDataViewTermDTO.getRelatedDataViewTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE )
                strSQL.append ( fieldtoDataViewTermDTO.getRelatedDataViewName() + " ");
            }
          }
        }
      }
    }


    //---------------------------------------------------------------
    public String constructQuerySQL ( QueryDTO queryDTO )
    {
      StringBuffer strSQL = new StringBuffer () ;

      DataViewFieldDTO dataViewFieldDTO ;
      ConstantFieldDTO constantFieldDTO ;
      FunctionFieldDTO functionFieldDTO ;
      //FieldTypeModel fieldTypeModel ;
      int fieldTypeID ;
      String strFieldName;
      String strFieldSQLCash;
          

    if (queryDTO.getOverRideSQL() != null && queryDTO.getOverRideSQL().length()!=0)
    {
      strSQL.append(queryDTO.getOverRideSQL()  ) ;

      Utility.logger.debug("(1) = "+ strSQL);
    }
    else
    {
      constructQuerySQLSelectClause   ( queryDTO, strSQL ) ;
      
      constructQuerySQLFromClause     ( queryDTO, strSQL ) ;

      constructQuerySQLWhereClause    ( queryDTO, strSQL ) ;

      constructQuerySQLGroupByClause  ( queryDTO, strSQL ) ;

      constructQuerySQLHavingClause   ( queryDTO, strSQL ) ;
       constructQuerySQLOrderByClause  ( queryDTO, strSQL ) ;
    }

     

      //Utility.logger.debug("(2) = "+ strSQL);

      // A semi-colon at the end of the SQL statement produces an error
      // strSQL.append (" ; ");

          // System.out.println("sql before:");
          // System.out.println(strSQL);
           
           String sql = strSQL.toString();

if (queryDTO.getRunUserID()!=null)
{
    sql = sql.replaceAll("FUNC_LOG_UID",queryDTO.getRunUserID());
}

      return sql ;
    }

    //---------------------------------------------------------------
    //---------------------- third scenario -------------------------
    //---------------------------------------------------------------

    private void saveParamRecursively ( 
        Connection m_conSDSConnection,
        FunctionParameterValueModel paramValue, 
        Long DataViewID,
        Long DataViewIssue,
        Long DataViewVersion,
        int functionFieldID,
        HashMap hMapOldToNewFieldIDMapping,
        HashMap hMapConstIndexToIDMapping,
        HashMap hMapParamIndexToIDMapping,
        HashMap hMapFunctionIndexToIDMapping,
        int index,
        Vector vecFunctionFields,
        Vector    vecParameters
      )
    {
      Long lNextVal = new Long (0) ;
      Long mappedParamValueID = null ; 
      //Vw_adh_fieldHome vw_adh_fieldHome ;
      try
      {
        Utility.logger.debug ( "\n  -->saveParamRecursively ") ;
        Utility.logger.debug ( "  paramValue.getParameterValueID()  ---> " + paramValue.getParameterValueID() ) ;

        //vw_adh_fieldHome = (Vw_adh_fieldHome)
        //      Utility.getEJBHome("Vw_adh_field",
        //      "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_fieldHome");
        //Vw_adh_parameter_valueHome vw_adh_parameter_valueHome = (Vw_adh_parameter_valueHome) 
        //     Utility.getEJBHome("Vw_adh_parameter_value",
        //      "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_parameter_valueHome");
//        Vw_adh_parameter_value vw_adh_parameter_value;

        int nParamType = paramValue.getParameterValueFieldType() ;
        if (nParamType == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD)
        {
          lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;

          //Utility.logger.debug ("  long name ---->" + paramValue.getParameterValueName());

         /* Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
              DataViewID,
              DataViewIssue,
              DataViewVersion,
              lNextVal,
              // temporary code - should enlarge the name in the Field table
              //paramValue.getParameterValueName().substring(0,39),
              paramValue.getParameterValueName(),
              new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
              null,
              new Long (QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD),
              null,
              new Long (paramValue.getParameterValueDataViewID() ),
              paramValue.getParameterValueSQLCache(),
              null, //"temporary desc"
              null
              );
              */
          QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,
              DataViewID,
              DataViewIssue,
              DataViewVersion,
              lNextVal,
              // temporary code - should enlarge the name in the Field table
              //paramValue.getParameterValueName().substring(0,39),
              paramValue.getParameterValueName(),
              new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
              null,
              new Long (QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD),
              null,
              new Long (paramValue.getParameterValueDataViewID() ),
              paramValue.getParameterValueSQLCache(),
              null, //"temporary desc"
              null
              );
              
          Utility.logger.debug ( "  old dataview field id   ---> " + paramValue.getParameterValueID() ) ;
          hMapOldToNewFieldIDMapping.put( new Long (paramValue.getParameterValueID()), lNextVal);
          paramValue.setParameterValueID( lNextVal.intValue() ); 
          Utility.logger.debug ( "  added dataview field id ---> " + paramValue.getParameterValueID( ) ) ;
          mappedParamValueID = new Long ( paramValue.getParameterValueID() ) ;
        }
        else if (nParamType == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD)
        {
          Long lConstMappedID = (Long) hMapConstIndexToIDMapping.get( new Long ( paramValue.getParameterValueID() ) ) ;
          if ( lConstMappedID == null )
          {
            lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
            /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                DataViewID,
                DataViewIssue,
                DataViewVersion,
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //paramValue.getParameterValueName().substring(0,39),
                paramValue.getParameterValueName(),
                //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD),
                null,
                null, //new Long (0),
                paramValue.getParameterValueSQLCache(),
                null, //"temporary description"
                new Long (paramValue.getParameterValueID()) //new Long (constantFieldDTO.getFieldID()) // null
                );
            */
            QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,
                DataViewID,
                DataViewIssue,
                DataViewVersion,
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //paramValue.getParameterValueName().substring(0,39),
                paramValue.getParameterValueName(),
                //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD),
                null,
                null, //new Long (0),
                paramValue.getParameterValueSQLCache(),
                null, //"temporary description"
                new Long (paramValue.getParameterValueID()) //new Long (constantFieldDTO.getFieldID()) // null
                );
            
            Utility.logger.debug ( "  old const field id   ---> " + paramValue.getParameterValueID() ) ;
            hMapConstIndexToIDMapping.put( new Long (paramValue.getParameterValueID()), lNextVal);
            paramValue.setParameterValueID( lNextVal.intValue() );
            Utility.logger.debug ( "  added const field id ---> " + paramValue.getParameterValueID() ) ;
            lConstMappedID = lNextVal ;
          }
          // saving the shadow of the constant
          // the rf_object of the shadow is the id of the original const
          // shadows are not added to the index to ID mapping
          lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
          /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
              DataViewID,
              DataViewIssue,
              DataViewVersion,
              lNextVal,
              // temporary code - should enlarge the name in the Field table
              //paramValue.getParameterValueName().substring(0,39),
              paramValue.getParameterValueName(),
              //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
              new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
              null,
              new Long (QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD),
              null,
              lConstMappedID, //null, //new Long (0),
              paramValue.getParameterValueSQLCache(),
              null, //"temporary description"
              null
              );

              */
          QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,
              DataViewID,
              DataViewIssue,
              DataViewVersion,
              lNextVal,
              // temporary code - should enlarge the name in the Field table
              //paramValue.getParameterValueName().substring(0,39),
              paramValue.getParameterValueName(),
              //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
              new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
              null,
              new Long (QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD),
              null,
              lConstMappedID, //null, //new Long (0),
              paramValue.getParameterValueSQLCache(),
              null, //"temporary description"
              null
              );
              
          Utility.logger.debug ( "  old const field id   ---> " + paramValue.getParameterValueID() ) ;
          paramValue.setParameterValueID( lNextVal.intValue() );
          Utility.logger.debug ( "  added const shadow field id ---> " + paramValue.getParameterValueID() ) ;

          mappedParamValueID = new Long ( paramValue.getParameterValueID() ); //lConstMappedID ;//new Long ( paramValue.getParameterValueID() ) ;
        }
        else if (nParamType == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD)
        {
          Long lParamMappedID = (Long) hMapParamIndexToIDMapping.get( new Long ( paramValue.getParameterValueID() ) ) ;
          if ( lParamMappedID == null )
          {
            lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
            /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                DataViewID,
                DataViewIssue,
                DataViewVersion,
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //paramValue.getParameterValueName().substring(0,39),
                paramValue.getParameterValueName(),
                //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD),
                null,
                null, //new Long (0),
                paramValue.getParameterValueSQLCache(),
                null, //"temporary description"
                new Long (paramValue.getParameterValueID()) //new Long (constantFieldDTO.getFieldID()) //null
                );
                */
            QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,
                DataViewID,
                DataViewIssue,
                DataViewVersion,
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //paramValue.getParameterValueName().substring(0,39),
                paramValue.getParameterValueName(),
                //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD),
                null,
                null, //new Long (0),
                paramValue.getParameterValueSQLCache(),
                null, //"temporary description"
                new Long (paramValue.getParameterValueID()) //new Long (constantFieldDTO.getFieldID()) //null
                );
            Utility.logger.debug ( "  old param field id   ---> " + paramValue.getParameterValueID() ) ;
            hMapParamIndexToIDMapping.put( new Long (paramValue.getParameterValueID()), lNextVal);
            paramValue.setParameterValueID( lNextVal.intValue() );
            Utility.logger.debug ( "  added param field id ---> " + paramValue.getParameterValueID() ) ;
            lParamMappedID = lNextVal ;

            //Waseem
            //insert here in adh_input_param
             for (int p = 0 ; p < vecParameters.size() ; p++ )
             {
              ParameterFieldDTO parameterFieldDTO = ( (ParameterFieldDTO) vecParameters.elementAt(p) ) ;
              if(parameterFieldDTO.getFieldName().compareTo(paramValue.getParameterValueName())==0
                && parameterFieldDTO.getFieldSQLCash().compareTo(paramValue.getParameterValueSQLCache()) == 0)
              {
              Long lNextValX = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_INPUT_PARAM_ID") ;

              QueryBuilderDAO.insertVwAdhInputParam(m_conSDSConnection,  
                lNextValX, //java.lang.Long input_param_id, 
                DataViewID, //java.lang.Long data_view_id, 
                lNextVal, //java.lang.Long field_id, 
                parameterFieldDTO.getLabelText(), //java.lang.String input_param_label_text, 
                new Long (parameterFieldDTO.getControlTypeID() ), //java.lang.Long input_control_types_id, 
                null, //java.lang.String input_control_types_value, 
                new Long (p) //java.lang.Long input_param_order 
                );
              }
            }
            //////////////////////////////////
          }
          // saving the shadow of the param
          // the rf_object of the shadow is the id of the original param
          // shadows are not added to the index to ID mapping
          lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
          /*
          Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
              DataViewID,
              DataViewIssue,
              DataViewVersion,
              lNextVal,
              // temporary code - should enlarge the name in the Field table
              //paramValue.getParameterValueName().substring(0,39),
              paramValue.getParameterValueName(),
              //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
              new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
              null,
              new Long (QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD),
              null,
              lParamMappedID, //null, //new Long (0),
              paramValue.getParameterValueSQLCache(),
              null, //"temporary description"
              null
              );
          */
          QueryBuilderDAO.insertVwAdhField(m_conSDSConnection, 
              DataViewID,
              DataViewIssue,
              DataViewVersion,
              lNextVal,
              // temporary code - should enlarge the name in the Field table
              //paramValue.getParameterValueName().substring(0,39),
              paramValue.getParameterValueName(),
              //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
              new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
              null,
              new Long (QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD),
              null,
              lParamMappedID, //null, //new Long (0),
              paramValue.getParameterValueSQLCache(),
              null, //"temporary description"
              null
              );
          Utility.logger.debug ( "  old param field id   ---> " + paramValue.getParameterValueID() ) ;
          paramValue.setParameterValueID( lNextVal.intValue() );
          Utility.logger.debug ( "  added param shadow field id ---> " + paramValue.getParameterValueID() ) ;

          mappedParamValueID = new Long ( paramValue.getParameterValueID() ); //lParamMappedID ;//new Long ( paramValue.getParameterValueID() ) ;
        }
        else if (nParamType == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD)
        {
          Utility.logger.debug ( "  -->nParamType == FIELD_TYPE_FUNCTION_FIELD ") ;
          // get the index of the function,
          Long lFunctionMappedID = (Long) hMapFunctionIndexToIDMapping.get( new Long ( paramValue.getParameterValueID() ) ) ;
          if ( lFunctionMappedID == null )
          {
            FunctionFieldDTO functionFieldDTO = ( (FunctionFieldDTO) vecFunctionFields.elementAt(paramValue.getParameterValueID()) ) ;
            lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
            /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                DataViewID,
                DataViewIssue,
                DataViewVersion,
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //functionFieldDTO.getFieldName().substring(0,39),
                functionFieldDTO.getFieldName().substring(0,Math.min(functionFieldDTO.getFieldName().length(),39)),
                
                //functionFieldDTO.getFieldName(),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD),
                null,
                new Long (functionFieldDTO.getFunctionDTO().getFunctionID() ),
                functionFieldDTO.getFieldSQLCash() ,
                functionFieldDTO.getFieldDescription(),
                new Long (functionFieldDTO.getFieldID())
                );
            */
            QueryBuilderDAO.insertVwAdhField(m_conSDSConnection, 
                DataViewID,
                DataViewIssue,
                DataViewVersion,
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //functionFieldDTO.getFieldName().substring(0,39),
                functionFieldDTO.getFieldName().substring(0,Math.min(functionFieldDTO.getFieldName().length(),39)),
                
                //functionFieldDTO.getFieldName(),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD),
                null,
                new Long (functionFieldDTO.getFunctionDTO().getFunctionID() ),
                functionFieldDTO.getFieldSQLCash() ,
                functionFieldDTO.getFieldDescription(),
                new Long (functionFieldDTO.getFieldID())
                );
            Utility.logger.debug ( "  old function field id   ---> " + functionFieldDTO.getFieldID() ) ;
            hMapFunctionIndexToIDMapping.put( new Long (functionFieldDTO.getFieldID()), lNextVal);
            functionFieldDTO.setFieldID( lNextVal.intValue() ); 
            Utility.logger.debug ( "  added function field id ---> " + functionFieldDTO.getFieldID() ) ;
            lFunctionMappedID = lNextVal ;
            //mappedParamValueID = lFunctionMappedID ;

            // saving the parameters of the function
            Vector paramValues = functionFieldDTO.getFunctionParameterValues() ;
            FunctionParameterValueModel newParamValue = null ;

            for (int j=0; j<paramValues.size() ; j++)
            {
              newParamValue = ((FunctionParameterValueModel) paramValues.elementAt(j)) ;

              saveParamRecursively (m_conSDSConnection,
                newParamValue,
                DataViewID,
                DataViewIssue,
                DataViewVersion,
                functionFieldDTO.getFieldID(),
                hMapOldToNewFieldIDMapping,
                hMapConstIndexToIDMapping,
                hMapParamIndexToIDMapping,
                hMapFunctionIndexToIDMapping,
                j, 
                vecFunctionFields,
                vecParameters) ;
            }
            //Utility.logger.debug ( "  <--nParamType == FIELD_TYPE_FUNCTION_FIELD ") ;
          }

          //mappedParamValueID = lFunctionMappedID ;

          
          //else 
          //  mappedParamValueID = (Long) hMapFunctionIndexToIDMapping.get ( new Long ( paramValue.getParameterValueID() ) ) ;

          // saving the shadow of the function
          // the rf_object of the shadow is the id of the original function
          // shadows are not added to the index to ID mapping
          FunctionFieldDTO functionFieldDTO = ( (FunctionFieldDTO) vecFunctionFields.elementAt(paramValue.getParameterValueID()) ) ;
          lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
          /*
          Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
              DataViewID,
              DataViewIssue,
              DataViewVersion,
              lNextVal,
              // temporary code - should enlarge the name in the Field table
              functionFieldDTO.getFieldName().substring(0,Math.min(functionFieldDTO.getFieldName().length(),39)),
              //functionFieldDTO.getFieldName(),
              new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
              null,
              new Long (QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_RF_FIELD),
              null,
              lFunctionMappedID, //new Long (functionFieldDTO.getFunctionDTO().getFunctionID() ),
              functionFieldDTO.getFieldSQLCash() ,
              functionFieldDTO.getFieldDescription(),
              null
              );
          */
          QueryBuilderDAO.insertVwAdhField(m_conSDSConnection, 
              DataViewID,
              DataViewIssue,
              DataViewVersion,
              lNextVal,
              // temporary code - should enlarge the name in the Field table
              functionFieldDTO.getFieldName().substring(0,Math.min(functionFieldDTO.getFieldName().length(),39)),
              //functionFieldDTO.getFieldName(),
              new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
              null,
              new Long (QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_RF_FIELD),
              null,
              lFunctionMappedID, //new Long (functionFieldDTO.getFunctionDTO().getFunctionID() ),
              functionFieldDTO.getFieldSQLCash() ,
              functionFieldDTO.getFieldDescription(),
              null
              );
          Utility.logger.debug ( "  old function field id   ---> " + functionFieldDTO.getFieldID() ) ;
          //hMapFunctionIndexToIDMapping.put( new Long (functionFieldDTO.getFieldID()), lNextVal);
          functionFieldDTO.setFieldID( lNextVal.intValue() ); 
          Utility.logger.debug ( "  added function shadow field id ---> " + functionFieldDTO.getFieldID() ) ;

          mappedParamValueID = new Long ( functionFieldDTO.getFieldID() ); 
        }



        lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_PARAMETER_VALUE_ID") ;
        /*vw_adh_parameter_value = vw_adh_parameter_valueHome.create( 
              lNextVal, //parameter_value_id, 
              new Long (functionFieldID), //link_field_id, 
              //new Long (paramValue.getParameterValueID() ), //value_field_id,
              mappedParamValueID,
              new Long (index), //parameter_value_place, 
              new Long (paramValue.getParameterDefinitionID() ) //java.lang.Long parameter_definition_id 
              );*/
        QueryBuilderDAO.insertVwAdhParameterValue(m_conSDSConnection,      
              lNextVal, //parameter_value_id, 
              new Long (functionFieldID), //link_field_id, 
              //new Long (paramValue.getParameterValueID() ), //value_field_id,
              mappedParamValueID,
              new Long (index), //parameter_value_place, 
              new Long (paramValue.getParameterDefinitionID() ) //java.lang.Long parameter_definition_id 
              );
        Utility.logger.debug ( "  added relation between function field id ---> "
              + functionFieldID
              + " and parameter field id ---> "
              //+ paramValue.getParameterValueID()
              + mappedParamValueID.intValue()
              ) ;
        Utility.logger.debug ( "  <--saveParamRecursively\n") ;
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }

    /*
    To test saving with respect to displayed fields, constants, functions and 
    functions as parameters to other functions, execute the following queries

    1.a.  define const_5, const_6, const_7, const_8, const_9
    1.b.  "select const_6, const_6, const_6, const_8, const_8" 

    2.    "select product.product_name, length (product.product_name)"

    3.a.  define const_fady
    3.b.  "select const_fady, length (const_fady)"

    4.a.  define const_fady, const_3
    4.b.  "select const_fady, length ( LPAD(const_fady,3) )"

    5.    "select length (product.product_name), length (product.product_name)"

    6.a.  define const_fady
    6.b.  "select length (const_fady), length (const_fady)"

    7.a.  define const_fady, const_3
    7.b.  "select LPAD(const_fady,3), length ( LPAD(const_fady,3) )"
    */


    private void saveField ( 
        Connection m_conSDSConnection,
        FieldDTO  fieldDTO,
        Long      DataViewID,
        Long      DataViewIssue,
        Long      DataViewVersion,
        HashMap   hMapOldToNewFieldIDMapping,
        HashMap   hMapConstIndexToIDMapping,
        HashMap   hMapParamIndexToIDMapping,
        HashMap   hMapFunctionIndexToIDMapping,
        Vector    vecFunctionFields,
        int       displayFlag,
        Vector    vecParameters
      )
    {
      Long lNextVal = new Long (0) ;
      int fieldTypeID ;
      DataViewFieldDTO dataViewFieldDTO ;
      ConstantFieldDTO constantFieldDTO ;
      ParameterFieldDTO parameterFieldDTO ;
      FunctionFieldDTO functionFieldDTO ;
      //Vw_adh_fieldHome vw_adh_fieldHome ;
      try
      {
          Utility.logger.debug ( "\n  -->saveField ") ;
          //vw_adh_fieldHome = (Vw_adh_fieldHome)
          //        Utility.getEJBHome("Vw_adh_field",
          //        "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_fieldHome");

          fieldTypeID = fieldDTO.getFieldType().getFieldTypeID() ;
          if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
          {
            dataViewFieldDTO = ( (DataViewFieldDTO) fieldDTO ) ;
            //if ( hMapOldToNewFieldIDMapping.get( new Long (dataViewFieldDTO.getFieldID() ) ) == null )
            //{
              lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
              /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                  DataViewID,
                  DataViewIssue,
                  DataViewVersion,
                  lNextVal,
                  // temporary code - should enlarge the name in the Field table
                  //dataViewFieldDTO.getFieldName().substring(0,39),
                  dataViewFieldDTO.getFieldName(),
                  //new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED), //new Long (1), //
                  new Long (displayFlag),
                  null,
                  new Long (QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD),
                  null,
                  new Long (dataViewFieldDTO.getParentDataViewID() ),
                  dataViewFieldDTO.getFieldSQLCash(),
                  dataViewFieldDTO.getFieldDescription(),
                  null
                  );
                  */
              QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,  
                  DataViewID,
                  DataViewIssue,
                  DataViewVersion,
                  lNextVal,
                  // temporary code - should enlarge the name in the Field table
                  //dataViewFieldDTO.getFieldName().substring(0,39),
                  dataViewFieldDTO.getFieldName(),
                  //new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED), //new Long (1), //
                  new Long (displayFlag),
                  null,
                  new Long (QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD),
                  null,
                  new Long (dataViewFieldDTO.getParentDataViewID() ),
                  dataViewFieldDTO.getFieldSQLCash(),
                  dataViewFieldDTO.getFieldDescription(),
                  null
                  );
              //vw_adh_field.setField_sql_cash (dataViewFieldDTO.getFieldSQLCash() ) ;
              //vw_adh_field.setField_description (dataViewFieldDTO.getFieldDescription() ) ;
              //vw_adh_field.setField_rf_object( new Long (dataViewFieldDTO.getParentDataViewID() ) ) ;
              Utility.logger.debug ( "  old dataview field id   ---> " + dataViewFieldDTO.getFieldID() ) ;
              hMapOldToNewFieldIDMapping.put( new Long (dataViewFieldDTO.getFieldID()), lNextVal);
              dataViewFieldDTO.setFieldID( lNextVal.intValue() ); 
              Utility.logger.debug ( "  added dataview field id ---> " + dataViewFieldDTO.getFieldID() ) ;
            //}
          }
          else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
          {
            constantFieldDTO = ( (ConstantFieldDTO) fieldDTO ) ;
            Long lConstMappedID = (Long) hMapConstIndexToIDMapping.get( new Long ( constantFieldDTO.getFieldID() ) ) ;
            if ( lConstMappedID == null )
            {
              lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
              /*
              Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                  DataViewID,
                  DataViewIssue,
                  DataViewVersion,
                  lNextVal,
                  // temporary code - should enlarge the name in the Field table
                  //constantFieldDTO.getFieldName().substring(0,39),
                  constantFieldDTO.getFieldName(),
                  //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
                  new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                  null,
                  new Long (QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD),
                  null,
                  null, //new Long (0),
                  constantFieldDTO.getFieldSQLCash(),
                  constantFieldDTO.getFieldDescription(), //null, //"temporary description"
                  new Long (constantFieldDTO.getFieldID())
                  );
              */  
              QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,   
                  DataViewID,
                  DataViewIssue,
                  DataViewVersion,
                  lNextVal,
                  // temporary code - should enlarge the name in the Field table
                  //constantFieldDTO.getFieldName().substring(0,39),
                  constantFieldDTO.getFieldName(),
                  //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
                  new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                  null,
                  new Long (QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD),
                  null,
                  null, //new Long (0),
                  constantFieldDTO.getFieldSQLCash(),
                  constantFieldDTO.getFieldDescription(), //null, //"temporary description"
                  new Long (constantFieldDTO.getFieldID())
                  );
              //Utility.logger.debug ( "  constantFieldDTO.getFieldSQLCash()  ---> " + constantFieldDTO.getFieldSQLCash() ) ;
              //vw_adh_field.setField_sql_cash( constantFieldDTO.getFieldSQLCash() );
              //Utility.logger.debug ( "index of constant --->" +  constantFieldDTO.getFieldID() ) ;
              Utility.logger.debug ( "  old const field id   ---> " + constantFieldDTO.getFieldID() ) ;
              hMapConstIndexToIDMapping.put( new Long (constantFieldDTO.getFieldID()), lNextVal);
              constantFieldDTO.setFieldID( lNextVal.intValue() ); 
              lConstMappedID = lNextVal ;
              Utility.logger.debug ( "  added const field id ---> " + constantFieldDTO.getFieldID() ) ;
            }
            // saving the shadow of the constant
            // the rf_object of the shadow is the id of the original const
            // shadows are not added to the index to ID mapping
            lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
            /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                DataViewID,
                DataViewIssue,
                DataViewVersion,
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //constantFieldDTO.getFieldName().substring(0,39),
                constantFieldDTO.getFieldName(),
                //new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED),
                new Long (displayFlag),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD),
                null,
                //new Long ( new Long (hMapConstIndexToIDMapping.get( new Long ( constantFieldDTO.getFieldID() ) )).intValue() ),
                //new Long (constantFieldDTO.getFieldID()), //new Long (0),
                lConstMappedID ,
                constantFieldDTO.getFieldSQLCash(),
                constantFieldDTO.getFieldDescription(), //null, //"temporary description"
                null
                );*/
            QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,
                DataViewID,
                DataViewIssue,
                DataViewVersion,
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //constantFieldDTO.getFieldName().substring(0,39),
                constantFieldDTO.getFieldName(),
                //new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED),
                new Long (displayFlag),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD),
                null,
                //new Long ( new Long (hMapConstIndexToIDMapping.get( new Long ( constantFieldDTO.getFieldID() ) )).intValue() ),
                //new Long (constantFieldDTO.getFieldID()), //new Long (0),
                lConstMappedID ,
                constantFieldDTO.getFieldSQLCash(),
                constantFieldDTO.getFieldDescription(), //null, //"temporary description"
                null
                );
            //Utility.logger.debug ( "  constantFieldDTO.getFieldSQLCash()  ---> " + constantFieldDTO.getFieldSQLCash() ) ;
            //vw_adh_field.setField_sql_cash( constantFieldDTO.getFieldSQLCash() );
            //Utility.logger.debug ( "index of constant --->" +  constantFieldDTO.getFieldID() ) ;
            Utility.logger.debug ( "  old const field id   ---> " + constantFieldDTO.getFieldID() ) ;
            //hMapConstIndexToIDMapping.put( new Long (constantFieldDTO.getFieldID()), lNextVal);
            constantFieldDTO.setFieldID( lNextVal.intValue() ); 
            Utility.logger.debug ( "  added const shadow field id ---> " + constantFieldDTO.getFieldID() ) ;
          }
          else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
          {
            parameterFieldDTO = ( (ParameterFieldDTO) fieldDTO ) ;
            
            Long lParamMappedID = (Long) hMapParamIndexToIDMapping.get( new Long ( parameterFieldDTO.getFieldID() ) ) ;
            if ( lParamMappedID == null )
            {
              lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
              
              
              //System.out.println("display Flag = "+displayFlag);
              
              
              
              QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,
                  DataViewID,
                  DataViewIssue,
                  DataViewVersion,
                  lNextVal,
                  // temporary code - should enlarge the name in the Field table
                  //constantFieldDTO.getFieldName().substring(0,39),
                  parameterFieldDTO.getFieldName(),
                  //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
                  new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                  null,
                  new Long (QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD),
                  null,
                  new Long(parameterFieldDTO.getFieldID()), //new Long (0),
                  "^^"+parameterFieldDTO.getFieldID() +"_"+parameterFieldDTO.getFieldSQLCash(),
                  parameterFieldDTO.getFieldDescription(),
                  new Long (parameterFieldDTO.getFieldID())
                  );
                  
               //   System.out.println("parameter " + parameterFieldDTO.getFieldSQLCash());
               //   System.out.println("parameter id "+ parameterFieldDTO.getFieldID());                
                //  System.out.println( "^^"+new Long(parameterFieldDTO.getFieldID()) +"_"+parameterFieldDTO.getFieldSQLCash());
              //Utility.logger.debug ( "  constantFieldDTO.getFieldSQLCash()  ---> " + constantFieldDTO.getFieldSQLCash() ) ;
              //vw_adh_field.setField_sql_cash( constantFieldDTO.getFieldSQLCash() );
              //Utility.logger.debug ( "index of constant --->" +  constantFieldDTO.getFieldID() ) ;
              Utility.logger.debug ( "  old param field id   ---> " + parameterFieldDTO.getFieldID() ) ;
              hMapParamIndexToIDMapping.put ( new Long (parameterFieldDTO.getFieldID()), lNextVal);
              parameterFieldDTO.setFieldID ( lNextVal.intValue() ); 
              lParamMappedID = lNextVal ;
              Utility.logger.debug ( "  added parameterFieldDTO field id ---> " + parameterFieldDTO.getFieldID() ) ;
            }
            // saving the shadow of the param
            // the rf_object of the shadow is the id of the original param
            // shadows are not added to the index to ID mapping
            lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
            /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                DataViewID,
                DataViewIssue,
                DataViewVersion,
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //constantFieldDTO.getFieldName().substring(0,39),
                parameterFieldDTO.getFieldName(),
                //new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED),
                new Long (displayFlag),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD),
                null,
                //new Long ( new Long (hMapConstIndexToIDMapping.get( new Long ( constantFieldDTO.getFieldID() ) )).intValue() ),
                //new Long (constantFieldDTO.getFieldID()), //new Long (0),
                lParamMappedID,
                parameterFieldDTO.getFieldSQLCash(),
                parameterFieldDTO.getFieldDescription(),
                null
                );
                */
            QueryBuilderDAO.insertVwAdhField(m_conSDSConnection, 
                DataViewID,
                DataViewIssue,
                DataViewVersion,
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //constantFieldDTO.getFieldName().substring(0,39),
                parameterFieldDTO.getFieldName(),
                //new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED),
                new Long (displayFlag),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD),
                null,
                //new Long ( new Long (hMapConstIndexToIDMapping.get( new Long ( constantFieldDTO.getFieldID() ) )).intValue() ),
                //new Long (constantFieldDTO.getFieldID()), //new Long (0),
                lParamMappedID,
                parameterFieldDTO.getFieldSQLCash(),
                parameterFieldDTO.getFieldDescription(),
                null
                );
            Utility.logger.debug ( "  old param field id   ---> " + parameterFieldDTO.getFieldID() ) ;
            parameterFieldDTO.setFieldID( lNextVal.intValue() ); 
            Utility.logger.debug ( "  added param shadow field id ---> " + parameterFieldDTO.getFieldID() ) ;


            //Vw_adh_input_paramHome vw_adh_input_paramHome = (Vw_adh_input_paramHome)
            //    Utility.getEJBHome("Vw_adh_input_param",
            //    "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_input_paramHome");

            // vw_adh_input_param = vw_adh_input_paramHome.create( java.lang.Long input_param_id, java.lang.Long field_id, java.lang.String input_param_label_text, java.lang.Long input_control_types_id, java.lang.String input_control_types_value, java.lang.Long input_param_order );
            lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_INPUT_PARAM_ID") ;
            /*
            Vw_adh_input_param vw_adh_input_param = vw_adh_input_paramHome.create( 
              lNextVal, //java.lang.Long input_param_id, 
              DataViewID, //java.lang.Long data_view_id, 
              lParamMappedID, //new Long ( parameterFieldDTO.getFieldID () ), //java.lang.Long field_id, 
              parameterFieldDTO.getLabelText(), //java.lang.String input_param_label_text, 
              new Long (parameterFieldDTO.getControlTypeID() ), //java.lang.Long input_control_types_id, 
              null, //java.lang.String input_control_types_value, 
              new Long (parameterFieldDTO.getOrder()) //java.lang.Long input_param_order 
              );*/
            QueryBuilderDAO.insertVwAdhInputParam(m_conSDSConnection,
              lNextVal, //java.lang.Long input_param_id, 
              DataViewID, //java.lang.Long data_view_id, 
              lParamMappedID, //new Long ( parameterFieldDTO.getFieldID () ), //java.lang.Long field_id, 
              parameterFieldDTO.getLabelText(), //java.lang.String input_param_label_text, 
              new Long (parameterFieldDTO.getControlTypeID() ), //java.lang.Long input_control_types_id, 
              null, //java.lang.String input_control_types_value, 
              new Long (parameterFieldDTO.getOrder()) //java.lang.Long input_param_order 
              );  
          }
          else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
          {
            functionFieldDTO = ( (FunctionFieldDTO) fieldDTO ) ;
            Long lFunctionMappedID = (Long) hMapFunctionIndexToIDMapping.get( new Long ( functionFieldDTO.getFieldID() ) ) ;
            if ( lFunctionMappedID == null )
            {
              lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
              /*
              Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                  DataViewID,
                  DataViewIssue,
                  DataViewVersion,
                  lNextVal,
                  // temporary code - should enlarge the name in the Field table
                  //functionFieldDTO.getFieldName().substring(0,39),
                  functionFieldDTO.getFieldName().substring(0,Math.min(functionFieldDTO.getFieldName().length(),39)),                  
                  //functionFieldDTO.getFieldName(),
                  new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                  null,
                  new Long (QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD),
                  null,
                  new Long (functionFieldDTO.getFunctionDTO().getFunctionID() ),
                  functionFieldDTO.getFieldSQLCash() ,
                  functionFieldDTO.getFieldDescription(),
                  new Long (functionFieldDTO.getFieldID())
                  );
              */  
              QueryBuilderDAO.insertVwAdhField(m_conSDSConnection, 
                  DataViewID,
                  DataViewIssue,
                  DataViewVersion,
                  lNextVal,
                  // temporary code - should enlarge the name in the Field table
                  //functionFieldDTO.getFieldName().substring(0,39),
                  functionFieldDTO.getFieldName().substring(0,Math.min(functionFieldDTO.getFieldName().length(),39)),                  
                  //functionFieldDTO.getFieldName(),
                  new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                  null,
                  new Long (QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD),
                  null,
                  new Long (functionFieldDTO.getFunctionDTO().getFunctionID() ),
                  functionFieldDTO.getFieldSQLCash() ,
                  functionFieldDTO.getFieldDescription(),
                  new Long (functionFieldDTO.getFieldID())
                  );
              Utility.logger.debug ( "  old function field id   ---> " + functionFieldDTO.getFieldID() ) ;
              hMapFunctionIndexToIDMapping.put( new Long (functionFieldDTO.getFieldID()), lNextVal);
              functionFieldDTO.setFieldID( lNextVal.intValue() ); 
              Utility.logger.debug ( "  added function field id ---> " + functionFieldDTO.getFieldID() ) ;
              lFunctionMappedID = lNextVal ;

              // saving the parameters of the function
              Vector paramValues = functionFieldDTO.getFunctionParameterValues() ;
              FunctionParameterValueModel paramValue = null ;

              for (int j=0; j<paramValues.size() ; j++)
              {
                paramValue = ((FunctionParameterValueModel) paramValues.elementAt(j)) ;

                saveParamRecursively (m_conSDSConnection,
                  paramValue,
                  DataViewID,
                  DataViewIssue,
                  DataViewVersion,
                  functionFieldDTO.getFieldID(),
                  hMapOldToNewFieldIDMapping,
                  hMapConstIndexToIDMapping,
                  hMapParamIndexToIDMapping,
                  hMapFunctionIndexToIDMapping,
                  j, 
                  vecFunctionFields,
                  vecParameters) ;
              }
            }
            // saving the shadow of the function
            // the rf_object of the shadow is the id of the original function
            // shadows are not added to the index to ID mapping
            //if ( lFunctionMappedID == null )
            //{
              lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
              /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                  DataViewID,
                  DataViewIssue,
                  DataViewVersion,
                  lNextVal,
                  // temporary code - should enlarge the name in the Field table
                 // functionFieldDTO.getFieldName().substring(0,39),
                  functionFieldDTO.getFieldName().substring(0,Math.min(functionFieldDTO.getFieldName().length(),39)),
                  
                  //functionFieldDTO.getFieldName(),
                  //new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED),
                  new Long (displayFlag),
                  null,
                  new Long (QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_RF_FIELD),
                  null,
                  lFunctionMappedID, //new Long (functionFieldDTO.getFunctionDTO().getFunctionID() ),
                  functionFieldDTO.getFieldSQLCash() ,
                  functionFieldDTO.getFieldDescription(),
                  null
                  );*/
              QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,  
                  DataViewID,
                  DataViewIssue,
                  DataViewVersion,
                  lNextVal,
                  // temporary code - should enlarge the name in the Field table
                 // functionFieldDTO.getFieldName().substring(0,39),
                  functionFieldDTO.getFieldName().substring(0,Math.min(functionFieldDTO.getFieldName().length(),39)),
                  
                  //functionFieldDTO.getFieldName(),
                  //new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED),
                  new Long (displayFlag),
                  null,
                  new Long (QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_RF_FIELD),
                  null,
                  lFunctionMappedID, //new Long (functionFieldDTO.getFunctionDTO().getFunctionID() ),
                  functionFieldDTO.getFieldSQLCash() ,
                  functionFieldDTO.getFieldDescription(),
                  null
                  );
              Utility.logger.debug ( "  old function field id   ---> " + functionFieldDTO.getFieldID() ) ;
              functionFieldDTO.setFieldID( lNextVal.intValue() ); 
              Utility.logger.debug ( "  added function shadow field id ---> " + functionFieldDTO.getFieldID() ) ;

              // saving the parameters of the function
              /*Vector paramValues = functionFieldDTO.getFunctionParameterValues() ;
              FunctionParameterValueModel paramValue = null ;

              for (int j=0; j<paramValues.size() ; j++)
              {
                paramValue = ((FunctionParameterValueModel) paramValues.elementAt(j)) ;

                saveParamRecursively (
                  paramValue,
                  DataViewID,
                  DataViewIssue,
                  DataViewVersion,
                  functionFieldDTO.getFieldID(),
                  hMapOldToNewFieldIDMapping,
                  hMapConstIndexToIDMapping,
                  hMapFunctionIndexToIDMapping,
                  j, 
                  vecFunctionFields) ;
              }*/
            //}


            
          }
          Utility.logger.debug ( "  -- Begin field HashMap ------------------------------------------------------ ") ;
          Object arrKey[] = hMapOldToNewFieldIDMapping.keySet().toArray() ;
          for (int i = 0 ; i < arrKey.length ; i++ )
          {
            Utility.logger.debug ( "  " + (Long) arrKey[i] + "\n    ----> " + (Long) hMapOldToNewFieldIDMapping.get ((Long) arrKey[i]) ) ;
          }
          Utility.logger.debug ( "  -- End HashMap ------------------------------------------------------ ") ;
          Utility.logger.debug ( "  -- Begin Const HashMap ------------------------------------------------------ ") ;
          arrKey = hMapConstIndexToIDMapping.keySet().toArray() ;
          for (int i = 0 ; i < arrKey.length ; i++ )
          {
            Utility.logger.debug ( "  " + (Long) arrKey[i] + "\n    ----> " + (Long) hMapConstIndexToIDMapping.get ((Long) arrKey[i]) ) ;
          }
          Utility.logger.debug ( "  -- End HashMap ------------------------------------------------------ ") ;
          Utility.logger.debug ( "  -- Begin Param HashMap ------------------------------------------------------ ") ;
          arrKey = hMapParamIndexToIDMapping.keySet().toArray() ;
          for (int i = 0 ; i < arrKey.length ; i++ )
          {
            Utility.logger.debug ( "  " + (Long) arrKey[i] + "\n    ----> " + (Long) hMapParamIndexToIDMapping.get ((Long) arrKey[i]) ) ;
          }
          Utility.logger.debug ( "  -- End HashMap ------------------------------------------------------ ") ;
          Utility.logger.debug ( "  -- Begin func HashMap ------------------------------------------------------ ") ;
          arrKey = hMapFunctionIndexToIDMapping.keySet().toArray() ;
          for (int i = 0 ; i < arrKey.length ; i++ )
          {
            Utility.logger.debug ( "  " + (Long) arrKey[i] + "\n    ----> " + (Long) hMapFunctionIndexToIDMapping.get ((Long) arrKey[i]) ) ;
          }
          Utility.logger.debug ( "  -- End HashMap ------------------------------------------------------ ") ;
          Utility.logger.debug ( "  <--saveField\n") ;
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }

    //---------------------------------------------------------------
    //---------------------- third scenario -------------------------
    //---------------------------------------------------------------
    /*
     * 
     * private Vw_adh_data_view saveQueryDTONewDataView ( Connection m_conSDSConnection,QueryDTO queryDTO )
    {
      Long lNextDataViewID = null ;
      Long lNextIssue = null ;
      Long lNextVersion = null ;
      Long lNextStatus = null ;
      Vw_adh_data_view vw_adh_data_view = null ;
      try
      {
        // ---------------------- saving the new DataView row
        //Vw_adh_data_viewHome vw_adh_data_viewHome = (Vw_adh_data_viewHome)
        //          Utility.getEJBHome("Vw_adh_data_view",
        //         "com.mobinil.sds.core.system.gn.dataview.dao.cmp.Vw_adh_data_viewHome");

        lNextDataViewID = Utility.getSequenceNextVal(m_conSDSConnection, "SEQ_ADH_DATA_VIEW_ID") ;
        if (queryDTO.getQuerySaveAs() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE )
        {
          // if it is a new query:
          // 1. a new issue ID is generated 
          // 2. the version is always 1
          // 3. the status is always active
          lNextIssue   = Utility.getSequenceNextVal(m_conSDSConnection, "SEQ_ADH_DATA_VIEW_ISSUE") ;
          lNextVersion = new Long ( 1 ) ;
          //lNextStatus  = new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE ) ;
        }
        else if (queryDTO.getQuerySaveAs() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_TRUE )
        {
          // if it is not a new query, the list of old versions of the same query 
          //   is retrieved and inactivated:
          // 1. The issue ID is saved as the same old issue which is retrieved from the HashMap  
          // 2. Its version is incremented to be the new version of the new query. 
          // 3. Its status is inactivated and the status of the new query is active

          lNextIssue   = new Long ( queryDTO.getQueryIssue() ) ;
          Vector vecVersionsList = DataViewDAO.getDataViewVersionsList (m_conSDSConnection, lNextIssue.intValue() ) ;
          int nDataViewID ;
          int nStatusID ;
          boolean bExit = false ;
          for ( int i=0 ; (i<vecVersionsList.size()) && (bExit==false) ; i++)
          {
            DataViewModel dataViewModel = (DataViewModel) vecVersionsList.elementAt(i) ;
            if ( i==0 )
                lNextVersion = new Long ( dataViewModel.getDataViewVersion() + 1 ) ;
            nStatusID = dataViewModel.getDataViewStatusID() ;

            if ( nStatusID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE)
            {
              nDataViewID = dataViewModel.getDataViewID() ;
              //Vw_adh_data_viewPK primaryKey = new Vw_adh_data_viewPK (new Long (nDataViewID) ) ;
              //Vw_adh_data_view vw_adh_data_view_original = vw_adh_data_viewHome.findByPrimaryKey(primaryKey);
              //vw_adh_data_view_original.setData_view_status_id(new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_INACTIVE) ) ;
              QueryBuilderDAO.updateVwAdhDataViewStatus(m_conSDSConnection,nDataViewID,QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_INACTIVE);
              bExit = true ;
            }
          }
        }

        debug ( "saveQueryDTONewDataView" ,  "lNextDataViewID: " + lNextDataViewID + 
                      " lNextIssue " + lNextIssue + " lNextVersion " + lNextVersion) ; 

        //vw_adh_data_view = vw_adh_data_viewHome.create ( 
        //    lNextDataViewID, 
        //    lNextIssue,
        //    lNextVersion,
        //    queryDTO.getQueryName(), 
        //    new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY ), //new Long (1),
        //    null, 
        //    new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE ), //lNextStatus,
        //    null, 
        //    queryDTO.getQueryDescription(), 
        //    new Long (queryDTO.getQueryUnique()) ) ;
            
        QueryBuilderDAO.insertVwAdhDataView(m_conSDSConnection,
            lNextDataViewID, 
            lNextIssue,
            lNextVersion,
            queryDTO.getQueryName(), 
            new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY ), //new Long (1),
            null, 
            new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE ), //lNextStatus,
            null, 
            queryDTO.getQueryDescription(), 
            new Long (queryDTO.getQueryUnique()) ) ;
        //Vw_adh_universe_definitionHome vw_adh_universe_definitionHome = (Vw_adh_universe_definitionHome)
        //            Utility.getEJBHome("Vw_adh_universe_definition",
        //            "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_universe_definitionHome");

        //Vw_adh_universe_definition vw_adh_universe_definition = vw_adh_universe_definitionHome.create( 
        //  new Long (queryDTO.getQueryUniverse()), // java.lang.Long universe_id, 
        //  lNextDataViewID //java.lang.Long data_view_id 
        //  );
        QueryBuilderDAO.insertAdhUniverseDefinition(m_conSDSConnection,queryDTO.getQueryUniverse(),lNextDataViewID);

        Utility.logger.debug   ( "  added dataview id ---> " + lNextDataViewID + "  in universe ---> " + queryDTO.getQueryUniverse() ) ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
      return vw_adh_data_view ;
    }*/
  
    private DataViewModel saveQueryDTONewDataView ( Connection m_conSDSConnection,QueryDTO queryDTO )
    {
      Long lNextDataViewID = null ;
      Long lNextIssue = null ;
      Long lNextVersion = null ;
      Long lNextStatus = null ;
      DataViewModel dataViewModel = null;
     // Vw_adh_data_view vw_adh_data_view = null ;
      try
      {
        // ---------------------- saving the new DataView row
        //Vw_adh_data_viewHome vw_adh_data_viewHome = (Vw_adh_data_viewHome)
        //          Utility.getEJBHome("Vw_adh_data_view",
        //         "com.mobinil.sds.core.system.gn.dataview.dao.cmp.Vw_adh_data_viewHome");

        lNextDataViewID = Utility.getSequenceNextVal(m_conSDSConnection, "SEQ_ADH_DATA_VIEW_ID") ;
        if (queryDTO.getQuerySaveAs() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_FALSE )
        {
          // if it is a new query:
          // 1. a new issue ID is generated 
          // 2. the version is always 1
          // 3. the status is always active
          lNextIssue   = Utility.getSequenceNextVal(m_conSDSConnection, "SEQ_ADH_DATA_VIEW_ISSUE") ;
          lNextVersion = new Long ( 1 ) ;
          //lNextStatus  = new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE ) ;
        }
        else if (queryDTO.getQuerySaveAs() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_TRUE )
        {
          // if it is not a new query, the list of old versions of the same query 
          //   is retrieved and inactivated:
          // 1. The issue ID is saved as the same old issue which is retrieved from the HashMap  
          // 2. Its version is incremented to be the new version of the new query. 
          // 3. Its status is inactivated and the status of the new query is active

          lNextIssue   = new Long ( queryDTO.getQueryIssue() ) ;
          Vector vecVersionsList = DataViewDAO.getDataViewVersionsList (m_conSDSConnection, lNextIssue.intValue() ) ;
          int nDataViewID ;
          int nStatusID ;
          boolean bExit = false ;
          
          for ( int i=0 ; (i<vecVersionsList.size()) && (bExit==false) ; i++)
          {
            dataViewModel = (DataViewModel) vecVersionsList.elementAt(i) ;
            if ( i==0 )
                lNextVersion = new Long ( dataViewModel.getDataViewVersion() + 1 ) ;
            nStatusID = dataViewModel.getDataViewStatusID() ;

            if ( nStatusID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE)
            {
              nDataViewID = dataViewModel.getDataViewID() ;
              //Vw_adh_data_viewPK primaryKey = new Vw_adh_data_viewPK (new Long (nDataViewID) ) ;
              //Vw_adh_data_view vw_adh_data_view_original = vw_adh_data_viewHome.findByPrimaryKey(primaryKey);
              //vw_adh_data_view_original.setData_view_status_id(new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_INACTIVE) ) ;
              QueryBuilderDAO.updateVwAdhDataViewStatus(m_conSDSConnection,nDataViewID,QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_INACTIVE);
              bExit = true ;
            }
          }
        }

        debug ( "saveQueryDTONewDataView" ,  "lNextDataViewID: " + lNextDataViewID + 
                      " lNextIssue " + lNextIssue + " lNextVersion " + lNextVersion) ; 

        /*vw_adh_data_view = vw_adh_data_viewHome.create ( 
            lNextDataViewID, 
            lNextIssue,
            lNextVersion,
            queryDTO.getQueryName(), 
            new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY ), //new Long (1),
            null, 
            new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE ), //lNextStatus,
            null, 
            queryDTO.getQueryDescription(), 
            new Long (queryDTO.getQueryUnique()) ) ;*/
        QueryBuilderDAO.insertVwAdhDataView(m_conSDSConnection,
            lNextDataViewID, 
            lNextIssue,
            lNextVersion,
            queryDTO.getQueryName(), 
            new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY ), //new Long (1),
            null, 
            new Long ( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE ), //lNextStatus,
            null, 
            queryDTO.getQueryDescription(), 
            new Long (queryDTO.getQueryUnique()) ) ;


         int intDataViewId = Integer.parseInt(lNextDataViewID+"");
         int intDataViewIssue = Integer.parseInt(lNextIssue+"");
         int intDataViewVersion = Integer.parseInt(lNextVersion+"");

         dataViewModel = new DataViewModel();          
         dataViewModel.setDataViewID(intDataViewId);
         dataViewModel.setDataViewIssue(intDataViewIssue);
         dataViewModel.setDataViewVersion(intDataViewVersion);
        //Vw_adh_universe_definitionHome vw_adh_universe_definitionHome = (Vw_adh_universe_definitionHome)
        //            Utility.getEJBHome("Vw_adh_universe_definition",
        //            "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_universe_definitionHome");

        //Vw_adh_universe_definition vw_adh_universe_definition = vw_adh_universe_definitionHome.create( 
        //  new Long (queryDTO.getQueryUniverse()), // java.lang.Long universe_id, 
        //  lNextDataViewID //java.lang.Long data_view_id 
        //  );
        QueryBuilderDAO.insertAdhUniverseDefinition(m_conSDSConnection,queryDTO.getQueryUniverse(),lNextDataViewID);

        Utility.logger.debug   ( "  added dataview id ---> " + lNextDataViewID + "  in universe ---> " + queryDTO.getQueryUniverse() ) ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
      return dataViewModel ;
    }
    //---------------------------------------------------------------
    private void saveQueryDTOFromList (Connection m_conSDSConnection,QueryDTO queryDTO, Long argDataViewID )
    {
      Long lNextVal = null ;
      try
      {
        Utility.logger.debug ( "\n  -->saveQueryDTOFromList ") ;
        // ---------------------- saving the list of dataviews in the from clause
        HashMap hMapDetailedDataViews = queryDTO.getDetailedDataViews ();
        DetailedDataViewDTO detailedDataViewDTO ;
        Object arrKeys[] = hMapDetailedDataViews.keySet().toArray();
        for (int i=0; i<arrKeys.length ; i++)
        {
          //Vw_adh_fromHome vw_adh_fromHome = (Vw_adh_fromHome)
          //        Utility.getEJBHome("Vw_adh_from",
          //        "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_fromHome");
          lNextVal = Utility.getSequenceNextVal(m_conSDSConnection,"SEQ_ADH_FROM_ID") ;
          
          detailedDataViewDTO = (DetailedDataViewDTO)hMapDetailedDataViews.get((Integer)arrKeys[i]);
          /*Vw_adh_from vw_adh_from = vw_adh_fromHome.create (
              lNextVal, 
              //vw_adh_data_view.getData_view_id() ,
              argDataViewID ,
              new Long (detailedDataViewDTO.getDataViewID() )
              ) ;*/
          QueryBuilderDAO.insertVwAdhFrom(m_conSDSConnection,    
              lNextVal, 
              //vw_adh_data_view.getData_view_id() ,
              argDataViewID ,
              new Long (detailedDataViewDTO.getDataViewID() )
              ) ;
          Utility.logger.debug ( "  added from: " + detailedDataViewDTO.getDataViewName() + " id ---> " + lNextVal ) ;
        }
        //Utility.logger.debug ("2. sizof of constants: " + queryDTO.getConstantsFields().size() ) ;
        Utility.logger.debug ( "  <--saveQueryDTOFromList\n") ;

      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
    }


    //---------------------------------------------------------------
    private void saveQueryDTODisplayedFields (Connection m_conSDSConnection, QueryDTO queryDTO, 
          Long    argDataViewID,
          Long    argDataViewIssue,
          Long    argDataViewVersion,
          HashMap hMapOldToNewFieldIDMapping,
          HashMap hMapConstIndexToIDMapping,
          HashMap hMapParamIndexToIDMapping, 
          HashMap hMapFunctionIndexToIDMapping
          )
    {
      try
      {
        Utility.logger.debug ( "\n  -->saveQueryDTODisplayedFields ") ;
        // ---------------------- saving the new displayed fields of the DataView
        Vector vecFields = queryDTO.getDisplayedFields() ;
        Vector vecFunctionFields = queryDTO.getFunctionsFields();
        Vector vecParameters = queryDTO.getParametersFields() ;
        FieldDTO fieldDTO ;

        for (int i = 0 ; i < vecFields.size() ; i++ )
        {
          fieldDTO = ( (FieldDTO) vecFields.elementAt(i) ) ;

          Utility.logger.debug("saving field = " + fieldDTO.getFieldID() + "  field NAme = " + fieldDTO.getFieldName() +"  display flag = " +QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED); 
          saveField ( m_conSDSConnection,
                fieldDTO,
                argDataViewID,
                argDataViewIssue,
                argDataViewVersion,
                hMapOldToNewFieldIDMapping,
                hMapConstIndexToIDMapping,
                hMapParamIndexToIDMapping,
                hMapFunctionIndexToIDMapping,
                vecFunctionFields,
                QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED,
                vecParameters
                );
        }
        Utility.logger.debug ( "  <--saveQueryDTODisplayedFields\n") ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
    }


    //---------------------------------------------------------------
    private void saveQueryDTOGroupByFields ( Connection m_conSDSConnection,QueryDTO queryDTO, 
          Long    argDataViewID,
          Long    argDataViewIssue,
          Long    argDataViewVersion,
          HashMap hMapOldToNewFieldIDMapping,
          HashMap hMapConstIndexToIDMapping,
          HashMap hMapParamIndexToIDMapping,
          HashMap hMapFunctionIndexToIDMapping
          )
    {
      try
      {
        Utility.logger.debug ( "\n  -->saveQueryDTOGroupByFields ") ;
        // ---------------------- saving the group by list of fields -------
        Vector vecFields = queryDTO.getGroupBy()  ;
        //Utility.logger.debug ( "  -->vecFields " + vecFields) ;
        Vector vecFunctionFields = queryDTO.getFunctionsFields();
        Vector vecParameters = queryDTO.getParametersFields() ;
        
        FieldDTO fieldDTO ;
        int fieldTypeID ;
        Long fieldID = null ;
        Long lNextVal = null ;

        for (int i = 0 ; i < vecFields.size() ; i++ )
        {
          fieldDTO = ( (FieldDTO) vecFields.elementAt(i) ) ;
          saveField ( m_conSDSConnection,
                fieldDTO,
                argDataViewID,
                argDataViewIssue,
                argDataViewVersion,
                hMapOldToNewFieldIDMapping,
                hMapConstIndexToIDMapping,
                hMapParamIndexToIDMapping,
                hMapFunctionIndexToIDMapping,
                vecFunctionFields,
                QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED,
                vecParameters
                );
                
          // Check the field type, accordingly check which HashMap
          /*fieldTypeID = fieldDTO.getFieldType().getFieldTypeID() ;
          if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
          {
            Utility.logger.debug ( "  found fieldTypeID FIELD_TYPE_DATAVIEW_FIELD ") ;
            Utility.logger.debug ( "  fieldDTO.getFieldID() " + fieldDTO.getFieldID()) ;
            fieldID = (Long) hMapOldToNewFieldIDMapping.get(new Long (fieldDTO.getFieldID()) ) ;
            Utility.logger.debug ( "  mapped fieldID " + fieldID.intValue()) ;
          }
          else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
          {
            Utility.logger.debug ( "  found fieldTypeID FIELD_TYPE_FUNCTION_FIELD ") ;
            Utility.logger.debug ( "  fieldDTO.getFieldID() " + fieldDTO.getFieldID()) ;
            fieldID = (Long) hMapFunctionIndexToIDMapping.get(new Long (fieldDTO.getFieldID()) ) ;
            Utility.logger.debug ( "  mapped fieldID " + fieldID.intValue()) ;
          }*/

          // Add mapping between argDataViewID and the mapped ID of the field

          //Vw_adh_group_byHome vw_adh_group_byHome = (Vw_adh_group_byHome)
          //        Utility.getEJBHome("Vw_adh_group_by",
          //        "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_group_byHome");
          lNextVal = Utility.getSequenceNextVal(m_conSDSConnection,"SEQ_ADH_GROUP_BY_ID") ;
          /*Vw_adh_group_by vw_adh_group_by = vw_adh_group_byHome.create ( 
              lNextVal, //java.lang.Long group_by_id, 
              argDataViewID, //java.lang.Long data_view_id, 
              null , //java.lang.Long data_view_issue, 
              null , //java.lang.Long data_view_version, 
              new Long ( fieldDTO.getFieldID() ) , //java.lang.Long group_field_id, 
              null , //java.lang.String group_field_name, 
              new Long (i) //java.lang.Long group_place 
              );*/
          QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,
              lNextVal, //java.lang.Long group_by_id, 
              argDataViewID, //java.lang.Long data_view_id, 
              null , //java.lang.Long data_view_issue, 
              null , //java.lang.Long data_view_version, 
              new Long ( fieldDTO.getFieldID() ) , //java.lang.Long group_field_id, 
              null , //java.lang.String group_field_name, 
              new Long (i) //java.lang.Long group_place 
              );
        }
        Utility.logger.debug ( "  <--saveQueryDTOGroupByFields\n") ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
    }




    //---------------------------------------------------------------
    private void saveQueryDTOOrderByFields (Connection m_conSDSConnection, QueryDTO queryDTO, 
          Long    argDataViewID,
          Long    argDataViewIssue,
          Long    argDataViewVersion,
          HashMap hMapOldToNewFieldIDMapping,
          HashMap hMapConstIndexToIDMapping,
          HashMap hMapParamIndexToIDMapping,
          HashMap hMapFunctionIndexToIDMapping
          )
    {
      FieldDTO fieldDTO ;
      int fieldTypeID ;
      Long fieldID = null ;
      Long lNextVal = null ;
      OrderByDTO orderByDTO = null ;
      try
      {
        Utility.logger.debug ( "\n  -->saveQueryDTOOrderByFields ") ;
        // ---------------------- saving the order by list of fields -------
        Vector vecFields = queryDTO.getOrderBy() ;
        //Utility.logger.debug ( "  -->vecFields " + vecFields) ;
        Vector vecFunctionFields = queryDTO.getFunctionsFields();
        Vector vecParameters = queryDTO.getParametersFields() ;
        
        for (int i = 0 ; i < vecFields.size() ; i++ )
        {
          orderByDTO = ( (OrderByDTO) vecFields.elementAt(i) ) ;
          fieldDTO = orderByDTO.getFieldDTO() ;
          saveField ( m_conSDSConnection,
                fieldDTO,
                argDataViewID,
                argDataViewIssue,
                argDataViewVersion,
                hMapOldToNewFieldIDMapping,
                hMapConstIndexToIDMapping,
                hMapParamIndexToIDMapping,
                hMapFunctionIndexToIDMapping,
                vecFunctionFields,
                QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED,
                vecParameters
                );

          // getting the ID of the order by type (asc, desc)
          Long lOrderTypeID = DataViewDAO.switchOrderByTypeToID ( 
                                m_conSDSConnection, orderByDTO.getOrderType() ) ;

          Utility.logger.debug ( "  lOrderTypeID: " + lOrderTypeID) ;
          // Add mapping between argDataViewID and the mapped ID of the field
          //Vw_adh_order_byHome vw_adh_order_byHome = (Vw_adh_order_byHome)
          //        Utility.getEJBHome("Vw_adh_order_by",
          //       "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_order_byHome");
          lNextVal = Utility.getSequenceNextVal(m_conSDSConnection,"SEQ_ADH_ORDER_BY_ID") ;
          /*Vw_adh_order_by vw_adh_order_by = vw_adh_order_byHome.create(
                lNextVal, //java.lang.Long order_by_id, 
                argDataViewID, //java.lang.Long data_view_id, 
                new Long ( fieldDTO.getFieldID() ) , //java.lang.Long field_id,
                lOrderTypeID, //java.lang.Long order_type_id,  
                new Long (i) //java.lang.Long order_place 
                );*/
          QueryBuilderDAO.insertVwAdhOrderBy(m_conSDSConnection,      
                lNextVal, //java.lang.Long order_by_id, 
                argDataViewID, //java.lang.Long data_view_id, 
                new Long ( fieldDTO.getFieldID() ) , //java.lang.Long field_id,
                lOrderTypeID, //java.lang.Long order_type_id,  
                new Long (i) //java.lang.Long order_place 
                );
          Utility.logger.debug ( "  added order by between field: " + fieldDTO.getFieldID() + " and lOrderTypeID: " + lOrderTypeID) ;
        }
        Utility.logger.debug ( "  <--saveQueryDTOOrderByFields\n") ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
    }



    //---------------------------------------------------------------
    private void saveQueryDTOConstantsList ( 
          Connection m_conSDSConnection,
          QueryDTO queryDTO ,
          Long    argDataViewID,
          Long    argDataViewIssue,
          Long    argDataViewVersion,
          HashMap hMapConstIndexToIDMapping )
    {
      //Vw_adh_fieldHome vw_adh_fieldHome ;
      ConstantFieldDTO constantFieldDTO = null ;
      Long lNextVal = null ;
      try
      {
        // ---------------------- saving the list of constant fields
        //Utility.logger.debug ("3. sizeof of constants: " + queryDTO.getConstantsFields().size() ) ;
        Utility.logger.debug ( "\n  -->saveQueryDTOConstantsList ") ;

        Vector vecConstants = queryDTO.getConstantsFields() ;
        //vw_adh_fieldHome = (Vw_adh_fieldHome)
        //        Utility.getEJBHome("Vw_adh_field",
        //        "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_fieldHome");

        //Utility.logger.debug ( "--finised added displayed fields") ;
        //Utility.logger.debug ( "--started adding list of constants " + vecConstants.size()) ;
        if (vecConstants != null)
        {
          for (int i = 0 ; i < vecConstants.size() ; i++ )
          {
            //System.out.print ( "  checking for constant in index: " + i ) ;
            //constIndex =  ;
            if ( hMapConstIndexToIDMapping.get( new Long (i)) == null )
            {
              //Utility.logger.debug ( " not found. adding it: " ) ;
              constantFieldDTO = ( (ConstantFieldDTO) vecConstants.elementAt(i) ) ;
              lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
              /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                argDataViewID , // vw_adh_data_view.getData_view_id(),
                argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //constantFieldDTO.getFieldName().substring(0,39),
                constantFieldDTO.getFieldName(),
                //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD),
                null,
                null,
                constantFieldDTO.getFieldSQLCash(),
                constantFieldDTO.getFieldDescription(),
                new Long (i)
                );
                */
               // System.out.println("saving data constants ");
               // System.out.println("contant field name= " + constantFieldDTO.getFieldName());
               // System.out.println("constant field value = " + constantFieldDTO.getFieldType());
               // System.out.println("constant field sql cach"+constantFieldDTO.getFieldSQLCash());
              QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,
                argDataViewID , // vw_adh_data_view.getData_view_id(),
                argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //constantFieldDTO.getFieldName().substring(0,39),
                constantFieldDTO.getFieldName(),
                //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD),
                null,
                null,
                constantFieldDTO.getFieldSQLCash(),
                constantFieldDTO.getFieldDescription(),
                new Long (i)
                );  
                //Utility.logger.debug ("  constantFieldDTO.getFieldSQLCash()" 
                //    + constantFieldDTO.getFieldSQLCash() );
                //vw_adh_field.setField_sql_cash ( constantFieldDTO.getFieldSQLCash() ) ;
              Utility.logger.debug ( "  added const field id ---> " + lNextVal ) ;
              hMapConstIndexToIDMapping.put( new Long (i), lNextVal);
            }
            //else 
            //{
            //  Utility.logger.debug ( " found. Not added in the DB" ) ;
            //}
          }
        }

        Utility.logger.debug ( "  <--saveQueryDTOConstantsList\n") ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
    }


    //---------------------------------------------------------------
    private void saveQueryDTOParametersList (
          Connection m_conSDSConnection,
          QueryDTO queryDTO ,
          Long    argDataViewID,
          Long    argDataViewIssue,
          Long    argDataViewVersion,
          HashMap hMapParamIndexToIDMapping )
    {
      //Vw_adh_fieldHome vw_adh_fieldHome ;
      ParameterFieldDTO parameterFieldDTO = null ;
      Long lNextVal = null ;
      try
      {
        // ---------------------- saving the list of parameter fields
        Utility.logger.debug ( "\n  -->saveQueryDTOParametersList ") ;

        Vector vecParameters = queryDTO.getParametersFields() ;
        //vw_adh_fieldHome = (Vw_adh_fieldHome)
        //        Utility.getEJBHome("Vw_adh_field",
        //        "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_fieldHome");

        //Utility.logger.debug ( "--finised added displayed fields") ;
        //Utility.logger.debug ( "--started adding list of constants " + vecConstants.size()) ;
        if (vecParameters != null)
        {
          for (int i = 0 ; i < vecParameters.size() ; i++ )
          {
            //System.out.print ( "  checking for constant in index: " + i ) ;
            //constIndex =  ;
            if ( hMapParamIndexToIDMapping.get( new Long (i)) == null )
            {
              parameterFieldDTO = ( (ParameterFieldDTO) vecParameters.elementAt(i) ) ;
              lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
              /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                argDataViewID , // vw_adh_data_view.getData_view_id(),
                argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                lNextVal,
                parameterFieldDTO.getFieldName(),
                //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD),
                null,
                null,
                parameterFieldDTO.getFieldSQLCash(),
                parameterFieldDTO.getFieldDescription(),
                new Long (i)
                );
                */
              QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,
                argDataViewID , // vw_adh_data_view.getData_view_id(),
                argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                lNextVal,
                parameterFieldDTO.getFieldName(),
                //new Long (1), //new Long (dataViewFieldDTO.getFieldDisplayType().getFieldDisplayTypeID()),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD),
                null,
                null,
                parameterFieldDTO.getFieldSQLCash(),
                parameterFieldDTO.getFieldDescription(),
                new Long (i)
                );
                //Utility.logger.debug ("  constantFieldDTO.getFieldSQLCash()" 
                //    + constantFieldDTO.getFieldSQLCash() );
                //vw_adh_field.setField_sql_cash ( constantFieldDTO.getFieldSQLCash() ) ;
              Utility.logger.debug ( "  added param field id ---> " + lNextVal ) ;
              hMapParamIndexToIDMapping.put( new Long (i), lNextVal);
              parameterFieldDTO.setFieldID (lNextVal.intValue());

              //Vw_adh_input_paramHome vw_adh_input_paramHome = (Vw_adh_input_paramHome)
              //    Utility.getEJBHome("Vw_adh_input_param",
              //    "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_input_paramHome");

              // vw_adh_input_param = vw_adh_input_paramHome.create( java.lang.Long input_param_id, java.lang.Long field_id, java.lang.String input_param_label_text, java.lang.Long input_control_types_id, java.lang.String input_control_types_value, java.lang.Long input_param_order );
              lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_INPUT_PARAM_ID") ;
              /*Vw_adh_input_param vw_adh_input_param = vw_adh_input_paramHome.create( 
                lNextVal, //java.lang.Long input_param_id, 
                argDataViewID, //java.lang.Long data_view_id, 
                new Long ( parameterFieldDTO.getFieldID () ), //java.lang.Long field_id, 
                parameterFieldDTO.getLabelText(), //java.lang.String input_param_label_text, 
                new Long (parameterFieldDTO.getControlTypeID() ), //java.lang.Long input_control_types_id, 
                null, //java.lang.String input_control_types_value, 
                new Long (i) //java.lang.Long input_param_order 
                );*/
              QueryBuilderDAO.insertVwAdhInputParam(m_conSDSConnection,  
                lNextVal, //java.lang.Long input_param_id, 
                argDataViewID, //java.lang.Long data_view_id, 
                new Long ( parameterFieldDTO.getFieldID () ), //java.lang.Long field_id, 
                parameterFieldDTO.getLabelText(), //java.lang.String input_param_label_text, 
                new Long (parameterFieldDTO.getControlTypeID() ), //java.lang.Long input_control_types_id, 
                null, //java.lang.String input_control_types_value, 
                new Long (i) //java.lang.Long input_param_order 
                );
            }
            //else 
            //{
            //  Utility.logger.debug ( " found. Not added in the DB" ) ;
            //}
          }
        }

        Utility.logger.debug ( "  <--saveQueryDTOParametersList\n") ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
    }


    //---------------------------------------------------------------
    private void saveQueryDTOFunctionsList( Connection m_conSDSConnection,QueryDTO queryDTO,
          Long    argDataViewID,
          Long    argDataViewIssue,
          Long    argDataViewVersion,
          HashMap hMapConstIndexToIDMapping,
          HashMap hMapParamIndexToIDMapping,
          HashMap hMapFunctionIndexToIDMapping
          )    
    {
      //Vw_adh_fieldHome vw_adh_fieldHome ;
      FunctionFieldDTO functionFieldDTO = null ;
      Long lNextVal = null ;
      try
      {
        Utility.logger.debug ( "\n  -->saveQueryDTOFunctionsList ") ;
        // ---------------------- saving the list of function fields
        Vector vecFunctionFields = queryDTO.getFunctionsFields();

        //vw_adh_fieldHome = (Vw_adh_fieldHome)
        //        Utility.getEJBHome("Vw_adh_field",
        //        "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_fieldHome");

        if (vecFunctionFields != null)
        {
          for (int i = 0 ; i < vecFunctionFields.size() ; i++)
          //for (int i = vecFunctionFields.size()-1 ; i >= 0  ; i--)
          {
            //System.out.print ( "  checking for constant in index: " + i ) ;
            //constIndex =  ;
            if ( hMapFunctionIndexToIDMapping.get( new Long (i)) == null )
            {
              //Utility.logger.debug ( " not found. adding it: " ) ;
              functionFieldDTO = ( (FunctionFieldDTO) vecFunctionFields.elementAt(i) ) ;

                
               Utility.logger.debug ("  long name ---->" + functionFieldDTO.getFieldName());



              lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
              /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                argDataViewID , // vw_adh_data_view.getData_view_id(),
                argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //functionFieldDTO.getFieldName().substring(0,39),
                functionFieldDTO.getFieldName().substring(0,Math.min(functionFieldDTO.getFieldName().length(),39)),
                
                //functionFieldDTO.getFieldName(),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD),
                null,
                new Long (functionFieldDTO.getFunctionDTO().getFunctionID() ),
                functionFieldDTO.getFieldSQLCash(),
                functionFieldDTO.getFieldDescription(),
                new Long (i)
                );
                */
              QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,
                argDataViewID , // vw_adh_data_view.getData_view_id(),
                argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                lNextVal,
                // temporary code - should enlarge the name in the Field table
                //functionFieldDTO.getFieldName().substring(0,39),
                functionFieldDTO.getFieldName().substring(0,Math.min(functionFieldDTO.getFieldName().length(),39)),
                
                //functionFieldDTO.getFieldName(),
                new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                null,
                new Long (QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD),
                null,
                new Long (functionFieldDTO.getFunctionDTO().getFunctionID() ),
                functionFieldDTO.getFieldSQLCash(),
                functionFieldDTO.getFieldDescription(),
                new Long (i)
                );
              //vw_adh_field.setField_sql_cash (functionFieldDTO.getFieldSQLCash() ) ;
              //vw_adh_field.setField_description (functionFieldDTO.getFieldDescription() ) ;
              //vw_adh_field.setField_rf_object( new Long (functionFieldDTO.getFunctionDTO().getFunctionID() ) ) ;

              hMapFunctionIndexToIDMapping.put( new Long (i), lNextVal) ;
              functionFieldDTO.setFieldID( lNextVal.intValue() ); 
              Utility.logger.debug ( "  added function field id ---> " + lNextVal ) ;

              // saving the parameters of the function
              Vector paramValues = functionFieldDTO.getFunctionParameterValues() ;
              FunctionParameterValueModel paramValue = null ;

              //Vw_adh_parameter_valueHome vw_adh_parameter_valueHome = (Vw_adh_parameter_valueHome) 
              //      Utility.getEJBHome("Vw_adh_parameter_value",
              //      "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_parameter_valueHome");
//              Vw_adh_parameter_value vw_adh_parameter_value;
              for (int j=0; j<paramValues.size() ; j++)
              {
                paramValue = ((FunctionParameterValueModel) paramValues.elementAt(j)) ;

                //debug ("saveQueryDTOFunctionsList", "param name: " + paramValue.getParameterValueName() ) ;
                Long lOriginalID = null;
                //String description = null ;
                if (paramValue.getParameterValueFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
                {
                  lOriginalID = (Long) hMapConstIndexToIDMapping.get ( new Long (paramValue.getParameterValueID()) );
                  //description = (String) hMapConstIndexToIDMapping.get ( new Long (paramValue.getParameterValueID()) );
                }
                else if (paramValue.getParameterValueFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
                {
                  lOriginalID = (Long) hMapParamIndexToIDMapping.get ( new Long (paramValue.getParameterValueID()) );
                }
                else if (paramValue.getParameterValueFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
                {
                  lOriginalID = (Long) hMapFunctionIndexToIDMapping.get ( new Long (paramValue.getParameterValueID()) );
                }

                //hagry saving the parameters of the function
                
                lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
                /*
                vw_adh_field = vw_adh_fieldHome.create (
                    argDataViewID , // vw_adh_data_view.getData_view_id(),
                    argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                    argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                    lNextVal,
                    //paramValue.getParameterValueName(),
                    // temporary code - should enlarge the name in the Field table
                    //paramValue.getParameterValueName().substring(0,39),
                    paramValue.getParameterValueName().substring(0,Math.min(paramValue.getParameterValueName().length(),39)),

                    //paramValue.getParameterValueName(),
                   // new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED),
                    new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                    null,
                    new Long (paramValue.getParameterValueFieldType()), //new Long (QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD), //this is wrong type but should verify with fadi
                    null,
                    lOriginalID, //new Long(paramValue.getParameterValueDataViewID()),  //new Long ( paramValue.getParameterDefinitionID() ),//this caused a bug consult with faid
                    paramValue.getParameterValueSQLCache(),
                    null, //"temporary description"
                    null //new Long (paramValue.getParameterValuePlace()) //null
                    //paramValue.getParameterValueSQLCache(),   //????
                    );
                    */
                  QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,  
                    argDataViewID , // vw_adh_data_view.getData_view_id(),
                    argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                    argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                    lNextVal,
                    //paramValue.getParameterValueName(),
                    // temporary code - should enlarge the name in the Field table
                    //paramValue.getParameterValueName().substring(0,39),
                    paramValue.getParameterValueName().substring(0,Math.min(paramValue.getParameterValueName().length(),39)),

                    //paramValue.getParameterValueName(),
                   // new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED),
                    new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED),
                    null,
                    new Long (paramValue.getParameterValueFieldType()), //new Long (QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD), //this is wrong type but should verify with fadi
                    null,
                    lOriginalID, //new Long(paramValue.getParameterValueDataViewID()),  //new Long ( paramValue.getParameterDefinitionID() ),//this caused a bug consult with faid
                    paramValue.getParameterValueSQLCache(),
                    null, //"temporary description"
                    null //new Long (paramValue.getParameterValuePlace()) //null
                    //paramValue.getParameterValueSQLCache(),   //????
                    );
/*
                  Utility.logger.debug("** 20040629 **********************");
                  Utility.logger.debug(paramValue.getParameterValueID());
                  Utility.logger.debug("original is: " + (Long) hMapConstIndexToIDMapping.get ( new Long (paramValue.getParameterValueID()) ) );
                  Utility.logger.debug(paramValue.getParameterDefinitionID());
                  Utility.logger.debug(paramValue.getParameterDefinitionName());
                  Utility.logger.debug(paramValue.getParameterValueDataViewID());
                  Utility.logger.debug(paramValue.getParameterValueDataViewName());
                  Utility.logger.debug(paramValue.getParameterValueDataViewType());
                  Utility.logger.debug(paramValue.getParameterValueFieldType());
                  Utility.logger.debug(paramValue.getParameterValueName());
                  Utility.logger.debug(paramValue.getParameterValuePlace());
                  Utility.logger.debug(paramValue.getParameterValueSQLCache());
                  Utility.logger.debug(paramValue.getParentFunctionFieldID());                                    
                  Utility.logger.debug("**********************************");
*/
                //Utility.logger.debug ( "  constantFieldDTO.getFieldSQLCash()  ---> " + constantFieldDTO.getFieldSQLCash() ) ;
                //vw_adh_field.setField_sql_cash (functionFieldDTO.getFieldSQLCash() ) ;
                //vw_adh_field.setField_description (functionFieldDTO.getFieldDescription() ) ;
                //vw_adh_field.setField_rf_object( new Long ( paramValue.getParameterDefinitionID() ) ) ;
                paramValue.setParameterValueID( lNextVal.intValue() ); 
                Utility.logger.debug ( "  added parameter field id ---> " + lNextVal ) ;


                lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_PARAMETER_VALUE_ID") ;
                /*vw_adh_parameter_value = vw_adh_parameter_valueHome.create( 
                      lNextVal, //parameter_value_id, 
                      new Long (functionFieldDTO.getFieldID() ), // link_field_id, 
                      new Long (paramValue.getParameterValueID() ), //value_field_id, 
                      new Long (j), //parameter_value_place, 
                      new Long (paramValue.getParameterDefinitionID() ) //java.lang.Long parameter_definition_id 
                      );*/
                QueryBuilderDAO.insertVwAdhParameterValue(m_conSDSConnection, 
                      lNextVal, //parameter_value_id, 
                      new Long (functionFieldDTO.getFieldID() ), // link_field_id, 
                      new Long (paramValue.getParameterValueID() ), //value_field_id, 
                      new Long (j), //parameter_value_place, 
                      new Long (paramValue.getParameterDefinitionID() ) //java.lang.Long parameter_definition_id 
                      );
                Utility.logger.debug ( "  added relation between function field id ---> "
                      + functionFieldDTO.getFieldID()
                      + " and parameter field id ---> "
                      + paramValue.getParameterValueID() 
                      ) ;
              }
            //else 
            //{
            //  Utility.logger.debug ( " found. Not added in the DB" ) ;
            //}
            }
          }
        }
        Utility.logger.debug ( "  <--saveQueryDTOFunctionsList\n") ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
    }


    //---------------------------------------------------------------
    private void saveQueryDTOTermsList (Connection m_conSDSConnection, QueryDTO queryDTO,
          Long    argDataViewID,
          Long    argDataViewIssue,
          Long    argDataViewVersion,
          HashMap hMapOldToNewFieldIDMapping,
          HashMap hMapConstIndexToIDMapping,
          HashMap hMapParamIndexToIDMapping,
          HashMap hMapFunctionIndexToIDMapping,
          HashMap hMapTermIndexToIDMapping )
    {
    
      int fieldTypeID ;
      //Vw_adh_fieldHome vw_adh_fieldHome ;
      //FunctionFieldDTO functionFieldDTO = null ;
      Long lNextVal = null ;
      try
      {
        Utility.logger.debug ( "\n  -->saveQueryDTOTermsList ") ;

          Utility.logger.debug ( "  -- Begin Param HashMap ------------------------------------------------------ ") ;
          Object arrKey[] = hMapParamIndexToIDMapping.keySet().toArray() ;
          for (int i = 0 ; i < arrKey.length ; i++ )
          {
            Utility.logger.debug ( "  " + (Long) arrKey[i] + "\n    ----> " + (Long) hMapParamIndexToIDMapping.get ((Long) arrKey[i]) ) ;
          }
          Utility.logger.debug ( "  -- End HashMap ------------------------------------------------------ ") ;

        
        // ---------------------- saving the list of terms
        Vector vecTerms = queryDTO.getTerms() ;

        //vw_adh_fieldHome = (Vw_adh_fieldHome)
        //        Utility.getEJBHome("Vw_adh_field",
        //        "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_fieldHome");

        if (vecTerms != null)
        {
          //Vw_adh_termHome vw_adh_termHome = (Vw_adh_termHome)
          //        Utility.getEJBHome("Vw_adh_term",
          //        "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_termHome");
                  //"com.mobinil.sds.core.system.gn.dataview.dao.cmp.Vw_adh_termHome");
                //PortableRemoteObject.narrow (context.lookup("Vw_adh_term"), Vw_adh_termHome.class) ;
          for (int i = 0 ; i < vecTerms.size() ; i++ )
          {
            Term term = ( (Term) vecTerms.elementAt(i) ) ;
            int termTypeID = term.getTermType().getTermTypeID() ;
            if (termTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE)
            {
              FieldToFieldTermDTO fieldToFieldTermDTO = ( (FieldToFieldTermDTO) vecTerms.elementAt(i) ) ;

              debug ( "saveQueryDTOTermsList", fieldToFieldTermDTO.getTermSQLCache() ) ;
              
              Long firstFieldID = null, secondFieldID = null ; 
              fieldTypeID = fieldToFieldTermDTO.get1stOperandFieldType() ;
              if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
              {
                //Utility.logger.debug ("looking for fieldid" + fieldToFieldTermDTO.get1stOperandFieldID() +
                //  "in the hashmap" ) ;
                firstFieldID = (Long) hMapOldToNewFieldIDMapping.get ( new Long (fieldToFieldTermDTO.get1stOperandFieldID()) ) ;
                // if the field appears for the first time it should be saved in the fields table 
                if ( firstFieldID == null )
                {
                  //Utility.logger.debug ("not found. adding it") ;
                  lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
                  /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                      argDataViewID , // vw_adh_data_view.getData_view_id(),
                      argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                      argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                      lNextVal,
                      // temporary code - should enlarge the name in the Field table
                      //fieldToFieldTermDTO.get1stOperandFieldName().substring(0,39),
                      fieldToFieldTermDTO.get1stOperandFieldName(),
                      new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED), //new Long (1), //
                      null,
                      new Long (fieldTypeID),
                      null,
                      new Long ( fieldToFieldTermDTO.get1stOperandFieldParentDataViewID() ),
                      fieldToFieldTermDTO.get1stOperandFieldSQLCache(),
                      null, //"temporary description"
                      null
                      );
                      */
                  QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,
                      argDataViewID , // vw_adh_data_view.getData_view_id(),
                      argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                      argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                      lNextVal,
                      // temporary code - should enlarge the name in the Field table
                      //fieldToFieldTermDTO.get1stOperandFieldName().substring(0,39),
                      fieldToFieldTermDTO.get1stOperandFieldName(),
                      new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED), //new Long (1), //
                      null,
                      new Long (fieldTypeID),
                      null,
                      new Long ( fieldToFieldTermDTO.get1stOperandFieldParentDataViewID() ),
                      fieldToFieldTermDTO.get1stOperandFieldSQLCache(),
                      null, //"temporary description"
                      null
                      );
                  //vw_adh_field.setField_sql_cash ( fieldToFieldTermDTO.get1stOperandFieldSQLCache() ) ;
                  //vw_adh_field.setField_rf_object( new Long ( fieldToFieldTermDTO.get1stOperandFieldParentDataViewID() ) ) ;
                  //vw_adh_field.setField_description (dataViewFieldDTO.getFieldDescription() ) ;
                  //Utility.logger.debug ( "  old  dataview field id ---> " + fieldToFieldTermDTO.get1stOperandFieldID() ) ;
                  fieldToFieldTermDTO.set1stOperandFieldID(lNextVal.intValue() );
                  firstFieldID = lNextVal;
                  Utility.logger.debug ( "  added dataview field id ---> " + lNextVal ) ;
                  //hMapOldToNewFieldIDMapping.put( new Long (dataViewFieldDTO.getFieldID()), lNextVal);
                }
                //else { Utility.logger.debug ("found. not adding it") ;}

              }
              else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
                firstFieldID = (Long) hMapConstIndexToIDMapping.get ( new Long (fieldToFieldTermDTO.get1stOperandFieldID()) ) ;
              else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
                firstFieldID = (Long) hMapParamIndexToIDMapping.get ( new Long (fieldToFieldTermDTO.get1stOperandFieldID()) ) ;
              else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
                firstFieldID = (Long) hMapFunctionIndexToIDMapping.get ( new Long (fieldToFieldTermDTO.get1stOperandFieldID()) ) ;
                

              fieldTypeID = fieldToFieldTermDTO.get2ndOperandFieldType() ;
              //Utility.logger.debug ( "3.get2ndOperandFieldType -------->"+ fieldTypeID );
              if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
              {
                secondFieldID = (Long) hMapOldToNewFieldIDMapping.get ( new Long (fieldToFieldTermDTO.get2ndOperandFieldID()) ) ;
                // if the field appears for the first time it should be saved in the fields table 
                if ( secondFieldID == null )
                {
                  lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
                  /*
                  Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                      argDataViewID , // vw_adh_data_view.getData_view_id(),
                      argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                      argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                      lNextVal,
                      // temporary code - should enlarge the name in the Field table
                      //fieldToFieldTermDTO.get2ndOperandFieldName().substring(0,39),
                      fieldToFieldTermDTO.get2ndOperandFieldName(),
                      new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED), //new Long (1), //
                      null,
                      new Long (fieldTypeID),
                      null,
                      new Long ( fieldToFieldTermDTO.get2ndOperandFieldParentDataViewID() ),
                      fieldToFieldTermDTO.get2ndOperandFieldSQLCache(),
                      null, //"temporary description"
                      null
                      );
                      */
                   QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,  
                      argDataViewID , // vw_adh_data_view.getData_view_id(),
                      argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                      argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                      lNextVal,
                      // temporary code - should enlarge the name in the Field table
                      //fieldToFieldTermDTO.get2ndOperandFieldName().substring(0,39),
                      fieldToFieldTermDTO.get2ndOperandFieldName(),
                      new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED), //new Long (1), //
                      null,
                      new Long (fieldTypeID),
                      null,
                      new Long ( fieldToFieldTermDTO.get2ndOperandFieldParentDataViewID() ),
                      fieldToFieldTermDTO.get2ndOperandFieldSQLCache(),
                      null, //"temporary description"
                      null
                      );
                  //vw_adh_field.setField_sql_cash ( fieldToFieldTermDTO.get2ndOperandFieldSQLCache() ) ;
                  //vw_adh_field.setField_rf_object( new Long ( fieldToFieldTermDTO.get2ndOperandFieldParentDataViewID() ) ) ;
                  //vw_adh_field.setField_description (dataViewFieldDTO.getFieldDescription() ) ;
                  //Utility.logger.debug ( "  old  dataview field id ---> " + fieldToFieldTermDTO.get1stOperandFieldID() ) ;
                  fieldToFieldTermDTO.set2ndOperandFieldID(lNextVal.intValue() );
                  secondFieldID = lNextVal;
                  Utility.logger.debug ( "  added dataview field id ---> " + lNextVal ) ;
                  //hMapOldToNewFieldIDMapping.put( new Long (dataViewFieldDTO.getFieldID()), lNextVal);
                }
              }
              else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
                secondFieldID = (Long) hMapConstIndexToIDMapping.get ( new Long (fieldToFieldTermDTO.get2ndOperandFieldID()) ) ;
              else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
                secondFieldID = (Long) hMapParamIndexToIDMapping.get ( new Long (fieldToFieldTermDTO.get2ndOperandFieldID()) ) ;
              else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
                secondFieldID = (Long) hMapFunctionIndexToIDMapping.get ( new Long (fieldToFieldTermDTO.get2ndOperandFieldID()) ) ;

              Utility.logger.debug ( "  fieldToFieldTermDTO.get2ndOperandFieldID() " + fieldToFieldTermDTO.get2ndOperandFieldID() ) ;
              Utility.logger.debug ( "  firstFieldID: " + firstFieldID + " secondFieldID: " + secondFieldID ) ;


              lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_TERM_ID") ;
              /*Vw_adh_term vw_adh_term = vw_adh_termHome.create (
                  lNextVal,
                  argDataViewID , // vw_adh_data_view.getData_view_id(),
                  new Long (fieldToFieldTermDTO.getTermType().getTermTypeID()),
                  //new Long (fieldToFieldTermDTO.get1stOperandFieldID() ),
                  //(Long) hMapOldToNewFieldIDMapping.get ( new Long (fieldToFieldTermDTO.get1stOperandFieldID()) ),
                  firstFieldID , 
                  //new Long (fieldToFieldTermDTO.get2ndOperandFieldID() ),
                  //(Long) hMapOldToNewFieldIDMapping.get ( new Long (fieldToFieldTermDTO.get2ndOperandFieldID()) ),
                  secondFieldID ,
                  new Long (fieldToFieldTermDTO.getOperatorID() ),
                  null, 
                  null,
                  fieldToFieldTermDTO.getTermSQLCache()
                  );*/
              QueryBuilderDAO.insertVwAdhTerm(m_conSDSConnection,   
                  lNextVal,
                  argDataViewID , // vw_adh_data_view.getData_view_id(),
                  new Long (fieldToFieldTermDTO.getTermType().getTermTypeID()),
                  //new Long (fieldToFieldTermDTO.get1stOperandFieldID() ),
                  //(Long) hMapOldToNewFieldIDMapping.get ( new Long (fieldToFieldTermDTO.get1stOperandFieldID()) ),
                  firstFieldID , 
                  //new Long (fieldToFieldTermDTO.get2ndOperandFieldID() ),
                  //(Long) hMapOldToNewFieldIDMapping.get ( new Long (fieldToFieldTermDTO.get2ndOperandFieldID()) ),
                  secondFieldID ,
                  new Long (fieldToFieldTermDTO.getOperatorID() ),
                  null, 
                  null,
                  fieldToFieldTermDTO.getTermSQLCache()
                  );
              //vw_adh_term.setTerm_sql( fieldToFieldTermDTO.getTermSQLCache() );
              Utility.logger.debug ( "  added field to field term id ---> " + lNextVal ) ;
              //Utility.logger.debug ( "  ----> mapping term index: " + i + " to new term id " + lNextVal ) ;
              hMapTermIndexToIDMapping.put( new Long (i), lNextVal);
              //hMapConstIndexToIDMapping.put( new Long (i), lNextVal);
            }
            else if (termTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE)
            {
              FieldtoDataViewTermDTO fieldtoDataViewTermDTO = ( (FieldtoDataViewTermDTO) vecTerms.elementAt(i) ) ;
              Long fieldID = null, dataViewID = null ; 
              fieldTypeID = fieldtoDataViewTermDTO.get1stOperandFieldType() ;
              Utility.logger.debug (" fieldTypeID: " + fieldTypeID ) ;
              if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
              {
                //Utility.logger.debug ("looking for fieldid" + fieldToFieldTermDTO.get1stOperandFieldID() +
                //  "in the hashmap" ) ;
                fieldID = (Long) hMapOldToNewFieldIDMapping.get ( new Long (fieldtoDataViewTermDTO.get1stOperandFieldID()) ) ;
                // if the field appears for the first time it should be saved in the fields table 
                if ( fieldID == null )
                {
                  //Utility.logger.debug ("not found. adding it") ;
                  lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_FIELD_ID") ;
                  /*Vw_adh_field vw_adh_field = vw_adh_fieldHome.create (
                      argDataViewID , // vw_adh_data_view.getData_view_id(),
                      argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                      argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                      lNextVal,
                      // temporary code - should enlarge the name in the Field table
                      //fieldtoDataViewTermDTO.get1stOperandFieldName().substring(0,39),
                      fieldtoDataViewTermDTO.get1stOperandFieldName(),
                      new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED), //new Long (1), //
                      null,
                      new Long (QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD),
                      null,
                      new Long ( fieldtoDataViewTermDTO.get1stOperandFieldParentDataViewID() ),
                      fieldtoDataViewTermDTO.get1stOperandFieldSQLCache(),
                      null, //"temporary description"
                      null
                      );
                      */
                  QueryBuilderDAO.insertVwAdhField(m_conSDSConnection,  
                      argDataViewID , // vw_adh_data_view.getData_view_id(),
                      argDataViewIssue , // vw_adh_data_view.getData_view_issue(), //null, //
                      argDataViewVersion , // vw_adh_data_view.getData_view_version(), //null, //
                      lNextVal,
                      // temporary code - should enlarge the name in the Field table
                      //fieldtoDataViewTermDTO.get1stOperandFieldName().substring(0,39),
                      fieldtoDataViewTermDTO.get1stOperandFieldName(),
                      new Long (QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED), //new Long (1), //
                      null,
                      new Long (QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD),
                      null,
                      new Long ( fieldtoDataViewTermDTO.get1stOperandFieldParentDataViewID() ),
                      fieldtoDataViewTermDTO.get1stOperandFieldSQLCache(),
                      null, //"temporary description"
                      null
                      );
                  //vw_adh_field.setField_rf_object( new Long ( fieldtoDataViewTermDTO.get1stOperandFieldParentDataViewID() ) ) ;
                  //vw_adh_field.setField_sql_cash( fieldtoDataViewTermDTO.get1stOperandFieldSQLCache() );
                  //vw_adh_field.setField_description (dataViewFieldDTO.getFieldDescription() ) ;
                  //Utility.logger.debug ( "  old  dataview field id ---> " + fieldToFieldTermDTO.get1stOperandFieldID() ) ;
                  fieldtoDataViewTermDTO.set1stOperandFieldID(lNextVal.intValue() );
                  fieldID = lNextVal;
                  Utility.logger.debug ( "  added dataview field id ---> " + lNextVal ) ;
                  //hMapOldToNewFieldIDMapping.put( new Long (dataViewFieldDTO.getFieldID()), lNextVal);
                }
                //else { Utility.logger.debug ("found. not adding it") ;}
              }
              else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
                fieldID = (Long) hMapConstIndexToIDMapping.get ( new Long (fieldtoDataViewTermDTO.get1stOperandFieldID()) ) ;
              else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD )
                fieldID = (Long) hMapParamIndexToIDMapping.get ( new Long (fieldtoDataViewTermDTO.get1stOperandFieldID()) ) ;
              else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD )
                fieldID = (Long) hMapFunctionIndexToIDMapping.get ( new Long (fieldtoDataViewTermDTO.get1stOperandFieldID()) ) ;

              dataViewID = new Long (fieldtoDataViewTermDTO.getRelatedDataViewID() ) ;
              lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_TERM_ID") ;
              /*Vw_adh_term vw_adh_term = vw_adh_termHome.create (
                  lNextVal,
                  argDataViewID , // vw_adh_data_view.getData_view_id(),
                  new Long (fieldtoDataViewTermDTO.getTermType().getTermTypeID()),
                  fieldID , 
                  dataViewID ,
                  new Long (fieldtoDataViewTermDTO.getOperatorID() ),
                  null, 
                  null,
                  fieldtoDataViewTermDTO.getTermSQLCache()
                  );*/
              QueryBuilderDAO.insertVwAdhTerm(m_conSDSConnection,      
                  lNextVal,
                  argDataViewID , // vw_adh_data_view.getData_view_id(),
                  new Long (fieldtoDataViewTermDTO.getTermType().getTermTypeID()),
                  fieldID , 
                  dataViewID ,
                  new Long (fieldtoDataViewTermDTO.getOperatorID() ),
                  null, 
                  null,
                  fieldtoDataViewTermDTO.getTermSQLCache()
                  );
              //vw_adh_term.setTerm_sql( fieldtoDataViewTermDTO.getTermSQLCache() );
              Utility.logger.debug ( "  added field to dataview term id ---> " + lNextVal ) ;
              //Utility.logger.debug ( "  ----> mapping term index: " + i + " to new term id " + lNextVal ) ;
              hMapTermIndexToIDMapping.put( new Long (i), lNextVal);
            }
          }
        }
        Utility.logger.debug ( "  <--saveQueryDTOTermsList\n") ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
    }



    //---------------------------------------------------------------
    private void saveQueryDTOWhereList ( Connection m_conSDSConnection,QueryDTO queryDTO,
          Long    argDataViewID,
          HashMap hMapTermIndexToIDMapping )
    {
      //Vw_adh_fieldHome vw_adh_fieldHome ;
      FunctionFieldDTO functionFieldDTO = null ;
      Long lNextVal = null ;
      try
      {
        Utility.logger.debug ( "\n  -->saveQueryDTOWhereList ") ;
        // ---------------------- saving the list of wheres
        QueryWhereDTO queryWhereDTO = queryDTO.getQueryWhere() ;
        if (queryWhereDTO != null)
        {
          Vector vecConditionElements = queryDTO.getQueryWhere().getConditionElements();
          if (vecConditionElements != null)
          {
            //Vw_adh_whereHome vw_adh_whereHome = (Vw_adh_whereHome)
            //      Utility.getEJBHome("Vw_adh_where",
            //      "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_whereHome");
                  //"com.mobinil.sds.core.system.gn.dataview.dao.cmp.Vw_adh_whereHome");
                  //PortableRemoteObject.narrow (context.lookup("Vw_adh_where"), Vw_adh_whereHome.class) ;
            for (int i = 0 ; i < vecConditionElements.size() ; i++ )
            {
              ConditionElement conditionElement = ( (ConditionElement) vecConditionElements.elementAt(i) ) ;
              int conditionElementTypeID = conditionElement.getConditionElementType().getConditionElementTypeID() ;

              if (conditionElementTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM)
              {
                Term term = ( (Term) vecConditionElements.elementAt(i) ) ;
                int termTypeID = term.getTermType().getTermTypeID() ;
                if (termTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE)
                {
                  FieldToFieldTermDTO fieldToFieldTerm = ( (FieldToFieldTermDTO) vecConditionElements.elementAt(i) ) ;
                  lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_WHERE_ID") ;
                  //Utility.logger.debug ("2. fieldToFieldTerm.getTermID()" + fieldToFieldTerm.getTermID()) ;
                  /*Vw_adh_where vw_adh_where = vw_adh_whereHome.create (
                      lNextVal,
                      argDataViewID, //vw_adh_data_view.getData_view_id(),
                      new Long (fieldToFieldTerm.getConditionElementType().getConditionElementTypeID() ),
                      null,
                      //new Long (fieldToFieldTerm.getTermID()),
                      (Long) hMapTermIndexToIDMapping.get ( new Long (fieldToFieldTerm.getTermID() ) ) ,
                      new Long ( i+1 ) // new Long ( 1 ) // Long where_place
                      );*/
                  QueryBuilderDAO.insertVwAdhWhere(m_conSDSConnection,    
                      lNextVal,
                      argDataViewID, //vw_adh_data_view.getData_view_id(),
                      new Long (fieldToFieldTerm.getConditionElementType().getConditionElementTypeID() ),
                      null,
                      //new Long (fieldToFieldTerm.getTermID()),
                      (Long) hMapTermIndexToIDMapping.get ( new Long (fieldToFieldTerm.getTermID() ) ) ,
                      new Long ( i+1 ) // new Long ( 1 ) // Long where_place
                      );
                  Utility.logger.debug ( "  added where term (field to field) id ---> " + lNextVal ) ;
                }
                else if (termTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE)
                {
                  FieldtoDataViewTermDTO fieldtoDataViewTerm = ( (FieldtoDataViewTermDTO) vecConditionElements.elementAt(i) ) ;
                  lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_WHERE_ID") ;
                  //Utility.logger.debug ("2. fieldtoDataViewTerm.getTermID()" + fieldtoDataViewTerm.getTermID()) ;
                  /*Vw_adh_where vw_adh_where = vw_adh_whereHome.create (
                      lNextVal,
                      argDataViewID, //vw_adh_data_view.getData_view_id(),
                      new Long (fieldtoDataViewTerm.getConditionElementType().getConditionElementTypeID() ),
                      null,
                      //new Long (fieldToFieldTerm.getTermID()),
                      (Long) hMapTermIndexToIDMapping.get ( new Long (fieldtoDataViewTerm.getTermID() ) ) ,
                      new Long ( i+1 ) // new Long ( 1 ) // Long where_place
                      );*/
                  QueryBuilderDAO.insertVwAdhWhere(m_conSDSConnection, 
                      lNextVal,
                      argDataViewID, //vw_adh_data_view.getData_view_id(),
                      new Long (fieldtoDataViewTerm.getConditionElementType().getConditionElementTypeID() ),
                      null,
                      //new Long (fieldToFieldTerm.getTermID()),
                      (Long) hMapTermIndexToIDMapping.get ( new Long (fieldtoDataViewTerm.getTermID() ) ) ,
                      new Long ( i+1 ) // new Long ( 1 ) // Long where_place
                      );
                  Utility.logger.debug ( "  added where term (field to dataview) id ---> " + lNextVal ) ;
                }
              }
              else if (conditionElementTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL)
              {
                RelationSymbolDTO symbol = ( (RelationSymbolDTO) vecConditionElements.elementAt(i) ) ;
                lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_WHERE_ID") ;
                /*Vw_adh_where vw_adh_where = vw_adh_whereHome.create (
                    lNextVal,
                    argDataViewID, //vw_adh_data_view.getData_view_id(),
                    new Long (symbol.getConditionElementType().getConditionElementTypeID()),
                    null,
                    new Long (symbol.getRelationSymbolID() ),
                      new Long ( i+1 ) // new Long ( 1 ) // Long where_place
                    );*/
                QueryBuilderDAO.insertVwAdhWhere(m_conSDSConnection,   
                    lNextVal,
                    argDataViewID, //vw_adh_data_view.getData_view_id(),
                    new Long (symbol.getConditionElementType().getConditionElementTypeID()),
                    null,
                    new Long (symbol.getRelationSymbolID() ),
                      new Long ( i+1 ) // new Long ( 1 ) // Long where_place
                    );
                //Utility.logger.debug ( "  added where term (symbol) id ---> " + lNextVal ) ;
            
                 // RelationSymbolDTO symbol = new RelationSymbolDTO () ; 
                 // symbol.setRelationSymbolSQL((String) paramHashMap.get ( symbolSQL ));
                 // ConditionElementTypeDTO conditionElementTypeDTO = new ConditionElementTypeDTO () ;
                 // conditionElementTypeDTO.setConditionElementTypeID(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL);
                 // symbol.setConditionElementType(conditionElementTypeDTO);
                 // symbol.setRelationSymbolID(new Integer ( (String) paramHashMap.get ( elementID ) ).intValue() );
                 // symbol.setRelationSymbolSQL((String) paramHashMap.get ( symbolSQL ) );

              }
            }
          }
        }
        
        Utility.logger.debug ( "  <--saveQueryDTOWhereList\n") ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
    }



    //---------------------------------------------------------------
    private void saveQueryDTOHavingList (Connection m_conSDSConnection, QueryDTO queryDTO,
          Long    argDataViewID,
          HashMap hMapTermIndexToIDMapping )
    {
      //Vw_adh_fieldHome vw_adh_fieldHome ;
      FunctionFieldDTO functionFieldDTO = null ;
      Long lNextVal = null ;
      try
      {
        Utility.logger.debug ( "\n  -->saveQueryDTOHavingList ") ;
        // ---------------------- saving the list of Having
        QueryHavingDTO queryHavingDTO = queryDTO.getQueryHaving() ;
        if (queryHavingDTO != null)
        {
          Vector vecConditionElements = queryDTO.getQueryHaving().getConditionElements();
          if (vecConditionElements != null)
          {
            //Vw_adh_havingHome vw_adh_havingHome = (Vw_adh_havingHome)
            //PortableRemoteObject.narrow(context.lookup("Vw_adh_having"), Vw_adh_havingHome.class);
            //Vw_adh_having vw_adh_having;

            //Vw_adh_havingHome vw_adh_havingHome = (Vw_adh_havingHome)
            //      Utility.getEJBHome("Vw_adh_having",
            //      "com.mobinil.sds.core.system.gn.querybuilder.dao.cmp.Vw_adh_havingHome");

            for (int i = 0 ; i < vecConditionElements.size() ; i++ )
            {
              ConditionElement conditionElement = ( (ConditionElement) vecConditionElements.elementAt(i) ) ;
              int conditionElementTypeID = conditionElement.getConditionElementType().getConditionElementTypeID() ;

              if (conditionElementTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM)
              {
                Term term = ( (Term) vecConditionElements.elementAt(i) ) ;
                int termTypeID = term.getTermType().getTermTypeID() ;
                if (termTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE)
                {
                  FieldToFieldTermDTO fieldToFieldTerm = ( (FieldToFieldTermDTO) vecConditionElements.elementAt(i) ) ;
                  lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_HAVING_ID") ;
                  //Utility.logger.debug ("2. fieldToFieldTerm.getTermID()" + fieldToFieldTerm.getTermID()) ;
                  /*Vw_adh_having vw_adh_having = vw_adh_havingHome.create (
                      lNextVal,
                      argDataViewID, //vw_adh_data_view.getData_view_id(),
                      new Long (fieldToFieldTerm.getConditionElementType().getConditionElementTypeID() ),
                      null,
                      //new Long (fieldToFieldTerm.getTermID()),
                      (Long) hMapTermIndexToIDMapping.get ( new Long (fieldToFieldTerm.getTermID() ) ) ,
                      new Long ( i+1 ) // new Long ( 1 ) // Long Having_place
                      );*/
                  QueryBuilderDAO.insertVwAdhHaving(m_conSDSConnection,
                      lNextVal,
                      argDataViewID, //vw_adh_data_view.getData_view_id(),
                      new Long (fieldToFieldTerm.getConditionElementType().getConditionElementTypeID() ),
                      null,
                      //new Long (fieldToFieldTerm.getTermID()),
                      (Long) hMapTermIndexToIDMapping.get ( new Long (fieldToFieldTerm.getTermID() ) ) ,
                      new Long ( i+1 ) // new Long ( 1 ) // Long Having_place
                      );
                  Utility.logger.debug ( "  added Having term (field to field) id ---> " + lNextVal ) ;
                }
                else if (termTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE)
                {
                  FieldtoDataViewTermDTO fieldtoDataViewTerm = ( (FieldtoDataViewTermDTO) vecConditionElements.elementAt(i) ) ;
                  lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_HAVING_ID") ;
                  //Utility.logger.debug ("2. fieldtoDataViewTerm.getTermID()" + fieldtoDataViewTerm.getTermID()) ;
                  /*Vw_adh_having vw_adh_having = vw_adh_havingHome.create (
                      lNextVal,
                      argDataViewID, //vw_adh_data_view.getData_view_id(),
                      new Long (fieldtoDataViewTerm.getConditionElementType().getConditionElementTypeID() ),
                      null,
                      //new Long (fieldToFieldTerm.getTermID()),
                      (Long) hMapTermIndexToIDMapping.get ( new Long (fieldtoDataViewTerm.getTermID() ) ) ,
                      new Long ( i+1 ) // new Long ( 1 ) // Long Having_place
                      );*/
                  QueryBuilderDAO.insertVwAdhHaving(m_conSDSConnection,    
                      lNextVal,
                      argDataViewID, //vw_adh_data_view.getData_view_id(),
                      new Long (fieldtoDataViewTerm.getConditionElementType().getConditionElementTypeID() ),
                      null,
                      //new Long (fieldToFieldTerm.getTermID()),
                      (Long) hMapTermIndexToIDMapping.get ( new Long (fieldtoDataViewTerm.getTermID() ) ) ,
                      new Long ( i+1 ) // new Long ( 1 ) // Long Having_place
                      );
                  Utility.logger.debug ( "  added Having term (field to dataview) id ---> " + lNextVal ) ;
                }
              }
              else if (conditionElementTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL)
              {
                RelationSymbolDTO symbol = ( (RelationSymbolDTO) vecConditionElements.elementAt(i) ) ;
                lNextVal = Utility.getSequenceNextVal (m_conSDSConnection,"SEQ_ADH_HAVING_ID") ;
                /*Vw_adh_having vw_adh_having = vw_adh_havingHome.create (
                    lNextVal,
                    argDataViewID, //vw_adh_data_view.getData_view_id(),
                    new Long (symbol.getConditionElementType().getConditionElementTypeID()),
                    null,
                    new Long (symbol.getRelationSymbolID() ),
                      new Long ( i+1 ) // new Long ( 1 ) // Long Having_place
                    );*/
                QueryBuilderDAO.insertVwAdhHaving(m_conSDSConnection,        
                    lNextVal,
                    argDataViewID, //vw_adh_data_view.getData_view_id(),
                    new Long (symbol.getConditionElementType().getConditionElementTypeID()),
                    null,
                    new Long (symbol.getRelationSymbolID() ),
                      new Long ( i+1 ) // new Long ( 1 ) // Long Having_place
                    );
              }
            }
          }
        }
        Utility.logger.debug ( "  <--saveQueryDTOHavingList\n") ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
    }


    //---------------------------------------------------------------
    private void saveQueryDTO ( Connection m_conSDSConnection,QueryDTO queryDTO )
    {
      //Context context = null ; 
      Long lNextVal = new Long (0) ;
      HashMap hMapOldToNewFieldIDMapping ;
      HashMap hMapConstIndexToIDMapping ;
      HashMap hMapFunctionIndexToIDMapping ;
      HashMap hMapParamIndexToIDMapping ;
      HashMap hMapTermIndexToIDMapping ;
      //DataViewFieldDTO dataViewFieldDTO ;
      //ConstantFieldDTO constantFieldDTO ;
      //FunctionFieldDTO functionFieldDTO ;
      //int fieldTypeID ;


      hMapOldToNewFieldIDMapping    = new HashMap ();
      hMapConstIndexToIDMapping     = new HashMap ();
      hMapFunctionIndexToIDMapping  = new HashMap ();
      hMapTermIndexToIDMapping      = new HashMap ();
      hMapParamIndexToIDMapping     = new HashMap ();

      //QueryViewerDTO queryViewerDTO = new QueryViewerDTO () ;
      //String strSQL = null ;
      /*
      PreparedStatement stmtQuery = null ;
      ResultSet rstQuery = null ;
      int nColNum = 0 ;
      String strColName ;*/ 
      try
      {

        //Vw_adh_data_view vw_adh_data_view = saveQueryDTONewDataView ( m_conSDSConnection,queryDTO ) ;
        DataViewModel dataViewModelX = saveQueryDTONewDataView ( m_conSDSConnection,queryDTO ) ;

        Long longDataViewId = new Long(dataViewModelX.getDataViewID());
        Long longDataViewIssue = new Long(dataViewModelX.getDataViewIssue());
        Long longDataViewVersion = new Long(dataViewModelX.getDataViewVersion());
        saveQueryDTOFromList ( m_conSDSConnection,queryDTO, longDataViewId ) ;

        saveQueryDTODisplayedFields ( m_conSDSConnection,queryDTO, 
                longDataViewId,
                longDataViewIssue,
                longDataViewVersion,
                hMapOldToNewFieldIDMapping,
                hMapConstIndexToIDMapping,
                hMapParamIndexToIDMapping,
                hMapFunctionIndexToIDMapping ) ;


        saveQueryDTOGroupByFields ( m_conSDSConnection,queryDTO, 
                longDataViewId,
                longDataViewIssue,
                longDataViewVersion,
                hMapOldToNewFieldIDMapping,
                hMapConstIndexToIDMapping,
                hMapParamIndexToIDMapping,
                hMapFunctionIndexToIDMapping ) ;


        saveQueryDTOOrderByFields ( m_conSDSConnection,queryDTO, 
                longDataViewId,
                longDataViewIssue,
                longDataViewVersion,
                hMapOldToNewFieldIDMapping,
                hMapConstIndexToIDMapping,
                hMapParamIndexToIDMapping,
                hMapFunctionIndexToIDMapping ) ;


        saveQueryDTOConstantsList ( m_conSDSConnection,queryDTO, 
                longDataViewId,
                longDataViewIssue,
                longDataViewVersion,
                hMapConstIndexToIDMapping ) ;

        saveQueryDTOParametersList ( m_conSDSConnection,queryDTO, 
                longDataViewId,
                longDataViewIssue,
                longDataViewVersion,
                hMapParamIndexToIDMapping ) ;

        saveQueryDTOFunctionsList( m_conSDSConnection,queryDTO, 
                longDataViewId,
                longDataViewIssue,
                longDataViewVersion,
                hMapConstIndexToIDMapping,
                hMapParamIndexToIDMapping,
                hMapFunctionIndexToIDMapping ) ;

        
        saveQueryDTOTermsList( m_conSDSConnection,queryDTO, 
                longDataViewId,
                longDataViewIssue,
                longDataViewVersion,
                hMapOldToNewFieldIDMapping,
                hMapConstIndexToIDMapping,
                hMapParamIndexToIDMapping,
                hMapFunctionIndexToIDMapping,
                hMapTermIndexToIDMapping ) ;


        saveQueryDTOWhereList ( m_conSDSConnection,queryDTO,
                longDataViewId,
                hMapTermIndexToIDMapping );

        saveQueryDTOHavingList ( m_conSDSConnection,queryDTO,
                longDataViewId,
                hMapTermIndexToIDMapping );


      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
    }

    //---------------------------------------------------------------
    public void saveQuery ( Connection m_conSDSConnection,HashMap paramHashMap )
    {
      QueryDTO queryDTO = null ;
      /*QueryViewerDTO queryViewerDTO = new QueryViewerDTO () ;
    */ 
      try
      {
        queryDTO = constructQueryDTO (m_conSDSConnection,paramHashMap) ;
       saveQueryDTO (m_conSDSConnection,queryDTO) ;
       Utility.logger.debug ("Query Saved") ;
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
        Utility.logger.debug(ex.toString(),ex);
      }
    }

    //---------------------------------------------------------------
    //---------------------- fourth scenario -------------------------
    //---------------------------------------------------------------
    public QueryViewerDTO previewQuery ( boolean previewColumnOnly ,Connection m_conSDSConnection,HashMap paramHashMap/*, String strErrorMessage */)
    {
      QueryDTO queryDTO = null ;
      QueryViewerDTO queryViewerDTO = new QueryViewerDTO () ;
      String strSQL = null ;
      PreparedStatement stmtQuery = null ;
      ResultSet rstQuery = null ;
      int nColNum = 0 ;
      String strColName ; 
      Vector vecHeaderFields = null ;
      String strErrorMessage = null ;

      try
      {
        queryDTO = constructQueryDTO (m_conSDSConnection,paramHashMap) ;
        strSQL = constructQuerySQL (queryDTO) ;        
        //System.out.println("strSQL="+ strSQL);
        //System.out.println("user id= ="+ paramHashMap.get("USER_ID"));
        //Utility.logger.debug ("generated SQL: \n" + strSQL ) ;
/*        
        strErrorMessage = 
            QueryBuilderDAO.verifyQuery (m_conSDSConnection, strSQL) ;
*/
        //Utility.logger.debug ("verifyQuery : " + strErrorMessage ) ;
        
        //this change make it work only if their is a where statement 
        //sql without where statement will make this not work
        if (previewColumnOnly)
        {
        strSQL = strSQL.replaceAll("WHERE", "WHERE ROWNUM<=0 AND");
        	
        }
      //  System.out.println("***> "+ strSQL);
        queryViewerDTO.setSQLCode (strSQL);
        
        vecHeaderFields = new Vector () ;
       // if (strErrorMessage == null )
        
          //Utility.logger.debug ("  strErrorMessage == null " ) ;
          try{
          stmtQuery = m_conSDSConnection.prepareStatement (strSQL) ;                             
          rstQuery = stmtQuery.executeQuery();          
          Vector vecRows = new Vector () ;

          nColNum = rstQuery.getMetaData().getColumnCount() ;
          for ( int i=0 ; i<nColNum; i++ )
          {
            strColName = rstQuery.getMetaData().getColumnName(i+1) ;
            vecHeaderFields.addElement ( strColName );
          }

          int rowCount = 0 ;
          while (rstQuery.next()&& rowCount< QueryBuilderInterfaceKey.PREVIEW_REPORT_ROW_MAX)
          {
            DataRowDTO dataRowDTO = new DataRowDTO() ;
            Vector vecValues = new Vector () ;

            for ( int j=0 ; j<nColNum; j++ )
            {              
              vecValues.addElement ( rstQuery.getString ((String) vecHeaderFields.elementAt(j)) ) ;                           
            }
            dataRowDTO.setValues (vecValues);
            vecRows.addElement (dataRowDTO) ;
            rowCount++ ;
          }

          queryViewerDTO.setRows (vecRows);
          queryViewerDTO.setHeaderInterfaceFields (vecHeaderFields);
          }
          catch(Exception e)
          {
        	  queryViewerDTO.setErrorMessage ( e.getMessage() );
        	  e.printStackTrace();
          }
          stmtQuery.close();
          /*
          int j = 1 ;
          while (rstQuery.next())
          {
            nColNum = rstQuery.getMetaData().getColumnCount() ;
          
            System.out.print ("  row : " + j + " ----> " ) ;
          
            for ( int i=0 ; i<nColNum; i++ )
            {
              strColName = rstQuery.getMetaData().getColumnName(i+1) ;
              System.out.print (" col: " + strColName ) ;
              System.out.print (" value: " + rstQuery.getString (strColName) ) ; 
            }
          
            Utility.logger.debug ("") ;
            j++ ;
          }
          */
        
       // else
       // {
        //Utility.logger.debug ("  else     strErrorMessage == null " ) ;
         // queryViewerDTO.setErrorMessage ( strErrorMessage );
        //}
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
      return queryViewerDTO ;
    }
    
    //---------------------------------------------------------------
    //---------------------- fourth scenario -------------------------
    //---------------------------------------------------------------

    public QueryDTO loadQueryDTO (Connection m_conSDSConnection, int argDataViewID ,Vector vecReportParameterList)
    {
       //QueryBuilderWizardDTO queryBuilderWizardDTO = null ;
       QueryDTO queryDTO = null ;
       DataViewModel dataViewModel = null ;
      try
      {
        queryDTO = new QueryDTO () ;
        dataViewModel = DataViewDAO.getDataViewDetails_Model (m_conSDSConnection, argDataViewID) ;
        if (dataViewModel == null)
        {
          queryDTO.setErrorMessage("Invalid DataView ID");
        }
        else
        {
        debug("argDataViewID " + argDataViewID);
          debug("query version  = " + queryDTO.getQueryVersion());
          
          queryDTO.setQueryName(dataViewModel.getDataViewName());
          queryDTO.setQueryIssue(dataViewModel.getDataViewIssue());
          queryDTO.setQueryVersion(dataViewModel.getDataViewVersion());
          queryDTO.setQueryDescription(dataViewModel.getDataViewDescription());
          queryDTO.setQueryUnique( dataViewModel.getDataViewUnique() );
          queryDTO.setQueryUniverse( dataViewModel.getDataViewUniverse() );
          queryDTO.setOverRideSQL(dataViewModel.getDataViewOverrideSQL());
          
          queryDTO.setQueryUniverseName( 
              DataViewDAO.switchUniverseTypeToString (m_conSDSConnection, queryDTO.getQueryUniverse() ) );
        }


        // --------- loading  fields for the dataview -------------------------------------

        //queryDTO = DataViewDAO.getDataViewDetailedDisplayedFieldsList (

        queryDTO = DataViewDAO.getDataViewFieldsList (
                m_conSDSConnection, argDataViewID, 
                queryDTO,
                //,                detailedDataViewDTO ,
                //dataViewModel,
                //hMapDetailedDataViews
                vecReportParameterList
                ) ;
        

        // --------- loading from list -------------------------------------
        Vector vecFromList = DataViewDAO.getDataViewFromList (m_conSDSConnection, argDataViewID) ;
        //Utility.logger.debug("vecFromList = " + vecFromList.size());
        HashMap hMapDetailedDataViews = new HashMap() ;
        DetailedDataViewDTO detailedDataViewDTO  ;
        for (int i = 0 ; i < vecFromList.size() ; i++)
        {
          FromModel fromModel = ( (FromModel) vecFromList.elementAt(i) ) ;
          detailedDataViewDTO = new DetailedDataViewDTO () ;
          detailedDataViewDTO.setDataViewID(fromModel.getFRDataViewID());
          detailedDataViewDTO.setDataViewName(fromModel.getFRDataViewName());
          detailedDataViewDTO.setDataViewTypeID(fromModel.getFRDataViewTypeID());
          detailedDataViewDTO.setDataViewVersion(fromModel.getFRDataViewVersion());
          detailedDataViewDTO.setDataViewDescription(fromModel.getFRDataViewDescription());
          if ( detailedDataViewDTO.getDataViewTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY )
          {
            Vector vec =new Vector();
            QueryDTO subQueryDTO = loadQueryDTO ( m_conSDSConnection,detailedDataViewDTO.getDataViewID() ,vec) ;
            String subQuerySQL = constructQuerySQL (subQueryDTO) ;
            detailedDataViewDTO.setDataViewOverrideSQL (subQuerySQL) ;
          }
          //detailedDataViewDTO.setFromID(fromModel.getFromID());


          // --------- loading fields for each table in the from clause list -------------------------------------
          //queryDTO = DataViewDAO.getDataViewDetailedFieldsList (
          queryDTO = DataViewDAO.getDataViewFromFieldsList (
                  m_conSDSConnection, //argDataViewID, 
                  queryDTO ,
                  detailedDataViewDTO ,
                  //dataViewModel,
                  hMapDetailedDataViews
                  ) ;
        }

        // --------- loading terms list -------------------------------------
//        Utility.logger.debug ( "before getTermsList of dataviewid: " + argDataViewID) ;
        Vector vecTerms = DataViewDAO.getTermsList(m_conSDSConnection, argDataViewID) ;
//        Utility.logger.debug ( "after  getTermsList of dataviewid: " + argDataViewID) ;
        queryDTO.setTerms (vecTerms);
        
        // --------- loading where list -------------------------------------
        QueryWhereDTO queryWhereDTO = new QueryWhereDTO () ;
//        Utility.logger.debug ( "before getWheresList of dataviewid: " + argDataViewID) ;
        Vector vecWheres = DataViewDAO.getWheresList(m_conSDSConnection, argDataViewID, vecTerms) ;
//        Utility.logger.debug ( "after  getWheresList of dataviewid: " + argDataViewID) ;
        queryWhereDTO.setConditionElements(vecWheres);
        queryDTO.setQueryWhere(queryWhereDTO);


        // --------- loading group by list -------------------------------------
        Vector vecGroupByList = DataViewDAO.getGroupByList(
                  m_conSDSConnection, argDataViewID, queryDTO.getFunctionsFields()) ;
        queryDTO.setGroupBy(vecGroupByList);

        // --------- loading group by list -------------------------------------
        Vector vecOrderByList = DataViewDAO.getOrderByList(
                  m_conSDSConnection, argDataViewID, queryDTO.getFunctionsFields()) ;
        queryDTO.setOrderBy(vecOrderByList);

        // --------- loading having list -------------------------------------
        QueryHavingDTO queryHavingDTO = new QueryHavingDTO () ;
        Vector vecHavings = DataViewDAO.getHavingList(m_conSDSConnection, argDataViewID, vecTerms) ;
        queryHavingDTO.setConditionElements(vecHavings);
        queryDTO.setQueryHaving(queryHavingDTO);


        // temprorary statments to check the generated query
/*
 * hagry i commented the next two lines since i do think it will enhance performance
 */
        /*
          String strSQL = constructQuerySQL(queryDTO);
          Utility.logger.debug ( "loadQueryDTO:generated SQL statement: \n" + strSQL) ;
        */
        
        //queryBuilderWizardDTO.setQuery (queryDTO);
      }
      catch(Throwable ex)
      {
        ex.printStackTrace();
      }
      return queryDTO ;
    }

   //---------------------------------------------------------------
    /*public void finalize()
    {
       try
      {
        Utility.closeConnection( m_conSDSConnection) ;
      }
      catch(Exception objExp)                                                                             
      {                                     
        objExp.printStackTrace();
      }
    }*/
    
   //---------------------------------------------------------------
    public Vector listDataViews ( Connection m_conSDSConnection,String strUniverseType )
    {
      Vector vecDataViewsList = null ;
      try
      {
        //String strDataViewUniverseID = (String) paramHashMap.get(QueryBuilderInterfaceKey.PARAM_INITIALIZE_WIZARD_UNIVERSE)  ;

        int nDataViewUniverseID =
           (DataViewDAO.switchUniverseTypeToID ( m_conSDSConnection, strUniverseType)).intValue() ; 

        vecDataViewsList = 
        DataViewDAO.getDataViewsList ( m_conSDSConnection, 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY , 
            QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE ,
            nDataViewUniverseID ) ;
      }
      catch(Exception objExp)                                                                             
      {                                     
        objExp.printStackTrace();
      }
      return vecDataViewsList;
    }


   //---------------------------------------------------------------
    /*public Context setInitialContext ()
    {
      Context context = null ;
      try
      {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.evermind.server.rmi.RMIInitialContextFactory");
        env.put(Context.SECURITY_PRINCIPAL, "admin");
        env.put(Context.SECURITY_CREDENTIALS, "welcome");
        env.put(Context.PROVIDER_URL, "ormi://localhost:23891/current-workspace-app");
        context = new InitialContext(env) ;
      }
      catch(Exception objExp)                                                                             
      {                                     
        objExp.printStackTrace();
      }
      return context;
    }*/

}
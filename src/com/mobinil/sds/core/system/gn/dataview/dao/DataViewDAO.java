package com.mobinil.sds.core.system.gn.dataview.dao;

import java.util.Vector;
import java.util.HashMap;
import java.sql.*;

import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.core.system.gn.querybuilder.dao.*;
import com.mobinil.sds.core.system.gn.querybuilder.model.*;
import com.mobinil.sds.core.system.gn.querybuilder.dto.*;
import com.mobinil.sds.core.system.gn.querybuilder.domain.*;
import com.mobinil.sds.core.system.gn.dataview.dao.* ;
import com.mobinil.sds.core.system.gn.dataview.dto.*;
import com.mobinil.sds.core.system.gn.dataview.model.*;
import com.mobinil.sds.web.interfaces.gn.querybuilder.QueryBuilderInterfaceKey;

import com.mobinil.sds.core.system.gn.reports.dto.*;

public class DataViewDAO implements java.io.Serializable
{
    public static  boolean   DEBUG_FLAG    = false ;

   //---------------------------------------------------------------
   
    public static void debug (  String msg )
    {
      if (DEBUG_FLAG )
      {
        Utility.logger.debug(msg);
      }
    }
    
    public static void debug ( String place, String msg )
    {
      if (DEBUG_FLAG == true)
      {
        System.out.print ( "  " ) ;
        if (place != null) System.out.print( place + ": " ) ;
        System.out.print ( msg ) ;
        Utility.logger.debug ("" ) ;
      }
    }

    // ----------------------------------------------------------------------
    /* this method is used in scenario 1 (initializeWizard) */
    public static Vector getDataViewsList (Connection argCon,
        int argDataViewTypeID, 
        int argDataViewStatusID,
        int argDataViewUniverseID
        //String argDataViewUniverseID
        )
    {
      Vector vecDataViewsList = new Vector() ;
      PreparedStatement stmtDataViewsList = null;
      try
      {
        String strDataViewsListQuery = 
            " select * " + 
            " from VW_ADH_DATA_VIEW, VW_ADH_UNIVERSE_DEFINITION, VW_ADH_UNIVERSE " +
            " where DATA_VIEW_TYPE_ID = ? " +
            "  and DATA_VIEW_STATUS_ID = ? " +
            //"  and VW_ADH_UNIVERSE.UNIVERSE_NAME = ? " + 
            "  and VW_ADH_UNIVERSE_DEFINITION.UNIVERSE_ID = ? " + 
            "  and VW_ADH_DATA_VIEW.DATA_VIEW_ID = VW_ADH_UNIVERSE_DEFINITION.DATA_VIEW_ID " +
            "  and VW_ADH_UNIVERSE.UNIVERSE_ID = VW_ADH_UNIVERSE_DEFINITION.UNIVERSE_ID " +
            //" order by DATA_VIEW_ISSUE, DATA_VIEW_VERSION " ;
            " order by UPPER(VW_ADH_DATA_VIEW.DATA_VIEW_NAME) " ;
        stmtDataViewsList = argCon.prepareStatement (strDataViewsListQuery) ;
        stmtDataViewsList.setInt (1, argDataViewTypeID);
        stmtDataViewsList.setInt (2, argDataViewStatusID);
        stmtDataViewsList.setInt (3, argDataViewUniverseID);
        //stmtDataViewsList.setString (3, argDataViewUniverseID);
        ResultSet rstDataViewsList = stmtDataViewsList.executeQuery();

        while(rstDataViewsList.next())
        {
           BriefDataViewDTO newBriefDataViewDTO = new BriefDataViewDTO ();
           newBriefDataViewDTO.setDataViewID (rstDataViewsList.getInt("DATA_VIEW_ID"));
           newBriefDataViewDTO.setDataViewIssue (rstDataViewsList.getInt("DATA_VIEW_ISSUE"));
           newBriefDataViewDTO.setDataViewVersion (rstDataViewsList.getInt("DATA_VIEW_VERSION"));
           newBriefDataViewDTO.setDataViewName (rstDataViewsList.getString("DATA_VIEW_NAME"));
           newBriefDataViewDTO.setDataViewTypeID (rstDataViewsList.getInt("DATA_VIEW_TYPE_ID"));
           newBriefDataViewDTO.setDataViewTypeName (rstDataViewsList.getString("DATA_VIEW_TYPE_NAME"));
           newBriefDataViewDTO.setDataViewDescription( rstDataViewsList.getString("DATA_VIEW_DESCRIPTION"));
           newBriefDataViewDTO.setOverRideSQL( rstDataViewsList.getString("DATA_VIEW_OVERRIDE_SQL"));
           

          if ( newBriefDataViewDTO.getOverRideSQL() ==null)
          {
            vecDataViewsList.addElement (newBriefDataViewDTO);
          }
          else          
          {
       //     Utility.logger.debug("init wizard didnt' add view "+ newBriefDataViewDTO.getDataViewName());
          }
          
        }
        rstDataViewsList.close();
        stmtDataViewsList.close();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecDataViewsList ;
    }


    // ----------------------------------------------------------------------
    /* this method is used in scenario 1 (initializeWizard) */
    public static Vector getDataViewsListAllUniverses (Connection argCon,
        int argDataViewTypeID, 
        int argDataViewStatusID
        )
    {
      Vector vecDataViewsList = new Vector() ;
      PreparedStatement stmtDataViewsList = null;
      try
      {
        String strDataViewsListQuery = 
            " select * " +
            " from VW_ADH_DATA_VIEW " +
            " where DATA_VIEW_TYPE_ID = ? and " +
            "       DATA_VIEW_STATUS_ID = ? " +
            " order by upper(VW_ADH_DATA_VIEW.DATA_VIEW_NAME) " ;
        stmtDataViewsList = argCon.prepareStatement (strDataViewsListQuery) ;
        stmtDataViewsList.setInt (1, argDataViewTypeID);
        stmtDataViewsList.setInt (2, argDataViewStatusID);
        ResultSet rstDataViewsList = stmtDataViewsList.executeQuery();

        while(rstDataViewsList.next())
        {
           BriefDataViewDTO newBriefDataViewDTO = new BriefDataViewDTO ();
           newBriefDataViewDTO.setDataViewID (rstDataViewsList.getInt("DATA_VIEW_ID"));
           newBriefDataViewDTO.setDataViewIssue (rstDataViewsList.getInt("DATA_VIEW_ISSUE"));
           newBriefDataViewDTO.setDataViewVersion (rstDataViewsList.getInt("DATA_VIEW_VERSION"));
           newBriefDataViewDTO.setDataViewName (rstDataViewsList.getString("DATA_VIEW_NAME"));
           newBriefDataViewDTO.setDataViewTypeID (rstDataViewsList.getInt("DATA_VIEW_TYPE_ID"));
           newBriefDataViewDTO.setDataViewTypeName (rstDataViewsList.getString("DATA_VIEW_TYPE_NAME"));
           newBriefDataViewDTO.setDataViewDescription( rstDataViewsList.getString("DATA_VIEW_DESCRIPTION"));
           newBriefDataViewDTO.setOverRideSQL( rstDataViewsList.getString("DATA_VIEW_OVERRIDE_SQL"));
          vecDataViewsList.addElement (newBriefDataViewDTO);
        }
        rstDataViewsList.close();
        stmtDataViewsList.close();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecDataViewsList ;
    }


    // ----------------------------------------------------------------------
    /* this method is used in scenario 2 (initializeQueryBuilder) */
    public static Vector getDataViewFieldsListByDisplayType (
            Connection argCon, 
            int argDataViewID, 
            int argFieldDisplayType)
    {
      Vector vecFieldList = new Vector() ;
      ResultSet rstFieldList ;
      PreparedStatement stmtFieldList ;
      String strFieldListQuery = null ;
      try
      {
          strFieldListQuery = new String (
            "select * " +
            "from VW_ADH_FIELD " +
            "where DATA_VIEW_ID = ? and FIELD_DISPLAY_TYPE_ID = ? " +
            "order by DATA_VIEW_ID, FIELD_ID");

        Utility.logger.debug(strFieldListQuery);
        stmtFieldList = argCon.prepareStatement(strFieldListQuery) ;
        //stmtFieldList.setString(1, (new Integer (argDataViewID)).toString());
        stmtFieldList.setInt(1, argDataViewID);
        stmtFieldList.setInt(2, argFieldDisplayType );

        Utility.logger.debug(argDataViewID+"---------"+argFieldDisplayType);
        
        rstFieldList = stmtFieldList.executeQuery();
        while(rstFieldList.next())
        {

         //System.out.print ( "field id ----> " + rstFieldList.getInt("FIELD_ID") ) ;
         //Utility.logger.debug ( " field name ----> " + rstFieldList.getString("FIELD_NAME") ) ;
         FieldModel newFieldModel = new FieldModel (

                  rstFieldList.getInt("DATA_VIEW_ID"),
                  rstFieldList.getInt("DATA_VIEW_ISSUE"),
                  rstFieldList.getInt("DATA_VIEW_VERSION"),
                  rstFieldList.getInt("FIELD_ID"),
                  rstFieldList.getString("FIELD_NAME"),
                  rstFieldList.getString("FIELD_DESCRIPTION"),
                  rstFieldList.getString("FIELD_SQL_CASH"),
                  rstFieldList.getInt("FIELD_DISPLAY_TYPE_ID"),
                  rstFieldList.getString("FIELD_DISPLAY_TYPE_NAME"),
                  rstFieldList.getInt("FIELD_TYPE_ID"),
                  rstFieldList.getString("FIELD_TYPE_NAME"),
                  rstFieldList.getInt("FIELD_TYPE_OBJECT_ID"),
                  rstFieldList.getInt("FIELD_RF_OBJECT")
                  //rstFieldList.getInt("FIELD_UNIQUE")
                  );

          debug("field id (1) = "+ newFieldModel.getFieldID());
          
          vecFieldList.addElement (newFieldModel);
        }

        rstFieldList.close();
        stmtFieldList.close();

      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecFieldList ;
    }


    // ----------------------------------------------------------------------
    /* this method is used in scenario 2 (initializeQueryBuilder) */
    public static DetailedDataViewDTO getDataViewDetails (Connection argCon, int argDataViewID)
    {
      DetailedDataViewDTO detailedDataViewDTO = new DetailedDataViewDTO ();
      try
      {
        PreparedStatement stmtDataViewDetails = null;

        //String strDataViewDetailsQuery = "select * from VW_ADH_DATA_VIEW where DATA_VIEW_ID = ? ";
        String strDataViewDetailsQuery =
          " select * " + 
          " from VW_ADH_DATA_VIEW, VW_ADH_UNIVERSE_DEFINITION, VW_ADH_UNIVERSE " + 
          " where VW_ADH_DATA_VIEW.DATA_VIEW_ID = ? " + 
          "   and VW_ADH_DATA_VIEW.DATA_VIEW_ID = VW_ADH_UNIVERSE_DEFINITION.DATA_VIEW_ID " + 
          "   and VW_ADH_UNIVERSE.UNIVERSE_ID = VW_ADH_UNIVERSE_DEFINITION.UNIVERSE_ID " ;

        stmtDataViewDetails = argCon.prepareStatement (strDataViewDetailsQuery) ;
        stmtDataViewDetails.setInt (1, argDataViewID);
        ResultSet rstDataViewDetails = stmtDataViewDetails.executeQuery();

        //Utility.logger.debug(" sql statement in getDataViewFieldsListByDisplayType " +strDataViewDetailsQuery );
        //Utility.logger.debug("first paramet is = " + argDataViewID);
        
        while (rstDataViewDetails.next())
        {
          detailedDataViewDTO.setDataViewID ( rstDataViewDetails.getInt("DATA_VIEW_ID") );
          detailedDataViewDTO.setDataViewIssue ( rstDataViewDetails.getInt("DATA_VIEW_ISSUE") );
          detailedDataViewDTO.setDataViewVersion ( rstDataViewDetails.getInt("DATA_VIEW_VERSION") );
          detailedDataViewDTO.setDataViewName ( rstDataViewDetails.getString("DATA_VIEW_NAME") );
          detailedDataViewDTO.setDataViewOverrideSQL ( rstDataViewDetails.getString("DATA_VIEW_OVERRIDE_SQL") );
          detailedDataViewDTO.setDataViewDescription ( rstDataViewDetails.getString("DATA_VIEW_DESCRIPTION") );
          detailedDataViewDTO.setDataViewTypeID ( rstDataViewDetails.getInt("DATA_VIEW_TYPE_ID") );
          detailedDataViewDTO.setDataViewTypeName ( rstDataViewDetails.getString("DATA_VIEW_TYPE_NAME") );
          detailedDataViewDTO.setDataViewUniverse ( rstDataViewDetails.getString("UNIVERSE_ID") );
        }

          /*DataViewModel newDataViewModel = new DataViewModel(
                rstDataViewsList.getInt("DATA_VIEW_ID"),
                rstDataViewsList.getInt("DATA_VIEW_ISSUE"),
                rstDataViewsList.getInt("DATA_VIEW_VERSION"),
                rstDataViewsList.getString("DATA_VIEW_NAME"),
                rstDataViewsList.getString("DATA_VIEW_OVERRIDE_SQL"),
                rstDataViewsList.getInt("DATA_VIEW_TYPE_ID"),
                rstDataViewsList.getString("DATA_VIEW_TYPE_NAME"),
                rstDataViewsList.getString("DATA_VIEW_DESCRIPTION"),
                rstDataViewsList.getInt("DATA_VIEW_STATUS_ID"),
                rstDataViewsList.getString("DATA_VIEW_STATUS_NAME") );
                */
        rstDataViewDetails.close();
        stmtDataViewDetails.close();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return detailedDataViewDTO ;
    }


    // ----------------------------------------------------------------------
    /* this method is used in scenario 5 (load query) */
    public static Vector getDataViewFromList(Connection argCon, int argDataViewID)
    {
      Vector vecFromList = new Vector() ;
      try
      {
        PreparedStatement stmtFromList ;
        //String strFromListQuery = "select * from VW_ADH_FROM where DATA_VIEW_ID = ? ";
        String strFromListQuery =   
              " select * " +
              " from VW_ADH_FROM, VW_ADH_DATA_VIEW " +
              " where VW_ADH_FROM.DATA_VIEW_ID = ? " +
              "      and VW_ADH_FROM.FR_DATA_VIEW_ID = VW_ADH_DATA_VIEW.DATA_VIEW_ID " +
              " order by  VW_ADH_FROM.FROM_ID DESC ";
        Utility.logger.debug(strFromListQuery);      
        stmtFromList = argCon.prepareStatement(strFromListQuery) ;
        stmtFromList.setInt (1, argDataViewID);
        ResultSet rstFromList = stmtFromList.executeQuery();
        while(rstFromList.next())
        {

          FromModel newFromModel = new FromModel();
          newFromModel.setFromID(rstFromList.getInt("FROM_ID"));
          newFromModel.setFRDataViewID(rstFromList.getInt("FR_DATA_VIEW_ID"));
          newFromModel.setFRDataViewName(rstFromList.getString("FR_DATA_VIEW_NAME"));
          newFromModel.setFRDataViewTypeID(rstFromList.getInt("DATA_VIEW_TYPE_ID"));
          newFromModel.setFRDataViewVersion(rstFromList.getInt("FR_DATA_VIEW_VERSION"));
          newFromModel.setFRDataViewDescription(rstFromList.getString("DATA_VIEW_DESCRIPTION"));
          //Utility.logger.debug ( "---> subquery type " + newFromModel.getFRDataViewTypeID() );

          vecFromList.addElement (newFromModel);
        }

        rstFromList.close();
        stmtFromList.close();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecFromList ;
    }



    // ----------------------------------------------------------------------
    /* this method is used in scenario 5 (load query) */
    public static DataViewModel getDataViewDetails_Model (Connection argCon, int argDataViewID)
    {
      DataViewModel newDataViewModel = null;
      ResultSet rstDataViewDetails = null;
      try
      {
        PreparedStatement stmtDataViewDetails = null;

        //String strDataViewDetailsQuery = "select * from VW_ADH_DATA_VIEW where DATA_VIEW_ID = ? ";
        String strDataViewDetailsQuery =
          " select * " + 
          " from VW_ADH_DATA_VIEW, VW_ADH_UNIVERSE_DEFINITION, VW_ADH_UNIVERSE " + 
          " where VW_ADH_DATA_VIEW.DATA_VIEW_ID = ? " + 
          "   and VW_ADH_DATA_VIEW.DATA_VIEW_ID = VW_ADH_UNIVERSE_DEFINITION.DATA_VIEW_ID " + 
          "   and VW_ADH_UNIVERSE.UNIVERSE_ID = VW_ADH_UNIVERSE_DEFINITION.UNIVERSE_ID " ;

        stmtDataViewDetails = argCon.prepareStatement (strDataViewDetailsQuery) ;
        stmtDataViewDetails.setInt (1, argDataViewID);
        rstDataViewDetails = stmtDataViewDetails.executeQuery();
        /*if (rstDataViewDetails != null)
          dataViewModel = new DataViewModel ();*/
        while (rstDataViewDetails.next())
        {
          newDataViewModel = new DataViewModel(
                rstDataViewDetails.getInt("DATA_VIEW_ID"),
                rstDataViewDetails.getInt("DATA_VIEW_ISSUE"),
                rstDataViewDetails.getInt("DATA_VIEW_VERSION"),
                rstDataViewDetails.getString("DATA_VIEW_NAME"),
                rstDataViewDetails.getString("DATA_VIEW_OVERRIDE_SQL"),
                rstDataViewDetails.getInt("DATA_VIEW_TYPE_ID"),
                rstDataViewDetails.getString("DATA_VIEW_TYPE_NAME"),
                rstDataViewDetails.getString("DATA_VIEW_DESCRIPTION"),
                rstDataViewDetails.getInt("DATA_VIEW_STATUS_ID"),
                rstDataViewDetails.getString("DATA_VIEW_STATUS_NAME"),
                rstDataViewDetails.getInt("DATA_VIEW_UNIQUE"),
                rstDataViewDetails.getInt("UNIVERSE_ID")
                );

          /*detailedDataViewDTO.setDataViewID ( rstDataViewDetails.getInt("DATA_VIEW_ID") );
          detailedDataViewDTO.setDataViewIssue ( rstDataViewDetails.getInt("DATA_VIEW_ISSUE") );
          detailedDataViewDTO.setDataViewVersion ( rstDataViewDetails.getInt("DATA_VIEW_VERSION") );
          detailedDataViewDTO.setDataViewName ( rstDataViewDetails.getString("DATA_VIEW_NAME") );
          detailedDataViewDTO.setDataViewOverrideSQL ( rstDataViewDetails.getString("DATA_VIEW_OVERRIDE_SQL") );
          detailedDataViewDTO.setDataViewDescription ( rstDataViewDetails.getString("DATA_VIEW_DESCRIPTION") );
          detailedDataViewDTO.setDataViewTypeID ( rstDataViewDetails.getInt("DATA_VIEW_TYPE_ID") );
          detailedDataViewDTO.setDataViewTypeName ( rstDataViewDetails.getString("DATA_VIEW_TYPE_NAME") );*/
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return newDataViewModel ;
    }
    // ----------------------------------------------------------------------
    /* this method is used in scenario 5 (load query) */
    /*public static Vector getDataViewDetailedFieldsList(Connection argCon, int argDataViewID,
                          Vector vecConstantsFieldList,
                          Vector vecDisplayedFieldList)
    {
      Vector vecFieldList = new Vector() ;
      ResultSet rstFieldList ;
      PreparedStatement stmtFieldList ;
      String strFieldListQuery = null ;
      try
      {
        strFieldListQuery = new String ("select * from VW_ADH_FIELD where DATA_VIEW_ID = ? order by FIELD_ID");

        stmtFieldList = argCon.prepareStatement(strFieldListQuery) ;
        //stmtFieldList.setString(1, (new Integer (argDataViewID)).toString());
        stmtFieldList.setInt(1, argDataViewID);

        rstFieldList = stmtFieldList.executeQuery();
        int fieldTypeID ;
        while(rstFieldList.next())
        {
          fieldTypeID = rstFieldList.getInt("FIELD_TYPE_ID");
          FieldTypeModel fieldTypeModel = new FieldTypeModel ();
          fieldTypeModel.setFieldTypeID (fieldTypeID) ;
          fieldTypeModel.setFieldTypeName( rstFieldList.getString("FIELD_TYPE_NAME") ) ;
          FieldDisplayTypeModel fieldDisplayTypeModel = new FieldDisplayTypeModel();
          fieldDisplayTypeModel.setFieldDisplayTypeID(rstFieldList.getInt("FIELD_DISPLAY_TYPE_ID"));
          fieldDisplayTypeModel.setFieldDisplayTypeName(rstFieldList.getString("FIELD_DISPLAY_TYPE_NAME"));
          if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
          {
            DataViewFieldDTO dataViewFieldDTO = new DataViewFieldDTO () ;
            dataViewFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );
            dataViewFieldDTO.setFieldName( rstFieldList.getString("FIELD_NAME") );
            dataViewFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            dataViewFieldDTO.setFieldType( fieldTypeModel );
            dataViewFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            dataViewFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );
            dataViewFieldDTO.setFieldDisplayType(fieldDisplayTypeModel);
            dataViewFieldDTO.setFieldUnique(rstFieldList.getInt("FIELD_UNIQUE"));
            //dataViewFieldDTO.setParentDataViewID();
            //dataViewFieldDTO.setParentDataViewVersion();
            //rstFieldList.getInt("FIELD_TYPE_OBJECT_ID"),
            //rstFieldList.getInt("FIELD_RF_OBJECT"),
            vecFieldList.addElement (dataViewFieldDTO);
            if ( fieldDisplayTypeModel.getFieldDisplayTypeID() == QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED )
            {
              Utility.logger.debug ("adding field: "+ dataViewFieldDTO.getFieldName()) ;
              DataViewFieldDTO dataViewFieldDTOClone = dataViewFieldDTO.dtoClone () ;
              Utility.logger.debug ("adding clone: "+ dataViewFieldDTOClone.getFieldName()) ;
              vecDisplayedFieldList.addElement (dataViewFieldDTOClone );
            }
          }
          else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
          {
            ConstantFieldDTO constantFieldDTO = new ConstantFieldDTO () ;
            constantFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );
            constantFieldDTO.setFieldName ( rstFieldList.getString("FIELD_NAME")  );
            constantFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            constantFieldDTO.setFieldType( fieldTypeModel );
            constantFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            constantFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );
            constantFieldDTO.setFieldDisplayType(fieldDisplayTypeModel);
            constantFieldDTO.setFieldUnique(rstFieldList.getInt("FIELD_UNIQUE"));
            //constantFieldDTO.setValue (   );
            vecFieldList.addElement (constantFieldDTO);
            if ( fieldDisplayTypeModel.getFieldDisplayTypeID() == QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED )
              vecDisplayedFieldList.addElement(constantFieldDTO);
            vecConstantsFieldList.addElement (constantFieldDTO);
          }
        }
        rstFieldList.close();
        stmtFieldList.close();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecFieldList ;
    }
    */
    //public static QueryDTO getDataViewDetailedDisplayedFieldsList(
    public static QueryDTO getDataViewFieldsList(
                          Connection argCon, 
                          int argDataViewID,
                          QueryDTO queryDTO,
                          Vector vecReportParameterList
                          )
    {
      //Vector vecFieldList = new Vector() ;
      ResultSet rstFieldList ;
      PreparedStatement stmtFieldList ;
      String strFieldListQuery = null ;
      Vector vecFunctionsFieldList  = new Vector () ;
      Vector vecDisplayedFieldList  = new Vector () ;
      Vector vecConstantsFieldList  = new Vector () ;
      Vector vecParametersFieldList = new Vector () ;
      //FieldDisplayTypeModel fieldDisplayTypeModel = null ;
      FieldTypeModel fieldTypeModel = null;
      try
      {
        debug("******************************************************************************************");
        debug (" getDataViewFieldsList " + argDataViewID);
        // getting list of functions ---------------------
        debug("$$$$$$$$$$4getting list of functions");
        strFieldListQuery = new String (
            "select * " +  
            "from VW_ADH_FIELD, VW_ADH_FUNCTION " + 
            "where VW_ADH_FIELD.DATA_VIEW_ID = ? " +  
            "   and  VW_ADH_FIELD.FIELD_TYPE_ID = ? " +
            "   and  VW_ADH_FIELD.FIELD_RF_OBJECT = VW_ADH_FUNCTION.FUNCTION_ID " +       
            "order by VW_ADH_FIELD.ITEM_POSITION ASC ");
            //"order by FIELD_ID ASC ");
            //"order by FIELD_ID DESC ");

        debug(strFieldListQuery);
        debug(argDataViewID+"");
        debug(QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD+"");
        stmtFieldList = argCon.prepareStatement(strFieldListQuery) ;
        stmtFieldList.setInt(1, argDataViewID);
        stmtFieldList.setInt(2, QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD);
        rstFieldList = stmtFieldList.executeQuery();
        fieldTypeModel = new FieldTypeModel ();
        fieldTypeModel.setFieldTypeID ( QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD ) ;
        while(rstFieldList.next())
        {

            FunctionFieldDTO functionFieldDTO = new FunctionFieldDTO () ;
            FunctionDTO functionDTO = new FunctionDTO () ;
            functionFieldDTO.setFieldType( fieldTypeModel );
            //functionFieldDTO.setFieldDisplayType(fieldDisplayTypeModel);
            functionFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );
            functionFieldDTO.setFieldName ( rstFieldList.getString("FIELD_NAME")  );
            functionFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            functionFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            functionFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );
            int functionID = rstFieldList.getInt("FIELD_RF_OBJECT") ;
            functionDTO.setFunctionID( functionID );
            functionDTO.setFunctionName( rstFieldList.getString("FUNCTION_NAME") );
            functionDTO.setFunctionSQL( rstFieldList.getString("FUNCTION_SQL_CALL") );
            functionDTO.setFunctionDescription( rstFieldList.getString("FUNCTION_DESCRIPTION") );
            functionDTO.setFunctionHelpText( rstFieldList.getString("FUNCTION_HELP_TEXT") );
            functionDTO.setFunctionStatusID( rstFieldList.getInt("FUNCTION_STATUS_ID") );
            functionDTO.setFunctionTypeID( rstFieldList.getInt("FUNCTION_TYPE_ID") );
            functionDTO.setFunctionTypeName(rstFieldList.getString("FUNCTION_TYPE_NAME"));
            functionDTO.setFunctionStatusName( rstFieldList.getString("FUNCTION_STATUS_NAME") ); 
            functionDTO.setParameterDefinitions( QueryBuilderDAO.getFunctionParametersList (argCon, functionID) );
            functionFieldDTO.setFunctionDTO(functionDTO); 

            //Utility.logger.debug("adding function  "+ functionFieldDTO.getFieldName());
            debug ( null, "  got function field id: " + functionFieldDTO.getFieldID() + 
                    " representing function id: " + functionID ) ;

            Vector vecParamList = new Vector () ;
            String strParamListQuery = new String (
                " select * " +
                " from   vw_adh_parameter_definition " +
                " where  vw_adh_parameter_definition.FUNCTION_ID = ? ");  
            PreparedStatement stmtParamList = argCon.prepareStatement(strParamListQuery) ;
            stmtParamList.setInt(1, functionID);
            ResultSet rstParamList = stmtParamList.executeQuery();
            while (rstParamList.next())
            {
              FunctionParameterValueModel paramModel = new FunctionParameterValueModel ();
              paramModel.setParameterDefinitionID(rstParamList.getInt("PARAMETER_DEFINITION_ID") );

              debug ( null, "  got param definition id: " + 
                    paramModel.getParameterDefinitionID() ) ;

              /*String strParamFieldListQuery = new String (
                  " select  param_field.FIELD_ID, param_field.FIELD_TYPE_ID, param_field.FIELD_SQL_CASH " +
                  " from vw_adh_parameter_value, vw_adh_field function_field, vw_adh_field param_field " +
                  " where vw_adh_parameter_value.LINK_FIELD_ID = function_field.FIELD_ID and " +
                  "  vw_adh_parameter_value.VALUE_FIELD_ID = param_field.FIELD_ID and " +
                  "  vw_adh_parameter_value.LINK_FIELD_ID = ? and " +
                  "  vw_adh_parameter_value.PARAMETER_DEFINITION_ID = ? " ) ;
              PreparedStatement stmtParamFieldList = argCon.prepareStatement(strParamFieldListQuery) ;
              stmtParamFieldList.setInt(1, functionFieldDTO.getFieldID());
              stmtParamFieldList.setInt(2, paramModel.getParameterDefinitionID());
              ResultSet rstParamFieldList = stmtParamFieldList.executeQuery();*/
              String strParamFieldListQuery = new String (
                  " select  * " +
                  " from vw_adh_parameter_value, vw_adh_field " + 
                  " where vw_adh_parameter_value.VALUE_FIELD_ID = vw_adh_field.FIELD_ID and " +
                  "     vw_adh_parameter_value.LINK_FIELD_ID = ? and " +
                  "     vw_adh_parameter_value.PARAMETER_DEFINITION_ID = ? " 
                  // + " order by vw_adh_parameter_value.PARAMETER_VALUE_PLACE  " 
                  ) ;

            //hagry constructing param list
/*
            Utility.logger.debug(" getting params of the function sql ");
            Utility.logger.debug(strParamFieldListQuery);
            Utility.logger.debug(functionFieldDTO.getFieldID());
            Utility.logger.debug(paramModel.getParameterDefinitionID());
*/            
            
              PreparedStatement stmtParamFieldList = argCon.prepareStatement(strParamFieldListQuery) ;
              stmtParamFieldList.setInt(1, functionFieldDTO.getFieldID());
              stmtParamFieldList.setInt(2, paramModel.getParameterDefinitionID());
              ResultSet rstParamFieldList = stmtParamFieldList.executeQuery();
              //Utility.logger.debug ("  executed query: \n  " + strParamFieldListQuery ) ;
              //Utility.logger.debug ("  param 1: " + functionID) ;
              //Utility.logger.debug ("  param 2: " + paramModel.getParameterDefinitionID()) ;
              
              while (rstParamFieldList.next())
              {
                // commented by fady 20040701
                //paramModel.setParameterValueID(rstParamFieldList.getInt("FIELD_ID") );
                paramModel.setParameterValueID(rstParamFieldList.getInt("FIELD_RF_OBJECT") );
                paramModel.setParameterValueFieldType(rstParamFieldList.getInt("FIELD_TYPE_ID") );
                paramModel.setParameterValueSQLCache(rstParamFieldList.getString("FIELD_SQL_CASH") );
                //hagry I ADDED this to put the code of the place of the parameter 
                paramModel.setParameterValuePlace(rstParamFieldList.getInt("PARAMETER_VALUE_PLACE"));

                debug ( "getDataViewFieldsList", 
                      "  got param field id: " + rstParamFieldList.getInt("FIELD_ID") +
                      "  rf object: " + rstParamFieldList.getInt("FIELD_RF_OBJECT") + 
                      " of type " + paramModel.getParameterValueFieldType() +
                      " paramModel.getParameterValueID() " + paramModel.getParameterValueID() 
                      ) ;
              }

              vecParamList.addElement(paramModel);
            }

            Utility.logger.debug("report parameter size  :"+vecReportParameterList.size());
            Utility.logger.debug("function parameter size  :"+vecParamList.size());

            /*Waseem
             * for (int h=0;h<vecReportParameterList.size();h++)
            {
              ParameterFieldDTO paramFieldDTO = (ParameterFieldDTO) vecReportParameterList.get(h); 
              int fieldId = paramFieldDTO.getFieldID();
              String fieldName = paramFieldDTO.getFieldName();
              String labelText = paramFieldDTO.getLabelText();
              
              Utility.logger.debug("field id : "+fieldId+" -- field name : "+fieldName+" -- label text : "+labelText);
            }

            for(int f=0;f<vecParamList.size();f++)
            {
              FunctionParameterValueModel functionParameterValueModel = (FunctionParameterValueModel)vecParamList.get(f);
              int paramDefFunId = functionParameterValueModel.getParameterDefinitionID();
              String paramDefName = functionParameterValueModel.getParameterDefinitionName();
              int paramValId = functionParameterValueModel.getParameterValueID();
              String paramValName = functionParameterValueModel.getParameterValueName();
              int paramValueFieldType = functionParameterValueModel.getParameterValueFieldType();
              int paramFunFieldId = functionParameterValueModel.getParentFunctionFieldID();
              int paramValueDataViewId = functionParameterValueModel.getParameterValueDataViewID();
              int paramValPlace = functionParameterValueModel.getParameterValuePlace();
              int paramValueParentFunctionId = functionParameterValueModel.getParentFunctionFieldID();
              Utility.logger.debug("xxxx param def fun id : "+paramDefFunId+" -- param val dataview Id : "+paramValueDataViewId+" -- param val id : "+paramValId+" param val place  : "+paramValPlace+" -- param val field type : "+paramValueFieldType+" -- param val parent function id : "+paramValueParentFunctionId);
            }

            if(vecReportParameterList.size() == vecParamList.size())
            {
              for(int r=0;r<vecParamList.size();r++)
              {
                ((FunctionParameterValueModel)vecParamList.get(r)).setParameterValueSQLCache(
                ( (ParameterFieldDTO) vecReportParameterList.elementAt(r) ).getFieldSQLCash() ) ;
              }
            }
            */
            
            functionFieldDTO.setFunctionParameterValues( vecParamList );

/*
            Utility.logger.debug("adding  function ");
            Utility.logger.debug(functionFieldDTO.getFunctionParameterValues().size());
*/            
            
            vecFunctionsFieldList.addElement (functionFieldDTO );
        }

        debug("$$$$$$$$$$4getting list of constants");
        // list of constants -----------------------------------------
        strFieldListQuery = new String (
            "select * " +  
            "from VW_ADH_FIELD " + 
            "where VW_ADH_FIELD.DATA_VIEW_ID = ? " +  
            "   and  VW_ADH_FIELD.FIELD_TYPE_ID = ? " +    
            "   and  VW_ADH_FIELD.FIELD_DISPLAY_TYPE_ID = ? " +    
            "   and  VW_ADH_FIELD.FIELD_RF_OBJECT is null " +    
            "order by VW_ADH_FIELD.ITEM_POSITION ASC " 
            //"order by FIELD_ID "
            );

        stmtFieldList = argCon.prepareStatement(strFieldListQuery) ;
        stmtFieldList.setInt(1, argDataViewID);
        stmtFieldList.setInt(2, QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD);
        stmtFieldList.setInt(3, QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED);
        rstFieldList = stmtFieldList.executeQuery();
        fieldTypeModel = null ;
        while(rstFieldList.next())
        {
            ConstantFieldDTO constantFieldDTO = new ConstantFieldDTO () ;
            constantFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );

            //debug("field id (2) = " + constantFieldDTO.getFieldID());
            //debug ( null, "  list of const -  const id: " + constantFieldDTO.getFieldID () ) ;
            constantFieldDTO.setFieldName ( rstFieldList.getString("FIELD_NAME")  );
            constantFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            fieldTypeModel = new FieldTypeModel ();
            fieldTypeModel.setFieldTypeID ( QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD ) ;
            constantFieldDTO.setFieldType( fieldTypeModel );
            constantFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            constantFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );
            //fieldDisplayTypeModel = new FieldDisplayTypeModel();
            //fieldDisplayTypeModel.setFieldDisplayTypeID(rstFieldList.getInt("FIELD_DISPLAY_TYPE_ID"));
            //fieldDisplayTypeModel.setFieldDisplayTypeName(rstFieldList.getString("FIELD_DISPLAY_TYPE_NAME"));
            //constantFieldDTO.setFieldDisplayType(fieldDisplayTypeModel);
            vecConstantsFieldList.addElement (constantFieldDTO );
        }

        // list of parameters -----------------------------------------
        debug("$$$$$$$$$$4getting list of paramaters");
        strFieldListQuery = new String (
            "select *  " + 
            "from VW_ADH_FIELD, VW_ADH_INPUT_PARAM  " + 
            "where VW_ADH_FIELD.DATA_VIEW_ID = ?   " + 
            "   and  VW_ADH_FIELD.FIELD_TYPE_ID = ?     " + 
            "   and  VW_ADH_FIELD.FIELD_DISPLAY_TYPE_ID = ?     " + 
            "   and  VW_ADH_FIELD.FIELD_RF_OBJECT is null     " + 
            "   and  VW_ADH_FIELD.FIELD_ID = VW_ADH_INPUT_PARAM.FIELD_ID     " + 
            "order by VW_ADH_FIELD.ITEM_POSITION ASC " 
            //"order by VW_ADH_INPUT_PARAM.INPUT_PARAM_ORDER " 
        );

        stmtFieldList = argCon.prepareStatement(strFieldListQuery) ;
        stmtFieldList.setInt(1, argDataViewID);
        stmtFieldList.setInt(2, QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD);
        stmtFieldList.setInt(3, QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED);

        /*Utility.logger.debug("**************");
        Utility.logger.debug(strFieldListQuery);
        Utility.logger.debug(argDataViewID);
        Utility.logger.debug(QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD);
        Utility.logger.debug(QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED);
        Utility.logger.debug("**************");*/
        
        rstFieldList = stmtFieldList.executeQuery();
        fieldTypeModel = null ;
        while(rstFieldList.next())
        {
            ParameterFieldDTO parameterFieldDTO = new ParameterFieldDTO () ;
            parameterFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );

            debug("field id (2) = " + parameterFieldDTO.getFieldID());
            //debug ( null, "  list of param -  param id: " + parameterFieldDTO.getFieldID () ) ;
            parameterFieldDTO.setFieldName ( rstFieldList.getString("FIELD_NAME") );
            parameterFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            fieldTypeModel = new FieldTypeModel ();
            fieldTypeModel.setFieldTypeID ( QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD ) ;
            parameterFieldDTO.setFieldType( fieldTypeModel );
            parameterFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            parameterFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );
            parameterFieldDTO.setControlTypeID( rstFieldList.getInt("INPUT_CONTROL_TYPES_ID") );
            parameterFieldDTO.setLabelText( rstFieldList.getString("INPUT_PARAM_LABEL_TEXT") );
            parameterFieldDTO.setOrder( rstFieldList.getInt("INPUT_PARAM_ORDER") );
            //parameterFieldDTO.setValueType( );
            //parameterFieldDTO.set
            vecParametersFieldList.addElement (parameterFieldDTO);
        }


        // getting list of displayed fields ---------------------
        debug("$$$$$$$$$$4getting list of displayed fields");
        strFieldListQuery = new String (
            "select * " +  
            "from VW_ADH_FIELD " + 
            "where VW_ADH_FIELD.DATA_VIEW_ID = ? " +            
            "   and  VW_ADH_FIELD.FIELD_DISPLAY_TYPE_ID = ? " +    
            "order by FIELD_ID "
            );
        debug(strFieldListQuery);
        stmtFieldList = argCon.prepareStatement(strFieldListQuery) ;
        //stmtFieldList.setString(1, (new Integer (argDataViewID)).toString());
        stmtFieldList.setInt(1, argDataViewID);
        stmtFieldList.setInt(2, QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED);
        debug(""+argDataViewID);
        debug(""+QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED);
        
        rstFieldList = stmtFieldList.executeQuery();
        int fieldTypeID ;
        while(rstFieldList.next())
        {
          fieldTypeID = rstFieldList.getInt("FIELD_TYPE_ID");
          fieldTypeModel = new FieldTypeModel ();
          fieldTypeModel.setFieldTypeID (fieldTypeID) ;
          fieldTypeModel.setFieldTypeName( rstFieldList.getString("FIELD_TYPE_NAME") ) ;

          
          // fieldDisplayTypeModel = new FieldDisplayTypeModel();
          //fieldDisplayTypeModel.setFieldDisplayTypeID(rstFieldList.getInt("FIELD_DISPLAY_TYPE_ID"));
          //fieldDisplayTypeModel.setFieldDisplayTypeName(rstFieldList.getString("FIELD_DISPLAY_TYPE_NAME"));

          if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
          {
            DataViewFieldDTO dataViewFieldDTO = new DataViewFieldDTO () ;
            dataViewFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );
            debug("field id (4) = "+ dataViewFieldDTO.getFieldID());
            dataViewFieldDTO.setFieldName( rstFieldList.getString("FIELD_NAME") );
            dataViewFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            dataViewFieldDTO.setFieldType( fieldTypeModel );
            dataViewFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            dataViewFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );
            //debug ( null, "  while loading: " + dataViewFieldDTO.getFieldSQLCash( ) ) ;
            //dataViewFieldDTO.setFieldDisplayType(fieldDisplayTypeModel);
            //dataViewFieldDTO.setFieldUnique(rstFieldList.getInt("FIELD_UNIQUE"));
            dataViewFieldDTO.setParentDataViewID(rstFieldList.getInt("FIELD_RF_OBJECT"));

            // join with dataview table
            String strDataViewListQuery = new String (
          			"select * " +
                "from VW_ADH_DATA_VIEW " +
                "where VW_ADH_DATA_VIEW.DATA_VIEW_ID = ? ");

            debug(strDataViewListQuery);
            PreparedStatement stmtDataViewList = argCon.prepareStatement(strDataViewListQuery) ;
            //stmtFieldList.setString(1, (new Integer (argDataViewID)).toString());
            stmtDataViewList.setInt(1, dataViewFieldDTO.getParentDataViewID());
            
            ResultSet rstDataViewList = stmtDataViewList.executeQuery();
            while(rstDataViewList.next())
            {
               //rstDataViewList.getString("FIELD_TYPE_ID");
               dataViewFieldDTO.setParentDataViewName(rstDataViewList.getString("DATA_VIEW_NAME"));
            }
            //dataViewFieldDTO.setParentDataViewName(rstFieldList.getString("FIELD_RF_OBJECT_NAME"));
//            Utility.logger.debug("dataViewFieldDTO.getFieldID "+ dataViewFieldDTO.getFieldID());
//            Utility.logger.debug("dataViewFieldDTO.getFieldName "+ dataViewFieldDTO.getFieldName());
            
            vecDisplayedFieldList.addElement (dataViewFieldDTO );
            //dataViewFieldDTO.setParentDataViewVersion();
            //rstFieldList.getInt("FIELD_TYPE_OBJECT_ID"),
            //rstFieldList.getInt("FIELD_RF_OBJECT"),
            //vecFieldList.addElement (dataViewFieldDTO);
            //if ( fieldDisplayTypeModel.getFieldDisplayTypeID() == QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED )
              //vecDisplayedFieldList.addElement (dataViewFieldDTO );
          }
          else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
          {
            ConstantFieldDTO constantFieldDTO = new ConstantFieldDTO () ;
            //constantFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );
            constantFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_RF_OBJECT") );
            constantFieldDTO.setFieldName ( rstFieldList.getString("FIELD_NAME")  );
            constantFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            constantFieldDTO.setFieldType( fieldTypeModel );
            constantFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            constantFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );
            //constantFieldDTO.setFieldDisplayType(fieldDisplayTypeModel);
            //vecFieldList.addElement (constantFieldDTO);
            vecDisplayedFieldList.addElement (constantFieldDTO );
            
            /*String strConstListQuery = new String (
                "select * " +  
                "from VW_ADH_FIELD " + 
                "where VW_ADH_FIELD.DATA_VIEW_ID = ? " +  
                "   and  VW_ADH_FIELD.FIELD_DISPLAY_TYPE_ID = ? " +    
                "   and  VW_ADH_FIELD.FIELD_RF_OBJECT = ? " +    
                "order by FIELD_ID "
                );
            PreparedStatement stmtConstList = argCon.prepareStatement(strConstListQuery) ;
            stmtConstList.setInt(1, argDataViewID);
            stmtConstList.setInt(2, QueryBuilderInterfaceKey.DISPLAY_TYPE_NOT_DISPLAYED);
            stmtConstList.setInt(3, constID);
            ResultSet rstConstList = stmtConstList.executeQuery();
            //int fieldTypeID ;
            while (rstConstList.next())
            {
              //fieldTypeID = rstConstList.getInt("FIELD_TYPE_ID");
              //constantFieldDTO.setFieldUnique(rstFieldList.getInt("FIELD_UNIQUE"));
              //constantFieldDTO.setValue (   );
              //vecFieldList.addElement (constantFieldDTO);
            }
            */



            /*
            for (int i=0 ; i<vecConstantsFieldList.size(); i++)
            {
              if ( ( (ConstantFieldDTO) vecConstantsFieldList.elementAt(i) ).getFieldID() == constID )
              {
                constantFieldDTO = (ConstantFieldDTO) ((ConstantFieldDTO) vecConstantsFieldList.elementAt(i)).clone() ;

                vecDisplayedFieldList.addElement (constantFieldDTO );
              }
            }*/
          }
          //else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD)
          else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_RF_FIELD)
          {
//          Utility.logger.debug("fucntion rf");

            FunctionFieldDTO functionFieldDTO = null ;
            int functionID = rstFieldList.getInt("FIELD_RF_OBJECT") ;
            for (int i=0 ; i<vecFunctionsFieldList.size(); i++)
            {
              if ( ( (FunctionFieldDTO) vecFunctionsFieldList.elementAt(i) ).getFieldID() == functionID )
              {
                functionFieldDTO = (FunctionFieldDTO) ((FunctionFieldDTO) vecFunctionsFieldList.elementAt(i)).clone() ;
               functionFieldDTO.setFieldName ( rstFieldList.getString("FIELD_NAME")  );
                //fieldDisplayTypeModel = new FieldDisplayTypeModel();
                //fieldDisplayTypeModel.setFieldDisplayTypeID (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED);
                //functionFieldDTO.setFieldDisplayType(fieldDisplayTypeModel);
                
                vecDisplayedFieldList.addElement (functionFieldDTO);
              }
            }
          }
          else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD)
          {
            ParameterFieldDTO parameterFieldDTO = null ;
            int parameterID = rstFieldList.getInt("FIELD_RF_OBJECT") ;
            for (int i=0 ; i<vecParametersFieldList.size(); i++)
            {
              if ( ( (ParameterFieldDTO) vecParametersFieldList.elementAt(i) ).getFieldID() == parameterID )
              {
                parameterFieldDTO = (ParameterFieldDTO) ((ParameterFieldDTO) vecParametersFieldList.elementAt(i)).clone() ;
                //fieldDisplayTypeModel = new FieldDisplayTypeModel();
                //fieldDisplayTypeModel.setFieldDisplayTypeID (QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED);
                //functionFieldDTO.setFieldDisplayType(fieldDisplayTypeModel);
                vecDisplayedFieldList.addElement (parameterFieldDTO);
              }
            }
          }
        }
        rstFieldList.close();
        stmtFieldList.close();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      //return vecFieldList ;

  
   //   detailedDataViewDTO.setDataViewFields (vecFieldList) ;
   //   hMapDetailedDataViews.put(new Integer (dataViewModel.getDataViewID()),detailedDataViewDTO) ;
   //   queryDTO.setDetailedDataViews(hMapDetailedDataViews);
 
/*
      for (int i = 0 ; i <vecDisplayedFieldList.size();i++)
      {
        Utility.logger.debug(vecDisplayedFieldList.get(i).getClass().getName());
      }
      */
      queryDTO.setDisplayedFields(vecDisplayedFieldList);
      queryDTO.setConstantsFields(vecConstantsFieldList);
      queryDTO.setParametersFields(vecParametersFieldList);
      queryDTO.setFunctionsFields(vecFunctionsFieldList);

      return queryDTO ;
    }

    // ----------------------------------------------------------------------
    
    //public static QueryDTO getDataViewDetailedFieldsList(
    public static QueryDTO getDataViewFromFieldsList(
                          Connection argCon, 
                          //int argDataViewID,
                          QueryDTO queryDTO,
                          DetailedDataViewDTO detailedDataViewDTO,
                          //DataViewModel dataViewModel,
                          HashMap hMapDetailedDataViews
                          )
    {
      //Vector vecConstantsFieldList = new Vector () ;
      //Vector vecFunctionsFieldList = new Vector () ;
      Vector vecFieldList = new Vector() ;
      ResultSet rstFieldList ;
      PreparedStatement stmtFieldList ;
      String strFieldListQuery = null ;
      //Vector vecDisplayedFieldList = new Vector () ;
      try
      {
        // strFieldListQuery = new String ("select * from VW_ADH_FIELD where DATA_VIEW_ID = ? order by FIELD_ID");
        strFieldListQuery = new String (
            "select " +  
            "	VW_ADH_FIELD.DATA_VIEW_ID, " +  
            "	VW_ADH_FIELD.DATA_VIEW_ISSUE, " +  
            "	VW_ADH_FIELD.DATA_VIEW_VERSION, " +  
            "	VW_ADH_FIELD.FIELD_ID, " +  
            "	VW_ADH_FIELD.FIELD_NAME, " +  
            "	VW_ADH_FIELD.FIELD_DESCRIPTION, " +  
            "	VW_ADH_FIELD.FIELD_SQL_CASH, " +  
            "	VW_ADH_FIELD.FIELD_DISPLAY_TYPE_ID, " +  
            "	VW_ADH_FIELD.FIELD_DISPLAY_TYPE_NAME, " +  
            "	VW_ADH_FIELD.FIELD_TYPE_ID, " +
            "	VW_ADH_FIELD.FIELD_RF_OBJECT, " +  
            "	VW_ADH_FIELD.FIELD_TYPE_NAME " +  
            "from VW_ADH_FIELD, VW_ADH_DATA_VIEW " +  
            "where VW_ADH_FIELD.DATA_VIEW_ID = ? " +  
            "	  and  VW_ADH_FIELD.DATA_VIEW_ID = VW_ADH_DATA_VIEW.DATA_VIEW_ID " +         
               "   and  VW_ADH_FIELD.FIELD_DISPLAY_TYPE_ID = ? " +    
            "order by FIELD_ID "
        );

/*
        Utility.logger.debug("sql = " + strFieldListQuery);
        Utility.logger.debug("param 1 = " + detailedDataViewDTO.getDataViewID());
        Utility.logger.debug("param 2 = " + QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED);
*/        
        stmtFieldList = argCon.prepareStatement(strFieldListQuery) ;
        stmtFieldList.setInt(1, detailedDataViewDTO.getDataViewID());      
        stmtFieldList.setInt(2, QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED);

        rstFieldList = stmtFieldList.executeQuery();
        int fieldTypeID ;
        while(rstFieldList.next())          
        {
          fieldTypeID = rstFieldList.getInt("FIELD_TYPE_ID");
          FieldTypeModel fieldTypeModel = new FieldTypeModel ();
          fieldTypeModel.setFieldTypeID (fieldTypeID) ;
          fieldTypeModel.setFieldTypeName( rstFieldList.getString("FIELD_TYPE_NAME") ) ;
          
          //FieldDisplayTypeModel fieldDisplayTypeModel = new FieldDisplayTypeModel();
          //fieldDisplayTypeModel.setFieldDisplayTypeID(rstFieldList.getInt("FIELD_DISPLAY_TYPE_ID"));
          //fieldDisplayTypeModel.setFieldDisplayTypeName(rstFieldList.getString("FIELD_DISPLAY_TYPE_NAME"));
          if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
          {
            DataViewFieldDTO dataViewFieldDTO = new DataViewFieldDTO () ;
            dataViewFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );
            debug("field id (6) = "+ dataViewFieldDTO.getFieldID());
            dataViewFieldDTO.setFieldName( rstFieldList.getString("FIELD_NAME") );
            dataViewFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            dataViewFieldDTO.setFieldType( fieldTypeModel );
            dataViewFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            dataViewFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );
         //   dataViewFieldDTO.setFieldDisplayType(1);
            //dataViewFieldDTO.setFieldUnique(rstFieldList.getInt("FIELD_UNIQUE"));
            //vecDisplayedFieldList.addElement (dataViewFieldDTO );
            //dataViewFieldDTO.setParentDataViewVersion();
            //rstFieldList.getInt("FIELD_TYPE_OBJECT_ID"),
            //rstFieldList.getInt("FIELD_RF_OBJECT"),
            vecFieldList.addElement (dataViewFieldDTO);
            //if ( fieldDisplayTypeModel.getFieldDisplayTypeID() == QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED )
              //vecDisplayedFieldList.addElement (dataViewFieldDTO );
          }
          else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
          {
/*
            ConstantFieldDTO constantFieldDTO = new ConstantFieldDTO () ;
            constantFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );
            constantFieldDTO.setFieldName ( rstFieldList.getString("FIELD_NAME")  );
            constantFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            constantFieldDTO.setFieldType( fieldTypeModel );
            constantFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            constantFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );
            
            
            vecFieldList.addElement (constantFieldDTO);

*/
            FieldTypeModel fieldTypeModel2 = new FieldTypeModel ();
            fieldTypeModel2.setFieldTypeID (1) ;
            fieldTypeModel2.setFieldTypeName("DB FIELD") ;
            
            DataViewFieldDTO dataViewFieldDTO = new DataViewFieldDTO () ;
            dataViewFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );
            
            dataViewFieldDTO.setFieldName( rstFieldList.getString("FIELD_NAME") );
            dataViewFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            dataViewFieldDTO.setFieldType( fieldTypeModel2 );
            dataViewFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            dataViewFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );
         //   dataViewFieldDTO.setFieldDisplayType(1);
            //dataViewFieldDTO.setFieldUnique(rstFieldList.getInt("FIELD_UNIQUE"));
            //vecDisplayedFieldList.addElement (dataViewFieldDTO );
            //dataViewFieldDTO.setParentDataViewVersion();
            //rstFieldList.getInt("FIELD_TYPE_OBJECT_ID"),
            //rstFieldList.getInt("FIELD_RF_OBJECT"),
            vecFieldList.addElement (dataViewFieldDTO);
            
            
          }
          else if (fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_RF_FIELD )
          {    
          //hagry i had to comment this code since for the view that use another view
          //there is no need for the info that this field is a function field or whatever
          //all what we need is the to see it as a db field 
          //the reason i modified the code that itcaused a bug when a function field in a data view is used in another view
          //and not put it in the selected field just put it in the term the load dont act properly
          //since on the client side it expect that all the fields come to it as db fields
          //same bug i discoverd in the constant part too that why i modified it too 4/8/2005

        /*
             FunctionFieldDTO functionFieldDTO = new FunctionFieldDTO () ;
            FunctionDTO functionDTO = new FunctionDTO () ;
            functionFieldDTO.setFieldType( fieldTypeModel );                         
            functionFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );
            functionFieldDTO.setFieldName ( rstFieldList.getString("FIELD_NAME")  );
            functionFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            functionFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            functionFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );

            int functionID = rstFieldList.getInt("FIELD_RF_OBJECT") ;
            functionDTO.setFunctionID( functionID );
            */
            
/*
            functionDTO.setFunctionName( rstFieldList.getString("FUNCTION_NAME") );
            functionDTO.setFunctionSQL( rstFieldList.getString("FUNCTION_SQL_CALL") );
            functionDTO.setFunctionDescription( rstFieldList.getString("FUNCTION_DESCRIPTION") );
            functionDTO.setFunctionHelpText( rstFieldList.getString("FUNCTION_HELP_TEXT") );
            functionDTO.setFunctionStatusID( rstFieldList.getInt("FUNCTION_STATUS_ID") );
            functionDTO.setFunctionTypeID( rstFieldList.getInt("FUNCTION_TYPE_ID") );
            functionDTO.setFunctionTypeName(rstFieldList.getString("FUNCTION_TYPE_NAME"));
            functionDTO.setFunctionStatusName( rstFieldList.getString("FUNCTION_STATUS_NAME") ); 
            functionDTO.setParameterDefinitions( QueryBuilderDAO.getFunctionParametersList (argCon, functionID) );
            functionFieldDTO.setFunctionDTO(functionDTO); 
*/
          //hagry i commented this to try something
        //    vecFieldList.addElement (functionFieldDTO );

        
            FieldTypeModel fieldTypeModel2 = new FieldTypeModel ();
            fieldTypeModel2.setFieldTypeID (1) ;
            fieldTypeModel2.setFieldTypeName("DB FIELD") ;
            
            DataViewFieldDTO dataViewFieldDTO = new DataViewFieldDTO () ;
            dataViewFieldDTO.setFieldID ( rstFieldList.getInt("FIELD_ID") );
            
            dataViewFieldDTO.setFieldName( rstFieldList.getString("FIELD_NAME") );
            dataViewFieldDTO.setDataViewID( rstFieldList.getInt("DATA_VIEW_ID") );
            dataViewFieldDTO.setFieldType( fieldTypeModel2 );
            dataViewFieldDTO.setFieldDescription( rstFieldList.getString("FIELD_DESCRIPTION") );
            dataViewFieldDTO.setFieldSQLCash( rstFieldList.getString("FIELD_SQL_CASH") );
         //   dataViewFieldDTO.setFieldDisplayType(1);
            //dataViewFieldDTO.setFieldUnique(rstFieldList.getInt("FIELD_UNIQUE"));
            //vecDisplayedFieldList.addElement (dataViewFieldDTO );
            //dataViewFieldDTO.setParentDataViewVersion();
            //rstFieldList.getInt("FIELD_TYPE_OBJECT_ID"),
            //rstFieldList.getInt("FIELD_RF_OBJECT"),
            vecFieldList.addElement (dataViewFieldDTO);
        
          }          
        }
        rstFieldList.close();
        stmtFieldList.close();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      //return vecFieldList ;

      detailedDataViewDTO.setDataViewFields (vecFieldList) ;
      hMapDetailedDataViews.put(new Integer (detailedDataViewDTO.getDataViewID()),detailedDataViewDTO) ;
      queryDTO.setDetailedDataViews(hMapDetailedDataViews);
      //queryDTO.setDisplayedFields(vecDisplayedFieldList);
      //queryDTO.setConstantsFields(vecConstantsFieldList);
      //queryDTO.setFunctionsFields(vecFunctionsFieldList);

      return queryDTO ;
    }

    // ----------------------------------------------------------------------
    /* this method is used in scenario 5 (load query) */
    public static Vector getTermsList(Connection argCon, int argDataViewID/*,
                          Vector vecConstantsFieldList,
                          Vector vecDisplayedFieldList*/)
    {

      DEBUG_FLAG = false ;
      Vector vecTermsList = new Vector() ;
      ResultSet rstTermsList ;
      PreparedStatement stmtTermsList ;
      String strTermsListQuery = null ;
      try
      {
        strTermsListQuery = new String ("select * from VW_ADH_TERM where DATA_VIEW_ID = ? ");

      //  debug(strTermsListQuery);
//        debug("argDataViewID= " + argDataViewID);
        
        stmtTermsList = argCon.prepareStatement(strTermsListQuery) ;
        stmtTermsList.setInt(1, argDataViewID);
        rstTermsList = stmtTermsList.executeQuery();
        int termTypeID ;
        int termID ;
        //Utility.logger.debug ("  getting terms for DataViewID " + argDataViewID ) ; 
        while(rstTermsList.next())
        {
          termTypeID = rstTermsList.getInt("TERM_TYPE_ID");
          termID = rstTermsList.getInt("TERM_ID") ;

  //        Utility.logger.debug ("termID " + termID ) ;
          
          // FieldToFieldTermDTO fieldToFieldTermDTO ;
          if (termTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE)
          {
          //debug("FieldToFieldTermDTO");
          
            String strFieldToFieldListQuery = new String (
                " select " +
                " VW_ADH_TERM.TERM_ID, " +
                " VW_ADH_TERM.TERM_SQL, " +
                " VW_ADH_TERM.DATA_VIEW_ID, " +
                " VW_ADH_TERM.DATA_VIEW_ISSUE, " +
                " VW_ADH_TERM.DATA_VIEW_VERSION, " +
                " VW_ADH_TERM.OPERAND1_TABLE_ID, " +
                " VW_ADH_TERM.OPERAND1_TABLE_NAME, " +
                " VW_ADH_TERM.OPERAND1_OBJECT_ID, " +
                " VW_ADH_FIELD1.FIELD_NAME as OPERAND1_OBJECT_NAME, " +
                " VW_ADH_FIELD1.FIELD_TYPE_ID as OPERAND1_OBJECT_TYPE, " +
                " VW_ADH_FIELD1.FIELD_SQL_CASH as OPERAND1_OBJECT_SQL_CASH, " +
                " VW_ADH_TERM.OPERAND2_TABLE_ID, " +
                " VW_ADH_TERM.OPERAND2_TABLE_NAME, " +
                " VW_ADH_TERM.OPERAND2_OBJECT_ID, " +
                " VW_ADH_FIELD2.FIELD_NAME as OPERAND2_OBJECT_NAME, " +
                " VW_ADH_FIELD2.FIELD_TYPE_ID as OPERAND2_OBJECT_TYPE, " +
                " VW_ADH_FIELD2.FIELD_SQL_CASH as OPERAND2_OBJECT_SQL_CASH, " +
                " VW_ADH_TERM.OPERATOR_ID, " +
                " VW_ADH_TERM.TERM_OPERATOR_NAME, " +
                " VW_ADH_TERM.TERM_OPERATOR_SQL, " +
                " VW_ADH_TERM.TERM_TYPE_ID, " +
                " VW_ADH_TERM.TERM_TYPE_NAME, " +
                " VW_ADH_TERM.TERM_SQL " +
                "from VW_ADH_TERM, VW_ADH_FIELD VW_ADH_FIELD1, VW_ADH_FIELD VW_ADH_FIELD2 " +
                "where VW_ADH_TERM.DATA_VIEW_ID = ? and " +
                " VW_ADH_TERM.TERM_ID = ? and " +
                " VW_ADH_TERM.OPERAND1_OBJECT_ID = VW_ADH_FIELD1.FIELD_ID and " +
                " VW_ADH_TERM.OPERAND2_OBJECT_ID = VW_ADH_FIELD2.FIELD_ID" ) ;


//            Utility.logger.debug ("  fieldTofieldQuery --->\n" + strFieldToFieldListQuery + "\n") ;

            PreparedStatement stmtFieldToFieldList = argCon.prepareStatement(strFieldToFieldListQuery) ;
            stmtFieldToFieldList.setInt(1, argDataViewID);
            stmtFieldToFieldList.setInt(2, termID);
            ResultSet rstFieldToFieldList = stmtFieldToFieldList.executeQuery();

            //System.out.println ("  getting Field to Field for DataViewID " + argDataViewID ) ;

            //System.out.println ("  term id  " + termID ) ;
            
            //System.out.println(strFieldToFieldListQuery);
            
            FieldToFieldTermDTO fieldToFieldTermDTO ;

            while(rstFieldToFieldList.next())
            {
              //termTypeID = rstTermsList.getInt("TERM_TYPE_ID");
//                 Utility.logger.debug ("  found termID " + termTypeID ) ;

                fieldToFieldTermDTO = new FieldToFieldTermDTO () ;
                fieldToFieldTermDTO.setTermID(termID);
                fieldToFieldTermDTO.setTermSQLCache ( rstFieldToFieldList.getString ( "TERM_SQL" ) );
               
               //System.out.println("\n%%%%%%%%%%%%%"); 
                //System.out.println("term sql = "+ fieldToFieldTermDTO.getTermSQLCache() + "term id="+termID);
                
                fieldToFieldTermDTO.setDataViewID(rstFieldToFieldList.getInt ( "DATA_VIEW_ID" ));
                fieldToFieldTermDTO.set1stOperandFieldID(rstFieldToFieldList.getInt ( "OPERAND1_OBJECT_ID" ) );
                fieldToFieldTermDTO.set1stOperandFieldName(rstFieldToFieldList.getString (  "OPERAND1_OBJECT_NAME" ) );
                fieldToFieldTermDTO.set1stOperandFieldType(rstFieldToFieldList.getInt ( "OPERAND1_OBJECT_TYPE" ) );
                fieldToFieldTermDTO.set1stOperandFieldSQLCache(rstFieldToFieldList.getString ("OPERAND1_OBJECT_SQL_CASH"));

                /*if (fieldToFieldTermDTO.get1stOperandFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
                {
                  ResultSet rstDataViewList ;
                  PreparedStatement stmtDataViewList ;
                  String strDataViewQuery = 
                      "select VW_ADH_FIELD.FIELD_RF_OBJECT, VW_ADH_DATA_VIEW.DATA_VIEW_NAME " +
                      "from VW_ADH_FIELD, VW_ADH_DATA_VIEW " +
                      "where  VW_ADH_FIELD.FIELD_ID = ? and " +
                      "     VW_ADH_FIELD.FIELD_RF_OBJECT = VW_ADH_DATA_VIEW.DATA_VIEW_ID " ;

                  stmtDataViewList = argCon.prepareStatement(strDataViewQuery) ;
                  stmtDataViewList.setInt(1, fieldToFieldTermDTO.get1stOperandFieldID());
                  rstDataViewList = stmtDataViewList.executeQuery();
                  while(rstDataViewList.next())
                  {
                    fieldToFieldTermDTO.set1stOperandFieldParentDataViewID(rstDataViewList.getInt ("FIELD_RF_OBJECT"));
                    fieldToFieldTermDTO.set1stOperandFieldParentDataViewName(rstDataViewList.getString ("DATA_VIEW_NAME"));
                    Utility.logger.debug ( "  -->get1stOperandFieldParentDataViewID()" + fieldToFieldTermDTO.get1stOperandFieldParentDataViewID() );
                    Utility.logger.debug ( "  -->get1stOperandFieldParentDataViewName()" + fieldToFieldTermDTO.get1stOperandFieldParentDataViewName() );
                  }
                }
                else */ 
                
                if (fieldToFieldTermDTO.get1stOperandFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
                fieldToFieldTermDTO.set1stOperandFieldSQLCache(rstFieldToFieldList.getString ( "OPERAND1_OBJECT_SQL_CASH" ) );
                //Utility.logger.debug ("set---> _" + fieldToFieldTermDTO.get1stOperandFieldName()  + "_" ) ;
                fieldToFieldTermDTO.set2ndOperandFieldID(rstFieldToFieldList.getInt ( "OPERAND2_OBJECT_ID" ) );
                fieldToFieldTermDTO.set2ndOperandFieldName(rstFieldToFieldList.getString ( "OPERAND2_OBJECT_NAME" ) );
                fieldToFieldTermDTO.set2ndOperandFieldType(rstFieldToFieldList.getInt ( "OPERAND2_OBJECT_TYPE" ) );
                fieldToFieldTermDTO.set2ndOperandFieldSQLCache(rstFieldToFieldList.getString ("OPERAND2_OBJECT_SQL_CASH"));

                /*if (fieldToFieldTermDTO.get2ndOperandFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
                {
                  ResultSet rstDataViewList ;
                  PreparedStatement stmtDataViewList ;
                  String strDataViewQuery = 
                      "select VW_ADH_FIELD.FIELD_RF_OBJECT, VW_ADH_DATA_VIEW.DATA_VIEW_NAME " +
                      "from VW_ADH_FIELD, VW_ADH_DATA_VIEW " +
                      "where  VW_ADH_FIELD.FIELD_ID = ? and " +
                      "     VW_ADH_FIELD.FIELD_RF_OBJECT = VW_ADH_DATA_VIEW.DATA_VIEW_ID " ;

                  stmtDataViewList = argCon.prepareStatement(strDataViewQuery) ;
                  stmtDataViewList.setInt(1, fieldToFieldTermDTO.get2ndOperandFieldID());
                  rstDataViewList = stmtDataViewList.executeQuery();
                  while(rstDataViewList.next())
                  {
                    fieldToFieldTermDTO.set2ndOperandFieldParentDataViewID(rstDataViewList.getInt ("FIELD_RF_OBJECT"));
                    fieldToFieldTermDTO.set2ndOperandFieldParentDataViewName(rstDataViewList.getString ("DATA_VIEW_NAME"));
                  }
                }
                else */
                if (fieldToFieldTermDTO.get2ndOperandFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD )
                  fieldToFieldTermDTO.set2ndOperandFieldSQLCache(rstFieldToFieldList.getString ( "OPERAND2_OBJECT_SQL_CASH" ) );
                //Utility.logger.debug ("set---> _" + fieldToFieldTermDTO.get2ndOperandFieldName()  + "_" ) ;

                fieldToFieldTermDTO.setOperatorID(rstFieldToFieldList.getInt ( "OPERATOR_ID" ));
                //System.out.println("operator id="+ fieldToFieldTermDTO.getOperatorID());
                
                fieldToFieldTermDTO.setOperatorSQL(rstFieldToFieldList.getString ( "TERM_OPERATOR_SQL" ));
                //System.out.println("operator sql = " + fieldToFieldTermDTO.get2ndOperandFieldSQLCache());
                //Utility.logger.debug ("---->" + fieldToFieldTermDTO.get1stOperandFieldName() );
                /*
                fieldToFieldTermDTO.setDataViewName();

                rstTermsList.getInt ( "DATA_VIEW_ISSUE" )
                rstTermsList.getInt ( "DATA_VIEW_VERSION" )
                rstTermsList.getInt ( "OPERAND1_TABLE_ID" )
                rstTermsList.getString ( "OPERAND1_TABLE_NAME" )
                rstTermsList.getInt ( "OPERAND2_TABLE_ID" )
                rstTermsList.getString ( "OPERAND2_TABLE_NAME" )
                rstTermsList.getString ( "TERM_TYPE_NAME" )
                rstTermsList.getString ( "TERM_SQL" )
                */
                
                
                if (fieldToFieldTermDTO.getOperatorID()==9) //QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_BEGINS_WITH)
                {
                    //String temp = constantFieldDTO.getFieldSQLCash();
                    //                                 
                       StringBuffer tmpSQLCache = new StringBuffer ( fieldToFieldTermDTO.get2ndOperandFieldSQLCache() ) ;
                      int tmpSQLCachelength = tmpSQLCache.length();
                       tmpSQLCache.deleteCharAt(tmpSQLCachelength-1);
                       tmpSQLCache.deleteCharAt(0); 
                   
                    fieldToFieldTermDTO.set2ndOperandFieldSQLCache("\'"+tmpSQLCache+"%\'");
                    //   temp = tmpSQLCache.toString();
                   
                }
                else  if (fieldToFieldTermDTO.getOperatorID()==10) //QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_ENDS_WITH
                {
                    //String temp = constantFieldDTO.getFieldSQLCash();
                    //                                 
                       StringBuffer tmpSQLCache = new StringBuffer ( fieldToFieldTermDTO.get2ndOperandFieldSQLCache() ) ;
                      int tmpSQLCachelength = tmpSQLCache.length();
                       tmpSQLCache.deleteCharAt(tmpSQLCachelength-1);
                       tmpSQLCache.deleteCharAt(0); 
                   
                    fieldToFieldTermDTO.set2ndOperandFieldSQLCache("\'%"+tmpSQLCache+"\'");
                    //   temp = tmpSQLCache.toString();
                   
                }
                else if (fieldToFieldTermDTO.getOperatorID()==11) //QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_CONTAINS)
                {
                        //String temp = constantFieldDTO.getFieldSQLCash();
                        //                                 
                           StringBuffer tmpSQLCache = new StringBuffer ( fieldToFieldTermDTO.get2ndOperandFieldSQLCache() ) ;
                          int tmpSQLCachelength = tmpSQLCache.length();
                           tmpSQLCache.deleteCharAt(tmpSQLCachelength-1);
                           tmpSQLCache.deleteCharAt(0); 
                       
                        fieldToFieldTermDTO.set2ndOperandFieldSQLCache("\'%"+tmpSQLCache+"%\'");
                        //   temp = tmpSQLCache.toString();
                       
                    }
                    
                                
                                


                ConditionElementTypeDTO conditionElementTypeDTO = new ConditionElementTypeDTO () ;
                conditionElementTypeDTO.setConditionElementTypeID( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM );

                TermTypeDTO termTypeDTO = new TermTypeDTO () ;
                termTypeDTO.setTermTypeID(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE);
                fieldToFieldTermDTO.setTermType(termTypeDTO);
                fieldToFieldTermDTO.setConditionElementType(conditionElementTypeDTO);

                //Utility.logger.debug ("adding term: " + fieldToFieldTermDTO.get1stOperandFieldSQLCache() + fieldToFieldTermDTO.get2ndOperandFieldSQLCache() );
                vecTermsList.addElement(fieldToFieldTermDTO);
            }
          }
          else if (termTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE)
          {
          
            String strFieldToDataViewListQuery = new String (
                " select " +
                " VW_ADH_TERM.TERM_ID, " +
                " VW_ADH_TERM.TERM_SQL, " +
                " VW_ADH_TERM.DATA_VIEW_ID, " + 
                " VW_ADH_TERM.DATA_VIEW_ISSUE, " + 
                " VW_ADH_TERM.DATA_VIEW_VERSION, " + 
                " VW_ADH_TERM.OPERAND1_TABLE_ID, " + 
                " VW_ADH_TERM.OPERAND1_TABLE_NAME, " + 
                " VW_ADH_TERM.OPERAND1_OBJECT_ID, " + 
                " VW_ADH_FIELD1.FIELD_NAME as OPERAND1_OBJECT_NAME, " + 
                " VW_ADH_FIELD1.FIELD_TYPE_ID as OPERAND1_OBJECT_TYPE, " + 
                " VW_ADH_FIELD1.FIELD_SQL_CASH as OPERAND1_OBJECT_SQL_CASH, " + 
                " VW_ADH_TERM.OPERAND2_TABLE_ID, " + 
                " VW_ADH_TERM.OPERAND2_TABLE_NAME, " + 
                " VW_ADH_TERM.OPERAND2_OBJECT_ID, " + 
                " VW_ADH_DATA_VIEW.DATA_VIEW_NAME as OPERAND2_OBJECT_NAME, " +
                "VW_ADH_DATA_VIEW.DATA_VIEW_TYPE_ID AS OPERAND2_OBJECT_TYPE, " +
                " VW_ADH_TERM.OPERATOR_ID, " + 
                " VW_ADH_TERM.TERM_OPERATOR_NAME, " + 
                " VW_ADH_TERM.TERM_OPERATOR_SQL, " + 
                " VW_ADH_TERM.TERM_TYPE_ID, " + 
                " VW_ADH_TERM.TERM_TYPE_NAME, " + 
                " VW_ADH_TERM.TERM_SQL  " + 
                "from VW_ADH_TERM, VW_ADH_FIELD VW_ADH_FIELD1, VW_ADH_DATA_VIEW " + 
                "where VW_ADH_TERM.DATA_VIEW_ID = ? and " +
                " VW_ADH_TERM.TERM_ID = ? and " +
                " VW_ADH_TERM.OPERAND1_OBJECT_ID = VW_ADH_FIELD1.FIELD_ID and " + 
                " VW_ADH_TERM.OPERAND2_OBJECT_ID = VW_ADH_DATA_VIEW.DATA_VIEW_ID " ) ;


            PreparedStatement stmtFieldToDataViewList = argCon.prepareStatement(strFieldToDataViewListQuery) ;
            stmtFieldToDataViewList.setInt(1, argDataViewID);
            stmtFieldToDataViewList.setInt(2, termID);
            ResultSet rstFieldToDataViewList = stmtFieldToDataViewList.executeQuery();
            //Utility.logger.debug ("  getting Field to Dataview for DataViewID " + argDataViewID ) ; 
            FieldtoDataViewTermDTO fieldtoDataViewTermDTO ;
            while(rstFieldToDataViewList.next())
            {
              //termTypeID = rstTermsList.getInt("TERM_TYPE_ID");
              //Utility.logger.debug ("  found termID " + termTypeID ) ; 

              fieldtoDataViewTermDTO = new FieldtoDataViewTermDTO () ;

              fieldtoDataViewTermDTO.setTermID(termID);
              fieldtoDataViewTermDTO.setTermSQLCache ( rstFieldToDataViewList.getString ( "TERM_SQL" ) );

              //Utility.logger.debug ( "  -->field to data view Term: " + fieldtoDataViewTermDTO.getTermID() );
              //fieldtoDataViewTermDTO.setTermID(rstTermsList.getInt("TERM_ID"));

              fieldtoDataViewTermDTO.setDataViewID(rstFieldToDataViewList.getInt ( "DATA_VIEW_ID" ));
              fieldtoDataViewTermDTO.set1stOperandFieldID(rstFieldToDataViewList.getInt ( "OPERAND1_OBJECT_ID" ) );
              fieldtoDataViewTermDTO.set1stOperandFieldName(rstFieldToDataViewList.getString (  "OPERAND1_OBJECT_NAME" ) );
              fieldtoDataViewTermDTO.set1stOperandFieldSQLCache(rstFieldToDataViewList.getString (  "OPERAND1_OBJECT_SQL_CASH" ) );
              fieldtoDataViewTermDTO.set1stOperandFieldType(rstFieldToDataViewList.getInt ( "OPERAND1_OBJECT_TYPE" ) );
              fieldtoDataViewTermDTO.setRelatedDataViewID(rstFieldToDataViewList.getInt ( "OPERAND2_OBJECT_ID" ) );

              
              fieldtoDataViewTermDTO.setRelatedDataViewName(rstFieldToDataViewList.getString ( "OPERAND2_OBJECT_NAME" ) );
              fieldtoDataViewTermDTO.setRelatedDataViewTypeID(rstFieldToDataViewList.getInt ( "OPERAND2_OBJECT_TYPE" ) );
              

              //fieldtoDataViewTermDTO.setRelatedDataViewName(rstFieldToDataViewList.getString ( "OPERAND2_OBJECT_NAME" ) );
              //fieldtoDataViewTermDTO.setRelatedDataViewName( new Long ( rstFieldToDataViewList.getInt ( "OPERAND2_OBJECT_ID" ) ).toString() ) ;

              QueryBuilderEngine subQueryBuilderEngine = new QueryBuilderEngine () ;
              QueryDTO subQueryDTO ;
              Vector vec =new Vector();
              subQueryDTO = subQueryBuilderEngine.loadQueryDTO(argCon,fieldtoDataViewTermDTO.getRelatedDataViewID(),vec) ;
              fieldtoDataViewTermDTO.setRelatedDataViewSQL( subQueryBuilderEngine.constructQuerySQL( subQueryDTO ) ); 

              //fieldtoDataViewTermDTO.setRelatedDataViewSQL( new Long ( rstFieldToDataViewList.getInt ( "OPERAND2_OBJECT_ID" ) ).toString() ) ;
              //fieldtoDataViewTermDTO.setRelatedDataViewTypeID(rstTermsList.getInt ( "OPERAND2_OBJECT_TYPE" ) );
              //Utility.logger.debug ( "  -->1stOperandFieldName DataViewName: " + fieldtoDataViewTermDTO.get1stOperandFieldName() + 
              //    fieldtoDataViewTermDTO.getRelatedDataViewName() ) ;

              fieldtoDataViewTermDTO.setOperatorID(rstFieldToDataViewList.getInt ( "OPERATOR_ID" ));
              fieldtoDataViewTermDTO.setOperatorSQL(rstFieldToDataViewList.getString ( "TERM_OPERATOR_SQL" ));
              //Utility.logger.debug ("---->" + fieldToFieldTermDTO.get1stOperandFieldName() );

              ConditionElementTypeDTO conditionElementTypeDTO = new ConditionElementTypeDTO () ;
              conditionElementTypeDTO.setConditionElementTypeID( QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM );

              TermTypeDTO termTypeDTO = new TermTypeDTO () ;
              termTypeDTO.setTermTypeID(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE);
              fieldtoDataViewTermDTO.setTermType(termTypeDTO);
              fieldtoDataViewTermDTO.setConditionElementType(conditionElementTypeDTO);
            
              vecTermsList.addElement(fieldtoDataViewTermDTO);
            }
          }
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }

      DEBUG_FLAG = false ;
      return vecTermsList ;
    }

    // ----------------------------------------------------------------------
    /* this method is used in scenario 5 (load query) */
    public static Vector getWheresList(Connection argCon, int argDataViewID, Vector vecTerms)
    {
      Vector vecWheresList = new Vector() ;
      ResultSet rstWheresList ;
      PreparedStatement stmtWheresList ;
      String strWheresListQuery = null ;
      try
      {
        strWheresListQuery = new String ("select * from VW_ADH_WHERE where DATA_VIEW_ID = ? order by WHERE_PLACE ");
        stmtWheresList = argCon.prepareStatement(strWheresListQuery) ;
        stmtWheresList.setInt(1, argDataViewID);
        rstWheresList = stmtWheresList.executeQuery();
        int conditionElementTypeID  ;
        while(rstWheresList.next())
        {
          conditionElementTypeID = rstWheresList.getInt("ELEMENT_TYPE_ID");
          ConditionElementTypeDTO conditionElementTypeDTO = new ConditionElementTypeDTO () ;
          conditionElementTypeDTO.setConditionElementTypeID(conditionElementTypeID);

          if (conditionElementTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM)
          {
            int termID = rstWheresList.getInt ( "ELEMENT_ID" );
            //Utility.logger.debug ("  trying to link term ID: " + termID + 
            //      " to where clause. vecTerms.size() " + vecTerms.size() );

            // search in the list of terms for the term that has the desired id
            for (int i = 0 ; i < vecTerms.size() ; i++)
            {
              Term term = (Term) vecTerms.elementAt(i);
              //Utility.logger.debug ("  checking the "+i+"th term. TermID " + term.getTermID());
              if ( term.getTermID() == termID)
              {
                vecWheresList.addElement(term);
                //term.setConditionElementType(conditionElementTypeDTO);
                //Utility.logger.debug ("  found. Added to wheresList ");
              }
            }
          }
          else if (conditionElementTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL)
          {
            RelationSymbolDTO symbol = new RelationSymbolDTO () ;
            symbol.setRelationSymbolID(rstWheresList.getInt ( "ELEMENT_ID" ));
            symbol.setConditionElementType(conditionElementTypeDTO);

            //Utility.logger.debug ("found a symbol with ID " + symbol.getRelationSymbolID() );


            String strSymbolsListQuery = new String (
              "select * " +
              "from   VW_ADH_WHERE, VW_ADH_SYMBOL_TYPE " +
              "where  VW_ADH_WHERE.DATA_VIEW_ID = ? and " +
              "       VW_ADH_WHERE.ELEMENT_TYPE_ID = ? and " +
              "       VW_ADH_SYMBOL_TYPE.SYMBOL_TYPE_ID = ? " );

            //Utility.logger.debug ("  strSymbolsListQuery " + strSymbolsListQuery );

            PreparedStatement stmtSymbolsList = argCon.prepareStatement(strSymbolsListQuery) ;
            stmtSymbolsList.setInt (1, argDataViewID);
            stmtSymbolsList.setInt (2, QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL);
            stmtSymbolsList.setInt (3, symbol.getRelationSymbolID());
            ResultSet rstSymbolsList = stmtSymbolsList.executeQuery();
            while(rstSymbolsList.next())
            {
              symbol.setRelationSymbolID( rstSymbolsList.getInt ( "SYMBOL_TYPE_ID" ) );
              symbol.setRelationSymbolSQL( rstSymbolsList.getString ( "SYMBOL_TYPE_SQL" ) );
              symbol.setRelationSymboName( rstSymbolsList.getString ( "SYMBOL_TYPE_NAME" ) );
              //Utility.logger.debug ("  symbol.getRelationSymboName() " + symbol.getRelationSymboName() );
              //symbol.setRelationSymbolDescription();
            }
            /*
            rstWheresList.getInt ( "WHERE_ID" )
            rstWheresList.getInt ( "DATA_VIEW_ID" )
            rstWheresList.getInt ( "DATA_VIEW_ISSUE" )
            rstWheresList.getInt ( "DATA_VIEW_VERSION" )
            rstWheresList.getString ( "ELEMENT_TYPE_NAME" )
            rstWheresList.getInt ( "WHERE_PLACE" )

            symbol.setRelationSymbolDescription();
            symbol.setRelationSymbolID();
            */
            vecWheresList.addElement(symbol);
          }
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecWheresList ;
    }


    // ----------------------------------------------------------------------
    /* this method is used in scenario 5 (load query) */
    public static Vector getGroupByList (Connection argCon, int argDataViewID, Vector vecFunctions)
    {
      Vector vecGroupByList = null ;
      ResultSet rstGroupByList ;
      PreparedStatement stmtGroupByList ;
      String strGroupByListQuery = null ;
      int fieldTypeID ;
      
      try
      {
        vecGroupByList = new Vector() ;
        
        //strGroupByListQuery = new String ("select * from VW_ADH_GROUP_BY where DATA_VIEW_ID = ? order by WHERE_PLACE ");
        strGroupByListQuery = new String (
          "select vw_adh_field.* " +
          "from  vw_adh_group_by, vw_adh_field " +
          "where vw_adh_group_by.GROUP_FIELD_ID =  vw_adh_field.FIELD_ID and " +
          "      vw_adh_group_by.data_view_id =  ? " +
          "order by vw_adh_group_by.GROUP_PLACE "
          );
        stmtGroupByList = argCon.prepareStatement(strGroupByListQuery) ;
        stmtGroupByList.setInt(1, argDataViewID);
        rstGroupByList = stmtGroupByList.executeQuery();
        
        while(rstGroupByList.next())
        {
          FieldTypeModel fieldTypeModel = new FieldTypeModel () ;
          //FieldDisplayTypeModel fieldDisplayTypeModel = new FieldDisplayTypeModel () ;
          fieldTypeID = rstGroupByList.getInt("FIELD_TYPE_ID");
          fieldTypeModel.setFieldTypeID (fieldTypeID) ;
          if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
          {
            DataViewFieldDTO dataViewFieldDTO = new DataViewFieldDTO () ;
            //fieldDisplayTypeModel.setFieldDisplayTypeID (rstGroupByList.getInt("FIELD_DISPLAY_TYPE_ID"));
            //dataViewFieldDTO.setFieldDisplayType (fieldDisplayTypeModel);
            dataViewFieldDTO.setFieldType (fieldTypeModel);
            dataViewFieldDTO.setFieldID(rstGroupByList.getInt("FIELD_ID"));
            debug("field id (7) ="+ dataViewFieldDTO.getFieldID());
            dataViewFieldDTO.setFieldName(rstGroupByList.getString("FIELD_NAME"));
            dataViewFieldDTO.setFieldDescription(rstGroupByList.getString("FIELD_DESCRIPTION"));
            dataViewFieldDTO.setDataViewID(argDataViewID);
            dataViewFieldDTO.setFieldSQLCash(rstGroupByList.getString("FIELD_SQL_CASH"));
            dataViewFieldDTO.setParentDataViewID(rstGroupByList.getInt("FIELD_RF_OBJECT"));
            //dataViewFieldDTO.setParentDataViewName();
            //dataViewFieldDTO.setParentDataViewVersion();
            
            vecGroupByList.addElement (dataViewFieldDTO);
          }
          else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_RF_FIELD )
          {
            //functionFieldDTO.setFieldName( (String) paramHashMap.get (paramName) );
            /*int nParamID = rstGroupByList.getInt("FIELD_ID");
            Utility.logger.debug ("  got function id: " + nParamID ) ;
            FunctionFieldDTO functionFieldDTO = (FunctionFieldDTO) ((FunctionFieldDTO) vecFunctions.elementAt(nParamID)).clone() ;
            Utility.logger.debug ("  functionFieldDTO.getFieldSQLCash(): " + functionFieldDTO.getFieldSQLCash() ) ;
            vecGroupByList.addElement( functionFieldDTO );*/

            FunctionFieldDTO functionFieldDTO = null ;
            int functionShadowID = rstGroupByList.getInt("FIELD_ID") ;
            //Utility.logger.debug ("  got shadow function id: " + functionShadowID ) ;

            String strFieldQuery = new String (
              "select vw_adh_field.* " +
              "from  vw_adh_field " +
              "where vw_adh_field.FIELD_ID = ? " 
              );
            PreparedStatement stmtFieldList = argCon.prepareStatement(strFieldQuery) ;
            stmtFieldList.setInt(1, functionShadowID);
            ResultSet rstFieldList = stmtFieldList.executeQuery();

            int functionID = 0 ;
            while(rstFieldList.next())
            {
              functionID = rstFieldList.getInt("FIELD_RF_OBJECT") ;
            }

            for (int i=0 ; i<vecFunctions.size(); i++)
            {
              if ( ( (FunctionFieldDTO) vecFunctions.elementAt(i) ).getFieldID() == functionID )
              {
                functionFieldDTO = (FunctionFieldDTO) ((FunctionFieldDTO) vecFunctions.elementAt(i)).clone() ;
                vecGroupByList.addElement( functionFieldDTO );
              }
            }
          }
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecGroupByList ;
    }

    // ----------------------------------------------------------------------
    /* this method is used in scenario 5 (load query) */
    public static Vector getOrderByList (Connection argCon, int argDataViewID, Vector vecFunctions)
    {
      Vector vecOrderByList = null ;
      ResultSet rstOrderByList ;
      PreparedStatement stmtOrderByList ;
      String strOrderByListQuery = null ;
      int fieldTypeID ;

      try
      {
        vecOrderByList = new Vector() ;
        strOrderByListQuery = new String (
          "select *  " +
          "from  vw_adh_order_by, vw_adh_field " +  
          "where vw_adh_order_by.FIELD_ID =  vw_adh_field.FIELD_ID and " +  
          "      vw_adh_order_by.data_view_id =  ?  " +
          "order by vw_adh_order_by.ORDER_PLACE " 
          );
        stmtOrderByList = argCon.prepareStatement(strOrderByListQuery) ;
        stmtOrderByList.setInt(1, argDataViewID);
        rstOrderByList = stmtOrderByList.executeQuery();
        
        while(rstOrderByList.next())
        {
          FieldTypeModel fieldTypeModel = new FieldTypeModel () ;
          //FieldDisplayTypeModel fieldDisplayTypeModel = new FieldDisplayTypeModel () ;
          fieldTypeID = rstOrderByList.getInt("FIELD_TYPE_ID");
          fieldTypeModel.setFieldTypeID (fieldTypeID) ;
          if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD )
          {
            DataViewFieldDTO dataViewFieldDTO = new DataViewFieldDTO () ;
            //fieldDisplayTypeModel.setFieldDisplayTypeID (rstOrderByList.getInt("FIELD_DISPLAY_TYPE_ID"));
            //dataViewFieldDTO.setFieldDisplayType (fieldDisplayTypeModel);
            dataViewFieldDTO.setFieldType (fieldTypeModel);
            dataViewFieldDTO.setFieldID (rstOrderByList.getInt("FIELD_ID"));
            debug("field id (8) ="+ dataViewFieldDTO.getFieldID());
            dataViewFieldDTO.setFieldName (rstOrderByList.getString("FIELD_NAME"));
            dataViewFieldDTO.setFieldDescription (rstOrderByList.getString("FIELD_DESCRIPTION"));
            dataViewFieldDTO.setDataViewID (argDataViewID);
            dataViewFieldDTO.setFieldSQLCash (rstOrderByList.getString("FIELD_SQL_CASH"));
            dataViewFieldDTO.setParentDataViewID (rstOrderByList.getInt("FIELD_RF_OBJECT"));
            OrderByDTO orderByDTO = new OrderByDTO () ;
            orderByDTO.setFieldDTO (dataViewFieldDTO) ;
            //orderByDTO.setOrderType (rstOrderByList.getInt("ORDER_BY_TYPE_ID")) ;
            //getOrderByType (  ) ;
            orderByDTO.setOrderType (switchOrderByTypeToString ( argCon, rstOrderByList.getInt("ORDER_BY_TYPE_ID") ) ) ;
            vecOrderByList.addElement (orderByDTO) ;
          }
          else if ( fieldTypeID == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_RF_FIELD )
          {
            //functionFieldDTO.setFieldName( (String) paramHashMap.get (paramName) );
            //int nParamID = rstGroupByList.getInt("FIELD_ID");
            //Utility.logger.debug ("  got function id: " + nParamID ) ;
            //FunctionFieldDTO functionFieldDTO = (FunctionFieldDTO) ((FunctionFieldDTO) vecFunctions.elementAt(nParamID)).clone() ;
            //Utility.logger.debug ("  functionFieldDTO.getFieldSQLCash(): " + functionFieldDTO.getFieldSQLCash() ) ;
            //vecGroupByList.addElement( functionFieldDTO );

            FunctionFieldDTO functionFieldDTO = null ;
            int functionShadowID = rstOrderByList.getInt("FIELD_ID") ;
            //Utility.logger.debug ("  got shadow function id: " + functionShadowID ) ;

            String strFieldQuery = new String (
              "select vw_adh_field.* " +
              "from  vw_adh_field " +
              "where vw_adh_field.FIELD_ID = ? " 
              );
            PreparedStatement stmtFieldList = argCon.prepareStatement(strFieldQuery) ;
            stmtFieldList.setInt(1, functionShadowID);
            ResultSet rstFieldList = stmtFieldList.executeQuery();

            int functionID = 0 ;
            String strOrderByTypeName = null ;
            while (rstFieldList.next())
            {
              functionID = rstFieldList.getInt("FIELD_RF_OBJECT") ;
              strOrderByTypeName = switchOrderByTypeToString ( argCon, rstOrderByList.getInt("ORDER_BY_TYPE_ID") ) ;
            }

            for (int i=0 ; i<vecFunctions.size(); i++)
            {
              if ( ( (FunctionFieldDTO) vecFunctions.elementAt(i) ).getFieldID() == functionID )
              {
                functionFieldDTO = (FunctionFieldDTO) ((FunctionFieldDTO) vecFunctions.elementAt(i)).clone() ;
                OrderByDTO orderByDTO = new OrderByDTO () ;
                orderByDTO.setFieldDTO (functionFieldDTO) ;
                //orderByDTO.setOrderType (rstOrderByList.getInt("ORDER_BY_TYPE_ID")) ;
                orderByDTO.setOrderType ( strOrderByTypeName ) ;
                vecOrderByList.addElement (orderByDTO) ;
              }
            }
          }
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecOrderByList ;
    }

    // ----------------------------------------------------------------------
    /* this method is used in scenario 5 (load query) */
    public static Long switchOrderByTypeToID (Connection argCon, String strOrderByType)
    {
      ResultSet rstOrderByType ;
      PreparedStatement stmtOrderByType ;
      String strOrderByTypeQuery = null ;
      Long lOrderByTypeID = null ;

      try
      {
        //Utility.logger.debug ( "  got strOrderByType: " + strOrderByType) ;
        strOrderByTypeQuery = new String (
          "select *  " +
          "from  vw_adh_order_by_type " +  
          "where order_by_type_name = ? "  
          );
        stmtOrderByType = argCon.prepareStatement(strOrderByTypeQuery) ;
        stmtOrderByType.setString(1, strOrderByType);
        rstOrderByType = stmtOrderByType.executeQuery();
        
        while(rstOrderByType.next())
        {
          //Utility.logger.debug ( "  got ORDER_BY_TYPE_ID: " + rstOrderByType.getInt("ORDER_BY_TYPE_ID")) ;
          lOrderByTypeID = new Long (rstOrderByType.getInt("ORDER_BY_TYPE_ID") );
        }

      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return lOrderByTypeID ;
    }

    // ----------------------------------------------------------------------
    /* this method is used in scenario 5 (load query) */
    public static String switchOrderByTypeToString (Connection argCon, int nOrderByTypeID)
    {
      ResultSet rstOrderByType ;
      PreparedStatement stmtOrderByType ;
      String strOrderByTypeQuery = null ;
      String strOrderByTypeName = null ;

      try
      {
        strOrderByTypeQuery = new String (
          "select *  " +
          "from  vw_adh_order_by_type " +  
          "where order_by_type_id = ? "  
          );
        stmtOrderByType = argCon.prepareStatement(strOrderByTypeQuery) ;
        stmtOrderByType.setInt(1, nOrderByTypeID);
        rstOrderByType = stmtOrderByType.executeQuery();
        
        while(rstOrderByType.next())
        {
          strOrderByTypeName = new String (rstOrderByType.getString ("ORDER_BY_TYPE_NAME"));
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return strOrderByTypeName ;
    }

    // ----------------------------------------------------------------------
    public static boolean dataViewNameExists (Connection argCon,
                    String strDataViewName )
    {
      Vector vecDataViewsList = new Vector() ;
      PreparedStatement stmtDataViewsList = null;
      boolean bDataViewExisting = false ;
      try
      {
        String strDataViewsListQuery = 
            " select * " + 
            " from VW_ADH_DATA_VIEW " +
            " where upper (DATA_VIEW_NAME) = upper (?)" ;
        stmtDataViewsList = argCon.prepareStatement (strDataViewsListQuery) ;
        stmtDataViewsList.setString (1, strDataViewName);
        ResultSet rstDataViewsList = stmtDataViewsList.executeQuery();

        while(rstDataViewsList.next())
        {
           //rstDataViewsList.getString("DATA_VIEW_NAME");
           bDataViewExisting = true ;
        }

        rstDataViewsList.close();
        stmtDataViewsList.close();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return bDataViewExisting ;
    }


    // ----------------------------------------------------------------------
    /* this method is used in scenario 5 (load query) */
    public static Vector getHavingList(Connection argCon, int argDataViewID, Vector vecTerms)
    {
      Vector vecHavingList = new Vector() ;
      ResultSet rstHavingList ;
      PreparedStatement stmtHavingList ;
      String strHavingListQuery = null ;
      try
      {
        strHavingListQuery = new String (
            "select * from VW_ADH_HAVING where DATA_VIEW_ID = ? order by HAVING_PLACE ");
        stmtHavingList = argCon.prepareStatement (strHavingListQuery) ;
        stmtHavingList.setInt(1, argDataViewID);
        rstHavingList = stmtHavingList.executeQuery();
        int conditionElementTypeID  ;
        while(rstHavingList.next())
        {
          conditionElementTypeID = rstHavingList.getInt("ELEMENT_TYPE_ID");
          ConditionElementTypeDTO conditionElementTypeDTO = new ConditionElementTypeDTO () ;
          conditionElementTypeDTO.setConditionElementTypeID(conditionElementTypeID);

          if (conditionElementTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM)
          {
            int termID = rstHavingList.getInt ( "ELEMENT_ID" );
            //Utility.logger.debug ("  trying to link term ID: " + termID + 
            //      " to where clause. vecTerms.size() " + vecTerms.size() );

            // search in the list of terms for the term that has the desired id
            for (int i = 0 ; i < vecTerms.size() ; i++)
            {
              Term term = (Term) vecTerms.elementAt(i);
              //Utility.logger.debug ("  checking the "+i+"th term. TermID " + term.getTermID());
              if ( term.getTermID() == termID)
              {
                vecHavingList.addElement(term);
                //term.setConditionElementType(conditionElementTypeDTO);
                //Utility.logger.debug ("  found. Added to HavingList ");
              }
            }
          }
          else if (conditionElementTypeID == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL)
          {
            RelationSymbolDTO symbol = new RelationSymbolDTO () ;
            symbol.setRelationSymbolID(rstHavingList.getInt ( "ELEMENT_ID" ));
            symbol.setConditionElementType(conditionElementTypeDTO);

            //Utility.logger.debug ("found a symbol with ID " + symbol.getRelationSymbolID() );


            String strSymbolsListQuery = new String (
              "select * " +
              "from   VW_ADH_HAVING, VW_ADH_SYMBOL_TYPE " +
              "where  VW_ADH_HAVING.DATA_VIEW_ID = ? and " +
              "       VW_ADH_HAVING.ELEMENT_TYPE_ID = ? and " +
              "       VW_ADH_SYMBOL_TYPE.SYMBOL_TYPE_ID = ? " );

            //Utility.logger.debug ("  strSymbolsListQuery " + strSymbolsListQuery );

            PreparedStatement stmtSymbolsList = argCon.prepareStatement(strSymbolsListQuery) ;
            stmtSymbolsList.setInt (1, argDataViewID);
            stmtSymbolsList.setInt (2, QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL);
            stmtSymbolsList.setInt (3, symbol.getRelationSymbolID());
            ResultSet rstSymbolsList = stmtSymbolsList.executeQuery();
            while(rstSymbolsList.next())
            {
              symbol.setRelationSymbolID( rstSymbolsList.getInt ( "SYMBOL_TYPE_ID" ) );
              symbol.setRelationSymbolSQL( rstSymbolsList.getString ( "SYMBOL_TYPE_SQL" ) );
              symbol.setRelationSymboName( rstSymbolsList.getString ( "SYMBOL_TYPE_NAME" ) );
              //Utility.logger.debug ("  symbol.getRelationSymboName() " + symbol.getRelationSymboName() );
              //symbol.setRelationSymbolDescription();
            }
            /*
            rstHavingList.getInt ( "WHERE_ID" )
            rstHavingList.getInt ( "DATA_VIEW_ID" )
            rstHavingList.getInt ( "DATA_VIEW_ISSUE" )
            rstHavingList.getInt ( "DATA_VIEW_VERSION" )
            rstHavingList.getString ( "ELEMENT_TYPE_NAME" )
            rstHavingList.getInt ( "WHERE_PLACE" )

            symbol.setRelationSymbolDescription();
            symbol.setRelationSymbolID();
            */
            vecHavingList.addElement(symbol);
          }
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecHavingList ;
    }

    // ----------------------------------------------------------------------
    /* this method is used in scenario 2 (initializeQueryBuilder) */
    /*public static Vector getDataViewFieldsListByDisplayType (
            Connection argCon, 
            int argDataViewID, 
            int argFieldDisplayType)
    {
      Vector vecFieldList = new Vector() ;
      ResultSet rstFieldList ;
      PreparedStatement stmtFieldList ;
      String strFieldListQuery = null ;
      try
      {
          strFieldListQuery = new String (
            "select * " +
            "from VW_ADH_FIELD " +
            "where DATA_VIEW_ID = ? and FIELD_DISPLAY_TYPE_ID = ?" +
            "order by DATA_VIEW_ID, FIELD_ID");

        stmtFieldList = argCon.prepareStatement(strFieldListQuery) ;
        //stmtFieldList.setString(1, (new Integer (argDataViewID)).toString());
        stmtFieldList.setInt(1, argDataViewID);
        stmtFieldList.setInt(2, argFieldDisplayType );

        rstFieldList = stmtFieldList.executeQuery();
        while(rstFieldList.next())
        {

         //System.out.print ( "field id ----> " + rstFieldList.getInt("FIELD_ID") ) ;
         //Utility.logger.debug ( " field name ----> " + rstFieldList.getString("FIELD_NAME") ) ;
         FieldModel newFieldModel = new FieldModel (

                  rstFieldList.getInt("DATA_VIEW_ID"),
                  rstFieldList.getInt("DATA_VIEW_ISSUE"),
                  rstFieldList.getInt("DATA_VIEW_VERSION"),
                  rstFieldList.getInt("FIELD_ID"),
                  rstFieldList.getString("FIELD_NAME"),
                  rstFieldList.getString("FIELD_DESCRIPTION"),
                  rstFieldList.getString("FIELD_SQL_CASH"),
                  rstFieldList.getInt("FIELD_DISPLAY_TYPE_ID"),
                  rstFieldList.getString("FIELD_DISPLAY_TYPE_NAME"),
                  rstFieldList.getInt("FIELD_TYPE_ID"),
                  rstFieldList.getString("FIELD_TYPE_NAME"),
                  rstFieldList.getInt("FIELD_TYPE_OBJECT_ID"),
                  rstFieldList.getInt("FIELD_RF_OBJECT")
                  //rstFieldList.getInt("FIELD_UNIQUE")
                  );

          vecFieldList.addElement (newFieldModel);
        }

        rstFieldList.close();
        stmtFieldList.close();

      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecFieldList ;
    }*/



    public static Vector getOrderByListSimplified (Connection argCon, int argDataViewID/*, Vector vecFunctions*/)
    {
      Vector vecOrderByList = null ;
      ResultSet rstOrderByList ;
      PreparedStatement stmtOrderByList ;
      String strOrderByListQuery = null ;
      int fieldTypeID ;
      int nOrderByTypeID ;

      try
      {
        vecOrderByList = new Vector() ;
        strOrderByListQuery = new String (
          "select *  " +
          "from  vw_adh_order_by, vw_adh_field " +  
          "where vw_adh_order_by.FIELD_ID =  vw_adh_field.FIELD_ID and " +  
          "      vw_adh_order_by.data_view_id =  ?  " +
          "order by vw_adh_order_by.ORDER_PLACE " 
          );
        stmtOrderByList = argCon.prepareStatement(strOrderByListQuery) ;
        stmtOrderByList.setInt(1, argDataViewID);
        rstOrderByList = stmtOrderByList.executeQuery();
        
        while(rstOrderByList.next())
        {
          ReportOrderByDTO reportOrderByDTO = new ReportOrderByDTO () ;

          reportOrderByDTO.setOrderByFieldID ( rstOrderByList.getInt("FIELD_ID") );
          reportOrderByDTO.setOrderByFieldName ( rstOrderByList.getString("FIELD_NAME") );
          nOrderByTypeID = rstOrderByList.getInt ("ORDER_BY_TYPE_ID") ;
          reportOrderByDTO.setOrderByType ( switchOrderByTypeToString ( argCon, nOrderByTypeID ) );
          vecOrderByList.addElement (reportOrderByDTO) ;
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecOrderByList ;
    }


    // ----------------------------------------------------------------------
    public static Long switchUniverseTypeToID (Connection argCon, String strType)
    {
      ResultSet rstType ;
      PreparedStatement stmtType ;
      String strTypeQuery = null ;
      Long lTypeID = null ;

      try
      {
        //Utility.logger.debug ( "  got strOrderByType: " + strOrderByType) ;
        strTypeQuery = new String (
          "select *  " +
          "from  vw_adh_universe " +  
          "where universe_name = ? "  
          );
        stmtType = argCon.prepareStatement(strTypeQuery) ;
        stmtType.setString(1, strType);
        rstType = stmtType.executeQuery();
        
        while(rstType.next())
        {
          //Utility.logger.debug ( "  got UNIVERSE_ID: " + rstType.getInt("UNIVERSE_ID") ) ;
          lTypeID = new Long (rstType.getInt("UNIVERSE_ID") );
        }

      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return lTypeID ;
    }



    // ----------------------------------------------------------------------
    public static String switchUniverseTypeToString (Connection argCon, int nTypeID)
    {
      ResultSet rstType ;
      PreparedStatement stmtType ;
      String strTypeQuery = null ;
      String strTypeName = null ;

      try
      {
        strTypeQuery = new String (
          "select *  " +
          "from  vw_adh_universe " +  
          "where universe_id = ? "  
          );
        stmtType = argCon.prepareStatement(strTypeQuery) ;
        stmtType.setInt(1, nTypeID);
        rstType = stmtType.executeQuery();
        
        while(rstType.next())
        {
          strTypeName = new String (rstType.getString ("UNIVERSE_NAME"));
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return strTypeName ;
    }



    // ----------------------------------------------------------------------
    /* this method is used in scenario 1 (initializeWizard) */
    public static Vector getDataViewVersionsList (Connection argCon,
        int argDataViewIssueID/*, 
        int argDataViewStatusID,
        int argDataViewUniverseID*/
        )
    {
      Vector vecDataViewsList = new Vector() ;
      PreparedStatement stmtDataViewsList = null;
      try
      {

        String strDataViewsListQuery = 
            " select * " + 
            " from VW_ADH_DATA_VIEW " +
            " where DATA_VIEW_ISSUE = ? " +
            " order by vw_adh_data_view.DATA_VIEW_VERSION desc " ;
        stmtDataViewsList = argCon.prepareStatement (strDataViewsListQuery) ;
        stmtDataViewsList.setInt (1, argDataViewIssueID);
        ResultSet rstDataViewsList = stmtDataViewsList.executeQuery();

        while(rstDataViewsList.next())
        {
          DataViewModel newDataViewModel = new DataViewModel ();
          newDataViewModel.setDataViewID (rstDataViewsList.getInt("DATA_VIEW_ID"));
          newDataViewModel.setDataViewVersion (rstDataViewsList.getInt("DATA_VIEW_VERSION"));
          newDataViewModel.setDataViewStatusID (rstDataViewsList.getInt("DATA_VIEW_STATUS_ID"));
          vecDataViewsList.addElement (newDataViewModel);
        }
        rstDataViewsList.close();
        stmtDataViewsList.close();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return vecDataViewsList ;
    }


}


package com.mobinil.sds.core.system.gn.querybuilder.dao;

/**
 * Query Builder DAO - Data Access Object holds a set of static methods used to retrieve data from the database. These
 *  operations needed to construct the query builder and gets its necessary data.
 * For example:
 * <pre>
 *      QueryBuilderDAO.getFieldsTypesList();
 * </pre>
 *
 * @version	1.01 Feb 2004
 * @author  Ahmed Mohamed Abd Elmonem
 * @see     
 *
 * SDS
 * MobiNil
 */ 


import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.system.gn.querybuilder.dto.*;
import com.mobinil.sds.core.system.gn.querybuilder.model.*;
import com.mobinil.sds.web.interfaces.gn.querybuilder.*;
import com.mobinil.sds.core.utilities.Utility;

public class QueryBuilderDAO 
{

/**
 * Get all the possible types of a field. 
 * @param  argCon Connection
 * @throws SQLException if the database operation failed
 * @return Collection of the field types available. The types include, constant field, database field, parameter field, etc..
 * @see 
 */
  public static Vector getFieldTypesList(Connection argCon) throws SQLException
  {
    FieldTypeModel modFieldType                              = null;
    Vector        colFieldTypes                              = null;
    StringBuffer  strQuery                                   = null; 
    Statement     objStatement                               = null; 
    ResultSet     objResultSet                               = null;
    try 
    {
      strQuery = new StringBuffer(" SELECT ");
      strQuery.append(" FIELD_TYPE_ID,FIELD_TYPE_NAME, FIELD_TYPE_DESC ");
      strQuery.append(" FROM ");
      strQuery.append(" VW_ADH_FIELD_TYPE ");
      objStatement  = argCon.createStatement();
      objResultSet = objStatement.executeQuery(strQuery.toString());
      if(!(objResultSet.isBeforeFirst() && objResultSet.isAfterLast()))
      {
            colFieldTypes                                     = new Vector();
            while(objResultSet.next())
            {
                  modFieldType = new FieldTypeModel();
                  modFieldType.setFieldTypeID(objResultSet.getInt("FIELD_TYPE_ID"));
                  modFieldType.setFieldTypeName(objResultSet.getString("FIELD_TYPE_NAME"));
                  modFieldType.setFieldTypeDescription(objResultSet.getString("FIELD_TYPE_DESC"));
                  colFieldTypes.add(modFieldType);
            }
      }
      objStatement.close();
      objResultSet.close();
    }   
    catch (SQLException objSQLEx) 
    {
          objSQLEx.printStackTrace();
    }
    catch (Exception objEx) 
    {
          objEx.printStackTrace();
    }
    finally 
    {
          if(objStatement != null)
            objStatement.close();
          if(objResultSet != null)
            objResultSet.close();
    }
    return colFieldTypes;
  }

/**
 * Get the possible display types set to fields. The types include: shown,displayed,etc..
 * @param  argCon Connection
 * @throws SQLException if the database operation failed
 * @return Collection of the field display types available. types include shown,displayed,etc..
 * @see 
 */

  public static Vector getFieldDisplayTypesList(Connection argCon) throws SQLException
  {
    FieldDisplayTypeModel modFieldDisplayType                              = null;
    Vector        colFieldDisplayTypes                                     = null;
    StringBuffer  strQuery                                                 = null; 
    Statement     objStatement                                             = null; 
    ResultSet     objResultSet                                             = null;
    try 
    {
      strQuery = new StringBuffer(" SELECT ");
      strQuery.append(" FIELD_DISPLAY_TYPE_ID,FIELD_DISPLAY_TYPE_NAME,FIELD_DISPLAY_TYPE_DESC  ");
      strQuery.append(" FROM ");
      strQuery.append(" VW_ADH_FIELD_DISPLAY_TYPE ");
      objStatement  = argCon.createStatement();
      objResultSet = objStatement.executeQuery(strQuery.toString());
      if(!(objResultSet.isBeforeFirst() && objResultSet.isAfterLast()))
      {
            colFieldDisplayTypes                                     = new Vector();
            while(objResultSet.next())
            {
                  modFieldDisplayType = new FieldDisplayTypeModel();
                  modFieldDisplayType.setFieldDisplayTypeID(objResultSet.getInt("FIELD_DISPLAY_TYPE_ID"));
                  modFieldDisplayType.setFieldDisplayTypeName(objResultSet.getString("FIELD_DISPLAY_TYPE_NAME"));
                  modFieldDisplayType.setFieldDisplayTypeDescription(objResultSet.getString("FIELD_DISPLAY_TYPE_DESC"));
                  colFieldDisplayTypes.add(modFieldDisplayType);
            }
       }
       objStatement.close();
       objResultSet.close();
    }   
    catch (SQLException objSQLEx) 
    {
          objSQLEx.printStackTrace();
    }
    catch (Exception objEx) 
    {
          objEx.printStackTrace();
    }
    finally 
    {
          if(objStatement != null)
            objStatement.close();
          if(objResultSet != null)
            objResultSet.close();
    }
    return colFieldDisplayTypes;
  }

/**
 * Get the relation symbols list. The symbols include: And,Or,Not,etc..
 * @param  argCon Connection
 * @throws SQLException if the database operation failed
 * @return Collection of the relation symbols supported. Symbols include And,Or,etc..
 * @see 
 */

  public static Vector getRelationSymbolsList(Connection argCon) throws SQLException
  {
    RelationSymbolDTO dtoRelationSymbol                                    = null;
    Vector        colRelationSymbols                                       = null;
    StringBuffer  strQuery                                                 = null; 
    Statement     objStatement                                             = null; 
    ResultSet     objResultSet                                             = null;
    ConditionElementTypeDTO dtoConditionElementType;
    try 
    {
      strQuery = new StringBuffer(" SELECT ");
      strQuery.append(" SYMBOL_TYPE_ID, SYMBOL_TYPE_NAME, SYMBOL_TYPE_SQL");      
      strQuery.append(" FROM ");
      strQuery.append(" VW_ADH_SYMBOL_TYPE ");
      objStatement  = argCon.createStatement();
      objResultSet = objStatement.executeQuery(strQuery.toString());
      if(!(objResultSet.isBeforeFirst() && objResultSet.isAfterLast()))
      {
            colRelationSymbols                                                   = new Vector();
            while(objResultSet.next())
            {
                  dtoRelationSymbol = new RelationSymbolDTO();
                  dtoRelationSymbol.setRelationSymbolID(objResultSet.getInt("SYMBOL_TYPE_ID"));
                  dtoRelationSymbol.setRelationSymboName(objResultSet.getString("SYMBOL_TYPE_NAME"));
                  dtoRelationSymbol.setRelationSymbolSQL(objResultSet.getString("SYMBOL_TYPE_SQL"));                  
                  dtoConditionElementType = new ConditionElementTypeDTO();
                  dtoConditionElementType.setConditionElementTypeID(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL);
                  dtoRelationSymbol.setConditionElementType(dtoConditionElementType);
                  colRelationSymbols.add(dtoRelationSymbol);
            }
      }
      objStatement.close();
      objResultSet.close();
    }   
    catch (SQLException objSQLEx) 
    {
          objSQLEx.printStackTrace();
    }
    catch (Exception objEx) 
    {
          objEx.printStackTrace();
    }
    finally 
    {
          if(objStatement != null)
            objStatement.close();
          if(objResultSet != null)
            objResultSet.close();
    }
    return colRelationSymbols;
  }

 
/**
 * Get the term types list. The types include: "Field to Field Term","Field to DataView Term",etc..
 * @param  argCon Connection
 * @throws SQLException if the database operation failed
 * @return Collection of the types supported. The types include "Field to Field Term","Field to DataView Term",etc..
 * @see 
 */
  public static Vector getTermTypesList(Connection argCon) throws SQLException
  {
    TermTypeDTO dtoTermType                                                = null;
    Vector        colTermTypes                                             = null;
    StringBuffer  strQuery                                                 = null; 
    Statement     objStatement                                             = null; 
    ResultSet     objResultSet                                             = null;
    try 
    {
      strQuery = new StringBuffer(" SELECT ");
      strQuery.append(" TERM_TYPE_ID, TERM_TYPE_NAME, TERM_TYPE_DESC,OPERAND1_TABLE_ID, OPERAND1_TABLE_NAME, OPERAND2_TABLE_ID, OPERAND2_TABLE_NAME");      
      strQuery.append(" FROM ");
      strQuery.append(" VW_ADH_TERM_TYPE ");
      objStatement  = argCon.createStatement();
      objResultSet = objStatement.executeQuery(strQuery.toString());
      if(!(objResultSet.isBeforeFirst() && objResultSet.isAfterLast()))
      {
            colTermTypes                                                   = new Vector();
            while(objResultSet.next())
            {
                  dtoTermType = new TermTypeDTO();
                  dtoTermType.setTermTypeID(objResultSet.getInt("TERM_TYPE_ID"));
                  dtoTermType.setTermTypeName(objResultSet.getString("TERM_TYPE_NAME"));
                  colTermTypes.add(dtoTermType);
            }
      }
      objStatement.close();
      objResultSet.close();
    }   
    catch (SQLException objSQLEx) 
    {
          objSQLEx.printStackTrace();
    }
    catch (Exception objEx) 
    {
          objEx.printStackTrace();
    }
    finally 
    {
          if(objStatement != null)
            objStatement.close();
          if(objResultSet != null)
            objResultSet.close();
    }
    return colTermTypes;
  }

/**
 * Get the term operators list. The operators include: >,<,>=,etc..
 * @param  argCon Connection
 * @throws SQLException if the database operation failed
 * @return Collection of the term operators supported. Operators include >,<,etc..
 * @see 
 */

  public static Vector getTermOperatorsList(Connection argCon) throws SQLException
  {
    TermOperatorModel modTermOperator                                      = null;
    Vector        colTermOperators                                         = null;
    StringBuffer  strQuery                                                 = null; 
    Statement     objStatement                                             = null; 
    ResultSet     objResultSet                                             = null;
    try 
    {
      strQuery = new StringBuffer(" SELECT ");
      strQuery.append(" TERM_OPERATOR_ID, TERM_OPERATOR_NAME, TERM_OPERATOR_SQL, TERM_DESC, TERM_TYPE_ID ");      
      strQuery.append(" FROM ");
      strQuery.append(" VW_ADH_TERM_OPERATOR ");
      objStatement  = argCon.createStatement();
      objResultSet = objStatement.executeQuery(strQuery.toString());
      if(!(objResultSet.isBeforeFirst() && objResultSet.isAfterLast()))
      {
            colTermOperators                                                   = new Vector();
            while(objResultSet.next())
            {
                  modTermOperator = new TermOperatorModel();
                  modTermOperator.setTermOperatorID(objResultSet.getInt("TERM_OPERATOR_ID"));
                  modTermOperator.setTermOperatorName(objResultSet.getString("TERM_OPERATOR_NAME"));
                  modTermOperator.setTermOperatorSQL(objResultSet.getString("TERM_OPERATOR_SQL"));
                  modTermOperator.setTermOperatorDescription(objResultSet.getString("TERM_DESC"));
                  modTermOperator.setTermOperatorTypeID(objResultSet.getInt("TERM_TYPE_ID"));
                  colTermOperators.add(modTermOperator);
            }
      }
      objStatement.close();
      objResultSet.close();
    }   
    catch (SQLException objSQLEx) 
    {
          objSQLEx.printStackTrace();
    }
    catch (Exception objEx) 
    {
          objEx.printStackTrace();
    }
    finally 
    {
          if(objStatement != null)
            objStatement.close();
          if(objResultSet != null)
            objResultSet.close();
    }
    return colTermOperators;
  }

/**
 * Get condition element types list. The types include: "Operator","Term",etc..
 * @param  argCon Connection
 * @throws SQLException if the database operation failed
 * @return Collection of the term types supported. Types include "Operator","Term",etc..
 * @see 
 */

  public static Vector getConditionElementTypesList(Connection argCon) throws SQLException
  {
    ConditionElementTypeDTO dtoConditionElementType                        = null;
    Vector        colConditionElementTypes                                 = null;
    StringBuffer  strQuery                                                 = null; 
    Statement     objStatement                                             = null; 
    ResultSet     objResultSet                                             = null;
    try 
    {
      strQuery = new StringBuffer(" SELECT ");
      strQuery.append(" ELEMENT_TYPE_ID, ELEMENT_TYPE_NAME, ELEMENT_TYPE_TABLE_ID, ELEMENT_TYPE_TABLE_NAME, ELEMENT_TYPE_DESC  ");
      strQuery.append(" FROM ");
      strQuery.append(" VW_ADH_ELEMENT_TYPE ");
      objStatement  = argCon.createStatement();
      objResultSet = objStatement.executeQuery(strQuery.toString());
      if(!(objResultSet.isBeforeFirst() && objResultSet.isAfterLast()))
      {
            colConditionElementTypes                                     = new Vector();
            while(objResultSet.next())
            {
                  dtoConditionElementType = new ConditionElementTypeDTO();
                  dtoConditionElementType.setConditionElementTypeID(objResultSet.getInt("ELEMENT_TYPE_ID"));
                  dtoConditionElementType.setConditionElementTypeName(objResultSet.getString("ELEMENT_TYPE_NAME"));
                  colConditionElementTypes.add(dtoConditionElementType);
            }
      }
      objStatement.close();
      objResultSet.close();
    }   
    catch (SQLException objSQLEx) 
    {
          objSQLEx.printStackTrace();
    }
    catch (Exception objEx) 
    {
          objEx.printStackTrace();
    }
    finally 
    {
          if(objStatement != null)
            objStatement.close();
          if(objResultSet != null)
            objResultSet.close();
    }
    return colConditionElementTypes;
  }


/**
 * Get the function types list.
 * @param  argCon Connection
 * @throws SQLException if the database operation failed
 * @return Collection of the function types supported
 * @see 
 */

  public static Vector getFunctionTypesList(Connection argCon) throws SQLException
  {
    FunctionTypeModel modFunctionType                                      = null;
    Vector        colFunctionTypes                                         = null;
    StringBuffer  strQuery                                                 = null; 
    Statement     objStatement                                             = null; 
    ResultSet     objResultSet                                             = null;
    try 
    {
      strQuery = new StringBuffer(" SELECT ");
      strQuery.append(" FUNCTION_TYPE_ID, FUNCTION_TYPE_NAME, FUNCTION_TYPE_DESC  ");
      strQuery.append(" FROM ");
      strQuery.append(" VW_ADH_FUNCTION_TYPE ");
      objStatement  = argCon.createStatement();
      objResultSet = objStatement.executeQuery(strQuery.toString());
      if(!(objResultSet.isBeforeFirst() && objResultSet.isAfterLast()))
      {
            colFunctionTypes                                     = new Vector();
            while(objResultSet.next())
            {
                  modFunctionType = new FunctionTypeModel();
                  modFunctionType.setFunctionTypeID(objResultSet.getInt("FUNCTION_TYPE_ID"));
                  modFunctionType.setFunctionTypeName(objResultSet.getString("FUNCTION_TYPE_NAME"));
                  modFunctionType.setFunctionTypeDescription(objResultSet.getString("FUNCTION_TYPE_DESC"));
                  colFunctionTypes.add(modFunctionType);
            }
      }
      objStatement.close();
      objResultSet.close();
    }   
    catch (SQLException objSQLEx) 
    {
          objSQLEx.printStackTrace();
    }
    catch (Exception objEx) 
    {
          objEx.printStackTrace();
    }
    finally 
    {
          if(objStatement != null)
            objStatement.close();
          if(objResultSet != null)
            objResultSet.close();
    }
    return colFunctionTypes;
  }

/**
 * Get the functions list and store them into a hashmap grouped by the function types.
 * @param  argCon Connection
 * @throws SQLException if the database operation failed
 * @return hashmap of the functions supported
 * @see 
 */
  public static HashMap getFunctionsList(Connection argCon) throws SQLException
  {
    HashMap hmFunctions                                                    = null;
    FunctionTypeModel modFunctionType                                      = null;
    
    Vector        colFunctionTypes                                         = null;
    Vector        colFunctions                                             = null;
    try 
    {
      hmFunctions = new HashMap(100);
      colFunctionTypes = getFunctionTypesList(argCon);
      for (int i = 0; i < colFunctionTypes.size(); i++) 
      {
        modFunctionType = (FunctionTypeModel)colFunctionTypes.elementAt(i);
        colFunctions    = getFunctionsListByType(argCon,modFunctionType.getFunctionTypeID(),QueryBuilderInterfaceKey.FUNCTION_STATUS_ACTIVE);
        hmFunctions.put(modFunctionType,colFunctions);
      }
      
      
    }   
    catch (SQLException objSQLEx) 
    {
          objSQLEx.printStackTrace();
    }
    catch (Exception objEx) 
    {
          objEx.printStackTrace();
    }
    return hmFunctions;
  }

/**
 * Get the functions list belonging to a certain type and for a certain status.
 * @param  argCon Connection, argFunctionTypeID int , argFunctionStatusID int
 * @throws SQLException if the database operation failed
 * @return Collection of the functions matching the sent criteria
 * @see 
 */

  public static Vector getFunctionParametersList(Connection argCon,int argFunctionID) throws SQLException
  {
    FunctionParameterDefinitionModel modFunctionParameterDefinition        = null;
    Vector        colFunctionParameterDefinitions                          = null;
    StringBuffer  strQuery                                                 = null; 
    Statement     objStatement                                             = null; 
    ResultSet     objResultSet                                             = null;
    try 
    {
      strQuery = new StringBuffer(" SELECT ");
      strQuery.append(" FUNCTION_ID, PARAMETER_DEFINITION_ID,PARAMETER_DEFINITION_NAME, PARAMETER_DEFINITION_DESC, PARAMETER_DEFINITION_OPTIONAL, PARAMETER_DEFINITION_IS_LIST  ");
      strQuery.append(" FROM ");
      strQuery.append(" VW_ADH_PARAMETER_DEFINITION ");
      strQuery.append(" WHERE ");
      strQuery.append(" FUNCTION_ID = "+ argFunctionID );
      objStatement  = argCon.createStatement();
      objResultSet = objStatement.executeQuery(strQuery.toString());
      if(!(objResultSet.isBeforeFirst() && objResultSet.isAfterLast()))
      {
            colFunctionParameterDefinitions                                = new Vector();
            while(objResultSet.next())
            {
                  modFunctionParameterDefinition = new FunctionParameterDefinitionModel();
                  modFunctionParameterDefinition.setFunctionID(objResultSet.getInt("FUNCTION_ID"));
                  modFunctionParameterDefinition.setParameterDefinitionID(objResultSet.getInt("PARAMETER_DEFINITION_ID"));
                  modFunctionParameterDefinition.setParameterDefinitionName(objResultSet.getString("PARAMETER_DEFINITION_NAME"));
                  modFunctionParameterDefinition.setParameterDefinitionDescription(objResultSet.getString("PARAMETER_DEFINITION_DESC"));
                  modFunctionParameterDefinition.setParameterDefinitionIsList(objResultSet.getInt("PARAMETER_DEFINITION_IS_LIST"));
                  modFunctionParameterDefinition.setParameterDefinitionOptional(objResultSet.getInt("PARAMETER_DEFINITION_OPTIONAL"));
                  colFunctionParameterDefinitions.add(modFunctionParameterDefinition);
            }
      }
      objStatement.close();
      objResultSet.close();
    }   
    catch (SQLException objSQLEx) 
    {
          objSQLEx.printStackTrace();
    }
    catch (Exception objEx) 
    {
          objEx.printStackTrace();
    }
    finally 
    {
          if(objStatement != null)
            objStatement.close();
          if(objResultSet != null)
            objResultSet.close();
    }
    return colFunctionParameterDefinitions;
  }


/**
 * Get the functions list belonging to a certain type and for a certain status.
 * @param  argCon Connection, argFunctionTypeID int , argFunctionStatusID int
 * @throws SQLException if the database operation failed
 * @return Collection of the functions matching the sent criteria
 * @see 
 */

  public static Vector getFunctionsListByType(Connection argCon,int argFunctionTypeID,int argFunctionStatusID) throws SQLException
  {
    
    FunctionDTO dtoFunction                                                = null;
    Vector        colFunctions                                             = null;
    StringBuffer  strQuery                                                 = null; 
    Statement     objStatement                                             = null; 
    ResultSet     objResultSet                                             = null;
    try 
    {
      strQuery = new StringBuffer(" SELECT ");
      strQuery.append(" FUNCTION_ID, FUNCTION_NAME, FUNCTION_SQL_CALL, FUNCTION_TYPE_ID, FUNCTION_TYPE_NAME, FUNCTION_HELP_TEXT, FUNCTION_DESCRIPTION, FUNCTION_STATUS_ID, FUNCTION_STATUS_NAME ");
      strQuery.append(" FROM ");
      strQuery.append(" VW_ADH_FUNCTION ");
      strQuery.append(" WHERE ");
      strQuery.append(" FUNCTION_TYPE_ID = "+ argFunctionTypeID +" AND FUNCTION_STATUS_ID = "+argFunctionStatusID);
      objStatement  = argCon.createStatement();
      objResultSet = objStatement.executeQuery(strQuery.toString());
      if(!(objResultSet.isBeforeFirst() && objResultSet.isAfterLast()))
      {
            colFunctions                                         = new Vector();
            while(objResultSet.next())
            {
                  dtoFunction = new FunctionDTO();
                  dtoFunction.setFunctionID(objResultSet.getInt("FUNCTION_ID"));
                  dtoFunction.setFunctionName(objResultSet.getString("FUNCTION_NAME"));
                  dtoFunction.setFunctionSQL(objResultSet.getString("FUNCTION_SQL_CALL"));
                  dtoFunction.setFunctionTypeID(objResultSet.getInt("FUNCTION_TYPE_ID"));
                  dtoFunction.setFunctionTypeName(objResultSet.getString("FUNCTION_TYPE_NAME"));
                  dtoFunction.setFunctionHelpText(objResultSet.getString("FUNCTION_HELP_TEXT"));
                  dtoFunction.setFunctionDescription(objResultSet.getString("FUNCTION_DESCRIPTION"));
                  dtoFunction.setFunctionStatusID(objResultSet.getInt("FUNCTION_STATUS_ID"));
                  dtoFunction.setFunctionStatusName(objResultSet.getString("FUNCTION_STATUS_NAME"));
                  dtoFunction.setParameterDefinitions(getFunctionParametersList(argCon,dtoFunction.getFunctionID()));
                  colFunctions.add(dtoFunction);
            }
      }
      objStatement.close();
      objResultSet.close();
    }   
    catch (SQLException objSQLEx) 
    {
          objSQLEx.printStackTrace();
    }
    catch (Exception objEx) 
    {
          objEx.printStackTrace();
    }
    finally 
    {
          if(objStatement != null)
            objStatement.close();
          if(objResultSet != null)
            objResultSet.close();
    }
    return colFunctions;
  }



/**
 * Verify the query SQL Syntax and return the error encountered if any
 * @param  argCon Connection, argQuery String : Query SQL text
 * @throws SQLException if the database query failed
 * @return The error message encountered..
 * @see 
 */

  public static String verifyQuery(Connection argCon,String argQuery) throws SQLException
  {
    String strErrorMessage                                                 = null; 
    StringBuffer  strQuery                                                 = null; 
    Statement     objStatement                                             = null; 
    ResultSet     objResultSet                                             = null;
    try 
    {
      Utility.logger.debug("Verify Query:  "+argQuery);
      objStatement  = argCon.createStatement();
      objResultSet = objStatement.executeQuery(argQuery);
      objStatement.close();
      objResultSet.close();
    }   
    catch (SQLException objSQLEx) 
    {
          //objSQLEx.printStackTrace();
          strErrorMessage = objSQLEx.getMessage();
    }
    catch (Exception objEx) 
    {
          objEx.printStackTrace();
    }
    finally 
    {
          if(objStatement != null)
            objStatement.close();
          if(objResultSet != null)
            objResultSet.close();
    }
    return strErrorMessage;
  }

  public static void insertVwAdhField(Connection con,
                                    Long data_view_id, 
                                    Long data_view_issue, 
                                    Long data_view_version, 
                                    Long field_id, 
                                    String field_name, 
                                    Long field_display_type_id, 
                                    String field_display_type_name, 
                                    Long field_type_id, 
                                    String field_type_name,
                                    Long newField_rf_object,
                                    String newField_sql_cash,
                                    String newField_description,
                                    Long newItem_position)
  {
      try
      {
       Statement stat = con.createStatement();


     // Utility.logger.debug("newField_sql_cash = " + newField_sql_cash);
      newField_sql_cash = newField_sql_cash.replaceAll("'","''");
   //   Utility.logger.debug("newField_sql_cash2 = " + newField_sql_cash);
   /*

      if(newField_sql_cash.startsWith("'"))newField_sql_cash = "'"+newField_sql_cash ;
       if(newField_sql_cash.endsWith("'"))newField_sql_cash = newField_sql_cash+"'";
    */
               
       String strSql = "insert into ADH_FIELD(DATA_VIEW_ID,FIELD_ID,FIELD_NAME,FIELD_DESCRIPTION,FIELD_SQL_CASH,FIELD_DISPLAY_TYPE_ID,FIELD_TYPE_ID,FIELD_RF_OBJECT,ITEM_POSITION) "+
                        " values("+data_view_id+","+field_id+",'"+field_name+"','"+newField_description+"','"+newField_sql_cash+"','"+field_display_type_id+"',"+field_type_id+","+newField_rf_object+","+newItem_position+") ";
       Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }

  public static void insertAdhUniverseDefinition(Connection con,int strUniverseId,Long strdataViewId)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = "insert into ADH_UNIVERSE_DEFINITION (UNIVERSE_ID,DATA_VIEW_ID) "+
                        " values("+strUniverseId+","+strdataViewId+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }

  public static void insertVwAdhField(Connection con,
                                      Long group_by_id, 
                                      Long data_view_id, 
                                      Long data_view_issue, 
                                      Long data_view_version, 
                                      Long group_field_id, 
                                      String group_field_name, 
                                      Long group_place)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = "INSERT INTO ADH_GROUP_BY (GROUP_BY_ID,DATA_VIEW_ID,GROUP_FIELD_ID,GROUP_PLACE) "+
                        " values("+group_by_id+","+data_view_id+","+group_field_id+","+group_place+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }

  public static void insertVwAdhHaving(Connection con,Long having_id, Long data_view_id, Long element_type_id, String element_type_name, Long element_id, Long having_place)
  {
    try
      {
       Statement stat = con.createStatement();
       String strSql = "INSERT INTO ADH_HAVING(HAVING_ID,DATA_VIEW_ID,HAVING_PLACE,ELEMENT_ID,ELEMENT_TYPE_ID) "+
                        " values("+having_id+","+data_view_id+","+having_place+","+element_id+","+element_type_id+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }

  public static void insertVwAdhInputParam(Connection con,Long input_param_id, Long data_view_id, Long field_id, String input_param_label_text, Long input_control_types_id, String input_control_types_value, Long input_param_order)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = "INSERT INTO ADH_INPUT_PARAM (INPUT_PARAM_ID,INPUT_PARAM_DATA_VIEW_ID,FIELD_ID,INPUT_PARAM_LABEL_TEXT,INPUT_CONTROL_TYPES_ID,INPUT_PARAM_ORDER) "+
                        " values("+input_param_id+","+data_view_id+","+field_id+",'"+input_param_label_text+"',"+input_control_types_id+","+input_param_order+") ";
       Utility.logger.debug("www"+strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }

  public static void insertVwAdhWhere(Connection con,Long where_id, Long data_view_id, Long element_type_id, String element_type_name, Long element_id, Long where_place)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = " INSERT INTO ADH_WHERE (WHERE_ID,DATA_VIEW_ID,WHERE_PLACE,ELEMENT_ID,ELEMENT_TYPE_ID) "+
                        " values("+where_id+","+data_view_id+","+where_place+","+element_id+","+element_type_id+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }  
  }

  public static void insertVwAdhOrderBy(Connection con,
                                        Long order_by_id, 
                                        Long data_view_id, 
                                        Long field_id, 
                                        Long order_by_type_id, 
                                        Long order_place)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = " INSERT INTO ADH_ORDER_BY (ORDER_BY_ID,DATA_VIEW_ID,FIELD_ID,ORDER_BY_TYPE_ID,ORDER_PLACE) "+
                        " values("+order_by_id+","+data_view_id+","+field_id+","+order_by_type_id+","+order_place+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }

  public static void insertVwAdhTerm(Connection con,
                                        Long term_id, 
                                        Long data_view_id, 
                                        Long term_type_id, 
                                        Long operand1_object_id, 
                                        Long operand2_object_id, 
                                        Long operator_id, 
                                        String term_operator_name, 
                                        String term_operator_sql,
                                        String newTerm_sql)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = " INSERT INTO ADH_TERM(TERM_ID,DATA_VIEW_ID,OPERAND1_OBJECT_ID,OPERAND2_OBJECT_ID,OPERATOR_ID,TERM_TYPE_ID,TERM_SQL) "+
                        " values("+term_id+","+data_view_id+","+operand1_object_id+","+operand2_object_id+","+operator_id+","+term_type_id+",'"+newTerm_sql+"') ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }


  public static void insertVwAdhParameterValue(Connection con,Long parameter_value_id, Long link_field_id, Long value_field_id, Long parameter_value_place, Long parameter_definition_id)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = " INSERT INTO ADH_PARAMETER_VALUE (PARAMETER_VALUE_ID,LINK_FIELD_ID,VALUE_FIELD_ID,PARAMETER_VALUE_PLACE,FUNCTION_PARAMETER_ID) "+
                        " values("+parameter_value_id+","+link_field_id+","+value_field_id+","+parameter_value_place+","+parameter_definition_id+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }


  public static void insertVwAdhFrom(Connection con,Long from_id, Long data_view_id, Long fr_data_view_id)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = "  INSERT INTO ADH_FROM(FROM_ID,DATA_VIEW_ID,FR_DATA_VIEW_ID) "+
                        " values("+from_id+","+data_view_id+","+fr_data_view_id+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }  
  }

  public static void insertVwAdhDataView(Connection con,Long data_view_id, Long data_view_issue, Long data_view_version, String data_view_name, Long data_view_type_id, String data_view_type_name, Long data_view_status_id, String data_view_status_name, 
            String newData_view_description, Long newData_view_unique)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = "  INSERT INTO ADH_DATA_VIEW (DATA_VIEW_ID, DATA_VIEW_ISSUE, DATA_VIEW_VERSION, DATA_VIEW_NAME, DATA_VIEW_STATUS_ID, DATA_VIEW_TYPE_ID, DATA_VIEW_DESCRIPTION, DATA_VIEW_UNIQUE)"+
                        " values("+data_view_id+","+data_view_issue+","+data_view_version+",'"+data_view_name+"',"+data_view_status_id+","+data_view_type_id+",'"+newData_view_description+"',"+newData_view_unique+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }


  public static void updateVwAdhDataViewStatus(Connection con,int data_view_id, int data_view_status_id)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = "  UPDATE ADH_DATA_VIEW set DATA_VIEW_STATUS_ID = "+data_view_status_id+" where DATA_VIEW_ID = "+data_view_id+" ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
  }
}
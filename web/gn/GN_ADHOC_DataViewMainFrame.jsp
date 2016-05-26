<%@ page buffer="1024kb" %>
<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import="java.util.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
%>

<%--
The code that uploads the data from the back end to the front end works on the assumption
that the loading will be in the same order that the date comes with.
Add by khaled reda 1/7/2004
--%>

<HTML>
      <SCRIPT language=JavaScript src="../resources/js/DataViews.js" type=text/javascript></SCRIPT>
   <HEAD>
<script language="javascript">
<!--Declaration of the main Objects used in the wizard -->

<!--The java script object of the new dataview created or the one being updated: holds all the query clauses-->
var objNewDataView = new NewDataView();    
<!--The java script object holding the look up used through the wizard-->
var objDataViewWizard = new DataViewWizard();

</script> 
<%!     
/**
 * Fill the data view issues retrieved to the client script object DataViewIssues
 * @param argRequest HttpServletRequest,argOut JspWriter 
 * @throws Exception if the IO operation failed
 * @return 
 * @see 
 */
 
 private void fillDataViewIssues(HttpServletRequest argRequest,JspWriter argOut) throws IOException
 {
    int nCurrentIssue                                        = -1;
//    int nDataViewTypeID                        = 0;

    StringBuffer strPredefinedDataViewScript;
    StringBuffer strUserdefinedDataViewScript;
    StringBuffer strWizarScript;
    Vector colDataViews                                      = null;
    Vector colUserDefinedDataViews                           = new Vector();
    
    HashMap hmData                                           = new HashMap(100);
    BriefDataViewDTO dtoBriefDataView                        = null;
    QueryBuilderWizardDTO dtoQueryBuilderWizard              = null;
    
    hmData = (HashMap)argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
    dtoQueryBuilderWizard = (QueryBuilderWizardDTO)hmData.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
    
// Writing the script used tp fill in the empty cell of the predefined data views
    colDataViews = (Vector)hmData.get(InterfaceKey.HASHMAP_KEY_COLLECTION); 
    strPredefinedDataViewScript = new StringBuffer("var objPreDefinedDataViewIssues=new DataViewIssues(); var objPreDefinedDataViewIssue = new DataViewIssue('','',''); ");
    strPredefinedDataViewScript.append(" var objDataViewVersion = new DataViewVersion('','',''); ");
    strPredefinedDataViewScript.append(" objPreDefinedDataViewIssue.addVersion(objDataViewVersion); ");
    strPredefinedDataViewScript.append(" objPreDefinedDataViewIssues.addIssue(objPreDefinedDataViewIssue); ");

 // Writing the script used tp fill in the empty cell of the user defined data views   
    strUserdefinedDataViewScript = new StringBuffer("var objUserDefinedDataViewIssues=new DataViewIssues(); var objUserDefinedDataViewIssue = new DataViewIssue('','',''); ");
    strUserdefinedDataViewScript.append(" var objDataViewVersion = new DataViewVersion('','',''); ");
    strUserdefinedDataViewScript.append(" objUserDefinedDataViewIssue.addVersion(objDataViewVersion); ");
    strUserdefinedDataViewScript.append(" objUserDefinedDataViewIssues.addIssue(objUserDefinedDataViewIssue); ");

 // Loop on the predefined data views and write the script to fill in the data view issues arrays
    for (int i = 0; i < colDataViews.size(); i++) 
    {
        dtoBriefDataView = (BriefDataViewDTO)colDataViews.elementAt(i);
  //      colAllDataViews.add(new Integer(dtoBriefDataView.getDataViewID()));
  //      nDataViewTypeID = dtoBriefDataView.getDataViewTypeID();
       if(dtoBriefDataView.getDataViewIssue()  ==  nCurrentIssue)  
       {
           strPredefinedDataViewScript.append(" var objDataViewVersion = new DataViewVersion('"+dtoBriefDataView.getDataViewID()+"','"+dtoBriefDataView.getDataViewName()+"','"+dtoBriefDataView.getDataViewVersion()+"','"+dtoBriefDataView.getDataViewDescription()+"');");  
           strPredefinedDataViewScript.append(" objPreDefinedDataViewIssue"+dtoBriefDataView.getDataViewIssue()+".addVersion(objDataViewVersion);");
       }    
       else
       {
            strPredefinedDataViewScript.append(" var objPreDefinedDataViewIssue"+dtoBriefDataView.getDataViewIssue()+"=new DataViewIssue('"+dtoBriefDataView.getDataViewIssue()+"','"+dtoBriefDataView.getDataViewName()+"','"+dtoBriefDataView.getDataViewID()+"');");
            strPredefinedDataViewScript.append(" var objDataViewVersion = new DataViewVersion('"+dtoBriefDataView.getDataViewID()+"','"+dtoBriefDataView.getDataViewName()+"','"+dtoBriefDataView.getDataViewVersion()+"','"+dtoBriefDataView.getDataViewDescription()+"');");
            strPredefinedDataViewScript.append(" objPreDefinedDataViewIssue"+dtoBriefDataView.getDataViewIssue()+".addVersion(objDataViewVersion);");
            strPredefinedDataViewScript.append(" objPreDefinedDataViewIssues.addIssue(objPreDefinedDataViewIssue"+dtoBriefDataView.getDataViewIssue()+"); ");
            nCurrentIssue = dtoBriefDataView.getDataViewIssue();
       }
    }
    
 // Clearing the vector then looping on the user defined data views and write the script to fill in the data view issues arrays    
    colDataViews.clear();
    colDataViews = (Vector)hmData.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION); 
    for (int j = 0; j < colDataViews.size(); j++) 
    {
       dtoBriefDataView = (BriefDataViewDTO)colDataViews.elementAt(j);
       colUserDefinedDataViews.add(new Integer(dtoBriefDataView.getDataViewID()));
       if(dtoBriefDataView.getDataViewIssue()  ==  nCurrentIssue)  
       {
          strUserdefinedDataViewScript.append(" var objDataViewVersion = new DataViewVersion('"+dtoBriefDataView.getDataViewID()+"','"+dtoBriefDataView.getDataViewName()+"','"+dtoBriefDataView.getDataViewVersion()+"','"+dtoBriefDataView.getDataViewDescription()+"');");  
          strUserdefinedDataViewScript.append(" objUserDefinedDataViewIssue"+dtoBriefDataView.getDataViewIssue()+".addVersion(objDataViewVersion);");
       }   
       else
       {
          strUserdefinedDataViewScript.append(" var objUserDefinedDataViewIssue"+dtoBriefDataView.getDataViewIssue()+"=new DataViewIssue('"+dtoBriefDataView.getDataViewIssue()+"','"+dtoBriefDataView.getDataViewName()+"','"+dtoBriefDataView.getDataViewID()+"');");
          strUserdefinedDataViewScript.append(" var objDataViewVersion = new DataViewVersion('"+dtoBriefDataView.getDataViewID()+"','"+dtoBriefDataView.getDataViewName()+"','"+dtoBriefDataView.getDataViewVersion()+"','"+dtoBriefDataView.getDataViewDescription()+"');");  
          strUserdefinedDataViewScript.append(" objUserDefinedDataViewIssue"+dtoBriefDataView.getDataViewIssue()+".addVersion(objDataViewVersion);");
          strUserdefinedDataViewScript.append(" objUserDefinedDataViewIssues.addIssue(objUserDefinedDataViewIssue"+dtoBriefDataView.getDataViewIssue()+"); ");
          nCurrentIssue = dtoBriefDataView.getDataViewIssue();
       }
     }

 // Call fillQueryBuilderWizard to fill in the look ups in the wizard object
     strWizarScript = fillQueryBuilderWizard(dtoQueryBuilderWizard,colUserDefinedDataViews,argOut);
//   Writing the script constructed above that fills the dataview issues and the look ups  
     argOut.println("<script language=\"javascript\"> ");
     argOut.println(strPredefinedDataViewScript.toString());
     argOut.println(strUserdefinedDataViewScript.toString());
     argOut.println(strWizarScript.toString());
 // Check on the action type and adjust the universe accordingly
     if(argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_GENERAL_WIZARD))
     {
       //System.out.println ("fady true: argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION): "+ argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION));
       argOut.println(" objNewDataView.m_strUniverseName ='"+QueryBuilderInterfaceKey.UNIVERSE_TYPE_AD_HOC_REPORTS+"';");
     }
     else if(argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_KPI_WIZARD))
     {
       //System.out.println ("fady false: argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION): "+ argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION)); 
       argOut.println(" objNewDataView.m_strUniverseName ='"+QueryBuilderInterfaceKey.UNIVERSE_TYPE_KPI+"';");
     }  
     else if(argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_ELIGIBLE_WIZARD))
     {
       argOut.println(" objNewDataView.m_strUniverseName ='"+QueryBuilderInterfaceKey.UNIVERSE_TYPE_LINES_ELIGIBLE_FOR_COMMISSIONING+"';");
     }
     else if(argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_DCM_QUOTA_CALCULATION_WIZARD))
     {
       argOut.println(" objNewDataView.m_strUniverseName ='"+QueryBuilderInterfaceKey.UNIVERSE_TYPE_DCM_QUOTA_CALCULATION+"';");
     }     
     argOut.println(" </script>");
 }
 // Function to replace string by another string used to replace the quote and the double quotes 
private String replaceByString(String oldString,String strReplacedByString,String NewString,String returnedString)
{
	int nStartIndex = 0;
	int nEndIndex = 0;
	String strReplacedString = "";
  
	nStartIndex = oldString.indexOf(NewString.charAt(0));
	if(nStartIndex == -1)
	{
		return returnedString + oldString;
	}
	else
	{
		nEndIndex = nStartIndex;
		strReplacedString = returnedString + oldString.substring(0,nEndIndex)+strReplacedByString;
		nEndIndex = oldString.length();
		oldString = oldString.substring(nStartIndex+1,nEndIndex);
		return replaceByString(oldString,strReplacedByString,NewString,strReplacedString);
	}
}

// Fill in the lookups loaded from the back end.
 private StringBuffer fillQueryBuilderWizard(QueryBuilderWizardDTO argWizard,Vector argAllDataViews,JspWriter argOut) throws IOException
 {
    int nFtFOperatorIndex                                    = 0;
    int nFtDVOperatorIndex                                   = 0;
    StringBuffer strWizardScript                             = new StringBuffer();
    String strFunctionDescription                            = "";
    String strFunctionHelpText                               = "";
    Vector colOperators                                      = null;
    Vector colFTFOperators                                   = new Vector();
    Vector colFDVOperators                                   = new Vector();
    Vector colRelationSymbols                                = null;
    Vector colConditionElementsTypes                         = null;
    Vector colFunctionParameters                             = null;
    Object[] arrFunctionKeys;
 /*
 For Update:
 */   
    QueryDTO dtoQuery;
/*
 End Update
 */   
 
    TermOperatorModel modTermOperator;
    RelationSymbolDTO dtoRelationSymbol;
    ConditionElementTypeDTO dtoConditionElementType;
    FunctionTypeModel modFunctionType;
    FunctionDTO dtoFunction;
    HashMap hmFunctions;
    FunctionParameterDefinitionModel modFunctionParameterDefinition;
        
    Vector colFunctions;
    Vector colFunctionTypes   = new Vector();
    Vector colFunctionIDs;
    HashMap hmFunctionsList   = new HashMap();

    
    colOperators              =  argWizard.getTermOperators();   
    colRelationSymbols        =  argWizard.getRelationSymbols(); 
    colConditionElementsTypes =  argWizard.getConditionElementTypes();
    hmFunctions               =  argWizard.getFunctions();   
    dtoQuery                  =  argWizard.getQuery(); 

 // Fill in the term operators (equal,less than, etc.)
    if(colOperators != null)
    {
      for (int i = 0; i < colOperators.size(); i++) 
      {
        modTermOperator = (TermOperatorModel)colOperators.elementAt(i);
        strWizardScript.append(" var objOperator = new Operator('"+modTermOperator.getTermOperatorID()+"','"+modTermOperator.getTermOperatorName()+"','"+modTermOperator.getTermOperatorSQL()+"','"+modTermOperator.getTermOperatorDescription()+"','"+modTermOperator.getTermOperatorTypeID()+"'); ");
 // Check on the operator type weather field to field or field to data view and write the script that loads them
 // in the data view wizard object in two separate arrays
        if(modTermOperator.getTermOperatorTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_FTF_OPERATORS_TYPE)
        {
            strWizardScript.append(" objOperator.m_nOperatorIndex = "+nFtFOperatorIndex+"; ");
            strWizardScript.append(" objDataViewWizard.addFtFOperator(objOperator); ");
            colFTFOperators.add(new Integer(modTermOperator.getTermOperatorID()));
            nFtFOperatorIndex++;
        }   
        else
        {
            strWizardScript.append(" objOperator.m_nOperatorIndex = "+nFtDVOperatorIndex+"; ");
            strWizardScript.append(" objDataViewWizard.addFtDVOperator(objOperator); ");
            colFDVOperators.add(new Integer(modTermOperator.getTermOperatorID()));
            nFtDVOperatorIndex++;
        }    
      }
    }
// Fill in the relation symbols (and, or etc...)   
// Write the script that loads them in the data view wizard object
    if(colRelationSymbols != null)
    {
      for (int j = 0; j < colRelationSymbols.size(); j++) 
      {
        dtoRelationSymbol = (RelationSymbolDTO)colRelationSymbols.elementAt(j);
        strWizardScript.append(" var objRelationSymbol = new RelationSymbol('"+dtoRelationSymbol.getRelationSymbolID()+"','"+dtoRelationSymbol.getRelationSymboName()+"','"+dtoRelationSymbol.getRelationSymbolSQL()+"','"+dtoRelationSymbol.getRelationSymbolDescription()+"','"+j+"'); ");
        strWizardScript.append(" objDataViewWizard.addRelationSymbol(objRelationSymbol); ");
      }
    }
 // Fill in the condition element types (term or symbol...)   
 // Write the script that loads them in the data view wizard object
    if(colConditionElementsTypes != null)
    {
      for (int k = 0; k < colConditionElementsTypes.size(); k++) 
      {
        dtoConditionElementType = (ConditionElementTypeDTO)colConditionElementsTypes.elementAt(k);
        strWizardScript.append(" var objConditionElementType = new ConditionElementType('"+dtoConditionElementType.getConditionElementTypeID()+"','"+dtoConditionElementType.getConditionElementTypeName()+"',null,"+k+"); ");
        strWizardScript.append(" objDataViewWizard.addConditionElementType(objConditionElementType); ");
      }
    }  

 // Fill in the functions
    arrFunctionKeys = hmFunctions.keySet().toArray();
// Loop on the function keys and for each key that represent a function type get list of the functions under that type
    for (int o = 0; o < arrFunctionKeys.length ; o++) 
    {
      modFunctionType = (FunctionTypeModel)arrFunctionKeys[o];
      colFunctions = (Vector)hmFunctions.get(modFunctionType);
      strWizardScript.append(" var objFunctionType = new FunctionType('"+modFunctionType.getFunctionTypeID()+"','"+modFunctionType.getFunctionTypeName()+"'); ");
// Fill in the IDS for the update data view only.      
      colFunctionTypes.add(new Integer(modFunctionType.getFunctionTypeID()));
      colFunctionIDs     = new Vector();
      for (int l = 0; l < colFunctions.size() ; l++) 
      {
           dtoFunction = (FunctionDTO)colFunctions.elementAt(l);
           colFunctionIDs.add(new Integer(dtoFunction.getFunctionID()));
           colFunctionParameters = dtoFunction.getParameterDefinitions();

           strFunctionDescription = replaceByString(dtoFunction.getFunctionDescription(),"\\\'","\'",strFunctionDescription);
           strFunctionHelpText = replaceByString(dtoFunction.getFunctionHelpText(),"\\\'","\'",strFunctionHelpText);

 // Write the script that loads them in the data view wizard object
           strWizardScript.append(" var objFunction  = new LookUpFunction('"+dtoFunction.getFunctionID()+"','"+dtoFunction.getFunctionName()+"','"+dtoFunction.getFunctionSQL()+"','"+strFunctionDescription+"','"+strFunctionHelpText+"','"+dtoFunction.getFunctionStatusID()+"','"+dtoFunction.getFunctionStatusName()+"','"+dtoFunction.getFunctionTypeID()+"','"+dtoFunction.getFunctionTypeName()+"','"+l+"'); ");
           for (int i = 0; i < colFunctionParameters.size(); i++) 
           {
              modFunctionParameterDefinition = (FunctionParameterDefinitionModel)colFunctionParameters.elementAt(i);
              strWizardScript.append(" var objFunctionParameter = new FunctionParameter('"+modFunctionParameterDefinition.getParameterDefinitionID()+"','"+modFunctionParameterDefinition.getParameterDefinitionName()+"','"+modFunctionParameterDefinition.getParameterDefinitionDescription()+"','"+modFunctionParameterDefinition.getParameterDefinitionOptional()+"','"+modFunctionParameterDefinition.getParameterDefinitionIsList()+"'); ");
              strWizardScript.append(" objFunction.addParameter(objFunctionParameter);");
           }
           strWizardScript.append(" objFunctionType.addNewFunction(objFunction); ");
           strFunctionDescription = "";
           strFunctionHelpText = "";
      }
      strWizardScript.append(" objDataViewWizard.addFunctionType(objFunctionType); ");
      hmFunctionsList.put(new Integer(modFunctionType.getFunctionTypeID()),colFunctionIDs);
      
    }     
    if(dtoQuery != null)
    {
 // For the update, call fill in query to load the data of the data view located in the query DTO into the new data view script object
         strWizardScript.append(fillInQuery(dtoQuery,colFunctionTypes,hmFunctionsList,colFTFOperators,colFDVOperators,argAllDataViews,colRelationSymbols,argOut));
    }
    return strWizardScript;
 }

 private String fillInQuery(QueryDTO argQuery,Vector argFunctionTypes,HashMap argFunctionsList,Vector argFTFOperators,Vector argFDVOperators,Vector argAllDataViews,Vector argRelationSymbols,JspWriter argOut) throws IOException
 {
    int nFtFOperatorIndex                                    = 0;
    int nFtDVOperatorIndex                                   = 0;
    StringBuffer strQueryScript                              = new StringBuffer();
    
    String strDataViewName                                   = null;
    String strDataViewDescription                            = null;

    Vector colSelectedDataViewFields                         = new Vector();
    Vector colSelectedDataViewTypes                          = new Vector();
    Vector colSelectedDataViewsFieldsSQLCash                 = new Vector();
    Vector colDataViewFields                                 = new Vector();  
    Vector colDataViewGroupBy                                = new Vector();  
    Vector colDataViewOrderBy                                = new Vector();  
    
    FieldDTO objField;
    String strFieldName                                           = null;
    String strFieldAlias                                          = null;

    Vector colDataViewConstants                              = null;
    Vector colDataViewParameters                             = null;
    Vector colDataViewTerms                                  = null;
    Vector colDataViewFunctions                              = null;
    Vector colFunctionParameters                             = null;
    HashMap hmSelectedDataViews                              = null;
    
 // Get the data view details from the query DTO  
    hmSelectedDataViews = argQuery.getDetailedDataViews();
    colDataViewFields = argQuery.getDisplayedFields();
    colDataViewConstants = argQuery.getConstantsFields();
    colDataViewParameters= argQuery.getParametersFields();
    colDataViewFunctions = argQuery.getFunctionsFields();
    colDataViewTerms = argQuery.getTerms();
    colDataViewGroupBy = argQuery.getGroupBy();
    colDataViewOrderBy = argQuery.getOrderBy();
    DetailedDataViewDTO dtoDetailedDataView                       = null;

 // Write the script to define the loaded data view name, description, version, etc.    
    strQueryScript = new StringBuffer(" objNewDataView.m_strDataViewName = '"+argQuery.getQueryName()+"';  objNewDataView.m_strDataViewDescription ='"+argQuery.getQueryDescription()+"';");
    strQueryScript.append(" objNewDataView.m_strUniverseName = '"+argQuery.getQueryUniverseName()+"';  objNewDataView.m_nDataViewIssue ='"+argQuery.getQueryIssue()+"';");
    strQueryScript.append(" objNewDataView.m_nSaveMode ='"+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_SAVE_TRUE+"';");
    strQueryScript.append(" objNewDataView.m_nDataViewVersion ='"+argQuery.getQueryVersion()+"';");
    
 // Write the script to define the from clause of the loaded data view
    strQueryScript.append(fillSelectedDataViews(hmSelectedDataViews,colSelectedDataViewsFieldsSQLCash,colSelectedDataViewTypes,colSelectedDataViewFields));
// Write the script to define the select clause of the loaded data view   
    strQueryScript.append(fillDataViewSelectedFields(colDataViewFields,colSelectedDataViewsFieldsSQLCash,colSelectedDataViewTypes,colDataViewConstants,colDataViewFunctions,colDataViewParameters));
// Write the script to define the constants defined in the loaded data view    
    strQueryScript.append(fillDataViewConstants(colDataViewConstants));
// Write the script to define the parameters defined in the loaded data view    
    strQueryScript.append(fillDataViewParameters(colDataViewParameters));
// Write the script to define the functions defined in the loaded data view    
    strQueryScript.append(fillDataViewFunctions(colDataViewFunctions,colSelectedDataViewsFieldsSQLCash,colSelectedDataViewFields,colSelectedDataViewTypes,colDataViewConstants,argFunctionTypes,argFunctionsList,colDataViewParameters));
// Write the script to define the terms defined in the loaded data view     
    strQueryScript.append(fillDataViewTerms(colDataViewTerms,colSelectedDataViewsFieldsSQLCash,colSelectedDataViewFields,colSelectedDataViewTypes,colDataViewConstants,colDataViewFunctions,colDataViewParameters,argFTFOperators,argFDVOperators,argAllDataViews));
// Write the script to define the where clause of the loaded data view        
    strQueryScript.append(fillDataViewWhere(argQuery.getQueryWhere(),colDataViewTerms,argRelationSymbols));
// Write the script to define the group by clause of the loaded data view        
    strQueryScript.append(fillDataViewGroupBy(colDataViewGroupBy,colSelectedDataViewsFieldsSQLCash,colSelectedDataViewTypes,colDataViewConstants,colDataViewFunctions));
// Write the script to define the having clause of the loaded data view        
    strQueryScript.append(fillDataViewHaving(argQuery.getQueryHaving(),colDataViewTerms,argRelationSymbols));
// Write the script to define the order by clause of the loaded data view            
    strQueryScript.append(fillDataViewOrderBy(colDataViewOrderBy,colSelectedDataViewsFieldsSQLCash,colSelectedDataViewTypes,colDataViewConstants,colDataViewFunctions));

    return  strQueryScript.toString();   
 }

private String fillSelectedDataViews(HashMap argSelectedDataViews,Vector argSelectedDataViewsFieldsSQLCach,Vector argSelectedDataViewsTypes,Vector argSelectedDataViewsFields) throws IOException
{
    int nDotIndex = 0;
    Vector colDataViewFields                                      = new Vector();
    FieldDTO objField;
    String strFieldName                                           = null;
    String strFieldAlias                                          = null;
    
    
    DetailedDataViewDTO dtoDetailedDataView                       = null;
    StringBuffer strSelectedDataViewScript                        = new StringBuffer();
    try
    {
        Collection objCollection = argSelectedDataViews.values();
        Iterator objIterator = objCollection.iterator();
        while(objIterator.hasNext())
        {
          dtoDetailedDataView = (DetailedDataViewDTO)objIterator.next();
          colDataViewFields = (Vector) dtoDetailedDataView.getDataViewFields();
          strSelectedDataViewScript.append(" var objSelectedDataView=new DataView('"+dtoDetailedDataView.getDataViewID()+"','"+dtoDetailedDataView.getDataViewName()+"','"+dtoDetailedDataView.getDataViewVersion()+"','"+dtoDetailedDataView.getDataViewDescription()+"');");
          strSelectedDataViewScript.append(" objSelectedDataView.m_nDataViewType = "+dtoDetailedDataView.getDataViewTypeID()+";");
          for (int i = 0; i <colDataViewFields.size() ; i++) 
          {
              objField = (FieldDTO)colDataViewFields.elementAt(i);
              /*if(objField.getFieldSQLCach() != null)
              {
                  nDotIndex     =  objField.getFieldSQLCach().indexOf('.');
                  strFieldName  = objField.getFieldSQLCach().substring(nDotIndex+1,objField.getFieldSQLCach().length());
                  strFieldAlias = objField.getFieldName();
              }    
              else
              {*/
                  strFieldName = objField.getFieldName();
                  strFieldAlias = "";
              //}
              
              argSelectedDataViewsFieldsSQLCach.add("\""+dtoDetailedDataView.getDataViewName()+"\".\""+strFieldName+"\"");
              argSelectedDataViewsTypes.add(new Integer(dtoDetailedDataView.getDataViewTypeID()));
              argSelectedDataViewsFields.add(objField);
              
              strSelectedDataViewScript.append(" var objDataViewField = new DataViewField('"+objField.getFieldID()+"','"+strFieldName+"','"+QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD+"','"+objField.getFieldDescription()+"','"+dtoDetailedDataView.getDataViewTypeID()+"');");
              //System.out.println("2 :" + objField.getFieldID());
              strSelectedDataViewScript.append(" objDataViewField.addFeatures('"+objField.getDataViewID()+"','"+dtoDetailedDataView.getDataViewName()+"',null,'"+strFieldAlias+"','"+i+"');");
              strSelectedDataViewScript.append(" objSelectedDataView.addField(objDataViewField);");
          }
          strSelectedDataViewScript.append(" objNewDataView.addSelectedDataView(objSelectedDataView);");
          
        }
    }
    catch(Exception objException)
    {
        objException.getMessage();
    }
    return strSelectedDataViewScript.toString();
}    

private String fillDataViewSelectedFields(Vector argDataViewFields,Vector argSelectedDataViewFieldsSQLCash,Vector argSelectedDataViewsTypes,Vector argDataViewConstants,Vector argDataViewFunctions,Vector argDataViewParameters) throws IOException
{
    int nDataViewType = -1;
    int nDotIndex   = 0;
    int nFieldIndex = 0;

    String strFieldName                                           = null;
    String strFieldAlias                                          = null;
    FieldDTO objField                                                = null;
    DataViewFieldDTO dtoDataViewField                             = null;
    
    StringBuffer strDataViewSelectedFieldsScript                  = new StringBuffer();
    try
    {
        for (int i = 0; i < argDataViewFields.size();i++) 
        {
          objField = (FieldDTO)argDataViewFields.elementAt(i);
          if(objField.getFieldType().getFieldTypeID() == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD)
          {
              if(objField.getFieldSQLCash() != null)
              {
                  nDotIndex =  objField.getFieldSQLCash().indexOf('.');
                  strFieldName = objField.getFieldSQLCash().substring(nDotIndex+2,objField.getFieldSQLCash().length()-1);
                  strFieldAlias = objField.getFieldName();
                  nFieldIndex = argSelectedDataViewFieldsSQLCash.indexOf(objField.getFieldSQLCash());
                  nDataViewType = ((Integer)argSelectedDataViewsTypes.elementAt(nFieldIndex)).intValue();
              }    
              else
              {
                  strFieldName = objField.getFieldName();
                  strFieldAlias = "ERROR";
//                  nDataViewType = QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE;
              }
              dtoDataViewField = (DataViewFieldDTO)objField;
              strDataViewSelectedFieldsScript.append(" var objSelectedField = new DataViewField('"+objField.getFieldID()+"','"+strFieldName+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"','"+nDataViewType+"');");
              //System.out.println("1 :" + objField.getFieldID());
//            strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objSelectedField); ");
              strDataViewSelectedFieldsScript.append(" objSelectedField.addFeatures('"+dtoDataViewField.getParentDataViewID()+"','"+dtoDataViewField.getParentDataViewName()+"',false,'"+strFieldAlias+"',"+nFieldIndex+");");
              strDataViewSelectedFieldsScript.append(" objNewDataView.m_arrDataViewFields[objNewDataView.m_arrDataViewFields.length] = objSelectedField;");
         }
         else
         {
              if(objField.getFieldType().getFieldTypeID() == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD)
              {  
                  strDataViewSelectedFieldsScript.append(" var objSelectedField = new DataViewField('"+objField.getFieldID()+"','"+objField.getFieldName()+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"');");
                  //System.out.println("3 :" + objField.getFieldID());
//                strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objSelectedField); ");
                  for (int p = 0; p <argDataViewConstants.size() ; p++) 
                  {
                        if(((ConstantFieldDTO)argDataViewConstants.elementAt(p)).getFieldID() == objField.getFieldID())
                        {
                            nFieldIndex = p;
                            break;
                        }
                  }
                  strFieldAlias = objField.getFieldName();
                  strDataViewSelectedFieldsScript.append(" objSelectedField.addFeatures(null,null,false,'"+strFieldAlias+"',"+nFieldIndex+");");
                  strDataViewSelectedFieldsScript.append(" objSelectedField.m_nFieldID = '"+nFieldIndex+"';");
                  strDataViewSelectedFieldsScript.append(" objNewDataView.m_arrDataViewFields[objNewDataView.m_arrDataViewFields.length] = objSelectedField;");
              }
              else if(objField.getFieldType().getFieldTypeID() == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD)
              {  
                  //waseem
                  strDataViewSelectedFieldsScript.append(" var objSelectedField = new DataViewField('"+objField.getFieldID()+"','"+objField.getFieldName()+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"');");
                  //System.out.println("3 :" + objField.getFieldID());
//                strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objSelectedField); ");
                  for (int p = 0; p <argDataViewParameters.size() ; p++) 
                  {
                        if(((ParameterFieldDTO)argDataViewParameters.elementAt(p)).getFieldID() == objField.getFieldID())
                        {
                            nFieldIndex = p;
                            break;
                        }
                  }
                  strFieldAlias = objField.getFieldName();
                  strDataViewSelectedFieldsScript.append(" objSelectedField.addFeatures(null,null,false,'"+strFieldAlias+"',"+nFieldIndex+");");
                  strDataViewSelectedFieldsScript.append(" objSelectedField.m_nFieldID = '"+nFieldIndex+"';");
                  strDataViewSelectedFieldsScript.append(" objNewDataView.m_arrDataViewFields[objNewDataView.m_arrDataViewFields.length] = objSelectedField;");
              
              }
              else
                  if(objField.getFieldType().getFieldTypeID() == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD)
                  {  
                      strDataViewSelectedFieldsScript.append(" var objSelectedField = new DataViewField('"+objField.getFieldID()+"','"+objField.getFieldName()+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"');");
//                    strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objSelectedField); ");
                      for (int q = 0; q <argDataViewFunctions.size() ; q++) 
                      {
                        if(((FunctionFieldDTO)argDataViewFunctions.elementAt(q)).getFieldID() == objField.getFieldID())
                        {
                            nFieldIndex = q;
                            break;
                        }
                      }
                      strFieldAlias = objField.getFieldName();                    
                      strDataViewSelectedFieldsScript.append(" objSelectedField.addFeatures(null,null,false,'"+strFieldAlias+"',"+nFieldIndex+");");
                      strDataViewSelectedFieldsScript.append(" objSelectedField.m_nFieldID = '"+nFieldIndex+"';");
                      strDataViewSelectedFieldsScript.append(" objNewDataView.m_arrDataViewFields[objNewDataView.m_arrDataViewFields.length] = objSelectedField;");
                  }
           }  
       }
    }
    catch(Exception objException)
    {
        
    }
    return strDataViewSelectedFieldsScript.toString();
}
private String fillDataViewConstants(Vector argDataViewConstants) throws IOException
{
    int nDataType;
    String strValue                                                = "";
    String strFieldSQLCash                                         = "";
    ConstantFieldDTO dtoConstantField                              = null;
    StringBuffer strDataViewConstantsScript                        = new StringBuffer();
    try
    {
        for (int i = 0; i < argDataViewConstants.size();i++) 
        {
           dtoConstantField = (ConstantFieldDTO)argDataViewConstants.elementAt(i);
           
           if(dtoConstantField.getFieldSQLCash().indexOf("to_date") != -1) 
           {
               nDataType = QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_DATE;
               strValue = dtoConstantField.getFieldSQLCash().substring(dtoConstantField.getFieldSQLCash().indexOf("'")+1,dtoConstantField.getFieldSQLCash().indexOf(",")-1);
           }    
           else
              if(dtoConstantField.getFieldSQLCash().indexOf("\'") != -1)
              {
                 nDataType = QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_TEXT;
                 strValue = dtoConstantField.getFieldSQLCash().substring(dtoConstantField.getFieldSQLCash().indexOf("\'")+1,dtoConstantField.getFieldSQLCash().length()-1);
              }   
              else
              {
                   nDataType = QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_NUMERIC;
                   strValue = dtoConstantField.getFieldSQLCash();
              }     
           strFieldSQLCash = replaceByString(dtoConstantField.getFieldSQLCash(),"","\'",strFieldSQLCash);

           strDataViewConstantsScript.append(" var objDataViewConstant = new DataViewConstant('"+dtoConstantField.getFieldID()+"','"+dtoConstantField.getFieldName()+"','"+nDataType+"','"+strValue+"','"+dtoConstantField.getFieldDescription()+"');");
           strFieldSQLCash = "";
           strValue = "";
           strDataViewConstantsScript.append(" objNewDataView.addConstant(objDataViewConstant); ");
        }
    }
    catch(Exception objException)
    {
        
    }
    return strDataViewConstantsScript.toString();
}    

private String fillDataViewParameters(Vector argDataViewParameters) throws IOException
{
    int nDataType;
    String strValue                                                  = "";
    String strFieldSQLCash                                           = "";
    ParameterFieldDTO dtoParameterField                              = null;
    StringBuffer strDataViewParametersScript                         = new StringBuffer();
    try
    {
        for (int i = 0; i < argDataViewParameters.size();i++) 
        {
           dtoParameterField = (ParameterFieldDTO)argDataViewParameters.elementAt(i);
           if(dtoParameterField.getFieldSQLCash().indexOf("to_date") != -1) 
           {
               nDataType = QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_DATE; 
               strValue = dtoParameterField.getFieldSQLCash().substring(dtoParameterField.getFieldSQLCash().indexOf("'")+1,dtoParameterField.getFieldSQLCash().indexOf(",")-1);
           }    
           else
              if(dtoParameterField.getFieldSQLCash().indexOf("\'") != -1)
              {
                 nDataType = QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_TEXT;
                 strValue = dtoParameterField.getFieldSQLCash().substring(dtoParameterField.getFieldSQLCash().indexOf("\'")+1,dtoParameterField.getFieldSQLCash().length()-1);
              }   
              else
              {
                   nDataType = QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_NUMERIC; 
                   strValue = dtoParameterField.getFieldSQLCash();
              } 
           strFieldSQLCash = replaceByString(dtoParameterField.getFieldSQLCash(),"","\'",strFieldSQLCash);

           strDataViewParametersScript.append(" var objDataViewParameter = new DataViewParameter('"+dtoParameterField.getFieldID()+"','"+dtoParameterField.getFieldName()+"','"+dtoParameterField.getLabelText()+"','"+dtoParameterField.getControlTypeID()+"','"+nDataType+"','"+strValue+"','"+dtoParameterField.getFieldDescription()+"');");
           strDataViewParametersScript.append(" objDataViewParameter.m_nParameterIndex = "+i+";");
           strFieldSQLCash = "";
           strValue        = "";
           strDataViewParametersScript.append(" objNewDataView.addInputParameter(objDataViewParameter); ");
        }
    }
    catch(Exception objException)
    {
        
    }
    return strDataViewParametersScript.toString();
}    

private String fillDataViewFunctions(Vector argDataViewFunctions,Vector argSelectedDataViewFieldsSQLCash,Vector argSelectedDataViewFields,Vector argSelectedDataViewTypes,Vector argDataViewConstants,Vector argFunctionTypes,HashMap argFunctionsList,Vector argDataViewParameters) throws IOException
{
    int nFunctionIndex                                             = 0;
    int nFunctionTypeIndex                                         = 0;
    int nParameterIndex                                            = 0;
    int nParameterPlace                                            = 0;
    int nDotIndex                                                  = 0;
    int nDataViewType                                              = 0;
        
    String strConstantValue                                        = ""; 
    String strParameterValue                                        = "";
    String strDataViewName                                         = ""; 
    String strFieldName                                            = ""; 
    String strFieldAlias                                           = ""; 
    
    String strFunctionSQLCash1                                      = ""; 
    String strFunctionSQLCash2                                      = ""; 
    String strFunctionDesc1                                         = ""; 
    String strFunctionDesc2                                         = ""; 
    
    FunctionFieldDTO dtoFunctionField                              = null;

    FieldDTO dtoField;
    ConstantFieldDTO dtoConstantField;
    ParameterFieldDTO dtoParameterField;
    FunctionFieldDTO dtoDataViewFunctionField                      = null;
    FunctionDTO dtoFunction                                        = null;
    FunctionParameterValueModel modFunctionParameterValue;
    FunctionParameterDefinitionModel modFunctionParameterDefinition;
    Vector colFunctionsList;
    Vector colParameters;
    Vector colFunctionParametersDefinition;
    StringBuffer strDataViewFunctionsScript                        = new StringBuffer();
    
    try
    {
        for (int i = 0; i < argDataViewFunctions.size();i++) 
        {
             dtoFunctionField = (FunctionFieldDTO)argDataViewFunctions.elementAt(i);
             dtoFunction = dtoFunctionField.getFunctionDTO();
             colFunctionParametersDefinition = dtoFunction.getParameterDefinitions();             
             colParameters = dtoFunctionField.getFunctionParameterValues();
             
             colFunctionsList = (Vector)argFunctionsList.get(new Integer(dtoFunction.getFunctionTypeID()));
             nFunctionIndex = colFunctionsList.indexOf(new Integer(dtoFunction.getFunctionID()));   
             nFunctionTypeIndex = argFunctionTypes.indexOf(new Integer(dtoFunction.getFunctionTypeID()));   
             
             strFunctionDesc1 = replaceByString(dtoFunction.getFunctionDescription(),"\\\'","\'",strFunctionDesc1);

             strFunctionDesc2 = replaceByString(strFunctionDesc1,"\\\"","\"",strFunctionDesc2);

             
             strFunctionSQLCash1 = replaceByString(dtoFunctionField.getFieldSQLCash(),"\\\'","\'",strFunctionSQLCash1);

             strFunctionSQLCash2 = replaceByString(strFunctionSQLCash1,"\\\"","\"",strFunctionSQLCash2);

             
           
              
                           
             //strDataViewFunctionsScript.append(" var objDataViewFunction  = new DataViewFunction('"+dtoFunction.getFunctionID()+"','"+dtoFunction.getFunctionName()+"','"+dtoFunction.getFunctionSQL()+"','"+dtoFunction.getFunctionTypeID()+"',"+nFunctionIndex+",'"+strFunctionSQLCash2+"','"+strFunctionDesc2+"');");             
             strDataViewFunctionsScript.append(" var objDataViewFunction  = new DataViewFunction('"+dtoFunction.getFunctionID()+"','"+dtoFunctionField.getFieldName()+"','"+dtoFunction.getFunctionSQL()+"','"+dtoFunction.getFunctionTypeID()+"',"+nFunctionIndex+",'"+strFunctionSQLCash2+"','"+strFunctionDesc2+"');");
             strDataViewFunctionsScript.append(" objDataViewFunction.m_nFunctionTypeIndex = "+nFunctionTypeIndex+"; ");
             strDataViewFunctionsScript.append(" objNewDataView.addFunction(objDataViewFunction); ");
//             strDataViewFunctionsScript.append(" var strFunction = new String('"+dtoFunction.getFunctionName()+"'); ");
//             strDataViewFunctionsScript.append(" strFunction += '('; ");
             strFunctionSQLCash1 = "";
             strFunctionDesc1    = "";
             strFunctionSQLCash2 = "";
             strFunctionDesc2    = "";
             strDataViewFunctionsScript.append(" var arrParameterValues = new Array(); ");
             for (int s = 0; s < colParameters.size(); s++) 
             {
                 modFunctionParameterValue = (FunctionParameterValueModel)colParameters.elementAt(s);   
                 nParameterPlace = modFunctionParameterValue.getParameterValuePlace();

                  //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%");
                  //System.out.println(modFunctionParameterValue.getParameterValueFieldType());
                  //System.out.println(modFunctionParameterValue.getParameterValueSQLCache());
                 // System.out.println(modFunctionParameterValue.getParameterValueID());
                 // System.out.println("%%%%%%%%%%%%%%%%%%%%%%%");
                  
                  if(modFunctionParameterValue.getParameterValueFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD)
                 {
                        modFunctionParameterDefinition = (FunctionParameterDefinitionModel)colFunctionParametersDefinition.elementAt(0);       
                        if(modFunctionParameterDefinition.getParameterDefinitionIsList() == 1)
                        {
                            strDataViewFunctionsScript.append(" var objParameterValue = new FunctionParameterValue("+modFunctionParameterDefinition.getParameterDefinitionID()+","+modFunctionParameterValue.getParameterValueFieldType()+"); ");
                        }
                        else
                        {
                            modFunctionParameterDefinition = (FunctionParameterDefinitionModel)colFunctionParametersDefinition.elementAt(nParameterPlace);       
                            strDataViewFunctionsScript.append(" var objParameterValue = new FunctionParameterValue("+modFunctionParameterDefinition.getParameterDefinitionID()+","+modFunctionParameterValue.getParameterValueFieldType()+"); ");
                        }


                        for (int m = 0; m <argDataViewConstants.size() ; m++) 
                        {
                            dtoConstantField = (ConstantFieldDTO)argDataViewConstants.elementAt(m);
                            if(dtoConstantField.getFieldID() == modFunctionParameterValue.getParameterValueID())                           
                      //hagry quick fix please try to fix that and not leave it work this way it should return to yu the reference value
                            //if(dtoConstantField.getFieldSQLCash().compareTo(modFunctionParameterValue.getParameterValueSQLCache())==0)
                            {
                                nParameterIndex = m;
                                break;
                            }    
                        }

                        
                        strConstantValue = replaceByString(((ConstantFieldDTO)argDataViewConstants.elementAt(nParameterIndex)).getFieldSQLCash(),"","\'",strConstantValue);

                        strDataViewFunctionsScript.append(" objParameterValue.addOtherParameterData("+nParameterIndex+",'"+strConstantValue+"'); ");

                  }
                  else  if(modFunctionParameterValue.getParameterValueFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD)
                 {
                        //waseem
                        modFunctionParameterDefinition = (FunctionParameterDefinitionModel)colFunctionParametersDefinition.elementAt(0);       
                        if(modFunctionParameterDefinition.getParameterDefinitionIsList() == 1)
                        {
                            strDataViewFunctionsScript.append(" var objParameterValue = new FunctionParameterValue("+modFunctionParameterDefinition.getParameterDefinitionID()+","+modFunctionParameterValue.getParameterValueFieldType()+"); ");
                        }
                        else
                        {
                            modFunctionParameterDefinition = (FunctionParameterDefinitionModel)colFunctionParametersDefinition.elementAt(nParameterPlace);       
                            strDataViewFunctionsScript.append(" var objParameterValue = new FunctionParameterValue("+modFunctionParameterDefinition.getParameterDefinitionID()+","+modFunctionParameterValue.getParameterValueFieldType()+"); ");
                        }


                        for (int m = 0; m <argDataViewParameters.size() ; m++) 
                        {
                            dtoParameterField = (ParameterFieldDTO)argDataViewParameters.elementAt(m);
                            if(dtoParameterField.getFieldID() == modFunctionParameterValue.getParameterValueID())                           
                      //hagry quick fix please try to fix that and not leave it work this way it should return to yu the reference value
                            //if(dtoConstantField.getFieldSQLCash().compareTo(modFunctionParameterValue.getParameterValueSQLCache())==0)
                            {
                                nParameterIndex = m;
                                break;
                            }    
                        }

                        
                        strParameterValue = replaceByString(((ParameterFieldDTO)argDataViewParameters.elementAt(nParameterIndex)).getFieldSQLCash(),"","\'",strParameterValue);

                        strDataViewFunctionsScript.append(" objParameterValue.addOtherParameterData("+nParameterIndex+",'"+strParameterValue+"'); ");

                  }
                  else
                  {

                     if(modFunctionParameterValue.getParameterValueFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD)
                     {
                        modFunctionParameterDefinition = (FunctionParameterDefinitionModel)colFunctionParametersDefinition.elementAt(0);       
                        if(modFunctionParameterDefinition.getParameterDefinitionIsList() == 1)
                        {
                            strDataViewFunctionsScript.append(" var objParameterValue = new FunctionParameterValue("+modFunctionParameterDefinition.getParameterDefinitionID()+","+modFunctionParameterValue.getParameterValueFieldType()+"); ");
                        }
                        else
                        {
                            modFunctionParameterDefinition = (FunctionParameterDefinitionModel)colFunctionParametersDefinition.elementAt(nParameterPlace);       
                            strDataViewFunctionsScript.append(" var objParameterValue = new FunctionParameterValue("+modFunctionParameterDefinition.getParameterDefinitionID()+","+modFunctionParameterValue.getParameterValueFieldType()+"); ");
                        }
                        nParameterIndex = argSelectedDataViewFieldsSQLCash.indexOf(modFunctionParameterValue.getParameterValueSQLCache());
                        dtoField = (FieldDTO)argSelectedDataViewFields.elementAt(nParameterIndex);
                        if(modFunctionParameterValue.getParameterValueSQLCache() != null)
                        {
                            nDotIndex =  modFunctionParameterValue.getParameterValueSQLCache().indexOf('.');
                            strDataViewName = modFunctionParameterValue.getParameterValueSQLCache().substring(1,nDotIndex-1);
                            strFieldName = dtoField.getFieldName();
                            strFieldAlias = dtoField.getFieldName();
                            nDataViewType = ((Integer)argSelectedDataViewTypes.elementAt(nParameterIndex)).intValue();
                        }    
                        else
                        {
                            strFieldName = dtoField.getFieldName();
                            strFieldAlias = "ERROR";
                        }
                        
                        strDataViewFunctionsScript.append(" var objOperandField = new DataViewField('"+dtoField.getFieldID()+"','"+strFieldName+"','"+dtoField.getFieldType().getFieldTypeID()+"','"+dtoField.getFieldDescription()+"','"+nDataViewType+"');");
                        //System.out.println("4 :" + dtoField.getFieldID());
                        strDataViewFunctionsScript.append(" objOperandField.addFeatures('"+dtoField.getDataViewID()+"','"+strDataViewName+"',false,'"+strFieldAlias+"',"+nParameterIndex+");");
                        strDataViewFunctionsScript.append(" objParameterValue.addFieldParameterData(objOperandField); ");
//                       strDataViewFunctionsScript.append(" strFunction += '"+modFunctionParameterValue.getParameterValueSQLCache().substring(1,modFunctionParameterValue.getParameterValueSQLCache().length()-1)+"'; ");
                    }
                    else
                    {
//commented by hagry cause of latest changing on the server side where we send a shadow 
//if(modFunctionParameterValue.getParameterValueFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD)
/*
i made the or between teh two different types since some time the server send it this way when it is saved from display 
*/
 if(modFunctionParameterValue.getParameterValueFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_RF_FIELD || modFunctionParameterValue.getParameterValueFieldType() == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD)

                       {
                          modFunctionParameterDefinition = (FunctionParameterDefinitionModel)colFunctionParametersDefinition.elementAt(0);
                          /*
                          here we check for the filed type to be equal to the function rf 
                          since we changed on the server and make send rf data 
                          however teh rest of the script depedns on the fact that this is a function field not an rf 
                          that is why i hardcoded the type value in both append functions to be QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD
                          */
                          
                          if(modFunctionParameterDefinition.getParameterDefinitionIsList() == 1)
                          {
                            strDataViewFunctionsScript.append(" var objParameterValue = new FunctionParameterValue("+modFunctionParameterDefinition.getParameterDefinitionID()+","+QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD+"); ");
                          }
                          else
                          {
                              modFunctionParameterDefinition = (FunctionParameterDefinitionModel)colFunctionParametersDefinition.elementAt(nParameterPlace);       
                              strDataViewFunctionsScript.append(" var objParameterValue = new FunctionParameterValue("+modFunctionParameterDefinition.getParameterDefinitionID()+","+QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD+"); ");
                          }

                           //System.out.println("check0 " + dtoDataViewFunctionField.getFieldName());
                          //System.out.println("function param available for selection");

                          /*
                          for (int k = 0; k <argDataViewFunctions.size() ; k++) 
                          {
                              dtoDataViewFunctionField = (FunctionFieldDTO)argDataViewFunctions.elementAt(k);                                                   
                          }
                          */
                          nParameterIndex =-1;
                          for (int k = 0; k <argDataViewFunctions.size() ; k++) 
                          {
                              dtoDataViewFunctionField = (FunctionFieldDTO)argDataViewFunctions.elementAt(k);
                                                                                        
                              if(dtoDataViewFunctionField.getFieldID() == modFunctionParameterValue.getParameterValueID())                                                      
                              {
                                nParameterIndex = k;  
                                break;
                              }  
                          }
                          //System.out.println("nParameterIndex = " + nParameterIndex);
                          strDataViewFunctionsScript.append(" objParameterValue.addOtherParameterData("+nParameterIndex+",null); ");
//                        strDataViewFunctionsScript.append(" strFunction += '"+dtoDataViewFunctionField.getFieldSQLCash()+"';" );
                      }
                   }
               }

               
               strDataViewFunctionsScript.append(" arrParameterValues[Number("+nParameterPlace+")] = objParameterValue; ");    
           }    
           strDataViewFunctionsScript.append(" objNewDataView.m_arrDataViewFunctionParameters[Number("+i+")] = arrParameterValues; ");    
        }   
    }
    catch(Exception objException)
    {
        
    }
//    strDataViewFunctionsScript.append(" objNewDataView.m_arrDataViewFunctionParameters = arrParameterValues; ");    
    return strDataViewFunctionsScript.toString();
}    

private String fillDataViewTerms(Vector argDataViewTerms,Vector argSelectedDataViewFieldsSQLCash,Vector argSelectedDataViewFields,Vector argSelectedDataViewTypes,Vector argDataViewConstants,Vector argDataViewFunctions,Vector argDataViewParameters,Vector argFTFOperators,Vector argFDVOperators,Vector argAllDataViews) throws IOException
{
    int nFieldIndex                                            = -1;
    String strTermSQL1                                         = ""; 
    String strTermSQL2                                         = ""; 
    String strTermSQL3                                         = ""; 
    StringBuffer strTermSQLCach                                = new StringBuffer(); 
    Term objTerm                                               = null;
    FieldDTO objField;
    DataViewFieldDTO dtoDataViewField;
    FieldToFieldTermDTO dtoFTFTerm;
    FieldtoDataViewTermDTO dtoFDVTerm;
    StringBuffer strDataViewTermsScript                        = new StringBuffer();
    try
    {
        for (int i = 0; i < argDataViewTerms.size();i++) 
        {
           //System.out.println("jsp term i = " + i);
           
             objTerm = (Term)argDataViewTerms.elementAt(i);


             
             strDataViewTermsScript.append(" var objTerm = new DataViewTerm('"+objTerm.getTermID()+"','"+objTerm.getTermSQLCache()+"','"+objTerm.getTermType().getTermTypeID()+"',"+i+");");
             
             if(objTerm.getTermType().getTermTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE)
             {
                  dtoFTFTerm  = (FieldToFieldTermDTO)objTerm;
                  //System.out.println("jsp term1  field id = " + dtoFTFTerm.get1stOperandFieldID());
                  strDataViewTermsScript.append(fillTermFieldOperand(dtoFTFTerm.get1stOperandFieldID(),dtoFTFTerm.get1stOperandFieldSQLCache(),dtoFTFTerm.get1stOperandFieldType(),argSelectedDataViewFieldsSQLCash,argSelectedDataViewFields,argSelectedDataViewTypes,argDataViewConstants,argDataViewFunctions,argDataViewParameters));
                  strDataViewTermsScript.append(" objTerm.m_obj1stOperand = objOperandField;");
                  strDataViewTermsScript.append(" objTerm.m_nOperatorIndex = "+argFTFOperators.indexOf(new Integer(dtoFTFTerm.getOperatorID()))+"; ");
                  strDataViewTermsScript.append(fillTermFieldOperand(dtoFTFTerm.get2ndOperandFieldID(),dtoFTFTerm.get2ndOperandFieldSQLCache(),dtoFTFTerm.get2ndOperandFieldType(),argSelectedDataViewFieldsSQLCash,argSelectedDataViewFields,argSelectedDataViewTypes,argDataViewConstants,argDataViewFunctions,argDataViewParameters));
                  strDataViewTermsScript.append(" objTerm.m_obj2ndOperand = objOperandField;");
                  strTermSQL1 = replaceByString(dtoFTFTerm.get1stOperandFieldSQLCache(),"","\'",strTermSQL1);
                  strTermSQL2 = replaceByString(strTermSQL1,"\\\"","\"",strTermSQL2);
                  strTermSQL3 = replaceByString(strTermSQL2,"","\n",strTermSQL3);
                  strTermSQLCach.append(strTermSQL3);
                  strTermSQLCach.append(dtoFTFTerm.getOperatorSQL());
                  strTermSQL1 = "";
                  strTermSQL2 = "";
                  strTermSQL3 = "";
                  strTermSQL1 = replaceByString(dtoFTFTerm.get2ndOperandFieldSQLCache(),"","\'",strTermSQL1);
                  strTermSQL2 = replaceByString(strTermSQL1,"\\\"","\"",strTermSQL2);
                  strTermSQL3 = replaceByString(strTermSQL2,"","\n",strTermSQL3);
                  strTermSQLCach.append(strTermSQL3);
                  strDataViewTermsScript.append(" objTerm.m_strTermSQL = '"+strTermSQLCach+"'; ");
                  strTermSQL1 = "";
                  strTermSQL2 = "";
                  strTermSQL3 = "";
                  strTermSQLCach.delete(0,strTermSQLCach.length());
            }
            else
            {
                if(objTerm.getTermType().getTermTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE)
                {
                    dtoFDVTerm  = (FieldtoDataViewTermDTO)objTerm;   
                    strDataViewTermsScript.append(fillTermFieldOperand(dtoFDVTerm.get1stOperandFieldID(),dtoFDVTerm.get1stOperandFieldSQLCache(),dtoFDVTerm.get1stOperandFieldType(),argSelectedDataViewFieldsSQLCash,argSelectedDataViewFields,argSelectedDataViewTypes,argDataViewConstants,argDataViewFunctions,argDataViewParameters));
                    strDataViewTermsScript.append(" objTerm.m_obj1stOperand = objOperandField;");
                    strDataViewTermsScript.append(" objTerm.m_nOperatorIndex = "+argFDVOperators.indexOf(new Integer(dtoFDVTerm.getOperatorID()))+"; ");
                    strDataViewTermsScript.append(fillTermDataViewOperand(dtoFDVTerm.getRelatedDataViewID(),dtoFDVTerm.getRelatedDataViewName(),dtoFDVTerm.getRelatedDataViewTypeID(),argAllDataViews));
                    strDataViewTermsScript.append(" objTerm.m_obj2ndOperand = objOperandDataView;");
                    strTermSQL1 = replaceByString(dtoFDVTerm.get1stOperandFieldSQLCache(),"","\'",strTermSQL1);
                    strTermSQL2 = replaceByString(strTermSQL1,"\\\"","\"",strTermSQL2);
                    strTermSQL3 = replaceByString(strTermSQL2,"","\n",strTermSQL3);
                    strTermSQLCach.append(strTermSQL3);
                    strTermSQLCach.append(dtoFDVTerm.getOperatorSQL());
                    strTermSQL1 = "";
                    strTermSQL2 = "";
                    strTermSQL3 = "";
                    strTermSQL1 = replaceByString(dtoFDVTerm.getRelatedDataViewSQL(),"","\'",strTermSQL1);
                    strTermSQL2 = replaceByString(strTermSQL1,"\\\"","\"",strTermSQL2);
                    strTermSQL3 = replaceByString(strTermSQL2,"","\n",strTermSQL3);
                    strTermSQLCach.append(strTermSQL3);
                    strDataViewTermsScript.append(" objTerm.m_strTermSQL = '"+strTermSQLCach+"'; ");
                    strTermSQL1 = "";
                    strTermSQL2 = "";
                    strTermSQL3 = "";
                    strTermSQLCach.delete(0,strTermSQLCach.length());
                 }   
            }
            strDataViewTermsScript.append(" objNewDataView.addTerm(objTerm); ");
         }   
   }
   catch(Exception objException)
   {
       
   }
   return strDataViewTermsScript.toString();
}

private String fillTermFieldOperand(int argFieldID,String argFieldSQLCash,int argFieldType,Vector argSelectedDataViewFieldsSQLCash,Vector argSelectedDataViewFields,Vector argSelectedDataViewTypes,Vector argDataViewConstants,Vector argDataViewFunctions,Vector argDataViewParameters)
{
    int nDotIndex                               = 0;
    int nDataViewType                           = 0;
    int nFieldIndex                             = 0;
    String strFieldName                         = "";
    String strDataViewName                                        = null;
    String strFieldAlias                        = "";
    
    StringBuffer strTermOperandScript           = new StringBuffer();
    FieldDTO objField                              = null;
    DataViewFieldDTO dtoDataViewField;

    switch(argFieldType)
    {
        case QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD:
        {
         //System.out.println(" term _ dataview _field ");
         //System.out.println(" argFieldSQLCash = "+ argFieldSQLCash);

         nFieldIndex = argSelectedDataViewFieldsSQLCash.indexOf(argFieldSQLCash);

        if (nFieldIndex==-1)
        {
            /*
            this code was done to fix the bug that was discoverd that loading the term when it is join term 
            left or right dont work 
            and that is due to that we save the (+) besdie the operand in the sql cash field of the term
            which result that the term can't be found among the field list since an exact match of the sql 
            is not there
            so this code is to remove the (+) and search in the vector of fields once again 
            
            */
        
            //System.out.println(argFieldSQLCash );
            int index = argFieldSQLCash.indexOf("(+)");
            argFieldSQLCash= argFieldSQLCash.substring(0,index);
            //System.out.println(argFieldSQLCash );
            argFieldSQLCash = argFieldSQLCash.trim();

           /* System.out.println("dump of argSelectedDataViewFieldsSQLCash ");
            for (int i =0; i < argSelectedDataViewFieldsSQLCash.size();i++)
            {
            System.out.println((String)argSelectedDataViewFieldsSQLCash.get(i));
            }
            System.out.println("end of dump");
            */
          }  

            nFieldIndex = argSelectedDataViewFieldsSQLCash.indexOf(argFieldSQLCash);
            
            //System.out.println("nfield index = " + nFieldIndex);
            objField = (FieldDTO)argSelectedDataViewFields.elementAt(nFieldIndex);

            //System.out.println("objField " + objField.getFieldID());

            if(argFieldSQLCash != null)
            {
                nDotIndex =  argFieldSQLCash.indexOf('.');
                strFieldName = argFieldSQLCash.substring(nDotIndex+2,argFieldSQLCash.length()-1);
                strDataViewName = argFieldSQLCash.substring(1,nDotIndex-1);
                strFieldAlias = objField.getFieldName();
                nDataViewType = ((Integer)argSelectedDataViewTypes.elementAt(nFieldIndex)).intValue();
            }    
            else
            {
                strFieldName = objField.getFieldName();
                strFieldAlias = "ERROR";
//                nDataViewType = QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE;
            }
            //System.out.println("after the if statement");
            //System.out.println(objField.getClass().getName());
            dtoDataViewField = null;

            if (objField instanceof DataViewFieldDTO)
            {
              dtoDataViewField = (DataViewFieldDTO)objField;
              strTermOperandScript.append(" var objOperandField = new DataViewField('"+objField.getFieldID()+"','"+strFieldName+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"','"+nDataViewType+"');");
              strTermOperandScript.append(" objOperandField.addFeatures('"+dtoDataViewField.getParentDataViewID()+"','"+strDataViewName+"',false,'"+strFieldAlias+"',"+nFieldIndex+");");
            }
            else if (objField instanceof FunctionFieldDTO)
            {
              FunctionFieldDTO funcField = (FunctionFieldDTO) objField;
              strTermOperandScript.append(" var objOperandField = new DataViewField('"+objField.getFieldID()+"','"+objField.getFieldName()+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"');");
              strTermOperandScript.append("alert (objOperandField );");
              //hagry here is the problem of the operand                                
              strTermOperandScript.append(" objOperandField.addFeatures(null,null,false,null,"+nFieldIndex+");");
              strTermOperandScript.append(" objOperandField.m_nFieldID = "+nFieldIndex+";");
            }
            
            
            
           // System.out.println("5: " + objField.getFieldID());
    //            strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objOperandField); ");
//            strTermOperandScript.append(" objNewDataView.m_arrDataViewFields[objNewDataView.m_arrDataViewFields.length] = objOperandField;");
        }  
        break;
        case QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD:
        {
            for (int i = 0; i <argDataViewConstants.size() ; i++) 
            {
                objField = (FieldDTO)argDataViewConstants.elementAt(i);
                if(objField.getFieldID() == argFieldID)
                {
                  nFieldIndex = i;
                  break;
                }  
            }
            strTermOperandScript.append(" var objOperandField = new DataViewField('"+objField.getFieldID()+"','"+objField.getFieldName()+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"');");
            //System.out.println("7 : " + objField.getFieldID());
//                strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objOperandField); ");
//            nFieldIndex = argDataViewConstants.indexOf(objField);
            strTermOperandScript.append(" objOperandField.addFeatures(null,null,false,null,"+nFieldIndex+");");
            strTermOperandScript.append(" objOperandField.m_nFieldID = "+nFieldIndex+";");
//            strTermOperandScript.append(" objNewDataView.m_arrDataViewFields[objNewDataView.m_arrDataViewFields.length] = objOperandField;");
        }  
        break;
        case QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD:
        {
            for (int i = 0; i <argDataViewFunctions.size() ; i++) 
            {
                objField = (FieldDTO)argDataViewFunctions.elementAt(i);
                if(objField.getFieldID() == argFieldID)
                {
                    nFieldIndex = i;
                    break;
                }    
            }
            strTermOperandScript.append(" var objOperandField = new DataViewField('"+objField.getFieldID()+"','"+objField.getFieldName()+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"');");
            //System.out.println("8: " + objField.getFieldID());
//          strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objOperandField); ");
            strTermOperandScript.append(" objOperandField.addFeatures(null,null,false,null,"+nFieldIndex+");");
            strTermOperandScript.append(" objOperandField.m_nFieldID = "+nFieldIndex+";");
//            strTermOperandScript.append(" objNewDataView.m_arrDataViewFields[objNewDataView.m_arrDataViewFields.length] = objOperandField;");
        }  
        break;
        case QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD:
        {
            for (int i = 0; i <argDataViewParameters.size() ; i++) 
            {
                objField = (FieldDTO)argDataViewParameters.elementAt(i);
                if(objField.getFieldID() == argFieldID)
                {
                  nFieldIndex = i;
                  break;
                }  
            }
            strTermOperandScript.append(" var objOperandField = new DataViewField('"+objField.getFieldID()+"','"+objField.getFieldName()+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"');");
            //System.out.println("9:" + objField.getFieldID());
//                strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objOperandField); ");
//            nFieldIndex = argDataViewConstants.indexOf(objField);
            strTermOperandScript.append(" objOperandField.addFeatures(null,null,false,null,"+nFieldIndex+");");
            strTermOperandScript.append(" objOperandField.m_nFieldID = "+nFieldIndex+";");
//            strTermOperandScript.append(" objNewDataView.m_arrDataViewFields[objNewDataView.m_arrDataViewFields.length] = objOperandField;");
        }  
        break;
        default:
          break;
    }
    return strTermOperandScript.toString();
}

private String fillTermDataViewOperand(int argDataViewID,String argDataViewName,int argDataViewType,Vector argAllDataViews)
{
    int nDataViewIndex                          = 0;
    StringBuffer strTermOperandScript           = new StringBuffer();
    
    nDataViewIndex = argAllDataViews.indexOf(new Integer(argDataViewID));
    nDataViewIndex++;
    strTermOperandScript.append(" var objOperandDataView = new DataView('"+argDataViewID+"','"+argDataViewName+"',null,null,'"+argDataViewType+"'); ");//  ,nDataViewVersion,strDataViewDescription);
    strTermOperandScript.append(" objOperandDataView.m_nDataViewIndex = "+nDataViewIndex+";");
    return strTermOperandScript.toString();
}

private String fillDataViewWhere(QueryWhereDTO argQueryWhere,Vector argDataViewTerms,Vector argRelationSymbols) throws IOException
{
    int nElementIndex                                     = 0;
    Vector colConditionElements;
    ConditionElement objConditionElement;
    RelationSymbolDTO dtoRelationSymbol;
    Term objTerm;
    colConditionElements                                  = argQueryWhere.getConditionElements();
    StringBuffer strWhereScript                           = new StringBuffer();
    try
    {
        for(int i=0;i<colConditionElements.size();i++)
        {
          objConditionElement = (ConditionElement)colConditionElements.elementAt(i);
          if(objConditionElement.getConditionElementType().getConditionElementTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM)
          {
              objTerm = (Term)objConditionElement; 
              nElementIndex = argDataViewTerms.indexOf(objTerm);
    //          strWhereScript.append(" var objTerm = objNewDataView.m_arrDataViewTerms["+nElementIndex+"]; ");
              strWhereScript.append(" var objWhereElement = new WhereElement("+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM+"); ");
              strWhereScript.append(" objWhereElement.m_nElementIndex = '"+nElementIndex+"'; ");
              strWhereScript.append(" objNewDataView.addWhereElement(objWhereElement); ");
          }
          else
          {
              if(objConditionElement.getConditionElementType().getConditionElementTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL)  
              {
                  dtoRelationSymbol = (RelationSymbolDTO)objConditionElement; 
                  for (int p = 0; p < argRelationSymbols.size(); p++) 
                  {
                      if(((RelationSymbolDTO)argRelationSymbols.elementAt(p)).getRelationSymbolID() == dtoRelationSymbol.getRelationSymbolID())
                      {
                          nElementIndex = p;
                          break;
                      }
                  }
                  
//                  nElementIndex = argRelationSymbols.indexOf(dtoRelationSymbol);
                  strWhereScript.append(" var objRelationSymbol = objDataViewWizard.m_arrDataViewRelationSymbols["+nElementIndex+"]; ");
                  strWhereScript.append(" var objWhereElement = new WhereElement("+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL+"); ");
                  strWhereScript.append(" objWhereElement.m_nRelationSymbolID = "+dtoRelationSymbol.getRelationSymbolID()+"; ");
                  strWhereScript.append(" objWhereElement.m_strRelationSymbolSQL = '"+dtoRelationSymbol.getRelationSymbolSQL()+"'; ");
                  strWhereScript.append(" objWhereElement.m_nElementIndex = "+nElementIndex+"; ");
                  strWhereScript.append(" objNewDataView.addWhereElement(objWhereElement); ");
               }
          }
       }
   }
   catch(Exception objException)
   {
       
   }
   return strWhereScript.toString();
}

private String fillDataViewGroupBy(Vector argDataViewGroupBy,Vector argSelectedDataViewFieldsSQLCash,Vector argSelectedDataViewsTypes,Vector argDataViewConstants,Vector argDataViewFunctions)
{
    int nDataViewType = -1;
    int nDotIndex   = 0;
    int nFieldIndex = 0;

    String strFieldName                                           = null;
    String strDataViewName                                        = null;
    String strFieldAlias                                          = null;
    FieldDTO objField                                                = null;
    DataViewFieldDTO dtoDataViewField                             = null;
    
    StringBuffer strDataViewGroupByScript                  = new StringBuffer();
    try
    {
        for (int i = 0; i < argDataViewGroupBy.size();i++) 
        {
          objField = (FieldDTO)argDataViewGroupBy.elementAt(i);
          if(objField.getFieldType().getFieldTypeID() == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD)
          {
              if(objField.getFieldSQLCash() != null)
              {
                  nDotIndex       = objField.getFieldSQLCash().indexOf('.');
                  strFieldName    = objField.getFieldSQLCash().substring(nDotIndex+2,objField.getFieldSQLCash().length()-1);
                  strDataViewName = objField.getFieldSQLCash().substring(1,nDotIndex-1);
                  strFieldAlias   = objField.getFieldName();
                  nFieldIndex = argSelectedDataViewFieldsSQLCash.indexOf(objField.getFieldSQLCash());
                  nDataViewType = ((Integer)argSelectedDataViewsTypes.elementAt(nFieldIndex)).intValue();
              }    
              else
              {
                  strFieldName = objField.getFieldName();
                  strFieldAlias = "ERROR";
//                  nDataViewType = QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE;
              }
              dtoDataViewField = (DataViewFieldDTO)objField;
              strDataViewGroupByScript.append(" var objSelectedField = new DataViewField('"+objField.getFieldID()+"','"+strFieldName+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"','"+nDataViewType+"');");
              
//            strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objSelectedField); ");
              strDataViewGroupByScript.append(" objSelectedField.addFeatures('"+dtoDataViewField.getParentDataViewID()+"','"+strDataViewName+"',false,'"+strFieldAlias+"',"+nFieldIndex+");");
              strDataViewGroupByScript.append(" var objGroupBy = new DataViewGroupBy("+objField.getFieldID()+","+objField.getFieldType().getFieldTypeID()+");");
              strDataViewGroupByScript.append(" objGroupBy.m_objGroupByField = objSelectedField; ");
              strDataViewGroupByScript.append(" objGroupBy.m_nGroupByIndex = "+nFieldIndex+"; ");
              strDataViewGroupByScript.append(" objNewDataView.addGroupBy(objGroupBy); ");
         }
        /* else
         {
              if(objField.getFieldType().getFieldTypeID() == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD)
              {  
                  strDataViewSelectedFieldsScript.append(" var objSelectedField = new DataViewField('"+objField.getFieldID()+"','"+objField.getFieldName()+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"');");
//                strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objSelectedField); ");
                  for (int p = 0; p <argDataViewConstants.size() ; p++) 
                  {
                        if(((ConstantFieldDTO)argDataViewConstants.elementAt(p)).getFieldID() == objField.getFieldID())
                        {
                            nFieldIndex = p;
                            break;
                        }
                  }
                  strFieldAlias = objField.getFieldName();
                  strDataViewSelectedFieldsScript.append(" objSelectedField.addFeatures(null,null,false,'"+strFieldAlias+"',"+nFieldIndex+");");
                  strDataViewSelectedFieldsScript.append(" objSelectedField.m_nFieldIndex = '"+nFieldIndex+"';");
                  strDataViewSelectedFieldsScript.append(" objNewDataView.m_arrDataViewFields[objNewDataView.m_arrDataViewFields.length] = objSelectedField;");
              }*/
         else
          if(objField.getFieldType().getFieldTypeID() == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD)
          {  
              strDataViewGroupByScript.append(" var objSelectedField = new DataViewField('"+objField.getFieldID()+"','"+objField.getFieldName()+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"');");
              for (int q = 0; q <argDataViewFunctions.size() ; q++) 
              {
                if(((FunctionFieldDTO)argDataViewFunctions.elementAt(q)).getFieldID() == objField.getFieldID())
                {
                    nFieldIndex = q;
                    break;
                }
              }
              strFieldAlias = objField.getFieldName();
              strDataViewGroupByScript.append(" objSelectedField.addFeatures(null,null,false,'"+strFieldAlias+"',"+nFieldIndex+");");
              strDataViewGroupByScript.append(" objSelectedField.m_nFieldID = '"+nFieldIndex+"';");
              strDataViewGroupByScript.append(" var objGroupBy = new DataViewGroupBy("+objField.getFieldID()+","+objField.getFieldType().getFieldTypeID()+");");
              strDataViewGroupByScript.append(" objGroupBy.m_nGroupByIndex = "+nFieldIndex+"; ");
              strDataViewGroupByScript.append(" objNewDataView.addGroupBy(objGroupBy); ");
          }
       }
    }
    catch(Exception objException)
    {
        
    }
    return strDataViewGroupByScript.toString();
 }   



private String fillDataViewHaving(QueryHavingDTO argQueryHaving,Vector argDataViewTerms,Vector argRelationSymbols) throws IOException
{
    int nElementIndex                                     = 0;
    Vector colConditionElements;
    ConditionElement objConditionElement;
    RelationSymbolDTO dtoRelationSymbol;
    Term objTerm;
    colConditionElements                                  = argQueryHaving.getConditionElements();
    StringBuffer strHavingScript                           = new StringBuffer();
    try
    {
        for(int i=0;i<colConditionElements.size();i++)
        {
          objConditionElement = (ConditionElement)colConditionElements.elementAt(i);
          if(objConditionElement.getConditionElementType().getConditionElementTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM)
          {
              objTerm = (Term)objConditionElement; 
              nElementIndex = argDataViewTerms.indexOf(objTerm);
    //          strWhereScript.append(" var objTerm = objNewDataView.m_arrDataViewTerms["+nElementIndex+"]; ");
              strHavingScript.append(" var objHavingElement = new HavingElement("+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM+"); ");
              strHavingScript.append(" objHavingElement.m_nElementIndex = '"+nElementIndex+"'; ");
              strHavingScript.append(" objNewDataView.addHavingElement(objHavingElement); ");
          }
          else
          {
              if(objConditionElement.getConditionElementType().getConditionElementTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL)  
              {
                  dtoRelationSymbol = (RelationSymbolDTO)objConditionElement; 
                  for (int p = 0; p < argRelationSymbols.size(); p++) 
                  {
                      if(((RelationSymbolDTO)argRelationSymbols.elementAt(p)).getRelationSymbolID() == dtoRelationSymbol.getRelationSymbolID())
                      {
                          nElementIndex = p;
                          break;
                      }
                  }
                  
//                  nElementIndex = argRelationSymbols.indexOf(dtoRelationSymbol);
                  strHavingScript.append(" var objRelationSymbol = objDataViewWizard.m_arrDataViewRelationSymbols["+nElementIndex+"]; ");
                  strHavingScript.append(" var objHavingElement = new HavingElement("+QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL+"); ");
                  strHavingScript.append(" objHavingElement.m_nRelationSymbolID = "+dtoRelationSymbol.getRelationSymbolID()+"; ");
                  strHavingScript.append(" objHavingElement.m_strRelationSymbolSQL = '"+dtoRelationSymbol.getRelationSymbolSQL()+"'; ");
                  strHavingScript.append(" objHavingElement.m_nElementIndex = "+nElementIndex+"; ");
                  strHavingScript.append(" objNewDataView.addHavingElement(objHavingElement); ");
               }
          }
       }
   }
   catch(Exception objException)
   {
       
   }
   return strHavingScript.toString();
}

private String fillDataViewOrderBy(Vector argDataViewOrderBy,Vector argSelectedDataViewFieldsSQLCash,Vector argSelectedDataViewsTypes,Vector argDataViewConstants,Vector argDataViewFunctions)
{
    int nDataViewType = -1;
    int nDotIndex   = 0;
    int nFieldIndex = 0;
    String strOrderByType;

    String strFieldName                                           = null;
    String strDataViewName                                        = null;
    String strFieldAlias                                          = null;
    OrderByDTO dtoOrderBy                                         = null;
    FieldDTO objField                                             = null;
    DataViewFieldDTO dtoDataViewField                             = null;
    
    StringBuffer strDataViewOrderByScript                  = new StringBuffer();
    try
    {
        for (int i = 0; i < argDataViewOrderBy.size();i++) 
        {
          dtoOrderBy = (OrderByDTO)argDataViewOrderBy.elementAt(i);
          objField = dtoOrderBy.getFieldDTO();
          strOrderByType = dtoOrderBy.getOrderType();
          if(objField.getFieldType().getFieldTypeID() == QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD)
          {
              if(objField.getFieldSQLCash() != null)
              {
                  nDotIndex =  objField.getFieldSQLCash().indexOf('.');
                  strFieldName = objField.getFieldSQLCash().substring(nDotIndex+2,objField.getFieldSQLCash().length()-1);
                  strDataViewName = objField.getFieldSQLCash().substring(1,nDotIndex-1);
                  strFieldAlias = objField.getFieldName();
                  nFieldIndex = argSelectedDataViewFieldsSQLCash.indexOf(objField.getFieldSQLCash());
                  nDataViewType = ((Integer)argSelectedDataViewsTypes.elementAt(nFieldIndex)).intValue();
              }    
              else
              {
                  strFieldName = objField.getFieldName();
                  strFieldAlias = "ERROR";
//                  nDataViewType = QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE;
              }
              dtoDataViewField = (DataViewFieldDTO)objField;
              strDataViewOrderByScript.append(" var objSelectedField = new DataViewField('"+objField.getFieldID()+"','"+strFieldName+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"','"+nDataViewType+"');");
//            strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objSelectedField); ");
              strDataViewOrderByScript.append(" objSelectedField.addFeatures('"+dtoDataViewField.getParentDataViewID()+"','"+strDataViewName+"',false,'"+strFieldAlias+"',"+nFieldIndex+");");
              strDataViewOrderByScript.append(" var objOrderBy = new DataViewOrderBy("+objField.getFieldID()+","+objField.getFieldType().getFieldTypeID()+",'"+strOrderByType+"');");
              strDataViewOrderByScript.append(" objOrderBy.m_objOrderByField = objSelectedField; ");
              strDataViewOrderByScript.append(" objOrderBy.m_nOrderByIndex = "+nFieldIndex+"; ");
              strDataViewOrderByScript.append(" objNewDataView.addOrderBy(objOrderBy); ");
         }
        /* else
         {
              if(objField.getFieldType().getFieldTypeID() == QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD)
              {  
                  strDataViewSelectedFieldsScript.append(" var objSelectedField = new DataViewField('"+objField.getFieldID()+"','"+objField.getFieldName()+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"');");
//                strDataViewSelectedFieldsScript.append(" objNewDataView.addField(objSelectedField); ");
                  for (int p = 0; p <argDataViewConstants.size() ; p++) 
                  {
                        if(((ConstantFieldDTO)argDataViewConstants.elementAt(p)).getFieldID() == objField.getFieldID())
                        {
                            nFieldIndex = p;
                            break;
                        }
                  }
                  strFieldAlias = objField.getFieldName();
                  strDataViewSelectedFieldsScript.append(" objSelectedField.addFeatures(null,null,false,'"+strFieldAlias+"',"+nFieldIndex+");");
                  strDataViewSelectedFieldsScript.append(" objSelectedField.m_nFieldIndex = '"+nFieldIndex+"';");
                  strDataViewSelectedFieldsScript.append(" objNewDataView.m_arrDataViewFields[objNewDataView.m_arrDataViewFields.length] = objSelectedField;");
              }*/
         else
          if(objField.getFieldType().getFieldTypeID() == QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD)
          {  
              strDataViewOrderByScript.append(" var objSelectedField = new DataViewField('"+objField.getFieldID()+"','"+objField.getFieldName()+"','"+objField.getFieldType().getFieldTypeID()+"','"+objField.getFieldDescription()+"');");
              for (int q = 0; q <argDataViewFunctions.size() ; q++) 
              {
                if(((FunctionFieldDTO)argDataViewFunctions.elementAt(q)).getFieldID() == objField.getFieldID())
                {
                    nFieldIndex = q;
                    break;
                }
              }
              strFieldAlias = objField.getFieldName();
              strDataViewOrderByScript.append(" objSelectedField.addFeatures(null,null,false,'"+strFieldAlias+"',"+nFieldIndex+");");
              strDataViewOrderByScript.append(" objSelectedField.m_nFieldID = '"+nFieldIndex+"';");
              strDataViewOrderByScript.append(" var objOrderBy = new DataViewOrderBy("+objField.getFieldID()+","+objField.getFieldType().getFieldTypeID()+",'"+strOrderByType+"');");
              strDataViewOrderByScript.append(" objOrderBy.m_nOrderByIndex = "+nFieldIndex+"; ");
              strDataViewOrderByScript.append(" objNewDataView.addOrderBy(objOrderBy); ");
          }
       }
    }
    catch(Exception objException)
    {
        
    }
    return strDataViewOrderByScript.toString();
 }
%>


 <% 
 if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_GENERAL_WIZARD) || request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_KPI_WIZARD) || request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_DCM_QUOTA_CALCULATION_WIZARD) || request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_ELIGIBLE_WIZARD))
 {
      fillDataViewIssues(request,out);
 }
 else if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_UPDATE_GENERAL_DATAVIEW)|| request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_UPDATE_KPI_DATAVIEW) || request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_UPDATE_DCM_QUOTA_CALCULATION_DATAVIEW) || request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_UPDATE_ELIGIBLE_DATAVIEW))
 {
      // Call the main fuction in this page to write the necessary scripts    
      fillDataViewIssues(request,out);
 }
 %>


   <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
      <TITLE>Data View</TITLE>
   </HEAD>
<!-- Define the main frame distribution of the page depending on the action-->   
 <FRAMESET cols="16%,*">  
   <% if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_GENERAL_WIZARD) || request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_KPI_WIZARD) || request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_DCM_QUOTA_CALCULATION_WIZARD) || request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_ELIGIBLE_WIZARD))
   {
   %>
   <FRAME id="Left" name="Left" src="../gn/GN_ADHOC_DataViewMenu.jsp" frameBorder=No noResize>
           <FRAME id="Right" name="Right" src="../gn/GN_ADHOC_DataViewWizard.jsp"  frameBorder=No noResize>
         </FRAMESET>
      
   <%         
   }      
   else if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_QUERY_BUILDER))
   {%>
       <FRAME id="Left" name="Left" src="../gn/GN_ADHOC_DataViewMenu.jsp" frameBorder=No noResize>
       <FRAME name="Right" id="Right" src="../gn/GN_ADHOC_DataViewMain.jsp" frameBorder=No noResize>
 <%} 
   else if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_UPDATE_GENERAL_DATAVIEW)|| request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_UPDATE_KPI_DATAVIEW)|| request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_UPDATE_DCM_QUOTA_CALCULATION_DATAVIEW) || request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_UPDATE_ELIGIBLE_DATAVIEW))
   {%>
     <FRAME id="Left" name="Left" src="../gn/GN_ADHOC_DataViewMenu.jsp" frameBorder=No noResize>
      <FRAME id="Right" name="Right" src="../gn/GN_ADHOC_DataViewWizard.jsp" frameBorder=No noResize>
  <!--         <FRAME name="Right" id="Right" src="../gn/GN_ADHOC_DataViewMain.jsp" frameBorder=No noResize>-->
  <%}  
   
%>  
</FRAMESET>

</HTML>

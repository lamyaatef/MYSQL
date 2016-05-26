function DataViewIssues() 
{
	this.m_arrDataViewIssue		= new Array();
	this.addIssue	 		= addIssue;
}

function DataViewIssue(argIssueID,argIssueName,argDataViewID) 
{
	this.m_nIssueID			= argIssueID;			
	this.m_arrDataViewIssuePairs	= new Array(argIssueName,argDataViewID);
	this.m_arrVersions  		= new Array();
	this.addVersion 		= addVersion;
}

function addVersion(argVersion)
{
	this.m_arrVersions[this.m_arrVersions.length] = argVersion;
}
function DataViewVersion(argDataViewID,argDataViewName,argVersionID,argVersionDescription) 
{
	this.m_nDataViewID	= argDataViewID;
	this.m_strDataViewName  		= argDataViewName;
	this.m_nVersionID		= argVersionID;
	this.m_strVersionDescription = argVersionDescription;
}

function addIssue(argIssue)
{
	if(this.m_arrDataViewIssue.length == 0)
	{
		this.m_arrDataViewIssue[this.m_arrDataViewIssue.length] = new Array();
		this.m_arrDataViewIssue[0][0] = argIssue;
	}
	else
	{
		this.m_arrDataViewIssue[this.m_arrDataViewIssue.length] = new Array();
		this.m_arrDataViewIssue[this.m_arrDataViewIssue.length-1][0] = argIssue;
	}
}

///////////////////////////////////////////////////////////////////////////////
/*
Data View and its wizard initialization objects
*/
///////////////////////////////////////////////////////////////////////////////
function DataViewWizard()
{
   this.m_arrDataViewFtFOperators 		= new Array();
   this.m_arrDataViewFtDVOperators 		= new Array();
//   this.m_arrArithmaticFunctions		= new Array();
   this.m_arrFunctionTypes	      		= new Array();
   this.m_arrDataViewRelationSymbols 		= new Array();
   this.m_arrDataViewConditionElementTypes 	= new Array();
   this.addFtFOperator = addFtFOperator;
   this.addFtDVOperator = addFtDVOperator;
   this.addRelationSymbol = addRelationSymbol;
   this.addConditionElementType = addConditionElementType;
//   this.addArithmaticFunction = addArithmaticFunction;
   this.addFunctionType = addFunctionType;

}
function addFunctionType(argFunctionType)
{
  this.m_arrFunctionTypes[this.m_arrFunctionTypes.length] = argFunctionType;
}
function FunctionType(argFunctionTypeID,argFunctionTypeName)
{
    this.m_nFunctionTypeID      = argFunctionTypeID;
    this.m_strFunctionTypeName  = argFunctionTypeName;
    this.m_arrFunctions         = new Array();
    this.addNewFunction = addNewFunction;
}

function addNewFunction(argFunction)
{
    this.m_arrFunctions[this.m_arrFunctions.length] = argFunction;
}

function addFtFOperator(argOperator)
{
   this.m_arrDataViewFtFOperators[this.m_arrDataViewFtFOperators.length] = argOperator;	
}
function addFtDVOperator(argOperator)
{
   this.m_arrDataViewFtDVOperators[this.m_arrDataViewFtDVOperators.length] = argOperator;	
}

function addRelationSymbol(argRelationSymbol)
{
   this.m_arrDataViewRelationSymbols[this.m_arrDataViewRelationSymbols.length] = argRelationSymbol;	
}
function addConditionElementType(argConditionElementType)
{
   this.m_arrDataViewConditionElementTypes[this.m_arrDataViewConditionElementTypes.length] = argConditionElementType;	
}

function addArithmaticFunction(argFunction)
{
   this.m_arrArithmaticFunctions[this.m_arrArithmaticFunctions.length] = argFunction;	
}
function addAggregateFunction(argFunction)
{
   this.m_arrAggregateFunctions[this.m_arrAggregateFunctions.length] = argFunction;	
}

function DataView(argDataViewID,argDataViewName,argDataViewVersion,argDataViewDescription,argDataViewType)
{
    this.m_nDataViewID 			= argDataViewID;		
    this.m_strDataViewName 		= argDataViewName;
    this.m_nDataViewVersion 		= argDataViewVersion;		
    this.m_strDataViewDescription 	= argDataViewDescription;
    this.m_nDataViewIndex;		
    this.m_nDataViewType		= argDataViewType;	
    this.m_bDataViewUnique = false;	
    this.m_arrDataViewFields    	= new Array();
    this.addField               	= addField;
}

function addField(argField)
{
	this.m_arrDataViewFields[this.m_arrDataViewFields.length] = argField;	
}

function DataViewField(argFieldID,argFieldName,argFieldType,argFieldDescription,argDataViewType)
{
	this.m_nFieldID = argFieldID;
	this.m_strFieldName = argFieldName;

	this.m_nDataViewID;
	this.m_strDataViewName;
  this.m_nDataViewType = argDataViewType;
	this.m_strFieldAlias;
	this.m_bFieldIsUnique;
	this.m_nFieldIndex;

	this.m_nFieldType 		= argFieldType;
	this.m_strFieldDescription 	= argFieldDescription;
	this.addFeatures 		= addFieldFeatures;	
}
/*function DataViewConstants()
{
	
}*/

function DataViewTerm(argTermID,argTermName,argTermType,argTermIndex)
{
	this.m_nTermID 	   = argTermID;
	this.m_strTermName = argTermName;
	this.m_strTermSQL;
	this.m_nTermType   = argTermType;
	this.m_nTermIndex  = argTermIndex;

	this.m_obj1stOperand;
	this.m_nOperatorIndex;
	this.m_obj2ndOperand;
}

function DataViewConstant(argConstantID,argConstantName,argConstantType,argConstantValue,argConstantDescription)
{
	this.m_nConstantID = argConstantID;
	this.m_strConstantName = argConstantName;

	this.m_nConstantType = argConstantType;
	this.m_strConstantValue = argConstantValue;
	this.m_strConstantDescription = argConstantDescription;
}

function DataViewOrderBy(argOrderByID,argOrderByElementTypeID,argOrderByType)
{
	this.m_nOrderByID 	          = argOrderByID;
	this.m_nOrderByElementTypeID  = argOrderByElementTypeID;
	this.m_strOrderByType         = argOrderByType;
	this.m_nOrderByIndex;
	this.m_objOrderByField;
}

function DataViewGroupBy(argGroupByID,argGroupByElementTypeID)
{
	this.m_nGroupByID 	          = argGroupByID;
	this.m_nGroupByElementTypeID  = argGroupByElementTypeID;
	this.m_nGroupByIndex;
	this.m_objGroupByField;
}

function DataViewParameter(argParameterID,argParameterName,argParameterLabel,argParameterControlType,argParameterDataType,argParameterValue,argParameterDescription)
{
	this.m_nParameterID 	             = argParameterID;
  this.m_strParameterName            = argParameterName;
  this.m_strParameterLabel           = argParameterLabel;
  this.m_nParameterDataType          = argParameterDataType;
  this.m_nParameterControlType       = argParameterControlType;
  this.m_strParameterDescription     = argParameterDescription;
  this.m_strParameterValue           = argParameterValue;
	this.m_nParameterIndex;
}

function NewDataView()
{
	this.m_strUniverseName		      = "";
	this.m_nSaveMode                = 0;
  this.m_nDataViewIssue           = 0;
  this.m_nDataViewVersion         = 1;
	this.m_strDataViewName		      = "";
	this.m_strDataViewDescription	  = "";
/*For the rules*/  
  this.m_KPIRulesViolated         = 0;
  this.m_EligibleRulesViolated    = 0;
/*End for the rules*/    
	this.m_arrSelectedDataViews     = new Array();
	this.m_arrDataViewConstants 	  = new Array();
	this.m_arrDataViewFields 	      = new Array();
	this.m_arrDataViewTerms 	      = new Array();
  this.m_arrDataViewFunctions 	          = new Array();
  this.m_arrDataViewFunctionParameters 	  = new Array();
	this.m_arrDataViewWhereElements	= new Array();
  this.m_arrDataViewOrderBy	= new Array();
  this.m_arrDataViewGroupBy	= new Array();
	this.m_arrDataViewHavingElements	= new Array();
  this.m_arrDataViewInputParameters	= new Array();
	this.addSelectedDataView 	        = addSelectedDataView;
	this.addField			                = addField;
	this.addTerm			                = addTerm;
	this.addConstant		              = addConstant;
	this.addWhereElement		          = addWhereElement;
  this.addFunction    		          = addFunction;
  this.addGroupBy    		            = addGroupBy;
  this.addOrderBy    	              = addOrderBy;
  this.addHavingElement    	        = addHavingElement;
  this.addInputParameter    	      = addInputParameter;
}

function addSelectedDataView(argSelectedDataView)
{
   this.m_arrSelectedDataViews[this.m_arrSelectedDataViews.length] = argSelectedDataView;
}

function addFieldFeatures(argDataViewID,argDataViewName,argFieldIsUnique,argFieldAlias,argFieldIndex)
{
	this.m_nDataViewID 	= argDataViewID;
	this.m_strDataViewName 	= argDataViewName;
	this.m_strFieldAlias	= argFieldAlias;
	this.m_bFieldIsUnique	= argFieldIsUnique;
	this.m_nFieldIndex	= argFieldIndex;
}

function addConstant(argConstant)
{
	this.m_arrDataViewConstants[this.m_arrDataViewConstants.length] = argConstant;	
}

function addOrderBy(argOrderBy)
{
	this.m_arrDataViewOrderBy[this.m_arrDataViewOrderBy.length] = argOrderBy;	
}

function addGroupBy(argGroupBy)
{
	this.m_arrDataViewGroupBy[this.m_arrDataViewGroupBy.length] = argGroupBy;	
}

function addInputParameter(argInputParameter)
{
	this.m_arrDataViewInputParameters[this.m_arrDataViewInputParameters.length] = argInputParameter;	
}
function addTerm(argTerm)
{
	this.m_arrDataViewTerms[this.m_arrDataViewTerms.length] = argTerm;	
}


function addWhereElement(argWhereElement)
{
	this.m_arrDataViewWhereElements[this.m_arrDataViewWhereElements.length] = argWhereElement;	
}

function addHavingElement(argHavingElement)
{
	this.m_arrDataViewHavingElements[this.m_arrDataViewHavingElements.length] = argHavingElement;	
}
function addFunction(argFunction)
{
	this.m_arrDataViewFunctions[this.m_arrDataViewFunctions.length] = argFunction;	
}
function WhereElement(argElementTypeID)
{
	this.m_nElementTypeID = argElementTypeID;
	this.m_nRelationSymbolID;
	this.m_strRelationSymbolSQL;
	this.m_nElementIndex;
}

function HavingElement(argElementTypeID)
{
	this.m_nElementTypeID = argElementTypeID;
	this.m_nRelationSymbolID;
	this.m_strRelationSymbolSQL;
	this.m_nElementIndex;
}
function Operator(argOperatorID,argOperatorName,argOperatorSQL,argOperatorDescription,argOperatorType) 
{
	this.m_nOperatorID 		= argOperatorID;
	this.m_strOperatorName 		= argOperatorName;
	this.m_strOperatorSQL 		= argOperatorSQL;
	this.m_strOperatorDescription	= argOperatorDescription;
	this.m_nOperatorIndex;
	this.m_nOperatorType 		= argOperatorType;
}

function RelationSymbol(argRelationSymbolID,argRelationSymbolName,argRelationSymbolSQL,argRelationSymbolDescription,argRelationSymbolIndex) 
{
	this.m_nRelationSymbolID 			= argRelationSymbolID;
	this.m_strRelationSymbolName 			= argRelationSymbolName;
	this.m_strRelationSymbolSQL 			= argRelationSymbolSQL;
	this.m_strRelationSymbolDescription		= argRelationSymbolDescription;
	this.m_nRelationSymbolIndex 			= argRelationSymbolIndex;
}

function ConditionElementType(argConditionElementTypeID,argConditionElementTypeName,argConditionElementTypeDescription,argConditionElementTypeIndex) 
{
	this.m_nConditionElementTypeID 			= argConditionElementTypeID;
	this.m_strConditionElementTypeName 		= argConditionElementTypeName;
	this.m_strConditionElementTypeDescription	= argConditionElementTypeDescription;
	this.m_nConditionElementTypeIndex 		= argConditionElementTypeIndex;
}

function LookUpFunction(argFunctionID,argFunctionName,argFunctionSQL,argFunctionDescription,argFunctionHelpText,argFunctionStatusID,argFunctionStatusName,argFunctionTypeID,argFunctionTypeName,argFunctionIndex)
{
    this.m_nFunctionID = argFunctionID;
    this.m_strFunctionName = argFunctionName;
    this.m_strFunctionSQL = argFunctionSQL;
    this.m_strFunctionDescription = argFunctionDescription;
    this.m_strFunctionHelpText = argFunctionHelpText;
    this.m_strFunctionStatusID = argFunctionStatusID;
    this.m_strFunctionStatusName = argFunctionStatusName;
    this.m_strFunctionTypeID = argFunctionTypeID;
    this.m_strFunctionTypeName = argFunctionTypeName;
    this.m_nFunctionIndex = argFunctionIndex;
    this.m_arrFunctionParameters =  new Array();
    this.addParameter                 = addParameter;
}

function FunctionParameter(argParameterID,argParameterName,argParameterDescription,argParameterIsOptional,argParameterIsList)
{
    this.m_nParameterID               = argParameterID;
    this.m_strParameterName           = argParameterName;
    this.m_strParameterDescription    = argParameterDescription
    this.m_nParameterIsOptional       = argParameterIsOptional;
    this.m_nParameterIsList           = argParameterIsList;
}

function addParameter(argParameter)
{
  	this.m_arrFunctionParameters[this.m_arrFunctionParameters.length] = argParameter;	
}

function FunctionParameterValue(argParameterDefinitionID,argParameterType)
{
    this.m_nParameterDefinitionID          = argParameterDefinitionID;
    this.m_nParameterType                  = argParameterType;
    this.m_nParameterIndex;
    this.m_objField;
    this.m_strParameterValue;
    this.addFieldParameterData = addFieldParameterData;
    this.addOtherParameterData = addOtherParameterData;
}

function addFieldParameterData(argField)
{
    this.m_objField         = argField;
}

function addOtherParameterData(argParameterIndex,argParameterValue)
{
    this.m_nParameterIndex = argParameterIndex;
    this.m_strParameterValue = argParameterValue;
}

function DataViewFunction(argFunctionID,argFunctionName,argFunctionSQL,argFunctionType,argFunctionIndex,argFunctionExpression,argFunctionDescription)
{
    this.m_nFunctionID              = argFunctionID;
    this.m_strFunctionName          = argFunctionName;
    this.m_strFunctionSQL           = argFunctionSQL;
    this.m_nFunctionType            = argFunctionType;
    this.m_nFunctionTypeIndex;
    this.m_nFunctionIndex           = argFunctionIndex;
    this.m_strFunctionExpression    = argFunctionExpression;
    this.m_strFunctionDescription    = argFunctionDescription;
}
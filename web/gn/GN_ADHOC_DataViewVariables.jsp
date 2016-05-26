<%@ page buffer="1024kb" %>
<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*" 
%>
<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/leftmenu.css">
      <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/validation.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/lst_add_remove.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/combobox.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/DataViews.js" type=text/javascript></SCRIPT>
      <TITLE>Data View Calculated Fields</TITLE>
   </HEAD>
   <div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>
   <BODY leftmargin=3 topmargin=0>
<!--    <Center>
      <H2>Data View Manipulated Fields</H2>
    </Center>-->
    
    <FORM id="formDataViewVariables" name="formDataViewVariables" action="" method=post>
    
        <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
          <TBODY>
                  <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
           </tr>
                  <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
           </tr>
           <tr class=TableHeader>
            <td class=TableHeader><font color=darkblue size=3>Data View Manipulated Fields</td>
           </tr>
          <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
           </tr>

          <TR>
            <TD class=TableHeader vAlign=top colSpan=9>Add Function field
              <A onclick="fun_RowSet_add('2','half_day');">
              <IMG alt="Click Here to add new Function ... " 
              src="../resources/img/img_sign_dn.gif" border=0></A>
            </TD>
          </TR>
          <TR>
            <TD class=TableTextColumn vAlign=top align=middle colSpan=9>
              <TABLE style="BORDER-COLLAPSE: collapse" borderColor=#000080 
              cellSpacing=0 cellPadding=0 align=left border=1>
              <TBODY>
                <TR>
                  <TD class=TableTextColumn>
                    <INPUT type=hidden size=15 value=0 name=fun_count>
                    <SCRIPT>
                      var objFunctionType;
                      var funTypeArray=new Array( 
                      new Array('',''))              

                      for(var t=0; t < top.objDataViewWizard.m_arrFunctionTypes.length;t++)
                      {
                          objFunctionType = top.objDataViewWizard.m_arrFunctionTypes[t];
                          funTypeArray[t+1] = new Array(objFunctionType.m_strFunctionTypeName,Number(t+1));
                      }

                      var availableFunArray=new Array(
                      new Array('',''));

                      function replaceSubstring(inputString, fromString, toString) {
   // Goes through the inputString and replaces every occurrence of fromString with toString
   var temp = inputString;
   if (fromString == "") {
      return inputString;
   }
   if (toString.indexOf(fromString) == -1) { 
   // If the string being replaced is not a part of the replacement string (normal situation)
      while (temp.indexOf(fromString) != -1) {
         var toTheLeft = temp.substring(0, temp.indexOf(fromString));
         var toTheRight = temp.substring(temp.indexOf(fromString)+fromString.length, temp.length);
         temp = toTheLeft + toString + toTheRight;
      }
   } else { // String being replaced is part of replacement string (like "+" being replaced with "++") - prevent an infinite loop
      var midStrings = new Array("~", "`", "_", "^", "#");
      var midStringLen = 1;
      var midString = "";
      // Find a string that doesn't exist in the inputString to be used
      // as an "inbetween" string
      while (midString == "") {
         for (var i=0; i < midStrings.length; i++) {
            var tempMidString = "";
            for (var j=0; j < midStringLen; j++) { tempMidString += midStrings [i]; }
            if (fromString.indexOf(tempMidString) == -1) {
               midString = tempMidString;
               i = midStrings.length + 1;
            }
         }
      } // Keep on going until we build an "inbetween" string that doesn't exist
      // Now go through and do two replaces - first, replace the "fromString" with the "inbetween" string
      while (temp.indexOf(fromString) != -1) {
         var toTheLeft = temp.substring(0, temp.indexOf(fromString));
         var toTheRight = temp.substring(temp.indexOf(fromString)+fromString.length, temp.length);
         temp = toTheLeft + midString + toTheRight;
      }
      // Next, replace the "inbetween" string with the "toString"
      while (temp.indexOf(midString) != -1) {
         var toTheLeft = temp.substring(0, temp.indexOf(midString));
         var toTheRight = temp.substring(temp.indexOf(midString)+midString.length, temp.length);
         temp = toTheLeft + toString + toTheRight;
      }
   } // Ends the check to see if the string being replaced is part of the replacement string or not
   return temp; // Send the updated string back to the user
} // Ends the "replaceSubstring" function
  
                      function popUpWindow(argObjID)
                      {
                          var objArguments = new Array();
                          var nRowIndex;
                          var nSelectedValue = 0;
                          var strFunctionTypeColumnID = new String();
                          var strFunctionIndexColumnID = new String();
                          var nParametersIndex = 0;
                          var strID= new String(argObjID);
                          nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
                          strFunctionTypeColumnID = strID.substring(0,strID.indexOf("__C")+3);
                          strFunctionIndexColumnID = strFunctionTypeColumnID + 3; 
                          strFunctionTypeColumnID += 2;
                          eval("nSelectedValue = document.formDataViewVariables."+strFunctionTypeColumnID+".value");
                          addToCart();   
                          var objFunctionType;
                          for(var h=1;h<=fun.getRowCount();h++)
                          {
                                if(fun.RowSet.getCell(h,5).cellElement.id==argObjID)
                                {
                                    nParametersIndex = h-1;
                                    break;
                                }
                          }      

                          if(nSelectedValue != '')  
                          {
                              objFunctionType = top.objDataViewWizard.m_arrFunctionTypes[Number(nSelectedValue-1)];
                              objArguments[0] = objFunctionType.m_arrFunctions[eval("document.formDataViewVariables."+strFunctionIndexColumnID+".value")];
                              objArguments[1] = Number(nParametersIndex+1);
                              objArguments[2] = top.objNewDataView.m_arrSelectedDataViews;
                              objArguments[3] = top.objNewDataView.m_arrDataViewConstants;
                              objArguments[4] = top.objNewDataView.m_arrDataViewFunctions;
                              objArguments[5] = top.objNewDataView.m_arrDataViewFunctionParameters[Number(nParametersIndex)];
                              objArguments[6] = top.objNewDataView.m_arrDataViewInputParameters;
                              
                              //alert( objArguments[5][0].m_nParameterIndex );   // added by khaled reda 21/6/2004                            

                              var sFeatures="dialogHeight: 500px; dialogWidth: 800px"
                              var objWin = showModalDialog("GN_ADHOC_DataViewFunctionParameters.jsp",objArguments,sFeatures);
                              if(objWin != null)
                              {
                                  var arrParameterValues = new Array();
                                  arrParameterValues = objWin[1];
                                  eval("document.formDataViewVariables.fun__R"+nRowIndex+"__C6.value = '"+replaceSubstring(objWin[0], "\'", "\\\'")+"';");
                                  top.objNewDataView.m_arrDataViewFunctionParameters[Number(nParametersIndex)] = arrParameterValues;
                              } 
                              addToCart();
//                            var newWin = window.open('FunctionParameters.html',"HTML",'width=' + '420' + ',height=' + '250');
                           }
                           else
                           {
                                alert("Please select a function from the functions list");
                            }
                        }

function popUpFunctions(argObjID,argDelete)
{
    var nRowIndex;
    var nSelectedValue = 0;
    var strPopUpColumnID = new String();
    var strID= new String(argObjID);
    var objFunctionType;
    nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
    strPopUpColumnID = strID.substring(0,strID.indexOf("__C")+3);
    strPopUpColumnID +=  3;
    if(argDelete == 1)
    {
        for(var h=1;h<=fun.getRowCount();h++)
        {
              if(fun.RowSet.getCell(h,2).cellElement.id==argObjID)
              {
                  nParametersIndex = h-1;
                  break;
              }
        }
        top.objNewDataView.m_arrDataViewFunctionParameters[nParametersIndex] = null;
        for(j=nParametersIndex;j<fun.getRowCount();j++)
        {
            top.objNewDataView.m_arrDataViewFunctionParameters[Number(j)] = top.objNewDataView.m_arrDataViewFunctionParameters[Number(j+1)];
        }
        top.objNewDataView.m_arrDataViewFunctionParameters[Number(fun.getRowCount()-1)] = null;
    }    
    
// Delete all functions 
    var nCount = Number(eval("document.formDataViewVariables."+strPopUpColumnID+".length"));
    for(var i=0;i<nCount;i++)
      eval("document.formDataViewVariables."+strPopUpColumnID+".remove(0);");

      eval("nSelectedValue = document.formDataViewVariables."+strID+".value");
      
      try
      {
          objFunctionType = top.objDataViewWizard.m_arrFunctionTypes[Number(nSelectedValue-1)]
          if(Number(objFunctionType.m_arrFunctions.length) > 0)
          {                      
            for(var i=0;i<Number(objFunctionType.m_arrFunctions.length);i++)
            {
                 var objOption = document.createElement("OPTION");
                 objOption.text = objFunctionType.m_arrFunctions[i].m_strFunctionName;
                 objOption.value = i;
                 eval("document.formDataViewVariables."+strPopUpColumnID+".add(objOption);");
            }
          }
          popUpDescription(strPopUpColumnID,0,argDelete);
      }
      catch(e)
      {
      }
    }          

    function popUpDescription(argObjID,argIndex,argDelete)
    {
          var nFunctionType = 0;
          var nRowIndex;
          var strFunctionTypeColumnID; 
          var nSelectedIndex = 0;
          var strPopUpColumnID = new String();
          var objFunctionType;
          var strID= new String(argObjID);
          nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
          strPopUpColumnID = strID.substring(0,strID.indexOf("__C")+3);
          strFunctionTypeColumnID = strPopUpColumnID;
          strPopUpColumnID +=  7;
          strFunctionTypeColumnID +=2;

          if(argDelete == 1)
          {
              for(var h=1;h<=fun.getRowCount();h++)
              {
                  if(fun.RowSet.getCell(h,3).cellElement.id==argObjID)
                  {
                      nParametersIndex = h-1;
                      break;
                  }
              }
              top.objNewDataView.m_arrDataViewFunctionParameters[nParametersIndex] = null;
              for(j=nParametersIndex;j<fun.getRowCount();j++)
              {
                 top.objNewDataView.m_arrDataViewFunctionParameters[Number(j)] = top.objNewDataView.m_arrDataViewFunctionParameters[Number(j+1)];
              }
              top.objNewDataView.m_arrDataViewFunctionParameters[Number(fun.getRowCount()-1)] = null;
         }    
// Delete description   
         eval("document.formDataViewVariables."+strPopUpColumnID+".value = ''");
         nFunctionType = eval("document.formDataViewVariables."+strFunctionTypeColumnID+".value");
         try
          {
              objFunctionType = top.objDataViewWizard.m_arrFunctionTypes[Number(nFunctionType-1)]
              if(Number(objFunctionType.m_arrFunctions.length) > 0)
                if(objFunctionType.m_arrFunctions[argIndex].m_strFunctionDescription == "" || objFunctionType.m_arrFunctions[argIndex].m_strFunctionDescription == "null")
                   eval("document.formDataViewVariables."+strPopUpColumnID+".value = 'N/A';");
                else
                  eval("document.formDataViewVariables."+strPopUpColumnID+".value = '"+ objFunctionType.m_arrFunctions[argIndex].m_strFunctionDescription+"';");
          }
          catch(e)
          {
          }
   }

    function popUpFunctionsWindow(argID)
    {
        var objArguments = new Array();
        var nRowIndex;
        var nFunctionType;
        var nFunctionID;
        var strID= new String(argID);
        nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
        var strColumnControlID = strID.substring(0,strID.indexOf("__C")+3);
        var strFunctionTypeControlID = strColumnControlID+2;
        var strFunctionIDControlID = strColumnControlID+3;


       
        eval("nFunctionType = document.formDataViewVariables."+strFunctionTypeControlID+".value");
        eval("nFunctionID = document.formDataViewVariables."+strFunctionIDControlID+".value");
        objArguments[0] = nFunctionType;
        objArguments[1] = nFunctionID;
        objArguments[2] = top.objDataViewWizard.m_arrFunctionTypes;
                        
       var sFeatures="dialogHeight: 500px; dialogWidth: 700px"
                              
       var objWin = showModalDialog("GN_ADHOC_FunctionsCatalog.jsp",objArguments,sFeatures);
       if(objWin != null)
       {
           eval("document.formDataViewVariables.fun__R"+nRowIndex+"__C2.value = '"+objWin[0]+"'");  
           popUpFunctions(strFunctionTypeControlID,0); 
           eval("document.formDataViewVariables.fun__R"+nRowIndex+"__C3.value = '"+objWin[1]+"'");
           eval("document.formDataViewVariables.fun__R"+nRowIndex+"__C7.value = '"+objWin[2]+"'");
       } 
    }
    
    function fun_RowSet_add(current_value,current_name)
    {
      document.formDataViewVariables.fun_count.value = fun.RowSet.getRowCount();
      ix = document.formDataViewVariables.fun_count.value = fun.RowSet.getRowCount();
      fun.RowSet.add();
      current_name = current_name+","+current_name;
      for (i = 0; i < funTypeArray.length; i++)
      {
        if (funTypeArray[i] == current_value)
        {
          fun.RowSet.getCell(ix+1,1).cellElement.selectedIndex = i;
        }
      }

      for (i=0 ; i < availableFunArray.length;i++)
      {
        if (availableFunArray[i] == current_name)
        {
          fun.RowSet.getCell(ix+1,2).cellElement.selectedIndex = i;
        }
      }
      fun.RowSet.getCell(ix+1,6).cellElement.readOnly = true;
      fun.RowSet.getCell(ix+1,7).cellElement.readOnly = true;
      fun.RowSet.getCell(ix+1,1).cellElement.maxLength = 30;
    }

      function function_removeRows()
      {
        i = confirm("This will remove this data")
        if (i==true){
          for(var i=1;i<=fun.getRowCount();i++){
            if(fun.getCell(i,8).getValue()==true)
            {
////////////////////////////////////////////////////////////////////////////////////
//  REMOVE THE DELETED FUNCTION FROM THE SELECTED FIELDS IF FOUND AND SWAP THEM ACCORDINGLY 
///////////////////////////////////////////////////////////////////////////////////
              var nFunctionIndex =Number(i-1);
              for(var j=0;j<top.objNewDataView.m_arrDataViewFields.length;j++)
              {
                 var objDataViewField = top.objNewDataView.m_arrDataViewFields[j];
                 if(objDataViewField != null)
                 {
                   if( objDataViewField.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
                   {
                         if(objDataViewField.m_nFieldIndex == nFunctionIndex)
                            top.objNewDataView.m_arrDataViewFields[j] = null;
                         else
                         if(objDataViewField.m_nFieldIndex > nFunctionIndex)
                            top.objNewDataView.m_arrDataViewFields[j].m_nFieldIndex = Number(top.objNewDataView.m_arrDataViewFields[j].m_nFieldIndex)-1;
                    }     
                 }   
              }
              
////////////////////////////////////////////////////////////////////////////////////
//  REMOVE THE FUNCTION PARAMETERS  
///////////////////////////////////////////////////////////////////////////////////

          for(var j=0;j<top.objNewDataView.m_arrDataViewFunctionParameters.length;j++)
          {
              var arrDataViewFunctionParameters = top.objNewDataView.m_arrDataViewFunctionParameters[j];
              try
              {
                 if(arrDataViewFunctionParameters != null && arrDataViewFunctionParameters !="")
                 {
                 }  
              }
              catch(e)
              {
                for(var k=0;k<arrDataViewFunctionParameters.length;k++)
                {
                    var objFunctionParameter = arrDataViewFunctionParameters[k]
                    if(objFunctionParameter != null)
                    {
                       if( objFunctionParameter.m_nParameterType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
                       {
                           if(objFunctionParameter.m_nParameterIndex == nFunctionIndex)
                              arrDataViewFunctionParameters[k] = null;
                           else
                           if(objFunctionParameter.m_nParameterIndex > nFunctionIndex)
                              arrDataViewFunctionParameters[k].m_nParameterIndex = Number(arrDataViewFunctionParameters[k].m_nParameterIndex)-1;
                       }     
                    }
                }
                top.objNewDataView.m_arrDataViewFunctionParameters[j] = arrDataViewFunctionParameters;
              }
          }
////////////////////////////////////////////////////////////////////////////////////
//  REMOVE THE DELETED FUNCTION FROM THE GROUP BY IF FOUND AND SWAP THEM ACCORDINGLY 
///////////////////////////////////////////////////////////////////////////////////
              for(var j=0;j<top.objNewDataView.m_arrDataViewGroupBy.length;j++)
              {
                 var objDataViewGroupBy = top.objNewDataView.m_arrDataViewGroupBy[j];
                 if(objDataViewGroupBy != null)
                 {
                   if( objDataViewGroupBy.m_nGroupByElementTypeID == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
                   {
                         if(objDataViewGroupBy.m_nGroupByIndex == nFunctionIndex)
                            top.objNewDataView.m_arrDataViewGroupBy[j] = null;
                         else
                         if(objDataViewGroupBy.m_nGroupByIndex > nFunctionIndex)
                            top.objNewDataView.m_arrDataViewGroupBy[j].m_nGroupByIndex = Number(top.objNewDataView.m_arrDataViewGroupBy[j].m_nGroupByIndex)-1;
                    }     
                 }   
              }

////////////////////////////////////////////////////////////////////////////////////
//  REMOVE THE DELETED FUNCTION FROM THE ORDER BY IF FOUND AND SWAP THEM ACCORDINGLY 
///////////////////////////////////////////////////////////////////////////////////
              for(var j=0;j<top.objNewDataView.m_arrDataViewOrderBy.length;j++)
              {
                 var objDataViewOrderBy = top.objNewDataView.m_arrDataViewOrderBy[j];
                 if(objDataViewOrderBy != null)
                 {
                     if( objDataViewOrderBy.m_nOrderByElementTypeID == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
                     {
                           if(objDataViewOrderBy.m_nOrderByIndex == nFunctionIndex)
                              top.objNewDataView.m_arrDataViewOrderBy[j] = null;
                           else
                           if(objDataViewOrderBy.m_nOrderByIndex > nFunctionIndex)
                              top.objNewDataView.m_arrDataViewOrderBy[j].m_nOrderByIndex = Number(top.objNewDataView.m_arrDataViewOrderBy[j].m_nOrderByIndex)-1;
                      }     
                 }    
              }

////////////////////////////////////////////////////////////////////////////////////
//  REMOVE THE DELETED FUNCTION FROM THE CONDITIONS,WHERE AND HAVING IF FOUND AND SWAP THEM ACCORDINGLY 
///////////////////////////////////////////////////////////////////////////////////
          for(var j=0;j<top.objNewDataView.m_arrDataViewTerms.length;j++)
          {
             var objDataViewTerm = top.objNewDataView.m_arrDataViewTerms[j];
             if(objDataViewTerm != null)
             {
                 var objDataViewTerm1stOperand = objDataViewTerm.m_obj1stOperand;
                 var objDataViewTerm2ndOperand = objDataViewTerm.m_obj2ndOperand;
                 if( objDataViewTerm1stOperand.m_nFieldType == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>" || objDataViewTerm2ndOperand.m_nFieldType == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>")
                 {
                     if((objDataViewTerm1stOperand.m_nFieldIndex == nFunctionIndex && objDataViewTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>) || (objDataViewTerm2ndOperand.m_nFieldIndex == nFunctionIndex && objDataViewTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>))
                     {
                          top.objNewDataView.m_arrDataViewTerms[j] = null;                          
                          
/////////////////////////////////////////////////////////////////////////////////////////
// Remove Where/Having
/////////////////////////////////////////////////////////////////////////////////////////
                         for(var g=0;g<top.objNewDataView.m_arrDataViewWhereElements.length;g++)
                         {
                             var objDataViewWhere = top.objNewDataView.m_arrDataViewWhereElements[g];
                             if(objDataViewWhere != null)
                             {
                                 if( objDataViewWhere.m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                                 { 
                                    if( objDataViewWhere.m_nElementIndex == Number(j))
                                    {
                                      top.objNewDataView.m_arrDataViewWhereElements[g] = null;
                                      
                                    }
                                 }
                             }    
                         }

                         for(var m=0;m<top.objNewDataView.m_arrDataViewHavingElements.length;m++)
                         {
                             var objDataViewHaving = top.objNewDataView.m_arrDataViewHavingElements[m];
                             if(objDataViewHaving != null)
                             {
                                 if( objDataViewHaving.m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>" )
                                 {
                                     if(objDataViewHaving.m_nElementIndex == Number(j))
                                     {
                                          top.objNewDataView.m_arrDataViewHavingElements[m] = null;
                                          
                                     }     
                                 }
                             }    
                         }
                   }
                   else
                   {
                          if(objDataViewTerm.m_obj1stOperand.m_nFieldIndex > nFunctionIndex && objDataViewTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
                              top.objNewDataView.m_arrDataViewTerms[j].m_obj1stOperand.m_nFieldIndex = Number(top.objNewDataView.m_arrDataViewTerms[j].m_obj1stOperand.m_nFieldIndex)-1;
                           if(objDataViewTerm.m_obj2ndOperand.m_nFieldIndex > nFunctionIndex && objDataViewTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
                              top.objNewDataView.m_arrDataViewTerms[j].m_obj2ndOperand.m_nFieldIndex = Number(top.objNewDataView.m_arrDataViewTerms[j].m_obj2ndOperand.m_nFieldIndex)-1;  
                   }
               }
             }
          }   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Remove the Null items and readjust the indexes
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
          var nOldIndex;
          for(var n=0;n<top.objNewDataView.m_arrDataViewTerms.length ;n++)
          {
             nOldIndex = -1; 
             if(top.objNewDataView.m_arrDataViewTerms[Number(n)] == null)
             {
                  for(var nn=n+1;nn<top.objNewDataView.m_arrDataViewTerms.length ;nn++)
                    if(top.objNewDataView.m_arrDataViewTerms[Number(nn)] != null)
                    {
                      top.objNewDataView.m_arrDataViewTerms[Number(n)] = top.objNewDataView.m_arrDataViewTerms[Number(nn)];
                      top.objNewDataView.m_arrDataViewTerms[Number(nn)] = null;
                      nOldIndex = top.objNewDataView.m_arrDataViewTerms[Number(n)].m_nTermIndex;
                      top.objNewDataView.m_arrDataViewTerms[Number(n)].m_nTermIndex = n;
                      break;
                    }
              }
              else
              {
                  nOldIndex = top.objNewDataView.m_arrDataViewTerms[Number(n)].m_nTermIndex;
              }
              
              for(var r=0;r<top.objNewDataView.m_arrDataViewWhereElements.length ;r++)
              {
                 if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)] != null)
                 {
                    if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex == Number(nOldIndex) && top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                    {
                        top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex = n ;
                    }   
                 }     
              }
          
              for(var t=0;t<top.objNewDataView.m_arrDataViewHavingElements.length ;t++)
              {
                 if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)] != null)
                 {
                    if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex == Number(nOldIndex) && top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                       top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex = n;
                 }     
              }
          }    
/////////////////////////////////////////////////////////////////////////////////////////////
// Remove the Null bubbels
/////////////////////////////////////////////////////////////////////////////////////////////
          for(var r=0;r<top.objNewDataView.m_arrDataViewWhereElements.length ;r++)
          {
              if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)] == null)
              {
                  for(var rr=r+1;rr<top.objNewDataView.m_arrDataViewWhereElements.length ;rr++)
                  if(top.objNewDataView.m_arrDataViewWhereElements[Number(rr)] != null)
                  {
                    top.objNewDataView.m_arrDataViewWhereElements[Number(r)] = top.objNewDataView.m_arrDataViewWhereElements[Number(rr)];
                    top.objNewDataView.m_arrDataViewWhereElements[Number(rr)] = null;
                    break;
                  }
              }    
          } 
          
          for(var t=0;t<top.objNewDataView.m_arrDataViewHavingElements.length ;t++)
          {
              if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)] == null)
              {
                  for(var tt=t+1;tt<top.objNewDataView.m_arrDataViewHavingElements.length ;tt++)
                  if(top.objNewDataView.m_arrDataViewHavingElements[Number(tt)] != null)
                  {
                      top.objNewDataView.m_arrDataViewHavingElements[Number(t)] = top.objNewDataView.m_arrDataViewHavingElements[Number(tt)];
                      top.objNewDataView.m_arrDataViewHavingElements[Number(tt)] = null;
                      break;
                  }           
              }
          }    


////////////////////////////////////////////////////////////////////////////////////
//  REMOVE THE RELATED FUNCTION PARAMETRS AND SWAP THE REST
///////////////////////////////////////////////////////////////////////////////////
                              
              fun.RowSet.deleteRow(i);
              top.objNewDataView.m_arrDataViewFunctionParameters[Number(i-1)] = null;
              for(j=i;j<=fun.getRowCount();j++)
              {
                top.objNewDataView.m_arrDataViewFunctionParameters[Number(j-1)] = top.objNewDataView.m_arrDataViewFunctionParameters[Number(j)];
              }
              top.objNewDataView.m_arrDataViewFunctionParameters[Number(fun.getRowCount())] = null;

              
            }//end if  
          }//end for
        }//end if
        else{
          for(var i=fun.getRowCount();i>=1;i--){
            if(fun.getCell(i,8).getValue()==true){
              fun.getCell(i,8).setValue() == false
            }//end if
          }//end for
        }//end else
       }
        var fun=new DeepGrid("fun",0,"")
        fun.ColumnHeaders.add(null,null,"Name",null,100,null,DG_TEXT,null,null,"onkeypress=checkQuotes()")
        fun.ColumnHeaders.add(null,null,"Function Type",null,150,null,DG_SELECT,funTypeArray,null,"onChange = popUpFunctions(this.id,1)");
        fun.ColumnHeaders.add(null,null,"Available Functions",null,140,"center",DG_SELECT,availableFunArray,null,"onChange = popUpDescription(this.id,this.value,1)")
        fun.ColumnHeaders.add(null,null,"(F)",null,10,"center",DG_BUTTON," (F) ",null,"onClick = popUpFunctionsWindow(this.id)");
        fun.ColumnHeaders.add(null,null,"(P)",null,10,"center",DG_BUTTON," (P) ",null,"onClick = popUpWindow(this.id)");
        fun.ColumnHeaders.add(null,null,"F()",null,10,null,DG_TEXT,null);
        fun.ColumnHeaders.add(null,null,"Description",null,155,null,DG_TEXTAREA,null)
        fun.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = function_removeRows()")
      </SCRIPT>
      <SCRIPT>
        try
        {
                if(Number(top.objNewDataView.m_arrDataViewFunctions.length) > 0)
                {
                  for(var i=0;i<Number(top.objNewDataView.m_arrDataViewFunctions.length);i++)
                  {
                        eval("fun.RowSet.add("+Number(i+1)+");");
                        eval("document.formDataViewVariables.fun__R"+Number(i+1)+"__C1.value = '"+ top.objNewDataView.m_arrDataViewFunctions[i].m_strFunctionName+"';");
                        eval("document.formDataViewVariables.fun__R"+Number(i+1)+"__C2.value = '"+ Number(top.objNewDataView.m_arrDataViewFunctions[i].m_nFunctionTypeIndex+1)+"';");
                        eval("popUpFunctions('fun__R"+Number(i+1)+"__C2',0);");
                        eval("document.formDataViewVariables.fun__R"+Number(i+1)+"__C3.value = '"+ top.objNewDataView.m_arrDataViewFunctions[i].m_nFunctionIndex+"';");
                        var strExp1 = replaceSubstring(top.objNewDataView.m_arrDataViewFunctions[i].m_strFunctionExpression, "\'", "\\\'");
                        var strExp2 = replaceSubstring(strExp1, "\"", "\\\"");
                        eval("document.formDataViewVariables.fun__R"+Number(i+1)+"__C6.value = '"+ strExp2+"';");
                        eval("document.formDataViewVariables.fun__R"+Number(i+1)+"__C7.value = '"+ top.objNewDataView.m_arrDataViewFunctions[i].m_strFunctionDescription+"';");
                        eval("document.formDataViewVariables.fun__R"+Number(i+1)+"__C6.readOnly = true;");
                        eval("document.formDataViewVariables.fun__R"+Number(i+1)+"__C7.readOnly = true;");
                    }
                }
                else
                {
                    fun_RowSet_add('','');
                }  
              }
              catch(e)
              {
                  fun_RowSet_add('','');
              }

            </SCRIPT>
           </TD>
                </TR>
              </TBODY>
              </TABLE>
            </TD>
          </TR>
        </TABLE>
        <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
          <TBODY>
          <TR>
            <TD class=TableHeader vAlign=top colSpan=9>Add Parameter field
              <A onclick="parameter_RowSet_add('2','half_day');">
              <IMG alt="Click Here to add new Parameter ... " 
              src="../resources/img/img_sign_dn.gif" border=0></A>
            </TD>
          </TR>
          <TR>
            <TD class=TableTextColumn vAlign=top align=middle colSpan=9>
              <TABLE style="BORDER-COLLAPSE: collapse" borderColor=#000080 
              cellSpacing=0 cellPadding=0 align=left border=1>
              <TBODY>
                <TR>
                  <TD class=TableTextColumn>
                    <INPUT type=hidden size=15 value=0 name=parameter_count>
                    <SCRIPT>
                      var controlTypeArray=new Array( 
                      new Array('',''), 
//                      new Array('Combo Box','<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONTROL_TYPE_SELECT%>'),
                      new Array('Text Input','<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONTROL_TYPE_TEXT%>'),
                      new Array('Date control','<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONTROL_TYPE_CALENDAR%>'));

                      var dataTypeArray=new Array( 
                      new Array('',''), 
                      new Array('Text','<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_TEXT%>'),
                      new Array('Number','<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_NUMERIC%>'),
                      new Array('Date','<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_PARAM_TYPE_DATE%>'));

                      function parameter_RowSet_add(current_value,current_name)
                      {
                        document.formDataViewVariables.parameter_count.value = parameter.RowSet.getRowCount();
                        ix = document.formDataViewVariables.parameter_count.value = parameter.RowSet.getRowCount();
                        parameter.RowSet.add();
                        current_name = current_name+","+current_name;
                        for (i=0 ; i < controlTypeArray.length;i++)
                        {
                          if (controlTypeArray[i] == current_name)
                          {
                            parameter.RowSet.getCell(ix+1,2).cellElement.selectedIndex = i;
                          }
                        }
                        for (i=0 ; i < dataTypeArray.length;i++)
                        {
                          if (dataTypeArray[i] == current_name)
                          {
                            parameter.RowSet.getCell(ix+1,2).cellElement.selectedIndex = i;
                          }
                        }
                        parameter.RowSet.getCell(ix+1,1).cellElement.maxLength = 30;
                        parameter.RowSet.getCell(ix+1,2).cellElement.maxLength = 500;
                        parameter.RowSet.getCell(ix+1,6).cellElement.maxLength = 250;
                      }
                      
                      function popUpDescriptionWindow(argID,argDescriptionFor)
                      {
                          var objArguments = new Array();
                          
                          var nRowIndex;
                          var strDescription;
                          var strID= new String(argID);
                          nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
                          strFunctionTypeColumnID = strID.substring(0,strID.indexOf("__C")+3);
                          if(argDescriptionFor == 'Constants' )
                          {
                              objArguments[0] = "Constant Description ";
                              strFunctionTypeColumnID += 4;
                              eval("strDescription = document.formDataViewVariables."+strFunctionTypeColumnID+".value");
                              objArguments[1] = strDescription;
                          }    
                          else
                          if(argDescriptionFor == 'Parameters' )
                          {
                              objArguments[0] = "Parameter Description ";
                              strFunctionTypeColumnID += 6;
                              eval("strDescription = document.formDataViewVariables."+strFunctionTypeColumnID+".value");
                              objArguments[1] = strDescription;
                          }    
                          
                          var sFeatures="dialogHeight: 370px; dialogWidth: 420px"
                              
                         var objWin = showModalDialog("GN_ADHOC_DescriptionEditor.jsp",objArguments,sFeatures);
                         if(objWin != null)
                         {
                              if(argDescriptionFor == 'Parameters' )
                              {
                                  eval("document.formDataViewVariables.parameter__R"+nRowIndex+"__C6.value = '"+objWin[0]+"'");
                              }
                              else
                              if(argDescriptionFor == 'Constants')
                              {
                                  eval("document.formDataViewVariables.constant__R"+nRowIndex+"__C4.value = '"+objWin[0]+"'");
                              }    
                         } 
                      }
                      
                      function parameter_removeRows()
                      {
                        i = confirm("This will remove this data")
                        if (i==true){
                          for(var i=parameter.getRowCount();i>=1;i--){
                            if(parameter.getCell(i,8).getValue()==true)
                            {
                                var nParameterIndex =Number(i-1);
////////////////////////////////////////////////////////////////////////////////////
//  REMOVE THE DELETED PARAMETER FROM THE CONDITIONS,WHERE AND HAVING IF FOUND AND SWAP THEM ACCORDINGLY 
///////////////////////////////////////////////////////////////////////////////////
          for(var j=0;j<top.objNewDataView.m_arrDataViewTerms.length;j++)
          {
             var objDataViewTerm = top.objNewDataView.m_arrDataViewTerms[j];
             if(objDataViewTerm != null)
             {
                 var objDataViewTerm1stOperand = objDataViewTerm.m_obj1stOperand;
                 var objDataViewTerm2ndOperand = objDataViewTerm.m_obj2ndOperand;
                 if( objDataViewTerm1stOperand.m_nFieldType == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>" || objDataViewTerm2ndOperand.m_nFieldType == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>")
                 {
                     if((objDataViewTerm1stOperand.m_nFieldIndex == nParameterIndex && objDataViewTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>) || (objDataViewTerm2ndOperand.m_nFieldIndex == nParameterIndex && objDataViewTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>))
                     {
                          top.objNewDataView.m_arrDataViewTerms[j] = null;                          
                          
/////////////////////////////////////////////////////////////////////////////////////////
// Remove Where/Having
/////////////////////////////////////////////////////////////////////////////////////////
                         for(var g=0;g<top.objNewDataView.m_arrDataViewWhereElements.length;g++)
                         {
                             var objDataViewWhere = top.objNewDataView.m_arrDataViewWhereElements[g];
                             if(objDataViewWhere != null)
                             {
                                 if( objDataViewWhere.m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                                 { 
                                    if( objDataViewWhere.m_nElementIndex == Number(j))
                                    {
                                      top.objNewDataView.m_arrDataViewWhereElements[g] = null;
                                      
                                    }
                                 }
                             }    
                         }

                         for(var m=0;m<top.objNewDataView.m_arrDataViewHavingElements.length;m++)
                         {
                             var objDataViewHaving = top.objNewDataView.m_arrDataViewHavingElements[m];
                             if(objDataViewHaving != null)
                             {
                                 if( objDataViewHaving.m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>" )
                                 {
                                     if(objDataViewHaving.m_nElementIndex == Number(j))
                                     {
                                          top.objNewDataView.m_arrDataViewHavingElements[m] = null;
                                          
                                     }     
                                 }
                             }    
                         }
                   }
                   else
                   {
                          if(objDataViewTerm.m_obj1stOperand.m_nFieldIndex > nParameterIndex && objDataViewTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)
                              top.objNewDataView.m_arrDataViewTerms[j].m_obj1stOperand.m_nFieldIndex = Number(top.objNewDataView.m_arrDataViewTerms[j].m_obj1stOperand.m_nFieldIndex)-1;
                           if(objDataViewTerm.m_obj2ndOperand.m_nFieldIndex > nParameterIndex && objDataViewTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)
                              top.objNewDataView.m_arrDataViewTerms[j].m_obj2ndOperand.m_nFieldIndex = Number(top.objNewDataView.m_arrDataViewTerms[j].m_obj2ndOperand.m_nFieldIndex)-1;  
                   }
               }
             }
          }   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Remove the Null items and readjust the indexes
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
          var nOldIndex;
          for(var n=0;n<top.objNewDataView.m_arrDataViewTerms.length ;n++)
          {
             nOldIndex = -1; 
             if(top.objNewDataView.m_arrDataViewTerms[Number(n)] == null)
             {
                  for(var nn=n+1;nn<top.objNewDataView.m_arrDataViewTerms.length ;nn++)
                    if(top.objNewDataView.m_arrDataViewTerms[Number(nn)] != null)
                    {
                      top.objNewDataView.m_arrDataViewTerms[Number(n)] = top.objNewDataView.m_arrDataViewTerms[Number(nn)];
                      top.objNewDataView.m_arrDataViewTerms[Number(nn)] = null;
                      nOldIndex = top.objNewDataView.m_arrDataViewTerms[Number(n)].m_nTermIndex;
                      top.objNewDataView.m_arrDataViewTerms[Number(n)].m_nTermIndex = n;
                      break;
                    }
              }
              else
              {
                  nOldIndex = top.objNewDataView.m_arrDataViewTerms[Number(n)].m_nTermIndex;
              }
              
              for(var r=0;r<top.objNewDataView.m_arrDataViewWhereElements.length ;r++)
              {
                 if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)] != null)
                 {
                    if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex == Number(nOldIndex) && top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                    {
                        top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex = n ;
                    }   
                 }     
              }
          
              for(var t=0;t<top.objNewDataView.m_arrDataViewHavingElements.length ;t++)
              {
                 if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)] != null)
                 {
                    if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex == Number(nOldIndex) && top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                       top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex = n;
                 }     
              }
          }    
/////////////////////////////////////////////////////////////////////////////////////////////
// Remove the Null bubbels
/////////////////////////////////////////////////////////////////////////////////////////////
          for(var r=0;r<top.objNewDataView.m_arrDataViewWhereElements.length ;r++)
          {
              if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)] == null)
              {
                  for(var rr=r+1;rr<top.objNewDataView.m_arrDataViewWhereElements.length ;rr++)
                  if(top.objNewDataView.m_arrDataViewWhereElements[Number(rr)] != null)
                  {
                    top.objNewDataView.m_arrDataViewWhereElements[Number(r)] = top.objNewDataView.m_arrDataViewWhereElements[Number(rr)];
                    top.objNewDataView.m_arrDataViewWhereElements[Number(rr)] = null;
                    break;
                  }
              }    
          } 
          
          for(var t=0;t<top.objNewDataView.m_arrDataViewHavingElements.length ;t++)
          {
              if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)] == null)
              {
                  for(var tt=t+1;tt<top.objNewDataView.m_arrDataViewHavingElements.length ;tt++)
                  if(top.objNewDataView.m_arrDataViewHavingElements[Number(tt)] != null)
                  {
                      top.objNewDataView.m_arrDataViewHavingElements[Number(t)] = top.objNewDataView.m_arrDataViewHavingElements[Number(tt)];
                      top.objNewDataView.m_arrDataViewHavingElements[Number(tt)] = null;
                      break;
                  }           
              }
          }    


                            
                              parameter.RowSet.deleteRow(i)
                            }//end if
                          }//end for
                        }//end if
                        else{
                          for(var i=parameter.getRowCount();i>=1;i--){
                            if(parameter.getCell(i,8).getValue()==true){
                              parameter.getCell(i,8).setValue() == false
                            }//end if
                          }//end for
                        }//end else
                      }
                      var parameter=new DeepGrid("parameter",0,"")
                      parameter.ColumnHeaders.add(null,null,"Name",null,140,null,DG_TEXT,null,null,"onkeypress=checkQuotes()")
                      parameter.ColumnHeaders.add(null,null,"Label",null,130,null,DG_TEXTAREA,null,null,"onkeypress=checkQuotes()")
                      parameter.ColumnHeaders.add(null,null,"Control Type",null,95,"center",DG_SELECT,controlTypeArray)
                      parameter.ColumnHeaders.add(null,null,"Data Type",null,95,"center",DG_SELECT,dataTypeArray)
                      parameter.ColumnHeaders.add(null,null,"Input",null,80,"center",DG_TEXT,null,null,"onkeypress=checkQuotes()")
                      parameter.ColumnHeaders.add(null,null,"Description",null,127,null,DG_TEXTAREA,null,null,"onkeypress=checkQuotes()")
                      parameter.ColumnHeaders.add(null,null,"(Desc)",null,20,"center",DG_BUTTON," (DESC) ",null,"onClick = popUpDescriptionWindow(this.id,'Parameters')");
                      parameter.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = parameter_removeRows()")
                    </SCRIPT>
                    <SCRIPT>
                    try
                    {
                      if(Number(top.objNewDataView.m_arrDataViewInputParameters.length) > 0)
                      {
                        for(var i=0;i<Number(top.objNewDataView.m_arrDataViewInputParameters.length);i++)
                        {
                          eval("parameter.RowSet.add("+Number(i+1)+");");
                          eval("document.formDataViewVariables.parameter__R"+Number(i+1)+"__C1.value = '"+ top.objNewDataView.m_arrDataViewInputParameters[i].m_strParameterName+"';");
                          eval("document.formDataViewVariables.parameter__R"+Number(i+1)+"__C2.value = '"+ top.objNewDataView.m_arrDataViewInputParameters[i].m_strParameterLabel+"';");
                          eval("document.formDataViewVariables.parameter__R"+Number(i+1)+"__C3.value = '"+ top.objNewDataView.m_arrDataViewInputParameters[i].m_nParameterControlType+"';");
                          eval("document.formDataViewVariables.parameter__R"+Number(i+1)+"__C4.value = '"+ top.objNewDataView.m_arrDataViewInputParameters[i].m_nParameterDataType+"';");
                          eval("document.formDataViewVariables.parameter__R"+Number(i+1)+"__C5.value = '"+ top.objNewDataView.m_arrDataViewInputParameters[i].m_strParameterValue+"';");
                          eval("document.formDataViewVariables.parameter__R"+Number(i+1)+"__C6.value = '"+ top.objNewDataView.m_arrDataViewInputParameters[i].m_strParameterDescription+"';");
                        }
                      }
                      else
                      {
                        parameter_RowSet_add('','');
                      }  
                    }
                    catch(e)
                    {
                      parameter_RowSet_add('','');
                    }
                    </SCRIPT>
                  </TD>
                </TR>
              </TBODY>
              </TABLE>
            </TD>
          </TR>
        </TABLE>
        <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
          <TBODY>
          <TR>
            <TD class=TableHeader vAlign=top colSpan=9>Add Constant field
              <A onclick="constant_RowSet_add('2','half_day');">
              <IMG alt="Click Here to add new Constant ... " 
              src="../resources/img/img_sign_dn.gif" border=0></A>
            </TD>
          </TR>
          <TR align=left>
            <TD class=TableTextColumn vAlign=top align=middle colSpan=9>
              <TABLE style="BORDER-COLLAPSE: collapse" borderColor=#000080 
              cellSpacing=0 cellPadding=0 align=left border=1>
              <TBODY>
                <TR>
                  <TD class=TableTextColumn>
                    <INPUT type=hidden size=15 value=0 name=constant_count>
                    <script language="javascript">
                      var dataTypeArray=new Array( 
                      new Array('',''), 
                      new Array('Text',<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_TEXT%>),
                      new Array('Number',<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_NUMERIC%>),
                      new Array('Date (DD/MM/YYYY)',<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONST_TYPE_DATE%>));
                      
                      function checkQuotes()
                      {
                        var nKeyCode = event.keyCode;
                        if( Number(nKeyCode)== 34 )
                        {
                            alert("You are not allowed to use the (\") character");
                            event.keyCode =0;
                            return false;
                        }    
                        if( Number(nKeyCode)== 39 )
                        {
                           alert("You are not allowed to use the (\') character");
                           event.keyCode = 0;
                           return false;
                        }
                        // Added by Khaled Reda 4/8/2004 to prevent the user from pressing the Enter button
                        if( Number(nKeyCode)== 13 )
                        {
                           event.keyCode = 0;
                           return false;
                        }   
                        return true;
                      }

                      function constant_RowSet_add(current_value,current_name)
                      {
                        document.formDataViewVariables.constant_count.value = constant.RowSet.getRowCount();
                        ix = document.formDataViewVariables.constant_count.value = constant.RowSet.getRowCount();
                        constant.RowSet.add();
                        current_name = current_name+","+current_name;
                        for (i = 0; i < dataTypeArray.length; i++)
                        {
                          if (dataTypeArray[i] == current_value)
                          {
                            constant.RowSet.getCell(ix+1,1).cellElement.selectedIndex = i;
                          }
                        }
                        constant.RowSet.getCell(ix+1,1).cellElement.maxLength = 30;
                        constant.RowSet.getCell(ix+1,4).cellElement.maxLength = 250;
                      }

function constant_removeRows()
{
   i = confirm("This will remove this data")
   if (i==true)
   {
      for(var i=1;i<=constant.getRowCount();i++)
      {
        if(constant.getCell(i,6).getValue()==true)
        {
//////////////////////////////////////////////////////////////////////////////////////
// Delete Field From Index
//////////////////////////////////////////////////////////////////////////////////////                              

          var nConstantIndex =Number(i-1);
          for(var j=0;j<top.objNewDataView.m_arrDataViewFields.length;j++)
          {
              var objDataViewField = top.objNewDataView.m_arrDataViewFields[j];
              if(objDataViewField != null)
              {
                   if( objDataViewField.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                    {
                         if(objDataViewField.m_nFieldIndex == nConstantIndex)
                            top.objNewDataView.m_arrDataViewFields[j] = null;
                         else
                         if(objDataViewField.m_nFieldIndex > nConstantIndex)
                            top.objNewDataView.m_arrDataViewFields[j].m_nFieldIndex = Number(top.objNewDataView.m_arrDataViewFields[j].m_nFieldIndex)-1;
                    }     
              }      
          }

////////////////////////////////////////////////////////////////////////////////////
//  REMOVE THE DELETED CONSTANT FROM THE CONDITIONS,WHERE AND HAVING IF FOUND AND SWAP THEM ACCORDINGLY 
///////////////////////////////////////////////////////////////////////////////////
          for(var j=0;j<top.objNewDataView.m_arrDataViewTerms.length;j++)
          {
             var objDataViewTerm = top.objNewDataView.m_arrDataViewTerms[j];
             if(objDataViewTerm != null)
             {
                 var objDataViewTerm1stOperand = objDataViewTerm.m_obj1stOperand;
                 var objDataViewTerm2ndOperand = objDataViewTerm.m_obj2ndOperand;
                 if( objDataViewTerm1stOperand.m_nFieldType == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>" || objDataViewTerm2ndOperand.m_nFieldType == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>")
                 {
                     if((objDataViewTerm1stOperand.m_nFieldIndex == nConstantIndex && objDataViewTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>) || (objDataViewTerm2ndOperand.m_nFieldIndex == nConstantIndex && objDataViewTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>))
                     {
                          top.objNewDataView.m_arrDataViewTerms[j] = null;                          
                          
/////////////////////////////////////////////////////////////////////////////////////////
// Remove Where/Having
/////////////////////////////////////////////////////////////////////////////////////////
                         for(var g=0;g<top.objNewDataView.m_arrDataViewWhereElements.length;g++)
                         {
                             var objDataViewWhere = top.objNewDataView.m_arrDataViewWhereElements[g];
                             if(objDataViewWhere != null)
                             {
                                 if( objDataViewWhere.m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                                 { 
                                    if( objDataViewWhere.m_nElementIndex == Number(j))
                                    {
                                      top.objNewDataView.m_arrDataViewWhereElements[g] = null;
                                      
                                    }
                                 }
                             }    
                         }

                         for(var m=0;m<top.objNewDataView.m_arrDataViewHavingElements.length;m++)
                         {
                             var objDataViewHaving = top.objNewDataView.m_arrDataViewHavingElements[m];
                             if(objDataViewHaving != null)
                             {
                                 if( objDataViewHaving.m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>" )
                                 {
                                     if(objDataViewHaving.m_nElementIndex == Number(j))
                                     {
                                          top.objNewDataView.m_arrDataViewHavingElements[m] = null;
                                          
                                     }     
                                 }
                             }    
                         }
                   }
                   else
                   {
                          if(objDataViewTerm.m_obj1stOperand.m_nFieldIndex > nConstantIndex && objDataViewTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                              top.objNewDataView.m_arrDataViewTerms[j].m_obj1stOperand.m_nFieldIndex = Number(top.objNewDataView.m_arrDataViewTerms[j].m_obj1stOperand.m_nFieldIndex)-1;
                           if(objDataViewTerm.m_obj2ndOperand.m_nFieldIndex > nConstantIndex && objDataViewTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                              top.objNewDataView.m_arrDataViewTerms[j].m_obj2ndOperand.m_nFieldIndex = Number(top.objNewDataView.m_arrDataViewTerms[j].m_obj2ndOperand.m_nFieldIndex)-1;  
                   }
               }
             }
          }   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Remove the Null items and readjust the indexes
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
          var nOldIndex;
          for(var n=0;n<top.objNewDataView.m_arrDataViewTerms.length ;n++)
          {
             nOldIndex = -1; 
             if(top.objNewDataView.m_arrDataViewTerms[Number(n)] == null)
             {
                  for(var nn=n+1;nn<top.objNewDataView.m_arrDataViewTerms.length ;nn++)
                    if(top.objNewDataView.m_arrDataViewTerms[Number(nn)] != null)
                    {
                      top.objNewDataView.m_arrDataViewTerms[Number(n)] = top.objNewDataView.m_arrDataViewTerms[Number(nn)];
                      top.objNewDataView.m_arrDataViewTerms[Number(nn)] = null;
                      nOldIndex = top.objNewDataView.m_arrDataViewTerms[Number(n)].m_nTermIndex;
                      top.objNewDataView.m_arrDataViewTerms[Number(n)].m_nTermIndex = n;
                      break;
                    }
              }
              else
              {
                  nOldIndex = top.objNewDataView.m_arrDataViewTerms[Number(n)].m_nTermIndex;
              }
              
              for(var r=0;r<top.objNewDataView.m_arrDataViewWhereElements.length ;r++)
              {
                 if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)] != null)
                 {
                    if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex == Number(nOldIndex) && top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                    {
                        top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex = n ;
                    }   
                 }     
              }
          
              for(var t=0;t<top.objNewDataView.m_arrDataViewHavingElements.length ;t++)
              {
                 if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)] != null)
                 {
                    if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex == Number(nOldIndex) && top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                       top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex = n;
                 }     
              }
          }    
/////////////////////////////////////////////////////////////////////////////////////////////
// Remove the Null bubbels
/////////////////////////////////////////////////////////////////////////////////////////////
          for(var r=0;r<top.objNewDataView.m_arrDataViewWhereElements.length ;r++)
          {
              if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)] == null)
              {
                  for(var rr=r+1;rr<top.objNewDataView.m_arrDataViewWhereElements.length ;rr++)
                  if(top.objNewDataView.m_arrDataViewWhereElements[Number(rr)] != null)
                  {
                    top.objNewDataView.m_arrDataViewWhereElements[Number(r)] = top.objNewDataView.m_arrDataViewWhereElements[Number(rr)];
                    top.objNewDataView.m_arrDataViewWhereElements[Number(rr)] = null;
                    break;
                  }
              }    
          } 
          
          for(var t=0;t<top.objNewDataView.m_arrDataViewHavingElements.length ;t++)
          {
              if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)] == null)
              {
                  for(var tt=t+1;tt<top.objNewDataView.m_arrDataViewHavingElements.length ;tt++)
                  if(top.objNewDataView.m_arrDataViewHavingElements[Number(tt)] != null)
                  {
                      top.objNewDataView.m_arrDataViewHavingElements[Number(t)] = top.objNewDataView.m_arrDataViewHavingElements[Number(tt)];
                      top.objNewDataView.m_arrDataViewHavingElements[Number(tt)] = null;
                      break;
                  }           
              }
          }    

          for(var j=0;j<top.objNewDataView.m_arrDataViewFunctionParameters.length;j++)
          {
              var arrDataViewFunctionParameters = top.objNewDataView.m_arrDataViewFunctionParameters[j];
              try
              {
                  if(arrDataViewFunctionParameters != null && arrDataViewFunctionParameters !="")
                  {
                  }    
                      
               }  
               catch(e)
               {
                      for(var k=0;k<arrDataViewFunctionParameters.length;k++)
                      {
                          var objFunctionParameter = arrDataViewFunctionParameters[k];
                          if(objFunctionParameter != null)
                          {
                             if( objFunctionParameter.m_nParameterType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                             {
                                 if(objFunctionParameter.m_nParameterIndex == nConstantIndex)
                                    arrDataViewFunctionParameters[k] = null;
                                 else
                                 if(objFunctionParameter.m_nParameterIndex > nConstantIndex)
                                    arrDataViewFunctionParameters[k].m_nParameterIndex = Number(arrDataViewFunctionParameters[k].m_nParameterIndex)-1;
                             }     
                          }
                      }
                      top.objNewDataView.m_arrDataViewFunctionParameters[j] = arrDataViewFunctionParameters;
                }
          }

          constant.RowSet.deleteRow(i);
        }//end if
      }//end for
   }//end if
   else
   {
      for(var i=1;i<=constant.getRowCount();i++)
      {
        if(constant.getCell(i,6).getValue()==true)
        {
          constant.getCell(i,6).setValue() == false
        }//end if
      }//end for
   }//end else
}
                    var constant=new DeepGrid("constant",0,"")
                    constant.ColumnHeaders.add(null,null,"Name",null,140,null,DG_TEXT,null,null,"onkeypress=checkQuotes()")
                    constant.ColumnHeaders.add(null,null,"Data Type",null,190,"center",DG_SELECT,dataTypeArray)
                    constant.ColumnHeaders.add(null,null,"Value",null,190,null,DG_TEXT,null,null,"onkeypress = checkQuotes()")
                    constant.ColumnHeaders.add(null,null,"Description",null,207,null,DG_TEXTAREA,null,null,"onkeypress=checkQuotes()")
                    constant.ColumnHeaders.add(null,null,"(Desc)",null,20,"center",DG_BUTTON," (DESC) ",null,"onClick = popUpDescriptionWindow(this.id,'Constants')");
                    constant.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = constant_removeRows()")
                    </SCRIPT>
                    <SCRIPT>
                    try
                    {
                      if(Number(top.objNewDataView.m_arrDataViewConstants.length) > 0)
                      {
                        for(var i=0;i<Number(top.objNewDataView.m_arrDataViewConstants.length);i++)
                        {
                          eval("constant.RowSet.add("+Number(i+1)+");");
                          eval("document.formDataViewVariables.constant__R"+Number(i+1)+"__C1.value = '"+ top.objNewDataView.m_arrDataViewConstants[i].m_strConstantName+"';");
                          eval("document.formDataViewVariables.constant__R"+Number(i+1)+"__C2.value = '"+ top.objNewDataView.m_arrDataViewConstants[i].m_nConstantType+"';");
                          eval("document.formDataViewVariables.constant__R"+Number(i+1)+"__C3.value = '"+ top.objNewDataView.m_arrDataViewConstants[i].m_strConstantValue+"';");
                          eval("document.formDataViewVariables.constant__R"+Number(i+1)+"__C4.value = '"+ top.objNewDataView.m_arrDataViewConstants[i].m_strConstantDescription+"';");
                        }
                      }
                      else
                      {
                        constant_RowSet_add('','');
                      }  
                    }
                    catch(e)
                    {
                      constant_RowSet_add('','');
                    }
                    
                    </SCRIPT>
                  </TD>
                </TR>
              </TBODY>
              </TABLE>
            </TD>
          </TR>
          <tr>
          <td align=center class=TableHeader>    <input type="button" name="butHelp" value="Help" class="button" onclick="return butHelp_onclick()"></td>
          </tr>
          <tr>
          <td class=TableHeader>&nbsp;</td>
          </tr>
                    <tr>
          <td class=TableHeader>&nbsp;</td>
          </tr>

        </TABLE>
     </form>
   </BODY>
<script language="javascript">
function butHelp_onclick()
{
   var nLeft = ((window.screen.availWidth)- 500)/2  ;
   var nTop = ((window.screen.availHeight) - 500)/2;
   window.open('GN_ADHOC_DataViewWizardStepHelp.html','',"height= 500px, width= 500px,top="+nTop+",left="+nLeft);
}

function addToCart()
{
    top.objNewDataView.m_arrDataViewConstants.length = 0;
    top.objNewDataView.m_arrDataViewFunctions.length = 0;
    top.objNewDataView.m_arrDataViewInputParameters.length = 0;
    
    for(var i=1;i<=constant.getRowCount();i++)
    {
       if(constant.getCell(i,2).getValue() != '')
       {
           var strConstantName =  constant.getCell(i,1).getValue();
           if(strConstantName == '' || strConstantName == "" || strConstantName == null )
              strConstantName = "Constant ("+ Number(i)+")";
              //+ constant.getCell(i,3).getValue();

           var objDataViewConstant = new DataViewConstant(0,strConstantName,constant.getCell(i,2).getValue(),constant.getCell(i,3).getValue(),constant.getCell(i,4).getValue());
           top.objNewDataView.addConstant(objDataViewConstant);
       }
   }

   for(var i=1;i<=parameter.getRowCount();i++)
   {
        
       if(parameter.getCell(i,3).getValue() != '' && parameter.getCell(i,4).getValue() != '')
       {
           var strParameterName =  parameter.getCell(i,1).getValue();
           //alert(strParameterName); 
           if(strParameterName == '' || strParameterName == "" || strParameterName == null )
              strParameterName = "Parameter ("+ Number(i)+")";
           var objDataViewInputParameter = new DataViewParameter(0,strParameterName,parameter.getCell(i,2).getValue(),parameter.getCell(i,3).getValue(),parameter.getCell(i,4).getValue(),parameter.getCell(i,5).getValue(),parameter.getCell(i,6).getValue());
           objDataViewInputParameter.m_nParameterIndex = Number(i-1);
           top.objNewDataView.addInputParameter(objDataViewInputParameter);
       }
   }

   
   for(var i=1;i<=fun.getRowCount();i++)
   {
       if(fun.getCell(i,2).getValue() != '')
       {
           var strFunctionName =  fun.getCell(i,1).getValue();
           var nFunctionTypeIndex = Number(fun.getCell(i,2).getValue()-1);
           var objFunctionType    = top.objDataViewWizard.m_arrFunctionTypes[nFunctionTypeIndex];
           var nFunctionIndex     = fun.getCell(i,3).getValue();
           var objFunction        = objFunctionType.m_arrFunctions[nFunctionIndex]; 
           var nFunctionID          = objFunction.m_nFunctionID;
           
           if(strFunctionName == '' || strFunctionName == "" || strFunctionName == null )
              strFunctionName = "Function ("+ Number(i)+")";
              //+ objFunction.m_strFunctionName+"()";
           

           var strFunctionSQL       = objFunction.m_strFunctionSQL;
           var nFunctionType        = objFunctionType.m_nFunctionTypeID;
           var strFunctionDescription    = objFunction.m_strFunctionDescription;
           var objDataViewFunction  = new DataViewFunction(nFunctionID,strFunctionName,strFunctionSQL,nFunctionType,nFunctionIndex,fun.getCell(i,6).getValue(),strFunctionDescription);
           objDataViewFunction.m_nFunctionTypeIndex = nFunctionTypeIndex;
           top.objNewDataView.addFunction(objDataViewFunction);
       }
   }
}
</script>

</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>

</HTML>

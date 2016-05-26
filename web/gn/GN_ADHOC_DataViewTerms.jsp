<%@ page buffer="1024kb" %>
<%@ page contentType="text/html;charset=windows-1256"
    import = "com.mobinil.sds.web.interfaces.gn.querybuilder.*" 
    import = "com.mobinil.sds.web.interfaces.*"  
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
      <TITLE>Data View Terms</TITLE>
   </HEAD>

    <div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>
    
   <BODY leftmargin=3 topmargin=0>
   <!-- <Center>
      <H2>Define Data View Conditions</H2>
    </Center>-->
    <FORM id="formDataViewTerms" name="formDataViewTerms" action="" method=post>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
            <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
           </tr>
           <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
           </tr>
           <tr class=TableHeader>
            <td class=TableHeader><font color=darkblue size=3>Data View Terms</td>
           </tr>
          <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
           </tr>

        <TR>
          <TD class=TableHeader vAlign=top colSpan=9>Add "Field to Field" relation
            <A onclick="FtFterm_RowSet_add('','');">
            <IMG alt="Click Here to add new field ... " 
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
                  <INPUT type=hidden size=15 value=0 name=FtFterm_count>
                  <SCRIPT>
///////////////////////////////////////////////////////////////////////////
//    Initializing the Arrays of the Field Types
//////////////////////////////////////////////////////////////////////////

            var FieldTypesArray=new Array( 
            new Array('',''), 
            new Array('Data View Field','<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>'),
            new Array('Constant','<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>'),
            new Array('Function','<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>'),
            new Array('Parameter','<%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>'));


            var firstFieldArray=new Array( 
            new Array('',''));
                  
            var secondFieldArray=new Array( 
            new Array('',''));
                    
/////////////////////////////////////////////////////////////////////////////////
// Get the data view fields from the top object objNewDataView (Available Fields)
//////////////////////////////////////////////////////////////////////////////////
        var FtFOperators =new Array();
        var FtFOperatorsIDs =new Array();
        var FtFOperatorsSQLs =new Array();
        var FtFOperatorsNames =new Array();

        var FtDVOperators =new Array();
        var FtDVOperatorsIDs =new Array();
        var FtDVOperatorsSQLs =new Array();

        var DataViewFieldNames =new Array();
        var FieldsIDs =new Array();
        var FieldsNames =new Array();
        var FieldsDataViewIDs =new Array();
        var FieldsDataViewNames =new Array();
        var FieldsDataViewTypes =new Array();
        var FieldsDescriptions =new Array();

        var DataViewNames           = new Array(new Array('',''));
        var DataViewIDs             = new Array();
        var DataViewVersions        = new Array();
        var DataViewDescriptions    = new Array();
        var DataViewTypes           = new Array();
        

        try
        {   
            var objFtFOperatorsArray = top.objDataViewWizard.m_arrDataViewFtFOperators;
            var nFtFOperatorIndex = 0;
            for(var j=0;j<objFtFOperatorsArray.length;j++)
            {
                var objFtFOperator = objFtFOperatorsArray[j]
                FtFOperators[nFtFOperatorIndex] = new Array(objFtFOperator.m_strOperatorName,j);
                FtFOperatorsIDs[nFtFOperatorIndex] = objFtFOperator.m_nOperatorID;
                FtFOperatorsSQLs[nFtFOperatorIndex] = objFtFOperator.m_strOperatorSQL;
                FtFOperatorsNames[nFtFOperatorIndex] = objFtFOperator.m_strOperatorName;
                nFtFOperatorIndex++;
            }    

            var objFtDVOperatorsArray = top.objDataViewWizard.m_arrDataViewFtDVOperators;
            var nFtDVOperatorIndex = 0;
            for(var j=0;j<objFtDVOperatorsArray.length;j++)
            {
                var objFtDVOperator = objFtDVOperatorsArray[j]
                FtDVOperators[nFtDVOperatorIndex] = new Array(objFtDVOperator.m_strOperatorName,j);
                FtDVOperatorsIDs[nFtDVOperatorIndex] = objFtDVOperator.m_nOperatorID;
                FtDVOperatorsSQLs[nFtDVOperatorIndex] = objFtDVOperator.m_strOperatorSQL;
                nFtDVOperatorIndex++;
            }
            
            var objSelectedDataViewsArray = top.objNewDataView.m_arrSelectedDataViews;
            var nDataViewIndex = 1;
            var nDataViewFieldsIndex = 0;
            for(var i=0;i<objSelectedDataViewsArray.length;i++)
            {
              var objDataView=objSelectedDataViewsArray[i]; 
              for(var j=0;j<objDataView.m_arrDataViewFields.length;j++)
              {
                DataViewFieldNames[nDataViewFieldsIndex] = new Array(objDataView.m_strDataViewName+"."+objDataView.m_arrDataViewFields[j].m_strFieldName,Number(nDataViewFieldsIndex));
                FieldsDataViewNames[nDataViewFieldsIndex] = objDataView.m_strDataViewName;
                FieldsDataViewIDs[nDataViewFieldsIndex] = objDataView.m_nDataViewID;
                FieldsDataViewTypes[nDataViewFieldsIndex] = objDataView.m_nDataViewType;
                FieldsIDs[nDataViewFieldsIndex] = objDataView.m_arrDataViewFields[j].m_nFieldID;
                FieldsNames[nDataViewFieldsIndex] = objDataView.m_arrDataViewFields[j].m_strFieldName;
                FieldsDescriptions[nDataViewFieldsIndex] = objDataView.m_arrDataViewFields[j].m_strFieldDescription;
                nDataViewFieldsIndex++;
              }  
            }

            /*var objPreDefinedDataViewsArray = top.objPreDefinedDataViewIssues.m_arrDataViewIssue;
            for(var i=0;i<objPreDefinedDataViewsArray.length;i++)
            {
                var objIssue = objPreDefinedDataViewsArray[i][0];
                var objDataViewVersions=objIssue.m_arrVersions;
                var objDataViewVersion = objDataViewVersions[0];
                if(objDataViewVersion.m_strDataViewName != '')
                {
                    DataViewNames[nDataViewIndex] = new Array(objDataViewVersion.m_strDataViewName,Number(nDataViewIndex));
                    DataViewIDs[nDataViewIndex] = objDataViewVersion.m_nDataViewID;
                    DataViewVersions[nDataViewIndex] = objDataViewVersion.m_nVersionID;
                    DataViewDescriptions[nDataViewIndex] = objDataViewVersion.m_strVersionDescription;
                    DataViewTypes[nDataViewIndex] = "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE%>";
                    nDataViewIndex++;
                }   
            }*/   

            var objUserDefinedDataViewsArray = top.objUserDefinedDataViewIssues.m_arrDataViewIssue;
            for(var i=0;i<objUserDefinedDataViewsArray.length;i++)
            {
                var objIssue = objUserDefinedDataViewsArray[i][0];
                var objDataViewVersions=objIssue.m_arrVersions;
                var objDataViewVersion = objDataViewVersions[0];
                if(objDataViewVersion.m_strDataViewName != '')
                {
                    DataViewNames[nDataViewIndex] = new Array(objDataViewVersion.m_strDataViewName,Number(nDataViewIndex));
                    DataViewIDs[nDataViewIndex] = objDataViewVersion.m_nDataViewID;
                    DataViewVersions[nDataViewIndex] = objDataViewVersion.m_nVersionID;
                    DataViewDescriptions[nDataViewIndex] = objDataViewVersion.m_strVersionDescription;
                    DataViewTypes[nDataViewIndex] = "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY%>";
                    nDataViewIndex++;
                }   
            }   
        }
        catch(e)
        {

        }


/////////////////////////////////////////////////////////////////////////////////
// Add FTF Term (The Add Button Action)
//////////////////////////////////////////////////////////////////////////////////

      function FtFterm_RowSet_add(current_value,current_name)
      {
        document.formDataViewTerms.FtFterm_count.value = FtFterm.RowSet.getRowCount();
        ix = document.formDataViewTerms.FtFterm_count.value = FtFterm.RowSet.getRowCount();
        FtFterm.RowSet.add();
        current_name = current_name+","+current_name;
        for (i = 0; i < firstFieldArray.length; i++)
        {
          if (firstFieldArray[i] == current_value)
          {
            FtFterm.RowSet.getCell(ix+1,1).cellElement.selectedIndex = i;
          }
        }
        for (i = 0; i < FtFOperators.length; i++)
        {
          if (FtFOperators[i] == current_value)
          {
            FtFterm.RowSet.getCell(ix+1,2).cellElement.selectedIndex = i;
          }
        }
        for (i = 0; i < secondFieldArray.length; i++)
        {
          if (secondFieldArray[i] == current_value)
          {
            FtFterm.RowSet.getCell(ix+1,3).cellElement.selectedIndex = i;
          }
        }
        FtFterm.RowSet.getCell(ix+1,1).cellElement.maxLength = 250;
      }

/////////////////////////////////////////////////////////////////////////////////
// Remove FTF Term  (The Remove Check Box Action)
//////////////////////////////////////////////////////////////////////////////////

      function FtFterm_removeRows()
      {
        i = confirm("This will remove this data")
        if (i==true){
          for(var i=1;i<=FtFterm.getRowCount();i++)
          {
            if(FtFterm.getCell(i,10).getValue()==true)
            {
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Remove the Null items and readjust the indexes
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
          var nTermIndex = Number(i-1);
          for(var r=0;r<top.objNewDataView.m_arrDataViewWhereElements.length ;r++)
          {
             if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)] != null)
             {
                if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex == Number(nTermIndex) && top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                {
                    top.objNewDataView.m_arrDataViewWhereElements[Number(r)] = null;
                }   
                else
                   if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex > nTermIndex)
                      top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex = Number(top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex)-1;
             }     
          }
          
          for(var t=0;t<top.objNewDataView.m_arrDataViewHavingElements.length ;t++)
          {
             if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)] != null)
             {
                if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex == Number(nTermIndex) && top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                   top.objNewDataView.m_arrDataViewHavingElements[Number(t)] = null;
                else
                   if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex > nTermIndex)
                      top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex = Number(top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex)-1;   
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

              FtFterm.RowSet.deleteRow(i)
            }//end if
          }//end for
        }//end if
        else{
          for(var i=FtFterm.getRowCount();i>=1;i--){
            if(FtFterm.getCell(i,10).getValue()==true){
              FtFterm.getCell(i,10).setValue() == false
            }//end if
          }//end for
        }//end else
      }
      
////////////////////////////////////////////////////////////////////////////////////////////////
// Draw The Grid
////////////////////////////////////////////////////////////////////////////////////////////////
      var FtFterm=new DeepGrid("FtFterm",0,"")
      FtFterm.ColumnHeaders.add(null,null,"Name",null,35,null,DG_TEXT,null,null,"onkeypress=checkQuotes()")
      FtFterm.ColumnHeaders.add(null,null,"1st Field Type",null,95,"center",DG_SELECT,FieldTypesArray,null,"onChange = popUpFields(this.id,3)")
      FtFterm.ColumnHeaders.add(null,null,"First Field",null,145,"center",DG_SELECT,firstFieldArray)
      FtFterm.ColumnHeaders.add(null,null,"(+)",null,10,"center",DG_BUTTON," (+) ",null,"onClick = popUpFieldsWindow(this.id,3)");
      FtFterm.ColumnHeaders.add(null,null,"Operator",null,100,"center",DG_SELECT,FtFOperators)
      FtFterm.ColumnHeaders.add(null,null,"2nd Field Type",null,95,"center",DG_SELECT,FieldTypesArray,null,"onChange = popUpFields(this.id,7)")
      FtFterm.ColumnHeaders.add(null,null,"Second Field",null,145,"center",DG_SELECT,secondFieldArray)
      FtFterm.ColumnHeaders.add(null,null,"(+)",null,10,"center",DG_BUTTON," (+) ",null,"onClick = popUpFieldsWindow(this.id,7)");
      FtFterm.ColumnHeaders.add(null,null,"SQL",null,20,"center",DG_BUTTON," SQL ",null,"onClick = popUpSQL(this.id,'FTF')");
      FtFterm.ColumnHeaders.add(null,null,"Remove",null,30,null,DG_BOOLEAN,null,null,"onClick = FtFterm_removeRows()")
      
      
                  </SCRIPT>
                  <SCRIPT>
      //              FtFterm_RowSet_add('','');
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
          <TD class=TableHeader vAlign=top colSpan=9>Add "Field to Data View" relation
            <A onclick="FtDVterm_RowSet_add('2','half_day');">
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
                  <INPUT type=hidden size=15 value=0 name=FtDVterm_count>
                  <SCRIPT>
      function popUpSQL(argID,argTermType)
      {
            var objArguments = new Array();
            var nRowIndex;
            var strTermSQL = "";
            var strID= new String(argID);
            nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
            var strColumnID = strID.substring(0,strID.indexOf("__C")+3);
            var str1stOperandColumnID;
            var str2ndOperandColumnID;
            var strOperatorColumnID;
            var nDGRowIndex = 0;
            for(var h=1;h<=FtFterm.getRowCount();h++)
            {
                  if(FtFterm.RowSet.getCell(h,9).cellElement.id==argID)
                  {
                      nDGRowIndex = h;
                      break;
                  }
            }
            for(var h=1;h<=FtDVterm.getRowCount();h++)
            {
                  if(FtDVterm.RowSet.getCell(h,8).cellElement.id==argID)
                  {
                      nDGRowIndex = h;
                      break;
                  }
            }
            
            var obj1stOperand;
            var obj2ndOperand;
            var nOperatorIndex;
            if(argTermType == 'FTF')
            { 
                objArguments[0] = "Field to feild term SQL";

                str1stOperandColumnID = strColumnID+3;
                strOperatorColumnID = strColumnID+5;
                str2ndOperandColumnID = strColumnID+7;
               if(eval("document.formDataViewTerms."+str1stOperandColumnID+".value != '' && document.formDataViewTerms."+str1stOperandColumnID+".value != 'undefined'")) 
               {
                   obj1stOperand = getFieldOperand('FtFterm',nDGRowIndex,2);
                   if(obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
                    strTermSQL = obj1stOperand.m_strDataViewName +"."+ obj1stOperand.m_strFieldName+ " ";
                    else
                   if(obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                        strTermSQL = "'"+top.objNewDataView.m_arrDataViewConstants[obj1stOperand.m_nFieldIndex].m_strConstantValue+ "' ";
                    else
                   if(obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
                      strTermSQL = top.objNewDataView.m_arrDataViewFunctions[obj1stOperand.m_nFieldIndex].m_strFunctionExpression+ " ";
                    else
                    if(obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)
                      strTermSQL = top.objNewDataView.m_arrDataViewInputParameters[obj1stOperand.m_nFieldIndex].m_strParameterName+ " ";    
               }  
               nOperatorIndex   = eval("document.formDataViewTerms."+strOperatorColumnID+".value") 

               var strUsedOperatorName = new String(FtFOperatorsNames[nOperatorIndex]);
               var strOperatorName = new String('<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_LEFT_JOIN%>');
               if(strUsedOperatorName.toUpperCase() == strOperatorName.toUpperCase()) 
                  strTermSQL +=   " (+) = ";
               else
               {
                  strOperatorName = new String('<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_RIGHT_JOIN%>');
                  if(strUsedOperatorName.toUpperCase() == strOperatorName.toUpperCase()) 
                     strTermSQL +=   " = ";
                  else   
                    strTermSQL +=   FtFOperatorsSQLs[nOperatorIndex]+ " ";
                } 

               if(eval("document.formDataViewTerms."+str2ndOperandColumnID+".value != '' && document.formDataViewTerms."+str2ndOperandColumnID+".value != 'undefined'")) 
               { 
                    obj2ndOperand = getFieldOperand('FtFterm',nDGRowIndex,6);
                    if(obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
                      strTermSQL += obj2ndOperand.m_strDataViewName + "."+ obj2ndOperand.m_strFieldName+ " ";
                    else
                    if(obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                        strTermSQL += "'"+top.objNewDataView.m_arrDataViewConstants[obj2ndOperand.m_nFieldIndex].m_strConstantValue+ "' ";     
                    else
                    if(obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
                      strTermSQL += top.objNewDataView.m_arrDataViewFunctions[obj2ndOperand.m_nFieldIndex].m_strFunctionExpression+ " ";
                    else
                    if(obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)
                       strTermSQL += top.objNewDataView.m_arrDataViewInputParameters[obj2ndOperand.m_nFieldIndex].m_strParameterName+ " ";    

                    strOperatorName = new String('<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_RIGHT_JOIN%>');
                    if(strUsedOperatorName.toUpperCase() == strOperatorName.toUpperCase()) 
                       strTermSQL +=   " (+) ";    
                }
            }
            else
            { 
                objArguments[0] = "Field to dataview term SQL";
                str1stOperandColumnID = strColumnID+3;
                strOperatorColumnID = strColumnID+5;
                str2ndOperandColumnID = strColumnID+6;
               if(eval("document.formDataViewTerms."+str1stOperandColumnID+".value != '' && document.formDataViewTerms."+str1stOperandColumnID+".value != 'undefined'")) 
               {
                   obj1stOperand = getFieldOperand('FtDVterm',nDGRowIndex,2);
                   if(obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
                    strTermSQL = obj1stOperand.m_strDataViewName +"."+ obj1stOperand.m_strFieldName+ " ";
                    else
                    if(obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
                        strTermSQL = "'"+top.objNewDataView.m_arrDataViewConstants[obj1stOperand.m_nFieldIndex].m_strConstantValue+ "' ";
                    else
                    if(obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
                      strTermSQL = top.objNewDataView.m_arrDataViewFunctions[obj1stOperand.m_nFieldIndex].m_strFunctionExpression+ " ";
                    else
                    if(obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)
                      strTermSQL = top.objNewDataView.m_arrDataViewInputParameters[obj1stOperand.m_nFieldIndex].m_strParameterName+ " ";    
               }  
               nOperatorIndex   = eval("document.formDataViewTerms."+strOperatorColumnID+".value") 
         
               strTermSQL +=   FtDVOperatorsSQLs[nOperatorIndex]+ " ";

               if(eval("document.formDataViewTerms."+str2ndOperandColumnID+".value != ''")) 
               { 
                    obj2ndOperand = getDataViewOperand(nDGRowIndex);
                    strTermSQL +=  obj2ndOperand.m_strDataViewName+ " ";
                }    
            }
            objArguments[1] = strTermSQL;
            objArguments[2] = 1;
            var sFeatures="dialogHeight: 370px; dialogWidth: 420px"
                              
           var objWin = showModalDialog("GN_ADHOC_TermSQL.jsp",objArguments,sFeatures);
      }
      function popUpDataViews(argID)
      {
            var objArguments = new Array();
            var nRowIndex;
            var nFieldID;
            var strID= new String(argID);
            nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
            var strColumnControlID = strID.substring(0,strID.indexOf("__C")+3);
            var strFieldIDControlIndex = strColumnControlID+6;
            eval("nFieldID = document.formDataViewTerms."+strFieldIDControlIndex+".value");
            var arrDataViews = new Array();   
            
            var nIndex = 0; 
            var strPairs=new String(DataViewNames);
            var arrPairs=new Array();
            arrPairs = strPairs.split(",");
            var strDataViewName=new String();
            for(var j =2;j<Number(arrPairs.length);j=Number(j+2))    
            {
               strDataViewName = arrPairs[j];
               arrDataViews[nIndex] = strDataViewName;
               nIndex++;
               strDataViewName = "";
            }
            
            if(nFieldID != '')
            {
                objArguments[1] = Number(Number(nFieldID)-1);
            }   
            else
                objArguments[1] = '0';

            objArguments[0] = "Select a data view:";
            objArguments[2] = arrDataViews;     

             var sFeatures="dialogHeight: 500px; dialogWidth: 700px"
                              
             var objWin = showModalDialog("GN_ADHOC_FieldsList.jsp",objArguments,sFeatures);
             if(objWin != null)
             {
                eval("document.formDataViewTerms."+strFieldIDControlIndex+".value = '"+Number(Number(objWin[0])+1)+"'");
             } 
      }
      
      function popUpFieldsWindow(argID,argPopUpPlace)
      {
            var objArguments = new Array();
            var nRowIndex;
            var nFieldType;
            var nFieldID;
            var strID= new String(argID);
            nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
            var strColumnControlID = strID.substring(0,strID.indexOf("__C")+3);
            var strFieldTypeControlIndex = strColumnControlID+Number(argPopUpPlace-1);
            var strFieldIDControlIndex = strColumnControlID+Number(argPopUpPlace);
            eval("nFieldType = document.formDataViewTerms."+strFieldTypeControlIndex+".value");
            if(nFieldType != '')
            {
                eval("nFieldID = document.formDataViewTerms."+strFieldIDControlIndex+".value");
      
                objArguments[1] = nFieldID;
                switch (nFieldType)
                {
                  case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>':
                  {
                     var nIndex = 0; 
                     var arrPairs=new Array();
                     var arrDataViewFields = new Array();
                     var strPairs=new String(DataViewFieldNames);
                     arrPairs = strPairs.split(",");
                     var strFieldName=new String();
                     var strParsedFieldName;
                     for(var j =0;j<Number(arrPairs.length);j=Number(j+2))    
                     {
                           strFieldName = arrPairs[j];
                           while(strFieldName.indexOf("~@^@~") !=-1)
                           {
                              strParsedFieldName = strFieldName.replace("~@^@~",",");
                              strFieldName = strParsedFieldName;
                           }
                           arrDataViewFields[nIndex] = strFieldName;
                           nIndex++;
                           strFieldName = "";
                     }   
                     objArguments[0] = "Select a dataview field:";
                     objArguments[2] = arrDataViewFields;          
                     break;
                  }
                  case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>':
                  {
                     objArguments[0] = "Select a constant field:";
                     objArguments[2] = top.objNewDataView.m_arrDataViewConstants;     
                     break; 
                  }
                  case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>':
                  {
                      var arrAvailableFunctions = new Array();
                      objArguments[0] = "Select a function field:";
                      for(var j =0;j<Number(top.objNewDataView.m_arrDataViewFunctions.length);j++)    
                      {
                           strFieldName = top.objNewDataView.m_arrDataViewFunctions[j].m_strFunctionName;
                           arrAvailableFunctions[j] = strFieldName;
                      }
                      objArguments[2] = arrAvailableFunctions;      
                      break;
                  }
                  case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>':
                  {
                     objArguments[0] = "Select a parameter field:";
                     objArguments[2] = top.objNewDataView.m_arrDataViewInputParameters;     
                     break; 
                  }
                  default:
                  {
                    break;
                  }
                }
                        
                var sFeatures="dialogHeight: 500px; dialogWidth: 700px"
                              
                var objWin = showModalDialog("GN_ADHOC_FieldsList.jsp",objArguments,sFeatures);
                if(objWin != null)
                {
                   popUpFields(strFieldTypeControlIndex,argPopUpPlace); 
                   eval("document.formDataViewTerms."+strFieldIDControlIndex+".value = '"+objWin[0]+"'");
                } 
           }
           else
              alert("Please select a field type from the types list");  
  }

  function popUpFields(argObjID,argPopUpPlace)
  {
      var nRowIndex;
      var nSelectedValue = 0;
      var strPopUpColumnID = new String();
      var strID= new String(argObjID);
      nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
      strPopUpColumnID = strID.substring(0,strID.indexOf("__C")+3);
      strPopUpColumnID += argPopUpPlace;

  // Delete all fields   
      var nCount = Number(eval("document.formDataViewTerms."+strPopUpColumnID+".length"));
      for(var i=0;i<nCount;i++)
          eval("document.formDataViewTerms."+strPopUpColumnID+".remove(0);");

      eval("nSelectedValue = document.formDataViewTerms."+strID+".value");
      
      switch (nSelectedValue)
      {
        case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>':
           try
           {
                  var arrPairs=new Array();
                  var strPairs=new String(DataViewFieldNames);
                  arrPairs = strPairs.split(",");
                  var strFieldName=new String();
                  var strParsedFieldName;
                  for(var j =0;j<Number(arrPairs.length);j=Number(j+2))    
                  {
                       var objOption = document.createElement("OPTION");
                       strFieldName = arrPairs[j];
                       while(strFieldName.indexOf("~@^@~") !=-1)
                       {
                          strParsedFieldName = strFieldName.replace("~@^@~",",");
                          strFieldName = strParsedFieldName;
                       }
                       objOption.text = strFieldName;
                       objOption.value = arrPairs[j+1];
                       strFieldName = "";
                       eval("document.formDataViewTerms."+strPopUpColumnID+".add(objOption);");
                  }  
//            popUpDescription(strPopUpColumnID,0);
           }
           catch(e)
           {
           }
           break;
                  
        case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>':
           try
           {
              if(Number(top.objNewDataView.m_arrDataViewConstants.length) > 0)
              {                      
                for(var i=0;i<Number(top.objNewDataView.m_arrDataViewConstants.length);i++)
                {
                     var objOption = document.createElement("OPTION");
                     objOption.text = top.objNewDataView.m_arrDataViewConstants[i].m_strConstantName;
                     objOption.value = i;
                     eval("document.formDataViewTerms."+strPopUpColumnID+".add(objOption);");
                }
             }
//             popUpDescription(strPopUpColumnID,0);
           }
           catch(e)
           {
           }
           break;
        case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>':
        try
           {
              if(Number(top.objNewDataView.m_arrDataViewFunctions.length) > 0)
              {                      
                for(var i=0;i<Number(top.objNewDataView.m_arrDataViewFunctions.length);i++)
                {
                     var objOption = document.createElement("OPTION");
                     objOption.text = top.objNewDataView.m_arrDataViewFunctions[i].m_strFunctionName;
                     objOption.value = i;
                     eval("document.formDataViewTerms."+strPopUpColumnID+".add(objOption);");
                }
             }
//             popUpDescription(strPopUpColumnID,0);
           }
           catch(e)
           {
           }
           break;
        case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>':
        try
           {
              if(Number(top.objNewDataView.m_arrDataViewInputParameters.length) > 0)
              {                      
                for(var i=0;i<Number(top.objNewDataView.m_arrDataViewInputParameters.length);i++)
                {
                     var objOption = document.createElement("OPTION");
                     objOption.text = top.objNewDataView.m_arrDataViewInputParameters[i].m_strParameterName;
                     objOption.value = i;
                     eval("document.formDataViewTerms."+strPopUpColumnID+".add(objOption);");
                }
             }
//             popUpDescription(strPopUpColumnID,0);
           }
           catch(e)
           {
           }
           break;
           
//        default: alert("No Matching");
    }
}
                    function openWin()
                    {
                      var newWin = window.open('FunctionParameters.html',"HTML",'width=' + '400' + ',height=' + '200');
                    }

                    function FtDVterm_RowSet_add(current_value,current_name)
                    {
                      document.formDataViewTerms.FtDVterm_count.value = FtDVterm.RowSet.getRowCount();
                      ix = document.formDataViewTerms.FtDVterm_count.value = FtDVterm.RowSet.getRowCount();
                      FtDVterm.RowSet.add();
                      current_name = current_name+","+current_name;
                      for (i = 0; i < firstFieldArray.length; i++)
                      {
                        if (firstFieldArray[i] == current_value)
                        {
                          FtDVterm.RowSet.getCell(ix+1,1).cellElement.selectedIndex = i;
                        }
                      }

                      for (i=0 ; i < FtFOperators.length;i++)
                      {
                        if (FtFOperators[i] == current_name)
                        {
                          FtDVterm.RowSet.getCell(ix+1,2).cellElement.selectedIndex = i;
                        }
                      }
                      for (i=0 ; i < DataViewNames.length;i++)
                      {
                        if (DataViewNames[i] == current_name)
                        {
                          FtDVterm.RowSet.getCell(ix+1,3).cellElement.selectedIndex = i;
                        }
                      }
                          FtDVterm.RowSet.getCell(ix+1,1).cellElement.maxLength = 250;
                    }

                    function function_removeRows()
                    {
                      i = confirm("This will remove this data")
                      if (i==true){
                        for(var i=1;i<=FtDVterm.getRowCount();i++){
                          if(FtDVterm.getCell(i,9).getValue()==true)
                          {
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Remove the Null items and readjust the indexes
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
          var nTermIndex = Number(FtFterm.getRowCount())+Number(i-1);
          for(var r=0;r<top.objNewDataView.m_arrDataViewWhereElements.length ;r++)
          {
             if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)] != null)
             {
                if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex == Number(nTermIndex) && top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                {
                    top.objNewDataView.m_arrDataViewWhereElements[Number(r)] = null;
                }   
                else
                   if(top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex > nTermIndex)
                      top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex = Number(top.objNewDataView.m_arrDataViewWhereElements[Number(r)].m_nElementIndex)-1;
             }     
          }
          
          for(var t=0;t<top.objNewDataView.m_arrDataViewHavingElements.length ;t++)
          {
             if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)] != null)
             {
                if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex == Number(nTermIndex) && top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementTypeID == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
                   top.objNewDataView.m_arrDataViewHavingElements[Number(t)] = null;
                else
                   if(top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex > nTermIndex)
                      top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex = Number(top.objNewDataView.m_arrDataViewHavingElements[Number(t)].m_nElementIndex)-1;   
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

                            FtDVterm.RowSet.deleteRow(i)
                          }//end if
                        }//end for
                      }//end if
                      else{
                        for(var i=FtDVterm.getRowCount();i>=1;i--){
                          if(FtDVterm.getCell(i,9).getValue()==true){
                            FtDVterm.getCell(i,9).setValue() == false
                          }//end if
                        }//end for
                      }//end else
                    }

                    var FtDVterm=new DeepGrid("FtDVterm",0,"")
                    FtDVterm.ColumnHeaders.add(null,null,"Name",null,35,null,DG_TEXT,null,null,"onkeypress=checkQuotes()")
                    FtDVterm.ColumnHeaders.add(null,null,"Field Type",null,100,"center",DG_SELECT,FieldTypesArray,null,"onChange = popUpFields(this.id,3)")
                    FtDVterm.ColumnHeaders.add(null,null,"Field Name",null,205,null,DG_SELECT,firstFieldArray)
                    FtDVterm.ColumnHeaders.add(null,null,"(+)",null,10,"center",DG_BUTTON," (+) ",null,"onClick = popUpFieldsWindow(this.id,3)");
                    FtDVterm.ColumnHeaders.add(null,null,"Operator",null,75,null,DG_SELECT,FtDVOperators)
                    FtDVterm.ColumnHeaders.add(null,null,"Data View",null,175,null,DG_SELECT,DataViewNames)
                    FtDVterm.ColumnHeaders.add(null,null,"(+)",null,20,"center",DG_BUTTON," (+) ",null,"onClick = popUpDataViews(this.id)");
                    FtDVterm.ColumnHeaders.add(null,null,"SQL",null,20,"center",DG_BUTTON," SQL ",null,"onClick = popUpSQL(this.id)");
                    FtDVterm.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = function_removeRows()")
                  </SCRIPT>
                  <SCRIPT>
//                    FtDVterm_RowSet_add('','');
      try
      {
            var nFtFTermsCount = 1;
            var nFtDVTermsCount = 1;
            if(top.objNewDataView.m_arrDataViewTerms.length > 0)
            {
                for(var i=0;i< Number(top.objNewDataView.m_arrDataViewTerms.length); i++)
                {
                    if(top.objNewDataView.m_arrDataViewTerms[i] != null)
                    {
                        if(top.objNewDataView.m_arrDataViewTerms[i].m_nTermType == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE%>")
                        {
                            eval("FtFterm.RowSet.add("+Number(nFtFTermsCount)+");");
                            eval("document.formDataViewTerms.FtFterm__R"+Number(nFtFTermsCount)+"__C1.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_strTermName+"';");
                            eval("document.formDataViewTerms.FtFterm__R"+Number(nFtFTermsCount)+"__C2.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_obj1stOperand.m_nFieldType+"';");
                            eval("popUpFields('FtFterm__R"+Number(nFtFTermsCount)+"__C2',3);");
                            eval("document.formDataViewTerms.FtFterm__R"+Number(nFtFTermsCount)+"__C3.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_obj1stOperand.m_nFieldIndex+"';");
                            eval("document.formDataViewTerms.FtFterm__R"+Number(nFtFTermsCount)+"__C5.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_nOperatorIndex+"';");
                            eval("document.formDataViewTerms.FtFterm__R"+Number(nFtFTermsCount)+"__C6.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_obj2ndOperand.m_nFieldType+"';");
                            eval("popUpFields('FtFterm__R"+Number(nFtFTermsCount)+"__C6',7);");
                            eval("document.formDataViewTerms.FtFterm__R"+Number(nFtFTermsCount)+"__C7.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_obj2ndOperand.m_nFieldIndex+"';");
                            nFtFTermsCount++;
                        }
                        else
                        {
                            if(top.objNewDataView.m_arrDataViewTerms[i].m_nTermType == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE%>")
                            {
                                eval("FtDVterm.RowSet.add("+Number(nFtDVTermsCount)+");");
                                eval("document.formDataViewTerms.FtDVterm__R"+Number(nFtDVTermsCount)+"__C1.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_strTermName+"';");
                                eval("document.formDataViewTerms.FtDVterm__R"+Number(nFtDVTermsCount)+"__C2.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_obj1stOperand.m_nFieldType+"';");
                                eval("popUpFields('FtDVterm__R"+Number(nFtDVTermsCount)+"__C2',3);");
                                eval("document.formDataViewTerms.FtDVterm__R"+Number(nFtDVTermsCount)+"__C3.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_obj1stOperand.m_nFieldIndex+"';");
                                eval("document.formDataViewTerms.FtDVterm__R"+Number(nFtDVTermsCount)+"__C5.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_nOperatorIndex+"';");
    //                          eval("document.formDataViewTerms.FtDVterm__R"+Number(nFtDVTermsCount)+"__C6.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_obj2ndOperand.m_nFieldType+"';");
    //                          eval("popUpFields('FtDVterm__R"+Number(nFtDVTermsCount)+"__C6');");
                                eval("document.formDataViewTerms.FtDVterm__R"+Number(nFtDVTermsCount)+"__C6.value = '"+ top.objNewDataView.m_arrDataViewTerms[Number(i)].m_obj2ndOperand.m_nDataViewIndex+"';");
                                nFtDVTermsCount++;
                              }
                        }    
                     }     
                }// End For
                if(FtFterm.getRowCount() == 0)
                  FtFterm_RowSet_add('','');
                if(FtDVterm.getRowCount() == 0)  
                  FtDVterm_RowSet_add('','');
            }// End If top
            else
            {
              eval("FtFterm_RowSet_add('','');");
              FtDVterm_RowSet_add('','');
            }
        }
        catch(e)
        {
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
      
      <center>
      <!--
        <table>
          <tr>
            <td colspan=2 align=center>
              <input type="submit" name="done" value="Done" class="button">
              <input type="Reset" name="cancel" value="Cancel" class="button">
            </td>
          </tr>
        </table>
        -->
      </center>
    </form>
  </BODY>

<script language="javascript">
function getFieldOperand(argGridName,argRowNumber,argTypeIndex)
{
    var objOperandField;
    if(eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex)+").getValue() == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>"))
    {
//        if(eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex)+").getValue() != '' && "+FieldsIDs.length+" > 0"))
//        {
              var nOperandIndex = eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex+1)+").getValue()");
              var nFieldID                = FieldsIDs[nOperandIndex];
              var strFieldName            = FieldsNames[nOperandIndex];
              var nDataViewID             = FieldsDataViewIDs[nOperandIndex];
              var strDataViewName         = FieldsDataViewNames[nOperandIndex];
              var strFieldDescription     = FieldsDescriptions[nOperandIndex];
              var nDataViewType           = FieldsDataViewTypes[nOperandIndex];
              
              objOperandField = new DataViewField(nFieldID,strFieldName,"<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>",strFieldDescription,nDataViewType);
              objOperandField.addFeatures(nDataViewID,strDataViewName,null,null,nOperandIndex);
//        }
    }
    else
    {
        if(eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex)+").getValue() == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>"))
        {
           var nOperandIndex = eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex+1)+").getValue()");
           var strName = eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex+1)+").getName()");
           var strConstantDescription     = top.objNewDataView.m_arrDataViewConstants[nOperandIndex].m_strConstantDescription;
           objOperandField = new DataViewField(nOperandIndex,eval("document.formDataViewTerms."+strName+".options[document.formDataViewTerms."+strName+".selectedIndex].text"),"<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>",strConstantDescription);
           objOperandField.addFeatures(null,null,null,null,nOperandIndex);
        }
        else
        {
            if(eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex)+").getValue() == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>"))
            {
               var nOperandIndex = eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex+1)+").getValue()");
               var strName = eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex+1)+").getName()");
               var strFunctionDescription     = top.objNewDataView.m_arrDataViewFunctions[nOperandIndex].m_strFunctionDescription;
               objOperandField = new DataViewField(nOperandIndex,eval("document.formDataViewTerms."+strName+".options[document.formDataViewTerms."+strName+".selectedIndex].text"),"<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>",strFunctionDescription);
               objOperandField.addFeatures(null,null,null,null,nOperandIndex);
            }
            else
            {
                if(eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex)+").getValue() == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>"))
                {
                   var nOperandIndex = eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex+1)+").getValue()");
                   var strParameterDescription     = top.objNewDataView.m_arrDataViewInputParameters[nOperandIndex].m_strParameterDescription;
                   var strName = eval(argGridName+".getCell("+argRowNumber+","+Number(argTypeIndex+1)+").getName()");
                   objOperandField = new DataViewField(nOperandIndex,eval("document.formDataViewTerms."+strName+".options[document.formDataViewTerms."+strName+".selectedIndex].text"),"<%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>",String(strParameterDescription));
                   objOperandField.addFeatures(null,null,null,null,nOperandIndex);  
                }
            }
        }
    }
    return objOperandField;
}

function getDataViewOperand(argRowNumber)
{
    var objOperandDataView;
    var nOperandIndex =  eval("FtDVterm.getCell("+argRowNumber+",6).getValue()");
    var nDataViewID             = DataViewIDs[nOperandIndex];
    var nDataViewVersion        = DataViewVersions[nOperandIndex];
    var strDataViewDescription  = DataViewDescriptions[nOperandIndex];
    var nDataViewType           = DataViewTypes[nOperandIndex];
    var strName = eval("FtDVterm.getCell("+argRowNumber+",6).getName()");
    objOperandDataView = new DataView(nDataViewID,eval("document.formDataViewTerms."+strName+".options[document.formDataViewTerms."+strName+".selectedIndex].text"),nDataViewVersion,strDataViewDescription,nDataViewType);
    objOperandDataView.m_nDataViewIndex = nOperandIndex;
    return objOperandDataView;
}


function addToCart()
{
  var strTermName = "";
  var strTermSQL  = ""; 
  top.objNewDataView.m_arrDataViewTerms.length = 0;
  var nTermsCount = 0;
  for(var i=1;i<=FtFterm.getRowCount();i++)
  {
      if(FtFterm.getCell(i,3).getValue() != '' && FtFterm.getCell(i,3).getValue() != 'undefined' && FtFterm.getCell(i,7).getValue()!='' && FtFterm.getCell(i,7).getValue()!='undefined')
      {

        strTermName = FtFterm.getCell(i,1).getValue();
        
        var objTerm = new DataViewTerm(0,strTermName,"<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE%>",Number(i-1));
        objTerm.m_obj1stOperand = getFieldOperand('FtFterm',i,2);
        objTerm.m_nOperatorIndex = FtFterm.getCell(i,5).getValue();
        objTerm.m_obj2ndOperand = getFieldOperand('FtFterm',i,6);
        if(strTermName == '' || strTermName == "" || strTermName == null )
        {
          strTermName = "Term:("+ Number(i)+")";
//          strTermName += objTerm.m_obj1stOperand.m_strFieldName + " " + FtFOperatorsSQLs[objTerm.m_nOperatorIndex] +" "+objTerm.m_obj2ndOperand.m_strFieldName;
        }   
        if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
          strTermSQL = objTerm.m_obj1stOperand.m_strDataViewName +"."+ objTerm.m_obj1stOperand.m_strFieldName+ " ";
        else
          if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
            strTermSQL = "'"+top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj1stOperand.m_nFieldIndex].m_strConstantValue+ "' ";
        else
          if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
            strTermSQL += top.objNewDataView.m_arrDataViewFunctions[objTerm.m_obj1stOperand.m_nFieldIndex].m_strFunctionExpression+ " ";
        else
          if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)
            strTermSQL += top.objNewDataView.m_arrDataViewInputParameters[objTerm.m_obj1stOperand.m_nFieldIndex].m_strParameterName+ " ";    
            
        var strUsedOperatorName = new String(FtFOperatorsNames[objTerm.m_nOperatorIndex]);
        var strOperatorName = new String('<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_LEFT_JOIN%>');
        if(strUsedOperatorName.toUpperCase() == strOperatorName.toUpperCase()) 
          strTermSQL +=   " (+) = ";
        else
        {
          strOperatorName = new String('<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_RIGHT_JOIN%>');
          if(strUsedOperatorName.toUpperCase() == strOperatorName.toUpperCase()) 
             strTermSQL +=   " = ";
          else   
            strTermSQL +=   FtFOperatorsSQLs[objTerm.m_nOperatorIndex]+ " ";
        }
        
        if(objTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
          strTermSQL += objTerm.m_obj2ndOperand.m_strDataViewName + "."+ objTerm.m_obj2ndOperand.m_strFieldName+ " ";
        else
          if(objTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
            strTermSQL += "'"+top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj2ndOperand.m_nFieldIndex].m_strConstantValue+ "' ";     
        else
          if(objTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
            strTermSQL += top.objNewDataView.m_arrDataViewFunctions[objTerm.m_obj2ndOperand.m_nFieldIndex].m_strFunctionExpression+ " ";
        else
          if(objTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)
            strTermSQL += top.objNewDataView.m_arrDataViewInputParameters[objTerm.m_obj2ndOperand.m_nFieldIndex].m_strParameterName+ " ";    

        strOperatorName = new String('<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_RIGHT_JOIN%>');
        if(strUsedOperatorName.toUpperCase() == strOperatorName.toUpperCase()) 
             strTermSQL +=   " (+) ";    
             
        objTerm.m_strTermName = strTermName;
        objTerm.m_strTermSQL = strTermSQL;
        top.objNewDataView.addTerm(objTerm);
        nTermsCount++;
      }
  }
  for(var i=1;i<=FtDVterm.getRowCount();i++)
  {
      if(FtDVterm.getCell(i,3).getValue() != '' && FtDVterm.getCell(i,3).getValue() != 'undefined' && FtDVterm.getCell(i,6).getValue() != '' && FtDVterm.getCell(i,6).getValue() != 'undefined')
      {

        var strTermName = FtDVterm.getCell(i,1).getValue();
        
        var objTerm = new DataViewTerm(0,strTermName,<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE%>,Number(i-1+nTermsCount));
        objTerm.m_obj1stOperand = getFieldOperand('FtDVterm',i,2);

        objTerm.m_nOperatorIndex = FtDVterm.getCell(i,5).getValue();

        objTerm.m_obj2ndOperand = getDataViewOperand(i);


        if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>)
          strTermSQL = objTerm.m_obj1stOperand.m_strDataViewName + "." + objTerm.m_obj1stOperand.m_strFieldName + " ";
        else
          if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
            strTermSQL = "'" + top.objNewDataView.m_arrDataViewConstants[objTerm.m_obj1stOperand.m_nFieldIndex].m_strConstantValue+ "' ";
        else
          if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
            strTermSQL += top.objNewDataView.m_arrDataViewFunctions[objTerm.m_obj1stOperand.m_nFieldIndex].m_strFunctionExpression+ " ";
        else
          if(objTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)
            strTermSQL += top.objNewDataView.m_arrDataViewInputParameters[objTerm.m_obj1stOperand.m_nFieldIndex].m_strParameterName+ " ";    

            
        strTermSQL +=   FtDVOperatorsSQLs[objTerm.m_nOperatorIndex]+ " ";
        strTermSQL += objTerm.m_obj2ndOperand.m_strDataViewName+ " ";
        if(strTermName == '' || strTermName == "" || strTermName == null )
        {
          strTermName = "Term ("+ Number(i)+")";
//          strTermName += objTerm.m_obj1stOperand.m_strFieldName + " " + FtDVOperatorsSQLs[objTerm.m_nOperatorIndex] +" "+objTerm.m_obj2ndOperand.m_strDataViewName;
        }  

        objTerm.m_strTermName = strTermName;
        objTerm.m_strTermSQL = strTermSQL;
        top.objNewDataView.addTerm(objTerm);
      }  
  }
}
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
                        return true;
                      }
function butHelp_onclick()
{
   var nLeft = ((window.screen.availWidth)- 500)/2  ;
   var nTop = ((window.screen.availHeight) - 500)/2;
   window.open('GN_ADHOC_DataViewWizardStepHelp.html','',"height= 500px, width= 500px,top="+nTop+",left="+nLeft);
}


</script>

</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>
</HTML>
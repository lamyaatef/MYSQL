<%@ page buffer="2048kb" %>
<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import= "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.web.interfaces.*"%>
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
      <TITLE>Function Parameters</TITLE>
   </HEAD>


 <div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>
 
   <BODY>
    <form action="Variables.html" name="formDataViewFunctionParameters" id="formDataViewFunctionParameters" method="post">
      <TABLE cellSpacing=2 cellPadding=4 width="100%" border=0>
        <TBODY>
        <TR>
          <TD class=TableHeader vAlign=top colSpan=9 id="tdFunctionName" name="tdFunctionName">
          </TD>
        </TR>
        <TR>
          <TD class=TableHeader vAlign=top colSpan=9 id="tdFunctionDescription" name="tdFunctionDescription">
          </TD>
        </TR>
        <TR>
           <TD class=TableHeader vAlign=top colSpan=9 id="tdFunctionHelpText" name="tdFunctionHelpText">
          </TD>
        </TR>
           
                  
          <TD class=TableHeader vAlign=top>
          <textarea cols=89 rows=4 id="textAreaFunctionFull" name="textAreaFunctionFull" readonly> 
          </textarea>
          </TD>
          <td class=TableHeader valign="middle">
          <input class="button" type="button" name="buttonFunction" id="buttonFunction" value="Fn()" onclick="return buttonFunction_onclick()">
          </TD>
        </TR>
        <TR>
          <TD class=TableHeader vAlign=top colSpan=9>Function Parameters
          </TD>
        </TR>
        <TR>
          <TD class=TableTextColumn vAlign=top align=middle colSpan=9>

          <TABLE id="tableAddParameters" name="tableAddParameters" cellSpacing=2 cellPadding=4 width="100%" border=0 style="display:none">
          <TBODY>
          <TR >
            <TD class=TableHeader vAlign=top colSpan=9>Add Parameter to be displayed
              <A onclick="parameter_RowSet_add('2','half_day');">
              <IMG alt="Click Here to add new parameter ... " 
              src="../resources/img/img_sign_dn.gif" border=0></A>
            </TD>
          </TR>
          <TR>
            <TABLE style="BORDER-COLLAPSE: collapse" borderColor=#000080 
            cellSpacing=0 cellPadding=0 align=left border=1>
            <TBODY>
              <TR>
                <TD class=TableTextColumn>
                  <INPUT type=hidden size=15 value=0 name=parameter_count>
     <SCRIPT>
///////Use this function to add function parameters dynamically.
      var arrParameterTypes=new Array( new Array('',''), 
      new Array('Data View Fields',"<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>"),
      new Array('Constants',"<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>"),
      new Array('Functions',"<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>"),
      new Array('Parameter','<%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>'));
      
      var arrValues=new Array( new Array('','')); 


      var objArguments         = window.dialogArguments;
      var objFunction          = objArguments[0];
      var nFunctionRowIndex    = objArguments[1];
      var arrSelectedDataViews = objArguments[2];
      var arrDataViewConstants = objArguments[3];
      var arrDataViewFunctions = objArguments[4];
      var arrFunctionParameterValues = objArguments[5];
      var arrDataViewInputParameters = objArguments[6];
      var DataViewFieldNames =new Array();
      var FieldsIDs =new Array();
      var FieldsNames =new Array();
      var FieldsDataViewIDs =new Array();
      var FieldsDataViewNames =new Array();
      var FieldsDataViewTypes =new Array();
      var FieldsDescriptions =new Array();

     var nDataViewFieldsIndex = 0;
     for(var i=0;i<arrSelectedDataViews.length;i++)
     {
        var objDataView=arrSelectedDataViews[i];
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
  
      function popUp(argObjID)
      {
          var nRowIndex;
          var nSelectedValue = 0;
          var strPopUpColumnID = new String();
          var strID= new String(argObjID);
          nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
          strPopUpColumnID = strID.substring(0,strID.indexOf("__C")+3);
          strPopUpColumnID +=  3;

// Delete all fields   
          var nCount = Number(eval("document.formDataViewFunctionParameters."+strPopUpColumnID+".length"));
          for(var i=0;i<nCount;i++)
              eval("document.formDataViewFunctionParameters."+strPopUpColumnID+".remove(0);");

          eval("nSelectedValue = document.formDataViewFunctionParameters."+strID+".value");
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
                       eval("document.formDataViewFunctionParameters."+strPopUpColumnID+".add(objOption);");
                  }  
               }
               catch(e)
               {
               }
               break;
                  
            case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>':
               try
               {
                  if(Number(arrDataViewConstants.length) > 0)
                  {                      
                    for(var i=0;i<Number(arrDataViewConstants.length);i++)
                    {
                         var objOption = document.createElement("OPTION");
                         objOption.text = arrDataViewConstants[i].m_strConstantName;
                         objOption.value = i;
                         eval("document.formDataViewFunctionParameters."+strPopUpColumnID+".add(objOption);");
                    }
                 }
               }
               catch(e)
               {
               }
               break;
            case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>':
               try
               {
                  if(Number(arrDataViewInputParameters.length) > 0)
                  {                      
                    for(var i=0;i<Number(arrDataViewInputParameters.length);i++)
                    {
                         var objOption = document.createElement("OPTION");
                         objOption.text = arrDataViewInputParameters[i].m_strParameterName;
                         objOption.value = i;
                         eval("document.formDataViewFunctionParameters."+strPopUpColumnID+".add(objOption);");
                    }
                 }
               }
               catch(e)
               {
               }
               break;   
            case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>':
            try
               {
                  if(Number(arrDataViewFunctions.length) > 0)
                  {                      
                    for(var i=0;i<Number(nFunctionRowIndex-1);i++)
                    {
                         var objOption = document.createElement("OPTION");
                         objOption.text = arrDataViewFunctions[i].m_strFunctionName;
                         objOption.value = i;
                         eval("document.formDataViewFunctionParameters."+strPopUpColumnID+".add(objOption);");
                    }
                 }
               }
               catch(e)
               {
               }
            break;
//            default: alert("No Matching");
          }
}  
       function parameter_removeRows()
       {
          i = confirm("This will remove this data")
          if (i==true){
            for(var i=parameter.getRowCount();i>=1;i--){
              if(parameter.getCell(i,6).getValue()==true){
                parameter.RowSet.deleteRow(i)
              }//end if
            }//end for
          }//end if
          else{
            for(var i=parameter.getRowCount();i>=1;i--){
              if(parameter.getCell(i,6).getValue()==true){
                parameter.getCell(i,6).setValue() == false
              }//end if
            }//end for
          }//end else
        }

        function parameter_RowSet_add(current_value,current_name)
        {
          document.formDataViewFunctionParameters.parameter_count.value = parameter.RowSet.getRowCount();
          ix = document.formDataViewFunctionParameters.parameter_count.value = parameter.RowSet.getRowCount();
          parameter.RowSet.add();
          current_name = current_name+","+current_name;
          for (i=0 ; i < arrParameterTypes.length;i++)
          {
            if (arrParameterTypes[i] == current_name)
            {
              parameter.RowSet.getCell(ix+1,2).cellElement.selectedIndex = i;
            }
          }
          for (i=0 ; i < arrValues.length;i++)
          {
            if (arrValues[i] == current_name)
            {
              parameter.RowSet.getCell(ix+1,2).cellElement.selectedIndex = i;
            }
          }
          parameter.RowSet.getCell(ix+1,1).cellElement.value = objParameterDefinitions[0].m_strParameterName;
          parameter.RowSet.getCell(ix+1,5).cellElement.value = objParameterDefinitions[0].m_strParameterDescription;
          parameter.RowSet.getCell(ix+1,1).cellElement.readOnly = true;
          parameter.RowSet.getCell(ix+1,5).cellElement.readOnly = true;
        }


        
        var arrFunctions = arrDataViewFunctions[Number(nFunctionRowIndex-1)];
        var arrFunctionParameterDefinitions = objFunction.m_arrFunctionParameters;
        var arrDataViewFields = new Array();

        var objParameterDefinitions = objFunction.m_arrFunctionParameters;
        var parameter=new DeepGrid("parameter",0,"")
        parameter.ColumnHeaders.add(null,null,"Parameter Name",null,190,"center",DG_TEXT,null)
        parameter.ColumnHeaders.add(null,null,"Parameter Type",null,190,"center",DG_SELECT,arrParameterTypes,null,"onChange = popUp(this.id)")
        parameter.ColumnHeaders.add(null,null,"Parameter Element",null,170,null,DG_SELECT,arrValues)
        parameter.ColumnHeaders.add(null,null,"(+)",null,20,"center",DG_BUTTON," (+) ",null,"onClick = popUpFields(this.id)");
        parameter.ColumnHeaders.add(null,null,"Parameter Description",null,190,null,DG_TEXTAREA,null)
        if(objParameterDefinitions.length > 0)
            if(objParameterDefinitions[0].m_nParameterIsList == 1)
                parameter.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = parameter_removeRows()")
     
       for (var i = 0; i <objParameterDefinitions.length ; i++) 
       {
            if(objParameterDefinitions[0].m_nParameterIsList == 1)
            {
                tableAddParameters.style.display = "block";
                if(arrFunctionParameterValues != null)
                { 
                      for (var i = 0; i < arrFunctionParameterValues.length; i++) 
                      {
                          eval("parameter.RowSet.add("+Number(i+1)+");");
                          eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C1.value = '"+ objParameterDefinitions[0].m_strParameterName+"';");
                          if(arrFunctionParameterValues[i] != null)
                          {
                              eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C2.value = '"+ arrFunctionParameterValues[i].m_nParameterType+"';");
                              eval("popUp('parameter__R"+Number(i+1)+"__C2');");
                              if(arrFunctionParameterValues[i].m_nParameterType == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>")
                              {
                                  eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C3.value = '"+ arrFunctionParameterValues[i].m_objField.m_nFieldIndex+"';");
                                  //alert( arrFunctionParameterValues[i].m_objField.m_nFieldIndex );  // added by khaled reda 21/6/2004
                              }
                              else
                              {
                                  eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C3.value = '"+ arrFunctionParameterValues[i].m_nParameterIndex+"';");    
                                  //alert( arrFunctionParameterValues[i].m_nParameterIndex );  // added by khaled reda 21/6/2004
                              }
                         }         
                         eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C1.readOnly = true;");         
                         eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C5.value = '"+objParameterDefinitions[0].m_strParameterDescription+"';");         
                         eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C5.readOnly = true;");         
                      }
                }      
                else
                {
                      eval("parameter_RowSet_add('','');");
                      eval("document.formDataViewFunctionParameters.parameter__R1__C1.value = '"+objParameterDefinitions[0].m_strParameterName+"';");         
                      eval("document.formDataViewFunctionParameters.parameter__R1__C1.readOnly = true;");         
                      eval("document.formDataViewFunctionParameters.parameter__R1__C5.value = '"+objParameterDefinitions[0].m_strParameterDescription+"';");         
                      eval("document.formDataViewFunctionParameters.parameter__R1__C5.readOnly = true;");         
                }      
            }
            else
            {
                eval("parameter.RowSet.add("+Number(i+1)+");");
                eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C1.value = '"+ arrFunctionParameterDefinitions[i].m_strParameterName+"';");
                if(arrFunctionParameterValues != null)
                { 
                      if(arrFunctionParameterValues[i] != null)
                      {
                          eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C2.value = '"+ arrFunctionParameterValues[i].m_nParameterType+"';");
                          eval("popUp('parameter__R"+Number(i+1)+"__C2');");
                          if(arrFunctionParameterValues[i].m_nParameterType == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>")
                          {
                             eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C3.value = '"+ arrFunctionParameterValues[i].m_objField.m_nFieldIndex+"';");
                             //alert( arrFunctionParameterValues[i].m_objField.m_nFieldIndex );  // added by khaled reda 21/6/2004
                          }
                          else
                          {
                             eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C3.value = '"+ arrFunctionParameterValues[i].m_nParameterIndex+"';");
                             //alert( arrFunctionParameterValues[i].m_nParameterIndex );  // added by khaled reda 21/6/2004
                          }
                      }       
                }      
                eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C5.value = '"+ arrFunctionParameterDefinitions[i].m_strParameterDescription+"';");
                eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C1.readOnly = true;");         
                eval("document.formDataViewFunctionParameters.parameter__R"+Number(i+1)+"__C5.readOnly = true;");         
            }    
       }
      tdFunctionName.innerText = "Function Name:" + objFunction.m_strFunctionName; 
      tdFunctionDescription.innerText = "Description:" + objFunction.m_strFunctionDescription; 
      tdFunctionHelpText.innerText = "Help:" + objFunction.m_strFunctionHelpText; 
                  </SCRIPT>
                  <SCRIPT>
//                    parameter_RowSet_add('','');
                  </SCRIPT>
                </TD>
              </TR>
            </TBODY>
            </TABLE>
          </TD>
        </TR>
      </TABLE>
      <Center>
        <table>
          <tr>
            <td colspan=2 align=center>
              <input type="button" id="butDone" name="butDone" value="Done" class="button" onclick="return butDone_onclick()">
              <input type="button" id="butCancel" name="butCancel" value="Cancel" class="button" onclick="return butCancel_onclick()">
            </td>
          </tr>
        </table>
      </Center>
    </form>
   </BODY>

   <script language="javascript">
function  popUpFields(argID)
{
      var objArguments = new Array();
      var nRowIndex;
      var nFieldType;
      var nFieldID;
      var strID= new String(argID);
      nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
      var strColumnControlID = strID.substring(0,strID.indexOf("__C")+3);
      var strFieldTypeControlIndex = strColumnControlID+2;
      var strFieldIDControlIndex = strColumnControlID+3;

      eval("nFieldType = document.formDataViewFunctionParameters."+strFieldTypeControlIndex+".value");
      if(nFieldType != '')
      {
          eval("nFieldID = document.formDataViewFunctionParameters."+strFieldIDControlIndex+".value");
      
          objArguments[1] = nFieldID;
          switch (nFieldType)
          {
            case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>':
            {
               var nIndex = 0; 
               var arrPairs=new Array();
               var arrDataViewFields = new Array();
               var arrAvailableFunctions = new Array();
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
               objArguments[2] = arrDataViewConstants;     
               break; 
            }
            case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>':
            {
               objArguments[0] = "Select a parameter field:";
               objArguments[2] = arrDataViewInputParameters;     
               break; 
            }
            case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>':
            {
                var arrAvailableFunctions = new Array();
                objArguments[0] = "Select a function field:";
                if(Number(arrDataViewFunctions.length) > 0)
                  {                      
                    for(var i=0;i<Number(nFunctionRowIndex-1);i++)
                    {
                         arrAvailableFunctions[i] = arrDataViewFunctions[i].m_strFunctionName;
                    }
                 }
                objArguments[2] = arrAvailableFunctions;      
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
             popUp(strFieldTypeControlIndex); 
             eval("document.formDataViewFunctionParameters.parameter__R"+nRowIndex+"__C3.value = '"+objWin[0]+"'");
          } 
     }
     else
        alert("Please select a parameter type from the types list");
}
function  buttonFunction_onclick()
{
  var strFunction = new String(objFunction.m_strFunctionName);
  strFunction += "("
  for (var i = 1; i <=parameter.getRowCount(); i++) 
  {
      if(parameter.getCell(i,3).getValue() != '')
      {
        if(parameter.getCell(i,2).getValue() == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
        {
           strFunction += "'"+arrDataViewConstants[parameter.getCell(i,3).getValue()].m_strConstantValue+"' ";
        }
        else if(parameter.getCell(i,2).getValue() == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)
        {
           strFunction += "'"+arrDataViewInputParameters[parameter.getCell(i,3).getValue()].m_strParameterValue+"' ";
        }
        else
        if(parameter.getCell(i,2).getValue() == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)
        {
           strFunction += arrDataViewFunctions[parameter.getCell(i,3).getValue()].m_strFunctionExpression;
        }
        else
           strFunction += eval("document.formDataViewFunctionParameters.parameter__R"+i+"__C3.options[document.formDataViewFunctionParameters.parameter__R"+i+"__C3.selectedIndex].text");
        strFunction +=",";
      }  
      else
      if(objParameterDefinitions[Number(i-1)].m_nParameterIsOptional == 0)
      {
        alert(" The parameter: ["+i+"] is not optional, please select this parameter");
        return false;
      }  
  }
  if(strFunction.charAt(strFunction.length-1) == ",")
    strFunction = strFunction.substring(0,strFunction.length-1);
  strFunction +=")";
  document.formDataViewFunctionParameters.textAreaFunctionFull.value = strFunction;
}

function butDone_onclick()
{
    var strFunction = new String(objFunction.m_strFunctionName);
    strFunction += "("

    var arrReturn = new Array();  
    var arrParameterValues = new Array();  
    for (var i = 1; i <=parameter.getRowCount(); i++) 
    {
      if(parameter.getCell(i,3).getValue() != '')
      {
        if(parameter.getCell(i,2).getValue() == <%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>)
        {
           if(objParameterDefinitions[0].m_nParameterIsList == 1)
              var objParameterValue = new FunctionParameterValue(objParameterDefinitions[0].m_nParameterID,parameter.getCell(i,2).getValue())
            else
              var objParameterValue = new FunctionParameterValue(objParameterDefinitions[Number(i-1)].m_nParameterID,parameter.getCell(i,2).getValue())
       
           objParameterValue.addOtherParameterData(parameter.getCell(i,3).getValue(),arrDataViewConstants[parameter.getCell(i,3).getValue()].m_strConstantValue);
           strFunction += "'"+arrDataViewConstants[parameter.getCell(i,3).getValue()].m_strConstantValue+"' ";
        }
        else if(parameter.getCell(i,2).getValue() == <%=QueryBuilderInterfaceKey.FIELD_TYPE_PARAMETER_FIELD%>)
        {
           if(objParameterDefinitions[0].m_nParameterIsList == 1)
              var objParameterValue = new FunctionParameterValue(objParameterDefinitions[0].m_nParameterID,parameter.getCell(i,2).getValue())
            else
              var objParameterValue = new FunctionParameterValue(objParameterDefinitions[Number(i-1)].m_nParameterID,parameter.getCell(i,2).getValue())
       
           objParameterValue.addOtherParameterData(parameter.getCell(i,3).getValue(),arrDataViewInputParameters[parameter.getCell(i,3).getValue()].m_strParameterValue);
           strFunction += "'"+arrDataViewInputParameters[parameter.getCell(i,3).getValue()].m_strParameterValue+"' ";
        }
        else
        {
            if(parameter.getCell(i,2).getValue() == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%> && FieldsIDs.length > 0)            
            {
                 if(objParameterDefinitions[0].m_nParameterIsList == 1)
                   var objParameterValue = new FunctionParameterValue(objParameterDefinitions[0].m_nParameterID,parameter.getCell(i,2).getValue())
                 else
                   var objParameterValue = new FunctionParameterValue(objParameterDefinitions[Number(i-1)].m_nParameterID,parameter.getCell(i,2).getValue())
      
                  var objOperandField;
                  var nOperandIndex           = parameter.getCell(i,3).getValue();
                  var nFieldID                = FieldsIDs[nOperandIndex];
                  var strFieldName            = FieldsNames[nOperandIndex];
                  var nDataViewID             = FieldsDataViewIDs[nOperandIndex];
                  var strDataViewName         = FieldsDataViewNames[nOperandIndex];
                  var strFieldDescription     = FieldsDescriptions[nOperandIndex];
                  var nDataViewType           = FieldsDataViewTypes[nOperandIndex];
                  objOperandField = new DataViewField(nFieldID,strFieldName,"<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>",strFieldDescription,nDataViewType);
                  objOperandField.addFeatures(nDataViewID,strDataViewName,null,null,nOperandIndex);
                  objParameterValue.addFieldParameterData(objOperandField);
                  strFunction += strDataViewName+"."+strFieldName;
                 
             }
             else
             {
                  if(parameter.getCell(i,2).getValue() == <%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>)                 
                  {
                     if(objParameterDefinitions[0].m_nParameterIsList == 1)
                        var objParameterValue = new FunctionParameterValue(objParameterDefinitions[0].m_nParameterID,parameter.getCell(i,2).getValue())
                     else
                        var objParameterValue = new FunctionParameterValue(objParameterDefinitions[Number(i-1)].m_nParameterID,parameter.getCell(i,2).getValue())
      
                     objParameterValue.addOtherParameterData(parameter.getCell(i,3).getValue(),null);
                     strFunction += arrDataViewFunctions[parameter.getCell(i,3).getValue()].m_strFunctionExpression;
                  }
             }
         }
        arrParameterValues[Number(i-1)] = objParameterValue;
        strFunction +=",";
      }  
      else
      {
          if(objParameterDefinitions[Number(i-1)].m_nParameterIsOptional == 0)
          {
            alert(" The parameter: ["+i+"] is not optional, please select this parameter");
            return false;
          }
          arrParameterValues[Number(i-1)] = null;  
      }    
    }
    if(strFunction.charAt(strFunction.length-1) == ",")
    strFunction = strFunction.substring(0,strFunction.length-1);
        strFunction +=")";
        
    arrReturn[0] = strFunction;
    arrReturn[1] = arrParameterValues;
    window.returnValue =arrReturn;
    window.close();
}
function butCancel_onclick()
{
  window.close();
}

   </script>

</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>

</HTML>

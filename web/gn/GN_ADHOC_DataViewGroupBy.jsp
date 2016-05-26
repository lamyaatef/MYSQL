<%@ page buffer="256kb" %>
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
      <TITLE>Data View Group By</TITLE>
   </HEAD>

   <div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>
   
   <BODY leftmargin=3 topmargin=0>
 <!--   <Center>
      <H2>Data View Group By</H2>
    </Center>-->
    <FORM id=formDataViewGroupBy name=formDataViewGroupBy action="" method=post>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
           </tr>
            <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
           </tr>
           <tr class=TableHeader>
            <td class=TableHeader><font color=darkblue size=3>Data View Group By</td>
           </tr>
          <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
           </tr>

        <TR>
          <TD class=TableHeader vAlign=top colSpan=9>Add group by clause
            <A onclick="field_RowSet_add('2','half_day');">
            <IMG alt="Click Here to add new group by ... " 
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
                  <INPUT type=hidden size=15 value=0 name=field_count>
                  <SCRIPT>
    function move_up(argobj)
    {
          var i = argobj.charAt(13);
          var k = argobj.charAt(14);
          if(k=="_")
          {
            
          }
          else
          {
              i = i+k;
          }
          if(i==1)
          {
              return;
          }
          if(document.getElementById("field__R"+i+"__C1"))
          {
          var tempfiledType = document.getElementById("field__R"+i+"__C1").value;
          var tempfiledName = document.getElementById("field__R"+i+"__C2").value;
          selectlist = eval("document.formDataViewGroupBy.field__R"+i+"__C2");
          var j = i-1;
          while(!document.getElementById("field__R"+j+"__C1"))
          {
            j=j-1;
            if(j==0)
            {
            return;
            }
          }  
          var tempfiledTypeabove = document.getElementById("field__R"+j+"__C1").value;
          var tempfiledNameabove = document.getElementById("field__R"+j+"__C2").value;
          selectlistabove = eval("document.formDataViewGroupBy.field__R"+j+"__C2");
          
          var selectlistabovearr = new Array()
          for(var m=0;m<selectlistabove.options.length;m++)
          {
              selectlistabovearr[m] = selectlistabove.options[m].value+"&"+selectlistabove.options[m].text
          }
          var selectlistarr = new Array()
          for(var s=0;s<selectlist.options.length;s++)
          {
              selectlistarr[s] = selectlist.options[s].value+"&"+selectlist.options[s].text
          }
          
          selectnameX = "field__R"+i+"__C2";
          selectnameaboveX = "field__R"+j+"__C2";
          removeAllOptions(selectnameX);
          removeAllOptions(selectnameaboveX);

          document.getElementById("field__R"+i+"__C1").value = tempfiledTypeabove;
          for(var f=0;f<selectlistabovearr.length;f++)
          {
              optionX = selectlistabovearr[f].split("&");
              optionvalueX = optionX[0];
              optiontextX = optionX[1];
              addOption(optionvalueX,optiontextX,selectnameX);
          }
          document.getElementById("field__R"+i+"__C2").value = tempfiledNameabove;
          
          document.getElementById("field__R"+j+"__C1").value = tempfiledType;
          for(var n=0;n<selectlistarr.length;n++)
          {
              optionY = selectlistarr[n].split("&");
              optionvalueY = optionY[0];
              optiontextY = optionY[1];
              addOption(optionvalueY,optiontextY,selectnameaboveX);
          }          
          document.getElementById("field__R"+j+"__C2").value = tempfiledName;
          }
          else
          {
          return;
          }
    }

    
		function removeAllOptions(selectname)
		{
		var elSel = document.getElementById(selectname);
		if (elSel.length == 0) 
			{
		  	return;
		  }
		elSel.remove(0);
		removeAllOptions(selectname)  	
		}
		      
		function addOption(optvalue,opttext,selectname)
		{
			var elSel = document.getElementById(selectname);
			var elOptNew = new Option();
		
			elOptNew.text =  opttext;
			elOptNew.value =  optvalue;
			
			elSel.options[elSel.length] = elOptNew;		
		}
    function move_down(argobj)
    {
          var i = argobj.charAt(13);
          var k = argobj.charAt(14);
          if(k=="_")
          {
              i = (i*1)+1;
          }
          else
          {
              i = i+k;
              i = (i*1)+1;
          }
          if(document.getElementById("field__R"+i+"__C1"))
          {
          var tempfiledType = document.getElementById("field__R"+i+"__C1").value;
          var tempfiledName = document.getElementById("field__R"+i+"__C2").value;
          selectlist = eval("document.formDataViewGroupBy.field__R"+i+"__C2");
          var j = i-1;
          while(!document.getElementById("field__R"+j+"__C1"))
          {
            j=j-1;
            if(j==0)
            {
            return;
            }
          }  
          var tempfiledTypeabove = document.getElementById("field__R"+j+"__C1").value;
          var tempfiledNameabove = document.getElementById("field__R"+j+"__C2").value;
          selectlistabove = eval("document.formDataViewGroupBy.field__R"+j+"__C2");
          
          var selectlistabovearr = new Array()
          for(var m=0;m<selectlistabove.options.length;m++)
          {
              selectlistabovearr[m] = selectlistabove.options[m].value+"&"+selectlistabove.options[m].text
          }
          var selectlistarr = new Array()
          for(var s=0;s<selectlist.options.length;s++)
          {
              selectlistarr[s] = selectlist.options[s].value+"&"+selectlist.options[s].text
          }
          
          selectnameX = "field__R"+i+"__C2";
          selectnameaboveX = "field__R"+j+"__C2";
          removeAllOptions(selectnameX);
          removeAllOptions(selectnameaboveX);

          document.getElementById("field__R"+i+"__C1").value = tempfiledTypeabove;
          for(var f=0;f<selectlistabovearr.length;f++)
          {
              optionX = selectlistabovearr[f].split("&");
              optionvalueX = optionX[0];
              optiontextX = optionX[1];
              addOption(optionvalueX,optiontextX,selectnameX);
          }
          document.getElementById("field__R"+i+"__C2").value = tempfiledNameabove;
          
          document.getElementById("field__R"+j+"__C1").value = tempfiledType;
          for(var n=0;n<selectlistarr.length;n++)
          {
              optionY = selectlistarr[n].split("&");
              optionvalueY = optionY[0];
              optiontextY = optionY[1];
              addOption(optionvalueY,optiontextY,selectnameaboveX);
          }          
          document.getElementById("field__R"+j+"__C2").value = tempfiledName;
          }
          else
          {
          return;
          }
    }

                  
    var DataViewFieldNames =new Array();
    var FieldsIDs =new Array();
    var FieldsNames =new Array();
    var FieldsDataViewIDs =new Array();
    var FieldsDataViewNames =new Array();
    var FieldsDataViewTypes =new Array();
    var FieldsDescriptions =new Array();

    var FieldTypesArray=new Array( 
    new Array('',''), 
    new Array('Data View Field','<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>'),
//    new Array('Constant','<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>'),
    new Array('Function','<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>'));

    var fieldArray=new Array( 
    new Array('',''));
                  

      try
      {   
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
              //out.println("khaled: m_strFieldName: " + m_strFieldName);  // added by khaled reda 23/6/2004
              FieldsDescriptions[nDataViewFieldsIndex] = objDataView.m_arrDataViewFields[j].m_strFieldDescription;
              nDataViewFieldsIndex++;
            }  
          }
      }
      catch(e)
      {

      }
      function popUpFieldsWindow(argID)
      {
            var objArguments = new Array();
            var nRowIndex;
            var nFieldType;
            var nFieldID;
            var strID= new String(argID);
            nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
            var strColumnControlID = strID.substring(0,strID.indexOf("__C")+3);
            var strFieldTypeControlIndex = strColumnControlID+1;
            var strFieldIDControlIndex = strColumnControlID+2;

            eval("nFieldType = document.formDataViewGroupBy."+strFieldTypeControlIndex+".value");
            if(nFieldType != '')
            {
                eval("nFieldID = document.formDataViewGroupBy."+strFieldIDControlIndex+".value");
      
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
                  case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>':
                  {
                      var arrAvailableFunctions = new Array();
                      objArguments[0] = "Select a function field:";
                      for(var j =0;j<Number(top.objNewDataView.m_arrDataViewFunctions.length);j++)    
                      {
                           strFieldName = top.objNewDataView.m_arrDataViewFunctions[j].m_strFunctionName;
                           arrAvailableFunctions[j] = strFieldName;
                           //alert("strFieldName: " + strFieldName);   // added by khaled reda 23/6/2004
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
                   popUpFields(strFieldTypeControlIndex,objWin[0]); 
                   eval("document.formDataViewGroupBy.field__R"+nRowIndex+"__C2.value = '"+objWin[0]+"'");
                } 
           }
           else
              alert("Please select a field type from the types list");  
      }

      function popUpFields(argObjID)
      {
          var nRowIndex;
          var nSelectedValue = 0;
          var strPopUpColumnID = new String();
          var strID= new String(argObjID);
          nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
          strPopUpColumnID = strID.substring(0,strID.indexOf("__C")+3);
          strPopUpColumnID += 2;

        // Delete all fields   
            var nCount = Number(eval("document.formDataViewGroupBy."+strPopUpColumnID+".length"));
            for(var i=0;i<nCount;i++)
                eval("document.formDataViewGroupBy."+strPopUpColumnID+".remove(0);");

      eval("nSelectedValue = document.formDataViewGroupBy."+strID+".value");
      
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
                          //alert("strFieldName: " + strFieldName);   // added by khaled reda 23/6/2004
                       }
                       objOption.text = strFieldName;
                       objOption.value = arrPairs[j+1];
                       //alert("objOption.value: " + objOption.value);  // added by khaled reda 23/6/2004
                       strFieldName = "";
                       eval("document.formDataViewGroupBy."+strPopUpColumnID+".add(objOption);");
              }  
//            popUpDescription(strPopUpColumnID,0);
           }
           catch(e)
           {
           }
           break;
                  
/*        case '<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>':
           try
           {
              if(Number(top.objNewDataView.m_arrDataViewConstants.length) > 0)
              {                      
                for(var i=0;i<Number(top.objNewDataView.m_arrDataViewConstants.length);i++)
                {
                     var objOption = document.createElement("OPTION");
                     objOption.text = top.objNewDataView.m_arrDataViewConstants[i].m_strConstantName;
                     objOption.value = i;
                     eval("document.formDataViewGroupBy."+strPopUpColumnID+".add(objOption);");
                }
             }
//             popUpDescription(strPopUpColumnID,0);

           }
           catch(e)
           {
           }
           break;*/
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
                     //alert("objOption.value: " + objOption.value + " and objOption.text: " + objOption.text);   // added by khaled reda 23/6/2004
                     eval("document.formDataViewGroupBy."+strPopUpColumnID+".add(objOption);");
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

      function field_RowSet_add(current_value,current_name)
      {
        document.formDataViewGroupBy.field_count.value = field.RowSet.getRowCount();
        ix = document.formDataViewGroupBy.field_count.value = field.RowSet.getRowCount();
        field.RowSet.add();
        current_name = current_name+","+current_name;
        for (i = 0; i < fieldArray.length; i++)
        {
          if (fieldArray[i] == current_value)
          {
            field.RowSet.getCell(ix+1,1).cellElement.selectedIndex = i;
          }
        }
        for (i = 0; i < FieldTypesArray.length; i++)
        {
          if (FieldTypesArray[i] == current_value)
          {
            field.RowSet.getCell(ix+1,1).cellElement.selectedIndex = i;
          }
        }
      }

                    function field_removeRows()
                    {
                      i = confirm("This will remove this data")
                      if (i==true){
                        for(var i=field.getRowCount();i>=1;i--){
                          if(field.getCell(i,4).getValue()==true){
                            field.RowSet.deleteRow(i)
                          }//end if
                        }//end for
                      }//end if
                      else{
                        for(var i=field.getRowCount();i>=1;i--){
                          if(field.getCell(i,4).getValue()==true){
                            field.getCell(i,4).setValue() == false
                          }//end if
                        }//end for
                      }//end else
                    }
                    var field=new DeepGrid("field",0,"")
                    field.ColumnHeaders.add(null,null,"Field Type",null,150,"center",DG_SELECT,FieldTypesArray,null,"onChange = popUpFields(this.id)")
                    field.ColumnHeaders.add(null,null,"Field Name",null,540,"center",DG_SELECT,fieldArray)
                    field.ColumnHeaders.add(null,null,"(+)",null,10,"center",DG_BUTTON," (+) ",null,"onClick = popUpFieldsWindow(this.id)");
                    field.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = field_removeRows()")
                    field.ColumnHeaders.add(null,null,"Up",null,20,null,DG_IMAGE,"../resources/img/up.gif",null,"onClick = move_up(this.name);")
                    field.ColumnHeaders.add(null,null,"Down",null,20,null,DG_IMAGE,"../resources/img/down.gif",null,"onClick = move_down(this.name);")
                  </SCRIPT>
                  <SCRIPT>
//                    field_RowSet_add('','');
    try
    {
            var nFieldsCount = 1;
            if(top.objNewDataView.m_arrDataViewGroupBy.length > 0)
            {
               //alert("top.objNewDataView.m_arrDataViewGroupBy.length = " + top.objNewDataView.m_arrDataViewGroupBy.length);   // added by khaled reda 23/6/2004
               for(var i=0;i< Number(top.objNewDataView.m_arrDataViewGroupBy.length); i++)
               {
                   if(top.objNewDataView.m_arrDataViewGroupBy[i] != null)
                   {
                       //alert("m_nGroupByIndex: " + m_nGroupByIndex);  // added by khaled reda 23/6/2004
                       eval("field.RowSet.add("+Number(nFieldsCount)+");");
                       eval("document.formDataViewGroupBy.field__R"+Number(nFieldsCount)+"__C1.value = '"+ top.objNewDataView.m_arrDataViewGroupBy[i].m_nGroupByElementTypeID+"';");
                       eval("popUpFields('field__R"+Number(nFieldsCount)+"__C1');");
                       eval("document.formDataViewGroupBy.field__R"+Number(nFieldsCount)+"__C2.value = '"+ top.objNewDataView.m_arrDataViewGroupBy[i].m_nGroupByIndex+"';");
                       nFieldsCount++;
                    }
               }// End For
               if(field.getRowCount() == 0)
               {
                  field_RowSet_add('','');
               }
            }
            else
            {
                 field_RowSet_add('','');
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
    </form>
  </BODY>
<SCRIPT LANGUAGE=javascript>


function addToCart()
{
  top.objNewDataView.m_arrDataViewGroupBy.length = 0;

  for(var i=1;i<=field.getRowCount();i++)
  {
    if(field.getCell(i,1).getValue() == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>")
    {
          if(field.getCell(i,2).getValue() != '' && FieldsIDs.length > 0)
          {
              var nIndex = field.getCell(i,2).getValue();
              var nFieldID            = FieldsIDs[nIndex];
              var strFieldName        = FieldsNames[nIndex];
              var nDataViewID         = FieldsDataViewIDs[nIndex];
              var strDataViewName     = FieldsDataViewNames[nIndex];
              var nDataViewType       = FieldsDataViewTypes[nIndex];
              var strFieldDescription = FieldsDescriptions[nIndex];

              var objSelectedField = new DataViewField(nFieldID,strFieldName,"<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>",strFieldDescription,nDataViewType);
              objSelectedField.addFeatures(nDataViewID,strDataViewName,false,null,nIndex);

              var objGroupBy = new DataViewGroupBy(0,'<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>');
              objGroupBy.m_objGroupByField = objSelectedField;
              objGroupBy.m_nGroupByIndex = nIndex;
              top.objNewDataView.addGroupBy(objGroupBy);
          }
      }
/*    else
      {
          if(field.getCell(i,1).getValue() == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>")
          {
              var nIndex = field.getCell(i,2).getValue();
              var objSelectedField = new DataViewField(field.getCell(i,2).getValue(),top.objNewDataView.m_arrDataViewConstants[nIndex],"<%=QueryBuilderInterfaceKey.FIELD_TYPE_CONST_FIELD%>",field.getCell(i,4).getValue());
//            objSelectedField.addFeatures(null,null,field.getCell(i,5).getValue(),field.getCell(i,3).getValue(),field.getCell(i,2).getValue());
              objSelectedField.addFeatures(null,null,false,field.getCell(i,3).getValue(),field.getCell(i,2).getValue());
              top.objNewDataView.m_arrDataViewFields[top.objNewDataView.m_arrDataViewFields.length] = objSelectedField;
          }*/
          else
          {
              if(field.getCell(i,1).getValue() == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>")
              {
             
                var nIndex = field.getCell(i,2).getValue();              
                var objSelectedField = new DataViewField(nFieldID,strFieldName,"<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>",strFieldDescription,nDataViewType);  // commented by khaled reda 29/6/2004
                objSelectedField.addFeatures(nDataViewID,strDataViewName,false,null,nIndex);                                  
                var objGroupBy = new DataViewGroupBy(0,'<%=QueryBuilderInterfaceKey.FIELD_TYPE_FUNCTION_FIELD%>');                
                objGroupBy.m_objGroupByField = objSelectedField;  
                objGroupBy.m_nGroupByIndex = nIndex;
                top.objNewDataView.addGroupBy(objGroupBy);
              }
//          }
      }
   }  
}

function butHelp_onclick()
{
   var nLeft = ((window.screen.availWidth)- 500)/2  ;
   var nTop = ((window.screen.availHeight) - 500)/2;
   window.open('GN_ADHOC_DataViewWizardStepHelp.html','',"height= 500px, width= 500px,top="+nTop+",left="+nLeft);
}

</SCRIPT>


  </div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>

</HTML>

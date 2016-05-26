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
      <TITLE>Data View Having Clause</TITLE>
   </HEAD>

   <div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>
   
   <BODY leftmargin=0 topmargin=0>
<!--    <Center>
      <H2>Define Data View Having</H2>
    </Center>-->
    <FORM id="formDataViewHaving" name="formDataViewHaving" action="" method=post>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr class=TableHeader>
            <td class=TableHeader colspan=9>&nbsp;</td>
           </tr>
           <tr class=TableHeader>
            <td class=TableHeader colspan=9><font color=darkblue size=3>Data View Having Clause</td>
           </tr>
          <tr class=TableHeader>
            <td class=TableHeader colspan=9>&nbsp;</td>
           </tr>

        <tr>
            <td class=TableHeader valign=top colSpan=9>Having statement</td>
        </tr>
        <tr>
            <td  colspan=2 class=TableHeader><textarea readonly=true id="DataViewHavingSQL" name="DataViewHavingSQL" cols="93" rows="5"></textarea></td><td class=TableHeader valign="middle">
            <input type="button" class="button" value="SQL" id="butShowSQL" name="butShowSQL" onClick="return butShowSQL_onclick()"></td>
        </tr>
        <TR>
          <TD class=TableHeader vAlign=top colSpan=9>Add Having
            <A onclick="having_RowSet_add('','');">
            <IMG alt="Click Here to add new having ... " 
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
                  <INPUT type=hidden size=15 value=0 name=having_count>
                  <SCRIPT>

/////////////////////////////////////////////////////////////////////////////////
// Get the relation symbols and teh condition elements types from the top object objDataViewWizard 
//////////////////////////////////////////////////////////////////////////////////
        var RelationSymbols =new Array();
//        var RelationSymbolsIDs =new Array();
//        var RelationSymbolsSQLs =new Array();

        var ConditionElementTypes =new Array(new Array('',''));

                    
        try
        {   
            
            var objRelationSymbolArray = top.objDataViewWizard.m_arrDataViewRelationSymbols;
            
            var nRelationSymbolIndex = 0;

            for(var j=0;j<objRelationSymbolArray.length;j++)
            {
                var objRelationSymbol = objRelationSymbolArray[j]
                RelationSymbols[nRelationSymbolIndex] = new Array(objRelationSymbol.m_strRelationSymbolName,j);
//                RelationSymbolsIDs[nRelationSymbolIndex] = objRelationSymbol.m_nRelationSymbolID;
//                RelationSymbolsSQLs[nRelationSymbolIndex] = objRelationSymbol.m_strRelationSymbolSQL;
                nRelationSymbolIndex++;
            }    

            var objConditionElementTypeArray = top.objDataViewWizard.m_arrDataViewConditionElementTypes;
            for(var i=0;i<objConditionElementTypeArray.length;i++)
            {
                var objConditionElementType = objConditionElementTypeArray[i]
                ConditionElementTypes[Number(i+1)] = new Array(objConditionElementType.m_strConditionElementTypeName,objConditionElementType.m_nConditionElementTypeID);
            }
      }
      catch(e)
      {
      }
            var criteriaArray=new Array( 
            new Array('',''));


function  butShowSQL_onclick() 
{
  document.formDataViewHaving.DataViewHavingSQL.value = "";
  var strSQL = "";
  for(i=1;i<=having.getRowCount();i++)
  {
      if(having.getCell(i,2).getValue() != '')
      {
          if(having.getCell(i,1).getValue() == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
          {
              var objTerm = top.objNewDataView.m_arrDataViewTerms[having.getCell(i,2).getValue()];
              strSQL += objTerm.m_strTermSQL + " ";
          }
          else
          {
              if(having.getCell(i,1).getValue() == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL%>")
              {
                var objRelationSymbol = top.objDataViewWizard.m_arrDataViewRelationSymbols[having.getCell(i,2).getValue()];
                strSQL += objRelationSymbol.m_strRelationSymbolSQL+ " ";
              }
          }
      }
  }    
  document.formDataViewHaving.DataViewHavingSQL.value = strSQL;
}

  function popUp(argObjID)
  {
      var nRowIndex;
      var nSelectedValue = 0;
      var strPopUpColumnID = new String();
      var strID= new String(argObjID);
      nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
      strPopUpColumnID = strID.substring(0,strID.indexOf("__C")+3);
      strPopUpColumnID += 2;

      // Delete all havings   
      var nCount = Number(eval("document.formDataViewHaving."+strPopUpColumnID+".length"));
      for(var i=0;i<nCount;i++)
          eval("document.formDataViewHaving."+strPopUpColumnID+".remove(0);");

      eval("nSelectedValue = document.formDataViewHaving."+strID+".value");
      
      switch (nSelectedValue)
      {
        case '<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>':
           try
           {
             for(var j =0;j<top.objNewDataView.m_arrDataViewTerms.length;j++)    
             {
                  var objTerm = top.objNewDataView.m_arrDataViewTerms[j];
                  if(objTerm != null) 
                  {
                       var objOption = document.createElement("OPTION");
                       objOption.text = objTerm.m_strTermName;
                       objOption.value = objTerm.m_nTermIndex;
                       eval("document.formDataViewHaving."+strPopUpColumnID+".add(objOption);");
                  }   
              }  
           }
           catch(e)
           {
           }
           break;
                
        case '<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL%>':
           try
           {
                 var arrPairs=new Array();
                 var strPairs=new String(RelationSymbols);
                 arrPairs = strPairs.split(",");
                 for(var j =0;j<arrPairs.length;j=j+2)    
                 {
                      var objOption = document.createElement("OPTION");
                      objOption.text = arrPairs[j];
                      objOption.value = arrPairs[j+1];
                      eval("document.formDataViewHaving."+strPopUpColumnID+".add(objOption);");
                }
          }
           catch(e)
           {
           }
           break;
        case 3:
//        default: alert("No Matching");
        
      }
  }


                    function having_RowSet_add(current_value,current_name)
                    {
                      document.formDataViewHaving.having_count.value = having.RowSet.getRowCount();
                      ix = document.formDataViewHaving.having_count.value = having.RowSet.getRowCount();
                      having.RowSet.add();
                      current_name = current_name+","+current_name;
                      for (i = 0; i < ConditionElementTypes.length; i++)
                      {
                        if (ConditionElementTypes[i] == current_value)
                        {
                          having.RowSet.getCell(ix+1,1).cellElement.selectedIndex = i;
                        }
                      }
                      for (i = 0; i < criteriaArray.length; i++)
                      {
                        if (criteriaArray[i] == current_value)
                        {
                          having.RowSet.getCell(ix+1,2).cellElement.selectedIndex = i;
                        }
                      }
                    }

                    function having_removeRows()
                    {
                      i = confirm("This will remove this data")
                      if (i==true){
                        for(var i=having.getRowCount();i>=1;i--){
                          if(having.getCell(i,3).getValue()==true){
                            having.RowSet.deleteRow(i)
                          }//end if
                        }//end for
                      }//end if
                      else{
                        for(var i=having.getRowCount();i>=1;i--){
                          if(having.getCell(i,3).getValue()==true){
                            having.getCell(i,3).setValue() == false
                          }//end if
                        }//end for
                      }//end else
                    }
                    var having=new DeepGrid("having",0,"")
                    having.ColumnHeaders.add(null,null,"Type",null,250,"center",DG_SELECT,ConditionElementTypes,null,"onChange = popUp(this.id)")
                    having.ColumnHeaders.add(null,null,"Element",null,450,"center",DG_SELECT,criteriaArray)
                    having.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = having_removeRows()")
                  </SCRIPT>
                  <SCRIPT>

        try
        {
            var nHavingCount = 1;
            if(top.objNewDataView.m_arrDataViewHavingElements.length > 0)
            {
               for(var i=0;i< Number(top.objNewDataView.m_arrDataViewHavingElements.length); i++)
               {
                    if(top.objNewDataView.m_arrDataViewHavingElements[i] != null)
                    {
                          eval("having.RowSet.add("+Number(nHavingCount)+");");
                          eval("document.formDataViewHaving.having__R"+Number(nHavingCount)+"__C1.value = '"+ top.objNewDataView.m_arrDataViewHavingElements[i].m_nElementTypeID+"';");
                          eval("popUp('having__R"+Number(nHavingCount)+"__C1');");
                          eval("document.formDataViewHaving.having__R"+Number(nHavingCount)+"__C2.value = '"+ top.objNewDataView.m_arrDataViewHavingElements[i].m_nElementIndex+"';");
                          nHavingCount++;
                    }
               }
               if(having.getRowCount() == 0)
               {
                 having_RowSet_add('','');
               }
                eval("butShowSQL_onclick();");
           }
           else
           {
                having_RowSet_add('','');
           }
        }
        catch(e)
        {
        }
                  
               //     having_RowSet_add('','');
                  </SCRIPT>
                </TD>
              </TR>
            </TBODY>
            </TABLE>
          </TD>
        </TR>
                          <tr colspan=9>
          <td align=center colspan=9 class=TableHeader>    <input type="button" name="butHelp" value="Help" class="button" onclick="return butHelp_onclick()"></td>
          </tr>
          <tr colspan=9>
          <td class=TableHeader colspan=9>&nbsp;</td>
          </tr>
                    <tr colspan=9>
          <td class=TableHeader colspan=9>&nbsp;</td>
          </tr>

      </TABLE>
    </form>
  </BODY>
 <script language = "javascript">



function addToCart()
{
  top.objNewDataView.m_arrDataViewHavingElements.length = 0;

  for(i=1;i<=having.getRowCount();i++)
  {
      if(having.getCell(i,2).getValue() != '')
      {
          if(having.getCell(i,1).getValue() == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>")
          {
              var objTerm = top.objNewDataView.m_arrDataViewTerms[having.getCell(i,2).getValue()];
              var objHavingElement = new HavingElement(<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM%>);
              objHavingElement.m_nElementIndex = having.getCell(i,2).getValue();
              top.objNewDataView.addHavingElement(objHavingElement);
          }
          else
          {
              if(having.getCell(i,1).getValue() == "<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL%>")
              {
                var objRelationSymbol = top.objDataViewWizard.m_arrDataViewRelationSymbols[having.getCell(i,2).getValue()];
                var objHavingElement = new HavingElement(<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_SYMBOL%>);
                objHavingElement.m_nRelationSymbolID = objRelationSymbol.m_nRelationSymbolID;
                objHavingElement.m_strRelationSymbolSQL = objRelationSymbol.m_strRelationSymbolSQL; 
                objHavingElement.m_nElementIndex = having.getCell(i,2).getValue();
                top.objNewDataView.addHavingElement(objHavingElement);
              }
          }
      }
  }
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

<%@ page buffer="256kb" %>
<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.IOException"
         import="java.io.PrintWriter"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"
%>
<%!
/**
 * Retrieve all the data needed for representing the query in the GUI
 * @param argRequest HttpServletRequest,argOut JspWriter 
 * @throws Exception if the IO operation failed
 * @return 
 * @see 
 */
 
 private void fillDataViewWizard(HttpServletRequest argRequest,JspWriter argOut) throws IOException
 {

    StringBuffer strDataViewScript;
    
    HashMap hmSelectedDataViews                              = new HashMap();
    HashMap hmData                                           = new HashMap(100);
    
    QueryDTO dtoQuery                                        = null;                            
    Vector colSelectedDataViewIDs                            ; 
            
    hmData = (HashMap)argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
    dtoQuery = (QueryDTO)hmData.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
//    dtoQuery = (QueryDTO)dtoQueryBuilderWizard.getQuery();
    hmSelectedDataViews = dtoQuery.getDetailedDataViews();
    strDataViewScript = new StringBuffer(" top.objNewDataView.m_strDataViewName = '"+dtoQuery.getQueryName()+"';  top.objNewDataView.m_strDataViewDescription ='"+dtoQuery.getQueryDescription()+"';");
    colSelectedDataViewIDs = dtoQuery.getSelectedDataViews();
    strDataViewScript.append(fillSelectedDataViews(hmSelectedDataViews,colSelectedDataViewIDs));
    argOut.println("<script language=\"javascript\"> ");
    argOut.println(strDataViewScript.toString());
    argOut.println(" </script>");
 }
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
/**
 * Retrieve all the selected data views data including the available fields.
 * @param argSelectedDataViews HashMap: The selected data views of the current Data View
 * @throws Exception if the IO operation failed
 * @return 
 * @see 
 */
private String fillSelectedDataViews(HashMap argSelectedDataViews,Vector argSelectedDataViewIDs) throws IOException
 {
    int nFieldIndex                                               = 0;
    Vector colDataViewFields                                      = new Vector();
    FieldModel modField;
    String strFieldName                                            = ""; 
    boolean bExit                                                  = false; 
    DetailedDataViewDTO dtoDetailedDataView                        = null;
    StringBuffer strSelectedDataViewScript                         = new StringBuffer();
    try
    {
       //argSelectedDataViews.keySet();
       
        //Iterator objIterator = objCollection.iterator();
        if(argSelectedDataViewIDs != null)
        {
            for (int m = 0; m < argSelectedDataViewIDs.size(); m++) 
            {
              bExit = false; 
            //while(objIterator.hasNext())
            //{
    //          dtoDetailedDataView = (DetailedDataViewDTO)objIterator.next();
                for(int h = 0; h < m; h++) 
                {
                    if(((Integer)argSelectedDataViewIDs.elementAt(h)).equals(argSelectedDataViewIDs.elementAt(m)))
                    {
                      bExit = true;
                      break;
                    }  
                }
                if(!bExit)
                {
                      dtoDetailedDataView = (DetailedDataViewDTO)argSelectedDataViews.get(argSelectedDataViewIDs.elementAt(m));
                      colDataViewFields = (Vector) dtoDetailedDataView.getDataViewFields();
                      strSelectedDataViewScript.append(" var objSelectedDataView=new DataView('"+dtoDetailedDataView.getDataViewID()+"','"+dtoDetailedDataView.getDataViewName()+"','"+dtoDetailedDataView.getDataViewVersion()+"','"+dtoDetailedDataView.getDataViewDescription()+"');");
                      strSelectedDataViewScript.append(" objSelectedDataView.m_nDataViewType = "+dtoDetailedDataView.getDataViewTypeID()+";");
                
                      for (int i = 0; i <colDataViewFields.size() ; i++) 
                      {
                        modField = (FieldModel)colDataViewFields.elementAt(i);
                        strFieldName = replaceByString(modField.getFieldName(),"~@^@~",",",strFieldName);
                        strSelectedDataViewScript.append(" var objDataViewField = new DataViewField('"+modField.getFieldID()+"','"+strFieldName+"','"+QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD+"','"+modField.getFieldDescription()+"','"+dtoDetailedDataView.getDataViewTypeID()+"');");
                        strSelectedDataViewScript.append(" objSelectedDataView.addField(objDataViewField);");

                        /*
                        Check the Group By and the Order By
                        */                        
                        strSelectedDataViewScript.append("   for (var i = 0; i <top.objNewDataView.m_arrDataViewFields.length ; i++) ");
                        strSelectedDataViewScript.append("  { ");
                        strSelectedDataViewScript.append("  var objSelectedField = top.objNewDataView.m_arrDataViewFields[i]; ");
                        strSelectedDataViewScript.append("  if(objSelectedField != null){ ");
                        strSelectedDataViewScript.append("  if(objSelectedField.m_strDataViewName == '"+dtoDetailedDataView.getDataViewName()+"' && objSelectedField.m_strFieldName == '"+strFieldName+"')");
                        strSelectedDataViewScript.append("      top.objNewDataView.m_arrDataViewFields[i].m_nFieldIndex = "+nFieldIndex+"; ");
                        strSelectedDataViewScript.append("   }} ");
                  
                        strSelectedDataViewScript.append("  for(var i = 0; i <top.objNewDataView.m_arrDataViewGroupBy.length ; i++) ");
                        strSelectedDataViewScript.append("  { ");
                        strSelectedDataViewScript.append("  var objGroupByElement = top.objNewDataView.m_arrDataViewGroupBy[i]; ");
                        strSelectedDataViewScript.append("  if(objGroupByElement != null){ ");
                        strSelectedDataViewScript.append("  if(objGroupByElement.m_nGroupByElementTypeID == '"+QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD+"')");
                        strSelectedDataViewScript.append("     if(objGroupByElement.m_objGroupByField.m_strDataViewName == '"+dtoDetailedDataView.getDataViewName()+"' && objGroupByElement.m_objGroupByField.m_strFieldName == '"+strFieldName+"')");
                        strSelectedDataViewScript.append("        top.objNewDataView.m_arrDataViewGroupBy[i].m_nFieldIndex = "+nFieldIndex+"; ");
                        strSelectedDataViewScript.append("  }} ");

                        strSelectedDataViewScript.append("  for(var i = 0; i <top.objNewDataView.m_arrDataViewOrderBy.length ; i++)");
                        strSelectedDataViewScript.append("  { ");
                        strSelectedDataViewScript.append("  var objOrderByElement = top.objNewDataView.m_arrDataViewOrderBy[i]; ");
                        strSelectedDataViewScript.append("  if(objOrderByElement != null){ ");
                        strSelectedDataViewScript.append("  if(objOrderByElement.m_nOrderByElementTypeID == '"+QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD+"')");
                        strSelectedDataViewScript.append("     if(objOrderByElement.m_objOrderByField.m_strDataViewName == '"+dtoDetailedDataView.getDataViewName()+"' && objOrderByElement.m_objOrderByField.m_strFieldName == '"+strFieldName+"')");
                        strSelectedDataViewScript.append("        top.objNewDataView.m_arrDataViewOrderBy[i].m_nFieldIndex = "+nFieldIndex+"; ");
                        strSelectedDataViewScript.append("  }} ");

      ////////////////////////////////////////////////////////////////////////////////////
      //  REMOVE THE FUNCTION PARAMETERS  
      ///////////////////////////////////////////////////////////////////////////////////
                        strSelectedDataViewScript.append(" for(var j=0;j<top.objNewDataView.m_arrDataViewFunctionParameters.length;j++) ");
                        strSelectedDataViewScript.append(" { ");
                        strSelectedDataViewScript.append(" var arrDataViewFunctionParameters = top.objNewDataView.m_arrDataViewFunctionParameters[j]; ");
                        strSelectedDataViewScript.append(" try{");
                        strSelectedDataViewScript.append(" if(arrDataViewFunctionParameters != null && arrDataViewFunctionParameters !=\"\"){}");
                        strSelectedDataViewScript.append(" } catch(e){");

      //                  strSelectedDataViewScript.append(" if(arrDataViewFunctionParameters != null && arrDataViewFunctionParameters != '')");
      //                  strSelectedDataViewScript.append(" { ");
                        strSelectedDataViewScript.append(" for(var k=0;k<arrDataViewFunctionParameters.length;k++) ");
                        strSelectedDataViewScript.append(" { ");
                        strSelectedDataViewScript.append(" var objFunctionParameter = arrDataViewFunctionParameters[k]; ");
                        strSelectedDataViewScript.append(" if(objFunctionParameter != null) ");
                        strSelectedDataViewScript.append(" { ");
                        strSelectedDataViewScript.append(" if( objFunctionParameter.m_nParameterType == '"+QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD+"')");
                        strSelectedDataViewScript.append(" { ");
                        strSelectedDataViewScript.append(" if(objFunctionParameter.m_objField.m_strDataViewName == '"+dtoDetailedDataView.getDataViewName()+"' && objFunctionParameter.m_objField.m_strFieldName == '"+strFieldName+"') ");
                        strSelectedDataViewScript.append("  arrDataViewFunctionParameters[k].m_objField.m_nFieldIndex = "+nFieldIndex+"; ");
                        strSelectedDataViewScript.append("  }  ");
                        strSelectedDataViewScript.append("  }  ");
                        strSelectedDataViewScript.append("  }  ");
                        strSelectedDataViewScript.append("  top.objNewDataView.m_arrDataViewFunctionParameters[j] = arrDataViewFunctionParameters;  ");
                        strSelectedDataViewScript.append("  }  ");
                        strSelectedDataViewScript.append("  }  ");
      ////////////////////////////////////////////////////////////////////////////////////
      //  REMOVE THE TERMS
      ///////////////////////////////////////////////////////////////////////////////////

          

                        strSelectedDataViewScript.append(" for(var j=0;j<top.objNewDataView.m_arrDataViewTerms.length;j++) ");
                        strSelectedDataViewScript.append(" { ");
                        strSelectedDataViewScript.append(" var objDataViewTerm = top.objNewDataView.m_arrDataViewTerms[j]; ");
                        strSelectedDataViewScript.append(" if(objDataViewTerm != null) ");
                        strSelectedDataViewScript.append(" { ");
                        strSelectedDataViewScript.append(" var objDataViewTerm1stOperand = objDataViewTerm.m_obj1stOperand; ");
                        strSelectedDataViewScript.append(" var objDataViewTerm2ndOperand = objDataViewTerm.m_obj2ndOperand; ");
                        strSelectedDataViewScript.append(" if( objDataViewTerm1stOperand.m_nFieldType == '"+QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD+"' || objDataViewTerm2ndOperand.m_nFieldType == '"+QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD+"')");
                        strSelectedDataViewScript.append(" { ");
                        strSelectedDataViewScript.append(" if(objDataViewTerm1stOperand.m_strDataViewName == '"+dtoDetailedDataView.getDataViewName()+"' && objDataViewTerm.m_obj1stOperand.m_nFieldType == '"+QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD+"' && objDataViewTerm.m_obj1stOperand.m_strFieldName == '"+strFieldName+"')  ");
                        strSelectedDataViewScript.append(" { ");
                        strSelectedDataViewScript.append("  top.objNewDataView.m_arrDataViewTerms[j].m_obj1stOperand.m_nFieldIndex = "+nFieldIndex+"; ");
                        strSelectedDataViewScript.append(" } ");
                        strSelectedDataViewScript.append(" if(objDataViewTerm2ndOperand.m_strDataViewName == '"+dtoDetailedDataView.getDataViewName()+"' && objDataViewTerm.m_obj2ndOperand.m_nFieldType == '"+QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD+"' && objDataViewTerm.m_obj2ndOperand.m_strFieldName == '"+strFieldName+"')  ");
                        strSelectedDataViewScript.append(" { ");
                        strSelectedDataViewScript.append("  top.objNewDataView.m_arrDataViewTerms[j].m_obj2ndOperand.m_nFieldIndex = "+nFieldIndex+"; ");
                        strSelectedDataViewScript.append(" } ");
                        strSelectedDataViewScript.append(" } ");
                        strSelectedDataViewScript.append(" } ");
                        strSelectedDataViewScript.append(" } ");
                  
                        nFieldIndex++; 
                        strFieldName = "";
                    }
                    strSelectedDataViewScript.append(" top.objNewDataView.addSelectedDataView(objSelectedDataView);");
              }
           }
        }    
    }
    catch(Exception objException)
    {
        
    }
    return strSelectedDataViewScript.toString();
} 
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
      <TITLE>Data View</TITLE>

   </HEAD>

<div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>

<%
    if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION)!= null)
    {
      if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(QueryBuilderInterfaceKey.ACTION_INITIALIZE_QUERY_BUILDER))
      {
         fillDataViewWizard(request,out);
      }
    }  
%>
   <BODY leftmargin=3 topmargin=0 valign=top>
   <form id="formDataView" name="formDataView" action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" method="post" target="Down">
   <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" id="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=QueryBuilderInterfaceKey.ACTION_INITIALIZE_QUERY_BUILDER%>" >    

<!--   <CENTER>
      <H2>Data View Definition</H2>
    </CENTER>-->
        <table  valign=top cellspacing="0" cellpadding="0" width="100%" border="0">
        <tr class=TableHeader>
            <td class=TableHeader colspan=8>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
           </tr>
        <tr class=TableHeader>
            <td class=TableHeader colspan=8>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
        </tr>
           <tr class=TableHeader>
            <td class=TableHeader colspan=8><font color=darkblue size=3>Data View Definition</td>
            <td class=TableHeader></td>
            <td class=TableHeader>&nbsp;</td>
           </tr>
          <tr class=TableHeader>
            <td class=TableHeader colspan=8>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
           </tr>
         
          <tr class=TableHeader>
            <td style="display:none" id=tdCell1 name=tdCell1 class=TableHeader>Data View Name</td>
            <td style="display:none" id=tdCell2 name=tdCell2 class=TableHeader>
              <input name="<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME%>" onkeypress="checkQuotes()" id="<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME%>" style="WIDTH: 350px; HEIGHT: 22px;" maxlength=30 size=50>
              <label> Version: </label>
              <input name="textVersion" id="textVersion" style="WIDTH: 50px; HEIGHT: 22px;" readonly>
              </td>
              <td colspan=12 class=TableHeader>
              <INPUT id="<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>" type=checkbox name="<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>">&nbsp;Remove Duplicate Rows
              </td>
          </tr>
          <tr style="display:none" id=trRow2 name=trRow2 class=TableHeader>
            <td class=TableHeader valign =top >Data View Description</td>
            <td class=TableHeader colspan=8><TEXTAREA onkeypress="checkQuotes()" style="WIDTH: 409px; HEIGHT: 86px" name="<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>" id="<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>" onkeypress="limitSize()" onbeforepaste="limitClipboard()" rows=5 cols=43></TEXTAREA></td>
            <td class=TableHeader>&nbsp;</td>
          </tr>
            <tr class=TableHeader>
            <td class=TableHeader colspan=8>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
          </tr>
        </table>
        
        <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
          <TBODY>
          <TR>
            <TD class=TableHeader vAlign=top colSpan=9>Use&nbsp;Pre-Defined Scopes
              <A onclick="data_view_RowSet_add('2','half_day','pre_defined_data_view_count','pre_defined_data_view','PreDefinedDataViewArray');">
              <IMG alt="Click Here to add New Row for Data view ... " 
              src="../resources/img/img_sign_dn.gif" border=0></A>
            </TD>
          </TR>
  <TR>
    <TD class=TableTextColumn vAlign=top align=middle colSpan=9>
      <P align=left>
      <TABLE style="BORDER-COLLAPSE: collapse" borderColor=#000080 cellSpacing=0 
      cellPadding=0 align=left border=1>
        
        <TR>
          <TD class=TableTextColumn><INPUT type=hidden size=15 value=0 
            name=pre_defined_data_view_count>
         
      <SCRIPT language="javascript">
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

    function data_view_RowSet_add(argCurrentValue,argCurrentName,argCounterName,argControlName,argArrayDataView)
    {
        eval("document.formDataView."+argCounterName+".value = Number("+ argControlName + ".RowSet.getRowCount());");
        ix = eval("document.formDataView."+argCounterName+".value = Number("+argControlName+".RowSet.getRowCount());");
        eval(argControlName+".RowSet.add()");
        argCurrentName = argCurrentName+","+argCurrentName;
        for (var i = 0; i < eval(argArrayDataView+".length"); i++)
        {
           if (eval(argArrayDataView+"["+i+"]") == argCurrentValue)
           {
              eval(argControlName+".RowSet.getCell("+Number(ix+1)+",1).cellElement.selectedIndex ="+i+";");                               
           }
        } 
        eval(argControlName+".RowSet.getCell("+Number(ix+1)+",2).cellElement.readOnly=true");
        eval(argControlName+".RowSet.getCell("+Number(ix+1)+",3).cellElement.readOnly=true");
        
/*      for (var i=0 ; i < eval(argArrayDataViewVersion+".length");i++)
        {
           if (eval(argArrayDataViewVersion+"["+i+"]") == argCurrentName)
           {
               eval(argControlName+".RowSet.getCell("+ix+1+",2).cellElement.selectedIndex = "+i+";");
           }
        }*/
      }

      function app_need_removeRows(argObject)
      {
        i = confirm("This will remove this data")
        if (i==true){
          for(var i=eval(argObject+".getRowCount()");i>=1;i--){
            if(eval(argObject+".getCell("+i+",4).getValue()")==true){
              eval(argObject+".RowSet.deleteRow("+i+");");
            }//end if
          }//end for
        }//end if
        else
        {
          for(var i=eval(argObject+".getRowCount()");i>=1;i--){
            if(eval(argObject+".getCell("+i+",4).getValue()")==true){
              eval(argObject+".getCell("+i+",4).setValue == false; ") 
            }//end if
          }//end for
        }//end else
      }


      var PreDefinedDataViewArray =new Array();
      var PreDefinedDataViewVersions =new Array();
//      var PreDefinedDataViewVersionArray=new Array(new Array('',''));
      var PreDefinedDataViewDescriptions =new Array();         
            
      var objPreDefinedDataViewIssuesArray = top.objPreDefinedDataViewIssues.m_arrDataViewIssue;
            
      for(var i=0;i<objPreDefinedDataViewIssuesArray.length;i++)
      {
        var objIssue=objPreDefinedDataViewIssuesArray[i];
        PreDefinedDataViewArray[i] = objIssue[0].m_arrDataViewIssuePairs;
        PreDefinedDataViewVersions[i] = objIssue[0].m_arrVersions[0].m_nVersionID;
        PreDefinedDataViewDescriptions[i] = objIssue[0].m_arrVersions[0].m_strVersionDescription;
      }
            
      var UserDefinedDataViewArray =new Array();
      var UserDefinedDataViewVersions =new Array();
//      var UserDefinedDataViewVersionArray=new Array(new Array('',''));
      var UserDefinedDataViewDescriptions = new Array();
      
      var objUserDefinedDataViewIssuesArray = top.objUserDefinedDataViewIssues.m_arrDataViewIssue;
            
      for(var i=0;i<objUserDefinedDataViewIssuesArray.length;i++)
      {
        var objIssue=objUserDefinedDataViewIssuesArray[i];
        UserDefinedDataViewArray[i] = objIssue[0].m_arrDataViewIssuePairs;
        UserDefinedDataViewVersions[i] = objIssue[0].m_arrVersions[0].m_nVersionID;
        UserDefinedDataViewDescriptions[i] = objIssue[0].m_arrVersions[0].m_strVersionDescription;
      }
//          var PreDefinedDataViewArray = objPredefinedDataViews.m_arrDataViewIssue[0][0].;
//          var a=top.objPreDefinedDataViewIssues.m_arrDataViewIssue[1];
//          var b=top.objPreDefinedDataViewIssues.m_arrDataViewIssue[2];




      function popUp(argObj,argVersionArrayName,argDescriptionArrayName)
      {
          var nRowIndex;
          var nSelectedIndex = 0;
          var strPopUpColumnIDVersion = new String();
          var strPopUpColumnIDDescription = new String();
          var strID= new String(argObj.id);
          nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
          strPopUpColumnIDVersion = strID.substring(0,strID.indexOf("__C")+3);
          strPopUpColumnIDVersion +=2
          strPopUpColumnIDDescription = strID.substring(0,strID.indexOf("__C")+3);
          strPopUpColumnIDDescription +=3;
          
// Delete all versions   
//          var nCount = Number(eval("document.formDataView."+strPopUpColumnID+".length"));
//          for(var i=0;i<nCount;i++)
//              eval("document.formDataView."+strPopUpColumnID+".remove(0);");

          eval("nSelectedIndex = document.formDataView."+strID+".selectedIndex");
          if(nSelectedIndex > 0)
          {
              var strDescrioption;
              var arrPairs=new Array();
              var strPairs=new String(eval(argVersionArrayName+"["+nSelectedIndex+"];"));
              strDescrioption = eval(argDescriptionArrayName+"["+nSelectedIndex+"];");
              arrPairs = strPairs.split(",");
          
    //          for(var j =0;j<arrPairs.length;j=j+2)    
    //          {
    //               var objOption = document.createElement("OPTION");

      //             objOption.text = arrPairs[j];

     //              objOption.value = arrPairs[j+1];


       //            eval("document.formDataView."+strPopUpColumnID+".add(objOption);");
       eval("document.formDataView."+strPopUpColumnIDVersion+".value ="+arrPairs[0]+";");
       if(strDescrioption == "" || strDescrioption == null || strDescrioption == "null")
           strDescrioption = "N/A";
       eval("document.formDataView."+strPopUpColumnIDDescription+".value ='"+strDescrioption+"';");
         //     }
        }
        else
        {
          eval("document.formDataView."+strPopUpColumnIDVersion+".value ='';");
          eval("document.formDataView."+strPopUpColumnIDDescription+".value ='';");
        }
      }




      var pre_defined_data_view=new DeepGrid("pre_defined_data_view",0,"");
      pre_defined_data_view.ColumnHeaders.add(null,null,"Pre-Defined Scope Name",null,250,"center",DG_SELECT,PreDefinedDataViewArray,null,"onChange = popUp(this,'PreDefinedDataViewVersions','PreDefinedDataViewDescriptions')");
//      pre_defined_data_view.ColumnHeaders.add(null,null,"Version",null,350,"center",DG_TEXT,PreDefinedDataViewVersionArray)
      pre_defined_data_view.ColumnHeaders.add(null,null,"Version",null,150,"center",DG_TEXT,null)
      pre_defined_data_view.ColumnHeaders.add(null,null,"Description",null,365,"center",DG_TEXTAREA,null)
      pre_defined_data_view.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = app_need_removeRows('pre_defined_data_view')")
      </SCRIPT>
       <SCRIPT>

       
//      data_view_RowSet_add('','','pre_defined_data_view_count','pre_defined_data_view','PreDefinedDataViewArray');
      
      </SCRIPT>
             </TD></TR></TABLE></P></TD></TR>
          <TR>
            <TD class=TableTextColumn vAlign=top align=middle colSpan=9>
            </TD>
          </TR></TBODY>
        </TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  
  <TR>
    <TD class=TableHeader vAlign=top colSpan=9>Use&nbsp;User-Defined&nbsp;Data 
      View <A onclick="data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');"><IMG 
      alt="Click Here to add New Row for Data view ... " 
      src="../resources/img/img_sign_dn.gif" border=0></A> </TD></TR>
  <TR>
    <TD class=TableTextColumn vAlign=top align=middle colSpan=9>
      <TABLE style="BORDER-COLLAPSE: collapse" borderColor=#000080 cellSpacing=0 
      cellPadding=0 align=left border=1>
        
        <TR>
          <TD class=TableTextColumn><INPUT type=hidden size=15 value=0 
            name=user_defined_data_view_count>
            <SCRIPT>
                  var user_defined_data_view=new DeepGrid("user_defined_data_view",0,"")
                  user_defined_data_view.ColumnHeaders.add(null,null,"User-Defined Data View Name",null,250,"center",DG_SELECT,UserDefinedDataViewArray,null,"onChange = popUp(this,'UserDefinedDataViewVersions','UserDefinedDataViewDescriptions')");
//                  user_defined_data_view.ColumnHeaders.add(null,null,"Version",null,350,"center",DG_SELECT,UserDefinedDataViewVersionArray)
                  user_defined_data_view.ColumnHeaders.add(null,null,"Version",null,150,"center",DG_TEXT,null)
                  user_defined_data_view.ColumnHeaders.add(null,null,"Description",null,365,"center",DG_TEXTAREA,null)
                  user_defined_data_view.ColumnHeaders.add(null,null,"Remove",null,60,null,DG_BOOLEAN,null,null,"onClick = app_need_removeRows('user_defined_data_view')")
             </SCRIPT>

            <SCRIPT>
  //                    data_view_RowSet_add('','','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');

     try
     {
       if(top.objNewDataView.m_nSaveMode == 1)
       {
//          document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME%>.style.display = 'block'; 
          tdCell1.style.display = 'block'; 
          tdCell2.style.display = 'block'; 
          trRow2.style.display = 'block'; 
          if(top.objNewDataView.m_strDataViewName != "null" || top.objNewDataView.m_strDataViewName != "")
            eval("document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME%>.value = \""+top.objNewDataView.m_strDataViewName+"\";");
          if(top.objNewDataView.m_strDataViewDescription != "" || top.objNewDataView.m_strDataViewDescription != "null")    
             eval("document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.value = \""+top.objNewDataView.m_strDataViewDescription+"\";");
          document.formDataView.textVersion.value =  top.objNewDataView.m_nDataViewVersion;
       }
       eval("document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>.checked = "+top.objNewDataView.m_bDataViewUnique+";");
       var objSelectedDataViewsArray = top.objNewDataView.m_arrSelectedDataViews;
       var nPreDefinedDataViewCount = 1;
       var nUserDefinedDataViewCount = 1;

       if(objSelectedDataViewsArray.length > 0)
       {
           for(var i=0;i<objSelectedDataViewsArray.length;i++)
           {
              var objDataView=objSelectedDataViewsArray[i];
              if(objDataView.m_nDataViewType == <%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE%>)
              {
                  eval("pre_defined_data_view.RowSet.add("+Number(nPreDefinedDataViewCount)+");");
                  eval("document.formDataView.pre_defined_data_view__R"+Number(nPreDefinedDataViewCount)+"__C1.value = '"+ objDataView.m_nDataViewID+"';");
                  eval("document.formDataView.pre_defined_data_view__R"+Number(nPreDefinedDataViewCount)+"__C2.value = '"+ objDataView.m_nDataViewVersion+"';");
                  eval("document.formDataView.pre_defined_data_view__R"+Number(nPreDefinedDataViewCount)+"__C3.value = '"+ objDataView.m_strDataViewDescription+"';");
                  eval("document.formDataView.pre_defined_data_view__R"+Number(nPreDefinedDataViewCount)+"__C2.readOnly=true");
                  eval("document.formDataView.pre_defined_data_view__R"+Number(nPreDefinedDataViewCount)+"__C3.readOnly=true");
                  nPreDefinedDataViewCount++;
              }
              else
              {
                  eval("user_defined_data_view.RowSet.add("+Number(nUserDefinedDataViewCount)+");");
                  eval("document.formDataView.user_defined_data_view__R"+Number(nUserDefinedDataViewCount)+"__C1.value = '"+ objDataView.m_nDataViewID+"';");
                  eval("document.formDataView.user_defined_data_view__R"+Number(nUserDefinedDataViewCount)+"__C2.value = '"+ objDataView.m_nDataViewVersion+"';");
                  eval("document.formDataView.user_defined_data_view__R"+Number(nUserDefinedDataViewCount)+"__C3.value = '"+ objDataView.m_strDataViewDescription+"';");
                  eval("document.formDataView.user_defined_data_view__R"+Number(nUserDefinedDataViewCount)+"__C2.readOnly=true");
                  eval("document.formDataView.user_defined_data_view__R"+Number(nUserDefinedDataViewCount)+"__C3.readOnly=true");
                  nUserDefinedDataViewCount++;
              }
           }
           if(pre_defined_data_view.getRowCount() == 0)
           {
              data_view_RowSet_add('','','pre_defined_data_view_count','pre_defined_data_view','PreDefinedDataViewArray');
              eval("document.formDataView.pre_defined_data_view__R1__C2.readOnly=true");
              eval("document.formDataView.pre_defined_data_view__R1__C3.readOnly=true");
           }   
           if(user_defined_data_view.getRowCount() == 0)
           {
              data_view_RowSet_add('','','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');
              eval("document.formDataView.user_defined_data_view__R1__C2.readOnly=true");
              eval("document.formDataView.user_defined_data_view__R1__C3.readOnly=true");
           }   
       }
       else
       {
            data_view_RowSet_add('','','pre_defined_data_view_count','pre_defined_data_view','PreDefinedDataViewArray');
            eval("document.formDataView.pre_defined_data_view__R1__C2.readOnly=true");
            eval("document.formDataView.pre_defined_data_view__R1__C3.readOnly=true");
            data_view_RowSet_add('','','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');
            eval("document.formDataView.user_defined_data_view__R1__C2.readOnly=true");
            eval("document.formDataView.user_defined_data_view__R1__C3.readOnly=true");
       }
   }
   catch(e)
   {
        data_view_RowSet_add('','','pre_defined_data_view_count','pre_defined_data_view','PreDefinedDataViewArray');
        eval("document.formDataView.pre_defined_data_view__R1__C2.readOnly=true");
        eval("document.formDataView.pre_defined_data_view__R1__C3.readOnly=true");
        data_view_RowSet_add('','','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');
        eval("document.formDataView.user_defined_data_view__R1__C2.readOnly=true");
        eval("document.formDataView.user_defined_data_view__R1__C3.readOnly=true");
   }

                    </SCRIPT>
             </TD></TR></TABLE></TD></TR></TABLE>
        <table  cellspacing="0" cellpadding="0" border="0" width="100%">
          <tr>
            <td class=TableHeader align=center>
              <input type="button" id="butApply" name="butApply" value="Apply" class="button" onclick="return butApply_onclick()">
              <input type="button" id="butHelp" name="butHelp" value="Help" class="button" onclick="return butHelp_onclick()">
           </td>
           <td class=TableHeader>&nbsp;</td>
          </tr>
                  <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
           </tr>
        <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
           </tr>
        <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
           </tr>

        </table>
      </FORM>
      
<SCRIPT LANGUAGE=javascript>
////////////////////////////////////////////////////////////////////////////
//                            Handling Events
////////////////////////////////////////////////////////////////////////////
function butApply_onclick()
{
  addSelecteddataViews();
}

function addSelecteddataViews() 
{
//    top.objNewDataView.m_arrSelectedDataViews.length = 0;
    var  nCountSelectedDataViews = 0;
    var bValid = true;

    top.objNewDataView.m_strDataViewName = eval("document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_NAME%>.value");
    if(top.objNewDataView.m_strDataViewName != "")
    {
        if(TrimInput(top.objNewDataView.m_strDataViewName) == "")
        {
            alert("The data view name can't be space characters only..");
            return false;
        }
    }

    top.objNewDataView.m_strDataViewDescription = eval("document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.value");
    if(eval("document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_UNIQUE%>.checked") )
      top.objNewDataView.m_bDataViewUnique = true;
    else
      top.objNewDataView.m_bDataViewUnique = false;
  
   
    for(var i=1;i<= pre_defined_data_view.getRowCount();i++)
    {
      if(Number(pre_defined_data_view.getCell(i,1).getValue()) > 0)
      {
        var objFormEelement = document.formDataView.all.item("<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS%>"+nCountSelectedDataViews);
        if( objFormEelement != null)
        {
          eval("document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS%>+nCountSelectedDataViews.value  ='"+pre_defined_data_view.getCell(i,1).getValue()+"';");
        }   
        else      
        {
          var objHidden= document.createElement("INPUT");
          objHidden.type ="hidden";
          objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS%>"+nCountSelectedDataViews;
          objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS%>"+nCountSelectedDataViews;
          objHidden.value = pre_defined_data_view.getCell(i,1).getValue();
          document.formDataView.appendChild(objHidden);
          nCountSelectedDataViews++;
        }
      } //end if
    }

    for(var i=1;i<=user_defined_data_view.getRowCount();i++)
    {
       if(Number(user_defined_data_view.getCell(i,1).getValue()) > 0)
       {
          var objFormEelement = document.formDataView.all.item("<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS%>");
          if( objFormEelement != null)
          {
            eval("document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS%>+nCountSelectedDataViews.value  ='"+user_defined_data_view.getCell(i,1).getValue()+"';");
          }   
          else      
          {
             var objHidden= document.createElement("INPUT");
             objHidden.type ="hidden";
             objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS%>"+nCountSelectedDataViews;
             objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS%>"+nCountSelectedDataViews;
             objHidden.value = user_defined_data_view.getCell(i,1).getValue();
             document.formDataView.appendChild(objHidden);
             nCountSelectedDataViews++;
           }
        }//end if
      }
      var objFormEelement = document.formDataView.all.item("<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS_NUM%>");
      if( objFormEelement != null)
      {
        eval("document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS_NUM%>.value  ='"+Number(nCountSelectedDataViews)+"';");
      }   
      else      
      {
         var objHidden= document.createElement("INPUT");
         objHidden.type ="hidden";
         objHidden.id= "<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS_NUM%>";
         objHidden.name= "<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_SELECTED_DATAVIEWS_NUM%>";
         objHidden.value = Number(nCountSelectedDataViews);
         document.formDataView.appendChild(objHidden);  
      }

      var bRemove;
      
      for(var t=0;t<top.objNewDataView.m_arrSelectedDataViews.length;t++)
      {
         bRemove  = true;
         var objPreviouslySelectedDataView = top.objNewDataView.m_arrSelectedDataViews[t];
         for(var i=1;i<=pre_defined_data_view.getRowCount();i++)
         {
            if(Number(pre_defined_data_view.getCell(i,1).getValue()) > 0)
            {
                if(objPreviouslySelectedDataView.m_nDataViewID == Number(pre_defined_data_view.getCell(i,1).getValue()))
                {
                    bRemove = false;
                    break;
                }    
            }
         }
         if(bRemove)
         for(var i=1;i<=user_defined_data_view.getRowCount();i++)
         {
            if(Number(user_defined_data_view.getCell(i,1).getValue()) > 0)
            {
                if(objPreviouslySelectedDataView.m_nDataViewID == Number(user_defined_data_view.getCell(i,1).getValue()))
                {
                    bRemove = false;
                    break;
                }    
            }
         }
      
         if(bRemove)
         {
              removeDataView(objPreviouslySelectedDataView.m_strDataViewName);
         }     
      }

      

      if(Number(nCountSelectedDataViews) > 0)
      {
        top.objNewDataView.m_arrSelectedDataViews.length = 0;
        document.formDataView.submit();
      }  
      else
         alert("Please select at least one data view..");
}

function limitSize()
{
  var strDesc = eval("document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.value");
  var nKeyCode = event.keyCode;
  if(strDesc.length >= 250)
  {
    event.keyCode = 0;
    return false;
  }
}
function limitClipboard()
{
  var strPastedText = new String();
  if(window.clipboardData.getData("Text") != null)
  {
    strPastedText = window.clipboardData.getData("Text");
  }  
  var strDesc = eval("document.formDataView.<%=QueryBuilderInterfaceKey.PARAM_INITIALIZE_QUERY_BUILDER_QUERY_DESC%>.value");
  var nCount= 0;
  if(Number(strDesc.length) < 250)
  {
    while(Number(strDesc.length) < 250)
    {
      strDesc += strPastedText[nCount];
      nCount++;
    }
  }
  else
    window.clipboardData.clearData("Text");
}

function TrimInput(strText)
{
    //this will get rid of leading spaces
    while (strText.substring(0,1) == ' ')
    {
        strText = strText.substring(1, strText.length);
    }
 
    //this will get rid of trailing spaces
    while (strText.substring(strText.length-1,strText.length) == ' ')
        strText = strText.substring(0, strText.length-1);
    return strText;
}

function removeDataView(argDataViewName)
{
  for (var i = 0; i <top.objNewDataView.m_arrDataViewFields.length ; i++) 
  {
      var objSelectedField = top.objNewDataView.m_arrDataViewFields[i];
      if(objSelectedField.m_strDataViewName == argDataViewName)        
        top.objNewDataView.m_arrDataViewFields[i] = null;
  }
  for(var i = 0; i <top.objNewDataView.m_arrDataViewGroupBy.length ; i++) 
  {
      var objGroupByElement = top.objNewDataView.m_arrDataViewGroupBy[i];
      if(objGroupByElement.m_nGroupByElementTypeID == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>")
          if(objGroupByElement.m_objGroupByField.m_strDataViewName == argDataViewName)        
              top.objNewDataView.m_arrDataViewGroupBy[i] = null;
  }
  for(var i = 0; i <top.objNewDataView.m_arrDataViewOrderBy.length ; i++) 
  {
      var objOrderByElement = top.objNewDataView.m_arrDataViewOrderBy[i];
      if(objOrderByElement.m_nOrderByElementTypeID == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>")
          if(objOrderByElement.m_objOrderByField.m_strDataViewName == argDataViewName)        
              top.objNewDataView.m_arrDataViewOrderBy[i] = null;
  }
////////////////////////////////////////////////////////////////////////////////////
//  REMOVE THE FUNCTION PARAMETERS  
///////////////////////////////////////////////////////////////////////////////////
      /*var arrValues = new Array();
      if(top.objNewDataView.m_arrDataViewFunctionParameters != null)
      {
        var strParameters = new String(top.objNewDataView.m_arrDataViewFunctionParameters);
        arrValues = strParameters.split(",")
      }  
      if(arrValues.length > 0)
      {*/
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
                       if( objFunctionParameter.m_nParameterType == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>")
                       {
                           if(objFunctionParameter.m_objField.m_strDataViewName == argDataViewName)        
                              arrDataViewFunctionParameters[k] = null;
                       }     
                    }
                  }
                  top.objNewDataView.m_arrDataViewFunctionParameters[j] = arrDataViewFunctionParameters;
               }
            }

////////////////////////////////////////////////////////////////////////////////////
//  REMOVE THE TERMS
///////////////////////////////////////////////////////////////////////////////////
          for(var j=0;j<top.objNewDataView.m_arrDataViewTerms.length;j++)
          {
             var objDataViewTerm = top.objNewDataView.m_arrDataViewTerms[j];
             if(objDataViewTerm != null)
             {
                 var objDataViewTerm1stOperand = objDataViewTerm.m_obj1stOperand;
                 var objDataViewTerm2ndOperand = objDataViewTerm.m_obj2ndOperand;
                 if( objDataViewTerm1stOperand.m_nFieldType == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>" || objDataViewTerm2ndOperand.m_nFieldType == "<%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>")
                 {
                     if((objDataViewTerm1stOperand.m_strDataViewName == argDataViewName && objDataViewTerm.m_obj1stOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>) || (objDataViewTerm2ndOperand.m_strDataViewName == argDataViewName && objDataViewTerm.m_obj2ndOperand.m_nFieldType == <%=QueryBuilderInterfaceKey.FIELD_TYPE_DATAVIEW_FIELD%>))
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

}
function butHelp_onclick()
{
   var nLeft = ((window.screen.availWidth)- 500)/2  ;
   var nTop = ((window.screen.availHeight) - 500)/2;
   window.open('../gn/GN_ADHOC_DataViewWizardStepHelp.html','',"height= 500px, width= 500px,top="+nTop+",left="+nLeft);
}
</SCRIPT>   

</BODY>

</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>

</HTML>


<%@ page  import = "java.util.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.fn.*"
          import = "java.io.*"
          import = "com.mobinil.sds.core.system.fn.addfunction.model.*"
          import = "com.mobinil.sds.core.system.fn.importdata.model.*"
          import = "javax.servlet.*" 
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"
          import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
%>

<% 
String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    if(NonBlank(document.formDataView.<%out.print(FunctionsInterfaceKey.CONTROL_TEXTAREA_FUNCTION_NAME);%>, true, 'Function Name'))
    {
      if(NonBlank(document.formDataView.<%out.print(FunctionsInterfaceKey.CONTROL_SELECT_FUNCTION_TYPE);%>, true, 'Function Type'))
      {
          if(NonBlank(document.formDataView.<%out.print(FunctionsInterfaceKey.CONTROL_SELECT_FUNCTION_STATUS);%>, true, 'Function Status'))
          {
              if(NonBlank(document.formDataView.<%out.print(FunctionsInterfaceKey.CONTROL_TEXT_FUNCTION_SQL_CALL);%>, true, 'Function SQL Call'))
              {
                  formDataView.submit();
              }
          }
       }
    }
    return false;  
  }
</SCRIPT>
<html>

  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/validation.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/lst_add_remove.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/combobox.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/DataViews.js" type=text/javascript></SCRIPT>
      
  </head>
<div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>
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
  //Number("+ argControlName + ".RowSet.getRowCount())
      ix = eval("document.formDataView."+argCounterName+".value = Number(document.formDataView."+argCounterName+".value) + 1;");
      ix = ix-1;
      eval(argControlName+".RowSet.add()");
      argCurrentName = argCurrentName+","+argCurrentName;
      for (var i = 0; i < eval(argArrayDataView+".length"); i++)
      {
         if (eval(argArrayDataView+"["+i+"]") == argCurrentValue)
         {
            eval(argControlName+".RowSet.getCell("+Number(ix+1)+",1).cellElement.selectedIndex ="+i+";");                               
         }
      } 
    }

    function app_need_removeRows(argObject)
    {
      i = confirm("This will remove this data")
      if (i==true){
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",3).getValue()")==true){
            eval(argObject+".RowSet.deleteRow("+i+");");
          }//end if
        }//end for
      }//end if
      else
      {
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",3).getValue()")==true){
            eval(argObject+".getCell("+i+",3).setValue == false; ") 
          }//end if
        }//end for
      }//end else
    }

    var UserDefinedDataViewArray =new Array();    
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

        eval("nSelectedIndex = document.formDataView."+strID+".selectedIndex");
        if(nSelectedIndex > 0)
        {
            var strDescrioption;
            var arrPairs=new Array();
            var strPairs=new String(eval(argVersionArrayName+"["+nSelectedIndex+"];"));
            strDescrioption = eval(argDescriptionArrayName+"["+nSelectedIndex+"];");
            arrPairs = strPairs.split(",");

           eval("document.formDataView."+strPopUpColumnID+".add(objOption);");
     eval("document.formDataView."+strPopUpColumnIDVersion+".value ="+arrPairs[0]+";");
     if(strDescrioption == "" || strDescrioption == null || strDescrioption == "null")
         strDescrioption = "N/A";
     eval("document.formDataView."+strPopUpColumnIDDescription+".value ='"+strDescrioption+"';");

      }
      else
      {
        eval("document.formDataView."+strPopUpColumnIDVersion+".value ='';");
        eval("document.formDataView."+strPopUpColumnIDDescription+".value ='';");
      }
    }
  </SCRIPT>
<body>
    <%
    showFunctionDetails(request, response, out);
    %>        
    <center><input class='button' type='button' value='Save' onclick=checkbeforSubmit();></center>
      </form>
<%!
  /**
   * showFunctionDetails method: 
   * Display the form in which a sheet type will be edited or added.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showFunctionDetails(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { 
    String xxFunctionId = "";
    String xxFunctionName = "";
    String xxFunctionTypeId = "";
    String xxFunctionTypeName = "";
    String xxFunctionStatusId = "";
    String xxFunctionStatusName = "";
    String xxFunctionSQLCall = "";
    String xxFunctionHelpText = "";
    String xxFunctionDesc = "";

    String yyParameterId = "";
    String yyFunctionId = "";
    String yyParameterName = "";
    String yyParameterDesc = "";
    String yyParameterDefOptional = "";
    String yyParameterDefIsList = "";    
    
    String appName = request.getContextPath();
    
    out.println("<CENTER>");
    out.println("  <H2>Function</H2>");
    out.println("</CENTER>");
  
    
    HashMap dataHashMap = new HashMap(100);

    String formActionSave = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                        +InterfaceKey.HASHMAP_KEY_ACTION+"="
                        +FunctionsInterfaceKey.ACTION_SAVE_FUNCTION;
    
    out.println("<form name='formDataView' action='"+formActionSave+"' method='post'>");


    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    if(dataHashMap.get(FunctionsInterfaceKey.HASHMAP_KEY_FUNCTION_COLLECTION) != null)
    {
      Vector CurrentfunVec = (Vector) dataHashMap.get(FunctionsInterfaceKey.HASHMAP_KEY_FUNCTION_COLLECTION);
            for(int k= 0; k<CurrentfunVec.size(); k++)
            {
              DataImportTableFunctionsModel functionModel = (DataImportTableFunctionsModel) CurrentfunVec.get(k);            
              xxFunctionId = functionModel.getFunctionId();
              xxFunctionName = functionModel.getFunctionName();
              xxFunctionStatusId = functionModel.getFunctionStatusId();
              xxFunctionStatusName = functionModel.getFunctionStatusName();
              xxFunctionSQLCall = functionModel.getFunctionSQLCall();
              xxFunctionTypeId = functionModel.getFunctionTypeId();
              xxFunctionTypeName = functionModel.getFunctionTypeName();
              xxFunctionHelpText = functionModel.getFunctionHelpText();
              xxFunctionDesc = functionModel.getFunctionDescription();
            }
    }
    
    Vector funTypeVec = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    Vector funStatusVec = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
          
    String functionaction = (String) dataHashMap.get(FunctionsInterfaceKey.FUNCTION_ACTION);
    
    out.println("<input type='hidden' name='functionaction' id='functionaction' value='"+functionaction+"'>");
    String fnID = FunctionsInterfaceKey.CONTROL_HIDDEN_FUNCTION_ID;
    String fnName = FunctionsInterfaceKey.CONTROL_TEXTAREA_FUNCTION_NAME;
    String fnType = FunctionsInterfaceKey.CONTROL_SELECT_FUNCTION_TYPE; 
    String fnStatus = FunctionsInterfaceKey.CONTROL_SELECT_FUNCTION_STATUS;
    String fnSQLCall = FunctionsInterfaceKey.CONTROL_TEXT_FUNCTION_SQL_CALL;
    String fnHelpText = FunctionsInterfaceKey.CONTROL_TEXTAREA_FUNCTION_HELP_TEXT;
    String fnDesc = FunctionsInterfaceKey.CONTROL_TEXTAREA_FUNCTION_DESCRIPTION;

    out.println("<table border=0 align='center' width='100%'>");
    out.println("    <tr class=TableTextNote>");
    out.println("        <td colspan=2><input type='hidden' name='"+fnID+"' id='"+fnID+"' value='"+xxFunctionId+"'></td>");
    out.println("    </tr>");
    out.println("    <tr class=TableTextNote>");
    out.println("        <td width='30%'>Function Name</td>");
    out.println("        <td width='70%'><textarea name='"+fnName+"' id='"+fnName+"' cols=70 rows=3>"+xxFunctionName+"</textarea></td>");
    out.println("    </tr>");
    out.println("    <tr class=TableTextNote>");
    out.println("        <td>Function Type</td>");
    out.println("        <td>");
    out.println("        <select name='"+fnType+"' id='"+fnType+"'>");
    out.println("              <option value=''></option>");
            functiontypeModel functionTypeModel;            
            String functionTypeIdX   ;
            String functionTypeNameX ;
            String functionTypeDescX ;
            String selected = "";
            
                    for(int k= 0; k<funTypeVec.size(); k++)
                    {
                      functionTypeModel = (functiontypeModel) funTypeVec.get(k);            
                      functionTypeIdX   = functionTypeModel.getFunctionTypeId();
                      functionTypeNameX = functionTypeModel.getFunctionTypeName();
                      functionTypeDescX = functionTypeModel.getFunctionTypeDesc();
                      selected = "";
                      if(xxFunctionTypeId.equals(functionTypeIdX)) 
                        {
                            selected = "selected";
                        }
                      
                      out.println("<option value='"+functionTypeIdX+"' "+selected+">"+functionTypeNameX+"</option>");
                      functionTypeIdX =""  ;
                      functionTypeNameX ="";
                      functionTypeDescX ="";
                      selected = "";
                    }
                
            out.println("</select>");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr class=TableTextNote>");
            out.println("<td>Function Status</td>");
            out.println("<td>");
            out.println("<select name='"+fnStatus+"' id='"+fnStatus+"'>");
            out.println("      <option value=''></option>");
                 functionstatusModel functionStatusModel;
                 String functionStatusIdX;
                 String functionStatusNameX;
                 String functionStatusDescX;
                 String selectedX;
                    for(int l= 0; l<funStatusVec.size(); l++)
                    {
                      functionStatusModel = (functionstatusModel) funStatusVec.get(l);            
                      functionStatusIdX   = functionStatusModel.getFunctionStatusId();
                      functionStatusNameX = functionStatusModel.getFunctionStatusName();
                      functionStatusDescX = functionStatusModel.getFunctionStatusDesc();
                      selectedX = "";
                      if(xxFunctionStatusId.equals(functionStatusIdX)) 
                      {
                          selectedX = "selected";
                      }
                      out.println("<option value='"+functionStatusIdX+"' "+selectedX+">"+functionStatusNameX+"</option>");
                      functionStatusIdX =""  ;
                      functionStatusNameX ="";
                      functionStatusDescX ="";
                      selected = "";
                    }
    
            out.println("</select>");
            out.println("</td>");
        out.println("</tr>");
        out.println("<tr class=TableTextNote>");
        out.println("    <td>Function SQL Call</td>");
        out.println("    <td><input type='text' name='"+fnSQLCall+"' id='"+fnSQLCall+"' value='"+xxFunctionSQLCall+"'></td>");
        out.println("</tr>");
        out.println("<tr class=TableTextNote>");
        out.println("    <td>Function Help Text</td>");
        out.println("    <td><textarea name='"+fnHelpText+"' id='"+fnHelpText+"' cols=70 rows=5>"+xxFunctionHelpText+"</textarea></td>");
        out.println("</tr>");
        out.println("<tr class=TableTextNote>");
        out.println("    <td>Function Description</td>");
        out.println("    <td><textarea name='"+fnDesc+"' id='"+fnDesc+"' cols=70 rows=5>"+xxFunctionDesc+"</textarea></td>");
        out.println("</tr>");

  out.println("<TR class=TableTextNote>");
  out.println("  <TD vAlign=top colSpan=2>Function&nbsp;Parameters");
  out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');\"><IMG"); 
  out.println("    alt=\"Click Here to add New Row for Data view ... \" "); 
  out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
  out.println("  </TD>");
  out.println("</TR>");


    out.println("<tr>");
    out.println("<td colspan=2><INPUT type=hidden size=15 value=0 name=user_defined_data_view_count>");
    out.println("<SCRIPT>  ");
    out.println("   var user_defined_data_view=new DeepGrid(\"user_defined_data_view\",0,\"\")   ");
//                user_defined_data_view.ColumnHeaders.add(null,null,"User-Defined Data View Name",null,250,"center",DG_TEXT,null);
//                user_defined_data_view.ColumnHeaders.add(null,null,"Version",null,350,"center",DG_SELECT,UserDefinedDataViewVersionArray)
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Parameter Name\",null,200,\"center\",DG_TEXT,null)   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Description\",null,400,\"center\",DG_TEXTAREA,null)  ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Remove\",null,60,null,DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('user_defined_data_view')\")   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"\",null,0,\"center\",DG_HIDDEN,null)   ");
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");
    
    out.println("</table>");
 
 if(dataHashMap.get(FunctionsInterfaceKey.HASHMAP_KEY_FUNCTION_PARAMETER_COLLECTION) != null)
    {
      int v = 0;

      Vector CurrentfunParametersVec = (Vector) dataHashMap.get(FunctionsInterfaceKey.HASHMAP_KEY_FUNCTION_PARAMETER_COLLECTION);
      int numberofparameters = CurrentfunParametersVec.size();
      out.println("<script>");
            for(int h= 0; h<CurrentfunParametersVec.size(); h++)
            {
            v = h+1;
            
              FunctionParameterModel functionParameterModel = (FunctionParameterModel) CurrentfunParametersVec.get(h);            
              yyParameterId = functionParameterModel.getParameterDefinitionId();
              yyFunctionId = functionParameterModel.getFunctionId();
              yyParameterName = functionParameterModel.getParameterDefinitionName();
              yyParameterDesc = functionParameterModel.getParameterDefinitionDesc();
              yyParameterDefOptional = functionParameterModel.getParameterDefinitionOptional();
              yyParameterDefIsList = functionParameterModel.getParameterDefinitionIsList();

                  
                  out.println("eval(\"user_defined_data_view.RowSet.add("+v+");\"); ");
                  //eval("document.formDataView.user_defined_data_view__R"+Number(nUserDefinedDataViewCount)+"__C1.value = '"+ objDataView.m_nDataViewID+"';");
                  out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C2.readOnly=false\"); ");
                  out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C1.readOnly=false\"); "); 
                  out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C2.value = '"+ yyParameterDesc+"';\"); ");
                  out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C1.value = '"+ yyParameterName+"';\"); ");
                  out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C4.value = '"+ yyParameterId+"';\"); ");

                  
            }
           out.println("eval(\"document.formDataView.user_defined_data_view_count.value = "+ numberofparameters+" ;\"); ");
           out.println("</script>"); 
    }
}
%>
</body>
</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>
</html>
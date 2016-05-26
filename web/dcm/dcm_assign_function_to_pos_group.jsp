<%@ page  import ="com.mobinil.sds.core.utilities.Utility"
          import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*" 
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"

          import = "com.mobinil.sds.web.interfaces.dcm.*"
          
          import = "com.mobinil.sds.core.system.fn.addfunction.model.*"
          import = "com.mobinil.sds.core.system.fn.importdata.model.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.fn.*"
          import = "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
          import = "com.mobinil.sds.core.system.dcm.group.model.*"          
          import = "com.mobinil.sds.core.system.dcm.function.model.*"            
          import = "com.mobinil.sds.core.system.dcm.genericModel.*"          
%>

<% 
String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {

  }
    var arrFunctionList = new Array
             ( 
  <%fillInFunctions(request,response,out);%>   
             )
             var arrTargetDuration = new Array
             (
             <%fillInTargetDuration(request,response,out);%>
             )
</SCRIPT>
<html>

  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
  </head>
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
          if(eval(argObject+".getCell("+i+",5).getValue()")==true){
            eval(argObject+".RowSet.deleteRow("+i+");");
          }//end if
        }//end for
      }//end if
      else
      {
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",5).getValue()")==true){
            eval(argObject+".getCell("+i+",5).setValue == false; ") 
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
    showPOSRelationDetails(request, response, out);
    %>        
    <center>
      <%
      out.println("<input class='button' type='button' value='Save' onclick=checkbeforSubmit();formDataView.submit();>");
      %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
    </center>
      </form>
<%!
  public void showPOSRelationDetails(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { 
    
    String appName = request.getContextPath();
    
    out.println("<CENTER>");
    out.println("  <H2>Assign Function To POS Group</H2>");
    out.println("</CENTER>");
  
    
    HashMap dataHashMap = new HashMap(100);

   
    
    out.println("<form name='formDataView' action='' method='post'>");


    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    String strErr = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    if(!strErr.equals("") && strErr != null)
      out.println("<script language='javascript'> alert('ERROR :"+strErr+"'); </script>");    
    GroupModel groupModel = (GroupModel)dataHashMap.get(DCMInterfaceKey.DCM_EDITED_GROUP_MODEL);
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+DCMInterfaceKey.ACTION_DCM_SAVE_GROUP_FUNCTION+"\">");


  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
                   Utility.logger.debug("ppppppppppp "+ groupModel.getGroupID());
  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID+"\""+
                  " value=\""+groupModel.getGroupID()+"\">");  
                 
    

    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("    <TR class=TableHeader>");
    out.println("      <TD align='center'>Group Name</TD>");
    out.println("      <TD align='center'>Description</TD>");
    out.println("      <TD align='center'>Status</TD>");
    out.println("    </tr>");
    out.println("    <TR>");
    out.println("      <TD align='center'>"+groupModel.getGroupName()+"</TD>");
    out.println("      <TD align='center'>"+groupModel.getGroupDescription()+"</TD>");
    out.println("      <TD align='center'>"+groupModel.getGroupStatusName()+"</TD>");
    out.println("    </tr>");
    out.println("  </table>  ");
    out.println("  <br>");

    
    out.println("<table border=0 align='center' width='40%'>");
    
    out.println("<TR>");
    out.println("  <TD class=TableHeader vAlign=top colSpan=2>Functions");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','functions_count','functions','UserDefinedDataViewArray');\"><IMG"); 
    out.println("    alt=\"Click Here to add New Row for Data view ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");


    out.println("<tr>");
    out.println("<td colspan=2><INPUT type=hidden size=15 value=0 name=functions_count>");
    out.println("<SCRIPT>  ");
    out.println("   var functions=new DeepGrid(\"functions\",0,\"\")   ");
    out.println("   functions.ColumnHeaders.add(null,null,\"Function\",null,200,\"center\",DG_SELECT,arrFunctionList,null,null)   ");
    out.println("   functions.ColumnHeaders.add(null,null,\"Target\",null,200,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   functions.ColumnHeaders.add(null,null,\"Target Type\",null,200,\"center\",DG_SELECT,arrTargetDuration,null,null)   ");    
    out.println("   functions.ColumnHeaders.add(null,null,null,null,200,\"center\",DG_HIDDEN,\"new\",null,null)   ");    
    out.println("   functions.ColumnHeaders.add(null,null,\"Remove\",null,60,null,DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('functions')\")   ");
    out.println("   functions.ColumnHeaders.add(null,null,\"\",null,0,\"center\",DG_HIDDEN,null)   ");
    out.println("</SCRIPT>  ");  
    for(int i = 1 ; i <= groupModel.getGroupFunctionIDs().size() ; i++)
    {
      out.println("<SCRIPT>  ");    
      out.println("data_view_RowSet_add('2','half_day','functions_count','functions','UserDefinedDataViewArray');");
      String functionID = (String)groupModel.getGroupFunctionIDs().get(i-1);
      out.println("eval(\"document.formDataView.functions__R"+i+"__C1.value = '"+ functionID +"';\");");
      out.println("document.formDataView.functions__R"+i+"__C2.value='"+groupModel.getGroupFunctionTargets().get(i-1)+"'");      
      int functionTargetTypeID = Integer.parseInt((String)groupModel.getGroupFunctionTargetTypes().get(i-1));
      out.println("eval(\"document.formDataView.functions__R"+i+"__C3.value = '"+ functionTargetTypeID +"';\");");      
            out.println("document.formDataView.functions__R"+i+"__C4.value = 'old';");      
    out.println("</SCRIPT>  ");
    out.println("<input type='hidden' name='functions__R"+i+"__C7' value='"+functionID+"'>");
    }

    out.println("</td>");
    out.println("</tr>");
    
    out.println("</table>");
}
public void fillInFunctions(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
      HashMap hmDataHashMap = new HashMap(100);
      hmDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      Vector functionList = (Vector) hmDataHashMap.get(DCMInterfaceKey.DCM_GROUP_FUNCTION_VECTOR);    
      if(functionList != null)
      for (int i = 0; i <functionList.size() ; i++) 
      {
        FunctionModel functionModel = (FunctionModel)functionList.get(i);
        int intFunctionId = functionModel.get_function_id();
        String strFunctionName = functionModel.get_function_name();
        out.println("new Array('"+strFunctionName+"',"+intFunctionId+")");
        Utility.logger.debug("FUNCTIONS:  "+strFunctionName);
        if(i<functionList.size()-1)
        out.println(",");
      }
  }
public void fillInTargetDuration(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
      HashMap hmDataHashMap = new HashMap(100);
      hmDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      Vector targetList = (Vector) hmDataHashMap.get(DCMInterfaceKey.VECTOR_GROUP_FUNCTION_TARGET);  
      if(targetList != null)
      for (int i = 0; i <targetList.size() ; i++) 
      {
        GenericModel targetModel = (GenericModel)targetList.get(i);
        int intTargetId = Integer.parseInt(targetModel.get_primary_key_value());
        String strTargetName = targetModel.get_field_2_value();
        out.println("new Array('"+strTargetName+"',"+intTargetId+")");
        Utility.logger.debug("target:  "+strTargetName);
        if(i<targetList.size()-1)
        out.println(",");
      }
  }
  
%>

</body>
</html>
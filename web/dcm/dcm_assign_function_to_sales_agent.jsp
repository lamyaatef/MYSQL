<%@ page  import = "java.util.*"
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
          import="com.mobinil.sds.core.system.dcm.user.model.*"
          import="com.mobinil.sds.core.system.dcm.function.model.*"
%>

<% 
String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    formDataView.submit();
  }
</SCRIPT>
<html>

  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
  </head>
<SCRIPT language="javascript">
  var arrFunctionList = new Array
             ( 
  <%fillInFunctions(request,response,out);%>                    
             )
             
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
          if(eval(argObject+".getCell("+i+",2).getValue()")==true){
            eval(argObject+".RowSet.deleteRow("+i+");");
          }//end if
        }//end for
      }//end if
      else
      {
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",2).getValue()")==true){
            eval(argObject+".getCell("+i+",2).setValue == false; ") 
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
    showUserFunctionAssigned(request, response, out);
    %>        
    <center>
      <%
      out.println("<input class='button' type='button' value='Save' onclick=\""+
                  "checkbeforSubmit();\">");
      %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
    </center>
      </form>
<%!
  public void showUserFunctionAssigned(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { 
    
    String appName = request.getContextPath();
    
    out.println("<CENTER>");
    out.println("  <H2>Assign Function To Sales Agent</H2>");
    out.println("</CENTER>");
  
    
    HashMap dataHashMap = new HashMap(100);
    
    out.println("<form name='formDataView' action='' method='post'>");


    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+DCMInterfaceKey.ACTION_DCM_SAVE_ASSIGN_FUNCTION_TO_SALES_AGENT+"\">");

    DCMUserDetailModel dcmUserDetailModel = (DCMUserDetailModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    String userDetailId = dcmUserDetailModel.getUserDetailId();
    String userId = dcmUserDetailModel.getUserId();
    String userFullName = dcmUserDetailModel.getUserFullName();
    String userAddress = dcmUserDetailModel.getUserAddress();
    if(userAddress == null)userAddress = "";
    String userEmail = dcmUserDetailModel.getUserEmail();
    String userMobile = dcmUserDetailModel.getUserMobile();
    if(userMobile == null)userMobile = "";
    String regionId = dcmUserDetailModel.getRegionId();
    String regionName = dcmUserDetailModel.getRegionName();

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID+"\""+
                  " value=\""+userId+"\">");
    
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("    <TR class=TableHeader>");
    out.println("      <TD align='center'>Manager Name</TD>");
    out.println("      <TD align='center'>E-mail</TD>");
    out.println("      <TD align='center'>Mobile Phone</TD>");
    out.println("      <TD align='center'>Address</TD>");
    out.println("      <TD align='center'>Region</TD>");
    out.println("    </tr>");
    out.println("    <TR>");
    out.println("      <TD align='center'>"+userFullName+"</TD>");
    out.println("      <TD align='center'>"+userEmail+"</TD>");
    out.println("      <TD align='center'>"+userMobile+"</TD>");
    out.println("      <TD align='center'>"+userAddress+"</TD>");
    out.println("      <TD align='center'>"+regionName+"</TD>");
    out.println("    </tr>");
    out.println("  </table>  ");
    out.println("  <br>");

    
    out.println("<table border=0 align='center' width='40%'>");
    
    out.println("<TR>");
    out.println("  <TD class=TableHeader vAlign=top colSpan=2>Functions");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');\"><IMG"); 
    out.println("    alt=\"Click Here to add New Row for Data view ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");


    out.println("<tr>");
    out.println("<td colspan=2><INPUT type=hidden size=15 value=0 name=user_defined_data_view_count>");
    out.println("<SCRIPT>  ");
    out.println("   var user_defined_data_view=new DeepGrid(\"user_defined_data_view\",0,\"\")   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Function\",null,200,\"center\",DG_SELECT,arrFunctionList,null,null)   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Remove\",null,60,null,DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('user_defined_data_view')\")   ");
    //out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"\",null,0,\"center\",DG_HIDDEN,null)   ");
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");
    
    out.println("</table>");

    Vector functionsAssignedTo = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
    for(int k=1;k<=functionsAssignedTo.size();k++)
    {
      String strFunctionAssignedToId = (String)functionsAssignedTo.get(k-1); 
      out.println("<script>");
      out.println("user_defined_data_view.RowSet.add("+k+");");
      out.println("eval(\"document.formDataView.user_defined_data_view__R"+k+"__C1.value = '"+ strFunctionAssignedToId +"';\");");
      out.println("</script>");

      out.println("<input type=\"hidden\" name=\"user_defined_data_view__R"+k+"__C3\""+
                  " value=\""+strFunctionAssignedToId+"\">");      
    }
}
%>


<%!
  public void fillInFunctions(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
      HashMap hmDataHashMap = new HashMap(100);
      hmDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      Vector functionList = (Vector)hmDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);       
      if(functionList != null)
      for (int i = 0; i <functionList.size() ; i++) 
      {
        FunctionModel functionModel = (FunctionModel)functionList.get(i);
        int intFunctionId = functionModel.get_function_id();
        String strFunctionName = functionModel.get_function_name();
        out.println("new Array('"+strFunctionName+"',"+intFunctionId+")");
        if(i<functionList.size()-1)
        out.println(",");
      }
  }

%>  
</body>
</html>
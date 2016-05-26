<%@ page  import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*" 
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"

          import = "com.mobinil.sds.web.interfaces.dcm.*"
          
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.dcm.*"
          import ="com.mobinil.sds.core.system.dcm.genericModel.*" 
          import ="com.mobinil.sds.core.system.dcm.service.model.*"
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
    showPOSRelationDetails(request, response, out);
    %>        
    <center>
      <input class='button' type='button' value='Save' onclick=checkbeforSubmit();>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
    </center>
      </form>
<%!
  public void showPOSRelationDetails(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { 
    
    String appName = request.getContextPath();
    
    out.println("<CENTER>");
    out.println("  <H2>Assign POS To Service Eligibility</H2>");
    out.println("</CENTER>");
  
    
    HashMap dataHashMap = new HashMap(100);
    
    out.println("<form name='formDataView' action='' method='post'>");


    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    ServiceModel serviceModel = (ServiceModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    HashMap posAssignedToService = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

    String posServiceId = serviceModel.getPosServiceId();
    String posServiceName = serviceModel.getPosServiceName();
    String posServiceDesc = serviceModel.getPosServiceDesc();
    if(posServiceDesc == null)posServiceDesc = "";  
    String serviceEligibilityTypeId = serviceModel.getServiceEligibilityTypeId();
    String serviceEligibilityTypeName = serviceModel.getServiceEligibilityTypeName();
    String posServiceStatusTypeId = serviceModel.getPosServiceStatusTypeId();
    String posServiceStatusTypeName = serviceModel.getPosServiceStatusTypeName();


    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+DCMInterfaceKey.ACTION_DCM_SAVE_ASSIGN_POS_TO_SERVICE+"\">");

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID+"\""+
                  " value=\""+posServiceId+"\">");

    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("    <TR class=TableHeader>");
    out.println("      <TD align='center'>Service Name</TD>");
    out.println("      <TD align='center'>Description</TD>");
    out.println("      <TD align='center'>Status</TD>");
    out.println("    </tr>");
    out.println("    <TR>");
    out.println("      <TD align='center'>"+posServiceName+"</TD>");
    out.println("      <TD align='center'>"+posServiceDesc+"</TD>");
    out.println("      <TD align='center'>"+posServiceStatusTypeName+"</TD>");
    out.println("    </tr>");
    out.println("  </table>  ");
    out.println("  <br>");

    
    out.println("<table border=0 align='center' width='40%'>");
    
    out.println("<TR>");
    out.println("  <TD class=TableHeader vAlign=top colSpan=2>POSs");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');\"><IMG"); 
    out.println("    alt=\"Click Here to add New Row for Data view ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");


    out.println("<tr>");
    out.println("<td colspan=2><INPUT type=hidden size=15 value=0 name=user_defined_data_view_count>");
    out.println("<SCRIPT>  ");
    out.println("   var user_defined_data_view=new DeepGrid(\"user_defined_data_view\",0,\"\")   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"POS Code\",null,150,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"POS Name\",null,150,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Remove\",null,60,null,DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('user_defined_data_view')\")   ");
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");
    
    out.println("</table>");

    for(int j=1; j<=posAssignedToService.size(); j++)
    {
      String assignedPosCode = (String)posAssignedToService.keySet().toArray()[j-1];
      String assignedPosName = (String)posAssignedToService.get(assignedPosCode);
      out.println("<SCRIPT>");  
      out.println("user_defined_data_view.RowSet.add("+j+");");
      out.println("eval(\"document.formDataView.user_defined_data_view__R"+j+"__C1.value = '"+ assignedPosCode +"';\");");
      out.println("eval(\"document.formDataView.user_defined_data_view__R"+j+"__C2.value = '"+ assignedPosName +"';\");");
      out.println("</SCRIPT>");  

      out.println("<input type=\"hidden\" name=\"user_defined_data_view__R"+j+"__C4\""+
                  " value=\""+assignedPosCode+"\">");   
    }
}
%>

</body>
</html>
<%@ page  import = "java.util.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.fn.*"
          import = "java.io.*"
          import = "com.mobinil.sds.core.system.fn.importdata.model.*"
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit(functionId)
  {
    document.myform.<%out.print(FunctionsInterfaceKey.CONTROL_HIDDEN_FUNCTION_ID);%>.value = functionId;  
    myform.submit();
  }
</SCRIPT>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
  </head>
<div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>  

  <body>
    <CENTER>
      <H2>Functions List</H2>
    </CENTER>
    <%
    String appName = request.getContextPath();

    String formActionEdit = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                        +InterfaceKey.HASHMAP_KEY_ACTION+"="
                        +FunctionsInterfaceKey.ACTION_EDIT_FUNCTION;

    String formActionAdd = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                        +InterfaceKey.HASHMAP_KEY_ACTION+"="
                        +FunctionsInterfaceKey.ACTION_ADD_FUNCTION;
    %>
    <form name="myform" action="<%out.print(formActionEdit);%>" method="post">
    <%
    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    Vector funVec = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    %>
    <center>
    <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <TR class=TableHeader>
        <th width="37.5%" nowrap align=middle>Function</th>
        <th width="37.5%" nowrap align=middle>Description</th>
        <th width="15%" nowrap align=middle>Type</th>
        <th noWrap align=middle width="10%">Edit</th>
    </TR>	 
    <%
    for(int k= 0; k<funVec.size(); k++)
    {
      DataImportTableFunctionsModel functionModel = (DataImportTableFunctionsModel) funVec.get(k);            
      String functionIdX = functionModel.getFunctionId();
      String functionNameX = functionModel.getFunctionName();
      String functionStatusIdX = functionModel.getFunctionStatusId();
      String functionStatusNameX = functionModel.getFunctionStatusName();
      String functionSQLCallX = functionModel.getFunctionSQLCall();
      String functionTypeIdX = functionModel.getFunctionTypeId();
      String functionTypeNameX = functionModel.getFunctionTypeName();
      String functionHelpTextX = functionModel.getFunctionHelpText();
      String functionDescriptionX = functionModel.getFunctionDescription();
      
      %>
     

      
      <tr class=TableTextNote>
          <td><%=functionNameX%></td>
          <td><%=functionDescriptionX%></td>
          <td><%=functionTypeNameX%></td>
          <td><center><input class=button type="button" name="edit" id="edit" value="   Edit   " onclick=checkbeforSubmit(<%=functionIdX%>);><center></td>
      </tr>    
      <%
    }
%>          
</table>
   <input type=hidden name="<%=FunctionsInterfaceKey.CONTROL_HIDDEN_FUNCTION_ID%>" id="<%=FunctionsInterfaceKey.CONTROL_HIDDEN_FUNCTION_ID%>" value=''>

   
</form>
<br><br>
<form name="myform2" action="<%out.print(formActionAdd);%>"method="post">
  <center><input class="button" type="submit" value="Add Function"></center>
</form>
</body>

</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>

</html>

<%@ page  import = "java.util.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.gn.scopes.*"
          import = "java.io.*"
          import = "com.mobinil.sds.core.system.gn.scopes.model.*"
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit(scopeId)
  {
    document.myform.<%out.print(ScopesInterfaceKey.CONTROL_HIDDEN_SCOPE_ID);%>.value = scopeId;  
    myform.submit();
  }
  function savestatusfunction()
  {
    document.myform.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value = "<%=ScopesInterfaceKey.ACTION_SAVE_SCOPE_STATUS%>";  
    myform.submit();
  }
  function addnewscope()
  {
    document.myform.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value = "<%=ScopesInterfaceKey.ACTION_ADD_SCOPE%>";  
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
      <H2>Scopes List</H2>
    </CENTER>
    <%
    String appName = request.getContextPath();
    %>
    <form name="myform" id="myform" action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" method="post">
    <%
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" id=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\""+ScopesInterfaceKey.ACTION_EDIT_SCOPE+"\">");
    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    Vector ScopesVec = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    Vector ScopesStatusVec = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    %>
    <center>
    <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <TR class=TableHeader>
        <th width="40%" nowrap align=middle>Scope Name</th>
        <th width="40%" nowrap align=middle>Description</th>
        <th noWrap align=middle width="10%">Edit</th>
        <th noWrap align=middle width="10%">Status</th>
    </TR>	 
    <%
    for(int k= 0; k<ScopesVec.size(); k++)
    {
      ScopesModel ScopesModel = (ScopesModel) ScopesVec.get(k);            

  String dataviewId = ScopesModel.getDataviewId();
  String dataviewIssue = ScopesModel.getDataviewIssue();
  String dataviewVersion = ScopesModel.getDataviewVersion();
  String dataviewName = ScopesModel.getDataviewName();
  String dataviewOverrideSQL = ScopesModel.getDataviewOverrideSQL();
  String dataviewTypeId = ScopesModel.getDataviewTypeId();
  String dataviewTypeName = ScopesModel.getDataviewTypeName();
  String dataviewDescription = ScopesModel.getDataviewDescription();
  String dataviewStatusId = ScopesModel.getDataviewStatusId();
  String dataviewStatusName = ScopesModel.getDataviewStatusName();
  String dataviewUnique = ScopesModel.getDataviewUnique();
 
      %>
     

      
      <tr class=TableTextNote>
          <td><%=dataviewName%></td>
          <td><%=dataviewDescription%></td>
          <td><center><input class=button type="button" name="edit" id="edit" value="   Edit   " onclick=checkbeforSubmit(<%=dataviewId%>);><center></td>
          <td>
            <select name="dataviewstatus<%=dataviewId%>" id="dataviewstatus<%=dataviewId%>">
            <%
            for(int j= 0; j<ScopesStatusVec.size(); j++)
                {
                  ScopeStatusModel ScopeStatusModel = (ScopeStatusModel) ScopesStatusVec.get(j);            

                  String dataviewStatusIdX = ScopeStatusModel.getDataviewStatusId();
                  String dataviewStatusNameX = ScopeStatusModel.getDataviewStatusName();
                  String dataviewStatuDescX = ScopeStatusModel.getDataviewStatusDesc();
                  String dataviewstatusselected = "";
                  if(dataviewStatusIdX.equals(dataviewStatusId))
                  {
                    dataviewstatusselected = "selected";
                  }
                  %><option value="<%=dataviewStatusIdX%>" <%=dataviewstatusselected%>><%=dataviewStatusNameX%></option><%
                }
            %>
            </select>
          </td>
      </tr>    
      <%
    }
%>          
</table>
   <input type=hidden name="<%=ScopesInterfaceKey.CONTROL_HIDDEN_SCOPE_ID%>" id="<%=ScopesInterfaceKey.CONTROL_HIDDEN_SCOPE_ID%>" value=''>
    <br><br>
   <center>
           <input class="button" type="button" value="Add Scope" onclick="addnewscope();">
           <input class="button" type="button" value="Save Status" onclick="savestatusfunction();">
   </center>
</form>
</body>

</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>

</html>

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
</SCRIPT>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
  </head>
<div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>  

  <body>
    <CENTER>
      <H2>Data Views List</H2>
    </CENTER>
    <%
    String appName = request.getContextPath();

    String formActionSaveStatus = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                        +InterfaceKey.HASHMAP_KEY_ACTION+"="
                        +ScopesInterfaceKey.ACTION_SAVE_DATA_VIEW_STATUS;
    %>
    <form name="myform" action="<%out.print(formActionSaveStatus);%>" method="post">
    <%
    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    Vector ScopesVec = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    Vector ScopesStatusVec = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

    if(dataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
    {
      String errMsg = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
      char[] chrErrMsg = errMsg.toCharArray();
      String[] listOfErrReports = new String[100];
      int cont = 0;
      int startindex = 0;
      for(int c=0;c<(chrErrMsg.length)-1;c++)
      {
        if(chrErrMsg[c] == '&' && chrErrMsg[c+1] == '&')
        {
          listOfErrReports[cont] = errMsg.substring(startindex,c);
          cont++;
          startindex = c+2;
        }
      }
      for(int l =2;l<listOfErrReports.length;l++)
      {
         if(listOfErrReports[l] != null)
         {
           out.print("<script>alert(\"Dataview : "+listOfErrReports[1]+" can't be changed because of "+listOfErrReports[0]+" : "+listOfErrReports[l]+" \");</script>");
         }
      }
      
    }
    %>
    <center>
    <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <TR class=TableHeader>
        <th width="45%" nowrap align=middle>Scope Name</th>
        <th width="45%" nowrap align=middle>Description</th>
        <th width="10%" nowrap align=middle>Status</th>
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
          <td>
            <select name="<%=ScopesInterfaceKey.CONTROL_SELECT_DATA_VIEW_STATUS+dataviewId%>" id="<%=ScopesInterfaceKey.CONTROL_SELECT_DATA_VIEW_STATUS+dataviewId%>">
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
            <input type="hidden" name="<%=ScopesInterfaceKey.CONTROL_HIDDEN_DATA_VIEW_PREVIOUS_STATUS+dataviewId%>" id="<%=ScopesInterfaceKey.CONTROL_HIDDEN_DATA_VIEW_PREVIOUS_STATUS+dataviewId%>" value="<%=dataviewStatusId%>">
            <input type="hidden" name="<%=ScopesInterfaceKey.CONTROL_HIDDEN_DATA_VIEW_NAME+dataviewId%>" id="<%=ScopesInterfaceKey.CONTROL_HIDDEN_DATA_VIEW_NAME+dataviewId%>" value="<%=dataviewName%>">
          </td>
      </tr>    
      <%
    }
%>          
</table>
   <input type=hidden name="<%=ScopesInterfaceKey.CONTROL_HIDDEN_SCOPE_ID%>" id="<%=ScopesInterfaceKey.CONTROL_HIDDEN_SCOPE_ID%>" value=''>

   
<br><br>
  <center><input class="button" type="submit" value="Save Status"></center>
</form>
</body>

</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>

</html>

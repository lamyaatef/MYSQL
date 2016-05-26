<%-- 
    Document   : active_stk_excel
    Created on : 25/10/2010, 14:50:10
    Author     : Ahmed Adel
--%>
<%@ page language="java" contentType="Application/vnd.excel;charset=windows-1256"
    pageEncoding="ISO-8859-1"%>

<%@ page      import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              import="com.mobinil.sds.core.system.scm.dao.*"
              %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        
    </head>
    <body>
    <%
    HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    response.addHeader("Content-Disposition", "attachment; filename=report.xls");
   Vector <POSModel> POSs= (Vector)dataHashMap.get(SCMInterfaceKey.POS_STATUS_SEARCH_RESULT);
   %>
   <center>
   <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border=1>

    <th scope="col" class=TableHeader>STK Dial Number</th>
<%

for (int i = 0; i < POSs.size(); i++)
{
    %>

    <tr>
        <TD align=left class="normalText" >

<% String Dial=POSs.get(i).getSTK_Dial().toString();
        out.println(Dial);
        System.out.print(Dial);
        %>
            
       
	</tr>
<%}

%>

   </TABLE>

</center>

    </body>
</html>

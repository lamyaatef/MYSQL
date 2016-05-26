<%-- 
    Document   : remaining_stk_stock
    Created on : 13/12/2010, 10:09:15
    Author     : Ahmed Adel
--%>

<%@ page language="java" contentType="Application/vnd.excel;charset=windows-1256"
    pageEncoding="ISO-8859-1"%>

<%@ page      import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"
              import="com.mobinil.sds.core.system.request.model.*"
              import="com.mobinil.sds.core.system.request.dao.*"
              %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">

    </head>
    <body>
    <%
    HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    response.addHeader("Content-Disposition", "attachment; filename=report.xls");
   Vector <String> STKStockRemaining= (Vector)dataHashMap.get(SCMInterfaceKey.STOCK_REMAINING_VECTOR);

   System.out.println("size is "+STKStockRemaining.size());
   System.out.println("obj is "+STKStockRemaining);
    %>
   <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="10%" border=1>
<th scope="col" class=TableHeader>STK_DIAL_NUMBER</th>
<%for(int i=0;i<STKStockRemaining.size();i++)
    {
    %>
    <tr>
    <td align=left class="normalText">
<%=STKStockRemaining.get(i)%>
</td>
</tr>
<%}%>
   </TABLE>

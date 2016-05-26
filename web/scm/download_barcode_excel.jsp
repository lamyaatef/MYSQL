<%-- 
    Document   : download_barcode_excel
    Created on : 14/12/2010, 17:28:38
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
        <title>JSP Page</title>
    </head>
    <body>
       <%
         HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    
    String appName = request.getContextPath();
    Vector<BarcodeRequestExcelModel> excelModels=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_BARCODE_REQUEST_STATICTIS);
    String pName = (String) dataHashMap.get(SCMInterfaceKey.BARCODE_PERSON_NAME);
    response.addHeader("Content-Disposition", "attachment; filename="+pName+".xls");
        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="10%" border=1>
        <tr>
        <th scope="col" class=TableHeader>POS Code</th>
        <th scope="col" class=TableHeader>POS Name</th>
        <th scope="col" class=TableHeader>Qty</th>
        <th scope="col" class=TableHeader>District</th>
        <th scope="col" class=TableHeader>Field Rep Name</th>
        <th scope="col" class=TableHeader>Rol</th>
        <th scope="col" class=TableHeader>POS Rate</th>
        </tr>
        <%for (int i=0 ; i<excelModels.size();i++){
            %>
            <tr>
        <td align=left class="normalText">
            <%=excelModels.get(i).getPOSCode()%>
        </td>
        <td align=left class="normalText">
            <%=excelModels.get(i).getPOSName()%>
        </td>
        <td align=left class="normalText">
            <%=Integer.toString(excelModels.get(i).getQuantity())%>
        </td>
        <td align=left class="normalText">
            <%=excelModels.get(i).getDistrict()%>
        </td>
        <td align=left class="normalText">
            <%=excelModels.get(i).getRepName()%>
        </td>
        <td align=left class="normalText">
            <%=excelModels.get(i).getPOSType()%>
        </td>
        <td align=left class="normalText">
            <%=Integer.toString(excelModels.get(i).getPOSPayment())%>
        </td>
           </tr>
        <%}%>
        </TABLE>
    </body>
</html>

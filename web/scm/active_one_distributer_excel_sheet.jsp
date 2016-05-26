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
    String posCode=(String)dataHashMap.get(SCMInterfaceKey.POS_CODE);
    Vector <DistributerStaticDataModel> distributerStaticData= (Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_DISTRIBUTERS_STATIC_DATA);
   Vector <DistributerSTKDetailsModel> distributerSTKData=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_DISTRIBUTERS_STK_DETAILS);
    %>
   <center>
   <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="10%" border=1>


<%
for (int i = 0; i <= 2; i++)
{%>
<th scope="col" class=TableHeader><%=distributerStaticData.get(i).getDataName()%></th>
<%}%>
<th scope="col" class=TableHeader>DCM Name</th>
<%
for (int i = 3; i <= 4; i++)
{%>
<th scope="col" class=TableHeader><%=distributerStaticData.get(i).getDataName()%></th>
<%}%>
<th scope="col" class=TableHeader>DCM Code</th>
<th scope="col" class=TableHeader>DCM Owner</th>
<th scope="col" class=TableHeader>English Address</th>
<th scope="col" class=TableHeader>City</th>
<%for (int i = 5; i <= 7; i++)
{%>
<th scope="col" class=TableHeader><%=distributerStaticData.get(i).getDataName()%></th>
<%}%>
<th scope="col" class=TableHeader>STK Dial Number</th>

<th scope="col" class=TableHeader><%=distributerStaticData.get(8).getDataName()%></th>

<th scope="col" class=TableHeader>STK Dial Number</th>

<%for (int i =9; i <distributerStaticData.size() ; i++)
{%>
<th scope="col" class=TableHeader><%=distributerStaticData.get(i).getDataName()%></th>
<%}%>

<th scope="col" class=TableHeader>assign Date</th>

<%for(int dists=0;dists<distributerSTKData.size();dists++){%>

<tr>
<%
for (int i = 0; i <= 2; i++)
{%>
<td align=left class="normalText">
<%=distributerStaticData.get(i).getDataValue()%>
</td>
<%}%>
<td align=left class="normalText">
<%=distributerSTKData.get(dists).getDCMName()%>
</td>
<%
for (int i = 3; i <= 4; i++)
{%>
<td align=left class="normalText">
<%=distributerStaticData.get(i).getDataValue()%>
</td>
<%}%>
<td align=left class="normalText">
<%=distributerSTKData.get(dists).getDCMCode()%>
</td>
<td align=left class="normalText">
<%=distributerSTKData.get(dists).getDCMOwner()%>
</td>
<td align=left class="normalText">
<%=distributerSTKData.get(dists).getDCMAddress()%>
</td>
<td align=left class="normalText">
<%=distributerSTKData.get(dists).getDCMCity()%>
</td>
<%for (int i = 5; i <= 7; i++)
{%>
<td align=left class="normalText">
<%=distributerStaticData.get(i).getDataValue()%>
</td>
<%}%>
<td align=left class="normalText">
<%=distributerSTKData.get(dists).getSTK_DIAL()%>
</td>
<td align=left class="normalText">
<%=distributerStaticData.get(8).getDataValue()%>
</td>
<td align=left class="normalText">
<%=distributerSTKData.get(dists).getSTK_DIAL()%>
</td>
<%for (int i = 9; i <distributerStaticData.size();i++)
{%>
<td align=left class="normalText">
<%=distributerStaticData.get(i).getDataValue()%>
</td>
<%}%>
<td align=left class="normalText">
<%=distributerSTKData.get(dists).getSTK_ASSIGN_DATE()%>
</td>
</tr>
<%}%>
</TABLE>
</center>

<%DistributerSTKDataDAO.activeSTKsForValidDistributers(posCode);%>
</body>
</html>
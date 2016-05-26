<%-- 
    Document   : show_speific_distributer_stks
    Created on : 3/11/2010, 15:56:17
    Author     : Ahmed Adel
--%>
<%@page import="com.lowagie.text.Document"%>
<%@ page contentType="text/html;charset=windows-1252"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              %>

    <%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String staticticsAction=appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?";
            String DCMName=(String)dataHashMap.get(SCMInterfaceKey.DISTRIBUTER_NAME);
    %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title>JSP Page</title>
    </head>
    <body>
        <div align="center">
        <br>
        <br>
        <h2 align="center"><%=DCMName%> STKs Statictics</h2>
        <br>
        <br>

        <%
            Vector <STKOwnerModel> distributerSTKs=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_STKS_FOR_DISTRIBUTER);
            int distributerSTKsVectorsize=0;
            if(distributerSTKs!=null&&distributerSTKs.size()!=0){
            distributerSTKsVectorsize=distributerSTKs.size();
        %>

        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" align="center">
                   <tr>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">STK Dial Number</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Stk Status</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Dcm Verified</font></td>
                    </tr>

               <%

                for(int i=0;i<distributerSTKsVectorsize;i++){
                    STKOwnerModel distributerSTKsReport = (STKOwnerModel)distributerSTKs.get(i);
                %>
                    <tr>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=distributerSTKsReport.getStkDial()%></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=distributerSTKsReport.getStkStatusName() %></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=distributerSTKsReport.getDcmVerifiedStatusName() %></font></td>
                    </tr>
                <%}}%>
                </table>
                <br>
                <input type="button" class="button" onclick="print()" value="Print">
        </div>


    </body>
</html>

<%-- 
    Document   : show_stl_stock_remaining
    Created on : 9/11/2010, 17:01:24
    Author     : Ahmed Adel
--%>
<%@ page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"
              %>

<html>
    <head>
        <%
                    String appName = request.getContextPath();
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title>Show STK Stock Remaining</title>
    </head>
    <%
     HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    %>
    <body>
        <div align="center">
        <br>
        <br>
        <h2>            STK Stock Remainings</h2>
        <br>
        <br>
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

            <tr>
                <td class=TableHeader nowrap align=center ><font style="font-size: 15px;font-family: tahoma;line-height: 15px"><b>Remaining STKs in the stock</b></font></td>
            </tr>
            <tr>
                <td align=center > <%=(Integer)dataHashMap.get(SCMInterfaceKey.STK_QUANTITY_REMAINING)%></td>
            </tr>
        </table>
        </div>
    </body>
</html>

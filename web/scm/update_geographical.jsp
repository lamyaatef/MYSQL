<%-- 
    Document   : view_edit_parent
    Created on : Dec 16, 2012, 5:34:38 PM
    Author     : Shady Akl
--%>

<%@ page contentType="text/html;charset=windows-1252"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.region.dao.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



<%
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String strUserID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title>JSP Page</title>
    </head>
    <head>


    </head>
    <body>

        <br>
        <br>
        <h2 align="center">Update Successfully</h2>
        <br>
        <br>

        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" align="center">
           



            <tr>        <input type = button value = "Back" onClick = "history.go(-1)">
            </tr>

        </table>



    </body>
</html>

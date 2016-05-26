<%-- 
    Document   : sessionExpired
    Created on : Dec 20, 2012, 6:27:59 PM
    Author     : Shady Akl
--%><%@page contentType="text/html" pageEncoding="UTF-8"
            import="com.mobinil.sds.web.interfaces.gn.ua.UserAccountInterfaceKey"
            import="com.mobinil.sds.web.interfaces.InterfaceKey"
            %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            out.print("Here In redirect Page");
            String link = request.getContextPath() + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?" + InterfaceKey.HASHMAP_KEY_ACTION + "=" + UserAccountInterfaceKey.ACTION_SHOW_LOGIN_PAGE;
        %> 
        % 
        <c:redirect url ="<%=link%>" /> 
    </body>
</html >

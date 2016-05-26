<%-- 
    Document   : save
    Created on : Sep 12, 2011, 3:57:57 PM
    Author     : Ahmed Adel
--%>

<%@page import="com.mobinil.sds.web.interfaces.InterfaceKey"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result</title>
    </head>
    <body>
        <h3 align="Left">
        ${cause}
       </h3>
       <% HttpSession s = request.getSession();
          String userId  =(String)s.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
        %>
        
       
        <form:form id="back" action='/SDS/dl/list/DCMList.htm' method="GET" > 
            <input type="hidden" name="data_user_id" value="<%=userId%>"/>
            <input type="hidden" name="hiddendl" value="${hiddendl}"/>
            <input value="Back" type="submit" onclick="history.go(-2);"/>
        </form:form>
    </body>
</html>

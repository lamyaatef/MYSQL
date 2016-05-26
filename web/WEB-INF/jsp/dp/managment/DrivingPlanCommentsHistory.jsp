<%-- 
    Document   : DrivingPlanCommentsHistory
    Created on : Oct 16, 2011, 5:33:40 PM
    Author     : Gado
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

        
<style>
    .error {
        color: #ff0000;
    }

    .errorblock {
        color: #000;
        background-color: #ffEEEE;
        border: 3px solid #ff0000;
        padding: 8px;
        margin: 16px;
    }
</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/js/media/css/Template1.css"/>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/js/media/css/demo_page.css"/>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/js/media/css/demo_table.css"/>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/js/media/css/TableTools.css"/>
    </head>
    <body>
        <h2 align="center">Driving Plan Comments History </h2>
        
        <table id="results" align="center" cellpadding="0" cellspacing="0" border="0"  class="display"  id="example">          
            <thead>
            <th>Plan Name</th>
            <th>User Name</th>
            <th>Status</th>
            <th>Date</th>
            <th>Comment</th>
                </thead>
                <tbody>
        <c:forEach items="${histories}" var="history">
            <tr>
                <td class="center">
                    ${history.dp.planName}
               </td>
               <td  class="center">
                   ${history.userID.person.personName}
              </td>
              <td class="center">
                   ${history.status}
              </td>
              <td  class="center">
               <fmt:formatDate value="${history.modifyDate}" pattern="dd-MM-yyy" /><br />
                   
              </td>
               <td  class="center">
                   ${history.comment}
              </td>
              
              
             </tr>   
        </c:forEach>
             </tbody>
        </table>

    </body>
</html>

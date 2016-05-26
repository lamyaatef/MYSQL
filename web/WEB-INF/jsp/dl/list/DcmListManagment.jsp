<%-- 
    Document   : DcmListManagment
    Created on : Nov 9, 2011, 3:52:39 PM
    Author     : Gado
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/js/media/css/Template1.css"/>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/js/media/css/demo_page.css"/>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/js/media/css/demo_table.css"/>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/js/media/css/TableTools.css"/>

        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <c:if test="${!empty results}">
        <script type="text/javascript" language="javascript" src="/SDS/resources/js/media/js/jquery.js"></script>
        <script type="text/javascript" language="javascript" src="/SDS/resources/js/media/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8" src="/SDS/resources/js/media/js/ZeroClipboard.js"></script>
        <script type="text/javascript" charset="utf-8" src="/SDS/resources/js/media/js/TableTools.js"></script>
        <script type="text/javascript" charset="utf-8">
            $(document).ready( function () {
                $('#example').dataTable( {
                    "sDom": 'T<"clear">lfrtip'
                } );
            } );
                        
        </script>
    </c:if>

    <title>Dcm List</title>
</head>
<body>
<center>
    <h2>Dcm List Management</h2>
    </center>
<br>
    <table align="center" cellpadding="0" cellspacing="0" border="0"  class="display"  id="example">                    
        <c:if test="${!empty results}">
            <thead>
                <tr>
                    <th >Name</th>
                    <!--<th >Type</th>-->
                    <th >Status</th>
                    <th >Created By</th>
                    <th >Creation Date</th>
                    <th >Last status Date</th>
                    <th> Add </th>
                    <th> Deactive </th>
                    

                </tr>
            </thead>                             
            <tbody>


            <c:forEach items="${results}" var="DL">
                <tr>
                    <td class="center">
                        ${DL.DL_LIST_NAME}
                    </td>
                    <!--
                    <td class="center">
                        ${DL.DL_TYPE.typeName}    
                    </td>
                    -->
                    <td class="center">
                        ${DL.DL_LIST_STATUS.statusName}    
                    </td>
                    <td class="center">
                        ${DL.USER_ID.person.personName}    
                    </td>
                    <td  class="center">
                    <fmt:formatDate value="${DL.CREATION_DATE}" pattern="dd-MM-yyy" /><br />
                    </td>
                    <td  class="center">
                    <fmt:formatDate value="${DL.LAST_STATUS_DATE}" pattern="dd-MM-yyy" /><br />
                    </td>
                <td class="center">
                    
                    <input type="button"  value ="Details" <c:if test="${DL.DL_LIST_STATUS.statusId==3}">disabled="disabled"</c:if>  onclick ="Details(${DL.DL_LIST_ID})"/>  
                </td>
                <td class="center">
                    <input type="button"   value ="Deactive" <c:if test="${DL.DL_LIST_STATUS.statusId==3}">disabled="disabled"</c:if> onclick ="DeActive(${DL.DL_LIST_ID})"/>  
                </td>
                </tr>
                </c:forEach>     
            </tbody>
        </c:if>
            <c:if test="${empty results}">
                <center>
                <h2>There's No Lists</h2>
                </center>
            </c:if>
            
    </table>
    <c:url var="history" value="/dl/list/history.htm" />
    <c:url var="deactive" value="/dl/list/deactive.htm" />
    <form id="edit" action="${upload}">
        <input type="hidden" id="hiddendl" name="hiddendl"/>
    </form>
</body>
<script>
    function Details (id)
    {        
        document.getElementById("hiddendl").value=id;
        document.getElementById("edit").action='${history}';
        document.getElementById("edit").submit();
        
    }
    function DeActive (id)
    {
        document.getElementById("hiddendl").value=id;
        document.getElementById("edit").action='${deactive}';
        document.getElementById("edit").submit();
        
    }
</script>
</html>

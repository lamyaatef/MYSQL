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

    
</head>

<body>
<center>
<h2>POS List</h2>
</center>
    <table align="center" cellpadding="0" cellspacing="0" border="0"  class="display"  id="example">                    
        <c:if test="${!empty results}">
            <thead>
                <tr>
                    <th >Name</th>
                    <th >Code</th>
                    <th >User</th>
                    <th> Creation Date </th>
                    <th >Delete</th>
                </tr>
            </thead>                             
            <tbody>


            <c:forEach items="${results}" var="POS">
                <tr>
                    <td class="center">
                        ${POS.posName}
                    </td>
                    <td class="center">
                        ${POS.posCode}    
                    </td>
                    <td>
                        ${POS.USER_ID.person.personName}
                    </td>
                    <td  class="center">
                    <fmt:formatDate value="${POS.enrtyDate}" pattern="dd/MM/yyy" /><br />
                    </td>
                    
                    </td>
                    <td class="center">
                        <input type="button" value="Delete" onclick="deletepos ('${POS.posCode}') "/>
                    </td>
                </tr>
                </c:forEach>     
            </tbody>
       
    </table>
    <br>
    <br>
  </c:if>
    <c:if test="${empty results}">
        <center>
        There's No POSs
        </center>
        <br>
        <br>
    </c:if>
<center>
    
    
    <c:url var="delete" value="/xyz/delete.htm" />
    <c:url var="add" value="/xyz/add.htm" />
    <form id="edit" action="${upload}">
        <input type="hidden" id="hiddendl" name="hiddendl"/>
        POS Code :
        <input type="text" id="poscode" name="poscode"/>
        <input type ="button" value ="add new poscode" onclick="add()">
    </form>
        </center>
</body>
<script>
    function deletepos (id)
    {        
        document.getElementById("hiddendl").value=id;
        document.getElementById("edit").action='${delete}';
        document.getElementById("edit").submit();
        
    }
    function add (id)
    {
        
        document.getElementById("edit").action='${add}';
        document.getElementById("edit").submit();
        
    }
</script>
</html>

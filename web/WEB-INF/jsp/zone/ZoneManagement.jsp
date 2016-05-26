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
    <c:if test="${!empty zones}">
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
<center>
 <h2>Zone List</h2>
 </center>
<body>
    <table align="center" cellpadding="0" cellspacing="0" border="0"  class="display"  id="example">                    
        <c:if test="${!empty zones}">
            <thead>
                <tr>
                    <th >ID</th>
                    <th >Name</th>
                    <th >View governorates</th>
                    <th >Delete</th>
                </tr>
            </thead>                             
            <tbody>


            <c:forEach items="${zones}" var="zone">
                <tr>
                    <td class="center">
                        ${zone.zoneCode}
                    </td>
                    <td class="center">
                        ${zone.zoneName}    
                    </td>
                   
                    <td class="center">
                        <input type="button" value="View governorate" onclick="view ('${zone.zoneCode}') "/>
                    </td>
                    <td class="center">
                        <input type="button" value="Delete" onclick="deletezone ('${zone.zoneCode}') "/>
                    </td>
                </tr>
                </c:forEach>     
            </tbody>
             </table>
        </c:if>
            
           <h2>${MSG}</h2>
    
    <br>
    <br>
<center>
    <c:url var="delete" value="/zone/delete.htm" />
   
    <c:url var="add" value="/zone/new.htm" />
    <c:url var="view" value="/zone/getgover.htm" />
    
    <form id="edit" action="${upload}">
        <input type="hidden" id="hiddendl" name="hiddendl"/>
        <input type ="button" value ="add new zone" onclick="add()">
    </form>
</center>
</body>
<script>
    function deletezone (id)
    {        
        document.getElementById("hiddendl").value=id;
        document.getElementById("edit").action='${delete}';
        document.getElementById("edit").submit();
        
    }
     function view (id)
    {        
        document.getElementById("hiddendl").value=id;
        document.getElementById("edit").action='${view}';
        document.getElementById("edit").submit();
        
    }
   
    function add ()
    {
        document.getElementById("edit").action='${add}';
        document.getElementById("edit").submit();
    }
</script>
</html>

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
    <c:if test="${!empty govers}">
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

    <h2 align="center"> ${zoneName} 's Governorates</h2>
</head>
<body>
    <table align="center" cellpadding="0" cellspacing="0" border="0"  class="display"  id="example">                    
        <c:if test="${!empty govers}">
            <thead>
                <tr>
                    <th >Name</th>
                    <th >Code </th>
                    <th> UnAssgin </th>
                </tr>
            </thead>                             
            <tbody>


            <c:forEach items="${govers}" var="gover">
                <tr>
                    <td class="center">
                        ${gover.governorateName}
                    </td>
                    <td class="center">
                        ${gover.governorateCode}
                    </td>
                    <td
                    
                    <td class="center">
                        <input type="button" value="UnAssign" onclick="deletegover ('${gover.governorateId}') "/>
                    </td>
                </tr>
                </c:forEach>     
            </tbody>
             </table>
        </c:if>
            
           <h2>${MSG} </h2>

                  
                  
    <br>
    <br>
<center>
    <c:url var="delete" value="/zone/delgover.htm" />
    <c:url var="upload" value="/zone/assigngover.htm" />
    
    <form id="edit" action="${upload}">
        <input type="hidden" id="zonecode" name="zonecode"/>
        <input type="hidden" id="hiddendl" name="hiddendl"/>
        <input type ="button" value ="add new governorate" onclick="upload()"/>
        <input type="button" VALUE="Back" onClick="history.go(-1);return true;">
    </form>
</center>
</body>
<script>
    function deletegover (id)
    {   
        document.getElementById("zonecode").value='${zonecode}';
        document.getElementById("hiddendl").value=id;
        document.getElementById("edit").action='${delete}';
        document.getElementById("edit").submit();
        
    }
     function upload ()
    {
        document.getElementById("hiddendl").value='${zonecode}';
        document.getElementById("edit").action='${upload}';
        document.getElementById("edit").submit();
    }
   
</script>
</html>


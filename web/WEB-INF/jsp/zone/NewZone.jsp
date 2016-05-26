<%-- 
    Document   : DrivingPlanMangment
    Created on : Sep 7, 2011, 8:05:11 PM
    Author     : Ahmed Adel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
      
    </head>
    <body>
        <h2 align="center">Add New Zone</h2>
        </br>
        </br>


        <c:url var="save" value="/zone/add.htm" />
        <form:form modelAttribute="zone" id='mainForm' name="mainForm" action="${save}" method="GET">
            <form:hidden path="zoneCode"/>
            <table border="1" align="center">
                <tr>
                    <td>
                        Name :  
                    </td>
                    <td>
                        <form:input path="zoneName"/>                        
                        <font color="red"><form:errors path="zoneName" /></font>
                    </td>
                </tr>
               
                <tr>
                    <td align="center">   
                        <input   type="button" value="Save" onclick="save();">
                    </td>
                    <td align="center">   
                        <input TYPE="button" VALUE="Back" onClick="history.go(-1);return true;">
                    </td>
                </tr>
            </table>
            
        </form:form>

    </table>          
</div>

<h3 align="center" style="color: RED">${MSG}</h3>

</body>
<script>
  
    function save ()
    {
            
        document.getElementById('mainForm').action='${save}';
        document.getElementById('mainForm').submit();
    
    }
</script>    
</html>

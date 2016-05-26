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
        <h2 align="center">Assign Governorate to  Zone  ${zoneName}</h2> 
        </br>
        </br>


        <c:url var="save" value="/zone/addgover.htm" />
        <form:form  id='mainForm' name="mainForm" action="${save}" method="post">
            
            <table border="1" align="center">
                <tr>
                    <td>
                        Governorate Code :  
                    </td>
                    <td>
                        <input type="text" name="govercode" id="govercode"/>
                        <input type="hidden" name="zonecode" id="zonecode" value="${zonecode}">
                    </td>
                </tr>    
                <tr>
                    <td align="center" colspan="2">   
                        <input type="button" value="Save" onclick="save();">
                    </td>
                    
                </tr>
            </table>
        </form:form>
       
           
           ${msg}
    

    </table>          
</div>
</body>
<script>
  
    function save ()
    {
            
        document.getElementById('mainForm').action='${save}';
        document.getElementById('mainForm').submit();
    
    }
</script>    
</html>

<%-- 
    Document   : uploadList
    Created on : Nov 1, 2011, 5:11:35 PM
    Author     : Ahmed Adel
--%>

<%@page import="com.mobinil.mcss.dp.dpManagement.model.Month"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

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
        <title>Dcm List </title>
    </head>
    <body id="dt_example">
        <br>
        <h3 align="center" style="color: Black" >New Dcm List</h3>
        </br>
        </br>

        <c:url var="save" value="/dl/list/save.htm" />

        <form:form modelAttribute="dcmList" id='mainForm' name="mainForm" action="${save}" method="get" >
            <table border="1" style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px" align="center">
                <tr class="TableTextNote">
                    <td class="TableTextNote">
                        Name : 
                    </td>   
                    
                    <td class="TableTextNote">
                        <form:input path="DL_LIST_NAME"/>
                        <font color="red"><form:errors path="DL_LIST_NAME" /></font>
                    </td>
               </tr>
               <!--
               <tr>
                   <td class="TableTextNote">
                       List Type
                   </td>
                    <td class="TableTextNote">
                        <%--<form:select path="DL_TYPE.typeId" onchange="changeType(this)">
                            <form:options items="${dcmtypelist}" itemValue="typeId" itemLabel="typeName" />
                        </form:select>--%>
                    </td>
                </tr> 
               -->
                
                
              <!--  <tr  style="display:none" id="dataview" >
                
                   <td class="TableTextNote">
                       Data view
                   </td>
                    <td class="TableTextNote">
                        <%--<form:select path="DATA_VIEW_ID.dataViewId" >
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${dataviews}" itemValue="dataViewId" itemLabel="dataviewName" />
                        </form:select>--%>
                    </td>
                </tr>-->
               
                <tr class="TableTextNote">
                    <td align="center" colspan="2">
                        <input  type="submit" value="Save" />
                    </td>
                </tr>
            </table>
    <center><h3 style="color: RED">
                    ${msg}</h3>
                    </center>
        </form:form>
        
        <script>
           function changeType (value)
           {
            if(value.value==2)
            document.getElementById("dataview").style.display='';  
            if(value.value==1)
            document.getElementById("dataview").style.display='none';  
           }
               
        </script>


    </body>
</html>
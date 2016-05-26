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
        <link rel="styles(type);heet" type="text/css"
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
        <h2>${DcmListName} Lists</h2>
    </center>
    <table align="center" cellpadding="0" cellspacing="0" border="0"  class="display"  id="example">                    
        <c:if test="${!empty results}">
            <thead>
                <tr>
                    <th >Month</th>
                    <th >Year</th>
                    <th >Affected Rows</th>
                    <th >Created Date</th>
                    <th >Upload</th>
                    <th >Delete</th>
                    <th >Export</th>
                </tr>
            </thead>                             
            <tbody>


                <c:forEach items="${results}" var="DLHistory">
                    <tr>
                        <td class="center">
                            ${DLHistory.month.monthName}    
                        </td>
                        <td class="center">
                            ${DLHistory.year}    
                        </td>
                        <td class="center">
                            ${DLHistory.affectedRows}    
                        </td>

                        <td  class="center">
                            <fmt:formatDate value="${DLHistory.creationDate}" pattern="dd-MM-yyy" /><br />
                        </td>

                        <td class="center">
                            <input type="button"  value ="Upload" onclick ="updateList(${DLHistory.historyId})" <c:if test="${deleted=='deleted'}">disabled="disabled"</c:if>/>  
                            </td>
                            <td class="center">
                                <input type="button"   value ="Delete" onclick ="deleteDL(${DLHistory.historyId})" <c:if test="${deleted=='deleted'}">disabled="disabled"</c:if>/>  
                            </td>
                            <td class="center">
                                <input type="button"   value ="Export" onclick ="exportDL(${DLHistory.historyId},'${DLHistory.month.monthName}','${DLHistory.year}')"/>  
                        </td>
                    </tr>
                </c:forEach>     
            </tbody>
        </c:if>
    </table>
    <center>
        <input type="button"  value ="Add New List" onclick ="newList(${DcmListId})" <c:if test="${deleted=='deleted'}">disabled="disabled"</c:if>/>    
            <br>
        <c:if test="${list_excel_file!=null}">   
            <a href="/SDS/file/${list_excel_file}">Download file</a>
        </c:if>
    </center>
    <c:url var="upload" value="/dl/list/upload.htm" />
    <c:url var="delete" value="/dl/list/delete.htm" />
    <c:url var="export" value="/dl/list/export.htm" />
    <form id="edit" action="${upload}">
        <input type="hidden" id="hiddendl" name="hiddendl" value="${hiddendl}"/>
        <input type="hidden" id="hiddenhistory" name="hiddenhistory"/>
        <input type="hidden" id="monthName" name="monthName"/>
        <input type="hidden" id="yearName" name="yearName"/>
        <input type="hidden" id="update" name="update">
        <c:if test="${deleted=='deleted'}">

            <input type="hidden" id="deleted" name="deleted" value="deleted"/>

        </c:if>
    </form>
</body>
<script>
    function newList (id)
    {
        document.getElementById("update").value=0;
        document.getElementById("hiddendl").value=id;
        document.getElementById("edit").submit();
        
    }
    function updateList (historyid)
    {
        document.getElementById("update").value=1;
        document.getElementById("hiddenhistory").value=historyid;
        document.getElementById("edit").submit();
        
    }
    function deleteDL (historyid)
    {   
        document.getElementById("hiddendl").value='${DcmListId}';
        document.getElementById("hiddenhistory").value=historyid;
        document.getElementById("edit").action = '${delete}';
        document.getElementById("edit").submit();
        
    }
    function exportDL (historyid,month,year)
    {        
        document.getElementById("hiddendl").value='${DcmListId}';
        document.getElementById("hiddenhistory").value=historyid;
        document.getElementById("monthName").value=month;
        document.getElementById("yearName").value=year;
        document.getElementById("edit").action = '${export}';
        document.getElementById("edit").submit();
        
    }
</script>
</html>

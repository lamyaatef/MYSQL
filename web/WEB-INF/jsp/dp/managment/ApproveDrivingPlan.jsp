<%-- 
    Document   : ApproveDrivingPlan
    Created on : Oct 12, 2011, 12:01:03 PM
    Author     : Gado
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
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
    <body>
        <br>
        <h3 align="center" style="color: Black" >Driving Plan Approve Management</h3>
        </br>
        </br>
        <table align="center"  cellspacing="0" border="0"  class="display"  id="example">                    
            <c:if test="${!empty results}">
                <thead>
                    <tr>
                        <th >Name</th>
                        <th >Channel</th>
                        <th >DCM Level</th>
                        <th >DCM Payment Type</th>
                        <th >DCM Payment Level</th>
                        <th >Commission Type</th>
                        <th >Month</th>
                        <th >Year</th>
                        <th >Status</th>
                        <th>View Factors</th>  
                        <th>Approve</th>
                        <th >Refuse</th>
                        <th >Delete</th>


                    </tr>
                </thead>                             
                <tbody>


                    <c:forEach items="${results}" var="DP">
                        <tr>
                            <td class="center">
                                ${DP.planName}
                            </td>
                            <td class="center">
                                ${DP.id.channel.channelName}    
                            </td>
                            <td class="center">
                                ${DP.id.dcmLevel.levelName}    
                            </td>
                            <td class="center">
                                ${DP.paymentType.typeName}    
                            </td>
                            <td class="center">
                                ${DP.id.dcmPaymentLevel.namePaymentLevel}    
                            </td>
                            <td class="center">
                                ${DP.commissionCategory.categoryName}    
                            </td>
                            <td class="center">
                                ${DP.id.DPMonth.monthName}
                            </td>
                            <td class="center">
                                ${DP.id.DPYear}    
                            </td>
                            <td class="center">
                                ${DP.planStatus.statusName}    
                            </td>
                            <td class="center">
                                <input type="button"   value ="View Factors" onclick ="factors(${DP.dpId})"/>  
                            </td>
                            <td class="center">
                                <input type="button" id="approvebutton"  value ="Approve" onclick ="approve(${DP.dpId})"/>  
                            </td>
                            <td class="center">
                                <input type="button"   value ="Refuse" onclick ="refuse(${DP.dpId})"/>  
                            </td>
                            <td class="center">
                                <c:if test="${DP.planStatus.statusId =='2'}">
                                    <input type="button"  value ="Delete" onclick ="deleteDP(${DP.dpId})"/>               
                                </c:if>
                                <c:if test="${DP.planStatus.statusId !='2'}">
                                    <input type="button"  disabled="disabled" value ="Delete"/>  
                                </c:if>
                            </td>

                    <input type="hidden" id="id${DP.dpId}" value="${DP.dpId}"/>

                </tr>
            </c:forEach>     
        </tbody>
    </c:if>
</table>
<h3 align="center" style="color: RED">${MSG}</h3>
<c:url var="approve" value="/dp/managment/fapprove.htm" />
<c:url var="disable" value="/dp/managment/refuse.htm" />
<c:url var="disable" value="/dp/managment/deleteDP.htm" />
<c:url var="factors" value="/dp/managment/viewfactors.htm" />
<div id="commentdiv" align="center">
    <form id="edit" action="${edit}" >
        <input type="hidden" name="hiddenDpId" id="hiddenDpId"/>


        <h3>Comment </h3>
        <br>
        <textarea rows="10" cols="50" name="comment" id="comment"></textarea>

    </form>
</div>
<c:if test="${empty results}">
    <script>
        document.getElementById('commentdiv').style.display="none";
    </script>
</c:if>

<script>
    function refuse (id)
    {
            
            
        if(document.getElementById('comment').value=="")
        {
            alert('you must insert comment')
            return;
        }
        document.getElementById('hiddenDpId').value=id;
           
        document.getElementById('edit').action='${refuse}';
        document.getElementById('edit').submit();
            
    }
    function deleteDP (id)
    {
            
        document.getElementById('hiddenDpId').value=id;
           
        document.getElementById('edit').action='${deleteDP}';
        document.getElementById('edit').submit();
            
    }
    function approve (id)
    {
            
        document.getElementById('hiddenDpId').value=id;
        document.getElementById('approvebutton').disabled="disabled";
            
        document.getElementById('edit').action='${approve}';
        document.getElementById('edit').submit();
    }
    function factors (id)
    {
        document.getElementById('hiddenDpId').value=id;
        document.getElementById('edit').action='${factors}';
        document.getElementById('edit').submit();
    }
</script>

</body>
</html>

<%-- 
    Document   : DrivingPlanMangment
    Created on : Sep 7, 2011, 8:05:11 PM
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
        <title>Driving Plan Management</title>
    </head>
    <body id="dt_example">
        <br>
        <h3 align="center" style="color: Black" >Driving Plan Management</h3>
        </br>
        </br>

        <c:url var="search" value="/dp/managment/search.htm" />

        <form:form modelAttribute="drivingPlan" id='mainForm' name="mainForm" action="${search}" method="GET">
            <form:hidden path="dpId"/>
            <table border="1" style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px" align="center">
                <tr class="TableTextNote">
                    <td class="TableTextNote">
                        Name : 
                    </td>   

                    <td class="TableTextNote">
                        <form:input path="planName"/>                        
                    </td>     


                </tr>               

                <tr class="TableTextNote">   
                    <td class="TableTextNote">
                        Year :  
                    </td>
                    <td class="TableTextNote">
                        <form:select path="id.DPYear">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${years}"/>
                        </form:select>     
                    </td>
                </tr>

                <tr class="TableTextNote">

                    <td class="TableTextNote">
                        Month :  
                    </td>
                    <td class="TableTextNote">
                        <form:select path="id.DPMonth.index">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${months}" itemValue="index" itemLabel="monthName"/>
                        </form:select>
                    </td>  
                </tr>
                <tr class="TableTextNote">
                    <td class="TableTextNote">
                        DCM Level :  
                    </td>
                    <td class="TableTextNote">
                        <form:select path="id.dcmLevel.idLevel">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${dcm_level_list}" itemValue="idLevel" itemLabel="levelName" />
                        </form:select>

                    </td>
                </tr>
                <tr class="TableTextNote">
                    <td class="TableTextNote">
                        Channel :  
                    </td>
                    <td class="TableTextNote">
                        <form:select path="id.channel.channelId">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${Channels_list}" itemValue="channelId" itemLabel="channelName" />
                        </form:select>

                    </td>
                </tr>     

                <tr>

                    <td class="TableTextNote">
                        DCM Payment Level :  

                    </td>
                    <td class="TableTextNote">
                        <form:select path="id.dcmPaymentLevel.idPaymentLevel">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${dcm_payment_level_list}" itemValue="idPaymentLevel" itemLabel="namePaymentLevel" />
                        </form:select>



                    </td>
                </tr>     
                <tr>
                    <td class="TableTextNote">
                        DrivingPlan Status :  
                    </td>
                    <td class="TableTextNote">
                        <form:select path="planStatus.statusId">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${dp_status_list}" itemValue="statusId" itemLabel="statusName" />
                        </form:select>



                    </td>
                </tr>
                <tr>
                    <td class="TableTextNote">
                        DrivingPlan Payment Type Status :  
                    </td>
                    <td class="TableTextNote">
                        <form:select path="paymentType.typeId">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${dp_payment_type_list}" itemValue="typeId" itemLabel="typeName" />
                        </form:select>



                    </td>
                </tr>
                 <tr>
                    <td class="TableTextNote">
                        DrivingPlan Commission Category:  
                    </td>
                    <td class="TableTextNote">
                        <form:select path="commissionCategory.categoryId">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${dp_commission_category_list}" itemValue="categoryId" itemLabel="categoryName" />
                        </form:select>

                    </td>
                </tr>
                <tr class="TableTextNote">
                    <td align="center">
                        <input  type="button" value="Search" onclick="search();">
                    </td>
                    <td align="center">
                        <input  type="button" value="Reset" onclick="reset();">
                    </td>
                </tr>
            </table>

        </form:form>



        <div id="container">			
            <div id="demo">

                <br>

                <table align="center" cellpadding="0" cellspacing="0" border="0"  class="display"  id="example">                    
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
                            <th >Copy</th>
                            <th>Approve</th>
                            <th >Edit</th>
                            <th >Delete</th>
                            <th >Manage Factors</th>
                            <th >View History</th>
                            
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

                                        <input type="button"  value ="Copy" onclick ="copy(${DP.dpId})"/>  


                                    </td>
                                    <td class="center">
                                        <c:if test="${DP.planStatus.statusId =='1' || DP.planStatus.statusId =='4'}">
                                        <input type="button"   value ="Approve" onclick ="approve(${DP.dpId})"/>  
                                        </c:if>
                                        <c:if test="${DP.planStatus.statusId == '3' || DP.planStatus.statusId == '2'}">
                                         <input type="button"   disabled="disabled" value ="Approve" onclick ="approve(${DP.dpId})"/>     
                                        </c:if>

                                    </td>
                                    <td class="center">
                                        <c:if test="${DP.planStatus.statusId=='1' || DP.planStatus.statusId =='4'}">
                                            <input type="button"  value ="Edit" onclick ="edit(${DP.dpId})"/>  
                                        </c:if>        
                                        <c:if test="${DP.planStatus.statusId =='3' || DP.planStatus.statusId =='2'}">
                                            <input type="button" disabled="disabled" value ="Edit" onclick ="edit(${DP.dpId})"/>    
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${DP.planStatus.statusId =='1' || DP.planStatus.statusId =='4' }">
                                            <input type="button"  value ="Delete" onclick ="deleteDP(${DP.dpId})"/>               
                                        </c:if>
                                        <c:if test="${DP.planStatus.statusId =='3' || DP.planStatus.statusId =='2' || DP.planStatus.statusId =='5'}">
                                            <input type="button"  disabled="disabled" value ="Delete"/>  
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${DP.planStatus.statusId =='1' || DP.planStatus.statusId =='4' }">
                                            <input type="button"  value ="Manage Factors" onclick ="managefactor(${DP.dpId})"/>               
                                        </c:if>
                                        <c:if test="${DP.planStatus.statusId =='3' || DP.planStatus.statusId =='2'}">
                                            <input type="button"   value ="view Factors" onclick ="viewfactors(${DP.dpId})"/>  
                                        </c:if>
                                        <c:if test="${DP.planStatus.statusId =='5'}">
                                            <input type="button"   disabled="disabled" value ="view Factors" />  
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${DP.planStatus.statusId =='2' || DP.planStatus.statusId =='4' }">
                                            <input type="button"  value ="View History" onclick ="history(${DP.dpId})"/>               
                                        </c:if>
                                        <c:if test="${DP.planStatus.statusId =='1' || DP.planStatus.statusId =='3' || DP.planStatus.statusId =='5'}">
                                            <input type="button"  disabled="disabled" value ="View History"/>  
                                        </c:if>
                                    </td>
                            <input type="hidden" id="id${DP.dpId}" value="${DP.dpId}"/>
                                   
                            </tr>
                        </c:forEach>     
                        </tbody>
                    </c:if>
                </table>

            </div>
            <div class="spacer"></div>
        </div>
        <h3 align="center" style="color: RED">${MSG}</h3>

        <c:url var="save" value="/dp/managment/DrivingPlanNew.htm" />
        <c:url var="approve" value="/dp/managment/approve.htm" />
        <c:url var="copy" value="/dp/managment/copy.htm" />
        <c:url var="delete" value="/dp/managment/delete.htm" />
        <c:url var="factor" value="/dp/managment/factor.htm" />
        <c:url var="history" value="/dp/managment/history.htm" />
        <c:url var="viewfactors" value="/dp/managment/viewfactors.htm" />
        
        <form action="${save}">
            <p align="center">
                <input   type="submit" value="Add New DrivingPlan" >
            </p>
        </form>

        <form id="edit" action="${edit}">
            <input type="hidden" name="hiddenDpId" id="hiddenDpId"/>
            <input type="hidden" name="editflag" id="editflag"/>
        </form>
          



    </body>
    <script>
        function search ()
        {
            
            document.getElementById('mainForm').action='${search}';
            document.getElementById('mainForm').submit();
    
        }
       
        function save ()
        {
            
            document.getElementById('mainForm').action='${save}';
            document.getElementById('mainForm').submit();
    
        }
        function copy (id)
        {
            document.getElementById('hiddenDpId').value=id;
            document.getElementById('editflag').value=0;
            document.getElementById('edit').action='${copy}';
            document.getElementById('edit').submit();
            
        }
        function approve (id)
        {
            document.getElementById('hiddenDpId').value=id;
            document.getElementById('editflag').value=0;
            document.getElementById('edit').action='${approve}';
            document.getElementById('edit').submit();
        }
        function history (id)
        {
            document.getElementById('hiddenDpId').value=id;
            document.getElementById('editflag').value=0;
            document.getElementById('edit').action='${history}';
            document.getElementById('edit').submit();
        }
        function edit (id)
        {
            document.getElementById('hiddenDpId').value=id;
            document.getElementById('editflag').value=1;
            document.getElementById('edit').action='${copy}';
            document.getElementById('edit').submit();
            
        }
        function reset ()
        {
            document.getElementById('planName').value=""
            document.getElementById('id.DPYear').value=""
            document.getElementById('id.DPMonth').value=""
            document.getElementById('id.dcmLevel.idLevel').value=""
            document.getElementById('id.channel.channelId').value=""
            document.getElementById('id.dcmPaymentLevel.idPaymentLevel').value=""
            document.getElementById('searchResult').style.display="none";
            
        }
        function deleteDP (id)
        {
            document.getElementById('hiddenDpId').value=id;
            document.getElementById('editflag').value=0;
            document.getElementById('edit').action='${delete}';
            document.getElementById('edit').submit()
            
            
        }
         function managefactor (id)
        {
            document.getElementById('hiddenDpId').value=id;
            document.getElementById('editflag').value=0;
            document.getElementById('edit').action='${factor}';
            document.getElementById('edit').submit()
            
            
        }
         function viewfactors (id)
        {
            document.getElementById('hiddenDpId').value=id;
            document.getElementById('edit').action='${viewfactors}';
            document.getElementById('edit').submit();
        }
    </script>
</html>

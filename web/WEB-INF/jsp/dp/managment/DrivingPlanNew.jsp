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
        <title>Driving Plan Management</title>
    </head>
    <body>
        <h2 align="center">Add New Driving Plan</h2>
        </br>
        </br>


        <c:url var="save" value="/dp/managment/add.htm" />
        <form:form modelAttribute="drivingPlan" id='mainForm' name="mainForm" action="${save}" method="GET">
            <form:hidden path="dpId"/>
            <table border="1" align="center">
                <tr>
                    <td>
                        Name :  
                    </td>
                    <td>
                        <form:input path="planName"/>                        
                        <font color="red"><form:errors path="planName" /></font>
                    </td>
                </tr>
                <tr>
                    <td>
                        Year :  
                    </td>
                    <td>
                        <form:select path="id.DPYear">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${years}"/>
                        </form:select>     

                        <font color="red"><form:errors path="id.DPYear" /></font>
                    </td>
                </tr>
                <tr>
                    <td>
                        Month :  
                    </td>
                    <td>
                        <form:select path="id.DPMonth.index">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${months}" itemValue="index" itemLabel="monthName"/>
                        </form:select>
                        <font color="red"><form:errors path="id.DPMonth" /></font>
                    </td>

                </tr>
                <tr>
                    <td>
                        DCM Level :  
                    </td>
                    <td>
                        <form:select path="id.dcmLevel.idLevel">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${dcm_level_list}" itemValue="idLevel" itemLabel="levelName" />
                        </form:select>
                        <font color="red"><form:errors path="id.dcmLevel.idLevel" /></font>
                    </td>
                </tr>
                <tr>
                    <td>
                        Channel :  
                    </td>
                    <td>
                        <form:select path="id.channel.channelId">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${Channels_list}" itemValue="channelId" itemLabel="channelName" />
                        </form:select>
                        <font color="red"><form:errors path="id.channel.channelId" /></font>
                    </td>

                </tr>
                <tr>
                    <td>
                        DCM Payment Level :  
                    </td>
                    <td>

                        <form:select path="id.dcmPaymentLevel.idPaymentLevel">
                            <form:option value="0" label="--Select--"/>
                            <form:options items="${dcm_payment_level_list}" itemValue="idPaymentLevel" itemLabel="namePaymentLevel" />
                        </form:select>
                        <font color="red"><form:errors path="id.dcmPaymentLevel.idPaymentLevel" /></font>


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
                        <font color="red"><form:errors path="paymentType.typeId" /></font>


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
                        <font color="red"><form:errors path="commissionCategory.categoryId" /></font>


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
            <input TYPE="hidden" name="CopyDpId" id="CopyDpId" value="${CopyDpId}">
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
</script>
</html>

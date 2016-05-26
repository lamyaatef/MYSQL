<%-- 
    Document   : addEditDCMRegion
    Created on : Dec 19, 2011, 12:31:35 PM
    Author     : mabdelaal
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:out value="${title}"/></title>
    </head>
    <body>
         <center>
<h3><c:out value="${title}"/></h3>

<c:url var="saveDCMUrl" value="/zone/saveDCM.htm" />
<form:form modelAttribute="DcmRegionModel" id="saveDcmform" name="saveDcmform" method="GET" action="${saveDCMUrl}">    
    <input type="hidden" name="isNewMode" id="isNewMode" value="${isNewMode}"/>    
    <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
           cellSpacing=2 cellPadding=1 width="746" border=1>
        <TR class="TableTextNote">
            <TD class="TableTextNote" style="WIDTH: 20%;" ><form:label path="dcmId">DCM Name:</form:label>    </TD>
            <TD class="TableTextNote" style="WIDTH: 30%;">
                <c:if test="${isNewMode=='true'}">
                    <form:hidden id="dcmId" path="dcmId"/>                    
                </c:if>
                <form:select cssStyle="WIDTH: 100%;" path="dcmId" disabled="${isNewMode}" multiple="false">
                <form:option value="0" label="--- Select ---" />
                <form:options items="${dcmValues}" itemLabel="dcmName" itemValue="dcmId"/>
            </form:select>
            </TD>            
            <TD class="TableTextNote" style="WIDTH: 50%;"><font color="red"><c:if test="${msgDCM!=null}">${msgDCM}</c:if> </font></TD>            
        
        </TR>       
        <TR class="TableTextNote">
	<TD class="TableTextNote" style="WIDTH: 20%;"><form:label path="selectedRegionsId">Regions:</form:label>    </TD>
        <TD class="TableTextNote" style="WIDTH: 30%;">
        <form:select id="selectRegions" cssStyle="WIDTH: 100%;" path="selectedRegionsId" multiple="true" >		                
            <c:forEach items="${regionsValues}" var="regionModel">                
                <form:option selected="${regionModel.strIsSelected}" label="${regionModel.regionName}" value="${regionModel.regionId}"/>
            </c:forEach>
                
                
	</form:select>        
        </TD>
        <TD class="TableTextNote" style="WIDTH: 50%;"><font color="red"><c:if test="${msgRegion!=null}">${msgRegion}</c:if></font></TD>            
	</TR>
        <TR class="TableTextNote">
            <TD  class="TableTextNote" colspan="3" align="center" >
                <input type="submit" value="Save Name" /></TD>
        </TR>
    </table>
</form:form>
</center>
    </body>
</html>

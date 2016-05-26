<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/css/Template1.css"/>
        <title>Factor</title>
    </head>
    <body>
    <center>
        <h3><c:out value="${dp_name}"/></h3>       
        <br>
          <form:form id="conFactorForm" name="conFactorForm" method="GET" >
              <h3>Constant Factors Management</h3>
            <table style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
                   cellSpacing=2 cellPadding=1 width="746" border=1>
                <tr>                    
                    <th class="TableTextNote">Factor Name</th>                                        
                    <th class="TableTextNote">Value</th>                                        
                </tr>
                
                <c:if test="${!empty factor_list_con}">                                    
                    <c:forEach items="${factor_list_con}" var="factor">                                                
                        <tr class="TableTextNote">				                            
                            <td class="TableTextNote"><c:out value="${factor.factorName.factorName}"/></td>                                                        
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).factorValue}"/></td>                            
                            
                        </tr>                        
                    </c:forEach>
                </c:if>                
            </table>    
        </form:form>
          <form:form id="monFactorForm" name="monFactorForm" method="GET" >
              <h3>Month Factors Management</h3>
            <table style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
                   cellSpacing=2 cellPadding=1 width="746" border=1>
                <tr>                                        
                    <th class="TableTextNote">Factor Name</th>                                        
                    <th class="TableTextNote">Value</th>
                    <th class="TableTextNote">Backward</th>
                    <th class="TableTextNote">Forward</th>                    
                    		
                </tr>
                
                <c:if test="${!empty factor_list_month}">                                    
                    <c:forEach items="${factor_list_month}" var="factor">                                                
                        <tr class="TableTextNote">				                            
                            <td class="TableTextNote"><c:out value="${factor.factorName.factorName}"/></td>                                                        
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).factorValue}"/></td>                            
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).monthFactorBackward}"/></td>                            
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).monthFactorForward}"/></td>                                                
                        </tr>                        
                    </c:forEach>
                </c:if>
                </tr>
            </table>    
        </form:form>
          <form:form id="dayFactorForm" name="dayFactorForm" method="GET" >
              <h3>Day Factors Management</h3>
            <table style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
                   cellSpacing=2 cellPadding=1 width="746" border=1>
                <tr>                    
                    <th rowspan="2" class="TableTextNote">Factor Name</th>                                        
                    <th colspan="3" class="TableTextNote">Factor Value(s)</th>                                                                              			
                </tr>
                <tr>
                     <th class="TableTextNote">Value</th>
                     <th class="TableTextNote">From</th>
                     <th class="TableTextNote">To</th>
                </tr>
                <c:if test="${!empty factor_list_day}">                                    
                    <c:forEach items="${factor_list_day}" var="factor"> 
                        <c:url value="${fn:length(factor.factorValues)}" var="dayFactorLen" />
                        <c:if test="${dayFactorLen==1}">
                        <tr class="TableTextNote">				                            
                            <td class="TableTextNote"><c:out value="${factor.factorName.factorName}"/></td>                                                        
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).factorValue}"/></td>
                            <td class="TableTextNote">${fn:replace(factor.factorValues.get(0).factorRangeFrom, " 00:00:00.0", "")}</td>
                            <td class="TableTextNote">${fn:replace(factor.factorValues.get(0).factorRangeTo, " 00:00:00.0", "")}</td>                            
                        </tr>
                        </c:if>
                        <c:if test="${dayFactorLen>1}">
                        <tr class="TableTextNote">				                            
                            <td rowspan="<c:out value="${dayFactorLen}"/>" class="TableTextNote"><c:out value="${factor.factorName.factorName}"/></td>                                                        
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).factorValue}"/></td>
                            <td class="TableTextNote">${fn:replace(factor.factorValues.get(0).factorRangeFrom, " 00:00:00.0", "")}</td>
                            <td class="TableTextNote">${fn:replace(factor.factorValues.get(0).factorRangeTo, " 00:00:00.0", "")}</td>                                                       
                        </tr>                        
                        
                        <c:forEach items="${factor.factorValues}" var="factorValue"> 
                            <c:if  test="${factor.factorValues.get(0).factorValueId != factorValue.factorValueId}" >
                         <tr class="TableTextNote">
                          <td class="TableTextNote"><c:out value="${factorValue.factorValue}"/></td>
                          <td class="TableTextNote">${fn:replace(factorValue.factorRangeFrom, " 00:00:00.0", "")}</td>
                            <td class="TableTextNote">${fn:replace(factorValue.factorRangeTo, " 00:00:00.0", "")}</td>                                                      
                        </tr>
                        </c:if>
                        </c:forEach>
                        
                        </c:if>
                        
                    </c:forEach>
                </c:if>
                        
            </table>    
        </form:form>
              <br>
                        <input type="button" value="Back" onClick="resetandBackforms();" />
    </center>
</body>
<script>
    function resetandBackforms(){        
        document.conFactorForm.reset();
        document.monFactorForm.reset();
        document.dayFactorForm.reset();
        history.go(-1);
    }
</script>

</html>
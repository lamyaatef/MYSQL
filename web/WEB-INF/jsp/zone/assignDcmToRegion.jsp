<%-- 
    Document   : assign_dcm_to_region
    Created on : Dec 18, 2011, 11:44:52 AM
    Author     : mabdelaal
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/css/Template1.css"/>
        <title>DCM Regions Management</title>
    </head>
    <body>
    <center>
        <h3>DCM Regions Management</h3>


        <c:url var="addeditDcmUrl" value="/zone/addEditDCMRegion.htm" />        
        <c:url var="deleteDcmUrl" value="/zone/deletedcm.htm" />            
          <form:form id="dcmRegForm" name="dcmRegForm" method="GET" action="${deleteDcmUrl}">
            <table style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
                   cellSpacing=2 cellPadding=1 width="746" border=1>
                <tr>
                    <th class="TableTextNote">DCM Name</th>
                    <th class="TableTextNote">Region Name</th>
                    <th class="TableTextNote">Edit</th>			
                    <th class="TableTextNote">Delete</th>			
                </tr>
                <c:if test="${!empty groupOfDCMWithRegions}">
                    <c:forEach items="${groupOfDCMWithRegions}" var="dcmRegionModel">  
                        <c:url value="${fn:length(dcmRegionModel.value)}" var="regionSize" />                        
                        <tr class="TableTextNote">				                            
                            <td class="TableTextNote" rowspan="${regionSize}">${dcmRegionModel.value.get(0).genDcm.dcmName}</td>                                              
                            <c:if test="${!empty dcmRegionModel.value}">                                
                                    <td class="TableTextNote">${dcmRegionModel.value.get(0).dcmRegion.regionName}</td>                                
                            </c:if>                            
                            <td align="center" class="TableTextNote" rowspan="${regionSize}" ><input type="submit" value="Edit" onclick="document.dcmRegForm.dcmIdHidden.value=${dcmRegionModel.key};
                                document.dcmRegForm.action='${addeditDcmUrl}';"  /></td>				
                            <td align="center" class="TableTextNote" rowspan="${regionSize}" ><input type="submit" value="Delete" onclick="document.dcmRegForm.dcmIdHidden.value=${dcmRegionModel.key};
                                document.dcmRegForm.action='${deleteDcmUrl}';" /></td>				
                        </tr>
                        <c:if test="${regionSize!=1 && regionSize!=0}">
                        
                        <c:if test="${!empty dcmRegionModel.value}">                            
                                <c:forEach items="${dcmRegionModel.value}" var="regionModel">
                                    <c:if test="${dcmRegionModel.value.get(0)!=regionModel}">
                                    <tr class="TableTextNote">
                                    <td class="TableTextNote">${regionModel.dcmRegion.regionName}</td>
                                    </tr>   
                                    </c:if>
                                </c:forEach>
                        
                        </c:if>
                        
                                
                            </c:if>          
                    </c:forEach>
                </c:if>
                <tr class="TableTextNote">                    
                    <td class="TableTextNote" colspan="4" align="center">
                        <input type="submit" value="Add DCM" onclick="document.dcmRegForm.action='${addeditDcmUrl}';"/>                                            
                    </td>

                </tr>                
            </table> 
                    <input type="hidden" name="dcmIdHidden" id="dcmIdHidden" value=""/>
        </form:form>
    </center>
</body>
<script>
    
    
</script>

</html>
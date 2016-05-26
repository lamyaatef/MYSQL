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


        
        <c:url var="editConFactorUrl" value="/dp/factor/editConFactor.htm" />        
        <c:url var="editMonFactorUrl" value="/dp/factor/editMonFactor.htm" />        
        <c:url var="editDayFactorUrl" value="/dp/factor/editDayFactor.htm" />        
        <c:url var="addConFactorUrl" value="/dp/factor/saveConFactor.htm" />        
        <c:url var="addMonFactorUrl" value="/dp/factor/saveMonFactor.htm" />        
        <c:url var="addDayFactorUrl" value="/dp/factor/saveDayFactor.htm" />        
        <c:url var="deleteFactorUrl" value="/dp/factor/del.htm" />            
          <form:form id="conFactorForm" name="conFactorForm" method="GET" action="${editFactorUrl}">
              <h3>Constant Factors Management</h3>
            <table style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
                   cellSpacing=2 cellPadding=1 width="746" border=1>
                <tr>
                    <th class="TableTextNote"  align="center"><input type="checkbox" id="selectDeselectAll" name="selectDeselectAll"  <c:if test="${!empty factor_list_con}">onclick="checkAll('conFactorForm',document.conFactorForm.list,this);uncheckAll('conFactorForm',document.conFactorForm.list,this);"</c:if> /></th>                    
                    <th class="TableTextNote">Factor Name</th>                                        
                    <th class="TableTextNote">Value</th>                    
                    <th class="TableTextNote">Edit</th>			
                </tr>
                
                <c:if test="${!empty factor_list_con}">                                    
                    <c:forEach items="${factor_list_con}" var="factor">                                                
                        <tr class="TableTextNote">				
                            <td  align="center" class="TableTextNote"><input type="checkbox" id="list" name="list" value="${factor.factorId}" onclick="deleteFactorId('conFactorForm','${factor.factorId}',this)" /></td>
                            <td class="TableTextNote"><c:out value="${factor.factorName.factorName}"/></td>                                                        
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).factorValue}"/></td>                            
                            <td align="center" class="TableTextNote"><input type="submit" value="Edit" onclick="changeAction('conFactorForm','${editConFactorUrl}',1,<c:out value="${dp_name_id}"/>);setFactorConId('conFactorForm','<c:out value="${factor.factorId}"/>','<c:out value="${factor.factorName.factorNameId}"/>','<c:out value="${factor.factorValues.get(0).factorValue}"/>','<c:out value="${factor.factorValues.get(0).factorValueId}"/>');"/></td>				
                        </tr>                        
                    </c:forEach>
                </c:if>
                <tr class="TableTextNote">                    
                    <td class="TableTextNote" colspan="9" align="center">
                        <input type="submit" value="Add Factor" onclick="changeAction('conFactorForm','${addConFactorUrl}',1,<c:out value="${dp_name_id}"/>)"/>                    
                        <input type="button" value="Delete Factors" onclick="checkDeleteAction('conFactorForm','${deleteFactorUrl}',document.conFactorForm.list,1,<c:out value="${dp_name_id}"/>);"/>
                    </td>

                </tr>
            </table>    

            <input type="hidden" name="factorType" id="factorType" value="" />            
            <input type="hidden" name="factorid" id="factorid" value="" />            
            <input type="hidden" name="factornameid" id="factornameid" value="" />            
            <input type="hidden" name="factorval" id="factorval" value="" />            
            <input type="hidden" name="factorvalid" id="factorvalid" value="" />            
            <input type="hidden" name="dpid" id="dpid" value="" />            
            <input type="hidden" name="deleteIds" id="deleteIds" value="" />
        </form:form>
          <form:form id="monFactorForm" name="monFactorForm" method="GET" action="${editFactorUrl}">
              <h3>Month Factors Management</h3>
            <table style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
                   cellSpacing=2 cellPadding=1 width="746" border=1>
                <tr>
                    <th class="TableTextNote"  align="center"><input type="checkbox" id="selectDeselectAll" name="selectDeselectAll"  <c:if test="${!empty factor_list_month}">onclick="checkAll('monFactorForm',document.monFactorForm.list,this);uncheckAll('monFactorForm',document.monFactorForm.list,this);"</c:if> /></th>                    
                    <th class="TableTextNote">Factor Name</th>                                        
                    <th class="TableTextNote">Value</th>
                    <th class="TableTextNote">Backward</th>
                    <th class="TableTextNote">Forward</th>                    
                    <th class="TableTextNote">Edit</th>			
                </tr>
                
                <c:if test="${!empty factor_list_month}">                                    
                    <c:forEach items="${factor_list_month}" var="factor">                                                
                        <tr class="TableTextNote">				
                            <td  align="center" class="TableTextNote"><input type="checkbox" id="list" name="list" value="${factor.factorId}" onclick="deleteFactorId('monFactorForm','${factor.factorId}',this)" /></td>
                            <td class="TableTextNote"><c:out value="${factor.factorName.factorName}"/></td>                                                        
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).factorValue}"/></td>                            
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).monthFactorBackward}"/></td>                            
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).monthFactorForward}"/></td>                            
                            <td align="center" class="TableTextNote"><input type="submit" value="Edit" onclick=";changeAction('monFactorForm','${editMonFactorUrl}',3,<c:out value="${dp_name_id}"/>);setFactorMonId('monFactorForm','<c:out value="${factor.factorId}"/>','<c:out value="${factor.factorName.factorNameId}"/>','<c:out value="${factor.factorValues.get(0).factorValue}"/>','<c:out value="${factor.factorValues.get(0).factorValueId}"/>','<c:out value="${factor.factorValues.get(0).monthFactorBackward}"/>','<c:out value="${factor.factorValues.get(0).monthFactorForward}"/>');"/></td>				
                        </tr>                        
                    </c:forEach>
                </c:if>
                <tr class="TableTextNote">                    
                    <td class="TableTextNote" colspan="9" align="center">
                        <input type="submit" value="Add Factor" onclick="changeAction('monFactorForm','${addMonFactorUrl}',3,<c:out value="${dp_name_id}"/>)"/>                    
                        <input type="button" value="Delete Factors" onclick="checkDeleteAction('monFactorForm','${deleteFactorUrl}',document.monFactorForm.list,3,<c:out value="${dp_name_id}"/>);"/>
                    </td>

                </tr>
            </table>    

            <input type="hidden" name="factorType" id="factorType" value="" />
            <input type="hidden" name="factorid" id="factorid" value="" />            
            <input type="hidden" name="factornameid" id="factornameid" value="" />            
            <input type="hidden" name="factorval" id="factorval" value="" />            
            <input type="hidden" name="factorvalid" id="factorvalid" value="" />                    
            <input type="hidden" name="factormonbck" id="factormonbck" value="" />                    
            <input type="hidden" name="factormonfwd" id="factormonfwd" value="" />                    
            <input type="hidden" name="dpid" id="dpid" value="" />            
            <input type="hidden" name="deleteIds" id="deleteIds" value="" />
        </form:form>
          <form:form id="dayFactorForm" name="dayFactorForm" method="GET" action="${editFactorUrl}">
              <h3>Day Factors Management</h3>
            <table style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
                   cellSpacing=2 cellPadding=1 width="746" border=1>
                <tr>
                    <th rowspan="2" class="TableTextNote"  align="center"><input type="checkbox" id="selectDeselectAll" name="selectDeselectAll"  <c:if test="${!empty factor_list_day}">onclick="checkAll('dayFactorForm',document.dayFactorForm.list,this);uncheckAll('dayFactorForm',document.dayFactorForm.list,this);"</c:if> /></th>                    
                    <th rowspan="2" class="TableTextNote">Factor Name</th>                                        
                    <th colspan="3" class="TableTextNote">Factor Value(s)</th>                                                          
                    <th rowspan="2"  class="TableTextNote">Edit</th>			
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
                            <td  align="center" class="TableTextNote"><input type="checkbox" id="list" name="list" value="${factor.factorId}" onclick="deleteFactorId('dayFactorForm','${factor.factorId}',this)" /></td>
                            <td class="TableTextNote"><c:out value="${factor.factorName.factorName}"/></td>                                                        
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).factorValue}"/></td>
                            
                            <td class="TableTextNote">${fn:replace(factor.factorValues.get(0).factorRangeFrom, " 00:00:00.0", "")}</td>
                            <td class="TableTextNote">${fn:replace(factor.factorValues.get(0).factorRangeTo, " 00:00:00.0", "")}</td>                            
                            
                            <td align="center" class="TableTextNote"><input type="submit" value="Edit"  onclick="changeAction('dayFactorForm','${editDayFactorUrl}',2,<c:out value="${dp_name_id}"/>);setFactorDayId('dayFactorForm','<c:out value="${factor.factorId}"/>','<c:out value="${factor.factorName.factorNameId}"/>');"/></td>				
                        </tr>
                        </c:if>
                        <c:if test="${dayFactorLen>1}">
                        <tr class="TableTextNote">				
                            <td rowspan="<c:out value="${dayFactorLen}"/>" align="center" class="TableTextNote"><input type="checkbox" id="list" name="list" value="${factor.factorId}" onclick="deleteFactorId('dayFactorForm','${factor.factorId}',this)" /></td>
                            <td rowspan="<c:out value="${dayFactorLen}"/>" class="TableTextNote"><c:out value="${factor.factorName.factorName}"/></td>                                                        
                            <td class="TableTextNote"><c:out value="${factor.factorValues.get(0).factorValue}"/></td>
                            <td class="TableTextNote">${fn:replace(factor.factorValues.get(0).factorRangeFrom, " 00:00:00.0", "")}</td>
                            <td class="TableTextNote">${fn:replace(factor.factorValues.get(0).factorRangeTo, " 00:00:00.0", "")}</td>                            
                            
                            <td rowspan="<c:out value="${dayFactorLen}"/>" align="center" class="TableTextNote"><input type="submit" value="Edit" onclick="changeAction('dayFactorForm','${editDayFactorUrl}',2,<c:out value="${dp_name_id}"/>);setFactorDayId('dayFactorForm','<c:out value="${factor.factorId}"/>','<c:out value="${factor.factorName.factorNameId}"/>');"/></td>				
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
                <tr class="TableTextNote">                    
                    <td class="TableTextNote" colspan="9" align="center">
                        <input type="submit" value="Add Factor" onclick="changeAction('dayFactorForm','${addDayFactorUrl}',2,<c:out value="${dp_name_id}"/>)"/>                    
                        <input type="button" value="Delete Factors" onclick="checkDeleteAction('dayFactorForm','${deleteFactorUrl}',document.dayFactorForm.list,2,<c:out value="${dp_name_id}"/>);"/>
                    </td>

                </tr>
            </table>    

            <input type="hidden" name="factorType" id="factorType" value="" />
            <input type="hidden" name="factorid" id="factorid" value="" /> 
            <input type="hidden" name="factornameid" id="factornameid" value="" />            
            <input type="hidden" name="dpid" id="dpid" value="" />            
            <input type="hidden" name="deleteIds" id="deleteIds" value="" />
        </form:form>
            <br>
                        <input type="button" value="Back" onClick="resetandBackforms();" />
    </center>
</body>
<script>
        
    function deleteFactorId(fromName,id,field){
        
        var deleteIds = document.getElementById(fromName).deleteIds.value;
        if (field.checked){
            deleteIds+=id+',';
        }
        else{
            deleteIds =deleteIds.replace(id+',', "");
        }        
        document.getElementById(fromName).deleteIds.value = deleteIds ;
    }   
    function checkAll(fromName,field,mainCheckBox)
    {        
        if (mainCheckBox.checked)
        {   
            var deleteIds = "";
            if (!field.checked)
            {                
                field.checked=true;
                if (field.value!=undefined){
                deleteIds=field.value+',';    
                }
            }            
            for (i = 0; i < field.length; i++){            
                field[i].checked = true ;
                deleteIds += field[i].value+',';
            }
            document.getElementById(fromName).deleteIds.value=deleteIds;                    
        }
    }    
    function uncheckAll(fromName,field,mainCheckBox)
    {
        if (!mainCheckBox.checked)
        {   
            if (field.checked)field.checked=false;
                
            for (i = 0; i < field.length; i++){
                field[i].checked = false ;
            }            
            document.getElementById(fromName).deleteIds.value="";
        }
    }
    function changeAction(fromName,action,factorType,dpid){            
        document.getElementById(fromName).action=action;
        document.getElementById(fromName).factorType.value = factorType;            
        document.getElementById(fromName).dpid.value = dpid;                    
    }
    function checkDeleteAction(fromName,action,field,factorType,dpid){            
        if (document.getElementById(fromName).deleteIds.value=="")
        {
            alert('Please select at least one factor.');
            return false;
        }            
        else
        {   
            changeAction(fromName,action,factorType,dpid);
            document.getElementById(fromName).submit();
                
        }
    
    }
    function setFactorConId(fromName,id,name,val,valid){
        document.getElementById(fromName).factorid.value = id;
        document.getElementById(fromName).factornameid.value = name;
        document.getElementById(fromName).factorval.value = val;
        document.getElementById(fromName).factorvalid.value = valid;
    }
    function setFactorMonId(fromName,id,name,val,valid,monbak,monfwd){
        document.getElementById(fromName).factorid.value = id;
        document.getElementById(fromName).factornameid.value = name;
        document.getElementById(fromName).factorval.value = val;
        document.getElementById(fromName).factorvalid.value = valid;
        document.getElementById(fromName).factormonbck.value = monbak;
        document.getElementById(fromName).factormonfwd.value = monfwd;
    }
    function setFactorDayId(fromName,id,name){
        document.getElementById(fromName).factorid.value = id;
        document.getElementById(fromName).factornameid.value = name;                        
    }
    function resetandBackforms(){        
        document.conFactorForm.reset();
        document.monFactorForm.reset();
        document.dayFactorForm.reset();
        history.go(-1);
    }

    
</script>

</html>
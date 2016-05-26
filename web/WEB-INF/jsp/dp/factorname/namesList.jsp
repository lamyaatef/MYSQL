<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
    <head>
        <link rel="stylesheet" type="text/css"
              href="/SDS/resources/css/Template1.css"/>
        <title>Factor Names</title>
    </head>
    <body>
    <center>
        <h3>Factor Name Management</h3>


        <c:url var="editNameUrl" value="/dp/factorname/edit.htm" />
        <c:url var="addNameUrl" value="/dp/factorname/add.htm" />        
        <c:url var="deleteNameUrl" value="/dp/factorname/delete.htm" />            
            <c:url var="searchNameUrl" value="/dp/factorname/search.htm" />            
            <form:form modelAttribute="name" id="searchFactorNameForm" name="searchFactorNameForm" method="GET" action="${searchNameUrl}">
            
            <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
                   cellSpacing=2 cellPadding=1 width="746" border=1>
                <TR class="TableTextNote">
                    <TD class="TableTextNote" style="WIDTH: 20%;" ><form:label path="factorName">Factor Name:</form:label>    </TD>
                    <TD class="TableTextNote" style="WIDTH: 30%;"><form:input cssStyle="WIDTH: 50%;" path="factorName" />    </TD>            
                </TR>
                <TR class="TableTextNote">
                    <TD class="TableTextNote" style="WIDTH: 20%;"><form:label path="factorTypeId">Factor Type:</form:label>    </TD>
                    <TD class="TableTextNote" style="WIDTH: 30%;"><form:select cssStyle="WIDTH: 50%;" path="factorTypeId" multiple="false">					   
                            <form:option value="0" label="--- Select ---" />
                            <form:options items="${types}"  itemLabel="factorTypeName" itemValue="factorTypeId"/>
                        </form:select></TD>        				
                </TR>
                <TR class="TableTextNote">
                    <TD  class="TableTextNote" colspan="2" align="center" >
                    <input type="submit" value="Search" onclick="changeAction('${searchNameUrl}')"/>                        
                    </TD>
                </TR>
            </Table>   
                    </form:form>
          <form:form id="factorNameForm" name="factorNameForm" method="GET" action="${editNameUrl}">
            <table style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
                   cellSpacing=2 cellPadding=1 width="746" border=1>
                <tr>

                    <th class="TableTextNote"  align="center"><input type="checkbox" id="selectDeselectAll" name="selectDeselectAll"  <c:if test="${!empty names}">onclick="checkAll(document.factorNameForm.list,this);uncheckAll(document.factorNameForm.list,this);"</c:if> /></th>
                    <th class="TableTextNote">Name</th>
                    <th class="TableTextNote">Type</th>
                    <th class="TableTextNote">Description</th>			
                    <th class="TableTextNote">Edit</th>			
                </tr>
                <c:if test="${!empty names}">
                    <c:forEach items="${names}" var="name">                    
                        <tr class="TableTextNote">				
                            <td  align="center" class="TableTextNote"><input type="checkbox" id="list" name="list" value="${name.factorNameId}" onclick="deleteFactorId('${name.factorNameId}',this)" /></td>
                            <td class="TableTextNote"><c:out value="${name.factorName}"/></td>
                            <td class="TableTextNote"><c:out value="${name.factorType.factorTypeName}"/></td>
                            <td class="TableTextNote"><c:out value="${name.factorDescribtion}"/></td>				
                            <td align="center" class="TableTextNote"><input type="submit" value="Edit" onclick="setFactorId('${name.factorNameId}','${name.factorName}','${name.factorDescribtion}','${name.factorType.factorTypeId}')" /></td>				
                        </tr>
                    </c:forEach>
                </c:if>
                <tr class="TableTextNote">                    
                    <td class="TableTextNote" colspan="5" align="center">
                        <input type="submit" value="Add Name" onclick="changeAction('${addNameUrl}')"/>                    
                        <input type="button" value="Delete Names" onclick="checkDeleteAction('${deleteNameUrl}',document.factorNameForm.list);"/>
                    </td>

                </tr>
                
                <c:if test="${erreoDel !=null}">
                <tr class="TableTextNote">                    
                    <td class="TableTextNote" colspan="5" align="center">
                        
                        <label >${erreoDel}</label>                               
                    </td>

                </tr>
                </c:if>
            </table>    

            <input type="hidden" name="factorNameId" id="factorNameId" value="" />
            <input type="hidden" name="factorTypeId" id="factorTypeId" value="" />
            <input type="hidden" name="factorName" id="factorName" value="" />
            <input type="hidden" name="factorNamedesc" id="factorNamedesc" value="" />
            <input type="hidden" name="deleteIds" id="deleteIds" value="" />
        </form:form>
    </center>
</body>
<script>
    
    function setFactorId(id,name,desc,type){
            
        document.factorNameForm.factorNameId.value=id;
        document.factorNameForm.factorTypeId.value=type;
        document.factorNameForm.factorName.value=name;
        document.factorNameForm.factorNamedesc.value=desc;
    }
    function deleteFactorId(id,field){
        
        var deleteIds = document.factorNameForm.deleteIds.value;
        if (field.checked){
            deleteIds+=id+',';
        }
        else{
            deleteIds =deleteIds.replace(id+',', "");
        }        
        document.factorNameForm.deleteIds.value = deleteIds ;
    }   
    function checkAll(field,mainCheckBox)
    {        
        if (mainCheckBox.checked)
        {   
            if (!field.checked)field.checked=true;
            var deleteIds = "";
            for (i = 0; i < field.length; i++){            
                field[i].checked = true ;
                deleteIds += field[i].value+',';
            }
            document.factorNameForm.deleteIds.value=deleteIds;        
        }
    }    
    function uncheckAll(field,mainCheckBox)
    {
        if (!mainCheckBox.checked)
        {   
            if (field.checked)field.checked=false;
                
            for (i = 0; i < field.length; i++){
                field[i].checked = false ;
            }            
            document.factorNameForm.deleteIds.value="";
        }
    }
    function changeAction(action){            
        document.factorNameForm.action=action;
    }
    function checkDeleteAction(action,field){            
        if (document.factorNameForm.deleteIds.value=="")
        {
            alert('Please select at least one factor name.');
            return false;
        }            
        else
        {                
            changeAction(action);
            document.factorNameForm.submit();
                
        }
    
    }
</script>

</html>
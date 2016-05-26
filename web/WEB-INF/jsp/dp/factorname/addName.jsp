<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>    
    <title>Factor Data</title>
    <link rel="stylesheet" type="text/css"
	href="/SDS/resources/css/Template1.css"/>
    
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
</head>
<body>
    <center>
<h3>Factor Data</h3>

<c:url var="saveNameUrl" value="/dp/factorname/save.htm" />
<form:form modelAttribute="name" id="saveNameform" name="saveNameform" method="GET" action="${saveNameUrl}">    
    <form:hidden path="factorNameId"/>    
    <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
           cellSpacing=2 cellPadding=1 width="746" border=1>
        <TR class="TableTextNote">
            <TD class="TableTextNote" style="WIDTH: 20%;" ><form:label path="factorName">Factor Name:</form:label>    </TD>
            <TD class="TableTextNote" style="WIDTH: 30%;"><form:input cssStyle="WIDTH: 100%;" path="factorName" />    </TD>            
            <TD class="TableTextNote" style="WIDTH: 50%;"><font color="red"><form:errors path="factorName" /></font></TD>            
        
        </TR>
        <TR class="TableTextNote">
            <TD class="TableTextNote" style="WIDTH: 20%;"><form:label path="factorDescribtion">Factor Name Desc:</form:label></TD>
            <TD class="TableTextNote" style="WIDTH: 30%;"><form:textarea cssStyle="WIDTH: 100%;" path="factorDescribtion" /></TD>                   
            <TD class="TableTextNote" style="WIDTH: 50%;">&nbsp;</TD>            
        </TR>
        <TR class="TableTextNote">
	<TD class="TableTextNote" style="WIDTH: 20%;"><form:label path="factorTypeId">Factor Type:</form:label>    </TD>
        <TD class="TableTextNote" style="WIDTH: 30%;"><form:select cssStyle="WIDTH: 100%;" path="factorTypeId" multiple="false">
					   <form:option value="0" label="--- Select ---" />
                                           <form:options items="${types}" itemLabel="factorTypeName" itemValue="factorTypeId"/>
					</form:select></TD>        				
				</TD>        
                                <TD class="TableTextNote" style="WIDTH: 50%;"><font color="red"><form:errors path="factorTypeId" /></font></TD>            
	</TR>
        <TR class="TableTextNote">
            <TD  class="TableTextNote" colspan="3" align="center" >
                <input type="submit" value="Save Name" /></TD>
        </TR>
    </table>        
</form:form>
</center>
</body>    
</script>
</html>